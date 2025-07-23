package com.android.systemui.qs;

import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import dagger.Lazy;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecQSTileInstanceManager {
    public final QSPipelineFlagsRepository mFeatureFlags;
    public final Lazy mQSHost;
    public final QSPipelineLogger mQSPipelineLogger;
    public final QSFactory mQsFactory;
    public final ConcurrentHashMap mTileInstances = new ConcurrentHashMap();
    public final ConcurrentHashMap mTileUsingHosts = new ConcurrentHashMap();
    public int mUserId;

    public SecQSTileInstanceManager(UserTracker userTracker, QSFactory qSFactory, Lazy lazy, QSPipelineFlagsRepository qSPipelineFlagsRepository, Lazy lazy2, QSPipelineLogger qSPipelineLogger) {
        this.mUserId = ((UserTrackerImpl) userTracker).getUserId();
        this.mQsFactory = qSFactory;
        this.mFeatureFlags = qSPipelineFlagsRepository;
        this.mQSHost = lazy2;
        this.mQSPipelineLogger = qSPipelineLogger;
    }

    public final void releaseTileUsing(Object obj, TileSpec tileSpec) {
        ArraySet arraySet;
        QSTile qSTile = (QSTile) this.mTileInstances.get(tileSpec);
        Log.d("SecQSTileInstanceManager", "releaseTileUsing host:" + obj + " tile: " + qSTile + " " + tileSpec);
        if (qSTile == null || (arraySet = (ArraySet) this.mTileUsingHosts.get(tileSpec)) == null) {
            return;
        }
        arraySet.remove(obj);
        if (!arraySet.isEmpty()) {
            this.mTileUsingHosts.put(tileSpec, arraySet);
            return;
        }
        this.mTileUsingHosts.remove(tileSpec);
        Log.i("SecQSTileInstanceManager", "Destroy tile  " + tileSpec);
        qSTile.destroy();
        this.mTileInstances.remove(tileSpec);
    }

    public final QSTile requestTileUsing(Object obj, TileSpec tileSpec) {
        QSTile qSTile = (QSTile) this.mTileInstances.get(tileSpec);
        Log.d("SecQSTileInstanceManager", "requestTileUsing host:" + obj + " tile: " + qSTile + " " + tileSpec);
        if (qSTile == null) {
            Log.i("SecQSTileInstanceManager", "createTileInstance  " + tileSpec);
            this.mFeatureFlags.getClass();
            qSTile = this.mQsFactory.createTile(tileSpec.getSpec());
            if (qSTile != null) {
                this.mTileInstances.put(tileSpec, qSTile);
                boolean equals = obj.equals("Bar");
                QSPipelineLogger qSPipelineLogger = this.mQSPipelineLogger;
                if (equals) {
                    qSPipelineLogger.logTileCreated(tileSpec, QSPipelineLogger.TileCreatedReason.BAR_TILE_CREATED);
                } else {
                    qSPipelineLogger.logTileCreated(tileSpec, QSPipelineLogger.TileCreatedReason.TILE_CREATED);
                }
            }
        }
        if (qSTile != null) {
            ArraySet arraySet = (ArraySet) this.mTileUsingHosts.get(tileSpec);
            if (arraySet == null) {
                arraySet = new ArraySet();
            }
            arraySet.add(obj);
            this.mTileUsingHosts.put(tileSpec, arraySet);
        }
        return qSTile;
    }
}
