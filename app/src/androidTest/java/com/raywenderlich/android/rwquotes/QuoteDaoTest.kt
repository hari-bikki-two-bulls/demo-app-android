/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.rwquotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raywenderlich.android.rwquotes.data.Quote
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Enzo Lizama Paredes on 7/26/20.
 * Contact: lizama.enzo@gmail.com
 */

/**
 * Testing the CRUD operations for DAO operations
 */
@RunWith(AndroidJUnit4::class)
open class QuoteDaoTest : DatabaseTest() {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Test
  fun insertQuoteTest() {
    val quote = Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
    appDatabase.quotesDao().insertQuote(quote)
    val quotesSize = appDatabase.quotesDao().getQuotes().getValueBlocking()?.size
    assertEquals(quotesSize, 1)
  }

  @Test
  fun deleteQuoteTest() {
    val quote = Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
    appDatabase.quotesDao().insertQuote(quote)
    assertEquals(appDatabase.quotesDao().getQuotes().getValueBlocking()?.size, 1)
    appDatabase.quotesDao().deleteQuote(quote)
    assertEquals(appDatabase.quotesDao().getQuotes().getValueBlocking()?.size, 0)
  }

  @Test
  fun getQuoteAsLiveDataTest() {
    val quote = Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
    appDatabase.quotesDao().insertQuote(quote)
    val quoteLiveDataValue = appDatabase.quotesDao().getQuotes().getValueBlocking()
    assertEquals(quoteLiveDataValue?.size, 1)
  }

  @Test
  fun updateQuoteTest() {
    var quote = Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
    appDatabase.quotesDao().insertQuote(quote)
    quote.author = "Enzo Lizama"
    appDatabase.quotesDao().updateQuote(quote)
    assertEquals(appDatabase.quotesDao().getQuotes().getValueBlocking()?.get(0)?.author, "Enzo " +
        "Lizama")
  }

}