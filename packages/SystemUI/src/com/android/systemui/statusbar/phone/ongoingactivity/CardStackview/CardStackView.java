package com.android.systemui.statusbar.phone.ongoingactivity.CardStackview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.transition.ChangeBounds;
import android.transition.PathMotion;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.NotificationActionListLayout;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.row.GutContentInitializer;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.row.SecNotificationAppInfo;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardAdapter;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$expandAnimation$1$1;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CardStackView extends FrameLayout {
    public static final PathInterpolator alphaInterpolator;
    public static final PathInterpolator cardRemoveAlphaInterpolator;
    public static final PathInterpolator collapseColorInterpolator;
    public static final PathInterpolator collapseRootInterpolator;
    public static final PathInterpolator expandRootInterpolator;
    public static final PathInterpolator topAlphaInterpolator;
    public OngoingCardAdapter adapter;
    public final int cardSwipeEndDp;
    public final int cardSwipeStartDp;
    public CardSwipeState cardSwipeState;
    public final int centerDp;
    public OngoingCardController$$ExternalSyntheticLambda0 collapseBackCall;
    public int currentIndex;
    public final CardStackView$dataObserver$1 dataObserver;
    public View decorView;
    public OngoingCardController$$ExternalSyntheticLambda0 dismiss;
    public final DisplayMetrics displayMetrics;
    public final float dpToFlot;
    public final boolean enableElevation;
    public List endViewStatusList;
    public final GestureDetector gestureDetector;
    public View guts;
    public boolean gutsClosedCheck;
    public View gutsContents;
    public GutsControlState gutsControlState;
    public boolean gutsDisplay;
    public final long gutsPressDelay;
    public IndicatorScaleGardener indicatorScaleGardener;
    public PointF initialTouchPoint;
    public boolean isAnimating;
    public boolean isChildTouchEventIntercepting;
    public boolean isDragging;
    public OngoingCardController$$ExternalSyntheticLambda0 isMediaPlaying;
    public boolean isRunningCardFlipAnimation;
    public boolean isRunningCollapseAnimation;
    public boolean isRunningExpandAnimation;
    public boolean isRunningSwipeDismissTopCardMove;
    public final CardStackView$layoutListener$1 layoutListener;
    public final CardStackView$longPressRunnable$1 longPressRunnable;
    public OngoingCardController.AnonymousClass6 onChangeListener;
    public final Map originalAllParentsClipChildrenConfig;
    public final Map originalAllParentsClipToPaddingConfig;
    public boolean pendingAnimation;
    public int pendingHeight;
    public OngoingCardController$expandAnimation$1$1 pendingOnStartListener;
    public int pendingWidth;
    public String removingSbnId;
    public final CustomAnimationSet resetAnimationSet;
    public final ArrayList sceneList;
    public final int stackMaxSize;
    public List startViewStatusList;
    public final CustomAnimationSet swipeAnimationSet;
    public final CustomAnimationSet swipeHorizontalAnimationSet;
    public PointF touchPoint;
    public int touchPointerId;
    public final float viewSpacing;
    public static final Companion Companion = new Companion(null);
    public static final PathInterpolator expandContentsAlphaInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 0.0f);

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class CardSwipeState {
        public static final /* synthetic */ CardSwipeState[] $VALUES;
        public static final CardSwipeState HORIZONTAL;
        public static final CardSwipeState INIT;
        public static final CardSwipeState VERTICAL;

        static {
            CardSwipeState cardSwipeState = new CardSwipeState("INIT", 0);
            INIT = cardSwipeState;
            CardSwipeState cardSwipeState2 = new CardSwipeState("HORIZONTAL", 1);
            HORIZONTAL = cardSwipeState2;
            CardSwipeState cardSwipeState3 = new CardSwipeState("VERTICAL", 2);
            VERTICAL = cardSwipeState3;
            CardSwipeState[] cardSwipeStateArr = {cardSwipeState, cardSwipeState2, cardSwipeState3};
            $VALUES = cardSwipeStateArr;
            EnumEntriesKt.enumEntries(cardSwipeStateArr);
        }

        private CardSwipeState(String str, int i) {
        }

        public static CardSwipeState valueOf(String str) {
            return (CardSwipeState) Enum.valueOf(CardSwipeState.class, str);
        }

        public static CardSwipeState[] values() {
            return (CardSwipeState[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class GutsControlState {
        public static final /* synthetic */ GutsControlState[] $VALUES;
        public static final GutsControlState INIT;
        public static final GutsControlState PRESS;

        static {
            GutsControlState gutsControlState = new GutsControlState("INIT", 0);
            INIT = gutsControlState;
            GutsControlState gutsControlState2 = new GutsControlState("PRESS", 1);
            PRESS = gutsControlState2;
            GutsControlState[] gutsControlStateArr = {gutsControlState, gutsControlState2};
            $VALUES = gutsControlStateArr;
            EnumEntriesKt.enumEntries(gutsControlStateArr);
        }

        private GutsControlState(String str, int i) {
        }

        public static GutsControlState valueOf(String str) {
            return (GutsControlState) Enum.valueOf(GutsControlState.class, str);
        }

        public static GutsControlState[] values() {
            return (GutsControlState[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ViewStatus {
        public final float baseViewAlpha;
        public final Context context;
        public final PointF point;
        public final float scaleX;
        public final float scaleY;

        public ViewStatus(PointF pointF, float f, float f2, float f3, Context context) {
            this.point = pointF;
            this.baseViewAlpha = f;
            this.scaleX = f2;
            this.scaleY = f3;
            this.context = context;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ViewStatus)) {
                return false;
            }
            ViewStatus viewStatus = (ViewStatus) obj;
            return Intrinsics.areEqual(this.point, viewStatus.point) && Float.compare(this.baseViewAlpha, viewStatus.baseViewAlpha) == 0 && Float.compare(this.scaleX, viewStatus.scaleX) == 0 && Float.compare(this.scaleY, viewStatus.scaleY) == 0 && Intrinsics.areEqual(this.context, viewStatus.context);
        }

        public final int hashCode() {
            return this.context.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scaleY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scaleX, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.baseViewAlpha, this.point.hashCode() * 31, 31), 31), 31);
        }

        public final String toString() {
            return "ViewStatus(point=" + this.point + ", baseViewAlpha=" + this.baseViewAlpha + ", scaleX=" + this.scaleX + ", scaleY=" + this.scaleY + ", context=" + this.context + ")";
        }
    }

    static {
        new PathInterpolator(0.34f, 1.45f, 0.64f, 1.0f);
        expandRootInterpolator = new PathInterpolator(0.4f, 1.22f, 0.47f, 0.99f);
        new PathInterpolator(0.34f, 1.16f, 0.64f, 1.0f);
        collapseRootInterpolator = new PathInterpolator(0.4f, 1.22f, 0.47f, 0.99f);
        collapseColorInterpolator = new PathInterpolator(0.13f, 1.01f, 0.36f, 0.98f);
        alphaInterpolator = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
        topAlphaInterpolator = new PathInterpolator(0.33f, 0.2f, 0.68f, 1.0f);
        cardRemoveAlphaInterpolator = new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f);
    }

    public CardStackView(Context context) {
        this(context, null, 0, 6, null);
    }

    public static void changeLayoutMargin(View view, int i) {
        boolean z = view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMarginStart(i);
        view.setLayoutParams(marginLayoutParams);
    }

    public static void changeLayoutSize(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i2;
        view.setLayoutParams(layoutParams);
    }

    public static View getBaseColorView(View view) {
        View findViewById = view.findViewById(R.id.stack_pip_layout);
        if (findViewById != null) {
            return findViewById;
        }
        throw new Exception("Not found stack_pip_layout");
    }

    public static float getCardInitialScale(int i) {
        if (i == 0) {
            return 1.0f;
        }
        if (i == 1) {
            return 0.92f;
        }
        if (i == 2) {
            return 0.84f;
        }
        throw new Exception("Not defined index");
    }

    public static int getChipBg(int i) {
        OngoingActivityDataHelper.INSTANCE.getClass();
        int size = OngoingActivityDataHelper.mOngoingActivityLists.size();
        if (size == 0) {
            Log.i("{OngoingActivityCardStackView}", "getChipBg : ongoingActivity is null ##");
            return 0;
        }
        if (i < 0 || i >= size) {
            i = 0;
        }
        return OngoingActivityDataHelper.getDataByIndex(i).mChipBackground;
    }

    public static void isMonochromeCard() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
            Log.i("{OngoingActivityCardStackView}", "isMonochromeCard : ongoingActivity is null ##");
        }
    }

    public static void setActionButtonClickable(View view, boolean z) {
        if (view.getId() == R.id.ongoing_action_button || view.getId() == R.id.sec_action0 || view.getId() == R.id.sec_action1 || view.getId() == R.id.sec_action2 || view.getId() == R.id.sec_action3 || view.getId() == R.id.sec_action4) {
            view.setClickable(z);
            view.setEnabled(z);
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt != null) {
                    setActionButtonClickable(childAt, z);
                }
            }
        }
    }

    public static void updateBottomCardShadowLP$default(CardStackView cardStackView, ViewGroup viewGroup, float f, View view) {
        cardStackView.getClass();
        if (viewGroup != null) {
            Log.i("{OngoingActivityCardStackView}", "Updating dummy shadow LP " + f);
            int i = (int) (((double) f) * 0.8d);
            float f2 = (float) i;
            float f3 = cardStackView.viewSpacing;
            if (f2 > f3) {
                i = (int) (f3 * 0.8f);
            }
            view.getClass();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight() + i);
            layoutParams.gravity = 48;
            layoutParams.setMargins(0, (int) 0.0f, 0, i);
            viewGroup.setTranslationY(0.0f);
            viewGroup.setTranslationZ(-1.0f);
            viewGroup.setLayoutParams(layoutParams);
        }
    }

    public final void addItem(int i, int i2, View view, boolean z) {
        int width = getWidth() - (getPaddingEnd() + getPaddingStart());
        int height = getHeight() - (getPaddingBottom() + getPaddingTop());
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "addItem() index = ", " , order = ", "{OngoingActivityCardStackView}");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(-2, -2);
        }
        view.measure(width | (layoutParams.width == -1 ? 1073741824 : Integer.MIN_VALUE), height | (layoutParams.height == -1 ? 1073741824 : Integer.MIN_VALUE));
        layoutItem(view, i, i2);
        float cardInitialScale = getCardInitialScale(i2);
        view.setScaleY(cardInitialScale);
        view.setScaleX(cardInitialScale);
        addViewInLayout(view, z ? -1 : 0, layoutParams, true);
    }

    public final void changeCardBg(View view, int i) {
        View baseColorView = getBaseColorView(view);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(TypedValue.applyDimension(1, 36.0f, this.displayMetrics));
        gradientDrawable.setColor(ColorStateList.valueOf(i));
        baseColorView.setBackground(gradientDrawable);
    }

    public final void colorTransition(final int i, int i2, boolean z) {
        final View childAt = getChildAt(i2);
        if (childAt == null) {
            Log.d("{OngoingActivityCardStackView}", "    colorTransition : view is null");
            return;
        }
        final int chipBg = z ? getChipBg(getTopViewIndex() - i2) : getCardBg();
        Pair pair = z ? new Pair(500L, new DecelerateInterpolator()) : new Pair(600L, collapseColorInterpolator);
        long longValue = ((Number) pair.component1()).longValue();
        Object component2 = pair.component2();
        changeCardBg(childAt, chipBg);
        final ValueAnimator ofInt = ValueAnimator.ofInt(0, 1);
        ofInt.setDuration(longValue);
        ofInt.setInterpolator((TimeInterpolator) component2);
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$colorTransition$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) argbEvaluator.evaluate(ofInt.getAnimatedFraction(), Integer.valueOf(chipBg), Integer.valueOf(i))).intValue();
                CardStackView cardStackView = this;
                View view = childAt;
                CardStackView.Companion companion = CardStackView.Companion;
                cardStackView.changeCardBg(view, intValue);
            }
        });
        ofInt.start();
    }

    public final void connectCardItem() {
        int i;
        OngoingCardAdapter ongoingCardAdapter = this.adapter;
        if (ongoingCardAdapter != null) {
            int min = Math.min(ongoingCardAdapter.getCount() - this.currentIndex, this.stackMaxSize);
            i = min - getChildCount();
            int childCount = getChildCount() + this.currentIndex;
            int childCount2 = getChildCount();
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = (min - i2) - 1;
                if (i3 < 0) {
                    break;
                }
                addItem(i3, childCount2 + i2, ongoingCardAdapter.getView(childCount + i2, null, this), false);
            }
        } else {
            i = 0;
        }
        if (i > 0 || this.isRunningExpandAnimation) {
            Log.d("{OngoingActivityCardStackView}", "reorderItems()");
            int childCount3 = getChildCount();
            for (int i4 = 0; i4 < childCount3; i4++) {
                View childAt = getChildAt(i4);
                int childCount4 = (getChildCount() - i4) - 1;
                childAt.getClass();
                layoutItem(childAt, i4, childCount4);
                float cardInitialScale = getCardInitialScale(childCount4);
                childAt.setScaleY(cardInitialScale);
                childAt.setScaleX(cardInitialScale);
            }
        }
    }

    public final void disableTopCardGuts(boolean z) {
        View view;
        this.gutsDisplay = false;
        final View childAt = getChildAt(getTopViewIndex());
        if (childAt == null) {
            return;
        }
        View findViewById = childAt.findViewById(R.id.stack_pip_layout);
        View findViewById2 = childAt.findViewById(R.id.stack_expand_contents);
        if (findViewById == null || findViewById2 == null || (view = this.guts) == null) {
            Log.e("{OngoingActivityCardStackView}", "disableTopCardGuts: fail");
            return;
        }
        if (view.getVisibility() != 0) {
            Log.w("{OngoingActivityCardStackView}", "disableTopCardGuts: guts is already disabled");
            return;
        }
        Log.i("{OngoingActivityCardStackView}", "disableTopCardGuts: change visibility: INVISIBLE. withoutAni:" + z);
        findViewById.setBackgroundResource(R.drawable.sec_ongoing_card_bg);
        findViewById2.setVisibility(0);
        if (z) {
            removeTopCardGuts(childAt);
            View view2 = this.guts;
            if (view2 != null) {
                view2.setVisibility(8);
                return;
            }
            return;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(360L);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation2.setDuration(360L);
        alphaAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$disableTopCardGuts$alphaAnimationOut$1$1
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                CardStackView cardStackView = CardStackView.this;
                if (cardStackView.gutsDisplay) {
                    return;
                }
                cardStackView.removeTopCardGuts(childAt);
                View view3 = CardStackView.this.guts;
                if (view3 != null) {
                    view3.setVisibility(8);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        findViewById2.startAnimation(alphaAnimation);
        View view3 = this.guts;
        if (view3 != null) {
            view3.startAnimation(alphaAnimation2);
        }
    }

    public final int getCardBg() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
            Log.i("{OngoingActivityCardStackView}", "getCardBg : ongoingActivity is null ##");
            return 0;
        }
        if (OngoingActivityDataHelper.getDataByIndex(0).mIsMediaOngoingData) {
            return 0;
        }
        return getContext().getResources().getColor(R.color.ongoing_activity_card_item_background_color);
    }

    public final float getCardInitialY(int i) {
        float cardInitialScale = getCardInitialScale(i);
        if (getTopViewIndex() <= 0) {
            return 0.0f;
        }
        return (((1.0f - cardInitialScale) * (getChildAt(getTopViewIndex()).findViewById(R.id.stack_pip_layout) != null ? r1.getMeasuredHeight() : 0)) / 2) + (i * this.viewSpacing * 0.8f);
    }

    public final List getCurrentViewStatusList() {
        List list = CollectionsKt___CollectionsKt.toList(new IntRange(0, getTopViewIndex()));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            View childAt = getChildAt(((Number) it.next()).intValue());
            ViewStatus viewStatus = childAt != null ? new ViewStatus(new PointF(childAt.getX(), childAt.getY()), getBaseColorView(childAt).getAlpha(), childAt.getScaleX(), childAt.getScaleY(), childAt.getContext()) : null;
            viewStatus.getClass();
            PointF pointF = viewStatus.point;
            Log.d("{OngoingActivityCardStackView}", "getCurrentViewStatusList() : x=" + pointF.x + ", y=" + pointF.y);
            arrayList.add(viewStatus);
        }
        return arrayList;
    }

    public final List getEndViewStatusList() {
        isMonochromeCard();
        List list = CollectionsKt___CollectionsKt.toList(new IntRange(0, getTopViewIndex()));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int intValue = ((Number) it.next()).intValue();
            View childAt = getChildAt(intValue);
            int topViewIndex = getTopViewIndex() - ((intValue + 1) % getChildCount());
            childAt.getClass();
            PointF pointF = new PointF((getWidth() - childAt.getMeasuredWidth()) / 2.0f, getCardInitialY(topViewIndex));
            CardStackViewUtils.Alpha.INSTANCE.getClass();
            arrayList.add(new ViewStatus(pointF, CardStackViewUtils.Alpha.MonochromeAlpha.INSTANCE.getUnderlayBaseColor(topViewIndex), getCardInitialScale(topViewIndex), getCardInitialScale(topViewIndex), getContext()));
        }
        return arrayList;
    }

    public final List getStartViewStatusList() {
        isMonochromeCard();
        List list = CollectionsKt___CollectionsKt.toList(new IntRange(0, getTopViewIndex()));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int intValue = ((Number) it.next()).intValue();
            View childAt = getChildAt(intValue);
            int topViewIndex = getTopViewIndex() - intValue;
            childAt.getClass();
            PointF pointF = new PointF((getWidth() - childAt.getMeasuredWidth()) / 2.0f, getCardInitialY(topViewIndex));
            CardStackViewUtils.Alpha.INSTANCE.getClass();
            arrayList.add(new ViewStatus(pointF, CardStackViewUtils.Alpha.MonochromeAlpha.INSTANCE.getUnderlayBaseColor(topViewIndex), getCardInitialScale(topViewIndex), getCardInitialScale(topViewIndex), getContext()));
        }
        return arrayList;
    }

    public final int getTopViewIndex() {
        return getChildCount() - 1;
    }

    public final PointF getTouchPoint(MotionEvent motionEvent) {
        int findPointerIndex = motionEvent.findPointerIndex(this.touchPointerId);
        if (findPointerIndex >= 0) {
            return new PointF(motionEvent.getX(findPointerIndex), motionEvent.getY(findPointerIndex));
        }
        return null;
    }

    public final boolean isReadyCollapseAnimation() {
        return !(this.isRunningExpandAnimation | this.isAnimating | this.isRunningCollapseAnimation);
    }

    public final void layoutItem(View view, int i, int i2) {
        int width = (int) ((getWidth() - view.getMeasuredWidth()) / 2.0f);
        float cardInitialY = getCardInitialY(i2);
        view.layout(width, getPaddingTop(), view.getMeasuredWidth() + width, view.getMeasuredHeight() + getPaddingTop());
        if (this.enableElevation) {
            view.setTranslationZ(i);
        }
        view.setY(cardInitialY);
        view.setX(0.0f);
        NotificationActionListLayout findViewById = view.findViewById(android.R.id.animation);
        if (findViewById != null) {
            for (View view2 : ConvenienceExtensionsKt.getChildren(findViewById)) {
                if (view2 instanceof TextView) {
                    ((TextView) view2).setHorizontallyScrolling(false);
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        View childAt;
        PointF touchPoint = getTouchPoint(motionEvent);
        if (touchPoint == null || (childAt = getChildAt(getTopViewIndex())) == null || touchPoint.y <= childAt.getHeight()) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.touchPointerId = motionEvent.getPointerId(0);
                this.initialTouchPoint = new PointF(motionEvent.getX(0), motionEvent.getY(0));
                View childAt2 = getChildAt(getTopViewIndex());
                if (childAt2 != null) {
                    setActionButtonClickable(childAt2, true);
                }
                int topViewIndex = getTopViewIndex();
                for (int i = 0; i < topViewIndex; i++) {
                    View childAt3 = getChildAt(i);
                    if (childAt3 != null) {
                        setActionButtonClickable(childAt3, false);
                    }
                }
            } else if (action == 2) {
                PointF touchPoint2 = getTouchPoint(motionEvent);
                if (!this.isChildTouchEventIntercepting) {
                    if (touchPoint2 == null) {
                        this.isChildTouchEventIntercepting = false;
                        return false;
                    }
                    PointF pointF = this.initialTouchPoint;
                    PointF pointF2 = new PointF(touchPoint2.x, touchPoint2.y);
                    pointF2.offset(-pointF.x, -pointF.y);
                    if (Math.abs(pointF2.y) + Math.abs(pointF2.x) > TypedValue.applyDimension(1, 16.0f, this.displayMetrics)) {
                        this.isChildTouchEventIntercepting = true;
                        startDrag(motionEvent);
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        OngoingCardAdapter ongoingCardAdapter = this.adapter;
        if (ongoingCardAdapter == null || ongoingCardAdapter.isEmpty()) {
            this.currentIndex = 0;
            removeAllViewsInLayout();
            return;
        }
        if (this.gutsClosedCheck) {
            this.gutsClosedCheck = false;
            disableTopCardGuts(false);
            onLayout(z, i, i2, i3, i4);
            return;
        }
        connectCardItem();
        updateItemBg();
        this.startViewStatusList = getStartViewStatusList();
        this.endViewStatusList = getEndViewStatusList();
        if (this.pendingAnimation) {
            this.pendingAnimation = false;
            Log.d("{OngoingActivityCardStackView}", "onLayout call expandAnimation()");
            this.sceneList.clear();
            int childCount = getChildCount();
            int i5 = 0;
            boolean z2 = false;
            while (true) {
                View view = null;
                if (i5 >= childCount) {
                    break;
                }
                ViewGroup viewGroup = (ViewGroup) getChildAt(i5);
                OngoingCardAdapter ongoingCardAdapter2 = this.adapter;
                ongoingCardAdapter2.getClass();
                OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = ongoingCardAdapter2.getMediaCardView;
                if (ongoingCardController$$ExternalSyntheticLambda0 == null) {
                    Log.e("MediaOngoingActivity", "getMediaCard. lambda is not initialized");
                } else {
                    view = (View) ongoingCardController$$ExternalSyntheticLambda0.mo779invoke(Unit.INSTANCE);
                }
                if (Intrinsics.areEqual(view, viewGroup)) {
                    z2 = true;
                }
                Scene scene = new Scene(viewGroup, viewGroup.findViewById(R.id.stack_pip_layout));
                final Scene scene2 = new Scene(viewGroup, viewGroup.findViewById(R.id.stack_pip_layout));
                this.sceneList.add(new Pair(scene2, scene));
                scene2.setEnterAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$expandAnimation$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CardStackView cardStackView = CardStackView.this;
                        ViewGroup sceneRoot = scene2.getSceneRoot();
                        CardStackView.Companion companion = CardStackView.Companion;
                        cardStackView.getClass();
                        View findViewById = sceneRoot.findViewById(R.id.stack_pip_layout);
                        findViewById.getClass();
                        CardStackView.changeLayoutSize(findViewById, cardStackView.pendingWidth, cardStackView.pendingHeight);
                        View findViewById2 = sceneRoot.findViewById(R.id.pip_dummy_chip_layout);
                        findViewById2.getClass();
                        CardStackView.changeLayoutSize(findViewById2, cardStackView.pendingWidth, cardStackView.pendingHeight);
                        View findViewById3 = sceneRoot.findViewById(R.id.pip_dummy_chip_layout);
                        findViewById3.getClass();
                        CardStackView.changeLayoutMargin(findViewById3, 0);
                        View findViewById4 = sceneRoot.findViewById(R.id.pip_dummy_chip_layout);
                        findViewById4.getClass();
                        findViewById4.setVisibility(0);
                    }
                });
                scene2.enter();
                i5++;
                z2 = z2;
            }
            if (!z2) {
                OngoingCardAdapter ongoingCardAdapter3 = this.adapter;
                View detachedMediaView = ongoingCardAdapter3 != null ? ongoingCardAdapter3.getDetachedMediaView() : null;
                if (detachedMediaView == null) {
                    Log.e("MediaOngoingActivity", "expandMediaCard cannot find media card");
                } else {
                    View findViewById = detachedMediaView.findViewById(R.id.stack_expand_contents);
                    View findViewById2 = detachedMediaView.findViewById(R.id.stack_pip_layout);
                    View findViewById3 = detachedMediaView.findViewById(R.id.pip_dummy_chip_layout);
                    if (findViewById3 != null) {
                        findViewById3.setVisibility(4);
                    }
                    if (findViewById != null) {
                        findViewById.setVisibility(0);
                    }
                    ViewGroup.LayoutParams layoutParams = findViewById2 != null ? findViewById2.getLayoutParams() : null;
                    if (layoutParams != null) {
                        layoutParams.width = getWidth();
                    }
                    if (layoutParams != null) {
                        layoutParams.height = -2;
                    }
                    if (findViewById2 != null) {
                        findViewById2.setLayoutParams(layoutParams);
                    }
                }
            }
            Log.d("{OngoingActivityCardStackView}", "expandAnimation addOnLayoutChangeListener");
            View view2 = this.decorView;
            view2.getClass();
            view2.addOnLayoutChangeListener(this.layoutListener);
        }
        super.onLayout(z, getLeft(), getTop(), getRight(), getBottom());
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.currentIndex = bundle.getInt("currentIndex");
            parcelable = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt("currentIndex", this.currentIndex);
        bundle.putParcelable("superState", super.onSaveInstanceState());
        return bundle;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00df, code lost:
    
        if (r18.touchPoint.y > getChildAt(getTopViewIndex() - 1).getHeight()) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00f2, code lost:
    
        r1.setPressed(true);
        startDrag(r19);
        r18.cardSwipeState = com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView.CardSwipeState.INIT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0106, code lost:
    
        if (r18.startViewStatusList.size() != getChildCount()) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0108, code lost:
    
        r1 = getTopViewIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x010c, code lost:
    
        if (r1 < 0) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x010e, code lost:
    
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x010f, code lost:
    
        r6 = getChildAt(r4);
        r7 = (com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView.ViewStatus) r18.startViewStatusList.get(r4);
        r6.setX(r7.point.x);
        r6.setY(r7.point.y);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0129, code lost:
    
        if (r4 == r1) goto L202;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x012b, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0151, code lost:
    
        com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper.INSTANCE.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x015c, code lost:
    
        if (com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0164, code lost:
    
        if (com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper.getDataByIndex(0).mIsMediaOngoingData == false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0168, code lost:
    
        r1 = r18.gutsControlState;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x016c, code lost:
    
        if (r1 == com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView.GutsControlState.INIT) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x016e, code lost:
    
        android.util.Log.i("{OngoingActivityCardStackView}", "startLongPressTimer stop by GutsControlState:" + r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0180, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0181, code lost:
    
        r1 = getHandler();
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0185, code lost:
    
        if (r1 == null) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0187, code lost:
    
        r1.removeCallbacks(r18.longPressRunnable);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x018c, code lost:
    
        r1 = getHandler();
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0190, code lost:
    
        if (r1 == null) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0192, code lost:
    
        r1.postDelayed(r18.longPressRunnable, r18.gutsPressDelay);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0199, code lost:
    
        r18.gutsControlState = com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView.GutsControlState.PRESS;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x019d, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x012e, code lost:
    
        android.util.Log.w("{OngoingActivityCardStackView}", "onTouchEvent ACTION_DOWN startViewStatusList.size:" + r18.startViewStatusList.size() + " != childCount:" + getChildCount());
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00ee, code lost:
    
        if (r18.touchPoint.y > r1.getHeight()) goto L87;
     */
    /* JADX WARN: Removed duplicated region for block: B:136:0x040f  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r19) {
        /*
            Method dump skipped, instructions count: 1164
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void removeBottomCardShadow() {
        int topViewIndex = getTopViewIndex();
        if (topViewIndex < 0) {
            return;
        }
        int i = 0;
        while (true) {
            ViewGroup viewGroup = (ViewGroup) getChildAt(i).findViewWithTag("BottomCardShadowView");
            View findViewById = getChildAt(i).findViewById(R.id.stack_pip_layout);
            if (viewGroup != null) {
                viewGroup.setVisibility(4);
            }
            if (viewGroup != null) {
                updateBottomCardShadowLP$default(this, viewGroup, this.viewSpacing, findViewById);
            }
            if (i == topViewIndex) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void removeTopCardGuts(View view) {
        View view2;
        View findViewById = view.findViewById(R.id.stack_pip_layout);
        if (findViewById == null || (view2 = this.guts) == null) {
            Log.e("{OngoingActivityCardStackView}", "removeTopCardGuts fail");
            return;
        }
        ((ViewGroup) findViewById).removeView(view2);
        this.guts = null;
        this.gutsContents = null;
        Log.i("{OngoingActivityCardStackView}", "Ongoing activity removeTopCardGuts done");
    }

    public final void resetAllParentsClipConfig$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        ViewGroup viewGroup = this;
        while (viewGroup.getParent() != null && (viewGroup.getParent() instanceof ViewGroup)) {
            viewGroup = (ViewGroup) viewGroup.getParent();
            Boolean bool = (Boolean) ((LinkedHashMap) this.originalAllParentsClipChildrenConfig).get(Integer.valueOf(viewGroup.getId()));
            boolean z = true;
            viewGroup.setClipChildren(bool != null ? bool.booleanValue() : true);
            Boolean bool2 = (Boolean) ((LinkedHashMap) this.originalAllParentsClipToPaddingConfig).get(Integer.valueOf(viewGroup.getId()));
            if (bool2 != null) {
                z = bool2.booleanValue();
            }
            viewGroup.setClipToPadding(z);
        }
        ((LinkedHashMap) this.originalAllParentsClipChildrenConfig).clear();
        ((LinkedHashMap) this.originalAllParentsClipToPaddingConfig).clear();
    }

    public final void resetItemPosition(boolean z) {
        ViewGroup viewGroup;
        final CardStackView cardStackView = this;
        Log.i("{OngoingActivityCardStackView}", "resetItemPosition()");
        List currentViewStatusList = cardStackView.getCurrentViewStatusList();
        CustomAnimationSet customAnimationSet = cardStackView.resetAnimationSet;
        Log.i("{OngoingActivityCardStackView}", "resetAnimationSet run()");
        int topViewIndex = cardStackView.getTopViewIndex();
        if (topViewIndex >= 0) {
            final int i = 0;
            while (true) {
                final View childAt = cardStackView.getChildAt(i);
                final ViewStatus viewStatus = (ViewStatus) ((ArrayList) currentViewStatusList).get(i);
                final ViewStatus viewStatus2 = (ViewStatus) cardStackView.startViewStatusList.get(i);
                childAt.getClass();
                customAnimationSet.add(childAt, DynamicAnimation.SCALE_X, viewStatus2.scaleX, 0.8131728f, 200.0f, 0L);
                customAnimationSet.add(childAt, DynamicAnimation.SCALE_Y, viewStatus2.scaleY, 0.8131728f, 200.0f, 0L);
                customAnimationSet.add(childAt, DynamicAnimation.X, viewStatus2.point.x, 0.78f, 200.0f, 0L);
                customAnimationSet.add(childAt, DynamicAnimation.Y, viewStatus2.point.y, 0.78f, 200.0f, 0L);
                final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(480L);
                ofFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
                if (z) {
                    ofFloat.setInterpolator(alphaInterpolator);
                    ofFloat.setDuration(600L);
                }
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                View childAt2 = cardStackView.getChildAt(cardStackView.getTopViewIndex());
                ref$BooleanRef.element = (childAt2 == null || (viewGroup = (ViewGroup) childAt2.findViewWithTag("BottomCardShadowView")) == null || viewGroup.getVisibility() != 0) ? false : true;
                int i2 = topViewIndex;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$resetItemPosition$1$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) ofFloat.getAnimatedValue()).floatValue();
                        CardStackView cardStackView2 = cardStackView;
                        View view = childAt;
                        view.getClass();
                        CardStackView.Companion companion = CardStackView.Companion;
                        cardStackView2.getClass();
                        View baseColorView = CardStackView.getBaseColorView(view);
                        float f = viewStatus.baseViewAlpha;
                        baseColorView.setAlpha(((viewStatus2.baseViewAlpha - f) * floatValue) + f);
                        if (i != cardStackView.getTopViewIndex() || floatValue < 0.5f || ref$BooleanRef.element) {
                            return;
                        }
                        r4.showBottomCardShadowIfNeeded(cardStackView.getTopViewIndex());
                        ref$BooleanRef.element = true;
                    }
                });
                customAnimationSet.add(0L, ofFloat);
                if (i == i2) {
                    break;
                }
                i++;
                cardStackView = this;
                topViewIndex = i2;
            }
        }
        customAnimationSet.start();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x017d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void resetTouchEvent() {
        /*
            Method dump skipped, instructions count: 402
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView.resetTouchEvent():void");
    }

    public final void sendDismiss() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
            Log.i("{OngoingActivityCardStackView}", "OngoingActivityDataHelper.getDataSize() == 0");
            return;
        }
        final String str = OngoingActivityDataHelper.getDataByIndex(0).mNotiID;
        this.removingSbnId = str;
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Card swipe dismiss. removingSbnId:", str, "{OngoingActivityCardStackView}");
        OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = this.dismiss;
        if (ongoingCardController$$ExternalSyntheticLambda0 != null) {
            ongoingCardController$$ExternalSyntheticLambda0.mo779invoke(str);
        }
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$sendDismiss$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                if (Intrinsics.areEqual(CardStackView.this.removingSbnId, "")) {
                    return;
                }
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Can't receive entryRemoved yet. force clear for sbnId:", str, "{OngoingActivityCardStackView}");
                OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                String str2 = CardStackView.this.removingSbnId;
                ongoingActivityDataHelper.getClass();
                OngoingActivityDataHelper.removeOngoingActivityByKey(str2);
                CardStackView cardStackView = CardStackView.this;
                cardStackView.removingSbnId = "";
                cardStackView.isRunningSwipeDismissTopCardMove = false;
                cardStackView.isAnimating = false;
            }
        };
        Handler handler = getHandler();
        if (handler != null) {
            handler.postDelayed(runnable, 500L);
        }
    }

    public final void setAllParentsClipConfig() {
        ViewGroup viewGroup = this;
        while (viewGroup.getParent() != null && (viewGroup.getParent() instanceof ViewGroup)) {
            viewGroup = (ViewGroup) viewGroup.getParent();
            this.originalAllParentsClipChildrenConfig.put(Integer.valueOf(viewGroup.getId()), Boolean.valueOf(viewGroup.getClipChildren()));
            this.originalAllParentsClipToPaddingConfig.put(Integer.valueOf(viewGroup.getId()), Boolean.valueOf(viewGroup.getClipToPadding()));
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }
    }

    public final void setCardColor(View view, int i) {
        isMonochromeCard();
        if (i >= 3) {
            throw new Exception("Not defined index");
        }
        if (this.isAnimating) {
            return;
        }
        View baseColorView = getBaseColorView(view);
        CardStackViewUtils.Alpha.INSTANCE.getClass();
        baseColorView.setAlpha(CardStackViewUtils.Alpha.MonochromeAlpha.INSTANCE.getUnderlayBaseColor(i));
    }

    public final void showBottomCardShadowIfNeeded(int i) {
        removeBottomCardShadow();
        if (getChildCount() < 2) {
            return;
        }
        Log.i("{OngoingActivityCardStackView}", "enabling dummy shadow  - secondary ?" + (i == getTopViewIndex()));
        updateBottomCardShadow();
        View childAt = getChildAt(i);
        final ViewGroup viewGroup = childAt != null ? (ViewGroup) childAt.findViewWithTag("BottomCardShadowView") : null;
        View childAt2 = getChildAt(i);
        updateBottomCardShadowLP$default(this, viewGroup, this.viewSpacing, childAt2 != null ? childAt2.findViewById(R.id.stack_pip_layout) : null);
        if (viewGroup != null) {
            viewGroup.setVisibility(4);
        }
        float floatValue = (viewGroup != null ? Float.valueOf(viewGroup.getY()) : null).floatValue();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(floatValue - this.viewSpacing, floatValue);
        ofFloat.setDuration(50L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$showBottomCardShadowIfNeeded$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewGroup viewGroup2;
                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (floatValue2 >= 0.0f && (viewGroup2 = viewGroup) != null) {
                    viewGroup2.setVisibility(0);
                }
                ViewGroup viewGroup3 = viewGroup;
                if (viewGroup3 != null) {
                    viewGroup3.setY(floatValue2);
                }
            }
        });
        ofFloat.start();
    }

    public final void startDrag(MotionEvent motionEvent) {
        requestDisallowInterceptTouchEvent(true);
        this.touchPointerId = motionEvent.getPointerId(0);
        this.initialTouchPoint = new PointF(motionEvent.getX(0), motionEvent.getY(0));
        this.swipeAnimationSet.clear();
        this.swipeHorizontalAnimationSet.clear();
        this.resetAnimationSet.clear();
        setAllParentsClipConfig();
        OngoingCardController.AnonymousClass6 anonymousClass6 = this.onChangeListener;
        if (anonymousClass6 != null) {
            OngoingCardController.this.onAllowStateChanged(true);
        }
        this.isDragging = true;
        View childAt = getChildAt(getChildCount() - 1);
        if (childAt != null) {
            childAt.setScaleX(0.99f);
            childAt.setScaleY(0.99f);
        }
    }

    public final void stopLongPressChecker() {
        GutsControlState gutsControlState = this.gutsControlState;
        if (gutsControlState != GutsControlState.PRESS) {
            Log.i("{OngoingActivityCardStackView}", "stopLongPressChecker stop by GutsControlState:" + gutsControlState);
        } else {
            Handler handler = getHandler();
            if (handler != null) {
                handler.removeCallbacks(this.longPressRunnable);
            }
            this.gutsControlState = GutsControlState.INIT;
        }
    }

    public final void swipeItemToHorizontal(final boolean z) {
        Log.i("{OngoingActivityCardStackView}", "swipeItemToHorizontal()");
        OngoingActivityDataHelper.INSTANCE.getClass();
        int i = 0;
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() != 0) {
            NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_ONGOING_DISMISS_EXPAND_VIEW, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
        }
        if (this.gutsDisplay) {
            disableTopCardGuts(true);
        }
        this.isRunningSwipeDismissTopCardMove = true;
        List currentViewStatusList = getCurrentViewStatusList();
        float f = (this.centerDp + this.cardSwipeEndDp) * (z ? 1 : -1) * this.dpToFlot;
        final View childAt = getChildAt(getTopViewIndex());
        SpringAnimation springAnimation = new SpringAnimation(childAt, DynamicAnimation.TRANSLATION_X, f);
        SpringForce springForce = springAnimation.mSpring;
        springForce.setStiffness(200.0f);
        springForce.setDampingRatio(0.8131728f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$swipeItemToHorizontal$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f2, float f3) {
                float x = z ? childAt.getX() : -childAt.getX();
                CardStackView cardStackView = this;
                int i2 = cardStackView.centerDp;
                int i3 = cardStackView.cardSwipeEndDp;
                float f4 = cardStackView.dpToFlot;
                float f5 = (i2 + i3) * f4;
                if (x >= f5) {
                    childAt.setAlpha(0.0f);
                    return;
                }
                if (x >= cardStackView.cardSwipeStartDp * f4) {
                    childAt.setAlpha((f5 - x) / (((i2 - r6) + i3) * f4));
                }
            }
        });
        if (getTopViewIndex() <= 0) {
            springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$swipeItemToHorizontal$1$2
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f2, float f3) {
                    Log.i("{OngoingActivityCardStackView}", "Horizon swipe ani done. sendDismiss");
                    CardStackView.Companion companion = CardStackView.Companion;
                    CardStackView cardStackView = CardStackView.this;
                    cardStackView.sendDismiss();
                    cardStackView.isAnimating = false;
                    cardStackView.resetAllParentsClipConfig$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                    OngoingCardController.AnonymousClass6 anonymousClass6 = cardStackView.onChangeListener;
                    if (anonymousClass6 != null) {
                        OngoingCardController.this.onAllowStateChanged(false);
                    }
                }
            });
        } else {
            springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$swipeItemToHorizontal$1$3
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f2, float f3) {
                    Log.i("{OngoingActivityCardStackView}", "Horizon swipe ani done");
                }
            });
        }
        springAnimation.start();
        if (getTopViewIndex() <= 0) {
            return;
        }
        CustomAnimationSet customAnimationSet = this.swipeHorizontalAnimationSet;
        Log.i("{OngoingActivityCardStackView}", "SwipeHorizontal AnimationSet run()");
        int topViewIndex = getTopViewIndex();
        while (i < topViewIndex) {
            final View childAt2 = getChildAt(i);
            final ViewStatus viewStatus = (ViewStatus) ((ArrayList) currentViewStatusList).get(i);
            final ViewStatus viewStatus2 = (ViewStatus) this.endViewStatusList.get(i);
            int i2 = i + 1;
            int childCount = i2 % getChildCount();
            childAt2.getClass();
            customAnimationSet.add(childAt2, DynamicAnimation.X, viewStatus2.point.x, 0.78f, 100.0f, 0L);
            customAnimationSet.add(childAt2, DynamicAnimation.Y, viewStatus2.point.y, 0.78f, 100.0f, 0L);
            customAnimationSet.add(childAt2, DynamicAnimation.SCALE_X, viewStatus2.scaleX, 0.6f, 200.0f, 0L);
            customAnimationSet.add(childAt2, DynamicAnimation.SCALE_Y, viewStatus2.scaleY, 0.6f, 200.0f, 0L);
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(480L);
            ofFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$swipeItemToHorizontal$2$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) ofFloat.getAnimatedValue()).floatValue();
                    CardStackView cardStackView = this;
                    View view = childAt2;
                    view.getClass();
                    CardStackView.Companion companion = CardStackView.Companion;
                    cardStackView.getClass();
                    View baseColorView = CardStackView.getBaseColorView(view);
                    float f2 = viewStatus.baseViewAlpha;
                    baseColorView.setAlpha(((viewStatus2.baseViewAlpha - f2) * floatValue) + f2);
                }
            });
            customAnimationSet.add(0L, ofFloat);
            childAt2.setTranslationZ(childCount);
            topViewIndex = topViewIndex;
            i = i2;
        }
        customAnimationSet.start();
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() > 2) {
            showBottomCardShadowIfNeeded(getTopViewIndex() - 1);
        }
    }

    public final void updateBottomCardShadow() {
        int topViewIndex;
        if (getChildCount() < 2 || (topViewIndex = getTopViewIndex()) < 0) {
            return;
        }
        int i = 0;
        while (true) {
            Log.i("{OngoingActivityCardStackView}", "Create dummy shadow needed? " + getChildAt(i).findViewWithTag("BottomCardShadowView"));
            View findViewById = getChildAt(i).findViewById(R.id.stack_pip_layout);
            ViewGroup viewGroup = (ViewGroup) getChildAt(i).findViewWithTag("BottomCardShadowView");
            ViewGroup viewGroup2 = viewGroup;
            if (viewGroup == null) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                frameLayout.setTag("BottomCardShadowView");
                frameLayout.setBackground(frameLayout.getContext().getDrawable(R.drawable.sec_ongoing_card_bg));
                frameLayout.setAlpha(0.3f);
                frameLayout.setScaleX(getCardInitialScale(1));
                frameLayout.setFocusableInTouchMode(false);
                frameLayout.setFocusable(false);
                if (getTopViewIndex() == i) {
                    frameLayout.setVisibility(0);
                } else {
                    frameLayout.setVisibility(4);
                }
                ViewGroup viewGroup3 = (ViewGroup) getChildAt(i).findViewById(R.id.item_root);
                if (viewGroup3 != null) {
                    viewGroup3.addView(frameLayout);
                }
                getChildAt(i).requestLayout();
                viewGroup2 = frameLayout;
            }
            Resources resources = getContext().getResources();
            OngoingActivityDataHelper.INSTANCE.getClass();
            viewGroup2.setContentDescription(resources.getQuantityString(R.plurals.ongoing_activity_card_count, 1, Integer.valueOf(OngoingActivityDataHelper.mOngoingActivityLists.size())));
            updateBottomCardShadowLP$default(this, viewGroup2, this.viewSpacing, findViewById);
            if (i == topViewIndex) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void updateItem$1() {
        View view;
        OngoingCardAdapter ongoingCardAdapter = this.adapter;
        if (ongoingCardAdapter != null) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            int min = Math.min(OngoingActivityDataHelper.mOngoingActivityLists.size(), this.stackMaxSize);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                int i2 = (min - i) - 1;
                if (i2 < 0) {
                    break;
                }
                OngoingActivityDataHelper.INSTANCE.getClass();
                OngoingActivityData dataByIndex = OngoingActivityDataHelper.getDataByIndex(i2);
                if (dataByIndex.mIsMediaOngoingData) {
                    removeView(getChildAt(i));
                    addView(ongoingCardAdapter.getDetachedMediaView(), i);
                    ongoingCardAdapter.inflateDummyChipView(dataByIndex);
                } else {
                    View childAt = getChildAt(i);
                    OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = ongoingCardAdapter.getMediaCardView;
                    if (ongoingCardController$$ExternalSyntheticLambda0 == null) {
                        Log.e("MediaOngoingActivity", "getMediaCard. lambda is not initialized");
                        view = null;
                    } else {
                        view = (View) ongoingCardController$$ExternalSyntheticLambda0.mo779invoke(Unit.INSTANCE);
                    }
                    if (Intrinsics.areEqual(view, childAt)) {
                        removeView(getChildAt(i));
                        addItem(i, getChildCount() + i, ongoingCardAdapter.getView(i2, null, this), false);
                    } else {
                        ongoingCardAdapter.bindView(getChildAt(i), i2);
                    }
                }
            }
            updateItemBg();
            if (this.onChangeListener != null) {
                OngoingCardController.AnonymousClass6.onChange(ongoingCardAdapter.getCount(), ongoingCardAdapter.getCount());
            }
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        OngoingActivityDataHelper.updateTopIndex();
        if (getChildCount() == this.startViewStatusList.size() && getChildCount() == this.endViewStatusList.size()) {
            return;
        }
        int topViewIndex = getTopViewIndex();
        int size = this.startViewStatusList.size();
        int size2 = this.endViewStatusList.size();
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(topViewIndex, size, "updateItem index mismatch. topViewIndex:", ", startViewStatusList.size:", ", endViewStatusList.size:");
        m.append(size2);
        Log.w("{OngoingActivityCardStackView}", m.toString());
        this.startViewStatusList = getStartViewStatusList();
        this.endViewStatusList = getEndViewStatusList();
    }

    public final void updateItemBg() {
        getCardBg();
        getCardBg();
        int topViewIndex = getTopViewIndex();
        if (topViewIndex < 0) {
            return;
        }
        int i = 0;
        while (true) {
            View childAt = getChildAt(i);
            int topViewIndex2 = getTopViewIndex() - i;
            if (this.removingSbnId.equals("")) {
                childAt.getClass();
                setCardColor(childAt, topViewIndex2);
            } else {
                Log.i("{OngoingActivityCardStackView}", "updateItemBg setCardColor by fixed index");
                childAt.getClass();
                if (topViewIndex2 > 0) {
                    topViewIndex2--;
                }
                setCardColor(childAt, topViewIndex2);
            }
            if (i == topViewIndex) {
                return;
            } else {
                i++;
            }
        }
    }

    public CardStackView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ CardStackView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$layoutListener$1] */
    /* JADX WARN: Type inference failed for: r6v17, types: [com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$longPressRunnable$1] */
    /* JADX WARN: Type inference failed for: r6v8, types: [com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$dataObserver$1] */
    public CardStackView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.displayMetrics = getResources().getDisplayMetrics();
        this.stackMaxSize = 2;
        this.enableElevation = true;
        this.initialTouchPoint = new PointF(0.0f, 0.0f);
        EmptyList emptyList = EmptyList.INSTANCE;
        this.startViewStatusList = emptyList;
        this.endViewStatusList = emptyList;
        this.swipeAnimationSet = new CustomAnimationSet(new CardStackView$$ExternalSyntheticLambda0(this, 0));
        this.swipeHorizontalAnimationSet = new CustomAnimationSet(new CardStackView$$ExternalSyntheticLambda0(this, 1));
        this.resetAnimationSet = new CustomAnimationSet(new CardStackView$$ExternalSyntheticLambda0(this, 2));
        this.originalAllParentsClipChildrenConfig = new LinkedHashMap();
        this.originalAllParentsClipToPaddingConfig = new LinkedHashMap();
        this.dataObserver = new DataSetObserver() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$dataObserver$1
            @Override // android.database.DataSetObserver
            public final void onChanged() {
                super.onChanged();
                Log.i("{OngoingActivityCardStackView}", "onChanged()");
                CardStackView.this.invalidate();
                CardStackView.this.requestLayout();
            }
        };
        this.removingSbnId = "";
        this.cardSwipeState = CardSwipeState.INIT;
        this.dpToFlot = Resources.getSystem().getDisplayMetrics().density;
        this.centerDp = 180;
        this.cardSwipeStartDp = 126;
        this.cardSwipeEndDp = 150;
        this.gutsPressDelay = 400L;
        this.longPressRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$longPressRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                int i2;
                View view;
                Icon icon;
                String str;
                GutContentInitializer.OnSettingsClickListener onSettingsClickListener;
                PendingIntent pendingIntent;
                Icon icon2;
                CardStackView cardStackView = CardStackView.this;
                if (cardStackView.gutsControlState != CardStackView.GutsControlState.PRESS) {
                    cardStackView.gutsControlState = CardStackView.GutsControlState.INIT;
                    return;
                }
                if (cardStackView.cardSwipeState == CardStackView.CardSwipeState.INIT) {
                    cardStackView.removeBottomCardShadow();
                    final CardStackView cardStackView2 = CardStackView.this;
                    if (cardStackView2.gutsDisplay) {
                        cardStackView2.disableTopCardGuts(false);
                    } else {
                        cardStackView2.gutsDisplay = true;
                        View childAt = cardStackView2.getChildAt(cardStackView2.getTopViewIndex());
                        if (childAt != null) {
                            final View findViewById = childAt.findViewById(R.id.stack_pip_layout);
                            final View findViewById2 = childAt.findViewById(R.id.stack_expand_contents);
                            View view2 = cardStackView2.guts;
                            if (view2 == null) {
                                if (view2 == null) {
                                    Log.i("{OngoingActivityCardStackView}", "addTopCardGuts: notification_guts is not inflated. do inflate() with ViewStub");
                                    ViewGroup viewGroup = (ViewGroup) childAt.findViewById(R.id.stack_pip_layout);
                                    if (viewGroup == null) {
                                        Log.e("{OngoingActivityCardStackView}", "addTopCardGuts: Cannot find stack_pip_layout");
                                    } else {
                                        OngoingActivityDataHelper.INSTANCE.getClass();
                                        if (OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
                                            Log.e("{OngoingActivityCardStackView}", "There is no OA data for GUTS");
                                        } else {
                                            ViewStub viewStub = new ViewStub(cardStackView2.getContext());
                                            viewStub.setLayoutResource(R.layout.notification_guts);
                                            viewStub.setInflatedId(R.id.notification_guts);
                                            viewStub.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                                            viewGroup.addView(viewStub);
                                            cardStackView2.guts = viewStub.inflate();
                                            View inflate = LayoutInflater.from(cardStackView2.getContext()).inflate(R.layout.sec_notification_app_info, (ViewGroup) null, false);
                                            cardStackView2.gutsContents = inflate;
                                            ((NotificationGuts) cardStackView2.guts).setGutsContent((SecNotificationAppInfo) inflate);
                                            CardStackView$addTopCardGuts$listener$1 cardStackView$addTopCardGuts$listener$1 = new CardStackView$addTopCardGuts$listener$1(cardStackView2);
                                            NotificationGuts notificationGuts = (NotificationGuts) cardStackView2.guts;
                                            notificationGuts.getClass();
                                            Log.i("NotificationGuts", "mClosedListenerForOngoingActivity added");
                                            notificationGuts.mClosedListenerForOngoingActivity = cardStackView$addTopCardGuts$listener$1;
                                            View view3 = cardStackView2.gutsContents;
                                            ((SecNotificationAppInfo) view3).mGutsContainer = (NotificationGuts) cardStackView2.guts;
                                            if (view3 == null) {
                                                Log.e("{OngoingActivityCardStackView}", "addTopCardGuts: Cannot find notification_guts from view");
                                            } else if (childAt.findViewById(R.id.notification_guts_container) == null) {
                                                Log.e("{OngoingActivityCardStackView}", "addTopCardGuts: Cannot find notification_guts_container from view");
                                            } else {
                                                final OngoingActivityData dataByIndex = OngoingActivityDataHelper.getDataByIndex(0);
                                                PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(dataByIndex.mUserId, cardStackView2.getContext());
                                                INotificationManager iNotificationManager = (INotificationManager) Dependency.sDependency.getDependencyInner(INotificationManager.class);
                                                NotificationChannel channel = dataByIndex.mNotificationEntry.mRanking.getChannel();
                                                final NotificationGutsManager notificationGutsManager = (NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class);
                                                final NotificationEntry notificationEntry = dataByIndex.mNotificationEntry;
                                                GutContentInitializer.OnSettingsClickListener onSettingsClickListener2 = new GutContentInitializer.OnSettingsClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$addTopCardGuts$onSettingsClick$1
                                                    @Override // com.android.systemui.statusbar.notification.row.GutContentInitializer.OnSettingsClickListener
                                                    public final void onClick() {
                                                        NotificationGutsManager.this.mOnSettingsClickListener.onSettingsClick(dataByIndex.mNotiID);
                                                        Log.i("{OngoingActivityCardStackView}", "Listen ongoing activity card guts settings click");
                                                        NotificationSAUtil.sendTypeLog(SystemUIAnalytics.EID_QPNE_GO_TO_SETTINGS_FROM_GUTS, notificationEntry);
                                                        OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = cardStackView2.collapseBackCall;
                                                        if (ongoingCardController$$ExternalSyntheticLambda0 != null) {
                                                            ongoingCardController$$ExternalSyntheticLambda0.mo779invoke(Unit.INSTANCE);
                                                        }
                                                    }
                                                };
                                                UiEventLogger uiEventLogger = (UiEventLogger) Dependency.sDependency.getDependencyInner(UiEventLogger.class);
                                                boolean z = ((DeviceProvisionedControllerImpl) ((DeviceProvisionedController) Dependency.sDependency.getDependencyInner(DeviceProvisionedController.class))).deviceProvisioned.get();
                                                Context context2 = cardStackView2.getContext();
                                                String str2 = dataByIndex.mPackageName;
                                                boolean z2 = !SecNotificationBlockManager.isBlockablePackage(context2, str2);
                                                ((HighPriorityProvider) Dependency.sDependency.getDependencyInner(HighPriorityProvider.class)).isHighPriority(notificationEntry, true);
                                                AssistantFeedbackController assistantFeedbackController = (AssistantFeedbackController) Dependency.sDependency.getDependencyInner(AssistantFeedbackController.class);
                                                String str3 = dataByIndex.mAodRemoteAppName;
                                                if (str3.length() <= 0 || (icon2 = dataByIndex.mAodRemoteAppIcon) == null) {
                                                    i2 = 1;
                                                } else {
                                                    i2 = 1;
                                                    if (dataByIndex.mAodRemoteAppPendingIntent != null) {
                                                        GutContentInitializer.OnSettingsClickListener onSettingsClickListener3 = new GutContentInitializer.OnSettingsClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$addTopCardGuts$1
                                                            @Override // com.android.systemui.statusbar.notification.row.GutContentInitializer.OnSettingsClickListener
                                                            public final void onClick() {
                                                                OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = CardStackView.this.collapseBackCall;
                                                                if (ongoingCardController$$ExternalSyntheticLambda0 != null) {
                                                                    ongoingCardController$$ExternalSyntheticLambda0.mo779invoke(Unit.INSTANCE);
                                                                }
                                                            }
                                                        };
                                                        PendingIntent pendingIntent2 = dataByIndex.mAodRemoteAppPendingIntent;
                                                        Log.i("{OngoingActivityCardStackView}", "AOD guts information is used. ongoingAppName:".concat(str3));
                                                        str = str3;
                                                        pendingIntent = pendingIntent2;
                                                        icon = icon2;
                                                        onSettingsClickListener = onSettingsClickListener3;
                                                        SecNotificationAppInfo secNotificationAppInfo = (SecNotificationAppInfo) cardStackView2.gutsContents;
                                                        NotificationGutsManager.AnonymousClass3 anonymousClass3 = notificationGutsManager.mOnFavoriteNotifUpdateListener;
                                                        notificationGutsManager.isFavoriteNotif(str2);
                                                        secNotificationAppInfo.bindNotification(packageManagerForUser, iNotificationManager, str2, channel, notificationEntry, onSettingsClickListener2, uiEventLogger, z, z2, assistantFeedbackController, icon, str, onSettingsClickListener, pendingIntent);
                                                    }
                                                }
                                                icon = null;
                                                str = null;
                                                onSettingsClickListener = null;
                                                pendingIntent = null;
                                                SecNotificationAppInfo secNotificationAppInfo2 = (SecNotificationAppInfo) cardStackView2.gutsContents;
                                                NotificationGutsManager.AnonymousClass3 anonymousClass32 = notificationGutsManager.mOnFavoriteNotifUpdateListener;
                                                notificationGutsManager.isFavoriteNotif(str2);
                                                secNotificationAppInfo2.bindNotification(packageManagerForUser, iNotificationManager, str2, channel, notificationEntry, onSettingsClickListener2, uiEventLogger, z, z2, assistantFeedbackController, icon, str, onSettingsClickListener, pendingIntent);
                                            }
                                        }
                                    }
                                } else {
                                    i2 = 1;
                                    Log.e("{OngoingActivityCardStackView}", "addTopCardGuts: notification_guts is already inflated.");
                                }
                                if (findViewById != null || findViewById2 == null || (view = cardStackView2.guts) == null) {
                                    Log.e("{OngoingActivityCardStackView}", "enableTopCardGuts: fail");
                                } else if (view.getVisibility() == 0) {
                                    Log.e("{OngoingActivityCardStackView}", "enableTopCardGuts: guts is already enabled");
                                } else {
                                    findViewById2.getLayoutParams().height = cardStackView2.getContext().getResources().getDimensionPixelSize(R.dimen.notification_guts_min_height);
                                    View view4 = cardStackView2.guts;
                                    if (view4 != null) {
                                        view4.setVisibility(0);
                                    }
                                    AnimatorListener animatorListener = new AnimatorListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$enableTopCardGuts$gutsDisplayAniListener$1
                                        @Override // android.animation.Animator.AnimatorListener
                                        public final void onAnimationEnd(Animator animator) {
                                            findViewById2.getLayoutParams().height = -2;
                                            if (cardStackView2.gutsDisplay) {
                                                findViewById.setBackgroundColor(0);
                                                findViewById2.setVisibility(4);
                                            }
                                        }

                                        @Override // android.animation.Animator.AnimatorListener
                                        public final void onAnimationCancel(Animator animator) {
                                        }
                                    };
                                    float width = cardStackView2.getWidth();
                                    double max = Math.max(width - r4, cardStackView2.touchPoint.x);
                                    float height = cardStackView2.getHeight();
                                    float hypot = (float) Math.hypot(max, Math.max(height - r6, cardStackView2.touchPoint.y));
                                    View view5 = cardStackView2.guts;
                                    PointF pointF = cardStackView2.touchPoint;
                                    Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view5, (int) pointF.x, (int) pointF.y, 0.0f, hypot);
                                    createCircularReveal.setDuration(360L);
                                    createCircularReveal.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                                    createCircularReveal.addListener(animatorListener);
                                    createCircularReveal.start();
                                    View view6 = cardStackView2.guts;
                                    if (view6 != null) {
                                        view6.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(i2));
                                    }
                                    Log.i("{OngoingActivityCardStackView}", "enableTopCardGuts done");
                                    OngoingActivityDataHelper.INSTANCE.getClass();
                                    if (OngoingActivityDataHelper.mOngoingActivityLists.size() != 0) {
                                        NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_ONGOING_SHOW_GUTS, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
                                    }
                                }
                            }
                            i2 = 1;
                            if (findViewById != null) {
                            }
                            Log.e("{OngoingActivityCardStackView}", "enableTopCardGuts: fail");
                        }
                    }
                }
                CardStackView.this.gutsControlState = CardStackView.GutsControlState.INIT;
            }
        };
        this.gutsControlState = GutsControlState.INIT;
        this.touchPoint = new PointF(0.0f, 0.0f);
        this.gestureDetector = new GestureDetector(context, new SingleTapConfirm());
        setClipToPadding(false);
        setClipChildren(false);
        post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$initializeViews$1
            @Override // java.lang.Runnable
            public final void run() {
                CardStackView cardStackView = CardStackView.this;
                CardStackView.Companion companion = CardStackView.Companion;
                cardStackView.setAllParentsClipConfig();
            }
        });
        setScrollContainer(false);
        setFocusableInTouchMode(true);
        this.viewSpacing = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_default_stack_spacing);
        this.isRunningExpandAnimation = true;
        this.sceneList = new ArrayList();
        this.layoutListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$layoutListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                Log.d("{OngoingActivityCardStackView}", "onLayoutChange called");
                View view2 = CardStackView.this.decorView;
                view2.getClass();
                view2.removeOnLayoutChangeListener(this);
                View view3 = CardStackView.this.decorView;
                view3.getClass();
                final CardStackView cardStackView = CardStackView.this;
                view3.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$layoutListener$1$onLayoutChange$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        final CardStackView cardStackView2 = CardStackView.this;
                        CardStackView.Companion companion = CardStackView.Companion;
                        cardStackView2.getClass();
                        Log.d("{OngoingActivityCardStackView}", "internalExpandAnimation");
                        ChangeBounds changeBounds = new ChangeBounds();
                        changeBounds.setInterpolator(CardStackView.expandRootInterpolator);
                        changeBounds.setDuration(500L);
                        changeBounds.addListener(new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$internalExpandAnimation$transition$1$1
                            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                            public final void onTransitionEnd(Transition transition) {
                                View view4;
                                Log.d("{OngoingActivityCardStackView}", "internalExpandAnimation onTransitionEnd");
                                CardStackView cardStackView3 = CardStackView.this;
                                cardStackView3.isRunningExpandAnimation = false;
                                int topViewIndex = cardStackView3.getTopViewIndex();
                                if (topViewIndex >= 0) {
                                    int i10 = 0;
                                    while (true) {
                                        View childAt = cardStackView3.getChildAt(i10);
                                        OngoingCardAdapter ongoingCardAdapter = cardStackView3.adapter;
                                        ongoingCardAdapter.getClass();
                                        childAt.getClass();
                                        OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = ongoingCardAdapter.getMediaCardView;
                                        if (ongoingCardController$$ExternalSyntheticLambda0 == null) {
                                            Log.e("MediaOngoingActivity", "getMediaCard. lambda is not initialized");
                                            view4 = null;
                                        } else {
                                            view4 = (View) ongoingCardController$$ExternalSyntheticLambda0.mo779invoke(Unit.INSTANCE);
                                        }
                                        if (Intrinsics.areEqual(view4, childAt)) {
                                            CardStackView.getBaseColorView(childAt).setBackgroundColor(0);
                                        } else {
                                            CardStackView.getBaseColorView(childAt).setBackground(cardStackView3.getContext().getResources().getDrawable(R.drawable.sec_ongoing_card_bg));
                                        }
                                        if (i10 == topViewIndex) {
                                            break;
                                        } else {
                                            i10++;
                                        }
                                    }
                                }
                                r6.showBottomCardShadowIfNeeded(CardStackView.this.getTopViewIndex());
                            }

                            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                            public final void onTransitionStart(Transition transition) {
                                Log.d("{OngoingActivityCardStackView}", "internalExpandAnimation onTransitionStart");
                                OngoingCardController$expandAnimation$1$1 ongoingCardController$expandAnimation$1$1 = CardStackView.this.pendingOnStartListener;
                                if (ongoingCardController$expandAnimation$1$1 != null) {
                                    ongoingCardController$expandAnimation$1$1.run();
                                }
                                CardStackView.this.isRunningExpandAnimation = true;
                            }
                        });
                        changeBounds.setPathMotion(new PathMotion() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$internalExpandAnimation$transition$1$2
                            @Override // android.transition.PathMotion
                            public final Path getPath(float f, float f2, float f3, float f4) {
                                Path path = new Path();
                                path.moveTo(f, f2);
                                path.lineTo(f3, f4);
                                return path;
                            }
                        });
                        ArrayList arrayList = cardStackView2.sceneList;
                        int size = arrayList.size();
                        final int i10 = 0;
                        int i11 = 0;
                        while (i11 < size) {
                            Object obj = arrayList.get(i11);
                            i11++;
                            int i12 = i10 + 1;
                            if (i10 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            final Scene scene = (Scene) ((Pair) obj).getSecond();
                            scene.setEnterAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$internalExpandAnimation$1$1
                                /* JADX WARN: Multi-variable type inference failed */
                                /* JADX WARN: Type inference failed for: r1v2, types: [T, android.view.View, java.lang.Object] */
                                @Override // java.lang.Runnable
                                public final void run() {
                                    final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                    ?? findViewById = scene.getSceneRoot().findViewById(R.id.stack_expand_contents);
                                    findViewById.getClass();
                                    ref$ObjectRef.element = findViewById;
                                    ((ViewGroup) findViewById).setAlpha(0.0f);
                                    ((ViewGroup) ref$ObjectRef.element).setVisibility(4);
                                    final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                                    final CardStackView cardStackView3 = cardStackView2;
                                    ofFloat.setDuration(500L);
                                    final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
                                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$internalExpandAnimation$1$1$1$1
                                        /* JADX WARN: Multi-variable type inference failed */
                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            float floatValue = ((Float) ofFloat.getAnimatedValue()).floatValue();
                                            if (((ViewGroup) ref$ObjectRef.element).getWidth() > IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount * cardStackView3.dpToFlot && ((ViewGroup) ref$ObjectRef.element).getVisibility() != 0) {
                                                ((ViewGroup) ref$ObjectRef.element).setVisibility(0);
                                                ref$FloatRef.element = Math.min(floatValue, 0.99f);
                                            }
                                            if (((ViewGroup) ref$ObjectRef.element).getVisibility() == 0) {
                                                ViewGroup viewGroup = (ViewGroup) ref$ObjectRef.element;
                                                float f = ref$FloatRef.element;
                                                viewGroup.setAlpha((floatValue - f) / (1.0f - f));
                                            }
                                        }
                                    });
                                    ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$internalExpandAnimation$1$1$1$2
                                        /* JADX WARN: Multi-variable type inference failed */
                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                        public final void onAnimationEnd(Animator animator) {
                                            ((ViewGroup) ref$ObjectRef.element).setVisibility(0);
                                            ((ViewGroup) ref$ObjectRef.element).setAlpha(1.0f);
                                        }
                                    });
                                    ofFloat.start();
                                    CardStackView cardStackView4 = cardStackView2;
                                    View findViewById2 = scene.getSceneRoot().findViewById(R.id.stack_pip_layout);
                                    findViewById2.getClass();
                                    CardStackView.Companion companion2 = CardStackView.Companion;
                                    cardStackView4.getClass();
                                    CardStackView.changeLayoutSize(findViewById2, -1, -2);
                                    View findViewById3 = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                                    findViewById3.getClass();
                                    if (i10 != cardStackView2.getTopViewIndex()) {
                                        findViewById3.setVisibility(4);
                                    }
                                    SpringAnimation springAnimation = new SpringAnimation(findViewById3, DynamicAnimation.SCALE_X, 2.0f);
                                    springAnimation.mSpring.setStiffness(200.0f);
                                    springAnimation.mSpring.setDampingRatio(0.8131728f);
                                    springAnimation.start();
                                    SpringAnimation springAnimation2 = new SpringAnimation(findViewById3, DynamicAnimation.SCALE_Y, 2.0f);
                                    springAnimation2.mSpring.setStiffness(200.0f);
                                    springAnimation2.mSpring.setDampingRatio(0.8131728f);
                                    springAnimation2.start();
                                    CardStackView.changeLayoutMargin(findViewById3, findViewById3.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_progress_start_margin));
                                    View findViewById4 = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                                    findViewById4.getClass();
                                    ViewPropertyAnimator alpha = findViewById4.animate().alpha(0.0f);
                                    CardStackView.Companion.getClass();
                                    alpha.setInterpolator(CardStackView.alphaInterpolator).setDuration(100L).start();
                                    cardStackView2.getChildCount();
                                    cardStackView2.colorTransition(cardStackView2.getCardBg(), i10, true);
                                }
                            });
                            TransitionManager.go(scene, changeBounds);
                            Log.d("{OngoingActivityCardStackView}", "internalExpandAnimation TransitionManager go");
                            i10 = i12;
                        }
                    }
                });
            }
        };
    }
}
