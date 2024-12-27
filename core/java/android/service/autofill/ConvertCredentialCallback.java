package android.service.autofill;

import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class ConvertCredentialCallback {
    private static final String TAG = "ConvertCredentialCallback";
    private final IConvertCredentialCallback mCallback;

    public ConvertCredentialCallback(IConvertCredentialCallback callback) {
        this.mCallback = callback;
    }

    public void onSuccess(ConvertCredentialResponse convertCredentialResponse) {
        try {
            this.mCallback.onSuccess(convertCredentialResponse);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void onFailure(CharSequence message) {
        try {
            this.mCallback.onFailure(message);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }
}
