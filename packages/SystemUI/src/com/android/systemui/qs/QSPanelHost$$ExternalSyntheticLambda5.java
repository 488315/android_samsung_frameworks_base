package com.android.systemui.qs;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.TileChunkLayoutBar$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SecQSPanelControllerBase.TileRecord) obj).tile.removeCallbacks();
                break;
            case 1:
                ((QSTile) obj).refreshState();
                break;
            case 2:
                ((TileChunkLayoutBar$$ExternalSyntheticLambda0) obj).f$0.calculateContainerHeight();
                break;
            default:
                ((QSTile) obj).click(null);
                break;
        }
    }
}
