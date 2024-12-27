package com.android.systemui.globalactions.presentation.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.UserHandle;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.viewmodel.ActionInfo;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KnoxCustomActionViewModel implements ActionViewModel {
    public ActionInfo mActionInfo;
    public final Context mContext;
    public final SamsungGlobalActions mGlobalActions;
    public BitmapDrawable mIcon;
    public Intent mIntent;
    public int mIntentAction;
    public String mText;

    public KnoxCustomActionViewModel(Context context, SamsungGlobalActions samsungGlobalActions) {
        this.mContext = context;
        this.mGlobalActions = samsungGlobalActions;
    }

    public final ActionInfo getActionInfo() {
        return this.mActionInfo;
    }

    public final BitmapDrawable getIcon() {
        return this.mIcon;
    }

    public final String getText() {
        return this.mText;
    }

    public final void onPress() {
        int i = this.mIntentAction;
        if (i == 1) {
            this.mContext.sendBroadcast(this.mIntent);
        } else if (i == 2) {
            this.mContext.sendStickyBroadcast(this.mIntent);
        } else if (i == 4) {
            this.mContext.startActivityAsUser(this.mIntent, UserHandle.OWNER);
        }
        this.mGlobalActions.dismissDialog(true);
    }

    public final void setActionInfo(ActionInfo actionInfo) {
        this.mActionInfo = actionInfo;
    }

    public final void setIcon(BitmapDrawable bitmapDrawable) {
        this.mIcon = bitmapDrawable;
    }

    public final void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public final void setIntentAction(int i) {
        this.mIntentAction = i;
    }

    public final void setText(String str) {
        this.mText = str;
    }

    public final boolean showBeforeProvisioning() {
        return true;
    }
}
