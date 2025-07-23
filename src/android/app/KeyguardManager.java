package android.app;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.INotificationManager;
import android.app.KeyguardManager;
import android.app.admin.PasswordMetrics;
import android.app.trust.ITrustManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.persistentdata.IPersistentDataBlockService;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Log;
import android.view.IOnKeyguardExitResult;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.policy.IDeviceLockedStateListener;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardLockedStateListener;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.IWeakEscrowTokenActivatedListener;
import com.android.internal.widget.IWeakEscrowTokenRemovedListener;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.PasswordValidationError;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.knox.SemPersonaManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class KeyguardManager {
    public static final String ACTION_CONFIRM_DEVICE_CREDENTIAL = "android.app.action.CONFIRM_DEVICE_CREDENTIAL";
    public static final String ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER = "android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER";
    public static final String ACTION_CONFIRM_FRP_CREDENTIAL = "android.app.action.CONFIRM_FRP_CREDENTIAL";
    public static final String ACTION_CONFIRM_REMOTE_DEVICE_CREDENTIAL = "android.app.action.CONFIRM_REMOTE_DEVICE_CREDENTIAL";
    public static final String ACTION_CONFIRM_REPAIR_MODE_DEVICE_CREDENTIAL = "android.app.action.CONFIRM_REPAIR_MODE_DEVICE_CREDENTIAL";
    public static final String ACTION_PREPARE_REPAIR_MODE_DEVICE_CREDENTIAL = "android.app.action.PREPARE_REPAIR_MODE_DEVICE_CREDENTIAL";
    public static final String EXTRA_ALTERNATE_BUTTON_LABEL = "android.app.extra.ALTERNATE_BUTTON_LABEL";
    public static final String EXTRA_CHECKBOX_LABEL = "android.app.extra.CHECKBOX_LABEL";
    public static final String EXTRA_DESCRIPTION = "android.app.extra.DESCRIPTION";
    public static final String EXTRA_DISALLOW_BIOMETRICS_IF_POLICY_EXISTS = "check_dpm";
    public static final String EXTRA_FORCE_TASK_OVERLAY = "android.app.KeyguardManager.FORCE_TASK_OVERLAY";
    public static final String EXTRA_REMOTE_LOCKSCREEN_VALIDATION_SESSION = "android.app.extra.REMOTE_LOCKSCREEN_VALIDATION_SESSION";
    public static final String EXTRA_TITLE = "android.app.extra.TITLE";
    public static final String LOCK_ON_USER_SWITCH_CALLBACK = "onSwitchCallback";

    @SystemApi
    public static final int PASSWORD = 0;

    @SystemApi
    public static final int PATTERN = 2;

    @SystemApi
    public static final int PIN = 1;
    public static final int RESULT_ALTERNATE = 1;
    public static final int SMART_CARD = 3;
    private static final String TAG = "KeyguardManager";
    private final Context mContext;
    private final LockPatternUtils mLockPatternUtils;
    private final ArrayMap<WeakEscrowTokenRemovedListener, IWeakEscrowTokenRemovedListener> mListeners = new ArrayMap<>();
    private final IKeyguardLockedStateListener mIKeyguardLockedStateListener = new AnonymousClass1();
    private final ArrayMap<KeyguardLockedStateListener, Executor> mKeyguardLockedStateListeners = new ArrayMap<>();
    private final IDeviceLockedStateListener mIDeviceLockedStateListener = new AnonymousClass2();
    private final ArrayMap<DeviceLockedStateListener, Executor> mDeviceLockedStateListeners = new ArrayMap<>();
    private final IWindowManager mWM = WindowManagerGlobal.getWindowManagerService();
    private final IActivityManager mAm = ActivityManager.getService();
    private final ITrustManager mTrustManager = ITrustManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.TRUST_SERVICE));
    private final INotificationManager mNotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getServiceOrThrow("notification"));

    @FunctionalInterface
    public interface DeviceLockedStateListener {
        void onDeviceLockedStateChanged(boolean z);
    }

    public static abstract class KeyguardDismissCallback {
        public void onDismissCancelled() {
        }

        public void onDismissError() {
        }

        public void onDismissSucceeded() {
        }
    }

    @FunctionalInterface
    public interface KeyguardLockedStateListener {
        void onKeyguardLockedStateChanged(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface LockTypes {
    }

    @Deprecated
    public interface OnKeyguardExitResult {
        void onKeyguardExitResult(boolean z);
    }

    @SystemApi
    public interface WeakEscrowTokenActivatedListener {
        void onWeakEscrowTokenActivated(long j, UserHandle userHandle);
    }

    @SystemApi
    public interface WeakEscrowTokenRemovedListener {
        void onWeakEscrowTokenRemoved(long j, UserHandle userHandle);
    }

    /* renamed from: android.app.KeyguardManager$1, reason: invalid class name */
    class AnonymousClass1 extends IKeyguardLockedStateListener.Stub {
        AnonymousClass1() {
        }

        @Override // com.android.internal.policy.IKeyguardLockedStateListener
        public void onKeyguardLockedStateChanged(final boolean z) {
            KeyguardManager.this.mKeyguardLockedStateListeners.forEach(new BiConsumer() { // from class: android.app.KeyguardManager$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    Executor executor = (Executor) obj2;
                    executor.execute(new Runnable() { // from class: android.app.KeyguardManager$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardManager.KeyguardLockedStateListener.this.onKeyguardLockedStateChanged(r2);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.app.KeyguardManager$2, reason: invalid class name */
    class AnonymousClass2 extends IDeviceLockedStateListener.Stub {
        AnonymousClass2() {
        }

        @Override // com.android.internal.policy.IDeviceLockedStateListener
        public void onDeviceLockedStateChanged(final boolean z) {
            if (Flags.deviceUnlockListener()) {
                synchronized (KeyguardManager.this.mDeviceLockedStateListeners) {
                    KeyguardManager.this.mDeviceLockedStateListeners.forEach(new BiConsumer() { // from class: android.app.KeyguardManager$2$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            Executor executor = (Executor) obj2;
                            executor.execute(new Runnable() { // from class: android.app.KeyguardManager$2$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    KeyguardManager.DeviceLockedStateListener.this.onDeviceLockedStateChanged(r2);
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    @Deprecated
    public Intent createConfirmDeviceCredentialIntent(CharSequence charSequence, CharSequence charSequence2) {
        if (!isDeviceSecure() && !SemPersonaManager.appliedPasswordPolicy(this.mContext.getUserId())) {
            return null;
        }
        Intent intent = new Intent(ACTION_CONFIRM_DEVICE_CREDENTIAL);
        intent.putExtra(EXTRA_TITLE, charSequence);
        intent.putExtra(EXTRA_DESCRIPTION, charSequence2);
        intent.setPackage(getSettingsPackageForIntent(intent));
        return intent;
    }

    public Intent createConfirmDeviceCredentialIntent(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (!isDeviceSecure(i) && !SemPersonaManager.appliedPasswordPolicy(i)) {
            return null;
        }
        Intent intent = new Intent(ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER);
        intent.putExtra(EXTRA_TITLE, charSequence);
        intent.putExtra(EXTRA_DESCRIPTION, charSequence2);
        intent.putExtra("android.intent.extra.USER_ID", i);
        intent.setPackage(getSettingsPackageForIntent(intent));
        return intent;
    }

    public Intent createConfirmDeviceCredentialIntent(CharSequence charSequence, CharSequence charSequence2, int i, boolean z) {
        Intent createConfirmDeviceCredentialIntent = createConfirmDeviceCredentialIntent(charSequence, charSequence2, i);
        if (createConfirmDeviceCredentialIntent != null) {
            createConfirmDeviceCredentialIntent.putExtra(EXTRA_DISALLOW_BIOMETRICS_IF_POLICY_EXISTS, z);
        }
        return createConfirmDeviceCredentialIntent;
    }

    @SystemApi
    public Intent createConfirmFactoryResetCredentialIntent(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        if (!LockPatternUtils.frpCredentialEnabled(this.mContext)) {
            Log.w(TAG, "Factory reset credentials not supported.");
            throw new UnsupportedOperationException("not supported on this device");
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
            Log.e(TAG, "Factory reset credential cannot be verified after provisioning.");
            throw new IllegalStateException("must not be provisioned yet");
        }
        try {
            IPersistentDataBlockService asInterface = IPersistentDataBlockService.Stub.asInterface(ServiceManager.getService(Context.PERSISTENT_DATA_BLOCK_SERVICE));
            if (asInterface == null) {
                Log.e(TAG, "No persistent data block service");
                throw new UnsupportedOperationException("not supported on this device");
            }
            if (!asInterface.hasFrpCredentialHandle()) {
                Log.i(TAG, "The persistent data block does not have a factory reset credential.");
                return null;
            }
            Intent intent = new Intent(ACTION_CONFIRM_FRP_CREDENTIAL);
            intent.putExtra(EXTRA_TITLE, charSequence);
            intent.putExtra(EXTRA_DESCRIPTION, charSequence2);
            intent.putExtra(EXTRA_ALTERNATE_BUTTON_LABEL, charSequence3);
            intent.setPackage(getSettingsPackageForIntent(intent));
            return intent;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Intent createConfirmDeviceCredentialForRemoteValidationIntent(RemoteLockscreenValidationSession remoteLockscreenValidationSession, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
        Intent putExtra = new Intent(ACTION_CONFIRM_REMOTE_DEVICE_CREDENTIAL).putExtra(EXTRA_REMOTE_LOCKSCREEN_VALIDATION_SESSION, remoteLockscreenValidationSession).putExtra(Intent.EXTRA_COMPONENT_NAME, componentName).putExtra(EXTRA_TITLE, charSequence).putExtra(EXTRA_DESCRIPTION, charSequence2).putExtra(EXTRA_CHECKBOX_LABEL, charSequence3).putExtra(EXTRA_ALTERNATE_BUTTON_LABEL, charSequence4);
        putExtra.setPackage(getSettingsPackageForIntent(putExtra));
        return putExtra;
    }

    @SystemApi
    public void setPrivateNotificationsAllowed(boolean z) {
        try {
            this.mNotificationManager.setPrivateNotificationsAllowed(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean getPrivateNotificationsAllowed() {
        try {
            return this.mNotificationManager.getPrivateNotificationsAllowed();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private String getSettingsPackageForIntent(Intent intent) {
        List<ResolveInfo> queryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(intent, 1048576);
        if (queryIntentActivities.size() > 0) {
            return queryIntentActivities.get(0).activityInfo.packageName;
        }
        return "com.android.settings";
    }

    @Deprecated
    public class KeyguardLock {
        private final String mTag;
        private final IBinder mToken = new Binder();

        KeyguardLock(String str) {
            if (str != null) {
                this.mTag = str + "#" + Process.myPid();
                return;
            }
            this.mTag = "#" + Process.myPid();
        }

        public void disableKeyguard() {
            try {
                EventLog.writeEvent(EventLogTags.SCREEN_DISABLED, this.mTag, 0);
                KeyguardManager.this.mWM.disableKeyguard(this.mToken, this.mTag, KeyguardManager.this.mContext.getUserId());
            } catch (RemoteException unused) {
            }
        }

        public void reenableKeyguard() {
            try {
                EventLog.writeEvent(EventLogTags.SCREEN_DISABLED, this.mTag, 1);
                KeyguardManager.this.mWM.reenableKeyguard(this.mToken, KeyguardManager.this.mContext.getUserId());
            } catch (RemoteException unused) {
            }
        }
    }

    KeyguardManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    @Deprecated
    public KeyguardLock newKeyguardLock(String str) {
        return new KeyguardLock(str);
    }

    public boolean isKeyguardLocked() {
        try {
            return this.mWM.isKeyguardLocked();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isKeyguardSecure() {
        try {
            return this.mWM.isKeyguardSecure(this.mContext.getUserId());
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean inKeyguardRestrictedInputMode() {
        return isKeyguardLocked();
    }

    public boolean isDeviceLocked() {
        return isDeviceLocked(this.mContext.getUserId(), this.mContext.getDeviceId());
    }

    public boolean isDeviceLocked(int i) {
        return isDeviceLocked(i, this.mContext.getDeviceId());
    }

    public boolean isDeviceLocked(int i, int i2) {
        try {
            return this.mTrustManager.isDeviceLocked(i, i2);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isDeviceSecure() {
        return isDeviceSecure(this.mContext.getUserId(), this.mContext.getDeviceId());
    }

    public boolean isDeviceSecure(int i) {
        return isDeviceSecure(i, this.mContext.getDeviceId());
    }

    public boolean isDeviceSecure(int i, int i2) {
        try {
            return this.mTrustManager.isDeviceSecure(i, i2);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void requestDismissKeyguard(Activity activity, KeyguardDismissCallback keyguardDismissCallback) {
        requestDismissKeyguard(activity, null, keyguardDismissCallback);
    }

    @SystemApi
    public void requestDismissKeyguard(final Activity activity, CharSequence charSequence, final KeyguardDismissCallback keyguardDismissCallback) {
        EventLog.writeEvent(EventLogTags.DISMISS_SCREEN, Integer.valueOf(Process.myPid()), "requestDismissKeyguard");
        ActivityClient.getInstance().dismissKeyguard(activity.getActivityToken(), new IKeyguardDismissCallback.Stub(this) { // from class: android.app.KeyguardManager.3
            @Override // com.android.internal.policy.IKeyguardDismissCallback
            public void onDismissError() throws RemoteException {
                if (keyguardDismissCallback == null || activity.isDestroyed()) {
                    return;
                }
                Handler handler = activity.mHandler;
                final KeyguardDismissCallback keyguardDismissCallback2 = keyguardDismissCallback;
                Objects.requireNonNull(keyguardDismissCallback2);
                handler.post(new Runnable() { // from class: android.app.KeyguardManager$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardManager.KeyguardDismissCallback.this.onDismissError();
                    }
                });
            }

            @Override // com.android.internal.policy.IKeyguardDismissCallback
            public void onDismissSucceeded() throws RemoteException {
                if (keyguardDismissCallback == null || activity.isDestroyed()) {
                    return;
                }
                Handler handler = activity.mHandler;
                final KeyguardDismissCallback keyguardDismissCallback2 = keyguardDismissCallback;
                Objects.requireNonNull(keyguardDismissCallback2);
                handler.post(new Runnable() { // from class: android.app.KeyguardManager$3$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardManager.KeyguardDismissCallback.this.onDismissSucceeded();
                    }
                });
            }

            @Override // com.android.internal.policy.IKeyguardDismissCallback
            public void onDismissCancelled() throws RemoteException {
                if (keyguardDismissCallback == null || activity.isDestroyed()) {
                    return;
                }
                Handler handler = activity.mHandler;
                final KeyguardDismissCallback keyguardDismissCallback2 = keyguardDismissCallback;
                Objects.requireNonNull(keyguardDismissCallback2);
                handler.post(new Runnable() { // from class: android.app.KeyguardManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardManager.KeyguardDismissCallback.this.onDismissCancelled();
                    }
                });
            }
        }, charSequence);
    }

    @Deprecated
    public void exitKeyguardSecurely(final OnKeyguardExitResult onKeyguardExitResult) {
        try {
            this.mWM.exitKeyguardSecurely(new IOnKeyguardExitResult.Stub(this) { // from class: android.app.KeyguardManager.4
                @Override // android.view.IOnKeyguardExitResult
                public void onKeyguardExitResult(boolean z) throws RemoteException {
                    OnKeyguardExitResult onKeyguardExitResult2 = onKeyguardExitResult;
                    if (onKeyguardExitResult2 != null) {
                        onKeyguardExitResult2.onKeyguardExitResult(z);
                    }
                }
            });
        } catch (RemoteException unused) {
        }
    }

    public boolean checkInitialLockMethodUsage() {
        if (hasPermission(Manifest.permission.SET_INITIAL_LOCK)) {
            return true;
        }
        throw new SecurityException("Requires SET_INITIAL_LOCK permission.");
    }

    private boolean hasPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    @SystemApi
    public boolean isValidLockPasswordComplexity(int i, byte[] bArr, int i2) {
        if (!checkInitialLockMethodUsage()) {
            return false;
        }
        Objects.requireNonNull(bArr, "Password cannot be null.");
        int sanitizeComplexityLevel = PasswordMetrics.sanitizeComplexityLevel(i2);
        PasswordMetrics requestedPasswordMetrics = this.mLockPatternUtils.getRequestedPasswordMetrics(this.mContext.getUserId());
        LockscreenCredential createLockscreenCredential = createLockscreenCredential(i, bArr);
        try {
            boolean z = PasswordMetrics.validateCredential(requestedPasswordMetrics, sanitizeComplexityLevel, createLockscreenCredential).size() == 0;
            if (createLockscreenCredential != null) {
                createLockscreenCredential.close();
            }
            return z;
        } catch (Throwable th) {
            if (createLockscreenCredential != null) {
                try {
                    createLockscreenCredential.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @SystemApi
    public int getMinLockLength(boolean z, int i) {
        if (!checkInitialLockMethodUsage()) {
            return -1;
        }
        return PasswordMetrics.applyComplexity(this.mLockPatternUtils.getRequestedPasswordMetrics(this.mContext.getUserId()), z, PasswordMetrics.sanitizeComplexityLevel(i)).length;
    }

    @SystemApi
    public boolean setLock(int i, byte[] bArr, int i2) {
        if (!checkInitialLockMethodUsage()) {
            return false;
        }
        int userId = this.mContext.getUserId();
        if (isDeviceSecure(userId)) {
            Log.e(TAG, "Password already set, rejecting call to setLock");
            return false;
        }
        try {
            if (!isValidLockPasswordComplexity(i, bArr, i2)) {
                Log.e(TAG, "Password is not valid, rejecting call to setLock");
                return false;
            }
            LockscreenCredential createLockscreenCredential = createLockscreenCredential(i, bArr);
            try {
                boolean lockCredential = this.mLockPatternUtils.setLockCredential(createLockscreenCredential, LockscreenCredential.createNone(), userId);
                if (createLockscreenCredential != null) {
                    createLockscreenCredential.close();
                }
                return lockCredential;
            } catch (Throwable th) {
                if (createLockscreenCredential != null) {
                    try {
                        createLockscreenCredential.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e(TAG, "Save lock exception", e);
            return false;
        } finally {
            LockPatternUtils.zeroize(bArr);
        }
    }

    @SystemApi
    public long addWeakEscrowToken(byte[] bArr, UserHandle userHandle, Executor executor, WeakEscrowTokenActivatedListener weakEscrowTokenActivatedListener) {
        Objects.requireNonNull(bArr, "Token cannot be null.");
        Objects.requireNonNull(userHandle, "User cannot be null.");
        Objects.requireNonNull(executor, "Executor cannot be null.");
        Objects.requireNonNull(weakEscrowTokenActivatedListener, "Listener cannot be null.");
        return this.mLockPatternUtils.addWeakEscrowToken(bArr, userHandle.getIdentifier(), new AnonymousClass5(this, executor, weakEscrowTokenActivatedListener));
    }

    /* renamed from: android.app.KeyguardManager$5, reason: invalid class name */
    class AnonymousClass5 extends IWeakEscrowTokenActivatedListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ WeakEscrowTokenActivatedListener val$listener;

        AnonymousClass5(KeyguardManager keyguardManager, Executor executor, WeakEscrowTokenActivatedListener weakEscrowTokenActivatedListener) {
            this.val$executor = executor;
            this.val$listener = weakEscrowTokenActivatedListener;
        }

        @Override // com.android.internal.widget.IWeakEscrowTokenActivatedListener
        public void onWeakEscrowTokenActivated(final long j, int i) {
            final UserHandle of = UserHandle.of(i);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final WeakEscrowTokenActivatedListener weakEscrowTokenActivatedListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.app.KeyguardManager$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardManager.WeakEscrowTokenActivatedListener.this.onWeakEscrowTokenActivated(j, of);
                    }
                });
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Log.i(KeyguardManager.TAG, "Weak escrow token activated.");
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    @SystemApi
    public boolean removeWeakEscrowToken(long j, UserHandle userHandle) {
        Objects.requireNonNull(userHandle, "User cannot be null.");
        return this.mLockPatternUtils.removeWeakEscrowToken(j, userHandle.getIdentifier());
    }

    @SystemApi
    public boolean isWeakEscrowTokenActive(long j, UserHandle userHandle) {
        Objects.requireNonNull(userHandle, "User cannot be null.");
        return this.mLockPatternUtils.isWeakEscrowTokenActive(j, userHandle.getIdentifier());
    }

    @SystemApi
    public boolean isWeakEscrowTokenValid(long j, byte[] bArr, UserHandle userHandle) {
        Objects.requireNonNull(bArr, "Token cannot be null.");
        Objects.requireNonNull(userHandle, "User cannot be null.");
        return this.mLockPatternUtils.isWeakEscrowTokenValid(j, bArr, userHandle.getIdentifier());
    }

    @SystemApi
    public boolean registerWeakEscrowTokenRemovedListener(Executor executor, WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener) {
        Objects.requireNonNull(weakEscrowTokenRemovedListener, "Listener cannot be null.");
        Objects.requireNonNull(executor, "Executor cannot be null.");
        Preconditions.checkArgument(!this.mListeners.containsKey(weakEscrowTokenRemovedListener), "Listener already registered: %s", weakEscrowTokenRemovedListener);
        AnonymousClass6 anonymousClass6 = new AnonymousClass6(this, executor, weakEscrowTokenRemovedListener);
        if (this.mLockPatternUtils.registerWeakEscrowTokenRemovedListener(anonymousClass6)) {
            this.mListeners.put(weakEscrowTokenRemovedListener, anonymousClass6);
            return true;
        }
        Log.e(TAG, "Listener failed to register");
        return false;
    }

    /* renamed from: android.app.KeyguardManager$6, reason: invalid class name */
    class AnonymousClass6 extends IWeakEscrowTokenRemovedListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ WeakEscrowTokenRemovedListener val$listener;

        AnonymousClass6(KeyguardManager keyguardManager, Executor executor, WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener) {
            this.val$executor = executor;
            this.val$listener = weakEscrowTokenRemovedListener;
        }

        @Override // com.android.internal.widget.IWeakEscrowTokenRemovedListener
        public void onWeakEscrowTokenRemoved(final long j, int i) {
            final UserHandle of = UserHandle.of(i);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.app.KeyguardManager$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardManager.WeakEscrowTokenRemovedListener.this.onWeakEscrowTokenRemoved(j, of);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @SystemApi
    public boolean unregisterWeakEscrowTokenRemovedListener(WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener) {
        Objects.requireNonNull(weakEscrowTokenRemovedListener, "Listener cannot be null.");
        IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener = this.mListeners.get(weakEscrowTokenRemovedListener);
        Preconditions.checkArgument(iWeakEscrowTokenRemovedListener != null, "Listener was not registered");
        if (this.mLockPatternUtils.unregisterWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener)) {
            this.mListeners.remove(weakEscrowTokenRemovedListener);
            return true;
        }
        Log.e(TAG, "Listener failed to unregister.");
        return false;
    }

    public boolean setLock(int i, byte[] bArr, int i2, byte[] bArr2) {
        int userId = this.mContext.getUserId();
        LockscreenCredential createLockscreenCredential = createLockscreenCredential(i2, bArr2);
        try {
            LockscreenCredential createLockscreenCredential2 = createLockscreenCredential(i, bArr);
            try {
                List<PasswordValidationError> validateCredential = PasswordMetrics.validateCredential(this.mLockPatternUtils.getRequestedPasswordMetrics(this.mContext.getUserId()), 0, createLockscreenCredential2);
                if (!validateCredential.isEmpty()) {
                    Log.e(TAG, "New credential is not valid: " + validateCredential.get(0));
                    if (createLockscreenCredential2 != null) {
                        createLockscreenCredential2.close();
                    }
                    if (createLockscreenCredential != null) {
                        createLockscreenCredential.close();
                    }
                    return false;
                }
                boolean lockCredential = this.mLockPatternUtils.setLockCredential(createLockscreenCredential2, createLockscreenCredential, userId);
                if (createLockscreenCredential2 != null) {
                    createLockscreenCredential2.close();
                }
                if (createLockscreenCredential != null) {
                    createLockscreenCredential.close();
                }
                return lockCredential;
            } finally {
            }
        } catch (Throwable th) {
            if (createLockscreenCredential != null) {
                try {
                    createLockscreenCredential.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public boolean checkLock(int i, byte[] bArr) {
        LockscreenCredential createLockscreenCredential = createLockscreenCredential(i, bArr);
        try {
            VerifyCredentialResponse verifyCredential = this.mLockPatternUtils.verifyCredential(createLockscreenCredential, this.mContext.getUserId(), 0);
            if (verifyCredential == null) {
                if (createLockscreenCredential != null) {
                    createLockscreenCredential.close();
                }
                return false;
            }
            boolean z = verifyCredential.getResponseCode() == 0;
            if (createLockscreenCredential != null) {
                createLockscreenCredential.close();
            }
            return z;
        } catch (Throwable th) {
            if (createLockscreenCredential != null) {
                try {
                    createLockscreenCredential.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @SystemApi
    public RemoteLockscreenValidationSession startRemoteLockscreenValidation() {
        return this.mLockPatternUtils.startRemoteLockscreenValidation();
    }

    @SystemApi
    public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) {
        return this.mLockPatternUtils.validateRemoteLockscreen(bArr);
    }

    private LockscreenCredential createLockscreenCredential(int i, byte[] bArr) {
        if (bArr == null) {
            return LockscreenCredential.createNone();
        }
        if (i == 0) {
            return LockscreenCredential.createPassword(new String(bArr, Charset.forName("UTF-8")));
        }
        if (i == 1) {
            return LockscreenCredential.createPin(new String(bArr));
        }
        if (i == 2) {
            return LockscreenCredential.createPattern(LockPatternUtils.byteArrayToPattern(bArr));
        }
        throw new IllegalArgumentException("Unknown lock type " + i);
    }

    public void addKeyguardLockedStateListener(Executor executor, KeyguardLockedStateListener keyguardLockedStateListener) {
        synchronized (this.mKeyguardLockedStateListeners) {
            this.mKeyguardLockedStateListeners.put(keyguardLockedStateListener, executor);
            if (this.mKeyguardLockedStateListeners.size() > 1) {
                return;
            }
            try {
                this.mWM.addKeyguardLockedStateListener(this.mIKeyguardLockedStateListener);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeKeyguardLockedStateListener(KeyguardLockedStateListener keyguardLockedStateListener) {
        synchronized (this.mKeyguardLockedStateListeners) {
            this.mKeyguardLockedStateListeners.remove(keyguardLockedStateListener);
            if (this.mKeyguardLockedStateListeners.isEmpty()) {
                try {
                    this.mWM.removeKeyguardLockedStateListener(this.mIKeyguardLockedStateListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void addDeviceLockedStateListener(Executor executor, DeviceLockedStateListener deviceLockedStateListener) {
        if (Flags.deviceUnlockListener()) {
            synchronized (this.mDeviceLockedStateListeners) {
                this.mDeviceLockedStateListeners.put(deviceLockedStateListener, executor);
                if (this.mDeviceLockedStateListeners.size() > 1) {
                    return;
                }
                try {
                    this.mTrustManager.registerDeviceLockedStateListener(this.mIDeviceLockedStateListener, this.mContext.getDeviceId());
                } catch (RemoteException e) {
                    Log.d(TAG, "TrustManager service died", e);
                }
            }
        }
    }

    public void removeDeviceLockedStateListener(DeviceLockedStateListener deviceLockedStateListener) {
        if (Flags.deviceUnlockListener()) {
            synchronized (this.mDeviceLockedStateListeners) {
                this.mDeviceLockedStateListeners.remove(deviceLockedStateListener);
                if (this.mDeviceLockedStateListeners.isEmpty()) {
                    try {
                        this.mTrustManager.unregisterDeviceLockedStateListener(this.mIDeviceLockedStateListener);
                    } catch (RemoteException e) {
                        Log.d(TAG, "TrustManager service died", e);
                    }
                }
            }
        }
    }

    public void semDismissKeyguard() {
        try {
            EventLog.writeEvent(EventLogTags.DISMISS_SCREEN, Integer.valueOf(Process.myPid()), "semDismissKeyguard");
            this.mWM.dismissKeyguard(null, null);
        } catch (RemoteException unused) {
        }
    }

    public boolean semIsKeyguardShowingAndNotOccluded() {
        try {
            return this.mWM.isKeyguardShowingAndNotOccluded();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void semSetPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) {
        try {
            EventLog.writeEvent(EventLogTags.PENDING_INTENT_AFTER_UNLOCK, Integer.valueOf(Process.myPid()), "pendingIntentAfterUnlock");
            this.mWM.setPendingIntentAfterUnlock(pendingIntent, intent);
        } catch (RemoteException unused) {
        }
    }

    public void semStartLockscreenFingerprintAuth() {
        try {
            this.mWM.startLockscreenFingerprintAuth();
        } catch (RemoteException unused) {
        }
    }
}
