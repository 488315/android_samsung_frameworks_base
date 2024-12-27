package com.android.systemui.screenshot.scroll;

import android.graphics.ColorSpace;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.media.Image;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ImageTile implements AutoCloseable {
    public static final ColorSpace COLOR_SPACE = ColorSpace.get(ColorSpace.Named.SRGB);
    public final Image mImage;
    public final Rect mLocation;
    public RenderNode mNode;

    public ImageTile(Image image, Rect rect) {
        Objects.requireNonNull(image, "image");
        this.mImage = image;
        Objects.requireNonNull(rect);
        this.mLocation = rect;
        Objects.requireNonNull(image.getHardwareBuffer(), "image must be a hardware image");
    }

    @Override // java.lang.AutoCloseable
    public final synchronized void close() {
        this.mImage.close();
        RenderNode renderNode = this.mNode;
        if (renderNode != null) {
            renderNode.discardDisplayList();
        }
    }

    public final String toString() {
        return "{location=" + this.mLocation + ", source=" + this.mImage + ", buffer=" + this.mImage.getHardwareBuffer() + "}";
    }
}
