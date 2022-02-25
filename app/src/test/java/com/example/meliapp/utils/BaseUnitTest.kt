package com.example.meliapp.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseUnitTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
}