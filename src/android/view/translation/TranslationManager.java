package android.view.translation;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SynchronousResultReceiver;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.view.translation.Translator;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.SyncResultReceiver;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes4.dex */
public final class TranslationManager {
    public static final String EXTRA_CAPABILITIES = "translation_capabilities";
    public static final int STATUS_SYNC_CALL_FAIL = 2;
    public static final int STATUS_SYNC_CALL_SUCCESS = 1;
    static final int SYNC_CALLS_TIMEOUT_MS = 60000;
    private static final String TAG = "TranslationManager";
    private final Context mContext;
    private final ITranslationManager mService;
    private static final SecureRandom ID_GENERATOR = new SecureRandom();
    private static final AtomicInteger sAvailableRequestId = new AtomicInteger(1);
    private final ArrayMap<Pair<Integer, Integer>, ArrayList<PendingIntent>> mTranslationCapabilityUpdateListeners = new ArrayMap<>();
    private final Map<Consumer<TranslationCapability>, IRemoteCallback> mCapabilityCallbacks = new ArrayMap();
    private final Object mLock = new Object();
    private final IntArray mTranslatorIds = new IntArray();
    private final Handler mHandler = Handler.createAsync(Looper.getMainLooper());

    public TranslationManager(Context context, ITranslationManager iTranslationManager) {
        this.mContext = (Context) Objects.requireNonNull(context, "context cannot be null");
        this.mService = iTranslationManager;
    }

