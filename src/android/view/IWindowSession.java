package android.view;

import android.content.ClipData;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.view.IWindow;
import android.view.IWindowId;
import android.view.InsetsSourceControl;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.inputmethod.ImeTracker;
import android.window.InputTransferToken;
import android.window.OnBackInvokedCallbackInfo;
import android.window.WindowContainerToken;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IWindowSession extends IInterface {

    public static class Default implements IWindowSession {
        @Override // android.view.IWindowSession
        public int addToDisplay(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowSession
        public int addToDisplayAsUser(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowSession
        public int addToDisplayWithoutInputChannel(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, InsetsState insetsState, Rect rect, float[] fArr) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IWindowSession
        public void cancelDragAndDrop(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public boolean cancelDraw(IWindow iWindow) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public void clearTouchableRegion(IWindow iWindow) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void clearTspDeadzone(IWindow iWindow) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void dragRecipientEntered(IWindow iWindow) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void dragRecipientExited(IWindow iWindow) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public boolean dropForAccessibility(IWindow iWindow, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public void finishDrawing(IWindow iWindow, SurfaceControl.Transaction transaction, int i) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void finishMovingTask(IWindow iWindow) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void generateDisplayHash(IWindow iWindow, Rect rect, String str, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public int getDragDeviceId() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowSession
        public int getDragPointerId() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowSession
        public IBinder getDragStateInputToken() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowSession
        public IWindowId getWindowId(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowSession
        public void grantEmbeddedWindowFocus(IWindow iWindow, InputTransferToken inputTransferToken, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void grantInputChannel(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void grantInputChannelWithTaskToken(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel, int i6, WindowContainerToken windowContainerToken) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public boolean moveFocusToAdjacentWindow(IWindow iWindow, int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public void notifyImeWindowVisibilityChangedFromClient(IWindow iWindow, boolean z, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public boolean outOfMemory(IWindow iWindow) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public void performClipDataUpdate(ClipData clipData) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public IBinder performDrag(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowSession
        public IBinder performDragWithArea(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData, RectF rectF, Point point) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowSession
        public void pokeDrawLock(IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public int relayout(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, WindowRelayoutResult windowRelayoutResult) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowSession
        public void relayoutAsync(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void remove(IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void removeWithTaskToken(IBinder iBinder, WindowContainerToken windowContainerToken) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void reportDecorViewGestureInterceptionChanged(IWindow iWindow, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void reportDropResult(IWindow iWindow, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void reportKeepClearAreasChanged(IWindow iWindow, List<Rect> list, List<Rect> list2) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void reportSystemGestureExclusionChanged(IWindow iWindow, List<Rect> list) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void sendWallpaperCommand(IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setInsets(IWindow iWindow, int i, Rect rect, Rect rect2, Region region, Rect rect3) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setKeyguardWallpaperTouchAllowed(IWindow iWindow, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setOnBackInvokedCallbackInfo(IWindow iWindow, OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setShouldZoomOutWallpaper(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setTspDeadzone(IWindow iWindow, Bundle bundle) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setTspNoteMode(IWindow iWindow, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setWallpaperDisplayOffset(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setWallpaperPosition(IBinder iBinder, float f, float f2, float f3, float f4) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setWallpaperZoomOut(IBinder iBinder, float f) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public boolean startMovingTask(IWindow iWindow, float f, float f2) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public void updateAnimatingTypes(IWindow iWindow, int i, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void updateInputChannel(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void updateInputChannelWithPointerRegion(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region, Region region2) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void updateRequestedVisibleTypes(IWindow iWindow, int i, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void updateTapExcludeRegion(IWindow iWindow, Region region) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) throws RemoteException {
        }

        @Override // android.view.IWindowSession
        public void wallpaperOffsetsComplete(IBinder iBinder) throws RemoteException {
        }
    }

    int addToDisplay(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) throws RemoteException;

    int addToDisplayAsUser(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) throws RemoteException;

    int addToDisplayWithoutInputChannel(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, InsetsState insetsState, Rect rect, float[] fArr) throws RemoteException;

    void cancelDragAndDrop(IBinder iBinder, boolean z) throws RemoteException;

    boolean cancelDraw(IWindow iWindow) throws RemoteException;

    void clearTouchableRegion(IWindow iWindow) throws RemoteException;

    void clearTspDeadzone(IWindow iWindow) throws RemoteException;

    void dragRecipientEntered(IWindow iWindow) throws RemoteException;

    void dragRecipientExited(IWindow iWindow) throws RemoteException;

    boolean dropForAccessibility(IWindow iWindow, int i, int i2) throws RemoteException;

    void finishDrawing(IWindow iWindow, SurfaceControl.Transaction transaction, int i) throws RemoteException;

    void finishMovingTask(IWindow iWindow) throws RemoteException;

    void generateDisplayHash(IWindow iWindow, Rect rect, String str, RemoteCallback remoteCallback) throws RemoteException;

    int getDragDeviceId() throws RemoteException;

    int getDragPointerId() throws RemoteException;

    IBinder getDragStateInputToken() throws RemoteException;

    IWindowId getWindowId(IBinder iBinder) throws RemoteException;

    void grantEmbeddedWindowFocus(IWindow iWindow, InputTransferToken inputTransferToken, boolean z) throws RemoteException;

    void grantInputChannel(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel) throws RemoteException;

    void grantInputChannelWithTaskToken(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel, int i6, WindowContainerToken windowContainerToken) throws RemoteException;

    boolean moveFocusToAdjacentWindow(IWindow iWindow, int i) throws RemoteException;

    void notifyImeWindowVisibilityChangedFromClient(IWindow iWindow, boolean z, ImeTracker.Token token) throws RemoteException;

    void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) throws RemoteException;

    boolean outOfMemory(IWindow iWindow) throws RemoteException;

    void performClipDataUpdate(ClipData clipData) throws RemoteException;

    IBinder performDrag(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData) throws RemoteException;

    IBinder performDragWithArea(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData, RectF rectF, Point point) throws RemoteException;

    void pokeDrawLock(IBinder iBinder) throws RemoteException;

    int relayout(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, WindowRelayoutResult windowRelayoutResult) throws RemoteException;

    void relayoutAsync(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    void remove(IBinder iBinder) throws RemoteException;

    void removeWithTaskToken(IBinder iBinder, WindowContainerToken windowContainerToken) throws RemoteException;

    void reportDecorViewGestureInterceptionChanged(IWindow iWindow, boolean z) throws RemoteException;

    void reportDropResult(IWindow iWindow, boolean z) throws RemoteException;

    void reportKeepClearAreasChanged(IWindow iWindow, List<Rect> list, List<Rect> list2) throws RemoteException;

    void reportSystemGestureExclusionChanged(IWindow iWindow, List<Rect> list) throws RemoteException;

    void sendWallpaperCommand(IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle, boolean z) throws RemoteException;

    void setInsets(IWindow iWindow, int i, Rect rect, Rect rect2, Region region, Rect rect3) throws RemoteException;

    void setKeyguardWallpaperTouchAllowed(IWindow iWindow, boolean z) throws RemoteException;

    void setOnBackInvokedCallbackInfo(IWindow iWindow, OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws RemoteException;

    void setShouldZoomOutWallpaper(IBinder iBinder, boolean z) throws RemoteException;

    void setTspDeadzone(IWindow iWindow, Bundle bundle) throws RemoteException;

    void setTspNoteMode(IWindow iWindow, boolean z) throws RemoteException;

    void setWallpaperDisplayOffset(IBinder iBinder, int i, int i2) throws RemoteException;

    void setWallpaperPosition(IBinder iBinder, float f, float f2, float f3, float f4) throws RemoteException;

    void setWallpaperZoomOut(IBinder iBinder, float f) throws RemoteException;

    boolean startMovingTask(IWindow iWindow, float f, float f2) throws RemoteException;

    void updateAnimatingTypes(IWindow iWindow, int i, ImeTracker.Token token) throws RemoteException;

    void updateInputChannel(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region) throws RemoteException;

    void updateInputChannelWithPointerRegion(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region, Region region2) throws RemoteException;

    void updateRequestedVisibleTypes(IWindow iWindow, int i, ImeTracker.Token token) throws RemoteException;

    void updateTapExcludeRegion(IWindow iWindow, Region region) throws RemoteException;

    void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) throws RemoteException;

    void wallpaperOffsetsComplete(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IWindowSession {
        public static final String DESCRIPTOR = "android.view.IWindowSession";
        static final int TRANSACTION_addToDisplay = 1;
        static final int TRANSACTION_addToDisplayAsUser = 2;
        static final int TRANSACTION_addToDisplayWithoutInputChannel = 3;
        static final int TRANSACTION_cancelDragAndDrop = 17;
        static final int TRANSACTION_cancelDraw = 47;
        static final int TRANSACTION_clearTouchableRegion = 46;
        static final int TRANSACTION_clearTspDeadzone = 51;
        static final int TRANSACTION_dragRecipientEntered = 18;
        static final int TRANSACTION_dragRecipientExited = 19;
        static final int TRANSACTION_dropForAccessibility = 15;
        static final int TRANSACTION_finishDrawing = 9;
        static final int TRANSACTION_finishMovingTask = 31;
        static final int TRANSACTION_generateDisplayHash = 44;
        static final int TRANSACTION_getDragDeviceId = 14;
        static final int TRANSACTION_getDragPointerId = 13;
        static final int TRANSACTION_getDragStateInputToken = 12;
        static final int TRANSACTION_getWindowId = 28;
        static final int TRANSACTION_grantEmbeddedWindowFocus = 43;
        static final int TRANSACTION_grantInputChannel = 38;
        static final int TRANSACTION_grantInputChannelWithTaskToken = 39;
        static final int TRANSACTION_moveFocusToAdjacentWindow = 48;
        static final int TRANSACTION_notifyImeWindowVisibilityChangedFromClient = 49;
        static final int TRANSACTION_onRectangleOnScreenRequested = 27;
        static final int TRANSACTION_outOfMemory = 7;
        static final int TRANSACTION_performClipDataUpdate = 53;
        static final int TRANSACTION_performDrag = 10;
        static final int TRANSACTION_performDragWithArea = 11;
        static final int TRANSACTION_pokeDrawLock = 29;
        static final int TRANSACTION_relayout = 5;
        static final int TRANSACTION_relayoutAsync = 6;
        static final int TRANSACTION_remove = 4;
        static final int TRANSACTION_removeWithTaskToken = 40;
        static final int TRANSACTION_reportDecorViewGestureInterceptionChanged = 36;
        static final int TRANSACTION_reportDropResult = 16;
        static final int TRANSACTION_reportKeepClearAreasChanged = 37;
        static final int TRANSACTION_reportSystemGestureExclusionChanged = 35;
        static final int TRANSACTION_sendWallpaperCommand = 25;
        static final int TRANSACTION_setInsets = 8;
        static final int TRANSACTION_setKeyguardWallpaperTouchAllowed = 54;
        static final int TRANSACTION_setOnBackInvokedCallbackInfo = 45;
        static final int TRANSACTION_setShouldZoomOutWallpaper = 22;
        static final int TRANSACTION_setTspDeadzone = 50;
        static final int TRANSACTION_setTspNoteMode = 52;
        static final int TRANSACTION_setWallpaperDisplayOffset = 24;
        static final int TRANSACTION_setWallpaperPosition = 20;
        static final int TRANSACTION_setWallpaperZoomOut = 21;
        static final int TRANSACTION_startMovingTask = 30;
        static final int TRANSACTION_updateAnimatingTypes = 34;
        static final int TRANSACTION_updateInputChannel = 41;
        static final int TRANSACTION_updateInputChannelWithPointerRegion = 42;
        static final int TRANSACTION_updateRequestedVisibleTypes = 33;
        static final int TRANSACTION_updateTapExcludeRegion = 32;
        static final int TRANSACTION_wallpaperCommandComplete = 26;
        static final int TRANSACTION_wallpaperOffsetsComplete = 23;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 53;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWindowSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWindowSession)) {
                return (IWindowSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "performDrag";
                case 11:
                    return "performDragWithArea";
                case 12:
                    return "getDragStateInputToken";
                case 13:
                    return "getDragPointerId";
                case 14:
                    return "getDragDeviceId";
                case 15:
                    return "dropForAccessibility";
                case 16:
                    return "reportDropResult";
                case 17:
                    return "cancelDragAndDrop";
                case 18:
                    return "dragRecipientEntered";
                case 19:
                    return "dragRecipientExited";
                case 20:
                    return "setWallpaperPosition";
                case 21:
                    return "setWallpaperZoomOut";
                case 22:
                    return "setShouldZoomOutWallpaper";
                case 23:
                    return "wallpaperOffsetsComplete";
                case 24:
                    return "setWallpaperDisplayOffset";
                case 25:
                    return "sendWallpaperCommand";
                case 26:
                    return "wallpaperCommandComplete";
                case 27:
                    return "onRectangleOnScreenRequested";
                case 28:
                    return "getWindowId";
                case 29:
                    return "pokeDrawLock";
                case 30:
                    return "startMovingTask";
                case 31:
                    return "finishMovingTask";
                case 32:
                    return "updateTapExcludeRegion";
                case 33:
                    return "updateRequestedVisibleTypes";
                case 34:
                    return "updateAnimatingTypes";
                case 35:
                    return "reportSystemGestureExclusionChanged";
                case 36:
                    return "reportDecorViewGestureInterceptionChanged";
                case 37:
                    return "reportKeepClearAreasChanged";
                case 38:
                    return "grantInputChannel";
                case 39:
                    return "grantInputChannelWithTaskToken";
                case 40:
                    return "removeWithTaskToken";
                case 41:
                    return "updateInputChannel";
                case 42:
                    return "updateInputChannelWithPointerRegion";
                case 43:
                    return "grantEmbeddedWindowFocus";
                case 44:
                    return "generateDisplayHash";
                case 45:
                    return "setOnBackInvokedCallbackInfo";
                case 46:
                    return "clearTouchableRegion";
                case 47:
                    return "cancelDraw";
                case 48:
                    return "moveFocusToAdjacentWindow";
                case 49:
                    return "notifyImeWindowVisibilityChangedFromClient";
                case 50:
                    return "setTspDeadzone";
                case 51:
                    return "clearTspDeadzone";
                case 52:
                    return "setTspNoteMode";
                case 53:
                    return "performClipDataUpdate";
                case 54:
                    return "setKeyguardWallpaperTouchAllowed";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    float[] fArr = null;
                    IWindow asInterface = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    InputChannel inputChannel = new InputChannel();
                    InsetsState insetsState = new InsetsState();
                    InsetsSourceControl.Array array = new InsetsSourceControl.Array();
                    Rect rect = new Rect();
                    int readInt4 = parcel.readInt();
                    if (readInt4 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt4);
                    }
                    if (readInt4 >= 0) {
                        fArr = new float[readInt4];
                    }
                    parcel.enforceNoDataAvail();
                    float[] fArr2 = fArr;
                    int addToDisplay = addToDisplay(asInterface, layoutParams, readInt, readInt2, readInt3, inputChannel, insetsState, array, rect, fArr2);
                    parcel2.writeNoException();
                    parcel2.writeInt(addToDisplay);
                    parcel2.writeTypedObject(inputChannel, 1);
                    parcel2.writeTypedObject(insetsState, 1);
                    parcel2.writeTypedObject(array, 1);
                    parcel2.writeTypedObject(rect, 1);
                    parcel2.writeFloatArray(fArr2);
                    return true;
                case 2:
                    IWindow asInterface2 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    InputChannel inputChannel2 = new InputChannel();
                    InsetsState insetsState2 = new InsetsState();
                    InsetsSourceControl.Array array2 = new InsetsSourceControl.Array();
                    Rect rect2 = new Rect();
                    int readInt9 = parcel.readInt();
                    if (readInt9 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt9);
                    }
                    float[] fArr3 = readInt9 < 0 ? null : new float[readInt9];
                    parcel.enforceNoDataAvail();
                    int addToDisplayAsUser = addToDisplayAsUser(asInterface2, layoutParams2, readInt5, readInt6, readInt7, readInt8, inputChannel2, insetsState2, array2, rect2, fArr3);
                    parcel2.writeNoException();
                    parcel2.writeInt(addToDisplayAsUser);
                    parcel2.writeTypedObject(inputChannel2, 1);
                    parcel2.writeTypedObject(insetsState2, 1);
                    parcel2.writeTypedObject(array2, 1);
                    parcel2.writeTypedObject(rect2, 1);
                    parcel2.writeFloatArray(fArr3);
                    return true;
                case 3:
                    IWindow asInterface3 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams3 = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    float[] fArr4 = null;
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    InsetsState insetsState3 = new InsetsState();
                    Rect rect3 = new Rect();
                    int readInt12 = parcel.readInt();
                    if (readInt12 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt12);
                    }
                    if (readInt12 >= 0) {
                        fArr4 = new float[readInt12];
                    }
                    parcel.enforceNoDataAvail();
                    int addToDisplayWithoutInputChannel = addToDisplayWithoutInputChannel(asInterface3, layoutParams3, readInt10, readInt11, insetsState3, rect3, fArr4);
                    parcel2.writeNoException();
                    parcel2.writeInt(addToDisplayWithoutInputChannel);
                    parcel2.writeTypedObject(insetsState3, 1);
                    parcel2.writeTypedObject(rect3, 1);
                    parcel2.writeFloatArray(fArr4);
                    return true;
                case 4:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    remove(readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IWindow asInterface4 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams4 = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    WindowRelayoutResult windowRelayoutResult = new WindowRelayoutResult();
                    parcel.enforceNoDataAvail();
                    int relayout = relayout(asInterface4, layoutParams4, readInt13, readInt14, readInt15, readInt16, readInt17, readInt18, windowRelayoutResult);
                    parcel2.writeNoException();
                    parcel2.writeInt(relayout);
                    parcel2.writeTypedObject(windowRelayoutResult, 1);
                    return true;
                case 6:
                    IWindow asInterface5 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams5 = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutAsync(asInterface5, layoutParams5, readInt19, readInt20, readInt21, readInt22, readInt23, readInt24);
                    return true;
                case 7:
                    IWindow asInterface6 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean outOfMemory = outOfMemory(asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outOfMemory);
                    return true;
                case 8:
                    IWindow asInterface7 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt25 = parcel.readInt();
                    Rect rect4 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Rect rect5 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                    Rect rect6 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInsets(asInterface7, readInt25, rect4, rect5, region, rect6);
                    return true;
                case 9:
                    IWindow asInterface8 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishDrawing(asInterface8, transaction, readInt26);
                    return true;
                case 10:
                    IWindow asInterface9 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt27 = parcel.readInt();
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    float readFloat3 = parcel.readFloat();
                    float readFloat4 = parcel.readFloat();
                    ClipData clipData = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder performDrag = performDrag(asInterface9, readInt27, surfaceControl, readInt28, readInt29, readInt30, readFloat, readFloat2, readFloat3, readFloat4, clipData);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(performDrag);
                    return true;
                case 11:
                    IWindow asInterface10 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt31 = parcel.readInt();
                    SurfaceControl surfaceControl2 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    float readFloat5 = parcel.readFloat();
                    float readFloat6 = parcel.readFloat();
                    float readFloat7 = parcel.readFloat();
                    float readFloat8 = parcel.readFloat();
                    ClipData clipData2 = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    RectF rectF = (RectF) parcel.readTypedObject(RectF.CREATOR);
                    Point point = (Point) parcel.readTypedObject(Point.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder performDragWithArea = performDragWithArea(asInterface10, readInt31, surfaceControl2, readInt32, readInt33, readInt34, readFloat5, readFloat6, readFloat7, readFloat8, clipData2, rectF, point);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(performDragWithArea);
                    return true;
                case 12:
                    z = true;
                    IBinder dragStateInputToken = getDragStateInputToken();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(dragStateInputToken);
                    return z;
                case 13:
                    z = true;
                    int dragPointerId = getDragPointerId();
                    parcel2.writeNoException();
                    parcel2.writeInt(dragPointerId);
                    return z;
                case 14:
                    z = true;
                    int dragDeviceId = getDragDeviceId();
                    parcel2.writeNoException();
                    parcel2.writeInt(dragDeviceId);
                    return z;
                case 15:
                    z = true;
                    IWindow asInterface11 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt35 = parcel.readInt();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dropForAccessibility = dropForAccessibility(asInterface11, readInt35, readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dropForAccessibility);
                    return z;
                case 16:
                    z = true;
                    IWindow asInterface12 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportDropResult(asInterface12, readBoolean);
                    return z;
                case 17:
                    z = true;
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    cancelDragAndDrop(readStrongBinder2, readBoolean2);
                    return z;
                case 18:
                    z = true;
                    IWindow asInterface13 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    dragRecipientEntered(asInterface13);
                    return z;
                case 19:
                    z = true;
                    IWindow asInterface14 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    dragRecipientExited(asInterface14);
                    return z;
                case 20:
                    z = true;
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    float readFloat9 = parcel.readFloat();
                    float readFloat10 = parcel.readFloat();
                    float readFloat11 = parcel.readFloat();
                    float readFloat12 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setWallpaperPosition(readStrongBinder3, readFloat9, readFloat10, readFloat11, readFloat12);
                    return z;
                case 21:
                    z = true;
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    float readFloat13 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setWallpaperZoomOut(readStrongBinder4, readFloat13);
                    return z;
                case 22:
                    z = true;
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldZoomOutWallpaper(readStrongBinder5, readBoolean3);
                    return z;
                case 23:
                    z = true;
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    wallpaperOffsetsComplete(readStrongBinder6);
                    return z;
                case 24:
                    z = true;
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWallpaperDisplayOffset(readStrongBinder7, readInt37, readInt38);
                    return z;
                case 25:
                    z = true;
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    String readString = parcel.readString();
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendWallpaperCommand(readStrongBinder8, readString, readInt39, readInt40, readInt41, bundle, readBoolean4);
                    return z;
                case 26:
                    z = true;
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    wallpaperCommandComplete(readStrongBinder9, bundle2);
                    return z;
                case 27:
                    z = true;
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    Rect rect7 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRectangleOnScreenRequested(readStrongBinder10, rect7);
                    return z;
                case 28:
                    z = true;
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    IWindowId windowId = getWindowId(readStrongBinder11);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(windowId);
                    return z;
                case 29:
                    z = true;
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    pokeDrawLock(readStrongBinder12);
                    parcel2.writeNoException();
                    return z;
                case 30:
                    z = true;
                    IWindow asInterface15 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    float readFloat14 = parcel.readFloat();
                    float readFloat15 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    boolean startMovingTask = startMovingTask(asInterface15, readFloat14, readFloat15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startMovingTask);
                    return z;
                case 31:
                    z = true;
                    IWindow asInterface16 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishMovingTask(asInterface16);
                    return z;
                case 32:
                    z = true;
                    IWindow asInterface17 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    Region region2 = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateTapExcludeRegion(asInterface17, region2);
                    return z;
                case 33:
                    z = true;
                    IWindow asInterface18 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt42 = parcel.readInt();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateRequestedVisibleTypes(asInterface18, readInt42, token);
                    return z;
                case 34:
                    z = true;
                    IWindow asInterface19 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt43 = parcel.readInt();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAnimatingTypes(asInterface19, readInt43, token2);
                    return z;
                case 35:
                    z = true;
                    IWindow asInterface20 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportSystemGestureExclusionChanged(asInterface20, createTypedArrayList);
                    return z;
                case 36:
                    z = true;
                    IWindow asInterface21 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportDecorViewGestureInterceptionChanged(asInterface21, readBoolean5);
                    return z;
                case 37:
                    z = true;
                    IWindow asInterface22 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(Rect.CREATOR);
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportKeepClearAreasChanged(asInterface22, createTypedArrayList2, createTypedArrayList3);
                    return z;
                case 38:
                    z = true;
                    int readInt44 = parcel.readInt();
                    SurfaceControl surfaceControl3 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    InputTransferToken inputTransferToken = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    InputTransferToken inputTransferToken2 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    String readString2 = parcel.readString();
                    InputChannel inputChannel3 = new InputChannel();
                    parcel.enforceNoDataAvail();
                    grantInputChannel(readInt44, surfaceControl3, readStrongBinder13, inputTransferToken, readInt45, readInt46, readInt47, readInt48, readStrongBinder14, inputTransferToken2, readString2, inputChannel3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputChannel3, 1);
                    return z;
                case 39:
                    int readInt49 = parcel.readInt();
                    SurfaceControl surfaceControl4 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    InputTransferToken inputTransferToken3 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    int readInt53 = parcel.readInt();
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    InputTransferToken inputTransferToken4 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    String readString3 = parcel.readString();
                    InputChannel inputChannel4 = new InputChannel();
                    int readInt54 = parcel.readInt();
                    WindowContainerToken windowContainerToken = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    grantInputChannelWithTaskToken(readInt49, surfaceControl4, readStrongBinder15, inputTransferToken3, readInt50, readInt51, readInt52, readInt53, readStrongBinder16, inputTransferToken4, readString3, inputChannel4, readInt54, windowContainerToken);
                    parcel2.writeNoException();
                    z = true;
                    parcel2.writeTypedObject(inputChannel4, 1);
                    return z;
                case 40:
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    WindowContainerToken windowContainerToken2 = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeWithTaskToken(readStrongBinder17, windowContainerToken2);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    int readInt55 = parcel.readInt();
                    SurfaceControl surfaceControl5 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int readInt56 = parcel.readInt();
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    Region region3 = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateInputChannel(readStrongBinder18, readInt55, surfaceControl5, readInt56, readInt57, readInt58, region3);
                    return true;
                case 42:
                    IBinder readStrongBinder19 = parcel.readStrongBinder();
                    int readInt59 = parcel.readInt();
                    SurfaceControl surfaceControl6 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    Region region4 = (Region) parcel.readTypedObject(Region.CREATOR);
                    Region region5 = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateInputChannelWithPointerRegion(readStrongBinder19, readInt59, surfaceControl6, readInt60, readInt61, readInt62, region4, region5);
                    return true;
                case 43:
                    IWindow asInterface23 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    InputTransferToken inputTransferToken5 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    grantEmbeddedWindowFocus(asInterface23, inputTransferToken5, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IWindow asInterface24 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    Rect rect8 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    String readString4 = parcel.readString();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    generateDisplayHash(asInterface24, rect8, readString4, remoteCallback);
                    return true;
                case 45:
                    IWindow asInterface25 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    OnBackInvokedCallbackInfo onBackInvokedCallbackInfo = (OnBackInvokedCallbackInfo) parcel.readTypedObject(OnBackInvokedCallbackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOnBackInvokedCallbackInfo(asInterface25, onBackInvokedCallbackInfo);
                    return true;
                case 46:
                    IWindow asInterface26 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearTouchableRegion(asInterface26);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    IWindow asInterface27 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean cancelDraw = cancelDraw(asInterface27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cancelDraw);
                    return true;
                case 48:
                    IWindow asInterface28 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean moveFocusToAdjacentWindow = moveFocusToAdjacentWindow(asInterface28, readInt63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(moveFocusToAdjacentWindow);
                    return true;
                case 49:
                    IWindow asInterface29 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean7 = parcel.readBoolean();
                    ImeTracker.Token token3 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyImeWindowVisibilityChangedFromClient(asInterface29, readBoolean7, token3);
                    return true;
                case 50:
                    IWindow asInterface30 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTspDeadzone(asInterface30, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IWindow asInterface31 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearTspDeadzone(asInterface31);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    IWindow asInterface32 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTspNoteMode(asInterface32, readBoolean8);
                    return true;
                case 53:
                    ClipData clipData3 = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    parcel.enforceNoDataAvail();
                    performClipDataUpdate(clipData3);
                    return true;
                case 54:
                    IWindow asInterface33 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKeyguardWallpaperTouchAllowed(asInterface33, readBoolean9);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWindowSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.IWindowSession
            public int addToDisplay(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(fArr.length);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        inputChannel.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        insetsState.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        array.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        rect.readFromParcel(obtain2);
                    }
                    obtain2.readFloatArray(fArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int addToDisplayAsUser(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(fArr.length);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        inputChannel.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        insetsState.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        array.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        rect.readFromParcel(obtain2);
                    }
                    obtain2.readFloatArray(fArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int addToDisplayWithoutInputChannel(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, InsetsState insetsState, Rect rect, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(fArr.length);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        insetsState.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        rect.readFromParcel(obtain2);
                    }
                    obtain2.readFloatArray(fArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void remove(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int relayout(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, WindowRelayoutResult windowRelayoutResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        windowRelayoutResult.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void relayoutAsync(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean outOfMemory(IWindow iWindow) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setInsets(IWindow iWindow, int i, Rect rect, Rect rect2, Region region, Rect rect3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(rect2, 0);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeTypedObject(rect3, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void finishDrawing(IWindow iWindow, SurfaceControl.Transaction transaction, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(transaction, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public IBinder performDrag(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    obtain.writeFloat(f4);
                    obtain.writeTypedObject(clipData, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public IBinder performDragWithArea(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData, RectF rectF, Point point) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    obtain.writeFloat(f4);
                    obtain.writeTypedObject(clipData, 0);
                    obtain.writeTypedObject(rectF, 0);
                    obtain.writeTypedObject(point, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public IBinder getDragStateInputToken() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int getDragPointerId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int getDragDeviceId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean dropForAccessibility(IWindow iWindow, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportDropResult(IWindow iWindow, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void cancelDragAndDrop(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void dragRecipientEntered(IWindow iWindow) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void dragRecipientExited(IWindow iWindow) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setWallpaperPosition(IBinder iBinder, float f, float f2, float f3, float f4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    obtain.writeFloat(f4);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setWallpaperZoomOut(IBinder iBinder, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloat(f);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setShouldZoomOutWallpaper(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void wallpaperOffsetsComplete(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setWallpaperDisplayOffset(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void sendWallpaperCommand(IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public IWindowId getWindowId(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return IWindowId.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void pokeDrawLock(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean startMovingTask(IWindow iWindow, float f, float f2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void finishMovingTask(IWindow iWindow) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateTapExcludeRegion(IWindow iWindow, Region region) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateRequestedVisibleTypes(IWindow iWindow, int i, ImeTracker.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateAnimatingTypes(IWindow iWindow, int i, ImeTracker.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportSystemGestureExclusionChanged(IWindow iWindow, List<Rect> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportDecorViewGestureInterceptionChanged(IWindow iWindow, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportKeepClearAreasChanged(IWindow iWindow, List<Rect> list, List<Rect> list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void grantInputChannel(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(inputTransferToken, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(inputTransferToken2, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        inputChannel.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void grantInputChannelWithTaskToken(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel, int i6, WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(inputTransferToken, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(inputTransferToken2, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i6);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        inputChannel.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void removeWithTaskToken(IBinder iBinder, WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateInputChannel(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateInputChannelWithPointerRegion(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region, Region region2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeTypedObject(region2, 0);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void grantEmbeddedWindowFocus(IWindow iWindow, InputTransferToken inputTransferToken, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(inputTransferToken, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void generateDisplayHash(IWindow iWindow, Rect rect, String str, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setOnBackInvokedCallbackInfo(IWindow iWindow, OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(onBackInvokedCallbackInfo, 0);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void clearTouchableRegion(IWindow iWindow) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean cancelDraw(IWindow iWindow) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean moveFocusToAdjacentWindow(IWindow iWindow, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void notifyImeWindowVisibilityChangedFromClient(IWindow iWindow, boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(49, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setTspDeadzone(IWindow iWindow, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void clearTspDeadzone(IWindow iWindow) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setTspNoteMode(IWindow iWindow, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void performClipDataUpdate(ClipData clipData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(clipData, 0);
                    this.mRemote.transact(53, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setKeyguardWallpaperTouchAllowed(IWindow iWindow, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
