package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.Map;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class Configuration {

    @SerializedName("music_share_enabled")
    private final boolean musicShareEnabled;

    @SerializedName("playback_preference_default")
    private final boolean playbackPreferenceDefault;

    @SerializedName("playback_preferences")
    private final Map<String, Boolean> playbackPreferences;

    @SerializedName("support_spotify_media_provider")
    private final boolean supportSpotifyMediaProvider;
    public long updateTime;

    public Configuration() {
        this(false, null, false, false, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Configuration)) {
            return false;
        }
        Configuration configuration = (Configuration) obj;
        return this.playbackPreferenceDefault == configuration.playbackPreferenceDefault && Intrinsics.areEqual(this.playbackPreferences, configuration.playbackPreferences) && this.supportSpotifyMediaProvider == configuration.supportSpotifyMediaProvider && this.musicShareEnabled == configuration.musicShareEnabled;
    }

    public final Map getPlaybackPreferences() {
        return this.playbackPreferences;
    }

    public final boolean getSupportSpotifyMediaProvider() {
        return this.supportSpotifyMediaProvider;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.musicShareEnabled) + TransitionData$$ExternalSyntheticOutline0.m((this.playbackPreferences.hashCode() + (Boolean.hashCode(this.playbackPreferenceDefault) * 31)) * 31, 31, this.supportSpotifyMediaProvider);
    }

    public final String toString() {
        return "Configuration(playbackPreferenceDefault=" + this.playbackPreferenceDefault + ", playbackPreferences=" + this.playbackPreferences + ", supportSpotifyMediaProvider=" + this.supportSpotifyMediaProvider + ", musicShareEnabled=" + this.musicShareEnabled + ")";
    }

    public Configuration(boolean z, Map<String, Boolean> map, boolean z2, boolean z3) {
        this.playbackPreferenceDefault = z;
        this.playbackPreferences = map;
        this.supportSpotifyMediaProvider = z2;
        this.musicShareEnabled = z3;
    }

    public Configuration(boolean z, Map map, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        z = (i & 1) != 0 ? false : z;
        if ((i & 2) != 0) {
            Pair pair = new Pair("com.spotify.music", Boolean.TRUE);
            map = Collections.singletonMap(pair.getFirst(), pair.getSecond());
        }
        this(z, map, (i & 4) != 0 ? true : z2, (i & 8) != 0 ? true : z3);
    }
}
