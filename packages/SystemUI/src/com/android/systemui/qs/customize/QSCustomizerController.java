package com.android.systemui.qs.customize;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.QSHost;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSCustomizerController extends ViewController {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass3 mConfigurationListener;
    public final AnonymousClass2 mKeyguardCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final LightBarController mLightBarController;
    public final AnonymousClass1 mOnMenuItemClickListener;
    public final ScreenLifecycle mScreenLifecycle;
    public final TileAdapter mTileAdapter;
    public final TileQueryHelper mTileQueryHelper;
    public final Toolbar mToolbar;
    public final UiEventLogger mUiEventLogger;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.customize.QSCustomizerController$1] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.qs.customize.QSCustomizerController$3] */
    public QSCustomizerController(QSCustomizer qSCustomizer, TileQueryHelper tileQueryHelper, QSHost qSHost, TileAdapter tileAdapter, ScreenLifecycle screenLifecycle, KeyguardStateController keyguardStateController, LightBarController lightBarController, ConfigurationController configurationController, UiEventLogger uiEventLogger) {
        super(qSCustomizer);
        this.mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() { // from class: com.android.systemui.qs.customize.QSCustomizerController.1
            @Override // android.widget.Toolbar.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() != 1) {
                    return false;
                }
                QSCustomizerController.this.mUiEventLogger.log(QSEditEvent.QS_EDIT_RESET);
                QSCustomizerController qSCustomizerController = QSCustomizerController.this;
                List defaultSpecs = QSHost.getDefaultSpecs(qSCustomizerController.getContext().getResources());
                TileAdapter tileAdapter2 = qSCustomizerController.mTileAdapter;
                tileAdapter2.mHost.changeTilesByUser(tileAdapter2.mCurrentSpecs, defaultSpecs);
                if (((ArrayList) defaultSpecs).equals(tileAdapter2.mCurrentSpecs)) {
                    return false;
                }
                tileAdapter2.mCurrentSpecs = defaultSpecs;
                return false;
            }
        };
        new KeyguardStateController.Callback() { // from class: com.android.systemui.qs.customize.QSCustomizerController.2
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                QSCustomizerController qSCustomizerController = QSCustomizerController.this;
                if (((QSCustomizer) ((ViewController) qSCustomizerController).mView).isAttachedToWindow() && ((KeyguardStateControllerImpl) qSCustomizerController.mKeyguardStateController).mShowing) {
                    ((QSCustomizer) ((ViewController) qSCustomizerController).mView).getClass();
                    qSCustomizerController.hide(true);
                }
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.customize.QSCustomizerController.3
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                QSCustomizerController qSCustomizerController = QSCustomizerController.this;
                ((QSCustomizer) ((ViewController) qSCustomizerController).mView).updateNavBackDrop(configuration, qSCustomizerController.mLightBarController);
                QSCustomizer qSCustomizer2 = (QSCustomizer) ((ViewController) qSCustomizerController).mView;
                qSCustomizer2.updateTransparentViewHeight();
                qSCustomizer2.mRecyclerView.mAdapter.notifyItemChanged(0);
                TileAdapter tileAdapter2 = qSCustomizerController.mTileAdapter;
                tileAdapter2.getClass();
                int integer = tileAdapter2.mContext.getResources().getInteger(R.integer.quick_settings_num_columns);
                if (integer != tileAdapter2.mNumColumns) {
                    tileAdapter2.mNumColumns = integer;
                    RecyclerView.LayoutManager layoutManager = ((QSCustomizer) ((ViewController) qSCustomizerController).mView).mRecyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        ((GridLayoutManager) layoutManager).setSpanCount(tileAdapter2.mNumColumns);
                    }
                }
            }
        };
        this.mTileQueryHelper = tileQueryHelper;
        this.mTileAdapter = tileAdapter;
        this.mScreenLifecycle = screenLifecycle;
        this.mKeyguardStateController = keyguardStateController;
        this.mLightBarController = lightBarController;
        this.mConfigurationController = configurationController;
        this.mUiEventLogger = uiEventLogger;
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (qSCustomizer.mSceneContainerEnabled) {
            qSCustomizer.mSceneContainerEnabled = false;
            qSCustomizer.updateTransparentViewHeight();
            if (qSCustomizer.mSceneContainerEnabled) {
                qSCustomizer.findViewById(R.id.nav_bar_background).setVisibility(8);
            } else {
                qSCustomizer.findViewById(R.id.nav_bar_background).setVisibility(qSCustomizer.mIsShowingNavBackdrop ? 0 : 8);
            }
        }
        this.mToolbar = (Toolbar) ((QSCustomizer) this.mView).findViewById(android.R.id.action_bar_container);
    }

    public final void applyBottomNavBarSizeToRecyclerViewPadding(int i) {
        QSCustomizer qSCustomizer = (QSCustomizer) this.mView;
        RecyclerView recyclerView = qSCustomizer.mRecyclerView;
        recyclerView.setPadding(recyclerView.getPaddingLeft(), qSCustomizer.mRecyclerView.getPaddingTop(), qSCustomizer.mRecyclerView.getPaddingRight(), i);
    }

    public final void hide(boolean z) {
        if (z) {
            int i = this.mScreenLifecycle.mScreenState;
        }
        ((QSCustomizer) this.mView).getClass();
    }

    public final boolean isCustomizing() {
        return ((QSCustomizer) this.mView).isCustomizing();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((QSCustomizer) this.mView).updateNavBackDrop(getResources().getConfiguration(), this.mLightBarController);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mTileQueryHelper.getClass();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_tile_margin_horizontal) / 2;
        TileAdapter tileAdapter = this.mTileAdapter;
        tileAdapter.mMarginDecoration.mHalfMargin = dimensionPixelSize;
        final RecyclerView recyclerView = ((QSCustomizer) this.mView).mRecyclerView;
        recyclerView.setAdapter(tileAdapter);
        tileAdapter.mItemTouchHelper.attachToRecyclerView(recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), tileAdapter.mNumColumns) { // from class: com.android.systemui.qs.customize.QSCustomizerController.4
            @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
            public final void calculateItemDecorationsForChild(Rect rect, View view) {
                if (view instanceof TextView) {
                    return;
                }
                rect.setEmpty();
                QSCustomizerController.this.mTileAdapter.mMarginDecoration.getItemOffsets(rect, view, recyclerView, new RecyclerView.State());
                ((ViewGroup.MarginLayoutParams) ((GridLayoutManager.LayoutParams) view.getLayoutParams())).leftMargin = rect.left;
                ((ViewGroup.MarginLayoutParams) ((GridLayoutManager.LayoutParams) view.getLayoutParams())).rightMargin = rect.right;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public final void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            }
        };
        gridLayoutManager.mSpanSizeLookup = tileAdapter.mSizeLookup;
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(tileAdapter.mDecoration);
        recyclerView.addItemDecoration(tileAdapter.mMarginDecoration);
        this.mToolbar.setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.QSCustomizerController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSCustomizerController.this.hide(true);
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mTileQueryHelper.getClass();
        this.mToolbar.setOnMenuItemClickListener(null);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    public final void saveInstanceState(Bundle bundle) {
        ((QSCustomizer) this.mView).getClass();
        bundle.putBoolean("qs_customizing", ((QSCustomizer) this.mView).isCustomizing());
    }

    public final void setContainerController(QSContainerController qSContainerController) {
        ((QSCustomizer) this.mView).mQsContainerController = qSContainerController;
    }

    public final void setQs(QS qs) {
        ((QSCustomizer) this.mView).mQs = qs;
    }
}
