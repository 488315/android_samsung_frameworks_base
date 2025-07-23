package android.telephony.ims;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telecom.VideoProfile;
import android.view.Surface;
import com.android.ims.internal.IImsVideoCallCallback;
import com.android.ims.internal.IImsVideoCallProvider;
import com.android.internal.os.SomeArgs;

@SystemApi
/* loaded from: classes4.dex */
public abstract class ImsVideoCallProvider {
    private static final int MSG_REQUEST_CALL_DATA_USAGE = 10;
    private static final int MSG_REQUEST_CAMERA_CAPABILITIES = 9;
    private static final int MSG_SEND_SESSION_MODIFY_REQUEST = 7;
    private static final int MSG_SEND_SESSION_MODIFY_RESPONSE = 8;
    private static final int MSG_SET_CALLBACK = 1;
    private static final int MSG_SET_CAMERA = 2;
    private static final int MSG_SET_DEVICE_ORIENTATION = 5;
    private static final int MSG_SET_DISPLAY_SURFACE = 4;
    private static final int MSG_SET_PAUSE_IMAGE = 11;
    private static final int MSG_SET_PREVIEW_SURFACE = 3;
    private static final int MSG_SET_ZOOM = 6;
    private IImsVideoCallCallback mCallback;
    private final Handler mProviderHandler = new Handler(Looper.getMainLooper()) { // from class: android.telephony.ims.ImsVideoCallProvider.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SomeArgs someArgs;
            switch (message.what) {
                case 1:
                    ImsVideoCallProvider.this.mCallback = (IImsVideoCallCallback) message.obj;
                    return;
                case 2:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        ImsVideoCallProvider.this.onSetCamera((String) someArgs.arg1);
                        ImsVideoCallProvider.this.onSetCamera((String) someArgs.arg1, someArgs.argi1);
                        return;
                    } finally {
                    }
                case 3:
                    ImsVideoCallProvider.this.onSetPreviewSurface((Surface) message.obj);
                    return;
                case 4:
                    ImsVideoCallProvider.this.onSetDisplaySurface((Surface) message.obj);
                    return;
                case 5:
                    ImsVideoCallProvider.this.onSetDeviceOrientation(message.arg1);
                    return;
                case 6:
                    ImsVideoCallProvider.this.onSetZoom(((Float) message.obj).floatValue());
                    return;
                case 7:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        ImsVideoCallProvider.this.onSendSessionModifyRequest((VideoProfile) someArgs.arg1, (VideoProfile) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 8:
                    ImsVideoCallProvider.this.onSendSessionModifyResponse((VideoProfile) message.obj);
                    return;
                case 9:
                    ImsVideoCallProvider.this.onRequestCameraCapabilities();
                    return;
                case 10:
                    ImsVideoCallProvider.this.onRequestCallDataUsage();
                    return;
                case 11:
                    ImsVideoCallProvider.this.onSetPauseImage((Uri) message.obj);
                    return;
                default:
                    return;
            }
        }
    };
    private final ImsVideoCallProviderBinder mBinder = new ImsVideoCallProviderBinder();

    public abstract void onRequestCallDataUsage();

    public abstract void onRequestCameraCapabilities();

    public abstract void onSendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2);

    public abstract void onSendSessionModifyResponse(VideoProfile videoProfile);

    public abstract void onSetCamera(String str);

    public void onSetCamera(String str, int i) {
    }

    public abstract void onSetDeviceOrientation(int i);

    public abstract void onSetDisplaySurface(Surface surface);

    public abstract void onSetPauseImage(Uri uri);

    public abstract void onSetPreviewSurface(Surface surface);

    public abstract void onSetZoom(float f);

    private final class ImsVideoCallProviderBinder extends IImsVideoCallProvider.Stub {
        private ImsVideoCallProviderBinder() {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setCallback(IImsVideoCallCallback iImsVideoCallCallback) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(1, iImsVideoCallCallback).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setCamera(String str, int i) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.argi1 = i;
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(2, obtain).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setPreviewSurface(Surface surface) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(3, surface).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setDisplaySurface(Surface surface) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(4, surface).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setDeviceOrientation(int i) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(5, i, 0).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setZoom(float f) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(6, Float.valueOf(f)).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = videoProfile;
            obtain.arg2 = videoProfile2;
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(7, obtain).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void sendSessionModifyResponse(VideoProfile videoProfile) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(8, videoProfile).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void requestCameraCapabilities() {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(9).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void requestCallDataUsage() {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(10).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setPauseImage(Uri uri) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(11, uri).sendToTarget();
        }
    }

    public final IImsVideoCallProvider getInterface() {
        return this.mBinder;
    }

    public void receiveSessionModifyRequest(VideoProfile videoProfile) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.receiveSessionModifyRequest(videoProfile);
            } catch (RemoteException unused) {
            }
        }
    }

    public void receiveSessionModifyResponse(int i, VideoProfile videoProfile, VideoProfile videoProfile2) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.receiveSessionModifyResponse(i, videoProfile, videoProfile2);
            } catch (RemoteException unused) {
            }
        }
    }

    public void handleCallSessionEvent(int i) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.handleCallSessionEvent(i);
            } catch (RemoteException unused) {
            }
        }
    }

    public void changePeerDimensions(int i, int i2) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.changePeerDimensions(i, i2);
            } catch (RemoteException unused) {
            }
        }
    }

    public void changeCallDataUsage(long j) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.changeCallDataUsage(j);
            } catch (RemoteException unused) {
            }
        }
    }

    public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.changeCameraCapabilities(cameraCapabilities);
            } catch (RemoteException unused) {
            }
        }
    }

    public void changeVideoQuality(int i) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.changeVideoQuality(i);
            } catch (RemoteException unused) {
            }
        }
    }
}
