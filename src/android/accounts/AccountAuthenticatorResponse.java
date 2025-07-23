package android.accounts;

import android.accounts.IAccountAuthenticatorResponse;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes.dex */
public class AccountAuthenticatorResponse implements Parcelable {
    public static final Parcelable.Creator<AccountAuthenticatorResponse> CREATOR = new Parcelable.Creator<AccountAuthenticatorResponse>() { // from class: android.accounts.AccountAuthenticatorResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountAuthenticatorResponse createFromParcel(Parcel parcel) {
            return new AccountAuthenticatorResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountAuthenticatorResponse[] newArray(int i) {
            return new AccountAuthenticatorResponse[i];
        }
    };
    private static final String TAG = "AccountAuthenticator";
    private IAccountAuthenticatorResponse mAccountAuthenticatorResponse;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AccountAuthenticatorResponse(IAccountAuthenticatorResponse iAccountAuthenticatorResponse) {
        this.mAccountAuthenticatorResponse = iAccountAuthenticatorResponse;
    }

    public AccountAuthenticatorResponse(Parcel parcel) {
        this.mAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    public void onResult(Bundle bundle) {
        if (Log.isLoggable(TAG, 2)) {
            bundle.keySet();
            Log.v(TAG, "AccountAuthenticatorResponse.onResult: " + AccountManager.sanitizeResult(bundle));
        }
        try {
            this.mAccountAuthenticatorResponse.onResult(bundle);
        } catch (RemoteException unused) {
        }
    }

    public void onRequestContinued() {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "AccountAuthenticatorResponse.onRequestContinued");
        }
        try {
            this.mAccountAuthenticatorResponse.onRequestContinued();
        } catch (RemoteException unused) {
        }
    }

    public void onError(int i, String str) {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "AccountAuthenticatorResponse.onError: " + i + ", " + str);
        }
        try {
            this.mAccountAuthenticatorResponse.onError(i, str);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mAccountAuthenticatorResponse.asBinder());
    }
}
