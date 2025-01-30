package com.android.settingslib.widget;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ActionBarShadowController implements LifecycleObserver {
    static final float ELEVATION_HIGH = 8.0f;
    static final float ELEVATION_LOW = 0.0f;
    public boolean mIsScrollWatcherAttached;
    ScrollChangeWatcher mScrollChangeWatcher;
    public final View mScrollView;

    private ActionBarShadowController(Activity activity, Lifecycle lifecycle, View view) {
        this.mScrollChangeWatcher = new ScrollChangeWatcher(this, activity);
        this.mScrollView = view;
        attachScrollWatcher();
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void attachScrollWatcher() {
        if (this.mIsScrollWatcherAttached) {
            return;
        }
        this.mIsScrollWatcherAttached = true;
        ScrollChangeWatcher scrollChangeWatcher = this.mScrollChangeWatcher;
        View view = this.mScrollView;
        view.setOnScrollChangeListener(scrollChangeWatcher);
        this.mScrollChangeWatcher.updateDropShadow(view);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void detachScrollWatcher() {
        this.mScrollView.setOnScrollChangeListener(null);
        this.mIsScrollWatcherAttached = false;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScrollChangeWatcher implements View.OnScrollChangeListener {
        public final Activity mActivity;
        public final View mAnchorView;

        public ScrollChangeWatcher(ActionBarShadowController actionBarShadowController, Activity activity) {
            this.mActivity = activity;
            this.mAnchorView = null;
        }

        @Override // android.view.View.OnScrollChangeListener
        public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
            updateDropShadow(view);
        }

        public final void updateDropShadow(View view) {
            ActionBar actionBar;
            boolean canScrollVertically = view.canScrollVertically(-1);
            View view2 = this.mAnchorView;
            float f = ActionBarShadowController.ELEVATION_HIGH;
            if (view2 != null) {
                if (!canScrollVertically) {
                    f = 0.0f;
                }
                view2.setElevation(f);
                return;
            }
            Activity activity = this.mActivity;
            if (activity == null || (actionBar = activity.getActionBar()) == null) {
                return;
            }
            if (!canScrollVertically) {
                f = 0.0f;
            }
            actionBar.setElevation(f);
        }

        public ScrollChangeWatcher(ActionBarShadowController actionBarShadowController, View view) {
            this.mAnchorView = view;
            this.mActivity = null;
        }
    }

    private ActionBarShadowController(View view, Lifecycle lifecycle, View view2) {
        this.mScrollChangeWatcher = new ScrollChangeWatcher(this, view);
        this.mScrollView = view2;
        attachScrollWatcher();
        lifecycle.addObserver(this);
    }
}
