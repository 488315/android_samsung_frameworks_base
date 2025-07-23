package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Size;
import android.view.Choreographer;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewConfiguration;
import android.window.InputTransferToken;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.wm.shell.windowdecor.DragResizeInputListener;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DragResizeInputListener implements AutoCloseable {
    public final ShellExecutor mBgExecutor;
    public final Choreographer mChoreographer;
    final IBinder mClientToken;
    public boolean mClosed;
    public final Context mContext;
    public final SurfaceControl mDecorationSurface;
    public final DesktopModeEventLogger mDesktopModeEventLogger;
    public final DisplayController mDisplayController;
    public final int mDisplayId;
    public final DragPositioningCallback mDragPositioningCallback;
    public final TaskResizeInputEventReceiverFactory mEventReceiverFactory;
    public final Handler mHandler;
    public final DragResizeInputListener$$ExternalSyntheticLambda1 mInitInputChannels;
    public InputChannel mInputChannel;
    public TaskResizeInputEventReceiver mInputEventReceiver;
    public SurfaceControl mInputSinkSurface;
    public final Region mLastTouchRegion;
    public final List mOnInitializedCallbacks;
    public final Region mPointerTouchableRegion;
    final IBinder mSinkClientToken;
    public InputChannel mSinkInputChannel;
    public final Supplier mSurfaceControlBuilderSupplier;
    public final Supplier mSurfaceControlTransactionSupplier;
    public final ActivityManager.RunningTaskInfo mTaskInfo;
    public final Region mTouchRegion;
    public boolean mTouchable;
    public final IWindowSession mWindowSession;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DefaultTaskResizeInputEventReceiverFactory implements TaskResizeInputEventReceiverFactory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class InputSetUpResult {
        public final InputChannel mInputChannel;
        public final SurfaceControl mInputSinkSurface;
        public final InputChannel mSinkInputChannel;

        public InputSetUpResult(SurfaceControl surfaceControl, InputChannel inputChannel, InputChannel inputChannel2) {
            this.mInputSinkSurface = surfaceControl;
            this.mInputChannel = inputChannel;
            this.mSinkInputChannel = inputChannel2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TaskResizeInputEventReceiver extends InputEventReceiver implements DragDetector.MotionEventHandler {
        public final DragPositioningCallback mCallback;
        public final Choreographer mChoreographer;
        public final DragResizeInputListener$$ExternalSyntheticLambda0 mConsumeBatchEventRunnable;
        public boolean mConsumeBatchEventScheduled;
        public final Context mContext;
        public final DesktopModeEventLogger mDesktopModeEventLogger;
        public final Supplier mDisplayLayoutSizeSupplier;
        public final DragDetector mDragDetector;
        public int mDragPointerId;
        public DragResizeWindowGeometry mDragResizeWindowGeometry;
        public Rect mDragStartTaskBounds;
        public final Handler mHandler;
        public final InputChannel mInputChannel;
        public final InputManager mInputManager;
        public boolean mIsPointerInput;
        public boolean mIsStylusFromTouchPad;
        public boolean mIsStylusInput;
        public int mLastCursorType;
        public MotionEvent mLastMotionEventOnDown;
        public DesktopModeEventLogger.Companion.ResizeTrigger mResizeTrigger;
        public final AnonymousClass1 mSetDefaultPointerRunnable;
        public boolean mShouldHandleEvents;
        public final ActivityManager.RunningTaskInfo mTaskInfo;
        public final Rect mTmpRect;
        public Region mTouchRegion;
        public final Consumer mTouchRegionConsumer;

        @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
        public final boolean handleMotionEvent(View view, MotionEvent motionEvent) {
            boolean isFromSource = motionEvent.isFromSource(16386);
            this.mIsStylusInput = isFromSource;
            this.mIsPointerInput = isFromSource || motionEvent.isFromSource(8194);
            boolean z = this.mIsStylusInput && ((motionEvent.getFlags() & 67108864) != 0);
            this.mIsStylusFromTouchPad = z;
            this.mInputManager.setIsStylusFromTouchpad(z);
            this.mDragResizeWindowGeometry.mIsPointerInput = this.mIsPointerInput;
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked != 2) {
                        if (actionMasked != 3) {
                            if (actionMasked == 7 || actionMasked == 9) {
                                updateCursorType(motionEvent.getDisplayId(), motionEvent.getDeviceId(), motionEvent.getPointerId(0), this.mIsStylusInput ? motionEvent.getX() : motionEvent.getXCursorPosition(), this.mIsStylusInput ? motionEvent.getY() : motionEvent.getYCursorPosition());
                                return true;
                            }
                            if (actionMasked == 10) {
                                this.mHandler.postDelayed(this.mSetDefaultPointerRunnable, 100L);
                                return true;
                            }
                        }
                    } else if (this.mShouldHandleEvents) {
                        this.mInputManager.pilferPointers(this.mInputChannel.getToken());
                        int findPointerIndex = motionEvent.findPointerIndex(this.mDragPointerId);
                        if (findPointerIndex >= 0) {
                            updateInputSinkRegionForDrag(this.mCallback.onDragPositioningMove(motionEvent.getRawX(findPointerIndex), motionEvent.getRawY(findPointerIndex), motionEvent.getDisplayId()));
                            return true;
                        }
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DESKTOP_MODE_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 8943025630346689205L, 0, "DragResizeInputListener");
                            return false;
                        }
                    }
                }
                if (this.mShouldHandleEvents) {
                    int findPointerIndex2 = motionEvent.findPointerIndex(this.mDragPointerId);
                    if (findPointerIndex2 < 0) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DESKTOP_MODE_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 6603006212180425160L, 4, "DragResizeInputListener", Long.valueOf(motionEvent.getActionMasked()));
                        }
                        this.mCallback.onDragPositioningEnd(-1.0f, -1.0f, motionEvent.getDisplayId());
                        this.mShouldHandleEvents = false;
                        return false;
                    }
                    Rect onDragPositioningEnd = this.mCallback.onDragPositioningEnd(motionEvent.getRawX(findPointerIndex2), motionEvent.getRawY(findPointerIndex2), motionEvent.getDisplayId());
                    if (onDragPositioningEnd.equals(this.mDragStartTaskBounds)) {
                        this.mTouchRegionConsumer.accept(this.mTouchRegion);
                    }
                    DesktopModeEventLogger desktopModeEventLogger = this.mDesktopModeEventLogger;
                    DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger = this.mResizeTrigger;
                    MotionEvent motionEvent2 = this.mLastMotionEventOnDown;
                    DesktopModeEventLogger.Companion.getClass();
                    desktopModeEventLogger.logTaskResizingEnded(resizeTrigger, DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent2), this.mTaskInfo, Integer.valueOf(onDragPositioningEnd.width()), Integer.valueOf(onDragPositioningEnd.height()), null, (Size) this.mDisplayLayoutSizeSupplier.get());
                }
                this.mShouldHandleEvents = false;
                this.mDragPointerId = -1;
                return true;
            }
            boolean shouldHandleEvent = this.mDragResizeWindowGeometry.shouldHandleEvent(motionEvent, new Point());
            this.mShouldHandleEvents = shouldHandleEvent;
            if (shouldHandleEvent) {
                this.mDragPointerId = motionEvent.getPointerId(0);
                float x = motionEvent.getX(0);
                float y = motionEvent.getY(0);
                float rawX = motionEvent.getRawX(0);
                float rawY = motionEvent.getRawY(0);
                int calculateCtrlType = this.mDragResizeWindowGeometry.calculateCtrlType(x, y, (motionEvent.getSource() & PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL) == 4098, DragResizeWindowGeometry.isEdgeResizePermitted(motionEvent));
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DESKTOP_MODE_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 2008231537477103770L, 4, "DragResizeInputListener", Long.valueOf(calculateCtrlType));
                }
                this.mDragStartTaskBounds = this.mCallback.onDragPositioningStart(calculateCtrlType, rawX, rawY, motionEvent.getDisplayId());
                this.mLastMotionEventOnDown = motionEvent;
                DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger2 = (calculateCtrlType == 8 || calculateCtrlType == 4 || calculateCtrlType == 2 || calculateCtrlType == 1) ? DesktopModeEventLogger.Companion.ResizeTrigger.EDGE : DesktopModeEventLogger.Companion.ResizeTrigger.CORNER;
                this.mResizeTrigger = resizeTrigger2;
                DesktopModeEventLogger desktopModeEventLogger2 = this.mDesktopModeEventLogger;
                DesktopModeEventLogger.Companion.getClass();
                desktopModeEventLogger2.logTaskResizingStarted(resizeTrigger2, DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent), this.mTaskInfo, Integer.valueOf(this.mDragStartTaskBounds.width()), Integer.valueOf(this.mDragStartTaskBounds.height()), null, (Size) this.mDisplayLayoutSizeSupplier.get());
                updateInputSinkRegionForDrag(this.mDragStartTaskBounds);
                updateCursorType(motionEvent.getDisplayId(), motionEvent.getDeviceId(), motionEvent.getPointerId(0), this.mIsStylusInput ? motionEvent.getX() : motionEvent.getXCursorPosition(), this.mIsStylusInput ? motionEvent.getY() : motionEvent.getYCursorPosition());
                return true;
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DESKTOP_MODE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -1822972204400427909L, 0, "DragResizeInputListener");
            }
            return false;
        }

        public final void onBatchedInputEventPending(int i) {
            if (this.mConsumeBatchEventScheduled) {
                return;
            }
            this.mChoreographer.postCallback(0, this.mConsumeBatchEventRunnable, null);
            this.mConsumeBatchEventScheduled = true;
        }

        public final void onInputEvent(InputEvent inputEvent) {
            finishInputEvent(inputEvent, !(inputEvent instanceof MotionEvent) ? false : this.mDragDetector.onMotionEvent(null, (MotionEvent) inputEvent));
        }

        public final void updateCursorType(int i, int i2, int i3, float f, float f2) {
            int i4;
            switch (this.mDragResizeWindowGeometry.calculateCtrlType(f, f2, false, true)) {
                case 1:
                case 2:
                    i4 = EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL;
                    break;
                case 3:
                case 7:
                default:
                    i4 = 1000;
                    break;
                case 4:
                case 8:
                    i4 = EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED;
                    break;
                case 5:
                case 10:
                    i4 = 1017;
                    break;
                case 6:
                case 9:
                    i4 = EnterpriseContainerCallback.CONTAINER_CANCELLED;
                    break;
            }
            if (this.mIsStylusInput && !this.mIsStylusFromTouchPad) {
                if (i4 != 20001) {
                    switch (i4) {
                        case EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL /* 1014 */:
                            i4 = 20006;
                            break;
                        case EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED /* 1015 */:
                            i4 = 20007;
                            break;
                        case EnterpriseContainerCallback.CONTAINER_CANCELLED /* 1016 */:
                            i4 = 20009;
                            break;
                        case 1017:
                            i4 = 20008;
                            break;
                    }
                } else {
                    i4 = 10121;
                }
            }
            int i5 = this.mLastCursorType;
            if (i5 == i4 && i4 == 1000) {
                return;
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DESKTOP_MODE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 2613953847014407996L, 20, "DragResizeInputListener", Long.valueOf(i5), Long.valueOf(i4));
            }
            this.mInputManager.setPointerIcon(PointerIcon.getSystemIcon(this.mContext, i4), i, i2, i3, this.mInputChannel.getToken());
            this.mLastCursorType = i4;
        }

        public final void updateInputSinkRegionForDrag(Rect rect) {
            this.mTmpRect.set(rect);
            Size size = (Size) this.mDisplayLayoutSizeSupplier.get();
            int i = rect.left;
            Region region = new Region(-i, -rect.top, size.getWidth() + (-i), size.getHeight() + (-rect.top));
            this.mTmpRect.offsetTo(0, 0);
            region.op(this.mTmpRect, Region.Op.DIFFERENCE);
            this.mTouchRegionConsumer.accept(region);
        }

        /* JADX WARN: Type inference failed for: r0v4, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$TaskResizeInputEventReceiver$1] */
        private TaskResizeInputEventReceiver(Context context, ActivityManager.RunningTaskInfo runningTaskInfo, InputChannel inputChannel, DragPositioningCallback dragPositioningCallback, Handler handler, Choreographer choreographer, Supplier<Size> supplier, Consumer<Region> consumer, DesktopModeEventLogger desktopModeEventLogger) {
            super(inputChannel, handler.getLooper());
            this.mTmpRect = new Rect();
            this.mLastCursorType = 1000;
            this.mDragPointerId = -1;
            this.mSetDefaultPointerRunnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener.TaskResizeInputEventReceiver.1
                @Override // java.lang.Runnable
                public final void run() {
                    TaskResizeInputEventReceiver.this.mInputManager.setPointerIconType(1000);
                }
            };
            this.mResizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.UNKNOWN_RESIZE_TRIGGER;
            this.mContext = context;
            this.mTaskInfo = runningTaskInfo;
            this.mInputManager = (InputManager) context.getSystemService(InputManager.class);
            this.mInputChannel = inputChannel;
            this.mCallback = dragPositioningCallback;
            this.mChoreographer = choreographer;
            this.mHandler = handler;
            this.mConsumeBatchEventRunnable = new DragResizeInputListener$$ExternalSyntheticLambda0(this, 1);
            this.mDragDetector = new DragDetector(this, 0L, ViewConfiguration.get(context).getScaledTouchSlop());
            this.mDisplayLayoutSizeSupplier = supplier;
            this.mTouchRegionConsumer = consumer;
            this.mDesktopModeEventLogger = desktopModeEventLogger;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TaskResizeInputEventReceiverFactory {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda1, java.lang.Runnable] */
    public DragResizeInputListener(Context context, IWindowSession iWindowSession, final ShellExecutor shellExecutor, ShellExecutor shellExecutor2, TaskResizeInputEventReceiverFactory taskResizeInputEventReceiverFactory, ActivityManager.RunningTaskInfo runningTaskInfo, Handler handler, Choreographer choreographer, int i, SurfaceControl surfaceControl, DragPositioningCallback dragPositioningCallback, Supplier<SurfaceControl.Builder> supplier, Supplier<SurfaceControl.Transaction> supplier2, DisplayController displayController, DesktopModeEventLogger desktopModeEventLogger, final InputChannel inputChannel, final InputChannel inputChannel2) {
        this.mTouchRegion = new Region();
        this.mOnInitializedCallbacks = new ArrayList();
        this.mClosed = false;
        this.mPointerTouchableRegion = new Region();
        this.mLastTouchRegion = new Region();
        this.mContext = context;
        this.mWindowSession = iWindowSession;
        this.mBgExecutor = shellExecutor2;
        this.mEventReceiverFactory = taskResizeInputEventReceiverFactory;
        this.mTaskInfo = runningTaskInfo;
        this.mHandler = handler;
        this.mChoreographer = choreographer;
        this.mDisplayId = i;
        SurfaceControl build = supplier.get().setName("").build();
        this.mDecorationSurface = build;
        build.copyFrom(surfaceControl, "DragResizeInputListener");
        this.mDragPositioningCallback = dragPositioningCallback;
        this.mSurfaceControlBuilderSupplier = supplier;
        this.mSurfaceControlTransactionSupplier = supplier2;
        this.mDisplayController = displayController;
        this.mDesktopModeEventLogger = desktopModeEventLogger;
        this.mClientToken = new Binder();
        this.mSinkClientToken = new Binder();
        ?? r2 = new Runnable() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ShellExecutor shellExecutor3;
                IBinder iBinder;
                Supplier supplier3;
                Supplier supplier4;
                SurfaceControl build2;
                SurfaceControl surfaceControl2;
                InputChannel inputChannel3;
                final DragResizeInputListener dragResizeInputListener = DragResizeInputListener.this;
                InputChannel inputChannel4 = inputChannel;
                InputChannel inputChannel5 = inputChannel2;
                ShellExecutor shellExecutor4 = shellExecutor;
                int i2 = dragResizeInputListener.mDisplayId;
                IWindowSession iWindowSession2 = dragResizeInputListener.mWindowSession;
                SurfaceControl surfaceControl3 = dragResizeInputListener.mDecorationSurface;
                IBinder iBinder2 = dragResizeInputListener.mClientToken;
                IBinder iBinder3 = dragResizeInputListener.mSinkClientToken;
                Supplier supplier5 = dragResizeInputListener.mSurfaceControlBuilderSupplier;
                Supplier supplier6 = dragResizeInputListener.mSurfaceControlTransactionSupplier;
                Trace.beginSection("DragResizeInputListener#setUpInputChannels");
                InputTransferToken inputTransferToken = new InputTransferToken();
                try {
                    iBinder = iBinder3;
                    supplier3 = supplier5;
                    supplier4 = supplier6;
                    shellExecutor3 = shellExecutor4;
                    try {
                        iWindowSession2.grantInputChannel(i2, surfaceControl3, iBinder2, (InputTransferToken) null, 8, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, 4, 2, (IBinder) null, inputTransferToken, "DragResizeInputListener of " + surfaceControl3, inputChannel4);
                    } catch (RemoteException e) {
                        e = e;
                        inputTransferToken = inputTransferToken;
                        e.rethrowFromSystemServer();
                        build2 = ((SurfaceControl.Builder) supplier3.get()).setName("TaskInputSink of " + surfaceControl3).setContainerLayer().setParent(surfaceControl3).setCallsite("DragResizeInputListener.setUpInputChannels").build();
                        ((SurfaceControl.Transaction) supplier4.get()).setLayer(build2, -2).show(build2).apply();
                        try {
                            iWindowSession2.grantInputChannel(i2, build2, iBinder, (InputTransferToken) null, 8, 0, 1, 2022, (IBinder) null, inputTransferToken, "TaskInputSink of " + surfaceControl3, inputChannel5);
                            surfaceControl2 = build2;
                            inputChannel3 = inputChannel5;
                        } catch (RemoteException e2) {
                            e = e2;
                            surfaceControl2 = build2;
                            inputChannel3 = inputChannel5;
                            e.rethrowFromSystemServer();
                            Trace.endSection();
                            final DragResizeInputListener.InputSetUpResult inputSetUpResult = new DragResizeInputListener.InputSetUpResult(surfaceControl2, inputChannel4, inputChannel3);
                            shellExecutor3.execute(new Runnable() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda2
                                /* JADX WARN: Type inference failed for: r8v0, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda3] */
                                /* JADX WARN: Type inference failed for: r9v0, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda4] */
                                @Override // java.lang.Runnable
                                public final void run() {
                                    final DragResizeInputListener dragResizeInputListener2 = DragResizeInputListener.this;
                                    DragResizeInputListener.InputSetUpResult inputSetUpResult2 = inputSetUpResult;
                                    if (dragResizeInputListener2.mClosed) {
                                        inputSetUpResult2.mInputChannel.dispose();
                                        inputSetUpResult2.mSinkInputChannel.dispose();
                                        ((SurfaceControl.Transaction) dragResizeInputListener2.mSurfaceControlTransactionSupplier.get()).remove(inputSetUpResult2.mInputSinkSurface).apply();
                                        return;
                                    }
                                    dragResizeInputListener2.mInputSinkSurface = inputSetUpResult2.mInputSinkSurface;
                                    dragResizeInputListener2.mInputChannel = inputSetUpResult2.mInputChannel;
                                    dragResizeInputListener2.mSinkInputChannel = inputSetUpResult2.mSinkInputChannel;
                                    Trace.beginSection("DragResizeInputListener#ctor-initReceiver");
                                    DragResizeInputListener.TaskResizeInputEventReceiverFactory taskResizeInputEventReceiverFactory2 = dragResizeInputListener2.mEventReceiverFactory;
                                    Context context2 = dragResizeInputListener2.mContext;
                                    ActivityManager.RunningTaskInfo runningTaskInfo2 = dragResizeInputListener2.mTaskInfo;
                                    InputChannel inputChannel6 = dragResizeInputListener2.mInputChannel;
                                    DragPositioningCallback dragPositioningCallback2 = dragResizeInputListener2.mDragPositioningCallback;
                                    Handler handler2 = dragResizeInputListener2.mHandler;
                                    Choreographer choreographer2 = dragResizeInputListener2.mChoreographer;
                                    ?? r8 = new Supplier() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            DragResizeInputListener dragResizeInputListener3 = DragResizeInputListener.this;
                                            DisplayLayout displayLayout = dragResizeInputListener3.mDisplayController.getDisplayLayout(dragResizeInputListener3.mDisplayId);
                                            return new Size(displayLayout.mWidth, displayLayout.mHeight);
                                        }
                                    };
                                    ?? r9 = new Consumer() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda4
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            DragResizeInputListener.this.updateSinkInputChannel();
                                        }
                                    };
                                    DesktopModeEventLogger desktopModeEventLogger2 = dragResizeInputListener2.mDesktopModeEventLogger;
                                    ((DragResizeInputListener.DefaultTaskResizeInputEventReceiverFactory) taskResizeInputEventReceiverFactory2).getClass();
                                    DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver = new DragResizeInputListener.TaskResizeInputEventReceiver(context2, runningTaskInfo2, inputChannel6, dragPositioningCallback2, handler2, choreographer2, (DragResizeInputListener$$ExternalSyntheticLambda3) r8, (DragResizeInputListener$$ExternalSyntheticLambda4) r9, desktopModeEventLogger2);
                                    dragResizeInputListener2.mInputEventReceiver = taskResizeInputEventReceiver;
                                    taskResizeInputEventReceiver.mDragDetector.mTouchSlop = ViewConfiguration.get(dragResizeInputListener2.mContext).getScaledTouchSlop();
                                    ArrayList arrayList = (ArrayList) dragResizeInputListener2.mOnInitializedCallbacks;
                                    int size = arrayList.size();
                                    int i3 = 0;
                                    while (i3 < size) {
                                        Object obj = arrayList.get(i3);
                                        i3++;
                                        ((Runnable) obj).run();
                                    }
                                    ((ArrayList) dragResizeInputListener2.mOnInitializedCallbacks).clear();
                                    Trace.endSection();
                                }
                            });
                        }
                        Trace.endSection();
                        final DragResizeInputListener.InputSetUpResult inputSetUpResult2 = new DragResizeInputListener.InputSetUpResult(surfaceControl2, inputChannel4, inputChannel3);
                        shellExecutor3.execute(new Runnable() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda2
                            /* JADX WARN: Type inference failed for: r8v0, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda3] */
                            /* JADX WARN: Type inference failed for: r9v0, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda4] */
                            @Override // java.lang.Runnable
                            public final void run() {
                                final DragResizeInputListener dragResizeInputListener2 = DragResizeInputListener.this;
                                DragResizeInputListener.InputSetUpResult inputSetUpResult22 = inputSetUpResult2;
                                if (dragResizeInputListener2.mClosed) {
                                    inputSetUpResult22.mInputChannel.dispose();
                                    inputSetUpResult22.mSinkInputChannel.dispose();
                                    ((SurfaceControl.Transaction) dragResizeInputListener2.mSurfaceControlTransactionSupplier.get()).remove(inputSetUpResult22.mInputSinkSurface).apply();
                                    return;
                                }
                                dragResizeInputListener2.mInputSinkSurface = inputSetUpResult22.mInputSinkSurface;
                                dragResizeInputListener2.mInputChannel = inputSetUpResult22.mInputChannel;
                                dragResizeInputListener2.mSinkInputChannel = inputSetUpResult22.mSinkInputChannel;
                                Trace.beginSection("DragResizeInputListener#ctor-initReceiver");
                                DragResizeInputListener.TaskResizeInputEventReceiverFactory taskResizeInputEventReceiverFactory2 = dragResizeInputListener2.mEventReceiverFactory;
                                Context context2 = dragResizeInputListener2.mContext;
                                ActivityManager.RunningTaskInfo runningTaskInfo2 = dragResizeInputListener2.mTaskInfo;
                                InputChannel inputChannel6 = dragResizeInputListener2.mInputChannel;
                                DragPositioningCallback dragPositioningCallback2 = dragResizeInputListener2.mDragPositioningCallback;
                                Handler handler2 = dragResizeInputListener2.mHandler;
                                Choreographer choreographer2 = dragResizeInputListener2.mChoreographer;
                                ?? r8 = new Supplier() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        DragResizeInputListener dragResizeInputListener3 = DragResizeInputListener.this;
                                        DisplayLayout displayLayout = dragResizeInputListener3.mDisplayController.getDisplayLayout(dragResizeInputListener3.mDisplayId);
                                        return new Size(displayLayout.mWidth, displayLayout.mHeight);
                                    }
                                };
                                ?? r9 = new Consumer() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda4
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        DragResizeInputListener.this.updateSinkInputChannel();
                                    }
                                };
                                DesktopModeEventLogger desktopModeEventLogger2 = dragResizeInputListener2.mDesktopModeEventLogger;
                                ((DragResizeInputListener.DefaultTaskResizeInputEventReceiverFactory) taskResizeInputEventReceiverFactory2).getClass();
                                DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver = new DragResizeInputListener.TaskResizeInputEventReceiver(context2, runningTaskInfo2, inputChannel6, dragPositioningCallback2, handler2, choreographer2, (DragResizeInputListener$$ExternalSyntheticLambda3) r8, (DragResizeInputListener$$ExternalSyntheticLambda4) r9, desktopModeEventLogger2);
                                dragResizeInputListener2.mInputEventReceiver = taskResizeInputEventReceiver;
                                taskResizeInputEventReceiver.mDragDetector.mTouchSlop = ViewConfiguration.get(dragResizeInputListener2.mContext).getScaledTouchSlop();
                                ArrayList arrayList = (ArrayList) dragResizeInputListener2.mOnInitializedCallbacks;
                                int size = arrayList.size();
                                int i3 = 0;
                                while (i3 < size) {
                                    Object obj = arrayList.get(i3);
                                    i3++;
                                    ((Runnable) obj).run();
                                }
                                ((ArrayList) dragResizeInputListener2.mOnInitializedCallbacks).clear();
                                Trace.endSection();
                            }
                        });
                    }
                } catch (RemoteException e3) {
                    e = e3;
                    shellExecutor3 = shellExecutor4;
                    iBinder = iBinder3;
                    supplier3 = supplier5;
                    supplier4 = supplier6;
                }
                build2 = ((SurfaceControl.Builder) supplier3.get()).setName("TaskInputSink of " + surfaceControl3).setContainerLayer().setParent(surfaceControl3).setCallsite("DragResizeInputListener.setUpInputChannels").build();
                ((SurfaceControl.Transaction) supplier4.get()).setLayer(build2, -2).show(build2).apply();
                try {
                    iWindowSession2.grantInputChannel(i2, build2, iBinder, (InputTransferToken) null, 8, 0, 1, 2022, (IBinder) null, inputTransferToken, "TaskInputSink of " + surfaceControl3, inputChannel5);
                    surfaceControl2 = build2;
                    inputChannel3 = inputChannel5;
                } catch (RemoteException e4) {
                    e = e4;
                    surfaceControl2 = build2;
                    inputChannel3 = inputChannel5;
                }
                Trace.endSection();
                final DragResizeInputListener.InputSetUpResult inputSetUpResult22 = new DragResizeInputListener.InputSetUpResult(surfaceControl2, inputChannel4, inputChannel3);
                shellExecutor3.execute(new Runnable() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda2
                    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda3] */
                    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda4] */
                    @Override // java.lang.Runnable
                    public final void run() {
                        final DragResizeInputListener dragResizeInputListener2 = DragResizeInputListener.this;
                        DragResizeInputListener.InputSetUpResult inputSetUpResult222 = inputSetUpResult22;
                        if (dragResizeInputListener2.mClosed) {
                            inputSetUpResult222.mInputChannel.dispose();
                            inputSetUpResult222.mSinkInputChannel.dispose();
                            ((SurfaceControl.Transaction) dragResizeInputListener2.mSurfaceControlTransactionSupplier.get()).remove(inputSetUpResult222.mInputSinkSurface).apply();
                            return;
                        }
                        dragResizeInputListener2.mInputSinkSurface = inputSetUpResult222.mInputSinkSurface;
                        dragResizeInputListener2.mInputChannel = inputSetUpResult222.mInputChannel;
                        dragResizeInputListener2.mSinkInputChannel = inputSetUpResult222.mSinkInputChannel;
                        Trace.beginSection("DragResizeInputListener#ctor-initReceiver");
                        DragResizeInputListener.TaskResizeInputEventReceiverFactory taskResizeInputEventReceiverFactory2 = dragResizeInputListener2.mEventReceiverFactory;
                        Context context2 = dragResizeInputListener2.mContext;
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = dragResizeInputListener2.mTaskInfo;
                        InputChannel inputChannel6 = dragResizeInputListener2.mInputChannel;
                        DragPositioningCallback dragPositioningCallback2 = dragResizeInputListener2.mDragPositioningCallback;
                        Handler handler2 = dragResizeInputListener2.mHandler;
                        Choreographer choreographer2 = dragResizeInputListener2.mChoreographer;
                        ?? r8 = new Supplier() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda3
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                DragResizeInputListener dragResizeInputListener3 = DragResizeInputListener.this;
                                DisplayLayout displayLayout = dragResizeInputListener3.mDisplayController.getDisplayLayout(dragResizeInputListener3.mDisplayId);
                                return new Size(displayLayout.mWidth, displayLayout.mHeight);
                            }
                        };
                        ?? r9 = new Consumer() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                DragResizeInputListener.this.updateSinkInputChannel();
                            }
                        };
                        DesktopModeEventLogger desktopModeEventLogger2 = dragResizeInputListener2.mDesktopModeEventLogger;
                        ((DragResizeInputListener.DefaultTaskResizeInputEventReceiverFactory) taskResizeInputEventReceiverFactory2).getClass();
                        DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver = new DragResizeInputListener.TaskResizeInputEventReceiver(context2, runningTaskInfo2, inputChannel6, dragPositioningCallback2, handler2, choreographer2, (DragResizeInputListener$$ExternalSyntheticLambda3) r8, (DragResizeInputListener$$ExternalSyntheticLambda4) r9, desktopModeEventLogger2);
                        dragResizeInputListener2.mInputEventReceiver = taskResizeInputEventReceiver;
                        taskResizeInputEventReceiver.mDragDetector.mTouchSlop = ViewConfiguration.get(dragResizeInputListener2.mContext).getScaledTouchSlop();
                        ArrayList arrayList = (ArrayList) dragResizeInputListener2.mOnInitializedCallbacks;
                        int size = arrayList.size();
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj = arrayList.get(i3);
                            i3++;
                            ((Runnable) obj).run();
                        }
                        ((ArrayList) dragResizeInputListener2.mOnInitializedCallbacks).clear();
                        Trace.endSection();
                    }
                });
            }
        };
        this.mInitInputChannels = r2;
        shellExecutor2.execute(r2);
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.mClosed = true;
        DragResizeInputListener$$ExternalSyntheticLambda1 dragResizeInputListener$$ExternalSyntheticLambda1 = this.mInitInputChannels;
        if (dragResizeInputListener$$ExternalSyntheticLambda1 != null) {
            ((HandlerExecutor) this.mBgExecutor).removeCallbacks(dragResizeInputListener$$ExternalSyntheticLambda1);
        }
        TaskResizeInputEventReceiver taskResizeInputEventReceiver = this.mInputEventReceiver;
        if (taskResizeInputEventReceiver != null) {
            taskResizeInputEventReceiver.dispose();
        }
        InputChannel inputChannel = this.mInputChannel;
        if (inputChannel != null) {
            inputChannel.dispose();
        }
        InputChannel inputChannel2 = this.mSinkInputChannel;
        if (inputChannel2 != null) {
            inputChannel2.dispose();
        }
        if (this.mInputSinkSurface != null) {
            ((SurfaceControl.Transaction) this.mSurfaceControlTransactionSupplier.get()).remove(this.mInputSinkSurface).apply();
        }
        this.mBgExecutor.execute(new DragResizeInputListener$$ExternalSyntheticLambda0(this, 0));
    }

    public final boolean setGeometry(DragResizeWindowGeometry dragResizeWindowGeometry, int i, boolean z) {
        if (dragResizeWindowGeometry.equals(this.mInputEventReceiver.mDragResizeWindowGeometry) && (!CoreRune.MW_CAPTION_FREEFORM || z == this.mTouchable)) {
            return false;
        }
        this.mInputEventReceiver.mDragDetector.mTouchSlop = i;
        this.mTouchRegion.setEmpty();
        this.mPointerTouchableRegion.setEmpty();
        Region region = this.mTouchRegion;
        Region region2 = this.mPointerTouchableRegion;
        dragResizeWindowGeometry.mTaskEdges.union(region);
        dragResizeWindowGeometry.mLargeTaskCorners.union(region);
        if (region2 != null) {
            dragResizeWindowGeometry.mPointerTaskEdges.union(region2);
            dragResizeWindowGeometry.mFineTaskCorners.union(region2);
        }
        TaskResizeInputEventReceiver taskResizeInputEventReceiver = this.mInputEventReceiver;
        taskResizeInputEventReceiver.mDragResizeWindowGeometry = dragResizeWindowGeometry;
        Region region3 = this.mTouchRegion;
        taskResizeInputEventReceiver.mTouchRegion = region3;
        if (CoreRune.MW_CAPTION_FREEFORM) {
            this.mTouchable = z;
            Region region4 = this.mLastTouchRegion;
            if (this.mTaskInfo.displayId != 0) {
                region3 = this.mPointerTouchableRegion;
            }
            region4.set(region3);
            try {
                this.mWindowSession.updateInputChannelWithPointerRegion(this.mInputChannel.getToken(), this.mDisplayId, this.mDecorationSurface, !z ? 24 : 8, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, 4, this.mLastTouchRegion, this.mPointerTouchableRegion);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        } else {
            try {
                this.mWindowSession.updateInputChannel(this.mInputChannel.getToken(), this.mDisplayId, this.mDecorationSurface, 8, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, 4, this.mTouchRegion);
            } catch (RemoteException e2) {
                e2.rethrowFromSystemServer();
            }
        }
        Size size = dragResizeWindowGeometry.mTaskSize;
        ((SurfaceControl.Transaction) this.mSurfaceControlTransactionSupplier.get()).setWindowCrop(this.mInputSinkSurface, size.getWidth(), size.getHeight()).apply();
        this.mTouchRegion.op(0, 0, size.getWidth(), size.getHeight(), Region.Op.DIFFERENCE);
        updateSinkInputChannel();
        return true;
    }

    public final void updateSinkInputChannel() {
        try {
            this.mWindowSession.updateInputChannelWithPointerRegion(this.mSinkInputChannel.getToken(), this.mDisplayId, this.mInputSinkSurface, !this.mTouchable ? 24 : 8, 0, 1, this.mLastTouchRegion, this.mPointerTouchableRegion);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public DragResizeInputListener(Context context, IWindowSession iWindowSession, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, ActivityManager.RunningTaskInfo runningTaskInfo, Handler handler, Choreographer choreographer, int i, SurfaceControl surfaceControl, DragPositioningCallback dragPositioningCallback, Supplier<SurfaceControl.Builder> supplier, Supplier<SurfaceControl.Transaction> supplier2, DisplayController displayController, DesktopModeEventLogger desktopModeEventLogger) {
        this(context, iWindowSession, shellExecutor, shellExecutor2, new DefaultTaskResizeInputEventReceiverFactory(), runningTaskInfo, handler, choreographer, i, surfaceControl, dragPositioningCallback, supplier, supplier2, displayController, desktopModeEventLogger, new InputChannel(), new InputChannel());
    }

    public DragResizeInputListener(Context context, IWindowSession iWindowSession, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, ActivityManager.RunningTaskInfo runningTaskInfo, Handler handler, Choreographer choreographer, int i, SurfaceControl surfaceControl, DragPositioningCallback dragPositioningCallback, Supplier<SurfaceControl.Builder> supplier, Supplier<SurfaceControl.Transaction> supplier2, DisplayController displayController) {
        this(context, iWindowSession, shellExecutor, shellExecutor2, runningTaskInfo, handler, choreographer, i, surfaceControl, dragPositioningCallback, supplier, supplier2, displayController, new DesktopModeEventLogger());
    }
}
