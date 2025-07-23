package com.android.internal.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Slog;
import android.widget.Toast;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class FrozenAppActivity extends AlertActivity {
    private static final String EXTRA_FROZEN_PACKAGE = "com.android.internal.app.extra.FROZEN_PACKAGE";
    private static final String PACKAGE_NAME = "com.android.internal.app";
    private static final String TAG = "FrozenAppActivity";

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra("android.intent.extra.USER_ID", -1);
        if (intExtra < 0) {
            Slog.wtf(TAG, "Invalid user: " + intExtra);
            finish();
            return;
        }
        String stringExtra = intent.getStringExtra(EXTRA_FROZEN_PACKAGE);
        if (TextUtils.isEmpty(stringExtra)) {
            Slog.wtf(TAG, "Invalid package: " + stringExtra);
            finish();
            return;
        }
        Toast.makeText(getApplicationContext(), getString(R.string.app_updating_message, getAppLabel(intExtra, stringExtra)), 1).show();
        finish();
    }

    private CharSequence getAppLabel(int i, String str) {
        PackageManager packageManager = getPackageManager();
        try {
            return packageManager.getApplicationInfoAsUser(str, 0, i).loadLabel(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "Package " + str + " not found", e);
            return str;
        }
    }

    public static Intent createIntent(int i, String str) {
        return new Intent().setClassName("android", FrozenAppActivity.class.getName()).putExtra("android.intent.extra.USER_ID", i).putExtra(EXTRA_FROZEN_PACKAGE, str).setFlags(276824064);
    }
}
