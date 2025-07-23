package com.android.systemui.qs.panels.ui.viewmodel.toolbar;

import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class ToolbarViewModel$settingsButtonViewModel$1 extends FunctionReferenceImpl implements Function1 {
    public ToolbarViewModel$settingsButtonViewModel$1(Object obj) {
        super(1, obj, ToolbarViewModel.class, "onSettingsButtonClicked", "onSettingsButtonClicked(Lcom/android/systemui/animation/Expandable;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Expandable expandable = (Expandable) obj;
        ToolbarViewModel toolbarViewModel = (ToolbarViewModel) this.receiver;
        if (!toolbarViewModel.falsingInteractor.manager.isFalseTap(1)) {
            ((FooterActionsInteractorImpl) toolbarViewModel.footerActionsInteractor).showSettings(expandable);
        }
        return Unit.INSTANCE;
    }
}
