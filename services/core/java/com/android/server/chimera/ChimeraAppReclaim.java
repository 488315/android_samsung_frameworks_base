package com.android.server.chimera;

import com.android.server.chimera.ChimeraAppInfo;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ChimeraAppReclaim {
    public ChimeraAppManager mAppManager;
    public final SystemRepository mSystemRepository;
    public String TAG = "ChimeraAppReclaim";
    public List mPkgCacheReclaimable = new ArrayList();
    public List mPkgGcList = new ArrayList();
    public int mActionReclaimCnt = 0;
    public int mSkipReclaimCnt = 0;
    public int mActionGcCnt = 0;

    public ChimeraAppReclaim(SystemRepository systemRepository, ChimeraAppManager chimeraAppManager) {
        this.mSystemRepository = systemRepository;
        this.mAppManager = chimeraAppManager;
    }

    public final boolean performCompaction(String str, int i) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/proc/" + i + "/reclaim");
            try {
                fileOutputStream.write(str.getBytes());
                fileOutputStream.close();
                return true;
            } finally {
            }
        } catch (Exception e) {
            this.mSystemRepository.log(this.TAG, "Exception performCompaction, e: " + e);
            return false;
        }
    }

    public void performReclaimCache(List list) {
        Iterator it;
        this.mSystemRepository.log(this.TAG, "Start performReclaimCache");
        try {
            long totalMemFree = ChimeraCommonUtil.getTotalMemFree();
            Iterator it2 = list.iterator();
            int i = 0;
            long j = 0;
            while (it2.hasNext()) {
                ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) it2.next();
                if (this.mPkgCacheReclaimable.contains(chimeraAppInfo.packageName)) {
                    Iterator it3 = chimeraAppInfo.procList.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            it = it2;
                            break;
                        }
                        ChimeraAppInfo.ProcessInfo processInfo = (ChimeraAppInfo.ProcessInfo) it3.next();
                        long procPss = ChimeraCommonUtil.getProcPss(this.mSystemRepository, processInfo.pid);
                        if (procPss < 10240) {
                            this.mSystemRepository.log(this.TAG, "performReclaimCache skiped pss " + procPss);
                        } else if (performCompaction("file", processInfo.pid)) {
                            int i2 = i + 1;
                            this.mActionReclaimCnt++;
                            long procPss2 = ChimeraCommonUtil.getProcPss(this.mSystemRepository, processInfo.pid);
                            it = it2;
                            this.mSystemRepository.log(this.TAG, "performReclaimCache " + processInfo.pid + ", " + chimeraAppInfo.packageName + " pss before " + procPss + " pss after " + procPss2);
                            if (procPss >= procPss2) {
                                j += procPss - procPss2;
                            }
                            if (j >= 30720) {
                                i = i2;
                                break;
                            } else {
                                it2 = it;
                                i = i2;
                            }
                        }
                    }
                    this.mPkgCacheReclaimable.remove(chimeraAppInfo.packageName);
                    if (j >= 30720) {
                        break;
                    } else {
                        it2 = it;
                    }
                }
            }
            if (i == 0) {
                this.mSkipReclaimCnt++;
                return;
            }
            long totalMemFree2 = ChimeraCommonUtil.getTotalMemFree();
            this.mSystemRepository.log(this.TAG, "memFreeBefore: " + totalMemFree + " memFreeAfter: " + totalMemFree2 + " memFree delta: " + (totalMemFree2 - totalMemFree));
        } catch (Exception e) {
            this.mSystemRepository.log(this.TAG, "Exception performReclaimCache, e: " + e);
        }
    }

    public void updatePackageCacheReclaimable(String str) {
        if (this.mPkgCacheReclaimable.size() > 50) {
            this.mPkgCacheReclaimable.remove(0);
        }
        this.mPkgCacheReclaimable.add(str);
    }

    public void updateGcPackage(String str) {
        if (this.mPkgGcList.size() > 50) {
            this.mPkgGcList.remove(0);
        }
        this.mPkgGcList.add(str);
    }

    public void performGc(List list) {
        this.mSystemRepository.log(this.TAG, "performGc");
        try {
            Iterator it = list.iterator();
            int i = 0;
            while (it.hasNext()) {
                ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) it.next();
                if (this.mPkgGcList.contains(chimeraAppInfo.packageName)) {
                    for (ChimeraAppInfo.ProcessInfo processInfo : chimeraAppInfo.procList) {
                        long procPss = ChimeraCommonUtil.getProcPss(this.mSystemRepository, processInfo.pid);
                        if (procPss < 10240) {
                            this.mSystemRepository.log(this.TAG, "performGc skiped pss " + procPss);
                        } else {
                            this.mSystemRepository.log(this.TAG, "performGc before " + processInfo.pid + ", " + chimeraAppInfo.packageName + "  " + procPss);
                            this.mSystemRepository.forceGc(processInfo.pid);
                        }
                    }
                    this.mPkgGcList.remove(chimeraAppInfo.packageName);
                    this.mActionGcCnt++;
                    i++;
                    if (i >= 2) {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            this.mSystemRepository.log(this.TAG, "Exception performGc, e: " + e);
        }
    }
}
