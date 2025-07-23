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
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler = new EventHandler(this, myLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        native_setup(new WeakReference(this));
    }

    public void setDataSource(FileDescriptor fileDescriptor) throws IOException, IllegalStateException, IllegalArgumentException {
        _setDataSource(fileDescriptor, 0L, 576460752303423487L);
    }

    public void setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException, IllegalStateException, IllegalArgumentException {
        ParcelFileDescriptor convertToModernFd = FileUtils.convertToModernFd(fileDescriptor);
        if (convertToModernFd == null) {
            _setDataSource(fileDescriptor, j, j2);
        } else {
            _setDataSource(convertToModernFd.getFileDescriptor(), j, j2);
        }
    }

    public void setDataSource(String str) throws IOException, IllegalStateException, IllegalArgumentException {
        setDataSource(str, (Map<String, String>) null, (List<HttpCookie>) null);
    }

    private void setDataSource(String str, Map<String, String> map, List<HttpCookie> list) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        String[] strArr;
        String[] strArr2;
        if (map != null) {
            strArr = new String[map.size()];
            strArr2 = new String[map.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                strArr[i] = entry.getKey();
                strArr2[i] = entry.getValue();
                i++;
            }
        } else {
            strArr = null;
            strArr2 = null;
        }
        setDataSource(str, strArr, strArr2, list);
    }

    private boolean attemptDataSource(ContentResolver contentResolver, Uri uri) {
        try {
            AssetFileDescriptor openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
            try {
                if (openAssetFileDescriptor.getDeclaredLength() < 0) {
                    setDataSource(openAssetFileDescriptor.getFileDescriptor());
                } else {
                    _setDataSource(openAssetFileDescriptor.getFileDescriptor(), openAssetFileDescriptor.getStartOffset(), openAssetFileDescriptor.getDeclaredLength());
                }
                if (openAssetFileDescriptor != null) {
                    openAssetFileDescriptor.close();
                }
                return true;
            } catch (Throwable th) {
                if (openAssetFileDescriptor == null) {
                    throw th;
                }
                try {
                    openAssetFileDescriptor.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        } catch (IOException | NullPointerException | SecurityException unused) {
            return false;
        }
    }

    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(context, uri, (Map<String, String>) null, (List<HttpCookie>) null);
    }

    public void setDataSource(Context context, Uri uri, Map<String, String> map, List<HttpCookie> list) throws IOException {
        CookieHandler cookieHandler;
        if (context == null) {
            throw new NullPointerException("context param can not be null.");
        }
        if (uri == null) {
            throw new NullPointerException("uri param can not be null.");
        }
        if (list != null && (cookieHandler = CookieHandler.getDefault()) != null && !(cookieHandler instanceof CookieManager)) {
            throw new IllegalArgumentException("The cookie handler has to be of CookieManager type when cookies are provided.");
        }
        ContentResolver contentResolver = context.getContentResolver();
        if ("file".equals(uri.getScheme())) {
            setDataSource(uri.getPath());
        } else {
            if (attemptDataSource(contentResolver, uri)) {
                return;
            }
            setDataSource(uri.toString(), map, list);
        }
    }

    static IBinder createHttpServiceBinderIfNecessary(String str, List<HttpCookie> list) {
        if (str.startsWith("http://") || str.startsWith("https://")) {
            return new MediaHTTPService(list).asBinder();
        }
        return null;
    }

    private void setDataSource(String str, String[] strArr, String[] strArr2, List<HttpCookie> list) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (str == null) {
            throw new IllegalArgumentException("input path is null.");
        }
        Uri parse = Uri.parse(str);
        String scheme = parse.getScheme();
        if ("file".equals(scheme)) {
            str = parse.getPath();
        } else if (scheme != null) {
            nativeSetDataSource(createHttpServiceBinderIfNecessary(str, list), str, strArr, strArr2);
            return;
        }
        File file = new File(str);
        if (file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    setDataSource(fileInputStream2.getFD());
                    fileInputStream2.close();
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } else {
            throw new IOException("setDataSource failed.");
        }
    }

    public void setOutputFile(FileDescriptor fileDescriptor) throws IOException, IllegalStateException, IllegalArgumentException {
        _setOutputFile(fileDescriptor);
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        _setVideoSurface(surfaceHolder != null ? surfaceHolder.getSurface() : null);
        updateSurfaceScreenOn();
    }

    public Bitmap getCaptureFrame(int i) throws IllegalStateException {
        return _getCaptureFrame(i);
    }

    public void setStartEndTime(int i, int i2) throws IllegalStateException, IllegalArgumentException {
        _setStartEndTime(i, i2);
    }

    public void setAudioVolumeFade(int i, int i2, int i3, int i4, int i5) throws IllegalStateException, IllegalArgumentException {
        _setAudioVolumeFade(i, i2, i3, i4, i5);
    }

    public void setParameter(int i, int i2) throws IllegalStateException, IllegalArgumentException {
        _setParameter(i, i2);
    }

    public void invoke(Parcel parcel, Parcel parcel2) throws IllegalStateException {
        int native_invoke = native_invoke(parcel, parcel2);
        parcel2.setDataPosition(0);
        if (native_invoke == 0) {
            return;
        }
        throw new RuntimeException("failure code: " + native_invoke);
    }

    public final class DynamicViewingConfiguration {
        private int mEndTime;
        private float mSpeedRate;
        private int mStartTime;

        public DynamicViewingConfiguration(SemMediaCapture semMediaCapture, int i, int i2, float f) {
            this.mStartTime = i;
            this.mEndTime = i2;
            this.mSpeedRate = f;
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

    public void setDynamicViewingConfigurations(List<DynamicViewingConfiguration> list) throws IllegalStateException, IllegalArgumentException {
        if (list == null) {
            throw new NullPointerException("dvConfigs param can not be null.");
        }
        int size = list.size();
        if (size <= 0) {
            throw new IllegalArgumentException("DynamicViewingConfiguration size : " + size);
        }
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_CAPTURE);
            obtain.writeInt(1);
            obtain.writeInt(size);
            for (int i = 0; i < size; i++) {
                DynamicViewingConfiguration dynamicViewingConfiguration = list.get(i);
                int startTime = dynamicViewingConfiguration.getStartTime();
                int endTime = dynamicViewingConfiguration.getEndTime();
                float speedRate = dynamicViewingConfiguration.getSpeedRate();
                if (startTime < 0 || endTime < 0 || startTime == endTime || speedRate <= 0.0f) {
                    throw new IllegalArgumentException("DynamicViewingConfiguration is abnormal. dvConfig(" + i + ") = " + startTime + ":" + endTime + ":" + speedRate);
                }
                obtain.writeInt(startTime);
                obtain.writeInt(endTime);
                obtain.writeFloat(speedRate);
            }
            invoke(obtain, obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    public final class BoomerangConfiguration {
        private int mEndTime;
        private int mLoopCount;
        private float mSpeedRate;
        private int mStartTime;

        public BoomerangConfiguration(SemMediaCapture semMediaCapture, int i, int i2, float f, int i3) {
            this.mStartTime = i;
            this.mEndTime = i2;
            this.mSpeedRate = f;
            this.mLoopCount = i3;
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

    public void setBoomerangConfiguration(BoomerangConfiguration boomerangConfiguration) throws IllegalArgumentException {
        if (boomerangConfiguration == null) {
            throw new NullPointerException("bmConfig param can not be null.");
        }
        int startTime = boomerangConfiguration.getStartTime();
        int endTime = boomerangConfiguration.getEndTime();
        float speedRate = boomerangConfiguration.getSpeedRate();
        int loopCount = boomerangConfiguration.getLoopCount();
        if (startTime < 0 || endTime < 0 || startTime == endTime || speedRate < 1.0f || loopCount < 1) {
            throw new IllegalArgumentException("BoomerangConfiguration is invalid. bmConfig = " + startTime + ":" + endTime + ":" + speedRate + ":" + loopCount);
        }
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_CAPTURE);
            obtain.writeInt(3);
            obtain.writeInt(startTime);
            obtain.writeInt(endTime);
            obtain.writeFloat(speedRate);
            obtain.writeInt(loopCount);
            invoke(obtain, obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
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

    public void setStartEndTimeForTrimming(int i, int i2) throws IllegalStateException, IllegalArgumentException {
        _setCaptureRange(i, i2);
    }

    public void seekForPreview(int i) throws IllegalStateException {
        _seekTo(i);
    }

    public int getPositionForPreview() throws IllegalStateException {
        return _getCurrentPosition();
    }

    public float getProgressForCapture() throws IllegalStateException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_CAPTURE);
            obtain.writeInt(2);
            invoke(obtain, obtain2);
            return obtain2.readFloat();
        } finally {
            obtain.recycle();
            obtain2.recycle();
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
            Parcel obtain = Parcel.obtain();
            obtain.writeInterfaceToken(SemMediaCapture.IMEDIA_CAPTURE);
            obtain.writeInt(this.mBGMInfos.size());
            Log.i(SemMediaCapture.TAG, "BackgroundMusic size : " + this.mBGMInfos.size());
            for (int i = 0; i < this.mBGMInfos.size(); i++) {
                obtain.writeFileDescriptor(this.mBGMInfos.get(i).fd);
                obtain.writeLong(this.mBGMInfos.get(i).offset);
                obtain.writeLong(this.mBGMInfos.get(i).length);
                obtain.writeInt(this.mBGMInfos.get(i).startTimeMs);
                obtain.writeInt(this.mBGMInfos.get(i).endTimeMs);
                obtain.writeInt(this.mBGMInfos.get(i).durationMs);
            }
            return obtain;
        }

        protected BGMInfo addInfo(BGMInfo bGMInfo, FileDescriptor fileDescriptor, int i, int i2) {
            bGMInfo.fd = fileDescriptor;
            bGMInfo.offset = 0L;
            bGMInfo.length = 576460752303423487L;
            bGMInfo.startTimeMs = i;
            bGMInfo.endTimeMs = i2;
            bGMInfo.durationMs = i2 - i;
            return bGMInfo;
        }

        protected BGMInfo addInfo(BGMInfo bGMInfo, AssetFileDescriptor assetFileDescriptor, int i, int i2) {
            bGMInfo.fd = assetFileDescriptor.getFileDescriptor();
            bGMInfo.offset = assetFileDescriptor.getStartOffset();
            bGMInfo.length = assetFileDescriptor.getLength();
            bGMInfo.startTimeMs = i;
            bGMInfo.endTimeMs = i2;
            bGMInfo.durationMs = i2 - i;
            return bGMInfo;
        }

        protected class BGMInfo {
            int durationMs;
            int endTimeMs;
            FileDescriptor fd;
            long length;
            long offset;
            int startTimeMs;

            protected BGMInfo(BackgroundMusic backgroundMusic) {
            }
        }
    }

    public static class SingleBackgroundMusic extends BackgroundMusic {
        public void set(FileDescriptor fileDescriptor, int i, int i2) {
            this.mBGMInfos.add(super.addInfo(new BackgroundMusic.BGMInfo(this), fileDescriptor, i, i2));
        }

        public void set(AssetFileDescriptor assetFileDescriptor, int i, int i2) {
            this.mBGMInfos.add(super.addInfo(new BackgroundMusic.BGMInfo(this), assetFileDescriptor, i, i2));
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

        public void setIntro(FileDescriptor fileDescriptor, int i, int i2) {
            if (this.mFBGMIntro == null) {
                this.mFBGMIntro = new BackgroundMusic.BGMInfo(this);
            }
            this.mFBGMIntro = super.addInfo(this.mFBGMIntro, fileDescriptor, i, i2);
        }

        public void setIntro(AssetFileDescriptor assetFileDescriptor, int i, int i2) {
            if (this.mFBGMIntro == null) {
                this.mFBGMIntro = new BackgroundMusic.BGMInfo(this);
            }
            this.mFBGMIntro = super.addInfo(this.mFBGMIntro, assetFileDescriptor, i, i2);
        }

        public int addBody(FileDescriptor fileDescriptor, int i, int i2) {
            this.mFBGMBody.add(super.addInfo(new BackgroundMusic.BGMInfo(this), fileDescriptor, i, i2));
            int i3 = this.mBodyCount + 1;
            this.mBodyCount = i3;
            return i3;
        }

        public int addBody(AssetFileDescriptor assetFileDescriptor, int i, int i2) {
            this.mFBGMBody.add(super.addInfo(new BackgroundMusic.BGMInfo(this), assetFileDescriptor, i, i2));
            int i3 = this.mBodyCount + 1;
            this.mBodyCount = i3;
            return i3;
        }

        public void setOutro(FileDescriptor fileDescriptor, int i, int i2) {
            if (this.mFBGMOutro == null) {
                this.mFBGMOutro = new BackgroundMusic.BGMInfo(this);
            }
            this.mFBGMOutro = super.addInfo(this.mFBGMOutro, fileDescriptor, i, i2);
        }

        public void setOutro(AssetFileDescriptor assetFileDescriptor, int i, int i2) {
            if (this.mFBGMOutro == null) {
                this.mFBGMOutro = new BackgroundMusic.BGMInfo(this);
            }
            this.mFBGMOutro = super.addInfo(this.mFBGMOutro, assetFileDescriptor, i, i2);
        }

        public void setPlaybackRule(int i, int i2, boolean z) throws IllegalArgumentException {
            if (i2 > this.mBodyCount) {
                throw new IllegalArgumentException("bodyLastIndex " + i2 + "is invalid; larger than BGM_SECTION_TYPE_BODY count " + this.mBodyCount);
            }
            this.mBodyCycle = i;
            this.mLastIndex = i2;
            this.mEndOutro = z;
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

    public void setBackgroundMusic(BackgroundMusic backgroundMusic) throws IllegalStateException {
        if (backgroundMusic == null) {
            throw new NullPointerException("BackgroundMusic param can not be null.");
        }
        Parcel writeToParcel = backgroundMusic.writeToParcel();
        _setBackgroundMusic(writeToParcel);
        writeToParcel.recycle();
    }

    public void setBackgroundMusic(SemBackgroundMusic semBackgroundMusic) throws IllegalStateException {
        if (semBackgroundMusic == null) {
            throw new NullPointerException("SemBackgroundMusic param can not be null.");
        }
        Parcel writeToParcel = semBackgroundMusic.writeToParcel(IMEDIA_CAPTURE);
        _setBackgroundMusic(writeToParcel);
        writeToParcel.recycle();
    }

    private void updateSurfaceScreenOn() {
        SurfaceHolder surfaceHolder = this.mSurfaceHolder;
        if (surfaceHolder != null) {
            surfaceHolder.setKeepScreenOn(this.mScreenOnWhilePlaying && this.mStayAwake);
        }
    }

    private class EventHandler extends Handler {
        private SemMediaCapture mMediaCapture;

        public EventHandler(SemMediaCapture semMediaCapture, Looper looper) {
            super(looper);
            this.mMediaCapture = semMediaCapture;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mMediaCapture.mNativeContext == 0) {
                Log.w(SemMediaCapture.TAG, "mediacapture went away with unhandled events");
                return;
            }
            int i = message.what;
            if (i != 0) {
                if (i == 1) {
                    if (SemMediaCapture.this.mOnPreparedListener != null) {
                        SemMediaCapture.this.mOnPreparedListener.onPrepared(this.mMediaCapture);
                        return;
                    }
                    return;
                }
                if (i == 5) {
                    if (SemMediaCapture.this.mOnDecodingCompletionListener != null) {
                        SemMediaCapture.this.mOnDecodingCompletionListener.onDecodingCompletion(this.mMediaCapture);
                        return;
                    }
                    return;
                }
                if (i == 6) {
                    if (SemMediaCapture.this.mOnPlaybackCompletionListener != null) {
                        SemMediaCapture.this.mOnPlaybackCompletionListener.onPlaybackCompletion(this.mMediaCapture);
                        return;
                    }
                    return;
                }
                if (i == 7) {
                    if (SemMediaCapture.this.mOnRecordingCompletionListener != null) {
                        SemMediaCapture.this.mOnRecordingCompletionListener.onRecordingCompletion(this.mMediaCapture);
                        return;
                    }
                    return;
                }
                if (i == 8) {
                    if (SemMediaCapture.this.mOnRenderingStartedListener != null) {
                        SemMediaCapture.this.mOnRenderingStartedListener.onRenderingStarted(this.mMediaCapture);
                        return;
                    }
                    return;
                }
                if (i == 10) {
                    if (SemMediaCapture.this.mOnDecodingUpdatedListener != null) {
                        SemMediaCapture.this.mOnDecodingUpdatedListener.onUpdated(this.mMediaCapture, message.arg1);
                    }
                } else {
                    if (i == 100) {
                        Log.e(SemMediaCapture.TAG, "Error (" + message.arg1 + "," + message.arg2 + NavigationBarInflaterView.KEY_CODE_END);
                        if (SemMediaCapture.this.mOnErrorListener != null) {
                            SemMediaCapture.this.mOnErrorListener.onError(this.mMediaCapture, message.arg1, message.arg2);
                            return;
                        }
                        return;
                    }
                    Log.e(SemMediaCapture.TAG, "Unknown message type " + message.what);
                }
            }
        }
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        EventHandler eventHandler;
        SemMediaCapture semMediaCapture = (SemMediaCapture) ((WeakReference) obj).get();
        if (semMediaCapture == null || (eventHandler = semMediaCapture.mEventHandler) == null) {
            return;
        }
        semMediaCapture.mEventHandler.sendMessage(eventHandler.obtainMessage(i, i2, i3, obj2));
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnRenderingStartedListener(OnRenderingStartedListener onRenderingStartedListener) {
        this.mOnRenderingStartedListener = onRenderingStartedListener;
    }

    public void setOnPlaybackCompletionListener(OnPlaybackCompletionListener onPlaybackCompletionListener) {
        this.mOnPlaybackCompletionListener = onPlaybackCompletionListener;
    }

    public void setOnRecordingCompletionListener(OnRecordingCompletionListener onRecordingCompletionListener) {
        this.mOnRecordingCompletionListener = onRecordingCompletionListener;
    }

    public void setOnDecodingCompletionListener(OnDecodingCompletionListener onDecodingCompletionListener) {
        this.mOnDecodingCompletionListener = onDecodingCompletionListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnDecodingUpdatedListener(OnDecodingUpdatedListener onDecodingUpdatedListener) {
        this.mOnDecodingUpdatedListener = onDecodingUpdatedListener;
    }
}
