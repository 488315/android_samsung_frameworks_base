package com.android.systemui.statusbar.featurepods.media.shared.model;

import android.graphics.drawable.Icon;
import com.android.systemui.media.controls.shared.model.MediaAction;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class MediaControlChipModel {
    public final Icon appIcon;
    public final String appName;
    public final MediaAction playOrPause;
    public final CharSequence songName;

    public MediaControlChipModel(Icon icon, String str, CharSequence charSequence, MediaAction mediaAction) {
        this.appIcon = icon;
        this.appName = str;
        this.songName = charSequence;
        this.playOrPause = mediaAction;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaControlChipModel)) {
            return false;
        }
        MediaControlChipModel mediaControlChipModel = (MediaControlChipModel) obj;
        return Intrinsics.areEqual(this.appIcon, mediaControlChipModel.appIcon) && Intrinsics.areEqual(this.appName, mediaControlChipModel.appName) && Intrinsics.areEqual(this.songName, mediaControlChipModel.songName) && Intrinsics.areEqual(this.playOrPause, mediaControlChipModel.playOrPause);
    }

    public final int hashCode() {
        Icon icon = this.appIcon;
        int iHashCode = (icon == null ? 0 : icon.hashCode()) * 31;
        String str = this.appName;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        CharSequence charSequence = this.songName;
        int iHashCode3 = (iHashCode2 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        MediaAction mediaAction = this.playOrPause;
        return iHashCode3 + (mediaAction != null ? mediaAction.hashCode() : 0);
    }

    public final String toString() {
        return "MediaControlChipModel(appIcon=" + this.appIcon + ", appName=" + this.appName + ", songName=" + ((Object) this.songName) + ", playOrPause=" + this.playOrPause + ")";
    }
}
