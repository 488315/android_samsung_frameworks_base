package com.samsung.android.media;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Process;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes6.dex */
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
    private static SemMediaResourceHelper mMediaResourceHelper;
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

    public static synchronized SemMediaResourceHelper createInstance(int i, boolean z) {
        SemMediaResourceHelper semMediaResourceHelper;
        synchronized (SemMediaResourceHelper.class) {
            if (mMediaResourceHelper == null) {
                mMediaResourceHelper = new SemMediaResourceHelper(i, z);
            } else {
                Log.i(TAG, "SemMediaResourceHelper is already created");
            }
            semMediaResourceHelper = mMediaResourceHelper;
        }
        return semMediaResourceHelper;
    }

    private SemMediaResourceHelper(int i, boolean z) {
        this.mPid = 0;
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
        this.mResourceType = i;
        this.mOwnResourceEventExcluded = z;
        this.mPid = Process.myPid();
        native_setup(new WeakReference(this));
        Log.i(TAG, "SemMediaResourceHelper() resourceType : " + i + ", ownResourceEventExcluded : " + z + ", myPid : " + this.mPid);
    }

    public synchronized void setResourceTypeForEvent(int i) {
        Log.i(TAG, "setResourceTypeForEvent() resourceType : " + i);
        this.mResourceType = i;
    }

    public synchronized void setOwnResourceEventExcluded(boolean z) {
        Log.i(TAG, "setOwnResourceEventExcluded() ownResourceEventExcluded : " + z);
        this.mOwnResourceEventExcluded = z;
    }

    public void setResourceInfoChangedListener(ResourceInfoChangedListener resourceInfoChangedListener) throws IllegalStateException {
        this.mResourceInfoChangedListener = resourceInfoChangedListener;
        if (resourceInfoChangedListener != null) {
            native_enableObserver(0, true);
        } else {
            native_enableObserver(0, false);
        }
    }

    public void setCodecStateChangedListener(CodecStateChangedListener codecStateChangedListener) throws IllegalStateException {
        this.mCodecStateChangedListener = codecStateChangedListener;
        if (codecStateChangedListener != null) {
            native_enableObserver(1, true);
        } else {
            native_enableObserver(1, false);
        }
    }

    public void setVideoCapacityErrorListener(VideoCapacityErrorListener videoCapacityErrorListener) throws IllegalStateException {
        this.mVideoCapacityErrorListener = videoCapacityErrorListener;
        if (videoCapacityErrorListener != null) {
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

    public void setResourcePriority(int i) throws IllegalStateException {
        native_setResourcePriority(i);
    }

    public final ArrayList<MediaResourceInfo> getMediaResourceInfo(int i) throws IllegalStateException {
        Parcel obtain = Parcel.obtain();
        try {
            native_getMediaResourceInfo(i, obtain);
            return makeMediaResourceInfo(obtain);
        } finally {
            obtain.recycle();
        }
    }

    private ArrayList<MediaResourceInfo> makeMediaResourceInfo(Parcel parcel) {
        int readInt;
        boolean z;
        int i;
        ArrayList<MediaResourceInfo> arrayList = new ArrayList<>();
        if (parcel != null && (readInt = parcel.readInt()) > 0) {
            for (int i2 = 0; i2 < readInt; i2++) {
                int readInt2 = parcel.readInt();
                boolean z2 = parcel.readInt() == 1;
                int readInt3 = parcel.readInt();
                long readLong = parcel.readLong();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                boolean z3 = parcel.readInt() == 1;
                boolean z4 = parcel.readInt() == 1;
                String readString8 = parcel.readString8();
                int readInt8 = parcel.readInt();
                int i3 = this.mResourceType;
                if ((i3 == 0 || i3 == readInt2) && (!(z = this.mOwnResourceEventExcluded) || (z && (i = this.mPid) > 0 && i != readInt3))) {
                    arrayList.add(new MediaResourceInfo(readInt2, z2, readInt3, readLong, readInt4, readInt5, readInt6, readInt7, z3, z4, readString8, readInt8));
                }
            }
        }
        return arrayList;
    }

    private boolean dropOwnResourceEvent(int i) {
        int i2;
        return this.mOwnResourceEventExcluded && (i2 = this.mPid) > 0 && i == i2;
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
        clearMediaResourceHelper();
    }

    private static void clearMediaResourceHelper() {
        mMediaResourceHelper = null;
    }

    private class EventHandler extends Handler {
        private SemMediaResourceHelper mMediaResourceHelper;

        public EventHandler(SemMediaResourceHelper semMediaResourceHelper, Looper looper) {
            super(looper);
            this.mMediaResourceHelper = semMediaResourceHelper;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Log.i(SemMediaResourceHelper.TAG, "onAdd");
                ArrayList<MediaResourceInfo> arrayList = (ArrayList) message.obj;
                if (SemMediaResourceHelper.this.mResourceInfoChangedListener != null) {
                    SemMediaResourceHelper.this.mResourceInfoChangedListener.onAdd(arrayList);
                    return;
                }
                return;
            }
            if (i == 2) {
                Log.i(SemMediaResourceHelper.TAG, "onRemove");
                ArrayList<MediaResourceInfo> arrayList2 = (ArrayList) message.obj;
                if (SemMediaResourceHelper.this.mResourceInfoChangedListener != null) {
                    SemMediaResourceHelper.this.mResourceInfoChangedListener.onRemove(arrayList2);
                    return;
                }
                return;
            }
            if (i == 3) {
                Log.i(SemMediaResourceHelper.TAG, "onState");
                ArrayList<MediaResourceInfo> arrayList3 = (ArrayList) message.obj;
                if (SemMediaResourceHelper.this.mCodecStateChangedListener != null) {
                    SemMediaResourceHelper.this.mCodecStateChangedListener.onStateChanged(arrayList3);
                    return;
                }
                return;
            }
            if (i == 4) {
                Log.i(SemMediaResourceHelper.TAG, "onCapacityError");
                if (message.obj instanceof ArrayList) {
                    ArrayList arrayList4 = (ArrayList) message.obj;
                    if (SemMediaResourceHelper.this.mVideoCapacityErrorListener != null) {
                        SemMediaResourceHelper.this.mVideoCapacityErrorListener.onError((MediaResourceInfo) arrayList4.get(0));
                        return;
                    }
                    return;
                }
                return;
            }
            if (i != 100) {
                return;
            }
            Log.i(SemMediaResourceHelper.TAG, "onError");
            if (SemMediaResourceHelper.this.mResourceInfoChangedListener != null) {
                SemMediaResourceHelper.this.mResourceInfoChangedListener.onError(this.mMediaResourceHelper);
            } else {
                Log.i(SemMediaResourceHelper.TAG, "no listener exists to handle event, release internally");
                this.mMediaResourceHelper.release();
            }
        }
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        SemMediaResourceHelper semMediaResourceHelper = (SemMediaResourceHelper) ((WeakReference) obj).get();
        if (semMediaResourceHelper == null) {
            Log.w(TAG, "semMediaResourceHelper ref is null");
            return;
        }
        if (semMediaResourceHelper.mEventHandler != null) {
            if (obj2 != null) {
                Parcel parcel = (Parcel) obj2;
                if (semMediaResourceHelper.dropOwnResourceEvent(parcel.readInt())) {
                    Log.i(TAG, "Skip event. mOwnResourceEventExcluded is enabled and owned resource");
                    return;
                } else {
                    ArrayList<MediaResourceInfo> makeMediaResourceInfo = semMediaResourceHelper.makeMediaResourceInfo(parcel);
                    parcel.recycle();
                    obj2 = makeMediaResourceInfo;
                }
            }
            semMediaResourceHelper.mEventHandler.sendMessage(semMediaResourceHelper.mEventHandler.obtainMessage(i, i2, i3, obj2));
        }
    }

    public static final class MediaResourceInfo {
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

        MediaResourceInfo(int i, boolean z, int i2, long j, int i3, int i4, int i5, int i6, boolean z2, boolean z3, String str, int i7) {
            this.mResourceType = i;
            this.mIsSecured = z;
            this.mCodecState = i3;
            this.mClientId = j;
            this.mPid = i2;
            this.mWidth = i4;
            this.mHeight = i5;
            this.mFramerate = i6;
            this.mIsEncoder = z2;
            this.mIsSoftware = z3;
            this.mCodecName = str;
            this.mBitrate = i7;
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

    static {
        System.loadLibrary("mediaresourcehelper");
    }
}
