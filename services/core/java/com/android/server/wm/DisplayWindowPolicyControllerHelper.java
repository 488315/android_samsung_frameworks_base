package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.util.ArraySet;
import android.window.DisplayWindowPolicyController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DisplayWindowPolicyControllerHelper {
    public final DisplayContent mDisplayContent;
    public final DisplayWindowPolicyController mDisplayWindowPolicyController;
    public ActivityRecord mTopRunningActivity = null;
    public ArraySet mRunningUid = new ArraySet();

    public DisplayWindowPolicyControllerHelper(DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
        this.mDisplayWindowPolicyController = displayContent.mWmService.mDisplayManagerInternal.getDisplayWindowPolicyController(displayContent.mDisplayId);
    }

    public final boolean hasController() {
        return this.mDisplayWindowPolicyController != null;
    }

    public final boolean keepActivityOnWindowFlagsChanged(ActivityInfo activityInfo, int i, int i2, int i3, int i4) {
        DisplayWindowPolicyController displayWindowPolicyController = this.mDisplayWindowPolicyController;
        if (displayWindowPolicyController != null && displayWindowPolicyController.isInterestedWindowFlags(i, i2)) {
            return this.mDisplayWindowPolicyController.keepActivityOnWindowFlagsChanged(activityInfo, i3, i4);
        }
        return true;
    }
}
