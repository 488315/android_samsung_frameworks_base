package androidx.media;

import android.media.session.MediaSessionManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaSessionManagerImplApi28$RemoteUserInfoImplApi28 extends MediaSessionManagerImplBase$RemoteUserInfoImplBase {
    public MediaSessionManagerImplApi28$RemoteUserInfoImplApi28(String str, int i, int i2) {
        super(str, i, i2);
        new MediaSessionManager.RemoteUserInfo(str, i, i2);
    }

    public MediaSessionManagerImplApi28$RemoteUserInfoImplApi28(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        super(remoteUserInfo.getPackageName(), remoteUserInfo.getPid(), remoteUserInfo.getUid());
    }
}
