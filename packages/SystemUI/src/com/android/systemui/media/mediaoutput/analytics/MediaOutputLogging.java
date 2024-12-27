package com.android.systemui.media.mediaoutput.analytics;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.enums.EnumEntriesKt;

public final class MediaOutputLogging {
    public static final MediaOutputLogging INSTANCE = new MediaOutputLogging();

    public final class CustomKey {
        public static final /* synthetic */ CustomKey[] $VALUES;
        public static final CustomKey ACTION;
        public static final CustomKey APP;
        public static final CustomKey FROM;
        public static final CustomKey LOCK;
        private final String customKey;

        static {
            CustomKey customKey = new CustomKey("FROM", 0, "from");
            FROM = customKey;
            CustomKey customKey2 = new CustomKey("LOCK", 1, "lock");
            LOCK = customKey2;
            CustomKey customKey3 = new CustomKey("APP", 2, "App");
            APP = customKey3;
            CustomKey customKey4 = new CustomKey("NAME", 3, "name");
            CustomKey customKey5 = new CustomKey("ACTION", 4, "Action");
            ACTION = customKey5;
            CustomKey[] customKeyArr = {customKey, customKey2, customKey3, customKey4, customKey5};
            $VALUES = customKeyArr;
            EnumEntriesKt.enumEntries(customKeyArr);
        }

        private CustomKey(String str, int i, String str2) {
            this.customKey = str2;
        }

        public static CustomKey valueOf(String str) {
            return (CustomKey) Enum.valueOf(CustomKey.class, str);
        }

        public static CustomKey[] values() {
            return (CustomKey[]) $VALUES.clone();
        }

        public final String getCustomKey() {
            return this.customKey;
        }
    }

    public final class Details {
        public static final /* synthetic */ Details[] $VALUES;
        public static final Details LOCK;
        public static final Details LOCKSCREEN;
        public static final Details PAUSE;
        public static final Details PLAY;
        public static final Details QUICKPANEL;
        public static final Details UNLOCK;
        private final String detail;

        static {
            Details details = new Details("LOCKSCREEN", 0, "Lockscreen");
            LOCKSCREEN = details;
            Details details2 = new Details("QUICKPANEL", 1, "Quickpanel");
            QUICKPANEL = details2;
            Details details3 = new Details("UNLOCK", 2, "Unlock");
            UNLOCK = details3;
            Details details4 = new Details("LOCK", 3, "Lock");
            LOCK = details4;
            Details details5 = new Details("PLAY", 4, "Play");
            PLAY = details5;
            Details details6 = new Details("PAUSE", 5, "Pause");
            PAUSE = details6;
            Details[] detailsArr = {details, details2, details3, details4, details5, details6};
            $VALUES = detailsArr;
            EnumEntriesKt.enumEntries(detailsArr);
        }

        private Details(String str, int i, String str2) {
            this.detail = str2;
        }

        public static Details valueOf(String str) {
            return (Details) Enum.valueOf(Details.class, str);
        }

        public static Details[] values() {
            return (Details[]) $VALUES.clone();
        }

        public final String getDetail() {
            return this.detail;
        }
    }

    public final class Event {
        public static final /* synthetic */ Event[] $VALUES;
        public static final Event APP_ICON;
        public static final Event LAUNCH_MEDIA_OUTPUT;
        public static final Event MEDIA_NEXT_BUTTON;
        public static final Event MEDIA_PLAY_PAUSE;
        public static final Event MEDIA_PREVIOUS_BUTTON;
        public static final Event SETTINGS;
        private final String id;

