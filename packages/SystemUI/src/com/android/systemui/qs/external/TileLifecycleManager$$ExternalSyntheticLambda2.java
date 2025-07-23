package com.android.systemui.qs.external;

import android.util.Log;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileLifecycleManager$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        QSTileServiceWrapper qSTileServiceWrapper = (QSTileServiceWrapper) obj;
        qSTileServiceWrapper.getClass();
        switch (i) {
            case 0:
                try {
                    qSTileServiceWrapper.mService.onTileAdded();
                    break;
                } catch (Exception e) {
                    Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
                    return false;
                }
            case 1:
                try {
                    qSTileServiceWrapper.mService.onUnlockComplete();
                    break;
                } catch (Exception e2) {
                    Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e2);
                    return false;
                }
            case 2:
                try {
                    qSTileServiceWrapper.mService.onStartListening();
                    break;
                } catch (Exception e3) {
                    Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e3);
                    return false;
                }
            case 3:
                try {
                    qSTileServiceWrapper.mService.onStopListening();
                    break;
                } catch (Exception e4) {
                    Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e4);
                    return false;
                }
            default:
                try {
                    qSTileServiceWrapper.mService.onTileRemoved();
                    break;
                } catch (Exception e5) {
                    Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e5);
                    return false;
                }
        }
        return false;
    }
}
