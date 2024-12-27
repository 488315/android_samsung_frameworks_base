package com.samsung.vekit.Control;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import com.samsung.vekit.Animation.Animation;
import com.samsung.vekit.Common.Object.CaptureInfo;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.ExportInfo;
import com.samsung.vekit.Common.Object.PVFrameInfo;
import com.samsung.vekit.Common.Object.PcmInfo;
import com.samsung.vekit.Common.Object.PreviewInfo;
import com.samsung.vekit.Common.Object.Vector4;
import com.samsung.vekit.Common.State.PortraitVideoStatus;
import com.samsung.vekit.Common.State.VEKitState;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.ErrorType;
import com.samsung.vekit.Common.Type.EventType;
import com.samsung.vekit.Common.Type.FrameworkType;
import com.samsung.vekit.Common.Type.ItemErrorType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.SeekType;
import com.samsung.vekit.Common.Type.ViewMode;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.ImageItem;
import com.samsung.vekit.Item.Item;
import com.samsung.vekit.Item.PortraitVideoItem;
import com.samsung.vekit.Layer.LayerGroup;
import com.samsung.vekit.Listener.AnimationStatusListener;
import com.samsung.vekit.Listener.CaptureFrameTaskListener;
import com.samsung.vekit.Listener.ExportStatusListener;
import com.samsung.vekit.Listener.ItemStatusListener;
import com.samsung.vekit.Listener.PcmInfoListener;
import com.samsung.vekit.Listener.PlayerStatusListener;
import com.samsung.vekit.Listener.VEControllerStatusListener;
import com.samsung.vekit.Task.CaptureFrameTask;
import com.samsung.vekit.Task.CaptureFrameThread;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class VEController extends Element {
    public static final int UI_ANIMATION_MANAGER = -1;
    public static final String VERSION = "2.0.16";
    private final String TAG;
    private VEAnalyzer analyzer;
    private AnimationEventHandler animationEventHandler;
    AnimationStatusListener animationStatusListener;
    private CaptureEventHandler captureEventHandler;
    CaptureFrameThread captureFrameThread;
    private ControllerEventHandler controllerEventHandler;
    VEControllerStatusListener controllerStatusListener;
    ExportStatusListener exportstatuslistener;
    boolean isAnimating;
    boolean isPlaying;
    PlayerStatusListener playerStatusListener;
    private PortraitVideoEventHandler portraitVideoEventHandler;
    long renderTime;
    long seekTime;

    public VEController() {
        super(null, ElementType.CONTROLLER, 0, "Controller");
        Looper looper;
        this.isAnimating = false;
        this.isPlaying = false;
        this.TAG = getClass().getSimpleName();
        Log.i(this.TAG, "[VEKit] Version : 2.0.16");
        if (Looper.myLooper() != null) {
            looper = Looper.myLooper();
        } else {
            Looper looper2 = Looper.getMainLooper();
            if (looper2 != null) {
                looper = Looper.getMainLooper();
            } else {
                looper = null;
            }
        }
        this.controllerEventHandler = new ControllerEventHandler(this, looper);
        this.animationEventHandler = new AnimationEventHandler(this, looper);
        this.captureEventHandler = new CaptureEventHandler(looper);
        this.portraitVideoEventHandler = new PortraitVideoEventHandler(this, looper);
        this.context = new VEContext();
        this.analyzer = new VEAnalyzer(this.context, looper);
        this.captureFrameThread = null;
        this.controllerStatusListener = new VEControllerStatusListener() { // from class: com.samsung.vekit.Control.VEController$$ExternalSyntheticLambda0
            @Override // com.samsung.vekit.Listener.VEControllerStatusListener
            public final void onEvent(EventType eventType) {
                VEController.this.m9386lambda$new$0$comsamsungvekitControlVEController(eventType);
            }
        };
        this.exportstatuslistener = null;
        this.animationStatusListener = new AnimationStatusListener() { // from class: com.samsung.vekit.Control.VEController.1
            @Override // com.samsung.vekit.Listener.AnimationStatusListener
            public void onAnimationStarted(Object interpolatedValue) {
                Log.i(VEController.this.TAG, "onAnimationStarted : UI Animations");
                VEController.this.isAnimating = true;
            }

            @Override // com.samsung.vekit.Listener.AnimationStatusListener
            public void onAnimationUpdated(Object interpolatedValue) {
                Log.i(VEController.this.TAG, "onAnimationUpdated : UI Animations");
            }

            @Override // com.samsung.vekit.Listener.AnimationStatusListener
            public void onAnimationFinished(Object interpolatedValue) {
                Log.i(VEController.this.TAG, "onAnimationFinished : UI Animations");
                VEController.this.cancelAnimation();
                VEController.this.isAnimating = false;
            }

            @Override // com.samsung.vekit.Listener.AnimationStatusListener
            public void onAnimationCanceled(Object interpolatedValue) {
                Log.i(VEController.this.TAG, "onAnimationCanceled : UI Animations");
                VEController.this.clearAnimations();
                VEController.this.isAnimating = false;
            }
        };
    }

    /* renamed from: lambda$new$0$com-samsung-vekit-Control-VEController, reason: not valid java name */
    /* synthetic */ void m9386lambda$new$0$comsamsungvekitControlVEController(EventType eventType) {
        Log.d(this.TAG, "onEvent : EventType : " + eventType.name());
    }

    public VEAnalyzer getAnalyzer() {
        return this.analyzer;
    }

    private class ControllerEventHandler extends Handler {
        private VEController controller;

        public ControllerEventHandler(VEController controller, Looper looper) {
            super(looper);
            this.controller = controller;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Log.i(VEController.this.TAG, "handleMessage : msg.what : " + msg.what);
            switch (msg.what) {
                case 1:
                    VEController.this.isPlaying = false;
                    if (VEController.this.playerStatusListener != null) {
                        VEController.this.playerStatusListener.onPlaybackCompleted();
                        break;
                    }
                    break;
                case 2:
                    VEController.this.isPlaying = false;
                    if (VEController.this.exportstatuslistener != null) {
                        VEController.this.exportstatuslistener.onExportCompleted();
                        break;
                    }
                    break;
                case 3:
                    if (VEController.this.playerStatusListener != null) {
                        VEController.this.playerStatusListener.onShowCompleted(((Long) msg.obj).longValue());
                        break;
                    }
                    break;
                case 4:
                    if (VEController.this.exportstatuslistener != null) {
                        VEController.this.exportstatuslistener.onExportStarted();
                        break;
                    }
                    break;
                case 5:
                    if (VEController.this.exportstatuslistener != null) {
                        VEController.this.exportstatuslistener.onExportPaused();
                        break;
                    }
                    break;
                case 100:
                    VEController.this.handleError(msg.arg1, msg.obj);
                    break;
                case 101:
                    VEController.this.handleItemError(msg.arg1, msg.arg2, ((Integer) msg.obj).intValue());
                    break;
                case 200:
                    break;
                case 201:
                    VEController.this.handleAudioPcmUpdate(msg.arg2, (HashMap) msg.obj);
                    break;
                default:
                    Log.i(VEController.this.TAG, "handleMessage :Invalid callback msg " + msg.what);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleError(int errorType, Object extension) {
        Log.e(this.TAG, "handleExecuteError with errorType : " + errorType + ", extension : " + extension);
        ErrorType type = ErrorType.values()[errorType];
        if (this.playerStatusListener != null) {
            switch (type) {
                case STOPPED_ON_CODEC_RECLAIMED:
                    this.playerStatusListener.onCodecReclaim(((Long) extension).longValue());
                    break;
                default:
                    this.playerStatusListener.onError(type, ((Long) extension).longValue());
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleItemError(int errorType, int elementType, int elementId) {
        ElementType type = ElementType.values()[elementType];
        switch (type) {
            case ITEM:
                ItemStatusListener listener = this.context.getItemManager().get(elementId);
                if (listener != null) {
                    ItemErrorType itemErrorType = ItemErrorType.values()[errorType];
                    Log.i(this.TAG, "handleItemError itemErrorType : " + itemErrorType + ", elementType : " + elementType + ", elementId : " + elementId);
                    listener.onError(itemErrorType);
                    break;
                }
                break;
            case CONTENT:
            case LAYER:
            case ANIMATION:
            case FILTER:
                Log.i(this.TAG, "Unsupported listener error handling ElementType : " + elementType + ", errorType : " + errorType);
                break;
            default:
                Log.i(this.TAG, "Invalid elementType : " + elementType);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAudioPcmUpdate(int elementId, HashMap<String, PcmInfo> data) {
        Item item = this.context.getItemManager().get(elementId);
        if (item == null) {
            Log.e(this.TAG, "Item is null");
            return;
        }
        if (data == null) {
            Log.i(this.TAG, "Data is null");
            return;
        }
        PcmInfoListener listener = item.getPcmInfoListener();
        if (listener == null) {
            Log.i(this.TAG, "listener is null");
        } else {
            listener.onUpdate(data);
        }
    }

    private class AnimationEventHandler extends Handler {
        private VEController controller;

        public AnimationEventHandler(VEController controller, Looper looper) {
            super(looper);
            this.controller = controller;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Animation<?> listener;
            Animation.AnimationStatus status = Animation.AnimationStatus.values()[msg.what];
            int animationId = msg.arg1;
            if (animationId != -1) {
                listener = VEController.this.context.getAnimationManager().get(animationId);
            } else {
                listener = VEController.this.animationStatusListener;
                Log.e(VEController.this.TAG, "UI AnimationListener");
            }
            if (listener == null) {
                Log.e(VEController.this.TAG, "AnimationListener is null");
            }
            switch (status) {
                case STARTED:
                    listener.onAnimationStarted(msg.obj);
                    break;
                case ANIMATING:
                    listener.onAnimationUpdated(msg.obj);
                    break;
                case CANCELED:
                    listener.onAnimationCanceled(msg.obj);
                    break;
                case FINISHED:
                    listener.onAnimationFinished(msg.obj);
                    break;
            }
        }
    }

    private class CaptureEventHandler extends Handler {
        public CaptureEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            CaptureInfo captureInfo = (CaptureInfo) msg.obj;
            CaptureFrameTaskListener listener = captureInfo.getListener();
            if (listener != null) {
                listener.onCaptureFrameReceived(msg.what, captureInfo.getBitmap());
            }
        }
    }

    private class PortraitVideoEventHandler extends Handler {
        private VEController controller;

        public PortraitVideoEventHandler(VEController controller, Looper looper) {
            super(looper);
            this.controller = controller;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            PortraitVideoStatus status = PortraitVideoStatus.values()[msg.what];
            int itemId = msg.arg1;
            Item item = this.controller.getContext().getItemManager().get(itemId);
            if (item == null || item.getItemType() != ItemType.PORTRAIT_VIDEO) {
                Log.e(VEController.this.TAG, "PortraitVideoEventHandler : item is invalid : " + itemId);
                return;
            }
            PortraitVideoItem targetItem = (PortraitVideoItem) item;
            switch (status) {
                case UPDATE_FRAME_INFO:
                    targetItem.onPortraitVideoFrameInfoUpdated((PVFrameInfo) msg.obj);
                    break;
                case UPDATE_KEYFRAME:
                    targetItem.onPortraitVideoKeyFrameUpdated((ArrayList) msg.obj);
                    break;
                case ERROR:
                    targetItem.onPortraitVideoError(msg.arg2);
                    break;
            }
        }
    }

    public void create() {
        try {
            this.context.getNativeInterface().createFramework(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void attachAnimation(Animation animation) {
        this.context.getAnimationManager().attachAnimation(animation);
    }

    public void detachAnimation(Animation animation) {
        this.context.getAnimationManager().detachAnimation(animation);
    }

    public void clearAnimations() {
        this.context.getAnimationManager().clearAnimations();
    }

    public void initializeController(Surface surface, int graphicBufferWidth, int graphicBufferHeight, int viewportWidth, int viewportHeight, ViewMode viewMode, FrameworkType frameworkType) {
        this.context.getNativeInterface().initializeFramework(surface, graphicBufferWidth, graphicBufferHeight, viewportWidth, viewportHeight, viewMode, frameworkType);
        this.context.initialize();
        this.context.setFrameworkType(frameworkType);
        if (viewMode == ViewMode.PREVIEW) {
            if (this.captureFrameThread == null) {
                this.captureFrameThread = new CaptureFrameThread(this.context, this.captureEventHandler);
            }
            this.captureFrameThread.startThread();
        }
    }

    public void finalizeController() {
        this.context.getNativeInterface().finalizeFramework();
        if (this.captureFrameThread != null) {
            this.captureFrameThread.stopThread();
            this.captureFrameThread = null;
        }
    }

    public void release() {
        this.context.getNativeInterface().releaseFramework();
    }

    public void updateViewport(Vector4<Integer> viewport) {
        this.context.getNativeInterface().updateViewport(viewport.getX().intValue(), viewport.getY().intValue(), viewport.getZ().intValue(), viewport.getW().intValue());
    }

    public VEController seekTo(long millisecond, SeekType seekType) {
        if (this.isPlaying) {
            Log.e(this.TAG, "show : invalid state [playing]");
            return this;
        }
        this.seekTime = millisecond;
        this.context.getNativeInterface().seekTo(millisecond, seekType);
        return this;
    }

    public long getCurrentMediaPosition() {
        this.seekTime = this.context.getNativeInterface().getCurrentMediaPosition();
        return this.seekTime;
    }

    public void captureAnimatedFrame(ImageItem item, int outputWidth, int outputHeight, CaptureFrameTaskListener listener) {
        if (this.captureFrameThread != null) {
            this.captureFrameThread.addRequest(item, outputWidth, outputHeight, listener);
        }
    }

    public void captureLatestFrame(int outputWidth, int outputHeight, CaptureFrameTaskListener listener) {
        if (this.captureFrameThread != null) {
            this.captureFrameThread.addRequest(outputWidth, outputHeight, listener);
        }
    }

    public void captureSuperHDRFrame(Item item, int outputWidth, int outputHeight, CaptureFrameTask.CaptureType captureType, CaptureFrameTaskListener listener) {
        if (this.captureFrameThread != null) {
            this.captureFrameThread.addRequest(item, outputWidth, outputHeight, captureType, listener);
        }
    }

    public void captureSuperHDRFrame(int outputWidth, int outputHeight, int centerX, int centerY, CaptureFrameTask.CaptureType captureType, CaptureFrameTaskListener listener) {
        if (this.captureFrameThread != null) {
            this.captureFrameThread.addRequest(outputWidth, outputHeight, centerX, centerY, captureType, listener);
        }
    }

    public long pauseExport() {
        this.renderTime = this.context.getNativeInterface().pauseExport();
        return this.renderTime;
    }

    public void resumeExport(long renderTime) {
        this.context.getNativeInterface().resumeExport(renderTime);
    }

    public long getExportPosition() {
        this.renderTime = this.context.getNativeInterface().getExportPosition();
        return this.renderTime;
    }

    public boolean isAnimating() {
        return this.isAnimating;
    }

    public boolean isPlaying() {
        return this.isAnimating;
    }

    @Override // com.samsung.vekit.Common.Object.Element
    public void update() {
        super.update();
    }

    public void show() {
        if (this.isPlaying) {
            Log.e(this.TAG, "show : invalid state [playing]");
        } else {
            this.context.getNativeInterface().show();
        }
    }

    public void animate() {
        this.context.getNativeInterface().animate();
        this.isAnimating = true;
    }

    public void cancelAnimation() {
        this.isAnimating = false;
        this.context.getNativeInterface().cancelAnimation();
    }

    public void play() {
        if (this.context == null || this.context.getLayerGroup() == null) {
            return;
        }
        LayerGroup layerGroup = this.context.getLayerGroup();
        layerGroup.calculateTotalDuration();
        layerGroup.update();
        this.isPlaying = true;
        this.context.getNativeInterface().play();
    }

    public void pause() {
        this.context.getNativeInterface().pause();
        this.isPlaying = false;
        this.seekTime = getCurrentMediaPosition();
    }

    public void stop() {
        this.context.getNativeInterface().stop();
        this.isPlaying = false;
    }

    private void onControllerEvent(int event, int arg1, int arg2, int arg3) {
        if (this.controllerEventHandler != null) {
            Message m = this.controllerEventHandler.obtainMessage(event, arg1, arg2, Integer.valueOf(arg3));
            this.controllerEventHandler.sendMessage(m);
        }
    }

    private void onControllerEvent(int event, int arg1, int arg2, long arg3) {
        if (this.controllerEventHandler != null) {
            Message m = this.controllerEventHandler.obtainMessage(event, arg1, arg2, Long.valueOf(arg3));
            this.controllerEventHandler.sendMessage(m);
        }
    }

    private void onControllerEvent(int event, int arg1, int arg2, Object arg3) {
        if (this.controllerEventHandler != null) {
            Message m = this.controllerEventHandler.obtainMessage(event, arg1, arg2, arg3);
            this.controllerEventHandler.sendMessage(m);
        }
    }

    private void onAnalyzeEvent(int event, int arg1, int arg2, int arg3) {
        if (this.analyzer == null) {
            return;
        }
        this.analyzer.onEvent(event, arg1, arg2, arg3);
    }

    private void onAnimationEvent(int status, int animationId, int type, float value) {
        if (this.animationEventHandler != null) {
            Message m = this.animationEventHandler.obtainMessage(status, animationId, type, Float.valueOf(value));
            this.animationEventHandler.sendMessage(m);
        }
    }

    private void onAnimationEvent(int status, int animationId, int type, float[] value) {
        if (this.animationEventHandler != null) {
            Message m = this.animationEventHandler.obtainMessage(status, animationId, type, value);
            this.animationEventHandler.sendMessage(m);
        }
    }

    private void onPortraitVideoEvent(int status, int itemId, int type, Object object) {
        Log.e(this.TAG, "onPortraitVideoEvent, status : " + status + " id : " + itemId);
        if (this.portraitVideoEventHandler != null) {
            Message m = this.portraitVideoEventHandler.obtainMessage(status, itemId, type, object);
            this.portraitVideoEventHandler.sendMessage(m);
        }
    }

    public void setPreviewInfo(PreviewInfo previewInfo) {
        Log.i(this.TAG, "setPreviewInfo");
        this.context.getNativeInterface().setPreviewInfo(previewInfo);
    }

    public void setExportInfo(ExportInfo info) {
        Log.i(this.TAG, "setExportInfo  width : " + info.getWidth() + " height : " + info.getHeight() + " fd : " + info.getFd());
        this.context.getNativeInterface().setExportInfo(info);
    }

    public void setExportstatuslistener(ExportStatusListener listener) {
        this.exportstatuslistener = listener;
    }

    public void setPlayerStatusListener(PlayerStatusListener listener) {
        this.playerStatusListener = listener;
    }

    public VEKitState getState() {
        return this.context.getState();
    }

    private class ControllerCallbackMsg {
        public static final int AUDIO_PCM_UPDATE = 201;
        public static final int ERROR = 100;
        public static final int ERROR_ON_ITEM = 101;
        public static final int EXPORT_COMPLETE = 2;
        public static final int EXPORT_PAUSED = 5;
        public static final int EXPORT_STARTED = 4;
        public static final int INFO = 200;
        public static final int PLAYBACK_COMPLETE = 1;
        public static final int SHOW_COMPLETE = 3;

        private ControllerCallbackMsg() {
        }
    }

    public String getVEKitVersion() {
        Log.e(this.TAG, "getVEKitVersion version : 2.0.16");
        return VERSION;
    }
}
