package com.samsung.android.sesl.transparentvideo.mediaplayer;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class IMediaPlayer$ErrorType {
    public static final /* synthetic */ IMediaPlayer$ErrorType[] $VALUES;
    public static final IMediaPlayer$ErrorType IO;
    public static final IMediaPlayer$ErrorType LOADING_INTERRUPTED;
    public static final IMediaPlayer$ErrorType MALFORMED;
    public static final IMediaPlayer$ErrorType NOT_VALID_FOR_PROGRESSIVE_PLAYBACK;
    public static final IMediaPlayer$ErrorType SERVER_DIED;
    public static final IMediaPlayer$ErrorType TIMED_OUT;
    public static final IMediaPlayer$ErrorType UNKNOWN;
    public static final IMediaPlayer$ErrorType UNSUPPORTED;

    static {
        IMediaPlayer$ErrorType iMediaPlayer$ErrorType = new IMediaPlayer$ErrorType("UNKNOWN", 0);
        UNKNOWN = iMediaPlayer$ErrorType;
        IMediaPlayer$ErrorType iMediaPlayer$ErrorType2 = new IMediaPlayer$ErrorType("SERVER_DIED", 1);
        SERVER_DIED = iMediaPlayer$ErrorType2;
        IMediaPlayer$ErrorType iMediaPlayer$ErrorType3 = new IMediaPlayer$ErrorType("NOT_VALID_FOR_PROGRESSIVE_PLAYBACK", 2);
        NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = iMediaPlayer$ErrorType3;
        IMediaPlayer$ErrorType iMediaPlayer$ErrorType4 = new IMediaPlayer$ErrorType("IO", 3);
        IO = iMediaPlayer$ErrorType4;
        IMediaPlayer$ErrorType iMediaPlayer$ErrorType5 = new IMediaPlayer$ErrorType("MALFORMED", 4);
        MALFORMED = iMediaPlayer$ErrorType5;
        IMediaPlayer$ErrorType iMediaPlayer$ErrorType6 = new IMediaPlayer$ErrorType("UNSUPPORTED", 5);
        UNSUPPORTED = iMediaPlayer$ErrorType6;
        IMediaPlayer$ErrorType iMediaPlayer$ErrorType7 = new IMediaPlayer$ErrorType("TIMED_OUT", 6);
        TIMED_OUT = iMediaPlayer$ErrorType7;
        IMediaPlayer$ErrorType iMediaPlayer$ErrorType8 = new IMediaPlayer$ErrorType("SYSTEM", 7);
        IMediaPlayer$ErrorType iMediaPlayer$ErrorType9 = new IMediaPlayer$ErrorType("LOADING_INTERRUPTED", 8);
        LOADING_INTERRUPTED = iMediaPlayer$ErrorType9;
        IMediaPlayer$ErrorType[] iMediaPlayer$ErrorTypeArr = {iMediaPlayer$ErrorType, iMediaPlayer$ErrorType2, iMediaPlayer$ErrorType3, iMediaPlayer$ErrorType4, iMediaPlayer$ErrorType5, iMediaPlayer$ErrorType6, iMediaPlayer$ErrorType7, iMediaPlayer$ErrorType8, iMediaPlayer$ErrorType9};
        $VALUES = iMediaPlayer$ErrorTypeArr;
        EnumEntriesKt.enumEntries(iMediaPlayer$ErrorTypeArr);
    }

    private IMediaPlayer$ErrorType(String str, int i) {
    }

    public static IMediaPlayer$ErrorType valueOf(String str) {
        return (IMediaPlayer$ErrorType) Enum.valueOf(IMediaPlayer$ErrorType.class, str);
    }

    public static IMediaPlayer$ErrorType[] values() {
        return (IMediaPlayer$ErrorType[]) $VALUES.clone();
    }
}
