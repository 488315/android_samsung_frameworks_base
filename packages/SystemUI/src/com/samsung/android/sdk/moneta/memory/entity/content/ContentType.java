package com.samsung.android.sdk.moneta.memory.entity.content;

import com.android.systemui.util.SystemUIAnalytics;
import java.util.Iterator;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ContentType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ContentType[] $VALUES;
    public static final Companion Companion;
    private final int value;
    public static final ContentType CalendarEvent = new ContentType("CalendarEvent", 0, 0);
    public static final ContentType CallLog = new ContentType("CallLog", 1, 1);
    public static final ContentType Media = new ContentType("Media", 2, 2);
    public static final ContentType Message = new ContentType("Message", 3, 3);
    public static final ContentType MobileApplication = new ContentType("MobileApplication", 4, 4);
    public static final ContentType Music = new ContentType("Music", 5, 5);
    public static final ContentType Video = new ContentType(SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_VIDEO, 6, 6);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static ContentType fromInt(Integer num) {
            Object obj;
            Iterator<E> it = ContentType.getEntries().iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                int value = ((ContentType) obj).getValue();
                if (num != null && value == num.intValue()) {
                    break;
                }
            }
            return (ContentType) obj;
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ ContentType[] $values() {
        return new ContentType[]{CalendarEvent, CallLog, Media, Message, MobileApplication, Music, Video};
    }

    static {
        ContentType[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
        Companion = new Companion(null);
    }

    private ContentType(String str, int i, int i2) {
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static ContentType valueOf(String str) {
        return (ContentType) Enum.valueOf(ContentType.class, str);
    }

    public static ContentType[] values() {
        return (ContentType[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
