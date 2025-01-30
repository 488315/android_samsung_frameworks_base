package com.android.systemui.media;

import android.content.Context;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.systemui.R;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.subscreen.SubScreenManager;
import java.util.Arrays;
import java.util.function.BooleanSupplier;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverMusicCapsuleController {
    public final Bundle bundle;
    public final RemoteViews capsule;
    public boolean isLiveStreaming;
    public final BooleanSupplier isPlayerCoverPlayedSupplier;
    public final SubScreenManager subScreenManager;

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

    public CoverMusicCapsuleController(Context context, SubScreenManager subScreenManager, BooleanSupplier booleanSupplier) {
        this.subScreenManager = subScreenManager;
        this.isPlayerCoverPlayedSupplier = booleanSupplier;
        Bundle bundle = new Bundle();
        bundle.putString("com.samsung.android.widgetComponentName", "com.samsung.android.app.aodservice/MusicTile");
        this.bundle = bundle;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sec_media_capsule);
        this.capsule = remoteViews;
        Log.d("CoverMusicCapsuleController", "capsule created");
        bundle.putBoolean("visible", true);
        bundle.putParcelable("capsule_layout", remoteViews);
        bundle.putString("capsule_priority", "low");
        updateCapsule();
    }

    public final void updateCapsule() {
        BooleanSupplier booleanSupplier = this.isPlayerCoverPlayedSupplier;
        Log.d("CoverMusicCapsuleController", "updateCapsule Called, isPlayerCoverPlayed : " + booleanSupplier.getAsBoolean());
        if (booleanSupplier.getAsBoolean()) {
            PluginSubScreen pluginSubScreen = this.subScreenManager.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "updateCapsule() no plugin");
            } else {
                pluginSubScreen.updateCapsule(this.bundle);
            }
        }
    }

    public final void updateEqualizerState(PlaybackState playbackState) {
        Triple triple;
        Log.d("CoverMusicCapsuleController", "updateEqualizerState: " + (playbackState == null ? "state is null" : playbackState));
        if (playbackState == null) {
            return;
        }
        RemoteViews remoteViews = this.capsule;
        Triple triple2 = playbackState.getState() == 3 ? new Triple(0, 8, Boolean.TRUE) : new Triple(8, 0, Boolean.FALSE);
        int intValue = ((Number) triple2.component1()).intValue();
        int intValue2 = ((Number) triple2.component2()).intValue();
        boolean booleanValue = ((Boolean) triple2.component3()).booleanValue();
        if (this.isLiveStreaming) {
            triple = new Triple(8, 8, 0);
        } else {
            if (!(playbackState.getPlaybackSpeed() == 1.0f)) {
                if (!(playbackState.getPlaybackSpeed() == 0.0f)) {
                    triple = new Triple(8, 0, 8);
                }
            }
            triple = new Triple(0, 8, 8);
        }
        int intValue3 = ((Number) triple.component1()).intValue();
        int intValue4 = ((Number) triple.component2()).intValue();
        int intValue5 = ((Number) triple.component3()).intValue();
        long elapsedRealtime = SystemClock.elapsedRealtime() - playbackState.getPosition();
        remoteViews.setViewVisibility(R.id.sec_media_capsule_equalizer_ongoing, intValue);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_equalizer_stop, intValue2);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_elapsed_time, intValue3);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_playing_speed, intValue4);
        remoteViews.setViewVisibility(R.id.sec_media_capsule_live_streaming_icon, intValue5);
        int i = StringCompanionObject.$r8$clinit;
        remoteViews.setTextViewText(R.id.sec_media_capsule_playing_speed, String.format("x%.2f", Arrays.copyOf(new Object[]{Float.valueOf(playbackState.getPlaybackSpeed())}, 1)));
        remoteViews.setChronometer(R.id.sec_media_capsule_elapsed_time, elapsedRealtime, null, booleanValue);
        String str = playbackState.getState() == 3 ? "normal" : "low";
        Bundle bundle = this.bundle;
        bundle.putParcelable("capsule_layout", remoteViews);
        bundle.putString("capsule_priority", str);
        updateCapsule();
    }
}
