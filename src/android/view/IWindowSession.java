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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWindowSession)) {
                return (IWindowSession) iInterfaceQueryLocalInterface;
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
                    IWindow iWindowAsInterface = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    InputChannel inputChannel = new InputChannel();
                    InsetsState insetsState = new InsetsState();
                    InsetsSourceControl.Array array = new InsetsSourceControl.Array();
                    Rect rect = new Rect();
                    int i6 = parcel.readInt();
                    if (i6 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i6);
                    }
                    if (i6 >= 0) {
                        fArr = new float[i6];
                    }
                    parcel.enforceNoDataAvail();
                    float[] fArr2 = fArr;
                    int iAddToDisplay = addToDisplay(iWindowAsInterface, layoutParams, i3, i4, i5, inputChannel, insetsState, array, rect, fArr2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddToDisplay);
                    parcel2.writeTypedObject(inputChannel, 1);
                    parcel2.writeTypedObject(insetsState, 1);
                    parcel2.writeTypedObject(array, 1);
                    parcel2.writeTypedObject(rect, 1);
                    parcel2.writeFloatArray(fArr2);
                    return true;
                case 2:
                    IWindow iWindowAsInterface2 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    InputChannel inputChannel2 = new InputChannel();
                    InsetsState insetsState2 = new InsetsState();
                    InsetsSourceControl.Array array2 = new InsetsSourceControl.Array();
                    Rect rect2 = new Rect();
                    int i11 = parcel.readInt();
                    if (i11 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i11);
                    }
                    float[] fArr3 = i11 < 0 ? null : new float[i11];
                    parcel.enforceNoDataAvail();
                    int iAddToDisplayAsUser = addToDisplayAsUser(iWindowAsInterface2, layoutParams2, i7, i8, i9, i10, inputChannel2, insetsState2, array2, rect2, fArr3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddToDisplayAsUser);
                    parcel2.writeTypedObject(inputChannel2, 1);
                    parcel2.writeTypedObject(insetsState2, 1);
                    parcel2.writeTypedObject(array2, 1);
                    parcel2.writeTypedObject(rect2, 1);
                    parcel2.writeFloatArray(fArr3);
                    return true;
                case 3:
                    IWindow iWindowAsInterface3 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams3 = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    float[] fArr4 = null;
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    InsetsState insetsState3 = new InsetsState();
                    Rect rect3 = new Rect();
                    int i14 = parcel.readInt();
                    if (i14 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i14);
                    }
                    if (i14 >= 0) {
                        fArr4 = new float[i14];
                    }
                    parcel.enforceNoDataAvail();
                    int iAddToDisplayWithoutInputChannel = addToDisplayWithoutInputChannel(iWindowAsInterface3, layoutParams3, i12, i13, insetsState3, rect3, fArr4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddToDisplayWithoutInputChannel);
                    parcel2.writeTypedObject(insetsState3, 1);
                    parcel2.writeTypedObject(rect3, 1);
                    parcel2.writeFloatArray(fArr4);
                    return true;
                case 4:
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    remove(strongBinder);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IWindow iWindowAsInterface4 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams4 = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    WindowRelayoutResult windowRelayoutResult = new WindowRelayoutResult();
                    parcel.enforceNoDataAvail();
                    int iRelayout = relayout(iWindowAsInterface4, layoutParams4, i15, i16, i17, i18, i19, i20, windowRelayoutResult);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRelayout);
                    parcel2.writeTypedObject(windowRelayoutResult, 1);
                    return true;
                case 6:
                    IWindow iWindowAsInterface5 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    WindowManager.LayoutParams layoutParams5 = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutAsync(iWindowAsInterface5, layoutParams5, i21, i22, i23, i24, i25, i26);
                    return true;
                case 7:
                    IWindow iWindowAsInterface6 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zOutOfMemory = outOfMemory(iWindowAsInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zOutOfMemory);
                    return true;
                case 8:
                    IWindow iWindowAsInterface7 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int i27 = parcel.readInt();
                    Rect rect4 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Rect rect5 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                    Rect rect6 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInsets(iWindowAsInterface7, i27, rect4, rect5, region, rect6);
                    return true;
                case 9:
                    IWindow iWindowAsInterface8 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishDrawing(iWindowAsInterface8, transaction, i28);
                    return true;
                case 10:
                    IWindow iWindowAsInterface9 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int i29 = parcel.readInt();
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    float f3 = parcel.readFloat();
                    float f4 = parcel.readFloat();
                    ClipData clipData = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder iBinderPerformDrag = performDrag(iWindowAsInterface9, i29, surfaceControl, i30, i31, i32, f, f2, f3, f4, clipData);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iBinderPerformDrag);
                    return true;
                case 11:
                    IWindow iWindowAsInterface10 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int i33 = parcel.readInt();
                    SurfaceControl surfaceControl2 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    float f5 = parcel.readFloat();
                    float f6 = parcel.readFloat();
                    float f7 = parcel.readFloat();
                    float f8 = parcel.readFloat();
                    ClipData clipData2 = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    RectF rectF = (RectF) parcel.readTypedObject(RectF.CREATOR);
                    Point point = (Point) parcel.readTypedObject(Point.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder iBinderPerformDragWithArea = performDragWithArea(iWindowAsInterface10, i33, surfaceControl2, i34, i35, i36, f5, f6, f7, f8, clipData2, rectF, point);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iBinderPerformDragWithArea);
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
                    IWindow iWindowAsInterface11 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int i37 = parcel.readInt();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDropForAccessibility = dropForAccessibility(iWindowAsInterface11, i37, i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDropForAccessibility);
                    return z;
                case 16:
                    z = true;
                    IWindow iWindowAsInterface12 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportDropResult(iWindowAsInterface12, z2);
                    return z;
                case 17:
                    z = true;
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    cancelDragAndDrop(strongBinder2, z3);
                    return z;
                case 18:
                    z = true;
                    IWindow iWindowAsInterface13 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    dragRecipientEntered(iWindowAsInterface13);
                    return z;
                case 19:
                    z = true;
                    IWindow iWindowAsInterface14 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    dragRecipientExited(iWindowAsInterface14);
                    return z;
                case 20:
                    z = true;
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    float f9 = parcel.readFloat();
                    float f10 = parcel.readFloat();
                    float f11 = parcel.readFloat();
                    float f12 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setWallpaperPosition(strongBinder3, f9, f10, f11, f12);
                    return z;
                case 21:
                    z = true;
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    float f13 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setWallpaperZoomOut(strongBinder4, f13);
                    return z;
                case 22:
                    z = true;
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldZoomOutWallpaper(strongBinder5, z4);
                    return z;
                case 23:
                    z = true;
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    wallpaperOffsetsComplete(strongBinder6);
                    return z;
                case 24:
                    z = true;
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWallpaperDisplayOffset(strongBinder7, i39, i40);
                    return z;
                case 25:
                    z = true;
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    String string = parcel.readString();
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    int i43 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendWallpaperCommand(strongBinder8, string, i41, i42, i43, bundle, z5);
                    return z;
                case 26:
                    z = true;
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    wallpaperCommandComplete(strongBinder9, bundle2);
                    return z;
                case 27:
                    z = true;
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    Rect rect7 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRectangleOnScreenRequested(strongBinder10, rect7);
                    return z;
                case 28:
                    z = true;
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    IWindowId windowId = getWindowId(strongBinder11);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(windowId);
                    return z;
                case 29:
                    z = true;
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    pokeDrawLock(strongBinder12);
                    parcel2.writeNoException();
                    return z;
                case 30:
                    z = true;
                    IWindow iWindowAsInterface15 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    float f14 = parcel.readFloat();
                    float f15 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    boolean zStartMovingTask = startMovingTask(iWindowAsInterface15, f14, f15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartMovingTask);
                    return z;
                case 31:
                    z = true;
                    IWindow iWindowAsInterface16 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishMovingTask(iWindowAsInterface16);
                    return z;
                case 32:
                    z = true;
                    IWindow iWindowAsInterface17 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    Region region2 = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateTapExcludeRegion(iWindowAsInterface17, region2);
                    return z;
                case 33:
                    z = true;
                    IWindow iWindowAsInterface18 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int i44 = parcel.readInt();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateRequestedVisibleTypes(iWindowAsInterface18, i44, token);
                    return z;
                case 34:
                    z = true;
                    IWindow iWindowAsInterface19 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int i45 = parcel.readInt();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAnimatingTypes(iWindowAsInterface19, i45, token2);
                    return z;
                case 35:
                    z = true;
                    IWindow iWindowAsInterface20 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportSystemGestureExclusionChanged(iWindowAsInterface20, arrayListCreateTypedArrayList);
                    return z;
                case 36:
                    z = true;
                    IWindow iWindowAsInterface21 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportDecorViewGestureInterceptionChanged(iWindowAsInterface21, z6);
                    return z;
                case 37:
                    z = true;
                    IWindow iWindowAsInterface22 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(Rect.CREATOR);
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportKeepClearAreasChanged(iWindowAsInterface22, arrayListCreateTypedArrayList2, arrayListCreateTypedArrayList3);
                    return z;
                case 38:
                    z = true;
                    int i46 = parcel.readInt();
                    SurfaceControl surfaceControl3 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    InputTransferToken inputTransferToken = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    InputTransferToken inputTransferToken2 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    String string2 = parcel.readString();
                    InputChannel inputChannel3 = new InputChannel();
                    parcel.enforceNoDataAvail();
                    grantInputChannel(i46, surfaceControl3, strongBinder13, inputTransferToken, i47, i48, i49, i50, strongBinder14, inputTransferToken2, string2, inputChannel3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputChannel3, 1);
                    return z;
                case 39:
                    int i51 = parcel.readInt();
                    SurfaceControl surfaceControl4 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    InputTransferToken inputTransferToken3 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    int i52 = parcel.readInt();
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    int i55 = parcel.readInt();
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    InputTransferToken inputTransferToken4 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    String string3 = parcel.readString();
                    InputChannel inputChannel4 = new InputChannel();
                    int i56 = parcel.readInt();
                    WindowContainerToken windowContainerToken = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    grantInputChannelWithTaskToken(i51, surfaceControl4, strongBinder15, inputTransferToken3, i52, i53, i54, i55, strongBinder16, inputTransferToken4, string3, inputChannel4, i56, windowContainerToken);
                    parcel2.writeNoException();
                    z = true;
                    parcel2.writeTypedObject(inputChannel4, 1);
                    return z;
                case 40:
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    WindowContainerToken windowContainerToken2 = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeWithTaskToken(strongBinder17, windowContainerToken2);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    int i57 = parcel.readInt();
                    SurfaceControl surfaceControl5 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int i58 = parcel.readInt();
                    int i59 = parcel.readInt();
                    int i60 = parcel.readInt();
                    Region region3 = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateInputChannel(strongBinder18, i57, surfaceControl5, i58, i59, i60, region3);
                    return true;
                case 42:
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    int i61 = parcel.readInt();
                    SurfaceControl surfaceControl6 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int i62 = parcel.readInt();
                    int i63 = parcel.readInt();
                    int i64 = parcel.readInt();
                    Region region4 = (Region) parcel.readTypedObject(Region.CREATOR);
                    Region region5 = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateInputChannelWithPointerRegion(strongBinder19, i61, surfaceControl6, i62, i63, i64, region4, region5);
                    return true;
                case 43:
                    IWindow iWindowAsInterface23 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    InputTransferToken inputTransferToken5 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    grantEmbeddedWindowFocus(iWindowAsInterface23, inputTransferToken5, z7);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IWindow iWindowAsInterface24 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    Rect rect8 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    String string4 = parcel.readString();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    generateDisplayHash(iWindowAsInterface24, rect8, string4, remoteCallback);
                    return true;
                case 45:
                    IWindow iWindowAsInterface25 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    OnBackInvokedCallbackInfo onBackInvokedCallbackInfo = (OnBackInvokedCallbackInfo) parcel.readTypedObject(OnBackInvokedCallbackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOnBackInvokedCallbackInfo(iWindowAsInterface25, onBackInvokedCallbackInfo);
                    return true;
                case 46:
                    IWindow iWindowAsInterface26 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearTouchableRegion(iWindowAsInterface26);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    IWindow iWindowAsInterface27 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zCancelDraw = cancelDraw(iWindowAsInterface27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCancelDraw);
                    return true;
                case 48:
                    IWindow iWindowAsInterface28 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMoveFocusToAdjacentWindow = moveFocusToAdjacentWindow(iWindowAsInterface28, i65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMoveFocusToAdjacentWindow);
                    return true;
                case 49:
                    IWindow iWindowAsInterface29 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean z8 = parcel.readBoolean();
                    ImeTracker.Token token3 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyImeWindowVisibilityChangedFromClient(iWindowAsInterface29, z8, token3);
                    return true;
                case 50:
                    IWindow iWindowAsInterface30 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTspDeadzone(iWindowAsInterface30, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IWindow iWindowAsInterface31 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearTspDeadzone(iWindowAsInterface31);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    IWindow iWindowAsInterface32 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTspNoteMode(iWindowAsInterface32, z9);
                    return true;
                case 53:
                    ClipData clipData3 = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    parcel.enforceNoDataAvail();
                    performClipDataUpdate(clipData3);
                    return true;
                case 54:
                    IWindow iWindowAsInterface33 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKeyguardWallpaperTouchAllowed(iWindowAsInterface33, z10);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(layoutParams, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(fArr.length);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i4 = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        inputChannel.readFromParcel(parcelObtain2);
                    }
                    if (parcelObtain2.readInt() != 0) {
                        insetsState.readFromParcel(parcelObtain2);
                    }
                    if (parcelObtain2.readInt() != 0) {
                        array.readFromParcel(parcelObtain2);
                    }
                    if (parcelObtain2.readInt() != 0) {
                        rect.readFromParcel(parcelObtain2);
                    }
                    parcelObtain2.readFloatArray(fArr);
                    return i4;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int addToDisplayAsUser(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(layoutParams, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(fArr.length);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i5 = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        inputChannel.readFromParcel(parcelObtain2);
                    }
                    if (parcelObtain2.readInt() != 0) {
                        insetsState.readFromParcel(parcelObtain2);
                    }
                    if (parcelObtain2.readInt() != 0) {
                        array.readFromParcel(parcelObtain2);
                    }
                    if (parcelObtain2.readInt() != 0) {
                        rect.readFromParcel(parcelObtain2);
                    }
                    parcelObtain2.readFloatArray(fArr);
                    return i5;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int addToDisplayWithoutInputChannel(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, InsetsState insetsState, Rect rect, float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(layoutParams, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(fArr.length);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i3 = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        insetsState.readFromParcel(parcelObtain2);
                    }
                    if (parcelObtain2.readInt() != 0) {
                        rect.readFromParcel(parcelObtain2);
                    }
                    parcelObtain2.readFloatArray(fArr);
                    return i3;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void remove(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int relayout(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, WindowRelayoutResult windowRelayoutResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(layoutParams, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i7 = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        windowRelayoutResult.readFromParcel(parcelObtain2);
                    }
                    return i7;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void relayoutAsync(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(layoutParams, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean outOfMemory(IWindow iWindow) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setInsets(IWindow iWindow, int i, Rect rect, Rect rect2, Region region, Rect rect3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeTypedObject(rect2, 0);
                    parcelObtain.writeTypedObject(region, 0);
                    parcelObtain.writeTypedObject(rect3, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void finishDrawing(IWindow iWindow, SurfaceControl.Transaction transaction, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(transaction, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public IBinder performDrag(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeFloat(f3);
                    parcelObtain.writeFloat(f4);
                    parcelObtain.writeTypedObject(clipData, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public IBinder performDragWithArea(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData, RectF rectF, Point point) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeFloat(f3);
                    parcelObtain.writeFloat(f4);
                    parcelObtain.writeTypedObject(clipData, 0);
                    parcelObtain.writeTypedObject(rectF, 0);
                    parcelObtain.writeTypedObject(point, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public IBinder getDragStateInputToken() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int getDragPointerId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int getDragDeviceId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean dropForAccessibility(IWindow iWindow, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportDropResult(IWindow iWindow, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void cancelDragAndDrop(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void dragRecipientEntered(IWindow iWindow) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void dragRecipientExited(IWindow iWindow) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setWallpaperPosition(IBinder iBinder, float f, float f2, float f3, float f4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeFloat(f3);
                    parcelObtain.writeFloat(f4);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setWallpaperZoomOut(IBinder iBinder, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setShouldZoomOutWallpaper(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void wallpaperOffsetsComplete(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setWallpaperDisplayOffset(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void sendWallpaperCommand(IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public IWindowId getWindowId(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IWindowId.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void pokeDrawLock(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean startMovingTask(IWindow iWindow, float f, float f2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void finishMovingTask(IWindow iWindow) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateTapExcludeRegion(IWindow iWindow, Region region) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(region, 0);
                    this.mRemote.transact(32, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateRequestedVisibleTypes(IWindow iWindow, int i, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(33, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateAnimatingTypes(IWindow iWindow, int i, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportSystemGestureExclusionChanged(IWindow iWindow, List<Rect> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(35, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportDecorViewGestureInterceptionChanged(IWindow iWindow, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportKeepClearAreasChanged(IWindow iWindow, List<Rect> list, List<Rect> list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedList(list2, 0);
                    this.mRemote.transact(37, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void grantInputChannel(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(inputTransferToken, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeTypedObject(inputTransferToken2, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        inputChannel.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void grantInputChannelWithTaskToken(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel, int i6, WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(inputTransferToken, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeTypedObject(inputTransferToken2, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        inputChannel.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void removeWithTaskToken(IBinder iBinder, WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateInputChannel(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(region, 0);
                    this.mRemote.transact(41, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateInputChannelWithPointerRegion(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region, Region region2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(region, 0);
                    parcelObtain.writeTypedObject(region2, 0);
                    this.mRemote.transact(42, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void grantEmbeddedWindowFocus(IWindow iWindow, InputTransferToken inputTransferToken, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(inputTransferToken, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void generateDisplayHash(IWindow iWindow, Rect rect, String str, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(44, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setOnBackInvokedCallbackInfo(IWindow iWindow, OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(onBackInvokedCallbackInfo, 0);
                    this.mRemote.transact(45, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void clearTouchableRegion(IWindow iWindow) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean cancelDraw(IWindow iWindow) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean moveFocusToAdjacentWindow(IWindow iWindow, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void notifyImeWindowVisibilityChangedFromClient(IWindow iWindow, boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(49, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setTspDeadzone(IWindow iWindow, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void clearTspDeadzone(IWindow iWindow) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setTspNoteMode(IWindow iWindow, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(52, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void performClipDataUpdate(ClipData clipData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clipData, 0);
                    this.mRemote.transact(53, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setKeyguardWallpaperTouchAllowed(IWindow iWindow, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(54, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
