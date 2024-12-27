package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.monet.ColorScheme;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaDeviceInfo implements MediaInfo {
    public static final Companion Companion = new Companion(null);
    public static final Lazy empty$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.entity.MediaDeviceInfo$Companion$empty$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new MediaDeviceInfo("-", new ResourceString(R.string.content_is_playing, null, 2, null), null, null, 12, null);
        }
    });
    public final Painter appIcon;
    public final String artist;
    public final String id;
    public final EmptyList mediaActions;
    public final String packageName;
    public final CharSequence title;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaDeviceInfo(String str, CharSequence charSequence, String str2, Painter painter) {
        this.id = str;
        this.title = charSequence;
        this.artist = str2;
        this.appIcon = painter;
        this.packageName = str;
        this.mediaActions = EmptyList.INSTANCE;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaDeviceInfo)) {
            return false;
        }
        MediaDeviceInfo mediaDeviceInfo = (MediaDeviceInfo) obj;
        return Intrinsics.areEqual(this.id, mediaDeviceInfo.id) && Intrinsics.areEqual(this.title, mediaDeviceInfo.title) && Intrinsics.areEqual(this.artist, mediaDeviceInfo.artist) && Intrinsics.areEqual(this.appIcon, mediaDeviceInfo.appIcon);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final long getActions() {
        return 0L;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final ColorScheme getAppColorScheme() {
        return null;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final Painter getAppIcon() {
        return this.appIcon;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final String getArtist() {
        return this.artist;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final long getDuration() {
        return 0L;
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
        return 0;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final long getPosition() {
        return 0L;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final ColorScheme getThumbColorScheme() {
        return null;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final ImageBitmap getThumbnail() {
        return null;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final CharSequence getTitle() {
        return this.title;
    }

    public final int hashCode() {
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ControlInfo$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.title), 31, this.artist);
        Painter painter = this.appIcon;
        return m + (painter == null ? 0 : painter.hashCode());
    }

    public final String toString() {
        return toLogText();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MediaDeviceInfo(java.lang.String r1, java.lang.CharSequence r2, java.lang.String r3, androidx.compose.ui.graphics.painter.Painter r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
        /*
            r0 = this;
            r6 = r5 & 4
            if (r6 == 0) goto L6
            java.lang.String r3 = ""
        L6:
            r5 = r5 & 8
            if (r5 == 0) goto L1d
            com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter$Companion r4 = com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter.Companion
            com.android.systemui.media.mediaoutput.icons.Icons$Feature r5 = com.android.systemui.media.mediaoutput.icons.Icons$Feature.INSTANCE
            kotlin.Lazy r5 = com.android.systemui.media.mediaoutput.icons.feature.IcTvKt.IcTv$delegate
            java.lang.Object r5 = r5.getValue()
            androidx.compose.ui.graphics.vector.ImageVector r5 = (androidx.compose.ui.graphics.vector.ImageVector) r5
            r4.getClass()
            com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter r4 = com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter.Companion.toConverter(r5)
        L1d:
            r0.<init>(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.entity.MediaDeviceInfo.<init>(java.lang.String, java.lang.CharSequence, java.lang.String, androidx.compose.ui.graphics.painter.Painter, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
