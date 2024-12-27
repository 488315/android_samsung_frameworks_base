package com.samsung.android.bio.iris;

import android.app.ActivityManagerNative;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.security.keystore.AndroidKeyStoreProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import com.samsung.android.camera.iris.IIrisService;
import com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback;
import com.samsung.android.camera.iris.IIrisServiceReceiver;
import com.samsung.android.camera.iris.Iris;
import java.security.Signature;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes5.dex */
public class SemIrisManager {
    public static final String CLIENTSPEC_KEY_ALLOW_INDEXES = "request_template_index_list";
    public static final String CLIENT_KEY_PRIVILEGED_ATTR = "privileged_attr";
    public static final int ENABLE_IMAGE_CALLBACK = 50000;
    public static final int FRONT_SENSOR_ORIENTATION = 50002;
    public static final int IRIS_ACQUIRED_DUPLICATED_SCANNED_IMAGE = 1002;
    public static final int IRIS_ACQUIRED_EYES_CLOSED = 9;
    public static final int IRIS_ACQUIRED_EYE_NOT_PRESENT = 10;
    public static final int IRIS_ACQUIRED_GOOD = 0;
    public static final int IRIS_ACQUIRED_INCORRECT_POSITION = 12;
    public static final int IRIS_ACQUIRED_INSUFFICIENT = 2;
    public static final int IRIS_ACQUIRED_MOVE_DOWN = 8;
    public static final int IRIS_ACQUIRED_MOVE_LEFT = 5;
    public static final int IRIS_ACQUIRED_MOVE_RIGHT = 6;
    public static final int IRIS_ACQUIRED_MOVE_UP = 7;
    public static final int IRIS_ACQUIRED_PARTIAL = 1;
    public static final int IRIS_ACQUIRED_TOO_BRIGHT = 11;
    public static final int IRIS_ACQUIRED_TOO_CLOSE = 4;
    public static final int IRIS_ACQUIRED_TOO_FAR = 3;
    public static final int IRIS_AUTH_TYPE_NONE = 0;
    public static final int IRIS_AUTH_TYPE_PREVIEW_CALLBACK = 1;
    public static final int IRIS_AUTH_TYPE_UI_NO_PREVIEW = 3;
    public static final int IRIS_AUTH_TYPE_UI_WITH_PREVIEW = 2;
    public static final int IRIS_DISABLE_PREVIEW_CALLBACK = 7;
    public static final int IRIS_ENABLE_PREVIEW_CALLBACK = 6;
    public static final int IRIS_ERROR_AUTH_VIEW_SIZE = 10;
    public static final int IRIS_ERROR_AUTH_WINDOW_TOKEN = 11;
    public static final int IRIS_ERROR_CANCELED = 4;
    public static final int IRIS_ERROR_EVICTED = 13;
    public static final int IRIS_ERROR_EVICTED_CAMERA_IN_USE = 19;
    public static final int IRIS_ERROR_EVICTED_DUE_TO_VIDEO_CALL = 14;
    public static final int IRIS_ERROR_EYE_SAFETY_TIMEOUT = 9;
    public static final int IRIS_ERROR_FEATURE_OFF = 18;
    public static final int IRIS_ERROR_FLIP_OFF = 17;
    public static final int IRIS_ERROR_HW_UNAVAILABLE = 0;
    public static final int IRIS_ERROR_LOCKOUT = 6;
    public static final int IRIS_ERROR_LOCKOUT_PERMANENT = 16;
    public static final int IRIS_ERROR_NEED_TO_RETRY = 5000;
    public static final int IRIS_ERROR_NO_EYE_DETECTED = 15;
    public static final int IRIS_ERROR_NO_SPACE = 3;
    public static final int IRIS_ERROR_OPEN_IR_CAMERA_FAIL = 8;
    public static final int IRIS_ERROR_PROXIMITY_ALERT = 123;
    public static final int IRIS_ERROR_PROXIMITY_TIMEOUT = 12;
    public static final int IRIS_ERROR_START_IR_CAMERA_PREVIEW_FAIL = 7;
    public static final int IRIS_ERROR_TIMEOUT = 2;
    public static final int IRIS_ERROR_UNABLE_TO_PROCESS = 1;
    public static final int IRIS_ERROR_UNABLE_TO_REMOVE = 5;
    public static final int IRIS_ERROR_UNSUPPORTED_ORIENTATION = 20;
    public static final int IRIS_ERROR_USER_CANCELED = 21;
    public static final int IRIS_ONE_EYE = 40000;
    public static final int IRIS_REQUEST_DVFS_FREQUENCY = 1004;
    public static final int IRIS_REQUEST_ENROLL_SESSION = 1002;
    public static final int IRIS_REQUEST_ENUMERATE = 11;
    public static final int IRIS_REQUEST_FACTORY_TEST_ALWAYS_LED_ON = 2001;
    public static final int IRIS_REQUEST_FACTORY_TEST_CAMERA_VERSION = 2004;
    public static final int IRIS_REQUEST_FACTORY_TEST_CAPTURE = 2002;
    public static final int IRIS_REQUEST_FACTORY_TEST_FULL_PREVIEW = 2000;
    public static final int IRIS_REQUEST_FACTORY_TEST_PREVIEW_MODE = 2003;
    public static final int IRIS_REQUEST_GET_IR_IDS = 1003;
    public static final int IRIS_REQUEST_GET_UNIQUE_ID = 7;
    public static final int IRIS_REQUEST_GET_VERSION = 4;
    public static final int IRIS_REQUEST_IR_PREVIEW_ENABLE = 2005;
    public static final int IRIS_REQUEST_LOCKOUT = 1001;
    public static final int IRIS_REQUEST_PROCESS_FIDO = 9;
    public static final int IRIS_REQUEST_REMOVE_IRIS = 1000;
    public static final int IRIS_REQUEST_SESSION_OPEN = 2;
    public static final int IRIS_REQUEST_UPDATE_SID = 10;
    public static final int IRIS_TWO_EYES = 40001;
    public static final int IRIS_VIEW_TYPE_PREVIEW_INVISIBLE = 4;
    public static final int IRIS_VIEW_TYPE_PREVIEW_VISIBLE = 5;
    public static final int IR_SENSOR_ORIENTATION = 50001;
    private static final String MANAGE_IRIS = "com.samsung.android.camera.iris.permission.MANAGE_IRIS";
    private static final int MSG_ACQUIRED = 101;
    private static final int MSG_AUTHENTICATION_FAILED = 103;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
    private static final int MSG_AUTHENTICATION_SUCCEEDED_FIDO_RESULT_DATA = 107;
    private static final int MSG_ENROLL_RESULT = 100;
    private static final int MSG_ERROR = 104;
    private static final int MSG_IR_IMAGE = 106;
    private static final int MSG_REMOVED = 105;
    public static final int PRIVILEGED_ATTR_EXCLUSIVE_IDENTIFY = 4;
    public static final int PRIVILEGED_ATTR_EXTRA_EVENT = 16;
    public static final int PRIVILEGED_ATTR_IRIS_DETECTION = 8;
    public static final int PRIVILEGED_ATTR_NO_LOCKOUT = 2;
    public static final int PRIVILEGED_ATTR_NO_VIBRATION = 1;
    public static final int PRIVILEGED_TYPE_KEYGUARD = Integer.MIN_VALUE;
    public static final int SENSOR_STATUS_ERROR = 100042;
    public static final int SENSOR_STATUS_LED_OFF = 30001;
    public static final int SENSOR_STATUS_LED_ON = 30000;
    public static final int SENSOR_STATUS_OK = 100040;
    public static final int SENSOR_STATUS_SECURE_DISABLE = 20001;
    public static final int SENSOR_STATUS_SECURE_ENALBE = 20000;
    public static final int SENSOR_STATUS_WORKING = 100041;
    private static final String SYSTEM_FEATURE_IRIS = "com.samsung.android.camera.iris";
    private static final String TAG = "Bio.SemIrisManager";
    private static final String USE_IRIS = "com.samsung.android.camera.iris.permission.USE_IRIS";
    private static SemIrisManager mSemIrisManager = null;
    private AuthenticationCallback mAuthenticationCallback;
    private Context mContext;
    private CryptoObject mCryptoObject;
    private CryptoObjectNew mCryptoObjectNew;
    private EnrollmentCallback mEnrollmentCallback;
    private Executor mExecutor;
    private GetterHandler mGetterHandler;
    private Handler mHandler;
    private OnIrImageReadyListener mListener;
    private RemovalCallback mRemovalCallback;
    private Iris mRemovalIris;
    private RequestCallback mRequestCallback;
    private IIrisService mService;
    private IBinder mToken = new Binder();
    private long mAuthBegin = 0;
    private IIrisServiceReceiver mServiceReceiver = new IIrisServiceReceiver.Stub() { // from class: com.samsung.android.bio.iris.SemIrisManager.4
        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onEnrollResult(long deviceId, int irisId, int groupId, int remaining) {
            SemIrisManager.this.mHandler.obtainMessage(100, remaining, 0, new Iris(null, groupId, irisId, deviceId)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAcquired(final long deviceId, final int acquireInfo) {
            if (SemIrisManager.this.mExecutor != null) {
                Log.v(SemIrisManager.TAG, "BioPrompt onAcquired 1");
                SemIrisManager.this.mExecutor.execute(new Runnable() { // from class: com.samsung.android.bio.iris.SemIrisManager.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SemIrisManager.this.sendAcquiredResult(deviceId, acquireInfo);
                    }
                });
            } else {
                Log.v(SemIrisManager.TAG, "BioPrompt onAcquired 2");
                SemIrisManager.this.mHandler.obtainMessage(101, acquireInfo, 0, Long.valueOf(deviceId)).sendToTarget();
            }
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationSucceeded(long deviceId, final Iris ir, final byte[] fidoResultData) {
            if (SemIrisManager.this.mExecutor != null) {
                Log.v(SemIrisManager.TAG, "BioPrompt onAuthenticationSucceeded 1");
                SemIrisManager.this.mExecutor.execute(new Runnable() { // from class: com.samsung.android.bio.iris.SemIrisManager.4.2
                    @Override // java.lang.Runnable
                    public void run() {
                        SemIrisManager.this.sendAuthenticatedSucceeded(ir, fidoResultData);
                        SemIrisManager.this.mExecutor = null;
                    }
                });
            } else {
                Log.v(SemIrisManager.TAG, "BioPrompt onAuthenticationSucceeded 2");
                SemIrisManager.this.mHandler.obtainMessage(107, fidoResultData).sendToTarget();
                SemIrisManager.this.mHandler.obtainMessage(102, ir).sendToTarget();
            }
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationFailed(long deviceId) {
            if (SemIrisManager.this.mExecutor != null) {
                Log.v(SemIrisManager.TAG, "BioPrompt onAuthenticationFailed 1");
                SemIrisManager.this.mExecutor.execute(new Runnable() { // from class: com.samsung.android.bio.iris.SemIrisManager.4.3
                    @Override // java.lang.Runnable
                    public void run() {
                        SemIrisManager.this.sendAuthenticatedFailed();
                    }
                });
            } else {
                Log.v(SemIrisManager.TAG, "BioPrompt onAuthenticationFailed 2");
                SemIrisManager.this.mHandler.obtainMessage(103).sendToTarget();
            }
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onError(final long deviceId, final int error) {
            if (SemIrisManager.this.mExecutor != null) {
                Log.v(SemIrisManager.TAG, "BioPrompt onError 1");
                SemIrisManager.this.mExecutor.execute(new Runnable() { // from class: com.samsung.android.bio.iris.SemIrisManager.4.4
                    @Override // java.lang.Runnable
                    public void run() {
                        SemIrisManager.this.sendErrorResult(deviceId, error);
                        SemIrisManager.this.mExecutor = null;
                    }
                });
            } else {
                Log.v(SemIrisManager.TAG, "BioPrompt onError 2");
                SemIrisManager.this.mHandler.obtainMessage(104, error, 0, Long.valueOf(deviceId)).sendToTarget();
            }
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onRemoved(long deviceId, int irisId, int groupId) {
            SemIrisManager.this.mHandler.obtainMessage(105, irisId, groupId, Long.valueOf(deviceId)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onIRImage(long deviceId, byte[] irisImage, int width, int height) {
            SemIrisManager.this.mHandler.obtainMessage(106, width, height, irisImage).sendToTarget();
        }
    };

    public interface OnIrImageReadyListener {
        void onIrImageReady(byte[] bArr, int i, int i2);
    }

    private class OnEnrollCancelListener implements CancellationSignal.OnCancelListener {
        private OnEnrollCancelListener() {
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            SemIrisManager.this.cancelEnrollment();
        }
    }

    private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private CryptoObject mCrypto;

        public OnAuthenticationCancelListener(CryptoObject crypto) {
            this.mCrypto = crypto;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            SemIrisManager.this.cancelAuthentication(this.mCrypto);
        }
    }

    public void setOnIrImageReadyListener(OnIrImageReadyListener listener) {
        this.mListener = listener;
        if (listener != null) {
            enableIRImageCallback(true);
        } else {
            enableIRImageCallback(false);
        }
    }

    public static final class CryptoObject {
        private final Object mCrypto;
        private byte[] mFidoRequestData;
        private byte[] mFidoResultData = null;

        public CryptoObject(Signature signature, byte[] fidoRequestData) {
            this.mCrypto = signature;
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Cipher cipher, byte[] fidoRequestData) {
            this.mCrypto = cipher;
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Mac mac, byte[] fidoRequestData) {
            this.mCrypto = mac;
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Signature signature) {
            this.mCrypto = signature;
        }

        public CryptoObject(Cipher cipher) {
            this.mCrypto = cipher;
        }

        public CryptoObject(Mac mac) {
            this.mCrypto = mac;
        }

        public Signature getSignature() {
            if (this.mCrypto instanceof Signature) {
                return (Signature) this.mCrypto;
            }
            return null;
        }

        public Cipher getCipher() {
            if (this.mCrypto instanceof Cipher) {
                return (Cipher) this.mCrypto;
            }
            return null;
        }

        public Mac getMac() {
            if (this.mCrypto instanceof Mac) {
                return (Mac) this.mCrypto;
            }
            return null;
        }

        public long getOpId() {
            if (this.mCrypto != null) {
                return AndroidKeyStoreProvider.getKeyStoreOperationHandle(this.mCrypto);
            }
            return 0L;
        }

        public byte[] getFidoRequestData() {
            return this.mFidoRequestData;
        }

        public void setFidoRequestData(byte[] requestData) {
            this.mFidoRequestData = requestData;
        }

        public byte[] getFidoResultData() {
            return this.mFidoResultData;
        }

        public void setFidoResultData(byte[] fidoResultData) {
            this.mFidoResultData = fidoResultData;
        }
    }

    public static final class CryptoObjectNew {
        private BiometricPrompt.CryptoObject mBioCryptoObject;
        private final byte[] mFidoRequestData;
        private byte[] mFidoResultData;

        CryptoObjectNew(BiometricPrompt.CryptoObject cry) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = cry;
            this.mFidoRequestData = null;
        }

        public BiometricPrompt.CryptoObject getBiometricCryptoObject() {
            return this.mBioCryptoObject;
        }

        public long getOpId() {
            return this.mBioCryptoObject.getOpId();
        }

        public CryptoObjectNew(Signature signature, byte[] fidoRequestData) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = new BiometricPrompt.CryptoObject(signature);
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObjectNew(Cipher cipher, byte[] fidoRequestData) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = new BiometricPrompt.CryptoObject(cipher);
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObjectNew(Mac mac, byte[] fidoRequestData) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = new BiometricPrompt.CryptoObject(mac);
            this.mFidoRequestData = fidoRequestData;
        }

        public Signature getSignature() {
            return this.mBioCryptoObject.getSignature();
        }

        public Cipher getCipher() {
            return this.mBioCryptoObject.getCipher();
        }

        public Mac getMac() {
            return this.mBioCryptoObject.getMac();
        }

        public byte[] getFidoRequestData() {
            return this.mFidoRequestData;
        }

        public byte[] getFidoResultData() {
            return this.mFidoResultData;
        }

        private void setFidoResultData(byte[] fidoResultData) {
            this.mFidoResultData = fidoResultData;
        }
    }

    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private Iris mIris;

        public AuthenticationResult(CryptoObject crypto, Iris iris) {
            this.mCryptoObject = crypto;
            this.mIris = iris;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Iris getIris() {
            return this.mIris;
        }

        public void setToken(byte[] token) {
            this.mCryptoObject.setFidoResultData(token);
        }

        public byte[] getToken() {
            return this.mCryptoObject.getFidoResultData();
        }
    }

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int errorCode, CharSequence errString) {
        }

        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationAcquired(int acquireInfo) {
        }
    }

    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int errMsgId, CharSequence errString) {
        }

        public void onEnrollmentHelp(int helpMsgId, CharSequence helpString) {
        }

        public void onEnrollmentProgress(int remaining) {
        }
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(Iris ir, int errMsgId, CharSequence errString) {
        }

        public void onRemovalSucceeded(Iris iris) {
        }
    }

