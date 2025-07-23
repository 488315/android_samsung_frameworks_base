package com.android.internal.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class BlockedAppActivity extends AlertActivity {
    private static final String EXTRA_BLOCKED_PACKAGE = "com.android.internal.app.extra.BLOCKED_PACKAGE";
    private static final String PACKAGE_NAME = "com.android.internal.app";
    private static final String TAG = "BlockedAppActivity";

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
        String stringExtra = intent.getStringExtra(EXTRA_BLOCKED_PACKAGE);
        if (TextUtils.isEmpty(stringExtra)) {
            Slog.wtf(TAG, "Invalid package: " + stringExtra);
            finish();
            return;
        }
        CharSequence appLabel = getAppLabel(intExtra, stringExtra);
        this.mAlertParams.mTitle = getString(R.string.app_blocked_title);
        this.mAlertParams.mMessage = getString(R.string.app_blocked_message, appLabel);
        this.mAlertParams.mPositiveButtonText = getString(17039370);
        setupAlert();
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
        return new Intent().setClassName("android", BlockedAppActivity.class.getName()).putExtra("android.intent.extra.USER_ID", i).putExtra(EXTRA_BLOCKED_PACKAGE, str);
    }
}
