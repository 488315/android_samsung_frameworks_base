package com.android.server.autofill;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.autofill.Dataset;
import android.service.autofill.FillEventHistory;
import android.service.autofill.augmented.IAugmentedAutofillService;
import android.service.autofill.augmented.IFillCallback;
import android.util.Slog;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutoFillManagerClient;
import android.view.inputmethod.InlineSuggestionsRequest;
import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.os.IResultReceiver;
import com.android.server.autofill.AutofillManagerServiceImpl;
import com.android.server.autofill.Session;
import com.android.server.autofill.ui.InlineFillUi;
import com.android.server.autofill.ui.InlineSuggestionFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
final class RemoteAugmentedAutofillService extends ServiceConnector.Impl {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final RemoteAugmentedAutofillServiceCallbacks mCallbacks;
    private final ComponentName mComponentName;
    private final int mIdleUnbindTimeoutMs;
    private final int mRequestTimeoutMs;
    private final AutofillUriGrantsManager mUriGrantsManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface RemoteAugmentedAutofillServiceCallbacks extends AbstractRemoteService.VultureCallback {
    }

    public static void $r8$lambda$IXYPMbjPeMt7KfClcttRpU_yFvU(RemoteAugmentedAutofillService remoteAugmentedAutofillService, AtomicReference atomicReference, ComponentName componentName, int i, Throwable th) {
        remoteAugmentedAutofillService.getClass();
        if (th instanceof CancellationException) {
            ICancellationSignal iCancellationSignal = (ICancellationSignal) atomicReference.get();
            if (iCancellationSignal == null) {
                return;
            }
            Handler.getMain().post(new RemoteAugmentedAutofillService$$ExternalSyntheticLambda4(iCancellationSignal));
            return;
        }
        if (!(th instanceof TimeoutException)) {
            if (th != null) {
                Slog.e("RemoteAugmentedAutofillService", "exception handling getAugmentedAutofillClient() for " + i + ": ", th);
                return;
            }
            return;
        }
        Slog.w("RemoteAugmentedAutofillService", "PendingAutofillRequest timed out (" + remoteAugmentedAutofillService.mRequestTimeoutMs + "ms) for " + remoteAugmentedAutofillService);
        ICancellationSignal iCancellationSignal2 = (ICancellationSignal) atomicReference.get();
        if (iCancellationSignal2 != null) {
            Handler.getMain().post(new RemoteAugmentedAutofillService$$ExternalSyntheticLambda4(iCancellationSignal2));
        }
        ComponentName componentName2 = remoteAugmentedAutofillService.mComponentName;
        if (componentName2 != null) {
            android.service.autofill.augmented.Helper.logResponse(15, componentName2.getPackageName(), componentName, i, remoteAugmentedAutofillService.mRequestTimeoutMs);
        }
    }

