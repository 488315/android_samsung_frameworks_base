package android.filterpacks.videosink;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: classes.dex */
public class MediaEncoderFilter extends Filter {
    private static final int NO_AUDIO_SOURCE = -1;
    private static final String TAG = "MediaEncoderFilter";

    @GenerateFieldPort(hasDefault = true, name = "audioSource")
    private int mAudioSource;
    private boolean mCaptureTimeLapse;

    @GenerateFieldPort(hasDefault = true, name = "errorListener")
    private MediaRecorder.OnErrorListener mErrorListener;

    @GenerateFieldPort(hasDefault = true, name = "outputFileDescriptor")
    private FileDescriptor mFd;

    @GenerateFieldPort(hasDefault = true, name = "framerate")
    private int mFps;

    @GenerateFieldPort(hasDefault = true, name = "height")
    private int mHeight;

    @GenerateFieldPort(hasDefault = true, name = "infoListener")
    private MediaRecorder.OnInfoListener mInfoListener;
    private long mLastTimeLapseFrameRealTimestampNs;
    private boolean mLogVerbose;

    @GenerateFieldPort(hasDefault = true, name = "maxDurationMs")
    private int mMaxDurationMs;

    @GenerateFieldPort(hasDefault = true, name = "maxFileSize")
    private long mMaxFileSize;
    private MediaRecorder mMediaRecorder;
    private int mNumFramesEncoded;

    @GenerateFieldPort(hasDefault = true, name = "orientationHint")
    private int mOrientationHint;

    @GenerateFieldPort(hasDefault = true, name = "outputFile")
    private String mOutputFile;

    @GenerateFieldPort(hasDefault = true, name = "outputFormat")
    private int mOutputFormat;

    @GenerateFieldPort(hasDefault = true, name = "recordingProfile")
    private CamcorderProfile mProfile;
    private ShaderProgram mProgram;

    @GenerateFieldPort(hasDefault = true, name = "recording")
    private boolean mRecording;
    private boolean mRecordingActive;

    @GenerateFieldPort(hasDefault = true, name = "recordingDoneListener")
    private OnRecordingDoneListener mRecordingDoneListener;
    private GLFrame mScreen;

    @GenerateFieldPort(hasDefault = true, name = "inputRegion")
    private Quad mSourceRegion;
    private int mSurfaceId;

    @GenerateFieldPort(hasDefault = true, name = "timelapseRecordingIntervalUs")
    private long mTimeBetweenTimeLapseFrameCaptureUs;
    private long mTimestampNs;

    @GenerateFieldPort(hasDefault = true, name = "videoEncoder")
    private int mVideoEncoder;

    @GenerateFieldPort(hasDefault = true, name = "width")
    private int mWidth;

    public interface OnRecordingDoneListener {
        void onRecordingDone();
    }

    public MediaEncoderFilter(String str) {
        super(str);
        this.mRecording = true;
        this.mOutputFile = new String("/sdcard/MediaEncoderOut.mp4");
        this.mFd = null;
        this.mAudioSource = -1;
        this.mInfoListener = null;
        this.mErrorListener = null;
        this.mRecordingDoneListener = null;
        this.mOrientationHint = 0;
        this.mProfile = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mFps = 30;
        this.mOutputFormat = 2;
        this.mVideoEncoder = 2;
        this.mMaxFileSize = 0L;
        this.mMaxDurationMs = 0;
        this.mTimeBetweenTimeLapseFrameCaptureUs = 0L;
        this.mRecordingActive = false;
        this.mTimestampNs = 0L;
        this.mLastTimeLapseFrameRealTimestampNs = 0L;
        this.mNumFramesEncoded = 0;
        this.mCaptureTimeLapse = false;
        this.mSourceRegion = new Quad(new Point(0.0f, 0.0f), new Point(1.0f, 0.0f), new Point(0.0f, 1.0f), new Point(1.0f, 1.0f));
        this.mLogVerbose = Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("videoframe", ImageFormat.create(3, 3));
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(String str, FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Port " + str + " has been updated");
        }
        if (str.equals("recording")) {
            return;
        }
        if (str.equals("inputRegion")) {
            if (isOpen()) {
                updateSourceRegion();
            }
        } else if (isOpen() && this.mRecordingActive) {
            throw new RuntimeException("Cannot change recording parameters when the filter is recording!");
        }
    }

