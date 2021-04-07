package com.example.testgps;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import com.example.testgps.UserSingleton;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import  org.mockito.ArgumentMatchers;
import static org.mockito.Mockito.when;
//Test cases
//TODO Test 1: Give two different users
//TODO Test 2: Give two of the same user
//TODO Test 3: Give empty element
//TODO Test 4: Give two teams to test team sorting


public class SecondMockitoTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    //TEST 1:
    @Test
    public void Test1() throws JSONException {

        UserSingleton singletonHandler = mock(UserSingleton.class);


        String name = "johnny";
        singletonHandler.setName(name);
        verify(singletonHandler).setName(ArgumentMatchers.eq(name));
    }

    //TEST 2:
    @Test
    public void Test2() throws JSONException {


        MockedStatic<UserSingleton> singletonHandler = mockStatic(UserSingleton.class);


        String id = "17";


        when(UserSingleton.getID()).thenReturn(id);



        String result = UserSingleton.getID();
        System.out.println(result);
        Assert.assertEquals((String)id, (String)result);


        singletonHandler.close();

    }

    //TEST 3:
    @Test
    public void Test3() throws JSONException {

        UserSingleton singletonHandler = mock(UserSingleton.class);

        String name = "johnny";
        singletonHandler.setName(name);

        singletonHandler.setName("bill");
        singletonHandler.setName("jill");
        singletonHandler.setName("fill");

        verify(singletonHandler, times(1)).setName("bill");


    }

    //TEST 4:
    @Test
    public void Test4() throws JSONException {
        MockedStatic<UserSingleton> singletonHandler = mockStatic(UserSingleton.class);
        assertNotNull(singletonHandler);

    }

}
