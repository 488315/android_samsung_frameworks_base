package com.samsung.systemui.splugins.volume;

import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class VolumePanelState {
    public static final int DIALOG_EXPAND_TIMEOUT_MILLIS = 5000;
    public static final int DIALOG_HOVERING_TIMEOUT_MILLIS = 16000;
    public static final int DIALOG_ODI_CAPTIONS_TOOLTIP_TIMEOUT_MILLIS = 5000;
    public static final int DIALOG_SAFETYWARNING_TIMEOUT_MILLIS = 5000;
    public static final int DIALOG_TIMEOUT_MILLIS = 3000;
    public static final int DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS = 60000;
    public static final int DIALOG_TIMEOUT_SUBDISPLAY = 1000;
    private HashMap<BooleanStateKey, Boolean> booleanState;
    private Object customState;
    private HashMap<IntegerStateKey, Integer> integerState;
    private HashMap<LongStateKey, Long> longState;
    private StateType stateType;
    private HashMap<StringStateKey, String> stringState;
    private List<VolumePanelRow> volumeRowList;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class BooleanStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ BooleanStateKey[] $VALUES;
        private final String fieldName;
        public static final BooleanStateKey SHOWING = new BooleanStateKey("SHOWING", 0, "isShowing");
        public static final BooleanStateKey DLNA_ENABLED = new BooleanStateKey("DLNA_ENABLED", 1, "isDlnaEnabled");
        public static final BooleanStateKey SUPPORT_TV_VOLUME_CONTROL = new BooleanStateKey("SUPPORT_TV_VOLUME_CONTROL", 2, "isSupportTvVolumeControl");
        public static final BooleanStateKey MEDIA_DEFAULT_ENABLED = new BooleanStateKey("MEDIA_DEFAULT_ENABLED", 3, "isMediaDefaultEnabled");
        public static final BooleanStateKey ANIMATING = new BooleanStateKey("ANIMATING", 4, "isAnimating");
        public static final BooleanStateKey PENDING_STATE = new BooleanStateKey("PENDING_STATE", 5, "isPendingState");
        public static final BooleanStateKey VOICE_CAPABLE = new BooleanStateKey("VOICE_CAPABLE", 6, "isVoiceCapable");
        public static final BooleanStateKey SAFE_MEDIA_DEVICE_ON = new BooleanStateKey("SAFE_MEDIA_DEVICE_ON", 7, "isSafeMediaDeviceOn");
        public static final BooleanStateKey SAFE_MEDIA_PIN_DEVICE_ON = new BooleanStateKey("SAFE_MEDIA_PIN_DEVICE_ON", 8, "isSafeMediaPinDeviceOn");
        public static final BooleanStateKey EXPANDED = new BooleanStateKey("EXPANDED", 9, "isExpanded");
        public static final BooleanStateKey TRACKING = new BooleanStateKey("TRACKING", 10, "isTracking");
        public static final BooleanStateKey HAS_VIBRATOR = new BooleanStateKey("HAS_VIBRATOR", 11, "isHasVibrator");
        public static final BooleanStateKey ALL_SOUND_OFF = new BooleanStateKey("ALL_SOUND_OFF", 12, "isAllSoundOff");
        public static final BooleanStateKey IS_DUAL_AUDIO = new BooleanStateKey("IS_DUAL_AUDIO", 13, "isDualAudio");
        public static final BooleanStateKey SHOW_A11Y_STREAM = new BooleanStateKey("SHOW_A11Y_STREAM", 14, "isShowA11yStream");
        public static final BooleanStateKey IS_FROM_KEY = new BooleanStateKey("IS_FROM_KEY", 15, "isFromKey");
        public static final BooleanStateKey CONFIGURATION_CHANGED = new BooleanStateKey("CONFIGURATION_CHANGED", 16, "isConfigurationChanged");
        public static final BooleanStateKey IS_COVER_CLOSED = new BooleanStateKey("IS_COVER_CLOSED", 17, "isCoverClosed");
        public static final BooleanStateKey SHOWING_VOLUME_LIMITER_DIALOG = new BooleanStateKey("SHOWING_VOLUME_LIMITER_DIALOG", 18, "isShowingVolumeLimiterDialog");
        public static final BooleanStateKey SHOWING_VOLUME_SAFETY_WARNING_DIALOG = new BooleanStateKey("SHOWING_VOLUME_SAFETY_WARNING_DIALOG", 19, "isShowingVolumeSafetyWarningDialog");
        public static final BooleanStateKey OPEN_THEME_CHANGED = new BooleanStateKey("OPEN_THEME_CHANGED", 20, "isOpenThemeChanged");
        public static final BooleanStateKey ZEN_MODE = new BooleanStateKey("ZEN_MODE", 21, "isZenMode");
        public static final BooleanStateKey WITH_ANIMATION = new BooleanStateKey("WITH_ANIMATION", 22, "isWithAnimation");
        public static final BooleanStateKey IS_LOCKSCREEN = new BooleanStateKey("IS_LOCKSCREEN", 23, "isLockscreen");
        public static final BooleanStateKey REMOTE_MIC = new BooleanStateKey("REMOTE_MIC", 24, "isRemoteMic");
        public static final BooleanStateKey IS_BT_SCO_ON = new BooleanStateKey("IS_BT_SCO_ON", 25, "isBtScoOn");
        public static final BooleanStateKey FOLDER_STATE = new BooleanStateKey("FOLDER_STATE", 26, "isFolded");
        public static final BooleanStateKey SHOWING_SUB_DISPLAY_VOLUME_PANEL = new BooleanStateKey("SHOWING_SUB_DISPLAY_VOLUME_PANEL", 27, "isShowingSubDisplayVolumePanel");
        public static final BooleanStateKey IS_MULTI_SOUND_BT = new BooleanStateKey("IS_MULTI_SOUND_BT", 28, "isMultiSoundBt");
        public static final BooleanStateKey IS_MEDIA_DEFAULT_OPTION_HIDE = new BooleanStateKey("IS_MEDIA_DEFAULT_OPTION_HIDE", 29, "isMediaDefaultOptionHide");
        public static final BooleanStateKey IS_CAPTION_COMPONENT_ENABLED = new BooleanStateKey("IS_CAPTION_COMPONENT_ENABLED", 30, "isCaptionComponentEnabled");
        public static final BooleanStateKey IS_CAPTION_ENABLED = new BooleanStateKey("IS_CAPTION_ENABLED", 31, "isCaptionEnabled");
        public static final BooleanStateKey VOLUME_BUDS_TOGETHER = new BooleanStateKey("VOLUME_BUDS_TOGETHER", 32, "isSupportBudsTogether");
        public static final BooleanStateKey SETUP_WIZARD_COMPLETE = new BooleanStateKey("SETUP_WIZARD_COMPLETE", 33, "isSetupWizardComplete");
        public static final BooleanStateKey SHOWING_VOLUME_CSD_100_WARNING_DIALOG = new BooleanStateKey("SHOWING_VOLUME_CSD_100_WARNING_DIALOG", 34, "isShowingVolumeCsd100WarningDialog");
        public static final BooleanStateKey IS_AOD_VOLUME_PANEL = new BooleanStateKey("IS_AOD_VOLUME_PANEL", 35, "isAodVolumePanel");
        public static final BooleanStateKey IS_KEY_DOWN = new BooleanStateKey("IS_KEY_DOWN", 36, "isKeyDown");
        public static final BooleanStateKey IS_VIBRATING = new BooleanStateKey("IS_VIBRATING", 37, "isVibrating");
        public static final BooleanStateKey IS_LE_BROADCASTING = new BooleanStateKey("IS_LE_BROADCASTING", 38, "isLeBroadcasting");
        public static final BooleanStateKey QP_VOLUMEBAR_EANBLED = new BooleanStateKey("QP_VOLUMEBAR_EANBLED", 39, "qpVolumeBarEnabled");

        private static final /* synthetic */ BooleanStateKey[] $values() {
            return new BooleanStateKey[]{SHOWING, DLNA_ENABLED, SUPPORT_TV_VOLUME_CONTROL, MEDIA_DEFAULT_ENABLED, ANIMATING, PENDING_STATE, VOICE_CAPABLE, SAFE_MEDIA_DEVICE_ON, SAFE_MEDIA_PIN_DEVICE_ON, EXPANDED, TRACKING, HAS_VIBRATOR, ALL_SOUND_OFF, IS_DUAL_AUDIO, SHOW_A11Y_STREAM, IS_FROM_KEY, CONFIGURATION_CHANGED, IS_COVER_CLOSED, SHOWING_VOLUME_LIMITER_DIALOG, SHOWING_VOLUME_SAFETY_WARNING_DIALOG, OPEN_THEME_CHANGED, ZEN_MODE, WITH_ANIMATION, IS_LOCKSCREEN, REMOTE_MIC, IS_BT_SCO_ON, FOLDER_STATE, SHOWING_SUB_DISPLAY_VOLUME_PANEL, IS_MULTI_SOUND_BT, IS_MEDIA_DEFAULT_OPTION_HIDE, IS_CAPTION_COMPONENT_ENABLED, IS_CAPTION_ENABLED, VOLUME_BUDS_TOGETHER, SETUP_WIZARD_COMPLETE, SHOWING_VOLUME_CSD_100_WARNING_DIALOG, IS_AOD_VOLUME_PANEL, IS_KEY_DOWN, IS_VIBRATING, IS_LE_BROADCASTING, QP_VOLUMEBAR_EANBLED};
        }

        static {
            BooleanStateKey[] booleanStateKeyArr$values = $values();
            $VALUES = booleanStateKeyArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(booleanStateKeyArr$values);
        }

        private BooleanStateKey(String str, int i, String str2) {
            this.fieldName = str2;
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

        public final String getFieldName() {
            return this.fieldName;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class IntegerStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ IntegerStateKey[] $VALUES;
        private final String fieldName;
        public static final IntegerStateKey TIME_OUT = new IntegerStateKey("TIME_OUT", 0, "timeOut");
        public static final IntegerStateKey ACTIVE_STREAM = new IntegerStateKey("ACTIVE_STREAM", 1, "activeStream");
        public static final IntegerStateKey PIN_DEVICE = new IntegerStateKey("PIN_DEVICE", 2, "pinDevice");
        public static final IntegerStateKey STREAM = new IntegerStateKey("STREAM", 3, "stream");
        public static final IntegerStateKey MUSIC_FINE_VOLUME = new IntegerStateKey("MUSIC_FINE_VOLUME", 4, "musicFineVolume");
        public static final IntegerStateKey RINGER_MODE_INTERNAL = new IntegerStateKey("RINGER_MODE_INTERNAL", 5, "ringerModeInternal");
        public static final IntegerStateKey EAR_PROTECT_LEVEL = new IntegerStateKey("EAR_PROTECT_LEVEL", 6, "earProtectLevel");
        public static final IntegerStateKey TIME_OUT_CONTROLS = new IntegerStateKey("TIME_OUT_CONTROLS", 7, "timeOutControls");
        public static final IntegerStateKey TIME_OUT_CONTROLS_TEXT = new IntegerStateKey("TIME_OUT_CONTROLS_TEXT", 8, "timeOutControlsText");
        public static final IntegerStateKey COVER_TYPE = new IntegerStateKey("COVER_TYPE", 9, "coverType");
        public static final IntegerStateKey CUTOUT_HEIGHT = new IntegerStateKey("CUTOUT_HEIGHT", 10, "cutoutHeight");
        public static final IntegerStateKey ICON_TARGET_STATE = new IntegerStateKey("ICON_TARGET_STATE", 11, "iconTargetState");
        public static final IntegerStateKey ICON_CURRENT_STATE = new IntegerStateKey("ICON_CURRENT_STATE", 12, "iconCurrentState");
        public static final IntegerStateKey VOLUME_DIRECTION = new IntegerStateKey("VOLUME_DIRECTION", 13, "volumeDirection");

        private static final /* synthetic */ IntegerStateKey[] $values() {
            return new IntegerStateKey[]{TIME_OUT, ACTIVE_STREAM, PIN_DEVICE, STREAM, MUSIC_FINE_VOLUME, RINGER_MODE_INTERNAL, EAR_PROTECT_LEVEL, TIME_OUT_CONTROLS, TIME_OUT_CONTROLS_TEXT, COVER_TYPE, CUTOUT_HEIGHT, ICON_TARGET_STATE, ICON_CURRENT_STATE, VOLUME_DIRECTION};
        }

        static {
            IntegerStateKey[] integerStateKeyArr$values = $values();
            $VALUES = integerStateKeyArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(integerStateKeyArr$values);
        }

        private IntegerStateKey(String str, int i, String str2) {
            this.fieldName = str2;
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

        public final String getFieldName() {
            return this.fieldName;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class LongStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ LongStateKey[] $VALUES;
        public static final LongStateKey SYSTEM_TIME_NOW = new LongStateKey("SYSTEM_TIME_NOW", 0, "systemTimeNow");
        private final String fieldName;

        private static final /* synthetic */ LongStateKey[] $values() {
            return new LongStateKey[]{SYSTEM_TIME_NOW};
        }

        static {
            LongStateKey[] longStateKeyArr$values = $values();
            $VALUES = longStateKeyArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(longStateKeyArr$values);
        }

        private LongStateKey(String str, int i, String str2) {
            this.fieldName = str2;
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

        public final String getFieldName() {
            return this.fieldName;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class StateType {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ StateType[] $VALUES;
        public static final StateType STATE_IDLE = new StateType("STATE_IDLE", 0);
        public static final StateType STATE_SHOW = new StateType("STATE_SHOW", 1);
        public static final StateType STATE_DISMISS = new StateType("STATE_DISMISS", 2);
        public static final StateType STATE_DISMISS_VOLUME_PANEL = new StateType("STATE_DISMISS_VOLUME_PANEL", 3);
        public static final StateType STATE_UPDATE = new StateType("STATE_UPDATE", 4);
        public static final StateType STATE_NO_DISPATCH = new StateType("STATE_NO_DISPATCH", 5);
        public static final StateType STATE_SET_STREAM_VOLUME = new StateType("STATE_SET_STREAM_VOLUME", 6);
        public static final StateType STATE_VOLUME_ICON_CLICKED = new StateType("STATE_VOLUME_ICON_CLICKED", 7);
        public static final StateType STATE_UPDATE_PROGRESS_BAR = new StateType("STATE_UPDATE_PROGRESS_BAR", 8);
        public static final StateType STATE_UPDATE_PROGRESS_BAR_LATER = new StateType("STATE_UPDATE_PROGRESS_BAR_LATER", 9);
        public static final StateType STATE_MEDIA_VOLUME_DEFAULT_CHANGED = new StateType("STATE_MEDIA_VOLUME_DEFAULT_CHANGED", 10);
        public static final StateType STATE_TOUCH_PANEL = new StateType("STATE_TOUCH_PANEL", 11);
        public static final StateType STATE_RESCHEDULE_TIME_OUT = new StateType("STATE_RESCHEDULE_TIME_OUT", 12);
        public static final StateType STATE_PLAY_SOUND_ON = new StateType("STATE_PLAY_SOUND_ON", 13);
        public static final StateType STATE_SMART_VIEW_ICON_CLICKED = new StateType("STATE_SMART_VIEW_ICON_CLICKED", 14);
        public static final StateType STATE_SMART_VIEW_SEEKBAR_TOUCHED = new StateType("STATE_SMART_VIEW_SEEKBAR_TOUCHED", 15);
        public static final StateType STATE_SHOW_VOLUME_LIMITER_DIALOG = new StateType("STATE_SHOW_VOLUME_LIMITER_DIALOG", 16);
        public static final StateType STATE_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED = new StateType("STATE_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED", 17);
        public static final StateType STATE_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED = new StateType("STATE_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED", 18);
        public static final StateType STATE_VOLUME_LIMITER_DIALOG_VOLUME_DOWN = new StateType("STATE_VOLUME_LIMITER_DIALOG_VOLUME_DOWN", 19);
        public static final StateType STATE_SHOW_VOLUME_SAFETY_WARNING_DIALOG = new StateType("STATE_SHOW_VOLUME_SAFETY_WARNING_DIALOG", 20);
        public static final StateType STATE_DISMISS_VOLUME_SAFETY_WARNING_DIALOG = new StateType("STATE_DISMISS_VOLUME_SAFETY_WARNING_DIALOG", 21);
        public static final StateType STATE_VOLUME_SAFETY_WARNING_DIALOG_FLAG_DISMISS = new StateType("STATE_VOLUME_SAFETY_WARNING_DIALOG_FLAG_DISMISS", 22);
        public static final StateType STATE_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED = new StateType("STATE_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED", 23);
        public static final StateType STATE_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED = new StateType("STATE_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED", 24);
        public static final StateType STATE_START_SLIDER_TRACKING = new StateType("STATE_START_SLIDER_TRACKING", 25);
        public static final StateType STATE_STOP_SLIDER_TRACKING = new StateType("STATE_STOP_SLIDER_TRACKING", 26);
        public static final StateType STATE_OPEN_THEME_CHANGED = new StateType("STATE_OPEN_THEME_CHANGED", 27);
        public static final StateType STATE_DISMISS_VOLUME_PANEL_COMPLETED = new StateType("STATE_DISMISS_VOLUME_PANEL_COMPLETED", 28);
        public static final StateType STATE_UPDATE_PANEL_HEIGHT = new StateType("STATE_UPDATE_PANEL_HEIGHT", 29);
        public static final StateType STATE_EXPAND_STATE_CHANGED = new StateType("STATE_EXPAND_STATE_CHANGED", 30);
        public static final StateType STATE_BACKGROUND_ANIMATION_FINISHED = new StateType("STATE_BACKGROUND_ANIMATION_FINISHED", 31);
        public static final StateType STATE_PANEL_ANIMATION_FINISHED = new StateType("STATE_PANEL_ANIMATION_FINISHED", 32);
        public static final StateType STATE_COVER_STATE_CHANGED = new StateType("STATE_COVER_STATE_CHANGED", 33);
        public static final StateType STATE_CONFIGURATION_CHANGED = new StateType("STATE_CONFIGURATION_CHANGED", 34);
        public static final StateType STATE_CUSTOM = new StateType("STATE_CUSTOM", 35);
        public static final StateType STATE_SHOW_SUB_DISPLAY_VOLUME_PANEL = new StateType("STATE_SHOW_SUB_DISPLAY_VOLUME_PANEL", 36);
        public static final StateType STATE_ARROW_LEFT_CLICKED = new StateType("STATE_ARROW_LEFT_CLICKED", 37);
        public static final StateType STATE_ARROW_RIGHT_CLICKED = new StateType("STATE_ARROW_RIGHT_CLICKED", 38);
        public static final StateType STATE_FOLDER_STATE_CHANGED = new StateType("STATE_FOLDER_STATE_CHANGED", 39);
        public static final StateType STATE_CAPTION_CHANGED = new StateType("STATE_CAPTION_CHANGED", 40);
        public static final StateType STATE_CAPTION_COMPONENT_CHANGED = new StateType("STATE_CAPTION_COMPONENT_CHANGED", 41);
        public static final StateType STATE_DUAL_PLAY_MODE_CHANGED = new StateType("STATE_DUAL_PLAY_MODE_CHANGED", 42);
        public static final StateType STATE_ORIENTATION_CHANGED = new StateType("STATE_ORIENTATION_CHANGED", 43);
        public static final StateType STATE_STATUS_MESSAGE_CLICKED = new StateType("STATE_STATUS_MESSAGE_CLICKED", 44);
        public static final StateType STATE_SETTINGS_BUTTON_CLICKED = new StateType("STATE_SETTINGS_BUTTON_CLICKED", 45);
        public static final StateType STATE_SEEKBAR_START_PROGRESS = new StateType("STATE_SEEKBAR_START_PROGRESS", 46);
        public static final StateType STATE_SEEKBAR_TOUCH_DOWN = new StateType("STATE_SEEKBAR_TOUCH_DOWN", 47);
        public static final StateType STATE_SEEKBAR_TOUCH_UP = new StateType("STATE_SEEKBAR_TOUCH_UP", 48);
        public static final StateType STATE_SET_VOLUME_STATE = new StateType("STATE_SET_VOLUME_STATE", 49);
        public static final StateType STATE_SHOW_VOLUME_CSD_100_WARNING_DIALOG = new StateType("STATE_SHOW_VOLUME_CSD_100_WARNING_DIALOG", 50);
        public static final StateType STATE_DISMISS_VOLUME_CSD_100_WARNING_DIALOG = new StateType("STATE_DISMISS_VOLUME_CSD_100_WARNING_DIALOG", 51);
        public static final StateType STATE_VOLUME_CSD_100_WARNING_DIALOG_FLAG_DISMISS = new StateType("STATE_VOLUME_CSD_100_WARNING_DIALOG_FLAG_DISMISS", 52);
        public static final StateType STATE_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED = new StateType("STATE_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED", 53);
        public static final StateType STATE_KEY_EVENT = new StateType("STATE_KEY_EVENT", 54);
        public static final StateType STATE_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME = new StateType("STATE_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME", 55);
        public static final StateType STATE_STATUS_LE_BROADCASTING_MESSAGE_CLICKED = new StateType("STATE_STATUS_LE_BROADCASTING_MESSAGE_CLICKED", 56);
        public static final StateType STATE_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED = new StateType("STATE_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED", 57);

        private static final /* synthetic */ StateType[] $values() {
            return new StateType[]{STATE_IDLE, STATE_SHOW, STATE_DISMISS, STATE_DISMISS_VOLUME_PANEL, STATE_UPDATE, STATE_NO_DISPATCH, STATE_SET_STREAM_VOLUME, STATE_VOLUME_ICON_CLICKED, STATE_UPDATE_PROGRESS_BAR, STATE_UPDATE_PROGRESS_BAR_LATER, STATE_MEDIA_VOLUME_DEFAULT_CHANGED, STATE_TOUCH_PANEL, STATE_RESCHEDULE_TIME_OUT, STATE_PLAY_SOUND_ON, STATE_SMART_VIEW_ICON_CLICKED, STATE_SMART_VIEW_SEEKBAR_TOUCHED, STATE_SHOW_VOLUME_LIMITER_DIALOG, STATE_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED, STATE_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED, STATE_VOLUME_LIMITER_DIALOG_VOLUME_DOWN, STATE_SHOW_VOLUME_SAFETY_WARNING_DIALOG, STATE_DISMISS_VOLUME_SAFETY_WARNING_DIALOG, STATE_VOLUME_SAFETY_WARNING_DIALOG_FLAG_DISMISS, STATE_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED, STATE_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED, STATE_START_SLIDER_TRACKING, STATE_STOP_SLIDER_TRACKING, STATE_OPEN_THEME_CHANGED, STATE_DISMISS_VOLUME_PANEL_COMPLETED, STATE_UPDATE_PANEL_HEIGHT, STATE_EXPAND_STATE_CHANGED, STATE_BACKGROUND_ANIMATION_FINISHED, STATE_PANEL_ANIMATION_FINISHED, STATE_COVER_STATE_CHANGED, STATE_CONFIGURATION_CHANGED, STATE_CUSTOM, STATE_SHOW_SUB_DISPLAY_VOLUME_PANEL, STATE_ARROW_LEFT_CLICKED, STATE_ARROW_RIGHT_CLICKED, STATE_FOLDER_STATE_CHANGED, STATE_CAPTION_CHANGED, STATE_CAPTION_COMPONENT_CHANGED, STATE_DUAL_PLAY_MODE_CHANGED, STATE_ORIENTATION_CHANGED, STATE_STATUS_MESSAGE_CLICKED, STATE_SETTINGS_BUTTON_CLICKED, STATE_SEEKBAR_START_PROGRESS, STATE_SEEKBAR_TOUCH_DOWN, STATE_SEEKBAR_TOUCH_UP, STATE_SET_VOLUME_STATE, STATE_SHOW_VOLUME_CSD_100_WARNING_DIALOG, STATE_DISMISS_VOLUME_CSD_100_WARNING_DIALOG, STATE_VOLUME_CSD_100_WARNING_DIALOG_FLAG_DISMISS, STATE_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED, STATE_KEY_EVENT, STATE_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME, STATE_STATUS_LE_BROADCASTING_MESSAGE_CLICKED, STATE_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED};
        }

        static {
            StateType[] stateTypeArr$values = $values();
            $VALUES = stateTypeArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(stateTypeArr$values);
        }

        private StateType(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static StateType valueOf(String str) {
            return (StateType) Enum.valueOf(StateType.class, str);
        }

        public static StateType[] values() {
            return (StateType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class StringStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ StringStateKey[] $VALUES;
        public static final StringStateKey PIN_APP_NAME = new StringStateKey("PIN_APP_NAME", 0, "pinAppName");
        public static final StringStateKey PIN_DEVICE_NAME = new StringStateKey("PIN_DEVICE_NAME", 1, "pinDeviceName");
        private final String fieldName;

        private static final /* synthetic */ StringStateKey[] $values() {
            return new StringStateKey[]{PIN_APP_NAME, PIN_DEVICE_NAME};
        }

        static {
            StringStateKey[] stringStateKeyArr$values = $values();
            $VALUES = stringStateKeyArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(stringStateKeyArr$values);
        }

        private StringStateKey(String str, int i, String str2) {
            this.fieldName = str2;
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

        public final String getFieldName() {
            return this.fieldName;
        }
    }

    public /* synthetic */ VolumePanelState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public final int getActiveStream() {
        return getIntegerValue(IntegerStateKey.ACTIVE_STREAM);
    }

    public final int getCoverType() {
        return getIntegerValue(IntegerStateKey.COVER_TYPE);
    }

    public final Object getCustomState() {
        return this.customState;
    }

    public final int getCutoutHeight() {
        return getIntegerValue(IntegerStateKey.CUTOUT_HEIGHT);
    }

    public final int getEarProtectLevel() {
        return getIntegerValue(IntegerStateKey.EAR_PROTECT_LEVEL);
    }

    public final int getIconCurrentState() {
        return getIntegerValue(IntegerStateKey.ICON_CURRENT_STATE);
    }

    public final int getIconTargetState() {
        return getIntegerValue(IntegerStateKey.ICON_TARGET_STATE);
    }

    public final int getIntegerValue(IntegerStateKey integerStateKey) {
        Integer num = this.integerState.get(integerStateKey);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public final long getLongValue(LongStateKey longStateKey) {
        Long l = this.longState.get(longStateKey);
        if (l != null) {
            return l.longValue();
        }
        return -1L;
    }

    public final int getMusicFineVolume() {
        return getIntegerValue(IntegerStateKey.MUSIC_FINE_VOLUME);
    }

    public final String getPinAppName() {
        return getStringValue(StringStateKey.PIN_APP_NAME);
    }

    public final int getPinDevice() {
        return getIntegerValue(IntegerStateKey.PIN_DEVICE);
    }

    public final String getPinDeviceName() {
        return getStringValue(StringStateKey.PIN_DEVICE_NAME);
    }

    public final int getRingerModeInternal() {
        return getIntegerValue(IntegerStateKey.RINGER_MODE_INTERNAL);
    }

    public final StateType getStateType() {
        return this.stateType;
    }

    public final int getStream() {
        return getIntegerValue(IntegerStateKey.STREAM);
    }

    public final String getStringValue(StringStateKey stringStateKey) {
        String str = this.stringState.get(stringStateKey);
        return str == null ? "" : str;
    }

    public final long getSystemTimeNow() {
        return getLongValue(LongStateKey.SYSTEM_TIME_NOW);
    }

    public final int getTimeOut() {
        return getIntegerValue(IntegerStateKey.TIME_OUT);
    }

    public final int getTimeOutControls() {
        return getIntegerValue(IntegerStateKey.TIME_OUT_CONTROLS);
    }

    public final int getTimeOutControlsText() {
        return getIntegerValue(IntegerStateKey.TIME_OUT_CONTROLS_TEXT);
    }

    public final int getVolumeDirection() {
        return getIntegerValue(IntegerStateKey.VOLUME_DIRECTION);
    }

    public final List<VolumePanelRow> getVolumeRowList() {
        return this.volumeRowList;
    }

    public final boolean isAllSoundOff() {
        return isEnabled(BooleanStateKey.ALL_SOUND_OFF);
    }

    public final boolean isAnimating() {
        return isEnabled(BooleanStateKey.ANIMATING);
    }

    public final boolean isAodVolumePanel() {
        return isEnabled(BooleanStateKey.IS_AOD_VOLUME_PANEL);
    }

    public final boolean isBtScoOn() {
        return isEnabled(BooleanStateKey.IS_BT_SCO_ON);
    }

    public final boolean isCaptionComponentEnabled() {
        return isEnabled(BooleanStateKey.IS_CAPTION_COMPONENT_ENABLED);
    }

    public final boolean isCaptionEnabled() {
        return isEnabled(BooleanStateKey.IS_CAPTION_ENABLED);
    }

    public final boolean isConfigurationChanged() {
        return isEnabled(BooleanStateKey.CONFIGURATION_CHANGED);
    }

    public final boolean isCoverClosed() {
        return isEnabled(BooleanStateKey.IS_COVER_CLOSED);
    }

    public final boolean isDlnaEnabled() {
        return isEnabled(BooleanStateKey.DLNA_ENABLED);
    }

    public final boolean isDualAudio() {
        return isEnabled(BooleanStateKey.IS_DUAL_AUDIO);
    }

    public final boolean isEnabled(BooleanStateKey booleanStateKey) {
        Boolean bool = this.booleanState.get(booleanStateKey);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public final boolean isExpanded() {
        return isEnabled(BooleanStateKey.EXPANDED);
    }

    public final boolean isFolded() {
        return isEnabled(BooleanStateKey.FOLDER_STATE);
    }

    public final boolean isFromKey() {
        return isEnabled(BooleanStateKey.IS_FROM_KEY);
    }

    public final boolean isHasVibrator() {
        return isEnabled(BooleanStateKey.HAS_VIBRATOR);
    }

    public final boolean isKeyDown() {
        return isEnabled(BooleanStateKey.IS_KEY_DOWN);
    }

    public final boolean isLeBroadcasting() {
        return isEnabled(BooleanStateKey.IS_LE_BROADCASTING);
    }

    public final boolean isLockscreen() {
        return isEnabled(BooleanStateKey.IS_LOCKSCREEN);
    }

    public final boolean isMediaDefaultEnabled() {
        return isEnabled(BooleanStateKey.MEDIA_DEFAULT_ENABLED);
    }

    public final boolean isMediaDefaultOptionHide() {
        return isEnabled(BooleanStateKey.IS_MEDIA_DEFAULT_OPTION_HIDE);
    }

    public final boolean isMultiSoundBt() {
        return isEnabled(BooleanStateKey.IS_MULTI_SOUND_BT);
    }

    public final boolean isOpenThemeChanged() {
        return isEnabled(BooleanStateKey.OPEN_THEME_CHANGED);
    }

    public final boolean isPendingState() {
        return isEnabled(BooleanStateKey.PENDING_STATE);
    }

    public final boolean isQpVolumeBarEnabled() {
        return isEnabled(BooleanStateKey.QP_VOLUMEBAR_EANBLED);
    }

    public final boolean isRemoteMic() {
        return isEnabled(BooleanStateKey.REMOTE_MIC);
    }

    public final boolean isSafeMediaDeviceOn() {
        return isEnabled(BooleanStateKey.SAFE_MEDIA_DEVICE_ON);
    }

    public final boolean isSafeMediaPinDeviceOn() {
        return isEnabled(BooleanStateKey.SAFE_MEDIA_PIN_DEVICE_ON);
    }

    public final boolean isSetupWizardComplete() {
        return isEnabled(BooleanStateKey.SETUP_WIZARD_COMPLETE);
    }

    public final boolean isShowA11yStream() {
        return isEnabled(BooleanStateKey.SHOW_A11Y_STREAM);
    }

    public final boolean isShowing() {
        return isEnabled(BooleanStateKey.SHOWING);
    }

    public final boolean isShowingSubDisplayVolumePanel() {
        return isEnabled(BooleanStateKey.SHOWING_SUB_DISPLAY_VOLUME_PANEL);
    }

    public final boolean isShowingVolumeCsd100WarningDialog() {
        return isEnabled(BooleanStateKey.SHOWING_VOLUME_CSD_100_WARNING_DIALOG);
    }

    public final boolean isShowingVolumeLimiterDialog() {
        return isEnabled(BooleanStateKey.SHOWING_VOLUME_LIMITER_DIALOG);
    }

    public final boolean isShowingVolumeSafetyWarningDialog() {
        return isEnabled(BooleanStateKey.SHOWING_VOLUME_SAFETY_WARNING_DIALOG);
    }

    public final boolean isSupportBudsTogether() {
        return isEnabled(BooleanStateKey.VOLUME_BUDS_TOGETHER);
    }

    public final boolean isSupportTvVolumeControl() {
        return isEnabled(BooleanStateKey.SUPPORT_TV_VOLUME_CONTROL);
    }

    public final boolean isTracking() {
        return isEnabled(BooleanStateKey.TRACKING);
    }

    public final boolean isVibrating() {
        return isEnabled(BooleanStateKey.IS_VIBRATING);
    }

    public final boolean isVoiceCapable() {
        return isEnabled(BooleanStateKey.VOICE_CAPABLE);
    }

    public final boolean isWithAnimation() {
        return isEnabled(BooleanStateKey.WITH_ANIMATION);
    }

    public final boolean isZenMode() {
        return isEnabled(BooleanStateKey.ZEN_MODE);
    }

    public final void setVolumeRowList(List<VolumePanelRow> list) {
        this.volumeRowList = list;
    }

    public String toString() throws IOException {
        int activeStream = getActiveStream();
        VolumePanelStateExt volumePanelStateExt = VolumePanelStateExt.INSTANCE;
        VolumePanelRow volumePanelRowFindRow = volumePanelStateExt.findRow(this, getActiveStream());
        Integer numValueOf = volumePanelRowFindRow != null ? Integer.valueOf(volumePanelRowFindRow.getRealLevel()) : null;
        int stream = getStream();
        VolumePanelRow volumePanelRowFindRow2 = volumePanelStateExt.findRow(this, getStream());
        Integer numValueOf2 = volumePanelRowFindRow2 != null ? Integer.valueOf(volumePanelRowFindRow2.getRealLevel()) : null;
        List[] listArr = new List[4];
        HashMap<BooleanStateKey, Boolean> map = this.booleanState;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<BooleanStateKey, Boolean> entry : map.entrySet()) {
            if (entry.getValue().booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            arrayList.add(((BooleanStateKey) entry2.getKey()).getFieldName() + " : " + entry2.getValue());
        }
        listArr[0] = arrayList;
        HashMap<IntegerStateKey, Integer> map2 = this.integerState;
        ArrayList arrayList2 = new ArrayList(map2.size());
        for (Map.Entry<IntegerStateKey, Integer> entry3 : map2.entrySet()) {
            arrayList2.add(entry3.getKey().getFieldName() + " : " + entry3.getValue());
        }
        listArr[1] = arrayList2;
        HashMap<LongStateKey, Long> map3 = this.longState;
        ArrayList arrayList3 = new ArrayList(map3.size());
        for (Map.Entry<LongStateKey, Long> entry4 : map3.entrySet()) {
            arrayList3.add(entry4.getKey().getFieldName() + " : " + entry4.getValue());
        }
        listArr[2] = arrayList3;
        HashMap<StringStateKey, String> map4 = this.stringState;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry<StringStateKey, String> entry5 : map4.entrySet()) {
            String value = entry5.getValue();
            if (value != null && value.length() != 0) {
                linkedHashMap2.put(entry5.getKey(), entry5.getValue());
            }
        }
        ArrayList arrayList4 = new ArrayList(linkedHashMap2.size());
        for (Map.Entry entry6 : linkedHashMap2.entrySet()) {
            arrayList4.add(((StringStateKey) entry6.getKey()).getFieldName() + " : " + entry6.getValue());
        }
        listArr[3] = arrayList4;
        return "activeStream=" + activeStream + "(vol=" + numValueOf + "), stream=" + stream + "(vol=" + numValueOf2 + ") } " + CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt__IterablesKt.flatten(Arrays.asList(listArr)), null, null, null, null, 63);
    }

    public final class Builder {
        public static final int $stable = 8;
        private VolumePanelState secVolumeState;

        public Builder() {
            this.secVolumeState = new VolumePanelState(null);
        }

        public final Builder activeStream(int i) {
            return setIntegerValue(IntegerStateKey.ACTIVE_STREAM, i);
        }

        public final VolumePanelState build() {
            return this.secVolumeState;
        }

        public final Builder coverType(int i) {
            return setIntegerValue(IntegerStateKey.COVER_TYPE, i);
        }

        public final Builder cutoutHeight(int i) {
            return setIntegerValue(IntegerStateKey.CUTOUT_HEIGHT, i);
        }

        public final Builder earProtectLevel(int i) {
            return setIntegerValue(IntegerStateKey.EAR_PROTECT_LEVEL, i);
        }

        public final Builder iconCurrentState(int i) {
            return setIntegerValue(IntegerStateKey.ICON_CURRENT_STATE, i);
        }

        public final Builder iconTargetState(int i) {
            return setIntegerValue(IntegerStateKey.ICON_TARGET_STATE, i);
        }

        public final Builder isAllSoundOff(boolean z) {
            return setEnabled(BooleanStateKey.ALL_SOUND_OFF, z);
        }

        public final Builder isAnimating(boolean z) {
            return setEnabled(BooleanStateKey.ANIMATING, z);
        }

        public final Builder isAodVolumePanel(boolean z) {
            return setEnabled(BooleanStateKey.IS_AOD_VOLUME_PANEL, z);
        }

        public final Builder isBtScoOn(boolean z) {
            return setEnabled(BooleanStateKey.IS_BT_SCO_ON, z);
        }

        public final Builder isCaptionComponentEnabled(boolean z) {
            return setEnabled(BooleanStateKey.IS_CAPTION_COMPONENT_ENABLED, z);
        }

        public final Builder isCaptionEnabled(boolean z) {
            return setEnabled(BooleanStateKey.IS_CAPTION_ENABLED, z);
        }

        public final Builder isConfigurationChanged(boolean z) {
            return setEnabled(BooleanStateKey.CONFIGURATION_CHANGED, z);
        }

        public final Builder isCoverClosed(boolean z) {
            return setEnabled(BooleanStateKey.IS_COVER_CLOSED, z);
        }

        public final Builder isDlnaEnabled(boolean z) {
            return setEnabled(BooleanStateKey.DLNA_ENABLED, z);
        }

        public final Builder isDualAudio(boolean z) {
            return setEnabled(BooleanStateKey.IS_DUAL_AUDIO, z);
        }

        public final Builder isExpanded(boolean z) {
            return setEnabled(BooleanStateKey.EXPANDED, z);
        }

        public final Builder isFolded(boolean z) {
            return setEnabled(BooleanStateKey.FOLDER_STATE, z);
        }

        public final Builder isFromKey(boolean z) {
            return setEnabled(BooleanStateKey.IS_FROM_KEY, z);
        }

        public final Builder isHasVibrator(boolean z) {
            return setEnabled(BooleanStateKey.HAS_VIBRATOR, z);
        }

        public final Builder isKeyDown(boolean z) {
            return setEnabled(BooleanStateKey.IS_KEY_DOWN, z);
        }

        public final Builder isLeBroadcasting(boolean z) {
            return setEnabled(BooleanStateKey.IS_LE_BROADCASTING, z);
        }

        public final Builder isLockscreen(boolean z) {
            return setEnabled(BooleanStateKey.IS_LOCKSCREEN, z);
        }

        public final Builder isMediaDefaultEnabled(boolean z) {
            return setEnabled(BooleanStateKey.MEDIA_DEFAULT_ENABLED, z);
        }

        public final Builder isMediaDefaultOptionHide(boolean z) {
            return setEnabled(BooleanStateKey.IS_MEDIA_DEFAULT_OPTION_HIDE, z);
        }

        public final Builder isMultiSoundBt(boolean z) {
            return setEnabled(BooleanStateKey.IS_MULTI_SOUND_BT, z);
        }

        public final Builder isOpenThemeChanged(boolean z) {
            return setEnabled(BooleanStateKey.OPEN_THEME_CHANGED, z);
        }

        public final Builder isPendingState(boolean z) {
            return setEnabled(BooleanStateKey.PENDING_STATE, z);
        }

        public final Builder isQpVolumeBarEnabled(boolean z) {
            return setEnabled(BooleanStateKey.QP_VOLUMEBAR_EANBLED, z);
        }

        public final Builder isRemoteMic(boolean z) {
            return setEnabled(BooleanStateKey.REMOTE_MIC, z);
        }

        public final Builder isSafeMediaDeviceOn(boolean z) {
            return setEnabled(BooleanStateKey.SAFE_MEDIA_DEVICE_ON, z);
        }

        public final Builder isSafeMediaPinDeviceOn(boolean z) {
            return setEnabled(BooleanStateKey.SAFE_MEDIA_PIN_DEVICE_ON, z);
        }

        public final Builder isSetupWizardComplete(boolean z) {
            return setEnabled(BooleanStateKey.SETUP_WIZARD_COMPLETE, z);
        }

        public final Builder isShowA11yStream(boolean z) {
            return setEnabled(BooleanStateKey.SHOW_A11Y_STREAM, z);
        }

        public final Builder isShowing(boolean z) {
            return setEnabled(BooleanStateKey.SHOWING, z);
        }

        public final Builder isShowingSubDisplayVolumePanel(boolean z) {
            return setEnabled(BooleanStateKey.SHOWING_SUB_DISPLAY_VOLUME_PANEL, z);
        }

        public final Builder isShowingVolumeCsd100WarningDialog(boolean z) {
            return setEnabled(BooleanStateKey.SHOWING_VOLUME_CSD_100_WARNING_DIALOG, z);
        }

        public final Builder isShowingVolumeLimiterDialog(boolean z) {
            return setEnabled(BooleanStateKey.SHOWING_VOLUME_LIMITER_DIALOG, z);
        }

        public final Builder isShowingVolumeSafetyWarningDialog(boolean z) {
            return setEnabled(BooleanStateKey.SHOWING_VOLUME_SAFETY_WARNING_DIALOG, z);
        }

        public final Builder isSupportBudsTogether(boolean z) {
            return setEnabled(BooleanStateKey.VOLUME_BUDS_TOGETHER, z);
        }

        public final Builder isSupportTvVolumeControl(boolean z) {
            return setEnabled(BooleanStateKey.SUPPORT_TV_VOLUME_CONTROL, z);
        }

        public final Builder isTracking(boolean z) {
            return setEnabled(BooleanStateKey.TRACKING, z);
        }

        public final Builder isVibrating(boolean z) {
            return setEnabled(BooleanStateKey.IS_VIBRATING, z);
        }

        public final Builder isVoiceCapable(boolean z) {
            return setEnabled(BooleanStateKey.VOICE_CAPABLE, z);
        }

        public final Builder isWithAnimation(boolean z) {
            return setEnabled(BooleanStateKey.WITH_ANIMATION, z);
        }

        public final Builder isZenMode(boolean z) {
            return setEnabled(BooleanStateKey.ZEN_MODE, z);
        }

        public final Builder musicFineVolume(int i) {
            return setIntegerValue(IntegerStateKey.MUSIC_FINE_VOLUME, i);
        }

        public final Builder pinAppName(String str) {
            return setStringValue(StringStateKey.PIN_APP_NAME, str);
        }

        public final Builder pinDevice(int i) {
            return setIntegerValue(IntegerStateKey.PIN_DEVICE, i);
        }

        public final Builder pinDeviceName(String str) {
            return setStringValue(StringStateKey.PIN_DEVICE_NAME, str);
        }

        public final Builder ringerModeInternal(int i) {
            return setIntegerValue(IntegerStateKey.RINGER_MODE_INTERNAL, i);
        }

        public final Builder setCustomState(Object obj) {
            this.secVolumeState.customState = obj;
            return this;
        }

        public final Builder setEnabled(BooleanStateKey booleanStateKey, boolean z) {
            this.secVolumeState.booleanState.put(booleanStateKey, Boolean.valueOf(z));
            return this;
        }

        public final Builder setIntegerValue(IntegerStateKey integerStateKey, int i) {
            this.secVolumeState.integerState.put(integerStateKey, Integer.valueOf(i));
            return this;
        }

        public final Builder setLongValue(LongStateKey longStateKey, long j) {
            this.secVolumeState.longState.put(longStateKey, Long.valueOf(j));
            return this;
        }

        public final Builder setStateType(StateType stateType) {
            this.secVolumeState.stateType = stateType;
            return this;
        }

        public final Builder setStringValue(StringStateKey stringStateKey, String str) {
            this.secVolumeState.stringState.put(stringStateKey, str);
            return this;
        }

        public final Builder setVolumeRowList(List<VolumePanelRow> list) {
            this.secVolumeState.setVolumeRowList(list);
            return this;
        }

        public final Builder stream(int i) {
            return setIntegerValue(IntegerStateKey.STREAM, i);
        }

        public final Builder systemTimeNow(long j) {
            return setLongValue(LongStateKey.SYSTEM_TIME_NOW, j);
        }

        public final Builder timeOut(int i) {
            return setIntegerValue(IntegerStateKey.TIME_OUT, i);
        }

        public final Builder timeOutControls(int i) {
            return setIntegerValue(IntegerStateKey.TIME_OUT_CONTROLS, i);
        }

        public final Builder timeOutControlsText(int i) {
            return setIntegerValue(IntegerStateKey.TIME_OUT_CONTROLS_TEXT, i);
        }

        public final Builder volumeDirection(int i) {
            return setIntegerValue(IntegerStateKey.VOLUME_DIRECTION, i);
        }

        public Builder(VolumePanelState volumePanelState) {
            VolumePanelState volumePanelState2 = new VolumePanelState(null);
            volumePanelState2.setVolumeRowList(volumePanelState.getVolumeRowList());
            volumePanelState2.stateType = volumePanelState.getStateType();
            volumePanelState2.booleanState = volumePanelState.booleanState;
            volumePanelState2.integerState = volumePanelState.integerState;
            volumePanelState2.longState = volumePanelState.longState;
            volumePanelState2.stringState = volumePanelState.stringState;
            volumePanelState2.customState = volumePanelState.getCustomState();
            this.secVolumeState = volumePanelState2;
        }

        public Builder(StateType stateType) {
            VolumePanelState volumePanelState = new VolumePanelState(null);
            volumePanelState.stateType = stateType;
            this.secVolumeState = volumePanelState;
        }
    }

    private VolumePanelState() {
        this.volumeRowList = new ArrayList();
        this.integerState = new HashMap<>();
        this.booleanState = new HashMap<>();
        this.longState = new HashMap<>();
        this.stringState = new HashMap<>();
        this.stateType = StateType.STATE_IDLE;
    }

    public static /* synthetic */ void getActiveStream$annotations() {
    }

    public static /* synthetic */ void getCoverType$annotations() {
    }

    public static /* synthetic */ void getCustomState$annotations() {
    }

    public static /* synthetic */ void getRingerModeInternal$annotations() {
    }

    public static /* synthetic */ void getStateType$annotations() {
    }

    public static /* synthetic */ void getStream$annotations() {
    }

    public static /* synthetic */ void getVolumeRowList$annotations() {
    }
}
