package com.android.systemui.qs.external;

import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.TileServiceRequestController;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class TileServiceRequestController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TileServiceRequestController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i = TileServiceRequestController.$r8$clinit;
                return new TileServiceRequestController.TileServiceRequestCommand((TileServiceRequestController) obj);
            default:
                int i2 = TileServiceRequestController.$r8$clinit;
                return new TileRequestDialog(((QSHost) obj).getContext());
        }
    }
}
