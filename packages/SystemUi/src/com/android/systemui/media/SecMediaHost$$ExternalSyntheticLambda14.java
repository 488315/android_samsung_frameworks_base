package com.android.systemui.media;

import android.view.View;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda14 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda14(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SecMediaControlPanel) obj).updateResources();
                break;
            case 1:
                QSMediaCornerRoundedView qSMediaCornerRoundedView = (QSMediaCornerRoundedView) ((View) obj);
                qSMediaCornerRoundedView.mIsCornerRound = false;
                qSMediaCornerRoundedView.invalidate();
                break;
            default:
                SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) obj;
                if (secMediaControlPanel.mType.getSupportCapsule() && secMediaControlPanel.mIsPlayerCoverPlayed) {
                    secMediaControlPanel.mIsPlayerCoverPlayed = false;
                    break;
                }
                break;
        }
    }
}
