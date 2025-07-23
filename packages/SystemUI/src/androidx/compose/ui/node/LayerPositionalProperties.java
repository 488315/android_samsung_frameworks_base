package androidx.compose.ui.node;

import androidx.compose.ui.graphics.TransformOrigin;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LayerPositionalProperties {
    public float rotationX;
    public float rotationY;
    public float rotationZ;
    public long transformOrigin;
    public float translationX;
    public float translationY;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float cameraDistance = 8.0f;

    public LayerPositionalProperties() {
        TransformOrigin.Companion.getClass();
        this.transformOrigin = TransformOrigin.Center;
    }
}
