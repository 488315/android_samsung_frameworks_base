package com.android.systemui.statusbar;

import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardSecIndicationPolicy$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KeyguardSecIndicationPolicy$$ExternalSyntheticLambda3(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                IndicationPosition indicationPosition = (IndicationPosition) obj;
                ((KeyguardSecIndicationController) this.f$1).onIndicationChanged(indicationPosition, (IndicationItem) ((KeyguardSecIndicationPolicy) this.f$0).mTopItemMap.get(indicationPosition));
                break;
            default:
                ((KeyguardSecIndicationController) obj).onIndicationChanged((IndicationPosition) this.f$0, (IndicationItem) this.f$1);
                break;
        }
    }
}
