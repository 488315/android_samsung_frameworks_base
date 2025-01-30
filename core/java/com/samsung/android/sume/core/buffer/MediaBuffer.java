package com.samsung.android.sume.core.buffer;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.p009os.Parcelable;
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

/* loaded from: classes4.dex */
public interface MediaBuffer extends Parcelable, Copyable<MediaBuffer> {
  public static final int BUFFER_FLAG_PACKED_EVALUATION_BUFFER = 2;
  public static final int BUFFER_FLAG_PACKED_IO_BUFFERS = 1;

  @Retention(RetentionPolicy.SOURCE)
  public @interface BufferFlag {}

  void addExtra(Map<String, Object> map);

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

  default boolean isEmpty() {
    return false;
  }

  default boolean isNotEmpty() {
    return true;
  }

  default List<MediaBuffer> asList() {
    return (List) stream().collect(Collectors.toList());
  }

  default MediaBuffer asRef() {
    return this;
  }

  @Deprecated
  static MediaBuffer ofEmpty(MediaFormat format) {
    return mutableOf(format);
  }

  static MutableMediaBuffer mutableOf(MediaFormat format) {
    return new MutableMediaBuffer(format);
  }

  static MutableMediaBuffer mutableOf() {
    return new MutableMediaBuffer();
  }

  static MutableMediaBuffer mutableOf(MediaBuffer buffer) {
    if (buffer instanceof MutableMediaBuffer) {
      return (MutableMediaBuffer) buffer;
    }
    return new MutableMediaBuffer(buffer);
  }

  static MutableMediaBuffer mutableOf(MediaType mediaType, Object... args) {
    return new MutableMediaBuffer(MediaFormat.mutableOf(mediaType, args));
  }

  /* renamed from: of */
  static MediaBuffer m323of(MediaFormat format) {
    if (format.size() == 0) {
      return mutableOf(format);
    }
    return MediaBufferAllocator.m333of(format).allocate();
  }

  static boolean isInstanceOfFormat(Object object) {
    return (object instanceof ColorFormat)
        || (object instanceof DataType)
        || (object instanceof Shape)
        || (object instanceof ColorSpace)
        || (object instanceof Rect);
  }

  /* renamed from: of */
  static MediaBuffer m332of(MediaType mediaType, Object... args) {
    List<Object> formatObject = new ArrayList<>();
    Object dataObject = null;
    Align align = new Align();
    for (Object arg : args) {
      if (isInstanceOfFormat(arg)) {
        formatObject.add(arg);
      } else if (arg instanceof Align) {
        align = (Align) arg;
      } else {
        dataObject = arg;
      }
    }
    MediaFormat format = MediaFormat.m394of(mediaType, formatObject.toArray());
    if (align.getDimension() == 0 && format.getShape() != null) {
      if (align.getStride() == 0) {
        if (format.getChannels() > 0) {
          align.setStride(format.getCols() * format.getChannels());
        } else {
          align.setScanline(format.getCols());
        }
      }
      if (align.getScanline() == 0) {
        align.setScanline(format.getRows());
      }
      align.adjustAlign();
    }
    if (dataObject != null) {
      if (dataObject instanceof ByteBuffer) {
        return m324of(format, align, (ByteBuffer) dataObject);
      }
      if (dataObject instanceof Bitmap) {
        return m324of(format, align, (Bitmap) dataObject);
      }
      return m324of(format, align, dataObject);
    }
    return m325of(format, align);
  }

  @Deprecated
  static MediaBuffer ofShared(MediaFormat format) {
    return sharedOf(format);
  }

