package com.android.server.multicontrol;

import android.R;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.IInputFilter;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.input.InputManagerService;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.multicontrol.IInputFilterInstallListener;
import com.samsung.android.multicontrol.IMultiControlDeathChecker;
import com.samsung.android.multicontrol.IMultiControlManager;
import com.samsung.android.multicontrol.MCTriggerManager;
import com.samsung.android.multicontrol.MultiControlManagerInternal;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MultiControlManagerService extends IMultiControlManager.Stub {
    public static final boolean DEBUG;
    public MultiControlAppDeathChecker mAppDeathChecker;
    public final Context mContext;
    public IInputFilter mInputFilter;
    public final InputManagerService.LocalService mInputManagerInternal;
    public final ContentResolver mResolver;
    public ContentObserver mUserSetupCompleteObserver;
    public final WindowManagerInternal mWindowManagerService;
    public final MCTriggerManager mcTriggerManager;
    public boolean mIsBootComplete = false;
    public int mCurrentUserId = -10000;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public MultiControlManagerService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = null;
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            MultiControlManagerService multiControlManagerService = this.mService;
            if (multiControlManagerService != null) {
                boolean z = MultiControlManagerService.DEBUG;
                multiControlManagerService.getClass();
                if (MultiControlManagerService.DEBUG) {
                    BootReceiver$$ExternalSyntheticOutline0.m(i, "onBootPhase(", ")", "MultiControl@MultiControlManagerService");
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.multicontrol.MultiControlManagerService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? multiControlManagerService = new MultiControlManagerService(getContext());
            this.mService = multiControlManagerService;
            publishBinderService("multicontrol", multiControlManagerService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            MultiControlManagerService multiControlManagerService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            boolean z = MultiControlManagerService.DEBUG;
            multiControlManagerService.getClass();
            if (MultiControlManagerService.DEBUG) {
                Log.d("onStartUser(), userId=" + userIdentifier);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            MultiControlManagerService multiControlManagerService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            boolean z = MultiControlManagerService.DEBUG;
            multiControlManagerService.getClass();
            if (MultiControlManagerService.DEBUG) {
                Log.d("onCleanupUser(), userId=" + userIdentifier);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            MultiControlManagerService multiControlManagerService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            boolean z = MultiControlManagerService.DEBUG;
            multiControlManagerService.getClass();
            if (MultiControlManagerService.DEBUG) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(userIdentifier, "onStopUser(), userId=", ", CurrentUser=");
                m.append(ActivityManager.getCurrentUser());
                Log.d(m.toString());
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            MultiControlManagerService multiControlManagerService = this.mService;
            int userIdentifier = targetUser2.getUserIdentifier();
            if (MultiControlManagerService.DEBUG) {
                multiControlManagerService.getClass();
                Log.d("onSwitchUser(), userId=" + userIdentifier);
            }
            multiControlManagerService.onUserChanged(userIdentifier);
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            MultiControlManagerService multiControlManagerService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            if (MultiControlManagerService.DEBUG) {
                multiControlManagerService.getClass();
                Log.d("onUnlockUser(), userId=" + userIdentifier + ", CurrentUser=" + ActivityManager.getCurrentUser());
            }
            multiControlManagerService.mIsBootComplete = true;
            if (userIdentifier == ActivityManager.getCurrentUser()) {
                multiControlManagerService.onUserChanged(userIdentifier);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends MultiControlManagerInternal {
        public LocalService() {
        }

        public final boolean isMultiControlEnabled() {
            return MultiControlManagerService.this.mInputFilter != null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MultiControlAppDeathChecker implements IBinder.DeathRecipient {
        public final IBinder mBinder;

        public MultiControlAppDeathChecker(IMultiControlDeathChecker iMultiControlDeathChecker) {
            this.mBinder = iMultiControlDeathChecker.asBinder();
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean z = MultiControlManagerService.DEBUG;
            Log.i("MultiControlAppDeathChecker - binderDied");
            Log.i("in resetMultiControlValue");
            MultiControlManagerService.this.forceHideCursor(false);
            MultiControlManagerService.this.setMultiControlOutOfFocus(false);
            try {
                MultiControlManagerService multiControlManagerService = MultiControlManagerService.this;
                if (multiControlManagerService.mInputFilter != null) {
                    multiControlManagerService.resetInputFilter();
                }
            } catch (Exception unused) {
            }
            try {
                Intent intent = new Intent("com.samsung.android.inputshare.action.ACTION_MULTI_CONTROL_DIED");
                intent.setPackage("com.samsung.android.inputshare");
                MultiControlManagerService.this.mContext.sendBroadcast(intent, "com.samsung.android.permission.MULTI_CONTROL_RECEIVER_PERMISSION");
                boolean z2 = MultiControlManagerService.DEBUG;
                Log.i("sendBroadcast - ACTION_MULTI_CONTROL_DIED");
            } catch (Exception e) {
                boolean z3 = MultiControlManagerService.DEBUG;
                Log.save('E', "sendBroadcast - ACTION_MULTI_CONTROL_DIED");
                android.util.Log.e("MultiControl@MultiControlManagerService", "sendBroadcast - ACTION_MULTI_CONTROL_DIED", e);
            }
            this.mBinder.unlinkToDeath(this, 0);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean z = MultiControlManagerService.DEBUG;
            if (z) {
                Log.d("onReceive(), action=" + action);
            }
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                if (z) {
                    Log.d("Shutdown received with UserId: " + getSendingUserId());
                }
                if (getSendingUserId() == -1) {
                    MultiControlManagerService.this.mIsBootComplete = false;
                }
            }
        }
    }

    static {
        DEBUG = Debug.semIsProductDev() || android.util.Log.isLoggable("RAMS", 3);
    }

    public MultiControlManagerService(Context context) {
        ServiceThread m = Watchdog$$ExternalSyntheticOutline0.m(-2, "multicontrol", false);
        Handler handler = new Handler(m.getLooper());
        this.mWindowManagerService = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        this.mContext = context;
        context.setTheme(R.style.Theme.DeviceDefault.Light);
        this.mResolver = context.getContentResolver();
        this.mInputManagerInternal = (InputManagerService.LocalService) LocalServices.getService(InputManagerService.LocalService.class);
        this.mcTriggerManager = new MCTriggerManager(context, m.getLooper());
        context.registerReceiverAsUser(new Receiver(), UserHandle.ALL, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.PHONE_STATE", "android.intent.action.ACTION_SHUTDOWN"), null, handler);
        LocalServices.addService(MultiControlManagerInternal.class, new LocalService());
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "MultiControl@MultiControlManagerService", printWriter)) {
            if (strArr == null || strArr.length == 0 || "-a".equals(strArr[0])) {
                PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
                indentingPrintWriter.println("MultiControlManagerService (dumpsys multicontrol):");
                indentingPrintWriter.println(Log.buildLogString('V', "MultiControl@StateStart", "=========================================================================="));
                Log.sSavedStates.dump(indentingPrintWriter);
                indentingPrintWriter.println(Log.buildLogString('V', "MultiControl@StateEnd", "=========================================================================="));
                indentingPrintWriter.println(Log.buildLogString('V', "MultiControl@SavedLogsStart", "=========================================================================="));
                Log.sSavedLogs.dump(indentingPrintWriter);
                indentingPrintWriter.println(Log.buildLogString('V', "MultiControl@SavedLogsEnd", "=========================================================================="));
                indentingPrintWriter.println();
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.printPair("mCurrentUserId", Integer.valueOf(this.mCurrentUserId));
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("Configuration", this.mContext.getResources().getConfiguration());
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("DISPLAY_SIZE_FORCED", Settings.Global.getString(this.mResolver, "display_size_forced"));
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("DISPLAY_DENSITY_FORCED", Settings.Secure.getStringForUser(this.mResolver, "display_density_forced", 0));
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("SCREEN_OFF_TIMEOUT", Settings.System.getStringForUser(this.mResolver, "screen_off_timeout", this.mCurrentUserId));
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("SHOW_IME_WITH_HARD_KEYBOARD", Settings.Secure.getStringForUser(this.mResolver, "show_ime_with_hard_keyboard", this.mCurrentUserId));
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    public final void enableTriggerDetection(boolean z) {
        Log.i("[enableTriggerDetection] in " + z);
        try {
            MCTriggerManager mCTriggerManager = this.mcTriggerManager;
            if (mCTriggerManager != null) {
                mCTriggerManager.enable(z);
            }
        } catch (Exception e) {
            Log.save('E', "[enableTriggerDetection]");
            android.util.Log.e("MultiControl@MultiControlManagerService", "[enableTriggerDetection]", e);
        }
    }

    public final void forceHideCursor(boolean z) {
        try {
            if (this.mInputManagerInternal != null) {
                Log.i("in forceHideCursor");
                InputManagerService.this.mNative.forceHideCursor(z);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            Log.save('E', message);
            android.util.Log.e("MultiControl@MultiControlManagerService", message);
        }
    }

    public final int getProtocolVersion() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "getProtocolVersion");
        return 1;
    }

    public final void initializeStates() {
        if (isUserSetupComplete()) {
            return;
        }
        if (this.mUserSetupCompleteObserver == null) {
            this.mUserSetupCompleteObserver = new ContentObserver() { // from class: com.android.server.multicontrol.MultiControlManagerService.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    super.onChange(z);
                    MultiControlManagerService.this.initializeStates();
                    MultiControlManagerService.this.mResolver.unregisterContentObserver(this);
                    MultiControlManagerService.this.mUserSetupCompleteObserver = null;
                }
            };
        }
        this.mResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this.mUserSetupCompleteObserver, this.mCurrentUserId);
    }

    public final boolean isAllowed() {
        boolean z;
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "isAllowed");
        if (!this.mIsBootComplete || !isUserSetupComplete() || FactoryTest.isFactoryBinary() || this.mCurrentUserId == -10000) {
            Log.i("isSystemReady(), mIsBootComplete=" + this.mIsBootComplete + ", isFactoryBinary=" + FactoryTest.isFactoryBinary() + ", mCurrentUserId=" + this.mCurrentUserId);
            z = false;
        } else {
            z = true;
        }
        Log.d("isAllowed = " + z);
        return z;
    }

    public final boolean isUserSetupComplete() {
        boolean z = Settings.Secure.getIntForUser(this.mResolver, "user_setup_complete", 0, this.mCurrentUserId) != 0;
        if (!z && DEBUG) {
            Log.d("isUserSetupComplete()=false");
        }
        return z;
    }

    public final void onUserChanged(int i) {
        if (i == this.mCurrentUserId) {
            return;
        }
        boolean z = DEBUG;
        if (z) {
            Log.d("onUserChanged(), userId=" + i);
        }
        if (z) {
            Log.d("setCurrentUserId(), userId=" + i);
        }
        this.mCurrentUserId = i;
        initializeStates();
    }

    public final void resetInputFilter() {
        this.mWindowManagerService.setInputFilter(null);
        this.mInputFilter = null;
    }

    public final void setCursorPosition(int i, int i2, int i3) {
        try {
            if (this.mInputManagerInternal != null) {
                Log.i("in setCursorPosition [displayId : " + i3 + "]");
                InputManagerService.this.mNative.setCursorPosition(i, i2, i3);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            Log.save('E', message);
            android.util.Log.e("MultiControl@MultiControlManagerService", message);
        }
    }

    public final void setInputFilter(IInputFilter iInputFilter, IInputFilterInstallListener iInputFilterInstallListener) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "setInputFilter");
        this.mInputFilter = iInputFilter;
        this.mWindowManagerService.setInputFilter(iInputFilter);
        try {
            iInputFilterInstallListener.onInstalled();
        } catch (RemoteException e) {
            String message = e.getMessage();
            Log.save('E', message);
            android.util.Log.e("MultiControl@MultiControlManagerService", message);
        }
    }

    public final void setInteractive(boolean z) {
        try {
            if (this.mInputManagerInternal != null) {
                Log.i("in setInteractive");
                this.mInputManagerInternal.setInteractive(z);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            Log.save('E', message);
            android.util.Log.e("MultiControl@MultiControlManagerService", message);
        }
    }

    public final void setMultiControlOutOfFocus(boolean z) {
        try {
            if (this.mInputManagerInternal != null) {
                Log.i("in setMultiControlOutOfFocus " + z);
                InputManagerService.this.mNative.setMultiControlOutOfFocus(z);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            Log.save('E', message);
            android.util.Log.e("MultiControl@MultiControlManagerService", message);
        }
    }

    public final void setProtocolVersion(int i) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "setProtocolVersion");
    }

    public final void setTriggerThreshold(int i) {
        try {
            Log.i("[setTriggerThreshold] in " + i);
            MCTriggerManager mCTriggerManager = this.mcTriggerManager;
            if (mCTriggerManager != null) {
                mCTriggerManager.setTriggerThreshold(i);
            }
        } catch (Exception e) {
            Log.save('E', "[setTriggerThreshold]");
            android.util.Log.e("MultiControl@MultiControlManagerService", "[setTriggerThreshold]", e);
        }
    }

    public final void startDeathChecker(IMultiControlDeathChecker iMultiControlDeathChecker) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "setInputFilter");
        MultiControlAppDeathChecker multiControlAppDeathChecker = this.mAppDeathChecker;
        if (multiControlAppDeathChecker != null) {
            Log.i("MultiControlAppDeathChecker - unlinkToDeath");
            multiControlAppDeathChecker.mBinder.unlinkToDeath(multiControlAppDeathChecker, 0);
            this.mAppDeathChecker = null;
        }
        Binder.getCallingPid();
        Binder.getCallingUid();
        this.mAppDeathChecker = new MultiControlAppDeathChecker(iMultiControlDeathChecker);
        try {
            iMultiControlDeathChecker.asBinder().linkToDeath(this.mAppDeathChecker, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void stopDeathChecker() {
        MultiControlAppDeathChecker multiControlAppDeathChecker = this.mAppDeathChecker;
        if (multiControlAppDeathChecker != null) {
            Log.i("MultiControlAppDeathChecker - unlinkToDeath");
            multiControlAppDeathChecker.mBinder.unlinkToDeath(multiControlAppDeathChecker, 0);
            this.mAppDeathChecker = null;
        }
    }
}
