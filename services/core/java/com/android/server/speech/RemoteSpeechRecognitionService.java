package com.android.server.speech;

import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.speech.IModelDownloadListener;
import android.speech.IRecognitionListener;
import android.speech.IRecognitionService;
import android.speech.IRecognitionSupportCallback;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.infra.ServiceConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

final class RemoteSpeechRecognitionService extends ServiceConnector.Impl {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final int mCallingUid;
    private final List mClientListeners;
    private final Map mClients;
    private final ComponentName mComponentName;
    private boolean mConnected;
    private final Object mLock;

    public final class ClientState {
        public DelegatingListener mDelegatingListener;
        public boolean mRecordingInProgress;
    }

    public final class DelegatingListener extends IRecognitionListener.Stub {
        public final Runnable mOnSessionFailure;
        public final Runnable mOnSessionSuccess;
        public final IRecognitionListener mRemoteListener;

        public DelegatingListener(
                IRecognitionListener iRecognitionListener,
                RemoteSpeechRecognitionService$$ExternalSyntheticLambda3
                        remoteSpeechRecognitionService$$ExternalSyntheticLambda3,
                RemoteSpeechRecognitionService$$ExternalSyntheticLambda3
                        remoteSpeechRecognitionService$$ExternalSyntheticLambda32) {
            this.mRemoteListener = iRecognitionListener;
            this.mOnSessionSuccess = remoteSpeechRecognitionService$$ExternalSyntheticLambda3;
            this.mOnSessionFailure = remoteSpeechRecognitionService$$ExternalSyntheticLambda32;
        }

        public final void onBeginningOfSpeech() {
            this.mRemoteListener.onBeginningOfSpeech();
        }

        public final void onBufferReceived(byte[] bArr) {
            this.mRemoteListener.onBufferReceived(bArr);
        }

        public final void onEndOfSegmentedSession() {
            this.mOnSessionSuccess.run();
            this.mRemoteListener.onEndOfSegmentedSession();
        }

        public final void onEndOfSpeech() {
            this.mRemoteListener.onEndOfSpeech();
        }

        public final void onError(int i) {
            this.mOnSessionFailure.run();
            this.mRemoteListener.onError(i);
        }

        public final void onEvent(int i, Bundle bundle) {
            this.mRemoteListener.onEvent(i, bundle);
        }

        public final void onLanguageDetection(Bundle bundle) {
            this.mRemoteListener.onLanguageDetection(bundle);
        }

        public final void onPartialResults(Bundle bundle) {
            this.mRemoteListener.onPartialResults(bundle);
        }

        public final void onReadyForSpeech(Bundle bundle) {
            this.mRemoteListener.onReadyForSpeech(bundle);
        }

        public final void onResults(Bundle bundle) {
            this.mOnSessionSuccess.run();
            this.mRemoteListener.onResults(bundle);
        }

        public final void onRmsChanged(float f) {
            this.mRemoteListener.onRmsChanged(f);
        }

        public final void onSegmentResults(Bundle bundle) {
            this.mRemoteListener.onSegmentResults(bundle);
        }
    }

    /* renamed from: $r8$lambda$XM077MW2pg9W0-weK1UAA4Npj6A, reason: not valid java name */
    public static /* synthetic */ void m903$r8$lambda$XM077MW2pg9W0weK1UAA4Npj6A(
            RemoteSpeechRecognitionService remoteSpeechRecognitionService,
            ClientState clientState) {
        synchronized (remoteSpeechRecognitionService.mLock) {
            clientState.mRecordingInProgress = false;
        }
    }

    public RemoteSpeechRecognitionService(
            int i, int i2, ComponentName componentName, Context context, boolean z) {
        super(
                context,
                new Intent("android.speech.RecognitionService").setComponent(componentName),
                z ? 67112961 : 1,
                i,
                new RemoteSpeechRecognitionService$$ExternalSyntheticLambda8());
        this.mLock = new Object();
        this.mConnected = false;
        this.mClients = new HashMap();
        this.mClientListeners = new ArrayList();
        this.mCallingUid = i2;
        this.mComponentName = componentName;
    }

    public static void tryRespondWithError(IRecognitionListener iRecognitionListener, int i) {
        if (iRecognitionListener != null) {
            try {
                iRecognitionListener.onError(i);
            } catch (RemoteException e) {
                Slog.w(
                        "RemoteSpeechRecognitionService",
                        TextUtils.formatSimple(
                                "Failed to respond with an error %d to the client",
                                new Object[] {Integer.valueOf(i)}),
                        e);
            }
        }
    }

