package com.android.systemui.util.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.IndentingPrintWriter;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.service.ObservableServiceConnection;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ObservableServiceConnection<T> implements ServiceConnection {
    public static final int DISCONNECT_REASON_BINDING_DIED = 3;
    public static final int DISCONNECT_REASON_DISCONNECTED = 2;
    public static final int DISCONNECT_REASON_NULL_BINDING = 1;
    public static final int DISCONNECT_REASON_UNBIND = 4;
    private final Executor mBgExecutor;
    private boolean mBoundCalled;
    private final Context mContext;
    private T mProxy;
    private final Intent mServiceIntent;
    private final ServiceTransformer<T> mTransformer;
    private final UserTracker mUserTracker;
    private static final String TAG = "ObservableSvcConn";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private final int mFlags = 1;
    private final ArrayList<WeakReference<Callback<T>>> mCallbacks = new ArrayList<>();
    private Optional<Integer> mLastDisconnectReason = Optional.empty();

    public interface Callback<T> {
        void onConnected(ObservableServiceConnection<T> observableServiceConnection, T t);

        void onDisconnected(ObservableServiceConnection<T> observableServiceConnection, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisconnectReason {
    }

    public interface ServiceTransformer<T> {
        T convert(IBinder iBinder);
    }

    public ObservableServiceConnection(Context context, Intent intent, UserTracker userTracker, Executor executor, ServiceTransformer<T> serviceTransformer) {
        this.mContext = context;
        this.mServiceIntent = intent;
        this.mUserTracker = userTracker;
        this.mBgExecutor = executor;
        this.mTransformer = serviceTransformer;
    }

    private void applyToCallbacksLocked(Consumer<Callback<T>> consumer) {
        Iterator<WeakReference<Callback<T>>> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            Callback<T> callback = it.next().get();
            if (callback != null) {
                consumer.accept(callback);
            } else {
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindInternal() {
        boolean z = false;
        try {
            z = this.mContext.bindServiceAsUser(this.mServiceIntent, this, this.mFlags, ((UserTrackerImpl) this.mUserTracker).getUserHandle());
            this.mBoundCalled = true;
        } catch (SecurityException e) {
            Log.d(TAG, "Could not bind to service", e);
            this.mContext.unbindService(this);
        }
        if (DEBUG) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("bind. bound:", TAG, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCallback$1(Callback callback) {
        Iterator<WeakReference<Callback<T>>> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            if (it.next().get() == callback) {
                return;
            }
        }
        this.mCallbacks.add(new WeakReference<>(callback));
        T t = this.mProxy;
        if (t != null) {
            callback.onConnected(this, t);
        } else if (this.mLastDisconnectReason.isPresent()) {
            callback.onDisconnected(this, this.mLastDisconnectReason.get().intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$7(IndentingPrintWriter indentingPrintWriter) {
        Iterator<WeakReference<Callback<T>>> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            indentingPrintWriter.println(it.next().get());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$8(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("mServiceIntent: " + this.mServiceIntent);
        indentingPrintWriter.println("mLastDisconnectReason: " + this.mLastDisconnectReason.orElse(-1));
        indentingPrintWriter.println("Callbacks:");
        DumpUtilsKt.withIncreasedIndent(indentingPrintWriter, new ObservableServiceConnection$$ExternalSyntheticLambda0(this, indentingPrintWriter, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindingDied$10() {
        onDisconnected(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisconnected$4(Callback callback) {
        callback.onDisconnected(this, this.mLastDisconnectReason.get().intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNullBinding$11() {
        onDisconnected(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onServiceConnected$5(Callback callback) {
        callback.onConnected(this, this.mProxy);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onServiceConnected$6(IBinder iBinder) {
        if (DEBUG) {
            Log.d(TAG, "onServiceConnected");
        }
        this.mProxy = this.mTransformer.convert(iBinder);
        applyToCallbacksLocked(new ObservableServiceConnection$$ExternalSyntheticLambda2(this, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onServiceDisconnected$9() {
        onDisconnected(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeCallback$2(Callback callback, WeakReference weakReference) {
        return weakReference.get() == callback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeCallback$3(final Callback callback) {
        this.mCallbacks.removeIf(new Predicate() { // from class: com.android.systemui.util.service.ObservableServiceConnection$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$removeCallback$2;
                lambda$removeCallback$2 = ObservableServiceConnection.lambda$removeCallback$2(ObservableServiceConnection.Callback.this, (WeakReference) obj);
                return lambda$removeCallback$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unbind$0() {
        onDisconnected(4);
    }

    private void onDisconnected(int i) {
        if (DEBUG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onDisconnected:", TAG);
        }
        if (this.mBoundCalled) {
            this.mBoundCalled = false;
            this.mLastDisconnectReason = Optional.of(Integer.valueOf(i));
            this.mContext.unbindService(this);
            this.mProxy = null;
            applyToCallbacksLocked(new ObservableServiceConnection$$ExternalSyntheticLambda2(this, 1));
        }
    }

    public void addCallback(Callback<T> callback) {
        if (DEBUG) {
            Log.d(TAG, "addCallback:" + callback);
        }
        this.mBgExecutor.execute(new ObservableServiceConnection$$ExternalSyntheticLambda1(this, callback, 1));
    }

    public void bind() {
        this.mBgExecutor.execute(new ObservableServiceConnection$$ExternalSyntheticLambda3(this, 1));
    }

    public void dump(PrintWriter printWriter) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("ObservableServiceConnection state:");
        DumpUtilsKt.withIncreasedIndent(asIndenting, new ObservableServiceConnection$$ExternalSyntheticLambda0(this, asIndenting, 1));
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(ComponentName componentName) {
        this.mBgExecutor.execute(new ObservableServiceConnection$$ExternalSyntheticLambda3(this, 4));
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName componentName) {
        this.mBgExecutor.execute(new ObservableServiceConnection$$ExternalSyntheticLambda3(this, 2));
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mBgExecutor.execute(new ObservableServiceConnection$$ExternalSyntheticLambda0(this, iBinder, 2));
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.mBgExecutor.execute(new ObservableServiceConnection$$ExternalSyntheticLambda3(this, 0));
    }

    public void removeCallback(Callback<T> callback) {
        if (DEBUG) {
            Log.d(TAG, "removeCallback:" + callback);
        }
        this.mBgExecutor.execute(new ObservableServiceConnection$$ExternalSyntheticLambda1(this, callback, 0));
    }

    public void unbind() {
        this.mBgExecutor.execute(new ObservableServiceConnection$$ExternalSyntheticLambda3(this, 3));
    }
}
