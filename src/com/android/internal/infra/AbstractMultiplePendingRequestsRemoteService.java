package com.android.internal.infra;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.IInterface;
import android.util.Slog;
import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes5.dex */
public abstract class AbstractMultiplePendingRequestsRemoteService<S extends AbstractMultiplePendingRequestsRemoteService<S, I>, I extends IInterface> extends AbstractRemoteService<S, I> {
    private final int mInitialCapacity;
    protected List<AbstractRemoteService.BasePendingRequest<S, I>> mPendingRequests;

    public AbstractMultiplePendingRequestsRemoteService(Context context, String str, ComponentName componentName, int i, AbstractRemoteService.VultureCallback<S> vultureCallback, Handler handler, int i2, boolean z, int i3) {
        super(context, str, componentName, i, vultureCallback, handler, i2, z);
        this.mInitialCapacity = i3;
        this.mPendingRequests = new ArrayList(i3);
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handlePendingRequests() {
        synchronized (this.mPendingRequests) {
            int size = this.mPendingRequests.size();
            if (this.mVerbose) {
                Slog.v(this.mTag, "Sending " + size + " pending requests");
            }
            for (int i = 0; i < size; i++) {
                handlePendingRequest(this.mPendingRequests.get(i));
            }
            this.mPendingRequests.clear();
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    protected void handleOnDestroy() {
        synchronized (this.mPendingRequests) {
            int size = this.mPendingRequests.size();
            if (this.mVerbose) {
                Slog.v(this.mTag, "Canceling " + size + " pending requests");
            }
            for (int i = 0; i < size; i++) {
                this.mPendingRequests.get(i).cancel();
            }
            this.mPendingRequests.clear();
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    final void handleBindFailure() {
        synchronized (this.mPendingRequests) {
            int size = this.mPendingRequests.size();
            if (this.mVerbose) {
                Slog.v(this.mTag, "Sending failure to " + size + " pending requests");
            }
            for (int i = 0; i < size; i++) {
                AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest = this.mPendingRequests.get(i);
                basePendingRequest.onFailed();
                basePendingRequest.finish();
            }
            this.mPendingRequests.clear();
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    public void dump(String str, PrintWriter printWriter) {
        int size;
        super.dump(str, printWriter);
        printWriter.append((CharSequence) str).append("initialCapacity=").append((CharSequence) String.valueOf(this.mInitialCapacity)).println();
        synchronized (this.mPendingRequests) {
            size = this.mPendingRequests.size();
        }
        printWriter.append((CharSequence) str).append("pendingRequests=").append((CharSequence) String.valueOf(size)).println();
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handlePendingRequestWhileUnBound(AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        synchronized (this.mPendingRequests) {
            this.mPendingRequests.add(basePendingRequest);
            if (this.mVerbose) {
                Slog.v(this.mTag, "queued " + this.mPendingRequests.size() + " requests; last=" + basePendingRequest);
            }
        }
    }
}
