package com.afeka.agile.hitchikercollector;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import utils.LocationUtil;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        LocationUtil locationUtil=new LocationUtil(appContext);
        String location=locationUtil.getCurrentLocation();
        System.out.print(location);
        //assertEquals("com.afeka.agile.hitchikercollector", appContext.getPackageName());
    }
}
