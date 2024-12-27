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
    public @interface MetadataKey {}

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

    static MediaFormat imageOf(Object... args) {
        return of(MediaType.IMAGE, args);
    }

    static MediaFormat compressedImageOf(Object... args) {
        return of(MediaType.COMPRESSED_IMAGE, args);
    }

    static MediaFormat audioOf(Object... args) {
        return of(MediaType.AUDIO, args);
    }

    static MediaFormat compressedAudioOf(Object... args) {
        return of(MediaType.COMPRESSED_AUDIO, args);
    }

    static MediaFormat videoOf(Object... args) {
        return of(MediaType.VIDEO, args);
    }

    static MediaFormat compressedVideoOf(Object... args) {
        return of(MediaType.COMPRESSED_VIDEO, args);
    }

    static MediaFormat metaOf(Object... args) {
        return of(
                MediaType.META,
                Stream.concat(Stream.of(DataType.U8C1), Arrays.stream(args)).toArray());
    }

    static MediaFormat gainMapOf(Object... args) {
        return mutableGainMapOf(args).toMediaFormat();
    }

    static MediaFormat thumbnailOf(Object... args) {
        return of(MediaType.IMAGE, Shape.of(384, 512));
    }

    static MediaFormat scalarOf(Object... args) {
        return of(MediaType.SCALA, args);
    }

    static MediaFormat of(MediaType mediaType, Object... args) {
        return mutableOf(mediaType, args).toMediaFormat();
    }

    static MutableMediaFormat mutableImageOf(Object... args) {
        return mutableOf(MediaType.IMAGE, args);
    }

    static MutableMediaFormat mutableCompressedImageOf(Object... args) {
        return mutableOf(MediaType.COMPRESSED_IMAGE, args);
    }

    static MutableMediaFormat mutableAudioOf(Object... args) {
        return mutableOf(MediaType.AUDIO, args);
    }

    static MutableMediaFormat mutableCompressedAudioOf(Object... args) {
        return mutableOf(MediaType.COMPRESSED_AUDIO, args);
    }

    static MutableMediaFormat mutableVideoOf(Object... args) {
        return mutableOf(MediaType.VIDEO, args);
    }

    static MutableMediaFormat mutableCompressedVideoOf(Object... args) {
        return mutableOf(MediaType.COMPRESSED_VIDEO, args);
    }

    static MutableMediaFormat mutableScalaOf(Object... args) {
        return mutableOf(MediaType.SCALA, args);
    }

    static MutableMediaFormat mutableMetaOf(Object... args) {
        return mutableOf(
                MediaType.META,
                Stream.concat(Stream.of(DataType.U8C1), Arrays.stream(args)).toArray());
    }

    static MutableMediaFormat mutableGainMapOf(Object... args) {
        return mutableMetaOf(args).set("gain-map", true);
    }

    static MediaFormat mutableThumbnailOf(Object... args) {
        return mutableOf(MediaType.IMAGE, Shape.of(384, 512));
    }

    static MutableMediaFormat mutableOf(MediaType mediaType, Object... args) {
        return new StapleMutableMediaFormat(mediaType, args);
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
                final List<MutableMediaFormat> planes = new ArrayList<>();
                if (mediaFormat.getColorFormat().isYuv()) {
                    final DataType depth = mediaFormat.getDataType().depth();
                    final Shape chromaShape =
                            mediaFormat
                                    .getShape()
                                    .toMutableShape()
                                    .setRows(mediaFormat.getRows() >> 1)
                                    .setCols(mediaFormat.getCols() >> 1)
                                    .setChannels(
                                            mediaFormat.getColorFormat().numberOfChromaChannels())
                                    .toShape();
                    planes.add(mutableImageOf(DataType.of(depth, 1), mediaFormat.getShape()));
                    IntStream.range(1, mediaFormat.getColorFormat().numberOfPlanes())
                            .forEach(
                                    new IntConsumer() { // from class:
                                                        // com.samsung.android.sume.core.format.MediaFormat$$ExternalSyntheticLambda0
                                        @Override // java.util.function.IntConsumer
                                        public final void accept(int i) {
                                            planes.add(
                                                    MediaFormat.mutableImageOf(depth, chromaShape));
                                        }
                                    });
                    return (List)
                            planes.stream()
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.sume.core.format.MediaFormat$$ExternalSyntheticLambda1
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return MediaFormat.lambda$getPlanes$1(
                                                            (MutableMediaFormat) obj);
                                                }
                                            })
                                    .collect(Collectors.toList());
                }
                throw new UnsupportedOperationException(
                        "not support yet for planar except yuv format");
            }
            return new ArrayList<
                    MediaFormat>() { // from class:
                                     // com.samsung.android.sume.core.format.MediaFormat.1
                {
                    add(MediaFormat.this);
                }
            };
        }
        throw new UnsupportedOperationException("not support non image format");
    }

    static /* synthetic */ MediaFormat lambda$getPlanes$1(MutableMediaFormat it) {
        if (it instanceof MutableMediaFormat) {
            return it.toMediaFormat();
        }
        return it;
    }
}
