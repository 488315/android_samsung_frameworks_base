package com.android.server.musicrecognition;

import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.media.AudioRecord;
import android.media.MediaMetadata;
import android.media.musicrecognition.IMusicRecognitionManagerCallback;
import android.media.musicrecognition.IMusicRecognitionServiceCallback;
import android.media.musicrecognition.RecognitionRequest;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.infra.AbstractRemoteService;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractPerUserSystemService;

import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public final class MusicRecognitionManagerPerUserService extends AbstractPerUserSystemService
        implements AbstractRemoteService.VultureCallback {
    public final AppOpsManager mAppOpsManager;
    public final String mAttributionMessage;
    public CompletableFuture mAttributionTagFuture;
    public RemoteMusicRecognitionService mRemoteService;
    public ServiceInfo mServiceInfo;

    public final class MusicRecognitionServiceCallback
            extends IMusicRecognitionServiceCallback.Stub {
        public final IMusicRecognitionManagerCallback mClientCallback;

        public MusicRecognitionServiceCallback(
                IMusicRecognitionManagerCallback iMusicRecognitionManagerCallback) {
            this.mClientCallback = iMusicRecognitionManagerCallback;
        }

        public final void onRecognitionFailed(int i) {
            try {
                this.mClientCallback.onRecognitionFailed(i);
            } catch (RemoteException unused) {
            }
            MusicRecognitionManagerPerUserService.this.destroyService();
        }

        public final void onRecognitionSucceeded(MediaMetadata mediaMetadata, Bundle bundle) {
            try {
                MusicRecognitionManagerPerUserService.sanitizeBundle(bundle);
                this.mClientCallback.onRecognitionSucceeded(mediaMetadata, bundle);
            } catch (RemoteException unused) {
            }
            MusicRecognitionManagerPerUserService.this.destroyService();
        }
    }

    public MusicRecognitionManagerPerUserService(
            MusicRecognitionManagerService musicRecognitionManagerService, Object obj, int i) {
        super(musicRecognitionManagerService, obj, i);
        this.mAppOpsManager =
                (AppOpsManager)
                        musicRecognitionManagerService
                                .getContext()
                                .createAttributionContext("MusicRecognitionManagerService")
                                .getSystemService(AppOpsManager.class);
        this.mAttributionMessage =
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        i, "MusicRecognitionManager.invokedByUid.");
        this.mAttributionTagFuture = null;
        this.mServiceInfo = null;
    }

    public static void sanitizeBundle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof Bundle) {
                sanitizeBundle((Bundle) obj);
            } else if ((obj instanceof IBinder) || (obj instanceof ParcelFileDescriptor)) {
                bundle.remove(str);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x008a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void beginRecognitionLocked(
            final android.media.musicrecognition.RecognitionRequest r14, android.os.IBinder r15) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.beginRecognitionLocked(android.media.musicrecognition.RecognitionRequest,"
                    + " android.os.IBinder):void");
    }

    public final void destroyService() {
        synchronized (this.mLock) {
            try {
                RemoteMusicRecognitionService remoteMusicRecognitionService = this.mRemoteService;
                if (remoteMusicRecognitionService != null) {
                    remoteMusicRecognitionService.destroy();
                    this.mRemoteService = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void finishRecordAudioOp(String str) {
        AppOpsManager appOpsManager = this.mAppOpsManager;
        String permissionToOp = AppOpsManager.permissionToOp("android.permission.RECORD_AUDIO");
        Objects.requireNonNull(permissionToOp);
        ServiceInfo serviceInfo = this.mServiceInfo;
        appOpsManager.finishProxyOp(
                permissionToOp, serviceInfo.applicationInfo.uid, serviceInfo.packageName, str);
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            ServiceInfo serviceInfo =
                    AppGlobals.getPackageManager()
                            .getServiceInfo(componentName, 128L, this.mUserId);
            if ("android.permission.BIND_MUSIC_RECOGNITION_SERVICE"
                    .equals(serviceInfo.permission)) {
                return serviceInfo;
            }
            Slog.w(
                    "MusicRecognitionManagerPerUserService",
                    "MusicRecognitionService from '"
                            + serviceInfo.packageName
                            + "' does not require permission"
                            + " android.permission.BIND_MUSIC_RECOGNITION_SERVICE");
            throw new SecurityException(
                    "Service does not require permission"
                        + " android.permission.BIND_MUSIC_RECOGNITION_SERVICE");
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(
                    AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(
                            componentName, "Could not get service for "));
        }
    }

    public final void onServiceDied(Object obj) {
        RemoteMusicRecognitionService remoteMusicRecognitionService =
                (RemoteMusicRecognitionService) obj;
        try {
            remoteMusicRecognitionService.mServerCallback.mClientCallback.onRecognitionFailed(5);
        } catch (RemoteException unused) {
        }
        Slog.w(
                "MusicRecognitionManagerPerUserService",
                "remote service died: " + remoteMusicRecognitionService);
        destroyService();
    }

    public final void startRecordAudioOp(String str) {
        AppOpsManager appOpsManager = this.mAppOpsManager;
        String permissionToOp = AppOpsManager.permissionToOp("android.permission.RECORD_AUDIO");
        Objects.requireNonNull(permissionToOp);
        ServiceInfo serviceInfo = this.mServiceInfo;
        int startProxyOp =
                appOpsManager.startProxyOp(
                        permissionToOp,
                        serviceInfo.applicationInfo.uid,
                        serviceInfo.packageName,
                        str,
                        this.mAttributionMessage);
        if (startProxyOp != 0) {
            throw new SecurityException(
                    String.format(
                            "Failed to obtain RECORD_AUDIO permission (status: %d) for receiving"
                                + " service: %s",
                            Integer.valueOf(startProxyOp), this.mServiceInfo.getComponentName()));
        }
        ServiceInfo serviceInfo2 = this.mServiceInfo;
        Slog.i(
                "MusicRecognitionManagerPerUserService",
                String.format(
                        "Starting audio streaming. Attributing to %s (%d) with tag '%s'",
                        serviceInfo2.packageName,
                        Integer.valueOf(serviceInfo2.applicationInfo.uid),
                        str));
    }

    public final void streamAudio(
            RecognitionRequest recognitionRequest,
            int i,
            AudioRecord audioRecord,
            OutputStream outputStream) {
        int bufferSizeInFrames = audioRecord.getBufferSizeInFrames() / i;
        byte[] bArr = new byte[bufferSizeInFrames];
        int ignoreBeginningFrames = recognitionRequest.getIgnoreBeginningFrames() * 2;
        audioRecord.startRecording();
        int i2 = 0;
        int i3 = 0;
        while (i2 >= 0
                && i3 < audioRecord.getBufferSizeInFrames() * 2
                && this.mRemoteService != null) {
            i2 = audioRecord.read(bArr, 0, bufferSizeInFrames);
            if (i2 > 0) {
                i3 += i2;
                if (ignoreBeginningFrames > 0) {
                    ignoreBeginningFrames -= i2;
                    if (ignoreBeginningFrames < 0) {
                        outputStream.write(
                                bArr, i2 + ignoreBeginningFrames, -ignoreBeginningFrames);
                    }
                } else {
                    outputStream.write(bArr);
                }
            }
        }
        BootReceiver$$ExternalSyntheticOutline0.m(
                i3,
                "Streamed ",
                " bytes from audio record",
                "MusicRecognitionManagerPerUserService");
    }
}
