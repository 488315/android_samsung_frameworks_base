package android.app;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PasswordMetrics;
import android.app.trust.ITrustManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.ResolveInfo;
import android.p009os.Binder;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.UserHandle;
import android.provider.Settings;
import android.service.persistentdata.IPersistentDataBlockService;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Log;
import android.view.IOnKeyguardExitResult;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardLockedStateListener;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.IWeakEscrowTokenActivatedListener;
import com.android.internal.widget.IWeakEscrowTokenRemovedListener;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.knox.SemPersonaManager;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class KeyguardManager {
  public static final String ACTION_CONFIRM_DEVICE_CREDENTIAL =
      "android.app.action.CONFIRM_DEVICE_CREDENTIAL";
  public static final String ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER =
      "android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER";
  public static final String ACTION_CONFIRM_FRP_CREDENTIAL =
      "android.app.action.CONFIRM_FRP_CREDENTIAL";
  public static final String ACTION_CONFIRM_REMOTE_DEVICE_CREDENTIAL =
      "android.app.action.CONFIRM_REMOTE_DEVICE_CREDENTIAL";
  public static final String EXTRA_ALTERNATE_BUTTON_LABEL =
      "android.app.extra.ALTERNATE_BUTTON_LABEL";
  public static final String EXTRA_CHECKBOX_LABEL = "android.app.extra.CHECKBOX_LABEL";
  public static final String EXTRA_DESCRIPTION = "android.app.extra.DESCRIPTION";
  public static final String EXTRA_DISALLOW_BIOMETRICS_IF_POLICY_EXISTS = "check_dpm";
  public static final String EXTRA_FORCE_TASK_OVERLAY =
      "android.app.KeyguardManager.FORCE_TASK_OVERLAY";
  public static final String EXTRA_REMOTE_LOCKSCREEN_VALIDATION_SESSION =
      "android.app.extra.REMOTE_LOCKSCREEN_VALIDATION_SESSION";
  public static final String EXTRA_TITLE = "android.app.extra.TITLE";

  @SystemApi public static final int PASSWORD = 0;

  @SystemApi public static final int PATTERN = 2;

  @SystemApi public static final int PIN = 1;
  public static final int RESULT_ALTERNATE = 1;
  public static final int SMART_CARD = 3;
  private static final String TAG = "KeyguardManager";
  private final Context mContext;
  private final LockPatternUtils mLockPatternUtils;
  private final ArrayMap<WeakEscrowTokenRemovedListener, IWeakEscrowTokenRemovedListener>
      mListeners = new ArrayMap<>();
  private final IKeyguardLockedStateListener mIKeyguardLockedStateListener = new BinderC01971();
  private final ArrayMap<KeyguardLockedStateListener, Executor> mKeyguardLockedStateListeners =
      new ArrayMap<>();
  private final IWindowManager mWM = WindowManagerGlobal.getWindowManagerService();
  private final IActivityManager mAm = ActivityManager.getService();
  private final ITrustManager mTrustManager =
      ITrustManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.TRUST_SERVICE));
  private final INotificationManager mNotificationManager =
      INotificationManager.Stub.asInterface(ServiceManager.getServiceOrThrow("notification"));

  @FunctionalInterface
  public interface KeyguardLockedStateListener {
    void onKeyguardLockedStateChanged(boolean z);
  }

  @interface LockTypes {}

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

  /* renamed from: android.app.KeyguardManager$1 */
  class BinderC01971 extends IKeyguardLockedStateListener.Stub {
    BinderC01971() {}

    @Override // com.android.internal.policy.IKeyguardLockedStateListener
    public void onKeyguardLockedStateChanged(final boolean isKeyguardLocked) {
      KeyguardManager.this.mKeyguardLockedStateListeners.forEach(
          new BiConsumer() { // from class: android.app.KeyguardManager$1$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
              Executor executor = (Executor) obj2;
              executor.execute(
                  new Runnable() { // from class:
                                   // android.app.KeyguardManager$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                      KeyguardManager.KeyguardLockedStateListener.this.onKeyguardLockedStateChanged(
                          r2);
                    }
                  });
            }
          });
    }
  }

  @Deprecated
  public Intent createConfirmDeviceCredentialIntent(CharSequence title, CharSequence description) {
    if (!isDeviceSecure() && !SemPersonaManager.appliedPasswordPolicy(this.mContext.getUserId())) {
      return null;
    }
    Intent intent = new Intent(ACTION_CONFIRM_DEVICE_CREDENTIAL);
    intent.putExtra(EXTRA_TITLE, title);
    intent.putExtra(EXTRA_DESCRIPTION, description);
    intent.setPackage(getSettingsPackageForIntent(intent));
    return intent;
  }

  public Intent createConfirmDeviceCredentialIntent(
      CharSequence title, CharSequence description, int userId) {
    if (!isDeviceSecure(userId) && !SemPersonaManager.appliedPasswordPolicy(userId)) {
      return null;
    }
    Intent intent = new Intent(ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER);
    intent.putExtra(EXTRA_TITLE, title);
    intent.putExtra(EXTRA_DESCRIPTION, description);
    intent.putExtra("android.intent.extra.USER_ID", userId);
    intent.setPackage(getSettingsPackageForIntent(intent));
    return intent;
  }

  public Intent createConfirmDeviceCredentialIntent(
      CharSequence title,
      CharSequence description,
      int userId,
      boolean disallowBiometricsIfPolicyExists) {
    Intent intent = createConfirmDeviceCredentialIntent(title, description, userId);
    if (intent != null) {
      intent.putExtra(EXTRA_DISALLOW_BIOMETRICS_IF_POLICY_EXISTS, disallowBiometricsIfPolicyExists);
    }
    return intent;
  }

  @SystemApi
  public Intent createConfirmFactoryResetCredentialIntent(
      CharSequence title, CharSequence description, CharSequence alternateButtonLabel) {
    if (!LockPatternUtils.frpCredentialEnabled(this.mContext)) {
      Log.m102w(TAG, "Factory reset credentials not supported.");
      throw new UnsupportedOperationException("not supported on this device");
    }
    if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
      Log.m96e(TAG, "Factory reset credential cannot be verified after provisioning.");
      throw new IllegalStateException("must not be provisioned yet");
    }
    try {
      IPersistentDataBlockService pdb =
          IPersistentDataBlockService.Stub.asInterface(
              ServiceManager.getService(Context.PERSISTENT_DATA_BLOCK_SERVICE));
      if (pdb == null) {
        Log.m96e(TAG, "No persistent data block service");
        throw new UnsupportedOperationException("not supported on this device");
      }
      if (!pdb.hasFrpCredentialHandle()) {
        Log.m98i(TAG, "The persistent data block does not have a factory reset credential.");
        return null;
      }
      Intent intent = new Intent(ACTION_CONFIRM_FRP_CREDENTIAL);
      intent.putExtra(EXTRA_TITLE, title);
      intent.putExtra(EXTRA_DESCRIPTION, description);
      intent.putExtra(EXTRA_ALTERNATE_BUTTON_LABEL, alternateButtonLabel);
      intent.setPackage(getSettingsPackageForIntent(intent));
      return intent;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public Intent createConfirmDeviceCredentialForRemoteValidationIntent(
      RemoteLockscreenValidationSession session,
      ComponentName remoteLockscreenValidationServiceComponent,
      CharSequence title,
      CharSequence description,
      CharSequence checkboxLabel,
      CharSequence alternateButtonLabel) {
    Intent intent =
        new Intent(ACTION_CONFIRM_REMOTE_DEVICE_CREDENTIAL)
            .putExtra(EXTRA_REMOTE_LOCKSCREEN_VALIDATION_SESSION, session)
            .putExtra(Intent.EXTRA_COMPONENT_NAME, remoteLockscreenValidationServiceComponent)
            .putExtra(EXTRA_TITLE, title)
            .putExtra(EXTRA_DESCRIPTION, description)
            .putExtra(EXTRA_CHECKBOX_LABEL, checkboxLabel)
            .putExtra(EXTRA_ALTERNATE_BUTTON_LABEL, alternateButtonLabel);
    intent.setPackage(getSettingsPackageForIntent(intent));
    return intent;
  }

  @SystemApi
  public void setPrivateNotificationsAllowed(boolean allow) {
    try {
      this.mNotificationManager.setPrivateNotificationsAllowed(allow);
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
    List<ResolveInfo> resolveInfos =
        this.mContext.getPackageManager().queryIntentActivities(intent, 1048576);
    if (0 < resolveInfos.size()) {
      return resolveInfos.get(0).activityInfo.packageName;
    }
    return "com.android.settings";
  }

  @Deprecated
  public class KeyguardLock {
    private final String mTag;
    private final IBinder mToken = new Binder();

    KeyguardLock(String tag) {
      if (tag != null) {
        this.mTag = tag + "#" + Process.myPid();
      } else {
        this.mTag = "#" + Process.myPid();
      }
    }

    public void disableKeyguard() {
      try {
        EventLog.writeEvent(EventLogTags.SCREEN_DISABLED, this.mTag, 0);
        KeyguardManager.this.mWM.disableKeyguard(
            this.mToken, this.mTag, KeyguardManager.this.mContext.getUserId());
      } catch (RemoteException e) {
      }
    }

    public void reenableKeyguard() {
      try {
        EventLog.writeEvent(EventLogTags.SCREEN_DISABLED, this.mTag, 1);
        KeyguardManager.this.mWM.reenableKeyguard(
            this.mToken, KeyguardManager.this.mContext.getUserId());
      } catch (RemoteException e) {
      }
    }
  }

  public abstract static class KeyguardDismissCallback {
    public void onDismissError() {}

    public void onDismissSucceeded() {}

    public void onDismissCancelled() {}
  }

  KeyguardManager(Context context) throws ServiceManager.ServiceNotFoundException {
    this.mContext = context;
    this.mLockPatternUtils = new LockPatternUtils(context);
  }

  @Deprecated
  public KeyguardLock newKeyguardLock(String tag) {
    return new KeyguardLock(tag);
  }

  public boolean isKeyguardLocked() {
    try {
      return this.mWM.isKeyguardLocked();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean isKeyguardSecure() {
    try {
      return this.mWM.isKeyguardSecure(this.mContext.getUserId());
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean inKeyguardRestrictedInputMode() {
    return isKeyguardLocked();
  }

  public boolean isDeviceLocked() {
    return isDeviceLocked(this.mContext.getUserId());
  }

  public boolean isDeviceLocked(int userId) {
    try {
      return this.mTrustManager.isDeviceLocked(userId, this.mContext.getAssociatedDisplayId());
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean isDeviceSecure() {
    return isDeviceSecure(this.mContext.getUserId());
  }

  public boolean isDeviceSecure(int userId) {
    try {
      return this.mTrustManager.isDeviceSecure(userId, this.mContext.getAssociatedDisplayId());
    } catch (RemoteException e) {
      return false;
    }
  }

  public void requestDismissKeyguard(Activity activity, KeyguardDismissCallback callback) {
    requestDismissKeyguard(activity, null, callback);
  }

  @SystemApi
  public void requestDismissKeyguard(
      final Activity activity, CharSequence message, final KeyguardDismissCallback callback) {
    EventLog.writeEvent(
        EventLogTags.DISMISS_SCREEN, Integer.valueOf(Process.myPid()), "requestDismissKeyguard");
    ActivityClient.getInstance()
        .dismissKeyguard(
            activity.getActivityToken(),
            new IKeyguardDismissCallback.Stub() { // from class: android.app.KeyguardManager.2
              @Override // com.android.internal.policy.IKeyguardDismissCallback
              public void onDismissError() throws RemoteException {
                if (callback != null && !activity.isDestroyed()) {
                  Handler handler = activity.mHandler;
                  final KeyguardDismissCallback keyguardDismissCallback = callback;
                  Objects.requireNonNull(keyguardDismissCallback);
                  handler.post(
                      new Runnable() { // from class:
                                       // android.app.KeyguardManager$2$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                          KeyguardManager.KeyguardDismissCallback.this.onDismissError();
                        }
                      });
                }
              }

              @Override // com.android.internal.policy.IKeyguardDismissCallback
              public void onDismissSucceeded() throws RemoteException {
                if (callback != null && !activity.isDestroyed()) {
                  Handler handler = activity.mHandler;
                  final KeyguardDismissCallback keyguardDismissCallback = callback;
                  Objects.requireNonNull(keyguardDismissCallback);
                  handler.post(
                      new Runnable() { // from class:
                                       // android.app.KeyguardManager$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                          KeyguardManager.KeyguardDismissCallback.this.onDismissSucceeded();
                        }
                      });
                }
              }

              @Override // com.android.internal.policy.IKeyguardDismissCallback
              public void onDismissCancelled() throws RemoteException {
                if (callback != null && !activity.isDestroyed()) {
                  Handler handler = activity.mHandler;
                  final KeyguardDismissCallback keyguardDismissCallback = callback;
                  Objects.requireNonNull(keyguardDismissCallback);
                  handler.post(
                      new Runnable() { // from class:
                                       // android.app.KeyguardManager$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                          KeyguardManager.KeyguardDismissCallback.this.onDismissCancelled();
                        }
                      });
                }
              }
            },
            message);
  }

  @Deprecated
  public void exitKeyguardSecurely(final OnKeyguardExitResult callback) {
    try {
      this.mWM.exitKeyguardSecurely(
          new IOnKeyguardExitResult.Stub() { // from class: android.app.KeyguardManager.3
            @Override // android.view.IOnKeyguardExitResult
            public void onKeyguardExitResult(boolean success) throws RemoteException {
              OnKeyguardExitResult onKeyguardExitResult = callback;
              if (onKeyguardExitResult != null) {
                onKeyguardExitResult.onKeyguardExitResult(success);
              }
            }
          });
    } catch (RemoteException e) {
    }
  }

  public boolean checkInitialLockMethodUsage() {
    if (!hasPermission(Manifest.permission.SET_INITIAL_LOCK)) {
      throw new SecurityException("Requires SET_INITIAL_LOCK permission.");
    }
    return true;
  }

  private boolean hasPermission(String permission) {
    return this.mContext.checkCallingOrSelfPermission(permission) == 0;
  }

  @SystemApi
  public boolean isValidLockPasswordComplexity(int lockType, byte[] password, int complexity) {
    if (!checkInitialLockMethodUsage()) {
      return false;
    }
    int complexity2 = PasswordMetrics.sanitizeComplexityLevel(complexity);
    DevicePolicyManager devicePolicyManager =
        (DevicePolicyManager) this.mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
    PasswordMetrics adminMetrics =
        devicePolicyManager.getPasswordMinimumMetrics(this.mContext.getUserId());
    boolean isPinOrPattern = lockType != 0;
    return PasswordMetrics.validatePassword(adminMetrics, complexity2, isPinOrPattern, password)
            .size()
        == 0;
  }

  @SystemApi
  public int getMinLockLength(boolean isPin, int complexity) {
    if (!checkInitialLockMethodUsage()) {
      return -1;
    }
    int complexity2 = PasswordMetrics.sanitizeComplexityLevel(complexity);
    DevicePolicyManager devicePolicyManager =
        (DevicePolicyManager) this.mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
    PasswordMetrics adminMetrics =
        devicePolicyManager.getPasswordMinimumMetrics(this.mContext.getUserId());
    PasswordMetrics minMetrics = PasswordMetrics.applyComplexity(adminMetrics, isPin, complexity2);
    return minMetrics.length;
  }

  @SystemApi
  public boolean setLock(int lockType, byte[] password, int complexity) {
    boolean success;
    if (!checkInitialLockMethodUsage()) {
      return false;
    }
    int userId = this.mContext.getUserId();
    if (isDeviceSecure(userId)) {
      Log.m96e(TAG, "Password already set, rejecting call to setLock");
      return false;
    }
    if (!isValidLockPasswordComplexity(lockType, password, complexity)) {
      Log.m96e(TAG, "Password is not valid, rejecting call to setLock");
      return false;
    }
    try {
      try {
        LockscreenCredential credential = createLockscreenCredential(lockType, password);
        success =
            this.mLockPatternUtils.setLockCredential(
                credential, LockscreenCredential.createNone(), userId);
      } catch (Exception e) {
        Log.m97e(TAG, "Save lock exception", e);
        success = false;
      }
      return success;
    } finally {
      Arrays.fill(password, (byte) 0);
    }
  }

  @SystemApi
  public long addWeakEscrowToken(
      byte[] token, UserHandle user, Executor executor, WeakEscrowTokenActivatedListener listener) {
    Objects.requireNonNull(token, "Token cannot be null.");
    Objects.requireNonNull(user, "User cannot be null.");
    Objects.requireNonNull(executor, "Executor cannot be null.");
    Objects.requireNonNull(listener, "Listener cannot be null.");
    int userId = user.getIdentifier();
    IWeakEscrowTokenActivatedListener internalListener = new BinderC02004(executor, listener);
    return this.mLockPatternUtils.addWeakEscrowToken(token, userId, internalListener);
  }

  /* renamed from: android.app.KeyguardManager$4 */
  class BinderC02004 extends IWeakEscrowTokenActivatedListener.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ WeakEscrowTokenActivatedListener val$listener;

    BinderC02004(
        Executor executor, WeakEscrowTokenActivatedListener weakEscrowTokenActivatedListener) {
      this.val$executor = executor;
      this.val$listener = weakEscrowTokenActivatedListener;
    }

    @Override // com.android.internal.widget.IWeakEscrowTokenActivatedListener
    public void onWeakEscrowTokenActivated(final long handle, int userId) {
      final UserHandle user = UserHandle.m55of(userId);
      long restoreToken = Binder.clearCallingIdentity();
      try {
        Executor executor = this.val$executor;
        final WeakEscrowTokenActivatedListener weakEscrowTokenActivatedListener = this.val$listener;
        executor.execute(
            new Runnable() { // from class: android.app.KeyguardManager$4$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                KeyguardManager.WeakEscrowTokenActivatedListener.this.onWeakEscrowTokenActivated(
                    handle, user);
              }
            });
        Binder.restoreCallingIdentity(restoreToken);
        Log.m98i(KeyguardManager.TAG, "Weak escrow token activated.");
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(restoreToken);
        throw th;
      }
    }
  }

  @SystemApi
  public boolean removeWeakEscrowToken(long handle, UserHandle user) {
    Objects.requireNonNull(user, "User cannot be null.");
    return this.mLockPatternUtils.removeWeakEscrowToken(handle, user.getIdentifier());
  }

  @SystemApi
  public boolean isWeakEscrowTokenActive(long handle, UserHandle user) {
    Objects.requireNonNull(user, "User cannot be null.");
    return this.mLockPatternUtils.isWeakEscrowTokenActive(handle, user.getIdentifier());
  }

  @SystemApi
  public boolean isWeakEscrowTokenValid(long handle, byte[] token, UserHandle user) {
    Objects.requireNonNull(token, "Token cannot be null.");
    Objects.requireNonNull(user, "User cannot be null.");
    return this.mLockPatternUtils.isWeakEscrowTokenValid(handle, token, user.getIdentifier());
  }

  @SystemApi
  public boolean registerWeakEscrowTokenRemovedListener(
      Executor executor, WeakEscrowTokenRemovedListener listener) {
    Objects.requireNonNull(listener, "Listener cannot be null.");
    Objects.requireNonNull(executor, "Executor cannot be null.");
    Preconditions.checkArgument(
        !this.mListeners.containsKey(listener), "Listener already registered: %s", listener);
    IWeakEscrowTokenRemovedListener internalListener = new BinderC02015(executor, listener);
    if (this.mLockPatternUtils.registerWeakEscrowTokenRemovedListener(internalListener)) {
      this.mListeners.put(listener, internalListener);
      return true;
    }
    Log.m96e(TAG, "Listener failed to register");
    return false;
  }

  /* renamed from: android.app.KeyguardManager$5 */
  class BinderC02015 extends IWeakEscrowTokenRemovedListener.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ WeakEscrowTokenRemovedListener val$listener;

    BinderC02015(Executor executor, WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener) {
      this.val$executor = executor;
      this.val$listener = weakEscrowTokenRemovedListener;
    }

    @Override // com.android.internal.widget.IWeakEscrowTokenRemovedListener
    public void onWeakEscrowTokenRemoved(final long handle, int userId) {
      final UserHandle user = UserHandle.m55of(userId);
      long token = Binder.clearCallingIdentity();
      try {
        Executor executor = this.val$executor;
        final WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener = this.val$listener;
        executor.execute(
            new Runnable() { // from class: android.app.KeyguardManager$5$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                KeyguardManager.WeakEscrowTokenRemovedListener.this.onWeakEscrowTokenRemoved(
                    handle, user);
              }
            });
      } finally {
        Binder.restoreCallingIdentity(token);
      }
    }
  }

  @SystemApi
  public boolean unregisterWeakEscrowTokenRemovedListener(WeakEscrowTokenRemovedListener listener) {
    Objects.requireNonNull(listener, "Listener cannot be null.");
    IWeakEscrowTokenRemovedListener internalListener = this.mListeners.get(listener);
    Preconditions.checkArgument(internalListener != null, "Listener was not registered");
    if (this.mLockPatternUtils.unregisterWeakEscrowTokenRemovedListener(internalListener)) {
      this.mListeners.remove(listener);
      return true;
    }
    Log.m96e(TAG, "Listener failed to unregister.");
    return false;
  }

  public boolean setLock(
      int newLockType, byte[] newPassword, int currentLockType, byte[] currentPassword) {
    int userId = this.mContext.getUserId();
    LockscreenCredential currentCredential =
        createLockscreenCredential(currentLockType, currentPassword);
    LockscreenCredential newCredential = createLockscreenCredential(newLockType, newPassword);
    return this.mLockPatternUtils.setLockCredential(newCredential, currentCredential, userId);
  }

  public boolean checkLock(int lockType, byte[] password) {
    LockscreenCredential credential = createLockscreenCredential(lockType, password);
    VerifyCredentialResponse response =
        this.mLockPatternUtils.verifyCredential(credential, this.mContext.getUserId(), 0);
    return response != null && response.getResponseCode() == 0;
  }

  @SystemApi
  public RemoteLockscreenValidationSession startRemoteLockscreenValidation() {
    return this.mLockPatternUtils.startRemoteLockscreenValidation();
  }

  @SystemApi
  public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] encryptedCredential) {
    return this.mLockPatternUtils.validateRemoteLockscreen(encryptedCredential);
  }

  private LockscreenCredential createLockscreenCredential(int lockType, byte[] password) {
    if (password == null) {
      return LockscreenCredential.createNone();
    }
    switch (lockType) {
      case 0:
        CharSequence passwordStr = new String(password, Charset.forName("UTF-8"));
        return LockscreenCredential.createPassword(passwordStr);
      case 1:
        CharSequence pinStr = new String(password);
        return LockscreenCredential.createPin(pinStr);
      case 2:
        List<LockPatternView.Cell> pattern = LockPatternUtils.byteArrayToPattern(password);
        return LockscreenCredential.createPattern(pattern);
      default:
        throw new IllegalArgumentException("Unknown lock type " + lockType);
    }
  }

  public void addKeyguardLockedStateListener(
      Executor executor, KeyguardLockedStateListener listener) {
    synchronized (this.mKeyguardLockedStateListeners) {
      this.mKeyguardLockedStateListeners.put(listener, executor);
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

  public void removeKeyguardLockedStateListener(KeyguardLockedStateListener listener) {
    synchronized (this.mKeyguardLockedStateListeners) {
      this.mKeyguardLockedStateListeners.remove(listener);
      if (this.mKeyguardLockedStateListeners.isEmpty()) {
        try {
          this.mWM.removeKeyguardLockedStateListener(this.mIKeyguardLockedStateListener);
        } catch (RemoteException e) {
          throw e.rethrowFromSystemServer();
        }
      }
    }
  }

  public void semDismissKeyguard() {
    try {
      EventLog.writeEvent(
          EventLogTags.DISMISS_SCREEN, Integer.valueOf(Process.myPid()), "semDismissKeyguard");
      this.mWM.dismissKeyguard(null, null);
    } catch (RemoteException e) {
    }
  }

  public boolean semIsKeyguardShowingAndNotOccluded() {
    try {
      return this.mWM.isKeyguardShowingAndNotOccluded();
    } catch (RemoteException e) {
      return false;
    }
  }

  public void semSetPendingIntentAfterUnlock(PendingIntent p, Intent fillInIntent) {
    try {
      EventLog.writeEvent(
          EventLogTags.PENDING_INTENT_AFTER_UNLOCK,
          Integer.valueOf(Process.myPid()),
          "pendingIntentAfterUnlock");
      this.mWM.setPendingIntentAfterUnlock(p, fillInIntent);
    } catch (RemoteException e) {
    }
  }

  public void semStartLockscreenFingerprintAuth() {
    try {
      this.mWM.startLockscreenFingerprintAuth();
    } catch (RemoteException e) {
    }
  }
}
