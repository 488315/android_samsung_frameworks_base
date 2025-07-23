package android.hardware.biometrics;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.IAuthService;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.security.identity.IdentityCredential;
import android.security.identity.PresentationSession;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.util.FrameworkStatsLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Signature;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;

/* loaded from: classes2.dex */
public class BiometricPrompt implements BiometricAuthenticator, BiometricConstants {
    public static final int AUTHENTICATION_RESULT_TYPE_BIOMETRIC = 2;
    public static final int AUTHENTICATION_RESULT_TYPE_DEVICE_CREDENTIAL = 1;
    private static final boolean DEBUG = Debug.semIsProductDev();
    public static final int DISMISSED_REASON_BIOMETRIC_CONFIRMED = 1;
    public static final int DISMISSED_REASON_BIOMETRIC_CONFIRM_NOT_REQUIRED = 4;
    public static final int DISMISSED_REASON_CONTENT_VIEW_MORE_OPTIONS = 8;
    public static final int DISMISSED_REASON_CREDENTIAL_CONFIRMED = 7;
    public static final int DISMISSED_REASON_ERROR = 5;
    public static final int DISMISSED_REASON_ERROR_NO_WM = 9;
    public static final int DISMISSED_REASON_NEGATIVE = 2;
    public static final int DISMISSED_REASON_SERVER_REQUESTED = 6;
    public static final int DISMISSED_REASON_USER_CANCEL = 3;
    public static final int HIDE_DIALOG_DELAY = 2000;
    static final int MAX_LOGO_DESCRIPTION_CHARACTER_NUMBER = 30;
    public static final int SEM_FLAG_BIOMETRIC_IDENITIFIER_ID = 2;
    public static final int SEM_FLAG_CHECK_ENROLLED_BIOMETRIC = 1;
    public static final int SEM_PRIVILEGED_FLAG_AVOID_LOCKOUT = 4;
    public static final int SEM_PRIVILEGED_FLAG_EXCLUSIVE_AUTHENTICATION = 8;
    public static final int SEM_PRIVILEGED_FLAG_KNOX_INTERNAL = 32;
    public static final int SEM_PRIVILEGED_FLAG_KNOX_ONLY_CONFIRM_BIOMETRIC = 128;
    public static final int SEM_PRIVILEGED_FLAG_KNOX_TWO_FACTOR = 64;
    public static final int SEM_PRIVILEGED_FLAG_USING_FIDO = 16;
    public static final int SEM_TYPE_DEVICE_CUSTOM_SCAN = 8;
    public static final int SEM_TYPE_FACE = 2;
    public static final int SEM_TYPE_FINGERPRINT = 1;
    public static final int SEM_TYPE_IRIS = 4;
    private static final String TAG = "BiometricPrompt";
    private AuthenticationCallback mAuthenticationCallback;
    private final IBiometricServiceReceiver mBiometricServiceReceiver;
    private final ButtonInfo mContentViewMoreOptionsButtonInfo;
    private final Context mContext;
    private CryptoObject mCryptoObject;
    private Executor mExecutor;
    private boolean mIsPromptShowing;
    private final ButtonInfo mNegativeButtonInfo;
    private final PromptInfo mPromptInfo;
    private final IAuthService mService;
    private final IBinder mToken;

    public static abstract class AuthenticationCallback extends BiometricAuthenticator.AuthenticationCallback {
        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationAcquired(int i) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int i, CharSequence charSequence) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationHelp(int i, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }

