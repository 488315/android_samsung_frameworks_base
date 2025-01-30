package com.android.systemui.media.dialog;

import android.app.KeyguardManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.session.MediaSessionManager;
import android.os.PowerExemptionManager;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import java.util.Optional;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaOutputDialogFactory {
    public static MediaOutputDialog mediaOutputDialog;
    public final AudioManager audioManager;
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final FeatureFlags featureFlags;
    public final KeyguardManager keyGuardManager;
    public final LocalBluetoothManager lbm;
    public final MediaSessionManager mediaSessionManager;
    public final Optional nearbyMediaDevicesManagerOptional;
    public final CommonNotifCollection notifCollection;
    public final PowerExemptionManager powerExemptionManager;
    public final ActivityStarter starter;
    public final UiEventLogger uiEventLogger;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public MediaOutputDialogFactory(Context context, MediaSessionManager mediaSessionManager, LocalBluetoothManager localBluetoothManager, ActivityStarter activityStarter, BroadcastSender broadcastSender, CommonNotifCollection commonNotifCollection, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, Optional<NearbyMediaDevicesManager> optional, AudioManager audioManager, PowerExemptionManager powerExemptionManager, KeyguardManager keyguardManager, FeatureFlags featureFlags, UserTracker userTracker) {
        this.context = context;
        this.mediaSessionManager = mediaSessionManager;
        this.lbm = localBluetoothManager;
        this.starter = activityStarter;
        this.broadcastSender = broadcastSender;
        this.notifCollection = commonNotifCollection;
        this.uiEventLogger = uiEventLogger;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.nearbyMediaDevicesManagerOptional = optional;
        this.audioManager = audioManager;
        this.powerExemptionManager = powerExemptionManager;
        this.keyGuardManager = keyguardManager;
        this.featureFlags = featureFlags;
        this.userTracker = userTracker;
    }

    public final void create(String str, View view) {
        MediaOutputDialog mediaOutputDialog2 = mediaOutputDialog;
        if (mediaOutputDialog2 != null) {
            mediaOutputDialog2.dismiss();
        }
        MediaOutputDialog mediaOutputDialog3 = new MediaOutputDialog(this.context, false, this.broadcastSender, new MediaOutputController(this.context, str, this.mediaSessionManager, this.lbm, this.starter, this.notifCollection, this.dialogLaunchAnimator, this.nearbyMediaDevicesManagerOptional, this.audioManager, this.powerExemptionManager, this.keyGuardManager, this.featureFlags, this.userTracker), this.dialogLaunchAnimator, this.uiEventLogger);
        mediaOutputDialog = mediaOutputDialog3;
        if (view != null) {
            DialogLaunchAnimator.showFromView$default(this.dialogLaunchAnimator, mediaOutputDialog3, view, new DialogCuj(58, "media_output"), false, 8);
        } else {
            mediaOutputDialog3.show();
        }
    }
}
