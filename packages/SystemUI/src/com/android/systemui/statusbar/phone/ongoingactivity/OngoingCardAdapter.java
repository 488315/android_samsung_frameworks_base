package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.core.os.BundleKt;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.R;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardRemoteContainer;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils;
import com.android.systemui.statusbar.phone.ongoingactivity.media.OngoingMediaResourceUtils;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

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

    public OngoingCardAdapter(Context context, IndicatorScaleGardener indicatorScaleGardener, NotificationRemoteInputManager notificationRemoteInputManager, SecMediaHost secMediaHost, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) throws Resources.NotFoundException {
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
                        View viewApply = remoteViews.apply(this.mContext, null, this.remoteHandler.mInteractionHandler);
                        if (dataByIndex.mCustomExpandedCardView != null) {
                            viewApply = this.faceWidgetNotificationControllerWrapper.getViewFromNowBar(viewApply, BundleKt.bundleOf(new Pair("type", "OA")));
                        }
                        view2 = viewApply;
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
                    Drawable background = null;
                    int i2 = dataByIndex.mChipBackground;
                    ViewGroup viewGroup3 = this.mCardExpandContents;
                    if (viewGroup3 != null) {
                        background = viewGroup3.getBackground();
                    }
                    cardStackViewUtils.getClass();
                    CardStackViewUtils.addGradientBackground(context2, ongoingCardWidth, measuredHeight, i2, background, true, z);
                    viewGroup2.getLayoutParams().height = -2;
                }
            }
            OngoingActivityLayoutUtil.updateNowbarSports(this.mContext, view, dataByIndex, OngoingType.OA);
            OngoingActivityLayoutUtil.updateOngoingChronometer(view, dataByIndex, false);
            OngoingActivityLayoutUtil.updateOngoingDescription(view);
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

    public final View getDetachedMediaView() throws Resources.NotFoundException {
        View view;
        OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = this.getMediaCardView;
        if (ongoingCardController$$ExternalSyntheticLambda0 == null) {
            Log.e("MediaOngoingActivity", "getMediaCard. lambda is not initialized");
            view = null;
        } else {
            view = (View) ongoingCardController$$ExternalSyntheticLambda0.mo781invoke(Unit.INSTANCE);
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
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sec_ongoing_card_item_layout, viewGroup, false);
            bindView(viewInflate, i);
            return viewInflate;
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

    /* JADX WARN: Removed duplicated region for block: B:104:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void inflateDummyChipView(OngoingActivityData ongoingActivityData) {
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        TextView textView;
        LinearLayout linearLayout;
        FrameLayout frameLayout;
        LinearLayout linearLayout2;
        LinearLayout linearLayout3;
        ViewGroup.LayoutParams layoutParams;
        FrameLayout frameLayout2;
        ViewGroup.LayoutParams layoutParams2;
        Unit unit;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        LinearLayout linearLayout4;
        FrameLayout frameLayout3;
        FrameLayout frameLayout4;
        View viewFindViewWithTag;
        FrameLayout frameLayout5;
        FrameLayout frameLayout6;
        int iRoundToInt;
        FrameLayout frameLayout7;
        FrameLayout frameLayout8;
        ViewGroup.LayoutParams layoutParams3;
        ViewGroup.LayoutParams layoutParams4;
        Unit unit2;
        Trace.beginSection("OCA.inflateDummyChipView");
        int mediaCardPrimaryInfoColor = OngoingMediaResourceUtils.getMediaCardUiType$default(OngoingMediaResourceUtils.INSTANCE, ongoingActivityData.mChipBackground).getMediaCardPrimaryInfoColor(this.mContext);
        if (ongoingActivityData.mExpandedChipView != null) {
            Icon icon = ongoingActivityData.mChipIcon;
            if (icon == null) {
                Icon icon2 = ongoingActivityData.mCardIcon;
                if (icon2 != null && (imageView4 = this.mDummySmallIcon) != null) {
                    imageView4.setImageIcon(icon2);
                    Unit unit3 = Unit.INSTANCE;
                }
                imageView5 = this.mDummySmallIcon;
                if (imageView5 != null) {
                    imageView5.setVisibility(0);
                }
                imageView6 = this.mDummySmallIcon;
                if (imageView6 != null) {
                    if (ContrastColorUtil.getInstance(this.mContext).isGrayscaleIcon(imageView6.getDrawable())) {
                        imageView6.setImageTintList(ColorStateList.valueOf(mediaCardPrimaryInfoColor));
                    } else {
                        imageView6.setImageTintList(null);
                    }
                }
                linearLayout4 = this.mDummyNotiParentLayout;
                if (linearLayout4 != null) {
                    linearLayout4.setVisibility(8);
                }
                frameLayout3 = this.mDummyRemoteContainer;
                if (frameLayout3 != null) {
                    frameLayout3.setVisibility(0);
                }
                frameLayout4 = this.mDummyRemoteContainer;
                if (frameLayout4 != null) {
                    frameLayout4.removeAllViews();
                }
                RemoteViews remoteViews = ongoingActivityData.mExpandedChipView;
                remoteViews.getClass();
                View viewApply = remoteViews.apply(this.mContext, null);
                float f = this.indicatorScaleGardener.getLatestScaleModel(this.mContext).ratio;
                viewApply.setScaleX(f);
                viewApply.setScaleY(f);
                viewFindViewWithTag = viewApply.findViewWithTag("chip_sports_score");
                if (viewFindViewWithTag instanceof TextView) {
                    TextView textView2 = (TextView) viewFindViewWithTag;
                    textView2.measure(0, 0);
                    if (MathKt__MathJVMKt.roundToInt(textView2.getMeasuredWidth() * f) >= MathKt__MathJVMKt.roundToInt(this.sportScoreMaxWidth * f)) {
                        textView2.setText("-");
                    }
                    textView2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    textView2.setHorizontalFadingEdgeEnabled(true);
                    textView2.setTextColor(this.mContext.getColor(R.color.ongoing_activity_custom_chip_text_color));
                }
                frameLayout5 = this.mDummyRemoteContainer;
                if (frameLayout5 != null) {
                    frameLayout5.addView(viewApply);
                }
                frameLayout6 = this.mDummyRemoteContainer;
                if (frameLayout6 != null) {
                    int i = this.customChipSidePadding;
                    frameLayout6.setPadding(i, 0, i, 0);
                }
                viewApply.measure(0, 0);
                iRoundToInt = MathKt__MathJVMKt.roundToInt((viewApply.getMeasuredWidth() * (f >= 1.0f ? f : 1.0f)) + (this.customChipSidePadding * 2));
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(viewApply.getMeasuredWidth(), this.customChipSidePadding, "inflateDummyChipView custom chip measuredWidth:", ", padding:", ", ratio:");
                sbM.append(f);
                sbM.append(", parentWidth:");
                sbM.append(iRoundToInt);
                Log.i("{OngoingActivityCardStackAdapter}", sbM.toString());
                frameLayout7 = this.mDummyRemoteContainer;
                if (frameLayout7 != null && (layoutParams4 = frameLayout7.getLayoutParams()) != null) {
                    layoutParams4.width = iRoundToInt;
                }
                frameLayout8 = this.mDummyRemoteContainer;
                if (frameLayout8 != null && (layoutParams3 = frameLayout8.getLayoutParams()) != null) {
                    layoutParams3.height = this.topHeight;
                }
            } else {
                ImageView imageView7 = this.mDummySmallIcon;
                if (imageView7 != null) {
                    imageView7.setImageIcon(icon);
                    unit2 = Unit.INSTANCE;
                } else {
                    unit2 = null;
                }
                if (unit2 == null) {
                }
                imageView5 = this.mDummySmallIcon;
                if (imageView5 != null) {
                }
                imageView6 = this.mDummySmallIcon;
                if (imageView6 != null) {
                }
                linearLayout4 = this.mDummyNotiParentLayout;
                if (linearLayout4 != null) {
                }
                frameLayout3 = this.mDummyRemoteContainer;
                if (frameLayout3 != null) {
                }
                frameLayout4 = this.mDummyRemoteContainer;
                if (frameLayout4 != null) {
                }
                RemoteViews remoteViews2 = ongoingActivityData.mExpandedChipView;
                remoteViews2.getClass();
                View viewApply2 = remoteViews2.apply(this.mContext, null);
                float f2 = this.indicatorScaleGardener.getLatestScaleModel(this.mContext).ratio;
                viewApply2.setScaleX(f2);
                viewApply2.setScaleY(f2);
                viewFindViewWithTag = viewApply2.findViewWithTag("chip_sports_score");
                if (viewFindViewWithTag instanceof TextView) {
                }
                frameLayout5 = this.mDummyRemoteContainer;
                if (frameLayout5 != null) {
                }
                frameLayout6 = this.mDummyRemoteContainer;
                if (frameLayout6 != null) {
                }
                viewApply2.measure(0, 0);
                iRoundToInt = MathKt__MathJVMKt.roundToInt((viewApply2.getMeasuredWidth() * (f2 >= 1.0f ? f2 : 1.0f)) + (this.customChipSidePadding * 2));
                StringBuilder sbM2 = MutableObjectList$$ExternalSyntheticOutline0.m(viewApply2.getMeasuredWidth(), this.customChipSidePadding, "inflateDummyChipView custom chip measuredWidth:", ", padding:", ", ratio:");
                sbM2.append(f2);
                sbM2.append(", parentWidth:");
                sbM2.append(iRoundToInt);
                Log.i("{OngoingActivityCardStackAdapter}", sbM2.toString());
                frameLayout7 = this.mDummyRemoteContainer;
                if (frameLayout7 != null) {
                    layoutParams4.width = iRoundToInt;
                }
                frameLayout8 = this.mDummyRemoteContainer;
                if (frameLayout8 != null) {
                    layoutParams3.height = this.topHeight;
                }
            }
        } else {
            FrameLayout frameLayout9 = this.mDummyRemoteContainer;
            if (frameLayout9 != null) {
                frameLayout9.removeAllViews();
            }
            FrameLayout frameLayout10 = this.mDummyRemoteContainer;
            if (frameLayout10 != null) {
                frameLayout10.setVisibility(8);
            }
            LinearLayout linearLayout5 = this.mDummyNotiParentLayout;
            if (linearLayout5 != null) {
                linearLayout5.setVisibility(0);
            }
            Icon icon3 = ongoingActivityData.mChipIcon;
            if (icon3 == null) {
                Icon icon4 = ongoingActivityData.mCardIcon;
                if (icon4 != null && (imageView = this.mDummySmallIcon) != null) {
                    imageView.setImageIcon(icon4);
                    Unit unit4 = Unit.INSTANCE;
                }
                imageView2 = this.mDummySmallIcon;
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
                imageView3 = this.mDummySmallIcon;
                if (imageView3 != null) {
                    if (ContrastColorUtil.getInstance(this.mContext).isGrayscaleIcon(imageView3.getDrawable())) {
                        imageView3.setImageTintList(ColorStateList.valueOf(mediaCardPrimaryInfoColor));
                    } else {
                        imageView3.setImageTintList(null);
                    }
                }
                if (ongoingActivityData.mExpandedChipText == null) {
                    TextView textView3 = new TextView(this.mContext);
                    textView3.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
                    CharSequence charSequence = ongoingActivityData.mExpandedChipText;
                    charSequence.getClass();
                    textView3.setText(charSequence);
                    textView = textView3;
                } else {
                    RemoteViews remoteViews3 = ongoingActivityData.mChronometerView;
                    if (remoteViews3 != null) {
                        Chronometer chronometer = (Chronometer) remoteViews3.apply(this.mContext, null);
                        chronometer.setTypeface(Typeface.create(Typeface.create("sec-num-fixed", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
                        chronometer.setFormat("%s");
                        chronometer.hidden_semSetMilliSecondCount(0);
                        chronometer.hidden_semSetForceTickTime(1000);
                        textView = chronometer;
                    } else {
                        TextView textView4 = new TextView(this.mContext);
                        textView4.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
                        textView4.setText(ongoingActivityData.mPrimaryInfo);
                        textView = textView4;
                    }
                }
                textView.setSingleLine(true);
                textView.setTextColor(mediaCardPrimaryInfoColor);
                textView.setTextSize(0, this.mDummyTextSize);
                textView.setHorizontalFadingEdgeEnabled(true);
                linearLayout = this.mDummyNotiParentLayout;
                if (linearLayout != null && (layoutParams2 = linearLayout.getLayoutParams()) != null) {
                    layoutParams2.height = this.topHeight;
                }
                frameLayout = this.mDummyExpandedInfo;
                if ((frameLayout == null ? frameLayout.getChildCount() : 0) > 0 && (frameLayout2 = this.mDummyExpandedInfo) != null) {
                    frameLayout2.removeAllViews();
                }
                float f3 = this.indicatorScaleGardener.getLatestScaleModel(this.mContext).ratio;
                int iRoundToInt2 = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_padding_start) * f3);
                int iRoundToInt3 = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_padding_end) * f3);
                linearLayout2 = this.mDummyNotiParentLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setPadding(iRoundToInt2, 0, iRoundToInt3, 0);
                }
                linearLayout3 = this.mDummyNotiParentLayout;
                if (linearLayout3 != null && (layoutParams = linearLayout3.getLayoutParams()) != null) {
                    layoutParams.width = -2;
                }
                if (textView instanceof Chronometer) {
                    textView.setElegantTextHeight(false);
                    FrameLayout frameLayout11 = this.mDummyExpandedInfo;
                    if (frameLayout11 != null) {
                        frameLayout11.addView(textView);
                    }
                    FrameLayout frameLayout12 = this.mDummyExpandedInfo;
                    if (frameLayout12 != null) {
                        frameLayout12.setVisibility(0);
                    }
                } else {
                    textView.measure(0, 0);
                    Chronometer chronometer2 = (Chronometer) textView;
                    chronometer2.setElegantTextHeight(false);
                    FrameLayout frameLayout13 = this.mDummyExpandedInfo;
                    if (frameLayout13 != null) {
                        frameLayout13.addView(textView, -2, chronometer2.getMeasuredHeight());
                    }
                    FrameLayout frameLayout14 = this.mDummyExpandedInfo;
                    if (frameLayout14 != null) {
                        frameLayout14.setVisibility(0);
                    }
                }
            } else {
                ImageView imageView8 = this.mDummySmallIcon;
                if (imageView8 != null) {
                    imageView8.setImageIcon(icon3);
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                if (unit == null) {
                }
                imageView2 = this.mDummySmallIcon;
                if (imageView2 != null) {
                }
                imageView3 = this.mDummySmallIcon;
                if (imageView3 != null) {
                }
                if (ongoingActivityData.mExpandedChipText == null) {
                }
                textView.setSingleLine(true);
                textView.setTextColor(mediaCardPrimaryInfoColor);
                textView.setTextSize(0, this.mDummyTextSize);
                textView.setHorizontalFadingEdgeEnabled(true);
                linearLayout = this.mDummyNotiParentLayout;
                if (linearLayout != null) {
                    layoutParams2.height = this.topHeight;
                }
                frameLayout = this.mDummyExpandedInfo;
                if ((frameLayout == null ? frameLayout.getChildCount() : 0) > 0) {
                    frameLayout2.removeAllViews();
                }
                float f32 = this.indicatorScaleGardener.getLatestScaleModel(this.mContext).ratio;
                int iRoundToInt22 = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_padding_start) * f32);
                int iRoundToInt32 = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_padding_end) * f32);
                linearLayout2 = this.mDummyNotiParentLayout;
                if (linearLayout2 != null) {
                }
                linearLayout3 = this.mDummyNotiParentLayout;
                if (linearLayout3 != null) {
                    layoutParams.width = -2;
                }
                if (textView instanceof Chronometer) {
                }
            }
        }
        Trace.endSection();
    }

    public final void initNormalNotificationView(View view) throws Resources.NotFoundException {
        ViewGroup.LayoutParams layoutParams;
        view.setLayoutDirection(this.mContext.getResources().getConfiguration().getLayoutDirection());
        this.mCardParentLayout = (ViewGroup) view.findViewById(R.id.stack_pip_layout);
        this.mCardExpandContents = (ViewGroup) view.findViewById(R.id.stack_expand_contents);
        View viewFindViewById = view.findViewById(R.id.dummy_capsule_item_top_layout);
        viewFindViewById.getClass();
        this.mDummyNotiParentLayout = (LinearLayout) viewFindViewById;
        View viewFindViewById2 = view.findViewById(R.id.dummy_capsule_item_app_icon);
        viewFindViewById2.getClass();
        this.mDummySmallIcon = (ImageView) viewFindViewById2;
        View viewFindViewById3 = view.findViewById(R.id.dummy_capsule_item_noti_expanded_info);
        viewFindViewById3.getClass();
        this.mDummyExpandedInfo = (FrameLayout) viewFindViewById3;
        View viewFindViewById4 = view.findViewById(R.id.dummy_capsule_remote_container);
        viewFindViewById4.getClass();
        this.mDummyRemoteContainer = (FrameLayout) viewFindViewById4;
        float f = this.indicatorScaleGardener.getLatestScaleModel(this.mContext).ratio;
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_size) * f);
        ImageView imageView = this.mDummySmallIcon;
        if (imageView != null && (layoutParams = imageView.getLayoutParams()) != null) {
            layoutParams.width = iRoundToInt;
            layoutParams.height = iRoundToInt;
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
