package com.android.systemui.window.ui.viewmodel;

import android.os.Build;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.transitions.GlanceableHubTransition;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.window.domain.interactor.WindowRootViewBlurInteractor;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes3.dex */
public final class WindowRootViewModel {
    public static final Companion Companion = new Companion(null);
    public static final boolean isLoggable = Build.isDebuggable();
    public final ChannelLimitedFlowMerge _blurRadius;
    public final WindowRootViewBlurInteractor blurInteractor;
    public final ChannelFlowTransformLatest blurRadius;
    public final EmptyList bouncerBlurRadiusFlows;
    public final EmptyList glanceableHubBlurRadiusFlows;
    public final ChannelFlowTransformLatest isBlurOpaque;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isPersistentEarlyWakeupRequired;
    public final KeyguardInteractor keyguardInteractor;
    public final ShadeInteractor shadeInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
    }

    public WindowRootViewModel(Set<PrimaryBouncerTransition> set, Set<GlanceableHubTransition> set2, WindowRootViewBlurInteractor windowRootViewBlurInteractor, KeyguardInteractor keyguardInteractor, ShadeInteractor shadeInteractor) {
        this.blurInteractor = windowRootViewBlurInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.shadeInteractor = shadeInteractor;
        EmptyList emptyList = EmptyList.INSTANCE;
        this.bouncerBlurRadiusFlows = emptyList;
        this.glanceableHubBlurRadiusFlows = emptyList;
        SpreadBuilder spreadBuilder = new SpreadBuilder(3);
        spreadBuilder.addSpread(emptyList.toArray(new Flow[0]));
        spreadBuilder.addSpread(emptyList.toArray(new Flow[0]));
        final ReadonlyStateFlow readonlyStateFlow = windowRootViewBlurInteractor.blurRadiusRequestedByShade;
        Flow flow = new Flow() { // from class: com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Float f = new Float(((Number) obj).intValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Companion.getClass();
        spreadBuilder.add(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flow, new WindowRootViewModel$Companion$logIfPossible$1("ShadeBlur", null)));
        this._blurRadius = FlowKt.merge(CollectionsKt__CollectionsKt.listOf(spreadBuilder.list.toArray(new Flow[spreadBuilder.list.size()])));
        WindowRootViewModel$special$$inlined$flatMapLatest$1 windowRootViewModel$special$$inlined$flatMapLatest$1 = new WindowRootViewModel$special$$inlined$flatMapLatest$1(null, this);
        ReadonlyStateFlow readonlyStateFlow2 = windowRootViewBlurInteractor.isBlurCurrentlySupported;
        this.blurRadius = FlowKt.transformLatest(readonlyStateFlow2, windowRootViewModel$special$$inlined$flatMapLatest$1);
        this.isPersistentEarlyWakeupRequired = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.transformLatest(readonlyStateFlow2, new WindowRootViewModel$special$$inlined$flatMapLatest$2(null, this))), new WindowRootViewModel$Companion$logIfPossible$1("isPersistentEarlyWakeupRequired", null));
        this.isBlurOpaque = FlowKt.transformLatest(readonlyStateFlow2, new WindowRootViewModel$special$$inlined$flatMapLatest$3(null, this));
    }
}
