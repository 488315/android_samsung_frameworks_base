package com.google.zxing;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Dimension {
    public final int height;
    public final int width;

    public Dimension(int i, int i2) {
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException();
        }
        this.width = i;
        this.height = i2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Dimension)) {
            return false;
        }
        Dimension dimension = (Dimension) obj;
        return this.width == dimension.width && this.height == dimension.height;
    }

    public final int hashCode() {
        return (this.width * 32713) + this.height;
    }

    public final String toString() {
        return this.width + "x" + this.height;
    }
}
