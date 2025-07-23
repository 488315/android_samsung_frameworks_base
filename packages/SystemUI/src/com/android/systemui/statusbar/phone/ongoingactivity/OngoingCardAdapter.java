package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import androidx.core.os.BundleKt;
import com.android.systemui.R;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardRemoteContainer;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class OngoingCardAdapter extends BaseAdapter {
    public int customChipSidePadding;
    public final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
    public OngoingCardController$$ExternalSyntheticLambda0 getMediaCardView;
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
    public final int sportScoreMaxWidth;
    public int topHeight;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public OngoingCardAdapter(Context context, IndicatorScaleGardener indicatorScaleGardener, NotificationRemoteInputManager notificationRemoteInputManager, SecMediaHost secMediaHost, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
        this.mContext = context;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.remoteHandler = notificationRemoteInputManager;
        this.faceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        this.customChipSidePadding = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side_for_custom);
        this.topHeight = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_top_height);
        this.mDummyTextSize = context.getResources().getDimension(R.dimen.ongoing_activity_chip_text_size);
        context.getResources().getDimension(R.dimen.ongoing_activity_chip_bg_radius);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_sixth_unit);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_fifth_unit);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_fourth_unit);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bold_sixth_unit);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bold_fifth_unit);
        context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_bold_fourth_unit);
        this.sportScoreMaxWidth = context.getResources().getDimensionPixelSize(R.dimen.sport_ongoing_activity_chip_text_max_width);
    }

    public final void bindView(View view, int i) {
        Trace.beginSection("OCA.bindview START");
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
            Log.i("{OngoingActivityCardStackAdapter}", "bindView : ongoingActivity is null ##");
            return;
        }
        initNormalNotificationView(view);
        OngoingActivityData dataByIndex = OngoingActivityDataHelper.getDataByIndex(i);
        OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
        Context context = this.mContext;
        ongoingActivityLayoutUtil.getClass();
        int ongoingCardWidth = OngoingActivityLayoutUtil.getOngoingCardWidth(context);
        if (dataByIndex.mIsMediaOngoingData) {
            Log.e("MediaOngoingActivity", "bindView is not support media ongoing data");
        } else {
            RemoteViews remoteViews = dataByIndex.mOngoingOAExpandView;
            if (remoteViews != null) {
                ViewGroup viewGroup = this.mCardExpandContents;
                if (viewGroup != null) {
                    viewGroup.removeAllViews();
                }
                ViewGroup viewGroup2 = this.mCardExpandContents;
                if (viewGroup2 != null) {
                    View view2 = dataByIndex.mNotificationEntry.mPromotedOngoingView;
                    ViewParent parent = view2 != null ? view2.getParent() : null;
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(view2);
                    }
                    if (view2 == null) {
                        View apply = remoteViews.apply(this.mContext, null, this.remoteHandler.mInteractionHandler);
                        if (dataByIndex.mCustomExpandedCardView != null) {
                            apply = this.faceWidgetNotificationControllerWrapper.getViewFromNowBar(apply, BundleKt.bundleOf(new Pair("type", "OA")));
                        }
                        view2 = apply;
                    }
                    if (dataByIndex.mCustomExpandedCardView != null) {
                        CardRemoteContainer cardRemoteContainer = new CardRemoteContainer(this.mContext, null, 0, 6, null);
                        cardRemoteContainer.addView(view2);
                        viewGroup2.addView(cardRemoteContainer);
                    } else {
                        viewGroup2.addView(view2);
                    }
                    view2.measure(0, 0);
                    boolean z = dataByIndex.mCustomExpandedCardView == null;
                    CardStackViewUtils cardStackViewUtils = CardStackViewUtils.INSTANCE;
                    View view3 = view2;
                    Context context2 = this.mContext;
                    int measuredHeight = view3.getMeasuredHeight();
                    Drawable drawable = null;
                    int i2 = dataByIndex.mChipBackground;
                    ViewGroup viewGroup3 = this.mCardExpandContents;
                    if (viewGroup3 != null) {
                        drawable = viewGroup3.getBackground();
                    }
                    cardStackViewUtils.getClass();
                    CardStackViewUtils.addGradientBackground(context2, ongoingCardWidth, measuredHeight, i2, drawable, true, z);
                    viewGroup2.getLayoutParams().height = -2;
                }
            }
            OngoingActivityLayoutUtil.updateNowbarSports(this.mContext, view, dataByIndex, OngoingType.OA);
            OngoingActivityLayoutUtil.updateOngoingChronometer(view, dataByIndex, false);
        }
        if (i == 0) {
            view.setImportantForAccessibility(1);
        } else {
            view.setImportantForAccessibility(4);
        }
        Trace.endSection();
        inflateDummyChipView(dataByIndex);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        return OngoingActivityDataHelper.mOngoingActivityLists.size();
    }

    public final View getDetachedMediaView() {
        View view;
        OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = this.getMediaCardView;
        if (ongoingCardController$$ExternalSyntheticLambda0 == null) {
            Log.e("MediaOngoingActivity", "getMediaCard. lambda is not initialized");
            view = null;
        } else {
            view = (View) ongoingCardController$$ExternalSyntheticLambda0.mo779invoke(Unit.INSTANCE);
        }
        if (view == null) {
            Log.e("MediaOngoingActivity", "getDetachedMediaView getMediaCard is null");
            return null;
        }
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        view.setVisibility(0);
        view.setAlpha(1.0f);
        initNormalNotificationView(view);
        return view;
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
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() <= i || !OngoingActivityDataHelper.getDataByIndex(i).mIsMediaOngoingData) {
            viewGroup.getClass();
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sec_ongoing_card_item_layout, viewGroup, false);
            bindView(inflate, i);
            return inflate;
        }
        View detachedMediaView = getDetachedMediaView();
        if (detachedMediaView != null) {
            inflateDummyChipView(OngoingActivityDataHelper.getDataByIndex(i));
            return detachedMediaView;
        }
        Log.e("MediaOngoingActivity", "getView. Cannot get media View");
        viewGroup.getClass();
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sec_ongoing_card_item_layout, viewGroup, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x0110, code lost:
    
        if (r1 == null) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x002b, code lost:
    
        if (r1 == null) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void inflateDummyChipView(com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData r9) {
        /*
            Method dump skipped, instructions count: 593
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardAdapter.inflateDummyChipView(com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData):void");
    }

    public final void initNormalNotificationView(View view) {
        ViewGroup.LayoutParams layoutParams;
        view.setLayoutDirection(this.mContext.getResources().getConfiguration().getLayoutDirection());
        this.mCardParentLayout = (ViewGroup) view.findViewById(R.id.stack_pip_layout);
        this.mCardExpandContents = (ViewGroup) view.findViewById(R.id.stack_expand_contents);
        View findViewById = view.findViewById(R.id.dummy_capsule_item_top_layout);
        findViewById.getClass();
        this.mDummyNotiParentLayout = (LinearLayout) findViewById;
        View findViewById2 = view.findViewById(R.id.dummy_capsule_item_app_icon);
        findViewById2.getClass();
        this.mDummySmallIcon = (ImageView) findViewById2;
        View findViewById3 = view.findViewById(R.id.dummy_capsule_item_noti_expanded_info);
        findViewById3.getClass();
        this.mDummyExpandedInfo = (FrameLayout) findViewById3;
        View findViewById4 = view.findViewById(R.id.dummy_capsule_remote_container);
        findViewById4.getClass();
        this.mDummyRemoteContainer = (FrameLayout) findViewById4;
        float f = this.indicatorScaleGardener.getLatestScaleModel(this.mContext).ratio;
        int roundToInt = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_size) * f);
        ImageView imageView = this.mDummySmallIcon;
        if (imageView != null && (layoutParams = imageView.getLayoutParams()) != null) {
            layoutParams.width = roundToInt;
            layoutParams.height = roundToInt;
        }
        FrameLayout frameLayout = this.mDummyExpandedInfo;
        ViewGroup.LayoutParams layoutParams2 = frameLayout != null ? frameLayout.getLayoutParams() : null;
        layoutParams2.getClass();
        ((LinearLayout.LayoutParams) layoutParams2).setMarginStart(MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_margin_between) * f));
        this.mDummyTextSize = this.mContext.getResources().getDimension(R.dimen.ongoing_activity_chip_text_size) * f;
        this.mContext.getResources().getDimension(R.dimen.ongoing_activity_chip_bg_radius);
        this.customChipSidePadding = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side_for_custom) * f);
        this.topHeight = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_top_height) * f);
    }
}
