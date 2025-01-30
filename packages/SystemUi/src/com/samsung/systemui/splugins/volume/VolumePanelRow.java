package com.samsung.systemui.splugins.volume;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VolumePanelRow {
    public static final int BASE_PRIORITY = 2;
    public static final Companion Companion = new Companion(null);
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
    public static final int ICON_MUTE = 1;
    public static final int ICON_REMOTE = 6;
    public static final int ICON_REMOTE_MUTE = 7;
    public static final int ICON_REMOTE_SPEAKER = 11;
    public static final int ICON_VIBRATE = 0;
    public static final int PROGRESS_BAR_UNIT = 100;
    public static final long USER_ATTEMPT_GRACE_PERIOD = 1000;
    private int streamType;
    private HashMap<BooleanStateKey, Boolean> boolMap = new HashMap<>();
    private HashMap<IntegerStateKey, Integer> intMap = new HashMap<>();
    private HashMap<StringStateKey, String> stringMap = new HashMap<>();
    private HashMap<LongStateKey, Long> longMap = new HashMap<>();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum BooleanStateKey {
        IMPORTANT,
        DYNAMIC,
        ROUTED_TO_BLUETOOTH,
        MUTED,
        SLIDER_ENABLED,
        TRACKING,
        VISIBILITY,
        ICON_CLICKABLE,
        ICON_ENABLED,
        ACTIVE_NOW
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @Target({ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IconTypes {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum IntegerStateKey {
        REAL_LEVEL,
        LEVEL,
        LEVEL_MIN,
        LEVEL_MAX,
        ICON_TYPE,
        AUDIBLE_LEVEL,
        EAR_PROTECT_LEVEL,
        PRIORITY,
        ORIGINAL_PRIORITY
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum LongStateKey {
        USER_ATTEMPT_TIME
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum StringStateKey {
        REMOTE_LABEL,
        DUAL_BT_DEVICE_ADDRESS,
        DUAL_BT_DEVICE_NAME,
        SMART_VIEW_LABEL,
        NAME_RES
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
        if (num == null) {
            return -1;
        }
        return num.intValue();
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
        if (l == null) {
            return -1L;
        }
        return l.longValue();
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
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
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
        return "[" + VolumePanelValues.INSTANCE.rowStreamTypeToString(this.streamType) + "(vol=" + getRealLevel() + ")] ";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Builder {
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
