package com.android.systemui.kairos;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BuildScope$DefaultImpls$$ExternalSyntheticLambda7 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function2 f$0;

    public /* synthetic */ BuildScope$DefaultImpls$$ExternalSyntheticLambda7(int i, Function2 function2) {
        this.$r8$classId = i;
        this.f$0 = function2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, final Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return BuildScopeKt.asyncEvent((BuildScope) obj, new BuildScope$mapAsyncLatest$1$1(this.f$0, obj2, null));
            case 1:
                this.f$0.invoke((EffectScope) obj, obj2);
                return Unit.INSTANCE;
            case 2:
                final Function2 function2 = this.f$0;
                final int i = 1;
                return new Function1() { // from class: com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda9
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj3) {
                        BuildScope buildScope = (BuildScope) obj3;
                        switch (i) {
                        }
                        return function2.invoke(buildScope, obj2);
                    }
                };
            default:
                final Function2 function22 = this.f$0;
                final int i2 = 0;
                return new Function1() { // from class: com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda9
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj3) {
                        BuildScope buildScope = (BuildScope) obj3;
                        switch (i2) {
                        }
                        return function22.invoke(buildScope, obj2);
                    }
                };
        }
    }
}
