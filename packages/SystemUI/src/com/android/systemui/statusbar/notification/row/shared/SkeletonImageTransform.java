package com.android.systemui.statusbar.notification.row.shared;

import android.content.Context;
import com.android.internal.util.ContrastColorUtil;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class SkeletonImageTransform extends ImageModelProvider$ImageTransform {
    public final ContrastColorUtil contrastColorUtil;

    /* JADX WARN: Illegal instructions before constructor call */
    public SkeletonImageTransform(Context context) {
        final String str = "Skeleton";
        new Object(str) { // from class: com.android.systemui.statusbar.notification.row.shared.ImageModelProvider$ImageTransform
            public final String key;

            {
                this.key = str;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || !(obj instanceof ImageModelProvider$ImageTransform)) {
                    return false;
                }
                return Intrinsics.areEqual(this.key, ((ImageModelProvider$ImageTransform) obj).key);
            }

            public final int hashCode() {
                return this.key.hashCode();
            }
        };
        this.contrastColorUtil = ContrastColorUtil.getInstance(context);
    }
}
