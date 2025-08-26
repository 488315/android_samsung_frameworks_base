package android.security;

import android.app.compat.CompatChanges;
import android.hardware.security.keymint.KeyParameter;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.security.keystore.BackendBusyException;
import android.security.keystore.KeyStoreConnectException;
import android.system.keystore2.AuthenticatorSpec;
import android.system.keystore2.CreateOperationResponse;
import android.system.keystore2.IKeystoreSecurityLevel;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyMetadata;
import android.util.Log;
import java.util.Calendar;
import java.util.Collection;

/* loaded from: classes3.dex */
public class KeyStoreSecurityLevel {
    private static final String TAG = "KeyStoreSecurityLevel";
    private final IKeystoreSecurityLevel mSecurityLevel;

    public KeyStoreSecurityLevel(IKeystoreSecurityLevel iKeystoreSecurityLevel) {
        Binder.allowBlocking(iKeystoreSecurityLevel.asBinder());
        this.mSecurityLevel = iKeystoreSecurityLevel;
    }

    private <R> R handleExceptions(CheckedRemoteRequest<R> checkedRemoteRequest) throws KeyStoreException {
        try {
            return checkedRemoteRequest.execute();
        } catch (RemoteException e) {
            Log.e(TAG, "Could not connect to Keystore.", e);
            throw new KeyStoreException(4, "", e.getMessage());
        } catch (ServiceSpecificException e2) {
            throw KeyStore2.getKeyStoreException(e2.errorCode, e2.getMessage());
        }
    }

    public KeyStoreOperation createOperation(KeyDescriptor keyDescriptor, Collection<KeyParameter> collection) throws InterruptedException, KeyStoreException {
        StrictMode.noteDiskWrite();
        while (true) {
            try {
                CreateOperationResponse createOperationResponseCreateOperation = this.mSecurityLevel.createOperation(keyDescriptor, (KeyParameter[]) collection.toArray(new KeyParameter[collection.size()]), false);
                return new KeyStoreOperation(createOperationResponseCreateOperation.iOperation, createOperationResponseCreateOperation.operationChallenge != null ? Long.valueOf(createOperationResponseCreateOperation.operationChallenge.challenge) : null, createOperationResponseCreateOperation.parameters != null ? createOperationResponseCreateOperation.parameters.keyParameter : null);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to keystore", e);
                throw new KeyStoreConnectException();
            } catch (ServiceSpecificException e2) {
                if (e2.errorCode == 18) {
                    long jRandom = (long) ((Math.random() * 80.0d) + 20.0d);
                    if (CompatChanges.isChangeEnabled(169897160L)) {
                        throw new BackendBusyException(jRandom);
                    }
                    interruptedPreservingSleep(jRandom);
                } else {
                    throw KeyStore2.getKeyStoreException(e2.errorCode, e2.getMessage());
                }
            }
        }
    }

    public KeyMetadata generateKey(final KeyDescriptor keyDescriptor, final KeyDescriptor keyDescriptor2, final Collection<KeyParameter> collection, final int i, final byte[] bArr) throws KeyStoreException {
        StrictMode.noteDiskWrite();
        return (KeyMetadata) handleExceptions(new CheckedRemoteRequest() { // from class: android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda2
            @Override // android.security.CheckedRemoteRequest
            public final Object execute() {
                return this.f$0.lambda$generateKey$0(keyDescriptor, keyDescriptor2, collection, i, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ KeyMetadata lambda$generateKey$0(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, Collection collection, int i, byte[] bArr) throws RemoteException {
        return this.mSecurityLevel.generateKey(keyDescriptor, keyDescriptor2, (KeyParameter[]) collection.toArray(new KeyParameter[collection.size()]), i, bArr);
    }

    public KeyMetadata importKey(final KeyDescriptor keyDescriptor, final KeyDescriptor keyDescriptor2, final Collection<KeyParameter> collection, final int i, final byte[] bArr) throws KeyStoreException {
        StrictMode.noteDiskWrite();
        return (KeyMetadata) handleExceptions(new CheckedRemoteRequest() { // from class: android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda0
            @Override // android.security.CheckedRemoteRequest
            public final Object execute() {
                return this.f$0.lambda$importKey$1(keyDescriptor, keyDescriptor2, collection, i, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ KeyMetadata lambda$importKey$1(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, Collection collection, int i, byte[] bArr) throws RemoteException {
        return this.mSecurityLevel.importKey(keyDescriptor, keyDescriptor2, (KeyParameter[]) collection.toArray(new KeyParameter[collection.size()]), i, bArr);
    }

    public KeyMetadata importWrappedKey(KeyDescriptor keyDescriptor, final KeyDescriptor keyDescriptor2, byte[] bArr, final byte[] bArr2, final Collection<KeyParameter> collection, final AuthenticatorSpec[] authenticatorSpecArr) throws KeyStoreException {
        StrictMode.noteDiskWrite();
        final KeyDescriptor keyDescriptor3 = new KeyDescriptor();
        keyDescriptor3.alias = keyDescriptor.alias;
        keyDescriptor3.nspace = keyDescriptor.nspace;
        keyDescriptor3.blob = bArr;
        keyDescriptor3.domain = keyDescriptor.domain;
        return (KeyMetadata) handleExceptions(new CheckedRemoteRequest() { // from class: android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda1
            @Override // android.security.CheckedRemoteRequest
            public final Object execute() {
                return this.f$0.lambda$importWrappedKey$2(keyDescriptor3, keyDescriptor2, bArr2, collection, authenticatorSpecArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ KeyMetadata lambda$importWrappedKey$2(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, byte[] bArr, Collection collection, AuthenticatorSpec[] authenticatorSpecArr) throws RemoteException {
        return this.mSecurityLevel.importWrappedKey(keyDescriptor, keyDescriptor2, bArr, (KeyParameter[]) collection.toArray(new KeyParameter[collection.size()]), authenticatorSpecArr);
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
}
