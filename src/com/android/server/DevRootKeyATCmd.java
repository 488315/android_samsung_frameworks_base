package com.android.server;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemProperties;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore2.AndroidKeyStoreSpi;
import android.telecom.Logging.Session;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager;
import com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager;
import com.samsung.android.service.DeviceRootKeyService.Tlv;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.ProviderException;
import java.security.SignatureException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes6.dex */
public class DevRootKeyATCmd implements IWorkOnAt {
    protected static final String AT_COMMAND_DEVROOTK = "DEVROOTK";
    protected static final String AT_COMMAND_HEADER = "AT";
    protected static final int MODE_MNFR_ALLOW_ATCMD = 28;
    protected static final String TAG = "DEVROOT#ATCmd(1.0.0)";
    protected static final String VERSION = "1.0.0";
    static int getKeyWaitTime;
    protected static final boolean isExceptionProduct;
    protected static final boolean isSupportATCommandV2;
    protected static final String productName;
    protected boolean isJDMProductNotInHouse = false;
    protected Context mContext;
    private DeviceIDProvisionManager mDeviceIDProvisionManager;
    private DeviceRootKeyServiceManager mDeviceRootKeyServiceManager;
    private Tlv mTlv;
    private String mTlvKeyBlob;
    private int mTlvKeyBlobCounter;

    private native int isExistDRK(int i);

    private native boolean isSupportedDrkV2();

    private native String readDrkUID(int i);

    private native byte[] readKeyInfo(int i);

    protected native byte[] generateCertificateSigningRequest(int i, String str, String str2);

    protected native int installDeviceBoundCertificate(int i, byte[] bArr);

    protected native int installDeviceUnboundKey(int i, byte[] bArr);

    protected native int validateDeviceKey(int i);

    static {
        String str = SystemProperties.get("ro.product.system.name");
        productName = str;
        boolean z = false;
        boolean z2 = str.contains("a36xq") || str.contains("gtact5pro");
        isExceptionProduct = z2;
        if (Integer.parseInt(SystemProperties.get("ro.product.first_api_level")) >= 35 && !z2) {
            z = true;
        }
        isSupportATCommandV2 = z;
        System.loadLibrary("_nativeJni.dk.samsung");
        getKeyWaitTime = 50;
    }

