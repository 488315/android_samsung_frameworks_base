package com.samsung.android.sume.core.types;

import android.security.keystore.KeyProperties;
import com.samsung.android.sume.core.Def;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'RAW_IMAGE' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes4.dex */
public final class MediaType implements NumericEnum {
  private static final /* synthetic */ MediaType[] $VALUES;
  public static final MediaType AUDIO;
  public static final MediaType COMPRESSED_AUDIO;
  public static final MediaType COMPRESSED_IMAGE;
  public static final MediaType COMPRESSED_VIDEO;
  public static final int FLAG_COMPRESSED = 1;
  public static final MediaType IMAGE;
  public static final MediaType META;
  private static final int MT_FLAG_SHIFT = 4;
  private static final int MT_RANK_MASK = 15;
  private static final int MT_RANK_MAX = 16;
  public static final MediaType NONE = new MediaType(KeyProperties.DIGEST_NONE, 0, 0);
  public static final MediaType RAW_AUDIO;
  public static final MediaType RAW_IMAGE;
  public static final MediaType RAW_VIDEO;
  public static final MediaType SCALA;
  public static final MediaType VIDEO;
  public static final MediaType _MAX_RANK_;
  private final int value;

  @Retention(RetentionPolicy.SOURCE)
  public @interface Flag {}

  private static /* synthetic */ MediaType[] $values() {
    return new MediaType[] {
      NONE,
      IMAGE,
      AUDIO,
      VIDEO,
      META,
      SCALA,
      _MAX_RANK_,
      RAW_IMAGE,
      RAW_AUDIO,
      RAW_VIDEO,
      COMPRESSED_IMAGE,
      COMPRESSED_AUDIO,
      COMPRESSED_VIDEO
    };
  }

  public static MediaType valueOf(String name) {
    return (MediaType) Enum.valueOf(MediaType.class, name);
  }

  public static MediaType[] values() {
    return (MediaType[]) $VALUES.clone();
  }

  static {
    MediaType mediaType = new MediaType("IMAGE", 1, 1);
    IMAGE = mediaType;
    MediaType mediaType2 = new MediaType("AUDIO", 2, 2);
    AUDIO = mediaType2;
    MediaType mediaType3 = new MediaType("VIDEO", 3, 3);
    VIDEO = mediaType3;
    META = new MediaType("META", 4, 4);
    SCALA = new MediaType("SCALA", 5, 5);
    _MAX_RANK_ = new MediaType("_MAX_RANK_", 6, 16);
    RAW_IMAGE = new MediaType("RAW_IMAGE", 7, typeValueOf(mediaType, 0));
    RAW_AUDIO = new MediaType("RAW_AUDIO", 8, typeValueOf(mediaType2, 0));
    RAW_VIDEO = new MediaType("RAW_VIDEO", 9, typeValueOf(mediaType3, 0));
    COMPRESSED_IMAGE = new MediaType("COMPRESSED_IMAGE", 10, typeValueOf(mediaType, 1));
    COMPRESSED_AUDIO = new MediaType("COMPRESSED_AUDIO", 11, typeValueOf(mediaType2, 1));
    COMPRESSED_VIDEO = new MediaType("COMPRESSED_VIDEO", 12, typeValueOf(mediaType3, 1));
    $VALUES = $values();
  }

  @Override // com.samsung.android.sume.core.types.NumericEnum
  public int getValue() {
    return this.value;
  }

  @Override // com.samsung.android.sume.core.types.NumericEnum
  public String stringfy() {
    return name() + ":" + this.value;
  }

  private MediaType(String str, int i, int value) {
    this.value = value;
  }

  public boolean isAudio() {
    return rank() == AUDIO;
  }

  public boolean isVideo() {
    return rank() == VIDEO;
  }

  public boolean isImage() {
    return rank() == IMAGE;
  }

  public boolean isMetaData() {
    return rank() == META;
  }

  public boolean isScala() {
    return rank() == SCALA;
  }

  public boolean isCompressed() {
    return (flag() & 1) != 0;
  }

  public boolean isRaw() {
    return (flag() & 1) == 0;
  }

  public int flag() {
    return this.value >> 4;
  }

  public MediaType rank() {
    return m468of(this.value & 15);
  }

  private static int typeValueOf(MediaType rank, int flag) {
    Def.require(
        rank.getValue() <= _MAX_RANK_.getValue(),
        "1st argument is not depth: " + rank.getValue(),
        new Object[0]);
    return (rank.getValue() & 15) + (flag << 4);
  }

  /* renamed from: of */
  private static MediaType m468of(final int value) {
    return (MediaType)
        Arrays.stream(values())
            .filter(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.types.MediaType$$ExternalSyntheticLambda0
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return MediaType.lambda$of$0(value, (MediaType) obj);
                  }
                })
            .findFirst()
            .orElseThrow(
                new Supplier() { // from class:
                                 // com.samsung.android.sume.core.types.MediaType$$ExternalSyntheticLambda1
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    return MediaType.lambda$of$1(value);
                  }
                });
  }

  static /* synthetic */ boolean lambda$of$0(int value, MediaType e) {
    return e.getValue() == value;
  }

  static /* synthetic */ InvalidParameterException lambda$of$1(int value) {
    return new InvalidParameterException("invalid MediaType value: " + value);
  }

  /* renamed from: of */
  public static MediaType m469of(MediaType rank, int flag) {
    return m468of(typeValueOf(rank, flag));
  }
}
