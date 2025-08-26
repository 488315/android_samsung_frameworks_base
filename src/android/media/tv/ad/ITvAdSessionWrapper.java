package android.media.tv.ad;

import android.content.Context;
import android.graphics.Rect;
import android.media.tv.TvTrackInfo;
import android.media.tv.ad.ITvAdSession;
import android.media.tv.ad.TvAdService;
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

    public ITvAdSessionWrapper(Context context, TvAdService.Session session, InputChannel inputChannel) {
        this.mSessionImpl = session;
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new TvAdEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void release() {
        this.mSessionImpl.scheduleMediaViewCleanup();
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(1));
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(Message message) {
        if (this.mSessionImpl == null) {
            return;
        }
        long jNanoTime = System.nanoTime();
        switch (message.what) {
            case 1:
                this.mSessionImpl.release();
                this.mSessionImpl = null;
                TvAdEventReceiver tvAdEventReceiver = this.mReceiver;
                if (tvAdEventReceiver != null) {
                    tvAdEventReceiver.dispose();
                    this.mReceiver = null;
                }
                InputChannel inputChannel = this.mChannel;
                if (inputChannel != null) {
                    inputChannel.dispose();
                    this.mChannel = null;
                    break;
                }
                break;
            case 2:
                this.mSessionImpl.setSurface((Surface) message.obj);
                break;
            case 3:
                SomeArgs someArgs = (SomeArgs) message.obj;
                TvAdService.Session session = this.mSessionImpl;
                int i = someArgs.argi1;
                Integer.valueOf(i).getClass();
                int i2 = someArgs.argi2;
                Integer.valueOf(i2).getClass();
                int i3 = someArgs.argi3;
                Integer.valueOf(i3).getClass();
                session.dispatchSurfaceChanged(i, i2, i3);
                someArgs.recycle();
                break;
            case 4:
                SomeArgs someArgs2 = (SomeArgs) message.obj;
                this.mSessionImpl.createMediaView((IBinder) someArgs2.arg1, (Rect) someArgs2.arg2);
                someArgs2.recycle();
                break;
            case 5:
                this.mSessionImpl.relayoutMediaView((Rect) message.obj);
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
                this.mSessionImpl.sendCurrentVideoBounds((Rect) message.obj);
                break;
            case 11:
                this.mSessionImpl.sendCurrentChannelUri((Uri) message.obj);
                break;
            case 12:
                this.mSessionImpl.sendTrackInfoList((List) message.obj);
                break;
            case 13:
                this.mSessionImpl.sendCurrentTvInputId((String) message.obj);
                break;
            case 14:
                SomeArgs someArgs3 = (SomeArgs) message.obj;
                this.mSessionImpl.sendSigningResult((String) someArgs3.arg1, (byte[]) someArgs3.arg2);
                someArgs3.recycle();
                break;
            case 15:
                SomeArgs someArgs4 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyError((String) someArgs4.arg1, (Bundle) someArgs4.arg2);
                someArgs4.recycle();
                break;
            case 16:
                SomeArgs someArgs5 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyTvMessage(((Integer) someArgs5.arg1).intValue(), (Bundle) someArgs5.arg2);
                someArgs5.recycle();
                break;
            case 17:
                SomeArgs someArgs6 = (SomeArgs) message.obj;
                this.mSessionImpl.notifyTvInputSessionData((String) someArgs6.arg1, (Bundle) someArgs6.arg2);
                someArgs6.recycle();
                break;
            default:
                Log.w(TAG, "Unhandled message code: " + message.what);
                break;
        }
        long jNanoTime2 = (System.nanoTime() - jNanoTime) / 1000000;
        if (jNanoTime2 > 1000) {
            Log.w(TAG, "Handling message (" + message.what + ") took too long time (duration=" + jNanoTime2 + "ms)");
        }
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void startAdService() throws RemoteException {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(7));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void stopAdService() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(8));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void resetAdService() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(9));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void setSurface(Surface surface) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(2, surface));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIIII(3, i, i2, i3, 0));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendCurrentVideoBounds(Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(10, rect));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendCurrentChannelUri(Uri uri) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(11, uri));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendTrackInfoList(List<TvTrackInfo> list) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(12, list));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendCurrentTvInputId(String str) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(13, str));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendSigningResult(String str, byte[] bArr) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(14, str, bArr));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void notifyError(String str, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(15, str, bundle));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void notifyTvMessage(int i, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(16, Integer.valueOf(i), bundle));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void createMediaView(IBinder iBinder, Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(4, iBinder, rect));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void relayoutMediaView(Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(5, rect));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void removeMediaView() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(6));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void notifyTvInputSessionData(String str, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(17, str, bundle));
    }

    private final class TvAdEventReceiver extends InputEventReceiver {
        TvAdEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent inputEvent) {
            if (ITvAdSessionWrapper.this.mSessionImpl == null) {
                finishInputEvent(inputEvent, false);
                return;
            }
            int iDispatchInputEvent = ITvAdSessionWrapper.this.mSessionImpl.dispatchInputEvent(inputEvent, this);
            if (iDispatchInputEvent != -1) {
                finishInputEvent(inputEvent, iDispatchInputEvent == 1);
            }
        }
    }
}
