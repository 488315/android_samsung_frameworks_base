package com.android.server.autofill;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.SystemClock;
import android.service.assist.classification.FieldClassificationRequest;
import android.service.assist.classification.FieldClassificationResponse;
import android.service.assist.classification.IFieldClassificationCallback;
import android.service.assist.classification.IFieldClassificationService;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.infra.ServiceConnector;
import java.lang.ref.WeakReference;
import java.util.function.Function;

/* loaded from: classes.dex */
final class RemoteFieldClassificationService extends ServiceConnector.Impl {
    public static final String TAG = "Autofill" + RemoteFieldClassificationService.class.getSimpleName();
    private final ComponentName mComponentName;

    public interface FieldClassificationServiceCallbacks {
        void onClassificationRequestFailure(int i, CharSequence charSequence);

        void onClassificationRequestSuccess(FieldClassificationResponse fieldClassificationResponse);
    }

    public long getAutoDisconnectTimeoutMs() {
        return 0L;
    }

    public RemoteFieldClassificationService(Context context, ComponentName componentName, int i, int i2) {
        super(context, new Intent("android.service.assist.classification.FieldClassificationService").setComponent(componentName), 0, i2, new Function() { // from class: com.android.server.autofill.RemoteFieldClassificationService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return IFieldClassificationService.Stub.asInterface((IBinder) obj);
            }
        });
        this.mComponentName = componentName;
        if (Helper.sDebug) {
            Slog.d(TAG, "About to connect to serviceName: " + componentName);
        }
        connect();
    }

    public static Pair getComponentName(String str, int i, boolean z) {
        int i2 = !z ? 1048704 : 128;
        try {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
            ServiceInfo serviceInfo = AppGlobals.getPackageManager().getServiceInfo(unflattenFromString, i2, i);
            if (serviceInfo == null) {
                Slog.e(TAG, "Bad service name for flags " + i2 + ": " + str);
                return null;
            }
            return new Pair(serviceInfo, unflattenFromString);
        } catch (Exception e) {
            Slog.e(TAG, "Error getting service info for '" + str + "': " + e);
            return null;
        }
    }

    public void onServiceConnectionStatusChanged(IFieldClassificationService iFieldClassificationService, boolean z) {
        try {
            if (z) {
                iFieldClassificationService.onConnected(false, false);
            } else {
                iFieldClassificationService.onDisconnected();
            }
        } catch (Exception e) {
            Slog.w(TAG, "Exception calling onServiceConnectionStatusChanged(" + z + "): ", e);
        }
    }

    public void onFieldClassificationRequest(final FieldClassificationRequest fieldClassificationRequest, final WeakReference weakReference) {
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        if (Helper.sVerbose) {
            Slog.v(TAG, "onFieldClassificationRequest request:" + fieldClassificationRequest);
        }
        run(new ServiceConnector.VoidJob() { // from class: com.android.server.autofill.RemoteFieldClassificationService$$ExternalSyntheticLambda1
            public final void runNoResult(Object obj) {
                RemoteFieldClassificationService.this.lambda$onFieldClassificationRequest$0(fieldClassificationRequest, elapsedRealtime, weakReference, (IFieldClassificationService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFieldClassificationRequest$0(FieldClassificationRequest fieldClassificationRequest, final long j, final WeakReference weakReference, IFieldClassificationService iFieldClassificationService) {
        iFieldClassificationService.onFieldClassificationRequest(fieldClassificationRequest, new IFieldClassificationCallback.Stub() { // from class: com.android.server.autofill.RemoteFieldClassificationService.1
            public void cancel() {
            }

            public boolean isCompleted() {
                return false;
            }

            public void onCancellable(ICancellationSignal iCancellationSignal) {
                RemoteFieldClassificationService.this.logLatency(j);
                if (Helper.sDebug) {
                    Log.d(RemoteFieldClassificationService.TAG, "onCancellable");
                }
            }

            public void onSuccess(FieldClassificationResponse fieldClassificationResponse) {
                String str;
                RemoteFieldClassificationService.this.logLatency(j);
                if (Helper.sDebug) {
                    if (Build.IS_DEBUGGABLE) {
                        Slog.d(RemoteFieldClassificationService.TAG, "onSuccess Response: " + fieldClassificationResponse);
                    } else {
                        if (fieldClassificationResponse == null || fieldClassificationResponse.getClassifications() == null) {
                            str = "null response";
                        } else {
                            str = "size: " + fieldClassificationResponse.getClassifications().size();
                        }
                        Slog.d(RemoteFieldClassificationService.TAG, "onSuccess " + str);
                    }
                }
                FieldClassificationServiceCallbacks fieldClassificationServiceCallbacks = (FieldClassificationServiceCallbacks) Helper.weakDeref(weakReference, RemoteFieldClassificationService.TAG, "onSuccess ");
                if (fieldClassificationServiceCallbacks == null) {
                    return;
                }
                fieldClassificationServiceCallbacks.onClassificationRequestSuccess(fieldClassificationResponse);
            }

            public void onFailure() {
                RemoteFieldClassificationService.this.logLatency(j);
                if (Helper.sDebug) {
                    Slog.d(RemoteFieldClassificationService.TAG, "onFailure");
                }
                FieldClassificationServiceCallbacks fieldClassificationServiceCallbacks = (FieldClassificationServiceCallbacks) Helper.weakDeref(weakReference, RemoteFieldClassificationService.TAG, "onFailure ");
                if (fieldClassificationServiceCallbacks == null) {
                    return;
                }
                fieldClassificationServiceCallbacks.onClassificationRequestFailure(0, null);
            }
        });
    }

    public final void logLatency(long j) {
        FieldClassificationEventLogger createLogger = FieldClassificationEventLogger.createLogger();
        createLogger.startNewLogForRequest();
        createLogger.maybeSetLatencyMillis(SystemClock.elapsedRealtime() - j);
        createLogger.logAndEndEvent();
    }
}
