package com.samsung.android.sdk.moneta.memory.entity.activity;

import java.util.Iterator;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class ActivityType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ActivityType[] $VALUES;
    public static final Companion Companion;
    private final int value;
    public static final ActivityType Eating = new ActivityType("Eating", 0, 0);
    public static final ActivityType Exercising = new ActivityType("Exercising", 1, 1);
    public static final ActivityType ListeningToMusic = new ActivityType("ListeningToMusic", 2, 2);
    public static final ActivityType Moving = new ActivityType("Moving", 3, 3);
    public static final ActivityType Paying = new ActivityType("Paying", 4, 4);
    public static final ActivityType Sleeping = new ActivityType("Sleeping", 5, 5);
    public static final ActivityType SpeakingOnPhone = new ActivityType("SpeakingOnPhone", 6, 6);
    public static final ActivityType Staying = new ActivityType("Staying", 7, 7);
    public static final ActivityType TakingPictures = new ActivityType("TakingPictures", 8, 8);
    public static final ActivityType WatchingVideo = new ActivityType("WatchingVideo", 9, 9);
    public static final ActivityType SharingContents = new ActivityType("SharingContents", 10, 10);
    public static final ActivityType StoringContents = new ActivityType("StoringContents", 11, 11);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static ActivityType fromInt(Integer num) {
            Object next;
            Iterator<E> it = ActivityType.getEntries().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (((ActivityType) next).getValue() == num.intValue()) {
                    break;
                }
            }
            return (ActivityType) next;
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ ActivityType[] $values() {
        return new ActivityType[]{Eating, Exercising, ListeningToMusic, Moving, Paying, Sleeping, SpeakingOnPhone, Staying, TakingPictures, WatchingVideo, SharingContents, StoringContents};
    }

    static {
        ActivityType[] activityTypeArr$values = $values();
        $VALUES = activityTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(activityTypeArr$values);
        Companion = new Companion(null);
    }

    private ActivityType(String str, int i, int i2) {
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static ActivityType valueOf(String str) {
        return (ActivityType) Enum.valueOf(ActivityType.class, str);
    }

    public static ActivityType[] values() {
        return (ActivityType[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
