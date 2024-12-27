package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.ongoingactivity.media.OngoingMediaResourceUtils;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.LinkedList;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class OngoingChipAdapter extends RecyclerView.Adapter {
    public final int availableSpace;
    public final float bgRadius;
    public final int customChipSidePadding;
    public int enableMaxWidth;
    public final float expandedTextSize;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public boolean isKeyguardGoneNow;
    public final Context mContext;
    public Pair marqueePair;
    public boolean shouldShowChipOnly;
    public final int topHeight;
    public ViewGroup viewGroup;
    public final String TAG = "{OngoingChipAdapter}";
    public final int mMaxItemCount = 2;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ChipViewHolder extends RecyclerView.ViewHolder {
        public final int boldFifthUnitWidth;
        public final int boldFourthUnitWidth;
        public final int boldSixthUnitWidth;
        public final int chipOffset;
        public final int fifthUnitWidth;
        public final int fourthUnitWidth;
        public final IndicatorScaleGardener indicatorScaleGardener;
        public final int infoTextExtra;
        public final FrameLayout mExpandedInfo;
        public final LinearLayout mNotiParentLayout;
        public final FrameLayout mRemoteContainer;
        public final View mRootLayout;
        public final ImageView mSmallIcon;
        public final int maximumWidth;
        public final int minimumWidth;
        public final int sixthUnitWidth;

        public ChipViewHolder(View view, IndicatorScaleGardener indicatorScaleGardener) {
            super(view);
            this.indicatorScaleGardener = indicatorScaleGardener;
            this.mRootLayout = view;
            View findViewById = view.findViewById(R.id.capsule_item_top_layout);
            Intrinsics.checkNotNull(findViewById);
            this.mNotiParentLayout = (LinearLayout) findViewById;
            View findViewById2 = view.findViewById(R.id.capsule_item_app_icon);
            Intrinsics.checkNotNull(findViewById2);
            ImageView imageView = (ImageView) findViewById2;
            this.mSmallIcon = imageView;
            View findViewById3 = view.findViewById(R.id.capsule_item_noti_expanded_info);
            Intrinsics.checkNotNull(findViewById3);
            FrameLayout frameLayout = (FrameLayout) findViewById3;
            this.mExpandedInfo = frameLayout;
            View findViewById4 = view.findViewById(R.id.capsule_remote_container);
            Intrinsics.checkNotNull(findViewById4);
            this.mRemoteContainer = (FrameLayout) findViewById4;
            this.minimumWidth = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_min_width);
            this.maximumWidth = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_max_width);
            this.infoTextExtra = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_info_text_space_extra);
            this.sixthUnitWidth = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_sixth_unit);
            this.fifthUnitWidth = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_fifth_unit);
            this.fourthUnitWidth = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_fourth_unit);
            this.boldSixthUnitWidth = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bold_sixth_unit);
            this.boldFifthUnitWidth = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bold_fifth_unit);
            this.boldFourthUnitWidth = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bold_fourth_unit);
            this.chipOffset = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset);
            float f = indicatorScaleGardener.getLatestScaleModel(view.getContext()).ratio;
            int roundToInt = MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_size) * f);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = roundToInt;
            layoutParams.height = roundToInt;
            ((LinearLayout.LayoutParams) frameLayout.getLayoutParams()).setMarginStart(MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_margin_between) * f));
            this.minimumWidth = MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_min_width) * f);
            this.maximumWidth = MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_max_width) * f);
            this.infoTextExtra = MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_info_text_space_extra) * f);
            this.chipOffset = MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset) * f);
        }

        public final void setChipMode() {
            float f = this.indicatorScaleGardener.getLatestScaleModel(this.mRootLayout.getContext()).ratio;
            int roundToInt = MathKt__MathJVMKt.roundToInt(this.mRootLayout.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side) * f);
            this.mNotiParentLayout.setPadding(roundToInt, 0, roundToInt, 0);
            this.mNotiParentLayout.getLayoutParams().width = MathKt__MathJVMKt.roundToInt(this.mRootLayout.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_min_width) * f);
            this.mExpandedInfo.removeAllViews();
            this.mExpandedInfo.setVisibility(8);
        }
    }

    public OngoingChipAdapter(Context context, IndicatorScaleGardener indicatorScaleGardener) {
        this.mContext = context;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.topHeight = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_top_height);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_second_height);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bottom_height);
        this.bgRadius = context.getResources().getDimension(R.dimen.ongoing_activity_chip_bg_radius);
        this.expandedTextSize = context.getResources().getDimension(R.dimen.ongoing_activity_chip_text_size);
        this.availableSpace = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_available_space_medium);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_max_width);
        this.customChipSidePadding = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side_for_custom);
        float f = indicatorScaleGardener.getLatestScaleModel(context).ratio;
        this.topHeight = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_top_height) * f);
        MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_second_height) * f);
        MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bottom_height) * f);
        this.availableSpace = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_available_space_medium) * f);
        MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_max_width) * f);
        this.bgRadius = context.getResources().getDimension(R.dimen.ongoing_activity_chip_bg_radius) * f;
        this.expandedTextSize = context.getResources().getDimension(R.dimen.ongoing_activity_chip_text_size) * f;
        this.customChipSidePadding = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side_for_custom) * f);
        notifyDataSetChanged();
    }

    public final ColorStateList getChipBg(int i, int i2) {
        if (i == getItemCount() - 1) {
            ColorStateList valueOf = ColorStateList.valueOf(i2);
            Intrinsics.checkNotNull(valueOf);
            return valueOf;
        }
        ColorStateList withAlpha = ColorStateList.valueOf(i2).withAlpha(102);
        Intrinsics.checkNotNull(withAlpha);
        return withAlpha;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        return Math.min(OngoingActivityDataHelper.mOngoingActivityLists.size(), this.mMaxItemCount);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TextView textView;
        int i2;
        ChipViewHolder chipViewHolder = (ChipViewHolder) viewHolder;
        String m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onBindViewHolder() notificationArrayList.position = ");
        String str = this.TAG;
        Log.i(str, m);
        OngoingActivityDataHelper.INSTANCE.getClass();
        LinkedList linkedList = OngoingActivityDataHelper.mOngoingActivityLists;
        if (linkedList.size() == 0) {
            Log.i(str, "onBindViewHolder : ongoingActivity is null ##");
            return;
        }
        OngoingActivityData dataByIndex = OngoingActivityDataHelper.getDataByIndex(0);
        int itemCount = getItemCount() - 1;
        OngoingMediaResourceUtils ongoingMediaResourceUtils = OngoingMediaResourceUtils.INSTANCE;
        Context context = this.mContext;
        int i3 = dataByIndex.mChipBackground;
        int mediaCardPrimaryInfoColor = OngoingMediaResourceUtils.getMediaCardUiType$default(ongoingMediaResourceUtils, i3).getMediaCardPrimaryInfoColor(context);
        if (dataByIndex.mExpandedChipView != null && i == itemCount) {
            chipViewHolder.mNotiParentLayout.setVisibility(8);
            chipViewHolder.mRemoteContainer.setVisibility(0);
            chipViewHolder.mRemoteContainer.removeAllViews();
            RemoteViews remoteViews = dataByIndex.mExpandedChipView;
            Intrinsics.checkNotNull(remoteViews);
            View apply = remoteViews.apply(this.mContext, null);
            float f = this.indicatorScaleGardener.getLatestScaleModel(this.mContext).ratio;
            apply.setScaleX(f);
            apply.setScaleY(f);
            chipViewHolder.mRemoteContainer.addView(apply);
            FrameLayout frameLayout = chipViewHolder.mRemoteContainer;
            int i4 = this.customChipSidePadding;
            frameLayout.setPadding(i4, 0, i4, 0);
            chipViewHolder.mRemoteContainer.setBackgroundTintList(getChipBg(i, i3));
            chipViewHolder.mRemoteContainer.getLayoutParams().height = this.topHeight;
            FrameLayout frameLayout2 = chipViewHolder.mRemoteContainer;
            ColorStateList chipBg = getChipBg(i, i3);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(0);
            gradientDrawable.setCornerRadius(this.bgRadius);
            frameLayout2.setBackground(gradientDrawable);
            frameLayout2.setBackgroundTintList(chipBg);
            return;
        }
        chipViewHolder.mRemoteContainer.removeAllViews();
        chipViewHolder.mRemoteContainer.setVisibility(8);
        chipViewHolder.mNotiParentLayout.setVisibility(0);
        if (i == itemCount) {
            Icon icon = dataByIndex.mChipIcon;
            if (icon != null) {
                chipViewHolder.mSmallIcon.setImageIcon(icon);
            } else {
                Icon icon2 = dataByIndex.mCardIcon;
                if (icon2 != null) {
                    chipViewHolder.mSmallIcon.setImageIcon(icon2);
                }
            }
            chipViewHolder.mSmallIcon.setVisibility(0);
            if (ContrastColorUtil.getInstance(this.mContext).isGrayscaleIcon(chipViewHolder.mSmallIcon.getDrawable())) {
                chipViewHolder.mSmallIcon.setImageTintList(ColorStateList.valueOf(mediaCardPrimaryInfoColor));
            } else {
                chipViewHolder.mSmallIcon.setImageTintList(null);
            }
        } else {
            chipViewHolder.mSmallIcon.setVisibility(4);
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(this.enableMaxWidth, this.availableSpace, "hasAvailableSpaceToExpand - ", " : ", " : "), this.shouldShowChipOnly, str);
        if (this.enableMaxWidth < this.availableSpace || this.shouldShowChipOnly || i != itemCount) {
            chipViewHolder.setChipMode();
        } else {
            if (dataByIndex.mExpandedChipText != null) {
                TextView textView2 = new TextView(this.mContext);
                CharSequence charSequence = dataByIndex.mExpandedChipText;
                Intrinsics.checkNotNull(charSequence);
                textView2.setText(charSequence);
                textView = textView2;
            } else {
                RemoteViews remoteViews2 = dataByIndex.mChronometerView;
                if (remoteViews2 != null) {
                    Chronometer chronometer = (Chronometer) remoteViews2.apply(this.mContext, null);
                    chronometer.setFormat("%s");
                    chronometer.hidden_semSetMilliSecondCount(0);
                    textView = chronometer;
                } else {
                    TextView textView3 = new TextView(this.mContext);
                    textView3.setText(dataByIndex.mPrimaryInfo);
                    textView = textView3;
                }
            }
            textView.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
            textView.setSingleLine(true);
            textView.setTextColor(mediaCardPrimaryInfoColor);
            textView.setTextSize(0, this.expandedTextSize);
            textView.setHorizontalFadingEdgeEnabled(true);
            int i5 = this.enableMaxWidth;
            if (chipViewHolder.mExpandedInfo.getChildCount() > 0) {
                chipViewHolder.mExpandedInfo.removeAllViews();
            }
            boolean z = textView.getContext().getResources().getConfiguration().fontWeightAdjustment == 300;
            Context context2 = chipViewHolder.mRootLayout.getContext();
            IndicatorScaleGardener indicatorScaleGardener = chipViewHolder.indicatorScaleGardener;
            float f2 = indicatorScaleGardener.getLatestScaleModel(context2).ratio;
            chipViewHolder.mNotiParentLayout.setPadding(MathKt__MathJVMKt.roundToInt(chipViewHolder.mRootLayout.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_padding_start) * f2), 0, MathKt__MathJVMKt.roundToInt(chipViewHolder.mRootLayout.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_padding_end) * f2), 0);
            chipViewHolder.mNotiParentLayout.getLayoutParams().width = -2;
            int i6 = linkedList.size() > 0 ? chipViewHolder.chipOffset : 0;
            textView.measure(0, 0);
            int measuredWidth = textView.getMeasuredWidth();
            int i7 = chipViewHolder.maximumWidth;
            if (measuredWidth <= i7 && (i2 = (i5 - chipViewHolder.infoTextExtra) - i6) <= i7 && i2 > (i7 = chipViewHolder.minimumWidth)) {
                i7 = i2;
            }
            textView.setMaxWidth(i7);
            textView.setElegantTextHeight(false);
            boolean z2 = textView instanceof Chronometer;
            if (z2) {
                textView.measure(0, 0);
                Chronometer chronometer2 = (Chronometer) textView;
                int i8 = (int) ((chronometer2.getText().length() <= 5 ? z ? chipViewHolder.boldFourthUnitWidth : chipViewHolder.fourthUnitWidth : chronometer2.getText().length() <= 7 ? z ? chipViewHolder.boldFifthUnitWidth : chipViewHolder.fifthUnitWidth : chronometer2.getText().length() <= 8 ? z ? chipViewHolder.boldSixthUnitWidth : chipViewHolder.sixthUnitWidth : 0) * indicatorScaleGardener.getLatestScaleModel(chipViewHolder.mRootLayout.getContext()).ratio);
                if (i8 > i7) {
                    chipViewHolder.setChipMode();
                } else {
                    chipViewHolder.mExpandedInfo.addView(textView, i8, chronometer2.getMeasuredHeight());
                    chipViewHolder.mExpandedInfo.setVisibility(0);
                }
            } else {
                chipViewHolder.mExpandedInfo.addView(textView);
                chipViewHolder.mExpandedInfo.setVisibility(0);
            }
            if (!z2) {
                this.marqueePair = new Pair(textView, dataByIndex);
            }
        }
        chipViewHolder.mNotiParentLayout.setBackgroundTintList(getChipBg(i, i3));
        chipViewHolder.mNotiParentLayout.getLayoutParams().height = this.topHeight;
        LinearLayout linearLayout = chipViewHolder.mNotiParentLayout;
        ColorStateList chipBg2 = getChipBg(i, i3);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setShape(0);
        gradientDrawable2.setCornerRadius(this.bgRadius);
        linearLayout.setBackground(gradientDrawable2);
        linearLayout.setBackgroundTintList(chipBg2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        LayoutInflater from = LayoutInflater.from(this.mContext);
        this.viewGroup = viewGroup;
        Log.i(this.TAG, "onCreateViewHolder() ");
        return new ChipViewHolder(from.inflate(R.layout.sec_ongoing_activity_chip_item, viewGroup, false), this.indicatorScaleGardener);
    }
}
