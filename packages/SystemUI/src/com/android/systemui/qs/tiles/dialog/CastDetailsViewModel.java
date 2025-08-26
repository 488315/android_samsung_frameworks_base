package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.android.internal.app.MediaRouteControllerContentManager;
import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;

/* loaded from: classes2.dex */
public final class CastDetailsViewModel implements MediaRouteControllerContentManager.Delegate, TileDetailsViewModel {
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public interface Factory {
        CastDetailsViewModel create(Context context, int i);
    }

    public CastDetailsViewModel(QSTileIntentUserInputHandler qSTileIntentUserInputHandler, Context context, int i) {
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final void clickOnSettingsButton() {
        QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, null, new Intent("android.settings.CAST_SETTINGS"));
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final String getSubTitle() {
        return "Searching for devices...";
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final String getTitle() {
        return "Cast screen to device";
    }

    public final void setMediaRouteDeviceIcon(Drawable drawable) {
    }

    public final void setMediaRouteDeviceTitle(CharSequence charSequence) {
    }

    public final void dismissView() {
    }
}
