package com.example.usermanager.data.repository

import com.example.usermanager.TestConstants
import com.example.usermanager.addRequestData
import com.example.usermanager.data.model.AddUserRequestData
import com.example.usermanager.data.model.UserItem
import com.example.usermanager.data.network.ApiService
import com.example.usermanager.data.reposirory.UsersRepositoryImpl
import com.example.usermanager.domain.model.AddUserRequestDataEntity
import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.userItem
import com.example.usermanager.userList
import com.example.usermanager.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Logger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UsersRepositoryImplTest {
    private lateinit var apiService: ApiService
    private lateinit var usersRepository: UsersRepository
    private lateinit var mockWebServer: MockWebServer
    private val logger = Logger.getLogger(UsersRepositoryImplTest::class.java.name)

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        usersRepository = UsersRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenAPiServiceReturnsUsers_whenGetUsersIsCalled_thenReturnUsers() = runTest {
        val gson = Gson()
        val userListJson = gson.toJson(userList)
        logger.info("Mock Response Body: $userListJson")

        val initialMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(userListJson)
            .addHeader("x-pagination-pages", "2")

        mockWebServer.enqueue(initialMockResponse)

        val finalMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(userListJson)

        mockWebServer.enqueue(finalMockResponse)

        val result = usersRepository.getUsers(TestConstants.PAGE).toList()
        logger.info("Repository Result: $result")
        assertEquals(2, result.size)
        assertTrue { result[0] is Resource.Loading }
        assertTrue { result[1] is Resource.Success }
        val users = (result[1] as Resource.Success).data
        assertEquals(1, users?.size)
        assertEquals(TestConstants.EMAIL, users?.get(0)?.email)
    }

    @Test
    fun givenAPiServiceReturnsError_whenGetUsersIsCalled_thenReturnError() = runTest {
        val errorResponse = MockResponse()
            .setResponseCode(404)
        mockWebServer.enqueue(errorResponse)
        val result = usersRepository.getUsers(TestConstants.PAGE).toList()
        logger.info("Repository Result: $result")
        assertEquals(2, result.size)
        assertTrue { result[0] is Resource.Loading }
        assertTrue { result[1] is Resource.Error }
    }

    @Test
    fun givenAPiServiceReturnsError_whenGetUsersIsCalled_thenReturnError_withInFinalResponse() = runTest {
        val gson = Gson()
        val userListJson = gson.toJson(userList)
        logger.info("Mock Response Body: $userListJson")

        val initialMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(userListJson)
            .addHeader("x-pagination-pages", "2")

        mockWebServer.enqueue(initialMockResponse)

        val errorResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(errorResponse)
        val result = usersRepository.getUsers(TestConstants.PAGE).toList()
        logger.info("Repository Result: $result")
        assertEquals(2, result.size)
        assertTrue { result[0] is Resource.Loading }
        assertTrue { result[1] is Resource.Error }
    }

    @Test
    fun givenApiServiceReturnsData_whenAddUserIsCalled_thenReturnData() = runTest {
        val gson = Gson()
        val userJson = gson.toJson(userItem)

        val mockResponse = MockResponse()
            .setResponseCode(201)
            .setBody(userJson)

        mockWebServer.enqueue(mockResponse)

        val result = usersRepository.addUser(addRequestData).toList()
        assertEquals(2, result.size)
        assertTrue { result[0] is Resource.Loading }
        assertTrue { result[1] is Resource.Success }
        val user = (result[1] as Resource.Success).data
        assertEquals(TestConstants.EMAIL, user?.email)
    }

    @Test
    fun givenApiServiceReturnsError_whenAddUserIsCalled_thenReturnError() = runTest {
        val errorResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(errorResponse)

        val result = usersRepository.addUser(addRequestData).toList()
        assertEquals(2, result.size)
        assertTrue { result[0] is Resource.Loading }
        assertTrue { result[1] is Resource.Error }
    }

    @Test
    fun givenApiServiceReturnsData_whenDeleteUserIsCalled_thenReturnData() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(204)
        mockWebServer.enqueue(mockResponse)
        val result = usersRepository.deleteUser(TestConstants.USER_ID).toList()
        assertEquals(2, result.size)
        assertTrue { result[0] is Resource.Loading }
        assertTrue { result[1] is Resource.Success }
    }

    @Test
    fun givenApiServiceReturnsError_whenDeleteUserIsCalled_thenReturnError() = runTest {
        val errorResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(errorResponse)
        val result = usersRepository.deleteUser(TestConstants.USER_ID).toList()
        assertEquals(2, result.size)
        assertTrue { result[0] is Resource.Loading }
        assertTrue { result[1] is Resource.Error }
    }

}

