package com.samsung.systemui.splugins.volume;

import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
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
public final class VolumePanelState {
    public static final Companion Companion = new Companion(null);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum BooleanStateKey {
        SHOWING("isShowing"),
        DLNA_ENABLED("isDlnaEnabled"),
        SUPPORT_TV_VOLUME_CONTROL("isSupportTvVolumeControl"),
        MEDIA_DEFAULT_ENABLED("isMediaDefaultEnabled"),
        ANIMATING("isAnimating"),
        PENDING_STATE("isPendingState"),
        VOICE_CAPABLE("isVoiceCapable"),
        SAFE_MEDIA_DEVICE_ON("isSafeMediaDeviceOn"),
        SAFE_MEDIA_PIN_DEVICE_ON("isSafeMediaPinDeviceOn"),
        EXPANDED("isExpanded"),
        TRACKING("isTracking"),
        HAS_VIBRATOR("isHasVibrator"),
        ALL_SOUND_OFF("isAllSoundOff"),
        IS_DUAL_AUDIO("isDualAudio"),
        SHOW_A11Y_STREAM("isShowA11yStream"),
        IS_FROM_KEY("isFromKey"),
        CONFIGURATION_CHANGED("isConfigurationChanged"),
        IS_COVER_CLOSED("isCoverClosed"),
        SHOWING_VOLUME_LIMITER_DIALOG("isShowingVolumeLimiterDialog"),
        SHOWING_VOLUME_SAFETY_WARNING_DIALOG("isShowingVolumeSafetyWarningDialog"),
        OPEN_THEME_CHANGED("isOpenThemeChanged"),
        ZEN_MODE("isZenMode"),
        WITH_ANIMATION("isWithAnimation"),
        IS_LOCKSCREEN("isLockscreen"),
        REMOTE_MIC("isRemoteMic"),
        IS_BT_SCO_ON("isBtScoOn"),
        FOLDER_STATE("isFolded"),
        SHOWING_SUB_DISPLAY_VOLUME_PANEL("isShowingSubDisplayVolumePanel"),
        IS_MULTI_SOUND_BT("isMultiSoundBt"),
        IS_MEDIA_DEFAULT_OPTION_HIDE("isMediaDefaultOptionHide"),
        IS_CAPTION_COMPONENT_ENABLED("isCaptionComponentEnabled"),
        IS_CAPTION_ENABLED("isCaptionEnabled"),
        VOLUME_BUDS_TOGETHER("isSupportBudsTogether"),
        SETUP_WIZARD_COMPLETE("isSetupWizardComplete"),
        SHOWING_VOLUME_CSD_100_WARNING_DIALOG("isShowingVolumeCsd100WarningDialog"),
        IS_AOD_VOLUME_PANEL("isAodVolumePanel"),
        IS_KEY_DOWN("isKeyDown"),
        IS_VIBRATING("isVibrating"),
        IS_LE_BROADCASTING("isLeBroadcasting");

        private final String fieldName;

        BooleanStateKey(String str) {
            this.fieldName = str;
        }

        public final String getFieldName() {
            return this.fieldName;
        }
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
    public enum IntegerStateKey {
        TIME_OUT("timeOut"),
        ACTIVE_STREAM("activeStream"),
        PIN_DEVICE("pinDevice"),
        STREAM("stream"),
        MUSIC_FINE_VOLUME("musicFineVolume"),
        RINGER_MODE_INTERNAL("ringerModeInternal"),
        EAR_PROTECT_LEVEL("earProtectLevel"),
        TIME_OUT_CONTROLS("timeOutControls"),
        TIME_OUT_CONTROLS_TEXT("timeOutControlsText"),
        COVER_TYPE("coverType"),
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
    public enum StateType {
        STATE_IDLE,
        STATE_SHOW,
        STATE_DISMISS,
        STATE_DISMISS_VOLUME_PANEL,
        STATE_UPDATE,
        STATE_NO_DISPATCH,
        STATE_SET_STREAM_VOLUME,
        STATE_VOLUME_ICON_CLICKED,
        STATE_UPDATE_PROGRESS_BAR,
        STATE_UPDATE_PROGRESS_BAR_LATER,
        STATE_MEDIA_VOLUME_DEFAULT_CHANGED,
        STATE_TOUCH_PANEL,
        STATE_RESCHEDULE_TIME_OUT,
        STATE_PLAY_SOUND_ON,
        STATE_SMART_VIEW_ICON_CLICKED,
        STATE_SMART_VIEW_SEEKBAR_TOUCHED,
        STATE_SHOW_VOLUME_LIMITER_DIALOG,
        STATE_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED,
        STATE_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED,
        STATE_VOLUME_LIMITER_DIALOG_VOLUME_DOWN,
        STATE_SHOW_VOLUME_SAFETY_WARNING_DIALOG,
        STATE_DISMISS_VOLUME_SAFETY_WARNING_DIALOG,
        STATE_VOLUME_SAFETY_WARNING_DIALOG_FLAG_DISMISS,
        STATE_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED,
        STATE_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED,
        STATE_START_SLIDER_TRACKING,
        STATE_STOP_SLIDER_TRACKING,
        STATE_OPEN_THEME_CHANGED,
        STATE_DISMISS_VOLUME_PANEL_COMPLETED,
        STATE_UPDATE_PANEL_HEIGHT,
        STATE_EXPAND_STATE_CHANGED,
        STATE_BACKGROUND_ANIMATION_FINISHED,
        STATE_PANEL_ANIMATION_FINISHED,
        STATE_COVER_STATE_CHANGED,
        STATE_CONFIGURATION_CHANGED,
        STATE_CUSTOM,
        STATE_SHOW_SUB_DISPLAY_VOLUME_PANEL,
        STATE_ARROW_LEFT_CLICKED,
        STATE_ARROW_RIGHT_CLICKED,
        STATE_FOLDER_STATE_CHANGED,
        STATE_CAPTION_CHANGED,
        STATE_CAPTION_COMPONENT_CHANGED,
        STATE_DUAL_PLAY_MODE_CHANGED,
        STATE_ORIENTATION_CHANGED,
        STATE_STATUS_MESSAGE_CLICKED,
        STATE_SETTINGS_BUTTON_CLICKED,
        STATE_SEEKBAR_START_PROGRESS,
        STATE_SEEKBAR_TOUCH_DOWN,
        STATE_SEEKBAR_TOUCH_UP,
        STATE_SET_VOLUME_STATE,
        STATE_SHOW_VOLUME_CSD_100_WARNING_DIALOG,
        STATE_DISMISS_VOLUME_CSD_100_WARNING_DIALOG,
        STATE_VOLUME_CSD_100_WARNING_DIALOG_FLAG_DISMISS,
        STATE_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED,
        STATE_KEY_EVENT,
        STATE_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME,
        STATE_STATUS_LE_BROADCASTING_MESSAGE_CLICKED
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum StringStateKey {
        PIN_APP_NAME("pinAppName"),
        PIN_DEVICE_NAME("pinDeviceName");

        private final String fieldName;

        StringStateKey(String str) {
            this.fieldName = str;
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
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public final long getLongValue(LongStateKey longStateKey) {
        Long l = this.longState.get(longStateKey);
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
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
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

    public String toString() {
        int activeStream = getActiveStream();
        VolumePanelStateExt volumePanelStateExt = VolumePanelStateExt.INSTANCE;
        VolumePanelRow findRow = volumePanelStateExt.findRow(this, getActiveStream());
        Integer valueOf = findRow != null ? Integer.valueOf(findRow.getRealLevel()) : null;
        int stream = getStream();
        VolumePanelRow findRow2 = volumePanelStateExt.findRow(this, getStream());
        Integer valueOf2 = findRow2 != null ? Integer.valueOf(findRow2.getRealLevel()) : null;
        List[] listArr = new List[4];
        HashMap<BooleanStateKey, Boolean> hashMap = this.booleanState;
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
        HashMap<IntegerStateKey, Integer> hashMap2 = this.integerState;
        ArrayList arrayList2 = new ArrayList(hashMap2.size());
        for (Map.Entry<IntegerStateKey, Integer> entry3 : hashMap2.entrySet()) {
            arrayList2.add(entry3.getKey().getFieldName() + " : " + entry3.getValue());
        }
        listArr[1] = arrayList2;
        HashMap<LongStateKey, Long> hashMap3 = this.longState;
        ArrayList arrayList3 = new ArrayList(hashMap3.size());
        for (Map.Entry<LongStateKey, Long> entry4 : hashMap3.entrySet()) {
            arrayList3.add(entry4.getKey().getFieldName() + " : " + entry4.getValue());
        }
        listArr[2] = arrayList3;
        HashMap<StringStateKey, String> hashMap4 = this.stringState;
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
        return "activeStream=" + activeStream + "(vol=" + valueOf + "), stream=" + stream + "(vol=" + valueOf2 + ") } " + CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt__IterablesKt.flatten(CollectionsKt__CollectionsKt.listOf(listArr)), null, null, null, null, 63);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Builder {
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
            this.secVolumeState.volumeRowList = list;
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
            volumePanelState2.volumeRowList = volumePanelState.getVolumeRowList();
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
