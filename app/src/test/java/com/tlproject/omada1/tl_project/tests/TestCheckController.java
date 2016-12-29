package com.tlproject.omada1.tl_project.tests;


import android.location.LocationManager;
import org.junit.Test;
import org.mockito.Mockito;

import com.tlproject.omada1.tl_project.Controller.CheckController;


import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestCheckController {
    @Test
    public void testCheckControllerWithNoGps() {
        CheckController cc = new CheckController();
        LocationManager locationManager = Mockito.mock(LocationManager.class);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        assertEquals(false, cc.GpsEnable(locationManager));
    }
    @Test
    public void testCheckControllerWithNoNetwork() {
        CheckController cc = new CheckController();
        LocationManager locationManager = Mockito.mock(LocationManager.class);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(false);
        assertEquals(false, cc.GpsEnable(locationManager));
    }
    @Test
    public void testCheckControllerWithAll() {
        CheckController cc = new CheckController();
        LocationManager locationManager = Mockito.mock(LocationManager.class);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        assertEquals(true, cc.GpsEnable(locationManager));
    }
}
