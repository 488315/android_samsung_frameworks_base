package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.impl.CompatUIEvents;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MultiTaskingAppCompatUIWindowManager extends CompatUIWindowManagerAbstract {
    public final String TAG;
    public boolean mIsRecreating;
    MultiTaskingAppCompatUILayout mLayout;
    public final MultiTaskingAppCompatUIController mMultiTaskingAppCompatUIController;
    public boolean mShouldShowHint;

    public MultiTaskingAppCompatUIWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, Consumer<CompatUIEvents> consumer, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, CompatUIController compatUIController) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.TAG = "MultiTaskingAppCompatUIWindowManager";
        this.mMultiTaskingAppCompatUIController = new MultiTaskingAppCompatUIController(this.mContext, this, taskInfo, consumer, compatUIController);
        this.mShouldShowHint = true;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        ViewGroup.LayoutParams layoutParams;
        final MultiTaskingAppCompatUILayout inflateLayout = inflateLayout();
        this.mLayout = inflateLayout;
        MultiTaskingAppCompatUIController multiTaskingAppCompatUIController = this.mMultiTaskingAppCompatUIController;
        inflateLayout.mWindowManager = this;
        inflateLayout.mController = multiTaskingAppCompatUIController;
        inflateLayout.mNaviButtonSize = (int) inflateLayout.getResources().getDimension(R.dimen.mt_app_compat_button_size);
        MultiTaskingAppCompatUIController multiTaskingAppCompatUIController2 = inflateLayout.mController;
        multiTaskingAppCompatUIController2.getClass();
        try {
            multiTaskingAppCompatUIController2.mOrientationPolicy = multiTaskingAppCompatUIController2.mActivityTaskManager.getOrientationControlPolicy(UserHandle.getCallingUserId(), multiTaskingAppCompatUIController2.mTaskInfo.baseActivity.getPackageName());
        } catch (RemoteException unused) {
            multiTaskingAppCompatUIController2.mOrientationPolicy = 0;
        }
        try {
            multiTaskingAppCompatUIController2.mIsRotationFrozen = multiTaskingAppCompatUIController2.mWindowManager.isRotationFrozen();
        } catch (RemoteException e) {
            Log.e("MultiTaskingAppCompatUIController", "Failed to load orientation policy, set frozen", e);
            multiTaskingAppCompatUIController2.mIsRotationFrozen = true;
        }
        try {
            multiTaskingAppCompatUIController2.mAlignment = multiTaskingAppCompatUIController2.mActivityTaskManager.getAppCompatAlignment();
        } catch (RemoteException e2) {
            Log.e("MultiTaskingAppCompatUIController", "Failed to retrieve app compat alignment.", e2);
        }
        MultiTaskingAppCompatUIController multiTaskingAppCompatUIController3 = inflateLayout.mController;
        multiTaskingAppCompatUIController3.getClass();
        Rect rect = new Rect(multiTaskingAppCompatUIController3.mTaskInfo.appCompatTaskInfo.topActivityBounds);
        TextView textView = (TextView) inflateLayout.findViewById(R.id.mt_app_compat_ui_main);
        if (textView != null && (layoutParams = textView.getLayoutParams()) != null) {
            if (MultiTaskingAppCompatUIController.isAlignedVertically(inflateLayout.mController.mTaskInfo)) {
                layoutParams.height = rect.height();
            } else {
                layoutParams.width = rect.width();
            }
            textView.setLayoutParams(layoutParams);
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(Integer.valueOf(R.id.mt_app_compat_fixed_aspect_ratio_shortcut_button), Integer.valueOf(R.id.mt_app_compat_restart_button), Integer.valueOf(R.id.mt_app_compat_rotation_on_button), Integer.valueOf(R.id.mt_app_compat_rotation_off_button)));
        AccessibilityManager accessibilityManager = inflateLayout.mController.mAccessibilityManager;
        if (accessibilityManager != null ? accessibilityManager.semIsScreenReaderEnabled() : false) {
            Log.d("MultiTaskingAppCompatUILayout", "ScreenReader was enabled, do not show alignment buttons");
            i = R.id.mt_app_compat_align_bottom_button;
            i2 = R.id.mt_app_compat_align_top_button;
            i3 = R.id.mt_app_compat_align_right_button;
            i4 = R.id.mt_app_compat_align_left_button;
        } else {
            Rect taskBounds = inflateLayout.mWindowManager.getTaskBounds();
            int dimension = (int) inflateLayout.getResources().getDimension(17106378);
            MultiTaskingAppCompatUIController multiTaskingAppCompatUIController4 = inflateLayout.mController;
            multiTaskingAppCompatUIController4.getClass();
            Rect rect2 = new Rect(multiTaskingAppCompatUIController4.mTaskInfo.appCompatTaskInfo.topActivityBounds);
            DisplayLayout displayLayout = inflateLayout.mWindowManager.mDisplayLayout;
            i = R.id.mt_app_compat_align_bottom_button;
            boolean isAlignedVertically = MultiTaskingAppCompatUIController.isAlignedVertically(inflateLayout.mController.mTaskInfo);
            i2 = R.id.mt_app_compat_align_top_button;
            i3 = R.id.mt_app_compat_align_right_button;
            if (isAlignedVertically) {
                int height = taskBounds.height();
                i4 = R.id.mt_app_compat_align_left_button;
                int height2 = ((((height - displayLayout.mNavBarFrameHeight) - rect2.height()) - dimension) - (inflateLayout.mNaviButtonSize << 1)) >> 2;
                inflateLayout.mVerticalMarginFromActivityBounds = height2;
                if (height2 >= 0) {
                    arrayList.add(Integer.valueOf(R.id.mt_app_compat_align_top_button));
                    arrayList.add(Integer.valueOf(R.id.mt_app_compat_align_bottom_button));
                } else {
                    StringBuilder sb = new StringBuilder("Not enough space to show alignment buttons vertically. taskBounds=");
                    sb.append(taskBounds);
                    sb.append(", buttonSize=");
                    sb.append(inflateLayout.mNaviButtonSize);
                    sb.append(", activityBounds=");
                    sb.append(rect2);
                    sb.append(", statusBarHeight=");
                    sb.append(dimension);
                    sb.append(", navBarFrameHeight=");
                    sb.append(displayLayout.mNavBarFrameHeight);
                    sb.append(", mVerticalMarginFromActivityBounds=");
                    RecyclerView$$ExternalSyntheticOutline0.m(inflateLayout.mVerticalMarginFromActivityBounds, "MultiTaskingAppCompatUILayout", sb);
                }
            } else {
                i4 = R.id.mt_app_compat_align_left_button;
                int width = taskBounds.width() - (rect2.width() + (inflateLayout.mNaviButtonSize << 1));
                inflateLayout.mHorizontalMarginFromActivityBounds = width;
                if (width >= 0) {
                    arrayList.add(Integer.valueOf(R.id.mt_app_compat_align_left_button));
                    arrayList.add(Integer.valueOf(R.id.mt_app_compat_align_right_button));
                } else {
                    Log.d("MultiTaskingAppCompatUILayout", "Not enough space to show alignment buttons, taskBounds=" + taskBounds + ", buttonSize=" + inflateLayout.mNaviButtonSize + ", activityBounds=" + rect2);
                }
            }
        }
        int size = arrayList.size();
        int i9 = 0;
        while (i9 < size) {
            Object obj = arrayList.get(i9);
            i9++;
            Integer num = (Integer) obj;
            View findViewById = inflateLayout.findViewById(num.intValue());
            if (findViewById instanceof ImageButton) {
                inflateLayout.mButtons.put(num, (ImageButton) findViewById);
            }
        }
        inflateLayout.mSwitchableButtonContainer = (FrameLayout) inflateLayout.findViewById(R.id.mt_app_compat_switchable_button_container);
        Iterator it = inflateLayout.mButtons.entrySet().iterator();
        while (it.hasNext()) {
            final ImageButton imageButton = (ImageButton) ((Map.Entry) it.next()).getValue();
            int id = imageButton.getId();
            imageButton.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout.2
                public final /* synthetic */ ImageButton val$btn;

                public AnonymousClass2(final ImageButton imageButton2) {
                    r2 = imageButton2;
                }

                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action != 0 && action != 1) {
                        return false;
                    }
                    r2.startAnimation(AnimationUtils.loadAnimation(((FrameLayout) MultiTaskingAppCompatUILayout.this).mContext, motionEvent.getAction() == 0 ? R.anim.mt_app_compat_ui_btn_press : R.anim.mt_app_compat_ui_btn_release));
                    return false;
                }
            });
            if (id == R.id.mt_app_compat_fixed_aspect_ratio_shortcut_button) {
                final int i10 = 0;
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i11 = i10;
                        MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = inflateLayout;
                        switch (i11) {
                            case 0:
                                int i12 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                intent.addFlags(268468224);
                                Bundle bundle = new Bundle();
                                ComponentName componentName = multiTaskingAppCompatUILayout.mController.mTaskInfo.topActivity;
                                bundle.putString(":settings:fragment_args_key", componentName != null ? componentName.getPackageName() : null);
                                intent.putExtra(":settings:show_fragment_args", bundle);
                                view.getContext().startActivityAsUser(intent, new UserHandle(multiTaskingAppCompatUILayout.mController.mTaskInfo.userId));
                                break;
                            case 1:
                                int i13 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(80);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            case 2:
                                int i14 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                MultiTaskingAppCompatUIController multiTaskingAppCompatUIController5 = multiTaskingAppCompatUILayout.mController;
                                multiTaskingAppCompatUIController5.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(multiTaskingAppCompatUIController5.mTaskInfo.taskId));
                                break;
                            case 3:
                                int i15 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(0);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                break;
                            case 4:
                                int i16 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(31);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                break;
                            case 5:
                                int i17 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(3);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            case 6:
                                int i18 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(5);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            default:
                                int i19 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(48);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            } else if (id == R.id.mt_app_compat_restart_button) {
                final int i11 = 2;
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i112 = i11;
                        MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = inflateLayout;
                        switch (i112) {
                            case 0:
                                int i12 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                intent.addFlags(268468224);
                                Bundle bundle = new Bundle();
                                ComponentName componentName = multiTaskingAppCompatUILayout.mController.mTaskInfo.topActivity;
                                bundle.putString(":settings:fragment_args_key", componentName != null ? componentName.getPackageName() : null);
                                intent.putExtra(":settings:show_fragment_args", bundle);
                                view.getContext().startActivityAsUser(intent, new UserHandle(multiTaskingAppCompatUILayout.mController.mTaskInfo.userId));
                                break;
                            case 1:
                                int i13 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(80);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            case 2:
                                int i14 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                MultiTaskingAppCompatUIController multiTaskingAppCompatUIController5 = multiTaskingAppCompatUILayout.mController;
                                multiTaskingAppCompatUIController5.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(multiTaskingAppCompatUIController5.mTaskInfo.taskId));
                                break;
                            case 3:
                                int i15 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(0);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                break;
                            case 4:
                                int i16 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(31);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                break;
                            case 5:
                                int i17 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(3);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            case 6:
                                int i18 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(5);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            default:
                                int i19 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(48);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            } else if (id == R.id.mt_app_compat_rotation_on_button) {
                final int i12 = 3;
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i112 = i12;
                        MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = inflateLayout;
                        switch (i112) {
                            case 0:
                                int i122 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                intent.addFlags(268468224);
                                Bundle bundle = new Bundle();
                                ComponentName componentName = multiTaskingAppCompatUILayout.mController.mTaskInfo.topActivity;
                                bundle.putString(":settings:fragment_args_key", componentName != null ? componentName.getPackageName() : null);
                                intent.putExtra(":settings:show_fragment_args", bundle);
                                view.getContext().startActivityAsUser(intent, new UserHandle(multiTaskingAppCompatUILayout.mController.mTaskInfo.userId));
                                break;
                            case 1:
                                int i13 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(80);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            case 2:
                                int i14 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                MultiTaskingAppCompatUIController multiTaskingAppCompatUIController5 = multiTaskingAppCompatUILayout.mController;
                                multiTaskingAppCompatUIController5.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(multiTaskingAppCompatUIController5.mTaskInfo.taskId));
                                break;
                            case 3:
                                int i15 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(0);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                break;
                            case 4:
                                int i16 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(31);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                break;
                            case 5:
                                int i17 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(3);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            case 6:
                                int i18 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(5);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            default:
                                int i19 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(48);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            } else if (id == R.id.mt_app_compat_rotation_off_button) {
                final int i13 = 4;
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i112 = i13;
                        MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = inflateLayout;
                        switch (i112) {
                            case 0:
                                int i122 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                intent.addFlags(268468224);
                                Bundle bundle = new Bundle();
                                ComponentName componentName = multiTaskingAppCompatUILayout.mController.mTaskInfo.topActivity;
                                bundle.putString(":settings:fragment_args_key", componentName != null ? componentName.getPackageName() : null);
                                intent.putExtra(":settings:show_fragment_args", bundle);
                                view.getContext().startActivityAsUser(intent, new UserHandle(multiTaskingAppCompatUILayout.mController.mTaskInfo.userId));
                                break;
                            case 1:
                                int i132 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(80);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            case 2:
                                int i14 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                MultiTaskingAppCompatUIController multiTaskingAppCompatUIController5 = multiTaskingAppCompatUILayout.mController;
                                multiTaskingAppCompatUIController5.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(multiTaskingAppCompatUIController5.mTaskInfo.taskId));
                                break;
                            case 3:
                                int i15 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(0);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                break;
                            case 4:
                                int i16 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(31);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                break;
                            case 5:
                                int i17 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(3);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            case 6:
                                int i18 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(5);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                            default:
                                int i19 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(48);
                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                break;
                        }
                    }
                });
            } else {
                i5 = i4;
                if (id == i5) {
                    final int i14 = 5;
                    imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i112 = i14;
                            MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = inflateLayout;
                            switch (i112) {
                                case 0:
                                    int i122 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                    Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                    intent.addFlags(268468224);
                                    Bundle bundle = new Bundle();
                                    ComponentName componentName = multiTaskingAppCompatUILayout.mController.mTaskInfo.topActivity;
                                    bundle.putString(":settings:fragment_args_key", componentName != null ? componentName.getPackageName() : null);
                                    intent.putExtra(":settings:show_fragment_args", bundle);
                                    view.getContext().startActivityAsUser(intent, new UserHandle(multiTaskingAppCompatUILayout.mController.mTaskInfo.userId));
                                    break;
                                case 1:
                                    int i132 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                    Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                    multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(80);
                                    multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                    break;
                                case 2:
                                    int i142 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                    Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                    MultiTaskingAppCompatUIController multiTaskingAppCompatUIController5 = multiTaskingAppCompatUILayout.mController;
                                    multiTaskingAppCompatUIController5.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(multiTaskingAppCompatUIController5.mTaskInfo.taskId));
                                    break;
                                case 3:
                                    int i15 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                    Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                    multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(0);
                                    multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                    break;
                                case 4:
                                    int i16 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                    Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                    multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(31);
                                    multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                    break;
                                case 5:
                                    int i17 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                    Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                    multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(3);
                                    multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                    break;
                                case 6:
                                    int i18 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                    Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                    multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(5);
                                    multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                    break;
                                default:
                                    int i19 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                    Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                    multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(48);
                                    multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                    break;
                            }
                        }
                    });
                    i8 = i;
                    i7 = i2;
                    i6 = i3;
                } else {
                    i6 = i3;
                    if (id == i6) {
                        final int i15 = 6;
                        imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda3
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i112 = i15;
                                MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = inflateLayout;
                                switch (i112) {
                                    case 0:
                                        int i122 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                        Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                        intent.addFlags(268468224);
                                        Bundle bundle = new Bundle();
                                        ComponentName componentName = multiTaskingAppCompatUILayout.mController.mTaskInfo.topActivity;
                                        bundle.putString(":settings:fragment_args_key", componentName != null ? componentName.getPackageName() : null);
                                        intent.putExtra(":settings:show_fragment_args", bundle);
                                        view.getContext().startActivityAsUser(intent, new UserHandle(multiTaskingAppCompatUILayout.mController.mTaskInfo.userId));
                                        break;
                                    case 1:
                                        int i132 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                        Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                        multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(80);
                                        multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                        break;
                                    case 2:
                                        int i142 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                        Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                        MultiTaskingAppCompatUIController multiTaskingAppCompatUIController5 = multiTaskingAppCompatUILayout.mController;
                                        multiTaskingAppCompatUIController5.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(multiTaskingAppCompatUIController5.mTaskInfo.taskId));
                                        break;
                                    case 3:
                                        int i152 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                        Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                        multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(0);
                                        multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                        break;
                                    case 4:
                                        int i16 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                        Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                        multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(31);
                                        multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                        break;
                                    case 5:
                                        int i17 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                        Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                        multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(3);
                                        multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                        break;
                                    case 6:
                                        int i18 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                        Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                        multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(5);
                                        multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                        break;
                                    default:
                                        int i19 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                        Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                        multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(48);
                                        multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                        break;
                                }
                            }
                        });
                        i8 = i;
                        i7 = i2;
                    } else {
                        i7 = i2;
                        if (id == i7) {
                            final int i16 = 7;
                            imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda3
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i112 = i16;
                                    MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = inflateLayout;
                                    switch (i112) {
                                        case 0:
                                            int i122 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                            Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                            intent.addFlags(268468224);
                                            Bundle bundle = new Bundle();
                                            ComponentName componentName = multiTaskingAppCompatUILayout.mController.mTaskInfo.topActivity;
                                            bundle.putString(":settings:fragment_args_key", componentName != null ? componentName.getPackageName() : null);
                                            intent.putExtra(":settings:show_fragment_args", bundle);
                                            view.getContext().startActivityAsUser(intent, new UserHandle(multiTaskingAppCompatUILayout.mController.mTaskInfo.userId));
                                            break;
                                        case 1:
                                            int i132 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                            Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                            multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(80);
                                            multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                            break;
                                        case 2:
                                            int i142 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                            Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                            MultiTaskingAppCompatUIController multiTaskingAppCompatUIController5 = multiTaskingAppCompatUILayout.mController;
                                            multiTaskingAppCompatUIController5.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(multiTaskingAppCompatUIController5.mTaskInfo.taskId));
                                            break;
                                        case 3:
                                            int i152 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                            Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                            multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(0);
                                            multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                            break;
                                        case 4:
                                            int i162 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                            Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                            multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(31);
                                            multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                            break;
                                        case 5:
                                            int i17 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                            Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                            multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(3);
                                            multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                            break;
                                        case 6:
                                            int i18 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                            Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                            multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(5);
                                            multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                            break;
                                        default:
                                            int i19 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                            Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                            multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(48);
                                            multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                            break;
                                    }
                                }
                            });
                            i8 = i;
                        } else {
                            i8 = i;
                            if (id == i8) {
                                final int i17 = 1;
                                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda3
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i112 = i17;
                                        MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = inflateLayout;
                                        switch (i112) {
                                            case 0:
                                                int i122 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                                Intent intent = new Intent("com.samsung.settings.FULL_SCREEN_APPS_SETTINGS");
                                                intent.addFlags(268468224);
                                                Bundle bundle = new Bundle();
                                                ComponentName componentName = multiTaskingAppCompatUILayout.mController.mTaskInfo.topActivity;
                                                bundle.putString(":settings:fragment_args_key", componentName != null ? componentName.getPackageName() : null);
                                                intent.putExtra(":settings:show_fragment_args", bundle);
                                                view.getContext().startActivityAsUser(intent, new UserHandle(multiTaskingAppCompatUILayout.mController.mTaskInfo.userId));
                                                break;
                                            case 1:
                                                int i132 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(80);
                                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                                break;
                                            case 2:
                                                int i142 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                                MultiTaskingAppCompatUIController multiTaskingAppCompatUIController5 = multiTaskingAppCompatUILayout.mController;
                                                multiTaskingAppCompatUIController5.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(multiTaskingAppCompatUIController5.mTaskInfo.taskId));
                                                break;
                                            case 3:
                                                int i152 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(0);
                                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                                break;
                                            case 4:
                                                int i162 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                                multiTaskingAppCompatUILayout.mController.setOrientationControlPolicy(31);
                                                multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
                                                break;
                                            case 5:
                                                int i172 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(3);
                                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                                break;
                                            case 6:
                                                int i18 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(5);
                                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                                break;
                                            default:
                                                int i19 = MultiTaskingAppCompatUILayout.$r8$clinit;
                                                Log.d("MultiTaskingAppCompatUILayout", "onClick v=" + view);
                                                multiTaskingAppCompatUILayout.mController.setMultiTaskingAppCompatAlignment(48);
                                                multiTaskingAppCompatUILayout.refreshButtonVisibility(true);
                                                break;
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
                i4 = i5;
                i3 = i6;
                i2 = i7;
                i = i8;
            }
            i8 = i;
            i7 = i2;
            i6 = i3;
            i5 = i4;
            i4 = i5;
            i3 = i6;
            i2 = i7;
            i = i8;
        }
        inflateLayout.refreshButtonVisibility(false);
        inflateLayout.getRootView().getViewTreeObserver().registerFrameCommitCallback(inflateLayout.mFrameCommitCallback);
        if (!this.mIsRecreating) {
            MultiTaskingAppCompatUIController multiTaskingAppCompatUIController5 = this.mMultiTaskingAppCompatUIController;
            TaskInfo taskInfo = multiTaskingAppCompatUIController5.mTaskInfo;
            if (taskInfo.appCompatTaskInfo.topActivityInDisplayCompat) {
                multiTaskingAppCompatUIController5.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonAppeared(taskInfo.taskId));
            }
        }
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        if (this.mDisplayId != 0) {
            return false;
        }
        return !this.mMultiTaskingAppCompatUIController.isInSizeCompat();
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final WindowManager.LayoutParams getWindowLayoutParams() {
        MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = this.mLayout;
        if (multiTaskingAppCompatUILayout == null) {
            Log.d(this.TAG, "getWindowLayoutParams: no layout");
            return new WindowManager.LayoutParams();
        }
        multiTaskingAppCompatUILayout.measure(0, 0);
        Rect taskBounds = getTaskBounds();
        return getWindowLayoutParams(taskBounds.width(), taskBounds.height());
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10001;
    }

    public MultiTaskingAppCompatUILayout inflateLayout() {
        MultiTaskingAppCompatUIController multiTaskingAppCompatUIController = this.mMultiTaskingAppCompatUIController;
        multiTaskingAppCompatUIController.getClass();
        Rect rect = new Rect(multiTaskingAppCompatUIController.mTaskInfo.appCompatTaskInfo.topActivityBounds);
        return (MultiTaskingAppCompatUILayout) LayoutInflater.from(this.mContext).inflate(rect.width() < rect.height() ? R.layout.mt_app_compat_ui_layout : R.layout.mt_app_compat_ui_layout_vertical, (ViewGroup) null);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayout = null;
        MultiTaskingAppCompatUIController multiTaskingAppCompatUIController = this.mMultiTaskingAppCompatUIController;
        multiTaskingAppCompatUIController.mHandler.removeCallbacksAndMessages(multiTaskingAppCompatUIController);
        MultiTaskingAppCompatUIUtil$TipPopupAdapter.INSTANCE.release();
    }

    public final String toString() {
        return this.TAG + "{mLayout=" + this.mLayout + ", mCompatUIController=, mMultiTaskingAppCompatUIController=}";
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout;
        MultiTaskingAppCompatUIController multiTaskingAppCompatUIController = this.mMultiTaskingAppCompatUIController;
        TaskInfo taskInfo2 = multiTaskingAppCompatUIController.mTaskInfo;
        multiTaskingAppCompatUIController.mTaskInfo = taskInfo;
        boolean z2 = (MultiTaskingAppCompatUIController.isAlignedVertically(taskInfo) == MultiTaskingAppCompatUIController.isAlignedVertically(taskInfo2) && taskInfo.appCompatTaskInfo.topActivityBounds.equals(taskInfo2.appCompatTaskInfo.topActivityBounds) && taskInfo.configuration.windowConfiguration.getBounds().equals(taskInfo2.configuration.windowConfiguration.getBounds())) ? false : true;
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION && (multiTaskingAppCompatUILayout = this.mLayout) != null && taskInfo.appCompatTaskInfo.singleTapFromLetterbox) {
            multiTaskingAppCompatUILayout.refreshButtonVisibility(false);
        } else if (z2) {
            release();
            this.mIsRecreating = true;
        }
        boolean updateCompatInfo = super.updateCompatInfo(taskInfo, taskListener, z);
        this.mIsRecreating = false;
        return updateCompatInfo;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void updateSurfacePosition() {
        if (this.mMultiTaskingAppCompatUIController.mTaskInfo.appCompatTaskInfo.topActivityInDisplayCompat) {
            Context context = this.mContext;
            MultiTaskingAppCompatUIUtil$Prefs[] multiTaskingAppCompatUIUtil$PrefsArr = MultiTaskingAppCompatUIUtil$Prefs.$VALUES;
            if (!SystemProperties.getBoolean("debug.mtappcompatui.hint", false)) {
                if ((context == null ? 3 : context.getSharedPreferences("boundscompatui_prefs", 0).getInt("RestartHintShownCount", 0)) >= 3) {
                    return;
                }
            }
            if (this.mShouldShowHint) {
                this.mShouldShowHint = false;
                MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = this.mLayout;
                Context context2 = this.mContext;
                int i = context2 == null ? 3 : context2.getSharedPreferences("boundscompatui_prefs", 0).getInt("RestartHintShownCount", 0);
                if (i < 3) {
                    int i2 = i + 1;
                    if (context2 != null) {
                        context2.getSharedPreferences("boundscompatui_prefs", 0).edit().putInt("RestartHintShownCount", i2).apply();
                    }
                }
                MultiTaskingAppCompatUIUtil$TipPopupBuilder multiTaskingAppCompatUIUtil$TipPopupBuilder = new MultiTaskingAppCompatUIUtil$TipPopupBuilder(multiTaskingAppCompatUILayout);
                multiTaskingAppCompatUIUtil$TipPopupBuilder.mMessage = this.mContext.getResources().getString(R.string.restart_button_guide_onboarding);
                MultiTaskingAppCompatUIUtil$TipPopupAdapter.m3225$$Nest$mbuild(MultiTaskingAppCompatUIUtil$TipPopupAdapter.INSTANCE, multiTaskingAppCompatUIUtil$TipPopupBuilder).show();
            }
        }
    }
}
