package com.samsung.android.sume.core.format;

import android.app.PendingIntent$$ExternalSyntheticLambda2;
import android.content.om.OverlayManagerExt$$ExternalSyntheticLambda4;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.types.CodecType;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.ColorSpace;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.FlipType;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.core.types.SplitType;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
class StapleMutableMediaFormat implements MutableMediaFormat {
    private static final String TAG = Def.tagOf((Class<?>) StapleMutableMediaFormat.class);
    protected DataType dataType;
    protected MediaType mediaType;
    protected MutableShape shape;
    private ColorFormat colorFormat = ColorFormat.NONE;
    private ColorSpace colorSpace = ColorSpace.NONE;
    protected Map<String, Object> attributes = new HashMap();

    private void configVideo(Object... objArr) {
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public MutableMediaFormat toMutableFormat() {
        return this;
    }

    public StapleMutableMediaFormat() {
    }

    public StapleMutableMediaFormat(MediaType mediaType, Object... objArr) {
        this.mediaType = mediaType;
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$MediaType[mediaType.rank().ordinal()];
        if (i == 1) {
            configImage(objArr);
            return;
        }
        if (i == 2) {
            configAudio(objArr);
            return;
        }
        if (i == 3) {
            configVideo(objArr);
        } else if (i == 4 || i == 5) {
            config(objArr);
        }
    }

    /* renamed from: com.samsung.android.sume.core.format.StapleMutableMediaFormat$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$MediaType;

        static {
            int[] iArr = new int[MediaType.values().length];
            $SwitchMap$com$samsung$android$sume$core$types$MediaType = iArr;
            try {
                iArr[MediaType.IMAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.AUDIO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.SCALA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.META.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    protected List<Object> config(Object... objArr) {
        Map map = (Map) Arrays.stream(objArr).collect(Collectors.partitioningBy(new Predicate() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StapleMutableMediaFormat.lambda$config$0(obj);
            }
        }));
        if (map.containsKey(true)) {
            for (Object obj : (List) map.get(true)) {
                if (obj instanceof DataType) {
                    this.dataType = (DataType) obj;
                } else if (obj instanceof Shape) {
                    this.shape = ((Shape) obj).toMutableShape();
                } else if (obj instanceof Integer) {
                    int intValue = ((Integer) obj).intValue();
                    if (intValue == 1) {
                        set("exif", true);
                    } else if (intValue == 2) {
                        set("icc", true);
                    } else if (intValue == 3) {
                        set("gain-map", true);
                    } else {
                        Log.w(TAG, "not supported metadata-key " + obj);
                    }
                }
            }
        }
        return (List) Optional.ofNullable((List) map.get(false)).orElseGet(new PendingIntent$$ExternalSyntheticLambda2());
    }

    static /* synthetic */ boolean lambda$config$0(Object obj) {
        return (obj instanceof DataType) || (obj instanceof Shape) || (obj instanceof Integer);
    }

    private void configImage(Object... objArr) {
        for (Object obj : config(objArr)) {
            if (obj instanceof Rect) {
                Rect rect = (Rect) obj;
                if (this.shape == null) {
                    this.shape = new StapleMutableShape(1, -1, -1, -1);
                }
                this.shape.setCols(rect.width());
                this.shape.setRows(rect.height());
            } else if (obj instanceof ColorFormat) {
                this.colorFormat = (ColorFormat) obj;
            } else if (obj instanceof ColorSpace) {
                this.colorSpace = (ColorSpace) obj;
            } else {
                throw new UnsupportedOperationException("not support for " + obj);
            }
        }
        adjustChannels(ColorFormat.class, DataType.class, Shape.class);
    }

    private void configAudio(Object... objArr) {
        for (Object obj : config(objArr)) {
            if (obj instanceof Integer) {
                setDataType(DataType.U8C1);
                setShape(Shape.of(1, ((Integer) obj).intValue(), 1, 1));
            } else {
                throw new UnsupportedOperationException("not supported argument: " + obj);
            }
        }
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public List<? extends MediaFormat> getPlanesFormat() {
        if (!this.mediaType.isImage() || getDataType() == DataType.NONE) {
            return null;
        }
        final ArrayList arrayList = new ArrayList();
        if (getColorFormat().isPlanar()) {
            if (getColorFormat().isYuv()) {
                final DataType depth = getDataType().depth();
                final Shape shape = getShape().toMutableShape().setRows(getRows() >> 1).setCols(getCols() >> 1).setChannels(getColorFormat().numberOfChromaChannels()).toShape();
                arrayList.add(MediaFormat.mutableImageOf(DataType.of(depth, 1), getShape()));
                IntStream.range(1, getColorFormat().numberOfPlanes()).forEach(new IntConsumer() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda13
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        arrayList.add(MediaFormat.mutableImageOf(depth, shape));
                    }
                });
                return arrayList;
            }
            throw new UnsupportedOperationException("not support yet for planar except yuv format");
        }
        arrayList.add(this);
        return arrayList;
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public MediaType getMediaType() {
        return this.mediaType;
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public DataType getDataType() {
        return (DataType) Optional.ofNullable(this.dataType).orElse(DataType.NONE);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public Shape getShape() {
        return (Shape) Optional.ofNullable(this.shape).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MutableShape) obj).toShape();
            }
        }).orElse(null);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getBatch() {
        return ((Integer) Optional.ofNullable(this.shape).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((MutableShape) obj).getBatch());
            }
        }).orElse(-1)).intValue();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getRows() {
        return ((Integer) Optional.ofNullable(this.shape).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((MutableShape) obj).getRows());
            }
        }).orElse(-1)).intValue();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getCols() {
        return ((Integer) Optional.ofNullable(this.shape).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((MutableShape) obj).getCols());
            }
        }).orElse(-1)).intValue();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getChannels() {
        return ((Integer) Optional.ofNullable(this.shape).map(new StapleMutableMediaFormat$$ExternalSyntheticLambda2()).orElse(-1)).intValue();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public int getRotation() {
        return ((Integer) get(GenerateXML.ROTATION, 0)).intValue();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public Rect getCropRect() {
        return (Rect) Optional.ofNullable((Rect) get("crop-rect")).orElse(null);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public SplitType getSplitType() {
        return (SplitType) Optional.ofNullable((SplitType) get("split-type")).orElse(SplitType.NONE);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public FlipType getFlipType() {
        return (FlipType) Optional.ofNullable((FlipType) get("flip-type")).orElse(FlipType.NONE);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public CodecType getCodecType() {
        return (CodecType) Optional.ofNullable((CodecType) get("codec-type")).orElse(CodecType.NONE);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public ColorFormat getColorFormat() {
        return this.colorFormat;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setColorFormat(ColorFormat colorFormat) {
        this.colorFormat = colorFormat;
        adjustChannels(ColorFormat.class);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public ColorSpace getColorSpace() {
        return this.colorSpace;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setColorSpace(ColorSpace colorSpace) {
        this.colorSpace = colorSpace;
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public float bytePerSample() {
        return ((Float) Optional.ofNullable((ColorFormat) get(android.media.MediaFormat.KEY_COLOR_FORMAT)).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Float valueOf;
                valueOf = Float.valueOf(r1.isPlanar() ? ((ColorFormat) obj).bytePerPixel() : 1.0f);
                return valueOf;
            }
        }).orElse(Float.valueOf(1.0f))).floatValue() * ((Float) Optional.ofNullable(getDataType()).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Float valueOf;
                DataType dataType = (DataType) obj;
                valueOf = Float.valueOf(dataType.size() * dataType.channels());
                return valueOf;
            }
        }).orElse(Float.valueOf(0.0f))).floatValue();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public float bytePerPixel() {
        return ((Float) Optional.ofNullable((ColorFormat) get(android.media.MediaFormat.KEY_COLOR_FORMAT)).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Float valueOf;
                valueOf = Float.valueOf(r1.isPlanar() ? ((ColorFormat) obj).bytePerPixel() : 1.0f);
                return valueOf;
            }
        }).orElse(Float.valueOf(1.0f))).floatValue() * ((Float) Optional.ofNullable(getDataType()).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Float valueOf;
                valueOf = Float.valueOf(((DataType) obj).size());
                return valueOf;
            }
        }).orElse(Float.valueOf(0.0f))).floatValue();
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public long size() {
        return (int) (bytePerSample() * ((Integer) Optional.ofNullable(this.shape).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((MutableShape) obj).getDimension());
            }
        }).orElse(0)).intValue());
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public boolean contains(String str) {
        return this.attributes.containsKey(str);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public boolean containsAnyOf(String... strArr) {
        return Arrays.stream(strArr).anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StapleMutableMediaFormat.this.m9568x64af6031((String) obj);
            }
        });
    }

    /* renamed from: lambda$containsAnyOf$6$com-samsung-android-sume-core-format-StapleMutableMediaFormat, reason: not valid java name */
    /* synthetic */ boolean m9568x64af6031(String str) {
        Stream<String> stream = this.attributes.keySet().stream();
        Objects.requireNonNull(str);
        return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda4(str));
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public boolean containsAllOf(String... strArr) {
        return Arrays.stream(strArr).allMatch(new Predicate() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StapleMutableMediaFormat.this.m9567xf68fa2e7((String) obj);
            }
        });
    }

    /* renamed from: lambda$containsAllOf$7$com-samsung-android-sume-core-format-StapleMutableMediaFormat, reason: not valid java name */
    /* synthetic */ boolean m9567xf68fa2e7(String str) {
        Stream<String> stream = this.attributes.keySet().stream();
        Objects.requireNonNull(str);
        return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda4(str));
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public boolean checkTypeOf(String str, Class<?> cls) {
        return this.attributes.containsKey(str) && cls.isAssignableFrom(Objects.requireNonNull(this.attributes.get(str)).getClass());
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public <T> T remove(String str) {
        return (T) this.attributes.remove(str);
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MediaFormat toMediaFormat() {
        return new StapleMediaFormat(this);
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        if (mediaType == MediaType.SCALA) {
            this.shape = Shape.mutableOf(1, 1, 1, 1);
        }
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setDataType(DataType dataType) {
        this.dataType = dataType;
        adjustChannels(ColorFormat.class, DataType.class, Shape.class);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setShape(Shape shape) {
        this.shape = shape.toMutableShape();
        adjustChannels(ColorFormat.class, Shape.class, DataType.class);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setRows(int i) {
        this.shape = ((MutableShape) Optional.ofNullable(this.shape).orElse(Shape.mutableOf())).setRows(i);
        adjustChannels(ColorFormat.class, Shape.class, DataType.class);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setCols(int i) {
        this.shape = ((MutableShape) Optional.ofNullable(this.shape).orElseGet(new StapleMutableMediaFormat$$ExternalSyntheticLambda3())).setCols(i);
        adjustChannels(ColorFormat.class, Shape.class, DataType.class);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setChannels(int i) {
        this.shape = ((MutableShape) Optional.ofNullable(this.shape).orElseGet(new StapleMutableMediaFormat$$ExternalSyntheticLambda3())).setChannels(i);
        adjustChannels(ColorFormat.class, Shape.class, DataType.class);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setRotation(int i) {
        set(GenerateXML.ROTATION, Integer.valueOf(i));
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setCropRect(Rect rect) {
        set("crop-rect", rect);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setSplitType(SplitType splitType) {
        set("split-type", splitType);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setFlipType(FlipType flipType) {
        set("flip-type", flipType);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat setCodecType(CodecType codecType) {
        set("codec-type", codecType);
        return this;
    }

    public void adjustChannels(Class<?>... clsArr) {
        int intValue = ((Integer) Stream.of((Object[]) clsArr).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StapleMutableMediaFormat.this.m9566x676c63c8((Class) obj);
            }
        }).filter(new Predicate() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StapleMutableMediaFormat.lambda$adjustChannels$9((Integer) obj);
            }
        }).findFirst().orElse(-1)).intValue();
        if (intValue != -1) {
            DataType dataType = this.dataType;
            if (dataType != null && intValue != dataType.channels()) {
                this.dataType = DataType.of(this.dataType.depth(), intValue);
            }
            MutableShape mutableShape = this.shape;
            if (mutableShape != null && intValue != mutableShape.getChannels()) {
                this.shape.setChannels(intValue);
            }
        }
        MutableShape mutableShape2 = this.shape;
        if (mutableShape2 == null || mutableShape2.getBatch() != -1) {
            return;
        }
        this.shape.setBatch(1);
    }

    /* renamed from: lambda$adjustChannels$8$com-samsung-android-sume-core-format-StapleMutableMediaFormat, reason: not valid java name */
    /* synthetic */ Integer m9566x676c63c8(Class cls) {
        if (cls == MutableShape.class || cls == Shape.class) {
            return (Integer) Optional.ofNullable(this.shape).map(new StapleMutableMediaFormat$$ExternalSyntheticLambda2()).orElse(-1);
        }
        if (cls == DataType.class) {
            return (Integer) Optional.ofNullable(this.dataType).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((DataType) obj).channels());
                }
            }).orElse(-1);
        }
        if (cls == ColorFormat.class) {
            try {
                return (Integer) Optional.ofNullable(getColorFormat()).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda10
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Integer.valueOf(((ColorFormat) obj).getChannels());
                    }
                }).orElse(-1);
            } catch (UnsupportedOperationException unused) {
                return -1;
            }
        }
        throw new IllegalArgumentException("not support channel supplier " + cls);
    }

    static /* synthetic */ boolean lambda$adjustChannels$9(Integer num) {
        return num.intValue() > 0;
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public <T> T get(String str) {
        str.hashCode();
        if (str.equals(android.media.MediaFormat.KEY_COLOR_FORMAT)) {
            return (T) this.colorFormat;
        }
        return (T) this.attributes.get(str);
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public <T> T get(String str, T t) {
        str.hashCode();
        if (str.equals(android.media.MediaFormat.KEY_COLOR_FORMAT)) {
            return (T) this.colorFormat;
        }
        return (T) this.attributes.getOrDefault(str, t);
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat set(String str, Object obj) {
        str.hashCode();
        if (str.equals(android.media.MediaFormat.KEY_COLOR_FORMAT)) {
            this.colorFormat = (ColorFormat) obj;
            return this;
        }
        this.attributes.put(str, obj);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public String contentToString() {
        return contentToString(this);
    }

    public String toString() {
        return contentToString(this);
    }

    private String getColorString() {
        try {
            return "color=" + getColorFormat();
        } catch (UnsupportedOperationException unused) {
            return "";
        }
    }

    @Override // com.samsung.android.sume.core.format.MediaFormat
    public String contentToString(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(Def.taglnOf(obj));
        sb.append(Def.contentToString("mediaType=" + this.mediaType, "dataType=" + this.dataType, getColorString(), "shape=" + this.shape, "colorspace=" + this.colorSpace));
        sb.append("\nattributes=");
        sb.append(Collections.singletonList(this.attributes));
        return sb.toString();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.format.Copyable
    public MutableMediaFormat copy() {
        try {
            return (MutableMediaFormat) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.format.Copyable
    /* renamed from: deepCopy */
    public MutableMediaFormat deepCopy2() {
        StapleMutableMediaFormat stapleMutableMediaFormat = (StapleMutableMediaFormat) copy();
        MutableShape mutableShape = this.shape;
        if (mutableShape != null) {
            stapleMutableMediaFormat.shape = (MutableShape) mutableShape.deepCopy2();
        }
        stapleMutableMediaFormat.attributes = new HashMap(this.attributes);
        return stapleMutableMediaFormat;
    }

    @Override // com.samsung.android.sume.core.format.MutableMediaFormat
    public MutableMediaFormat set(MediaFilter.Option option) {
        SplitType splitType = (SplitType) option.get(2, SplitType.NONE);
        if (splitType != SplitType.NONE) {
            if (option.contains(8)) {
                this.attributes.put("split-type", splitType);
            } else if (option.contains(9)) {
                this.attributes.put("merge-type", splitType);
            }
        }
        Pair pair = (Pair) option.get(1);
        if (pair != null && option.contains(8)) {
            this.attributes.put("pad-type", pair.first);
            this.attributes.put("pad-size", pair.second);
        }
        return this;
    }
}
