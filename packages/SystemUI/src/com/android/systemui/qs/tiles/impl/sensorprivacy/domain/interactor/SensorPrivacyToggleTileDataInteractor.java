package com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.sensorprivacy.domain.model.SensorPrivacyToggleTileModel;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes2.dex */
public final class SensorPrivacyToggleTileDataInteractor implements QSTileDataInteractor {
    public final CoroutineContext bgCoroutineContext;
    public final IndividualSensorPrivacyController privacyController;
    public final int sensorId;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor$availability$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = SensorPrivacyToggleTileDataInteractor.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0056, code lost:
        
            if (r1.emit(r7, r6) == r0) goto L18;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            FlowCollector flowCollector;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                SensorPrivacyToggleTileDataInteractor sensorPrivacyToggleTileDataInteractor = SensorPrivacyToggleTileDataInteractor.this;
                this.L$0 = flowCollector;
                this.label = 1;
                if (((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTileDataInteractor.privacyController).mSensorPrivacyManager.supportsSensorToggle(sensorPrivacyToggleTileDataInteractor.sensorId)) {
                    obj = BuildersKt.withContext(sensorPrivacyToggleTileDataInteractor.bgCoroutineContext, new SensorPrivacyToggleTileDataInteractor$isSensorDeviceConfigSet$2(sensorPrivacyToggleTileDataInteractor, null), this);
                } else {
                    obj = Boolean.FALSE;
                }
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            this.L$0 = null;
            this.label = 2;
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor$tileData$1, reason: invalid class name and case insensitive filesystem */
    final class C10201 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public C10201(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10201 c10201 = SensorPrivacyToggleTileDataInteractor.this.new C10201(continuation);
            c10201.L$0 = obj;
            return c10201;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10201) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor$tileData$1$callback$1, java.lang.Object] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final SensorPrivacyToggleTileDataInteractor sensorPrivacyToggleTileDataInteractor = SensorPrivacyToggleTileDataInteractor.this;
                final ?? r1 = new IndividualSensorPrivacyController.Callback() { // from class: com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor$tileData$1$callback$1
                    @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
                    public final void onSensorBlockedChanged(int i2, boolean z) {
                        if (i2 == sensorPrivacyToggleTileDataInteractor.sensorId) {
                            ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(SensorPrivacyToggleTileModel.m2934boximpl(z));
                        }
                    }
                };
                ((IndividualSensorPrivacyControllerImpl) SensorPrivacyToggleTileDataInteractor.this.privacyController).addCallback(r1);
                final SensorPrivacyToggleTileDataInteractor sensorPrivacyToggleTileDataInteractor2 = SensorPrivacyToggleTileDataInteractor.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor$tileData$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTileDataInteractor2.privacyController).removeCallback(r1);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor$tileData$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = SensorPrivacyToggleTileDataInteractor.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                SensorPrivacyToggleTileDataInteractor sensorPrivacyToggleTileDataInteractor = SensorPrivacyToggleTileDataInteractor.this;
                SensorPrivacyToggleTileModel sensorPrivacyToggleTileModelM2934boximpl = SensorPrivacyToggleTileModel.m2934boximpl(((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTileDataInteractor.privacyController).isSensorBlocked(sensorPrivacyToggleTileDataInteractor.sensorId));
                this.label = 1;
                if (flowCollector.emit(sensorPrivacyToggleTileModelM2934boximpl, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public SensorPrivacyToggleTileDataInteractor(CoroutineContext coroutineContext, IndividualSensorPrivacyController individualSensorPrivacyController, int i) {
        this.bgCoroutineContext = coroutineContext;
        this.privacyController = individualSensorPrivacyController;
        this.sensorId = i;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return FlowKt.flowOn(new SafeFlow(new AnonymousClass1(null)), this.bgCoroutineContext);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(null), FlowConflatedKt.conflatedCallbackFlow(new C10201(null)))), this.bgCoroutineContext);
    }
}
