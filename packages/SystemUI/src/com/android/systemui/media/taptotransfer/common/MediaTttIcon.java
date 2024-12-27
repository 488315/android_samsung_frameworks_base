package com.android.systemui.media.taptotransfer.common;

import android.graphics.drawable.Drawable;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public interface MediaTttIcon {

    public final class Loaded implements MediaTttIcon {
        public final Drawable drawable;

        public Loaded(Drawable drawable) {
            this.drawable = drawable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Loaded) && Intrinsics.areEqual(this.drawable, ((Loaded) obj).drawable);
        }

        public final int hashCode() {
            return this.drawable.hashCode();
        }

        public final String toString() {
            return "Loaded(drawable=" + this.drawable + ")";
        }
    }

    public final class Resource implements MediaTttIcon {
        public final int res;

        public Resource(int i) {
            this.res = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Resource) && this.res == ((Resource) obj).res;
        }

        public final int hashCode() {
            return Integer.hashCode(this.res);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.res, ")", new StringBuilder("Resource(res="));
        }
    }
}
