package android.media.musicrecognition;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.MediaMetadata;
import android.media.musicrecognition.IMusicRecognitionService;
import android.media.musicrecognition.MusicRecognitionService;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

@SystemApi
/* loaded from: classes3.dex */
public abstract class MusicRecognitionService extends Service {
    public static final String ACTION_MUSIC_SEARCH_LOOKUP = "android.service.musicrecognition.MUSIC_RECOGNITION";
    private static final String TAG = "MusicRecognitionService";
    private Handler mHandler;
    private final IMusicRecognitionService mServiceInterface = new IMusicRecognitionService.Stub() { // from class: android.media.musicrecognition.MusicRecognitionService.1
        @Override // android.media.musicrecognition.IMusicRecognitionService
        public void onAudioStreamStarted(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, final IMusicRecognitionServiceCallback iMusicRecognitionServiceCallback) {
            Handler handler = MusicRecognitionService.this.mHandler;
            final MusicRecognitionService musicRecognitionService = MusicRecognitionService.this;
            handler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.musicrecognition.MusicRecognitionService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    MusicRecognitionService.this.onRecognize((ParcelFileDescriptor) obj, (AudioFormat) obj2, (MusicRecognitionService.AnonymousClass1.C00051) obj3);
                }
            }, parcelFileDescriptor, audioFormat, new Callback(this) { // from class: android.media.musicrecognition.MusicRecognitionService.1.1
                @Override // android.media.musicrecognition.MusicRecognitionService.Callback
                public void onRecognitionSucceeded(MediaMetadata mediaMetadata, Bundle bundle) {
                    try {
                        iMusicRecognitionServiceCallback.onRecognitionSucceeded(mediaMetadata, bundle);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }

                @Override // android.media.musicrecognition.MusicRecognitionService.Callback
                public void onRecognitionFailed(int i) {
                    try {
                        iMusicRecognitionServiceCallback.onRecognitionFailed(i);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }));
        }

        @Override // android.media.musicrecognition.IMusicRecognitionService
        public void getAttributionTag(IMusicRecognitionAttributionTagCallback iMusicRecognitionAttributionTagCallback) throws RemoteException {
            iMusicRecognitionAttributionTagCallback.onAttributionTag(MusicRecognitionService.this.getAttributionTag());
        }
    };

    public interface Callback {
        void onRecognitionFailed(int i);

        void onRecognitionSucceeded(MediaMetadata mediaMetadata, Bundle bundle);
    }

    public abstract void onRecognize(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, Callback callback);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (ACTION_MUSIC_SEARCH_LOOKUP.equals(intent.getAction())) {
            return this.mServiceInterface.asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.musicrecognition.MUSIC_RECOGNITION: " + intent);
        return null;
    }
}
