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
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raywenderlich.android.rwquotes.data.Quote
import com.raywenderlich.android.rwquotes.data.QuotesRepositoryImpl
import com.raywenderlich.android.rwquotes.ui.viewmodel.QuotesViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Created by Enzo Lizama Paredes on 7/25/20.
 * Contact: lizama.enzo@gmail.com
 */

@RunWith(AndroidJUnit4::class)
class QuotesViewModelTest {

  @Mock
  private lateinit var viewModel: QuotesViewModel

  @Mock
  private lateinit var isLoadingLiveData: LiveData<Boolean>

  @Mock
  private lateinit var observer: Observer<Boolean>

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  /**
   * Setup values before init tests
   *
   */
  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    viewModel = spy(QuotesViewModel(ApplicationProvider.getApplicationContext(),
        QuotesRepositoryImpl(ApplicationProvider.getApplicationContext())))
    isLoadingLiveData = viewModel.dataLoading
  }

  /**
   * Testing *onChanged()* method for [LiveData]
   *
   */
  @Test
  fun `Verify livedata values changes on event`() {
    assertNotNull(viewModel.getAllQuotes())
    viewModel.dataLoading.observeForever(observer)
    verify(observer).onChanged(false)
    viewModel.getAllQuotes()
    verify(observer).onChanged(true)
  }

  /**
   * Test asserting values for [LiveData] items on [QuotesViewModel] to insert [Quote]
   *
   */
  @Test
  fun `Assert loading values are correct fetching quotes`() {
    val testQuote = Quote(id = 1, text = "Hello World!", author = "Ray Wenderlich",
        date = "27/12/1998")
    var isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertTrue(it) }
    viewModel.insertQuote(testQuote)
    isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertFalse(it) }
  }

  /**
   * Test asserting values for [LiveData] items on [QuotesViewModel] to delete [Quote]
   *
   */
  @Test
  fun `Assert loading values are correct deleting quote`() {
    val testQuote = Quote(id = 1, text = "Hello World!", author = "Ray Wenderlich",
        date = "27/12/1998")
    var isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertTrue(it) }
    viewModel.delete(testQuote)
    isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertFalse(it) }
  }

  /**
   * Test asserting values for [LiveData] items on [QuotesViewModel] to update [Quote]
   *
   */
  @Test
  fun `Assert loading values are correct updating quote`() {
    val testQuote = Quote(id = 1, text = "Hello World!", author = "Ray Wenderlich",
        date = "27/12/1998")
    var isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertTrue(it) }
    viewModel.updateQuote(testQuote)
    isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertFalse(it) }
  }


}