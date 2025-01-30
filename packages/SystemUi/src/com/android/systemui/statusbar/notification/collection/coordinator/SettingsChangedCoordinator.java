package com.android.systemui.statusbar.notification.collection.coordinator;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SettingsChangedCoordinator implements Coordinator {
    public final SettingsChangedCoordinator$mSettingsCallback$1 mSettingsCallback = new SettingsChangedCoordinator$mSettingsCallback$1(this);
    public final NotifLiveDataStoreImpl notifLiveDataStoreImpl;

    public SettingsChangedCoordinator(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        this.notifLiveDataStoreImpl = notifLiveDataStoreImpl;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        Uri[] uriArr = {Settings.Secure.getUriFor("show_notification_snooze")};
        SettingsChangedCoordinator$mSettingsCallback$1 settingsChangedCoordinator$mSettingsCallback$1 = this.mSettingsCallback;
        settingsHelper.registerCallback(settingsChangedCoordinator$mSettingsCallback$1, uriArr);
        settingsChangedCoordinator$mSettingsCallback$1.onChanged(Settings.Secure.getUriFor("show_notification_snooze"));
    }
}
