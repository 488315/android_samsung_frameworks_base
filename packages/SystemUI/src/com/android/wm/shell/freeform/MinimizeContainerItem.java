package com.android.wm.shell.freeform;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MinimizeContainerItem extends FreeformContainerItem {
    public final int mTaskId;

    public MinimizeContainerItem(Context context, String str, ComponentName componentName, int i, int i2, boolean z) {
        super(context, str, componentName, i2);
        this.mItemType = 1;
        this.mTaskId = i;
        this.mAnimationCompleted = z;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final int getTaskId() {
        return this.mTaskId;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void launch() {
        FreeformContainerSystemProxy.mExecutor.execute(new FreeformContainerSystemProxy$$ExternalSyntheticLambda2(this.mContext, this.mTaskId));
        if (CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING) {
            if (CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY ? true ^ MultiWindowUtils.isInSubDisplay(this.mContext) : true) {
                CoreSaLogger.logForAdvanced("2201");
            }
        }
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void loadShowingIcon(FreeformContainerIconLoader freeformContainerIconLoader) {
        Context context = this.mContext;
        ComponentName componentName = this.mComponentName;
        int i = this.mUserId;
        Drawable showingIcon = freeformContainerIconLoader.getShowingIcon(MultiWindowUtils.getAppIcon(context, componentName, i, this.mPackageName), null);
        if (SemPersonaManager.isKnoxId(i) || SemPersonaManager.isDualAppId(i)) {
            showingIcon = freeformContainerIconLoader.mPackageManager.getUserBadgedIcon(showingIcon, new UserHandle(i));
        }
        this.mShowingIcon = showingIcon;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void throwAway(FreeformContainerItemController freeformContainerItemController) {
        freeformContainerItemController.removeItem(this);
        FreeformContainerSystemProxy.mExecutor.execute(new FreeformContainerSystemProxy$$ExternalSyntheticLambda4(this));
    }

    public final String toString() {
        return "MinimizeContainerItem {mPackageName=" + this.mPackageName + ", mTaskId=" + this.mTaskId + ", mUserId=" + this.mUserId;
    }
}
