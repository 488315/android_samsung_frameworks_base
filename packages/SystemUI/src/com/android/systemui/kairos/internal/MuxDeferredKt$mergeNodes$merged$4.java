package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.ArrayMapK;
import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.util.Maybe;
import com.android.systemui.kairos.util.These;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class MuxDeferredKt$mergeNodes$merged$4 implements Function3 {
    public static final MuxDeferredKt$mergeNodes$merged$4 INSTANCE = new MuxDeferredKt$mergeNodes$merged$4();

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Object objM2588boximpl;
        Object objM2588boximpl2;
        Object objM2588boximpl3;
        Object objM2588boximpl4;
        Object objM2588boximpl5;
        Object first;
        EvalScope evalScope = (EvalScope) obj;
        ((Number) obj3).intValue();
        ArrayMapK arrayMapK = (ArrayMapK) ((MapK) obj2);
        Object obj4 = arrayMapK.get(0);
        if (obj4 != null || arrayMapK.containsKey(0)) {
            Maybe.Companion.getClass();
            objM2588boximpl = Maybe.Present.m2588boximpl(obj4);
        } else {
            Maybe.Companion.getClass();
            objM2588boximpl = Maybe.Companion.absent;
        }
        if (objM2588boximpl instanceof Maybe.Present) {
            These these = (These) ((PullNode) ((Maybe.Present) objM2588boximpl).value).getPushEvent(evalScope);
            if (these instanceof These.Both) {
                Maybe.Companion companion = Maybe.Companion;
                Object obj5 = ((These.Both) these).first;
                companion.getClass();
                objM2588boximpl2 = Maybe.Present.m2588boximpl(obj5);
            } else if (these instanceof These.Second) {
                Maybe.Companion.getClass();
                objM2588boximpl2 = Maybe.Companion.absent;
            } else {
                if (!(these instanceof These.First)) {
                    throw new NoWhenBranchMatchedException();
                }
                Maybe.Companion companion2 = Maybe.Companion;
                Object obj6 = ((These.First) these).value;
                companion2.getClass();
                objM2588boximpl2 = Maybe.Present.m2588boximpl(obj6);
            }
        } else {
            if (!(objM2588boximpl instanceof Maybe.Absent)) {
                throw new NoWhenBranchMatchedException();
            }
            objM2588boximpl2 = Maybe.Absent.INSTANCE;
        }
        Object obj7 = arrayMapK.get(1);
        if (obj7 != null || arrayMapK.containsKey(1)) {
            Maybe.Companion.getClass();
            objM2588boximpl3 = Maybe.Present.m2588boximpl(obj7);
        } else {
            Maybe.Companion.getClass();
            objM2588boximpl3 = Maybe.Companion.absent;
        }
        if (objM2588boximpl3 instanceof Maybe.Present) {
            These these2 = (These) ((PullNode) ((Maybe.Present) objM2588boximpl3).value).getPushEvent(evalScope);
            if (these2 instanceof These.Both) {
                Maybe.Companion companion3 = Maybe.Companion;
                Object obj8 = ((These.Both) these2).second;
                companion3.getClass();
                objM2588boximpl4 = Maybe.Present.m2588boximpl(obj8);
            } else if (these2 instanceof These.Second) {
                Maybe.Companion companion4 = Maybe.Companion;
                Object obj9 = ((These.Second) these2).value;
                companion4.getClass();
                objM2588boximpl4 = Maybe.Present.m2588boximpl(obj9);
            } else {
                if (!(these2 instanceof These.First)) {
                    throw new NoWhenBranchMatchedException();
                }
                Maybe.Companion.getClass();
                objM2588boximpl4 = Maybe.Companion.absent;
            }
        } else {
            if (!(objM2588boximpl3 instanceof Maybe.Absent)) {
                throw new NoWhenBranchMatchedException();
            }
            objM2588boximpl4 = Maybe.Absent.INSTANCE;
        }
        if (objM2588boximpl2 instanceof Maybe.Present) {
            Maybe.Companion companion5 = Maybe.Companion;
            if (objM2588boximpl4 instanceof Maybe.Present) {
                These.Companion companion6 = These.Companion;
                Object obj10 = ((Maybe.Present) objM2588boximpl2).value;
                Object obj11 = ((Maybe.Present) objM2588boximpl4).value;
                companion6.getClass();
                first = new These.Both(obj10, obj11);
            } else {
                These.Companion companion7 = These.Companion;
                Object obj12 = ((Maybe.Present) objM2588boximpl2).value;
                companion7.getClass();
                first = new These.First(obj12);
            }
            companion5.getClass();
            objM2588boximpl5 = Maybe.Present.m2588boximpl(first);
        } else if (objM2588boximpl4 instanceof Maybe.Present) {
            Maybe.Companion companion8 = Maybe.Companion;
            These.Companion companion9 = These.Companion;
            Object obj13 = ((Maybe.Present) objM2588boximpl4).value;
            companion9.getClass();
            These.Second second = new These.Second(obj13);
            companion8.getClass();
            objM2588boximpl5 = Maybe.Present.m2588boximpl(second);
        } else {
            Maybe.Companion.getClass();
            objM2588boximpl5 = Maybe.Companion.absent;
        }
        if (objM2588boximpl5 instanceof Maybe.Present) {
            return (These) ((Maybe.Present) objM2588boximpl5).value;
        }
        if (objM2588boximpl5 instanceof Maybe.Absent) {
            throw new IllegalStateException("unexpected missing merge result");
        }
        throw new NoWhenBranchMatchedException();
    }
}
