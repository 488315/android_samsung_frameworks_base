package com.android.server;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Binder;
import android.os.Process;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.internal.midi.MidiConstants;

import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import com.samsung.android.service.SemService.ISemService;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes5.dex */
public final class SemService extends ISemService.Stub {
    public static final int ERROR = -1;
    public static final int ERROR_ALREADY_OPENED = -11;
    public static final int ERROR_CLASS_NOT_FOUND = -2;
    public static final int ERROR_EXCEPTION = -90;
    public static final int ERROR_NOT_OPENED = -12;
    public static final int ERROR_NOT_SUPPORTED = -10;
    public static final int ERROR_NO_PERMISSION = -91;
    public static final int ERROR_NO_PERMISSION_SIZE = 0;
    public static final int ERROR_NO_SERVICE = -92;
    private static final int ERROR_SPI_ALREADY_OPENED = -200;
    public static final int ERROR_UNSAT_LINK = -3;
    private static final int MAX_GET_ESEA_DATA = 1024;
    private static final int MAX_RETRY_SPI_CHECK = 15;
    private static final long MAX_TIMEOUT_IN_SECOND = 30;
    public static final int NO_ERROR = 0;
    public static final int NO_ERROR_SPI = 0;
    public static final int SSD_NOT_EXIST_APPLET_EXIST = 5;
    public static final int SSD_NOT_EXIST_APPLET_NOT_EXIST = 4;
    public static final int SSD_NOT_SELECTABLE_APPLET_EXIST = 2;
    public static final int SSD_NOT_SELECTABLE_APPLET_NOT_EXIST = 3;
    public static final int SSD_SELECTABLE_APPLET_EXIST = 0;
    public static final int SSD_SELECTABLE_APPLET_NOT_EXIST = 1;
    private static final String TAG = "SEC_ESE_Service";
    private Timer SPITimeout;
    private ConnectivityManager connectivityManager;
    private Context mContext;
    private SemServiceAccessControl mSemServiceAccessControl;
    private StringBuffer secureBuffer;
    private boolean supportReeSpi;
    public static final String ERROR_NO_PERMISSION_STRING = null;
    private static String cosName = "JCOP7.0U";
    private static String chipVendor = "NXP";
    private static final Object mLock = new Object();
    private byte[] bytePublicKeyDataSKMS = new byte[300];
    private byte[] bytePublicKeyDataSecurity = new byte[300];
    private int bytePublicKeySecurityLen = 0;
    private int bytePublicKeySKMSLen = 0;
    private boolean supportEsek = true;
    private String skuChipName = "";
    private String spiOpenPackageName = null;
    private boolean mIsOpened = false;
    ConnectivityManager.NetworkCallback CMCallback = null;
    private byte[] bodyData = null;

    private native byte[] getDPDLog();

    public native int checkSeStatus(byte[] bArr, byte[] bArr2);

    public native int closeDriverSpi();

    public native int closeSpi(int i);

    public native int coldReset();

    public native int continueattestation(String str, int i, byte[] bArr);

    public native int deactivateCards(int i, byte[][] bArr, int[] iArr, int i2);

    public native int deactivateCardsAID(
            int i,
            int i2,
            byte[][] bArr,
            int[] iArr,
            int i3,
            byte[][] bArr2,
            int[] iArr2,
            int i4,
            byte[][] bArr3,
            int[] iArr3,
            int i5);

    public native int eSEAidFactoryReset(byte[] bArr, int i);

    public native int eSEFactoryReset();

    public native int eSEFullFactoryReset();

    public native int eSELowFactoryReset();

    public native int esekCertificateCheck();

    public native int getAtr();

    public native int getCPLC14mode(byte[] bArr);

    public native int getESEA(byte[] bArr);

    public native int getHQMMemory(byte[] bArr);

    public native int getpkSKMS(byte[] bArr);

    public native int getpkSecurity(byte[] bArr);

    public native int grdmCheckRestrictedMode(byte[] bArr);

    public native int grdmCheckStatusInfo();

    public native int grdmGetAttesCert(int i, byte[] bArr);

    public native int grdmGetSession();

    public native int grdmReleaseSession();

    public native int grdmRequestKey(int i, byte[] bArr);

    public native String[] handleCCM(byte[] bArr, int i);

    public native String[] handleCCMCB(byte[] bArr, int i, byte[] bArr2, int i2);

    public native int handleCCMScp11c(byte[] bArr, int i);

    public native int jniICD();

    public native int jniIsLccmSwp();

    public native int openDriverSpi();

    public native int openSpi(int i);

    public native void printEnhancedDump();

    public native int scp11CertificateCheck();

    public native void semFactory();

    public native int sendData(byte[] bArr, int i, byte[] bArr2, int i2);

    public native int startRequestCredentials(byte[] bArr, byte[] bArr2, String str, byte[] bArr3);

    public native int startRequestCredentialsList(
            byte[] bArr, byte[] bArr2, String str, byte[] bArr3, byte[] bArr4, byte[] bArr5);

    public native int startattestation(byte[] bArr, int i, byte[] bArr2, int i2);

    public native void stopRequestCredentials();

    static {
        System.loadLibrary("sec_sem");
    }

    public SemService(Context context) {
        this.secureBuffer = null;
        this.supportReeSpi = true;
        this.connectivityManager = null;
        Log.i(TAG, "Start SemService");
        this.mSemServiceAccessControl = new SemServiceAccessControl(context);
        this.mSemServiceAccessControl.setAllowedPackages();
        this.mContext = context;
        if (isGRDMSupported()) {
            this.mSemServiceAccessControl.setGrdmAllowedPackages();
        }
        readSkuProperty();
        if (chipVendor.contains("SKU")) {
            if (TextUtils.isEmpty(this.skuChipName)) {
                if (this.supportReeSpi) {
                    this.supportReeSpi = false;
                }
            } else {
                setCosNameProperty();
            }
        } else {
            setCosNameProperty();
        }
        this.secureBuffer = new StringBuffer();
        this.connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
    }

    private void readSkuProperty() {
        try {
            this.skuChipName = SystemProperties.get("ro.boot.hardware.sku");
        } catch (Exception e) {
            Log.e(TAG, "failed to get sysProp: ro.boot.hardware.sku");
        }
    }

    private boolean isShutdownRequested() {
        try {
            String sysShutDownRequested = SystemProperties.get("sys.shutdown.requested");
            if (!TextUtils.isEmpty(sysShutDownRequested)) {
                Log.w(TAG, "Not supported to get ESEA during shutdown process");
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "failed to get prop: sys.shutdown.requested");
            return false;
        }
    }

    private void setCosNameProperty() {
        StringBuilder propCosName = new StringBuilder();
        if (chipVendor.equals("MULTI")) {
            if (this.skuChipName.equals("s3fwrn5")) {
                propCosName.append("UT5.1");
                propCosName.append("_01000012");
            } else if (this.skuChipName.equals("sn110t")) {
                propCosName.append("JCOP5.3T");
                propCosName.append("_00353145");
            } else if (this.skuChipName.equals("sn220t")) {
                propCosName.append("JCOP6.2T");
                propCosName.append("_00354A4A");
            } else {
                Log.e(TAG, "Not supported skuChipName, " + this.skuChipName);
                return;
            }
        } else {
            propCosName.append(cosName);
            if (cosName.equals("JCOP5.1F")) {
                propCosName.append("_00354C52");
            } else if (cosName.equals("JCOP6.2F")) {
                propCosName.append("_0035544B");
            } else if (cosName.equals("JCOP6.2P")) {
                propCosName.append("_00505644");
                propCosName.append("_0051414C");
            } else if (cosName.equals("JCOP7.0P")) {
                propCosName.append("_004D4838");
            } else if (cosName.equals("UT8.2P")) {
                propCosName.append("_0B010001");
            } else {
                return;
            }
        }
        try {
            SystemProperties.set("ro.security.ese.cosname", propCosName.toString());
        } catch (Exception e) {
            Log.e(TAG, "failed to set sysProp: cosname");
        }
    }

    private boolean isValidPackageForSpi() {
        if (this.spiOpenPackageName == null) {
            Log.e(TAG, "SPI is currently not in use");
            return false;
        }
        String packageName = this.mSemServiceAccessControl.getPackageName();
        if (this.spiOpenPackageName.equals(packageName)) {
            return true;
        }
        Log.e(
                TAG,
                "The package name currently using SPI does not match, opened : "
                        + this.spiOpenPackageName
                        + ", requested : "
                        + packageName);
        return false;
    }

    private boolean requestSpiUsage() {
        for (int i = 1; i <= 15; i++) {
            synchronized (mLock) {
                if (this.spiOpenPackageName == null) {
                    this.spiOpenPackageName = this.mSemServiceAccessControl.getPackageName();
                    Log.w(TAG, "SPI is opened by " + this.spiOpenPackageName);
                    return true;
                }
            }
            Log.d(
                    TAG,
                    "SPI is currently in use by "
                            + this.spiOpenPackageName
                            + ", wait for 0.5 sec. Retry count : "
                            + i);
            try {
                Thread.sleep(500L);
            } catch (Exception e) {
                Log.e(TAG, "Exception in sleep " + e);
            }
        }
        Log.e(TAG, "SPI is currently in use by " + this.spiOpenPackageName);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseSpiUsage() {
        if (this.spiOpenPackageName == null) {
            Log.w(TAG, "SPI is currently not in use");
        } else {
            Log.w(TAG, "SPI is released by " + this.spiOpenPackageName);
            this.spiOpenPackageName = null;
        }
    }

    class SPITimeoutTask extends TimerTask {
        SPITimeoutTask() {}

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Log.e(SemService.TAG, "Close SPI if theree's no APDU communication in 30 seconds");
            int ret = SemService.this.synchronizedCloseSpi(0);
            if (ret == 0) {
                SemService.this.releaseSpiUsage();
            }
        }
    }

