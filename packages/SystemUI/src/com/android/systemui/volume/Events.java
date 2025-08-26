package com.android.systemui.volume;

import android.media.AudioSystem;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class Events {
    public static final String TAG = Util.logTag(Events.class);
    public static final String[] EVENT_TAGS = {"show_dialog", "dismiss_dialog", "active_stream_changed", "expand", "key", "collection_started", "collection_stopped", "icon_click", "settings_click", "touch_level_changed", "level_changed", "internal_ringer_mode_changed", "external_ringer_mode_changed", "zen_mode_changed", "suppressor_changed", "mute_changed", "touch_level_done", "zen_mode_config_changed", "ringer_toggle", "show_usb_overheat_alarm", "dismiss_usb_overheat_alarm", "odi_captions_click", "odi_captions_tooltip_click", "slider_touch_tracking"};
    public static final String[] DISMISS_REASONS = {"unknown", "touch_outside", "volume_controller", "timeout", "screen_off", "settings_clicked", "done_clicked", "a11y_stream_changed", "output_chooser", "usb_temperature_below_threshold", "csd_warning_timeout", "posture_changed"};
    public static final String[] SHOW_REASONS = {"unknown", "volume_changed", "remote_volume_changed", "usb_temperature_above_threshold"};
    static MetricsLogger sLegacyLogger = new MetricsLogger();
    static UiEventLogger sUiEventLogger = new UiEventLoggerImpl();

    public enum VolumeDialogCloseEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        VOLUME_DIALOG_DISMISS_TOUCH_OUTSIDE(134),
        VOLUME_DIALOG_DISMISS_SYSTEM(135),
        VOLUME_DIALOG_DISMISS_TIMEOUT(136),
        VOLUME_DIALOG_DISMISS_SCREEN_OFF(137),
        VOLUME_DIALOG_DISMISS_SETTINGS(138),
        VOLUME_DIALOG_DISMISS_STREAM_GONE(140),
        VOLUME_DIALOG_DISMISS_USB_TEMP_ALARM_CHANGED(142);

        private final int mId;

        VolumeDialogCloseEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public enum VolumeDialogEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        VOLUME_DIALOG_SETTINGS_CLICK(143),
        VOLUME_DIALOG_EXPAND_DETAILS(144),
        VOLUME_DIALOG_COLLAPSE_DETAILS(145),
        VOLUME_DIALOG_ACTIVE_STREAM_CHANGED(146),
        VOLUME_DIALOG_MUTE_STREAM(147),
        VOLUME_DIALOG_UNMUTE_STREAM(148),
        VOLUME_DIALOG_TO_VIBRATE_STREAM(149),
        VOLUME_DIALOG_SLIDER(150),
        VOLUME_DIALOG_SLIDER_TO_ZERO(151),
        VOLUME_DIALOG_SLIDER_STARTED_TRACKING_TOUCH(1620),
        VOLUME_DIALOG_SLIDER_STOPPED_TRACKING_TOUCH(1621),
        VOLUME_DIALOG_ODI_CAPTIONS_CLICKED(VolteConstants.ErrorCode.DNS_FAILURE_HOST),
        VOLUME_DIALOG_ODI_CAPTIONS_TOOLTIP_CLICKED(VolteConstants.ErrorCode.DNS_FAILURE_NAPTR),
        VOLUME_KEY_TO_ZERO(152),
        VOLUME_KEY(153),
        RINGER_MODE_SILENT(154),
        RINGER_MODE_VIBRATE(155),
        RINGER_MODE_NORMAL(334),
        USB_OVERHEAT_ALARM(160),
        USB_OVERHEAT_ALARM_DISMISSED(161);

        private final int mId;

        VolumeDialogEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public enum VolumeDialogOpenEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        VOLUME_DIALOG_SHOW_VOLUME_CHANGED(128),
        VOLUME_DIALOG_SHOW_REMOTE_VOLUME_CHANGED(129),
        VOLUME_DIALOG_SHOW_USB_TEMP_ALARM_CHANGED(130);

        private final int mId;

        VolumeDialogOpenEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public enum ZenModeEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        ZEN_MODE_OFF(335),
        ZEN_MODE_IMPORTANT_ONLY(157),
        ZEN_MODE_ALARMS_ONLY(158),
        ZEN_MODE_NO_INTERRUPTIONS(159);

        private final int mId;

        ZenModeEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void writeEvent(int i, Object... objArr) {
        int iIntValue;
        String string;
        System.currentTimeMillis();
        String[] strArr = EVENT_TAGS;
        if (i >= strArr.length) {
            string = "";
        } else {
            StringBuilder sb = new StringBuilder("writeEvent ");
            sb.append(strArr[i]);
            if (objArr.length == 0) {
                if (i == 8) {
                    sLegacyLogger.action(1386);
                    sUiEventLogger.log(VolumeDialogEvent.VOLUME_DIALOG_SETTINGS_CLICK);
                } else if (i == 21) {
                    sUiEventLogger.log(VolumeDialogEvent.VOLUME_DIALOG_ODI_CAPTIONS_CLICKED);
                } else if (i == 22) {
                    sUiEventLogger.log(VolumeDialogEvent.VOLUME_DIALOG_ODI_CAPTIONS_TOOLTIP_CLICKED);
                }
                string = sb.toString();
            } else {
                sb.append(" ");
                String str = SystemUIAnalytics.QPNE_VID_SILENT;
                String strM = "vibrate";
                String str2 = "unknown";
                String[] strArr2 = SHOW_REASONS;
                String[] strArr3 = DISMISS_REASONS;
                switch (i) {
                    case 0:
                        sLegacyLogger.visible(207);
                        if (objArr.length > 1) {
                            Integer num = (Integer) objArr[0];
                            Boolean bool = (Boolean) objArr[1];
                            sLegacyLogger.histogram("volume_from_keyguard", bool.booleanValue() ? 1 : 0);
                            UiEventLogger uiEventLogger = sUiEventLogger;
                            int iIntValue2 = num.intValue();
                            uiEventLogger.log(iIntValue2 != 1 ? iIntValue2 != 2 ? iIntValue2 != 3 ? VolumeDialogOpenEvent.INVALID : VolumeDialogOpenEvent.VOLUME_DIALOG_SHOW_USB_TEMP_ALARM_CHANGED : VolumeDialogOpenEvent.VOLUME_DIALOG_SHOW_REMOTE_VOLUME_CHANGED : VolumeDialogOpenEvent.VOLUME_DIALOG_SHOW_VOLUME_CHANGED);
                            sb.append(strArr2[num.intValue()]);
                            sb.append(" keyguard=");
                            sb.append(bool);
                            break;
                        }
                        break;
                    case 1:
                        sLegacyLogger.hidden(207);
                        Integer num2 = (Integer) objArr[0];
                        UiEventLogger uiEventLogger2 = sUiEventLogger;
                        int iIntValue3 = num2.intValue();
                        uiEventLogger2.log(iIntValue3 != 1 ? iIntValue3 != 2 ? iIntValue3 != 3 ? iIntValue3 != 4 ? iIntValue3 != 5 ? iIntValue3 != 7 ? iIntValue3 != 9 ? VolumeDialogCloseEvent.INVALID : VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_USB_TEMP_ALARM_CHANGED : VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_STREAM_GONE : VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_SETTINGS : VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_SCREEN_OFF : VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_TIMEOUT : VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_SYSTEM : VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_TOUCH_OUTSIDE);
                        sb.append(strArr3[num2.intValue()]);
                        break;
                    case 2:
                        Integer num3 = (Integer) objArr[0];
                        sLegacyLogger.action(210, num3.intValue());
                        sUiEventLogger.log(VolumeDialogEvent.VOLUME_DIALOG_ACTIVE_STREAM_CHANGED);
                        sb.append(AudioSystem.streamToString(num3.intValue()));
                        break;
                    case 3:
                        Boolean bool2 = (Boolean) objArr[0];
                        sLegacyLogger.visibility(208, bool2.booleanValue());
                        sUiEventLogger.log(bool2.booleanValue() ? VolumeDialogEvent.VOLUME_DIALOG_EXPAND_DETAILS : VolumeDialogEvent.VOLUME_DIALOG_COLLAPSE_DETAILS);
                        sb.append(bool2);
                        break;
                    case 4:
                        if (objArr.length > 1) {
                            Integer num4 = (Integer) objArr[0];
                            sLegacyLogger.action(IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState, num4.intValue());
                            Integer num5 = (Integer) objArr[1];
                            sUiEventLogger.log(num5.intValue() == 0 ? VolumeDialogEvent.VOLUME_KEY_TO_ZERO : VolumeDialogEvent.VOLUME_KEY);
                            sb.append(AudioSystem.streamToString(num4.intValue()));
                            sb.append(' ');
                            sb.append(num5);
                            break;
                        }
                        break;
                    case 5:
                    case 6:
                    case 8:
                    case 17:
                    case 21:
                    case 22:
                    default:
                        sb.append(Arrays.asList(objArr));
                        break;
                    case 7:
                        if (objArr.length > 1) {
                            Integer num6 = (Integer) objArr[0];
                            sLegacyLogger.action(IKnoxCustomManager.Stub.TRANSACTION_getWifiState, num6.intValue());
                            Integer num7 = (Integer) objArr[1];
                            UiEventLogger uiEventLogger3 = sUiEventLogger;
                            int iIntValue4 = num7.intValue();
                            uiEventLogger3.log(iIntValue4 != 1 ? iIntValue4 != 2 ? iIntValue4 != 3 ? VolumeDialogEvent.INVALID : VolumeDialogEvent.VOLUME_DIALOG_TO_VIBRATE_STREAM : VolumeDialogEvent.VOLUME_DIALOG_MUTE_STREAM : VolumeDialogEvent.VOLUME_DIALOG_UNMUTE_STREAM);
                            sb.append(AudioSystem.streamToString(num6.intValue()));
                            sb.append(' ');
                            int iIntValue5 = num7.intValue();
                            if (iIntValue5 == 1) {
                                strM = "unmute";
                            } else if (iIntValue5 == 2) {
                                strM = "mute";
                            } else if (iIntValue5 != 3) {
                                strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iIntValue5, "unknown_state_");
                            }
                            sb.append(strM);
                            break;
                        }
                        break;
                    case 9:
                    case 10:
                    case 15:
                        if (objArr.length > 1) {
                            sb.append(AudioSystem.streamToString(((Integer) objArr[0]).intValue()));
                            sb.append(' ');
                            sb.append(objArr[1]);
                            break;
                        }
                        break;
                    case 11:
                        iIntValue = ((Integer) objArr[0]).intValue();
                        if (iIntValue != 0) {
                            str = iIntValue != 1 ? iIntValue != 2 ? "unknown" : SystemUIAnalytics.QPNE_VID_NORMAL : "vibrate";
                        }
                        sb.append(str);
                        break;
                    case 12:
                        sLegacyLogger.action(IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber, ((Integer) objArr[0]).intValue());
                        iIntValue = ((Integer) objArr[0]).intValue();
                        if (iIntValue != 0) {
                        }
                        sb.append(str);
                        break;
                    case 13:
                        Integer num8 = (Integer) objArr[0];
                        int iIntValue6 = num8.intValue();
                        if (iIntValue6 == 0) {
                            str2 = "off";
                        } else if (iIntValue6 == 1) {
                            str2 = "important_interruptions";
                        } else if (iIntValue6 == 2) {
                            str2 = "no_interruptions";
                        } else if (iIntValue6 == 3) {
                            str2 = "alarms";
                        }
                        sb.append(str2);
                        UiEventLogger uiEventLogger4 = sUiEventLogger;
                        int iIntValue7 = num8.intValue();
                        uiEventLogger4.log(iIntValue7 != 0 ? iIntValue7 != 1 ? iIntValue7 != 2 ? iIntValue7 != 3 ? ZenModeEvent.INVALID : ZenModeEvent.ZEN_MODE_ALARMS_ONLY : ZenModeEvent.ZEN_MODE_NO_INTERRUPTIONS : ZenModeEvent.ZEN_MODE_IMPORTANT_ONLY : ZenModeEvent.ZEN_MODE_OFF);
                        break;
                    case 14:
                        if (objArr.length > 1) {
                            sb.append(objArr[0]);
                            sb.append(' ');
                            sb.append(objArr[1]);
                            break;
                        }
                        break;
                    case 16:
                        if (objArr.length > 1) {
                            Integer num9 = (Integer) objArr[1];
                            sLegacyLogger.action(209, num9.intValue());
                            sUiEventLogger.log(num9.intValue() == 0 ? VolumeDialogEvent.VOLUME_DIALOG_SLIDER_TO_ZERO : VolumeDialogEvent.VOLUME_DIALOG_SLIDER);
                        }
                        if (objArr.length > 1) {
                        }
                        break;
                    case 18:
                        Integer num10 = (Integer) objArr[0];
                        sLegacyLogger.action(1385, num10.intValue());
                        UiEventLogger uiEventLogger5 = sUiEventLogger;
                        int iIntValue8 = num10.intValue();
                        uiEventLogger5.log(iIntValue8 != 0 ? iIntValue8 != 1 ? iIntValue8 != 2 ? VolumeDialogEvent.INVALID : VolumeDialogEvent.RINGER_MODE_NORMAL : VolumeDialogEvent.RINGER_MODE_VIBRATE : VolumeDialogEvent.RINGER_MODE_SILENT);
                        int iIntValue9 = num10.intValue();
                        if (iIntValue9 != 0) {
                            str = iIntValue9 != 1 ? iIntValue9 != 2 ? "unknown" : SystemUIAnalytics.QPNE_VID_NORMAL : "vibrate";
                        }
                        sb.append(str);
                        break;
                    case 19:
                        sLegacyLogger.visible(1457);
                        sUiEventLogger.log(VolumeDialogEvent.USB_OVERHEAT_ALARM);
                        if (objArr.length > 1) {
                            Boolean bool3 = (Boolean) objArr[1];
                            sLegacyLogger.histogram("show_usb_overheat_alarm", bool3.booleanValue() ? 1 : 0);
                            sb.append(strArr2[((Integer) objArr[0]).intValue()]);
                            sb.append(" keyguard=");
                            sb.append(bool3);
                            break;
                        }
                        break;
                    case 20:
                        sLegacyLogger.hidden(1457);
                        sUiEventLogger.log(VolumeDialogEvent.USB_OVERHEAT_ALARM_DISMISSED);
                        if (objArr.length > 1) {
                            Boolean bool4 = (Boolean) objArr[1];
                            sLegacyLogger.histogram("dismiss_usb_overheat_alarm", bool4.booleanValue() ? 1 : 0);
                            sb.append(strArr3[((Integer) objArr[0]).intValue()]);
                            sb.append(" keyguard=");
                            sb.append(bool4);
                            break;
                        }
                        break;
                    case 23:
                        sUiEventLogger.log(((Boolean) objArr[0]).booleanValue() ? VolumeDialogEvent.VOLUME_DIALOG_SLIDER_STARTED_TRACKING_TOUCH : VolumeDialogEvent.VOLUME_DIALOG_SLIDER_STOPPED_TRACKING_TOUCH);
                        sb.append(Arrays.asList(objArr));
                        break;
                }
                string = sb.toString();
            }
        }
        Log.i(TAG, string);
    }
}
