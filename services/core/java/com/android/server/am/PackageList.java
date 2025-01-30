package com.android.server.am;

import android.content.pm.VersionedPackage;
import android.util.ArrayMap;
import com.android.internal.app.procstats.ProcessStats;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public final class PackageList {
    public final ArrayMap mPkgList = new ArrayMap();
    public final ProcessRecord mProcess;

    public PackageList(ProcessRecord processRecord) {
        this.mProcess = processRecord;
    }

    public ProcessStats.ProcessStateHolder put(String str, ProcessStats.ProcessStateHolder processStateHolder) {
        ProcessStats.ProcessStateHolder processStateHolder2;
        synchronized (this) {
            this.mProcess.getWindowProcessController().addPackage(str);
            processStateHolder2 = (ProcessStats.ProcessStateHolder) this.mPkgList.put(str, processStateHolder);
        }
        return processStateHolder2;
    }

    public void clear() {
        synchronized (this) {
            this.mPkgList.clear();
            this.mProcess.getWindowProcessController().clearPackageList();
        }
    }

    public int size() {
        int size;
        synchronized (this) {
            size = this.mPkgList.size();
        }
        return size;
    }

    public boolean containsKey(Object obj) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mPkgList.containsKey(obj);
        }
        return containsKey;
    }

    public ProcessStats.ProcessStateHolder get(String str) {
        ProcessStats.ProcessStateHolder processStateHolder;
        synchronized (this) {
            processStateHolder = (ProcessStats.ProcessStateHolder) this.mPkgList.get(str);
        }
        return processStateHolder;
    }

    public void forEachPackage(Consumer consumer) {
        synchronized (this) {
            int size = this.mPkgList.size();
            for (int i = 0; i < size; i++) {
                consumer.accept((String) this.mPkgList.keyAt(i));
            }
        }
    }

    public void forEachPackage(BiConsumer biConsumer) {
        synchronized (this) {
            int size = this.mPkgList.size();
            for (int i = 0; i < size; i++) {
                biConsumer.accept((String) this.mPkgList.keyAt(i), (ProcessStats.ProcessStateHolder) this.mPkgList.valueAt(i));
            }
        }
    }

    public Object searchEachPackage(Function function) {
        synchronized (this) {
            int size = this.mPkgList.size();
            for (int i = 0; i < size; i++) {
                Object apply = function.apply((String) this.mPkgList.keyAt(i));
                if (apply != null) {
                    return apply;
                }
            }
            return null;
        }
    }

    public void forEachPackageProcessStats(Consumer consumer) {
        synchronized (this) {
            int size = this.mPkgList.size();
            for (int i = 0; i < size; i++) {
                consumer.accept((ProcessStats.ProcessStateHolder) this.mPkgList.valueAt(i));
            }
        }
    }

    public ArrayMap getPackageListLocked() {
        return this.mPkgList;
    }

    public String[] getPackageList() {
        synchronized (this) {
            int size = this.mPkgList.size();
            if (size == 0) {
                return null;
            }
            String[] strArr = new String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = (String) this.mPkgList.keyAt(i);
            }
            return strArr;
        }
    }

    public List getPackageListWithVersionCode() {
        synchronized (this) {
            int size = this.mPkgList.size();
            if (size == 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < size; i++) {
                arrayList.add(new VersionedPackage((String) this.mPkgList.keyAt(i), ((ProcessStats.ProcessStateHolder) this.mPkgList.valueAt(i)).appVersion));
            }
            return arrayList;
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        synchronized (this) {
            printWriter.print(str);
            printWriter.print("packageList={");
            int size = this.mPkgList.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    printWriter.print(", ");
                }
                printWriter.print((String) this.mPkgList.keyAt(i));
            }
            printWriter.println("}");
        }
    }
}
