package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                int i = 0;
                Configuration configuration2 = configuration;
                int i2 = SecQSDetail.$r8$clinit;
                SecQSDetailController secQSDetailController = ((SecQSDetailController$onConfigurationChangedListener$1) obj).this$0;
                if (secQSDetailController.configChanges.applyNewConfig(secQSDetailController.getContext().getResources())) {
                    SecQSDetail secQSDetail = secQSDetailController.view;
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
                }
                DetailAdapter detailAdapter2 = secQSDetailController.detailAdapter;
                if (detailAdapter2 == null || !detailAdapter2.shouldUseFullScreen()) {
                    secQSDetailController.updateDetailButtonText();
                    SparseArray sparseArray = secQSDetailController.detailViews;
                    int size = sparseArray.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        sparseArray.keyAt(i3);
                        ((View) sparseArray.valueAt(i3)).dispatchConfigurationChanged(configuration2);
                    }
                }
                Intrinsics.checkNotNull(configuration2);
                if (secQSDetailController.oldOrientation == configuration2.orientation) {
                    return;
                }
                secQSDetailController.setDetailExtendedContainerHeight();
                secQSDetailController.updateDetailHeader();
                DetailAdapter detailAdapter3 = secQSDetailController.detailAdapter;
                if (detailAdapter3 != null) {
                    secQSDetailController.updateDetailTitle(detailAdapter3.getToggleState(), detailAdapter3.getTitle());
                    SecQSDetailController.access$handleUpdatingDetail(secQSDetailController, detailAdapter3);
                    secQSDetailController.updateVisibility(detailAdapter3);
                }
                String str = secQSDetailController.detailTileSpec;
                if (str != null && (secQSPanelController = secQSDetailController.panelController) != null && (tileRecord = (SecQSPanelControllerBase.TileRecord) secQSPanelController.mQsPanelHost.mRecords.stream().filter(new QSPanelHost$$ExternalSyntheticLambda17(str, i)).findFirst().orElse(null)) != null && !Intrinsics.areEqual(tileRecord, secQSDetailController.currentRecord)) {
                    secQSDetailController.currentRecord = tileRecord;
                }
                secQSDetailController.oldOrientation = configuration2.orientation;
                secQSDetailController.updateMarginAndPadding();
            }
        });
    }
}
