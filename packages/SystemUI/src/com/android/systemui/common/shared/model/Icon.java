package com.android.systemui.common.shared.model;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Icon {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode;
            ContentDescription contentDescription = this.contentDescription;
            int hashCode2 = contentDescription != null ? contentDescription.hashCode() : 0;
            Integer num = this.res;
            if (num != null) {
                i = hashCode2 * 31;
                hashCode = num.hashCode();
            } else {
                i = hashCode2 * 31;
                hashCode = this.drawable.hashCode();
            }
            return hashCode + i;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public /* synthetic */ Icon(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ContentDescription getContentDescription();

    private Icon() {
    }
}
