package com.android.internal.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class BlockedAppStreamingActivity extends AlertActivity {
    private static final String BLOCKED_COMPONENT_PLAYSTORE = "com.android.vending";
    private static final String BLOCKED_COMPONENT_SETTINGS = "com.android.settings";
    private static final String EXTRA_BLOCKED_ACTIVITY_INFO = "com.android.internal.app.extra.BLOCKED_ACTIVITY_INFO";
    private static final String EXTRA_STREAMED_DEVICE = "com.android.internal.app.extra.STREAMED_DEVICE";
    private static final String PACKAGE_NAME = "com.android.internal.app";
    private static final String TAG = "BlockedAppStreamingActivity";

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        ActivityInfo activityInfo = (ActivityInfo) intent.getParcelableExtra(EXTRA_BLOCKED_ACTIVITY_INFO, ActivityInfo.class);
        CharSequence charSequenceLoadLabel = activityInfo != null ? activityInfo.loadLabel(getPackageManager()) : null;
        if (TextUtils.isEmpty(charSequenceLoadLabel)) {
            Slog.wtf(TAG, "Invalid activity info: " + activityInfo);
            finish();
            return;
        }
        CharSequence charSequenceExtra = intent.getCharSequenceExtra(EXTRA_STREAMED_DEVICE);
        if (!TextUtils.isEmpty(charSequenceExtra)) {
            if (TextUtils.equals(activityInfo.packageName, getPackageManager().getPermissionControllerPackageName())) {
                this.mAlertParams.mTitle = getString(R.string.app_streaming_blocked_title_for_permission_dialog);
                this.mAlertParams.mMessage = getString(R.string.app_streaming_blocked_message_for_permission_request);
            } else if (TextUtils.equals(activityInfo.packageName, BLOCKED_COMPONENT_PLAYSTORE)) {
                this.mAlertParams.mTitle = getString(R.string.app_streaming_blocked_title_for_playstore_dialog);
                this.mAlertParams.mMessage = getString(R.string.app_streaming_blocked_message, charSequenceExtra);
            } else if (TextUtils.equals(activityInfo.packageName, "com.android.settings")) {
                this.mAlertParams.mTitle = getString(R.string.app_streaming_blocked_title_for_settings_dialog);
                this.mAlertParams.mMessage = getString(R.string.app_streaming_blocked_message_for_settings_dialog, charSequenceExtra);
            } else {
                this.mAlertParams.mMessage = getString(R.string.app_streaming_blocked_message, charSequenceExtra);
            }
        } else {
            this.mAlertParams.mMessage = getString(R.string.app_blocked_message, charSequenceLoadLabel);
        }
        this.mAlertParams.mPositiveButtonText = getString(17039370);
        setupAlert();
    }

    public static Intent createIntent(ActivityInfo activityInfo, CharSequence charSequence) {
        return new Intent().setClassName("android", BlockedAppStreamingActivity.class.getName()).putExtra(EXTRA_BLOCKED_ACTIVITY_INFO, activityInfo).putExtra(EXTRA_STREAMED_DEVICE, charSequence);
    }
}
