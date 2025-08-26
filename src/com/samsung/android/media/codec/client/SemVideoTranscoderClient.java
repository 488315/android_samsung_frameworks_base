package com.samsung.android.media.codec.client;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetadataRetriever;
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
    public void stop() throws InterruptedException {
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
    public void transcode() throws IOException {
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

        /* JADX WARN: Code restructure failed: missing block: B:44:0x0173, code lost:
        
            if (r3 > 8) goto L18;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:47:0x0179 A[Catch: Exception -> 0x0203, all -> 0x0207, TryCatch #3 {all -> 0x0207, blocks: (B:3:0x0033, B:5:0x0060, B:9:0x0108, B:47:0x0179, B:49:0x01ab, B:52:0x01ba, B:54:0x01cb, B:67:0x020d, B:69:0x022e, B:71:0x0249, B:74:0x0252, B:51:0x01b1, B:55:0x01e2, B:10:0x0123, B:15:0x012d, B:21:0x013a, B:36:0x015a, B:42:0x0166), top: B:91:0x0033, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:55:0x01e2 A[Catch: Exception -> 0x0201, all -> 0x0207, TRY_LEAVE, TryCatch #3 {all -> 0x0207, blocks: (B:3:0x0033, B:5:0x0060, B:9:0x0108, B:47:0x0179, B:49:0x01ab, B:52:0x01ba, B:54:0x01cb, B:67:0x020d, B:69:0x022e, B:71:0x0249, B:74:0x0252, B:51:0x01b1, B:55:0x01e2, B:10:0x0123, B:15:0x012d, B:21:0x013a, B:36:0x015a, B:42:0x0166), top: B:91:0x0033, inners: #0 }] */
        /* JADX WARN: Type inference failed for: r2v16 */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            String str;
            int i;
            boolean z;
            int i2;
            String str2 = "Task(";
            super.run();
            Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, getName() + " is running");
            try {
                try {
                    try {
                        String str3 = (String) SemVideoTranscoderClient.this.mArgs.get(SemVideoTranscodingService.KEY_INPUT_PATH);
                        String str4 = (String) SemVideoTranscoderClient.this.mArgs.get(SemVideoTranscodingService.KEY_OUTPUT_PATH);
                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        mediaMetadataRetriever.setDataSource(str3);
                        String strExtractMetadata = mediaMetadataRetriever.extractMetadata(18);
                        str = "Client has stopped ";
                        try {
                            String strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(19);
                            String strExtractMetadata3 = mediaMetadataRetriever.extractMetadata(36);
                            String strExtractMetadata4 = mediaMetadataRetriever.extractMetadata(1027);
                            String strExtractMetadata5 = mediaMetadataRetriever.extractMetadata(1028);
                            String strExtractMetadata6 = mediaMetadataRetriever.extractMetadata(1022);
                            mediaMetadataRetriever.release();
                            Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    METADATA_KEY_VIDEO_WIDTH[" + strExtractMetadata + NavigationBarInflaterView.SIZE_MOD_END);
                            Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    METADATA_KEY_VIDEO_HEIGHT[" + strExtractMetadata2 + NavigationBarInflaterView.SIZE_MOD_END);
                            Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    METADATA_KEY_COLOR_TRANSFER[" + strExtractMetadata3 + NavigationBarInflaterView.SIZE_MOD_END);
                            Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    SEM_METADATA_KEY_HDR10_VIDEO[" + strExtractMetadata4 + NavigationBarInflaterView.SIZE_MOD_END);
                            Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    SEM_METADATA_KEY_VIDEO_BIT_DEPTH[" + strExtractMetadata5 + NavigationBarInflaterView.SIZE_MOD_END);
                            Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    SEM_METADATA_KEY_RECORDINGMODE[" + strExtractMetadata6 + NavigationBarInflaterView.SIZE_MOD_END);
                            int i3 = Integer.parseInt(strExtractMetadata);
                            str2 = Integer.parseInt(strExtractMetadata2);
                            int i4 = SemVideoTranscoderClient.this.mMode;
                            if (i4 == 0) {
                                if (SemVideoTranscoderClient.isSupportedHdrToSdr() && strExtractMetadata4 != null && strExtractMetadata5 != null) {
                                    int i5 = Integer.parseInt(strExtractMetadata5);
                                    if (strExtractMetadata4.equals("yes")) {
                                    }
                                }
                                i = 4;
                                z = false;
                                if (z) {
                                }
                                str2 = 0;
                                SemVideoTranscoderClient.this.mIsRunning = false;
                                SemVideoTranscoderClient.this.mTranscodingService.stopTask(SemVideoTranscoderClient.this.mID);
                                return;
                            }
                            if (i4 == 1) {
                                if (strExtractMetadata6 != null && ((i2 = Integer.parseInt(strExtractMetadata6)) == 1 || i2 == 12 || i2 == 13 || i2 == 15 || i2 == 19 || i2 == 21)) {
                                    i = i2 == 21 ? 5 : 4;
                                    z = true;
                                    if (z) {
                                    }
                                    str2 = 0;
                                    SemVideoTranscoderClient.this.mIsRunning = false;
                                    SemVideoTranscoderClient.this.mTranscodingService.stopTask(SemVideoTranscoderClient.this.mID);
                                    return;
                                }
                                i = 4;
                                z = false;
                                if (z) {
                                }
                                str2 = 0;
                                SemVideoTranscoderClient.this.mIsRunning = false;
                                SemVideoTranscoderClient.this.mTranscodingService.stopTask(SemVideoTranscoderClient.this.mID);
                                return;
                            }
                            try {
                                if (i4 == 2) {
                                    if (!SemVideoTranscoderClient.isSupportedHdrToSdr() || strExtractMetadata3 == null || Integer.parseInt(strExtractMetadata3) != 7) {
                                    }
                                    z = true;
                                    if (z) {
                                        SemVideoTranscoderClient.this.mTranscoder.initialize(str4, i3, str2, str3);
                                        SemVideoTranscoderClient.this.mTranscoder.setVideoTranscodingServiceCallback(SemVideoTranscoderClient.this.mProgressCallback);
                                        SemVideoTranscoderClient.this.mTranscoder.setOutputConfig(1, i);
                                        SemVideoTranscoderClient.this.mTranscoder.setOutputConfig(2, 2);
                                        if (SemVideoTranscoderClient.this.mMode == 0 || SemVideoTranscoderClient.this.mMode == 2) {
                                            SemVideoTranscoderClient.this.mTranscoder.setOutputConfig(4, 8);
                                        }
                                        SemVideoTranscoderClient.this.mIsRunning = true;
                                        SemVideoTranscoderClient.this.mTranscoder.encode();
                                        Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Task(" + SemVideoTranscoderClient.this.mID + ") has been finished");
                                    } else {
                                        Log.w(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Invalid argument");
                                        SemVideoTranscoderClient.this.mProgressCallback.onError();
                                    }
                                    str2 = 0;
                                    SemVideoTranscoderClient.this.mIsRunning = false;
                                    SemVideoTranscoderClient.this.mTranscodingService.stopTask(SemVideoTranscoderClient.this.mID);
                                    return;
                                }
                                Log.w(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Unsupported mode (" + SemVideoTranscoderClient.this.mMode + NavigationBarInflaterView.KEY_CODE_END);
                                if (z) {
                                }
                                str2 = 0;
                                SemVideoTranscoderClient.this.mIsRunning = false;
                                SemVideoTranscoderClient.this.mTranscodingService.stopTask(SemVideoTranscoderClient.this.mID);
                                return;
                            } catch (Exception e) {
                                e = e;
                            }
                            i = 4;
                            z = false;
                        } catch (Exception e2) {
                            e = e2;
                            str2 = "Task(";
                        }
                    } catch (Exception e3) {
                        e = e3;
                        str = "Client has stopped ";
                    }
                    e.printStackTrace();
                    Log.w(Context.SEM_VIDEO_TRANSCODING_SERVICE, str2 + SemVideoTranscoderClient.this.mID + ") has been terminated unexpectedly");
                    if (SemVideoTranscoderClient.this.mIgnoreError) {
                        Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, str + SemVideoTranscoderClient.this.mID + ", Ignore this error.");
                    } else {
                        try {
                            SemVideoTranscoderClient.this.mProgressCallback.onError();
                        } catch (RemoteException e4) {
                            e4.printStackTrace();
                        }
                    }
                    SemVideoTranscoderClient.this.mIsRunning = false;
                    SemVideoTranscoderClient.this.mTranscodingService.stopTask(SemVideoTranscoderClient.this.mID);
                } finally {
                }
            } catch (RemoteException e5) {
                e5.printStackTrace();
            }
        }
    }
}