    private void updateSourceRegion() {
        Quad quad = new Quad();
        quad.p0 = this.mSourceRegion.p2;
        quad.p1 = this.mSourceRegion.p3;
        quad.p2 = this.mSourceRegion.p0;
        quad.p3 = this.mSourceRegion.p1;
        this.mProgram.setSourceRegion(quad);
    }

    private void updateMediaRecorderParams() throws IllegalStateException, IllegalArgumentException {
        int i;
        int i2;
        this.mCaptureTimeLapse = this.mTimeBetweenTimeLapseFrameCaptureUs > 0;
        this.mMediaRecorder.setVideoSource(2);
        if (!this.mCaptureTimeLapse && (i2 = this.mAudioSource) != -1) {
            this.mMediaRecorder.setAudioSource(i2);
        }
        CamcorderProfile camcorderProfile = this.mProfile;
        if (camcorderProfile != null) {
            this.mMediaRecorder.setProfile(camcorderProfile);
            this.mFps = this.mProfile.videoFrameRate;
            int i3 = this.mWidth;
            if (i3 > 0 && (i = this.mHeight) > 0) {
                this.mMediaRecorder.setVideoSize(i3, i);
            }
        } else {
            this.mMediaRecorder.setOutputFormat(this.mOutputFormat);
            this.mMediaRecorder.setVideoEncoder(this.mVideoEncoder);
            this.mMediaRecorder.setVideoSize(this.mWidth, this.mHeight);
            this.mMediaRecorder.setVideoFrameRate(this.mFps);
        }
        this.mMediaRecorder.setOrientationHint(this.mOrientationHint);
        this.mMediaRecorder.setOnInfoListener(this.mInfoListener);
        this.mMediaRecorder.setOnErrorListener(this.mErrorListener);
        FileDescriptor fileDescriptor = this.mFd;
        if (fileDescriptor != null) {
            this.mMediaRecorder.setOutputFile(fileDescriptor);
        } else {
            this.mMediaRecorder.setOutputFile(this.mOutputFile);
        }
        try {
            this.mMediaRecorder.setMaxFileSize(this.mMaxFileSize);
        } catch (Exception e) {
            Log.w(TAG, "Setting maxFileSize on MediaRecorder unsuccessful! " + e.getMessage());
        }
        this.mMediaRecorder.setMaxDuration(this.mMaxDurationMs);
    }

