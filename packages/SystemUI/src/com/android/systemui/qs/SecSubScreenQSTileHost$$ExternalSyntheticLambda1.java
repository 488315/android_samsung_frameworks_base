package com.android.systemui.qs;

import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.qs.QSTile;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecSubScreenQSTileHost$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecSubScreenQSTileHost$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                SecSubScreenQSTileHost secSubScreenQSTileHost = (SecSubScreenQSTileHost) obj2;
                Map.Entry entry = (Map.Entry) obj;
                secSubScreenQSTileHost.mQSTileInstanceManager.releaseTileUsing(secSubScreenQSTileHost.mTileUsingBySubScreen, ((QSTile) entry.getValue()).getTileSpec());
                secSubScreenQSTileHost.mQSLogger.logTileDestroyed((String) entry.getKey(), "Tile removed at SubScreen");
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Destroying tile: "), (String) entry.getKey(), "SecSubScreenQSTileHost");
                break;
            default:
                ((ArrayList) obj2).add((String) obj);
                break;
        }
    }
}
