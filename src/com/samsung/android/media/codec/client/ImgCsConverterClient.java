package com.samsung.android.media.codec.client;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.media.codec.IVideoTranscodingService;
import com.samsung.android.media.codec.ImgCsConverter;
import com.samsung.android.media.codec.SemVideoTranscodingService;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes6.dex */
public class ImgCsConverterClient extends ClientImpl {
    public ImgCsConverterClient(IVideoTranscodingService iVideoTranscodingService, String str, int i, Map map, SemVideoTranscodingService.ProgressCallback progressCallback) {
        super(iVideoTranscodingService, str, i, map, progressCallback);
    }

    @Override // com.samsung.android.media.codec.client.ClientImpl
    public void stop() {
        this.mIgnoreError = true;
        if (this.mIsRunning) {
            Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, "stop running client id(" + this.mID + NavigationBarInflaterView.KEY_CODE_END);
            this.mIsRunning = false;
        }
        try {
            this.mTranscodingService.stopTask(this.mID);
        } catch (RemoteException e) {
            Log.e(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Exception startTask()");
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.media.codec.client.ClientImpl
    public void transcode() throws IOException {
        try {
            new ConverterThread().start();
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

    private class ConverterThread extends Thread {
        private static final String THREAD_PREFIX = "converter";

        public ConverterThread() {
            setName(THREAD_PREFIX + ImgCsConverterClient.this.mID);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, getName() + " is running");
            try {
                try {
                    try {
                        ImgCsConverterClient.this.mIsRunning = true;
                        ImgCsConverterClient.this.mProgressCallback.onStarted();
                        if (ImgCsConverter.convertToSRGB((String) ImgCsConverterClient.this.mArgs.get(SemVideoTranscodingService.KEY_INPUT_PATH), (String) ImgCsConverterClient.this.mArgs.get(SemVideoTranscodingService.KEY_OUTPUT_PATH))) {
                            ImgCsConverterClient.this.mProgressCallback.onProgressChanged(100);
                            ImgCsConverterClient.this.mProgressCallback.onCompleted();
                        } else {
                            ImgCsConverterClient.this.mProgressCallback.onError();
                        }
                        ImgCsConverterClient.this.mIsRunning = false;
                        ImgCsConverterClient.this.mTranscodingService.stopTask(ImgCsConverterClient.this.mID);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.w(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Task(" + ImgCsConverterClient.this.mID + ") has been terminated unexpectedly");
                        if (ImgCsConverterClient.this.mIgnoreError) {
                            Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, "Client has stopped " + ImgCsConverterClient.this.mID + ", Ignore this error.");
                        } else {
                            try {
                                ImgCsConverterClient.this.mProgressCallback.onError();
                            } catch (RemoteException e2) {
                                e2.printStackTrace();
                            }
                        }
                        ImgCsConverterClient.this.mIsRunning = false;
                        ImgCsConverterClient.this.mTranscodingService.stopTask(ImgCsConverterClient.this.mID);
                    }
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                }
            } catch (Throwable th) {
                ImgCsConverterClient.this.mIsRunning = false;
                try {
                    ImgCsConverterClient.this.mTranscodingService.stopTask(ImgCsConverterClient.this.mID);
                } catch (RemoteException e4) {
                    e4.printStackTrace();
                }
                throw th;
            }
        }
    }
}
