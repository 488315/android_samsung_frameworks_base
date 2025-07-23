package android.security;

import android.hardware.security.keymint.KeyParameter;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.system.keystore2.IKeystoreOperation;
import android.util.Log;

/* loaded from: classes3.dex */
public class KeyStoreOperation {
    static final String TAG = "KeyStoreOperation";
    private final Long mChallenge;
    private final IKeystoreOperation mOperation;
    private final KeyParameter[] mParameters;

    public KeyStoreOperation(IKeystoreOperation iKeystoreOperation, Long l, KeyParameter[] keyParameterArr) {
        Binder.allowBlocking(iKeystoreOperation.asBinder());
        this.mOperation = iKeystoreOperation;
        this.mChallenge = l;
        this.mParameters = keyParameterArr;
    }

    public Long getChallenge() {
        return this.mChallenge;
    }

    public KeyParameter[] getParameters() {
        return this.mParameters;
    }

    private <R> R handleExceptions(CheckedRemoteRequest<R> checkedRemoteRequest) throws KeyStoreException {
        try {
            return checkedRemoteRequest.execute();
        } catch (RemoteException e) {
            Log.e(TAG, "Remote exception while advancing a KeyStoreOperation.", e);
            throw new KeyStoreException(-28, "", e.getMessage());
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 19) {
                throw new IllegalThreadStateException("Cannot update the same operation concurrently.");
            }
            throw KeyStore2.getKeyStoreException(e2.errorCode, e2.getMessage());
        }
    }

    public void updateAad(final byte[] bArr) throws KeyStoreException {
        StrictMode.noteSlowCall("updateAad");
        handleExceptions(new CheckedRemoteRequest() { // from class: android.security.KeyStoreOperation$$ExternalSyntheticLambda0
            @Override // android.security.CheckedRemoteRequest
            public final Object execute() {
                Integer lambda$updateAad$0;
                lambda$updateAad$0 = KeyStoreOperation.this.lambda$updateAad$0(bArr);
                return lambda$updateAad$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$updateAad$0(byte[] bArr) throws RemoteException {
        this.mOperation.updateAad(bArr);
        return 0;
    }

    public byte[] update(final byte[] bArr) throws KeyStoreException {
        StrictMode.noteSlowCall("update");
        return (byte[]) handleExceptions(new CheckedRemoteRequest() { // from class: android.security.KeyStoreOperation$$ExternalSyntheticLambda2
            @Override // android.security.CheckedRemoteRequest
            public final Object execute() {
                byte[] lambda$update$1;
                lambda$update$1 = KeyStoreOperation.this.lambda$update$1(bArr);
                return lambda$update$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ byte[] lambda$update$1(byte[] bArr) throws RemoteException {
        return this.mOperation.update(bArr);
    }

    public byte[] finish(final byte[] bArr, final byte[] bArr2) throws KeyStoreException {
        StrictMode.noteSlowCall("finish");
        return (byte[]) handleExceptions(new CheckedRemoteRequest() { // from class: android.security.KeyStoreOperation$$ExternalSyntheticLambda1
            @Override // android.security.CheckedRemoteRequest
            public final Object execute() {
                byte[] lambda$finish$2;
                lambda$finish$2 = KeyStoreOperation.this.lambda$finish$2(bArr, bArr2);
                return lambda$finish$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ byte[] lambda$finish$2(byte[] bArr, byte[] bArr2) throws RemoteException {
        return this.mOperation.finish(bArr, bArr2);
    }

    public void abort() throws KeyStoreException {
        StrictMode.noteSlowCall("abort");
        handleExceptions(new CheckedRemoteRequest() { // from class: android.security.KeyStoreOperation$$ExternalSyntheticLambda3
            @Override // android.security.CheckedRemoteRequest
            public final Object execute() {
                Integer lambda$abort$3;
                lambda$abort$3 = KeyStoreOperation.this.lambda$abort$3();
                return lambda$abort$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$abort$3() throws RemoteException {
        this.mOperation.abort();
        return 0;
    }
}
