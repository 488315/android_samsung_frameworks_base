package android.app;

import android.os.IBinder;
import com.android.internal.util.Preconditions;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class BackgroundStartPrivileges {
    private final boolean mAllowsBackgroundActivityStarts;
    private final boolean mAllowsBackgroundForegroundServiceStarts;
    private final IBinder mOriginatingToken;
    public static final BackgroundStartPrivileges NONE = new BackgroundStartPrivileges(false, false, null);
    public static final BackgroundStartPrivileges ALLOW_BAL = new BackgroundStartPrivileges(true, true, null);
    public static final BackgroundStartPrivileges ALLOW_FGS = new BackgroundStartPrivileges(false, true, null);

    private BackgroundStartPrivileges(boolean z, boolean z2, IBinder iBinder) {
        Preconditions.checkArgument(!z || z2, "backgroundActivityStarts implies bgFgServiceStarts");
        this.mAllowsBackgroundActivityStarts = z;
        this.mAllowsBackgroundForegroundServiceStarts = z2;
        this.mOriginatingToken = iBinder;
    }

    public static BackgroundStartPrivileges allowBackgroundActivityStarts(IBinder iBinder) {
        if (iBinder == null) {
            return ALLOW_BAL;
        }
        return new BackgroundStartPrivileges(true, true, iBinder);
    }

    public BackgroundStartPrivileges merge(BackgroundStartPrivileges backgroundStartPrivileges) {
        BackgroundStartPrivileges backgroundStartPrivileges2 = NONE;
        if (backgroundStartPrivileges != backgroundStartPrivileges2 && backgroundStartPrivileges != null) {
            if (this != backgroundStartPrivileges2) {
                boolean z = allowsBackgroundActivityStarts() || backgroundStartPrivileges.allowsBackgroundActivityStarts();
                boolean z2 = allowsBackgroundFgsStarts() || backgroundStartPrivileges.allowsBackgroundFgsStarts();
                IBinder iBinder = this.mOriginatingToken;
                if (iBinder != backgroundStartPrivileges.mOriginatingToken) {
                    if (z) {
                        return ALLOW_BAL;
                    }
                    return z2 ? ALLOW_FGS : backgroundStartPrivileges2;
                }
                if (this.mAllowsBackgroundActivityStarts != z || this.mAllowsBackgroundForegroundServiceStarts != z2) {
                    if (backgroundStartPrivileges.mAllowsBackgroundActivityStarts != z || backgroundStartPrivileges.mAllowsBackgroundForegroundServiceStarts != z2) {
                        return new BackgroundStartPrivileges(z, z2, iBinder);
                    }
                }
            }
            return backgroundStartPrivileges;
        }
        return this;
    }

    public static BackgroundStartPrivileges merge(List<BackgroundStartPrivileges> list) {
        if (list == null || list.isEmpty()) {
            return NONE;
        }
        BackgroundStartPrivileges backgroundStartPrivileges = list.get(0);
        int size = list.size();
        while (true) {
            int i = size - 1;
            if (size <= 1) {
                return backgroundStartPrivileges;
            }
            backgroundStartPrivileges = backgroundStartPrivileges.merge(list.get(i));
            size = i;
        }
    }

    public boolean allowsBackgroundActivityStarts() {
        return this.mAllowsBackgroundActivityStarts;
    }

    public boolean allowsBackgroundFgsStarts() {
        return this.mAllowsBackgroundForegroundServiceStarts;
    }

    public boolean allowsAny() {
        return this.mAllowsBackgroundActivityStarts || this.mAllowsBackgroundForegroundServiceStarts;
    }

    public boolean allowsNothing() {
        return !allowsAny();
    }

    public IBinder getOriginatingToken() {
        return this.mOriginatingToken;
    }

    public String toString() {
        if (this == ALLOW_BAL) {
            return "BSP.ALLOW_BAL";
        }
        if (this == ALLOW_FGS) {
            return "BSP.ALLOW_FGS";
        }
        if (this == NONE) {
            return "BSP.NONE";
        }
        return "BackgroundStartPrivileges[allowsBackgroundActivityStarts=" + this.mAllowsBackgroundActivityStarts + ", allowsBackgroundForegroundServiceStarts=" + this.mAllowsBackgroundForegroundServiceStarts + ", originatingToken=" + this.mOriginatingToken + ']';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            BackgroundStartPrivileges backgroundStartPrivileges = (BackgroundStartPrivileges) obj;
            if (this.mAllowsBackgroundActivityStarts == backgroundStartPrivileges.mAllowsBackgroundActivityStarts && this.mAllowsBackgroundForegroundServiceStarts == backgroundStartPrivileges.mAllowsBackgroundForegroundServiceStarts && Objects.equals(this.mOriginatingToken, backgroundStartPrivileges.mOriginatingToken)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mAllowsBackgroundActivityStarts), Boolean.valueOf(this.mAllowsBackgroundForegroundServiceStarts), this.mOriginatingToken);
    }
}
