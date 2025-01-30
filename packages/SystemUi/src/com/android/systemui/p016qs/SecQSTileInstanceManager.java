package com.android.systemui.p016qs;

import android.content.Context;
import android.os.Handler;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArraySet;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.LinkedHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSTileInstanceManager {
    public final QSLogger mQSLogger;
    public final QSTileHost mQSTileHost;
    public final LinkedHashMap mTileInstances = new LinkedHashMap();
    public final LinkedHashMap mTileUsingHosts = new LinkedHashMap();
    public int mUserId;

    public SecQSTileInstanceManager(Context context, QSTileHost qSTileHost, UserTracker userTracker, QSLogger qSLogger) {
        new Handler();
        this.mQSTileHost = qSTileHost;
        this.mQSLogger = qSLogger;
        this.mUserId = ((UserTrackerImpl) userTracker).getUserId();
    }

    public final void releaseTileUsing(Object obj, String str) {
        LinkedHashMap linkedHashMap = this.mTileInstances;
        QSTile qSTile = (QSTile) linkedHashMap.get(str);
        StringBuilder sb = new StringBuilder("releaseTileUsing host:");
        sb.append(obj);
        sb.append(" tile: ");
        sb.append(qSTile);
        sb.append(" ");
        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str, "SecQSTileInstanceManager");
        if (qSTile != null) {
            LinkedHashMap linkedHashMap2 = this.mTileUsingHosts;
            ArraySet arraySet = (ArraySet) linkedHashMap2.get(str);
            if (arraySet != null) {
                arraySet.remove(obj);
                if (!arraySet.isEmpty()) {
                    linkedHashMap2.put(str, arraySet);
                    return;
                }
                linkedHashMap2.remove(str);
                qSTile.destroy();
                linkedHashMap.remove(str);
            }
        }
    }

    public final QSTile requestTileUsing(Object obj, String str) {
        LinkedHashMap linkedHashMap = this.mTileInstances;
        QSTile qSTile = (QSTile) linkedHashMap.get(str);
        StringBuilder sb = new StringBuilder("requestTileUsing host:");
        sb.append(obj);
        sb.append(" tile: ");
        sb.append(qSTile);
        sb.append(" ");
        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str, "SecQSTileInstanceManager");
        if (qSTile == null) {
            qSTile = this.mQSTileHost.createTile(str);
            if (qSTile != null) {
                qSTile.setTileSpec(str);
                boolean isAvailable = qSTile.isAvailable();
                QSLogger qSLogger = this.mQSLogger;
                if (isAvailable) {
                    qSLogger.logTileAdded(str);
                } else {
                    qSTile.destroy();
                    Log.d("SecQSTileInstanceManager", "Destroying not available tile: " + str);
                    qSLogger.logTileDestroyed(str, "Tile not available");
                    qSTile = null;
                }
            } else {
                AbstractC0000x2c234b15.m3m("No factory for a spec: ", str, "SecQSTileInstanceManager");
            }
            if (qSTile != null) {
                linkedHashMap.put(str, qSTile);
            }
        }
        if (qSTile != null) {
            LinkedHashMap linkedHashMap2 = this.mTileUsingHosts;
            ArraySet arraySet = (ArraySet) linkedHashMap2.get(str);
            if (arraySet == null) {
                arraySet = new ArraySet();
            }
            arraySet.add(obj);
            linkedHashMap2.put(str, arraySet);
        }
        return qSTile;
    }
}
