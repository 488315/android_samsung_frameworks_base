package com.android.internal.app;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class SetScreenLockDialogActivity extends AlertActivity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public static final String EXTRA_LAUNCH_REASON = "launch_reason";
    public static final String EXTRA_ORIGIN_USER_ID = "origin_user_id";
    public static final int LAUNCH_REASON_DISABLE_QUIET_MODE = 1;
    public static final int LAUNCH_REASON_PRIVATE_SPACE_SETTINGS_ACCESS = 2;
    public static final int LAUNCH_REASON_UNKNOWN = -1;
    private static final String PACKAGE_NAME = "android";
    private static final String TAG = "SetScreenLockDialog";
    private int mOriginUserId;
    private int mReason;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchReason {
    }

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Flags.allowPrivateProfile() || !android.multiuser.Flags.showSetScreenLockDialog() || !android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            finish();
            return;
        }
        Intent intent = getIntent();
        this.mReason = intent.getIntExtra(EXTRA_LAUNCH_REASON, -1);
        this.mOriginUserId = intent.getIntExtra(EXTRA_ORIGIN_USER_ID, -10000);
        if (this.mReason == -1) {
            Log.e(TAG, "Invalid launch reason: " + this.mReason);
            finish();
            return;
        }
        KeyguardManager km = (KeyguardManager) getSystemService(KeyguardManager.class);
        if (km == null) {
            Log.e(TAG, "Error fetching keyguard manager");
            return;
        }
        if (km.isDeviceSecure()) {
            Log.w(TAG, "Closing the activity since screen lock is already set");
            return;
        }
        Log.d(TAG, "Launching screen lock setup dialog due to " + this.mReason);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.set_up_screen_lock_title).setOnDismissListener(this).setPositiveButton(R.string.set_up_screen_lock_action_label, this).setNegativeButton(17039360, this);
        setLaunchUserSpecificMessage(builder);
        AlertDialog dialog = builder.create();
        dialog.create();
        getWindow().setHideOverlayWindows(true);
        dialog.getButton(-1).setFilterTouchesWhenObscured(true);
        dialog.show();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            Intent setNewLockIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
            setNewLockIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, 32768);
            startActivity(setNewLockIntent);
            return;
        }
        finish();
    }

    private void setLaunchUserSpecificMessage(AlertDialog.Builder builder) {
        UserInfo userInfo;
        if (this.mReason == 2) {
            builder.setMessage(R.string.private_space_set_up_screen_lock_message);
            return;
        }
        UserManager userManager = (UserManager) getApplicationContext().getSystemService(UserManager.class);
        if (userManager != null && (userInfo = userManager.getUserInfo(this.mOriginUserId)) != null && userInfo.isPrivateProfile()) {
            builder.setMessage(R.string.private_space_set_up_screen_lock_message);
        }
    }

    public static Intent createBaseIntent(int launchReason) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("android", SetScreenLockDialogActivity.class.getName()));
        intent.setFlags(276824064);
        intent.putExtra(EXTRA_LAUNCH_REASON, launchReason);
        return intent;
    }
}
