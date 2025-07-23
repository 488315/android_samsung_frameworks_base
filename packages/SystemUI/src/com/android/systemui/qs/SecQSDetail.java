package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.animator.SecQSImplAnimatorBase;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecQSDetail extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final List mOnConfigurationChangedListeners;

    public SecQSDetail(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnConfigurationChangedListeners = new ArrayList();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(final Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((ArrayList) this.mOnConfigurationChangedListeners).forEach(new Consumer() { // from class: com.android.systemui.qs.SecQSDetail$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SecQSPanelController secQSPanelController;
                SecQSPanelControllerBase.TileRecord tileRecord;
                View view;
                int i = 0;
                Configuration configuration2 = configuration;
                int i2 = SecQSDetail.$r8$clinit;
                final SecQSDetailController secQSDetailController = ((SecQSDetailController$onConfigurationChangedListener$1) obj).this$0;
                boolean applyNewConfig = secQSDetailController.configChanges.applyNewConfig(secQSDetailController.getContext().getResources());
                SecQSDetail secQSDetail = secQSDetailController.view;
                if (applyNewConfig) {
                    secQSDetail.removeAllViews();
                    secQSDetail.addView((FrameLayout) LayoutInflater.from(secQSDetail.getContext()).inflate(R.layout.sec_qs_detail_container, (ViewGroup) secQSDetail, false));
                    secQSDetailController.updateViews(secQSDetail);
                    DetailAdapter detailAdapter = secQSDetailController.detailAdapter;
                    if (detailAdapter != null) {
                        secQSDetailController.setupDetailHeader(detailAdapter);
                        secQSDetailController.setupDetailFooter(detailAdapter);
                    }
                    secQSDetailController.updateDetailButtonText();
                    secQSDetailController.updateDetailHeader();
                    secQSDetailController.detailViews.clear();
                    secQSDetail.requestLayout();
                    if (secQSDetailController.qsAnimatorManager != null && SecQSImplAnimatorBase.isDetailVisible()) {
                        DetailAdapter detailAdapter2 = secQSDetailController.detailAdapter;
                        boolean shouldUseFullScreen = detailAdapter2 != null ? detailAdapter2.shouldUseFullScreen() : false;
                        DetailAdapter detailAdapter3 = secQSDetailController.detailAdapter;
                        Integer valueOf = detailAdapter3 != null ? Integer.valueOf(detailAdapter3.getMetricsCategory()) : null;
                        if (!shouldUseFullScreen) {
                            secQSDetailController.setDetailExtendedContainerHeight();
                            DetailAdapter detailAdapter4 = secQSDetailController.detailAdapter;
                            if (detailAdapter4 != null) {
                                secQSDetailController.setupDetailHeader(detailAdapter4);
                            }
                            secQSDetailController.updateHeaderProgress(false);
                        }
                        DetailAdapter detailAdapter5 = secQSDetailController.detailAdapter;
                        if (detailAdapter5 != null) {
                            View createDetailView = detailAdapter5.createDetailView(secQSDetailController.getContext(), (View) secQSDetailController.detailViews.get(valueOf != null ? valueOf.intValue() : 0), secQSDetailController.detailContent);
                            if (createDetailView != null) {
                                ViewGroup viewGroup = shouldUseFullScreen ? secQSDetailController.toViewGroup(R.id.qs_detail_full_screen_container) : secQSDetailController.detailContent;
                                if (viewGroup != null) {
                                    viewGroup.removeAllViews();
                                    viewGroup.addView(createDetailView);
                                }
                                secQSDetailController.detailViews.put(valueOf != null ? valueOf.intValue() : 0, createDetailView);
                                DetailAdapter detailAdapter6 = secQSDetailController.detailAdapter;
                                if (detailAdapter6 != null) {
                                    secQSDetailController.setupDetailFooter(detailAdapter6);
                                }
                                secQSDetailController.updateMarginAndPadding();
                                if (!shouldUseFullScreen) {
                                    secQSDetailController.updateDetailButtonText();
                                    if (secQSDetailController.isDNDTile()) {
                                        secQSDetailController.updateDndDetail();
                                    }
                                }
                            }
                        }
                        DetailAdapter detailAdapter7 = secQSDetailController.detailAdapter;
                        Log.e("SecQSDetailController", "Tile = " + ((Object) (detailAdapter7 != null ? detailAdapter7.getTitle() : null)) + " detailView is null");
                    }
                }
                DetailAdapter detailAdapter8 = secQSDetailController.detailAdapter;
                if (detailAdapter8 == null || !detailAdapter8.shouldUseFullScreen()) {
                    secQSDetailController.updateDetailButtonText();
                    SparseArray sparseArray = secQSDetailController.detailViews;
                    int size = sparseArray.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        sparseArray.keyAt(i3);
                        ((View) sparseArray.valueAt(i3)).dispatchConfigurationChanged(configuration2);
                    }
                }
                configuration2.getClass();
                if (secQSDetailController.oldOrientation == configuration2.orientation) {
                    return;
                }
                if (secQSDetailController.qsAnimatorManager != null && SecQSImplAnimatorBase.isDetailVisible()) {
                    view = ((ViewController) secQSDetailController).mView;
                    ((SecQSDetail) view).postDelayed(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$onConfigurationChangedListener$1$updateResources$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SecQSDetailController secQSDetailController2 = SecQSDetailController.this;
                            int i4 = SecQSDetailController.$r8$clinit;
                            secQSDetailController2.setDetailExtendedContainerHeight();
                        }
                    }, 50L);
                }
                secQSDetailController.updateDetailHeader();
                DetailAdapter detailAdapter9 = secQSDetailController.detailAdapter;
                if (detailAdapter9 != null) {
                    secQSDetailController.updateDetailTitle(detailAdapter9.getToggleState(), detailAdapter9.getTitle());
                    SecQSDetailController.access$handleUpdatingDetail(secQSDetailController, detailAdapter9);
                    secQSDetailController.updateVisibility(detailAdapter9);
                }
                String str = secQSDetailController.detailTileSpec;
                if (str != null && (secQSPanelController = secQSDetailController.panelController) != null && (tileRecord = (SecQSPanelControllerBase.TileRecord) secQSPanelController.mQsPanelHost.mRecords.stream().filter(new QSPanelHost$$ExternalSyntheticLambda17(str, i)).findFirst().orElse(null)) != null && !Intrinsics.areEqual(tileRecord, secQSDetailController.currentRecord)) {
                    secQSDetailController.currentRecord = tileRecord;
                }
                secQSDetailController.oldOrientation = configuration2.orientation;
                secQSDetail.setTranslationX(SecQSDetailController.isLargeScreen$5() ? secQSDetailController.resourcePicker.getQsFrameX() : 0.0f);
                secQSDetailController.updateMarginAndPadding();
            }
        });
    }
}
