package com.android.server.chimera;

import com.android.internal.util.RingBuffer;
import com.android.server.chimera.SystemRepository;
import com.android.server.display.DisplayPowerController2;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ReentryCounter implements SystemRepository.ChimeraProcessObserver, SystemRepository.ChimeraUidObserver {
    public final SystemRepository mSystemRepository;
    public float mTargetReentryCount;
    public Map mPkgReentryMap = new HashMap();
    public int mLaunchedAppCnt = 0;
    public boolean mEnableReentry = false;
    public RingBuffer mPkgReentryBuffer = new RingBuffer(Integer.class, 50);

    public ReentryCounter(SystemRepository systemRepository) {
        this.mSystemRepository = systemRepository;
        systemRepository.registerProcessObserver(this);
        systemRepository.registerUidObserver(this);
    }

    public void setTargetReentryCount(float f) {
        this.mTargetReentryCount = f;
    }

    public boolean isReentryEnabled() {
        return this.mEnableReentry;
    }

    public float getReentry() {
        int size = this.mPkgReentryBuffer.size();
        if (size <= 0) {
            return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }
        int i = 0;
        for (Integer num : (Integer[]) this.mPkgReentryBuffer.toArray()) {
            i += num.intValue();
        }
        return i / size;
    }

    public void onUidGone(int i, String str) {
        if (this.mPkgReentryMap.containsKey(str)) {
            if (this.mSystemRepository.isKilledByRecentTask(i, str)) {
                this.mPkgReentryMap.remove(str);
                return;
            }
            this.mSystemRepository.log("ReentryCounter", "app reentry info: app name: " + str + "  reentry count:" + this.mPkgReentryMap.get(str));
            this.mPkgReentryBuffer.append((Integer) this.mPkgReentryMap.get(str));
            this.mPkgReentryMap.remove(str);
        }
    }

    @Override // com.android.server.chimera.SystemRepository.ChimeraProcessObserver
    public void onForegroundActivitiesChanged(int i, int i2, boolean z, int i3, String[] strArr, boolean z2) {
        if (!z || z2) {
            return;
        }
        if (this.mSystemRepository.hasPkgIcon(strArr[0], i3)) {
            for (String str : strArr) {
                if (!this.mPkgReentryMap.containsKey(str)) {
                    this.mPkgReentryMap.put(str, 0);
                }
            }
        }
        for (String str2 : this.mPkgReentryMap.keySet()) {
            Map map = this.mPkgReentryMap;
            map.put(str2, Integer.valueOf(((Integer) map.get(str2)).intValue() + 1));
        }
        int i4 = this.mLaunchedAppCnt;
        if (i4 > this.mTargetReentryCount) {
            this.mEnableReentry = true;
        } else {
            this.mLaunchedAppCnt = i4 + 1;
        }
    }

    @Override // com.android.server.chimera.SystemRepository.ChimeraUidObserver
    public void onUidGone(int i, boolean z) {
        if (this.mSystemRepository.isApp(i)) {
            for (String str : this.mSystemRepository.getPackageNameFromUid(i)) {
                onUidGone(i, str);
            }
        }
    }
}
