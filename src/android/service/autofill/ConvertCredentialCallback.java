package android.service.autofill;

import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class ConvertCredentialCallback {
    private static final String TAG = "ConvertCredentialCallback";
    private final IConvertCredentialCallback mCallback;

    public ConvertCredentialCallback(IConvertCredentialCallback iConvertCredentialCallback) {
        this.mCallback = iConvertCredentialCallback;
    }

    public void onSuccess(ConvertCredentialResponse convertCredentialResponse) {
        try {
            this.mCallback.onSuccess(convertCredentialResponse);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void onFailure(CharSequence charSequence) {
        try {
            this.mCallback.onFailure(charSequence);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }
}
