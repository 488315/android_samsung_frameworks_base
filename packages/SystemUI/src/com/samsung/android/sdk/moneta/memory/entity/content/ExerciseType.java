package com.samsung.android.sdk.moneta.memory.entity.content;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class ExerciseType {
    public static final /* synthetic */ ExerciseType[] $VALUES;
    public static final Companion Companion;
    public static final ExerciseType OTHER;
    public static final Map map;
    private final String value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        ExerciseType exerciseType = new ExerciseType("OTHER", 0, "Other");
        OTHER = exerciseType;
        ExerciseType[] exerciseTypeArr = {exerciseType, new ExerciseType("WALKING", 1, "Walking"), new ExerciseType("RUNNING", 2, "Running"), new ExerciseType("FLOOR_CLIMB", 3, "Floor climb"), new ExerciseType("TRACK_RUN", 4, "Track run"), new ExerciseType("BASEBALL", 5, "Baseball"), new ExerciseType("SOFTBALL", 6, "Softball"), new ExerciseType("CRICKET", 7, "Cricket"), new ExerciseType("GOLF", 8, "Golf"), new ExerciseType("BOWLING", 9, "Bowling"), new ExerciseType("HOCKEY", 10, "Hockey"), new ExerciseType("RUGBY", 11, "Rugby"), new ExerciseType("BASKETBALL", 12, "Basketball"), new ExerciseType("SOCCER", 13, "Soccer"), new ExerciseType("HANDBALL", 14, "Handball"), new ExerciseType("FOOTBALL", 15, "Football"), new ExerciseType("VOLLEYBALL", 16, "Volleyball"), new ExerciseType("BEACH_VOLLEYBALL", 17, "Beach volleyball"), new ExerciseType("SQUASH", 18, "Squash"), new ExerciseType("TENNIS", 19, "Tennis"), new ExerciseType("BADMINTON", 20, "Badminton"), new ExerciseType("TABLE_TENNIS", 21, "Table tennis"), new ExerciseType("RACQUETBALL", 22, "Racquetball"), new ExerciseType("BOXING", 23, "Boxing"), new ExerciseType("MARTIAL_ARTS", 24, "Martial arts"), new ExerciseType("BALLET", 25, "Ballet"), new ExerciseType("DANCING", 26, "Dancing"), new ExerciseType("BALLROOM_DANCING", 27, "Ballroom dancing"), new ExerciseType("PILATES", 28, "Pilates"), new ExerciseType("YOGA", 29, "Yoga"), new ExerciseType("STRETCHING", 30, "Stretching"), new ExerciseType("JUMP_ROPE", 31, "Jump rope"), new ExerciseType("HULA_HOOPING", 32, "Hula-hooping"), new ExerciseType("PUSH_UPS", 33, "Push-ups"), new ExerciseType("PULL_UPS", 34, "Pull-ups"), new ExerciseType("SIT_UPS", 35, "Sit-ups"), new ExerciseType("CIRCUIT_TRAINING", 36, "Circuit training"), new ExerciseType("MOUNTAIN_CLIMBERS", 37, "Mountain climbers"), new ExerciseType("JUMPING_JACKS", 38, "Jumping Jacks"), new ExerciseType("BURPEE", 39, "Burpee"), new ExerciseType("BENCH_PRESS", 40, "Bench press"), new ExerciseType("SQUATS", 41, "Squats"), new ExerciseType("LUNGES", 42, "Lunges"), new ExerciseType("LEG_PRESSES", 43, "Leg presses"), new ExerciseType("LEG_EXTENSIONS", 44, "Leg extensions"), new ExerciseType("LEG_CURLS", 45, "Leg curls"), new ExerciseType("BACK_EXTENSIONS", 46, "Back extensions"), new ExerciseType("LAT_PULL_DOWNS", 47, "Lat Pull-downs"), new ExerciseType("DEADLIFTS", 48, "Deadlifts"), new ExerciseType("SHOULDER_PRESSES", 49, "Shoulder presses"), new ExerciseType("FRONT_RAISES", 50, "Front raises"), new ExerciseType("LATERAL_RAISES", 51, "Lateral raises"), new ExerciseType("CRUNCHES", 52, "Crunches"), new ExerciseType("LEG_RAISES", 53, "Leg raises"), new ExerciseType("PLANK", 54, "Plank"), new ExerciseType("ARM_CURLS", 55, "Arm curls"), new ExerciseType("ARM_EXTENSIONS", 56, "Arm extensions"), new ExerciseType("SKATERS", 57, "Skaters"), new ExerciseType("HANG_KNEES", 58, "High knees"), new ExerciseType("INLINE_SKATING", 59, "Inline skating"), new ExerciseType("HANG_GLIDING", 60, "Hang gliding"), new ExerciseType("ARCHERY", 61, "Archery"), new ExerciseType("HORSEBACK_RIDING", 62, "Horseback riding"), new ExerciseType("BIKE", 63, "Bike"), new ExerciseType("FLYING_DISC", 64, "Flying disc"), new ExerciseType("ROLLER_SKATING", 65, "Roller skating"), new ExerciseType("AEROBICS", 66, "Aerobics"), new ExerciseType("HIKING", 67, "Hiking"), new ExerciseType("ROCK_CLIMBING", 68, "Rock climbing"), new ExerciseType("BACKPACKING", 69, "Backpacking"), new ExerciseType("MOUNTAIN_BIKING", 70, "Mountain biking"), new ExerciseType("ORIENTEERING", 71, "Orienteering"), new ExerciseType("SWIMMING", 72, "Swimming"), new ExerciseType("AQUAROBICS", 73, "Aquarobics"), new ExerciseType("CANOEING", 74, "Canoeing"), new ExerciseType("SAILING", 75, "Sailing"), new ExerciseType("SCUBA_DIVING", 76, "Scuba diving"), new ExerciseType("SNORKELING", 77, "Snorkeling"), new ExerciseType("KAYAKING", 78, "Kayaking"), new ExerciseType("KITESURFING", 79, "Kitesurfing"), new ExerciseType("RAFTING", 80, "Rafting"), new ExerciseType("ROWING", 81, "Rowing"), new ExerciseType("WINDSURFING", 82, "Windsurfing"), new ExerciseType("YACHTING", 83, "Yachting"), new ExerciseType("WATER_SKIING", 84, "Water skiing"), new ExerciseType("STEP_MACHINE", 85, "Step machine"), new ExerciseType("WEIGHT_MACHINE", 86, "Weight machine"), new ExerciseType("BIKE_INDOOR", 87, "Bike indoor"), new ExerciseType("ROWING_MACHINE", 88, "Rowing machine"), new ExerciseType("TREADMILL", 89, "Treadmill"), new ExerciseType("ELLIPTICAL_TRAINER", 90, "Elliptical trainer"), new ExerciseType("STAIR_CLIMBER", 91, "Stair climber"), new ExerciseType("CROSS_COUNTRY_SKIING", 92, "Cross-country skiing"), new ExerciseType("SKIING", 93, "Skiing"), new ExerciseType("ICE_DANCING", 94, "Ice dancing"), new ExerciseType("ICE_SKATING", 95, "Ice skating"), new ExerciseType("ICE_HOCKEY", 96, "Ice hockey"), new ExerciseType("SNOWBOARDING", 97, "Snowboarding"), new ExerciseType("ALPINE_SKIING", 98, "Alpine skiing"), new ExerciseType("SNOWSHOEING", 99, "Snowshoeing"), new ExerciseType("TRIATHLON", 100, "Triathlon"), new ExerciseType("DUATHLON", 101, "Duathlon"), new ExerciseType("AQUATHLON", 102, "Aquathlon"), new ExerciseType("AQUABIKE", 103, "Aquabike"), new ExerciseType("CROSS_TRIATHLON", 104, "Cross triathlon"), new ExerciseType("CROSS_DUATHLON", 105, "Cross Duathlon"), new ExerciseType("BREAK", 106, "Break"), new ExerciseType("COOL_DOWN", 107, "Cool Down"), new ExerciseType("WARM_UP", 108, "Warm Up"), new ExerciseType("TRANSITION", 109, "Transition")};
        $VALUES = exerciseTypeArr;
        List listEnumEntries = EnumEntriesKt.enumEntries(exerciseTypeArr);
        Companion = new Companion(null);
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(listEnumEntries, 10));
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity < 16 ? 16 : iMapCapacity);
        AbstractList.IteratorImpl iteratorImpl = ((AbstractList) listEnumEntries).new IteratorImpl();
        while (iteratorImpl.hasNext()) {
            Object next = iteratorImpl.next();
            linkedHashMap.put(((ExerciseType) next).value, next);
        }
        map = linkedHashMap;
    }

    private ExerciseType(String str, int i, String str2) {
        this.value = str2;
    }

    public static ExerciseType valueOf(String str) {
        return (ExerciseType) Enum.valueOf(ExerciseType.class, str);
    }

    public static ExerciseType[] values() {
        return (ExerciseType[]) $VALUES.clone();
    }
}
