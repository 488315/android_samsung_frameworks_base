package com.android.systemui.qs.ui.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.qs.QSContainerImpl;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.dagger.QSSceneComponent;
import com.android.systemui.qs.ui.adapter.CustomizerState;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.util.kotlin.FlowKt;
import java.io.PrintWriter;
import javax.inject.Provider;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSSceneAdapterImpl implements QSContainerController, QSSceneAdapter, Dumpable {
    public final StateFlowImpl _customizingState;
    public final StateFlowImpl _qsImpl;
    public final Function1 asyncLayoutInflaterFactory;
    public final SharedFlowImpl bottomNavBarSize;
    public final ConfigurationInteractor configurationInteractor;
    public final ReadonlyStateFlow customizerAnimationDuration;
    public final ReadonlyStateFlow customizerState;
    public final InterestingConfigChanges interestingChanges;
    public final ReadonlyStateFlow isCustomizerShowing;
    public final ReadonlyStateFlow isCustomizing;
    public final CoroutineDispatcher mainDispatcher;
    public final ReadonlyStateFlow qsImpl;
    public final Provider qsImplProvider;
    public final QSSceneComponent.Factory qsSceneComponentFactory;
    public final ReadonlyStateFlow qsView;
    public final StateFlowImpl state;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(1, AsyncLayoutInflater.class, "<init>", "<init>(Landroid/content/Context;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return new AsyncLayoutInflater((Context) obj);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSSceneAdapterImpl this$0;

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$2$1$2, reason: invalid class name and collision with other inner class name */
            final /* synthetic */ class C01632 extends AdaptedFunctionReference implements Function3 {
                public static final C01632 INSTANCE = new C01632();

                public C01632() {
                    super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    return new Pair((QSSceneAdapter.State) obj, (CustomizerState) obj2);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(QSSceneAdapterImpl qSSceneAdapterImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSSceneAdapterImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
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
                    QSSceneAdapterImpl qSSceneAdapterImpl = this.this$0;
                    Flow sample = FlowKt.sample(qSSceneAdapterImpl.state, qSSceneAdapterImpl._customizingState, C01632.INSTANCE);
                    final QSSceneAdapterImpl qSSceneAdapterImpl2 = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl.2.1.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Pair pair = (Pair) obj2;
                            QSSceneAdapter.State state = (QSSceneAdapter.State) pair.component1();
                            CustomizerState customizerState = (CustomizerState) pair.component2();
                            QSSceneAdapterImpl qSSceneAdapterImpl3 = QSSceneAdapterImpl.this;
                            QSImpl qSImpl = (QSImpl) qSSceneAdapterImpl3.qsImpl.$$delegate_0.getValue();
                            if (qSImpl != null) {
                                QSSceneAdapter.State.Companion.getClass();
                                if (!Intrinsics.areEqual(state, QSSceneAdapter.State.Companion.QS) && customizerState.isShowing()) {
                                    qSImpl.mQSCustomizerController.hide(false);
                                }
                                QSSceneAdapterImpl.access$applyState(qSSceneAdapterImpl3, qSImpl, state);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (sample.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$2$2, reason: invalid class name and collision with other inner class name */
        final class C01642 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSSceneAdapterImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01642(QSSceneAdapterImpl qSSceneAdapterImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSSceneAdapterImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01642(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01642) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final QSSceneAdapterImpl qSSceneAdapterImpl = this.this$0;
                    Flow flow = qSSceneAdapterImpl.configurationInteractor.configurationValues;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl.2.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            View view;
                            View view2;
                            Configuration configuration = (Configuration) obj2;
                            QSSceneAdapterImpl qSSceneAdapterImpl2 = QSSceneAdapterImpl.this;
                            InterestingConfigChanges interestingConfigChanges = qSSceneAdapterImpl2.interestingChanges;
                            Configuration configuration2 = interestingConfigChanges.mLastConfiguration;
                            boolean z = (interestingConfigChanges.mFlags & configuration2.updateFrom(Configuration.generateDelta(configuration2, configuration))) != 0;
                            ReadonlyStateFlow readonlyStateFlow = qSSceneAdapterImpl2.qsImpl;
                            if (z) {
                                QSImpl qSImpl = (QSImpl) readonlyStateFlow.$$delegate_0.getValue();
                                if (qSImpl != null && (view2 = qSImpl.mRootView) != null) {
                                    Context context = view2.getContext();
                                    qSSceneAdapterImpl2.getClass();
                                    Object withContext = BuildersKt.withContext(qSSceneAdapterImpl2.mainDispatcher, new QSSceneAdapterImpl$inflate$2(qSSceneAdapterImpl2, context, null), continuation);
                                    if (withContext != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                        withContext = Unit.INSTANCE;
                                    }
                                    if (withContext == CoroutineSingletons.COROUTINE_SUSPENDED) {
                                        return withContext;
                                    }
                                }
                            } else {
                                QSImpl qSImpl2 = (QSImpl) readonlyStateFlow.$$delegate_0.getValue();
                                if (qSImpl2 != null) {
                                    qSImpl2.onConfigurationChanged(configuration);
                                }
                                QSImpl qSImpl3 = (QSImpl) readonlyStateFlow.$$delegate_0.getValue();
                                if (qSImpl3 != null && (view = qSImpl3.mRootView) != null) {
                                    view.dispatchConfigurationChanged(configuration);
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSSceneAdapterImpl this$0;

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$2$3$2, reason: invalid class name and collision with other inner class name */
            final /* synthetic */ class C01652 extends AdaptedFunctionReference implements Function3 {
                public static final C01652 INSTANCE = new C01652();

                public C01652() {
                    super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    return new Pair(new Integer(((Number) obj).intValue()), (QSImpl) obj2);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(QSSceneAdapterImpl qSSceneAdapterImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSSceneAdapterImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    QSSceneAdapterImpl qSSceneAdapterImpl = this.this$0;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(qSSceneAdapterImpl.bottomNavBarSize, new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(qSSceneAdapterImpl.qsImpl), C01652.INSTANCE);
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new FlowCollector() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl.2.3.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Pair pair = (Pair) obj2;
                            QSImpl qSImpl = (QSImpl) pair.getSecond();
                            qSImpl.mQSCustomizerController.applyBottomNavBarSizeToRecyclerViewPadding(((Number) pair.getFirst()).intValue());
                            return Unit.INSTANCE;
                        }
                    }, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ ShadeInteractor $shadeInteractor;
            int label;
            final /* synthetic */ QSSceneAdapterImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(ShadeInteractor shadeInteractor, QSSceneAdapterImpl qSSceneAdapterImpl, Continuation continuation) {
                super(2, continuation);
                this.$shadeInteractor = shadeInteractor;
                this.this$0 = qSSceneAdapterImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$shadeInteractor, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlow shadeMode = ((ShadeInteractorImpl) this.$shadeInteractor).baseShadeInteractor.getShadeMode();
                    final QSSceneAdapterImpl qSSceneAdapterImpl = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl.2.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ShadeMode shadeMode2 = (ShadeMode) obj2;
                            QSImpl qSImpl = (QSImpl) QSSceneAdapterImpl.this.qsImpl.$$delegate_0.getValue();
                            if (qSImpl != null) {
                                qSImpl.setInSplitShade(Intrinsics.areEqual(shadeMode2, ShadeMode.Split.INSTANCE));
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (shadeMode.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ShadeInteractor shadeInteractor, Continuation continuation) {
            super(2, continuation);
            this.$shadeInteractor = shadeInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = QSSceneAdapterImpl.this.new AnonymousClass2(this.$shadeInteractor, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(QSSceneAdapterImpl.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new C01642(QSSceneAdapterImpl.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(QSSceneAdapterImpl.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$shadeInteractor, QSSceneAdapterImpl.this, null), 3);
            return Unit.INSTANCE;
        }
    }

    public QSSceneAdapterImpl(QSSceneComponent.Factory factory, Provider provider, ShadeInteractor shadeInteractor, DumpManager dumpManager, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, ConfigurationInteractor configurationInteractor, Function1 function1) {
        this.qsSceneComponentFactory = factory;
        this.qsImplProvider = provider;
        this.mainDispatcher = coroutineDispatcher;
        this.configurationInteractor = configurationInteractor;
        this.asyncLayoutInflaterFactory = function1;
        this.bottomNavBarSize = SharedFlowKt.MutableSharedFlow$default(0, 1, BufferOverflow.DROP_OLDEST, 1);
        this.state = StateFlowKt.MutableStateFlow(QSSceneAdapter.State.CLOSED.INSTANCE);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(CustomizerState.Hidden.INSTANCE);
        this._customizingState = MutableStateFlow;
        final ReadonlyStateFlow asStateFlow = kotlinx.coroutines.flow.FlowKt.asStateFlow(MutableStateFlow);
        Flow flow = new Flow() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.qs.ui.adapter.CustomizerState r5 = (com.android.systemui.qs.ui.adapter.CustomizerState) r5
                        boolean r5 = r5.isCustomizing()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        StateFlow stateFlow = asStateFlow.$$delegate_0;
        kotlinx.coroutines.flow.FlowKt.stateIn(flow, coroutineScope, WhileSubscribed$default, Boolean.valueOf(((CustomizerState) stateFlow.getValue()).isCustomizing()));
        this.isCustomizerShowing = kotlinx.coroutines.flow.FlowKt.stateIn(new Flow() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.qs.ui.adapter.CustomizerState r5 = (com.android.systemui.qs.ui.adapter.CustomizerState) r5
                        boolean r5 = r5.isShowing()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(((CustomizerState) stateFlow.getValue()).isShowing()));
        Flow flow2 = new Flow() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L55
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.qs.ui.adapter.CustomizerState r5 = (com.android.systemui.qs.ui.adapter.CustomizerState) r5
                        boolean r6 = r5 instanceof com.android.systemui.qs.ui.adapter.CustomizerState.Animating
                        if (r6 == 0) goto L3b
                        com.android.systemui.qs.ui.adapter.CustomizerState$Animating r5 = (com.android.systemui.qs.ui.adapter.CustomizerState.Animating) r5
                        goto L3c
                    L3b:
                        r5 = 0
                    L3c:
                        if (r5 == 0) goto L44
                        long r5 = r5.getAnimationDuration()
                        int r5 = (int) r5
                        goto L45
                    L44:
                        r5 = 0
                    L45:
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L55
                        return r1
                    L55:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed WhileSubscribed$default2 = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Object value = stateFlow.getValue();
        CustomizerState.Animating animating = value instanceof CustomizerState.Animating ? (CustomizerState.Animating) value : null;
        kotlinx.coroutines.flow.FlowKt.stateIn(flow2, coroutineScope, WhileSubscribed$default2, Integer.valueOf(animating != null ? (int) animating.getAnimationDuration() : 0));
        final StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._qsImpl = MutableStateFlow2;
        this.qsImpl = kotlinx.coroutines.flow.FlowKt.asStateFlow(MutableStateFlow2);
        Flow flow3 = new Flow() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$4

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.qs.QSImpl r5 = (com.android.systemui.qs.QSImpl) r5
                        if (r5 == 0) goto L39
                        android.view.View r5 = r5.mRootView
                        goto L3a
                    L39:
                        r5 = 0
                    L3a:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed WhileSubscribed$default3 = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        QSImpl qSImpl = (QSImpl) MutableStateFlow2.getValue();
        kotlinx.coroutines.flow.FlowKt.stateIn(flow3, coroutineScope, WhileSubscribed$default3, qSImpl != null ? qSImpl.mRootView : null);
        this.interestingChanges = new InterestingConfigChanges(-1073741820);
        dumpManager.registerDumpable(this);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(shadeInteractor, null), 3);
    }

    public static final void access$applyState(QSSceneAdapterImpl qSSceneAdapterImpl, QSImpl qSImpl, QSSceneAdapter.State state) {
        qSSceneAdapterImpl.getClass();
        qSImpl.setQsVisible(state.isVisible());
        qSImpl.setExpanded(state.isVisible() && state.getExpansion() > 0.0f);
        qSImpl.setListening(state.isVisible());
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        int i;
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("Last state: ", this.state.getValue(), printWriter);
        printWriter.println("CustomizerState: " + this._customizingState.getValue());
        QSImpl qSImpl = (QSImpl) this.qsImpl.$$delegate_0.getValue();
        printWriter.println("QQS height: " + (qSImpl != null ? qSImpl.mContainer.mHeader.getHeight() : 0));
        QSImpl qSImpl2 = (QSImpl) this.qsImpl.$$delegate_0.getValue();
        if (qSImpl2 != null) {
            QSContainerImpl qSContainerImpl = qSImpl2.mContainer;
            qSContainerImpl.mQSCustomizer.getClass();
            i = qSContainerImpl.mQSPanel.getMeasuredHeight();
        } else {
            i = 0;
        }
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("QS height: ", i, printWriter);
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setCustomizerAnimating(boolean z) {
        Object value;
        StateFlowImpl stateFlowImpl = this._customizingState;
        if (!(stateFlowImpl.getValue() instanceof CustomizerState.Animating) || z) {
            return;
        }
        do {
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, ((CustomizerState) value) instanceof CustomizerState.AnimatingIntoCustomizer ? CustomizerState.Showing.INSTANCE : CustomizerState.Hidden.INSTANCE));
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setCustomizerShowing(boolean z) {
        setCustomizerShowing(z, 0L);
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setCustomizerShowing(boolean z, long j) {
        StateFlowImpl stateFlowImpl;
        Object value;
        do {
            stateFlowImpl = this._customizingState;
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, z ? j > 0 ? new CustomizerState.AnimatingIntoCustomizer(j) : CustomizerState.Showing.INSTANCE : j > 0 ? new CustomizerState.AnimatingOutOfCustomizer(j) : CustomizerState.Hidden.INSTANCE));
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setDetailShowing(boolean z) {
    }

    public QSSceneAdapterImpl(QSSceneComponent.Factory factory, Provider provider, ShadeInteractor shadeInteractor, DumpManager dumpManager, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, ConfigurationInteractor configurationInteractor) {
        this(factory, provider, shadeInteractor, dumpManager, coroutineDispatcher, coroutineScope, configurationInteractor, AnonymousClass1.INSTANCE);
    }
}
