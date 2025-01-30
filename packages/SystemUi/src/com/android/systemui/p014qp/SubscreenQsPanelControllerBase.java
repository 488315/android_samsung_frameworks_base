package com.android.systemui.p014qp;

import android.content.Context;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.p016qs.QSEvents;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QSPanelControllerBase$TileRecord;
import com.android.systemui.p016qs.QSTileHost;
import com.android.systemui.p016qs.SecSubScreenQSTileHost;
import com.android.systemui.plugins.p013qs.QSFactory;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.p013qs.QSTileView;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenQsPanelControllerBase extends ViewController implements SubscreenQSControllerContract$BaseViewController {
    public final Context mContext;
    public final C20091 mDisplayListener;
    public final QSTileHost mHost;
    public final SubscreenQsPanelControllerBase$$ExternalSyntheticLambda0 mQSHostCallback;
    public final SecSubScreenQSTileHost mSubScreenTileHost;
    public final ArrayList mSubscreenRecords;
    public final UiEventLogger mUiEventLogger;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.qp.SubscreenQsPanelControllerBase$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.qp.SubscreenQsPanelControllerBase$1, java.lang.Object] */
    public SubscreenQsPanelControllerBase(SubroomQuickSettingsQSPanelBaseView subroomQuickSettingsQSPanelBaseView, QSTileHost qSTileHost) {
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
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onFolderStateChanged", z, "SubscreenQsPanelControllerBase");
                SubscreenQsPanelControllerBase subscreenQsPanelControllerBase = SubscreenQsPanelControllerBase.this;
                if (z) {
                    subscreenQsPanelControllerBase.setListening(false);
                    subscreenQsPanelControllerBase.removeAllTileViews();
                    SecSubScreenQSTileHost secSubScreenQSTileHost = subscreenQsPanelControllerBase.mSubScreenTileHost;
                    if (secSubScreenQSTileHost != null) {
                        ((ArrayList) secSubScreenQSTileHost.mCallbacks).remove(subscreenQsPanelControllerBase.mQSHostCallback);
                        return;
                    }
                    return;
                }
                SecSubScreenQSTileHost secSubScreenQSTileHost2 = subscreenQsPanelControllerBase.mSubScreenTileHost;
                if (secSubScreenQSTileHost2 != null) {
                    ((ArrayList) secSubScreenQSTileHost2.mCallbacks).add(subscreenQsPanelControllerBase.mQSHostCallback);
                }
                subscreenQsPanelControllerBase.setTiles();
                subscreenQsPanelControllerBase.setListening(true);
                View view = subscreenQsPanelControllerBase.mView;
                if (view != null) {
                    ((SubroomQuickSettingsQSPanelBaseView) view).addPagedTileLayout();
                    ((SubroomQuickSettingsQSPanelBaseView) subscreenQsPanelControllerBase.mView).updateResources();
                }
            }
        };
        this.mDisplayListener = r2;
        new View.OnLongClickListener() { // from class: com.android.systemui.qp.SubscreenQsPanelControllerBase.2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).startActivity(SubscreenQsPanelControllerBase.this.mContext, "com.android.systemui.qp.customize.SubscreenCustomizerActivity");
                return true;
            }
        };
        this.mHost = qSTileHost;
        this.mSubScreenTileHost = qSTileHost.mSubScreenTileHost;
        this.mContext = getContext();
        DisplayLifecycle displayLifecycle = (DisplayLifecycle) Dependency.get(DisplayLifecycle.class);
        if (displayLifecycle != 0) {
            displayLifecycle.addObserver(r2);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        Objects.toString(this.mView);
        SubroomQuickSettingsQSPanelBaseView subroomQuickSettingsQSPanelBaseView = (SubroomQuickSettingsQSPanelBaseView) this.mView;
        if (subroomQuickSettingsQSPanelBaseView.mTileLayout == null) {
            SubscreenPagedTileLayout subscreenPagedTileLayout = (SubscreenPagedTileLayout) LayoutInflater.from(subroomQuickSettingsQSPanelBaseView.mContext).inflate(R.layout.subscreen_paged_tile_layout, (ViewGroup) subroomQuickSettingsQSPanelBaseView, false);
            subroomQuickSettingsQSPanelBaseView.mTileLayout = subscreenPagedTileLayout;
            subscreenPagedTileLayout.setSquishinessFraction();
        }
        subroomQuickSettingsQSPanelBaseView.mTileLayout = subroomQuickSettingsQSPanelBaseView.mTileLayout;
        subroomQuickSettingsQSPanelBaseView.updatePageIndicator$1();
        new SubscreenBrightnessController(this.mContext, (SubroomBrightnessSettingsView) ((SubroomQuickSettingsQSPanelBaseView) this.mView).findViewById(R.id.subroom_brightness_settings)).init();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        Objects.toString(this.mView);
        SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
        if (secSubScreenQSTileHost != null) {
            ((ArrayList) secSubScreenQSTileHost.mCallbacks).add(this.mQSHostCallback);
        }
        setTiles();
        ((SubroomQuickSettingsQSPanelBaseView) this.mView).addPagedTileLayout();
        ((SubroomQuickSettingsQSPanelBaseView) this.mView).updateResources();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        Objects.toString(this.mView);
        SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
        if (secSubScreenQSTileHost != null) {
            ((ArrayList) secSubScreenQSTileHost.mCallbacks).remove(this.mQSHostCallback);
        }
        setListening(false);
        removeAllTileViews();
    }

    public final void removeAllTileViews() {
        ArrayList arrayList = this.mSubscreenRecords;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) it.next();
            View view = this.mView;
            if (view != null) {
                ((SubroomQuickSettingsQSPanelBaseView) view).mTileLayout.removeTile(qSPanelControllerBase$TileRecord);
            }
            qSPanelControllerBase$TileRecord.tile.removeCallback(qSPanelControllerBase$TileRecord.callback);
        }
        arrayList.clear();
    }

    public final void setListening(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setListening", z, "SubscreenQsPanelControllerBase");
        View view = this.mView;
        if (((SubroomQuickSettingsQSPanelBaseView) view).mListening == z) {
            return;
        }
        ((SubroomQuickSettingsQSPanelBaseView) view).mListening = z;
        if (((SubroomQuickSettingsQSPanelBaseView) view).mTileLayout != null) {
            ((SubroomQuickSettingsQSPanelBaseView) view).mTileLayout.setListening(z, this.mUiEventLogger);
        }
        if (((SubroomQuickSettingsQSPanelBaseView) this.mView).mListening) {
            Log.d("SubscreenQsPanelControllerBase", "refreshAllTiles");
            Iterator it = this.mSubscreenRecords.iterator();
            while (it.hasNext()) {
                QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) it.next();
                if (!qSPanelControllerBase$TileRecord.tile.isListening()) {
                    qSPanelControllerBase$TileRecord.tile.refreshState();
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0048, code lost:
    
        r3 = new com.android.systemui.p016qs.QSPanelControllerBase$TileRecord(r1, r5);
        r1 = (com.android.systemui.p014qp.SubroomQuickSettingsQSPanelBaseView) r7.mView;
        r1.getClass();
        r4 = new com.android.systemui.p016qs.QSPanel.C20511();
        r5 = r3.tile;
        r5.addCallback(r4);
        r3.callback = r4;
        r3.tileView.init(r5);
        r5.refreshState();
        r1 = r1.mTileLayout;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x006a, code lost:
    
        if (r1 == null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x006c, code lost:
    
        r1.addTile(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x006f, code lost:
    
        r1 = r7.mSubscreenRecords;
        r1.add(r3);
        android.util.Log.d("SubscreenQsPanelControllerBase", "addTile tile.getTileSpec():  record: " + r3 + " mSubscreenRecords.size(): " + r1.size());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setTiles() {
        SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
        if (secSubScreenQSTileHost != null) {
            Collection<QSTile> values = secSubScreenQSTileHost.mTiles.values();
            Log.d("SubscreenQsPanelControllerBase", "setTiles ");
            removeAllTileViews();
            for (QSTile qSTile : values) {
                AbstractC0000x2c234b15.m3m("adding Tilespec : ", qSTile.getTileSpec(), "SubscreenQsPanelControllerBase");
                int i = 0;
                while (true) {
                    ArrayList arrayList = this.mHost.mQsFactories;
                    if (i >= arrayList.size()) {
                        throw new RuntimeException("Default factory didn't create view for " + qSTile.getTileSpec());
                    }
                    QSTileView createCoverScreenTileView = ((QSFactory) arrayList.get(i)).createCoverScreenTileView(this.mContext, qSTile, false);
                    if (createCoverScreenTileView != null) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
    }
}
