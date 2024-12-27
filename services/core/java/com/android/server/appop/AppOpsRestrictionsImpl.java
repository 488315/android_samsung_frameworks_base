package com.android.server.appop;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Handler;
import android.os.PackageTagsList;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppOpsRestrictionsImpl implements AppOpsRestrictions {
    public final AppOpsService$$ExternalSyntheticLambda6 mAppOpsRestrictionRemovedListener;
    public final Context mContext;
    public final Handler mHandler;
    public final ArrayMap mGlobalRestrictions = new ArrayMap();
    public final ArrayMap mUserRestrictions = new ArrayMap();
    public final ArrayMap mUserRestrictionExcludedPackageTags = new ArrayMap();

    public AppOpsRestrictionsImpl(
            Context context,
            Handler handler,
            AppOpsService$$ExternalSyntheticLambda6 appOpsService$$ExternalSyntheticLambda6) {
        this.mContext = context;
        this.mHandler = handler;
        this.mAppOpsRestrictionRemovedListener = appOpsService$$ExternalSyntheticLambda6;
    }

    public final boolean clearUserRestrictions(Object obj) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        SparseArray sparseArray = (SparseArray) this.mUserRestrictions.get(obj);
        if (sparseArray != null) {
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                SparseBooleanArray sparseBooleanArray2 =
                        (SparseBooleanArray) sparseArray.valueAt(i);
                int size2 = sparseBooleanArray2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    sparseBooleanArray.put(sparseBooleanArray2.keyAt(i2), true);
                }
            }
        }
        boolean z =
                (this.mUserRestrictions.remove(obj) != null)
                        | (this.mUserRestrictionExcludedPackageTags.remove(obj) != null);
        int size3 = sparseBooleanArray.size();
        for (int i3 = 0; i3 < size3; i3++) {
            final int keyAt = sparseBooleanArray.keyAt(i3);
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.appop.AppOpsRestrictionsImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppOpsRestrictionsImpl appOpsRestrictionsImpl =
                                    AppOpsRestrictionsImpl.this;
                            appOpsRestrictionsImpl.mAppOpsRestrictionRemovedListener.f$0
                                    .notifyWatchersOnDefaultDevice(keyAt, -2);
                        }
                    });
        }
        return z;
    }

    public final void dumpRestrictions(PrintWriter printWriter, String str, int i) {
        boolean z;
        String str2;
        int i2;
        String str3;
        boolean z2;
        int i3;
        String str4;
        SparseArray sparseArray;
        int i4;
        AppOpsRestrictionsImpl appOpsRestrictionsImpl = this;
        PrintWriter printWriter2 = printWriter;
        int size = appOpsRestrictionsImpl.mGlobalRestrictions.size();
        int i5 = 0;
        while (true) {
            z = true;
            str2 = "[";
            if (i5 >= size) {
                break;
            }
            Object keyAt = appOpsRestrictionsImpl.mGlobalRestrictions.keyAt(i5);
            SparseBooleanArray sparseBooleanArray =
                    (SparseBooleanArray) appOpsRestrictionsImpl.mGlobalRestrictions.valueAt(i5);
            printWriter2.println("  Global restrictions for token " + keyAt + ":");
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            int size2 = sparseBooleanArray.size();
            for (int i6 = 0; i6 < size2; i6++) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append(AppOpsManager.opToName(sparseBooleanArray.keyAt(i6)));
            }
            sb.append("]");
            printWriter2.println("      Restricted ops: " + ((Object) sb));
            i5++;
        }
        int size3 = appOpsRestrictionsImpl.mUserRestrictions.size();
        int i7 = 0;
        while (i7 < size3) {
            Object keyAt2 = appOpsRestrictionsImpl.mUserRestrictions.keyAt(i7);
            SparseArray sparseArray2 =
                    (SparseArray) appOpsRestrictionsImpl.mUserRestrictions.get(keyAt2);
            SparseArray sparseArray3 =
                    (SparseArray)
                            appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.get(keyAt2);
            int size4 = sparseArray2 != null ? sparseArray2.size() : 0;
            if (size4 <= 0 || str != null) {
                i2 = size3;
                str3 = str2;
                z2 = false;
            } else {
                int i8 = 0;
                z2 = false;
                boolean z3 = false;
                while (i8 < size4) {
                    int keyAt3 = sparseArray2.keyAt(i8);
                    int i9 = size3;
                    SparseBooleanArray sparseBooleanArray2 =
                            (SparseBooleanArray) sparseArray2.valueAt(i8);
                    if (sparseBooleanArray2 != null && (i < 0 || sparseBooleanArray2.get(i))) {
                        sparseArray = sparseArray2;
                        if (z2) {
                            i4 = size4;
                        } else {
                            i4 = size4;
                            printWriter2.println("  User restrictions for token " + keyAt2 + ":");
                            z2 = true;
                        }
                        if (!z3) {
                            printWriter2.println("      Restricted ops:");
                            z3 = true;
                        }
                        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str2);
                        int size5 = sparseBooleanArray2.size();
                        str4 = str2;
                        int i10 = 0;
                        while (i10 < size5) {
                            int keyAt4 = sparseBooleanArray2.keyAt(i10);
                            SparseBooleanArray sparseBooleanArray3 = sparseBooleanArray2;
                            int i11 = size5;
                            if (m.length() > 1) {
                                m.append(", ");
                            }
                            m.append(AppOpsManager.opToName(keyAt4));
                            i10++;
                            sparseBooleanArray2 = sparseBooleanArray3;
                            size5 = i11;
                        }
                        m.append("]");
                        printWriter2.print("        ");
                        printWriter2.print("user: ");
                        printWriter2.print(keyAt3);
                        printWriter2.print(" restricted ops: ");
                        printWriter2.println(m);
                    } else {
                        str4 = str2;
                        sparseArray = sparseArray2;
                        i4 = size4;
                    }
                    i8++;
                    sparseArray2 = sparseArray;
                    size3 = i9;
                    size4 = i4;
                    str2 = str4;
                }
                i2 = size3;
                str3 = str2;
            }
            int size6 = sparseArray3 != null ? sparseArray3.size() : 0;
            if (size6 > 0 && i < 0) {
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter2);
                indentingPrintWriter.increaseIndent();
                int i12 = 0;
                boolean z4 = false;
                while (i12 < size6) {
                    int keyAt5 = sparseArray3.keyAt(i12);
                    PackageTagsList packageTagsList = (PackageTagsList) sparseArray3.valueAt(i12);
                    if (packageTagsList != null) {
                        if (str != null ? packageTagsList.includes(str) : true) {
                            i3 = size6;
                            if (!z2) {
                                indentingPrintWriter.println(
                                        "User restrictions for token " + keyAt2 + ":");
                                z2 = true;
                            }
                            indentingPrintWriter.increaseIndent();
                            if (!z4) {
                                indentingPrintWriter.println("Excluded packages:");
                                z4 = true;
                            }
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.print("user: ");
                            indentingPrintWriter.print(keyAt5);
                            indentingPrintWriter.println(" packages: ");
                            indentingPrintWriter.increaseIndent();
                            packageTagsList.dump(indentingPrintWriter);
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.decreaseIndent();
                            i12++;
                            size6 = i3;
                        }
                    }
                    i3 = size6;
                    i12++;
                    size6 = i3;
                }
                indentingPrintWriter.decreaseIndent();
            }
            i7++;
            appOpsRestrictionsImpl = this;
            printWriter2 = printWriter;
            size3 = i2;
            str2 = str3;
            z = true;
        }
    }
}
