package com.android.systemui.qs;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.qs.QSTile;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileHost f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda6(QSTileHost qSTileHost, int i) {
        this.$r8$classId = i;
        this.f$0 = qSTileHost;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        QSTileHost qSTileHost = this.f$0;
        Map.Entry entry = (Map.Entry) obj;
        switch (i) {
            case 0:
                qSTileHost.getClass();
                Log.d("QSTileHost", "Destroying tile: " + ((String) entry.getKey()));
                qSTileHost.mQSLogger.logTileDestroyed((String) entry.getKey(), "Tile removed");
                qSTileHost.mQSTileInstanceManager.releaseTileUsing(qSTileHost.mTileUsingByPanel, ((QSTile) entry.getValue()).getTileSpec());
                break;
            default:
                if (QSTileHost.DEBUG) {
                    qSTileHost.getClass();
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Destroying tile: "), (String) entry.getKey(), "QSTileHost");
                }
                qSTileHost.mQSTileInstanceManager.releaseTileUsing(qSTileHost.mTileUsingByPanel, ((QSTile) entry.getValue()).getTileSpec());
                break;
        }
    }
}