  static MediaBuffer sharedOf(MediaFormat format) {
    return MediaBufferAllocator.m333of(format).allocateShared();
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* renamed from: of */
  static <T> MediaBuffer m325of(MediaFormat format, T t) {
    if (t instanceof Align) {
      return MediaBufferAllocator.m333of(format).allocate((Align) t);
    }
    if (t instanceof Bitmap) {
      MutableMediaFormat fmt = format.toMutableFormat();
      fmt.setColorSpace(ColorSpace.m466of((Bitmap) t));
      return MediaBufferAllocator.m333of(fmt.toMediaFormat()).wrap(t);
    }
    return MediaBufferAllocator.m333of(format).wrap(t);
  }

  /* renamed from: of */
  static <T> MediaBuffer m324of(MediaFormat format, Align align, T data) {
    return MediaBufferAllocator.m334of(format, align).wrap(data);
  }

  @Deprecated
  static MediaBuffer ofEmpty(MutableMediaFormat format) {
    return mutableOf(format.toMediaFormat());
  }

  static MutableMediaBuffer mutableOf(MutableMediaFormat format) {
    return mutableOf(format.toMediaFormat());
  }

  /* renamed from: of */
  static MediaBuffer m326of(MutableMediaFormat format) {
    return m323of(format.toMediaFormat());
  }

  @Deprecated
  static MediaBuffer ofShared(MutableMediaFormat format) {
    return sharedOf(format.toMediaFormat());
  }

  static MediaBuffer sharedOf(MutableMediaFormat format) {
    return sharedOf(format.toMediaFormat());
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* renamed from: of */
  static <T> MediaBuffer m328of(MutableMediaFormat format, T t) {
    if (t instanceof Align) {
      return m325of(format.toMediaFormat(), (Align) t);
    }
    return m325of(format.toMediaFormat(), t);
  }

  /* renamed from: of */
  static <T> MediaBuffer m327of(MutableMediaFormat format, Align align, T data) {
    return m324of(format.toMediaFormat(), align, data);
  }

  /* renamed from: of */
  static <T> MediaBuffer m331of(MediaType mediaType, T data) {
    MediaFormat format = BufferExtension.describe(data).setMediaType(mediaType).toMediaFormat();
    return MediaBufferAllocator.m333of(format).wrap(data);
  }

  /* renamed from: of */
  static <T> MediaBuffer m330of(MediaType mediaType, Align align, T data) {
    MediaFormat format = BufferExtension.describe(data).setMediaType(mediaType).toMediaFormat();
    return MediaBufferAllocator.m334of(format, align).wrap(data);
  }

  /* renamed from: of */
  static <T> MediaBuffer m329of(
      MutableMediaFormat format, T data, List<MediaBuffer> metaDataBuffers) {
    MediaBuffer buffer = m328of(format, (Object) data);
    metaDataBuffers.add(0, buffer);
    return groupOf(buffer, metaDataBuffers);
  }

  static MutableMediaBuffer mutableImageOf(Object... args) {
    return mutableOf(MediaType.IMAGE, args);
  }

  static MutableMediaBuffer mutableCompressedImageOf(Object... args) {
    return mutableOf(MediaType.COMPRESSED_IMAGE, args);
  }

  static MutableMediaBuffer mutableAudioOf(Object... args) {
    return mutableOf(MediaType.AUDIO, args);
  }

  static MutableMediaBuffer mutableCompressedAudioOf(Object... args) {
    return mutableOf(MediaType.COMPRESSED_AUDIO, args);
  }

  static MutableMediaBuffer mutableVideoOf(Object... args) {
    return mutableOf(MediaType.VIDEO, args);
  }

  static MutableMediaBuffer mutableCompressedVideoOf(Object... args) {
    return mutableOf(MediaType.COMPRESSED_VIDEO, args);
  }

  static MutableMediaBuffer mutableMetaOf(Object... args) {
    return mutableOf(
        MediaType.META, Stream.concat(Stream.of(DataType.U8C1), Arrays.stream(args)).toArray());
  }

  static MutableMediaBuffer mutableScalaOf(Object... args) {
    return mutableOf(MediaType.SCALA, args);
  }

  static MutableMediaBuffer mutableThumbnailOf(Object... args) {
    return mutableOf(MediaType.IMAGE, Shape.m397of(384, 512), args);
  }

  static MediaBuffer imageOf(Object... args) {
    return m332of(MediaType.IMAGE, args);
  }

  static MediaBuffer compressedImageOf(Object... args) {
    return m332of(MediaType.COMPRESSED_IMAGE, args);
  }

  static MediaBuffer audioOf(Object... args) {
    return m332of(MediaType.AUDIO, args);
  }

  static MediaBuffer compressedAudioOf(Object... args) {
    return m332of(MediaType.COMPRESSED_AUDIO, args);
  }

  static MediaBuffer videoOf(Object... args) {
    return m332of(MediaType.VIDEO, args);
  }

  static MediaBuffer compressedVideoOf(Object... args) {
    return m332of(MediaType.COMPRESSED_VIDEO, args);
  }

  static MediaBuffer metaOf(Object... args) {
    return m332of(
        MediaType.META, Stream.concat(Stream.of(DataType.U8C1), Arrays.stream(args)).toArray());
  }

  static MediaBuffer scalaOf(Object... args) {
    return m332of(MediaType.SCALA, args);
  }

  static MediaBuffer thumbnailOf(Object... args) {
    return m332of(MediaType.IMAGE, Shape.m397of(384, 512), args);
  }

  static MediaBuffer metadataBufferOf(int metadataKey, ByteBuffer byteBuffer) {
    MutableMediaFormat metaFormat = MediaFormat.mutableMetaOf(Shape.m397of(1, byteBuffer.limit()));
    switch (metadataKey) {
      case 1:
        metaFormat.set("exif", true);
        break;
      case 2:
        metaFormat.set("icc", true);
        break;
      case 3:
        metaFormat.set("gain-map", true);
        break;
      default:
        throw new UnsupportedOperationException("not support for " + metadataKey);
    }
    return m328of(metaFormat, byteBuffer);
  }

  static MediaBuffer metadataBufferOf(int metadataKey, Bitmap bitmap) {
    MutableMediaFormat fmt =
        MediaFormat.mutableMetaOf(
            Integer.valueOf(metadataKey),
            DataType.U8C3,
            ColorFormat.RGB,
            Shape.m397of(bitmap.getHeight(), bitmap.getWidth()));
    fmt.setColorSpace(ColorSpace.m466of(bitmap));
    float ratio = fmt.size() / bitmap.getByteCount();
    if (Math.round(ratio * 100.0f) / 100.0f == 0.75f) {
      fmt.setDataType(DataType.U8C4);
      fmt.setColorFormat(ColorFormat.RGBA);
    } else if (Math.round(ratio * 10.0f) / 10.0f == 0.5f) {
      fmt.setDataType(DataType.U16C3);
    } else if (Math.round(ratio * 1000.0f) / 1000.0f == 0.375f) {
      fmt.setDataType(DataType.U16C4);
      fmt.setColorFormat(ColorFormat.RGBA);
    } else if (Math.round(ratio) == 3) {
      fmt.setDataType(DataType.U8C1);
      fmt.setColorFormat(ColorFormat.GRAY);
    } else {
      throw new IllegalArgumentException(
          "byte count +"
              + bitmap.getByteCount()
              + "is differ from calculated buffer size"
              + fmt.size());
    }
    return m328of(fmt, bitmap);
  }

  static MediaBuffer metadataBufferOf(int metadataKey, UniExifInterface uniExifInterface) {
    return metadataBufferOf(metadataKey, uniExifInterface.toExifByteBuffer());
  }

  /* JADX WARN: Multi-variable type inference failed */
  static <T> MediaBuffer metadataBufferOf(String metadataKeyStr, T t) {
    MutableMediaFormat format;
    if (t instanceof ByteBuffer) {
      format = MediaFormat.mutableMetaOf(Shape.m397of(1, ((ByteBuffer) t).limit()));
    } else if (t instanceof UniExifInterface) {
      ByteBuffer byteBufferData = ((UniExifInterface) t).toExifByteBuffer();
      format = MediaFormat.mutableMetaOf(Shape.m397of(1, byteBufferData.limit()));
    } else if (t instanceof Bitmap) {
      Bitmap bitmap = (Bitmap) t;
      MutableMediaFormat format2 =
          MediaFormat.mutableMetaOf(
              DataType.U8C3, ColorFormat.RGB, Shape.m397of(bitmap.getHeight(), bitmap.getWidth()));
      format2.setColorSpace(ColorSpace.m466of(bitmap));
      float ratio = format2.size() / bitmap.getByteCount();
      if (Math.round(ratio * 100.0f) / 100.0f == 0.75f) {
        format2.setDataType(DataType.U8C4);
        format2.setColorFormat(ColorFormat.RGBA);
      } else if (Math.round(ratio * 10.0f) / 10.0f == 0.5f) {
        format2.setDataType(DataType.U16C3);
      } else if (Math.round(ratio * 1000.0f) / 1000.0f == 0.375f) {
        format2.setDataType(DataType.U16C4);
        format2.setColorFormat(ColorFormat.RGBA);
      } else if (Math.round(ratio) == 3) {
        format2.setDataType(DataType.U8C1);
        format2.setColorFormat(ColorFormat.GRAY);
      } else {
        throw new IllegalArgumentException(
            "byte count +"
                + bitmap.getByteCount()
                + "is differ from calculated buffer size"
                + format2.size());
      }
      format = format2;
    } else {
      format = MediaFormat.mutableMetaOf(new Object[0]);
    }
    format.set(metadataKeyStr, true);
    return m328of(format, (Object) t);
  }

  static MediaBuffer exifBufferOf(ByteBuffer exifByteBuffer) {
    MutableMediaFormat exifFormat =
        MediaFormat.mutableMetaOf(Shape.m397of(1, exifByteBuffer.limit()));
    exifFormat.set("exif", true);
    return m328of(exifFormat, exifByteBuffer);
  }

  static MediaBuffer exifBufferOf(UniExifInterface uniExifInterface) {
    return exifBufferOf(uniExifInterface.toExifByteBuffer());
  }

  static MediaBuffer iccBufferOf(ByteBuffer iccByteBuffer) {
    MutableMediaFormat iccFormat =
        MediaFormat.mutableMetaOf(Shape.m397of(1, iccByteBuffer.limit()));
    iccFormat.set("icc", true);
    return m328of(iccFormat, iccByteBuffer);
  }

  static MediaBuffer gainMapBufferOf(ByteBuffer byteBuffer) {
    MutableMediaFormat fmt = MediaFormat.mutableGainMapOf(Shape.m397of(1, byteBuffer.limit()));
    return m328of(fmt, byteBuffer);
  }

  static MediaBuffer gainMapBufferOf(Bitmap bitmap) {
    MutableMediaFormat fmt =
        MediaFormat.mutableGainMapOf(
            DataType.U8C3, ColorFormat.RGB, Shape.m397of(bitmap.getHeight(), bitmap.getWidth()));
    fmt.setColorSpace(ColorSpace.m466of(bitmap));
    float ratio = fmt.size() / bitmap.getByteCount();
    if (Math.round(ratio * 100.0f) / 100.0f == 0.75f) {
      fmt.setDataType(DataType.U8C4);
      fmt.setColorFormat(ColorFormat.RGBA);
    } else if (Math.round(ratio * 10.0f) / 10.0f == 0.5f) {
      fmt.setDataType(DataType.U16C3);
    } else if (Math.round(ratio * 1000.0f) / 1000.0f == 0.375f) {
      fmt.setDataType(DataType.U16C4);
      fmt.setColorFormat(ColorFormat.RGBA);
    } else if (Math.round(ratio) == 3) {
      fmt.setDataType(DataType.U8C1);
      fmt.setColorFormat(ColorFormat.GRAY);
    } else {
      throw new IllegalArgumentException(
          "byte count +"
              + bitmap.getByteCount()
              + "is differ from calculated buffer size"
              + fmt.size());
    }
    return m328of(fmt, bitmap);
  }

  static MediaBuffer groupOf(MediaBuffer primary, List<MediaBuffer> derivatives) {
    return new DeriveBufferGroup(primary, derivatives);
  }

  static MediaBuffer groupOf(int primaryId, MediaBuffer... buffers) {
    return groupOf(
        primaryId,
        (List<MediaBuffer>) (buffers.length == 0 ? new ArrayList() : Arrays.asList(buffers)));
  }

  static MediaBuffer groupOf(int primaryId, List<MediaBuffer> buffers) {
    return new CollectBufferGroup(primaryId, buffers);
  }

  static MediaBuffer groupOf(MediaBuffer... buffers) {
    return groupOf(0, buffers);
  }

  static MediaBuffer groupOf(List<MediaBuffer> buffers) {
    return groupOf(0, buffers);
  }

  static <T> MediaBuffer groupOf(MediaFormat format, T data, List<MediaBuffer> metaDataBuffers) {
    MediaBuffer buffer = m325of(format, data);
    metaDataBuffers.add(0, buffer);
    return groupOf(buffer, metaDataBuffers);
  }

  static <T> MediaBuffer groupOf(
      MutableMediaFormat format, T data, List<MediaBuffer> metaDataBuffers) {
    return groupOf(format.toMediaFormat(), data, metaDataBuffers);
  }

  static <T> MediaBuffer groupOf(MediaType mediaType, T data, List<MediaBuffer> metaDataBuffers) {
    MediaBuffer buffer = m331of(mediaType, data);
    metaDataBuffers.add(0, buffer);
    return groupOf(buffer, metaDataBuffers);
  }
}
