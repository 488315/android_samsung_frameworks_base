package android.app;

import android.net.Uri;
import android.os.IBinder;

import java.util.Objects;

/* loaded from: classes.dex */
public final class ComponentCaller {
    private final IBinder mActivityToken;
    private final IBinder mCallerToken;

    public ComponentCaller(IBinder activityToken, IBinder callerToken) {
        this.mActivityToken = activityToken;
        this.mCallerToken = callerToken;
    }

    public int getUid() {
        return ActivityClient.getInstance()
                .getActivityCallerUid(this.mActivityToken, this.mCallerToken);
    }

    public String getPackage() {
        return ActivityClient.getInstance()
                .getActivityCallerPackage(this.mActivityToken, this.mCallerToken);
    }

    public int checkContentUriPermission(Uri uri, int modeFlags) {
        return ActivityClient.getInstance()
                .checkActivityCallerContentUriPermission(
                        this.mActivityToken, this.mCallerToken, uri, modeFlags);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ComponentCaller)) {
            return false;
        }
        ComponentCaller other = (ComponentCaller) obj;
        return this.mActivityToken == other.mActivityToken
                && this.mCallerToken == other.mCallerToken;
    }

    public int hashCode() {
        int result = (17 * 31) + Objects.hashCode(this.mActivityToken);
        return (result * 31) + Objects.hashCode(this.mCallerToken);
    }
}
