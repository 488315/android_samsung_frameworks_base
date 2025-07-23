package androidx.media;

import android.media.session.MediaSessionManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
