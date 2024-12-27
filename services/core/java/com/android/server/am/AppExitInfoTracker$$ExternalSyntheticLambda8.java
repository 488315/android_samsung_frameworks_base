package com.android.server.am;

import android.app.ApplicationExitInfo;
import android.util.ArraySet;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.server.am.AppExitInfoTracker;
import java.io.File;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppExitInfoTracker$$ExternalSyntheticLambda8 implements BiFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AppExitInfoTracker$$ExternalSyntheticLambda8(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Object obj3 = this.f$0;
        switch (i) {
            case 0:
                ProtoOutputStream protoOutputStream = (ProtoOutputStream) obj3;
                SparseArray sparseArray = (SparseArray) obj2;
                long start = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1138166333441L, (String) obj);
                int size = sparseArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer = (AppExitInfoTracker.AppExitInfoContainer) sparseArray.valueAt(i2);
                    appExitInfoContainer.getClass();
                    long start2 = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1120986464257L, appExitInfoContainer.mUid);
                    for (int i3 = 0; i3 < appExitInfoContainer.mInfos.size(); i3++) {
                        ((ApplicationExitInfo) appExitInfoContainer.mInfos.valueAt(i3)).writeToProto(protoOutputStream, 2246267895810L);
                    }
                    for (int i4 = 0; i4 < appExitInfoContainer.mRecoverableCrashes.size(); i4++) {
                        ((ApplicationExitInfo) appExitInfoContainer.mRecoverableCrashes.valueAt(i4)).writeToProto(protoOutputStream, 2246267895811L);
                    }
                    protoOutputStream.end(start2);
                }
                protoOutputStream.end(start);
                break;
            default:
                ArraySet arraySet = (ArraySet) obj3;
                SparseArray sparseArray2 = (SparseArray) obj2;
                for (int size2 = sparseArray2.size() - 1; size2 >= 0; size2--) {
                    AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer2 = (AppExitInfoTracker.AppExitInfoContainer) sparseArray2.valueAt(size2);
                    for (int size3 = appExitInfoContainer2.mInfos.size() - 1; size3 >= 0; size3--) {
                        appExitInfoContainer2.mInfos.keyAt(size3);
                        File traceFile = ((ApplicationExitInfo) appExitInfoContainer2.mInfos.valueAt(size3)).getTraceFile();
                        if (traceFile != null) {
                            arraySet.remove(traceFile.getName());
                        }
                    }
                    for (int size4 = appExitInfoContainer2.mRecoverableCrashes.size() - 1; size4 >= 0; size4--) {
                        appExitInfoContainer2.mRecoverableCrashes.keyAt(size4);
                        File traceFile2 = ((ApplicationExitInfo) appExitInfoContainer2.mRecoverableCrashes.valueAt(size4)).getTraceFile();
                        if (traceFile2 != null) {
                            arraySet.remove(traceFile2.getName());
                        }
                    }
                }
                break;
        }
        return 0;
    }
}
