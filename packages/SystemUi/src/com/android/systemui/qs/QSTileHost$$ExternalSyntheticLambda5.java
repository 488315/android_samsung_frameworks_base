package com.android.systemui.qs;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.android.systemui.Dependency;
import com.android.systemui.indexsearch.SystemUIIndexMediator;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileHost f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda5(QSTileHost qSTileHost, int i) {
        this.$r8$classId = i;
        this.f$0 = qSTileHost;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String tileMapKey;
        switch (this.$r8$classId) {
            case 0:
                QSTileHost qSTileHost = this.f$0;
                SharedPreferences.Editor editor = qSTileHost.mEditor;
                if (editor != null) {
                    ArrayList arrayList = new ArrayList();
                    for (QSTile qSTile : (List) qSTileHost.mTiles.values().stream().collect(Collectors.toList())) {
                        if ((qSTile instanceof SQSTile) && (tileMapKey = ((SQSTile) qSTile).getTileMapKey()) != null) {
                            arrayList.add(tileMapKey);
                        }
                    }
                    editor.putString("QPBS1101", TextUtils.join(",", arrayList));
                    editor.apply();
                    if (QSTileHost.LOGGING_DEBUG) {
                        boolean z = SystemUIAnalytics.sConfigured;
                        return;
                    }
                    return;
                }
                return;
            default:
                QSTileHost qSTileHost2 = this.f$0;
                qSTileHost2.getClass();
                SystemUIIndexMediator systemUIIndexMediator = (SystemUIIndexMediator) Dependency.get(SystemUIIndexMediator.class);
                ArrayList arrayList2 = qSTileHost2.mSearchables;
                synchronized (systemUIIndexMediator.mTileSearchables) {
                    systemUIIndexMediator.mTileSearchables.clear();
                    systemUIIndexMediator.mTileSearchables.addAll(arrayList2);
                }
                return;
        }
    }
}
