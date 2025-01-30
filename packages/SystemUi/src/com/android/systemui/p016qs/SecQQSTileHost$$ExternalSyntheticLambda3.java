package com.android.systemui.p016qs;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.p013qs.SQSTile;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQQSTileHost$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecQQSTileHost f$0;

    public /* synthetic */ SecQQSTileHost$$ExternalSyntheticLambda3(SecQQSTileHost secQQSTileHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secQQSTileHost;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String tileMapKey;
        switch (this.$r8$classId) {
            case 0:
                SecQQSTileHost secQQSTileHost = this.f$0;
                secQQSTileHost.restoreQQSTileListIfNeeded(null);
                Log.d("SecQQSTileHost", "start addTunable");
                ((TunerService) Dependency.get(TunerService.class)).addTunable(secQQSTileHost, "sysui_quick_qs_tiles");
                break;
            default:
                SecQQSTileHost secQQSTileHost2 = this.f$0;
                SharedPreferences.Editor editor = secQQSTileHost2.mEditor;
                if (editor != null) {
                    ArrayList arrayList = new ArrayList();
                    for (QSTile qSTile : secQQSTileHost2.mTiles.values()) {
                        if ((qSTile instanceof SQSTile) && (tileMapKey = ((SQSTile) qSTile).getTileMapKey()) != null) {
                            arrayList.add(tileMapKey);
                        }
                    }
                    editor.putString("QPBS1100", TextUtils.join(",", arrayList));
                    editor.apply();
                    if (SecQQSTileHost.LOGGING_DEBUG) {
                        boolean z = SystemUIAnalytics.sConfigured;
                        break;
                    }
                }
                break;
        }
    }
}
