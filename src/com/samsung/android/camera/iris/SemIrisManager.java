package com.samsung.android.camera.iris;

import android.app.ActivityManagerNative;
import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import java.security.Signature;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes6.dex */
public class SemIrisManager {
    public static final String CLIENTSPEC_KEY_ALLOW_INDEXES = "request_template_index_list";
    public static final String CLIENTSPEC_KEY_USE_MANUAL_TIMEOUT = "useManualTimeout";
    public static final String CLIENT_KEY_PRIVILEGED_ATTR = "privileged_attr";
    public static final int ENABLE_IMAGE_CALLBACK = 50000;
    public static final int FRONT_SENSOR_ORIENTATION = 50002;
    public static final int IRIS_ACQUIRED_CHANGE_YOUR_POSITION = 12;
    public static final int IRIS_ACQUIRED_DUPLICATED_SCANNED_IMAGE = 1002;
    public static final int IRIS_ACQUIRED_EYE_NOT_PRESENT = 10;
    public static final int IRIS_ACQUIRED_GOOD = 0;
    public static final int IRIS_ACQUIRED_INSUFFICIENT = 2;
    public static final int IRIS_ACQUIRED_MOVE_CLOSER = 3;
    public static final int IRIS_ACQUIRED_MOVE_DOWN = 8;
    public static final int IRIS_ACQUIRED_MOVE_FARTHER = 4;
    public static final int IRIS_ACQUIRED_MOVE_LEFT = 5;
    public static final int IRIS_ACQUIRED_MOVE_RIGHT = 6;
    public static final int IRIS_ACQUIRED_MOVE_SOMEWHERE_DARKER = 11;
    public static final int IRIS_ACQUIRED_MOVE_UP = 7;
    public static final int IRIS_ACQUIRED_OPEN_EYES_WIDER = 9;
    public static final int IRIS_ACQUIRED_PARTIAL = 1;
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
    public static final int IRIS_ERROR_FLIP_OFF = 17;
    public static final int IRIS_ERROR_HW_UNAVAILABLE = 0;
    public static final int IRIS_ERROR_LOCKOUT = 6;
    public static final int IRIS_ERROR_LOCKOUT_PERMANENT = 16;
    public static final int IRIS_ERROR_NEED_SET_LOCK_TYPE = 18;
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
    public static final int IRIS_INVISIBLE_PREVIEW = 4;
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
    public static final int IRIS_VISIBLE_PREVIEW = 5;
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
    private static final String TAG = "SemIrisManager";
    private static final String USE_IRIS = "com.samsung.android.camera.iris.permission.USE_IRIS";
    private static SemIrisManager mSemIrisManager;
    private AuthenticationCallback mAuthenticationCallback;
    private Context mContext;
    private CryptoObject mCryptoObject;
    private EnrollmentCallback mEnrollmentCallback;
    private GetterHandler mGetterHandler;
    private Handler mHandler;
    private RemovalCallback mRemovalCallback;
    private Iris mRemovalIris;
    private RequestCallback mRequestCallback;
    private IIrisService mService;
    private IBinder mToken = new Binder();
    private long mAuthBegin = 0;
    private IIrisServiceReceiver mServiceReceiver = new IIrisServiceReceiver.Stub() { // from class: com.samsung.android.camera.iris.SemIrisManager.4
        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onEnrollResult(long j, int i, int i2, int i3) {
            SemIrisManager.this.mHandler.obtainMessage(100, i3, 0, new Iris(null, i2, i, j)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAcquired(long j, int i) {
            SemIrisManager.this.mHandler.obtainMessage(101, i, 0, Long.valueOf(j)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationSucceeded(long j, Iris iris, byte[] bArr) {
            SemIrisManager.this.mHandler.obtainMessage(107, bArr).sendToTarget();
            SemIrisManager.this.mHandler.obtainMessage(102, iris).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationFailed(long j) {
            SemIrisManager.this.mHandler.obtainMessage(103).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onError(long j, int i) {
            SemIrisManager.this.mHandler.obtainMessage(104, i, 0, Long.valueOf(j)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onRemoved(long j, int i, int i2) {
            SemIrisManager.this.mHandler.obtainMessage(105, i, i2, Long.valueOf(j)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onIRImage(long j, byte[] bArr, int i, int i2) {
            SemIrisManager.this.mHandler.obtainMessage(106, i, i2, bArr).sendToTarget();
        }
    };

    public static abstract class AuthenticationCallback {
        public void onAuthenticationAcquired(int i) {
        }

        public void onAuthenticationError(int i, CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationHelp(int i, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }

        public void onIRImage(byte[] bArr, int i, int i2) {
        }
    }

    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int i, CharSequence charSequence) {
        }

        public void onEnrollmentHelp(int i, CharSequence charSequence) {
        }

        public void onEnrollmentProgress(int i) {
        }

        public void onIRImage(byte[] bArr, int i, int i2) {
        }
    }

    public static abstract class LockoutResetCallback {
        public void onLockoutReset() {
        }
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(Iris iris, int i, CharSequence charSequence) {
        }

        public void onRemovalSucceeded(Iris iris) {
        }
    }

    public static abstract class RequestCallback {
        public void onRequested(int i) {
        }
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

        public OnAuthenticationCancelListener(CryptoObject cryptoObject) {
            this.mCrypto = cryptoObject;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            SemIrisManager.this.cancelAuthentication(this.mCrypto);
        }
    }

    public static final class CryptoObject {
        private final Object mCrypto;
        private final byte[] mFidoRequestData;
        private byte[] mFidoResultData = null;

        public CryptoObject(Signature signature, byte[] bArr) {
            this.mCrypto = signature;
            this.mFidoRequestData = bArr;
        }

        public CryptoObject(Cipher cipher, byte[] bArr) {
            this.mCrypto = cipher;
            this.mFidoRequestData = bArr;
        }

        public CryptoObject(Mac mac, byte[] bArr) {
            this.mCrypto = mac;
            this.mFidoRequestData = bArr;
        }

        public Signature getSignature() {
            Object obj = this.mCrypto;
            if (obj instanceof Signature) {
                return (Signature) obj;
            }
            return null;
        }

        public Cipher getCipher() {
            Object obj = this.mCrypto;
            if (obj instanceof Cipher) {
                return (Cipher) obj;
            }
            return null;
        }

        public Mac getMac() {
            Object obj = this.mCrypto;
            if (obj instanceof Mac) {
                return (Mac) obj;
            }
            return null;
        }

        public long getOpId() {
            Object obj = this.mCrypto;
            if (obj != null) {
                return AndroidKeyStoreProvider.getKeyStoreOperationHandle(obj);
            }
            return 0L;
        }

        public byte[] getFidoRequestData() {
            return this.mFidoRequestData;
        }

        public byte[] getFidoResultData() {
            return this.mFidoResultData;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFidoResultData(byte[] bArr) {
            this.mFidoResultData = bArr;
        }
    }

    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private Iris mIris;

        public AuthenticationResult(CryptoObject cryptoObject, Iris iris) {
            this.mCryptoObject = cryptoObject;
            this.mIris = iris;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Iris getIris() {
            return this.mIris;
        }
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int i, AuthenticationCallback authenticationCallback, Handler handler, View view) {
        authenticate(cryptoObject, cancellationSignal, i, authenticationCallback, handler, view, UserHandle.myUserId());
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int i, AuthenticationCallback authenticationCallback, Handler handler, View view, int i2) {
        authenticate(cryptoObject, cancellationSignal, i, authenticationCallback, handler, i2, null, view);
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int i, AuthenticationCallback authenticationCallback, Handler handler, int i2, Bundle bundle, View view) {
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancellationSignal != null) {
            if (cancellationSignal.isCanceled()) {
                Log.w(TAG, "authentication already canceled");
                return;
            }
            cancellationSignal.setOnCancelListener(new OnAuthenticationCancelListener(cryptoObject));
        }
        if (ensureServiceConnected() && this.mService != null) {
            try {
                useHandler(handler);
                this.mEnrollmentCallback = null;
                this.mAuthenticationCallback = authenticationCallback;
                this.mCryptoObject = cryptoObject;
                long opId = cryptoObject != null ? cryptoObject.getOpId() : 0L;
                byte[] fidoRequestData = cryptoObject != null ? this.mCryptoObject.getFidoRequestData() : null;
                if (view == null) {
                    this.mService.authenticate(this.mToken, null, 0, 0, 0, 0, opId, i2, this.mServiceReceiver, i, this.mContext.getOpPackageName(), bundle, fidoRequestData);
                } else {
                    this.mAuthBegin = System.currentTimeMillis();
                    checkAuthViewWindowToken(cryptoObject, cancellationSignal, i, authenticationCallback, handler, i2, bundle, view, opId, fidoRequestData);
                }
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote exception while authenticating");
                authenticationCallback.onAuthenticationError(1, getErrorString(1));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAuthViewWindowToken(final CryptoObject cryptoObject, final CancellationSignal cancellationSignal, final int i, final AuthenticationCallback authenticationCallback, final Handler handler, final int i2, final Bundle bundle, final View view, final long j, final byte[] bArr) {
        if (this.mGetterHandler == null) {
            this.mGetterHandler = new GetterHandler(Looper.getMainLooper());
        }
        if (view.getWindowToken() == null) {
            Log.e(TAG, "checking AuthViewWindowToken");
            if (System.currentTimeMillis() - this.mAuthBegin < JobInfo.MIN_BACKOFF_MILLIS) {
                this.mGetterHandler.postGetterCallback(new Runnable() { // from class: com.samsung.android.camera.iris.SemIrisManager.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SemIrisManager.this.checkAuthViewWindowToken(cryptoObject, cancellationSignal, i, authenticationCallback, handler, i2, bundle, view, j, bArr);
                    }
                });
                return;
            }
            Log.e(TAG, "checkAuthViewWindowToken is null");
            this.mGetterHandler.removeAllGetterCallbacks();
            if (authenticationCallback != null) {
                authenticationCallback.onAuthenticationError(1, getErrorString(1));
                return;
            }
            return;
        }
        this.mGetterHandler.removeAllGetterCallbacks();
        try {
            IBinder windowToken = view.getWindowToken();
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            if (this.mToken == null) {
                Log.e(TAG, "mToken null");
            }
            Size minimumIrisViewSize = getMinimumIrisViewSize();
            if ((view.getWidth() < minimumIrisViewSize.getWidth() || view.getHeight() < minimumIrisViewSize.getHeight()) && authenticationCallback != null) {
                Log.e(TAG, "Invalid irisView size. IrisView's proper size:" + minimumIrisViewSize.getWidth() + "x" + minimumIrisViewSize.getHeight() + ", but app's size:" + view.getWidth() + "x" + view.getHeight());
            }
            this.mService.authenticate(this.mToken, windowToken, iArr[0], iArr[1], view.getWidth(), view.getHeight(), j, i2, this.mServiceReceiver, i, this.mContext.getOpPackageName(), bundle, bArr);
        } catch (RemoteException unused) {
            Log.w(TAG, "Remote exception while authenticating");
            if (authenticationCallback != null) {
                authenticationCallback.onAuthenticationError(1, getErrorString(1));
            }
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
            if (message.what != 1) {
                return;
            }
            ((Runnable) message.obj).run();
        }

        public void postGetterCallback(Runnable runnable) {
            postDelayedGetterCallback(runnable, 10L);
        }

        public void postDelayedGetterCallback(Runnable runnable, long j) {
            runnable.getClass();
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = runnable;
            sendMessageDelayed(obtain, j);
        }

        public void removeAllGetterCallbacks() {
            removeMessages(1);
        }
    }

    public void enroll(byte[] bArr, CancellationSignal cancellationSignal, int i, EnrollmentCallback enrollmentCallback, View view) {
        enroll(bArr, cancellationSignal, i, getCurrentUserId(), enrollmentCallback, null, view);
    }

    public void enroll(byte[] bArr, CancellationSignal cancellationSignal, int i, int i2, EnrollmentCallback enrollmentCallback, Bundle bundle, View view) {
        String str;
        int currentUserId = i2 == -2 ? getCurrentUserId() : i2;
        if (enrollmentCallback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancellationSignal != null) {
            if (cancellationSignal.isCanceled()) {
                Log.w(TAG, "enrollment already canceled");
                return;
            }
            cancellationSignal.setOnCancelListener(new OnEnrollCancelListener());
        }
        if (!ensureServiceConnected() || this.mService == null) {
            return;
        }
        try {
            this.mAuthenticationCallback = null;
            this.mEnrollmentCallback = enrollmentCallback;
            if (view == null) {
                Log.v(TAG, "irisView null");
                this.mService.enroll(this.mToken, null, 0, 0, 0, 0, bArr, currentUserId, this.mServiceReceiver, i, this.mContext.getOpPackageName(), bundle);
                return;
            }
            Log.v(TAG, "irisView not null");
            str = TAG;
            try {
                checkEnrollViewWindowToken(bArr, cancellationSignal, i, currentUserId, enrollmentCallback, bundle, view);
            } catch (RemoteException unused) {
                Log.w(str, "Remote exception in enroll");
                enrollmentCallback.onEnrollmentError(1, getErrorString(1));
            }
        } catch (RemoteException unused2) {
            str = TAG;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkEnrollViewWindowToken(final byte[] bArr, final CancellationSignal cancellationSignal, final int i, final int i2, final EnrollmentCallback enrollmentCallback, final Bundle bundle, final View view) {
        int i3;
        Log.v(TAG, "checkEnrollViewWindowToken");
        if (this.mGetterHandler == null) {
            this.mGetterHandler = new GetterHandler(Looper.getMainLooper());
        }
        if (view.getWindowToken() == null) {
            Log.v(TAG, "check, irisView.getWindowToken() is null");
            this.mGetterHandler.postGetterCallback(new Runnable() { // from class: com.samsung.android.camera.iris.SemIrisManager.2
                @Override // java.lang.Runnable
                public void run() {
                    SemIrisManager.this.checkEnrollViewWindowToken(bArr, cancellationSignal, i, i2, enrollmentCallback, bundle, view);
                }
            });
            return;
        }
        this.mGetterHandler.removeAllGetterCallbacks();
        try {
            IBinder windowToken = view.getWindowToken();
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            if (this.mToken == null) {
                Log.v(TAG, "mToken null");
            }
            Log.v(TAG, "check, irisView.Width=" + view.getWidth() + "irisView.Height=" + view.getHeight());
            i3 = 1;
            try {
                this.mService.enroll(this.mToken, windowToken, iArr[0], iArr[1], view.getWidth(), view.getHeight(), bArr, i2, this.mServiceReceiver, i, this.mContext.getOpPackageName(), bundle);
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote exception in enroll");
                if (enrollmentCallback != null) {
                    enrollmentCallback.onEnrollmentError(i3, getErrorString(i3));
                }
            }
        } catch (RemoteException unused2) {
            i3 = 1;
        }
    }

    public long preEnroll() {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.preEnroll(this.mToken);
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote exception in enroll");
            }
        }
        return 0L;
    }

    public int postEnroll() {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.postEnroll(this.mToken);
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote exception in post enroll");
            }
        }
        return 0;
    }

    public void setActiveUser(int i) {
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                iIrisService.setActiveUser(i);
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote exception in setActiveUser");
            }
        }
    }

    public void remove(Iris iris, int i, RemovalCallback removalCallback) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                this.mRemovalCallback = removalCallback;
                this.mRemovalIris = iris;
                iIrisService.remove(this.mToken, iris.getIrisId(), iris.getGroupId(), i, this.mServiceReceiver);
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote exception in remove");
                if (removalCallback != null) {
                    removalCallback.onRemovalError(iris, 1, getErrorString(1));
                }
            }
        }
    }

    public void remove(Iris iris, RemovalCallback removalCallback) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                this.mRemovalCallback = removalCallback;
                this.mRemovalIris = iris;
                iIrisService.remove(this.mToken, iris.getIrisId(), iris.getGroupId(), getCurrentUserId(), this.mServiceReceiver);
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote exception in remove");
                if (removalCallback != null) {
                    removalCallback.onRemovalError(iris, 1, getErrorString(1));
                }
            }
        }
    }

    public void rename(int i, int i2, String str) {
        if (ensureServiceConnected()) {
            IIrisService iIrisService = this.mService;
            if (iIrisService != null) {
                try {
                    iIrisService.rename(i, i2, str);
                    return;
                } catch (RemoteException unused) {
                    Log.v(TAG, "Remote exception in rename()");
                    return;
                }
            }
            Log.w(TAG, "rename(): Service not connected!");
        }
    }

    public List<Iris> getEnrolledIrises(int i) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.getEnrolledIrises(i, this.mContext.getOpPackageName());
            } catch (RemoteException unused) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        return null;
    }

    public List<Iris> getEnrolledIrises() {
        return getEnrolledIrises(UserHandle.myUserId());
    }

    public boolean hasEnrolledIrises() {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.hasEnrolledIrises(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException unused) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        return false;
    }

    public boolean hasDisabledIris() {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.hasDisabledIris(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException unused) {
                Log.v(TAG, "Remote exception in getEnrolledFaces");
            }
        }
        return false;
    }

    public boolean hasEnrolledIrises(int i) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.hasEnrolledIrises(i, this.mContext.getOpPackageName());
            } catch (RemoteException unused) {
                Log.v(TAG, "Remote exception in getEnrolledIrises, userId : " + i);
            }
        }
        return false;
    }

    public boolean isHardwareDetected() {
        Log.w(TAG, "isIrisHardwareDetected()");
        Context context = this.mContext;
        if (context != null) {
            return context.getPackageManager().hasSystemFeature(SYSTEM_FEATURE_IRIS);
        }
        return false;
    }

    public Size getMinimumIrisViewSize() {
        int i;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int round = Math.round(displayMetrics.density);
        if (displayMetrics.widthPixels < displayMetrics.heightPixels) {
            i = displayMetrics.widthPixels / round;
        } else {
            i = displayMetrics.heightPixels / round;
        }
        return new Size(i * round, ((int) (i / 1.7777778f)) * round);
    }

    public void setIrisViewType(int i) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                iIrisService.setIrisViewType(UserHandle.myUserId(), this.mContext.getOpPackageName(), i);
            } catch (RemoteException unused) {
                Log.v(TAG, "Remote exception in setIrisViewType");
            }
        }
    }

    public void enableIRImageCallback(boolean z) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                if (z) {
                    iIrisService.enableIRImageCallback(UserHandle.myUserId(), this.mContext.getOpPackageName(), 6);
                } else {
                    iIrisService.enableIRImageCallback(UserHandle.myUserId(), this.mContext.getOpPackageName(), 7);
                }
            } catch (RemoteException unused) {
                Log.v(TAG, "Remote exception in enableIRImageCallback");
            }
        }
    }

    public SparseArray getEnrolledIrisUniqueID() {
        List<Iris> enrolledIrises;
        if (!ensureServiceConnected()) {
            return null;
        }
        SparseArray sparseArray = new SparseArray();
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                enrolledIrises = iIrisService.getEnrolledIrises(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException unused) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
            if (enrolledIrises != null || enrolledIrises.size() <= 0 || this.mContext == null) {
                return null;
            }
            Iterator<Iris> it = enrolledIrises.iterator();
            int i = 1;
            while (it.hasNext()) {
                sparseArray.put(i, byteArrayToHex(requestGetUniqueID(it.next().getIrisId(), this.mContext.getOpPackageName())));
                i++;
            }
            return sparseArray;
        }
        enrolledIrises = null;
        return enrolledIrises != null ? null : null;
    }

    public int request(int i, byte[] bArr, byte[] bArr2, int i2, RequestCallback requestCallback) {
        if (!ensureServiceConnected()) {
            return 0;
        }
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            if (bArr == null) {
                try {
                    bArr = new byte[0];
                } catch (RemoteException unused) {
                    Log.v(TAG, "Remote exception in request()");
                    return -2;
                }
            }
            byte[] bArr3 = bArr;
            if (bArr2 == null) {
                bArr2 = new byte[0];
            }
            this.mRequestCallback = requestCallback;
            return iIrisService.request(this.mToken, i, bArr3, bArr2, i2, getCurrentUserId(), this.mServiceReceiver);
        }
        Log.w(TAG, "request(): Service not connected!");
        return -2;
    }

    public boolean isEnrollSession() {
        return request(1002, null, null, 0, null) > 0;
    }

    public boolean requestSessionOpen() {
        return request(2, null, null, 0, null) >= 0;
    }

    public byte[] requestGetVersion() {
        byte[] bArr = new byte[256];
        int request = request(4, null, bArr, 0, null);
        if (request <= 0) {
            return null;
        }
        return Arrays.copyOf(bArr, request);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private byte[] requestGetUniqueID(int r11, java.lang.String r12) {
        /*
            r10 = this;
            boolean r0 = r10.ensureServiceConnected()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            r0 = 256(0x100, float:3.59E-43)
            byte[] r6 = new byte[r0]
            com.samsung.android.camera.iris.IIrisService r2 = r10.mService
            if (r2 == 0) goto L2a
            android.os.IBinder r3 = r10.mToken     // Catch: android.os.RemoteException -> L23
            byte[] r5 = r12.getBytes()     // Catch: android.os.RemoteException -> L23
            int r8 = android.os.UserHandle.myUserId()     // Catch: android.os.RemoteException -> L23
            com.samsung.android.camera.iris.IIrisServiceReceiver r9 = r10.mServiceReceiver     // Catch: android.os.RemoteException -> L23
            r4 = 7
            r7 = r11
            int r10 = r2.request(r3, r4, r5, r6, r7, r8, r9)     // Catch: android.os.RemoteException -> L23
            goto L2b
        L23:
            java.lang.String r10 = "SemIrisManager"
            java.lang.String r11 = "Remote exception in request()"
            android.util.Log.v(r10, r11)
        L2a:
            r10 = 0
        L2b:
            if (r10 > 0) goto L2e
            return r1
        L2e:
            byte[] r10 = java.util.Arrays.copyOf(r6, r10)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.camera.iris.SemIrisManager.requestGetUniqueID(int, java.lang.String):byte[]");
    }

    public byte[] requestProcessFIDO(byte[] bArr) {
        byte[] bArr2 = new byte[10240];
        int request = request(9, bArr, bArr2, 0, null);
        if (request <= 0) {
            return null;
        }
        return Arrays.copyOf(bArr2, request);
    }

    public boolean requestUpdateSID(byte[] bArr) {
        return request(10, bArr, null, 0, null) >= 0;
    }

    public boolean requestLedOn() {
        return request(2001, null, null, 0, null) >= 0;
    }

    public boolean requestFullPreview() {
        return request(2000, null, null, 0, null) >= 0;
    }

    public boolean requestPreviewMode() {
        return request(2003, null, null, 0, null) >= 0;
    }

    public boolean requestCapture() {
        return request(2002, null, null, 0, null) >= 0;
    }

    public boolean requestCameraVersion() {
        return request(2004, null, null, 0, null) >= 0;
    }

    public long getAuthenticatorId() {
        if (!ensureServiceConnected()) {
            return 0L;
        }
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                return iIrisService.getAuthenticatorId(this.mContext.getOpPackageName());
            } catch (RemoteException unused) {
                Log.v(TAG, "Remote exception in getAuthenticatorId()");
            }
        } else {
            Log.w(TAG, "getAuthenticatorId(): Service not connected!");
        }
        return 0L;
    }

    public void resetTimeout(byte[] bArr) {
        if (ensureServiceConnected()) {
            IIrisService iIrisService = this.mService;
            if (iIrisService != null) {
                try {
                    iIrisService.resetTimeout(bArr);
                    return;
                } catch (RemoteException unused) {
                    Log.v(TAG, "Remote exception in resetTimeout()");
                    return;
                }
            }
            Log.w(TAG, "resetTimeout(): Service not connected!");
        }
    }

    public void addLockoutResetCallback(final LockoutResetCallback lockoutResetCallback) {
        if (ensureServiceConnected()) {
            if (this.mService != null) {
                try {
                    final PowerManager powerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
                    this.mService.addLockoutResetCallback(new IIrisServiceLockoutResetCallback.Stub() { // from class: com.samsung.android.camera.iris.SemIrisManager.3
                        @Override // com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback
                        public void onLockoutReset(long j) throws RemoteException {
                            final PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "lockoutResetCallback");
                            newWakeLock.acquire();
                            SemIrisManager.this.mHandler.post(new Runnable() { // from class: com.samsung.android.camera.iris.SemIrisManager.3.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    try {
                                        lockoutResetCallback.onLockoutReset();
                                    } finally {
                                        newWakeLock.release();
                                    }
                                }
                            });
                        }
                    });
                    return;
                } catch (RemoteException unused) {
                    Log.v(TAG, "Remote exception in addLockoutResetCallback()");
                    return;
                }
            }
            Log.w(TAG, "addLockoutResetCallback(): Service not connected!");
        }
    }

    private class MyHandler extends Handler {
        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 100:
                    sendEnrollResult((Iris) message.obj, message.arg1);
                    break;
                case 101:
                    sendAcquiredResult(((Long) message.obj).longValue(), message.arg1);
                    break;
                case 102:
                    sendAuthenticatedSucceeded((Iris) message.obj);
                    break;
                case 103:
                    sendAuthenticatedFailed();
                    break;
                case 104:
                    sendErrorResult(((Long) message.obj).longValue(), message.arg1);
                    break;
                case 105:
                    sendRemovedResult(((Long) message.obj).longValue(), message.arg1, message.arg2);
                    break;
                case 106:
                    sendIRImage((byte[]) message.obj, message.arg1, message.arg2);
                    break;
                case 107:
                    sendAuthenticatedSucceededFidoResultData((byte[]) message.obj);
                    break;
            }
        }

        private void sendIRImage(byte[] bArr, int i, int i2) {
            Log.w(SemIrisManager.TAG, "sendIRImage, width : " + i + " height : " + i2);
            if (SemIrisManager.this.mEnrollmentCallback != null) {
                SemIrisManager.this.mEnrollmentCallback.onIRImage(bArr, i, i2);
            }
            if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onIRImage(bArr, i, i2);
            }
        }

        private void sendRemovedResult(long j, int i, int i2) {
            if (SemIrisManager.this.mRemovalCallback != null) {
                int irisId = SemIrisManager.this.mRemovalIris.getIrisId();
                int groupId = SemIrisManager.this.mRemovalIris.getGroupId();
                if (i != irisId) {
                    Log.w(SemIrisManager.TAG, "Iris id didn't match: " + i + " != " + irisId);
                }
                if (i2 != groupId) {
                    Log.w(SemIrisManager.TAG, "Group id didn't match: " + i2 + " != " + groupId);
                }
                SemIrisManager.this.mRemovalCallback.onRemovalSucceeded(SemIrisManager.this.mRemovalIris);
            }
        }

        private void sendErrorResult(long j, int i) {
            Log.w(SemIrisManager.TAG, "sendErrorResult, errMsgId : " + i);
            if (i == 4) {
                return;
            }
            if (SemIrisManager.this.mEnrollmentCallback != null) {
                SemIrisManager.this.mEnrollmentCallback.onEnrollmentError(i, SemIrisManager.this.getErrorString(i));
            } else if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationError(i, SemIrisManager.this.getErrorString(i));
            } else if (SemIrisManager.this.mRemovalCallback != null) {
                SemIrisManager.this.mRemovalCallback.onRemovalError(SemIrisManager.this.mRemovalIris, i, SemIrisManager.this.getErrorString(i));
            }
        }

        private void sendEnrollResult(Iris iris, int i) {
            if (SemIrisManager.this.mEnrollmentCallback != null) {
                SemIrisManager.this.mEnrollmentCallback.onEnrollmentProgress(i);
            }
        }

        private void sendAuthenticatedSucceededFidoResultData(byte[] bArr) {
            Log.w(SemIrisManager.TAG, "sendAuthenticatedSucceededFidoResultData, fidoResultData : " + Arrays.toString(bArr));
            if (SemIrisManager.this.mCryptoObject != null) {
                SemIrisManager.this.mCryptoObject.setFidoResultData(bArr);
            }
        }

        private void sendAuthenticatedSucceeded(Iris iris) {
            Log.w(SemIrisManager.TAG, "sendAuthenticatedSucceeded, ir : " + iris);
            if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationSucceeded(new AuthenticationResult(SemIrisManager.this.mCryptoObject, iris));
            }
        }

        private void sendAuthenticatedFailed() {
            if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationFailed();
            }
        }

        private void sendAcquiredResult(long j, int i) {
            if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationAcquired(i);
            }
            String acquiredString = SemIrisManager.this.getAcquiredString(i);
            if (acquiredString == null) {
                return;
            }
            if (SemIrisManager.this.mEnrollmentCallback != null) {
                SemIrisManager.this.mEnrollmentCallback.onEnrollmentHelp(i, acquiredString);
            } else if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationHelp(i, acquiredString);
            }
        }
    }

    public SemIrisManager(Context context, IIrisService iIrisService) {
        this.mContext = context;
        this.mService = iIrisService;
        if (iIrisService == null) {
            Log.v(TAG, "SemIrisManagerService was null");
        }
        this.mHandler = new MyHandler(context);
        this.mGetterHandler = new GetterHandler(context);
    }

    private int getCurrentUserId() {
        try {
            return ActivityManagerNative.getDefault().getCurrentUser().id;
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get current user id\n");
            return -10000;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelEnrollment() {
        IIrisService iIrisService;
        Log.e(TAG, "cancelEnrollment");
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                iIrisService.cancelEnrollment(this.mToken);
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote exception while canceling enrollment");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(CryptoObject cryptoObject) {
        IIrisService iIrisService;
        Log.e(TAG, "cancelAuthentication");
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                iIrisService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote exception while canceling authentication");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getErrorString(int i) {
        Resources resources;
        try {
            resources = this.mContext.getPackageManager().getResourcesForApplication("com.samsung.android.server.iris");
        } catch (Exception e) {
            Log.e(TAG, "getErrorString, Exception = " + e);
            resources = null;
        }
        if (resources == null) {
            Log.e(TAG, "mRes is null");
            return null;
        }
        if (i != 25) {
            try {
                if (i != 123) {
                    switch (i) {
                        case 0:
                            return resources.getString(resources.getIdentifier("iris_error_sensor_no_response", "string", "com.samsung.android.server.iris"));
                        case 1:
                            return resources.getString(resources.getIdentifier("iris_error_unable_to_process", "string", "com.samsung.android.server.iris"));
                        case 2:
                            return resources.getString(resources.getIdentifier("iris_error_timeout", "string", "com.samsung.android.server.iris"));
                        case 3:
                            return resources.getString(resources.getIdentifier("iris_error_no_space", "string", "com.samsung.android.server.iris"));
                        case 4:
                            return resources.getString(resources.getIdentifier("iris_error_canceled", "string", "com.samsung.android.server.iris"));
                        case 5:
                            return resources.getString(resources.getIdentifier("iris_error_unable_to_remove", "string", "com.samsung.android.server.iris"));
                        case 6:
                            return resources.getString(resources.getIdentifier("iris_error_lockout", "string", "com.samsung.android.server.iris"));
                        case 7:
                        case 8:
                            return "";
                        case 9:
                            return resources.getString(resources.getIdentifier("iris_error_eye_safety_timeout", "string", "com.samsung.android.server.iris"));
                        case 10:
                            return resources.getString(resources.getIdentifier("iris_error_auth_view_size", "string", "com.samsung.android.server.iris"));
                        default:
                            switch (i) {
                                case 12:
                                    return resources.getString(resources.getIdentifier("iris_error_proximity_timeout", "string", "com.samsung.android.server.iris"));
                                case 13:
                                    return resources.getString(resources.getIdentifier("iris_error_evicted", "string", "com.samsung.android.server.iris"));
                                case 14:
                                    return resources.getString(resources.getIdentifier("iris_error_video_call_interrupt", "string", "com.samsung.android.server.iris"));
                                case 15:
                                    return resources.getString(resources.getIdentifier("iris_error_no_eye_detected", "string", "com.samsung.android.server.iris"));
                                default:
                                    switch (i) {
                                        case 17:
                                            return resources.getString(resources.getIdentifier("iris_error_flip_off", "string", "com.samsung.android.server.iris"));
                                        case 18:
                                            return resources.getString(resources.getIdentifier("iris_error_need_set_lock_type", "string", "com.samsung.android.server.iris"));
                                        case 19:
                                            return resources.getString(resources.getIdentifier("iris_error_while_camera_in_use", "string", "com.samsung.android.server.iris"));
                                        case 20:
                                            return resources.getString(resources.getIdentifier("iris_error_unsupported_orientation", "string", "com.samsung.android.server.iris"));
                                        default:
                                            return resources.getString(resources.getIdentifier("iris_error_unable_to_process", "string", "com.samsung.android.server.iris"));
                                    }
                            }
                    }
                }
                return resources.getString(resources.getIdentifier("iris_error_proximity_alert", "string", "com.samsung.android.server.iris"));
            } catch (Resources.NotFoundException e2) {
                Log.d(TAG, "getErrorString, NotFoundException = " + e2);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAcquiredString(int i) {
        Resources resources;
        try {
            resources = this.mContext.getPackageManager().getResourcesForApplication("com.samsung.android.server.iris");
        } catch (Exception e) {
            Log.e(TAG, "getAcquiredString, Exception = " + e);
            resources = null;
        }
        if (resources == null) {
            Log.e(TAG, "mRes is null");
            return null;
        }
        try {
            if (i == 3) {
                return resources.getString(resources.getIdentifier("iris_acquired_move_closer", "string", "com.samsung.android.server.iris"));
            }
            if (i == 4) {
                return resources.getString(resources.getIdentifier("iris_acquired_move_farther", "string", "com.samsung.android.server.iris"));
            }
            if (i == 9) {
                return resources.getString(resources.getIdentifier("iris_acquired_open_wider", "string", "com.samsung.android.server.iris"));
            }
            if (i == 11) {
                return resources.getString(resources.getIdentifier("iris_acquired_move_somewhere_darker", "string", "com.samsung.android.server.iris"));
            }
            if (i != 12) {
                return null;
            }
            return resources.getString(resources.getIdentifier("iris_acquired_change_your_position", "string", "com.samsung.android.server.iris"));
        } catch (Resources.NotFoundException e2) {
            Log.d(TAG, "getAcquiredString, NotFoundException = " + e2);
            return null;
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

    public IIrisService getService() {
        ensureServiceConnected();
        return this.mService;
    }

    private synchronized boolean ensureServiceConnected() {
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                iIrisService.isHardwareDetected(0L, this.mContext.getOpPackageName());
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
        for (int i = 1; i <= 20; i++) {
            IIrisService asInterface = IIrisService.Stub.asInterface(ServiceManager.getService("samsung.iris"));
            this.mService = asInterface;
            if (asInterface != null) {
                Log.v(TAG, "Service connected!");
                return;
            }
            try {
                Thread.sleep(50L);
            } catch (InterruptedException unused) {
            }
        }
    }

    private static String byteArrayToHex(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    private static String bytesToString(byte[] bArr, int i) {
        if (i > bArr.length || i < 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(i * 2);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(String.format("%c", Integer.valueOf(bArr[i2] & 255)));
        }
        return sb.toString();
    }
}
