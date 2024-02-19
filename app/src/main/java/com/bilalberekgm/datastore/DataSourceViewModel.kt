package com.bilalberekgm.datastore

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


//Create a Preferences DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
class DataSourceViewModel(var context: Application): AndroidViewModel(context) {
    val exampleCounterFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[EXAMPLE_COUNTER] ?: 0
        }
             fun incrementCounter() {
                 viewModelScope.launch {
                     context.dataStore.edit { settings ->
                         var currentValue = settings[EXAMPLE_COUNTER] ?: 0
                         currentValue += 1
                         settings[EXAMPLE_COUNTER] = currentValue
                         Log.d("counterController", currentValue.toString())
                     }
                 }

             }
}