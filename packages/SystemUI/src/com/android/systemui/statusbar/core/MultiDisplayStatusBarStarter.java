package com.android.systemui.statusbar.core;

import android.view.View;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.display.data.repository.PerDisplayStoreImpl;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.data.repository.LightBarControllerStore;
import com.android.systemui.statusbar.data.repository.PrivacyDotWindowControllerStore;
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

/* loaded from: classes3.dex */
public final class MultiDisplayStatusBarStarter implements CoreStartable {
    public final CoroutineScope applicationScope;
    public final DisplayRepository displayRepository;
    public final LightBarControllerStore lightBarControllerStore;
    public final MultiDisplayStatusBarOrchestratorStore multiDisplayStatusBarOrchestratorStore;
    public final PrivacyDotWindowControllerStore privacyDotWindowControllerStore;
    public final StatusBarInitializerStore statusBarInitializerStore;

    /* renamed from: com.android.systemui.statusbar.core.MultiDisplayStatusBarStarter$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.statusbar.core.MultiDisplayStatusBarStarter$start$1$1, reason: invalid class name and collision with other inner class name */
        final class C04891 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ Object L$1;
            int label;

            public C04891(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                C04891 c04891 = new C04891((Continuation) obj3);
                c04891.L$0 = (Set) obj;
                c04891.L$1 = (Set) obj2;
                return c04891.invokeSuspend(Unit.INSTANCE);
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

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MultiDisplayStatusBarStarter.this.new AnonymousClass1(continuation);
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
                FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(MultiDisplayStatusBarStarter.this, null), FlowKt.pairwiseBy(((DisplayRepositoryImpl) MultiDisplayStatusBarStarter.this.displayRepository).displaysWithDecorationsRepositoryImpl.getDisplayIdsWithSystemDecorations(), new C04891(null)));
                final MultiDisplayStatusBarStarter multiDisplayStatusBarStarter = MultiDisplayStatusBarStarter.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.core.MultiDisplayStatusBarStarter.start.1.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        final PrivacyDotWindowController privacyDotWindowController;
                        Iterator it = ((Set) obj2).iterator();
                        while (it.hasNext()) {
                            int iIntValue = ((Number) it.next()).intValue();
                            MultiDisplayStatusBarStarter multiDisplayStatusBarStarter2 = multiDisplayStatusBarStarter;
                            if (((StatusBarOrchestrator) multiDisplayStatusBarStarter2.multiDisplayStatusBarOrchestratorStore.forDisplay(iIntValue)) != null) {
                                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                                int i2 = StatusBarConnectedDisplays.$r8$clinit;
                                throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
                            }
                            StatusBarInitializer statusBarInitializer = (StatusBarInitializer) multiDisplayStatusBarStarter2.statusBarInitializerStore.forDisplay(iIntValue);
                            if (statusBarInitializer != null) {
                                ((StatusBarInitializerImpl) statusBarInitializer).doStart();
                            }
                            if (iIntValue != 0 && (privacyDotWindowController = (PrivacyDotWindowController) ((PerDisplayStoreImpl) multiDisplayStatusBarStarter2.privacyDotWindowControllerStore).forDisplay(iIntValue)) != null) {
                                privacyDotWindowController.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotWindowController$start$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        PrivacyDotWindowController privacyDotWindowController2 = privacyDotWindowController;
                                        List providers = privacyDotWindowController2.dotFactory.getProviders();
                                        View viewInflate = privacyDotWindowController2.inflate(providers, 1, 0);
                                        View viewInflate2 = privacyDotWindowController2.inflate(providers, 1, 2);
                                        View viewInflate3 = privacyDotWindowController2.inflate(providers, 3, 0);
                                        View viewInflate4 = privacyDotWindowController2.inflate(providers, 3, 2);
                                        Iterator it2 = ArraysKt___ArraysKt.filterNotNull(new View[]{privacyDotWindowController2.addToWindow(viewInflate, PrivacyDotCorner.TopLeft), privacyDotWindowController2.addToWindow(viewInflate2, PrivacyDotCorner.TopRight), privacyDotWindowController2.addToWindow(viewInflate3, PrivacyDotCorner.BottomLeft), privacyDotWindowController2.addToWindow(viewInflate4, PrivacyDotCorner.BottomRight)}).iterator();
                                        while (it2.hasNext()) {
                                            privacyDotWindowController2.dotViews.add((View) it2.next());
                                        }
                                        privacyDotWindowController2.privacyDotViewController.initialize(viewInflate, viewInflate2, viewInflate3, viewInflate4);
                                    }
                                });
                            }
                            ((PerDisplayStoreImpl) multiDisplayStatusBarStarter2.lightBarControllerStore).forDisplay(iIntValue);
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

    public MultiDisplayStatusBarStarter(CoroutineScope coroutineScope, MultiDisplayStatusBarOrchestratorStore multiDisplayStatusBarOrchestratorStore, DisplayRepository displayRepository, StatusBarInitializerStore statusBarInitializerStore, PrivacyDotWindowControllerStore privacyDotWindowControllerStore, LightBarControllerStore lightBarControllerStore) {
        this.applicationScope = coroutineScope;
        this.multiDisplayStatusBarOrchestratorStore = multiDisplayStatusBarOrchestratorStore;
        this.displayRepository = displayRepository;
        this.statusBarInitializerStore = statusBarInitializerStore;
        this.privacyDotWindowControllerStore = privacyDotWindowControllerStore;
        this.lightBarControllerStore = lightBarControllerStore;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass1(null), 7);
    }
}
