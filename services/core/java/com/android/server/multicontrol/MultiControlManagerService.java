package com.android.server.multicontrol;

import android.R;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import android.view.IInputFilter;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.input.InputManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.multicontrol.IInputFilterInstallListener;
import com.samsung.android.multicontrol.IMultiControlDeathChecker;
import com.samsung.android.multicontrol.IMultiControlManager;
import com.samsung.android.multicontrol.MCTriggerManager;
import com.samsung.android.multicontrol.MultiControlManagerInternal;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class MultiControlManagerService extends IMultiControlManager.Stub {
    public static final boolean DEBUG;
    public static final String TAG = "MultiControl@" + MultiControlManagerService.class.getSimpleName();
    public MultiControlAppDeathChecker mAppDeathChecker;
    public int mAppProtocolVersion;
    public final Context mContext;
    public int mCurrentUserId;
    public final Handler mHandler;
    public IInputFilter mInputFilter;
    public InputManagerInternal mInputManagerInternal;
    public boolean mIsBootComplete;
    public boolean mIsMultiControlEnabled;
    public boolean mIsTriggerDetectionEnabled;
    public LocalService mLocalService;
    public final ContentResolver mResolver;
    public final ServiceThread mThread;
    public ContentObserver mUserSetupCompleteObserver;
    public final WindowManagerInternal mWindowManagerService;
    public MCTriggerManager mcTriggerManager;

    static {
        DEBUG = Debug.semIsProductDev() || android.util.Log.isLoggable("RAMS", 3);
    }

    public final class Lifecycle extends SystemService {
        public MultiControlManagerService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.multicontrol.MultiControlManagerService] */
        @Override // com.android.server.SystemService
        public void onStart() {
            ?? multiControlManagerService = new MultiControlManagerService(getContext());
            this.mService = multiControlManagerService;
            publishBinderService("multicontrol", multiControlManagerService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            MultiControlManagerService multiControlManagerService = this.mService;
            if (multiControlManagerService != null) {
                multiControlManagerService.onBootPhase(i);
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(SystemService.TargetUser targetUser) {
            this.mService.onUserStarting(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(SystemService.TargetUser targetUser) {
            this.mService.onUserUnlocking(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            this.mService.onUserSwitching(targetUser2.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(SystemService.TargetUser targetUser) {
            this.mService.onUserStopping(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(SystemService.TargetUser targetUser) {
            this.mService.onUserStopped(targetUser.getUserIdentifier());
        }
    }

    public class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        public void register() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PHONE_STATE");
            intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
            MultiControlManagerService.this.mContext.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, null, MultiControlManagerService.this.mHandler);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean z = MultiControlManagerService.DEBUG;
            if (z) {
                Log.m47d(MultiControlManagerService.TAG, "onReceive(), action=" + action);
            }
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                if (z) {
                    Log.m47d(MultiControlManagerService.TAG, "Shutdown received with UserId: " + getSendingUserId());
                }
                if (getSendingUserId() == -1) {
                    MultiControlManagerService.this.mIsBootComplete = false;
                }
            }
        }
    }

    public final class LocalService extends MultiControlManagerInternal {
        public LocalService() {
        }

        public boolean isMultiControlEnabled() {
            return MultiControlManagerService.this.mInputFilter != null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MultiControlManagerService(Context context) {
        this.mIsBootComplete = false;
        this.mIsMultiControlEnabled = false;
        this.mIsTriggerDetectionEnabled = false;
        this.mCurrentUserId = -10000;
        this.mAppProtocolVersion = 1;
        ServiceThread serviceThread = new ServiceThread("multicontrol", -2, false);
        this.mThread = serviceThread;
        serviceThread.start();
        this.mHandler = new Handler(serviceThread.getLooper());
        this.mWindowManagerService = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        Object[] objArr = 0;
        this.mLocalService = null;
        this.mContext = context;
        context.setTheme(R.style.Theme.DeviceDefault.Light);
        this.mResolver = context.getContentResolver();
        this.mInputManagerInternal = (InputManagerInternal) LocalServices.getService(InputManagerInternal.class);
        this.mcTriggerManager = new MCTriggerManager(context, serviceThread.getLooper());
        new Receiver().register();
        if (this.mLocalService == null) {
            LocalService localService = new LocalService();
            this.mLocalService = localService;
            LocalServices.addService(MultiControlManagerInternal.class, localService);
        }
    }

    public void setTriggerThreshold(int i) {
        try {
            Log.m50i(TAG, "[setTriggerThreshold] in " + i);
            MCTriggerManager mCTriggerManager = this.mcTriggerManager;
            if (mCTriggerManager != null) {
                mCTriggerManager.setTriggerThreshold(i);
            }
        } catch (Exception e) {
            Log.m49e(TAG, "[setTriggerThreshold]", e);
        }
    }

    public void enableTriggerDetection(boolean z) {
        Log.m50i(TAG, "[enableTriggerDetection] in " + z);
        try {
            MCTriggerManager mCTriggerManager = this.mcTriggerManager;
            if (mCTriggerManager != null) {
                mCTriggerManager.enable(z);
            }
        } catch (Exception e) {
            Log.m49e(TAG, "[enableTriggerDetection]", e);
        }
    }

    public void forceHideCursor(boolean z) {
        try {
            if (this.mInputManagerInternal != null) {
                Log.m50i(TAG, "in forceHideCursor");
                this.mInputManagerInternal.forceHideCursor(z);
            }
        } catch (Exception e) {
            Log.m48e(TAG, e.getMessage());
        }
    }

    public void setCursorPosition(int i, int i2, int i3) {
        try {
            if (this.mInputManagerInternal != null) {
                Log.m50i(TAG, "in setCursorPosition [displayId : " + i3 + "]");
                this.mInputManagerInternal.setCursorPosition(i, i2, i3);
            }
        } catch (Exception e) {
            Log.m48e(TAG, e.getMessage());
        }
    }

    public void setInteractive(boolean z) {
        try {
            if (this.mInputManagerInternal != null) {
                Log.m50i(TAG, "in setInteractive");
                this.mInputManagerInternal.setInteractive(z);
            }
        } catch (Exception e) {
            Log.m48e(TAG, e.getMessage());
        }
    }

    public void setMultiControlOutOfFocus(boolean z) {
        try {
            if (this.mInputManagerInternal != null) {
                Log.m50i(TAG, "in setMultiControlOutOfFocus " + z);
                this.mInputManagerInternal.setMultiControlOutOfFocus(z);
            }
        } catch (Exception e) {
            Log.m48e(TAG, e.getMessage());
        }
    }

    public final void onBootPhase(int i) {
        if (DEBUG) {
            Slog.i(TAG, "onBootPhase(" + i + ")");
        }
    }

    public final void initializeStates() {
        if (isUserSetupComplete()) {
            return;
        }
        if (this.mUserSetupCompleteObserver == null) {
            this.mUserSetupCompleteObserver = new ContentObserver(null) { // from class: com.android.server.multicontrol.MultiControlManagerService.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    super.onChange(z);
                    MultiControlManagerService.this.initializeStates();
                    MultiControlManagerService.this.mResolver.unregisterContentObserver(this);
                    MultiControlManagerService.this.mUserSetupCompleteObserver = null;
                }
            };
        }
        this.mResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this.mUserSetupCompleteObserver, this.mCurrentUserId);
    }

    public final void onUserStarting(int i) {
        if (DEBUG) {
            Log.m47d(TAG, "onStartUser(), userId=" + i);
        }
    }

    public final void onUserUnlocking(int i) {
        if (DEBUG) {
            Log.m47d(TAG, "onUnlockUser(), userId=" + i + ", CurrentUser=" + ActivityManager.getCurrentUser());
        }
        this.mIsBootComplete = true;
        if (i == ActivityManager.getCurrentUser()) {
            onUserChanged(i);
        }
    }

    public final void onUserSwitching(int i) {
        if (DEBUG) {
            Log.m47d(TAG, "onSwitchUser(), userId=" + i);
        }
        onUserChanged(i);
    }

    public final void onUserStopping(int i) {
        if (DEBUG) {
            Log.m47d(TAG, "onStopUser(), userId=" + i + ", CurrentUser=" + ActivityManager.getCurrentUser());
        }
    }

    public final void onUserStopped(int i) {
        if (DEBUG) {
            Log.m47d(TAG, "onCleanupUser(), userId=" + i);
        }
    }

    public void setCurrentUserId(int i) {
        if (DEBUG) {
            Log.m47d(TAG, "setCurrentUserId(), userId=" + i);
        }
        this.mCurrentUserId = i;
    }

    public final void onUserChanged(int i) {
        if (i == this.mCurrentUserId) {
            return;
        }
        if (DEBUG) {
            Log.m47d(TAG, "onUserChanged(), userId=" + i);
        }
        setCurrentUserId(i);
        initializeStates();
    }

    public final boolean isUserSetupComplete() {
        boolean z = Settings.Secure.getIntForUser(this.mResolver, "user_setup_complete", 0, this.mCurrentUserId) != 0;
        if (!z && DEBUG) {
            Log.m47d(TAG, "isUserSetupComplete()=false");
        }
        return z;
    }

    public final boolean isSystemReady() {
        if (this.mIsBootComplete && isUserSetupComplete() && !FactoryTest.isFactoryBinary() && this.mCurrentUserId != -10000) {
            return true;
        }
        Log.m50i(TAG, "isSystemReady(), mIsBootComplete=" + this.mIsBootComplete + ", isFactoryBinary=" + FactoryTest.isFactoryBinary() + ", mCurrentUserId=" + this.mCurrentUserId);
        return false;
    }

    public void setProtocolVersion(int i) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "setProtocolVersion");
        this.mAppProtocolVersion = i;
    }

    public boolean isAllowed() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "isAllowed");
        boolean isSystemReady = isSystemReady();
        Log.m47d(TAG, "isAllowed = " + isSystemReady);
        return isSystemReady;
    }

    public int getProtocolVersion() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "getProtocolVersion");
        return 1;
    }

    public void setInputFilter(IInputFilter iInputFilter, IInputFilterInstallListener iInputFilterInstallListener) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "setInputFilter");
        this.mInputFilter = iInputFilter;
        this.mWindowManagerService.setInputFilter(iInputFilter);
        try {
            iInputFilterInstallListener.onInstalled();
        } catch (RemoteException e) {
            Log.m48e(TAG, e.getMessage());
        }
    }

    public void startDeathChecker(IMultiControlDeathChecker iMultiControlDeathChecker) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_MULTI_CONTROL_MANAGER", "setInputFilter");
        unlinkListenerToDeath();
        this.mAppDeathChecker = new MultiControlAppDeathChecker(iMultiControlDeathChecker, Binder.getCallingPid(), Binder.getCallingUid());
        try {
            iMultiControlDeathChecker.asBinder().linkToDeath(this.mAppDeathChecker, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopDeathChecker() {
        unlinkListenerToDeath();
    }

    public void resetInputFilter() {
        this.mWindowManagerService.setInputFilter(null);
        this.mInputFilter = null;
    }

    public final void unlinkListenerToDeath() {
        MultiControlAppDeathChecker multiControlAppDeathChecker = this.mAppDeathChecker;
        if (multiControlAppDeathChecker != null) {
            multiControlAppDeathChecker.unlinkToDeath();
            this.mAppDeathChecker = null;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            if (strArr == null || strArr.length == 0 || "-a".equals(strArr[0])) {
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
                indentingPrintWriter.println("MultiControlManagerService (dumpsys multicontrol):");
                Log.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.increaseIndent();
                dumpImpl(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    public final void dumpImpl(IndentingPrintWriter indentingPrintWriter) {
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
    }

    public class MultiControlAppDeathChecker implements IBinder.DeathRecipient {
        public final IBinder mBinder;
        public final IMultiControlDeathChecker mListener;
        public final int mPid;
        public final int mUid;

        public MultiControlAppDeathChecker(IMultiControlDeathChecker iMultiControlDeathChecker, int i, int i2) {
            this.mListener = iMultiControlDeathChecker;
            this.mBinder = iMultiControlDeathChecker.asBinder();
            this.mPid = i;
            this.mUid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.m50i(MultiControlManagerService.TAG, "MultiControlAppDeathChecker - binderDied");
            resetMultiControlValue();
            this.mBinder.unlinkToDeath(this, 0);
        }

        public void unlinkToDeath() {
            Log.m50i(MultiControlManagerService.TAG, "MultiControlAppDeathChecker - unlinkToDeath");
            this.mBinder.unlinkToDeath(this, 0);
        }

        public final void resetMultiControlValue() {
            Log.m50i(MultiControlManagerService.TAG, "in resetMultiControlValue");
            MultiControlManagerService.this.forceHideCursor(false);
            MultiControlManagerService.this.setMultiControlOutOfFocus(false);
            try {
                if (MultiControlManagerService.this.mInputFilter != null) {
                    MultiControlManagerService.this.resetInputFilter();
                }
            } catch (Exception unused) {
            }
            try {
                Intent intent = new Intent("com.samsung.android.inputshare.action.ACTION_MULTI_CONTROL_DIED");
                intent.setPackage("com.samsung.android.inputshare");
                MultiControlManagerService.this.mContext.sendBroadcast(intent, "com.samsung.android.permission.MULTI_CONTROL_RECEIVER_PERMISSION");
                Log.m50i(MultiControlManagerService.TAG, "sendBroadcast - ACTION_MULTI_CONTROL_DIED");
            } catch (Exception e) {
                Log.m49e(MultiControlManagerService.TAG, "sendBroadcast - ACTION_MULTI_CONTROL_DIED", e);
            }
        }
    }
}
