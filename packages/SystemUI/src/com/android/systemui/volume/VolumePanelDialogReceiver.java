package com.android.systemui.volume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.android.systemui.Flags;
import com.android.systemui.volume.domain.interactor.VolumePanelNavigationInteractor;
import com.android.systemui.volume.domain.model.VolumePanelRoute;
import com.android.systemui.volume.ui.navigation.VolumeNavigator;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VolumePanelDialogReceiver extends BroadcastReceiver {
    public final VolumeNavigator volumeNavigator;
    public final VolumePanelNavigationInteractor volumePanelNavigationInteractor;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public VolumePanelDialogReceiver(VolumeNavigator volumeNavigator, VolumePanelNavigationInteractor volumePanelNavigationInteractor) {
        this.volumeNavigator = volumeNavigator;
        this.volumePanelNavigationInteractor = volumePanelNavigationInteractor;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (TextUtils.equals("com.android.systemui.action.LAUNCH_VOLUME_PANEL_DIALOG", intent.getAction()) || TextUtils.equals("android.settings.panel.action.VOLUME", intent.getAction())) {
            VolumeNavigator volumeNavigator = this.volumeNavigator;
            this.volumePanelNavigationInteractor.volumePanelFlag.getClass();
            Flags.FEATURE_FLAGS.getClass();
            volumeNavigator.openVolumePanel(VolumePanelRoute.COMPOSE_VOLUME_PANEL);
        }
    }
}
