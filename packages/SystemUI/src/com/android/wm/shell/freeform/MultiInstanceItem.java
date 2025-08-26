package com.android.wm.shell.freeform;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.Log;
import android.widget.ImageView;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class MultiInstanceItem extends FreeformContainerItem {
    public final List mChildItemList;
    public final MultiInstanceItem mParentItem;
    public Bitmap mSnapshotBitmap;
    public final int mTaskId;

    public MultiInstanceItem(FreeformContainerItem freeformContainerItem, MultiInstanceItem multiInstanceItem) {
        super(freeformContainerItem.mContext, freeformContainerItem.mPackageName, freeformContainerItem.mComponentName, freeformContainerItem.mUserId);
        this.mChildItemList = Collections.synchronizedList(new ArrayList());
        this.mItemType = 3;
        this.mTaskId = freeformContainerItem.getTaskId();
        this.mParentItem = multiInstanceItem;
    }

    public final void addChildItem(MultiInstanceItem multiInstanceItem) {
        if (this.mChildItemList.contains(multiInstanceItem)) {
            Log.w("FreeformContainer", "[MultiInstanceItem]  addChildItem: failed, duplicated, " + multiInstanceItem);
        } else {
            this.mChildItemList.add(multiInstanceItem);
            Log.d("FreeformContainer", "addChildItem: " + multiInstanceItem + ", this=" + this);
        }
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final int getItemCount() {
        return this.mChildItemList.size();
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final List getItemList() {
        return this.mChildItemList;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final int getTaskId() {
        return this.mTaskId;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final boolean isParentMultiInstanceItem() {
        return this.mParentItem == null;
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
    public final void setIconView(ImageView imageView) {
        this.mChildItemList.forEach(new MultiInstanceItem$$ExternalSyntheticLambda1(imageView, 1));
        this.mIconView = imageView;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void throwAway(FreeformContainerItemController freeformContainerItemController) {
        freeformContainerItemController.removeItem(this);
        FreeformContainerSystemProxy.mExecutor.execute(new FreeformContainerSystemProxy$$ExternalSyntheticLambda4(this));
    }

    public final String toString() {
        return "MultiInstanceItem{" + Integer.toHexString(System.identityHashCode(this)) + " isParent=" + isParentMultiInstanceItem() + " pkg=" + this.mPackageName + " mSnapshot=" + this.mSnapshotBitmap + " itemCount=" + this.mChildItemList.size() + "}";
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void unsetIconLoadCompleted() {
        Iterator it = this.mChildItemList.iterator();
        while (it.hasNext()) {
            ((FreeformContainerItem) it.next()).unsetIconLoadCompleted();
        }
        this.mIconLoadCompleted = false;
        this.mPublishCompleted = false;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final MultiInstanceItem asMultiInstanceItem() {
        return this;
    }
}
