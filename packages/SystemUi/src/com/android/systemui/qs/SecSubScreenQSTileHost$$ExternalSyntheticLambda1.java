package com.android.systemui.qs;

import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.qs.QSTile;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        switch (this.$r8$classId) {
            case 0:
                SecSubScreenQSTileHost secSubScreenQSTileHost = (SecSubScreenQSTileHost) this.f$0;
                Map.Entry entry = (Map.Entry) obj;
                secSubScreenQSTileHost.mQSTileInstanceManager.releaseTileUsing(secSubScreenQSTileHost.mTileUsingBySubScreen, ((QSTile) entry.getValue()).getTileSpec());
                secSubScreenQSTileHost.mQSLogger.logTileDestroyed((String) entry.getKey(), "Tile removed at SubScreen");
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Destroying tile: "), (String) entry.getKey(), "SecSubScreenQSTileHost");
                break;
            default:
                ((ArrayList) this.f$0).add((String) obj);
                break;
        }
    }
}
