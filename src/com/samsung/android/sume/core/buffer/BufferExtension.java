package com.samsung.android.sume.core.buffer;

import android.graphics.Bitmap;
import android.hardware.HardwareBuffer;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.UniExifInterface;
import com.samsung.android.sume.core.buffer.BufferExtension;
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

/* loaded from: classes6.dex */
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

    public static <T> MutableMediaFormat describe(T t) {
        return getInstance().doDescribe(t);
    }

    public static <T> T alloc(MediaFormat mediaFormat, Class<T> cls) {
        return (T) getInstance().doAlloc(mediaFormat, cls);
    }

    public static <T> void dealloc(T t) {
        getInstance().doDealloc(t);
    }

    public static <T> void putInternalBufferHandler(Consumer<T> consumer) {
        getInstance().internalBufferHandlerMap.put(Long.valueOf(Thread.currentThread().getId()), consumer);
    }

    static <T> Consumer<T> popInternalBufferHandler() {
        return (Consumer) getInstance().internalBufferHandlerMap.remove(Long.valueOf(Thread.currentThread().getId()));
    }

    static boolean isWrappedTransform(BiFunction<MediaFormat, Object, Object> biFunction) {
        return getInstance().wrappedTransformList.contains(Integer.valueOf(biFunction.hashCode()));
    }

    public static <T> String stringfy(T t) {
        return getInstance().doStringfy(t);
    }

    public static <T, R> R transform(MediaFormat mediaFormat, T t, Class<R> cls) {
        return (R) getInstance().doTransform(mediaFormat, t, cls);
    }

    public static void reset() {
        BufferExtension bufferExtension = getInstance();
        bufferExtension.extensionClassMap.clear();
        bufferExtension.describeMap.clear();
        bufferExtension.allocMap.clear();
        bufferExtension.deallocMap.clear();
        bufferExtension.transformMap.clear();
        bufferExtension.stringfyMap.clear();
    }

    public static <T, R> boolean isSupportedTransform(Class<T> cls, Class<R> cls2) {
        return getInstance().transformMap.containsKey(getInstance().getBinaryKey(cls, cls2));
    }

    public static <T> boolean isRequiredToRelease(Class<T> cls) {
        return getInstance().deallocMap.containsKey(getInstance().getUnaryKey(cls));
    }

    public static Registry newRegistry() {
        return new Registry();
    }

    public static Unregistry newUnregistry() {
        return new Unregistry();
    }

    private BufferExtension() {
        Registry addDealloc = newRegistry().addDescribe(Number.class, new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda21
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.lambda$new$0((Number) obj);
            }
        }).addDescribe(ByteBuffer.class, new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                MutableMediaFormat mutableOf;
                mutableOf = MediaFormat.mutableOf(MediaType.NONE, Shape.of(1, ((ByteBuffer) obj).limit()));
                return mutableOf;
            }
        }).addDescribe(Bitmap.class, new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda27
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.lambda$new$2((Bitmap) obj);
            }
        }).addTransform(Number.class, ByteBuffer.class, new BiFunction() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda28
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BufferExtension.lambda$new$3((MediaFormat) obj, (Number) obj2);
            }
        }).addTransform(ByteBuffer.class, Number.class, new BiFunction() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda29
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BufferExtension.lambda$new$4((MediaFormat) obj, (ByteBuffer) obj2);
            }
        }).addStringfy(ByteBuffer.class, new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda30
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ByteBuffer) obj).toString();
            }
        }).addStringfy(ParcelFileDescriptor.class, new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda31
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String fmtstr;
                fmtstr = Def.fmtstr("fd=%d, len=%ld", Integer.valueOf(r1.getFd()), Long.valueOf(((ParcelFileDescriptor) obj).getStatSize()));
                return fmtstr;
            }
        }).addTransform(ByteBuffer.class, HardwareBuffer.class, new BiFunction() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda32
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BufferExtension.lambda$new$6((MediaFormat) obj, (ByteBuffer) obj2);
            }
        }).addTransform(HardwareBuffer.class, ByteBuffer.class, new BiFunction() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda33
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BufferExtension.lambda$new$7((MediaFormat) obj, (HardwareBuffer) obj2);
            }
        }).addTransform(Bitmap.class, ByteBuffer.class, new BiFunction() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda34
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BufferExtension.lambda$new$8((MediaFormat) obj, (Bitmap) obj2);
            }
        }).addTransform(ByteBuffer.class, Bitmap.class, new BiFunction() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda22
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BufferExtension.lambda$new$9((MediaFormat) obj, (ByteBuffer) obj2);
            }
        }).addTransform(ByteBuffer.class, UniExifInterface.class, new BiFunction() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda23
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BufferExtension.lambda$new$10((MediaFormat) obj, (ByteBuffer) obj2);
            }
        }).addStringfy(HardwareBuffer.class, new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String fmtstr;
                fmtstr = Def.fmtstr(HardwareBuffer.class.getName() + "[w=%d, h=%d, fmt=%d]", Integer.valueOf(r1.getWidth()), Integer.valueOf(r1.getHeight()), Integer.valueOf(((HardwareBuffer) obj).getFormat()));
                return fmtstr;
            }
        }).addDealloc(HardwareBuffer.class, new Consumer() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((HardwareBuffer) obj).close();
            }
        });
        registerDescribe(addDealloc.getDescribe());
        registerAlloc(addDealloc.getAlloc());
        registerDealloc(addDealloc.getDealloc());
        registerStringfy(addDealloc.getStringfy());
        registerTransform(addDealloc.getTransform());
        registerWrappedTransform(addDealloc.getWrappedTransform());
    }

    static /* synthetic */ MutableMediaFormat lambda$new$0(Number number) {
        MutableMediaFormat mutableOf = MediaFormat.mutableOf(MediaType.SCALA, Shape.of(1, 1));
        if (number instanceof Byte) {
            mutableOf.setDataType(DataType.U8C1);
            return mutableOf;
        }
        if (number instanceof Integer) {
            mutableOf.setDataType(DataType.U32C1);
            return mutableOf;
        }
        if (number instanceof Float) {
            mutableOf.setDataType(DataType.F32C1);
            return mutableOf;
        }
        throw new UnsupportedOperationException("implement not yet");
    }

    static /* synthetic */ MutableMediaFormat lambda$new$2(Bitmap bitmap) {
        MutableMediaFormat mutableImageOf = MediaFormat.mutableImageOf(Shape.rectOf(bitmap.getWidth(), bitmap.getHeight()));
        mutableImageOf.setDataType(DataType.U8C3);
        mutableImageOf.setColorFormat(ColorFormat.RGB);
        mutableImageOf.setColorSpace(ColorSpace.of(bitmap));
        float size = mutableImageOf.size() / bitmap.getByteCount();
        if (Math.round(size * 100.0f) / 100.0f == 0.75f) {
            mutableImageOf.setDataType(DataType.U8C4);
            mutableImageOf.setColorFormat(ColorFormat.RGBA);
            return mutableImageOf;
        }
        if (Math.round(size * 10.0f) / 10.0f == 0.5f) {
            mutableImageOf.setDataType(DataType.U16C3);
            return mutableImageOf;
        }
        if (Math.round(size * 1000.0f) / 1000.0f == 0.375f) {
            mutableImageOf.setDataType(DataType.U16C4);
            mutableImageOf.setColorFormat(ColorFormat.RGBA);
            return mutableImageOf;
        }
        if (Math.round(size) == 3) {
            mutableImageOf.setDataType(DataType.U8C1);
            mutableImageOf.setColorFormat(ColorFormat.GRAY);
            return mutableImageOf;
        }
        throw new IllegalArgumentException("byte count +" + bitmap.getByteCount() + "is differ from calculated buffer size" + mutableImageOf.size());
    }

    static /* synthetic */ ByteBuffer lambda$new$3(MediaFormat mediaFormat, Number number) {
        if (number instanceof Integer) {
            ByteBuffer allocate = ByteBuffer.allocate(32);
            allocate.asIntBuffer().put(((Integer) number).intValue());
            return allocate;
        }
        if (number instanceof Long) {
            ByteBuffer allocate2 = ByteBuffer.allocate(64);
            allocate2.asLongBuffer().put(((Long) number).longValue());
            return allocate2;
        }
        if (number instanceof Float) {
            ByteBuffer allocate3 = ByteBuffer.allocate(32);
            allocate3.asFloatBuffer().put(((Float) number).floatValue());
            return allocate3;
        }
        if (number instanceof Byte) {
            ByteBuffer allocate4 = ByteBuffer.allocate(8);
            allocate4.put(((Byte) number).byteValue());
            return allocate4;
        }
        if (number instanceof Short) {
            ByteBuffer allocate5 = ByteBuffer.allocate(16);
            allocate5.asShortBuffer().put(((Short) number).shortValue());
            return allocate5;
        }
        throw new UnsupportedOperationException("not supported number type");
    }

    static /* synthetic */ Number lambda$new$4(MediaFormat mediaFormat, ByteBuffer byteBuffer) {
        Def.check(mediaFormat.getMediaType().isScala(), "media is not scala", new Object[0]);
        if (mediaFormat.getDataType().isInt()) {
            return Integer.valueOf(byteBuffer.asIntBuffer().get(0));
        }
        if (mediaFormat.getDataType().isLong()) {
            return Long.valueOf(byteBuffer.asLongBuffer().get(0));
        }
        if (mediaFormat.getDataType().isFloat()) {
            return Float.valueOf(byteBuffer.asFloatBuffer().get(0));
        }
        if (mediaFormat.getDataType().isByte()) {
            return Byte.valueOf(byteBuffer.get(0));
        }
        if (mediaFormat.getDataType().isShort()) {
            return Short.valueOf(byteBuffer.asShortBuffer().get(0));
        }
        throw new UnsupportedOperationException("not supported scala type");
    }

    static /* synthetic */ HardwareBuffer lambda$new$6(MediaFormat mediaFormat, ByteBuffer byteBuffer) {
        HardwareBuffer create = SharedBufferManager.create(mediaFormat);
        SharedBufferManager.copyFromByteBuffer(mediaFormat, byteBuffer, create);
        return create;
    }

    static /* synthetic */ ByteBuffer lambda$new$7(MediaFormat mediaFormat, HardwareBuffer hardwareBuffer) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect((int) mediaFormat.size());
        SharedBufferManager.copyToByteBuffer(mediaFormat, hardwareBuffer, allocateDirect);
        return allocateDirect;
    }

    static /* synthetic */ ByteBuffer lambda$new$8(MediaFormat mediaFormat, Bitmap bitmap) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(allocateDirect);
        allocateDirect.rewind();
        return allocateDirect;
    }

    static /* synthetic */ Bitmap lambda$new$9(MediaFormat mediaFormat, ByteBuffer byteBuffer) {
        Def.check(mediaFormat.getColorFormat() == ColorFormat.RGBA);
        Bitmap createBitmap = Bitmap.createBitmap(mediaFormat.getCols(), mediaFormat.getRows(), Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(byteBuffer);
        return createBitmap;
    }

    static /* synthetic */ UniExifInterface lambda$new$10(MediaFormat mediaFormat, ByteBuffer byteBuffer) {
        Def.check(mediaFormat.contains("exif"));
        return UniExifInterface.of(byteBuffer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> String getUnaryKey(Class<T> cls) {
        return cls.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T, R> String getBinaryKey(Class<T> cls, Class<R> cls2) {
        return cls.getName() + "->" + cls2.getName();
    }

    private void addToClassMap(Class<?> cls) {
        if (cls.getPackage().getName().startsWith("android") || cls.getPackage().getName().startsWith("java")) {
            return;
        }
        this.extensionClassMap.put(cls.getName(), cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerDescribe(Map<Class<?>, Function<?, MutableMediaFormat>> map) {
        this.describeMap.putAll((Map) map.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.this.m9505x94e37bf4((Map.Entry) obj);
            }
        }, new BufferExtension$$ExternalSyntheticLambda9())));
        return this;
    }

    /* renamed from: lambda$registerDescribe$12$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9505x94e37bf4(Map.Entry entry) {
        addToClassMap((Class) entry.getKey());
        return getUnaryKey((Class) entry.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterDescribe(final List<String> list) {
        this.describeMap.entrySet().removeIf(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean anyMatch;
                anyMatch = list.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        boolean equals;
                        equals = ((String) obj2).equals(r1.getKey());
                        return equals;
                    }
                });
                return anyMatch;
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerAlloc(Map<Class<?>, Function<MediaFormat, ?>> map) {
        this.allocMap.putAll((Map) map.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda37
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.this.m9503xe9dbe83b((Map.Entry) obj);
            }
        }, new BufferExtension$$ExternalSyntheticLambda9())));
        return this;
    }

    /* renamed from: lambda$registerAlloc$15$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9503xe9dbe83b(Map.Entry entry) {
        addToClassMap((Class) entry.getKey());
        return getUnaryKey((Class) entry.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterAlloc(final List<String> list) {
        this.allocMap.entrySet().removeIf(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean anyMatch;
                anyMatch = list.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        boolean equals;
                        equals = ((String) obj2).equals(r1.getKey());
                        return equals;
                    }
                });
                return anyMatch;
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerDealloc(Map<Class<?>, Consumer<?>> map) {
        this.deallocMap.putAll((Map) map.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.this.m9504xdd0ed737((Map.Entry) obj);
            }
        }, new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Consumer) ((Map.Entry) obj).getValue();
            }
        })));
        return this;
    }

    /* renamed from: lambda$registerDealloc$18$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9504xdd0ed737(Map.Entry entry) {
        addToClassMap((Class) entry.getKey());
        return getUnaryKey((Class) entry.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterDealloc(final List<String> list) {
        this.deallocMap.entrySet().removeIf(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean anyMatch;
                anyMatch = list.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda41
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        boolean equals;
                        equals = ((String) obj2).equals(r1.getKey());
                        return equals;
                    }
                });
                return anyMatch;
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerTransform(Map<Pair<Class<?>, Class<?>>, BiFunction<MediaFormat, ?, ?>> map) {
        this.transformMap.putAll((Map) map.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.this.m9507x297a7a37((Map.Entry) obj);
            }
        }, new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.lambda$registerTransform$22((Map.Entry) obj);
            }
        })));
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$registerTransform$21$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9507x297a7a37(Map.Entry entry) {
        Class<?> cls = (Class) ((Pair) entry.getKey()).first;
        Class<?> cls2 = (Class) ((Pair) entry.getKey()).second;
        addToClassMap(cls);
        addToClassMap(cls2);
        return getBinaryKey(cls, cls2);
    }

    static /* synthetic */ TransformFunction lambda$registerTransform$22(Map.Entry entry) {
        return new TransformFunction((BiFunction<MediaFormat, ?, ?>[]) new BiFunction[]{(BiFunction) entry.getValue()});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerWrappedTransform(List<Integer> list) {
        this.wrappedTransformList.addAll(list);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterTransform(final List<String> list) {
        this.transformMap.entrySet().removeIf(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean anyMatch;
                anyMatch = list.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        boolean equals;
                        equals = ((String) obj2).equals(r1.getKey());
                        return equals;
                    }
                });
                return anyMatch;
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension registerStringfy(Map<Class<?>, Function<?, String>> map) {
        this.stringfyMap.putAll((Map) map.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda42
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.this.m9506x6defe3eb((Map.Entry) obj);
            }
        }, new BufferExtension$$ExternalSyntheticLambda9())));
        return this;
    }

    /* renamed from: lambda$registerStringfy$25$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ String m9506x6defe3eb(Map.Entry entry) {
        addToClassMap((Class) entry.getKey());
        return getUnaryKey((Class) entry.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BufferExtension unRegisterStringfy(final List<String> list) {
        this.stringfyMap.entrySet().removeIf(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean anyMatch;
                anyMatch = list.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        boolean equals;
                        equals = ((String) obj2).equals(r1.getKey());
                        return equals;
                    }
                });
                return anyMatch;
            }
        });
        return this;
    }

    private <T> MutableMediaFormat doDescribe(T t) {
        Function<?, MutableMediaFormat> function = this.describeMap.get(findAvailableUnaryKey(t.getClass(), this.describeMap));
        if (function != null) {
            return function.apply(t);
        }
        throw new UnsupportedOperationException();
    }

    private <T> T doAlloc(MediaFormat mediaFormat, Class<T> cls) {
        Function<MediaFormat, ?> function = this.allocMap.get(findAvailableUnaryKey(cls, this.allocMap));
        if (function != null) {
            return (T) function.apply(mediaFormat);
        }
        throw new UnsupportedOperationException();
    }

    private <T> void doDealloc(T t) {
        Consumer<?> consumer = this.deallocMap.get(findAvailableUnaryKey(t.getClass(), this.deallocMap));
        if (consumer != null) {
            consumer.accept(t);
            return;
        }
        throw new UnsupportedOperationException();
    }

    private <T, R> R doTransform(MediaFormat mediaFormat, T t, Class<R> cls) {
        return (R) this.transformMap.get(findAvailableBinaryKey(t.getClass(), cls, this.transformMap)).apply(mediaFormat, t);
    }

    private <T> String doStringfy(final T t) {
        return (String) Optional.ofNullable(this.stringfyMap.get(findAvailableUnaryKey(t.getClass(), this.stringfyMap))).map(new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.lambda$doStringfy$28(t, (Function) obj);
            }
        }).orElse(t.toString());
    }

    static /* synthetic */ String lambda$doStringfy$28(Object obj, Function function) {
        return (String) function.apply(obj);
    }

    private <T, R> String findAvailableUnaryKey(final Class<T> cls, Map<String, R> map) {
        String unaryKey = getUnaryKey(cls);
        if (!map.containsKey(unaryKey)) {
            map.put(unaryKey, map.get(map.keySet().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return BufferExtension.lambda$findAvailableUnaryKey$29(cls, (String) obj);
                }
            }).findFirst().orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return BufferExtension.lambda$findAvailableUnaryKey$30(cls);
                }
            })));
        }
        return unaryKey;
    }

    static /* synthetic */ boolean lambda$findAvailableUnaryKey$29(Class cls, String str) {
        try {
            return Class.forName(str).isAssignableFrom(cls);
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    static /* synthetic */ UnsupportedOperationException lambda$findAvailableUnaryKey$30(Class cls) {
        return new UnsupportedOperationException("no extension exist for " + cls);
    }

    private <T1, T2> String findAvailableBinaryKey(final Class<T1> cls, final Class<T2> cls2, final Map<String, TransformFunction> map) {
        final String binaryKey = getBinaryKey(cls, cls2);
        if (!map.containsKey(binaryKey)) {
            Log.d(TAG, "no transform exist for " + binaryKey + ", find alternatives");
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            Optional<String> findFirst = map.keySet().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda38
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return BufferExtension.this.m9499x6a4f7af7(cls, cls2, binaryKey, arrayList, arrayList2, (String) obj);
                }
            }).findFirst();
            Objects.requireNonNull(map);
            map.put(binaryKey, (TransformFunction) findFirst.map(new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda39
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (BufferExtension.TransformFunction) map.get((String) obj);
                }
            }).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda40
                @Override // java.util.function.Supplier
                public final Object get() {
                    return BufferExtension.this.m9502xecd8f412(arrayList, arrayList2, binaryKey, map, cls, cls2);
                }
            }));
        }
        return binaryKey;
    }

    /* renamed from: lambda$findAvailableBinaryKey$31$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ boolean m9499x6a4f7af7(Class cls, Class cls2, String str, List list, List list2, String str2) {
        try {
            String[] split = str2.split("->");
            Class<?> cls3 = this.extensionClassMap.get(split[0]);
            if (cls3 == null) {
                cls3 = Class.forName(split[0]);
            }
            Class<?> cls4 = this.extensionClassMap.get(split[1]);
            if (cls4 == null) {
                cls4 = Class.forName(split[1]);
            }
            if (!cls3.isAssignableFrom(cls) || !cls4.isAssignableFrom(cls2)) {
                if (cls3.isAssignableFrom(cls)) {
                    list.add(new Pair(cls3, cls4));
                } else if (cls4.isAssignableFrom(cls2)) {
                    list2.add(new Pair(cls3, cls4));
                }
                return false;
            }
            Log.d(TAG, "find alternative for " + str + ": " + str2);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* renamed from: lambda$findAvailableBinaryKey$36$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ TransformFunction m9502xecd8f412(List list, final List list2, final String str, final Map map, final Class cls, final Class cls2) {
        return (TransformFunction) list.stream().map(new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.this.m9501xb8a1f6d4(list2, str, map, (Pair) obj);
            }
        }).filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((BufferExtension.TransformFunction) obj);
            }
        }).findFirst().orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda13
            @Override // java.util.function.Supplier
            public final Object get() {
                return BufferExtension.lambda$findAvailableBinaryKey$35(cls, cls2);
            }
        });
    }

    /* renamed from: lambda$findAvailableBinaryKey$34$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ TransformFunction m9501xb8a1f6d4(List list, final String str, final Map map, final Pair pair) {
        return (TransformFunction) list.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isAssignableFrom;
                isAssignableFrom = ((Class) Pair.this.second).isAssignableFrom((Class) ((Pair) obj).first);
                return isAssignableFrom;
            }
        }).findFirst().map(new Function() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$$ExternalSyntheticLambda36
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BufferExtension.this.m9500x9e867835(pair, str, map, (Pair) obj);
            }
        }).orElse(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$findAvailableBinaryKey$33$com-samsung-android-sume-core-buffer-BufferExtension, reason: not valid java name */
    /* synthetic */ TransformFunction m9500x9e867835(Pair pair, String str, Map map, Pair pair2) {
        String binaryKey = getBinaryKey((Class) pair.first, (Class) pair.second);
        String binaryKey2 = getBinaryKey((Class) pair2.first, (Class) pair2.second);
        Log.d(TAG, "find 2nd order combinations for" + str + ": " + binaryKey + " => " + binaryKey2);
        return new TransformFunction((TransformFunction) map.get(binaryKey), (TransformFunction) map.get(binaryKey2));
    }

    static /* synthetic */ UnsupportedOperationException lambda$findAvailableBinaryKey$35(Class cls, Class cls2) {
        return new UnsupportedOperationException("no extension exist for " + cls + " -> " + cls2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TransformFunction {
        private final List<BiFunction<MediaFormat, ?, ?>> functionList;

        @SafeVarargs
        TransformFunction(BiFunction<MediaFormat, ?, ?>... biFunctionArr) {
            this.functionList = Arrays.asList(biFunctionArr);
        }

        TransformFunction(TransformFunction... transformFunctionArr) {
            this.functionList = new ArrayList();
            Arrays.asList(transformFunctionArr).forEach(new Consumer() { // from class: com.samsung.android.sume.core.buffer.BufferExtension$TransformFunction$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BufferExtension.TransformFunction.this.m9508x3e8b297f((BufferExtension.TransformFunction) obj);
                }
            });
        }

        /* renamed from: lambda$new$0$com-samsung-android-sume-core-buffer-BufferExtension$TransformFunction, reason: not valid java name */
        /* synthetic */ void m9508x3e8b297f(TransformFunction transformFunction) {
            this.functionList.addAll(transformFunction.functionList);
        }

        <U, R> R apply(MediaFormat mediaFormat, U u) {
            Consumer popInternalBufferHandler = BufferExtension.popInternalBufferHandler();
            if (this.functionList.size() == 1) {
                popInternalBufferHandler = null;
            }
            for (BiFunction<MediaFormat, ?, ?> biFunction : this.functionList) {
                if (popInternalBufferHandler != null && BufferExtension.isWrappedTransform(biFunction)) {
                    popInternalBufferHandler.accept(u);
                }
                u = (R) biFunction.apply(mediaFormat, u);
            }
            return (R) u;
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

        public <T> Registry addDescribe(Class<T> cls, Function<T, MutableMediaFormat> function) {
            this.describeMap.put(cls, function);
            return this;
        }

        public <T> Registry addAlloc(Class<T> cls, Function<MediaFormat, ?> function) {
            this.allocMap.put(cls, function);
            return this;
        }

        public <T, R> Registry addWrappedTransform(Class<T> cls, Class<R> cls2, BiFunction<MediaFormat, T, R> biFunction) {
            this.transformMap.put(new Pair<>(cls, cls2), biFunction);
            this.wrappedTransformList.add(Integer.valueOf(biFunction.hashCode()));
            return this;
        }

        public <T> Registry addDealloc(Class<T> cls, Consumer<T> consumer) {
            this.deallocMap.put(cls, consumer);
            return this;
        }

        public <T, R> Registry addTransform(Class<T> cls, Class<R> cls2, BiFunction<MediaFormat, T, R> biFunction) {
            this.transformMap.put(new Pair<>(cls, cls2), biFunction);
            return this;
        }

        public <T> Registry addStringfy(Class<T> cls, Function<T, String> function) {
            this.stringfyMap.put(cls, function);
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
            BufferExtension.getInstance().registerDescribe(this.describeMap).registerAlloc(this.allocMap).registerDealloc(this.deallocMap).registerTransform(this.transformMap).registerWrappedTransform(this.wrappedTransformList).registerStringfy(this.stringfyMap);
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

        public <T> Unregistry removeDescribe(Class<T> cls) {
            this.describeList.add(BufferExtension.getInstance().getUnaryKey(cls));
            return this;
        }

        public <T> Unregistry removeAlloc(Class<T> cls) {
            this.allocList.add(BufferExtension.getInstance().getUnaryKey(cls));
            return this;
        }

        public <T> Unregistry removeDealloc(Class<T> cls) {
            this.deallocList.add(BufferExtension.getInstance().getUnaryKey(cls));
            return this;
        }

        public <T, R> Unregistry removeTransform(Class<T> cls, Class<R> cls2) {
            this.transformList.add(BufferExtension.getInstance().getBinaryKey(cls, cls2));
            return this;
        }

        public <T> Unregistry removeStringfy(Class<T> cls) {
            this.stringfyList.add(BufferExtension.getInstance().getUnaryKey(cls));
            return this;
        }

        public void unregister() {
            BufferExtension.getInstance().unRegisterDescribe(this.describeList).unRegisterAlloc(this.allocList).unRegisterDealloc(this.deallocList).unRegisterTransform(this.transformList).unRegisterStringfy(this.stringfyList);
        }
    }
}
