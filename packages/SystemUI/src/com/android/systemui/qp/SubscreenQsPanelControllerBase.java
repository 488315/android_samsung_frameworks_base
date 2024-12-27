package com.android.systemui.qp;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSEvents;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase$TileRecord;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecSubScreenQSTileHost;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SubscreenQsPanelControllerBase extends ViewController implements SubscreenQSControllerContract$BaseViewController {
    public final Context mContext;
    public final AnonymousClass1 mDisplayListener;
    public final QSTileHost mHost;
    public final SubscreenQsPanelControllerBase$$ExternalSyntheticLambda0 mQSHostCallback;
    public final SecSubScreenQSTileHost mSubScreenTileHost;
    public final ArrayList mSubscreenRecords;
    public final UiEventLogger mUiEventLogger;

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.qp.SubscreenQsPanelControllerBase$$ExternalSyntheticLambda0] */
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
        DisplayLifecycle.Observer observer = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qp.SubscreenQsPanelControllerBase.1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onFolderStateChanged", "SubscreenQsPanelControllerBase", z);
                SubscreenQsPanelControllerBase subscreenQsPanelControllerBase = SubscreenQsPanelControllerBase.this;
                if (z) {
                    subscreenQsPanelControllerBase.setListening$1(false);
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
                subscreenQsPanelControllerBase.setListening$1(true);
                if (((ViewController) subscreenQsPanelControllerBase).mView != null) {
                    ((SubroomQuickSettingsQSPanelBaseView) ((ViewController) subscreenQsPanelControllerBase).mView).addPagedTileLayout();
                    ((SubroomQuickSettingsQSPanelBaseView) ((ViewController) subscreenQsPanelControllerBase).mView).updateResources$1();
                }
            }
        };
        new View.OnLongClickListener() { // from class: com.android.systemui.qp.SubscreenQsPanelControllerBase.2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).startActivity(SubscreenQsPanelControllerBase.this.mContext, "com.android.systemui.qp.customize.SubscreenCustomizerActivity");
                return true;
            }
        };
        this.mHost = qSTileHost;
        this.mSubScreenTileHost = qSTileHost.mSubScreenTileHost;
        this.mContext = getContext();
        DisplayLifecycle displayLifecycle = (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class);
        if (displayLifecycle != null) {
            displayLifecycle.addObserver(observer);
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
                    Iterator it = subscreenTileLayout.mRecords.iterator();
                    while (it.hasNext()) {
                        ViewParent viewParent = ((QSPanelControllerBase$TileRecord) it.next()).tileView;
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
        ((SubroomQuickSettingsQSPanelBaseView) this.mView).updateResources$1();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        Objects.toString(this.mView);
        SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
        if (secSubScreenQSTileHost != null) {
            ((ArrayList) secSubScreenQSTileHost.mCallbacks).remove(this.mQSHostCallback);
        }
        setListening$1(false);
        removeAllTileViews();
    }

    public final void removeAllTileViews() {
        Iterator it = this.mSubscreenRecords.iterator();
        while (it.hasNext()) {
            QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) it.next();
            T t = this.mView;
            if (t != 0) {
                ((SubroomQuickSettingsQSPanelBaseView) t).mTileLayout.removeTile(qSPanelControllerBase$TileRecord);
            }
            qSPanelControllerBase$TileRecord.tile.removeCallback(qSPanelControllerBase$TileRecord.callback);
        }
        this.mSubscreenRecords.clear();
    }

    public final void setListening$1(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setListening", "SubscreenQsPanelControllerBase", z);
        SubroomQuickSettingsQSPanelBaseView subroomQuickSettingsQSPanelBaseView = (SubroomQuickSettingsQSPanelBaseView) this.mView;
        if (subroomQuickSettingsQSPanelBaseView.mListening == z) {
            return;
        }
        subroomQuickSettingsQSPanelBaseView.mListening = z;
        QSPanel.QSTileLayout qSTileLayout = subroomQuickSettingsQSPanelBaseView.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.setListening(z, this.mUiEventLogger);
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

    public final void setTiles() {
        SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
        if (secSubScreenQSTileHost != null) {
            Collection<QSTile> values = secSubScreenQSTileHost.mTiles.values();
            Log.d("SubscreenQsPanelControllerBase", "setTiles ");
            removeAllTileViews();
            for (QSTile qSTile : values) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("adding Tilespec : ", qSTile.getTileSpec(), "SubscreenQsPanelControllerBase");
                Context context = this.mContext;
                this.mHost.getClass();
                QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = new QSPanelControllerBase$TileRecord(qSTile, new SubRoomQsTileBaseView(context, new QSIconViewImpl(context), true));
                ((SubroomQuickSettingsQSPanelBaseView) this.mView).addTile(qSPanelControllerBase$TileRecord);
                this.mSubscreenRecords.add(qSPanelControllerBase$TileRecord);
                Log.d("SubscreenQsPanelControllerBase", "addTile tile.getTileSpec():  record: " + qSPanelControllerBase$TileRecord + " mSubscreenRecords.size(): " + this.mSubscreenRecords.size());
            }
        }
    }
}
