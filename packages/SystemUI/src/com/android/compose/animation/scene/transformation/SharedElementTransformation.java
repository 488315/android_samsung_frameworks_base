package com.android.compose.animation.scene.transformation;

import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ElementMatcher;
import com.android.compose.animation.scene.transformation.Transformation;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SharedElementTransformation implements Transformation {
    public final ContentKey elevateInContent;
    public final boolean enabled;

    public final class Factory implements Transformation.Factory {
        public final ContentKey elevateInContent;
        public final boolean enabled;
        public final ElementMatcher matcher;

        public Factory(ElementMatcher elementMatcher, boolean z, ContentKey contentKey) {
            this.matcher = elementMatcher;
            this.enabled = z;
            this.elevateInContent = contentKey;
        }

        @Override // com.android.compose.animation.scene.transformation.Transformation.Factory
        public final Transformation create() {
            return new SharedElementTransformation(this.enabled, this.elevateInContent);
        }
    }

    public SharedElementTransformation(boolean z, ContentKey contentKey) {
        this.enabled = z;
        this.elevateInContent = contentKey;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SharedElementTransformation)) {
            return false;
        }
        SharedElementTransformation sharedElementTransformation = (SharedElementTransformation) obj;
        return this.enabled == sharedElementTransformation.enabled && Intrinsics.areEqual(this.elevateInContent, sharedElementTransformation.elevateInContent);
    }

    public final int hashCode() {
        int iHashCode = Boolean.hashCode(this.enabled) * 31;
        ContentKey contentKey = this.elevateInContent;
        return iHashCode + (contentKey == null ? 0 : contentKey.identity.hashCode());
    }

    public final String toString() {
        return "SharedElementTransformation(enabled=" + this.enabled + ", elevateInContent=" + this.elevateInContent + ")";
    }
}
