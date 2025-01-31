package com.project.androidbingo.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
// zostawiamy do przyszlych celow rozbudowy aplikacji

data class LoggedInUserView(
    val displayName: String
    //... other data fields that may be accessible to the UI
)