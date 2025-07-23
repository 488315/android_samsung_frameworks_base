package com.android.internal.app;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.UserPackage;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.app.AlertController;
import com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.Flags;
import com.android.internal.util.ArrayUtils;
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public class SuspendedAppActivity extends AlertActivity implements DialogInterface.OnClickListener {
    private static final String DIGITAL_WELLBEING_PACKAGE = "com.samsung.android.forest";
    public static final String EXTRA_ACTIVITY_OPTIONS = "com.android.internal.app.extra.ACTIVITY_OPTIONS";
    public static final String EXTRA_DIALOG_INFO = "com.android.internal.app.extra.DIALOG_INFO";
    public static final String EXTRA_SUSPENDED_PACKAGE = "com.android.internal.app.extra.SUSPENDED_PACKAGE";
    public static final String EXTRA_SUSPENDING_PACKAGE = "com.android.internal.app.extra.SUSPENDING_PACKAGE";
    public static final String EXTRA_SUSPENDING_USER = "com.android.internal.app.extra.SUSPENDING_USER";
    public static final String EXTRA_UNSUSPEND_INTENT = "com.android.internal.app.extra.UNSUSPEND_INTENT";
    private static final String PACKAGE_NAME = "com.android.internal.app";
    private static final String TAG = "SuspendedAppActivity";
    private Intent mMoreDetailsIntent;
    private int mNeutralButtonAction;
    private IntentSender mOnUnsuspend;
    private Bundle mOptions;
    private PackageManager mPm;
    private SuspendDialogInfo mSuppliedDialogInfo;
    private BroadcastReceiver mSuspendModifiedReceiver = new BroadcastReceiver() { // from class: com.android.internal.app.SuspendedAppActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_PACKAGES_SUSPENSION_CHANGED.equals(intent.getAction()) && ArrayUtils.contains(intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST), SuspendedAppActivity.this.mSuspendedPackage)) {
                SuspendedAppActivity suspendedAppActivity = SuspendedAppActivity.this;
                if (suspendedAppActivity.isPackageSuspended(suspendedAppActivity.mSuspendedPackage) || SuspendedAppActivity.this.isFinishing()) {
                    return;
                }
                Slog.w(SuspendedAppActivity.TAG, "Package " + SuspendedAppActivity.this.mSuspendedPackage + " has modified suspension conditions while dialog was visible. Finishing.");
                SuspendedAppActivity.this.finish();
            }
        }
    };
    private String mSuspendedPackage;
    private Resources mSuspendingAppResources;
    private String mSuspendingPackage;
    private int mSuspendingUserId;
    private int mUserId;
    private UsageStatsManager mUsm;

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPackageSuspended(String str) {
        try {
            return this.mPm.isPackageSuspended(str);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "Package " + str + " not found", e);
            return false;
        }
    }

    private CharSequence getAppLabel(String str) {
        try {
            return this.mPm.getApplicationInfoAsUser(str, 0, this.mUserId).loadLabel(this.mPm);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "Package " + str + " not found", e);
            return str;
        }
    }

    private Intent getMoreDetailsActivity() {
        Intent intent = new Intent(Intent.ACTION_SHOW_SUSPENDED_APP_DETAILS).setPackage(this.mSuspendingPackage);
        ResolveInfo resolveActivityAsUser = this.mPm.resolveActivityAsUser(intent, 786432, this.mSuspendingUserId);
        if (resolveActivityAsUser == null || resolveActivityAsUser.activityInfo == null || !Manifest.permission.SEND_SHOW_SUSPENDED_APP_DETAILS.equals(resolveActivityAsUser.activityInfo.permission)) {
            return null;
        }
        if (isDigitalWellbingPackage(this.mSuspendingPackage)) {
            intent.putExtra("android.intent.extra.PACKAGE_NAME", this.mSuspendedPackage).setFlags(335577088);
            return intent;
        }
        intent.putExtra("android.intent.extra.PACKAGE_NAME", this.mSuspendedPackage).setFlags(335544320);
        return intent;
    }

    private Drawable resolveIcon() {
        Resources resources;
        SuspendDialogInfo suspendDialogInfo = this.mSuppliedDialogInfo;
        int iconResId = suspendDialogInfo != null ? suspendDialogInfo.getIconResId() : 0;
        if (iconResId == 0 || (resources = this.mSuspendingAppResources) == null) {
            return null;
        }
        try {
            return resources.getDrawable(iconResId, getTheme());
        } catch (Resources.NotFoundException unused) {
            Slog.e(TAG, "Could not resolve drawable resource id " + iconResId);
            return null;
        }
    }

    private String resolveTitle() {
        Resources resources;
        SuspendDialogInfo suspendDialogInfo = this.mSuppliedDialogInfo;
        if (suspendDialogInfo != null) {
            int titleResId = suspendDialogInfo.getTitleResId();
            String title = this.mSuppliedDialogInfo.getTitle();
            if (titleResId != 0 && (resources = this.mSuspendingAppResources) != null) {
                try {
                    return resources.getString(titleResId);
                } catch (Resources.NotFoundException unused) {
                    Slog.e(TAG, "Could not resolve string resource id " + titleResId);
                }
            } else if (title != null) {
                return title;
            }
        }
        return this.getString(R.string.app_suspended_title);
    }

    private String resolveDialogMessage() {
        Resources resources;
        CharSequence appLabel = getAppLabel(this.mSuspendedPackage);
        SuspendDialogInfo suspendDialogInfo = this.mSuppliedDialogInfo;
        if (suspendDialogInfo != null) {
            int dialogMessageResId = suspendDialogInfo.getDialogMessageResId();
            String dialogMessage = this.mSuppliedDialogInfo.getDialogMessage();
            if (dialogMessageResId != 0 && (resources = this.mSuspendingAppResources) != null) {
                try {
                    return resources.getString(dialogMessageResId, appLabel);
                } catch (Resources.NotFoundException unused) {
                    Slog.e(TAG, "Could not resolve string resource id " + dialogMessageResId);
                }
            } else if (dialogMessage != null) {
                return String.format(getResources().getConfiguration().getLocales().get(0), dialogMessage, appLabel);
            }
        }
        return this.getString(R.string.app_suspended_default_message, appLabel, this.getAppLabel(this.mSuspendingPackage));
    }

    private String resolveNeutralButtonText() {
        int i;
        Resources resources;
        int i2 = this.mNeutralButtonAction;
        if (i2 != 0) {
            if (i2 != 1) {
                Slog.w(TAG, "Unknown neutral button action: " + this.mNeutralButtonAction);
                return null;
            }
            i = R.string.app_suspended_unsuspend_message;
        } else {
            if (this.mMoreDetailsIntent == null) {
                return null;
            }
            i = R.string.app_suspended_more_details;
        }
        SuspendDialogInfo suspendDialogInfo = this.mSuppliedDialogInfo;
        if (suspendDialogInfo != null) {
            int neutralButtonTextResId = suspendDialogInfo.getNeutralButtonTextResId();
            String neutralButtonText = this.mSuppliedDialogInfo.getNeutralButtonText();
            if (neutralButtonTextResId != 0 && (resources = this.mSuspendingAppResources) != null) {
                try {
                    return resources.getString(neutralButtonTextResId);
                } catch (Resources.NotFoundException unused) {
                    Slog.e(TAG, "Could not resolve string resource id " + neutralButtonTextResId);
                }
            } else if (neutralButtonText != null) {
                return neutralButtonText;
            }
        }
        return this.getString(i);
    }

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPm = getPackageManager();
        this.mUsm = (UsageStatsManager) getSystemService(UsageStatsManager.class);
        getWindow().setType(2008);
        Intent intent = getIntent();
        this.mOptions = intent.getBundleExtra(EXTRA_ACTIVITY_OPTIONS);
        int intExtra = intent.getIntExtra("android.intent.extra.USER_ID", -1);
        this.mUserId = intExtra;
        if (intExtra < 0) {
            Slog.wtf(TAG, "Invalid user: " + this.mUserId);
            finish();
            return;
        }
        this.mSuspendedPackage = intent.getStringExtra(EXTRA_SUSPENDED_PACKAGE);
        this.mSuspendingPackage = intent.getStringExtra(EXTRA_SUSPENDING_PACKAGE);
        if (Flags.crossUserSuspensionEnabledRo()) {
            this.mSuspendingUserId = intent.getIntExtra(EXTRA_SUSPENDING_USER, this.mUserId);
        } else {
            this.mSuspendingUserId = this.mUserId;
        }
        this.mSuppliedDialogInfo = (SuspendDialogInfo) intent.getParcelableExtra(EXTRA_DIALOG_INFO, SuspendDialogInfo.class);
        this.mOnUnsuspend = (IntentSender) intent.getParcelableExtra(EXTRA_UNSUSPEND_INTENT, IntentSender.class);
        if (isDigitalWellbingPackage(this.mSuspendingPackage) && (getResources().getConfiguration().uiMode & 32) != 0) {
            setTheme(16974545);
        }
        if (this.mSuppliedDialogInfo != null) {
            try {
                this.mSuspendingAppResources = createContextAsUser(UserHandle.of(this.mSuspendingUserId), 0).getPackageManager().getResourcesForApplication(this.mSuspendingPackage);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.e(TAG, "Could not find resources for " + this.mSuspendingPackage, e);
            }
        }
        SuspendDialogInfo suspendDialogInfo = this.mSuppliedDialogInfo;
        int neutralButtonAction = suspendDialogInfo != null ? suspendDialogInfo.getNeutralButtonAction() : 0;
        this.mNeutralButtonAction = neutralButtonAction;
        this.mMoreDetailsIntent = neutralButtonAction == 0 ? getMoreDetailsActivity() : null;
        AlertController.AlertParams alertParams = this.mAlertParams;
        alertParams.mIcon = resolveIcon();
        alertParams.mTitle = resolveTitle();
        alertParams.mMessage = resolveDialogMessage();
        alertParams.mPositiveButtonText = getString(17039370);
        alertParams.mNeutralButtonText = resolveNeutralButtonText();
        alertParams.mNeutralButtonListener = this;
        alertParams.mPositiveButtonListener = this;
        getWindow().setGravity(80);
        requestDismissKeyguardIfNeeded(alertParams.mMessage);
        setupAlert();
        registerReceiverAsUser(this.mSuspendModifiedReceiver, UserHandle.of(this.mUserId), new IntentFilter(Intent.ACTION_PACKAGES_SUSPENSION_CHANGED), null, null);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mSuspendModifiedReceiver);
    }

    private void requestDismissKeyguardIfNeeded(CharSequence charSequence) {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class);
        if (keyguardManager.isKeyguardLocked()) {
            keyguardManager.requestDismissKeyguard(this, charSequence, new KeyguardManager.KeyguardDismissCallback() { // from class: com.android.internal.app.SuspendedAppActivity.2
                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissError() {
                    Slog.e(SuspendedAppActivity.TAG, "Error while dismissing keyguard. Keeping the dialog visible.");
                }

                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissCancelled() {
                    Slog.w(SuspendedAppActivity.TAG, "Keyguard dismiss was cancelled. Finishing.");
                    SuspendedAppActivity.this.finish();
                }
            });
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        SuspendedAppActivity suspendedAppActivity;
        RemoteException remoteException;
        if (i == -3) {
            int i2 = this.mNeutralButtonAction;
            if (i2 == 0) {
                suspendedAppActivity = this;
                Intent intent = suspendedAppActivity.mMoreDetailsIntent;
                if (intent != null) {
                    suspendedAppActivity.startActivityAsUser(intent, suspendedAppActivity.mOptions, UserHandle.of(suspendedAppActivity.mSuspendingUserId));
                } else {
                    Slog.wtf(TAG, "Neutral button should not have existed!");
                }
            } else if (i2 == 1) {
                IPackageManager packageManager = AppGlobals.getPackageManager();
                try {
                    String[] strArr = {this.mSuspendedPackage};
                    String str = this.mSuspendingPackage;
                    int i3 = this.mUserId;
                    if (ArrayUtils.contains(packageManager.setPackagesSuspendedAsUser(strArr, false, null, null, null, 0, str, i3, i3), this.mSuspendedPackage)) {
                        try {
                            Slog.e(TAG, "Could not unsuspend " + this.mSuspendedPackage);
                        } catch (RemoteException e) {
                            remoteException = e;
                            suspendedAppActivity = this;
                            Slog.e(TAG, "Can't talk to system process", remoteException);
                            suspendedAppActivity.mUsm.reportUserInteraction(suspendedAppActivity.mSuspendingPackage, suspendedAppActivity.mUserId);
                            suspendedAppActivity.finish();
                        }
                    } else {
                        sendBroadcastAsUser(new Intent().setAction(Intent.ACTION_PACKAGE_UNSUSPENDED_MANUALLY).putExtra("android.intent.extra.PACKAGE_NAME", this.mSuspendedPackage).setPackage(this.mSuspendingPackage).addFlags(16777216), UserHandle.of(this.mSuspendingUserId));
                        if (this.mOnUnsuspend != null) {
                            try {
                                suspendedAppActivity = this;
                                try {
                                    this.mOnUnsuspend.sendIntent(suspendedAppActivity, 0, (Intent) null, (String) null, ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle(), (Executor) null, (IntentSender.OnFinished) null);
                                } catch (IntentSender.SendIntentException e2) {
                                    e = e2;
                                    Slog.e(TAG, "Error while starting intent " + suspendedAppActivity.mOnUnsuspend, e);
                                    suspendedAppActivity.mUsm.reportUserInteraction(suspendedAppActivity.mSuspendingPackage, suspendedAppActivity.mUserId);
                                    suspendedAppActivity.finish();
                                }
                            } catch (IntentSender.SendIntentException e3) {
                                e = e3;
                                suspendedAppActivity = this;
                            }
                        }
                    }
                } catch (RemoteException e4) {
                    suspendedAppActivity = this;
                    remoteException = e4;
                }
            } else {
                Slog.e(TAG, "Unexpected action on neutral button: " + this.mNeutralButtonAction);
            }
            suspendedAppActivity.mUsm.reportUserInteraction(suspendedAppActivity.mSuspendingPackage, suspendedAppActivity.mUserId);
            suspendedAppActivity.finish();
        }
        suspendedAppActivity = this;
        suspendedAppActivity.mUsm.reportUserInteraction(suspendedAppActivity.mSuspendingPackage, suspendedAppActivity.mUserId);
        suspendedAppActivity.finish();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        finish();
    }

    public static Intent createSuspendedAppInterceptIntent(String str, UserPackage userPackage, SuspendDialogInfo suspendDialogInfo, Bundle bundle, IntentSender intentSender, int i) {
        if (userPackage != null && isDigitalWellbingPackage(userPackage.packageName)) {
            Intent flags = new Intent().setClassName("android", SuspendedAppActivity.class.getName()).putExtra(EXTRA_SUSPENDED_PACKAGE, str).putExtra(EXTRA_DIALOG_INFO, suspendDialogInfo).putExtra(EXTRA_SUSPENDING_PACKAGE, userPackage.packageName).putExtra(EXTRA_UNSUSPEND_INTENT, intentSender).putExtra(EXTRA_ACTIVITY_OPTIONS, bundle).putExtra("android.intent.extra.USER_ID", i).setFlags(276889600);
            if (Flags.crossUserSuspensionEnabledRo()) {
                flags.putExtra(EXTRA_SUSPENDING_USER, userPackage.userId);
            }
            return flags;
        }
        Intent flags2 = new Intent().setClassName("android", SuspendedAppActivity.class.getName()).putExtra(EXTRA_SUSPENDED_PACKAGE, str).putExtra(EXTRA_DIALOG_INFO, suspendDialogInfo).putExtra(EXTRA_SUSPENDING_PACKAGE, userPackage != null ? userPackage.packageName : null).putExtra(EXTRA_UNSUSPEND_INTENT, intentSender).putExtra(EXTRA_ACTIVITY_OPTIONS, bundle).putExtra("android.intent.extra.USER_ID", i).setFlags(276824064);
        if (Flags.crossUserSuspensionEnabledRo() && userPackage != null) {
            flags2.putExtra(EXTRA_SUSPENDING_USER, userPackage.userId);
        }
        return flags2;
    }

    private static boolean isDigitalWellbingPackage(String str) {
        return DIGITAL_WELLBEING_PACKAGE.equals(str);
    }
}
