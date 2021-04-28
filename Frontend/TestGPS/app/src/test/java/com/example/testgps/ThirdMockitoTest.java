package com.example.testgps;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ThirdMockitoTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    //TEST 1:
    @Test
    public void Test1() throws JSONException {
        BogeySingleton singleton = mock(BogeySingleton.class);

        singleton.setName("johnny");
        singleton.setPass("mom1245");
        singleton.setID("5");

        //checking we can set values
        verify(singleton).setName(ArgumentMatchers.eq("johnny"));
        verify(singleton).setPass(ArgumentMatchers.eq("mom1245"));
        verify(singleton).setID(ArgumentMatchers.eq("5"));
    }

    //TEST 2:
    @Test
    public void Test2() throws JSONException {
        BogeySingleton singleton = mock(BogeySingleton.class);

        singleton.setName("johnny");
        singleton.setPass("mom1245");
        singleton.setID("5");

        singleton.setName("tyler");
        singleton.setPass("dad4321");
        singleton.setID("2");

        //checking that you're able to reset a value
        verify(singleton).setName(ArgumentMatchers.eq("tyler"));
        verify(singleton).setPass(ArgumentMatchers.eq("dad4321"));
        verify(singleton).setID(ArgumentMatchers.eq("2"));
    }


    //TEST 3:
    @Test
    public void Test3() throws JSONException {
        MockedStatic<BogeySingleton> singleton = mockStatic(BogeySingleton.class);
        //BogeySingleton singleton = mock(BogeySingleton.class);

//        singleton.setName("johnny");
//        singleton.setPass("mom1245");
//        singleton.setID("5");

        //checking the get methods
        when(BogeySingleton.getName()).thenReturn("johnny");
        when(BogeySingleton.getPass()).thenReturn("mom1245");
        when(BogeySingleton.getID()).thenReturn("5");

        Assert.assertEquals(BogeySingleton.getName(), "johnny");
        Assert.assertEquals(BogeySingleton.getPass(), "mom1245");
        Assert.assertEquals(BogeySingleton.getID(), "5");


    }

    //TEST 4:
    @Test
    public void Test4() throws JSONException {
        BogeySingleton singleton = mock(BogeySingleton.class);


        when(BogeySingleton.getName()).thenReturn("johnny");
        when(BogeySingleton.getPass()).thenReturn("mom1245");
        when(BogeySingleton.getID()).thenReturn("5");

        Assert.assertNotEquals(BogeySingleton.getName(), "");
        Assert.assertNotEquals(BogeySingleton.getPass(), "");
        Assert.assertNotEquals(BogeySingleton.getID(), "");



    }

}
