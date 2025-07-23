package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProvider;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProviderImpl;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.impl.notes.domain.interactor.NotesTileDataInteractor;
import com.android.systemui.qs.tiles.impl.notes.domain.interactor.NotesTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.notes.domain.model.NotesTileModel;
import com.android.systemui.qs.tiles.impl.notes.ui.mapper.NotesTileMapper;
import com.android.systemui.qs.tiles.impl.notes.ui.mapper.NotesTileMapper$$ExternalSyntheticLambda0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NotesTile extends QSTileImpl {
    public final ActivityStarter activityStarter;
    public final QSTileConfig config;
    public final NotesTileDataInteractor dataInteractor;
    public final FalsingManager falsingManager;
    public final StatusBarStateController statusBarStateController;
    public final NotesTileMapper tileMapper;
    public QSTileState tileState;
    public final NotesTileUserActionInteractor userActionInteractor;

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

    public NotesTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, QSTileConfigProvider qSTileConfigProvider, NotesTileDataInteractor notesTileDataInteractor, NotesTileMapper notesTileMapper, NotesTileUserActionInteractor notesTileUserActionInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.falsingManager = falsingManager;
        this.statusBarStateController = statusBarStateController;
        this.activityStarter = activityStarter;
        this.dataInteractor = notesTileDataInteractor;
        this.tileMapper = notesTileMapper;
        this.userActionInteractor = notesTileUserActionInteractor;
        this.config = ((QSTileConfigProviderImpl) qSTileConfigProvider).getConfig("notes");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return this.userActionInteractor.longClickIntent;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(this.config.uiConfig.getLabelRes());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        NotesTileUserActionInteractor notesTileUserActionInteractor = this.userActionInteractor;
        notesTileUserActionInteractor.getClass();
        NoteTaskEntryPoint noteTaskEntryPoint = NoteTaskEntryPoint.QS_NOTES_TILE;
        NoteTaskController noteTaskController = notesTileUserActionInteractor.noteTaskController;
        if (noteTaskController.isEnabled) {
            noteTaskController.showNoteTaskAsUser(noteTaskEntryPoint, noteTaskController.getUserForHandlingNotesTaking(noteTaskEntryPoint));
        }
        ((PanelInteractorImpl) notesTileUserActionInteractor.panelInteractor).collapsePanels();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        if (!(obj instanceof NotesTileModel)) {
            this.dataInteractor.getClass();
            NotesTileModel notesTileModel = NotesTileModel.INSTANCE;
        }
        NotesTileMapper notesTileMapper = this.tileMapper;
        notesTileMapper.getClass();
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = notesTileMapper.resources;
        Resources.Theme theme = notesTileMapper.theme;
        QSTileUIConfig qSTileUIConfig = this.config.uiConfig;
        NotesTileMapper$$ExternalSyntheticLambda0 notesTileMapper$$ExternalSyntheticLambda0 = new NotesTileMapper$$ExternalSyntheticLambda0(notesTileMapper);
        companion.getClass();
        QSTileState build = QSTileState.Companion.build(resources, theme, qSTileUIConfig, notesTileMapper$$ExternalSyntheticLambda0);
        this.tileState = build;
        if (state != null) {
            state.state = build.activationState.getLegacyState();
            QSTileState qSTileState = this.tileState;
            if (qSTileState == null) {
                qSTileState = null;
            }
            Integer num = ((Icon.Loaded) qSTileState.icon).res;
            int intValue = num != null ? num.intValue() : R.drawable.ic_qs_notes;
            int i = QsInCompose.$r8$clinit;
            state.icon = QSTileImpl.ResourceIcon.get(intValue);
            QSTileState qSTileState2 = this.tileState;
            state.label = (qSTileState2 == null ? null : qSTileState2).label;
            state.contentDescription = (qSTileState2 == null ? null : qSTileState2).contentDescription;
            state.expandedAccessibilityClassName = (qSTileState2 != null ? qSTileState2 : null).expandedAccessibilityClassName;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        this.dataInteractor.getClass();
        return false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.state = 1;
        return state;
    }
}
