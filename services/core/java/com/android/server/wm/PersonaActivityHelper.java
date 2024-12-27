package com.android.server.wm;

import android.R;
import android.app.KeyguardManager;
import android.app.TaskStackListener;
import android.app.trust.ITrustManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.ArgumentPlaceholder;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.knox.KnoxAnalyticsContainer;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.UserManagerService;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.ArrayList;
import java.util.List;

public final class PersonaActivityHelper {
    public final Context mContext;
    public KeyguardManager mKeyguardManager;
    public int mLastPIPModeActivityUserId;
    public final PersonaActivityHandler mPersonaActivityHandler;
    public PersonaManagerService mPersonaManager;
    public RootWindowContainer mRootWindowContainer;
    public SemPersonaManager mSemPersonaManager;
    public final ActivityTaskManagerService mService;
    public UserManagerService mUserManager;
    public SemDesktopModeManager mDesktopModeManager = null;
    public ActivityRecord mLastReceivedResumedActivity = null;
    public ActivityRecord mLastResumedSFActivity = null;
    public List mLockSecureFolderExceptionalList = null;

    public final class PersonaActivityHandler extends Handler {
        public PersonaActivityHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case EndpointMonitorConst.TRACE_EVENT_APP_BINDING /* 601 */:
                    Context context = PersonaActivityHelper.this.mContext;
                    Toast.makeText(
                                    context,
                                    context.getText(R.string.permdesc_nearby_wifi_devices)
                                            .toString(),
                                    0)
                            .show();
                    return;
                case EndpointMonitorConst.TRACE_EVENT_APP_DYING /* 602 */:
                    WindowManagerGlobalLock windowManagerGlobalLock =
                            PersonaActivityHelper.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            RecentTasks recentTasks =
                                    PersonaActivityHelper.this.mService.mRecentTasks;
                            for (int size = recentTasks.mTasks.size() - 1; size >= 0; size--) {
                                Task task = (Task) recentTasks.mTasks.get(size);
                                if (SemPersonaManager.isSecureFolderId(task.mUserId)
                                        && recentTasks.isVisibleRecentTask(task)) {
                                    recentTasks.mTasks.remove(size);
                                    recentTasks.notifyTaskRemoved(task, true, true);
                                }
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case FrameworkStatsLog.AUTOFILL_UI_EVENT_REPORTED /* 603 */:
                    int i = message.arg1;
                    Bundle bundle = (Bundle) message.obj;
                    boolean z = bundle.getBoolean("isHomeActivity");
                    String string = bundle.getString("componentName");
                    Intent intent =
                            new Intent("com.samsung.android.knox.container.LOCK_SECUREFOLDER");
                    intent.putExtra("userid", i);
                    intent.putExtra("isHomeActivity", z);
                    intent.putExtra("componentName", string);
                    intent.setPackage("com.samsung.android.knox.containercore");
                    PersonaActivityHelper.this.mContext.sendBroadcastAsUser(
                            intent, UserHandle.OWNER);
                    return;
                case FrameworkStatsLog.AUTOFILL_FILL_REQUEST_REPORTED /* 604 */:
                    int i2 = message.arg1;
                    try {
                        ITrustManager asInterface =
                                ITrustManager.Stub.asInterface(ServiceManager.getService("trust"));
                        if (asInterface != null) {
                            asInterface.setDeviceLockedForUser(i2, true);
                        } else {
                            Log.d(
                                    "PersonaActivityHandler",
                                    "device lock failed. trustmanager may be null.");
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case FrameworkStatsLog.AUTOFILL_FILL_RESPONSE_REPORTED /* 605 */:
                    ActivityRecord activityRecord = (ActivityRecord) message.obj;
                    PersonaManagerService personaManagerService =
                            PersonaActivityHelper.this.mPersonaManager;
                    int i3 = activityRecord.mUserId;
                    synchronized (personaManagerService.mFocusLock) {
                        try {
                            if (PersonaManagerService.DEBUG) {
                                Log.d(
                                        "PersonaManagerService",
                                        "Current focused persona service handled id set to : "
                                                + personaManagerService.mFocusedUserId);
                            }
                            personaManagerService.mFocusedUserId = i3;
                        } finally {
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: -$$Nest$mcheckAndLockSecureFolder, reason: not valid java name */
    public static void m1067$$Nest$mcheckAndLockSecureFolder(
            PersonaActivityHelper personaActivityHelper, ActivityRecord activityRecord) {
        personaActivityHelper.getClass();
        ActivityRecord activityRecord2 = activityRecord != null ? activityRecord.resultTo : null;
        if ((activityRecord2 == null
                        || !SemPersonaManager.isSecureFolderId(activityRecord2.mUserId)
                        || activityRecord2.mUserId == activityRecord.mUserId)
                && activityRecord != null) {
            if (activityRecord.mState != ActivityRecord.State.RESUMED) {
                Log.d(
                        "PersonaActivityHelper",
                        "Activity not in resumed state, do not run immediate lock");
                return;
            }
            int secureFolderId =
                    SemPersonaManager.getSecureFolderId(personaActivityHelper.mContext);
            if (secureFolderId > 0 && !SemPersonaManager.isSecureFolderId(activityRecord.mUserId)) {
                try {
                    if (Settings.System.getIntForUser(
                                    personaActivityHelper.mContext.getContentResolver(),
                                    "knox_screen_off_timeout",
                                    -1,
                                    secureFolderId)
                            != -2) {
                        return;
                    }
                    try {
                        if (personaActivityHelper.mKeyguardManager == null) {
                            personaActivityHelper.mKeyguardManager =
                                    (KeyguardManager)
                                            personaActivityHelper.mContext.getSystemService(
                                                    "keyguard");
                        }
                        KeyguardManager keyguardManager = personaActivityHelper.mKeyguardManager;
                        if (keyguardManager != null) {
                            if (keyguardManager == null) {
                                personaActivityHelper.mKeyguardManager =
                                        (KeyguardManager)
                                                personaActivityHelper.mContext.getSystemService(
                                                        "keyguard");
                            }
                            if (personaActivityHelper.mKeyguardManager.isDeviceLocked(
                                    secureFolderId)) {
                                return;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!SystemProperties.getBoolean(
                                    "persist.sys.knox.secure_folder_state_available", true)
                            || personaActivityHelper.isLockSecureFolderExceptionalCase(
                                    activityRecord.shortComponentName)) {
                        return;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            PersonaActivityHandler personaActivityHandler =
                    personaActivityHelper.mPersonaActivityHandler;
            personaActivityHandler.removeMessages(FrameworkStatsLog.AUTOFILL_UI_EVENT_REPORTED);
            Message obtainMessage =
                    personaActivityHandler.obtainMessage(
                            FrameworkStatsLog.AUTOFILL_UI_EVENT_REPORTED);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isHomeActivity", activityRecord.isActivityTypeHome());
            bundle.putString("componentName", activityRecord.shortComponentName);
            obtainMessage.obj = bundle;
            obtainMessage.arg1 = activityRecord.mUserId;
            personaActivityHandler.sendMessage(obtainMessage);
        }
    }

    public PersonaActivityHelper(
            ActivityTaskManagerService activityTaskManagerService, Looper looper) {
        this.mService = activityTaskManagerService;
        Context context = activityTaskManagerService.mContext;
        this.mContext = context;
        context.getPackageManager();
        this.mUserManager = UserManagerService.getInstance();
        this.mPersonaActivityHandler = new PersonaActivityHandler(looper);
        activityTaskManagerService.registerTaskStackListener(
                new TaskStackListener() { // from class:
                                          // com.android.server.wm.PersonaActivityHelper.1
                    public final void onActivityPinned(String str, int i, int i2, int i3) {
                        PersonaActivityHelper.this.mLastPIPModeActivityUserId = i;
                    }

                    public final void onActivityUnpinned() {
                        if (SemPersonaManager.isSecureFolderId(
                                PersonaActivityHelper.this.mLastPIPModeActivityUserId)) {
                            PersonaActivityHelper personaActivityHelper =
                                    PersonaActivityHelper.this;
                            PersonaActivityHelper.m1067$$Nest$mcheckAndLockSecureFolder(
                                    personaActivityHelper,
                                    personaActivityHelper.mLastReceivedResumedActivity);
                        }
                    }
                });
        activityTaskManagerService.mWindowOrganizerController.mTransitionController
                .registerLegacyListener(
                        new WindowManagerInternal
                                .AppTransitionListener() { // from class:
                                                           // com.android.server.wm.PersonaActivityHelper.2
                            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
                            public final void onAppTransitionFinishedLocked(IBinder iBinder) {
                                PersonaActivityHelper personaActivityHelper =
                                        PersonaActivityHelper.this;
                                if (personaActivityHelper.mRootWindowContainer != null) {
                                    ActivityRecord forTokenLocked =
                                            ActivityRecord.forTokenLocked(iBinder);
                                    ActivityRecord activityRecord =
                                            personaActivityHelper.mLastResumedSFActivity;
                                    if (activityRecord == null || activityRecord.info == null) {
                                        return;
                                    }
                                    Log.d(
                                            "PersonaActivityHelper",
                                            "token.toString()  " + iBinder.toString());
                                    Log.d(
                                            "PersonaActivityHelper",
                                            "mLastResumedSFActivity getPackageName  "
                                                    + personaActivityHelper
                                                            .mLastResumedSFActivity
                                                            .info
                                                            .getComponentName()
                                                            .getPackageName());
                                    Log.d(
                                            "PersonaActivityHelper",
                                            "mLastResumedSFActivity getShortClassName  "
                                                    + personaActivityHelper
                                                            .mLastResumedSFActivity
                                                            .info
                                                            .getComponentName()
                                                            .getShortClassName());
                                    if (forTokenLocked == null) {
                                        if (iBinder.toString()
                                                        .contains(
                                                                personaActivityHelper
                                                                        .mLastResumedSFActivity
                                                                        .info
                                                                        .getComponentName()
                                                                        .getPackageName())
                                                && iBinder.toString()
                                                        .contains(
                                                                personaActivityHelper
                                                                        .mLastResumedSFActivity
                                                                        .info
                                                                        .getComponentName()
                                                                        .getShortClassName())) {
                                            PersonaActivityHelper
                                                    .m1067$$Nest$mcheckAndLockSecureFolder(
                                                            personaActivityHelper,
                                                            personaActivityHelper
                                                                    .mLastReceivedResumedActivity);
                                            return;
                                        }
                                        return;
                                    }
                                    if (forTokenLocked == null
                                            || forTokenLocked.info == null
                                            || !personaActivityHelper
                                                    .mLastResumedSFActivity
                                                    .info
                                                    .getComponentName()
                                                    .equals(forTokenLocked.info.getComponentName())
                                            || forTokenLocked.mVisible) {
                                        return;
                                    }
                                    PersonaActivityHelper.m1067$$Nest$mcheckAndLockSecureFolder(
                                            personaActivityHelper,
                                            personaActivityHelper.mLastReceivedResumedActivity);
                                }
                            }
                        });
    }

    public final boolean checkKnoxFeatureEnabled() {
        String str = SystemProperties.get("persist.sys.knox.userinfo");
        boolean z = (str == null || str.length() == 0) ? false : true;
        PersonaManagerService personaManagerService = this.mPersonaManager;
        if (personaManagerService == null) {
            if (personaManagerService == null) {
                this.mPersonaManager =
                        ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
            }
            this.mPersonaManager = this.mPersonaManager;
        }
        if (this.mPersonaManager != null && z) {
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (SemPersonaManager.isDoEnabled(0)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final UserHandle getCurrentScreenUserId(UserHandle userHandle) {
        if (checkKnoxFeatureEnabled()
                && "2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
            if (this.mSemPersonaManager == null) {
                this.mSemPersonaManager =
                        (SemPersonaManager) this.mContext.getSystemService("persona");
            }
            int focusedKnoxId = this.mSemPersonaManager.getFocusedKnoxId();
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    focusedKnoxId, "screenshot: check focused user : ", "PersonaActivityHelper");
            if (this.mUserManager == null) {
                this.mUserManager = UserManagerService.getInstance();
            }
            UserInfo userInfo = this.mUserManager.getUserInfo(focusedKnoxId);
            if (userInfo != null
                    && (userInfo.isManagedProfile()
                            || SemPersonaManager.isSecureFolderId(focusedKnoxId))) {
                UserHandle userHandle2 = new UserHandle(focusedKnoxId);
                Log.d(
                        "PersonaActivityHelper",
                        "screenshot: getCurrentScreenUserId:premium = " + userHandle2);
                return userHandle2;
            }
            if (userInfo != null && userInfo.isUserTypeAppSeparation()) {
                UserHandle userHandle3 = new UserHandle(focusedKnoxId);
                Log.d(
                        "PersonaActivityHelper",
                        "screenshot: App Separation user type. ID = " + userHandle3);
                return userHandle3;
            }
            Log.d("PersonaActivityHelper", "screenshot: getCurrentScreenUserId = " + userHandle);
        }
        return userHandle;
    }

    public final boolean isKnoxWindowVisibleLocked(int i, int i2) {
        if (!checkKnoxFeatureEnabled() || this.mRootWindowContainer == null) {
            return false;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (int childCount = this.mRootWindowContainer.getChildCount() - 1;
                        childCount >= 0;
                        childCount--) {
                    DisplayContent displayContent =
                            (DisplayContent) this.mRootWindowContainer.getChildAt(childCount);
                    if (displayContent != null) {
                        PersonaActivityHelper$$ExternalSyntheticLambda0
                                personaActivityHelper$$ExternalSyntheticLambda0 =
                                        new PersonaActivityHelper$$ExternalSyntheticLambda0();
                        ArgumentPlaceholder __ = PooledLambda.__(Task.class);
                        Boolean bool = Boolean.FALSE;
                        PooledPredicate obtainPredicate =
                                PooledLambda.obtainPredicate(
                                        personaActivityHelper$$ExternalSyntheticLambda0,
                                        __,
                                        bool,
                                        Integer.valueOf(i),
                                        bool,
                                        Integer.valueOf(i2));
                        Task task = displayContent.getTask(obtainPredicate, true);
                        obtainPredicate.recycle();
                        if (task != null) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return false;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean isLockSecureFolderExceptionalCase(String str) {
        if (this.mLockSecureFolderExceptionalList == null) {
            ArrayList arrayList = new ArrayList();
            this.mLockSecureFolderExceptionalList = arrayList;
            arrayList.add("com.android.systemui/.keyguard.WorkLockActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("android/com.android.internal.app.ForwardIntentToManagedProfile");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("android/com.android.internal.app.ForwardIntentToManagedProfile4");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.android.intentresolver/.ChooserActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.android.intentresolver/.ChooserActivityLauncher");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add(
                            "com.samsung.android.allshare.service.fileshare/.client.DeviceSelectActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.sec.android.app.myfiles/.external.ui.PermissionActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add(
                            "com.google.android.permissioncontroller/com.android.permissioncontroller.permission.ui.GrantPermissionsActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add(
                            "com.google.android.permissioncontroller/com.android.permissioncontroller.permission.ui.ManagePermissionsActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add(
                            "com.google.android.permissioncontroller/com.android.permissioncontroller.role.ui.RequestRoleActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.samsung.android.messaging/.ui.view.main.WithActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.samsung.android.messaging/.ui.view.recipientspicker.PickerActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add(
                            "com.samsung.android.messaging/.ui.view.firstlaunch.GoogleFirstLaunchActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.google.android.gms/.constellation.ui.ApiConsentActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.samsung.android.messaging/com.android.mms.ui.ConversationComposer");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add(
                            "com.samsung.android.messaging/.ui.view.permission.CheckDefaultSmsAppsActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add(
                            "com.samsung.android.biometrics.app.setting/.fingerprint.enroll.FingerprintEnrollActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.samsung.android.settings.notification.SecRedactionInterstitial");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add(
                            "com.wssyncmldm/com.idm.fotaagent.enabler.ui.downloadconfirm.DownloadConfirmActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.sec.android.app.camera/.QrScannerActivity");
            ((ArrayList) this.mLockSecureFolderExceptionalList)
                    .add("com.sec.android.app.camera/.QrScanner");
            if (this.mDesktopModeManager == null) {
                this.mDesktopModeManager =
                        (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
            }
            SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
            if (semDesktopModeManager != null) {
                if (semDesktopModeManager == null) {
                    this.mDesktopModeManager =
                            (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
                }
                int enabled = this.mDesktopModeManager.getDesktopModeState().getEnabled();
                if ((enabled == 4 || enabled == 3)
                        && Settings.System.getIntForUser(
                                        this.mContext.getContentResolver(),
                                        "dexonpc_connection_state",
                                        0,
                                        0)
                                == 3) {
                    Log.d("PersonaActivityHelper", "DeX on PC is running");
                    ((ArrayList) this.mLockSecureFolderExceptionalList)
                            .add(
                                    "com.samsung.knox.securefolder/.presentation.switcher.view.SecureFolderShortcutActivity");
                }
            }
        }
        return ((ArrayList) this.mLockSecureFolderExceptionalList).contains(str)
                || str.startsWith("com.android.settings/");
    }

    public final boolean isQuickSwitchExceptionalCase(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock;
        try {
            windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception :: "), "PersonaActivityHelper");
        }
        synchronized (windowManagerGlobalLock) {
            try {
                Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i);
                if (anyTaskForId == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                ActivityRecord topNonFinishingActivity =
                        anyTaskForId.getTopNonFinishingActivity(true, true);
                if (topNonFinishingActivity == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                Intent intent = topNonFinishingActivity.intent;
                if (intent == null
                        || !SemPersonaManager.isKnoxId(
                                intent.getIntExtra("android.intent.extra.USER_ID", 0))) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return true;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void notifyActivityResumedLocked(boolean z, ActivityRecord activityRecord) {
        if (checkKnoxFeatureEnabled() || SemPersonaManager.isDoEnabled(0)) {
            ActivityRecord.State state = activityRecord.mState;
            final int i = activityRecord.mUserId;
            final ComponentName componentName = activityRecord.info.getComponentName();
            activityRecord.getRootTask().getActivityType();
            activityRecord.getRootTask().getWindowingMode();
            activityRecord.occludesParent(false);
            final boolean z2 = z && (activityRecord.nowVisible || activityRecord.mVisible);
            boolean canShowWhenLocked = activityRecord.canShowWhenLocked();
            if (!canShowWhenLocked) {
                canShowWhenLocked =
                        activityRecord.packageName.equals("com.samsung.android.incallui")
                                || activityRecord.packageName.equals("com.android.server.telecom")
                                || activityRecord.shortComponentName.equals(
                                        "com.android.calendar/.alerts.PopUpActivity")
                                || activityRecord.shortComponentName.equals(
                                        "com.samsung.android.calendar/com.samsung.android.app.calendarnotification.AlertPopupActivity")
                                || activityRecord.shortComponentName.equals(
                                        "com.microsoft.teams/com.microsoft.skype.teams.views.activities.PreCallActivity")
                                || activityRecord.shortComponentName.equals(
                                        "com.microsoft.teams/com.microsoft.skype.teams.views.activities.InCallActivity");
            }
            activityRecord.isDexMode();
            if (state == ActivityRecord.State.RESUMED) {
                PersonaManagerService personaManagerService = this.mPersonaManager;
                if (canShowWhenLocked) {
                    SemDualAppManager.isDualAppId(i);
                }
                final KnoxAnalyticsContainer knoxAnalyticsContainer =
                        personaManagerService.mKnoxAnalyticsContainer;
                knoxAnalyticsContainer.getClass();
                knoxAnalyticsContainer.analyticsHandler.post(
                        new Runnable() { // from class:
                            // com.android.server.knox.KnoxAnalyticsContainer.1
                            public final /* synthetic */ ComponentName val$component;
                            public final /* synthetic */ boolean val$isVisible;
                            public final /* synthetic */ int val$userId;

                            public AnonymousClass1(
                                    final int i2,
                                    final ComponentName componentName2,
                                    final boolean z22) {
                                r2 = i2;
                                r3 = componentName2;
                                r4 = z22;
                            }

                            /* JADX WARN: Code restructure failed: missing block: B:11:0x0032, code lost:

                               if (com.samsung.android.knox.SemPersonaManager.isDoEnabled(r2) != false) goto L60;
                            */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void run() {
                                /*
                                    Method dump skipped, instructions count: 330
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.knox.KnoxAnalyticsContainer.AnonymousClass1.run():void");
                            }
                        });
            }
        }
    }
}
