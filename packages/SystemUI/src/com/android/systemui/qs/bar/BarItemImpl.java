package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.customize.view.CustomizerNoLabelTileView;
import com.android.systemui.qs.tileimpl.CustomizerLargeTileView;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public abstract class BarItemImpl {
    public View mBarRootView;
    public BarController.AnonymousClass4 mCallback;
    public View mClonedBarView;
    public Context mContext;
    public boolean mQsExpanded;
    public final String TAG = getClass().getSimpleName();
    public boolean mListening = true;
    public boolean mShowing = true;
    public boolean mIsOnCollapsedState = false;
    public boolean mIsUnderneathQqs = false;
    public ColoredBGHelper mBGColorHelper = null;
    public final SecQsUiDisplayModeInteractor mSecQsUiDisplayModeInteractor = (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);

    public BarItemImpl(Context context) {
        this.mContext = context;
    }

    public final void createTilesViewAndDistribute(View view, ArrayList arrayList, SecQSPanelResourcePicker secQSPanelResourcePicker, Boolean bool) {
        QSTileView customizerLargeTileView;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
            QSTile.State state = tileRecord.tile.getState();
            if (bool.booleanValue()) {
                customizerLargeTileView = new CustomizerLargeTileView(this.mContext, secQSPanelResourcePicker, this.mContext.getString(R.string.sec_no_bg_tiles).contains(tileRecord.tile.getTileSpec()));
            } else {
                customizerLargeTileView = new CustomizerNoLabelTileView(this.mContext, secQSPanelResourcePicker);
            }
            customizerLargeTileView.onStateChanged(state);
            ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
            if (coloredBGHelper != null) {
                coloredBGHelper.addBarBackground(customizerLargeTileView, false);
            }
            ((ViewGroup) view).addView(customizerLargeTileView);
        }
    }

    public void destroy() {
        this.mCallback = null;
    }

    public int getBarHeight() {
        if (this.mShowing) {
            return this.mBarRootView.getMeasuredHeight();
        }
        return 0;
    }

    public abstract int getBarLayout();

    public int getBarWidthWeight(Context context) {
        return 4;
    }

    public View getClonedBarView() {
        return this.mClonedBarView;
    }

    public void inflateViews(ViewGroup viewGroup) {
        this.mBarRootView = (ViewGroup) LayoutInflater.from(this.mContext).inflate(getBarLayout(), viewGroup, false);
        onFinishInflate();
    }

    public boolean isAvailable() {
        return true;
    }

    public boolean isNeedToEdit() {
        return this instanceof QSMediaPlayerBar;
    }

    public int orignBottomMargin() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.bar_top_margin);
    }

    public void setCallback(BarController.AnonymousClass4 anonymousClass4) {
        this.mCallback = anonymousClass4;
    }

    public void setExpanded(boolean z) {
        this.mQsExpanded = z;
    }

    public void setListening(boolean z) {
        this.mListening = z;
    }

    public void setUnderneathQqs(boolean z) {
        this.mIsUnderneathQqs = z;
    }

    public void showBar(boolean z) {
        if (this.mBarRootView == null) {
            return;
        }
        Log.i(this.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("showBar : ", z));
        this.mShowing = z;
        this.mBarRootView.setVisibility(z ? 0 : 8);
        BarController.AnonymousClass4 anonymousClass4 = this.mCallback;
        if (anonymousClass4 != null) {
            BarController barController = BarController.this;
            BarController.AnonymousClass3 anonymousClass3 = barController.mBarListener;
            if (anonymousClass3 != null) {
                anonymousClass3.val$containerRunner.run();
                anonymousClass3.val$animatorRunner.run();
            }
            barController.updateBarUnderneathQqs();
            boolean z2 = barController.mContext.getResources().getConfiguration().orientation == 2;
            boolean zIsTablet = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
            if (!z2 || zIsTablet) {
                barController.mBarOrderInteractor.updateLastShowingBar();
            }
        }
    }

    public void onConfigChanged(Configuration configuration) {
    }

    public void makeCloneBar() {
    }

    public void onFinishInflate() {
    }

    public void onKnoxPolicyChanged() {
    }

    public void onUiModeChanged() {
    }

    public void removeCloneTileBG() {
    }

    public void updateClonedBar() {
    }

    public void updateHeightMargins() {
    }
}
