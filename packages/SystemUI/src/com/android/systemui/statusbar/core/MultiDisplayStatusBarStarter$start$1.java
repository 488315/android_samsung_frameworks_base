package com.android.systemui.statusbar.core;

import android.view.View;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.display.data.repository.PerDisplayStoreImpl;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.events.PrivacyDotWindowController;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MultiDisplayStatusBarStarter$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MultiDisplayStatusBarStarter this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.core.MultiDisplayStatusBarStarter$start$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (Set) obj;
            anonymousClass1.L$1 = (Set) obj2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return SetsKt___SetsKt.minus((Set) this.L$1, (Iterable) this.L$0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.core.MultiDisplayStatusBarStarter$start$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ MultiDisplayStatusBarStarter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(MultiDisplayStatusBarStarter multiDisplayStatusBarStarter, Continuation continuation) {
            super(2, continuation);
            this.this$0 = multiDisplayStatusBarStarter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
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
                Object value = ((DisplayRepositoryImpl) this.this$0.displayRepository).displaysWithDecorationsRepositoryImpl.getDisplayIdsWithSystemDecorations().getValue();
                this.label = 1;
                if (flowCollector.emit(value, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiDisplayStatusBarStarter$start$1(MultiDisplayStatusBarStarter multiDisplayStatusBarStarter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = multiDisplayStatusBarStarter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MultiDisplayStatusBarStarter$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MultiDisplayStatusBarStarter$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(this.this$0, null), FlowKt.pairwiseBy(((DisplayRepositoryImpl) this.this$0.displayRepository).displaysWithDecorationsRepositoryImpl.getDisplayIdsWithSystemDecorations(), new AnonymousClass1(null)));
            final MultiDisplayStatusBarStarter multiDisplayStatusBarStarter = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.core.MultiDisplayStatusBarStarter$start$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    final PrivacyDotWindowController privacyDotWindowController;
                    Iterator it = ((Set) obj2).iterator();
                    while (it.hasNext()) {
                        int intValue = ((Number) it.next()).intValue();
                        MultiDisplayStatusBarStarter multiDisplayStatusBarStarter2 = MultiDisplayStatusBarStarter.this;
                        if (((StatusBarOrchestrator) multiDisplayStatusBarStarter2.multiDisplayStatusBarOrchestratorStore.forDisplay(intValue)) != null) {
                            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                            int i2 = StatusBarConnectedDisplays.$r8$clinit;
                            throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
                        }
                        StatusBarInitializer statusBarInitializer = (StatusBarInitializer) multiDisplayStatusBarStarter2.statusBarInitializerStore.forDisplay(intValue);
                        if (statusBarInitializer != null) {
                            ((StatusBarInitializerImpl) statusBarInitializer).doStart();
                        }
                        if (intValue != 0 && (privacyDotWindowController = (PrivacyDotWindowController) ((PerDisplayStoreImpl) multiDisplayStatusBarStarter2.privacyDotWindowControllerStore).forDisplay(intValue)) != null) {
                            privacyDotWindowController.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotWindowController$start$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PrivacyDotWindowController privacyDotWindowController2 = PrivacyDotWindowController.this;
                                    List providers = privacyDotWindowController2.dotFactory.getProviders();
                                    View inflate = privacyDotWindowController2.inflate(providers, 1, 0);
                                    View inflate2 = privacyDotWindowController2.inflate(providers, 1, 2);
                                    View inflate3 = privacyDotWindowController2.inflate(providers, 3, 0);
                                    View inflate4 = privacyDotWindowController2.inflate(providers, 3, 2);
                                    Iterator it2 = ArraysKt___ArraysKt.filterNotNull(new View[]{privacyDotWindowController2.addToWindow(inflate, PrivacyDotCorner.TopLeft), privacyDotWindowController2.addToWindow(inflate2, PrivacyDotCorner.TopRight), privacyDotWindowController2.addToWindow(inflate3, PrivacyDotCorner.BottomLeft), privacyDotWindowController2.addToWindow(inflate4, PrivacyDotCorner.BottomRight)}).iterator();
                                    while (it2.hasNext()) {
                                        privacyDotWindowController2.dotViews.add((View) it2.next());
                                    }
                                    privacyDotWindowController2.privacyDotViewController.initialize(inflate, inflate2, inflate3, inflate4);
                                }
                            });
                        }
                        ((PerDisplayStoreImpl) multiDisplayStatusBarStarter2.lightBarControllerStore).forDisplay(intValue);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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
