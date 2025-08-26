package com.android.compose.animation.scene.content;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.Element;
import com.android.compose.ui.graphics.ContainerNode;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class ContentElement extends ModifierNodeElement<ContentNode> {
    public final Content content;
    public final boolean isElevationPossible;
    public final boolean isInvisible;

    public ContentElement(Content content, boolean z, boolean z2) {
        this.content = content;
        this.isElevationPossible = z;
        this.isInvisible = z2;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ContentNode(this.content, this.isElevationPossible, this.isInvisible);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ContentElement)) {
            return false;
        }
        ContentElement contentElement = (ContentElement) obj;
        return Intrinsics.areEqual(this.content, contentElement.content) && this.isElevationPossible == contentElement.isElevationPossible && this.isInvisible == contentElement.isInvisible;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isInvisible) + TransitionData$$ExternalSyntheticOutline0.m(this.content.hashCode() * 31, 31, this.isElevationPossible);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ContentElement(content=");
        sb.append(this.content);
        sb.append(", isElevationPossible=");
        sb.append(this.isElevationPossible);
        sb.append(", isInvisible=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isInvisible, ")");
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ContainerNode containerNode;
        ContentNode contentNode = (ContentNode) node;
        Content content = contentNode.content;
        Content content2 = this.content;
        if (!Intrinsics.areEqual(content2, content)) {
            Content content3 = contentNode.content;
            Element.Companion.getClass();
            long j = Element.SizeUnspecified;
            ((SnapshotMutableStateImpl) content3.targetSize$delegate).setValue(IntSize.m861boximpl(j));
            contentNode.content = content2;
        }
        boolean zAreEqual = Intrinsics.areEqual(content2, contentNode.content);
        boolean z = this.isElevationPossible;
        if (!zAreEqual || z != contentNode.isElevationPossible) {
            contentNode.isElevationPossible = z;
            ContainerNode containerNode2 = contentNode.containerDelegate;
            if (containerNode2 != null) {
                contentNode.undelegate(containerNode2);
            }
            if (z) {
                containerNode = new ContainerNode(contentNode.content.containerState);
                contentNode.delegate(containerNode);
            } else {
                containerNode = null;
            }
            contentNode.containerDelegate = containerNode;
        }
        contentNode.isInvisible = this.isInvisible;
    }
}
