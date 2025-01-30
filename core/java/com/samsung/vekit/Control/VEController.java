package com.samsung.vekit.Control;

import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import android.util.Log;
import android.view.Surface;
import com.samsung.vekit.Animation.Animation;
import com.samsung.vekit.Common.Object.CaptureInfo;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.ExportInfo;
import com.samsung.vekit.Common.Object.Vector4;
import com.samsung.vekit.Common.State.VEKitState;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.ErrorType;
import com.samsung.vekit.Common.Type.EventType;
import com.samsung.vekit.Common.Type.FrameworkType;
import com.samsung.vekit.Common.Type.ItemErrorType;
import com.samsung.vekit.Common.Type.SeekType;
import com.samsung.vekit.Common.Type.ViewMode;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.ImageItem;
import com.samsung.vekit.Layer.LayerGroup;
import com.samsung.vekit.Listener.AnimationStatusListener;
import com.samsung.vekit.Listener.CaptureFrameTaskListener;
import com.samsung.vekit.Listener.ExportStatusListener;
import com.samsung.vekit.Listener.ItemStatusListener;
import com.samsung.vekit.Listener.PlayerStatusListener;
import com.samsung.vekit.Listener.VEControllerStatusListener;
import com.samsung.vekit.Task.CaptureFrameThread;

/* loaded from: classes6.dex */
public class VEController extends Element {
  public static final int UI_ANIMATION_MANAGER = -1;
  public static final String VERSION = "1.0.15";
  private final String TAG;
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
  long renderTime;
  long seekTime;

  public VEController() {
    super(null, ElementType.CONTROLLER, 0, "Controller");
    this.isAnimating = false;
    this.isPlaying = false;
    String simpleName = getClass().getSimpleName();
    this.TAG = simpleName;
    Log.m98i(simpleName, "[VEKit] Version : 1.0.15");
    Looper looper = Looper.myLooper();
    if (looper != null) {
      this.controllerEventHandler = new ControllerEventHandler(this, looper);
      this.animationEventHandler = new AnimationEventHandler(this, looper);
      this.captureEventHandler = new CaptureEventHandler(looper);
    } else {
      Looper looper2 = Looper.getMainLooper();
      if (looper2 != null) {
        this.controllerEventHandler = new ControllerEventHandler(this, looper2);
        this.animationEventHandler = new AnimationEventHandler(this, looper2);
        this.captureEventHandler = new CaptureEventHandler(looper2);
      } else {
        this.controllerEventHandler = null;
        this.animationEventHandler = null;
        this.captureEventHandler = null;
      }
    }
    this.context = new VEContext();
    this.captureFrameThread = null;
    this.controllerStatusListener =
        new VEControllerStatusListener() { // from class:
                                           // com.samsung.vekit.Control.VEController$$ExternalSyntheticLambda0
          @Override // com.samsung.vekit.Listener.VEControllerStatusListener
          public final void onEvent(EventType eventType) {
            VEController.this.m9377lambda$new$0$comsamsungvekitControlVEController(eventType);
          }
        };
    this.exportstatuslistener = null;
    this.animationStatusListener =
        new AnimationStatusListener() { // from class: com.samsung.vekit.Control.VEController.1
          @Override // com.samsung.vekit.Listener.AnimationStatusListener
          public void onAnimationStarted(Object interpolatedValue) {
            Log.m98i(VEController.this.TAG, "onAnimationStarted : UI Animations");
            VEController.this.isAnimating = true;
          }

          @Override // com.samsung.vekit.Listener.AnimationStatusListener
          public void onAnimationUpdated(Object interpolatedValue) {
            Log.m98i(VEController.this.TAG, "onAnimationUpdated : UI Animations");
          }

          @Override // com.samsung.vekit.Listener.AnimationStatusListener
          public void onAnimationFinished(Object interpolatedValue) {
            Log.m98i(VEController.this.TAG, "onAnimationFinished : UI Animations");
            VEController.this.cancelAnimation();
            VEController.this.isAnimating = false;
          }

          @Override // com.samsung.vekit.Listener.AnimationStatusListener
          public void onAnimationCanceled(Object interpolatedValue) {
            Log.m98i(VEController.this.TAG, "onAnimationCanceled : UI Animations");
            VEController.this.clearAnimations();
            VEController.this.isAnimating = false;
          }
        };
  }

  /* renamed from: lambda$new$0$com-samsung-vekit-Control-VEController, reason: not valid java name */
  /* synthetic */ void m9377lambda$new$0$comsamsungvekitControlVEController(EventType eventType) {
    Log.m94d(this.TAG, "onEvent : EventType : " + eventType.name());
  }

  private class ControllerEventHandler extends Handler {
    private VEController controller;

