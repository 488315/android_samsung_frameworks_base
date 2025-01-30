package com.android.server;

import android.content.Context;
import android.p009os.Build;
import android.p009os.SystemProperties;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore2.AndroidKeyStoreSpi;
import android.telecom.Logging.Session;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
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
import java.security.PublicKey;
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
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public class DevRootKeyATCmd implements IWorkOnAt {
    protected static final String AT_COMMAND_DEVROOTK = "DEVROOTK";
    protected static final String AT_COMMAND_HEADER = "AT";
    protected static final int MODE_MNFR_ALLOW_ATCMD = 28;
    protected static final String TAG = "DEVROOT#ATCmd(1.0.0)";
    protected static final String VERSION = "1.0.0";
    static int getKeyWaitTime;
    protected static final boolean isSupportnewSAKatcmd;
    protected boolean isJDMProductNotInHouse = false;
    protected Context mContext;
    private DeviceRootKeyServiceManager mDeviceRootKeyServiceManager;
    private Tlv mTlv;
    private String mTlvKeyBlob;
    private int mTlvKeyBlobCounter;

    private native byte[] generateCertificateSigningRequest(int i, String str, String str2);

    private native int installDeviceBoundCertificate(int i, byte[] bArr);

    private native int installDeviceID(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9);

    private native int installDeviceUnboundKey(int i, byte[] bArr);

    private native int isExistDRK(int i);

    private native boolean isSupportedDrkV2();

    private native String readDrkUID(int i);

    private native byte[] readKeyInfo(int i);

    private native int validateDeviceKey(int i);

    static {
        isSupportnewSAKatcmd = Integer.parseInt(SystemProperties.get("ro.product.first_api_level")) > 33;
        System.loadLibrary("_nativeJni.dk.samsung");
        getKeyWaitTime = 50;
    }

    public DevRootKeyATCmd(Context context) {
        this.mContext = context;
        initTlvKeyBlob();
        this.mDeviceRootKeyServiceManager = new DeviceRootKeyServiceManager(context.getApplicationContext());
        DeviceIDProvisionManager deviceIDProvisionManager = new DeviceIDProvisionManager(context.getApplicationContext());
        if (deviceIDProvisionManager.isAvailable()) {
            deviceIDProvisionManager.provision();
        }
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_DEVROOTK;
    }

    /* JADX WARN: Code restructure failed: missing block: B:202:0x005d, code lost:
    
        if (java.lang.Integer.parseInt(r6[0]) == 1) goto L16;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v103, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v100 */
    /* JADX WARN: Type inference failed for: r7v101 */
    /* JADX WARN: Type inference failed for: r7v102 */
    /* JADX WARN: Type inference failed for: r7v103 */
    /* JADX WARN: Type inference failed for: r7v104 */
    /* JADX WARN: Type inference failed for: r7v105 */
    /* JADX WARN: Type inference failed for: r7v106 */
    /* JADX WARN: Type inference failed for: r7v107 */
    /* JADX WARN: Type inference failed for: r7v108 */
    /* JADX WARN: Type inference failed for: r7v109 */
    /* JADX WARN: Type inference failed for: r7v110 */
    /* JADX WARN: Type inference failed for: r7v111 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v73 */
    /* JADX WARN: Type inference failed for: r7v74 */
    /* JADX WARN: Type inference failed for: r7v75 */
    /* JADX WARN: Type inference failed for: r7v76 */
    /* JADX WARN: Type inference failed for: r7v77 */
    /* JADX WARN: Type inference failed for: r7v78 */
    /* JADX WARN: Type inference failed for: r7v79 */
    /* JADX WARN: Type inference failed for: r7v80 */
    /* JADX WARN: Type inference failed for: r7v81 */
    /* JADX WARN: Type inference failed for: r7v82 */
    /* JADX WARN: Type inference failed for: r7v83 */
    /* JADX WARN: Type inference failed for: r7v84 */
    /* JADX WARN: Type inference failed for: r7v85 */
    /* JADX WARN: Type inference failed for: r7v86 */
    /* JADX WARN: Type inference failed for: r7v87 */
    /* JADX WARN: Type inference failed for: r7v88 */
    /* JADX WARN: Type inference failed for: r7v89 */
    /* JADX WARN: Type inference failed for: r7v90 */
    /* JADX WARN: Type inference failed for: r7v91 */
    /* JADX WARN: Type inference failed for: r7v92 */
    /* JADX WARN: Type inference failed for: r7v93 */
    /* JADX WARN: Type inference failed for: r7v94 */
    /* JADX WARN: Type inference failed for: r7v95 */
    /* JADX WARN: Type inference failed for: r7v96 */
    /* JADX WARN: Type inference failed for: r7v97 */
    /* JADX WARN: Type inference failed for: r7v98 */
    /* JADX WARN: Type inference failed for: r7v99 */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.lang.StringBuilder] */
    @Override // com.android.server.IWorkOnAt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String processCmd(String str) {
        String str2;
        int i;
        int i2;
        int i3;
        ?? r2 = str;
        String str3 = new String();
        String[] parsingParam = parsingParam(str);
        ?? r7 = SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
        if (parsingParam == null || parsingParam.length != 3) {
            return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
        }
        try {
            Log.m98i(TAG, "ProcessCmd [" + r2 + "] start");
            str3 = parsingParam[0] + ",";
            if (Integer.parseInt(parsingParam[0]) != 0) {
                try {
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    return str3 + "NG_FAIL(EXCEPTION_OCCURS) " + e.getMessage();
                }
            }
            if (!parsingParam[2].equals("0")) {
                return str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
            }
            try {
                try {
                    try {
                        switch (Integer.parseInt(parsingParam[0] + parsingParam[1])) {
                            case 0:
                            case 2:
                                str2 = TAG;
                                if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                                    int isExistDRK = isExistDRK(1);
                                    if (isExistDRK == 0) {
                                        str3 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                        r7 = r7;
                                    } else {
                                        str3 = str3 + "NG_FAIL " + isExistDRK;
                                        r7 = r7;
                                    }
                                } else if (isValidEM()) {
                                    DeviceRootKeyServiceManager deviceRootKeyServiceManager = this.mDeviceRootKeyServiceManager;
                                    if (deviceRootKeyServiceManager != null) {
                                        if (deviceRootKeyServiceManager.isAliveDeviceRootKeyService()) {
                                            if (this.mDeviceRootKeyServiceManager.isExistDeviceRootKey(1)) {
                                                str3 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                                r7 = r7;
                                            } else {
                                                str3 = str3 + SecureKeyConst.AT_RESPONSE_FAILED;
                                                r7 = r7;
                                            }
                                        }
                                    }
                                }
                                try {
                                    r2 = new StringBuilder();
                                    Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                    break;
                                } catch (Exception e2) {
                                    e = e2;
                                    e.printStackTrace();
                                    return str3 + "NG_FAIL(EXCEPTION_OCCURS) " + e.getMessage();
                                }
                                break;
                            case 1:
                            case 11:
                            case 21:
                                str2 = TAG;
                                str3 = str3 + SecureKeyConst.AT_RESPONSE_PMK_OK;
                                r7 = r7;
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 3:
                                str2 = TAG;
                                int validateDeviceKey = validateDeviceKey(3);
                                if (validateDeviceKey == 0) {
                                    str3 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                    r7 = r7;
                                } else {
                                    str3 = str3 + "NG_FAIL " + validateDeviceKey;
                                    r7 = r7;
                                }
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 4:
                                str2 = TAG;
                                int checkKeyValidity = checkKeyValidity(1);
                                if (checkKeyValidity == 0) {
                                    str3 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                    r7 = r7;
                                } else {
                                    str3 = str3 + "NG_FAIL " + checkKeyValidity;
                                    r7 = r7;
                                }
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 5:
                                str2 = TAG;
                                int checkKeyValidity2 = checkKeyValidity(4);
                                if (checkKeyValidity2 == 0) {
                                    str3 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                    r7 = r7;
                                } else {
                                    str3 = str3 + "NG_FAIL " + checkKeyValidity2;
                                    r7 = r7;
                                }
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 14:
                            case 15:
                            case 16:
                            case 17:
                            case 18:
                            case 19:
                            default:
                                str2 = TAG;
                                str3 = str3 + processTestCmd(Integer.parseInt(parsingParam[0] + parsingParam[1]), parsingParam[2]);
                                r7 = r7;
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 10:
                                str2 = TAG;
                                if (isValidEM()) {
                                    String readDrkUID = readDrkUID(1);
                                    if (readDrkUID != null) {
                                        str3 = str3 + readDrkUID;
                                        r7 = r7;
                                    } else {
                                        str3 = str3 + SecureKeyConst.AT_RESPONSE_FAILED;
                                        r7 = r7;
                                    }
                                    r2 = new StringBuilder();
                                    Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                    break;
                                }
                                break;
                            case 12:
                                str2 = TAG;
                                if (isValidEM()) {
                                    if (isSupportedDrkV2()) {
                                        str3 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                        r7 = r7;
                                    } else {
                                        str3 = str3 + SecureKeyConst.AT_RESPONSE_FAILED;
                                        r7 = r7;
                                    }
                                    r2 = new StringBuilder();
                                    Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                    break;
                                }
                                break;
                            case 13:
                                str2 = TAG;
                                byte[] readKeyInfo = readKeyInfo(5);
                                if (readKeyInfo != null) {
                                    str3 = str3 + new String(readKeyInfo);
                                    r7 = r7;
                                } else {
                                    str3 = str3 + SecureKeyConst.AT_RESPONSE_FAILED;
                                    r7 = r7;
                                }
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 20:
                                str2 = TAG;
                                String substring = parsingParam[2].substring(0, 2);
                                if (!substring.equals(SecureKeyConst.AT_CMD_DRK_V1_WRITING_END)) {
                                    int parseInt = Integer.parseInt(substring);
                                    if (appendKeyBlob(parseInt, parsingParam[2].substring(parsingParam[2].indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                        StringBuilder append = new StringBuilder().append(str3);
                                        str3 = append.append(SecureKeyConst.AT_RESPONSE_OK).toString();
                                        r7 = append;
                                    } else {
                                        StringBuilder append2 = new StringBuilder().append(str3).append("NG_FAIL(DATA MISSED) SN-").append(parseInt).append(" TB-");
                                        int keyBlobIndex = getKeyBlobIndex() + 1;
                                        str3 = append2.append(keyBlobIndex).toString();
                                        r7 = keyBlobIndex;
                                    }
                                } else if (isValidEM()) {
                                    StringBuilder sb = null;
                                    int installDeviceUnboundKey = installDeviceUnboundKey(0, getTotalKeyBlob());
                                    try {
                                        if (installDeviceUnboundKey == 0) {
                                            StringBuilder append3 = new StringBuilder().append(str3);
                                            str3 = append3.append(SecureKeyConst.AT_RESPONSE_OK).toString();
                                            sb = append3;
                                        } else {
                                            str3 = str3 + "NG_FAIL " + installDeviceUnboundKey;
                                        }
                                        initTlvKeyBlob();
                                        r7 = sb;
                                    } catch (Exception e3) {
                                        e = e3;
                                        e.printStackTrace();
                                        return str3 + "NG_FAIL(EXCEPTION_OCCURS) " + e.getMessage();
                                    }
                                }
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 22:
                                str2 = TAG;
                                if (isValidEM()) {
                                    if (parsingParam[2].length() != 17) {
                                        str3 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                        r7 = r7;
                                    } else {
                                        String substring2 = parsingParam[2].substring(0, 14);
                                        String str4 = parsingParam[2];
                                        byte[] generateCertificateSigningRequest = generateCertificateSigningRequest(2, substring2, str4.substring(14, 17));
                                        if (generateCertificateSigningRequest != null) {
                                            str3 = str3 + new String(generateCertificateSigningRequest);
                                            r7 = str4;
                                        } else {
                                            str3 = str3 + SecureKeyConst.AT_RESPONSE_FAILED;
                                            r7 = str4;
                                        }
                                    }
                                    r2 = new StringBuilder();
                                    Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                    break;
                                }
                                break;
                            case 23:
                                str2 = TAG;
                                String substring3 = parsingParam[2].substring(2, 5);
                                if (parsingParam[2].substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                    int keyBlobIndex2 = substring3.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(substring3);
                                    if (appendKeyBlob(keyBlobIndex2, parsingParam[2].substring(parsingParam[2].indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                        if (!substring3.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                            i = 0;
                                        } else if (isValidEM()) {
                                            int installDeviceBoundCertificate = installDeviceBoundCertificate(2, getTotalKeyBlob());
                                            initTlvKeyBlob();
                                            i = installDeviceBoundCertificate;
                                            if (installDeviceBoundCertificate != 0) {
                                                str3 = str3 + "NG_FAIL " + installDeviceBoundCertificate;
                                                r7 = installDeviceBoundCertificate;
                                            }
                                        }
                                        str3 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                        r7 = i;
                                    } else {
                                        StringBuilder append4 = new StringBuilder().append(str3).append("NG_FAIL(DATA MISSED) SN-").append(keyBlobIndex2).append(" TB-");
                                        int keyBlobIndex3 = getKeyBlobIndex() + 1;
                                        str3 = append4.append(keyBlobIndex3).toString();
                                        r7 = keyBlobIndex3;
                                    }
                                } else {
                                    str3 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                    r7 = r7;
                                }
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 24:
                                str2 = TAG;
                                if (parsingParam[2].length() != 17) {
                                    str3 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                    r7 = r7;
                                } else {
                                    r7 = 17;
                                    r7 = 17;
                                    byte[] generateCertificateSigningRequest2 = generateCertificateSigningRequest(3, parsingParam[2].substring(0, 14), parsingParam[2].substring(14, 17));
                                    str3 = generateCertificateSigningRequest2 != null ? str3 + new String(generateCertificateSigningRequest2) : str3 + SecureKeyConst.AT_RESPONSE_FAILED;
                                }
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 25:
                                str2 = TAG;
                                String substring4 = parsingParam[2].substring(2, 5);
                                if (parsingParam[2].substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                    int keyBlobIndex4 = substring4.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(substring4);
                                    if (appendKeyBlob(keyBlobIndex4, parsingParam[2].substring(parsingParam[2].indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                        if (substring4.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                            int installDeviceBoundCertificate2 = installDeviceBoundCertificate(3, getTotalKeyBlob());
                                            initTlvKeyBlob();
                                            i2 = installDeviceBoundCertificate2;
                                            if (installDeviceBoundCertificate2 != 0) {
                                                str3 = str3 + "NG_FAIL " + installDeviceBoundCertificate2;
                                                r7 = installDeviceBoundCertificate2;
                                            }
                                        } else {
                                            i2 = 0;
                                        }
                                        str3 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                        r7 = i2;
                                    } else {
                                        StringBuilder append5 = new StringBuilder().append(str3).append("NG_FAIL(DATA MISSED) SN-").append(keyBlobIndex4).append(" TB-");
                                        int keyBlobIndex5 = getKeyBlobIndex() + 1;
                                        str3 = append5.append(keyBlobIndex5).toString();
                                        r7 = keyBlobIndex5;
                                    }
                                } else {
                                    str3 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                    r7 = r7;
                                }
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                            case 26:
                            case 27:
                                String str5 = parsingParam[2];
                                str2 = TAG;
                                String substring5 = str5.substring(2, 5);
                                if (parsingParam[2].substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                    int keyBlobIndex6 = substring5.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(substring5);
                                    if (appendKeyBlob(keyBlobIndex6, parsingParam[2].substring(parsingParam[2].indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                        if (substring5.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                            int installDeviceUnboundKey2 = Integer.parseInt(new StringBuilder().append(parsingParam[0]).append(parsingParam[1]).toString()) == 26 ? installDeviceUnboundKey(1, getTotalKeyBlob()) : installDeviceUnboundKey(4, getTotalKeyBlob());
                                            initTlvKeyBlob();
                                            i3 = installDeviceUnboundKey2;
                                            if (installDeviceUnboundKey2 != 0) {
                                                str3 = str3 + "NG_FAIL " + installDeviceUnboundKey2;
                                                r7 = installDeviceUnboundKey2;
                                            }
                                        } else {
                                            i3 = 0;
                                        }
                                        str3 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                        r7 = i3;
                                    } else {
                                        StringBuilder append6 = new StringBuilder().append(str3).append("NG_FAIL(DATA MISSED) SN-").append(keyBlobIndex6).append(" TB-");
                                        int keyBlobIndex7 = getKeyBlobIndex() + 1;
                                        str3 = append6.append(keyBlobIndex7).toString();
                                        r7 = keyBlobIndex7;
                                    }
                                } else {
                                    str3 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                    r7 = r7;
                                }
                                r2 = new StringBuilder();
                                Log.m98i(str2, r2.append("ProcessCmd [").append(str).append("] end").toString());
                                break;
                        }
                    } catch (Exception e4) {
                        e = e4;
                    }
                } catch (Exception e5) {
                    e = e5;
                }
            } catch (Exception e6) {
                e = e6;
            }
        } catch (Exception e7) {
            e = e7;
        }
        return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
    }

    public String generateCertWithTlv(boolean tlv) {
        byte[] tlvTestExponent = {2, 2, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -17};
        byte[] tlvTestKeyUsage = {3, 2, 2, -4};
        byte[] tlvTestSubjectAlterName = {SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -122, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 84, 104, 105, 115, 32, 105, 115, 32, 115, 117, 98, 106, 101, 99, 116, 32, SprAttributeBase.TYPE_ANIMATOR_SET, 108, 116, 101, 114, 110, SprAttributeBase.TYPE_ANIMATOR_SET, 116, 105, 118, 101, 32, 110, SprAttributeBase.TYPE_ANIMATOR_SET, 109, 101, 32, 102, 105, 101, 108, 100, 32, 116, 101, 115, 116, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 95};
        byte[] tlbTestHashAlgo = {6, 9, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, -122, 72, -122, -9, 13, 1, 1, 5};
        if (tlv) {
            Tlv tlv2 = new Tlv();
            this.mTlv = tlv2;
            tlv2.setTlv(1, tlvTestExponent);
            this.mTlv.setTlv(5, tlvTestKeyUsage);
            this.mTlv.setTlv(29, tlvTestSubjectAlterName);
            this.mTlv.setTlv(3, tlbTestHashAlgo);
        } else {
            this.mTlv = null;
        }
        byte[] serviceCert = this.mDeviceRootKeyServiceManager.doSelfTestProvService(1, this.mTlv);
        if (serviceCert != null) {
            return SecureKeyConst.AT_RESPONSE_OK;
        }
        return SecureKeyConst.AT_RESPONSE_FAILED;
    }

    public String processTestCmd(int cmd, String subData) {
        String result;
        if (!"eng".equals(Build.TYPE)) {
            return SecureKeyConst.AT_RESPONSE_UNIMPLEMENTED;
        }
        DeviceRootKeyServiceManager deviceRootKeyServiceManager = this.mDeviceRootKeyServiceManager;
        if (deviceRootKeyServiceManager == null) {
            return SecureKeyConst.AT_RESPONSE_INSTANCE_ERROR;
        }
        try {
            switch (cmd) {
                case 90:
                    if (!deviceRootKeyServiceManager.isAliveDeviceRootKeyService()) {
                        result = SecureKeyConst.AT_RESPONSE_CONN_FAILED;
                        break;
                    } else {
                        result = SecureKeyConst.AT_RESPONSE_OK;
                        break;
                    }
                case 91:
                    if (isSupportedDrkV2()) {
                        result = SecureKeyConst.AT_RESPONSE_OK;
                        break;
                    } else {
                        result = SecureKeyConst.AT_RESPONSE_FAILED;
                        break;
                    }
                case 92:
                    if (!deviceRootKeyServiceManager.isExistDeviceRootKey(1)) {
                        result = SecureKeyConst.AT_RESPONSE_FAILED;
                        break;
                    } else {
                        result = SecureKeyConst.AT_RESPONSE_OK;
                        break;
                    }
                case 93:
                    String tmpStrResult = deviceRootKeyServiceManager.getDeviceRootKeyUID(1);
                    if (tmpStrResult == null) {
                        result = SecureKeyConst.AT_RESPONSE_FAILED;
                        break;
                    } else {
                        result = SecureKeyConst.AT_RESPONSE_OK;
                        break;
                    }
                case 94:
                    byte[] tmpResult = deviceRootKeyServiceManager.getDeviceRootKeyCertificate(1);
                    if (tmpResult == null) {
                        result = SecureKeyConst.AT_RESPONSE_FAILED;
                        break;
                    } else {
                        result = SecureKeyConst.AT_RESPONSE_OK;
                        break;
                    }
                case 95:
                    result = generateCertWithTlv(false);
                    break;
                case 96:
                    result = generateCertWithTlv(true);
                    break;
                case 97:
                    DeviceRootKeyServiceManager.DeviceInfo dInfo = deviceRootKeyServiceManager.getDeviceInfo(14);
                    if (dInfo == null) {
                        result = SecureKeyConst.AT_RESPONSE_FAILED;
                        break;
                    } else {
                        result = SecureKeyConst.AT_RESPONSE_OK;
                        break;
                    }
                default:
                    return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            String result2 = "NG_FAIL(EXCEPTION_OCCURS) " + e.getMessage();
            return result2;
        }
    }

    protected String[] parsingParam(String cmd) {
        try {
            String params = cmd.substring(0, cmd.length());
            String[] result = params.split(",");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected boolean appendKeyBlob(int sequenceNo, String keyBlob) {
        if (sequenceNo != 1) {
            if (sequenceNo == this.mTlvKeyBlobCounter + 1) {
                this.mTlvKeyBlobCounter = sequenceNo;
                this.mTlvKeyBlob += keyBlob;
            } else {
                initTlvKeyBlob();
                return false;
            }
        } else {
            this.mTlvKeyBlobCounter = sequenceNo;
            this.mTlvKeyBlob = keyBlob;
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

    protected int checkKeyValidity(int keyType) {
        int ret;
        int ret2 = 0;
        "factory".equals(SystemProperties.get("ro.factory.factory_binary"));
        boolean isSupportIDAttestation = false;
        boolean isSystemFirstApiLevelMoreThanT = Integer.parseInt(SystemProperties.get("ro.product.first_api_level")) >= 33;
        boolean isVendorFirstApiLevelMoreThanT = Integer.parseInt(SystemProperties.get("ro.vendor.build.version.sdk")) >= 33;
        boolean isExceptionHandlingGrfSModules = SystemProperties.get("ro.build.flavor", "").contains("a14m") || SystemProperties.get("ro.build.flavor", "").contains("a14xm") || SystemProperties.get("ro.build.flavor", "").contains("a24") || SystemProperties.get("ro.build.flavor", "").contains("a34x");
        if (isSystemFirstApiLevelMoreThanT && (isVendorFirstApiLevelMoreThanT || isExceptionHandlingGrfSModules)) {
            isSupportIDAttestation = true;
        }
        if (1 != 0) {
            if (isSupportIDAttestation && (ret = installDeviceID(keyType)) != 0) {
                Log.m96e(TAG, "installDeviceID failed");
                return ret;
            }
            ret2 = validateDeviceKey(keyType);
            if (ret2 != 0) {
                Log.m96e(TAG, "validateDeviceKey failed");
                return ret2;
            }
        }
        if ((keyType == 1 || (keyType == 4 && 1 != 0)) && (ret2 = validateDeviceKeyFromKeystore(keyType, isSupportIDAttestation)) != 0) {
            Log.m96e(TAG, "validateDeviceKeyFromKeystore failed");
            return ret2;
        }
        return ret2;
    }

    protected int installDeviceID(int keyType) {
        TelephonyManager telephonyService = (TelephonyManager) this.mContext.getSystemService("phone");
        String brand = isPropertyEmptyOrUnknown(Build.BRAND_FOR_ATTESTATION) ? Build.BRAND : Build.BRAND_FOR_ATTESTATION;
        String device = isPropertyEmptyOrUnknown(Build.DEVICE_FOR_ATTESTATION) ? Build.DEVICE : Build.DEVICE_FOR_ATTESTATION;
        String produt = isPropertyEmptyOrUnknown(Build.PRODUCT_FOR_ATTESTATION) ? Build.PRODUCT : Build.PRODUCT_FOR_ATTESTATION;
        String manufacturer = isPropertyEmptyOrUnknown(Build.MANUFACTURER_FOR_ATTESTATION) ? Build.MANUFACTURER : Build.MANUFACTURER_FOR_ATTESTATION;
        String model = isPropertyEmptyOrUnknown(Build.MODEL_FOR_ATTESTATION) ? Build.MODEL : Build.MODEL_FOR_ATTESTATION;
        String serial = Build.getSerial();
        String imei1 = telephonyService.getImei(0);
        String imei2 = telephonyService.getImei(1);
        String meid = telephonyService.getMeid(0);
        return installDeviceID(keyType, brand, device, produt, serial, imei1, imei2, meid, manufacturer, model);
    }

    protected boolean isEqualsRootPubKey(Certificate rootCert, int keyType) throws CertificateException {
        X509Certificate X509RootCert = (X509Certificate) rootCert;
        PublicKey pubkey = X509RootCert.getPublicKey();
        byte[] pubkeyEncoded = pubkey.getEncoded();
        if (keyType == 1 || keyType == 4) {
            if (Arrays.equals(pubkeyEncoded, SecureKeyConst.GoogleRootPubKey) || Arrays.equals(pubkeyEncoded, SecureKeyConst.GoogleDevRootPubKey)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00be A[Catch: IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException -> 0x0258, TryCatch #2 {IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException -> 0x0258, blocks: (B:92:0x00aa, B:19:0x00be, B:21:0x00c4, B:22:0x00cc, B:23:0x00d3, B:25:0x00d8, B:27:0x00e9, B:30:0x0104, B:31:0x0108), top: B:91:0x00aa }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00d8 A[Catch: IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException -> 0x0258, LOOP:0: B:23:0x00d3->B:25:0x00d8, LOOP_END, TryCatch #2 {IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException -> 0x0258, blocks: (B:92:0x00aa, B:19:0x00be, B:21:0x00c4, B:22:0x00cc, B:23:0x00d3, B:25:0x00d8, B:27:0x00e9, B:30:0x0104, B:31:0x0108), top: B:91:0x00aa }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00e9 A[EDGE_INSN: B:26:0x00e9->B:27:0x00e9 BREAK  A[LOOP:0: B:23:0x00d3->B:25:0x00d8], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00a1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected int validateDeviceKeyFromKeystore(int keyType, boolean isSupportIDAttestation) {
        Exception e;
        List<Integer> list;
        int[] idTypes;
        int i;
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder("gak_ec_alias", 12);
            builder.setKeySize(256);
            try {
                builder.setDigests("SHA-256", KeyProperties.DIGEST_NONE);
                builder.setAttestationChallenge("gak_ec_challenge".getBytes(Charset.forName("UTF-8")));
                if (isSupportIDAttestation) {
                    TelephonyManager telephonyService = (TelephonyManager) this.mContext.getSystemService("phone");
                    List<Integer> list2 = new ArrayList<>();
                    String serial = Build.getSerial();
                    try {
                        String imei = telephonyService.getImei(0);
                        String meid = telephonyService.getMeid(0);
                        if (serial != null) {
                            try {
                                if (serial.length() != 0) {
                                    list = list2;
                                    try {
                                        list.add(1);
                                        if (imei != null) {
                                            try {
                                                if (imei.length() != 0) {
                                                    try {
                                                        list.add(2);
                                                        if (meid != null && meid.length() != 0) {
                                                            list.add(3);
                                                        }
                                                        idTypes = new int[list.size()];
                                                        i = 0;
                                                        while (true) {
                                                            String meid2 = meid;
                                                            if (i >= idTypes.length) {
                                                                break;
                                                            }
                                                            idTypes[i] = list.get(i).intValue();
                                                            i++;
                                                            meid = meid2;
                                                        }
                                                        builder.setDevicePropertiesAttestationIncluded(true);
                                                        builder.setAttestationIds(idTypes);
                                                    } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e2) {
                                                        e = e2;
                                                        e.printStackTrace();
                                                        return SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
                                                    }
                                                }
                                            } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e3) {
                                                e = e3;
                                                e.printStackTrace();
                                                return SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
                                            }
                                        }
                                        if (meid != null) {
                                            list.add(3);
                                        }
                                        idTypes = new int[list.size()];
                                        i = 0;
                                        while (true) {
                                            String meid22 = meid;
                                            if (i >= idTypes.length) {
                                            }
                                            idTypes[i] = list.get(i).intValue();
                                            i++;
                                            meid = meid22;
                                        }
                                        builder.setDevicePropertiesAttestationIncluded(true);
                                        builder.setAttestationIds(idTypes);
                                    } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e4) {
                                        e = e4;
                                        e.printStackTrace();
                                        return SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
                                    }
                                }
                            } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e5) {
                                e = e5;
                            }
                        }
                        list = list2;
                        if (imei != null) {
                        }
                        if (meid != null) {
                        }
                        idTypes = new int[list.size()];
                        i = 0;
                        while (true) {
                            String meid222 = meid;
                            if (i >= idTypes.length) {
                            }
                            idTypes[i] = list.get(i).intValue();
                            i++;
                            meid = meid222;
                        }
                        builder.setDevicePropertiesAttestationIncluded(true);
                        builder.setAttestationIds(idTypes);
                    } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e6) {
                        e = e6;
                    }
                }
                if (keyType == 4) {
                    builder.setIsStrongBoxBacked(true);
                }
                AlgorithmParameterSpec spec = builder.build();
                KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, AndroidKeyStoreSpi.NAME);
                kpGenerator.initialize(spec);
                kpGenerator.generateKeyPair();
                Certificate[] gakEcCertChain = keyStore.getCertificateChain("gak_ec_alias");
                boolean ret = verifyCertChains(gakEcCertChain);
                if (!ret) {
                    return SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
                }
                try {
                    X509Certificate appEcCert = (X509Certificate) gakEcCertChain[0];
                    X509Certificate gakEcCert = (X509Certificate) gakEcCertChain[1];
                    X509Certificate appEcCert2 = appEcCert;
                    appEcCert2.verify(gakEcCert.getPublicKey());
                    if (!isEqualsRootPubKey(gakEcCertChain[gakEcCertChain.length - 1], keyType)) {
                        return SecureKeyConst.ERR_KEYMASTER_GAK_ROOT_FAIL;
                    }
                    KeyGenParameterSpec.Builder builder2 = new KeyGenParameterSpec.Builder("gak_rsa_alias", 12);
                    builder2.setKeySize(2048);
                    builder2.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PSS, KeyProperties.SIGNATURE_PADDING_RSA_PKCS1);
                    builder2.setDigests("SHA-256", "SHA-1");
                    builder2.setAttestationChallenge("gak_rsa_challenge".getBytes(Charset.forName("UTF-8")));
                    if (isSupportIDAttestation) {
                        TelephonyManager telephonyService2 = (TelephonyManager) this.mContext.getSystemService("phone");
                        List<Integer> list3 = new ArrayList<>();
                        String serial2 = Build.getSerial();
                        String imei2 = telephonyService2.getImei(0);
                        String meid3 = telephonyService2.getMeid(0);
                        if (serial2 != null && serial2.length() != 0) {
                            list3.add(1);
                        }
                        if (imei2 != null && imei2.length() != 0) {
                            list3.add(2);
                        }
                        if (meid3 != null && meid3.length() != 0) {
                            list3.add(3);
                        }
                        int[] idTypes2 = new int[list3.size()];
                        int i2 = 0;
                        while (true) {
                            X509Certificate appEcCert3 = appEcCert2;
                            if (i2 >= idTypes2.length) {
                                break;
                            }
                            idTypes2[i2] = list3.get(i2).intValue();
                            i2++;
                            appEcCert2 = appEcCert3;
                        }
                        builder2.setDevicePropertiesAttestationIncluded(true);
                        builder2.setAttestationIds(idTypes2);
                    }
                    if (keyType == 4) {
                        builder2.setIsStrongBoxBacked(true);
                    }
                    AlgorithmParameterSpec spec2 = builder2.build();
                    KeyPairGenerator kpGenerator2 = KeyPairGenerator.getInstance("RSA", AndroidKeyStoreSpi.NAME);
                    kpGenerator2.initialize(spec2);
                    kpGenerator2.generateKeyPair();
                    Certificate[] gakRsaCertChain = keyStore.getCertificateChain("gak_rsa_alias");
                    boolean ret2 = verifyCertChains(gakRsaCertChain);
                    if (!ret2) {
                        return SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
                    }
                    try {
                        X509Certificate appRsaCert = (X509Certificate) gakRsaCertChain[0];
                        X509Certificate gakRsaCert = (X509Certificate) gakRsaCertChain[1];
                        appRsaCert.verify(gakRsaCert.getPublicKey());
                        if (isEqualsRootPubKey(gakRsaCertChain[gakRsaCertChain.length - 1], keyType)) {
                            return 0;
                        }
                        return SecureKeyConst.ERR_KEYMASTER_GAK_ROOT_FAIL;
                    } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e7) {
                        e = e7;
                        e.printStackTrace();
                        return SecureKeyConst.ERR_KEYMASTER_VERIFICATION_FAIL;
                    }
                } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e8) {
                    e = e8;
                }
            } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e9) {
                e = e9;
            }
        } catch (IOException | IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | ProviderException | SignatureException | CertPathValidatorException | CertificateException e10) {
            e = e10;
        }
    }

    protected boolean verifyCertChains(Certificate[] certs) throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException, CertPathValidatorException, InvalidAlgorithmParameterException {
        List<X509Certificate> x509Certs = new ArrayList<>();
        int len = certs.length;
        for (int i = 1; i < len; i++) {
            X509Certificate x509Cert = (X509Certificate) certs[i];
            x509Certs.add(x509Cert);
        }
        return verifyCertChains(x509Certs);
    }

    protected boolean verifyCertChains(List<X509Certificate> certs) throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException, CertPathValidatorException, InvalidAlgorithmParameterException {
        int len = certs.size();
        if (len != 0) {
            X509Certificate rootCert = certs.get(len - 1);
            X509Certificate finalCert = certs.get(0);
            CollectionCertStoreParameters ccsp = new CollectionCertStoreParameters(certs);
            CertStore store = CertStore.getInstance("Collection", ccsp);
            Calendar validDate = Calendar.getInstance();
            validDate.setTime(finalCert.getNotBefore());
            validDate.add(5, 2);
            List<X509Certificate> certchain = new ArrayList<>();
            for (int i = 0; i < len - 1; i++) {
                certchain.add(certs.get(i));
            }
            CertPath cp = CertificateFactory.getInstance("X.509").generateCertPath(certchain);
            Set<TrustAnchor> trust = new HashSet<>();
            trust.add(new TrustAnchor(rootCert, null));
            CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
            PKIXParameters param = new PKIXParameters(trust);
            param.addCertStore(store);
            param.setDate(validDate.getTime());
            param.setRevocationEnabled(false);
            PKIXCertPathValidatorResult result = (PKIXCertPathValidatorResult) cpv.validate(cp, param);
            PublicKey subjectPublicKey = result.getPublicKey();
            if (!subjectPublicKey.equals(finalCert.getPublicKey())) {
                Log.m96e(TAG, "wrong public key returned");
                return false;
            }
            return true;
        }
        Log.m96e(TAG, "certification chain size is invalid");
        return false;
    }

    protected boolean isValidEM() {
        if (!SecureKeyConst.isJDM) {
            Log.m98i(TAG, "It is not a JDM project");
            return true;
        }
        if (SecureKeyConst.isDevDevice.equals("0x1")) {
            Log.m98i(TAG, "It is not A User Product Device");
            return true;
        }
        EngineeringModeManager EMMgr = new EngineeringModeManager(this.mContext.getApplicationContext());
        if (!EMMgr.isConnected()) {
            Log.m96e(TAG, "Failed to connect to em service");
            return false;
        }
        if (EMMgr.getStatus(28) == 1) {
            Log.m98i(TAG, "EM Status : Permitted");
            return true;
        }
        Log.m96e(TAG, "EM Status : Not Permitted");
        return false;
    }

    private boolean isPropertyEmptyOrUnknown(String property) {
        return TextUtils.isEmpty(property) || property.equals("unknown");
    }
}
