package com.nathanbernal.duocva

import android.util.Log
import com.nathanbernal.duocva.models.Usuario
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class MyUnitTest {

    @Test
    fun checkEmailError() {
        assertEquals(false, EmailValida("nathanbernal2gmail.com"))
    }

    @Test
    fun checkEmail() {
        assertEquals(true, EmailValida("nathanbernal@gmail.com"))
    }

}