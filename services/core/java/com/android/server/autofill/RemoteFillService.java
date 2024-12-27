package com.android.server.autofill;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.RemoteException;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.service.autofill.Flags;
import android.service.autofill.IAutoFillService;
import android.service.autofill.IFillCallback;
import android.service.autofill.ISaveCallback;
import android.service.autofill.SaveRequest;
import android.util.Slog;

import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;

import java.lang.ref.WeakReference;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

final class RemoteFillService extends ServiceConnector.Impl {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final FillServiceCallbacks mCallbacks;
    private final ComponentName mComponentName;
    private AtomicReference mConvertCredentialCallback;
    private AtomicReference mFillCallback;
    private final boolean mIsCredentialAutofillService;
    private final Object mLock;
    private CompletableFuture mPendingFillRequest;
    private int mPendingFillRequestId;
    private AtomicReference mSaveCallback;

    /* renamed from: com.android.server.autofill.RemoteFillService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IFillCallback.Stub {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ RemoteFillService this$0;
        public final /* synthetic */ AtomicReference val$cancellationSink;
        public final /* synthetic */ CompletableFuture val$fillRequest;
        public final /* synthetic */ AtomicReference val$futureRef;

        public /* synthetic */ AnonymousClass1(
                RemoteFillService remoteFillService,
                AtomicReference atomicReference,
                AtomicReference atomicReference2,
                CompletableFuture completableFuture,
                int i) {
            this.$r8$classId = i;
            this.this$0 = remoteFillService;
            this.val$futureRef = atomicReference;
            this.val$cancellationSink = atomicReference2;
            this.val$fillRequest = completableFuture;
        }

        public final void onCancellable(ICancellationSignal iCancellationSignal) {
            switch (this.$r8$classId) {
                case 0:
                    CompletableFuture completableFuture =
                            (CompletableFuture) this.val$futureRef.get();
                    if (completableFuture != null && completableFuture.isCancelled()) {
                        RemoteFillService remoteFillService = this.this$0;
                        int i = RemoteFillService.$r8$clinit;
                        remoteFillService.getClass();
                        RemoteFillService.dispatchCancellationSignal(iCancellationSignal);
                        break;
                    } else {
                        this.val$cancellationSink.set(iCancellationSignal);
                        break;
                    }
                default:
                    CompletableFuture completableFuture2 =
                            (CompletableFuture) this.val$futureRef.get();
                    if (completableFuture2 != null && completableFuture2.isCancelled()) {
                        RemoteFillService remoteFillService2 = this.this$0;
                        int i2 = RemoteFillService.$r8$clinit;
                        remoteFillService2.getClass();
                        RemoteFillService.dispatchCancellationSignal(iCancellationSignal);
                        break;
                    } else {
                        this.val$cancellationSink.set(iCancellationSignal);
                        break;
                    }
            }
        }

        public final void onFailure(int i, CharSequence charSequence) {
            switch (this.$r8$classId) {
                case 0:
                    this.val$fillRequest.completeExceptionally(
                            new RuntimeException(
                                    charSequence == null ? "" : String.valueOf(charSequence)));
                    break;
                default:
                    this.val$fillRequest.completeExceptionally(
                            new RuntimeException(
                                    charSequence == null ? "" : String.valueOf(charSequence)));
                    break;
            }
        }

