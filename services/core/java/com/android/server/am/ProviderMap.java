package com.android.server.am;

import android.app.IApplicationThread;
import android.content.ComponentName;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.internal.os.TransferPipe;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProviderMap {
    public final ActivityManagerService mAm;
    public final HashMap mSingletonByName = new HashMap();
    public final HashMap mSingletonByClass = new HashMap();
    public final SparseArray mProvidersByNamePerUser = new SparseArray();
    public final SparseArray mProvidersByClassPerUser = new SparseArray();

    public ProviderMap(ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
    }

    public static boolean collectPackageProvidersLocked(
            String str, Set set, boolean z, boolean z2, HashMap hashMap, ArrayList arrayList) {
        boolean z3 = false;
        for (ContentProviderRecord contentProviderRecord : hashMap.values()) {
            if (str != null) {
                if (!contentProviderRecord.info.packageName.equals(str)) {
                    continue;
                } else if (set != null) {
                    if (!((ArraySet) set).contains(contentProviderRecord.name.getClassName())) {
                        continue;
                    }
                }
            }
            ProcessRecord processRecord = contentProviderRecord.proc;
            if (processRecord == null || z2 || !processRecord.mPersistent) {
                z3 = true;
                if (!z) {
                    return true;
                }
                arrayList.add(contentProviderRecord);
            }
        }
        return z3;
    }

    public static boolean dumpProvidersByClassLocked(
            PrintWriter printWriter,
            boolean z,
            String str,
            String str2,
            boolean z2,
            HashMap hashMap) {
        Iterator it = hashMap.entrySet().iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            ContentProviderRecord contentProviderRecord =
                    (ContentProviderRecord) ((Map.Entry) it.next()).getValue();
            if (str == null || str.equals(contentProviderRecord.appInfo.packageName)) {
                if (z2) {
                    printWriter.println("");
                    z2 = false;
                }
                if (str2 != null) {
                    printWriter.println(str2);
                    str2 = null;
                }
                printWriter.print("  * ");
                printWriter.println(contentProviderRecord);
                contentProviderRecord.dump(printWriter, "    ", z);
                z3 = true;
            }
        }
        return z3;
    }

    public static boolean dumpProvidersByNameLocked(
            PrintWriter printWriter, String str, String str2, boolean z, HashMap hashMap) {
        boolean z2 = false;
        for (Map.Entry entry : hashMap.entrySet()) {
            ContentProviderRecord contentProviderRecord = (ContentProviderRecord) entry.getValue();
            if (str == null || str.equals(contentProviderRecord.appInfo.packageName)) {
                if (z) {
                    printWriter.println("");
                    z = false;
                }
                if (str2 != null) {
                    printWriter.println(str2);
                    str2 = null;
                }
                printWriter.print("  ");
                printWriter.print((String) entry.getKey());
                printWriter.print(": ");
                printWriter.println(contentProviderRecord.toShortString());
                z2 = true;
            }
        }
        return z2;
    }

    public static void dumpToTransferPipe(
            String str,
            FileDescriptor fileDescriptor,
            PrintWriter printWriter,
            ContentProviderRecord contentProviderRecord,
            IApplicationThread iApplicationThread,
            String[] strArr) {
        try {
            TransferPipe transferPipe = new TransferPipe();
            try {
                iApplicationThread.dumpProvider(
                        transferPipe.getWriteFd(),
                        contentProviderRecord.provider.asBinder(),
                        strArr);
                transferPipe.setBufferPrefix(str);
                transferPipe.go(fileDescriptor, 2000L);
                transferPipe.kill();
            } catch (Throwable th) {
                transferPipe.kill();
                throw th;
            }
        } catch (RemoteException unused) {
            printWriter.println("      Got a RemoteException while dumping the service");
        } catch (IOException e) {
            printWriter.println("      Failure while dumping the provider: " + e);
        }
    }

    public final boolean collectPackageProvidersLocked(
            String str, Set set, boolean z, boolean z2, int i, ArrayList arrayList) {
        boolean collectPackageProvidersLocked =
                (i == -1 || i == 0)
                        ? collectPackageProvidersLocked(
                                str, set, z, z2, this.mSingletonByClass, arrayList)
                        : false;
        if (!z && collectPackageProvidersLocked) {
            return true;
        }
        if (i != -1) {
            return collectPackageProvidersLocked
                    | collectPackageProvidersLocked(
                            str, set, z, z2, getProvidersByClass(i), arrayList);
        }
        boolean z3 = collectPackageProvidersLocked;
        for (int i2 = 0; i2 < this.mProvidersByClassPerUser.size(); i2++) {
            if (collectPackageProvidersLocked(
                    str,
                    set,
                    z,
                    z2,
                    (HashMap) this.mProvidersByClassPerUser.valueAt(i2),
                    arrayList)) {
                if (!z) {
                    return true;
                }
                z3 = true;
            }
        }
        return z3;
    }

    public final void dumpProvider(
            FileDescriptor fileDescriptor,
            PrintWriter printWriter,
            ContentProviderRecord contentProviderRecord,
            String[] strArr,
            boolean z) {
        ProcessRecord processRecord = contentProviderRecord.proc;
        IApplicationThread iApplicationThread =
                processRecord != null ? processRecord.mThread : null;
        for (String str : strArr) {
            if (!z && str.contains("--proto")) {
                if (iApplicationThread != null) {
                    dumpToTransferPipe(
                            null,
                            fileDescriptor,
                            printWriter,
                            contentProviderRecord,
                            iApplicationThread,
                            strArr);
                    return;
                }
                return;
            }
        }
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                printWriter.print("");
                printWriter.print("PROVIDER ");
                printWriter.print(contentProviderRecord);
                printWriter.print(" pid=");
                ProcessRecord processRecord2 = contentProviderRecord.proc;
                if (processRecord2 != null) {
                    printWriter.println(processRecord2.mPid);
                } else {
                    printWriter.println("(not running)");
                }
                if (z) {
                    contentProviderRecord.dump(printWriter, "  ", true);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        if (iApplicationThread != null) {
            printWriter.println("    Client:");
            printWriter.flush();
            dumpToTransferPipe(
                    "      ",
                    fileDescriptor,
                    printWriter,
                    contentProviderRecord,
                    iApplicationThread,
                    strArr);
        }
    }

    public final ContentProviderRecord getProviderByClass(int i, ComponentName componentName) {
        ContentProviderRecord contentProviderRecord =
                (ContentProviderRecord) this.mSingletonByClass.get(componentName);
        return contentProviderRecord != null
                ? contentProviderRecord
                : (ContentProviderRecord) getProvidersByClass(i).get(componentName);
    }

    public final ContentProviderRecord getProviderByName(int i, String str) {
        ContentProviderRecord contentProviderRecord =
                (ContentProviderRecord) this.mSingletonByName.get(str);
        return contentProviderRecord != null
                ? contentProviderRecord
                : (ContentProviderRecord) getProvidersByName(i).get(str);
    }

    public final HashMap getProvidersByClass(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Bad user "));
        }
        HashMap hashMap = (HashMap) this.mProvidersByClassPerUser.get(i);
        if (hashMap != null) {
            return hashMap;
        }
        HashMap hashMap2 = new HashMap();
        this.mProvidersByClassPerUser.put(i, hashMap2);
        return hashMap2;
    }

    public final HashMap getProvidersByName(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Bad user "));
        }
        HashMap hashMap = (HashMap) this.mProvidersByNamePerUser.get(i);
        if (hashMap != null) {
            return hashMap;
        }
        HashMap hashMap2 = new HashMap();
        this.mProvidersByNamePerUser.put(i, hashMap2);
        return hashMap2;
    }

    public final ArrayList getProvidersForName(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Predicate filterRecord = DumpUtils.filterRecord(str);
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                arrayList.addAll(this.mSingletonByClass.values());
                for (int i = 0; i < this.mProvidersByClassPerUser.size(); i++) {
                    arrayList.addAll(((HashMap) this.mProvidersByClassPerUser.valueAt(i)).values());
                }
                CollectionUtils.addIf(arrayList, arrayList2, filterRecord);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        arrayList2.sort(Comparator.comparing(new ProviderMap$$ExternalSyntheticLambda0()));
        return arrayList2;
    }

    public final void putProviderByClass(
            ComponentName componentName, ContentProviderRecord contentProviderRecord) {
        if (contentProviderRecord.singleton) {
            this.mSingletonByClass.put(componentName, contentProviderRecord);
        } else {
            getProvidersByClass(UserHandle.getUserId(contentProviderRecord.appInfo.uid))
                    .put(componentName, contentProviderRecord);
        }
    }
}
