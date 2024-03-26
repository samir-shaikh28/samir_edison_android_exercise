package jp.speakbuddy.edisonandroidexercise.domain

import jp.speakbuddy.edisonandroidexercise.domain.model.Fact
import org.junit.Assert.assertTrue
import org.junit.Test

class FactTest {

    @Test
    fun testThat_showLengthLabelIsSetToFalse_whenLengthIsLessThan100(){
        //Setup & Action
        val fact = Fact("some fact", 20)
        // Assert
        assertTrue(!fact.showLengthLabel)
    }

    @Test
    fun testThat_showLengthLabelIsSetToTrue_whenLengthGraterOrEqualTo100(){
        //Setup & Action
        val factOne = Fact("some fact", 100)
        val factTwo = Fact("some other fact", 120)
        // Assert
        assertTrue(factOne.showLengthLabel)
        assertTrue(factTwo.showLengthLabel)
    }

    @Test
    fun testThat_showMultipleCatsLabelIsSetToTrue_whenFactContainsWordCats(){
        //Setup & Action
        val fact = Fact("some cats fact", 100)
        // Assert
        assertTrue(fact.showMultipleCatsLabel)
    }
 }