        public final void onSuccess(FillResponse fillResponse) {
            switch (this.$r8$classId) {
                case 0:
                    this.val$fillRequest.complete(fillResponse);
                    break;
                default:
                    this.val$fillRequest.complete(fillResponse);
                    break;
            }
        }
    }

    /* renamed from: com.android.server.autofill.RemoteFillService$4, reason: invalid class name */
    public final class AnonymousClass4 extends ISaveCallback.Stub {
        public final /* synthetic */ int $r8$classId = 1;
        public Object val$save;

        public /* synthetic */ AnonymousClass4() {}

        public AnonymousClass4(CompletableFuture completableFuture) {
            this.val$save = completableFuture;
        }

        public final void onFailure(CharSequence charSequence) {
            switch (this.$r8$classId) {
                case 0:
                    ((CompletableFuture) this.val$save)
                            .completeExceptionally(
                                    new RuntimeException(String.valueOf(charSequence)));
                    break;
                default:
                    ISaveCallback iSaveCallback =
                            (ISaveCallback) ((WeakReference) this.val$save).get();
                    if (iSaveCallback != null) {
                        iSaveCallback.onFailure(charSequence);
                        break;
                    }
                    break;
            }
        }

        public final void onSuccess(IntentSender intentSender) {
            switch (this.$r8$classId) {
                case 0:
                    ((CompletableFuture) this.val$save).complete(intentSender);
                    break;
                default:
                    ISaveCallback iSaveCallback =
                            (ISaveCallback) ((WeakReference) this.val$save).get();
                    if (iSaveCallback != null) {
                        iSaveCallback.onSuccess(intentSender);
                        break;
                    }
                    break;
            }
        }
    }

    public interface FillServiceCallbacks extends AbstractRemoteService.VultureCallback {
        void onFillRequestFailure(int i, Throwable th);

        void onFillRequestSuccess(int i, FillResponse fillResponse, int i2);

        void onSaveRequestFailure(String str, CharSequence charSequence);

        void onSaveRequestSuccess(String str, IntentSender intentSender);
    }

    public final class IFillCallbackDelegate extends IFillCallback.Stub {
        public WeakReference mCallbackWeakRef;

        public final void onCancellable(ICancellationSignal iCancellationSignal) {
            IFillCallback iFillCallback = (IFillCallback) this.mCallbackWeakRef.get();
            if (iFillCallback != null) {
                iFillCallback.onCancellable(iCancellationSignal);
            }
        }

        public final void onFailure(int i, CharSequence charSequence) {
            IFillCallback iFillCallback = (IFillCallback) this.mCallbackWeakRef.get();
            if (iFillCallback != null) {
                iFillCallback.onFailure(i, charSequence);
            }
        }

        public final void onSuccess(FillResponse fillResponse) {
            IFillCallback iFillCallback = (IFillCallback) this.mCallbackWeakRef.get();
            if (iFillCallback != null) {
                iFillCallback.onSuccess(fillResponse);
            }
        }
    }

    public static CompletableFuture $r8$lambda$0drfjd02UtjtR1pzZwZfvLOqncQ(
            RemoteFillService remoteFillService,
            SaveRequest saveRequest,
            IAutoFillService iAutoFillService) {
        remoteFillService.getClass();
        if (Helper.sVerbose) {
            Slog.v("RemoteFillService", "calling onSaveRequest()");
        }
        CompletableFuture completableFuture = new CompletableFuture();
        ISaveCallback anonymousClass4 = new AnonymousClass4(completableFuture);
        if (Flags.remoteFillServiceUseWeakReference()) {
            remoteFillService.mSaveCallback = new AtomicReference(anonymousClass4);
            ISaveCallback anonymousClass42 = new AnonymousClass4();
            anonymousClass42.val$save = new WeakReference(anonymousClass4);
            anonymousClass4 = anonymousClass42;
        }
        iAutoFillService.onSaveRequest(saveRequest, anonymousClass4);
        return completableFuture;
    }

    public static /* synthetic */ void $r8$lambda$1hw0FNOarmG_sgD3O92JoX9WtBE(
            RemoteFillService remoteFillService, Throwable th, IntentSender intentSender) {
        if (th == null) {
            remoteFillService.mCallbacks.onSaveRequestSuccess(
                    remoteFillService.mComponentName.getPackageName(), intentSender);
            return;
        }
        remoteFillService.mCallbacks.onSaveRequestFailure(
                th.getMessage(), remoteFillService.mComponentName.getPackageName());
    }

    public static /* synthetic */ void $r8$lambda$BpMSr5I3_9bplEhls6FgDTRLWpg(
            RemoteFillService remoteFillService,
            Throwable th,
            FillRequest fillRequest,
            FillResponse fillResponse,
            AtomicReference atomicReference) {
        synchronized (remoteFillService.mLock) {
            remoteFillService.mPendingFillRequest = null;
            remoteFillService.mPendingFillRequestId = Integer.MIN_VALUE;
        }
        FillServiceCallbacks fillServiceCallbacks = remoteFillService.mCallbacks;
        if (fillServiceCallbacks == null) {
            Slog.w(
                    "RemoteFillService",
                    "Error calling RemoteFillService - service already unbound");
            return;
        }
        if (th == null) {
            int id = fillRequest.getId();
            remoteFillService.mComponentName.getPackageName();
            fillServiceCallbacks.onFillRequestSuccess(id, fillResponse, fillRequest.getFlags());
            return;
        }
        Slog.e("RemoteFillService", "Error calling on fill request", th);
        if (th instanceof TimeoutException) {
            dispatchCancellationSignal((ICancellationSignal) atomicReference.get());
            remoteFillService.mCallbacks.onFillRequestFailure(fillRequest.getId(), th);
        } else if (th instanceof CancellationException) {
            dispatchCancellationSignal((ICancellationSignal) atomicReference.get());
        } else {
            remoteFillService.mCallbacks.onFillRequestFailure(fillRequest.getId(), th);
        }
    }

    public static /* synthetic */ void $r8$lambda$PXtmzf6bY2FCvCgDzGBClXL04mI(
            RemoteFillService remoteFillService,
            Throwable th,
            FillRequest fillRequest,
            FillResponse fillResponse,
            AtomicReference atomicReference) {
        synchronized (remoteFillService.mLock) {
            remoteFillService.mPendingFillRequest = null;
            remoteFillService.mPendingFillRequestId = Integer.MIN_VALUE;
        }
        if (th == null) {
            FillServiceCallbacks fillServiceCallbacks = remoteFillService.mCallbacks;
            int id = fillRequest.getId();
            remoteFillService.mComponentName.getPackageName();
            fillServiceCallbacks.onFillRequestSuccess(id, fillResponse, fillRequest.getFlags());
            return;
        }
        Slog.e("RemoteFillService", "Error calling on fill request", th);
        if (th instanceof TimeoutException) {
            dispatchCancellationSignal((ICancellationSignal) atomicReference.get());
            remoteFillService.mCallbacks.onFillRequestFailure(fillRequest.getId(), th);
        } else if (th instanceof CancellationException) {
            dispatchCancellationSignal((ICancellationSignal) atomicReference.get());
        } else {
            remoteFillService.mCallbacks.onFillRequestFailure(fillRequest.getId(), th);
        }
    }

    public RemoteFillService(
            Context context,
            ComponentName componentName,
            int i,
            FillServiceCallbacks fillServiceCallbacks,
            boolean z,
            ComponentName componentName2) {
        super(
                context,
                new Intent("android.service.autofill.AutofillService").setComponent(componentName),
                (z ? 4194304 : 0) | 1048576,
                i,
                new RemoteFillService$$ExternalSyntheticLambda2());
        this.mLock = new Object();
        this.mPendingFillRequestId = Integer.MIN_VALUE;
        this.mCallbacks = fillServiceCallbacks;
        this.mComponentName = componentName;
        this.mIsCredentialAutofillService = componentName.equals(componentName2);
    }

    public static void dispatchCancellationSignal(ICancellationSignal iCancellationSignal) {
        if (iCancellationSignal == null) {
            return;
        }
        try {
            iCancellationSignal.cancel();
        } catch (RemoteException e) {
            Slog.e("RemoteFillService", "Error requesting a cancellation", e);
        }
    }

    public final void addLast(Object obj) {
        cancelPendingJobs();
        super.addLast((ServiceConnector.Job) obj);
    }

    public final int cancelCurrentRequest() {
        int i;
        synchronized (this.mLock) {
            try {
                CompletableFuture completableFuture = this.mPendingFillRequest;
                i =
                        (completableFuture == null || !completableFuture.cancel(false))
                                ? Integer.MIN_VALUE
                                : this.mPendingFillRequestId;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final long getAutoDisconnectTimeoutMs() {
        return 5000L;
    }

    public final boolean isCredentialAutofillService() {
        return this.mIsCredentialAutofillService;
    }

    public final IFillCallback maybeWrapWithWeakReference(IFillCallback iFillCallback) {
        if (!Flags.remoteFillServiceUseWeakReference()) {
            return iFillCallback;
        }
        this.mFillCallback = new AtomicReference(iFillCallback);
        IFillCallbackDelegate iFillCallbackDelegate = new IFillCallbackDelegate();
        iFillCallbackDelegate.mCallbackWeakRef = new WeakReference(iFillCallback);
        return iFillCallbackDelegate;
    }

    public final void onFillCredentialRequest(
            final FillRequest fillRequest, final IBinder iBinder) {
        if (Helper.sVerbose) {
            Slog.v("RemoteFillService", "onFillRequest:" + fillRequest);
        }
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        AndroidFuture orTimeout =
                postAsync(
                                new ServiceConnector
                                        .Job() { // from class:
                                                 // com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda5
                                    public final Object run(Object obj) {
                                        RemoteFillService remoteFillService =
                                                RemoteFillService.this;
                                        FillRequest fillRequest2 = fillRequest;
                                        AtomicReference atomicReference3 = atomicReference2;
                                        AtomicReference atomicReference4 = atomicReference;
                                        IBinder iBinder2 = iBinder;
                                        IAutoFillService iAutoFillService = (IAutoFillService) obj;
                                        int i = RemoteFillService.$r8$clinit;
                                        remoteFillService.getClass();
                                        if (Helper.sVerbose) {
                                            Slog.v(
                                                    "RemoteFillService",
                                                    "calling onFillRequest() for id="
                                                            + fillRequest2.getId());
                                        }
                                        CompletableFuture completableFuture =
                                                new CompletableFuture();
                                        iAutoFillService.onFillCredentialRequest(
                                                fillRequest2,
                                                remoteFillService.maybeWrapWithWeakReference(
                                                        new RemoteFillService.AnonymousClass1(
                                                                remoteFillService,
                                                                atomicReference3,
                                                                atomicReference4,
                                                                completableFuture,
                                                                0)),
                                                iBinder2);
                                        return completableFuture;
                                    }
                                })
                        .orTimeout(5000L, TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        synchronized (this.mLock) {
            this.mPendingFillRequest = orTimeout;
            this.mPendingFillRequestId = fillRequest.getId();
        }
        orTimeout.whenComplete(
                new RemoteFillService$$ExternalSyntheticLambda4(
                        this, fillRequest, atomicReference, 1));
    }

    public final void onFillRequest(final FillRequest fillRequest) {
        if (Helper.sVerbose) {
            Slog.v("RemoteFillService", "onFillRequest:" + fillRequest);
        }
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        AndroidFuture orTimeout =
                postAsync(
                                new ServiceConnector
                                        .Job() { // from class:
                                                 // com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda3
                                    public final Object run(Object obj) {
                                        RemoteFillService remoteFillService =
                                                RemoteFillService.this;
                                        FillRequest fillRequest2 = fillRequest;
                                        AtomicReference atomicReference3 = atomicReference2;
                                        AtomicReference atomicReference4 = atomicReference;
                                        IAutoFillService iAutoFillService = (IAutoFillService) obj;
                                        int i = RemoteFillService.$r8$clinit;
                                        remoteFillService.getClass();
                                        if (Helper.sVerbose) {
                                            Slog.v(
                                                    "RemoteFillService",
                                                    "calling onFillRequest() for id="
                                                            + fillRequest2.getId());
                                        }
                                        CompletableFuture completableFuture =
                                                new CompletableFuture();
                                        iAutoFillService.onFillRequest(
                                                fillRequest2,
                                                remoteFillService.maybeWrapWithWeakReference(
                                                        new RemoteFillService.AnonymousClass1(
                                                                remoteFillService,
                                                                atomicReference3,
                                                                atomicReference4,
                                                                completableFuture,
                                                                1)));
                                        return completableFuture;
                                    }
                                })
                        .orTimeout(5000L, TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        synchronized (this.mLock) {
            this.mPendingFillRequest = orTimeout;
            this.mPendingFillRequestId = fillRequest.getId();
        }
        orTimeout.whenComplete(
                new RemoteFillService$$ExternalSyntheticLambda4(
                        this, fillRequest, atomicReference, 0));
    }

    public final void onServiceConnectionStatusChanged(IInterface iInterface, boolean z) {
        try {
            ((IAutoFillService) iInterface).onConnectedStateChanged(z);
        } catch (Exception e) {
            Slog.w(
                    "RemoteFillService",
                    "Exception calling onConnectedStateChanged(" + z + "): " + e);
        }
    }
}
