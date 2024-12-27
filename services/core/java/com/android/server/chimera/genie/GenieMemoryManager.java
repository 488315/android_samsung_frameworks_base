package com.android.server.chimera.genie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.android.internal.util.MemInfoReader;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.SecLmkdStats;
import com.android.server.chimera.SystemRepository;

import com.samsung.android.chimera.genie.MemRequest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public final class GenieMemoryManager {
    public static final SimpleDateFormat DATE_FORMAT;
    public static final int DEFAULT_GOOGLE_MODEL_SIZE;
    public static final int DEFAULT_SAMSUNG_MODEL_SIZE;
    public Context mContext;
    public GenieConfigurations mGenieConfigurations;
    public boolean mHasReclaimed;
    public long mLastReclaimTime;
    public Object mLock;
    public MemoryReclaimer mMemoryReclaimer;
    public String mName;
    public RbinManager mRbinManager;
    public ReclaimerHandler mReclaimerHandler;
    public int mSessionStatus;
    public SystemRepository mSystemRepository;
    public ServiceThread mTimeOutThread;

    public final class ModelEventReceiver extends BroadcastReceiver {
        public ModelEventReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("size", 0);
            if (action != null) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        intExtra, "Receive broadcast: ", action, " size : ", "GenieMemoryManager");
            }
            if ("com.samsung.GEN_AI_RECLAIM".equals(action)) {
                if (intExtra == 0) {
                    intExtra = GenieMemoryManager.DEFAULT_SAMSUNG_MODEL_SIZE;
                }
                Log.d("GenieMemoryManager", "Calling reclaimer through Broadcast " + intExtra);
                GenieMemoryManager.this.prepareMemory(new MemRequest(1, intExtra), action);
                GenieMemoryManager.this.startUnloadTimerLocked(
                        "com.samsung.android.offline.languagemodel");
                return;
            }
            if (!"AICORE_BROADCAST_ACTION_MODEL_LOADING".equals(action)) {
                if ("com.samsung.GEN_AI_RECLAIM_END".equals(action)
                        || "AICORE_BROADCAST_ACTION_MODEL_UNLOADED".equals(action)) {
                    Log.d("GenieMemoryManager", "Stopping Reclaimer through Broadcast ");
                    GenieMemoryManager.this.mMemoryReclaimer.getClass();
                    GenieMemoryManager.this.setGenieSessionEnd();
                    return;
                }
                return;
            }
            if (intExtra == 0) {
                intExtra = GenieMemoryManager.DEFAULT_GOOGLE_MODEL_SIZE;
            }
            Log.d("GenieMemoryManager", "Calling reclaimer through Broadcast " + intExtra);
            GenieMemoryManager.this.prepareMemory(new MemRequest(1, intExtra), action);
        }
    }

    public final class ReclaimerHandler extends Handler {
        public ReclaimerHandler() {
            super(GenieMemoryManager.this.mTimeOutThread.getLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            long j;
            long j2;
            int i = message.what;
            if (i == 0) {
                GenieMemoryManager.this.setGenieSessionEnd();
                return;
            }
            if (i != 1) {
                if (i == 2) {
                    int killCountFromSlotRange =
                            SecLmkdStats.getKillCountFromSlotRange(0, 15, false, false)
                                    - message.arg1;
                    int totalCriticalKillCount =
                            SecLmkdStats.getTotalCriticalKillCount() - message.arg2;
                    ArrayList arrayList = (ArrayList) message.obj;
                    arrayList.add(
                            GenieMemoryManager.DATE_FORMAT.format(Calendar.getInstance().getTime())
                                    + " lmkdKill : "
                                    + killCountFromSlotRange
                                    + " lmkdCriticalKill : "
                                    + totalCriticalKillCount);
                    synchronized (GenieLogger.sLock) {
                        try {
                            if (GenieLogger.sDump == null) {
                                GenieLogger.sDump = new LinkedList();
                            }
                            if (arrayList.size() > 0) {
                                if (((LinkedList) GenieLogger.sDump).size() >= 1000) {
                                    ((LinkedList) GenieLogger.sDump).remove();
                                }
                                ((LinkedList) GenieLogger.sDump).add(arrayList);
                                GenieLogger.sRequestCount++;
                                if (arrayList.size() > 3) {
                                    GenieLogger.sReclaimedRequests++;
                                }
                            }
                        } finally {
                        }
                    }
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            killCountFromSlotRange,
                            totalCriticalKillCount,
                            "Aft GenAI LMKD Kill ",
                            " LMKD Critical ",
                            "GenieMemoryManager");
                    return;
                }
                if (i != 3) {
                    if (i != 4) {
                        return;
                    }
                    Log.d("GenieMemoryManager", "UNLOAD_S_LLM_MODEL message handler");
                    String str = (String) message.obj;
                    if (GenieMemoryManager.this.mSystemRepository.killGenieProcess(
                            GenieConfigurations.GENAI_UNLOAD_OOMADJ_THRESHOLD, str)) {
                        Log.d("GenieMemoryManager", "process killed, remove from LRU list");
                        GenieLRUList.getInstance().remove(str);
                        return;
                    } else {
                        Log.d("GenieMemoryManager", "resetting timer to unload S.LLM");
                        sendMessageDelayed(
                                GenieMemoryManager.this.mReclaimerHandler.obtainMessage(4, str),
                                GenieConfigurations.GENAI_UNLOAD_MODEL_TIMEOUT);
                        return;
                    }
                }
                RbinManager rbinManager = GenieMemoryManager.this.mRbinManager;
                if (rbinManager.mFeatureEnabled) {
                    synchronized (rbinManager.mLock) {
                        try {
                            int i2 = rbinManager.mCount;
                            if (i2 != 0) {
                                int i3 = i2 - 1;
                                rbinManager.mCount = i3;
                                if (i3 <= 0) {
                                    try {
                                        BufferedWriter bufferedWriter =
                                                new BufferedWriter(
                                                        new FileWriter(
                                                                "/sys/kernel/rbin/refill_mode"));
                                        try {
                                            bufferedWriter.write(48);
                                            bufferedWriter.flush();
                                            bufferedWriter.close();
                                        } finally {
                                        }
                                    } catch (IOException e) {
                                        Log.e("RbinManager", "Error writing to the file ", e);
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                }
                return;
            }
            ArrayList arrayList2 = (ArrayList) message.obj;
            GenieMemoryManager genieMemoryManager = GenieMemoryManager.this;
            long j3 = message.arg1;
            genieMemoryManager.getClass();
            MemInfoReader memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
            long[] rawInfo = memInfoReader.getRawInfo();
            long j4 = rawInfo[1];
            long j5 = rawInfo[29];
            long j6 = rawInfo[6];
            long j7 = (j4 - j5) - j6;
            long j8 = rawInfo[26];
            long j9 = rawInfo[27];
            long j10 = j8 + j9;
            long j11 = (j10 / 2) + j7;
            if (j3 > j11) {
                j2 = j3 - j11;
                j = j8;
            } else {
                j = j8;
                j2 = 0;
            }
            arrayList2.add(
                    GenieMemoryManager.DATE_FORMAT.format(Calendar.getInstance().getTime())
                            + " curAvailable : "
                            + j11
                            + " toReclaim : "
                            + j2);
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "reclaimTarget: ", j2, " = targetSize ");
            m.append(j3);
            BootReceiver$$ExternalSyntheticOutline0.m(m, " - ", j7, " (");
            m.append(j4);
            BootReceiver$$ExternalSyntheticOutline0.m(m, ", ", j5, ", ");
            m.append(j6);
            BootReceiver$$ExternalSyntheticOutline0.m(m, ")  - ", j10, " (");
            m.append(j);
            m.append(", ");
            m.append(j9);
            m.append(")");
            Log.i("GenieMemoryManager", m.toString());
            sendMessageDelayed(
                    obtainMessage(
                            2,
                            SecLmkdStats.getKillCountFromSlotRange(0, 15, false, false),
                            SecLmkdStats.getTotalCriticalKillCount(),
                            arrayList2),
                    5000L);
            if (j2 <= 0) {
                GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                        "Memory to Reclaim is less, so skipping GenIE: ", j2, "GenieMemoryManager");
                GenieMemoryManager.this.setGenieSessionEnd();
                return;
            }
            GenieMemoryManager.this.mMemoryReclaimer.getClass();
            Log.i("MemoryReclaimer", "Genie Memory Reclaimer Called");
            try {
                MemoryReclaimer.executeQuickSwap(j2, arrayList2);
            } catch (Exception e2) {
                Log.i("MemoryReclaimer", "error excuting command " + e2.toString());
            }
            synchronized (GenieMemoryManager.this.mLock) {
                GenieMemoryManager.this.mHasReclaimed = true;
            }
            sendMessageDelayed(obtainMessage(0), 5000L);
        }
    }

    static {
        Map map = GenieConfigurations.sAIVersionGoogleModelSize;
        int i = GenieConfigurations.sAIVersion;
        Integer num = (Integer) ((HashMap) map).get(Integer.valueOf(i));
        if (num == null) {
            Log.d("GenieConfigurations", "No models are used on this phone");
        }
        DEFAULT_GOOGLE_MODEL_SIZE = num == null ? 0 : num.intValue();
        Integer num2 =
                (Integer)
                        ((HashMap) GenieConfigurations.sAIVersionSepModelSize)
                                .get(Integer.valueOf(i));
        if (num2 == null) {
            Log.d("GenieConfigurations", "No models are used on this phone");
        }
        DEFAULT_SAMSUNG_MODEL_SIZE = num2 != null ? num2.intValue() : 0;
        DATE_FORMAT = new SimpleDateFormat("[MM-dd HH:mm:ss.SSS]", Locale.getDefault());
    }

    public final void prepareMemory(MemRequest memRequest, String str) {
        long j;
        if (startReclaimerHandlerCheck() == null) {
            Log.e("GenieMemoryManager", "ReclaimerHandler not initialized!!!!! Retry start");
            startReclaimerHandlerCheck();
            if (this.mReclaimerHandler == null) {
                Log.e("GenieMemoryManager", "ReclaimerHandler not initialized!!!!! Return");
                return;
            }
        }
        RbinManager rbinManager = this.mRbinManager;
        if (rbinManager.mFeatureEnabled) {
            synchronized (rbinManager.mLock) {
                rbinManager.mCount++;
            }
            try {
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new FileWriter("/sys/kernel/rbin/refill_mode"));
                try {
                    bufferedWriter.write(49);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } finally {
                }
            } catch (IOException e) {
                Log.e("RbinManager", "Error writing to the file ", e);
            }
        }
        ArrayList arrayList = new ArrayList();
        ReclaimerHandler reclaimerHandler = this.mReclaimerHandler;
        if (reclaimerHandler != null) {
            reclaimerHandler.sendMessageDelayed(reclaimerHandler.obtainMessage(3), 5000L);
        }
        long size = memRequest.getSize();
        synchronized (this.mLock) {
            try {
                int callingUid = Binder.getCallingUid();
                if (str != null) {
                    this.mName = str;
                } else {
                    this.mName = this.mContext.getPackageManager().getPackagesForUid(callingUid)[0];
                }
                Log.i("GenieMemoryManager", "PrepareMemory Calling Package " + this.mName);
                String str2 =
                        (String) ((HashMap) GenieConfigurations.sGenAIPackageMap).get(this.mName);
                if (str2 != null) {
                    Log.d("GenieMemoryManager", "AIPackage:".concat(str2));
                    if (GenieLRUList.getInstance().get(str2) == null) {
                        Log.d("GenieMemoryManager", "Adding to LRU: ".concat(str2));
                        GenieLRUList.getInstance().put(str2, this.mName);
                    } else {
                        Log.d("GenieMemoryManager", "LRU Access:".concat(str2));
                    }
                } else {
                    Log.d("GenieMemoryManager", "AI package is null for " + this.mName);
                }
                if (size <= 0) {
                    Log.e(
                            "GenieMemoryManager",
                            "Invalid Memory Request; No reclaimer triggered " + size);
                } else {
                    j = size + 524288;
                    if (SystemClock.uptimeMillis() - this.mLastReclaimTime < 600000) {
                        Log.e("GenieMemoryManager", "Too early to start another reclaim");
                    } else if (this.mSessionStatus == 1) {
                        Log.e("GenieMemoryManager", "Session already running");
                    } else {
                        GenieConfigurations genieConfigurations = this.mGenieConfigurations;
                        String str3 = this.mName;
                        genieConfigurations.getClass();
                        if (callingUid != 1000
                                && !((ArrayList) GenieConfigurations.sAllowedPackages)
                                        .contains(str3)
                                && !((ArrayList) GenieConfigurations.sAllowedBroadcastActions)
                                        .contains(str3)) {
                            Log.e(
                                    "GenieMemoryManager",
                                    "Contact Memory Team for permissions to access these APIs");
                        }
                        arrayList.add(
                                DATE_FORMAT.format(Calendar.getInstance().getTime())
                                        + " Package: "
                                        + this.mName
                                        + " Uid : "
                                        + callingUid
                                        + " ReclaimRequest : "
                                        + j
                                        + " kB");
                        this.mSessionStatus = 1;
                        Log.d("GenieMemoryManager", "prepareMemoryInternalLocked exit: " + j);
                    }
                }
                j = 0;
            } finally {
            }
        }
        if (j <= 0) {
            Log.e("GenieMemoryManager", "Request to PrepareMemory is denied");
            return;
        }
        ReclaimerHandler reclaimerHandler2 = this.mReclaimerHandler;
        if (reclaimerHandler2 != null) {
            Message obtainMessage = reclaimerHandler2.obtainMessage(1);
            obtainMessage.arg1 = memRequest.getSize();
            obtainMessage.obj = arrayList;
            this.mReclaimerHandler.sendMessage(obtainMessage);
        }
    }

    public final void setGenieSessionEnd() {
        Log.i("GenieMemoryManager", "setGenieSessionEnd");
        synchronized (this.mLock) {
            try {
                if (this.mSessionStatus == 0) {
                    Log.i("GenieMemoryManager", "No session running now ");
                    return;
                }
                this.mMemoryReclaimer.getClass();
                this.mName = null;
                this.mSessionStatus = 0;
                if (this.mHasReclaimed) {
                    this.mLastReclaimTime = SystemClock.uptimeMillis();
                    this.mHasReclaimed = false;
                }
            } finally {
            }
        }
    }

    public final ReclaimerHandler startReclaimerHandlerCheck() {
        synchronized (this.mLock) {
            try {
                if (this.mTimeOutThread == null) {
                    this.mTimeOutThread = new ServiceThread(10, "Queued-Genie-Looper", true);
                }
                if (!this.mTimeOutThread.isAlive()) {
                    this.mTimeOutThread.start();
                    Log.d("GenieMemoryManager", "mTimeOutThread started");
                    this.mReclaimerHandler = null;
                }
                if (this.mReclaimerHandler == null && this.mTimeOutThread.isAlive()) {
                    Log.d("GenieMemoryManager", "ReclaimerHandler created");
                    this.mReclaimerHandler = new ReclaimerHandler();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mReclaimerHandler;
    }

    public final void startUnloadTimerLocked(String str) {
        synchronized (this.mLock) {
            try {
                if (!"com.samsung.android.offline.languagemodel".equals(str)) {
                    Log.d("GenieMemoryManager", "Not supported " + str);
                    return;
                }
                StringBuilder sb = new StringBuilder("startUnloadTimerLocked pkg: ");
                sb.append(str);
                sb.append(", timeout in ms:");
                int i = GenieConfigurations.GENAI_UNLOAD_MODEL_TIMEOUT;
                sb.append(i);
                Log.d("GenieMemoryManager", sb.toString());
                ReclaimerHandler reclaimerHandler = this.mReclaimerHandler;
                if (reclaimerHandler != null) {
                    reclaimerHandler.removeMessages(4);
                    ReclaimerHandler reclaimerHandler2 = this.mReclaimerHandler;
                    reclaimerHandler2.sendMessageDelayed(
                            reclaimerHandler2.obtainMessage(4, str), i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
