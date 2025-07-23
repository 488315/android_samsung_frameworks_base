package com.android.systemui.qs;

import android.widget.ScrollView;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import java.util.function.Function;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSPanelControllerBase$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ SecQSDetailController f$0;

    public /* synthetic */ SecQSPanelControllerBase$$ExternalSyntheticLambda1(SecQSDetailController secQSDetailController) {
        this.f$0 = secQSDetailController;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        final SecQSDetailController secQSDetailController = this.f$0;
        final SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
        secQSDetailController.getClass();
        return new SQSTile.SCallback() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1
            public final boolean needToSkip() {
                return !Intrinsics.areEqual(secQSDetailController.currentRecord, SecQSPanelControllerBase.TileRecord.this);
            }

            @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
            public final void onScanStateChanged(final boolean z) {
                SecQSPanelControllerBase.TileRecord.this.getClass();
                if (needToSkip()) {
                    return;
                }
                final SecQSDetailController secQSDetailController2 = secQSDetailController;
                secQSDetailController2.view.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1$onScanStateChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecQSDetailController secQSDetailController3 = SecQSDetailController.this;
                        boolean z2 = z;
                        int i = SecQSDetailController.$r8$clinit;
                        if (secQSDetailController3.scanState == z2) {
                            return;
                        }
                        secQSDetailController3.scanState = z2;
                        secQSDetailController3.updateHeaderProgress(z2);
                    }
                });
            }

            @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
            public final void onScrollToDetail(final int i, final int i2) {
                if (needToSkip()) {
                    return;
                }
                final SecQSDetailController secQSDetailController2 = secQSDetailController;
                secQSDetailController2.view.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1$onScrollToDetail$1
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
                final SecQSDetailController secQSDetailController2 = secQSDetailController;
                CurrentTilesInteractor currentTilesInteractor = secQSDetailController2.interactor;
                final SecQSPanelControllerBase.TileRecord tileRecord2 = SecQSPanelControllerBase.TileRecord.this;
                if (currentTilesInteractor.isLargeBarTile(tileRecord2.tile.getTileSpec()) && tileRecord2.tileView.getClass().getSimpleName().equals("NoLabelTileView")) {
                    return;
                }
                secQSDetailController2.view.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1$onShowDetail$1
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
                final SecQSDetailController secQSDetailController2 = secQSDetailController;
                secQSDetailController2.view.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$createTileCallback$1$onToggleStateChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecQSDetailController secQSDetailController3 = SecQSDetailController.this;
                        boolean z2 = z;
                        DetailAdapter detailAdapter = secQSDetailController3.detailAdapter;
                        boolean z3 = false;
                        if (detailAdapter != null && detailAdapter.getToggleEnabled()) {
                            z3 = true;
                        }
                        SecQSDetailController.access$handleToggleStateChanged(secQSDetailController3, z2, z3);
                    }
                });
            }

            @Override // com.android.systemui.plugins.qs.SQSTile.SCallback
            public final void onUpdateDetail() {
                if (needToSkip()) {
                    return;
                }
                final SecQSDetailController secQSDetailController2 = secQSDetailController;
                SecQSDetail secQSDetail = secQSDetailController2.view;
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
}
