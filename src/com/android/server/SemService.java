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
import com.android.server.SemServiceAccessControl;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.service.SemService.ISemService;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
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

/* loaded from: classes6.dex */
public final class SemService extends ISemService.Stub {
    public static final int ERROR = -1;
    public static final int ERROR_ALREADY_OPENED = -11;
    public static final int ERROR_CLASS_NOT_FOUND = -2;
    public static final int ERROR_EXCEPTION = -90;
    public static final int ERROR_NOT_OPENED = -12;
    public static final int ERROR_NOT_SUPPORTED = -10;
    public static final int ERROR_NO_PERMISSION = -91;
    public static final int ERROR_NO_PERMISSION_SIZE = 0;
    public static final String ERROR_NO_PERMISSION_STRING = null;
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
    private static String chipVendor = "NXP";
    private static String cosName = "JCOP7.1U";
    private static final Object mLock = new Object();
    ConnectivityManager.NetworkCallback CMCallback;
    private Timer SPITimeout;
    private byte[] bodyData;
    private ConnectivityManager connectivityManager;
    private Context mContext;
    private boolean mIsOpened;
    private SemServiceAccessControl mSemServiceAccessControl;
    private StringBuffer secureBuffer;
    private String skuChipName;
    private String spiOpenPackageName;
    private boolean supportEsek;
    private boolean supportReeSpi;
    private byte[] bytePublicKeyDataSKMS = new byte[300];
    private byte[] bytePublicKeyDataSecurity = new byte[300];
    private int bytePublicKeySecurityLen = 0;
    private int bytePublicKeySKMSLen = 0;

    private native byte[] getDPDLog();

    private boolean isGRDMSupported() {
        return false;
    }

    public native int checkSeStatus(byte[] bArr, byte[] bArr2);

    public native int closeDriverSpi();

    public native int closeSpi(int i);

    public native int coldReset();

    public native int continueattestation(String str, int i, byte[] bArr);

    public native int deactivateCards(int i, byte[][] bArr, int[] iArr, int i2);

    public native int deactivateCardsAID(int i, int i2, byte[][] bArr, int[] iArr, int i3, byte[][] bArr2, int[] iArr2, int i4, byte[][] bArr3, int[] iArr3, int i5);

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

    public native int startRequestCredentialsList(byte[] bArr, byte[] bArr2, String str, byte[] bArr3, byte[] bArr4, byte[] bArr5);

    public native int startattestation(byte[] bArr, int i, byte[] bArr2, int i2);

    public native void stopRequestCredentials();

    static {
        System.loadLibrary("sec_sem");
    }

    public SemService(Context context) {
        this.secureBuffer = null;
        this.supportReeSpi = (cosName.isEmpty() || cosName.isBlank()) ? false : true;
        this.supportEsek = true;
        this.skuChipName = "";
        this.spiOpenPackageName = null;
        this.mIsOpened = false;
        this.connectivityManager = null;
        this.CMCallback = null;
        this.bodyData = null;
        Log.i(TAG, "Start SemService");
        SemServiceAccessControl semServiceAccessControl = new SemServiceAccessControl(context);
        this.mSemServiceAccessControl = semServiceAccessControl;
        semServiceAccessControl.setAllowedPackages();
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
        this.connectivityManager = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
    }

    private void readSkuProperty() {
        try {
            this.skuChipName = SystemProperties.get("ro.boot.hardware.sku");
        } catch (Exception unused) {
            Log.e(TAG, "failed to get sysProp: ro.boot.hardware.sku");
        }
    }

    private boolean isShutdownRequested() {
        try {
            if (TextUtils.isEmpty(SystemProperties.get("sys.shutdown.requested"))) {
                return false;
            }
            Log.w(TAG, "Not supported to get ESEA during shutdown process");
            return true;
        } catch (Exception unused) {
            Log.e(TAG, "failed to get prop: sys.shutdown.requested");
            return false;
        }
    }

    private void setCosNameProperty() {
        StringBuilder sb = new StringBuilder();
        if (chipVendor.equals("MULTI")) {
            if (this.skuChipName.equals("s3fwrn5")) {
                sb.append("UT5.1_01000012");
            } else if (this.skuChipName.equals("sn110t")) {
                sb.append("JCOP5.3T_00353145");
            } else if (this.skuChipName.equals("sn220t")) {
                sb.append("JCOP6.2T_00354A4A");
            } else {
                Log.e(TAG, "Not supported skuChipName, " + this.skuChipName);
                return;
            }
        } else {
            sb.append(cosName);
            if (cosName.equals("JCOP5.1F")) {
                sb.append("_00354C52");
            } else if (cosName.equals("JCOP6.2F")) {
                sb.append("_0035544B");
            } else if (cosName.equals("JCOP6.2P")) {
                sb.append("_00505644_0051414C");
            } else if (cosName.equals("JCOP7.0P")) {
                sb.append("_004D4838");
            } else if (!cosName.equals("UT8.2P")) {
                return;
            } else {
                sb.append("_0B010001");
            }
        }
        try {
            SystemProperties.set("ro.security.ese.cosname", sb.toString());
        } catch (Exception unused) {
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
        Log.e(TAG, "The package name currently using SPI does not match, opened : " + this.spiOpenPackageName + ", requested : " + packageName);
        return false;
    }

    private boolean requestSpiUsage() {
        for (int i = 1; i <= 15; i++) {
            synchronized (mLock) {
                if (this.spiOpenPackageName == null) {
                    String packageName = this.mSemServiceAccessControl.getPackageName();
                    this.spiOpenPackageName = packageName;
                    if (packageName != null && packageName != "") {
                        Log.w(TAG, "SPI is opened by " + this.spiOpenPackageName);
                        return true;
                    }
                    Log.w(TAG, "failed to getPackageName.");
                    return false;
                }
            }
            Log.d(TAG, "SPI is currently in use by " + this.spiOpenPackageName + ", wait for 0.5 sec. Retry count : " + i);
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
            return;
        }
        Log.w(TAG, "SPI is released by " + this.spiOpenPackageName);
        this.spiOpenPackageName = null;
    }

    class SPITimeoutTask extends TimerTask {
        SPITimeoutTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Log.e(SemService.TAG, "Close SPI if theree's no APDU communication in 30 seconds");
            if (SemService.this.synchronizedCloseSpi(0) == 0) {
                SemService.this.releaseSpiUsage();
            }
        }
    }

