package android.telecom;

import android.net.Uri;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.RemoteException;
import android.telecom.InCallService;
import android.telecom.VideoProfile;
import android.telephony.Rlog;
import android.view.Surface;
import com.android.internal.p029os.SomeArgs;
import com.android.internal.telecom.IVideoCallback;
import com.android.internal.telecom.IVideoProvider;
import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
public class VideoCallImpl extends InCallService.VideoCall {
    private static final String LOG_TAG = "Telecom-VideoCallImpl";
    private final VideoCallListenerBinder mBinder;
    private InCallService.VideoCall.Callback mCallback;
    private final String mCallingPackageName;
    private Handler mHandler;
    private int mTargetSdkVersion;
    private final IVideoProvider mVideoProvider;
    private int mVideoQuality = 0;
    private int mVideoState = 0;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: android.telecom.VideoCallImpl.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            try {
                VideoCallImpl.this.mVideoProvider.asBinder().unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
            }
        }
    };

    private final class VideoCallListenerBinder extends IVideoCallback.Stub {
        private VideoCallListenerBinder() {
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void receiveSessionModifyRequest(VideoProfile videoProfile) {
            Rlog.m77d(VideoCallImpl.LOG_TAG, "receiveSessionModifyRequest - videoProfile: " + videoProfile);
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(1, videoProfile).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void receiveSessionModifyResponse(int status, VideoProfile requestProfile, VideoProfile responseProfile) {
            Rlog.m77d(VideoCallImpl.LOG_TAG, "receiveSessionModifyResponse - status: " + status + ", request: " + requestProfile + ", response: " + responseProfile);
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = Integer.valueOf(status);
            args.arg2 = requestProfile;
            args.arg3 = responseProfile;
            VideoCallImpl.this.mHandler.obtainMessage(2, args).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void handleCallSessionEvent(int event) {
            Rlog.m77d(VideoCallImpl.LOG_TAG, "handleCallSessionEvent - event: " + event);
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(3, Integer.valueOf(event)).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changePeerDimensions(int width, int height) {
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = Integer.valueOf(width);
            args.arg2 = Integer.valueOf(height);
            VideoCallImpl.this.mHandler.obtainMessage(4, args).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeVideoQuality(int videoQuality) {
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(7, videoQuality, 0).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeCallDataUsage(long dataUsage) {
            Rlog.m77d(VideoCallImpl.LOG_TAG, "changeCallDataUsage - dataUsage: " + dataUsage);
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(5, Long.valueOf(dataUsage)).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) {
            Rlog.m77d(VideoCallImpl.LOG_TAG, "changeCameraCapabilities - capabilities: " + cameraCapabilities);
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(6, cameraCapabilities).sendToTarget();
        }
    }

    private final class MessageHandler extends Handler {
        private static final int MSG_CHANGE_CALL_DATA_USAGE = 5;
        private static final int MSG_CHANGE_CAMERA_CAPABILITIES = 6;
        private static final int MSG_CHANGE_PEER_DIMENSIONS = 4;
        private static final int MSG_CHANGE_VIDEO_QUALITY = 7;
        private static final int MSG_HANDLE_CALL_SESSION_EVENT = 3;
        private static final int MSG_RECEIVE_SESSION_MODIFY_REQUEST = 1;
        private static final int MSG_RECEIVE_SESSION_MODIFY_RESPONSE = 2;

        public MessageHandler(Looper looper) {
            super(looper);
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            SomeArgs args;
            if (VideoCallImpl.this.mCallback == null) {
                return;
            }
            switch (msg.what) {
                case 1:
                    VideoCallImpl.this.mCallback.onSessionModifyRequestReceived((VideoProfile) msg.obj);
                    return;
                case 2:
                    args = (SomeArgs) msg.obj;
                    try {
                        int status = ((Integer) args.arg1).intValue();
                        VideoProfile requestProfile = (VideoProfile) args.arg2;
                        VideoProfile responseProfile = (VideoProfile) args.arg3;
                        VideoCallImpl.this.mCallback.onSessionModifyResponseReceived(status, requestProfile, responseProfile);
                        return;
                    } finally {
                    }
                case 3:
                    VideoCallImpl.this.mCallback.onCallSessionEvent(((Integer) msg.obj).intValue());
                    return;
                case 4:
                    args = (SomeArgs) msg.obj;
                    try {
                        int width = ((Integer) args.arg1).intValue();
                        int height = ((Integer) args.arg2).intValue();
                        VideoCallImpl.this.mCallback.onPeerDimensionsChanged(width, height);
                        return;
                    } finally {
                    }
                case 5:
                    VideoCallImpl.this.mCallback.onCallDataUsageChanged(((Long) msg.obj).longValue());
                    return;
                case 6:
                    VideoCallImpl.this.mCallback.onCameraCapabilitiesChanged((VideoProfile.CameraCapabilities) msg.obj);
                    return;
                case 7:
                    VideoCallImpl.this.mVideoQuality = msg.arg1;
                    VideoCallImpl.this.mCallback.onVideoQualityChanged(msg.arg1);
                    return;
                default:
                    return;
            }
        }
    }

    VideoCallImpl(IVideoProvider videoProvider, String callingPackageName, int targetSdkVersion) throws RemoteException {
        this.mVideoProvider = videoProvider;
        videoProvider.asBinder().linkToDeath(this.mDeathRecipient, 0);
        VideoCallListenerBinder videoCallListenerBinder = new VideoCallListenerBinder();
        this.mBinder = videoCallListenerBinder;
        videoProvider.addVideoCallback(videoCallListenerBinder);
        this.mCallingPackageName = callingPackageName;
        setTargetSdkVersion(targetSdkVersion);
        Rlog.m77d(LOG_TAG, "New VideoCallImpl - videoProvider: " + videoProvider + ", binder: " + videoCallListenerBinder);
    }

    public void setTargetSdkVersion(int sdkVersion) {
        this.mTargetSdkVersion = sdkVersion;
    }

    @Override // android.telecom.InCallService.VideoCall
    public void destroy() {
        Rlog.m77d(LOG_TAG, "destroy");
        unregisterCallback(this.mCallback);
        try {
            this.mVideoProvider.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
        } catch (NoSuchElementException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void registerCallback(InCallService.VideoCall.Callback callback) {
        registerCallback(callback, null);
    }

    @Override // android.telecom.InCallService.VideoCall
    public void registerCallback(InCallService.VideoCall.Callback callback, Handler handler) {
        this.mCallback = callback;
        if (handler == null) {
            this.mHandler = new MessageHandler(Looper.getMainLooper());
        } else {
            this.mHandler = new MessageHandler(handler.getLooper());
        }
        Rlog.m77d(LOG_TAG, "registerCallback - callback: " + this.mCallback + ", handler: " + this.mHandler);
    }

    @Override // android.telecom.InCallService.VideoCall
    public void unregisterCallback(InCallService.VideoCall.Callback callback) {
        if (callback != this.mCallback) {
            Rlog.m77d(LOG_TAG, "unregisterCallback is ignored. callback is different. mCallback: " + this.mCallback + ", callback: " + callback);
            return;
        }
        Rlog.m77d(LOG_TAG, "unregisterCallback - callback: " + callback);
        this.mCallback = null;
        try {
            this.mVideoProvider.removeVideoCallback(this.mBinder);
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setCamera(String cameraId) {
        try {
            Log.m75w(this, "setCamera: cameraId=%s, calling=%s", cameraId, this.mCallingPackageName);
            this.mVideoProvider.setCamera(cameraId, this.mCallingPackageName, this.mTargetSdkVersion);
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setPreviewSurface(Surface surface) {
        try {
            this.mVideoProvider.setPreviewSurface(surface);
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setDisplaySurface(Surface surface) {
        try {
            this.mVideoProvider.setDisplaySurface(surface);
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setDeviceOrientation(int rotation) {
        try {
            this.mVideoProvider.setDeviceOrientation(rotation);
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setZoom(float value) {
        try {
            this.mVideoProvider.setZoom(value);
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void sendSessionModifyRequest(VideoProfile requestProfile) {
        Rlog.m77d(LOG_TAG, "sendSessionModifyRequest - request: " + requestProfile);
        try {
            VideoProfile originalProfile = new VideoProfile(this.mVideoState, this.mVideoQuality);
            this.mVideoProvider.sendSessionModifyRequest(originalProfile, requestProfile);
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void sendSessionModifyResponse(VideoProfile responseProfile) {
        Rlog.m77d(LOG_TAG, "sendSessionModifyResponse - response: " + responseProfile);
        try {
            this.mVideoProvider.sendSessionModifyResponse(responseProfile);
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void requestCameraCapabilities() {
        try {
            this.mVideoProvider.requestCameraCapabilities();
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void requestCallDataUsage() {
        Rlog.m77d(LOG_TAG, "requestCallDataUsage");
        try {
            this.mVideoProvider.requestCallDataUsage();
        } catch (RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setPauseImage(Uri uri) {
        Rlog.m77d(LOG_TAG, "setPauseImage");
        try {
            this.mVideoProvider.setPauseImage(uri);
        } catch (RemoteException e) {
        }
    }

    public void setVideoState(int videoState) {
        if (this.mVideoState != videoState) {
            Rlog.m77d(LOG_TAG, "setVideoState - videoState: " + videoState);
        }
        this.mVideoState = videoState;
    }

    public IVideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }
}
