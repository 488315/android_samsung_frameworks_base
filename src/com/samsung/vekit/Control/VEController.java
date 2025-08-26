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
    public static final String VERSION = "3.0.3";
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
    ViewMode viewMode;

    public VEController() {
        Looper mainLooper;
        super(null, ElementType.CONTROLLER, 0, "Controller");
        this.isAnimating = false;
        this.isPlaying = false;
        String simpleName = getClass().getSimpleName();
        this.TAG = simpleName;
        Log.i(simpleName, "[VEKit] Version : 3.0.3");
        if (Looper.myLooper() != null) {
            mainLooper = Looper.myLooper();
        } else {
            mainLooper = Looper.getMainLooper() != null ? Looper.getMainLooper() : null;
        }
        this.controllerEventHandler = new ControllerEventHandler(this, mainLooper);
        this.animationEventHandler = new AnimationEventHandler(this, mainLooper);
        this.captureEventHandler = new CaptureEventHandler(mainLooper);
        this.portraitVideoEventHandler = new PortraitVideoEventHandler(this, mainLooper);
        this.context = new VEContext();
        this.analyzer = new VEAnalyzer(this.context, mainLooper);
        this.captureFrameThread = null;
        this.controllerStatusListener = new VEControllerStatusListener() { // from class: com.samsung.vekit.Control.VEController$$ExternalSyntheticLambda0
            @Override // com.samsung.vekit.Listener.VEControllerStatusListener
            public final void onEvent(EventType eventType) {
                this.f$0.m9819lambda$new$0$comsamsungvekitControlVEController(eventType);
            }
        };
        this.exportstatuslistener = null;
        this.animationStatusListener = new AnimationStatusListener() { // from class: com.samsung.vekit.Control.VEController.1
            @Override // com.samsung.vekit.Listener.AnimationStatusListener
            public void onAnimationStarted(Object obj) {
                Log.i(VEController.this.TAG, "onAnimationStarted : UI Animations");
                VEController.this.isAnimating = true;
            }

            @Override // com.samsung.vekit.Listener.AnimationStatusListener
            public void onAnimationUpdated(Object obj) {
                Log.i(VEController.this.TAG, "onAnimationUpdated : UI Animations");
            }

            @Override // com.samsung.vekit.Listener.AnimationStatusListener
            public void onAnimationFinished(Object obj) {
                Log.i(VEController.this.TAG, "onAnimationFinished : UI Animations");
                VEController.this.cancelAnimation();
                VEController.this.isAnimating = false;
            }

            @Override // com.samsung.vekit.Listener.AnimationStatusListener
            public void onAnimationCanceled(Object obj) {
                Log.i(VEController.this.TAG, "onAnimationCanceled : UI Animations");
                VEController.this.clearAnimations();
                VEController.this.isAnimating = false;
            }
        };
    }

    /* renamed from: lambda$new$0$com-samsung-vekit-Control-VEController, reason: not valid java name */
    /* synthetic */ void m9819lambda$new$0$comsamsungvekitControlVEController(EventType eventType) {
        Log.d(this.TAG, "onEvent : EventType : " + eventType.name());
    }

    public VEAnalyzer getAnalyzer() {
        return this.analyzer;
    }

    private class ControllerEventHandler extends Handler {
        private VEController controller;

        public ControllerEventHandler(VEController vEController, Looper looper) {
            super(looper);
            this.controller = vEController;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i(VEController.this.TAG, "handleMessage : msg.what : " + message.what);
            int i = message.what;
            if (i == 1) {
                VEController.this.isPlaying = false;
                if (VEController.this.playerStatusListener != null) {
                    VEController.this.playerStatusListener.onPlaybackCompleted();
                    return;
                }
                return;
            }
            if (i == 2) {
                VEController.this.isPlaying = false;
                if (VEController.this.exportstatuslistener != null) {
                    VEController.this.exportstatuslistener.onExportCompleted();
                    return;
                }
                return;
            }
            if (i == 3) {
                if (VEController.this.playerStatusListener != null) {
                    VEController.this.playerStatusListener.onShowCompleted(((Long) message.obj).longValue());
                    return;
                }
                return;
            }
            if (i == 4) {
                if (VEController.this.exportstatuslistener != null) {
                    VEController.this.exportstatuslistener.onExportStarted();
                    return;
                }
                return;
            }
            if (i == 5) {
                if (VEController.this.exportstatuslistener != null) {
                    VEController.this.exportstatuslistener.onExportPaused();
                    return;
                }
                return;
            }
            if (i == 100) {
                VEController.this.handleError(message.arg1, message.arg2, message.obj);
                return;
            }
            if (i == 101) {
                VEController.this.handleItemError(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                return;
            }
            if (i != 200) {
                if (i != 201) {
                    Log.i(VEController.this.TAG, "handleMessage :Invalid callback msg " + message.what);
                    return;
                }
                VEController.this.handleAudioPcmUpdate(message.arg2, (HashMap) message.obj);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleError(int i, int i2, Object obj) {
        Log.e(this.TAG, "handleExecuteError with errorType : " + i + ", extension : " + i2 + ", extension2 : " + obj);
        ErrorType errorType = ErrorType.values()[i];
        if (this.playerStatusListener != null) {
            if (AnonymousClass2.$SwitchMap$com$samsung$vekit$Common$Type$ErrorType[errorType.ordinal()] == 1) {
                if (i2 == ViewMode.PREVIEW.ordinal()) {
                    pause();
                    this.playerStatusListener.onCodecReclaim(this.seekTime);
                    return;
                } else {
                    pauseExport();
                    this.exportstatuslistener.onCodecReclaim(this.renderTime);
                    return;
                }
            }
            if (i2 == ViewMode.PREVIEW.ordinal()) {
                this.playerStatusListener.onError(errorType, ((Long) obj).longValue());
            } else {
                this.exportstatuslistener.onError(errorType, ((Long) obj).longValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleItemError(int i, int i2, int i3) {
        int i4 = AnonymousClass2.$SwitchMap$com$samsung$vekit$Common$Type$ElementType[ElementType.values()[i2].ordinal()];
        if (i4 == 1) {
            Item item = this.context.getItemManager().get(i3);
            if (item != null) {
                ItemErrorType itemErrorType = ItemErrorType.values()[i];
                Log.i(this.TAG, "handleItemError itemErrorType : " + itemErrorType + ", elementType : " + i2 + ", elementId : " + i3);
                item.onError(itemErrorType);
                return;
            }
            return;
        }
        if (i4 == 2 || i4 == 3 || i4 == 4 || i4 == 5) {
            Log.i(this.TAG, "Unsupported listener error handling ElementType : " + i2 + ", errorType : " + i);
            return;
        }
        Log.i(this.TAG, "Invalid elementType : " + i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAudioPcmUpdate(int i, HashMap<String, PcmInfo> map) {
        Item item = this.context.getItemManager().get(i);
        if (item == null) {
            Log.e(this.TAG, "Item is null");
            return;
        }
        if (map == null) {
            Log.i(this.TAG, "Data is null");
            return;
        }
        PcmInfoListener pcmInfoListener = item.getPcmInfoListener();
        if (pcmInfoListener == null) {
            Log.i(this.TAG, "listener is null");
        } else {
            pcmInfoListener.onUpdate(map);
        }
    }

    private class AnimationEventHandler extends Handler {
        private VEController controller;

        public AnimationEventHandler(VEController vEController, Looper looper) {
            super(looper);
            this.controller = vEController;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Animation<?> animation;
            Animation.AnimationStatus animationStatus = Animation.AnimationStatus.values()[message.what];
            int i = message.arg1;
            if (i != -1) {
                animation = VEController.this.context.getAnimationManager().get(i);
            } else {
                animation = VEController.this.animationStatusListener;
                Log.e(VEController.this.TAG, "UI AnimationListener");
            }
            if (animation == null) {
                Log.e(VEController.this.TAG, "AnimationListener is null");
                return;
            }
            int i2 = AnonymousClass2.$SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[animationStatus.ordinal()];
            if (i2 == 2) {
                animation.onAnimationStarted(message.obj);
                return;
            }
            if (i2 == 3) {
                animation.onAnimationUpdated(message.obj);
            } else if (i2 == 4) {
                animation.onAnimationCanceled(message.obj);
            } else {
                if (i2 != 5) {
                    return;
                }
                animation.onAnimationFinished(message.obj);
            }
        }
    }

    private class CaptureEventHandler extends Handler {
        public CaptureEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            CaptureInfo captureInfo = (CaptureInfo) message.obj;
            CaptureFrameTaskListener listener = captureInfo.getListener();
            if (listener != null) {
                listener.onCaptureFrameReceived(message.what, captureInfo.getBitmap());
            }
        }
    }

    private class PortraitVideoEventHandler extends Handler {
        private VEController controller;

        public PortraitVideoEventHandler(VEController vEController, Looper looper) {
            super(looper);
            this.controller = vEController;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            PortraitVideoStatus portraitVideoStatus = PortraitVideoStatus.values()[message.what];
            int i = message.arg1;
            Item item = this.controller.getContext().getItemManager().get(i);
            if (item == null || item.getItemType() != ItemType.PORTRAIT_VIDEO) {
                Log.e(VEController.this.TAG, "PortraitVideoEventHandler : item is invalid : " + i);
                return;
            }
            PortraitVideoItem portraitVideoItem = (PortraitVideoItem) item;
            int i2 = AnonymousClass2.$SwitchMap$com$samsung$vekit$Common$State$PortraitVideoStatus[portraitVideoStatus.ordinal()];
            if (i2 == 1) {
                portraitVideoItem.onPortraitVideoFrameInfoUpdated((PVFrameInfo) message.obj);
            } else if (i2 == 2) {
                portraitVideoItem.onPortraitVideoKeyFrameUpdated((ArrayList) message.obj);
            } else {
                if (i2 != 3) {
                    return;
                }
                portraitVideoItem.onPortraitVideoError(message.arg2);
            }
        }
    }

    /* renamed from: com.samsung.vekit.Control.VEController$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus;
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$State$PortraitVideoStatus;
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ElementType;
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ErrorType;

        static {
            int[] iArr = new int[PortraitVideoStatus.values().length];
            $SwitchMap$com$samsung$vekit$Common$State$PortraitVideoStatus = iArr;
            try {
                iArr[PortraitVideoStatus.UPDATE_FRAME_INFO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$State$PortraitVideoStatus[PortraitVideoStatus.UPDATE_KEYFRAME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$State$PortraitVideoStatus[PortraitVideoStatus.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[Animation.AnimationStatus.values().length];
            $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus = iArr2;
            try {
                iArr2[Animation.AnimationStatus.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[Animation.AnimationStatus.STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[Animation.AnimationStatus.ANIMATING.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[Animation.AnimationStatus.CANCELED.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[Animation.AnimationStatus.FINISHED.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr3 = new int[ElementType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$ElementType = iArr3;
            try {
                iArr3[ElementType.ITEM.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ElementType[ElementType.CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ElementType[ElementType.LAYER.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ElementType[ElementType.ANIMATION.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ElementType[ElementType.FILTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            int[] iArr4 = new int[ErrorType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$ErrorType = iArr4;
            try {
                iArr4[ErrorType.STOPPED_ON_CODEC_RECLAIMED.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
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

    public void initializeController(Surface surface, int i, int i2, int i3, int i4, ViewMode viewMode, FrameworkType frameworkType) {
        this.context.getNativeInterface().initializeFramework(surface, i, i2, i3, i4, viewMode, frameworkType);
        this.context.initialize();
        this.context.setFrameworkType(frameworkType);
        this.viewMode = viewMode;
        if (viewMode == ViewMode.PREVIEW) {
            if (this.captureFrameThread == null) {
                this.captureFrameThread = new CaptureFrameThread(this.context, this.captureEventHandler);
            }
            this.captureFrameThread.startThread();
        }
    }

    public boolean resizeGraphicBuffers(int i, int i2) {
        return this.context.getNativeInterface().resizeGraphicBuffers(i, i2);
    }

    public void finalizeController() {
        this.context.getNativeInterface().finalizeFramework();
        CaptureFrameThread captureFrameThread = this.captureFrameThread;
        if (captureFrameThread != null) {
            captureFrameThread.stopThread();
            this.captureFrameThread = null;
        }
    }

    public void release() {
        this.context.getNativeInterface().releaseFramework();
    }

    public void updateViewport(Vector4<Integer> vector4) {
        this.context.getNativeInterface().updateViewport(vector4.getX().intValue(), vector4.getY().intValue(), vector4.getZ().intValue(), vector4.getW().intValue());
    }

    public VEController seekTo(long j, SeekType seekType) {
        if (this.isPlaying) {
            Log.e(this.TAG, "show : invalid state [playing]");
            return this;
        }
        this.seekTime = j;
        this.context.getNativeInterface().seekTo(j, seekType);
        return this;
    }

    public long getCurrentMediaPosition() {
        long currentMediaPosition = this.context.getNativeInterface().getCurrentMediaPosition();
        this.seekTime = currentMediaPosition;
        return currentMediaPosition;
    }

    public void captureAnimatedFrame(ImageItem imageItem, int i, int i2, CaptureFrameTaskListener captureFrameTaskListener) {
        CaptureFrameThread captureFrameThread = this.captureFrameThread;
        if (captureFrameThread != null) {
            captureFrameThread.addRequest(imageItem, i, i2, captureFrameTaskListener);
        }
    }

    public void captureLatestFrame(int i, int i2, CaptureFrameTaskListener captureFrameTaskListener) {
        CaptureFrameThread captureFrameThread = this.captureFrameThread;
        if (captureFrameThread != null) {
            captureFrameThread.addRequest(i, i2, captureFrameTaskListener);
        }
    }

    public void captureSuperHDRFrame(Item item, int i, int i2, CaptureFrameTask.CaptureType captureType, CaptureFrameTaskListener captureFrameTaskListener) {
        CaptureFrameThread captureFrameThread = this.captureFrameThread;
        if (captureFrameThread != null) {
            captureFrameThread.addRequest(item, i, i2, captureType, captureFrameTaskListener);
        }
    }

    public void captureSuperHDRFrame(int i, int i2, int i3, int i4, CaptureFrameTask.CaptureType captureType, CaptureFrameTaskListener captureFrameTaskListener) {
        CaptureFrameThread captureFrameThread = this.captureFrameThread;
        if (captureFrameThread != null) {
            captureFrameThread.addRequest(i, i2, i3, i4, captureType, captureFrameTaskListener);
        }
    }

    public long pauseExport() {
        long jPauseExport = this.context.getNativeInterface().pauseExport();
        this.renderTime = jPauseExport;
        return jPauseExport;
    }

    public void resumeExport(long j) {
        this.context.getNativeInterface().resumeExport(j);
    }

    public long getExportPosition() {
        long exportPosition = this.context.getNativeInterface().getExportPosition();
        this.renderTime = exportPosition;
        return exportPosition;
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

    private void onControllerEvent(int i, int i2, int i3, int i4) {
        ControllerEventHandler controllerEventHandler = this.controllerEventHandler;
        if (controllerEventHandler != null) {
            this.controllerEventHandler.sendMessage(controllerEventHandler.obtainMessage(i, i2, i3, Integer.valueOf(i4)));
        }
    }

    private void onControllerEvent(int i, int i2, int i3, long j) {
        ControllerEventHandler controllerEventHandler = this.controllerEventHandler;
        if (controllerEventHandler != null) {
            this.controllerEventHandler.sendMessage(controllerEventHandler.obtainMessage(i, i2, i3, Long.valueOf(j)));
        }
    }

    private void onControllerEvent(int i, int i2, int i3, Object obj) {
        ControllerEventHandler controllerEventHandler = this.controllerEventHandler;
        if (controllerEventHandler != null) {
            this.controllerEventHandler.sendMessage(controllerEventHandler.obtainMessage(i, i2, i3, obj));
        }
    }

    private void onAnalyzeEvent(int i, int i2, int i3, int i4) {
        VEAnalyzer vEAnalyzer = this.analyzer;
        if (vEAnalyzer == null) {
            return;
        }
        vEAnalyzer.onEvent(i, i2, i3, i4);
    }

    private void onAnimationEvent(int i, int i2, int i3, float f) {
        AnimationEventHandler animationEventHandler = this.animationEventHandler;
        if (animationEventHandler != null) {
            this.animationEventHandler.sendMessage(animationEventHandler.obtainMessage(i, i2, i3, Float.valueOf(f)));
        }
    }

    private void onAnimationEvent(int i, int i2, int i3, float[] fArr) {
        AnimationEventHandler animationEventHandler = this.animationEventHandler;
        if (animationEventHandler != null) {
            this.animationEventHandler.sendMessage(animationEventHandler.obtainMessage(i, i2, i3, fArr));
        }
    }

    private void onPortraitVideoEvent(int i, int i2, int i3, Object obj) {
        Log.e(this.TAG, "onPortraitVideoEvent, status : " + i + " id : " + i2);
        PortraitVideoEventHandler portraitVideoEventHandler = this.portraitVideoEventHandler;
        if (portraitVideoEventHandler != null) {
            this.portraitVideoEventHandler.sendMessage(portraitVideoEventHandler.obtainMessage(i, i2, i3, obj));
        }
    }

    public void setPreviewInfo(PreviewInfo previewInfo) {
        Log.i(this.TAG, "setPreviewInfo");
        this.context.getNativeInterface().setPreviewInfo(previewInfo);
    }

    public void setExportInfo(ExportInfo exportInfo) {
        Log.i(this.TAG, "setExportInfo  width : " + exportInfo.getWidth() + " height : " + exportInfo.getHeight() + " fd : " + exportInfo.getFd());
        this.context.getNativeInterface().setExportInfo(exportInfo);
    }

    public void setExportstatuslistener(ExportStatusListener exportStatusListener) {
        this.exportstatuslistener = exportStatusListener;
    }

    public void setPlayerStatusListener(PlayerStatusListener playerStatusListener) {
        this.playerStatusListener = playerStatusListener;
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
        Log.e(this.TAG, "getVEKitVersion version : 3.0.3");
        return VERSION;
    }
}
