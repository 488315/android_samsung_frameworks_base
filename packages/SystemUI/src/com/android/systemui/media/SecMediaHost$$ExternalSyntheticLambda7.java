package com.android.systemui.media;

import android.view.View;
import java.util.function.Consumer;

public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda7(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                QSMediaCornerRoundedView qSMediaCornerRoundedView = (QSMediaCornerRoundedView) ((View) obj);
                qSMediaCornerRoundedView.mIsCornerRound = false;
                qSMediaCornerRoundedView.invalidate();
                break;
            case 1:
                SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) obj;
                if (secMediaControlPanel.mType.getSupportCapsule() && secMediaControlPanel.mIsPlayerCoverPlayed) {
                    secMediaControlPanel.mIsPlayerCoverPlayed = false;
                    break;
                }
                break;
            default:
                ((SecMediaControlPanel) obj).updateResources();
                break;
        }
    }
}
