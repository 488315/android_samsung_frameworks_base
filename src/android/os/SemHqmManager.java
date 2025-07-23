package android.os;

/* loaded from: classes3.dex */
public class SemHqmManager {
    private static final String TAG = "SemHqmManager";
    Handler mHandler;
    ISemHqmManager mService;
    private static final boolean DEBUG = "eng".equals(Build.TYPE);
    private static final Object BDlock = new Object();
    private static final Object BDlock_sys = new Object();

    public SemHqmManager(ISemHqmManager iSemHqmManager, Handler handler) {
        this.mService = iSemHqmManager;
        this.mHandler = handler;
    }

    public boolean sendHWParamServer(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        boolean sendHWParamServer;
        if (this.mService == null) {
            return false;
        }
        synchronized (BDlock) {
            try {
                try {
                    sendHWParamServer = this.mService.sendHWParamServer(i, str, str2, str3, str4, str5, str6, str7);
                } catch (Exception e) {
                    printExceptionTrace(e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sendHWParamServer;
    }

    public boolean sendHWParamToHQM(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        boolean sendHWParamToHQM;
        if (this.mService == null) {
            return false;
        }
        synchronized (BDlock) {
            try {
                try {
                    sendHWParamToHQM = this.mService.sendHWParamToHQM(i, str, str2, str3, str4, str5, str6, str7, str8);
                } catch (Exception e) {
                    printExceptionTrace(e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sendHWParamToHQM;
    }

    public boolean sendHWParamToHQMwithAppId(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        boolean sendHWParamToHQMwithAppId;
        if (this.mService == null) {
            return false;
        }
        synchronized (BDlock) {
            try {
                try {
                    sendHWParamToHQMwithAppId = this.mService.sendHWParamToHQMwithAppId(i, str, str2, str3, str4, str5, str6, str7, str8, str9);
                } catch (Exception e) {
                    printExceptionTrace(e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sendHWParamToHQMwithAppId;
    }

    public boolean sendHWParamToHQMwithFile(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        boolean sendHWParamToHQMwithFile;
        if (this.mService == null) {
            return false;
        }
        synchronized (BDlock) {
            try {
                try {
                    sendHWParamToHQMwithFile = this.mService.sendHWParamToHQMwithFile(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10);
                } catch (Exception e) {
                    printExceptionTrace(e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sendHWParamToHQMwithFile;
    }

    public void sendSystemInfoToHQM(int i, String str, String str2) {
        if (this.mService == null) {
            return;
        }
        synchronized (BDlock_sys) {
            try {
                this.mService.sendSystemInfoToHQM(i, str, str2);
            } catch (Exception e) {
                printExceptionTrace(e);
            }
        }
    }

    public boolean getHqmEnable() {
        boolean hqmEnable;
        if (this.mService == null) {
            return false;
        }
        synchronized (BDlock) {
            try {
                try {
                    hqmEnable = this.mService.getHqmEnable();
                } catch (Exception e) {
                    printExceptionTrace(e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return hqmEnable;
    }

    public boolean getDVServerEnable() {
        boolean dVServerEnable;
        if (this.mService == null) {
            return false;
        }
        synchronized (BDlock) {
            try {
                try {
                    dVServerEnable = this.mService.getDVServerEnable();
                } catch (Exception e) {
                    printExceptionTrace(e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return dVServerEnable;
    }

    public boolean getCFServerEnable() {
        boolean cFServerEnable;
        if (this.mService == null) {
            return false;
        }
        synchronized (BDlock) {
            try {
                try {
                    cFServerEnable = this.mService.getCFServerEnable();
                } catch (Exception e) {
                    printExceptionTrace(e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return cFServerEnable;
    }

    private static void printExceptionTrace(Exception exc) {
        if (DEBUG) {
            exc.printStackTrace();
        }
    }
}
