package com.android.systemui.screenshot.scroll;

import android.graphics.ColorSpace;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.media.Image;
import java.util.Objects;

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
