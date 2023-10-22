package com.subreax.hackaton.data.user.mock

import com.subreax.hackaton.data.user.User
import com.subreax.hackaton.data.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MockUserRepository @Inject constructor() : UserRepository {
    override suspend fun getUser(): User = withContext(Dispatchers.IO) {
        delay(1)
        User("123456", "subreax")
    }
}
