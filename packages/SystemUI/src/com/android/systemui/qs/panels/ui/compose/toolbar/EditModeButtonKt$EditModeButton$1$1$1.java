package com.android.systemui.qs.panels.ui.compose.toolbar;

import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.toolbar.EditModeButtonViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class EditModeButtonKt$EditModeButton$1$1$1 extends FunctionReferenceImpl implements Function0 {
    public EditModeButtonKt$EditModeButton$1$1$1(Object obj) {
        super(0, obj, EditModeButtonViewModel.class, "onButtonClick", "onButtonClick()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        final EditModeButtonViewModel editModeButtonViewModel = (EditModeButtonViewModel) this.receiver;
        if (!editModeButtonViewModel.falsingInteractor.manager.isFalseTap(1)) {
            editModeButtonViewModel.activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.panels.ui.viewmodel.toolbar.EditModeButtonViewModel$onButtonClick$1
                @Override // java.lang.Runnable
                public final void run() {
                    EditModeViewModel editModeViewModel = EditModeButtonViewModel.this.editModeViewModel;
                    if (!((Boolean) editModeViewModel.isEditing.$$delegate_0.getValue()).booleanValue()) {
                        editModeViewModel.uiEventLogger.log(QSEditEvent.QS_EDIT_OPEN);
                    }
                    editModeViewModel._isEditing.updateState(null, Boolean.TRUE);
                }
            });
        }
        return Unit.INSTANCE;
    }
}
