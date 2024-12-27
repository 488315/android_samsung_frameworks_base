package com.android.server.notification.sec.timetoleave;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.AppSearchSession;
import android.util.Log;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TimeToLeaveHelper$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TimeToLeaveHelper f$0;
    public final /* synthetic */ CompletableFuture f$1;

    public /* synthetic */ TimeToLeaveHelper$$ExternalSyntheticLambda0(
            TimeToLeaveHelper timeToLeaveHelper, CompletableFuture completableFuture, int i) {
        this.$r8$classId = i;
        this.f$0 = timeToLeaveHelper;
        this.f$1 = completableFuture;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                TimeToLeaveHelper timeToLeaveHelper = this.f$0;
                CompletableFuture completableFuture = this.f$1;
                AppSearchResult appSearchResult = (AppSearchResult) obj;
                timeToLeaveHelper.getClass();
                if (!appSearchResult.isSuccess()) {
                    completableFuture.completeExceptionally(
                            new IllegalStateException(appSearchResult.getErrorMessage()));
                    Log.d("TimeToLeaveHelper", "Failed to create AppSearchSession");
                    break;
                } else if (appSearchResult.getResultValue() != null) {
                    completableFuture.complete((AppSearchSession) appSearchResult.getResultValue());
                    Log.d("TimeToLeaveHelper", "Succeeded to create AppSearchSession");
                    break;
                }
                break;
            case 1:
                TimeToLeaveHelper timeToLeaveHelper2 = this.f$0;
                CompletableFuture completableFuture2 = this.f$1;
                timeToLeaveHelper2.getClass();
                completableFuture2.complete(null);
                Log.d("TimeToLeaveHelper", "succeeded to clear documents");
                break;
            default:
                TimeToLeaveHelper timeToLeaveHelper3 = this.f$0;
                CompletableFuture completableFuture3 = this.f$1;
                timeToLeaveHelper3.getClass();
                completableFuture3.complete(null);
                Log.d("TimeToLeaveHelper", "Succeeded to set Schema");
                break;
        }
    }
}
