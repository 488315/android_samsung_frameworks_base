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
    private static final String KEYSTORE2_SERVICE_NAME =
            "android.system.keystore2.IKeystoreService/default";
    private static final String KEYSTORE_ENGINE_GRANT_ALIAS_PREFIX =
            "ks2_keystore-engine_grant_id:0x";
    static final long KEYSTORE_OPERATION_CREATION_MAY_FAIL = 169897160;
    private static final int RECOVERY_GRACE_PERIOD_MS = 50;
    private static final String TAG = "KeyStore";
    private IKeystoreService mBinder = null;

    @FunctionalInterface
    interface CheckedRemoteRequest<R> {
        R execute(IKeystoreService iKeystoreService) throws RemoteException;
    }

    private <R> R handleRemoteExceptionWithRetry(CheckedRemoteRequest<R> request)
            throws KeyStoreException {
        IKeystoreService service = getService(false);
        boolean firstTry = true;
        while (true) {
            try {
                return request.execute(service);
            } catch (RemoteException e) {
                if (firstTry) {
                    Log.w(TAG, "Looks like we may have lost connection to the Keystore daemon.");
                    Log.w(TAG, "Retrying after giving Keystore 50ms to recover.");
                    interruptedPreservingSleep(50L);
                    service = getService(true);
                    firstTry = false;
                } else {
                    Log.e(TAG, "Cannot connect to Keystore daemon.", e);
                    throw new KeyStoreException(4, "", e.getMessage());
                }
            } catch (ServiceSpecificException e2) {
                throw getKeyStoreException(e2.errorCode, e2.getMessage());
            }
        }
    }

    private KeyStore2() {}

    public static KeyStore2 getInstance() {
        return new KeyStore2();
    }

    private synchronized IKeystoreService getService(boolean retryLookup) {
        if (this.mBinder == null || retryLookup) {
            this.mBinder =
                    IKeystoreService.Stub.asInterface(
                            ServiceManager.getService(KEYSTORE2_SERVICE_NAME));
        }
        if (this.mBinder == null) {
            throw new IllegalStateException(
                    "Could not connect to Keystore service. Keystore may have crashed or not been"
                        + " initialized");
        }
        Binder.allowBlocking(this.mBinder.asBinder());
        return this.mBinder;
    }

    void delete(final KeyDescriptor descriptor) throws KeyStoreException {
        StrictMode.noteDiskWrite();
        handleRemoteExceptionWithRetry(
                new CheckedRemoteRequest() { // from class:
                                             // android.security.KeyStore2$$ExternalSyntheticLambda6
                    @Override // android.security.KeyStore2.CheckedRemoteRequest
                    public final Object execute(IKeystoreService iKeystoreService) {
                        return KeyStore2.lambda$delete$0(KeyDescriptor.this, iKeystoreService);
                    }
                });
    }

    static /* synthetic */ Integer lambda$delete$0(
            KeyDescriptor descriptor, IKeystoreService service) throws RemoteException {
        service.deleteKey(descriptor);
        return 0;
    }

    public KeyDescriptor[] list(final int domain, final long namespace) throws KeyStoreException {
        StrictMode.noteDiskRead();
        return (KeyDescriptor[])
                handleRemoteExceptionWithRetry(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStore2$$ExternalSyntheticLambda3
                            @Override // android.security.KeyStore2.CheckedRemoteRequest
                            public final Object execute(IKeystoreService iKeystoreService) {
                                KeyDescriptor[] listEntries;
                                listEntries = iKeystoreService.listEntries(domain, namespace);
                                return listEntries;
                            }
                        });
    }

    public KeyDescriptor[] listBatch(
            final int domain, final long namespace, final String startPastAlias)
            throws KeyStoreException {
        StrictMode.noteDiskRead();
        return (KeyDescriptor[])
                handleRemoteExceptionWithRetry(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStore2$$ExternalSyntheticLambda5
                            @Override // android.security.KeyStore2.CheckedRemoteRequest
                            public final Object execute(IKeystoreService iKeystoreService) {
                                KeyDescriptor[] listEntriesBatched;
                                listEntriesBatched =
                                        iKeystoreService.listEntriesBatched(
                                                domain, namespace, startPastAlias);
                                return listEntriesBatched;
                            }
                        });
    }

    public static String makeKeystoreEngineGrantString(long grantId) {
        return String.format("%s%016X", KEYSTORE_ENGINE_GRANT_ALIAS_PREFIX, Long.valueOf(grantId));
    }

    public static KeyDescriptor keystoreEngineGrantString2KeyDescriptor(String grantString) {
        KeyDescriptor key = new KeyDescriptor();
        key.domain = 1;
        key.nspace =
                Long.parseUnsignedLong(
                        grantString.substring(KEYSTORE_ENGINE_GRANT_ALIAS_PREFIX.length()), 16);
        key.alias = null;
        key.blob = null;
        return key;
    }

    public KeyDescriptor grant(
            final KeyDescriptor descriptor, final int granteeUid, final int accessVector)
            throws KeyStoreException {
        StrictMode.noteDiskWrite();
        return (KeyDescriptor)
                handleRemoteExceptionWithRetry(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStore2$$ExternalSyntheticLambda9
                            @Override // android.security.KeyStore2.CheckedRemoteRequest
                            public final Object execute(IKeystoreService iKeystoreService) {
                                KeyDescriptor grant;
                                grant =
                                        iKeystoreService.grant(
                                                KeyDescriptor.this, granteeUid, accessVector);
                                return grant;
                            }
                        });
    }

    public void ungrant(final KeyDescriptor descriptor, final int granteeUid)
            throws KeyStoreException {
        StrictMode.noteDiskWrite();
        handleRemoteExceptionWithRetry(
                new CheckedRemoteRequest() { // from class:
                                             // android.security.KeyStore2$$ExternalSyntheticLambda2
                    @Override // android.security.KeyStore2.CheckedRemoteRequest
                    public final Object execute(IKeystoreService iKeystoreService) {
                        return KeyStore2.lambda$ungrant$4(
                                KeyDescriptor.this, granteeUid, iKeystoreService);
                    }
                });
    }

    static /* synthetic */ Integer lambda$ungrant$4(
            KeyDescriptor descriptor, int granteeUid, IKeystoreService service)
            throws RemoteException {
        service.ungrant(descriptor, granteeUid);
        return 0;
    }

    public KeyEntryResponse getKeyEntry(final KeyDescriptor descriptor) throws KeyStoreException {
        StrictMode.noteDiskRead();
        return (KeyEntryResponse)
                handleRemoteExceptionWithRetry(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStore2$$ExternalSyntheticLambda8
                            @Override // android.security.KeyStore2.CheckedRemoteRequest
                            public final Object execute(IKeystoreService iKeystoreService) {
                                KeyEntryResponse keyEntry;
                                keyEntry = iKeystoreService.getKeyEntry(KeyDescriptor.this);
                                return keyEntry;
                            }
                        });
    }

    public KeyStoreSecurityLevel getSecurityLevel(final int securityLevel)
            throws KeyStoreException {
        return (KeyStoreSecurityLevel)
                handleRemoteExceptionWithRetry(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStore2$$ExternalSyntheticLambda1
                            @Override // android.security.KeyStore2.CheckedRemoteRequest
                            public final Object execute(IKeystoreService iKeystoreService) {
                                return KeyStore2.lambda$getSecurityLevel$6(
                                        securityLevel, iKeystoreService);
                            }
                        });
    }

    static /* synthetic */ KeyStoreSecurityLevel lambda$getSecurityLevel$6(
            int securityLevel, IKeystoreService service) throws RemoteException {
        return new KeyStoreSecurityLevel(service.getSecurityLevel(securityLevel));
    }

    public void updateSubcomponents(
            final KeyDescriptor key, final byte[] publicCert, final byte[] publicCertChain)
            throws KeyStoreException {
        KeyStoreAuditLog.AuditLogParams params = KeyStoreAuditLog.AuditLogParams.init(key, 3, TAG);
        params.setUserCertAndChain(publicCert, publicCertChain);
        try {
            try {
                KeyStoreAuditLog.checkCertificateTrustful(params);
                handleRemoteExceptionWithRetry(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStore2$$ExternalSyntheticLambda7
                            @Override // android.security.KeyStore2.CheckedRemoteRequest
                            public final Object execute(IKeystoreService iKeystoreService) {
                                return KeyStore2.lambda$updateSubcomponents$7(
                                        KeyDescriptor.this,
                                        publicCert,
                                        publicCertChain,
                                        iKeystoreService);
                            }
                        });
            } catch (KeyStoreException e) {
                params.setErrorCode(e.getErrorCode());
                throw e;
            }
        } finally {
            if (KeyStoreAuditLog.isAuditLogEnabledAsUser()) {
                KeyStoreAuditLog.auditLogPrivilegedAsUser(params);
            }
        }
    }

    static /* synthetic */ Integer lambda$updateSubcomponents$7(
            KeyDescriptor key, byte[] publicCert, byte[] publicCertChain, IKeystoreService service)
            throws RemoteException {
        service.updateSubcomponent(key, publicCert, publicCertChain);
        return 0;
    }

    private static /* synthetic */ Integer lambda$updateSubcomponents$8(
            KeyDescriptor key, byte[] publicCert, byte[] publicCertChain, IKeystoreService service)
            throws RemoteException {
        service.updateSubcomponent(key, publicCert, publicCertChain);
        return 0;
    }

    public void deleteKey(final KeyDescriptor descriptor) throws KeyStoreException {
        KeyStoreAuditLog.AuditLogParams params =
                KeyStoreAuditLog.AuditLogParams.init(descriptor, 2, TAG);
        params.setX509Certificates(getCertificates(descriptor));
        try {
            try {
                handleRemoteExceptionWithRetry(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStore2$$ExternalSyntheticLambda4
                            @Override // android.security.KeyStore2.CheckedRemoteRequest
                            public final Object execute(IKeystoreService iKeystoreService) {
                                return KeyStore2.lambda$deleteKey$9(
                                        KeyDescriptor.this, iKeystoreService);
                            }
                        });
                KeyStoreAuditLog.notifyCertificateRemovedAsUser(params);
            } catch (KeyStoreException e) {
                params.setErrorCode(e.getErrorCode());
                throw e;
            }
        } finally {
            if (KeyStoreAuditLog.isAuditLogEnabledAsUser()) {
                KeyStoreAuditLog.auditLogPrivilegedAsUser(params);
            }
        }
    }

    static /* synthetic */ Integer lambda$deleteKey$9(
            KeyDescriptor descriptor, IKeystoreService service) throws RemoteException {
        service.deleteKey(descriptor);
        return 0;
    }

    private static /* synthetic */ Integer lambda$deleteKey$10(
            KeyDescriptor descriptor, IKeystoreService service) throws RemoteException {
        service.deleteKey(descriptor);
        return 0;
    }

    public int getNumberOfEntries(final int domain, final long namespace) throws KeyStoreException {
        StrictMode.noteDiskRead();
        return ((Integer)
                        handleRemoteExceptionWithRetry(
                                new CheckedRemoteRequest() { // from class:
                                                             // android.security.KeyStore2$$ExternalSyntheticLambda0
                                    @Override // android.security.KeyStore2.CheckedRemoteRequest
                                    public final Object execute(IKeystoreService iKeystoreService) {
                                        Integer valueOf;
                                        valueOf =
                                                Integer.valueOf(
                                                        iKeystoreService.getNumberOfEntries(
                                                                domain, namespace));
                                        return valueOf;
                                    }
                                }))
                .intValue();
    }

    protected static void interruptedPreservingSleep(long millis) {
        boolean wasInterrupted = false;
        Calendar calendar = Calendar.getInstance();
        long target = calendar.getTimeInMillis() + millis;
        while (true) {
            try {
                Thread.sleep(target - calendar.getTimeInMillis());
                break;
            } catch (IllegalArgumentException e) {
            } catch (InterruptedException e2) {
                wasInterrupted = true;
            }
        }
        if (wasInterrupted) {
            Thread.currentThread().interrupt();
        }
    }

    static KeyStoreException getKeyStoreException(int errorCode, String serviceErrorMessage) {
        if (errorCode > 0) {
            switch (errorCode) {
                case 2:
                    return new KeyStoreException(
                            errorCode, "User authentication required", serviceErrorMessage);
                case 3:
                    return new KeyStoreException(
                            errorCode, "Keystore not initialized", serviceErrorMessage);
                case 4:
                    return new KeyStoreException(errorCode, "System error", serviceErrorMessage);
                case 6:
                    return new KeyStoreException(
                            errorCode, "Permission denied", serviceErrorMessage);
                case 7:
                    return new KeyStoreException(errorCode, "Key not found", serviceErrorMessage);
                case 8:
                    return new KeyStoreException(
                            errorCode, "Key blob corrupted", serviceErrorMessage);
                case 17:
                    return new KeyStoreException(
                            errorCode, "Key permanently invalidated", serviceErrorMessage);
                case 22:
                    return new KeyStoreException(errorCode, serviceErrorMessage, 1);
                default:
                    return new KeyStoreException(
                            errorCode, String.valueOf(errorCode), serviceErrorMessage);
            }
        }
        switch (errorCode) {
            case -16:
                return new KeyStoreException(
                        errorCode,
                        "Invalid user authentication validity duration",
                        serviceErrorMessage);
            default:
                return new KeyStoreException(
                        errorCode, KeymasterDefs.getErrorMessage(errorCode), serviceErrorMessage);
        }
    }

    private List<X509Certificate> getCertificates(KeyDescriptor keyDescriptor) {
        if (keyDescriptor.alias != null && UserHandle.getAppId(Binder.getCallingUid()) == 1000) {
            try {
                KeyEntryResponse response = getKeyEntry(keyDescriptor);
                if (response == null || response.metadata == null) {
                    Log.w(TAG, "[AuditLog] No certificate : " + keyDescriptor.alias);
                } else {
                    if (response.metadata.certificate != null) {
                        return KeyStoreAuditLog.toCertificates(response.metadata.certificate);
                    }
                    if (response.metadata.certificateChain != null) {
                        return KeyStoreAuditLog.toCertificates(response.metadata.certificateChain);
                    }
                }
            } catch (KeyStoreException e) {
                if (e.getErrorCode() != 7) {
                    Log.w(TAG, "[AuditLog] Unable to get certificate : " + e);
                }
            }
        }
        return Collections.emptyList();
    }
}
