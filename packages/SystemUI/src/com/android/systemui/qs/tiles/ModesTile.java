package com.android.systemui.qs.tiles;

import android.R;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTileIconKt;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProvider;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProviderImpl;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.dialog.ModesDetailsViewModel;
import com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor;
import com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor$tileData$$inlined$map$1;
import com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.modes.domain.model.ModesTileModel;
import com.android.systemui.qs.tiles.impl.modes.ui.mapper.ModesTileMapper;
import com.android.systemui.qs.tiles.impl.modes.ui.mapper.ModesTileMapper$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ModesTile extends QSTileImpl {
    public static final Companion Companion = new Companion(null);
    public static final int ICON_RES_ID = R.drawable.menu_selector;
    public final QSTileConfig config;
    public final ModesTileDataInteractor dataInteractor;
    public final ModesDialogViewModel modesDialogViewModel;
    public final ModesTileMapper tileMapper;
    public QSTileState tileState;
    public final ModesTileUserActionInteractor userActionInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.ModesTile$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.tiles.ModesTile$1$1, reason: invalid class name and collision with other inner class name */
        final class C02611 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ ModesTile this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02611(ModesTile modesTile, Continuation continuation) {
                super(2, continuation);
                this.this$0 = modesTile;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02611(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02611) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ModesTileDataInteractor modesTileDataInteractor = this.this$0.dataInteractor;
                    Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.flowOn(new ModesTileDataInteractor$tileData$$inlined$map$1(modesTileDataInteractor.zenModeInteractor.activeModes, modesTileDataInteractor), modesTileDataInteractor.bgDispatcher));
                    final ModesTile modesTile = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.tiles.ModesTile.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Companion companion = ModesTile.Companion;
                            ModesTile.this.refreshState((ModesTileModel) obj2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (distinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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
            return ModesTile.this.new AnonymousClass1(continuation);
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
                ModesTile modesTile = ModesTile.this;
                LifecycleRegistry lifecycleRegistry = modesTile.mLifecycle;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C02611 c02611 = new C02611(modesTile, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleRegistry, state, c02611, this) == coroutineSingletons) {
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
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ModesTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, QSTileConfigProvider qSTileConfigProvider, ModesTileDataInteractor modesTileDataInteractor, ModesTileMapper modesTileMapper, ModesTileUserActionInteractor modesTileUserActionInteractor, ModesDialogViewModel modesDialogViewModel) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.dataInteractor = modesTileDataInteractor;
        this.tileMapper = modesTileMapper;
        this.userActionInteractor = modesTileUserActionInteractor;
        this.modesDialogViewModel = modesDialogViewModel;
        this.config = ((QSTileConfigProviderImpl) qSTileConfigProvider).getConfig("dnd");
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(this.mLifecycle), null, null, new AnonymousClass1(null), 7);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final TileDetailsViewModel getDetailsViewModel() {
        return new ModesDetailsViewModel(new Function0() { // from class: com.android.systemui.qs.tiles.ModesTile$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ModesTileUserActionInteractor modesTileUserActionInteractor = ModesTile.this.userActionInteractor;
                QSTileIntentUserInputHandler.handle$default(modesTileUserActionInteractor.qsTileIntentUserInputHandler, null, modesTileUserActionInteractor.longClickIntent);
                return Unit.INSTANCE;
            }
        }, this.modesDialogViewModel);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return this.userActionInteractor.longClickIntent;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        QSTileState qSTileState = this.tileState;
        if (qSTileState == null) {
            qSTileState = null;
        }
        return qSTileState.label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new ModesTile$handleClick$1(this, expandable, null));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new ModesTile$handleSecondaryClick$1(this, null));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.Icon icon;
        if (!(obj instanceof ModesTileModel)) {
            obj = BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new ModesTile$handleUpdateState$model$1(this, null));
        }
        ModesTileModel modesTileModel = (ModesTileModel) obj;
        ModesTileMapper modesTileMapper = this.tileMapper;
        modesTileMapper.getClass();
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = modesTileMapper.resources;
        Resources.Theme theme = modesTileMapper.theme;
        QSTileUIConfig qSTileUIConfig = this.config.uiConfig;
        ModesTileMapper$$ExternalSyntheticLambda0 modesTileMapper$$ExternalSyntheticLambda0 = new ModesTileMapper$$ExternalSyntheticLambda0(modesTileModel, modesTileMapper);
        companion.getClass();
        QSTileState build = QSTileState.Companion.build(resources, theme, qSTileUIConfig, modesTileMapper$$ExternalSyntheticLambda0);
        this.tileState = build;
        if (state != null) {
            state.state = build.activationState.getLegacyState();
            QSTileState qSTileState = this.tileState;
            if (qSTileState == null) {
                qSTileState = null;
            }
            Icon icon2 = qSTileState.icon;
            if (icon2 != null) {
                icon = QSTileIconKt.asQSTileIcon(icon2);
            } else {
                int i = QsInCompose.$r8$clinit;
                icon = QSTileImpl.ResourceIcon.get(ICON_RES_ID);
            }
            state.icon = icon;
            state.label = getTileLabel();
            QSTileState qSTileState2 = this.tileState;
            state.secondaryLabel = (qSTileState2 == null ? null : qSTileState2).secondaryLabel;
            state.contentDescription = (qSTileState2 == null ? null : qSTileState2).contentDescription;
            state.expandedAccessibilityClassName = (qSTileState2 != null ? qSTileState2 : null).expandedAccessibilityClassName;
            state.handlesSecondaryClick = true;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.label = this.mContext.getString(com.android.systemui.R.string.quick_settings_modes_label);
        state.icon = QSTileImpl.ResourceIcon.get(ICON_RES_ID);
        state.state = 1;
        return state;
    }
}
