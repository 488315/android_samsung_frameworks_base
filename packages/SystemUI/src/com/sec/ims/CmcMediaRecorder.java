package com.sec.ims;

import android.os.RemoteException;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.nexus.video.BuildConfig;
import com.sec.ims.cmc.CmcRecordingInfo;
import com.sec.ims.cmc.ICmcRecordingListener;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CmcMediaRecorder {
    public static final int CMC_RECORDER_ERROR_UNKNOWN = 1;
    public static final int CMC_RECORDER_INFO_DURATION_IN_PROGRESS = 901;
    public static final int CMC_RECORDER_INFO_FILESIZE_IN_PROGRESS = 900;
    public static final int CMC_RECORDER_INFO_MAX_DURATION_REACHED = 800;
    public static final int CMC_RECORDER_INFO_MAX_FILESIZE_REACHED = 801;
    public static final int CMC_RECORDER_SUCCESS = 0;
    private static final int EVENT_PAUSE = 4;
    private static final int EVENT_PREPARE = 1;
    private static final int EVENT_RELEASE = 7;
    private static final int EVENT_RESET = 6;
    private static final int EVENT_RESUME = 5;
    private static final int EVENT_START = 2;
    private static final int EVENT_STOP = 3;
    private static final String LOG_TAG = "CmcMediaRecorder";
    private static final int STATE_DATASOURCE_CONFIGURED = 3;
    private static final int STATE_ERROR = 7;
    private static final int STATE_INITIAL = 1;
    private static final int STATE_INITIALIZED = 2;
    private static final int STATE_PREPARED = 4;
    private static final int STATE_RECORDING = 5;
    private static final int STATE_RELEASED = 6;
    private static final int STATE_UNKNOWN = 8;
    private CmcRecordingInfo mCmcRecordingInfo = new CmcRecordingInfo();
    IImsService mImsService;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private int mPhoneId;
    private int mState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnErrorListener {
        void onError(CmcMediaRecorder cmcMediaRecorder, int i, int i2);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnInfoListener {
        void onInfo(CmcMediaRecorder cmcMediaRecorder, int i, int i2);
    }

    private CmcMediaRecorder() {
    }

    public CmcRecordingInfo getCmcRecordingInfo() {
        return this.mCmcRecordingInfo;
    }

    public void pause() throws IllegalStateException {
        Log.d(LOG_TAG, "pause");
        if (this.mState != 5) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        try {
            this.mImsService.sendCmcRecordingEvent(this.mPhoneId, 4, this.mCmcRecordingInfo);
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void prepare() throws IllegalStateException {
        Log.d(LOG_TAG, "prepare");
        if (this.mState != 3) {
            throw new IllegalStateException("Current stats is " + this.mState);
        }
        try {
            this.mImsService.sendCmcRecordingEvent(this.mPhoneId, 1, this.mCmcRecordingInfo);
            this.mState = 4;
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void release() throws IllegalStateException {
        Log.d(LOG_TAG, BuildConfig.BUILD_TYPE);
        if (this.mState != 1) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        try {
            this.mImsService.sendCmcRecordingEvent(this.mPhoneId, 7, this.mCmcRecordingInfo);
            this.mState = 6;
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void reset() throws IllegalStateException {
        Log.d(LOG_TAG, UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
        if (this.mState == 6) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        try {
            this.mImsService.sendCmcRecordingEvent(this.mPhoneId, 6, this.mCmcRecordingInfo);
            this.mState = 1;
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void resume() throws IllegalStateException {
        Log.d(LOG_TAG, "resume");
        if (this.mState != 5) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        try {
            this.mImsService.sendCmcRecordingEvent(this.mPhoneId, 5, this.mCmcRecordingInfo);
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void setAudioChannels(int i) throws IllegalStateException, IllegalArgumentException {
        Log.d(LOG_TAG, "setAudioChannels");
        if (this.mState != 3) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        if (i <= 0) {
            throw new IllegalArgumentException("Number of channels is not positive");
        }
        this.mCmcRecordingInfo.setAudioChannels(i);
    }

    public void setAudioEncoder(int i) throws IllegalStateException {
        Log.d(LOG_TAG, "setAudioEncoder");
        if (this.mState == 3) {
            this.mCmcRecordingInfo.setAudioEncoder(i);
        } else {
            throw new IllegalStateException("Current state is " + this.mState);
        }
    }

    public void setAudioEncodingBitRate(int i) throws IllegalStateException, IllegalArgumentException {
        Log.d(LOG_TAG, "setAudioEncodingBitRate");
        if (this.mState != 3) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        if (i <= 0) {
            throw new IllegalArgumentException("Audio encoding bit rate is not positive");
        }
        this.mCmcRecordingInfo.setAudioEncodingBitRate(i);
    }

    public void setAudioSamplingRate(int i) throws IllegalStateException, IllegalArgumentException {
        Log.d(LOG_TAG, "setAudioSamplingRate");
        if (this.mState != 3) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        if (i <= 0) {
            throw new IllegalArgumentException("Audio sampling rate is not positive");
        }
        this.mCmcRecordingInfo.setAudioSamplingRate(i);
    }

    public void setAudioSource(int i) throws IllegalStateException {
        Log.d(LOG_TAG, "setAudioSource");
        int i2 = this.mState;
        if (i2 == 1 || i2 == 2) {
            this.mCmcRecordingInfo.setAudioSource(i);
            this.mState = 2;
        } else {
            throw new IllegalStateException("Current state is " + this.mState);
        }
    }

    public void setAuthor(int i) throws IllegalStateException {
        if (this.mState != 3) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        this.mCmcRecordingInfo.setAuthor("param-meta-author=" + i);
    }

    public void setDurationInterval(int i) throws IllegalStateException, IllegalArgumentException {
        Log.d(LOG_TAG, "setDurationInterval");
        if (this.mState != 3) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        if (i <= 0) {
            throw new IllegalArgumentException("Duration interval is not positive");
        }
        this.mCmcRecordingInfo.setDurationInterval(i);
    }

    public void setFileSizeInterval(long j) throws IllegalStateException, IllegalArgumentException {
        Log.d(LOG_TAG, "setFileSizeInterval");
        if (this.mState != 3) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        if (j <= 0) {
            throw new IllegalArgumentException("File size interval is not positive");
        }
        this.mCmcRecordingInfo.setFileSizeInterval(j);
    }

    public void setMaxDuration(int i) throws IllegalStateException, IllegalArgumentException {
        Log.d(LOG_TAG, "setMaxDuration");
        if (this.mState != 3) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        if (i <= 0) {
            throw new IllegalArgumentException("Max duration is not positive");
        }
        this.mCmcRecordingInfo.setMaxDuration(i);
    }

    public void setMaxFileSize(long j) throws IllegalStateException, IllegalArgumentException {
        Log.d(LOG_TAG, "setMaxFileSize");
        if (this.mState != 3) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        if (j <= 0) {
            throw new IllegalArgumentException("Max file size is not positive");
        }
        this.mCmcRecordingInfo.setMaxFileSize(j);
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        Log.d(LOG_TAG, "setOnErrorListener");
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        Log.d(LOG_TAG, "setOnInfoListener");
        this.mOnInfoListener = onInfoListener;
    }

    public void setOutputFormat(int i) throws IllegalStateException {
        Log.d(LOG_TAG, "setOutputFormat");
        if (this.mState == 2) {
            this.mCmcRecordingInfo.setOutputFormat(i);
            this.mState = 3;
        } else {
            throw new IllegalStateException("Current state is " + this.mState);
        }
    }

    public void setOutputPath(String str) throws IllegalStateException, IllegalArgumentException {
        Log.d(LOG_TAG, "setOutputPath");
        if (this.mState != 3) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Output absolute path is empty");
        }
        this.mCmcRecordingInfo.setOutputPath(str);
    }

    public void start() throws IllegalStateException {
        Log.d(LOG_TAG, NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
        if (this.mState != 4) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        try {
            this.mImsService.sendCmcRecordingEvent(this.mPhoneId, 2, this.mCmcRecordingInfo);
            this.mState = 5;
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void stop() throws IllegalStateException {
        Log.d(LOG_TAG, "stop");
        if (this.mState != 5) {
            throw new IllegalStateException("Current state is " + this.mState);
        }
        try {
            this.mImsService.sendCmcRecordingEvent(this.mPhoneId, 3, this.mCmcRecordingInfo);
            this.mState = 1;
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public CmcMediaRecorder(IImsService iImsService, int i) {
        this.mImsService = iImsService;
        this.mPhoneId = i;
        try {
            iImsService.registerCmcRecordingListener(i, new ICmcRecordingListener.Stub() { // from class: com.sec.ims.CmcMediaRecorder.1
                @Override // com.sec.ims.cmc.ICmcRecordingListener
                public void onError(int i2, int i3) {
                    SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i3, "ICmcRecordingListener onError : ", " ", CmcMediaRecorder.LOG_TAG);
                    CmcMediaRecorder.this.mState = 7;
                    if (CmcMediaRecorder.this.mOnErrorListener != null) {
                        CmcMediaRecorder.this.mOnErrorListener.onError(CmcMediaRecorder.this, i2, i3);
                    }
                }

                @Override // com.sec.ims.cmc.ICmcRecordingListener
                public void onInfo(int i2, int i3) {
                    SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i3, "ICmcRecordingListener onInfo : ", " ", CmcMediaRecorder.LOG_TAG);
                    if (CmcMediaRecorder.this.mOnInfoListener != null) {
                        CmcMediaRecorder.this.mOnInfoListener.onInfo(CmcMediaRecorder.this, i2, i3);
                    }
                }
            });
            this.mState = 1;
        } catch (RemoteException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }
}