    public ControllerEventHandler(VEController controller, Looper looper) {
      super(looper);
      this.controller = controller;
    }

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      Log.m98i(VEController.this.TAG, "handleMessage : msg.what : " + msg.what);
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
        default:
          Log.m98i(VEController.this.TAG, "handleMessage :Invalid callback msg " + msg.what);
          break;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleError(int errorType, Object extension) {
    Log.m96e(
        this.TAG,
        "handleExecuteError with errorType : " + errorType + ", extension : " + extension);
    ErrorType type = ErrorType.values()[errorType];
    if (this.playerStatusListener != null) {
      switch (C53812.$SwitchMap$com$samsung$vekit$Common$Type$ErrorType[type.ordinal()]) {
        case 1:
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
    switch (C53812.$SwitchMap$com$samsung$vekit$Common$Type$ElementType[type.ordinal()]) {
      case 1:
        ItemStatusListener listener = this.context.getItemManager().get(elementId);
        if (listener != null) {
          ItemErrorType itemErrorType = ItemErrorType.values()[errorType];
          Log.m98i(
              this.TAG,
              "handleItemError itemErrorType : "
                  + itemErrorType
                  + ", elementType : "
                  + elementType
                  + ", elementId : "
                  + elementId);
          listener.onError(itemErrorType);
          break;
        }
        break;
      case 2:
      case 3:
      case 4:
      case 5:
        Log.m98i(
            this.TAG,
            "Unsupported listener error handling ElementType : "
                + elementType
                + ", errorType : "
                + errorType);
        break;
      default:
        Log.m98i(this.TAG, "Invalid elementType : " + elementType);
        break;
    }
  }

  private class AnimationEventHandler extends Handler {
    private VEController controller;

    public AnimationEventHandler(VEController controller, Looper looper) {
      super(looper);
      this.controller = controller;
    }

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      Animation<?> listener;
      Animation.AnimationStatus status = Animation.AnimationStatus.values()[msg.what];
      int animationId = msg.arg1;
      if (animationId != -1) {
        listener = VEController.this.context.getAnimationManager().get(animationId);
      } else {
        listener = VEController.this.animationStatusListener;
        Log.m96e(VEController.this.TAG, "UI AnimationListener");
      }
      if (listener == null) {
        Log.m96e(VEController.this.TAG, "AnimationListener is null");
      }
      switch (C53812.$SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[
          status.ordinal()]) {
        case 2:
          listener.onAnimationStarted(msg.obj);
          break;
        case 3:
          listener.onAnimationUpdated(msg.obj);
          break;
        case 4:
          listener.onAnimationCanceled(msg.obj);
          break;
        case 5:
          listener.onAnimationFinished(msg.obj);
          break;
      }
    }
  }

  /* renamed from: com.samsung.vekit.Control.VEController$2 */
  static /* synthetic */ class C53812 {
    static final /* synthetic */ int[]
        $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus;
    static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ElementType;
    static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ErrorType;

    static {
      int[] iArr = new int[Animation.AnimationStatus.values().length];
      $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus = iArr;
      try {
        iArr[Animation.AnimationStatus.INITIALIZED.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[
                Animation.AnimationStatus.STARTED.ordinal()] =
            2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[
                Animation.AnimationStatus.ANIMATING.ordinal()] =
            3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[
                Animation.AnimationStatus.CANCELED.ordinal()] =
            4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Animation$Animation$AnimationStatus[
                Animation.AnimationStatus.FINISHED.ordinal()] =
            5;
      } catch (NoSuchFieldError e5) {
      }
      int[] iArr2 = new int[ElementType.values().length];
      $SwitchMap$com$samsung$vekit$Common$Type$ElementType = iArr2;
      try {
        iArr2[ElementType.ITEM.ordinal()] = 1;
      } catch (NoSuchFieldError e6) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ElementType[ElementType.CONTENT.ordinal()] = 2;
      } catch (NoSuchFieldError e7) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ElementType[ElementType.LAYER.ordinal()] = 3;
      } catch (NoSuchFieldError e8) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ElementType[ElementType.ANIMATION.ordinal()] = 4;
      } catch (NoSuchFieldError e9) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ElementType[ElementType.FILTER.ordinal()] = 5;
      } catch (NoSuchFieldError e10) {
      }
      int[] iArr3 = new int[ErrorType.values().length];
      $SwitchMap$com$samsung$vekit$Common$Type$ErrorType = iArr3;
      try {
        iArr3[ErrorType.STOPPED_ON_CODEC_RECLAIMED.ordinal()] = 1;
      } catch (NoSuchFieldError e11) {
      }
    }
  }

  private class CaptureEventHandler extends Handler {
    public CaptureEventHandler(Looper looper) {
      super(looper);
    }

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      CaptureInfo captureInfo = (CaptureInfo) msg.obj;
      CaptureFrameTaskListener listener = captureInfo.getListener();
      if (listener != null) {
        listener.onCaptureFrameReceived(msg.what, captureInfo.getBitmap());
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

  public void initializeController(
      Surface surface,
      int graphicBufferWidth,
      int graphicBufferHeight,
      int viewportWidth,
      int viewportHeight,
      ViewMode viewMode,
      FrameworkType frameworkType) {
    this.context
        .getNativeInterface()
        .initializeFramework(
            surface,
            graphicBufferWidth,
            graphicBufferHeight,
            viewportWidth,
            viewportHeight,
            viewMode,
            frameworkType);
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
    CaptureFrameThread captureFrameThread = this.captureFrameThread;
    if (captureFrameThread != null) {
      captureFrameThread.stopThread();
      this.captureFrameThread = null;
    }
  }

  public void release() {
    this.context.getNativeInterface().releaseFramework();
  }

  public void updateViewport(Vector4<Integer> viewport) {
    this.context
        .getNativeInterface()
        .updateViewport(
            viewport.getX().intValue(),
            viewport.getY().intValue(),
            viewport.getZ().intValue(),
            viewport.getW().intValue());
  }

  public VEController seekTo(long millisecond, SeekType seekType) {
    if (this.isPlaying) {
      Log.m96e(this.TAG, "show : invalid state [playing]");
      return this;
    }
    this.seekTime = millisecond;
    this.context.getNativeInterface().seekTo(millisecond, seekType);
    return this;
  }

  public long getCurrentMediaPosition() {
    long currentMediaPosition = this.context.getNativeInterface().getCurrentMediaPosition();
    this.seekTime = currentMediaPosition;
    return currentMediaPosition;
  }

  public void captureAnimatedFrame(
      ImageItem item, int outputWidth, int outputHeight, CaptureFrameTaskListener listener) {
    CaptureFrameThread captureFrameThread = this.captureFrameThread;
    if (captureFrameThread != null) {
      captureFrameThread.addRequest(item, outputWidth, outputHeight, listener);
    }
  }

  public void captureLatestFrame(
      int outputWidth, int outputHeight, CaptureFrameTaskListener listener) {
    CaptureFrameThread captureFrameThread = this.captureFrameThread;
    if (captureFrameThread != null) {
      captureFrameThread.addRequest(outputWidth, outputHeight, listener);
    }
  }

  public long pauseExport() {
    long pauseExport = this.context.getNativeInterface().pauseExport();
    this.renderTime = pauseExport;
    return pauseExport;
  }

  public void resumeExport(long renderTime) {
    this.context.getNativeInterface().resumeExport(renderTime);
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
      Log.m96e(this.TAG, "show : invalid state [playing]");
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
    ControllerEventHandler controllerEventHandler = this.controllerEventHandler;
    if (controllerEventHandler != null) {
      Message m = controllerEventHandler.obtainMessage(event, arg1, arg2, Integer.valueOf(arg3));
      this.controllerEventHandler.sendMessage(m);
    }
  }

  private void onControllerEvent(int event, int arg1, int arg2, long arg3) {
    ControllerEventHandler controllerEventHandler = this.controllerEventHandler;
    if (controllerEventHandler != null) {
      Message m = controllerEventHandler.obtainMessage(event, arg1, arg2, Long.valueOf(arg3));
      this.controllerEventHandler.sendMessage(m);
    }
  }

  private void onAnimationEvent(int status, int animationId, int type, float value) {
    AnimationEventHandler animationEventHandler = this.animationEventHandler;
    if (animationEventHandler != null) {
      Message m =
          animationEventHandler.obtainMessage(status, animationId, type, Float.valueOf(value));
      this.animationEventHandler.sendMessage(m);
    }
  }

  private void onAnimationEvent(int status, int animationId, int type, float[] value) {
    AnimationEventHandler animationEventHandler = this.animationEventHandler;
    if (animationEventHandler != null) {
      Message m = animationEventHandler.obtainMessage(status, animationId, type, value);
      this.animationEventHandler.sendMessage(m);
    }
  }

  public void setExportInfo(ExportInfo info) {
    Log.m98i(
        this.TAG,
        "setExportInfo  width : "
            + info.getWidth()
            + " height : "
            + info.getHeight()
            + " fd : "
            + info.getFd());
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
    public static final int ERROR = 100;
    public static final int ERROR_ON_ITEM = 101;
    public static final int EXPORT_COMPLETE = 2;
    public static final int EXPORT_PAUSED = 5;
    public static final int EXPORT_STARTED = 4;
    public static final int INFO = 200;
    public static final int PLAYBACK_COMPLETE = 1;
    public static final int SHOW_COMPLETE = 3;

    private ControllerCallbackMsg() {}
  }

  public String getVEKitVersion() {
    Log.m96e(this.TAG, "getVEKitVersion version : 1.0.15");
    return VERSION;
  }
}
