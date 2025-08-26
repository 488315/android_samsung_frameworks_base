package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.touchpad.tutorial.ui.gesture.RecentAppsGestureRecognizer;
import com.android.systemui.touchpad.tutorial.ui.gesture.VelocityTracker;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class RecentAppsGestureRecognizerProvider implements GestureRecognizerProvider {
    public final RecentAppsGestureRecognizerProvider$special$$inlined$map$1 recognizer;
    public final VelocityTracker velocityTracker;

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.touchpad.tutorial.ui.viewmodel.RecentAppsGestureRecognizerProvider$special$$inlined$map$1] */
    public RecentAppsGestureRecognizerProvider(TouchpadGestureResources touchpadGestureResources, VelocityTracker velocityTracker) {
        this.velocityTracker = velocityTracker;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(touchpadGestureResources.distanceThreshold(), FlowKt.distinctUntilChanged(new TouchpadGestureResources$velocityThreshold$$inlined$map$1(((ConfigurationInteractorImpl) touchpadGestureResources.configurationInteractor).onAnyConfigurationChange, touchpadGestureResources, R.dimen.touchpad_recent_apps_gesture_velocity_threshold)), new RecentAppsGestureRecognizerProvider$recognizer$1(null));
        this.recognizer = new Flow() { // from class: com.android.systemui.touchpad.tutorial.ui.viewmodel.RecentAppsGestureRecognizerProvider$special$$inlined$map$1

            /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.RecentAppsGestureRecognizerProvider$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ RecentAppsGestureRecognizerProvider this$0;

                /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.RecentAppsGestureRecognizerProvider$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, RecentAppsGestureRecognizerProvider recentAppsGestureRecognizerProvider) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = recentAppsGestureRecognizerProvider;
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
                        Pair pair = (Pair) obj;
                        RecentAppsGestureRecognizer recentAppsGestureRecognizer = new RecentAppsGestureRecognizer(((Number) pair.component1()).intValue(), ((Number) pair.component2()).floatValue(), this.this$0.velocityTracker);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(recentAppsGestureRecognizer, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerProvider
    public final Flow getRecognizer() {
        return this.recognizer;
    }
}