    public final void associateClientWithActiveListener(
            IBinder iBinder, IRecognitionListener iRecognitionListener) {
        synchronized (this.mLock) {
            try {
                if (this.mClients.containsKey(iRecognitionListener.asBinder())) {
                    this.mClientListeners.add(new Pair(iBinder, iRecognitionListener));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void cancel(IRecognitionListener iRecognitionListener, final boolean z) {
        if (!this.mConnected) {
            tryRespondWithError(iRecognitionListener, 11);
        }
        synchronized (this.mLock) {
            try {
                ClientState clientState =
                        (ClientState) this.mClients.get(iRecognitionListener.asBinder());
                if (clientState != null) {
                    clientState.mRecordingInProgress = false;
                    final DelegatingListener delegatingListener = clientState.mDelegatingListener;
                    run(
                            new ServiceConnector
                                    .VoidJob() { // from class:
                                                 // com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda1
                                public final void runNoResult(Object obj) {
                                    IRecognitionListener iRecognitionListener2 = delegatingListener;
                                    boolean z2 = z;
                                    int i = RemoteSpeechRecognitionService.$r8$clinit;
                                    ((IRecognitionService) obj).cancel(iRecognitionListener2, z2);
                                }
                            });
                }
                if (z) {
                    removeClient(iRecognitionListener);
                    if (this.mClients.isEmpty()) {
                        run(new RemoteSpeechRecognitionService$$ExternalSyntheticLambda2(0, this));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void checkRecognitionSupport(
            Intent intent,
            AttributionSource attributionSource,
            IRecognitionSupportCallback iRecognitionSupportCallback) {
        if (this.mConnected) {
            run(
                    new RemoteSpeechRecognitionService$$ExternalSyntheticLambda0(
                            intent, attributionSource, iRecognitionSupportCallback));
            return;
        }
        try {
            iRecognitionSupportCallback.onError(11);
        } catch (RemoteException e) {
            Slog.w(
                    "RemoteSpeechRecognitionService",
                    "Failed to report the connection broke to the caller.",
                    e);
            e.printStackTrace();
        }
    }

    public final long getAutoDisconnectTimeoutMs() {
        return 0L;
    }

    public final ComponentName getServiceComponentName() {
        return this.mComponentName;
    }

    public final boolean hasActiveSessions() {
        boolean z;
        synchronized (this.mLock) {
            z = !this.mClients.isEmpty();
        }
        return z;
    }

    public final void onServiceConnectionStatusChanged(IInterface iInterface, boolean z) {
        this.mConnected = z;
        synchronized (this.mLock) {
            if (!z) {
                try {
                    if (this.mClients.isEmpty()) {
                        Slog.i(
                                "RemoteSpeechRecognitionService",
                                "Connection to speech recognition service lost, but no"
                                    + " #startListening has been invoked yet.");
                        return;
                    }
                    for (ClientState clientState :
                            (ClientState[]) this.mClients.values().toArray(new ClientState[0])) {
                        tryRespondWithError(clientState.mDelegatingListener.mRemoteListener, 11);
                        removeClient(clientState.mDelegatingListener.mRemoteListener);
                    }
                } finally {
                }
            }
        }
    }

    public final void removeClient(final IRecognitionListener iRecognitionListener) {
        synchronized (this.mLock) {
            ClientState clientState =
                    (ClientState) this.mClients.remove(iRecognitionListener.asBinder());
            if (clientState != null) {
                clientState.mDelegatingListener = null;
                clientState.mRecordingInProgress = false;
            }
            this.mClientListeners.removeIf(
                    new Predicate() { // from class:
                                      // com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda9
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            IRecognitionListener iRecognitionListener2 = iRecognitionListener;
                            int i = RemoteSpeechRecognitionService.$r8$clinit;
                            return ((Pair) obj).second == iRecognitionListener2;
                        }
                    });
        }
    }

    public final void shutdown(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                for (Pair pair : this.mClientListeners) {
                    if (pair.first == iBinder) {
                        cancel((IRecognitionListener) pair.second, true);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda3] */
    public final void startListening(
            Intent intent,
            final IRecognitionListener iRecognitionListener,
            AttributionSource attributionSource) {
        if (iRecognitionListener == null) {
            Slog.w(
                    "RemoteSpeechRecognitionService",
                    "#startListening called with no preceding #setListening - ignoring.");
            return;
        }
        if (!this.mConnected) {
            tryRespondWithError(iRecognitionListener, 11);
            return;
        }
        synchronized (this.mLock) {
            try {
                final ClientState clientState =
                        (ClientState) this.mClients.get(iRecognitionListener.asBinder());
                if (clientState == null) {
                    if (this.mClients.size() >= 100) {
                        tryRespondWithError(iRecognitionListener, 8);
                        Log.i(
                                "RemoteSpeechRecognitionService",
                                "#startListening received when the recognizer's capacity is full -"
                                    + " ignoring this call.");
                        return;
                    }
                    clientState = new ClientState();
                    clientState.mDelegatingListener = null;
                    clientState.mRecordingInProgress = true;
                    final int i = 0;
                    final int i2 = 1;
                    clientState.mDelegatingListener =
                            new DelegatingListener(
                                    iRecognitionListener,
                                    new Runnable(
                                            this) { // from class:
                                                    // com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda3
                                        public final /* synthetic */ RemoteSpeechRecognitionService
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i) {
                                                case 0:
                                                    RemoteSpeechRecognitionService
                                                            .m903$r8$lambda$XM077MW2pg9W0weK1UAA4Npj6A(
                                                                    this.f$0,
                                                                    (RemoteSpeechRecognitionService
                                                                                    .ClientState)
                                                                            clientState);
                                                    break;
                                                default:
                                                    RemoteSpeechRecognitionService
                                                            remoteSpeechRecognitionService =
                                                                    this.f$0;
                                                    IRecognitionListener iRecognitionListener2 =
                                                            (IRecognitionListener) clientState;
                                                    int i3 =
                                                            RemoteSpeechRecognitionService
                                                                    .$r8$clinit;
                                                    remoteSpeechRecognitionService.removeClient(
                                                            iRecognitionListener2);
                                                    break;
                                            }
                                        }
                                    },
                                    new Runnable(
                                            this) { // from class:
                                                    // com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda3
                                        public final /* synthetic */ RemoteSpeechRecognitionService
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i2) {
                                                case 0:
                                                    RemoteSpeechRecognitionService
                                                            .m903$r8$lambda$XM077MW2pg9W0weK1UAA4Npj6A(
                                                                    this.f$0,
                                                                    (RemoteSpeechRecognitionService
                                                                                    .ClientState)
                                                                            iRecognitionListener);
                                                    break;
                                                default:
                                                    RemoteSpeechRecognitionService
                                                            remoteSpeechRecognitionService =
                                                                    this.f$0;
                                                    IRecognitionListener iRecognitionListener2 =
                                                            (IRecognitionListener)
                                                                    iRecognitionListener;
                                                    int i3 =
                                                            RemoteSpeechRecognitionService
                                                                    .$r8$clinit;
                                                    remoteSpeechRecognitionService.removeClient(
                                                            iRecognitionListener2);
                                                    break;
                                            }
                                        }
                                    });
                    this.mClients.put(iRecognitionListener.asBinder(), clientState);
                } else {
                    if (clientState.mRecordingInProgress) {
                        Slog.i(
                                "RemoteSpeechRecognitionService",
                                "#startListening called while listening is in progress for this"
                                    + " caller.");
                        tryRespondWithError(iRecognitionListener, 5);
                        return;
                    }
                    clientState.mRecordingInProgress = true;
                }
                run(
                        new RemoteSpeechRecognitionService$$ExternalSyntheticLambda0(
                                intent, clientState.mDelegatingListener, attributionSource));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopListening(IRecognitionListener iRecognitionListener) {
        if (!this.mConnected) {
            tryRespondWithError(iRecognitionListener, 11);
            return;
        }
        synchronized (this.mLock) {
            try {
                ClientState clientState =
                        (ClientState) this.mClients.get(iRecognitionListener.asBinder());
                if (clientState == null) {
                    Slog.w(
                            "RemoteSpeechRecognitionService",
                            "#stopListening called with no preceding #startListening - ignoring.");
                    tryRespondWithError(iRecognitionListener, 5);
                } else if (clientState.mRecordingInProgress) {
                    clientState.mRecordingInProgress = false;
                    run(
                            new RemoteSpeechRecognitionService$$ExternalSyntheticLambda2(
                                    1, clientState.mDelegatingListener));
                } else {
                    tryRespondWithError(iRecognitionListener, 5);
                    Slog.i(
                            "RemoteSpeechRecognitionService",
                            "#stopListening called while listening isn't in progress - ignoring.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void triggerModelDownload(
            Intent intent,
            AttributionSource attributionSource,
            IModelDownloadListener iModelDownloadListener) {
        if (this.mConnected) {
            run(
                    new RemoteSpeechRecognitionService$$ExternalSyntheticLambda0(
                            intent, attributionSource, iModelDownloadListener));
            return;
        }
        try {
            iModelDownloadListener.onError(11);
        } catch (RemoteException e) {
            Slog.w("RemoteSpeechRecognitionService", "#downloadModel failed due to connection.", e);
            e.printStackTrace();
        }
    }
}
