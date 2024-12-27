package android.view;

import android.credentials.GetCredentialException;
import android.credentials.GetCredentialRequest;
import android.credentials.GetCredentialResponse;
import android.os.OutcomeReceiver;

/* loaded from: classes4.dex */
public class ViewCredentialHandler {
    private OutcomeReceiver<GetCredentialResponse, GetCredentialException> mCallback;
    private GetCredentialRequest mRequest;

    ViewCredentialHandler(GetCredentialRequest request, OutcomeReceiver<GetCredentialResponse, GetCredentialException> callback) {
        this.mRequest = request;
        this.mCallback = callback;
    }

    public GetCredentialRequest getRequest() {
        return this.mRequest;
    }

    public OutcomeReceiver<GetCredentialResponse, GetCredentialException> getCallback() {
        return this.mCallback;
    }
}
