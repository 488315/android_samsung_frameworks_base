package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

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
            /* JADX WARN: Removed duplicated region for block: B:52:0x00df  */
            @Override // java.util.function.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(Object obj) throws Resources.NotFoundException {
                SecQSPanelController secQSPanelController;
                SecQSPanelControllerBase.TileRecord tileRecord;
                int i = 0;
                Configuration configuration2 = configuration;
                int i2 = SecQSDetail.$r8$clinit;
                final SecQSDetailController secQSDetailController = ((SecQSDetailController$onConfigurationChangedListener$1) obj).this$0;
                boolean zApplyNewConfig = secQSDetailController.configChanges.applyNewConfig(secQSDetailController.getContext().getResources());
                SecQSDetail secQSDetail = secQSDetailController.view;
                if (zApplyNewConfig) {
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
                        boolean zShouldUseFullScreen = detailAdapter2 != null ? detailAdapter2.shouldUseFullScreen() : false;
                        DetailAdapter detailAdapter3 = secQSDetailController.detailAdapter;
                        Integer numValueOf = detailAdapter3 != null ? Integer.valueOf(detailAdapter3.getMetricsCategory()) : null;
                        if (!zShouldUseFullScreen) {
                            secQSDetailController.setDetailExtendedContainerHeight();
                            DetailAdapter detailAdapter4 = secQSDetailController.detailAdapter;
                            if (detailAdapter4 != null) {
                                secQSDetailController.setupDetailHeader(detailAdapter4);
                            }
                            secQSDetailController.updateHeaderProgress(false);
                        }
                        DetailAdapter detailAdapter5 = secQSDetailController.detailAdapter;
                        if (detailAdapter5 != null) {
                            View viewCreateDetailView = detailAdapter5.createDetailView(secQSDetailController.getContext(), (View) secQSDetailController.detailViews.get(numValueOf != null ? numValueOf.intValue() : 0), secQSDetailController.detailContent);
                            if (viewCreateDetailView != null) {
                                ViewGroup viewGroup = zShouldUseFullScreen ? secQSDetailController.toViewGroup(R.id.qs_detail_full_screen_container) : secQSDetailController.detailContent;
                                if (viewGroup != null) {
                                    viewGroup.removeAllViews();
                                    viewGroup.addView(viewCreateDetailView);
                                }
                                secQSDetailController.detailViews.put(numValueOf != null ? numValueOf.intValue() : 0, viewCreateDetailView);
                                DetailAdapter detailAdapter6 = secQSDetailController.detailAdapter;
                                if (detailAdapter6 != null) {
                                    secQSDetailController.setupDetailFooter(detailAdapter6);
                                }
                                secQSDetailController.updateMarginAndPadding();
                                if (!zShouldUseFullScreen) {
                                    secQSDetailController.updateDetailButtonText();
                                    if (secQSDetailController.isDNDTile()) {
                                        secQSDetailController.updateDndDetail();
                                    }
                                }
                            } else {
                                DetailAdapter detailAdapter7 = secQSDetailController.detailAdapter;
                                Log.e("SecQSDetailController", "Tile = " + ((Object) (detailAdapter7 != null ? detailAdapter7.getTitle() : null)) + " detailView is null");
                            }
                        }
                    }
                    SecQSDetailContentView secQSDetailContentView = secQSDetailController.detailContentParent;
                    if (secQSDetailContentView == null) {
                        secQSDetailContentView = null;
                    }
                    ColoredBGHelper coloredBGHelper = secQSDetailController.coloredBGHelper;
                    coloredBGHelper.setBackGroundDrawable(secQSDetailContentView, coloredBGHelper.getBGColor());
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
                    ((SecQSDetail) ((ViewController) secQSDetailController).mView).postDelayed(new Runnable() { // from class: com.android.systemui.qs.SecQSDetailController$onConfigurationChangedListener$1$updateResources$1
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            SecQSDetailController secQSDetailController2 = secQSDetailController;
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
                secQSDetail.setTranslationX(SecQSDetailController.isLargeScreen$6() ? secQSDetailController.resourcePicker.getQsFrameX() : 0.0f);
                secQSDetailController.updateMarginAndPadding();
            }
        });
    }
}
