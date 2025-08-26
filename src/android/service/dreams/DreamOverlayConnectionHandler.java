package android.service.dreams;

import android.appwidget.AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1;
import android.content.Context;
import android.content.Intent;
import android.media.audio.Enums;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.service.dreams.IDreamOverlay;
import android.service.dreams.IDreamOverlayClientCallback;
import android.util.Log;
import com.android.internal.util.ObservableServiceConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class DreamOverlayConnectionHandler {
    private static final int MSG_ADD_CONSUMER = 1;
    private static final int MSG_OVERLAY_CLIENT_READY = 3;
    private static final int MSG_REMOVE_CONSUMER = 2;
    private static final String TAG = "DreamOverlayConnection";
    private final OverlayConnectionCallback mCallback;
    private IDreamOverlayClient mClient;
    private final ObservableServiceConnection<IDreamOverlay> mConnection;
    private final List<Consumer<IDreamOverlayClient>> mConsumers;
    private final Handler mHandler;
    private final Runnable mOnDisconnected;

    DreamOverlayConnectionHandler(Context context, Looper looper, Intent intent, Runnable runnable) {
        this(context, looper, intent, runnable, new Injector());
    }

    public DreamOverlayConnectionHandler(Context context, Looper looper, Intent intent, Runnable runnable, Injector injector) {
        this.mConsumers = new ArrayList();
        this.mCallback = new OverlayConnectionCallback();
        Handler handler = new Handler(looper, new OverlayHandlerCallback());
        this.mHandler = handler;
        this.mOnDisconnected = runnable;
        this.mConnection = injector.buildConnection(context, handler, intent);
    }

    public boolean bind() {
        this.mConnection.addCallback(this.mCallback);
        boolean zBind = this.mConnection.bind();
        if (!zBind) {
            unbind();
        }
        return zBind;
    }

    public void unbind() {
        this.mConnection.removeCallback(this.mCallback);
        this.mHandler.removeCallbacksAndMessages(null);
        this.mClient = null;
        this.mConsumers.clear();
        this.mConnection.unbind();
    }

    public void addConsumer(Consumer<IDreamOverlayClient> consumer) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, consumer));
    }

    public void removeConsumer(Consumer<IDreamOverlayClient> consumer) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, consumer));
        this.mHandler.removeMessages(1, consumer);
    }

    private final class OverlayHandlerCallback implements Handler.Callback {
        private OverlayHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                DreamOverlayConnectionHandler.this.onAddConsumer((Consumer) message.obj);
            } else if (i == 2) {
                DreamOverlayConnectionHandler.this.onRemoveConsumer((Consumer) message.obj);
            } else if (i == 3) {
                DreamOverlayConnectionHandler.this.onOverlayClientReady((IDreamOverlayClient) message.obj);
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onOverlayClientReady(IDreamOverlayClient iDreamOverlayClient) {
        this.mClient = iDreamOverlayClient;
        Iterator<Consumer<IDreamOverlayClient>> it = this.mConsumers.iterator();
        while (it.hasNext()) {
            it.next().accept(this.mClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAddConsumer(Consumer<IDreamOverlayClient> consumer) {
        IDreamOverlayClient iDreamOverlayClient = this.mClient;
        if (iDreamOverlayClient != null) {
            consumer.accept(iDreamOverlayClient);
        }
        this.mConsumers.add(consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRemoveConsumer(Consumer<IDreamOverlayClient> consumer) {
        this.mConsumers.remove(consumer);
    }

    private final class OverlayConnectionCallback implements ObservableServiceConnection.Callback<IDreamOverlay> {
        private final IDreamOverlayClientCallback mClientCallback;

        private OverlayConnectionCallback() {
            this.mClientCallback = new IDreamOverlayClientCallback.Stub() { // from class: android.service.dreams.DreamOverlayConnectionHandler.OverlayConnectionCallback.1
                @Override // android.service.dreams.IDreamOverlayClientCallback
                public void onDreamOverlayClient(IDreamOverlayClient iDreamOverlayClient) {
                    DreamOverlayConnectionHandler.this.mHandler.sendMessage(DreamOverlayConnectionHandler.this.mHandler.obtainMessage(3, iDreamOverlayClient));
                }
            };
        }

        @Override // com.android.internal.util.ObservableServiceConnection.Callback
        public void onConnected(ObservableServiceConnection<IDreamOverlay> observableServiceConnection, IDreamOverlay iDreamOverlay) {
            try {
                iDreamOverlay.getClient(this.mClientCallback);
            } catch (RemoteException e) {
                Log.e(DreamOverlayConnectionHandler.TAG, "could not get DreamOverlayClient", e);
            }
        }

        @Override // com.android.internal.util.ObservableServiceConnection.Callback
        public void onDisconnected(ObservableServiceConnection<IDreamOverlay> observableServiceConnection, int i) {
            Log.i(DreamOverlayConnectionHandler.TAG, "Dream overlay disconnected, reason: " + i);
            DreamOverlayConnectionHandler.this.mClient = null;
            DreamOverlayConnectionHandler.this.mHandler.removeMessages(3);
            if (DreamOverlayConnectionHandler.this.mOnDisconnected != null) {
                DreamOverlayConnectionHandler.this.mOnDisconnected.run();
            }
        }
    }

    public static class Injector {
        public ObservableServiceConnection<IDreamOverlay> buildConnection(Context context, Handler handler, Intent intent) {
            Objects.requireNonNull(handler);
            return new ObservableServiceConnection<>(context, new AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1(handler), new ObservableServiceConnection.ServiceTransformer() { // from class: android.service.dreams.DreamOverlayConnectionHandler$Injector$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.ObservableServiceConnection.ServiceTransformer
                public final Object convert(IBinder iBinder) {
                    return IDreamOverlay.Stub.asInterface(iBinder);
                }
            }, intent, Enums.AUDIO_FORMAT_AAC_MAIN);
        }
    }
}
