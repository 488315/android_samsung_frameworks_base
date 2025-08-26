package com.android.systemui.media;

import android.content.Context;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.util.Arrays;
import java.util.function.BooleanSupplier;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;

/* loaded from: classes2.dex */
public final class CoverMusicCapsuleController {
    public final Bundle bundle;
    public final RemoteViews capsule;
    public boolean isLiveStreaming;
    public final BooleanSupplier isPlayerCoverPlayedSupplier;
    public final SubScreenManager subScreenManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CoverMusicCapsuleController(Context context, SubScreenManager subScreenManager, BooleanSupplier booleanSupplier) {
        this.subScreenManager = subScreenManager;
        this.isPlayerCoverPlayedSupplier = booleanSupplier;
        Bundle bundleM = KeyguardSecPatternView$$ExternalSyntheticOutline0.m("com.samsung.android.widgetComponentName", "com.samsung.android.app.aodservice/MusicTile");
        this.bundle = bundleM;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sec_media_capsule);
        this.capsule = remoteViews;
        Log.d("CoverMusicCapsuleController", "capsule created");
        bundleM.putBoolean("visible", true);
        bundleM.putParcelable("capsule_layout", remoteViews);
        bundleM.putString("capsule_priority", SignalSeverity.LOW);
        updateCapsule();
    }

    public final void updateCapsule() {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("updateCapsule Called, isPlayerCoverPlayed : ", "CoverMusicCapsuleController", this.isPlayerCoverPlayedSupplier.getAsBoolean());
        if (this.isPlayerCoverPlayedSupplier.getAsBoolean()) {
            Bundle bundle = this.bundle;
            PluginSubScreen pluginSubScreen = this.subScreenManager.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "updateCapsule() no plugin");
            } else {
                pluginSubScreen.updateCapsule(bundle);
            }
        }
    }

    public final void updateEqualizerState(PlaybackState playbackState) {
        Log.d("CoverMusicCapsuleController", "updateEqualizerState: " + (playbackState == null ? "state is null" : playbackState));
        if (playbackState == null) {
            return;
        }
        RemoteViews remoteViews = this.capsule;
        Triple triple = playbackState.getState() == 3 ? new Triple(0, 8, Boolean.TRUE) : new Triple(8, 0, Boolean.FALSE);
        int iIntValue = ((Number) triple.component1()).intValue();
        int iIntValue2 = ((Number) triple.component2()).intValue();
        boolean zBooleanValue = ((Boolean) triple.component3()).booleanValue();
        Triple triple2 = this.isLiveStreaming ? new Triple(8, 8, 0) : (playbackState.getPlaybackSpeed() == 1.0f || playbackState.getPlaybackSpeed() == 0.0f) ? new Triple(0, 8, 8) : new Triple(8, 0, 8);
        int iIntValue3 = ((Number) triple2.component1()).intValue();
        int iIntValue4 = ((Number) triple2.component2()).intValue();
        int iIntValue5 = ((Number) triple2.component3()).intValue();
        long jElapsedRealtime = SystemClock.elapsedRealtime() - playbackState.getPosition();
        remoteViews.setViewVisibility(R.id.sec_media_capsule_equalizer_ongoing, iIntValue);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_equalizer_stop, iIntValue2);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_elapsed_time, iIntValue3);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_playing_speed, iIntValue4);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_live_streaming_icon, iIntValue5);
        int i = StringCompanionObject.$r8$clinit;
        remoteViews.setTextViewText(R.id.sec_media_capsule_playing_speed, String.format("x%.2f", Arrays.copyOf(new Object[]{Float.valueOf(playbackState.getPlaybackSpeed())}, 1)));
        remoteViews.setChronometer(R.id.sec_media_capsule_elapsed_time, jElapsedRealtime, null, zBooleanValue);
        Bundle bundle = this.bundle;
        String str = playbackState.getState() == 3 ? SystemUIAnalytics.QPNE_VID_NORMAL : SignalSeverity.LOW;
        bundle.putParcelable("capsule_layout", this.capsule);
        bundle.putString("capsule_priority", str);
        updateCapsule();
    }
}
