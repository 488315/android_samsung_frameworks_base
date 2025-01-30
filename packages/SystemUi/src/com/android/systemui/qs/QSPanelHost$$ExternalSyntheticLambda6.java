package com.android.systemui.qs;

import com.android.systemui.Dumpable;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda6(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                QSPanelHost qSPanelHost = (QSPanelHost) this.f$0;
                List list = (List) this.f$1;
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                qSPanelHost.getClass();
                list.add(tileRecord);
                qSPanelHost.mRecords.add(tileRecord);
                break;
            default:
                PrintWriter printWriter = (PrintWriter) this.f$0;
                String[] strArr = (String[]) this.f$1;
                SecQSPanelControllerBase.TileRecord tileRecord2 = (SecQSPanelControllerBase.TileRecord) obj;
                printWriter.print("    ");
                ((Dumpable) tileRecord2.tile).dump(printWriter, strArr);
                printWriter.print("    ");
                printWriter.println(tileRecord2.tileView.toString());
                break;
        }
    }
}
