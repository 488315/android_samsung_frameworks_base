package com.android.systemui.qs.external;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        switch (this.$r8$classId) {
            case 0:
                TileServices tileServices = (TileServices) this.f$0;
                CustomTile customTile = (CustomTile) obj;
                tileServices.getClass();
                if (customTile.isSecActiveTile()) {
                    tileServices.requestListening(customTile.mComponent);
                    break;
                }
                break;
            default:
                ((ArrayList) this.f$0).add((CustomTile) obj);
                break;
        }
    }
}
