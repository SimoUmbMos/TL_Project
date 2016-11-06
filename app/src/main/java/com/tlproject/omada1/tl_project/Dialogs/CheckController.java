package com.tlproject.omada1.tl_project.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.tlproject.omada1.tl_project.R;

public class CheckController implements CheckInterface {
    public boolean GpsEnable(final Context Caller){
        LocationManager lm = (LocationManager)Caller.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}
        if(!gps_enabled && !network_enabled) {
            final Dialog dialog = new Dialog(Caller);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setTitle("Gps Not Enable");
            Button ButtonYes = (Button) dialog.findViewById(R.id.btn_yes);
            Button ButtonNo = (Button) dialog.findViewById(R.id.btn_no);
            ButtonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    Caller.startActivity(intent);
                    dialog.dismiss();
                }
            });
            ButtonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            return false;
        }
        return true;
    }
}
