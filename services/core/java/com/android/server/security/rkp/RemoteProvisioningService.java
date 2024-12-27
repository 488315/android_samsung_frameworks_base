package com.android.server.security.rkp;

import android.content.Context;
import android.os.Binder;
import android.os.OutcomeReceiver;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.security.rkp.IGetRegistrationCallback;
import android.security.rkp.IRemoteProvisioning;
import android.security.rkp.service.RegistrationProxy;
import android.util.Log;
import com.android.internal.util.DumpUtils;
import com.android.server.SystemService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RemoteProvisioningService extends SystemService {
    public static final Duration CREATE_REGISTRATION_TIMEOUT = Duration.ofSeconds(10);
    public final RemoteProvisioningImpl mBinderImpl;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RegistrationReceiver implements OutcomeReceiver {
        public final IGetRegistrationCallback mCallback;
        public final Executor mExecutor;

        public RegistrationReceiver(Executor executor, IGetRegistrationCallback iGetRegistrationCallback) {
            this.mExecutor = executor;
            this.mCallback = iGetRegistrationCallback;
        }

        @Override // android.os.OutcomeReceiver
        public final void onError(Throwable th) {
            try {
                this.mCallback.onError(((Exception) th).toString());
            } catch (RemoteException e) {
                Log.e("RemoteProvisionSysSvc", "Error calling error callback " + this.mCallback.asBinder().hashCode(), e);
            }
        }

        @Override // android.os.OutcomeReceiver
        public final void onResult(Object obj) {
            try {
                this.mCallback.onSuccess(new RemoteProvisioningRegistration((RegistrationProxy) obj, this.mExecutor));
            } catch (RemoteException e) {
                Log.e("RemoteProvisionSysSvc", "Error calling success callback " + this.mCallback.asBinder().hashCode(), e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteProvisioningImpl extends IRemoteProvisioning.Stub {
        public RemoteProvisioningImpl() {
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(RemoteProvisioningService.this.getContext(), "RemoteProvisionSysSvc", printWriter)) {
                int callingUidOrThrow = Binder.getCallingUidOrThrow();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    new RemoteProvisioningShellCommand(RemoteProvisioningService.this.getContext(), callingUidOrThrow).dump(printWriter);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void getRegistration(String str, IGetRegistrationCallback iGetRegistrationCallback) {
            int callingUidOrThrow = Binder.getCallingUidOrThrow();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Executor mainExecutor = RemoteProvisioningService.this.getContext().getMainExecutor();
            try {
                Log.i("RemoteProvisionSysSvc", "getRegistration(" + str + ")");
                RegistrationProxy.createAsync(RemoteProvisioningService.this.getContext(), callingUidOrThrow, str, RemoteProvisioningService.CREATE_REGISTRATION_TIMEOUT, mainExecutor, new RegistrationReceiver(mainExecutor, iGetRegistrationCallback));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
            int callingUidOrThrow = Binder.getCallingUidOrThrow();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return new RemoteProvisioningShellCommand(RemoteProvisioningService.this.getContext(), callingUidOrThrow).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public RemoteProvisioningService(Context context) {
        super(context);
        this.mBinderImpl = new RemoteProvisioningImpl();
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("remote_provisioning", this.mBinderImpl);
    }
}
