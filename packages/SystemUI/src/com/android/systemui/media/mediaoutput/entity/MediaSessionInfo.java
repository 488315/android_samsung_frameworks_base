package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class MediaSessionInfo implements MediaInfo {
    public static final Companion Companion = new Companion(null);
    public static final MediaSessionInfo empty = new MediaSessionInfo("", null, new ResourceString(R.string.no_media, null, 2, null), null, 0, 0, null, 0, 0, null, null, null, null, 7754, null);
    public final long actions;
    public final ColorScheme appColorScheme;
    public final Painter appIcon;
    public final String artist;
    public final long duration;
    public final String id;
    public final List mediaActions;
    public final String packageName;
    public final int playbackState;
    public final long position;
    public final ColorScheme thumbColorScheme;
    public final ImageBitmap thumbnail;
    public final CharSequence title;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaSessionInfo(String str, String str2, CharSequence charSequence, String str3, long j, long j2, ImageBitmap imageBitmap, int i, long j3, List<MediaAction> list, Painter painter, ColorScheme colorScheme, ColorScheme colorScheme2) {
        this.id = str;
        this.packageName = str2;
        this.title = charSequence;
        this.artist = str3;
        this.duration = j;
        this.position = j2;
        this.thumbnail = imageBitmap;
        this.playbackState = i;
        this.actions = j3;
        this.mediaActions = list;
        this.appIcon = painter;
        this.appColorScheme = colorScheme;
        this.thumbColorScheme = colorScheme2;
    }

    public static MediaSessionInfo copy$default(MediaSessionInfo mediaSessionInfo, String str, String str2, CharSequence charSequence, String str3, long j, long j2, ImageBitmap imageBitmap, int i, long j3, List list, Painter painter, ColorScheme colorScheme, ColorScheme colorScheme2, int i2) {
        String str4 = (i2 & 1) != 0 ? mediaSessionInfo.id : str;
        String str5 = (i2 & 2) != 0 ? mediaSessionInfo.packageName : str2;
        CharSequence charSequence2 = (i2 & 4) != 0 ? mediaSessionInfo.title : charSequence;
        String str6 = (i2 & 8) != 0 ? mediaSessionInfo.artist : str3;
        long j4 = (i2 & 16) != 0 ? mediaSessionInfo.duration : j;
        long j5 = (i2 & 32) != 0 ? mediaSessionInfo.position : j2;
        ImageBitmap imageBitmap2 = (i2 & 64) != 0 ? mediaSessionInfo.thumbnail : imageBitmap;
        int i3 = (i2 & 128) != 0 ? mediaSessionInfo.playbackState : i;
        long j6 = (i2 & 256) != 0 ? mediaSessionInfo.actions : j3;
        List list2 = (i2 & 512) != 0 ? mediaSessionInfo.mediaActions : list;
        Painter painter2 = (i2 & 1024) != 0 ? mediaSessionInfo.appIcon : painter;
        ColorScheme colorScheme3 = (i2 & 2048) != 0 ? mediaSessionInfo.appColorScheme : colorScheme;
        ColorScheme colorScheme4 = (i2 & 4096) != 0 ? mediaSessionInfo.thumbColorScheme : colorScheme2;
        mediaSessionInfo.getClass();
        return new MediaSessionInfo(str4, str5, charSequence2, str6, j4, j5, imageBitmap2, i3, j6, list2, painter2, colorScheme3, colorScheme4);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaSessionInfo)) {
            return false;
        }
        MediaSessionInfo mediaSessionInfo = (MediaSessionInfo) obj;
        return Intrinsics.areEqual(this.id, mediaSessionInfo.id) && Intrinsics.areEqual(this.packageName, mediaSessionInfo.packageName) && Intrinsics.areEqual(this.title, mediaSessionInfo.title) && Intrinsics.areEqual(this.artist, mediaSessionInfo.artist) && this.duration == mediaSessionInfo.duration && this.position == mediaSessionInfo.position && Intrinsics.areEqual(this.thumbnail, mediaSessionInfo.thumbnail) && this.playbackState == mediaSessionInfo.playbackState && this.actions == mediaSessionInfo.actions && Intrinsics.areEqual(this.mediaActions, mediaSessionInfo.mediaActions) && Intrinsics.areEqual(this.appIcon, mediaSessionInfo.appIcon) && Intrinsics.areEqual(this.appColorScheme, mediaSessionInfo.appColorScheme) && Intrinsics.areEqual(this.thumbColorScheme, mediaSessionInfo.thumbColorScheme);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final long getActions() {
        return this.actions;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final ColorScheme getAppColorScheme() {
        return this.appColorScheme;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final Painter getAppIcon() {
        return this.appIcon;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final String getArtist() {
        return this.artist;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo, com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return CollectionsKt___CollectionsKt.plus((Iterable) CollectionsKt__CollectionsKt.listOf(new Pair("artist", this.artist), new Pair("duration", Long.valueOf(this.duration)), new Pair(SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION, Long.valueOf(this.position)), new Pair("playbackState", Integer.valueOf(this.playbackState)), new Pair(SystemUIAnalytics.QPNE_VID_ACTIONS, Long.valueOf(this.actions)), new Pair("mediaActions", this.mediaActions)), (Collection) super.getAttributes());
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final long getDuration() {
        return this.duration;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final String getId() {
        return this.id;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final List getMediaActions() {
        return this.mediaActions;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final String getPackageName() {
        return this.packageName;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final int getPlaybackState() {
        return this.playbackState;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final long getPosition() {
        return this.position;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final ColorScheme getThumbColorScheme() {
        return this.thumbColorScheme;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final ImageBitmap getThumbnail() {
        return this.thumbnail;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final CharSequence getTitle() {
        return this.title;
    }

    public final int hashCode() {
        int m = ControlInfo$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.packageName), 31, this.title);
        String str = this.artist;
        int m2 = Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m((m + (str == null ? 0 : str.hashCode())) * 31, 31, this.duration), 31, this.position);
        ImageBitmap imageBitmap = this.thumbnail;
        int m3 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.mediaActions, Scale$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.playbackState, (m2 + (imageBitmap == null ? 0 : imageBitmap.hashCode())) * 31, 31), 31, this.actions), 31);
        Painter painter = this.appIcon;
        int hashCode = (m3 + (painter == null ? 0 : painter.hashCode())) * 31;
        ColorScheme colorScheme = this.appColorScheme;
        int hashCode2 = (hashCode + (colorScheme == null ? 0 : colorScheme.hashCode())) * 31;
        ColorScheme colorScheme2 = this.thumbColorScheme;
        return hashCode2 + (colorScheme2 != null ? colorScheme2.hashCode() : 0);
    }

    public final String toString() {
        return toLogText();
    }

    public MediaSessionInfo(String str, String str2, CharSequence charSequence, String str3, long j, long j2, ImageBitmap imageBitmap, int i, long j3, List list, Painter painter, ColorScheme colorScheme, ColorScheme colorScheme2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? str : str2, charSequence, (i2 & 8) != 0 ? null : str3, j, j2, (i2 & 64) != 0 ? null : imageBitmap, i, j3, (i2 & 512) != 0 ? EmptyList.INSTANCE : list, (i2 & 1024) != 0 ? null : painter, (i2 & 2048) != 0 ? null : colorScheme, (i2 & 4096) != 0 ? null : colorScheme2);
    }
}
