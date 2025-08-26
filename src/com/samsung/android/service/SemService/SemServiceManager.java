package com.samsung.android.service.SemService;

import android.content.Context;
import android.content.Intent;
import android.hardware.gnss.GnssSignalType;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import com.android.internal.midi.MidiConstants;
import com.android.server.SecureKeyConst;
import com.samsung.android.service.SemService.ISemService;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes6.dex */
public final class SemServiceManager {
    public static final int ERROR = -1;
    public static final int ERROR_ADD_DEL_LIST = -16;
    public static final int ERROR_BOOT_DEACT = -17;
    public static final int ERROR_CLASS_NOT_FOUND = -2;
    public static final int ERROR_DATA = -13;
    public static final int ERROR_DEACTIVATION = -18;
    public static final int ERROR_EXCEPTION = -90;
    public static final int ERROR_FACTORY_ERROR = -10;
    public static final int ERROR_NOT_SUPPORTED = -10;
    public static final int ERROR_NO_PERMISSION = -91;
    public static final String ERROR_NO_PERMISSION_STRING = null;
    public static final int ERROR_NO_SERVICE = -92;
    public static final int ERROR_SELECT_ERROR = -11;
    public static final int ERROR_SEND_ERROR = -12;
    public static final int ERROR_UNSAT_LINK = -3;
    public static final int ESESTATUS_BUSY = -200;
    public static final int ESESTATUS_NOT_SUPPORTED = -100;
    private static final int MAX_CAPDU_SIZE = 65545;
    private static final int MAX_RAPDU_SIZE = 65538;
    public static final int NO_ERROR = 0;
    public static final int NO_ERROR_SPI = 0;
    public static final int SSD_NOT_EXIST_APPLET_EXIST = 5;
    public static final int SSD_NOT_EXIST_APPLET_NOT_EXIST = 4;
    public static final int SSD_NOT_SELECTABLE_APPLET_EXIST = 2;
    public static final int SSD_NOT_SELECTABLE_APPLET_NOT_EXIST = 3;
    public static final int SSD_NOT_SELECTABLE_APPLET_NOT_IN_SSD = 6;
    public static final int SSD_SELECTABLE_APPLET_EXIST = 0;
    public static final int SSD_SELECTABLE_APPLET_NOT_EXIST = 1;
    private static final String TAG = "SEC_ESE_ServiceManager";
    public static final boolean isSupportSemService = true;
    private static final int normalSpi_Flag = 0;
    private static final int secureSpi_Flag = 1;
    private Context mContext;
    private ISemService mSemService;
    private String skuChipName = "";
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', DateFormat.DAY, 'F'};
    private static boolean isSupportSemServiceManager = true;
    private static String cosName = "JCOP7.1U";
    private static String chipVendor = "NXP";

    public int accessControlForCOSU(int i) {
        return 0;
    }

    public SemServiceManager(Context context) {
        this.mContext = context;
        if (cosName.contains("E")) {
            isSupportSemServiceManager = false;
        }
        if (chipVendor.contains("SKU")) {
            readSkuProperty();
            if (!this.skuChipName.contains("ese")) {
                isSupportSemServiceManager = false;
            }
        }
        if (isSupportSemServiceManager) {
            ISemService iSemServiceAsInterface = ISemService.Stub.asInterface(ServiceManager.getService("SemService"));
            this.mSemService = iSemServiceAsInterface;
            if (iSemServiceAsInterface == null) {
                Log.w(TAG, this.mContext.getPackageName() + " connects to SemService is failed.");
                return;
            }
            Log.i(TAG, this.mContext.getPackageName() + " connects to SemService.");
            return;
        }
        Log.w(TAG, "SemService is not supported");
    }

    private void readSkuProperty() {
        try {
            this.skuChipName = SystemProperties.get("ro.boot.hardware.sku");
        } catch (Exception unused) {
            Log.e(TAG, "failed to get sysProp: ro.boot.hardware.sku");
        }
    }

    public boolean isConnected() {
        if (isSupportSemServiceManager) {
            return this.mSemService != null;
        }
        Log.i(TAG, "SemService is not supported");
        return false;
    }

