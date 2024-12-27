package android.media.audio.common;

public @interface AudioFormatType {
    public static final byte DEFAULT = 0;
    public static final byte NON_PCM = 0;
    public static final byte PCM = 1;
    public static final byte SYS_RESERVED_INVALID = -1;
}
