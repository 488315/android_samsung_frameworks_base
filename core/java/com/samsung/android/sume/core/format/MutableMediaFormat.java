package com.samsung.android.sume.core.format;

import android.graphics.Rect;

import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.types.CodecType;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.ColorSpace;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.FlipType;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.core.types.SplitType;

/* loaded from: classes6.dex */
public interface MutableMediaFormat extends MediaFormat, Copyable<MutableMediaFormat> {
    MutableMediaFormat set(MediaFilter.Option option);

    MutableMediaFormat set(String str, Object obj);

    MutableMediaFormat setChannels(int i);

    MutableMediaFormat setCodecType(CodecType codecType);

    MutableMediaFormat setCols(int i);

    MutableMediaFormat setCropRect(Rect rect);

    MutableMediaFormat setDataType(DataType dataType);

    MutableMediaFormat setFlipType(FlipType flipType);

    MutableMediaFormat setMediaType(MediaType mediaType);

    MutableMediaFormat setRotation(int i);

    MutableMediaFormat setRows(int i);

    MutableMediaFormat setShape(Shape shape);

    MutableMediaFormat setSplitType(SplitType splitType);

    default MutableMediaFormat setDimension(int width, int height) {
        setCols(width);
        setRows(height);
        return this;
    }

    default MutableMediaFormat setColorFormat(ColorFormat colorFormat) {
        throw new UnsupportedOperationException("not support for " + getMediaType());
    }

    default MutableMediaFormat setColorSpace(ColorSpace colorSpace) {
        throw new UnsupportedOperationException("not support for " + getMediaType());
    }

    default MediaFormat toMediaFormat() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    static MutableMediaFormat of(MediaType mediaType, Object... args) {
        return MediaFormat.mutableOf(mediaType, args);
    }
}
