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
    protected void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        Context applicationContext = getApplicationContext();
        this.mContext = applicationContext;
        this.mResources = applicationContext.getResources();
        this.mIsTablet = MaintenanceModeUtils.isTablet();
        int iCheckRequiredConditions = MaintenanceModeUtils.checkRequiredConditions(this.mContext, false);
        if (iCheckRequiredConditions != 0) {
            showToast(iCheckRequiredConditions);
            finish();
        } else {
            startIntroActivity();
            finish();
        }
    }

    private void showToast(int i) throws Resources.NotFoundException {
        String string;
        if (i == 1) {
            string = this.mResources.getString(this.mIsTablet ? R.string.maintenance_mode_proxy_isnt_supported_toast_message_tablet : R.string.maintenance_mode_proxy_isnt_supported_toast_message_phone);
        } else if (i == 2) {
            Resources resources = this.mResources;
            string = resources.getString(R.string.maintenance_mode_proxy_only_be_used_toast_message, resources.getString(R.string.maintenance_mode_name));
        } else if (i == 3) {
            Resources resources2 = this.mResources;
            string = resources2.getString(R.string.maintenance_mode_proxy_cant_be_used_toast_message, resources2.getString(R.string.maintenance_mode_toast_text_device_admin));
        } else if (i == 4) {
            Resources resources3 = this.mResources;
            string = resources3.getString(R.string.maintenance_mode_proxy_cant_be_used_toast_message, resources3.getString(R.string.maintenance_mode_toast_text_samsung_dex));
        } else if (i != 5) {
            string = "";
        } else {
            string = this.mResources.getString(R.string.maintenance_mode_proxy_cant_use_while_mpsm_toast_message);
        }
        if (TextUtils.isEmpty(string)) {
            return;
        }
        Toast.makeText(this, string, 1).show();
    }

    private void startIntroActivity() {
        try {
            startActivity(new Intent(this, (Class<?>) MaintenanceModeIntroActivity.class));
        } catch (ActivityNotFoundException e) {
            Log.i("MaintenanceMode", "Failed to start Intro activity: " + e.toString());
        }
    }
}
