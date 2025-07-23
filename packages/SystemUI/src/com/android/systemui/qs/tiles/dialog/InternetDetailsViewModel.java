package com.android.systemui.qs.tiles.dialog;

import android.content.Intent;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.dialog.InternetDetailsContentManager;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InternetDetailsViewModel implements TileDetailsViewModel {
    public final AccessPointController accessPointController;
    public final InternetDetailsContentManager.Factory contentManagerFactory;
    public final Lazy internetDetailsContentManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsViewModel$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            InternetDetailsViewModel internetDetailsViewModel = InternetDetailsViewModel.this;
            AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) internetDetailsViewModel.accessPointController;
            return internetDetailsViewModel.contentManagerFactory.create(accessPointControllerImpl.canConfigMobileData(), accessPointControllerImpl.canConfigWifi());
        }
    });
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        InternetDetailsViewModel create();
    }

    public InternetDetailsViewModel(AccessPointController accessPointController, InternetDetailsContentManager.Factory factory, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.accessPointController = accessPointController;
        this.contentManagerFactory = factory;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final void clickOnSettingsButton() {
        QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, null, new Intent("android.settings.WIFI_SETTINGS"));
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final String getSubTitle() {
        return (String) ((SnapshotMutableStateImpl) ((InternetDetailsContentManager) this.internetDetailsContentManager$delegate.getValue()).subTitle$delegate).getValue();
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final String getTitle() {
        return (String) ((SnapshotMutableStateImpl) ((InternetDetailsContentManager) this.internetDetailsContentManager$delegate.getValue()).title$delegate).getValue();
    }
}
