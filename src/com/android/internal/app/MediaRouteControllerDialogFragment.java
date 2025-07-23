package com.android.internal.app;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/* loaded from: classes5.dex */
public class MediaRouteControllerDialogFragment extends DialogFragment {
    public MediaRouteControllerDialogFragment() {
        setCancelable(true);
    }

    public MediaRouteControllerDialog onCreateControllerDialog(Context context, Bundle bundle) {
        return new MediaRouteControllerDialog(context, getTheme());
    }

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        return onCreateControllerDialog(getContext(), bundle);
    }
}
