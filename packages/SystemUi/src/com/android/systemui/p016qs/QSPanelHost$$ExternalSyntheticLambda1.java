package com.android.systemui.p016qs;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.systemui.p016qs.SecQSPanelControllerBase;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.p013qs.QSTileView;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SecQSPanelControllerBase.TileRecord) obj).tile.removeCallbacks();
                return;
            case 1:
                ((QSTile) obj).refreshState();
                return;
            case 2:
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(obj);
                throw null;
            case 3:
                ((QSTileView) obj).onPanelModeChanged();
                return;
            default:
                ((QSTile) obj).click(null);
                return;
        }
    }
}
