package com.android.systemui.appops;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.permission.PermissionManager;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppOpsControllerImpl extends BroadcastReceiver implements AppOpsController, AppOpsManager.OnOpActiveChangedListener, AppOpsManager.OnOpNotedInternalListener, IndividualSensorPrivacyController.Callback, Dumpable {
    public static final int[] OPS = {42, 26, 101, 24, 27, 120, 121, 100, 0, 1};
    public final AppOpsManager mAppOps;
    public final AudioManager mAudioManager;
    public HandlerC1036H mBGHandler;
    public boolean mCameraDisabled;
    public final SystemClock mClock;
    public final Context mContext;
    public final BroadcastDispatcher mDispatcher;
    public boolean mListening;
    public boolean mMicMuted;
    public final IndividualSensorPrivacyController mSensorPrivacyController;
    public final List mCallbacks = new ArrayList();
    public final SparseArray mCallbacksByCode = new SparseArray();
    public final List mActiveItems = new ArrayList();
    public final List mNotedItems = new ArrayList();
    public final SparseArray mRecordingsByUid = new SparseArray();
    public final C10351 mAudioRecordingCallback = new C10351();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.appops.AppOpsControllerImpl$1 */
    public final class C10351 extends AudioManager.AudioRecordingCallback {
        public C10351() {
        }

        @Override // android.media.AudioManager.AudioRecordingCallback
        public final void onRecordingConfigChanged(List list) {
            synchronized (AppOpsControllerImpl.this.mActiveItems) {
                AppOpsControllerImpl.this.mRecordingsByUid.clear();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    AudioRecordingConfiguration audioRecordingConfiguration = (AudioRecordingConfiguration) list.get(i);
                    ArrayList arrayList = (ArrayList) AppOpsControllerImpl.this.mRecordingsByUid.get(audioRecordingConfiguration.getClientUid());
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        AppOpsControllerImpl.this.mRecordingsByUid.put(audioRecordingConfiguration.getClientUid(), arrayList);
                    }
                    arrayList.add(audioRecordingConfiguration);
                }
            }
            AppOpsControllerImpl.this.updateSensorDisabledStatus();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.appops.AppOpsControllerImpl$H */
    public final class HandlerC1036H extends Handler {
        public HandlerC1036H(Looper looper) {
            super(looper);
        }
    }

    public AppOpsControllerImpl(Context context, Looper looper, DumpManager dumpManager, AudioManager audioManager, IndividualSensorPrivacyController individualSensorPrivacyController, BroadcastDispatcher broadcastDispatcher, SystemClock systemClock) {
        this.mDispatcher = broadcastDispatcher;
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
        this.mBGHandler = new HandlerC1036H(looper);
        for (int i = 0; i < 10; i++) {
            this.mCallbacksByCode.put(OPS[i], new ArraySet());
        }
        this.mAudioManager = audioManager;
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mMicMuted = audioManager.isMicrophoneMute() || ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlocked(1);
        this.mCameraDisabled = ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlocked(2);
        this.mContext = context;
        this.mClock = systemClock;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "AppOpsControllerImpl", this);
    }

    public static AppOpItem getAppOpItemLocked(int i, int i2, String str, List list) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            AppOpItem appOpItem = (AppOpItem) arrayList.get(i3);
            if (appOpItem.mCode == i && appOpItem.mUid == i2 && appOpItem.mPackageName.equals(str)) {
                return appOpItem;
            }
        }
        return null;
    }

    public final void addCallback(int[] iArr, AppOpsController.Callback callback) {
        int length = iArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (this.mCallbacksByCode.contains(iArr[i])) {
                ((Set) this.mCallbacksByCode.get(iArr[i])).add(callback);
                z = true;
            }
        }
        if (z) {
            ((ArrayList) this.mCallbacks).add(callback);
        }
        if (((ArrayList) this.mCallbacks).isEmpty()) {
            return;
        }
        setListening(true);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "AppOpsController state:", "  Listening: ");
        m75m.append(this.mListening);
        printWriter.println(m75m.toString());
        printWriter.println("  Active Items:");
        for (int i = 0; i < ((ArrayList) this.mActiveItems).size(); i++) {
            AppOpItem appOpItem = (AppOpItem) ((ArrayList) this.mActiveItems).get(i);
            printWriter.print("    ");
            printWriter.println(appOpItem.toString());
        }
        printWriter.println("  Noted Items:");
        for (int i2 = 0; i2 < ((ArrayList) this.mNotedItems).size(); i2++) {
            AppOpItem appOpItem2 = (AppOpItem) ((ArrayList) this.mNotedItems).get(i2);
            printWriter.print("    ");
            printWriter.println(appOpItem2.toString());
        }
    }

    public final List getActiveAppOps(boolean z) {
        int i;
        Assert.isNotMainThread();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mActiveItems) {
            int size = ((ArrayList) this.mActiveItems).size();
            for (int i2 = 0; i2 < size; i2++) {
                AppOpItem appOpItem = (AppOpItem) ((ArrayList) this.mActiveItems).get(i2);
                if (isUserVisible(appOpItem.mPackageName, appOpItem.mAttributionTag) && (z || !appOpItem.mIsDisabled)) {
                    arrayList.add(appOpItem);
                }
            }
        }
        synchronized (this.mNotedItems) {
            int size2 = ((ArrayList) this.mNotedItems).size();
            for (i = 0; i < size2; i++) {
                AppOpItem appOpItem2 = (AppOpItem) ((ArrayList) this.mNotedItems).get(i);
                if (isUserVisible(appOpItem2.mPackageName, appOpItem2.mAttributionTag)) {
                    arrayList.add(appOpItem2);
                }
            }
        }
        return arrayList;
    }

    public final boolean isAnyRecordingPausedLocked(int i) {
        if (this.mMicMuted) {
            return true;
        }
        List list = (List) this.mRecordingsByUid.get(i);
        if (list == null) {
            return false;
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((AudioRecordingConfiguration) list.get(i2)).isClientSilenced()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUserVisible(String str, String str2) {
        return PermissionManager.shouldShowPackageForIndicatorCached(this.mContext, str) || "SLocationService".equals(str2) || "Biometrics_FaceService".equals(str2);
    }

    public final void notifySuscribers(final int i, final String str, final String str2, final int i2, final boolean z) {
        this.mBGHandler.post(new Runnable() { // from class: com.android.systemui.appops.AppOpsControllerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AppOpsControllerImpl appOpsControllerImpl = AppOpsControllerImpl.this;
                int i3 = i;
                int i4 = i2;
                appOpsControllerImpl.notifySuscribersWorker(i3, str, str2, i4, z);
            }
        });
    }

    public final void notifySuscribersWorker(int i, String str, String str2, int i2, boolean z) {
        if (this.mCallbacksByCode.contains(i) && isUserVisible(str, str2)) {
            Iterator it = ((Set) this.mCallbacksByCode.get(i)).iterator();
            while (it.hasNext()) {
                ((AppOpsController.Callback) it.next()).onActiveStateChanged(z, str, i, i2);
            }
        }
    }

    @Override // android.app.AppOpsManager.OnOpActiveChangedListener
    public final void onOpActiveChanged(String str, int i, String str2, boolean z) {
        onOpActiveChanged(str, i, str2, null, z, 0, -1);
    }

    public final void onOpNoted(int i, int i2, String str, String str2, int i3, int i4) {
        final AppOpItem appOpItemLocked;
        boolean z;
        boolean z2;
        if (i4 != 0) {
            return;
        }
        synchronized (this.mNotedItems) {
            appOpItemLocked = getAppOpItemLocked(i, i2, str, this.mNotedItems);
            z = true;
            if (appOpItemLocked == null) {
                ((SystemClockImpl) this.mClock).getClass();
                appOpItemLocked = new AppOpItem(i, i2, str, android.os.SystemClock.elapsedRealtime(), str2);
                ((ArrayList) this.mNotedItems).add(appOpItemLocked);
                z2 = true;
            } else {
                z2 = false;
            }
        }
        this.mBGHandler.removeCallbacksAndMessages(appOpItemLocked);
        final HandlerC1036H handlerC1036H = this.mBGHandler;
        handlerC1036H.removeCallbacksAndMessages(appOpItemLocked);
        handlerC1036H.postDelayed(new Runnable() { // from class: com.android.systemui.appops.AppOpsControllerImpl.H.1
            @Override // java.lang.Runnable
            public final void run() {
                boolean z3;
                AppOpsControllerImpl appOpsControllerImpl = AppOpsControllerImpl.this;
                AppOpItem appOpItem = appOpItemLocked;
                int i5 = appOpItem.mCode;
                int i6 = appOpItem.mUid;
                String str3 = appOpItem.mPackageName;
                String str4 = appOpItem.mAttributionTag;
                synchronized (appOpsControllerImpl.mNotedItems) {
                    AppOpItem appOpItemLocked2 = AppOpsControllerImpl.getAppOpItemLocked(i5, i6, str3, appOpsControllerImpl.mNotedItems);
                    if (appOpItemLocked2 == null) {
                        return;
                    }
                    ((ArrayList) appOpsControllerImpl.mNotedItems).remove(appOpItemLocked2);
                    synchronized (appOpsControllerImpl.mActiveItems) {
                        z3 = AppOpsControllerImpl.getAppOpItemLocked(i5, i6, str3, appOpsControllerImpl.mActiveItems) != null;
                    }
                    if (z3) {
                        return;
                    }
                    appOpsControllerImpl.notifySuscribersWorker(i5, str3, str4, i6, false);
                }
            }
        }, appOpItemLocked, 5000L);
        if (z2) {
            synchronized (this.mActiveItems) {
                if (getAppOpItemLocked(i, i2, str, this.mActiveItems) == null) {
                    z = false;
                }
            }
            if (z) {
                return;
            }
            notifySuscribers(i, str, str2, i2, true);
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        boolean z = true;
        if (!this.mAudioManager.isMicrophoneMute() && !((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).isSensorBlocked(1)) {
            z = false;
        }
        this.mMicMuted = z;
        updateSensorDisabledStatus();
    }

    @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
    public final void onSensorBlockedChanged(final int i, final boolean z) {
        this.mBGHandler.post(new Runnable() { // from class: com.android.systemui.appops.AppOpsControllerImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AppOpsControllerImpl appOpsControllerImpl = AppOpsControllerImpl.this;
                int i2 = i;
                boolean z2 = z;
                if (i2 == 2) {
                    appOpsControllerImpl.mCameraDisabled = z2;
                } else {
                    boolean z3 = true;
                    if (i2 == 1) {
                        if (!appOpsControllerImpl.mAudioManager.isMicrophoneMute() && !z2) {
                            z3 = false;
                        }
                        appOpsControllerImpl.mMicMuted = z3;
                    }
                }
                appOpsControllerImpl.updateSensorDisabledStatus();
            }
        });
    }

    public void setBGHandler(HandlerC1036H handlerC1036H) {
        this.mBGHandler = handlerC1036H;
    }

    public void setListening(boolean z) {
        this.mListening = z;
        if (!z) {
            this.mAppOps.stopWatchingActive(this);
            this.mAppOps.stopWatchingNoted(this);
            this.mAudioManager.unregisterAudioRecordingCallback(this.mAudioRecordingCallback);
            ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).removeCallback(this);
            this.mBGHandler.removeCallbacksAndMessages(null);
            this.mDispatcher.unregisterReceiver(this);
            synchronized (this.mActiveItems) {
                ((ArrayList) this.mActiveItems).clear();
                this.mRecordingsByUid.clear();
            }
            synchronized (this.mNotedItems) {
                ((ArrayList) this.mNotedItems).clear();
            }
            return;
        }
        for (AppOpsManager.PackageOps packageOps : this.mAppOps.getPackagesForOps(OPS)) {
            for (AppOpsManager.OpEntry opEntry : packageOps.getOps()) {
                for (Map.Entry entry : opEntry.getAttributedOpEntries().entrySet()) {
                    if (((AppOpsManager.AttributedOpEntry) entry.getValue()).isRunning()) {
                        onOpActiveChanged(opEntry.getOpStr(), packageOps.getUid(), packageOps.getPackageName(), (String) entry.getKey(), true, 0, -1);
                    }
                }
            }
        }
        AppOpsManager appOpsManager = this.mAppOps;
        int[] iArr = OPS;
        appOpsManager.startWatchingActive(iArr, this);
        this.mAppOps.startWatchingNoted(iArr, this);
        this.mAudioManager.registerAudioRecordingCallback(this.mAudioRecordingCallback, this.mBGHandler);
        ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).addCallback(this);
        boolean z2 = true;
        if (!this.mAudioManager.isMicrophoneMute() && !((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).isSensorBlocked(1)) {
            z2 = false;
        }
        this.mMicMuted = z2;
        this.mCameraDisabled = ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).isSensorBlocked(2);
        this.mBGHandler.post(new Runnable() { // from class: com.android.systemui.appops.AppOpsControllerImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AppOpsControllerImpl appOpsControllerImpl = AppOpsControllerImpl.this;
                appOpsControllerImpl.mAudioRecordingCallback.onRecordingConfigChanged(appOpsControllerImpl.mAudioManager.getActiveRecordingConfigurations());
            }
        });
        this.mDispatcher.registerReceiverWithHandler(this, new IntentFilter("android.media.action.MICROPHONE_MUTE_CHANGED"), this.mBGHandler);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002e A[Catch: all -> 0x0063, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000f, B:15:0x002e, B:16:0x0047, B:18:0x004b, B:21:0x0058, B:23:0x005e, B:33:0x0043, B:38:0x0061), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004b A[Catch: all -> 0x0063, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000f, B:15:0x002e, B:16:0x0047, B:18:0x004b, B:21:0x0058, B:23:0x005e, B:33:0x0043, B:38:0x0061), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0043 A[Catch: all -> 0x0063, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000f, B:15:0x002e, B:16:0x0047, B:18:0x004b, B:21:0x0058, B:23:0x005e, B:33:0x0043, B:38:0x0061), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSensorDisabledStatus() {
        boolean z;
        boolean z2;
        boolean z3;
        synchronized (this.mActiveItems) {
            int size = ((ArrayList) this.mActiveItems).size();
            for (int i = 0; i < size; i++) {
                AppOpItem appOpItem = (AppOpItem) ((ArrayList) this.mActiveItems).get(i);
                int i2 = appOpItem.mCode;
                if (i2 != 27 && i2 != 100 && i2 != 120) {
                    z = false;
                    if (z) {
                        if (i2 != 26 && i2 != 101) {
                            z2 = false;
                            z3 = !z2 ? this.mCameraDisabled : false;
                        }
                        z2 = true;
                        if (!z2) {
                        }
                    } else {
                        z3 = isAnyRecordingPausedLocked(appOpItem.mUid);
                    }
                    if (appOpItem.mIsDisabled == z3) {
                        appOpItem.mIsDisabled = z3;
                        notifySuscribers(appOpItem.mCode, appOpItem.mPackageName, appOpItem.mAttributionTag, appOpItem.mUid, !z3);
                    }
                }
                z = true;
                if (z) {
                }
                if (appOpItem.mIsDisabled == z3) {
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0054 A[Catch: all -> 0x00b0, TryCatch #0 {, blocks: (B:15:0x001e, B:18:0x002a, B:27:0x0054, B:28:0x006d, B:31:0x007b, B:58:0x0069, B:63:0x0081, B:64:0x0088, B:66:0x008b), top: B:14:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0069 A[Catch: all -> 0x00b0, TryCatch #0 {, blocks: (B:15:0x001e, B:18:0x002a, B:27:0x0054, B:28:0x006d, B:31:0x007b, B:58:0x0069, B:63:0x0081, B:64:0x0088, B:66:0x008b), top: B:14:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onOpActiveChanged(String str, int i, String str2, String str3, boolean z, int i2, int i3) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int strOpToOp = AppOpsManager.strOpToOp(str);
        if (z && i3 != -1 && i2 != 0 && (i2 & 1) == 0 && (i2 & 8) == 0) {
            return;
        }
        synchronized (this.mActiveItems) {
            AppOpItem appOpItemLocked = getAppOpItemLocked(strOpToOp, i, str2, this.mActiveItems);
            if (appOpItemLocked == null && z) {
                ((SystemClockImpl) this.mClock).getClass();
                AppOpItem appOpItem = new AppOpItem(strOpToOp, i, str2, android.os.SystemClock.elapsedRealtime(), str3);
                if (strOpToOp != 27 && strOpToOp != 100 && strOpToOp != 120) {
                    z4 = false;
                    if (z4) {
                        if (strOpToOp != 26 && strOpToOp != 101) {
                            z5 = false;
                            if (z5) {
                                appOpItem.mIsDisabled = this.mCameraDisabled;
                            }
                        }
                        z5 = true;
                        if (z5) {
                        }
                    } else {
                        appOpItem.mIsDisabled = isAnyRecordingPausedLocked(i);
                    }
                    ((ArrayList) this.mActiveItems).add(appOpItem);
                    z2 = appOpItem.mIsDisabled;
                }
                z4 = true;
                if (z4) {
                }
                ((ArrayList) this.mActiveItems).add(appOpItem);
                if (appOpItem.mIsDisabled) {
                }
            } else if (appOpItemLocked == null || z) {
                z2 = false;
            } else {
                ((ArrayList) this.mActiveItems).remove(appOpItemLocked);
                z2 = true;
            }
        }
        if (z2) {
            synchronized (this.mNotedItems) {
                z3 = getAppOpItemLocked(strOpToOp, i, str2, this.mNotedItems) != null;
            }
            if (z3) {
                return;
            }
            notifySuscribers(strOpToOp, str2, str3, i, z);
        }
    }
}
