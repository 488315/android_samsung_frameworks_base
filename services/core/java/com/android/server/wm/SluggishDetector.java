package com.android.server.wm;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.p005os.IInstalld;
import android.util.PerfLog;
import android.util.Slog;
import com.android.server.ServiceThread;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupManagerConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes3.dex */
public abstract class SluggishDetector {
    public static long Before_Store_time = -1;
    public static boolean ENABLE = true;
    public static final String TAG = "SluggishDetector";
    public static Context mContext;

    static {
        String simpleName = SluggishDetector.class.getSimpleName();
        if (new File("/proc/kperfmon").isFile()) {
            ENABLE = true;
        } else {
            Slog.d(simpleName, " [SD] kperfmon nonexist");
            ENABLE = false;
        }
        if (ENABLE) {
            SluggishDetectorHandler.init();
            PeriodicStoreOLOG.init();
            try {
                PeriodicStoreOLOG.step();
            } catch (Exception unused) {
            }
        }
    }

    public final class SluggishDetectorHandler extends Handler {
        public static final String TAG = SluggishDetector.TAG + "_" + SluggishDetectorHandler.class.getSimpleName();
        public static ServiceThread sHandlerThread = null;
        public static SluggishDetectorHandler sHandler = null;
        public static boolean ENABLE_HANDLER = false;

        public static void init() {
            if (SluggishDetector.ENABLE) {
                ServiceThread serviceThread = new ServiceThread(SluggishDetectorHandler.class.getSimpleName(), 10, true);
                sHandlerThread = serviceThread;
                serviceThread.start();
                sHandler = new SluggishDetectorHandler(sHandlerThread.getLooper());
                setEnableWithDelay();
            }
        }

        public static boolean check() {
            return (sHandlerThread == null || sHandler == null) ? false : true;
        }

        public static void sendMessageToHandlerDelayed(int i, long j) {
            if (SluggishDetector.ENABLE) {
                if (!check()) {
                    init();
                }
                sHandler.sendEmptyMessageDelayed(i, j);
            }
        }

        public static void sendDataToHandler(int i, Object obj) {
            if (SluggishDetector.ENABLE && ENABLE_HANDLER && obj != null) {
                if (!check()) {
                    init();
                }
                sHandler.sendMessage(sHandler.obtainMessage(i, obj));
            }
        }

