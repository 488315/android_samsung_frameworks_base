package com.samsung.android.sume.core.buffer;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.types.DataType;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class RawDataReader implements MediaBufferReader<Number> {
    private static final String TAG = Def.tagOf((Class<?>) RawDataReader.class);
    private final Supplier<?> pixelRead;
    private final Function<Integer, ?> pixelReadByIndex;

    @Override // com.samsung.android.sume.core.buffer.MediaBufferReader
    public Number get() {
        return (Number) this.pixelRead.get();
    }

    public Number get(int index) {
        return (Number) this.pixelReadByIndex.apply(Integer.valueOf(index));
    }

    private Supplier<?> getRawDataRead(DataType dataType, final ByteBuffer byteBuffer) {
        if (dataType.isByte()) {
            Objects.requireNonNull(byteBuffer);
            return new Supplier() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Byte.valueOf(byteBuffer.get());
                }
            };
        }
        if (dataType.isShort()) {
            return new Supplier() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    Object valueOf;
                    valueOf = Short.valueOf(byteBuffer.asShortBuffer().get());
                    return valueOf;
                }
            };
        }
        if (dataType.isInt()) {
            return new Supplier() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    Object valueOf;
                    valueOf = Integer.valueOf(byteBuffer.asIntBuffer().get());
                    return valueOf;
                }
            };
        }
        if (dataType.isLong()) {
            return new Supplier() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    Object valueOf;
                    valueOf = Long.valueOf(byteBuffer.asLongBuffer().get());
                    return valueOf;
                }
            };
        }
        if (dataType.isFloat()) {
            return new Supplier() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    Object valueOf;
                    valueOf = Float.valueOf(byteBuffer.asFloatBuffer().get());
                    return valueOf;
                }
            };
        }
        throw new UnsupportedOperationException("not supported");
    }

    private Function<Integer, ?> getRawDataReadByIndex(
            DataType dataType, final ByteBuffer byteBuffer) {
        if (dataType.isByte()) {
            Objects.requireNonNull(byteBuffer);
            return new Function() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Byte.valueOf(byteBuffer.get(((Integer) obj).intValue()));
                }
            };
        }
        if (dataType.isShort()) {
            return new Function() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Object valueOf;
                    valueOf =
                            Short.valueOf(
                                    byteBuffer.asShortBuffer().get(((Integer) obj).intValue()));
                    return valueOf;
                }
            };
        }
        if (dataType.isInt()) {
            return new Function() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Object valueOf;
                    valueOf =
                            Integer.valueOf(
                                    byteBuffer.asIntBuffer().get(((Integer) obj).intValue()));
                    return valueOf;
                }
            };
        }
        if (dataType.isLong()) {
            return new Function() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Object valueOf;
                    valueOf =
                            Long.valueOf(byteBuffer.asLongBuffer().get(((Integer) obj).intValue()));
                    return valueOf;
                }
            };
        }
        if (dataType.isFloat()) {
            return new Function() { // from class:
                                    // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda11
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Object valueOf;
                    valueOf =
                            Float.valueOf(
                                    byteBuffer.asFloatBuffer().get(((Integer) obj).intValue()));
                    return valueOf;
                }
            };
        }
        throw new UnsupportedOperationException("not supported");
    }

    public RawDataReader(MediaBuffer buffer) {
        final MediaFormat format = buffer.getFormat();
        this.pixelRead =
                (Supplier)
                        Optional.ofNullable((ByteBuffer) buffer.getTypedData(ByteBuffer.class))
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda5
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return RawDataReader.this
                                                        .m9116lambda$new$8$comsamsungandroidsumecorebufferRawDataReader(
                                                                format, (ByteBuffer) obj);
                                            }
                                        })
                                .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        this.pixelReadByIndex =
                (Function)
                        Optional.ofNullable((ByteBuffer) buffer.getTypedData(ByteBuffer.class))
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.sume.core.buffer.RawDataReader$$ExternalSyntheticLambda6
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return RawDataReader.this
                                                        .m9117lambda$new$9$comsamsungandroidsumecorebufferRawDataReader(
                                                                format, (ByteBuffer) obj);
                                            }
                                        })
                                .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }

    /* renamed from: lambda$new$8$com-samsung-android-sume-core-buffer-RawDataReader, reason: not valid java name */
    /* synthetic */ Supplier m9116lambda$new$8$comsamsungandroidsumecorebufferRawDataReader(
            MediaFormat format, ByteBuffer it) {
        return getRawDataRead(format.getDataType(), it);
    }

    /* renamed from: lambda$new$9$com-samsung-android-sume-core-buffer-RawDataReader, reason: not valid java name */
    /* synthetic */ Function m9117lambda$new$9$comsamsungandroidsumecorebufferRawDataReader(
            MediaFormat format, ByteBuffer e) {
        return getRawDataReadByIndex(format.getDataType(), e);
    }
}
