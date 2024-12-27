package com.android.systemui.qs.tiles.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.internal.logging.InstanceId;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

public final class QSTileViewModelAdapter implements QSTile, Dumpable {
    public static final Companion Companion = new Companion(null);
    public final CoroutineScope applicationScope;
    public final Collection callbacks = new LinkedHashSet();
    public final Collection listeningClients = new LinkedHashSet();
    public final QSHost qsHost;
    public final QSTileViewModel qsTileViewModel;
    public Job stateJob;
    public final Job tileAdapterJob;

    /* renamed from: com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$1$1, reason: invalid class name and collision with other inner class name */
        final class C01621 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSTileViewModelAdapter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01621(QSTileViewModelAdapter qSTileViewModelAdapter, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSTileViewModelAdapter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01621(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01621) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlow isAvailable = this.this$0.qsTileViewModel.isAvailable();
                    final QSTileViewModelAdapter qSTileViewModelAdapter = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$1$1$invokeSuspend$$inlined$collectIndexed$1
                        public int index;

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int i2 = this.index;
                            this.index = i2 + 1;
                            if (i2 < 0) {
                                throw new ArithmeticException("Index overflow has happened");
                            }
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            if (!booleanValue) {
                                QSTileViewModelAdapter qSTileViewModelAdapter2 = QSTileViewModelAdapter.this;
                                qSTileViewModelAdapter2.qsHost.removeTile(qSTileViewModelAdapter2.getTileSpec());
                            }
                            if (i2 <= 0 || !booleanValue) {
                                return Unit.INSTANCE;
                            }
                            throw new UnsupportedOperationException("Turning on tile is not supported now");
                        }
                    };
                    this.label = 1;
                    if (isAvailable.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSTileViewModelAdapter this$0;

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
                    SharedFlow state = this.this$0.qsTileViewModel.getState();
                    this.label = 1;
                    if (FlowKt.first(state, this) == coroutineSingletons) {
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
            BuildersKt.launch$default(coroutineScope, null, null, new C01621(QSTileViewModelAdapter.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(QSTileViewModelAdapter.this, null), 3);
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        private Companion() {
        }

        public static QSTile.AdapterState mapState(Context context, final QSTileState qSTileState, QSTileConfig qSTileConfig) {
            Drawable drawable;
            QSTile.AdapterState adapterState = new QSTile.AdapterState();
            adapterState.spec = qSTileConfig.tileSpec.getSpec();
            adapterState.label = qSTileState.label;
            QSTileState.ActivationState activationState = QSTileState.ActivationState.ACTIVE;
            QSTileState.ActivationState activationState2 = qSTileState.activationState;
            adapterState.value = activationState2 == activationState;
            adapterState.secondaryLabel = qSTileState.secondaryLabel;
            adapterState.handlesLongClick = qSTileState.supportedActions.contains(QSTileState.UserAction.LONG_CLICK);
            adapterState.iconSupplier = new Supplier() { // from class: com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$Companion$mapState$1$1
                @Override // java.util.function.Supplier
                public final Object get() {
                    Icon icon = (Icon) QSTileState.this.icon.invoke();
                    if (icon instanceof Icon.Loaded) {
                        Integer num = QSTileState.this.iconRes;
                        return num == null ? new QSTileImpl.DrawableIcon(((Icon.Loaded) icon).drawable) : new QSTileImpl.DrawableIconWithRes(((Icon.Loaded) icon).drawable, num.intValue());
                    }
                    if (icon instanceof Icon.Resource) {
                        return QSTileImpl.ResourceIcon.get(((Icon.Resource) icon).res);
                    }
                    if (icon == null) {
                        return null;
                    }
                    throw new NoWhenBranchMatchedException();
                }
            };
            adapterState.state = activationState2.getLegacyState();
            adapterState.contentDescription = qSTileState.contentDescription;
            adapterState.stateDescription = qSTileState.stateDescription;
            adapterState.disabledByPolicy = qSTileState.enabledState == QSTileState.EnabledState.DISABLED;
            adapterState.expandedAccessibilityClassName = qSTileState.expandedAccessibilityClassName;
            adapterState.isTransient = false;
            QSTileState.SideViewIcon sideViewIcon = qSTileState.sideViewIcon;
            if (sideViewIcon instanceof QSTileState.SideViewIcon.Custom) {
                Icon icon = ((QSTileState.SideViewIcon.Custom) sideViewIcon).icon;
                if (icon instanceof Icon.Loaded) {
                    drawable = ((Icon.Loaded) icon).drawable;
                } else {
                    if (!(icon instanceof Icon.Resource)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    drawable = context.getDrawable(((Icon.Resource) icon).res);
                }
                adapterState.sideViewCustomDrawable = drawable;
            } else if (sideViewIcon instanceof QSTileState.SideViewIcon.Chevron) {
                adapterState.forceExpandIcon = true;
            } else if (sideViewIcon instanceof QSTileState.SideViewIcon.None) {
                adapterState.forceExpandIcon = false;
            }
            return adapterState;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public interface Factory {
        QSTileViewModelAdapter create(QSTileViewModel qSTileViewModel);
    }

    public QSTileViewModelAdapter(CoroutineScope coroutineScope, QSHost qSHost, QSTileViewModel qSTileViewModel) {
        this.applicationScope = coroutineScope;
        this.qsHost = qSHost;
        this.qsTileViewModel = qSTileViewModel;
        this.tileAdapterJob = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
        userSwitch(qSHost.getUserId());
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void addCallback(QSTile.Callback callback) {
        if (callback == null) {
            return;
        }
        synchronized (this.callbacks) {
            this.callbacks.add(callback);
            QSTile.State state = getState();
            if (state != null) {
                callback.onStateChanged(state);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void click(Expandable expandable) {
        if (isActionSupported(QSTileState.UserAction.CLICK)) {
            this.qsTileViewModel.onActionPerformed(new QSTileUserAction.Click(expandable));
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void destroy() {
        Job job = this.stateJob;
        if (job != null) {
            job.cancel(null);
        }
        Job job2 = this.tileAdapterJob;
        if (job2 != null) {
            job2.cancel(null);
        }
        this.qsTileViewModel.destroy();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        QSTileViewModel qSTileViewModel = this.qsTileViewModel;
        Unit unit = null;
        Dumpable dumpable = qSTileViewModel instanceof Dumpable ? (Dumpable) qSTileViewModel : null;
        if (dumpable != null) {
            dumpable.dump(printWriter, strArr);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            printWriter.println(getTileSpec() + ": QSTileViewModel isn't dumpable");
        }
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
    public final QSTile.State getState() {
        QSTileViewModel qSTileViewModel = this.qsTileViewModel;
        QSTileState qSTileState = (QSTileState) CollectionsKt___CollectionsKt.lastOrNull(qSTileViewModel.getState().getReplayCache());
        if (qSTileState == null) {
            return null;
        }
        Context context = this.qsHost.getContext();
        QSTileConfig config = qSTileViewModel.getConfig();
        Companion.getClass();
        return Companion.mapState(context, qSTileState, config);
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        CharSequence charSequence;
        QSTileViewModel qSTileViewModel = this.qsTileViewModel;
        QSTileUIConfig qSTileUIConfig = qSTileViewModel.getConfig().uiConfig;
        if (qSTileUIConfig instanceof QSTileUIConfig.Empty) {
            QSTileState qSTileState = (QSTileState) CollectionsKt___CollectionsKt.lastOrNull(qSTileViewModel.getState().getReplayCache());
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
        QSTileState qSTileState = (QSTileState) CollectionsKt___CollectionsKt.lastOrNull(this.qsTileViewModel.getState().getReplayCache());
        return (qSTileState == null || (set = qSTileState.supportedActions) == null || !set.contains(userAction)) ? false : true;
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return ((Boolean) this.qsTileViewModel.isAvailable().getValue()).booleanValue();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isListening() {
        boolean z;
        synchronized (this.listeningClients) {
            z = !this.listeningClients.isEmpty();
        }
        return z;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isTileReady() {
        return ((QSTileState) CollectionsKt___CollectionsKt.lastOrNull(this.qsTileViewModel.getState().getReplayCache())) != null;
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
        synchronized (this.callbacks) {
            this.callbacks.remove(callback);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void removeCallbacks() {
        synchronized (this.callbacks) {
            this.callbacks.clear();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void secondaryClick(Expandable expandable) {
        if (isActionSupported(QSTileState.UserAction.CLICK)) {
            this.qsTileViewModel.onActionPerformed(new QSTileUserAction.Click(expandable));
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void setListening(Object obj, boolean z) {
        Job job;
        if (obj == null) {
            return;
        }
        synchronized (this.listeningClients) {
            try {
                if (z) {
                    this.listeningClients.add(obj);
                    if (this.listeningClients.size() == 1) {
                        final SharedFlow state = this.qsTileViewModel.getState();
                        this.stateJob = FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$setListening$lambda$4$$inlined$map$1

                            /* renamed from: com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$setListening$lambda$4$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ QSTileViewModelAdapter this$0;

                                /* renamed from: com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$setListening$lambda$4$$inlined$map$1$2$1, reason: invalid class name */
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

                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                                    /*
                                        r5 = this;
                                        boolean r0 = r7 instanceof com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$setListening$lambda$4$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r7
                                        com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$setListening$lambda$4$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$setListening$lambda$4$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$setListening$lambda$4$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$setListening$lambda$4$$inlined$map$1$2$1
                                        r0.<init>(r7)
                                    L18:
                                        java.lang.Object r7 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r7)
                                        goto L56
                                    L27:
                                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                        r5.<init>(r6)
                                        throw r5
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r7)
                                        com.android.systemui.qs.tiles.viewmodel.QSTileState r6 = (com.android.systemui.qs.tiles.viewmodel.QSTileState) r6
                                        com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$Companion r7 = com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter.Companion
                                        com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter r2 = r5.this$0
                                        com.android.systemui.qs.QSHost r4 = r2.qsHost
                                        android.content.Context r4 = r4.getContext()
                                        com.android.systemui.qs.tiles.viewmodel.QSTileViewModel r2 = r2.qsTileViewModel
                                        com.android.systemui.qs.tiles.viewmodel.QSTileConfig r2 = r2.getConfig()
                                        r7.getClass()
                                        com.android.systemui.plugins.qs.QSTile$AdapterState r6 = com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter.Companion.mapState(r4, r6, r2)
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                        java.lang.Object r5 = r5.emit(r6, r0)
                                        if (r5 != r1) goto L56
                                        return r1
                                    L56:
                                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                        return r5
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$setListening$lambda$4$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        }, new QSTileViewModelAdapter$setListening$1$2(this, null)), this.applicationScope);
                    }
                } else {
                    this.listeningClients.remove(obj);
                    if (this.listeningClients.isEmpty() && (job = this.stateJob) != null) {
                        job.cancel(null);
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void setTileSpec(String str) {
        throw new UnsupportedOperationException("Tile spec is immutable in new tiles");
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void userSwitch(int i) {
        this.qsTileViewModel.onUserChanged(UserHandle.of(i));
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void setDetailListening(boolean z) {
    }
}
