package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.MultiWindowOverheatUI;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.widget.SemTipPopup;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class LaunchableDataDropTargetController implements IDropTargetUiController {
    public final Context mContext;
    public final DragAndDropController mController;
    public final DisplayController mDisplayController;
    public int mEdgeFlags;
    public final InputMethodManager mInputMethodManager;
    public boolean mShowDropTarget;
    public boolean mDragStartedWithinThreshold = false;
    public boolean mIgnoreActionDragLocation = false;

    public LaunchableDataDropTargetController(Context context, DragAndDropController dragAndDropController, DisplayController displayController) {
        this.mContext = context;
        this.mController = dragAndDropController;
        this.mDisplayController = displayController;
        this.mInputMethodManager = (InputMethodManager) context.getSystemService(InputMethodManager.class);
    }

    public static boolean containsFlag$1(int i, int i2) {
        return (i & i2) != 0;
    }

    public static boolean isInThreshold$1(DragEvent dragEvent, DragAndDropController.PerDisplay perDisplay) {
        int min = (int) ((Math.min(r4.width(), r4.height()) * 0.056f) + 0.5f);
        return dragEvent.getX() < ((float) min) || dragEvent.getX() > ((float) (perDisplay.wm.getCurrentWindowMetrics().getBounds().right - min));
    }

    @Override // com.android.wm.shell.draganddrop.IDropTargetUiController
    public final boolean onDrag(DragEvent dragEvent, int i, final DragAndDropController.PerDisplay perDisplay) {
        boolean z;
        int action = dragEvent.getAction();
        DragAndDropController dragAndDropController = this.mController;
        if (action != 1) {
            if (action != 2) {
                if (action == 3) {
                    this.mIgnoreActionDragLocation = true;
                    return dragAndDropController.handleDrop(dragEvent, perDisplay);
                }
                if (action == 4) {
                    this.mIgnoreActionDragLocation = true;
                    DropTargetLayout dropTargetLayout = (DropTargetLayout) perDisplay.dragLayout;
                    if (!dropTargetLayout.mHasDropped) {
                        perDisplay.activeDragCount--;
                        if (this.mShowDropTarget) {
                            dropTargetLayout.hide(new Runnable() { // from class: com.android.wm.shell.draganddrop.LaunchableDataDropTargetController$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LaunchableDataDropTargetController launchableDataDropTargetController = LaunchableDataDropTargetController.this;
                                    DragAndDropController.PerDisplay perDisplay2 = perDisplay;
                                    launchableDataDropTargetController.getClass();
                                    if (perDisplay2.activeDragCount == 0) {
                                        launchableDataDropTargetController.mController.getClass();
                                        DragAndDropController.setDropTargetWindowVisibility(perDisplay2, 4);
                                    }
                                }
                            }, true);
                        }
                    }
                    this.mShowDropTarget = false;
                    perDisplay.smartTipController.dismissHelpTipIfPossible();
                    dragAndDropController.getClass();
                    final int i2 = 1;
                    dragAndDropController.notifyListeners(new Function() { // from class: com.android.wm.shell.draganddrop.LaunchableDataDropTargetController$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            DragAndDropController.DragAndDropListener dragAndDropListener = (DragAndDropController.DragAndDropListener) obj;
                            switch (i2) {
                                case 0:
                                    dragAndDropListener.onDragStarted();
                                    break;
                                default:
                                    dragAndDropListener.getClass();
                                    break;
                            }
                            return Boolean.FALSE;
                        }
                    });
                    return true;
                }
                if (action == 6) {
                    ((DropTargetLayout) perDisplay.dragLayout).hide(null, this.mShowDropTarget);
                    return true;
                }
            } else {
                if (this.mIgnoreActionDragLocation) {
                    Slog.d("DragAndDropController_Launchable", "Ignore ACTION_DRAG_LOCATION");
                    return false;
                }
                if (this.mDragStartedWithinThreshold) {
                    if (!isInThreshold$1(dragEvent, perDisplay)) {
                        this.mDragStartedWithinThreshold = false;
                        return true;
                    }
                } else if (this.mShowDropTarget) {
                    ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                    perDisplay.smartTipController.dismissHelpTipIfPossible();
                    DragLayoutProvider dragLayoutProvider = perDisplay.dragLayout;
                    DropTargetLayout dropTargetLayout2 = (DropTargetLayout) dragLayoutProvider;
                    if (dropTargetLayout2.mIsIntentSenderDropTarget ? false : dropTargetLayout2.mDismissView.mIsEnterDismissButton) {
                        this.mShowDropTarget = false;
                        ((DropTargetLayout) dragLayoutProvider).hide(null, false);
                        dragAndDropController.getClass();
                        DragAndDropController.setDropTargetWindowVisibility(perDisplay, 4);
                        DropTargetLayout dropTargetLayout3 = (DropTargetLayout) perDisplay.dragLayout;
                        if (dropTargetLayout3.mHasDrawable) {
                            dropTargetLayout3.mHasDrawable = false;
                            MultiWindowManager.getInstance().notifyDragSplitAppIconHasDrawable(false);
                        }
                        return false;
                    }
                } else {
                    Rect bounds = perDisplay.wm.getCurrentWindowMetrics().getBounds();
                    int x = (int) dragEvent.getX();
                    int min = (int) ((Math.min(bounds.width(), bounds.height()) * 0.056f) + 0.5f);
                    int i3 = bounds.right - min;
                    if ((containsFlag$1(this.mEdgeFlags, 1) && x < min) || (containsFlag$1(this.mEdgeFlags, 2) && x > i3)) {
                        if (MultiWindowOverheatUI.showIfNeeded(this.mContext)) {
                            this.mIgnoreActionDragLocation = true;
                            return false;
                        }
                        AppResult appResult = perDisplay.executableAppHolder.mResult;
                        if (!(appResult != null && appResult.hasResizableResolveInfo())) {
                            Toast.makeText(this.mContext, this.mContext.getString(R.string.drag_and_split_not_available_toast), 0).show();
                            this.mIgnoreActionDragLocation = true;
                            return false;
                        }
                        IDropTargetUiController.performDragStartedHapticAndSound(perDisplay);
                        dragAndDropController.getClass();
                        DragAndDropController.setDropTargetWindowVisibility(perDisplay, 0);
                        ((DropTargetLayout) perDisplay.dragLayout).show();
                        this.mShowDropTarget = true;
                        InputMethodManager inputMethodManager = this.mInputMethodManager;
                        if (inputMethodManager != null && inputMethodManager.isInputMethodShown()) {
                            dragAndDropController.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.draganddrop.LaunchableDataDropTargetController$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LaunchableDataDropTargetController.this.mInputMethodManager.semForceHideSoftInput();
                                    Log.i("DragAndDropController_Launchable", "Hide the Ime when Drag Layout is shown");
                                }
                            });
                        }
                        Intent intent = new Intent("com.samsung.android.action.ENTER_CONTENTS_TO_WINDOW");
                        intent.addFlags(1073741824);
                        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.permission.MULTI_WINDOW_MONITOR", -1);
                    }
                    SmartTipController smartTipController = perDisplay.smartTipController;
                    int y = (int) dragEvent.getY();
                    if (smartTipController.mShown) {
                        int i4 = smartTipController.mInitialX;
                        int max = Math.max(0, (y - (smartTipController.mSurfaceHeight / 2)) - smartTipController.mGapWithContent);
                        SmartTip smartTip = smartTipController.mHelpTip;
                        SemTipPopup semTipPopup = smartTip.mTipPopup;
                        if (semTipPopup != null && semTipPopup.isShowing()) {
                            smartTip.mTipPopup.setTargetPosition(i4, max);
                            smartTip.mTipPopup.update();
                        }
                    }
                }
            }
            return true;
        }
        if (perDisplay.activeDragCount != 0) {
            Slog.w("DragAndDropController_Launchable", "Unexpected drag start during an active drag");
            return false;
        }
        perDisplay.dragSession.initialize();
        perDisplay.activeDragCount++;
        ((DropTargetLayout) perDisplay.dragLayout).prepare(perDisplay.dragSession, null, dragEvent.getDragSurface(), perDisplay.mHiddenDropTargetArea, false);
        this.mDragStartedWithinThreshold = isInThreshold$1(dragEvent, perDisplay);
        this.mEdgeFlags = 3;
        int i5 = perDisplay.displayId;
        DisplayController displayController = this.mDisplayController;
        DisplayLayout displayLayout = displayController.getDisplayLayout(i5);
        if (displayLayout != null) {
            int navigationBarPosition = DisplayLayout.navigationBarPosition(this.mContext.getResources(), displayLayout.mWidth, displayLayout.mHeight, displayLayout.mRotation);
            if (navigationBarPosition == 1) {
                this.mEdgeFlags &= -2;
            } else if (navigationBarPosition == 2) {
                this.mEdgeFlags &= -3;
            }
        }
        ClipData clipData = dragEvent.getClipData();
        if (clipData == null) {
            Slog.d("DragAndDropController_Launchable", "setIgnoreEdgeFlags. clipData null.");
        } else {
            ClipDescription description = clipData.getDescription();
            if (description == null) {
                Slog.d("DragAndDropController_Launchable", "setIgnoreEdgeFlags. description null.");
            } else {
                PersistableBundle extras = description.getExtras();
                if (extras == null) {
                    Slog.d("DragAndDropController_Launchable", "setIgnoreEdgeFlags. description null.");
                } else {
                    if (extras.getBoolean("com.samsung.android.content.clipdescription.extra.IGNORE_LEFT_EDGE")) {
                        this.mEdgeFlags &= -2;
                    }
                    if (extras.getBoolean("com.samsung.android.content.clipdescription.extra.IGNORE_RIGHT_EDGE")) {
                        this.mEdgeFlags &= -3;
                    }
                    if (CoreRune.FW_SUPPORT_ONE_TOUCH && Settings.Secure.getInt(this.mContext.getContentResolver(), "otch_long_press_enabled_setting", 1) == 1) {
                        this.mEdgeFlags &= -3;
                    }
                }
            }
        }
        final int i6 = 0;
        dragAndDropController.notifyListeners(new Function() { // from class: com.android.wm.shell.draganddrop.LaunchableDataDropTargetController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                DragAndDropController.DragAndDropListener dragAndDropListener = (DragAndDropController.DragAndDropListener) obj;
                switch (i6) {
                    case 0:
                        dragAndDropListener.onDragStarted();
                        break;
                    default:
                        dragAndDropListener.getClass();
                        break;
                }
                return Boolean.FALSE;
            }
        });
        if (dragAndDropController.supportsMultiWindow()) {
            DisplayLayout displayLayout2 = displayController.getDisplayLayout(i);
            int x2 = (containsFlag$1(this.mEdgeFlags, 1) && containsFlag$1(this.mEdgeFlags, 2)) ? (int) dragEvent.getX() : containsFlag$1(this.mEdgeFlags, 1) ? 0 : containsFlag$1(this.mEdgeFlags, 2) ? displayLayout2.mWidth : -1;
            if (x2 != -1) {
                SmartTipController smartTipController2 = perDisplay.smartTipController;
                int y2 = (int) dragEvent.getY();
                int height = dragEvent.getDragSurface().getHeight();
                smartTipController2.mGapWithContent = smartTipController2.mContext.getResources().getDimensionPixelSize(R.dimen.drag_and_split_help_tip_gap_size);
                displayLayout2.getDisplayBounds(smartTipController2.mDisplayBounds);
                smartTipController2.mSurfaceHeight = height;
                int i7 = x2 > smartTipController2.mDisplayBounds.width() / 2 ? smartTipController2.mDisplayBounds.right : smartTipController2.mDisplayBounds.left;
                smartTipController2.mInitialX = i7;
                int max2 = Math.max(0, (y2 - (smartTipController2.mSurfaceHeight / 2)) - smartTipController2.mGapWithContent);
                int i8 = x2 > smartTipController2.mDisplayBounds.width() / 2 ? 1 : 0;
                SmartTip smartTip2 = smartTipController2.mHelpTip;
                SharedPreferences sharedPreferences = smartTip2.mPreferences;
                String str = smartTip2.mKey;
                if (sharedPreferences.getInt(str, 0) < smartTip2.mLimitCount && !smartTip2.mShowRequested) {
                    smartTip2.mShowRequested = true;
                    if (smartTip2.mTipPopup == null) {
                        smartTip2.mRootView = LayoutInflater.from(smartTip2.mContext).inflate(smartTip2.mLayoutResId, (ViewGroup) null);
                        StringBuilder sb = new StringBuilder("SmartTip");
                        String str2 = smartTip2.mTitle;
                        sb.append(str2);
                        Log.d(sb.toString(), "addView: mRootView=" + smartTip2.mRootView);
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, VolteConstants.ErrorCode.DIAL_ALTERNATIVE_NUMBER, 16777496, -3);
                        layoutParams.setTitle(str2);
                        layoutParams.layoutInDisplayCutoutMode = 1;
                        smartTip2.mWindowManager.addView(smartTip2.mRootView, layoutParams);
                        SemTipPopup semTipPopup2 = new SemTipPopup(smartTip2.mRootView, 0);
                        smartTip2.mTipPopup = semTipPopup2;
                        if (semTipPopup2.semGetBubblePopupWindow() != null) {
                            smartTip2.mTipPopup.semGetBubblePopupWindow().setTouchModal(false);
                        }
                        if (smartTip2.mTipPopup.semGetBalloonPopupWindow() != null) {
                            smartTip2.mTipPopup.semGetBalloonPopupWindow().setTouchModal(false);
                        }
                    }
                    int i9 = i8 ^ 1;
                    if (smartTip2.mRootView.isAttachedToWindow()) {
                        smartTip2.showTipPopup(i7, max2, i9, true);
                    } else {
                        smartTip2.mRootView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.draganddrop.SmartTip.1
                            public final /* synthetic */ int val$direction;
                            public final /* synthetic */ boolean val$isExpanded;
                            public final /* synthetic */ int val$posX;
                            public final /* synthetic */ int val$posY;

                            public AnonymousClass1(int i72, int max22, boolean z2, int i92) {
                                r2 = i72;
                                r3 = max22;
                                r4 = z2;
                                r5 = i92;
                            }

                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewAttachedToWindow(View view) {
                                SmartTip.this.showTipPopup(r2, r3, r5, r4);
                            }

                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewDetachedFromWindow(View view) {
                            }
                        });
                    }
                    SharedPreferences.Editor edit = smartTip2.mPreferences.edit();
                    edit.putInt(str, smartTip2.mPreferences.getInt(str, 0) + 1);
                    edit.apply();
                    z = true;
                } else {
                    z = false;
                }
                smartTipController2.mShown = z;
            }
        }
        this.mIgnoreActionDragLocation = false;
        return true;
    }
}
