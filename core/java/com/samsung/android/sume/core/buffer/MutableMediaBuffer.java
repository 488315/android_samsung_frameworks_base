package com.samsung.android.sume.core.buffer;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.UniExifInterface;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.functional.PlaceHolder;
import com.samsung.android.sume.core.types.MediaType;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public final class MutableMediaBuffer extends MediaBufferBase implements PlaceHolder<MediaBuffer> {
  public static final Parcelable.Creator<MutableMediaBuffer> CREATOR =
      new Parcelable.Creator<
          MutableMediaBuffer>() { // from class:
                                  // com.samsung.android.sume.core.buffer.MutableMediaBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MutableMediaBuffer createFromParcel(Parcel in) {
          return new MutableMediaBuffer(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MutableMediaBuffer[] newArray(int size) {
          return new MutableMediaBuffer[size];
        }
      };
  private MediaBuffer buffer;

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ boolean containFlags(int[] iArr) {
    return super.containFlags(iArr);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.format.Copyable
  public /* bridge */ /* synthetic */ MediaBuffer copy() {
    return super.copy();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.format.Copyable
  /* renamed from: deepCopy */
  public /* bridge */ /* synthetic */ MediaBuffer deepCopy2() {
    return super.deepCopy2();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ Align getAlign() {
    return super.getAlign();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ int getChannels() {
    return super.getChannels();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ int getCols() {
    return super.getCols();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ int getRows() {
    return super.getRows();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ int getScanline() {
    return super.getScanline();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ int getStride() {
    return super.getStride();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ Object getTypedDataOr(Class cls, Object obj) {
    return super.getTypedDataOr(cls, obj);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ void release() {
    super.release();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ Object removeExtra(String str) {
    return super.removeExtra(str);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ void setExtra(String str, Object obj) {
    super.setExtra(str, obj);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ void setExtra(Map map) {
    super.setExtra(map);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ MediaBuffer setFlags(int[] iArr) {
    return super.setFlags(iArr);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ MediaBuffer setScanline(int i) {
    return super.setScanline(i);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public /* bridge */ /* synthetic */ MediaBuffer setStride(int i) {
    return super.setStride(i);
  }

  MutableMediaBuffer() {
    super(MediaFormat.m394of(MediaType.NONE, new Object[0]));
  }

  MutableMediaBuffer(MediaFormat format) {
    super(format);
  }

  MutableMediaBuffer(MediaBuffer buffer) {
    super(buffer.getFormat());
    this.buffer = buffer;
  }

  @Override // com.samsung.android.sume.core.functional.PlaceHolder
  public void put(MediaBuffer instance) {
    if (instance instanceof MutableMediaBuffer) {
      if (instance.isEmpty()) {
        setExtra(instance.getExtra());
        return;
      }
      instance = ((MutableMediaBuffer) instance).reset();
    }
    MediaBuffer mediaBuffer = this.buffer;
    if (instance == mediaBuffer) {
      return;
    }
    if (mediaBuffer != null) {
      instance.addExtra(mediaBuffer.getExtra());
    }
    MediaFormat mediaFormat = this.format;
    if (this.extra != null) {
      instance.addExtra(this.extra);
    }
    this.buffer = instance;
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.android.sume.core.functional.PlaceHolder
  public MediaBuffer reset() {
    MediaBuffer ret = this.buffer;
    MediaBuffer mediaBuffer = this.buffer;
    if (mediaBuffer != null) {
      super.addExtra(mediaBuffer.getExtra());
      this.buffer = null;
    }
    return ret;
  }

  public MediaBuffer get() {
    return this.buffer;
  }

  public MediaBuffer moveTo(MutableMediaBuffer other) {
    MediaBuffer ret = other.buffer;
    other.put(reset());
    return ret;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer,
            // com.samsung.android.sume.core.functional.PlaceHolder
  public boolean isEmpty() {
    return this.buffer == null;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer,
            // com.samsung.android.sume.core.functional.PlaceHolder
  public boolean isNotEmpty() {
    return this.buffer != null;
  }

  public void setFormat(MediaFormat mediaFormat) {
    this.format = mediaFormat;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public MediaFormat getFormat() {
    return (MediaFormat)
        Optional.ofNullable(this.buffer)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda4
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return ((MediaBuffer) obj).getFormat();
                  }
                })
            .orElse(super.getFormat());
  }

  /* renamed from: lambda$stream$0$com-samsung-android-sume-core-buffer-MutableMediaBuffer */
  /* synthetic */ Stream m344x681fd1b8() {
    return Stream.of(this);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public Stream<MediaBuffer> stream() {
    return (Stream)
        Optional.ofNullable(this.buffer)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda0
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return ((MediaBuffer) obj).stream();
                  }
                })
            .orElseGet(
                new Supplier() { // from class:
                                 // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda1
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    return MutableMediaBuffer.this.m344x681fd1b8();
                  }
                });
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public <V> MediaBuffer convertTo(final Class<V> clazz) {
    return (MediaBuffer)
        Optional.ofNullable(this.buffer)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda6
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    MediaBuffer convertTo;
                    convertTo = ((MediaBuffer) obj).convertTo(clazz);
                    return convertTo;
                  }
                })
            .orElse(null);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public <V> V getData() {
    return (V)
        Optional.ofNullable(this.buffer)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda10
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    Object data;
                    data = ((MediaBuffer) obj).getData();
                    return data;
                  }
                })
            .orElse(null);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public Class<?> getDataClass() {
    return (Class)
        Optional.ofNullable(this.buffer)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda8
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    Class cls;
                    cls = ((MediaBuffer) obj).getClass();
                    return cls;
                  }
                })
            .orElse(null);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public <V> V getTypedData(final Class<V> cls) {
    return (V)
        Optional.ofNullable(this.buffer)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda11
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    Object typedData;
                    typedData = ((MediaBuffer) obj).getTypedData(cls);
                    return typedData;
                  }
                })
            .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda12());
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public List<MediaBuffer> getMetaDataBuffers() {
    return (List)
        this.buffer.asList().stream()
            .filter(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda2
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return MutableMediaBuffer.lambda$getMetaDataBuffers$4((MediaBuffer) obj);
                  }
                })
            .collect(Collectors.toList());
  }

  static /* synthetic */ boolean lambda$getMetaDataBuffers$4(MediaBuffer it) {
    return it.getFormat().getMediaType() == MediaType.META;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public MediaBuffer getExifBuffer() {
    return this.buffer.asList().stream()
        .filter(
            new Predicate() { // from class:
                              // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda7
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                return MutableMediaBuffer.lambda$getExifBuffer$5((MediaBuffer) obj);
              }
            })
        .findAny()
        .orElse(null);
  }

  static /* synthetic */ boolean lambda$getExifBuffer$5(MediaBuffer it) {
    return it.getFormat().getMediaType() == MediaType.META && it.getFormat().contains("exif");
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public MediaBuffer getIccBuffer() {
    return this.buffer.asList().stream()
        .filter(
            new Predicate() { // from class:
                              // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda9
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                return MutableMediaBuffer.lambda$getIccBuffer$6((MediaBuffer) obj);
              }
            })
        .findAny()
        .orElse(null);
  }

  static /* synthetic */ boolean lambda$getIccBuffer$6(MediaBuffer it) {
    return it.getFormat().getMediaType() == MediaType.META && it.getFormat().contains("icc");
  }

  public MediaBuffer setAlign(Align align) {
    if (align.getStride() != 0) {
      this.align = align;
    } else if (align.getAlignOfWidth() != 0) {
      this.align.set(align.getAlignOfWidth(), align.getAlignOfHeight());
    }
    return this;
  }

  public MediaBuffer setMetaDataBuffer(List<MediaBuffer> metaDataBuffer) {
    List<MediaBuffer> mediaBuffers = this.buffer.asList();
    mediaBuffers.addAll(metaDataBuffer);
    put(MediaBuffer.groupOf(this.buffer, mediaBuffers));
    return this.buffer;
  }

  public MediaBuffer setExifBuffer(MediaBuffer exifBuffer) {
    List<MediaBuffer> newMediaBuffers =
        (List)
            this.buffer.asList().stream()
                .filter(
                    new Predicate() { // from class:
                                      // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        return MutableMediaBuffer.lambda$setExifBuffer$7((MediaBuffer) obj);
                      }
                    })
                .collect(Collectors.toList());
    newMediaBuffers.add(exifBuffer);
    return setMetaDataBuffer(newMediaBuffers);
  }

  static /* synthetic */ boolean lambda$setExifBuffer$7(MediaBuffer it) {
    return !it.getFormat().contains("exif");
  }

  public MediaBuffer setIccBuffer(MediaBuffer iccBuffer) {
    List<MediaBuffer> newMediaBuffers =
        (List)
            this.buffer.asList().stream()
                .filter(
                    new Predicate() { // from class:
                                      // com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda5
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        return MutableMediaBuffer.lambda$setIccBuffer$8((MediaBuffer) obj);
                      }
                    })
                .collect(Collectors.toList());
    newMediaBuffers.add(iccBuffer);
    return setMetaDataBuffer(newMediaBuffers);
  }

  static /* synthetic */ boolean lambda$setIccBuffer$8(MediaBuffer it) {
    return !it.getFormat().contains("icc");
  }

  public MediaBuffer setExifBuffer(ByteBuffer exifByteBuffer) {
    MutableMediaFormat exifFormat =
        MediaFormat.mutableMetaOf(Shape.m397of(1, exifByteBuffer.limit()));
    exifFormat.set("exif", true);
    return setExifBuffer(MediaBuffer.m328of(exifFormat, exifByteBuffer));
  }

  public MediaBuffer setExifBuffer(UniExifInterface uniExifInterface) {
    return setExifBuffer(uniExifInterface.toExifByteBuffer());
  }

  public MediaBuffer setIccBuffer(ByteBuffer iccByteBuffer) {
    MutableMediaFormat iccFormat =
        MediaFormat.mutableMetaOf(Shape.m397of(1, iccByteBuffer.limit()));
    iccFormat.set("icc", true);
    return setIccBuffer(MediaBuffer.m328of(iccFormat, iccByteBuffer));
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public void addExtra(Map<String, Object> other) {
    MediaBuffer mediaBuffer = this.buffer;
    if (mediaBuffer != null) {
      mediaBuffer.addExtra(other);
    } else {
      super.addExtra(other);
    }
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public Map<String, Object> getExtra() {
    MediaBuffer mediaBuffer = this.buffer;
    return mediaBuffer != null ? mediaBuffer.getExtra() : super.getExtra();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public <V> V getExtra(String str) {
    MediaBuffer mediaBuffer = this.buffer;
    return mediaBuffer != null ? (V) mediaBuffer.getExtra(str) : (V) super.getExtra(str);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public <V> V getExtra(String str, V v) {
    MediaBuffer mediaBuffer = this.buffer;
    return mediaBuffer != null ? (V) mediaBuffer.getExtra(str, v) : (V) super.getExtra(str, v);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public boolean containsExtra(String key) {
    MediaBuffer mediaBuffer = this.buffer;
    return mediaBuffer != null ? mediaBuffer.containsExtra(key) : super.containsExtra(key);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public boolean containsAnyExtra(String... keys) {
    MediaBuffer mediaBuffer = this.buffer;
    return mediaBuffer != null ? mediaBuffer.containsAnyExtra(keys) : super.containsAnyExtra(keys);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase,
            // com.samsung.android.sume.core.buffer.MediaBuffer
  public boolean containsAllExtra(String... keys) {
    MediaBuffer mediaBuffer = this.buffer;
    return mediaBuffer != null ? mediaBuffer.containsAllExtra(keys) : super.containsAllExtra(keys);
  }

  public String toString() {
    return contentToString(this);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public String contentToString(Object obj) {
    StringBuilder append = new StringBuilder().append(Def.taglnOf(obj));
    String[] strArr = new String[2];
    strArr[0] = "format=" + (this.format != null ? this.format.contentToString() : "n/a");
    strArr[1] = "data=" + this.buffer;
    return append.append(Def.contentToStringln("    ", strArr)).toString();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public String contentToString() {
    return contentToString(this);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public long size() {
    MediaBuffer mediaBuffer = this.buffer;
    if (mediaBuffer != null) {
      return mediaBuffer.size();
    }
    return 0L;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    if (this.buffer != null) {
      dest.writeInt(1);
      dest.writeParcelable(this.buffer, flags);
    } else {
      dest.writeInt(0);
    }
  }

  public MutableMediaBuffer(Parcel in) {
    super(in);
    if (in.readInt() != 0) {
      this.buffer = (MediaBuffer) in.readParcelable(MediaBuffer.class.getClassLoader());
    }
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, android.os.Parcelable
  public int describeContents() {
    return 0;
  }
}
