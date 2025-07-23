package com.android.systemui.screenrecord;

import android.R;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.media.projection.MediaProjection;
import android.net.Uri;
import android.os.Handler;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.mediaprojection.MediaProjectionCaptureTarget;
import com.android.systemui.recordissue.ScreenRecordingStartTimeStore;
import com.android.systemui.settings.UserTrackerImpl;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ScreenMediaRecorder extends MediaProjection.Callback {
    public ScreenInternalAudioRecorder mAudio;
    public final ScreenRecordingAudioSource mAudioSource;
    public final MediaProjectionCaptureTarget mCaptureRegion;
    public final Context mContext;
    public final int mDisplayId;
    public final Handler mHandler;
    public Surface mInputSurface;
    public final ScreenMediaRecorderListener mListener;
    public MediaProjection mMediaProjection;
    public MediaRecorder mMediaRecorder;
    public final ScreenRecordingStartTimeStore mScreenRecordingStartTimeStore;
    public File mTempAudioFile;
    public File mTempVideoFile;
    public final int mUid;
    public VirtualDisplay mVirtualDisplay;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Closer implements Closeable {
        public final List mCloseables;

        public /* synthetic */ Closer(int i) {
            this();
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            Throwable th = null;
            for (int i = 0; i < ((ArrayList) this.mCloseables).size(); i++) {
                try {
                    ((Closeable) ((ArrayList) this.mCloseables).get(i)).close();
                } catch (Throwable th2) {
                    if (th == null) {
                        th = th2;
                    } else {
                        th2.printStackTrace();
                    }
                }
            }
            if (th != null) {
                if (th instanceof IOException) {
                    throw ((IOException) th);
                }
                if (!(th instanceof RuntimeException)) {
                    throw ((Error) th);
                }
                throw ((RuntimeException) th);
            }
        }

        public final void register(Closeable closeable) {
            ((ArrayList) this.mCloseables).add(closeable);
        }

        private Closer() {
            this.mCloseables = new ArrayList();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedRecording {
        public final Icon mThumbnailIcon;
        public final Uri mUri;

        public SavedRecording(ScreenMediaRecorder screenMediaRecorder, Uri uri, File file, Size size) {
            this.mUri = uri;
            try {
                this.mThumbnailIcon = Icon.createWithBitmap(ThumbnailUtils.createVideoThumbnail(file, size, null));
            } catch (IOException e) {
                Log.e("ScreenMediaRecorder", "Error creating thumbnail", e);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ScreenMediaRecorderListener {
    }

    public ScreenMediaRecorder(Context context, Handler handler, int i, ScreenRecordingAudioSource screenRecordingAudioSource, MediaProjectionCaptureTarget mediaProjectionCaptureTarget, int i2, ScreenMediaRecorderListener screenMediaRecorderListener, ScreenRecordingStartTimeStore screenRecordingStartTimeStore) {
        this.mContext = context;
        this.mHandler = handler;
        this.mUid = i;
        this.mCaptureRegion = mediaProjectionCaptureTarget;
        this.mListener = screenMediaRecorderListener;
        this.mAudioSource = screenRecordingAudioSource;
        this.mDisplayId = i2;
        this.mScreenRecordingStartTimeStore = screenRecordingStartTimeStore;
    }

    public final void end(final int i) {
        Closer closer = new Closer(0);
        final MediaRecorder mediaRecorder = this.mMediaRecorder;
        Objects.requireNonNull(mediaRecorder);
        final int i2 = 0;
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda0
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                int i3 = i2;
                MediaRecorder mediaRecorder2 = mediaRecorder;
                switch (i3) {
                    case 0:
                        mediaRecorder2.stop();
                        break;
                    default:
                        mediaRecorder2.release();
                        break;
                }
            }
        });
        final MediaRecorder mediaRecorder2 = this.mMediaRecorder;
        Objects.requireNonNull(mediaRecorder2);
        final int i3 = 1;
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda0
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                int i32 = i3;
                MediaRecorder mediaRecorder22 = mediaRecorder2;
                switch (i32) {
                    case 0:
                        mediaRecorder22.stop();
                        break;
                    default:
                        mediaRecorder22.release();
                        break;
                }
            }
        });
        final Surface surface = this.mInputSurface;
        Objects.requireNonNull(surface);
        final int i4 = 0;
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda2
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                int i5 = i4;
                Object obj = surface;
                switch (i5) {
                    case 0:
                        ((Surface) obj).release();
                        break;
                    case 1:
                        ((VirtualDisplay) obj).release();
                        break;
                    default:
                        ScreenMediaRecorder screenMediaRecorder = (ScreenMediaRecorder) obj;
                        ScreenRecordingAudioSource screenRecordingAudioSource = screenMediaRecorder.mAudioSource;
                        if (screenRecordingAudioSource == ScreenRecordingAudioSource.INTERNAL || screenRecordingAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
                            ScreenInternalAudioRecorder screenInternalAudioRecorder = screenMediaRecorder.mAudio;
                            screenInternalAudioRecorder.mAudioRecord.stop();
                            boolean z = screenInternalAudioRecorder.mMic;
                            if (z) {
                                screenInternalAudioRecorder.mAudioRecordMic.stop();
                            }
                            screenInternalAudioRecorder.mAudioRecord.release();
                            if (z) {
                                screenInternalAudioRecorder.mAudioRecordMic.release();
                            }
                            try {
                                screenInternalAudioRecorder.mThread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            screenInternalAudioRecorder.mCodec.stop();
                            screenInternalAudioRecorder.mCodec.release();
                            screenInternalAudioRecorder.mMuxer.stop();
                            screenInternalAudioRecorder.mMuxer.release();
                            screenInternalAudioRecorder.mThread = null;
                            screenMediaRecorder.mAudio = null;
                            break;
                        }
                        break;
                }
            }
        });
        final VirtualDisplay virtualDisplay = this.mVirtualDisplay;
        Objects.requireNonNull(virtualDisplay);
        final int i5 = 1;
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda2
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                int i52 = i5;
                Object obj = virtualDisplay;
                switch (i52) {
                    case 0:
                        ((Surface) obj).release();
                        break;
                    case 1:
                        ((VirtualDisplay) obj).release();
                        break;
                    default:
                        ScreenMediaRecorder screenMediaRecorder = (ScreenMediaRecorder) obj;
                        ScreenRecordingAudioSource screenRecordingAudioSource = screenMediaRecorder.mAudioSource;
                        if (screenRecordingAudioSource == ScreenRecordingAudioSource.INTERNAL || screenRecordingAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
                            ScreenInternalAudioRecorder screenInternalAudioRecorder = screenMediaRecorder.mAudio;
                            screenInternalAudioRecorder.mAudioRecord.stop();
                            boolean z = screenInternalAudioRecorder.mMic;
                            if (z) {
                                screenInternalAudioRecorder.mAudioRecordMic.stop();
                            }
                            screenInternalAudioRecorder.mAudioRecord.release();
                            if (z) {
                                screenInternalAudioRecorder.mAudioRecordMic.release();
                            }
                            try {
                                screenInternalAudioRecorder.mThread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            screenInternalAudioRecorder.mCodec.stop();
                            screenInternalAudioRecorder.mCodec.release();
                            screenInternalAudioRecorder.mMuxer.stop();
                            screenInternalAudioRecorder.mMuxer.release();
                            screenInternalAudioRecorder.mThread = null;
                            screenMediaRecorder.mAudio = null;
                            break;
                        }
                        break;
                }
            }
        });
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda4
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                ScreenMediaRecorder screenMediaRecorder = ScreenMediaRecorder.this;
                int i6 = i;
                if (i6 == 0) {
                    screenMediaRecorder.mMediaProjection.stop();
                } else {
                    screenMediaRecorder.mMediaProjection.stop(i6);
                }
            }
        });
        final int i6 = 2;
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda2
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                int i52 = i6;
                Object obj = this;
                switch (i52) {
                    case 0:
                        ((Surface) obj).release();
                        break;
                    case 1:
                        ((VirtualDisplay) obj).release();
                        break;
                    default:
                        ScreenMediaRecorder screenMediaRecorder = (ScreenMediaRecorder) obj;
                        ScreenRecordingAudioSource screenRecordingAudioSource = screenMediaRecorder.mAudioSource;
                        if (screenRecordingAudioSource == ScreenRecordingAudioSource.INTERNAL || screenRecordingAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
                            ScreenInternalAudioRecorder screenInternalAudioRecorder = screenMediaRecorder.mAudio;
                            screenInternalAudioRecorder.mAudioRecord.stop();
                            boolean z = screenInternalAudioRecorder.mMic;
                            if (z) {
                                screenInternalAudioRecorder.mAudioRecordMic.stop();
                            }
                            screenInternalAudioRecorder.mAudioRecord.release();
                            if (z) {
                                screenInternalAudioRecorder.mAudioRecordMic.release();
                            }
                            try {
                                screenInternalAudioRecorder.mThread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            screenInternalAudioRecorder.mCodec.stop();
                            screenInternalAudioRecorder.mCodec.release();
                            screenInternalAudioRecorder.mMuxer.stop();
                            screenInternalAudioRecorder.mMuxer.release();
                            screenInternalAudioRecorder.mThread = null;
                            screenMediaRecorder.mAudio = null;
                            break;
                        }
                        break;
                }
            }
        });
        closer.close();
        this.mMediaRecorder = null;
        this.mMediaProjection = null;
        Log.d("ScreenMediaRecorder", "end recording");
    }

    @Override // android.media.projection.MediaProjection.Callback
    public final void onStop() {
        Log.d("ScreenMediaRecorder", "The system notified about stopping the projection");
        RecordingService recordingService = (RecordingService) this.mListener;
        if (recordingService.mController.isRecording()) {
            Log.d(recordingService.getTag(), "Stopping recording because the system requested the stop");
            recordingService.stopService(-1, 0);
        }
    }

    public final SavedRecording save() {
        String format = new SimpleDateFormat("'screen-'yyyyMMdd-HHmmss'.mp4'").format(new Date());
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", format);
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("date_added", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri insert = contentResolver.insert(MediaStore.Video.Media.getContentUri("external_primary"), contentValues);
        Log.d("ScreenMediaRecorder", insert.toString());
        ScreenRecordingAudioSource screenRecordingAudioSource = this.mAudioSource;
        if (screenRecordingAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL || screenRecordingAudioSource == ScreenRecordingAudioSource.INTERNAL) {
            try {
                Log.d("ScreenMediaRecorder", "muxing recording");
                File createTempFile = File.createTempFile("temp", ".mp4", this.mContext.getCacheDir());
                new ScreenRecordingMuxer(0, createTempFile.getAbsolutePath(), this.mTempVideoFile.getAbsolutePath(), this.mTempAudioFile.getAbsolutePath()).mux();
                this.mTempVideoFile.delete();
                this.mTempVideoFile = createTempFile;
            } catch (IOException e) {
                Log.e("ScreenMediaRecorder", "muxing recording " + e.getMessage());
                e.printStackTrace();
            }
        }
        OutputStream openOutputStream = contentResolver.openOutputStream(insert, "w");
        Files.copy(this.mTempVideoFile.toPath(), openOutputStream);
        openOutputStream.close();
        File file = this.mTempAudioFile;
        if (file != null) {
            file.delete();
        }
        File file2 = this.mTempVideoFile;
        boolean isLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
        SavedRecording savedRecording = new SavedRecording(this, insert, file2, new Size(this.mContext.getResources().getDimensionPixelSize(isLowRamDeviceStatic ? R.dimen.timepicker_selector_radius : R.dimen.timepicker_selector_dot_radius), this.mContext.getResources().getDimensionPixelSize(isLowRamDeviceStatic ? R.dimen.timepicker_radial_picker_top_margin : R.dimen.timepicker_radial_picker_horizontal_margin)));
        this.mTempVideoFile.delete();
        return savedRecording;
    }

    public final void start() {
        int i;
        String str;
        boolean z;
        int[] iArr;
        Log.d("ScreenMediaRecorder", "start recording");
        IMediaProjection asInterface = IMediaProjection.Stub.asInterface(IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection")).createProjection(this.mUid, this.mContext.getPackageName(), 0, false, this.mDisplayId).asBinder());
        MediaProjectionCaptureTarget mediaProjectionCaptureTarget = this.mCaptureRegion;
        if (mediaProjectionCaptureTarget != null) {
            asInterface.setLaunchCookie(mediaProjectionCaptureTarget.launchCookie);
            asInterface.setTaskId(this.mCaptureRegion.taskId);
        }
        MediaProjection mediaProjection = new MediaProjection(this.mContext, asInterface);
        this.mMediaProjection = mediaProjection;
        mediaProjection.registerCallback(this, this.mHandler);
        File cacheDir = this.mContext.getCacheDir();
        cacheDir.mkdirs();
        this.mTempVideoFile = File.createTempFile("temp", ".mp4", cacheDir);
        MediaRecorder mediaRecorder = new MediaRecorder();
        this.mMediaRecorder = mediaRecorder;
        ScreenRecordingAudioSource screenRecordingAudioSource = this.mAudioSource;
        ScreenRecordingAudioSource screenRecordingAudioSource2 = ScreenRecordingAudioSource.MIC;
        if (screenRecordingAudioSource == screenRecordingAudioSource2) {
            mediaRecorder.setAudioSource(0);
        }
        this.mMediaRecorder.setVideoSource(2);
        this.mMediaRecorder.setOutputFormat(2);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(this.mDisplayId);
        display.getRealMetrics(displayMetrics);
        int refreshRate = (int) display.getRefreshRate();
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        MediaCodec createDecoderByType = MediaCodec.createDecoderByType("video/avc");
        MediaCodecInfo.VideoCapabilities videoCapabilities = createDecoderByType.getCodecInfo().getCapabilitiesForType("video/avc").getVideoCapabilities();
        createDecoderByType.release();
        int intValue = videoCapabilities.getSupportedWidths().getUpper().intValue();
        int intValue2 = videoCapabilities.getSupportedHeights().getUpper().intValue();
        int widthAlignment = i2 % videoCapabilities.getWidthAlignment() != 0 ? i2 - (i2 % videoCapabilities.getWidthAlignment()) : i2;
        int heightAlignment = i3 % videoCapabilities.getHeightAlignment() != 0 ? i3 - (i3 % videoCapabilities.getHeightAlignment()) : i3;
        if (intValue < widthAlignment || intValue2 < heightAlignment || !videoCapabilities.isSizeSupported(widthAlignment, heightAlignment)) {
            double d = intValue;
            i = 2;
            str = "temp";
            double d2 = i2;
            double d3 = intValue2;
            z = false;
            double d4 = i3;
            double min = Math.min(d / d2, d3 / d4);
            int i4 = (int) (d2 * min);
            int i5 = (int) (d4 * min);
            if (i4 % videoCapabilities.getWidthAlignment() != 0) {
                i4 -= i4 % videoCapabilities.getWidthAlignment();
            }
            if (i5 % videoCapabilities.getHeightAlignment() != 0) {
                i5 -= i5 % videoCapabilities.getHeightAlignment();
            }
            int intValue3 = videoCapabilities.getSupportedFrameRatesFor(i4, i5).getUpper().intValue();
            if (intValue3 >= refreshRate) {
                intValue3 = refreshRate;
            }
            Log.d("ScreenMediaRecorder", "Resized by " + min + ": " + i4 + ", " + i5 + ", " + intValue3);
            iArr = new int[]{i4, i5, intValue3};
        } else {
            int intValue4 = videoCapabilities.getSupportedFrameRatesFor(widthAlignment, heightAlignment).getUpper().intValue();
            if (intValue4 < refreshRate) {
                refreshRate = intValue4;
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m(refreshRate, "Screen size supported at rate ", "ScreenMediaRecorder");
            iArr = new int[]{widthAlignment, heightAlignment, refreshRate};
            i = 2;
            str = "temp";
            z = false;
        }
        int i6 = iArr[z ? 1 : 0];
        int i7 = iArr[1];
        int i8 = iArr[i];
        this.mMediaRecorder.setVideoEncoder(i);
        this.mMediaRecorder.setVideoEncodingProfileLevel(8, 256);
        this.mMediaRecorder.setVideoSize(i6, i7);
        this.mMediaRecorder.setVideoFrameRate(i8);
        this.mMediaRecorder.setVideoEncodingBitRate((((i6 * i7) * i8) / 30) * 6);
        this.mMediaRecorder.setMaxDuration(3600000);
        this.mMediaRecorder.setMaxFileSize(5000000000L);
        if (this.mAudioSource == screenRecordingAudioSource2) {
            this.mMediaRecorder.setAudioEncoder(4);
            this.mMediaRecorder.setAudioChannels(1);
            this.mMediaRecorder.setAudioEncodingBitRate(196000);
            this.mMediaRecorder.setAudioSamplingRate(44100);
        }
        this.mMediaRecorder.setOutputFile(this.mTempVideoFile);
        this.mMediaRecorder.prepare();
        Surface surface = this.mMediaRecorder.getSurface();
        this.mInputSurface = surface;
        this.mVirtualDisplay = this.mMediaProjection.createVirtualDisplay("Recording Display", i6, i7, displayMetrics.densityDpi, 16, surface, new VirtualDisplay.Callback() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder.1
            @Override // android.hardware.display.VirtualDisplay.Callback
            public final void onStopped() {
                ScreenMediaRecorder.this.onStop();
            }
        }, this.mHandler);
        this.mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda6
            @Override // android.media.MediaRecorder.OnInfoListener
            public final void onInfo(MediaRecorder mediaRecorder2, int i9, int i10) {
                RecordingService recordingService = (RecordingService) ScreenMediaRecorder.this.mListener;
                Log.d(recordingService.getTag(), "Media recorder info: " + i9);
                Intent putExtra = new Intent(recordingService, (Class<?>) RecordingService.class).setAction("com.android.systemui.screenrecord.STOP").putExtra("android.intent.extra.user_handle", recordingService.getUserId());
                putExtra.putExtra("extra_stopReason", 10);
                recordingService.onStartCommand(putExtra, 0, 0);
            }
        });
        ScreenRecordingAudioSource screenRecordingAudioSource3 = this.mAudioSource;
        ScreenRecordingAudioSource screenRecordingAudioSource4 = ScreenRecordingAudioSource.INTERNAL;
        if (screenRecordingAudioSource3 == screenRecordingAudioSource4 || screenRecordingAudioSource3 == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
            File createTempFile = File.createTempFile(str, ".aac", this.mContext.getCacheDir());
            this.mTempAudioFile = createTempFile;
            String absolutePath = createTempFile.getAbsolutePath();
            MediaProjection mediaProjection2 = this.mMediaProjection;
            if (this.mAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
                z = true;
            }
            this.mAudio = new ScreenInternalAudioRecorder(absolutePath, mediaProjection2, z);
        }
        this.mMediaRecorder.start();
        ScreenRecordingStartTimeStore screenRecordingStartTimeStore = this.mScreenRecordingStartTimeStore;
        screenRecordingStartTimeStore.getClass();
        screenRecordingStartTimeStore.userIdToScreenRecordingStartTime.put(((UserTrackerImpl) screenRecordingStartTimeStore.userTracker).getUserId(), new JSONObject().put("elapsedRealTimeNanos", SystemClock.elapsedRealtimeNanos()).put("realToElapsedTimeOffsetNanos", TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) - SystemClock.elapsedRealtimeNanos()));
        ScreenRecordingAudioSource screenRecordingAudioSource5 = this.mAudioSource;
        if (screenRecordingAudioSource5 == screenRecordingAudioSource4 || screenRecordingAudioSource5 == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
            ScreenInternalAudioRecorder screenInternalAudioRecorder = this.mAudio;
            synchronized (screenInternalAudioRecorder) {
                try {
                    if (screenInternalAudioRecorder.mStarted) {
                        if (screenInternalAudioRecorder.mThread != null) {
                            throw new IllegalStateException("Recording already started");
                        }
                        throw new IllegalStateException("Recording stopped and can't restart (single use)");
                    }
                    screenInternalAudioRecorder.mStarted = true;
                    screenInternalAudioRecorder.mAudioRecord.startRecording();
                    if (screenInternalAudioRecorder.mMic) {
                        screenInternalAudioRecorder.mAudioRecordMic.startRecording();
                    }
                    Log.d("ScreenAudioRecorder", "channel count " + screenInternalAudioRecorder.mAudioRecord.getChannelCount());
                    screenInternalAudioRecorder.mCodec.start();
                    if (screenInternalAudioRecorder.mAudioRecord.getRecordingState() != 3) {
                        throw new IllegalStateException("Audio recording failed to start");
                    }
                    screenInternalAudioRecorder.mThread.start();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
