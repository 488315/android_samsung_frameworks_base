package com.android.systemui.qs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileSALogHelper {
    public static final boolean LOGGING_DEBUG;
    public final SharedPreferences.Editor editor;
    public final CurrentTilesInteractor qsTilesInteractor;
    public final CurrentTilesInteractor quickQsTilesInteractor;
    public final HashMap tilesMap = new HashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        LOGGING_DEBUG = Log.isLoggable(SystemUIAnalytics.TAG_QUICK_SETTINGS, 3);
    }

    public TileSALogHelper(Context context, CurrentTilesInteractor currentTilesInteractor, CurrentTilesInteractor currentTilesInteractor2) {
        this.qsTilesInteractor = currentTilesInteractor;
        this.quickQsTilesInteractor = currentTilesInteractor2;
        String[] stringArray = context.getResources().getStringArray(R.array.tile_ids);
        ArrayList arrayList = new ArrayList();
        for (String str : stringArray) {
            str.getClass();
            arrayList.add(str);
            if (arrayList.size() == 3) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(arrayList.get(1));
                arrayList2.add(arrayList.get(2));
                this.tilesMap.put(arrayList.get(0), arrayList2);
                arrayList.clear();
            }
        }
        this.editor = context.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0).edit();
    }

    public final int getTileIndex(TileSpec tileSpec) {
        return SystemUIAnalytics.SID_QUICKPANEL_EXPANDED.equals(SystemUIAnalytics.getCurrentScreenID()) ? ((ArrayList) this.qsTilesInteractor.getCurrentTilesSpecs()).indexOf(tileSpec) : ((ArrayList) this.quickQsTilesInteractor.getCurrentTilesSpecs()).indexOf(tileSpec);
    }
}
