package com.samsung.systemui.splugins.volume;

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
public final class VolumePanelAction {
    public static final int $stable = 8;
    private ActionType actionType;
    private HashMap<BooleanStateKey, Boolean> boolMap;
    private Object customAction;
    private List<Integer> disabledStreamList;
    private List<Integer> enabledStreamList;
    private List<Integer> importantStreamList;
    private HashMap<IntegerStateKey, Integer> intMap;
    private HashMap<LongStateKey, Long> longMap;
    private HashMap<StringStateKey, String> stringMap;
    private List<Integer> unImportantStreamList;
    private VolumeState volumeState;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class ActionType {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ ActionType[] $VALUES;
        public static final ActionType ACTION_NONE = new ActionType("ACTION_NONE", 0);
        public static final ActionType ACTION_PANEL_SHOW = new ActionType("ACTION_PANEL_SHOW", 1);
        public static final ActionType ACTION_STATE_CHANGED = new ActionType("ACTION_STATE_CHANGED", 2);
        public static final ActionType ACTION_ANIMATION_START = new ActionType("ACTION_ANIMATION_START", 3);
        public static final ActionType ACTION_ANIMATION_FINISHED = new ActionType("ACTION_ANIMATION_FINISHED", 4);
        public static final ActionType ACTION_TIME_OUT = new ActionType("ACTION_TIME_OUT", 5);
        public static final ActionType ACTION_INIT = new ActionType("ACTION_INIT", 6);
        public static final ActionType ACTION_TOUCH_OUTSIDE = new ActionType("ACTION_TOUCH_OUTSIDE", 7);
        public static final ActionType ACTION_UPDATE_PROGRESS_BAR = new ActionType("ACTION_UPDATE_PROGRESS_BAR", 8);
        public static final ActionType ACTION_START_SLIDER_TRACKING = new ActionType("ACTION_START_SLIDER_TRACKING", 9);
        public static final ActionType ACTION_STOP_SLIDER_TRACKING = new ActionType("ACTION_STOP_SLIDER_TRACKING", 10);
        public static final ActionType ACTION_EXPAND_BUTTON_CLICKED = new ActionType("ACTION_EXPAND_BUTTON_CLICKED", 11);
        public static final ActionType ACTION_VOLUME_ICON_CLICKED = new ActionType("ACTION_VOLUME_ICON_CLICKED", 12);
        public static final ActionType ACTION_CHECK_IF_NEED_TO_SET_PROGRESS = new ActionType("ACTION_CHECK_IF_NEED_TO_SET_PROGRESS", 13);
        public static final ActionType ACTION_MEDIA_VOLUME_DEFAULT_CHANGED = new ActionType("ACTION_MEDIA_VOLUME_DEFAULT_CHANGED", 14);
        public static final ActionType ACTION_TOUCH_PANEL = new ActionType("ACTION_TOUCH_PANEL", 15);
        public static final ActionType ACTION_SCREEN_OFF = new ActionType("ACTION_SCREEN_OFF", 16);
        public static final ActionType ACTION_DISMISS_REQUESTED = new ActionType("ACTION_DISMISS_REQUESTED", 17);
        public static final ActionType ACTION_ALL_SOUND_OFF_CHANGED = new ActionType("ACTION_ALL_SOUND_OFF_CHANGED", 18);
        public static final ActionType ACTION_SEND_ACCESSIBILITY_EVENT = new ActionType("ACTION_SEND_ACCESSIBILITY_EVENT", 19);
        public static final ActionType ACTION_ACCESSIBILITY_MODE_CHANGED = new ActionType("ACTION_ACCESSIBILITY_MODE_CHANGED", 20);
        public static final ActionType ACTION_PLAY_SOUND_ON = new ActionType("ACTION_PLAY_SOUND_ON", 21);
        public static final ActionType ACTION_CONFIGURATION_CHANGED = new ActionType("ACTION_CONFIGURATION_CHANGED", 22);
        public static final ActionType ACTION_COVER_STATE_CHAGNED = new ActionType("ACTION_COVER_STATE_CHAGNED", 23);
        public static final ActionType ACTION_MIRROR_LINK_ON = new ActionType("ACTION_MIRROR_LINK_ON", 24);
        public static final ActionType ACTION_SMART_VIEW_SEEKBAR_TOUCHED = new ActionType("ACTION_SMART_VIEW_SEEKBAR_TOUCHED", 25);
        public static final ActionType ACTION_SHOW_VOLUME_LIMITER_DIALOG = new ActionType("ACTION_SHOW_VOLUME_LIMITER_DIALOG", 26);
        public static final ActionType ACTION_DISMISS_VOLUME_LIMITER_DIALOG = new ActionType("ACTION_DISMISS_VOLUME_LIMITER_DIALOG", 27);
        public static final ActionType ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED = new ActionType("ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED", 28);
        public static final ActionType ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED = new ActionType("ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED", 29);
        public static final ActionType ACTION_VOLUME_LIMITER_DIALOG_VOLUME_DOWN = new ActionType("ACTION_VOLUME_LIMITER_DIALOG_VOLUME_DOWN", 30);
        public static final ActionType ACTION_DISMISS_VOLUME_PANEL = new ActionType("ACTION_DISMISS_VOLUME_PANEL", 31);
        public static final ActionType ACTION_SHOW_VOLUME_SAFETY_WARNING_DIALOG = new ActionType("ACTION_SHOW_VOLUME_SAFETY_WARNING_DIALOG", 32);
        public static final ActionType ACTION_DISMISS_VOLUME_SAFETY_WARNING_DIALOG = new ActionType("ACTION_DISMISS_VOLUME_SAFETY_WARNING_DIALOG", 33);
        public static final ActionType ACTION_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED = new ActionType("ACTION_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED", 34);
        public static final ActionType ACTION_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED = new ActionType("ACTION_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED", 35);
        public static final ActionType ACTION_OPEN_THEME_CHANGED = new ActionType("ACTION_OPEN_THEME_CHANGED", 36);
        public static final ActionType ACTION_PANEL_LAYOUT_CHANGED = new ActionType("ACTION_PANEL_LAYOUT_CHANGED", 37);
        public static final ActionType ACTION_BACKGROUND_ANIMATION_FINISHED = new ActionType("ACTION_BACKGROUND_ANIMATION_FINISHED", 38);
        public static final ActionType ACTION_SWIPE_PANEL = new ActionType("ACTION_SWIPE_PANEL", 39);
        public static final ActionType ACTION_PANEL_ANIMATION_FINISHED = new ActionType("ACTION_PANEL_ANIMATION_FINISHED", 40);
        public static final ActionType ACTION_CUSTOM = new ActionType("ACTION_CUSTOM", 41);
        public static final ActionType ACTION_FOLDER_STATE_CHANGED = new ActionType("ACTION_FOLDER_STATE_CHANGED", 42);
        public static final ActionType ACTION_DISMISS_SUB_DISPLAY_VOLUME_PANEL = new ActionType("ACTION_DISMISS_SUB_DISPLAY_VOLUME_PANEL", 43);
        public static final ActionType ACTION_ARROW_LEFT_CLICKED = new ActionType("ACTION_ARROW_LEFT_CLICKED", 44);
        public static final ActionType ACTION_ARROW_RIGHT_CLICKED = new ActionType("ACTION_ARROW_RIGHT_CLICKED", 45);
        public static final ActionType ACTION_USER_SWITCHED = new ActionType("ACTION_USER_SWITCHED", 46);
        public static final ActionType ACTION_CAPTION_COMPONENT_CHANGED = new ActionType("ACTION_CAPTION_COMPONENT_CHANGED", 47);
        public static final ActionType ACTION_CAPTION_CHANGED = new ActionType("ACTION_CAPTION_CHANGED", 48);
        public static final ActionType ACTION_IDLE = new ActionType("ACTION_IDLE", 49);
        public static final ActionType ACTION_DUAL_PLAY_MODE_CHANGED = new ActionType("ACTION_DUAL_PLAY_MODE_CHANGED", 50);
        public static final ActionType ACTION_STATUS_MESSAGE_CLICKED = new ActionType("ACTION_STATUS_MESSAGE_CLICKED", 51);
        public static final ActionType ACTION_SETTINGS_BUTTON_CLICKED = new ActionType("ACTION_SETTINGS_BUTTON_CLICKED", 52);
        public static final ActionType ACTION_SEEKBAR_START_PROGRESS = new ActionType("ACTION_SEEKBAR_START_PROGRESS", 53);
        public static final ActionType ACTION_SEEKBAR_TOUCH_DOWN = new ActionType("ACTION_SEEKBAR_TOUCH_DOWN", 54);
        public static final ActionType ACTION_SEEKBAR_TOUCH_UP = new ActionType("ACTION_SEEKBAR_TOUCH_UP", 55);
        public static final ActionType ACTION_SETUP_WIZARD_COMPLETE = new ActionType("ACTION_SETUP_WIZARD_COMPLETE", 56);
        public static final ActionType ACTION_VOLUME_ICON_ANIMATION_FINISHED = new ActionType("ACTION_VOLUME_ICON_ANIMATION_FINISHED", 57);
        public static final ActionType ACTION_SWIPE_COLLAPSED = new ActionType("ACTION_SWIPE_COLLAPSED", 58);
        public static final ActionType ACTION_SHOW_VOLUME_CSD_100_WARNING_DIALOG = new ActionType("ACTION_SHOW_VOLUME_CSD_100_WARNING_DIALOG", 59);
        public static final ActionType ACTION_DISMISS_VOLUME_CSD_100_WARNING_DIALOG = new ActionType("ACTION_DISMISS_VOLUME_CSD_100_WARNING_DIALOG", 60);
        public static final ActionType ACTION_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED = new ActionType("ACTION_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED", 61);
        public static final ActionType ACTION_KEY_EVENT = new ActionType("ACTION_KEY_EVENT", 62);
        public static final ActionType ACTION_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME = new ActionType("ACTION_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME", 63);
        public static final ActionType ACTION_VOLUME_CSD_100_WARNING_DIALOG_TIMEOUT = new ActionType("ACTION_VOLUME_CSD_100_WARNING_DIALOG_TIMEOUT", 64);
        public static final ActionType ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED = new ActionType("ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED", 65);
        public static final ActionType ACTION_HEADSET_CONNECTION = new ActionType("ACTION_HEADSET_CONNECTION", 66);
        public static final ActionType ACTION_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED = new ActionType("ACTION_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED", 67);

        private static final /* synthetic */ ActionType[] $values() {
            return new ActionType[]{ACTION_NONE, ACTION_PANEL_SHOW, ACTION_STATE_CHANGED, ACTION_ANIMATION_START, ACTION_ANIMATION_FINISHED, ACTION_TIME_OUT, ACTION_INIT, ACTION_TOUCH_OUTSIDE, ACTION_UPDATE_PROGRESS_BAR, ACTION_START_SLIDER_TRACKING, ACTION_STOP_SLIDER_TRACKING, ACTION_EXPAND_BUTTON_CLICKED, ACTION_VOLUME_ICON_CLICKED, ACTION_CHECK_IF_NEED_TO_SET_PROGRESS, ACTION_MEDIA_VOLUME_DEFAULT_CHANGED, ACTION_TOUCH_PANEL, ACTION_SCREEN_OFF, ACTION_DISMISS_REQUESTED, ACTION_ALL_SOUND_OFF_CHANGED, ACTION_SEND_ACCESSIBILITY_EVENT, ACTION_ACCESSIBILITY_MODE_CHANGED, ACTION_PLAY_SOUND_ON, ACTION_CONFIGURATION_CHANGED, ACTION_COVER_STATE_CHAGNED, ACTION_MIRROR_LINK_ON, ACTION_SMART_VIEW_SEEKBAR_TOUCHED, ACTION_SHOW_VOLUME_LIMITER_DIALOG, ACTION_DISMISS_VOLUME_LIMITER_DIALOG, ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED, ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED, ACTION_VOLUME_LIMITER_DIALOG_VOLUME_DOWN, ACTION_DISMISS_VOLUME_PANEL, ACTION_SHOW_VOLUME_SAFETY_WARNING_DIALOG, ACTION_DISMISS_VOLUME_SAFETY_WARNING_DIALOG, ACTION_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED, ACTION_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED, ACTION_OPEN_THEME_CHANGED, ACTION_PANEL_LAYOUT_CHANGED, ACTION_BACKGROUND_ANIMATION_FINISHED, ACTION_SWIPE_PANEL, ACTION_PANEL_ANIMATION_FINISHED, ACTION_CUSTOM, ACTION_FOLDER_STATE_CHANGED, ACTION_DISMISS_SUB_DISPLAY_VOLUME_PANEL, ACTION_ARROW_LEFT_CLICKED, ACTION_ARROW_RIGHT_CLICKED, ACTION_USER_SWITCHED, ACTION_CAPTION_COMPONENT_CHANGED, ACTION_CAPTION_CHANGED, ACTION_IDLE, ACTION_DUAL_PLAY_MODE_CHANGED, ACTION_STATUS_MESSAGE_CLICKED, ACTION_SETTINGS_BUTTON_CLICKED, ACTION_SEEKBAR_START_PROGRESS, ACTION_SEEKBAR_TOUCH_DOWN, ACTION_SEEKBAR_TOUCH_UP, ACTION_SETUP_WIZARD_COMPLETE, ACTION_VOLUME_ICON_ANIMATION_FINISHED, ACTION_SWIPE_COLLAPSED, ACTION_SHOW_VOLUME_CSD_100_WARNING_DIALOG, ACTION_DISMISS_VOLUME_CSD_100_WARNING_DIALOG, ACTION_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED, ACTION_KEY_EVENT, ACTION_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME, ACTION_VOLUME_CSD_100_WARNING_DIALOG_TIMEOUT, ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED, ACTION_HEADSET_CONNECTION, ACTION_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED};
        }

        static {
            ActionType[] actionTypeArr$values = $values();
            $VALUES = actionTypeArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(actionTypeArr$values);
        }

        private ActionType(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static ActionType valueOf(String str) {
            return (ActionType) Enum.valueOf(ActionType.class, str);
        }

        public static ActionType[] values() {
            return (ActionType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class BooleanStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ BooleanStateKey[] $VALUES;
        private final String fieldName;
        public static final BooleanStateKey MEDIA_DEFAULT = new BooleanStateKey("MEDIA_DEFAULT", 0, "isMediaDefault");
        public static final BooleanStateKey SAFE_MEDIA_DEVICE_ON = new BooleanStateKey("SAFE_MEDIA_DEVICE_ON", 1, "isSafeMediaDeviceOn");
        public static final BooleanStateKey SAFE_MEDIA_PIN_DEVICE_ON = new BooleanStateKey("SAFE_MEDIA_PIN_DEVICE_ON", 2, "isSafeMediaPinDeviceOn");
        public static final BooleanStateKey DUAL_PLAY_MODE = new BooleanStateKey("DUAL_PLAY_MODE", 3, "isDualPlayMode");
        public static final BooleanStateKey VOICE_CAPABLE = new BooleanStateKey("VOICE_CAPABLE", 4, "isVoiceCapble");
        public static final BooleanStateKey FROM_OUTSIDE = new BooleanStateKey("FROM_OUTSIDE", 5, "isFromOutside");
        public static final BooleanStateKey HAS_VIBRATOR = new BooleanStateKey("HAS_VIBRATOR", 6, "isHasVibrator");
        public static final BooleanStateKey ALL_SOUND_OFF = new BooleanStateKey("ALL_SOUND_OFF", 7, "isAllSoundOff");
        public static final BooleanStateKey SHOW_A11Y_STREAM = new BooleanStateKey("SHOW_A11Y_STREAM", 8, "isShowA11yStream");
        public static final BooleanStateKey IS_ORIENTATION_CHANGED = new BooleanStateKey("IS_ORIENTATION_CHANGED", 9, "isOrientationChanged");
        public static final BooleanStateKey IS_COVER_CLOSED = new BooleanStateKey("IS_COVER_CLOSED", 10, "isCoverClosed");
        public static final BooleanStateKey SUPPORT_TV_VOLUME_SYNC = new BooleanStateKey("SUPPORT_TV_VOLUME_SYNC", 11, "isSupportTvVolumeSync");
        public static final BooleanStateKey IS_ZEN_ENABLED = new BooleanStateKey("IS_ZEN_ENABLED", 12, "isZenEnabled");
        public static final BooleanStateKey IS_ZEN_PRIORITY_ONLY = new BooleanStateKey("IS_ZEN_PRIORITY_ONLY", 13, "isZenPriorityOnly");
        public static final BooleanStateKey IS_ZEN_NONE = new BooleanStateKey("IS_ZEN_NONE", 14, "isZenNone");
        public static final BooleanStateKey IS_LOCKSCREEN = new BooleanStateKey("IS_LOCKSCREEN", 15, "isLockscreen");
        public static final BooleanStateKey IS_BT_SCO_ON = new BooleanStateKey("IS_BT_SCO_ON", 16, "isBtScoOn");
        public static final BooleanStateKey FOLDER_STATE = new BooleanStateKey("FOLDER_STATE", 17, "isFolded");
        public static final BooleanStateKey IS_DENSITY_OR_FONT_CHANGED = new BooleanStateKey("IS_DENSITY_OR_FONT_CHANGED", 18, "isDensityOrFontChanged");
        public static final BooleanStateKey IS_MULTI_SOUND_BT = new BooleanStateKey("IS_MULTI_SOUND_BT", 19, "isMultiSoundBt");
        public static final BooleanStateKey IS_MEDIA_DEFAULT_OPTION_HIDE = new BooleanStateKey("IS_MEDIA_DEFAULT_OPTION_HIDE", 20, "isMediaDefaultOptionHide");
        public static final BooleanStateKey IS_CAPTION_COMPONENT_ENABLED = new BooleanStateKey("IS_CAPTION_COMPONENT_ENABLED", 21, "isCaptionComponentEnabled");
        public static final BooleanStateKey IS_CAPTION_ENABLED = new BooleanStateKey("IS_CAPTION_ENABLED", 22, "isCaptionEnabled");
        public static final BooleanStateKey IS_DISPLAY_TYPE_CHANGED = new BooleanStateKey("IS_DISPLAY_TYPE_CHANGED", 23, "isDisplayTypeChanged");
        public static final BooleanStateKey IS_FROM_KEY = new BooleanStateKey("IS_FROM_KEY", 24, "isFromKey");
        public static final BooleanStateKey SETUP_WIZARD_COMPLETE = new BooleanStateKey("SETUP_WIZARD_COMPLETE", 25, "isSetupWizardComplete");
        public static final BooleanStateKey IS_AOD_SCREEN = new BooleanStateKey("IS_AOD_SCREEN", 26, "isAodScreen");
        public static final BooleanStateKey IS_KEY_DOWN = new BooleanStateKey("IS_KEY_DOWN", 27, "isKeyDown");
        public static final BooleanStateKey IS_VIBRATING = new BooleanStateKey("IS_VIBRATING", 28, "isVibrating");
        public static final BooleanStateKey IS_HEADSET_CONNECTED = new BooleanStateKey("IS_HEADSET_CONNECTED", 29, "isHeadsetConnected");
        public static final BooleanStateKey VOLUME_SMART_VIEW_STREAM = new BooleanStateKey("VOLUME_SMART_VIEW_STREAM", 30, "isSupportSmartViewStream");
        public static final BooleanStateKey VOLUME_WARNING_POPUP_WALLET_MINI = new BooleanStateKey("VOLUME_WARNING_POPUP_WALLET_MINI", 31, "isSupportWarningPopupWalletMini");
        public static final BooleanStateKey VOLUME_WARNING_POPUP_SIDE_VIEW = new BooleanStateKey("VOLUME_WARNING_POPUP_SIDE_VIEW", 32, "isSupportWarningPopupSideView");
        public static final BooleanStateKey VOLUME_BUDS_TOGETHER = new BooleanStateKey("VOLUME_BUDS_TOGETHER", 33, "isSupportBudsTogether");
        public static final BooleanStateKey VOLUME_DUAL_AUDIO = new BooleanStateKey("VOLUME_DUAL_AUDIO", 34, "isSupportDualAudio");

        private static final /* synthetic */ BooleanStateKey[] $values() {
            return new BooleanStateKey[]{MEDIA_DEFAULT, SAFE_MEDIA_DEVICE_ON, SAFE_MEDIA_PIN_DEVICE_ON, DUAL_PLAY_MODE, VOICE_CAPABLE, FROM_OUTSIDE, HAS_VIBRATOR, ALL_SOUND_OFF, SHOW_A11Y_STREAM, IS_ORIENTATION_CHANGED, IS_COVER_CLOSED, SUPPORT_TV_VOLUME_SYNC, IS_ZEN_ENABLED, IS_ZEN_PRIORITY_ONLY, IS_ZEN_NONE, IS_LOCKSCREEN, IS_BT_SCO_ON, FOLDER_STATE, IS_DENSITY_OR_FONT_CHANGED, IS_MULTI_SOUND_BT, IS_MEDIA_DEFAULT_OPTION_HIDE, IS_CAPTION_COMPONENT_ENABLED, IS_CAPTION_ENABLED, IS_DISPLAY_TYPE_CHANGED, IS_FROM_KEY, SETUP_WIZARD_COMPLETE, IS_AOD_SCREEN, IS_KEY_DOWN, IS_VIBRATING, IS_HEADSET_CONNECTED, VOLUME_SMART_VIEW_STREAM, VOLUME_WARNING_POPUP_WALLET_MINI, VOLUME_WARNING_POPUP_SIDE_VIEW, VOLUME_BUDS_TOGETHER, VOLUME_DUAL_AUDIO};
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

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class IntegerStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ IntegerStateKey[] $VALUES;
        private final String fieldName;
        public static final IntegerStateKey PIN_DEVICE = new IntegerStateKey("PIN_DEVICE", 0, "pinDevice");
        public static final IntegerStateKey ACTIVE_STREAM = new IntegerStateKey("ACTIVE_STREAM", 1, "activeStream");
        public static final IntegerStateKey PROGRESS = new IntegerStateKey("PROGRESS", 2, "progress");
        public static final IntegerStateKey STREAM = new IntegerStateKey("STREAM", 3, "stream");
        public static final IntegerStateKey EAR_PROTECT_LEVEL = new IntegerStateKey("EAR_PROTECT_LEVEL", 4, "earProtectLevel");
        public static final IntegerStateKey TIME_OUT_CONTROLS = new IntegerStateKey("TIME_OUT_CONTROLS", 5, "timeOutControls");
        public static final IntegerStateKey TIME_OUT_CONTROLS_TEXT = new IntegerStateKey("TIME_OUT_CONTROLS_TEXT", 6, "timeOutControlsText");
        public static final IntegerStateKey COVER_TYPE = new IntegerStateKey("COVER_TYPE", 7, "coverType");
        public static final IntegerStateKey MUSIC_FINE_VOLUME = new IntegerStateKey("MUSIC_FINE_VOLUME", 8, "musicFineVolume");
        public static final IntegerStateKey FLAGS = new IntegerStateKey("FLAGS", 9, "flags");
        public static final IntegerStateKey CUTOUT_HEIGHT = new IntegerStateKey("CUTOUT_HEIGHT", 10, "cutoutHeight");
        public static final IntegerStateKey ICON_TARGET_STATE = new IntegerStateKey("ICON_TARGET_STATE", 11, "iconTargetState");
        public static final IntegerStateKey ICON_CURRENT_STATE = new IntegerStateKey("ICON_CURRENT_STATE", 12, "iconCurrentState");
        public static final IntegerStateKey VOLUME_DIRECTION = new IntegerStateKey("VOLUME_DIRECTION", 13, "volumeDirection");

        private static final /* synthetic */ IntegerStateKey[] $values() {
            return new IntegerStateKey[]{PIN_DEVICE, ACTIVE_STREAM, PROGRESS, STREAM, EAR_PROTECT_LEVEL, TIME_OUT_CONTROLS, TIME_OUT_CONTROLS_TEXT, COVER_TYPE, MUSIC_FINE_VOLUME, FLAGS, CUTOUT_HEIGHT, ICON_TARGET_STATE, ICON_CURRENT_STATE, VOLUME_DIRECTION};
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
    public final class StringStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ StringStateKey[] $VALUES;
        private final String fieldName;
        public static final StringStateKey SMART_VIEW_DEVICE_NAME = new StringStateKey("SMART_VIEW_DEVICE_NAME", 0, "smartViewDeviceName");
        public static final StringStateKey ACTIVE_BT_DEVICE_NAME = new StringStateKey("ACTIVE_BT_DEVICE_NAME", 1, "activeBtDeviceName");
        public static final StringStateKey PIN_APP_NAME = new StringStateKey("PIN_APP_NAME", 2, "pinAppName");
        public static final StringStateKey PIN_DEVICE_NAME = new StringStateKey("PIN_DEVICE_NAME", 3, "pinDeviceName");
        public static final StringStateKey BT_CALL_DEVICE_NAME = new StringStateKey("BT_CALL_DEVICE_NAME", 4, "btCallDeviceName");
        public static final StringStateKey AUDIO_SHARING_DEVICE_NAME = new StringStateKey("AUDIO_SHARING_DEVICE_NAME", 5, "audioSharingDeviceName");

        private static final /* synthetic */ StringStateKey[] $values() {
            return new StringStateKey[]{SMART_VIEW_DEVICE_NAME, ACTIVE_BT_DEVICE_NAME, PIN_APP_NAME, PIN_DEVICE_NAME, BT_CALL_DEVICE_NAME, AUDIO_SHARING_DEVICE_NAME};
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

    public /* synthetic */ VolumePanelAction(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public final ActionType getActionType() {
        return this.actionType;
    }

    public final String getActiveBtDeviceName() {
        return getStringValue(StringStateKey.ACTIVE_BT_DEVICE_NAME);
    }

    public final int getActiveStream() {
        return getIntegerValue(IntegerStateKey.ACTIVE_STREAM);
    }

    public final String getAudioSharingDeviceName() {
        return getStringValue(StringStateKey.AUDIO_SHARING_DEVICE_NAME);
    }

    public final String getBtCallDeviceName() {
        return getStringValue(StringStateKey.BT_CALL_DEVICE_NAME);
    }

    public final int getCoverType() {
        return getIntegerValue(IntegerStateKey.COVER_TYPE);
    }

    public final Object getCustomAction() {
        return this.customAction;
    }

    public final int getCutoutHeight() {
        return getIntegerValue(IntegerStateKey.CUTOUT_HEIGHT);
    }

    public final List<Integer> getDisabledStreamList() {
        return this.disabledStreamList;
    }

    public final int getEarProtectLevel() {
        return getIntegerValue(IntegerStateKey.EAR_PROTECT_LEVEL);
    }

    public final List<Integer> getEnabledStreamList() {
        return this.enabledStreamList;
    }

    public final int getFlags() {
        return getIntegerValue(IntegerStateKey.FLAGS);
    }

    public final int getIconCurrentState() {
        return getIntegerValue(IntegerStateKey.ICON_CURRENT_STATE);
    }

    public final int getIconTargetState() {
        return getIntegerValue(IntegerStateKey.ICON_TARGET_STATE);
    }

    public final List<Integer> getImportantStreamList() {
        return this.importantStreamList;
    }

    public final int getIntegerValue(IntegerStateKey integerStateKey) {
        Integer num = this.intMap.get(integerStateKey);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public final long getLongValue(LongStateKey longStateKey) {
        Long l = this.longMap.get(longStateKey);
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

    public final int getProgress() {
        return getIntegerValue(IntegerStateKey.PROGRESS);
    }

    public final String getSmartViewDeviceName() {
        return getStringValue(StringStateKey.SMART_VIEW_DEVICE_NAME);
    }

    public final int getStream() {
        return getIntegerValue(IntegerStateKey.STREAM);
    }

    public final String getStringValue(StringStateKey stringStateKey) {
        String str = this.stringMap.get(stringStateKey);
        return str == null ? "" : str;
    }

    public final long getSystemTimeNow() {
        return getLongValue(LongStateKey.SYSTEM_TIME_NOW);
    }

    public final int getTimeOutControls() {
        return getIntegerValue(IntegerStateKey.TIME_OUT_CONTROLS);
    }

    public final int getTimeOutControlsText() {
        return getIntegerValue(IntegerStateKey.TIME_OUT_CONTROLS_TEXT);
    }

    public final List<Integer> getUnImportantStreamList() {
        return this.unImportantStreamList;
    }

    public final int getVolumeDirection() {
        return getIntegerValue(IntegerStateKey.VOLUME_DIRECTION);
    }

    public final VolumeState getVolumeState() {
        return this.volumeState;
    }

    public final boolean isAllSoundOff() {
        return isEnabled(BooleanStateKey.ALL_SOUND_OFF);
    }

    public final boolean isAodScreen() {
        return isEnabled(BooleanStateKey.IS_AOD_SCREEN);
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

    public final boolean isCoverClosed() {
        return isEnabled(BooleanStateKey.IS_COVER_CLOSED);
    }

    public final boolean isDensityOrFontChanged() {
        return isEnabled(BooleanStateKey.IS_DENSITY_OR_FONT_CHANGED);
    }

    public final boolean isDisplayTypeChanged() {
        return isEnabled(BooleanStateKey.IS_DISPLAY_TYPE_CHANGED);
    }

    public final boolean isDualPlayMode() {
        return isEnabled(BooleanStateKey.DUAL_PLAY_MODE);
    }

    public final boolean isEnabled(BooleanStateKey booleanStateKey) {
        Boolean bool = this.boolMap.get(booleanStateKey);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public final boolean isFolded() {
        return isEnabled(BooleanStateKey.FOLDER_STATE);
    }

    public final boolean isFromKey() {
        return isEnabled(BooleanStateKey.IS_FROM_KEY);
    }

    public final boolean isFromOutside() {
        return isEnabled(BooleanStateKey.FROM_OUTSIDE);
    }

    public final boolean isHasVibrator() {
        return isEnabled(BooleanStateKey.HAS_VIBRATOR);
    }

    public final boolean isHeadsetConnected() {
        return isEnabled(BooleanStateKey.IS_HEADSET_CONNECTED);
    }

    public final boolean isKeyDown() {
        return isEnabled(BooleanStateKey.IS_KEY_DOWN);
    }

    public final boolean isLockscreen() {
        return isEnabled(BooleanStateKey.IS_LOCKSCREEN);
    }

    public final boolean isMediaDefault() {
        return isEnabled(BooleanStateKey.MEDIA_DEFAULT);
    }

    public final boolean isMediaDefaultOptionHide() {
        return isEnabled(BooleanStateKey.IS_MEDIA_DEFAULT_OPTION_HIDE);
    }

    public final boolean isMultiSoundBt() {
        return isEnabled(BooleanStateKey.IS_MULTI_SOUND_BT);
    }

    public final boolean isOrientationChanged() {
        return isEnabled(BooleanStateKey.IS_ORIENTATION_CHANGED);
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

    public final boolean isSupportTvVolumeSync() {
        return isEnabled(BooleanStateKey.SUPPORT_TV_VOLUME_SYNC);
    }

    public final boolean isVibrating() {
        return isEnabled(BooleanStateKey.IS_VIBRATING);
    }

    public final boolean isVoiceCapable() {
        return isEnabled(BooleanStateKey.VOICE_CAPABLE);
    }

    public final boolean isZenEnabled() {
        return isEnabled(BooleanStateKey.IS_ZEN_ENABLED);
    }

    public final boolean isZenNone() {
        return isEnabled(BooleanStateKey.IS_ZEN_NONE);
    }

    public final boolean isZenPriorityOnly() {
        return isEnabled(BooleanStateKey.IS_ZEN_PRIORITY_ONLY);
    }

    public String toString() {
        List[] listArr = new List[4];
        HashMap<BooleanStateKey, Boolean> map = this.boolMap;
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
        HashMap<IntegerStateKey, Integer> map2 = this.intMap;
        ArrayList arrayList2 = new ArrayList(map2.size());
        for (Map.Entry<IntegerStateKey, Integer> entry3 : map2.entrySet()) {
            arrayList2.add(entry3.getKey().getFieldName() + " : " + entry3.getValue());
        }
        listArr[1] = arrayList2;
        HashMap<LongStateKey, Long> map3 = this.longMap;
        ArrayList arrayList3 = new ArrayList(map3.size());
        for (Map.Entry<LongStateKey, Long> entry4 : map3.entrySet()) {
            arrayList3.add(entry4.getKey().getFieldName() + " : " + entry4.getValue());
        }
        listArr[2] = arrayList3;
        HashMap<StringStateKey, String> map4 = this.stringMap;
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
        return CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt__IterablesKt.flatten(Arrays.asList(listArr)), null, null, null, null, 63);
    }

    private VolumePanelAction() {
        this.actionType = ActionType.ACTION_NONE;
        this.intMap = new HashMap<>();
        this.boolMap = new HashMap<>();
        this.stringMap = new HashMap<>();
        this.longMap = new HashMap<>();
        this.importantStreamList = new ArrayList();
        this.unImportantStreamList = new ArrayList();
        this.enabledStreamList = new ArrayList();
        this.disabledStreamList = new ArrayList();
    }

    public final class Builder {
        public static final int $stable = 8;
        private VolumePanelAction action;

        public Builder(ActionType actionType) {
            VolumePanelAction volumePanelAction = new VolumePanelAction(null);
            volumePanelAction.actionType = actionType;
            this.action = volumePanelAction;
        }

        public static /* synthetic */ Builder btCallDeviceName$default(Builder builder, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = "";
            }
            return builder.btCallDeviceName(str);
        }

        public final Builder activeBtDeviceName(String str) {
            return setStringValue(StringStateKey.ACTIVE_BT_DEVICE_NAME, str);
        }

        public final Builder activeStream(int i) {
            return setIntegerValue(IntegerStateKey.ACTIVE_STREAM, i);
        }

        public final Builder audioSharingDeviceName(String str) {
            return setStringValue(StringStateKey.AUDIO_SHARING_DEVICE_NAME, str);
        }

        public final Builder btCallDeviceName(String str) {
            return setStringValue(StringStateKey.BT_CALL_DEVICE_NAME, str);
        }

        public final VolumePanelAction build() {
            return this.action;
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

        public final Builder flags(int i) {
            return setIntegerValue(IntegerStateKey.FLAGS, i);
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

        public final Builder isAodScreen(boolean z) {
            return setEnabled(BooleanStateKey.IS_AOD_SCREEN, z);
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

        public final Builder isCoverClosed(boolean z) {
            return setEnabled(BooleanStateKey.IS_COVER_CLOSED, z);
        }

        public final Builder isDensityOrFontChanged(boolean z) {
            return setEnabled(BooleanStateKey.IS_DENSITY_OR_FONT_CHANGED, z);
        }

        public final Builder isDisplayTypeChanged(boolean z) {
            return setEnabled(BooleanStateKey.IS_DISPLAY_TYPE_CHANGED, z);
        }

        public final Builder isDualPlayMode(boolean z) {
            return setEnabled(BooleanStateKey.DUAL_PLAY_MODE, z);
        }

        public final Builder isFolded(boolean z) {
            return setEnabled(BooleanStateKey.FOLDER_STATE, z);
        }

        public final Builder isFromKey(boolean z) {
            return setEnabled(BooleanStateKey.IS_FROM_KEY, z);
        }

        public final Builder isFromOutside(boolean z) {
            return setEnabled(BooleanStateKey.FROM_OUTSIDE, z);
        }

        public final Builder isHasVibrator(boolean z) {
            return setEnabled(BooleanStateKey.HAS_VIBRATOR, z);
        }

        public final Builder isHeadsetConnected(boolean z) {
            return setEnabled(BooleanStateKey.IS_HEADSET_CONNECTED, z);
        }

        public final Builder isKeyDown(boolean z) {
            return setEnabled(BooleanStateKey.IS_KEY_DOWN, z);
        }

        public final Builder isLockscreen(boolean z) {
            return setEnabled(BooleanStateKey.IS_LOCKSCREEN, z);
        }

        public final Builder isMediaDefault(boolean z) {
            return setEnabled(BooleanStateKey.MEDIA_DEFAULT, z);
        }

        public final Builder isMediaDefaultOptionHide(boolean z) {
            return setEnabled(BooleanStateKey.IS_MEDIA_DEFAULT_OPTION_HIDE, z);
        }

        public final Builder isMultiSoundBt(boolean z) {
            return setEnabled(BooleanStateKey.IS_MULTI_SOUND_BT, z);
        }

        public final Builder isOrientationChanged(boolean z) {
            return setEnabled(BooleanStateKey.IS_ORIENTATION_CHANGED, z);
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

        public final Builder isSupportBudsTogether(boolean z) {
            return setEnabled(BooleanStateKey.VOLUME_BUDS_TOGETHER, z);
        }

        public final Builder isSupportDualAudio(boolean z) {
            return setEnabled(BooleanStateKey.VOLUME_DUAL_AUDIO, z);
        }

        public final Builder isSupportSmartViewStream(boolean z) {
            return setEnabled(BooleanStateKey.VOLUME_SMART_VIEW_STREAM, z);
        }

        public final Builder isSupportTvVolumeSync(boolean z) {
            return setEnabled(BooleanStateKey.SUPPORT_TV_VOLUME_SYNC, z);
        }

        public final Builder isSupportWarningPopupSideView(boolean z) {
            return setEnabled(BooleanStateKey.VOLUME_WARNING_POPUP_SIDE_VIEW, z);
        }

        public final Builder isSupportWarningPopupWalletMini(boolean z) {
            return setEnabled(BooleanStateKey.VOLUME_WARNING_POPUP_WALLET_MINI, z);
        }

        public final Builder isVibrating(boolean z) {
            return setEnabled(BooleanStateKey.IS_VIBRATING, z);
        }

        public final Builder isVoiceCapable(boolean z) {
            return setEnabled(BooleanStateKey.VOICE_CAPABLE, z);
        }

        public final Builder isZenEnabled(boolean z) {
            return setEnabled(BooleanStateKey.IS_ZEN_ENABLED, z);
        }

        public final Builder isZenNone(boolean z) {
            return setEnabled(BooleanStateKey.IS_ZEN_NONE, z);
        }

        public final Builder isZenPriorityOnly(boolean z) {
            return setEnabled(BooleanStateKey.IS_ZEN_PRIORITY_ONLY, z);
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

        public final Builder progress(int i) {
            return setIntegerValue(IntegerStateKey.PROGRESS, i);
        }

        public final Builder setCustomAction(Object obj) {
            this.action.customAction = obj;
            return this;
        }

        public final Builder setDisabledStreamList(List<Integer> list) {
            this.action.disabledStreamList = list;
            return this;
        }

        public final Builder setEnabled(BooleanStateKey booleanStateKey, boolean z) {
            this.action.boolMap.put(booleanStateKey, Boolean.valueOf(z));
            return this;
        }

        public final Builder setEnabledStreamList(List<Integer> list) {
            this.action.enabledStreamList = list;
            return this;
        }

        public final Builder setImportantStreamList(List<Integer> list) {
            this.action.importantStreamList = list;
            return this;
        }

        public final Builder setIntegerValue(IntegerStateKey integerStateKey, int i) {
            this.action.intMap.put(integerStateKey, Integer.valueOf(i));
            return this;
        }

        public final Builder setLongValue(LongStateKey longStateKey, long j) {
            this.action.longMap.put(longStateKey, Long.valueOf(j));
            return this;
        }

        public final Builder setStringValue(StringStateKey stringStateKey, String str) {
            this.action.stringMap.put(stringStateKey, str);
            return this;
        }

        public final Builder setUnImportantStreamList(List<Integer> list) {
            this.action.unImportantStreamList = list;
            return this;
        }

        public final Builder setVolumeState(VolumeState volumeState) {
            this.action.volumeState = volumeState;
            return this;
        }

        public final Builder smartViewDeviceName(String str) {
            return setStringValue(StringStateKey.SMART_VIEW_DEVICE_NAME, str);
        }

        public final Builder stream(int i) {
            return setIntegerValue(IntegerStateKey.STREAM, i);
        }

        public final Builder systemTimeNow(long j) {
            return setLongValue(LongStateKey.SYSTEM_TIME_NOW, j);
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

        public Builder(VolumePanelAction volumePanelAction) {
            VolumePanelAction volumePanelAction2 = new VolumePanelAction(null);
            volumePanelAction2.actionType = volumePanelAction.getActionType();
            volumePanelAction2.volumeState = volumePanelAction.getVolumeState();
            volumePanelAction2.importantStreamList = volumePanelAction.getImportantStreamList();
            volumePanelAction2.unImportantStreamList = volumePanelAction.getUnImportantStreamList();
            volumePanelAction2.enabledStreamList = volumePanelAction.getEnabledStreamList();
            volumePanelAction2.disabledStreamList = volumePanelAction.getDisabledStreamList();
            volumePanelAction2.intMap = volumePanelAction.intMap;
            volumePanelAction2.stringMap = volumePanelAction.stringMap;
            volumePanelAction2.boolMap = volumePanelAction.boolMap;
            volumePanelAction2.longMap = volumePanelAction.longMap;
            volumePanelAction2.customAction = volumePanelAction.getCustomAction();
            this.action = volumePanelAction2;
        }
    }

    public static /* synthetic */ void getActionType$annotations() {
    }

    public static /* synthetic */ void getActiveStream$annotations() {
    }

    public static /* synthetic */ void getCoverType$annotations() {
    }

    public static /* synthetic */ void getCustomAction$annotations() {
    }

    public static /* synthetic */ void getFlags$annotations() {
    }

    public static /* synthetic */ void getIconCurrentState$annotations() {
    }

    public static /* synthetic */ void getIconTargetState$annotations() {
    }

    public static /* synthetic */ void getStream$annotations() {
    }
}
