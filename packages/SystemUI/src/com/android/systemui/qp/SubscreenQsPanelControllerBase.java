package com.android.systemui.qp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSEvents;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSPanelControllerBase$TileRecord;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SubscreenQsPanelControllerBase extends ViewController implements SubscreenQSControllerContract$BaseViewController {
    public final Context mContext;
    public final AnonymousClass1 mDisplayListener;
    public final SubscreenQsPanelControllerBase$$ExternalSyntheticLambda0 mQSHostCallback;
    public final QSHost mSubScreenTileHost;
    public final ArrayList mSubscreenRecords;
    public final UiEventLogger mUiEventLogger;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.qp.SubscreenQsPanelControllerBase$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.qp.SubscreenQsPanelControllerBase$1, java.lang.Object] */
    public SubscreenQsPanelControllerBase(SubroomQuickSettingsQSPanelBaseView subroomQuickSettingsQSPanelBaseView, QSHost qSHost) {
        super(subroomQuickSettingsQSPanelBaseView);
        this.mSubscreenRecords = new ArrayList();
        QSEvents.INSTANCE.getClass();
        this.mUiEventLogger = QSEvents.qsUiEventsLogger;
        this.mQSHostCallback = new QSHost.Callback() { // from class: com.android.systemui.qp.SubscreenQsPanelControllerBase$$ExternalSyntheticLambda0
            @Override // com.android.systemui.qs.QSHost.Callback
            public final void onTilesChanged() {
                SubscreenQsPanelControllerBase.this.setTiles();
            }
        };
        ?? r2 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qp.SubscreenQsPanelControllerBase.1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onFolderStateChanged", "SubscreenQsPanelControllerBase", z);
                SubscreenQsPanelControllerBase subscreenQsPanelControllerBase = SubscreenQsPanelControllerBase.this;
                if (z) {
                    subscreenQsPanelControllerBase.setListening$2(false);
                    subscreenQsPanelControllerBase.removeAllTileViews();
                    QSHost qSHost2 = subscreenQsPanelControllerBase.mSubScreenTileHost;
                    if (qSHost2 != null) {
                        qSHost2.removeCallback(subscreenQsPanelControllerBase.mQSHostCallback);
                        return;
                    }
                    return;
                }
                QSHost qSHost3 = subscreenQsPanelControllerBase.mSubScreenTileHost;
                if (qSHost3 != null) {
                    qSHost3.addCallback(subscreenQsPanelControllerBase.mQSHostCallback);
                }
                subscreenQsPanelControllerBase.setTiles();
                subscreenQsPanelControllerBase.setListening$2(true);
                if (((ViewController) subscreenQsPanelControllerBase).mView != null) {
                    ((SubroomQuickSettingsQSPanelBaseView) ((ViewController) subscreenQsPanelControllerBase).mView).addPagedTileLayout();
                    ((SubroomQuickSettingsQSPanelBaseView) ((ViewController) subscreenQsPanelControllerBase).mView).updateResources$1();
                }
            }
        };
        this.mDisplayListener = r2;
        new View.OnLongClickListener() { // from class: com.android.systemui.qp.SubscreenQsPanelControllerBase.2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).startActivity(SubscreenQsPanelControllerBase.this.mContext, "com.android.systemui.qp.customize.SubscreenCustomizerActivity");
                return true;
            }
        };
        this.mSubScreenTileHost = qSHost;
        this.mContext = getContext();
        DisplayLifecycle displayLifecycle = (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class);
        if (displayLifecycle != 0) {
            displayLifecycle.addObserver(r2);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        Objects.toString(this.mView);
        SubroomQuickSettingsQSPanelBaseView subroomQuickSettingsQSPanelBaseView = (SubroomQuickSettingsQSPanelBaseView) this.mView;
        if (subroomQuickSettingsQSPanelBaseView.mTileLayout == null) {
            SubscreenPagedTileLayout subscreenPagedTileLayout = (SubscreenPagedTileLayout) LayoutInflater.from(subroomQuickSettingsQSPanelBaseView.mContext).inflate(R.layout.subscreen_paged_tile_layout, (ViewGroup) subroomQuickSettingsQSPanelBaseView, false);
            subroomQuickSettingsQSPanelBaseView.mTileLayout = subscreenPagedTileLayout;
            int size = subscreenPagedTileLayout.mPages.size();
            for (int i = 0; i < size; i++) {
                SubscreenTileLayout subscreenTileLayout = (SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(i);
                if (Float.compare(subscreenTileLayout.mSquishinessFraction, 1.0f) != 0) {
                    subscreenTileLayout.mSquishinessFraction = 1.0f;
                    subscreenTileLayout.layoutTileRecords$1(subscreenTileLayout.mRecords.size(), false);
                    ArrayList arrayList = subscreenTileLayout.mRecords;
                    int size2 = arrayList.size();
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ViewParent viewParent = ((QSPanelControllerBase$TileRecord) obj).tileView;
                        if (viewParent instanceof HeightOverrideable) {
                            float f = subscreenTileLayout.mSquishinessFraction;
                            QSTileViewImpl qSTileViewImpl = (QSTileViewImpl) ((HeightOverrideable) viewParent);
                            if (qSTileViewImpl.squishinessFraction != f) {
                                qSTileViewImpl.squishinessFraction = f;
                                qSTileViewImpl.updateHeight();
                            }
                        }
                    }
                }
            }
        }
        subroomQuickSettingsQSPanelBaseView.mTileLayout = subroomQuickSettingsQSPanelBaseView.mTileLayout;
        subroomQuickSettingsQSPanelBaseView.updatePageIndicator();
        new SubscreenBrightnessController(this.mContext, (SubroomBrightnessSettingsView) ((SubroomQuickSettingsQSPanelBaseView) this.mView).findViewById(R.id.subroom_brightness_settings)).init();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        Objects.toString(this.mView);
        QSHost qSHost = this.mSubScreenTileHost;
        if (qSHost != null) {
            qSHost.addCallback(this.mQSHostCallback);
        }
        setTiles();
        ((SubroomQuickSettingsQSPanelBaseView) this.mView).addPagedTileLayout();
        ((SubroomQuickSettingsQSPanelBaseView) this.mView).updateResources$1();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        Objects.toString(this.mView);
        QSHost qSHost = this.mSubScreenTileHost;
        if (qSHost != null) {
            qSHost.removeCallback(this.mQSHostCallback);
        }
        setListening$2(false);
        removeAllTileViews();
    }

    public final void removeAllTileViews() {
        ArrayList arrayList = this.mSubscreenRecords;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) obj;
            T t = this.mView;
            if (t != 0) {
                SubscreenPagedTileLayout subscreenPagedTileLayout = ((SubroomQuickSettingsQSPanelBaseView) t).mTileLayout;
                if (subscreenPagedTileLayout.mTiles.remove(qSPanelControllerBase$TileRecord)) {
                    subscreenPagedTileLayout.mDistributeTiles = true;
                    subscreenPagedTileLayout.requestLayout();
                }
            }
            qSPanelControllerBase$TileRecord.tile.removeCallback(qSPanelControllerBase$TileRecord.callback);
        }
        this.mSubscreenRecords.clear();
    }

    public final void setListening$2(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("setListening", "SubscreenQsPanelControllerBase", z);
        SubroomQuickSettingsQSPanelBaseView subroomQuickSettingsQSPanelBaseView = (SubroomQuickSettingsQSPanelBaseView) this.mView;
        if (subroomQuickSettingsQSPanelBaseView.mListening == z) {
            return;
        }
        subroomQuickSettingsQSPanelBaseView.mListening = z;
        SubscreenPagedTileLayout subscreenPagedTileLayout = subroomQuickSettingsQSPanelBaseView.mTileLayout;
        if (subscreenPagedTileLayout != null) {
            subscreenPagedTileLayout.setListening(z, this.mUiEventLogger);
        }
        if (((SubroomQuickSettingsQSPanelBaseView) this.mView).mListening) {
            Log.d("SubscreenQsPanelControllerBase", "refreshAllTiles");
            ArrayList arrayList = this.mSubscreenRecords;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) obj;
                if (!qSPanelControllerBase$TileRecord.tile.isListening()) {
                    qSPanelControllerBase$TileRecord.tile.refreshState();
                }
            }
        }
    }

    public final void setTiles() {
        QSHost qSHost = this.mSubScreenTileHost;
        if (qSHost != null) {
            Collection tiles = qSHost.getTiles();
            Log.d("SubscreenQsPanelControllerBase", "setTiles ");
            removeAllTileViews();
            ArrayList arrayList = (ArrayList) tiles;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                QSTile qSTile = (QSTile) obj;
                Log.d("SubscreenQsPanelControllerBase", "adding Tilespec : " + qSTile.getTileSpec());
                QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = new QSPanelControllerBase$TileRecord(qSTile, new SubRoomQsTileBaseView(this.mContext, new QSIconViewImpl(this.mContext), false));
                ((SubroomQuickSettingsQSPanelBaseView) this.mView).addTile(qSPanelControllerBase$TileRecord);
                this.mSubscreenRecords.add(qSPanelControllerBase$TileRecord);
                Log.d("SubscreenQsPanelControllerBase", "addTile tile.getTileSpec():  record: " + qSPanelControllerBase$TileRecord + " mSubscreenRecords.size(): " + this.mSubscreenRecords.size());
            }
        }
    }
}
