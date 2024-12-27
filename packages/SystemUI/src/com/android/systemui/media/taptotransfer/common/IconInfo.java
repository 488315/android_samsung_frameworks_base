package com.android.systemui.media.taptotransfer.common;

import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.media.taptotransfer.common.MediaTttIcon;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class IconInfo {
    public final ContentDescription contentDescription;
    public final MediaTttIcon icon;
    public final boolean isAppIcon;
    public final Integer tint;

    public IconInfo(ContentDescription contentDescription, MediaTttIcon mediaTttIcon, Integer num, boolean z) {
        this.contentDescription = contentDescription;
        this.icon = mediaTttIcon;
        this.tint = num;
        this.isAppIcon = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.common.shared.model.ContentDescription] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.media.taptotransfer.common.MediaTttIcon] */
    public static IconInfo copy$default(IconInfo iconInfo, ContentDescription.Loaded loaded, MediaTttIcon.Loaded loaded2, boolean z, int i) {
        ContentDescription.Loaded loaded3 = loaded;
        if ((i & 1) != 0) {
            loaded3 = iconInfo.contentDescription;
        }
        MediaTttIcon.Loaded loaded4 = loaded2;
        if ((i & 2) != 0) {
            loaded4 = iconInfo.icon;
        }
        if ((i & 8) != 0) {
            z = iconInfo.isAppIcon;
        }
        return new IconInfo(loaded3, loaded4, iconInfo.tint, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IconInfo)) {
            return false;
        }
        IconInfo iconInfo = (IconInfo) obj;
        return Intrinsics.areEqual(this.contentDescription, iconInfo.contentDescription) && Intrinsics.areEqual(this.icon, iconInfo.icon) && Intrinsics.areEqual(this.tint, iconInfo.tint) && this.isAppIcon == iconInfo.isAppIcon;
    }

    public final int hashCode() {
        int hashCode = (this.icon.hashCode() + (this.contentDescription.hashCode() * 31)) * 31;
        Integer num = this.tint;
        return Boolean.hashCode(this.isAppIcon) + ((hashCode + (num == null ? 0 : num.hashCode())) * 31);
    }

    public final String toString() {
        return "IconInfo(contentDescription=" + this.contentDescription + ", icon=" + this.icon + ", tint=" + this.tint + ", isAppIcon=" + this.isAppIcon + ")";
    }

    public final TintedIcon toTintedIcon() {
        Icon resource;
        MediaTttIcon mediaTttIcon = this.icon;
        boolean z = mediaTttIcon instanceof MediaTttIcon.Loaded;
        ContentDescription contentDescription = this.contentDescription;
        if (z) {
            resource = new Icon.Loaded(((MediaTttIcon.Loaded) mediaTttIcon).drawable, contentDescription);
        } else {
            if (!(mediaTttIcon instanceof MediaTttIcon.Resource)) {
                throw new NoWhenBranchMatchedException();
            }
            resource = new Icon.Resource(((MediaTttIcon.Resource) mediaTttIcon).res, contentDescription);
        }
        return new TintedIcon(resource, this.tint);
    }
}
