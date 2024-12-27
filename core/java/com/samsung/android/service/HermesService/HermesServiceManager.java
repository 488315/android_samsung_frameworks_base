package com.samsung.android.service.HermesService;

import android.content.Context;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.samsung.android.sepunion.SemUnionManager;
import com.samsung.android.sepunion.UnionConstants;

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
            SemUnionManager um =
                    (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
            IBinder b = um.getSemSystemService(UnionConstants.SERVICE_HERMES);
            this.mService = IHermesService.Stub.asInterface(b);
        }
        return this.mService;
    }

    public HermesServiceManager(Context context) {
        this.mContext = context;
        if (bindHermesService() == null) {
            Log.i(TAG, this.mContext.getPackageName() + " It Can't connects to HermesService.");
        } else {
            Log.i(TAG, this.mContext.getPackageName() + " It connects to HermesService.");
        }
        PowerManager pm = (PowerManager) this.mContext.getSystemService(PowerManager.class);
        this.mWakeLock = pm.newWakeLock(1, TAG);
    }

    public byte[] hermesSelftest(String cmd) {
        byte[] ret;
        Log.i(TAG, "hermesSelftest() is called.");
        if (this.mWakeLock != null) {
            if (!this.mWakeLock.isHeld()) {
                Log.i(TAG, "hermesSelftest acquire wakelock.");
                this.mWakeLock.acquire();
            } else {
                Log.i(TAG, "hermesSelftest already acquried wakelock.");
            }
        } else {
            Log.e(TAG, "hermesSelftest start mWakeLock is null.");
        }
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                ret = mBinder.hermesSelftest2(cmd);
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to hermesSelftest service.");
                npe.printStackTrace();
                ret = null;
            } catch (Exception e) {
                e.printStackTrace();
                ret = null;
            }
        } else {
            Log.e(TAG, "bindHermesService is null");
            ret = null;
        }
        if (this.mWakeLock != null) {
            if (this.mWakeLock.isHeld()) {
                Log.i(TAG, "hermesSelftest release wakelock.");
                this.mWakeLock.release();
            } else {
                Log.i(TAG, "hermesSelftest already released wakelock.");
            }
        } else {
            Log.e(TAG, "hermesSelftest end mWakeLock is null.");
        }
        return ret;
    }

    public byte[] hermesSelftest() {
        byte[] ret;
        Log.i(TAG, "hermesSelftest() is called.");
        if (this.mWakeLock != null) {
            if (!this.mWakeLock.isHeld()) {
                Log.i(TAG, "hermesSelftest acquire wakelock.");
                this.mWakeLock.acquire();
            } else {
                Log.i(TAG, "hermesSelftest already acquried wakelock.");
            }
        } else {
            Log.e(TAG, "hermesSelftest start mWakeLock is null.");
        }
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                ret = mBinder.hermesSelftest();
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to hermesSelftest service.");
                npe.printStackTrace();
                ret = null;
            } catch (Exception e) {
                e.printStackTrace();
                ret = null;
            }
        } else {
            Log.e(TAG, "bindHermesService is null");
            ret = null;
        }
        if (this.mWakeLock != null) {
            if (this.mWakeLock.isHeld()) {
                Log.i(TAG, "hermesSelftest release wakelock.");
                this.mWakeLock.release();
            } else {
                Log.i(TAG, "hermesSelftest already released wakelock.");
            }
        } else {
            Log.e(TAG, "hermesSelftest end mWakeLock is null.");
        }
        return ret;
    }

    public int hermesProvisioning() {
        Log.i(TAG, "hermesProvisioning() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesProvisioning();
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return -10000;
            } catch (Exception e) {
                e.printStackTrace();
                return -10000;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return -10000;
    }

    public int hermesVerifyProvisioning() {
        Log.i(TAG, "hermesVerifyProvisioning() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesVerifyProvisioning();
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return -10000;
            } catch (Exception e) {
                e.printStackTrace();
                return -10000;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return -10000;
    }

    public byte[] hermesGetSecureHWInfo() {
        Log.i(TAG, "hermesGetSecureHWInfo() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesGetSecureHWInfo();
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] hermesUpdateCryptoFW() {
        Log.i(TAG, "hermesUpdateCryptoFW() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesUpdateCryptoFW();
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] hermesUpdateApplet() {
        Log.i(TAG, "hermesUpdateApplet() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesUpdateApplet();
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public int open() {
        Log.i(TAG, "open() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesSecureHwPowerOn();
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return -10000;
            } catch (Exception e) {
                e.printStackTrace();
                return -10000;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return -10000;
    }

    public int close() {
        Log.i(TAG, "close() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesSecureHwPowerOff();
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return -10000;
            } catch (Exception e) {
                e.printStackTrace();
                return -10000;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return -10000;
    }

    public byte[] send(byte[] capdu) {
        Log.i(TAG, "send() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesSendApdu(capdu);
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] cosPatchTest(byte[] script) {
        Log.i(TAG, "cosPatchTest() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesCosPatchTest(script);
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }

    public byte[] getSeId() {
        Log.i(TAG, "getSeId() is called.");
        IHermesService mBinder = bindHermesService();
        if (mBinder != null) {
            try {
                return mBinder.hermesGetSeId();
            } catch (NullPointerException npe) {
                Log.e(TAG, "Failed to connect service.");
                npe.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "bindHermesService is null");
        return null;
    }
}
