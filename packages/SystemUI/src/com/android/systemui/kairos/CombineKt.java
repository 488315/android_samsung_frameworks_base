package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.StateImplKt;
import com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda5;
import com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda6;
import java.util.Arrays;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CombineKt {
    public static final StateInit combine(final State state, final State state2, final Function3 function3) {
        return new StateInit(new Init("combine", new Function1() { // from class: com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos = State.this.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2 = state2.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                return StateImplKt.mapStateImpl(new StateImplKt$$ExternalSyntheticLambda6(StateImplKt.zipStateList("combine", 2, new Init(null, new Function1() { // from class: com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda3
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        NetworkScope networkScope = (NetworkScope) obj2;
                        return Arrays.asList(Init.this.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2.connect(networkScope));
                    }
                })), 2), "combine", "combine", new StateImplKt$$ExternalSyntheticLambda5(new CombineKt$$ExternalSyntheticLambda1(function3), 0));
            }
        }));
    }

    public static final StateInit combine(final State state, final State state2, final State state3, final Function4 function4) {
        return new StateInit(new Init("combine", new Function1() { // from class: com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos = State.this.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2 = state2.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos3 = state3.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                return StateImplKt.mapStateImpl(new StateImplKt$$ExternalSyntheticLambda6(StateImplKt.zipStateList("combine", 3, new Init(null, new Function1() { // from class: com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda9
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        NetworkScope networkScope = (NetworkScope) obj2;
                        return Arrays.asList(Init.this.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos3.connect(networkScope));
                    }
                })), 2), "combine", "combine", new StateImplKt$$ExternalSyntheticLambda5(new CombineKt$$ExternalSyntheticLambda4(function4), 1));
            }
        }));
    }

    public static final StateInit combine(final State state, final State state2, final State state3, final State state4, final Function5 function5) {
        return new StateInit(new Init("combine", new Function1() { // from class: com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos = State.this.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2 = state2.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos3 = state3.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos4 = state4.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                return StateImplKt.mapStateImpl(new StateImplKt$$ExternalSyntheticLambda6(StateImplKt.zipStateList("combine", 4, new Init(null, new Function1() { // from class: com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda18
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        NetworkScope networkScope = (NetworkScope) obj2;
                        return Arrays.asList(Init.this.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos3.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos4.connect(networkScope));
                    }
                })), 2), "combine", "combine", new StateImplKt$$ExternalSyntheticLambda5(new CombineKt$$ExternalSyntheticLambda11(function5), 3));
            }
        }));
    }
}
