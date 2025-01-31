package com.project.androidbingo.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
// zostawiamy do przyszlych celow rozbudowy aplikacji

data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)