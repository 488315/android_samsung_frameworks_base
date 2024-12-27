package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.customize.view.CustomizerNoLabelTileView;
import com.android.systemui.qs.tileimpl.CustomizerLargeTileView;
import java.util.ArrayList;
import java.util.Iterator;

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

    public BarItemImpl(Context context) {
        this.mContext = context;
    }

    public final void createTilesViewAndDistribute(View view, ArrayList arrayList, SecQSPanelResourcePicker secQSPanelResourcePicker, Boolean bool, QSHost qSHost) {
        QSTileView customizerLargeTileView;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) it.next();
            QSTile.State state = tileRecord.tile.getState();
            if (bool.booleanValue()) {
                customizerLargeTileView = new CustomizerLargeTileView(this.mContext, secQSPanelResourcePicker, qSHost != null ? qSHost.isNoBgLargeTile(tileRecord.tile.getTileSpec()) : false);
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
        Log.i(this.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("showBar : ", z));
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
            if (barController.mContext.getResources().getConfiguration().orientation != 2) {
                barController.mBarOrderInteractor.updateLastShowingBar();
            }
        }
    }

    public void makeCloneBar() {
    }

    public void onFinishInflate() {
    }

    public void onKnoxPolicyChanged() {
    }

    public void onUiModeChanged() {
    }

    public void updateClonedBar() {
    }

    public void updateHeightMargins() {
    }

    public void onConfigChanged(Configuration configuration) {
    }
}
