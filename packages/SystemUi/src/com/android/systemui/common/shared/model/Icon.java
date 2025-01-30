package com.android.systemui.common.shared.model;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Icon {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Loaded extends Icon {
        public final ContentDescription contentDescription;
        public final Drawable drawable;

        public Loaded(Drawable drawable, ContentDescription contentDescription) {
            super(null);
            this.drawable = drawable;
            this.contentDescription = contentDescription;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Loaded)) {
                return false;
            }
            Loaded loaded = (Loaded) obj;
            return Intrinsics.areEqual(this.drawable, loaded.drawable) && Intrinsics.areEqual(this.contentDescription, loaded.contentDescription);
        }

        @Override // com.android.systemui.common.shared.model.Icon
        public final ContentDescription getContentDescription() {
            return this.contentDescription;
        }

        public final int hashCode() {
            int hashCode = this.drawable.hashCode() * 31;
            ContentDescription contentDescription = this.contentDescription;
            return hashCode + (contentDescription == null ? 0 : contentDescription.hashCode());
        }

        public final String toString() {
            return "Loaded(drawable=" + this.drawable + ", contentDescription=" + this.contentDescription + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Resource extends Icon {
        public final ContentDescription contentDescription;
        public final int res;

        public Resource(int i, ContentDescription contentDescription) {
            super(null);
            this.res = i;
            this.contentDescription = contentDescription;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Resource)) {
                return false;
            }
            Resource resource = (Resource) obj;
            return this.res == resource.res && Intrinsics.areEqual(this.contentDescription, resource.contentDescription);
        }

        @Override // com.android.systemui.common.shared.model.Icon
        public final ContentDescription getContentDescription() {
            return this.contentDescription;
        }

        public final int hashCode() {
            int hashCode = Integer.hashCode(this.res) * 31;
            ContentDescription contentDescription = this.contentDescription;
            return hashCode + (contentDescription == null ? 0 : contentDescription.hashCode());
        }

        public final String toString() {
            return "Resource(res=" + this.res + ", contentDescription=" + this.contentDescription + ")";
        }
    }

    private Icon() {
    }

    public /* synthetic */ Icon(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ContentDescription getContentDescription();
}
