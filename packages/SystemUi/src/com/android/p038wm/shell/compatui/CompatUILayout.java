package com.android.p038wm.shell.compatui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.TaskAppearedInfo;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.compatui.CompatUIController;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class CompatUILayout extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public CompatUIWindowManager mWindowManager;

    public CompatUILayout(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ImageButton imageButton = (ImageButton) findViewById(R.id.size_compat_restart_button);
        final int i = 0;
        imageButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda0
            public final /* synthetic */ CompatUILayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TaskAppearedInfo taskAppearedInfo;
                TaskAppearedInfo taskAppearedInfo2;
                switch (i) {
                    case 0:
                        CompatUIWindowManager compatUIWindowManager = this.f$0.mWindowManager;
                        compatUIWindowManager.mOnRestartButtonClicked.accept(Pair.create(compatUIWindowManager.mTaskInfo, compatUIWindowManager.mTaskListener));
                        return;
                    case 1:
                        CompatUILayout compatUILayout = this.f$0;
                        int i2 = CompatUILayout.$r8$clinit;
                        compatUILayout.setViewVisibility(R.id.size_compat_hint, false);
                        return;
                    case 2:
                        CompatUIWindowManager compatUIWindowManager2 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager2.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        int i3 = compatUIWindowManager2.mCameraCompatControlState == 1 ? 2 : 1;
                        compatUIWindowManager2.mCameraCompatControlState = i3;
                        CompatUIController.CompatUICallback compatUICallback = compatUIWindowManager2.mCallback;
                        int i4 = compatUIWindowManager2.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) compatUICallback;
                        synchronized (shellTaskOrganizer.mLock) {
                            taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.get(i4);
                        }
                        if (taskAppearedInfo != null) {
                            shellTaskOrganizer.updateCameraCompatControlState(taskAppearedInfo.getTaskInfo().token, i3);
                        }
                        compatUIWindowManager2.mLayout.updateCameraTreatmentButton(compatUIWindowManager2.mCameraCompatControlState);
                        return;
                    case 3:
                        CompatUIWindowManager compatUIWindowManager3 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager3.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        compatUIWindowManager3.mCameraCompatControlState = 3;
                        CompatUIController.CompatUICallback compatUICallback2 = compatUIWindowManager3.mCallback;
                        int i5 = compatUIWindowManager3.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer2 = (ShellTaskOrganizer) compatUICallback2;
                        synchronized (shellTaskOrganizer2.mLock) {
                            taskAppearedInfo2 = (TaskAppearedInfo) shellTaskOrganizer2.mTasks.get(i5);
                        }
                        if (taskAppearedInfo2 != null) {
                            shellTaskOrganizer2.updateCameraCompatControlState(taskAppearedInfo2.getTaskInfo().token, 3);
                        }
                        CompatUILayout compatUILayout2 = compatUIWindowManager3.mLayout;
                        compatUILayout2.setViewVisibility(R.id.camera_compat_control, false);
                        compatUILayout2.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                    default:
                        CompatUILayout compatUILayout3 = this.f$0;
                        int i6 = CompatUILayout.$r8$clinit;
                        compatUILayout3.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                }
            }
        });
        imageButton.setOnLongClickListener(new View.OnLongClickListener(this) { // from class: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda1
            public final /* synthetic */ CompatUILayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                switch (i) {
                    case 0:
                        CompatUILayout compatUILayout = this.f$0.mWindowManager.mLayout;
                        if (compatUILayout != null) {
                            compatUILayout.setViewVisibility(R.id.size_compat_hint, true);
                            break;
                        }
                        break;
                    case 1:
                        CompatUILayout compatUILayout2 = this.f$0.mWindowManager.mLayout;
                        if (compatUILayout2 != null) {
                            compatUILayout2.setViewVisibility(R.id.camera_compat_hint, true);
                            break;
                        }
                        break;
                    default:
                        CompatUILayout compatUILayout3 = this.f$0.mWindowManager.mLayout;
                        if (compatUILayout3 != null) {
                            compatUILayout3.setViewVisibility(R.id.camera_compat_hint, true);
                            break;
                        }
                        break;
                }
                return true;
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.size_compat_hint);
        ((TextView) linearLayout.findViewById(R.id.compat_mode_hint_text)).setText(R.string.restart_button_description);
        final int i2 = 1;
        linearLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda0
            public final /* synthetic */ CompatUILayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TaskAppearedInfo taskAppearedInfo;
                TaskAppearedInfo taskAppearedInfo2;
                switch (i2) {
                    case 0:
                        CompatUIWindowManager compatUIWindowManager = this.f$0.mWindowManager;
                        compatUIWindowManager.mOnRestartButtonClicked.accept(Pair.create(compatUIWindowManager.mTaskInfo, compatUIWindowManager.mTaskListener));
                        return;
                    case 1:
                        CompatUILayout compatUILayout = this.f$0;
                        int i22 = CompatUILayout.$r8$clinit;
                        compatUILayout.setViewVisibility(R.id.size_compat_hint, false);
                        return;
                    case 2:
                        CompatUIWindowManager compatUIWindowManager2 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager2.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        int i3 = compatUIWindowManager2.mCameraCompatControlState == 1 ? 2 : 1;
                        compatUIWindowManager2.mCameraCompatControlState = i3;
                        CompatUIController.CompatUICallback compatUICallback = compatUIWindowManager2.mCallback;
                        int i4 = compatUIWindowManager2.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) compatUICallback;
                        synchronized (shellTaskOrganizer.mLock) {
                            taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.get(i4);
                        }
                        if (taskAppearedInfo != null) {
                            shellTaskOrganizer.updateCameraCompatControlState(taskAppearedInfo.getTaskInfo().token, i3);
                        }
                        compatUIWindowManager2.mLayout.updateCameraTreatmentButton(compatUIWindowManager2.mCameraCompatControlState);
                        return;
                    case 3:
                        CompatUIWindowManager compatUIWindowManager3 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager3.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        compatUIWindowManager3.mCameraCompatControlState = 3;
                        CompatUIController.CompatUICallback compatUICallback2 = compatUIWindowManager3.mCallback;
                        int i5 = compatUIWindowManager3.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer2 = (ShellTaskOrganizer) compatUICallback2;
                        synchronized (shellTaskOrganizer2.mLock) {
                            taskAppearedInfo2 = (TaskAppearedInfo) shellTaskOrganizer2.mTasks.get(i5);
                        }
                        if (taskAppearedInfo2 != null) {
                            shellTaskOrganizer2.updateCameraCompatControlState(taskAppearedInfo2.getTaskInfo().token, 3);
                        }
                        CompatUILayout compatUILayout2 = compatUIWindowManager3.mLayout;
                        compatUILayout2.setViewVisibility(R.id.camera_compat_control, false);
                        compatUILayout2.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                    default:
                        CompatUILayout compatUILayout3 = this.f$0;
                        int i6 = CompatUILayout.$r8$clinit;
                        compatUILayout3.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                }
            }
        });
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.camera_compat_treatment_button);
        final int i3 = 2;
        imageButton2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda0
            public final /* synthetic */ CompatUILayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TaskAppearedInfo taskAppearedInfo;
                TaskAppearedInfo taskAppearedInfo2;
                switch (i3) {
                    case 0:
                        CompatUIWindowManager compatUIWindowManager = this.f$0.mWindowManager;
                        compatUIWindowManager.mOnRestartButtonClicked.accept(Pair.create(compatUIWindowManager.mTaskInfo, compatUIWindowManager.mTaskListener));
                        return;
                    case 1:
                        CompatUILayout compatUILayout = this.f$0;
                        int i22 = CompatUILayout.$r8$clinit;
                        compatUILayout.setViewVisibility(R.id.size_compat_hint, false);
                        return;
                    case 2:
                        CompatUIWindowManager compatUIWindowManager2 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager2.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        int i32 = compatUIWindowManager2.mCameraCompatControlState == 1 ? 2 : 1;
                        compatUIWindowManager2.mCameraCompatControlState = i32;
                        CompatUIController.CompatUICallback compatUICallback = compatUIWindowManager2.mCallback;
                        int i4 = compatUIWindowManager2.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) compatUICallback;
                        synchronized (shellTaskOrganizer.mLock) {
                            taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.get(i4);
                        }
                        if (taskAppearedInfo != null) {
                            shellTaskOrganizer.updateCameraCompatControlState(taskAppearedInfo.getTaskInfo().token, i32);
                        }
                        compatUIWindowManager2.mLayout.updateCameraTreatmentButton(compatUIWindowManager2.mCameraCompatControlState);
                        return;
                    case 3:
                        CompatUIWindowManager compatUIWindowManager3 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager3.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        compatUIWindowManager3.mCameraCompatControlState = 3;
                        CompatUIController.CompatUICallback compatUICallback2 = compatUIWindowManager3.mCallback;
                        int i5 = compatUIWindowManager3.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer2 = (ShellTaskOrganizer) compatUICallback2;
                        synchronized (shellTaskOrganizer2.mLock) {
                            taskAppearedInfo2 = (TaskAppearedInfo) shellTaskOrganizer2.mTasks.get(i5);
                        }
                        if (taskAppearedInfo2 != null) {
                            shellTaskOrganizer2.updateCameraCompatControlState(taskAppearedInfo2.getTaskInfo().token, 3);
                        }
                        CompatUILayout compatUILayout2 = compatUIWindowManager3.mLayout;
                        compatUILayout2.setViewVisibility(R.id.camera_compat_control, false);
                        compatUILayout2.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                    default:
                        CompatUILayout compatUILayout3 = this.f$0;
                        int i6 = CompatUILayout.$r8$clinit;
                        compatUILayout3.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                }
            }
        });
        imageButton2.setOnLongClickListener(new View.OnLongClickListener(this) { // from class: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda1
            public final /* synthetic */ CompatUILayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                switch (i2) {
                    case 0:
                        CompatUILayout compatUILayout = this.f$0.mWindowManager.mLayout;
                        if (compatUILayout != null) {
                            compatUILayout.setViewVisibility(R.id.size_compat_hint, true);
                            break;
                        }
                        break;
                    case 1:
                        CompatUILayout compatUILayout2 = this.f$0.mWindowManager.mLayout;
                        if (compatUILayout2 != null) {
                            compatUILayout2.setViewVisibility(R.id.camera_compat_hint, true);
                            break;
                        }
                        break;
                    default:
                        CompatUILayout compatUILayout3 = this.f$0.mWindowManager.mLayout;
                        if (compatUILayout3 != null) {
                            compatUILayout3.setViewVisibility(R.id.camera_compat_hint, true);
                            break;
                        }
                        break;
                }
                return true;
            }
        });
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.camera_compat_dismiss_button);
        final int i4 = 3;
        imageButton3.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda0
            public final /* synthetic */ CompatUILayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TaskAppearedInfo taskAppearedInfo;
                TaskAppearedInfo taskAppearedInfo2;
                switch (i4) {
                    case 0:
                        CompatUIWindowManager compatUIWindowManager = this.f$0.mWindowManager;
                        compatUIWindowManager.mOnRestartButtonClicked.accept(Pair.create(compatUIWindowManager.mTaskInfo, compatUIWindowManager.mTaskListener));
                        return;
                    case 1:
                        CompatUILayout compatUILayout = this.f$0;
                        int i22 = CompatUILayout.$r8$clinit;
                        compatUILayout.setViewVisibility(R.id.size_compat_hint, false);
                        return;
                    case 2:
                        CompatUIWindowManager compatUIWindowManager2 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager2.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        int i32 = compatUIWindowManager2.mCameraCompatControlState == 1 ? 2 : 1;
                        compatUIWindowManager2.mCameraCompatControlState = i32;
                        CompatUIController.CompatUICallback compatUICallback = compatUIWindowManager2.mCallback;
                        int i42 = compatUIWindowManager2.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) compatUICallback;
                        synchronized (shellTaskOrganizer.mLock) {
                            taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.get(i42);
                        }
                        if (taskAppearedInfo != null) {
                            shellTaskOrganizer.updateCameraCompatControlState(taskAppearedInfo.getTaskInfo().token, i32);
                        }
                        compatUIWindowManager2.mLayout.updateCameraTreatmentButton(compatUIWindowManager2.mCameraCompatControlState);
                        return;
                    case 3:
                        CompatUIWindowManager compatUIWindowManager3 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager3.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        compatUIWindowManager3.mCameraCompatControlState = 3;
                        CompatUIController.CompatUICallback compatUICallback2 = compatUIWindowManager3.mCallback;
                        int i5 = compatUIWindowManager3.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer2 = (ShellTaskOrganizer) compatUICallback2;
                        synchronized (shellTaskOrganizer2.mLock) {
                            taskAppearedInfo2 = (TaskAppearedInfo) shellTaskOrganizer2.mTasks.get(i5);
                        }
                        if (taskAppearedInfo2 != null) {
                            shellTaskOrganizer2.updateCameraCompatControlState(taskAppearedInfo2.getTaskInfo().token, 3);
                        }
                        CompatUILayout compatUILayout2 = compatUIWindowManager3.mLayout;
                        compatUILayout2.setViewVisibility(R.id.camera_compat_control, false);
                        compatUILayout2.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                    default:
                        CompatUILayout compatUILayout3 = this.f$0;
                        int i6 = CompatUILayout.$r8$clinit;
                        compatUILayout3.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                }
            }
        });
        imageButton3.setOnLongClickListener(new View.OnLongClickListener(this) { // from class: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda1
            public final /* synthetic */ CompatUILayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                switch (i3) {
                    case 0:
                        CompatUILayout compatUILayout = this.f$0.mWindowManager.mLayout;
                        if (compatUILayout != null) {
                            compatUILayout.setViewVisibility(R.id.size_compat_hint, true);
                            break;
                        }
                        break;
                    case 1:
                        CompatUILayout compatUILayout2 = this.f$0.mWindowManager.mLayout;
                        if (compatUILayout2 != null) {
                            compatUILayout2.setViewVisibility(R.id.camera_compat_hint, true);
                            break;
                        }
                        break;
                    default:
                        CompatUILayout compatUILayout3 = this.f$0.mWindowManager.mLayout;
                        if (compatUILayout3 != null) {
                            compatUILayout3.setViewVisibility(R.id.camera_compat_hint, true);
                            break;
                        }
                        break;
                }
                return true;
            }
        });
        final int i5 = 4;
        ((LinearLayout) findViewById(R.id.camera_compat_hint)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda0
            public final /* synthetic */ CompatUILayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TaskAppearedInfo taskAppearedInfo;
                TaskAppearedInfo taskAppearedInfo2;
                switch (i5) {
                    case 0:
                        CompatUIWindowManager compatUIWindowManager = this.f$0.mWindowManager;
                        compatUIWindowManager.mOnRestartButtonClicked.accept(Pair.create(compatUIWindowManager.mTaskInfo, compatUIWindowManager.mTaskListener));
                        return;
                    case 1:
                        CompatUILayout compatUILayout = this.f$0;
                        int i22 = CompatUILayout.$r8$clinit;
                        compatUILayout.setViewVisibility(R.id.size_compat_hint, false);
                        return;
                    case 2:
                        CompatUIWindowManager compatUIWindowManager2 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager2.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        int i32 = compatUIWindowManager2.mCameraCompatControlState == 1 ? 2 : 1;
                        compatUIWindowManager2.mCameraCompatControlState = i32;
                        CompatUIController.CompatUICallback compatUICallback = compatUIWindowManager2.mCallback;
                        int i42 = compatUIWindowManager2.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) compatUICallback;
                        synchronized (shellTaskOrganizer.mLock) {
                            taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.get(i42);
                        }
                        if (taskAppearedInfo != null) {
                            shellTaskOrganizer.updateCameraCompatControlState(taskAppearedInfo.getTaskInfo().token, i32);
                        }
                        compatUIWindowManager2.mLayout.updateCameraTreatmentButton(compatUIWindowManager2.mCameraCompatControlState);
                        return;
                    case 3:
                        CompatUIWindowManager compatUIWindowManager3 = this.f$0.mWindowManager;
                        if (!compatUIWindowManager3.shouldShowCameraControl()) {
                            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
                            return;
                        }
                        compatUIWindowManager3.mCameraCompatControlState = 3;
                        CompatUIController.CompatUICallback compatUICallback2 = compatUIWindowManager3.mCallback;
                        int i52 = compatUIWindowManager3.mTaskId;
                        ShellTaskOrganizer shellTaskOrganizer2 = (ShellTaskOrganizer) compatUICallback2;
                        synchronized (shellTaskOrganizer2.mLock) {
                            taskAppearedInfo2 = (TaskAppearedInfo) shellTaskOrganizer2.mTasks.get(i52);
                        }
                        if (taskAppearedInfo2 != null) {
                            shellTaskOrganizer2.updateCameraCompatControlState(taskAppearedInfo2.getTaskInfo().token, 3);
                        }
                        CompatUILayout compatUILayout2 = compatUIWindowManager3.mLayout;
                        compatUILayout2.setViewVisibility(R.id.camera_compat_control, false);
                        compatUILayout2.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                    default:
                        CompatUILayout compatUILayout3 = this.f$0;
                        int i6 = CompatUILayout.$r8$clinit;
                        compatUILayout3.setViewVisibility(R.id.camera_compat_hint, false);
                        return;
                }
            }
        });
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        CompatUIWindowManager compatUIWindowManager = this.mWindowManager;
        WindowManager.LayoutParams windowLayoutParams = compatUIWindowManager.getWindowLayoutParams();
        SurfaceControlViewHost surfaceControlViewHost = compatUIWindowManager.mViewHost;
        if (surfaceControlViewHost == null) {
            return;
        }
        surfaceControlViewHost.relayout(windowLayoutParams);
        compatUIWindowManager.updateSurfacePosition();
    }

    public final void setViewVisibility(int i, boolean z) {
        View findViewById = findViewById(i);
        int i2 = z ? 0 : 8;
        if (findViewById.getVisibility() == i2) {
            return;
        }
        findViewById.setVisibility(i2);
    }

    public final void updateCameraTreatmentButton(int i) {
        int i2 = i == 1 ? R.drawable.camera_compat_treatment_suggested_ripple : R.drawable.camera_compat_treatment_applied_ripple;
        int i3 = i == 1 ? R.string.camera_compat_treatment_suggested_button_description : R.string.camera_compat_treatment_applied_button_description;
        ImageButton imageButton = (ImageButton) findViewById(R.id.camera_compat_treatment_button);
        imageButton.setImageResource(i2);
        imageButton.setContentDescription(getResources().getString(i3));
        ((TextView) ((LinearLayout) findViewById(R.id.camera_compat_hint)).findViewById(R.id.compat_mode_hint_text)).setText(i3);
    }

    public CompatUILayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CompatUILayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CompatUILayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
