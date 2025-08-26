package com.samsung.android.wallpaper.stretch.utils;

import android.graphics.Rect;
import android.graphics.Region;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class BgRegion extends SemRegion {
    public int contentsType;
    public int version;

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

    public BgRegion(int i) {
        this.version = 1;
        this.contentsType = i;
    }

    @Override // com.samsung.android.wallpaper.stretch.utils.SemRegion
    public final void fromByteArray(int i, byte[] bArr) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        int i2 = byteBufferWrap.getInt();
        this.version = byteBufferWrap.getInt();
        this.contentsType = byteBufferWrap.getInt();
        super.fromByteArray(i2, bArr);
    }

    @Override // com.samsung.android.wallpaper.stretch.utils.SemRegion, android.graphics.Region
    public final String toString() {
        int i = this.version;
        int i2 = this.contentsType;
        Rect bounds = getBounds();
        int iWidth = getBounds().width();
        int iHeight = getBounds().height();
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "BgRegion version=", ", contentsType=", ", bounds=");
        sbM.append(bounds);
        sbM.append(", size=");
        sbM.append(iWidth);
        sbM.append("x");
        sbM.append(iHeight);
        return sbM.toString();
    }

    public BgRegion(int i, Region region) {
        super(region);
        this.version = 1;
        this.contentsType = i;
    }

    public BgRegion(int i, Rect rect) {
        super(rect);
        this.version = 1;
        this.contentsType = i;
    }

    public BgRegion(int i, int i2, int i3, int i4, int i5) {
        super(i2, i3, i4, i5);
        this.version = 1;
        this.contentsType = i;
    }

    public BgRegion(byte[] bArr) {
        this.version = 1;
        fromByteArray(0, bArr);
        this.isMutable = false;
    }

    public BgRegion(int i, ArrayList<Rect> arrayList) {
        super(arrayList);
        this.version = 1;
        this.contentsType = i;
        this.isMutable = false;
    }
}
