package com.android.systemui.qs;

import android.text.TextUtils;
import com.android.systemui.Dependency;
import com.android.systemui.indexsearch.SystemUIIndexMediator;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda13 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileHost f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda13(QSTileHost qSTileHost, int i) {
        this.$r8$classId = i;
        this.f$0 = qSTileHost;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String tileMapKey;
        int i = this.$r8$classId;
        QSTileHost qSTileHost = this.f$0;
        switch (i) {
            case 0:
                if (qSTileHost.mEditor != null) {
                    ArrayList arrayList = new ArrayList();
                    for (QSTile qSTile : (List) qSTileHost.mTiles.values().stream().collect(Collectors.toList())) {
                        if ((qSTile instanceof SQSTile) && (tileMapKey = ((SQSTile) qSTile).getTileMapKey()) != null) {
                            arrayList.add(tileMapKey);
                        }
                    }
                    qSTileHost.mEditor.putString(SystemUIAnalytics.EID_2DEPTH_QUICK_BUTTON_ORDER, TextUtils.join(",", arrayList));
                    qSTileHost.mEditor.apply();
                    if (QSTileHost.LOGGING_DEBUG) {
                        SystemUIAnalytics.getCurrentScreenID();
                        return;
                    }
                    return;
                }
                return;
            default:
                qSTileHost.getClass();
                SystemUIIndexMediator systemUIIndexMediator = (SystemUIIndexMediator) Dependency.sDependency.getDependencyInner(SystemUIIndexMediator.class);
                ArrayList arrayList2 = qSTileHost.mSearchables;
                synchronized (systemUIIndexMediator.mTileSearchables) {
                    systemUIIndexMediator.mTileSearchables.clear();
                    systemUIIndexMediator.mTileSearchables.addAll(arrayList2);
                }
                return;
        }
    }
}
