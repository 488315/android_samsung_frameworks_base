package com.samsung.systemui.splugins.volume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VolumePanelAction {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ActionType {
        ACTION_NONE,
        ACTION_PANEL_SHOW,
        ACTION_STATE_CHANGED,
        ACTION_ANIMATION_START,
        ACTION_ANIMATION_FINISHED,
        ACTION_TIME_OUT,
        ACTION_INIT,
        ACTION_TOUCH_OUTSIDE,
        ACTION_UPDATE_PROGRESS_BAR,
        ACTION_START_SLIDER_TRACKING,
        ACTION_STOP_SLIDER_TRACKING,
        ACTION_EXPAND_BUTTON_CLICKED,
        ACTION_VOLUME_ICON_CLICKED,
        ACTION_CHECK_IF_NEED_TO_SET_PROGRESS,
        ACTION_MEDIA_VOLUME_DEFAULT_CHANGED,
        ACTION_TOUCH_PANEL,
        ACTION_SCREEN_OFF,
        ACTION_DISMISS_REQUESTED,
        ACTION_ALL_SOUND_OFF_CHANGED,
        ACTION_SEND_ACCESSIBILITY_EVENT,
        ACTION_ACCESSIBILITY_MODE_CHANGED,
        ACTION_PLAY_SOUND_ON,
        ACTION_CONFIGURATION_CHANGED,
        ACTION_COVER_STATE_CHAGNED,
        ACTION_MIRROR_LINK_ON,
        ACTION_SMART_VIEW_SEEKBAR_TOUCHED,
        ACTION_SHOW_VOLUME_LIMITER_DIALOG,
        ACTION_DISMISS_VOLUME_LIMITER_DIALOG,
        ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED,
        ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED,
        ACTION_VOLUME_LIMITER_DIALOG_VOLUME_DOWN,
        ACTION_DISMISS_VOLUME_PANEL,
        ACTION_SHOW_VOLUME_SAFETY_WARNING_DIALOG,
        ACTION_DISMISS_VOLUME_SAFETY_WARNING_DIALOG,
        ACTION_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED,
        ACTION_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED,
        ACTION_OPEN_THEME_CHANGED,
        ACTION_PANEL_LAYOUT_CHANGED,
        ACTION_BACKGROUND_ANIMATION_FINISHED,
        ACTION_SWIPE_PANEL,
        ACTION_PANEL_ANIMATION_FINISHED,
        ACTION_CUSTOM,
        ACTION_FOLDER_STATE_CHANGED,
        ACTION_DISMISS_SUB_DISPLAY_VOLUME_PANEL,
        ACTION_ARROW_LEFT_CLICKED,
        ACTION_ARROW_RIGHT_CLICKED,
        ACTION_USER_SWITCHED,
        ACTION_CAPTION_COMPONENT_CHANGED,
        ACTION_CAPTION_CHANGED,
        ACTION_IDLE,
        ACTION_DUAL_PLAY_MODE_CHANGED,
        ACTION_STATUS_MESSAGE_CLICKED,
        ACTION_SETTINGS_BUTTON_CLICKED,
        ACTION_SEEKBAR_START_PROGRESS,
        ACTION_SEEKBAR_TOUCH_DOWN,
        ACTION_SEEKBAR_TOUCH_UP,
        ACTION_SETUP_WIZARD_COMPLETE,
        ACTION_VOLUME_ICON_ANIMATION_FINISHED,
        ACTION_SWIPE_COLLAPSED,
        ACTION_SHOW_VOLUME_CSD_100_WARNING_DIALOG,
        ACTION_DISMISS_VOLUME_CSD_100_WARNING_DIALOG,
        ACTION_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED,
        ACTION_KEY_EVENT,
        ACTION_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME,
        ACTION_VOLUME_CSD_100_WARNING_DIALOG_TIMEOUT,
        ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED,
        ACTION_HEADSET_CONNECTION
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum BooleanStateKey {
        MEDIA_DEFAULT("isMediaDefault"),
        SAFE_MEDIA_DEVICE_ON("isSafeMediaDeviceOn"),
        SAFE_MEDIA_PIN_DEVICE_ON("isSafeMediaPinDeviceOn"),
        DUAL_PLAY_MODE("isDualPlayMode"),
        VOICE_CAPABLE("isVoiceCapble"),
        FROM_OUTSIDE("isFromOutside"),
        HAS_VIBRATOR("isHasVibrator"),
        ALL_SOUND_OFF("isAllSoundOff"),
        SHOW_A11Y_STREAM("isShowA11yStream"),
        IS_ORIENTATION_CHANGED("isOrientationChanged"),
        IS_COVER_CLOSED("isCoverClosed"),
        SUPPORT_TV_VOLUME_SYNC("isSupportTvVolumeSync"),
        IS_ZEN_ENABLED("isZenEnabled"),
        IS_ZEN_PRIORITY_ONLY("isZenPriorityOnly"),
        IS_ZEN_NONE("isZenNone"),
        IS_LOCKSCREEN("isLockscreen"),
        IS_BT_SCO_ON("isBtScoOn"),
        FOLDER_STATE("isFolded"),
        IS_DENSITY_OR_FONT_CHANGED("isDensityOrFontChanged"),
        IS_MULTI_SOUND_BT("isMultiSoundBt"),
        IS_MEDIA_DEFAULT_OPTION_HIDE("isMediaDefaultOptionHide"),
        IS_CAPTION_COMPONENT_ENABLED("isCaptionComponentEnabled"),
        IS_CAPTION_ENABLED("isCaptionEnabled"),
        IS_DISPLAY_TYPE_CHANGED("isDisplayTypeChanged"),
        IS_FROM_KEY("isFromKey"),
        SETUP_WIZARD_COMPLETE("isSetupWizardComplete"),
        IS_AOD_SCREEN("isAodScreen"),
        IS_KEY_DOWN("isKeyDown"),
        IS_VIBRATING("isVibrating"),
        IS_HEADSET_CONNECTED("isHeadsetConnected"),
        VOLUME_SMART_VIEW_STREAM("isSupportSmartViewStream"),
        VOLUME_WARNING_POPUP_WALLET_MINI("isSupportWarningPopupWalletMini"),
        VOLUME_WARNING_POPUP_SIDE_VIEW("isSupportWarningPopupSideView"),
        VOLUME_BUDS_TOGETHER("isSupportBudsTogether"),
        VOLUME_DUAL_AUDIO("isSupportDualAudio");

        private final String fieldName;

        BooleanStateKey(String str) {
            this.fieldName = str;
        }

        public final String getFieldName() {
            return this.fieldName;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum IntegerStateKey {
        PIN_DEVICE("pinDevice"),
        ACTIVE_STREAM("activeStream"),
        PROGRESS("progress"),
        STREAM("stream"),
        EAR_PROTECT_LEVEL("earProtectLevel"),
        TIME_OUT_CONTROLS("timeOutControls"),
        TIME_OUT_CONTROLS_TEXT("timeOutControlsText"),
        COVER_TYPE("coverType"),
        MUSIC_FINE_VOLUME("musicFineVolume"),
        FLAGS("flags"),
        CUTOUT_HEIGHT("cutoutHeight"),
        ICON_TARGET_STATE("iconTargetState"),
        ICON_CURRENT_STATE("iconCurrentState"),
        VOLUME_DIRECTION("volumeDirection");

        private final String fieldName;

        IntegerStateKey(String str) {
            this.fieldName = str;
        }

        public final String getFieldName() {
            return this.fieldName;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum LongStateKey {
        SYSTEM_TIME_NOW("systemTimeNow");

        private final String fieldName;

        LongStateKey(String str) {
            this.fieldName = str;
        }

        public final String getFieldName() {
            return this.fieldName;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum StringStateKey {
        SMART_VIEW_DEVICE_NAME("smartViewDeviceName"),
        ACTIVE_BT_DEVICE_NAME("activeBtDeviceName"),
        PIN_APP_NAME("pinAppName"),
        PIN_DEVICE_NAME("pinDeviceName"),
        BT_CALL_DEVICE_NAME("btCallDeviceName"),
        AUDIO_SHARING_DEVICE_NAME("audioSharingDeviceName");

        private final String fieldName;

        StringStateKey(String str) {
            this.fieldName = str;
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
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public final long getLongValue(LongStateKey longStateKey) {
        Long l = this.longMap.get(longStateKey);
        if (l == null) {
            return -1L;
        }
        return l.longValue();
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
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
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
        HashMap<BooleanStateKey, Boolean> hashMap = this.boolMap;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<BooleanStateKey, Boolean> entry : hashMap.entrySet()) {
            if (entry.getValue().booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            arrayList.add(((BooleanStateKey) entry2.getKey()).getFieldName() + " : " + entry2.getValue());
        }
        listArr[0] = arrayList;
        HashMap<IntegerStateKey, Integer> hashMap2 = this.intMap;
        ArrayList arrayList2 = new ArrayList(hashMap2.size());
        for (Map.Entry<IntegerStateKey, Integer> entry3 : hashMap2.entrySet()) {
            arrayList2.add(entry3.getKey().getFieldName() + " : " + entry3.getValue());
        }
        listArr[1] = arrayList2;
        HashMap<LongStateKey, Long> hashMap3 = this.longMap;
        ArrayList arrayList3 = new ArrayList(hashMap3.size());
        for (Map.Entry<LongStateKey, Long> entry4 : hashMap3.entrySet()) {
            arrayList3.add(entry4.getKey().getFieldName() + " : " + entry4.getValue());
        }
        listArr[2] = arrayList3;
        HashMap<StringStateKey, String> hashMap4 = this.stringMap;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry<StringStateKey, String> entry5 : hashMap4.entrySet()) {
            String value = entry5.getValue();
            if (!(value == null || value.length() == 0)) {
                linkedHashMap2.put(entry5.getKey(), entry5.getValue());
            }
        }
        ArrayList arrayList4 = new ArrayList(linkedHashMap2.size());
        for (Map.Entry entry6 : linkedHashMap2.entrySet()) {
            arrayList4.add(((StringStateKey) entry6.getKey()).getFieldName() + " : " + entry6.getValue());
        }
        listArr[3] = arrayList4;
        return CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt__IterablesKt.flatten(CollectionsKt__CollectionsKt.listOf(listArr)), null, null, null, null, 63);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Builder {
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
