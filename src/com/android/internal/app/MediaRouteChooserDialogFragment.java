package com.android.internal.app;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/* loaded from: classes5.dex */
public class MediaRouteChooserDialogFragment extends DialogFragment {
    private final String ARGUMENT_ROUTE_TYPES = "routeTypes";
    private View.OnClickListener mExtendedSettingsClickListener;

    public MediaRouteChooserDialogFragment() {
        int i = MediaRouteChooserDialog.isLightTheme(getContext()) ? 16974130 : 16974126;
        setCancelable(true);
        setStyle(0, i);
    }

    public int getRouteTypes() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getInt("routeTypes");
        }
        return 0;
    }

    public void setRouteTypes(int i) {
        if (i != getRouteTypes()) {
            Bundle arguments = getArguments();
            if (arguments == null) {
                arguments = new Bundle();
            }
            arguments.putInt("routeTypes", i);
            setArguments(arguments);
            MediaRouteChooserDialog mediaRouteChooserDialog = (MediaRouteChooserDialog) getDialog();
            if (mediaRouteChooserDialog != null) {
                mediaRouteChooserDialog.setRouteTypes(i);
            }
        }
    }

    public void setExtendedSettingsClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != this.mExtendedSettingsClickListener) {
            this.mExtendedSettingsClickListener = onClickListener;
            MediaRouteChooserDialog mediaRouteChooserDialog = (MediaRouteChooserDialog) getDialog();
            if (mediaRouteChooserDialog != null) {
                mediaRouteChooserDialog.setExtendedSettingsClickListener(onClickListener);
            }
        }
    }

    public MediaRouteChooserDialog onCreateChooserDialog(Context context, Bundle bundle) {
        return new MediaRouteChooserDialog(context, getTheme());
    }

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        MediaRouteChooserDialog onCreateChooserDialog = onCreateChooserDialog(getActivity(), bundle);
        onCreateChooserDialog.setRouteTypes(getRouteTypes());
        onCreateChooserDialog.setExtendedSettingsClickListener(this.mExtendedSettingsClickListener);
        return onCreateChooserDialog;
    }
}
