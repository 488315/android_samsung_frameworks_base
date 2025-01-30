package com.android.systemui.media.taptotransfer.common;

import android.graphics.drawable.Drawable;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface MediaTttIcon {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("Resource(res="), this.res, ")");
        }
    }
}
