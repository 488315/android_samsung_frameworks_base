package com.android.systemui.qs;

import android.content.res.Configuration;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class SecQSPanelControllerBase extends ViewController implements Dumpable {
    public Runnable mCollapseExpandAction;
    public final SecQSDetailController mDetailController;
    public final DumpManager mDumpManager;
    public boolean mExpandSettingsPanel;
    public boolean mExpanded;
    public final QSHost mHost;
    public final ConfigurationState mLastConfigurationState;
    public boolean mListening;
    public final MediaHost mMediaHost;
    public final MetricsLogger mMetricsLogger;
    protected final SecQSPanel.OnConfigurationChangedListener mOnConfigurationChangedListener;
    public int mOrientation;
    public final QSLogger mQSLogger;
    public final QSPanelHost mQsPanelHost;
    public final SecQSPanelResourcePicker mResourcePicker;
    public SecQSPanel.QSTileLayout mTileLayout;
    public final UiEventLogger mUiEventLogger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class Record {
        public DetailAdapter mDetailAdapter;
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TileRecord extends Record {
        public SQSTile.SCallback callback;
        public final QSTile tile;
        public final QSTileView tileView;

        public TileRecord(QSTile qSTile, QSTileView qSTileView) {
            this.tile = qSTile;
            this.tileView = qSTileView;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("TileRecord{");
            CharSequence tileLabel = this.tile.getTileLabel();
            sb.append((tileLabel == null || tileLabel.isEmpty()) ? "" : tileLabel.toString().replace("\n", " "));
            sb.append(", view = ");
            sb.append(this.tileView);
            sb.append("}");
            return sb.toString();
        }
    }

    public static void $r8$lambda$1i4lGWbt4JPhc9SgoAITwPQZoew(SecQSPanelControllerBase secQSPanelControllerBase, BarItemImpl barItemImpl) {
        secQSPanelControllerBase.getClass();
        if (barItemImpl.mBarRootView == null) {
            barItemImpl.inflateViews((ViewGroup) secQSPanelControllerBase.mView);
        }
        QSPanelHost qSPanelHost = secQSPanelControllerBase.mQsPanelHost;
        if (qSPanelHost.isHeader()) {
            secQSPanelControllerBase.switchToParent(barItemImpl.mBarRootView, (ViewGroup) secQSPanelControllerBase.mView);
        }
        if (barItemImpl instanceof TileChunkLayoutBar) {
            TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) barItemImpl;
            SecTileChunkLayout secTileChunkLayout = (SecTileChunkLayout) secQSPanelControllerBase.mTileLayout;
            ViewGroup viewGroup = tileChunkLayoutBar.mTileLayoutContainer;
            if (viewGroup != null) {
                tileChunkLayoutBar.mTileLayout = secTileChunkLayout;
                viewGroup.removeView(secTileChunkLayout);
                tileChunkLayoutBar.mTileLayoutContainer.addView(tileChunkLayoutBar.mTileLayout, 0);
            }
            tileChunkLayoutBar.mPanelHost = qSPanelHost;
            qSPanelHost.mCallbacks.add(tileChunkLayoutBar.mTilesChangedCallback);
        }
    }

    /* renamed from: $r8$lambda$DEp4l-o7MDBmfDD2MwoTLB3AMIw, reason: not valid java name */
    public static void m2063$r8$lambda$DEp4lo7MDBmfDD2MwoTLB3AMIw(SecQSPanelControllerBase secQSPanelControllerBase, final BarItemImpl barItemImpl) {
        secQSPanelControllerBase.getClass();
        if (barItemImpl instanceof TileChunkLayoutBar) {
            ((SecQSPanel) secQSPanelControllerBase.mView).getHandler().postDelayed(new Runnable() { // from class: com.android.systemui.qs.SecQSPanelControllerBase$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) BarItemImpl.this;
                    tileChunkLayoutBar.setContainerHeight(tileChunkLayoutBar.mContainerExpandedHeight);
                }
            }, 100L);
        }
    }

    public SecQSPanelControllerBase(SecQSPanel secQSPanel, QSHost qSHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager, QSPanelHost qSPanelHost, BarController barController, SecQSPanelResourcePicker secQSPanelResourcePicker, MediaHost mediaHost, final SecQSDetailController secQSDetailController) {
        super(secQSPanel);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ASSET_SEQ, ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.THEME_SEQ, ConfigurationState.ConfigurationField.UI_MODE));
        this.mOnConfigurationChangedListener = new SecQSPanel.OnConfigurationChangedListener() { // from class: com.android.systemui.qs.SecQSPanelControllerBase$$ExternalSyntheticLambda0
            @Override // com.android.systemui.qs.SecQSPanel.OnConfigurationChangedListener
            public final void onConfigurationChange(Configuration configuration) {
                SecQSPanelControllerBase secQSPanelControllerBase = SecQSPanelControllerBase.this;
                secQSPanelControllerBase.getClass();
                Log.d("SecQSPanelControllerBase", "OnConfigurationChangedListener ,onConfigurationChange event");
                secQSPanelControllerBase.onConfigurationChanged(configuration);
            }
        };
        this.mExpandSettingsPanel = false;
        this.mHost = qSHost;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mQSLogger = qSLogger;
        this.mDumpManager = dumpManager;
        this.mQsPanelHost = qSPanelHost;
        Objects.requireNonNull(secQSDetailController);
        qSPanelHost.mTileCallbackFunction = new Function() { // from class: com.android.systemui.qs.SecQSPanelControllerBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                final SecQSDetailController secQSDetailController2 = SecQSDetailController.this;
                final SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                secQSDetailController2.getClass();
                return new SQSTile.SCallback() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1
                    public final boolean needToSkip() {
                        return !Intrinsics.areEqual(secQSDetailController2.currentRecord, SecQSPanelControllerBase.TileRecord.this);
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onScanStateChanged(final boolean z) {
                        SecQSPanelControllerBase.TileRecord.this.getClass();
                        if (needToSkip()) {
                            return;
                        }
                        final SecQSDetailController secQSDetailController3 = secQSDetailController2;
                        secQSDetailController3.view.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1$onScanStateChanged$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecQSDetailController secQSDetailController4 = SecQSDetailController.this;
                                boolean z2 = z;
                                int i = SecQSDetailController.$r8$clinit;
                                if (secQSDetailController4.scanState == z2) {
                                    return;
                                }
                                secQSDetailController4.scanState = z2;
                                ProgressBar progressBar = secQSDetailController4.qsDetailHeaderProgress;
                                if (progressBar != null) {
                                    progressBar.setVisibility((z2 && secQSDetailController4.isBluetoothOrWifiTile()) ? 0 : 8);
                                }
                            }
                        });
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onScrollToDetail(final int i, final int i2) {
                        if (needToSkip()) {
                            return;
                        }
                        final SecQSDetailController secQSDetailController3 = secQSDetailController2;
                        secQSDetailController3.view.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1$onScrollToDetail$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScrollView scrollView = (ScrollView) SecQSDetailController.this.view.findViewById(R.id.qs_detail_scroll);
                                if (scrollView != null) {
                                    scrollView.scrollTo(i, i2);
                                }
                            }
                        });
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onShowDetail(final boolean z) {
                        final SecQSDetailController secQSDetailController3 = secQSDetailController2;
                        QSTileHost qSTileHost = secQSDetailController3.tileHost;
                        final SecQSPanelControllerBase.TileRecord tileRecord2 = SecQSPanelControllerBase.TileRecord.this;
                        if (qSTileHost.isLargeBarTile(tileRecord2.tile.getTileSpec()) && tileRecord2.tileView.getClass().getSimpleName().equals("NoLabelTileView")) {
                            return;
                        }
                        secQSDetailController3.view.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1$onShowDetail$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecQSDetailController.this.showDetail(z, tileRecord2);
                            }
                        });
                    }

                    @Override // com.android.systemui.plugins.qs.QSTile.Callback
                    public final void onStateChanged(QSTile.State state) {
                        SecQSPanelControllerBase.TileRecord.this.tileView.onStateChanged(state);
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onToggleStateChanged(final boolean z) {
                        if (needToSkip()) {
                            return;
                        }
                        final SecQSDetailController secQSDetailController3 = secQSDetailController2;
                        secQSDetailController3.view.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1$onToggleStateChanged$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecQSDetailController secQSDetailController4 = SecQSDetailController.this;
                                boolean z2 = z;
                                DetailAdapter detailAdapter = secQSDetailController4.detailAdapter;
                                boolean z3 = false;
                                if (detailAdapter != null && detailAdapter.getToggleEnabled()) {
                                    z3 = true;
                                }
                                SecQSDetailController.access$handleToggleStateChanged(secQSDetailController4, z2, z3);
                            }
                        });
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onUpdateDetail() {
                        if (needToSkip()) {
                            return;
                        }
                        final SecQSDetailController secQSDetailController3 = secQSDetailController2;
                        SecQSDetail secQSDetail = secQSDetailController3.view;
                        final SecQSPanelControllerBase.TileRecord tileRecord2 = SecQSPanelControllerBase.TileRecord.this;
                        secQSDetail.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1$onUpdateDetail$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecQSDetailController.access$handleUpdatingDetail(SecQSDetailController.this, tileRecord2.mDetailAdapter);
                            }
                        });
                    }
                };
            }
        };
        qSPanelHost.mBarController = barController;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mMediaHost = mediaHost;
        this.mDetailController = secQSDetailController;
    }

    private void switchToParent(View view, ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        viewGroup.removeView(view);
        viewGroup.addView(view);
    }

    public void addBarItems() {
        ArrayList barItems = this.mQsPanelHost.getBarItems();
        if (barItems == null) {
            return;
        }
        barItems.forEach(new SecQSPanelControllerBase$$ExternalSyntheticLambda2(this, 1));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        QSPanelHost qSPanelHost = this.mQsPanelHost;
        qSPanelHost.getClass();
        printWriter.println("QSPanelHost:");
        printWriter.println("  Tile records:");
        qSPanelHost.mRecords.stream().filter(new QSPanelHost$$ExternalSyntheticLambda1(2)).forEach(new QSPanelHost$$ExternalSyntheticLambda14(0, printWriter, strArr));
        ((SecQSPanel) this.mView).getRootView().getViewRootImpl().dump("SecQSPanelControllerBase", printWriter);
    }

    public final void flipPageWithTile(final String str) {
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null && (qSTileLayout instanceof SecTileChunkLayout)) {
            this.mQsPanelHost.getBarItems().forEach(new SecQSPanelControllerBase$$ExternalSyntheticLambda2(this, 0));
            if (this.mTileLayout instanceof SecTileChunkLayout) {
                final int i = 0;
                ((SecQSPanel) this.mView).getHandler().postDelayed(new Runnable(this) { // from class: com.android.systemui.qs.SecQSPanelControllerBase$$ExternalSyntheticLambda3
                    public final /* synthetic */ SecQSPanelControllerBase f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                SecQSPanelControllerBase secQSPanelControllerBase = this.f$0;
                                String str2 = str;
                                QSPanelHost qSPanelHost = secQSPanelControllerBase.mQsPanelHost;
                                ViewParent parent = qSPanelHost.mTargetView.getParent();
                                QSTileView tileView = qSPanelHost.getTileView(str2);
                                if (parent != null && (parent instanceof NonInterceptingScrollView) && tileView != null && tileView.isAttachedToWindow()) {
                                    ((NonInterceptingScrollView) parent).smoothScrollToDescendant(tileView);
                                    break;
                                }
                                break;
                            default:
                                SecQSPanelControllerBase secQSPanelControllerBase2 = this.f$0;
                                QSTileView tileView2 = secQSPanelControllerBase2.mQsPanelHost.getTileView(str);
                                if (tileView2 != null && tileView2.isAttachedToWindow()) {
                                    tileView2.setPressed(true);
                                    tileView2.setPressed(false);
                                    break;
                                }
                                break;
                        }
                    }
                }, 400L);
            }
            final int i2 = 1;
            ((SecQSPanel) this.mView).getHandler().postDelayed(new Runnable(this) { // from class: com.android.systemui.qs.SecQSPanelControllerBase$$ExternalSyntheticLambda3
                public final /* synthetic */ SecQSPanelControllerBase f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            SecQSPanelControllerBase secQSPanelControllerBase = this.f$0;
                            String str2 = str;
                            QSPanelHost qSPanelHost = secQSPanelControllerBase.mQsPanelHost;
                            ViewParent parent = qSPanelHost.mTargetView.getParent();
                            QSTileView tileView = qSPanelHost.getTileView(str2);
                            if (parent != null && (parent instanceof NonInterceptingScrollView) && tileView != null && tileView.isAttachedToWindow()) {
                                ((NonInterceptingScrollView) parent).smoothScrollToDescendant(tileView);
                                break;
                            }
                            break;
                        default:
                            SecQSPanelControllerBase secQSPanelControllerBase2 = this.f$0;
                            QSTileView tileView2 = secQSPanelControllerBase2.mQsPanelHost.getTileView(str);
                            if (tileView2 != null && tileView2.isAttachedToWindow()) {
                                tileView2.setPressed(true);
                                tileView2.setPressed(false);
                                break;
                            }
                            break;
                    }
                }
            }, 700L);
        }
    }

    public SecQSPanel.QSTileLayout getOrCreateTileLayout() {
        if (this.mTileLayout == null) {
            SecQSPanel.QSTileLayout qSTileLayout = (SecQSPanel.QSTileLayout) LayoutInflater.from(((SecQSPanel) this.mView).getContext()).inflate(R.layout.qs_paged_tile_layout, (ViewGroup) this.mView, false);
            this.mTileLayout = qSTileLayout;
            qSTileLayout.setLogger(this.mQSLogger);
        }
        return this.mTileLayout;
    }

    public final SecQSPanel getView() {
        return (SecQSPanel) this.mView;
    }

    public void onConfigurationChanged(Configuration configuration) {
        int i = ((SecQSPanel) this.mView).getContext().getResources().getConfiguration().orientation;
        ConfigurationState configurationState = this.mLastConfigurationState;
        boolean needToUpdate = configurationState.needToUpdate(configuration);
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onConfigurationChanged currentOrientation = ", ", newConfig.orientation = ");
        m.append(configuration.orientation);
        m.append(", mOrientation = ");
        m.append(this.mOrientation);
        m.append(", needToUpdate = ");
        m.append(needToUpdate);
        Log.d("SecQSPanelControllerBase", m.toString());
        Log.d("SecQSPanelControllerBase", "onConfigurationChanged diff = " + configurationState.toCompareString(configuration));
        Log.d("SecQSPanelControllerBase", "onConfigurationChanged view.config = " + ((SecQSPanel) this.mView).getContext().getResources().getConfiguration() + ", appContext.config = " + ((SecQSPanel) this.mView).getContext().getApplicationContext().getResources().getConfiguration());
        if (needToUpdate || this.mOrientation != i) {
            this.mOrientation = i;
            SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
            if (qSTileLayout != null) {
                qSTileLayout.updateResources();
                updatePaddingAndMargins();
            }
            updatePaddingAndMargins();
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        SecQSPanel.QSTileLayout orCreateTileLayout = getOrCreateTileLayout();
        this.mTileLayout = orCreateTileLayout;
        this.mQsPanelHost.mTileLayout = orCreateTileLayout;
        boolean z = this.mListening;
        this.mQSLogger.logAllTilesChangeListening(((SecQSPanel) this.mView).getDumpableTag(), "", z);
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.updateResources();
            updatePaddingAndMargins();
        }
        updatePaddingAndMargins();
        SecQSPanel secQSPanel = (SecQSPanel) this.mView;
        ((ArrayList) secQSPanel.mOnConfigurationChangedListeners).add(this.mOnConfigurationChangedListener);
        QSPanelHost qSPanelHost = this.mQsPanelHost;
        qSPanelHost.setTiles();
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).registerApplier(qSPanelHost.mResetSettingsApplier);
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).registerDemoApplier(qSPanelHost.mDemoResetSettingsApplier);
        updatePanelContents();
        String dumpableTag = ((SecQSPanel) this.mView).getDumpableTag();
        DumpManager dumpManager = this.mDumpManager;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, dumpableTag, this);
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        SecQSPanel secQSPanel = (SecQSPanel) this.mView;
        ((ArrayList) secQSPanel.mOnConfigurationChangedListeners).remove(this.mOnConfigurationChangedListener);
        QSPanelHost qSPanelHost = this.mQsPanelHost;
        qSPanelHost.mRecords.forEach(new QSPanelHost$$ExternalSyntheticLambda5(0));
        qSPanelHost.mRecords.clear();
        boolean isHeader = qSPanelHost.isHeader();
        QSHost qSHost = qSPanelHost.mQsHost;
        if (isHeader) {
            ((ArrayList) qSHost.getQQSTileHost().mCallbacks).remove(qSPanelHost);
        } else {
            qSHost.removeCallback(qSPanelHost);
        }
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).unregisterApplier(qSPanelHost.mResetSettingsApplier);
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).unregisterDemoApplier(qSPanelHost.mDemoResetSettingsApplier);
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.setListening(false, this.mUiEventLogger);
        }
        this.mDumpManager.unregisterDumpable(((SecQSPanel) this.mView).getDumpableTag());
    }

    public final void refreshAllTiles() {
        this.mQsPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda6(1)).filter(new QSPanelHost$$ExternalSyntheticLambda1(1)).forEach(new QSPanelHost$$ExternalSyntheticLambda5(1));
    }

    public final void setCollapseExpandAction(Runnable runnable) {
        this.mCollapseExpandAction = runnable;
        ((SecQSPanel) this.mView).mCollapseExpandAction = runnable;
    }

    public void setExpanded(boolean z) {
        if (this.mExpanded == z) {
            return;
        }
        this.mExpanded = z;
        this.mQSLogger.logPanelExpanded(((SecQSPanel) this.mView).getDumpableTag(), z);
        this.mMetricsLogger.visibility(111, z);
        if (!z) {
            this.mUiEventLogger.log(((SecQSPanel) this.mView).closePanelEvent());
            this.mDetailController.closeDetail();
            return;
        }
        this.mUiEventLogger.log(((SecQSPanel) this.mView).openPanelEvent());
        QSPanelHost qSPanelHost = this.mQsPanelHost;
        Stream map = qSPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda6(6)).map(new QSPanelHost$$ExternalSyntheticLambda6(7));
        MetricsLogger metricsLogger = qSPanelHost.mMetricsLogger;
        Objects.requireNonNull(metricsLogger);
        map.forEach(new QSPanelHost$$ExternalSyntheticLambda0(metricsLogger, 3));
    }

    public final void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (this.mTileLayout != null) {
            this.mQSLogger.logAllTilesChangeListening(((SecQSPanel) this.mView).getDumpableTag(), (String) this.mQsPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda6(0)).collect(Collectors.joining(",")), z);
            this.mTileLayout.setListening(z, this.mUiEventLogger);
        }
        if (z) {
            refreshAllTiles();
        }
    }

    public void updatePaddingAndMargins() {
        int panelSidePadding = this.mResourcePicker.resourcePickHelper.getTargetPicker().getPanelSidePadding(getContext());
        SecQSPanel secQSPanel = (SecQSPanel) this.mView;
        secQSPanel.setPadding(panelSidePadding, secQSPanel.getPaddingTop(), panelSidePadding, ((SecQSPanel) this.mView).getPaddingBottom());
    }

    public void updatePanelContents() {
        addBarItems();
    }
}
