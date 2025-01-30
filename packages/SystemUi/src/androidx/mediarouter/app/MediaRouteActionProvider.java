package androidx.mediarouter.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.core.view.ActionProvider;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MediaRouteActionProvider extends ActionProvider {
    public MediaRouteButton mButton;
    public final MediaRouteDialogFactory mDialogFactory;
    public final MediaRouter mRouter;
    public final MediaRouteSelector mSelector;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaRouterCallback extends MediaRouter.Callback {
        public final WeakReference mProviderWeak;

        public MediaRouterCallback(MediaRouteActionProvider mediaRouteActionProvider) {
            this.mProviderWeak = new WeakReference(mediaRouteActionProvider);
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onProviderAdded(MediaRouter mediaRouter) {
            refreshRoute(mediaRouter);
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onProviderChanged(MediaRouter mediaRouter) {
            refreshRoute(mediaRouter);
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onProviderRemoved(MediaRouter mediaRouter) {
            refreshRoute(mediaRouter);
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteAdded(MediaRouter mediaRouter) {
            refreshRoute(mediaRouter);
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            refreshRoute(mediaRouter);
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteRemoved(MediaRouter mediaRouter) {
            refreshRoute(mediaRouter);
        }

        public final void refreshRoute(MediaRouter mediaRouter) {
            MediaRouteActionProvider mediaRouteActionProvider = (MediaRouteActionProvider) this.mProviderWeak.get();
            if (mediaRouteActionProvider == null) {
                mediaRouter.removeCallback(this);
                return;
            }
            MenuItemImpl.C00441 c00441 = mediaRouteActionProvider.mVisibilityListener;
            if (c00441 != null) {
                mediaRouteActionProvider.isVisible();
                MenuBuilder menuBuilder = MenuItemImpl.this.mMenu;
                menuBuilder.mIsVisibleItemsStale = true;
                menuBuilder.onItemsChanged(true);
            }
        }
    }

    public MediaRouteActionProvider(Context context) {
        super(context);
        this.mSelector = MediaRouteSelector.EMPTY;
        this.mDialogFactory = MediaRouteDialogFactory.sDefault;
        this.mRouter = MediaRouter.getInstance(context);
        new MediaRouterCallback(this);
    }

    @Override // androidx.core.view.ActionProvider
    public final boolean isVisible() {
        this.mRouter.getClass();
        return MediaRouter.isRouteAvailable(this.mSelector);
    }

    @Override // androidx.core.view.ActionProvider
    public final View onCreateActionView() {
        if (this.mButton != null) {
            Log.e("MRActionProvider", "onCreateActionView: this ActionProvider is already associated with a menu item. Don't reuse MediaRouteActionProvider instances! Abandoning the old menu item...");
        }
        MediaRouteButton mediaRouteButton = new MediaRouteButton(this.mContext);
        this.mButton = mediaRouteButton;
        if (true != mediaRouteButton.mCheatSheetEnabled) {
            mediaRouteButton.mCheatSheetEnabled = true;
            mediaRouteButton.updateContentDescription();
        }
        MediaRouteButton mediaRouteButton2 = this.mButton;
        MediaRouteSelector mediaRouteSelector = this.mSelector;
        if (mediaRouteSelector == null) {
            mediaRouteButton2.getClass();
            throw new IllegalArgumentException("selector must not be null");
        }
        if (!mediaRouteButton2.mSelector.equals(mediaRouteSelector)) {
            if (mediaRouteButton2.mAttachedToWindow) {
                if (!mediaRouteButton2.mSelector.isEmpty()) {
                    mediaRouteButton2.mRouter.removeCallback(mediaRouteButton2.mCallback);
                }
                if (!mediaRouteSelector.isEmpty()) {
                    mediaRouteButton2.mRouter.addCallback(mediaRouteSelector, mediaRouteButton2.mCallback, 0);
                }
            }
            mediaRouteButton2.mSelector = mediaRouteSelector;
            mediaRouteButton2.refreshRoute();
        }
        MediaRouteButton mediaRouteButton3 = this.mButton;
        if (mediaRouteButton3.mAlwaysVisible) {
            mediaRouteButton3.mAlwaysVisible = false;
            mediaRouteButton3.refreshVisibility();
            mediaRouteButton3.refreshRoute();
        }
        MediaRouteButton mediaRouteButton4 = this.mButton;
        MediaRouteDialogFactory mediaRouteDialogFactory = this.mDialogFactory;
        if (mediaRouteDialogFactory == null) {
            mediaRouteButton4.getClass();
            throw new IllegalArgumentException("factory must not be null");
        }
        mediaRouteButton4.mDialogFactory = mediaRouteDialogFactory;
        mediaRouteButton4.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
        return this.mButton;
    }

    @Override // androidx.core.view.ActionProvider
    public final boolean onPerformDefaultAction() {
        MediaRouteSelector mediaRouteSelector;
        Activity activity;
        MediaRouteButton mediaRouteButton = this.mButton;
        if (mediaRouteButton == null || !mediaRouteButton.mAttachedToWindow) {
            return false;
        }
        mediaRouteButton.mRouter.getClass();
        MediaRouter.checkCallingThread();
        MediaRouter.getGlobalRouter();
        Context context = mediaRouteButton.getContext();
        while (true) {
            mediaRouteSelector = null;
            if (!(context instanceof ContextWrapper)) {
                activity = null;
                break;
            }
            if (context instanceof Activity) {
                activity = (Activity) context;
                break;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        FragmentManagerImpl supportFragmentManager = activity instanceof FragmentActivity ? ((FragmentActivity) activity).getSupportFragmentManager() : null;
        if (supportFragmentManager == null) {
            throw new IllegalStateException("The activity must be a subclass of FragmentActivity");
        }
        mediaRouteButton.mRouter.getClass();
        if (MediaRouter.getSelectedRoute().isDefaultOrBluetooth()) {
            if (supportFragmentManager.findFragmentByTag("android.support.v7.mediarouter:MediaRouteChooserDialogFragment") != null) {
                Log.w("MediaRouteButton", "showDialog(): Route chooser dialog already showing!");
                return false;
            }
            mediaRouteButton.mDialogFactory.getClass();
            MediaRouteChooserDialogFragment mediaRouteChooserDialogFragment = new MediaRouteChooserDialogFragment();
            MediaRouteSelector mediaRouteSelector2 = mediaRouteButton.mSelector;
            if (mediaRouteSelector2 == null) {
                throw new IllegalArgumentException("selector must not be null");
            }
            mediaRouteChooserDialogFragment.ensureRouteSelector();
            if (!mediaRouteChooserDialogFragment.mSelector.equals(mediaRouteSelector2)) {
                mediaRouteChooserDialogFragment.mSelector = mediaRouteSelector2;
                Bundle bundle = mediaRouteChooserDialogFragment.mArguments;
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putBundle("selector", mediaRouteSelector2.mBundle);
                mediaRouteChooserDialogFragment.setArguments(bundle);
                AppCompatDialog appCompatDialog = mediaRouteChooserDialogFragment.mDialog;
                if (appCompatDialog != null) {
                    if (mediaRouteChooserDialogFragment.mUseDynamicGroup) {
                        ((MediaRouteDynamicChooserDialog) appCompatDialog).setRouteSelector(mediaRouteSelector2);
                    } else {
                        ((MediaRouteChooserDialog) appCompatDialog).setRouteSelector(mediaRouteSelector2);
                    }
                }
            }
            BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
            backStackRecord.doAddOp(0, mediaRouteChooserDialogFragment, "android.support.v7.mediarouter:MediaRouteChooserDialogFragment", 1);
            backStackRecord.commitAllowingStateLoss();
        } else {
            if (supportFragmentManager.findFragmentByTag("android.support.v7.mediarouter:MediaRouteControllerDialogFragment") != null) {
                Log.w("MediaRouteButton", "showDialog(): Route controller dialog already showing!");
                return false;
            }
            mediaRouteButton.mDialogFactory.getClass();
            MediaRouteControllerDialogFragment mediaRouteControllerDialogFragment = new MediaRouteControllerDialogFragment();
            MediaRouteSelector mediaRouteSelector3 = mediaRouteButton.mSelector;
            if (mediaRouteSelector3 == null) {
                throw new IllegalArgumentException("selector must not be null");
            }
            if (mediaRouteControllerDialogFragment.mSelector == null) {
                Bundle bundle2 = mediaRouteControllerDialogFragment.mArguments;
                if (bundle2 != null) {
                    Bundle bundle3 = bundle2.getBundle("selector");
                    if (bundle3 != null) {
                        mediaRouteSelector = new MediaRouteSelector(bundle3, null);
                    } else {
                        MediaRouteSelector mediaRouteSelector4 = MediaRouteSelector.EMPTY;
                    }
                    mediaRouteControllerDialogFragment.mSelector = mediaRouteSelector;
                }
                if (mediaRouteControllerDialogFragment.mSelector == null) {
                    mediaRouteControllerDialogFragment.mSelector = MediaRouteSelector.EMPTY;
                }
            }
            if (!mediaRouteControllerDialogFragment.mSelector.equals(mediaRouteSelector3)) {
                mediaRouteControllerDialogFragment.mSelector = mediaRouteSelector3;
                Bundle bundle4 = mediaRouteControllerDialogFragment.mArguments;
                if (bundle4 == null) {
                    bundle4 = new Bundle();
                }
                bundle4.putBundle("selector", mediaRouteSelector3.mBundle);
                mediaRouteControllerDialogFragment.setArguments(bundle4);
                AppCompatDialog appCompatDialog2 = mediaRouteControllerDialogFragment.mDialog;
                if (appCompatDialog2 != null && mediaRouteControllerDialogFragment.mUseDynamicGroup) {
                    ((MediaRouteDynamicControllerDialog) appCompatDialog2).setRouteSelector(mediaRouteSelector3);
                }
            }
            BackStackRecord backStackRecord2 = new BackStackRecord(supportFragmentManager);
            backStackRecord2.doAddOp(0, mediaRouteControllerDialogFragment, "android.support.v7.mediarouter:MediaRouteControllerDialogFragment", 1);
            backStackRecord2.commitAllowingStateLoss();
        }
        return true;
    }
}
