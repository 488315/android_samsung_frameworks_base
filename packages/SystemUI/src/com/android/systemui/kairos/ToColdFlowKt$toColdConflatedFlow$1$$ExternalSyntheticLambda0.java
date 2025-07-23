package com.android.systemui.kairos;

import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ToColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ ProducerScope f$1;

    public /* synthetic */ ToColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda0(Object obj, ProducerScope producerScope, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = producerScope;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        BuildScope buildScope = (BuildScope) obj;
        switch (this.$r8$classId) {
            case 0:
                final ProducerScope producerScope = this.f$1;
                final int i = 0;
                return BuildScope.DefaultImpls.observe$default(buildScope, (Events) this.f$0, new Function2() { // from class: com.android.systemui.kairos.ToColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        switch (i) {
                            case 0:
                                ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(obj3);
                                break;
                            default:
                                ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(obj3);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                }, 1);
            default:
                final ProducerScope producerScope2 = this.f$1;
                final int i2 = 1;
                return ((BuildScopeImpl) buildScope).observe((State) this.f$0, new Function2() { // from class: com.android.systemui.kairos.ToColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        switch (i2) {
                            case 0:
                                ((ChannelCoroutine) producerScope2).mo3456trySendJP2dKIU(obj3);
                                break;
                            default:
                                ((ChannelCoroutine) producerScope2).mo3456trySendJP2dKIU(obj3);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                });
        }
    }
}
