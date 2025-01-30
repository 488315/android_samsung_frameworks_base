package com.android.systemui.media;

import com.android.systemui.media.SecMediaHost;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda0(SecMediaHost secMediaHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secMediaHost;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SecMediaHost secMediaHost = this.f$0;
                secMediaHost.getClass();
                SecMediaHost.iteratePlayers((SecMediaPlayerData) obj, new SecMediaHost$$ExternalSyntheticLambda0(secMediaHost, 5));
                break;
            case 1:
                this.f$0.getClass();
                SecMediaHost.iteratePlayers((SecMediaPlayerData) obj, new SecMediaHost$$ExternalSyntheticLambda14(0));
                break;
            case 2:
                this.f$0.mVisibilityListeners.add((SecMediaHost.MediaPanelVisibilityListener) obj);
                break;
            case 3:
                this.f$0.mVisibilityListeners.remove((SecMediaHost.MediaPanelVisibilityListener) obj);
                break;
            case 4:
                this.f$0.onMediaVisibilityChanged((Boolean) obj);
                break;
            case 5:
                ((SecMediaControlPanel) obj).setListening(this.f$0.mLocalListening);
                break;
            default:
                ((SecMediaControlPanel) obj).mToggleCallback = this.f$0.mPlayerBarExpandHelper.expandCallback;
                break;
        }
    }
}
