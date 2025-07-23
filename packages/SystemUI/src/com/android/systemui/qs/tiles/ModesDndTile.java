package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl;
import com.android.settingslib.notification.modes.ZenMode;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTileIconKt;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProvider;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProviderImpl;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesDndTileDataInteractor;
import com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesDndTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.modes.domain.model.ModesDndTileModel;
import com.android.systemui.qs.tiles.impl.modes.ui.ModesDndTileMapper;
import com.android.systemui.qs.tiles.impl.modes.ui.ModesDndTileMapper$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ModesDndTile extends QSTileImpl {
    public final QSTileConfig config;
    public final ModesDndTileDataInteractor dataInteractor;
    public final ModesDndTileMapper tileMapper;
    public QSTileState tileState;
    public final ModesDndTileUserActionInteractor userActionInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.ModesDndTile$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.tiles.ModesDndTile$1$1, reason: invalid class name and collision with other inner class name */
        final class C02601 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ ModesDndTile this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02601(ModesDndTile modesDndTile, Continuation continuation) {
                super(2, continuation);
                this.this$0 = modesDndTile;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02601(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02601) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i != 0) {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.dataInteractor.zenModeInteractor.getClass();
                ZenModeInteractor.getDndMode();
                throw null;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ModesDndTile.this.new AnonymousClass1(continuation);
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
                ModesDndTile modesDndTile = ModesDndTile.this;
                LifecycleRegistry lifecycleRegistry = modesDndTile.mLifecycle;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C02601 c02601 = new C02601(modesDndTile, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleRegistry, state, c02601, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public ModesDndTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, QSTileConfigProvider qSTileConfigProvider, ModesDndTileDataInteractor modesDndTileDataInteractor, ModesDndTileMapper modesDndTileMapper, ModesDndTileUserActionInteractor modesDndTileUserActionInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.dataInteractor = modesDndTileDataInteractor;
        this.tileMapper = modesDndTileMapper;
        this.userActionInteractor = modesDndTileUserActionInteractor;
        this.config = ((QSTileConfigProviderImpl) qSTileConfigProvider).getConfig("modes_dnd");
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(this.mLifecycle), null, null, new AnonymousClass1(null), 7);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        this.userActionInteractor.zenModeInteractor.getClass();
        ZenModeInteractor.getDndMode();
        throw null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_dnd_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new ModesDndTile$handleClick$1(this, null));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        QSTile.Icon icon;
        ModesDndTileModel modesDndTileModel = obj instanceof ModesDndTileModel ? (ModesDndTileModel) obj : null;
        if (modesDndTileModel == null) {
            ModesDndTileDataInteractor modesDndTileDataInteractor = this.dataInteractor;
            ArrayList arrayList = (ArrayList) ((ZenModeRepositoryImpl) modesDndTileDataInteractor.zenModeInteractor.zenModeRepository).backend.getModes();
            int size = arrayList.size();
            boolean z = false;
            Object obj2 = null;
            int i = 0;
            while (i < size) {
                Object obj3 = arrayList.get(i);
                i++;
                if (((ZenMode) obj3).isManualDnd()) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    z = true;
                    obj2 = obj3;
                }
            }
            if (!z) {
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
            ZenMode zenMode = (ZenMode) obj2;
            modesDndTileModel = new ModesDndTileModel(zenMode.isActive(), TextUtils.nullIfEmpty(modesDndTileDataInteractor.zenModeDescriptions.getTriggerDescription(zenMode)));
        }
        ModesDndTileMapper modesDndTileMapper = this.tileMapper;
        modesDndTileMapper.getClass();
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = modesDndTileMapper.resources;
        Resources.Theme theme = modesDndTileMapper.theme;
        QSTileUIConfig qSTileUIConfig = this.config.uiConfig;
        ModesDndTileMapper$$ExternalSyntheticLambda0 modesDndTileMapper$$ExternalSyntheticLambda0 = new ModesDndTileMapper$$ExternalSyntheticLambda0(modesDndTileModel, modesDndTileMapper);
        companion.getClass();
        QSTileState build = QSTileState.Companion.build(resources, theme, qSTileUIConfig, modesDndTileMapper$$ExternalSyntheticLambda0);
        this.tileState = build;
        if (booleanState != null) {
            boolean z2 = modesDndTileModel.isActivated;
            booleanState.value = z2;
            booleanState.state = build.activationState.getLegacyState();
            QSTileState qSTileState = this.tileState;
            if (qSTileState == null) {
                qSTileState = null;
            }
            Icon icon2 = qSTileState.icon;
            if (icon2 != null) {
                icon = QSTileIconKt.asQSTileIcon(icon2);
            } else {
                int i2 = z2 ? R.drawable.qs_dnd_icon_on : R.drawable.qs_dnd_icon_off;
                int i3 = QsInCompose.$r8$clinit;
                icon = QSTileImpl.ResourceIcon.get(i2);
            }
            booleanState.icon = icon;
            booleanState.label = getTileLabel();
            QSTileState qSTileState2 = this.tileState;
            booleanState.secondaryLabel = (qSTileState2 == null ? null : qSTileState2).secondaryLabel;
            booleanState.contentDescription = (qSTileState2 == null ? null : qSTileState2).contentDescription;
            booleanState.stateDescription = (qSTileState2 == null ? null : qSTileState2).stateDescription;
            booleanState.expandedAccessibilityClassName = (qSTileState2 != null ? qSTileState2 : null).expandedAccessibilityClassName;
        }
    }
}