    public static abstract class RequestCallback {
        public void onRequested(int msgId) {
        }
    }

    public static abstract class LockoutResetCallback {
        public void onLockoutReset() {
        }
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, AuthenticationCallback callback, Handler handler, View irisView) {
        authenticate(crypto, cancel, 0, callback, handler, irisView, UserHandle.myUserId());
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, AuthenticationCallback callback, Handler handler, View irisView, byte[] requestData) {
        if (crypto != null && requestData != null) {
            crypto.setFidoRequestData(requestData);
        }
        authenticate(crypto, cancel, 0, callback, handler, irisView, UserHandle.myUserId());
    }

    private void cancelAuthentication() {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                this.mService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception while canceling authentication");
            }
        }
    }

    public IIrisService getService() {
        if (ensureServiceConnected()) {
            return this.mService;
        }
        return null;
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, int flags, AuthenticationCallback callback, Handler handler, View irisView, int userId) {
        authenticate(crypto, cancel, flags, callback, handler, userId, null, irisView);
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, int flags, AuthenticationCallback callback, Handler handler, int userId, Bundle attr, View irisView) {
        String str;
        AuthenticationCallback authenticationCallback;
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancel != null) {
            if (!cancel.isCanceled()) {
                cancel.setOnCancelListener(new OnAuthenticationCancelListener(crypto));
            } else {
                Log.w(TAG, "authentication already canceled");
                return;
            }
        }
        if (ensureServiceConnected() && this.mService != null) {
            try {
                useHandler(handler);
                byte[] bArr = null;
                this.mEnrollmentCallback = null;
                this.mAuthenticationCallback = callback;
                this.mCryptoObject = crypto;
                long sessionId = crypto != null ? crypto.getOpId() : 0L;
                if (crypto != null) {
                    bArr = this.mCryptoObject.getFidoRequestData();
                }
                byte[] fidoRequestData = bArr;
                if (irisView == null) {
                    this.mService.authenticate(this.mToken, null, 0, 0, 0, 0, sessionId, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName(), attr, fidoRequestData);
                    return;
                }
                this.mAuthBegin = System.currentTimeMillis();
                str = TAG;
                authenticationCallback = callback;
                try {
                    checkAuthViewWindowToken(crypto, cancel, flags, callback, handler, userId, attr, irisView, sessionId, fidoRequestData);
                } catch (RemoteException e) {
                    Log.w(str, "Remote exception while authenticating");
                    authenticationCallback.onAuthenticationError(1, getErrorString(1));
                }
            } catch (RemoteException e2) {
                str = TAG;
                authenticationCallback = callback;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAuthViewWindowToken(final CryptoObject crypto, final CancellationSignal cancel, final int flags, final AuthenticationCallback callback, final Handler handler, final int userId, final Bundle attr, final View irisView, final long sessionId, final byte[] fidoRequestData) {
        AuthenticationCallback authenticationCallback;
        if (this.mGetterHandler == null) {
            this.mGetterHandler = new GetterHandler(Looper.getMainLooper());
        }
        if (irisView.getWindowToken() == null) {
            if (System.currentTimeMillis() - this.mAuthBegin >= 3000) {
                Log.e(TAG, "checkAuthViewWindowToken is null");
                this.mGetterHandler.removeAllGetterCallbacks();
                if (callback != null) {
                    callback.onAuthenticationError(1, getErrorString(1));
                    return;
                }
                return;
            }
            this.mGetterHandler.postGetterCallback(new Runnable() { // from class: com.samsung.android.bio.iris.SemIrisManager.1
                @Override // java.lang.Runnable
                public void run() {
                    SemIrisManager.this.checkAuthViewWindowToken(crypto, cancel, flags, callback, handler, userId, attr, irisView, sessionId, fidoRequestData);
                }
            });
            return;
        }
        this.mGetterHandler.removeAllGetterCallbacks();
        try {
            IBinder mWindowToken = irisView.getWindowToken();
            int[] position = new int[2];
            try {
                irisView.getLocationInWindow(position);
                if (this.mToken == null) {
                    Log.e(TAG, "mToken null");
                }
                Size mAuthViewSize = getMinimumIrisViewSize();
                if (irisView.getWidth() >= mAuthViewSize.getWidth()) {
                    if (irisView.getHeight() >= mAuthViewSize.getHeight()) {
                        authenticationCallback = callback;
                        this.mService.authenticate(this.mToken, mWindowToken, position[0], position[1], irisView.getWidth(), irisView.getHeight(), sessionId, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName(), attr, fidoRequestData);
                    }
                }
                authenticationCallback = callback;
                if (authenticationCallback != null) {
                    try {
                        Log.e(TAG, "Invalid irisView size. IrisView's proper size:" + mAuthViewSize.getWidth() + "x" + mAuthViewSize.getHeight() + ", but app's size:" + irisView.getWidth() + "x" + irisView.getHeight());
                    } catch (RemoteException e) {
                        Log.w(TAG, "Remote exception while authenticating");
                        if (authenticationCallback != null) {
                            authenticationCallback.onAuthenticationError(1, getErrorString(1));
                            return;
                        }
                        return;
                    }
                }
                this.mService.authenticate(this.mToken, mWindowToken, position[0], position[1], irisView.getWidth(), irisView.getHeight(), sessionId, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName(), attr, fidoRequestData);
            } catch (RemoteException e2) {
                authenticationCallback = callback;
            }
        } catch (RemoteException e3) {
            authenticationCallback = callback;
        }
    }

    static class GetterHandler extends Handler {
        private static final int IMAGE_GETTER_CALLBACK = 1;

        public GetterHandler(Context context) {
            super(context.getMainLooper());
        }

        private GetterHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    ((Runnable) message.obj).run();
                    break;
            }
        }

        public void postGetterCallback(Runnable callback) {
            postDelayedGetterCallback(callback, 0L);
        }

        public void postDelayedGetterCallback(Runnable callback, long delay) {
            if (callback == null) {
                throw new NullPointerException();
            }
            Message message = Message.obtain();
            message.what = 1;
            message.obj = callback;
            sendMessageDelayed(message, delay);
        }

        public void removeAllGetterCallbacks() {
            removeMessages(1);
        }
    }

    public void enroll(byte[] token, CancellationSignal cancel, int flags, EnrollmentCallback callback, View irisView) {
        enroll(token, cancel, flags, getCurrentUserId(), callback, null, irisView);
    }

    public void enroll(byte[] token, CancellationSignal cancel, int flags, int userId, EnrollmentCallback callback, Bundle attr, View irisView) {
        int userId2;
        String str;
        if (userId != -2) {
            userId2 = userId;
        } else {
            userId2 = getCurrentUserId();
        }
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancel != null) {
            if (!cancel.isCanceled()) {
                cancel.setOnCancelListener(new OnEnrollCancelListener());
            } else {
                Log.w(TAG, "enrollment already canceled");
                return;
            }
        }
        if (ensureServiceConnected() && this.mService != null) {
            try {
                this.mAuthenticationCallback = null;
                this.mEnrollmentCallback = callback;
                if (irisView == null) {
                    this.mService.enroll(this.mToken, null, 0, 0, 0, 0, token, userId2, this.mServiceReceiver, flags, this.mContext.getOpPackageName(), attr);
                    return;
                }
                int i = userId2;
                str = TAG;
                try {
                    checkEnrollViewWindowToken(token, cancel, flags, i, callback, attr, irisView);
                } catch (RemoteException e) {
                    Log.w(str, "Remote exception in enroll");
                    callback.onEnrollmentError(1, getErrorString(1));
                }
            } catch (RemoteException e2) {
                str = TAG;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkEnrollViewWindowToken(final byte[] token, final CancellationSignal cancel, final int flags, final int userId, final EnrollmentCallback callback, final Bundle attr, final View irisView) {
        if (this.mGetterHandler == null) {
            this.mGetterHandler = new GetterHandler(Looper.getMainLooper());
        }
        if (irisView.getWindowToken() == null) {
            this.mGetterHandler.postGetterCallback(new Runnable() { // from class: com.samsung.android.bio.iris.SemIrisManager.2
                @Override // java.lang.Runnable
                public void run() {
                    SemIrisManager.this.checkEnrollViewWindowToken(token, cancel, flags, userId, callback, attr, irisView);
                }
            });
            return;
        }
        this.mGetterHandler.removeAllGetterCallbacks();
        try {
            IBinder mWindowToken = irisView.getWindowToken();
            int[] position = new int[2];
            try {
                irisView.getLocationInWindow(position);
                if (this.mToken == null) {
                    Log.e(TAG, "mToken null");
                }
                this.mService.enroll(this.mToken, mWindowToken, position[0], position[1], irisView.getWidth(), irisView.getHeight(), token, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName(), attr);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception in enroll");
                if (callback != null) {
                    callback.onEnrollmentError(1, getErrorString(1));
                }
            }
        } catch (RemoteException e2) {
        }
    }

    public long preEnroll() {
        if (!ensureServiceConnected() || this.mService == null) {
            return 0L;
        }
        try {
            long result = this.mService.preEnroll(this.mToken);
            return result;
        } catch (RemoteException e) {
            Log.w(TAG, "Remote exception in enroll");
            return 0L;
        }
    }

    public int postEnroll() {
        if (!ensureServiceConnected() || this.mService == null) {
            return 0;
        }
        try {
            int result = this.mService.postEnroll(this.mToken);
            return result;
        } catch (RemoteException e) {
            Log.w(TAG, "Remote exception in post enroll");
            return 0;
        }
    }

    public void setActiveUser(int userId) {
        if (this.mService != null) {
            try {
                this.mService.setActiveUser(userId);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception in setActiveUser");
            }
        }
    }

    public void remove(Iris ir, int userId, RemovalCallback callback) {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                this.mRemovalCallback = callback;
                this.mRemovalIris = ir;
                this.mService.remove(this.mToken, ir.getIrisId(), ir.getGroupId(), userId, this.mServiceReceiver);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception in remove");
                if (callback != null) {
                    callback.onRemovalError(ir, 1, getErrorString(1));
                }
            }
        }
    }

    public void remove(Iris ir, RemovalCallback callback) {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                this.mRemovalCallback = callback;
                this.mRemovalIris = ir;
                this.mService.remove(this.mToken, ir.getIrisId(), ir.getGroupId(), getCurrentUserId(), this.mServiceReceiver);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception in remove");
                if (callback != null) {
                    callback.onRemovalError(ir, 1, getErrorString(1));
                }
            }
        }
    }

    public void rename(int irId, int userId, String newName) {
        if (!ensureServiceConnected()) {
            return;
        }
        if (this.mService != null) {
            try {
                this.mService.rename(irId, userId, newName);
                return;
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in rename()");
                return;
            }
        }
        Log.w(TAG, "rename(): Service not connected!");
    }

    public List<Iris> getEnrolledIrises(int userId) {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                return this.mService.getEnrolledIrises(userId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        return null;
    }

    public List<Iris> getEnrolledIrises() {
        return getEnrolledIrises(UserHandle.myUserId());
    }

    public boolean hasEnrolledIrises() {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                return this.mService.hasEnrolledIrises(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        return false;
    }

    public boolean hasEnrolledIris() {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                return this.mService.hasEnrolledIrises(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        return false;
    }

    public boolean hasDisabledIris() {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                return this.mService.hasDisabledIris(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledFaces");
            }
        }
        return false;
    }

    public boolean hasEnrolledIrises(int userId) {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                return this.mService.hasEnrolledIrises(userId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises, userId : " + userId);
            }
        }
        return false;
    }

    public boolean isHardwareDetected() {
        Log.w(TAG, "isIrisHardwareDetected()");
        if (this.mContext != null) {
            return this.mContext.getPackageManager().hasSystemFeature(SYSTEM_FEATURE_IRIS);
        }
        return false;
    }

    public Size getMinimumIrisViewSize() {
        int width;
        int height;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
        int roundDensity = Math.round(displaymetrics.density);
        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
            width = displaymetrics.widthPixels / roundDensity;
            height = (int) (width / 1.7777778f);
        } else {
            width = displaymetrics.heightPixels / roundDensity;
            height = (int) (width / 1.7777778f);
        }
        return new Size(width * roundDensity, height * roundDensity);
    }

    public void setIrisViewType(int irisViewType) {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                this.mService.setIrisViewType(UserHandle.myUserId(), this.mContext.getOpPackageName(), irisViewType);
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in setIrisViewType");
            }
        }
    }

    public void enableIRImageCallback(boolean enabled) {
        if (ensureServiceConnected() && this.mService != null) {
            try {
                if (enabled) {
                    this.mService.enableIRImageCallback(UserHandle.myUserId(), this.mContext.getOpPackageName(), 6);
                } else {
                    this.mService.enableIRImageCallback(UserHandle.myUserId(), this.mContext.getOpPackageName(), 7);
                }
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in enableIRImageCallback");
            }
        }
    }

    public SparseArray getEnrolledIrisUniqueID() {
        if (!ensureServiceConnected()) {
            return null;
        }
        SparseArray localSparseArray = new SparseArray();
        List<Iris> irisList = null;
        int index = 1;
        if (this.mService != null) {
            try {
                irisList = this.mService.getEnrolledIrises(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        if (irisList == null || irisList.size() <= 0 || this.mContext == null) {
            return null;
        }
        for (Iris ir : irisList) {
            localSparseArray.put(index, byteArrayToHex(requestGetUniqueID(ir.getIrisId(), this.mContext.getOpPackageName())));
            index++;
        }
        return localSparseArray;
    }

    public String getEnrolledIrisId() {
        if (!ensureServiceConnected()) {
            return null;
        }
        List<Iris> irisList = null;
        if (this.mService != null) {
            try {
                irisList = this.mService.getEnrolledIrises(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        if (irisList == null || irisList.size() <= 0 || this.mContext == null) {
            return null;
        }
        Iterator<Iris> it = irisList.iterator();
        if (!it.hasNext()) {
            return null;
        }
        Iris ir = it.next();
        return byteArrayToHex(requestGetUniqueID(ir.getIrisId(), this.mContext.getOpPackageName()));
    }

    public int request(int cmd, byte[] inputBuf, byte[] outputBuf, int inParam, RequestCallback callback) {
        byte[] inputBuf2;
        byte[] outputBuf2;
        if (!ensureServiceConnected()) {
            return 0;
        }
        if (this.mService != null) {
            if (inputBuf != null) {
                inputBuf2 = inputBuf;
            } else {
                try {
                    inputBuf2 = new byte[0];
                } catch (RemoteException e) {
                    Log.v(TAG, "Remote exception in request()");
                    return -2;
                }
            }
            if (outputBuf != null) {
                outputBuf2 = outputBuf;
            } else {
                try {
                    outputBuf2 = new byte[0];
                } catch (RemoteException e2) {
                    Log.v(TAG, "Remote exception in request()");
                    return -2;
                }
            }
            try {
                this.mRequestCallback = callback;
                return this.mService.request(this.mToken, cmd, inputBuf2, outputBuf2, inParam, getCurrentUserId(), this.mServiceReceiver);
            } catch (RemoteException e3) {
                Log.v(TAG, "Remote exception in request()");
                return -2;
            }
        }
        Log.w(TAG, "request(): Service not connected!");
        return -2;
    }

    public boolean isEnrollSession() {
        int ret = request(1002, null, null, 0, null);
        return ret > 0;
    }

    public boolean requestSessionOpen() {
        if (request(2, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public byte[] requestGetVersion() {
        byte[] outBuf = new byte[256];
        int size = request(4, null, outBuf, 0, null);
        if (size <= 0) {
            return null;
        }
        return Arrays.copyOf(outBuf, size);
    }

    private byte[] requestGetUniqueID(int irisId, String packageName) {
        if (!ensureServiceConnected()) {
            return null;
        }
        byte[] outBuf = new byte[256];
        int size = 0;
        if (this.mService != null) {
            try {
                size = this.mService.request(this.mToken, 7, packageName.getBytes(), outBuf, irisId, UserHandle.myUserId(), this.mServiceReceiver);
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in request()");
            }
        }
        if (size <= 0) {
            return null;
        }
        return Arrays.copyOf(outBuf, size);
    }

    public byte[] requestProcessFIDO(byte[] inBuf) {
        byte[] outBuf = new byte[10240];
        int size = request(9, inBuf, outBuf, 0, null);
        if (size <= 0) {
            return null;
        }
        return Arrays.copyOf(outBuf, size);
    }

    public boolean requestUpdateSID(byte[] sId) {
        if (request(10, sId, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestLedOn() {
        if (request(2001, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestFullPreview() {
        if (request(2000, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestPreviewMode() {
        if (request(2003, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestCapture() {
        if (request(2002, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestCameraVersion() {
        if (request(2004, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public long getAuthenticatorId() {
        if (!ensureServiceConnected()) {
            return 0L;
        }
        if (this.mService != null) {
            try {
                return this.mService.getAuthenticatorId(this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getAuthenticatorId()");
            }
        } else {
            Log.w(TAG, "getAuthenticatorId(): Service not connected!");
        }
        return 0L;
    }

    public void resetTimeout(byte[] token) {
        if (!ensureServiceConnected()) {
            return;
        }
        if (this.mService != null) {
            try {
                this.mService.resetTimeout(token);
                return;
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in resetTimeout()");
                return;
            }
        }
        Log.w(TAG, "resetTimeout(): Service not connected!");
    }

    public void addLockoutResetCallback(final LockoutResetCallback callback) {
        if (!ensureServiceConnected()) {
            return;
        }
        if (this.mService != null) {
            try {
                final PowerManager powerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
                this.mService.addLockoutResetCallback(new IIrisServiceLockoutResetCallback.Stub() { // from class: com.samsung.android.bio.iris.SemIrisManager.3
                    @Override // com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback
                    public void onLockoutReset(long deviceId) throws RemoteException {
                        final PowerManager.WakeLock wakeLock = powerManager.newWakeLock(1, "lockoutResetCallback");
                        wakeLock.acquire();
                        SemIrisManager.this.mHandler.post(new Runnable() { // from class: com.samsung.android.bio.iris.SemIrisManager.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    callback.onLockoutReset();
                                } finally {
                                    wakeLock.release();
                                }
                            }
                        });
                    }
                });
                return;
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in addLockoutResetCallback()");
                return;
            }
        }
        Log.w(TAG, "addLockoutResetCallback(): Service not connected!");
    }

    private class MyHandler extends Handler {
        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    sendEnrollResult((Iris) msg.obj, msg.arg1);
                    break;
                case 101:
                    SemIrisManager.this.sendAcquiredResult(((Long) msg.obj).longValue(), msg.arg1);
                    break;
                case 102:
                    sendAuthenticatedSucceeded((Iris) msg.obj);
                    break;
                case 103:
                    SemIrisManager.this.sendAuthenticatedFailed();
                    break;
                case 104:
                    SemIrisManager.this.sendErrorResult(((Long) msg.obj).longValue(), msg.arg1);
                    break;
                case 105:
                    sendRemovedResult(((Long) msg.obj).longValue(), msg.arg1, msg.arg2);
                    break;
                case 106:
                    sendIRImage((byte[]) msg.obj, msg.arg1, msg.arg2);
                    break;
                case 107:
                    sendAuthenticatedSucceededFidoResultData((byte[]) msg.obj);
                    break;
            }
        }

        private void sendIRImage(byte[] irisImage, int width, int height) {
            Log.w(SemIrisManager.TAG, "sendIRImage, width : " + width + " height : " + height);
            if (SemIrisManager.this.mListener != null) {
                SemIrisManager.this.mListener.onIrImageReady(irisImage, width, height);
            }
        }

        private void sendRemovedResult(long deviceId, int irisId, int groupId) {
            if (SemIrisManager.this.mRemovalCallback != null) {
                int reqIrisId = SemIrisManager.this.mRemovalIris.getIrisId();
                int reqGroupId = SemIrisManager.this.mRemovalIris.getGroupId();
                if (irisId != reqIrisId) {
                    Log.w(SemIrisManager.TAG, "Iris id didn't match: " + irisId + " != " + reqIrisId);
                }
                if (groupId != reqGroupId) {
                    Log.w(SemIrisManager.TAG, "Group id didn't match: " + groupId + " != " + reqGroupId);
                }
                SemIrisManager.this.mRemovalCallback.onRemovalSucceeded(SemIrisManager.this.mRemovalIris);
            }
        }

        private void sendEnrollResult(Iris ir, int remaining) {
            if (SemIrisManager.this.mEnrollmentCallback != null) {
                SemIrisManager.this.mEnrollmentCallback.onEnrollmentProgress(remaining);
            }
        }

        private void sendAuthenticatedSucceededFidoResultData(byte[] fidoResultData) {
            Log.w(SemIrisManager.TAG, "sendAuthenticatedSucceededFidoResultData, fidoResultData : " + Arrays.toString(fidoResultData));
            if (SemIrisManager.this.mCryptoObject != null) {
                SemIrisManager.this.mCryptoObject.setFidoResultData(fidoResultData);
            }
        }

        private void sendAuthenticatedSucceeded(Iris ir) {
            Log.w(SemIrisManager.TAG, "sendAuthenticatedSucceeded, ir : " + ir);
            if (SemIrisManager.this.mAuthenticationCallback != null) {
                AuthenticationResult result = new AuthenticationResult(SemIrisManager.this.mCryptoObject, ir);
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationSucceeded(result);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorResult(long deviceId, int errMsgId) {
        Log.w(TAG, "sendErrorResult, errMsgId : " + errMsgId);
        if (errMsgId == 4) {
            return;
        }
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentError(errMsgId, getErrorString(errMsgId));
        } else if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationError(errMsgId, getErrorString(errMsgId));
        } else if (this.mRemovalCallback != null) {
            this.mRemovalCallback.onRemovalError(this.mRemovalIris, errMsgId, getErrorString(errMsgId));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAcquiredResult(long deviceId, int acquireInfo) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationAcquired(acquireInfo);
        }
        String msg = getAcquiredString(acquireInfo);
        if (msg == null) {
            return;
        }
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentHelp(acquireInfo, msg);
        } else {
            if (this.mAuthenticationCallback == null || msg == null) {
                return;
            }
            this.mAuthenticationCallback.onAuthenticationHelp(acquireInfo, msg);
        }
    }

    private void sendAuthenticatedSucceeded(AuthenticationResult result) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationSucceeded(result);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedSucceeded(Iris ir, byte[] fidoResultData) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedFailed() {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationFailed();
        }
    }

    public SemIrisManager(Context context, IIrisService service) {
        this.mContext = context;
        this.mService = service;
        if (this.mService == null) {
            Log.v(TAG, "SemIrisManagerService was null");
        }
        this.mHandler = new MyHandler(context);
        this.mGetterHandler = new GetterHandler(context);
    }

    private int getCurrentUserId() {
        try {
            return ActivityManagerNative.getDefault().getCurrentUser().id;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to get current user id\n");
            return -10000;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelEnrollment() {
        Log.e(TAG, "cancelEnrollment");
        if (ensureServiceConnected() && this.mService != null) {
            try {
                this.mService.cancelEnrollment(this.mToken);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception while canceling enrollment");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(CryptoObject cryptoObject) {
        Log.e(TAG, "cancelAuthentication");
        if (ensureServiceConnected() && this.mService != null) {
            try {
                this.mService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception while canceling authentication");
            }
        }
    }

    private String getErrorString(int errMsg) {
        Resources mRes;
        PackageManager mPm = this.mContext.getPackageManager();
        try {
            mRes = mPm.getResourcesForApplication("com.samsung.android.server.iris");
        } catch (Exception e) {
            Log.e(TAG, "getErrorString, Exception = " + e);
            mRes = null;
        }
        if (mRes == null) {
            Log.e(TAG, "mRes is null");
            return null;
        }
        try {
            switch (errMsg) {
                case 0:
                    return mRes.getString(mRes.getIdentifier("iris_error_sensor_no_response", "string", "com.samsung.android.server.iris"));
                case 1:
                    return mRes.getString(mRes.getIdentifier("iris_error_unable_to_process", "string", "com.samsung.android.server.iris"));
                case 2:
                    return mRes.getString(mRes.getIdentifier("iris_error_timeout", "string", "com.samsung.android.server.iris"));
                case 3:
                    return mRes.getString(mRes.getIdentifier("iris_error_no_space", "string", "com.samsung.android.server.iris"));
                case 4:
                    return mRes.getString(mRes.getIdentifier("iris_error_canceled", "string", "com.samsung.android.server.iris"));
                case 5:
                    return mRes.getString(mRes.getIdentifier("iris_error_unable_to_remove", "string", "com.samsung.android.server.iris"));
                case 6:
                    return mRes.getString(mRes.getIdentifier("iris_error_lockout", "string", "com.samsung.android.server.iris"));
                case 7:
                    return "";
                case 8:
                    return "";
                case 9:
                    return mRes.getString(mRes.getIdentifier("iris_error_eye_safety_timeout", "string", "com.samsung.android.server.iris"));
                case 10:
                    return mRes.getString(mRes.getIdentifier("iris_error_auth_view_size", "string", "com.samsung.android.server.iris"));
                case 12:
                    return mRes.getString(mRes.getIdentifier("iris_error_proximity_timeout", "string", "com.samsung.android.server.iris"));
                case 13:
                    return mRes.getString(mRes.getIdentifier("iris_error_evicted", "string", "com.samsung.android.server.iris"));
                case 14:
                    return mRes.getString(mRes.getIdentifier("iris_error_video_call_interrupt", "string", "com.samsung.android.server.iris"));
                case 15:
                    return mRes.getString(mRes.getIdentifier("iris_error_no_eye_detected", "string", "com.samsung.android.server.iris"));
                case 17:
                    return mRes.getString(mRes.getIdentifier("iris_error_flip_off", "string", "com.samsung.android.server.iris"));
                case 18:
                    return mRes.getString(mRes.getIdentifier("iris_error_need_set_lock_type", "string", "com.samsung.android.server.iris"));
                case 19:
                    return mRes.getString(mRes.getIdentifier("iris_error_while_camera_in_use", "string", "com.samsung.android.server.iris"));
                case 20:
                    return mRes.getString(mRes.getIdentifier("iris_error_unsupported_orientation", "string", "com.samsung.android.server.iris"));
                case 25:
                    return null;
                case 123:
                    return mRes.getString(mRes.getIdentifier("iris_error_proximity_alert", "string", "com.samsung.android.server.iris"));
                default:
                    return mRes.getString(mRes.getIdentifier("iris_error_unable_to_process", "string", "com.samsung.android.server.iris"));
            }
        } catch (Resources.NotFoundException e2) {
            Log.d(TAG, "getErrorString, NotFoundException = " + e2);
            return null;
        }
    }

    private String getAcquiredString(int acquireInfo) {
        Resources mRes;
        PackageManager mPm = this.mContext.getPackageManager();
        try {
            mRes = mPm.getResourcesForApplication("com.samsung.android.server.iris");
        } catch (Exception e) {
            Log.e(TAG, "getAcquiredString, Exception = " + e);
            mRes = null;
        }
        if (mRes == null) {
            Log.e(TAG, "mRes is null");
            return null;
        }
        try {
            switch (acquireInfo) {
                case 1:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 9:
                    break;
                case 11:
                    break;
            }
        } catch (Resources.NotFoundException e2) {
            Log.d(TAG, "getAcquiredString, NotFoundException = " + e2);
            return null;
        }
        return null;
    }

    public static synchronized SemIrisManager getInstance(Context context) {
        synchronized (SemIrisManager.class) {
            if (!context.getPackageManager().hasSystemFeature(SYSTEM_FEATURE_IRIS)) {
                return null;
            }
            if (mSemIrisManager == null) {
                mSemIrisManager = new SemIrisManager(context);
            }
            return mSemIrisManager;
        }
    }

    public static synchronized SemIrisManager getSemIrisManager(Context context) {
        synchronized (SemIrisManager.class) {
            if (!context.getPackageManager().hasSystemFeature(SYSTEM_FEATURE_IRIS)) {
                return null;
            }
            if (mSemIrisManager == null) {
                mSemIrisManager = new SemIrisManager(context);
            }
            return mSemIrisManager;
        }
    }

    public SemIrisManager(Context context) {
        this.mContext = context;
        this.mHandler = new MyHandler(context);
        this.mGetterHandler = new GetterHandler(context);
    }

    private synchronized boolean ensureServiceConnected() {
        if (this.mService != null) {
            try {
                this.mService.isHardwareDetected(0L, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    this.mService = null;
                }
            }
        }
        if (this.mService == null) {
            startIrisService();
            waitForService();
        }
        return this.mService != null;
    }

    private void startIrisService() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.samsung.android.server.iris", "com.samsung.android.server.iris.IrisService"));
            this.mContext.startServiceAsUser(intent, UserHandle.CURRENT_OR_SELF);
        } catch (Exception e) {
            Log.e(TAG, "Starting startIrisService failed: " + e);
        }
    }

    private void waitForService() {
        for (int count = 1; count <= 20; count++) {
            this.mService = IIrisService.Stub.asInterface(ServiceManager.getService("samsung.iris"));
            if (this.mService != null) {
                Log.v(TAG, "Service connected!");
                return;
            }
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
            }
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(byteArray.length * 2);
        for (byte b : byteArray) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    private static String bytesToString(byte[] a, int len) {
        if (len > a.length || len < 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            sb.append(String.format("%c", Integer.valueOf(a[i] & 255)));
        }
        return sb.toString();
    }
}
