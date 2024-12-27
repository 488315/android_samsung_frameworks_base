package com.android.systemui.qs;

import com.android.systemui.Dumpable;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda14 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda14(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PrintWriter printWriter = (PrintWriter) this.f$0;
                String[] strArr = (String[]) this.f$1;
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                printWriter.print("    ");
                ((Dumpable) tileRecord.tile).dump(printWriter, strArr);
                printWriter.print("    ");
                printWriter.println(tileRecord.tileView.toString());
                break;
            default:
                QSPanelHost qSPanelHost = (QSPanelHost) this.f$0;
                List list = (List) this.f$1;
                SecQSPanelControllerBase.TileRecord tileRecord2 = (SecQSPanelControllerBase.TileRecord) obj;
                qSPanelHost.getClass();
                list.add(tileRecord2);
                qSPanelHost.mRecords.add(tileRecord2);
                break;
        }
    }
}
