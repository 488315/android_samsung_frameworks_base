package com.android.systemui.statusbar;

import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardSecIndicationPolicy$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IndicationEventType f$0;

    public /* synthetic */ KeyguardSecIndicationPolicy$$ExternalSyntheticLambda0(IndicationEventType indicationEventType, int i) {
        this.$r8$classId = i;
        this.f$0 = indicationEventType;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((IndicationItem) obj).mEventType != this.f$0) {
                    break;
                }
                break;
            case 1:
                IndicationItem indicationItem = (IndicationItem) obj;
                if (indicationItem.mEventType == this.f$0) {
                    if (indicationItem.mDurationTime == -1) {
                    }
                }
                break;
            default:
                IndicationItem indicationItem2 = (IndicationItem) obj;
                if (indicationItem2.mEventType == this.f$0) {
                    if (indicationItem2.mDurationTime == -1) {
                    }
                }
                break;
        }
        return false;
    }
}
