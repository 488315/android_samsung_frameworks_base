package com.android.systemui.keyguard.bouncer.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.bouncer.data.factory.BouncerMessageFactory;
import com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepository;
import com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl;
import com.android.systemui.keyguard.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.keyguard.bouncer.shared.model.Message;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerMessageInteractor {
    public final Flow bouncerMessage;
    public final BouncerMessageFactory factory;

    public BouncerMessageInteractor(BouncerMessageRepository bouncerMessageRepository, BouncerMessageFactory bouncerMessageFactory, UserRepository userRepository, CountDownTimerUtil countDownTimerUtil, FeatureFlags featureFlags) {
        this.factory = bouncerMessageFactory;
        Flags flags = Flags.INSTANCE;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new BouncerMessageModel(new Message("", null, null, null, false, 30, null), new Message("", null, null, null, false, 30, null)));
        BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = (BouncerMessageRepositoryImpl) bouncerMessageRepository;
        StateFlowImpl stateFlowImpl = bouncerMessageRepositoryImpl.primaryAuthMessage;
        Flow flow = bouncerMessageRepositoryImpl.biometricAuthMessage;
        StateFlowImpl stateFlowImpl2 = bouncerMessageRepositoryImpl.fingerprintAcquisitionMessage;
        StateFlowImpl stateFlowImpl3 = bouncerMessageRepositoryImpl.faceAcquisitionMessage;
        StateFlowImpl stateFlowImpl4 = bouncerMessageRepositoryImpl.customMessage;
        Flow flow2 = bouncerMessageRepositoryImpl.authFlagsMessage;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = bouncerMessageRepositoryImpl.biometricLockedOutMessage;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = ((UserRepositoryImpl) userRepository).selectedUserInfo;
        Iterator it = CollectionsKt__CollectionsKt.listOf(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, stateFlowImpl, flow, stateFlowImpl2, stateFlowImpl3, stateFlowImpl4, flow2, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new Flow() { // from class: com.android.systemui.keyguard.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1$2 */
            public final class C15502 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BouncerMessageInteractor this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1$2", m277f = "BouncerMessageInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C15502.this.emit(null, this);
                    }
                }

                public C15502(FlowCollector flowCollector, BouncerMessageInteractor bouncerMessageInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = bouncerMessageInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                BouncerMessageModel createFromPromptReason = this.this$0.factory.createFromPromptReason(14, ((UserInfo) obj).id);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(createFromPromptReason, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C15502(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }).iterator();
        if (!it.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it.next();
        while (it.hasNext()) {
            next = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1((Flow) next, (Flow) it.next(), new BouncerMessageInteractor$firstNonNullMessage$1(null));
        }
        this.bouncerMessage = FlowKt.distinctUntilChanged((Flow) next);
    }
}
