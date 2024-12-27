package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.credentials.CreateCredentialException;
import android.credentials.GetCredentialException;
import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.service.credentials.BeginCreateCredentialRequest;
import android.service.credentials.BeginGetCredentialRequest;
import android.service.credentials.ClearCredentialStateRequest;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;

import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class RemoteCredentialService extends ServiceConnector.Impl {
    public static final /* synthetic */ int $r8$clinit = 0;
    private ProviderCallbacks mCallback;
    private final ComponentName mComponentName;
    private AtomicBoolean mOngoingRequest;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ProviderCallbacks {
        void onProviderCancellable(ICancellationSignal iCancellationSignal);

        void onProviderResponseFailure(Exception exc);

        void onProviderResponseSuccess(Object obj);

        void onProviderServiceDied(RemoteCredentialService remoteCredentialService);
    }

    public RemoteCredentialService(Context context, ComponentName componentName, int i) {
        super(
                context,
                new Intent("android.service.credentials.CredentialProviderService")
                        .setComponent(componentName),
                0,
                i,
                new RemoteCredentialService$$ExternalSyntheticLambda6());
        this.mOngoingRequest = new AtomicBoolean(false);
        this.mComponentName = componentName;
    }

    public static void dispatchCancellationSignal(ICancellationSignal iCancellationSignal) {
        if (iCancellationSignal == null) {
            Slog.e("CredentialManager", "Error dispatching a cancellation - Signal is null");
            return;
        }
        try {
            iCancellationSignal.cancel();
        } catch (RemoteException e) {
            Slog.e("CredentialManager", "Error dispatching a cancellation", e);
        }
    }

    public final void binderDied() {
        super.binderDied();
        Slog.w("CredentialManager", "binderDied");
        if (this.mCallback != null) {
            this.mOngoingRequest.set(false);
            this.mCallback.onProviderServiceDied(this);
        }
    }

    public final long getAutoDisconnectTimeoutMs() {
        return 5000L;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final void handleExecutionResponse(
            Object obj, Throwable th, AtomicReference atomicReference) {
        if (th == null) {
            ProviderCallbacks providerCallbacks = this.mCallback;
            if (providerCallbacks != null) {
                providerCallbacks.onProviderResponseSuccess(obj);
                return;
            }
            return;
        }
        if (th instanceof TimeoutException) {
            Slog.i(
                    "CredentialManager",
                    "Remote provider response timed tuo for: " + this.mComponentName);
            if (this.mOngoingRequest.get()) {
                dispatchCancellationSignal((ICancellationSignal) atomicReference.get());
                if (this.mCallback != null) {
                    this.mOngoingRequest.set(false);
                    this.mCallback.onProviderResponseFailure(null);
                    return;
                }
                return;
            }
            return;
        }
        if (th instanceof CancellationException) {
            Slog.i(
                    "CredentialManager",
                    "Cancellation exception for remote provider: " + this.mComponentName);
            if (this.mOngoingRequest.get()) {
                dispatchCancellationSignal((ICancellationSignal) atomicReference.get());
                if (this.mCallback != null) {
                    this.mOngoingRequest.set(false);
                    this.mCallback.onProviderResponseFailure(null);
                    return;
                }
                return;
            }
            return;
        }
        if (th instanceof GetCredentialException) {
            ProviderCallbacks providerCallbacks2 = this.mCallback;
            if (providerCallbacks2 != null) {
                providerCallbacks2.onProviderResponseFailure((GetCredentialException) th);
                return;
            }
            return;
        }
        if (th instanceof CreateCredentialException) {
            ProviderCallbacks providerCallbacks3 = this.mCallback;
            if (providerCallbacks3 != null) {
                providerCallbacks3.onProviderResponseFailure((CreateCredentialException) th);
                return;
            }
            return;
        }
        ProviderCallbacks providerCallbacks4 = this.mCallback;
        if (providerCallbacks4 != null) {
            providerCallbacks4.onProviderResponseFailure((Exception) th);
        }
    }

    public final void onBeginCreateCredential(
            BeginCreateCredentialRequest beginCreateCredentialRequest) {
        if (this.mCallback == null) {
            Slog.w("CredentialManager", "Callback is not set");
            return;
        }
        this.mOngoingRequest.set(true);
        AtomicReference atomicReference = new AtomicReference();
        AtomicReference atomicReference2 = new AtomicReference();
        AndroidFuture orTimeout =
                postAsync(
                                new RemoteCredentialService$$ExternalSyntheticLambda0(
                                        this,
                                        beginCreateCredentialRequest,
                                        atomicReference2,
                                        atomicReference))
                        .orTimeout(3000L, TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        orTimeout.whenComplete(
                new RemoteCredentialService$$ExternalSyntheticLambda1(this, atomicReference, 0));
    }

    public final void onBeginGetCredential(BeginGetCredentialRequest beginGetCredentialRequest) {
        if (this.mCallback == null) {
            Slog.w("CredentialManager", "Callback is not set");
            return;
        }
        this.mOngoingRequest.set(true);
        AtomicReference atomicReference = new AtomicReference();
        AtomicReference atomicReference2 = new AtomicReference();
        AndroidFuture orTimeout =
                postAsync(
                                new RemoteCredentialService$$ExternalSyntheticLambda0(
                                        this,
                                        beginGetCredentialRequest,
                                        atomicReference2,
                                        atomicReference))
                        .orTimeout(3000L, TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        orTimeout.whenComplete(
                new RemoteCredentialService$$ExternalSyntheticLambda1(this, atomicReference, 1));
    }

    public final void onBindingDied(ComponentName componentName) {
        super.onBindingDied(componentName);
        Slog.w("CredentialManager", "binding died for: " + componentName);
    }

    public final void onClearCredentialState(
            ClearCredentialStateRequest clearCredentialStateRequest) {
        if (this.mCallback == null) {
            Slog.w("CredentialManager", "Callback is not set");
            return;
        }
        this.mOngoingRequest.set(true);
        AtomicReference atomicReference = new AtomicReference();
        AtomicReference atomicReference2 = new AtomicReference();
        AndroidFuture orTimeout =
                postAsync(
                                new RemoteCredentialService$$ExternalSyntheticLambda0(
                                        this,
                                        clearCredentialStateRequest,
                                        atomicReference2,
                                        atomicReference))
                        .orTimeout(3000L, TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        orTimeout.whenComplete(
                new RemoteCredentialService$$ExternalSyntheticLambda1(this, atomicReference, 2));
    }

    public final void setCallback(ProviderSession providerSession) {
        this.mCallback = providerSession;
    }
}