        static {
            Event event = new Event("LAUNCH_MEDIA_OUTPUT", 0, "Mo100");
            LAUNCH_MEDIA_OUTPUT = event;
            Event event2 = new Event("APP_ICON", 1, "Mo101");
            APP_ICON = event2;
            Event event3 = new Event("MEDIA_CONTROL_CUSTOM_BUTTON_1", 2, "Mo102");
            Event event4 = new Event("MEDIA_PREVIOUS_BUTTON", 3, "Mo103");
            MEDIA_PREVIOUS_BUTTON = event4;
            Event event5 = new Event("MEDIA_PLAY_PAUSE", 4, "Mo104");
            MEDIA_PLAY_PAUSE = event5;
            Event event6 = new Event("MEDIA_NEXT_BUTTON", 5, "Mo105");
            MEDIA_NEXT_BUTTON = event6;
            Event event7 = new Event("MEDIA_CONTROL_CUSTOM_BUTTON_2", 6, "Mo106");
            Event event8 = new Event("SETTINGS", 7, "Mo107");
            SETTINGS = event8;
            Event[] eventArr = {event, event2, event3, event4, event5, event6, event7, event8, new Event("TOTAL_OUTPUT_DEVICE", 8, "Mo200"), new Event("ACTIVE_OUTPUT_DEVICE", 9, "Mo201"), new Event("PHONE_SPEAKER", 10, "Mo202"), new Event("CONNECTED_BUDS", 11, "Mo203"), new Event("WIFI_SPEAKER", 12, "Mo204"), new Event("GROUP_WIFI_SPEAKER", 13, "Mo205"), new Event("CONNECTED_BT", 14, "Mo206"), new Event("DISCONNECTED_BT", 15, "Mo207"), new Event("SHARED_DEVICES_WITH_MUSIC_SHARE", 16, "Mo301"), new Event("MUSIC_SHARE_ON_MY_DEVICE", 17, "Mo302"), new Event("SEARCH_MUSIC_SHARE", 18, "Mo303"), new Event("MUSIC_SHARE_DEVICES_COUNT", 19, "Mo304"), new Event("PLAY_MUSIC_SHARE", 20, "Mo305"), new Event("MUSIC_SHARE_CONNECTION_FAILURE", 21, "Mo306"), new Event("END_MUSIC_SHARE_CLIENT", 22, "Mo307"), new Event("END_MUSIC_SHARE_HOST", 23, "Mo308"), new Event("WIFI_SPEAKER_CHECKBOX", 24, "Mo401"), new Event("ADD_STREAM", 25, "Mo402"), new Event("REMOVE_STREAM", 26, "Mo403"), new Event("WIFI_SPEAKER_PLAYBACK_PREFS_EVENT", 27, "Mo501"), new Event("SHOW_MUSIC_SHARE", 28, "Mo502"), new Event("MIRRORING", 29, "Mo601"), new Event("CASTING", 30, "Mo602"), new Event("NUMBER_OF_APPS", 31, "Mo603")};
            $VALUES = eventArr;
            EnumEntriesKt.enumEntries(eventArr);
        }

        private Event(String str, int i, String str2) {
            this.id = str2;
        }

        public static Event valueOf(String str) {
            return (Event) Enum.valueOf(Event.class, str);
        }

        public static Event[] values() {
            return (Event[]) $VALUES.clone();
        }

        public final String getId() {
            return this.id;
        }
    }

    public final class ScreenId {
        public static final /* synthetic */ ScreenId[] $VALUES;
        public static final ScreenId MEDIA_OUTPUT;
        private final String id;

        static {
            ScreenId screenId = new ScreenId("MEDIA_OUTPUT", 0, "Mo01");
            MEDIA_OUTPUT = screenId;
            ScreenId[] screenIdArr = {screenId, new ScreenId("MEDIA_OUTPUT_SETTINGS", 1, "Mo02"), new ScreenId("WIFI_SPEAKER_PLAYBACK_PREFS", 2, "Mo03")};
            $VALUES = screenIdArr;
            EnumEntriesKt.enumEntries(screenIdArr);
        }

        private ScreenId(String str, int i, String str2) {
            this.id = str2;
        }

        public static ScreenId valueOf(String str) {
            return (ScreenId) Enum.valueOf(ScreenId.class, str);
        }

        public static ScreenId[] values() {
            return (ScreenId[]) $VALUES.clone();
        }

        public final String getId() {
            return this.id;
        }
    }

    private MediaOutputLogging() {
    }

    public static void sendEventCDLog(ScreenId screenId, Event event, Map map) {
        String id = screenId.getId();
        String id2 = event.getId();
        Set<Map.Entry> entrySet = map.entrySet();
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        for (Map.Entry entry : entrySet) {
            Pair pair = new Pair(((CustomKey) entry.getKey()).getCustomKey(), entry.getValue());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        SystemUIAnalytics.sendEventCDLog(id, id2, linkedHashMap);
        Unit unit = Unit.INSTANCE;
        Log.d("MediaOutputLogging", "sendEventLog: screen = " + screenId + " , event = " + event + ", customDimen = " + map);
    }

    public static void sendEventLog$default(MediaOutputLogging mediaOutputLogging, ScreenId screenId, Event event) {
        mediaOutputLogging.getClass();
        SystemUIAnalytics.sendEventLog(screenId.getId(), event.getId());
        Unit unit = Unit.INSTANCE;
        StringBuilder sb = new StringBuilder("sendEventLog: screen = ");
        sb.append(screenId);
        sb.append(" , event = ");
        sb.append(event);
        sb.append(", detail = ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, null, "MediaOutputLogging");
    }
}
