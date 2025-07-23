package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.AttributeSet;
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
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.DrawableKt;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.ongoingactivity.media.OngoingMediaResourceUtils;
import com.sec.ims.volte2.data.VolteConstants;
import java.lang.reflect.Field;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Pair;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingChipAdapter extends RecyclerView.Adapter {
    public final int availableSpace;
    public final float bgRadius;
    public final int customChipSidePadding;
    public int enableMaxWidth;
    public final float expandedTextSize;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final int infoTextTotalMargin;
    public boolean isKeyguardGoneNow;
    public final Context mContext;
    public final int marqueeLimitedWidth;
    public Pair marqueePair;
    public MarqueeState marqueeState;
    public final int maxChipWidth;
    public final int maximumWidth;
    public final NotificationIconAreaController notificationIconAreaController;
    public final int notificationIconWidth;
    public final int shadowWidth;
    public boolean shouldShowChipOnly;
    public final int sportScoreMaxWidth;
    public final int topHeight;
    public ViewGroup viewGroup;
    public final String TAG = "{OngoingChipAdapter}";
    public final int mMaxItemCount = 2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChipViewHolder extends RecyclerView.ViewHolder {
        public final String TAG;
        public final IndicatorScaleGardener indicatorScaleGardener;
        public final int infoTextExtra;
        public final FrameLayout mExpandedInfo;
        public final LinearLayout mNotiParentLayout;
        public final FrameLayout mRemoteContainer;
        public final View mRootLayout;
        public final ImageView mSmallIcon;
        public final int maximumWidth;

        public ChipViewHolder(View view, IndicatorScaleGardener indicatorScaleGardener) {
            super(view);
            this.indicatorScaleGardener = indicatorScaleGardener;
            this.TAG = "{OngoingChipViewHolder}";
            this.mRootLayout = view;
            View findViewById = view.findViewById(R.id.capsule_item_top_layout);
            findViewById.getClass();
            this.mNotiParentLayout = (LinearLayout) findViewById;
            View findViewById2 = view.findViewById(R.id.capsule_item_app_icon);
            findViewById2.getClass();
            ImageView imageView = (ImageView) findViewById2;
            this.mSmallIcon = imageView;
            View findViewById3 = view.findViewById(R.id.capsule_item_noti_expanded_info);
            findViewById3.getClass();
            FrameLayout frameLayout = (FrameLayout) findViewById3;
            this.mExpandedInfo = frameLayout;
            View findViewById4 = view.findViewById(R.id.capsule_remote_container);
            findViewById4.getClass();
            this.mRemoteContainer = (FrameLayout) findViewById4;
            view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_min_width);
            this.maximumWidth = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_max_width);
            this.infoTextExtra = view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_info_text_space_extra);
            view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset);
            view.getResources().getDimensionPixelSize(R.dimen.notification_dnd_status_icon_size);
            float f = indicatorScaleGardener.getLatestScaleModel(view.getContext()).ratio;
            int roundToInt = MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_size) * f);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = roundToInt;
            layoutParams.height = roundToInt;
            ((LinearLayout.LayoutParams) frameLayout.getLayoutParams()).setMarginStart(MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_margin_between) * f));
            MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_min_width) * f);
            this.maximumWidth = MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_max_width) * f);
            this.infoTextExtra = MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_info_text_space_extra) * f);
            MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset) * f);
            MathKt__MathJVMKt.roundToInt(view.getResources().getDimensionPixelSize(R.dimen.notification_dnd_status_icon_size) * f);
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

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class MarqueeState {
        public static final /* synthetic */ MarqueeState[] $VALUES;
        public static final MarqueeState INIT;
        public static final MarqueeState RUN;
        public static final MarqueeState WAIT_FINISH;

        static {
            MarqueeState marqueeState = new MarqueeState("INIT", 0);
            INIT = marqueeState;
            MarqueeState marqueeState2 = new MarqueeState("RUN", 1);
            RUN = marqueeState2;
            MarqueeState marqueeState3 = new MarqueeState("WAIT_FINISH", 2);
            WAIT_FINISH = marqueeState3;
            MarqueeState[] marqueeStateArr = {marqueeState, marqueeState2, marqueeState3};
            $VALUES = marqueeStateArr;
            EnumEntriesKt.enumEntries(marqueeStateArr);
        }

        private MarqueeState(String str, int i) {
        }

        public static MarqueeState valueOf(String str) {
            return (MarqueeState) Enum.valueOf(MarqueeState.class, str);
        }

        public static MarqueeState[] values() {
            return (MarqueeState[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MarqueeTextView extends TextView {
        public OngoingChipAdapter$marqueeIfNeeded$1$1 mMarqueeEndListener;

        public MarqueeTextView(Context context) {
            this(context, null, 0, 0, 14, null);
        }

        @Override // android.widget.TextView, android.view.View
        public final void onDraw(Canvas canvas) {
            Field field;
            Object obj;
            OngoingChipAdapter$marqueeIfNeeded$1$1 ongoingChipAdapter$marqueeIfNeeded$1$1;
            super.onDraw(canvas);
            Field field2 = null;
            try {
                Field declaredField = TextView.class.getDeclaredField("mMarquee");
                declaredField.setAccessible(true);
                obj = declaredField.get(this);
                if (obj != null) {
                    try {
                        field2 = obj.getClass().getDeclaredField("mStatus");
                        if (field2 != null) {
                            field2.setAccessible(true);
                        }
                    } catch (Exception e) {
                        e = e;
                        field = field2;
                        field2 = obj;
                        Log.w("{OngoingChipAdapterMarqueeTextView}", "onDraw: ", e);
                        Field field3 = field;
                        obj = field2;
                        field2 = field3;
                        if (obj != null) {
                            return;
                        } else {
                            return;
                        }
                    }
                }
            } catch (Exception e2) {
                e = e2;
                field = null;
            }
            if (obj != null || field2 == null) {
                return;
            }
            try {
                if (field2.getInt(obj) != 0 || (ongoingChipAdapter$marqueeIfNeeded$1$1 = this.mMarqueeEndListener) == null) {
                    return;
                }
                ongoingChipAdapter$marqueeIfNeeded$1$1.onMarqueeEnd();
            } catch (Exception e3) {
                Log.w("{OngoingChipAdapterMarqueeTextView}", "onDraw: ", e3);
            }
        }

        public MarqueeTextView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0, 0, 12, null);
        }

        public MarqueeTextView(Context context, AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0, 8, null);
        }

        public /* synthetic */ MarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
        }

        public MarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
        }
    }

    public OngoingChipAdapter(Context context, IndicatorScaleGardener indicatorScaleGardener, NotificationIconAreaController notificationIconAreaController) {
        this.mContext = context;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.notificationIconAreaController = notificationIconAreaController;
        this.topHeight = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_top_height);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_second_height);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bottom_height);
        this.bgRadius = context.getResources().getDimension(R.dimen.ongoing_activity_chip_bg_radius);
        this.sportScoreMaxWidth = context.getResources().getDimensionPixelSize(R.dimen.sport_ongoing_activity_chip_text_max_width);
        this.expandedTextSize = context.getResources().getDimension(R.dimen.ongoing_activity_chip_text_size);
        this.availableSpace = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_available_space_medium);
        this.maxChipWidth = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_max_width);
        this.customChipSidePadding = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side_for_custom);
        this.shadowWidth = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset);
        this.notificationIconWidth = context.getResources().getDimensionPixelSize(R.dimen.notification_dnd_status_icon_size);
        this.infoTextTotalMargin = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_info_text_space_extra);
        this.maximumWidth = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_max_width);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_min_width);
        this.marqueeLimitedWidth = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_marquee_available_width);
        this.marqueeState = MarqueeState.INIT;
        float f = indicatorScaleGardener.getLatestScaleModel(context).ratio;
        this.topHeight = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_top_height) * f);
        MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_second_height) * f);
        MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bottom_height) * f);
        this.availableSpace = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_available_space_medium) * f);
        this.maxChipWidth = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_max_width) * f);
        this.bgRadius = context.getResources().getDimension(R.dimen.ongoing_activity_chip_bg_radius) * f;
        this.expandedTextSize = context.getResources().getDimension(R.dimen.ongoing_activity_chip_text_size) * f;
        this.customChipSidePadding = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side_for_custom) * f);
        this.sportScoreMaxWidth = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.sport_ongoing_activity_chip_text_max_width) * f);
        this.shadowWidth = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset) * f);
        this.notificationIconWidth = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.notification_dnd_status_icon_size) * f);
        this.infoTextTotalMargin = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_info_text_space_extra) * f);
        this.maximumWidth = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_max_width) * f);
        MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_min_width) * f);
        this.marqueeLimitedWidth = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_marquee_available_width) * f);
        notifyDataSetChanged();
    }

    public final ColorStateList getChipBg(int i, int i2) {
        if (i == getItemCount() - 1) {
            ColorStateList valueOf = ColorStateList.valueOf(i2);
            valueOf.getClass();
            return valueOf;
        }
        ColorStateList withAlpha = ColorStateList.valueOf(i2).withAlpha((Color.alpha(i2) * 40) / 100);
        withAlpha.getClass();
        return withAlpha;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        return Math.min(OngoingActivityDataHelper.mOngoingActivityLists.size(), this.mMaxItemCount);
    }

    public final boolean hasAvailableSpaceToExpand(int i) {
        OngoingActivityDataHelper.INSTANCE.getClass();
        int i2 = (OngoingActivityDataHelper.mOngoingActivityLists.size() > 1 ? this.shadowWidth : 0) + this.availableSpace;
        if (this.notificationIconAreaController.getShowingIconCount() > 0) {
            i2 += this.notificationIconWidth;
        }
        int i3 = i2 + i;
        int i4 = this.enableMaxWidth;
        boolean z = this.shouldShowChipOnly;
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i4, i3, "hasAvailableSpaceToExpand - enableMax:", " totalWidth", " call:");
        m.append(z);
        Log.d(this.TAG, m.toString());
        return this.enableMaxWidth >= i3;
    }

    public final void marqueeIfNeeded() {
        Pair pair = this.marqueePair;
        if (pair != null) {
            String str = "OA chip marqueeIfNeeded it.second.mNeedMarquee:" + ((OngoingActivityData) pair.getSecond()).mNeedMarquee + ", isKeyguardGoneNow:" + this.isKeyguardGoneNow;
            String str2 = this.TAG;
            Log.i(str2, str);
            if (Intrinsics.areEqual(((OngoingActivityData) pair.getSecond()).mNeedMarquee, Boolean.TRUE) || this.isKeyguardGoneNow) {
                if (!(pair.getFirst() instanceof MarqueeTextView)) {
                    Log.e(str2, "Oa chip textView is not MarqueeTextView. But it added marqueePair");
                    this.isKeyguardGoneNow = false;
                    this.marqueePair = null;
                    return;
                }
                if (this.marqueeState == MarqueeState.INIT) {
                    this.marqueeState = MarqueeState.RUN;
                }
                ((TextView) pair.getFirst()).setEllipsize(TextUtils.TruncateAt.MARQUEE);
                ((TextView) pair.getFirst()).setMarqueeRepeatLimit(1);
                ((TextView) pair.getFirst()).setSelected(true);
                MarqueeTextView marqueeTextView = (MarqueeTextView) pair.getFirst();
                marqueeTextView.mMarqueeEndListener = new OngoingChipAdapter$marqueeIfNeeded$1$1(marqueeTextView, this);
                ((OngoingActivityData) pair.getSecond()).mNeedMarquee = Boolean.FALSE;
            }
        }
        this.isKeyguardGoneNow = false;
        this.marqueePair = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MarqueeTextView marqueeTextView;
        ChipViewHolder chipViewHolder = (ChipViewHolder) viewHolder;
        String m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onBindViewHolder() notificationArrayList.position = ");
        String str = this.TAG;
        Log.i(str, m);
        OngoingActivityDataHelper.INSTANCE.getClass();
        CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.mOngoingActivityLists;
        if (copyOnWriteArrayList.size() == 0) {
            Log.i(str, "onBindViewHolder : ongoingActivity is null ##");
            return;
        }
        OngoingActivityData dataByIndex = OngoingActivityDataHelper.getDataByIndex(0);
        int itemCount = getItemCount() - 1;
        int mediaCardPrimaryInfoColor = OngoingMediaResourceUtils.getMediaCardUiType$default(OngoingMediaResourceUtils.INSTANCE, dataByIndex.mChipBackground).getMediaCardPrimaryInfoColor(this.mContext);
        RemoteViews remoteViews = dataByIndex.mExpandedChipView;
        if (remoteViews != null && i == itemCount) {
            View apply = remoteViews.apply(this.mContext, null);
            float f = this.indicatorScaleGardener.getLatestScaleModel(this.mContext).ratio;
            apply.setScaleX(f);
            apply.setScaleY(f);
            apply.measure(0, 0);
            if (!hasAvailableSpaceToExpand(Math.min((this.customChipSidePadding * 2) + apply.getMeasuredWidth(), this.maxChipWidth))) {
                chipViewHolder.mRemoteContainer.removeAllViews();
                chipViewHolder.mRemoteContainer.setVisibility(8);
                chipViewHolder.mNotiParentLayout.setVisibility(0);
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
                chipViewHolder.setChipMode();
                int i2 = dataByIndex.mChipBackground;
                chipViewHolder.mNotiParentLayout.getLayoutParams().height = this.topHeight;
                setChipBg(chipViewHolder.mNotiParentLayout, i, getChipBg(i, i2));
                return;
            }
            chipViewHolder.mNotiParentLayout.setVisibility(8);
            chipViewHolder.mRemoteContainer.setVisibility(0);
            chipViewHolder.mRemoteContainer.removeAllViews();
            View findViewWithTag = apply.findViewWithTag("chip_sports_score");
            if (findViewWithTag instanceof TextView) {
                TextView textView = (TextView) findViewWithTag;
                textView.measure(0, 0);
                if (MathKt__MathJVMKt.roundToInt(textView.getMeasuredWidth() * f) >= this.sportScoreMaxWidth) {
                    textView.setText("-");
                }
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setHorizontalFadingEdgeEnabled(true);
                textView.setTextColor(this.mContext.getColor(R.color.ongoing_activity_custom_chip_text_color));
                this.marqueePair = new Pair(findViewWithTag, dataByIndex);
            }
            chipViewHolder.mRemoteContainer.addView(apply);
            FrameLayout frameLayout = chipViewHolder.mRemoteContainer;
            int i3 = this.customChipSidePadding;
            frameLayout.setPadding(i3, 0, i3, 0);
            chipViewHolder.mRemoteContainer.getLayoutParams().height = this.topHeight;
            setChipBg(chipViewHolder.mRemoteContainer, i, getChipBg(i, dataByIndex.mChipBackground));
            return;
        }
        chipViewHolder.mRemoteContainer.removeAllViews();
        chipViewHolder.mRemoteContainer.setVisibility(8);
        chipViewHolder.mNotiParentLayout.setVisibility(0);
        if (i == itemCount) {
            Icon icon3 = dataByIndex.mChipIcon;
            if (icon3 != null) {
                chipViewHolder.mSmallIcon.setImageIcon(icon3);
            } else {
                Icon icon4 = dataByIndex.mCardIcon;
                if (icon4 != null) {
                    chipViewHolder.mSmallIcon.setImageIcon(icon4);
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
        if (i == itemCount) {
            if (dataByIndex.mExpandedChipText != null) {
                MarqueeTextView marqueeTextView2 = new MarqueeTextView(this.mContext, null, 0, 0, 14, null);
                marqueeTextView2.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
                CharSequence charSequence = dataByIndex.mExpandedChipText;
                charSequence.getClass();
                marqueeTextView2.setText(charSequence);
                marqueeTextView = marqueeTextView2;
            } else {
                RemoteViews remoteViews2 = dataByIndex.mChronometerView;
                if (remoteViews2 != null) {
                    Chronometer chronometer = (Chronometer) remoteViews2.apply(this.mContext, null);
                    chronometer.setTypeface(Typeface.create(Typeface.create("sec-num-fixed", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
                    chronometer.setFormat("%s");
                    chronometer.hidden_semSetMilliSecondCount(0);
                    chronometer.hidden_semSetForceTickTime(1000);
                    marqueeTextView = chronometer;
                } else {
                    MarqueeTextView marqueeTextView3 = new MarqueeTextView(this.mContext, null, 0, 0, 14, null);
                    marqueeTextView3.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
                    marqueeTextView3.setText(dataByIndex.mPrimaryInfo);
                    marqueeTextView = marqueeTextView3;
                }
            }
            marqueeTextView.setSingleLine(true);
            marqueeTextView.setTextColor(mediaCardPrimaryInfoColor);
            marqueeTextView.setTextSize(0, this.expandedTextSize);
            marqueeTextView.setHorizontalFadingEdgeEnabled(true);
            marqueeTextView.measure(0, 0);
            hasAvailableSpaceToExpand(Math.min(marqueeTextView.getMeasuredWidth(), this.maximumWidth) + this.infoTextTotalMargin);
            int i4 = (copyOnWriteArrayList.size() > 1 ? this.shadowWidth : 0) + this.availableSpace;
            NotificationIconAreaController notificationIconAreaController = this.notificationIconAreaController;
            if (notificationIconAreaController.getShowingIconCount() > 0) {
                i4 += this.notificationIconWidth;
            }
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.enableMaxWidth - i4, "getChipAvailableSpace - enableMax: ", " ", str);
            int i5 = this.enableMaxWidth - i4;
            if (i5 <= this.marqueeLimitedWidth) {
                chipViewHolder.setChipMode();
                int i6 = dataByIndex.mChipBackground;
                chipViewHolder.mNotiParentLayout.getLayoutParams().height = this.topHeight;
                setChipBg(chipViewHolder.mNotiParentLayout, i, getChipBg(i, i6));
                return;
            }
            notificationIconAreaController.getShowingIconCount();
            if (chipViewHolder.mExpandedInfo.getChildCount() > 0) {
                chipViewHolder.mExpandedInfo.removeAllViews();
            }
            float f2 = chipViewHolder.indicatorScaleGardener.getLatestScaleModel(chipViewHolder.mRootLayout.getContext()).ratio;
            chipViewHolder.mNotiParentLayout.setPadding(MathKt__MathJVMKt.roundToInt(chipViewHolder.mRootLayout.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_padding_start) * f2), 0, MathKt__MathJVMKt.roundToInt(chipViewHolder.mRootLayout.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_padding_end) * f2), 0);
            chipViewHolder.mNotiParentLayout.getLayoutParams().width = -2;
            copyOnWriteArrayList.size();
            int min = Math.min(i5 - chipViewHolder.infoTextExtra, chipViewHolder.maximumWidth);
            boolean z = marqueeTextView instanceof Chronometer;
            if (z) {
                marqueeTextView.measure(0, 0);
                Chronometer chronometer2 = (Chronometer) marqueeTextView;
                Log.i(chipViewHolder.TAG, ListImplementation$$ExternalSyntheticOutline0.m(chronometer2.getMeasuredWidth(), min, "setExpandedChipMode for Chronometer width:", ", availableWidth:"));
                chronometer2.setMaxWidth(min);
                chronometer2.setElegantTextHeight(false);
                if (chronometer2.getMeasuredWidth() == 0 || chronometer2.getMeasuredWidth() > min) {
                    chipViewHolder.setChipMode();
                } else {
                    chipViewHolder.mExpandedInfo.addView(marqueeTextView, -2, chronometer2.getMeasuredHeight());
                    chipViewHolder.mExpandedInfo.setVisibility(0);
                }
            } else {
                marqueeTextView.setMaxWidth(min);
                marqueeTextView.setElegantTextHeight(false);
                chipViewHolder.mExpandedInfo.addView(marqueeTextView);
                chipViewHolder.mExpandedInfo.setVisibility(0);
            }
            if (!z) {
                this.marqueePair = new Pair(marqueeTextView, dataByIndex);
                marqueeIfNeeded();
            }
        } else {
            chipViewHolder.setChipMode();
        }
        int i7 = dataByIndex.mChipBackground;
        chipViewHolder.mNotiParentLayout.getLayoutParams().height = this.topHeight;
        setChipBg(chipViewHolder.mNotiParentLayout, i, getChipBg(i, i7));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater from = LayoutInflater.from(this.mContext);
        this.viewGroup = viewGroup;
        Log.i(this.TAG, "onCreateViewHolder() ");
        return new ChipViewHolder(from.inflate(R.layout.sec_ongoing_activity_chip_item, viewGroup, false), this.indicatorScaleGardener);
    }

    public final void setChipBg(View view, int i, ColorStateList colorStateList) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(this.bgRadius);
        gradientDrawable.setColor(colorStateList);
        int itemCount = getItemCount() - 1;
        if (getItemCount() <= 1 || i == itemCount) {
            view.setBackground(gradientDrawable);
            return;
        }
        float f = this.indicatorScaleGardener.getLatestScaleModel(this.mContext).ratio;
        int roundToInt = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset) * f);
        int roundToInt2 = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_min_width) * f);
        int i2 = this.topHeight;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap createBitmap = Bitmap.createBitmap(roundToInt2, i2, config);
        Canvas canvas = new Canvas(createBitmap);
        Bitmap bitmap = DrawableKt.toBitmap(gradientDrawable, roundToInt2, i2, config);
        Paint paint = new Paint(1);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        paint.setColor(0);
        paint.setAntiAlias(true);
        paint.setBlendMode(BlendMode.CLEAR);
        boolean z = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.mContext) == 1;
        float f2 = z ? roundToInt : 0.0f;
        if (!z) {
            roundToInt2 -= roundToInt;
        }
        float f3 = roundToInt2;
        float f4 = this.bgRadius;
        canvas.drawRoundRect(f2, 0.0f, f3, i2, f4, f4, paint);
        view.setBackground(new BitmapDrawable(this.mContext.getResources(), createBitmap));
    }
}
