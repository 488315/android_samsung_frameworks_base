package com.android.systemui.qs;

import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.SecDarkModeEasel;
import com.android.systemui.qs.SecQSDetail;
import com.android.systemui.qs.SecQSDetail.C20682.AnonymousClass3;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.PagedTileLayoutBar;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SecQSPanelControllerBase extends ViewController implements Dumpable {
    public Runnable mCollapseExpandAction;
    public final SecDarkModeEasel mDarkModelEasel;
    public SecQSDetail.C20682 mDetailCallback;
    public Record mDetailRecord;
    public String mDetailTileSpec;
    public final DumpManager mDumpManager;
    public boolean mExpandSettingsPanel;
    public boolean mExpanded;
    public final HandlerC2075H mHandler;
    public final QSHost mHost;
    public final ConfigurationState mLastConfigurationState;
    public boolean mListening;
    public final MetricsLogger mMetricsLogger;
    protected final SecQSPanel.OnConfigurationChangedListener mOnConfigurationChangedListener;
    public int mOrientation;
    public final QSLogger mQSLogger;
    public final QSPanelHost mQsPanelHost;
    public final SecQSPanelResourcePicker mResourcePicker;
    public SecQSPanel.QSTileLayout mTileLayout;
    public final UiEventLogger mUiEventLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.SecQSPanelControllerBase$H */
    public final class HandlerC2075H extends Handler {
        public /* synthetic */ HandlerC2075H(SecQSPanelControllerBase secQSPanelControllerBase, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            StringBuilder sb = new StringBuilder("handleMessage: msg.what: ");
            sb.append(message.what);
            sb.append(" msg.arg1:");
            RecyclerView$$ExternalSyntheticOutline0.m46m(sb, message.arg1, "SecQSPanelControllerBase");
            int i = message.what;
            if (i == 1) {
                ((SecQSPanel) SecQSPanelControllerBase.this.mView).announceForAccessibility((CharSequence) message.obj);
                return;
            }
            if (i != 99) {
                if (i == 100) {
                    Record record = (Record) message.obj;
                    final SecQSDetail.C20682 c20682 = SecQSPanelControllerBase.this.mDetailCallback;
                    if (c20682 != null) {
                        final DetailAdapter detailAdapter = record.mDetailAdapter;
                        SecQSDetail.this.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetail.2.4
                            public final /* synthetic */ DetailAdapter val$detail;

                            public AnonymousClass4(final DetailAdapter detailAdapter2) {
                                r2 = detailAdapter2;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                SecQSDetail secQSDetail = SecQSDetail.this;
                                DetailAdapter detailAdapter2 = r2;
                                int i2 = SecQSDetail.$r8$clinit;
                                secQSDetail.handleUpdatingDetail(detailAdapter2);
                            }
                        });
                        return;
                    }
                    return;
                }
                return;
            }
            Record record2 = (Record) message.obj;
            boolean z = message.arg1 != 0;
            if (!(record2 instanceof TileRecord)) {
                handleShowDetailImpl(record2, z);
                return;
            }
            TileRecord tileRecord = (TileRecord) record2;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("handleShowDetailTile :show ", z, "SecQSPanelControllerBase");
            Record record3 = SecQSPanelControllerBase.this.mDetailRecord;
            if ((record3 != null) == z && record3 == tileRecord) {
                return;
            }
            if (z) {
                DetailAdapter detailAdapter2 = tileRecord.tile.getDetailAdapter();
                tileRecord.mDetailAdapter = detailAdapter2;
                if (detailAdapter2 == null) {
                    return;
                }
            }
            SecQSPanelControllerBase.this.mDetailTileSpec = z ? tileRecord.tile.getTileSpec() : "";
            tileRecord.tile.setDetailListening(z);
            handleShowDetailImpl(tileRecord, z);
        }

        public final void handleShowDetailImpl(Record record, boolean z) {
            SecQSPanelControllerBase secQSPanelControllerBase = SecQSPanelControllerBase.this;
            Record record2 = z ? record : null;
            if (record2 != secQSPanelControllerBase.mDetailRecord) {
                secQSPanelControllerBase.mDetailRecord = record2;
                if (record2 instanceof TileRecord) {
                    boolean z2 = ((TileRecord) record2).scanState;
                }
            }
            final SecQSDetail.C20682 c20682 = secQSPanelControllerBase.mDetailCallback;
            if (c20682 != null) {
                final DetailAdapter detailAdapter = z ? record.mDetailAdapter : null;
                SecQSDetail.this.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetail.2.2
                    public final /* synthetic */ DetailAdapter val$detail;

                    public AnonymousClass2(final DetailAdapter detailAdapter2) {
                        r2 = detailAdapter2;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        if (SecQSDetail.this.isAttachedToWindow()) {
                            SecQSDetail.this.handleShowingDetail(r2);
                        }
                    }
                });
            }
        }

        private HandlerC2075H() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Record {
        public DetailAdapter mDetailAdapter;

        private Record() {
        }

        public /* synthetic */ Record(int i) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TileRecord extends Record {
        public SQSTile.SCallback callback;
        public boolean scanState;
        public final QSTile tile;
        public final QSTileView tileView;

        public TileRecord(QSTile qSTile, QSTileView qSTileView) {
            super(0);
            this.tile = qSTile;
            this.tileView = qSTileView;
        }
    }

    /* renamed from: $r8$lambda$X8r1Fwkz1Q6QfQQCpSy31GTu-7M, reason: not valid java name */
    public static void m1622$r8$lambda$X8r1Fwkz1Q6QfQQCpSy31GTu7M(SecQSPanelControllerBase secQSPanelControllerBase, BarItemImpl barItemImpl) {
        ViewGroup viewGroup;
        secQSPanelControllerBase.getClass();
        if (barItemImpl.mBarRootView == null) {
            ViewGroup viewGroup2 = (ViewGroup) secQSPanelControllerBase.mView;
            barItemImpl.inflateViews(viewGroup2);
            secQSPanelControllerBase.switchToParent(barItemImpl.mBarRootView, viewGroup2);
        }
        if (!(barItemImpl instanceof PagedTileLayoutBar) || (viewGroup = (ViewGroup) ((PagedTileLayoutBar) barItemImpl).mTileLayoutContainer) == null) {
            return;
        }
        viewGroup.removeView((View) secQSPanelControllerBase.mTileLayout);
        viewGroup.addView((View) secQSPanelControllerBase.mTileLayout, 0);
    }

    public SecQSPanelControllerBase(SecQSPanel secQSPanel, QSHost qSHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager, QSPanelHost qSPanelHost, BarController barController, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        super(secQSPanel);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION));
        this.mHandler = new HandlerC2075H(this, 0);
        this.mOnConfigurationChangedListener = new SecQSPanel.OnConfigurationChangedListener() { // from class: com.android.systemui.qs.SecQSPanelControllerBase.1
            @Override // com.android.systemui.qs.SecQSPanel.OnConfigurationChangedListener
            public final void onConfigurationChange(Configuration configuration) {
                Log.d("SecQSPanelControllerBase", "OnConfigurationChangedListener ,onConfigurationChange event");
                SecQSPanelControllerBase.this.onConfigurationChanged(configuration);
            }
        };
        this.mExpandSettingsPanel = false;
        this.mDarkModelEasel = new SecDarkModeEasel(new SecDarkModeEasel.PictureSubject() { // from class: com.android.systemui.qs.SecQSPanelControllerBase$$ExternalSyntheticLambda1
            @Override // com.android.systemui.qs.SecDarkModeEasel.PictureSubject
            public final void drawDarkModelPicture() {
                ArrayList arrayList;
                SecQSPanelControllerBase secQSPanelControllerBase = SecQSPanelControllerBase.this;
                QSPanelHost qSPanelHost2 = secQSPanelControllerBase.mQsPanelHost;
                if (qSPanelHost2 != null && (arrayList = qSPanelHost2.mRecords) != null) {
                    arrayList.stream().map(new QSPanelHost$$ExternalSyntheticLambda0(4)).forEach(new QSPanelHost$$ExternalSyntheticLambda1(3));
                }
                ((SecQSPanel) secQSPanelControllerBase.mView).updateDetailColor();
            }
        });
        this.mHost = qSHost;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mQSLogger = qSLogger;
        this.mDumpManager = dumpManager;
        this.mQsPanelHost = qSPanelHost;
        qSPanelHost.mTileCallbackFunction = new Function() { // from class: com.android.systemui.qs.SecQSPanelControllerBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                final SecQSPanelControllerBase secQSPanelControllerBase = SecQSPanelControllerBase.this;
                final SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                secQSPanelControllerBase.getClass();
                return new SQSTile.SCallback() { // from class: com.android.systemui.qs.SecQSPanelControllerBase.2
                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onAnnouncementRequested(CharSequence charSequence) {
                        if (charSequence != null) {
                            SecQSPanelControllerBase.this.mHandler.obtainMessage(1, charSequence).sendToTarget();
                        }
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onScanStateChanged(boolean z) {
                        SecQSDetail.C20682 c20682;
                        TileRecord tileRecord2 = tileRecord;
                        tileRecord2.scanState = z;
                        SecQSPanelControllerBase secQSPanelControllerBase2 = SecQSPanelControllerBase.this;
                        if (secQSPanelControllerBase2.mDetailRecord == tileRecord2 && (c20682 = secQSPanelControllerBase2.mDetailCallback) != null) {
                            SecQSDetail.this.post(c20682.new AnonymousClass3(z));
                        }
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onScrollToDetail(final int i, final int i2) {
                        final SecQSDetail.C20682 c20682;
                        SecQSPanelControllerBase secQSPanelControllerBase2 = SecQSPanelControllerBase.this;
                        if (secQSPanelControllerBase2.mDetailRecord == tileRecord && (c20682 = secQSPanelControllerBase2.mDetailCallback) != null) {
                            c20682.getClass();
                            SecQSDetail.this.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetail$2$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SecQSDetail.C20682 c206822 = SecQSDetail.C20682.this;
                                    int i3 = i;
                                    int i4 = i2;
                                    c206822.getClass();
                                    int i5 = SecQSDetail.$r8$clinit;
                                    ScrollView scrollView = (ScrollView) SecQSDetail.this.findViewById(R.id.qs_detail_scroll);
                                    if (scrollView != null) {
                                        scrollView.scrollTo(i3, i4);
                                    }
                                }
                            });
                        }
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onShowDetail(boolean z) {
                        SecQSPanelControllerBase.this.showDetail(tileRecord, z);
                    }

                    @Override // com.android.systemui.plugins.qs.QSTile.Callback
                    public final void onStateChanged(QSTile.State state) {
                        tileRecord.tileView.onStateChanged(state);
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onToggleStateChanged(final boolean z) {
                        final SecQSDetail.C20682 c20682;
                        SecQSPanelControllerBase secQSPanelControllerBase2 = SecQSPanelControllerBase.this;
                        if (secQSPanelControllerBase2.mDetailRecord == tileRecord && (c20682 = secQSPanelControllerBase2.mDetailCallback) != null) {
                            c20682.getClass();
                            SecQSDetail.this.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetail.2.1
                                public final /* synthetic */ boolean val$state;

                                public AnonymousClass1(final boolean z2) {
                                    r2 = z2;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    SecQSDetail secQSDetail = SecQSDetail.this;
                                    boolean z2 = r2;
                                    DetailAdapter detailAdapter = secQSDetail.mDetailAdapter;
                                    secQSDetail.handleToggleStateChanged(z2, detailAdapter != null && detailAdapter.getToggleEnabled());
                                }
                            });
                        }
                    }

                    @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
                    public final void onUpdateDetail() {
                        SecQSPanelControllerBase secQSPanelControllerBase2 = SecQSPanelControllerBase.this;
                        Record record = secQSPanelControllerBase2.mDetailRecord;
                        TileRecord tileRecord2 = tileRecord;
                        if (record != tileRecord2) {
                            return;
                        }
                        secQSPanelControllerBase2.mHandler.obtainMessage(100, 0, 0, tileRecord2).sendToTarget();
                    }
                };
            }
        };
        qSPanelHost.mBarController = barController;
        this.mResourcePicker = secQSPanelResourcePicker;
    }

    private void switchToParent(View view, ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        viewGroup.removeView(view);
        viewGroup.addView(view);
    }

    public final void addBarItems() {
        ArrayList barItems = this.mQsPanelHost.getBarItems();
        if (barItems == null) {
            return;
        }
        barItems.forEach(new Consumer() { // from class: com.android.systemui.qs.SecQSPanelControllerBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SecQSPanelControllerBase.m1622$r8$lambda$X8r1Fwkz1Q6QfQQCpSy31GTu7M(SecQSPanelControllerBase.this, (BarItemImpl) obj);
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        QSPanelHost qSPanelHost = this.mQsPanelHost;
        qSPanelHost.getClass();
        printWriter.println("QSPanelHost:");
        printWriter.println("  Tile records:");
        qSPanelHost.mRecords.stream().filter(new QSPanelHost$$ExternalSyntheticLambda3(2)).forEach(new QSPanelHost$$ExternalSyntheticLambda6(1, printWriter, strArr));
    }

    public final void flipPageWithTile(String str) {
        int i;
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout == null || !(qSTileLayout instanceof PagedTileLayout)) {
            return;
        }
        PagedTileLayout pagedTileLayout = (PagedTileLayout) qSTileLayout;
        SecPagedTileLayout secPagedTileLayout = pagedTileLayout.mSecPagedTileLayout;
        Iterator it = ((List) secPagedTileLayout.tilesSupplier.get()).iterator();
        int i2 = 0;
        while (true) {
            if (!it.hasNext()) {
                i2 = -1;
                break;
            } else if (Intrinsics.areEqual(((TileRecord) it.next()).tile.getTileSpec(), str)) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 > 0) {
            int asInt = secPagedTileLayout.getColumnCountSupplier.getAsInt();
            ArrayList arrayList = (ArrayList) secPagedTileLayout.pagesSupplier.get();
            i = i2 / (asInt * (arrayList.size() == 0 ? 0 : ((TileLayout) arrayList.get(0)).mRows));
        } else {
            i = 0;
        }
        pagedTileLayout.setCurrentItem(i, false);
        final QSTileView qSTileView = (QSTileView) this.mQsPanelHost.mRecords.stream().filter(new QSPanelHost$$ExternalSyntheticLambda5(str, 2)).findFirst().map(new QSPanelHost$$ExternalSyntheticLambda0(8)).orElse(null);
        if (qSTileView != null) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.SecQSPanelControllerBase$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    QSTileView qSTileView2 = QSTileView.this;
                    qSTileView2.setPressed(true);
                    qSTileView2.setPressed(false);
                }
            }, 500L);
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

    public final QSTile getTile(DetailAdapter detailAdapter) {
        SecQSPanelController secQSPanelController = (SecQSPanelController) this;
        QSPanelHost qSPanelHost = this.mQsPanelHost;
        qSPanelHost.getClass();
        if (secQSPanelController.mDetailRecord instanceof TileRecord) {
            return (QSTile) qSPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda0(7)).filter(new QSPanelHost$$ExternalSyntheticLambda8(detailAdapter, 1)).findFirst().orElse(null);
        }
        return null;
    }

    public void onConfigurationChanged(Configuration configuration) {
        int i = ((SecQSPanel) this.mView).getContext().getResources().getConfiguration().orientation;
        RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("onConfigurationChanged currentOrientation = ", i, ",newConfig.orientation = "), configuration.orientation, "SecQSPanelControllerBase");
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.needToUpdate(configuration) || this.mOrientation != i) {
            this.mOrientation = i;
            updateResources();
            updatePaddingAndMargins();
            configurationState.update(configuration);
        }
        this.mDarkModelEasel.updateColors(configuration);
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
        updateResources();
        updatePaddingAndMargins();
        SecQSPanel secQSPanel = (SecQSPanel) this.mView;
        ((ArrayList) secQSPanel.mOnConfigurationChangedListeners).add(this.mOnConfigurationChangedListener);
        this.mQsPanelHost.setTiles();
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
        ArrayList arrayList = qSPanelHost.mRecords;
        arrayList.forEach(new QSPanelHost$$ExternalSyntheticLambda1(0));
        arrayList.clear();
        boolean isHeader = qSPanelHost.isHeader();
        QSHost qSHost = qSPanelHost.mQsHost;
        if (isHeader) {
            ((ArrayList) qSHost.getQQSTileHost().mCallbacks).remove(qSPanelHost);
        } else {
            qSHost.removeCallback(qSPanelHost);
        }
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.setListening(false, this.mUiEventLogger);
        }
        this.mDumpManager.unregisterDumpable(((SecQSPanel) this.mView).getDumpableTag());
    }

    public final void refreshAllTiles() {
        this.mQsPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda0(3)).filter(new QSPanelHost$$ExternalSyntheticLambda3(0)).forEach(new QSPanelHost$$ExternalSyntheticLambda1(1));
    }

    public void setExpanded(boolean z) {
        SecQSPanel.QSTileLayout qSTileLayout;
        if (this.mExpanded == z) {
            return;
        }
        this.mExpanded = z;
        int i = 0;
        if (!z && (qSTileLayout = this.mTileLayout) != null && (qSTileLayout instanceof PagedTileLayout)) {
            ((PagedTileLayout) qSTileLayout).setCurrentItem(0, false);
        }
        this.mQSLogger.logPanelExpanded(((SecQSPanel) this.mView).getDumpableTag(), z);
        this.mMetricsLogger.visibility(111, z);
        UiEventLogger uiEventLogger = this.mUiEventLogger;
        if (!z) {
            uiEventLogger.log(((SecQSPanel) this.mView).closePanelEvent());
            showDetail(this.mDetailRecord, false);
            return;
        }
        uiEventLogger.log(((SecQSPanel) this.mView).openPanelEvent());
        QSPanelHost qSPanelHost = this.mQsPanelHost;
        Stream map = qSPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda0(1)).map(new QSPanelHost$$ExternalSyntheticLambda0(2));
        MetricsLogger metricsLogger = qSPanelHost.mMetricsLogger;
        Objects.requireNonNull(metricsLogger);
        map.forEach(new QSPanelHost$$ExternalSyntheticLambda2(metricsLogger, i));
    }

    public final void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (this.mTileLayout != null) {
            this.mQSLogger.logAllTilesChangeListening(((SecQSPanel) this.mView).getDumpableTag(), (String) this.mQsPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda0(0)).collect(Collectors.joining(",")), z);
            this.mTileLayout.setListening(z, this.mUiEventLogger);
        }
        if (z) {
            refreshAllTiles();
        }
    }

    public final void showDetail(Record record, boolean z) {
        this.mHandler.obtainMessage(99, z ? 1 : 0, 0, record).sendToTarget();
    }

    public void updatePanelContents() {
        addBarItems();
    }

    public final void updateResources() {
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout == null) {
            return;
        }
        qSTileLayout.updateResources();
        updatePaddingAndMargins();
    }

    public void updatePaddingAndMargins() {
    }
}
