package android.app;

import android.net.Uri;
import android.os.IBinder;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ComponentCaller {
    private final IBinder mActivityToken;
    private final IBinder mCallerToken;

    public ComponentCaller(IBinder iBinder, IBinder iBinder2) {
        this.mActivityToken = iBinder;
        this.mCallerToken = iBinder2;
    }

    public int getUid() {
        return ActivityClient.getInstance().getActivityCallerUid(this.mActivityToken, this.mCallerToken);
    }

    public String getPackage() {
        return ActivityClient.getInstance().getActivityCallerPackage(this.mActivityToken, this.mCallerToken);
    }

    public int checkContentUriPermission(Uri uri, int i) {
        return ActivityClient.getInstance().checkActivityCallerContentUriPermission(this.mActivityToken, this.mCallerToken, uri, i);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ComponentCaller)) {
            ComponentCaller componentCaller = (ComponentCaller) obj;
            if (this.mActivityToken == componentCaller.mActivityToken && this.mCallerToken == componentCaller.mCallerToken) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((527 + Objects.hashCode(this.mActivityToken)) * 31) + Objects.hashCode(this.mCallerToken);
    }
}
