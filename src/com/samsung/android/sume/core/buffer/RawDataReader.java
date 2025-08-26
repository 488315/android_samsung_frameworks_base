package com.samsung.android.sume.core.buffer;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.types.DataType;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class RawDataReader implements MediaBufferReader<Number> {
    private static final String TAG = Def.tagOf((Class<?>) RawDataReader.class);
    private final Supplier<?> pixelRead;
    private final Function<Integer, ?> pixelReadByIndex;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.buffer.MediaBufferReader
    public Number get() {
        return (Number) this.pixelRead.get();
    }

    public Number get(int i) {
        return (Number) this.pixelReadByIndex.apply(Integer.valueOf(i));
    }

    private Supplier<?> getRawDataRead(DataType dataType, final ByteBuffer byteBuffer) {
        if (dataType.isByte()) {
            Objects.requireNonNull(byteBuffer);
            return new Supplier() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Byte.valueOf(byteBuffer.get());
                }
            };
        }
        if (dataType.isShort()) {
            return new Supplier() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Short.valueOf(byteBuffer.asShortBuffer().get());
                }
            };
        }
        if (dataType.isInt()) {
            return new Supplier() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Integer.valueOf(byteBuffer.asIntBuffer().get());
                }
            };
        }
        if (dataType.isLong()) {
            return new Supplier() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Long.valueOf(byteBuffer.asLongBuffer().get());
                }
            };
        }
        if (dataType.isFloat()) {
            return new Supplier() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Float.valueOf(byteBuffer.asFloatBuffer().get());
                }
            };
        }
        throw new UnsupportedOperationException("not supported");
    }

    private Function<Integer, ?> getRawDataReadByIndex(DataType dataType, final ByteBuffer byteBuffer) {
        if (dataType.isByte()) {
            Objects.requireNonNull(byteBuffer);
            return new Function() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Byte.valueOf(byteBuffer.get(((Integer) obj).intValue()));
                }
            };
        }
        if (dataType.isShort()) {
            return new Function() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Short.valueOf(byteBuffer.asShortBuffer().get(((Integer) obj).intValue()));
                }
            };
        }
        if (dataType.isInt()) {
            return new Function() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(byteBuffer.asIntBuffer().get(((Integer) obj).intValue()));
                }
            };
        }
        if (dataType.isLong()) {
            return new Function() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(byteBuffer.asLongBuffer().get(((Integer) obj).intValue()));
                }
            };
        }
        if (dataType.isFloat()) {
            return new Function() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda11
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Float.valueOf(byteBuffer.asFloatBuffer().get(((Integer) obj).intValue()));
                }
            };
        }
        throw new UnsupportedOperationException("not supported");
    }

    public RawDataReader(MediaBuffer mediaBuffer) {
        final MediaFormat format = mediaBuffer.getFormat();
        this.pixelRead = (Supplier) Optional.ofNullable((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)).map(new Function() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m9530lambda$new$8$comsamsungandroidsumecorebufferRawDataReader(format, (ByteBuffer) obj);
            }
        }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        this.pixelReadByIndex = (Function) Optional.ofNullable((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)).map(new Function() { // from class: com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m9531lambda$new$9$comsamsungandroidsumecorebufferRawDataReader(format, (ByteBuffer) obj);
            }
        }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }

    /* renamed from: lambda$new$8$com-samsung-android-sume-core-buffer-RawDataReader, reason: not valid java name */
    /* synthetic */ Supplier m9530lambda$new$8$comsamsungandroidsumecorebufferRawDataReader(MediaFormat mediaFormat, ByteBuffer byteBuffer) {
        return getRawDataRead(mediaFormat.getDataType(), byteBuffer);
    }

    /* renamed from: lambda$new$9$com-samsung-android-sume-core-buffer-RawDataReader, reason: not valid java name */
    /* synthetic */ Function m9531lambda$new$9$comsamsungandroidsumecorebufferRawDataReader(MediaFormat mediaFormat, ByteBuffer byteBuffer) {
        return getRawDataReadByIndex(mediaFormat.getDataType(), byteBuffer);
    }
}
