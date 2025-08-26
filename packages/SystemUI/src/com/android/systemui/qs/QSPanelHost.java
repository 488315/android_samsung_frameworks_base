package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ToBooleanFunction;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BottomLargeTileBar;
import com.android.systemui.qs.bar.BrightnessVolumeBar;
import com.android.systemui.qs.bar.SmartViewLargeTileBar;
import com.android.systemui.qs.bar.TopLargeTileBar;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.qs.tileimpl.LabelTileView;
import com.android.systemui.qs.tileimpl.LargeTileView;
import com.android.systemui.qs.tileimpl.NoLabelTileView;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.accounts.HostAuth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes2.dex */
public class QSPanelHost implements QSHost.Callback {
    public SecQSPanelController$$ExternalSyntheticLambda1 applyBarOrderRunnable;
    public BarController mBarController;
    public SecQSDetailController mDetailController;
    public final MetricsLogger mMetricsLogger;
    public IntSupplier mOrientationSupplier;
    public final QSHost mQsHost;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SecQSPanel mTargetView;
    public SecQSPanelControllerBase$$ExternalSyntheticLambda1 mTileCallbackFunction;
    public SecQSPanel.QSTileLayout mTileLayout;
    public final int mType;
    public SecQSPanelController$$ExternalSyntheticLambda0 resetBarSettingsRunnable;
    public final QSPanelHost$$ExternalSyntheticLambda15 mResetSettingsApplier = new QsResetSettingsManager.ResetSettingsApplier() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda15
        @Override // com.android.systemui.util.QsResetSettingsManager.ResetSettingsApplier
        public final void applyResetSetting() {
            this.f$0.resetSettings();
        }
    };
    public final QSPanelHost$$ExternalSyntheticLambda16 mDemoResetSettingsApplier = new QsResetSettingsManager.DemoResetSettingsApplier() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda16
        @Override // com.android.systemui.util.QsResetSettingsManager.DemoResetSettingsApplier
        public final void applyDemoResetSetting() {
            QSPanelHost qSPanelHost = this.f$0;
            qSPanelHost.resetSettings();
            SecQSPanelController$$ExternalSyntheticLambda1 secQSPanelController$$ExternalSyntheticLambda1 = qSPanelHost.applyBarOrderRunnable;
            if (secQSPanelController$$ExternalSyntheticLambda1 != null) {
                secQSPanelController$$ExternalSyntheticLambda1.run();
            }
        }
    };
    public final ArrayList mRecords = new ArrayList();
    public final ArrayList mCallbacks = new ArrayList();

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda15] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda16] */
    public QSPanelHost(int i, View view, QSHost qSHost, MetricsLogger metricsLogger, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        this.mType = i;
        this.mTargetView = (SecQSPanel) view;
        this.mQsHost = qSHost;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mMetricsLogger = metricsLogger;
        qSHost.addCallback(this);
    }

    public final void addBarTiles(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        this.mQsHost.getBarTilesByType(i2, i).stream().map(new QSPanelHost$$ExternalSyntheticLambda4(this, 1)).forEach(new QSPanelHost$$ExternalSyntheticLambda14(1, this, arrayList));
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
        Resources resources = context.getResources();
        String tileSpec = qSTile.getTileSpec();
        return this.mQsHost.isLargeBarTile(tileSpec) ? new LargeTileView(context, secQSPanelResourcePicker, resources.getString(R.string.sec_no_bg_tiles).contains(tileSpec)) : resources.getString(R.string.sec_brightness_volume_bar_tiles_default).contains(tileSpec) ? new NoLabelTileView(context, secQSPanelResourcePicker) : new LabelTileView(context, secQSPanelResourcePicker);
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
        setTiles(Boolean.TRUE);
        this.mCallbacks.forEach(new QSPanelHost$$ExternalSyntheticLambda0(2));
    }

    public final void resetSettings() {
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setPanelSplit(true);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setPanelSplitReversed(false);
        SecQSPanelController$$ExternalSyntheticLambda0 secQSPanelController$$ExternalSyntheticLambda0 = this.resetBarSettingsRunnable;
        if (secQSPanelController$$ExternalSyntheticLambda0 != null) {
            secQSPanelController$$ExternalSyntheticLambda0.run();
        }
    }

    public final void setBarsToPanel(List list) throws Resources.NotFoundException {
        SecQSPanel secQSPanel = this.mTargetView;
        int dimensionPixelSize = secQSPanel.getContext().getResources().getDimensionPixelSize(R.dimen.bar_top_margin);
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            View view = (View) obj;
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            secQSPanel.addView(view);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.bottomMargin = dimensionPixelSize;
            view.setLayoutParams(layoutParams);
        }
        SecQSImpl$onComponentCreated$3$1 secQSImpl$onComponentCreated$3$1 = this.mBarController.mUpdateAnimatorsRunner;
        if (secQSImpl$onComponentCreated$3$1 != null) {
            secQSImpl$onComponentCreated$3$1.run();
        }
    }

    public final void setTiles(Boolean bool) {
        int asInt;
        List arrayList;
        SecQSDetailController secQSDetailController;
        Context context = this.mTargetView.getContext();
        IntSupplier intSupplier = this.mOrientationSupplier;
        if (intSupplier == null || intSupplier.getAsInt() == 0) {
            asInt = context.getResources().getConfiguration().orientation;
        } else {
            StringBuilder sb = new StringBuilder("getOrientation controller.orientation = ");
            sb.append(this.mOrientationSupplier.getAsInt());
            sb.append(", view.orientation = ");
            RecyclerView$$ExternalSyntheticOutline0.m(context.getResources().getConfiguration().orientation, "QSPanelHost", sb);
            asInt = this.mOrientationSupplier.getAsInt();
        }
        if (!((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && !QpRune.QUICK_PANEL_BLUR_MASSIVE && bool.booleanValue() && QsAnimatorState.isDetailPopupShowing && (secQSDetailController = this.mDetailController) != null && asInt == secQSDetailController.oldOrientation) {
            Log.d("QSPanelHost", "setTiles isDetailPopupShowing : closeDetail");
            this.mDetailController.closeDetail();
        }
        StringBuilder sb2 = new StringBuilder("setTiles isForcedCloseDetail = ");
        sb2.append(bool);
        sb2.append(", orientation = ");
        sb2.append(asInt != 0 ? asInt != 1 ? asInt != 2 ? String.valueOf(asInt) : "land" : HostAuth.PORT : "undef");
        Log.d("QSPanelHost", sb2.toString());
        boolean zIsHeader = isHeader();
        QSHost qSHost = this.mQsHost;
        if (zIsHeader) {
            this.mResourcePicker.resourcePickHelper.getTargetPicker().getClass();
            SecQSPanelResourceCommon.Companion.getClass();
            arrayList = (List) qSHost.getTiles().stream().limit(SecQSPanelResourceCommon.Companion.m2904int(R.integer.sec_quick_qs_panel_max_columns, context)).collect(Collectors.toList());
        } else {
            arrayList = new ArrayList(qSHost.getTiles());
        }
        this.mRecords.forEach(new QSPanelHost$$ExternalSyntheticLambda1(this, 0));
        this.mRecords.clear();
        if (this.mType == 0) {
            addBarTiles(0, asInt);
            addBarTiles(1, asInt);
            addBarTiles(3, asInt);
            addBarTiles(2, asInt);
            getBarItems().stream().filter(new QSPanelHost$$ExternalSyntheticLambda2(0)).forEach(new QSPanelHost$$ExternalSyntheticLambda1(arrayList, 1));
        }
        Stream map = arrayList.stream().map(new QSPanelHost$$ExternalSyntheticLambda4(this, 0));
        ArrayList arrayList2 = this.mRecords;
        Objects.requireNonNull(arrayList2);
        map.forEach(new QSPanelHost$$ExternalSyntheticLambda1(arrayList2, 2));
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.updateResources();
        }
    }
}
