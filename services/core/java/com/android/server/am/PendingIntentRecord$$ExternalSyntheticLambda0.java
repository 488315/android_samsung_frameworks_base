package com.android.server.am;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public final /* synthetic */ class PendingIntentRecord$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PendingIntentRecord pendingIntentRecord = (PendingIntentRecord) obj;
        synchronized (pendingIntentRecord.controller.mLock) {
            try {
                if (((WeakReference)
                                pendingIntentRecord.controller.mIntentSenderRecords.get(
                                        pendingIntentRecord.key))
                        == pendingIntentRecord.ref) {
                    pendingIntentRecord.controller.mIntentSenderRecords.remove(
                            pendingIntentRecord.key);
                    pendingIntentRecord.controller.decrementUidStatLocked(pendingIntentRecord);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
