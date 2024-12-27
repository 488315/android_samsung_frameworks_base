package com.samsung.android.sume.core.types;

import com.samsung.android.sume.core.Def;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public enum MediaType implements NumericEnum {
    NONE(0),
    IMAGE(1),
    AUDIO(2),
    VIDEO(3),
    META(4),
    SCALA(5),
    _MAX_RANK_(16),
    RAW_IMAGE(typeValueOf(IMAGE, 0)),
    RAW_AUDIO(typeValueOf(AUDIO, 0)),
    RAW_VIDEO(typeValueOf(VIDEO, 0)),
    COMPRESSED_IMAGE(typeValueOf(IMAGE, 1)),
    COMPRESSED_AUDIO(typeValueOf(AUDIO, 1)),
    COMPRESSED_VIDEO(typeValueOf(VIDEO, 1));

    public static final int FLAG_COMPRESSED = 1;
    private static final int MT_FLAG_SHIFT = 4;
    private static final int MT_RANK_MASK = 15;
    private static final int MT_RANK_MAX = 16;
    private final int value;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flag {
    }

    @Override // com.samsung.android.sume.core.types.NumericEnum
    public int getValue() {
        return this.value;
    }

    @Override // com.samsung.android.sume.core.types.NumericEnum
    public String stringfy() {
        return name() + ":" + this.value;
    }

    MediaType(int value) {
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
        return of(this.value & 15);
    }

    private static int typeValueOf(MediaType rank, int flag) {
        Def.require(rank.getValue() <= _MAX_RANK_.getValue(), "1st argument is not depth: " + rank.getValue(), new Object[0]);
        return (rank.getValue() & 15) + (flag << 4);
    }

    private static MediaType of(final int value) {
        return (MediaType) Arrays.stream(values()).filter(new Predicate() { // from class: com.samsung.android.sume.core.types.MediaType$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaType.lambda$of$0(value, (MediaType) obj);
            }
        }).findFirst().orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.types.MediaType$$ExternalSyntheticLambda1
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

    public static MediaType of(MediaType rank, int flag) {
        return of(typeValueOf(rank, flag));
    }
}
