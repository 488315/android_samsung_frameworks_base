package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.ArrayMapK;
import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.util.Maybe;
import com.android.systemui.kairos.util.These;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MuxDeferredKt$mergeNodes$merged$4 implements Function3 {
    public static final MuxDeferredKt$mergeNodes$merged$4 INSTANCE = new MuxDeferredKt$mergeNodes$merged$4();

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Object m2573boximpl;
        Object obj4;
        Object m2573boximpl2;
        Object obj5;
        Object obj6;
        Object first;
        EvalScope evalScope = (EvalScope) obj;
        ((Number) obj3).intValue();
        ArrayMapK arrayMapK = (ArrayMapK) ((MapK) obj2);
        Object obj7 = arrayMapK.get(0);
        if (obj7 != null || arrayMapK.containsKey(0)) {
            Maybe.Companion.getClass();
            m2573boximpl = Maybe.Present.m2573boximpl(obj7);
        } else {
            Maybe.Companion.getClass();
            m2573boximpl = Maybe.Companion.absent;
        }
        if (m2573boximpl instanceof Maybe.Present) {
            These these = (These) ((PullNode) ((Maybe.Present) m2573boximpl).value).getPushEvent(evalScope);
            if (these instanceof These.Both) {
                Maybe.Companion companion = Maybe.Companion;
                Object obj8 = ((These.Both) these).first;
                companion.getClass();
                obj4 = Maybe.Present.m2573boximpl(obj8);
            } else if (these instanceof These.Second) {
                Maybe.Companion.getClass();
                obj4 = Maybe.Companion.absent;
            } else {
                if (!(these instanceof These.First)) {
                    throw new NoWhenBranchMatchedException();
                }
                Maybe.Companion companion2 = Maybe.Companion;
                Object obj9 = ((These.First) these).value;
                companion2.getClass();
                obj4 = Maybe.Present.m2573boximpl(obj9);
            }
        } else {
            if (!(m2573boximpl instanceof Maybe.Absent)) {
                throw new NoWhenBranchMatchedException();
            }
            obj4 = Maybe.Absent.INSTANCE;
        }
        Object obj10 = arrayMapK.get(1);
        if (obj10 != null || arrayMapK.containsKey(1)) {
            Maybe.Companion.getClass();
            m2573boximpl2 = Maybe.Present.m2573boximpl(obj10);
        } else {
            Maybe.Companion.getClass();
            m2573boximpl2 = Maybe.Companion.absent;
        }
        if (m2573boximpl2 instanceof Maybe.Present) {
            These these2 = (These) ((PullNode) ((Maybe.Present) m2573boximpl2).value).getPushEvent(evalScope);
            if (these2 instanceof These.Both) {
                Maybe.Companion companion3 = Maybe.Companion;
                Object obj11 = ((These.Both) these2).second;
                companion3.getClass();
                obj5 = Maybe.Present.m2573boximpl(obj11);
            } else if (these2 instanceof These.Second) {
                Maybe.Companion companion4 = Maybe.Companion;
                Object obj12 = ((These.Second) these2).value;
                companion4.getClass();
                obj5 = Maybe.Present.m2573boximpl(obj12);
            } else {
                if (!(these2 instanceof These.First)) {
                    throw new NoWhenBranchMatchedException();
                }
                Maybe.Companion.getClass();
                obj5 = Maybe.Companion.absent;
            }
        } else {
            if (!(m2573boximpl2 instanceof Maybe.Absent)) {
                throw new NoWhenBranchMatchedException();
            }
            obj5 = Maybe.Absent.INSTANCE;
        }
        if (obj4 instanceof Maybe.Present) {
            Maybe.Companion companion5 = Maybe.Companion;
            if (obj5 instanceof Maybe.Present) {
                These.Companion companion6 = These.Companion;
                Object obj13 = ((Maybe.Present) obj4).value;
                Object obj14 = ((Maybe.Present) obj5).value;
                companion6.getClass();
                first = new These.Both(obj13, obj14);
            } else {
                These.Companion companion7 = These.Companion;
                Object obj15 = ((Maybe.Present) obj4).value;
                companion7.getClass();
                first = new These.First(obj15);
            }
            companion5.getClass();
            obj6 = Maybe.Present.m2573boximpl(first);
        } else if (obj5 instanceof Maybe.Present) {
            Maybe.Companion companion8 = Maybe.Companion;
            These.Companion companion9 = These.Companion;
            Object obj16 = ((Maybe.Present) obj5).value;
            companion9.getClass();
            These.Second second = new These.Second(obj16);
            companion8.getClass();
            obj6 = Maybe.Present.m2573boximpl(second);
        } else {
            Maybe.Companion.getClass();
            obj6 = Maybe.Companion.absent;
        }
        if (obj6 instanceof Maybe.Present) {
            return (These) ((Maybe.Present) obj6).value;
        }
        if (obj6 instanceof Maybe.Absent) {
            throw new IllegalStateException("unexpected missing merge result");
        }
        throw new NoWhenBranchMatchedException();
    }
}
