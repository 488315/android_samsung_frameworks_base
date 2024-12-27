package com.android.systemui.media;

import com.android.systemui.media.SecMediaHost;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda5(SecMediaHost secMediaHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secMediaHost;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        SecMediaHost secMediaHost = this.f$0;
        switch (i) {
            case 0:
                secMediaHost.getClass();
                SecMediaHost.iteratePlayers((SecMediaPlayerData) obj, new SecMediaHost$$ExternalSyntheticLambda5(secMediaHost, 2));
                break;
            case 1:
                secMediaHost.mVisibilityListeners.remove((SecMediaHost.MediaPanelVisibilityListener) obj);
                break;
            case 2:
                ((SecMediaControlPanel) obj).setListening(secMediaHost.mLocalListening);
                break;
            case 3:
                secMediaHost.getClass();
                SecMediaHost.iteratePlayers((SecMediaPlayerData) obj, new SecMediaHost$$ExternalSyntheticLambda7(2));
                break;
            default:
                secMediaHost.mVisibilityListeners.add((SecMediaHost.MediaPanelVisibilityListener) obj);
                break;
        }
    }
}
