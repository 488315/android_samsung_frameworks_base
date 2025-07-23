package com.samsung.android.sume.core.buffer;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Parcelable;
import com.samsung.android.sume.core.UniExifInterface;
import com.samsung.android.sume.core.format.Copyable;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.functional.PlaceHolder;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.ColorSpace;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.MediaType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public interface MediaBuffer extends Parcelable, Copyable<MediaBuffer> {
    public static final int BUFFER_FLAG_PACKED_EVALUATION_BUFFER = 2;
    public static final int BUFFER_FLAG_PACKED_IO_BUFFERS = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BufferFlag {
    }

    void addExtra(Map<String, Object> map);

    default MediaBuffer asRef() {
        return this;
    }

    boolean containFlags(int... iArr);

    boolean containsAllExtra(String... strArr);

    boolean containsAnyExtra(String... strArr);

    boolean containsExtra(String str);

    String contentToString();

    String contentToString(Object obj);

    <T> MediaBuffer convertTo(Class<T> cls);

    Align getAlign();

    int getChannels();

    int getCols();

    <T> T getData();

    Class<?> getDataClass();

    MediaBuffer getExifBuffer();

    <T> T getExtra(String str);

    <V> V getExtra(String str, V v);

    Map<String, Object> getExtra();

    MediaFormat getFormat();

    MediaBuffer getIccBuffer();

    List<MediaBuffer> getMetaDataBuffers();

    int getRows();

    int getScanline();

    int getStride();

    <T> T getTypedData(Class<T> cls);

    <T> T getTypedDataOr(Class<T> cls, T t);

    default boolean isEmpty() {
        return false;
    }

    default boolean isNotEmpty() {
        return true;
    }

    void release();

    <T> T removeExtra(String str);

    void setExtra(String str, Object obj);

    void setExtra(Map<String, Object> map);

    MediaBuffer setFlags(int... iArr);

    MediaBuffer setScanline(int i);

    MediaBuffer setStride(int i);

    long size();

    Stream<MediaBuffer> stream();

    default boolean isMutable() {
        return this instanceof PlaceHolder;
    }

    default List<MediaBuffer> asList() {
        return (List) stream().collect(Collectors.toList());
    }

    @Deprecated
    static MediaBuffer ofEmpty(MediaFormat mediaFormat) {
        return mutableOf(mediaFormat);
    }

    static MutableMediaBuffer mutableOf(MediaFormat mediaFormat) {
        return new MutableMediaBuffer(mediaFormat);
    }

    static MutableMediaBuffer mutableOf() {
        return new MutableMediaBuffer();
    }

    static MutableMediaBuffer mutableOf(MediaBuffer mediaBuffer) {
        if (mediaBuffer instanceof MutableMediaBuffer) {
            return (MutableMediaBuffer) mediaBuffer;
        }
        return new MutableMediaBuffer(mediaBuffer);
    }

    static MutableMediaBuffer mutableOf(MediaType mediaType, Object... objArr) {
        return new MutableMediaBuffer(MediaFormat.mutableOf(mediaType, objArr));
    }

    static MediaBuffer of(MediaFormat mediaFormat) {
        if (mediaFormat.size() == 0) {
            return mutableOf(mediaFormat);
        }
        return MediaBufferAllocator.of(mediaFormat).allocate();
    }

    static boolean isInstanceOfFormat(Object obj) {
        return (obj instanceof ColorFormat) || (obj instanceof DataType) || (obj instanceof Shape) || (obj instanceof ColorSpace) || (obj instanceof Rect);
    }

    static MediaBuffer of(MediaType mediaType, Object... objArr) {
        ArrayList arrayList = new ArrayList();
        Align align = new Align();
        Object obj = null;
        for (Object obj2 : objArr) {
            if (isInstanceOfFormat(obj2)) {
                arrayList.add(obj2);
            } else if (obj2 instanceof Align) {
                align = (Align) obj2;
            } else {
                obj = obj2;
            }
        }
        MediaFormat of = MediaFormat.of(mediaType, arrayList.toArray());
        if (align.getDimension() == 0 && of.getShape() != null) {
            if (align.getStride() == 0) {
                if (of.getChannels() > 0) {
                    align.setStride(of.getCols() * of.getChannels());
                } else {
                    align.setScanline(of.getCols());
                }
            }
            if (align.getScanline() == 0) {
                align.setScanline(of.getRows());
            }
            align.adjustAlign();
        }
        if (obj != null) {
            if (obj instanceof ByteBuffer) {
                return of(of, align, (ByteBuffer) obj);
            }
            if (obj instanceof Bitmap) {
                return of(of, align, (Bitmap) obj);
            }
            return of(of, align, obj);
        }
        return of(of, align);
    }

    @Deprecated
    static MediaBuffer ofShared(MediaFormat mediaFormat) {
        return sharedOf(mediaFormat);
    }

    static MediaBuffer sharedOf(MediaFormat mediaFormat) {
        return MediaBufferAllocator.of(mediaFormat).allocateShared();
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T> MediaBuffer of(MediaFormat mediaFormat, T t) {
        if (t instanceof Align) {
            return MediaBufferAllocator.of(mediaFormat).allocate((Align) t);
        }
        if (t instanceof Bitmap) {
            MutableMediaFormat mutableFormat = mediaFormat.toMutableFormat();
            mutableFormat.setColorSpace(ColorSpace.of((Bitmap) t));
            return MediaBufferAllocator.of(mutableFormat.toMediaFormat()).wrap(t);
        }
        return MediaBufferAllocator.of(mediaFormat).wrap(t);
    }

    static <T> MediaBuffer of(MediaFormat mediaFormat, Align align, T t) {
        return MediaBufferAllocator.of(mediaFormat, align).wrap(t);
    }

    @Deprecated
    static MediaBuffer ofEmpty(MutableMediaFormat mutableMediaFormat) {
        return mutableOf(mutableMediaFormat.toMediaFormat());
    }

    static MutableMediaBuffer mutableOf(MutableMediaFormat mutableMediaFormat) {
        return mutableOf(mutableMediaFormat.toMediaFormat());
    }

    static MediaBuffer of(MutableMediaFormat mutableMediaFormat) {
        return of(mutableMediaFormat.toMediaFormat());
    }

    @Deprecated
    static MediaBuffer ofShared(MutableMediaFormat mutableMediaFormat) {
        return sharedOf(mutableMediaFormat.toMediaFormat());
    }

    static MediaBuffer sharedOf(MutableMediaFormat mutableMediaFormat) {
        return sharedOf(mutableMediaFormat.toMediaFormat());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T> MediaBuffer of(MutableMediaFormat mutableMediaFormat, T t) {
        if (t instanceof Align) {
            return of(mutableMediaFormat.toMediaFormat(), (Align) t);
        }
        return of(mutableMediaFormat.toMediaFormat(), t);
    }

    static <T> MediaBuffer of(MutableMediaFormat mutableMediaFormat, Align align, T t) {
        return of(mutableMediaFormat.toMediaFormat(), align, t);
    }

    static <T> MediaBuffer of(MediaType mediaType, T t) {
        return MediaBufferAllocator.of(BufferExtension.describe(t).setMediaType(mediaType).toMediaFormat()).wrap(t);
    }

    static <T> MediaBuffer of(MediaType mediaType, Align align, T t) {
        return MediaBufferAllocator.of(BufferExtension.describe(t).setMediaType(mediaType).toMediaFormat(), align).wrap(t);
    }

    static <T> MediaBuffer of(MutableMediaFormat mutableMediaFormat, T t, List<MediaBuffer> list) {
        MediaBuffer of = of(mutableMediaFormat, (Object) t);
        list.add(0, of);
        return groupOf(of, list);
    }

    static MutableMediaBuffer mutableImageOf(Object... objArr) {
        return mutableOf(MediaType.IMAGE, objArr);
    }

    static MutableMediaBuffer mutableCompressedImageOf(Object... objArr) {
        return mutableOf(MediaType.COMPRESSED_IMAGE, objArr);
    }

    static MutableMediaBuffer mutableAudioOf(Object... objArr) {
        return mutableOf(MediaType.AUDIO, objArr);
    }

    static MutableMediaBuffer mutableCompressedAudioOf(Object... objArr) {
        return mutableOf(MediaType.COMPRESSED_AUDIO, objArr);
    }

    static MutableMediaBuffer mutableVideoOf(Object... objArr) {
        return mutableOf(MediaType.VIDEO, objArr);
    }

    static MutableMediaBuffer mutableCompressedVideoOf(Object... objArr) {
        return mutableOf(MediaType.COMPRESSED_VIDEO, objArr);
    }

    static MutableMediaBuffer mutableMetaOf(Object... objArr) {
        return mutableOf(MediaType.META, Stream.concat(Stream.of(DataType.U8C1), Arrays.stream(objArr)).toArray());
    }

    static MutableMediaBuffer mutableScalaOf(Object... objArr) {
        return mutableOf(MediaType.SCALA, objArr);
    }

    static MutableMediaBuffer mutableThumbnailOf(Object... objArr) {
        return mutableOf(MediaType.IMAGE, Shape.of(384, 512), objArr);
    }

    static MediaBuffer imageOf(Object... objArr) {
        return of(MediaType.IMAGE, objArr);
    }

    static MediaBuffer compressedImageOf(Object... objArr) {
        return of(MediaType.COMPRESSED_IMAGE, objArr);
    }

    static MediaBuffer audioOf(Object... objArr) {
        return of(MediaType.AUDIO, objArr);
    }

    static MediaBuffer compressedAudioOf(Object... objArr) {
        return of(MediaType.COMPRESSED_AUDIO, objArr);
    }

    static MediaBuffer videoOf(Object... objArr) {
        return of(MediaType.VIDEO, objArr);
    }

    static MediaBuffer compressedVideoOf(Object... objArr) {
        return of(MediaType.COMPRESSED_VIDEO, objArr);
    }

    static MediaBuffer metaOf(Object... objArr) {
        return of(MediaType.META, Stream.concat(Stream.of(DataType.U8C1), Arrays.stream(objArr)).toArray());
    }

    static MediaBuffer scalaOf(Object... objArr) {
        return of(MediaType.SCALA, objArr);
    }

    static MediaBuffer thumbnailOf(Object... objArr) {
        return of(MediaType.IMAGE, Shape.of(384, 512), objArr);
    }

    static MediaBuffer metadataBufferOf(int i, ByteBuffer byteBuffer) {
        MutableMediaFormat mutableMetaOf = MediaFormat.mutableMetaOf(Shape.of(1, byteBuffer.limit()));
        if (i == 1) {
            mutableMetaOf.set("exif", true);
        } else if (i == 2) {
            mutableMetaOf.set("icc", true);
        } else if (i == 3) {
            mutableMetaOf.set("gain-map", true);
        } else {
            throw new UnsupportedOperationException("not support for " + i);
        }
        return of(mutableMetaOf, byteBuffer);
    }

    static MediaBuffer metadataBufferOf(int i, Bitmap bitmap) {
        MutableMediaFormat mutableMetaOf = MediaFormat.mutableMetaOf(Integer.valueOf(i), DataType.U8C3, ColorFormat.RGB, Shape.of(bitmap.getHeight(), bitmap.getWidth()));
        mutableMetaOf.setColorSpace(ColorSpace.of(bitmap));
        float size = mutableMetaOf.size() / bitmap.getByteCount();
        if (Math.round(size * 100.0f) / 100.0f == 0.75f) {
            mutableMetaOf.setDataType(DataType.U8C4);
            mutableMetaOf.setColorFormat(ColorFormat.RGBA);
        } else if (Math.round(size * 10.0f) / 10.0f == 0.5f) {
            mutableMetaOf.setDataType(DataType.U16C3);
        } else if (Math.round(size * 1000.0f) / 1000.0f == 0.375f) {
            mutableMetaOf.setDataType(DataType.U16C4);
            mutableMetaOf.setColorFormat(ColorFormat.RGBA);
        } else if (Math.round(size) == 3) {
            mutableMetaOf.setDataType(DataType.U8C1);
            mutableMetaOf.setColorFormat(ColorFormat.GRAY);
        } else {
            throw new IllegalArgumentException("byte count +" + bitmap.getByteCount() + "is differ from calculated buffer size" + mutableMetaOf.size());
        }
        return of(mutableMetaOf, bitmap);
    }

    static MediaBuffer metadataBufferOf(int i, UniExifInterface uniExifInterface) {
        return metadataBufferOf(i, uniExifInterface.toExifByteBuffer());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T> MediaBuffer metadataBufferOf(String str, T t) {
        MutableMediaFormat mutableMetaOf;
        if (t instanceof ByteBuffer) {
            mutableMetaOf = MediaFormat.mutableMetaOf(Shape.of(1, ((ByteBuffer) t).limit()));
        } else if (t instanceof UniExifInterface) {
            mutableMetaOf = MediaFormat.mutableMetaOf(Shape.of(1, ((UniExifInterface) t).toExifByteBuffer().limit()));
        } else if (t instanceof Bitmap) {
            Bitmap bitmap = (Bitmap) t;
            MutableMediaFormat mutableMetaOf2 = MediaFormat.mutableMetaOf(DataType.U8C3, ColorFormat.RGB, Shape.of(bitmap.getHeight(), bitmap.getWidth()));
            mutableMetaOf2.setColorSpace(ColorSpace.of(bitmap));
            float size = mutableMetaOf2.size() / bitmap.getByteCount();
            if (Math.round(size * 100.0f) / 100.0f == 0.75f) {
                mutableMetaOf2.setDataType(DataType.U8C4);
                mutableMetaOf2.setColorFormat(ColorFormat.RGBA);
            } else if (Math.round(size * 10.0f) / 10.0f == 0.5f) {
                mutableMetaOf2.setDataType(DataType.U16C3);
            } else if (Math.round(size * 1000.0f) / 1000.0f == 0.375f) {
                mutableMetaOf2.setDataType(DataType.U16C4);
                mutableMetaOf2.setColorFormat(ColorFormat.RGBA);
            } else if (Math.round(size) == 3) {
                mutableMetaOf2.setDataType(DataType.U8C1);
                mutableMetaOf2.setColorFormat(ColorFormat.GRAY);
            } else {
                throw new IllegalArgumentException("byte count +" + bitmap.getByteCount() + "is differ from calculated buffer size" + mutableMetaOf2.size());
            }
            mutableMetaOf = mutableMetaOf2;
        } else {
            mutableMetaOf = MediaFormat.mutableMetaOf(new Object[0]);
        }
        mutableMetaOf.set(str, true);
        return of(mutableMetaOf, (Object) t);
    }

    static MediaBuffer exifBufferOf(ByteBuffer byteBuffer) {
        MutableMediaFormat mutableMetaOf = MediaFormat.mutableMetaOf(Shape.of(1, byteBuffer.limit()));
        mutableMetaOf.set("exif", true);
        return of(mutableMetaOf, byteBuffer);
    }

    static MediaBuffer exifBufferOf(UniExifInterface uniExifInterface) {
        return exifBufferOf(uniExifInterface.toExifByteBuffer());
    }

    static MediaBuffer iccBufferOf(ByteBuffer byteBuffer) {
        MutableMediaFormat mutableMetaOf = MediaFormat.mutableMetaOf(Shape.of(1, byteBuffer.limit()));
        mutableMetaOf.set("icc", true);
        return of(mutableMetaOf, byteBuffer);
    }

    static MediaBuffer gainMapBufferOf(ByteBuffer byteBuffer) {
        return of(MediaFormat.mutableGainMapOf(Shape.of(1, byteBuffer.limit())), byteBuffer);
    }

    static MediaBuffer gainMapBufferOf(Bitmap bitmap) {
        MutableMediaFormat mutableGainMapOf = MediaFormat.mutableGainMapOf(DataType.U8C3, ColorFormat.RGB, Shape.of(bitmap.getHeight(), bitmap.getWidth()));
        mutableGainMapOf.setColorSpace(ColorSpace.of(bitmap));
        float size = mutableGainMapOf.size() / bitmap.getByteCount();
        if (Math.round(size * 100.0f) / 100.0f == 0.75f) {
            mutableGainMapOf.setDataType(DataType.U8C4);
            mutableGainMapOf.setColorFormat(ColorFormat.RGBA);
        } else if (Math.round(size * 10.0f) / 10.0f == 0.5f) {
            mutableGainMapOf.setDataType(DataType.U16C3);
        } else if (Math.round(size * 1000.0f) / 1000.0f == 0.375f) {
            mutableGainMapOf.setDataType(DataType.U16C4);
            mutableGainMapOf.setColorFormat(ColorFormat.RGBA);
        } else if (Math.round(size) == 3) {
            mutableGainMapOf.setDataType(DataType.U8C1);
            mutableGainMapOf.setColorFormat(ColorFormat.GRAY);
        } else {
            throw new IllegalArgumentException("byte count +" + bitmap.getByteCount() + "is differ from calculated buffer size" + mutableGainMapOf.size());
        }
        return of(mutableGainMapOf, bitmap);
    }

    static MediaBuffer groupOf(MediaBuffer mediaBuffer, List<MediaBuffer> list) {
        return new DeriveBufferGroup(mediaBuffer, list);
    }

    static MediaBuffer groupOf(int i, MediaBuffer... mediaBufferArr) {
        return groupOf(i, (List<MediaBuffer>) (mediaBufferArr.length == 0 ? new ArrayList() : Arrays.asList(mediaBufferArr)));
    }

    static MediaBuffer groupOf(int i, List<MediaBuffer> list) {
        return new CollectBufferGroup(i, list);
    }

    static MediaBuffer groupOf(MediaBuffer... mediaBufferArr) {
        return groupOf(0, mediaBufferArr);
    }

    static MediaBuffer groupOf(List<MediaBuffer> list) {
        return groupOf(0, list);
    }

    static <T> MediaBuffer groupOf(MediaFormat mediaFormat, T t, List<MediaBuffer> list) {
        MediaBuffer of = of(mediaFormat, t);
        list.add(0, of);
        return groupOf(of, list);
    }

    static <T> MediaBuffer groupOf(MutableMediaFormat mutableMediaFormat, T t, List<MediaBuffer> list) {
        return groupOf(mutableMediaFormat.toMediaFormat(), t, list);
    }

    static <T> MediaBuffer groupOf(MediaType mediaType, T t, List<MediaBuffer> list) {
        MediaBuffer of = of(mediaType, t);
        list.add(0, of);
        return groupOf(of, list);
    }
}
