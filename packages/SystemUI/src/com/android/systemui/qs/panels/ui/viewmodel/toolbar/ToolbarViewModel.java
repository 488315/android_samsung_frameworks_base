package com.android.systemui.qs.panels.ui.viewmodel.toolbar;

import android.content.Context;
import android.view.ContextThemeWrapper;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.development.ui.viewmodel.BuildNumberViewModel;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$powerButtonViewModel$$inlined$map$1;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1;
import com.android.systemui.qs.panels.ui.viewmodel.toolbar.EditModeButtonViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import javax.inject.Provider;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ToolbarViewModel extends ExclusiveActivatable {
    public final BuildNumberViewModel.Factory buildNumberViewModelFactory;
    public final EditModeButtonViewModel editModeButtonViewModel;
    public final FalsingInteractor falsingInteractor;
    public final FooterActionsInteractor footerActionsInteractor;
    public final MutableState globalActionsDialogLite$delegate;
    public final Provider globalActionsDialogLiteProvider;
    public final Hydrator hydrator;
    public final State powerButtonViewModel$delegate;
    public final FooterActionsButtonViewModel settingsButtonViewModel;
    public final State userSwitcherViewModel$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ToolbarViewModel create();
    }

    public ToolbarViewModel(EditModeButtonViewModel.Factory factory, BuildNumberViewModel.Factory factory2, FooterActionsInteractor footerActionsInteractor, Provider provider, FalsingInteractor falsingInteractor, ShadeModeInteractor shadeModeInteractor, Context context) {
        this.buildNumberViewModelFactory = factory2;
        this.footerActionsInteractor = footerActionsInteractor;
        this.globalActionsDialogLiteProvider = provider;
        this.falsingInteractor = falsingInteractor;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.Theme_SystemUI_QuickSettings);
        Hydrator hydrator = new Hydrator("ToolbarViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.powerButtonViewModel$delegate = hydrator.hydratedStateOf("powerButtonViewModel", null, new FooterActionsViewModelKt$powerButtonViewModel$$inlined$map$1(((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode, contextThemeWrapper, new ToolbarViewModel$powerButtonViewModel$2(this)));
        this.settingsButtonViewModel = FooterActionsViewModelKt.settingsButtonViewModel(contextThemeWrapper, new ToolbarViewModel$settingsButtonViewModel$1(this));
        this.userSwitcherViewModel$delegate = hydrator.hydratedStateOf("userSwitcherViewModel", null, FlowKt.distinctUntilChanged(new FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1(((FooterActionsInteractorImpl) footerActionsInteractor).userSwitcherStatus, contextThemeWrapper, new ToolbarViewModel$userSwitcherViewModel$2(this))));
        this.editModeButtonViewModel = factory.create();
        this.globalActionsDialogLite$delegate = SnapshotStateKt.mutableStateOf$default(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$1 r0 = (com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$1 r0 = new com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$2 r5 = new com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
