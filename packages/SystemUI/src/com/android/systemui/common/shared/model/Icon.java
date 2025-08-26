package com.android.systemui.common.shared.model;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class Icon {

    public final class Loaded extends Icon {
        public final ContentDescription contentDescription;
        public final Drawable drawable;
        public final Integer res;

        public Loaded(Drawable drawable, ContentDescription contentDescription) {
            this(drawable, contentDescription, null, 4, null);
        }

        public final boolean equals(Object obj) {
            Loaded loaded = obj instanceof Loaded ? (Loaded) obj : null;
            if (loaded == null) {
                return false;
            }
            ContentDescription contentDescription = this.contentDescription;
            ContentDescription contentDescription2 = loaded.contentDescription;
            Integer num = loaded.res;
            Integer num2 = this.res;
            return (num2 == null || num == null) ? Intrinsics.areEqual(num2, num) && Intrinsics.areEqual(this.drawable, loaded.drawable) && Intrinsics.areEqual(contentDescription, contentDescription2) : Intrinsics.areEqual(num2, num) && Intrinsics.areEqual(contentDescription, contentDescription2);
        }

        @Override // com.android.systemui.common.shared.model.Icon
        public final ContentDescription getContentDescription() {
            return this.contentDescription;
        }

        public final int hashCode() {
            int i;
            int iHashCode;
            ContentDescription contentDescription = this.contentDescription;
            int iHashCode2 = contentDescription != null ? contentDescription.hashCode() : 0;
            Integer num = this.res;
            if (num != null) {
                i = iHashCode2 * 31;
                iHashCode = num.hashCode();
            } else {
                i = iHashCode2 * 31;
                iHashCode = this.drawable.hashCode();
            }
            return iHashCode + i;
        }

        public final String toString() {
            return "Loaded(drawable=" + this.drawable + ", contentDescription=" + this.contentDescription + ", res=" + this.res + ")";
        }

        public /* synthetic */ Loaded(Drawable drawable, ContentDescription contentDescription, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(drawable, contentDescription, (i & 4) != 0 ? null : num);
        }

        public Loaded(Drawable drawable, ContentDescription contentDescription, Integer num) {
            super(null);
            this.drawable = drawable;
            this.contentDescription = contentDescription;
            this.res = num;
        }
    }

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
            int iHashCode = Integer.hashCode(this.res) * 31;
            ContentDescription contentDescription = this.contentDescription;
            return iHashCode + (contentDescription == null ? 0 : contentDescription.hashCode());
        }

        public final String toString() {
            return "Resource(res=" + this.res + ", contentDescription=" + this.contentDescription + ")";
        }
    }

    public /* synthetic */ Icon(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ContentDescription getContentDescription();

    private Icon() {
    }
}
