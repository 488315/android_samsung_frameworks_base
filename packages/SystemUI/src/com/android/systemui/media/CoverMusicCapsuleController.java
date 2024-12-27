package com.android.systemui.media;

import android.content.Context;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CoverMusicCapsuleController {
    public final Bundle bundle;
    public final RemoteViews capsule;
    public boolean isLiveStreaming;
    public final BooleanSupplier isPlayerCoverPlayedSupplier;
    public final SubScreenManager subScreenManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public CoverMusicCapsuleController(Context context, SubScreenManager subScreenManager, BooleanSupplier booleanSupplier) {
        this.subScreenManager = subScreenManager;
        this.isPlayerCoverPlayedSupplier = booleanSupplier;
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("com.samsung.android.widgetComponentName", "com.samsung.android.app.aodservice/MusicTile");
        this.bundle = m;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sec_media_capsule);
        this.capsule = remoteViews;
        Log.d("CoverMusicCapsuleController", "capsule created");
        m.putBoolean("visible", true);
        m.putParcelable("capsule_layout", remoteViews);
        m.putString("capsule_priority", SignalSeverity.LOW);
        updateCapsule();
    }

    public final void updateCapsule() {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("updateCapsule Called, isPlayerCoverPlayed : ", "CoverMusicCapsuleController", this.isPlayerCoverPlayedSupplier.getAsBoolean());
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
        int intValue = ((Number) triple.component1()).intValue();
        int intValue2 = ((Number) triple.component2()).intValue();
        boolean booleanValue = ((Boolean) triple.component3()).booleanValue();
        Triple triple2 = this.isLiveStreaming ? new Triple(8, 8, 0) : (playbackState.getPlaybackSpeed() == 1.0f || playbackState.getPlaybackSpeed() == 0.0f) ? new Triple(0, 8, 8) : new Triple(8, 0, 8);
        int intValue3 = ((Number) triple2.component1()).intValue();
        int intValue4 = ((Number) triple2.component2()).intValue();
        int intValue5 = ((Number) triple2.component3()).intValue();
        long elapsedRealtime = SystemClock.elapsedRealtime() - playbackState.getPosition();
        remoteViews.setViewVisibility(R.id.sec_media_capsule_equalizer_ongoing, intValue);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_equalizer_stop, intValue2);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_elapsed_time, intValue3);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_playing_speed, intValue4);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_live_streaming_icon, intValue5);
        int i = StringCompanionObject.$r8$clinit;
        remoteViews.setTextViewText(R.id.sec_media_capsule_playing_speed, String.format("x%.2f", Arrays.copyOf(new Object[]{Float.valueOf(playbackState.getPlaybackSpeed())}, 1)));
        remoteViews.setChronometer(R.id.sec_media_capsule_elapsed_time, elapsedRealtime, null, booleanValue);
        Bundle bundle = this.bundle;
        String str = playbackState.getState() == 3 ? SystemUIAnalytics.QPNE_VID_NORMAL : SignalSeverity.LOW;
        bundle.putParcelable("capsule_layout", this.capsule);
        bundle.putString("capsule_priority", str);
        updateCapsule();
    }
}
