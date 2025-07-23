package android.credentials.selection;

import android.annotation.SystemApi;
import android.os.IBinder;

@SystemApi
/* loaded from: classes.dex */
public final class RequestToken {
    private final IBinder mToken;

    public IBinder getToken() {
        return this.mToken;
    }

    public RequestToken(IBinder iBinder) {
        this.mToken = iBinder;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RequestToken)) {
            return false;
        }
        return this.mToken.equals(((RequestToken) obj).mToken);
    }

    public int hashCode() {
        return this.mToken.hashCode();
    }
}
