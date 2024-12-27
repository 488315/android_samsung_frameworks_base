package com.android.systemui.qs.bar;

import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                ColoredBGHelper coloredBGHelper = largeTileBar.mBGColorHelper;
                QSTileView qSTileView = tileRecord.tileView;
                if (coloredBGHelper.barBGList.contains(qSTileView)) {
                    coloredBGHelper.barBGList.remove(qSTileView);
                    break;
                }
                break;
            case 2:
                largeTileBar.mTileContainer.addView(tileRecord.tileView);
                break;
            default:
                largeTileBar.getClass();
                tileRecord.tile.setListening(largeTileBar, largeTileBar.mListening);
                break;
        }
    }
}
