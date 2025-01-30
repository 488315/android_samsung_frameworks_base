package com.android.systemui.screenshot;

import com.android.systemui.screenshot.IOnDoneCallback;
import kotlin.Unit;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.JobSupportKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActionIntentExecutor$dismissKeyguard$onDoneBinder$1 extends IOnDoneCallback.Stub {
    public final /* synthetic */ CompletableDeferred $completion;

    public ActionIntentExecutor$dismissKeyguard$onDoneBinder$1(CompletableDeferred completableDeferred) {
        this.$completion = completableDeferred;
    }

    @Override // com.android.systemui.screenshot.IOnDoneCallback
    public final void onDone(boolean z) {
        Object tryMakeCompleting;
        CompletableDeferred completableDeferred = this.$completion;
        Unit unit = Unit.INSTANCE;
        CompletableDeferredImpl completableDeferredImpl = (CompletableDeferredImpl) completableDeferred;
        do {
            tryMakeCompleting = completableDeferredImpl.tryMakeCompleting(completableDeferredImpl.m293x8adbf455(), unit);
            if (tryMakeCompleting == JobSupportKt.COMPLETING_ALREADY || tryMakeCompleting == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
                return;
            }
        } while (tryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
    }
}
