package com.android.server.appfunctions;

import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.AppSearchSession;
import android.app.appsearch.BatchResultCallback;
import android.app.appsearch.GetByDocumentIdRequest;
import android.app.appsearch.PutDocumentsRequest;
import android.app.appsearch.RemoveByDocumentIdRequest;
import android.app.appsearch.SearchSpec;
import android.app.appsearch.SetSchemaRequest;

import com.android.internal.infra.AndroidFuture;

import java.util.concurrent.Executor;
import java.util.function.Function;

public final /* synthetic */ class FutureAppSearchSessionImpl$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ FutureAppSearchSessionImpl$$ExternalSyntheticLambda0(
            SearchSpec searchSpec) {
        this.$r8$classId = 3;
        this.f$0 = "";
        this.f$1 = searchSpec;
    }

    public /* synthetic */ FutureAppSearchSessionImpl$$ExternalSyntheticLambda0(
            FutureAppSearchSessionImpl futureAppSearchSessionImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = futureAppSearchSessionImpl;
        this.f$1 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                FutureAppSearchSessionImpl futureAppSearchSessionImpl =
                        (FutureAppSearchSessionImpl) this.f$0;
                SetSchemaRequest setSchemaRequest = (SetSchemaRequest) this.f$1;
                futureAppSearchSessionImpl.getClass();
                AndroidFuture androidFuture = new AndroidFuture();
                Executor executor = futureAppSearchSessionImpl.mExecutor;
                ((AppSearchSession) obj)
                        .setSchema(
                                setSchemaRequest,
                                executor,
                                executor,
                                new FutureAppSearchSessionImpl$$ExternalSyntheticLambda7(
                                        androidFuture));
                return androidFuture.thenApply(
                        new FutureAppSearchSessionImpl$$ExternalSyntheticLambda8(0));
            case 1:
                FutureAppSearchSessionImpl futureAppSearchSessionImpl2 =
                        (FutureAppSearchSessionImpl) this.f$0;
                PutDocumentsRequest putDocumentsRequest = (PutDocumentsRequest) this.f$1;
                futureAppSearchSessionImpl2.getClass();
                final AndroidFuture androidFuture2 = new AndroidFuture();
                ((AppSearchSession) obj)
                        .put(
                                putDocumentsRequest,
                                futureAppSearchSessionImpl2.mExecutor,
                                new BatchResultCallback() { // from class:
                                                            // com.android.server.appfunctions.FutureAppSearchSessionImpl$$ExternalSyntheticLambda9
                                    @Override // android.app.appsearch.BatchResultCallback
                                    public final void onResult(
                                            AppSearchBatchResult appSearchBatchResult) {
                                        androidFuture2.complete(appSearchBatchResult);
                                    }
                                });
                return androidFuture2;
            case 2:
                FutureAppSearchSessionImpl futureAppSearchSessionImpl3 =
                        (FutureAppSearchSessionImpl) this.f$0;
                RemoveByDocumentIdRequest removeByDocumentIdRequest =
                        (RemoveByDocumentIdRequest) this.f$1;
                futureAppSearchSessionImpl3.getClass();
                AndroidFuture androidFuture3 = new AndroidFuture();
                ((AppSearchSession) obj)
                        .remove(
                                removeByDocumentIdRequest,
                                futureAppSearchSessionImpl3.mExecutor,
                                new FutureAppSearchSessionImpl.BatchResultCallbackAdapter(
                                        androidFuture3));
                return androidFuture3;
            case 3:
                return ((AppSearchSession) obj).search((String) this.f$0, (SearchSpec) this.f$1);
            default:
                FutureAppSearchSessionImpl futureAppSearchSessionImpl4 =
                        (FutureAppSearchSessionImpl) this.f$0;
                GetByDocumentIdRequest getByDocumentIdRequest = (GetByDocumentIdRequest) this.f$1;
                futureAppSearchSessionImpl4.getClass();
                AndroidFuture androidFuture4 = new AndroidFuture();
                ((AppSearchSession) obj)
                        .getByDocumentId(
                                getByDocumentIdRequest,
                                futureAppSearchSessionImpl4.mExecutor,
                                new FutureAppSearchSessionImpl.BatchResultCallbackAdapter(
                                        androidFuture4));
                return androidFuture4;
        }
    }
}
