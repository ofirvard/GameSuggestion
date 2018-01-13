package com.example.ofir.gamesuggestion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by ofir on 1/11/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitTestOne
{
    private static final String FAKE_STRING = "HELLO WORLD";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString()
    {
        // Given a mocked Context injected into the object under test...
        when(mMockContext.getString(R.string.hello_world))
                .thenReturn(FAKE_STRING);
        User myObjectUnderTest = new User("ofir", "123", true);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.sayHello();

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_STRING));
    }
}
