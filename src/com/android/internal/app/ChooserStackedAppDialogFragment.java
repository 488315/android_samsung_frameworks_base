package com.android.internal.app;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.app.chooser.MultiDisplayResolveInfo;

/* loaded from: classes5.dex */
public class ChooserStackedAppDialogFragment extends ChooserTargetActionsDialogFragment implements DialogInterface.OnClickListener {
    static final String MULTI_DRI_KEY = "multi_dri_key";
    static final String WHICH_KEY = "which_key";
    private MultiDisplayResolveInfo mMultiDisplayResolveInfo;
    private int mParentWhich;

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment
    protected Drawable getItemIcon(DisplayResolveInfo displayResolveInfo) {
        return null;
    }

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment
    void setStateFromBundle(Bundle bundle) {
        MultiDisplayResolveInfo multiDisplayResolveInfo = (MultiDisplayResolveInfo) bundle.get(MULTI_DRI_KEY);
        this.mMultiDisplayResolveInfo = multiDisplayResolveInfo;
        this.mTargetInfos = multiDisplayResolveInfo.getTargets();
        this.mUserHandle = (UserHandle) bundle.get("user_handle");
        this.mParentWhich = bundle.getInt(WHICH_KEY);
    }

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment, android.app.DialogFragment, android.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(WHICH_KEY, this.mParentWhich);
        bundle.putParcelable(MULTI_DRI_KEY, this.mMultiDisplayResolveInfo);
    }

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment
    protected CharSequence getItemLabel(DisplayResolveInfo displayResolveInfo) {
        return displayResolveInfo.getResolveInfo().loadLabel(getContext().getPackageManager());
    }

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment, android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.mMultiDisplayResolveInfo.setSelected(i);
        ((ChooserActivity) getActivity()).startSelected(this.mParentWhich, false, true);
        dismiss();
    }
}
