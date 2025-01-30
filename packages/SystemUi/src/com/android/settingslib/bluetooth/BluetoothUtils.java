package com.android.settingslib.bluetooth;

import android.R;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManufacturerData;
import android.bluetooth.SemBluetoothUuid;
import android.content.ContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.settingslib.bluetooth.BluetoothRestoredDevice;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.sec.ims.configuration.DATA;
import com.sec.ims.im.ImIntent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BluetoothUtils {
    public static boolean mDexQuickPannelOn;
    public static boolean mQuickPannelOn;
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final String[] BD_ROTATE_LEFT = {"00", "02", "04", "06", "08", "0A", "0C", "0E", DATA.DM_FIELD_INDEX.SMS_OVER_IMS, DATA.DM_FIELD_INDEX.SIP_T1_TIMER, DATA.DM_FIELD_INDEX.SIP_T4_TIMER, DATA.DM_FIELD_INDEX.SIP_TB_TIMER, DATA.DM_FIELD_INDEX.SIP_TD_TIMER, "1A", "1C", "1E", DATA.DM_FIELD_INDEX.SIP_TF_TIMER, DATA.DM_FIELD_INDEX.SIP_TH_TIMER, DATA.DM_FIELD_INDEX.SIP_TJ_TIMER, DATA.DM_FIELD_INDEX.CAP_CACHE_EXP, DATA.DM_FIELD_INDEX.SRC_THROTTLE_PUBLISH, "2A", "2C", "2E", DATA.DM_FIELD_INDEX.LVC_BETA_SETTING, DATA.DM_FIELD_INDEX.AVAIL_CACHE_EXP, DATA.DM_FIELD_INDEX.FQDN_FOR_PCSCF, DATA.DM_FIELD_INDEX.PUBLISH_TIMER, DATA.DM_FIELD_INDEX.GZIP_FLAG, "3A", "3C", "3E", DATA.DM_FIELD_INDEX.T_DELAY, DATA.DM_FIELD_INDEX.MIN_SE, DATA.DM_FIELD_INDEX.SILENT_REDIAL_ENABLE, DATA.DM_FIELD_INDEX.PUBLISH_ERR_RETRY_TIMER, DATA.DM_FIELD_INDEX.RINGING_TIMER, "4A", "4C", "4E", DATA.DM_FIELD_INDEX.RTP_RTCP_TIMER, DATA.DM_FIELD_INDEX.URI_MEDIA_RSC_SERV_3WAY_CALL, DATA.DM_FIELD_INDEX.CAP_DISCOVERY, DATA.DM_FIELD_INDEX.SRC_AMR, DATA.DM_FIELD_INDEX.HD_VOICE, "5A", "5C", "5E", DATA.DM_FIELD_INDEX.AUDIO_RTP_PORT_START, DATA.DM_FIELD_INDEX.VIDEO_RTP_PORT_START, DATA.DM_FIELD_INDEX.AMR_WB_OCTET_ALIGNED, DATA.DM_FIELD_INDEX.AMR_OCTET_ALIGNED, DATA.DM_FIELD_INDEX.H264_VGA, "6A", "6C", "6E", DATA.DM_FIELD_INDEX.DTMF_WB, DATA.DM_FIELD_INDEX.VOLTE_PREF_SERVICE_STATUS, DATA.DM_FIELD_INDEX.DM_APP_ID, DATA.DM_FIELD_INDEX.DM_CON_REF, DATA.DM_FIELD_INDEX.ICSI, "7A", "7C", "7E", DATA.DM_FIELD_INDEX.RSC_ALLOC_MODE, DATA.DM_FIELD_INDEX.VOICE_DOMAIN_PREF_UTRAN, DATA.DM_FIELD_INDEX.REG_RETRY_BASE_TIME, DATA.DM_FIELD_INDEX.PHONE_CONTEXT_PARAM, DATA.DM_FIELD_INDEX.SS_DOMAIN_SETTING, "8A", "8C", "8E", DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD, DATA.DM_FIELD_INDEX.CONF_FACTORY_URI, DATA.DM_FIELD_INDEX.LVC_ENABLED, DATA.DM_FIELD_INDEX.VOLTE_ENABLED_BY_USER, DATA.DM_FIELD_INDEX.USSD_CONTROL_PREF, "9A", "9C", "9E", "A0", "A2", "A4", "A6", "A8", "AA", "AC", "AE", "B0", "B2", "B4", "B6", "B8", "BA", "BC", "BE", "C0", "C2", "C4", "C6", "C8", "CA", "CC", "CE", "D0", "D2", "D4", "D6", "D8", "DA", "DC", "DE", "E0", "E2", "E4", "E6", "E8", "EA", CertProvisionProfile.KEY_TYPE_EC, "EE", "F0", "F2", "F4", "F6", "F8", "FA", "FC", "FE", "01", "03", "05", "07", "09", "0B", "0D", "0F", DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, DATA.DM_FIELD_INDEX.SIP_T2_TIMER, DATA.DM_FIELD_INDEX.SIP_TA_TIMER, DATA.DM_FIELD_INDEX.SIP_TC_TIMER, DATA.DM_FIELD_INDEX.SIP_TE_TIMER, "1B", PeripheralBarcodeConstants.Symbology.Type.TYPE_1D, "1F", DATA.DM_FIELD_INDEX.SIP_TG_TIMER, DATA.DM_FIELD_INDEX.SIP_TI_TIMER, DATA.DM_FIELD_INDEX.SIP_TK_TIMER, DATA.DM_FIELD_INDEX.CAP_POLL_INTERVAL, DATA.DM_FIELD_INDEX.SUBSCRIBE_MAX_ENTRY, "2B", PeripheralBarcodeConstants.Symbology.Type.TYPE_2D, "2F", DATA.DM_FIELD_INDEX.EAB_SETTING, DATA.DM_FIELD_INDEX.PREF_CSCF_PORT, DATA.DM_FIELD_INDEX.POLL_LIST_SUB_EXP, DATA.DM_FIELD_INDEX.PUBLISH_TIMER_EXTEND, DATA.DM_FIELD_INDEX.TIMER_VZW, "3B", "3D", "3F", DATA.DM_FIELD_INDEX.IMS_TEST_MODE, DATA.DM_FIELD_INDEX.DCN_NUMBER, DATA.DM_FIELD_INDEX.T_LTE_911_FAIL, DATA.DM_FIELD_INDEX.SPEAKER_DEFAULT_VIDEO, DATA.DM_FIELD_INDEX.RINGBACK_TIMER, "4B", "4D", "4F", DATA.DM_FIELD_INDEX.DOMAIN_PUI, "53", DATA.DM_FIELD_INDEX.AMR_WB, DATA.DM_FIELD_INDEX.SRC_AMR_WB, DATA.DM_FIELD_INDEX.UDP_KEEP_ALIVE, "5B", "5D", "5F", DATA.DM_FIELD_INDEX.AUDIO_RTP_PORT_END, DATA.DM_FIELD_INDEX.VIDEO_RTP_PORT_END, DATA.DM_FIELD_INDEX.AMR_WB_BANDWITH_EFFICIENT, DATA.DM_FIELD_INDEX.AMR_BANDWITH_EFFICIENT, DATA.DM_FIELD_INDEX.H264_QVGA, "6B", "6D", "6F", DATA.DM_FIELD_INDEX.DTMF_NB, DATA.DM_FIELD_INDEX.SMS_PSI, DATA.DM_FIELD_INDEX.DM_USER_DISP_NAME, DATA.DM_FIELD_INDEX.PDP_CONTEXT_PREF, DATA.DM_FIELD_INDEX.ICSI_RSC_ALLOC_MODE, "7B", "7D", "7F", DATA.DM_FIELD_INDEX.VOICE_DOMAIN_PREF_EUTRAN, DATA.DM_FIELD_INDEX.IMS_VOICE_TERMINATION, DATA.DM_FIELD_INDEX.REG_RETRY_MAX_TIME, DATA.DM_FIELD_INDEX.PHONE_CONTEXT_PUID, DATA.DM_FIELD_INDEX.SS_CONTROL_PREF, "8B", "8D", "8F", DATA.DM_FIELD_INDEX.ICCID, DATA.DM_FIELD_INDEX.VOLTE_ENABLED, DATA.DM_FIELD_INDEX.EAB_SETTING_BY_USER, DATA.DM_FIELD_INDEX.LVC_ENABLED_BY_USER, DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF, "9B", "9D", "9F", "A1", "A3", "A5", "A7", "A9", "AB", "AD", "AF", "B1", "B3", "B5", "B7", "B9", "BB", "BD", "BF", "C1", "C3", "C5", "C7", "C9", "CB", "CD", "CF", "D1", "D3", "D5", "D7", "D9", "DB", "DD", "DF", "E1", "E3", "E5", "E7", "E9", "EB", "ED", "EF", "F1", "F3", "F5", "F7", "F9", "FB", "FD", "FF"};
    public static final String[] BD_ROTATE_RIGHT = {"00", DATA.DM_FIELD_INDEX.RSC_ALLOC_MODE, "01", DATA.DM_FIELD_INDEX.VOICE_DOMAIN_PREF_EUTRAN, "02", DATA.DM_FIELD_INDEX.VOICE_DOMAIN_PREF_UTRAN, "03", DATA.DM_FIELD_INDEX.IMS_VOICE_TERMINATION, "04", DATA.DM_FIELD_INDEX.REG_RETRY_BASE_TIME, "05", DATA.DM_FIELD_INDEX.REG_RETRY_MAX_TIME, "06", DATA.DM_FIELD_INDEX.PHONE_CONTEXT_PARAM, "07", DATA.DM_FIELD_INDEX.PHONE_CONTEXT_PUID, "08", DATA.DM_FIELD_INDEX.SS_DOMAIN_SETTING, "09", DATA.DM_FIELD_INDEX.SS_CONTROL_PREF, "0A", "8A", "0B", "8B", "0C", "8C", "0D", "8D", "0E", "8E", "0F", "8F", DATA.DM_FIELD_INDEX.SMS_OVER_IMS, DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, DATA.DM_FIELD_INDEX.ICCID, DATA.DM_FIELD_INDEX.SIP_T1_TIMER, DATA.DM_FIELD_INDEX.CONF_FACTORY_URI, DATA.DM_FIELD_INDEX.SIP_T2_TIMER, DATA.DM_FIELD_INDEX.VOLTE_ENABLED, DATA.DM_FIELD_INDEX.SIP_T4_TIMER, DATA.DM_FIELD_INDEX.LVC_ENABLED, DATA.DM_FIELD_INDEX.SIP_TA_TIMER, DATA.DM_FIELD_INDEX.EAB_SETTING_BY_USER, DATA.DM_FIELD_INDEX.SIP_TB_TIMER, DATA.DM_FIELD_INDEX.VOLTE_ENABLED_BY_USER, DATA.DM_FIELD_INDEX.SIP_TC_TIMER, DATA.DM_FIELD_INDEX.LVC_ENABLED_BY_USER, DATA.DM_FIELD_INDEX.SIP_TD_TIMER, DATA.DM_FIELD_INDEX.USSD_CONTROL_PREF, DATA.DM_FIELD_INDEX.SIP_TE_TIMER, DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF, "1A", "9A", "1B", "9B", "1C", "9C", PeripheralBarcodeConstants.Symbology.Type.TYPE_1D, "9D", "1E", "9E", "1F", "9F", DATA.DM_FIELD_INDEX.SIP_TF_TIMER, "A0", DATA.DM_FIELD_INDEX.SIP_TG_TIMER, "A1", DATA.DM_FIELD_INDEX.SIP_TH_TIMER, "A2", DATA.DM_FIELD_INDEX.SIP_TI_TIMER, "A3", DATA.DM_FIELD_INDEX.SIP_TJ_TIMER, "A4", DATA.DM_FIELD_INDEX.SIP_TK_TIMER, "A5", DATA.DM_FIELD_INDEX.CAP_CACHE_EXP, "A6", DATA.DM_FIELD_INDEX.CAP_POLL_INTERVAL, "A7", DATA.DM_FIELD_INDEX.SRC_THROTTLE_PUBLISH, "A8", DATA.DM_FIELD_INDEX.SUBSCRIBE_MAX_ENTRY, "A9", "2A", "AA", "2B", "AB", "2C", "AC", PeripheralBarcodeConstants.Symbology.Type.TYPE_2D, "AD", "2E", "AE", "2F", "AF", DATA.DM_FIELD_INDEX.LVC_BETA_SETTING, "B0", DATA.DM_FIELD_INDEX.EAB_SETTING, "B1", DATA.DM_FIELD_INDEX.AVAIL_CACHE_EXP, "B2", DATA.DM_FIELD_INDEX.PREF_CSCF_PORT, "B3", DATA.DM_FIELD_INDEX.FQDN_FOR_PCSCF, "B4", DATA.DM_FIELD_INDEX.POLL_LIST_SUB_EXP, "B5", DATA.DM_FIELD_INDEX.PUBLISH_TIMER, "B6", DATA.DM_FIELD_INDEX.PUBLISH_TIMER_EXTEND, "B7", DATA.DM_FIELD_INDEX.GZIP_FLAG, "B8", DATA.DM_FIELD_INDEX.TIMER_VZW, "B9", "3A", "BA", "3B", "BB", "3C", "BC", "3D", "BD", "3E", "BE", "3F", "BF", DATA.DM_FIELD_INDEX.T_DELAY, "C0", DATA.DM_FIELD_INDEX.IMS_TEST_MODE, "C1", DATA.DM_FIELD_INDEX.MIN_SE, "C2", DATA.DM_FIELD_INDEX.DCN_NUMBER, "C3", DATA.DM_FIELD_INDEX.SILENT_REDIAL_ENABLE, "C4", DATA.DM_FIELD_INDEX.T_LTE_911_FAIL, "C5", DATA.DM_FIELD_INDEX.PUBLISH_ERR_RETRY_TIMER, "C6", DATA.DM_FIELD_INDEX.SPEAKER_DEFAULT_VIDEO, "C7", DATA.DM_FIELD_INDEX.RINGING_TIMER, "C8", DATA.DM_FIELD_INDEX.RINGBACK_TIMER, "C9", "4A", "CA", "4B", "CB", "4C", "CC", "4D", "CD", "4E", "CE", "4F", "CF", DATA.DM_FIELD_INDEX.RTP_RTCP_TIMER, "D0", DATA.DM_FIELD_INDEX.DOMAIN_PUI, "D1", DATA.DM_FIELD_INDEX.URI_MEDIA_RSC_SERV_3WAY_CALL, "D2", "53", "D3", DATA.DM_FIELD_INDEX.CAP_DISCOVERY, "D4", DATA.DM_FIELD_INDEX.AMR_WB, "D5", DATA.DM_FIELD_INDEX.SRC_AMR, "D6", DATA.DM_FIELD_INDEX.SRC_AMR_WB, "D7", DATA.DM_FIELD_INDEX.HD_VOICE, "D8", DATA.DM_FIELD_INDEX.UDP_KEEP_ALIVE, "D9", "5A", "DA", "5B", "DB", "5C", "DC", "5D", "DD", "5E", "DE", "5F", "DF", DATA.DM_FIELD_INDEX.AUDIO_RTP_PORT_START, "E0", DATA.DM_FIELD_INDEX.AUDIO_RTP_PORT_END, "E1", DATA.DM_FIELD_INDEX.VIDEO_RTP_PORT_START, "E2", DATA.DM_FIELD_INDEX.VIDEO_RTP_PORT_END, "E3", DATA.DM_FIELD_INDEX.AMR_WB_OCTET_ALIGNED, "E4", DATA.DM_FIELD_INDEX.AMR_WB_BANDWITH_EFFICIENT, "E5", DATA.DM_FIELD_INDEX.AMR_OCTET_ALIGNED, "E6", DATA.DM_FIELD_INDEX.AMR_BANDWITH_EFFICIENT, "E7", DATA.DM_FIELD_INDEX.H264_VGA, "E8", DATA.DM_FIELD_INDEX.H264_QVGA, "E9", "6A", "EA", "6B", "EB", "6C", CertProvisionProfile.KEY_TYPE_EC, "6D", "ED", "6E", "EE", "6F", "EF", DATA.DM_FIELD_INDEX.DTMF_WB, "F0", DATA.DM_FIELD_INDEX.DTMF_NB, "F1", DATA.DM_FIELD_INDEX.VOLTE_PREF_SERVICE_STATUS, "F2", DATA.DM_FIELD_INDEX.SMS_PSI, "F3", DATA.DM_FIELD_INDEX.DM_APP_ID, "F4", DATA.DM_FIELD_INDEX.DM_USER_DISP_NAME, "F5", DATA.DM_FIELD_INDEX.DM_CON_REF, "F6", DATA.DM_FIELD_INDEX.PDP_CONTEXT_PREF, "F7", DATA.DM_FIELD_INDEX.ICSI, "F8", DATA.DM_FIELD_INDEX.ICSI_RSC_ALLOC_MODE, "F9", "7A", "FA", "7B", "FB", "7C", "FC", "7D", "FD", "7E", "FE", "7F", "FF"};
    public static final C09152 mOnInitCallback = new Object() { // from class: com.android.settingslib.bluetooth.BluetoothUtils.2
    };

    public static boolean compareSameWithGear(int i, String str, String str2, String str3) {
        byte[] bArr = new byte[6];
        int i2 = 0;
        int i3 = 0;
        while (i2 < str2.length()) {
            if (str2.charAt(i2) != ':') {
                bArr[i3] = (byte) Integer.parseInt(str2.substring(i2, i2 + 2), 16);
                i3++;
                i2++;
            }
            i2++;
        }
        if (i == 0) {
            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
            m18m.append(String.format(Locale.US, "%02X", Byte.valueOf(bArr[0])).substring(1, 2));
            if (!m18m.toString().equals(str3.substring(0, 2))) {
                return false;
            }
            for (int i4 = 1; i4 < 6; i4++) {
                int i5 = i4 * 3;
                if (!BD_ROTATE_RIGHT[bArr[i4] & 255].equals(str3.substring(i5, i5 + 2))) {
                    return false;
                }
            }
        } else {
            if (i != 1 || !String.format(Locale.US, "%02X", Byte.valueOf((byte) (bArr[0] | 192))).equals(str3.substring(0, 2))) {
                return false;
            }
            for (int i6 = 1; i6 < 6; i6++) {
                int i7 = i6 * 3;
                if (!BD_ROTATE_LEFT[bArr[i6] & 255].equals(str3.substring(i7, i7 + 2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static Pair getBtClassDrawableWithDescription(Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothClass bluetoothClass = cachedBluetoothDevice.mDevice.getBluetoothClass();
        if (bluetoothClass != null) {
            int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
            if (majorDeviceClass == 256) {
                return new Pair(context.getDrawable(R.drawable.ic_alert_window_layer), context.getString(com.android.systemui.R.string.bluetooth_talkback_computer));
            }
            if (majorDeviceClass == 512) {
                return new Pair(context.getDrawable(R.drawable.ic_perm_group_camera), context.getString(com.android.systemui.R.string.bluetooth_talkback_phone));
            }
            if (majorDeviceClass == 1280) {
                return new Pair(context.getDrawable(HidProfile.getHidClassDrawable(bluetoothClass)), context.getString(com.android.systemui.R.string.bluetooth_talkback_input_peripheral));
            }
            if (majorDeviceClass == 1536) {
                return new Pair(context.getDrawable(R.drawable.ic_qs_bluetooth), context.getString(com.android.systemui.R.string.bluetooth_talkback_imaging));
            }
        }
        int i = 0;
        for (LocalBluetoothProfile localBluetoothProfile : cachedBluetoothDevice.getProfiles()) {
            int drawableResource = localBluetoothProfile.getDrawableResource(bluetoothClass);
            if (drawableResource != 0) {
                if ((localBluetoothProfile instanceof HearingAidProfile) || (localBluetoothProfile instanceof HapClientProfile)) {
                    return new Pair(context.getDrawable(drawableResource), null);
                }
                if (i == 0) {
                    i = drawableResource;
                }
            }
        }
        if (i != 0) {
            return new Pair(context.getDrawable(i), null);
        }
        if (bluetoothClass != null) {
            if (bluetoothClass.doesClassMatch(0)) {
                return new Pair(context.getDrawable(R.drawable.ic_add_supervised_user), context.getString(com.android.systemui.R.string.bluetooth_talkback_headset));
            }
            if (bluetoothClass.doesClassMatch(1)) {
                return new Pair(context.getDrawable(R.drawable.ic_action_open), context.getString(com.android.systemui.R.string.bluetooth_talkback_headphone));
            }
        }
        return new Pair(context.getDrawable(R.drawable.ic_qs_auto_rotate).mutate(), context.getString(com.android.systemui.R.string.bluetooth_talkback_bluetooth));
    }

    public static int getConnectionStateSummary(int i) {
        if (i == 0) {
            return com.android.systemui.R.string.bluetooth_disconnected;
        }
        if (i == 1) {
            return com.android.systemui.R.string.bluetooth_connecting;
        }
        if (i == 2) {
            return com.android.systemui.R.string.bluetooth_connected;
        }
        if (i != 3) {
            return 0;
        }
        return com.android.systemui.R.string.bluetooth_disconnecting;
    }

    public static CachedBluetoothDevice getDeviceForGroupConnectionState(CachedBluetoothDevice cachedBluetoothDevice) {
        int maxConnectionState = cachedBluetoothDevice.getMaxConnectionState();
        if (maxConnectionState == 2) {
            return cachedBluetoothDevice;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : cachedBluetoothDevice.mMemberDevices) {
            if (maxConnectionState != cachedBluetoothDevice2.getMaxConnectionState()) {
                int maxConnectionState2 = cachedBluetoothDevice.getMaxConnectionState();
                int maxConnectionState3 = cachedBluetoothDevice2.getMaxConnectionState();
                if (maxConnectionState2 == 0 ? maxConnectionState3 != 0 : !(maxConnectionState2 == 1 ? maxConnectionState3 != 2 : maxConnectionState2 != 3 || (maxConnectionState3 != 1 && maxConnectionState3 != 2))) {
                    cachedBluetoothDevice = cachedBluetoothDevice2;
                }
                if (cachedBluetoothDevice.getMaxConnectionState() == 2) {
                    break;
                }
            }
        }
        return cachedBluetoothDevice;
    }

    public static Drawable getHostOverlayIconDrawable(Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        int color = "com.android.systemui".equals(context.getPackageName().toLowerCase()) ? context.getResources().getColor(com.android.systemui.R.color.qs_detail_item_device_bt_icon_tint_color) : context.getResources().getColor(com.android.systemui.R.color.bt_device_icon_tint_color);
        if (cachedBluetoothDevice == null) {
            Log.d("BluetoothUtils", "getHostOverlayIconDrawable - cachedBluetoothDevice is null");
            Drawable drawable = context.getResources().getDrawable(com.android.systemui.R.drawable.list_ic_sound_accessory_default);
            drawable.setTint(color);
            return drawable;
        }
        Drawable iconDrawable = cachedBluetoothDevice.getIconDrawable();
        if (isBtCastConnectedAsHost(context, cachedBluetoothDevice.getAddress())) {
            return getOverlayIconTintableDrawable(iconDrawable, context, com.android.systemui.R.drawable.sharing_ic_overlay, com.android.systemui.R.drawable.sharing_ic_tintable);
        }
        iconDrawable.setTint(color);
        return iconDrawable;
    }

    public static Drawable getOverlayIconTintableDrawable(Drawable drawable, Context context, int i, int i2) {
        int color = "com.android.systemui".equals(context.getPackageName().toLowerCase()) ? context.getResources().getColor(com.android.systemui.R.color.qs_detail_item_device_bt_icon_tint_color) : context.getResources().getColor(com.android.systemui.R.color.bt_device_icon_tint_color);
        drawable.setTint(color);
        Bitmap drawableToBitmap = drawableToBitmap(drawable);
        Bitmap drawableToBitmap2 = drawableToBitmap(context.getResources().getDrawable(i));
        Drawable drawable2 = context.getResources().getDrawable(i2);
        drawable2.setTint(color);
        Bitmap drawableToBitmap3 = drawableToBitmap(drawable2);
        Bitmap createBitmap = Bitmap.createBitmap(drawableToBitmap.getWidth(), drawableToBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(drawableToBitmap, 0.0f, 0.0f, (Paint) null);
        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawBitmap(drawableToBitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(drawableToBitmap3, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return new BitmapDrawable(context.getResources(), createBitmap);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0176  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List getRestoredDevices(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, boolean z) {
        ArrayList arrayList;
        ArrayList arrayList2;
        Cursor cursor;
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        Cursor cursor2 = null;
        try {
            try {
                arrayList3 = arrayList6;
                arrayList4 = arrayList5;
                try {
                    cursor = context.getContentResolver().query(ContentProvider.maybeAddUserId(Uri.parse("content://com.samsung.bt.btservice.btsettingsprovider/bonddevice"), ActivityManager.getCurrentUser()), null, "bond_state == 1 OR bond_state == 4", null, "timestamp DESC");
                    try {
                        try {
                        } catch (IllegalStateException e) {
                            e = e;
                            arrayList2 = arrayList3;
                        }
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            Log.e("BluetoothUtils", "getRestoredDevices :: will be cursor close");
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (IllegalStateException e2) {
                    e = e2;
                    arrayList2 = arrayList3;
                    arrayList = arrayList4;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IllegalStateException e3) {
            e = e3;
            arrayList = arrayList5;
            arrayList2 = arrayList6;
        }
        if (cursor == null) {
            Log.e("BluetoothUtils", "getRestoredDevices() :: query return null");
            if (cursor != null) {
                Log.e("BluetoothUtils", "getRestoredDevices :: will be cursor close");
                cursor.close();
            }
            return null;
        }
        Log.e("BluetoothUtils", "getRestoredDevices() :: cursor count: " + cursor.getCount() + ", Columns : " + cursor.getColumnCount());
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex("address");
        int columnIndex2 = cursor.getColumnIndex("name");
        int columnIndex3 = cursor.getColumnIndex("cod");
        int columnIndex4 = cursor.getColumnIndex("bond_state");
        int columnIndex5 = cursor.getColumnIndex("appearance");
        int columnIndex6 = cursor.getColumnIndex("manufacturerdata");
        int columnIndex7 = cursor.getColumnIndex(PhoneRestrictionPolicy.TIMESTAMP);
        int columnIndex8 = cursor.getColumnIndex("linktype");
        int columnIndex9 = cursor.getColumnIndex("uuids");
        while (!cursor.isAfterLast()) {
            BluetoothRestoredDevice bluetoothRestoredDevice = new BluetoothRestoredDevice(context, cursor.getString(columnIndex));
            bluetoothRestoredDevice.mName = cursor.getString(columnIndex2);
            bluetoothRestoredDevice.mCod = cursor.getInt(columnIndex3);
            bluetoothRestoredDevice.mBondState = cursor.getInt(columnIndex4);
            bluetoothRestoredDevice.mAppearance = cursor.getInt(columnIndex5);
            byte[] stringToByte = stringToByte(cursor.getString(columnIndex6));
            bluetoothRestoredDevice.mManufacturerData = stringToByte;
            int i = columnIndex2;
            int i2 = columnIndex3;
            bluetoothRestoredDevice.mTimeStamp = cursor.getLong(columnIndex7);
            bluetoothRestoredDevice.mLinkType = cursor.getInt(columnIndex8);
            String string = cursor.getString(columnIndex9);
            boolean isSyncDevice = isSyncDevice(stringToByte, string);
            String[] stringToken = getStringToken(string);
            if (stringToken != null) {
                bluetoothRestoredDevice.mUuids = makeParcelUuids(stringToken);
            }
            CachedBluetoothDevice cachedBluetoothDevice = new CachedBluetoothDevice(context, localBluetoothProfileManager, bluetoothRestoredDevice, isSyncDevice);
            if (z && isSyncDevice) {
                arrayList2 = arrayList3;
                try {
                    arrayList2.add(cachedBluetoothDevice);
                    arrayList = arrayList4;
                } catch (IllegalStateException e4) {
                    e = e4;
                    arrayList = arrayList4;
                    cursor2 = cursor;
                    Log.e("BluetoothUtils", "getRestoredDevices :: Occurs IllegalStateException");
                    e.printStackTrace();
                    if (cursor2 != null) {
                    }
                    boolean z2 = DEBUG;
                    if (z) {
                    }
                }
            } else {
                arrayList2 = arrayList3;
                arrayList = arrayList4;
                try {
                    arrayList.add(cachedBluetoothDevice);
                } catch (IllegalStateException e5) {
                    e = e5;
                    cursor2 = cursor;
                    Log.e("BluetoothUtils", "getRestoredDevices :: Occurs IllegalStateException");
                    e.printStackTrace();
                    if (cursor2 != null) {
                        cursor = cursor2;
                        Log.e("BluetoothUtils", "getRestoredDevices :: will be cursor close");
                        cursor.close();
                    }
                    boolean z22 = DEBUG;
                    if (z) {
                    }
                }
            }
            cursor.moveToNext();
            arrayList3 = arrayList2;
            arrayList4 = arrayList;
            columnIndex2 = i;
            columnIndex3 = i2;
        }
        arrayList2 = arrayList3;
        arrayList = arrayList4;
        Log.e("BluetoothUtils", "getRestoredDevices :: will be cursor close");
        cursor.close();
        boolean z222 = DEBUG;
        if (z) {
            if (z222) {
                Log.d("BluetoothUtils", "getRestoredDevices :: syncedDevices");
            }
            return arrayList2;
        }
        if (z222) {
            Log.d("BluetoothUtils", "getRestoredDevices :: restoredDevices");
        }
        return arrayList;
    }

    public static String getStringMetaData(BluetoothDevice bluetoothDevice, int i) {
        byte[] metadata;
        if (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(i)) == null) {
            return null;
        }
        return new String(metadata);
    }

    public static String[] getStringToken(String str) {
        if (str == null || "null".equalsIgnoreCase(str) || str.isEmpty()) {
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
        String[] strArr = new String[stringTokenizer.countTokens()];
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            strArr[i] = stringTokenizer.nextToken();
            i++;
        }
        return strArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0011, code lost:
    
        if (r3[r1 + 1] == 1) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean hasGearManufacturerData(byte[] bArr) {
        boolean z;
        if (bArr != null) {
            int length = bArr.length;
            int i = BluetoothManufacturerData.OFFSET_OLD_DEVICE_ID;
            z = length >= i + 2 && bArr[i] == 0;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("hasGearManufacturerData : ", z, "BluetoothUtils");
        return z;
    }

    public static boolean isAdvancedDetailsHeader(BluetoothDevice bluetoothDevice) {
        boolean z;
        boolean z2;
        byte[] metadata;
        if (DeviceConfig.getBoolean("settings_ui", "bt_advanced_header_enabled", true)) {
            z = true;
        } else {
            Log.d("BluetoothUtils", "isAdvancedDetailsHeader: advancedEnabled is false");
            z = false;
        }
        if (!z) {
            return false;
        }
        if ((bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(6)) == null) ? false : Boolean.parseBoolean(new String(metadata))) {
            Log.d("BluetoothUtils", "isAdvancedDetailsHeader: untetheredHeadset is true");
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            return true;
        }
        String stringMetaData = getStringMetaData(bluetoothDevice, 17);
        if (!TextUtils.equals(stringMetaData, "Untethered Headset") && !TextUtils.equals(stringMetaData, "Watch") && !TextUtils.equals(stringMetaData, "Default") && !TextUtils.equals(stringMetaData, "Stylus")) {
            return false;
        }
        AbstractC0000x2c234b15.m3m("isAdvancedDetailsHeader: deviceType is ", stringMetaData, "BluetoothUtils");
        return true;
    }

    public static boolean isBtCastConnectedAsHost(Context context, String str) {
        LocalBluetoothManager localBluetoothManager;
        AudioCastProfile audioCastProfile;
        if (SemBluetoothCastAdapter.isBluetoothCastSupported() && (localBluetoothManager = LocalBluetoothManager.getInstance(context, mOnInitCallback)) != null && (audioCastProfile = localBluetoothManager.mLocalCastProfileManager.mAudioCastProfile) != null) {
            int i = 1;
            if (Settings.Secure.getInt(audioCastProfile.mContext.getContentResolver(), "bluetooth_cast_mode", 1) == 1) {
                SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                List list = (List) (semBluetoothAudioCast == null ? new ArrayList() : semBluetoothAudioCast.getConnectedDevices()).stream().filter(new BluetoothUtils$$ExternalSyntheticLambda0(2)).filter(new BluetoothUtils$$ExternalSyntheticLambda1(audioCastProfile, i)).filter(new BluetoothUtils$$ExternalSyntheticLambda0(3)).map(new BluetoothUtils$$ExternalSyntheticLambda2(1)).collect(Collectors.toList());
                if (!list.isEmpty() && list.contains(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isGalaxyWatchDevice(String str, BluetoothClass bluetoothClass, byte[] bArr, ParcelUuid[] parcelUuidArr) {
        int deviceClass = bluetoothClass == null ? 7936 : bluetoothClass.getDeviceClass();
        if (deviceClass == 7936 || parcelUuidArr == null) {
            if (("SM-V700".equalsIgnoreCase(str) || "Samsung Galaxy Gear".equalsIgnoreCase(str) || str.toLowerCase().startsWith("galaxy gear")) || hasGearManufacturerData(bArr)) {
                return true;
            }
        } else {
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("isGalaxyWatchDevice: uuids = "), Arrays.toString(parcelUuidArr), "BluetoothUtils");
            if (deviceClass == 1796 && (SemBluetoothUuid.isUuidPresent(parcelUuidArr, ParcelUuid.fromString("a49eb41e-cb06-495c-9f4f-bb80a90cdf00")) || SemBluetoothUuid.isUuidPresent(parcelUuidArr, ParcelUuid.fromString("5e8945b0-9525-11e3-a5e2-0800200c9a66")) || hasGearManufacturerData(bArr))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRTL(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 192) == 128;
    }

    public static boolean isSyncDevice(byte[] bArr, String str) {
        String[] stringToken;
        boolean z = DEBUG;
        if (bArr != null) {
            byte[] bArr2 = new ManufacturerData(bArr).mData.mDeviceId;
            int i = bArr2[1] & 255;
            byte b = bArr2[0];
            if (((b == 1 || b == 2 || b == 3) && i >= 1 && i <= 255) || (b == 65 && i >= 1 && i <= 255)) {
                if (z) {
                    Log.d("BluetoothUtils", "isSyncDevice :: DeviceId");
                }
                return true;
            }
        }
        if (str != null && str.length() > 0 && (stringToken = getStringToken(str)) != null) {
            for (String str2 : stringToken) {
                if ("e7ab2241-ca64-4a69-ac02-05f5c6fe2d62".equals(str2)) {
                    if (z) {
                        Log.d("BluetoothUtils", "isSyncDevice :: UUID");
                    }
                    return true;
                }
            }
        }
        if (z) {
            Log.d("BluetoothUtils", "isSyncDevice :: It is not synced device");
        }
        return false;
    }

    public static ParcelUuid[] makeParcelUuids(String[] strArr) {
        ParcelUuid[] parcelUuidArr = new ParcelUuid[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                parcelUuidArr[i] = ParcelUuid.fromString(strArr[i]);
            } catch (Exception e) {
                Log.d("BluetoothUtils", "failed makeParcelUuids");
                e.printStackTrace();
            }
        }
        return parcelUuidArr;
    }

    public static void setQuickPannelOn(boolean z) {
        Log.d("BluetoothUtils", "setQuickPannelOn :: " + z + ", from Dex :: false");
        mQuickPannelOn = z;
        mDexQuickPannelOn = false;
    }

    public static void showToast(final Context context, final String str) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.settingslib.bluetooth.BluetoothUtils.1
            @Override // java.lang.Runnable
            public final void run() {
                Toast.makeText(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.Settings), str, 0).show();
            }
        }, 0L);
    }

    public static byte[] stringToByte(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            try {
                bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
            } catch (NumberFormatException unused) {
                Log.d("BluetoothUtils", "stringToByte : Wrong format - ".concat(str));
                return null;
            }
        }
        return bArr;
    }

    public static void updateDeviceName(Context context) {
        LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), ImIntent.Extras.DEVICE_NAME, -2);
        if (stringForUser == null) {
            stringForUser = Settings.Global.getString(context.getContentResolver(), ImIntent.Extras.DEVICE_NAME);
        }
        if (localBluetoothAdapter == null || stringForUser == null) {
            return;
        }
        BluetoothAdapter bluetoothAdapter = localBluetoothAdapter.mAdapter;
        if (stringForUser.equals(bluetoothAdapter.getName())) {
            return;
        }
        bluetoothAdapter.setName(stringForUser);
        Log.d("BluetoothUtils", "updateDeviceName :: change device name to ".concat(stringForUser));
    }
}
