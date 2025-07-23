package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.security.KeyStore2;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore2.AndroidKeyStoreProvider;
import android.system.keystore2.KeyDescriptor;
import com.android.internal.widget.ILockSettings;
import java.security.Key;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SystemApi
/* loaded from: classes3.dex */
public class RecoveryController {
    private static final String APPLICATION_KEY_GRANT_PREFIX = "recoverable_key:";
    public static final int ERROR_BAD_CERTIFICATE_FORMAT = 25;
    public static final int ERROR_DECRYPTION_FAILED = 26;
    public static final int ERROR_DOWNGRADE_CERTIFICATE = 29;
    public static final int ERROR_INSECURE_USER = 23;
    public static final int ERROR_INVALID_CERTIFICATE = 28;
    public static final int ERROR_INVALID_KEY_FORMAT = 27;
    public static final int ERROR_KEY_NOT_FOUND = 30;
    public static final int ERROR_NO_SNAPSHOT_PENDING = 21;
    public static final int ERROR_SERVICE_INTERNAL_ERROR = 22;
    public static final int ERROR_SESSION_EXPIRED = 24;
    public static final int RECOVERY_STATUS_PERMANENT_FAILURE = 3;
    public static final int RECOVERY_STATUS_SYNCED = 0;
    public static final int RECOVERY_STATUS_SYNC_IN_PROGRESS = 1;
    private static final String TAG = "RecoveryController";
    private final ILockSettings mBinder;

    private RecoveryController(ILockSettings iLockSettings) {
        this.mBinder = iLockSettings;
    }

    ILockSettings getBinder() {
        return this.mBinder;
    }

