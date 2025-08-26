package com.android.compose.animation.scene;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class Ancestor {
    public final ContentKey inContent;
    public final SceneTransitionLayoutImpl layoutImpl;

    public Ancestor(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, ContentKey contentKey) {
        this.layoutImpl = sceneTransitionLayoutImpl;
        this.inContent = contentKey;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ancestor)) {
            return false;
        }
        Ancestor ancestor = (Ancestor) obj;
        return Intrinsics.areEqual(this.layoutImpl, ancestor.layoutImpl) && Intrinsics.areEqual(this.inContent, ancestor.inContent);
    }

    public final int hashCode() {
        return this.inContent.identity.hashCode() + (this.layoutImpl.hashCode() * 31);
    }

    public final String toString() {
        return "Ancestor(layoutImpl=" + this.layoutImpl + ", inContent=" + this.inContent + ")";
    }
}
