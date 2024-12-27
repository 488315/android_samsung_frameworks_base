package com.samsung.android.media.codec.client;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.media.codec.IVideoTranscodingService;
import com.samsung.android.media.codec.SemVideoTranscodingService;
import com.samsung.android.media.mediacapture.SemMediaCapture;
import com.samsung.android.media.mediacapture.SemMediaCapture.BoomerangConfiguration;
import com.samsung.android.media.mediacapture.SemMediaCapture.DynamicViewingConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes6.dex */
public class SemMediaCaptureClient extends ClientImpl {
    private final SemMediaCapture mCapture;

    public SemMediaCaptureClient(
            IVideoTranscodingService transcodingService,
            String id,
            int mode,
            Map args,
            SemVideoTranscodingService.ProgressCallback progressCallback) {
        super(transcodingService, id, mode, args, progressCallback);
        this.mCapture = new SemMediaCapture();
    }

    @Override // com.samsung.android.media.codec.client.ClientImpl
    public void stop() {
        this.mIgnoreError = true;
        if (this.mIsRunning) {
            Log.i(
                    Context.SEM_VIDEO_TRANSCODING_SERVICE,
                    "stop running client id(" + this.mID + NavigationBarInflaterView.KEY_CODE_END);
            this.mCapture.stopCapture();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.mCapture.reset();
            this.mCapture.release();
            try {
                if (this.mFis != null) {
                    this.mFis.close();
                    this.mFis = null;
                }
                if (this.mFos != null) {
                    this.mFos.close();
                    this.mFos = null;
                }
            } catch (IOException ie) {
                ie.printStackTrace();
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
            this.mCapture.setOnPreparedListener(
                    new SemMediaCapture
                            .OnPreparedListener() { // from class:
                                                    // com.samsung.android.media.codec.client.SemMediaCaptureClient.1
                        @Override // com.samsung.android.media.mediacapture.SemMediaCapture.OnPreparedListener
                        public void onPrepared(SemMediaCapture semMediaCapture) {
                            Log.d(
                                    Context.SEM_VIDEO_TRANSCODING_SERVICE,
                                    "onPrepared() " + SemMediaCaptureClient.this.mID);
                            SemMediaCaptureClient.this.mCapture.startCapture();
                            CaptureThread thread = SemMediaCaptureClient.this.new CaptureThread();
                            thread.start();
                        }
                    });
            this.mCapture.setOnRecordingCompletionListener(
                    new SemMediaCapture
                            .OnRecordingCompletionListener() { // from class:
                                                               // com.samsung.android.media.codec.client.SemMediaCaptureClient.2
                        @Override // com.samsung.android.media.mediacapture.SemMediaCapture.OnRecordingCompletionListener
                        public void onRecordingCompletion(SemMediaCapture semMediaCapture) {
                            Log.d(
                                    Context.SEM_VIDEO_TRANSCODING_SERVICE,
                                    "onRecordingCompletion() " + SemMediaCaptureClient.this.mID);
                            try {
                                SemMediaCaptureClient.this.mIsRunning = false;
                                SemMediaCaptureClient.this.mCapture.reset();
                                SemMediaCaptureClient.this.mCapture.release();
                                try {
                                    if (SemMediaCaptureClient.this.mFis != null) {
                                        SemMediaCaptureClient.this.mFis.close();
                                        SemMediaCaptureClient.this.mFis = null;
                                    }
                                    if (SemMediaCaptureClient.this.mFos != null) {
                                        SemMediaCaptureClient.this.mFos.close();
                                        SemMediaCaptureClient.this.mFos = null;
                                    }
                                } catch (IOException ie) {
                                    ie.printStackTrace();
                                }
                                SemMediaCaptureClient.this.mProgressCallback.onProgressChanged(100);
                                SemMediaCaptureClient.this.mProgressCallback.onCompleted();
                            } catch (RemoteException re) {
                                re.printStackTrace();
                            }
                        }
                    });
            this.mCapture.setOnErrorListener(
                    new SemMediaCapture
                            .OnErrorListener() { // from class:
                                                 // com.samsung.android.media.codec.client.SemMediaCaptureClient.3
                        @Override // com.samsung.android.media.mediacapture.SemMediaCapture.OnErrorListener
                        public boolean onError(SemMediaCapture semMediaCapture, int i, int i1) {
                            Log.d(
                                    Context.SEM_VIDEO_TRANSCODING_SERVICE,
                                    "onError() " + SemMediaCaptureClient.this.mID);
                            SemMediaCaptureClient.this.mIsRunning = false;
                            SemMediaCaptureClient.this.mCapture.reset();
                            SemMediaCaptureClient.this.mCapture.release();
                            try {
                                if (SemMediaCaptureClient.this.mFis != null) {
                                    SemMediaCaptureClient.this.mFis.close();
                                    SemMediaCaptureClient.this.mFis = null;
                                }
                                if (SemMediaCaptureClient.this.mFos != null) {
                                    SemMediaCaptureClient.this.mFos.close();
                                    SemMediaCaptureClient.this.mFos = null;
                                }
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            if (SemMediaCaptureClient.this.mIgnoreError) {
                                Log.i(
                                        Context.SEM_VIDEO_TRANSCODING_SERVICE,
                                        "Client has stopped "
                                                + SemMediaCaptureClient.this.mID
                                                + ", Ignore this error.");
                            } else {
                                try {
                                    SemMediaCaptureClient.this.mProgressCallback.onError();
                                    SemMediaCaptureClient.this.mTranscodingService.stopTask(
                                            SemMediaCaptureClient.this.mID);
                                } catch (RemoteException re) {
                                    re.printStackTrace();
                                }
                            }
                            return false;
                        }
                    });
            String inputPath = (String) this.mArgs.get(SemVideoTranscodingService.KEY_INPUT_PATH);
            this.mFis = new FileInputStream(new File(inputPath));
            this.mCapture.setDataSource(this.mFis.getFD());
            String outputPath = (String) this.mArgs.get(SemVideoTranscodingService.KEY_OUTPUT_PATH);
            this.mFos = new FileOutputStream(new File(outputPath));
            this.mCapture.setOutputFile(this.mFos.getFD());
            this.mCapture.setParameter(1006, 1);
            if (this.mMode != 200 && this.mMode != 201) {
                if (this.mMode == 202) {
                    this.mCapture.setParameter(1011, 3);
                    this.mCapture.setParameter(1012, 80);
                    ArrayList<SemVideoTranscodingService.PlaybackSpeedChange> changes =
                            (ArrayList)
                                    this.mArgs.get(
                                            SemVideoTranscodingService.KEY_PLAYBACK_SPEED_CHANGES);
                    SemVideoTranscodingService.PlaybackSpeedChange change = changes.get(0);
                    SemMediaCapture semMediaCapture = this.mCapture;
                    SemMediaCapture semMediaCapture2 = this.mCapture;
                    Objects.requireNonNull(semMediaCapture2);
                    semMediaCapture.setBoomerangConfiguration(
                            semMediaCapture2
                            .new BoomerangConfiguration(
                                    change.startMs, change.endMs, change.rate, change.repeatCount));
                    this.mCapture.prepare();
                }
                this.mCapture.prepare();
            }
            this.mCapture.setParameter(1010, 1);
            this.mCapture.setParameter(1011, 2);
            this.mCapture.setParameter(1012, 89);
            if (this.mMode == 201) {
                this.mCapture.setParameter(1013, 1);
            }
            ArrayList<SemMediaCapture.DynamicViewingConfiguration> dvConfigs = new ArrayList<>();
            ArrayList<SemVideoTranscodingService.PlaybackSpeedChange> changes2 =
                    (ArrayList)
                            this.mArgs.get(SemVideoTranscodingService.KEY_PLAYBACK_SPEED_CHANGES);
            Iterator<SemVideoTranscodingService.PlaybackSpeedChange> it = changes2.iterator();
            while (it.hasNext()) {
                SemVideoTranscodingService.PlaybackSpeedChange change2 = it.next();
                SemMediaCapture semMediaCapture3 = this.mCapture;
                Objects.requireNonNull(semMediaCapture3);
                SemMediaCapture.DynamicViewingConfiguration dvConfig =
                        semMediaCapture3
                        .new DynamicViewingConfiguration(
                                change2.startMs, change2.endMs, change2.rate);
                dvConfigs.add(dvConfig);
            }
            this.mCapture.setDynamicViewingConfigurations(dvConfigs);
            this.mCapture.prepare();
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(
                    Context.SEM_VIDEO_TRANSCODING_SERVICE,
                    "Task(" + this.mID + ") has been terminated unexpectedly");
            if (this.mIgnoreError) {
                Log.i(
                        Context.SEM_VIDEO_TRANSCODING_SERVICE,
                        "Client has stopped " + this.mID + ", Ignore this error.");
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

    private class CaptureThread extends Thread {
        private static final String THREAD_PREFIX = "capture";

        public CaptureThread() {
            setName(THREAD_PREFIX + SemMediaCaptureClient.this.mID);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            Log.i(Context.SEM_VIDEO_TRANSCODING_SERVICE, getName() + " is running");
            try {
                try {
                    try {
                        SemMediaCaptureClient.this.mIsRunning = true;
                        while (SemMediaCaptureClient.this.mIsRunning) {
                            SemMediaCaptureClient.this.mProgressCallback.onProgressChanged(
                                    (int)
                                            (SemMediaCaptureClient.this.mCapture
                                                            .getProgressForCapture()
                                                    * 100.0f));
                            sleep(100L);
                        }
                        SemMediaCaptureClient.this.mIsRunning = false;
                        SemMediaCaptureClient.this.mTranscodingService.stopTask(
                                SemMediaCaptureClient.this.mID);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.w(
                                Context.SEM_VIDEO_TRANSCODING_SERVICE,
                                "Task("
                                        + SemMediaCaptureClient.this.mID
                                        + ") has been terminated unexpectedly");
                        if (SemMediaCaptureClient.this.mIgnoreError) {
                            Log.i(
                                    Context.SEM_VIDEO_TRANSCODING_SERVICE,
                                    "Client has stopped "
                                            + SemMediaCaptureClient.this.mID
                                            + ", Ignore this error.");
                        } else {
                            try {
                                SemMediaCaptureClient.this.mProgressCallback.onError();
                            } catch (RemoteException re) {
                                re.printStackTrace();
                            }
                        }
                        SemMediaCaptureClient.this.mIsRunning = false;
                        SemMediaCaptureClient.this.mTranscodingService.stopTask(
                                SemMediaCaptureClient.this.mID);
                    }
                } catch (Throwable th) {
                    SemMediaCaptureClient.this.mIsRunning = false;
                    try {
                        SemMediaCaptureClient.this.mTranscodingService.stopTask(
                                SemMediaCaptureClient.this.mID);
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                    throw th;
                }
            } catch (RemoteException e3) {
                e3.printStackTrace();
            }
        }
    }
}
