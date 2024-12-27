package com.android.server.location.contexthub;

import android.hardware.contexthub.MessageDeliveryStatus;
import android.util.Log;

import java.util.List;

public final /* synthetic */
class IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback
            f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */
    IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda0(
            IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback contextHubAidlCallback,
            Object obj,
            int i) {
        this.$r8$classId = i;
        this.f$0 = contextHubAidlCallback;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mCallback.handleNanoappInfo((List) this.f$1);
                return;
            default:
                IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback
                        contextHubAidlCallback = this.f$0;
                MessageDeliveryStatus messageDeliveryStatus = (MessageDeliveryStatus) this.f$1;
                ContextHubTransactionManager contextHubTransactionManager =
                        ContextHubService.this.mTransactionManager;
                int i = messageDeliveryStatus.messageSequenceNumber;
                int i2 = 0;
                boolean z = messageDeliveryStatus.errorCode == 0;
                synchronized (contextHubTransactionManager) {
                    ContextHubServiceTransaction contextHubServiceTransaction =
                            (ContextHubServiceTransaction)
                                    contextHubTransactionManager.mTransactionQueue.peek();
                    if (contextHubServiceTransaction == null) {
                        Log.w(
                                "ContextHubTransactionManager",
                                "Received unexpected transaction response (no transaction"
                                    + " pending)");
                        return;
                    }
                    Integer num = contextHubServiceTransaction.mMessageSequenceNumber;
                    if (contextHubServiceTransaction.mTransactionType == 5
                            && num != null
                            && num.intValue() == i) {
                        if (!z) {
                            i2 = 5;
                        }
                        contextHubServiceTransaction.onTransactionComplete(i2);
                        contextHubTransactionManager.removeTransactionAndStartNext();
                        return;
                    }
                    Log.w(
                            "ContextHubTransactionManager",
                            "Received unexpected message transaction response (expected message"
                                + " sequence number = "
                                    + contextHubServiceTransaction.mMessageSequenceNumber
                                    + ", received messageSequenceNumber = "
                                    + i
                                    + ")");
                    return;
                }
        }
    }
}
