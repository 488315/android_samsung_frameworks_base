package com.android.wm.shell.freeform;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SmartPopupViewItem extends FreeformContainerItem {
    public final Notification mNotification;
    public final String mNotificationKey;

    public SmartPopupViewItem(Context context, String str, Notification notification2, String str2) {
        super(context, str, notification2.contentIntent.getIntent().getComponent(), context.getUserId());
        this.mItemType = 2;
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
        if ((CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY ? !MultiWindowUtils.isInSubDisplay(this.mContext) : true) && !"MESSAGE_KT_TWO_PHONE_OPPOSITE_RECEIVED".equals(this.mNotification.getGroup())) {
            makeBasic.setLaunchWindowingMode(5);
            makeBasic.preserveTaskWindowingMode();
        }
        makeBasic.setLaunchDisplayId(0);
        makeBasic.setPendingIntentBackgroundActivityStartMode(1);
        final PendingIntent pendingIntent = this.mNotification.contentIntent;
        FreeformContainerSystemProxy.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerSystemProxy$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                PendingIntent pendingIntent2 = pendingIntent;
                ActivityOptions activityOptions = makeBasic;
                ExecutorService executorService = FreeformContainerSystemProxy.mExecutor;
                try {
                    pendingIntent2.send(null, 0, null, null, null, null, activityOptions.toBundle());
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
        this.mShowingIcon = freeformContainerIconLoader.getShowingIcon(MultiWindowUtils.getAppIcon(this.mContext, this.mComponentName, this.mUserId, this.mPackageName), this.mNotification.getLargeIcon() != null ? this.mNotification.getLargeIcon().loadDrawable(this.mContext) : null);
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final boolean needLoading(FreeformContainerItemController freeformContainerItemController) {
        freeformContainerItemController.getClass();
        boolean z = false;
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            ArrayList arrayList = new ArrayList(freeformContainerItemController.mItemList);
            int size = arrayList.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                Object obj = arrayList.get(i);
                i++;
                FreeformContainerItem freeformContainerItem = (FreeformContainerItem) obj;
                if (freeformContainerItem instanceof SmartPopupViewItem) {
                    if (this.mNotificationKey.equals(((SmartPopupViewItem) freeformContainerItem).mNotificationKey)) {
                        z = true;
                        break;
                    }
                }
            }
        }
        return !z;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SmartPopupViewItem {mPackageName=");
        sb.append(this.mPackageName);
        sb.append(", mNotification=");
        sb.append(this.mNotification);
        sb.append(", mNotificationKey= ");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.mNotificationKey, "}");
    }
}
