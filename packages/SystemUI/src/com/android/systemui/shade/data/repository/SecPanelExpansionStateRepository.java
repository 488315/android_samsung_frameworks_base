package com.android.systemui.shade.data.repository;

import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class SecPanelExpansionStateRepository {
    public final StateFlowImpl _lockscreenShadeFraction;
    public final StateFlowImpl _screenOffState;
    public final StateFlowImpl _shadeFraction;
    public final StateFlowImpl _statusBarState;
    public int currentPanelState;
    public final ReadonlyStateFlow lockscreenShadeFraction;
    public final ReadonlyStateFlow panelState;
    public final ReadonlyStateFlow screenOffState;
    public final ReadonlyStateFlow shadeFraction;
    public final ReadonlyStateFlow statusBarState;

    /* renamed from: com.android.systemui.shade.data.repository.SecPanelExpansionStateRepository$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $notify;
        int label;

        /* renamed from: com.android.systemui.shade.data.repository.SecPanelExpansionStateRepository$1$1, reason: invalid class name and collision with other inner class name */
        final class C04791 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function1 $notify;
            /* synthetic */ int I$0;
            int label;
            final /* synthetic */ SecPanelExpansionStateRepository this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04791(SecPanelExpansionStateRepository secPanelExpansionStateRepository, Function1 function1, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secPanelExpansionStateRepository;
                this.$notify = function1;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C04791 c04791 = new C04791(this.this$0, this.$notify, continuation);
                c04791.I$0 = ((Number) obj).intValue();
                return c04791;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04791) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                int i = this.I$0;
                int i2 = this.this$0.currentPanelState;
                MediaSessions$H$$ExternalSyntheticOutline0.m("NOTIFY !!! ", i2 != 0 ? i2 != 1 ? "OPEN" : "OPENING" : "CLOSED", " -> ", i != 0 ? i != 1 ? "OPEN" : "OPENING" : "CLOSED", "SecPanelExpansionStateRepository");
                this.$notify.mo781invoke(new Integer(i));
                this.this$0.currentPanelState = i;
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$notify = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SecPanelExpansionStateRepository.this.new AnonymousClass1(this.$notify, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SecPanelExpansionStateRepository secPanelExpansionStateRepository = SecPanelExpansionStateRepository.this;
                ReadonlyStateFlow readonlyStateFlow = secPanelExpansionStateRepository.panelState;
                C04791 c04791 = new C04791(secPanelExpansionStateRepository, this.$notify, null);
                this.label = 1;
                if (FlowKt.collectLatest(readonlyStateFlow, c04791, this) == coroutineSingletons) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SecPanelExpansionStateRepository(CoroutineScope coroutineScope, Function1 function1) {
        Float fValueOf = Float.valueOf(0.0f);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(fValueOf);
        this._lockscreenShadeFraction = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.lockscreenShadeFraction = readonlyStateFlowAsStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._screenOffState = stateFlowImplMutableStateFlow2;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow2 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        this.screenOffState = readonlyStateFlowAsStateFlow2;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(fValueOf);
        this._shadeFraction = stateFlowImplMutableStateFlow3;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow3 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.shadeFraction = readonlyStateFlowAsStateFlow3;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(0);
        this._statusBarState = stateFlowImplMutableStateFlow4;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow4 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        this.statusBarState = readonlyStateFlowAsStateFlow4;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlowAsStateFlow, readonlyStateFlowAsStateFlow2, readonlyStateFlowAsStateFlow3, readonlyStateFlowAsStateFlow4, new SecPanelExpansionStateRepository$panelState$1(null)));
        SharingStarted.Companion.getClass();
        this.panelState = FlowKt.stateIn(flowDistinctUntilChanged, coroutineScope, SharingStarted.Companion.Eagerly, 0);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(function1, null), 3);
    }
}
