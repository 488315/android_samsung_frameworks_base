package com.android.systemui.classifier;

import java.util.function.BinaryOperator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class HistoryTracker$$ExternalSyntheticLambda1 implements BinaryOperator {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        Double d = (Double) obj;
        switch (this.$r8$classId) {
            case 0:
                return Double.valueOf(Double.sum(d.doubleValue(), ((Double) obj2).doubleValue()));
            default:
                Double d2 = (Double) obj2;
                int i = HistoryTracker.$r8$clinit;
                return Double.valueOf((d2.doubleValue() * d.doubleValue()) / (((1.0d - d2.doubleValue()) * (1.0d - d.doubleValue())) + (d2.doubleValue() * d.doubleValue())));
        }
    }
}
