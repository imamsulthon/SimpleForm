package com.imams.simpleform.ui.common

sealed class FieldState<T> {
    data class Init<T>(val data: T) : FieldState<T>()
    data class IsNullOrEmpty<T>(val nullOrEmpty: Boolean) : FieldState<T>()
    data class Valid<T>(val data: T? = null) : FieldState<T>()
    data class Warn<T>(val message: String) : FieldState<T>()
}