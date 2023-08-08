package com.herokuapp.bookinginfo;

import com.herokuapp.bookingInfo.BookingSteps;
import com.herokuapp.testbase.TestBase;
import com.herokuapp.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class BookungCRUDTestWithSteps {
    @RunWith(SerenityRunner.class)
    public class BookingCURDTestWithSteps extends TestBase {
        String firstName = "PrimUser" + TestUtils.getRandomValue();
         String updateFirstName = "Update" + TestUtils.getRandomValue();
        String lastName = "Testing" + TestUtils.getRandomValue();
         String additionalNeeds = "Breakfast";
         int bookingId;

         String token;

        @Steps
        BookingSteps bookingSteps;

        @Title("This will return token")
        @Test
        public void test001() {
            token = bookingSteps.fetchToken("admin","password123");
            System.out.println("Token :" + token);
            Assert.assertNotNull(token);
        }


        @Title("This will create booking")
        @Test()
        public void test002() {
            System.out.println("====================" + token);
            bookingId = bookingSteps.creatingBooking(firstName,lastName,111, true, additionalNeeds);
            System.out.println("bookingId :" + bookingId);
            Assert.assertNotNull(bookingId);
        }


        @Title("This will update booking with firstname")
        @Test
        public void test003() {
            String updatedFirstNameResult =  bookingSteps.updatingBooking(updateFirstName,lastName,111,true,additionalNeeds, token, bookingId);
            System.out.println("updatedFirstNameResult -- " + updatedFirstNameResult);
            Assert.assertEquals(updateFirstName, updatedFirstNameResult);
        }
        @Title("This will fetch booking details by booking id")
        @Test
        public void test004() {
            HashMap<String, Object> bookingMap = bookingSteps.fetchDetailsByBookingId(bookingId, token);
            System.out.println("updateFirstName - " + updateFirstName);
            System.out.println("lastName - " + lastName);
            System.out.println("additionalNeeds - " + additionalNeeds);
            Assert.assertThat(bookingMap, hasValue(updateFirstName));
            Assert.assertThat(bookingMap, hasValue(lastName));
            Assert.assertThat(bookingMap, hasValue(additionalNeeds));
        }

        @Title("This will delete booking")
        @Test
        public void test005() {

            bookingSteps.deleteBooking(bookingId, token).statusCode(201);
            bookingSteps.fetchBooking(bookingId, token).statusCode(404);
        }}}