    @Override // android.filterfw.core.Filter
    public void prepare(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Preparing");
        }
        this.mProgram = ShaderProgram.createIdentity(filterContext);
        this.mRecordingActive = false;
    }

    @Override // android.filterfw.core.Filter
    public void open(FilterContext filterContext) throws IllegalStateException, IllegalArgumentException {
        if (this.mLogVerbose) {
            Log.v(TAG, "Opening");
        }
        updateSourceRegion();
        if (this.mRecording) {
            startRecording(filterContext);
        }
    }

    private void startRecording(FilterContext filterContext) throws IllegalStateException, IllegalArgumentException {
        int i;
        if (this.mLogVerbose) {
            Log.v(TAG, "Starting recording");
        }
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(2, 3);
        mutableFrameFormat.setBytesPerSample(4);
        int i2 = this.mWidth;
        boolean z = i2 > 0 && this.mHeight > 0;
        CamcorderProfile camcorderProfile = this.mProfile;
        if (camcorderProfile != null && !z) {
            i2 = camcorderProfile.videoFrameWidth;
            i = this.mProfile.videoFrameHeight;
        } else {
            i = this.mHeight;
        }
        mutableFrameFormat.setDimensions(i2, i);
        this.mScreen = (GLFrame) filterContext.getFrameManager().newBoundFrame(mutableFrameFormat, 101, 0L);
        this.mMediaRecorder = new MediaRecorder();
        updateMediaRecorderParams();
        try {
            this.mMediaRecorder.prepare();
            this.mMediaRecorder.start();
            if (this.mLogVerbose) {
                Log.v(TAG, "Open: registering surface from Mediarecorder");
            }
            this.mSurfaceId = filterContext.getGLEnvironment().registerSurfaceFromMediaRecorder(this.mMediaRecorder);
            this.mNumFramesEncoded = 0;
            this.mRecordingActive = true;
        } catch (IOException e) {
            throw new RuntimeException("IOException inMediaRecorder.prepare()!", e);
        } catch (IllegalStateException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new RuntimeException("Unknown Exception inMediaRecorder.prepare()!", e3);
        }
    }

    public boolean skipFrameAndModifyTimestamp(long j) {
        int i = this.mNumFramesEncoded;
        if (i == 0) {
            this.mLastTimeLapseFrameRealTimestampNs = j;
            this.mTimestampNs = j;
            if (this.mLogVerbose) {
                Log.v(TAG, "timelapse: FIRST frame, last real t= " + this.mLastTimeLapseFrameRealTimestampNs + ", setting t = " + this.mTimestampNs);
            }
            return false;
        }
        if (i >= 2 && j < this.mLastTimeLapseFrameRealTimestampNs + (this.mTimeBetweenTimeLapseFrameCaptureUs * 1000)) {
            if (!this.mLogVerbose) {
                return true;
            }
            Log.v(TAG, "timelapse: skipping intermediate frame");
            return true;
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "timelapse: encoding frame, Timestamp t = " + j + ", last real t= " + this.mLastTimeLapseFrameRealTimestampNs + ", interval = " + this.mTimeBetweenTimeLapseFrameCaptureUs);
        }
        this.mLastTimeLapseFrameRealTimestampNs = j;
        this.mTimestampNs += 1000000000 / this.mFps;
        if (this.mLogVerbose) {
            Log.v(TAG, "timelapse: encoding frame, setting t = " + this.mTimestampNs + ", delta t = " + (1000000000 / this.mFps) + ", fps = " + this.mFps);
        }
        return false;
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) throws IllegalStateException, IllegalArgumentException {
        GLEnvironment gLEnvironment = filterContext.getGLEnvironment();
        Frame framePullInput = pullInput("videoframe");
        if (!this.mRecordingActive && this.mRecording) {
            startRecording(filterContext);
        }
        if (this.mRecordingActive && !this.mRecording) {
            stopRecording(filterContext);
        }
        if (this.mRecordingActive) {
            if (this.mCaptureTimeLapse) {
                if (skipFrameAndModifyTimestamp(framePullInput.getTimestamp())) {
                    return;
                }
            } else {
                this.mTimestampNs = framePullInput.getTimestamp();
            }
            gLEnvironment.activateSurfaceWithId(this.mSurfaceId);
            this.mProgram.process(framePullInput, this.mScreen);
            gLEnvironment.setSurfaceTimestamp(this.mTimestampNs);
            gLEnvironment.swapBuffers();
            this.mNumFramesEncoded++;
        }
    }

    private void stopRecording(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Stopping recording");
        }
        this.mRecordingActive = false;
        this.mNumFramesEncoded = 0;
        GLEnvironment gLEnvironment = filterContext.getGLEnvironment();
        if (this.mLogVerbose) {
            Log.v(TAG, String.format("Unregistering surface %d", Integer.valueOf(this.mSurfaceId)));
        }
        gLEnvironment.unregisterSurfaceId(this.mSurfaceId);
        try {
            this.mMediaRecorder.stop();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
            this.mScreen.release();
            this.mScreen = null;
            OnRecordingDoneListener onRecordingDoneListener = this.mRecordingDoneListener;
            if (onRecordingDoneListener != null) {
                onRecordingDoneListener.onRecordingDone();
            }
        } catch (RuntimeException e) {
            throw new MediaRecorderStopException("MediaRecorder.stop() failed!", e);
        }
    }

    @Override // android.filterfw.core.Filter
    public void close(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Closing");
        }
        if (this.mRecordingActive) {
            stopRecording(filterContext);
        }
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(FilterContext filterContext) {
        MediaRecorder mediaRecorder = this.mMediaRecorder;
        if (mediaRecorder != null) {
            mediaRecorder.release();
        }
        GLFrame gLFrame = this.mScreen;
        if (gLFrame != null) {
            gLFrame.release();
        }
    }
}