    public DevRootKeyATCmd(Context context) {
        this.mContext = context;
        initTlvKeyBlob();
        this.mDeviceRootKeyServiceManager = new DeviceRootKeyServiceManager(context.getApplicationContext());
        this.mDeviceIDProvisionManager = new DeviceIDProvisionManager(context.getApplicationContext());
        if (DeviceIDProvisionManager.isAvailable()) {
            this.mDeviceIDProvisionManager.provisionForFirstBoot();
        }
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_DEVROOTK;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:153:0x05d3  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x05d6 A[Catch: Exception -> 0x0678, TryCatch #0 {Exception -> 0x0678, blocks: (B:8:0x001b, B:10:0x004d, B:16:0x006f, B:29:0x00ba, B:177:0x0663, B:30:0x00e8, B:32:0x0101, B:33:0x0112, B:35:0x0118, B:37:0x0123, B:39:0x0141, B:40:0x0161, B:42:0x0167, B:44:0x0184, B:46:0x0198, B:48:0x019d, B:45:0x018f, B:49:0x01b1, B:36:0x011f, B:50:0x01c2, B:52:0x01da, B:53:0x01eb, B:55:0x01f1, B:57:0x0200, B:59:0x021e, B:60:0x023f, B:62:0x0245, B:64:0x0253, B:65:0x0266, B:66:0x0269, B:56:0x01fa, B:67:0x0279, B:69:0x0285, B:71:0x0299, B:73:0x02b3, B:74:0x02c8, B:75:0x02d8, B:77:0x02e0, B:79:0x02f5, B:81:0x0310, B:82:0x0321, B:84:0x0327, B:86:0x0336, B:88:0x0354, B:89:0x0376, B:91:0x037c, B:94:0x0383, B:96:0x0391, B:97:0x03a5, B:85:0x0330, B:98:0x03b6, B:101:0x03c1, B:103:0x03cd, B:104:0x03de, B:106:0x03f6, B:107:0x040c, B:108:0x041d, B:110:0x0427, B:112:0x043c, B:114:0x044e, B:117:0x0455, B:119:0x0460, B:121:0x0482, B:120:0x0470, B:122:0x0487, B:124:0x04ab, B:125:0x04bc, B:126:0x04de, B:127:0x04f1, B:130:0x04fa, B:132:0x0500, B:133:0x0511, B:134:0x0522, B:137:0x052b, B:139:0x0532, B:140:0x0543, B:141:0x0554, B:143:0x055b, B:144:0x056c, B:145:0x057f, B:147:0x0586, B:148:0x0596, B:149:0x05a9, B:151:0x05b0, B:152:0x05c0, B:154:0x05d6, B:156:0x05ea, B:158:0x05f9, B:160:0x0600, B:161:0x0610, B:162:0x0623, B:165:0x062a, B:169:0x0631, B:173:0x063a, B:175:0x0643, B:176:0x0653, B:12:0x0055, B:14:0x005f), top: B:182:0x001b }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01b1 A[Catch: Exception -> 0x0678, TryCatch #0 {Exception -> 0x0678, blocks: (B:8:0x001b, B:10:0x004d, B:16:0x006f, B:29:0x00ba, B:177:0x0663, B:30:0x00e8, B:32:0x0101, B:33:0x0112, B:35:0x0118, B:37:0x0123, B:39:0x0141, B:40:0x0161, B:42:0x0167, B:44:0x0184, B:46:0x0198, B:48:0x019d, B:45:0x018f, B:49:0x01b1, B:36:0x011f, B:50:0x01c2, B:52:0x01da, B:53:0x01eb, B:55:0x01f1, B:57:0x0200, B:59:0x021e, B:60:0x023f, B:62:0x0245, B:64:0x0253, B:65:0x0266, B:66:0x0269, B:56:0x01fa, B:67:0x0279, B:69:0x0285, B:71:0x0299, B:73:0x02b3, B:74:0x02c8, B:75:0x02d8, B:77:0x02e0, B:79:0x02f5, B:81:0x0310, B:82:0x0321, B:84:0x0327, B:86:0x0336, B:88:0x0354, B:89:0x0376, B:91:0x037c, B:94:0x0383, B:96:0x0391, B:97:0x03a5, B:85:0x0330, B:98:0x03b6, B:101:0x03c1, B:103:0x03cd, B:104:0x03de, B:106:0x03f6, B:107:0x040c, B:108:0x041d, B:110:0x0427, B:112:0x043c, B:114:0x044e, B:117:0x0455, B:119:0x0460, B:121:0x0482, B:120:0x0470, B:122:0x0487, B:124:0x04ab, B:125:0x04bc, B:126:0x04de, B:127:0x04f1, B:130:0x04fa, B:132:0x0500, B:133:0x0511, B:134:0x0522, B:137:0x052b, B:139:0x0532, B:140:0x0543, B:141:0x0554, B:143:0x055b, B:144:0x056c, B:145:0x057f, B:147:0x0586, B:148:0x0596, B:149:0x05a9, B:151:0x05b0, B:152:0x05c0, B:154:0x05d6, B:156:0x05ea, B:158:0x05f9, B:160:0x0600, B:161:0x0610, B:162:0x0623, B:165:0x062a, B:169:0x0631, B:173:0x063a, B:175:0x0643, B:176:0x0653, B:12:0x0055, B:14:0x005f), top: B:182:0x001b }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x03a5 A[Catch: Exception -> 0x0678, TryCatch #0 {Exception -> 0x0678, blocks: (B:8:0x001b, B:10:0x004d, B:16:0x006f, B:29:0x00ba, B:177:0x0663, B:30:0x00e8, B:32:0x0101, B:33:0x0112, B:35:0x0118, B:37:0x0123, B:39:0x0141, B:40:0x0161, B:42:0x0167, B:44:0x0184, B:46:0x0198, B:48:0x019d, B:45:0x018f, B:49:0x01b1, B:36:0x011f, B:50:0x01c2, B:52:0x01da, B:53:0x01eb, B:55:0x01f1, B:57:0x0200, B:59:0x021e, B:60:0x023f, B:62:0x0245, B:64:0x0253, B:65:0x0266, B:66:0x0269, B:56:0x01fa, B:67:0x0279, B:69:0x0285, B:71:0x0299, B:73:0x02b3, B:74:0x02c8, B:75:0x02d8, B:77:0x02e0, B:79:0x02f5, B:81:0x0310, B:82:0x0321, B:84:0x0327, B:86:0x0336, B:88:0x0354, B:89:0x0376, B:91:0x037c, B:94:0x0383, B:96:0x0391, B:97:0x03a5, B:85:0x0330, B:98:0x03b6, B:101:0x03c1, B:103:0x03cd, B:104:0x03de, B:106:0x03f6, B:107:0x040c, B:108:0x041d, B:110:0x0427, B:112:0x043c, B:114:0x044e, B:117:0x0455, B:119:0x0460, B:121:0x0482, B:120:0x0470, B:122:0x0487, B:124:0x04ab, B:125:0x04bc, B:126:0x04de, B:127:0x04f1, B:130:0x04fa, B:132:0x0500, B:133:0x0511, B:134:0x0522, B:137:0x052b, B:139:0x0532, B:140:0x0543, B:141:0x0554, B:143:0x055b, B:144:0x056c, B:145:0x057f, B:147:0x0586, B:148:0x0596, B:149:0x05a9, B:151:0x05b0, B:152:0x05c0, B:154:0x05d6, B:156:0x05ea, B:158:0x05f9, B:160:0x0600, B:161:0x0610, B:162:0x0623, B:165:0x062a, B:169:0x0631, B:173:0x063a, B:175:0x0643, B:176:0x0653, B:12:0x0055, B:14:0x005f), top: B:182:0x001b }] */
    @Override // com.android.server.IWorkOnAt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String processCmd(String str) throws NumberFormatException {
        String string;
        String str2;
        String str3;
        String str4;
        String str5 = TAG;
        String str6 = new String();
        String[] strArrParsingParam = parsingParam(str);
        if (strArrParsingParam == null || strArrParsingParam.length != 3) {
            return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
        }
        try {
            Log.i(TAG, "ProcessCmd [" + str + "] start");
            String str7 = strArrParsingParam[0] + ",";
            if ((Integer.parseInt(strArrParsingParam[0]) == 0 || Integer.parseInt(strArrParsingParam[0]) == 1) && !strArrParsingParam[2].equals("0")) {
                return str7 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
            }
            int i = Integer.parseInt(strArrParsingParam[0] + strArrParsingParam[1]);
            if (i == 0) {
                if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                    int iIsExistDRK = isExistDRK(1);
                    if (iIsExistDRK == 0) {
                        string = str7 + SecureKeyConst.AT_RESPONSE_OK;
                    } else {
                        string = str7 + "NG_FAIL " + iIsExistDRK;
                    }
                } else {
                    if (!isValidEM()) {
                        return SecureKeyConst.AT_RESPONSE_NO_EM_TOKEN;
                    }
                    DeviceRootKeyServiceManager deviceRootKeyServiceManager = this.mDeviceRootKeyServiceManager;
                    if (deviceRootKeyServiceManager == null) {
                        return SecureKeyConst.AT_RESPONSE_INSTANCE_ERROR;
                    }
                    if (!deviceRootKeyServiceManager.isAliveDeviceRootKeyService()) {
                        return SecureKeyConst.AT_RESPONSE_CONN_FAILED;
                    }
                    if (this.mDeviceRootKeyServiceManager.isExistDeviceRootKey(1)) {
                        string = str7 + SecureKeyConst.AT_RESPONSE_OK;
                    } else {
                        string = str7 + SecureKeyConst.AT_RESPONSE_FAILED;
                    }
                }
                str2 = string;
            } else if (i == 1) {
                string = str7 + SecureKeyConst.AT_RESPONSE_PMK_OK;
                str2 = string;
            } else {
                if (i != 2) {
                    if (i == 3) {
                        int iValidateDeviceKey = validateDeviceKey(3);
                        if (iValidateDeviceKey == 0) {
                            string = str7 + SecureKeyConst.AT_RESPONSE_OK;
                        } else {
                            string = str7 + "NG_FAIL " + iValidateDeviceKey;
                        }
                    } else if (i == 4) {
                        int iCheckKeyValidity = checkKeyValidity(1);
                        if (iCheckKeyValidity == 0) {
                            string = str7 + SecureKeyConst.AT_RESPONSE_OK;
                        } else {
                            string = str7 + "NG_FAIL " + iCheckKeyValidity;
                        }
                    } else {
                        if (i != 5) {
                            switch (i) {
                                case 10:
                                    if (isValidEM()) {
                                        String drkUID = readDrkUID(1);
                                        if (drkUID == null) {
                                            string = str7 + SecureKeyConst.AT_RESPONSE_FAILED;
                                            break;
                                        } else {
                                            string = str7 + drkUID;
                                            break;
                                        }
                                    }
                                    break;
                                case 11:
                                    break;
                                case 12:
                                    if (isValidEM()) {
                                        if (!isSupportedDrkV2()) {
                                            string = str7 + SecureKeyConst.AT_RESPONSE_FAILED;
                                            break;
                                        } else {
                                            string = str7 + SecureKeyConst.AT_RESPONSE_OK;
                                            break;
                                        }
                                    }
                                    break;
                                case 13:
                                    string = str7 + SecureKeyConst.AT_RESPONSE_UNIMPLEMENTED;
                                    break;
                                default:
                                    switch (i) {
                                        case 20:
                                            if (!SecureKeyConst.checkblockDrkWriting()) {
                                                str5 = TAG;
                                                String strSubstring = strArrParsingParam[2].substring(0, 2);
                                                if (!strSubstring.equals(SecureKeyConst.AT_CMD_DRK_V1_WRITING_END)) {
                                                    int i2 = Integer.parseInt(strSubstring);
                                                    String str8 = strArrParsingParam[2];
                                                    if (!appendKeyBlob(i2, str8.substring(str8.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, strArrParsingParam[2].length()).trim())) {
                                                        string = str7 + "NG_FAIL(DATA MISSED) SN-" + i2 + " TB-" + (getKeyBlobIndex() + 1);
                                                        break;
                                                    } else {
                                                        string = str7 + SecureKeyConst.AT_RESPONSE_OK;
                                                        break;
                                                    }
                                                } else if (isValidEM()) {
                                                    int iInstallDeviceUnboundKey = installDeviceUnboundKey(0, getTotalKeyBlob());
                                                    if (iInstallDeviceUnboundKey == 0) {
                                                        str2 = str7 + SecureKeyConst.AT_RESPONSE_OK;
                                                    } else {
                                                        str2 = str7 + "NG_FAIL " + iInstallDeviceUnboundKey;
                                                    }
                                                    initTlvKeyBlob();
                                                    break;
                                                }
                                            } else {
                                                Log.i(TAG, "ProcessCmd [" + str + "] is deprecated");
                                                break;
                                            }
                                            break;
                                        case 21:
                                            break;
                                        case 22:
                                            str3 = TAG;
                                            if (isValidEM()) {
                                                if (strArrParsingParam[2].length() != 17) {
                                                    str4 = str7 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                                } else {
                                                    byte[] bArrGenerateCertificateSigningRequest = generateCertificateSigningRequest(2, strArrParsingParam[2].substring(0, 14), strArrParsingParam[2].substring(14, 17));
                                                    if (bArrGenerateCertificateSigningRequest != null) {
                                                        str4 = str7 + new String(bArrGenerateCertificateSigningRequest);
                                                    } else {
                                                        str4 = str7 + SecureKeyConst.AT_RESPONSE_FAILED;
                                                    }
                                                }
                                                str2 = str4;
                                                str5 = str3;
                                                break;
                                            }
                                            break;
                                        case 23:
                                            if (!SecureKeyConst.checkblockDrkWriting()) {
                                                String strSubstring2 = strArrParsingParam[2].substring(2, 5);
                                                String str9 = strArrParsingParam[2];
                                                str3 = TAG;
                                                if (str9.substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                                    int keyBlobIndex = strSubstring2.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(strSubstring2);
                                                    String str10 = strArrParsingParam[2];
                                                    if (!appendKeyBlob(keyBlobIndex, str10.substring(str10.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, strArrParsingParam[2].length()).trim())) {
                                                        str4 = str7 + "NG_FAIL(DATA MISSED) SN-" + keyBlobIndex + " TB-" + (getKeyBlobIndex() + 1);
                                                    } else if (!strSubstring2.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                                        str4 = str7 + SecureKeyConst.AT_RESPONSE_OK;
                                                    } else if (isValidEM()) {
                                                        int iInstallDeviceBoundCertificate = installDeviceBoundCertificate(2, getTotalKeyBlob());
                                                        initTlvKeyBlob();
                                                        if (iInstallDeviceBoundCertificate != 0) {
                                                            str4 = str7 + "NG_FAIL " + iInstallDeviceBoundCertificate;
                                                        }
                                                    }
                                                } else {
                                                    str4 = str7 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                                }
                                                str2 = str4;
                                                str5 = str3;
                                                break;
                                            } else {
                                                Log.i(TAG, "ProcessCmd [" + str + "] is deprecated");
                                                break;
                                            }
                                        case 24:
                                            str3 = TAG;
                                            if (strArrParsingParam[2].length() != 17) {
                                                str4 = str7 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                            } else {
                                                byte[] bArrGenerateCertificateSigningRequest2 = generateCertificateSigningRequest(3, strArrParsingParam[2].substring(0, 14), strArrParsingParam[2].substring(14, 17));
                                                if (bArrGenerateCertificateSigningRequest2 != null) {
                                                    str4 = str7 + new String(bArrGenerateCertificateSigningRequest2);
                                                } else {
                                                    str4 = str7 + SecureKeyConst.AT_RESPONSE_FAILED;
                                                }
                                            }
                                            str2 = str4;
                                            str5 = str3;
                                            break;
                                        case 25:
                                            str3 = TAG;
                                            String strSubstring3 = strArrParsingParam[2].substring(2, 5);
                                            if (strArrParsingParam[2].substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                                int keyBlobIndex2 = strSubstring3.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(strSubstring3);
                                                String str11 = strArrParsingParam[2];
                                                if (!appendKeyBlob(keyBlobIndex2, str11.substring(str11.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, strArrParsingParam[2].length()).trim())) {
                                                    str4 = str7 + "NG_FAIL(DATA MISSED) SN-" + keyBlobIndex2 + " TB-" + (getKeyBlobIndex() + 1);
                                                } else if (strSubstring3.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                                    int iInstallDeviceBoundCertificate2 = installDeviceBoundCertificate(3, getTotalKeyBlob());
                                                    initTlvKeyBlob();
                                                    if (iInstallDeviceBoundCertificate2 != 0) {
                                                        str4 = str7 + "NG_FAIL " + iInstallDeviceBoundCertificate2;
                                                    } else {
                                                        sendSakUidMsgAppletBindingIntent();
                                                        str4 = str7 + SecureKeyConst.AT_RESPONSE_OK;
                                                    }
                                                } else {
                                                    str4 = str7 + SecureKeyConst.AT_RESPONSE_OK;
                                                }
                                            } else {
                                                str4 = str7 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                            }
                                            str2 = str4;
                                            str5 = str3;
                                            break;
                                        case 26:
                                        case 27:
                                            String strSubstring4 = strArrParsingParam[2].substring(2, 5);
                                            String str12 = strArrParsingParam[2];
                                            str3 = TAG;
                                            if (str12.substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                                int keyBlobIndex3 = strSubstring4.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(strSubstring4);
                                                String str13 = strArrParsingParam[2];
                                                if (!appendKeyBlob(keyBlobIndex3, str13.substring(str13.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, strArrParsingParam[2].length()).trim())) {
                                                    str4 = str7 + "NG_FAIL(DATA MISSED) SN-" + keyBlobIndex3 + " TB-" + (getKeyBlobIndex() + 1);
                                                } else if (strSubstring4.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                                    StringBuilder sb = new StringBuilder();
                                                    sb.append(strArrParsingParam[0]);
                                                    sb.append(strArrParsingParam[1]);
                                                    int iInstallDeviceUnboundKey2 = Integer.parseInt(sb.toString()) == 26 ? installDeviceUnboundKey(1, getTotalKeyBlob()) : installDeviceUnboundKey(4, getTotalKeyBlob());
                                                    initTlvKeyBlob();
                                                    if (iInstallDeviceUnboundKey2 != 0) {
                                                        str4 = str7 + "NG_FAIL " + iInstallDeviceUnboundKey2;
                                                    } else {
                                                        str4 = str7 + SecureKeyConst.AT_RESPONSE_OK;
                                                    }
                                                }
                                            } else {
                                                str4 = str7 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                            }
                                            str2 = str4;
                                            str5 = str3;
                                            break;
                                        default:
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(str7);
                                            sb2.append(processTestCmd(Integer.parseInt(strArrParsingParam[0] + strArrParsingParam[1]), strArrParsingParam[2]));
                                            string = sb2.toString();
                                            break;
                                    }
                            }
                            return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                        }
                        int iCheckKeyValidity2 = checkKeyValidity(4);
                        if (iCheckKeyValidity2 == 0) {
                            string = str7 + SecureKeyConst.AT_RESPONSE_OK;
                        } else {
                            string = str7 + "NG_FAIL " + iCheckKeyValidity2;
                        }
                    }
                }
                str2 = string;
            }
            Log.i(str5, "ProcessCmd [" + str + "] end");
            return str2;
        } catch (Exception e) {
            e.printStackTrace();
            return str6 + "NG_FAIL(EXCEPTION_OCCURS) " + e.getMessage();
        }
    }

