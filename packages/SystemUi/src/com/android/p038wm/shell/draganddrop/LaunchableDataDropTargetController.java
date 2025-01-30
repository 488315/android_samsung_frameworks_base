package com.android.p038wm.shell.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.MultiWindowOverheatUI;
import com.android.p038wm.shell.draganddrop.DragAndDropController;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.widget.SemTipPopup;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LaunchableDataDropTargetController implements IDropTargetUiController {
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

    public static boolean containsFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public static boolean isInThreshold(DragEvent dragEvent, DragAndDropController.PerDisplay perDisplay) {
        int min = (int) ((Math.min(r4.width(), r4.height()) * 0.056f) + 0.5f);
        return dragEvent.getX() < ((float) min) || dragEvent.getX() > ((float) (perDisplay.f444wm.getCurrentWindowMetrics().getBounds().right - min));
    }

    @Override // com.android.p038wm.shell.draganddrop.IDropTargetUiController
    public final boolean onDrag(DragEvent dragEvent, int i, final DragAndDropController.PerDisplay perDisplay) {
        int action = dragEvent.getAction();
        Context context = this.mContext;
        DragAndDropController dragAndDropController = this.mController;
        boolean z = false;
        if (action == 1) {
            int i2 = perDisplay.activeDragCount;
            if (i2 != 0) {
                Slog.w("DragAndDropController_Launchable", "Unexpected drag start during an active drag=" + perDisplay.activeDragCount);
                return false;
            }
            this.mIgnoreActionDragLocation = false;
            perDisplay.activeDragCount = i2 + 1;
            IDragLayout iDragLayout = perDisplay.dragLayout;
            DisplayController displayController = this.mDisplayController;
            ((DropTargetLayout) iDragLayout).prepare(displayController.getDisplayLayout(i), dragEvent.getClipData(), null, dragEvent.getDragSurface(), perDisplay.executableAppHolder, perDisplay.visibleTasks);
            this.mDragStartedWithinThreshold = isInThreshold(dragEvent, perDisplay);
            this.mEdgeFlags = 3;
            DisplayLayout displayLayout = displayController.getDisplayLayout(perDisplay.displayId);
            if (displayLayout != null) {
                int navigationBarPosition = DisplayLayout.navigationBarPosition(context.getResources(), displayLayout.mWidth, displayLayout.mHeight, displayLayout.mRotation);
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
                    }
                }
            }
            dragAndDropController.notifyDragStarted();
            if (dragAndDropController.supportsMultiWindow()) {
                DisplayLayout displayLayout2 = displayController.getDisplayLayout(i);
                int x = containsFlag(this.mEdgeFlags, 1) && containsFlag(this.mEdgeFlags, 2) ? (int) dragEvent.getX() : containsFlag(this.mEdgeFlags, 1) ? 0 : containsFlag(this.mEdgeFlags, 2) ? displayLayout2.mWidth : -1;
                if (x != -1) {
                    SmartTipController smartTipController = perDisplay.smartTipController;
                    int y = (int) dragEvent.getY();
                    int height = dragEvent.getDragSurface().getHeight();
                    smartTipController.mGapWithContent = smartTipController.mContext.getResources().getDimensionPixelSize(R.dimen.drag_and_split_help_tip_gap_size);
                    Rect rect = smartTipController.mDisplayBounds;
                    displayLayout2.getDisplayBounds(rect);
                    smartTipController.mSurfaceHeight = height;
                    int i3 = x > rect.width() / 2 ? rect.right : rect.left;
                    smartTipController.mInitialX = i3;
                    int max = Math.max(0, (y - (smartTipController.mSurfaceHeight / 2)) - smartTipController.mGapWithContent);
                    int i4 = x > rect.width() / 2 ? 1 : 0;
                    SmartTip smartTip = smartTipController.mHelpTip;
                    if (smartTip.mPreferences.getInt(smartTip.mKey, 0) < smartTip.mLimitCount && !smartTip.mShowRequested) {
                        smartTip.mShowRequested = true;
                        if (smartTip.mTipPopup == null) {
                            smartTip.mRootView = LayoutInflater.from(smartTip.mContext).inflate(smartTip.mLayoutResId, (ViewGroup) null);
                            StringBuilder sb = new StringBuilder("SmartTip");
                            String str = smartTip.mTitle;
                            sb.append(str);
                            Log.d(sb.toString(), "addView: mRootView=" + smartTip.mRootView);
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, VolteConstants.ErrorCode.DIAL_ALTERNATIVE_NUMBER, 16777496, -3);
                            layoutParams.setTitle(str);
                            layoutParams.layoutInDisplayCutoutMode = 1;
                            smartTip.mWindowManager.addView(smartTip.mRootView, layoutParams);
                            SemTipPopup semTipPopup = new SemTipPopup(smartTip.mRootView, 0);
                            smartTip.mTipPopup = semTipPopup;
                            if (semTipPopup.semGetBubblePopupWindow() != null) {
                                smartTip.mTipPopup.semGetBubblePopupWindow().setTouchModal(false);
                            }
                            if (smartTip.mTipPopup.semGetBalloonPopupWindow() != null) {
                                smartTip.mTipPopup.semGetBalloonPopupWindow().setTouchModal(false);
                            }
                        }
                        int i5 = i4 ^ 1;
                        if (smartTip.mRootView.isAttachedToWindow()) {
                            smartTip.showTipPopup(i3, max, i5, true);
                        } else {
                            smartTip.mRootView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.draganddrop.SmartTip.1
                                public final /* synthetic */ int val$direction;
                                public final /* synthetic */ boolean val$isExpanded;
                                public final /* synthetic */ int val$posX;
                                public final /* synthetic */ int val$posY;

                                public ViewOnAttachStateChangeListenerC39691(int i32, int max2, boolean z2, int i52) {
                                    r2 = i32;
                                    r3 = max2;
                                    r4 = z2;
                                    r5 = i52;
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
                        SharedPreferences sharedPreferences = smartTip.mPreferences;
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        String str2 = smartTip.mKey;
                        edit.putInt(str2, sharedPreferences.getInt(str2, 0) + 1);
                        edit.apply();
                        z = true;
                    }
                    smartTipController.mShown = z;
                }
            }
        } else if (action != 2) {
            if (action == 3) {
                this.mIgnoreActionDragLocation = true;
                return dragAndDropController.handleDrop(dragEvent, perDisplay);
            }
            if (action == 4) {
                this.mIgnoreActionDragLocation = true;
                IDragLayout iDragLayout2 = perDisplay.dragLayout;
                if (!((DropTargetLayout) iDragLayout2).mHasDropped) {
                    perDisplay.activeDragCount--;
                    if (this.mShowDropTarget) {
                        iDragLayout2.hide(dragEvent, new Runnable() { // from class: com.android.wm.shell.draganddrop.LaunchableDataDropTargetController$$ExternalSyntheticLambda0
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
                        });
                    }
                }
                this.mShowDropTarget = false;
                perDisplay.smartTipController.dismissHelpTipIfPossible();
            } else if (action == 6) {
                ((DropTargetLayout) perDisplay.dragLayout).hide((Runnable) null, this.mShowDropTarget);
            }
        } else {
            if (this.mIgnoreActionDragLocation) {
                Slog.d("DragAndDropController_Launchable", "Ignore ACTION_DRAG_LOCATION");
                return false;
            }
            if (this.mDragStartedWithinThreshold) {
                if (!isInThreshold(dragEvent, perDisplay)) {
                    this.mDragStartedWithinThreshold = false;
                }
                return true;
            }
            if (this.mShowDropTarget) {
                ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                perDisplay.smartTipController.dismissHelpTipIfPossible();
                DropTargetLayout dropTargetLayout = (DropTargetLayout) perDisplay.dragLayout;
                if (dropTargetLayout.mDismissButtonView.mIsEnterDismissButton) {
                    this.mShowDropTarget = false;
                    dropTargetLayout.hide((Runnable) null, false);
                    dragAndDropController.getClass();
                    DragAndDropController.setDropTargetWindowVisibility(perDisplay, 4);
                    DropTargetLayout dropTargetLayout2 = (DropTargetLayout) perDisplay.dragLayout;
                    if (dropTargetLayout2.mHasDrawable) {
                        dropTargetLayout2.mHasDrawable = false;
                        MultiWindowManager.getInstance().notifyDragSplitAppIconHasDrawable(false);
                    }
                    return false;
                }
            } else {
                Rect bounds = perDisplay.f444wm.getCurrentWindowMetrics().getBounds();
                int x2 = (int) dragEvent.getX();
                int min = (int) ((Math.min(bounds.width(), bounds.height()) * 0.056f) + 0.5f);
                if ((containsFlag(this.mEdgeFlags, 1) && x2 < min) || (containsFlag(this.mEdgeFlags, 2) && x2 > bounds.right - min)) {
                    if (MultiWindowOverheatUI.showIfNeeded(context)) {
                        this.mIgnoreActionDragLocation = true;
                        return false;
                    }
                    AppResult appResult = perDisplay.executableAppHolder.mResult;
                    if (!(appResult != null && appResult.hasResizableResolveInfo())) {
                        Toast.makeText(context, context.getString(R.string.drag_and_split_not_available_toast), 0).show();
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
                        ((HandlerExecutor) dragAndDropController.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.draganddrop.LaunchableDataDropTargetController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                LaunchableDataDropTargetController.this.mInputMethodManager.semForceHideSoftInput();
                                Log.i("DragAndDropController_Launchable", "Hide the Ime when Drag Layout is shown");
                            }
                        });
                    }
                    Intent intent = new Intent("com.samsung.android.action.ENTER_CONTENTS_TO_WINDOW");
                    intent.addFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                    context.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.permission.MULTI_WINDOW_MONITOR", -1);
                }
                SmartTipController smartTipController2 = perDisplay.smartTipController;
                int y2 = (int) dragEvent.getY();
                if (smartTipController2.mShown) {
                    int i6 = smartTipController2.mInitialX;
                    int max2 = Math.max(0, (y2 - (smartTipController2.mSurfaceHeight / 2)) - smartTipController2.mGapWithContent);
                    SmartTip smartTip2 = smartTipController2.mHelpTip;
                    SemTipPopup semTipPopup2 = smartTip2.mTipPopup;
                    if (semTipPopup2 != null && semTipPopup2.isShowing()) {
                        smartTip2.mTipPopup.setTargetPosition(i6, max2);
                        smartTip2.mTipPopup.update();
                    }
                }
            }
        }
        return true;
    }
}
