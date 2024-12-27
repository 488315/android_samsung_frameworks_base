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

    public SemVideoTranscoderClient(IVideoTranscodingService transcodingService, String id, int mode, Map args, SemVideoTranscodingService.ProgressCallback progressCallback) {
        super(transcodingService, id, mode, args, progressCallback);
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
            TranscoderThread thread = new TranscoderThread();
            thread.start();
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
                } catch (RemoteException re) {
                    re.printStackTrace();
                }
            }
            try {
                if (this.mFis != null) {
                    this.mFis.close();
                }
                if (this.mFos != null) {
                    this.mFos.close();
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSupportedHdrToSdr() {
        boolean result = false;
        if (Build.VERSION.SEM_PLATFORM_INT >= 100000) {
            result = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HDR2SDR");
        }
        Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "isSupportedHdrToSdr() " + result);
        return result;
    }

    private class TranscoderThread extends Thread {
        private static final String THREAD_PREFIX = "transcoder";

        public TranscoderThread() {
            setName(THREAD_PREFIX + SemVideoTranscoderClient.this.mID);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            int dstVideoCodecType;
            int i;
            super.run();
            Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, getName() + " is running");
            try {
                try {
                    try {
                        String inputPath = (String) SemVideoTranscoderClient.this.mArgs.get(SemVideoTranscodingService.KEY_INPUT_PATH);
                        String outputPath = (String) SemVideoTranscoderClient.this.mArgs.get(SemVideoTranscodingService.KEY_OUTPUT_PATH);
                        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                        retriever.setDataSource(inputPath);
                        String width = retriever.extractMetadata(18);
                        String height = retriever.extractMetadata(19);
                        String colorTransfer = retriever.extractMetadata(36);
                        String hdr10bit = retriever.extractMetadata(1027);
                        String bitDepth = retriever.extractMetadata(1028);
                        String recording_mode = retriever.extractMetadata(1022);
                        retriever.release();
                        Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    METADATA_KEY_VIDEO_WIDTH[" + width + NavigationBarInflaterView.SIZE_MOD_END);
                        Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    METADATA_KEY_VIDEO_HEIGHT[" + height + NavigationBarInflaterView.SIZE_MOD_END);
                        Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    METADATA_KEY_COLOR_TRANSFER[" + colorTransfer + NavigationBarInflaterView.SIZE_MOD_END);
                        Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    SEM_METADATA_KEY_HDR10_VIDEO[" + hdr10bit + NavigationBarInflaterView.SIZE_MOD_END);
                        Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    SEM_METADATA_KEY_VIDEO_BIT_DEPTH[" + bitDepth + NavigationBarInflaterView.SIZE_MOD_END);
                        Log.d(Context.SEM_VIDEO_TRANSCODING_SERVICE, "    SEM_METADATA_KEY_RECORDINGMODE[" + recording_mode + NavigationBarInflaterView.SIZE_MOD_END);
                        int w = Integer.parseInt(width);
                        int h = Integer.parseInt(height);
                        boolean isValid = false;
                        switch (SemVideoTranscoderClient.this.mMode) {
                            case 0:
                                if (SemVideoTranscoderClient.isSupportedHdrToSdr() && hdr10bit != null && bitDepth != null) {
                                    int bit = Integer.parseInt(bitDepth);
                                    if (hdr10bit.equals("yes") && bit > 8) {
                                        isValid = true;
                                        dstVideoCodecType = 4;
                                        break;
                                    }
                                }
                                dstVideoCodecType = 4;
                                break;
                            case 1:
                                if (recording_mode != null && ((i = Integer.parseInt(recording_mode)) == 1 || i == 12 || i == 13 || i == 15 || i == 19 || i == 21)) {
                                    int dstVideoCodecType2 = i == 21 ? 5 : 4;
                                    isValid = true;
                                    dstVideoCodecType = dstVideoCodecType2;
                                    break;
                                }
                                dstVideoCodecType = 4;
                                break;
                            case 2:
                                if (SemVideoTranscoderClient.isSupportedHdrToSdr() && colorTransfer != null) {
                                    int color = Integer.parseInt(colorTransfer);
                                    if (color == 7) {
                                        isValid = true;
                                        dstVideoCodecType = 4;
                                        break;
                                    }
                                }
                                dstVideoCodecType = 4;
                                break;
                            default:
                                Log.w(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Unsupported mode (" + SemVideoTranscoderClient.this.mMode + NavigationBarInflaterView.KEY_CODE_END);
                                dstVideoCodecType = 4;
                        }
                        if (isValid) {
                            SemVideoTranscoderClient.this.mTranscoder.initialize(outputPath, w, h, inputPath);
                            SemVideoTranscoderClient.this.mTranscoder.setVideoTranscodingServiceCallback(SemVideoTranscoderClient.this.mProgressCallback);
                            SemVideoTranscoderClient.this.mTranscoder.setOutputConfig(1, dstVideoCodecType);
                            SemVideoTranscoderClient.this.mTranscoder.setOutputConfig(2, 2);
                            if (SemVideoTranscoderClient.this.mMode != 0 && SemVideoTranscoderClient.this.mMode != 2) {
                                SemVideoTranscoderClient.this.mIsRunning = true;
                                SemVideoTranscoderClient.this.mTranscoder.encode();
                                Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Task(" + SemVideoTranscoderClient.this.mID + ") has been finished");
                            }
                            SemVideoTranscoderClient.this.mTranscoder.setOutputConfig(4, 8);
                            SemVideoTranscoderClient.this.mIsRunning = true;
                            SemVideoTranscoderClient.this.mTranscoder.encode();
                            Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Task(" + SemVideoTranscoderClient.this.mID + ") has been finished");
                        } else {
                            Log.w(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Invalid argument");
                            SemVideoTranscoderClient.this.mProgressCallback.onError();
                        }
                        SemVideoTranscoderClient.this.mIsRunning = false;
                        SemVideoTranscoderClient.this.mTranscodingService.stopTask(SemVideoTranscoderClient.this.mID);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.w(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Task(" + SemVideoTranscoderClient.this.mID + ") has been terminated unexpectedly");
                        if (SemVideoTranscoderClient.this.mIgnoreError) {
                            Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Client has stopped " + SemVideoTranscoderClient.this.mID + ", Ignore this error.");
                        } else {
                            try {
                                SemVideoTranscoderClient.this.mProgressCallback.onError();
                            } catch (RemoteException re) {
                                re.printStackTrace();
                            }
                        }
                        SemVideoTranscoderClient.this.mIsRunning = false;
                        SemVideoTranscoderClient.this.mTranscodingService.stopTask(SemVideoTranscoderClient.this.mID);
                    }
                } finally {
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }
}