    public String generateCertWithTlv(boolean z) {
        byte[] bArr = {2, 2, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -17};
        byte[] bArr2 = {3, 2, 2, -4};
        byte[] bArr3 = {SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -122, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 84, 104, 105, 115, 32, 105, 115, 32, 115, 117, 98, 106, 101, 99, 116, 32, SprAttributeBase.TYPE_ANIMATOR_SET, 108, 116, 101, 114, 110, SprAttributeBase.TYPE_ANIMATOR_SET, 116, 105, 118, 101, 32, 110, SprAttributeBase.TYPE_ANIMATOR_SET, 109, 101, 32, 102, 105, 101, 108, 100, 32, 116, 101, 115, 116, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 95};
        byte[] bArr4 = {6, 9, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, -122, 72, -122, -9, 13, 1, 1, 5};
        if (z) {
            Tlv tlv = new Tlv();
            this.mTlv = tlv;
            tlv.setTlv(1, bArr);
            this.mTlv.setTlv(5, bArr2);
            this.mTlv.setTlv(29, bArr3);
            this.mTlv.setTlv(3, bArr4);
        } else {
            this.mTlv = null;
        }
        if (this.mDeviceRootKeyServiceManager.doSelfTestProvService(1, this.mTlv) != null) {
            return SecureKeyConst.AT_RESPONSE_OK;
        }
        return SecureKeyConst.AT_RESPONSE_FAILED;
    }

