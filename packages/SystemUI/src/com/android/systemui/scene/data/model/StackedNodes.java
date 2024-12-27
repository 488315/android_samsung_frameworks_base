package com.android.systemui.scene.data.model;

import com.android.compose.animation.scene.SceneKey;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StackedNodes implements SceneStack {
    public final SceneKey head;
    public final SceneStack tail;

    public StackedNodes(SceneKey sceneKey, SceneStack sceneStack) {
        this.head = sceneKey;
        this.tail = sceneStack;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StackedNodes)) {
            return false;
        }
        StackedNodes stackedNodes = (StackedNodes) obj;
        return Intrinsics.areEqual(this.head, stackedNodes.head) && Intrinsics.areEqual(this.tail, stackedNodes.tail);
    }

    public final int hashCode() {
        return this.tail.hashCode() + (this.head.identity.hashCode() * 31);
    }

    public final String toString() {
        return "StackedNodes(head=" + this.head + ", tail=" + this.tail + ")";
    }
}
