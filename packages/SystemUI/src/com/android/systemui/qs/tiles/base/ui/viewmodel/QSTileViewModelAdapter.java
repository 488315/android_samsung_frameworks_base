package com.android.systemui.qs.tiles.base.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class QSTileViewModelAdapter implements QSTile, Dumpable {
    public static final Companion Companion = new Companion(null);
    public final CoroutineScope applicationScope;
    public final QSTile.AdapterState cachedState;
    public final CopyOnWriteArraySet callbacks = new CopyOnWriteArraySet();
    public final CopyOnWriteArraySet listeningClients = new CopyOnWriteArraySet();
    public final QSHost qsHost;
    public final QSTileViewModel qsTileViewModel;
    public StandaloneCoroutine stateJob;
    public final StandaloneCoroutine tileAdapterJob;
    public final CoroutineDispatcher uiBgDispatcher;

    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$1$1, reason: invalid class name and collision with other inner class name */
        final class C04201 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSTileViewModelAdapter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04201(QSTileViewModelAdapter qSTileViewModelAdapter, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSTileViewModelAdapter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04201(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04201) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlow stateFlowIsAvailable = this.this$0.qsTileViewModel.isAvailable();
                    final QSTileViewModelAdapter qSTileViewModelAdapter = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$1$1$invokeSuspend$$inlined$collectIndexed$1
                        public int index;

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int i2 = this.index;
                            this.index = i2 + 1;
                            if (i2 < 0) {
                                throw new ArithmeticException("Index overflow has happened");
                            }
                            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                            QSTileViewModelAdapter qSTileViewModelAdapter2 = qSTileViewModelAdapter;
                            if (!zBooleanValue && qSTileViewModelAdapter2.qsTileViewModel.getConfig().autoRemoveOnUnavailable) {
                                qSTileViewModelAdapter2.qsHost.removeTile(qSTileViewModelAdapter2.getTileSpec());
                            }
                            if (i2 <= 0 || !zBooleanValue) {
                                return Unit.INSTANCE;
                            }
                            throw new UnsupportedOperationException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Turning on tile is not supported now. Tile spec: ", qSTileViewModelAdapter2.getTileSpec()));
                        }
                    };
                    this.label = 1;
                    if (stateFlowIsAvailable.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSTileViewModelAdapter this$0;

            /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$1$2$1, reason: invalid class name and collision with other inner class name */
            final class C04211 extends SuspendLambda implements Function2 {
                /* synthetic */ Object L$0;
                int label;

                public C04211(Continuation continuation) {
                    super(2, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04211 c04211 = new C04211(continuation);
                    c04211.L$0 = obj;
                    return c04211;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04211) create((QSTileState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Boolean.valueOf(((QSTileState) this.L$0) == null);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(QSTileViewModelAdapter qSTileViewModelAdapter, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSTileViewModelAdapter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1 flowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1 = new FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1(this.this$0.qsTileViewModel.getState(), new C04211(null));
                    C04222 c04222 = new FlowCollector() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter.1.2.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1.collect(c04222, this) == coroutineSingletons) {
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
            AnonymousClass1 anonymousClass1 = QSTileViewModelAdapter.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new C04201(QSTileViewModelAdapter.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(QSTileViewModelAdapter.this, null), 3);
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static QSTile.AdapterState mapState(Context context, QSTileState qSTileState, QSTileConfig qSTileConfig) {
            QSTile.Icon drawableIcon;
            Drawable drawable;
            QSTile.AdapterState adapterState = new QSTile.AdapterState();
            adapterState.spec = qSTileConfig.tileSpec.getSpec();
            adapterState.label = qSTileState.label;
            QSTileState.ActivationState activationState = QSTileState.ActivationState.ACTIVE;
            QSTileState.ActivationState activationState2 = qSTileState.activationState;
            adapterState.value = activationState2 == activationState;
            adapterState.secondaryLabel = qSTileState.secondaryLabel;
            adapterState.handlesLongClick = qSTileState.supportedActions.contains(QSTileState.UserAction.LONG_CLICK);
            adapterState.handlesSecondaryClick = qSTileState.supportedActions.contains(QSTileState.UserAction.TOGGLE_CLICK);
            Icon icon = qSTileState.icon;
            if (icon instanceof Icon.Loaded) {
                Icon.Loaded loaded = (Icon.Loaded) icon;
                Integer num = loaded.res;
                drawableIcon = num == null ? new QSTileImpl.DrawableIcon(loaded.drawable) : new QSTileImpl.DrawableIconWithRes(loaded.drawable, num.intValue());
            } else if (icon instanceof Icon.Resource) {
                drawableIcon = QSTileImpl.ResourceIcon.get(((Icon.Resource) icon).res);
            } else {
                if (icon != null) {
                    throw new NoWhenBranchMatchedException();
                }
                drawableIcon = null;
            }
            adapterState.icon = drawableIcon;
            adapterState.state = activationState2.getLegacyState();
            adapterState.contentDescription = qSTileState.contentDescription;
            adapterState.stateDescription = qSTileState.stateDescription;
            adapterState.disabledByPolicy = qSTileState.enabledState == QSTileState.EnabledState.DISABLED;
            adapterState.expandedAccessibilityClassName = qSTileState.expandedAccessibilityClassName;
            adapterState.isTransient = false;
            QSTileState.SideViewIcon sideViewIcon = qSTileState.sideViewIcon;
            if (!(sideViewIcon instanceof QSTileState.SideViewIcon.Custom)) {
                if (sideViewIcon instanceof QSTileState.SideViewIcon.Chevron) {
                    adapterState.forceExpandIcon = true;
                    return adapterState;
                }
                if (!(sideViewIcon instanceof QSTileState.SideViewIcon.None)) {
                    throw new NoWhenBranchMatchedException();
                }
                adapterState.forceExpandIcon = false;
                return adapterState;
            }
            Icon icon2 = ((QSTileState.SideViewIcon.Custom) sideViewIcon).icon;
            if (icon2 instanceof Icon.Loaded) {
                drawable = ((Icon.Loaded) icon2).drawable;
            } else {
                if (!(icon2 instanceof Icon.Resource)) {
                    throw new NoWhenBranchMatchedException();
                }
                drawable = context.getDrawable(((Icon.Resource) icon2).res);
            }
            adapterState.sideViewCustomDrawable = drawable;
            return adapterState;
        }

        private Companion() {
        }
    }

    public interface Factory {
        QSTileViewModelAdapter create(QSTileViewModel qSTileViewModel);
    }

    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1, reason: invalid class name and case insensitive filesystem */
    final class C10121 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $client;
        int label;

        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ QSTileViewModelAdapter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(QSTileViewModelAdapter qSTileViewModelAdapter, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSTileViewModelAdapter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
                anonymousClass2.L$0 = obj;
                return anonymousClass2;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((QSTile.AdapterState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                QSTile.AdapterState adapterState = (QSTile.AdapterState) this.L$0;
                if (adapterState.copyTo(this.this$0.cachedState)) {
                    Iterator it = this.this$0.callbacks.iterator();
                    while (it.hasNext()) {
                        ((QSTile.Callback) it.next()).onStateChanged(adapterState);
                    }
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10121(Object obj, Continuation continuation) {
            super(2, continuation);
            this.$client = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSTileViewModelAdapter.this.new C10121(this.$client, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10121) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (QSTileViewModelAdapter.this.listeningClients.add(this.$client) && QSTileViewModelAdapter.this.listeningClients.size() == 1) {
                QSTileViewModelAdapter qSTileViewModelAdapter = QSTileViewModelAdapter.this;
                final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(qSTileViewModelAdapter.qsTileViewModel.getState());
                final QSTileViewModelAdapter qSTileViewModelAdapter2 = QSTileViewModelAdapter.this;
                qSTileViewModelAdapter.stateJob = FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1

                    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ QSTileViewModelAdapter this$0;

                        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, QSTileViewModelAdapter qSTileViewModelAdapter) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = qSTileViewModelAdapter;
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
                                QSTileViewModelAdapter.Companion companion = QSTileViewModelAdapter.Companion;
                                QSTileViewModelAdapter qSTileViewModelAdapter = this.this$0;
                                Context context = qSTileViewModelAdapter.qsHost.getContext();
                                QSTileConfig config = qSTileViewModelAdapter.qsTileViewModel.getConfig();
                                companion.getClass();
                                QSTile.AdapterState adapterStateMapState = QSTileViewModelAdapter.Companion.mapState(context, (QSTileState) obj, config);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(adapterStateMapState, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector, qSTileViewModelAdapter2), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, new AnonymousClass2(QSTileViewModelAdapter.this, null)), QSTileViewModelAdapter.this.uiBgDispatcher), QSTileViewModelAdapter.this.applicationScope);
            }
            return Unit.INSTANCE;
        }
    }

    public QSTileViewModelAdapter(CoroutineScope coroutineScope, QSHost qSHost, QSTileViewModel qSTileViewModel, CoroutineDispatcher coroutineDispatcher) {
        this.applicationScope = coroutineScope;
        this.qsHost = qSHost;
        this.qsTileViewModel = qSTileViewModel;
        this.uiBgDispatcher = coroutineDispatcher;
        this.tileAdapterJob = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
        userSwitch(qSHost.getUserId());
        this.cachedState = new QSTile.AdapterState();
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void addCallback(QSTile.Callback callback) {
        if (callback == null) {
            return;
        }
        this.callbacks.add(callback);
        getState().copyTo(this.cachedState);
        callback.onStateChanged(getState());
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void click(Expandable expandable) {
        if (isActionSupported(QSTileState.UserAction.CLICK)) {
            this.qsTileViewModel.onActionPerformed(new QSTileUserAction.Click(expandable));
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void destroy() {
        StandaloneCoroutine standaloneCoroutine = this.stateJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        StandaloneCoroutine standaloneCoroutine2 = this.tileAdapterJob;
        if (standaloneCoroutine2 != null) {
            standaloneCoroutine2.cancel(null);
        }
        this.qsTileViewModel.destroy();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        QSTileViewModel qSTileViewModel = this.qsTileViewModel;
        Dumpable dumpable = qSTileViewModel instanceof Dumpable ? (Dumpable) qSTileViewModel : null;
        if (dumpable != null) {
            dumpable.dump(printWriter, strArr);
        } else {
            QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, getTileSpec(), ": QSTileViewModel isn't dumpable");
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final int getCurrentTileUser() {
        return this.qsTileViewModel.getCurrentTileUser();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final TileDetailsViewModel getDetailsViewModel() {
        return this.qsTileViewModel.getTileDetailsViewModel();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final InstanceId getInstanceId() {
        return this.qsTileViewModel.getConfig().instanceId;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        CharSequence charSequence;
        QSTileViewModel qSTileViewModel = this.qsTileViewModel;
        QSTileUIConfig qSTileUIConfig = qSTileViewModel.getConfig().uiConfig;
        if (qSTileUIConfig instanceof QSTileUIConfig.Empty) {
            QSTileState qSTileState = (QSTileState) qSTileViewModel.getState().getValue();
            return (qSTileState == null || (charSequence = qSTileState.label) == null) ? "" : charSequence;
        }
        if (qSTileUIConfig instanceof QSTileUIConfig.Resource) {
            return this.qsHost.getContext().getString(((QSTileUIConfig.Resource) qSTileUIConfig).labelRes);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final String getTileSpec() {
        return this.qsTileViewModel.getConfig().tileSpec.getSpec();
    }

    public final boolean isActionSupported(QSTileState.UserAction userAction) {
        Set set;
        QSTileState qSTileState = (QSTileState) this.qsTileViewModel.getState().getValue();
        return (qSTileState == null || (set = qSTileState.supportedActions) == null || !set.contains(userAction)) ? false : true;
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return ((Boolean) this.qsTileViewModel.isAvailable().getValue()).booleanValue();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isDestroyed() {
        return !(this.tileAdapterJob != null ? r0.isActive() : false);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isListening() {
        return !this.listeningClients.isEmpty();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isTileReady() {
        return ((QSTileState) this.qsTileViewModel.getState().getValue()) != null;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void longClick(Expandable expandable) {
        if (isActionSupported(QSTileState.UserAction.LONG_CLICK)) {
            this.qsTileViewModel.onActionPerformed(new QSTileUserAction.LongClick(expandable));
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void refreshState() {
        this.qsTileViewModel.forceUpdate();
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void removeCallback(QSTile.Callback callback) {
        if (callback == null) {
            return;
        }
        this.callbacks.remove(callback);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void removeCallbacks() {
        this.callbacks.clear();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void secondaryClick(Expandable expandable) {
        if (isActionSupported(QSTileState.UserAction.TOGGLE_CLICK)) {
            this.qsTileViewModel.onActionPerformed(new QSTileUserAction.ToggleClick(expandable));
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void setListening(Object obj, boolean z) {
        StandaloneCoroutine standaloneCoroutine;
        if (obj == null) {
            return;
        }
        if (z) {
            BuildersKt.launch$default(this.applicationScope, this.uiBgDispatcher, null, new C10121(obj, null), 2);
            return;
        }
        this.listeningClients.remove(obj);
        if (!this.listeningClients.isEmpty() || (standaloneCoroutine = this.stateJob) == null) {
            return;
        }
        standaloneCoroutine.cancel(null);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void setTileSpec(String str) {
        throw new UnsupportedOperationException("Tile spec is immutable in new tiles");
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void userSwitch(int i) {
        this.qsTileViewModel.onUserChanged(UserHandle.of(i));
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final QSTile.AdapterState getState() {
        QSTileViewModel qSTileViewModel = this.qsTileViewModel;
        QSTileState qSTileState = (QSTileState) qSTileViewModel.getState().getValue();
        if (qSTileState == null) {
            return new QSTile.AdapterState();
        }
        Context context = this.qsHost.getContext();
        QSTileConfig config = qSTileViewModel.getConfig();
        Companion.getClass();
        return Companion.mapState(context, qSTileState, config);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void setDetailListening(boolean z) {
    }
}
