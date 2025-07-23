package com.android.settingslib.bluetooth;

import android.R;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.SemBluetoothUuid;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.google.common.collect.ImmutableSet;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.sec.ims.configuration.DATA;
import com.sec.ims.im.ImIntent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BluetoothUtils {
    public static final String[] BD_ROTATE_LEFT;
    public static final String[] BD_ROTATE_RIGHT = null;
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final AnonymousClass2 mOnInitCallback;
    public static boolean mQuickPannelOn;

    /* JADX WARN: Type inference failed for: r0v247, types: [com.android.settingslib.bluetooth.BluetoothUtils$2] */
    static {
        ImmutableSet.construct(3, 2, 22, 21);
        List.of(1048, 1028);
        BD_ROTATE_LEFT = new String[]{"00", "02", "04", "06", "08", "0A", "0C", "0E", DATA.DM_FIELD_INDEX.SMS_OVER_IMS, DATA.DM_FIELD_INDEX.SIP_T1_TIMER, DATA.DM_FIELD_INDEX.SIP_T4_TIMER, DATA.DM_FIELD_INDEX.SIP_TB_TIMER, DATA.DM_FIELD_INDEX.SIP_TD_TIMER, "1A", "1C", "1E", DATA.DM_FIELD_INDEX.SIP_TF_TIMER, DATA.DM_FIELD_INDEX.SIP_TH_TIMER, DATA.DM_FIELD_INDEX.SIP_TJ_TIMER, DATA.DM_FIELD_INDEX.CAP_CACHE_EXP, DATA.DM_FIELD_INDEX.SRC_THROTTLE_PUBLISH, "2A", "2C", "2E", DATA.DM_FIELD_INDEX.LVC_BETA_SETTING, DATA.DM_FIELD_INDEX.AVAIL_CACHE_EXP, DATA.DM_FIELD_INDEX.FQDN_FOR_PCSCF, DATA.DM_FIELD_INDEX.PUBLISH_TIMER, DATA.DM_FIELD_INDEX.GZIP_FLAG, "3A", "3C", "3E", DATA.DM_FIELD_INDEX.T_DELAY, DATA.DM_FIELD_INDEX.MIN_SE, DATA.DM_FIELD_INDEX.SILENT_REDIAL_ENABLE, DATA.DM_FIELD_INDEX.PUBLISH_ERR_RETRY_TIMER, DATA.DM_FIELD_INDEX.RINGING_TIMER, "4A", "4C", "4E", DATA.DM_FIELD_INDEX.RTP_RTCP_TIMER, DATA.DM_FIELD_INDEX.URI_MEDIA_RSC_SERV_3WAY_CALL, DATA.DM_FIELD_INDEX.CAP_DISCOVERY, DATA.DM_FIELD_INDEX.SRC_AMR, DATA.DM_FIELD_INDEX.HD_VOICE, "5A", "5C", "5E", DATA.DM_FIELD_INDEX.AUDIO_RTP_PORT_START, DATA.DM_FIELD_INDEX.VIDEO_RTP_PORT_START, DATA.DM_FIELD_INDEX.AMR_WB_OCTET_ALIGNED, DATA.DM_FIELD_INDEX.AMR_OCTET_ALIGNED, DATA.DM_FIELD_INDEX.H264_VGA, "6A", "6C", "6E", DATA.DM_FIELD_INDEX.DTMF_WB, DATA.DM_FIELD_INDEX.VOLTE_PREF_SERVICE_STATUS, DATA.DM_FIELD_INDEX.DM_APP_ID, DATA.DM_FIELD_INDEX.DM_CON_REF, DATA.DM_FIELD_INDEX.ICSI, "7A", "7C", "7E", DATA.DM_FIELD_INDEX.RSC_ALLOC_MODE, DATA.DM_FIELD_INDEX.VOICE_DOMAIN_PREF_UTRAN, DATA.DM_FIELD_INDEX.REG_RETRY_BASE_TIME, DATA.DM_FIELD_INDEX.PHONE_CONTEXT_PARAM, DATA.DM_FIELD_INDEX.SS_DOMAIN_SETTING, "8A", "8C", "8E", DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD, DATA.DM_FIELD_INDEX.CONF_FACTORY_URI, DATA.DM_FIELD_INDEX.LVC_ENABLED, DATA.DM_FIELD_INDEX.VOLTE_ENABLED_BY_USER, DATA.DM_FIELD_INDEX.USSD_CONTROL_PREF, "9A", "9C", "9E", "A0", "A2", "A4", "A6", "A8", "AA", "AC", "AE", "B0", "B2", "B4", "B6", "B8", "BA", "BC", "BE", "C0", "C2", "C4", "C6", "C8", "CA", "CC", "CE", "D0", "D2", "D4", "D6", "D8", "DA", "DC", "DE", "E0", "E2", "E4", "E6", "E8", "EA", CertProvisionProfile.KEY_TYPE_EC, "EE", "F0", "F2", "F4", "F6", "F8", "FA", "FC", "FE", "01", "03", "05", "07", "09", "0B", "0D", "0F", DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, DATA.DM_FIELD_INDEX.SIP_T2_TIMER, DATA.DM_FIELD_INDEX.SIP_TA_TIMER, DATA.DM_FIELD_INDEX.SIP_TC_TIMER, DATA.DM_FIELD_INDEX.SIP_TE_TIMER, "1B", PeripheralBarcodeConstants.Symbology.Type.TYPE_1D, "1F", DATA.DM_FIELD_INDEX.SIP_TG_TIMER, DATA.DM_FIELD_INDEX.SIP_TI_TIMER, DATA.DM_FIELD_INDEX.SIP_TK_TIMER, DATA.DM_FIELD_INDEX.CAP_POLL_INTERVAL, DATA.DM_FIELD_INDEX.SUBSCRIBE_MAX_ENTRY, "2B", PeripheralBarcodeConstants.Symbology.Type.TYPE_2D, "2F", DATA.DM_FIELD_INDEX.EAB_SETTING, DATA.DM_FIELD_INDEX.PREF_CSCF_PORT, DATA.DM_FIELD_INDEX.POLL_LIST_SUB_EXP, DATA.DM_FIELD_INDEX.PUBLISH_TIMER_EXTEND, DATA.DM_FIELD_INDEX.TIMER_VZW, "3B", "3D", "3F", DATA.DM_FIELD_INDEX.IMS_TEST_MODE, DATA.DM_FIELD_INDEX.DCN_NUMBER, DATA.DM_FIELD_INDEX.T_LTE_911_FAIL, DATA.DM_FIELD_INDEX.SPEAKER_DEFAULT_VIDEO, DATA.DM_FIELD_INDEX.RINGBACK_TIMER, "4B", "4D", "4F", DATA.DM_FIELD_INDEX.DOMAIN_PUI, "53", DATA.DM_FIELD_INDEX.AMR_WB, DATA.DM_FIELD_INDEX.SRC_AMR_WB, DATA.DM_FIELD_INDEX.UDP_KEEP_ALIVE, "5B", "5D", "5F", DATA.DM_FIELD_INDEX.AUDIO_RTP_PORT_END, DATA.DM_FIELD_INDEX.VIDEO_RTP_PORT_END, DATA.DM_FIELD_INDEX.AMR_WB_BANDWITH_EFFICIENT, DATA.DM_FIELD_INDEX.AMR_BANDWITH_EFFICIENT, DATA.DM_FIELD_INDEX.H264_QVGA, "6B", "6D", "6F", DATA.DM_FIELD_INDEX.DTMF_NB, DATA.DM_FIELD_INDEX.SMS_PSI, DATA.DM_FIELD_INDEX.DM_USER_DISP_NAME, DATA.DM_FIELD_INDEX.PDP_CONTEXT_PREF, DATA.DM_FIELD_INDEX.ICSI_RSC_ALLOC_MODE, "7B", "7D", "7F", DATA.DM_FIELD_INDEX.VOICE_DOMAIN_PREF_EUTRAN, DATA.DM_FIELD_INDEX.IMS_VOICE_TERMINATION, DATA.DM_FIELD_INDEX.REG_RETRY_MAX_TIME, DATA.DM_FIELD_INDEX.PHONE_CONTEXT_PUID, DATA.DM_FIELD_INDEX.SS_CONTROL_PREF, "8B", "8D", "8F", DATA.DM_FIELD_INDEX.ICCID, DATA.DM_FIELD_INDEX.VOLTE_ENABLED, DATA.DM_FIELD_INDEX.EAB_SETTING_BY_USER, DATA.DM_FIELD_INDEX.LVC_ENABLED_BY_USER, DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF, "9B", "9D", "9F", "A1", "A3", "A5", "A7", "A9", "AB", "AD", "AF", "B1", "B3", "B5", "B7", "B9", "BB", "BD", "BF", "C1", "C3", "C5", "C7", "C9", "CB", "CD", "CF", "D1", "D3", "D5", "D7", "D9", "DB", "DD", "DF", "E1", "E3", "E5", "E7", "E9", "EB", "ED", "EF", "F1", "F3", "F5", "F7", "F9", "FB", "FD", "FF"};
        mOnInitCallback = new Object() { // from class: com.android.settingslib.bluetooth.BluetoothUtils.2
        };
    }

    public static boolean compareSameWithGear(String str, String str2) {
        byte[] bArr = new byte[6];
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            if (str.charAt(i) != ':') {
                bArr[i2] = (byte) Integer.parseInt(str.substring(i, i + 2), 16);
                i2++;
                i++;
            }
            i++;
        }
        if (String.format(Locale.US, "%02X", Byte.valueOf((byte) (bArr[0] | 192))).equals(str2.substring(0, 2))) {
            for (int i3 = 1; i3 < 6; i3++) {
                int i4 = i3 * 3;
                if (BD_ROTATE_LEFT[bArr[i3] & 255].equals(str2.substring(i4, i4 + 2))) {
                }
            }
            return true;
        }
        return false;
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

    public static boolean getBooleanMetaData(BluetoothDevice bluetoothDevice) {
        byte[] metadata;
        if (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(6)) == null) {
            return false;
        }
        return Boolean.parseBoolean(new String(metadata));
    }

    public static Pair getBtClassDrawableWithDescription(Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothClass bluetoothClass = cachedBluetoothDevice.mDevice.getBluetoothClass();
        if (bluetoothClass != null) {
            int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
            if (majorDeviceClass == 256) {
                return new Pair(context.getDrawable(R.drawable.ic_doc_presentation), context.getString(com.android.systemui.R.string.bluetooth_talkback_computer));
            }
            if (majorDeviceClass == 512) {
                return new Pair(context.getDrawable(R.drawable.input_method_fullscreen_background), context.getString(com.android.systemui.R.string.bluetooth_talkback_phone));
            }
            if (majorDeviceClass == 1280) {
                return new Pair(context.getDrawable(HidProfile.getHidClassDrawable(bluetoothClass)), context.getString(com.android.systemui.R.string.bluetooth_talkback_input_peripheral));
            }
            if (majorDeviceClass == 1536) {
                return new Pair(context.getDrawable(R.drawable.jog_tab_bar_right_end_confirm_red), context.getString(com.android.systemui.R.string.bluetooth_talkback_imaging));
            }
        }
        if (cachedBluetoothDevice.isHearingAidDevice()) {
            return new Pair(context.getDrawable(R.drawable.ic_doc_powerpoint), context.getString(com.android.systemui.R.string.bluetooth_talkback_hearing_aids));
        }
        int i = 0;
        for (LocalBluetoothProfile localBluetoothProfile : cachedBluetoothDevice.getProfiles()) {
            int drawableResource = localBluetoothProfile.getDrawableResource(bluetoothClass);
            if (drawableResource != 0) {
                if ((localBluetoothProfile instanceof HearingAidProfile) || (localBluetoothProfile instanceof HapClientProfile)) {
                    return new Pair(context.getDrawable(drawableResource), context.getString(com.android.systemui.R.string.bluetooth_talkback_hearing_aids));
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
                return new Pair(context.getDrawable(R.drawable.ic_doc_pdf), context.getString(com.android.systemui.R.string.bluetooth_talkback_headset));
            }
            if (bluetoothClass.doesClassMatch(1)) {
                return new Pair(context.getDrawable(R.drawable.ic_doc_image), context.getString(com.android.systemui.R.string.bluetooth_talkback_headphone));
            }
        }
        return new Pair(context.getDrawable(R.drawable.jog_tab_bar_right_end_confirm_gray).mutate(), context.getString(com.android.systemui.R.string.bluetooth_talkback_bluetooth));
    }

    public static Pair getBtDrawableWithDescription(Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        byte[] metadata;
        Pair btClassDrawableWithDescription = getBtClassDrawableWithDescription(context, cachedBluetoothDevice);
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.bt_nearby_icon_size);
        Resources resources = context.getResources();
        if (isAdvancedDetailsHeader(bluetoothDevice)) {
            String str = (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(5)) == null) ? null : new String(metadata);
            Uri parse = str != null ? Uri.parse(str) : null;
            if (parse != null) {
                try {
                    context.getContentResolver().takePersistableUriPermission(parse, 1);
                } catch (SecurityException e) {
                    Log.e("BluetoothUtils", "Failed to take persistable permission for: " + parse, e);
                }
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), parse);
                    if (bitmap != null) {
                        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, dimensionPixelSize, dimensionPixelSize, false);
                        bitmap.recycle();
                        return new Pair(new BitmapDrawable(resources, createScaledBitmap), (String) btClassDrawableWithDescription.second);
                    }
                } catch (IOException e2) {
                    Log.e("BluetoothUtils", "Failed to get drawable for: " + parse, e2);
                } catch (SecurityException e3) {
                    Log.e("BluetoothUtils", "Failed to get permission for: " + parse, e3);
                }
            }
        }
        return new Pair((Drawable) btClassDrawableWithDescription.first, (String) btClassDrawableWithDescription.second);
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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0032, code lost:
    
        r8 = r3;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0037 A[EDGE_INSN: B:22:0x0037->B:23:0x0037 BREAK  A[LOOP:0: B:6:0x000c->B:25:0x000c], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x000c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.settingslib.bluetooth.CachedBluetoothDevice getDeviceForGroupConnectionState(com.android.settingslib.bluetooth.CachedBluetoothDevice r8) {
        /*
            int r0 = r8.mCachedMaxConnectionState
            r1 = 2
            if (r0 != r1) goto L6
            return r8
        L6:
            java.util.Set r2 = r8.mMemberDevices
            java.util.Iterator r2 = r2.iterator()
        Lc:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L37
            java.lang.Object r3 = r2.next()
            com.android.settingslib.bluetooth.CachedBluetoothDevice r3 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r3
            int r4 = r3.mCachedMaxConnectionState
            if (r0 != r4) goto L1d
            goto Lc
        L1d:
            int r5 = r8.mCachedMaxConnectionState
            if (r5 == 0) goto L30
            r6 = 1
            if (r5 == r6) goto L2d
            r7 = 3
            if (r5 == r7) goto L28
            goto L33
        L28:
            if (r4 == r6) goto L32
            if (r4 != r1) goto L33
            goto L32
        L2d:
            if (r4 != r1) goto L33
            goto L32
        L30:
            if (r4 == 0) goto L33
        L32:
            r8 = r3
        L33:
            int r3 = r8.mCachedMaxConnectionState
            if (r3 != r1) goto Lc
        L37:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.BluetoothUtils.getDeviceForGroupConnectionState(com.android.settingslib.bluetooth.CachedBluetoothDevice):com.android.settingslib.bluetooth.CachedBluetoothDevice");
    }

    public static String getFastPairCustomizedField(BluetoothDevice bluetoothDevice, String str) {
        byte[] metadata;
        String str2 = (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(25)) == null) ? null : new String(metadata);
        if (!TextUtils.isEmpty(str2)) {
            StringBuilder sb = new StringBuilder();
            Locale locale = Locale.ENGLISH;
            sb.append("<" + str + ">");
            sb.append("(.*?)");
            sb.append("</" + str + ">");
            Matcher matcher = Pattern.compile(sb.toString()).matcher(str2);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    public static int getIntMetaData(BluetoothDevice bluetoothDevice, int i) {
        byte[] metadata;
        if (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(i)) == null) {
            return -1;
        }
        try {
            return Integer.parseInt(new String(metadata));
        } catch (NumberFormatException unused) {
            return -1;
        }
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

    /* JADX WARN: Removed duplicated region for block: B:30:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0175  */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List getRestoredDevices(android.content.Context r26, com.android.settingslib.bluetooth.LocalBluetoothProfileManager r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 390
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.BluetoothUtils.getRestoredDevices(android.content.Context, com.android.settingslib.bluetooth.LocalBluetoothProfileManager, boolean):java.util.List");
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean hasGearManufacturerData(byte[] r3) {
        /*
            if (r3 == 0) goto L14
            int r0 = r3.length
            int r1 = android.bluetooth.BluetoothManufacturerData.OFFSET_OLD_DEVICE_ID
            int r2 = r1 + 2
            if (r0 < r2) goto L14
            r0 = r3[r1]
            if (r0 != 0) goto L14
            r0 = 1
            int r1 = r1 + r0
            r3 = r3[r1]
            if (r3 != r0) goto L14
            goto L15
        L14:
            r0 = 0
        L15:
            java.lang.String r3 = "hasGearManufacturerData : "
            java.lang.String r1 = "BluetoothUtils"
            com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0.m(r3, r1, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.BluetoothUtils.hasGearManufacturerData(byte[]):boolean");
    }

    public static boolean isActiveMediaDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        return cachedBluetoothDevice.isActiveDevice(2) || cachedBluetoothDevice.isActiveDevice(1) || cachedBluetoothDevice.isActiveDevice(21) || cachedBluetoothDevice.isActiveDevice(22);
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
        if (z) {
            if (getBooleanMetaData(bluetoothDevice)) {
                Log.d("BluetoothUtils", "isAdvancedDetailsHeader: untetheredHeadset is true");
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                return true;
            }
            String str = (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(5)) == null) ? null : new String(metadata);
            if ((str != null ? Uri.parse(str) : null) != null) {
                Log.d("BluetoothUtils", "isAdvancedDetailsHeader is true with main icon uri");
                return true;
            }
        }
        return false;
    }

    public static boolean isAvailableHearingDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        if (!isDeviceConnected(cachedBluetoothDevice) || !cachedBluetoothDevice.isConnectedHearingAidDevice()) {
            return false;
        }
        Log.d("BluetoothUtils", "isFilterMatched() device : " + cachedBluetoothDevice.getName() + ", the profile is connected.");
        return true;
    }

    public static boolean isAvailableMediaBluetoothDevice(CachedBluetoothDevice cachedBluetoothDevice, boolean z) {
        HeadsetProfile headsetProfile;
        char c = z ? (char) 1 : (char) 2;
        if (isDeviceConnected(cachedBluetoothDevice)) {
            if (cachedBluetoothDevice.isConnectedAshaHearingAidDevice() || cachedBluetoothDevice.isConnectedLeAudioDevice()) {
                Log.d("BluetoothUtils", "isFilterMatched() device : " + cachedBluetoothDevice.getName() + ", the profile is connected.");
                return true;
            }
            if (c == 1) {
                LocalBluetoothProfileManager localBluetoothProfileManager = cachedBluetoothDevice.mProfileManager;
                return (localBluetoothProfileManager == null || (headsetProfile = localBluetoothProfileManager.mHeadsetProfile) == null || headsetProfile.getConnectionStatus(cachedBluetoothDevice.mDevice) != 2) ? false : true;
            }
            if (c == 2) {
                return cachedBluetoothDevice.isConnectedA2dpDevice();
            }
        }
        return false;
    }

    public static boolean isBtCastConnectedAsHost(Context context, String str) {
        LocalBluetoothManager localBluetoothManager;
        AudioCastProfile audioCastProfile;
        if (!SemBluetoothCastAdapter.isBluetoothCastSupported() || (localBluetoothManager = LocalBluetoothManager.getInstance(context, mOnInitCallback)) == null || (audioCastProfile = localBluetoothManager.mLocalCastProfileManager.mAudioCastProfile) == null || Settings.Secure.getInt(audioCastProfile.mContext.getContentResolver(), "bluetooth_cast_mode", 1) != 1) {
            return false;
        }
        List list = (List) audioCastProfile.getConnectedDevices().stream().filter(new BluetoothUtils$$ExternalSyntheticLambda0(2)).filter(new BluetoothUtils$$ExternalSyntheticLambda1(audioCastProfile, 1)).filter(new BluetoothUtils$$ExternalSyntheticLambda0(3)).map(new BluetoothUtils$$ExternalSyntheticLambda3()).collect(Collectors.toList());
        return !list.isEmpty() && list.contains(str);
    }

    public static boolean isDeviceConnected(CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        return bluetoothDevice.getBondState() == 12 && bluetoothDevice.isConnected();
    }

    public static boolean isExclusivelyManagedBluetoothDevice(Context context, BluetoothDevice bluetoothDevice) {
        String str;
        boolean z;
        byte[] metadata = bluetoothDevice.getMetadata(29);
        if (metadata == null) {
            Log.d("BluetoothUtils", "Bluetooth device " + bluetoothDevice.getName() + " doesn't have exclusive manager");
            str = null;
        } else {
            str = new String(metadata);
        }
        if (str == null) {
            return false;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            str = unflattenFromString.getPackageName();
        }
        try {
            z = context.getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (PackageManager.NameNotFoundException unused) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Package ", str, " is not installed/enabled", "BluetoothUtils");
            z = false;
        }
        if (!z) {
            return false;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Found exclusively managed app ", str, "BluetoothUtils");
        return true;
    }

    public static boolean isGalaxyWatchDevice(String str, BluetoothClass bluetoothClass, byte[] bArr, ParcelUuid[] parcelUuidArr) {
        int deviceClass = bluetoothClass == null ? 7936 : bluetoothClass.getDeviceClass();
        if (deviceClass == 7936 || parcelUuidArr == null) {
            return "SM-V700".equalsIgnoreCase(str) || "Samsung Galaxy Gear".equalsIgnoreCase(str) || str.toLowerCase().startsWith("galaxy gear") || hasGearManufacturerData(bArr);
        }
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("isGalaxyWatchDevice: uuids = "), Arrays.toString(parcelUuidArr), "BluetoothUtils");
        if (deviceClass == 1796) {
            return SemBluetoothUuid.isUuidPresent(parcelUuidArr, ParcelUuid.fromString("a49eb41e-cb06-495c-9f4f-bb80a90cdf00")) || SemBluetoothUuid.isUuidPresent(parcelUuidArr, ParcelUuid.fromString("5e8945b0-9525-11e3-a5e2-0800200c9a66")) || hasGearManufacturerData(bArr);
        }
        return false;
    }

    public static boolean isRTL(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 192) == 128;
    }

    public static boolean isSyncDevice(String str, byte[] bArr) {
        String[] stringToken;
        boolean z = DEBUG;
        if (bArr != null) {
            byte[] bArr2 = new ManufacturerData(bArr).mData.mDeviceId;
            int i = bArr2[1] & 255;
            byte b = bArr2[0];
            if (((b == 1 || b == 2 || b == 3) && i >= 1 && i <= 255) || (b == 65 && i >= 1 && i <= 255)) {
                if (z) {
                    Log.d("BluetoothUtils", "isSyncDevice :: DeviceId");
                    return true;
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

    public static void setQuickPannelOn(boolean z) {
        Log.d("BluetoothUtils", "setQuickPannelOn :: " + z + ", from Dex :: false");
        mQuickPannelOn = z;
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
        if (localBluetoothAdapter == null || stringForUser == null || stringForUser.equals(localBluetoothAdapter.mAdapter.getName())) {
            return;
        }
        localBluetoothAdapter.mAdapter.setName(stringForUser);
        Log.d("BluetoothUtils", "updateDeviceName :: change device name to ".concat(stringForUser));
    }
}