    public String processTestCmd(int i, String str) {
        if (!"eng".equals(Build.TYPE)) {
            return SecureKeyConst.AT_RESPONSE_UNIMPLEMENTED;
        }
        DeviceRootKeyServiceManager deviceRootKeyServiceManager = this.mDeviceRootKeyServiceManager;
        if (deviceRootKeyServiceManager == null) {
            return SecureKeyConst.AT_RESPONSE_INSTANCE_ERROR;
        }
        try {
            switch (i) {
                case 90:
                    return !deviceRootKeyServiceManager.isAliveDeviceRootKeyService() ? SecureKeyConst.AT_RESPONSE_CONN_FAILED : SecureKeyConst.AT_RESPONSE_OK;
                case 91:
                    return isSupportedDrkV2() ? SecureKeyConst.AT_RESPONSE_OK : SecureKeyConst.AT_RESPONSE_FAILED;
                case 92:
                    return !deviceRootKeyServiceManager.isExistDeviceRootKey(1) ? SecureKeyConst.AT_RESPONSE_FAILED : SecureKeyConst.AT_RESPONSE_OK;
                case 93:
                    return deviceRootKeyServiceManager.getDeviceRootKeyUID(1) == null ? SecureKeyConst.AT_RESPONSE_FAILED : SecureKeyConst.AT_RESPONSE_OK;
                case 94:
                    return deviceRootKeyServiceManager.getDeviceRootKeyCertificate(1) == null ? SecureKeyConst.AT_RESPONSE_FAILED : SecureKeyConst.AT_RESPONSE_OK;
                case 95:
                    return generateCertWithTlv(false);
                case 96:
                    return generateCertWithTlv(true);
                case 97:
                    return deviceRootKeyServiceManager.getDeviceInfo(14) == null ? SecureKeyConst.AT_RESPONSE_FAILED : SecureKeyConst.AT_RESPONSE_OK;
                default:
                    return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "NG_FAIL(EXCEPTION_OCCURS) " + e.getMessage();
        }
    }

    protected String[] parsingParam(String str) {
        try {
            return str.substring(0, str.length()).split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected boolean appendKeyBlob(int i, String str) {
        if (i == 1) {
            this.mTlvKeyBlobCounter = i;
            this.mTlvKeyBlob = str;
        } else if (i == this.mTlvKeyBlobCounter + 1) {
            this.mTlvKeyBlobCounter = i;
            this.mTlvKeyBlob += str;
        } else {
            initTlvKeyBlob();
            return false;
        }
        return true;
    }

    protected byte[] getTotalKeyBlob() {
        return this.mTlvKeyBlob.getBytes();
    }

    protected int getKeyBlobIndex() {
        return this.mTlvKeyBlobCounter;
    }

    protected void initTlvKeyBlob() {
        this.mTlvKeyBlobCounter = 0;
        this.mTlvKeyBlob = "";
    }

    protected int checkKeyValidity(int i) throws InterruptedException, NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
        int iProvisionForATCommand = 0;
        boolean z = (Integer.parseInt(SystemProperties.get("ro.product.first_api_level")) >= 33) && ((Integer.parseInt(SystemProperties.get("ro.board.first_api_level")) >= 33) || (SystemProperties.get("ro.build.flavor", "").contains("a14m") || SystemProperties.get("ro.build.flavor", "").contains("a14xm") || SystemProperties.get("ro.build.flavor", "").contains("a24") || SystemProperties.get("ro.build.flavor", "").contains("a34x")));
        if (z) {
            if (i == 1) {
                iProvisionForATCommand = this.mDeviceIDProvisionManager.provisionForATCommand(6);
            } else if (i == 4) {
                iProvisionForATCommand = this.mDeviceIDProvisionManager.provisionForATCommand(7);
            }
            if (iProvisionForATCommand != 0) {
                Log.e(TAG, "installDeviceID failed");
                return iProvisionForATCommand;
            }
        }
        int iValidateDeviceKey = validateDeviceKey(i);
        if (iValidateDeviceKey != 0) {
            Log.e(TAG, "validateDeviceKey failed");
            return iValidateDeviceKey;
        }
        if (i != 1 && i != 4) {
            return iValidateDeviceKey;
        }
        int iValidateDeviceKeyFromKeystore = validateDeviceKeyFromKeystore(i, z);
        if (iValidateDeviceKeyFromKeystore != 0) {
            Log.e(TAG, "validateDeviceKeyFromKeystore failed");
        }
        return iValidateDeviceKeyFromKeystore;
    }

    protected int provisionDeviceID(int i) throws InterruptedException {
        int iProvisionForATCommand = 0;
        boolean z = Integer.parseInt(SystemProperties.get("ro.product.first_api_level")) >= 33;
        boolean z2 = Integer.parseInt(SystemProperties.get("ro.vendor.build.version.sdk")) >= 33;
        boolean z3 = SystemProperties.get("ro.build.flavor", "").contains("a14m") || SystemProperties.get("ro.build.flavor", "").contains("a14xm") || SystemProperties.get("ro.build.flavor", "").contains("a24") || SystemProperties.get("ro.build.flavor", "").contains("a34x");
        if (z && (z2 || z3)) {
            if (i == 1) {
                iProvisionForATCommand = this.mDeviceIDProvisionManager.provisionForATCommand(6);
            } else if (i == 4) {
                iProvisionForATCommand = this.mDeviceIDProvisionManager.provisionForATCommand(7);
            }
            if (iProvisionForATCommand != 0) {
                Log.e(TAG, "installDeviceID failed");
            }
        }
        return iProvisionForATCommand;
    }

    protected boolean isEqualsRootPubKey(Certificate certificate, int i) throws CertificateException {
        byte[] encoded = ((X509Certificate) certificate).getPublicKey().getEncoded();
        if (i == 1 || i == 4) {
            return Arrays.equals(encoded, SecureKeyConst.GoogleRootPubKey) || Arrays.equals(encoded, SecureKeyConst.GoogleDevRootPubKey);
        }
        return false;
    }

    protected int validateDeviceKeyFromKeystore(int i, boolean z) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
        int i2;
        int i3;
        String meid;
        int i4;
        int i5;
        String meid2;
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder("gak_ec_alias", 12);
            builder.setKeySize(256);
            builder.setDigests("SHA-256", KeyProperties.DIGEST_NONE);
            builder.setAttestationChallenge("gak_ec_challenge".getBytes(Charset.forName("UTF-8")));
            i2 = SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
            if (z) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
                    i3 = 3;
                    ArrayList arrayList = new ArrayList();
                    String serial = Build.getSerial();
                    String imei = telephonyManager.getImei(0);
                    try {
                        meid = telephonyManager.getMeid(0);
                    } catch (UnsupportedOperationException e) {
                        e.printStackTrace();
                        meid = null;
                    }
                    if (serial == null || serial.length() == 0) {
                        i4 = 2;
                    } else {
                        i4 = 2;
                        arrayList.add(1);
                    }
                    if (imei != null && imei.length() != 0) {
                        arrayList.add(Integer.valueOf(i4));
                    }
                    if (meid != null && meid.length() != 0) {
                        arrayList.add(3);
                    }
                    int size = arrayList.size();
                    int[] iArr = new int[size];
                    i5 = 0;
                    for (int i6 = 0; i6 < size; i6++) {
                        iArr[i6] = ((Integer) arrayList.get(i6)).intValue();
                    }
                    builder.setDevicePropertiesAttestationIncluded(true);
                    builder.setAttestationIds(iArr);
                } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e2) {
                    e = e2;
                    e.printStackTrace();
                    return i2;
                }
            } else {
                i5 = 0;
                i3 = 3;
                i4 = 2;
            }
            if (i == 4) {
                builder.setIsStrongBoxBacked(true);
            }
            KeyGenParameterSpec keyGenParameterSpecBuild = builder.build();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, AndroidKeyStoreSpi.NAME);
            keyPairGenerator.initialize(keyGenParameterSpecBuild);
            keyPairGenerator.generateKeyPair();
            Certificate[] certificateChain = keyStore.getCertificateChain("gak_ec_alias");
            if (!verifyCertChains(certificateChain)) {
                return SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
            }
            ((X509Certificate) certificateChain[i5]).verify(((X509Certificate) certificateChain[1]).getPublicKey());
            if (!isEqualsRootPubKey(certificateChain[certificateChain.length - 1], i)) {
                return SecureKeyConst.ERR_KEYMASTER_GAK_ROOT_FAIL;
            }
            KeyGenParameterSpec.Builder builder2 = new KeyGenParameterSpec.Builder("gak_rsa_alias", 12);
            builder2.setKeySize(2048);
            builder2.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PSS, KeyProperties.SIGNATURE_PADDING_RSA_PKCS1);
            builder2.setDigests("SHA-256", "SHA-1");
            builder2.setAttestationChallenge("gak_rsa_challenge".getBytes(Charset.forName("UTF-8")));
            if (z) {
                TelephonyManager telephonyManager2 = (TelephonyManager) this.mContext.getSystemService("phone");
                ArrayList arrayList2 = new ArrayList();
                String serial2 = Build.getSerial();
                int i7 = i5;
                String imei2 = telephonyManager2.getImei(i7);
                try {
                    meid2 = telephonyManager2.getMeid(i7);
                } catch (UnsupportedOperationException e3) {
                    e3.printStackTrace();
                    meid2 = null;
                }
                if (serial2 != null && serial2.length() != 0) {
                    arrayList2.add(1);
                }
                if (imei2 != null && imei2.length() != 0) {
                    arrayList2.add(Integer.valueOf(i4));
                }
                if (meid2 != null && meid2.length() != 0) {
                    arrayList2.add(Integer.valueOf(i3));
                }
                int size2 = arrayList2.size();
                int[] iArr2 = new int[size2];
                for (int i8 = 0; i8 < size2; i8++) {
                    iArr2[i8] = ((Integer) arrayList2.get(i8)).intValue();
                }
                builder2.setDevicePropertiesAttestationIncluded(true);
                builder2.setAttestationIds(iArr2);
            }
            if (i == 4) {
                builder2.setIsStrongBoxBacked(true);
            }
            KeyGenParameterSpec keyGenParameterSpecBuild2 = builder2.build();
            KeyPairGenerator keyPairGenerator2 = KeyPairGenerator.getInstance("RSA", AndroidKeyStoreSpi.NAME);
            keyPairGenerator2.initialize(keyGenParameterSpecBuild2);
            keyPairGenerator2.generateKeyPair();
            Certificate[] certificateChain2 = keyStore.getCertificateChain("gak_rsa_alias");
            if (!verifyCertChains(certificateChain2)) {
                return SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
            }
            ((X509Certificate) certificateChain2[0]).verify(((X509Certificate) certificateChain2[1]).getPublicKey());
            if (isEqualsRootPubKey(certificateChain2[certificateChain2.length - 1], i)) {
                return 0;
            }
            return SecureKeyConst.ERR_KEYMASTER_GAK_ROOT_FAIL;
        } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e4) {
            e = e4;
            i2 = SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
        }
    }

    protected boolean verifyCertChains(Certificate[] certificateArr) throws NoSuchAlgorithmException, CertificateException, CertPathValidatorException, NoSuchProviderException, InvalidAlgorithmParameterException {
        ArrayList arrayList = new ArrayList();
        int length = certificateArr.length;
        for (int i = 1; i < length; i++) {
            arrayList.add((X509Certificate) certificateArr[i]);
        }
        return verifyCertChains(arrayList);
    }

    protected boolean verifyCertChains(List<X509Certificate> list) throws NoSuchAlgorithmException, CertificateException, CertPathValidatorException, NoSuchProviderException, InvalidAlgorithmParameterException {
        int size = list.size();
        if (size == 0) {
            Log.e(TAG, "certification chain size is invalid");
            return false;
        }
        int i = size - 1;
        X509Certificate x509Certificate = list.get(i);
        X509Certificate x509Certificate2 = list.get(0);
        CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(list));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(x509Certificate2.getNotBefore());
        calendar.add(5, 2);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(list.get(i2));
        }
        CertPath certPathGenerateCertPath = CertificateFactory.getInstance("X.509").generateCertPath(arrayList);
        HashSet hashSet = new HashSet();
        hashSet.add(new TrustAnchor(x509Certificate, null));
        CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
        PKIXParameters pKIXParameters = new PKIXParameters(hashSet);
        pKIXParameters.addCertStore(certStore);
        pKIXParameters.setDate(calendar.getTime());
        pKIXParameters.setRevocationEnabled(false);
        if (((PKIXCertPathValidatorResult) certPathValidator.validate(certPathGenerateCertPath, pKIXParameters)).getPublicKey().equals(x509Certificate2.getPublicKey())) {
            return true;
        }
        Log.e(TAG, "wrong public key returned");
        return false;
    }

    protected boolean isValidEM() {
        if (!SecureKeyConst.isJDM) {
            Log.i(TAG, "It is not a JDM project");
            return true;
        }
        if (SecureKeyConst.isDevDevice.equals("0x1")) {
            Log.i(TAG, "It is not A User Product Device");
            return true;
        }
        EngineeringModeManager engineeringModeManager = new EngineeringModeManager(this.mContext.getApplicationContext());
        if (!engineeringModeManager.isConnected()) {
            Log.e(TAG, "Failed to connect to em service");
            return false;
        }
        if (engineeringModeManager.getStatus(28) == 1) {
            Log.i(TAG, "EM Status : Permitted");
            return true;
        }
        Log.e(TAG, "EM Status : Not Permitted");
        return false;
    }

    protected void sendSakUidMsgAppletBindingIntent() {
        Intent intent = new Intent("com.samsung.android.ese.test.action.REQUEST");
        intent.putExtra("com.samsung.android.ese.test.extra.ID", 21);
        intent.putExtra("com.samsung.android.ese.test.extra.CMD", 19);
        intent.setPackage("com.sem.factoryapp");
        this.mContext.getApplicationContext().sendBroadcast(intent, "com.samsung.permission.ESE_FACTORY");
    }
}
