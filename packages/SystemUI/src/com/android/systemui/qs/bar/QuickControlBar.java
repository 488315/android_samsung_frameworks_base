package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.SecSTQuickControlRequestReceiver;
import com.android.systemui.util.ViewUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class QuickControlBar extends BarItemImpl {
    public int mCurrentOrientation;
    public FrameLayout mRemoteViews;
    public final SecSTQuickControlRequestReceiver mSecSTQuickControlRequestReceiver;

    public QuickControlBar(Context context) {
        super(context);
        this.mSecSTQuickControlRequestReceiver = (SecSTQuickControlRequestReceiver) Dependency.sDependency.getDependencyInner(SecSTQuickControlRequestReceiver.class);
        this.mContext = context;
        this.mCurrentOrientation = context.getResources().getConfiguration().orientation;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        SecSTQuickControlRequestReceiver secSTQuickControlRequestReceiver = this.mSecSTQuickControlRequestReceiver;
        secSTQuickControlRequestReceiver.getClass();
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("fini: unregistered, this = ", ViewUtil.INSTANCE.toShortIdSting(secSTQuickControlRequestReceiver), "SecSTQuickControlRequestReceiver");
        ((BroadcastDispatcher) secSTQuickControlRequestReceiver.broadcastDispatcher$delegate.getValue()).unregisterReceiver(secSTQuickControlRequestReceiver);
        FrameLayout frameLayout = secSTQuickControlRequestReceiver.remoteViewsContainer;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        secSTQuickControlRequestReceiver.currentRemoteView = null;
        secSTQuickControlRequestReceiver.hideBarRunnable = null;
        secSTQuickControlRequestReceiver.showBarRunnable = null;
        secSTQuickControlRequestReceiver.expandedSupplier = null;
        secSTQuickControlRequestReceiver.isShowingSupplier = null;
        secSTQuickControlRequestReceiver.remoteViewsContainer = null;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_height);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.qs_quick_control_bar;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarWidthWeight(Context context) {
        return (this.mSecQsUiDisplayModeInteractor.isTablet() || context.getResources().getConfiguration().orientation != 2) ? 4 : 2;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        if (this.mBarRootView == null) {
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_quick_control_bar, viewGroup, false);
            this.mBarRootView = inflate;
            this.mRemoteViews = (FrameLayout) inflate.findViewById(R.id.quick_control_container);
            ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
            if (coloredBGHelper != null) {
                coloredBGHelper.addBarBackground(this.mBarRootView, false);
            }
            showBar(false);
            Context context = this.mContext;
            View view = this.mBarRootView;
            FrameLayout frameLayout = this.mRemoteViews;
            QuickControlBar$$ExternalSyntheticLambda0 quickControlBar$$ExternalSyntheticLambda0 = new QuickControlBar$$ExternalSyntheticLambda0(this, 0);
            QuickControlBar$$ExternalSyntheticLambda1 quickControlBar$$ExternalSyntheticLambda1 = new QuickControlBar$$ExternalSyntheticLambda1(this, 0);
            QuickControlBar$$ExternalSyntheticLambda1 quickControlBar$$ExternalSyntheticLambda12 = new QuickControlBar$$ExternalSyntheticLambda1(this, 1);
            QuickControlBar$$ExternalSyntheticLambda0 quickControlBar$$ExternalSyntheticLambda02 = new QuickControlBar$$ExternalSyntheticLambda0(this, 1);
            SecSTQuickControlRequestReceiver secSTQuickControlRequestReceiver = this.mSecSTQuickControlRequestReceiver;
            secSTQuickControlRequestReceiver.getClass();
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("init: registered, this = ", ViewUtil.INSTANCE.toShortIdSting(secSTQuickControlRequestReceiver), "SecSTQuickControlRequestReceiver");
            secSTQuickControlRequestReceiver.isShowingSupplier = quickControlBar$$ExternalSyntheticLambda0;
            secSTQuickControlRequestReceiver.hideBarRunnable = quickControlBar$$ExternalSyntheticLambda1;
            secSTQuickControlRequestReceiver.showBarRunnable = quickControlBar$$ExternalSyntheticLambda12;
            secSTQuickControlRequestReceiver.expandedSupplier = quickControlBar$$ExternalSyntheticLambda02;
            secSTQuickControlRequestReceiver.rootView = view;
            secSTQuickControlRequestReceiver.remoteViewsContainer = frameLayout;
            BroadcastDispatcher.registerReceiverWithHandler$default((BroadcastDispatcher) secSTQuickControlRequestReceiver.broadcastDispatcher$delegate.getValue(), secSTQuickControlRequestReceiver, secSTQuickControlRequestReceiver.filter, secSTQuickControlRequestReceiver.handler, null, "com.android.systemui.qs.permission.ST_QUICK_CONTROL", 24);
            if (secSTQuickControlRequestReceiver.newRemoteView != null) {
                quickControlBar$$ExternalSyntheticLambda12.run();
                secSTQuickControlRequestReceiver.updateRemoteView(context);
            }
            updateBarLayout();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_quick_control_bar, (ViewGroup) null);
        this.mClonedBarView = inflate;
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper != null) {
            coloredBGHelper.addBarBackground(inflate, false);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mClonedBarView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LinearLayout.LayoutParams(-1, -1);
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_height);
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = -1;
        this.mClonedBarView.setLayoutParams(layoutParams);
        if (this.mBarRootView.getMeasuredWidth() > 0) {
            Bitmap createBitmap = Bitmap.createBitmap(this.mBarRootView.getMeasuredWidth(), dimensionPixelSize, Bitmap.Config.ARGB_8888);
            this.mRemoteViews.draw(new Canvas(createBitmap));
            ImageView imageView = new ImageView(this.mContext);
            imageView.setImageBitmap(createBitmap);
            ((FrameLayout) this.mClonedBarView.findViewById(R.id.quick_control_container)).addView(imageView);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        Log.d("QuickControlBar", "onConfigChanged " + this.mContext.getResources().getConfiguration() + " > " + configuration);
        int i = this.mCurrentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mCurrentOrientation = i2;
            if (this.mBarRootView == null) {
                return;
            }
            updateBarLayout();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void removeCloneTileBG() {
        View view;
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper == null || (view = this.mClonedBarView) == null) {
            return;
        }
        coloredBGHelper.removeFromBarBackground(view);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setExpanded(boolean z) {
        if (z && !this.mQsExpanded) {
            this.mSecSTQuickControlRequestReceiver.updateRemoteView(this.mContext);
        }
        this.mQsExpanded = z;
    }

    public final void updateBarLayout() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBarRootView.getLayoutParams();
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_height);
        if (this.mSecQsUiDisplayModeInteractor.isTablet() || this.mCurrentOrientation != 2) {
            layoutParams.width = -1;
            layoutParams.setMarginEnd(0);
        } else {
            layoutParams.weight = 2.0f;
            layoutParams.width = 0;
        }
        this.mBarRootView.setLayoutParams(layoutParams);
    }
}
