package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.SecSTQuickControlRequestReceiver;
import com.android.systemui.util.ViewUtil;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QuickControlBar extends BarItemImpl {
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
        return (QpRune.QUICK_TABLET || context.getResources().getConfiguration().orientation != 2) ? 4 : 2;
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
            FrameLayout frameLayout = this.mRemoteViews;
            final int i = 0;
            Supplier supplier = new Supplier(this) { // from class: com.android.systemui.qs.bar.QuickControlBar$$ExternalSyntheticLambda0
                public final /* synthetic */ QuickControlBar f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.Supplier
                public final Object get() {
                    int i2 = i;
                    QuickControlBar quickControlBar = this.f$0;
                    switch (i2) {
                        case 0:
                            return Boolean.valueOf(quickControlBar.mShowing);
                        default:
                            return Boolean.valueOf(quickControlBar.mQsExpanded);
                    }
                }
            };
            final int i2 = 0;
            Runnable runnable = new Runnable(this) { // from class: com.android.systemui.qs.bar.QuickControlBar$$ExternalSyntheticLambda1
                public final /* synthetic */ QuickControlBar f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i3 = i2;
                    QuickControlBar quickControlBar = this.f$0;
                    switch (i3) {
                        case 0:
                            quickControlBar.showBar(false);
                            break;
                        default:
                            quickControlBar.showBar(true);
                            break;
                    }
                }
            };
            final int i3 = 1;
            Runnable runnable2 = new Runnable(this) { // from class: com.android.systemui.qs.bar.QuickControlBar$$ExternalSyntheticLambda1
                public final /* synthetic */ QuickControlBar f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i32 = i3;
                    QuickControlBar quickControlBar = this.f$0;
                    switch (i32) {
                        case 0:
                            quickControlBar.showBar(false);
                            break;
                        default:
                            quickControlBar.showBar(true);
                            break;
                    }
                }
            };
            final int i4 = 1;
            Supplier supplier2 = new Supplier(this) { // from class: com.android.systemui.qs.bar.QuickControlBar$$ExternalSyntheticLambda0
                public final /* synthetic */ QuickControlBar f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.Supplier
                public final Object get() {
                    int i22 = i4;
                    QuickControlBar quickControlBar = this.f$0;
                    switch (i22) {
                        case 0:
                            return Boolean.valueOf(quickControlBar.mShowing);
                        default:
                            return Boolean.valueOf(quickControlBar.mQsExpanded);
                    }
                }
            };
            SecSTQuickControlRequestReceiver secSTQuickControlRequestReceiver = this.mSecSTQuickControlRequestReceiver;
            secSTQuickControlRequestReceiver.getClass();
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("init: registered, this = ", ViewUtil.INSTANCE.toShortIdSting(secSTQuickControlRequestReceiver), "SecSTQuickControlRequestReceiver");
            secSTQuickControlRequestReceiver.isShowingSupplier = supplier;
            secSTQuickControlRequestReceiver.hideBarRunnable = runnable;
            secSTQuickControlRequestReceiver.showBarRunnable = runnable2;
            secSTQuickControlRequestReceiver.expandedSupplier = supplier2;
            secSTQuickControlRequestReceiver.remoteViewsContainer = frameLayout;
            BroadcastDispatcher.registerReceiverWithHandler$default((BroadcastDispatcher) secSTQuickControlRequestReceiver.broadcastDispatcher$delegate.getValue(), secSTQuickControlRequestReceiver, secSTQuickControlRequestReceiver.filter, (Handler) secSTQuickControlRequestReceiver.handler$delegate.getValue(), null, "com.android.systemui.qs.permission.ST_QUICK_CONTROL", 24);
            if (secSTQuickControlRequestReceiver.newRemoteView != null) {
                runnable2.run();
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
    public final void setExpanded(boolean z) {
        if (z && !this.mQsExpanded) {
            this.mSecSTQuickControlRequestReceiver.updateRemoteView(this.mContext);
        }
        this.mQsExpanded = z;
    }

    public final void updateBarLayout() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBarRootView.getLayoutParams();
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_height);
        if (QpRune.QUICK_TABLET || this.mCurrentOrientation != 2) {
            layoutParams.width = -1;
            layoutParams.setMarginEnd(0);
        } else {
            layoutParams.weight = 2.0f;
            layoutParams.width = 0;
        }
        this.mBarRootView.setLayoutParams(layoutParams);
    }
}
