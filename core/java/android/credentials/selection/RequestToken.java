package android.credentials.selection;

import android.annotation.SystemApi;
import android.os.IBinder;

@SystemApi
public final class RequestToken {
    private final IBinder mToken;

    public IBinder getToken() {
        return this.mToken;
    }

    public RequestToken(IBinder token) {
        this.mToken = token;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RequestToken)) {
            return false;
        }
        RequestToken other = (RequestToken) obj;
        return this.mToken.equals(other.mToken);
    }

    public int hashCode() {
        return this.mToken.hashCode();
    }
}
