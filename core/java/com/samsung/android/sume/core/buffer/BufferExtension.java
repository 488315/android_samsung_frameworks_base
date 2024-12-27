package com.samsung.android.sume.core.buffer;

import android.graphics.Bitmap;
import android.hardware.HardwareBuffer;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.UniExifInterface;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.ColorSpace;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.MediaType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BufferExtension {
    private static final String TAG = Def.tagOf((Class<?>) BufferExtension.class);
    private static final String binaryKeySEP = "->";
    private static volatile BufferExtension sInstance;
    private final Map<String, Class<?>> extensionClassMap = new HashMap();
    private final Map<String, Function<?, MutableMediaFormat>> describeMap = new HashMap();
    private final Map<String, Function<MediaFormat, ?>> allocMap = new HashMap();
    private final Map<String, Consumer<?>> deallocMap = new HashMap();
    private final Map<String, TransformFunction> transformMap = new HashMap();
    private final Map<String, Function<?, String>> stringfyMap = new HashMap();
    private final List<Integer> wrappedTransformList = new ArrayList();
    private final Map<Long, Consumer<?>> internalBufferHandlerMap = new ConcurrentHashMap();

    /* JADX INFO: Access modifiers changed from: private */
    public static BufferExtension getInstance() {
        if (sInstance == null) {
            synchronized (BufferExtension.class) {
                if (sInstance == null) {
                    sInstance = new BufferExtension();
                }
            }
        }
        return sInstance;
    }

    public static <T> MutableMediaFormat describe(T data) {
        return getInstance().doDescribe(data);
    }

    public static <T> T alloc(MediaFormat mediaFormat, Class<T> cls) {
        return (T) getInstance().doAlloc(mediaFormat, cls);
    }

    public static <T> void dealloc(T data) {
        getInstance().doDealloc(data);
    }

    public static <T> void putInternalBufferHandler(Consumer<T> bufferHandler) {
        getInstance()
                .internalBufferHandlerMap
                .put(Long.valueOf(Thread.currentThread().getId()), bufferHandler);
    }

    static <T> Consumer<T> popInternalBufferHandler() {
        return (Consumer)
                getInstance()
                        .internalBufferHandlerMap
                        .remove(Long.valueOf(Thread.currentThread().getId()));
    }

    static boolean isWrappedTransform(BiFunction<MediaFormat, Object, Object> func) {
        return getInstance().wrappedTransformList.contains(Integer.valueOf(func.hashCode()));
    }

    public static <T> String stringfy(T data) {
        return getInstance().doStringfy(data);
    }

    public static <T, R> R transform(MediaFormat mediaFormat, T t, Class<R> cls) {
        return (R) getInstance().doTransform(mediaFormat, t, cls);
    }

    public static void reset() {
        BufferExtension ex = getInstance();
        ex.extensionClassMap.clear();
        ex.describeMap.clear();
        ex.allocMap.clear();
        ex.deallocMap.clear();
        ex.transformMap.clear();
        ex.stringfyMap.clear();
    }

    public static <T, R> boolean isSupportedTransform(Class<T> from, Class<R> to) {
        return getInstance().transformMap.containsKey(getInstance().getBinaryKey(from, to));
    }

    public static <T> boolean isRequiredToRelease(Class<T> clazz) {
        return getInstance().deallocMap.containsKey(getInstance().getUnaryKey(clazz));
    }

    public static Registry newRegistry() {
        return new Registry();
    }

    public static Unregistry newUnregistry() {
        return new Unregistry();
    }

    private BufferExtension() {
        Registry registry =
                newRegistry()
                        .addDescribe(
                                Number.class,
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda21
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return BufferExtension.lambda$new$0((Number) obj);
                                    }
                                })
                        .addDescribe(
                                ByteBuffer.class,
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda26
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        MutableMediaFormat mutableOf;
                                        mutableOf =
                                                MediaFormat.mutableOf(
                                                        MediaType.NONE,
                                                        Shape.of(1, ((ByteBuffer) obj).limit()));
                                        return mutableOf;
                                    }
                                })
                        .addDescribe(
                                Bitmap.class,
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda27
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return BufferExtension.lambda$new$2((Bitmap) obj);
                                    }
                                })
                        .addTransform(
                                Number.class,
                                ByteBuffer.class,
                                new BiFunction() { // from class:
                                                   // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda28
                                    @Override // java.util.function.BiFunction
                                    public final Object apply(Object obj, Object obj2) {
                                        return BufferExtension.lambda$new$3(
                                                (MediaFormat) obj, (Number) obj2);
                                    }
                                })
                        .addTransform(
                                ByteBuffer.class,
                                Number.class,
                                new BiFunction() { // from class:
                                                   // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda29
                                    @Override // java.util.function.BiFunction
                                    public final Object apply(Object obj, Object obj2) {
                                        return BufferExtension.lambda$new$4(
                                                (MediaFormat) obj, (ByteBuffer) obj2);
                                    }
                                })
                        .addStringfy(
                                ByteBuffer.class,
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda30
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return ((ByteBuffer) obj).toString();
                                    }
                                })
                        .addStringfy(
                                ParcelFileDescriptor.class,
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda31
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        String fmtstr;
                                        fmtstr =
                                                Def.fmtstr(
                                                        "fd=%d, len=%ld",
                                                        Integer.valueOf(r1.getFd()),
                                                        Long.valueOf(
                                                                ((ParcelFileDescriptor) obj)
                                                                        .getStatSize()));
                                        return fmtstr;
                                    }
                                })
                        .addTransform(
                                ByteBuffer.class,
                                HardwareBuffer.class,
                                new BiFunction() { // from class:
                                                   // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda32
                                    @Override // java.util.function.BiFunction
                                    public final Object apply(Object obj, Object obj2) {
                                        return BufferExtension.lambda$new$6(
                                                (MediaFormat) obj, (ByteBuffer) obj2);
                                    }
                                })
                        .addTransform(
                                HardwareBuffer.class,
                                ByteBuffer.class,
                                new BiFunction() { // from class:
                                                   // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda33
                                    @Override // java.util.function.BiFunction
                                    public final Object apply(Object obj, Object obj2) {
                                        return BufferExtension.lambda$new$7(
                                                (MediaFormat) obj, (HardwareBuffer) obj2);
                                    }
                                })
                        .addTransform(
                                Bitmap.class,
                                ByteBuffer.class,
                                new BiFunction() { // from class:
                                                   // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda34
                                    @Override // java.util.function.BiFunction
                                    public final Object apply(Object obj, Object obj2) {
                                        return BufferExtension.lambda$new$8(
                                                (MediaFormat) obj, (Bitmap) obj2);
                                    }
                                })
                        .addTransform(
                                ByteBuffer.class,
                                Bitmap.class,
                                new BiFunction() { // from class:
                                                   // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda22
                                    @Override // java.util.function.BiFunction
                                    public final Object apply(Object obj, Object obj2) {
                                        return BufferExtension.lambda$new$9(
                                                (MediaFormat) obj, (ByteBuffer) obj2);
                                    }
                                })
                        .addTransform(
                                ByteBuffer.class,
                                UniExifInterface.class,
                                new BiFunction() { // from class:
                                                   // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda23
                                    @Override // java.util.function.BiFunction
                                    public final Object apply(Object obj, Object obj2) {
                                        return BufferExtension.lambda$new$10(
                                                (MediaFormat) obj, (ByteBuffer) obj2);
                                    }
                                })
                        .addStringfy(
                                HardwareBuffer.class,
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda24
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        String fmtstr;
                                        fmtstr =
                                                Def.fmtstr(
                                                        HardwareBuffer.class.getName()
                                                                + "[w=%d, h=%d, fmt=%d]",
                                                        Integer.valueOf(r1.getWidth()),
                                                        Integer.valueOf(r1.getHeight()),
                                                        Integer.valueOf(
                                                                ((HardwareBuffer) obj)
                                                                        .getFormat()));
                                        return fmtstr;
                                    }
                                })
                        .addDealloc(
                                HardwareBuffer.class,
                                new Consumer() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda25
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ((HardwareBuffer) obj).close();
                                    }
                                });
        registerDescribe(registry.getDescribe());
        registerAlloc(registry.getAlloc());
        registerDealloc(registry.getDealloc());
        registerStringfy(registry.getStringfy());
        registerTransform(registry.getTransform());
        registerWrappedTransform(registry.getWrappedTransform());
    }

    static /* synthetic */ MutableMediaFormat lambda$new$0(Number number) {
        MutableMediaFormat format = MediaFormat.mutableOf(MediaType.SCALA, Shape.of(1, 1));
        if (number instanceof Byte) {
            format.setDataType(DataType.U8C1);
        } else if (number instanceof Integer) {
            format.setDataType(DataType.U32C1);
        } else if (number instanceof Float) {
            format.setDataType(DataType.F32C1);
        } else {
            throw new UnsupportedOperationException("implement not yet");
        }
        return format;
    }

    static /* synthetic */ MutableMediaFormat lambda$new$2(Bitmap bitmap) {
        MutableMediaFormat fmt =
                MediaFormat.mutableImageOf(Shape.rectOf(bitmap.getWidth(), bitmap.getHeight()));
        fmt.setDataType(DataType.U8C3);
        fmt.setColorFormat(ColorFormat.RGB);
        fmt.setColorSpace(ColorSpace.of(bitmap));
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
        return fmt;
    }

    static /* synthetic */ ByteBuffer lambda$new$3(MediaFormat format, Number number) {
        if (number instanceof Integer) {
            ByteBuffer buffer = ByteBuffer.allocate(32);
            buffer.asIntBuffer().put(((Integer) number).intValue());
            return buffer;
        }
        if (number instanceof Long) {
            ByteBuffer buffer2 = ByteBuffer.allocate(64);
            buffer2.asLongBuffer().put(((Long) number).longValue());
            return buffer2;
        }
        if (number instanceof Float) {
            ByteBuffer buffer3 = ByteBuffer.allocate(32);
            buffer3.asFloatBuffer().put(((Float) number).floatValue());
            return buffer3;
        }
        if (number instanceof Byte) {
            ByteBuffer buffer4 = ByteBuffer.allocate(8);
            buffer4.put(((Byte) number).byteValue());
            return buffer4;
        }
        if (number instanceof Short) {
            ByteBuffer buffer5 = ByteBuffer.allocate(16);
            buffer5.asShortBuffer().put(((Short) number).shortValue());
            return buffer5;
        }
        throw new UnsupportedOperationException("not supported number type");
    }

    static /* synthetic */ Number lambda$new$4(MediaFormat format, ByteBuffer byteBuffer) {
        Def.check(format.getMediaType().isScala(), "media is not scala", new Object[0]);
        if (format.getDataType().isInt()) {
            return Integer.valueOf(byteBuffer.asIntBuffer().get(0));
        }
        if (format.getDataType().isLong()) {
            return Long.valueOf(byteBuffer.asLongBuffer().get(0));
        }
        if (format.getDataType().isFloat()) {
            return Float.valueOf(byteBuffer.asFloatBuffer().get(0));
        }
        if (format.getDataType().isByte()) {
            return Byte.valueOf(byteBuffer.get(0));
        }
        if (format.getDataType().isShort()) {
            return Short.valueOf(byteBuffer.asShortBuffer().get(0));
        }
        throw new UnsupportedOperationException("not supported scala type");
    }

    static /* synthetic */ HardwareBuffer lambda$new$6(MediaFormat format, ByteBuffer srcBuffer) {
        HardwareBuffer dstBuffer = SharedBufferManager.create(format);
        SharedBufferManager.copyFromByteBuffer(format, srcBuffer, dstBuffer);
        return dstBuffer;
    }

    static /* synthetic */ ByteBuffer lambda$new$7(MediaFormat format, HardwareBuffer srcBuffer) {
        ByteBuffer dstBuffer = ByteBuffer.allocateDirect((int) format.size());
        SharedBufferManager.copyToByteBuffer(format, srcBuffer, dstBuffer);
        return dstBuffer;
    }

    static /* synthetic */ ByteBuffer lambda$new$8(MediaFormat format, Bitmap srcBuffer) {
        ByteBuffer dstBuffer = ByteBuffer.allocateDirect(srcBuffer.getByteCount());
        srcBuffer.copyPixelsToBuffer(dstBuffer);
        dstBuffer.rewind();
        return dstBuffer;
    }

    static /* synthetic */ Bitmap lambda$new$9(MediaFormat format, ByteBuffer srcBuffer) {
        Def.check(format.getColorFormat() == ColorFormat.RGBA);
        Bitmap dstBuffer =
                Bitmap.createBitmap(format.getCols(), format.getRows(), Bitmap.Config.ARGB_8888);
        dstBuffer.copyPixelsFromBuffer(srcBuffer);
        return dstBuffer;
    }

    static /* synthetic */ UniExifInterface lambda$new$10(
            MediaFormat format, ByteBuffer srcBuffer) {
        Def.check(format.contains("exif"));
        return UniExifInterface.of(srcBuffer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> String getUnaryKey(Class<T> clazz) {
        return clazz.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T, R> String getBinaryKey(Class<T> from, Class<R> to) {
        return from.getName() + "->" + to.getName();
    }

    private void addToClassMap(Class<?> clazz) {
        if (!clazz.getPackage().getName().startsWith("android")
                && !clazz.getPackage().getName().startsWith("java")) {
            this.extensionClassMap.put(clazz.getName(), clazz);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerDescribe(
            Map<Class<?>, Function<?, MutableMediaFormat>> describeMap) {
        Map<String, Function<?, MutableMediaFormat>> map =
                (Map)
                        describeMap.entrySet().stream()
                                .collect(
                                        Collectors.toMap(
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda8
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return BufferExtension.this.m9104x94e37bf4(
                                                                (Map.Entry) obj);
                                                    }
                                                },
                                                new BufferExtension$$ExternalSyntheticLambda9()));
        this.describeMap.putAll(map);
        return this;
    }

    /* renamed from: lambda$registerDescribe$12$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9104x94e37bf4(Map.Entry it) {
        addToClassMap((Class) it.getKey());
        return getUnaryKey((Class) it.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterDescribe(final List<String> deallocList) {
        this.describeMap
                .entrySet()
                .removeIf(
                        new Predicate() { // from class:
                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda18
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean anyMatch;
                                anyMatch =
                                        deallocList.stream()
                                                .anyMatch(
                                                        new Predicate() { // from class:
                                                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda15
                                                            @Override // java.util.function.Predicate
                                                            public final boolean test(Object obj2) {
                                                                boolean equals;
                                                                equals =
                                                                        ((String) obj2)
                                                                                .equals(
                                                                                        r1
                                                                                                .getKey());
                                                                return equals;
                                                            }
                                                        });
                                return anyMatch;
                            }
                        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerAlloc(Map<Class<?>, Function<MediaFormat, ?>> allocMap) {
        Map<String, Function<MediaFormat, ?>> map =
                (Map)
                        allocMap.entrySet().stream()
                                .collect(
                                        Collectors.toMap(
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda37
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return BufferExtension.this.m9102xe9dbe83b(
                                                                (Map.Entry) obj);
                                                    }
                                                },
                                                new BufferExtension$$ExternalSyntheticLambda9()));
        this.allocMap.putAll(map);
        return this;
    }

    /* renamed from: lambda$registerAlloc$15$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9102xe9dbe83b(Map.Entry it) {
        addToClassMap((Class) it.getKey());
        return getUnaryKey((Class) it.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterAlloc(final List<String> allocList) {
        this.allocMap
                .entrySet()
                .removeIf(
                        new Predicate() { // from class:
                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda14
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean anyMatch;
                                anyMatch =
                                        allocList.stream()
                                                .anyMatch(
                                                        new Predicate() { // from class:
                                                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda10
                                                            @Override // java.util.function.Predicate
                                                            public final boolean test(Object obj2) {
                                                                boolean equals;
                                                                equals =
                                                                        ((String) obj2)
                                                                                .equals(
                                                                                        r1
                                                                                                .getKey());
                                                                return equals;
                                                            }
                                                        });
                                return anyMatch;
                            }
                        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerDealloc(Map<Class<?>, Consumer<?>> deallocMap) {
        Map<String, Consumer<?>> map =
                (Map)
                        deallocMap.entrySet().stream()
                                .collect(
                                        Collectors.toMap(
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda4
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return BufferExtension.this.m9103xdd0ed737(
                                                                (Map.Entry) obj);
                                                    }
                                                },
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda5
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return (Consumer)
                                                                ((Map.Entry) obj).getValue();
                                                    }
                                                }));
        this.deallocMap.putAll(map);
        return this;
    }

    /* renamed from: lambda$registerDealloc$18$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9103xdd0ed737(Map.Entry it) {
        addToClassMap((Class) it.getKey());
        return getUnaryKey((Class) it.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterDealloc(final List<String> deallocList) {
        this.deallocMap
                .entrySet()
                .removeIf(
                        new Predicate() { // from class:
                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda17
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean anyMatch;
                                anyMatch =
                                        deallocList.stream()
                                                .anyMatch(
                                                        new Predicate() { // from class:
                                                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda41
                                                            @Override // java.util.function.Predicate
                                                            public final boolean test(Object obj2) {
                                                                boolean equals;
                                                                equals =
                                                                        ((String) obj2)
                                                                                .equals(
                                                                                        r1
                                                                                                .getKey());
                                                                return equals;
                                                            }
                                                        });
                                return anyMatch;
                            }
                        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerTransform(
            Map<Pair<Class<?>, Class<?>>, BiFunction<MediaFormat, ?, ?>> transforms) {
        Map<String, TransformFunction> map =
                (Map)
                        transforms.entrySet().stream()
                                .collect(
                                        Collectors.toMap(
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda6
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return BufferExtension.this.m9106x297a7a37(
                                                                (Map.Entry) obj);
                                                    }
                                                },
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return BufferExtension
                                                                .lambda$registerTransform$22(
                                                                        (Map.Entry) obj);
                                                    }
                                                }));
        this.transformMap.putAll(map);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$registerTransform$21$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9106x297a7a37(Map.Entry it) {
        Class<?> from = (Class) ((Pair) it.getKey()).first;
        Class<?> to = (Class) ((Pair) it.getKey()).second;
        addToClassMap(from);
        addToClassMap(to);
        return getBinaryKey(from, to);
    }

    static /* synthetic */ TransformFunction lambda$registerTransform$22(Map.Entry it) {
        return new TransformFunction(
                (BiFunction<MediaFormat, ?, ?>[]) new BiFunction[] {(BiFunction) it.getValue()});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerWrappedTransform(List<Integer> wrappedTransforms) {
        this.wrappedTransformList.addAll(wrappedTransforms);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterTransform(final List<String> transforms) {
        this.transformMap
                .entrySet()
                .removeIf(
                        new Predicate() { // from class:
                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean anyMatch;
                                anyMatch =
                                        transforms.stream()
                                                .anyMatch(
                                                        new Predicate() { // from class:
                                                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda16
                                                            @Override // java.util.function.Predicate
                                                            public final boolean test(Object obj2) {
                                                                boolean equals;
                                                                equals =
                                                                        ((String) obj2)
                                                                                .equals(
                                                                                        r1
                                                                                                .getKey());
                                                                return equals;
                                                            }
                                                        });
                                return anyMatch;
                            }
                        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerStringfy(Map<Class<?>, Function<?, String>> stringfy) {
        Map<String, Function<?, String>> map =
                (Map)
                        stringfy.entrySet().stream()
                                .collect(
                                        Collectors.toMap(
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda42
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return BufferExtension.this.m9105x6defe3eb(
                                                                (Map.Entry) obj);
                                                    }
                                                },
                                                new BufferExtension$$ExternalSyntheticLambda9()));
        this.stringfyMap.putAll(map);
        return this;
    }

    /* renamed from: lambda$registerStringfy$25$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9105x6defe3eb(Map.Entry it) {
        addToClassMap((Class) it.getKey());
        return getUnaryKey((Class) it.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterStringfy(final List<String> stringfy) {
        this.stringfyMap
                .entrySet()
                .removeIf(
                        new Predicate() { // from class:
                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda3
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean anyMatch;
                                anyMatch =
                                        stringfy.stream()
                                                .anyMatch(
                                                        new Predicate() { // from class:
                                                                          // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda20
                                                            @Override // java.util.function.Predicate
                                                            public final boolean test(Object obj2) {
                                                                boolean equals;
                                                                equals =
                                                                        ((String) obj2)
                                                                                .equals(
                                                                                        r1
                                                                                                .getKey());
                                                                return equals;
                                                            }
                                                        });
                                return anyMatch;
                            }
                        });
        return this;
    }

    private <T> MutableMediaFormat doDescribe(T data) {
        String key = findAvailableUnaryKey(data.getClass(), this.describeMap);
        Function<?, MutableMediaFormat> function = this.describeMap.get(key);
        if (function != null) {
            return function.apply(data);
        }
        throw new UnsupportedOperationException();
    }

    private <T> T doAlloc(MediaFormat mediaFormat, Class<T> cls) {
        Function<MediaFormat, ?> function =
                this.allocMap.get(findAvailableUnaryKey(cls, this.allocMap));
        if (function != null) {
            return (T) function.apply(mediaFormat);
        }
        throw new UnsupportedOperationException();
    }

    private <T> void doDealloc(T data) {
        String key = findAvailableUnaryKey(data.getClass(), this.deallocMap);
        Consumer<?> consumer = this.deallocMap.get(key);
        if (consumer != null) {
            consumer.accept(data);
            return;
        }
        throw new UnsupportedOperationException();
    }

    private <T, R> R doTransform(MediaFormat mediaFormat, T t, Class<R> cls) {
        return (R)
                this.transformMap
                        .get(findAvailableBinaryKey(t.getClass(), cls, this.transformMap))
                        .apply(mediaFormat, t);
    }

    private <T> String doStringfy(final T data) {
        String key = findAvailableUnaryKey(data.getClass(), this.stringfyMap);
        Optional<Function<T, String>> stringfy = Optional.ofNullable(this.stringfyMap.get(key));
        return (String)
                stringfy.map(
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda19
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return BufferExtension.lambda$doStringfy$28(
                                                data, (Function) obj);
                                    }
                                })
                        .orElse(data.toString());
    }

    static /* synthetic */ String lambda$doStringfy$28(Object data, Function it) {
        return (String) it.apply(data);
    }

    private <T, R> String findAvailableUnaryKey(final Class<T> clazz, Map<String, R> registry) {
        String key = getUnaryKey(clazz);
        if (!registry.containsKey(key)) {
            String found =
                    registry.keySet().stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return BufferExtension.lambda$findAvailableUnaryKey$29(
                                                    clazz, (String) obj);
                                        }
                                    })
                            .findFirst()
                            .orElseThrow(
                                    new Supplier() { // from class:
                                                     // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return BufferExtension.lambda$findAvailableUnaryKey$30(
                                                    clazz);
                                        }
                                    });
            registry.put(key, registry.get(found));
        }
        return key;
    }

    static /* synthetic */ boolean lambda$findAvailableUnaryKey$29(Class clazz, String it) {
        try {
            return Class.forName(it).isAssignableFrom(clazz);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    static /* synthetic */ UnsupportedOperationException lambda$findAvailableUnaryKey$30(
            Class clazz) {
        return new UnsupportedOperationException("no extension exist for " + clazz);
    }

    private <T1, T2> String findAvailableBinaryKey(
            final Class<T1> from,
            final Class<T2> to,
            final Map<String, TransformFunction> registry) {
        final String key = getBinaryKey(from, to);
        if (registry.containsKey(key)) {
            return key;
        }
        Log.d(TAG, "no transform exist for " + key + ", find alternatives");
        final List<Pair<Class<?>, Class<?>>> fromList = new ArrayList<>();
        final List<Pair<Class<?>, Class<?>>> toList = new ArrayList<>();
        Optional<String> findFirst =
                registry.keySet().stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda38
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return BufferExtension.this.m9098x6a4f7af7(
                                                from, to, key, fromList, toList, (String) obj);
                                    }
                                })
                        .findFirst();
        Objects.requireNonNull(registry);
        TransformFunction found =
                (TransformFunction)
                        findFirst
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda39
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return (BufferExtension.TransformFunction)
                                                        registry.get((String) obj);
                                            }
                                        })
                                .orElseGet(
                                        new Supplier() { // from class:
                                                         // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda40
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                return BufferExtension.this.m9101xecd8f412(
                                                        fromList, toList, key, registry, from, to);
                                            }
                                        });
        registry.put(key, found);
        return key;
    }

    /* renamed from: lambda$findAvailableBinaryKey$31$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ boolean m9098x6a4f7af7(
            Class from, Class to, String key, List fromList, List toList, String it) {
        try {
            String[] token = it.split("->");
            Class<?> fromClass = this.extensionClassMap.get(token[0]);
            if (fromClass == null) {
                fromClass = Class.forName(token[0]);
            }
            Class<?> toClass = this.extensionClassMap.get(token[1]);
            if (toClass == null) {
                toClass = Class.forName(token[1]);
            }
            if (fromClass.isAssignableFrom(from) && toClass.isAssignableFrom(to)) {
                Log.d(TAG, "find alternative for " + key + ": " + it);
                return true;
            }
            if (fromClass.isAssignableFrom(from)) {
                fromList.add(new Pair(fromClass, toClass));
            } else if (toClass.isAssignableFrom(to)) {
                toList.add(new Pair(fromClass, toClass));
            }
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$findAvailableBinaryKey$33$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ TransformFunction m9099x9e867835(Pair it1, String key, Map registry, Pair it2) {
        String first = getBinaryKey((Class) it1.first, (Class) it1.second);
        String second = getBinaryKey((Class) it2.first, (Class) it2.second);
        Log.d(TAG, "find 2nd order combinations for" + key + ": " + first + " => " + second);
        return new TransformFunction(
                (TransformFunction) registry.get(first), (TransformFunction) registry.get(second));
    }

    /* renamed from: lambda$findAvailableBinaryKey$34$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ TransformFunction m9100xb8a1f6d4(
            List toList, final String key, final Map registry, final Pair it1) {
        return (TransformFunction)
                toList.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda35
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        boolean isAssignableFrom;
                                        isAssignableFrom =
                                                ((Class) Pair.this.second)
                                                        .isAssignableFrom(
                                                                (Class) ((Pair) obj).first);
                                        return isAssignableFrom;
                                    }
                                })
                        .findFirst()
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda36
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return BufferExtension.this.m9099x9e867835(
                                                it1, key, registry, (Pair) obj);
                                    }
                                })
                        .orElse(null);
    }

    static /* synthetic */ UnsupportedOperationException lambda$findAvailableBinaryKey$35(
            Class from, Class to) {
        return new UnsupportedOperationException("no extension exist for " + from + " -> " + to);
    }

    /* renamed from: lambda$findAvailableBinaryKey$36$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ TransformFunction m9101xecd8f412(
            List fromList,
            final List toList,
            final String key,
            final Map registry,
            final Class from,
            final Class to) {
        return (TransformFunction)
                fromList.stream()
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda11
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return BufferExtension.this.m9100xb8a1f6d4(
                                                toList, key, registry, (Pair) obj);
                                    }
                                })
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda12
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return Objects.nonNull(
                                                (BufferExtension.TransformFunction) obj);
                                    }
                                })
                        .findFirst()
                        .orElseThrow(
                                new Supplier() { // from class:
                                                 // com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda13
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return BufferExtension.lambda$findAvailableBinaryKey$35(
                                                from, to);
                                    }
                                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TransformFunction {
        private final List<BiFunction<MediaFormat, ?, ?>> functionList;

        @SafeVarargs
        TransformFunction(BiFunction<MediaFormat, ?, ?>... transforms) {
            this.functionList = Arrays.asList(transforms);
        }

        TransformFunction(TransformFunction... transforms) {
            this.functionList = new ArrayList();
            Arrays.asList(transforms)
                    .forEach(
                            new Consumer() { // from class:
                                             // com.samsung.android.sume.core.buffer.BufferExtension$TransformFunction$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    BufferExtension.TransformFunction.this.m9107x3e8b297f(
                                            (BufferExtension.TransformFunction) obj);
                                }
                            });
        }

        /* renamed from: lambda$new$0$com-samsung-android-sume-core-buffer-BufferExtension$TransformFunction, reason: not valid java name */
        /* synthetic */ void m9107x3e8b297f(TransformFunction it) {
            this.functionList.addAll(it.functionList);
        }

        <U, R> R apply(MediaFormat mediaFormat, U u) {
            R r = (R) u;
            Consumer popInternalBufferHandler = BufferExtension.popInternalBufferHandler();
            if (this.functionList.size() == 1) {
                popInternalBufferHandler = null;
            }
            for (BiFunction<MediaFormat, ?, ?> biFunction : this.functionList) {
                if (popInternalBufferHandler != null
                        && BufferExtension.isWrappedTransform(biFunction)) {
                    popInternalBufferHandler.accept(r);
                }
                r = (R) biFunction.apply(mediaFormat, r);
            }
            return r;
        }
    }

    public static class Registry {
        private final Map<Class<?>, Function<MediaFormat, ?>> allocMap;
        private final Map<Class<?>, Consumer<?>> deallocMap;
        private final Map<Class<?>, Function<?, MutableMediaFormat>> describeMap;
        private final Map<Class<?>, Function<?, String>> stringfyMap;
        private final Map<Pair<Class<?>, Class<?>>, BiFunction<MediaFormat, ?, ?>> transformMap;
        private final List<Integer> wrappedTransformList;

        private Registry() {
            this.describeMap = new HashMap();
            this.allocMap = new HashMap();
            this.deallocMap = new HashMap();
            this.transformMap = new HashMap();
            this.stringfyMap = new HashMap();
            this.wrappedTransformList = new ArrayList();
        }

        public <T> Registry addDescribe(Class<T> clazz, Function<T, MutableMediaFormat> describe) {
            this.describeMap.put(clazz, describe);
            return this;
        }

        public <T> Registry addAlloc(Class<T> clazz, Function<MediaFormat, ?> alloc) {
            this.allocMap.put(clazz, alloc);
            return this;
        }

        public <T, R> Registry addWrappedTransform(
                Class<T> from, Class<R> to, BiFunction<MediaFormat, T, R> mapper) {
            this.transformMap.put(new Pair<>(from, to), mapper);
            this.wrappedTransformList.add(Integer.valueOf(mapper.hashCode()));
            return this;
        }

        public <T> Registry addDealloc(Class<T> clazz, Consumer<T> dealloc) {
            this.deallocMap.put(clazz, dealloc);
            return this;
        }

        public <T, R> Registry addTransform(
                Class<T> from, Class<R> to, BiFunction<MediaFormat, T, R> transform) {
            this.transformMap.put(new Pair<>(from, to), transform);
            return this;
        }

        public <T> Registry addStringfy(Class<T> clazz, Function<T, String> stringfy) {
            this.stringfyMap.put(clazz, stringfy);
            return this;
        }

        Map<Class<?>, Function<?, MutableMediaFormat>> getDescribe() {
            return this.describeMap;
        }

        Map<Class<?>, Function<MediaFormat, ?>> getAlloc() {
            return this.allocMap;
        }

        Map<Class<?>, Consumer<?>> getDealloc() {
            return this.deallocMap;
        }

        Map<Pair<Class<?>, Class<?>>, BiFunction<MediaFormat, ?, ?>> getTransform() {
            return this.transformMap;
        }

        List<Integer> getWrappedTransform() {
            return this.wrappedTransformList;
        }

        Map<Class<?>, Function<?, String>> getStringfy() {
            return this.stringfyMap;
        }

        public void register() {
            BufferExtension.getInstance()
                    .registerDescribe(this.describeMap)
                    .registerAlloc(this.allocMap)
                    .registerDealloc(this.deallocMap)
                    .registerTransform(this.transformMap)
                    .registerWrappedTransform(this.wrappedTransformList)
                    .registerStringfy(this.stringfyMap);
        }
    }

    public static class Unregistry {
        private final List<String> allocList;
        private final List<String> deallocList;
        private final List<String> describeList;
        private final List<String> stringfyList;
        private final List<String> transformList;

        private Unregistry() {
            this.describeList = new ArrayList();
            this.allocList = new ArrayList();
            this.deallocList = new ArrayList();
            this.transformList = new ArrayList();
            this.stringfyList = new ArrayList();
        }

        public <T> Unregistry removeDescribe(Class<T> clazz) {
            this.describeList.add(BufferExtension.getInstance().getUnaryKey(clazz));
            return this;
        }

        public <T> Unregistry removeAlloc(Class<T> clazz) {
            this.allocList.add(BufferExtension.getInstance().getUnaryKey(clazz));
            return this;
        }

        public <T> Unregistry removeDealloc(Class<T> clazz) {
            this.deallocList.add(BufferExtension.getInstance().getUnaryKey(clazz));
            return this;
        }

        public <T, R> Unregistry removeTransform(Class<T> from, Class<R> to) {
            this.transformList.add(BufferExtension.getInstance().getBinaryKey(from, to));
            return this;
        }

        public <T> Unregistry removeStringfy(Class<T> clazz) {
            this.stringfyList.add(BufferExtension.getInstance().getUnaryKey(clazz));
            return this;
        }

        public void unregister() {
            BufferExtension.getInstance()
                    .unRegisterDescribe(this.describeList)
                    .unRegisterAlloc(this.allocList)
                    .unRegisterDealloc(this.deallocList)
                    .unRegisterTransform(this.transformList)
                    .unRegisterStringfy(this.stringfyList);
        }
    }
}
