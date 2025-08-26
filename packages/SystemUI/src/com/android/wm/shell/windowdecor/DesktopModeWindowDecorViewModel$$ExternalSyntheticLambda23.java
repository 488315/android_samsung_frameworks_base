package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.android.systemui.R;
import com.android.wm.shell.windowdecor.policy.CaptionButtonPolicy;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder;
import com.android.wm.shell.windowdecor.widget.CaptionButton;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda23 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda23(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolderAsMultiTaskingAppHeader;
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) obj;
        switch (this.$r8$classId) {
            case 0:
                if (DesktopModeWindowDecoration.isAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder)) {
                    MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle = DesktopModeWindowDecoration.asMultiTaskingAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder);
                    if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle != null) {
                        boolean z = desktopModeWindowDecoration.mIsDesktopModeSupportedOnDisplay;
                        ImageButton imageButton = multiTaskingHandleViewHolderAsMultiTaskingAppHandle.captionHandle;
                        ActivityManager.RunningTaskInfo runningTaskInfo = multiTaskingHandleViewHolderAsMultiTaskingAppHandle.taskInfo;
                        if (runningTaskInfo == null) {
                            runningTaskInfo = null;
                        }
                        imageButton.setImageTintList(ColorStateList.valueOf(multiTaskingHandleViewHolderAsMultiTaskingAppHandle.getCaptionHandleColor(runningTaskInfo, z)));
                        break;
                    }
                } else if (DesktopModeWindowDecoration.isAppHeader(desktopModeWindowDecoration.mWindowDecorViewHolder) && (multiTaskingHeaderViewHolderAsMultiTaskingAppHeader = DesktopModeWindowDecoration.asMultiTaskingAppHeader(desktopModeWindowDecoration.mWindowDecorViewHolder)) != null) {
                    CaptionButtonPolicy captionButtonPolicy = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader.captionButtonPolicy;
                    View view = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader.captionView;
                    captionButtonPolicy.getClass();
                    ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.button_container);
                    if (viewGroup != null) {
                        ColorStateList buttonColor = captionButtonPolicy.getButtonColor();
                        for (int i = 0; i < viewGroup.getChildCount(); i++) {
                            View childAt = viewGroup.getChildAt(i);
                            if (childAt instanceof CaptionButton) {
                                ((CaptionButton) childAt).setImageTintList(buttonColor);
                            }
                        }
                        break;
                    }
                }
                break;
            default:
                desktopModeWindowDecoration.relayout(desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mHasGlobalFocus, desktopModeWindowDecoration.mExclusionRegion);
                break;
        }
    }
}
