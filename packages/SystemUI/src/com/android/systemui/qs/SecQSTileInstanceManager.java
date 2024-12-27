package com.android.systemui.qs;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArraySet;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.LinkedHashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecQSTileInstanceManager {
    public final QSLogger mQSLogger;
    public final QSTileHost mQSTileHost;
    public final LinkedHashMap mTileInstances = new LinkedHashMap();
    public final LinkedHashMap mTileUsingHosts = new LinkedHashMap();
    public int mUserId;

    public SecQSTileInstanceManager(Context context, QSTileHost qSTileHost, UserTracker userTracker, QSLogger qSLogger) {
        this.mQSTileHost = qSTileHost;
        this.mQSLogger = qSLogger;
        this.mUserId = ((UserTrackerImpl) userTracker).getUserId();
    }

    public final void releaseTileUsing(Object obj, String str) {
        ArraySet arraySet;
        QSTile qSTile = (QSTile) this.mTileInstances.get(str);
        StringBuilder sb = new StringBuilder("releaseTileUsing host:");
        sb.append(obj);
        sb.append(" tile: ");
        sb.append(qSTile);
        sb.append(" ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "SecQSTileInstanceManager");
        if (qSTile == null || (arraySet = (ArraySet) this.mTileUsingHosts.get(str)) == null) {
            return;
        }
        arraySet.remove(obj);
        if (!arraySet.isEmpty()) {
            this.mTileUsingHosts.put(str, arraySet);
            return;
        }
        this.mTileUsingHosts.remove(str);
        qSTile.destroy();
        this.mTileInstances.remove(str);
    }

    public final QSTile requestTileUsing(Object obj, String str) {
        QSTile qSTile = (QSTile) this.mTileInstances.get(str);
        StringBuilder sb = new StringBuilder("requestTileUsing host:");
        sb.append(obj);
        sb.append(" tile: ");
        sb.append(qSTile);
        sb.append(" ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "SecQSTileInstanceManager");
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
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("No factory for a spec: ", str, "SecQSTileInstanceManager");
            }
            if (qSTile != null) {
                this.mTileInstances.put(str, qSTile);
            }
        }
        if (qSTile != null) {
            ArraySet arraySet = (ArraySet) this.mTileUsingHosts.get(str);
            if (arraySet == null) {
                arraySet = new ArraySet();
            }
            arraySet.add(obj);
            this.mTileUsingHosts.put(str, arraySet);
        }
        return qSTile;
    }
}
