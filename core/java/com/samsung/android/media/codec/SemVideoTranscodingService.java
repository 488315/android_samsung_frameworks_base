package com.samsung.android.media.codec;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.media.codec.IVideoTranscodingService;
import com.samsung.android.media.codec.IVideoTranscodingServiceCallback;
import com.samsung.android.media.codec.client.ClientImpl;
import com.samsung.android.media.codec.client.ImgCsConverterClient;
import com.samsung.android.media.codec.client.SemMediaCaptureClient;
import com.samsung.android.media.codec.client.SemVideoTranscoderClient;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemVideoTranscodingService {
    public static final String KEY_INPUT_PATH = "input-path";
    public static final String KEY_OUTPUT_PATH = "output-path";
    public static final String KEY_PLAYBACK_SPEED_CHANGES = "playback-speed-changes";
    private static final String TAG = "SemVideoTranscodingService";
    public static final int TRANSCODING_MODE_BOOMERANG = 202;
    public static final int TRANSCODING_MODE_HDR_TO_SDR = 0;
    public static final int TRANSCODING_MODE_HLG_TO_SDR = 2;
    public static final int TRANSCODING_MODE_INSTANT_SLOW_MOTION = 200;
    public static final int TRANSCODING_MODE_INSTANT_SLOW_MOTION_WITH_HDR2SDR = 201;
    public static final int TRANSCODING_MODE_P3_TO_SRGB = 100;
    public static final int TRANSCODING_MODE_SLOW_MOTION_TO_NORMAL = 1;
    private IVideoTranscodingService mService;

    public static class PlaybackSpeedChange {
        public int endMs;
        public float rate;
        public int repeatCount;
        public int startMs;

        public PlaybackSpeedChange() {
            this.startMs = 0;
            this.endMs = 0;
            this.rate = 1.0f;
            this.repeatCount = 1;
        }

        public PlaybackSpeedChange(int startMs, int endMs, float rate) {
            this.startMs = startMs;
            this.endMs = endMs;
            this.rate = rate;
            this.repeatCount = 1;
        }

        public PlaybackSpeedChange(int startMs, int endMs, float rate, int repeatCount) {
            this.startMs = startMs;
            this.endMs = endMs;
            this.rate = rate;
            this.repeatCount = repeatCount;
        }
    }

    public static class ProgressCallback extends IVideoTranscodingServiceCallback.Stub {
        private Client mClient;

        public void setClient(Client client) {
            this.mClient = client;
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onReady() throws RemoteException {
            this.mClient.transcode();
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onStarted() throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onProgressChanged(int i) throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onCompleted() throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onError() throws RemoteException {
        }
    }

    public static class Client {
        private final ClientImpl mImpl;

        public Client(IVideoTranscodingService transcodingService, String id, int mode, Map args, ProgressCallback progressCallback) {
            progressCallback.setClient(this);
            if (mode == 0 || mode == 1 || mode == 2) {
                this.mImpl = new SemVideoTranscoderClient(transcodingService, id, mode, args, progressCallback);
                return;
            }
            if (mode == 100) {
                this.mImpl = new ImgCsConverterClient(transcodingService, id, mode, args, progressCallback);
            } else if (mode == 200 || mode == 201 || mode == 202) {
                this.mImpl = new SemMediaCaptureClient(transcodingService, id, mode, args, progressCallback);
            } else {
                this.mImpl = null;
            }
        }

        public void start() {
            this.mImpl.start();
        }

        public void stop() {
            this.mImpl.stop();
        }

        public void transcode() {
            this.mImpl.transcode();
        }

        public boolean isValid() {
            return this.mImpl != null;
        }
    }

    public SemVideoTranscodingService() {
        IBinder b = ServiceManager.getService("SemVideoTranscodingService");
        this.mService = IVideoTranscodingService.Stub.asInterface(b);
    }

    public Client createClient(int mode, String inputPath, String outputPath, ProgressCallback progressCallback) {
        Log.d("SemVideoTranscodingService", "mode(" + mode + ") in(" + inputPath + ") out(" + outputPath + NavigationBarInflaterView.KEY_CODE_END);
        Map args = new HashMap();
        args.put(KEY_INPUT_PATH, inputPath);
        args.put(KEY_OUTPUT_PATH, outputPath);
        return createClient(mode, args, progressCallback);
    }

    public Client createClient(int mode, Map args, ProgressCallback progressCallback) {
        Log.d("SemVideoTranscodingService", "mode(" + mode + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mService == null) {
            Log.w("SemVideoTranscodingService", "IVideoTranscodingService is null");
            return null;
        }
        try {
            String id = this.mService.register(mode, progressCallback);
            if (id == null) {
                Log.w("SemVideoTranscodingService", "id is null");
                return null;
            }
            Client client = new Client(this.mService, id, mode, args, progressCallback);
            if (!client.isValid()) {
                Log.w("SemVideoTranscodingService", "Unsupported mode (" + mode + NavigationBarInflaterView.KEY_CODE_END);
                return null;
            }
            return client;
        } catch (RemoteException e) {
            Log.e("SemVideoTranscodingService", "Exception createClient()");
            e.printStackTrace();
            return null;
        }
    }
}
