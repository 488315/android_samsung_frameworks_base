package com.samsung.systemui.splugins.volume;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class VolumePanelRow {
    public static final int BASE_PRIORITY = 2;
    public static final int ICON_APP_MIRRORING = 8;
    public static final int ICON_AUDIO_ACC = 4;
    public static final int ICON_BLUETOOTH = 2;
    public static final int ICON_BUDS = 10;
    public static final int ICON_BUDS3 = 13;
    public static final int ICON_DEFAULT = 3;
    public static final int ICON_HEADSET = 9;
    public static final int ICON_HEARING_AID = 14;
    public static final int ICON_HOME_MINI = 12;
    public static final int ICON_MIRRORING = 5;
    public static final int ICON_MUSIC_FRAME = 15;
    public static final int ICON_MUTE = 1;
    public static final int ICON_REMOTE = 6;
    public static final int ICON_REMOTE_MUTE = 7;
    public static final int ICON_REMOTE_SPEAKER = 11;
    public static final int ICON_VIBRATE = 0;
    public static final int PROGRESS_BAR_UNIT = 100;
    public static final long USER_ATTEMPT_GRACE_PERIOD = 1000;
    private int streamType;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private HashMap<BooleanStateKey, Boolean> boolMap = new HashMap<>();
    private HashMap<IntegerStateKey, Integer> intMap = new HashMap<>();
    private HashMap<StringStateKey, String> stringMap = new HashMap<>();
    private HashMap<LongStateKey, Long> longMap = new HashMap<>();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BooleanStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ BooleanStateKey[] $VALUES;
        public static final BooleanStateKey IMPORTANT = new BooleanStateKey("IMPORTANT", 0);
        public static final BooleanStateKey DYNAMIC = new BooleanStateKey("DYNAMIC", 1);
        public static final BooleanStateKey ROUTED_TO_BLUETOOTH = new BooleanStateKey("ROUTED_TO_BLUETOOTH", 2);
        public static final BooleanStateKey MUTED = new BooleanStateKey("MUTED", 3);
        public static final BooleanStateKey SLIDER_ENABLED = new BooleanStateKey("SLIDER_ENABLED", 4);
        public static final BooleanStateKey TRACKING = new BooleanStateKey("TRACKING", 5);
        public static final BooleanStateKey VISIBILITY = new BooleanStateKey("VISIBILITY", 6);
        public static final BooleanStateKey ICON_CLICKABLE = new BooleanStateKey("ICON_CLICKABLE", 7);
        public static final BooleanStateKey ICON_ENABLED = new BooleanStateKey("ICON_ENABLED", 8);
        public static final BooleanStateKey ACTIVE_NOW = new BooleanStateKey("ACTIVE_NOW", 9);

        private static final /* synthetic */ BooleanStateKey[] $values() {
            return new BooleanStateKey[]{IMPORTANT, DYNAMIC, ROUTED_TO_BLUETOOTH, MUTED, SLIDER_ENABLED, TRACKING, VISIBILITY, ICON_CLICKABLE, ICON_ENABLED, ACTIVE_NOW};
        }

        static {
            BooleanStateKey[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private BooleanStateKey(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static BooleanStateKey valueOf(String str) {
            return (BooleanStateKey) Enum.valueOf(BooleanStateKey.class, str);
        }

        public static BooleanStateKey[] values() {
            return (BooleanStateKey[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Target({ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IconTypes {
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IntegerStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ IntegerStateKey[] $VALUES;
        public static final IntegerStateKey REAL_LEVEL = new IntegerStateKey("REAL_LEVEL", 0);
        public static final IntegerStateKey LEVEL = new IntegerStateKey("LEVEL", 1);
        public static final IntegerStateKey LEVEL_MIN = new IntegerStateKey("LEVEL_MIN", 2);
        public static final IntegerStateKey LEVEL_MAX = new IntegerStateKey("LEVEL_MAX", 3);
        public static final IntegerStateKey ICON_TYPE = new IntegerStateKey("ICON_TYPE", 4);
        public static final IntegerStateKey AUDIBLE_LEVEL = new IntegerStateKey("AUDIBLE_LEVEL", 5);
        public static final IntegerStateKey EAR_PROTECT_LEVEL = new IntegerStateKey("EAR_PROTECT_LEVEL", 6);
        public static final IntegerStateKey PRIORITY = new IntegerStateKey("PRIORITY", 7);
        public static final IntegerStateKey ORIGINAL_PRIORITY = new IntegerStateKey("ORIGINAL_PRIORITY", 8);

        private static final /* synthetic */ IntegerStateKey[] $values() {
            return new IntegerStateKey[]{REAL_LEVEL, LEVEL, LEVEL_MIN, LEVEL_MAX, ICON_TYPE, AUDIBLE_LEVEL, EAR_PROTECT_LEVEL, PRIORITY, ORIGINAL_PRIORITY};
        }

        static {
            IntegerStateKey[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private IntegerStateKey(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static IntegerStateKey valueOf(String str) {
            return (IntegerStateKey) Enum.valueOf(IntegerStateKey.class, str);
        }

        public static IntegerStateKey[] values() {
            return (IntegerStateKey[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LongStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ LongStateKey[] $VALUES;
        public static final LongStateKey USER_ATTEMPT_TIME = new LongStateKey("USER_ATTEMPT_TIME", 0);

        private static final /* synthetic */ LongStateKey[] $values() {
            return new LongStateKey[]{USER_ATTEMPT_TIME};
        }

        static {
            LongStateKey[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private LongStateKey(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static LongStateKey valueOf(String str) {
            return (LongStateKey) Enum.valueOf(LongStateKey.class, str);
        }

        public static LongStateKey[] values() {
            return (LongStateKey[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StringStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ StringStateKey[] $VALUES;
        public static final StringStateKey REMOTE_LABEL = new StringStateKey("REMOTE_LABEL", 0);
        public static final StringStateKey DUAL_BT_DEVICE_ADDRESS = new StringStateKey("DUAL_BT_DEVICE_ADDRESS", 1);
        public static final StringStateKey DUAL_BT_DEVICE_NAME = new StringStateKey("DUAL_BT_DEVICE_NAME", 2);
        public static final StringStateKey SMART_VIEW_LABEL = new StringStateKey("SMART_VIEW_LABEL", 3);
        public static final StringStateKey NAME_RES = new StringStateKey("NAME_RES", 4);

        private static final /* synthetic */ StringStateKey[] $values() {
            return new StringStateKey[]{REMOTE_LABEL, DUAL_BT_DEVICE_ADDRESS, DUAL_BT_DEVICE_NAME, SMART_VIEW_LABEL, NAME_RES};
        }

        static {
            StringStateKey[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private StringStateKey(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static StringStateKey valueOf(String str) {
            return (StringStateKey) Enum.valueOf(StringStateKey.class, str);
        }

        public static StringStateKey[] values() {
            return (StringStateKey[]) $VALUES.clone();
        }
    }

    public final int getAudibleLevel() {
        return getIntegerValue(IntegerStateKey.AUDIBLE_LEVEL);
    }

    public final String getDualBtDeviceAddress() {
        return getStringValue(StringStateKey.DUAL_BT_DEVICE_ADDRESS);
    }

    public final String getDualBtDeviceName() {
        return getStringValue(StringStateKey.DUAL_BT_DEVICE_NAME);
    }

    public final int getEarProtectLevel() {
        return getIntegerValue(IntegerStateKey.EAR_PROTECT_LEVEL);
    }

    public final int getIconType() {
        return getIntegerValue(IntegerStateKey.ICON_TYPE);
    }

    public final int getIntegerValue(IntegerStateKey integerStateKey) {
        Integer num = this.intMap.get(integerStateKey);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public final int getLevel() {
        return getIntegerValue(IntegerStateKey.LEVEL);
    }

    public final int getLevelMax() {
        return getIntegerValue(IntegerStateKey.LEVEL_MAX);
    }

    public final int getLevelMin() {
        return getIntegerValue(IntegerStateKey.LEVEL_MIN);
    }

    public final long getLongValue(LongStateKey longStateKey) {
        Long l = this.longMap.get(longStateKey);
        if (l != null) {
            return l.longValue();
        }
        return -1L;
    }

    public final String getNameRes() {
        return getStringValue(StringStateKey.NAME_RES);
    }

    public final int getOriginalPriority() {
        return getIntegerValue(IntegerStateKey.ORIGINAL_PRIORITY);
    }

    public final int getPriority() {
        return getIntegerValue(IntegerStateKey.PRIORITY);
    }

    public final int getRealLevel() {
        return getIntegerValue(IntegerStateKey.REAL_LEVEL);
    }

    public final String getRemoteLabel() {
        return getStringValue(StringStateKey.REMOTE_LABEL);
    }

    public final String getSmartViewLabel() {
        return getStringValue(StringStateKey.SMART_VIEW_LABEL);
    }

    public final int getStreamType() {
        return this.streamType;
    }

    public final String getStringValue(StringStateKey stringStateKey) {
        String str = this.stringMap.get(stringStateKey);
        return str == null ? "" : str;
    }

    public final long getUserAttemptTime() {
        return getLongValue(LongStateKey.USER_ATTEMPT_TIME);
    }

    public final boolean isActiveShow() {
        return isEnabled(BooleanStateKey.ACTIVE_NOW);
    }

    public final boolean isDynamic() {
        return isEnabled(BooleanStateKey.DYNAMIC);
    }

    public final boolean isEnabled(BooleanStateKey booleanStateKey) {
        Boolean bool = this.boolMap.get(booleanStateKey);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public final boolean isIconClickable() {
        return isEnabled(BooleanStateKey.ICON_CLICKABLE);
    }

    public final boolean isIconEnabled() {
        return isEnabled(BooleanStateKey.ICON_ENABLED);
    }

    public final boolean isImportant() {
        return isEnabled(BooleanStateKey.IMPORTANT);
    }

    public final boolean isMuted() {
        return isEnabled(BooleanStateKey.MUTED);
    }

    public final boolean isRoutedToBluetooth() {
        return isEnabled(BooleanStateKey.ROUTED_TO_BLUETOOTH);
    }

    public final boolean isSliderEnabled() {
        return isEnabled(BooleanStateKey.SLIDER_ENABLED);
    }

    public final boolean isTracking() {
        return isEnabled(BooleanStateKey.TRACKING);
    }

    public final boolean isVisible() {
        return isEnabled(BooleanStateKey.VISIBILITY);
    }

    public String toString() {
        return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(getRealLevel(), "[", VolumePanelValues.INSTANCE.rowStreamTypeToString(this.streamType), "(vol=", ")] ");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public static final int $stable = 8;
        private VolumePanelRow volumePanelRow;

        public Builder() {
            this.volumePanelRow = new VolumePanelRow();
        }

        public final Builder audibleLevel(int i) {
            return setIntegerValue(IntegerStateKey.AUDIBLE_LEVEL, i);
        }

        public final VolumePanelRow build() {
            return this.volumePanelRow;
        }

        public final Builder dualBtDeviceAddress(String str) {
            return setStringValue(StringStateKey.DUAL_BT_DEVICE_ADDRESS, str);
        }

        public final Builder dualBtDeviceName(String str) {
            return setStringValue(StringStateKey.DUAL_BT_DEVICE_NAME, str);
        }

        public final Builder earProtectionLevel(int i) {
            return setIntegerValue(IntegerStateKey.EAR_PROTECT_LEVEL, i);
        }

        public final Builder iconType(int i) {
            return setIntegerValue(IntegerStateKey.ICON_TYPE, i);
        }

        public final Builder isActiveShow(boolean z) {
            return setEnabled(BooleanStateKey.ACTIVE_NOW, z);
        }

        public final Builder isDynamic(boolean z) {
            return setEnabled(BooleanStateKey.DYNAMIC, z);
        }

        public final Builder isIconClickable(boolean z) {
            return setEnabled(BooleanStateKey.ICON_CLICKABLE, z);
        }

        public final Builder isIconEnabled(boolean z) {
            return setEnabled(BooleanStateKey.ICON_ENABLED, z);
        }

        public final Builder isImportant(boolean z) {
            return setEnabled(BooleanStateKey.IMPORTANT, z);
        }

        public final Builder isMuted(boolean z) {
            return setEnabled(BooleanStateKey.MUTED, z);
        }

        public final Builder isRoutedToBluetooth(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_BLUETOOTH, z);
        }

        public final Builder isSliderEnabled(boolean z) {
            return setEnabled(BooleanStateKey.SLIDER_ENABLED, z);
        }

        public final Builder isTracking(boolean z) {
            return setEnabled(BooleanStateKey.TRACKING, z);
        }

        public final Builder isVisible(boolean z) {
            return setEnabled(BooleanStateKey.VISIBILITY, z);
        }

        public final Builder level(int i) {
            return setIntegerValue(IntegerStateKey.LEVEL, i);
        }

        public final Builder levelMax(int i) {
            return setIntegerValue(IntegerStateKey.LEVEL_MAX, i);
        }

        public final Builder levelMin(int i) {
            return setIntegerValue(IntegerStateKey.LEVEL_MIN, i);
        }

        public final Builder nameRes(String str) {
            return setStringValue(StringStateKey.NAME_RES, str);
        }

        public final Builder originalPriority(int i) {
            return setIntegerValue(IntegerStateKey.ORIGINAL_PRIORITY, i);
        }

        public final Builder priority(int i) {
            return setIntegerValue(IntegerStateKey.PRIORITY, i);
        }

        public final Builder realLevel(int i) {
            return setIntegerValue(IntegerStateKey.REAL_LEVEL, i);
        }

        public final Builder remoteLabel(String str) {
            setStringValue(StringStateKey.REMOTE_LABEL, str);
            return this;
        }

        public final Builder setEnabled(BooleanStateKey booleanStateKey, boolean z) {
            this.volumePanelRow.boolMap.put(booleanStateKey, Boolean.valueOf(z));
            return this;
        }

        public final Builder setIntegerValue(IntegerStateKey integerStateKey, int i) {
            this.volumePanelRow.intMap.put(integerStateKey, Integer.valueOf(i));
            return this;
        }

        public final Builder setLongValue(LongStateKey longStateKey, long j) {
            this.volumePanelRow.longMap.put(longStateKey, Long.valueOf(j));
            return this;
        }

        public final Builder setStreamType(int i) {
            this.volumePanelRow.streamType = i;
            return this;
        }

        public final Builder setStringValue(StringStateKey stringStateKey, String str) {
            this.volumePanelRow.stringMap.put(stringStateKey, str);
            return this;
        }

        public final Builder smartViewLabel(String str) {
            return setStringValue(StringStateKey.SMART_VIEW_LABEL, str);
        }

        public final Builder userAttemptTime(long j) {
            return setLongValue(LongStateKey.USER_ATTEMPT_TIME, j);
        }

        public Builder(VolumePanelRow volumePanelRow) {
            VolumePanelRow volumePanelRow2 = new VolumePanelRow();
            volumePanelRow2.streamType = volumePanelRow.getStreamType();
            volumePanelRow2.boolMap = volumePanelRow.boolMap;
            volumePanelRow2.intMap = volumePanelRow.intMap;
            volumePanelRow2.stringMap = volumePanelRow.stringMap;
            volumePanelRow2.longMap = volumePanelRow.longMap;
            this.volumePanelRow = volumePanelRow2;
        }
    }

    public static /* synthetic */ void getIconType$annotations() {
    }

    public static /* synthetic */ void getStreamType$annotations() {
    }
}