    public String get_ESEA() {
        Log.i(TAG, "get_ESEA() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        try {
            return this.mSemService.get_ESEA();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (NoClassDefFoundError e2) {
            e2.printStackTrace();
            return null;
        } catch (NullPointerException e3) {
            Log.e(TAG, "Failed to connect service.");
            e3.printStackTrace();
            return null;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public String getCPLC14mode() {
        Log.i(TAG, "getCPLC14mode() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        try {
            return this.mSemService.getCPLC14mode();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (NoClassDefFoundError e2) {
            e2.printStackTrace();
            return null;
        } catch (NullPointerException e3) {
            Log.e(TAG, "Failed to connect service.");
            e3.printStackTrace();
            return null;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public void semFactory() {
        Log.i(TAG, "semFactory() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return;
        }
        try {
            this.mSemService.sem_factory();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e2) {
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            Log.e(TAG, "Failed to connect service.");
            e3.printStackTrace();
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
        }
    }

    private boolean isSupportEsek() {
        try {
            String str = SystemProperties.get("ro.security.ese.support_esek");
            if (!TextUtils.isEmpty(str)) {
                if (str.equals("1")) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            Log.e(TAG, "failed to get sysProp: ro.security.ese.support_esek");
            return false;
        }
    }

    public int esekCertificateCheck() {
        Log.i(TAG, "esekCertificateCheck() is called.");
        if (!isSupportEsek()) {
            Log.i(TAG, "eSEK is not supported");
            return -10;
        }
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.esek_certificate_check();
        } catch (Exception e) {
            e.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e2) {
            e2.printStackTrace();
            return -90;
        } catch (NullPointerException e3) {
            Log.e(TAG, "Failed to connect service.");
            e3.printStackTrace();
            return -92;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int scp11CertificateCheck() {
        Log.i(TAG, "scp11CertificateCheck() is called.");
        if (!isSupportEsek()) {
            Log.i(TAG, "eSEK is not supported");
            return -10;
        }
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.scp11_certificate_check();
        } catch (Exception e) {
            e.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e2) {
            e2.printStackTrace();
            return -90;
        } catch (NullPointerException e3) {
            Log.e(TAG, "Failed to connect service.");
            e3.printStackTrace();
            return -92;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int sem_handleCCMScp11c(byte[] bArr, int i) {
        Log.i(TAG, "sem_handleCCMScp11c() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -10;
        }
        try {
            return this.mSemService.handle_CCMScp11c(bArr, i);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public String[] sem_handleCCM(byte[] bArr, int i) {
        Log.i(TAG, "sem_handleCCM() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        try {
            return this.mSemService.handle_CCM(bArr, i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (NoClassDefFoundError e2) {
            e2.printStackTrace();
            return null;
        } catch (NullPointerException e3) {
            Log.e(TAG, "Failed to connect service.");
            e3.printStackTrace();
            return null;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public String[] sem_handleCCMCB(byte[] bArr, int i, byte[] bArr2, int i2) {
        Log.i(TAG, "sem_handleCCMCB() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        try {
            String[] strArrHandle_CCMCB = this.mSemService.handle_CCMCB(bArr, i, bArr2, i2);
            Log.i(TAG, "ccmData : " + bytesToHex(Arrays.copyOf(bArr2, i2)));
            Log.i(TAG, "ccmDataLen : " + i2);
            return strArrHandle_CCMCB;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e2) {
            Log.e(TAG, "Failed to connect service.");
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public int isLccmSwp() {
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.isLccmSwp();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            return -90;
        } catch (NullPointerException e2) {
            Log.e(TAG, "Failed to connect service.");
            e2.printStackTrace();
            return -92;
        } catch (Exception e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int getHQMMemory(byte[] bArr) {
        Log.i(TAG, "getHQMMemory() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.get_HQMMemory(bArr);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int deactivateCards(int i, String[] strArr, int[] iArr, int i2) {
        Log.i(TAG, "deactivateCards is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.deactivate_Cards(i, strArr, iArr, i2);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int deactivateCardsAID(int i, int i2, String[] strArr, int[] iArr, int i3) {
        Log.i(TAG, "deactivateCardsAID is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.deactivate_CardsAID(i, i2, strArr, iArr, i3);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int getSCRSVersion() throws NumberFormatException {
        String str;
        String str2;
        byte[] bArr = {0, -92, 4, 0, 9, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 1, 81, 67, 82, 83, 0};
        byte[] bArr2 = {Byte.MIN_VALUE, -54, 0, -16, 0};
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        int i = -90;
        boolean z = false;
        try {
            str = SystemProperties.get("ro.factory.factory_binary");
        } catch (Error e) {
            Log.e(TAG, "Error " + e);
        } catch (NullPointerException e2) {
            Log.e(TAG, "Failed to connect service. " + e2);
        } catch (Exception e3) {
            Log.e(TAG, "Exception " + e3);
        } catch (NoClassDefFoundError e4) {
            Log.e(TAG, "Failed to find class." + e4);
        } catch (UnsatisfiedLinkError unused) {
            Log.e(TAG, "Failed to link.");
        }
        if (str != null && "factory".equals(str)) {
            Log.i(TAG, "FACTORY NOT SUPPORT");
            return -10;
        }
        int iOpen = open();
        if (iOpen != 0) {
            Log.e(TAG, "OPEN Error " + iOpen);
            return iOpen;
        }
        z = true;
        byte[] bArrSend = send(bArr);
        if (bArrSend != null && bArrSend.length >= 2) {
            int length = bArrSend.length;
            StringBuilder sb = new StringBuilder("Select SW : ");
            int i2 = length - 2;
            sb.append(byteToHex(bArrSend[i2]));
            int i3 = length - 1;
            sb.append(byteToHex(bArrSend[i3]));
            Log.i(TAG, sb.toString());
            if (length >= 2 && bArrSend[i2] == -112 && bArrSend[i3] == 0) {
                byte[] bArrSend2 = send(bArr2);
                int i4 = -12;
                if (bArrSend2 != null && bArrSend2.length >= 2) {
                    int length2 = bArrSend2.length;
                    StringBuilder sb2 = new StringBuilder("RSP SW : ");
                    int i5 = length2 - 2;
                    sb2.append(byteToHex(bArrSend2[i5]));
                    int i6 = length2 - 1;
                    sb2.append(byteToHex(bArrSend2[i6]));
                    Log.i(TAG, sb2.toString());
                    if (length2 > 3 && bArrSend2[i5] == -112 && bArrSend2[i6] == 0) {
                        str2 = byteToHex(bArrSend2[2]) + byteToHex(bArrSend2[3]);
                        if (str2 != null) {
                            i4 = Integer.parseInt(str2);
                        } else {
                            Log.e(TAG, "Parse Version Error");
                            i4 = -13;
                        }
                    } else {
                        Log.e(TAG, "Send Fail " + bytesToHex(bArrSend2));
                        str2 = null;
                    }
                    Log.i(TAG, "SCRS Version : " + str2);
                    i = i4;
                }
                Log.e(TAG, "Send Error");
                close();
                return -12;
            }
            Log.e(TAG, "Select Fail " + bytesToHex(bArrSend));
            i = -11;
            if (z) {
                close();
            }
            return i;
        }
        Log.e(TAG, "Select Error");
        close();
        return -11;
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x020b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int addSCRSList(String str, ArrayList<String> arrayList) {
        int i;
        int i2;
        byte[] bArrSend;
        byte[] bArr = {0, -92, 4, 0, 9, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 1, 81, 67, 82, 83, 0};
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        int i3 = 0;
        try {
        } catch (NoClassDefFoundError e) {
            e = e;
        } catch (Error e2) {
            e = e2;
        } catch (NullPointerException e3) {
            e = e3;
        } catch (Exception e4) {
            e = e4;
        } catch (UnsatisfiedLinkError e5) {
            e = e5;
        }
        if (arrayList == null || str == null) {
            Log.e(TAG, "AID/Flag Null Error");
            return -13;
        }
        int iOpen = open();
        if (iOpen != 0) {
            Log.e(TAG, "OPEN Error " + iOpen);
            return iOpen;
        }
        try {
            bArrSend = send(bArr);
        } catch (Exception e6) {
            e = e6;
            i3 = 1;
            Log.e(TAG, "Exception " + e);
            i = i3;
            i2 = -90;
            if (i != 0) {
            }
            return i2;
        } catch (NoClassDefFoundError e7) {
            e = e7;
            i3 = 1;
            Log.e(TAG, "Failed to find class." + e);
            i = i3;
            i2 = -90;
            if (i != 0) {
            }
            return i2;
        } catch (Error e8) {
            e = e8;
            i3 = 1;
            Log.e(TAG, "Error " + e);
            i = i3;
            i2 = -90;
            if (i != 0) {
            }
            return i2;
        } catch (NullPointerException e9) {
            e = e9;
            i3 = 1;
            Log.e(TAG, "Failed to connect service." + e);
            i = i3;
            i2 = -90;
            if (i != 0) {
            }
            return i2;
        } catch (UnsatisfiedLinkError e10) {
            e = e10;
            i3 = 1;
            Log.e(TAG, "Failed to link." + e);
            i = i3;
            i2 = -90;
            if (i != 0) {
            }
            return i2;
        }
        if (bArrSend != null && bArrSend.length >= 2) {
            int length = bArrSend.length;
            StringBuilder sb = new StringBuilder("Select SW : ");
            int i4 = length - 2;
            sb.append(byteToHex(bArrSend[i4]));
            int i5 = length - 1;
            sb.append(byteToHex(bArrSend[i5]));
            Log.i(TAG, sb.toString());
            if (length >= 2 && bArrSend[i4] == -112 && bArrSend[i5] == 0) {
                Log.i(TAG, "LS : " + arrayList.size());
                int i6 = 0;
                while (i3 < arrayList.size()) {
                    if (arrayList.get(i3) == null) {
                        Log.e(TAG, "AID Data Error");
                    } else {
                        String str2 = arrayList.get(i3);
                        String str3 = String.format("%02x", Integer.valueOf(str2.length() / 2));
                        String str4 = (("80F600" + String.format("%02x", Integer.valueOf(Integer.parseInt(str)))) + str3) + str2;
                        byte[] bArrHexToBytes = hexToBytes(str4);
                        Log.d(TAG, "AID : " + str4);
                        byte[] bArrSend2 = send(bArrHexToBytes);
                        if (bArrSend2 == null || bArrSend2.length < 2) {
                            Log.e(TAG, "Send Error");
                        } else {
                            int length2 = bArrSend2.length;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("RSP SW : ");
                            int i7 = length2 - 2;
                            sb2.append(byteToHex(bArrSend2[i7]));
                            int i8 = length2 - 1;
                            sb2.append(byteToHex(bArrSend2[i8]));
                            Log.i(TAG, sb2.toString());
                            if (length2 >= 2 && bArrSend2[i7] == -112 && bArrSend2[i8] == 0) {
                                Log.i(TAG, "AL Success");
                                i3++;
                            } else {
                                Log.e(TAG, "AL Fail");
                            }
                        }
                    }
                    i6++;
                    i3++;
                }
                i3 = i6;
            } else {
                Log.e(TAG, "Select Fail" + bytesToHex(bArrSend));
                iOpen = -11;
            }
            i2 = i3 > 0 ? -16 : iOpen;
            i = 1;
            if (i != 0) {
                close();
            }
            return i2;
        }
        Log.e(TAG, "Select Error");
        close();
        return -11;
    }

    public int deactivateSCRSList(final String str, final ArrayList<String> arrayList) {
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        new Thread(new Runnable() { // from class: com.samsung.android.service.SemService.SemServiceManager.1
            /* JADX WARN: Code restructure failed: missing block: B:182:0x0424, code lost:
            
                android.util.Log.i(com.samsung.android.service.SemService.SemServiceManager.TAG, "#");
             */
            /* JADX WARN: Code restructure failed: missing block: B:183:0x0427, code lost:
            
                r17 = false;
             */
            /* JADX WARN: Code restructure failed: missing block: B:184:0x0429, code lost:
            
                r6 = false;
             */
            /* JADX WARN: Code restructure failed: missing block: B:193:0x0456, code lost:
            
                r0 = e;
             */
            /* JADX WARN: Code restructure failed: missing block: B:197:0x045b, code lost:
            
                r13 = r17;
                r17 = true;
             */
            /* JADX WARN: Removed duplicated region for block: B:115:0x0238 A[Catch: Error -> 0x011d, Exception -> 0x0122, UnsatisfiedLinkError -> 0x0128, NoClassDefFoundError -> 0x012d, NullPointerException -> 0x0132, TRY_ENTER, TRY_LEAVE, TryCatch #18 {Exception -> 0x0122, blocks: (B:41:0x00c5, B:43:0x00d6, B:45:0x00da, B:47:0x0100, B:49:0x0104, B:51:0x0108, B:52:0x010f, B:53:0x0114, B:54:0x011c, B:70:0x0147, B:72:0x015b, B:75:0x018f, B:77:0x0197, B:79:0x019d, B:81:0x01a9, B:83:0x01b2, B:82:0x01ad, B:87:0x01c3, B:89:0x01c9, B:91:0x01cd, B:93:0x01d9, B:95:0x01e2, B:97:0x01ea, B:115:0x0238, B:120:0x0252, B:122:0x025f, B:125:0x0266, B:128:0x026d, B:130:0x0275, B:132:0x02a3, B:135:0x02ad, B:137:0x02c1, B:138:0x02d0, B:140:0x02de, B:142:0x02f6, B:143:0x0305, B:145:0x0313, B:147:0x032b, B:148:0x033a, B:170:0x03c2, B:174:0x03dc, B:176:0x03f4, B:94:0x01dd, B:100:0x0206, B:102:0x020c, B:104:0x0212, B:109:0x0224, B:110:0x022e, B:71:0x0154), top: B:296:0x00c3 }] */
            /* JADX WARN: Removed duplicated region for block: B:117:0x0242 A[Catch: Error -> 0x052b, Exception -> 0x052e, UnsatisfiedLinkError -> 0x0536, NoClassDefFoundError -> 0x053a, NullPointerException -> 0x053e, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x052e, blocks: (B:27:0x006b, B:30:0x0075, B:32:0x0079, B:34:0x00a9, B:36:0x00af, B:38:0x00b3, B:65:0x0137, B:113:0x0233, B:117:0x0242, B:151:0x0347, B:153:0x034f), top: B:291:0x006b }] */
            /* JADX WARN: Removed duplicated region for block: B:220:0x04f8 A[Catch: Exception -> 0x0529, Error -> 0x052b, UnsatisfiedLinkError -> 0x0536, NoClassDefFoundError -> 0x053a, NullPointerException -> 0x053e, TryCatch #3 {Exception -> 0x0529, blocks: (B:218:0x04d9, B:220:0x04f8, B:221:0x04fe, B:199:0x0463, B:201:0x046d, B:203:0x0473, B:205:0x0484, B:207:0x0488, B:209:0x04b1, B:211:0x04b7, B:213:0x04bb, B:214:0x04c1, B:215:0x04c7, B:216:0x04d1, B:217:0x04d2, B:222:0x0504, B:224:0x051c, B:225:0x0528), top: B:293:0x0073 }] */
            /* JADX WARN: Removed duplicated region for block: B:221:0x04fe A[Catch: Exception -> 0x0529, Error -> 0x052b, UnsatisfiedLinkError -> 0x0536, NoClassDefFoundError -> 0x053a, NullPointerException -> 0x053e, TryCatch #3 {Exception -> 0x0529, blocks: (B:218:0x04d9, B:220:0x04f8, B:221:0x04fe, B:199:0x0463, B:201:0x046d, B:203:0x0473, B:205:0x0484, B:207:0x0488, B:209:0x04b1, B:211:0x04b7, B:213:0x04bb, B:214:0x04c1, B:215:0x04c7, B:216:0x04d1, B:217:0x04d2, B:222:0x0504, B:224:0x051c, B:225:0x0528), top: B:293:0x0073 }] */
            /* JADX WARN: Removed duplicated region for block: B:239:0x0542  */
            /* JADX WARN: Removed duplicated region for block: B:263:0x058f  */
            /* JADX WARN: Removed duplicated region for block: B:286:0x05fc A[Catch: Error -> 0x05f0, Exception -> 0x05f2, TRY_LEAVE, TryCatch #31 {Error -> 0x05f0, Exception -> 0x05f2, blocks: (B:279:0x05ea, B:284:0x05f4, B:286:0x05fc), top: B:301:0x05ea }] */
            /* JADX WARN: Removed duplicated region for block: B:291:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:301:0x05ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:336:? A[RETURN, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() throws Exception {
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4;
                String str2;
                int iOpen;
                byte[] bArrSend;
                String str3;
                ArrayList arrayList2;
                boolean z5;
                byte[] bArrSend2;
                int length;
                byte[] bArr = {0, -92, 4, 0, 9, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 1, 81, 67, 82, 83, 0};
                byte[] bArr2 = new byte[92160];
                byte[] bArr3 = {Byte.MIN_VALUE, -8, 0, 0, 0};
                byte[] bArr4 = {Byte.MIN_VALUE, -8, 1, 0, 0};
                try {
                    str2 = str;
                } catch (Error e) {
                    e = e;
                } catch (NullPointerException e2) {
                    e = e2;
                } catch (Exception e3) {
                    e = e3;
                    z = false;
                } catch (NoClassDefFoundError e4) {
                    e = e4;
                } catch (UnsatisfiedLinkError e5) {
                    e = e5;
                }
                try {
                } catch (NoClassDefFoundError e6) {
                    e = e6;
                    z2 = false;
                    Log.e(SemServiceManager.TAG, "Failed to find class." + e);
                    z4 = z2;
                    if (z4) {
                    }
                    if ("03".equalsIgnoreCase(str)) {
                    }
                } catch (Error e7) {
                    e = e7;
                    z2 = false;
                    Log.e(SemServiceManager.TAG, "Error " + e);
                    z4 = z2;
                    if (z4) {
                    }
                    if ("03".equalsIgnoreCase(str)) {
                    }
                } catch (NullPointerException e8) {
                    e = e8;
                    z2 = false;
                    Log.e(SemServiceManager.TAG, "Failed to connect service." + e);
                    z4 = z2;
                    if (z4) {
                    }
                    if ("03".equalsIgnoreCase(str)) {
                    }
                } catch (Exception e9) {
                    e = e9;
                    z3 = false;
                    if (!z3) {
                    }
                    z4 = z;
                    if (z4) {
                    }
                    if ("03".equalsIgnoreCase(str)) {
                    }
                } catch (UnsatisfiedLinkError e10) {
                    e = e10;
                    z2 = false;
                    Log.e(SemServiceManager.TAG, "Failed to link." + e);
                    z4 = z2;
                    if (z4) {
                    }
                    if ("03".equalsIgnoreCase(str)) {
                    }
                }
                if (str2 == null) {
                    Log.e(SemServiceManager.TAG, "Flag Error");
                    throw new NullPointerException("Flag Error");
                }
                if (arrayList == null) {
                    try {
                        if (str2.equalsIgnoreCase("02") || str.equalsIgnoreCase("03")) {
                            Log.e(SemServiceManager.TAG, "AID Null Error");
                            throw new NullPointerException("AID Error");
                        }
                        iOpen = SemServiceManager.this.open();
                        if (iOpen == 0) {
                            Log.e(SemServiceManager.TAG, "Open Error " + iOpen);
                            throw new Exception("OPEN Error");
                        }
                        try {
                            try {
                                bArrSend = SemServiceManager.this.send(bArr);
                            } catch (Exception e11) {
                                e = e11;
                            }
                            try {
                                if (bArrSend == null || bArrSend.length < 2) {
                                    Log.e(SemServiceManager.TAG, "Select Error");
                                    throw new Exception("RESP Error");
                                }
                                int length2 = bArrSend.length;
                                ArrayList arrayList3 = new ArrayList();
                                ArrayList arrayList4 = new ArrayList();
                                StringBuilder sb = new StringBuilder("Select SW : ");
                                int i = length2 - 2;
                                sb.append(SemServiceManager.byteToHex(bArrSend[i]));
                                int i2 = length2 - 1;
                                sb.append(SemServiceManager.byteToHex(bArrSend[i2]));
                                Log.i(SemServiceManager.TAG, sb.toString());
                                if (length2 >= 2 && bArrSend[i] == -112 && bArrSend[i2] == 0) {
                                    String str4 = "BDAL Fail";
                                    try {
                                        try {
                                            if (str.equalsIgnoreCase(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                                Log.i(SemServiceManager.TAG, "Start ALL BDAL");
                                                byte[] bArrSend3 = SemServiceManager.this.send(new byte[]{Byte.MIN_VALUE, -8, 0, 1, 0});
                                                if (bArrSend3 == null || bArrSend3.length < 2) {
                                                    Log.e(SemServiceManager.TAG, "BDAL Error");
                                                    throw new Exception("RESP Error");
                                                }
                                                int length3 = bArrSend3.length;
                                                StringBuilder sb2 = new StringBuilder("RSP SW : ");
                                                int i3 = length3 - 2;
                                                sb2.append(SemServiceManager.byteToHex(bArrSend3[i3]));
                                                int i4 = length3 - 1;
                                                sb2.append(SemServiceManager.byteToHex(bArrSend3[i4]));
                                                Log.i(SemServiceManager.TAG, sb2.toString());
                                                if (length3 >= 2 && bArrSend3[i3] == -112 && bArrSend3[i4] == 0) {
                                                    Log.i(SemServiceManager.TAG, "BDAL Success");
                                                } else {
                                                    Log.e(SemServiceManager.TAG, "BDAL Fail");
                                                }
                                            } else {
                                                Log.i(SemServiceManager.TAG, "Start Get-L");
                                                boolean z6 = false;
                                                int i5 = 0;
                                                int i6 = 0;
                                                while (i6 < 20) {
                                                    if (z6) {
                                                        Log.d(SemServiceManager.TAG, GnssSignalType.CODE_TYPE_M);
                                                        bArrSend2 = SemServiceManager.this.send(bArr4);
                                                        length = bArrSend2.length;
                                                    } else {
                                                        bArrSend2 = SemServiceManager.this.send(bArr3);
                                                        length = bArrSend2.length;
                                                    }
                                                    byte[] bArr5 = bArr3;
                                                    StringBuilder sb3 = new StringBuilder();
                                                    byte[] bArr6 = bArr4;
                                                    sb3.append("List SW : ");
                                                    int i7 = length - 2;
                                                    int i8 = i6;
                                                    sb3.append(SemServiceManager.byteToHex(bArrSend2[i7]));
                                                    int i9 = length - 1;
                                                    sb3.append(SemServiceManager.byteToHex(bArrSend2[i9]));
                                                    Log.i(SemServiceManager.TAG, sb3.toString());
                                                    int i10 = 2;
                                                    if (length >= 2) {
                                                        str3 = str4;
                                                        if (bArrSend2[i7] == 99 && bArrSend2[i9] == 16) {
                                                            Log.i(SemServiceManager.TAG, "M-Get List");
                                                            int i11 = i5 + i7;
                                                            if (i11 > 92160) {
                                                                Log.e(SemServiceManager.TAG, "Size Error");
                                                            } else {
                                                                System.arraycopy(bArrSend2, 0, bArr2, i5, i7);
                                                                i5 = i11;
                                                            }
                                                            i6 = i8 + 1;
                                                            z6 = true;
                                                            bArr3 = bArr5;
                                                            bArr4 = bArr6;
                                                            str4 = str3;
                                                        } else {
                                                            i10 = 2;
                                                        }
                                                    } else {
                                                        str3 = str4;
                                                    }
                                                    if (length >= i10) {
                                                        if (bArrSend2[i7] == -112 && bArrSend2[i9] == 0) {
                                                            Log.i(SemServiceManager.TAG, "Get List Succ");
                                                            int i12 = i5 + i7;
                                                            if (i12 > 92160) {
                                                                Log.e(SemServiceManager.TAG, "Size Error");
                                                            } else {
                                                                System.arraycopy(bArrSend2, 0, bArr2, i5, i7);
                                                                i5 = i12;
                                                            }
                                                            arrayList4 = SemServiceManager.this.parseList(bArr2, i5);
                                                            if (arrayList4 != null) {
                                                                Log.i(SemServiceManager.TAG, "L CNT : " + arrayList4.size());
                                                            }
                                                            arrayList2 = arrayList4;
                                                            Boolean.valueOf(true);
                                                            if (arrayList2 != null) {
                                                                Log.e(SemServiceManager.TAG, "whiteAidList Null");
                                                            } else if (str.equalsIgnoreCase("02")) {
                                                                Log.i(SemServiceManager.TAG, "Start LD");
                                                                if (arrayList.size() > 0 && arrayList2.size() > 0) {
                                                                    for (int i13 = 0; i13 < arrayList2.size(); i13++) {
                                                                        int i14 = 0;
                                                                        while (true) {
                                                                            if (i14 < arrayList.size()) {
                                                                                Log.d(SemServiceManager.TAG, "COM List : " + ((String) arrayList2.get(i13)) + " : " + ((String) arrayList.get(i14)));
                                                                                if (arrayList2.get(i13) == null || arrayList.get(i14) == null) {
                                                                                    Log.e(SemServiceManager.TAG, "COM Data Error");
                                                                                } else {
                                                                                    if (((String) arrayList2.get(i13)).equalsIgnoreCase((String) arrayList.get(i14))) {
                                                                                        Log.i(SemServiceManager.TAG, "N A-DAL");
                                                                                        arrayList3.add((String) arrayList2.get(i13));
                                                                                        break;
                                                                                    }
                                                                                    if (((String) arrayList.get(i14)).contains("*")) {
                                                                                        if (((String) arrayList2.get(i13)).startsWith(((String) arrayList.get(i14)).replaceAll("*", ""))) {
                                                                                            Log.i(SemServiceManager.TAG, "* A-DAL");
                                                                                            arrayList3.add((String) arrayList2.get(i13));
                                                                                            break;
                                                                                        }
                                                                                    } else if (((String) arrayList.get(i14)).contains("#")) {
                                                                                        if (((String) arrayList2.get(i13)).endsWith(((String) arrayList.get(i14)).replaceAll("#", ""))) {
                                                                                            Log.i(SemServiceManager.TAG, "# A-DAL");
                                                                                            arrayList3.add((String) arrayList2.get(i13));
                                                                                            break;
                                                                                        }
                                                                                    } else {
                                                                                        continue;
                                                                                    }
                                                                                }
                                                                                i14++;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                if (str.equalsIgnoreCase("03")) {
                                                                    Log.i(SemServiceManager.TAG, "Start BLD");
                                                                    if (arrayList.size() > 0) {
                                                                        try {
                                                                            if (arrayList2.size() > 0) {
                                                                                for (int i15 = 0; i15 < arrayList2.size(); i15++) {
                                                                                    Boolean bool = true;
                                                                                    int i16 = 0;
                                                                                    while (true) {
                                                                                        if (i16 >= arrayList.size()) {
                                                                                            break;
                                                                                        }
                                                                                        Log.d(SemServiceManager.TAG, "COM List : " + ((String) arrayList2.get(i15)) + " : " + ((String) arrayList.get(i16)));
                                                                                        if (arrayList2.get(i15) == null || arrayList.get(i16) == null) {
                                                                                            z5 = false;
                                                                                            Log.e(SemServiceManager.TAG, "COM Data Error");
                                                                                        } else {
                                                                                            if (((String) arrayList2.get(i15)).equalsIgnoreCase((String) arrayList.get(i16))) {
                                                                                                Log.i(SemServiceManager.TAG, GnssSignalType.CODE_TYPE_N);
                                                                                                bool = false;
                                                                                                break;
                                                                                            }
                                                                                            if (((String) arrayList.get(i16)).contains("*")) {
                                                                                                if (((String) arrayList2.get(i15)).startsWith(((String) arrayList.get(i16)).replaceAll("*", ""))) {
                                                                                                    Log.i(SemServiceManager.TAG, "*");
                                                                                                    bool = false;
                                                                                                    break;
                                                                                                }
                                                                                            } else if (((String) arrayList.get(i16)).contains("#")) {
                                                                                                if (((String) arrayList2.get(i15)).endsWith(((String) arrayList.get(i16)).replaceAll("#", ""))) {
                                                                                                    break;
                                                                                                }
                                                                                            }
                                                                                            z5 = false;
                                                                                        }
                                                                                        i16++;
                                                                                    }
                                                                                    if (bool.booleanValue()) {
                                                                                        Log.i(SemServiceManager.TAG, "A-DAL");
                                                                                        arrayList3.add((String) arrayList2.get(i15));
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (Exception e12) {
                                                                            e = e12;
                                                                            z5 = false;
                                                                        }
                                                                    }
                                                                    if (arrayList.size() < 1 && arrayList2.size() > 0) {
                                                                        Log.i(SemServiceManager.TAG, "Start ALL BDAL");
                                                                        byte[] bArrSend4 = SemServiceManager.this.send(new byte[]{Byte.MIN_VALUE, -8, 0, 1, 0});
                                                                        if (bArrSend4 == null || bArrSend4.length < 2) {
                                                                            Log.e(SemServiceManager.TAG, "BDAL Error");
                                                                            throw new Exception("RESP Error");
                                                                        }
                                                                        int length4 = bArrSend4.length;
                                                                        StringBuilder sb4 = new StringBuilder();
                                                                        sb4.append("RSP SW : ");
                                                                        int i17 = length4 - 2;
                                                                        sb4.append(SemServiceManager.byteToHex(bArrSend4[i17]));
                                                                        int i18 = length4 - 1;
                                                                        sb4.append(SemServiceManager.byteToHex(bArrSend4[i18]));
                                                                        Log.i(SemServiceManager.TAG, sb4.toString());
                                                                        if (length4 >= 2 && bArrSend4[i17] == -112 && bArrSend4[i18] == 0) {
                                                                            Log.i(SemServiceManager.TAG, "BDAL Success");
                                                                        } else {
                                                                            Log.e(SemServiceManager.TAG, str3);
                                                                        }
                                                                    }
                                                                } else {
                                                                    Log.e(SemServiceManager.TAG, "Type Error");
                                                                }
                                                                Log.i(SemServiceManager.TAG, "DL CNT : " + arrayList3.size());
                                                                if (arrayList3.size() < 1) {
                                                                    Log.i(SemServiceManager.TAG, "DA List Null");
                                                                } else {
                                                                    SemServiceManager.this.AIDDeactivation(arrayList3);
                                                                }
                                                            }
                                                            Log.i(SemServiceManager.TAG, "DL CNT : " + arrayList3.size());
                                                            if (arrayList3.size() < 1) {
                                                            }
                                                        } else {
                                                            i10 = 2;
                                                        }
                                                    }
                                                    if (length < i10 || bArrSend2[i7] != 106 || bArrSend2[i9] != -120) {
                                                        Log.e(SemServiceManager.TAG, "Get List Fail");
                                                        throw new Exception("RESP Error");
                                                    }
                                                    Log.i(SemServiceManager.TAG, "List Null");
                                                    try {
                                                        throw new Exception("RESP Error");
                                                    } catch (Exception e13) {
                                                        e = e13;
                                                        z3 = true;
                                                        z = true;
                                                        if (!z3) {
                                                        }
                                                        z4 = z;
                                                        if (z4) {
                                                        }
                                                        if ("03".equalsIgnoreCase(str)) {
                                                        }
                                                    }
                                                }
                                                str3 = str4;
                                                arrayList2 = arrayList4;
                                                Boolean.valueOf(true);
                                                if (arrayList2 != null) {
                                                }
                                                Log.i(SemServiceManager.TAG, "DL CNT : " + arrayList3.size());
                                                if (arrayList3.size() < 1) {
                                                }
                                            }
                                        } catch (Exception e14) {
                                            e = e14;
                                            z = true;
                                            z3 = false;
                                        }
                                    } catch (NoClassDefFoundError e15) {
                                        e = e15;
                                        z2 = true;
                                        Log.e(SemServiceManager.TAG, "Failed to find class." + e);
                                        z4 = z2;
                                        if (z4) {
                                        }
                                        if ("03".equalsIgnoreCase(str)) {
                                        }
                                    } catch (NullPointerException e16) {
                                        e = e16;
                                        z2 = true;
                                        Log.e(SemServiceManager.TAG, "Failed to connect service." + e);
                                        z4 = z2;
                                        if (z4) {
                                        }
                                        if ("03".equalsIgnoreCase(str)) {
                                        }
                                    } catch (UnsatisfiedLinkError e17) {
                                        e = e17;
                                        z2 = true;
                                        Log.e(SemServiceManager.TAG, "Failed to link." + e);
                                        z4 = z2;
                                        if (z4) {
                                        }
                                        if ("03".equalsIgnoreCase(str)) {
                                        }
                                    } catch (Error e18) {
                                        e = e18;
                                        z2 = true;
                                        Log.e(SemServiceManager.TAG, "Error " + e);
                                        z4 = z2;
                                        if (z4) {
                                        }
                                        if ("03".equalsIgnoreCase(str)) {
                                        }
                                    }
                                } else {
                                    Log.e(SemServiceManager.TAG, "Selet Fail" + SemServiceManager.bytesToHex(bArrSend));
                                }
                                z4 = true;
                            } catch (Exception e19) {
                                e = e19;
                                z3 = false;
                                z = true;
                                if (!z3) {
                                    Log.e(SemServiceManager.TAG, "Exception " + e);
                                }
                                z4 = z;
                                if (z4) {
                                }
                                if ("03".equalsIgnoreCase(str)) {
                                }
                            }
                        } catch (NoClassDefFoundError e20) {
                            e = e20;
                            z2 = true;
                        } catch (UnsatisfiedLinkError e21) {
                            e = e21;
                            z2 = true;
                        } catch (Error e22) {
                            e = e22;
                            z2 = true;
                        } catch (NullPointerException e23) {
                            e = e23;
                            z2 = true;
                        }
                    } catch (NullPointerException e24) {
                        e = e24;
                        z2 = false;
                        Log.e(SemServiceManager.TAG, "Failed to connect service." + e);
                        z4 = z2;
                        if (z4) {
                        }
                        if ("03".equalsIgnoreCase(str)) {
                        }
                    } catch (Exception e25) {
                        e = e25;
                        z3 = false;
                        z = false;
                        if (!z3) {
                        }
                        z4 = z;
                        if (z4) {
                        }
                        if ("03".equalsIgnoreCase(str)) {
                        }
                    } catch (NoClassDefFoundError e26) {
                        e = e26;
                        z2 = false;
                        Log.e(SemServiceManager.TAG, "Failed to find class." + e);
                        z4 = z2;
                        if (z4) {
                        }
                        if ("03".equalsIgnoreCase(str)) {
                        }
                    } catch (UnsatisfiedLinkError e27) {
                        e = e27;
                        z2 = false;
                        Log.e(SemServiceManager.TAG, "Failed to link." + e);
                        z4 = z2;
                        if (z4) {
                        }
                        if ("03".equalsIgnoreCase(str)) {
                        }
                    } catch (Error e28) {
                        e = e28;
                        z2 = false;
                        Log.e(SemServiceManager.TAG, "Error " + e);
                        z4 = z2;
                        if (z4) {
                        }
                        if ("03".equalsIgnoreCase(str)) {
                        }
                    }
                } else {
                    iOpen = SemServiceManager.this.open();
                    if (iOpen == 0) {
                    }
                }
                if (z4) {
                    try {
                        SemServiceManager.this.close();
                    } catch (Error e29) {
                        Log.e(SemServiceManager.TAG, "PROP Error " + e29);
                        return;
                    } catch (Exception e30) {
                        Log.e(SemServiceManager.TAG, "PROP Exception " + e30);
                        return;
                    }
                }
                if ("03".equalsIgnoreCase(str)) {
                    return;
                }
                Log.i(SemServiceManager.TAG, "Set PROP");
                Intent intent = new Intent("com.sec.action.CDA_FINISH");
                intent.putExtra("com.sec.action.CDA_VALUE", 1);
                intent.setPackage("com.samsung.android.app.telephonyui");
                SemServiceManager.this.mContext.sendBroadcast(intent);
                SystemProperties.set("security.cdafinish", "1");
            }
        }).start();
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int AIDDeactivation(ArrayList<String> arrayList) {
        byte b;
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        int i = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (arrayList.get(i2) == null) {
                Log.e(TAG, "AID Null Error");
            } else {
                String str = arrayList.get(i2);
                int length = str.length() / 2;
                String str2 = String.format("%02x", Integer.valueOf(length));
                byte[] bArrSend = send(hexToBytes(((("80F80002" + String.format("%02x", Integer.valueOf(length + 1))) + str2) + str) + "00"));
                if (bArrSend == null || bArrSend.length < 2) {
                    Log.e(TAG, "Aid Deactivation Error");
                } else {
                    int length2 = bArrSend.length;
                    StringBuilder sb = new StringBuilder("DEAID SW : ");
                    int i3 = length2 - 2;
                    sb.append(byteToHex(bArrSend[i3]));
                    int i4 = length2 - 1;
                    sb.append(byteToHex(bArrSend[i4]));
                    Log.i(TAG, sb.toString());
                    if (length2 >= 2 && (((b = bArrSend[i3]) == -112 && bArrSend[i4] == 0) || (b == 99 && bArrSend[i4] == 8))) {
                        Log.i(TAG, "deactivate list success");
                    } else {
                        Log.e(TAG, "deactivate list fail");
                    }
                }
                i++;
            }
        }
        return i;
    }

    public int eSEFactoryReset() {
        Log.i(TAG, "eSEFactoryReset() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.eSE_FactoryReset();
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int eSELowFactoryReset() {
        Log.i(TAG, "eSELowFactoryReset() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.eSE_LowFactoryReset();
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int eSEFullFactoryReset() {
        Log.i(TAG, "eSEFullFactoryReset() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.eSE_FullFactoryReset();
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int eSEAidFactoryReset(byte[] bArr, int i) {
        Log.i(TAG, "eSEAidFactoryReset() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.eSE_AidFactoryReset(bArr, i);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public void checkFRANetwork(int i) {
        Log.i(TAG, "checkFRANetwork() is called. " + i);
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return;
        }
        try {
            this.mSemService.check_Network(i);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            Log.e(TAG, "Failed to connect service.");
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<String> parseList(byte[] bArr, int i) {
        if (bArr == 0) {
            Log.e(TAG, "parse list aid null error");
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = bArr[i2];
            if (i4 > 0) {
                int i5 = i2 + 1;
                byte[] bArr2 = new byte[i4];
                byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, i5, i5 + i4);
                i2 = i5 + (i4 - 1);
                arrayList.add(bytesToHex(bArrCopyOfRange));
                Log.d(TAG, i3 + " : " + i2 + " : " + arrayList.get(i3));
                i3++;
            }
            i2++;
        }
        return arrayList;
    }

    public int jniICD() {
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.ICD();
        } catch (RemoteException e) {
            Log.e(TAG, "RE Exception : " + e);
            return -999;
        } catch (Exception e2) {
            Log.e(TAG, "Exception : " + e2);
            return -999;
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NCDF Exception : " + e3);
            return -999;
        } catch (UnsatisfiedLinkError e4) {
            Log.e(TAG, "USLE Exception : " + e4);
            return -999;
        }
    }

    public int startattestation(byte[] bArr, int i, byte[] bArr2, int i2) {
        Log.i(TAG, "startattestation() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            sercureLog("StartAttestation");
            return this.mSemService.start_attestation(bArr, i, bArr2, i2);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            return -92;
        } catch (NullPointerException e2) {
            Log.e(TAG, "Failed to connect service.");
            e2.printStackTrace();
            return -92;
        } catch (Exception e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -92;
        }
    }

    public int continueattestation(String str, int i, byte[] bArr) {
        Log.i(TAG, "continueattestation() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            sercureLog("ContinueAttestation");
            return this.mSemService.continue_attestation(str, i, bArr);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -92;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -92;
        }
    }

    public int getPK(byte[] bArr) {
        Log.i(TAG, "getPK() is called.");
        return 0;
    }

    public void sercureLog(String str) {
        Log.i(TAG, "SecureLog() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return;
        }
        try {
            this.mSemService.secureLog(str);
        } catch (Exception unused) {
            Log.e(TAG, "SL-EX");
        } catch (NoClassDefFoundError unused2) {
            Log.e(TAG, "SL-NCDFE");
        } catch (NullPointerException unused3) {
            Log.e(TAG, "Failed to connect service.");
        } catch (UnsatisfiedLinkError unused4) {
            Log.e(TAG, "SL-ULE");
        } catch (Error unused5) {
            Log.e(TAG, "SL-ER");
        }
    }

    public void startSLOG() {
        Log.i(TAG, "START SLOG is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return;
        }
        try {
            this.mSemService.start_SLOG();
        } catch (Exception unused) {
            Log.e(TAG, "S_SL-EX");
        } catch (NoClassDefFoundError unused2) {
            Log.e(TAG, "S_SL-NCDFE");
        } catch (NullPointerException unused3) {
            Log.e(TAG, "Failed to connect service.");
        } catch (UnsatisfiedLinkError unused4) {
            Log.e(TAG, "S_SL-ULE");
        } catch (Error unused5) {
            Log.e(TAG, "S_SL-ER");
        }
    }

    public void stopSLOG() {
        Log.i(TAG, "STOP SLOG is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return;
        }
        try {
            this.mSemService.stop_SLOG();
        } catch (Exception unused) {
            Log.e(TAG, "ST_SL-EX");
        } catch (NoClassDefFoundError unused2) {
            Log.e(TAG, "ST_SL-NCDFE");
        } catch (NullPointerException unused3) {
            Log.e(TAG, "Failed to connect service.");
        } catch (UnsatisfiedLinkError unused4) {
            Log.e(TAG, "ST_SL-ULE");
        } catch (Error unused5) {
            Log.e(TAG, "S_SL-ER");
        }
    }

    public void agentSLog(String str) {
        Log.i(TAG, "SLOG is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return;
        }
        try {
            this.mSemService.agent_SLOG(str);
        } catch (Exception unused) {
            Log.e(TAG, "ST_SL-EX");
        } catch (NoClassDefFoundError unused2) {
            Log.e(TAG, "ST_SL-NCDFE");
        } catch (NullPointerException unused3) {
            Log.e(TAG, "Failed to connect service.");
        } catch (UnsatisfiedLinkError unused4) {
            Log.e(TAG, "ST_SL-ULE");
        } catch (Error unused5) {
            Log.e(TAG, "S_SL-ER");
        }
    }

    public int getAtr() {
        Log.i(TAG, "getAtr() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.getAtr_Spi();
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int resetForCOSU(int i) {
        Log.i(TAG, "resetForCOSU is called. " + i);
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        ISemService iSemService = this.mSemService;
        int iResetForCOSU = -1;
        if (iSemService == null) {
            Log.e(TAG, "SemService is not connected");
            return -1;
        }
        try {
            iResetForCOSU = iSemService.resetForCOSU();
            Log.i(TAG, "resetForCOSU : " + iResetForCOSU);
            return iResetForCOSU;
        } catch (Exception e) {
            Log.e(TAG, "Call resetForCOSU Exception " + e);
            return iResetForCOSU;
        }
    }

    public int open() {
        Log.i(TAG, "open() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        return openSpi(0);
    }

    public int close() {
        Log.i(TAG, "close() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        return closeSpi(0);
    }

    public boolean isOpened() {
        Log.d(TAG, "isOpened() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return true;
        }
        try {
            int iOpenSpi = openSpi(0);
            try {
                if (iOpenSpi == -200) {
                    Log.d(TAG, "eSE is busy now");
                    return true;
                }
                if (iOpenSpi == 0) {
                    Log.d(TAG, "eSE is NOT busy");
                    closeSpi(0);
                } else {
                    Log.e(TAG, "eSE returned error value : " + iOpenSpi);
                    closeSpi(0);
                }
                return false;
            } catch (Exception e) {
                Log.e(TAG, "isOpened close Exception! : " + e);
                closeSpi(0);
                return false;
            }
        } catch (Exception e2) {
            Log.e(TAG, "isOpened openSpi Error : " + e2);
            return false;
        }
    }

    public byte[] send(byte[] bArr) {
        int length;
        Log.i(TAG, "send() for SE API is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        byte[] bArr2 = new byte[65538];
        if (bArr != null) {
            length = bArr.length;
            Log.d(TAG, "Len : " + length);
        } else {
            length = 0;
        }
        try {
            int iSend_Data = this.mSemService.send_Data(bArr, length, bArr2, 0);
            if (iSend_Data < 1) {
                Log.e(TAG, "RSP is null");
                return null;
            }
            byte[] bArrCopyOf = Arrays.copyOf(bArr2, iSend_Data);
            Log.d(TAG, "baRsp : " + bytesToHex(bArrCopyOf));
            return bArrCopyOf;
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "send exception " + e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "Exception : " + e2);
            return null;
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NCDF Exception : " + e3);
            return null;
        } catch (UnsatisfiedLinkError e4) {
            Log.e(TAG, "USLE Exception : " + e4);
            return null;
        }
    }

    public int open(int i) {
        Log.i(TAG, "open() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        return openSpi(i);
    }

    public int close(int i) {
        Log.i(TAG, "close() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        return closeSpi(i);
    }

    public byte[] send(byte[] bArr, int i) {
        int length;
        Log.i(TAG, "send() for normal/secure SPI is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        byte[] bArr2 = new byte[65538];
        if (bArr != null) {
            length = bArr.length;
            Log.d(TAG, "Len : " + length);
        } else {
            length = 0;
        }
        try {
            int iSend_Data = this.mSemService.send_Data(bArr, length, bArr2, i);
            if (iSend_Data < 1) {
                Log.e(TAG, "RSP is null");
                return null;
            }
            byte[] bArrCopyOf = Arrays.copyOf(bArr2, iSend_Data);
            Log.d(TAG, "baRsp : " + bytesToHex(bArrCopyOf));
            return bArrCopyOf;
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "send exception " + e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "Exception : " + e2);
            return null;
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NCDF Exception : " + e3);
            return null;
        } catch (UnsatisfiedLinkError e4) {
            Log.e(TAG, "USLE Exception : " + e4);
            return null;
        }
    }

    public int checkSeState(byte[] bArr, byte[] bArr2) {
        Log.i(TAG, "checkSeState() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.check_SeState(bArr, bArr2);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int startRequestCredentials(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        Log.i(TAG, "startRequestCredentials() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.start_request_credentials(bArr, bArr2, str, bArr3);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public void stopRequestCredentials() {
        Log.i(TAG, "stopRequestCredentials() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return;
        }
        try {
            this.mSemService.stop_request_credentials();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e2) {
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            Log.e(TAG, "Failed to connect service.");
            e3.printStackTrace();
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
        }
    }

    public int grdmGetSession() {
        Log.i(TAG, "grdmGetSession() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.grdm_get_session();
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -90;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int grdmRequestKey(int i, byte[] bArr) {
        Log.i(TAG, "grdmRequestKey() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.grdm_request_key(i, bArr);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -90;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int grdmReleaseSession() {
        Log.i(TAG, "grdmReleaseSession() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.grdm_release_session();
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -90;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int grdmGetAttesCert(int i, byte[] bArr) {
        Log.i(TAG, "grdmGetAttesCert() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.grdm_get_attes_cert(i, bArr);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -90;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public String grdmCheckRestrictedMode() {
        Log.i(TAG, "grdmCheckRestrictedMode() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        try {
            return this.mSemService.grdm_check_restricted_mode();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (NoClassDefFoundError e2) {
            e2.printStackTrace();
            return null;
        } catch (NullPointerException e3) {
            Log.e(TAG, "Failed to connect service.");
            e3.printStackTrace();
            return null;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public int grdmCheckStatus() {
        Log.i(TAG, "grdmCheckStatus() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.grdm_Check_Status();
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -90;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public static String bytesToHex(byte[] bArr) {
        if (bArr == null || bArr.length < 1) {
            return null;
        }
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            char[] cArr2 = HEX_CHARS;
            byte b = bArr[i];
            cArr[i2] = cArr2[(b & 240) >>> 4];
            cArr[i2 + 1] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public static String byteToHex(byte b) {
        char[] cArr = HEX_CHARS;
        return new String(new char[]{cArr[(b >> 4) & 15], cArr[b & 15]});
    }

    public static byte[] hexToBytes(String str) {
        if (str == null || str.length() < 2) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public boolean isEseSupported() {
        Log.i(TAG, "isEseSupported() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return false;
        }
        try {
            if (this.mSemService.openSpiDriver() == -100) {
                return false;
            }
            this.mSemService.closeSpiDriver();
            return true;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e2) {
            Log.e(TAG, "Failed to connect service.");
            e2.printStackTrace();
            return false;
        } catch (Exception e3) {
            e3.printStackTrace();
            return false;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return false;
        }
    }

    public int openSpi(int i) {
        Log.i(TAG, "openSpi() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.open_Spi(i);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int closeSpi(int i) {
        Log.i(TAG, "closeSpi() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.close_Spi(i);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int sendData(byte[] bArr, int i, byte[] bArr2, int i2) {
        Log.i(TAG, "sendData() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.send_Data(bArr, i, bArr2, i2);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int sendData(byte[] bArr, int i, byte[] bArr2) {
        Log.i(TAG, "sendData() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            return this.mSemService.send_Data(bArr, i, bArr2, 0);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }

    public int COSSPIAccessControl(int i) {
        Log.i(TAG, "COSSPIAccessControl() is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        try {
            if (i == 1) {
                Log.i(TAG, "SPIAC SPI Open");
                return this.mSemService.openSpiDriver();
            }
            if (i == 0) {
                Log.i(TAG, "SPIAC SPI Close");
                return this.mSemService.closeSpiDriver();
            }
            Log.e(TAG, "SPIAC Type Error");
            return -90;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -92;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -90;
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            return -90;
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
            return -90;
        }
    }
}
