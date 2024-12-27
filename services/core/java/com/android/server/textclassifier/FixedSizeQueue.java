package com.android.server.textclassifier;

import com.android.internal.util.Preconditions;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticLambda10;

import java.util.ArrayDeque;
import java.util.Queue;

public final class FixedSizeQueue {
    public final Queue mDelegate;
    public final int mMaxSize;
    public final VcnManagementService$$ExternalSyntheticLambda10 mOnEntryEvictedListener;

    public FixedSizeQueue(
            VcnManagementService$$ExternalSyntheticLambda10
                    vcnManagementService$$ExternalSyntheticLambda10) {
        Preconditions.checkArgument(true, "maxSize (%s) must > 0", new Object[] {20});
        this.mDelegate = new ArrayDeque(20);
        this.mMaxSize = 20;
        this.mOnEntryEvictedListener = vcnManagementService$$ExternalSyntheticLambda10;
    }

    public final void add(TextClassificationManagerService.PendingRequest pendingRequest) {
        if (((ArrayDeque) this.mDelegate).size() == this.mMaxSize) {
            Object remove = ((ArrayDeque) this.mDelegate).remove();
            if (this.mOnEntryEvictedListener != null) {
                TextClassificationManagerService.PendingRequest pendingRequest2 =
                        (TextClassificationManagerService.PendingRequest) remove;
                PinnerService$$ExternalSyntheticOutline0.m(
                        "Pending request[",
                        pendingRequest2.mName,
                        "] is dropped",
                        "TextClassificationManagerService");
                pendingRequest2.mOnServiceFailure.run();
            }
        }
        ((ArrayDeque) this.mDelegate).add(pendingRequest);
    }
}
