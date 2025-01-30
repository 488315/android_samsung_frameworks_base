package androidx.media;

import android.media.session.MediaSessionManager;
import android.text.TextUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaSessionManager$RemoteUserInfo {
    public final MediaSessionManagerImplApi28$RemoteUserInfoImplApi28 mImpl;

    public MediaSessionManager$RemoteUserInfo(String str, int i, int i2) {
        if (str == null) {
            throw new NullPointerException("package shouldn't be null");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("packageName should be nonempty");
        }
        this.mImpl = new MediaSessionManagerImplApi28$RemoteUserInfoImplApi28(str, i, i2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaSessionManager$RemoteUserInfo)) {
            return false;
        }
        return this.mImpl.equals(((MediaSessionManager$RemoteUserInfo) obj).mImpl);
    }

    public final int hashCode() {
        return this.mImpl.hashCode();
    }

    public MediaSessionManager$RemoteUserInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        String packageName = remoteUserInfo.getPackageName();
        if (packageName != null) {
            if (!TextUtils.isEmpty(packageName)) {
                this.mImpl = new MediaSessionManagerImplApi28$RemoteUserInfoImplApi28(remoteUserInfo);
                return;
            }
            throw new IllegalArgumentException("packageName should be nonempty");
        }
        throw new NullPointerException("package shouldn't be null");
    }
}
