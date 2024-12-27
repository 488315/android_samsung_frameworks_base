package com.android.systemui.qs;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ToBooleanFunction;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BottomLargeTileBar;
import com.android.systemui.qs.bar.BrightnessVolumeBar;
import com.android.systemui.qs.bar.SmartViewLargeTileBar;
import com.android.systemui.qs.bar.TopLargeTileBar;
import com.android.systemui.qs.tileimpl.LabelTileView;
import com.android.systemui.qs.tileimpl.LargeTileView;
import com.android.systemui.qs.tileimpl.NoLabelTileView;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.accounts.HostAuth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSPanelHost implements QSHost.Callback {
    public BarController mBarController;
    public final ArrayList mCallbacks;
    public final MetricsLogger mMetricsLogger;
    public IntSupplier mOrientationSupplier;
    public final QSHost mQsHost;
    public final ArrayList mRecords;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SecQSPanel mTargetView;
    public Function mTileCallbackFunction;
    public SecQSPanel.QSTileLayout mTileLayout;
    public final int mType;
    public Runnable resetBarSettingsRunnable;
    public final QSPanelHost$$ExternalSyntheticLambda15 mResetSettingsApplier = new QsResetSettingsManager.ResetSettingsApplier() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda15
        @Override // com.android.systemui.util.QsResetSettingsManager.ResetSettingsApplier
        public final void applyResetSetting() {
            QSPanelHost qSPanelHost = QSPanelHost.this;
            qSPanelHost.getClass();
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setPanelSplit(true);
            Runnable runnable = qSPanelHost.resetBarSettingsRunnable;
            if (runnable != null) {
                runnable.run();
            }
        }
    };
    public final QSPanelHost$$ExternalSyntheticLambda16 mDemoResetSettingsApplier = new QsResetSettingsManager.DemoResetSettingsApplier() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda16
        @Override // com.android.systemui.util.QsResetSettingsManager.DemoResetSettingsApplier
        public final void applyDemoResetSetting() {
            QSPanelHost qSPanelHost = QSPanelHost.this;
            qSPanelHost.getClass();
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setPanelSplit(true);
            Runnable runnable = qSPanelHost.resetBarSettingsRunnable;
            if (runnable != null) {
                runnable.run();
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda15] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda16] */
    public QSPanelHost(int i, View view, QSHost qSHost, MetricsLogger metricsLogger, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        this.mType = i;
        this.mTargetView = (SecQSPanel) view;
        this.mQsHost = qSHost;
        this.mResourcePicker = secQSPanelResourcePicker;
        if (isHeader()) {
            ((ArrayList) qSHost.getQQSTileHost().mCallbacks).add(this);
        } else {
            qSHost.addCallback(this);
        }
        this.mMetricsLogger = metricsLogger;
        this.mRecords = new ArrayList();
        this.mCallbacks = new ArrayList();
    }

    public static String orientationConvertToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "land" : HostAuth.PORT : "undef";
    }

    public final void addBarTiles(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        this.mQsHost.getBarTilesByType(i2, i).stream().map(new QSPanelHost$$ExternalSyntheticLambda3(this, 1)).forEach(new QSPanelHost$$ExternalSyntheticLambda14(1, this, arrayList));
        Log.d("QSPanelHost", "addBarTiles: type=" + i + " tileRecords=" + arrayList + " orientation=" + i2);
        if (i == 0) {
            final int i3 = 0;
            addTilesToBar(arrayList, new ToBooleanFunction() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda20
                public final boolean apply(Object obj) {
                    switch (i3) {
                        case 0:
                            return TopLargeTileBar.class.isInstance((BarItemImpl) obj);
                        case 1:
                            return BrightnessVolumeBar.class.isInstance((BarItemImpl) obj);
                        case 2:
                            return BottomLargeTileBar.class.isInstance((BarItemImpl) obj);
                        default:
                            return SmartViewLargeTileBar.class.isInstance((BarItemImpl) obj);
                    }
                }
            }, "TopBar");
            return;
        }
        if (i == 1) {
            final int i4 = 1;
            addTilesToBar(arrayList, new ToBooleanFunction() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda20
                public final boolean apply(Object obj) {
                    switch (i4) {
                        case 0:
                            return TopLargeTileBar.class.isInstance((BarItemImpl) obj);
                        case 1:
                            return BrightnessVolumeBar.class.isInstance((BarItemImpl) obj);
                        case 2:
                            return BottomLargeTileBar.class.isInstance((BarItemImpl) obj);
                        default:
                            return SmartViewLargeTileBar.class.isInstance((BarItemImpl) obj);
                    }
                }
            }, "BrightnessVolumeBar");
        } else if (i == 2) {
            final int i5 = 2;
            addTilesToBar(arrayList, new ToBooleanFunction() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda20
                public final boolean apply(Object obj) {
                    switch (i5) {
                        case 0:
                            return TopLargeTileBar.class.isInstance((BarItemImpl) obj);
                        case 1:
                            return BrightnessVolumeBar.class.isInstance((BarItemImpl) obj);
                        case 2:
                            return BottomLargeTileBar.class.isInstance((BarItemImpl) obj);
                        default:
                            return SmartViewLargeTileBar.class.isInstance((BarItemImpl) obj);
                    }
                }
            }, "BottomBar");
        } else {
            if (i != 3) {
                return;
            }
            final int i6 = 3;
            addTilesToBar(arrayList, new ToBooleanFunction() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda20
                public final boolean apply(Object obj) {
                    switch (i6) {
                        case 0:
                            return TopLargeTileBar.class.isInstance((BarItemImpl) obj);
                        case 1:
                            return BrightnessVolumeBar.class.isInstance((BarItemImpl) obj);
                        case 2:
                            return BottomLargeTileBar.class.isInstance((BarItemImpl) obj);
                        default:
                            return SmartViewLargeTileBar.class.isInstance((BarItemImpl) obj);
                    }
                }
            }, "SmartViewBar");
        }
    }

    public final void addTilesToBar(List list, ToBooleanFunction toBooleanFunction, String str) {
        getBarItems().stream().filter(new QSPanelHost$$ExternalSyntheticLambda25(toBooleanFunction, 1)).map(new QSPanelHost$$ExternalSyntheticLambda6(4)).forEach(new QSPanelHost$$ExternalSyntheticLambda31(this, list, str));
    }

    public final QSTileView createTileView(QSTile qSTile, boolean z) {
        Context context = this.mTargetView.getContext();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        if (z) {
            return new NoLabelTileView(context, secQSPanelResourcePicker);
        }
        String tileSpec = qSTile.getTileSpec();
        QSHost qSHost = this.mQsHost;
        return qSHost.isLargeBarTile(tileSpec) ? new LargeTileView(context, secQSPanelResourcePicker, qSHost.isNoBgLargeTile(qSTile.getTileSpec())) : qSHost.isBrightnessVolumeBarTile(qSTile.getTileSpec()) ? new NoLabelTileView(context, secQSPanelResourcePicker) : new LabelTileView(context, secQSPanelResourcePicker);
    }

    public final ArrayList getBarItems() {
        if (this.mBarController != null) {
            return isHeader() ? this.mBarController.mCollapsedBarItems : this.mBarController.mExpandedBarItems;
        }
        Log.e("QSPanelHost", "getBarItems: mBarController is null");
        return new ArrayList();
    }

    public final QSTileView getTileView(String str) {
        return (QSTileView) this.mRecords.stream().filter(new QSPanelHost$$ExternalSyntheticLambda17(str, 1)).findFirst().map(new QSPanelHost$$ExternalSyntheticLambda6(3)).orElse(null);
    }

    public final boolean isHeader() {
        return this.mType == 1;
    }

    @Override // com.android.systemui.qs.QSHost.Callback
    public final void onTilesChanged() {
        setTiles();
        this.mCallbacks.forEach(new QSPanelHost$$ExternalSyntheticLambda5(2));
    }

    public final void setBarsToPanel(List list) {
        SecQSPanel secQSPanel = this.mTargetView;
        int dimensionPixelSize = secQSPanel.getContext().getResources().getDimensionPixelSize(R.dimen.bar_top_margin);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            secQSPanel.addView(view);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.bottomMargin = dimensionPixelSize;
            view.setLayoutParams(layoutParams);
        }
        Runnable runnable = this.mBarController.mUpdateAnimatorsRunner;
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void setTiles() {
        List arrayList;
        Context context = this.mTargetView.getContext();
        int asInt = this.mOrientationSupplier.getAsInt() != 0 ? this.mOrientationSupplier.getAsInt() : context.getResources().getConfiguration().orientation;
        Log.d("QSPanelHost", "setTiles orientation = " + orientationConvertToString(asInt) + ", controller.orientation = " + orientationConvertToString(this.mOrientationSupplier.getAsInt()) + ", view.orientation = " + orientationConvertToString(context.getResources().getConfiguration().orientation));
        boolean isHeader = isHeader();
        QSHost qSHost = this.mQsHost;
        if (isHeader) {
            arrayList = (List) (isHeader() ? qSHost.getQQSTileHost().mTiles.values() : qSHost.getTiles()).stream().limit(this.mResourcePicker.resourcePickHelper.getTargetPicker().getQuickQsTileNum(context)).collect(Collectors.toList());
        } else {
            arrayList = new ArrayList(isHeader() ? qSHost.getQQSTileHost().mTiles.values() : qSHost.getTiles());
        }
        this.mRecords.forEach(new QSPanelHost$$ExternalSyntheticLambda0(this, 0));
        this.mRecords.clear();
        if (this.mType == 0) {
            addBarTiles(0, asInt);
            addBarTiles(1, asInt);
            addBarTiles(2, asInt);
            addBarTiles(3, asInt);
            getBarItems().stream().filter(new QSPanelHost$$ExternalSyntheticLambda1(0)).forEach(new QSPanelHost$$ExternalSyntheticLambda0(arrayList, 1));
        }
        Stream map = arrayList.stream().map(new QSPanelHost$$ExternalSyntheticLambda3(this, 0));
        ArrayList arrayList2 = this.mRecords;
        Objects.requireNonNull(arrayList2);
        map.forEach(new QSPanelHost$$ExternalSyntheticLambda0(arrayList2, 2));
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.updateResources();
        }
    }
}
