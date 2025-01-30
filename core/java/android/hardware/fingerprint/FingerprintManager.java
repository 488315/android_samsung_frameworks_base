package android.hardware.fingerprint;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.biometrics.BiometricStateListener;
import android.hardware.biometrics.BiometricTestSession;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.SensorProperties;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.CancellationSignal;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.IRemoteCallback;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.PowerManager;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import android.security.identity.IdentityCredential;
import android.security.identity.PresentationSession;
import android.util.Slog;
import android.view.WindowManager;
import com.android.internal.C4337R;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;
import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;
import com.samsung.android.bio.fingerprint.SemFingerprintManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@Deprecated
/* loaded from: classes2.dex */
public class FingerprintManager implements BiometricAuthenticator, BiometricFingerprintConstants {
  private static final boolean DEBUG = true;
  public static final int ENROLL_ENROLL = 2;
  public static final int ENROLL_FIND_SENSOR = 1;
  private static final int MSG_ACQUIRED = 101;
  private static final int MSG_AUTHENTICATION_FAILED = 103;
  private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
  private static final int MSG_CHALLENGE_GENERATED = 106;
  private static final int MSG_ENROLL_RESULT = 100;
  private static final int MSG_ERROR = 104;
  private static final int MSG_FINGERPRINT_DETECTED = 107;
  private static final int MSG_POWER_BUTTON_PRESSED = 110;
  private static final int MSG_REMOVED = 105;
  private static final int MSG_UDFPS_POINTER_DOWN = 108;
  private static final int MSG_UDFPS_POINTER_UP = 109;
  public static final int SECURITY_LEVEL_CONVENIENCE = 3;
  public static final int SECURITY_LEVEL_NONE = 0;
  public static final int SECURITY_LEVEL_STRONG = 1;
  public static final int SECURITY_LEVEL_WEAK = 2;
  public static final int SEM_FLAG_AUTHENTICATION_NO_SYSTEM_UI = 32768;
  public static final int SEM_SENSOR_POSITION_DISPLAY = 2;
  public static final int SEM_SENSOR_POSITION_HOME_KEY = 1;
  public static final int SEM_SENSOR_POSITION_POWER_KEY = 4;
  public static final int SEM_SENSOR_POSITION_REAR = 3;
  public static final int SENSOR_ID_ANY = -1;
  private static final String TAG = "FingerprintManager";
  private AuthenticationCallback mAuthenticationCallback;
  private Context mContext;
  private CryptoObject mCryptoObject;
  private float[] mEnrollStageThresholds;
  private EnrollmentCallback mEnrollmentCallback;
  private FingerprintDetectionCallback mFingerprintDetectionCallback;
  private GenerateChallengeCallback mGenerateChallengeCallback;
  private Handler mHandler;
  private RemovalCallback mRemovalCallback;
  private RemoveTracker mRemoveTracker;
  private IFingerprintService mService;
  private IBinder mToken = new Binder();
  private List<FingerprintSensorPropertiesInternal> mProps = new ArrayList();
  private IFingerprintServiceReceiver mServiceReceiver =
      new IFingerprintServiceReceiver
          .Stub() { // from class: android.hardware.fingerprint.FingerprintManager.3
        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(Fingerprint fp, int remaining) {
          FingerprintManager.this.mHandler.obtainMessage(100, remaining, 0, fp).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(int acquireInfo, int vendorCode) {
          FingerprintManager.this
              .mHandler
              .obtainMessage(101, acquireInfo, vendorCode)
              .sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(Fingerprint fingerprint, int i, boolean z) {
          FingerprintManager.this
              .mHandler
              .obtainMessage(102, i, z ? 1 : 0, fingerprint)
              .sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onFingerprintDetected(int sensorId, int userId, boolean isStrongBiometric) {
          FingerprintManager.this
              .mHandler
              .obtainMessage(107, sensorId, userId, Boolean.valueOf(isStrongBiometric))
              .sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed() {
          FingerprintManager.this.mHandler.obtainMessage(103).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(int error, int vendorCode) {
          FingerprintManager.this.mHandler.obtainMessage(104, error, vendorCode).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(Fingerprint fp, int remaining) {
          FingerprintManager.this.mHandler.obtainMessage(105, remaining, 0, fp).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onChallengeGenerated(int sensorId, int userId, long challenge) {
          FingerprintManager.this
              .mHandler
              .obtainMessage(106, sensorId, userId, Long.valueOf(challenge))
              .sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerDown(int sensorId) {
          FingerprintManager.this.mHandler.obtainMessage(108, sensorId, 0).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerUp(int sensorId) {
          FingerprintManager.this.mHandler.obtainMessage(109, sensorId, 0).sendToTarget();
        }
      };

  @Retention(RetentionPolicy.SOURCE)
  public @interface AuthenticationFlag {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface EnrollReason {}

  public interface FingerprintDetectionCallback {
    void onFingerprintDetected(int i, int i2, boolean z);
  }

  public interface GenerateChallengeCallback {
    void onChallengeGenerated(int i, int i2, long j);
  }

  @Retention(RetentionPolicy.SOURCE)
  public @interface SecurityLevel {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface SensorPosition {}

  private static class RemoveTracker {
    static final int REMOVE_ALL = 2;
    static final int REMOVE_SINGLE = 1;
    final int mRemoveRequest;
    final Fingerprint mSingleFingerprint;

    @interface RemoveRequest {}

    RemoveTracker(int request, Fingerprint fingerprint) {
      this.mRemoveRequest = request;
      this.mSingleFingerprint = fingerprint;
    }
  }

  public List<SensorProperties> getSensorProperties() {
    List<SensorProperties> properties = new ArrayList<>();
    List<FingerprintSensorPropertiesInternal> internalProperties = getSensorPropertiesInternal();
    for (FingerprintSensorPropertiesInternal internalProp : internalProperties) {
      properties.add(FingerprintSensorProperties.from(internalProp));
    }
    return properties;
  }

  public BiometricTestSession createTestSession(int sensorId) {
    try {
      return new BiometricTestSession(
          this.mContext,
          sensorId,
          new BiometricTestSession
              .TestSessionProvider() { // from class:
                                       // android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda0
            @Override // android.hardware.biometrics.BiometricTestSession.TestSessionProvider
            public final ITestSession createTestSession(
                Context context, int i, ITestSessionCallback iTestSessionCallback) {
              ITestSession lambda$createTestSession$0;
              lambda$createTestSession$0 =
                  FingerprintManager.this.lambda$createTestSession$0(
                      context, i, iTestSessionCallback);
              return lambda$createTestSession$0;
            }
          });
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ ITestSession lambda$createTestSession$0(
      Context context, int sensorId1, ITestSessionCallback callback) throws RemoteException {
    return this.mService.createTestSession(sensorId1, callback, context.getOpPackageName());
  }

  private class OnEnrollCancelListener implements CancellationSignal.OnCancelListener {
    private final long mAuthRequestId;

    private OnEnrollCancelListener(long id) {
      this.mAuthRequestId = id;
    }

    @Override // android.os.CancellationSignal.OnCancelListener
    public void onCancel() {
      Slog.m113d(
          FingerprintManager.TAG,
          "Cancel fingerprint enrollment requested for: " + this.mAuthRequestId);
      FingerprintManager.this.cancelEnrollment(this.mAuthRequestId);
    }
  }

  private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
    private final long mAuthRequestId;

    OnAuthenticationCancelListener(long id) {
      this.mAuthRequestId = id;
    }

    @Override // android.os.CancellationSignal.OnCancelListener
    public void onCancel() {
      Slog.m113d(
          FingerprintManager.TAG,
          "Cancel fingerprint authentication requested for: " + this.mAuthRequestId);
      FingerprintManager.this.cancelAuthentication(this.mAuthRequestId);
    }
  }

  private class OnFingerprintDetectionCancelListener
      implements CancellationSignal.OnCancelListener {
    private final long mAuthRequestId;

    OnFingerprintDetectionCancelListener(long id) {
      this.mAuthRequestId = id;
    }

    @Override // android.os.CancellationSignal.OnCancelListener
    public void onCancel() {
      Slog.m113d(
          FingerprintManager.TAG,
          "Cancel fingerprint detect requested for: " + this.mAuthRequestId);
      FingerprintManager.this.cancelFingerprintDetect(this.mAuthRequestId);
    }
  }

  @Deprecated
  public static final class CryptoObject extends android.hardware.biometrics.CryptoObject {
    public CryptoObject(Signature signature) {
      super(signature);
    }

    public CryptoObject(Cipher cipher) {
      super(cipher);
    }

    public CryptoObject(Mac mac) {
      super(mac);
    }

    @Override // android.hardware.biometrics.CryptoObject
    public Signature getSignature() {
      return super.getSignature();
    }

    @Override // android.hardware.biometrics.CryptoObject
    public Cipher getCipher() {
      return super.getCipher();
    }

    @Override // android.hardware.biometrics.CryptoObject
    public Mac getMac() {
      return super.getMac();
    }

    @Override // android.hardware.biometrics.CryptoObject
    @Deprecated
    public IdentityCredential getIdentityCredential() {
      return super.getIdentityCredential();
    }

    @Override // android.hardware.biometrics.CryptoObject
    public PresentationSession getPresentationSession() {
      return super.getPresentationSession();
    }
  }

  @Deprecated
  public static class AuthenticationResult {
    private CryptoObject mCryptoObject;
    private Fingerprint mFingerprint;
    private boolean mIsStrongBiometric;
    private int mUserId;

    public AuthenticationResult(
        CryptoObject crypto, Fingerprint fingerprint, int userId, boolean isStrongBiometric) {
      this.mCryptoObject = crypto;
      this.mFingerprint = fingerprint;
      this.mUserId = userId;
      this.mIsStrongBiometric = isStrongBiometric;
    }

    public CryptoObject getCryptoObject() {
      return this.mCryptoObject;
    }

    public Fingerprint getFingerprint() {
      return this.mFingerprint;
    }

    public int getUserId() {
      return this.mUserId;
    }

    public boolean isStrongBiometric() {
      return this.mIsStrongBiometric;
    }
  }

  @Deprecated
  public abstract static class AuthenticationCallback
      extends BiometricAuthenticator.AuthenticationCallback {
    @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
    public void onAuthenticationError(int errorCode, CharSequence errString) {}

    @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {}

    public void onAuthenticationSucceeded(AuthenticationResult result) {}

    @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
    public void onAuthenticationFailed() {}

    @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
    public void onAuthenticationAcquired(int acquireInfo) {}

    public void onUdfpsPointerDown(int sensorId) {}

    public void onUdfpsPointerUp(int sensorId) {}
  }

  public abstract static class EnrollmentCallback {
    public void onEnrollmentError(int errMsgId, CharSequence errString) {}

    public void onEnrollmentHelp(int helpMsgId, CharSequence helpString) {}

    public void onEnrollmentProgress(int remaining) {}

    public void onAcquired(boolean isAcquiredGood) {}

    public void onPointerDown(int sensorId) {}

    public void onPointerUp(int sensorId) {}
  }

  public abstract static class RemovalCallback {
    public void onRemovalError(Fingerprint fp, int errMsgId, CharSequence errString) {}

    public void onRemovalSucceeded(Fingerprint fp, int remaining) {}
  }

  public abstract static class LockoutResetCallback {
    public void onLockoutReset(int sensorId) {}
  }

  public abstract static class SemRequestCallback {
    public void onRequested(int msgId) {}
  }

  private void useHandler(Handler handler) {
    if (handler != null) {
      this.mHandler = new MyHandler(handler.getLooper());
    } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
      this.mHandler = new MyHandler(this.mContext.getMainLooper());
    }
  }

  @Deprecated
  public void authenticate(
      CryptoObject crypto,
      CancellationSignal cancel,
      int flags,
      AuthenticationCallback callback,
      Handler handler) {
    authenticate(crypto, cancel, callback, handler, -1, this.mContext.getUserId(), flags);
  }

  @Deprecated
  public void authenticate(
      CryptoObject crypto,
      CancellationSignal cancel,
      AuthenticationCallback callback,
      Handler handler,
      int userId) {
    authenticate(crypto, cancel, callback, handler, -1, userId, 0);
  }

  @Deprecated
  public void authenticate(
      CryptoObject crypto,
      CancellationSignal cancel,
      AuthenticationCallback callback,
      Handler handler,
      int sensorId,
      int userId,
      int flags) {
    authenticate(
        crypto,
        cancel,
        callback,
        handler,
        new FingerprintAuthenticateOptions.Builder()
            .setSensorId(sensorId)
            .setUserId(userId)
            .setIgnoreEnrollmentState(flags != 0)
            .build());
  }

  public void authenticate(
      CryptoObject crypto,
      CancellationSignal cancel,
      AuthenticationCallback callback,
      Handler handler,
      FingerprintAuthenticateOptions options) {
    authenticate(crypto, cancel, callback, handler, options, false);
  }

  public void authenticate(
      CryptoObject crypto,
      CancellationSignal cancel,
      AuthenticationCallback callback,
      Handler handler,
      FingerprintAuthenticateOptions options,
      boolean allowEvenIfEncryptedOrLockdown) {
    FrameworkStatsLog.write(
        356,
        1,
        this.mContext.getApplicationInfo().uid,
        this.mContext.getApplicationInfo().targetSdkVersion);
    if (callback == null) {
      throw new IllegalArgumentException("Must supply an authentication callback");
    }
    if (cancel != null && cancel.isCanceled()) {
      Slog.m121w(TAG, "authentication already canceled");
      return;
    }
    options.setOpPackageName(this.mContext.getOpPackageName());
    options.setAttributionTag(this.mContext.getAttributionTag());
    if (this.mService != null) {
      try {
        useHandler(handler);
        this.mAuthenticationCallback = callback;
        this.mCryptoObject = crypto;
        long operationId = crypto != null ? crypto.getOpId() : 0L;
        Bundle extraInfo = new Bundle();
        SemFingerprintManager.setExtraInfo(this.mContext, extraInfo);
        extraInfo.putBoolean(
            SemFingerprintManager.EXTRA_KEY_ALLOW_AUTH_EVEN_IF_ENCRYPTED_OR_LOCKDOWN,
            allowEvenIfEncryptedOrLockdown);
        long authId =
            this.mService.semAuthenticate(
                this.mToken, operationId, this.mServiceReceiver, options, extraInfo);
        if (cancel != null) {
          cancel.setOnCancelListener(new OnAuthenticationCancelListener(authId));
        }
        if (authId < 0) {
          this.mHandler.obtainMessage(104, 5, 0).sendToTarget();
        }
      } catch (RemoteException e) {
        Slog.m122w(TAG, "Remote exception while authenticating: ", e);
        callback.onAuthenticationError(1, getErrorString(this.mContext, 1, 0));
      }
    }
  }

  public void detectFingerprint(
      CancellationSignal cancel,
      FingerprintDetectionCallback callback,
      FingerprintAuthenticateOptions options) {
    if (this.mService == null) {
      return;
    }
    if (cancel.isCanceled()) {
      Slog.m121w(TAG, "Detection already cancelled");
      return;
    }
    options.setOpPackageName(this.mContext.getOpPackageName());
    options.setAttributionTag(this.mContext.getAttributionTag());
    this.mFingerprintDetectionCallback = callback;
    try {
      long authId = this.mService.detectFingerprint(this.mToken, this.mServiceReceiver, options);
      cancel.setOnCancelListener(new OnFingerprintDetectionCancelListener(authId));
    } catch (RemoteException e) {
      Slog.m122w(TAG, "Remote exception when requesting finger detect", e);
    }
  }

  public void enroll(
      byte[] hardwareAuthToken,
      CancellationSignal cancel,
      int userId,
      EnrollmentCallback callback,
      int enrollReason) {
    if (userId == -2) {
      userId = getCurrentUserId();
    }
    if (callback == null) {
      throw new IllegalArgumentException("Must supply an enrollment callback");
    }
    if (cancel != null && cancel.isCanceled()) {
      Slog.m121w(TAG, "enrollment already canceled");
      return;
    }
    if (hardwareAuthToken == null) {
      callback.onEnrollmentError(2, getErrorString(this.mContext, 2, 0));
      return;
    }
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        this.mEnrollmentCallback = callback;
        long enrollId =
            iFingerprintService.enroll(
                this.mToken,
                hardwareAuthToken,
                userId,
                this.mServiceReceiver,
                this.mContext.getOpPackageName(),
                enrollReason);
        if (cancel != null) {
          cancel.setOnCancelListener(new OnEnrollCancelListener(enrollId));
        }
        if (enrollId < 0) {
          this.mHandler.obtainMessage(104, 5, 0).sendToTarget();
        }
      } catch (RemoteException e) {
        Slog.m122w(TAG, "Remote exception in enroll: ", e);
        callback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
      }
    }
  }

  public void generateChallenge(int sensorId, int userId, GenerateChallengeCallback callback) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        this.mGenerateChallengeCallback = callback;
        iFingerprintService.generateChallenge(
            this.mToken, sensorId, userId, this.mServiceReceiver, this.mContext.getOpPackageName());
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
  }

  public void generateChallenge(int userId, GenerateChallengeCallback callback) {
    FingerprintSensorPropertiesInternal sensorProps = getFirstFingerprintSensor();
    if (sensorProps == null) {
      Slog.m115e(TAG, "No sensors");
    } else {
      generateChallenge(sensorProps.sensorId, userId, callback);
    }
  }

  public void revokeChallenge(int userId, long challenge) {
    if (this.mService != null) {
      try {
        FingerprintSensorPropertiesInternal sensorProps = getFirstFingerprintSensor();
        if (sensorProps == null) {
          Slog.m115e(TAG, "No sensors");
        } else {
          this.mService.revokeChallenge(
              this.mToken,
              sensorProps.sensorId,
              userId,
              this.mContext.getOpPackageName(),
              challenge);
        }
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
  }

  public void resetLockout(int sensorId, int userId, byte[] hardwareAuthToken) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.resetLockout(
            this.mToken, sensorId, userId, hardwareAuthToken, this.mContext.getOpPackageName());
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
  }

  public void remove(Fingerprint fp, int userId, RemovalCallback callback) {
    if (this.mService != null) {
      try {
        this.mRemovalCallback = callback;
        this.mRemoveTracker = new RemoveTracker(1, fp);
        this.mService.remove(
            this.mToken,
            fp.getBiometricId(),
            userId,
            this.mServiceReceiver,
            this.mContext.getOpPackageName());
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
  }

  public void removeAll(int userId, RemovalCallback callback) {
    if (this.mService != null) {
      try {
        this.mRemovalCallback = callback;
        this.mRemoveTracker = new RemoveTracker(2, null);
        this.mService.removeAll(
            this.mToken, userId, this.mServiceReceiver, this.mContext.getOpPackageName());
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
  }

  public void rename(int fpId, int userId, String newName) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.rename(fpId, userId, newName);
        return;
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
    Slog.m121w(TAG, "rename(): Service not connected!");
  }

  public List<Fingerprint> getEnrolledFingerprints(int userId) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.getEnrolledFingerprints(
            userId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
    return null;
  }

  public List<Fingerprint> getEnrolledFingerprints() {
    return getEnrolledFingerprints(this.mContext.getUserId());
  }

  public boolean hasEnrolledTemplates() {
    return hasEnrolledFingerprints();
  }

  public boolean hasEnrolledTemplates(int userId) {
    return hasEnrolledFingerprints(userId);
  }

  public void setUdfpsOverlayController(IUdfpsOverlayController controller) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService == null) {
      Slog.m121w(TAG, "setUdfpsOverlayController: no fingerprint service");
      return;
    }
    try {
      iFingerprintService.setUdfpsOverlayController(controller);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void setSidefpsController(ISidefpsController controller) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService == null) {
      Slog.m121w(TAG, "setSidefpsController: no fingerprint service");
      return;
    }
    try {
      iFingerprintService.setSidefpsController(controller);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void setUdfpsOverlay(IUdfpsOverlay controller) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService == null) {
      Slog.m121w(TAG, "setUdfpsOverlay: no fingerprint service");
      return;
    }
    try {
      iFingerprintService.setUdfpsOverlay(controller);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void registerBiometricStateListener(BiometricStateListener listener) {
    try {
      this.mService.registerBiometricStateListener(listener);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void onPointerDown(long requestId, int sensorId, int x, int y, float minor, float major) {
    if (this.mService == null) {
      Slog.m121w(TAG, "onPointerDown: no fingerprint service");
      return;
    }
    PointerContext pc = new PointerContext();
    pc.f109x = x;
    pc.f110y = y;
    pc.minor = minor;
    pc.major = major;
    try {
      this.mService.onPointerDown(requestId, sensorId, pc);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void onPointerUp(long requestId, int sensorId) {
    if (this.mService == null) {
      Slog.m121w(TAG, "onPointerUp: no fingerprint service");
      return;
    }
    PointerContext pc = new PointerContext();
    try {
      this.mService.onPointerUp(requestId, sensorId, pc);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void onPointerDown(
      long requestId,
      int sensorId,
      int pointerId,
      float x,
      float y,
      float minor,
      float major,
      float orientation,
      long time,
      long gestureStart,
      boolean isAod) {
    if (this.mService == null) {
      Slog.m121w(TAG, "onPointerDown: no fingerprint service");
      return;
    }
    PointerContext pc = new PointerContext();
    pc.pointerId = pointerId;
    pc.f109x = x;
    pc.f110y = y;
    pc.minor = minor;
    pc.major = major;
    pc.orientation = orientation;
    pc.time = time;
    pc.gestureStart = gestureStart;
    pc.isAod = isAod;
    try {
      try {
        this.mService.onPointerDown(requestId, sensorId, pc);
      } catch (RemoteException e) {
        e = e;
        throw e.rethrowFromSystemServer();
      }
    } catch (RemoteException e2) {
      e = e2;
    }
  }

  public void onPointerUp(
      long requestId,
      int sensorId,
      int pointerId,
      float x,
      float y,
      float minor,
      float major,
      float orientation,
      long time,
      long gestureStart,
      boolean isAod) {
    if (this.mService == null) {
      Slog.m121w(TAG, "onPointerUp: no fingerprint service");
      return;
    }
    PointerContext pc = new PointerContext();
    pc.pointerId = pointerId;
    pc.f109x = x;
    pc.f110y = y;
    pc.minor = minor;
    pc.major = major;
    pc.orientation = orientation;
    pc.time = time;
    pc.gestureStart = gestureStart;
    pc.isAod = isAod;
    try {
      try {
        this.mService.onPointerUp(requestId, sensorId, pc);
      } catch (RemoteException e) {
        e = e;
        throw e.rethrowFromSystemServer();
      }
    } catch (RemoteException e2) {
      e = e2;
    }
  }

  public void onUiReady(long requestId, int sensorId) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService == null) {
      Slog.m121w(TAG, "onUiReady: no fingerprint service");
      return;
    }
    try {
      iFingerprintService.onUiReady(requestId, sensorId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void onPowerPressed() {
    Slog.m117i(TAG, "onPowerPressed");
    this.mHandler.obtainMessage(110).sendToTarget();
  }

  @Deprecated
  public boolean hasEnrolledFingerprints() {
    FrameworkStatsLog.write(
        356,
        2,
        this.mContext.getApplicationInfo().uid,
        this.mContext.getApplicationInfo().targetSdkVersion);
    return hasEnrolledFingerprints(UserHandle.myUserId());
  }

  public boolean hasEnrolledFingerprints(int userId) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.hasEnrolledFingerprintsDeprecated(
            userId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
    return false;
  }

  @Deprecated
  public boolean isHardwareDetected() {
    FrameworkStatsLog.write(
        356,
        3,
        this.mContext.getApplicationInfo().uid,
        this.mContext.getApplicationInfo().targetSdkVersion);
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.isHardwareDetectedDeprecated(
            this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
    Slog.m121w(TAG, "isFingerprintHardwareDetected(): Service not connected!");
    return false;
  }

  public List<FingerprintSensorPropertiesInternal> getSensorPropertiesInternal() {
    IFingerprintService iFingerprintService;
    try {
      if (this.mProps.isEmpty() && (iFingerprintService = this.mService) != null) {
        return iFingerprintService.getSensorPropertiesInternal(this.mContext.getOpPackageName());
      }
      return this.mProps;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public boolean isPowerbuttonFps() {
    FingerprintSensorPropertiesInternal sensorProps = getFirstFingerprintSensor();
    return sensorProps != null && sensorProps.sensorType == 4;
  }

  public void addAuthenticatorsRegisteredCallback(
      IFingerprintAuthenticatorsRegisteredCallback callback) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.addAuthenticatorsRegisteredCallback(callback);
        return;
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
    Slog.m121w(TAG, "addProvidersAvailableCallback(): Service not connected!");
  }

  public int getLockoutModeForUser(int sensorId, int userId) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.getLockoutModeForUser(sensorId, userId);
      } catch (RemoteException e) {
        e.rethrowFromSystemServer();
        return 0;
      }
    }
    return 0;
  }

  public void scheduleWatchdog() {
    try {
      this.mService.scheduleWatchdog();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void addLockoutResetCallback(LockoutResetCallback callback) {
    if (this.mService != null) {
      try {
        PowerManager powerManager =
            (PowerManager) this.mContext.getSystemService(PowerManager.class);
        this.mService.addLockoutResetCallback(
            new BinderC11531(powerManager, callback), this.mContext.getOpPackageName());
        return;
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
    Slog.m121w(TAG, "addLockoutResetCallback(): Service not connected!");
  }

  /* renamed from: android.hardware.fingerprint.FingerprintManager$1 */
  class BinderC11531 extends IBiometricServiceLockoutResetCallback.Stub {
    final /* synthetic */ LockoutResetCallback val$callback;
    final /* synthetic */ PowerManager val$powerManager;

    BinderC11531(PowerManager powerManager, LockoutResetCallback lockoutResetCallback) {
      this.val$powerManager = powerManager;
      this.val$callback = lockoutResetCallback;
    }

    @Override // android.hardware.biometrics.IBiometricServiceLockoutResetCallback
    public void onLockoutReset(final int sensorId, IRemoteCallback serverCallback)
        throws RemoteException {
      try {
        final PowerManager.WakeLock wakeLock =
            this.val$powerManager.newWakeLock(1, "lockoutResetCallback");
        wakeLock.acquire();
        Handler handler = FingerprintManager.this.mHandler;
        final LockoutResetCallback lockoutResetCallback = this.val$callback;
        handler.post(
            new Runnable() { // from class:
                             // android.hardware.fingerprint.FingerprintManager$1$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                FingerprintManager.BinderC11531.lambda$onLockoutReset$0(
                    FingerprintManager.LockoutResetCallback.this, sensorId, wakeLock);
              }
            });
      } finally {
        serverCallback.sendResult(null);
      }
    }

    static /* synthetic */ void lambda$onLockoutReset$0(
        LockoutResetCallback callback, int sensorId, PowerManager.WakeLock wakeLock) {
      try {
        callback.onLockoutReset(sensorId);
      } finally {
        wakeLock.release();
      }
    }
  }

  private class MyHandler extends Handler {
    private MyHandler(Context context) {
      super(context.getMainLooper());
    }

    private MyHandler(Looper looper) {
      super(looper);
    }

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      Slog.m117i(
          FingerprintManager.TAG,
          "handleMessage = " + msg.what + ", " + msg.arg1 + ", " + msg.arg2);
      switch (msg.what) {
        case 100:
          FingerprintManager.this.sendEnrollResult((Fingerprint) msg.obj, msg.arg1);
          break;
        case 101:
          FingerprintManager.this.sendAcquiredResult(msg.arg1, msg.arg2);
          break;
        case 102:
          FingerprintManager.this.sendAuthenticatedSucceeded(
              (Fingerprint) msg.obj, msg.arg1, msg.arg2 == 1);
          break;
        case 103:
          FingerprintManager.this.sendAuthenticatedFailed();
          break;
        case 104:
          FingerprintManager.this.sendErrorResult(msg.arg1, msg.arg2);
          break;
        case 105:
          FingerprintManager.this.sendRemovedResult((Fingerprint) msg.obj, msg.arg1);
          break;
        case 106:
          FingerprintManager.this.sendChallengeGenerated(
              msg.arg1, msg.arg2, ((Long) msg.obj).longValue());
          break;
        case 107:
          FingerprintManager.this.sendFingerprintDetected(
              msg.arg1, msg.arg2, ((Boolean) msg.obj).booleanValue());
          break;
        case 108:
          FingerprintManager.this.sendUdfpsPointerDown(msg.arg1);
          break;
        case 109:
          FingerprintManager.this.sendUdfpsPointerUp(msg.arg1);
          break;
        case 110:
          FingerprintManager.this.sendPowerPressed();
          break;
        default:
          Slog.m121w(FingerprintManager.TAG, "Unknown message: " + msg.what);
          break;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendRemovedResult(Fingerprint fingerprint, int remaining) {
    if (this.mRemovalCallback == null) {
      return;
    }
    RemoveTracker removeTracker = this.mRemoveTracker;
    if (removeTracker == null) {
      Slog.m121w(TAG, "Removal tracker is null");
      return;
    }
    if (removeTracker.mRemoveRequest == 1) {
      if (fingerprint == null) {
        Slog.m115e(TAG, "Received MSG_REMOVED, but fingerprint is null");
        return;
      }
      if (this.mRemoveTracker.mSingleFingerprint == null) {
        Slog.m115e(TAG, "Missing fingerprint");
        return;
      }
      int fingerId = fingerprint.getBiometricId();
      int reqFingerId = this.mRemoveTracker.mSingleFingerprint.getBiometricId();
      if (reqFingerId != 0 && fingerId != 0 && fingerId != reqFingerId) {
        Slog.m121w(TAG, "Finger id didn't match: " + fingerId + " != " + reqFingerId);
        return;
      }
    }
    this.mRemovalCallback.onRemovalSucceeded(fingerprint, remaining);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendEnrollResult(Fingerprint fp, int remaining) {
    EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
    if (enrollmentCallback != null) {
      enrollmentCallback.onEnrollmentProgress(remaining);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendAuthenticatedSucceeded(Fingerprint fp, int userId, boolean isStrongBiometric) {
    if (this.mAuthenticationCallback != null) {
      AuthenticationResult result =
          new AuthenticationResult(this.mCryptoObject, fp, userId, isStrongBiometric);
      this.mAuthenticationCallback.onAuthenticationSucceeded(result);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendAuthenticatedFailed() {
    AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
    if (authenticationCallback != null) {
      authenticationCallback.onAuthenticationFailed();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendAcquiredResult(int acquireInfo, int vendorCode) {
    AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
    if (authenticationCallback != null) {
      authenticationCallback.onAuthenticationAcquired(acquireInfo);
    }
    EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
    if (enrollmentCallback != null && acquireInfo != 7) {
      enrollmentCallback.onAcquired(acquireInfo == 0);
    }
    String msg = getAcquiredString(this.mContext, acquireInfo, vendorCode);
    int clientInfo = acquireInfo == 6 ? vendorCode : acquireInfo;
    EnrollmentCallback enrollmentCallback2 = this.mEnrollmentCallback;
    if (enrollmentCallback2 != null) {
      enrollmentCallback2.onEnrollmentHelp(clientInfo, msg);
      return;
    }
    AuthenticationCallback authenticationCallback2 = this.mAuthenticationCallback;
    if (authenticationCallback2 != null && acquireInfo != 7 && msg != null) {
      authenticationCallback2.onAuthenticationHelp(clientInfo, msg);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendErrorResult(int errMsgId, int vendorCode) {
    int clientErrMsgId = errMsgId == 8 ? vendorCode : errMsgId;
    EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
    if (enrollmentCallback != null) {
      enrollmentCallback.onEnrollmentError(
          clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
      this.mEnrollmentCallback = null;
      return;
    }
    AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
    if (authenticationCallback != null) {
      authenticationCallback.onAuthenticationError(
          clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
    } else if (this.mRemovalCallback != null) {
      RemoveTracker removeTracker = this.mRemoveTracker;
      Fingerprint fp = removeTracker != null ? removeTracker.mSingleFingerprint : null;
      this.mRemovalCallback.onRemovalError(
          fp, clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendChallengeGenerated(int sensorId, int userId, long challenge) {
    GenerateChallengeCallback generateChallengeCallback = this.mGenerateChallengeCallback;
    if (generateChallengeCallback == null) {
      Slog.m115e(TAG, "sendChallengeGenerated, callback null");
    } else {
      generateChallengeCallback.onChallengeGenerated(sensorId, userId, challenge);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendFingerprintDetected(int sensorId, int userId, boolean isStrongBiometric) {
    FingerprintDetectionCallback fingerprintDetectionCallback = this.mFingerprintDetectionCallback;
    if (fingerprintDetectionCallback == null) {
      Slog.m115e(TAG, "sendFingerprintDetected, callback null");
    } else {
      fingerprintDetectionCallback.onFingerprintDetected(sensorId, userId, isStrongBiometric);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendUdfpsPointerDown(int sensorId) {
    AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
    if (authenticationCallback == null) {
      Slog.m115e(TAG, "sendUdfpsPointerDown, callback null");
    } else {
      authenticationCallback.onUdfpsPointerDown(sensorId);
    }
    EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
    if (enrollmentCallback != null) {
      enrollmentCallback.onPointerDown(sensorId);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendUdfpsPointerUp(int sensorId) {
    AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
    if (authenticationCallback == null) {
      Slog.m115e(TAG, "sendUdfpsPointerUp, callback null");
    } else {
      authenticationCallback.onUdfpsPointerUp(sensorId);
    }
    EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
    if (enrollmentCallback != null) {
      enrollmentCallback.onPointerUp(sensorId);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendPowerPressed() {
    try {
      this.mService.onPowerPressed();
    } catch (RemoteException e) {
      Slog.m116e(TAG, "Error sending power press", e);
    }
  }

  public FingerprintManager(Context context, IFingerprintService service) {
    this.mContext = context;
    this.mService = service;
    if (service == null) {
      Slog.m119v(TAG, "FingerprintService was null");
    }
    this.mHandler = new MyHandler(context);
    if (context.checkCallingOrSelfPermission(Manifest.permission.USE_BIOMETRIC_INTERNAL) == 0) {
      addAuthenticatorsRegisteredCallback(
          new IFingerprintAuthenticatorsRegisteredCallback
              .Stub() { // from class: android.hardware.fingerprint.FingerprintManager.2
            @Override // android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback
            public void onAllAuthenticatorsRegistered(
                List<FingerprintSensorPropertiesInternal> sensors) {
              FingerprintManager.this.mProps = sensors;
            }
          });
    }
  }

  private int getCurrentUserId() {
    try {
      return ActivityManager.getService().getCurrentUser().f54id;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  private FingerprintSensorPropertiesInternal getFirstFingerprintSensor() {
    List<FingerprintSensorPropertiesInternal> allSensors = getSensorPropertiesInternal();
    if (allSensors.isEmpty()) {
      return null;
    }
    return allSensors.get(0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void cancelEnrollment(long requestId) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.cancelEnrollment(this.mToken, requestId);
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void cancelAuthentication(long requestId) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.cancelAuthentication(
            this.mToken,
            this.mContext.getOpPackageName(),
            this.mContext.getAttributionTag(),
            requestId);
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void cancelFingerprintDetect(long requestId) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService == null) {
      return;
    }
    try {
      iFingerprintService.cancelFingerprintDetect(
          this.mToken, this.mContext.getOpPackageName(), requestId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int getEnrollStageCount() {
    if (this.mEnrollStageThresholds == null) {
      this.mEnrollStageThresholds = createEnrollStageThresholds(this.mContext);
    }
    return this.mEnrollStageThresholds.length + 1;
  }

  public float getEnrollStageThreshold(int index) {
    if (this.mEnrollStageThresholds == null) {
      this.mEnrollStageThresholds = createEnrollStageThresholds(this.mContext);
    }
    if (index >= 0) {
      float[] fArr = this.mEnrollStageThresholds;
      if (index <= fArr.length) {
        if (index == fArr.length) {
          return 1.0f;
        }
        return fArr[index];
      }
    }
    Slog.m121w(TAG, "Unsupported enroll stage index: " + index);
    return index < 0 ? 0.0f : 1.0f;
  }

  private float[] createEnrollStageThresholds(Context context) {
    String[] enrollStageThresholdStrings;
    if (isPowerbuttonFps()) {
      enrollStageThresholdStrings =
          context.getResources().getStringArray(C4337R.array.config_sfps_enroll_stage_thresholds);
    } else {
      enrollStageThresholdStrings =
          context.getResources().getStringArray(C4337R.array.config_udfps_enroll_stage_thresholds);
    }
    float[] enrollStageThresholds = new float[enrollStageThresholdStrings.length];
    for (int i = 0; i < enrollStageThresholds.length; i++) {
      enrollStageThresholds[i] = Float.parseFloat(enrollStageThresholdStrings[i]);
    }
    return enrollStageThresholds;
  }

  public static String getErrorString(Context context, int errMsg, int vendorCode) {
    String msg = semGetErrorString(context, errMsg, vendorCode);
    if (msg != null) {
      return msg;
    }
    switch (errMsg) {
      case 1:
        return context.getString(C4337R.string.fingerprint_error_hw_not_available);
      case 2:
        return context.getString(C4337R.string.fingerprint_error_unable_to_process);
      case 3:
        return context.getString(C4337R.string.fingerprint_error_timeout);
      case 4:
        return context.getString(C4337R.string.fingerprint_error_no_space);
      case 5:
        return context.getString(C4337R.string.fingerprint_error_canceled);
      case 6:
      case 13:
      case 14:
      case 16:
      case 17:
      default:
        Slog.m121w(TAG, "Invalid error message: " + errMsg + ", " + vendorCode);
        return context.getString(C4337R.string.fingerprint_error_vendor_unknown);
      case 7:
        return context.getString(C4337R.string.fingerprint_error_lockout);
      case 8:
        return context.getString(C4337R.string.fingerprint_error_unable_to_process);
      case 9:
        return context.getString(C4337R.string.fingerprint_error_lockout_permanent);
      case 10:
        return context.getString(C4337R.string.fingerprint_error_user_canceled);
      case 11:
        return context.getString(C4337R.string.fingerprint_error_no_fingerprints);
      case 12:
        return context.getString(C4337R.string.fingerprint_error_hw_not_present);
      case 15:
        return context.getString(C4337R.string.fingerprint_error_security_update_required);
      case 18:
        return context.getString(C4337R.string.fingerprint_error_bad_calibration);
      case 19:
        return context.getString(C4337R.string.fingerprint_error_power_pressed);
    }
  }

  public static String getAcquiredString(Context context, int acquireInfo, int vendorCode) {
    String msg = semGetAcquiredString(context, acquireInfo, vendorCode);
    if (msg != null || acquireInfo == 6) {
      return msg;
    }
    switch (acquireInfo) {
      case 0:
        return null;
      case 1:
        return context.getString(C4337R.string.fingerprint_acquired_partial);
      case 2:
        return context.getString(C4337R.string.fingerprint_acquired_insufficient);
      case 3:
        return context.getString(C4337R.string.fingerprint_acquired_imager_dirty);
      case 4:
        return context.getString(C4337R.string.fingerprint_acquired_too_slow);
      case 5:
        return context.getString(C4337R.string.fingerprint_acquired_too_fast);
      case 6:
        String[] msgArray =
            context.getResources().getStringArray(C4337R.array.fingerprint_acquired_vendor);
        if (vendorCode < msgArray.length) {
          return msgArray[vendorCode];
        }
        break;
      case 7:
        return null;
      case 9:
        return context.getString(C4337R.string.fingerprint_acquired_immobile);
      case 10:
        return context.getString(C4337R.string.fingerprint_acquired_too_bright);
      case 11:
        return context.getString(C4337R.string.fingerprint_acquired_power_press);
    }
    Slog.m121w(TAG, "Invalid acquired message: " + acquireInfo + ", " + vendorCode);
    return null;
  }

  public static int semGetTransitionEffectValue() {
    if ("google_touch_display_optical,settings=3,screen_off".contains("transition_effect_on")) {
      return 1;
    }
    if ("google_touch_display_optical,settings=3,screen_off".contains("transition_effect_off")) {
      return 0;
    }
    return -1;
  }

  public static int semGetSensorPosition() {
    if ("google_touch_display_optical,settings=3,screen_off".contains("touch_display")) {
      return 2;
    }
    if ("google_touch_display_optical,settings=3,screen_off".contains("touch_rear")) {
      return 3;
    }
    if ("google_touch_display_optical,settings=3,screen_off".contains("touch_side")) {
      return 4;
    }
    return 1;
  }

  public int semGetMaxEnrollmentNumber() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetMaxEnrollmentNumber();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semGetMaxEnrollmentNumber: " + e.getMessage());
        return 3;
      }
    }
    return 3;
  }

  public boolean semHasFeature(int feature) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semHasFeature(feature);
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semHasFeature: " + e.getMessage());
        return false;
      }
    }
    return false;
  }

  public IFingerprintService semGetService() {
    return this.mService;
  }

  public int semForceCBGE() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.semForceCBGE();
        return 0;
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semForceCBGE: " + e.getMessage());
        return -2;
      }
    }
    return -2;
  }

  public boolean semIsEnrollSession() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semIsEnrollSession();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semIsEnrollSession: " + e.getMessage());
        return false;
      }
    }
    return false;
  }

  public boolean semIsTemplateDbCorrupted() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semIsTemplateDbCorrupted();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semIsTemplateDbCorrupted: " + e.getMessage());
        return false;
      }
    }
    return false;
  }

  public int semGetSensorStatus() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetSensorStatus();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semGetSensorStatus: " + e.getMessage());
        return 0;
      }
    }
    return 0;
  }

  public boolean semPauseEnroll() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semPauseEnroll();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semPauseEnroll: " + e.getMessage());
        return false;
      }
    }
    return false;
  }

  public boolean semResumeEnroll() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semResumeEnroll();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semResumeEnroll: " + e.getMessage());
        return false;
      }
    }
    return false;
  }

  public boolean requestSessionOpen() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semOpenSession();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "requestSessionOpen: " + e.getMessage());
        return false;
      }
    }
    return false;
  }

  public String semGetSensorInfo() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetSensorInfo();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semGetSensorInfo: " + e.getMessage());
        return "";
      }
    }
    return "";
  }

  public String[] semGetUserIdList() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetUserIdList();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semGetUserIdList: " + e.getMessage());
      }
    }
    return new String[0];
  }

  public String semGetDaemonVersion() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetDaemonVersion();
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semGetDaemonVersion: " + e.getMessage());
        return "";
      }
    }
    return "";
  }

  public int semRunSensorTest(int cmd, int param, SemRequestCallback callback) {
    if (this.mService != null) {
      if (callback == null) {
        throw new IllegalArgumentException("Must supply an Request callback");
      }
      try {
        ISemFingerprintRequestCallback requestCallback = new BinderC11564(callback);
        return this.mService.semRunSensorTest(this.mToken, cmd, param, requestCallback);
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semRunSensorTest: " + e.getMessage());
        return -2;
      }
    }
    return -2;
  }

  /* renamed from: android.hardware.fingerprint.FingerprintManager$4 */
  class BinderC11564 extends ISemFingerprintRequestCallback.Stub {
    final /* synthetic */ SemRequestCallback val$callback;

    BinderC11564(SemRequestCallback semRequestCallback) {
      this.val$callback = semRequestCallback;
    }

    @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
    public void onResult(final int code) {
      Handler handler = FingerprintManager.this.mHandler;
      final SemRequestCallback semRequestCallback = this.val$callback;
      handler.post(
          new Runnable() { // from class:
                           // android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintManager.SemRequestCallback.this.onRequested(code);
            }
          });
    }
  }

  public int semGetSensorTestResult(byte[] outBuffer) {
    if (outBuffer == null) {
      return -1;
    }
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetSensorTestResult(outBuffer);
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semGetSensorTestResult: " + e.getMessage());
        return -2;
      }
    }
    return -2;
  }

  public int semSetScreenStatus(int screenStatus) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semSetScreenStatus(screenStatus);
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semSetScreenStatus: " + e.getMessage());
        return 0;
      }
    }
    return 0;
  }

  public int semShowBouncerScreen(int showStatus) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semShowBouncerScreen(showStatus);
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semShowBouncerScreen: " + e.getMessage());
        return 0;
      }
    }
    return 0;
  }

  public IBinder semAddMaskView() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semAddMaskView(this.mToken, this.mContext.getOpPackageName());
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semAddMaskView: " + e.getMessage());
        return null;
      }
    }
    return null;
  }

