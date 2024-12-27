package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OngoingCardAdapter extends BaseAdapter {
    public final int customChipSidePadding;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public ViewGroup mCardExpandContents;
    public ViewGroup mCardParentLayout;
    public final Context mContext;
    public FrameLayout mDummyExpandedInfo;
    public LinearLayout mDummyNotiParentLayout;
    public FrameLayout mDummyRemoteContainer;
    public ImageView mDummySmallIcon;
    public float mDummyTextSize;
    public final NotificationRemoteInputManager remoteHandler;
    public final int topHeight;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public OngoingCardAdapter(Context context, IndicatorScaleGardener indicatorScaleGardener, NotificationRemoteInputManager notificationRemoteInputManager) {
        this.mContext = context;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.remoteHandler = notificationRemoteInputManager;
        this.customChipSidePadding = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side_for_custom);
        this.topHeight = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_top_height);
        this.mDummyTextSize = context.getResources().getDimension(R.dimen.ongoing_activity_chip_text_size);
        context.getResources().getDimension(R.dimen.ongoing_activity_chip_bg_radius);
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x01f5, code lost:
    
        if (r13 == null) goto L83;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bindView(android.view.View r12, int r13) {
        /*
            Method dump skipped, instructions count: 703
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardAdapter.bindView(android.view.View, int):void");
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        return OngoingActivityDataHelper.mOngoingActivityLists.size();
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        OngoingActivityDataHelper.INSTANCE.getClass();
        return OngoingActivityDataHelper.getDataByIndex(i);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        if (view != null && viewGroup != null) {
            viewGroup.removeView(view);
        }
        Intrinsics.checkNotNull(viewGroup);
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sec_ongoing_card_item_layout, viewGroup, false);
        bindView(inflate, i);
        return inflate;
    }
}
