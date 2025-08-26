package com.android.systemui.qp;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.util.DeviceState;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SubscreenParentLayout extends FrameLayout {
    public final Context mContext;
    public boolean mIsRotation180;
    public FrameLayout mMediaPanelView;
    public final SubscreenParentLayout$mQSHostCallback$1 mQSHostCallback;
    public int mediaCol;
    public int mediaRow;
    public QSHost qsHost;

    public /* synthetic */ SubscreenParentLayout(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final int calculateXPos(Resources resources) {
        int dimensionPixelSize = (resources.getDimensionPixelSize(R.dimen.cover_screen_horizontal_margin) * this.mediaCol) + (resources.getDimensionPixelSize(R.dimen.subscreen_qs_tile_icon_size) * this.mediaCol);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        Context context = this.mContext;
        secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
        int screenWidth = ((DeviceState.getScreenWidth(context) - ((getResources().getDimensionPixelSize(R.dimen.cover_screen_horizontal_margin) * 3) + (getResources().getDimensionPixelSize(R.dimen.subscreen_qs_tile_icon_size) * 4))) / 2) + dimensionPixelSize;
        return resources.getConfiguration().getLayoutDirection() == 1 ? screenWidth * (-1) : screenWidth;
    }

    public final int calculateYPos(Resources resources) {
        return (resources.getDimensionPixelSize(R.dimen.cover_screen_vertical_margin) * this.mediaRow) + (resources.getDimensionPixelSize(R.dimen.subscreen_qs_tile_icon_size) * this.mediaRow) + (this.mIsRotation180 ? resources.getDimensionPixelSize(R.dimen.cover_screen_page_layout_top_margin_flexmode) : resources.getDimensionPixelSize(R.dimen.cover_screen_page_layout_top_margin));
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        SecMediaHost secMediaHost = (SecMediaHost) Dependency.sDependency.getDependencyInner(SecMediaHost.class);
        if (secMediaHost != null) {
            FrameLayout frameLayout = this.mMediaPanelView;
            if (frameLayout == null) {
                frameLayout = null;
            }
            frameLayout.setTranslationX(calculateXPos(this.mContext.getResources()));
            MediaType mediaType = MediaType.COVER_QS;
            secMediaHost.removeMediaFrame(mediaType);
            FrameLayout frameLayout2 = this.mMediaPanelView;
            if (frameLayout2 == null) {
                frameLayout2 = null;
            }
            frameLayout2.removeAllViews();
            FrameLayout frameLayout3 = this.mMediaPanelView;
            secMediaHost.addMediaFrame(mediaType, frameLayout3 != null ? frameLayout3 : null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        QSHost qSHost = this.qsHost;
        if (qSHost != null) {
            qSHost.removeCallback(this.mQSHostCallback);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.subscreen_media_player_root_view);
        this.mMediaPanelView = frameLayout;
        if (frameLayout == null) {
            frameLayout = null;
        }
        frameLayout.setTranslationX(calculateXPos(this.mContext.getResources()));
        FrameLayout frameLayout2 = this.mMediaPanelView;
        if (frameLayout2 == null) {
            frameLayout2 = null;
        }
        frameLayout2.setTranslationY(calculateYPos(this.mContext.getResources()));
        SecMediaHost secMediaHost = (SecMediaHost) Dependency.sDependency.getDependencyInner(SecMediaHost.class);
        if (secMediaHost != null) {
            MediaType mediaType = MediaType.COVER_QS;
            secMediaHost.removeMediaFrame(mediaType);
            FrameLayout frameLayout3 = this.mMediaPanelView;
            secMediaHost.addMediaFrame(mediaType, frameLayout3 != null ? frameLayout3 : null);
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qp.SubscreenParentLayout$mQSHostCallback$1] */
    public SubscreenParentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mediaRow = 2;
        this.mediaCol = 1;
        this.mQSHostCallback = new QSHost.Callback() { // from class: com.android.systemui.qp.SubscreenParentLayout$mQSHostCallback$1
            @Override // com.android.systemui.qs.QSHost.Callback
            public final void onTilesChanged() {
                SubscreenParentLayout subscreenParentLayout = this.this$0;
                QSHost qSHost = subscreenParentLayout.qsHost;
                int size = qSHost != null ? ((ArrayList) qSHost.getTiles()).size() : 9;
                int i = size / 4;
                subscreenParentLayout.mediaRow = i;
                int i2 = size % 4;
                subscreenParentLayout.mediaCol = i2;
                if (i2 > 1) {
                    subscreenParentLayout.mediaRow = i + 1;
                    subscreenParentLayout.mediaCol = 0;
                }
                FrameLayout frameLayout = subscreenParentLayout.mMediaPanelView;
                if (frameLayout == null) {
                    frameLayout = null;
                }
                frameLayout.setTranslationX(subscreenParentLayout.calculateXPos(subscreenParentLayout.mContext.getResources()));
                FrameLayout frameLayout2 = subscreenParentLayout.mMediaPanelView;
                (frameLayout2 != null ? frameLayout2 : null).setTranslationY(subscreenParentLayout.calculateYPos(subscreenParentLayout.mContext.getResources()));
            }
        };
    }
}
