package com.samsung.android.sume.core.buffer;

import android.content.p001om.OverlayManagerExt$$ExternalSyntheticLambda1;
import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.MediaFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
abstract class MediaBufferBase implements MediaBuffer {
  static final int DATA_HARDWARE_BUFFER = 2;
  static final int DATA_NOTHING = 1;
  static final int DATA_PARCELABLE = 4;
  static final int DATA_PARCEL_FILEDESCRIPTOR = 3;
  static final int DATA_SERIALIZABLE = 5;
  protected static final String INDENT_MARK = "    ";
  private static final String TAG = Def.tagOf((Class<?>) MediaBufferBase.class);
  protected Align align;
  protected HashMap<String, Object> extra;
  protected int flags;
  protected MediaFormat format;
  protected List<Object> internalBuffers;
  protected AtomicInteger sharedCount;
  protected Set<Integer> sharedObj;

  public MediaBufferBase(MediaFormat format) {
    this.sharedCount = new AtomicInteger(0);
    this.sharedObj = new HashSet();
    this.flags = 0;
    this.extra = new HashMap<>();
    this.internalBuffers = new ArrayList();
    this.align = new Align();
    this.format = format;
    this.align = Align.setByFormat(format);
  }

  public MediaBufferBase(MediaFormat format, Align align) {
    this.sharedCount = new AtomicInteger(0);
    this.sharedObj = new HashSet();
    this.flags = 0;
    this.extra = new HashMap<>();
    this.internalBuffers = new ArrayList();
    this.align = new Align();
    this.format = format;
    this.align = align;
  }

