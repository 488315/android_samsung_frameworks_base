package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.monet.ColorScheme;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoMediaSessionInfo implements MediaInfo {
    public final Lazy actions$delegate;
    public final ColorScheme appColorScheme;
    public final Painter appIcon;
    public final Lazy duration$delegate;
    public final String id;
    public final List mediaActions;
    public final String packageName;
    public final Lazy playbackState$delegate;
    public final Lazy position$delegate;
    public final ColorScheme thumbColorScheme;
    public final CharSequence title;

    public NoMediaSessionInfo(String str, String str2, CharSequence charSequence, List<MediaAction> list, Painter painter, ColorScheme colorScheme, ColorScheme colorScheme2) {
        this.id = str;
        this.packageName = str2;
        this.title = charSequence;
        this.mediaActions = list;
        this.appIcon = painter;
        this.appColorScheme = colorScheme;
        this.thumbColorScheme = colorScheme2;
        this.duration$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.entity.NoMediaSessionInfo$duration$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return 0L;
            }
        });
        this.position$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.entity.NoMediaSessionInfo$position$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return 0L;
            }
        });
        this.playbackState$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.entity.NoMediaSessionInfo$playbackState$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return 0;
            }
        });
        this.actions$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.entity.NoMediaSessionInfo$actions$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return 0L;
            }
        });
    }

    public static NoMediaSessionInfo copy$default(NoMediaSessionInfo noMediaSessionInfo, String str, CharSequence charSequence, List list, Painter painter, ColorScheme colorScheme, int i) {
        String str2 = noMediaSessionInfo.id;
        if ((i & 2) != 0) {
            str = noMediaSessionInfo.packageName;
        }
        String str3 = str;
        if ((i & 4) != 0) {
            charSequence = noMediaSessionInfo.title;
        }
        CharSequence charSequence2 = charSequence;
        if ((i & 8) != 0) {
            list = noMediaSessionInfo.mediaActions;
        }
        List list2 = list;
        if ((i & 16) != 0) {
            painter = noMediaSessionInfo.appIcon;
        }
        Painter painter2 = painter;
        if ((i & 32) != 0) {
            colorScheme = noMediaSessionInfo.appColorScheme;
        }
        ColorScheme colorScheme2 = noMediaSessionInfo.thumbColorScheme;
        noMediaSessionInfo.getClass();
        return new NoMediaSessionInfo(str2, str3, charSequence2, list2, painter2, colorScheme, colorScheme2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NoMediaSessionInfo)) {
            return false;
        }
        NoMediaSessionInfo noMediaSessionInfo = (NoMediaSessionInfo) obj;
        return Intrinsics.areEqual(this.id, noMediaSessionInfo.id) && Intrinsics.areEqual(this.packageName, noMediaSessionInfo.packageName) && Intrinsics.areEqual(this.title, noMediaSessionInfo.title) && Intrinsics.areEqual(this.mediaActions, noMediaSessionInfo.mediaActions) && Intrinsics.areEqual(this.appIcon, noMediaSessionInfo.appIcon) && Intrinsics.areEqual(this.appColorScheme, noMediaSessionInfo.appColorScheme) && Intrinsics.areEqual(this.thumbColorScheme, noMediaSessionInfo.thumbColorScheme);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final long getActions() {
        return ((Number) this.actions$delegate.getValue()).longValue();
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
        return null;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo, com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(new Pair("mediaActions", this.mediaActions)), (Collection) super.getAttributes());
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final long getDuration() {
        return ((Number) this.duration$delegate.getValue()).longValue();
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
        return ((Number) this.playbackState$delegate.getValue()).intValue();
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final long getPosition() {
        return ((Number) this.position$delegate.getValue()).longValue();
    }

    @Override // com.android.systemui.media.mediaoutput.entity.MediaInfo
    public final ColorScheme getThumbColorScheme() {
        return this.thumbColorScheme;
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
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.mediaActions, ControlInfo$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.packageName), 31, this.title), 31);
        Painter painter = this.appIcon;
        int hashCode = (m + (painter == null ? 0 : painter.hashCode())) * 31;
        ColorScheme colorScheme = this.appColorScheme;
        int hashCode2 = (hashCode + (colorScheme == null ? 0 : colorScheme.hashCode())) * 31;
        ColorScheme colorScheme2 = this.thumbColorScheme;
        return hashCode2 + (colorScheme2 != null ? colorScheme2.hashCode() : 0);
    }

    public final String toString() {
        return toLogText();
    }

    public NoMediaSessionInfo(String str, String str2, CharSequence charSequence, List list, Painter painter, ColorScheme colorScheme, ColorScheme colorScheme2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? str : str2, (i & 4) != 0 ? new ResourceString(R.string.no_media, null, 2, null) : charSequence, (i & 8) != 0 ? EmptyList.INSTANCE : list, (i & 16) != 0 ? null : painter, (i & 32) != 0 ? null : colorScheme, (i & 64) == 0 ? colorScheme2 : null);
    }
}
