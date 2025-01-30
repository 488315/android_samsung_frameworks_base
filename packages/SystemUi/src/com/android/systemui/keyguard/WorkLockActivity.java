package com.android.systemui.keyguard;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class WorkLockActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public KeyguardManager mKgm;
    public final PackageManager mPackageManager;
    public final UserManager mUserManager;
    public WorkLockActivityHelper workLockActivityHelper = null;
    public final WorkLockActivity$$ExternalSyntheticLambda0 mBackCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.keyguard.WorkLockActivity$$ExternalSyntheticLambda0
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            WorkLockActivity workLockActivity = WorkLockActivity.this;
            int i = WorkLockActivity.$r8$clinit;
            workLockActivity.getClass();
        }
    };
    public final C15181 mLockEventReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.WorkLockActivity.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int targetUserId = WorkLockActivity.this.getTargetUserId();
            if (intent.getIntExtra("android.intent.extra.user_handle", targetUserId) != targetUserId || WorkLockActivity.this.getKeyguardManager().isDeviceLocked(targetUserId)) {
                return;
            }
            WorkLockActivity.this.finish();
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.keyguard.WorkLockActivity$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.keyguard.WorkLockActivity$1] */
    public WorkLockActivity(BroadcastDispatcher broadcastDispatcher, UserManager userManager, PackageManager packageManager) {
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserManager = userManager;
        this.mPackageManager = packageManager;
    }

    public Drawable getBadgedIcon() {
        String stringExtra = getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME");
        if (stringExtra.isEmpty()) {
            return null;
        }
        try {
            UserManager userManager = this.mUserManager;
            PackageManager packageManager = this.mPackageManager;
            return userManager.getBadgedIconForUser(packageManager.getApplicationIcon(packageManager.getApplicationInfoAsUser(stringExtra, PackageManager.ApplicationInfoFlags.of(0L), getTargetUserId())), UserHandle.of(getTargetUserId()));
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public final KeyguardManager getKeyguardManager() {
        if (this.mKgm == null) {
            this.mKgm = (KeyguardManager) getSystemService("keyguard");
        }
        return this.mKgm;
    }

    public final int getTargetUserId() {
        return getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1 || i2 == -1) {
            return;
        }
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.addCategory("android.intent.category.HOME");
        intent2.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        if (getDisplay().getDisplayId() != 0) {
            intent2.putExtra("DesktopModeService", true);
            intent2.setPackage("com.sec.android.app.desktoplauncher");
        }
        startActivity(intent2);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.workLockActivityHelper = new WorkLockActivityHelper(this, this, getTargetUserId());
        this.mBroadcastDispatcher.registerReceiver(this.mLockEventReceiver, new IntentFilter("android.intent.action.DEVICE_LOCKED_CHANGED"), null, UserHandle.ALL);
        if (!getKeyguardManager().isDeviceLocked(getTargetUserId())) {
            finish();
            return;
        }
        final WorkLockActivityHelper workLockActivityHelper = this.workLockActivityHelper;
        if (workLockActivityHelper != null) {
            Context context = workLockActivityHelper.mContext;
            Activity activity = workLockActivityHelper.mActivity;
            activity.setContentView(R.layout.switcher_workwindow_lock);
            workLockActivityHelper.mwLockScreen = (RelativeLayout) activity.findViewById(R.id.switcher_workwindow_lock_screen);
            TextView textView = (TextView) activity.findViewById(R.id.switcher_unlock_workwindow);
            textView.setText("");
            try {
                String stringExtra = activity.getIntent().getStringExtra("componentName");
                int i = workLockActivityHelper.mUserId;
                ComponentName unflattenFromString = (stringExtra == null && SemPersonaManager.isSecureFolderId(i)) ? ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode() ? ComponentName.unflattenFromString("com.samsung.knox.securefolder/.launcher.view.LauncherActivityForDex") : ComponentName.unflattenFromString("com.samsung.knox.securefolder/.launcher.view.LauncherActivity") : ComponentName.unflattenFromString(stringExtra);
                PackageManager packageManager = activity.getPackageManager();
                textView.setText(String.format(context.getString(R.string.unlock_workwindow_appname), AppGlobals.getPackageManager().getActivityInfo(unflattenFromString, 0L, i).loadLabel(packageManager).toString()));
                ((ImageView) activity.findViewById(R.id.switcher_pkgIcon)).setImageDrawable(packageManager.getUserBadgedIcon(activity.getApplicationContext().createPackageContextAsUser(unflattenFromString.getPackageName(), 0, new UserHandle(i)).getApplicationInfo().loadIcon(packageManager), new UserHandle(i)));
            } catch (Exception e) {
                android.util.Log.e("WorkLockActivityHelper", "Failed to load icon and label", e);
            }
            View view = new View(context);
            workLockActivityHelper.blankView = view;
            view.setBackgroundColor(EmergencyPhoneWidget.BG_COLOR);
            if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode()) {
                workLockActivityHelper.mwLockScreen.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.keyguard.WorkLockActivityHelper.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        ((WorkLockActivity) WorkLockActivityHelper.this.mActivity).showConfirmCredentialActivity();
                    }
                });
                workLockActivityHelper.setContentblank(false);
            } else if (activity.isInMultiWindowMode()) {
                workLockActivityHelper.setContentblank(false);
            } else {
                workLockActivityHelper.setContentblank(true);
            }
        } else {
            setOverlayWithDecorCaptionEnabled(true);
            setContentView(R.layout.auth_biometric_background);
            Drawable badgedIcon = getBadgedIcon();
            if (badgedIcon != null) {
                ((ImageView) findViewById(R.id.icon)).setImageDrawable(badgedIcon);
            }
        }
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mBackCallback);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        unregisterBroadcastReceiver();
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mBackCallback);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onResume() {
        WorkLockActivityHelper workLockActivityHelper = this.workLockActivityHelper;
        if (workLockActivityHelper != null) {
            workLockActivityHelper.getClass();
            if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode()) {
                workLockActivityHelper.setContentblank(false);
            } else {
                Activity activity = workLockActivityHelper.mActivity;
                if (activity.isInMultiWindowMode() && workLockActivityHelper.isblankView) {
                    workLockActivityHelper.setContentblank(false);
                } else if (!activity.isInMultiWindowMode() && !workLockActivityHelper.isblankView) {
                    workLockActivityHelper.setContentblank(true);
                }
            }
        }
        super.onResume();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode()) {
            android.util.Log.d("WorkLockActivity", "onWindowFocusChanged: returning without starting cdc");
        } else if (z) {
            showConfirmCredentialActivity();
        }
    }

    public final void showConfirmCredentialActivity() {
        Intent createConfirmDeviceCredentialIntent;
        if (isFinishing() || !getKeyguardManager().isDeviceLocked(getTargetUserId()) || (createConfirmDeviceCredentialIntent = getKeyguardManager().createConfirmDeviceCredentialIntent(null, null, getTargetUserId(), true)) == null) {
            return;
        }
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchTaskId(getTaskId());
        PendingIntent activity = PendingIntent.getActivity(this, -1, getIntent(), 1409286144, makeBasic.toBundle());
        if (activity != null) {
            createConfirmDeviceCredentialIntent.putExtra("android.intent.extra.INTENT", activity.getIntentSender());
        }
        int targetUserId = getTargetUserId();
        boolean z = false;
        try {
            IPasswordPolicy asInterface = IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy"));
            if (asInterface != null) {
                if (asInterface.isChangeRequestedAsUser(targetUserId) > 0) {
                    z = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (z) {
            createConfirmDeviceCredentialIntent.setFlags(276840448);
            startActivity(createConfirmDeviceCredentialIntent);
            return;
        }
        ActivityOptions makeBasic2 = ActivityOptions.makeBasic();
        makeBasic2.setLaunchTaskId(getTaskId());
        makeBasic2.setTaskOverlay(true, true);
        createConfirmDeviceCredentialIntent.putExtra("android.app.KeyguardManager.FORCE_TASK_OVERLAY", true);
        createConfirmDeviceCredentialIntent.putExtra("knox.container.proxy.EXTRA_TASK_ID", getTaskId());
        startActivityForResult(createConfirmDeviceCredentialIntent, 1, makeBasic2.toBundle());
    }

    public void unregisterBroadcastReceiver() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mLockEventReceiver);
    }

    @Override // android.app.Activity
    public final void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
    }
}
