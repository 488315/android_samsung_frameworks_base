package android.security;

import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.os.UserHandle;
import android.security.keymaster.KeymasterDefs;
import android.system.keystore2.IKeystoreService;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyEntryResponse;
import android.util.Log;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class KeyStore2 {
    private static final String KEYSTORE2_SERVICE_NAME = "android.system.keystore2.IKeystoreService/default";
    private static final String KEYSTORE_ENGINE_GRANT_ALIAS_PREFIX = "ks2_keystore-engine_grant_id:0x";
    static final long KEYSTORE_OPERATION_CREATION_MAY_FAIL = 169897160;
    private static final int RECOVERY_GRACE_PERIOD_MS = 50;
    private static final String TAG = "KeyStore";
    private IKeystoreService mBinder = null;

    @FunctionalInterface
    interface CheckedRemoteRequest<R> {
        R execute(IKeystoreService iKeystoreService) throws RemoteException;
    }

    <R> R handleRemoteExceptionWithRetry(CheckedRemoteRequest<R> checkedRemoteRequest) throws InterruptedException, KeyStoreException {
        IKeystoreService service = getService(false);
        boolean z = true;
        while (true) {
            try {
                return checkedRemoteRequest.execute(service);
            } catch (RemoteException e) {
                if (z) {
                    Log.w(TAG, "Looks like we may have lost connection to the Keystore daemon.");
                    Log.w(TAG, "Retrying after giving Keystore 50ms to recover.");
                    interruptedPreservingSleep(50L);
                    service = this.getService(true);
                    z = false;
                } else {
                    Log.e(TAG, "Cannot connect to Keystore daemon.", e);
                    throw new KeyStoreException(4, "", e.getMessage());
                }
            } catch (ServiceSpecificException e2) {
                throw getKeyStoreException(e2.errorCode, e2.getMessage());
            }
        }
    }

    private KeyStore2() {
    }

    public static KeyStore2 getInstance() {
        return new KeyStore2();
    }

    private synchronized IKeystoreService getService(boolean z) {
        if (this.mBinder == null || z) {
            this.mBinder = IKeystoreService.Stub.asInterface(ServiceManager.getService(KEYSTORE2_SERVICE_NAME));
        }
        IKeystoreService iKeystoreService = this.mBinder;
        if (iKeystoreService == null) {
            throw new IllegalStateException("Could not connect to Keystore service. Keystore may have crashed or not been initialized");
        }
        Binder.allowBlocking(iKeystoreService.asBinder());
        return this.mBinder;
    }

    void delete(final KeyDescriptor keyDescriptor) throws InterruptedException, KeyStoreException {
        StrictMode.noteDiskWrite();
        handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda6
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return KeyStore2.lambda$delete$0(keyDescriptor, iKeystoreService);
            }
        });
    }

    static /* synthetic */ Integer lambda$delete$0(KeyDescriptor keyDescriptor, IKeystoreService iKeystoreService) throws RemoteException {
        iKeystoreService.deleteKey(keyDescriptor);
        return 0;
    }

    public KeyDescriptor[] list(final int i, final long j) throws KeyStoreException {
        StrictMode.noteDiskRead();
        return (KeyDescriptor[]) handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda3
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return iKeystoreService.listEntries(i, j);
            }
        });
    }

    public KeyDescriptor[] listBatch(final int i, final long j, final String str) throws KeyStoreException {
        StrictMode.noteDiskRead();
        return (KeyDescriptor[]) handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda5
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return iKeystoreService.listEntriesBatched(i, j, str);
            }
        });
    }

    public static String makeKeystoreEngineGrantString(long j) {
        return String.format("%s%016X", KEYSTORE_ENGINE_GRANT_ALIAS_PREFIX, Long.valueOf(j));
    }

    public static KeyDescriptor keystoreEngineGrantString2KeyDescriptor(String str) {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = 1;
        keyDescriptor.nspace = Long.parseUnsignedLong(str.substring(31), 16);
        keyDescriptor.alias = null;
        keyDescriptor.blob = null;
        return keyDescriptor;
    }

    public KeyDescriptor grant(final KeyDescriptor keyDescriptor, final int i, final int i2) throws KeyStoreException {
        StrictMode.noteDiskWrite();
        return (KeyDescriptor) handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda9
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return iKeystoreService.grant(keyDescriptor, i, i2);
            }
        });
    }

    public void ungrant(final KeyDescriptor keyDescriptor, final int i) throws InterruptedException, KeyStoreException {
        StrictMode.noteDiskWrite();
        handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda2
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return KeyStore2.lambda$ungrant$4(keyDescriptor, i, iKeystoreService);
            }
        });
    }

    static /* synthetic */ Integer lambda$ungrant$4(KeyDescriptor keyDescriptor, int i, IKeystoreService iKeystoreService) throws RemoteException {
        iKeystoreService.ungrant(keyDescriptor, i);
        return 0;
    }

    public KeyEntryResponse getKeyEntry(final KeyDescriptor keyDescriptor) throws KeyStoreException {
        StrictMode.noteDiskRead();
        return (KeyEntryResponse) handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda8
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return iKeystoreService.getKeyEntry(keyDescriptor);
            }
        });
    }

    public KeyStoreSecurityLevel getSecurityLevel(final int i) throws KeyStoreException {
        return (KeyStoreSecurityLevel) handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda1
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return KeyStore2.lambda$getSecurityLevel$6(i, iKeystoreService);
            }
        });
    }

    static /* synthetic */ KeyStoreSecurityLevel lambda$getSecurityLevel$6(int i, IKeystoreService iKeystoreService) throws RemoteException {
        return new KeyStoreSecurityLevel(iKeystoreService.getSecurityLevel(i));
    }

    public void updateSubcomponents(final KeyDescriptor keyDescriptor, final byte[] bArr, final byte[] bArr2) throws InterruptedException, KeyStoreException {
        KnoxKeyStoreHelper.checkCertificateTrustful(bArr, bArr2);
        handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda7
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return KeyStore2.lambda$updateSubcomponents$7(keyDescriptor, bArr, bArr2, iKeystoreService);
            }
        });
    }

    static /* synthetic */ Integer lambda$updateSubcomponents$7(KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2, IKeystoreService iKeystoreService) throws RemoteException {
        iKeystoreService.updateSubcomponent(keyDescriptor, bArr, bArr2);
        return 0;
    }

    private static /* synthetic */ Integer lambda$updateSubcomponents$8(KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2, IKeystoreService iKeystoreService) throws RemoteException {
        iKeystoreService.updateSubcomponent(keyDescriptor, bArr, bArr2);
        return 0;
    }

    public void deleteKey(final KeyDescriptor keyDescriptor) throws InterruptedException, KeyStoreException {
        List<X509Certificate> certificates = getCertificates(keyDescriptor);
        handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda4
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return KeyStore2.lambda$deleteKey$9(keyDescriptor, iKeystoreService);
            }
        });
        KnoxKeyStoreHelper.notifyCertificateRemovedAsUser(certificates);
    }

    static /* synthetic */ Integer lambda$deleteKey$9(KeyDescriptor keyDescriptor, IKeystoreService iKeystoreService) throws RemoteException {
        iKeystoreService.deleteKey(keyDescriptor);
        return 0;
    }

    private static /* synthetic */ Integer lambda$deleteKey$10(KeyDescriptor keyDescriptor, IKeystoreService iKeystoreService) throws RemoteException {
        iKeystoreService.deleteKey(keyDescriptor);
        return 0;
    }

    public int getNumberOfEntries(final int i, final long j) throws KeyStoreException {
        StrictMode.noteDiskRead();
        return ((Integer) handleRemoteExceptionWithRetry(new CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda0
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                return Integer.valueOf(iKeystoreService.getNumberOfEntries(i, j));
            }
        })).intValue();
    }

    protected static void interruptedPreservingSleep(long j) throws InterruptedException {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis() + j;
        boolean z = false;
        while (true) {
            try {
                Thread.sleep(timeInMillis - calendar.getTimeInMillis());
                break;
            } catch (IllegalArgumentException unused) {
            } catch (InterruptedException unused2) {
                z = true;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    public byte[] getSupplementaryAttestationInfo(int i) throws KeyStoreException {
        return KeyStore2HalVersion.getSupplementaryAttestationInfoHelper(i, this);
    }

    static KeyStoreException getKeyStoreException(int i, String str) {
        if (i <= 0) {
            if (i == -16) {
                return new KeyStoreException(i, "Invalid user authentication validity duration", str);
            }
            return new KeyStoreException(i, KeymasterDefs.getErrorMessage(i), str);
        }
        if (i == 2) {
            return new KeyStoreException(i, "User authentication required", str);
        }
        if (i == 3) {
            return new KeyStoreException(i, "Keystore not initialized", str);
        }
        if (i == 4) {
            return new KeyStoreException(i, "System error", str);
        }
        if (i == 6) {
            return new KeyStoreException(i, "Permission denied", str);
        }
        if (i == 7) {
            return new KeyStoreException(i, "Key not found", str);
        }
        if (i == 8) {
            return new KeyStoreException(i, "Key blob corrupted", str);
        }
        if (i == 17) {
            return new KeyStoreException(i, "Key permanently invalidated", str);
        }
        if (i == 22) {
            return new KeyStoreException(i, str, 1);
        }
        return new KeyStoreException(i, String.valueOf(i), str);
    }

    private List<X509Certificate> getCertificates(KeyDescriptor keyDescriptor) {
        if (keyDescriptor.alias != null && UserHandle.getAppId(Binder.getCallingUid()) == 1000) {
            try {
                KeyEntryResponse keyEntry = getKeyEntry(keyDescriptor);
                if (keyEntry == null || keyEntry.metadata == null) {
                    Log.w(TAG, "[AuditLog] No certificate : " + keyDescriptor.alias);
                } else {
                    if (keyEntry.metadata.certificate != null) {
                        return KnoxKeyStoreHelper.toCertificates(keyEntry.metadata.certificate);
                    }
                    if (keyEntry.metadata.certificateChain != null) {
                        return KnoxKeyStoreHelper.toCertificates(keyEntry.metadata.certificateChain);
                    }
                }
            } catch (KeyStoreException e) {
                if (e.getErrorCode() != 7) {
                    Log.w(TAG, "[AuditLog] Unable to get certificate : " + e);
                }
            }
        }
        return Collections.EMPTY_LIST;
    }
}
