package android.view;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ICrossWindowBlurEnabledListener;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public final class CrossWindowBlurListeners {
    private static final String TAG = "CrossWindowBlurListeners";
    private static volatile CrossWindowBlurListeners sInstance;
    private boolean mCrossWindowBlurEnabled;
    private static final String BLUR_PROPERTY = "ro.surface_flinger.supports_background_blur";
    public static final boolean CROSS_WINDOW_BLUR_SUPPORTED = SystemProperties.getBoolean(BLUR_PROPERTY, false);
    private static final Object sLock = new Object();
    private final BlurEnabledListenerInternal mListenerInternal = new BlurEnabledListenerInternal();
    private final ArrayMap<Consumer<Boolean>, Executor> mListeners = new ArrayMap<>();
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private boolean mInternalListenerAttached = false;

    private CrossWindowBlurListeners() {
    }

    public static CrossWindowBlurListeners getInstance() {
        CrossWindowBlurListeners crossWindowBlurListeners;
        CrossWindowBlurListeners crossWindowBlurListeners2 = sInstance;
        if (crossWindowBlurListeners2 != null) {
            return crossWindowBlurListeners2;
        }
        synchronized (sLock) {
            crossWindowBlurListeners = sInstance;
            if (crossWindowBlurListeners == null) {
                crossWindowBlurListeners = new CrossWindowBlurListeners();
                sInstance = crossWindowBlurListeners;
            }
        }
        return crossWindowBlurListeners;
    }

    public boolean isCrossWindowBlurEnabled() {
        boolean z;
        synchronized (sLock) {
            attachInternalListenerIfNeededLocked();
            z = this.mCrossWindowBlurEnabled;
        }
        return z;
    }

    public void addListener(Executor executor, Consumer<Boolean> consumer) {
        Preconditions.checkNotNull(consumer, "listener cannot be null");
        Preconditions.checkNotNull(executor, "executor cannot be null");
        synchronized (sLock) {
            attachInternalListenerIfNeededLocked();
            this.mListeners.put(consumer, executor);
            notifyListener(consumer, executor, this.mCrossWindowBlurEnabled);
        }
    }

    public void removeListener(Consumer<Boolean> consumer) {
        Preconditions.checkNotNull(consumer, "listener cannot be null");
        synchronized (sLock) {
            this.mListeners.remove(consumer);
            if (this.mInternalListenerAttached && this.mListeners.size() == 0) {
                try {
                    WindowManagerGlobal.getWindowManagerService().unregisterCrossWindowBlurEnabledListener(this.mListenerInternal);
                    this.mInternalListenerAttached = false;
                } catch (RemoteException unused) {
                    Log.d(TAG, "Could not unregister ICrossWindowBlurEnabledListener");
                }
            }
        }
    }

    private void attachInternalListenerIfNeededLocked() {
        if (this.mInternalListenerAttached) {
            return;
        }
        try {
            this.mCrossWindowBlurEnabled = WindowManagerGlobal.getWindowManagerService().registerCrossWindowBlurEnabledListener(this.mListenerInternal);
            this.mInternalListenerAttached = true;
        } catch (RemoteException unused) {
            Log.d(TAG, "Could not register ICrossWindowBlurEnabledListener");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListener(final Consumer<Boolean> consumer, Executor executor, final boolean z) {
        executor.execute(new Runnable() { // from class: android.view.CrossWindowBlurListeners$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(Boolean.valueOf(z));
            }
        });
    }

    private final class BlurEnabledListenerInternal extends ICrossWindowBlurEnabledListener.Stub {
        private BlurEnabledListenerInternal() {
        }

        @Override // android.view.ICrossWindowBlurEnabledListener
        public void onCrossWindowBlurEnabledChanged(boolean z) {
            synchronized (CrossWindowBlurListeners.sLock) {
                CrossWindowBlurListeners.this.mCrossWindowBlurEnabled = z;
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                for (int i = 0; i < CrossWindowBlurListeners.this.mListeners.size(); i++) {
                    try {
                        CrossWindowBlurListeners crossWindowBlurListeners = CrossWindowBlurListeners.this;
                        crossWindowBlurListeners.notifyListener((Consumer) crossWindowBlurListeners.mListeners.keyAt(i), (Executor) CrossWindowBlurListeners.this.mListeners.valueAt(i), z);
                    } finally {
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                    }
                }
            }
        }
    }
}
