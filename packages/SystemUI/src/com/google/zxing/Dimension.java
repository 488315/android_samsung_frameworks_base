package com.google.zxing;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
        if (obj instanceof Dimension) {
            Dimension dimension = (Dimension) obj;
            if (this.width == dimension.width && this.height == dimension.height) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (this.width * 32713) + this.height;
    }

    public final String toString() {
        return this.width + "x" + this.height;
    }
}
