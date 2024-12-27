package com.samsung.android.media.mediacapture;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaHTTPService;
import android.net.Uri;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.samsung.android.media.SemBackgroundMusic;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class SemMediaCapture {
    public static final int AUDIO_CAPTURE_OFF = 0;
    public static final int AUDIO_CAPTURE_ON = 1;
    public static final int AUDIO_VOLUME_FADE_IN = 1;
    public static final int AUDIO_VOLUME_FADE_INOUT = 3;
    public static final int AUDIO_VOLUME_FADE_NONE = 0;
    public static final int AUDIO_VOLUME_FADE_OUT = 2;
    public static final int CUSTOM_EFFECT_TYPE_BOOMERANG = 3;
    public static final int CUSTOM_EFFECT_TYPE_COLOR_GRADING = 4;
    public static final int CUSTOM_EFFECT_TYPE_DYNAMIC_VIEWING = 2;
    public static final int CUSTOM_EFFECT_TYPE_NONE = 0;
    public static final int CUSTOM_EFFECT_TYPE_VIDEO_SUPER_RESOLUTION = 1;
    public static final int DIRECTION_FORWARD = 0;
    public static final int DIRECTION_FORWARD_REVERSE = 2;
    public static final int DIRECTION_REVERSE = 1;
    public static final int DIRECTION_SUPER_SLOW_FORWARD = 3;
    public static final int DIRECTION_SUPER_SLOW_REVERSE = 4;
    public static final int DIRECTION_SUPER_SLOW_SWING = 5;
    public static final int HDR_TO_SDR_CONVERSION_OFF = 0;
    public static final int HDR_TO_SDR_CONVERSION_ON = 1;
    private static final String IMEDIA_CAPTURE = "android.media.IMediaCapture";
    public static final int INSTANT_SLOW_MO = 89;
    private static final int INSTANT_SLOW_MO_HDR10_PLUS = 88;
    private static final int INSTANT_SLOW_MO_LOG_VIDEO = 87;
    private static final int INVOKE_ID_GET_CAPTURE_PROGRESS = 2;
    private static final int INVOKE_ID_SET_BOOMERANG_INFO = 3;
    private static final int INVOKE_ID_SET_DYNAMIC_VIEWING_INFO = 1;
    public static final int KEY_PARAMETER_AUDIO_CAPTURE = 1010;
    public static final int KEY_PARAMETER_CUSTOM_EFFECT_TYPE = 1011;
    public static final int KEY_PARAMETER_DECODING_UPDATED_INTERVAL = 1008;
    public static final int KEY_PARAMETER_DIRECTION = 1003;
    public static final int KEY_PARAMETER_ENABLE_ALL_SUPER_SLOW_REGION = 1009;
    public static final int KEY_PARAMETER_FORMAT = 1006;
    public static final int KEY_PARAMETER_FRAMERATE = 1001;
    public static final int KEY_PARAMETER_HDR_TO_SDR_CONVERSION = 1013;
    public static final int KEY_PARAMETER_HEIGHT = 1005;
    public static final int KEY_PARAMETER_LOOP = 1002;
    public static final int KEY_PARAMETER_PLAYBACK_RATE = 1007;
    public static final int KEY_PARAMETER_RECORDING_MODE = 1012;
    public static final int KEY_PARAMETER_WIDTH = 1004;
    public static final int LOOP_OFF = 0;
    public static final int LOOP_ON = 1;
    private static final int MEDIA_CAPTURE_DECODING_COMPLETE = 5;
    private static final int MEDIA_CAPTURE_DECODING_UPDATE = 10;
    private static final int MEDIA_CAPTURE_ERROR = 100;
    private static final int MEDIA_CAPTURE_INFO = 200;
    private static final int MEDIA_CAPTURE_NOP = 0;
    private static final int MEDIA_CAPTURE_PAUSED = 4;
    private static final int MEDIA_CAPTURE_PLAYBACK_COMPLETE = 6;
    private static final int MEDIA_CAPTURE_PREPARE_COMPLETE = 1;
    private static final int MEDIA_CAPTURE_RECORDING_COMPLETE = 7;
    private static final int MEDIA_CAPTURE_RENDERING_STARTED = 8;
    private static final int MEDIA_CAPTURE_SEEK_COMPLETE = 9;
    private static final int MEDIA_CAPTURE_STARTED = 2;
    private static final int MEDIA_CAPTURE_STOPPED = 3;
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    public static final int MEDIA_FORMAT_GIF = 0;
    public static final int MEDIA_FORMAT_MP4 = 1;
    public static final int MOTION_PHOTO_BOOMERANG = 80;
    public static final int MOTION_PHOTO_SLOWMO = 81;
    public static final int NORMAL = 0;
    public static final int PIP = 1;
    public static final int SUGGESTED_EDITS_BOOMERANG = 99;
    public static final int SUGGESTED_EDITS_CLIP = 96;
    public static final int SUGGESTED_EDITS_HIGHLIGHT = 97;
    public static final int SUGGESTED_EDITS_HIGHLIGHTS = 95;
    public static final int SUGGESTED_EDITS_SLOW_MOTION = 98;
    public static final int SUGGESTED_EDITS_SPEED_MIX = 93;
    public static final int SUGGESTED_EDITS_SPEED_RUN = 94;
    public static final int SUGGESTED_EDITS_SUPER_SLOW_MOTION_FORWARD = 90;
    public static final int SUGGESTED_EDITS_SUPER_SLOW_MOTION_REVERSE = 91;
    public static final int SUGGESTED_EDITS_SUPER_SLOW_MOTION_SWING = 92;
    private static final String TAG = "SemMediaCapture";
    private EventHandler mEventHandler;
    private long mNativeContext;
    private long mNativeSurfaceTexture;
    private OnDecodingCompletionListener mOnDecodingCompletionListener;
    private OnDecodingUpdatedListener mOnDecodingUpdatedListener;
    private OnErrorListener mOnErrorListener;
    private OnPlaybackCompletionListener mOnPlaybackCompletionListener;
    private OnPreparedListener mOnPreparedListener;
    private OnRecordingCompletionListener mOnRecordingCompletionListener;
    private OnRenderingStartedListener mOnRenderingStartedListener;
    private boolean mScreenOnWhilePlaying;
    private boolean mStayAwake;
    private SurfaceHolder mSurfaceHolder;

    public interface OnDecodingCompletionListener {
        void onDecodingCompletion(SemMediaCapture semMediaCapture);
    }

    public interface OnDecodingUpdatedListener {
        void onUpdated(SemMediaCapture semMediaCapture, int i);
    }

    public interface OnErrorListener {
        boolean onError(SemMediaCapture semMediaCapture, int i, int i2);
    }

    public interface OnPlaybackCompletionListener {
        void onPlaybackCompletion(SemMediaCapture semMediaCapture);
    }

    public interface OnPreparedListener {
        void onPrepared(SemMediaCapture semMediaCapture);
    }

    public interface OnRecordingCompletionListener {
        void onRecordingCompletion(SemMediaCapture semMediaCapture);
    }

    public interface OnRenderingStartedListener {
        void onRenderingStarted(SemMediaCapture semMediaCapture);
    }

    private native Bitmap _getCaptureFrame(int i) throws IllegalStateException;

    private native int _getCurrentPosition();

    private native void _pause() throws IllegalStateException;

    private native void _prepare() throws IOException, IllegalStateException;

    private native void _release();

    private native void _reset();

    private native void _seekTo(int i);

    private native void _setAudioVolumeFade(int i, int i2, int i3, int i4, int i5) throws IllegalStateException, IllegalArgumentException;

    private native void _setBackgroundMusic(Parcel parcel) throws IllegalStateException;

    private native void _setCaptureRange(int i, int i2);

    private native void _setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IllegalArgumentException, IllegalStateException;

    private native void _setOutputFile(FileDescriptor fileDescriptor) throws IllegalArgumentException, IllegalStateException;

    private native void _setParameter(int i, int i2);

    private native void _setStartEndTime(int i, int i2);

    private native void _setVideoSurface(Surface surface);

    private native void _start() throws IllegalStateException;

    private native void _startCapture();

    private native void _stop() throws IllegalStateException;

    private native void _stopCapture();

    private native void nativeSetDataSource(IBinder iBinder, String str, String[] strArr, String[] strArr2) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    private final native void native_finalize();

    private static final native void native_init();

    private final native int native_invoke(Parcel parcel, Parcel parcel2);

    private final native void native_setup(Object obj);

    static {
        System.loadLibrary("mediacapture_jni");
        native_init();
    }

    public SemMediaCapture() {
        Looper looper = Looper.myLooper();
        if (looper != null) {
            this.mEventHandler = new EventHandler(this, looper);
        } else {
            Looper looper2 = Looper.getMainLooper();
            if (looper2 != null) {
                this.mEventHandler = new EventHandler(this, looper2);
            } else {
                this.mEventHandler = null;
            }
        }
        native_setup(new WeakReference(this));
    }

    public void setDataSource(FileDescriptor fd) throws IOException, IllegalStateException, IllegalArgumentException {
        _setDataSource(fd, 0L, 576460752303423487L);
    }

    public void setDataSource(FileDescriptor fd, long offset, long length) throws IOException, IllegalStateException, IllegalArgumentException {
        ParcelFileDescriptor modernFd = FileUtils.convertToModernFd(fd);
        if (modernFd == null) {
            _setDataSource(fd, offset, length);
        } else {
            _setDataSource(modernFd.getFileDescriptor(), offset, length);
        }
    }

    public void setDataSource(String path) throws IOException, IllegalStateException, IllegalArgumentException {
        setDataSource(path, (Map<String, String>) null, (List<HttpCookie>) null);
    }

    private void setDataSource(String path, Map<String, String> headers, List<HttpCookie> cookies) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        String[] keys = null;
        String[] values = null;
        if (headers != null) {
            keys = new String[headers.size()];
            values = new String[headers.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                keys[i] = entry.getKey();
                values[i] = entry.getValue();
                i++;
            }
        }
        setDataSource(path, keys, values, cookies);
    }

    private boolean attemptDataSource(ContentResolver resolver, Uri uri) {
        try {
            AssetFileDescriptor afd = resolver.openAssetFileDescriptor(uri, "r");
            try {
                if (afd.getDeclaredLength() < 0) {
                    setDataSource(afd.getFileDescriptor());
                } else {
                    _setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                }
                if (afd != null) {
                    afd.close();
                    return true;
                }
                return true;
            } catch (Throwable th) {
                if (afd != null) {
                    try {
                        afd.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException | NullPointerException | SecurityException e) {
            return false;
        }
    }

    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(context, uri, (Map<String, String>) null, (List<HttpCookie>) null);
    }

    public void setDataSource(Context context, Uri uri, Map<String, String> headers, List<HttpCookie> cookies) throws IOException {
        CookieHandler cookieHandler;
        if (context == null) {
            throw new NullPointerException("context param can not be null.");
        }
        if (uri == null) {
            throw new NullPointerException("uri param can not be null.");
        }
        if (cookies != null && (cookieHandler = CookieHandler.getDefault()) != null && !(cookieHandler instanceof CookieManager)) {
            throw new IllegalArgumentException("The cookie handler has to be of CookieManager type when cookies are provided.");
        }
        ContentResolver resolver = context.getContentResolver();
        String scheme = uri.getScheme();
        if ("file".equals(scheme)) {
            setDataSource(uri.getPath());
        } else {
            if (attemptDataSource(resolver, uri)) {
                return;
            }
            setDataSource(uri.toString(), headers, cookies);
        }
    }

    static IBinder createHttpServiceBinderIfNecessary(String path, List<HttpCookie> cookies) {
        if (path.startsWith("http://") || path.startsWith("https://")) {
            return new MediaHTTPService(cookies).asBinder();
        }
        return null;
    }

    private void setDataSource(String path, String[] keys, String[] values, List<HttpCookie> cookies) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (path == null) {
            throw new IllegalArgumentException("input path is null.");
        }
        Uri uri = Uri.parse(path);
        String scheme = uri.getScheme();
        if ("file".equals(scheme)) {
            path = uri.getPath();
        } else if (scheme != null) {
            nativeSetDataSource(createHttpServiceBinderIfNecessary(path, cookies), path, keys, values);
            return;
        }
        File file = new File(path);
        if (file.exists()) {
            FileInputStream is = null;
            try {
                is = new FileInputStream(file);
                FileDescriptor fd = is.getFD();
                setDataSource(fd);
                is.close();
                return;
            } catch (Throwable th) {
                if (is != null) {
                    is.close();
                }
                throw th;
            }
        }
        throw new IOException("setDataSource failed.");
    }

    public void setOutputFile(FileDescriptor fd) throws IOException, IllegalStateException, IllegalArgumentException {
        _setOutputFile(fd);
    }

    public void setDisplay(SurfaceHolder sh) {
        Surface surface;
        this.mSurfaceHolder = sh;
        if (sh != null) {
            surface = sh.getSurface();
        } else {
            surface = null;
        }
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
    }

    public Bitmap getCaptureFrame(int msec) throws IllegalStateException {
        return _getCaptureFrame(msec);
    }

    public void setStartEndTime(int startMs, int endMs) throws IllegalStateException, IllegalArgumentException {
        _setStartEndTime(startMs, endMs);
    }

    public void setAudioVolumeFade(int mode, int fadeInStart, int fadeInDuration, int fadeOutStart, int fadeOutDuration) throws IllegalStateException, IllegalArgumentException {
        _setAudioVolumeFade(mode, fadeInStart, fadeInDuration, fadeOutStart, fadeOutDuration);
    }

    public void setParameter(int key, int value) throws IllegalStateException, IllegalArgumentException {
        _setParameter(key, value);
    }

    public void invoke(Parcel request, Parcel reply) throws IllegalStateException {
        int retcode = native_invoke(request, reply);
        reply.setDataPosition(0);
        if (retcode != 0) {
            throw new RuntimeException("failure code: " + retcode);
        }
    }

    public final class DynamicViewingConfiguration {
        private int mEndTime;
        private float mSpeedRate;
        private int mStartTime;

        public DynamicViewingConfiguration(int startTimeMs, int endTimeMs, float speedRate) {
            this.mStartTime = startTimeMs;
            this.mEndTime = endTimeMs;
            this.mSpeedRate = speedRate;
        }

        public int getStartTime() {
            return this.mStartTime;
        }

        public int getEndTime() {
            return this.mEndTime;
        }

        public float getSpeedRate() {
            return this.mSpeedRate;
        }
    }

    public void setDynamicViewingConfigurations(List<DynamicViewingConfiguration> dvConfigs) throws IllegalStateException, IllegalArgumentException {
        if (dvConfigs == null) {
            throw new NullPointerException("dvConfigs param can not be null.");
        }
        int numDvConfigs = dvConfigs.size();
        if (numDvConfigs <= 0) {
            throw new IllegalArgumentException("DynamicViewingConfiguration size : " + numDvConfigs);
        }
        Parcel request = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            request.writeInterfaceToken(IMEDIA_CAPTURE);
            request.writeInt(1);
            request.writeInt(numDvConfigs);
            for (int i = 0; i < numDvConfigs; i++) {
                DynamicViewingConfiguration dvConfig = dvConfigs.get(i);
                int startTime = dvConfig.getStartTime();
                int endTime = dvConfig.getEndTime();
                float speedRate = dvConfig.getSpeedRate();
                if (startTime < 0 || endTime < 0 || startTime == endTime || speedRate <= 0.0f) {
                    throw new IllegalArgumentException("DynamicViewingConfiguration is abnormal. dvConfig(" + i + ") = " + startTime + ":" + endTime + ":" + speedRate);
                }
                request.writeInt(startTime);
                request.writeInt(endTime);
                request.writeFloat(speedRate);
            }
            invoke(request, reply);
        } finally {
            request.recycle();
            reply.recycle();
        }
    }

    public final class BoomerangConfiguration {
        private int mEndTime;
        private int mLoopCount;
        private float mSpeedRate;
        private int mStartTime;

        public BoomerangConfiguration(int startTimeMs, int endTimeMs, float speedRate, int loopCount) {
            this.mStartTime = startTimeMs;
            this.mEndTime = endTimeMs;
            this.mSpeedRate = speedRate;
            this.mLoopCount = loopCount;
        }

        public int getStartTime() {
            return this.mStartTime;
        }

        public int getEndTime() {
            return this.mEndTime;
        }

        public float getSpeedRate() {
            return this.mSpeedRate;
        }

        public int getLoopCount() {
            return this.mLoopCount;
        }
    }

    public void setBoomerangConfiguration(BoomerangConfiguration bmConfig) throws IllegalArgumentException {
        if (bmConfig == null) {
            throw new NullPointerException("bmConfig param can not be null.");
        }
        int startTime = bmConfig.getStartTime();
        int endTime = bmConfig.getEndTime();
        float speedRate = bmConfig.getSpeedRate();
        int loopCount = bmConfig.getLoopCount();
        if (startTime < 0 || endTime < 0 || startTime == endTime || speedRate < 1.0f || loopCount < 1) {
            throw new IllegalArgumentException("BoomerangConfiguration is invalid. bmConfig = " + startTime + ":" + endTime + ":" + speedRate + ":" + loopCount);
        }
        Parcel request = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            request.writeInterfaceToken(IMEDIA_CAPTURE);
            request.writeInt(3);
            request.writeInt(startTime);
            request.writeInt(endTime);
            request.writeFloat(speedRate);
            request.writeInt(loopCount);
            invoke(request, reply);
        } finally {
            request.recycle();
            reply.recycle();
        }
    }

    public void startCapture() throws IllegalStateException {
        _startCapture();
    }

    public void stopCapture() throws IllegalStateException {
        _stopCapture();
    }

    public void prepare() throws IOException, IllegalStateException {
        _prepare();
    }

    public void startPlayback() throws IllegalStateException {
        _start();
    }

    public void pausePlayback() throws IllegalStateException {
        _pause();
    }

    public void stopPlayback() throws IllegalStateException {
        _stop();
    }

    public void setStartEndTimeForTrimming(int startTimeMs, int endTimeMs) throws IllegalStateException, IllegalArgumentException {
        _setCaptureRange(startTimeMs, endTimeMs);
    }

    public void seekForPreview(int msec) throws IllegalStateException {
        _seekTo(msec);
    }

    public int getPositionForPreview() throws IllegalStateException {
        return _getCurrentPosition();
    }

    public float getProgressForCapture() throws IllegalStateException {
        Parcel request = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            request.writeInterfaceToken(IMEDIA_CAPTURE);
            request.writeInt(2);
            invoke(request, reply);
            float progress = reply.readFloat();
            return progress;
        } finally {
            request.recycle();
            reply.recycle();
        }
    }

    public void reset() throws IllegalStateException {
        _reset();
        this.mEventHandler.removeCallbacksAndMessages(null);
    }

    public void release() throws IllegalStateException {
        this.mOnPreparedListener = null;
        this.mOnRecordingCompletionListener = null;
        this.mOnPlaybackCompletionListener = null;
        this.mOnErrorListener = null;
        _release();
    }

    protected void finalize() {
        native_finalize();
    }

    public static abstract class BackgroundMusic {
        protected ArrayList<BGMInfo> mBGMInfos = new ArrayList<>();

        public void clear() {
            this.mBGMInfos.clear();
        }

        protected Parcel writeToParcel() {
            Parcel p = Parcel.obtain();
            p.writeInterfaceToken(SemMediaCapture.IMEDIA_CAPTURE);
            p.writeInt(this.mBGMInfos.size());
            Log.i(SemMediaCapture.TAG, "BackgroundMusic size : " + this.mBGMInfos.size());
            for (int i = 0; i < this.mBGMInfos.size(); i++) {
                p.writeFileDescriptor(this.mBGMInfos.get(i).fd);
                p.writeLong(this.mBGMInfos.get(i).offset);
                p.writeLong(this.mBGMInfos.get(i).length);
                p.writeInt(this.mBGMInfos.get(i).startTimeMs);
                p.writeInt(this.mBGMInfos.get(i).endTimeMs);
                p.writeInt(this.mBGMInfos.get(i).durationMs);
            }
            return p;
        }

        protected BGMInfo addInfo(BGMInfo bgmInfo, FileDescriptor fd, int startTime, int endTime) {
            bgmInfo.fd = fd;
            bgmInfo.offset = 0L;
            bgmInfo.length = 576460752303423487L;
            bgmInfo.startTimeMs = startTime;
            bgmInfo.endTimeMs = endTime;
            bgmInfo.durationMs = endTime - startTime;
            return bgmInfo;
        }

        protected BGMInfo addInfo(BGMInfo bgmInfo, AssetFileDescriptor afd, int startTime, int endTime) {
            bgmInfo.fd = afd.getFileDescriptor();
            bgmInfo.offset = afd.getStartOffset();
            bgmInfo.length = afd.getLength();
            bgmInfo.startTimeMs = startTime;
            bgmInfo.endTimeMs = endTime;
            bgmInfo.durationMs = endTime - startTime;
            return bgmInfo;
        }

        protected class BGMInfo {
            int durationMs;
            int endTimeMs;
            FileDescriptor fd;
            long length;
            long offset;
            int startTimeMs;

            protected BGMInfo() {
            }
        }
    }

    public static class SingleBackgroundMusic extends BackgroundMusic {
        public void set(FileDescriptor fd, int startTime, int endTime) {
            BackgroundMusic.BGMInfo bgmSingle = new BackgroundMusic.BGMInfo();
            this.mBGMInfos.add(super.addInfo(bgmSingle, fd, startTime, endTime));
        }

        public void set(AssetFileDescriptor afd, int startTime, int endTime) {
            BackgroundMusic.BGMInfo bgmSingle = new BackgroundMusic.BGMInfo();
            this.mBGMInfos.add(super.addInfo(bgmSingle, afd, startTime, endTime));
        }
    }

    public static class FragmentedBackgroundMusic extends BackgroundMusic {
        private BackgroundMusic.BGMInfo mFBGMIntro = null;
        private BackgroundMusic.BGMInfo mFBGMOutro = null;
        private ArrayList<BackgroundMusic.BGMInfo> mFBGMBody = new ArrayList<>();
        private final int BGM_SECTION_TYPE_INTRO = 0;
        private final int BGM_SECTION_TYPE_BODY = 1;
        private final int BGM_SECTION_TYPE_OUTRO = 2;
        private int mBodyCount = 0;
        private int mBodyCycle = 0;
        private int mLastIndex = 0;
        private boolean mEndOutro = false;

        @Override // com.samsung.android.media.mediacapture.SemMediaCapture.BackgroundMusic
        public void clear() {
            super.clear();
            this.mFBGMIntro = null;
            this.mFBGMBody.clear();
            this.mFBGMOutro = null;
            this.mBodyCount = 0;
            this.mBodyCycle = 0;
            this.mLastIndex = 0;
            this.mEndOutro = false;
        }

        @Override // com.samsung.android.media.mediacapture.SemMediaCapture.BackgroundMusic
        public Parcel writeToParcel() {
            addSections();
            Parcel writeToParcel = super.writeToParcel();
            writeToParcel.writeInt(1);
            writeToParcel.writeInt(this.mBodyCycle);
            writeToParcel.writeInt(this.mLastIndex);
            writeToParcel.writeInt(this.mEndOutro ? 1 : 0);
            return writeToParcel;
        }

        public void setIntro(FileDescriptor fd, int startTime, int endTime) {
            if (this.mFBGMIntro == null) {
                this.mFBGMIntro = new BackgroundMusic.BGMInfo();
            }
            this.mFBGMIntro = super.addInfo(this.mFBGMIntro, fd, startTime, endTime);
        }

        public void setIntro(AssetFileDescriptor afd, int startTime, int endTime) {
            if (this.mFBGMIntro == null) {
                this.mFBGMIntro = new BackgroundMusic.BGMInfo();
            }
            this.mFBGMIntro = super.addInfo(this.mFBGMIntro, afd, startTime, endTime);
        }

        public int addBody(FileDescriptor fd, int startTime, int endTime) {
            BackgroundMusic.BGMInfo bgmInfo = new BackgroundMusic.BGMInfo();
            this.mFBGMBody.add(super.addInfo(bgmInfo, fd, startTime, endTime));
            this.mBodyCount++;
            return this.mBodyCount;
        }

        public int addBody(AssetFileDescriptor afd, int startTime, int endTime) {
            BackgroundMusic.BGMInfo bgmInfo = new BackgroundMusic.BGMInfo();
            this.mFBGMBody.add(super.addInfo(bgmInfo, afd, startTime, endTime));
            this.mBodyCount++;
            return this.mBodyCount;
        }

        public void setOutro(FileDescriptor fd, int startTime, int endTime) {
            if (this.mFBGMOutro == null) {
                this.mFBGMOutro = new BackgroundMusic.BGMInfo();
            }
            this.mFBGMOutro = super.addInfo(this.mFBGMOutro, fd, startTime, endTime);
        }

        public void setOutro(AssetFileDescriptor afd, int startTime, int endTime) {
            if (this.mFBGMOutro == null) {
                this.mFBGMOutro = new BackgroundMusic.BGMInfo();
            }
            this.mFBGMOutro = super.addInfo(this.mFBGMOutro, afd, startTime, endTime);
        }

        public void setPlaybackRule(int bodyRepeatCount, int bodyLastIndex, boolean useOutro) throws IllegalArgumentException {
            if (bodyLastIndex > this.mBodyCount) {
                String msg = "bodyLastIndex " + bodyLastIndex + "is invalid; larger than BGM_SECTION_TYPE_BODY count " + this.mBodyCount;
                throw new IllegalArgumentException(msg);
            }
            this.mBodyCycle = bodyRepeatCount;
            this.mLastIndex = bodyLastIndex;
            this.mEndOutro = useOutro;
        }

        private void addSections() {
            if (this.mBGMInfos.size() > 0) {
                this.mBGMInfos.clear();
            }
            if (this.mFBGMIntro != null) {
                this.mBGMInfos.add(this.mFBGMIntro);
            }
            for (int i = 0; i < this.mFBGMBody.size(); i++) {
                this.mBGMInfos.add(this.mFBGMBody.get(i));
            }
            if (this.mFBGMOutro != null) {
                this.mBGMInfos.add(this.mFBGMOutro);
            }
        }
    }

    public void setBackgroundMusic(BackgroundMusic semBGM) throws IllegalStateException {
        if (semBGM == null) {
            throw new NullPointerException("BackgroundMusic param can not be null.");
        }
        Parcel p = semBGM.writeToParcel();
        _setBackgroundMusic(p);
        p.recycle();
    }

    public void setBackgroundMusic(SemBackgroundMusic semBgm) throws IllegalStateException {
        if (semBgm == null) {
            throw new NullPointerException("SemBackgroundMusic param can not be null.");
        }
        Parcel p = semBgm.writeToParcel(IMEDIA_CAPTURE);
        _setBackgroundMusic(p);
        p.recycle();
    }

    private void updateSurfaceScreenOn() {
        if (this.mSurfaceHolder != null) {
            this.mSurfaceHolder.setKeepScreenOn(this.mScreenOnWhilePlaying && this.mStayAwake);
        }
    }

    private class EventHandler extends Handler {
        private SemMediaCapture mMediaCapture;

        public EventHandler(SemMediaCapture mc, Looper looper) {
            super(looper);
            this.mMediaCapture = mc;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (this.mMediaCapture.mNativeContext == 0) {
                Log.w(SemMediaCapture.TAG, "mediacapture went away with unhandled events");
            }
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    if (SemMediaCapture.this.mOnPreparedListener != null) {
                        SemMediaCapture.this.mOnPreparedListener.onPrepared(this.mMediaCapture);
                        break;
                    }
                    break;
                case 5:
                    if (SemMediaCapture.this.mOnDecodingCompletionListener != null) {
                        SemMediaCapture.this.mOnDecodingCompletionListener.onDecodingCompletion(this.mMediaCapture);
                        break;
                    }
                    break;
                case 6:
                    if (SemMediaCapture.this.mOnPlaybackCompletionListener != null) {
                        SemMediaCapture.this.mOnPlaybackCompletionListener.onPlaybackCompletion(this.mMediaCapture);
                        break;
                    }
                    break;
                case 7:
                    if (SemMediaCapture.this.mOnRecordingCompletionListener != null) {
                        SemMediaCapture.this.mOnRecordingCompletionListener.onRecordingCompletion(this.mMediaCapture);
                        break;
                    }
                    break;
                case 8:
                    if (SemMediaCapture.this.mOnRenderingStartedListener != null) {
                        SemMediaCapture.this.mOnRenderingStartedListener.onRenderingStarted(this.mMediaCapture);
                        break;
                    }
                    break;
                case 10:
                    if (SemMediaCapture.this.mOnDecodingUpdatedListener != null) {
                        SemMediaCapture.this.mOnDecodingUpdatedListener.onUpdated(this.mMediaCapture, msg.arg1);
                        break;
                    }
                    break;
                case 100:
                    Log.e(SemMediaCapture.TAG, "Error (" + msg.arg1 + "," + msg.arg2 + NavigationBarInflaterView.KEY_CODE_END);
                    if (SemMediaCapture.this.mOnErrorListener != null) {
                        SemMediaCapture.this.mOnErrorListener.onError(this.mMediaCapture, msg.arg1, msg.arg2);
                        break;
                    }
                    break;
                default:
                    Log.e(SemMediaCapture.TAG, "Unknown message type " + msg.what);
                    break;
            }
        }
    }

    private static void postEventFromNative(Object mediacapture_ref, int what, int arg1, int arg2, Object obj) {
        SemMediaCapture mc = (SemMediaCapture) ((WeakReference) mediacapture_ref).get();
        if (mc != null && mc.mEventHandler != null) {
            Message m = mc.mEventHandler.obtainMessage(what, arg1, arg2, obj);
            mc.mEventHandler.sendMessage(m);
        }
    }

    public void setOnPreparedListener(OnPreparedListener listener) {
        this.mOnPreparedListener = listener;
    }

    public void setOnRenderingStartedListener(OnRenderingStartedListener listener) {
        this.mOnRenderingStartedListener = listener;
    }

    public void setOnPlaybackCompletionListener(OnPlaybackCompletionListener listener) {
        this.mOnPlaybackCompletionListener = listener;
    }

    public void setOnRecordingCompletionListener(OnRecordingCompletionListener listener) {
        this.mOnRecordingCompletionListener = listener;
    }

    public void setOnDecodingCompletionListener(OnDecodingCompletionListener listener) {
        this.mOnDecodingCompletionListener = listener;
    }

    public void setOnErrorListener(OnErrorListener listener) {
        this.mOnErrorListener = listener;
    }

    public void setOnDecodingUpdatedListener(OnDecodingUpdatedListener listener) {
        this.mOnDecodingUpdatedListener = listener;
    }
}
