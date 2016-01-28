package com.tolsma.ryan.airlinecheckin.services;

import com.squareup.otto.Bus;
import com.tolsma.ryan.airlinecheckin.BuildConfig;
import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.components.AppComponent;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.services.requests.SouthwestCheckinRequest;
import com.tolsma.ryan.airlinecheckin.services.requests.SouthwestDeliveryRequest;
import com.tolsma.ryan.airlinecheckin.services.requests.SouthwestValidityRequest;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ryan on 1/26/16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = CleanupApplication.class, sdk = 21)
public class LoginServiceTest {
    static String confirmation = "HD6YXD", firstName = "Ryan", lastName = "Tolsma";
    static String phoneNumber = "9495006863", emailAddress = "1tolsmar@gmail.com";
    SouthwestLoginEvent sl = new SouthwestLoginEvent();

    @Before
    public void init() {
        sl.setConfirmationCode(confirmation);
        sl.setFirstName(firstName);
        sl.setLastName(lastName);
        sl.setAirline("Southwest");
        sl.setFlightDate(new Date(new Date().getTime() + ConstantsConfig.DAY_MILLLIS));

        AppComponent appComponent = mock(AppComponent.class);
        when(appComponent.context()).thenReturn(RuntimeEnvironment.application);
        when(appComponent.eventBus()).thenReturn(mock(Bus.class));
        CleanupApplication.setUseMocked(true);
        //Setup activity and application

    }


    @Test
    public void loginValidityAttemptTest() {

        assertTrue(SouthwestValidityRequest.isLoginValid(new SouthwestLogin(sl), false));
    }

    @Test
    public void loginCheckinAttemptTest() {

        assertTrue(SouthwestCheckinRequest.isCheckedIn());
    }

    @Test
    public void passDeliveredTest() {

        assertTrue(SouthwestDeliveryRequest.isDeliveredIn(emailAddress, true));
        assertTrue(SouthwestDeliveryRequest.isDeliveredIn(phoneNumber, false));
        assertFalse(SouthwestDeliveryRequest.isDeliveredIn(phoneNumber, true));
    }

    @Test
    public void fullCheckIn() {
        assertTrue(
                SouthwestValidityRequest.isLoginValid(new SouthwestLogin(sl), false)
                        && SouthwestCheckinRequest.isCheckedIn()
                        && SouthwestDeliveryRequest.isDeliveredIn(emailAddress, true)
                        && SouthwestDeliveryRequest.isDeliveredIn(phoneNumber, false)
        );

    }


}