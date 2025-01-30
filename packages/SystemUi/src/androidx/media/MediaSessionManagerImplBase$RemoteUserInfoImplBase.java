package androidx.media;

import android.text.TextUtils;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MediaSessionManagerImplBase$RemoteUserInfoImplBase {
    public final String mPackageName;
    public final int mPid;
    public final int mUid;

    public MediaSessionManagerImplBase$RemoteUserInfoImplBase(String str, int i, int i2) {
        this.mPackageName = str;
        this.mPid = i;
        this.mUid = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaSessionManagerImplBase$RemoteUserInfoImplBase)) {
            return false;
        }
        MediaSessionManagerImplBase$RemoteUserInfoImplBase mediaSessionManagerImplBase$RemoteUserInfoImplBase = (MediaSessionManagerImplBase$RemoteUserInfoImplBase) obj;
        int i = this.mUid;
        String str = this.mPackageName;
        int i2 = this.mPid;
        return (i2 < 0 || mediaSessionManagerImplBase$RemoteUserInfoImplBase.mPid < 0) ? TextUtils.equals(str, mediaSessionManagerImplBase$RemoteUserInfoImplBase.mPackageName) && i == mediaSessionManagerImplBase$RemoteUserInfoImplBase.mUid : TextUtils.equals(str, mediaSessionManagerImplBase$RemoteUserInfoImplBase.mPackageName) && i2 == mediaSessionManagerImplBase$RemoteUserInfoImplBase.mPid && i == mediaSessionManagerImplBase$RemoteUserInfoImplBase.mUid;
    }

    public final int hashCode() {
        return Objects.hash(this.mPackageName, Integer.valueOf(this.mUid));
    }
}
