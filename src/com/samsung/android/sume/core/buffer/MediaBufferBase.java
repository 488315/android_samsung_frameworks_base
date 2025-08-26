package com.samsung.android.sume.core.buffer;

import android.content.om.OverlayManagerExt$$ExternalSyntheticLambda4;
import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.MediaFormat;
import java.io.IOException;
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

/* loaded from: classes6.dex */
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MediaBufferBase(MediaFormat mediaFormat) {
        this.sharedCount = new AtomicInteger(0);
        this.sharedObj = new HashSet();
        this.flags = 0;
        this.extra = new HashMap<>();
        this.internalBuffers = new ArrayList();
        this.align = new Align();
        this.format = mediaFormat;
        this.align = Align.setByFormat(mediaFormat);
    }

    public MediaBufferBase(MediaFormat mediaFormat, Align align) {
        this.sharedCount = new AtomicInteger(0);
        this.sharedObj = new HashSet();
        this.flags = 0;
        this.extra = new HashMap<>();
        this.internalBuffers = new ArrayList();
        new Align();
        this.format = mediaFormat;
        this.align = align;
    }

    public MediaBufferBase(Parcel parcel) throws ClassNotFoundException, IOException {
        this.sharedCount = new AtomicInteger(0);
        this.sharedObj = new HashSet();
        this.flags = 0;
        this.extra = new HashMap<>();
        this.internalBuffers = new ArrayList();
        this.align = new Align();
        this.format = (MediaFormat) parcel.readSerializable();
        this.flags = parcel.readInt();
        this.align.setShape(this.format.getCols() * this.format.getChannels(), this.format.getRows());
        parcel.readMap(this.extra, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(this.format);
        parcel.writeInt(i);
        parcel.writeMap(this.extra);
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
        } catch (NullPointerException unused) {
            return t;
        }
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer setFlags(int... iArr) {
        for (int i : iArr) {
            this.flags = i | this.flags;
        }
        return this;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public boolean containFlags(int... iArr) {
        return Arrays.stream(iArr).allMatch(new IntPredicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda5
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return this.f$0.m9523x14a22c8(i);
            }
        });
    }

    /* renamed from: lambda$containFlags$0$com-samsung-android-sume-core-buffer-MediaBufferBase, reason: not valid java name */
    /* synthetic */ boolean m9523x14a22c8(int i) {
        return (this.flags & i) != 0;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer setStride(int i) {
        this.align.setStride(i);
        return this;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer setScanline(int i) {
        this.align.setScanline(i);
        return this;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public void setExtra(String str, Object obj) {
        getExtra().put(str, obj);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public void setExtra(Map<String, Object> map) {
        HashMap<String, Object> map2 = this.extra;
        if (map2 == map) {
            return;
        }
        map2.putAll(map);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public void addExtra(Map<String, Object> map) {
        HashMap<String, Object> map2 = this.extra;
        if (map2 == map) {
            return;
        }
        map2.putAll((Map) map.entrySet().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m9522x2f0e598b((Map.Entry) obj);
            }
        }).collect(Collectors.toMap(new MediaBufferBase$$ExternalSyntheticLambda3(), new MediaBufferBase$$ExternalSyntheticLambda4())));
    }

    /* renamed from: lambda$addExtra$1$com-samsung-android-sume-core-buffer-MediaBufferBase, reason: not valid java name */
    /* synthetic */ boolean m9522x2f0e598b(Map.Entry entry) {
        return !this.extra.containsKey(entry.getKey());
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public Map<String, Object> getExtra() {
        return (Map) Optional.ofNullable(this.extra).orElseGet(new MediaBufferBase$$ExternalSyntheticLambda8());
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
    public boolean containsExtra(final String str) {
        return ((Boolean) Optional.ofNullable(this.extra).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((HashMap) obj).containsKey(str));
            }
        }).orElse(false)).booleanValue();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public boolean containsAnyExtra(final String... strArr) {
        return ((Boolean) Optional.ofNullable(this.extra).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(Arrays.stream(strArr).anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        return MediaBufferBase.lambda$containsAnyExtra$3(map, (String) obj2);
                    }
                }));
            }
        }).orElse(false)).booleanValue();
    }

    static /* synthetic */ boolean lambda$containsAnyExtra$3(HashMap map, String str) {
        Stream stream = map.keySet().stream();
        Objects.requireNonNull(str);
        return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda4(str));
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public boolean containsAllExtra(final String... strArr) {
        return ((Boolean) Optional.ofNullable(this.extra).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(Arrays.stream(strArr).allMatch(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        return MediaBufferBase.lambda$containsAllExtra$5(map, (String) obj2);
                    }
                }));
            }
        }).orElse(false)).booleanValue();
    }

    static /* synthetic */ boolean lambda$containsAllExtra$5(HashMap map, String str) {
        Stream stream = map.keySet().stream();
        Objects.requireNonNull(str);
        return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda4(str));
    }

    /* renamed from: lambda$transformDataTo$7$com-samsung-android-sume-core-buffer-MediaBufferBase, reason: not valid java name */
    /* synthetic */ void m9524x9603d8fd(Object obj) {
        this.internalBuffers.add(obj);
    }

    protected <T, V> V transformDataTo(T t, Class<V> cls) {
        try {
            BufferExtension.putInternalBufferHandler(new Consumer() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m9524x9603d8fd(obj);
                }
            });
            return (V) BufferExtension.transform(getFormat(), t, cls);
        } catch (UnsupportedOperationException unused) {
            if ((t instanceof HardwareBuffer) || cls == HardwareBuffer.class) {
                return (V) BufferExtension.transform(this.getFormat(), t, cls);
            }
            return null;
        }
    }

    protected boolean isDataShared() {
        return ((Boolean) Optional.ofNullable(getDataClass()).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(BufferExtension.isRequiredToRelease((Class) obj));
            }
        }).orElse(false)).booleanValue();
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
        HashMap<String, Object> map = this.extra;
        if (map != null) {
            map.clear();
        }
        this.format = null;
        this.extra = null;
        this.internalBuffers.forEach(new Consumer() { // from class: com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaBufferBase.lambda$release$9(obj);
            }
        });
        this.internalBuffers.clear();
    }

    static /* synthetic */ void lambda$release$9(Object obj) {
        if (BufferExtension.isRequiredToRelease(obj.getClass())) {
            BufferExtension.dealloc(obj);
        }
    }

    @Override // com.samsung.android.sume.core.format.Copyable
    public MediaBuffer copy() {
        try {
            MediaBuffer mediaBuffer = (MediaBuffer) clone();
            if (isDataShared()) {
                int iIncrementAndGet = this.sharedCount.incrementAndGet();
                Log.d(TAG, "shared count increased: " + iIncrementAndGet + NavigationBarInflaterView.KEY_CODE_START + hashCode() + NavigationBarInflaterView.KEY_CODE_END);
            }
            return mediaBuffer;
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
}
