package com.android.settingslib.media.session;

import android.media.session.MediaSessionManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaSessionManagerExtKt$activeMediaChanges$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaSessionManager f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MediaSessionManagerExtKt$activeMediaChanges$1$$ExternalSyntheticLambda0(MediaSessionManager mediaSessionManager, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaSessionManager;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.removeOnActiveSessionsChangedListener((MediaSessionManagerExtKt$activeMediaChanges$1$listener$1) this.f$1);
                break;
            default:
                this.f$0.unregisterRemoteSessionCallback((MediaSessionManagerExtKt$defaultRemoteSessionChanged$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
