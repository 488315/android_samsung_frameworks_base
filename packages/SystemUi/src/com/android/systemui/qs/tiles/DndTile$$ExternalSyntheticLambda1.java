package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.widget.TextView;
import com.android.systemui.qs.tiles.DndTile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DndTile$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DndTile$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DndTile dndTile = (DndTile) this.f$0;
                dndTile.getClass();
                int i = DndTile.DndDetailAdapter.$r8$clinit;
                DndTile.DndDetailAdapter dndDetailAdapter = dndTile.mDetailAdapter;
                TextView textView = dndDetailAdapter.mSummary;
                if (textView != null) {
                    textView.setText(dndDetailAdapter.this$0.mDndMenuSummary);
                    break;
                }
                break;
            default:
                ((Dialog) this.f$0).show();
                break;
        }
    }
}
