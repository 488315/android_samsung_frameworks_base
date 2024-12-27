package com.android.server.am;

import android.util.ArrayMap;

import com.android.internal.app.procstats.ProcessStats;
import com.android.server.wm.WindowProcessController;

import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageList {
    public final ArrayMap mPkgList = new ArrayMap();
    public final ProcessRecord mProcess;

    public PackageList(ProcessRecord processRecord) {
        this.mProcess = processRecord;
    }

    public final void clear() {
        synchronized (this) {
            this.mPkgList.clear();
            WindowProcessController windowProcessController =
                    this.mProcess.mWindowProcessController;
            synchronized (windowProcessController.mPkgList) {
                windowProcessController.mPkgList.clear();
            }
        }
    }

    public final boolean containsKey(Object obj) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mPkgList.containsKey(obj);
        }
        return containsKey;
    }

    public final void forEachPackage(Consumer consumer) {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                for (int i = 0; i < size; i++) {
                    consumer.accept((String) this.mPkgList.keyAt(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void forEachPackageProcessStats(Consumer consumer) {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                for (int i = 0; i < size; i++) {
                    consumer.accept((ProcessStats.ProcessStateHolder) this.mPkgList.valueAt(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ProcessStats.ProcessStateHolder get(String str) {
        ProcessStats.ProcessStateHolder processStateHolder;
        synchronized (this) {
            processStateHolder = (ProcessStats.ProcessStateHolder) this.mPkgList.get(str);
        }
        return processStateHolder;
    }

    public final String[] getPackageList() {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                if (size == 0) {
                    return null;
                }
                String[] strArr = new String[size];
                for (int i = 0; i < size; i++) {
                    strArr[i] = (String) this.mPkgList.keyAt(i);
                }
                return strArr;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void put(String str, ProcessStats.ProcessStateHolder processStateHolder) {
        synchronized (this) {
            WindowProcessController windowProcessController =
                    this.mProcess.mWindowProcessController;
            synchronized (windowProcessController.mPkgList) {
                try {
                    if (!windowProcessController.mPkgList.contains(str)) {
                        windowProcessController.mPkgList.add(str);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final Object searchEachPackage(Function function) {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                for (int i = 0; i < size; i++) {
                    Object apply = function.apply((String) this.mPkgList.keyAt(i));
                    if (apply != null) {
                        return apply;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int size() {
        int size;
        synchronized (this) {
            size = this.mPkgList.size();
        }
        return size;
    }
}