        public void onSystemEvent(int i) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthenticationResultType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DismissedReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemBiometricType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemPrivilegedFlag {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCredentialAllowed(int i) {
        return (i & 32768) != 0;
    }

    static class ButtonInfo {
        Executor executor;
        DialogInterface.OnClickListener listener;

        ButtonInfo(Executor executor, DialogInterface.OnClickListener onClickListener) {
            this.executor = executor;
            this.listener = onClickListener;
        }
    }

    public static class Builder {
        private ButtonInfo mContentViewMoreOptionsButtonInfo;
        private Context mContext;
        private ButtonInfo mNegativeButtonInfo;
        private PromptInfo mPromptInfo = new PromptInfo();
        private IAuthService mService;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setLogoRes(int i) {
            if (this.mPromptInfo.getLogoBitmap() != null) {
                throw new IllegalStateException("Exclusively one of logo resource or logo bitmap can be set");
            }
            if (i != 0) {
                this.mPromptInfo.setLogo(i, BiometricPrompt.convertDrawableToBitmap(this.mContext.getDrawable(i)));
            }
            return this;
        }

        public Builder setLogoBitmap(Bitmap bitmap) {
            if (this.mPromptInfo.getLogoRes() != 0) {
                throw new IllegalStateException("Exclusively one of logo resource or logo bitmap can be set");
            }
            this.mPromptInfo.setLogo(0, bitmap);
            return this;
        }

        public Builder setLogoDescription(String str) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("Logo description passed in can not be null");
            }
            if (str.length() > 30) {
                Log.w(BiometricPrompt.TAG, "Logo description passed in exceeds30 character number and may be truncated.");
            }
            this.mPromptInfo.setLogoDescription(str);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.mPromptInfo.setTitle(charSequence);
            return this;
        }

        public Builder setUseDefaultTitle() {
            this.mPromptInfo.setUseDefaultTitle(true);
            return this;
        }

        public Builder setSubtitle(CharSequence charSequence) {
            this.mPromptInfo.setSubtitle(charSequence);
            return this;
        }

        public Builder setUseDefaultSubtitle() {
            this.mPromptInfo.setUseDefaultSubtitle(true);
            return this;
        }

        public Builder setDescription(CharSequence charSequence) {
            this.mPromptInfo.setDescription(charSequence);
            return this;
        }

        public Builder setContentView(PromptContentView promptContentView) {
            this.mPromptInfo.setContentView(promptContentView);
            if (this.mPromptInfo.isContentViewMoreOptionsButtonUsed()) {
                this.mContentViewMoreOptionsButtonInfo = ((PromptContentViewWithMoreOptionsButton) promptContentView).getButtonInfo();
            }
            return this;
        }

        public Builder setService(IAuthService iAuthService) {
            this.mService = iAuthService;
            return this;
        }

        public Builder setTextForDeviceCredential(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
            if (charSequence != null) {
                this.mPromptInfo.setDeviceCredentialTitle(charSequence);
            }
            if (charSequence2 != null) {
                this.mPromptInfo.setDeviceCredentialSubtitle(charSequence2);
            }
            if (charSequence3 != null) {
                this.mPromptInfo.setDeviceCredentialDescription(charSequence3);
            }
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, Executor executor, DialogInterface.OnClickListener onClickListener) {
            if (TextUtils.isEmpty(charSequence)) {
                throw new IllegalArgumentException("Text must be set and non-empty");
            }
            if (executor == null) {
                throw new IllegalArgumentException("Executor must not be null");
            }
            if (onClickListener == null) {
                throw new IllegalArgumentException("Listener must not be null");
            }
            this.mPromptInfo.setNegativeButtonText(charSequence);
            this.mNegativeButtonInfo = new ButtonInfo(executor, onClickListener);
            return this;
        }

        public Builder setConfirmationRequired(boolean z) {
            this.mPromptInfo.setConfirmationRequested(z);
            return this;
        }

        @Deprecated
        public Builder setDeviceCredentialAllowed(boolean z) {
            this.mPromptInfo.setDeviceCredentialAllowed(z);
            return this;
        }

        public Builder setAllowedAuthenticators(int i) {
            this.mPromptInfo.setAuthenticators(i);
            return this;
        }

        public Builder setAllowedSensorIds(List<Integer> list) {
            this.mPromptInfo.setAllowedSensorIds(list);
            return this;
        }

        public Builder setAllowBackgroundAuthentication(boolean z) {
            this.mPromptInfo.setAllowBackgroundAuthentication(z);
            return this;
        }

        public Builder setAllowBackgroundAuthentication(boolean z, boolean z2) {
            this.mPromptInfo.setAllowBackgroundAuthentication(z);
            this.mPromptInfo.setUseParentProfileForDeviceCredential(z2);
            return this;
        }

        public Builder setDisallowBiometricsIfPolicyExists(boolean z) {
            this.mPromptInfo.setDisallowBiometricsIfPolicyExists(z);
            return this;
        }

        public Builder setReceiveSystemEvents(boolean z) {
            this.mPromptInfo.setReceiveSystemEvents(z);
            return this;
        }

