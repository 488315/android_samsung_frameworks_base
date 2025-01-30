package com.samsung.android.sume.core.buffer;

import android.graphics.Bitmap;
import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.Parcelable;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBufferAllocator;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.MediaType;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class GenericMediaBuffer<T> extends MediaBufferBase {
    private T data;
    private final Class<T> dataClass;
    private volatile List<MediaFormat> planes;
    private static final String TAG = Def.tagOf((Class<?>) GenericMediaBuffer.class);
    public static final Parcelable.Creator<GenericMediaBuffer<?>> CREATOR = new Parcelable.Creator<GenericMediaBuffer<?>>() { // from class: com.samsung.android.sume.core.buffer.GenericMediaBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GenericMediaBuffer<?> createFromParcel(Parcel in) {
            return new GenericMediaBuffer<>(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GenericMediaBuffer<?>[] newArray(int size) {
            return new GenericMediaBuffer[size];
        }
    };

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ void addExtra(Map map) {
        super.addExtra(map);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ boolean containFlags(int[] iArr) {
        return super.containFlags(iArr);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ boolean containsAllExtra(String[] strArr) {
        return super.containsAllExtra(strArr);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ boolean containsAnyExtra(String[] strArr) {
        return super.containsAnyExtra(strArr);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ boolean containsExtra(String str) {
        return super.containsExtra(str);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.format.Copyable
    public /* bridge */ /* synthetic */ MediaBuffer copy() {
        return super.copy();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.format.Copyable
    /* renamed from: deepCopy */
    public /* bridge */ /* synthetic */ MediaBuffer deepCopy2() {
        return super.deepCopy2();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, android.p009os.Parcelable
    public /* bridge */ /* synthetic */ int describeContents() {
        return super.describeContents();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Align getAlign() {
        return super.getAlign();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getChannels() {
        return super.getChannels();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getCols() {
        return super.getCols();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Object getExtra(String str) {
        return super.getExtra(str);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Object getExtra(String str, Object obj) {
        return super.getExtra(str, obj);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Map getExtra() {
        return super.getExtra();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ MediaFormat getFormat() {
        return super.getFormat();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getRows() {
        return super.getRows();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getScanline() {
        return super.getScanline();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getStride() {
        return super.getStride();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Object getTypedDataOr(Class cls, Object obj) {
        return super.getTypedDataOr(cls, obj);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Object removeExtra(String str) {
        return super.removeExtra(str);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ void setExtra(String str, Object obj) {
        super.setExtra(str, obj);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ void setExtra(Map map) {
        super.setExtra(map);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ MediaBuffer setFlags(int[] iArr) {
        return super.setFlags(iArr);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ MediaBuffer setScanline(int i) {
        return super.setScanline(i);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ MediaBuffer setStride(int i) {
        return super.setStride(i);
    }

    GenericMediaBuffer(MediaFormat mediaFormat, T t) {
        super(mediaFormat);
        this.data = t;
        this.dataClass = (Class<T>) t.getClass();
        adjustShape();
    }

    GenericMediaBuffer(MediaFormat mediaFormat, Align align, T t) {
        super(mediaFormat, align);
        this.data = t;
        this.dataClass = (Class<T>) t.getClass();
        adjustShape();
    }

    protected GenericMediaBuffer(Parcel parcel) {
        super(parcel);
        switch (parcel.readInt()) {
            case 1:
                this.dataClass = MediaBufferAllocator.Nothing.class;
                this.data = (T) new MediaBufferAllocator.Nothing();
                break;
            case 2:
                this.dataClass = HardwareBuffer.class;
                this.data = (T) parcel.readParcelable(HardwareBuffer.class.getClassLoader());
                break;
            case 3:
                this.dataClass = ParcelFileDescriptor.class;
                this.data = (T) parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader());
                break;
            case 4:
            default:
                throw new IllegalArgumentException("unknown type");
            case 5:
                T t = (T) parcel.readSerializable();
                this.data = t;
                this.dataClass = (Class<T>) t.getClass();
                break;
        }
        this.planes = (List) parcel.readSerializable();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        HardwareBuffer hardwareBuffer;
        super.writeToParcel(parcel, i);
        T t = this.data;
        if (t instanceof MediaBufferAllocator.Nothing) {
            parcel.writeInt(1);
        } else if (t instanceof ParcelFileDescriptor) {
            parcel.writeInt(3);
            parcel.writeParcelable((Parcelable) this.data, i);
        } else if (t instanceof Number) {
            parcel.writeInt(5);
            parcel.writeSerializable((Number) this.data);
        } else {
            try {
                hardwareBuffer = (HardwareBuffer) BufferExtension.transform(getFormat(), this.data, HardwareBuffer.class);
            } catch (UnsupportedOperationException e) {
                hardwareBuffer = (HardwareBuffer) BufferExtension.transform(getFormat(), this.data, HardwareBuffer.class);
            }
            parcel.writeInt(2);
            parcel.writeParcelable(hardwareBuffer, i);
        }
        parcel.writeSerializable((Serializable) this.planes);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer asRef() {
        int useCount = this.sharedCount.incrementAndGet();
        Log.m94d(TAG, "inc ref count now: " + useCount + NavigationBarInflaterView.KEY_CODE_START + hashCode() + NavigationBarInflaterView.KEY_CODE_END);
        return this;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public void release() {
        int useCount = this.sharedCount.decrementAndGet();
        String str = TAG;
        Log.m94d(str, "dec ref count now: " + useCount + NavigationBarInflaterView.KEY_CODE_START + hashCode() + NavigationBarInflaterView.KEY_CODE_END);
        if (useCount > 0) {
            Log.m94d(str, "release skipped(" + hashCode() + NavigationBarInflaterView.KEY_CODE_END);
            return;
        }
        Log.m94d(str, "release(" + hashCode() + NavigationBarInflaterView.KEY_CODE_END);
        super.release();
        if (this.data != null && isDataShared()) {
            try {
                BufferExtension.dealloc(this.data);
            } catch (UnsupportedOperationException e) {
            }
        }
        this.data = null;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <V> MediaBuffer convertTo(Class<V> clazz) {
        MediaBuffer newBuffer = MediaBuffer.m325of(getFormat(), getTypedData(clazz));
        release();
        return newBuffer;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public Class<?> getDataClass() {
        return this.dataClass;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <V> V getData() {
        return this.dataClass.cast(this.data);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <V> V getTypedData(Class<V> cls) throws NullPointerException {
        if (this.data == null) {
            throw new NullPointerException();
        }
        if (cls.isAssignableFrom(this.dataClass) || ((cls.isPrimitive() && Number.class.isAssignableFrom(this.dataClass)) || (this.dataClass.isPrimitive() && Number.class.isAssignableFrom(cls)))) {
            return this.data;
        }
        return (V) transformDataTo(this.data, cls);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public List<MediaBuffer> getMetaDataBuffers() {
        if (this.format.getMediaType() == MediaType.META) {
            return asList();
        }
        return null;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer getExifBuffer() {
        if (this.format.getMediaType() == MediaType.META && this.format.contains("exif")) {
            return this;
        }
        return null;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer getIccBuffer() {
        if (this.format.getMediaType() == MediaType.META && this.format.contains("icc")) {
            return this;
        }
        return null;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public Stream<MediaBuffer> stream() {
        return Stream.of(this);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public long size() {
        return (long) (getFormat().bytePerPixel() * getAlign().getDimension());
    }

    public void adjustShape() {
        int i = 0;
        T t = this.data;
        if (t != null) {
            if (t instanceof ByteBuffer) {
                i = ((ByteBuffer) t).limit();
            } else if (t instanceof Bitmap) {
                i = ((Bitmap) t).getByteCount();
            }
        }
        ColorFormat colorFormat = this.format.getColorFormat();
        if (colorFormat == ColorFormat.NONE || colorFormat == ColorFormat.OPAQUE || colorFormat == ColorFormat.P010 || colorFormat == ColorFormat.P010_ZIPPED) {
            return;
        }
        MutableMediaFormat mutableFormat = this.format.toMutableFormat();
        if (i != 0 && this.format.getShape() != null) {
            if (this.format.getDataType() == null || this.format.getDataType() == DataType.NONE) {
                int channels = getChannels();
                float dimension = i / (this.align.getDimension() * colorFormat.bytePerPixel());
                if (dimension == 1.0f) {
                    mutableFormat.setDataType(DataType.m467of(DataType.f3067U8, channels));
                } else if (dimension == 2.0f) {
                    mutableFormat.setDataType(DataType.m467of(DataType.U16, channels));
                } else if (dimension == 3.0f) {
                    mutableFormat.setDataType(DataType.m467of(DataType.F32, channels));
                } else if (dimension == 4.0f) {
                    mutableFormat.setDataType(DataType.m467of(DataType.F64, channels));
                } else {
                    throw new IllegalArgumentException("data-size and align(shape) doesn't match" + i + " vs " + this.format + " & " + this.align);
                }
                this.format = mutableFormat.toMediaFormat();
            }
        }
    }

    public String toString() {
        return contentToString(this);
    }

    private String dataToString(Object data) {
        return (String) Optional.ofNullable(data).map(new Function() { // from class: com.samsung.android.sume.core.buffer.GenericMediaBuffer$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GenericMediaBuffer.lambda$dataToString$0(obj);
            }
        }).orElse("n/a");
    }

    static /* synthetic */ String lambda$dataToString$0(Object it) {
        try {
            return BufferExtension.stringfy(it);
        } catch (UnsupportedOperationException e) {
            if (it instanceof HardwareBuffer) {
                return BufferExtension.stringfy(it);
            }
            return it.toString();
        }
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public String contentToString(Object obj) {
        return Def.taglnOf(obj) + Def.contentToStringln("    ", "format=" + ((String) Optional.ofNullable(this.format).map(new GenericMediaBuffer$$ExternalSyntheticLambda0()).orElse("n/a")), "alignShape=" + ((String) Optional.ofNullable(this.align).map(new Function() { // from class: com.samsung.android.sume.core.buffer.GenericMediaBuffer$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return ((Align) obj2).contentToString();
            }
        }).orElse("n/a")), "extra=" + Collections.singletonList(this.extra), "data=" + dataToString(this.data));
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public String contentToString() {
        return contentToString(this);
    }
}
