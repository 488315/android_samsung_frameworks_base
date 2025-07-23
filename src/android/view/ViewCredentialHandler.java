package android.view;

import android.credentials.GetCredentialException;
import android.credentials.GetCredentialRequest;
import android.credentials.GetCredentialResponse;
import android.os.OutcomeReceiver;

/* loaded from: classes4.dex */
public class ViewCredentialHandler {
    private OutcomeReceiver<GetCredentialResponse, GetCredentialException> mCallback;
    private GetCredentialRequest mRequest;

    ViewCredentialHandler(GetCredentialRequest getCredentialRequest, OutcomeReceiver<GetCredentialResponse, GetCredentialException> outcomeReceiver) {
        this.mRequest = getCredentialRequest;
        this.mCallback = outcomeReceiver;
    }

    public GetCredentialRequest getRequest() {
        return this.mRequest;
    }

    public OutcomeReceiver<GetCredentialResponse, GetCredentialException> getCallback() {
        return this.mCallback;
    }
}