    public void createOnDeviceTranslator(TranslationContext translationContext, final Executor executor, final Consumer<Translator> consumer) {
        Objects.requireNonNull(translationContext, "translationContext cannot be null");
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(consumer, "callback cannot be null");
        synchronized (this.mLock) {
            while (true) {
                final int iAbs = Math.abs(ID_GENERATOR.nextInt());
                if (iAbs != 0 && this.mTranslatorIds.indexOf(iAbs) < 0) {
                    new Translator(this.mContext, translationContext, iAbs, this, this.mHandler, this.mService, new Consumer() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            this.f$0.lambda$createOnDeviceTranslator$4(executor, consumer, iAbs, (Translator) obj);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnDeviceTranslator$4(final Executor executor, final Consumer consumer, int i, final Translator translator) {
        if (translator == null) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            consumer.accept(null);
                        }
                    });
                }
            });
            return;
        }
        synchronized (this.mLock) {
            this.mTranslatorIds.add(i);
        }
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
                executor.execute(new Runnable() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(translator);
                    }
                });
            }
        });
    }

    @Deprecated
    public Translator createOnDeviceTranslator(TranslationContext translationContext) {
        int iAbs;
        Objects.requireNonNull(translationContext, "translationContext cannot be null");
        synchronized (this.mLock) {
            while (true) {
                iAbs = Math.abs(ID_GENERATOR.nextInt());
                if (iAbs != 0 && this.mTranslatorIds.indexOf(iAbs) < 0) {
                    break;
                }
            }
            Translator translator = new Translator(this.mContext, translationContext, iAbs, this, this.mHandler, this.mService);
            translator.start();
            try {
                if (!translator.isSessionCreated()) {
                    return null;
                }
                this.mTranslatorIds.add(iAbs);
                return translator;
            } catch (Translator.ServiceBinderReceiver.TimeoutException e) {
                Log.e(TAG, "Timed out getting create session: " + e);
                return null;
            }
        }
    }

    @Deprecated
    public Translator createTranslator(TranslationContext translationContext) {
        return createOnDeviceTranslator(translationContext);
    }

    public Set<TranslationCapability> getOnDeviceTranslationCapabilities(int i, int i2) {
        try {
            SynchronousResultReceiver synchronousResultReceiver = new SynchronousResultReceiver();
            this.mService.onTranslationCapabilitiesRequest(i, i2, synchronousResultReceiver, this.mContext.getUserId());
            SynchronousResultReceiver.Result resultAwaitResult = synchronousResultReceiver.awaitResult(60000L);
            if (resultAwaitResult.resultCode != 1) {
                return Collections.EMPTY_SET;
            }
            ParceledListSlice parceledListSlice = (ParceledListSlice) resultAwaitResult.bundle.getParcelable(EXTRA_CAPABILITIES, ParceledListSlice.class);
            return new ArraySet(parceledListSlice == null ? null : parceledListSlice.getList());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (TimeoutException e2) {
            Log.e(TAG, "Timed out getting supported translation capabilities: " + e2);
            return Collections.EMPTY_SET;
        }
    }

    @Deprecated
    public Set<TranslationCapability> getTranslationCapabilities(int i, int i2) {
        return getOnDeviceTranslationCapabilities(i, i2);
    }

    public void addOnDeviceTranslationCapabilityUpdateListener(Executor executor, Consumer<TranslationCapability> consumer) {
        Objects.requireNonNull(executor, "executor should not be null");
        Objects.requireNonNull(consumer, "capability listener should not be null");
        synchronized (this.mLock) {
            if (this.mCapabilityCallbacks.containsKey(consumer)) {
                Log.w(TAG, "addOnDeviceTranslationCapabilityUpdateListener: the listener for " + consumer + " already registered; ignoring.");
                return;
            }
            TranslationCapabilityRemoteCallback translationCapabilityRemoteCallback = new TranslationCapabilityRemoteCallback(executor, consumer);
            try {
                this.mService.registerTranslationCapabilityCallback(translationCapabilityRemoteCallback, this.mContext.getUserId());
                this.mCapabilityCallbacks.put(consumer, translationCapabilityRemoteCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void addOnDeviceTranslationCapabilityUpdateListener(int i, int i2, PendingIntent pendingIntent) {
        Objects.requireNonNull(pendingIntent, "pending intent should not be null");
        synchronized (this.mLock) {
            this.mTranslationCapabilityUpdateListeners.computeIfAbsent(new Pair<>(Integer.valueOf(i), Integer.valueOf(i2)), new Function() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return TranslationManager.lambda$addOnDeviceTranslationCapabilityUpdateListener$5((Pair) obj);
                }
            }).add(pendingIntent);
        }
    }

    static /* synthetic */ ArrayList lambda$addOnDeviceTranslationCapabilityUpdateListener$5(Pair pair) {
        return new ArrayList();
    }

    @Deprecated
    public void addTranslationCapabilityUpdateListener(int i, int i2, PendingIntent pendingIntent) {
        addOnDeviceTranslationCapabilityUpdateListener(i, i2, pendingIntent);
    }

    public void removeOnDeviceTranslationCapabilityUpdateListener(Consumer<TranslationCapability> consumer) {
        Objects.requireNonNull(consumer, "capability callback should not be null");
        synchronized (this.mLock) {
            IRemoteCallback iRemoteCallback = this.mCapabilityCallbacks.get(consumer);
            if (iRemoteCallback == null) {
                Log.w(TAG, "removeOnDeviceTranslationCapabilityUpdateListener: the capability listener not found; ignoring.");
                return;
            }
            try {
                this.mService.unregisterTranslationCapabilityCallback(iRemoteCallback, this.mContext.getUserId());
                this.mCapabilityCallbacks.remove(consumer);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void removeOnDeviceTranslationCapabilityUpdateListener(int i, int i2, PendingIntent pendingIntent) {
        Objects.requireNonNull(pendingIntent, "pending intent should not be null");
        synchronized (this.mLock) {
            Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i2));
            if (this.mTranslationCapabilityUpdateListeners.containsKey(pair)) {
                ArrayList<PendingIntent> arrayList = this.mTranslationCapabilityUpdateListeners.get(pair);
                if (arrayList.contains(pendingIntent)) {
                    arrayList.remove(pendingIntent);
                } else {
                    Log.w(TAG, "pending intent=" + pendingIntent + " does not exist in mTranslationCapabilityUpdateListeners");
                }
            } else {
                Log.w(TAG, "format pair=" + pair + " does not exist in mTranslationCapabilityUpdateListeners");
            }
        }
    }

    @Deprecated
    public void removeTranslationCapabilityUpdateListener(int i, int i2, PendingIntent pendingIntent) {
        removeOnDeviceTranslationCapabilityUpdateListener(i, i2, pendingIntent);
    }

    public PendingIntent getOnDeviceTranslationSettingsActivityIntent() {
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(60000);
        try {
            this.mService.getServiceSettingsActivity(syncResultReceiver, this.mContext.getUserId());
            try {
                return (PendingIntent) syncResultReceiver.getParcelableResult();
            } catch (SyncResultReceiver.TimeoutException unused) {
                Log.e(TAG, "Fail to get translation service settings activity.");
                return null;
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public PendingIntent getTranslationSettingsActivityIntent() {
        return getOnDeviceTranslationSettingsActivityIntent();
    }

    void removeTranslator(int i) {
        synchronized (this.mLock) {
            int iIndexOf = this.mTranslatorIds.indexOf(i);
            if (iIndexOf >= 0) {
                this.mTranslatorIds.remove(iIndexOf);
            }
        }
    }

    AtomicInteger getAvailableRequestId() {
        AtomicInteger atomicInteger;
        synchronized (this.mLock) {
            atomicInteger = sAvailableRequestId;
        }
        return atomicInteger;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TranslationCapabilityRemoteCallback extends IRemoteCallback.Stub {
        private final Executor mExecutor;
        private final Consumer<TranslationCapability> mListener;

        TranslationCapabilityRemoteCallback(Executor executor, Consumer<TranslationCapability> consumer) {
            this.mExecutor = executor;
            this.mListener = consumer;
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(final Bundle bundle) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.view.translation.TranslationManager$TranslationCapabilityRemoteCallback$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$sendResult$1(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendResult$1(final Bundle bundle) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.view.translation.TranslationManager$TranslationCapabilityRemoteCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendResult$0(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: onTranslationCapabilityUpdate, reason: merged with bridge method [inline-methods] */
        public void lambda$sendResult$0(Bundle bundle) {
            this.mListener.accept((TranslationCapability) bundle.getParcelable(TranslationManager.EXTRA_CAPABILITIES, TranslationCapability.class));
        }
    }
}