        public Builder setIgnoreEnrollmentState(boolean z) {
            this.mPromptInfo.setIgnoreEnrollmentState(z);
            return this;
        }

        public Builder setIsForLegacyFingerprintManager(int i) {
            this.mPromptInfo.setIsForLegacyFingerprintManager(i);
            return this;
        }

        public Builder setShowEmergencyCallButton(boolean z) {
            this.mPromptInfo.setShowEmergencyCallButton(z);
            return this;
        }

        public Builder setRealCallerForConfirmDeviceCredentialActivity(ComponentName componentName) {
            this.mPromptInfo.setRealCallerForConfirmDeviceCredentialActivity(componentName);
            this.mPromptInfo.setClassNameIfItIsConfirmDeviceCredentialActivity(this.mContext.getClass().getName());
            return this;
        }

        public Builder semSetBiometricType(int i) {
            if (this.mContext.checkSelfPermission(Manifest.permission.BIOMETRICS_PRIVILEGED) == -1) {
                throw new SecurityException("Must have com.samsung.android.permission.BIOMETRICS_PRIVILEGED permission.");
            }
            this.mPromptInfo.semSetBiometricType(i);
            return this;
        }

        public Builder semSetPrivilegedFlag(int i) {
            if (this.mContext.checkSelfPermission(Manifest.permission.BIOMETRICS_PRIVILEGED) == -1) {
                throw new SecurityException("Must have com.samsung.android.permission.BIOMETRICS_PRIVILEGED permission.");
            }
            this.mPromptInfo.semSetPrivilegedFlag(i);
            return this;
        }

