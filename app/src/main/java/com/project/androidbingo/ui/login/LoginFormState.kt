package com.project.androidbingo.ui.login

/**
 * Data validation state of the login form.
 */
// zostawiamy do przyszlych celow rozbudowy aplikacji

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)