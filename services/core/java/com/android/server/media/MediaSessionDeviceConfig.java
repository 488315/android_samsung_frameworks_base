package com.android.server.media;

public abstract class MediaSessionDeviceConfig {
    public static volatile long sMediaButtonReceiverFgsAllowlistDurationMs = 10000;
    public static volatile long sMediaSessionCallbackFgsAllowlistDurationMs = 10000;
    public static volatile long sMediaSessionCallbackFgsWhileInUseTempAllowDurationMs = 10000;
}
