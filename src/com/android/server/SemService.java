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
import com.android.server.SemServiceAccessControl;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
        if (chipVendor.contains("SKU")) {
            readSkuProperty();
            if (this.skuChipName.contains("ese")) {
                setDefaultCosNameProperty();
            } else {
                this.supportReeSpi = false;
            }
        } else {
            setDefaultCosNameProperty();
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

    private void setDefaultCosNameProperty() {
        try {
            SystemProperties.set("ro.security.ese.cosname.default", cosName);
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

    private boolean requestSpiUsage() throws InterruptedException {
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
                byte[] bArrCopyOf = Arrays.copyOf(bArr, esea);
                Log.d(TAG, "getESEA Return0 : ".concat(new String(bArrCopyOf, StandardCharsets.UTF_8)));
                return new String(bArrCopyOf, StandardCharsets.UTF_8);
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
        int iEsekCertificateCheck;
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
            iEsekCertificateCheck = esekCertificateCheck();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            iEsekCertificateCheck = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            iEsekCertificateCheck = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            iEsekCertificateCheck = -3;
        }
        releaseSpiUsage();
        return iEsekCertificateCheck;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int scp11_certificate_check() {
        int iScp11CertificateCheck;
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
            iScp11CertificateCheck = scp11CertificateCheck();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            iScp11CertificateCheck = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            iScp11CertificateCheck = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            iScp11CertificateCheck = -3;
        }
        releaseSpiUsage();
        return iScp11CertificateCheck;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int handle_CCMScp11c(byte[] bArr, int i) {
        int iHandleCCMScp11c;
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
            iHandleCCMScp11c = -1;
            releaseSpiUsage();
            return iHandleCCMScp11c;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef handle_CCM, " + e2.toString());
            iHandleCCMScp11c = -1;
            releaseSpiUsage();
            return iHandleCCMScp11c;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield handle_CCM, " + e3.toString());
            iHandleCCMScp11c = -1;
            releaseSpiUsage();
            return iHandleCCMScp11c;
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
        iHandleCCMScp11c = handleCCMScp11c(bArr2, bArr2.length);
        releaseSpiUsage();
        return iHandleCCMScp11c;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public String[] handle_CCM(byte[] bArr, int i) {
        Log.i(TAG, "Start handle_CCM");
        String[] strArrHandleCCM = null;
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
            strArrHandleCCM = handleCCM(bArr, i);
        } catch (Exception e) {
            Log.e(TAG, "Failed to handle_CCM, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef handle_CCM, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield handle_CCM, " + e3.toString());
        }
        releaseSpiUsage();
        return strArrHandleCCM;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public String[] handle_CCMCB(byte[] bArr, int i, byte[] bArr2, int i2) {
        Log.i(TAG, "Start handle_CCM");
        String[] strArrHandleCCMCB = null;
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
            strArrHandleCCMCB = handleCCMCB(bArr, i, bArr2, i2);
        } catch (Exception e) {
            Log.e(TAG, "Failed to handle_CCM, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef handle_CCM, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield handle_CCM, " + e3.toString());
        }
        releaseSpiUsage();
        return strArrHandleCCMCB;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int isLccmSwp() {
        int iJniIsLccmSwp;
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
            iJniIsLccmSwp = jniIsLccmSwp();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            iJniIsLccmSwp = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            iJniIsLccmSwp = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            iJniIsLccmSwp = -3;
        }
        releaseSpiUsage();
        return iJniIsLccmSwp;
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
                int iCheckLength = SemServiceTools.checkLength(bArr, 1);
                if (iCheckLength <= 0) {
                    Log.e(TAG, "not supported tag e0");
                    return false;
                }
                byte b = bArr[1];
                int i2 = (b & 255) < 128 ? 2 : b == -127 ? 3 : b == -126 ? 4 : b == -125 ? 5 : 0;
                int i3 = iCheckLength + i2;
                Log.d(TAG, i3 + "," + iCheckLength + "," + i2);
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
                int iCheckLength2 = SemServiceTools.checkLength(bArr, i4) + i5;
                Log.d(TAG, "Date : " + SemServiceTools.bytesToHex(Arrays.copyOfRange(bArr, i5, iCheckLength2)));
                if (bArr[iCheckLength2] != -31) {
                    Log.e(TAG, "Tag 'E1' read error");
                    return false;
                }
                int i6 = iCheckLength2 + 1;
                int i7 = iCheckLength2 + 2;
                int iCheckLength3 = SemServiceTools.checkLength(bArr, i6);
                Log.i(TAG, "TC : " + ((int) bArr[i7]));
                int i8 = i7 + iCheckLength3;
                if (bArr[i8] != -30) {
                    Log.e(TAG, "Tag 'E2' read error");
                    return false;
                }
                int i9 = i8 + 1;
                int iCheckLength4 = SemServiceTools.checkLength(bArr, i9);
                if (iCheckLength4 <= 0) {
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
                int i10 = iCheckLength4 + i8;
                this.bodyData = Arrays.copyOfRange(bArr, i8, i10);
                if (bArr[i10] != -22) {
                    Log.e(TAG, "Invalid script: No signature");
                    return false;
                }
                int i11 = i10 + 1;
                int i12 = i10 + 2;
                int iCheckLength5 = SemServiceTools.checkLength(bArr, i11);
                if (iCheckLength5 <= 0) {
                    Log.e(TAG, "not supported tag ea");
                    return false;
                }
                int i13 = iCheckLength5 + i12;
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
        int hQMMemory = 0;
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
            hQMMemory = getHQMMemory(bArr);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get_AttackCountCheck, " + e.toString());
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef get_AttackCountCheck, " + e2.toString());
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield get_AttackCountCheck, " + e3.toString());
        }
        releaseSpiUsage();
        return hQMMemory;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int deactivate_Cards(int i, String[] strArr, int[] iArr, int i2) throws IOException {
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
        int iDeactivateCards = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                new DataOutputStream(byteArrayOutputStream).writeUTF(strArr[i3]);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                bArr[i3] = byteArray;
                bArr[i3] = Arrays.copyOfRange(byteArray, 2, byteArray.length);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            Log.d(TAG, "Package Name : " + bArr[i3]);
        }
        try {
            iDeactivateCards = deactivateCards(i, bArr, iArr, i2);
        } catch (Exception e2) {
            Log.e(TAG, "Failed to deactivate_Cards, " + e2.toString());
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NoClassDef deactivate_Cards, " + e3.toString());
        } catch (UnsatisfiedLinkError e4) {
            Log.e(TAG, "Unsatisfield deactivate_Cards, " + e4.toString());
        }
        releaseSpiUsage();
        return iDeactivateCards;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int deactivate_CardsAID(int i, int i2, String[] strArr, int[] iArr, int i3) {
        int iDeactivateCardsAID;
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
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < i3; i7++) {
            try {
                String str = strArr[i7];
                if (str == null) {
                    Log.e(TAG, "element is null");
                } else if (str.contains("*")) {
                    String strReplaceAll = str.replaceAll("[*]", "");
                    if (strReplaceAll != null) {
                        byte[] bArrHexToBytes = SemServiceTools.hexToBytes(strReplaceAll);
                        bArr2[i5] = bArrHexToBytes;
                        if (bArrHexToBytes != null) {
                            iArr2[i5] = bArrHexToBytes.length;
                            i5++;
                        }
                    }
                } else if (str.contains("#")) {
                    String strReplaceAll2 = str.replaceAll("#", "");
                    if (strReplaceAll2 != null) {
                        byte[] bArrHexToBytes2 = SemServiceTools.hexToBytes(strReplaceAll2);
                        bArr3[i6] = bArrHexToBytes2;
                        if (bArrHexToBytes2 != null) {
                            iArr3[i6] = bArrHexToBytes2.length;
                            i6++;
                        }
                    }
                } else if (str != null) {
                    byte[] bArrHexToBytes3 = SemServiceTools.hexToBytes(str);
                    bArr[i4] = bArrHexToBytes3;
                    if (bArrHexToBytes3 != null) {
                        iArr[i4] = bArrHexToBytes3.length;
                        i4++;
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
            iDeactivateCardsAID = deactivateCardsAID(i, i2, bArr, iArr, i4, bArr2, iArr2, i5, bArr3, iArr3, i6);
        } catch (Exception e3) {
            Log.e(TAG, "Failed to deactivate_Cards, " + e3.toString());
            iDeactivateCardsAID = 0;
            releaseSpiUsage();
            return iDeactivateCardsAID;
        } catch (NoClassDefFoundError e4) {
            Log.e(TAG, "NoClassDef deactivate_Cards, " + e4.toString());
            iDeactivateCardsAID = 0;
            releaseSpiUsage();
            return iDeactivateCardsAID;
        } catch (UnsatisfiedLinkError e5) {
            Log.e(TAG, "Unsatisfield deactivate_Cards, " + e5.toString());
            iDeactivateCardsAID = 0;
            releaseSpiUsage();
            return iDeactivateCardsAID;
        }
        releaseSpiUsage();
        return iDeactivateCardsAID;
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
        int iESEFactoryReset = -90;
        try {
            iESEFactoryReset = eSEFactoryReset();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            iESEFactoryReset = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            iESEFactoryReset = -3;
        } catch (Error e4) {
            Log.e(TAG, "Error : " + e4);
        }
        releaseSpiUsage();
        return iESEFactoryReset;
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
        int iESELowFactoryReset = -90;
        try {
            iESELowFactoryReset = eSELowFactoryReset();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            iESELowFactoryReset = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            iESELowFactoryReset = -3;
        } catch (Error e4) {
            Log.e(TAG, "Error : " + e4);
        }
        releaseSpiUsage();
        return iESELowFactoryReset;
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
        int iESEFullFactoryReset = -90;
        try {
            iESEFullFactoryReset = eSEFullFactoryReset();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            iESEFullFactoryReset = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            iESEFullFactoryReset = -3;
        } catch (Error e4) {
            Log.e(TAG, "Error : " + e4);
        }
        releaseSpiUsage();
        return iESEFullFactoryReset;
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
        int iESEAidFactoryReset = -90;
        try {
            iESEAidFactoryReset = eSEAidFactoryReset(bArr, i);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            iESEAidFactoryReset = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            iESEAidFactoryReset = -3;
        } catch (Error e4) {
            Log.e(TAG, "Error : " + e4);
        }
        releaseSpiUsage();
        return iESEAidFactoryReset;
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
        int iStartattestation = -92;
        try {
            iStartattestation = startattestation(bArr, i, bArr2, i2);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            iStartattestation = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
        }
        releaseSpiUsage();
        return iStartattestation;
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
        int iContinueattestation = -92;
        try {
            iContinueattestation = continueattestation(str, i, bArr);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            iContinueattestation = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
        }
        releaseSpiUsage();
        Log.i(TAG, "result : " + iContinueattestation);
        return iContinueattestation;
    }

    private String encData(String str) throws BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
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
                byte[] bArrCopyOf = Arrays.copyOf(bArr2, this.bytePublicKeySKMSLen);
                this.bytePublicKeyDataSKMS = bArrCopyOf;
                if (bArrCopyOf != null && this.bytePublicKeyDataSecurity != null && this.bytePublicKeySecurityLen >= 1 && this.bytePublicKeySKMSLen >= 1) {
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
            PublicKey publicKeyGeneratePublic = keyFactory.generatePublic(new X509EncodedKeySpec(this.bytePublicKeyDataSecurity));
            PublicKey publicKeyGeneratePublic2 = keyFactory.generatePublic(new X509EncodedKeySpec(this.bytePublicKeyDataSKMS));
            byte[] bArr5 = new byte[48];
            System.arraycopy(ivParameterSpec.getIV(), 0, bArr5, 0, 16);
            System.arraycopy(secretKeySpec.getEncoded(), 0, bArr5, 16, 32);
            Cipher cipher2 = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            cipher2.init(1, publicKeyGeneratePublic);
            byte[] bArrDoFinal = cipher2.doFinal(bArr5);
            Cipher cipher3 = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            cipher3.init(1, publicKeyGeneratePublic2);
            byte[] bArrDoFinal2 = cipher3.doFinal(bArr5);
            byte[] bArrDoFinal3 = cipher.doFinal(str.getBytes());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(bArrDoFinal);
            byteArrayOutputStream.write(bArrDoFinal2);
            byteArrayOutputStream.write(bArrDoFinal3);
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

    private String getSCRSActivationList() {
        String str;
        boolean z;
        int i;
        byte[] bArr = {0, -92, 4, 0, 9, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 1, 81, 67, 82, 83, 0};
        int i2 = 8;
        byte[] bArr2 = {Byte.MIN_VALUE, MidiConstants.STATUS_SONG_POSITION, 64, 0, 2, 79, 0, 0};
        byte[] bArr3 = {Byte.MIN_VALUE, MidiConstants.STATUS_SONG_POSITION, 64, 1, 2, 79, 0, 0};
        int i3 = 9216;
        byte[] bArr4 = new byte[9216];
        String str2 = null;
        try {
            byte[] bArr5 = new byte[9216];
            if (open_Spi(0) != 0) {
                Log.i(TAG, "S-LOG SCRS Open Fail");
                return null;
            }
            try {
                int iSend_Data = send_Data(bArr, 14, bArr5, 0);
                byte[] bArrCopyOf = Arrays.copyOf(bArr5, iSend_Data);
                StringBuilder sb = new StringBuilder("Select SW : ");
                int i4 = iSend_Data - 2;
                sb.append(SemServiceTools.byteToHex(bArrCopyOf[i4]));
                int i5 = iSend_Data - 1;
                sb.append(SemServiceTools.byteToHex(bArrCopyOf[i5]));
                Log.d(TAG, sb.toString());
                if (iSend_Data >= 2 && bArrCopyOf[i4] == -112 && bArrCopyOf[i5] == 0) {
                    int i6 = 0;
                    i = 0;
                    while (true) {
                        if (i6 >= 10) {
                            break;
                        }
                        byte[] bArr6 = new byte[i3];
                        int iSend_Data2 = i6 == 0 ? send_Data(bArr2, i2, bArr6, 0) : send_Data(bArr3, i2, bArr6, 0);
                        byte[] bArrCopyOf2 = Arrays.copyOf(bArr6, iSend_Data2);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("SEND SW[");
                        sb2.append(i6);
                        sb2.append("] : ");
                        int i7 = iSend_Data2 - 2;
                        str = str2;
                        try {
                            sb2.append(SemServiceTools.byteToHex(bArrCopyOf2[i7]));
                            int i8 = iSend_Data2 - 1;
                            sb2.append(SemServiceTools.byteToHex(bArrCopyOf2[i8]));
                            Log.i(TAG, sb2.toString());
                            if (iSend_Data2 >= 2 && bArrCopyOf2[i7] == -112 && bArrCopyOf2[i8] == 0) {
                                Log.i(TAG, "GET DATA FINISH");
                                System.arraycopy(bArrCopyOf2, 0, bArr4, i, i7);
                                i += i7;
                                break;
                            }
                            if (iSend_Data2 < 2 || bArrCopyOf2[i7] != 99 || bArrCopyOf2[i8] != 16) {
                                break;
                            }
                            Log.i(TAG, "GET DATA MORE");
                            System.arraycopy(bArrCopyOf2, 0, bArr4, i, i7);
                            i += i7;
                            i6++;
                            str2 = str;
                            i2 = 8;
                            i3 = 9216;
                        } catch (Exception unused) {
                            z = true;
                            Log.e(TAG, "GET DATA EXCEPTION");
                            if (z) {
                                close_Spi(0);
                            }
                            return str;
                        }
                    }
                    Log.e(TAG, "Send Error");
                    close_Spi(0);
                    return str;
                }
                i = 0;
                close_Spi(0);
                return SemServiceTools.bytesToHex(Arrays.copyOf(bArr4, i));
            } catch (Exception unused2) {
                str = str2;
            }
        } catch (Exception unused3) {
            str = null;
            z = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0116  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String getAccessRule() {
        String str;
        boolean z;
        int i;
        int iSend_Data;
        byte[] bArr = {0, -92, 4, 0, 9, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 1, 81, 65, 67, 76, 0};
        int i2 = 5;
        byte[] bArr2 = {Byte.MIN_VALUE, -54, -1, 64, 0};
        byte[] bArr3 = {Byte.MIN_VALUE, -54, -1, SprAttributeBase.TYPE_DURATION, 0};
        int i3 = 9216;
        byte[] bArr4 = new byte[9216];
        String str2 = null;
        try {
            byte[] bArr5 = new byte[9216];
            if (open_Spi(0) != 0) {
                Log.e(TAG, "S-LOG Open Fail");
                return null;
            }
            z = true;
            try {
                int iSend_Data2 = send_Data(bArr, 14, bArr5, 0);
                byte[] bArrCopyOf = Arrays.copyOf(bArr5, iSend_Data2);
                if (iSend_Data2 >= 2 && bArrCopyOf[iSend_Data2 - 2] == -112 && bArrCopyOf[iSend_Data2 - 1] == 0) {
                    int i4 = 0;
                    i = 0;
                    while (i4 < 50) {
                        byte[] bArr6 = new byte[i3];
                        if (i4 == 0) {
                            try {
                                iSend_Data = send_Data(bArr2, i2, bArr6, 0);
                            } catch (Error e) {
                                e = e;
                                str = str2;
                                Log.e(TAG, "GET DATA Error " + e);
                                if (z) {
                                }
                                return str;
                            } catch (Exception e2) {
                                e = e2;
                                str = str2;
                                Log.e(TAG, "GET DATA EXCEPTION " + e);
                                if (z) {
                                }
                                return str;
                            }
                        } else {
                            iSend_Data = send_Data(bArr3, i2, bArr6, 0);
                        }
                        byte[] bArrCopyOf2 = Arrays.copyOf(bArr6, iSend_Data);
                        StringBuilder sb = new StringBuilder();
                        sb.append("SEND SW[");
                        sb.append(i4);
                        sb.append("] : ");
                        int i5 = iSend_Data - 2;
                        str = str2;
                        try {
                            sb.append(SemServiceTools.byteToHex(bArrCopyOf2[i5]));
                            int i6 = iSend_Data - 1;
                            sb.append(SemServiceTools.byteToHex(bArrCopyOf2[i6]));
                            Log.i(TAG, sb.toString());
                            if (iSend_Data >= 2 && bArrCopyOf2[i5] == 105 && bArrCopyOf2[i6] == -123) {
                                break;
                            }
                            if (iSend_Data < 2 || bArrCopyOf2[i5] != -112 || bArrCopyOf2[i6] != 0) {
                                Log.e(TAG, "Send Error");
                                close_Spi(0);
                                return str;
                            }
                            System.arraycopy(bArrCopyOf2, 0, bArr4, i, i5);
                            i += i5;
                            i4++;
                            str2 = str;
                            i2 = 5;
                            i3 = 9216;
                            z = true;
                        } catch (Error e3) {
                            e = e3;
                            z = true;
                            Log.e(TAG, "GET DATA Error " + e);
                            if (z) {
                                close_Spi(0);
                            }
                            return str;
                        } catch (Exception e4) {
                            e = e4;
                            z = true;
                            Log.e(TAG, "GET DATA EXCEPTION " + e);
                            if (z) {
                                close_Spi(0);
                            }
                            return str;
                        }
                    }
                } else {
                    i = 0;
                }
                close_Spi(0);
                return SemServiceTools.bytesToHex(Arrays.copyOf(bArr4, i));
            } catch (Error e5) {
                e = e5;
                str = str2;
            } catch (Exception e6) {
                e = e6;
                str = str2;
            }
        } catch (Error e7) {
            e = e7;
            str = null;
            z = false;
        } catch (Exception e8) {
            e = e8;
            str = null;
            z = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String appCheckLog() {
        boolean z;
        byte[] bArr = {0, -92, 4, 0, 16, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 2, 32, 32, 3, 1, 3, 1, 0, 0, 0, 0, 0, 17};
        byte[] bArr2 = {0, -92, 4, 0, 12, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 8, SprAttributeBase.TYPE_SHADOW, -4, 0, 0, 0, 0, 0, 7};
        try {
            byte[] bArr3 = new byte[9216];
            if (open_Spi(0) != 0) {
                Log.e(TAG, "S-LOG Open Fail");
                return null;
            }
            z = true;
            try {
                int iSend_Data = send_Data(bArr, 21, bArr3, 0);
                byte[] bArrCopyOf = Arrays.copyOf(bArr3, iSend_Data);
                StringBuilder sb = new StringBuilder("SW : ");
                int i = iSend_Data - 2;
                sb.append(SemServiceTools.byteToHex(bArrCopyOf[i]));
                int i2 = iSend_Data - 1;
                sb.append(SemServiceTools.byteToHex(bArrCopyOf[i2]));
                Log.d(TAG, sb.toString());
                if (iSend_Data >= 2 && bArrCopyOf[i] == -112 && bArrCopyOf[i2] == 0) {
                    Log.i(TAG, "get mDL");
                } else {
                    Log.e(TAG, "Select Response Error");
                }
                byte[] bArr4 = new byte[9216];
                int iSend_Data2 = send_Data(bArr2, 17, bArr4, 0);
                byte[] bArrCopyOf2 = Arrays.copyOf(bArr4, iSend_Data2);
                StringBuilder sb2 = new StringBuilder("SW : ");
                int i3 = iSend_Data2 - 2;
                sb2.append(SemServiceTools.byteToHex(bArrCopyOf2[i3]));
                int i4 = iSend_Data2 - 1;
                sb2.append(SemServiceTools.byteToHex(bArrCopyOf2[i4]));
                Log.d(TAG, sb2.toString());
                if (iSend_Data2 >= 2 && bArrCopyOf2[i3] == -112 && bArrCopyOf2[i4] == 0) {
                    Log.i(TAG, "M Select Success");
                } else {
                    Log.e(TAG, "M Select Response Error");
                }
                String strBytesToHex = SemServiceTools.bytesToHex(bArrCopyOf2);
                close_Spi(0);
                return (("mDL : " + SemServiceTools.bytesToHex(bArrCopyOf)) + "\nMSG : ") + strBytesToHex;
            } catch (Error e) {
                e = e;
                Log.e(TAG, "GET DATA Error " + e);
                if (z) {
                    this.close_Spi(0);
                }
                return null;
            } catch (Exception e2) {
                e = e2;
                Log.e(TAG, "GET DATA EXCEPTION " + e);
                if (z) {
                }
                return null;
            }
        } catch (Error e3) {
            e = e3;
            z = false;
        } catch (Exception e4) {
            e = e4;
            z = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0109, code lost:
    
        close_Spi(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x010c, code lost:
    
        r2 = android.util.Base64.encodeToString(java.util.Arrays.copyOf(r11, r3), 2);
        android.util.Log.d(com.android.server.SemService.TAG, "SEMSVC[4] : " + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0128, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0138, code lost:
    
        r15 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0140, code lost:
    
        close_Spi(0);
     */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String getEncodedDCKLog() {
        String str;
        byte[] bArr = {0, -92, 4, 0, 13, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 8, 9, 67, 67, 67, 68, 75, 65, 118, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, 0};
        int i = 5;
        byte[] bArr2 = {Byte.MIN_VALUE, -54, -97, 8, 0};
        byte[] bArr3 = {Byte.MIN_VALUE, -8, 0, 0, 0};
        byte[] bArr4 = {Byte.MIN_VALUE, -8, 1, 0, 0};
        int i2 = 9216;
        byte[] bArr5 = new byte[9216];
        try {
            byte[] bArr6 = new byte[9216];
            if (open_Spi(0) != 0) {
                Log.e(TAG, "S-LOG DCK Open Fail");
                return null;
            }
            try {
                int iSend_Data = send_Data(bArr, 19, bArr6, 0);
                byte[] bArrCopyOf = Arrays.copyOf(bArr6, iSend_Data);
                StringBuilder sb = new StringBuilder("Select SW : ");
                int i3 = iSend_Data - 2;
                str = null;
                try {
                    sb.append(SemServiceTools.byteToHex(bArrCopyOf[i3]));
                    int i4 = iSend_Data - 1;
                    sb.append(SemServiceTools.byteToHex(bArrCopyOf[i4]));
                    Log.e(TAG, sb.toString());
                    if (iSend_Data < 2 || bArrCopyOf[i3] != -112 || bArrCopyOf[i4] != 0) {
                        Log.e(TAG, "Select Failed");
                        close_Spi(0);
                        return null;
                    }
                    byte[] bArr7 = new byte[9216];
                    Log.d(TAG, "DCK Version : " + SemServiceTools.bytesToHex(Arrays.copyOf(bArr7, send_Data(bArr2, 5, bArr7, 0))));
                    int i5 = 0;
                    int i6 = 0;
                    while (true) {
                        if (i5 >= 65) {
                            break;
                        }
                        byte[] bArr8 = new byte[i2];
                        int iSend_Data2 = i5 == 0 ? send_Data(bArr3, i, bArr8, 0) : send_Data(bArr4, i, bArr8, 0);
                        byte[] bArrCopyOf2 = Arrays.copyOf(bArr8, iSend_Data2);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("SEND SW[");
                        sb2.append(i5);
                        sb2.append("] : ");
                        int i7 = iSend_Data2 - 2;
                        sb2.append(SemServiceTools.byteToHex(bArrCopyOf2[i7]));
                        int i8 = iSend_Data2 - 1;
                        sb2.append(SemServiceTools.byteToHex(bArrCopyOf2[i8]));
                        Log.d(TAG, sb2.toString());
                        if (iSend_Data2 >= 2 && bArrCopyOf2[i7] == -112 && bArrCopyOf2[i8] == 0) {
                            Log.d(TAG, "GET DATA FINISH");
                            System.arraycopy(bArrCopyOf2, 0, bArr5, i6, i7);
                            i6 += i7;
                            break;
                        }
                        if (iSend_Data2 < 2 || bArrCopyOf2[i7] != 99 || bArrCopyOf2[i8] != 16) {
                            break;
                        }
                        Log.d(TAG, "GET DATA MORE");
                        System.arraycopy(bArrCopyOf2, 0, bArr5, i6, i7);
                        i6 += i7;
                        i5++;
                        i = 5;
                        i2 = 9216;
                    }
                    Log.e(TAG, "Send Error");
                    close_Spi(0);
                    return null;
                } catch (Exception unused) {
                    boolean z = true;
                    Log.e(TAG, "GET DATA EXCEPTION");
                    if (z) {
                    }
                    return str;
                }
            } catch (Exception unused2) {
                str = null;
            }
        } catch (Exception unused3) {
            str = null;
        }
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

    /* JADX WARN: Removed duplicated region for block: B:41:0x007a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String DCKLog() throws Throwable {
        BufferedWriter bufferedWriter;
        Exception e;
        StringBuilder sb;
        String string = "Close Fail ";
        Log.d(TAG, "DP :DK");
        String encodedDCKLog = getEncodedDCKLog();
        if (encodedDCKLog != null) {
            BufferedWriter bufferedWriter2 = null;
            try {
                try {
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/data/log/sse4", false), StandardCharsets.UTF_8));
                    try {
                        bufferedWriter.write(encodedDCKLog);
                        bufferedWriter.write(ShaderAssembler.NEWLINE);
                        bufferedWriter.flush();
                        Runtime.getRuntime().exec("chmod a+r -R /data/log/sse4").waitFor();
                    } catch (Exception e2) {
                        e = e2;
                        Log.e(TAG, "Save Exception " + e);
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (IOException e3) {
                                e = e3;
                                sb = new StringBuilder("Close Fail ");
                                sb.append(e);
                                string = sb.toString();
                                Log.e(TAG, string);
                                return encodedDCKLog;
                            }
                        }
                        return encodedDCKLog;
                    }
                    try {
                        bufferedWriter.close();
                        return encodedDCKLog;
                    } catch (IOException e4) {
                        e = e4;
                        sb = new StringBuilder("Close Fail ");
                        sb.append(e);
                        string = sb.toString();
                        Log.e(TAG, string);
                        return encodedDCKLog;
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter2 = bufferedWriter;
                    if (bufferedWriter2 != null) {
                        try {
                            bufferedWriter2.close();
                        } catch (IOException e5) {
                            Log.e(TAG, string + e5);
                        }
                    }
                    throw th;
                }
            } catch (Exception e6) {
                bufferedWriter = null;
                e = e6;
            } catch (Throwable th2) {
                th = th2;
                if (bufferedWriter2 != null) {
                }
                throw th;
            }
        }
        return encodedDCKLog;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0091 A[PHI: r2 r4 r8
      0x0091: PHI (r2v5 java.io.FileOutputStream) = (r2v2 java.io.FileOutputStream), (r2v8 java.io.FileOutputStream) binds: [B:36:0x00ba, B:28:0x008f] A[DONT_GENERATE, DONT_INLINE]
      0x0091: PHI (r4v7 java.lang.Process) = (r4v5 java.lang.Process), (r4v9 java.lang.Process) binds: [B:36:0x00ba, B:28:0x008f] A[DONT_GENERATE, DONT_INLINE]
      0x0091: PHI (r8v5 java.io.BufferedWriter) = (r8v3 java.io.BufferedWriter), (r8v8 java.io.BufferedWriter) binds: [B:36:0x00ba, B:28:0x008f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00de A[Catch: IOException -> 0x00da, TRY_LEAVE, TryCatch #1 {IOException -> 0x00da, blocks: (B:40:0x00d3, B:44:0x00de), top: B:85:0x00d3 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00a6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.samsung.android.service.SemService.ISemService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void stop_SLOG() throws Throwable {
        FileOutputStream fileOutputStream;
        Process processExec;
        StringBuilder sb;
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "Stop_SLOG Permission Error");
            return;
        }
        String string = this.secureBuffer.toString();
        BufferedWriter bufferedWriter = null;
        bufferedWriter = null;
        bufferedWriter = null;
        bufferedWriter = null;
        bufferedWriter = null;
        Process process = null;
        try {
            try {
                if (string != null) {
                    String sLogPath = getSLogPath("/data/log/sse1", "/data/log/sse2");
                    String strEncData = encData(string);
                    Log.d(TAG, "DP : " + sLogPath);
                    fileOutputStream = new FileOutputStream(sLogPath, true);
                    try {
                        BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
                        if (strEncData != null) {
                            try {
                                bufferedWriter2.write(strEncData);
                            } catch (Exception e) {
                                e = e;
                                bufferedWriter = bufferedWriter2;
                                processExec = null;
                                Log.e(TAG, "Save Exception " + e);
                                if (bufferedWriter != null) {
                                }
                                if (processExec != null) {
                                }
                                try {
                                    Log.i(TAG, "Buffer Init");
                                    StringBuffer stringBuffer = this.secureBuffer;
                                    stringBuffer.delete(0, stringBuffer.length());
                                    this.secureBuffer.setLength(0);
                                    if (fileOutputStream != null) {
                                    }
                                    if (bufferedWriter != null) {
                                    }
                                } catch (Throwable th) {
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.flush();
                                            fileOutputStream.close();
                                        } catch (IOException e2) {
                                            Log.e(TAG, "Close Exception " + e2);
                                            throw th;
                                        }
                                    }
                                    if (bufferedWriter != null) {
                                        bufferedWriter.flush();
                                        bufferedWriter.close();
                                    }
                                    throw th;
                                }
                            }
                        }
                        bufferedWriter2.write(ShaderAssembler.NEWLINE);
                        bufferedWriter2.flush();
                        bufferedWriter2.close();
                        Runtime runtime = Runtime.getRuntime();
                        processExec = runtime.exec("chmod a+r -R /data/log/sse1");
                        try {
                            try {
                                processExec.waitFor();
                                processExec = runtime.exec("chmod a+r -R /data/log/sse2");
                                processExec.waitFor();
                            } catch (Exception e3) {
                                e = e3;
                                Log.e(TAG, "Save Exception " + e);
                                if (bufferedWriter != null) {
                                    try {
                                        bufferedWriter.close();
                                    } catch (IOException e4) {
                                        Log.e(TAG, "Close Fail " + e4);
                                    }
                                }
                                if (processExec != null) {
                                }
                                Log.i(TAG, "Buffer Init");
                                StringBuffer stringBuffer2 = this.secureBuffer;
                                stringBuffer2.delete(0, stringBuffer2.length());
                                this.secureBuffer.setLength(0);
                                if (fileOutputStream != null) {
                                }
                                if (bufferedWriter != null) {
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            process = processExec;
                            if (process != null) {
                            }
                            throw th;
                        }
                    } catch (Exception e5) {
                        e = e5;
                        processExec = null;
                    }
                } else {
                    Log.e(TAG, "LD Null Error");
                    fileOutputStream = null;
                    processExec = null;
                }
            } catch (Throwable th3) {
                th = th3;
                if (process != null) {
                    process.destroy();
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            fileOutputStream = null;
            processExec = null;
        }
        if (processExec != null) {
            processExec.destroy();
        }
        try {
            Log.i(TAG, "Buffer Init");
            StringBuffer stringBuffer22 = this.secureBuffer;
            stringBuffer22.delete(0, stringBuffer22.length());
            this.secureBuffer.setLength(0);
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e7) {
                    e = e7;
                    sb = new StringBuilder("Close Exception ");
                    sb.append(e);
                    Log.e(TAG, sb.toString());
                    return;
                }
            }
            if (bufferedWriter != null) {
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        } catch (Error unused) {
            Log.e(TAG, "Buffer Error");
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e8) {
                    e = e8;
                    sb = new StringBuilder("Close Exception ");
                    sb.append(e);
                    Log.e(TAG, sb.toString());
                    return;
                }
            }
            if (bufferedWriter != null) {
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        } catch (Exception unused2) {
            Log.e(TAG, "Buffer Exception");
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e9) {
                    e = e9;
                    sb = new StringBuilder("Close Exception ");
                    sb.append(e);
                    Log.e(TAG, sb.toString());
                    return;
                }
            }
            if (bufferedWriter != null) {
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00e9 A[Catch: IOException -> 0x00e5, TRY_LEAVE, TryCatch #2 {IOException -> 0x00e5, blocks: (B:48:0x00de, B:52:0x00e9), top: B:56:0x00de }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00de A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.samsung.android.service.SemService.ISemService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void agent_SLOG(String str) throws Throwable {
        BufferedWriter bufferedWriter;
        StringBuilder sb;
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MJavaPkgList)) {
            Log.e(TAG, "Agent_SLOG Permission Error");
            return;
        }
        try {
            byte[] bArr = new byte[300];
            byte[] bArr2 = new byte[300];
            int i = getpkSecurity(bArr);
            int i2 = getpkSKMS(bArr2);
            this.bytePublicKeySecurityLen = i;
            this.bytePublicKeySKMSLen = i2;
            this.bytePublicKeyDataSecurity = Arrays.copyOf(bArr, i);
            this.bytePublicKeyDataSKMS = Arrays.copyOf(bArr2, this.bytePublicKeySKMSLen);
        } catch (Exception e) {
            Log.e(TAG, "Get SL Key ex " + e);
        }
        String sLogPath = getSLogPath("/data/log/sse5", "/data/log/sse6");
        FileOutputStream fileOutputStream = null;
        try {
            String strEncData = encData(str);
            Log.d(TAG, "DP : " + sLogPath);
            FileOutputStream fileOutputStream2 = new FileOutputStream(sLogPath, true);
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream2, StandardCharsets.UTF_8));
                if (strEncData != null) {
                    try {
                        bufferedWriter.write(strEncData);
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        try {
                            Log.e(TAG, "ASLog ex " + e);
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (IOException e3) {
                                    e = e3;
                                    sb = new StringBuilder("ASLog Ex ");
                                    sb.append(e);
                                    Log.e(TAG, sb.toString());
                                }
                            }
                            if (bufferedWriter != null) {
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (IOException e4) {
                                    Log.e(TAG, "ASLog Ex " + e4);
                                    throw th;
                                }
                            }
                            if (bufferedWriter != null) {
                                bufferedWriter.flush();
                                bufferedWriter.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                        }
                        if (bufferedWriter != null) {
                        }
                        throw th;
                    }
                }
                bufferedWriter.write(ShaderAssembler.NEWLINE);
                bufferedWriter.flush();
                bufferedWriter.close();
                try {
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                } catch (IOException e5) {
                    e = e5;
                    sb = new StringBuilder("ASLog Ex ");
                    sb.append(e);
                    Log.e(TAG, sb.toString());
                }
            } catch (Exception e6) {
                e = e6;
                bufferedWriter = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter = null;
            }
        } catch (Exception e7) {
            e = e7;
            bufferedWriter = null;
        } catch (Throwable th4) {
            th = th4;
            bufferedWriter = null;
        }
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
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) throws Throwable {
        String str;
        String str2;
        String str3;
        String fileBytes;
        String fileBytes2;
        String strEncData;
        String strEncData2;
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
                String strAppCheckLog = appCheckLog();
                String _esea = get_ESEA();
                String str4 = get_DPDLog();
                String strDCKLog = DCKLog();
                str2 = TAG;
                try {
                    if (this.supportEsek) {
                        str3 = packageName;
                        fileBytes2 = SemServiceTools.readFileBytes(Paths.get("/efs/sec_efs/esek/esek_cert.dat", new String[0]));
                        fileBytes = SemServiceTools.readFileBytes(Paths.get("/efs/sec_efs/esek/scp11_cert.dat", new String[0]));
                    } else {
                        str3 = packageName;
                        fileBytes = null;
                        fileBytes2 = null;
                    }
                    start_SLOG();
                    String strEncData3 = encData(sCRSActivationList);
                    String strEncData4 = encData(accessRule);
                    String strEncData5 = encData(strAppCheckLog);
                    String strEncData6 = encData(cPLC14mode);
                    String strEncData7 = encData(_esea);
                    if (this.supportEsek) {
                        strEncData2 = fileBytes2 != null ? encData(fileBytes2) : null;
                        strEncData = fileBytes != null ? encData(fileBytes) : null;
                    } else {
                        strEncData = null;
                        strEncData2 = null;
                    }
                    String str5 = strEncData;
                    this.secureBuffer.append(sCRSActivationList + ShaderAssembler.NEWLINE);
                    this.secureBuffer.append(strEncData4 + ShaderAssembler.NEWLINE);
                    this.secureBuffer.append(strAppCheckLog + ShaderAssembler.NEWLINE);
                    this.secureBuffer.append(cPLC14mode + ShaderAssembler.NEWLINE);
                    this.secureBuffer.append(_esea + ShaderAssembler.NEWLINE);
                    stop_SLOG();
                    stringBuffer.append(ShaderAssembler.NEWLINE + strEncData6);
                    stringBuffer.append(ShaderAssembler.NEWLINE + strEncData3);
                    stringBuffer.append(ShaderAssembler.NEWLINE + strEncData4);
                    stringBuffer.append(ShaderAssembler.NEWLINE + strEncData5);
                    stringBuffer.append(ShaderAssembler.NEWLINE + strEncData7);
                    stringBuffer.append("\nDPD : " + str4);
                    if (strDCKLog != null) {
                        stringBuffer.append("\nSEMSVC[4]");
                        stringBuffer.append(ShaderAssembler.NEWLINE + strDCKLog);
                    }
                    if (this.supportEsek) {
                        if (strEncData2 != null) {
                            stringBuffer.append("\nESEK_Cert : " + strEncData2);
                        }
                        if (str5 != null) {
                            stringBuffer.append("\nSCP11 Cert : " + str5);
                        }
                    }
                    String str6 = str3;
                    this.mSemServiceAccessControl.removeAllowedPackage(str6, SemServiceAccessControl.PackageList.MJavaPkgList);
                    this.mSemServiceAccessControl.removeAllowedPackage(str6, SemServiceAccessControl.PackageList.MFactoryPkgList);
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
            int iOpenSpi = openSpi(i);
            if (iOpenSpi != 0) {
                releaseSpiUsage();
                return iOpenSpi;
            }
            if (i == 0) {
                startSPITimer();
            }
            return iOpenSpi;
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
        int iSynchronizedCloseSpi = synchronizedCloseSpi(i);
        releaseSpiUsage();
        return iSynchronizedCloseSpi;
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
        int iSendData = sendData(bArr, i, bArr2, i2);
        if (i2 == 0) {
            startSPITimer();
        }
        return iSendData;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int check_SeState(byte[] bArr, byte[] bArr2) {
        int iCheckSeStatus;
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
            iCheckSeStatus = checkSeStatus(bArr, bArr2);
        } catch (Exception e) {
            Log.e(TAG, "Failed to check_SeState, " + e.toString());
            iCheckSeStatus = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef check_SeState, " + e2.toString());
            iCheckSeStatus = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield check_SeState, " + e3.toString());
            iCheckSeStatus = -3;
        }
        releaseSpiUsage();
        return iCheckSeStatus;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int start_request_credentials(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        int iStartRequestCredentials;
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
                        int iStartRequestCredentialsList = startRequestCredentialsList(bArr, bArr2, str, scpkmTeeSigData, scpkmTeeListData, bArr3);
                        releaseSpiUsage();
                        return iStartRequestCredentialsList;
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
            iStartRequestCredentials = startRequestCredentials(bArr, bArr2, str, bArr3);
        } catch (Exception e4) {
            Log.e(TAG, "Failed to start_request_credentials, " + e4.toString());
            iStartRequestCredentials = -90;
        } catch (NoClassDefFoundError e5) {
            Log.e(TAG, "NoClassDef start_request_credentials, " + e5.toString());
            iStartRequestCredentials = -2;
        } catch (UnsatisfiedLinkError e6) {
            Log.e(TAG, "Unsatisfield start_request_credentials, " + e6.toString());
            iStartRequestCredentials = -3;
        }
        releaseSpiUsage();
        return iStartRequestCredentials;
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
        int iGrdmGetSession;
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
                        iGrdmGetSession = -11;
                    } else {
                        iGrdmGetSession = grdmGetSession();
                        if (iGrdmGetSession == 1) {
                            this.mIsOpened = true;
                        }
                    }
                } catch (UnsatisfiedLinkError e) {
                    Log.e(TAG, "Unsatisfield start_request_grdm, " + e.toString());
                    iGrdmGetSession = -3;
                }
            } catch (Exception e2) {
                Log.e(TAG, "Failed to start_request_grdm, " + e2.toString());
                iGrdmGetSession = -90;
            }
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NoClassDef start_request_grdm, " + e3.toString());
            iGrdmGetSession = -2;
        }
        return iGrdmGetSession;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_request_key(int i, byte[] bArr) {
        int iGrdmRequestKey;
        Log.i(TAG, "Start grdm_request_key");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                iGrdmRequestKey = grdmRequestKey(i, bArr);
            } catch (NoClassDefFoundError e) {
                Log.e(TAG, "NoClassDef grdm_request_key, " + e.toString());
                iGrdmRequestKey = -2;
            }
        } catch (Exception e2) {
            Log.e(TAG, "Failed to grdm_request_key, " + e2.toString());
            iGrdmRequestKey = -90;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield grdm_request_key, " + e3.toString());
            iGrdmRequestKey = -3;
        }
        return iGrdmRequestKey;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_release_session() {
        int iGrdmReleaseSession;
        Log.i(TAG, "Start grdm_release_session");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            if (this.mIsOpened) {
                iGrdmReleaseSession = grdmReleaseSession();
                this.mIsOpened = false;
            } else {
                iGrdmReleaseSession = -12;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to grdm_release_session, " + e.toString());
            iGrdmReleaseSession = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NoClassDef grdm_release_session, " + e2.toString());
            iGrdmReleaseSession = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield grdm_release_session, " + e3.toString());
            iGrdmReleaseSession = -3;
        }
        return iGrdmReleaseSession;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_get_attes_cert(int i, byte[] bArr) {
        int iGrdmGetAttesCert;
        Log.i(TAG, "Start grdm_get_attes_cert");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                iGrdmGetAttesCert = grdmGetAttesCert(i, bArr);
            } catch (NoClassDefFoundError e) {
                Log.e(TAG, "NoClassDef grdm_get_attes_cert, " + e.toString());
                iGrdmGetAttesCert = -2;
            }
        } catch (Exception e2) {
            Log.e(TAG, "Failed to grdm_get_attes_cert, " + e2.toString());
            iGrdmGetAttesCert = -90;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield grdm_get_attes_cert, " + e3.toString());
            iGrdmGetAttesCert = -3;
        }
        return iGrdmGetAttesCert;
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
                    int iGrdmCheckRestrictedMode = grdmCheckRestrictedMode(bArr);
                    if (iGrdmCheckRestrictedMode <= 0) {
                        Log.e(TAG, "no data to be returned");
                        return null;
                    }
                    if (iGrdmCheckRestrictedMode < 1000) {
                        String str = new String(Arrays.copyOf(bArr, iGrdmCheckRestrictedMode), StandardCharsets.UTF_8);
                        Log.i(TAG, "grdm_check_restricted_mode Return : ".concat(str));
                        return str;
                    }
                    Log.e(TAG, "data overflow");
                    return null;
                } catch (UnsatisfiedLinkError e) {
                    Log.e(TAG, "Unsatisfield grdm_check_restricted_mode, " + e.toString());
                    return null;
                }
            } catch (Exception e2) {
                Log.e(TAG, "Failed to grdm_check_restricted_mode, " + e2.toString());
                return null;
            }
        } catch (NoClassDefFoundError e3) {
            Log.e(TAG, "NoClassDef grdm_check_restricted_mode, " + e3.toString());
            return null;
        }
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public synchronized int grdm_Check_Status() {
        int iGrdmCheckStatusInfo;
        Log.i(TAG, "Start grdm_Check_Status");
        if (!isGRDMSupported()) {
            return -10;
        }
        if (!this.mSemServiceAccessControl.hasAccessPermission(SemServiceAccessControl.PackageList.MGrdmPkgList)) {
            return -91;
        }
        try {
            try {
                iGrdmCheckStatusInfo = grdmCheckStatusInfo();
            } catch (NoClassDefFoundError e) {
                Log.e(TAG, "NoClassDef grdm_get_attes_cert, " + e.toString());
                iGrdmCheckStatusInfo = -2;
            }
        } catch (Exception e2) {
            Log.e(TAG, "Failed to grdm_get_attes_cert, " + e2.toString());
            iGrdmCheckStatusInfo = -90;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Unsatisfield grdm_get_attes_cert, " + e3.toString());
            iGrdmCheckStatusInfo = -3;
        }
        return iGrdmCheckStatusInfo;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int openSpiDriver() {
        int iOpenDriverSpi;
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
            iOpenDriverSpi = openDriverSpi();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            iOpenDriverSpi = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            iOpenDriverSpi = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            iOpenDriverSpi = -3;
        }
        releaseSpiUsage();
        return iOpenDriverSpi;
    }

    @Override // com.samsung.android.service.SemService.ISemService
    public int closeSpiDriver() {
        int iCloseDriverSpi;
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
            iCloseDriverSpi = closeDriverSpi();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            iCloseDriverSpi = -90;
        } catch (NoClassDefFoundError e2) {
            Log.e(TAG, "NCDF Exception : " + e2);
            iCloseDriverSpi = -2;
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "USLE Exception : " + e3);
            iCloseDriverSpi = -3;
        }
        releaseSpiUsage();
        return iCloseDriverSpi;
    }
}
