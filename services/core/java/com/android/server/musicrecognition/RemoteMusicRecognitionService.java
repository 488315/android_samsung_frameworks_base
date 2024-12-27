package com.android.server.musicrecognition;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioFormat;
import android.media.musicrecognition.IMusicRecognitionAttributionTagCallback;
import android.media.musicrecognition.IMusicRecognitionService;
import android.os.IBinder;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;

import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;

import java.util.concurrent.CompletableFuture;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RemoteMusicRecognitionService
        extends AbstractMultiplePendingRequestsRemoteService {
    public final MusicRecognitionManagerPerUserService.MusicRecognitionServiceCallback
            mServerCallback;

    public RemoteMusicRecognitionService(
            Context context,
            ComponentName componentName,
            int i,
            MusicRecognitionManagerPerUserService musicRecognitionManagerPerUserService,
            MusicRecognitionManagerPerUserService.MusicRecognitionServiceCallback
                    musicRecognitionServiceCallback,
            boolean z,
            boolean z2) {
        super(
                context,
                "android.service.musicrecognition.MUSIC_RECOGNITION",
                componentName,
                i,
                musicRecognitionManagerPerUserService,
                context.getMainThreadHandler(),
                (z ? 4194304 : 0) | 4096,
                z2,
                1);
        this.mServerCallback = musicRecognitionServiceCallback;
    }

    public final CompletableFuture getAttributionTag() {
        final CompletableFuture completableFuture = new CompletableFuture();
        scheduleAsyncRequest(
                new AbstractRemoteService
                        .AsyncRequest() { // from class:
                                          // com.android.server.musicrecognition.RemoteMusicRecognitionService$$ExternalSyntheticLambda1
                    public final void run(IInterface iInterface) {
                        RemoteMusicRecognitionService remoteMusicRecognitionService =
                                RemoteMusicRecognitionService.this;
                        final CompletableFuture completableFuture2 = completableFuture;
                        remoteMusicRecognitionService.getClass();
                        ((IMusicRecognitionService) iInterface)
                                .getAttributionTag(
                                        new IMusicRecognitionAttributionTagCallback
                                                .Stub() { // from class:
                                                          // com.android.server.musicrecognition.RemoteMusicRecognitionService.1
                                            public final void onAttributionTag(String str) {
                                                completableFuture2.complete(str);
                                            }
                                        });
                    }
                });
        return completableFuture;
    }

    public final IInterface getServiceInterface(IBinder iBinder) {
        return IMusicRecognitionService.Stub.asInterface(iBinder);
    }

    public final long getTimeoutIdleBindMillis() {
        return 40000L;
    }

    public final void onAudioStreamStarted(
            final ParcelFileDescriptor parcelFileDescriptor, final AudioFormat audioFormat) {
        scheduleAsyncRequest(
                new AbstractRemoteService
                        .AsyncRequest() { // from class:
                                          // com.android.server.musicrecognition.RemoteMusicRecognitionService$$ExternalSyntheticLambda0
                    public final void run(IInterface iInterface) {
                        IMusicRecognitionService iMusicRecognitionService =
                                (IMusicRecognitionService) iInterface;
                        iMusicRecognitionService.onAudioStreamStarted(
                                parcelFileDescriptor,
                                audioFormat,
                                RemoteMusicRecognitionService.this.mServerCallback);
                    }
                });
    }
}
