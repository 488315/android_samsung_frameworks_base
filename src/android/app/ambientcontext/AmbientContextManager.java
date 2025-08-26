package android.app.ambientcontext;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.ambientcontext.IAmbientContextObserver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes.dex */
public final class AmbientContextManager {
    public static final String EXTRA_AMBIENT_CONTEXT_EVENTS = "android.app.ambientcontext.extra.AMBIENT_CONTEXT_EVENTS";
    public static final int STATUS_ACCESS_DENIED = 5;
    public static final int STATUS_MICROPHONE_DISABLED = 4;
    public static final int STATUS_NOT_SUPPORTED = 2;
    public static final String STATUS_RESPONSE_BUNDLE_KEY = "android.app.ambientcontext.AmbientContextStatusBundleKey";
    public static final int STATUS_SERVICE_UNAVAILABLE = 3;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN = 0;
    private final Context mContext;
    private final IAmbientContextManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusCode {
    }

    public static List<AmbientContextEvent> getEventsFromIntent(Intent intent) {
        if (intent.hasExtra(EXTRA_AMBIENT_CONTEXT_EVENTS)) {
            return intent.getParcelableArrayListExtra(EXTRA_AMBIENT_CONTEXT_EVENTS, AmbientContextEvent.class);
        }
        return new ArrayList();
    }

    public AmbientContextManager(Context context, IAmbientContextManager iAmbientContextManager) {
        this.mContext = context;
        this.mService = iAmbientContextManager;
    }

    public void queryAmbientContextServiceStatus(Set<Integer> set, final Executor executor, final Consumer<Integer> consumer) {
        try {
            this.mService.queryServiceStatus(integerSetToIntArray(set), this.mContext.getOpPackageName(), new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.app.ambientcontext.AmbientContextManager$$ExternalSyntheticLambda3
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                    AmbientContextManager.lambda$queryAmbientContextServiceStatus$1(executor, consumer, bundle);
                }
            }));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$queryAmbientContextServiceStatus$1(Executor executor, final Consumer consumer, Bundle bundle) {
        final int i = bundle.getInt(STATUS_RESPONSE_BUNDLE_KEY);
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            executor.execute(new Runnable() { // from class: android.app.ambientcontext.AmbientContextManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(Integer.valueOf(i));
                }
            });
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void startConsentActivity(Set<Integer> set) {
        try {
            this.mService.startConsentActivity(integerSetToIntArray(set), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static int[] integerSetToIntArray(Set<Integer> set) {
        int[] iArr = new int[set.size()];
        Iterator<Integer> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        return iArr;
    }

    public void registerObserver(AmbientContextEventRequest ambientContextEventRequest, PendingIntent pendingIntent, final Executor executor, final Consumer<Integer> consumer) {
        Preconditions.checkArgument(!pendingIntent.isImmutable());
        try {
            this.mService.registerObserver(ambientContextEventRequest, pendingIntent, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.app.ambientcontext.AmbientContextManager$$ExternalSyntheticLambda2
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                    AmbientContextManager.lambda$registerObserver$3(executor, consumer, bundle);
                }
            }));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$registerObserver$3(Executor executor, final Consumer consumer, Bundle bundle) {
        final int i = bundle.getInt(STATUS_RESPONSE_BUNDLE_KEY);
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            executor.execute(new Runnable() { // from class: android.app.ambientcontext.AmbientContextManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(Integer.valueOf(i));
                }
            });
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    /* renamed from: android.app.ambientcontext.AmbientContextManager$1, reason: invalid class name */
    class AnonymousClass1 extends IAmbientContextObserver.Stub {
        final /* synthetic */ AmbientContextCallback val$ambientContextCallback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(AmbientContextManager ambientContextManager, Executor executor, AmbientContextCallback ambientContextCallback) {
            this.val$executor = executor;
            this.val$ambientContextCallback = ambientContextCallback;
        }

        @Override // android.app.ambientcontext.IAmbientContextObserver
        public void onEvents(final List<AmbientContextEvent> list) throws RemoteException {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final AmbientContextCallback ambientContextCallback = this.val$ambientContextCallback;
                executor.execute(new Runnable() { // from class: android.app.ambientcontext.AmbientContextManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ambientContextCallback.onEvents(list);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.app.ambientcontext.IAmbientContextObserver
        public void onRegistrationComplete(final int i) throws RemoteException {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final AmbientContextCallback ambientContextCallback = this.val$ambientContextCallback;
                executor.execute(new Runnable() { // from class: android.app.ambientcontext.AmbientContextManager$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ambientContextCallback.onRegistrationComplete(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    public void registerObserver(AmbientContextEventRequest ambientContextEventRequest, Executor executor, AmbientContextCallback ambientContextCallback) {
        try {
            this.mService.registerObserverWithCallback(ambientContextEventRequest, this.mContext.getPackageName(), new AnonymousClass1(this, executor, ambientContextCallback));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterObserver() {
        try {
            this.mService.unregisterObserver(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
