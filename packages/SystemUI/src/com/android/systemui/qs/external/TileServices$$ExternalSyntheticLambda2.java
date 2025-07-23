package com.android.systemui.qs.external;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileServices$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TileServices$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                TileServices tileServices = (TileServices) obj2;
                CustomTileInterface customTileInterface = (CustomTileInterface) obj;
                boolean z = TileServices.DEBUG;
                tileServices.getClass();
                if (customTileInterface.isSecActiveTile()) {
                    tileServices.requestListening(customTileInterface.getComponent());
                    break;
                }
                break;
            default:
                ((ArrayList) obj2).add((CustomTileInterface) obj);
                break;
        }
    }
}