        public SluggishDetectorHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message == null) {
                return;
            }
            try {
                int i = message.what;
                if (i == 1) {
                    Slog.e(TAG, "case MSG_ENABLE_HANDLER");
                    setEnable();
                } else if (i == 21) {
                    storeOLOGDay();
                } else if (i != 22) {
                    switch (i) {
                        case 13:
                            Object obj = message.obj;
                            if (obj != null) {
                                LockContentionInfo lockContentionInfo = new LockContentionInfo(obj.toString());
                                if (lockContentionInfo.checkTime()) {
                                    lockContentionInfo.updateLCD();
                                    sendToInfoForLockContention(lockContentionInfo);
                                    break;
                                }
                            }
                            break;
                    }
                } else {
                    storeOLOGNow();
                }
            } catch (Exception unused) {
            }
        }

        public final void sendToInfoForLockContention(LockContentionInfo lockContentionInfo) {
            lockContentionInfo.LockInfo_Logging();
        }

        public final void storeOLOGDay() {
            removeMessages(21);
            PeriodicStoreOLOG.storeOLOG("/data/log/remaining_olog");
        }

        public final void storeOLOGNow() {
            removeMessages(22);
            PeriodicStoreOLOG.storeOLOG("/data/log/remaining_olog_now");
        }

        public static void setEnable() {
            ENABLE_HANDLER = true;
        }

        public static void setEnableWithDelay() {
            sendMessageToHandlerDelayed(1, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
        }
    }

    public abstract class PeriodicStoreOLOG {
        public static final String TAG = SluggishDetector.TAG + "_" + PeriodicStoreOLOG.class.getSimpleName();
        public static boolean STORE_FLAG_FOR_ADD = true;
        public static long Origin_OlogLength = 0;
        public static long Diff_OlogLength = 0;

        public static boolean check() {
            return true;
        }

        public static void init() {
            String str = SluggishDetector.TAG;
        }

        public static void step() {
            if (!check()) {
                init();
            }
            makestoreOLOG();
        }

        /* JADX WARN: Removed duplicated region for block: B:65:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:71:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:72:0x0082 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static boolean storeOLOG(String str) {
            FileOutputStream fileOutputStream;
            FileInputStream fileInputStream;
            Throwable th;
            long currentTimeMillis = System.currentTimeMillis();
            System.nanoTime();
            if (SluggishDetector.Before_Store_time > 0 && currentTimeMillis - SluggishDetector.Before_Store_time < BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS) {
                return false;
            }
            byte[] bArr = new byte[IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES];
            System.currentTimeMillis();
            System.nanoTime();
            File file = new File("/proc/kperfmon");
            File file2 = new File(str);
            FileInputStream fileInputStream2 = null;
            try {
                if (file.isFile()) {
                    fileInputStream = new FileInputStream(file);
                    try {
                        fileOutputStream = new FileOutputStream(file2);
                        while (true) {
                            try {
                                int read = fileInputStream.read(bArr);
                                if (read <= 0) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            } catch (FileNotFoundException unused) {
                                fileInputStream2 = fileInputStream;
                                if (fileInputStream2 != null) {
                                    try {
                                        fileInputStream2.close();
                                    } catch (IOException unused2) {
                                    }
                                }
                                if (fileOutputStream == null) {
                                    return true;
                                }
                                fileOutputStream.close();
                                return true;
                            } catch (IOException unused3) {
                                fileInputStream2 = fileInputStream;
                                if (fileInputStream2 != null) {
                                    try {
                                        fileInputStream2.close();
                                    } catch (IOException unused4) {
                                    }
                                }
                                if (fileOutputStream == null) {
                                    return true;
                                }
                                fileOutputStream.close();
                                return true;
                            } catch (NumberFormatException unused5) {
                                fileInputStream2 = fileInputStream;
                                if (fileInputStream2 != null) {
                                    try {
                                        fileInputStream2.close();
                                    } catch (IOException unused6) {
                                    }
                                }
                                if (fileOutputStream == null) {
                                    return true;
                                }
                                fileOutputStream.close();
                                return true;
                            } catch (Throwable th2) {
                                th = th2;
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException unused7) {
                                    }
                                }
                                if (fileOutputStream == null) {
                                    try {
                                        fileOutputStream.close();
                                        throw th;
                                    } catch (IOException unused8) {
                                        throw th;
                                    }
                                }
                                throw th;
                            }
                        }
                        fileInputStream.close();
                        fileOutputStream.close();
                        long currentTimeMillis2 = System.currentTimeMillis();
                        System.nanoTime();
                        SluggishDetector.Before_Store_time = currentTimeMillis2;
                        fileInputStream2 = fileInputStream;
                    } catch (FileNotFoundException unused9) {
                        fileOutputStream = null;
                    } catch (IOException unused10) {
                        fileOutputStream = null;
                    } catch (NumberFormatException unused11) {
                        fileOutputStream = null;
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream = null;
                        th = th;
                        if (fileInputStream != null) {
                        }
                        if (fileOutputStream == null) {
                        }
                    }
                } else {
                    fileOutputStream = null;
                }
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException unused12) {
                    }
                }
                if (fileOutputStream == null) {
                    return true;
                }
            } catch (FileNotFoundException unused13) {
                fileOutputStream = null;
            } catch (IOException unused14) {
                fileOutputStream = null;
            } catch (NumberFormatException unused15) {
                fileOutputStream = null;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                fileInputStream = null;
            }
            try {
                fileOutputStream.close();
                return true;
            } catch (IOException unused16) {
                return true;
            }
        }

        public static void makestoreOLOG() {
            sendPeriodicReportToHandler();
            clear();
        }

        public static void sendPeriodicReportToHandler() {
            SluggishDetectorHandler.sendMessageToHandlerDelayed(21, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
        }

        public static void clear() {
            if (check()) {
                return;
            }
            init();
        }
    }

    public final class LockContentionInfo {
        public static final String TAG = SluggishDetector.TAG + "_" + LockContentionInfo.class.getSimpleName();
        public static long prevTime = -1;
        public int LcdOnValue;
        public int mOwnerFileLine;
        public String mOwnerFileName;
        public String mOwnerMethod;
        public String mThreadName;
        public int mWaitTime;

        public final void LockInfo_Logging() {
        }

        public LockContentionInfo(String str) {
            String[] split = str.split("/");
            this.mThreadName = split[0];
            this.mWaitTime = Integer.parseInt(split[1]);
            this.mOwnerFileName = split[2];
            this.mOwnerFileLine = Integer.parseInt(split[3]);
            this.mOwnerMethod = split[4];
            this.LcdOnValue = -1;
        }

        public boolean checkTime() {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = prevTime;
            if (j != -1 && (elapsedRealtime - j) / 1000 < 5) {
                return false;
            }
            prevTime = elapsedRealtime;
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3 */
        /* JADX WARN: Type inference failed for: r0v4, types: [int] */
        /* JADX WARN: Type inference failed for: r0v6 */
        public void updateLCD() {
            PowerManager powerManager = (PowerManager) SluggishDetector.mContext.getSystemService("power");
            this.LcdOnValue = powerManager != null ? powerManager.isInteractive() : -99;
        }

        public String toString() {
            return "\"SDVR\":\"1.2.0\",\"THNM\":\"" + this.mThreadName + "\",\"OMTD\":\"" + this.mOwnerMethod + "\",\"OFNM\":\"" + this.mOwnerFileName + "\",\"OFLN\":\"" + this.mOwnerFileLine + "\",\"WTTM\":\"" + this.mWaitTime + "\",\"LCDV\":\"" + this.LcdOnValue + "\"";
        }
    }

    public static void setLockContentionInfo(short s, String str) {
        if (ENABLE) {
            try {
                SluggishDetectorHandler.sendDataToHandler(13, str);
                PerfLog.d(14, s, str);
                PeriodicStoreOLOG.step();
            } catch (Exception unused) {
            }
        }
    }
}
