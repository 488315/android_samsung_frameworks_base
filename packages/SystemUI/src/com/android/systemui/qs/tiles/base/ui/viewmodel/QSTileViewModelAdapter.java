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
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$1$1, reason: invalid class name and collision with other inner class name */
        final class C02641 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSTileViewModelAdapter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02641(QSTileViewModelAdapter qSTileViewModelAdapter, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSTileViewModelAdapter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02641(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02641) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlow isAvailable = this.this$0.qsTileViewModel.isAvailable();
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
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            QSTileViewModelAdapter qSTileViewModelAdapter2 = QSTileViewModelAdapter.this;
                            if (!booleanValue && qSTileViewModelAdapter2.qsTileViewModel.getConfig().autoRemoveOnUnavailable) {
                                qSTileViewModelAdapter2.qsHost.removeTile(qSTileViewModelAdapter2.getTileSpec());
                            }
                            if (i2 <= 0 || !booleanValue) {
                                return Unit.INSTANCE;
                            }
                            throw new UnsupportedOperationException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Turning on tile is not supported now. Tile spec: ", qSTileViewModelAdapter2.getTileSpec()));
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSTileViewModelAdapter this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$1$2$1, reason: invalid class name and collision with other inner class name */
            final class C02651 extends SuspendLambda implements Function2 {
                /* synthetic */ Object L$0;
                int label;

                public C02651(Continuation continuation) {
                    super(2, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C02651 c02651 = new C02651(continuation);
                    c02651.L$0 = obj;
                    return c02651;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02651) create((QSTileState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1 flowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1 = new FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1(this.this$0.qsTileViewModel.getState(), new C02651(null));
                    C02662 c02662 = new FlowCollector() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter.1.2.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1.collect(c02662, this) == coroutineSingletons) {
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
            BuildersKt.launch$default(coroutineScope, null, null, new C02641(QSTileViewModelAdapter.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(QSTileViewModelAdapter.this, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static QSTile.AdapterState mapState(Context context, QSTileState qSTileState, QSTileConfig qSTileConfig) {
            QSTile.Icon icon;
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
            Icon icon2 = qSTileState.icon;
            if (icon2 instanceof Icon.Loaded) {
                Icon.Loaded loaded = (Icon.Loaded) icon2;
                Integer num = loaded.res;
                icon = num == null ? new QSTileImpl.DrawableIcon(loaded.drawable) : new QSTileImpl.DrawableIconWithRes(loaded.drawable, num.intValue());
            } else if (icon2 instanceof Icon.Resource) {
                icon = QSTileImpl.ResourceIcon.get(((Icon.Resource) icon2).res);
            } else {
                if (icon2 != null) {
                    throw new NoWhenBranchMatchedException();
                }
                icon = null;
            }
            adapterState.icon = icon;
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
            Icon icon3 = ((QSTileState.SideViewIcon.Custom) sideViewIcon).icon;
            if (icon3 instanceof Icon.Loaded) {
                drawable = ((Icon.Loaded) icon3).drawable;
            } else {
                if (!(icon3 instanceof Icon.Resource)) {
                    throw new NoWhenBranchMatchedException();
                }
                drawable = context.getDrawable(((Icon.Resource) icon3).res);
            }
            adapterState.sideViewCustomDrawable = drawable;
            return adapterState;
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        QSTileViewModelAdapter create(QSTileViewModel qSTileViewModel);
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
            BuildersKt.launch$default(this.applicationScope, this.uiBgDispatcher, null, new QSTileViewModelAdapter$setListening$1(this, obj, null), 2);
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
