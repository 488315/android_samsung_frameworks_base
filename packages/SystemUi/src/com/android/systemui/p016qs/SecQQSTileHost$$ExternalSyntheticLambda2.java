package com.android.systemui.p016qs;

import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.p013qs.QSTile;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQQSTileHost$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecQQSTileHost$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SecQQSTileHost secQQSTileHost = (SecQQSTileHost) this.f$0;
                Map.Entry entry = (Map.Entry) obj;
                secQQSTileHost.mQSTileInstanceManager.releaseTileUsing(secQQSTileHost.mTileUsingByQQS, ((QSTile) entry.getValue()).getTileSpec());
                secQQSTileHost.mQSLogger.logTileDestroyed((String) entry.getKey(), "Tile removed at QQS");
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Destroying tile: "), (String) entry.getKey(), "SecQQSTileHost");
                break;
            case 1:
                SecQQSTileHost secQQSTileHost2 = (SecQQSTileHost) this.f$0;
                Map.Entry entry2 = (Map.Entry) obj;
                secQQSTileHost2.mQSTileInstanceManager.releaseTileUsing(secQQSTileHost2.mTileUsingByQQS, ((QSTile) entry2.getValue()).getTileSpec());
                secQQSTileHost2.mQSLogger.logTileDestroyed((String) entry2.getKey(), "Tile removed at QQS");
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Destroying tile: "), (String) entry2.getKey(), "SecQQSTileHost");
                break;
            default:
                ((ArrayList) this.f$0).add((String) obj);
                break;
        }
    }
}
