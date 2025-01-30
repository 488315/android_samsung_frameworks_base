package com.android.p038wm.shell.compatui;

import android.app.IActivityTaskManager;
import android.app.TaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
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
import android.window.TaskAppearedInfo;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.FrameworkStatsLog;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.compatui.CompatUIController;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BoundsCompatUIWindowManager extends CompatUIWindowManagerAbstract {
    public final String TAG;
    public final BoundsCompatUIController mBoundsCompatUIController;
    public boolean mIsRecreating;
    BoundsCompatUILayout mLayout;
    public boolean mShouldShowHint;

    public BoundsCompatUIWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, CompatUIController.CompatUICallback compatUICallback, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, CompatUIController compatUIController) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.TAG = "BoundsCompatUIWindowManager";
        this.mBoundsCompatUIController = new BoundsCompatUIController(this.mContext, this, taskInfo, compatUICallback, compatUIController);
        this.mShouldShowHint = true;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        TaskAppearedInfo taskAppearedInfo;
        ActivityInfo activityInfo;
        Rect activityBounds;
        TextView textView;
        ViewGroup.LayoutParams layoutParams;
        final BoundsCompatUILayout inflateLayout = inflateLayout();
        this.mLayout = inflateLayout;
        BoundsCompatUIController boundsCompatUIController = this.mBoundsCompatUIController;
        inflateLayout.mWindowManager = this;
        inflateLayout.mController = boundsCompatUIController;
        if (CoreRune.SAFE_DEBUG) {
            BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT = SystemProperties.getBoolean("debug.boundscompatui.layout", false);
        }
        inflateLayout.mNaviButtonSize = (int) inflateLayout.getResources().getDimension(R.dimen.bounds_compat_button_size);
        BoundsCompatUIController boundsCompatUIController2 = inflateLayout.mController;
        String str = boundsCompatUIController2.TAG;
        IActivityTaskManager iActivityTaskManager = boundsCompatUIController2.mActivityTaskManager;
        try {
            boundsCompatUIController2.mOrientationPolicy = iActivityTaskManager.getOrientationControlPolicy(UserHandle.getCallingUserId(), boundsCompatUIController2.mTaskInfo.baseActivity.getPackageName());
        } catch (RemoteException unused) {
            boundsCompatUIController2.mOrientationPolicy = 0;
        }
        try {
            boundsCompatUIController2.mIsRotationFrozen = boundsCompatUIController2.mWindowManager.isRotationFrozen();
        } catch (RemoteException e) {
            Log.e(str, "Failed to load orientation policy, set frozen", e);
            boundsCompatUIController2.mIsRotationFrozen = true;
        }
        try {
            boundsCompatUIController2.mAlignment = iActivityTaskManager.getBoundsCompatAlignment();
        } catch (RemoteException e2) {
            Log.e(str, "Failed to retrieve bounds compat alignment.", e2);
        }
        if (CoreRune.FW_BOUNDS_COMPAT_UI_SUPPORT_ALIGNMENT && (activityBounds = inflateLayout.mController.getActivityBounds()) != null && (textView = (TextView) inflateLayout.findViewById(R.id.bounds_compat_ui_main)) != null && (layoutParams = textView.getLayoutParams()) != null) {
            if (BoundsCompatUIController.isAlignedVertically(inflateLayout.mController.mTaskInfo)) {
                layoutParams.height = activityBounds.height();
            } else {
                layoutParams.width = activityBounds.width();
            }
            textView.setLayoutParams(layoutParams);
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(Integer.valueOf(R.id.bounds_compat_fixed_aspect_ratio_shortcut_button), Integer.valueOf(R.id.bounds_compat_restart_button), Integer.valueOf(R.id.bounds_compat_rotation_on_button), Integer.valueOf(R.id.bounds_compat_rotation_off_button)));
        AccessibilityManager accessibilityManager = inflateLayout.mController.mAccessibilityManager;
        if (accessibilityManager != null ? accessibilityManager.semIsScreenReaderEnabled() : false) {
            Log.d("BoundsCompatUILayout", "ScreenReader was enabled, do not show alignment buttons");
        } else {
            Rect taskBounds = inflateLayout.mWindowManager.getTaskBounds();
            int dimension = (int) inflateLayout.getResources().getDimension(R.dimen.status_bar_height);
            Rect activityBounds2 = inflateLayout.mController.getActivityBounds();
            DisplayLayout displayLayout = inflateLayout.mWindowManager.mDisplayLayout;
            if (BoundsCompatUIController.isAlignedVertically(inflateLayout.mController.mTaskInfo)) {
                int height = ((((taskBounds.height() - displayLayout.mNavBarFrameHeight) - activityBounds2.height()) - dimension) - (inflateLayout.mNaviButtonSize << 1)) >> 2;
                inflateLayout.mVerticalMarginFromActivityBounds = height;
                if (height >= 0) {
                    arrayList.add(Integer.valueOf(R.id.bounds_compat_align_top_button));
                    arrayList.add(Integer.valueOf(R.id.bounds_compat_align_bottom_button));
                    if (BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT) {
                        StringBuilder sb = new StringBuilder("Show alignment buttons vertically. taskBounds=");
                        sb.append(taskBounds);
                        sb.append(", buttonSize=");
                        sb.append(inflateLayout.mNaviButtonSize);
                        sb.append(", activityBounds=");
                        sb.append(activityBounds2);
                        sb.append(", statusBarHeight=");
                        sb.append(dimension);
                        sb.append(", navBarFrameHeight=");
                        sb.append(displayLayout.mNavBarFrameHeight);
                        sb.append(", btnSize=");
                        sb.append(inflateLayout.mNaviButtonSize);
                        sb.append(", mVerticalMarginFromActivityBounds=");
                        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, inflateLayout.mVerticalMarginFromActivityBounds, "BoundsCompatUILayout");
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder("Not enough space to show alignment buttons vertically. taskBounds=");
                    sb2.append(taskBounds);
                    sb2.append(", buttonSize=");
                    sb2.append(inflateLayout.mNaviButtonSize);
                    sb2.append(", activityBounds=");
                    sb2.append(activityBounds2);
                    sb2.append(", statusBarHeight=");
                    sb2.append(dimension);
                    sb2.append(", navBarFrameHeight=");
                    sb2.append(displayLayout.mNavBarFrameHeight);
                    sb2.append(", btnSize=");
                    sb2.append(inflateLayout.mNaviButtonSize);
                    sb2.append(", mVerticalMarginFromActivityBounds=");
                    RecyclerView$$ExternalSyntheticOutline0.m46m(sb2, inflateLayout.mVerticalMarginFromActivityBounds, "BoundsCompatUILayout");
                }
            } else if (taskBounds.width() > activityBounds2.width() + (inflateLayout.mNaviButtonSize << 1)) {
                arrayList.add(Integer.valueOf(R.id.bounds_compat_align_left_button));
                arrayList.add(Integer.valueOf(R.id.bounds_compat_align_right_button));
            } else {
                Log.d("BoundsCompatUILayout", "Not enough space to show alignment buttons, taskBounds=" + taskBounds + ", buttonSize=" + inflateLayout.mNaviButtonSize + ", activityBounds=" + activityBounds2);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            View findViewById = inflateLayout.findViewById(intValue);
            if (findViewById instanceof ImageButton) {
                inflateLayout.mButtons.put(Integer.valueOf(intValue), (ImageButton) findViewById);
            }
        }
        inflateLayout.mSwitchableButtonContainer = (FrameLayout) inflateLayout.findViewById(R.id.bounds_compat_switchable_button_container);
        Iterator it2 = inflateLayout.mButtons.entrySet().iterator();
        while (it2.hasNext()) {
            final ImageButton imageButton = (ImageButton) ((Map.Entry) it2.next()).getValue();
            int id = imageButton.getId();
            imageButton.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUILayout.2
                public final /* synthetic */ ImageButton val$btn;

                public ViewOnTouchListenerC39002(final ImageButton imageButton2) {
                    r2 = imageButton2;
                }

                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action != 0 && action != 1) {
                        return false;
                    }
                    r2.startAnimation(AnimationUtils.loadAnimation(((FrameLayout) BoundsCompatUILayout.this).mContext, motionEvent.getAction() == 0 ? R.anim.bounds_compat_ui_btn_press : R.anim.bounds_compat_ui_btn_release));
                    return false;
                }
            });
            if (id == R.id.bounds_compat_fixed_aspect_ratio_shortcut_button) {
                final int i = 0;
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUILayout$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                BoundsCompatUILayout boundsCompatUILayout = inflateLayout;
                                boolean z = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout.getClass();
                                Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                intent.addFlags(268468224);
                                Bundle bundle = new Bundle();
                                bundle.putString(":settings:fragment_args_key", boundsCompatUILayout.mController.mTaskInfo.baseActivity.getPackageName());
                                intent.putExtra(":settings:show_fragment_args", bundle);
                                view.getContext().startActivityAsUser(intent, new UserHandle(boundsCompatUILayout.mController.mTaskInfo.userId));
                                break;
                            case 1:
                                BoundsCompatUILayout boundsCompatUILayout2 = inflateLayout;
                                boolean z2 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout2.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                BoundsCompatUIController boundsCompatUIController3 = boundsCompatUILayout2.mController;
                                ((ShellTaskOrganizer) boundsCompatUIController3.mCallback).onSizeCompatRestartButtonClicked(boundsCompatUIController3.mTaskInfo.taskId);
                                break;
                            case 2:
                                BoundsCompatUILayout boundsCompatUILayout3 = inflateLayout;
                                boolean z3 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout3.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout3.mController.setOrientationControlPolicy(0);
                                boundsCompatUILayout3.refreshButtonVisibility(false);
                                break;
                            case 3:
                                BoundsCompatUILayout boundsCompatUILayout4 = inflateLayout;
                                boolean z4 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout4.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout4.mController.setOrientationControlPolicy(31);
                                boundsCompatUILayout4.refreshButtonVisibility(false);
                                break;
                            case 4:
                                BoundsCompatUILayout boundsCompatUILayout5 = inflateLayout;
                                boolean z5 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout5.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout5.mController.setBoundsCompatAlignment(3);
                                boundsCompatUILayout5.refreshButtonVisibility(true);
                                break;
                            case 5:
                                BoundsCompatUILayout boundsCompatUILayout6 = inflateLayout;
                                boolean z6 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout6.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout6.mController.setBoundsCompatAlignment(5);
                                boundsCompatUILayout6.refreshButtonVisibility(true);
                                break;
                            case 6:
                                BoundsCompatUILayout boundsCompatUILayout7 = inflateLayout;
                                boolean z7 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout7.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout7.mController.setBoundsCompatAlignment(48);
                                boundsCompatUILayout7.refreshButtonVisibility(true);
                                break;
                            default:
                                BoundsCompatUILayout boundsCompatUILayout8 = inflateLayout;
                                boolean z8 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout8.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout8.mController.setBoundsCompatAlignment(80);
                                boundsCompatUILayout8.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            } else if (id == R.id.bounds_compat_restart_button) {
                final int i2 = 1;
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUILayout$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                BoundsCompatUILayout boundsCompatUILayout = inflateLayout;
                                boolean z = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout.getClass();
                                Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                intent.addFlags(268468224);
                                Bundle bundle = new Bundle();
                                bundle.putString(":settings:fragment_args_key", boundsCompatUILayout.mController.mTaskInfo.baseActivity.getPackageName());
                                intent.putExtra(":settings:show_fragment_args", bundle);
                                view.getContext().startActivityAsUser(intent, new UserHandle(boundsCompatUILayout.mController.mTaskInfo.userId));
                                break;
                            case 1:
                                BoundsCompatUILayout boundsCompatUILayout2 = inflateLayout;
                                boolean z2 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout2.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                BoundsCompatUIController boundsCompatUIController3 = boundsCompatUILayout2.mController;
                                ((ShellTaskOrganizer) boundsCompatUIController3.mCallback).onSizeCompatRestartButtonClicked(boundsCompatUIController3.mTaskInfo.taskId);
                                break;
                            case 2:
                                BoundsCompatUILayout boundsCompatUILayout3 = inflateLayout;
                                boolean z3 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout3.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout3.mController.setOrientationControlPolicy(0);
                                boundsCompatUILayout3.refreshButtonVisibility(false);
                                break;
                            case 3:
                                BoundsCompatUILayout boundsCompatUILayout4 = inflateLayout;
                                boolean z4 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout4.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout4.mController.setOrientationControlPolicy(31);
                                boundsCompatUILayout4.refreshButtonVisibility(false);
                                break;
                            case 4:
                                BoundsCompatUILayout boundsCompatUILayout5 = inflateLayout;
                                boolean z5 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout5.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout5.mController.setBoundsCompatAlignment(3);
                                boundsCompatUILayout5.refreshButtonVisibility(true);
                                break;
                            case 5:
                                BoundsCompatUILayout boundsCompatUILayout6 = inflateLayout;
                                boolean z6 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout6.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout6.mController.setBoundsCompatAlignment(5);
                                boundsCompatUILayout6.refreshButtonVisibility(true);
                                break;
                            case 6:
                                BoundsCompatUILayout boundsCompatUILayout7 = inflateLayout;
                                boolean z7 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout7.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout7.mController.setBoundsCompatAlignment(48);
                                boundsCompatUILayout7.refreshButtonVisibility(true);
                                break;
                            default:
                                BoundsCompatUILayout boundsCompatUILayout8 = inflateLayout;
                                boolean z8 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout8.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout8.mController.setBoundsCompatAlignment(80);
                                boundsCompatUILayout8.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            } else if (id == R.id.bounds_compat_rotation_on_button) {
                final int i3 = 2;
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUILayout$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i3) {
                            case 0:
                                BoundsCompatUILayout boundsCompatUILayout = inflateLayout;
                                boolean z = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout.getClass();
                                Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                intent.addFlags(268468224);
                                Bundle bundle = new Bundle();
                                bundle.putString(":settings:fragment_args_key", boundsCompatUILayout.mController.mTaskInfo.baseActivity.getPackageName());
                                intent.putExtra(":settings:show_fragment_args", bundle);
                                view.getContext().startActivityAsUser(intent, new UserHandle(boundsCompatUILayout.mController.mTaskInfo.userId));
                                break;
                            case 1:
                                BoundsCompatUILayout boundsCompatUILayout2 = inflateLayout;
                                boolean z2 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout2.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                BoundsCompatUIController boundsCompatUIController3 = boundsCompatUILayout2.mController;
                                ((ShellTaskOrganizer) boundsCompatUIController3.mCallback).onSizeCompatRestartButtonClicked(boundsCompatUIController3.mTaskInfo.taskId);
                                break;
                            case 2:
                                BoundsCompatUILayout boundsCompatUILayout3 = inflateLayout;
                                boolean z3 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout3.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout3.mController.setOrientationControlPolicy(0);
                                boundsCompatUILayout3.refreshButtonVisibility(false);
                                break;
                            case 3:
                                BoundsCompatUILayout boundsCompatUILayout4 = inflateLayout;
                                boolean z4 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout4.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout4.mController.setOrientationControlPolicy(31);
                                boundsCompatUILayout4.refreshButtonVisibility(false);
                                break;
                            case 4:
                                BoundsCompatUILayout boundsCompatUILayout5 = inflateLayout;
                                boolean z5 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout5.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout5.mController.setBoundsCompatAlignment(3);
                                boundsCompatUILayout5.refreshButtonVisibility(true);
                                break;
                            case 5:
                                BoundsCompatUILayout boundsCompatUILayout6 = inflateLayout;
                                boolean z6 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout6.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout6.mController.setBoundsCompatAlignment(5);
                                boundsCompatUILayout6.refreshButtonVisibility(true);
                                break;
                            case 6:
                                BoundsCompatUILayout boundsCompatUILayout7 = inflateLayout;
                                boolean z7 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout7.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout7.mController.setBoundsCompatAlignment(48);
                                boundsCompatUILayout7.refreshButtonVisibility(true);
                                break;
                            default:
                                BoundsCompatUILayout boundsCompatUILayout8 = inflateLayout;
                                boolean z8 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout8.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout8.mController.setBoundsCompatAlignment(80);
                                boundsCompatUILayout8.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            } else if (id == R.id.bounds_compat_rotation_off_button) {
                final int i4 = 3;
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUILayout$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i4) {
                            case 0:
                                BoundsCompatUILayout boundsCompatUILayout = inflateLayout;
                                boolean z = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout.getClass();
                                Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                intent.addFlags(268468224);
                                Bundle bundle = new Bundle();
                                bundle.putString(":settings:fragment_args_key", boundsCompatUILayout.mController.mTaskInfo.baseActivity.getPackageName());
                                intent.putExtra(":settings:show_fragment_args", bundle);
                                view.getContext().startActivityAsUser(intent, new UserHandle(boundsCompatUILayout.mController.mTaskInfo.userId));
                                break;
                            case 1:
                                BoundsCompatUILayout boundsCompatUILayout2 = inflateLayout;
                                boolean z2 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout2.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                BoundsCompatUIController boundsCompatUIController3 = boundsCompatUILayout2.mController;
                                ((ShellTaskOrganizer) boundsCompatUIController3.mCallback).onSizeCompatRestartButtonClicked(boundsCompatUIController3.mTaskInfo.taskId);
                                break;
                            case 2:
                                BoundsCompatUILayout boundsCompatUILayout3 = inflateLayout;
                                boolean z3 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout3.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout3.mController.setOrientationControlPolicy(0);
                                boundsCompatUILayout3.refreshButtonVisibility(false);
                                break;
                            case 3:
                                BoundsCompatUILayout boundsCompatUILayout4 = inflateLayout;
                                boolean z4 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout4.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout4.mController.setOrientationControlPolicy(31);
                                boundsCompatUILayout4.refreshButtonVisibility(false);
                                break;
                            case 4:
                                BoundsCompatUILayout boundsCompatUILayout5 = inflateLayout;
                                boolean z5 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout5.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout5.mController.setBoundsCompatAlignment(3);
                                boundsCompatUILayout5.refreshButtonVisibility(true);
                                break;
                            case 5:
                                BoundsCompatUILayout boundsCompatUILayout6 = inflateLayout;
                                boolean z6 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout6.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout6.mController.setBoundsCompatAlignment(5);
                                boundsCompatUILayout6.refreshButtonVisibility(true);
                                break;
                            case 6:
                                BoundsCompatUILayout boundsCompatUILayout7 = inflateLayout;
                                boolean z7 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout7.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout7.mController.setBoundsCompatAlignment(48);
                                boundsCompatUILayout7.refreshButtonVisibility(true);
                                break;
                            default:
                                BoundsCompatUILayout boundsCompatUILayout8 = inflateLayout;
                                boolean z8 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                boundsCompatUILayout8.getClass();
                                Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                boundsCompatUILayout8.mController.setBoundsCompatAlignment(80);
                                boundsCompatUILayout8.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            } else {
                final int i5 = 4;
                if (!CoreRune.FW_BOUNDS_COMPAT_UI_SUPPORT_ALIGNMENT) {
                    imageButton2.setVisibility(4);
                } else if (id == R.id.bounds_compat_align_left_button) {
                    imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUILayout$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i5) {
                                case 0:
                                    BoundsCompatUILayout boundsCompatUILayout = inflateLayout;
                                    boolean z = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout.getClass();
                                    Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                    intent.addFlags(268468224);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(":settings:fragment_args_key", boundsCompatUILayout.mController.mTaskInfo.baseActivity.getPackageName());
                                    intent.putExtra(":settings:show_fragment_args", bundle);
                                    view.getContext().startActivityAsUser(intent, new UserHandle(boundsCompatUILayout.mController.mTaskInfo.userId));
                                    break;
                                case 1:
                                    BoundsCompatUILayout boundsCompatUILayout2 = inflateLayout;
                                    boolean z2 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout2.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    BoundsCompatUIController boundsCompatUIController3 = boundsCompatUILayout2.mController;
                                    ((ShellTaskOrganizer) boundsCompatUIController3.mCallback).onSizeCompatRestartButtonClicked(boundsCompatUIController3.mTaskInfo.taskId);
                                    break;
                                case 2:
                                    BoundsCompatUILayout boundsCompatUILayout3 = inflateLayout;
                                    boolean z3 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout3.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout3.mController.setOrientationControlPolicy(0);
                                    boundsCompatUILayout3.refreshButtonVisibility(false);
                                    break;
                                case 3:
                                    BoundsCompatUILayout boundsCompatUILayout4 = inflateLayout;
                                    boolean z4 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout4.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout4.mController.setOrientationControlPolicy(31);
                                    boundsCompatUILayout4.refreshButtonVisibility(false);
                                    break;
                                case 4:
                                    BoundsCompatUILayout boundsCompatUILayout5 = inflateLayout;
                                    boolean z5 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout5.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout5.mController.setBoundsCompatAlignment(3);
                                    boundsCompatUILayout5.refreshButtonVisibility(true);
                                    break;
                                case 5:
                                    BoundsCompatUILayout boundsCompatUILayout6 = inflateLayout;
                                    boolean z6 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout6.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout6.mController.setBoundsCompatAlignment(5);
                                    boundsCompatUILayout6.refreshButtonVisibility(true);
                                    break;
                                case 6:
                                    BoundsCompatUILayout boundsCompatUILayout7 = inflateLayout;
                                    boolean z7 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout7.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout7.mController.setBoundsCompatAlignment(48);
                                    boundsCompatUILayout7.refreshButtonVisibility(true);
                                    break;
                                default:
                                    BoundsCompatUILayout boundsCompatUILayout8 = inflateLayout;
                                    boolean z8 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout8.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout8.mController.setBoundsCompatAlignment(80);
                                    boundsCompatUILayout8.refreshButtonVisibility(true);
                                    break;
                            }
                        }
                    });
                } else if (id == R.id.bounds_compat_align_right_button) {
                    final int i6 = 5;
                    imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUILayout$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i6) {
                                case 0:
                                    BoundsCompatUILayout boundsCompatUILayout = inflateLayout;
                                    boolean z = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout.getClass();
                                    Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                    intent.addFlags(268468224);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(":settings:fragment_args_key", boundsCompatUILayout.mController.mTaskInfo.baseActivity.getPackageName());
                                    intent.putExtra(":settings:show_fragment_args", bundle);
                                    view.getContext().startActivityAsUser(intent, new UserHandle(boundsCompatUILayout.mController.mTaskInfo.userId));
                                    break;
                                case 1:
                                    BoundsCompatUILayout boundsCompatUILayout2 = inflateLayout;
                                    boolean z2 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout2.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    BoundsCompatUIController boundsCompatUIController3 = boundsCompatUILayout2.mController;
                                    ((ShellTaskOrganizer) boundsCompatUIController3.mCallback).onSizeCompatRestartButtonClicked(boundsCompatUIController3.mTaskInfo.taskId);
                                    break;
                                case 2:
                                    BoundsCompatUILayout boundsCompatUILayout3 = inflateLayout;
                                    boolean z3 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout3.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout3.mController.setOrientationControlPolicy(0);
                                    boundsCompatUILayout3.refreshButtonVisibility(false);
                                    break;
                                case 3:
                                    BoundsCompatUILayout boundsCompatUILayout4 = inflateLayout;
                                    boolean z4 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout4.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout4.mController.setOrientationControlPolicy(31);
                                    boundsCompatUILayout4.refreshButtonVisibility(false);
                                    break;
                                case 4:
                                    BoundsCompatUILayout boundsCompatUILayout5 = inflateLayout;
                                    boolean z5 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout5.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout5.mController.setBoundsCompatAlignment(3);
                                    boundsCompatUILayout5.refreshButtonVisibility(true);
                                    break;
                                case 5:
                                    BoundsCompatUILayout boundsCompatUILayout6 = inflateLayout;
                                    boolean z6 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout6.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout6.mController.setBoundsCompatAlignment(5);
                                    boundsCompatUILayout6.refreshButtonVisibility(true);
                                    break;
                                case 6:
                                    BoundsCompatUILayout boundsCompatUILayout7 = inflateLayout;
                                    boolean z7 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout7.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout7.mController.setBoundsCompatAlignment(48);
                                    boundsCompatUILayout7.refreshButtonVisibility(true);
                                    break;
                                default:
                                    BoundsCompatUILayout boundsCompatUILayout8 = inflateLayout;
                                    boolean z8 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout8.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout8.mController.setBoundsCompatAlignment(80);
                                    boundsCompatUILayout8.refreshButtonVisibility(true);
                                    break;
                            }
                        }
                    });
                } else if (id == R.id.bounds_compat_align_top_button) {
                    final int i7 = 6;
                    imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUILayout$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i7) {
                                case 0:
                                    BoundsCompatUILayout boundsCompatUILayout = inflateLayout;
                                    boolean z = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout.getClass();
                                    Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                    intent.addFlags(268468224);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(":settings:fragment_args_key", boundsCompatUILayout.mController.mTaskInfo.baseActivity.getPackageName());
                                    intent.putExtra(":settings:show_fragment_args", bundle);
                                    view.getContext().startActivityAsUser(intent, new UserHandle(boundsCompatUILayout.mController.mTaskInfo.userId));
                                    break;
                                case 1:
                                    BoundsCompatUILayout boundsCompatUILayout2 = inflateLayout;
                                    boolean z2 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout2.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    BoundsCompatUIController boundsCompatUIController3 = boundsCompatUILayout2.mController;
                                    ((ShellTaskOrganizer) boundsCompatUIController3.mCallback).onSizeCompatRestartButtonClicked(boundsCompatUIController3.mTaskInfo.taskId);
                                    break;
                                case 2:
                                    BoundsCompatUILayout boundsCompatUILayout3 = inflateLayout;
                                    boolean z3 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout3.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout3.mController.setOrientationControlPolicy(0);
                                    boundsCompatUILayout3.refreshButtonVisibility(false);
                                    break;
                                case 3:
                                    BoundsCompatUILayout boundsCompatUILayout4 = inflateLayout;
                                    boolean z4 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout4.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout4.mController.setOrientationControlPolicy(31);
                                    boundsCompatUILayout4.refreshButtonVisibility(false);
                                    break;
                                case 4:
                                    BoundsCompatUILayout boundsCompatUILayout5 = inflateLayout;
                                    boolean z5 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout5.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout5.mController.setBoundsCompatAlignment(3);
                                    boundsCompatUILayout5.refreshButtonVisibility(true);
                                    break;
                                case 5:
                                    BoundsCompatUILayout boundsCompatUILayout6 = inflateLayout;
                                    boolean z6 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout6.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout6.mController.setBoundsCompatAlignment(5);
                                    boundsCompatUILayout6.refreshButtonVisibility(true);
                                    break;
                                case 6:
                                    BoundsCompatUILayout boundsCompatUILayout7 = inflateLayout;
                                    boolean z7 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout7.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout7.mController.setBoundsCompatAlignment(48);
                                    boundsCompatUILayout7.refreshButtonVisibility(true);
                                    break;
                                default:
                                    BoundsCompatUILayout boundsCompatUILayout8 = inflateLayout;
                                    boolean z8 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout8.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout8.mController.setBoundsCompatAlignment(80);
                                    boundsCompatUILayout8.refreshButtonVisibility(true);
                                    break;
                            }
                        }
                    });
                } else if (id == R.id.bounds_compat_align_bottom_button) {
                    final int i8 = 7;
                    imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUILayout$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i8) {
                                case 0:
                                    BoundsCompatUILayout boundsCompatUILayout = inflateLayout;
                                    boolean z = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout.getClass();
                                    Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                    intent.addFlags(268468224);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(":settings:fragment_args_key", boundsCompatUILayout.mController.mTaskInfo.baseActivity.getPackageName());
                                    intent.putExtra(":settings:show_fragment_args", bundle);
                                    view.getContext().startActivityAsUser(intent, new UserHandle(boundsCompatUILayout.mController.mTaskInfo.userId));
                                    break;
                                case 1:
                                    BoundsCompatUILayout boundsCompatUILayout2 = inflateLayout;
                                    boolean z2 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout2.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    BoundsCompatUIController boundsCompatUIController3 = boundsCompatUILayout2.mController;
                                    ((ShellTaskOrganizer) boundsCompatUIController3.mCallback).onSizeCompatRestartButtonClicked(boundsCompatUIController3.mTaskInfo.taskId);
                                    break;
                                case 2:
                                    BoundsCompatUILayout boundsCompatUILayout3 = inflateLayout;
                                    boolean z3 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout3.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout3.mController.setOrientationControlPolicy(0);
                                    boundsCompatUILayout3.refreshButtonVisibility(false);
                                    break;
                                case 3:
                                    BoundsCompatUILayout boundsCompatUILayout4 = inflateLayout;
                                    boolean z4 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout4.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout4.mController.setOrientationControlPolicy(31);
                                    boundsCompatUILayout4.refreshButtonVisibility(false);
                                    break;
                                case 4:
                                    BoundsCompatUILayout boundsCompatUILayout5 = inflateLayout;
                                    boolean z5 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout5.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout5.mController.setBoundsCompatAlignment(3);
                                    boundsCompatUILayout5.refreshButtonVisibility(true);
                                    break;
                                case 5:
                                    BoundsCompatUILayout boundsCompatUILayout6 = inflateLayout;
                                    boolean z6 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout6.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout6.mController.setBoundsCompatAlignment(5);
                                    boundsCompatUILayout6.refreshButtonVisibility(true);
                                    break;
                                case 6:
                                    BoundsCompatUILayout boundsCompatUILayout7 = inflateLayout;
                                    boolean z7 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout7.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout7.mController.setBoundsCompatAlignment(48);
                                    boundsCompatUILayout7.refreshButtonVisibility(true);
                                    break;
                                default:
                                    BoundsCompatUILayout boundsCompatUILayout8 = inflateLayout;
                                    boolean z8 = BoundsCompatUILayout.DEBUG_BOUNDS_COMPAT_UI_LAYOUT;
                                    boundsCompatUILayout8.getClass();
                                    Log.d("BoundsCompatUILayout", "onClick v=" + view);
                                    boundsCompatUILayout8.mController.setBoundsCompatAlignment(80);
                                    boundsCompatUILayout8.refreshButtonVisibility(true);
                                    break;
                            }
                        }
                    });
                }
            }
        }
        inflateLayout.refreshButtonVisibility(false);
        inflateLayout.getRootView().getViewTreeObserver().registerFrameCommitCallback(inflateLayout.mFrameCommitCallback);
        if (!this.mIsRecreating) {
            BoundsCompatUIController boundsCompatUIController3 = this.mBoundsCompatUIController;
            if (boundsCompatUIController3.mTaskInfo.topActivityInDisplayCompat) {
                int i9 = this.mTaskId;
                ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) boundsCompatUIController3.mCallback;
                synchronized (shellTaskOrganizer.mLock) {
                    taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.get(i9);
                }
                if (taskAppearedInfo != null && (activityInfo = taskAppearedInfo.getTaskInfo().topActivityInfo) != null) {
                    FrameworkStatsLog.write(387, activityInfo.applicationInfo.uid, 1);
                }
            }
        }
        return this.mLayout;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        boolean z = false;
        if (this.mDisplayId != 0) {
            return false;
        }
        TaskInfo taskInfo = this.mBoundsCompatUIController.mTaskInfo;
        if (taskInfo.topActivityInSizeCompat && !taskInfo.topActivityInFixedAspectRatio && !taskInfo.topActivityInDisplayCompat) {
            z = true;
        }
        return !z;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final WindowManager.LayoutParams getWindowLayoutParams() {
        BoundsCompatUILayout boundsCompatUILayout = this.mLayout;
        if (boundsCompatUILayout == null) {
            Log.d(this.TAG, "getWindowLayoutParams: no layout");
            return new WindowManager.LayoutParams();
        }
        boundsCompatUILayout.measure(0, 0);
        Rect taskBounds = getTaskBounds();
        return getWindowLayoutParams(taskBounds.width(), taskBounds.height());
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10001;
    }

    public BoundsCompatUILayout inflateLayout() {
        Rect activityBounds = this.mBoundsCompatUIController.getActivityBounds();
        return (BoundsCompatUILayout) LayoutInflater.from(this.mContext).inflate(activityBounds.width() < activityBounds.height() ? R.layout.bounds_compat_ui_layout : R.layout.bounds_compat_ui_layout_vertical, (ViewGroup) null);
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayout = null;
        BoundsCompatUIController boundsCompatUIController = this.mBoundsCompatUIController;
        boundsCompatUIController.mHandler.removeCallbacksAndMessages(boundsCompatUIController);
        BoundsCompatUIUtil$TipPopupAdapter.INSTANCE.release();
    }

    public final String toString() {
        return this.TAG + "{mLayout=" + this.mLayout + ", mCompatUIController=, mBoundsCompatUIController=}";
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        BoundsCompatUILayout boundsCompatUILayout;
        BoundsCompatUIController boundsCompatUIController = this.mBoundsCompatUIController;
        TaskInfo taskInfo2 = boundsCompatUIController.mTaskInfo;
        boundsCompatUIController.mTaskInfo = taskInfo;
        int i = (BoundsCompatUIController.isAlignedVertically(taskInfo) == BoundsCompatUIController.isAlignedVertically(taskInfo2) && taskInfo.topActivityBounds.equals(taskInfo2.topActivityBounds) && taskInfo.configuration.windowConfiguration.getBounds().equals(taskInfo2.configuration.windowConfiguration.getBounds())) ? 0 : 1;
        if (taskInfo.topActivityInDisplayCompat || taskInfo.topActivityInFixedAspectRatio) {
            if (!(taskInfo2.topActivityInDisplayCompat || taskInfo2.topActivityInFixedAspectRatio)) {
                i |= 2;
            }
        }
        if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_FOR_BOUNDS_COMPAT_UI_EXPERIENCE && (boundsCompatUILayout = this.mLayout) != null && taskInfo.singleTapFromLetterbox) {
            boundsCompatUILayout.refreshButtonVisibility(false);
        } else if ((i & 1) != 0) {
            release();
            this.mIsRecreating = true;
        } else {
            BoundsCompatUILayout boundsCompatUILayout2 = this.mLayout;
            if (boundsCompatUILayout2 != null && (i & 2) != 0) {
                boundsCompatUILayout2.refreshButtonVisibility(false);
            }
        }
        boolean updateCompatInfo = super.updateCompatInfo(taskInfo, taskListener, z);
        this.mIsRecreating = false;
        return updateCompatInfo;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void updateSurfacePosition() {
        boolean z;
        if (this.mBoundsCompatUIController.mTaskInfo.topActivityInDisplayCompat) {
            Context context = this.mContext;
            BoundsCompatUIUtil$Prefs[] boundsCompatUIUtil$PrefsArr = BoundsCompatUIUtil$Prefs.$VALUES;
            if (!SystemProperties.getBoolean("debug.boundscompatui.hint", false)) {
                if ((context == null ? 3 : context.getSharedPreferences("boundscompatui_prefs", 0).getInt("RestartHintShownCount", 0)) >= 3) {
                    z = false;
                    if (z || !this.mShouldShowHint) {
                    }
                    this.mShouldShowHint = false;
                    BoundsCompatUILayout boundsCompatUILayout = this.mLayout;
                    Context context2 = this.mContext;
                    int i = context2 == null ? 3 : context2.getSharedPreferences("boundscompatui_prefs", 0).getInt("RestartHintShownCount", 0);
                    if (i < 3) {
                        int i2 = i + 1;
                        if (context2 != null) {
                            context2.getSharedPreferences("boundscompatui_prefs", 0).edit().putInt("RestartHintShownCount", i2).apply();
                        }
                    }
                    BoundsCompatUIUtil$TipPopupBuilder boundsCompatUIUtil$TipPopupBuilder = new BoundsCompatUIUtil$TipPopupBuilder(boundsCompatUILayout);
                    boundsCompatUIUtil$TipPopupBuilder.mMessage = this.mContext.getResources().getString(R.string.restart_button_guide_onboarding);
                    BoundsCompatUIUtil$TipPopupAdapter.m2745$$Nest$mbuild(BoundsCompatUIUtil$TipPopupAdapter.INSTANCE, boundsCompatUIUtil$TipPopupBuilder).show();
                    return;
                }
            }
            z = true;
            if (z) {
            }
        }
    }
}
