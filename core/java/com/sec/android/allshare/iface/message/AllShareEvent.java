package com.sec.android.allshare.iface.message;

/* loaded from: classes6.dex */
public class AllShareEvent {
    public static final String EVENT_AIRPLANE_MODE_OFF =
            "com.sec.android.allshare.event.EVENT_AIRPLANE_MODE_OFF";
    public static final String EVENT_AIRPLANE_MODE_ON =
            "com.sec.android.allshare.event.EVENT_AIRPLANE_MODE_ON";
    public static final String EVENT_AP_CHANGED = "com.sec.android.allshare.event.EVENT_AP_CHANGED";
    public static final String EVENT_AV_PLAYER_DISCOVERY =
            "com.sec.android.allshare.event.EVENT_AV_PLAYER_DISCOVERY";
    public static final String EVENT_BATTERY_CHANGED =
            "com.sec.android.allshare.event.EVENT_BATTERY_CHANGED";
    public static final String EVENT_BATTERY_LOW =
            "com.sec.android.allshare.event.EVENT_BATTERY_LOW";
    public static final String EVENT_BATTERY_OKAY =
            "com.sec.android.allshare.event.EVENT_BATTERY_OKAY";
    public static final String EVENT_CALL_IDLE = "com.sec.android.allshare.event.EVENT_CALL_IDLE";
    public static final String EVENT_CALL_OFFHOOK =
            "com.sec.android.allshare.event.EVENT_CALL_OFFHOOK";
    public static final String EVENT_CALL_RINGING =
            "com.sec.android.allshare.event.EVENT_CALL_RINGING";
    public static final String EVENT_CATEGORY_DEVICE = "com.sec.android.allshare.event.DEVICE";
    public static final String EVENT_CATEGORY_GLOBAL = "com.sec.android.allshare.event.GLOBAL";
    public static final String EVENT_DEVICE_SUBSCRIBE =
            "com.sec.android.allshare.event.EVENT_DEVICE_SUBSCRIBE";
    public static final String EVENT_DEVICE_UNSUBSCRIBE =
            "com.sec.android.allshare.event.EVENT_DEVICE_UNSUBSCRIBE";
    public static final String EVENT_DMR_DISCOVERY =
            "com.sec.android.allshare.event.EVENT_DMR_DISCOVERY";
    public static final String EVENT_ETHERNET_CONNECTED =
            "com.sec.android.allshare.event.EVENT_ETHERNET_CONNECTED";
    public static final String EVENT_ETHERNET_DISCONNECTED =
            "com.sec.android.allshare.event.EVENT_ETHERNET_DISCONNECTED";
    public static final String EVENT_FILERECEIVER_DISCOVERY =
            "com.sec.android.allshare.event.EVENT_FILERECEIVER_DISCOVERY";
    public static final String EVENT_IMAGE_VIEWER_DISCOVERY =
            "com.sec.android.allshare.event.EVENT_IMAGE_VIEWER_DISCOVERY";
    public static final String EVENT_KIES_SYNC_SERVER_DISCOVERY =
            "com.sec.android.allshare.event.EVENT_KIES_SYNC_SERVER_DISCOVERY";
    public static final String EVENT_MAIN_TV_AGENT =
            "com.sec.android.allshare.event.EVENT_MAIN_TV_AGENT";
    public static final String EVENT_PARAMETER_KEY1 =
            "com.sec.android.allshare.event.EVENT_PARAMETER_KEY1";
    private static final String EVENT_PREFIX = "com.sec.android.allshare.event.";
    public static final String EVENT_PROVIDER_CONTENTS_UPDATED =
            "com.sec.android.allshare.event.EVENT_PROVIDER_CONTENTS_UPDATED";
    public static final String EVENT_PROVIDER_DISCOVERY =
            "com.sec.android.allshare.event.EVENT_PROVIDER_DISCOVERY";
    public static final String EVENT_RECEIVER_COMPLETED_BY_ITEM =
            "com.sec.android.allshare.event.EVENT_RECEIVER_COMPLETED_BY_ITEM";
    public static final String EVENT_RECEIVER_PROGRESS_UPDATE_BY_ITEM =
            "com.sec.android.allshare.event.EVENT_RECEIVER_PROGRESS_UPDATE_BY_ITEM";
    public static final String EVENT_RELAY_ERROR =
            "com.sec.android.allshare.event.EVENT_RELAY_ERROR";
    public static final String EVENT_RENDERER_360_VIEW =
            "com.sec.android.allshare.event.EVENT_RENDERER_360_VIEW";
    public static final String EVENT_RENDERER_ASPECT_RATIO =
            "com.sec.android.allshare.event.EVENT_RENDERER_ASPECT_RATIO";
    public static final String EVENT_RENDERER_CAPTIONS =
            "com.sec.android.allshare.event.EVENT_RENDERER_CAPTIONS";
    public static final String EVENT_RENDERER_ENABLED_CAPTIONS =
            "com.sec.android.allshare.event.EVENT_RENDERER_ENABLED_CAPTIONS";
    public static final String EVENT_RENDERER_STATE_BUFFERING =
            "com.sec.android.allshare.event.EVENT_RENDERER_STATE_BUFFERING";
    public static final String EVENT_RENDERER_STATE_CONTENT_CHANGED =
            "com.sec.android.allshare.event.EVENT_RENDERER_STATE_CONTENT_CHANGED";
    public static final String EVENT_RENDERER_STATE_FINISHED =
            "com.sec.android.allshare.event.EVENT_RENDERER_STATE_FINISHED";
    public static final String EVENT_RENDERER_STATE_NOMEDIA =
            "com.sec.android.allshare.event.EVENT_RENDERER_STATE_NOMEDIA";
    public static final String EVENT_RENDERER_STATE_PAUSED =
            "com.sec.android.allshare.event.EVENT_RENDERER_STATE_PAUSED";
    public static final String EVENT_RENDERER_STATE_PLAYING =
            "com.sec.android.allshare.event.EVENT_RENDERER_STATE_PLAYING";
    public static final String EVENT_RENDERER_STATE_STOPPED =
            "com.sec.android.allshare.event.EVENT_RENDERER_STATE_STOPPED";
    public static final String EVENT_SCREENSHARING_DISCOVERY =
            "com.sec.android.allshare.event.EVENT_SCREENSHARING_DISCOVERY";
    public static final String EVENT_SCREEN_OFF = "com.sec.android.allshare.event.EVENT_SCREEN_OFF";
    public static final String EVENT_SCREEN_ON = "com.sec.android.allshare.event.EVENT_SCREEN_ON";
    public static final String EVENT_SMARTCONTROL_DISCOVERY =
            "com.sec.android.allshare.event.EVENT_SMARTCONTROL_DISCOVERY";
    public static final String EVENT_TYPE_TV = "com.sec.android.allshare.event.EVENT_TYPE_TV";
    public static final String EVENT_UPNP_DISCOVERY =
            "com.sec.android.allshare.event.EVENT_UPNP_DISCOVERY";
    public static final String EVENT_USER_PRESENT =
            "com.sec.android.allshare.event.EVENT_USER_PRESENT";
    public static final String EVENT_WIFI_AP_DISABLED =
            "com.sec.android.allshare.event.EVENT_WIFI_AP_DISABLED";
    public static final String EVENT_WIFI_AP_ENABLED =
            "com.sec.android.allshare.event.EVENT_WIFI_AP_ENABLED";
    public static final String EVENT_WIFI_DISABLED =
            "com.sec.android.allshare.event.EVENT_WIFI_DISABLED";
    public static final String EVENT_WIFI_ENABLED =
            "com.sec.android.allshare.event.EVENT_WIFI_ENABLED";
    public static final String EVENT_WIFI_P2P_CONNECTED =
            "com.sec.android.allshare.event.EVENT_WIFI_P2P_ENABLED";
    public static final String EVENT_WIFI_P2P_DISCONNECTED =
            "com.sec.android.allshare.event.EVENT_WIFI_P2P_DISABLED";
    public static final String EVENT_WIFI_UNKNOWN =
            "com.sec.android.allshare.event.EVENT_WIFI_UNKNOWN";

    public static class FileReceiverEvent {
        public static final String EVENT_FILE_RECEIVER_COMPLETED =
                "com.sec.android.allshare.event.EVENT_FILE_RECEIVER_COMPLECTED";
        public static final String EVENT_FILE_RECEIVER_FAILED =
                "com.sec.android.allshare.event.EVENT_FILE_RECEIVER_FAILED";
        public static final String EVENT_FILE_RECEIVER_PROGRESS =
                "com.sec.android.allshare.event.EVENT_FILE_RECEIVER_PROGRESS";

        private FileReceiverEvent() {}
    }
}
