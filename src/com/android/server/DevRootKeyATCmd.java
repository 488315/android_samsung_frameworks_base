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
    @Override // com.android.server.IWorkOnAt
    public String processCmd(String str) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6 = TAG;
        String str7 = new String();
        String[] parsingParam = parsingParam(str);
        if (parsingParam == null || parsingParam.length != 3) {
            return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
        }
        try {
            Log.i(TAG, "ProcessCmd [" + str + "] start");
            String str8 = parsingParam[0] + ",";
            if ((Integer.parseInt(parsingParam[0]) == 0 || Integer.parseInt(parsingParam[0]) == 1) && !parsingParam[2].equals("0")) {
                return str8 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
            }
            int parseInt = Integer.parseInt(parsingParam[0] + parsingParam[1]);
            if (parseInt != 0) {
                if (parseInt != 1) {
                    if (parseInt != 2) {
                        if (parseInt == 3) {
                            int validateDeviceKey = validateDeviceKey(3);
                            if (validateDeviceKey == 0) {
                                str2 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                            } else {
                                str2 = str8 + "NG_FAIL " + validateDeviceKey;
                            }
                        } else if (parseInt == 4) {
                            int checkKeyValidity = checkKeyValidity(1);
                            if (checkKeyValidity == 0) {
                                str2 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                            } else {
                                str2 = str8 + "NG_FAIL " + checkKeyValidity;
                            }
                        } else {
                            if (parseInt != 5) {
                                switch (parseInt) {
                                    case 10:
                                        if (isValidEM()) {
                                            String readDrkUID = readDrkUID(1);
                                            if (readDrkUID == null) {
                                                str2 = str8 + SecureKeyConst.AT_RESPONSE_FAILED;
                                                break;
                                            } else {
                                                str2 = str8 + readDrkUID;
                                                break;
                                            }
                                        }
                                        break;
                                    case 11:
                                        break;
                                    case 12:
                                        if (isValidEM()) {
                                            if (!isSupportedDrkV2()) {
                                                str2 = str8 + SecureKeyConst.AT_RESPONSE_FAILED;
                                                break;
                                            } else {
                                                str2 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                                                break;
                                            }
                                        }
                                        break;
                                    case 13:
                                        str2 = str8 + SecureKeyConst.AT_RESPONSE_UNIMPLEMENTED;
                                        break;
                                    default:
                                        switch (parseInt) {
                                            case 20:
                                                if (!SecureKeyConst.checkblockDrkWriting()) {
                                                    str6 = TAG;
                                                    String substring = parsingParam[2].substring(0, 2);
                                                    if (!substring.equals(SecureKeyConst.AT_CMD_DRK_V1_WRITING_END)) {
                                                        int parseInt2 = Integer.parseInt(substring);
                                                        String str9 = parsingParam[2];
                                                        if (!appendKeyBlob(parseInt2, str9.substring(str9.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                                            str2 = str8 + "NG_FAIL(DATA MISSED) SN-" + parseInt2 + " TB-" + (getKeyBlobIndex() + 1);
                                                            break;
                                                        } else {
                                                            str2 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                                                            break;
                                                        }
                                                    } else if (isValidEM()) {
                                                        int installDeviceUnboundKey = installDeviceUnboundKey(0, getTotalKeyBlob());
                                                        if (installDeviceUnboundKey == 0) {
                                                            str3 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                                                        } else {
                                                            str3 = str8 + "NG_FAIL " + installDeviceUnboundKey;
                                                        }
                                                        initTlvKeyBlob();
                                                        Log.i(str6, "ProcessCmd [" + str + "] end");
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
                                                str4 = TAG;
                                                if (isValidEM()) {
                                                    if (parsingParam[2].length() != 17) {
                                                        str5 = str8 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                                    } else {
                                                        byte[] generateCertificateSigningRequest = generateCertificateSigningRequest(2, parsingParam[2].substring(0, 14), parsingParam[2].substring(14, 17));
                                                        if (generateCertificateSigningRequest != null) {
                                                            str5 = str8 + new String(generateCertificateSigningRequest);
                                                        } else {
                                                            str5 = str8 + SecureKeyConst.AT_RESPONSE_FAILED;
                                                        }
                                                    }
                                                    str3 = str5;
                                                    str6 = str4;
                                                    Log.i(str6, "ProcessCmd [" + str + "] end");
                                                    break;
                                                }
                                                break;
                                            case 23:
                                                if (!SecureKeyConst.checkblockDrkWriting()) {
                                                    String substring2 = parsingParam[2].substring(2, 5);
                                                    String str10 = parsingParam[2];
                                                    str4 = TAG;
                                                    if (str10.substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                                        int keyBlobIndex = substring2.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(substring2);
                                                        String str11 = parsingParam[2];
                                                        if (appendKeyBlob(keyBlobIndex, str11.substring(str11.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                                            if (substring2.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                                                if (isValidEM()) {
                                                                    int installDeviceBoundCertificate = installDeviceBoundCertificate(2, getTotalKeyBlob());
                                                                    initTlvKeyBlob();
                                                                    if (installDeviceBoundCertificate != 0) {
                                                                        str5 = str8 + "NG_FAIL " + installDeviceBoundCertificate;
                                                                    }
                                                                }
                                                            }
                                                            str5 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                                                        } else {
                                                            str5 = str8 + "NG_FAIL(DATA MISSED) SN-" + keyBlobIndex + " TB-" + (getKeyBlobIndex() + 1);
                                                        }
                                                    } else {
                                                        str5 = str8 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                                    }
                                                    str3 = str5;
                                                    str6 = str4;
                                                    Log.i(str6, "ProcessCmd [" + str + "] end");
                                                    break;
                                                } else {
                                                    Log.i(TAG, "ProcessCmd [" + str + "] is deprecated");
                                                    break;
                                                }
                                                break;
                                            case 24:
                                                str4 = TAG;
                                                if (parsingParam[2].length() != 17) {
                                                    str5 = str8 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                                } else {
                                                    byte[] generateCertificateSigningRequest2 = generateCertificateSigningRequest(3, parsingParam[2].substring(0, 14), parsingParam[2].substring(14, 17));
                                                    if (generateCertificateSigningRequest2 != null) {
                                                        str5 = str8 + new String(generateCertificateSigningRequest2);
                                                    } else {
                                                        str5 = str8 + SecureKeyConst.AT_RESPONSE_FAILED;
                                                    }
                                                }
                                                str3 = str5;
                                                str6 = str4;
                                                Log.i(str6, "ProcessCmd [" + str + "] end");
                                                break;
                                            case 25:
                                                str4 = TAG;
                                                String substring3 = parsingParam[2].substring(2, 5);
                                                if (parsingParam[2].substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                                    int keyBlobIndex2 = substring3.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(substring3);
                                                    String str12 = parsingParam[2];
                                                    if (appendKeyBlob(keyBlobIndex2, str12.substring(str12.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                                        if (substring3.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                                            int installDeviceBoundCertificate2 = installDeviceBoundCertificate(3, getTotalKeyBlob());
                                                            initTlvKeyBlob();
                                                            if (installDeviceBoundCertificate2 != 0) {
                                                                str5 = str8 + "NG_FAIL " + installDeviceBoundCertificate2;
                                                            } else {
                                                                sendSakUidMsgAppletBindingIntent();
                                                            }
                                                        }
                                                        str5 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                                                    } else {
                                                        str5 = str8 + "NG_FAIL(DATA MISSED) SN-" + keyBlobIndex2 + " TB-" + (getKeyBlobIndex() + 1);
                                                    }
                                                } else {
                                                    str5 = str8 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                                }
                                                str3 = str5;
                                                str6 = str4;
                                                Log.i(str6, "ProcessCmd [" + str + "] end");
                                                break;
                                            case 26:
                                            case 27:
                                                String substring4 = parsingParam[2].substring(2, 5);
                                                String str13 = parsingParam[2];
                                                str4 = TAG;
                                                if (str13.substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                                    int keyBlobIndex3 = substring4.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(substring4);
                                                    String str14 = parsingParam[2];
                                                    if (appendKeyBlob(keyBlobIndex3, str14.substring(str14.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                                        if (substring4.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                                            StringBuilder sb = new StringBuilder();
                                                            sb.append(parsingParam[0]);
                                                            sb.append(parsingParam[1]);
                                                            int installDeviceUnboundKey2 = Integer.parseInt(sb.toString()) == 26 ? installDeviceUnboundKey(1, getTotalKeyBlob()) : installDeviceUnboundKey(4, getTotalKeyBlob());
                                                            initTlvKeyBlob();
                                                            if (installDeviceUnboundKey2 != 0) {
                                                                str5 = str8 + "NG_FAIL " + installDeviceUnboundKey2;
                                                            }
                                                        }
                                                        str5 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                                                    } else {
                                                        str5 = str8 + "NG_FAIL(DATA MISSED) SN-" + keyBlobIndex3 + " TB-" + (getKeyBlobIndex() + 1);
                                                    }
                                                } else {
                                                    str5 = str8 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                                }
                                                str3 = str5;
                                                str6 = str4;
                                                Log.i(str6, "ProcessCmd [" + str + "] end");
                                                break;
                                            default:
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append(str8);
                                                sb2.append(processTestCmd(Integer.parseInt(parsingParam[0] + parsingParam[1]), parsingParam[2]));
                                                str2 = sb2.toString();
                                                break;
                                        }
                                }
                                return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                            }
                            int checkKeyValidity2 = checkKeyValidity(4);
                            if (checkKeyValidity2 == 0) {
                                str2 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                            } else {
                                str2 = str8 + "NG_FAIL " + checkKeyValidity2;
                            }
                        }
                        str3 = str2;
                        Log.i(str6, "ProcessCmd [" + str + "] end");
                        return str3;
                    }
                }
                str2 = str8 + SecureKeyConst.AT_RESPONSE_PMK_OK;
                str3 = str2;
                Log.i(str6, "ProcessCmd [" + str + "] end");
                return str3;
            }
            if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                int isExistDRK = isExistDRK(1);
                if (isExistDRK == 0) {
                    str2 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                } else {
                    str2 = str8 + "NG_FAIL " + isExistDRK;
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
                    str2 = str8 + SecureKeyConst.AT_RESPONSE_OK;
                } else {
                    str2 = str8 + SecureKeyConst.AT_RESPONSE_FAILED;
                }
            }
            str3 = str2;
            Log.i(str6, "ProcessCmd [" + str + "] end");
            return str3;
        } catch (Exception e) {
            e.printStackTrace();
            return str7 + "NG_FAIL(EXCEPTION_OCCURS) " + e.getMessage();
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

    protected int checkKeyValidity(int i) {
        int i2 = 0;
        boolean z = (Integer.parseInt(SystemProperties.get("ro.product.first_api_level")) >= 33) && ((Integer.parseInt(SystemProperties.get("ro.vendor.build.version.sdk")) >= 33) || (SystemProperties.get("ro.build.flavor", "").contains("a14m") || SystemProperties.get("ro.build.flavor", "").contains("a14xm") || SystemProperties.get("ro.build.flavor", "").contains("a24") || SystemProperties.get("ro.build.flavor", "").contains("a34x")));
        if (z) {
            if (i == 1) {
                i2 = this.mDeviceIDProvisionManager.provisionForATCommand(6);
            } else if (i == 4) {
                i2 = this.mDeviceIDProvisionManager.provisionForATCommand(7);
            }
            if (i2 != 0) {
                Log.e(TAG, "installDeviceID failed");
                return i2;
            }
        }
        int validateDeviceKey = validateDeviceKey(i);
        if (validateDeviceKey != 0) {
            Log.e(TAG, "validateDeviceKey failed");
            return validateDeviceKey;
        }
        if (i != 1 && i != 4) {
            return validateDeviceKey;
        }
        int validateDeviceKeyFromKeystore = validateDeviceKeyFromKeystore(i, z);
        if (validateDeviceKeyFromKeystore != 0) {
            Log.e(TAG, "validateDeviceKeyFromKeystore failed");
        }
        return validateDeviceKeyFromKeystore;
    }

    protected int provisionDeviceID(int i) {
        int i2 = 0;
        boolean z = Integer.parseInt(SystemProperties.get("ro.product.first_api_level")) >= 33;
        boolean z2 = Integer.parseInt(SystemProperties.get("ro.vendor.build.version.sdk")) >= 33;
        boolean z3 = SystemProperties.get("ro.build.flavor", "").contains("a14m") || SystemProperties.get("ro.build.flavor", "").contains("a14xm") || SystemProperties.get("ro.build.flavor", "").contains("a24") || SystemProperties.get("ro.build.flavor", "").contains("a34x");
        if (z && (z2 || z3)) {
            if (i == 1) {
                i2 = this.mDeviceIDProvisionManager.provisionForATCommand(6);
            } else if (i == 4) {
                i2 = this.mDeviceIDProvisionManager.provisionForATCommand(7);
            }
            if (i2 != 0) {
                Log.e(TAG, "installDeviceID failed");
            }
        }
        return i2;
    }

    protected boolean isEqualsRootPubKey(Certificate certificate, int i) throws CertificateException {
        byte[] encoded = ((X509Certificate) certificate).getPublicKey().getEncoded();
        if (i == 1 || i == 4) {
            return Arrays.equals(encoded, SecureKeyConst.GoogleRootPubKey) || Arrays.equals(encoded, SecureKeyConst.GoogleDevRootPubKey);
        }
        return false;
    }

    protected int validateDeviceKeyFromKeystore(int i, boolean z) {
        int i2;
        int i3;
        String str;
        int i4;
        int i5;
        String str2;
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
                        str = telephonyManager.getMeid(0);
                    } catch (UnsupportedOperationException e) {
                        e.printStackTrace();
                        str = null;
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
                    if (str != null && str.length() != 0) {
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
            KeyGenParameterSpec build = builder.build();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, AndroidKeyStoreSpi.NAME);
            keyPairGenerator.initialize(build);
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
                    str2 = telephonyManager2.getMeid(i7);
                } catch (UnsupportedOperationException e3) {
                    e3.printStackTrace();
                    str2 = null;
                }
                if (serial2 != null && serial2.length() != 0) {
                    arrayList2.add(1);
                }
                if (imei2 != null && imei2.length() != 0) {
                    arrayList2.add(Integer.valueOf(i4));
                }
                if (str2 != null && str2.length() != 0) {
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
            KeyGenParameterSpec build2 = builder2.build();
            KeyPairGenerator keyPairGenerator2 = KeyPairGenerator.getInstance("RSA", AndroidKeyStoreSpi.NAME);
            keyPairGenerator2.initialize(build2);
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

    protected boolean verifyCertChains(Certificate[] certificateArr) throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException, CertPathValidatorException, InvalidAlgorithmParameterException {
        ArrayList arrayList = new ArrayList();
        int length = certificateArr.length;
        for (int i = 1; i < length; i++) {
            arrayList.add((X509Certificate) certificateArr[i]);
        }
        return verifyCertChains(arrayList);
    }

    protected boolean verifyCertChains(List<X509Certificate> list) throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException, CertPathValidatorException, InvalidAlgorithmParameterException {
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
        CertPath generateCertPath = CertificateFactory.getInstance("X.509").generateCertPath(arrayList);
        HashSet hashSet = new HashSet();
        hashSet.add(new TrustAnchor(x509Certificate, null));
        CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
        PKIXParameters pKIXParameters = new PKIXParameters(hashSet);
        pKIXParameters.addCertStore(certStore);
        pKIXParameters.setDate(calendar.getTime());
        pKIXParameters.setRevocationEnabled(false);
        if (((PKIXCertPathValidatorResult) certPathValidator.validate(generateCertPath, pKIXParameters)).getPublicKey().equals(x509Certificate2.getPublicKey())) {
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
