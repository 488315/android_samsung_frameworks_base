package com.samsung.android.sume.core.format;

import android.content.p001om.OverlayManagerExt$$ExternalSyntheticLambda1;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.channel.ChannelRouterBase$$ExternalSyntheticLambda6;
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

/* loaded from: classes4.dex */
class StapleMutableMediaFormat implements MutableMediaFormat {
  private static final String TAG = Def.tagOf((Class<?>) StapleMutableMediaFormat.class);
  protected DataType dataType;
  protected MediaType mediaType;
  protected MutableShape shape;
  private ColorFormat colorFormat = ColorFormat.NONE;
  private ColorSpace colorSpace = ColorSpace.NONE;
  protected Map<String, Object> attributes = new HashMap();

  public StapleMutableMediaFormat() {}

  public StapleMutableMediaFormat(MediaType mediaType, Object... args) {
    this.mediaType = mediaType;
    switch (C52821.$SwitchMap$com$samsung$android$sume$core$types$MediaType[
        mediaType.rank().ordinal()]) {
      case 1:
        configImage(args);
        break;
      case 2:
        configAudio(args);
        break;
      case 3:
        configVideo(args);
        break;
      case 4:
      case 5:
        config(args);
        break;
    }
  }

  /* renamed from: com.samsung.android.sume.core.format.StapleMutableMediaFormat$1 */
  static /* synthetic */ class C52821 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$MediaType;