  public int semRemoveMaskView(IBinder token) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semRemoveMaskView(token, this.mContext.getOpPackageName());
      } catch (RemoteException e) {
        Slog.m121w(TAG, "semRemoveMaskView: " + e.getMessage());
        return -2;
      }
    }
    return -2;
  }

  public IBinder semRegisterFingerprintViewListener(SemFingerprintViewListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Must supply an listener");
    }
    if (this.mService != null) {
      try {
        ISemFingerprintAodController aodController = new BinderC11575(listener);
        this.mService.semRegisterAodController(this.mToken, aodController);
        return this.mToken;
      } catch (RemoteException re) {
        Slog.m122w(TAG, "semRegisterAodController : ", re);
      }
    } else {
      Slog.m121w(TAG, "semRegisterFingerprintViewListener : Service not connected!");
    }
    return this.mToken;
  }

  /* renamed from: android.hardware.fingerprint.FingerprintManager$5 */
  class BinderC11575 extends ISemFingerprintAodController.Stub {
    final /* synthetic */ SemFingerprintViewListener val$listener;

    BinderC11575(SemFingerprintViewListener semFingerprintViewListener) {
      this.val$listener = semFingerprintViewListener;
    }

    @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
    public void turnOnDozeMode() {
      Handler handler = FingerprintManager.this.mHandler;
      final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
      handler.post(
          new Runnable() { // from class:
                           // android.hardware.fingerprint.FingerprintManager$5$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintManager.BinderC11575.lambda$turnOnDozeMode$0(
                  SemFingerprintViewListener.this);
            }
          });
    }

    static /* synthetic */ void lambda$turnOnDozeMode$0(SemFingerprintViewListener listener) {
      Slog.m117i(FingerprintManager.TAG, "deliver event to AOD: turnOnDozeMode");
      listener.onStarted();
    }

    @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
    public void turnOffDozeMode() {
      Handler handler = FingerprintManager.this.mHandler;
      final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
      handler.post(
          new Runnable() { // from class:
                           // android.hardware.fingerprint.FingerprintManager$5$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintManager.BinderC11575.lambda$turnOffDozeMode$1(
                  SemFingerprintViewListener.this);
            }
          });
    }

    static /* synthetic */ void lambda$turnOffDozeMode$1(SemFingerprintViewListener listener) {
      Slog.m117i(FingerprintManager.TAG, "deliver event to AOD: turnOffDozeMode");
      listener.onStopped();
    }

    @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
    public void turnOnDozeHlpmMode() {
      Handler handler = FingerprintManager.this.mHandler;
      final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
      handler.post(
          new Runnable() { // from class:
                           // android.hardware.fingerprint.FingerprintManager$5$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintManager.BinderC11575.lambda$turnOnDozeHlpmMode$2(
                  SemFingerprintViewListener.this);
            }
          });
    }

    static /* synthetic */ void lambda$turnOnDozeHlpmMode$2(SemFingerprintViewListener listener) {
      Slog.m117i(FingerprintManager.TAG, "deliver event to AOD: turnOnDozeHlpmMode");
      listener.onShow();
    }

    @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
    public void turnOffDozeHlpmMode() {
      Handler handler = FingerprintManager.this.mHandler;
      final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
      handler.post(
          new Runnable() { // from class:
                           // android.hardware.fingerprint.FingerprintManager$5$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintManager.BinderC11575.lambda$turnOffDozeHlpmMode$3(
                  SemFingerprintViewListener.this);
            }
          });
    }

    static /* synthetic */ void lambda$turnOffDozeHlpmMode$3(SemFingerprintViewListener listener) {
      Slog.m117i(FingerprintManager.TAG, "deliver event to AOD: turnOffDozeHlpmMode");
      listener.onDismiss();
    }

    @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
    public void hideAodScreen() {
      Handler handler = FingerprintManager.this.mHandler;
      final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
      handler.post(
          new Runnable() { // from class:
                           // android.hardware.fingerprint.FingerprintManager$5$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintManager.BinderC11575.lambda$hideAodScreen$4(
                  SemFingerprintViewListener.this);
            }
          });
    }

    static /* synthetic */ void lambda$hideAodScreen$4(SemFingerprintViewListener listener) {
      Slog.m117i(FingerprintManager.TAG, "deliver event to AOD: hideAodScreen");
      listener.onAuthenticationSucceeded();
    }
  }

  public void semUnregisterFingerprintViewListener(IBinder token) {
    if (token == null) {
      throw new IllegalArgumentException("Must supply an token");
    }
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.semUnregisterAodController(token);
        return;
      } catch (RemoteException re) {
        Slog.m122w(TAG, "semUnregisterAodController : ", re);
        return;
      }
    }
    Slog.m121w(TAG, "semUnregisterFingerprintViewListener : Service not connected!");
  }

  public Rect semGetSensorAreaInDisplay() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetSensorAreaInDisplay(0, 0, null);
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semGetSensorAreaInDisplay: ", e);
      }
    }
    return new Rect();
  }

  public Rect semGetFingerIconRectInDisplay() {
    if (this.mService != null) {
      try {
        WindowManager wm = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getRealSize(size);
        return this.mService.semGetSensorAreaInDisplay(
            1, wm.getDefaultDisplay().getRotation(), size);
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semGetFingerIconRectInDisplay: ", e);
      }
    }
    return new Rect();
  }

  public int semGetIconBottomMargin() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetIconBottomMargin();
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semGetIconBottomMargin: ", e);
        return 0;
      }
    }
    return 0;
  }

  public void semMoveSensorIconInDisplay(int x, int y) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.semMoveSensorIconInDisplay(x, y);
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semMoveSensorIconInDisplay: ", e);
      }
    }
  }

  public void semSetFlagForIFAA(int flag, String targetAppPackageName) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.semSetFlagForIFAA(flag, targetAppPackageName);
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semSetFlagForIFAA: ", e);
      }
    }
  }

  public int semGetSecurityLevel() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetSecurityLevel();
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semGetSecurityLevel: ", e);
        return 0;
      }
    }
    return 0;
  }

  public String semGetTrustAppVersion() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetTrustAppVersion();
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semGetTrustAppVersion: ", e);
        return "";
      }
    }
    return "";
  }

  public void semUpdateTrustApp(String path, SemRequestCallback callback) {
    if (this.mService != null) {
      if (callback == null) {
        throw new IllegalArgumentException("Must supply an Request callback");
      }
      try {
        ISemFingerprintRequestCallback requestCallback = new BinderC11586(callback);
        this.mService.semUpdateTrustApp(path, requestCallback, this.mContext.getOpPackageName());
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semUpdateTrustApp: ", e);
      }
    }
  }

  /* renamed from: android.hardware.fingerprint.FingerprintManager$6 */
  class BinderC11586 extends ISemFingerprintRequestCallback.Stub {
    final /* synthetic */ SemRequestCallback val$callback;

    BinderC11586(SemRequestCallback semRequestCallback) {
      this.val$callback = semRequestCallback;
    }

    @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
    public void onResult(final int code) {
      Handler handler = FingerprintManager.this.mHandler;
      final SemRequestCallback semRequestCallback = this.val$callback;
      handler.post(
          new Runnable() { // from class:
                           // android.hardware.fingerprint.FingerprintManager$6$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintManager.SemRequestCallback.this.onRequested(code);
            }
          });
    }
  }

  public void semSetFodStrictMode(boolean isStrictMode) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.semSetFodStrictMode(isStrictMode);
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semSetFodStrictMode: ", e);
      }
    }
  }

  public int semSetCalibrationMode(int param) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        iFingerprintService.semSetCalibrationMode(
            this.mToken, param, this.mContext.getOpPackageName());
        return -1;
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semSetCalibrationMode: ", e);
        return -1;
      }
    }
    return -1;
  }

  public int semProcessFido(int userId, byte[] in, byte[] out) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semProcessFido(
            userId, in, out, this.mContext.getOpPackageName());
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semProcessFido: ", e);
        return -1;
      }
    }
    return -1;
  }

  public int semGetRemainingLockoutTime(int userId) {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semGetRemainingLockoutTime(userId);
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semGetRemainingLockoutTime: ", e);
        return 0;
      }
    }
    return 0;
  }

  public boolean semCanChangeDeviceColorMode() {
    IFingerprintService iFingerprintService = this.mService;
    if (iFingerprintService != null) {
      try {
        return iFingerprintService.semCanChangeDeviceColorMode();
      } catch (RemoteException e) {
        Slog.m116e(TAG, "semCanChangeDeviceColorMode: ", e);
        return true;
      }
    }
    return true;
  }

  public int request(
      int cmd, byte[] inputBuf, byte[] outputBuf, int inParam, SemRequestCallback callback) {
    return request(cmd, inputBuf, outputBuf, inParam, callback, this.mContext.getUserId());
  }

  public int request(
      int cmd,
      byte[] inputBuf,
      byte[] outputBuf,
      int inParam,
      SemRequestCallback callback,
      int userId) {
    byte[] inputBuf2;
    byte[] outputBuf2;
    switch (cmd) {
      case 9:
        return semProcessFido(userId, inputBuf, outputBuf);
      case 1000:
        semRemove(userId, inParam, callback);
        return 0;
      case 1016:
        return semSetCalibrationMode(inParam);
      case 10000:
        if (outputBuf == null || outputBuf.length <= 0) {
          return -1;
        }
        String taVersion = semGetTrustAppVersion();
        byte[] src = taVersion.getBytes(StandardCharsets.UTF_8);
        int size = Math.min(src.length, outputBuf.length);
        System.arraycopy(src, 0, outputBuf, 0, size);
        return size;
      case 10001:
        if (inputBuf == null || inputBuf.length <= 0) {
          return -1;
        }
        semUpdateTrustApp(new String(inputBuf, StandardCharsets.UTF_8), callback);
        return 0;
      default:
        if (this.mService != null) {
          if (inputBuf != null) {
            inputBuf2 = inputBuf;
          } else {
            try {
              inputBuf2 = new byte[0];
            } catch (RemoteException e) {
              e = e;
              Slog.m120v(TAG, "Remote exception in request : ", e);
              return -2;
            }
          }
          if (outputBuf != null) {
            outputBuf2 = outputBuf;
          } else {
            try {
              outputBuf2 = new byte[0];
            } catch (RemoteException e2) {
              e = e2;
              Slog.m120v(TAG, "Remote exception in request : ", e);
              return -2;
            }
          }
          ISemFingerprintRequestCallback requestCallback = null;
          if (callback != null) {
            try {
              requestCallback = new BinderC11597(callback);
            } catch (RemoteException e3) {
              e = e3;
              Slog.m120v(TAG, "Remote exception in request : ", e);
              return -2;
            }
          }
          return this.mService.semRequest(
              this.mToken,
              cmd,
              inputBuf2,
              outputBuf2,
              inParam,
              userId,
              this.mContext.getOpPackageName(),
              requestCallback);
        }
        Slog.m121w(TAG, "request : Service not connected!");
        return -2;
    }
  }

  /* renamed from: android.hardware.fingerprint.FingerprintManager$7 */
  class BinderC11597 extends ISemFingerprintRequestCallback.Stub {
    final /* synthetic */ SemRequestCallback val$callback;

    BinderC11597(SemRequestCallback semRequestCallback) {
      this.val$callback = semRequestCallback;
    }

    @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
    public void onResult(final int code) {
      Handler handler = FingerprintManager.this.mHandler;
      final SemRequestCallback semRequestCallback = this.val$callback;
      handler.post(
          new Runnable() { // from class:
                           // android.hardware.fingerprint.FingerprintManager$7$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintManager.SemRequestCallback.this.onRequested(code);
            }
          });
    }
  }

  private void semRemove(int userId, int fingerId, final SemRequestCallback requestCallback) {
    if (this.mContext.checkSelfPermission(Manifest.permission.MANAGE_FINGERPRINT) == -1) {
      throw new SecurityException("Must have android.permission.MANAGE_FINGERPRINT permission.");
    }
    RemovalCallback removalCallback =
        new RemovalCallback() { // from class: android.hardware.fingerprint.FingerprintManager.8
          @Override // android.hardware.fingerprint.FingerprintManager.RemovalCallback
          public void onRemovalError(Fingerprint fp, int errMsgId, CharSequence errString) {
            Slog.m113d(FingerprintManager.TAG, "semRemove: removal error");
            SemRequestCallback semRequestCallback = requestCallback;
            if (semRequestCallback != null) {
              semRequestCallback.onRequested(6);
            }
          }

          @Override // android.hardware.fingerprint.FingerprintManager.RemovalCallback
          public void onRemovalSucceeded(Fingerprint fp, int remaining) {
            Slog.m113d(FingerprintManager.TAG, "semRemove: removal succeeded");
            SemRequestCallback semRequestCallback = requestCallback;
            if (semRequestCallback != null) {
              semRequestCallback.onRequested(0);
            }
          }
        };
    if (fingerId == -1) {
      removeAll(userId, removalCallback);
    } else {
      remove(new Fingerprint("", fingerId, 0L), userId, removalCallback);
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  private static String semGetAcquiredString(Context context, int acquireInfo, int vendorCode) {
    try {
    } catch (Exception e) {
      Slog.m121w(TAG, "semGetAcquiredString : Exception = " + e);
    }
    switch (acquireInfo) {
      case 0:
        return null;
      case 1:
        return context.getString(C4337R.string.sem_fingerprint_acquired_partial);
      case 2:
        return context.getString(C4337R.string.sem_fingerprint_acquired_insufficient);
      case 3:
        return context.getString(C4337R.string.sem_fingerprint_acquired_image_dirty);
      case 4:
      default:
        return null;
      case 5:
        return context.getString(C4337R.string.sem_fingerprint_acquired_too_fast);
      case 6:
        if (vendorCode == 1001) {
          return context.getString(C4337R.string.sem_fingerprint_acquired_too_wet);
        }
        if (vendorCode == 1003) {
          return context.getString(C4337R.string.sem_fingerprint_acquired_light);
        }
        if (vendorCode == 1004) {
          return context.getString(C4337R.string.sem_fingerprint_acquired_tsp_block);
        }
        return null;
    }
  }

  private static String semGetErrorString(Context context, int errMsg, int vendorCode) {
    if (errMsg == 5 || errMsg == 8) {
      switch (vendorCode) {
        case BiometricFingerprintConstants.SEM_FINGERPRINT_ERROR_ONE_HAND_MODE /* 5001 */:
          return context.getString(C4337R.string.sem_fingerprint_error_onehand_mode);
        case BiometricFingerprintConstants.SEM_FINGERPRINT_ERROR_DISABLED_BIOMETRICS /* 5002 */:
          return context.getString(C4337R.string.sem_fingerprint_error_insecure_biometrics);
        case 5003:
          return context.getString(
              C4337R.string.sem_fingerprint_error_smart_view,
              context.getString(C4337R.string.sem_fingerprint_app_smart_view));
        default:
          return context.getString(C4337R.string.fingerprint_error_canceled);
      }
    }
    return null;
  }
}
