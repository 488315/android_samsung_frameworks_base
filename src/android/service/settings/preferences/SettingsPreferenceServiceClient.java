package android.service.settings.preferences;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.OutcomeReceiver;
import android.os.RemoteException;
import android.service.settings.preferences.IGetValueCallback;
import android.service.settings.preferences.IMetadataCallback;
import android.service.settings.preferences.ISetValueCallback;
import android.service.settings.preferences.ISettingsPreferenceService;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class SettingsPreferenceServiceClient implements AutoCloseable {
    private final Context mContext;
    private ISettingsPreferenceService mRemoteService;
    private final ServiceConnection mServiceConnection;
    private final Intent mServiceIntent;

    public SettingsPreferenceServiceClient(Context context, String str, Executor executor, OutcomeReceiver<SettingsPreferenceServiceClient, Exception> outcomeReceiver) {
        this(context, str, true, executor, outcomeReceiver);
    }

    public SettingsPreferenceServiceClient(Context context, String str, boolean z, Executor executor, OutcomeReceiver<SettingsPreferenceServiceClient, Exception> outcomeReceiver) {
        this.mContext = context.getApplicationContext();
        this.mServiceIntent = new Intent(SettingsPreferenceService.ACTION_PREFERENCE_SERVICE).setPackage(str);
        this.mServiceConnection = createServiceConnection(executor, outcomeReceiver);
        connect(z, executor, outcomeReceiver);
    }

    public void getAllPreferenceMetadata(MetadataRequest metadataRequest, Executor executor, final OutcomeReceiver<MetadataResult, Exception> outcomeReceiver) {
        ISettingsPreferenceService iSettingsPreferenceService = this.mRemoteService;
        if (iSettingsPreferenceService == null) {
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(new IllegalStateException("Service not ready"));
                }
            });
            return;
        }
        try {
            iSettingsPreferenceService.getAllPreferenceMetadata(metadataRequest, new AnonymousClass1(this, executor, outcomeReceiver));
        } catch (RemoteException | RuntimeException e) {
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(e);
                }
            });
        }
    }

    /* renamed from: android.service.settings.preferences.SettingsPreferenceServiceClient$1, reason: invalid class name */
    class AnonymousClass1 extends IMetadataCallback.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OutcomeReceiver val$receiver;

        AnonymousClass1(SettingsPreferenceServiceClient settingsPreferenceServiceClient, Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$executor = executor;
            this.val$receiver = outcomeReceiver;
        }

        @Override // android.service.settings.preferences.IMetadataCallback
        public void onSuccess(final MetadataResult metadataResult) {
            Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$receiver;
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onResult(metadataResult);
                }
            });
        }

        @Override // android.service.settings.preferences.IMetadataCallback
        public void onFailure() {
            Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$receiver;
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(new IllegalStateException("Service call failure"));
                }
            });
        }
    }

    public void getPreferenceValue(GetValueRequest getValueRequest, Executor executor, final OutcomeReceiver<GetValueResult, Exception> outcomeReceiver) {
        ISettingsPreferenceService iSettingsPreferenceService = this.mRemoteService;
        if (iSettingsPreferenceService == null) {
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(new IllegalStateException("Service not ready"));
                }
            });
            return;
        }
        try {
            iSettingsPreferenceService.getPreferenceValue(getValueRequest, new AnonymousClass2(this, executor, outcomeReceiver));
        } catch (RemoteException | RuntimeException e) {
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(e);
                }
            });
        }
    }

    /* renamed from: android.service.settings.preferences.SettingsPreferenceServiceClient$2, reason: invalid class name */
    class AnonymousClass2 extends IGetValueCallback.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OutcomeReceiver val$receiver;

        AnonymousClass2(SettingsPreferenceServiceClient settingsPreferenceServiceClient, Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$executor = executor;
            this.val$receiver = outcomeReceiver;
        }

        @Override // android.service.settings.preferences.IGetValueCallback
        public void onSuccess(final GetValueResult getValueResult) {
            Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$receiver;
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onResult(getValueResult);
                }
            });
        }

        @Override // android.service.settings.preferences.IGetValueCallback
        public void onFailure() {
            Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$receiver;
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(new IllegalStateException("Service call failure"));
                }
            });
        }
    }

    public void setPreferenceValue(SetValueRequest setValueRequest, Executor executor, final OutcomeReceiver<SetValueResult, Exception> outcomeReceiver) {
        ISettingsPreferenceService iSettingsPreferenceService = this.mRemoteService;
        if (iSettingsPreferenceService == null) {
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(new IllegalStateException("Service not ready"));
                }
            });
            return;
        }
        try {
            iSettingsPreferenceService.setPreferenceValue(setValueRequest, new AnonymousClass3(this, executor, outcomeReceiver));
        } catch (RemoteException | RuntimeException e) {
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(e);
                }
            });
        }
    }

    /* renamed from: android.service.settings.preferences.SettingsPreferenceServiceClient$3, reason: invalid class name */
    class AnonymousClass3 extends ISetValueCallback.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OutcomeReceiver val$receiver;

        AnonymousClass3(SettingsPreferenceServiceClient settingsPreferenceServiceClient, Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$executor = executor;
            this.val$receiver = outcomeReceiver;
        }

        @Override // android.service.settings.preferences.ISetValueCallback
        public void onSuccess(final SetValueResult setValueResult) {
            Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$receiver;
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onResult(setValueResult);
                }
            });
        }

        @Override // android.service.settings.preferences.ISetValueCallback
        public void onFailure() {
            Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$receiver;
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(new IllegalStateException("Service call failure"));
                }
            });
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.mRemoteService != null) {
            this.mRemoteService = null;
            this.mContext.unbindService(this.mServiceConnection);
        }
    }

    private void connect(boolean z, Executor executor, final OutcomeReceiver<SettingsPreferenceServiceClient, Exception> outcomeReceiver) {
        PackageManager.ResolveInfoFlags resolveInfoFlagsOf;
        PackageManager packageManager = this.mContext.getPackageManager();
        if (z) {
            resolveInfoFlagsOf = PackageManager.ResolveInfoFlags.of(1048576L);
        } else {
            resolveInfoFlagsOf = PackageManager.ResolveInfoFlags.of(131072L);
        }
        if (packageManager.queryIntentServices(this.mServiceIntent, resolveInfoFlagsOf).size() == 1 && this.mContext.bindService(this.mServiceIntent, this.mServiceConnection, 1)) {
            return;
        }
        executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                outcomeReceiver.onError(new IllegalStateException("Unable to bind service"));
            }
        });
    }

    /* renamed from: android.service.settings.preferences.SettingsPreferenceServiceClient$4, reason: invalid class name */
    class AnonymousClass4 implements ServiceConnection {
        final /* synthetic */ Executor val$callbackExecutor;
        final /* synthetic */ OutcomeReceiver val$clientCallback;

        AnonymousClass4(Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$clientCallback = outcomeReceiver;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SettingsPreferenceServiceClient.this.mRemoteService = ISettingsPreferenceService.Stub.asInterface(iBinder);
            Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$clientCallback;
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onServiceConnected$0(outcomeReceiver);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$0(OutcomeReceiver outcomeReceiver) {
            outcomeReceiver.onResult(SettingsPreferenceServiceClient.this);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            SettingsPreferenceServiceClient.this.mRemoteService = null;
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            SettingsPreferenceServiceClient.this.close();
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$clientCallback;
            executor.execute(new Runnable() { // from class: android.service.settings.preferences.SettingsPreferenceServiceClient$4$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    outcomeReceiver.onError(new IllegalStateException("Unable to connect client"));
                }
            });
            SettingsPreferenceServiceClient.this.close();
        }
    }

    private ServiceConnection createServiceConnection(Executor executor, OutcomeReceiver<SettingsPreferenceServiceClient, Exception> outcomeReceiver) {
        return new AnonymousClass4(executor, outcomeReceiver);
    }
}
