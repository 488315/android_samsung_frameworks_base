package android.app;

import android.app.Activity;
import android.app.IScreenCaptureObserver;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class ScreenCaptureCallbackHandler {
    private final IBinder mActivityToken;
    private final ScreenCaptureObserver mObserver;
    private final ArrayMap<Activity.ScreenCaptureCallback, ScreenCaptureRegistration> mScreenCaptureRegistrations;

    public ScreenCaptureCallbackHandler(IBinder iBinder) {
        ArrayMap<Activity.ScreenCaptureCallback, ScreenCaptureRegistration> arrayMap = new ArrayMap<>();
        this.mScreenCaptureRegistrations = arrayMap;
        this.mActivityToken = iBinder;
        this.mObserver = new ScreenCaptureObserver(arrayMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ScreenCaptureRegistration {
        Activity.ScreenCaptureCallback mCallback;
        Executor mExecutor;

        ScreenCaptureRegistration(Executor executor, Activity.ScreenCaptureCallback screenCaptureCallback) {
            this.mExecutor = executor;
            this.mCallback = screenCaptureCallback;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ScreenCaptureObserver extends IScreenCaptureObserver.Stub {
        ArrayMap<Activity.ScreenCaptureCallback, ScreenCaptureRegistration> mRegistrations;

        ScreenCaptureObserver(ArrayMap<Activity.ScreenCaptureCallback, ScreenCaptureRegistration> arrayMap) {
            this.mRegistrations = arrayMap;
        }

        @Override // android.app.IScreenCaptureObserver
        public void onScreenCaptured() {
            for (final ScreenCaptureRegistration screenCaptureRegistration : this.mRegistrations.values()) {
                screenCaptureRegistration.mExecutor.execute(new Runnable() { // from class: android.app.ScreenCaptureCallbackHandler$ScreenCaptureObserver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        screenCaptureRegistration.mCallback.onScreenCaptured();
                    }
                });
            }
        }
    }

    public void registerScreenCaptureCallback(Executor executor, Activity.ScreenCaptureCallback screenCaptureCallback) {
        ScreenCaptureRegistration screenCaptureRegistration = new ScreenCaptureRegistration(executor, screenCaptureCallback);
        synchronized (this.mScreenCaptureRegistrations) {
            if (this.mScreenCaptureRegistrations.containsKey(screenCaptureCallback)) {
                throw new IllegalStateException("Capture observer already registered with the activity");
            }
            this.mScreenCaptureRegistrations.put(screenCaptureCallback, screenCaptureRegistration);
            if (this.mScreenCaptureRegistrations.size() == 1) {
                try {
                    ActivityTaskManager.getService().registerScreenCaptureObserver(this.mActivityToken, this.mObserver);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void unregisterScreenCaptureCallback(Activity.ScreenCaptureCallback screenCaptureCallback) {
        synchronized (this.mScreenCaptureRegistrations) {
            if (!this.mScreenCaptureRegistrations.containsKey(screenCaptureCallback)) {
                throw new IllegalStateException("Capture observer not registered with the activity");
            }
            this.mScreenCaptureRegistrations.remove(screenCaptureCallback);
            if (this.mScreenCaptureRegistrations.size() == 0) {
                try {
                    ActivityTaskManager.getService().unregisterScreenCaptureObserver(this.mActivityToken, this.mObserver);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }
}
