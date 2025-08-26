package com.samsung.android.sume.core.format;

import android.graphics.Rect;
import com.samsung.android.sume.core.types.CodecType;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.ColorSpace;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.FlipType;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.core.types.SplitType;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public interface MediaFormat extends Serializable, Cloneable {
    public static final int EXIF = 1;
    public static final int GAINMAP = 3;
    public static final int ICC = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface MetadataKey {
    }

    float bytePerPixel();

    float bytePerSample();

    boolean checkTypeOf(String str, Class<?> cls);

    boolean contains(String str);

    boolean containsAllOf(String... strArr);

    boolean containsAnyOf(String... strArr);

    String contentToString();

    String contentToString(Object obj);

    <T> T get(String str);

    <T> T get(String str, T t);

    int getBatch();

    int getChannels();

    CodecType getCodecType();

    int getCols();

    Rect getCropRect();

    DataType getDataType();

    FlipType getFlipType();

    MediaType getMediaType();

    List<? extends MediaFormat> getPlanesFormat();

    int getRotation();

    int getRows();

    Shape getShape();

    SplitType getSplitType();

    <T> T remove(String str);

    long size();

    MutableMediaFormat toMutableFormat();

    default Rect getDimensionRect() {
        return new Rect(0, 0, getCols(), getRows());
    }

    default ColorFormat getColorFormat() {
        throw new UnsupportedOperationException("not support for " + getMediaType());
    }

    default ColorSpace getColorSpace() {
        throw new UnsupportedOperationException("not suppor for " + getMediaType());
    }

    default int dimension() {
        return getCols() * getRows();
    }

    default int total() {
        return dimension() * getChannels();
    }

    static MediaFormat imageOf(Object... objArr) {
        return of(MediaType.IMAGE, objArr);
    }

    static MediaFormat compressedImageOf(Object... objArr) {
        return of(MediaType.COMPRESSED_IMAGE, objArr);
    }

    static MediaFormat audioOf(Object... objArr) {
        return of(MediaType.AUDIO, objArr);
    }

    static MediaFormat compressedAudioOf(Object... objArr) {
        return of(MediaType.COMPRESSED_AUDIO, objArr);
    }

    static MediaFormat videoOf(Object... objArr) {
        return of(MediaType.VIDEO, objArr);
    }

    static MediaFormat compressedVideoOf(Object... objArr) {
        return of(MediaType.COMPRESSED_VIDEO, objArr);
    }

    static MediaFormat metaOf(Object... objArr) {
        return of(MediaType.META, Stream.concat(Stream.of(DataType.U8C1), Arrays.stream(objArr)).toArray());
    }

    static MediaFormat gainMapOf(Object... objArr) {
        return mutableGainMapOf(objArr).toMediaFormat();
    }

    static MediaFormat thumbnailOf(Object... objArr) {
        return of(MediaType.IMAGE, Shape.of(384, 512));
    }

    static MediaFormat scalarOf(Object... objArr) {
        return of(MediaType.SCALA, objArr);
    }

    static MediaFormat of(MediaType mediaType, Object... objArr) {
        return mutableOf(mediaType, objArr).toMediaFormat();
    }

    static MutableMediaFormat mutableImageOf(Object... objArr) {
        return mutableOf(MediaType.IMAGE, objArr);
    }

    static MutableMediaFormat mutableCompressedImageOf(Object... objArr) {
        return mutableOf(MediaType.COMPRESSED_IMAGE, objArr);
    }

    static MutableMediaFormat mutableAudioOf(Object... objArr) {
        return mutableOf(MediaType.AUDIO, objArr);
    }

    static MutableMediaFormat mutableCompressedAudioOf(Object... objArr) {
        return mutableOf(MediaType.COMPRESSED_AUDIO, objArr);
    }

    static MutableMediaFormat mutableVideoOf(Object... objArr) {
        return mutableOf(MediaType.VIDEO, objArr);
    }

    static MutableMediaFormat mutableCompressedVideoOf(Object... objArr) {
        return mutableOf(MediaType.COMPRESSED_VIDEO, objArr);
    }

    static MutableMediaFormat mutableScalaOf(Object... objArr) {
        return mutableOf(MediaType.SCALA, objArr);
    }

    static MutableMediaFormat mutableMetaOf(Object... objArr) {
        return mutableOf(MediaType.META, Stream.concat(Stream.of(DataType.U8C1), Arrays.stream(objArr)).toArray());
    }

    static MutableMediaFormat mutableGainMapOf(Object... objArr) {
        return mutableMetaOf(objArr).set("gain-map", true);
    }

    static MediaFormat mutableThumbnailOf(Object... objArr) {
        return mutableOf(MediaType.IMAGE, Shape.of(384, 512));
    }

    static MutableMediaFormat mutableOf(MediaType mediaType, Object... objArr) {
        return new StapleMutableMediaFormat(mediaType, objArr);
    }

    static MutableMediaFormat mutableEmptyOf() {
        return new StapleMutableMediaFormat();
    }

    static List<MediaFormat> getPlanes(MediaFormat mediaFormat) {
        if (mediaFormat.getMediaType().isImage()) {
            if (mediaFormat.getDataType() == DataType.NONE) {
                throw new IllegalStateException("not support non data type");
            }
            if (mediaFormat.getColorFormat().isPlanar()) {
                final ArrayList arrayList = new ArrayList();
                if (mediaFormat.getColorFormat().isYuv()) {
                    final DataType dataTypeDepth = mediaFormat.getDataType().depth();
                    final Shape shape = mediaFormat.getShape().toMutableShape().setRows(mediaFormat.getRows() >> 1).setCols(mediaFormat.getCols() >> 1).setChannels(mediaFormat.getColorFormat().numberOfChromaChannels()).toShape();
                    arrayList.add(mutableImageOf(DataType.of(dataTypeDepth, 1), mediaFormat.getShape()));
                    IntStream.range(1, mediaFormat.getColorFormat().numberOfPlanes()).forEach(new IntConsumer() { // from class: com.samsung.android.sume.core.format.MediaFormat$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            arrayList.add(MediaFormat.mutableImageOf(dataTypeDepth, shape));
                        }
                    });
                    return (List) arrayList.stream().map(new Function() { // from class: com.samsung.android.sume.core.format.MediaFormat$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return MediaFormat.lambda$getPlanes$1((MutableMediaFormat) obj);
                        }
                    }).collect(Collectors.toList());
                }
                throw new UnsupportedOperationException("not support yet for planar except yuv format");
            }
            return new ArrayList<MediaFormat>() { // from class: com.samsung.android.sume.core.format.MediaFormat.1
                {
                    add(MediaFormat.this);
                }
            };
        }
        throw new UnsupportedOperationException("not support non image format");
    }

    static /* synthetic */ MediaFormat lambda$getPlanes$1(MutableMediaFormat mutableMediaFormat) {
        return mutableMediaFormat instanceof MutableMediaFormat ? mutableMediaFormat.toMediaFormat() : mutableMediaFormat;
    }
}
