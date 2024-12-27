package android.media.tv.ad;

import android.content.Context;
import android.graphics.Rect;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.Surface;

import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;

import java.util.List;

/* loaded from: classes3.dex */
public class ITvAdSessionWrapper extends ITvAdSession.Stub implements HandlerCaller.Callback {
    private static final int DO_CREATE_MEDIA_VIEW = 4;
    private static final int DO_DISPATCH_SURFACE_CHANGED = 3;
    private static final int DO_NOTIFY_ERROR = 15;
    private static final int DO_NOTIFY_INPUT_SESSION_DATA = 17;
    private static final int DO_NOTIFY_TV_MESSAGE = 16;
    private static final int DO_RELAYOUT_MEDIA_VIEW = 5;
    private static final int DO_RELEASE = 1;
    private static final int DO_REMOVE_MEDIA_VIEW = 6;
    private static final int DO_RESET_AD_SERVICE = 9;
    private static final int DO_SEND_CURRENT_CHANNEL_URI = 11;
    private static final int DO_SEND_CURRENT_TV_INPUT_ID = 13;
    private static final int DO_SEND_CURRENT_VIDEO_BOUNDS = 10;
    private static final int DO_SEND_SIGNING_RESULT = 14;
    private static final int DO_SEND_TRACK_INFO_LIST = 12;
    private static final int DO_SET_SURFACE = 2;
    private static final int DO_START_AD_SERVICE = 7;
    private static final int DO_STOP_AD_SERVICE = 8;
    private static final int EXECUTE_MESSAGE_TIMEOUT_LONG_MILLIS = 5000;
    private static final int EXECUTE_MESSAGE_TIMEOUT_SHORT_MILLIS = 1000;
    private static final String TAG = "ITvAdSessionWrapper";
    private final HandlerCaller mCaller;
    private InputChannel mChannel;
    private TvAdEventReceiver mReceiver;
    private TvAdService.Session mSessionImpl;

    public ITvAdSessionWrapper(
            Context context, TvAdService.Session mSessionImpl, InputChannel channel) {
        this.mSessionImpl = mSessionImpl;
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mChannel = channel;
        if (channel != null) {
            this.mReceiver = new TvAdEventReceiver(channel, context.getMainLooper());
        }
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void release() {
        this.mSessionImpl.scheduleMediaViewCleanup();
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(1));
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(Message msg) {
        if (this.mSessionImpl == null) {
            return;
        }
        long startTime = System.nanoTime();
        switch (msg.what) {
            case 1:
                this.mSessionImpl.release();
                this.mSessionImpl = null;
                if (this.mReceiver != null) {
                    this.mReceiver.dispose();
                    this.mReceiver = null;
                }
                if (this.mChannel != null) {
                    this.mChannel.dispose();
                    this.mChannel = null;
                    break;
                }
                break;
            case 2:
                this.mSessionImpl.setSurface((Surface) msg.obj);
                break;
            case 3:
                SomeArgs args = (SomeArgs) msg.obj;
                this.mSessionImpl.dispatchSurfaceChanged(
                        Integer.valueOf(args.argi1).intValue(),
                        Integer.valueOf(args.argi2).intValue(),
                        Integer.valueOf(args.argi3).intValue());
                args.recycle();
                break;
            case 4:
                SomeArgs args2 = (SomeArgs) msg.obj;
                this.mSessionImpl.createMediaView((IBinder) args2.arg1, (Rect) args2.arg2);
                args2.recycle();
                break;
            case 5:
                this.mSessionImpl.relayoutMediaView((Rect) msg.obj);
                break;
            case 6:
                this.mSessionImpl.removeMediaView(true);
                break;
            case 7:
                this.mSessionImpl.startAdService();
                break;
            case 8:
                this.mSessionImpl.stopAdService();
                break;
            case 9:
                this.mSessionImpl.resetAdService();
                break;
            case 10:
                this.mSessionImpl.sendCurrentVideoBounds((Rect) msg.obj);
                break;
            case 11:
                this.mSessionImpl.sendCurrentChannelUri((Uri) msg.obj);
                break;
            case 12:
                this.mSessionImpl.sendTrackInfoList((List) msg.obj);
                break;
            case 13:
                this.mSessionImpl.sendCurrentTvInputId((String) msg.obj);
                break;
            case 14:
                SomeArgs args3 = (SomeArgs) msg.obj;
                this.mSessionImpl.sendSigningResult((String) args3.arg1, (byte[]) args3.arg2);
                args3.recycle();
                break;
            case 15:
                SomeArgs args4 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyError((String) args4.arg1, (Bundle) args4.arg2);
                args4.recycle();
                break;
            case 16:
                SomeArgs args5 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyTvMessage(
                        ((Integer) args5.arg1).intValue(), (Bundle) args5.arg2);
                args5.recycle();
                break;
            case 17:
                SomeArgs args6 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyTvInputSessionData(
                        (String) args6.arg1, (Bundle) args6.arg2);
                args6.recycle();
                break;
            default:
                Log.w(TAG, "Unhandled message code: " + msg.what);
                break;
        }
        long durationMs = (System.nanoTime() - startTime) / 1000000;
        if (durationMs > 1000) {
            Log.w(
                    TAG,
                    "Handling message ("
                            + msg.what
                            + ") took too long time (duration="
                            + durationMs
                            + "ms)");
        }
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void startAdService() throws RemoteException {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(7));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void stopAdService() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(8));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void resetAdService() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(9));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void setSurface(Surface surface) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(2, surface));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void dispatchSurfaceChanged(int format, int width, int height) {
        this.mCaller.executeOrSendMessage(
                this.mCaller.obtainMessageIIII(3, format, width, height, 0));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendCurrentVideoBounds(Rect bounds) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(10, bounds));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendCurrentChannelUri(Uri channelUri) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(11, channelUri));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendTrackInfoList(List<TvTrackInfo> tracks) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(12, tracks));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendCurrentTvInputId(String inputId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(13, inputId));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendSigningResult(String signingId, byte[] result) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(14, signingId, result));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void notifyError(String errMsg, Bundle params) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(15, errMsg, params));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void notifyTvMessage(int type, Bundle data) {
        this.mCaller.executeOrSendMessage(
                this.mCaller.obtainMessageOO(16, Integer.valueOf(type), data));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void createMediaView(IBinder windowToken, Rect frame) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(4, windowToken, frame));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void relayoutMediaView(Rect frame) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(5, frame));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void removeMediaView() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(6));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void notifyTvInputSessionData(String type, Bundle data) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(17, type, data));
    }

    private final class TvAdEventReceiver extends InputEventReceiver {
        TvAdEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent event) {
            if (ITvAdSessionWrapper.this.mSessionImpl == null) {
                finishInputEvent(event, false);
                return;
            }
            int handled = ITvAdSessionWrapper.this.mSessionImpl.dispatchInputEvent(event, this);
            if (handled != -1) {
                finishInputEvent(event, handled == 1);
            }
        }
    }
}
