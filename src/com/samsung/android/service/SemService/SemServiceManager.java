package com.samsung.android.service.SemService;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import com.android.internal.midi.MidiConstants;
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
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', DateFormat.DAY, 'F'};
    private static boolean isSupportSemServiceManager = true;
    private static String cosName = "JCOP7.1U";

    public int accessControlForCOSU(int i) {
        return 0;
    }

    public SemServiceManager(Context context) {
        this.mContext = context;
        if (cosName.contains("E")) {
            isSupportSemServiceManager = false;
        }
        if (isSupportSemServiceManager) {
            ISemService asInterface = ISemService.Stub.asInterface(ServiceManager.getService("SemService"));
            this.mSemService = asInterface;
            if (asInterface == null) {
                Log.w(TAG, this.mContext.getPackageName() + " connects to SemService is failed.");
                return;
            }
            Log.i(TAG, this.mContext.getPackageName() + " connects to SemService.");
            return;
        }
        Log.w(TAG, "SemService is not supported");
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
            String[] handle_CCMCB = this.mSemService.handle_CCMCB(bArr, i, bArr2, i2);
            Log.i(TAG, "ccmData : " + bytesToHex(Arrays.copyOf(bArr2, i2)));
            Log.i(TAG, "ccmDataLen : " + i2);
            return handle_CCMCB;
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

    public int getSCRSVersion() {
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
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "Failed to find class." + e2);
        } catch (NullPointerException e3) {
            Log.e(TAG, "Failed to connect service. " + e3);
        } catch (UnsatisfiedLinkError unused) {
            Log.e(TAG, "Failed to link.");
        } catch (Error e4) {
            Log.e(TAG, "Error " + e4);
        }
        if (str != null && "factory".equals(str)) {
            Log.i(TAG, "FACTORY NOT SUPPORT");
            return -10;
        }
        int open = open();
        if (open != 0) {
            Log.e(TAG, "OPEN Error " + open);
            return open;
        }
        z = true;
        byte[] send = send(bArr);
        if (send != null && send.length >= 2) {
            int length = send.length;
            StringBuilder sb = new StringBuilder("Select SW : ");
            int i2 = length - 2;
            sb.append(byteToHex(send[i2]));
            int i3 = length - 1;
            sb.append(byteToHex(send[i3]));
            Log.i(TAG, sb.toString());
            if (length >= 2 && send[i2] == -112 && send[i3] == 0) {
                byte[] send2 = send(bArr2);
                int i4 = -12;
                if (send2 != null && send2.length >= 2) {
                    int length2 = send2.length;
                    StringBuilder sb2 = new StringBuilder("RSP SW : ");
                    int i5 = length2 - 2;
                    sb2.append(byteToHex(send2[i5]));
                    int i6 = length2 - 1;
                    sb2.append(byteToHex(send2[i6]));
                    Log.i(TAG, sb2.toString());
                    if (length2 > 3 && send2[i5] == -112 && send2[i6] == 0) {
                        str2 = byteToHex(send2[2]) + byteToHex(send2[3]);
                        if (str2 != null) {
                            i4 = Integer.parseInt(str2);
                        } else {
                            Log.e(TAG, "Parse Version Error");
                            i4 = -13;
                        }
                    } else {
                        Log.e(TAG, "Send Fail " + bytesToHex(send2));
                        str2 = null;
                    }
                    Log.i(TAG, "SCRS Version : " + str2);
                    i = i4;
                }
                Log.e(TAG, "Send Error");
                close();
                return -12;
            }
            Log.e(TAG, "Select Fail " + bytesToHex(send));
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

    /* JADX WARN: Removed duplicated region for block: B:56:0x020b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int addSCRSList(java.lang.String r18, java.util.ArrayList<java.lang.String> r19) {
        /*
            Method dump skipped, instructions count: 539
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.service.SemService.SemServiceManager.addSCRSList(java.lang.String, java.util.ArrayList):int");
    }

    public int deactivateSCRSList(final String str, final ArrayList<String> arrayList) {
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return -92;
        }
        new Thread(new Runnable() { // from class: com.samsung.android.service.SemService.SemServiceManager.1
            /* JADX WARN: Code restructure failed: missing block: B:199:0x0456, code lost:
            
                r0 = e;
             */
            /* JADX WARN: Code restructure failed: missing block: B:200:0x045b, code lost:
            
                r13 = r17;
                r17 = true;
             */
            /* JADX WARN: Code restructure failed: missing block: B:219:0x0424, code lost:
            
                android.util.Log.i(com.samsung.android.service.SemService.SemServiceManager.TAG, "#");
             */
            /* JADX WARN: Code restructure failed: missing block: B:220:0x0427, code lost:
            
                r17 = false;
             */
            /* JADX WARN: Code restructure failed: missing block: B:222:0x0429, code lost:
            
                r6 = false;
             */
            /* JADX WARN: Removed duplicated region for block: B:100:0x0238 A[Catch: Error -> 0x011d, Exception -> 0x0122, UnsatisfiedLinkError -> 0x0128, NoClassDefFoundError -> 0x012d, NullPointerException -> 0x0132, TRY_ENTER, TRY_LEAVE, TryCatch #18 {Exception -> 0x0122, blocks: (B:32:0x00c5, B:34:0x00d6, B:36:0x00da, B:38:0x0100, B:40:0x0104, B:42:0x0108, B:61:0x010f, B:62:0x0114, B:63:0x011c, B:69:0x0147, B:70:0x015b, B:73:0x018f, B:75:0x0197, B:77:0x019d, B:79:0x01a9, B:81:0x01b2, B:82:0x01ad, B:87:0x01c3, B:89:0x01c9, B:91:0x01cd, B:93:0x01d9, B:94:0x01e2, B:96:0x01ea, B:100:0x0238, B:109:0x0252, B:111:0x025f, B:114:0x0266, B:117:0x026d, B:119:0x0275, B:121:0x02a3, B:124:0x02ad, B:146:0x02c1, B:126:0x02d0, B:128:0x02de, B:131:0x02f6, B:136:0x0305, B:138:0x0313, B:141:0x032b, B:148:0x033a, B:224:0x03c2, B:193:0x03dc, B:206:0x03f4, B:234:0x01dd, B:237:0x0206, B:239:0x020c, B:241:0x0212, B:248:0x0224, B:249:0x022e, B:253:0x0154), top: B:30:0x00c3 }] */
            /* JADX WARN: Removed duplicated region for block: B:104:0x04f8 A[Catch: Exception -> 0x0529, Error -> 0x052b, UnsatisfiedLinkError -> 0x0536, NoClassDefFoundError -> 0x053a, NullPointerException -> 0x053e, TryCatch #3 {Exception -> 0x0529, blocks: (B:102:0x04d9, B:104:0x04f8, B:105:0x04fe, B:157:0x0463, B:159:0x046d, B:161:0x0473, B:163:0x0484, B:165:0x0488, B:167:0x04b1, B:169:0x04b7, B:171:0x04bb, B:172:0x04c1, B:173:0x04c7, B:174:0x04d1, B:233:0x04d2, B:271:0x0504, B:272:0x051c, B:273:0x0528), top: B:18:0x0073 }] */
            /* JADX WARN: Removed duplicated region for block: B:105:0x04fe A[Catch: Exception -> 0x0529, Error -> 0x052b, UnsatisfiedLinkError -> 0x0536, NoClassDefFoundError -> 0x053a, NullPointerException -> 0x053e, TryCatch #3 {Exception -> 0x0529, blocks: (B:102:0x04d9, B:104:0x04f8, B:105:0x04fe, B:157:0x0463, B:159:0x046d, B:161:0x0473, B:163:0x0484, B:165:0x0488, B:167:0x04b1, B:169:0x04b7, B:171:0x04bb, B:172:0x04c1, B:173:0x04c7, B:174:0x04d1, B:233:0x04d2, B:271:0x0504, B:272:0x051c, B:273:0x0528), top: B:18:0x0073 }] */
            /* JADX WARN: Removed duplicated region for block: B:106:0x0242 A[Catch: Error -> 0x052b, Exception -> 0x052e, UnsatisfiedLinkError -> 0x0536, NoClassDefFoundError -> 0x053a, NullPointerException -> 0x053e, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x052e, blocks: (B:16:0x006b, B:20:0x0075, B:22:0x0079, B:24:0x00a9, B:26:0x00af, B:28:0x00b3, B:64:0x0137, B:98:0x0233, B:106:0x0242, B:152:0x0347, B:154:0x034f), top: B:15:0x006b }] */
            /* JADX WARN: Removed duplicated region for block: B:202:0x058f  */
            /* JADX WARN: Removed duplicated region for block: B:47:0x05fc A[Catch: Error -> 0x05f0, Exception -> 0x05f2, TRY_LEAVE, TryCatch #31 {Error -> 0x05f0, Exception -> 0x05f2, blocks: (B:60:0x05ea, B:45:0x05f4, B:47:0x05fc), top: B:59:0x05ea }] */
            /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:59:0x05ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    Method dump skipped, instructions count: 1653
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.service.SemService.SemServiceManager.AnonymousClass1.run():void");
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
                String format = String.format("%02x", Integer.valueOf(length));
                byte[] send = send(hexToBytes(((("80F80002" + String.format("%02x", Integer.valueOf(length + 1))) + format) + str) + "00"));
                if (send == null || send.length < 2) {
                    Log.e(TAG, "Aid Deactivation Error");
                } else {
                    int length2 = send.length;
                    StringBuilder sb = new StringBuilder("DEAID SW : ");
                    int i3 = length2 - 2;
                    sb.append(byteToHex(send[i3]));
                    int i4 = length2 - 1;
                    sb.append(byteToHex(send[i4]));
                    Log.i(TAG, sb.toString());
                    if (length2 >= 2 && (((b = send[i3]) == -112 && send[i4] == 0) || (b == 99 && send[i4] == 8))) {
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
                byte[] copyOfRange = Arrays.copyOfRange(bArr, i5, i5 + i4);
                i2 = i5 + (i4 - 1);
                arrayList.add(bytesToHex(copyOfRange));
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
        } catch (NoClassDefFoundError unused) {
            Log.e(TAG, "SL-NCDFE");
        } catch (UnsatisfiedLinkError unused2) {
            Log.e(TAG, "SL-ULE");
        } catch (Error unused3) {
            Log.e(TAG, "SL-ER");
        } catch (NullPointerException unused4) {
            Log.e(TAG, "Failed to connect service.");
        } catch (Exception unused5) {
            Log.e(TAG, "SL-EX");
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
        } catch (NoClassDefFoundError unused) {
            Log.e(TAG, "S_SL-NCDFE");
        } catch (UnsatisfiedLinkError unused2) {
            Log.e(TAG, "S_SL-ULE");
        } catch (Error unused3) {
            Log.e(TAG, "S_SL-ER");
        } catch (NullPointerException unused4) {
            Log.e(TAG, "Failed to connect service.");
        } catch (Exception unused5) {
            Log.e(TAG, "S_SL-EX");
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
        } catch (NoClassDefFoundError unused) {
            Log.e(TAG, "ST_SL-NCDFE");
        } catch (UnsatisfiedLinkError unused2) {
            Log.e(TAG, "ST_SL-ULE");
        } catch (Error unused3) {
            Log.e(TAG, "S_SL-ER");
        } catch (NullPointerException unused4) {
            Log.e(TAG, "Failed to connect service.");
        } catch (Exception unused5) {
            Log.e(TAG, "ST_SL-EX");
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
        } catch (NoClassDefFoundError unused) {
            Log.e(TAG, "ST_SL-NCDFE");
        } catch (UnsatisfiedLinkError unused2) {
            Log.e(TAG, "ST_SL-ULE");
        } catch (Error unused3) {
            Log.e(TAG, "S_SL-ER");
        } catch (NullPointerException unused4) {
            Log.e(TAG, "Failed to connect service.");
        } catch (Exception unused5) {
            Log.e(TAG, "ST_SL-EX");
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
        int i2 = -1;
        if (iSemService == null) {
            Log.e(TAG, "SemService is not connected");
            return -1;
        }
        try {
            i2 = iSemService.resetForCOSU();
            Log.i(TAG, "resetForCOSU : " + i2);
            return i2;
        } catch (Exception e) {
            Log.e(TAG, "Call resetForCOSU Exception " + e);
            return i2;
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
            int openSpi = openSpi(0);
            try {
                if (openSpi == -200) {
                    Log.d(TAG, "eSE is busy now");
                    return true;
                }
                if (openSpi == 0) {
                    Log.d(TAG, "eSE is NOT busy");
                    closeSpi(0);
                } else {
                    Log.e(TAG, "eSE returned error value : " + openSpi);
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
        int i;
        Log.i(TAG, "send() for SE API is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        byte[] bArr2 = new byte[65538];
        if (bArr != null) {
            i = bArr.length;
            Log.d(TAG, "Len : " + i);
        } else {
            i = 0;
        }
        try {
            int send_Data = this.mSemService.send_Data(bArr, i, bArr2, 0);
            if (send_Data < 1) {
                Log.e(TAG, "RSP is null");
                return null;
            }
            byte[] copyOf = Arrays.copyOf(bArr2, send_Data);
            Log.d(TAG, "baRsp : " + bytesToHex(copyOf));
            return copyOf;
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
        int i2;
        Log.i(TAG, "send() for normal/secure SPI is called.");
        if (!isSupportSemServiceManager) {
            Log.i(TAG, "SemService is not supported");
            return null;
        }
        byte[] bArr2 = new byte[65538];
        if (bArr != null) {
            i2 = bArr.length;
            Log.d(TAG, "Len : " + i2);
        } else {
            i2 = 0;
        }
        try {
            int send_Data = this.mSemService.send_Data(bArr, i2, bArr2, i);
            if (send_Data < 1) {
                Log.e(TAG, "RSP is null");
                return null;
            }
            byte[] copyOf = Arrays.copyOf(bArr2, send_Data);
            Log.d(TAG, "baRsp : " + bytesToHex(copyOf));
            return copyOf;
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
