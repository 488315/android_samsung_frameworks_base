package android.service.translation;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.service.translation.ITranslationService;
import android.util.Log;
import android.view.translation.ITranslationDirectManager;
import android.view.translation.ITranslationServiceCallback;
import android.view.translation.TranslationCapability;
import android.view.translation.TranslationContext;
import android.view.translation.TranslationManager;
import android.view.translation.TranslationRequest;
import android.view.translation.TranslationResponse;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class TranslationService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.translation.TranslationService";
    public static final String SERVICE_META_DATA = "android.translation_service";
    private static final String TAG = "TranslationService";
    private ITranslationServiceCallback mCallback;
    private Handler mHandler;
    private final ITranslationService mInterface = new AnonymousClass1();
    private final ITranslationDirectManager mClientInterface = new ITranslationDirectManager.Stub() { // from class: android.service.translation.TranslationService.2
        @Override // android.view.translation.ITranslationDirectManager
        public void onTranslationRequest(TranslationRequest translationRequest, int i, ICancellationSignal iCancellationSignal, ITranslationCallback iTranslationCallback) throws RemoteException {
            TranslationService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: android.service.translation.TranslationService$2$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    ((TranslationService) obj).onTranslationRequest((TranslationRequest) obj2, ((Integer) obj3).intValue(), (CancellationSignal) obj4, (Consumer<TranslationResponse>) obj5);
                }
            }, TranslationService.this, translationRequest, Integer.valueOf(i), CancellationSignal.fromTransport(iCancellationSignal), new OnTranslationResultCallbackWrapper(iTranslationCallback)));
        }

        @Override // android.view.translation.ITranslationDirectManager
        public void onFinishTranslationSession(int i) throws RemoteException {
            TranslationService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.translation.TranslationService$2$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((TranslationService) obj).onFinishTranslationSession(((Integer) obj2).intValue());
                }
            }, TranslationService.this, Integer.valueOf(i)));
        }
    };

    @Deprecated
    public interface OnTranslationResultCallback {
        @Deprecated
        void onError();

        void onTranslationSuccess(TranslationResponse translationResponse);
    }

    public void onConnected() {
    }

    @Deprecated
    public void onCreateTranslationSession(TranslationContext translationContext, int i) {
    }

    public abstract void onCreateTranslationSession(TranslationContext translationContext, int i, Consumer<Boolean> consumer);

    public void onDisconnected() {
    }

    public abstract void onFinishTranslationSession(int i);

    public abstract void onTranslationCapabilitiesRequest(int i, int i2, Consumer<Set<TranslationCapability>> consumer);

    @Deprecated
    public void onTranslationRequest(TranslationRequest translationRequest, int i, CancellationSignal cancellationSignal, OnTranslationResultCallback onTranslationResultCallback) {
    }

    public abstract void onTranslationRequest(TranslationRequest translationRequest, int i, CancellationSignal cancellationSignal, Consumer<TranslationResponse> consumer);

    /* renamed from: android.service.translation.TranslationService$1, reason: invalid class name */
    class AnonymousClass1 extends ITranslationService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.translation.ITranslationService
        public void onConnected(IBinder iBinder) {
            TranslationService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.translation.TranslationService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((TranslationService) obj).handleOnConnected((IBinder) obj2);
                }
            }, TranslationService.this, iBinder));
        }

        @Override // android.service.translation.ITranslationService
        public void onDisconnected() {
            TranslationService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.translation.TranslationService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((TranslationService) obj).onDisconnected();
                }
            }, TranslationService.this));
        }

        @Override // android.service.translation.ITranslationService
        public void onCreateTranslationSession(TranslationContext translationContext, int i, IResultReceiver iResultReceiver) throws RemoteException {
            TranslationService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.translation.TranslationService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((TranslationService) obj).handleOnCreateTranslationSession((TranslationContext) obj2, ((Integer) obj3).intValue(), (IResultReceiver) obj4);
                }
            }, TranslationService.this, translationContext, Integer.valueOf(i), iResultReceiver));
        }

        @Override // android.service.translation.ITranslationService
        public void onTranslationCapabilitiesRequest(int i, int i2, ResultReceiver resultReceiver) throws RemoteException {
            TranslationService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.translation.TranslationService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((TranslationService) obj).handleOnTranslationCapabilitiesRequest(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (ResultReceiver) obj4);
                }
            }, TranslationService.this, Integer.valueOf(i), Integer.valueOf(i2), resultReceiver));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
        BaseBundle.setShouldDefuse(true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.translation.TranslationService: " + intent);
        return null;
    }

    public final void updateTranslationCapability(TranslationCapability translationCapability) {
        Objects.requireNonNull(translationCapability, "translation capability should not be null");
        ITranslationServiceCallback iTranslationServiceCallback = this.mCallback;
        if (iTranslationServiceCallback == null) {
            Log.w(TAG, "updateTranslationCapability(): no server callback");
            return;
        }
        try {
            iTranslationServiceCallback.updateTranslationCapability(translationCapability);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnected(IBinder iBinder) {
        this.mCallback = ITranslationServiceCallback.Stub.asInterface(iBinder);
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnCreateTranslationSession(final TranslationContext translationContext, final int i, final IResultReceiver iResultReceiver) {
        onCreateTranslationSession(translationContext, i, new Consumer<Boolean>() { // from class: android.service.translation.TranslationService.3
            @Override // java.util.function.Consumer
            public void accept(Boolean bool) {
                try {
                    if (bool.booleanValue()) {
                        Bundle bundle = new Bundle();
                        bundle.putBinder("binder", TranslationService.this.mClientInterface.asBinder());
                        bundle.putInt("sessionId", i);
                        iResultReceiver.send(1, bundle);
                        return;
                    }
                    Log.w(TranslationService.TAG, "handleOnCreateTranslationSession(): context=" + translationContext + " not supported by service.");
                    iResultReceiver.send(2, null);
                } catch (RemoteException e) {
                    Log.w(TranslationService.TAG, "RemoteException sending client interface: " + e);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnTranslationCapabilitiesRequest(final int i, final int i2, final ResultReceiver resultReceiver) {
        onTranslationCapabilitiesRequest(i, i2, new Consumer<Set<TranslationCapability>>() { // from class: android.service.translation.TranslationService.4
            @Override // java.util.function.Consumer
            public void accept(Set<TranslationCapability> set) {
                if (!TranslationService.this.isValidCapabilities(i, i2, set)) {
                    throw new IllegalStateException("Invalid capabilities and format compatibility");
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(TranslationManager.EXTRA_CAPABILITIES, new ParceledListSlice(Arrays.asList((TranslationCapability[]) set.toArray(new TranslationCapability[0]))));
                resultReceiver.send(1, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidCapabilities(int i, int i2, Set<TranslationCapability> set) {
        if (i != 1 && i2 != 1) {
            return true;
        }
        Iterator<TranslationCapability> it = set.iterator();
        while (it.hasNext()) {
            if (it.next().getState() == 1000) {
                return false;
            }
        }
        return true;
    }
}
