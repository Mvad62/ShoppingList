package com.example.shoppinglist.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.ShoppingItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingViewModel(private val dao: com.example.shoppinglist.data.ShoppingDao) : ViewModel() {

    val items: StateFlow<List<ShoppingItem>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addItem(name: String) {
        viewModelScope.launch {
            dao.insert(ShoppingItem(name = name))
        }
    }

    fun toggleBought(item: ShoppingItem) {
        viewModelScope.launch {
            dao.update(item.copy(isBought = !item.isBought))
        }
    }

    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            dao.clearAll()
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val dao = AppDatabase.getDatabase(context).shoppingDao()
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(dao) as T
        }
    }
}