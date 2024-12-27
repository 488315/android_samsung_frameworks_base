package android.service.dreams;

import android.content.Context;
import android.content.Intent;
import android.media.audio.Enums;
import android.media.midi.MidiManager$$ExternalSyntheticLambda0;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.android.internal.util.ObservableServiceConnection;
import com.android.internal.util.PersistentServiceConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class DreamOverlayConnectionHandler {
    private static final int MSG_ADD_CONSUMER = 1;
    private static final int MSG_OVERLAY_CLIENT_READY = 3;
    private static final int MSG_REMOVE_CONSUMER = 2;
    private static final String TAG = "DreamOverlayConnection";
    private final OverlayConnectionCallback mCallback;
    private IDreamOverlayClient mClient;
    private final PersistentServiceConnection<IDreamOverlay> mConnection;
    private final List<Consumer<IDreamOverlayClient>> mConsumers;
    private final Handler mHandler;

    DreamOverlayConnectionHandler(
            Context context,
            Looper looper,
            Intent serviceIntent,
            int minConnectionDurationMs,
            int maxReconnectAttempts,
            int baseReconnectDelayMs) {
        this(
                context,
                looper,
                serviceIntent,
                minConnectionDurationMs,
                maxReconnectAttempts,
                baseReconnectDelayMs,
                new Injector());
    }

    public DreamOverlayConnectionHandler(
            Context context,
            Looper looper,
            Intent intent,
            int i,
            int i2,
            int i3,
            Injector injector) {
        this.mConsumers = new ArrayList();
        this.mCallback = new OverlayConnectionCallback();
        this.mHandler = new Handler(looper, new OverlayHandlerCallback());
        this.mConnection = injector.buildConnection(context, this.mHandler, intent, i, i2, i3);
    }

    public boolean bind() {
        this.mConnection.addCallback(this.mCallback);
        boolean success = this.mConnection.bind();
        if (!success) {
            unbind();
        }
        return success;
    }

    public void unbind() {
        this.mConnection.removeCallback(this.mCallback);
        this.mHandler.removeCallbacksAndMessages(null);
        this.mClient = null;
        this.mConsumers.clear();
        this.mConnection.unbind();
    }

    public void addConsumer(Consumer<IDreamOverlayClient> consumer) {
        Message msg = this.mHandler.obtainMessage(1, consumer);
        this.mHandler.sendMessage(msg);
    }

    public void removeConsumer(Consumer<IDreamOverlayClient> consumer) {
        Message msg = this.mHandler.obtainMessage(2, consumer);
        this.mHandler.sendMessage(msg);
        this.mHandler.removeMessages(1, consumer);
    }

    private final class OverlayHandlerCallback implements Handler.Callback {
        private OverlayHandlerCallback() {}

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    DreamOverlayConnectionHandler.this.onAddConsumer((Consumer) msg.obj);
                    break;
                case 2:
                    DreamOverlayConnectionHandler.this.onRemoveConsumer((Consumer) msg.obj);
                    break;
                case 3:
                    DreamOverlayConnectionHandler.this.onOverlayClientReady(
                            (IDreamOverlayClient) msg.obj);
                    break;
            }
            return true;
        }
    }

    public void onOverlayClientReady(IDreamOverlayClient client) {
        this.mClient = client;
        for (Consumer<IDreamOverlayClient> consumer : this.mConsumers) {
            consumer.accept(this.mClient);
        }
    }

    public void onAddConsumer(Consumer<IDreamOverlayClient> consumer) {
        if (this.mClient != null) {
            consumer.accept(this.mClient);
        }
        this.mConsumers.add(consumer);
    }

    public void onRemoveConsumer(Consumer<IDreamOverlayClient> consumer) {
        this.mConsumers.remove(consumer);
    }

    private final class OverlayConnectionCallback
            implements ObservableServiceConnection.Callback<IDreamOverlay> {
        private final IDreamOverlayClientCallback mClientCallback;

        private OverlayConnectionCallback() {
            this.mClientCallback =
                    new IDreamOverlayClientCallback
                            .Stub() { // from class:
                                      // android.service.dreams.DreamOverlayConnectionHandler.OverlayConnectionCallback.1
                        @Override // android.service.dreams.IDreamOverlayClientCallback
                        public void onDreamOverlayClient(IDreamOverlayClient client) {
                            Message msg =
                                    DreamOverlayConnectionHandler.this.mHandler.obtainMessage(
                                            3, client);
                            DreamOverlayConnectionHandler.this.mHandler.sendMessage(msg);
                        }
                    };
        }

        @Override // com.android.internal.util.ObservableServiceConnection.Callback
        public void onConnected(
                ObservableServiceConnection<IDreamOverlay> connection, IDreamOverlay service) {
            try {
                service.getClient(this.mClientCallback);
            } catch (RemoteException e) {
                Log.e(DreamOverlayConnectionHandler.TAG, "could not get DreamOverlayClient", e);
            }
        }

        @Override // com.android.internal.util.ObservableServiceConnection.Callback
        public void onDisconnected(
                ObservableServiceConnection<IDreamOverlay> connection, int reason) {
            DreamOverlayConnectionHandler.this.mClient = null;
            DreamOverlayConnectionHandler.this.mHandler.removeMessages(3);
        }
    }

    public static class Injector {
        public PersistentServiceConnection<IDreamOverlay> buildConnection(
                Context context,
                Handler handler,
                Intent serviceIntent,
                int minConnectionDurationMs,
                int maxReconnectAttempts,
                int baseReconnectDelayMs) {
            Objects.requireNonNull(handler);
            Executor executor = new MidiManager$$ExternalSyntheticLambda0(handler);
            return new PersistentServiceConnection<>(
                    context,
                    executor,
                    handler,
                    new ObservableServiceConnection
                            .ServiceTransformer() { // from class:
                                                    // android.service.dreams.DreamOverlayConnectionHandler$Injector$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.ObservableServiceConnection.ServiceTransformer
                        public final Object convert(IBinder iBinder) {
                            return IDreamOverlay.Stub.asInterface(iBinder);
                        }
                    },
                    serviceIntent,
                    Enums.AUDIO_FORMAT_AAC_MAIN,
                    minConnectionDurationMs,
                    maxReconnectAttempts,
                    baseReconnectDelayMs);
        }
    }
}
