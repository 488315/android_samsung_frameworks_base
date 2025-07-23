package com.android.systemui.qs.tiles.impl.notes.domain.interactor;

import android.content.Intent;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NotesTileUserActionInteractor implements QSTileUserActionInteractor {
    public final Intent longClickIntent;
    public final NoteTaskController noteTaskController;
    public final PanelInteractor panelInteractor;
    public final QSTileIntentUserInputHandler qsTileIntentUserInputHandler;

    public NotesTileUserActionInteractor(QSTileIntentUserInputHandler qSTileIntentUserInputHandler, PanelInteractor panelInteractor, NoteTaskController noteTaskController) {
        this.qsTileIntentUserInputHandler = qSTileIntentUserInputHandler;
        this.panelInteractor = panelInteractor;
        this.noteTaskController = noteTaskController;
        NoteTaskController.Companion.getClass();
        this.longClickIntent = new Intent("android.intent.action.MANAGE_DEFAULT_APP").putExtra("android.intent.extra.ROLE_NAME", "android.app.role.NOTES");
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            NoteTaskEntryPoint noteTaskEntryPoint = NoteTaskEntryPoint.QS_NOTES_TILE;
            NoteTaskController noteTaskController = this.noteTaskController;
            if (noteTaskController.isEnabled) {
                noteTaskController.showNoteTaskAsUser(noteTaskEntryPoint, noteTaskController.getUserForHandlingNotesTaking(noteTaskEntryPoint));
            }
            ((PanelInteractorImpl) this.panelInteractor).collapsePanels();
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserInputHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, this.longClickIntent);
        } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
