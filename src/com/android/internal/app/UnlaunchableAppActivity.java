package com.android.internal.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.telecom.TelecomManager;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.samsung.android.knox.SemPersonaManager;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class UnlaunchableAppActivity extends Activity implements DialogInterface.OnDismissListener, DialogInterface.OnClickListener {
    private static final String EXTRA_UNLAUNCHABLE_REASON = "unlaunchable_reason";
    private static final String TAG = "UnlaunchableAppActivity";
    private static final int UNLAUNCHABLE_REASON_QUIET_MODE = 1;
    private int mReason;
    private IntentSender mTarget;
    private TelecomManager mTelecomManager;
    private int mUserId;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws Resources.NotFoundException {
        AlertDialog.Builder builder;
        super.onCreate(bundle);
        requestWindowFeature(1);
        Intent intent = getIntent();
        this.mTelecomManager = (TelecomManager) getSystemService(TelecomManager.class);
        this.mReason = intent.getIntExtra(EXTRA_UNLAUNCHABLE_REASON, -1);
        this.mUserId = intent.getIntExtra("android.intent.extra.user_handle", -10000);
        this.mTarget = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT", IntentSender.class);
        String stringExtra = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
        Log.i(TAG, "Unlaunchable activity for target package: " + stringExtra);
        final UserManager userManager = UserManager.get(this);
        int i = this.mUserId;
        if (i == -10000) {
            Log.wtf(TAG, "Invalid user id: " + this.mUserId + ". Stopping.");
            finish();
            return;
        }
        if (SemPersonaManager.isSecureFolderId(i)) {
            Log.d(TAG, "requestQuietMode off for secure folder user");
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.internal.app.UnlaunchableAppActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$0(userManager);
                }
            });
            finish();
            return;
        }
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && !userManager.isManagedProfile(this.mUserId)) {
            Log.e(TAG, "Unlaunchable activity for target package " + stringExtra + " called for a non-managed-profile " + this.mUserId);
            finish();
            return;
        }
        if (this.mReason != 1) {
            Log.wtf(TAG, "Invalid unlaunchable type: " + this.mReason);
            finish();
            return;
        }
        String dialogTitle = getDialogTitle();
        String dialogMessage = getDialogMessage();
        String string = getResources().getString(R.string.work_mode_turn_on_btn);
        boolean z = stringExtra != null && stringExtra.equals(this.mTelecomManager.getDefaultDialerPackage(UserHandle.of(this.mUserId)));
        if (z) {
            builder = new AlertDialog.Builder(this, R.style.AlertDialogWithEmergencyButton);
            builder.setNeutralButton(R.string.work_mode_emergency_call_button, this);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(dialogTitle).setOnDismissListener(this).setPositiveButton(string, this).setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
        builder.setMessage(dialogMessage);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.create();
        if (z) {
            alertDialogCreate.getWindow().findViewById(R.id.parentPanel).setPadding(0, 0, 0, 30);
            alertDialogCreate.getWindow().findViewById(16908315).setOutlineProvider(null);
        }
        getWindow().setHideOverlayWindows(true);
        alertDialogCreate.getButton(-1).setFilterTouchesWhenObscured(true);
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(UserManager userManager) {
        userManager.requestQuietModeEnabled(false, UserHandle.of(this.mUserId), this.mTarget);
    }

    private String getWorkProfileName() {
        return SemPersonaManager.getKnoxInfoForApp(this, "getContainerLabel").getString("getContainerLabel");
    }

    private String getDialogTitle() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.UNLAUNCHABLE_APP_WORK_PAUSED_TITLE, new Supplier() { // from class: com.android.internal.app.UnlaunchableAppActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$getDialogTitle$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getDialogTitle$1() {
        return getString(R.string.work_mode_off_title_alert, getWorkProfileName());
    }

    private String getDialogMessage() {
        return getResources().getString(R.string.work_profile_on_message);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (this.mReason != 1) {
            return;
        }
        if (i == -1) {
            final UserManager userManager = UserManager.get(this);
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.internal.app.UnlaunchableAppActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onClick$2(userManager);
                }
            });
        } else if (i == -3) {
            launchEmergencyDialer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(UserManager userManager) {
        userManager.requestQuietModeEnabled(false, UserHandle.of(this.mUserId), this.mTarget);
    }

    private void launchEmergencyDialer() {
        startActivity(this.mTelecomManager.createLaunchEmergencyDialerIntent(null).setFlags(343932928));
    }

    private static final Intent createBaseIntent() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("android", UnlaunchableAppActivity.class.getName()));
        intent.setFlags(276824064);
        return intent;
    }

    public static Intent createInQuietModeDialogIntent(int i) {
        Intent intentCreateBaseIntent = createBaseIntent();
        intentCreateBaseIntent.putExtra(EXTRA_UNLAUNCHABLE_REASON, 1);
        intentCreateBaseIntent.putExtra("android.intent.extra.user_handle", i);
        return intentCreateBaseIntent;
    }

    public static Intent createInQuietModeDialogIntent(int i, IntentSender intentSender, ResolveInfo resolveInfo) {
        Intent intentCreateInQuietModeDialogIntent = createInQuietModeDialogIntent(i);
        intentCreateInQuietModeDialogIntent.putExtra("android.intent.extra.INTENT", intentSender);
        if (resolveInfo != null) {
            intentCreateInQuietModeDialogIntent.putExtra("android.intent.extra.PACKAGE_NAME", resolveInfo.getComponentInfo().packageName);
        }
        return intentCreateInQuietModeDialogIntent;
    }
}
