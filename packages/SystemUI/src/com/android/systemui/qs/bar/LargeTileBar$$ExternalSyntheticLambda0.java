package com.android.systemui.qs.bar;

import com.android.systemui.qs.SecQSPanelControllerBase;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LargeTileBar$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LargeTileBar f$0;

    public /* synthetic */ LargeTileBar$$ExternalSyntheticLambda0(LargeTileBar largeTileBar, int i) {
        this.$r8$classId = i;
        this.f$0 = largeTileBar;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        LargeTileBar largeTileBar = this.f$0;
        SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
        switch (i) {
            case 0:
                largeTileBar.getClass();
                tileRecord.tile.setListening(largeTileBar, false);
                break;
            case 1:
                largeTileBar.mBGColorHelper.removeFromBarBackground(tileRecord.tileView);
                break;
            case 2:
                largeTileBar.mTileContainer.addView(tileRecord.tileView);
                ColoredBGHelper coloredBGHelper = largeTileBar.mBGColorHelper;
                if (coloredBGHelper != null) {
                    coloredBGHelper.addBarBackground(tileRecord.tileView, false);
                    break;
                }
                break;
            default:
                largeTileBar.getClass();
                tileRecord.tile.setListening(largeTileBar, largeTileBar.mListening && largeTileBar.mQsExpanded);
                break;
        }
    }
}
