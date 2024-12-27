package com.samsung.android.core.pm.mm;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.R;

/* loaded from: classes6.dex */
public class MaintenanceModeProxyActivity extends Activity {
    private static final String TAG = "MaintenanceMode";
    private Context mContext;
    private boolean mIsTablet = false;
    private Resources mResources;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();
        this.mResources = this.mContext.getResources();
        this.mIsTablet = MaintenanceModeUtils.isTablet();
        int unsupportedReason = MaintenanceModeUtils.checkRequiredConditions(this.mContext, false);
        if (unsupportedReason != 0) {
            showToast(unsupportedReason);
            finish();
        } else {
            startIntroActivity();
            finish();
        }
    }

    private void showToast(int reason) {
        int i;
        String msg = "";
        switch (reason) {
            case 1:
                Resources resources = this.mResources;
                if (this.mIsTablet) {
                    i = R.string.maintenance_mode_proxy_isnt_supported_toast_message_tablet;
                } else {
                    i = R.string.maintenance_mode_proxy_isnt_supported_toast_message_phone;
                }
                msg = resources.getString(i);
                break;
            case 2:
                msg =
                        this.mResources.getString(
                                R.string.maintenance_mode_proxy_only_be_used_toast_message,
                                this.mResources.getString(R.string.maintenance_mode_name));
                break;
            case 3:
                msg =
                        this.mResources.getString(
                                R.string.maintenance_mode_proxy_cant_be_used_toast_message,
                                this.mResources.getString(
                                        R.string.maintenance_mode_toast_text_device_admin));
                break;
            case 4:
                msg =
                        this.mResources.getString(
                                R.string.maintenance_mode_proxy_cant_be_used_toast_message,
                                this.mResources.getString(
                                        R.string.maintenance_mode_toast_text_samsung_dex));
                break;
            case 5:
                msg =
                        this.mResources.getString(
                                R.string.maintenance_mode_proxy_cant_use_while_mpsm_toast_message);
                break;
        }
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, 1).show();
        }
    }

    private void startIntroActivity() {
        try {
            Intent intent = new Intent(this, (Class<?>) MaintenanceModeIntroActivity.class);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.i("MaintenanceMode", "Failed to start Intro activity: " + e.toString());
        }
    }
}
