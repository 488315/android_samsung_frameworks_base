package android.app;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;

import java.util.concurrent.Executor;

public class ScreenCaptureCallbackHandler {
    private final IBinder mActivityToken;
    private final ArrayMap<Activity.ScreenCaptureCallback, ScreenCaptureRegistration>
            mScreenCaptureRegistrations = new ArrayMap<>();
    private final ScreenCaptureObserver mObserver =
            new ScreenCaptureObserver(this.mScreenCaptureRegistrations);

    public ScreenCaptureCallbackHandler(IBinder activityToken) {
        this.mActivityToken = activityToken;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ScreenCaptureRegistration {
        Activity.ScreenCaptureCallback mCallback;
        Executor mExecutor;

        ScreenCaptureRegistration(Executor executor, Activity.ScreenCaptureCallback callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ScreenCaptureObserver extends IScreenCaptureObserver.Stub {
        ArrayMap<Activity.ScreenCaptureCallback, ScreenCaptureRegistration> mRegistrations;

        ScreenCaptureObserver(
                ArrayMap<Activity.ScreenCaptureCallback, ScreenCaptureRegistration> registrations) {
            this.mRegistrations = registrations;
        }

        @Override // android.app.IScreenCaptureObserver
        public void onScreenCaptured() {
            for (final ScreenCaptureRegistration registration : this.mRegistrations.values()) {
                registration.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.app.ScreenCaptureCallbackHandler$ScreenCaptureObserver$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScreenCaptureCallbackHandler.ScreenCaptureRegistration.this
                                        .mCallback.onScreenCaptured();
                            }
                        });
            }
        }
    }

    public void registerScreenCaptureCallback(
            Executor executor, Activity.ScreenCaptureCallback callback) {
        ScreenCaptureRegistration registration = new ScreenCaptureRegistration(executor, callback);
        synchronized (this.mScreenCaptureRegistrations) {
            if (this.mScreenCaptureRegistrations.containsKey(callback)) {
                throw new IllegalStateException(
                        "Capture observer already registered with the activity");
            }
            this.mScreenCaptureRegistrations.put(callback, registration);
            if (this.mScreenCaptureRegistrations.size() == 1) {
                try {
                    ActivityTaskManager.getService()
                            .registerScreenCaptureObserver(this.mActivityToken, this.mObserver);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void unregisterScreenCaptureCallback(Activity.ScreenCaptureCallback callback) {
        synchronized (this.mScreenCaptureRegistrations) {
            if (!this.mScreenCaptureRegistrations.containsKey(callback)) {
                throw new IllegalStateException(
                        "Capture observer not registered with the activity");
            }
            this.mScreenCaptureRegistrations.remove(callback);
            if (this.mScreenCaptureRegistrations.size() == 0) {
                try {
                    ActivityTaskManager.getService()
                            .unregisterScreenCaptureObserver(this.mActivityToken, this.mObserver);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }
}