  public MediaBufferBase(Parcel in) {
    this.sharedCount = new AtomicInteger(0);
    this.sharedObj = new HashSet();
    this.flags = 0;
    this.extra = new HashMap<>();
    this.internalBuffers = new ArrayList();
    this.align = new Align();
    this.format = (MediaFormat) in.readSerializable();
    this.flags = in.readInt();
    this.align.setShape(this.format.getCols() * this.format.getChannels(), this.format.getRows());
    in.readMap(this.extra, null);
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeSerializable(this.format);
    dest.writeInt(flags);
    dest.writeMap(this.extra);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public MediaFormat getFormat() {
    return this.format;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public int getRows() {
    return getFormat().getRows();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public int getCols() {
    return getFormat().getCols();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public int getChannels() {
    return getFormat().getChannels();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public int getStride() {
    return getAlign().getStride();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public int getScanline() {
    return getAlign().getScanline();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public Align getAlign() {
    return this.align;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public <T> T getTypedDataOr(Class<T> cls, T t) {
    try {
      return (T) getTypedData(cls);
    } catch (NullPointerException e) {
      return t;
    }
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public MediaBuffer setFlags(int... flags) {
    for (int flag : flags) {
      this.flags |= flag;
    }
    return this;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public boolean containFlags(int... flagsToCheck) {
    return Arrays.stream(flagsToCheck)
        .allMatch(
            new IntPredicate() { // from class:
                                 // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda3
              @Override // java.util.function.IntPredicate
              public final boolean test(int i) {
                return MediaBufferBase.this.m336x14a22c8(i);
              }
            });
  }

  /* renamed from: lambda$containFlags$0$com-samsung-android-sume-core-buffer-MediaBufferBase */
  /* synthetic */ boolean m336x14a22c8(int it) {
    return (this.flags & it) != 0;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public MediaBuffer setStride(int stride) {
    this.align.setStride(stride);
    return this;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public MediaBuffer setScanline(int scanline) {
    this.align.setScanline(scanline);
    return this;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public void setExtra(String key, Object obj) {
    getExtra().put(key, obj);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public void setExtra(Map<String, Object> other) {
    HashMap<String, Object> hashMap = this.extra;
    if (hashMap == other) {
      return;
    }
    hashMap.putAll(other);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public void addExtra(Map<String, Object> other) {
    HashMap<String, Object> hashMap = this.extra;
    if (hashMap == other) {
      return;
    }
    hashMap.putAll(
        (Map)
            other.entrySet().stream()
                .filter(
                    new Predicate() { // from class:
                                      // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda0
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        return MediaBufferBase.this.m335x2f0e598b((Map.Entry) obj);
                      }
                    })
                .collect(
                    Collectors.toMap(
                        new MediaBufferBase$$ExternalSyntheticLambda1(),
                        new MediaBufferBase$$ExternalSyntheticLambda2())));
  }

  /* renamed from: lambda$addExtra$1$com-samsung-android-sume-core-buffer-MediaBufferBase */
  /* synthetic */ boolean m335x2f0e598b(Map.Entry it) {
    return !this.extra.containsKey(it.getKey());
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public Map<String, Object> getExtra() {
    return (Map)
        Optional.ofNullable(this.extra).orElseGet(new MediaBufferBase$$ExternalSyntheticLambda5());
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public <V> V getExtra(String str) {
    return (V) getExtra().get(str);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public <V> V getExtra(String str, V v) {
    return (V) getExtra().getOrDefault(str, v);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public <T> T removeExtra(String str) {
    return (T) getExtra().remove(str);
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public boolean containsExtra(final String key) {
    return ((Boolean)
            Optional.ofNullable(this.extra)
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda4
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Boolean valueOf;
                        valueOf = Boolean.valueOf(((HashMap) obj).containsKey(key));
                        return valueOf;
                      }
                    })
                .orElse(false))
        .booleanValue();
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public boolean containsAnyExtra(final String... keys) {
    return ((Boolean)
            Optional.ofNullable(this.extra)
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda11
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Boolean valueOf;
                        valueOf =
                            Boolean.valueOf(
                                Arrays.stream(keys)
                                    .anyMatch(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda12
                                          @Override // java.util.function.Predicate
                                          public final boolean test(Object obj2) {
                                            return MediaBufferBase.lambda$containsAnyExtra$3(
                                                r1, (String) obj2);
                                          }
                                        }));
                        return valueOf;
                      }
                    })
                .orElse(false))
        .booleanValue();
  }

  static /* synthetic */ boolean lambda$containsAnyExtra$3(HashMap e, String it) {
    Stream stream = e.keySet().stream();
    Objects.requireNonNull(it);
    return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda1(it));
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public boolean containsAllExtra(final String... keys) {
    return ((Boolean)
            Optional.ofNullable(this.extra)
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda10
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Boolean valueOf;
                        valueOf =
                            Boolean.valueOf(
                                Arrays.stream(keys)
                                    .allMatch(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda8
                                          @Override // java.util.function.Predicate
                                          public final boolean test(Object obj2) {
                                            return MediaBufferBase.lambda$containsAllExtra$5(
                                                r1, (String) obj2);
                                          }
                                        }));
                        return valueOf;
                      }
                    })
                .orElse(false))
        .booleanValue();
  }

  static /* synthetic */ boolean lambda$containsAllExtra$5(HashMap e, String it) {
    Stream stream = e.keySet().stream();
    Objects.requireNonNull(it);
    return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda1(it));
  }

  /* renamed from: lambda$transformDataTo$7$com-samsung-android-sume-core-buffer-MediaBufferBase */
  /* synthetic */ void m337x9603d8fd(Object buffer) {
    this.internalBuffers.add(buffer);
  }

  protected <T, V> V transformDataTo(T t, Class<V> cls) {
    try {
      BufferExtension.putInternalBufferHandler(
          new Consumer() { // from class:
                           // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              MediaBufferBase.this.m337x9603d8fd(obj);
            }
          });
      return (V) BufferExtension.transform(getFormat(), t, cls);
    } catch (UnsupportedOperationException e) {
      if ((t instanceof HardwareBuffer) || cls == HardwareBuffer.class) {
        return (V) BufferExtension.transform(getFormat(), t, cls);
      }
      return null;
    }
  }

  protected boolean isDataShared() {
    return ((Boolean)
            Optional.ofNullable(getDataClass())
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda7
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Boolean valueOf;
                        valueOf = Boolean.valueOf(BufferExtension.isRequiredToRelease((Class) obj));
                        return valueOf;
                      }
                    })
                .orElse(false))
        .booleanValue();
  }

  protected synchronized boolean isDataRequireToRelease() {
    if (this.sharedObj.contains(Integer.valueOf(hashCode()))) {
      return false;
    }
    this.sharedObj.add(Integer.valueOf(hashCode()));
    return this.sharedCount.decrementAndGet() == 0;
  }

  @Override // com.samsung.android.sume.core.buffer.MediaBuffer
  public void release() {
    HashMap<String, Object> hashMap = this.extra;
    if (hashMap != null) {
      hashMap.clear();
    }
    this.format = null;
    this.extra = null;
    this.internalBuffers.forEach(
        new Consumer() { // from class:
                         // com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda9
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            MediaBufferBase.lambda$release$9(obj);
          }
        });
    this.internalBuffers.clear();
  }

  static /* synthetic */ void lambda$release$9(Object buffer) {
    if (BufferExtension.isRequiredToRelease(buffer.getClass())) {
      BufferExtension.dealloc(buffer);
    }
  }

  @Override // com.samsung.android.sume.core.format.Copyable
  public MediaBuffer copy() {
    try {
      MediaBuffer copied = (MediaBuffer) clone();
      if (isDataShared()) {
        int use_count = this.sharedCount.incrementAndGet();
        Log.m94d(
            TAG,
            "shared count increased: "
                + use_count
                + NavigationBarInflaterView.KEY_CODE_START
                + hashCode()
                + NavigationBarInflaterView.KEY_CODE_END);
      }
      return copied;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      throw new IllegalStateException();
    }
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.android.sume.core.format.Copyable
  /* renamed from: deepCopy */
  public MediaBuffer deepCopy2() {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }
}
