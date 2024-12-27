package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.monet.ColorScheme;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

public interface MediaInfo extends EntityString {
    long getActions();

    ColorScheme getAppColorScheme();

    Painter getAppIcon();

    String getArtist();

    @Override // com.android.systemui.media.mediaoutput.entity.EntityString
    default List getAttributes() {
        return CollectionsKt__CollectionsKt.listOf(new Pair("id", getId()), new Pair(UniversalCredentialUtil.AGENT_TITLE, getTitle()));
    }

    long getDuration();

    String getId();

    List getMediaActions();

    String getPackageName();

    int getPlaybackState();

    long getPosition();

    ColorScheme getThumbColorScheme();

    ImageBitmap getThumbnail();

    CharSequence getTitle();

    default boolean isInvalidAction() {
        return ((isSupportAction(4L) | isSupportAction(2L)) || isSupportAction(512L) || isSupportAction(32L) || isSupportAction(16L)) ? false : true;
    }

    default boolean isSame(String str) {
        return Intrinsics.areEqual(getId(), str) || Intrinsics.areEqual(getPackageName(), str);
    }

    default boolean isSupportAction(long j) {
        return (getActions() & j) != 0;
    }
}
