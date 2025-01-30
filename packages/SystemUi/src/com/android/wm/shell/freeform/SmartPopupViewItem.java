package com.android.wm.shell.freeform;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SmartPopupViewItem extends FreeformContainerItem {
    public final Notification mNotification;
    public final String mNotificationKey;

    public SmartPopupViewItem(Context context, String str, Notification notification2, String str2) {
        super(context, str, notification2.contentIntent.getIntent().getComponent(), context.getUserId());
        this.mNotification = notification2;
        this.mNotificationKey = str2;
        this.mAnimationCompleted = true;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final int getTaskId() {
        return -1;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void launch() {
        final ActivityOptions makeBasic = ActivityOptions.makeBasic();
        if (CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY ? !MultiWindowUtils.isInSubDisplay(this.mContext) : true) {
            makeBasic.setLaunchWindowingMode(5);
            makeBasic.preserveTaskWindowingMode();
        }
        makeBasic.setLaunchDisplayId(0);
        makeBasic.setPendingIntentBackgroundActivityStartMode(1);
        final PendingIntent pendingIntent = this.mNotification.contentIntent;
        FreeformContainerSystemProxy.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerSystemProxy$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    pendingIntent.send(null, 0, null, null, null, null, makeBasic.toBundle());
                } catch (PendingIntent.CanceledException e) {
                    Log.w("FreeformContainer", "[FreeformContainerSystemProxy] Failed to sendContentIntent: " + e);
                    e.printStackTrace();
                }
            }
        });
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW_SA_LOGGING && makeBasic.getLaunchWindowingMode() == 5) {
            CoreSaLogger.logForAdvanced("2004", "From Smart Popup");
        }
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void loadShowingIcon(FreeformContainerIconLoader freeformContainerIconLoader) {
        Drawable drawable;
        int i = this.mUserId;
        Context context = this.mContext;
        String str = this.mPackageName;
        freeformContainerIconLoader.getClass();
        try {
            drawable = context.getPackageManager().semGetApplicationIconForIconTray(PackageManagerUtil.getApplicationInfoAsUser(context, i, str), 48);
        } catch (Exception e) {
            e.printStackTrace();
            drawable = null;
        }
        Notification notification2 = this.mNotification;
        this.mShowingIcon = freeformContainerIconLoader.getShowingIcon(drawable, notification2.getLargeIcon() != null ? notification2.getLargeIcon().loadDrawable(context) : null);
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final boolean needLoading(FreeformContainerItemController freeformContainerItemController) {
        boolean z;
        freeformContainerItemController.getClass();
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            Iterator it = new ArrayList(freeformContainerItemController.mItemList).iterator();
            while (it.hasNext()) {
                FreeformContainerItem freeformContainerItem = (FreeformContainerItem) it.next();
                if (freeformContainerItem instanceof SmartPopupViewItem) {
                    if (this.mNotificationKey.equals(((SmartPopupViewItem) freeformContainerItem).mNotificationKey)) {
                        z = true;
                        break;
                    }
                }
            }
        }
        z = false;
        return !z;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void removeDuplicatedItemsIfExist(FreeformContainerItemController freeformContainerItemController) {
        String str = this.mPackageName;
        FreeformContainerItem itemByName = freeformContainerItemController.getItemByName(str);
        while (itemByName != null) {
            freeformContainerItemController.removeItem(itemByName);
            itemByName = freeformContainerItemController.getItemByName(str);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SmartPopupViewItem {mPackageName=");
        sb.append(this.mPackageName);
        sb.append(", mNotification=");
        sb.append(this.mNotification);
        sb.append(", mNotificationKey= ");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.mNotificationKey, "}");
    }
}
