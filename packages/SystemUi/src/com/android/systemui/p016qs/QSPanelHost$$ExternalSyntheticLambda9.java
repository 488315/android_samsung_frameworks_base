package com.android.systemui.p016qs;

import android.util.Log;
import com.android.systemui.p016qs.SecQSPanelControllerBase;
import com.android.systemui.p016qs.bar.TileHostable;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ QSPanelHost f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda9(QSPanelHost qSPanelHost, String str, TileHostable tileHostable) {
        this.f$0 = qSPanelHost;
        this.f$2 = str;
        this.f$1 = tileHostable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                QSPanelHost qSPanelHost = this.f$0;
                List list = (List) this.f$1;
                String str = this.f$2;
                TileHostable tileHostable = (TileHostable) obj;
                qSPanelHost.getClass();
                tileHostable.removeAllTiles();
                list.forEach(new QSPanelHost$$ExternalSyntheticLambda9(qSPanelHost, str, tileHostable));
                break;
            default:
                QSPanelHost qSPanelHost2 = this.f$0;
                String str2 = this.f$2;
                TileHostable tileHostable2 = (TileHostable) this.f$1;
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                qSPanelHost2.getClass();
                Log.d("QSPanelHost", str2 + ": tile[ " + tileRecord.tile.getTileSpec() + " ]: record: " + tileRecord);
                qSPanelHost2.addCallbackAndInit(tileRecord);
                tileHostable2.addTile(tileRecord);
                break;
        }
    }

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda9(QSPanelHost qSPanelHost, List list, String str) {
        this.f$0 = qSPanelHost;
        this.f$1 = list;
        this.f$2 = str;
    }
}