    public static RecoveryController getInstance(Context context) {
        return new RecoveryController(ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings")));
    }

    public static boolean isRecoverableKeyStoreEnabled(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        return keyguardManager != null && keyguardManager.isDeviceSecure();
    }

    public void initRecoveryService(String str, byte[] bArr, byte[] bArr2) throws CertificateException, InternalRecoveryServiceException {
        try {
            this.mBinder.initRecoveryServiceWithSigFile(str, bArr, bArr2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 25 || e2.errorCode == 28) {
                throw new CertificateException("Invalid certificate for recovery service", e2);
            }
            if (e2.errorCode == 29) {
                throw new CertificateException("Downgrading certificate serial version isn't supported.", e2);
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public KeyChainSnapshot getKeyChainSnapshot() throws InternalRecoveryServiceException {
        try {
            return this.mBinder.getKeyChainSnapshot();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 21) {
                return null;
            }
            throw this.wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) throws InternalRecoveryServiceException {
        try {
            this.mBinder.setSnapshotCreatedPendingIntent(pendingIntent);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public void setServerParams(byte[] bArr) throws InternalRecoveryServiceException {
        try {
            this.mBinder.setServerParams(bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public List<String> getAliases() throws InternalRecoveryServiceException {
        try {
            return new ArrayList(this.mBinder.getRecoveryStatus().keySet());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public void setRecoveryStatus(String str, int i) throws InternalRecoveryServiceException {
        try {
            this.mBinder.setRecoveryStatus(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public int getRecoveryStatus(String str) throws InternalRecoveryServiceException {
        try {
            Integer num = (Integer) this.mBinder.getRecoveryStatus().get(str);
            if (num == null) {
                return 3;
            }
            return num.intValue();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw this.wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public void setRecoverySecretTypes(int[] iArr) throws InternalRecoveryServiceException {
        try {
            this.mBinder.setRecoverySecretTypes(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public int[] getRecoverySecretTypes() throws InternalRecoveryServiceException {
        try {
            return this.mBinder.getRecoverySecretTypes();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw this.wrapUnexpectedServiceSpecificException(e2);
        }
    }

    @Deprecated
    public Key generateKey(String str) throws InternalRecoveryServiceException, LockScreenRequiredException {
        try {
            String generateKey = this.mBinder.generateKey(str);
            if (generateKey == null) {
                throw new InternalRecoveryServiceException("null grant alias");
            }
            return getKeyFromGrant(generateKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 23) {
                throw new LockScreenRequiredException(e2.getMessage());
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        } catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException e3) {
            throw new InternalRecoveryServiceException("Failed to get key from keystore", e3);
        }
    }

    public Key generateKey(String str, byte[] bArr) throws InternalRecoveryServiceException, LockScreenRequiredException {
        try {
            String generateKeyWithMetadata = this.mBinder.generateKeyWithMetadata(str, bArr);
            if (generateKeyWithMetadata == null) {
                throw new InternalRecoveryServiceException("null grant alias");
            }
            return getKeyFromGrant(generateKeyWithMetadata);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 23) {
                throw new LockScreenRequiredException(e2.getMessage());
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        } catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException e3) {
            throw new InternalRecoveryServiceException("Failed to get key from keystore", e3);
        }
    }

    @Deprecated
    public Key importKey(String str, byte[] bArr) throws InternalRecoveryServiceException, LockScreenRequiredException {
        try {
            String importKey = this.mBinder.importKey(str, bArr);
            if (importKey == null) {
                throw new InternalRecoveryServiceException("Null grant alias");
            }
            return getKeyFromGrant(importKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 23) {
                throw new LockScreenRequiredException(e2.getMessage());
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        } catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException e3) {
            throw new InternalRecoveryServiceException("Failed to get key from keystore", e3);
        }
    }

    public Key importKey(String str, byte[] bArr, byte[] bArr2) throws InternalRecoveryServiceException, LockScreenRequiredException {
        try {
            String importKeyWithMetadata = this.mBinder.importKeyWithMetadata(str, bArr, bArr2);
            if (importKeyWithMetadata == null) {
                throw new InternalRecoveryServiceException("Null grant alias");
            }
            return getKeyFromGrant(importKeyWithMetadata);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 23) {
                throw new LockScreenRequiredException(e2.getMessage());
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        } catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException e3) {
            throw new InternalRecoveryServiceException("Failed to get key from keystore", e3);
        }
    }

    public Key getKey(String str) throws InternalRecoveryServiceException, UnrecoverableKeyException {
        try {
            String key = this.mBinder.getKey(str);
            if (key != null && !"".equals(key)) {
                return getKeyFromGrant(key);
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 30) {
                throw new UnrecoverableKeyException(e2.getMessage());
            }
            throw this.wrapUnexpectedServiceSpecificException(e2);
        } catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException e3) {
            throw new UnrecoverableKeyException(e3.getMessage());
        }
    }

    Key getKeyFromGrant(String str) throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        return AndroidKeyStoreProvider.loadAndroidKeyStoreSecretKeyFromKeystore(KeyStore2.getInstance(), getGrantDescriptor(str));
    }

    private static KeyDescriptor getGrantDescriptor(String str) {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = 1;
        keyDescriptor.blob = null;
        keyDescriptor.alias = null;
        try {
            keyDescriptor.nspace = Long.parseUnsignedLong(str.substring(16), 16);
            return keyDescriptor;
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public void removeKey(String str) throws InternalRecoveryServiceException {
        try {
            this.mBinder.removeKey(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public RecoverySession createRecoverySession() {
        return RecoverySession.newInstance(this);
    }

    public Map<String, X509Certificate> getRootCertificates() {
        return TrustedRootCertificates.getRootCertificates();
    }

    InternalRecoveryServiceException wrapUnexpectedServiceSpecificException(ServiceSpecificException serviceSpecificException) {
        if (serviceSpecificException.errorCode == 22) {
            return new InternalRecoveryServiceException(serviceSpecificException.getMessage(), serviceSpecificException);
        }
        return new InternalRecoveryServiceException("Unexpected error code for method: " + serviceSpecificException.errorCode, serviceSpecificException);
    }
}
