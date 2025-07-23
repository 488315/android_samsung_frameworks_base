package com.android.systemui.audio.soundcraft.utils;

import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SoundCraftSALogging {
    public static final SoundCraftSALogging INSTANCE = new SoundCraftSALogging();
    public static final String ON = "On";
    public static final String OFF = "Off";

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Event {
        public static final /* synthetic */ Event[] $VALUES;
        public static final Event ACTIVE_NOISE_CANCELING;
        public static final Event ACTIVE_NOISE_CANCELING_LEVEL;
        public static final Event ADAPTIVE;
        public static final Event AMBIENT_SOUND;
        public static final Event AMBIENT_SOUND_LEVEL;
        public static final Event BOOST_DIALOGUE;
        public static final Event DETAIL;
        public static final Event DOLBY_ATMOS;
        public static final Event EQUALIZER;
        public static final Event LOUDNESS_NORMALIZATION;
        public static final Event NOISE_CONTROL_OFF;
        public static final Event SHOW;
        public static final Event SHOW_COVER;
        public static final Event SPATIAL_AUDIO;
        private final String id;

        static {
            Event event = new Event("SHOW", 0, "ASSC1001");
            SHOW = event;
            Event event2 = new Event("DOLBY_ATMOS", 1, "ASSC1002");
            DOLBY_ATMOS = event2;
            Event event3 = new Event("EQUALIZER", 2, "ASSC1003");
            EQUALIZER = event3;
            Event event4 = new Event("BOOST_DIALOGUE", 3, "ASSC1004");
            BOOST_DIALOGUE = event4;
            Event event5 = new Event("LOUDNESS_NORMALIZATION", 4, "ASSC1005");
            LOUDNESS_NORMALIZATION = event5;
            Event event6 = new Event("NOISE_CONTROL_OFF", 5, "ASSC1006");
            NOISE_CONTROL_OFF = event6;
            Event event7 = new Event("AMBIENT_SOUND", 6, "ASSC1007");
            AMBIENT_SOUND = event7;
            Event event8 = new Event("ADAPTIVE", 7, "ASSC1008");
            ADAPTIVE = event8;
            Event event9 = new Event("ACTIVE_NOISE_CANCELING", 8, "ASSC1009");
            ACTIVE_NOISE_CANCELING = event9;
            Event event10 = new Event("AMBIENT_SOUND_LEVEL", 9, "ASSC1010");
            AMBIENT_SOUND_LEVEL = event10;
            Event event11 = new Event("ACTIVE_NOISE_CANCELING_LEVEL", 10, "ASSC1011");
            ACTIVE_NOISE_CANCELING_LEVEL = event11;
            Event event12 = new Event("SPATIAL_AUDIO", 11, "ASSC1012");
            SPATIAL_AUDIO = event12;
            Event event13 = new Event("DETAIL", 12, "ASSC1013");
            DETAIL = event13;
            Event event14 = new Event("SHOW_COVER", 13, "ASSC1014");
            SHOW_COVER = event14;
            Event[] eventArr = {event, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11, event12, event13, event14};
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

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ScreenId {
        public static final /* synthetic */ ScreenId[] $VALUES;
        public static final ScreenId EID_BUDS_DETAIL_SETTING;
        public static final ScreenId EID_PHONE_DETAIL_SETTING;
        private final String id;

        static {
            ScreenId screenId = new ScreenId("EID_PHONE_DETAIL_SETTING", 0, "ASPS");
            EID_PHONE_DETAIL_SETTING = screenId;
            ScreenId screenId2 = new ScreenId("EID_BUDS_DETAIL_SETTING", 1, "ASBS");
            EID_BUDS_DETAIL_SETTING = screenId2;
            ScreenId[] screenIdArr = {screenId, screenId2};
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

    private SoundCraftSALogging() {
    }

    public static void sendEventLog$default(SoundCraftSALogging soundCraftSALogging, ScreenId screenId, Event event, String str, int i) {
        if ((i & 4) != 0) {
            str = null;
        }
        soundCraftSALogging.getClass();
        if (str != null) {
            SystemUIAnalytics.sendEventLog(screenId.getId(), event.getId(), str);
        } else {
            SystemUIAnalytics.sendEventLog(screenId.getId(), event.getId());
        }
        Unit unit = Unit.INSTANCE;
        StringBuilder sb = new StringBuilder("sendEventLog: screen = ");
        sb.append(screenId);
        sb.append(" , event = ");
        sb.append(event);
        sb.append(", detail = ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "SoundCraftSALogging");
    }
}
