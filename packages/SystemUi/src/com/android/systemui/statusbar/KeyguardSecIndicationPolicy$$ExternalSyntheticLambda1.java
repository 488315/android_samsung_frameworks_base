package com.android.systemui.statusbar;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardSecIndicationPolicy$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KeyguardSecIndicationPolicy$$ExternalSyntheticLambda1(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = (KeyguardSecIndicationPolicy) this.f$0;
                IndicationPosition indicationPosition = (IndicationPosition) obj;
                ((KeyguardSecIndicationController) ((IndicationChangeListener) this.f$1)).onIndicationChanged(indicationPosition, (IndicationItem) keyguardSecIndicationPolicy.mTopItemMap.get(indicationPosition));
                break;
            default:
                ((KeyguardSecIndicationController) ((IndicationChangeListener) obj)).onIndicationChanged((IndicationPosition) this.f$0, (IndicationItem) this.f$1);
                break;
        }
    }
}
