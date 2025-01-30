package com.android.systemui.classifier;

import java.util.function.BinaryOperator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class HistoryTracker$$ExternalSyntheticLambda1 implements BinaryOperator {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                Double d = (Double) obj;
                Double d2 = (Double) obj2;
                return Double.valueOf((d2.doubleValue() * d.doubleValue()) / (((1.0d - d2.doubleValue()) * (1.0d - d.doubleValue())) + (d2.doubleValue() * d.doubleValue())));
            case 1:
                return Double.valueOf(Double.sum(((Double) obj).doubleValue(), ((Double) obj2).doubleValue()));
            default:
                return Double.valueOf(Double.sum(((Double) obj).doubleValue(), ((Double) obj2).doubleValue()));
        }
    }
}
