package com.android.systemui.qs.bar;

import android.widget.LinearLayout;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.util.function.Consumer;

public final /* synthetic */ class BrightnessBar$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BrightnessBar$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                BrightnessBar brightnessBar = (BrightnessBar) obj2;
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                brightnessBar.mTileLayout.removeView(tileRecord.tileView);
                tileRecord.tile.setListening(brightnessBar, false);
                break;
            case 1:
                BrightnessBar brightnessBar2 = (BrightnessBar) obj2;
                brightnessBar2.getClass();
                ((SecQSPanelControllerBase.TileRecord) obj).tile.setListening(brightnessBar2, brightnessBar2.mListening);
                break;
            default:
                ((LinearLayout) obj2).addView(((SecQSPanelControllerBase.TileRecord) obj).tileView);
                break;
        }
    }
}
