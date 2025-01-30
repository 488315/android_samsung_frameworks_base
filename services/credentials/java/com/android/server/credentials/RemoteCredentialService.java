package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.credentials.ClearCredentialStateException;
import android.credentials.CreateCredentialException;
import android.credentials.GetCredentialException;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.service.credentials.BeginCreateCredentialRequest;
import android.service.credentials.BeginCreateCredentialResponse;
import android.service.credentials.BeginGetCredentialRequest;
import android.service.credentials.BeginGetCredentialResponse;
import android.service.credentials.ClearCredentialStateRequest;
import android.service.credentials.IBeginCreateCredentialCallback;
import android.service.credentials.IBeginGetCredentialCallback;
import android.service.credentials.IClearCredentialStateCallback;
import android.service.credentials.ICredentialProviderService;
import android.util.Slog;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class RemoteCredentialService extends ServiceConnector.Impl {
    private ProviderCallbacks mCallback;
    private final ComponentName mComponentName;
    private AtomicBoolean mOngoingRequest;

    public interface ProviderCallbacks {
        void onProviderCancellable(ICancellationSignal iCancellationSignal);

        void onProviderResponseFailure(int i, Exception exc);

        void onProviderResponseSuccess(Object obj);

        void onProviderServiceDied(RemoteCredentialService remoteCredentialService);
    }

    public long getAutoDisconnectTimeoutMs() {
        return 5000L;
    }

    public RemoteCredentialService(Context context, ComponentName componentName, int i) {
        super(context, new Intent("android.service.credentials.CredentialProviderService").setComponent(componentName), 0, i, new Function() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ICredentialProviderService.Stub.asInterface((IBinder) obj);
            }
        });
        this.mOngoingRequest = new AtomicBoolean(false);
        this.mComponentName = componentName;
    }

    public void setCallback(ProviderCallbacks providerCallbacks) {
        this.mCallback = providerCallbacks;
    }

    public void onBindingDied(ComponentName componentName) {
        super.onBindingDied(componentName);
        Slog.w("RemoteCredentialService", "binding died for: " + componentName);
    }

    public void binderDied() {
        super.binderDied();
        Slog.w("RemoteCredentialService", "binderDied");
        if (this.mCallback != null) {
            this.mOngoingRequest.set(false);
            this.mCallback.onProviderServiceDied(this);
        }
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public void onBeginGetCredential(final BeginGetCredentialRequest beginGetCredentialRequest) {
        if (this.mCallback == null) {
            Slog.w("RemoteCredentialService", "Callback is not set");
            return;
        }
        this.mOngoingRequest.set(true);
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        AndroidFuture orTimeout = postAsync(new ServiceConnector.Job() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda5
            public final Object run(Object obj) {
                CompletableFuture lambda$onBeginGetCredential$0;
                lambda$onBeginGetCredential$0 = RemoteCredentialService.this.lambda$onBeginGetCredential$0(beginGetCredentialRequest, atomicReference2, atomicReference, (ICredentialProviderService) obj);
                return lambda$onBeginGetCredential$0;
            }
        }).orTimeout(3000L, TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        orTimeout.whenComplete(new BiConsumer() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                RemoteCredentialService.this.lambda$onBeginGetCredential$2(atomicReference, (BeginGetCredentialResponse) obj, (Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CompletableFuture lambda$onBeginGetCredential$0(BeginGetCredentialRequest beginGetCredentialRequest, final AtomicReference atomicReference, final AtomicReference atomicReference2, ICredentialProviderService iCredentialProviderService) {
        final CompletableFuture completableFuture = new CompletableFuture();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            iCredentialProviderService.onBeginGetCredential(beginGetCredentialRequest, new IBeginGetCredentialCallback.Stub() { // from class: com.android.server.credentials.RemoteCredentialService.1
                public void onSuccess(BeginGetCredentialResponse beginGetCredentialResponse) {
                    completableFuture.complete(beginGetCredentialResponse);
                }

                public void onFailure(String str, CharSequence charSequence) {
                    String valueOf = charSequence == null ? "" : String.valueOf(charSequence);
                    Slog.w("RemoteCredentialService", "Get credential errorMsg=" + valueOf);
                    completableFuture.completeExceptionally(new GetCredentialException(str, valueOf));
                }

                public void onCancellable(ICancellationSignal iCancellationSignal) {
                    CompletableFuture completableFuture2 = (CompletableFuture) atomicReference.get();
                    if (completableFuture2 != null && completableFuture2.isCancelled()) {
                        RemoteCredentialService.this.dispatchCancellationSignal(iCancellationSignal);
                        return;
                    }
                    atomicReference2.set(iCancellationSignal);
                    if (RemoteCredentialService.this.mCallback != null) {
                        RemoteCredentialService.this.mCallback.onProviderCancellable(iCancellationSignal);
                    }
                }
            });
            return completableFuture;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBeginGetCredential$2(final AtomicReference atomicReference, final BeginGetCredentialResponse beginGetCredentialResponse, final Throwable th) {
        Handler.getMain().post(new Runnable() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                RemoteCredentialService.this.lambda$onBeginGetCredential$1(beginGetCredentialResponse, th, atomicReference);
            }
        });
    }

    public void onBeginCreateCredential(final BeginCreateCredentialRequest beginCreateCredentialRequest) {
        if (this.mCallback == null) {
            Slog.w("RemoteCredentialService", "Callback is not set");
            return;
        }
        this.mOngoingRequest.set(true);
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        AndroidFuture orTimeout = postAsync(new ServiceConnector.Job() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda3
            public final Object run(Object obj) {
                CompletableFuture lambda$onBeginCreateCredential$3;
                lambda$onBeginCreateCredential$3 = RemoteCredentialService.this.lambda$onBeginCreateCredential$3(beginCreateCredentialRequest, atomicReference2, atomicReference, (ICredentialProviderService) obj);
                return lambda$onBeginCreateCredential$3;
            }
        }).orTimeout(3000L, TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        orTimeout.whenComplete(new BiConsumer() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                RemoteCredentialService.this.lambda$onBeginCreateCredential$5(atomicReference, (BeginCreateCredentialResponse) obj, (Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CompletableFuture lambda$onBeginCreateCredential$3(BeginCreateCredentialRequest beginCreateCredentialRequest, final AtomicReference atomicReference, final AtomicReference atomicReference2, ICredentialProviderService iCredentialProviderService) {
        final CompletableFuture completableFuture = new CompletableFuture();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            iCredentialProviderService.onBeginCreateCredential(beginCreateCredentialRequest, new IBeginCreateCredentialCallback.Stub() { // from class: com.android.server.credentials.RemoteCredentialService.2
                public void onSuccess(BeginCreateCredentialResponse beginCreateCredentialResponse) {
                    completableFuture.complete(beginCreateCredentialResponse);
                }

                public void onFailure(String str, CharSequence charSequence) {
                    String valueOf = charSequence == null ? "" : String.valueOf(charSequence);
                    Slog.w("RemoteCredentialService", "Create credential errorMsg=" + valueOf);
                    completableFuture.completeExceptionally(new CreateCredentialException(str, valueOf));
                }

                public void onCancellable(ICancellationSignal iCancellationSignal) {
                    CompletableFuture completableFuture2 = (CompletableFuture) atomicReference.get();
                    if (completableFuture2 != null && completableFuture2.isCancelled()) {
                        RemoteCredentialService.this.dispatchCancellationSignal(iCancellationSignal);
                        return;
                    }
                    atomicReference2.set(iCancellationSignal);
                    if (RemoteCredentialService.this.mCallback != null) {
                        RemoteCredentialService.this.mCallback.onProviderCancellable(iCancellationSignal);
                    }
                }
            });
            return completableFuture;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBeginCreateCredential$5(final AtomicReference atomicReference, final BeginCreateCredentialResponse beginCreateCredentialResponse, final Throwable th) {
        Handler.getMain().post(new Runnable() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                RemoteCredentialService.this.lambda$onBeginCreateCredential$4(beginCreateCredentialResponse, th, atomicReference);
            }
        });
    }

    public void onClearCredentialState(final ClearCredentialStateRequest clearCredentialStateRequest) {
        if (this.mCallback == null) {
            Slog.w("RemoteCredentialService", "Callback is not set");
            return;
        }
        this.mOngoingRequest.set(true);
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        AndroidFuture orTimeout = postAsync(new ServiceConnector.Job() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda0
            public final Object run(Object obj) {
                CompletableFuture lambda$onClearCredentialState$6;
                lambda$onClearCredentialState$6 = RemoteCredentialService.this.lambda$onClearCredentialState$6(clearCredentialStateRequest, atomicReference2, atomicReference, (ICredentialProviderService) obj);
                return lambda$onClearCredentialState$6;
            }
        }).orTimeout(3000L, TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        orTimeout.whenComplete(new BiConsumer() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                RemoteCredentialService.this.lambda$onClearCredentialState$8(atomicReference, (Void) obj, (Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CompletableFuture lambda$onClearCredentialState$6(ClearCredentialStateRequest clearCredentialStateRequest, final AtomicReference atomicReference, final AtomicReference atomicReference2, ICredentialProviderService iCredentialProviderService) {
        final CompletableFuture completableFuture = new CompletableFuture();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            iCredentialProviderService.onClearCredentialState(clearCredentialStateRequest, new IClearCredentialStateCallback.Stub() { // from class: com.android.server.credentials.RemoteCredentialService.3
                public void onSuccess() {
                    completableFuture.complete(null);
                }

                public void onFailure(String str, CharSequence charSequence) {
                    String valueOf = charSequence == null ? "" : String.valueOf(charSequence);
                    Slog.w("RemoteCredentialService", "Clear credential errorMsg=" + valueOf);
                    completableFuture.completeExceptionally(new ClearCredentialStateException(str, valueOf));
                }

                public void onCancellable(ICancellationSignal iCancellationSignal) {
                    CompletableFuture completableFuture2 = (CompletableFuture) atomicReference.get();
                    if (completableFuture2 != null && completableFuture2.isCancelled()) {
                        RemoteCredentialService.this.dispatchCancellationSignal(iCancellationSignal);
                        return;
                    }
                    atomicReference2.set(iCancellationSignal);
                    if (RemoteCredentialService.this.mCallback != null) {
                        RemoteCredentialService.this.mCallback.onProviderCancellable(iCancellationSignal);
                    }
                }
            });
            return completableFuture;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClearCredentialState$8(final AtomicReference atomicReference, final Void r4, final Throwable th) {
        Handler.getMain().post(new Runnable() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                RemoteCredentialService.this.lambda$onClearCredentialState$7(r4, th, atomicReference);
            }
        });
    }

    /* renamed from: handleExecutionResponse, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final void lambda$onClearCredentialState$7(Object obj, Throwable th, AtomicReference atomicReference) {
        if (th == null) {
            ProviderCallbacks providerCallbacks = this.mCallback;
            if (providerCallbacks != null) {
                providerCallbacks.onProviderResponseSuccess(obj);
                return;
            }
            return;
        }
        if (th instanceof TimeoutException) {
            Slog.i("RemoteCredentialService", "Remote provider response timed tuo for: " + this.mComponentName);
            if (this.mOngoingRequest.get()) {
                dispatchCancellationSignal((ICancellationSignal) atomicReference.get());
                if (this.mCallback != null) {
                    this.mOngoingRequest.set(false);
                    this.mCallback.onProviderResponseFailure(1, null);
                    return;
                }
                return;
            }
            return;
        }
        if (th instanceof CancellationException) {
            Slog.i("RemoteCredentialService", "Cancellation exception for remote provider: " + this.mComponentName);
            if (this.mOngoingRequest.get()) {
                dispatchCancellationSignal((ICancellationSignal) atomicReference.get());
                if (this.mCallback != null) {
                    this.mOngoingRequest.set(false);
                    this.mCallback.onProviderResponseFailure(2, null);
                    return;
                }
                return;
            }
            return;
        }
        if (th instanceof GetCredentialException) {
            ProviderCallbacks providerCallbacks2 = this.mCallback;
            if (providerCallbacks2 != null) {
                providerCallbacks2.onProviderResponseFailure(3, (GetCredentialException) th);
                return;
            }
            return;
        }
        if (th instanceof CreateCredentialException) {
            ProviderCallbacks providerCallbacks3 = this.mCallback;
            if (providerCallbacks3 != null) {
                providerCallbacks3.onProviderResponseFailure(3, (CreateCredentialException) th);
                return;
            }
            return;
        }
        ProviderCallbacks providerCallbacks4 = this.mCallback;
        if (providerCallbacks4 != null) {
            providerCallbacks4.onProviderResponseFailure(0, (Exception) th);
        }
    }

    public final void dispatchCancellationSignal(ICancellationSignal iCancellationSignal) {
        if (iCancellationSignal == null) {
            Slog.e("RemoteCredentialService", "Error dispatching a cancellation - Signal is null");
            return;
        }
        try {
            iCancellationSignal.cancel();
        } catch (RemoteException e) {
            Slog.e("RemoteCredentialService", "Error dispatching a cancellation", e);
        }
    }
}
