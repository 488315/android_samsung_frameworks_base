package com.android.server.am;

import android.app.BackgroundStartPrivileges;
import android.os.PowerExemptionManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;

import java.util.ArrayList;
import java.util.function.Function;

public final /* synthetic */ class ActiveServices$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActiveServices f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ ArraySet f$2;

    public /* synthetic */ ActiveServices$$ExternalSyntheticLambda2(
            ActiveServices activeServices, int i, ArraySet arraySet, int i2) {
        this.$r8$classId = i2;
        this.f$0 = activeServices;
        this.f$1 = i;
        this.f$2 = arraySet;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i;
        ArrayList arrayList;
        int i2;
        switch (this.$r8$classId) {
            case 0:
                ActiveServices activeServices = this.f$0;
                int i3 = this.f$1;
                ArraySet arraySet = this.f$2;
                ProcessRecord processRecord = (ProcessRecord) obj;
                activeServices.getClass();
                if (processRecord.uid == i3) {
                    ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    int size = processServiceRecord.mServices.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        ArrayMap arrayMap =
                                ((ServiceRecord) processServiceRecord.mServices.valueAt(i4))
                                        .connections;
                        int size2 = arrayMap.size();
                        int i5 = 0;
                        while (i5 < size2) {
                            ArrayList arrayList2 = (ArrayList) arrayMap.valueAt(i5);
                            int i6 = 0;
                            while (i6 < arrayList2.size()) {
                                ConnectionRecord connectionRecord =
                                        (ConnectionRecord) arrayList2.get(i6);
                                ProcessRecord processRecord2 = connectionRecord.binding.client;
                                if (!processRecord2.mPersistent) {
                                    int i7 = processRecord2.mPid;
                                    int i8 = processRecord2.uid;
                                    if (i8 != i3 && !arraySet.contains(Integer.valueOf(i8))) {
                                        String str = connectionRecord.clientPackageName;
                                        BackgroundStartPrivileges backgroundStartPrivileges =
                                                BackgroundStartPrivileges.NONE;
                                        i = i6;
                                        arrayList = arrayList2;
                                        i2 = i5;
                                        int shouldAllowFgsStartForegroundNoBindingCheckLocked =
                                                activeServices
                                                        .shouldAllowFgsStartForegroundNoBindingCheckLocked(
                                                                activeServices
                                                                        .shouldAllowFgsWhileInUsePermissionLocked(
                                                                                str,
                                                                                i7,
                                                                                i8,
                                                                                null,
                                                                                backgroundStartPrivileges),
                                                                i7,
                                                                i8,
                                                                str,
                                                                null,
                                                                backgroundStartPrivileges);
                                        if (shouldAllowFgsStartForegroundNoBindingCheckLocked
                                                != -1) {
                                            return new Pair(
                                                    Integer.valueOf(
                                                            shouldAllowFgsStartForegroundNoBindingCheckLocked),
                                                    str);
                                        }
                                        arraySet.add(Integer.valueOf(i8));
                                        i6 = i + 1;
                                        arrayList2 = arrayList;
                                        i5 = i2;
                                    }
                                }
                                i = i6;
                                arrayList = arrayList2;
                                i2 = i5;
                                i6 = i + 1;
                                arrayList2 = arrayList;
                                i5 = i2;
                            }
                            i5++;
                        }
                    }
                }
                return null;
            default:
                ActiveServices activeServices2 = this.f$0;
                int i9 = this.f$1;
                ArraySet arraySet2 = this.f$2;
                ProcessRecord processRecord3 = (ProcessRecord) obj;
                activeServices2.getClass();
                if (processRecord3.uid == i9) {
                    ProcessServiceRecord processServiceRecord2 = processRecord3.mServices;
                    int size3 = processServiceRecord2.mServices.size();
                    for (int i10 = 0; i10 < size3; i10++) {
                        ArrayMap arrayMap2 =
                                ((ServiceRecord) processServiceRecord2.mServices.valueAt(i10))
                                        .connections;
                        int size4 = arrayMap2.size();
                        for (int i11 = 0; i11 < size4; i11++) {
                            ArrayList arrayList3 = (ArrayList) arrayMap2.valueAt(i11);
                            for (int i12 = 0; i12 < arrayList3.size(); i12++) {
                                ConnectionRecord connectionRecord2 =
                                        (ConnectionRecord) arrayList3.get(i12);
                                int i13 = connectionRecord2.binding.client.uid;
                                if (i13 != i9 && !arraySet2.contains(Integer.valueOf(i13))) {
                                    int uidProcStateLOSP =
                                            activeServices2.mAm.mProcessList.getUidProcStateLOSP(
                                                    i9);
                                    boolean z = uidProcStateLOSP == 2;
                                    boolean z2 =
                                            uidProcStateLOSP < 2
                                                    && connectionRecord2.hasFlag(1048576);
                                    if (z || z2) {
                                        return Integer.valueOf(
                                                PowerExemptionManager.getReasonCodeFromProcState(
                                                        uidProcStateLOSP));
                                    }
                                    arraySet2.add(Integer.valueOf(i13));
                                }
                            }
                        }
                    }
                }
                return null;
        }
    }
}
