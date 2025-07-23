package com.android.internal.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.android.internal.util.CallbackRegistry;
import com.android.internal.util.ObservableServiceConnection;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class ObservableServiceConnection<T> implements ServiceConnection {
    public static final int DISCONNECT_REASON_BINDING_DIED = 3;
    public static final int DISCONNECT_REASON_DISCONNECTED = 2;
    public static final int DISCONNECT_REASON_NONE = 0;
    public static final int DISCONNECT_REASON_NULL_BINDING = 1;
    public static final int DISCONNECT_REASON_UNBIND = 4;
    private final Context mContext;
    private final Executor mExecutor;
    private final int mFlags;
    private T mService;
    private final Intent mServiceIntent;
    private final ServiceTransformer<T> mTransformer;
    private final Object mLock = new Object();
    private boolean mBoundCalled = false;
    private int mLastDisconnectReason = 0;
    private final CallbackRegistry<Callback<T>, ObservableServiceConnection<T>, T> mCallbackRegistry = new CallbackRegistry<>(new AnonymousClass1());

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

    /* renamed from: com.android.internal.util.ObservableServiceConnection$1, reason: invalid class name */
    class AnonymousClass1 extends CallbackRegistry.NotifierCallback<Callback<T>, ObservableServiceConnection<T>, T> {
        AnonymousClass1() {
        }

        @Override // com.android.internal.util.CallbackRegistry.NotifierCallback
        public /* bridge */ /* synthetic */ void onNotifyCallback(Object obj, Object obj2, int i, Object obj3) {
            onNotifyCallback((Callback<int>) obj, (ObservableServiceConnection<int>) obj2, i, (int) obj3);
        }

        public void onNotifyCallback(final Callback<T> callback, final ObservableServiceConnection<T> observableServiceConnection, final int i, final T t) {
            ObservableServiceConnection.this.mExecutor.execute(new Runnable() { // from class: com.android.internal.util.ObservableServiceConnection$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ObservableServiceConnection.AnonymousClass1.this.lambda$onNotifyCallback$0(t, callback, observableServiceConnection, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNotifyCallback$0(Object obj, Callback callback, ObservableServiceConnection observableServiceConnection, int i) {
            synchronized (ObservableServiceConnection.this.mLock) {
                if (obj != null) {
                    callback.onConnected(observableServiceConnection, obj);
                } else if (ObservableServiceConnection.this.mLastDisconnectReason != 0) {
                    callback.onDisconnected(observableServiceConnection, i);
                }
            }
        }
    }

    public ObservableServiceConnection(Context context, Executor executor, ServiceTransformer<T> serviceTransformer, Intent intent, int i) {
        this.mContext = context;
        this.mExecutor = executor;
        this.mTransformer = serviceTransformer;
        this.mServiceIntent = intent;
        this.mFlags = i;
    }

    public void execute(Runnable runnable) {
        this.mExecutor.execute(runnable);
    }

    public boolean bind() {
        synchronized (this.mLock) {
            if (this.mBoundCalled) {
                return false;
            }
            boolean bindService = this.mContext.bindService(this.mServiceIntent, this.mFlags, this.mExecutor, this);
            this.mBoundCalled = true;
            return bindService;
        }
    }

    public void unbind() {
        onDisconnected(4);
    }

    public void addCallback(final Callback<T> callback) {
        this.mCallbackRegistry.add(callback);
        this.mExecutor.execute(new Runnable() { // from class: com.android.internal.util.ObservableServiceConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ObservableServiceConnection.this.lambda$addCallback$0(callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCallback$0(Callback callback) {
        synchronized (this.mLock) {
            T t = this.mService;
            if (t != null) {
                callback.onConnected(this, t);
            } else {
                int i = this.mLastDisconnectReason;
                if (i != 0) {
                    callback.onDisconnected(this, i);
                }
            }
        }
    }

    public void removeCallback(Callback<T> callback) {
        synchronized (this.mLock) {
            this.mCallbackRegistry.remove(callback);
        }
    }

    private void onDisconnected(int i) {
        synchronized (this.mLock) {
            if (this.mBoundCalled) {
                this.mBoundCalled = false;
                this.mLastDisconnectReason = i;
                this.mContext.unbindService(this);
                this.mService = null;
                this.mCallbackRegistry.notifyCallbacks(this, i, null);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.mLock) {
            T convert = this.mTransformer.convert(iBinder);
            this.mService = convert;
            this.mLastDisconnectReason = 0;
            this.mCallbackRegistry.notifyCallbacks(this, 0, convert);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        onDisconnected(2);
    }

    @Override // android.content.ServiceConnection
    public final void onBindingDied(ComponentName componentName) {
        onDisconnected(3);
    }

    @Override // android.content.ServiceConnection
    public final void onNullBinding(ComponentName componentName) {
        onDisconnected(1);
    }
}
