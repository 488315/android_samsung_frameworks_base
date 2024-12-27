package com.android.server.wm;

import android.app.BackgroundStartPrivileges;
import android.util.ArrayMap;
import android.util.IntArray;

import com.android.server.notification.NotificationManagerService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BackgroundLaunchProcessController {
    public final NotificationManagerService.AnonymousClass2 mBackgroundActivityStartCallback;
    public ArrayMap mBackgroundStartPrivileges;
    public IntArray mBalOptInBoundClientUids;
    public final IntPredicate mUidHasActiveVisibleWindowPredicate;

    public BackgroundLaunchProcessController(
            WindowProcessController$$ExternalSyntheticLambda4
                    windowProcessController$$ExternalSyntheticLambda4,
            NotificationManagerService.AnonymousClass2 anonymousClass2) {
        this.mUidHasActiveVisibleWindowPredicate =
                windowProcessController$$ExternalSyntheticLambda4;
        this.mBackgroundActivityStartCallback = anonymousClass2;
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this) {
            try {
                ArrayMap arrayMap = this.mBackgroundStartPrivileges;
                if (arrayMap != null && !arrayMap.isEmpty()) {
                    printWriter.print("    ");
                    printWriter.println(
                            "Background activity start tokens (token: originating token):");
                    for (int size = this.mBackgroundStartPrivileges.size() - 1; size >= 0; size--) {
                        printWriter.print("    ");
                        printWriter.print("  - ");
                        printWriter.print(this.mBackgroundStartPrivileges.keyAt(size));
                        printWriter.print(": ");
                        printWriter.println(this.mBackgroundStartPrivileges.valueAt(size));
                    }
                }
                IntArray intArray = this.mBalOptInBoundClientUids;
                if (intArray != null && intArray.size() > 0) {
                    printWriter.print("    ");
                    printWriter.print("BoundClientUids:");
                    printWriter.println(Arrays.toString(this.mBalOptInBoundClientUids.toArray()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getOriginatingTokensThatAllowBal() {
        ArrayList arrayList = new ArrayList();
        int size = this.mBackgroundStartPrivileges.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return arrayList;
            }
            BackgroundStartPrivileges backgroundStartPrivileges =
                    (BackgroundStartPrivileges) this.mBackgroundStartPrivileges.valueAt(i);
            if (backgroundStartPrivileges.allowsBackgroundActivityStarts()) {
                arrayList.add(backgroundStartPrivileges.getOriginatingToken());
            }
            size = i;
        }
    }

    public final boolean isBoundByForegroundUid() {
        synchronized (this) {
            try {
                IntArray intArray = this.mBalOptInBoundClientUids;
                if (intArray != null) {
                    for (int size = intArray.size() - 1; size >= 0; size--) {
                        if (this.mUidHasActiveVisibleWindowPredicate.test(
                                this.mBalOptInBoundClientUids.get(size))) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
