package com.android.systemui.media;

import com.android.systemui.media.SecMediaHost;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda7(SecMediaHost secMediaHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secMediaHost;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        SecMediaHost secMediaHost = this.f$0;
        switch (i) {
            case 0:
                secMediaHost.mVisibilityListeners.add((SecMediaHost.MediaPanelVisibilityListener) obj);
                break;
            case 1:
                secMediaHost.getClass();
                SecMediaHost.iteratePlayers((SecMediaPlayerData) obj, new SecMediaHost$$ExternalSyntheticLambda3(3));
                break;
            default:
                secMediaHost.mVisibilityListeners.remove((SecMediaHost.MediaPanelVisibilityListener) obj);
                break;
        }
    }
}
