package android.view;

import android.content.ClipData;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteCallback;
import android.p009os.RemoteException;
import android.util.MergedConfiguration;
import android.window.ClientWindowFrames;
import android.window.OnBackInvokedCallbackInfo;
import android.window.WindowContainerToken;
import java.util.List;

/* loaded from: classes4.dex */
public interface IWindowSession extends IInterface {
  int addToDisplay(
      IWindow iWindow,
      WindowManager.LayoutParams layoutParams,
      int i,
      int i2,
      int i3,
      InputChannel inputChannel,
      InsetsState insetsState,
      InsetsSourceControl.Array array,
      Rect rect,
      float[] fArr)
      throws RemoteException;

  int addToDisplayAsUser(
      IWindow iWindow,
      WindowManager.LayoutParams layoutParams,
      int i,
      int i2,
      int i3,
      int i4,
      InputChannel inputChannel,
      InsetsState insetsState,
      InsetsSourceControl.Array array,
      Rect rect,
      float[] fArr)
      throws RemoteException;

  int addToDisplayWithoutInputChannel(
      IWindow iWindow,
      WindowManager.LayoutParams layoutParams,
      int i,
      int i2,
      InsetsState insetsState,
      Rect rect,
      float[] fArr)
      throws RemoteException;

  void cancelDragAndDrop(IBinder iBinder, boolean z) throws RemoteException;

  boolean cancelDraw(IWindow iWindow) throws RemoteException;

  void clearTouchableRegion(IWindow iWindow) throws RemoteException;

  void clearTspDeadzone(IWindow iWindow) throws RemoteException;

  void dragRecipientEntered(IWindow iWindow) throws RemoteException;

  void dragRecipientExited(IWindow iWindow) throws RemoteException;

  boolean dropForAccessibility(IWindow iWindow, int i, int i2) throws RemoteException;

  void finishDrawing(IWindow iWindow, SurfaceControl.Transaction transaction, int i)
      throws RemoteException;

  void finishMovingTask(IWindow iWindow) throws RemoteException;

  void generateDisplayHash(IWindow iWindow, Rect rect, String str, RemoteCallback remoteCallback)
      throws RemoteException;

  IWindowId getWindowId(IBinder iBinder) throws RemoteException;

  void grantEmbeddedWindowFocus(IWindow iWindow, IBinder iBinder, boolean z) throws RemoteException;

  void grantInputChannel(
      int i,
      SurfaceControl surfaceControl,
      IWindow iWindow,
      IBinder iBinder,
      int i2,
      int i3,
      int i4,
      int i5,
      IBinder iBinder2,
      IBinder iBinder3,
      String str,
      InputChannel inputChannel)
      throws RemoteException;

  void grantInputChannelWithTaskToken(
      int i,
      SurfaceControl surfaceControl,
      IWindow iWindow,
      IBinder iBinder,
      int i2,
      int i3,
      int i4,
      int i5,
      IBinder iBinder2,
      IBinder iBinder3,
      String str,
      InputChannel inputChannel,
      int i6,
      WindowContainerToken windowContainerToken)
      throws RemoteException;

