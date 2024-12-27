package com.android.systemui.volume.panel.component.bottombar.ui;

import android.content.Intent;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.volume.panel.component.bottombar.ui.viewmodel.BottomBarViewModel;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class BottomBarComponent$Content$1$1 extends FunctionReferenceImpl implements Function0 {
    public BottomBarComponent$Content$1$1(Object obj) {
        super(0, obj, BottomBarViewModel.class, "onSettingsClicked", "onSettingsClicked()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        final BottomBarViewModel bottomBarViewModel = (BottomBarViewModel) this.receiver;
        bottomBarViewModel.uiEventLogger.log(VolumePanelUiEvent.VOLUME_PANEL_SOUND_SETTINGS_CLICKED);
        bottomBarViewModel.activityStarter.startActivityDismissingKeyguard(new Intent("android.settings.SOUND_SETTINGS"), false, true, false, new ActivityStarter.Callback() { // from class: com.android.systemui.volume.panel.component.bottombar.ui.viewmodel.BottomBarViewModel$onSettingsClicked$1
            @Override // com.android.systemui.plugins.ActivityStarter.Callback
            public final void onActivityStarted(int i) {
                BottomBarViewModel.this.volumePanelViewModel.volumePanelGlobalStateInteractor.setVisible(false);
            }
        }, 131072, null, null);
        return Unit.INSTANCE;
    }
}
