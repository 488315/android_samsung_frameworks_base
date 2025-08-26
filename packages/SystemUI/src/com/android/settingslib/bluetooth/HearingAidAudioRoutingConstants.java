package com.android.settingslib.bluetooth;

import android.media.AudioDeviceAttributes;

/* loaded from: classes.dex */
public final class HearingAidAudioRoutingConstants {
    public static final int[] CALL_ROUTING_ATTRIBUTES = {2};
    public static final int[] MEDIA_ROUTING_ATTRIBUTES = {1, 11, 3};
    public static final int[] RINGTONE_ROUTING_ATTRIBUTES = {6};
    public static final int[] NOTIFICATION_ROUTING_ATTRIBUTES = {5};
    public static final AudioDeviceAttributes BUILTIN_SPEAKER = new AudioDeviceAttributes(2, 2, "");
    public static final AudioDeviceAttributes BUILTIN_MIC = new AudioDeviceAttributes(1, 15, "");
}
