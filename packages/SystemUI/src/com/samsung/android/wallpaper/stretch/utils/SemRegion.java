package com.samsung.android.wallpaper.stretch.utils;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public class SemRegion extends Region {
    public boolean isMutable;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SemRegion() {
        this.isMutable = true;
    }

    public final void crop(Rect rect) {
        boolean z = this.isMutable;
        if (!z) {
            throw new IllegalStateException("Cannot crop immutable region.");
        }
        if (!z && equals(this)) {
            throw new IllegalStateException("Cannot crop immutable region.");
        }
        Region region = new Region(rect);
        region.op(this, Region.Op.INTERSECT);
        set(region);
    }

    public void fromByteArray(int i, byte[] bArr) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.position(i);
        int i2 = 0;
        boolean z = byteBufferWrap.get() == 0;
        int iRemaining = byteBufferWrap.remaining() / ((z ? 2 : 4) * 4);
        if (z) {
            while (i2 < iRemaining) {
                op(new Rect(byteBufferWrap.getShort(), byteBufferWrap.getShort(), byteBufferWrap.getShort(), byteBufferWrap.getShort()), Region.Op.UNION);
                i2++;
            }
        } else {
            while (i2 < iRemaining) {
                op(new Rect(byteBufferWrap.getInt(), byteBufferWrap.getInt(), byteBufferWrap.getInt(), byteBufferWrap.getInt()), Region.Op.UNION);
                i2++;
            }
        }
    }

    @Override // android.graphics.Region
    public final boolean op(Rect rect, Region region, Region.Op op) {
        if (this.isMutable) {
            return super.op(rect, region, op);
        }
        throw new IllegalStateException("Cannot op immutable region.");
    }

    @Override // android.graphics.Region
    public final boolean set(Rect rect) {
        if (this.isMutable) {
            return super.set(rect);
        }
        throw new IllegalStateException("Cannot set rect immutable region.");
    }

    @Override // android.graphics.Region
    public final void setEmpty() {
        if (!this.isMutable) {
            throw new IllegalStateException("Cannot set Empty immutable region.");
        }
        super.setEmpty();
    }

    @Override // android.graphics.Region
    public final boolean setPath(Path path, Region region) {
        if (this.isMutable) {
            return super.setPath(path, region);
        }
        throw new IllegalStateException("Cannot set Path immutable region.");
    }

    @Override // android.graphics.Region
    public String toString() {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Sem", super.toString());
    }

    @Override // android.graphics.Region
    public final void translate(int i, int i2) {
        if (!this.isMutable) {
            throw new IllegalStateException("Cannot translate immutable region.");
        }
        super.translate(i, i2);
    }

    public SemRegion(Rect rect) {
        super(rect);
        this.isMutable = true;
    }

    @Override // android.graphics.Region
    public final boolean op(Rect rect, Region.Op op) {
        if (this.isMutable) {
            return super.op(rect, op);
        }
        throw new IllegalStateException("Cannot op immutable region.");
    }

    @Override // android.graphics.Region
    public final boolean set(Region region) {
        if (this.isMutable) {
            return super.set(region);
        }
        throw new IllegalStateException("Cannot set region immutable region.");
    }

    @Override // android.graphics.Region
    public final void translate(int i, int i2, Region region) {
        if (this.isMutable) {
            super.translate(i, i2, region);
            return;
        }
        throw new IllegalStateException("Cannot translate immutable region.");
    }

    public SemRegion(Region region) {
        super(region);
        this.isMutable = true;
    }

    public SemRegion(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
        this.isMutable = true;
    }

    @Override // android.graphics.Region
    public final boolean op(Region region, Region region2, Region.Op op) {
        if (this.isMutable) {
            return super.op(region, region2, op);
        }
        throw new IllegalStateException("Cannot op immutable region.");
    }

    @Override // android.graphics.Region
    public final boolean set(int i, int i2, int i3, int i4) {
        if (this.isMutable) {
            return super.set(i, i2, i3, i4);
        }
        throw new IllegalStateException("Cannot set ltrb immutable region.");
    }

    public SemRegion(byte[] bArr) {
        this.isMutable = true;
        fromByteArray(0, bArr);
    }

    @Override // android.graphics.Region
    public final boolean op(Region region, Region.Op op) {
        if (this.isMutable) {
            return super.op(region, op);
        }
        throw new IllegalStateException("Cannot op immutable region.");
    }

    public SemRegion(ArrayList<Rect> arrayList) {
        this.isMutable = true;
        for (Rect rect : (Rect[]) arrayList.toArray(new Rect[0])) {
            op(rect, Region.Op.UNION);
        }
    }

    @Override // android.graphics.Region
    public final boolean op(int i, int i2, int i3, int i4, Region.Op op) {
        if (this.isMutable) {
            return super.op(i, i2, i3, i4, op);
        }
        throw new IllegalStateException("Cannot op immutable region.");
    }
}