  void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) throws RemoteException;

  boolean outOfMemory(IWindow iWindow) throws RemoteException;

  void performClipDataUpdate(ClipData clipData) throws RemoteException;

  IBinder performDrag(
      IWindow iWindow,
      int i,
      SurfaceControl surfaceControl,
      int i2,
      float f,
      float f2,
      float f3,
      float f4,
      ClipData clipData)
      throws RemoteException;

  IBinder performDragWithArea(
      IWindow iWindow,
      int i,
      SurfaceControl surfaceControl,
      int i2,
      float f,
      float f2,
      float f3,
      float f4,
      ClipData clipData,
      RectF rectF,
      Point point)
      throws RemoteException;

  boolean performHapticFeedback(int i, boolean z) throws RemoteException;

  void performHapticFeedbackAsync(int i, boolean z) throws RemoteException;

  void pokeDrawLock(IBinder iBinder) throws RemoteException;

  int relayout(
      IWindow iWindow,
      WindowManager.LayoutParams layoutParams,
      int i,
      int i2,
      int i3,
      int i4,
      int i5,
      int i6,
      ClientWindowFrames clientWindowFrames,
      MergedConfiguration mergedConfiguration,
      SurfaceControl surfaceControl,
      InsetsState insetsState,
      InsetsSourceControl.Array array,
      Bundle bundle)
      throws RemoteException;

  void relayoutAsync(
      IWindow iWindow,
      WindowManager.LayoutParams layoutParams,
      int i,
      int i2,
      int i3,
      int i4,
      int i5,
      int i6)
      throws RemoteException;

  void remove(IWindow iWindow) throws RemoteException;

  void removeWithTaskToken(IWindow iWindow, WindowContainerToken windowContainerToken)
      throws RemoteException;

  void reportDropResult(IWindow iWindow, boolean z) throws RemoteException;

  void reportKeepClearAreasChanged(IWindow iWindow, List<Rect> list, List<Rect> list2)
      throws RemoteException;

  void reportSystemGestureExclusionChanged(IWindow iWindow, List<Rect> list) throws RemoteException;

  Bundle sendWallpaperCommand(
      IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle, boolean z)
      throws RemoteException;

  void setInsets(IWindow iWindow, int i, Rect rect, Rect rect2, Region region)
      throws RemoteException;

  void setKeyguardWallpaperTouchAllowed(IWindow iWindow, boolean z) throws RemoteException;

  void setOnBackInvokedCallbackInfo(
      IWindow iWindow, OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws RemoteException;

  void setShouldZoomOutWallpaper(IBinder iBinder, boolean z) throws RemoteException;

  void setTspDeadzone(IWindow iWindow, Bundle bundle) throws RemoteException;

  void setTspNoteMode(IWindow iWindow, boolean z) throws RemoteException;

  void setWallpaperDisplayOffset(IBinder iBinder, int i, int i2) throws RemoteException;

  void setWallpaperPosition(IBinder iBinder, float f, float f2, float f3, float f4)
      throws RemoteException;

  void setWallpaperZoomOut(IBinder iBinder, float f) throws RemoteException;

  boolean startMovingTask(IWindow iWindow, float f, float f2) throws RemoteException;

  boolean transferEmbeddedTouchFocusToHost(IWindow iWindow) throws RemoteException;

  void updateInputChannel(
      IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region)
      throws RemoteException;

  void updateInputChannelWithPointerRegion(
      IBinder iBinder,
      int i,
      SurfaceControl surfaceControl,
      int i2,
      int i3,
      int i4,
      Region region,
      Region region2)
      throws RemoteException;

  void updatePointerIcon(IWindow iWindow) throws RemoteException;

  void updateRequestedVisibleTypes(IWindow iWindow, int i) throws RemoteException;

  void updateTapExcludeRegion(IWindow iWindow, Region region) throws RemoteException;

  void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) throws RemoteException;

  void wallpaperOffsetsComplete(IBinder iBinder) throws RemoteException;

  public static class Default implements IWindowSession {
    @Override // android.view.IWindowSession
    public int addToDisplay(
        IWindow window,
        WindowManager.LayoutParams attrs,
        int viewVisibility,
        int layerStackId,
        int requestedVisibleTypes,
        InputChannel outInputChannel,
        InsetsState insetsState,
        InsetsSourceControl.Array activeControls,
        Rect attachedFrame,
        float[] sizeCompatScale)
        throws RemoteException {
      return 0;
    }

    @Override // android.view.IWindowSession
    public int addToDisplayAsUser(
        IWindow window,
        WindowManager.LayoutParams attrs,
        int viewVisibility,
        int layerStackId,
        int userId,
        int requestedVisibleTypes,
        InputChannel outInputChannel,
        InsetsState insetsState,
        InsetsSourceControl.Array activeControls,
        Rect attachedFrame,
        float[] sizeCompatScale)
        throws RemoteException {
      return 0;
    }

    @Override // android.view.IWindowSession
    public int addToDisplayWithoutInputChannel(
        IWindow window,
        WindowManager.LayoutParams attrs,
        int viewVisibility,
        int layerStackId,
        InsetsState insetsState,
        Rect attachedFrame,
        float[] sizeCompatScale)
        throws RemoteException {
      return 0;
    }

    @Override // android.view.IWindowSession
    public void remove(IWindow window) throws RemoteException {}

    @Override // android.view.IWindowSession
    public int relayout(
        IWindow window,
        WindowManager.LayoutParams attrs,
        int requestedWidth,
        int requestedHeight,
        int viewVisibility,
        int flags,
        int seq,
        int lastSyncSeqId,
        ClientWindowFrames outFrames,
        MergedConfiguration outMergedConfiguration,
        SurfaceControl outSurfaceControl,
        InsetsState insetsState,
        InsetsSourceControl.Array activeControls,
        Bundle bundle)
        throws RemoteException {
      return 0;
    }

    @Override // android.view.IWindowSession
    public void relayoutAsync(
        IWindow window,
        WindowManager.LayoutParams attrs,
        int requestedWidth,
        int requestedHeight,
        int viewVisibility,
        int flags,
        int seq,
        int lastSyncSeqId)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public boolean outOfMemory(IWindow window) throws RemoteException {
      return false;
    }

    @Override // android.view.IWindowSession
    public void setInsets(
        IWindow window,
        int touchableInsets,
        Rect contentInsets,
        Rect visibleInsets,
        Region touchableRegion)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void finishDrawing(
        IWindow window, SurfaceControl.Transaction postDrawTransaction, int seqId)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public boolean performHapticFeedback(int effectId, boolean always) throws RemoteException {
      return false;
    }

    @Override // android.view.IWindowSession
    public void performHapticFeedbackAsync(int effectId, boolean always) throws RemoteException {}

    @Override // android.view.IWindowSession
    public IBinder performDrag(
        IWindow window,
        int flags,
        SurfaceControl surface,
        int touchSource,
        float touchX,
        float touchY,
        float thumbCenterX,
        float thumbCenterY,
        ClipData data)
        throws RemoteException {
      return null;
    }

    @Override // android.view.IWindowSession
    public IBinder performDragWithArea(
        IWindow window,
        int flags,
        SurfaceControl surface,
        int touchSource,
        float touchX,
        float touchY,
        float thumbCenterX,
        float thumbCenterY,
        ClipData data,
        RectF selectedArea,
        Point viewLocation)
        throws RemoteException {
      return null;
    }

    @Override // android.view.IWindowSession
    public boolean dropForAccessibility(IWindow window, int x, int y) throws RemoteException {
      return false;
    }

    @Override // android.view.IWindowSession
    public void reportDropResult(IWindow window, boolean consumed) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void cancelDragAndDrop(IBinder dragToken, boolean skipAnimation)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void dragRecipientEntered(IWindow window) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void dragRecipientExited(IWindow window) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void setWallpaperPosition(
        IBinder windowToken, float x, float y, float xstep, float ystep) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void setWallpaperZoomOut(IBinder windowToken, float scale) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void setShouldZoomOutWallpaper(IBinder windowToken, boolean shouldZoom)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void wallpaperOffsetsComplete(IBinder window) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void setWallpaperDisplayOffset(IBinder windowToken, int x, int y)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public Bundle sendWallpaperCommand(
        IBinder window, String action, int x, int y, int z, Bundle extras, boolean sync)
        throws RemoteException {
      return null;
    }

    @Override // android.view.IWindowSession
    public void wallpaperCommandComplete(IBinder window, Bundle result) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void onRectangleOnScreenRequested(IBinder token, Rect rectangle)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public IWindowId getWindowId(IBinder window) throws RemoteException {
      return null;
    }

    @Override // android.view.IWindowSession
    public void pokeDrawLock(IBinder window) throws RemoteException {}

    @Override // android.view.IWindowSession
    public boolean startMovingTask(IWindow window, float startX, float startY)
        throws RemoteException {
      return false;
    }

    @Override // android.view.IWindowSession
    public void finishMovingTask(IWindow window) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void updatePointerIcon(IWindow window) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void updateTapExcludeRegion(IWindow window, Region region) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void updateRequestedVisibleTypes(IWindow window, int requestedVisibleTypes)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void reportSystemGestureExclusionChanged(IWindow window, List<Rect> exclusionRects)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void reportKeepClearAreasChanged(
        IWindow window, List<Rect> restricted, List<Rect> unrestricted) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void grantInputChannel(
        int displayId,
        SurfaceControl surface,
        IWindow window,
        IBinder hostInputToken,
        int flags,
        int privateFlags,
        int inputFeatures,
        int type,
        IBinder windowToken,
        IBinder focusGrantToken,
        String inputHandleName,
        InputChannel outInputChannel)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void grantInputChannelWithTaskToken(
        int displayId,
        SurfaceControl surface,
        IWindow window,
        IBinder hostInputToken,
        int flags,
        int privateFlags,
        int inputFeatures,
        int type,
        IBinder windowToken,
        IBinder focusGrantToken,
        String inputHandleName,
        InputChannel outInputChannel,
        int surfaceInset,
        WindowContainerToken taskToken)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void removeWithTaskToken(IWindow window, WindowContainerToken taskToken)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void updateInputChannel(
        IBinder channelToken,
        int displayId,
        SurfaceControl surface,
        int flags,
        int privateFlags,
        int inputFeatures,
        Region region)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void updateInputChannelWithPointerRegion(
        IBinder channelToken,
        int displayId,
        SurfaceControl surface,
        int flags,
        int privateFlags,
        int inputFeatures,
        Region region,
        Region pointerRegion)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void grantEmbeddedWindowFocus(IWindow window, IBinder inputToken, boolean grantFocus)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void generateDisplayHash(
        IWindow window, Rect boundsInWindow, String hashAlgorithm, RemoteCallback callback)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void setOnBackInvokedCallbackInfo(IWindow window, OnBackInvokedCallbackInfo callbackInfo)
        throws RemoteException {}

    @Override // android.view.IWindowSession
    public void clearTouchableRegion(IWindow window) throws RemoteException {}

    @Override // android.view.IWindowSession
    public boolean cancelDraw(IWindow window) throws RemoteException {
      return false;
    }

    @Override // android.view.IWindowSession
    public boolean transferEmbeddedTouchFocusToHost(IWindow embeddedWindow) throws RemoteException {
      return false;
    }

    @Override // android.view.IWindowSession
    public void setTspDeadzone(IWindow window, Bundle deadzone) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void clearTspDeadzone(IWindow window) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void setTspNoteMode(IWindow window, boolean isTspNoteMode) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void performClipDataUpdate(ClipData data) throws RemoteException {}

    @Override // android.view.IWindowSession
    public void setKeyguardWallpaperTouchAllowed(IWindow window, boolean allowed)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IWindowSession {
    public static final String DESCRIPTOR = "android.view.IWindowSession";
    static final int TRANSACTION_addToDisplay = 1;
    static final int TRANSACTION_addToDisplayAsUser = 2;
    static final int TRANSACTION_addToDisplayWithoutInputChannel = 3;
    static final int TRANSACTION_cancelDragAndDrop = 16;
    static final int TRANSACTION_cancelDraw = 45;
    static final int TRANSACTION_clearTouchableRegion = 44;
    static final int TRANSACTION_clearTspDeadzone = 48;
    static final int TRANSACTION_dragRecipientEntered = 17;
    static final int TRANSACTION_dragRecipientExited = 18;
    static final int TRANSACTION_dropForAccessibility = 14;
    static final int TRANSACTION_finishDrawing = 9;
    static final int TRANSACTION_finishMovingTask = 30;
    static final int TRANSACTION_generateDisplayHash = 42;
    static final int TRANSACTION_getWindowId = 27;
    static final int TRANSACTION_grantEmbeddedWindowFocus = 41;
    static final int TRANSACTION_grantInputChannel = 36;
    static final int TRANSACTION_grantInputChannelWithTaskToken = 37;
    static final int TRANSACTION_onRectangleOnScreenRequested = 26;
    static final int TRANSACTION_outOfMemory = 7;
    static final int TRANSACTION_performClipDataUpdate = 50;
    static final int TRANSACTION_performDrag = 12;
    static final int TRANSACTION_performDragWithArea = 13;
    static final int TRANSACTION_performHapticFeedback = 10;
    static final int TRANSACTION_performHapticFeedbackAsync = 11;
    static final int TRANSACTION_pokeDrawLock = 28;
    static final int TRANSACTION_relayout = 5;
    static final int TRANSACTION_relayoutAsync = 6;
    static final int TRANSACTION_remove = 4;
    static final int TRANSACTION_removeWithTaskToken = 38;
    static final int TRANSACTION_reportDropResult = 15;
    static final int TRANSACTION_reportKeepClearAreasChanged = 35;
    static final int TRANSACTION_reportSystemGestureExclusionChanged = 34;
    static final int TRANSACTION_sendWallpaperCommand = 24;
    static final int TRANSACTION_setInsets = 8;
    static final int TRANSACTION_setKeyguardWallpaperTouchAllowed = 51;
    static final int TRANSACTION_setOnBackInvokedCallbackInfo = 43;
    static final int TRANSACTION_setShouldZoomOutWallpaper = 21;
    static final int TRANSACTION_setTspDeadzone = 47;
    static final int TRANSACTION_setTspNoteMode = 49;
    static final int TRANSACTION_setWallpaperDisplayOffset = 23;
    static final int TRANSACTION_setWallpaperPosition = 19;
    static final int TRANSACTION_setWallpaperZoomOut = 20;
    static final int TRANSACTION_startMovingTask = 29;
    static final int TRANSACTION_transferEmbeddedTouchFocusToHost = 46;
    static final int TRANSACTION_updateInputChannel = 39;
    static final int TRANSACTION_updateInputChannelWithPointerRegion = 40;
    static final int TRANSACTION_updatePointerIcon = 31;
    static final int TRANSACTION_updateRequestedVisibleTypes = 33;
    static final int TRANSACTION_updateTapExcludeRegion = 32;
    static final int TRANSACTION_wallpaperCommandComplete = 25;
    static final int TRANSACTION_wallpaperOffsetsComplete = 22;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IWindowSession asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IWindowSession)) {
        return (IWindowSession) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "addToDisplay";
        case 2:
          return "addToDisplayAsUser";
        case 3:
          return "addToDisplayWithoutInputChannel";
        case 4:
          return "remove";
        case 5:
          return "relayout";
        case 6:
          return "relayoutAsync";
        case 7:
          return "outOfMemory";
        case 8:
          return "setInsets";
        case 9:
          return "finishDrawing";
        case 10:
          return "performHapticFeedback";
        case 11:
          return "performHapticFeedbackAsync";
        case 12:
          return "performDrag";
        case 13:
          return "performDragWithArea";
        case 14:
          return "dropForAccessibility";
        case 15:
          return "reportDropResult";
        case 16:
          return "cancelDragAndDrop";
        case 17:
          return "dragRecipientEntered";
        case 18:
          return "dragRecipientExited";
        case 19:
          return "setWallpaperPosition";
        case 20:
          return "setWallpaperZoomOut";
        case 21:
          return "setShouldZoomOutWallpaper";
        case 22:
          return "wallpaperOffsetsComplete";
        case 23:
          return "setWallpaperDisplayOffset";
        case 24:
          return "sendWallpaperCommand";
        case 25:
          return "wallpaperCommandComplete";
        case 26:
          return "onRectangleOnScreenRequested";
        case 27:
          return "getWindowId";
        case 28:
          return "pokeDrawLock";
        case 29:
          return "startMovingTask";
        case 30:
          return "finishMovingTask";
        case 31:
          return "updatePointerIcon";
        case 32:
          return "updateTapExcludeRegion";
        case 33:
          return "updateRequestedVisibleTypes";
        case 34:
          return "reportSystemGestureExclusionChanged";
        case 35:
          return "reportKeepClearAreasChanged";
        case 36:
          return "grantInputChannel";
        case 37:
          return "grantInputChannelWithTaskToken";
        case 38:
          return "removeWithTaskToken";
        case 39:
          return "updateInputChannel";
        case 40:
          return "updateInputChannelWithPointerRegion";
        case 41:
          return "grantEmbeddedWindowFocus";
        case 42:
          return "generateDisplayHash";
        case 43:
          return "setOnBackInvokedCallbackInfo";
        case 44:
          return "clearTouchableRegion";
        case 45:
          return "cancelDraw";
        case 46:
          return "transferEmbeddedTouchFocusToHost";
        case 47:
          return "setTspDeadzone";
        case 48:
          return "clearTspDeadzone";
        case 49:
          return "setTspNoteMode";
        case 50:
          return "performClipDataUpdate";
        case 51:
          return "setKeyguardWallpaperTouchAllowed";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      float[] _arg9;
      float[] _arg10;
      float[] _arg6;
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IWindow _arg0 = IWindow.Stub.asInterface(data.readStrongBinder());
              WindowManager.LayoutParams _arg1 =
                  (WindowManager.LayoutParams)
                      data.readTypedObject(WindowManager.LayoutParams.CREATOR);
              int _arg2 = data.readInt();
              int _arg3 = data.readInt();
              int _arg4 = data.readInt();
              InputChannel _arg5 = new InputChannel();
              InsetsState _arg62 = new InsetsState();
              InsetsSourceControl.Array _arg7 = new InsetsSourceControl.Array();
              Rect _arg8 = new Rect();
              int _arg9_length = data.readInt();
              if (_arg9_length < 0) {
                _arg9 = null;
              } else {
                float[] _arg92 = new float[_arg9_length];
                _arg9 = _arg92;
              }
              data.enforceNoDataAvail();
              float[] _arg93 = _arg9;
              int _result =
                  addToDisplay(
                      _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg62, _arg7, _arg8, _arg93);
              reply.writeNoException();
              reply.writeInt(_result);
              reply.writeTypedObject(_arg5, 1);
              reply.writeTypedObject(_arg62, 1);
              reply.writeTypedObject(_arg7, 1);
              reply.writeTypedObject(_arg8, 1);
              reply.writeFloatArray(_arg93);
              return true;
            case 2:
              IWindow _arg02 = IWindow.Stub.asInterface(data.readStrongBinder());
              WindowManager.LayoutParams _arg12 =
                  (WindowManager.LayoutParams)
                      data.readTypedObject(WindowManager.LayoutParams.CREATOR);
              int _arg22 = data.readInt();
              int _arg32 = data.readInt();
              int _arg42 = data.readInt();
              int _arg52 = data.readInt();
              InputChannel _arg63 = new InputChannel();
              InsetsState _arg72 = new InsetsState();
              InsetsSourceControl.Array _arg82 = new InsetsSourceControl.Array();
              Rect _arg94 = new Rect();
              int _arg10_length = data.readInt();
              if (_arg10_length < 0) {
                _arg10 = null;
              } else {
                float[] _arg102 = new float[_arg10_length];
                _arg10 = _arg102;
              }
              data.enforceNoDataAvail();
              float[] _arg103 = _arg10;
              int _result2 =
                  addToDisplayAsUser(
                      _arg02, _arg12, _arg22, _arg32, _arg42, _arg52, _arg63, _arg72, _arg82,
                      _arg94, _arg103);
              reply.writeNoException();
              reply.writeInt(_result2);
              reply.writeTypedObject(_arg63, 1);
              reply.writeTypedObject(_arg72, 1);
              reply.writeTypedObject(_arg82, 1);
              reply.writeTypedObject(_arg94, 1);
              reply.writeFloatArray(_arg103);
              return true;
            case 3:
              IWindow _arg03 = IWindow.Stub.asInterface(data.readStrongBinder());
              WindowManager.LayoutParams _arg13 =
                  (WindowManager.LayoutParams)
                      data.readTypedObject(WindowManager.LayoutParams.CREATOR);
              int _arg23 = data.readInt();
              int _arg33 = data.readInt();
              InsetsState _arg43 = new InsetsState();
              Rect _arg53 = new Rect();
              int _arg6_length = data.readInt();
              if (_arg6_length < 0) {
                _arg6 = null;
              } else {
                _arg6 = new float[_arg6_length];
              }
              data.enforceNoDataAvail();
              float[] _arg64 = _arg6;
              int _result3 =
                  addToDisplayWithoutInputChannel(
                      _arg03, _arg13, _arg23, _arg33, _arg43, _arg53, _arg64);
              reply.writeNoException();
              reply.writeInt(_result3);
              reply.writeTypedObject(_arg43, 1);
              reply.writeTypedObject(_arg53, 1);
              reply.writeFloatArray(_arg64);
              return true;
            case 4:
              IWindow _arg04 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              remove(_arg04);
              reply.writeNoException();
              return true;
            case 5:
              IWindow _arg05 = IWindow.Stub.asInterface(data.readStrongBinder());
              WindowManager.LayoutParams _arg14 =
                  (WindowManager.LayoutParams)
                      data.readTypedObject(WindowManager.LayoutParams.CREATOR);
              int _arg24 = data.readInt();
              int _arg34 = data.readInt();
              int _arg44 = data.readInt();
              int _arg54 = data.readInt();
              int _arg65 = data.readInt();
              int _arg73 = data.readInt();
              ClientWindowFrames _arg83 = new ClientWindowFrames();
              MergedConfiguration _arg95 = new MergedConfiguration();
              SurfaceControl _arg104 = new SurfaceControl();
              InsetsState _arg11 = new InsetsState();
              InsetsSourceControl.Array _arg122 = new InsetsSourceControl.Array();
              Bundle _arg132 = new Bundle();
              data.enforceNoDataAvail();
              int _result4 =
                  relayout(
                      _arg05, _arg14, _arg24, _arg34, _arg44, _arg54, _arg65, _arg73, _arg83,
                      _arg95, _arg104, _arg11, _arg122, _arg132);
              reply.writeNoException();
              reply.writeInt(_result4);
              reply.writeTypedObject(_arg83, 1);
              reply.writeTypedObject(_arg95, 1);
              reply.writeTypedObject(_arg104, 1);
              reply.writeTypedObject(_arg11, 1);
              reply.writeTypedObject(_arg122, 1);
              reply.writeTypedObject(_arg132, 1);
              return true;
            case 6:
              IWindow _arg06 = IWindow.Stub.asInterface(data.readStrongBinder());
              WindowManager.LayoutParams _arg15 =
                  (WindowManager.LayoutParams)
                      data.readTypedObject(WindowManager.LayoutParams.CREATOR);
              int _arg25 = data.readInt();
              int _arg35 = data.readInt();
              int _arg45 = data.readInt();
              int _arg55 = data.readInt();
              int _arg66 = data.readInt();
              int _arg74 = data.readInt();
              data.enforceNoDataAvail();
              relayoutAsync(_arg06, _arg15, _arg25, _arg35, _arg45, _arg55, _arg66, _arg74);
              return true;
            case 7:
              IWindow _arg07 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result5 = outOfMemory(_arg07);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 8:
              IWindow _arg08 = IWindow.Stub.asInterface(data.readStrongBinder());
              int _arg16 = data.readInt();
              Rect _arg26 = (Rect) data.readTypedObject(Rect.CREATOR);
              Rect _arg36 = (Rect) data.readTypedObject(Rect.CREATOR);
              Region _arg46 = (Region) data.readTypedObject(Region.CREATOR);
              data.enforceNoDataAvail();
              setInsets(_arg08, _arg16, _arg26, _arg36, _arg46);
              return true;
            case 9:
              IWindow _arg09 = IWindow.Stub.asInterface(data.readStrongBinder());
              SurfaceControl.Transaction _arg17 =
                  (SurfaceControl.Transaction)
                      data.readTypedObject(SurfaceControl.Transaction.CREATOR);
              int _arg27 = data.readInt();
              data.enforceNoDataAvail();
              finishDrawing(_arg09, _arg17, _arg27);
              return true;
            case 10:
              int _arg010 = data.readInt();
              boolean _arg18 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result6 = performHapticFeedback(_arg010, _arg18);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 11:
              int _arg011 = data.readInt();
              boolean _arg19 = data.readBoolean();
              data.enforceNoDataAvail();
              performHapticFeedbackAsync(_arg011, _arg19);
              return true;
            case 12:
              IWindow _arg012 = IWindow.Stub.asInterface(data.readStrongBinder());
              int _arg110 = data.readInt();
              SurfaceControl _arg28 = (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
              int _arg37 = data.readInt();
              float _arg47 = data.readFloat();
              float _arg56 = data.readFloat();
              float _arg67 = data.readFloat();
              float _arg75 = data.readFloat();
              ClipData _arg84 = (ClipData) data.readTypedObject(ClipData.CREATOR);
              data.enforceNoDataAvail();
              IBinder _result7 =
                  performDrag(
                      _arg012, _arg110, _arg28, _arg37, _arg47, _arg56, _arg67, _arg75, _arg84);
              reply.writeNoException();
              reply.writeStrongBinder(_result7);
              return true;
            case 13:
              IWindow _arg013 = IWindow.Stub.asInterface(data.readStrongBinder());
              int _arg111 = data.readInt();
              SurfaceControl _arg29 = (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
              int _arg38 = data.readInt();
              float _arg48 = data.readFloat();
              float _arg57 = data.readFloat();
              float _arg68 = data.readFloat();
              float _arg76 = data.readFloat();
              ClipData _arg85 = (ClipData) data.readTypedObject(ClipData.CREATOR);
              RectF _arg96 = (RectF) data.readTypedObject(RectF.CREATOR);
              Point _arg105 = (Point) data.readTypedObject(Point.CREATOR);
              data.enforceNoDataAvail();
              IBinder _result8 =
                  performDragWithArea(
                      _arg013, _arg111, _arg29, _arg38, _arg48, _arg57, _arg68, _arg76, _arg85,
                      _arg96, _arg105);
              reply.writeNoException();
              reply.writeStrongBinder(_result8);
              return true;
            case 14:
              IWindow _arg014 = IWindow.Stub.asInterface(data.readStrongBinder());
              int _arg112 = data.readInt();
              int _arg210 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result9 = dropForAccessibility(_arg014, _arg112, _arg210);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 15:
              IWindow _arg015 = IWindow.Stub.asInterface(data.readStrongBinder());
              boolean _arg113 = data.readBoolean();
              data.enforceNoDataAvail();
              reportDropResult(_arg015, _arg113);
              return true;
            case 16:
              IBinder _arg016 = data.readStrongBinder();
              boolean _arg114 = data.readBoolean();
              data.enforceNoDataAvail();
              cancelDragAndDrop(_arg016, _arg114);
              return true;
            case 17:
              IWindow _arg017 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              dragRecipientEntered(_arg017);
              return true;
            case 18:
              IWindow _arg018 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              dragRecipientExited(_arg018);
              return true;
            case 19:
              IBinder _arg019 = data.readStrongBinder();
              float _arg115 = data.readFloat();
              float _arg211 = data.readFloat();
              float _arg39 = data.readFloat();
              float _arg49 = data.readFloat();
              data.enforceNoDataAvail();
              setWallpaperPosition(_arg019, _arg115, _arg211, _arg39, _arg49);
              return true;
            case 20:
              IBinder _arg020 = data.readStrongBinder();
              float _arg116 = data.readFloat();
              data.enforceNoDataAvail();
              setWallpaperZoomOut(_arg020, _arg116);
              return true;
            case 21:
              IBinder _arg021 = data.readStrongBinder();
              boolean _arg117 = data.readBoolean();
              data.enforceNoDataAvail();
              setShouldZoomOutWallpaper(_arg021, _arg117);
              return true;
            case 22:
              IBinder _arg022 = data.readStrongBinder();
              data.enforceNoDataAvail();
              wallpaperOffsetsComplete(_arg022);
              return true;
            case 23:
              IBinder _arg023 = data.readStrongBinder();
              int _arg118 = data.readInt();
              int _arg212 = data.readInt();
              data.enforceNoDataAvail();
              setWallpaperDisplayOffset(_arg023, _arg118, _arg212);
              return true;
            case 24:
              IBinder _arg024 = data.readStrongBinder();
              String _arg119 = data.readString();
              int _arg213 = data.readInt();
              int _arg310 = data.readInt();
              int _arg410 = data.readInt();
              Bundle _arg58 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              boolean _arg69 = data.readBoolean();
              data.enforceNoDataAvail();
              Bundle _result10 =
                  sendWallpaperCommand(_arg024, _arg119, _arg213, _arg310, _arg410, _arg58, _arg69);
              reply.writeNoException();
              reply.writeTypedObject(_result10, 1);
              return true;
            case 25:
              IBinder _arg025 = data.readStrongBinder();
              Bundle _arg120 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              wallpaperCommandComplete(_arg025, _arg120);
              return true;
            case 26:
              IBinder _arg026 = data.readStrongBinder();
              Rect _arg121 = (Rect) data.readTypedObject(Rect.CREATOR);
              data.enforceNoDataAvail();
              onRectangleOnScreenRequested(_arg026, _arg121);
              return true;
            case 27:
              IBinder _arg027 = data.readStrongBinder();
              data.enforceNoDataAvail();
              IWindowId _result11 = getWindowId(_arg027);
              reply.writeNoException();
              reply.writeStrongInterface(_result11);
              return true;
            case 28:
              IBinder _arg028 = data.readStrongBinder();
              data.enforceNoDataAvail();
              pokeDrawLock(_arg028);
              reply.writeNoException();
              return true;
            case 29:
              IWindow _arg029 = IWindow.Stub.asInterface(data.readStrongBinder());
              float _arg123 = data.readFloat();
              float _arg214 = data.readFloat();
              data.enforceNoDataAvail();
              boolean _result12 = startMovingTask(_arg029, _arg123, _arg214);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 30:
              IWindow _arg030 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              finishMovingTask(_arg030);
              return true;
            case 31:
              IWindow _arg031 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              updatePointerIcon(_arg031);
              return true;
            case 32:
              IWindow _arg032 = IWindow.Stub.asInterface(data.readStrongBinder());
              Region _arg124 = (Region) data.readTypedObject(Region.CREATOR);
              data.enforceNoDataAvail();
              updateTapExcludeRegion(_arg032, _arg124);
              return true;
            case 33:
              IWindow _arg033 = IWindow.Stub.asInterface(data.readStrongBinder());
              int _arg125 = data.readInt();
              data.enforceNoDataAvail();
              updateRequestedVisibleTypes(_arg033, _arg125);
              return true;
            case 34:
              IWindow _arg034 = IWindow.Stub.asInterface(data.readStrongBinder());
              List<Rect> _arg126 = data.createTypedArrayList(Rect.CREATOR);
              data.enforceNoDataAvail();
              reportSystemGestureExclusionChanged(_arg034, _arg126);
              return true;
            case 35:
              IWindow _arg035 = IWindow.Stub.asInterface(data.readStrongBinder());
              List<Rect> _arg127 = data.createTypedArrayList(Rect.CREATOR);
              List<Rect> _arg215 = data.createTypedArrayList(Rect.CREATOR);
              data.enforceNoDataAvail();
              reportKeepClearAreasChanged(_arg035, _arg127, _arg215);
              return true;
            case 36:
              int _arg036 = data.readInt();
              SurfaceControl _arg128 =
                  (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
              IWindow _arg216 = IWindow.Stub.asInterface(data.readStrongBinder());
              IBinder _arg311 = data.readStrongBinder();
              int _arg411 = data.readInt();
              int _arg59 = data.readInt();
              int _arg610 = data.readInt();
              int _arg77 = data.readInt();
              IBinder _arg86 = data.readStrongBinder();
              IBinder _arg97 = data.readStrongBinder();
              String _arg106 = data.readString();
              InputChannel _arg1110 = new InputChannel();
              data.enforceNoDataAvail();
              grantInputChannel(
                  _arg036, _arg128, _arg216, _arg311, _arg411, _arg59, _arg610, _arg77, _arg86,
                  _arg97, _arg106, _arg1110);
              reply.writeNoException();
              reply.writeTypedObject(_arg1110, 1);
              return true;
            case 37:
              int _arg037 = data.readInt();
              SurfaceControl _arg129 =
                  (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
              IWindow _arg217 = IWindow.Stub.asInterface(data.readStrongBinder());
              IBinder _arg312 = data.readStrongBinder();
              int _arg412 = data.readInt();
              int _arg510 = data.readInt();
              int _arg611 = data.readInt();
              int _arg78 = data.readInt();
              IBinder _arg87 = data.readStrongBinder();
              IBinder _arg98 = data.readStrongBinder();
              String _arg107 = data.readString();
              InputChannel _arg1111 = new InputChannel();
              int _arg1210 = data.readInt();
              WindowContainerToken _arg133 =
                  (WindowContainerToken) data.readTypedObject(WindowContainerToken.CREATOR);
              data.enforceNoDataAvail();
              grantInputChannelWithTaskToken(
                  _arg037, _arg129, _arg217, _arg312, _arg412, _arg510, _arg611, _arg78, _arg87,
                  _arg98, _arg107, _arg1111, _arg1210, _arg133);
              reply.writeNoException();
              reply.writeTypedObject(_arg1111, 1);
              return true;
            case 38:
              IWindow _arg038 = IWindow.Stub.asInterface(data.readStrongBinder());
              WindowContainerToken _arg130 =
                  (WindowContainerToken) data.readTypedObject(WindowContainerToken.CREATOR);
              data.enforceNoDataAvail();
              removeWithTaskToken(_arg038, _arg130);
              reply.writeNoException();
              return true;
            case 39:
              IBinder _arg039 = data.readStrongBinder();
              int _arg131 = data.readInt();
              SurfaceControl _arg218 =
                  (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
              int _arg313 = data.readInt();
              int _arg413 = data.readInt();
              int _arg511 = data.readInt();
              Region _arg612 = (Region) data.readTypedObject(Region.CREATOR);
              data.enforceNoDataAvail();
              updateInputChannel(_arg039, _arg131, _arg218, _arg313, _arg413, _arg511, _arg612);
              return true;
            case 40:
              IBinder _arg040 = data.readStrongBinder();
              int _arg134 = data.readInt();
              SurfaceControl _arg219 =
                  (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
              int _arg314 = data.readInt();
              int _arg414 = data.readInt();
              int _arg512 = data.readInt();
              Region _arg613 = (Region) data.readTypedObject(Region.CREATOR);
              Region _arg79 = (Region) data.readTypedObject(Region.CREATOR);
              data.enforceNoDataAvail();
              updateInputChannelWithPointerRegion(
                  _arg040, _arg134, _arg219, _arg314, _arg414, _arg512, _arg613, _arg79);
              return true;
            case 41:
              IWindow _arg041 = IWindow.Stub.asInterface(data.readStrongBinder());
              IBinder _arg135 = data.readStrongBinder();
              boolean _arg220 = data.readBoolean();
              data.enforceNoDataAvail();
              grantEmbeddedWindowFocus(_arg041, _arg135, _arg220);
              reply.writeNoException();
              return true;
            case 42:
              IWindow _arg042 = IWindow.Stub.asInterface(data.readStrongBinder());
              Rect _arg136 = (Rect) data.readTypedObject(Rect.CREATOR);
              String _arg221 = data.readString();
              RemoteCallback _arg315 =
                  (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
              data.enforceNoDataAvail();
              generateDisplayHash(_arg042, _arg136, _arg221, _arg315);
              return true;
            case 43:
              IWindow _arg043 = IWindow.Stub.asInterface(data.readStrongBinder());
              OnBackInvokedCallbackInfo _arg137 =
                  (OnBackInvokedCallbackInfo)
                      data.readTypedObject(OnBackInvokedCallbackInfo.CREATOR);
              data.enforceNoDataAvail();
              setOnBackInvokedCallbackInfo(_arg043, _arg137);
              return true;
            case 44:
              IWindow _arg044 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              clearTouchableRegion(_arg044);
              reply.writeNoException();
              return true;
            case 45:
              IWindow _arg045 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result13 = cancelDraw(_arg045);
              reply.writeNoException();
              reply.writeBoolean(_result13);
              return true;
            case 46:
              IWindow _arg046 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result14 = transferEmbeddedTouchFocusToHost(_arg046);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 47:
              IWindow _arg047 = IWindow.Stub.asInterface(data.readStrongBinder());
              Bundle _arg138 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              setTspDeadzone(_arg047, _arg138);
              reply.writeNoException();
              return true;
            case 48:
              IWindow _arg048 = IWindow.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              clearTspDeadzone(_arg048);
              reply.writeNoException();
              return true;
            case 49:
              IWindow _arg049 = IWindow.Stub.asInterface(data.readStrongBinder());
              boolean _arg139 = data.readBoolean();
              data.enforceNoDataAvail();
              setTspNoteMode(_arg049, _arg139);
              return true;
            case 50:
              ClipData _arg050 = (ClipData) data.readTypedObject(ClipData.CREATOR);
              data.enforceNoDataAvail();
              performClipDataUpdate(_arg050);
              return true;
            case 51:
              IWindow _arg051 = IWindow.Stub.asInterface(data.readStrongBinder());
              boolean _arg140 = data.readBoolean();
              data.enforceNoDataAvail();
              setKeyguardWallpaperTouchAllowed(_arg051, _arg140);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IWindowSession {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.view.IWindowSession
      public int addToDisplay(
          IWindow window,
          WindowManager.LayoutParams attrs,
          int viewVisibility,
          int layerStackId,
          int requestedVisibleTypes,
          InputChannel outInputChannel,
          InsetsState insetsState,
          InsetsSourceControl.Array activeControls,
          Rect attachedFrame,
          float[] sizeCompatScale)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedObject(attrs, 0);
          _data.writeInt(viewVisibility);
          _data.writeInt(layerStackId);
          _data.writeInt(requestedVisibleTypes);
          _data.writeInt(sizeCompatScale.length);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          if (_reply.readInt() != 0) {
            outInputChannel.readFromParcel(_reply);
          }
          if (_reply.readInt() != 0) {
            insetsState.readFromParcel(_reply);
          }
          if (_reply.readInt() != 0) {
            activeControls.readFromParcel(_reply);
          }
          if (_reply.readInt() != 0) {
            attachedFrame.readFromParcel(_reply);
          }
          _reply.readFloatArray(sizeCompatScale);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public int addToDisplayAsUser(
          IWindow window,
          WindowManager.LayoutParams attrs,
          int viewVisibility,
          int layerStackId,
          int userId,
          int requestedVisibleTypes,
          InputChannel outInputChannel,
          InsetsState insetsState,
          InsetsSourceControl.Array activeControls,
          Rect attachedFrame,
          float[] sizeCompatScale)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          try {
            _data.writeStrongInterface(window);
            try {
              _data.writeTypedObject(attrs, 0);
            } catch (Throwable th) {
              th = th;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th2) {
            th = th2;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th3) {
          th = th3;
        }
        try {
          _data.writeInt(viewVisibility);
          try {
            _data.writeInt(layerStackId);
            try {
              _data.writeInt(userId);
              try {
                _data.writeInt(requestedVisibleTypes);
                _data.writeInt(sizeCompatScale.length);
                try {
                  this.mRemote.transact(2, _data, _reply, 0);
                  _reply.readException();
                  int _result = _reply.readInt();
                  if (_reply.readInt() != 0) {
                    try {
                      outInputChannel.readFromParcel(_reply);
                    } catch (Throwable th4) {
                      th = th4;
                      _reply.recycle();
                      _data.recycle();
                      throw th;
                    }
                  }
                  if (_reply.readInt() != 0) {
                    try {
                      insetsState.readFromParcel(_reply);
                    } catch (Throwable th5) {
                      th = th5;
                      _reply.recycle();
                      _data.recycle();
                      throw th;
                    }
                  }
                  if (_reply.readInt() != 0) {
                    try {
                      activeControls.readFromParcel(_reply);
                    } catch (Throwable th6) {
                      th = th6;
                      _reply.recycle();
                      _data.recycle();
                      throw th;
                    }
                  }
                  if (_reply.readInt() != 0) {
                    try {
                      attachedFrame.readFromParcel(_reply);
                    } catch (Throwable th7) {
                      th = th7;
                      _reply.recycle();
                      _data.recycle();
                      throw th;
                    }
                  }
                  _reply.readFloatArray(sizeCompatScale);
                  _reply.recycle();
                  _data.recycle();
                  return _result;
                } catch (Throwable th8) {
                  th = th8;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th9) {
                th = th9;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.view.IWindowSession
      public int addToDisplayWithoutInputChannel(
          IWindow window,
          WindowManager.LayoutParams attrs,
          int viewVisibility,
          int layerStackId,
          InsetsState insetsState,
          Rect attachedFrame,
          float[] sizeCompatScale)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedObject(attrs, 0);
          _data.writeInt(viewVisibility);
          _data.writeInt(layerStackId);
          _data.writeInt(sizeCompatScale.length);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          if (_reply.readInt() != 0) {
            insetsState.readFromParcel(_reply);
          }
          if (_reply.readInt() != 0) {
            attachedFrame.readFromParcel(_reply);
          }
          _reply.readFloatArray(sizeCompatScale);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void remove(IWindow window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public int relayout(
          IWindow window,
          WindowManager.LayoutParams attrs,
          int requestedWidth,
          int requestedHeight,
          int viewVisibility,
          int flags,
          int seq,
          int lastSyncSeqId,
          ClientWindowFrames outFrames,
          MergedConfiguration outMergedConfiguration,
          SurfaceControl outSurfaceControl,
          InsetsState insetsState,
          InsetsSourceControl.Array activeControls,
          Bundle bundle)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          try {
            _data.writeTypedObject(attrs, 0);
            try {
              _data.writeInt(requestedWidth);
              try {
                _data.writeInt(requestedHeight);
              } catch (Throwable th) {
                th = th;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th2) {
              th = th2;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th3) {
            th = th3;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeInt(viewVisibility);
            try {
              _data.writeInt(flags);
              try {
                _data.writeInt(seq);
                try {
                  _data.writeInt(lastSyncSeqId);
                } catch (Throwable th4) {
                  th = th4;
                }
              } catch (Throwable th5) {
                th = th5;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th6) {
              th = th6;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th7) {
            th = th7;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th8) {
          th = th8;
        }
        try {
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          if (_reply.readInt() != 0) {
            try {
              outFrames.readFromParcel(_reply);
            } catch (Throwable th9) {
              th = th9;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          }
          if (_reply.readInt() != 0) {
            try {
              outMergedConfiguration.readFromParcel(_reply);
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          }
          if (_reply.readInt() != 0) {
            try {
              outSurfaceControl.readFromParcel(_reply);
            } catch (Throwable th11) {
              th = th11;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          }
          if (_reply.readInt() != 0) {
            insetsState.readFromParcel(_reply);
          }
          if (_reply.readInt() != 0) {
            activeControls.readFromParcel(_reply);
          }
          if (_reply.readInt() != 0) {
            try {
              bundle.readFromParcel(_reply);
            } catch (Throwable th12) {
              th = th12;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          }
          _reply.recycle();
          _data.recycle();
          return _result;
        } catch (Throwable th13) {
          th = th13;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.view.IWindowSession
      public void relayoutAsync(
          IWindow window,
          WindowManager.LayoutParams attrs,
          int requestedWidth,
          int requestedHeight,
          int viewVisibility,
          int flags,
          int seq,
          int lastSyncSeqId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedObject(attrs, 0);
          _data.writeInt(requestedWidth);
          _data.writeInt(requestedHeight);
          _data.writeInt(viewVisibility);
          _data.writeInt(flags);
          _data.writeInt(seq);
          _data.writeInt(lastSyncSeqId);
          this.mRemote.transact(6, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public boolean outOfMemory(IWindow window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void setInsets(
          IWindow window,
          int touchableInsets,
          Rect contentInsets,
          Rect visibleInsets,
          Region touchableRegion)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeInt(touchableInsets);
          _data.writeTypedObject(contentInsets, 0);
          _data.writeTypedObject(visibleInsets, 0);
          _data.writeTypedObject(touchableRegion, 0);
          this.mRemote.transact(8, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void finishDrawing(
          IWindow window, SurfaceControl.Transaction postDrawTransaction, int seqId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedObject(postDrawTransaction, 0);
          _data.writeInt(seqId);
          this.mRemote.transact(9, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public boolean performHapticFeedback(int effectId, boolean always) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(effectId);
          _data.writeBoolean(always);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void performHapticFeedbackAsync(int effectId, boolean always) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(effectId);
          _data.writeBoolean(always);
          this.mRemote.transact(11, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public IBinder performDrag(
          IWindow window,
          int flags,
          SurfaceControl surface,
          int touchSource,
          float touchX,
          float touchY,
          float thumbCenterX,
          float thumbCenterY,
          ClipData data)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeInt(flags);
          _data.writeTypedObject(surface, 0);
          _data.writeInt(touchSource);
          _data.writeFloat(touchX);
          _data.writeFloat(touchY);
          _data.writeFloat(thumbCenterX);
          _data.writeFloat(thumbCenterY);
          _data.writeTypedObject(data, 0);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          IBinder _result = _reply.readStrongBinder();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public IBinder performDragWithArea(
          IWindow window,
          int flags,
          SurfaceControl surface,
          int touchSource,
          float touchX,
          float touchY,
          float thumbCenterX,
          float thumbCenterY,
          ClipData data,
          RectF selectedArea,
          Point viewLocation)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          try {
            _data.writeInt(flags);
            try {
              _data.writeTypedObject(surface, 0);
              try {
                _data.writeInt(touchSource);
              } catch (Throwable th) {
                th = th;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th2) {
              th = th2;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th3) {
            th = th3;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeFloat(touchX);
            try {
              _data.writeFloat(touchY);
              try {
                _data.writeFloat(thumbCenterX);
                try {
                  _data.writeFloat(thumbCenterY);
                } catch (Throwable th4) {
                  th = th4;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th5) {
                th = th5;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th6) {
              th = th6;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeTypedObject(data, 0);
              try {
                _data.writeTypedObject(selectedArea, 0);
                try {
                  _data.writeTypedObject(viewLocation, 0);
                  try {
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                  } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                  }
                } catch (Throwable th8) {
                  th = th8;
                }
              } catch (Throwable th9) {
                th = th9;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
        }
      }

      @Override // android.view.IWindowSession
      public boolean dropForAccessibility(IWindow window, int x, int y) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeInt(x);
          _data.writeInt(y);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void reportDropResult(IWindow window, boolean consumed) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeBoolean(consumed);
          this.mRemote.transact(15, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void cancelDragAndDrop(IBinder dragToken, boolean skipAnimation)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(dragToken);
          _data.writeBoolean(skipAnimation);
          this.mRemote.transact(16, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void dragRecipientEntered(IWindow window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          this.mRemote.transact(17, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void dragRecipientExited(IWindow window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          this.mRemote.transact(18, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void setWallpaperPosition(
          IBinder windowToken, float x, float y, float xstep, float ystep) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(windowToken);
          _data.writeFloat(x);
          _data.writeFloat(y);
          _data.writeFloat(xstep);
          _data.writeFloat(ystep);
          this.mRemote.transact(19, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void setWallpaperZoomOut(IBinder windowToken, float scale) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(windowToken);
          _data.writeFloat(scale);
          this.mRemote.transact(20, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void setShouldZoomOutWallpaper(IBinder windowToken, boolean shouldZoom)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(windowToken);
          _data.writeBoolean(shouldZoom);
          this.mRemote.transact(21, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void wallpaperOffsetsComplete(IBinder window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(window);
          this.mRemote.transact(22, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void setWallpaperDisplayOffset(IBinder windowToken, int x, int y)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(windowToken);
          _data.writeInt(x);
          _data.writeInt(y);
          this.mRemote.transact(23, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public Bundle sendWallpaperCommand(
          IBinder window, String action, int x, int y, int z, Bundle extras, boolean sync)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(window);
          _data.writeString(action);
          _data.writeInt(x);
          _data.writeInt(y);
          _data.writeInt(z);
          _data.writeTypedObject(extras, 0);
          _data.writeBoolean(sync);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void wallpaperCommandComplete(IBinder window, Bundle result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(window);
          _data.writeTypedObject(result, 0);
          this.mRemote.transact(25, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void onRectangleOnScreenRequested(IBinder token, Rect rectangle)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(token);
          _data.writeTypedObject(rectangle, 0);
          this.mRemote.transact(26, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public IWindowId getWindowId(IBinder window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(window);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          IWindowId _result = IWindowId.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void pokeDrawLock(IBinder window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(window);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public boolean startMovingTask(IWindow window, float startX, float startY)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeFloat(startX);
          _data.writeFloat(startY);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void finishMovingTask(IWindow window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          this.mRemote.transact(30, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void updatePointerIcon(IWindow window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          this.mRemote.transact(31, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void updateTapExcludeRegion(IWindow window, Region region) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedObject(region, 0);
          this.mRemote.transact(32, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void updateRequestedVisibleTypes(IWindow window, int requestedVisibleTypes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeInt(requestedVisibleTypes);
          this.mRemote.transact(33, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void reportSystemGestureExclusionChanged(IWindow window, List<Rect> exclusionRects)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedList(exclusionRects, 0);
          this.mRemote.transact(34, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void reportKeepClearAreasChanged(
          IWindow window, List<Rect> restricted, List<Rect> unrestricted) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedList(restricted, 0);
          _data.writeTypedList(unrestricted, 0);
          this.mRemote.transact(35, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void grantInputChannel(
          int displayId,
          SurfaceControl surface,
          IWindow window,
          IBinder hostInputToken,
          int flags,
          int privateFlags,
          int inputFeatures,
          int type,
          IBinder windowToken,
          IBinder focusGrantToken,
          String inputHandleName,
          InputChannel outInputChannel)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(displayId);
          try {
            _data.writeTypedObject(surface, 0);
            try {
              _data.writeStrongInterface(window);
            } catch (Throwable th) {
              th = th;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th2) {
            th = th2;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th3) {
          th = th3;
        }
        try {
          _data.writeStrongBinder(hostInputToken);
          try {
            _data.writeInt(flags);
            try {
              _data.writeInt(privateFlags);
              try {
                _data.writeInt(inputFeatures);
              } catch (Throwable th4) {
                th = th4;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th5) {
              th = th5;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th6) {
            th = th6;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeInt(type);
            try {
              _data.writeStrongBinder(windowToken);
              try {
                _data.writeStrongBinder(focusGrantToken);
                try {
                  _data.writeString(inputHandleName);
                } catch (Throwable th7) {
                  th = th7;
                }
              } catch (Throwable th8) {
                th = th8;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th9) {
              th = th9;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th10) {
            th = th10;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th11) {
          th = th11;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
        try {
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          if (_reply.readInt() != 0) {
            try {
              outInputChannel.readFromParcel(_reply);
            } catch (Throwable th12) {
              th = th12;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          }
          _reply.recycle();
          _data.recycle();
        } catch (Throwable th13) {
          th = th13;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.view.IWindowSession
      public void grantInputChannelWithTaskToken(
          int displayId,
          SurfaceControl surface,
          IWindow window,
          IBinder hostInputToken,
          int flags,
          int privateFlags,
          int inputFeatures,
          int type,
          IBinder windowToken,
          IBinder focusGrantToken,
          String inputHandleName,
          InputChannel outInputChannel,
          int surfaceInset,
          WindowContainerToken taskToken)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeTypedObject(surface, 0);
          try {
            _data.writeStrongInterface(window);
          } catch (Throwable th) {
            th = th;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th2) {
          th = th2;
        }
        try {
          _data.writeStrongBinder(hostInputToken);
          try {
            _data.writeInt(flags);
            try {
              _data.writeInt(privateFlags);
              try {
                _data.writeInt(inputFeatures);
                try {
                  _data.writeInt(type);
                  try {
                    _data.writeStrongBinder(windowToken);
                    try {
                      _data.writeStrongBinder(focusGrantToken);
                      try {
                        _data.writeString(inputHandleName);
                      } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                      }
                    } catch (Throwable th4) {
                      th = th4;
                      _reply.recycle();
                      _data.recycle();
                      throw th;
                    }
                  } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                  }
                } catch (Throwable th6) {
                  th = th6;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th7) {
                th = th7;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th8) {
              th = th8;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeInt(surfaceInset);
              try {
                _data.writeTypedObject(taskToken, 0);
                this.mRemote.transact(37, _data, _reply, 0);
                _reply.readException();
                if (_reply.readInt() != 0) {
                  try {
                    outInputChannel.readFromParcel(_reply);
                  } catch (Throwable th9) {
                    th = th9;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                  }
                }
                _reply.recycle();
                _data.recycle();
              } catch (Throwable th10) {
                th = th10;
              }
            } catch (Throwable th11) {
              th = th11;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th12) {
            th = th12;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th13) {
          th = th13;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.view.IWindowSession
      public void removeWithTaskToken(IWindow window, WindowContainerToken taskToken)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedObject(taskToken, 0);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void updateInputChannel(
          IBinder channelToken,
          int displayId,
          SurfaceControl surface,
          int flags,
          int privateFlags,
          int inputFeatures,
          Region region)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(channelToken);
          _data.writeInt(displayId);
          _data.writeTypedObject(surface, 0);
          _data.writeInt(flags);
          _data.writeInt(privateFlags);
          _data.writeInt(inputFeatures);
          _data.writeTypedObject(region, 0);
          this.mRemote.transact(39, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void updateInputChannelWithPointerRegion(
          IBinder channelToken,
          int displayId,
          SurfaceControl surface,
          int flags,
          int privateFlags,
          int inputFeatures,
          Region region,
          Region pointerRegion)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(channelToken);
          _data.writeInt(displayId);
          _data.writeTypedObject(surface, 0);
          _data.writeInt(flags);
          _data.writeInt(privateFlags);
          _data.writeInt(inputFeatures);
          _data.writeTypedObject(region, 0);
          _data.writeTypedObject(pointerRegion, 0);
          this.mRemote.transact(40, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void grantEmbeddedWindowFocus(IWindow window, IBinder inputToken, boolean grantFocus)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeStrongBinder(inputToken);
          _data.writeBoolean(grantFocus);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void generateDisplayHash(
          IWindow window, Rect boundsInWindow, String hashAlgorithm, RemoteCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedObject(boundsInWindow, 0);
          _data.writeString(hashAlgorithm);
          _data.writeTypedObject(callback, 0);
          this.mRemote.transact(42, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void setOnBackInvokedCallbackInfo(
          IWindow window, OnBackInvokedCallbackInfo callbackInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedObject(callbackInfo, 0);
          this.mRemote.transact(43, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void clearTouchableRegion(IWindow window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public boolean cancelDraw(IWindow window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public boolean transferEmbeddedTouchFocusToHost(IWindow embeddedWindow)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(embeddedWindow);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void setTspDeadzone(IWindow window, Bundle deadzone) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeTypedObject(deadzone, 0);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void clearTspDeadzone(IWindow window) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void setTspNoteMode(IWindow window, boolean isTspNoteMode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeBoolean(isTspNoteMode);
          this.mRemote.transact(49, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void performClipDataUpdate(ClipData data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(data, 0);
          this.mRemote.transact(50, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.view.IWindowSession
      public void setKeyguardWallpaperTouchAllowed(IWindow window, boolean allowed)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(window);
          _data.writeBoolean(allowed);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 50;
    }
  }
}
