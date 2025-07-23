package com.android.internal.infra;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.IInterface;
import android.util.Slog;
import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.infra.AbstractSinglePendingRequestRemoteService;
import java.io.PrintWriter;

@Deprecated
/* loaded from: classes5.dex */
public abstract class AbstractSinglePendingRequestRemoteService<S extends AbstractSinglePendingRequestRemoteService<S, I>, I extends IInterface> extends AbstractRemoteService<S, I> {
    protected AbstractRemoteService.BasePendingRequest<S, I> mPendingRequest;

    public AbstractSinglePendingRequestRemoteService(Context context, String str, ComponentName componentName, int i, AbstractRemoteService.VultureCallback<S> vultureCallback, Handler handler, int i2, boolean z) {
        super(context, str, componentName, i, vultureCallback, handler, i2, z);
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handlePendingRequests() {
        AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest = this.mPendingRequest;
        if (basePendingRequest != null) {
            this.mPendingRequest = null;
            handlePendingRequest(basePendingRequest);
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    protected void handleOnDestroy() {
        AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest = this.mPendingRequest;
        if (basePendingRequest != null) {
            basePendingRequest.cancel();
            this.mPendingRequest = null;
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handleBindFailure() {
        if (this.mPendingRequest != null) {
            if (this.mVerbose) {
                Slog.v(this.mTag, "Sending failure to " + this.mPendingRequest);
            }
            this.mPendingRequest.onFailed();
            this.mPendingRequest = null;
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    public void dump(String str, PrintWriter printWriter) {
        super.dump(str, printWriter);
        printWriter.append((CharSequence) str).append("hasPendingRequest=").append((CharSequence) String.valueOf(this.mPendingRequest != null)).println();
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handlePendingRequestWhileUnBound(AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        if (this.mPendingRequest != null) {
            if (this.mVerbose) {
                Slog.v(this.mTag, "handlePendingRequestWhileUnBound(): cancelling " + this.mPendingRequest + " to handle " + basePendingRequest);
            }
            this.mPendingRequest.cancel();
        }
        this.mPendingRequest = basePendingRequest;
    }
}
