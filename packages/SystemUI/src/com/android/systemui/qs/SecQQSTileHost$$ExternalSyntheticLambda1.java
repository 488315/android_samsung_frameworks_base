package com.android.systemui.qs;

import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final /* synthetic */ class SecQQSTileHost$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecQQSTileHost f$0;

    public /* synthetic */ SecQQSTileHost$$ExternalSyntheticLambda1(SecQQSTileHost secQQSTileHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secQQSTileHost;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String tileMapKey;
        int i = this.$r8$classId;
        SecQQSTileHost secQQSTileHost = this.f$0;
        switch (i) {
            case 0:
                secQQSTileHost.restoreQQSTileListIfNeeded(null);
                Log.d("SecQQSTileHost", "start addTunable");
                ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).addTunable(secQQSTileHost, "sysui_quick_qs_tiles");
                break;
            default:
                if (secQQSTileHost.mEditor != null) {
                    ArrayList arrayList = new ArrayList();
                    for (QSTile qSTile : (List) secQQSTileHost.mTiles.values().stream().collect(Collectors.toList())) {
                        if ((qSTile instanceof SQSTile) && (tileMapKey = ((SQSTile) qSTile).getTileMapKey()) != null) {
                            arrayList.add(tileMapKey);
                        }
                    }
                    secQQSTileHost.mEditor.putString(SystemUIAnalytics.EID_1DEPTH_QUICK_BUTTON_ORDER, TextUtils.join(",", arrayList));
                    secQQSTileHost.mEditor.apply();
                    if (SecQQSTileHost.LOGGING_DEBUG) {
                        SystemUIAnalytics.getCurrentScreenID();
                        break;
                    }
                }
                break;
        }
    }
}
