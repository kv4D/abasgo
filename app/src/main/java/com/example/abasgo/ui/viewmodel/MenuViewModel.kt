package com.example.abasgo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.abasgo.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

}