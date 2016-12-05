package com.tlproject.omada1.tl_project.tests;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.view.Display;

import com.tlproject.omada1.tl_project.Controller.UserController;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class TestUserController {
    @Test
    public void testUserConexpforLvl(){
        UserController userController=new UserController();
        User user=new User();
        user.setLvl(1);
        int res=userController.expforLvl(user);
        assertEquals(1000, res);
    }
}
