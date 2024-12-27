package com.samsung.android.sume.core.format;

import android.graphics.Rect;
import com.samsung.android.sume.core.types.CodecType;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.ColorSpace;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.FlipType;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.core.types.SplitType;
import java.util.List;

/* loaded from: classes6.dex */
class StapleMediaFormat implements MediaFormat {
    MutableMediaFormat impl;

    public StapleMediaFormat(MutableMediaFormat mutableMediaFormat) {
        this.impl = mutableMediaFormat;
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public MediaType getMediaType() {
        return this.impl.getMediaType();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public DataType getDataType() {
        return this.impl.getDataType();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public Shape getShape() {
        return this.impl.getShape();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getBatch() {
        return this.impl.getBatch();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getRows() {
        return this.impl.getRows();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getCols() {
        return this.impl.getCols();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getChannels() {
        return this.impl.getChannels();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getRotation() {
        return this.impl.getRotation();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public Rect getCropRect() {
        return this.impl.getCropRect();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public SplitType getSplitType() {
        return this.impl.getSplitType();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public FlipType getFlipType() {
        return this.impl.getFlipType();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public CodecType getCodecType() {
        return this.impl.getCodecType();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public ColorFormat getColorFormat() {
        return this.impl.getColorFormat();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public ColorSpace getColorSpace() {
        return this.impl.getColorSpace();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public float bytePerSample() {
        return this.impl.bytePerSample();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public float bytePerPixel() {
        return this.impl.bytePerPixel();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public long size() {
        return this.impl.size();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public List<? extends MediaFormat> getPlanesFormat() {
        return this.impl.getPlanesFormat();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public <T> T get(String str) {
        return (T) this.impl.get(str);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public <T> T get(String str, T t) {
        return (T) this.impl.get(str, t);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public boolean contains(String key) {
        return this.impl.contains(key);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public boolean containsAnyOf(String... keys) {
        return this.impl.containsAnyOf(keys);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public boolean containsAllOf(String... keys) {
        return this.impl.containsAllOf(keys);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public boolean checkTypeOf(String key, Class<?> clazz) {
        return this.impl.checkTypeOf(key, clazz);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public <T> T remove(String str) {
        return (T) this.impl.remove(str);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public MutableMediaFormat toMutableFormat() {
        return this.impl.deepCopy2();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public String contentToString() {
        return this.impl.contentToString();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public String contentToString(Object obj) {
        return this.impl.contentToString(obj);
    }

    public String toString() {
        return contentToString(this);
    }
}
