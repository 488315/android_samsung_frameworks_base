package android.security.intrusiondetection;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback;
import android.security.intrusiondetection.IIntrusionDetectionServiceStateCallback;
import android.util.Log;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public class IntrusionDetectionManager {
    public static final int ERROR_DATA_SOURCE_UNAVAILABLE = 4;
    public static final int ERROR_PERMISSION_DENIED = 1;
    public static final int ERROR_TRANSPORT_UNAVAILABLE = 3;
    public static final int ERROR_UNKNOWN = 0;
    public static final int STATE_DISABLED = 1;
    public static final int STATE_ENABLED = 2;
    public static final int STATE_UNKNOWN = 0;
    private static final String TAG = "IntrusionDetectionManager";
    private final IIntrusionDetectionService mService;
    private final ConcurrentHashMap<Consumer<Integer>, IIntrusionDetectionServiceStateCallback> mStateCallbacks = new ConcurrentHashMap<>();

    public interface CommandCallback {
        void onFailure(int i);

        void onSuccess();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IntrusionDetectionError {
    }

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IntrusionDetectionState {
    }

    public IntrusionDetectionManager(IIntrusionDetectionService iIntrusionDetectionService) {
        this.mService = iIntrusionDetectionService;
    }

    public void addStateCallback(Executor executor, Consumer<Integer> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        if (this.mStateCallbacks.get(consumer) != null) {
            Log.d(TAG, "addStateCallback callback already present");
            return;
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, executor, consumer);
        try {
            this.mService.addStateCallback(anonymousClass1);
            this.mStateCallbacks.put(consumer, anonymousClass1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.security.intrusiondetection.IntrusionDetectionManager$1, reason: invalid class name */
    class AnonymousClass1 extends IIntrusionDetectionServiceStateCallback.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(IntrusionDetectionManager intrusionDetectionManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionServiceStateCallback
        public void onStateChange(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(new Runnable() { // from class: android.security.intrusiondetection.IntrusionDetectionManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(Integer.valueOf(i));
                }
            });
        }
    }

    public void removeStateCallback(Consumer<Integer> consumer) {
        Objects.requireNonNull(consumer);
        if (!this.mStateCallbacks.containsKey(consumer)) {
            Log.d(TAG, "removeStateCallback callback not present");
            return;
        }
        try {
            this.mService.removeStateCallback(this.mStateCallbacks.get(consumer));
            this.mStateCallbacks.remove(consumer);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enable(Executor executor, CommandCallback commandCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(commandCallback);
        try {
            this.mService.enable(new AnonymousClass2(this, executor, commandCallback));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.security.intrusiondetection.IntrusionDetectionManager$2, reason: invalid class name */
    class AnonymousClass2 extends IIntrusionDetectionServiceCommandCallback.Stub {
        final /* synthetic */ CommandCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass2(IntrusionDetectionManager intrusionDetectionManager, Executor executor, CommandCallback commandCallback) {
            this.val$executor = executor;
            this.val$callback = commandCallback;
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback
        public void onSuccess() {
            Executor executor = this.val$executor;
            CommandCallback commandCallback = this.val$callback;
            Objects.requireNonNull(commandCallback);
            executor.execute(new IntrusionDetectionManager$2$$ExternalSyntheticLambda1(commandCallback));
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback
        public void onFailure(final int i) {
            Executor executor = this.val$executor;
            final CommandCallback commandCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.security.intrusiondetection.IntrusionDetectionManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    commandCallback.onFailure(i);
                }
            });
        }
    }

    public void disable(Executor executor, CommandCallback commandCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(commandCallback);
        try {
            this.mService.disable(new AnonymousClass3(this, executor, commandCallback));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.security.intrusiondetection.IntrusionDetectionManager$3, reason: invalid class name */
    class AnonymousClass3 extends IIntrusionDetectionServiceCommandCallback.Stub {
        final /* synthetic */ CommandCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass3(IntrusionDetectionManager intrusionDetectionManager, Executor executor, CommandCallback commandCallback) {
            this.val$executor = executor;
            this.val$callback = commandCallback;
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback
        public void onSuccess() {
            Executor executor = this.val$executor;
            CommandCallback commandCallback = this.val$callback;
            Objects.requireNonNull(commandCallback);
            executor.execute(new IntrusionDetectionManager$2$$ExternalSyntheticLambda1(commandCallback));
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback
        public void onFailure(final int i) {
            Executor executor = this.val$executor;
            final CommandCallback commandCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.security.intrusiondetection.IntrusionDetectionManager$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    commandCallback.onFailure(i);
                }
            });
        }
    }
}
