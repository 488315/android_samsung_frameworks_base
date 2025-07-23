package com.samsung.android.media.codec;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
    private IVideoTranscodingService mService = IVideoTranscodingService.Stub.asInterface(ServiceManager.getService("SemVideoTranscodingService"));

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

        public PlaybackSpeedChange(int i, int i2, float f) {
            this.startMs = i;
            this.endMs = i2;
            this.rate = f;
            this.repeatCount = 1;
        }

        public PlaybackSpeedChange(int i, int i2, float f, int i3) {
            this.startMs = i;
            this.endMs = i2;
            this.rate = f;
            this.repeatCount = i3;
        }
    }

    public static class ProgressCallback extends IVideoTranscodingServiceCallback.Stub {
        private Client mClient;

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onCompleted() throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onError() throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onProgressChanged(int i) throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onStarted() throws RemoteException {
        }

        public void setClient(Client client) {
            this.mClient = client;
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onReady() throws RemoteException {
            this.mClient.transcode();
        }
    }

    public static class Client {
        private final ClientImpl mImpl;

        public Client(IVideoTranscodingService iVideoTranscodingService, String str, int i, Map map, ProgressCallback progressCallback) {
            progressCallback.setClient(this);
            if (i == 0 || i == 1 || i == 2) {
                this.mImpl = new SemVideoTranscoderClient(iVideoTranscodingService, str, i, map, progressCallback);
                return;
            }
            if (i == 100) {
                this.mImpl = new ImgCsConverterClient(iVideoTranscodingService, str, i, map, progressCallback);
            } else if (i == 200 || i == 201 || i == 202) {
                this.mImpl = new SemMediaCaptureClient(iVideoTranscodingService, str, i, map, progressCallback);
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

    public Client createClient(int i, String str, String str2, ProgressCallback progressCallback) {
        Log.d("SemVideoTranscodingService", "mode(" + i + ") in(" + str + ") out(" + str2 + NavigationBarInflaterView.KEY_CODE_END);
        HashMap hashMap = new HashMap();
        hashMap.put(KEY_INPUT_PATH, str);
        hashMap.put(KEY_OUTPUT_PATH, str2);
        return createClient(i, hashMap, progressCallback);
    }

    public Client createClient(int i, Map map, ProgressCallback progressCallback) {
        Log.d("SemVideoTranscodingService", "mode(" + i + NavigationBarInflaterView.KEY_CODE_END);
        IVideoTranscodingService iVideoTranscodingService = this.mService;
        if (iVideoTranscodingService == null) {
            Log.w("SemVideoTranscodingService", "IVideoTranscodingService is null");
            return null;
        }
        try {
            String register = iVideoTranscodingService.register(i, progressCallback);
            if (register == null) {
                Log.w("SemVideoTranscodingService", "id is null");
                return null;
            }
            Client client = new Client(this.mService, register, i, map, progressCallback);
            if (client.isValid()) {
                return client;
            }
            Log.w("SemVideoTranscodingService", "Unsupported mode (" + i + NavigationBarInflaterView.KEY_CODE_END);
            return null;
        } catch (RemoteException e) {
            Log.e("SemVideoTranscodingService", "Exception createClient()");
            e.printStackTrace();
            return null;
        }
    }
}