    private void startSPITimer() {
        try {
            Log.d(TAG, "startSPITimer");
            if (this.SPITimeout != null) {
                Log.d(TAG, "Timer's already been started");
                return;
            }
            Timer timer = new Timer();
            this.SPITimeout = timer;
            timer.schedule(new SPITimeoutTask(), 30000L);
        } catch (Exception e) {
            Log.e(TAG, "Exception in startSPITimer " + e);
            this.SPITimeout = null;
        }
    }

    private void stopSPITimer() {
        try {
            Log.d(TAG, "stopSPITimer");
            Timer timer = this.SPITimeout;
            if (timer == null) {
                Log.d(TAG, "Timer's already been stopped");
            } else {
                timer.cancel();
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
        byte[] bArr = new byte[100];
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "getCPLC14mode Permission Error");
            return ERROR_NO_PERMISSION_STRING;
        }
        if (!requestSpiUsage()) {
            return null;
        }
        try {
            int cPLC14mode = getCPLC14mode(bArr);
            Log.i(TAG, "GetCPLC14mode Len " + cPLC14mode);
            releaseSpiUsage();
            if (cPLC14mode <= 0) {
                Log.e(TAG, "no data to be returned");
                return null;
            }
            if (cPLC14mode < 1000) {
                return SemServiceTools.bytesToHex(Arrays.copyOf(bArr, cPLC14mode));
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
        byte[] bArr = new byte[1024];
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "get_ESEA Permission Error");
            return ERROR_NO_PERMISSION_STRING;
        }
        if (!requestSpiUsage() || isShutdownRequested()) {
            return null;
        }
        try {
            int esea = getESEA(bArr);
            Log.d(TAG, "getESEA Len " + esea);
            releaseSpiUsage();
            if (esea <= 0) {
                Log.e(TAG, "no data to be returned");
                return null;
            }
            if (esea < 1024) {
                byte[] copyOf = Arrays.copyOf(bArr, esea);
                Log.d(TAG, "getESEA Return0 : ".concat(new String(copyOf, StandardCharsets.UTF_8)));
                return new String(copyOf, StandardCharsets.UTF_8);
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
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "get_DPDLog Permission Error");
            return ERROR_NO_PERMISSION_STRING;
        }
        if (!requestSpiUsage() || isShutdownRequested()) {
            return null;
        }
        try {
            byte[] dPDLog = getDPDLog();
            Log.d(TAG, "getDPDLog Len " + dPDLog.length);
            releaseSpiUsage();
            return SemServiceTools.getHexString(dPDLog);
        } catch (Exception e) {
            Log.e(TAG, "Failed to getESEA, " + e.toString());
            this.releaseSpiUsage();
            return null;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void sem_factory() {
        Log.i(TAG, "sem_factory");
        if (this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryPkgList) && requestSpiUsage()) {
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
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int esek_certificate_check() {
        int i;
        Log.i(TAG, "Start esek_certificate_check");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryPkgList)) {
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
            i = esekCertificateCheck();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            i = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            i = -3;
        }
        releaseSpiUsage();
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int scp11_certificate_check() {
        int i;
        Log.i(TAG, "Start scp11_certificate_check");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryPkgList)) {
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
            i = scp11CertificateCheck();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            i = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            i = -3;
        }
        releaseSpiUsage();
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int handle_CCMScp11c(byte[] bArr, int i) {
        int i2;
        Log.i(TAG, "Start handle_CCM11c");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryPkgList)) {
            Log.e(TAG, "handle_CCMScp11c Permission Error");
            return -91;
        }
        if (!requestSpiUsage()) {
            return -200;
        }
        try {
        } catch (Exception e) {
            Log.e(TAG, "Failed to handle_CCM, " + e.toString());
            i2 = -1;
            releaseSpiUsage();
            return i2;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef handle_CCM, " + e2.toString());
            i2 = -1;
            releaseSpiUsage();
            return i2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield handle_CCM, " + e3.toString());
            i2 = -1;
            releaseSpiUsage();
            return i2;
        }
        if (!parseScript(bArr, i)) {
            Log.e(TAG, "CCM script error");
            return -9;
        }
        if (this.bodyData == null) {
            Log.e(TAG, "CCM data error");
            return -10;
        }
        Log.d(TAG, "BD Len " + this.bodyData.length);
        byte[] bArr2 = this.bodyData;
        i2 = handleCCMScp11c(bArr2, bArr2.length);
        releaseSpiUsage();
        return i2;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public String[] handle_CCM(byte[] bArr, int i) {
        Log.i(TAG, "Start handle_CCM");
        String[] strArr = null;
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MLccmPkgList)) {
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
            strArr = handleCCM(bArr, i);
        } catch (Exception e) {
            Log.e(TAG, "Failed to handle_CCM, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef handle_CCM, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield handle_CCM, " + e3.toString());
        }
        releaseSpiUsage();
        return strArr;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public String[] handle_CCMCB(byte[] bArr, int i, byte[] bArr2, int i2) {
        Log.i(TAG, "Start handle_CCM");
        String[] strArr = null;
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MLccmPkgList)) {
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
            strArr = handleCCMCB(bArr, i, bArr2, i2);
        } catch (Exception e) {
            Log.e(TAG, "Failed to handle_CCM, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef handle_CCM, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield handle_CCM, " + e3.toString());
        }
        releaseSpiUsage();
        return strArr;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int isLccmSwp() {
        int i;
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MLccmPkgList)) {
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
            i = jniIsLccmSwp();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            i = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            i = -3;
        }
        releaseSpiUsage();
        return i;
    }

    private boolean parseScript(byte[] bArr, int i) {
        try {
            if (bArr == null) {
                Log.e(TAG, "data is null");
                return false;
            }
            if (i <= 2097152 && i >= 0) {
                if (bArr.length < i) {
                    Log.e(TAG, "data overflow");
                    return false;
                }
                if (bArr[0] != -32) {
                    Log.e(TAG, "unknown tag");
                    return false;
                }
                int checkLength = SemServiceTools.checkLength(bArr, 1);
                if (checkLength <= 0) {
                    Log.e(TAG, "not supported tag e0");
                    return false;
                }
                byte b = bArr[1];
                int i2 = (b & 255) < 128 ? 2 : b == -127 ? 3 : b == -126 ? 4 : b == -125 ? 5 : 0;
                int i3 = checkLength + i2;
                Log.d(TAG, i3 + "," + checkLength + "," + i2);
                if (i3 + 66 != i) {
                    Log.e(TAG, "data is inconsistency");
                    return false;
                }
                if (bArr[i2] != 63 || bArr[i2 + 1] != 49) {
                    Log.d(TAG, "Tag '3F31' read error");
                    return false;
                }
                int i4 = i2 + 2;
                int i5 = i2 + 3;
                int checkLength2 = SemServiceTools.checkLength(bArr, i4) + i5;
                Log.d(TAG, "Date : " + SemServiceTools.bytesToHex(Arrays.copyOfRange(bArr, i5, checkLength2)));
                if (bArr[checkLength2] != -31) {
                    Log.e(TAG, "Tag 'E1' read error");
                    return false;
                }
                int i6 = checkLength2 + 1;
                int i7 = checkLength2 + 2;
                int checkLength3 = SemServiceTools.checkLength(bArr, i6);
                Log.i(TAG, "TC : " + ((int) bArr[i7]));
                int i8 = i7 + checkLength3;
                if (bArr[i8] != -30) {
                    Log.e(TAG, "Tag 'E2' read error");
                    return false;
                }
                int i9 = i8 + 1;
                int checkLength4 = SemServiceTools.checkLength(bArr, i9);
                if (checkLength4 <= 0) {
                    Log.e(TAG, "not supported tag e2");
                    return false;
                }
                byte b2 = bArr[i9];
                if ((b2 & 255) < 128) {
                    i8 += 2;
                } else if (b2 == -127) {
                    i8 += 3;
                } else if (b2 == -126) {
                    i8 += 4;
                } else if (b2 == -125) {
                    i8 += 5;
                }
                int i10 = checkLength4 + i8;
                this.bodyData = Arrays.copyOfRange(bArr, i8, i10);
                if (bArr[i10] != -22) {
                    Log.e(TAG, "Invalid script: No signature");
                    return false;
                }
                int i11 = i10 + 1;
                int i12 = i10 + 2;
                int checkLength5 = SemServiceTools.checkLength(bArr, i11);
                if (checkLength5 <= 0) {
                    Log.e(TAG, "not supported tag ea");
                    return false;
                }
                int i13 = checkLength5 + i12;
                if (i != i13) {
                    Log.e(TAG, "wrong length!");
                    return false;
                }
                if (SemServiceTools.ccmVerify(Arrays.copyOfRange(bArr, 0, i3), Arrays.copyOfRange(bArr, i12, i13))) {
                    return true;
                }
                Log.e(TAG, "Invalid signature!");
                return false;
            }
            Log.e(TAG, "invalid data lenth");
            return false;
        } catch (Exception e) {
            Log.e(TAG, "LCCM script Exception : " + e.getMessage());
            return false;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int get_HQMMemory(byte[] bArr) {
        Log.i(TAG, "Start get_HQMMemory");
        int i = 0;
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MHWParamPkgList)) {
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
            i = getHQMMemory(bArr);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get_AttackCountCheck, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef get_AttackCountCheck, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield get_AttackCountCheck, " + e3.toString());
        }
        releaseSpiUsage();
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int deactivate_Cards(int i, String[] strArr, int[] iArr, int i2) {
        Log.i(TAG, "Start deactivate_Cards");
        byte[][] bArr = new byte[i2][];
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MSKMSCardPkgList)) {
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
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                new DataOutputStream(byteArrayOutputStream).writeUTF(strArr[i4]);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                bArr[i4] = byteArray;
                bArr[i4] = Arrays.copyOfRange(byteArray, 2, byteArray.length);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            Log.d(TAG, "Package Name : " + bArr[i4]);
        }
        try {
            i3 = deactivateCards(i, bArr, iArr, i2);
        } catch (Exception e2) {
            Log.e(TAG, "Failed to deactivate_Cards, " + e2.toString());
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NoClassDef deactivate_Cards, " + e3.toString());
        } catch (UnsatisfiedLinkError e4) {
            Log.e(TAG, "Unsatisfield deactivate_Cards, " + e4.toString());
        }
        releaseSpiUsage();
        return i3;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int deactivate_CardsAID(int i, int i2, String[] strArr, int[] iArr, int i3) {
        int i4;
        Log.i(TAG, "Start deactivate_Cards");
        byte[][] bArr = new byte[i3][];
        byte[][] bArr2 = new byte[i3][];
        byte[][] bArr3 = new byte[i3][];
        int[] iArr2 = new int[i3];
        int[] iArr3 = new int[i3];
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MSKMSCardPkgList)) {
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
        Log.d(TAG, "A Size : " + i3);
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < i3; i8++) {
            try {
                String str = strArr[i8];
                if (str == null) {
                    Log.e(TAG, "element is null");
                } else if (str.contains("*")) {
                    String replaceAll = str.replaceAll("[*]", "");
                    if (replaceAll != null) {
                        byte[] hexToBytes = SemServiceTools.hexToBytes(replaceAll);
                        bArr2[i6] = hexToBytes;
                        if (hexToBytes != null) {
                            iArr2[i6] = hexToBytes.length;
                            i6++;
                        }
                    }
                } else if (str.contains("#")) {
                    String replaceAll2 = str.replaceAll("#", "");
                    if (replaceAll2 != null) {
                        byte[] hexToBytes2 = SemServiceTools.hexToBytes(replaceAll2);
                        bArr3[i7] = hexToBytes2;
                        if (hexToBytes2 != null) {
                            iArr3[i7] = hexToBytes2.length;
                            i7++;
                        }
                    }
                } else if (str != null) {
                    byte[] hexToBytes3 = SemServiceTools.hexToBytes(str);
                    bArr[i5] = hexToBytes3;
                    if (hexToBytes3 != null) {
                        iArr[i5] = hexToBytes3.length;
                        i5++;
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
            i4 = deactivateCardsAID(i, i2, bArr, iArr, i5, bArr2, iArr2, i6, bArr3, iArr3, i7);
        } catch (Exception e3) {
            Log.e(TAG, "Failed to deactivate_Cards, " + e3.toString());
            i4 = 0;
            releaseSpiUsage();
            return i4;
        } catch (NoClassDefFoundError e4) {
            Log.e(TAG, "NoClassDef deactivate_Cards, " + e4.toString());
            i4 = 0;
            releaseSpiUsage();
            return i4;
        } catch (UnsatisfiedLinkError e5) {
            Log.e(TAG, "Unsatisfield deactivate_Cards, " + e5.toString());
            i4 = 0;
            releaseSpiUsage();
            return i4;
        }
        releaseSpiUsage();
        return i4;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int eSE_FactoryReset() {
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryResetList)) {
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
        int i = -90;
        try {
            i = eSEFactoryReset();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            i = -3;
        } catch (Error e4) {
            Log.e(TAG, "Error : " + e4);
        }
        releaseSpiUsage();
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int eSE_LowFactoryReset() {
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryResetList)) {
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
        int i = -90;
        try {
            i = eSELowFactoryReset();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            i = -3;
        } catch (Error e4) {
            Log.e(TAG, "Error : " + e4);
        }
        releaseSpiUsage();
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int eSE_FullFactoryReset() {
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryResetList)) {
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
        int i = -90;
        try {
            i = eSEFullFactoryReset();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            i = -3;
        } catch (Error e4) {
            Log.e(TAG, "Error : " + e4);
        }
        releaseSpiUsage();
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int eSE_AidFactoryReset(byte[] bArr, int i) {
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryResetList)) {
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
        int i2 = -90;
        try {
            i2 = eSEAidFactoryReset(bArr, i);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            i2 = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            i2 = -3;
        } catch (Error e4) {
            Log.e(TAG, "Error : " + e4);
        }
        releaseSpiUsage();
        return i2;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void check_Network(int i) {
        if (this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MFactoryResetList)) {
            if (!this.mSemServiceAccessControl.checkStatus()) {
                Log.e(TAG, "ICCC Device Status Error");
                return;
            }
            try {
                if (i == 0) {
                    Log.i(TAG, "F-R-NC");
                    ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.SemService.1
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onAvailable(Network network) {
                            Log.e(SemService.TAG, "F-NC : " + network);
                            Intent intent = new Intent("com.sec.action.SKMS_NETWORK");
                            intent.putExtra("com.sec.action.SKMS_NETWORK_VALUE", 1);
                            intent.setPackage("com.skms.android.agent");
                            SemService.this.mContext.sendBroadcastAsUser(intent, Process.myUserHandle(), "com.samsung.permission.ESE_SYSTEM_PROTECTION");
                        }
                    };
                    this.CMCallback = networkCallback;
                    this.connectivityManager.registerDefaultNetworkCallback(networkCallback);
                    return;
                }
                if (i == 1) {
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
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int ICD() {
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "ICD Permission Error");
            return -91;
        }
        try {
            return jniICD();
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
    public int start_attestation(byte[] bArr, int i, byte[] bArr2, int i2) {
        Log.i(TAG, "start_attestation");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MJavaPkgList)) {
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
        int i3 = -92;
        try {
            i3 = startattestation(bArr, i, bArr2, i2);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            i3 = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
        }
        releaseSpiUsage();
        return i3;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int continue_attestation(String str, int i, byte[] bArr) {
        Log.i(TAG, "continue_atteestation");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MJavaPkgList)) {
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
        int i2 = -92;
        try {
            i2 = continueattestation(str, i, bArr);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            i2 = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
        }
        releaseSpiUsage();
        Log.i(TAG, "result : " + i2);
        return i2;
    }

    private String encData(String str) {
        Log.i(TAG, "S-ED");
        byte[] bArr = new byte[300];
        byte[] bArr2 = new byte[300];
        try {
            if (this.bytePublicKeySecurityLen < 1 || this.bytePublicKeySKMSLen < 1) {
                Log.e(TAG, "PK Error");
                int i = getpkSecurity(bArr);
                int i2 = getpkSKMS(bArr2);
                this.bytePublicKeySecurityLen = i;
                this.bytePublicKeySKMSLen = i2;
                this.bytePublicKeyDataSecurity = Arrays.copyOf(bArr, i);
                byte[] copyOf = Arrays.copyOf(bArr2, this.bytePublicKeySKMSLen);
                this.bytePublicKeyDataSKMS = copyOf;
                if (copyOf != null && this.bytePublicKeyDataSecurity != null && this.bytePublicKeySecurityLen >= 1 && this.bytePublicKeySKMSLen >= 1) {
                    Log.i(TAG, "GET DATA");
                }
                Log.e(TAG, "GET DATA FAIL");
                return null;
            }
            byte[] bArr3 = new byte[16];
            byte[] bArr4 = new byte[32];
            SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
            instanceStrong.setSeed(instanceStrong.generateSeed(16));
            instanceStrong.nextBytes(bArr3);
            instanceStrong.setSeed(instanceStrong.generateSeed(32));
            instanceStrong.nextBytes(bArr4);
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr4, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr3);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(1, secretKeySpec, ivParameterSpec);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey generatePublic = keyFactory.generatePublic(new X509EncodedKeySpec(this.bytePublicKeyDataSecurity));
            PublicKey generatePublic2 = keyFactory.generatePublic(new X509EncodedKeySpec(this.bytePublicKeyDataSKMS));
            byte[] bArr5 = new byte[48];
            System.arraycopy(ivParameterSpec.getIV(), 0, bArr5, 0, 16);
            System.arraycopy(secretKeySpec.getEncoded(), 0, bArr5, 16, 32);
            Cipher cipher2 = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            cipher2.init(1, generatePublic);
            byte[] doFinal = cipher2.doFinal(bArr5);
            Cipher cipher3 = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            cipher3.init(1, generatePublic2);
            byte[] doFinal2 = cipher3.doFinal(bArr5);
            byte[] doFinal3 = cipher.doFinal(str.getBytes());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(doFinal);
            byteArrayOutputStream.write(doFinal2);
            byteArrayOutputStream.write(doFinal3);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return Base64.encodeToString(byteArray, 2);
        } catch (Error e) {
            Log.e(TAG, "ENC Data Error " + e);
            return null;
        } catch (NullPointerException e2) {
            Log.e(TAG, "ENC Data NullpointException " + e2);
            return null;
        } catch (Exception e3) {
            Log.e(TAG, "ENC Data Exception " + e3);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e8, code lost:
    
        return r17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getSCRSActivationList() {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SemService.getSCRSActivationList():java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0116  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getAccessRule() {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SemService.getAccessRule():java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String appCheckLog() {
        /*
            Method dump skipped, instructions count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SemService.appCheckLog():java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0108, code lost:
    
        return null;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getEncodedDCKLog() {
        /*
            Method dump skipped, instructions count: 361
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SemService.getEncodedDCKLog():java.lang.String");
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void secureLog(String str) {
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "SecureLog Permission Error");
            return;
        }
        try {
            this.secureBuffer.append(getDate() + " : " + str + ShaderAssembler.NEWLINE);
        } catch (Error e) {
            Log.e(TAG, "S-LOG Error " + e);
        } catch (Exception e2) {
            Log.e(TAG, "S-LOG Exception " + e2);
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void start_SLOG() {
        Log.i(TAG, "Start_SLOG");
        byte[] bArr = new byte[300];
        byte[] bArr2 = new byte[300];
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "Start_SLOG Permission Error");
            return;
        }
        try {
            int i = getpkSecurity(bArr);
            int i2 = getpkSKMS(bArr2);
            this.bytePublicKeySecurityLen = i;
            this.bytePublicKeySKMSLen = i2;
            this.bytePublicKeyDataSecurity = Arrays.copyOf(bArr, i);
            this.bytePublicKeyDataSKMS = Arrays.copyOf(bArr2, this.bytePublicKeySKMSLen);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
        } catch (Error e4) {
            Log.e(TAG, "Error : " + e4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x007a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String DCKLog() {
        /*
            r8 = this;
            java.lang.String r0 = "Close Fail "
            java.lang.String r1 = "Save Exception "
            java.lang.String r2 = "DP :DK"
            java.lang.String r3 = "SEC_ESE_Service"
            android.util.Log.d(r3, r2)
            java.lang.String r8 = r8.getEncodedDCKLog()
            if (r8 == 0) goto L8f
            r2 = 0
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            java.lang.String r5 = "/data/log/sse4"
            r6 = 0
            r4.<init>(r5, r6)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            java.io.BufferedWriter r5 = new java.io.BufferedWriter     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            java.io.OutputStreamWriter r6 = new java.io.OutputStreamWriter     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            java.nio.charset.Charset r7 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r6.<init>(r4, r7)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r5.write(r8)     // Catch: java.lang.Exception -> L49 java.lang.Throwable -> L76
            java.lang.String r2 = "\n"
            r5.write(r2)     // Catch: java.lang.Exception -> L49 java.lang.Throwable -> L76
            r5.flush()     // Catch: java.lang.Exception -> L49 java.lang.Throwable -> L76
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Exception -> L49 java.lang.Throwable -> L76
            java.lang.String r4 = "chmod a+r -R /data/log/sse4"
            java.lang.Process r2 = r2.exec(r4)     // Catch: java.lang.Exception -> L49 java.lang.Throwable -> L76
            r2.waitFor()     // Catch: java.lang.Exception -> L49 java.lang.Throwable -> L76
            r5.close()     // Catch: java.io.IOException -> L42
            return r8
        L42:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r0)
            goto L6b
        L49:
            r2 = move-exception
            goto L50
        L4b:
            r8 = move-exception
            goto L78
        L4d:
            r4 = move-exception
            r5 = r2
            r2 = r4
        L50:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L76
            r4.<init>(r1)     // Catch: java.lang.Throwable -> L76
            r4.append(r2)     // Catch: java.lang.Throwable -> L76
            java.lang.String r1 = r4.toString()     // Catch: java.lang.Throwable -> L76
            android.util.Log.e(r3, r1)     // Catch: java.lang.Throwable -> L76
            if (r5 == 0) goto L8f
            r5.close()     // Catch: java.io.IOException -> L65
            goto L8f
        L65:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r0)
        L6b:
            r2.append(r1)
            java.lang.String r0 = r2.toString()
            android.util.Log.e(r3, r0)
            goto L8f
        L76:
            r8 = move-exception
            r2 = r5
        L78:
            if (r2 == 0) goto L8e
            r2.close()     // Catch: java.io.IOException -> L7e
            goto L8e
        L7e:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r0)
            r2.append(r1)
            java.lang.String r0 = r2.toString()
            android.util.Log.e(r3, r0)
        L8e:
            throw r8
        L8f:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SemService.DCKLog():java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x008f, code lost:
    
        if (r4 != null) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0091, code lost:
    
        r4.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00bd, code lost:
    
        android.util.Log.i(com.android.server.SemService.TAG, "Buffer Init");
        r1 = r11.secureBuffer;
        r1.delete(0, r1.length());
        r11.secureBuffer.setLength(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00d1, code lost:
    
        if (r2 == null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00dc, code lost:
    
        if (r8 == null) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00de, code lost:
    
        r8.flush();
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00da, code lost:
    
        r11 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00e5, code lost:
    
        r1 = new java.lang.StringBuilder("Close Exception ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x012a, code lost:
    
        r1.append(r11);
        android.util.Log.e(com.android.server.SemService.TAG, r1.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0134, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00d3, code lost:
    
        r2.flush();
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00eb, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0135, code lost:
    
        if (r2 != null) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0140, code lost:
    
        if (r8 != null) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0142, code lost:
    
        r8.flush();
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x013e, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0149, code lost:
    
        android.util.Log.e(com.android.server.SemService.TAG, "Close Exception " + r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0158, code lost:
    
        throw r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0137, code lost:
    
        r2.flush();
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ed, code lost:
    
        android.util.Log.e(com.android.server.SemService.TAG, "Buffer Error");
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f2, code lost:
    
        if (r2 != null) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00fd, code lost:
    
        if (r8 != null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ff, code lost:
    
        r8.flush();
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00fb, code lost:
    
        r11 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0106, code lost:
    
        r1 = new java.lang.StringBuilder("Close Exception ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00f4, code lost:
    
        r2.flush();
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x010c, code lost:
    
        android.util.Log.e(com.android.server.SemService.TAG, "Buffer Exception");
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0111, code lost:
    
        if (r2 != null) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x011c, code lost:
    
        if (r8 != null) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x011e, code lost:
    
        r8.flush();
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x011a, code lost:
    
        r11 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0125, code lost:
    
        r1 = new java.lang.StringBuilder("Close Exception ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0113, code lost:
    
        r2.flush();
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00ba, code lost:
    
        if (r4 == null) goto L90;
     */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00a6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x015d  */
    @Override // com.samsung.android.service.SemService.ISemService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void stop_SLOG() {
        /*
            Method dump skipped, instructions count: 353
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SemService.stop_SLOG():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00e9 A[Catch: IOException -> 0x00e5, TRY_LEAVE, TryCatch #2 {IOException -> 0x00e5, blocks: (B:51:0x00de, B:44:0x00e9), top: B:50:0x00de }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00de A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.samsung.android.service.SemService.ISemService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void agent_SLOG(java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SemService.agent_SLOG(java.lang.String):void");
    }

    public String getSLogPath(String str, String str2) {
        try {
            File file = new File(str);
            File file2 = new File(str2);
            if (file.exists() && file.length() > 102400) {
                if (!file2.exists() || file2.length() <= 102400) {
                    return str2;
                }
                if (file.lastModified() > file2.lastModified()) {
                    file2.delete();
                    return str2;
                }
                file.delete();
                return str;
            }
        } catch (Exception unused) {
        }
        return str;
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        try {
            if (SystemProperties.get("ro.security.ese.cosname", "").equals("")) {
                printWriter.println("not support eSE device: can't dump");
                return;
            }
            Log.i(TAG, "DUMP MANAGER LOG START");
            if (this.mContext.checkCallingOrSelfPermission(Manifest.permission.DUMP) != 0) {
                printWriter.println("Permission Denial: can't dump");
                return;
            }
            if (printWriter == null) {
                Log.e(TAG, "invalid parameters dump");
                str2 = TAG;
            } else {
                StringBuffer stringBuffer = new StringBuffer("\n***SemService EDS***\n[SCRS LIST]");
                int callingUid = Binder.getCallingUid();
                String packageName = this.mSemServiceAccessControl.getPackageName();
                this.mSemServiceAccessControl.addAllowedPackage(packageName, callingUid, SemServiceAccessControl.PackageList.MJavaPkgList);
                this.mSemServiceAccessControl.addAllowedPackage(packageName, callingUid, SemServiceAccessControl.PackageList.MFactoryPkgList);
                String sCRSActivationList = getSCRSActivationList();
                String accessRule = getAccessRule();
                String cPLC14mode = getCPLC14mode();
                String appCheckLog = appCheckLog();
                String _esea = get_ESEA();
                String str8 = get_DPDLog();
                String DCKLog = DCKLog();
                str2 = TAG;
                try {
                    if (this.supportEsek) {
                        str3 = packageName;
                        str5 = SemServiceTools.readFileBytes(Paths.get("/efs/sec_efs/esek/esek_cert.dat", new String[0]));
                        str4 = SemServiceTools.readFileBytes(Paths.get("/efs/sec_efs/esek/scp11_cert.dat", new String[0]));
                    } else {
                        str3 = packageName;
                        str4 = null;
                        str5 = null;
                    }
                    start_SLOG();
                    String encData = encData(sCRSActivationList);
                    String encData2 = encData(accessRule);
                    String encData3 = encData(appCheckLog);
                    String encData4 = encData(cPLC14mode);
                    String encData5 = encData(_esea);
                    if (this.supportEsek) {
                        str7 = str5 != null ? encData(str5) : null;
                        str6 = str4 != null ? encData(str4) : null;
                    } else {
                        str6 = null;
                        str7 = null;
                    }
                    String str9 = str6;
                    this.secureBuffer.append(sCRSActivationList + ShaderAssembler.NEWLINE);
                    this.secureBuffer.append(encData2 + ShaderAssembler.NEWLINE);
                    this.secureBuffer.append(appCheckLog + ShaderAssembler.NEWLINE);
                    this.secureBuffer.append(cPLC14mode + ShaderAssembler.NEWLINE);
                    this.secureBuffer.append(_esea + ShaderAssembler.NEWLINE);
                    stop_SLOG();
                    stringBuffer.append(ShaderAssembler.NEWLINE + encData4);
                    stringBuffer.append(ShaderAssembler.NEWLINE + encData);
                    stringBuffer.append(ShaderAssembler.NEWLINE + encData2);
                    stringBuffer.append(ShaderAssembler.NEWLINE + encData3);
                    stringBuffer.append(ShaderAssembler.NEWLINE + encData5);
                    stringBuffer.append("\nDPD : " + str8);
                    if (DCKLog != null) {
                        stringBuffer.append("\nSEMSVC[4]");
                        stringBuffer.append(ShaderAssembler.NEWLINE + DCKLog);
                    }
                    if (this.supportEsek) {
                        if (str7 != null) {
                            stringBuffer.append("\nESEK_Cert : " + str7);
                        }
                        if (str9 != null) {
                            stringBuffer.append("\nSCP11 Cert : " + str9);
                        }
                    }
                    String str10 = str3;
                    this.mSemServiceAccessControl.removeAllowedPackage(str10, SemServiceAccessControl.PackageList.MJavaPkgList);
                    this.mSemServiceAccessControl.removeAllowedPackage(str10, SemServiceAccessControl.PackageList.MFactoryPkgList);
                    printWriter.println(stringBuffer.toString());
                } catch (Error e) {
                    e = e;
                    str = str2;
                    Log.e(str, "DUMP MANAGER ERROR " + e);
                } catch (Exception e2) {
                    e = e2;
                    str = str2;
                    Log.e(str, "DUMP MANAGER EXCEPTION " + e);
                }
            }
            printEnhancedDump();
            str = str2;
            try {
                Log.i(str, "DUMP MANAGER LOG END");
            } catch (Error e3) {
                e = e3;
                Log.e(str, "DUMP MANAGER ERROR " + e);
            } catch (Exception e4) {
                e = e4;
                Log.e(str, "DUMP MANAGER EXCEPTION " + e);
            }
        } catch (Error e5) {
            e = e5;
            str = TAG;
        } catch (Exception e6) {
            e = e6;
            str = TAG;
        }
    }

    private String getDate() {
        try {
            return new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis()));
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int getAtr_Spi() {
        Log.i(TAG, "Start getAtr");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MCosPatchPkgList)) {
            Log.e(TAG, "getAtr Permission Error");
            return -91;
        }
        if (!isValidPackageForSpi()) {
            return -200;
        }
        try {
            return getAtr();
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
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MCosPatchPkgList)) {
            Log.e(TAG, "resetForCOSU Permission Error");
            return -91;
        }
        if (isValidPackageForSpi()) {
            return coldReset();
        }
        return -200;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int open_Spi(int i) {
        Log.i(TAG, "Start open_Spi");
        if (i == 0 && !this.supportReeSpi) {
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
            int openSpi = openSpi(i);
            if (openSpi != 0) {
                releaseSpiUsage();
                return openSpi;
            }
            if (i == 0) {
                startSPITimer();
            }
            return openSpi;
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
    public int close_Spi(int i) {
        Log.i(TAG, "Start close_Spi");
        if (i == 0 && !this.supportReeSpi) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.SEAPIAccessPermission()) {
            Log.e(TAG, "close_Spi Permission Error");
            return -91;
        }
        if (!isValidPackageForSpi()) {
            return -200;
        }
        if (i == 0) {
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
        int synchronizedCloseSpi = synchronizedCloseSpi(i);
        releaseSpiUsage();
        return synchronizedCloseSpi;
    }

    public synchronized int synchronizedCloseSpi(int i) {
        return closeSpi(i);
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int send_Data(byte[] bArr, int i, byte[] bArr2, int i2) {
        Log.i(TAG, "Start send_Data");
        if (i2 == 0 && !this.supportReeSpi) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.SEAPIAccessPermission()) {
            Log.e(TAG, "send_Data Permission Error");
            return -91;
        }
        if (!isValidPackageForSpi()) {
            return -200;
        }
        if (i2 == 0) {
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
        int sendData = sendData(bArr, i, bArr2, i2);
        if (i2 == 0) {
            startSPITimer();
        }
        return sendData;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int check_SeState(byte[] bArr, byte[] bArr2) {
        int i;
        Log.i(TAG, "Start checkSeState");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MScpKmPkgList)) {
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
            i = checkSeStatus(bArr, bArr2);
        } catch (Exception e) {
            Log.e(TAG, "Failed to check_SeState, " + e.toString());
            i = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef check_SeState, " + e2.toString());
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield check_SeState, " + e3.toString());
            i = -3;
        }
        releaseSpiUsage();
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int start_request_credentials(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        int i;
        Log.i(TAG, "Start start_request_credentials");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MScpKmPkgList)) {
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
                    byte[] scpkmTeeSigData = this.mSemServiceAccessControl.getScpkmTeeSigData();
                    byte[] scpkmTeeListData = this.mSemServiceAccessControl.getScpkmTeeListData();
                    if (scpkmTeeSigData != null && scpkmTeeListData != null) {
                        int startRequestCredentialsList = startRequestCredentialsList(bArr, bArr2, str, scpkmTeeSigData, scpkmTeeListData, bArr3);
                        releaseSpiUsage();
                        return startRequestCredentialsList;
                    }
                    Log.e(TAG, "Data Error");
                } catch (Exception e) {
                    Log.e(TAG, "Get tList Ex " + e);
                } catch (NoClassDefFoundError e2) {
                    Log.e(TAG, "Get tList NoClassDef " + e2);
                } catch (UnsatisfiedLinkError e3) {
                    Log.e(TAG, "Get tList Unsatisfield " + e3);
                }
            }
            i = startRequestCredentials(bArr, bArr2, str, bArr3);
        } catch (Exception e4) {
            Log.e(TAG, "Failed to start_request_credentials, " + e4.toString());
            i = -90;
        } catch (NoClassDefFoundError e5) {
            Log.e(TAG, "NoClassDef start_request_credentials, " + e5.toString());
            i = -2;
        } catch (UnsatisfiedLinkError e6) {
            Log.e(TAG, "Unsatisfield start_request_credentials, " + e6.toString());
            i = -3;
        }
        releaseSpiUsage();
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public void stop_request_credentials() {
        Log.i(TAG, "Start stop_request_credentials");
        if (this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MScpKmPkgList)) {
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
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_get_session() {
        int i;
        Log.i(TAG, "Start grdm_get_session");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                try {
                    if (this.mIsOpened) {
                        i = -11;
                    } else {
                        i = grdmGetSession();
                        if (i == 1) {
                            this.mIsOpened = true;
                        }
                    }
                } catch (NoClassDefFoundError e) {
                    Log.e(TAG, "NoClassDef start_request_grdm, " + e.toString());
                    i = -2;
                }
            } catch (Exception e2) {
                Log.e(TAG, "Failed to start_request_grdm, " + e2.toString());
                i = -90;
            }
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield start_request_grdm, " + e3.toString());
            i = -3;
        }
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_request_key(int i, byte[] bArr) {
        int i2;
        Log.i(TAG, "Start grdm_request_key");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                i2 = grdmRequestKey(i, bArr);
            } catch (Exception e) {
                Log.e(TAG, "Failed to grdm_request_key, " + e.toString());
                i2 = -90;
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "Unsatisfield grdm_request_key, " + e2.toString());
                i2 = -3;
            }
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NoClassDef grdm_request_key, " + e3.toString());
            i2 = -2;
        }
        return i2;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_release_session() {
        int i;
        Log.i(TAG, "Start grdm_release_session");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            if (this.mIsOpened) {
                i = grdmReleaseSession();
                this.mIsOpened = false;
            } else {
                i = -12;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to grdm_release_session, " + e.toString());
            i = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef grdm_release_session, " + e2.toString());
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield grdm_release_session, " + e3.toString());
            i = -3;
        }
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_get_attes_cert(int i, byte[] bArr) {
        int i2;
        Log.i(TAG, "Start grdm_get_attes_cert");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                i2 = grdmGetAttesCert(i, bArr);
            } catch (Exception e) {
                Log.e(TAG, "Failed to grdm_get_attes_cert, " + e.toString());
                i2 = -90;
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "Unsatisfield grdm_get_attes_cert, " + e2.toString());
                i2 = -3;
            }
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NoClassDef grdm_get_attes_cert, " + e3.toString());
            i2 = -2;
        }
        return i2;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized String grdm_check_restricted_mode() {
        Log.i(TAG, "Start grdm_check_restricted_mode");
        if (!isGRDMSupported() || !this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return ERROR_NO_PERMISSION_STRING;
        }
        try {
            try {
                try {
                    byte[] bArr = new byte[1000];
                    int grdmCheckRestrictedMode = grdmCheckRestrictedMode(bArr);
                    if (grdmCheckRestrictedMode <= 0) {
                        Log.e(TAG, "no data to be returned");
                        return null;
                    }
                    if (grdmCheckRestrictedMode < 1000) {
                        String str = new String(Arrays.copyOf(bArr, grdmCheckRestrictedMode), StandardCharsets.UTF_8);
                        Log.i(TAG, "grdm_check_restricted_mode Return : ".concat(str));
                        return str;
                    }
                    Log.e(TAG, "data overflow");
                    return null;
                } catch (NoClassDefFoundError e) {
                    Log.e(TAG, "NoClassDef grdm_check_restricted_mode, " + e.toString());
                    return null;
                }
            } catch (Exception e2) {
                Log.e(TAG, "Failed to grdm_check_restricted_mode, " + e2.toString());
                return null;
            }
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield grdm_check_restricted_mode, " + e3.toString());
            return null;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_Check_Status() {
        int i;
        Log.i(TAG, "Start grdm_Check_Status");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                i = grdmCheckStatusInfo();
            } catch (Exception e) {
                Log.e(TAG, "Failed to grdm_get_attes_cert, " + e.toString());
                i = -90;
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "Unsatisfield grdm_get_attes_cert, " + e2.toString());
                i = -3;
            }
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NoClassDef grdm_get_attes_cert, " + e3.toString());
            i = -2;
        }
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int openSpiDriver() {
        int i;
        Log.i(TAG, "openSpiDriver");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MJavaPkgList)) {
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
            i = openDriverSpi();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            i = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            i = -3;
        }
        releaseSpiUsage();
        return i;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int closeSpiDriver() {
        int i;
        Log.i(TAG, "closeSpiDriver");
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MJavaPkgList)) {
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
            i = closeDriverSpi();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            i = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            i = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            i = -3;
        }
        releaseSpiUsage();
        return i;
    }
}
