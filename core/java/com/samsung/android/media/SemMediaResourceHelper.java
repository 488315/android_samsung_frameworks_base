package com.samsung.android.media;

import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.Parcel;
import android.p009os.Process;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public final class SemMediaResourceHelper {
    public static final int CODEC_STATE_RUNNING = 1;
    public static final int CODEC_STATE_WAITING = 0;
    private static final boolean DEBUG = true;
    private static final int EVENT_ADD_RESOURCE = 1;
    private static final int EVENT_CAPACITY_ERROR = 4;
    private static final int EVENT_ERROR = 100;
    private static final int EVENT_REMOVE_RESOURCE = 2;
    private static final int EVENT_UPDATE_STATE = 3;
    private static final int LISTENER_TYPE_CAPACITY_ERROR = 2;
    private static final int LISTENER_TYPE_INFO = 0;
    private static final int LISTENER_TYPE_STATE = 1;
    private static final int PARAMETER_CAPACITY_MAX = 0;
    private static final int PARAMETER_CAPACITY_REMAINED = 1;
    public static final int RESOURCE_PRIORITY_HIGH = 10;
    public static final int RESOURCE_PRIORITY_LOW = 0;
    public static final int RESOURCE_TYPE_ALL = 0;
    public static final int RESOURCE_TYPE_AUDIO = 1;
    public static final int RESOURCE_TYPE_VIDEO = 2;
    private static final String TAG = "SemMediaResourceHelper";
    private static SemMediaResourceHelper mMediaResourceHelper = null;
    private EventHandler mEventHandler;
    private long mNativeContext;
    private boolean mOwnResourceEventExcluded;
    private int mPid;
    private int mResourceType;
    private ResourceInfoChangedListener mResourceInfoChangedListener = null;
    private CodecStateChangedListener mCodecStateChangedListener = null;
    private VideoCapacityErrorListener mVideoCapacityErrorListener = null;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CodecState {
    }

    public interface CodecStateChangedListener {
        void onStateChanged(ArrayList<MediaResourceInfo> arrayList);
    }

    public interface ResourceInfoChangedListener {
        void onAdd(ArrayList<MediaResourceInfo> arrayList);

        void onError(SemMediaResourceHelper semMediaResourceHelper);

        void onRemove(ArrayList<MediaResourceInfo> arrayList);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResourceType {
    }

    public interface VideoCapacityErrorListener {
        void onError(MediaResourceInfo mediaResourceInfo);
    }

    private native void native_enableObserver(int i, boolean z) throws IllegalStateException;

    private final native void native_finalize();

    private native int native_getCodecCapacity(int i) throws IllegalStateException;

    private native void native_getMediaResourceInfo(int i, Parcel parcel) throws IllegalStateException;

    private final native void native_release();

    private native void native_setResourcePriority(int i) throws IllegalStateException;

    private final native void native_setup(Object obj);

    static {
        System.loadLibrary("mediaresourcehelper");
    }

    public static synchronized SemMediaResourceHelper createInstance(int resourceType, boolean ownResourceEventExcluded) {
        SemMediaResourceHelper semMediaResourceHelper;
        synchronized (SemMediaResourceHelper.class) {
            if (mMediaResourceHelper == null) {
                mMediaResourceHelper = new SemMediaResourceHelper(resourceType, ownResourceEventExcluded);
            } else {
                Log.m98i(TAG, "SemMediaResourceHelper is already created");
            }
            semMediaResourceHelper = mMediaResourceHelper;
        }
        return semMediaResourceHelper;
    }

    private SemMediaResourceHelper(int resourceType, boolean ownResourceEventExcluded) {
        this.mPid = 0;
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
        this.mResourceType = resourceType;
        this.mOwnResourceEventExcluded = ownResourceEventExcluded;
        this.mPid = Process.myPid();
        native_setup(new WeakReference(this));
        Log.m98i(TAG, "SemMediaResourceHelper() resourceType : " + resourceType + ", ownResourceEventExcluded : " + ownResourceEventExcluded + ", myPid : " + this.mPid);
    }

    public synchronized void setResourceTypeForEvent(int resourceType) {
        Log.m98i(TAG, "setResourceTypeForEvent() resourceType : " + resourceType);
        this.mResourceType = resourceType;
    }

    public synchronized void setOwnResourceEventExcluded(boolean ownResourceEventExcluded) {
        Log.m98i(TAG, "setOwnResourceEventExcluded() ownResourceEventExcluded : " + ownResourceEventExcluded);
        this.mOwnResourceEventExcluded = ownResourceEventExcluded;
    }

    public void setResourceInfoChangedListener(ResourceInfoChangedListener listener) throws IllegalStateException {
        this.mResourceInfoChangedListener = listener;
        if (listener != null) {
            native_enableObserver(0, true);
        } else {
            native_enableObserver(0, false);
        }
    }

    public void setCodecStateChangedListener(CodecStateChangedListener listener) throws IllegalStateException {
        this.mCodecStateChangedListener = listener;
        if (listener == null) {
            native_enableObserver(1, false);
        } else {
            native_enableObserver(1, true);
        }
    }

    public void setVideoCapacityErrorListener(VideoCapacityErrorListener listener) throws IllegalStateException {
        this.mVideoCapacityErrorListener = listener;
        if (listener != null) {
            native_enableObserver(2, true);
        } else {
            native_enableObserver(2, false);
        }
    }

    public int getMaxVideoCapacity() throws IllegalStateException {
        return native_getCodecCapacity(0);
    }

    public int getRemainedVideoCapacity() throws IllegalStateException {
        return native_getCodecCapacity(1);
    }

    public void setResourcePriority(int priority) throws IllegalStateException {
        native_setResourcePriority(priority);
    }

    public final ArrayList<MediaResourceInfo> getMediaResourceInfo(int resourceType) throws IllegalStateException {
        Parcel reply = Parcel.obtain();
        try {
            native_getMediaResourceInfo(resourceType, reply);
            return makeMediaResourceInfo(reply);
        } finally {
            reply.recycle();
        }
    }

    private ArrayList<MediaResourceInfo> makeMediaResourceInfo(Parcel in) {
        boolean z;
        int i;
        int total_size;
        ArrayList<MediaResourceInfo> mediaResourceInfo;
        int i2;
        SemMediaResourceHelper semMediaResourceHelper = this;
        ArrayList<MediaResourceInfo> mediaResourceInfo2 = new ArrayList<>();
        if (in == null) {
            return mediaResourceInfo2;
        }
        int total_size2 = in.readInt();
        if (total_size2 <= 0) {
            return mediaResourceInfo2;
        }
        int i3 = 0;
        while (i3 < total_size2) {
            int resourceType = in.readInt();
            boolean isSecured = in.readInt() == 1;
            int pid = in.readInt();
            long client = in.readLong();
            int state = in.readInt();
            int width = in.readInt();
            int height = in.readInt();
            int framerate = in.readInt();
            boolean isEncoder = in.readInt() == 1;
            boolean isSWCodec = in.readInt() == 1;
            String ComponentName = in.readString8();
            int bitrate = in.readInt();
            int i4 = semMediaResourceHelper.mResourceType;
            if ((i4 == 0 || i4 == resourceType) && (!(z = semMediaResourceHelper.mOwnResourceEventExcluded) || (z && (i2 = semMediaResourceHelper.mPid) > 0 && i2 != pid))) {
                i = i3;
                total_size = total_size2;
                mediaResourceInfo = mediaResourceInfo2;
                MediaResourceInfo resourceInfo = new MediaResourceInfo(resourceType, isSecured, pid, client, state, width, height, framerate, isEncoder, isSWCodec, ComponentName, bitrate);
                mediaResourceInfo.add(resourceInfo);
            } else {
                i = i3;
                total_size = total_size2;
                mediaResourceInfo = mediaResourceInfo2;
            }
            i3 = i + 1;
            mediaResourceInfo2 = mediaResourceInfo;
            total_size2 = total_size;
            semMediaResourceHelper = this;
        }
        return mediaResourceInfo2;
    }

    private boolean dropOwnResourceEvent(int event_occured_pid) {
        int i;
        if (this.mOwnResourceEventExcluded && (i = this.mPid) > 0 && event_occured_pid == i) {
            return true;
        }
        return false;
    }

    public void release() {
        native_release();
        EventHandler eventHandler = this.mEventHandler;
        if (eventHandler != null) {
            eventHandler.removeCallbacksAndMessages(null);
        }
        this.mResourceInfoChangedListener = null;
        this.mCodecStateChangedListener = null;
        this.mVideoCapacityErrorListener = null;
        this.mEventHandler = null;
        mMediaResourceHelper = null;
    }

    private class EventHandler extends Handler {
        private SemMediaResourceHelper mMediaResourceHelper;

        public EventHandler(SemMediaResourceHelper semMediaResourceHelper, Looper looper) {
            super(looper);
            this.mMediaResourceHelper = semMediaResourceHelper;
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.m98i(SemMediaResourceHelper.TAG, "onAdd");
                    ArrayList<MediaResourceInfo> mediaResourceInfo = (ArrayList) msg.obj;
                    if (SemMediaResourceHelper.this.mResourceInfoChangedListener != null) {
                        SemMediaResourceHelper.this.mResourceInfoChangedListener.onAdd(mediaResourceInfo);
                        break;
                    }
                    break;
                case 2:
                    Log.m98i(SemMediaResourceHelper.TAG, "onRemove");
                    ArrayList<MediaResourceInfo> mediaResourceInfo2 = (ArrayList) msg.obj;
                    if (SemMediaResourceHelper.this.mResourceInfoChangedListener != null) {
                        SemMediaResourceHelper.this.mResourceInfoChangedListener.onRemove(mediaResourceInfo2);
                        break;
                    }
                    break;
                case 3:
                    Log.m98i(SemMediaResourceHelper.TAG, "onState");
                    ArrayList<MediaResourceInfo> mediaResourceInfo3 = (ArrayList) msg.obj;
                    if (SemMediaResourceHelper.this.mCodecStateChangedListener != null) {
                        SemMediaResourceHelper.this.mCodecStateChangedListener.onStateChanged(mediaResourceInfo3);
                        break;
                    }
                    break;
                case 4:
                    Log.m98i(SemMediaResourceHelper.TAG, "onCapacityError");
                    ArrayList<MediaResourceInfo> mediaResourceInfo4 = (ArrayList) msg.obj;
                    if (SemMediaResourceHelper.this.mVideoCapacityErrorListener != null) {
                        SemMediaResourceHelper.this.mVideoCapacityErrorListener.onError(mediaResourceInfo4.get(0));
                        break;
                    }
                    break;
                case 100:
                    Log.m98i(SemMediaResourceHelper.TAG, "onError");
                    if (SemMediaResourceHelper.this.mResourceInfoChangedListener != null) {
                        SemMediaResourceHelper.this.mResourceInfoChangedListener.onError(this.mMediaResourceHelper);
                        break;
                    }
                    break;
            }
        }
    }

    private static void postEventFromNative(Object semMediaResourceHelper_ref, int what, int arg1, int arg2, Object obj) {
        SemMediaResourceHelper semMediaResourceHelper = (SemMediaResourceHelper) ((WeakReference) semMediaResourceHelper_ref).get();
        if (semMediaResourceHelper == null) {
            Log.m102w(TAG, "semMediaResourceHelper ref is null");
            return;
        }
        if (semMediaResourceHelper.mEventHandler != null) {
            if (obj != null) {
                Parcel parcel = (Parcel) obj;
                int event_occured_pid = parcel.readInt();
                if (semMediaResourceHelper.dropOwnResourceEvent(event_occured_pid)) {
                    Log.m98i(TAG, "Skip event. mOwnResourceEventExcluded is enabled and owned resource");
                    return;
                } else {
                    ArrayList<MediaResourceInfo> mediaResourceInfo = semMediaResourceHelper.makeMediaResourceInfo(parcel);
                    parcel.recycle();
                    obj = mediaResourceInfo;
                }
            }
            Message msg = semMediaResourceHelper.mEventHandler.obtainMessage(what, arg1, arg2, obj);
            semMediaResourceHelper.mEventHandler.sendMessage(msg);
        }
    }

    public final class MediaResourceInfo {
        private final int mBitrate;
        private final long mClientId;
        private final String mCodecName;
        private final int mCodecState;
        private final int mFramerate;
        private final int mHeight;
        private final boolean mIsEncoder;
        private final boolean mIsSecured;
        private final boolean mIsSoftware;
        private final int mPid;
        private final int mResourceType;
        private final int mWidth;

        MediaResourceInfo(int resourceType, boolean isSecured, int pid, long clientId, int state, int width, int height, int framerate, boolean isEncoder, boolean isSoftware, String codecName, int bitrate) {
            this.mResourceType = resourceType;
            this.mIsSecured = isSecured;
            this.mCodecState = state;
            this.mClientId = clientId;
            this.mPid = pid;
            this.mWidth = width;
            this.mHeight = height;
            this.mFramerate = framerate;
            this.mIsEncoder = isEncoder;
            this.mIsSoftware = isSoftware;
            this.mCodecName = codecName;
            this.mBitrate = bitrate;
        }

        public int getResourceType() {
            return this.mResourceType;
        }

        public boolean isSecured() {
            return this.mIsSecured;
        }

        public int getPid() {
            return this.mPid;
        }

        public long getClientId() {
            return this.mClientId;
        }

        public int getCodecState() {
            return this.mCodecState;
        }

        public int getVideoWidth() {
            return this.mWidth;
        }

        public int getVideoHeight() {
            return this.mHeight;
        }

        public int getVideoFrameRate() {
            return this.mFramerate;
        }

        public boolean isEncoder() {
            return this.mIsEncoder;
        }

        public boolean isSoftware() {
            return this.mIsSoftware;
        }

        public String getCodecName() {
            return this.mCodecName;
        }

        public int getVideoBitrate() {
            return this.mBitrate;
        }
    }

    protected void finalize() {
        native_finalize();
    }
}
