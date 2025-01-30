package com.android.systemui.qs;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.qs.QSTile;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileHost f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda3(QSTileHost qSTileHost, int i) {
        this.$r8$classId = i;
        this.f$0 = qSTileHost;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                QSTileHost qSTileHost = this.f$0;
                Map.Entry entry = (Map.Entry) obj;
                qSTileHost.getClass();
                Log.d("QSTileHost", "Destroying tile: " + ((String) entry.getKey()));
                qSTileHost.mQSLogger.logTileDestroyed((String) entry.getKey(), "Tile removed");
                qSTileHost.mQSTileInstanceManager.releaseTileUsing(qSTileHost.mTileUsingByPanel, ((QSTile) entry.getValue()).getTileSpec());
                break;
            default:
                QSTileHost qSTileHost2 = this.f$0;
                Map.Entry entry2 = (Map.Entry) obj;
                if (QSTileHost.DEBUG) {
                    qSTileHost2.getClass();
                    ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Destroying tile: "), (String) entry2.getKey(), "QSTileHost");
                }
                qSTileHost2.mQSTileInstanceManager.releaseTileUsing(qSTileHost2.mTileUsingByPanel, ((QSTile) entry2.getValue()).getTileSpec());
                break;
        }
    }
}
