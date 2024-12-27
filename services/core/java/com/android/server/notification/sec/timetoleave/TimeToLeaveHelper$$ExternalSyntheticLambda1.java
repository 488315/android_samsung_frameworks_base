package com.android.server.notification.sec.timetoleave;

import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.BatchResultCallback;
import android.util.Log;

import java.util.concurrent.CompletableFuture;

public final /* synthetic */ class TimeToLeaveHelper$$ExternalSyntheticLambda1
        implements BatchResultCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TimeToLeaveHelper f$0;
    public final /* synthetic */ CompletableFuture f$1;

    public /* synthetic */ TimeToLeaveHelper$$ExternalSyntheticLambda1(
            TimeToLeaveHelper timeToLeaveHelper, CompletableFuture completableFuture, int i) {
        this.$r8$classId = i;
        this.f$0 = timeToLeaveHelper;
        this.f$1 = completableFuture;
    }

    @Override // android.app.appsearch.BatchResultCallback
    public final void onResult(AppSearchBatchResult appSearchBatchResult) {
        switch (this.$r8$classId) {
            case 0:
                TimeToLeaveHelper timeToLeaveHelper = this.f$0;
                CompletableFuture completableFuture = this.f$1;
                timeToLeaveHelper.getClass();
                completableFuture.complete(null);
                Log.d("TimeToLeaveHelper", "succeeded to remove documents");
                break;
            default:
                TimeToLeaveHelper timeToLeaveHelper2 = this.f$0;
                CompletableFuture completableFuture2 = this.f$1;
                timeToLeaveHelper2.getClass();
                completableFuture2.complete(null);
                Log.d("TimeToLeaveHelper", "succeeded to put documents");
                break;
        }
    }
}
