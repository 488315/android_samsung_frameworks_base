package com.samsung.android.service.HermesService;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import com.samsung.android.sepunion.SemUnionManager;
import com.samsung.android.sepunion.UnionConstants;
import com.samsung.android.service.HermesService.IHermesService;

/* loaded from: classes6.dex */
public final class HermesServiceManager {
    public static final int ERR_SERVICE_ERROR = -10000;
    public static final int NO_ERROR = 0;
    private static final String TAG = "HERMES#Manager";
    private final Context mContext;
    private IHermesService mService;
    private PowerManager.WakeLock mWakeLock;

    private IHermesService bindHermesService() {
        if (this.mService == null) {
            Log.i(TAG, "bindHermesService() is called");
            this.mService = IHermesService.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_HERMES));
        }
        return this.mService;
    }

    public HermesServiceManager(Context context) {
        this.mContext = context;
        if (bindHermesService() == null) {
            Log.i(TAG, context.getPackageName() + " It Can't connects to HermesService.");
        } else {
            Log.i(TAG, context.getPackageName() + " It connects to HermesService.");
        }
        this.mWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, TAG);
    }

    public byte[] hermesSelftest(String str) {
        Log.i(TAG, "hermesSelftest() is called.");
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            if (!wakeLock.isHeld()) {
                Log.i(TAG, "hermesSelftest acquire wakelock.");
                this.mWakeLock.acquire();
            } else {
                Log.i(TAG, "hermesSelftest already acquried wakelock.");
            }
        } else {
            Log.e(TAG, "hermesSelftest start mWakeLock is null.");
        }
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        byte[] bArrHermesSelftest2 = null;
        if (iHermesServiceBindHermesService != null) {
            try {
                bArrHermesSelftest2 = iHermesServiceBindHermesService.hermesSelftest2(str);
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to hermesSelftest service.");
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Log.e(TAG, "bindHermesService is null");
        }
        PowerManager.WakeLock wakeLock2 = this.mWakeLock;
        if (wakeLock2 != null) {
            if (wakeLock2.isHeld()) {
                Log.i(TAG, "hermesSelftest release wakelock.");
                this.mWakeLock.release();
            } else {
                Log.i(TAG, "hermesSelftest already released wakelock.");
            }
        } else {
            Log.e(TAG, "hermesSelftest end mWakeLock is null.");
        }
        return bArrHermesSelftest2;
    }

    public byte[] hermesSelftest() {
        Log.i(TAG, "hermesSelftest() is called.");
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            if (!wakeLock.isHeld()) {
                Log.i(TAG, "hermesSelftest acquire wakelock.");
                this.mWakeLock.acquire();
            } else {
                Log.i(TAG, "hermesSelftest already acquried wakelock.");
            }
        } else {
            Log.e(TAG, "hermesSelftest start mWakeLock is null.");
        }
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        byte[] bArrHermesSelftest = null;
        if (iHermesServiceBindHermesService != null) {
            try {
                bArrHermesSelftest = iHermesServiceBindHermesService.hermesSelftest();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to hermesSelftest service.");
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Log.e(TAG, "bindHermesService is null");
        }
        PowerManager.WakeLock wakeLock2 = this.mWakeLock;
        if (wakeLock2 != null) {
            if (wakeLock2.isHeld()) {
                Log.i(TAG, "hermesSelftest release wakelock.");
                this.mWakeLock.release();
            } else {
                Log.i(TAG, "hermesSelftest already released wakelock.");
            }
        } else {
            Log.e(TAG, "hermesSelftest end mWakeLock is null.");
        }
        return bArrHermesSelftest;
    }

    public int hermesProvisioning() {
        Log.i(TAG, "hermesProvisioning() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesProvisioning();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return -10000;
            } catch (Exception e2) {
                e2.printStackTrace();
                return -10000;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return -10000;
    }

    public int hermesVerifyProvisioning() {
        Log.i(TAG, "hermesVerifyProvisioning() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesVerifyProvisioning();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return -10000;
            } catch (Exception e2) {
                e2.printStackTrace();
                return -10000;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return -10000;
    }

    public byte[] hermesGetSecureHWInfo() {
        Log.i(TAG, "hermesGetSecureHWInfo() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesGetSecureHWInfo();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] hermesUpdateCryptoFW() {
        Log.i(TAG, "hermesUpdateCryptoFW() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesUpdateCryptoFW();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] hermesUpdateApplet() {
        Log.i(TAG, "hermesUpdateApplet() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesUpdateApplet();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public int open() {
        Log.i(TAG, "open() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesSecureHwPowerOn();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return -10000;
            } catch (Exception e2) {
                e2.printStackTrace();
                return -10000;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return -10000;
    }

    public int close() {
        Log.i(TAG, "close() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesSecureHwPowerOff();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return -10000;
            } catch (Exception e2) {
                e2.printStackTrace();
                return -10000;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return -10000;
    }

    public byte[] send(byte[] bArr) {
        Log.i(TAG, "send() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesSendApdu(bArr);
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] cosPatchTest(byte[] bArr) {
        Log.i(TAG, "cosPatchTest() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesCosPatchTest(bArr);
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] getSeId() {
        Log.i(TAG, "getSeId() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesGetSeId();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] cosUnitTest(String str) {
        Log.i(TAG, "cosUnitTest() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesCosUnitTest(str);
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] hermesGetAppletVersion() {
        Log.i(TAG, "getAppletVersion() is called.");
        IHermesService iHermesServiceBindHermesService = bindHermesService();
        if (iHermesServiceBindHermesService != null) {
            try {
                return iHermesServiceBindHermesService.hermesGetAppletVersion();
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to connect service.");
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }
}