    /* renamed from: -$$Nest$mmaybeRequestShowInlineSuggestions, reason: not valid java name */
    public static void m296$$Nest$mmaybeRequestShowInlineSuggestions(RemoteAugmentedAutofillService remoteAugmentedAutofillService, final int i, InlineSuggestionsRequest inlineSuggestionsRequest, List list, final Bundle bundle, final AutofillId autofillId, AutofillValue autofillValue, final Function function, final IAutoFillManagerClient iAutoFillManagerClient, final Runnable runnable, RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService, final int i2, final ComponentName componentName, final IBinder iBinder) {
        String str;
        remoteAugmentedAutofillService.getClass();
        if (list == null || list.isEmpty() || function == null || inlineSuggestionsRequest == null || remoteInlineSuggestionRenderService == null) {
            if (function == null || inlineSuggestionsRequest == null) {
                return;
            }
            function.apply(new InlineFillUi(autofillId));
            return;
        }
        AutofillManagerServiceImpl autofillManagerServiceImpl = AutofillManagerServiceImpl.this;
        synchronized (autofillManagerServiceImpl.mLock) {
            str = null;
            autofillManagerServiceImpl.mAugmentedAutofillEventHistory = new FillEventHistory(i, null);
        }
        if (autofillValue != null && autofillValue.isText()) {
            str = autofillValue.getTextValue().toString();
        }
        InlineFillUi.InlineFillUiInfo inlineFillUiInfo = new InlineFillUi.InlineFillUiInfo(inlineSuggestionsRequest, autofillId, str, remoteInlineSuggestionRenderService, i2, i);
        if (((Boolean) function.apply(new InlineFillUi(inlineFillUiInfo, InlineSuggestionFactory.createInlineSuggestions(inlineFillUiInfo, "android:platform", list, new InlineFillUi.InlineSuggestionUiCallback() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService.2
            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public final void authenticate() {
                int i3 = RemoteAugmentedAutofillService.$r8$clinit;
                Slog.e("RemoteAugmentedAutofillService", "authenticate not implemented for augmented autofill");
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public final void autofill(Dataset dataset, int i3) {
                boolean z = true;
                if (dataset.getAuthentication() != null) {
                    RemoteAugmentedAutofillServiceCallbacks remoteAugmentedAutofillServiceCallbacks = RemoteAugmentedAutofillService.this.mCallbacks;
                    int i4 = i;
                    String id = dataset.getId();
                    Bundle bundle2 = bundle;
                    AutofillManagerServiceImpl autofillManagerServiceImpl2 = AutofillManagerServiceImpl.this;
                    synchronized (autofillManagerServiceImpl2.mLock) {
                        try {
                            FillEventHistory fillEventHistory = autofillManagerServiceImpl2.mAugmentedAutofillEventHistory;
                            if (fillEventHistory != null && fillEventHistory.getSessionId() == i4) {
                                autofillManagerServiceImpl2.mAugmentedAutofillEventHistory.addEvent(new FillEventHistory.Event(1, id, bundle2, null, null, null, null, null, null, null, null));
                            }
                        } finally {
                        }
                    }
                    IntentSender authentication = dataset.getAuthentication();
                    int makeAuthenticationId = AutofillManager.makeAuthenticationId(1, i3);
                    Intent intent = new Intent();
                    intent.putExtra("android.view.autofill.extra.CLIENT_STATE", bundle);
                    try {
                        iAutoFillManagerClient.authenticate(i, makeAuthenticationId, authentication, intent, false);
                        return;
                    } catch (RemoteException unused) {
                        int i5 = RemoteAugmentedAutofillService.$r8$clinit;
                        Slog.w("RemoteAugmentedAutofillService", "Error starting auth flow");
                        function.apply(new InlineFillUi(autofillId));
                        return;
                    }
                }
                AutofillManagerServiceImpl.this.logAugmentedAutofillSelected(i, dataset.getId(), bundle);
                try {
                    ArrayList fieldIds = dataset.getFieldIds();
                    ClipData fieldContent = dataset.getFieldContent();
                    if (fieldContent != null) {
                        RemoteAugmentedAutofillService.this.mUriGrantsManager.grantUriPermissions(componentName, iBinder, i2, fieldContent);
                        AutofillId autofillId2 = (AutofillId) fieldIds.get(0);
                        if (Helper.sDebug) {
                            Slog.d("RemoteAugmentedAutofillService", "Calling client autofillContent(): id=" + autofillId2 + ", content=" + fieldContent);
                        }
                        iAutoFillManagerClient.autofillContent(i, autofillId2, fieldContent);
                    } else {
                        if (fieldIds.size() != 1 || !((AutofillId) fieldIds.get(0)).equals(autofillId)) {
                            z = false;
                        }
                        if (Helper.sDebug) {
                            Slog.d("RemoteAugmentedAutofillService", "Calling client autofill(): ids=" + fieldIds + ", values=" + dataset.getFieldValues());
                        }
                        iAutoFillManagerClient.autofill(i, fieldIds, dataset.getFieldValues(), z);
                    }
                    function.apply(new InlineFillUi(autofillId));
                } catch (RemoteException unused2) {
                    int i6 = RemoteAugmentedAutofillService.$r8$clinit;
                    Slog.w("RemoteAugmentedAutofillService", "Encounter exception autofilling the values");
                }
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public final void onError() {
                runnable.run();
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public final void onInflate() {
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public final void startIntentSender(IntentSender intentSender) {
                try {
                    iAutoFillManagerClient.startIntentSender(intentSender, new Intent());
                } catch (RemoteException unused) {
                    int i3 = RemoteAugmentedAutofillService.$r8$clinit;
                    Slog.w("RemoteAugmentedAutofillService", "RemoteException starting intent sender");
                }
            }
        }, false)))).booleanValue()) {
            AutofillManagerServiceImpl autofillManagerServiceImpl2 = AutofillManagerServiceImpl.this;
            synchronized (autofillManagerServiceImpl2.mLock) {
                try {
                    FillEventHistory fillEventHistory = autofillManagerServiceImpl2.mAugmentedAutofillEventHistory;
                    if (fillEventHistory != null && fillEventHistory.getSessionId() == i) {
                        autofillManagerServiceImpl2.mAugmentedAutofillEventHistory.addEvent(new FillEventHistory.Event(5, null, bundle, null, null, null, null, null, null, null, null, 0, 2));
                    }
                } finally {
                }
            }
        }
    }

    public RemoteAugmentedAutofillService(Context context, int i, ComponentName componentName, int i2, AutofillManagerServiceImpl.AnonymousClass1 anonymousClass1, boolean z, int i3, int i4) {
        super(context, new Intent("android.service.autofill.augmented.AugmentedAutofillService").setComponent(componentName), z ? 4194304 : 0, i2, new RemoteAugmentedAutofillService$$ExternalSyntheticLambda0());
        this.mIdleUnbindTimeoutMs = i3;
        this.mRequestTimeoutMs = i4;
        this.mComponentName = componentName;
        this.mCallbacks = anonymousClass1;
        this.mUriGrantsManager = new AutofillUriGrantsManager(i);
        connect();
    }

    public final long getAutoDisconnectTimeoutMs() {
        return this.mIdleUnbindTimeoutMs;
    }

    public final AutofillUriGrantsManager getAutofillUriGrantsManager() {
        return this.mUriGrantsManager;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final void onRequestAutofillLocked(final int i, final IAutoFillManagerClient iAutoFillManagerClient, final int i2, final ComponentName componentName, final IBinder iBinder, final AutofillId autofillId, final AutofillValue autofillValue, final InlineSuggestionsRequest inlineSuggestionsRequest, final Session.AugmentedAutofillInlineSuggestionsResponseCallback augmentedAutofillInlineSuggestionsResponseCallback, final Session.AugmentedAutofillErrorCallback augmentedAutofillErrorCallback, final RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService, final int i3) {
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        final AtomicReference atomicReference = new AtomicReference();
        postAsync(new ServiceConnector.Job() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService$$ExternalSyntheticLambda2
            public final Object run(Object obj) {
                final RemoteAugmentedAutofillService remoteAugmentedAutofillService = RemoteAugmentedAutofillService.this;
                final IAutoFillManagerClient iAutoFillManagerClient2 = iAutoFillManagerClient;
                final int i4 = i;
                final int i5 = i2;
                final ComponentName componentName2 = componentName;
                final AutofillId autofillId2 = autofillId;
                final AutofillValue autofillValue2 = autofillValue;
                final long j = elapsedRealtime;
                final InlineSuggestionsRequest inlineSuggestionsRequest2 = inlineSuggestionsRequest;
                Function function = augmentedAutofillInlineSuggestionsResponseCallback;
                Runnable runnable = augmentedAutofillErrorCallback;
                final RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService2 = remoteInlineSuggestionRenderService;
                final int i6 = i3;
                final IBinder iBinder2 = iBinder;
                final AtomicReference atomicReference2 = atomicReference;
                final IAugmentedAutofillService iAugmentedAutofillService = (IAugmentedAutofillService) obj;
                int i7 = RemoteAugmentedAutofillService.$r8$clinit;
                remoteAugmentedAutofillService.getClass();
                final AndroidFuture androidFuture = new AndroidFuture();
                final Session.AugmentedAutofillInlineSuggestionsResponseCallback augmentedAutofillInlineSuggestionsResponseCallback2 = (Session.AugmentedAutofillInlineSuggestionsResponseCallback) function;
                final Session.AugmentedAutofillErrorCallback augmentedAutofillErrorCallback2 = (Session.AugmentedAutofillErrorCallback) runnable;
                iAutoFillManagerClient2.getAugmentedAutofillClient(new IResultReceiver.Stub() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService.1
                    public final void send(int i8, Bundle bundle) {
                        iAugmentedAutofillService.onFillRequest(i4, bundle.getBinder("android.view.autofill.extra.AUGMENTED_AUTOFILL_CLIENT"), i5, componentName2, autofillId2, autofillValue2, j, inlineSuggestionsRequest2, new IFillCallback.Stub() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService.1.1
                            public final void cancel() {
                                androidFuture.cancel(true);
                            }

                            public final boolean isCompleted() {
                                return androidFuture.isDone() && !androidFuture.isCancelled();
                            }

                            public final void onCancellable(ICancellationSignal iCancellationSignal) {
                                if (!androidFuture.isCancelled()) {
                                    atomicReference2.set(iCancellationSignal);
                                    return;
                                }
                                RemoteAugmentedAutofillService.this.getClass();
                                if (iCancellationSignal == null) {
                                    return;
                                }
                                Handler.getMain().post(new RemoteAugmentedAutofillService$$ExternalSyntheticLambda4(iCancellationSignal));
                            }

                            public final void onSuccess(List list, Bundle bundle2, boolean z) {
                                AutofillManagerServiceImpl autofillManagerServiceImpl = AutofillManagerServiceImpl.this;
                                synchronized (autofillManagerServiceImpl.mLock) {
                                    autofillManagerServiceImpl.mAugmentedAutofillEventHistory = null;
                                }
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                RemoteAugmentedAutofillService.m296$$Nest$mmaybeRequestShowInlineSuggestions(RemoteAugmentedAutofillService.this, i4, inlineSuggestionsRequest2, list, bundle2, autofillId2, autofillValue2, augmentedAutofillInlineSuggestionsResponseCallback2, iAutoFillManagerClient2, augmentedAutofillErrorCallback2, remoteInlineSuggestionRenderService2, i6, componentName2, iBinder2);
                                if (z) {
                                    return;
                                }
                                androidFuture.complete((Object) null);
                            }
                        });
                    }
                });
                return androidFuture;
            }
        }).orTimeout(this.mRequestTimeoutMs, TimeUnit.MILLISECONDS).whenComplete(new BiConsumer() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                RemoteAugmentedAutofillService.$r8$lambda$IXYPMbjPeMt7KfClcttRpU_yFvU(RemoteAugmentedAutofillService.this, atomicReference, componentName, i, (Throwable) obj2);
            }
        });
    }

    public final void onServiceConnectionStatusChanged(IInterface iInterface, boolean z) {
        IAugmentedAutofillService iAugmentedAutofillService = (IAugmentedAutofillService) iInterface;
        try {
            if (z) {
                iAugmentedAutofillService.onConnected(Helper.sDebug, Helper.sVerbose);
            } else {
                iAugmentedAutofillService.onDisconnected();
            }
        } catch (Exception e) {
            Slog.w("RemoteAugmentedAutofillService", "Exception calling onServiceConnectionStatusChanged(" + z + "): ", e);
        }
    }

    public final String toString() {
        return "RemoteAugmentedAutofillService[" + ComponentName.flattenToShortString(this.mComponentName) + "]";
    }
}
