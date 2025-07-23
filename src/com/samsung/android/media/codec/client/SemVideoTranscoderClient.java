package com.samsung.android.media.codec.client;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.media.codec.IVideoTranscodingService;
import com.samsung.android.media.codec.SemVideoTranscoder;
import com.samsung.android.media.codec.SemVideoTranscodingService;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemVideoTranscoderClient extends ClientImpl {
    private static final int RECORDING_MODE_SLOW_MOTION = 1;
    private static final int RECORDING_MODE_SLOW_MOTION_V2 = 12;
    private static final int RECORDING_MODE_SLOW_MOTION_V2_120 = 13;
    private static final int RECORDING_MODE_SLOW_MOTION_V2_HEVC = 21;
    private static final int RECORDING_MODE_SLOW_MOTION_V2_WITHOUT_SVC = 15;
    private static final int RECORDING_MODE_SLOW_MOTION_V2_WITHOUT_SVC_240 = 19;
    private final SemVideoTranscoder mTranscoder;

    public SemVideoTranscoderClient(IVideoTranscodingService iVideoTranscodingService, String str, int i, Map map, SemVideoTranscodingService.ProgressCallback progressCallback) {
        super(iVideoTranscodingService, str, i, map, progressCallback);
        this.mTranscoder = new SemVideoTranscoder();
    }

    @Override // com.samsung.android.media.codec.client.ClientImpl
    public void stop() {
        this.mIgnoreError = true;
        if (this.mIsRunning) {
            Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, "stop running client id(" + this.mID + NavigationBarInflaterView.KEY_CODE_END);
            this.mTranscoder.stop();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.mIsRunning = false;
        }
        try {
            this.mTranscodingService.stopTask(this.mID);
        } catch (RemoteException e2) {
            Log.e(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Exception startTask()");
            e2.printStackTrace();
        }
    }

    @Override // com.samsung.android.media.codec.client.ClientImpl
    public void transcode() {
        try {
            new TranscoderThread().start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Task(" + this.mID + ") has been terminated unexpectedly");
            if (this.mIgnoreError) {
                Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Client has stopped " + this.mID + ", Ignore this error.");
            } else {
                try {
                    this.mIsRunning = false;
                    this.mProgressCallback.onError();
                    this.mTranscodingService.stopTask(this.mID);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
            try {
                if (this.mFis != null) {
                    this.mFis.close();
                }
                if (this.mFos != null) {
                    this.mFos.close();
                }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSupportedHdrToSdr() {
        boolean z = Build.VERSION.SEM_PLATFORM_INT >= 100000 ? SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HDR2SDR") : false;
        Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "isSupportedHdrToSdr() " + z);
        return z;
    }

    private class TranscoderThread extends Thread {
        private static final String THREAD_PREFIX = "transcoder";

        public TranscoderThread() {
            setName(THREAD_PREFIX + SemVideoTranscoderClient.this.mID);
        }

        /* JADX WARN: Code restructure failed: missing block: B:76:0x0173, code lost:
        
            if (r3 > 8) goto L18;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0179 A[Catch: Exception -> 0x0203, all -> 0x0207, TryCatch #3 {all -> 0x0207, blocks: (B:4:0x0033, B:7:0x0060, B:11:0x0108, B:15:0x0179, B:17:0x01ab, B:19:0x01ba, B:21:0x01cb, B:31:0x020d, B:33:0x022e, B:38:0x0249, B:41:0x0252, B:28:0x01b1, B:29:0x01e2, B:42:0x0123, B:47:0x012d, B:54:0x013a, B:68:0x015a, B:74:0x0166), top: B:3:0x0033, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:29:0x01e2 A[Catch: Exception -> 0x0201, all -> 0x0207, TRY_LEAVE, TryCatch #3 {all -> 0x0207, blocks: (B:4:0x0033, B:7:0x0060, B:11:0x0108, B:15:0x0179, B:17:0x01ab, B:19:0x01ba, B:21:0x01cb, B:31:0x020d, B:33:0x022e, B:38:0x0249, B:41:0x0252, B:28:0x01b1, B:29:0x01e2, B:42:0x0123, B:47:0x012d, B:54:0x013a, B:68:0x015a, B:74:0x0166), top: B:3:0x0033, inners: #0 }] */
        /* JADX WARN: Type inference failed for: r2v16 */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 641
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.codec.client.SemVideoTranscoderClient.TranscoderThread.run():void");
        }
    }
}
