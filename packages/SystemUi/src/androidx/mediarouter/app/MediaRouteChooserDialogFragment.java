package androidx.mediarouter.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;
import androidx.mediarouter.media.MediaRouteSelector;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MediaRouteChooserDialogFragment extends DialogFragment {
    public AppCompatDialog mDialog;
    public MediaRouteSelector mSelector;
    public boolean mUseDynamicGroup = false;

    public MediaRouteChooserDialogFragment() {
        this.mCancelable = true;
        Dialog dialog = super.mDialog;
        if (dialog != null) {
            dialog.setCancelable(true);
        }
    }

    public final void ensureRouteSelector() {
        if (this.mSelector == null) {
            Bundle bundle = this.mArguments;
            if (bundle != null) {
                Bundle bundle2 = bundle.getBundle("selector");
                MediaRouteSelector mediaRouteSelector = null;
                if (bundle2 != null) {
                    mediaRouteSelector = new MediaRouteSelector(bundle2, null);
                } else {
                    MediaRouteSelector mediaRouteSelector2 = MediaRouteSelector.EMPTY;
                }
                this.mSelector = mediaRouteSelector;
            }
            if (this.mSelector == null) {
                this.mSelector = MediaRouteSelector.EMPTY;
            }
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
        AppCompatDialog appCompatDialog = this.mDialog;
        if (appCompatDialog == null) {
            return;
        }
        if (!this.mUseDynamicGroup) {
            MediaRouteChooserDialog mediaRouteChooserDialog = (MediaRouteChooserDialog) appCompatDialog;
            mediaRouteChooserDialog.getWindow().setLayout(MediaRouteDialogHelper.getDialogWidth(mediaRouteChooserDialog.getContext()), -2);
        } else {
            MediaRouteDynamicChooserDialog mediaRouteDynamicChooserDialog = (MediaRouteDynamicChooserDialog) appCompatDialog;
            Context context = mediaRouteDynamicChooserDialog.mContext;
            mediaRouteDynamicChooserDialog.getWindow().setLayout(!context.getResources().getBoolean(R.bool.is_tablet) ? -1 : MediaRouteDialogHelper.getDialogWidth(context), mediaRouteDynamicChooserDialog.mContext.getResources().getBoolean(R.bool.is_tablet) ? -2 : -1);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog() {
        if (this.mUseDynamicGroup) {
            MediaRouteDynamicChooserDialog mediaRouteDynamicChooserDialog = new MediaRouteDynamicChooserDialog(getContext());
            this.mDialog = mediaRouteDynamicChooserDialog;
            ensureRouteSelector();
            mediaRouteDynamicChooserDialog.setRouteSelector(this.mSelector);
        } else {
            MediaRouteChooserDialog mediaRouteChooserDialog = new MediaRouteChooserDialog(getContext());
            this.mDialog = mediaRouteChooserDialog;
            ensureRouteSelector();
            mediaRouteChooserDialog.setRouteSelector(this.mSelector);
        }
        return this.mDialog;
    }
}
