package com.android.server.musicrecognition;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.musicrecognition.IMusicRecognitionManager;
import android.os.Binder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;

import java.io.FileDescriptor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class MusicRecognitionManagerService extends AbstractMasterSystemService {
    public final ExecutorService mExecutorService;

    public final class MusicRecognitionManagerStub extends IMusicRecognitionManager.Stub {
        public MusicRecognitionManagerStub() {}

        /* JADX WARN: Can't wrap try/catch for region: R(6:5|6|(5:8|(1:10)(1:20)|(2:12|(1:14))|15|16)|21|22|16) */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void beginRecognition(
                android.media.musicrecognition.RecognitionRequest r6, android.os.IBinder r7) {
            /*
                r5 = this;
                com.android.server.musicrecognition.MusicRecognitionManagerService r0 = com.android.server.musicrecognition.MusicRecognitionManagerService.this
                android.content.Context r0 = r0.getContext()
                java.lang.String r1 = "android.permission.MANAGE_MUSIC_RECOGNITION"
                int r0 = r0.checkCallingPermission(r1)
                if (r0 != 0) goto L54
                com.android.server.musicrecognition.MusicRecognitionManagerService r0 = com.android.server.musicrecognition.MusicRecognitionManagerService.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                int r1 = android.os.UserHandle.getCallingUserId()     // Catch: java.lang.Throwable -> L42
                com.android.server.musicrecognition.MusicRecognitionManagerService r2 = com.android.server.musicrecognition.MusicRecognitionManagerService.this     // Catch: java.lang.Throwable -> L42
                com.android.server.infra.AbstractPerUserSystemService r2 = r2.getServiceForUserLocked(r1)     // Catch: java.lang.Throwable -> L42
                com.android.server.musicrecognition.MusicRecognitionManagerPerUserService r2 = (com.android.server.musicrecognition.MusicRecognitionManagerPerUserService) r2     // Catch: java.lang.Throwable -> L42
                if (r2 == 0) goto L48
                com.android.server.musicrecognition.MusicRecognitionManagerService r3 = com.android.server.musicrecognition.MusicRecognitionManagerService.this     // Catch: java.lang.Throwable -> L42
                com.android.server.infra.ServiceNameBaseResolver r3 = r3.mServiceNameResolver     // Catch: java.lang.Throwable -> L42
                java.lang.String r3 = r3.getDefaultServiceName(r1)     // Catch: java.lang.Throwable -> L42
                if (r3 != 0) goto L2d
                r1 = 0
                goto L39
            L2d:
                com.android.server.musicrecognition.MusicRecognitionManagerService r4 = com.android.server.musicrecognition.MusicRecognitionManagerService.this     // Catch: java.lang.Throwable -> L42
                com.android.server.infra.ServiceNameBaseResolver r4 = r4.mServiceNameResolver     // Catch: java.lang.Throwable -> L42
                java.lang.String r1 = r4.getServiceName(r1)     // Catch: java.lang.Throwable -> L42
                boolean r1 = r3.equals(r1)     // Catch: java.lang.Throwable -> L42
            L39:
                if (r1 != 0) goto L44
                boolean r5 = r5.isCalledByServiceAppLocked()     // Catch: java.lang.Throwable -> L42
                if (r5 == 0) goto L48
                goto L44
            L42:
                r5 = move-exception
                goto L52
            L44:
                r2.beginRecognitionLocked(r6, r7)     // Catch: java.lang.Throwable -> L42
                goto L50
            L48:
                android.media.musicrecognition.IMusicRecognitionManagerCallback r5 = android.media.musicrecognition.IMusicRecognitionManagerCallback.Stub.asInterface(r7)     // Catch: java.lang.Throwable -> L42 android.os.RemoteException -> L50
                r6 = 3
                r5.onRecognitionFailed(r6)     // Catch: java.lang.Throwable -> L42 android.os.RemoteException -> L50
            L50:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L42
                return
            L52:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L42
                throw r5
            L54:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                java.lang.String r6 = "Permission Denial: beginRecognition from pid="
                r5.<init>(r6)
                int r6 = android.os.Binder.getCallingPid()
                r5.append(r6)
                java.lang.String r6 = ", uid="
                r5.append(r6)
                int r6 = android.os.Binder.getCallingUid()
                r5.append(r6)
                java.lang.String r6 = " doesn't hold android.permission.MANAGE_MUSIC_RECOGNITION"
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                java.lang.SecurityException r6 = new java.lang.SecurityException
                r6.<init>(r5)
                throw r6
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.musicrecognition.MusicRecognitionManagerService.MusicRecognitionManagerStub.beginRecognition(android.media.musicrecognition.RecognitionRequest,"
                        + " android.os.IBinder):void");
        }

        public final boolean isCalledByServiceAppLocked() {
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            String serviceName =
                    MusicRecognitionManagerService.this.mServiceNameResolver.getServiceName(
                            callingUserId);
            if (serviceName == null) {
                Slog.e(
                        "MusicRecognitionManagerService",
                        "beginRecognition: called by UID "
                                + callingUid
                                + ", but there's no service set for user "
                                + callingUserId);
                return false;
            }
            ComponentName unflattenFromString = ComponentName.unflattenFromString(serviceName);
            if (unflattenFromString == null) {
                Slog.w(
                        "MusicRecognitionManagerService",
                        "beginRecognition: invalid service name: ".concat(serviceName));
                return false;
            }
            try {
                int packageUidAsUser =
                        MusicRecognitionManagerService.this
                                .getContext()
                                .getPackageManager()
                                .getPackageUidAsUser(
                                        unflattenFromString.getPackageName(),
                                        UserHandle.getCallingUserId());
                if (callingUid == packageUidAsUser) {
                    return true;
                }
                Slog.e(
                        "MusicRecognitionManagerService",
                        "beginRecognition: called by UID "
                                + callingUid
                                + ", but service UID is "
                                + packageUidAsUser);
                return false;
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.w(
                        "MusicRecognitionManagerService",
                        "beginRecognition: could not verify UID for ".concat(serviceName));
                return false;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new MusicRecognitionManagerServiceShellCommand(MusicRecognitionManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }
    }

    public MusicRecognitionManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(context, R.string.default_audio_route_id),
                null);
        this.mExecutorService = Executors.newCachedThreadPool();
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext()
                .enforceCallingPermission(
                        "android.permission.MANAGE_MUSIC_RECOGNITION",
                        "MusicRecognitionManagerService");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 60000;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new MusicRecognitionManagerPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("music_recognition", new MusicRecognitionManagerStub());
    }
}
