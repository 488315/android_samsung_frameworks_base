package com.android.server.chimera;

import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ChimeraRecentAppManager {
    public final RecentAppHandler mHandler;
    final SystemRepository.ChimeraProcessObserver mProcessObserver;
    public final SettingRepository mSettingRepository;
    public final SystemRepository mSystemRepository;
    public final Object mRecentAppLock = new Object();
    public final Object mPsiListLock = new Object();
    List mRecentAppList = new ArrayList();
    Map mPsiSomeTotalList = new HashMap();
    Map mPsiFullTotalList = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RecentAppHandler extends Handler {
        public RecentAppHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int intValue = ((Integer) message.obj).intValue();
            int i = message.what;
            ChimeraRecentAppManager chimeraRecentAppManager = ChimeraRecentAppManager.this;
            if (i == 1) {
                chimeraRecentAppManager.checkAppUsageStart(intValue);
            } else {
                if (i != 2) {
                    return;
                }
                chimeraRecentAppManager.checkAppUsageEnd(intValue);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ResourceUsageInfo {}

    public ChimeraRecentAppManager(
            SystemRepository systemRepository, SettingRepository settingRepository, Looper looper) {
        SystemRepository.ChimeraProcessObserver chimeraProcessObserver =
                new SystemRepository
                        .ChimeraProcessObserver() { // from class:
                                                    // com.android.server.chimera.ChimeraRecentAppManager.1
                    @Override // com.android.server.chimera.SystemRepository.ChimeraProcessObserver
                    public final void onForegroundActivitiesChanged(
                            int i, int i2, boolean z, int i3, String[] strArr, boolean z2) {
                        if (!z || z2) {
                            return;
                        }
                        ChimeraRecentAppManager chimeraRecentAppManager =
                                ChimeraRecentAppManager.this;
                        if (chimeraRecentAppManager
                                .mSettingRepository
                                .mIsDynamicTargetFreeEnabled) {
                            boolean z3 = false;
                            String str = strArr[0];
                            SystemRepository systemRepository2 =
                                    chimeraRecentAppManager.mSystemRepository;
                            if (((ArrayMap) systemRepository2.mPkgIconMap).containsKey(str)) {
                                z3 =
                                        ((Boolean)
                                                        ((ArrayMap) systemRepository2.mPkgIconMap)
                                                                .get(str))
                                                .booleanValue();
                            } else {
                                Intent intent =
                                        new Intent("android.intent.action.MAIN", (Uri) null);
                                intent.addCategory("android.intent.category.LAUNCHER");
                                intent.setPackage(str);
                                List queryIntentActivitiesAsUser =
                                        systemRepository2.mPackageManager
                                                .queryIntentActivitiesAsUser(intent, 0, i3);
                                if (queryIntentActivitiesAsUser != null
                                        && queryIntentActivitiesAsUser.size() > 0) {
                                    z3 = true;
                                }
                                ((ArrayMap) systemRepository2.mPkgIconMap)
                                        .put(str, Boolean.valueOf(z3));
                            }
                            if (z3) {
                                RecentAppHandler recentAppHandler =
                                        chimeraRecentAppManager.mHandler;
                                Integer valueOf = Integer.valueOf(i2);
                                systemRepository2.getClass();
                                if (recentAppHandler != null) {
                                    recentAppHandler.sendMessage(
                                            Message.obtain(recentAppHandler, 1, valueOf));
                                }
                                if (chimeraRecentAppManager.mPsiSomeTotalList.containsKey(
                                        Integer.valueOf(i2))) {
                                    RecentAppHandler recentAppHandler2 =
                                            chimeraRecentAppManager.mHandler;
                                    Integer valueOf2 = Integer.valueOf(i2);
                                    systemRepository2.getClass();
                                    if (recentAppHandler2.hasMessages(2, valueOf2)) {
                                        RecentAppHandler recentAppHandler3 =
                                                chimeraRecentAppManager.mHandler;
                                        Integer valueOf3 = Integer.valueOf(i2);
                                        systemRepository2.getClass();
                                        recentAppHandler3.removeMessages(2, valueOf3);
                                    }
                                }
                                RecentAppHandler recentAppHandler4 =
                                        chimeraRecentAppManager.mHandler;
                                Integer valueOf4 = Integer.valueOf(i2);
                                systemRepository2.getClass();
                                if (recentAppHandler4 != null) {
                                    recentAppHandler4.sendMessageDelayed(
                                            Message.obtain(recentAppHandler4, 2, valueOf4), 1000L);
                                }
                            }
                        }
                    }
                };
        this.mProcessObserver = chimeraProcessObserver;
        this.mSystemRepository = systemRepository;
        this.mSettingRepository = settingRepository;
        this.mHandler = new RecentAppHandler(looper);
        systemRepository.registerProcessObserver(chimeraProcessObserver);
    }

    public void checkAppUsageEnd(int i) {
        String[] list;
        BufferedReader bufferedReader;
        int[] iArr = ChimeraCommonUtil.ADJ_LEVELS;
        String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i, "/acct/uid_");
        File file = new File(m);
        HashSet hashSet = new HashSet();
        BufferedReader bufferedReader2 = null;
        if (file.isDirectory() && (list = file.list()) != null) {
            for (int i2 = 0; i2 < list.length; i2++) {
                if (list[i2].contains("pid")) {
                    String m2 =
                            AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                    Preconditions$$ExternalSyntheticOutline0.m(m, "/"),
                                    list[i2],
                                    "/cgroup.procs");
                    try {
                        bufferedReader = new BufferedReader(new FileReader(m2));
                    } catch (IOException e) {
                        e = e;
                        bufferedReader = null;
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        try {
                            for (String readLine = bufferedReader.readLine();
                                    readLine != null;
                                    readLine = bufferedReader.readLine()) {
                                hashSet.add(Integer.valueOf(readLine));
                            }
                            try {
                                bufferedReader.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        } catch (IOException e3) {
                            e = e3;
                            Log.e("ChimeraCommonUtil", "can't read " + m2 + e.getMessage());
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader2 = bufferedReader;
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            }
        }
        if (hashSet.size() > 0) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                this.mSystemRepository.getClass();
                Debug.getPss(intValue, null, null);
            }
            ChimeraCommonUtil.getAvailableMemoryKb(this.mSystemRepository);
            Pair psiInfo = getPsiInfo(i);
            updatePsiInfo(i);
            Pair psiInfo2 = getPsiInfo(i);
            if (psiInfo == null || psiInfo2 == null) {
                return;
            }
            ((Long) psiInfo2.first).getClass();
            ((Long) psiInfo.first).getClass();
            ((Long) psiInfo2.second).getClass();
            ((Long) psiInfo.second).getClass();
            synchronized (this.mPsiListLock) {
                this.mPsiSomeTotalList.remove(Integer.valueOf(i));
                this.mPsiFullTotalList.remove(Integer.valueOf(i));
            }
            synchronized (this.mRecentAppLock) {
                try {
                    this.mRecentAppList.add(new ResourceUsageInfo());
                    if (this.mRecentAppList.size() > 10) {
                        this.mRecentAppList.remove(0);
                    }
                } finally {
                }
            }
        }
    }

    public void checkAppUsageStart(int i) {
        updatePsiInfo(i);
    }

    public final Pair getPsiInfo(int i) {
        synchronized (this.mPsiListLock) {
            try {
                Long l = (Long) this.mPsiSomeTotalList.get(Integer.valueOf(i));
                Long l2 = (Long) this.mPsiFullTotalList.get(Integer.valueOf(i));
                if (l == null || l2 == null) {
                    return null;
                }
                return Pair.create(l, l2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00cb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updatePsiInfo(int r9) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.chimera.ChimeraRecentAppManager.updatePsiInfo(int):void");
    }
}