    static {
      int[] iArr = new int[MediaType.values().length];
      $SwitchMap$com$samsung$android$sume$core$types$MediaType = iArr;
      try {
        iArr[MediaType.IMAGE.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.AUDIO.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.VIDEO.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.SCALA.ordinal()] = 4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.META.ordinal()] = 5;
      } catch (NoSuchFieldError e5) {
      }
    }
  }

  protected List<Object> config(Object... args) {
    Map<Boolean, List<Object>> result =
        (Map)
            Arrays.stream(args)
                .collect(
                    Collectors.partitioningBy(
                        new Predicate() { // from class:
                                          // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda1
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            return StapleMutableMediaFormat.lambda$config$0(obj);
                          }
                        }));
    if (result.containsKey(true)) {
      for (Object arg : result.get(true)) {
        if (arg instanceof DataType) {
          this.dataType = (DataType) arg;
        } else if (arg instanceof Shape) {
          this.shape = ((Shape) arg).toMutableShape();
        } else if (arg instanceof Integer) {
          switch (((Integer) arg).intValue()) {
            case 1:
              set("exif", true);
              break;
            case 2:
              set("icc", true);
              break;
            case 3:
              set("gain-map", true);
              break;
            default:
              Log.m102w(TAG, "not supported metadata-key " + arg);
              break;
          }
        }
      }
    }
    return (List)
        Optional.ofNullable(result.get(false))
            .orElseGet(new ChannelRouterBase$$ExternalSyntheticLambda6());
  }

  static /* synthetic */ boolean lambda$config$0(Object it) {
    return (it instanceof DataType) || (it instanceof Shape) || (it instanceof Integer);
  }

  private void configImage(Object... args) {
    for (Object arg : config(args)) {
      if (arg instanceof Rect) {
        Rect rect = (Rect) arg;
        if (this.shape == null) {
          this.shape = new StapleMutableShape(1, -1, -1, -1);
        }
        this.shape.setCols(rect.width());
        this.shape.setRows(rect.height());
      } else if (arg instanceof ColorFormat) {
        this.colorFormat = (ColorFormat) arg;
      } else if (arg instanceof ColorSpace) {
        this.colorSpace = (ColorSpace) arg;
      } else {
        throw new UnsupportedOperationException("not support for " + arg);
      }
    }
    adjustChannels(ColorFormat.class, DataType.class, Shape.class);
  }

  private void configAudio(Object... args) {
    for (Object arg : config(args)) {
      if (arg instanceof Integer) {
        setDataType(DataType.U8C1);
        setShape(Shape.m398of(1, ((Integer) arg).intValue(), 1, 1));
      } else {
        throw new UnsupportedOperationException("not supported argument: " + arg);
      }
    }
  }

  private void configVideo(Object... args) {}

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public List<? extends MediaFormat> getPlanesFormat() {
    if (!this.mediaType.isImage() || getDataType() == DataType.NONE) {
      return null;
    }
    final List<MutableMediaFormat> planes = new ArrayList<>();
    if (getColorFormat().isPlanar()) {
      if (getColorFormat().isYuv()) {
        final DataType depth = getDataType().depth();
        final Shape chromaShape =
            getShape()
                .toMutableShape()
                .setRows(getRows() >> 1)
                .setCols(getCols() >> 1)
                .setChannels(getColorFormat().numberOfChromaChannels())
                .toShape();
        planes.add(MediaFormat.mutableImageOf(DataType.m467of(depth, 1), getShape()));
        IntStream.range(1, getColorFormat().numberOfPlanes())
            .forEach(
                new IntConsumer() { // from class:
                                    // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda4
                  @Override // java.util.function.IntConsumer
                  public final void accept(int i) {
                    planes.add(MediaFormat.mutableImageOf(depth, chromaShape));
                  }
                });
      } else {
        throw new UnsupportedOperationException("not support yet for planar except yuv format");
      }
    } else {
      planes.add(this);
    }
    return planes;
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
    return (Shape)
        Optional.ofNullable(this.shape)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda6
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return ((MutableShape) obj).toShape();
                  }
                })
            .orElse(null);
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public int getBatch() {
    return ((Integer)
            Optional.ofNullable(this.shape)
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda2
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        return Integer.valueOf(((MutableShape) obj).getBatch());
                      }
                    })
                .orElse(-1))
        .intValue();
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public int getRows() {
    return ((Integer)
            Optional.ofNullable(this.shape)
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda3
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        return Integer.valueOf(((MutableShape) obj).getRows());
                      }
                    })
                .orElse(-1))
        .intValue();
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public int getCols() {
    return ((Integer)
            Optional.ofNullable(this.shape)
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda5
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        return Integer.valueOf(((MutableShape) obj).getCols());
                      }
                    })
                .orElse(-1))
        .intValue();
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public int getChannels() {
    return ((Integer)
            Optional.ofNullable(this.shape)
                .map(new StapleMutableMediaFormat$$ExternalSyntheticLambda10())
                .orElse(-1))
        .intValue();
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
    float bppOfColor =
        ((Float)
                Optional.ofNullable((ColorFormat) get(android.media.MediaFormat.KEY_COLOR_FORMAT))
                    .map(
                        new Function() { // from class:
                                         // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda7
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            Float valueOf;
                            valueOf =
                                Float.valueOf(
                                    e.isPlanar() ? ((ColorFormat) obj).bytePerPixel() : 1.0f);
                            return valueOf;
                          }
                        })
                    .orElse(Float.valueOf(1.0f)))
            .floatValue();
    float bppOfDataType =
        ((Float)
                Optional.ofNullable(getDataType())
                    .map(
                        new Function() { // from class:
                                         // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda8
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            Float valueOf;
                            DataType dataType = (DataType) obj;
                            valueOf = Float.valueOf(dataType.size() * dataType.channels());
                            return valueOf;
                          }
                        })
                    .orElse(Float.valueOf(0.0f)))
            .floatValue();
    return bppOfColor * bppOfDataType;
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public float bytePerPixel() {
    float bppOfColor =
        ((Float)
                Optional.ofNullable((ColorFormat) get(android.media.MediaFormat.KEY_COLOR_FORMAT))
                    .map(
                        new Function() { // from class:
                                         // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda11
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            Float valueOf;
                            valueOf =
                                Float.valueOf(
                                    e.isPlanar() ? ((ColorFormat) obj).bytePerPixel() : 1.0f);
                            return valueOf;
                          }
                        })
                    .orElse(Float.valueOf(1.0f)))
            .floatValue();
    float dataTypeSize =
        ((Float)
                Optional.ofNullable(getDataType())
                    .map(
                        new Function() { // from class:
                                         // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda12
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            Float valueOf;
                            valueOf = Float.valueOf(((DataType) obj).size());
                            return valueOf;
                          }
                        })
                    .orElse(Float.valueOf(0.0f)))
            .floatValue();
    return bppOfColor * dataTypeSize;
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public long size() {
    return (int)
        (bytePerSample()
            * ((Integer)
                    Optional.ofNullable(this.shape)
                        .map(
                            new Function() { // from class:
                                             // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda13
                              @Override // java.util.function.Function
                              public final Object apply(Object obj) {
                                return Integer.valueOf(((MutableShape) obj).getDimension());
                              }
                            })
                        .orElse(0))
                .intValue());
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public boolean contains(String key) {
    return this.attributes.containsKey(key);
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public boolean containsAnyOf(String... keys) {
    return Arrays.stream(keys)
        .anyMatch(
            new Predicate() { // from class:
                              // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda18
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                return StapleMutableMediaFormat.this.m401x64af6031((String) obj);
              }
            });
  }

  /* renamed from: lambda$containsAnyOf$6$com-samsung-android-sume-core-format-StapleMutableMediaFormat */
  /* synthetic */ boolean m401x64af6031(String e) {
    Stream<String> stream = this.attributes.keySet().stream();
    Objects.requireNonNull(e);
    return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda1(e));
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public boolean containsAllOf(String... keys) {
    return Arrays.stream(keys)
        .allMatch(
            new Predicate() { // from class:
                              // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda9
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                return StapleMutableMediaFormat.this.m400xf68fa2e7((String) obj);
              }
            });
  }

  /* renamed from: lambda$containsAllOf$7$com-samsung-android-sume-core-format-StapleMutableMediaFormat */
  /* synthetic */ boolean m400xf68fa2e7(String e) {
    Stream<String> stream = this.attributes.keySet().stream();
    Objects.requireNonNull(e);
    return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda1(e));
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public boolean checkTypeOf(String key, Class<?> clazz) {
    return this.attributes.containsKey(key)
        && clazz.isAssignableFrom(Objects.requireNonNull(this.attributes.get(key)).getClass());
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public <T> T remove(String str) {
    return (T) this.attributes.remove(str);
  }

  @Override // com.samsung.android.sume.core.format.MutableMediaFormat
  public MediaFormat toMediaFormat() {
    return new StapleMediaFormat(this);
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public MutableMediaFormat toMutableFormat() {
    return this;
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
  public MutableMediaFormat setRows(int rows) {
    this.shape =
        ((MutableShape) Optional.ofNullable(this.shape).orElse(Shape.mutableOf())).setRows(rows);
    adjustChannels(ColorFormat.class, Shape.class, DataType.class);
    return this;
  }

  @Override // com.samsung.android.sume.core.format.MutableMediaFormat
  public MutableMediaFormat setCols(int cols) {
    this.shape =
        ((MutableShape)
                Optional.ofNullable(this.shape)
                    .orElseGet(new StapleMutableMediaFormat$$ExternalSyntheticLambda0()))
            .setCols(cols);
    adjustChannels(ColorFormat.class, Shape.class, DataType.class);
    return this;
  }

  @Override // com.samsung.android.sume.core.format.MutableMediaFormat
  public MutableMediaFormat setChannels(int channels) {
    this.shape =
        ((MutableShape)
                Optional.ofNullable(this.shape)
                    .orElseGet(new StapleMutableMediaFormat$$ExternalSyntheticLambda0()))
            .setChannels(channels);
    adjustChannels(ColorFormat.class, Shape.class, DataType.class);
    return this;
  }

  @Override // com.samsung.android.sume.core.format.MutableMediaFormat
  public MutableMediaFormat setRotation(int rotation) {
    set(GenerateXML.ROTATION, Integer.valueOf(rotation));
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

  public void adjustChannels(Class<?>... channelSuppliers) {
    int channel =
        ((Integer)
                Stream.of((Object[]) channelSuppliers)
                    .map(
                        new Function() { // from class:
                                         // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda16
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            return StapleMutableMediaFormat.this.m399x676c63c8((Class) obj);
                          }
                        })
                    .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda17
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            return StapleMutableMediaFormat.lambda$adjustChannels$9((Integer) obj);
                          }
                        })
                    .findFirst()
                    .orElse(-1))
            .intValue();
    if (channel != -1) {
      DataType dataType = this.dataType;
      if (dataType != null && channel != dataType.channels()) {
        this.dataType = DataType.m467of(this.dataType.depth(), channel);
      }
      MutableShape mutableShape = this.shape;
      if (mutableShape != null && channel != mutableShape.getChannels()) {
        this.shape.setChannels(channel);
      }
    }
    MutableShape mutableShape2 = this.shape;
    if (mutableShape2 != null && mutableShape2.getBatch() == -1) {
      this.shape.setBatch(1);
    }
  }

  /* renamed from: lambda$adjustChannels$8$com-samsung-android-sume-core-format-StapleMutableMediaFormat */
  /* synthetic */ Integer m399x676c63c8(Class it) {
    if (it == MutableShape.class || it == Shape.class) {
      return (Integer)
          Optional.ofNullable(this.shape)
              .map(new StapleMutableMediaFormat$$ExternalSyntheticLambda10())
              .orElse(-1);
    }
    if (it == DataType.class) {
      return (Integer)
          Optional.ofNullable(this.dataType)
              .map(
                  new Function() { // from class:
                                   // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda14
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Integer.valueOf(((DataType) obj).channels());
                    }
                  })
              .orElse(-1);
    }
    if (it == ColorFormat.class) {
      try {
        return (Integer)
            Optional.ofNullable(getColorFormat())
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.format.StapleMutableMediaFormat$$ExternalSyntheticLambda15
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        return Integer.valueOf(((ColorFormat) obj).getChannels());
                      }
                    })
                .orElse(-1);
      } catch (UnsupportedOperationException e) {
        return -1;
      }
    }
    throw new IllegalArgumentException("not support channel supplier " + it);
  }

  static /* synthetic */ boolean lambda$adjustChannels$9(Integer it) {
    return it.intValue() > 0;
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public <T> T get(String str) {
    char c;
    switch (str.hashCode()) {
      case 614641057:
        if (str.equals(android.media.MediaFormat.KEY_COLOR_FORMAT)) {
          c = 0;
          break;
        }
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
        return (T) this.colorFormat;
      default:
        return (T) this.attributes.get(str);
    }
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public <T> T get(String str, T t) {
    char c;
    switch (str.hashCode()) {
      case 614641057:
        if (str.equals(android.media.MediaFormat.KEY_COLOR_FORMAT)) {
          c = 0;
          break;
        }
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
        return (T) this.colorFormat;
      default:
        return (T) this.attributes.getOrDefault(str, t);
    }
  }

  @Override // com.samsung.android.sume.core.format.MutableMediaFormat
  public MutableMediaFormat set(String key, Object obj) {
    char c;
    switch (key.hashCode()) {
      case 614641057:
        if (key.equals(android.media.MediaFormat.KEY_COLOR_FORMAT)) {
          c = 0;
          break;
        }
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
        this.colorFormat = (ColorFormat) obj;
        return this;
      default:
        this.attributes.put(key, obj);
        return this;
    }
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
    } catch (UnsupportedOperationException e) {
      return "";
    }
  }

  @Override // com.samsung.android.sume.core.format.MediaFormat
  public String contentToString(Object obj) {
    return Def.taglnOf(obj)
        + Def.contentToString(
            "mediaType=" + this.mediaType,
            "dataType=" + this.dataType,
            getColorString(),
            "shape=" + this.shape,
            "colorspace=" + this.colorSpace)
        + "\nattributes="
        + Collections.singletonList(this.attributes);
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
    StapleMutableMediaFormat copied = (StapleMutableMediaFormat) copy();
    MutableShape mutableShape = this.shape;
    if (mutableShape != null) {
      copied.shape = (MutableShape) mutableShape.deepCopy2();
    }
    copied.attributes = new HashMap(this.attributes);
    return copied;
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
    Pair<Integer, Integer> pad = (Pair) option.get(1);
    if (pad != null && option.contains(8)) {
      this.attributes.put("pad-type", pad.first);
      this.attributes.put("pad-size", pad.second);
    }
    return this;
  }
}
