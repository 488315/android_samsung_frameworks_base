package com.android.internal.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.media.MediaRouter;
import android.util.Log;
import android.view.View;

/* loaded from: classes5.dex */
public abstract class MediaRouteDialogPresenter {
    private static final String CHOOSER_FRAGMENT_TAG = "android.app.MediaRouteButton:MediaRouteChooserDialogFragment";
    private static final String CONTROLLER_FRAGMENT_TAG = "android.app.MediaRouteButton:MediaRouteControllerDialogFragment";
    private static final String TAG = "MediaRouter";

    public static DialogFragment showDialogFragment(Activity activity, int i, View.OnClickListener onClickListener) {
        MediaRouter mediaRouter = (MediaRouter) activity.getSystemService(Context.MEDIA_ROUTER_SERVICE);
        FragmentManager fragmentManager = activity.getFragmentManager();
        MediaRouter.RouteInfo selectedRoute = mediaRouter.getSelectedRoute();
        if (selectedRoute.isDefault() || !selectedRoute.matchesTypes(i)) {
            if (fragmentManager.findFragmentByTag(CHOOSER_FRAGMENT_TAG) != null) {
                Log.w(TAG, "showDialog(): Route chooser dialog already showing!");
                return null;
            }
            MediaRouteChooserDialogFragment mediaRouteChooserDialogFragment = new MediaRouteChooserDialogFragment();
            mediaRouteChooserDialogFragment.setRouteTypes(i);
            mediaRouteChooserDialogFragment.setExtendedSettingsClickListener(onClickListener);
            mediaRouteChooserDialogFragment.show(fragmentManager, CHOOSER_FRAGMENT_TAG);
            return mediaRouteChooserDialogFragment;
        }
        if (fragmentManager.findFragmentByTag(CONTROLLER_FRAGMENT_TAG) != null) {
            Log.w(TAG, "showDialog(): Route controller dialog already showing!");
            return null;
        }
        MediaRouteControllerDialogFragment mediaRouteControllerDialogFragment = new MediaRouteControllerDialogFragment();
        mediaRouteControllerDialogFragment.show(fragmentManager, CONTROLLER_FRAGMENT_TAG);
        return mediaRouteControllerDialogFragment;
    }

    public static Dialog createDialog(Context context, int i, View.OnClickListener onClickListener) {
        return createDialog(context, i, onClickListener, MediaRouteChooserDialog.isLightTheme(context) ? 16974130 : 16974126);
    }

    public static Dialog createDialog(Context context, int i, View.OnClickListener onClickListener, int i2) {
        return createDialog(context, i, onClickListener, i2, true);
    }

    public static Dialog createDialog(Context context, int i, View.OnClickListener onClickListener, int i2, boolean z) {
        if (shouldShowChooserDialog(context, i)) {
            MediaRouteChooserDialog mediaRouteChooserDialog = new MediaRouteChooserDialog(context, i2, z);
            mediaRouteChooserDialog.setRouteTypes(i);
            mediaRouteChooserDialog.setExtendedSettingsClickListener(onClickListener);
            return mediaRouteChooserDialog;
        }
        return new MediaRouteControllerDialog(context, i2);
    }

    public static boolean shouldShowChooserDialog(Context context, int i) {
        MediaRouter.RouteInfo selectedRoute = ((MediaRouter) context.getSystemService(MediaRouter.class)).getSelectedRoute();
        return selectedRoute.isDefault() || !selectedRoute.matchesTypes(i);
    }
}