        public BiometricPrompt build() {
            CharSequence title = this.mPromptInfo.getTitle();
            CharSequence negativeButtonText = this.mPromptInfo.getNegativeButtonText();
            boolean isUseDefaultTitle = this.mPromptInfo.isUseDefaultTitle();
            boolean z = this.mPromptInfo.isDeviceCredentialAllowed() || BiometricPrompt.isCredentialAllowed(this.mPromptInfo.getAuthenticators());
            if (TextUtils.isEmpty(title) && !isUseDefaultTitle) {
                throw new IllegalArgumentException("Title must be set and non-empty");
            }
            if (TextUtils.isEmpty(negativeButtonText) && !z) {
                throw new IllegalArgumentException("Negative text must be set and non-empty");
            }
            if (!TextUtils.isEmpty(negativeButtonText) && z) {
                throw new IllegalArgumentException("Can't have both negative button behavior and device credential enabled");
            }
            IAuthService iAuthService = this.mService;
            if (iAuthService == null) {
                iAuthService = IAuthService.Stub.asInterface(ServiceManager.getService(Context.AUTH_SERVICE));
            }
            this.mService = iAuthService;
            return new BiometricPrompt(this.mContext, this.mPromptInfo, this.mNegativeButtonInfo, this.mContentViewMoreOptionsButtonInfo, this.mService);
        }
    }

    private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnAuthenticationCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            if (!BiometricPrompt.this.mIsPromptShowing) {
                Log.w(BiometricPrompt.TAG, "BP is not showing");
                return;
            }
            Log.d(BiometricPrompt.TAG, "Cancel BP authentication requested for: " + this.mAuthRequestId);
            BiometricPrompt.this.cancelAuthentication(this.mAuthRequestId);
        }
    }

    /* renamed from: android.hardware.biometrics.BiometricPrompt$1, reason: invalid class name */
    class AnonymousClass1 extends IBiometricServiceReceiver.Stub {
        AnonymousClass1() {
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAuthenticationSucceeded(final int i) {
            Slog.i(BiometricPrompt.TAG, "onAuthenticationSucceeded: " + i);
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onAuthenticationSucceeded$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$0(int i) {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationSucceeded(new AuthenticationResult(BiometricPrompt.this.mCryptoObject, i));
            BiometricPrompt.this.mIsPromptShowing = false;
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAuthenticationFailed() {
            Slog.i(BiometricPrompt.TAG, "onAuthenticationFailed");
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onAuthenticationFailed$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationFailed$1() {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationFailed();
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onError(int i, final int i2, int i3) {
            final String errorString;
            Slog.i(BiometricPrompt.TAG, "onError: " + i2 + ", " + i3);
            if (i != 2) {
                errorString = i != 8 ? null : FaceManager.getErrorString(BiometricPrompt.this.mContext, i2, i3);
            } else {
                errorString = FingerprintManager.getErrorString(BiometricPrompt.this.mContext, i2, i3);
            }
            if (errorString == null) {
                if (i2 == 5) {
                    errorString = BiometricPrompt.this.mContext.getString(R.string.biometric_error_canceled);
                } else if (i2 == 10) {
                    errorString = BiometricPrompt.this.mContext.getString(R.string.biometric_error_user_canceled);
                } else if (i2 == 12) {
                    errorString = BiometricPrompt.this.mContext.getString(R.string.biometric_error_hw_unavailable);
                } else if (i2 == 14) {
                    errorString = BiometricPrompt.this.mContext.getString(R.string.biometric_error_device_not_secured);
                } else {
                    Log.e(BiometricPrompt.TAG, "Unknown error, modality: " + i + " error: " + i2 + " vendorCode: " + i3);
                    errorString = BiometricPrompt.this.mContext.getString(R.string.biometric_error_generic);
                }
            }
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onError$2(i2, errorString);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(int i, String str) {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationError(i, str);
            BiometricPrompt.this.mIsPromptShowing = false;
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAcquired(final int i, final String str) {
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onAcquired$3(i, str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcquired$3(int i, String str) {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationHelp(i, str);
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onDialogDismissed(int i) {
            Slog.i(BiometricPrompt.TAG, "onDialogDismissed: " + i);
            if (i == 2) {
                if (BiometricPrompt.this.mNegativeButtonInfo != null) {
                    BiometricPrompt.this.mNegativeButtonInfo.executor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            BiometricPrompt.AnonymousClass1.this.lambda$onDialogDismissed$4();
                        }
                    });
                    return;
                } else {
                    BiometricPrompt.this.mAuthenticationCallback.onAuthenticationError(10, null);
                    return;
                }
            }
            if (i == 8) {
                if (BiometricPrompt.this.mContentViewMoreOptionsButtonInfo != null) {
                    BiometricPrompt.this.mContentViewMoreOptionsButtonInfo.executor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            BiometricPrompt.AnonymousClass1.this.lambda$onDialogDismissed$5();
                        }
                    });
                }
            } else {
                BiometricPrompt.this.mIsPromptShowing = false;
                Log.e(BiometricPrompt.TAG, "Unknown reason: " + i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDialogDismissed$4() {
            BiometricPrompt.this.mNegativeButtonInfo.listener.onClick(null, -2);
            BiometricPrompt.this.mIsPromptShowing = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDialogDismissed$5() {
            BiometricPrompt.this.mContentViewMoreOptionsButtonInfo.listener.onClick(null, -2);
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onSystemEvent(final int i) {
            Slog.i(BiometricPrompt.TAG, "onSystemEvent: " + i);
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onSystemEvent$6(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemEvent$6(int i) {
            BiometricPrompt.this.mAuthenticationCallback.onSystemEvent(i);
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onSemAuthenticationSucceeded(final int i, final int i2, final byte[] bArr) {
            Slog.i(BiometricPrompt.TAG, "onSemAuthenticationSucceeded: " + i);
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onSemAuthenticationSucceeded$7(i, bArr, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemAuthenticationSucceeded$7(int i, byte[] bArr, int i2) {
            AuthenticationResult authenticationResult = new AuthenticationResult(BiometricPrompt.this.mCryptoObject, i);
            if (bArr != null) {
                authenticationResult.setToken(bArr);
            }
            authenticationResult.mBiometricId = i2;
            if (BiometricPrompt.DEBUG) {
                Slog.d(BiometricPrompt.TAG, "AuthenticationSucceeded: " + i2 + ", " + Arrays.toString(bArr));
            }
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationSucceeded(authenticationResult);
        }
    }

    private BiometricPrompt(Context context, PromptInfo promptInfo, ButtonInfo buttonInfo, ButtonInfo buttonInfo2, IAuthService iAuthService) {
        this.mToken = new Binder();
        this.mBiometricServiceReceiver = new AnonymousClass1();
        this.mContext = context;
        this.mPromptInfo = promptInfo;
        this.mNegativeButtonInfo = buttonInfo;
        this.mContentViewMoreOptionsButtonInfo = buttonInfo2;
        this.mService = iAuthService;
        this.mIsPromptShowing = false;
    }

    public int getLogoRes() {
        return this.mPromptInfo.getLogoRes();
    }

    public Bitmap getLogoBitmap() {
        return this.mPromptInfo.getLogoBitmap();
    }

    public String getLogoDescription() {
        return this.mPromptInfo.getLogoDescription();
    }

    public CharSequence getTitle() {
        return this.mPromptInfo.getTitle();
    }

    public boolean shouldUseDefaultTitle() {
        return this.mPromptInfo.isUseDefaultTitle();
    }

    public CharSequence getSubtitle() {
        return this.mPromptInfo.getSubtitle();
    }

    public boolean shouldUseDefaultSubtitle() {
        return this.mPromptInfo.isUseDefaultSubtitle();
    }

    public CharSequence getDescription() {
        return this.mPromptInfo.getDescription();
    }

    public PromptContentView getContentView() {
        return this.mPromptInfo.getContentView();
    }

    public CharSequence getNegativeButtonText() {
        return this.mPromptInfo.getNegativeButtonText();
    }

    public boolean isConfirmationRequired() {
        return this.mPromptInfo.isConfirmationRequested();
    }

    public int getAllowedAuthenticators() {
        return this.mPromptInfo.getAuthenticators();
    }

    public List<Integer> getAllowedSensorIds() {
        return this.mPromptInfo.getAllowedSensorIds();
    }

    public boolean isAllowBackgroundAuthentication() {
        return this.mPromptInfo.isAllowBackgroundAuthentication();
    }

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

        @Deprecated
        public CryptoObject(IdentityCredential identityCredential) {
            super(identityCredential);
        }

        public CryptoObject(PresentationSession presentationSession) {
            super(presentationSession);
        }

        public CryptoObject(KeyAgreement keyAgreement) {
            super(keyAgreement);
        }

        public CryptoObject(long j) {
            super(j);
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

        @Override // android.hardware.biometrics.CryptoObject
        public KeyAgreement getKeyAgreement() {
            return super.getKeyAgreement();
        }

        public long getOperationHandle() {
            return super.getOpId();
        }
    }

    public static class AuthenticationResult extends BiometricAuthenticator.AuthenticationResult {
        private int mBiometricId;
        private byte[] mToken;

        public AuthenticationResult(CryptoObject cryptoObject, int i) {
            super(cryptoObject, i, null, 0);
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationResult
        public CryptoObject getCryptoObject() {
            return (CryptoObject) super.getCryptoObject();
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationResult
        public int getAuthenticationType() {
            return super.getAuthenticationType();
        }

        public int getBiometricId() {
            return this.mBiometricId;
        }

        public void setToken(byte[] bArr) {
            this.mToken = bArr;
        }

        public byte[] semGetToken() {
            if (BiometricPrompt.DEBUG) {
                StringBuilder sb = new StringBuilder("semGetToken: token = ");
                byte[] bArr = this.mToken;
                sb.append(bArr == null ? "NULL" : Integer.valueOf(bArr.length));
                Slog.d(BiometricPrompt.TAG, sb.toString());
            }
            return this.mToken;
        }
    }

    public void authenticateUser(CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback, int i) {
        if (cancellationSignal == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        authenticateInternal(0L, cancellationSignal, executor, authenticationCallback, i);
    }

    public long authenticateForOperation(CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback, long j) {
        if (cancellationSignal == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        return authenticateInternal(j, cancellationSignal, executor, authenticationCallback, this.mContext.getUserId());
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback) {
        FrameworkStatsLog.write(353, true, this.mPromptInfo.isConfirmationRequested(), this.mPromptInfo.isDeviceCredentialAllowed(), this.mPromptInfo.getAuthenticators() != 0, this.mPromptInfo.getAuthenticators());
        if (cryptoObject == null) {
            throw new IllegalArgumentException("Must supply a crypto object");
        }
        if (cancellationSignal == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        int authenticators = this.mPromptInfo.getAuthenticators();
        if (authenticators == 0) {
            authenticators = 15;
        }
        if ((authenticators & 240) != 0) {
            throw new IllegalArgumentException("Only Strong biometrics supported with crypto");
        }
        authenticateInternal(cryptoObject, cancellationSignal, executor, authenticationCallback, this.mContext.getUserId());
    }

    public void authenticate(CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback) {
        FrameworkStatsLog.write(353, false, this.mPromptInfo.isConfirmationRequested(), this.mPromptInfo.isDeviceCredentialAllowed(), this.mPromptInfo.getAuthenticators() != 0, this.mPromptInfo.getAuthenticators());
        if (cancellationSignal == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        authenticateInternal((CryptoObject) null, cancellationSignal, executor, authenticationCallback, this.mContext.getUserId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(long j) {
        IAuthService iAuthService = this.mService;
        if (iAuthService != null) {
            try {
                iAuthService.cancelAuthentication(this.mToken, this.mContext.getPackageName(), j);
            } catch (RemoteException e) {
                Log.e(TAG, "Unable to cancel authentication", e);
            }
        }
    }

    private void authenticateInternal(CryptoObject cryptoObject, CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback, int i) {
        this.mCryptoObject = cryptoObject;
        authenticateInternal(cryptoObject != null ? cryptoObject.getOpId() : 0L, cancellationSignal, executor, authenticationCallback, i);
    }

    private long authenticateInternal(long j, CancellationSignal cancellationSignal, Executor executor, final AuthenticationCallback authenticationCallback, int i) {
        PromptInfo promptInfo;
        CryptoObject cryptoObject = this.mCryptoObject;
        if (cryptoObject != null && cryptoObject.getOpId() != j) {
            Log.w(TAG, "CryptoObject operation ID does not match argument; setting field to null");
            this.mCryptoObject = null;
        }
        try {
            if (cancellationSignal.isCanceled()) {
                Log.w(TAG, "Authentication already canceled");
                return -1L;
            }
            this.mExecutor = executor;
            this.mAuthenticationCallback = authenticationCallback;
            if (this.mIsPromptShowing) {
                final String string = this.mContext.getString(R.string.biometric_error_canceled);
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BiometricPrompt.this.lambda$authenticateInternal$0(string);
                    }
                });
                return -1L;
            }
            if (j != 0) {
                Parcel obtain = Parcel.obtain();
                this.mPromptInfo.writeToParcel(obtain, 0);
                obtain.setDataPosition(0);
                promptInfo = new PromptInfo(obtain);
                if (promptInfo.getAuthenticators() == 0) {
                    promptInfo.setAuthenticators(15);
                }
            } else {
                promptInfo = this.mPromptInfo;
            }
            PromptInfo promptInfo2 = promptInfo;
            semSetExtraInfo(promptInfo2);
            long authenticate = this.mService.authenticate(this.mToken, j, i, this.mBiometricServiceReceiver, this.mContext.getPackageName(), promptInfo2);
            cancellationSignal.setOnCancelListener(new OnAuthenticationCancelListener(authenticate));
            this.mIsPromptShowing = true;
            return authenticate;
        } catch (RemoteException e) {
            Log.e(TAG, "Remote exception while authenticating", e);
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.this.lambda$authenticateInternal$1(authenticationCallback);
                }
            });
            return -1L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticateInternal$0(String str) {
        this.mAuthenticationCallback.onAuthenticationError(5, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticateInternal$1(AuthenticationCallback authenticationCallback) {
        authenticationCallback.onAuthenticationError(1, this.mContext.getString(R.string.biometric_error_hw_unavailable));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        Bitmap createBitmap;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            createBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public void semAuthenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback, byte[] bArr) {
        if (cancellationSignal == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        if (bArr != null) {
            this.mPromptInfo.semSetChallengeData(bArr);
        }
        authenticateInternal(cryptoObject, cancellationSignal, executor, authenticationCallback, this.mContext.getUserId());
    }

    private void semSetExtraInfo(PromptInfo promptInfo) {
        int i;
        try {
            i = this.mContext.getDisplayId();
        } catch (Exception e) {
            Slog.w(TAG, "semSetExtraInfo: " + e.getMessage());
            i = 0;
        }
        promptInfo.semSetDisplayId(i);
        try {
            Context context = this.mContext;
            if (context instanceof Activity) {
                promptInfo.semSetTaskId(((Activity) context).getTaskId());
            }
        } catch (Exception e2) {
            Slog.w(TAG, "semSetExtraInfo: " + e2.getMessage());
        }
    }
}
