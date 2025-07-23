package com.android.systemui.media;

import android.view.View;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;

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
            case 2:
                SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) obj;
                if (secMediaControlPanel2.mType.getSupportOAChip() && secMediaControlPanel2.mIsPlayerOAPlayed) {
                    secMediaControlPanel2.mIsPlayerOAPlayed = false;
                    break;
                }
                break;
            default:
                ((SecMediaControlPanel) obj).updateResources();
                break;
        }
    }
}
