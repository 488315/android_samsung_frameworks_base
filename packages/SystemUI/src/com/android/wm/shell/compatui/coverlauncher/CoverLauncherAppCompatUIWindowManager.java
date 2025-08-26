package com.android.wm.shell.compatui.coverlauncher;

import android.app.TaskInfo;
import android.content.Context;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.CompatUIController;
import com.android.wm.shell.compatui.CompatUIWindowManagerAbstract;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class CoverLauncherAppCompatUIWindowManager extends CompatUIWindowManagerAbstract {
    public final String TAG;
    public final CoverLauncherAppCompatUIController mCoverLauncherAppCompatUIController;
    CoverLauncherAppCompatUILayout mLayout;

    public CoverLauncherAppCompatUIWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, CompatUIController compatUIController) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.TAG = "CoverLauncherAppCompatUIWindowManager";
        this.mCoverLauncherAppCompatUIController = new CoverLauncherAppCompatUIController(this.mContext, this, taskInfo, compatUIController);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        TextView textView;
        ViewGroup.LayoutParams layoutParams;
        final CoverLauncherAppCompatUILayout coverLauncherAppCompatUILayoutInflateLayout = inflateLayout();
        this.mLayout = coverLauncherAppCompatUILayoutInflateLayout;
        CoverLauncherAppCompatUIController coverLauncherAppCompatUIController = this.mCoverLauncherAppCompatUIController;
        coverLauncherAppCompatUILayoutInflateLayout.mWindowManager = this;
        coverLauncherAppCompatUILayoutInflateLayout.mController = coverLauncherAppCompatUIController;
        coverLauncherAppCompatUILayoutInflateLayout.mNaviButtonSize = (int) coverLauncherAppCompatUILayoutInflateLayout.getResources().getDimension(R.dimen.fw_cover_launcher_app_compat_button_size);
        CoverLauncherAppCompatUIController coverLauncherAppCompatUIController2 = coverLauncherAppCompatUILayoutInflateLayout.mController;
        coverLauncherAppCompatUIController2.getClass();
        try {
            coverLauncherAppCompatUIController2.mAlignment = coverLauncherAppCompatUIController2.mActivityTaskManager.getCoverLauncherAppCompatAlignment();
        } catch (RemoteException e) {
            Log.e("CoverLauncherAppCompatUIController", "Failed to retrieve app compat alignment.", e);
        }
        boolean z = CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_UI;
        if (z) {
            CoverLauncherAppCompatUIController coverLauncherAppCompatUIController3 = coverLauncherAppCompatUILayoutInflateLayout.mController;
            coverLauncherAppCompatUIController3.getClass();
            Rect rect = z ? new Rect(coverLauncherAppCompatUIController3.mTaskInfo.appCompatTaskInfo.topActivityBounds) : null;
            if (rect != null && (textView = (TextView) coverLauncherAppCompatUILayoutInflateLayout.findViewById(R.id.fw_cover_launcher_app_compat_ui_main)) != null && (layoutParams = textView.getLayoutParams()) != null) {
                layoutParams.width = rect.width();
                textView.setLayoutParams(layoutParams);
            }
        }
        ArrayList arrayList = new ArrayList();
        AccessibilityManager accessibilityManager = coverLauncherAppCompatUILayoutInflateLayout.mController.mAccessibilityManager;
        if (accessibilityManager != null ? accessibilityManager.semIsScreenReaderEnabled() : false) {
            Log.d("CoverLauncherAppCompatUILayout", "ScreenReader was enabled, do not show alignment buttons");
        } else {
            Rect taskBounds = coverLauncherAppCompatUILayoutInflateLayout.mWindowManager.getTaskBounds();
            CoverLauncherAppCompatUIController coverLauncherAppCompatUIController4 = coverLauncherAppCompatUILayoutInflateLayout.mController;
            coverLauncherAppCompatUIController4.getClass();
            Rect rect2 = z ? new Rect(coverLauncherAppCompatUIController4.mTaskInfo.appCompatTaskInfo.topActivityBounds) : null;
            if (taskBounds.width() - (rect2.width() + (coverLauncherAppCompatUILayoutInflateLayout.mNaviButtonSize << 1)) >= 0) {
                arrayList.add(Integer.valueOf(R.id.fw_cover_launcher_app_compat_align_left_button));
                arrayList.add(Integer.valueOf(R.id.fw_cover_launcher_app_compat_align_right_button));
            } else {
                Log.d("CoverLauncherAppCompatUILayout", "Not enough space to show alignment buttons, taskBounds=" + taskBounds + ", buttonSize=" + coverLauncherAppCompatUILayoutInflateLayout.mNaviButtonSize + ", activityBounds=" + rect2);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Integer num = (Integer) obj;
            View viewFindViewById = coverLauncherAppCompatUILayoutInflateLayout.findViewById(num.intValue());
            if (viewFindViewById instanceof ImageButton) {
                coverLauncherAppCompatUILayoutInflateLayout.mButtons.put(num, (ImageButton) viewFindViewById);
            }
        }
        Iterator it = coverLauncherAppCompatUILayoutInflateLayout.mButtons.entrySet().iterator();
        while (it.hasNext()) {
            final ImageButton imageButton = (ImageButton) ((Map.Entry) it.next()).getValue();
            int id = imageButton.getId();
            imageButton.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout.2
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action != 0 && action != 1) {
                        return false;
                    }
                    imageButton.startAnimation(AnimationUtils.loadAnimation(((FrameLayout) CoverLauncherAppCompatUILayout.this).mContext, motionEvent.getAction() == 0 ? R.anim.mt_app_compat_ui_btn_press : R.anim.mt_app_compat_ui_btn_release));
                    return false;
                }
            });
            if (!CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_UI) {
                imageButton.setVisibility(4);
            } else if (id == R.id.fw_cover_launcher_app_compat_align_left_button) {
                final int i2 = 0;
                imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i3 = i2;
                        CoverLauncherAppCompatUILayout coverLauncherAppCompatUILayout = coverLauncherAppCompatUILayoutInflateLayout;
                        switch (i3) {
                            case 0:
                                int i4 = CoverLauncherAppCompatUILayout.$r8$clinit;
                                Log.d("CoverLauncherAppCompatUILayout", "onClick v=" + view);
                                coverLauncherAppCompatUILayout.mController.setCoverLauncherAppCompatAlignment(3);
                                coverLauncherAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            default:
                                int i5 = CoverLauncherAppCompatUILayout.$r8$clinit;
                                Log.d("CoverLauncherAppCompatUILayout", "onClick v=" + view);
                                coverLauncherAppCompatUILayout.mController.setCoverLauncherAppCompatAlignment(5);
                                coverLauncherAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            } else if (id == R.id.fw_cover_launcher_app_compat_align_right_button) {
                final int i3 = 1;
                imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i32 = i3;
                        CoverLauncherAppCompatUILayout coverLauncherAppCompatUILayout = coverLauncherAppCompatUILayoutInflateLayout;
                        switch (i32) {
                            case 0:
                                int i4 = CoverLauncherAppCompatUILayout.$r8$clinit;
                                Log.d("CoverLauncherAppCompatUILayout", "onClick v=" + view);
                                coverLauncherAppCompatUILayout.mController.setCoverLauncherAppCompatAlignment(3);
                                coverLauncherAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            default:
                                int i5 = CoverLauncherAppCompatUILayout.$r8$clinit;
                                Log.d("CoverLauncherAppCompatUILayout", "onClick v=" + view);
                                coverLauncherAppCompatUILayout.mController.setCoverLauncherAppCompatAlignment(5);
                                coverLauncherAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            }
        }
        coverLauncherAppCompatUILayoutInflateLayout.refreshButtonVisibility(false);
        coverLauncherAppCompatUILayoutInflateLayout.getRootView().getViewTreeObserver().registerFrameCommitCallback(coverLauncherAppCompatUILayoutInflateLayout.mFrameCommitCallback);
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        return this.mDisplayId == 1 && this.mTaskInfo.isLaunchedFromMultistarCoverLauncher;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final WindowManager.LayoutParams getWindowLayoutParams() {
        CoverLauncherAppCompatUILayout coverLauncherAppCompatUILayout = this.mLayout;
        if (coverLauncherAppCompatUILayout == null) {
            Log.d(this.TAG, "getWindowLayoutParams: no layout");
            return new WindowManager.LayoutParams();
        }
        coverLauncherAppCompatUILayout.measure(0, 0);
        Rect taskBounds = getTaskBounds();
        return getWindowLayoutParams(taskBounds.width(), taskBounds.height());
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10001;
    }

    public CoverLauncherAppCompatUILayout inflateLayout() {
        return (CoverLauncherAppCompatUILayout) LayoutInflater.from(this.mContext).inflate(R.layout.fw_cover_launcher_app_compat_ui_layout, (ViewGroup) null);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayout = null;
        CoverLauncherAppCompatUIController coverLauncherAppCompatUIController = this.mCoverLauncherAppCompatUIController;
        coverLauncherAppCompatUIController.mHandler.removeCallbacksAndMessages(coverLauncherAppCompatUIController);
    }

    public final String toString() {
        return this.TAG + "{mLayout=" + this.mLayout + ", mCompatUIController=, mCoverLauncherAppCompatUIController=}";
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        CoverLauncherAppCompatUILayout coverLauncherAppCompatUILayout;
        CoverLauncherAppCompatUIController coverLauncherAppCompatUIController = this.mCoverLauncherAppCompatUIController;
        TaskInfo taskInfo2 = coverLauncherAppCompatUIController.mTaskInfo;
        coverLauncherAppCompatUIController.mTaskInfo = taskInfo;
        boolean z2 = (taskInfo.appCompatTaskInfo.topActivityBounds.width() == taskInfo2.appCompatTaskInfo.topActivityBounds.width() && taskInfo.appCompatTaskInfo.topActivityBounds.height() == taskInfo2.appCompatTaskInfo.topActivityBounds.height() && taskInfo.configuration.windowConfiguration.getBounds().equals(taskInfo2.configuration.windowConfiguration.getBounds())) ? false : true;
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_CONFIGURATION && (coverLauncherAppCompatUILayout = this.mLayout) != null && taskInfo.appCompatTaskInfo.singleTapFromLetterbox) {
            coverLauncherAppCompatUILayout.refreshButtonVisibility(false);
        } else if (z2) {
            release();
        }
        return super.updateCompatInfo(taskInfo, taskListener, z);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void updateSurfacePosition() {
    }
}
