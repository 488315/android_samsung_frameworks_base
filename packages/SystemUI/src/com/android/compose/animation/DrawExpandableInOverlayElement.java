package com.android.compose.animation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.platform.ComposeView;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DrawExpandableInOverlayElement extends ModifierNodeElement<DrawExpandableInOverlayNode> {
    public final GraphicsLayer contentGraphicsLayer;
    public final ExpandableControllerImpl controller;
    public final ComposeView overlayComposeView;

    public DrawExpandableInOverlayElement(ComposeView composeView, ExpandableControllerImpl expandableControllerImpl, GraphicsLayer graphicsLayer) {
        this.overlayComposeView = composeView;
        this.controller = expandableControllerImpl;
        this.contentGraphicsLayer = graphicsLayer;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new DrawExpandableInOverlayNode(this.overlayComposeView, this.controller, this.contentGraphicsLayer);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DrawExpandableInOverlayElement)) {
            return false;
        }
        DrawExpandableInOverlayElement drawExpandableInOverlayElement = (DrawExpandableInOverlayElement) obj;
        return Intrinsics.areEqual(this.overlayComposeView, drawExpandableInOverlayElement.overlayComposeView) && Intrinsics.areEqual(this.controller, drawExpandableInOverlayElement.controller) && Intrinsics.areEqual(this.contentGraphicsLayer, drawExpandableInOverlayElement.contentGraphicsLayer);
    }

    public final int hashCode() {
        return this.contentGraphicsLayer.hashCode() + ((this.controller.hashCode() + (this.overlayComposeView.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "DrawExpandableInOverlayElement(overlayComposeView=" + this.overlayComposeView + ", controller=" + this.controller + ", contentGraphicsLayer=" + this.contentGraphicsLayer + ")";
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        DrawExpandableInOverlayNode drawExpandableInOverlayNode = (DrawExpandableInOverlayNode) node;
        if (Intrinsics.areEqual(drawExpandableInOverlayNode.controller.currentNodeInOverlay, drawExpandableInOverlayNode)) {
            drawExpandableInOverlayNode.controller.currentNodeInOverlay = null;
        }
        ExpandableControllerImpl expandableControllerImpl = this.controller;
        drawExpandableInOverlayNode.controller = expandableControllerImpl;
        expandableControllerImpl.currentNodeInOverlay = drawExpandableInOverlayNode;
        drawExpandableInOverlayNode.composeViewLocationOnScreen = this.overlayComposeView.getLocationOnScreen();
        drawExpandableInOverlayNode.contentGraphicsLayer = this.contentGraphicsLayer;
    }
}
