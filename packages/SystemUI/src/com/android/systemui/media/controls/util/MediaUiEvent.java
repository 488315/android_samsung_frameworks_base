package com.android.systemui.media.controls.util;

import com.android.internal.logging.UiEventLogger;
import com.samsung.android.knox.EnterpriseContainerCallback;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class MediaUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ MediaUiEvent[] $VALUES;
    public static final MediaUiEvent ACTION_SEEK;
    public static final MediaUiEvent ACTIVE_TO_RESUME;
    public static final MediaUiEvent CAROUSEL_PAGE;
    public static final MediaUiEvent CAST_MEDIA_ADDED;
    public static final MediaUiEvent DISMISS_LONG_PRESS;
    public static final MediaUiEvent DISMISS_SWIPE;
    public static final MediaUiEvent LOCAL_MEDIA_ADDED;
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_COMMUNAL;
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_DREAM;
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_LOCKSCREEN;
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_QQS;
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_QS;
    public static final MediaUiEvent MEDIA_CAROUSEL_MULTIPLE_PLAYERS;
    public static final MediaUiEvent MEDIA_CAROUSEL_SINGLE_PLAYER;
    public static final MediaUiEvent MEDIA_OPEN_BROADCAST_DIALOG;
    public static final MediaUiEvent MEDIA_RECOMMENDATION_ACTIVATED;
    public static final MediaUiEvent MEDIA_RECOMMENDATION_ADDED;
    public static final MediaUiEvent MEDIA_RECOMMENDATION_CARD_TAP;
    public static final MediaUiEvent MEDIA_RECOMMENDATION_ITEM_TAP;
    public static final MediaUiEvent MEDIA_RECOMMENDATION_REMOVED;
    public static final MediaUiEvent MEDIA_REMOVED;
    public static final MediaUiEvent MEDIA_TAP_CONTENT_VIEW;
    public static final MediaUiEvent MEDIA_TIMEOUT;
    public static final MediaUiEvent OPEN_LONG_PRESS;
    public static final MediaUiEvent OPEN_OUTPUT_SWITCHER;
    public static final MediaUiEvent OPEN_SETTINGS_CAROUSEL;
    public static final MediaUiEvent OPEN_SETTINGS_LONG_PRESS;
    public static final MediaUiEvent REMOTE_MEDIA_ADDED;
    public static final MediaUiEvent RESUME_MEDIA_ADDED;
    public static final MediaUiEvent TAP_ACTION_NEXT;
    public static final MediaUiEvent TAP_ACTION_OTHER;
    public static final MediaUiEvent TAP_ACTION_PLAY_PAUSE;
    public static final MediaUiEvent TAP_ACTION_PREV;
    public static final MediaUiEvent TRANSFER_TO_CAST;
    public static final MediaUiEvent TRANSFER_TO_LOCAL;
    public static final MediaUiEvent TRANSFER_TO_REMOTE;
    private final int metricId;

    static {
        MediaUiEvent mediaUiEvent = new MediaUiEvent("LOCAL_MEDIA_ADDED", 0, 1029);
        LOCAL_MEDIA_ADDED = mediaUiEvent;
        MediaUiEvent mediaUiEvent2 = new MediaUiEvent("CAST_MEDIA_ADDED", 1, 1030);
        CAST_MEDIA_ADDED = mediaUiEvent2;
        MediaUiEvent mediaUiEvent3 = new MediaUiEvent("REMOTE_MEDIA_ADDED", 2, 1031);
        REMOTE_MEDIA_ADDED = mediaUiEvent3;
        MediaUiEvent mediaUiEvent4 = new MediaUiEvent("TRANSFER_TO_LOCAL", 3, 1032);
        TRANSFER_TO_LOCAL = mediaUiEvent4;
        MediaUiEvent mediaUiEvent5 = new MediaUiEvent("TRANSFER_TO_CAST", 4, 1033);
        TRANSFER_TO_CAST = mediaUiEvent5;
        MediaUiEvent mediaUiEvent6 = new MediaUiEvent("TRANSFER_TO_REMOTE", 5, 1034);
        TRANSFER_TO_REMOTE = mediaUiEvent6;
        MediaUiEvent mediaUiEvent7 = new MediaUiEvent("RESUME_MEDIA_ADDED", 6, EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_FAILED);
        RESUME_MEDIA_ADDED = mediaUiEvent7;
        MediaUiEvent mediaUiEvent8 = new MediaUiEvent("ACTIVE_TO_RESUME", 7, EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL);
        ACTIVE_TO_RESUME = mediaUiEvent8;
        MediaUiEvent mediaUiEvent9 = new MediaUiEvent("MEDIA_TIMEOUT", 8, EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED);
        MEDIA_TIMEOUT = mediaUiEvent9;
        MediaUiEvent mediaUiEvent10 = new MediaUiEvent("MEDIA_REMOVED", 9, EnterpriseContainerCallback.CONTAINER_CANCELLED);
        MEDIA_REMOVED = mediaUiEvent10;
        MediaUiEvent mediaUiEvent11 = new MediaUiEvent("CAROUSEL_PAGE", 10, 1017);
        CAROUSEL_PAGE = mediaUiEvent11;
        MediaUiEvent mediaUiEvent12 = new MediaUiEvent("DISMISS_SWIPE", 11, 1018);
        DISMISS_SWIPE = mediaUiEvent12;
        MediaUiEvent mediaUiEvent13 = new MediaUiEvent("OPEN_LONG_PRESS", 12, 1019);
        OPEN_LONG_PRESS = mediaUiEvent13;
        MediaUiEvent mediaUiEvent14 = new MediaUiEvent("DISMISS_LONG_PRESS", 13, 1020);
        DISMISS_LONG_PRESS = mediaUiEvent14;
        MediaUiEvent mediaUiEvent15 = new MediaUiEvent("OPEN_SETTINGS_LONG_PRESS", 14, 1021);
        OPEN_SETTINGS_LONG_PRESS = mediaUiEvent15;
        MediaUiEvent mediaUiEvent16 = new MediaUiEvent("OPEN_SETTINGS_CAROUSEL", 15, 1022);
        OPEN_SETTINGS_CAROUSEL = mediaUiEvent16;
        MediaUiEvent mediaUiEvent17 = new MediaUiEvent("TAP_ACTION_PLAY_PAUSE", 16, 1023);
        TAP_ACTION_PLAY_PAUSE = mediaUiEvent17;
        MediaUiEvent mediaUiEvent18 = new MediaUiEvent("TAP_ACTION_PREV", 17, 1024);
        TAP_ACTION_PREV = mediaUiEvent18;
        MediaUiEvent mediaUiEvent19 = new MediaUiEvent("TAP_ACTION_NEXT", 18, 1025);
        TAP_ACTION_NEXT = mediaUiEvent19;
        MediaUiEvent mediaUiEvent20 = new MediaUiEvent("TAP_ACTION_OTHER", 19, 1026);
        TAP_ACTION_OTHER = mediaUiEvent20;
        MediaUiEvent mediaUiEvent21 = new MediaUiEvent("ACTION_SEEK", 20, 1027);
        ACTION_SEEK = mediaUiEvent21;
        MediaUiEvent mediaUiEvent22 = new MediaUiEvent("OPEN_OUTPUT_SWITCHER", 21, 1028);
        OPEN_OUTPUT_SWITCHER = mediaUiEvent22;
        MediaUiEvent mediaUiEvent23 = new MediaUiEvent("MEDIA_TAP_CONTENT_VIEW", 22, 1036);
        MEDIA_TAP_CONTENT_VIEW = mediaUiEvent23;
        MediaUiEvent mediaUiEvent24 = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_QQS", 23, 1037);
        MEDIA_CAROUSEL_LOCATION_QQS = mediaUiEvent24;
        MediaUiEvent mediaUiEvent25 = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_QS", 24, 1038);
        MEDIA_CAROUSEL_LOCATION_QS = mediaUiEvent25;
        MediaUiEvent mediaUiEvent26 = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_LOCKSCREEN", 25, 1039);
        MEDIA_CAROUSEL_LOCATION_LOCKSCREEN = mediaUiEvent26;
        MediaUiEvent mediaUiEvent27 = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_DREAM", 26, 1040);
        MEDIA_CAROUSEL_LOCATION_DREAM = mediaUiEvent27;
        MediaUiEvent mediaUiEvent28 = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_COMMUNAL", 27, 1520);
        MEDIA_CAROUSEL_LOCATION_COMMUNAL = mediaUiEvent28;
        MediaUiEvent mediaUiEvent29 = new MediaUiEvent("MEDIA_RECOMMENDATION_ADDED", 28, 1041);
        MEDIA_RECOMMENDATION_ADDED = mediaUiEvent29;
        MediaUiEvent mediaUiEvent30 = new MediaUiEvent("MEDIA_RECOMMENDATION_REMOVED", 29, 1042);
        MEDIA_RECOMMENDATION_REMOVED = mediaUiEvent30;
        MediaUiEvent mediaUiEvent31 = new MediaUiEvent("MEDIA_RECOMMENDATION_ACTIVATED", 30, 1043);
        MEDIA_RECOMMENDATION_ACTIVATED = mediaUiEvent31;
        MediaUiEvent mediaUiEvent32 = new MediaUiEvent("MEDIA_RECOMMENDATION_ITEM_TAP", 31, 1044);
        MEDIA_RECOMMENDATION_ITEM_TAP = mediaUiEvent32;
        MediaUiEvent mediaUiEvent33 = new MediaUiEvent("MEDIA_RECOMMENDATION_CARD_TAP", 32, 1045);
        MEDIA_RECOMMENDATION_CARD_TAP = mediaUiEvent33;
        MediaUiEvent mediaUiEvent34 = new MediaUiEvent("MEDIA_OPEN_BROADCAST_DIALOG", 33, 1079);
        MEDIA_OPEN_BROADCAST_DIALOG = mediaUiEvent34;
        MediaUiEvent mediaUiEvent35 = new MediaUiEvent("MEDIA_CAROUSEL_SINGLE_PLAYER", 34, 1244);
        MEDIA_CAROUSEL_SINGLE_PLAYER = mediaUiEvent35;
        MediaUiEvent mediaUiEvent36 = new MediaUiEvent("MEDIA_CAROUSEL_MULTIPLE_PLAYERS", 35, 1245);
        MEDIA_CAROUSEL_MULTIPLE_PLAYERS = mediaUiEvent36;
        MediaUiEvent[] mediaUiEventArr = {mediaUiEvent, mediaUiEvent2, mediaUiEvent3, mediaUiEvent4, mediaUiEvent5, mediaUiEvent6, mediaUiEvent7, mediaUiEvent8, mediaUiEvent9, mediaUiEvent10, mediaUiEvent11, mediaUiEvent12, mediaUiEvent13, mediaUiEvent14, mediaUiEvent15, mediaUiEvent16, mediaUiEvent17, mediaUiEvent18, mediaUiEvent19, mediaUiEvent20, mediaUiEvent21, mediaUiEvent22, mediaUiEvent23, mediaUiEvent24, mediaUiEvent25, mediaUiEvent26, mediaUiEvent27, mediaUiEvent28, mediaUiEvent29, mediaUiEvent30, mediaUiEvent31, mediaUiEvent32, mediaUiEvent33, mediaUiEvent34, mediaUiEvent35, mediaUiEvent36};
        $VALUES = mediaUiEventArr;
        EnumEntriesKt.enumEntries(mediaUiEventArr);
    }

    private MediaUiEvent(String str, int i, int i2) {
        this.metricId = i2;
    }

    public static MediaUiEvent valueOf(String str) {
        return (MediaUiEvent) Enum.valueOf(MediaUiEvent.class, str);
    }

    public static MediaUiEvent[] values() {
        return (MediaUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.metricId;
    }
}