    private void startSPITimer() {
        try {
            Log.d(TAG, "startSPITimer");
            if (this.SPITimeout != null) {
                Log.d(TAG, "Timer's already been started");
            } else {
                this.SPITimeout = new Timer();
                this.SPITimeout.schedule(new SPITimeoutTask(), 30000L);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in startSPITimer " + e);
            this.SPITimeout = null;
        }
    }

    private void stopSPITimer() {
        try {
            Log.d(TAG, "stopSPITimer");
            if (this.SPITimeout == null) {
                Log.d(TAG, "Timer's already been stopped");
            } else {
                this.SPITimeout.cancel();
                this.SPITimeout = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in stopSPITimer " + e);
            this.SPITimeout = null;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public String getCPLC14mode() {
        Log.i(TAG, "Start GetCPLC14mode");
        byte[] get_cplc_data = new byte[100];
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "getCPLC14mode Permission Error");
            return ERROR_NO_PERMISSION_STRING;
        }
        if (!requestSpiUsage()) {
            return null;
        }
        try {
            int dataLen = getCPLC14mode(get_cplc_data);
            Log.i(TAG, "GetCPLC14mode Len " + dataLen);
            releaseSpiUsage();
            if (dataLen <= 0) {
                Log.e(TAG, "no data to be returned");
                return null;
            }
            if (dataLen < 1000) {
                return SemServiceTools.bytesToHex(Arrays.copyOf(get_cplc_data, dataLen));
            }
            Log.e(TAG, "data overflow");
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Failed to getCPLC14mode, " + e.toString());
            releaseSpiUsage();
            return null;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public String get_ESEA() {
        Log.i(TAG, "Start get_ESEA");
        byte[] getESEAData = new byte[1024];
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "get_ESEA Permission Error");
            return ERROR_NO_PERMISSION_STRING;
        }
        if (!requestSpiUsage() || isShutdownRequested()) {
            return null;
        }
        try {
            int dataLen = getESEA(getESEAData);
            Log.d(TAG, "getESEA Len " + dataLen);
            releaseSpiUsage();
            if (dataLen <= 0) {
                Log.e(TAG, "no data to be returned");
                return null;
            }
            if (dataLen < 1024) {
                byte[] getESEAData2 = Arrays.copyOf(getESEAData, dataLen);
                Log.d(TAG, "getESEA Return0 : " + new String(getESEAData2, StandardCharsets.UTF_8));
                return new String(getESEAData2, StandardCharsets.UTF_8);
            }
            Log.e(TAG, "data overflow");
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Failed to getESEA, " + e.toString());
            releaseSpiUsage();
            return null;
        }
    }

    public String get_DPDLog() {
        Log.i(TAG, "Start get_DPDLog");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "get_DPDLog Permission Error");
            return ERROR_NO_PERMISSION_STRING;
        }
        if (!requestSpiUsage() || isShutdownRequested()) {
            return null;
        }
        try {
            byte[] data = getDPDLog();
            Log.d(TAG, "getDPDLog Len " + data.length);
            releaseSpiUsage();
            return SemServiceTools.getHexString(data);
        } catch (Exception e) {
            Log.e(TAG, "Failed to getESEA, " + e.toString());
            releaseSpiUsage();
            return null;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void sem_factory() {
        Log.i(TAG, "sem_factory");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                        SemServiceAccessControl.PackageList.MFactoryPkgList)
                || !requestSpiUsage()) {
            return;
        }
        try {
            semFactory();
        } catch (Exception e) {
            Log.e(TAG, "Failed to sem_factory, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef sem_factory, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield sem_factory, " + e3.toString());
        }
        releaseSpiUsage();
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int esek_certificate_check() {
        int ret;
        Log.i(TAG, "Start esek_certificate_check");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "esek_certificate_check Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        if (isShutdownRequested()) {
            return -10;
        }
        try {
            ret = esekCertificateCheck();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            ret = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            ret = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            ret = -3;
        }
        releaseSpiUsage();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int scp11_certificate_check() {
        int ret;
        Log.i(TAG, "Start scp11_certificate_check");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "scp11_certificate_check Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        if (isShutdownRequested()) {
            return -10;
        }
        try {
            ret = scp11CertificateCheck();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            ret = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            ret = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            ret = -3;
        }
        releaseSpiUsage();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int handle_CCMScp11c(byte[] ccmData, int ccmDataLen) {
        Log.i(TAG, "Start handle_CCM11c");
        int ret = -1;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "handle_CCMScp11c Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        try {
        } catch (Exception e) {
            Log.e(TAG, "Failed to handle_CCM, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef handle_CCM, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield handle_CCM, " + e3.toString());
        }
        if (!parseScript(ccmData, ccmDataLen)) {
            Log.e(TAG, "CCM script error");
            return -9;
        }
        if (this.bodyData == null) {
            Log.e(TAG, "CCM data error");
            return -10;
        }
        Log.d(TAG, "BD Len " + this.bodyData.length);
        ret = handleCCMScp11c(this.bodyData, this.bodyData.length);
        releaseSpiUsage();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public String[] handle_CCM(byte[] ccmData, int ccmDataLen) {
        Log.i(TAG, "Start handle_CCM");
        String[] ret = null;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MLccmPkgList)) {
            Log.e(TAG, "handle_CCM Permission Error");
            return null;
        }
        if (!requestSpiUsage()) {
            return null;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return null;
        }
        try {
            ret = handleCCM(ccmData, ccmDataLen);
        } catch (Exception e) {
            Log.e(TAG, "Failed to handle_CCM, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef handle_CCM, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield handle_CCM, " + e3.toString());
        }
        releaseSpiUsage();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public String[] handle_CCMCB(byte[] ccmData, int ccmDataLen, byte[] respData, int respLen) {
        Log.i(TAG, "Start handle_CCM");
        String[] ret = null;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MLccmPkgList)) {
            Log.e(TAG, "handle_CCM Permission Error");
            return null;
        }
        if (!requestSpiUsage()) {
            return null;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return null;
        }
        try {
            ret = handleCCMCB(ccmData, ccmDataLen, respData, respLen);
        } catch (Exception e) {
            Log.e(TAG, "Failed to handle_CCM, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef handle_CCM, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield handle_CCM, " + e3.toString());
        }
        releaseSpiUsage();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int isLccmSwp() {
        int ret;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MLccmPkgList)) {
            Log.e(TAG, "isLccmSwp Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        try {
            ret = jniIsLccmSwp();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            ret = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            ret = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            ret = -3;
        }
        releaseSpiUsage();
        return ret;
    }

    private boolean parseScript(byte[] ccmData, int ccmDataLen) {
        int offset = 0;
        if (ccmData == null) {
            try {
                Log.e(TAG, "data is null");
                return false;
            } catch (Exception e) {
                e = e;
            }
        } else {
            try {
                if (ccmDataLen <= 2097152 && ccmDataLen >= 0) {
                    try {
                        if (ccmData.length < ccmDataLen) {
                            try {
                                Log.e(TAG, "data overflow");
                                return false;
                            } catch (Exception e2) {
                                e = e2;
                            }
                        } else {
                            if (ccmData[0] != -32) {
                                Log.e(TAG, "unknown tag");
                                return false;
                            }
                            try {
                                int tagEZLength = SemServiceTools.checkLength(ccmData, 0 + 1);
                                if (tagEZLength <= 0) {
                                    Log.e(TAG, "not supported tag e0");
                                    return false;
                                }
                                if ((ccmData[0 + 1] & 255) < 128) {
                                    offset = 0 + 2;
                                } else if (ccmData[0 + 1] == -127) {
                                    offset = 0 + 3;
                                } else if (ccmData[0 + 1] == -126) {
                                    offset = 0 + 4;
                                } else if (ccmData[0 + 1] == -125) {
                                    offset = 0 + 5;
                                }
                                int msgLength = tagEZLength + offset;
                                Log.d(TAG, msgLength + "," + tagEZLength + "," + offset);
                                if (msgLength + 2 + 64 != ccmDataLen) {
                                    Log.e(TAG, "data is inconsistency");
                                    return false;
                                }
                                if (ccmData[offset] != 63 || ccmData[offset + 1] != 49) {
                                    Log.d(TAG, "Tag '3F31' read error");
                                    return false;
                                }
                                int offset2 = offset + 2;
                                int offset3 = offset2 + 1;
                                try {
                                    int dateLength = SemServiceTools.checkLength(ccmData, offset2);
                                    byte[] scriptDate =
                                            Arrays.copyOfRange(
                                                    ccmData, offset3, offset3 + dateLength);
                                    int offset4 = offset3 + dateLength;
                                    Log.d(TAG, "Date : " + SemServiceTools.bytesToHex(scriptDate));
                                    if (ccmData[offset4] != -31) {
                                        Log.e(TAG, "Tag 'E1' read error");
                                        return false;
                                    }
                                    int offset5 = offset4 + 1;
                                    offset3 = offset5 + 1;
                                    int tagEOLength = SemServiceTools.checkLength(ccmData, offset5);
                                    int tagEOCnt = ccmData[offset3];
                                    Log.i(TAG, "TC : " + tagEOCnt);
                                    int offset6 = offset3 + tagEOLength;
                                    if (ccmData[offset6] != -30) {
                                        Log.e(TAG, "Tag 'E2' read error");
                                        return false;
                                    }
                                    int tagETLen =
                                            SemServiceTools.checkLength(ccmData, offset6 + 1);
                                    if (tagETLen <= 0) {
                                        Log.e(TAG, "not supported tag e2");
                                        return false;
                                    }
                                    if ((ccmData[offset6 + 1] & 255) < 128) {
                                        offset6 += 2;
                                    } else if (ccmData[offset6 + 1] == -127) {
                                        offset6 += 3;
                                    } else if (ccmData[offset6 + 1] == -126) {
                                        offset6 += 4;
                                    } else if (ccmData[offset6 + 1] == -125) {
                                        offset6 += 5;
                                    }
                                    this.bodyData =
                                            Arrays.copyOfRange(
                                                    ccmData, offset6, offset6 + tagETLen);
                                    int offset7 = offset6 + tagETLen;
                                    if (ccmData[offset7] != -22) {
                                        Log.e(TAG, "Invalid script: No signature");
                                        return false;
                                    }
                                    int offset8 = offset7 + 1;
                                    offset3 = offset8 + 1;
                                    try {
                                        int tagEALen =
                                                SemServiceTools.checkLength(ccmData, offset8);
                                        if (tagEALen <= 0) {
                                            Log.e(TAG, "not supported tag ea");
                                            return false;
                                        }
                                        if (ccmDataLen != offset3 + tagEALen) {
                                            Log.e(TAG, "wrong length!");
                                            return false;
                                        }
                                        byte[] signature =
                                                Arrays.copyOfRange(
                                                        ccmData, offset3, offset3 + tagEALen);
                                        byte[] message = Arrays.copyOfRange(ccmData, 0, msgLength);
                                        if (SemServiceTools.ccmVerify(message, signature)) {
                                            return true;
                                        }
                                        Log.e(TAG, "Invalid signature!");
                                        return false;
                                    } catch (Exception e3) {
                                        e = e3;
                                        Log.e(TAG, "LCCM script Exception : " + e.getMessage());
                                        return false;
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                }
                            } catch (Exception e5) {
                                e = e5;
                            }
                        }
                    } catch (Exception e6) {
                        e = e6;
                    }
                }
                Log.e(TAG, "invalid data lenth");
                return false;
            } catch (Exception e7) {
                e = e7;
            }
        }
        Log.e(TAG, "LCCM script Exception : " + e.getMessage());
        return false;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int get_HQMMemory(byte[] hw_memory_data) {
        Log.i(TAG, "Start get_HQMMemory");
        int dataLen = 0;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MHWParamPkgList)) {
            Log.e(TAG, "get_HQMMemory Permission Error");
            return 0;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        if (isShutdownRequested()) {
            return -10;
        }
        try {
            dataLen = getHQMMemory(hw_memory_data);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get_AttackCountCheck, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef get_AttackCountCheck, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield get_AttackCountCheck, " + e3.toString());
        }
        releaseSpiUsage();
        return dataLen;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int deactivate_Cards(
            int RequestType, String[] package_name_list, int[] package_len, int arrayLen) {
        Log.i(TAG, "Start deactivate_Cards");
        int ret = 0;
        byte[][] package_name = new byte[arrayLen][];
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MSKMSCardPkgList)) {
            Log.e(TAG, "deactivate_Cards Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        if (isShutdownRequested()) {
            return -10;
        }
        for (int i = 0; i < arrayLen; i++) {
            try {
                ByteArrayOutputStream OutputStream = new ByteArrayOutputStream();
                DataOutputStream DataOut = new DataOutputStream(OutputStream);
                DataOut.writeUTF(package_name_list[i]);
                package_name[i] = OutputStream.toByteArray();
                package_name[i] = Arrays.copyOfRange(package_name[i], 2, package_name[i].length);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            Log.d(TAG, "Package Name : " + package_name[i]);
        }
        try {
            ret = deactivateCards(RequestType, package_name, package_len, arrayLen);
        } catch (Exception e2) {
            Log.e(TAG, "Failed to deactivate_Cards, " + e2.toString());
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NoClassDef deactivate_Cards, " + e3.toString());
        } catch (UnsatisfiedLinkError e4) {
            Log.e(TAG, "Unsatisfield deactivate_Cards, " + e4.toString());
        }
        releaseSpiUsage();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int deactivate_CardsAID(
            int RequestType, int bean, String[] package_aid, int[] package_len, int arrayLen) {
        Log.i(TAG, "Start deactivate_Cards");
        int ret = 0;
        byte[][] package_name = new byte[arrayLen][];
        byte[][] package_name_Star = new byte[arrayLen][];
        byte[][] package_name_Sharp = new byte[arrayLen][];
        int[] package_len_Star = new int[arrayLen];
        int[] package_len_Sharp = new int[arrayLen];
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MSKMSCardPkgList)) {
            Log.e(TAG, "deactivate_CardsAID Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        if (isShutdownRequested()) {
            return -10;
        }
        Log.d(TAG, "A Size : " + arrayLen);
        int name_cnt = 0;
        int star_cnt = 0;
        int sharp_cnt = 0;
        for (int i = 0; i < arrayLen; i++) {
            String element = null;
            try {
                element = package_aid[i];
                if (element == null) {
                    Log.e(TAG, "element is null");
                } else if (element.contains("*")) {
                    element = element.replaceAll("[*]", "");
                    if (element != null) {
                        package_name_Star[star_cnt] = SemServiceTools.hexToBytes(element);
                        if (package_name_Star[star_cnt] != null) {
                            package_len_Star[star_cnt] = package_name_Star[star_cnt].length;
                            star_cnt++;
                        }
                    }
                } else if (element.contains("#")) {
                    element = element.replaceAll("#", "");
                    if (element != null) {
                        package_name_Sharp[sharp_cnt] = SemServiceTools.hexToBytes(element);
                        if (package_name_Sharp[sharp_cnt] != null) {
                            package_len_Sharp[sharp_cnt] = package_name_Sharp[sharp_cnt].length;
                            sharp_cnt++;
                        }
                    }
                } else if (element != null) {
                    package_name[name_cnt] = SemServiceTools.hexToBytes(element);
                    if (package_name[name_cnt] != null) {
                        package_len[name_cnt] = package_name[name_cnt].length;
                        name_cnt++;
                    }
                }
            } catch (NullPointerException e) {
                Log.e(TAG, "DDA Null Point Exception " + e);
            } catch (Exception e2) {
                Log.e(TAG, "DDA Exception " + e2);
            }
        }
        try {
            Log.i(TAG, "DDA Start ");
        } catch (Exception e3) {
            e = e3;
        } catch (NoClassDefFoundError e4) {
            e = e4;
        } catch (UnsatisfiedLinkError e5) {
            e = e5;
        }
        try {
            ret =
                    deactivateCardsAID(
                            RequestType,
                            bean,
                            package_name,
                            package_len,
                            name_cnt,
                            package_name_Star,
                            package_len_Star,
                            star_cnt,
                            package_name_Sharp,
                            package_len_Sharp,
                            sharp_cnt);
        } catch (Exception e6) {
            e = e6;
            Log.e(TAG, "Failed to deactivate_Cards, " + e.toString());
            releaseSpiUsage();
            return ret;
        } catch (NoClassDefFoundError e7) {
            e = e7;
            Log.e(TAG, "NoClassDef deactivate_Cards, " + e.toString());
            releaseSpiUsage();
            return ret;
        } catch (UnsatisfiedLinkError e8) {
            e = e8;
            Log.e(TAG, "Unsatisfield deactivate_Cards, " + e.toString());
            releaseSpiUsage();
            return ret;
        }
        releaseSpiUsage();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int eSE_FactoryReset() {
        int factoryResetResult;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryResetList)) {
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        if (isShutdownRequested()) {
            return -10;
        }
        try {
            factoryResetResult = eSEFactoryReset();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "USLE Exception : " + e);
            factoryResetResult = -3;
        } catch (Error e2) {
            Log.e(TAG, "Error : " + e2);
            factoryResetResult = -90;
        } catch (Exception e3) {
            Log.e(TAG, "Exception : " + e3);
            factoryResetResult = -90;
        } catch (NoClassDefFoundError e4) {
            Log.e(TAG, "NCDF Exception : " + e4);
            factoryResetResult = -2;
        }
        releaseSpiUsage();
        return factoryResetResult;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int eSE_LowFactoryReset() {
        int factoryResetResult;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryResetList)) {
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        if (isShutdownRequested()) {
            return -10;
        }
        try {
            factoryResetResult = eSELowFactoryReset();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "USLE Exception : " + e);
            factoryResetResult = -3;
        } catch (Error e2) {
            Log.e(TAG, "Error : " + e2);
            factoryResetResult = -90;
        } catch (Exception e3) {
            Log.e(TAG, "Exception : " + e3);
            factoryResetResult = -90;
        } catch (NoClassDefFoundError e4) {
            Log.e(TAG, "NCDF Exception : " + e4);
            factoryResetResult = -2;
        }
        releaseSpiUsage();
        return factoryResetResult;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int eSE_FullFactoryReset() {
        int factoryResetResult;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryResetList)) {
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        if (isShutdownRequested()) {
            return -10;
        }
        try {
            factoryResetResult = eSEFullFactoryReset();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "USLE Exception : " + e);
            factoryResetResult = -3;
        } catch (Error e2) {
            Log.e(TAG, "Error : " + e2);
            factoryResetResult = -90;
        } catch (Exception e3) {
            Log.e(TAG, "Exception : " + e3);
            factoryResetResult = -90;
        } catch (NoClassDefFoundError e4) {
            Log.e(TAG, "NCDF Exception : " + e4);
            factoryResetResult = -2;
        }
        releaseSpiUsage();
        return factoryResetResult;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int eSE_AidFactoryReset(byte[] aid, int aidLen) {
        int factoryResetResult;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryResetList)) {
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        if (isShutdownRequested()) {
            return -10;
        }
        try {
            factoryResetResult = eSEAidFactoryReset(aid, aidLen);
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "USLE Exception : " + e);
            factoryResetResult = -3;
        } catch (Error e2) {
            Log.e(TAG, "Error : " + e2);
            factoryResetResult = -90;
        } catch (Exception e3) {
            Log.e(TAG, "Exception : " + e3);
            factoryResetResult = -90;
        } catch (NoClassDefFoundError e4) {
            Log.e(TAG, "NCDF Exception : " + e4);
            factoryResetResult = -2;
        }
        releaseSpiUsage();
        return factoryResetResult;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void check_Network(int type) {
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MFactoryResetList)) {
            return;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            return;
        }
        try {
            if (type == 0) {
                Log.i(TAG, "F-R-NC");
                this.CMCallback =
                        new ConnectivityManager
                                .NetworkCallback() { // from class: com.android.server.SemService.1
                            @Override // android.net.ConnectivityManager.NetworkCallback
                            public void onAvailable(Network network) {
                                Log.e(SemService.TAG, "F-NC : " + network);
                                Intent BRIntent = new Intent("com.sec.action.SKMS_NETWORK");
                                BRIntent.putExtra("com.sec.action.SKMS_NETWORK_VALUE", 1);
                                BRIntent.setPackage("com.skms.android.agent");
                                SemService.this.mContext.sendBroadcastAsUser(
                                        BRIntent,
                                        Process.myUserHandle(),
                                        "com.samsung.permission.ESE_SYSTEM_PROTECTION");
                            }
                        };
                this.connectivityManager.registerDefaultNetworkCallback(this.CMCallback);
            } else if (type == 1) {
                Log.i(TAG, "F-UR-NC");
                this.connectivityManager.unregisterNetworkCallback(this.CMCallback);
                this.CMCallback = null;
            }
        } catch (Error e) {
            Log.e(TAG, "Error : " + e);
        } catch (Exception e2) {
            Log.e(TAG, "Exception : " + e2);
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int ICD() {
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "ICD Permission Error");
            return -91;
        }
        try {
            int icdResult = jniICD();
            return icdResult;
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            return -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            return -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            return -3;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int start_attestation(byte[] drRsp, int drLen, byte[] svRsp, int svLen) {
        int result;
        Log.i(TAG, "start_attestation");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MJavaPkgList)) {
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        try {
            result = startattestation(drRsp, drLen, svRsp, svLen);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            result = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            result = -92;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            result = -92;
        }
        releaseSpiUsage();
        return result;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int continue_attestation(String data, int dataLen, byte[] rspData) {
        int result;
        Log.i(TAG, "continue_atteestation");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MJavaPkgList)) {
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        try {
            result = continueattestation(data, dataLen, rspData);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            result = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            result = -92;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            result = -92;
        }
        releaseSpiUsage();
        Log.i(TAG, "result : " + result);
        return result;
    }

    private String encData(String msg) {
        SecretKeySpec encryptKey;
        byte[] encKeyDataSKMS;
        byte[] encKeyDataSecurity;
        Log.i(TAG, "S-ED");
        byte[] getPKDataSecurity = new byte[300];
        byte[] getPKDataSKMS = new byte[300];
        try {
            try {
                if (this.bytePublicKeySecurityLen >= 1) {
                    try {
                        if (this.bytePublicKeySKMSLen >= 1) {
                            byte[] bIv = new byte[16];
                            byte[] bEnc = new byte[32];
                            SecureRandom random = SecureRandom.getInstanceStrong();
                            try {
                                random.setSeed(random.generateSeed(16));
                                random.nextBytes(bIv);
                                random.setSeed(random.generateSeed(32));
                                random.nextBytes(bEnc);
                                SecretKeySpec encryptKey2 = new SecretKeySpec(bEnc, "AES");
                                try {
                                    IvParameterSpec ivAES = new IvParameterSpec(bIv);
                                    try {
                                        Cipher cipherAES =
                                                Cipher.getInstance("AES/CBC/PKCS7Padding");
                                        try {
                                            cipherAES.init(1, encryptKey2, ivAES);
                                            KeyFactory fac = KeyFactory.getInstance("RSA");
                                            try {
                                                byte[] encKeyDataSKMS2 =
                                                        this.bytePublicKeyDataSecurity;
                                                X509EncodedKeySpec publicKeySpecSecurity =
                                                        new X509EncodedKeySpec(encKeyDataSKMS2);
                                                PublicKey publicKeySecurity =
                                                        fac.generatePublic(publicKeySpecSecurity);
                                                try {
                                                    byte[] encKeyDataSecurity2 =
                                                            this.bytePublicKeyDataSKMS;
                                                    X509EncodedKeySpec publicKeySpecSKMS =
                                                            new X509EncodedKeySpec(
                                                                    encKeyDataSecurity2);
                                                    PublicKey publicKeySKMS =
                                                            fac.generatePublic(publicKeySpecSKMS);
                                                    byte[] AESIVKeyValue = new byte[48];
                                                    try {
                                                        System.arraycopy(
                                                                ivAES.getIV(),
                                                                0,
                                                                AESIVKeyValue,
                                                                0,
                                                                16);
                                                        try {
                                                            System.arraycopy(
                                                                    encryptKey2.getEncoded(),
                                                                    0,
                                                                    AESIVKeyValue,
                                                                    16,
                                                                    32);
                                                            Cipher cipherRSA =
                                                                    Cipher.getInstance(
                                                                            "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
                                                            cipherRSA.init(1, publicKeySecurity);
                                                            byte[] encKeyDataSecurity3 =
                                                                    cipherRSA.doFinal(
                                                                            AESIVKeyValue);
                                                            try {
                                                                Cipher cipherRSA2 =
                                                                        Cipher.getInstance(
                                                                                "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
                                                                cipherRSA2.init(1, publicKeySKMS);
                                                                byte[] encKeyDataSKMS3 =
                                                                        cipherRSA2.doFinal(
                                                                                AESIVKeyValue);
                                                            } catch (Error e) {
                                                                e = e;
                                                            } catch (NullPointerException e2) {
                                                                e = e2;
                                                            } catch (Exception e3) {
                                                                e = e3;
                                                            }
                                                        } catch (Error e4) {
                                                            e = e4;
                                                        } catch (NullPointerException e5) {
                                                            e = e5;
                                                        } catch (Exception e6) {
                                                            e = e6;
                                                        }
                                                    } catch (Error e7) {
                                                        e = e7;
                                                    } catch (NullPointerException e8) {
                                                        e = e8;
                                                    } catch (Exception e9) {
                                                        e = e9;
                                                    }
                                                } catch (Error e10) {
                                                    e = e10;
                                                } catch (NullPointerException e11) {
                                                    e = e11;
                                                } catch (Exception e12) {
                                                    e = e12;
                                                }
                                                try {
                                                    byte[] byteEncBuffer =
                                                            cipherAES.doFinal(msg.getBytes());
                                                    ByteArrayOutputStream outputStream =
                                                            new ByteArrayOutputStream();
                                                    outputStream.write(encKeyDataSecurity3);
                                                    outputStream.write(encKeyDataSKMS3);
                                                    outputStream.write(byteEncBuffer);
                                                    byte[] byteLogbuffer =
                                                            outputStream.toByteArray();
                                                    outputStream.close();
                                                    return Base64.encodeToString(byteLogbuffer, 2);
                                                } catch (Error e13) {
                                                    e = e13;
                                                    Log.e(TAG, "ENC Data Error " + e);
                                                    return null;
                                                } catch (NullPointerException e14) {
                                                    e = e14;
                                                    Log.e(TAG, "ENC Data NullpointException " + e);
                                                    return null;
                                                } catch (Exception e15) {
                                                    e = e15;
                                                    Log.e(TAG, "ENC Data Exception " + e);
                                                    return null;
                                                }
                                            } catch (Error e16) {
                                                e = e16;
                                            } catch (NullPointerException e17) {
                                                e = e17;
                                            } catch (Exception e18) {
                                                e = e18;
                                            }
                                        } catch (Error e19) {
                                            e = e19;
                                        } catch (NullPointerException e20) {
                                            e = e20;
                                        } catch (Exception e21) {
                                            e = e21;
                                        }
                                    } catch (Error e22) {
                                        e = e22;
                                    } catch (NullPointerException e23) {
                                        e = e23;
                                    } catch (Exception e24) {
                                        e = e24;
                                    }
                                } catch (Error e25) {
                                    e = e25;
                                } catch (NullPointerException e26) {
                                    e = e26;
                                } catch (Exception e27) {
                                    e = e27;
                                }
                            } catch (Error e28) {
                                e = e28;
                            } catch (NullPointerException e29) {
                                e = e29;
                            } catch (Exception e30) {
                                e = e30;
                            }
                        }
                    } catch (Error e31) {
                        e = e31;
                        Log.e(TAG, "ENC Data Error " + e);
                        return null;
                    } catch (NullPointerException e32) {
                        e = e32;
                        Log.e(TAG, "ENC Data NullpointException " + e);
                        return null;
                    } catch (Exception e33) {
                        e = e33;
                        Log.e(TAG, "ENC Data Exception " + e);
                        return null;
                    }
                }
                Log.e(TAG, "PK Error");
                int pkResultSecurity = getpkSecurity(getPKDataSecurity);
                int pkResultSKMS = getpkSKMS(getPKDataSKMS);
                this.bytePublicKeySecurityLen = pkResultSecurity;
                this.bytePublicKeySKMSLen = pkResultSKMS;
                int pkResultSKMS2 = this.bytePublicKeySecurityLen;
                this.bytePublicKeyDataSecurity = Arrays.copyOf(getPKDataSecurity, pkResultSKMS2);
                this.bytePublicKeyDataSKMS =
                        Arrays.copyOf(getPKDataSKMS, this.bytePublicKeySKMSLen);
                if (this.bytePublicKeyDataSKMS == null || this.bytePublicKeyDataSecurity == null) {
                    encryptKey = null;
                    encKeyDataSKMS = null;
                    encKeyDataSecurity = null;
                } else if (this.bytePublicKeySecurityLen >= 1) {
                    try {
                        if (this.bytePublicKeySKMSLen >= 1) {
                            Log.i(TAG, "GET DATA");
                            byte[] bIv2 = new byte[16];
                            byte[] bEnc2 = new byte[32];
                            SecureRandom random2 = SecureRandom.getInstanceStrong();
                            random2.setSeed(random2.generateSeed(16));
                            random2.nextBytes(bIv2);
                            random2.setSeed(random2.generateSeed(32));
                            random2.nextBytes(bEnc2);
                            SecretKeySpec encryptKey22 = new SecretKeySpec(bEnc2, "AES");
                            IvParameterSpec ivAES2 = new IvParameterSpec(bIv2);
                            Cipher cipherAES2 = Cipher.getInstance("AES/CBC/PKCS7Padding");
                            cipherAES2.init(1, encryptKey22, ivAES2);
                            KeyFactory fac2 = KeyFactory.getInstance("RSA");
                            byte[] encKeyDataSKMS22 = this.bytePublicKeyDataSecurity;
                            X509EncodedKeySpec publicKeySpecSecurity2 =
                                    new X509EncodedKeySpec(encKeyDataSKMS22);
                            PublicKey publicKeySecurity2 =
                                    fac2.generatePublic(publicKeySpecSecurity2);
                            byte[] encKeyDataSecurity22 = this.bytePublicKeyDataSKMS;
                            X509EncodedKeySpec publicKeySpecSKMS2 =
                                    new X509EncodedKeySpec(encKeyDataSecurity22);
                            PublicKey publicKeySKMS2 = fac2.generatePublic(publicKeySpecSKMS2);
                            byte[] AESIVKeyValue2 = new byte[48];
                            System.arraycopy(ivAES2.getIV(), 0, AESIVKeyValue2, 0, 16);
                            System.arraycopy(encryptKey22.getEncoded(), 0, AESIVKeyValue2, 16, 32);
                            Cipher cipherRSA3 =
                                    Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
                            cipherRSA3.init(1, publicKeySecurity2);
                            byte[] encKeyDataSecurity32 = cipherRSA3.doFinal(AESIVKeyValue2);
                            Cipher cipherRSA22 =
                                    Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
                            cipherRSA22.init(1, publicKeySKMS2);
                            byte[] encKeyDataSKMS32 = cipherRSA22.doFinal(AESIVKeyValue2);
                            byte[] byteEncBuffer2 = cipherAES2.doFinal(msg.getBytes());
                            ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
                            outputStream2.write(encKeyDataSecurity32);
                            outputStream2.write(encKeyDataSKMS32);
                            outputStream2.write(byteEncBuffer2);
                            byte[] byteLogbuffer2 = outputStream2.toByteArray();
                            outputStream2.close();
                            return Base64.encodeToString(byteLogbuffer2, 2);
                        }
                        encryptKey = null;
                        encKeyDataSKMS = null;
                        encKeyDataSecurity = null;
                    } catch (Error e34) {
                        e = e34;
                        Log.e(TAG, "ENC Data Error " + e);
                        return null;
                    } catch (NullPointerException e35) {
                        e = e35;
                        Log.e(TAG, "ENC Data NullpointException " + e);
                        return null;
                    } catch (Exception e36) {
                        e = e36;
                        Log.e(TAG, "ENC Data Exception " + e);
                        return null;
                    }
                } else {
                    encryptKey = null;
                    encKeyDataSKMS = null;
                    encKeyDataSecurity = null;
                }
                try {
                    Log.e(TAG, "GET DATA FAIL");
                    return null;
                } catch (Error e37) {
                    e = e37;
                    Log.e(TAG, "ENC Data Error " + e);
                    return null;
                } catch (NullPointerException e38) {
                    e = e38;
                    Log.e(TAG, "ENC Data NullpointException " + e);
                    return null;
                } catch (Exception e39) {
                    e = e39;
                    Log.e(TAG, "ENC Data Exception " + e);
                    return null;
                }
            } catch (Error e40) {
                e = e40;
            } catch (NullPointerException e41) {
                e = e41;
            } catch (Exception e42) {
                e = e42;
            }
        } catch (Error e43) {
            e = e43;
        } catch (NullPointerException e44) {
            e = e44;
        } catch (Exception e45) {
            e = e45;
        }
    }

    private String getSCRSActivationList() {
        byte[] APDU_SCRS_SELECT = {
            0, -92, 4, 0, 9, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 1, 81, 67, 82, 83, 0
        };
        byte[] APDU_GETSTATUS_FIRST = {
            Byte.MIN_VALUE, MidiConstants.STATUS_SONG_POSITION, 64, 0, 2, 79, 0, 0
        };
        byte[] APDU_GETSTATUS_REMAINED = {
            Byte.MIN_VALUE, MidiConstants.STATUS_SONG_POSITION, 64, 1, 2, 79, 0, 0
        };
        byte[] ListByteData = new byte[9216];
        int ListByteDataLen = 0;
        try {
            byte[] res = new byte[9216];
            int ret = open_Spi(0);
            if (ret == 0) {
                int resLen = send_Data(APDU_SCRS_SELECT, APDU_SCRS_SELECT.length, res, 0);
                if (res.length < 2) {
                    Log.e(TAG, "Select Error");
                    close_Spi(0);
                    return null;
                }
                byte[] res2 = Arrays.copyOf(res, resLen);
                Log.d(
                        TAG,
                        "Select SW : "
                                + SemServiceTools.byteToHex(res2[resLen - 2])
                                + SemServiceTools.byteToHex(res2[resLen - 1]));
                if (resLen >= 2 && res2[resLen - 2] == -112 && res2[resLen - 1] == 0) {
                    int i = 0;
                    while (true) {
                        if (i >= 10) {
                            break;
                        }
                        byte[] res3 = new byte[9216];
                        int resLen2 =
                                i == 0
                                        ? send_Data(
                                                APDU_GETSTATUS_FIRST,
                                                APDU_GETSTATUS_FIRST.length,
                                                res3,
                                                0)
                                        : send_Data(
                                                APDU_GETSTATUS_REMAINED,
                                                APDU_GETSTATUS_REMAINED.length,
                                                res3,
                                                0);
                        if (res3.length < 2) {
                            Log.e(TAG, "Select Error");
                            close_Spi(0);
                            return null;
                        }
                        byte[] res4 = Arrays.copyOf(res3, resLen2);
                        Log.i(
                                TAG,
                                "SEND SW["
                                        + i
                                        + "] : "
                                        + SemServiceTools.byteToHex(res4[resLen2 - 2])
                                        + SemServiceTools.byteToHex(res4[resLen2 - 1]));
                        if (resLen2 >= 2 && res4[resLen2 - 2] == -112 && res4[resLen2 - 1] == 0) {
                            Log.i(TAG, "GET DATA FINISH");
                            int resLen3 = resLen2 - 2;
                            System.arraycopy(res4, 0, ListByteData, ListByteDataLen, resLen3);
                            ListByteDataLen += resLen3;
                            break;
                        }
                        if (resLen2 < 2 || res4[resLen2 - 2] != 99 || res4[resLen2 - 1] != 16) {
                            break;
                        }
                        Log.i(TAG, "GET DATA MORE");
                        int resLen4 = resLen2 - 2;
                        System.arraycopy(res4, 0, ListByteData, ListByteDataLen, resLen4);
                        ListByteDataLen += resLen4;
                        i++;
                    }
                    Log.e(TAG, "Send Error");
                    close_Spi(0);
                    return null;
                }
                close_Spi(0);
                String resultList =
                        SemServiceTools.bytesToHex(Arrays.copyOf(ListByteData, ListByteDataLen));
                return resultList;
            }
            Log.i(TAG, "S-LOG SCRS Open Fail");
            return null;
        } catch (Exception e) {
            Log.e(TAG, "GET DATA EXCEPTION");
            if (0 != 0) {
                close_Spi(0);
                return null;
            }
            return null;
        }
    }

    private String getAccessRule() {
        byte[] APDU_ARAM_SELECT = {
            0, -92, 4, 0, 9, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 1, 81, 65, 67, 76, 0
        };
        byte[] APDU_GETRULE_FIRST = {Byte.MIN_VALUE, -54, -1, 64, 0};
        byte[] APDU_GETRULE_REMAINED = {Byte.MIN_VALUE, -54, -1, SprAttributeBase.TYPE_DURATION, 0};
        byte[] ListByteData = new byte[9216];
        int ListByteDataLen = 0;
        try {
            byte[] res = new byte[9216];
            int ret = open_Spi(0);
            if (ret == 0) {
                int resLen = send_Data(APDU_ARAM_SELECT, APDU_ARAM_SELECT.length, res, 0);
                if (res.length < 2) {
                    Log.e(TAG, "Select Error");
                    close_Spi(0);
                    return null;
                }
                byte[] res2 = Arrays.copyOf(res, resLen);
                if (resLen >= 2 && res2[resLen - 2] == -112 && res2[resLen - 1] == 0) {
                    int i = 0;
                    while (i < 50) {
                        byte[] res3 = new byte[9216];
                        int resLen2 =
                                i == 0
                                        ? send_Data(
                                                APDU_GETRULE_FIRST,
                                                APDU_GETRULE_FIRST.length,
                                                res3,
                                                0)
                                        : send_Data(
                                                APDU_GETRULE_REMAINED,
                                                APDU_GETRULE_REMAINED.length,
                                                res3,
                                                0);
                        if (res3.length < 2) {
                            Log.e(TAG, "Select Error");
                            close_Spi(0);
                            return null;
                        }
                        byte[] res4 = Arrays.copyOf(res3, resLen2);
                        Log.i(
                                TAG,
                                "SEND SW["
                                        + i
                                        + "] : "
                                        + SemServiceTools.byteToHex(res4[resLen2 - 2])
                                        + SemServiceTools.byteToHex(res4[resLen2 - 1]));
                        if (resLen2 >= 2 && res4[resLen2 - 2] == 105 && res4[resLen2 - 1] == -123) {
                            break;
                        }
                        if (resLen2 >= 2 && res4[resLen2 - 2] == -112 && res4[resLen2 - 1] == 0) {
                            int resLen3 = resLen2 - 2;
                            System.arraycopy(res4, 0, ListByteData, ListByteDataLen, resLen3);
                            ListByteDataLen += resLen3;
                            i++;
                        } else {
                            Log.e(TAG, "Send Error");
                            close_Spi(0);
                            return null;
                        }
                    }
                }
                close_Spi(0);
                String resultList =
                        SemServiceTools.bytesToHex(Arrays.copyOf(ListByteData, ListByteDataLen));
                return resultList;
            }
            Log.e(TAG, "S-LOG Open Fail");
            return null;
        } catch (Error e) {
            Log.e(TAG, "GET DATA Error " + e);
            if (0 != 0) {
                close_Spi(0);
                return null;
            }
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "GET DATA EXCEPTION " + e2);
            if (0 != 0) {
                close_Spi(0);
                return null;
            }
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String appCheckLog() {
        /*
            Method dump skipped, instructions count: 383
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.SemService.appCheckLog():java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0174, code lost:

       close_Spi(0);
       r12 = android.util.Base64.encodeToString(java.util.Arrays.copyOf(r8, r9), 2);
       android.util.Log.d(com.android.server.SemService.TAG, "SEMSVC[4] : " + r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x019b, code lost:

       return r12;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getEncodedDCKLog() {
        /*
            Method dump skipped, instructions count: 475
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.SemService.getEncodedDCKLog():java.lang.String");
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void secureLog(String msg) {
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "SecureLog Permission Error");
            return;
        }
        try {
            this.secureBuffer.append(getDate() + " : " + msg + "\n");
        } catch (Error e) {
            Log.e(TAG, "S-LOG Error " + e);
        } catch (Exception e2) {
            Log.e(TAG, "S-LOG Exception " + e2);
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void start_SLOG() {
        Log.i(TAG, "Start_SLOG");
        byte[] getPKDataSecurity = new byte[300];
        byte[] getPKDataSKMS = new byte[300];
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "Start_SLOG Permission Error");
            return;
        }
        try {
            int resultSecurity = getpkSecurity(getPKDataSecurity);
            int resultSKMS = getpkSKMS(getPKDataSKMS);
            this.bytePublicKeySecurityLen = resultSecurity;
            this.bytePublicKeySKMSLen = resultSKMS;
            this.bytePublicKeyDataSecurity =
                    Arrays.copyOf(getPKDataSecurity, this.bytePublicKeySecurityLen);
            this.bytePublicKeyDataSKMS = Arrays.copyOf(getPKDataSKMS, this.bytePublicKeySKMSLen);
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "USLE Exception : " + e);
        } catch (Error e2) {
            Log.e(TAG, "Error : " + e2);
        } catch (Exception e3) {
            Log.e(TAG, "Exception : " + e3);
        } catch (NoClassDefFoundError e4) {
            Log.e(TAG, "NCDF Exception : " + e4);
        }
    }

    public String DCKLog() {
        StringBuilder sb;
        Log.d(TAG, "DP :DK");
        String EdckLog = getEncodedDCKLog();
        if (EdckLog != null) {
            BufferedWriter bw = null;
            try {
                try {
                    FileOutputStream fos = new FileOutputStream("/data/log/sse4", false);
                    bw = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
                    bw.write(EdckLog);
                    bw.write("\n");
                    bw.flush();
                    Runtime rt = Runtime.getRuntime();
                    Process prc = rt.exec("chmod a+r -R /data/log/sse4");
                    prc.waitFor();
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e1 = e;
                        sb = new StringBuilder();
                        Log.e(TAG, sb.append("Close Fail ").append(e1).toString());
                        return EdckLog;
                    }
                } catch (Throwable e1) {
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e12) {
                            Log.e(TAG, "Close Fail " + e12);
                        }
                    }
                    throw e1;
                }
            } catch (Exception e2) {
                Log.e(TAG, "Save Exception " + e2);
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e3) {
                        e1 = e3;
                        sb = new StringBuilder();
                        Log.e(TAG, sb.append("Close Fail ").append(e1).toString());
                        return EdckLog;
                    }
                }
            }
        }
        return EdckLog;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x008f, code lost:

       if (r9 != null) goto L18;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0091, code lost:

       r9.destroy();
    */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00cb, code lost:

       android.util.Log.i(com.android.server.SemService.TAG, "Buffer Init");
       r14.secureBuffer.delete(0, r14.secureBuffer.length());
       r14.secureBuffer.setLength(0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00e1, code lost:

       if (r7 == null) goto L32;
    */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00ed, code lost:

       if (r6 == null) goto L87;
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00ef, code lost:

       r6.flush();
       r6.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00eb, code lost:

       r10 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00f8, code lost:

       r11 = new java.lang.StringBuilder();
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0142, code lost:

       android.util.Log.e(com.android.server.SemService.TAG, r11.append("Close Exception ").append(r10).toString());
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0153, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00e3, code lost:

       r7.flush();
       r7.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00fe, code lost:

       r10 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0154, code lost:

       if (r7 != null) goto L81;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0160, code lost:

       if (r6 != null) goto L66;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0162, code lost:

       r6.flush();
       r6.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x015e, code lost:

       r11 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x016b, code lost:

       android.util.Log.e(com.android.server.SemService.TAG, "Close Exception " + r11);
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0181, code lost:

       throw r10;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0156, code lost:

       r7.flush();
       r7.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0101, code lost:

       android.util.Log.e(com.android.server.SemService.TAG, "Buffer Error");
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0106, code lost:

       if (r7 != null) goto L79;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0112, code lost:

       if (r6 != null) goto L45;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0114, code lost:

       r6.flush();
       r6.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0110, code lost:

       r10 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x011b, code lost:

       r11 = new java.lang.StringBuilder();
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0108, code lost:

       r7.flush();
       r7.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0122, code lost:

       android.util.Log.e(com.android.server.SemService.TAG, "Buffer Exception");
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0127, code lost:

       if (r7 != null) goto L77;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0133, code lost:

       if (r6 != null) goto L55;
    */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0135, code lost:

       r6.flush();
       r6.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0131, code lost:

       r10 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x013d, code lost:

       r11 = new java.lang.StringBuilder();
    */
    /* JADX WARN: Code restructure failed: missing block: B:67:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0129, code lost:

       r7.flush();
       r7.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00c8, code lost:

       if (0 == 0) goto L85;
    */
    @Override // com.samsung.android.service.SemService.ISemService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void stop_SLOG() {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.SemService.stop_SLOG():void");
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void agent_SLOG(String logStr) {
        StringBuilder sb;
        BufferedWriter bw;
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "Agent_SLOG Permission Error");
            return;
        }
        BufferedWriter bw2 = null;
        FileOutputStream fos = null;
        try {
            byte[] getPKDataSecurity = new byte[300];
            byte[] getPKDataSKMS = new byte[300];
            int resultSecurityLen = getpkSecurity(getPKDataSecurity);
            int resultSKMSLen = getpkSKMS(getPKDataSKMS);
            this.bytePublicKeySecurityLen = resultSecurityLen;
            this.bytePublicKeySKMSLen = resultSKMSLen;
            this.bytePublicKeyDataSecurity =
                    Arrays.copyOf(getPKDataSecurity, this.bytePublicKeySecurityLen);
            this.bytePublicKeyDataSKMS = Arrays.copyOf(getPKDataSKMS, this.bytePublicKeySKMSLen);
        } catch (Exception e) {
            Log.e(TAG, "Get SL Key ex " + e);
        }
        String dirPath = getSLogPath("/data/log/sse5", "/data/log/sse6");
        try {
            try {
                String logStr2 = encData(logStr);
                Log.d(TAG, "DP : " + dirPath);
                fos = new FileOutputStream(dirPath, true);
                bw2 = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
                if (logStr2 != null) {
                    bw2.write(logStr2);
                }
                bw2.write("\n");
                bw2.flush();
                bw2.close();
                bw = null;
            } catch (Exception e2) {
                Log.e(TAG, "ASLog ex " + e2);
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e3) {
                        e1 = e3;
                        sb = new StringBuilder();
                        Log.e(TAG, sb.append("ASLog Ex ").append(e1).toString());
                    }
                }
                if (bw2 != null) {
                    bw2.flush();
                    bw2.close();
                } else {
                    return;
                }
            }
            try {
                fos.flush();
                fos.close();
                if (0 != 0) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException e4) {
                e1 = e4;
                sb = new StringBuilder();
                Log.e(TAG, sb.append("ASLog Ex ").append(e1).toString());
            }
        } catch (Throwable e1) {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e12) {
                    Log.e(TAG, "ASLog Ex " + e12);
                    throw e1;
                }
            }
            if (bw2 != null) {
                bw2.flush();
                bw2.close();
            }
            throw e1;
        }
    }

    public String getSLogPath(String dirPath1, String dirPath2) {
        String resultPath;
        try {
            File file1 = new File(dirPath1);
            File file2 = new File(dirPath2);
            if (file1.exists() && file1.length() > 102400) {
                if (!file2.exists() || file2.length() <= 102400) {
                    resultPath = dirPath2;
                } else if (file1.lastModified() > file2.lastModified()) {
                    resultPath = dirPath2;
                    file2.delete();
                } else {
                    resultPath = dirPath1;
                    file1.delete();
                }
            } else {
                resultPath = dirPath1;
            }
            return resultPath;
        } catch (Exception e) {
            return dirPath1;
        }
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        String encEsekCert;
        String ESEKCert;
        String str;
        String str2 = TAG;
        try {
            if (SystemProperties.get("ro.security.ese.cosname", "").equals("")) {
                pw.println("not support eSE device: can't dump");
                return;
            }
            Log.i(TAG, "DUMP MANAGER LOG START");
            if (this.mContext.checkCallingOrSelfPermission(Manifest.permission.DUMP) != 0) {
                pw.println("Permission Denial: can't dump");
                return;
            }
            if (pw == null) {
                Log.e(TAG, "invalid parameters dump");
                str = TAG;
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append("\n***SemService EDS***");
                sb.append("\n[SCRS LIST]");
                int uid = Binder.getCallingUid();
                String packageName = this.mSemServiceAccessControl.getPackageName();
                this.mSemServiceAccessControl.addAllowedPackage(
                        packageName, uid, SemServiceAccessControl.PackageList.MJavaPkgList);
                this.mSemServiceAccessControl.addAllowedPackage(
                        packageName, uid, SemServiceAccessControl.PackageList.MFactoryPkgList);
                String SCRSInfo = getSCRSActivationList();
                String ARAInfo = getAccessRule();
                String CPLCInfo = getCPLC14mode();
                String appletInfo = appCheckLog();
                String ACdumpInfo = get_ESEA();
                String DPDLog = get_DPDLog();
                String DCKInfo = DCKLog();
                String ESEKCert2 = null;
                String SCP11Cert = null;
                if (this.supportEsek) {
                    String ESEKCert3 =
                            SemServiceTools.readFileBytes(
                                    Paths.get("/efs/sec_efs/esek/esek_cert.dat", new String[0]));
                    SCP11Cert =
                            SemServiceTools.readFileBytes(
                                    Paths.get("/efs/sec_efs/esek/scp11_cert.dat", new String[0]));
                    ESEKCert2 = ESEKCert3;
                }
                start_SLOG();
                String encSCRSInfo = encData(SCRSInfo);
                String encARAInfo = encData(ARAInfo);
                String encAppletInfo = encData(appletInfo);
                String encCPLCInfo = encData(CPLCInfo);
                String encACdumpInfo = encData(ACdumpInfo);
                String encEsekCert2 = null;
                String encSCP11Cert = null;
                if (!this.supportEsek) {
                    encEsekCert = null;
                    ESEKCert = null;
                } else {
                    if (ESEKCert2 != null) {
                        encEsekCert2 = encData(ESEKCert2);
                    }
                    if (SCP11Cert != null) {
                        encSCP11Cert = encData(SCP11Cert);
                    }
                    encEsekCert = encEsekCert2;
                    ESEKCert = encSCP11Cert;
                }
                StringBuffer stringBuffer = this.secureBuffer;
                str = TAG;
                try {
                    stringBuffer.append(SCRSInfo + "\n");
                    this.secureBuffer.append(encARAInfo + "\n");
                    this.secureBuffer.append(appletInfo + "\n");
                    this.secureBuffer.append(CPLCInfo + "\n");
                    this.secureBuffer.append(ACdumpInfo + "\n");
                    stop_SLOG();
                    sb.append("\n" + encCPLCInfo);
                    sb.append("\n" + encSCRSInfo);
                    sb.append("\n" + encARAInfo);
                    sb.append("\n" + encAppletInfo);
                    sb.append("\n" + encACdumpInfo);
                    sb.append("\nDPD : " + DPDLog);
                    if (DCKInfo != null) {
                        sb.append("\nSEMSVC[4]");
                        sb.append("\n" + DCKInfo);
                    }
                    if (this.supportEsek) {
                        if (encEsekCert != null) {
                            sb.append("\nESEK_Cert : " + encEsekCert);
                        }
                        if (ESEKCert != null) {
                            sb.append("\nSCP11 Cert : " + ESEKCert);
                        }
                    }
                    this.mSemServiceAccessControl.removeAllowedPackage(
                            packageName, SemServiceAccessControl.PackageList.MJavaPkgList);
                    this.mSemServiceAccessControl.removeAllowedPackage(
                            packageName, SemServiceAccessControl.PackageList.MFactoryPkgList);
                    pw.println(sb.toString());
                } catch (Error e) {
                    e = e;
                    str2 = str;
                    Log.e(str2, "DUMP MANAGER ERROR " + e);
                    return;
                } catch (Exception e2) {
                    e = e2;
                    str2 = str;
                    Log.e(str2, "DUMP MANAGER EXCEPTION " + e);
                    return;
                }
            }
            printEnhancedDump();
            Log.i(str, "DUMP MANAGER LOG END");
        } catch (Error e3) {
            e = e3;
        } catch (Exception e4) {
            e = e4;
        }
    }

    private String getDate() {
        try {
            return new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS", Locale.getDefault())
                    .format(Long.valueOf(System.currentTimeMillis()));
        } catch (Exception e) {
            return "";
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int getAtr_Spi() {
        Log.i(TAG, "Start getAtr");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MCosPatchPkgList)) {
            Log.e(TAG, "getAtr Permission Error");
            return -91;
        }
        if (!isValidPackageForSpi()) {
            return -200;
        }
        try {
            int ret = getAtr();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to getAtr_Spi, " + e.toString());
            return 0;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef getAtr_Spi, " + e2.toString());
            return 0;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield getAtr_Spi, " + e3.toString());
            return 0;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int resetForCOSU() {
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MCosPatchPkgList)) {
            Log.e(TAG, "resetForCOSU Permission Error");
            return -91;
        }
        if (!isValidPackageForSpi()) {
            return -200;
        }
        int ret = coldReset();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int open_Spi(int flag) {
        Log.i(TAG, "Start open_Spi");
        if (flag == 0 && !this.supportReeSpi) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.SEAPIAccessPermission()) {
            Log.e(TAG, "open_Spi Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        try {
            int ret = openSpi(flag);
            if (ret != 0) {
                releaseSpiUsage();
            } else if (flag == 0) {
                startSPITimer();
            }
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to open_Spi, " + e.toString());
            return -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef open_Spi, " + e2.toString());
            return -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield open_Spi, " + e3.toString());
            return -3;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int close_Spi(int flag) {
        Log.i(TAG, "Start close_Spi");
        if (flag == 0 && !this.supportReeSpi) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.SEAPIAccessPermission()) {
            Log.e(TAG, "close_Spi Permission Error");
            return -91;
        }
        if (!isValidPackageForSpi()) {
            return -200;
        }
        if (flag == 0) {
            try {
                stopSPITimer();
            } catch (Exception e) {
                Log.e(TAG, "Failed to close_Spi, " + e.toString());
                return -90;
            } catch (NoClassDefFoundError e2) {
                Log.e(TAG, "NoClassDef close_Spi, " + e2.toString());
                return -2;
            } catch (UnsatisfiedLinkError e3) {
                Log.e(TAG, "Unsatisfield close_Spi, " + e3.toString());
                return -3;
            }
        }
        int ret = synchronizedCloseSpi(flag);
        releaseSpiUsage();
        return ret;
    }

    public synchronized int synchronizedCloseSpi(int flag) {
        return closeSpi(flag);
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int send_Data(byte[] baCmd, int cLen, byte[] baRsp, int flag) {
        Log.i(TAG, "Start send_Data");
        if (flag == 0 && !this.supportReeSpi) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.SEAPIAccessPermission()) {
            Log.e(TAG, "send_Data Permission Error");
            return -91;
        }
        if (!isValidPackageForSpi()) {
            return -200;
        }
        if (flag == 0) {
            try {
                stopSPITimer();
            } catch (Exception e) {
                Log.e(TAG, "Failed to send_Data, " + e.toString());
                return -90;
            } catch (NoClassDefFoundError e2) {
                Log.e(TAG, "NoClassDef send_Data, " + e2.toString());
                return -2;
            } catch (UnsatisfiedLinkError e3) {
                Log.e(TAG, "Unsatisfield send_Data, " + e3.toString());
                return -3;
            }
        }
        int ret = sendData(baCmd, cLen, baRsp, flag);
        if (flag != 0) {
            return ret;
        }
        startSPITimer();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int check_SeState(byte[] appletAid, byte[] associatedAid) {
        int ret;
        Log.i(TAG, "Start checkSeState");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MScpKmPkgList)) {
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        try {
            ret = checkSeStatus(appletAid, associatedAid);
        } catch (Exception e) {
            Log.e(TAG, "Failed to check_SeState, " + e.toString());
            ret = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef check_SeState, " + e2.toString());
            ret = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield check_SeState, " + e3.toString());
            ret = -3;
        }
        releaseSpiUsage();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int start_request_credentials(
            byte[] appletAid, byte[] associatedAid, String serviceName, byte[] key_blob) {
        int ret;
        byte[] teeAllowList;
        Log.i(TAG, "Start start_request_credentials");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MScpKmPkgList)) {
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        try {
            if (this.mSemServiceAccessControl.getScpkmDAFileSupport()) {
                try {
                    byte[] teeAllowListSignature =
                            this.mSemServiceAccessControl.getScpkmTeeSigData();
                    try {
                        teeAllowList = this.mSemServiceAccessControl.getScpkmTeeListData();
                    } catch (Exception e) {
                        e = e;
                    } catch (NoClassDefFoundError e2) {
                        e = e2;
                    } catch (UnsatisfiedLinkError e3) {
                        e = e3;
                    }
                    try {
                        if (teeAllowListSignature == null || teeAllowList == null) {
                            Log.e(TAG, "Data Error");
                        } else {
                            int ret2 =
                                    startRequestCredentialsList(
                                            appletAid,
                                            associatedAid,
                                            serviceName,
                                            teeAllowListSignature,
                                            teeAllowList,
                                            key_blob);
                            releaseSpiUsage();
                            return ret2;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        Log.e(TAG, "Get tList Ex " + e);
                        ret =
                                startRequestCredentials(
                                        appletAid, associatedAid, serviceName, key_blob);
                        releaseSpiUsage();
                        return ret;
                    } catch (NoClassDefFoundError e5) {
                        e = e5;
                        Log.e(TAG, "Get tList NoClassDef " + e);
                        ret =
                                startRequestCredentials(
                                        appletAid, associatedAid, serviceName, key_blob);
                        releaseSpiUsage();
                        return ret;
                    } catch (UnsatisfiedLinkError e6) {
                        e = e6;
                        Log.e(TAG, "Get tList Unsatisfield " + e);
                        ret =
                                startRequestCredentials(
                                        appletAid, associatedAid, serviceName, key_blob);
                        releaseSpiUsage();
                        return ret;
                    }
                } catch (Exception e7) {
                    e = e7;
                } catch (NoClassDefFoundError e8) {
                    e = e8;
                } catch (UnsatisfiedLinkError e9) {
                    e = e9;
                }
            }
            ret = startRequestCredentials(appletAid, associatedAid, serviceName, key_blob);
        } catch (Exception e10) {
            Log.e(TAG, "Failed to start_request_credentials, " + e10.toString());
            ret = -90;
        } catch (NoClassDefFoundError e11) {
            Log.e(TAG, "NoClassDef start_request_credentials, " + e11.toString());
            ret = -2;
        } catch (UnsatisfiedLinkError e12) {
            Log.e(TAG, "Unsatisfield start_request_credentials, " + e12.toString());
            ret = -3;
        }
        releaseSpiUsage();
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void stop_request_credentials() {
        Log.i(TAG, "Start stop_request_credentials");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MScpKmPkgList)) {
            return;
        }
        try {
            stopRequestCredentials();
        } catch (Exception e) {
            Log.e(TAG, "Failed to stop_request_credentials, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef stop_request_credentials, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield stop_request_credentials, " + e3.toString());
        }
    }

    private boolean isGRDMSupported() {
        return !"".equals("");
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_get_session() {
        int ret;
        Log.i(TAG, "Start grdm_get_session");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                if (!this.mIsOpened) {
                    ret = grdmGetSession();
                    if (ret == 1) {
                        this.mIsOpened = true;
                    }
                } else {
                    ret = -11;
                }
            } catch (NoClassDefFoundError e) {
                Log.e(TAG, "NoClassDef start_request_grdm, " + e.toString());
                ret = -2;
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "Unsatisfield start_request_grdm, " + e2.toString());
                ret = -3;
            }
        } catch (Exception e3) {
            Log.e(TAG, "Failed to start_request_grdm, " + e3.toString());
            ret = -90;
        }
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_request_key(int domainIndex, byte[] key_blob) {
        int ret;
        Log.i(TAG, "Start grdm_request_key");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                try {
                    ret = grdmRequestKey(domainIndex, key_blob);
                } catch (NoClassDefFoundError e) {
                    Log.e(TAG, "NoClassDef grdm_request_key, " + e.toString());
                    ret = -2;
                }
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "Unsatisfield grdm_request_key, " + e2.toString());
                ret = -3;
            }
        } catch (Exception e3) {
            Log.e(TAG, "Failed to grdm_request_key, " + e3.toString());
            ret = -90;
        }
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_release_session() {
        int ret;
        Log.i(TAG, "Start grdm_release_session");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                if (this.mIsOpened) {
                    ret = grdmReleaseSession();
                    this.mIsOpened = false;
                } else {
                    ret = -12;
                }
            } catch (NoClassDefFoundError e) {
                Log.e(TAG, "NoClassDef grdm_release_session, " + e.toString());
                ret = -2;
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "Unsatisfield grdm_release_session, " + e2.toString());
                ret = -3;
            }
        } catch (Exception e3) {
            Log.e(TAG, "Failed to grdm_release_session, " + e3.toString());
            ret = -90;
        }
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_get_attes_cert(int index, byte[] rspData) {
        int ret;
        Log.i(TAG, "Start grdm_get_attes_cert");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                try {
                    ret = grdmGetAttesCert(index, rspData);
                } catch (NoClassDefFoundError e) {
                    Log.e(TAG, "NoClassDef grdm_get_attes_cert, " + e.toString());
                    ret = -2;
                }
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "Unsatisfield grdm_get_attes_cert, " + e2.toString());
                ret = -3;
            }
        } catch (Exception e3) {
            Log.e(TAG, "Failed to grdm_get_attes_cert, " + e3.toString());
            ret = -90;
        }
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized String grdm_check_restricted_mode() {
        Log.i(TAG, "Start grdm_check_restricted_mode");
        if (!isGRDMSupported()
                || !this.mSemServiceAccessControl.hasAccessPermission(
                        SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return ERROR_NO_PERMISSION_STRING;
        }
        try {
            try {
                byte[] dataBuf = new byte[1000];
                int dataLen = grdmCheckRestrictedMode(dataBuf);
                if (dataLen <= 0) {
                    Log.e(TAG, "no data to be returned");
                    return null;
                }
                if (dataLen < 1000) {
                    String data =
                            new String(Arrays.copyOf(dataBuf, dataLen), StandardCharsets.UTF_8);
                    Log.i(TAG, "grdm_check_restricted_mode Return : " + data);
                    return data;
                }
                Log.e(TAG, "data overflow");
                return null;
            } catch (Exception e) {
                Log.e(TAG, "Failed to grdm_check_restricted_mode, " + e.toString());
                return null;
            } catch (NoClassDefFoundError e2) {
                Log.e(TAG, "NoClassDef grdm_check_restricted_mode, " + e2.toString());
                return null;
            }
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield grdm_check_restricted_mode, " + e3.toString());
            return null;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_Check_Status() {
        int ret;
        Log.i(TAG, "Start grdm_Check_Status");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                try {
                    ret = grdmCheckStatusInfo();
                } catch (NoClassDefFoundError e) {
                    Log.e(TAG, "NoClassDef grdm_get_attes_cert, " + e.toString());
                    ret = -2;
                }
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "Unsatisfield grdm_get_attes_cert, " + e2.toString());
                ret = -3;
            }
        } catch (Exception e3) {
            Log.e(TAG, "Failed to grdm_get_attes_cert, " + e3.toString());
            ret = -90;
        }
        return ret;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int openSpiDriver() {
        int openResult;
        Log.i(TAG, "openSpiDriver");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "openSpiDriver Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        try {
            openResult = openDriverSpi();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            openResult = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            openResult = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            openResult = -3;
        }
        releaseSpiUsage();
        return openResult;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int closeSpiDriver() {
        int closeResult;
        Log.i(TAG, "closeSpiDriver");
        if (!this.mSemServiceAccessControl.hasAccessPermission(
                SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "closeSpiDriver Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        if (!this.mSemServiceAccessControl.checkStatus()) {
            Log.e(TAG, "ICCC Device Status Error");
            releaseSpiUsage();
            return -91;
        }
        try {
            closeResult = closeDriverSpi();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            closeResult = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            closeResult = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            closeResult = -3;
        }
        releaseSpiUsage();
        return closeResult;
    }
}
