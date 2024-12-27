package com.android.server.am;

import android.util.ArraySet;

import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProcessReceiverRecord {
    public int mCurReceiversSize;
    public final ActivityManagerService mService;
    public final ArraySet mCurReceivers = new ArraySet();
    public final ArraySet mReceivers = new ArraySet();

    public ProcessReceiverRecord(ProcessRecord processRecord) {
        this.mService = processRecord.mService;
    }

    public final void dumpReceivers(PrintWriter printWriter) {
        if (this.mReceivers.size() > 0) {
            ArrayList arrayList = new ArrayList();
            int size = this.mReceivers.size();
            for (int i = 0; i < size; i++) {
                arrayList.add(((ReceiverList) this.mReceivers.valueAt(i)).toString());
            }
            arrayList.sort(new ProcessReceiverRecord$$ExternalSyntheticLambda0());
            printWriter.println("Receiver list:");
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print("    #" + i2 + ":");
                printWriter.println((String) arrayList.get(i2));
            }
        }
    }
}
