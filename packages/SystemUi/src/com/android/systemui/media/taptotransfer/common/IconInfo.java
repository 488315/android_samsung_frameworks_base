package com.android.systemui.media.taptotransfer.common;

import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.media.taptotransfer.common.MediaTttIcon;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        Integer num = (i & 4) != 0 ? iconInfo.tint : null;
        if ((i & 8) != 0) {
            z = iconInfo.isAppIcon;
        }
        return new IconInfo(loaded3, loaded4, num, z);
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.icon.hashCode() + (this.contentDescription.hashCode() * 31)) * 31;
        Integer num = this.tint;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        boolean z = this.isAppIcon;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode2 + i;
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
