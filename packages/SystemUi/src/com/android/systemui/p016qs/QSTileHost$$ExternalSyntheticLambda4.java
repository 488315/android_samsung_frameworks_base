package com.android.systemui.p016qs;

import android.content.SharedPreferences;
import com.android.systemui.p016qs.QSTileHost;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.p045ex.peripheral.PeripheralBarcodeConstants;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileHost f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda4(QSTileHost qSTileHost, String str, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = qSTileHost;
        this.f$1 = str;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                QSTileHost qSTileHost = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                SharedPreferences.Editor editor = qSTileHost.mEditor;
                if (editor != null && str != null) {
                    int i2 = QSTileHost.TilesMap.SID_TILE_STATE;
                    qSTileHost.mTilesMap.getClass();
                    String id = QSTileHost.TilesMap.getId(i2, str);
                    if (id != null && !PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE.equals(id) && !"QPBS1002".equals(id) && !"QPBS1004".equals(id)) {
                        editor.putInt(id, i);
                        editor.apply();
                        if (QSTileHost.LOGGING_DEBUG) {
                            boolean z = SystemUIAnalytics.sConfigured;
                            break;
                        }
                    }
                }
                break;
            default:
                QSTileHost qSTileHost2 = this.f$0;
                final String str2 = this.f$1;
                final int i3 = this.f$2;
                qSTileHost2.getClass();
                qSTileHost2.changeTileSpecs(new Predicate() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        String str3 = str2;
                        int i4 = i3;
                        List list = (List) obj;
                        if (list.contains(str3)) {
                            return false;
                        }
                        int size = list.size();
                        if (i4 == -1 || i4 >= size) {
                            list.add(str3);
                        } else {
                            list.add(i4, str3);
                        }
                        return true;
                    }
                });
                break;
        }
    }
}
