package com.android.systemui.media.dialog;

import android.app.KeyguardManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.session.MediaSessionManager;
import android.os.PowerExemptionManager;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaOutputBroadcastDialogFactory {
    public final AudioManager audioManager;
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final FeatureFlags featureFlags;
    public final KeyguardManager keyGuardManager;
    public final LocalBluetoothManager lbm;
    public MediaOutputBroadcastDialog mediaOutputBroadcastDialog;
    public final MediaSessionManager mediaSessionManager;
    public final Optional nearbyMediaDevicesManagerOptional;
    public final CommonNotifCollection notifCollection;
    public final PowerExemptionManager powerExemptionManager;
    public final ActivityStarter starter;
    public final UserTracker userTracker;

    public MediaOutputBroadcastDialogFactory(Context context, MediaSessionManager mediaSessionManager, LocalBluetoothManager localBluetoothManager, ActivityStarter activityStarter, BroadcastSender broadcastSender, CommonNotifCollection commonNotifCollection, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, Optional<NearbyMediaDevicesManager> optional, AudioManager audioManager, PowerExemptionManager powerExemptionManager, KeyguardManager keyguardManager, FeatureFlags featureFlags, UserTracker userTracker) {
        this.context = context;
        this.mediaSessionManager = mediaSessionManager;
        this.lbm = localBluetoothManager;
        this.starter = activityStarter;
        this.broadcastSender = broadcastSender;
        this.notifCollection = commonNotifCollection;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.nearbyMediaDevicesManagerOptional = optional;
        this.audioManager = audioManager;
        this.powerExemptionManager = powerExemptionManager;
        this.keyGuardManager = keyguardManager;
        this.featureFlags = featureFlags;
        this.userTracker = userTracker;
    }
}
