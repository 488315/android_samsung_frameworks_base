package com.android.wm.shell.freeform;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class FreeformContainerItem {
    public final ComponentName mComponentName;
    public final Context mContext;
    public String mDescription;
    public ImageView mIconView;
    public int mItemType;
    public final String mPackageName;
    public Drawable mShowingIcon;
    public final int mUserId;
    public boolean mAnimationCompleted = false;
    public boolean mIconLoadCompleted = false;
    public boolean mPublishCompleted = false;

    public FreeformContainerItem(Context context, String str, ComponentName componentName, int i) {
        this.mContext = context;
        this.mPackageName = str;
        if (str.equals("com.samsung.android.messaging")) {
            this.mComponentName = context.getPackageManager().getLaunchIntentForPackage("com.samsung.android.messaging").getComponent();
        } else {
            this.mComponentName = componentName;
        }
        this.mUserId = i;
        this.mItemType = 0;
    }

    public MultiInstanceItem asMultiInstanceItem() {
        return null;
    }

    public int getItemCount() {
        return 0;
    }

    public List getItemList() {
        return null;
    }

    public abstract int getTaskId();

    public boolean isParentMultiInstanceItem() {
        return false;
    }

    public abstract void launch();

    public abstract void loadShowingIcon(FreeformContainerIconLoader freeformContainerIconLoader);

    public boolean needLoading(FreeformContainerItemController freeformContainerItemController) {
        return true;
    }

    public void setIconView(ImageView imageView) {
        this.mIconView = imageView;
    }

    public void throwAway(FreeformContainerItemController freeformContainerItemController) {
        freeformContainerItemController.removeItem(this);
    }

    public void unsetIconLoadCompleted() {
        this.mIconLoadCompleted = false;
        this.mPublishCompleted = false;
    }
}
