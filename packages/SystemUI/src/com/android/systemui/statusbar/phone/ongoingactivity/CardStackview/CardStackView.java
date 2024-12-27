package com.android.systemui.statusbar.phone.ongoingactivity.CardStackview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
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
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
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
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$createCardController$1;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardAdapter;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.IntRange;

public class CardStackView extends FrameLayout {
    public static final PathInterpolator alphaInterpolator;
    public static final PathInterpolator collapseRootInterpolator;
    public static final PathInterpolator collapseTranslationInterpolator;
    public static final PathInterpolator expandRootInterpolator;
    public static final PathInterpolator expandTranslationInterpolator;
    public OngoingCardAdapter adapter;
    public final int cardSwipeEndDp;
    public final int cardSwipeStartDp;
    public CardSwipeState cardSwipeState;
    public final int centerDp;
    public Function1 collapseBackCall;
    public int currentIndex;
    public final CardStackView$dataObserver$1 dataObserver;
    public View decorView;
    public Function1 dismiss;
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
    public PointF initialTouchPoint;
    public boolean isAnimating;
    public boolean isChildTouchEventIntercepting;
    public boolean isRunningCollapseAnimation;
    public boolean isRunningExpandAnimation;
    public boolean isRunningSwipeDismissTopCardMove;
    public final CardStackView$layoutListener$1 layoutListener;
    public final CardStackView$longPressRunnable$1 longPressRunnable;
    public OngoingCardController.AnonymousClass3 onChangeListener;
    public final Map originalAllParentsClipChildrenConfig;
    public final Map originalAllParentsClipToPaddingConfig;
    public boolean pendingAnimation;
    public int pendingHeight;
    public Runnable pendingOnStartListener;
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

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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

    public final class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return true;
        }
    }

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
        expandRootInterpolator = new PathInterpolator(0.34f, 1.0f, 0.64f, 1.0f);
        new PathInterpolator(0.34f, 1.16f, 0.64f, 1.0f);
        collapseRootInterpolator = new PathInterpolator(0.34f, 1.0f, 0.64f, 1.0f);
        alphaInterpolator = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
        expandTranslationInterpolator = new PathInterpolator(0.0f, 1.0f, 0.68f, 1.0f);
        collapseTranslationInterpolator = new PathInterpolator(0.7f, 1.0f, 0.68f, 1.0f);
    }

    public CardStackView(Context context) {
        this(context, null, 0, 6, null);
    }

    public static final void access$sendDismiss(final CardStackView cardStackView) {
        cardStackView.getClass();
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
            Log.i("{OngoingActivityCardStackView}", "OngoingActivityDataHelper.getDataSize() == 0");
            return;
        }
        final String str = OngoingActivityDataHelper.getDataByIndex(0).mNotiID;
        cardStackView.removingSbnId = str;
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Card swipe dismiss. removingSbnId:", str, "{OngoingActivityCardStackView}");
        Function1 function1 = cardStackView.dismiss;
        if (function1 != null) {
            function1.invoke(str);
        }
        cardStackView.getHandler().postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$sendDismiss$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                if (Intrinsics.areEqual(CardStackView.this.removingSbnId, "")) {
                    return;
                }
                Log.e("{OngoingActivityCardStackView}", "dismiss fail. force clear for sbnId:" + str);
                OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                String str2 = CardStackView.this.removingSbnId;
                ongoingActivityDataHelper.getClass();
                OngoingActivityDataHelper.removeOngoingActivityByKey(str2);
                CardStackView cardStackView2 = CardStackView.this;
                cardStackView2.removingSbnId = "";
                cardStackView2.isRunningSwipeDismissTopCardMove = false;
                cardStackView2.isAnimating = false;
            }
        }, 500L);
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

    public static void isMonochromeCard() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
            Log.i("{OngoingActivityCardStackView}", "isMonochromeCard : ongoingActivity is null ##");
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
            Intrinsics.checkNotNull(view);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight() + i);
            layoutParams.gravity = 48;
            layoutParams.setMargins(0, (int) 0.0f, 0, i);
            viewGroup.setTranslationY(0.0f);
            viewGroup.setLayoutParams(layoutParams);
        }
    }

    public final void changeCardBg(View view, int i) {
        View baseColorView = getBaseColorView(view);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(TypedValue.applyDimension(1, 36.0f, this.displayMetrics));
        gradientDrawable.setColor(ColorStateList.valueOf(i));
        baseColorView.setBackground(gradientDrawable);
    }

    public final void connectCardItem() {
        int i;
        int i2;
        OngoingCardAdapter ongoingCardAdapter;
        int i3;
        OngoingCardAdapter ongoingCardAdapter2 = this.adapter;
        int i4 = 1;
        if (ongoingCardAdapter2 != null) {
            int min = Math.min(ongoingCardAdapter2.getCount() - this.currentIndex, this.stackMaxSize);
            i2 = min - getChildCount();
            int childCount = getChildCount() + this.currentIndex;
            int childCount2 = getChildCount();
            int i5 = 0;
            while (i5 < i2) {
                int i6 = (min - i5) - i4;
                if (i6 < 0) {
                    break;
                }
                View view = ongoingCardAdapter2.getView(childCount + i5, null, this);
                int i7 = childCount2 + i5;
                int width = getWidth() - (getPaddingEnd() + getPaddingStart());
                int height = getHeight() - (getPaddingBottom() + getPaddingTop());
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i6, i7, "addItem() index = ", " , order = ", "{OngoingActivityCardStackView}");
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new FrameLayout.LayoutParams(-2, -2);
                }
                int i8 = Integer.MIN_VALUE;
                if (layoutParams.width == -1) {
                    ongoingCardAdapter = ongoingCardAdapter2;
                    i3 = 1073741824;
                } else {
                    ongoingCardAdapter = ongoingCardAdapter2;
                    i3 = Integer.MIN_VALUE;
                }
                if (layoutParams.height == -1) {
                    i8 = 1073741824;
                }
                view.measure(i3 | width, i8 | height);
                layoutItem(view, i6, i7);
                float cardInitialScale = getCardInitialScale(i7);
                view.setScaleY(cardInitialScale);
                view.setScaleX(cardInitialScale);
                addViewInLayout(view, 0, layoutParams, true);
                i5++;
                ongoingCardAdapter2 = ongoingCardAdapter;
                i4 = 1;
            }
            i = 0;
        } else {
            i = 0;
            i2 = 0;
        }
        if (i2 > 0 || this.isRunningExpandAnimation || this.isRunningCollapseAnimation) {
            Log.d("{OngoingActivityCardStackView}", "reorderItems()");
            int childCount3 = getChildCount();
            while (i < childCount3) {
                View childAt = getChildAt(i);
                int childCount4 = (getChildCount() - i) - 1;
                Intrinsics.checkNotNull(childAt);
                layoutItem(childAt, i, childCount4);
                float cardInitialScale2 = getCardInitialScale(childCount4);
                childAt.setScaleY(cardInitialScale2);
                childAt.setScaleX(cardInitialScale2);
                i++;
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
        if (view == null || view.getVisibility() != 0) {
            Log.w("{OngoingActivityCardStackView}", "disableTopCardGuts: guts is already disabled");
            return;
        }
        Log.i("{OngoingActivityCardStackView}", "disableTopCardGuts: change visibility: INVISIBLE. withoutAni:" + z);
        findViewById.setBackgroundResource(R.drawable.sec_ongoing_card_bg);
        findViewById2.setVisibility(0);
        if (z) {
            removeTopCardGuts(childAt);
            View view2 = this.guts;
            if (view2 == null) {
                return;
            }
            view2.setVisibility(8);
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
                if (view3 == null) {
                    return;
                }
                view3.setVisibility(8);
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
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() != 0) {
            return getContext().getResources().getColor(R.color.ongoing_activity_card_item_background_color);
        }
        Log.i("{OngoingActivityCardStackView}", "getCardBg : ongoingActivity is null ##");
        return 0;
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
            Intrinsics.checkNotNull(viewStatus);
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
            Intrinsics.checkNotNull(childAt);
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
            Intrinsics.checkNotNull(childAt);
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
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.touchPointerId = motionEvent.getPointerId(0);
            this.initialTouchPoint = new PointF(motionEvent.getX(0), motionEvent.getY(0));
            return false;
        }
        if (action != 2) {
            return false;
        }
        PointF touchPoint = getTouchPoint(motionEvent);
        if (this.isChildTouchEventIntercepting) {
            return true;
        }
        if (touchPoint == null) {
            this.isChildTouchEventIntercepting = false;
            return false;
        }
        PointF pointF = this.initialTouchPoint;
        PointF pointF2 = new PointF(touchPoint.x, touchPoint.y);
        pointF2.offset(-pointF.x, -pointF.y);
        if (Math.abs(pointF2.y) + Math.abs(pointF2.x) <= TypedValue.applyDimension(1, 16.0f, this.displayMetrics)) {
            return false;
        }
        this.isChildTouchEventIntercepting = true;
        startDrag(motionEvent);
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
            this.sceneList.clear();
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                ViewGroup viewGroup = (ViewGroup) getChildAt(i5);
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
                        Intrinsics.checkNotNull(findViewById);
                        CardStackView.changeLayoutSize(findViewById, cardStackView.pendingWidth, cardStackView.pendingHeight);
                        View findViewById2 = sceneRoot.findViewById(R.id.pip_dummy_chip_layout);
                        Intrinsics.checkNotNull(findViewById2);
                        CardStackView.changeLayoutSize(findViewById2, cardStackView.pendingWidth, cardStackView.pendingHeight);
                        View findViewById3 = sceneRoot.findViewById(R.id.pip_dummy_chip_layout);
                        Intrinsics.checkNotNull(findViewById3);
                        CardStackView.changeLayoutMargin(findViewById3, 0);
                        View findViewById4 = sceneRoot.findViewById(R.id.dummy_capsule_item_noti_expanded_info);
                        Intrinsics.checkNotNull(findViewById4);
                        View findViewById5 = sceneRoot.findViewById(R.id.pip_dummy_chip_layout);
                        Intrinsics.checkNotNull(findViewById5);
                        findViewById4.setAlpha(0.0f);
                        findViewById5.setVisibility(0);
                    }
                });
                scene2.enter();
            }
            View view = this.decorView;
            Intrinsics.checkNotNull(view);
            view.addOnLayoutChangeListener(this.layoutListener);
            this.pendingAnimation = false;
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

    /* JADX WARN: Removed duplicated region for block: B:96:0x0377  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r22) {
        /*
            Method dump skipped, instructions count: 1386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void rebuildAllItems() {
        Log.i("{OngoingActivityCardStackView}", "rebuildAllItems()");
        OngoingCardAdapter ongoingCardAdapter = this.adapter;
        if (ongoingCardAdapter != null || (ongoingCardAdapter != null && !ongoingCardAdapter.isEmpty())) {
            removeAllViewsInLayout();
        }
        OngoingCardAdapter ongoingCardAdapter2 = this.adapter;
        if (ongoingCardAdapter2 != null) {
            connectCardItem();
            requestLayout();
            if (this.onChangeListener != null) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(ongoingCardAdapter2.getCount(), ongoingCardAdapter2.getCount(), "remainingCardsCount :: ", ", totalCardsCount :: ", "StackView");
            }
        }
        View childAt = getChildAt(getChildCount() - 1);
        if (childAt != null) {
            childAt.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$rebuildAllItems$2
                @Override // java.lang.Runnable
                public final void run() {
                    CardStackView.this.updateBottomCardShadow();
                }
            });
        }
        this.startViewStatusList = getStartViewStatusList();
        this.endViewStatusList = getEndViewStatusList();
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
        Log.i("{OngoingActivityCardStackView}", "resetItemPosition()");
        List currentViewStatusList = getCurrentViewStatusList();
        CustomAnimationSet customAnimationSet = this.resetAnimationSet;
        Log.i("{OngoingActivityCardStackView}", "resetAnimationSet run()");
        int topViewIndex = getTopViewIndex();
        if (topViewIndex >= 0) {
            int i = 0;
            while (true) {
                final View childAt = getChildAt(i);
                final ViewStatus viewStatus = (ViewStatus) ((ArrayList) currentViewStatusList).get(i);
                final ViewStatus viewStatus2 = (ViewStatus) this.startViewStatusList.get(i);
                Intrinsics.checkNotNull(childAt);
                customAnimationSet.add(childAt, DynamicAnimation.SCALE_X, viewStatus2.scaleX, 0.9f, 400.0f);
                customAnimationSet.add(childAt, DynamicAnimation.SCALE_Y, viewStatus2.scaleY, 0.9f, 400.0f);
                customAnimationSet.add(childAt, DynamicAnimation.X, viewStatus2.point.x, 0.9f, 400.0f);
                customAnimationSet.add(childAt, DynamicAnimation.Y, viewStatus2.point.y, 0.9f, 400.0f);
                final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(480L);
                ofFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
                if (z) {
                    ofFloat.setInterpolator(alphaInterpolator);
                    ofFloat.setDuration(150L);
                }
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                View childAt2 = getChildAt(getTopViewIndex());
                ref$BooleanRef.element = (childAt2 == null || (viewGroup = (ViewGroup) childAt2.findViewWithTag("BottomCardShadowView")) == null || viewGroup.getVisibility() != 0) ? false : true;
                final int i2 = i;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$resetItemPosition$1$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) ofFloat.getAnimatedValue()).floatValue();
                        CardStackView cardStackView = this;
                        View view = childAt;
                        CardStackView.Companion companion = CardStackView.Companion;
                        cardStackView.getClass();
                        View baseColorView = CardStackView.getBaseColorView(view);
                        float f = viewStatus.baseViewAlpha;
                        baseColorView.setAlpha(((viewStatus2.baseViewAlpha - f) * floatValue) + f);
                        if (i2 != this.getTopViewIndex() || floatValue < 0.7f || ref$BooleanRef.element) {
                            return;
                        }
                        CardStackView cardStackView2 = this;
                        cardStackView2.showBottomCardShadowIfNeeded(cardStackView2.getTopViewIndex());
                        ref$BooleanRef.element = true;
                    }
                });
                customAnimationSet.add(ofFloat);
                if (i == topViewIndex) {
                    break;
                } else {
                    i++;
                }
            }
        }
        customAnimationSet.start();
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
                if (viewGroup3 == null) {
                    return;
                }
                viewGroup3.setY(floatValue2);
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
        OngoingCardController.AnonymousClass3 anonymousClass3 = this.onChangeListener;
        if (anonymousClass3 != null) {
            ((OngoingActivityController$createCardController$1) OngoingCardController.this.mStateListener).onAllowStateChanged(true);
        }
        View childAt = getChildAt(getChildCount() - 1);
        childAt.setScaleX(0.99f);
        childAt.setScaleY(0.99f);
    }

    public final void stopLongPressChecker() {
        GutsControlState gutsControlState = this.gutsControlState;
        if (gutsControlState == GutsControlState.PRESS) {
            getHandler().removeCallbacks(this.longPressRunnable);
            this.gutsControlState = GutsControlState.INIT;
        } else {
            Log.i("{OngoingActivityCardStackView}", "stopLongPressChecker stop by GutsControlState:" + gutsControlState);
        }
    }

    public final void swipeItemToHorizontal(final boolean z) {
        Log.i("{OngoingActivityCardStackView}", "swipeItemToHorizontal()");
        Context context = getContext();
        OngoingActivityDataHelper.INSTANCE.getClass();
        int i = 0;
        NotificationSAUtil.sendOALog(context, SystemUIAnalytics.OAID_ONGOING_DISMISS_EXPAND_VIEW, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
        if (this.gutsDisplay) {
            disableTopCardGuts(true);
        }
        this.isRunningSwipeDismissTopCardMove = true;
        List currentViewStatusList = getCurrentViewStatusList();
        float f = (this.centerDp + this.cardSwipeEndDp) * (z ? 1 : -1) * this.dpToFlot;
        final View childAt = getChildAt(getTopViewIndex());
        SpringAnimation springAnimation = new SpringAnimation(childAt, DynamicAnimation.TRANSLATION_X, f);
        SpringForce springForce = springAnimation.mSpring;
        springForce.setStiffness(400.0f);
        springForce.setDampingRatio(0.875f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$swipeItemToHorizontal$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f2, float f3) {
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
                    childAt.setAlpha((f5 - x) / (((i2 - r7) + i3) * f4));
                }
            }
        });
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$swipeItemToHorizontal$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f2, float f3) {
                CardStackView.Companion companion = CardStackView.Companion;
                CardStackView cardStackView = CardStackView.this;
                if (cardStackView.getTopViewIndex() > 0) {
                    Log.i("{OngoingActivityCardStackView}", "Horizon swipe ani done");
                } else {
                    Log.i("{OngoingActivityCardStackView}", "Horizon swipe ani done. sendDismiss");
                    CardStackView.access$sendDismiss(cardStackView);
                }
            }
        });
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
            float f2 = childCount == getTopViewIndex() ? 60.0f : 199.5f;
            float f3 = childCount == getTopViewIndex() ? 0.34999996f : 0.6f;
            Intrinsics.checkNotNull(childAt2);
            customAnimationSet.add(childAt2, DynamicAnimation.SCALE_X, viewStatus2.scaleX, f3, f2);
            customAnimationSet.add(childAt2, DynamicAnimation.SCALE_Y, viewStatus2.scaleY, f3, f2);
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(480L);
            ofFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            CustomAnimationSet customAnimationSet2 = customAnimationSet;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$swipeItemToHorizontal$2$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) ofFloat.getAnimatedValue()).floatValue();
                    View view = childAt2;
                    float f4 = viewStatus.point.x;
                    view.setX(((viewStatus2.point.x - f4) * floatValue) + f4);
                    View view2 = childAt2;
                    float f5 = viewStatus.point.y;
                    view2.setY(((viewStatus2.point.y - f5) * floatValue) + f5);
                    CardStackView cardStackView = this;
                    View view3 = childAt2;
                    CardStackView.Companion companion = CardStackView.Companion;
                    cardStackView.getClass();
                    View baseColorView = CardStackView.getBaseColorView(view3);
                    float f6 = viewStatus.baseViewAlpha;
                    baseColorView.setAlpha(((viewStatus2.baseViewAlpha - f6) * floatValue) + f6);
                }
            });
            customAnimationSet2.add(ofFloat);
            childAt2.setTranslationZ(childCount);
            customAnimationSet = customAnimationSet2;
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
            updateBottomCardShadowLP$default(this, viewGroup2, this.viewSpacing, findViewById);
            if (i == topViewIndex) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void updateItem() {
        Log.i("{OngoingActivityCardStackView}", "updateItem()");
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
                ongoingCardAdapter.bindView(getChildAt(i), i2);
            }
            updateItemBg();
            if (this.onChangeListener != null) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(ongoingCardAdapter.getCount(), ongoingCardAdapter.getCount(), "remainingCardsCount :: ", ", totalCardsCount :: ", "StackView");
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
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(topViewIndex, size, "updateItem index mismatch. topViewIndex:", ", startViewStatusList.size:", ", endViewStatusList.size:");
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
                Intrinsics.checkNotNull(childAt);
                setCardColor(childAt, topViewIndex2);
            } else {
                Log.i("{OngoingActivityCardStackView}", "updateItemBg setCardColor by fixed index");
                Intrinsics.checkNotNull(childAt);
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
        this.swipeAnimationSet = new CustomAnimationSet(new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$swipeAnimationSet$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.i("{OngoingActivityCardStackView}", "swipeAnimationSet end()");
                CardStackView cardStackView = CardStackView.this;
                cardStackView.isAnimating = false;
                cardStackView.resetAllParentsClipConfig$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                CardStackView cardStackView2 = CardStackView.this;
                if (cardStackView2.getChildCount() >= 2) {
                    KeyguardSecPatternView$$ExternalSyntheticOutline0.m(cardStackView2.getTopViewIndex(), "swapTopItemToBottom() topViewIndex = ", "{OngoingActivityCardStackView}");
                    OngoingActivityDataHelper.INSTANCE.getClass();
                    LinkedList linkedList = OngoingActivityDataHelper.mOngoingActivityLists;
                    if (linkedList.size() > 1) {
                        Collections.rotate(linkedList, -1);
                    }
                    View childAt = cardStackView2.getChildAt(cardStackView2.getTopViewIndex());
                    cardStackView2.removeView(childAt);
                    cardStackView2.addView(childAt, 0);
                    cardStackView2.updateItem();
                }
                CardStackView.this.requestLayout();
                OngoingCardController.AnonymousClass3 anonymousClass3 = CardStackView.this.onChangeListener;
                if (anonymousClass3 != null) {
                    ((OngoingActivityController$createCardController$1) OngoingCardController.this.mStateListener).onAllowStateChanged(false);
                }
                return Unit.INSTANCE;
            }
        });
        this.swipeHorizontalAnimationSet = new CustomAnimationSet(new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$swipeHorizontalAnimationSet$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.i("{OngoingActivityCardStackView}", "swipeHorizontalAnimationSet end()");
                CardStackView cardStackView = CardStackView.this;
                CardStackView.Companion companion = CardStackView.Companion;
                if (cardStackView.getTopViewIndex() > 0) {
                    Log.i("{OngoingActivityCardStackView}", "swipeHorizontalAnimationSet ani done. send dismiss");
                    CardStackView.access$sendDismiss(CardStackView.this);
                } else {
                    Log.i("{OngoingActivityCardStackView}", "swipeHorizontalAnimationSet ani done");
                }
                CardStackView cardStackView2 = CardStackView.this;
                cardStackView2.isAnimating = false;
                cardStackView2.resetAllParentsClipConfig$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                OngoingCardController.AnonymousClass3 anonymousClass3 = CardStackView.this.onChangeListener;
                if (anonymousClass3 != null) {
                    ((OngoingActivityController$createCardController$1) OngoingCardController.this.mStateListener).onAllowStateChanged(false);
                }
                return Unit.INSTANCE;
            }
        });
        this.resetAnimationSet = new CustomAnimationSet(new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$resetAnimationSet$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.i("{OngoingActivityCardStackView}", "resetAnimationSet end()");
                CardStackView cardStackView = CardStackView.this;
                cardStackView.isAnimating = false;
                cardStackView.resetAllParentsClipConfig$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                CardStackView.this.requestLayout();
                OngoingCardController.AnonymousClass3 anonymousClass3 = CardStackView.this.onChangeListener;
                if (anonymousClass3 != null) {
                    ((OngoingActivityController$createCardController$1) OngoingCardController.this.mStateListener).onAllowStateChanged(false);
                }
                return Unit.INSTANCE;
            }
        });
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
                View view;
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
                                        ViewStub viewStub = new ViewStub(cardStackView2.getContext());
                                        viewStub.setLayoutResource(R.layout.notification_guts);
                                        viewStub.setInflatedId(R.id.notification_guts);
                                        viewStub.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                                        viewGroup.addView(viewStub);
                                        cardStackView2.guts = viewStub.inflate();
                                        View inflate = LayoutInflater.from(cardStackView2.getContext()).inflate(R.layout.sec_notification_app_info, (ViewGroup) null, false);
                                        cardStackView2.gutsContents = inflate;
                                        ((NotificationGuts) cardStackView2.guts).setGutsContent((SecNotificationAppInfo) inflate);
                                        NotificationGuts.OnGutsClosedListener onGutsClosedListener = new NotificationGuts.OnGutsClosedListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$addTopCardGuts$listener$1
                                            @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.OnGutsClosedListener
                                            public final void onGutsClosed(NotificationGuts notificationGuts) {
                                                Log.i("{OngoingActivityCardStackView}", "onGutsClosed: Listen ongoing activity card guts is closed");
                                                CardStackView.this.gutsClosedCheck = true;
                                            }
                                        };
                                        NotificationGuts notificationGuts = (NotificationGuts) cardStackView2.guts;
                                        Log.i("NotificationGuts", "mClosedListenerForOngoingActivity added");
                                        notificationGuts.mClosedListenerForOngoingActivity = onGutsClosedListener;
                                        View view3 = cardStackView2.gutsContents;
                                        ((SecNotificationAppInfo) view3).mGutsContainer = (NotificationGuts) cardStackView2.guts;
                                        if (view3 == null) {
                                            Log.e("{OngoingActivityCardStackView}", "addTopCardGuts: Cannot find notification_guts from view");
                                        } else if (childAt.findViewById(R.id.notification_guts_container) == null) {
                                            Log.e("{OngoingActivityCardStackView}", "addTopCardGuts: Cannot find notification_guts_container from view");
                                        } else {
                                            OngoingActivityDataHelper.INSTANCE.getClass();
                                            final OngoingActivityData dataByIndex = OngoingActivityDataHelper.getDataByIndex(0);
                                            PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(dataByIndex.mUserId, cardStackView2.getContext());
                                            INotificationManager iNotificationManager = (INotificationManager) Dependency.sDependency.getDependencyInner(INotificationManager.class);
                                            NotificationChannel channel = dataByIndex.mNotificationEntry.mRanking.getChannel();
                                            final NotificationGutsManager notificationGutsManager = (NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class);
                                            final NotificationEntry notificationEntry = dataByIndex.mNotificationEntry;
                                            GutContentInitializer.OnSettingsClickListener onSettingsClickListener = new GutContentInitializer.OnSettingsClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$addTopCardGuts$onSettingsClick$1
                                                @Override // com.android.systemui.statusbar.notification.row.GutContentInitializer.OnSettingsClickListener
                                                public final void onClick() {
                                                    NotificationGutsManager.this.mOnSettingsClickListener.onSettingsClick(dataByIndex.mNotiID);
                                                    Log.i("{OngoingActivityCardStackView}", "Listen ongoing activity card guts settings click");
                                                    NotificationSAUtil.sendTypeLog(SystemUIAnalytics.EID_QPNE_GO_TO_SETTINGS_FROM_GUTS, notificationEntry);
                                                    Function1 function1 = cardStackView2.collapseBackCall;
                                                    if (function1 != null) {
                                                        function1.invoke(Unit.INSTANCE);
                                                    }
                                                }
                                            };
                                            UiEventLogger uiEventLogger = (UiEventLogger) Dependency.sDependency.getDependencyInner(UiEventLogger.class);
                                            boolean z = ((DeviceProvisionedControllerImpl) ((DeviceProvisionedController) Dependency.sDependency.getDependencyInner(DeviceProvisionedController.class))).deviceProvisioned.get();
                                            Context context2 = cardStackView2.getContext();
                                            String str = dataByIndex.mPackageName;
                                            boolean z2 = !SecNotificationBlockManager.isBlockablePackage(context2, str);
                                            ((HighPriorityProvider) Dependency.sDependency.getDependencyInner(HighPriorityProvider.class)).isHighPriority(notificationEntry, true);
                                            AssistantFeedbackController assistantFeedbackController = (AssistantFeedbackController) Dependency.sDependency.getDependencyInner(AssistantFeedbackController.class);
                                            SecNotificationAppInfo secNotificationAppInfo = (SecNotificationAppInfo) cardStackView2.gutsContents;
                                            NotificationGutsManager.AnonymousClass2 anonymousClass2 = notificationGutsManager.mOnFavoriteNotifUpdateListener;
                                            notificationGutsManager.isFavoriteNotif(str);
                                            secNotificationAppInfo.bindNotification(packageManagerForUser, iNotificationManager, str, channel, notificationEntry, onSettingsClickListener, uiEventLogger, z, z2, assistantFeedbackController);
                                        }
                                    }
                                } else {
                                    Log.e("{OngoingActivityCardStackView}", "addTopCardGuts: notification_guts is already inflated.");
                                }
                            }
                            if (findViewById == null || findViewById2 == null || (view = cardStackView2.guts) == null) {
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
                                double max = Math.max(width - r6, cardStackView2.touchPoint.x);
                                float height = cardStackView2.getHeight();
                                float hypot = (float) Math.hypot(max, Math.max(height - r8, cardStackView2.touchPoint.y));
                                View view5 = cardStackView2.guts;
                                PointF pointF = cardStackView2.touchPoint;
                                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view5, (int) pointF.x, (int) pointF.y, 0.0f, hypot);
                                createCircularReveal.setDuration(360L);
                                createCircularReveal.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                                createCircularReveal.addListener(animatorListener);
                                createCircularReveal.start();
                                View view6 = cardStackView2.guts;
                                if (view6 != null) {
                                    view6.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
                                }
                                Log.i("{OngoingActivityCardStackView}", "enableTopCardGuts done");
                                Context context3 = cardStackView2.getContext();
                                OngoingActivityDataHelper.INSTANCE.getClass();
                                NotificationSAUtil.sendOALog(context3, SystemUIAnalytics.OAID_ONGOING_SHOW_GUTS, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
                            }
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
                View view2 = CardStackView.this.decorView;
                Intrinsics.checkNotNull(view2);
                view2.removeOnLayoutChangeListener(this);
                View view3 = CardStackView.this.decorView;
                Intrinsics.checkNotNull(view3);
                final CardStackView cardStackView = CardStackView.this;
                view3.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$layoutListener$1$onLayoutChange$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        final CardStackView cardStackView2 = CardStackView.this;
                        CardStackView.Companion companion = CardStackView.Companion;
                        cardStackView2.getClass();
                        ChangeBounds changeBounds = new ChangeBounds();
                        changeBounds.setInterpolator(CardStackView.expandRootInterpolator);
                        changeBounds.setDuration(300L);
                        changeBounds.addListener(new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$internalExpandAnimation$transition$1$1
                            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                            public final void onTransitionEnd(Transition transition) {
                                CardStackView cardStackView3 = CardStackView.this;
                                cardStackView3.isRunningExpandAnimation = false;
                                cardStackView3.showBottomCardShadowIfNeeded(cardStackView3.getTopViewIndex());
                            }

                            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                            public final void onTransitionStart(Transition transition) {
                                Runnable runnable = CardStackView.this.pendingOnStartListener;
                                if (runnable != null) {
                                    runnable.run();
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
                        Iterator it = cardStackView2.sceneList.iterator();
                        int i10 = 0;
                        while (it.hasNext()) {
                            Object next = it.next();
                            int i11 = i10 + 1;
                            if (i10 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            final Scene scene = (Scene) ((Pair) next).getSecond();
                            scene.setEnterAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$internalExpandAnimation$1$1
                                /* JADX WARN: Multi-variable type inference failed */
                                /* JADX WARN: Type inference failed for: r1v2, types: [T, android.view.View, java.lang.Object] */
                                @Override // java.lang.Runnable
                                public final void run() {
                                    final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                    ?? findViewById = scene.getSceneRoot().findViewById(R.id.stack_expand_contents);
                                    Intrinsics.checkNotNull(findViewById);
                                    ref$ObjectRef.element = findViewById;
                                    ((ViewGroup) findViewById).setAlpha(0.0f);
                                    ViewPropertyAnimator alpha = ((ViewGroup) ref$ObjectRef.element).animate().alpha(1.0f);
                                    CardStackView.Companion.getClass();
                                    alpha.setInterpolator(CardStackView.expandContentsAlphaInterpolator).setDuration(300L).start();
                                    ((ViewGroup) ref$ObjectRef.element).setVisibility(4);
                                    final ValueAnimator ofInt = ValueAnimator.ofInt(0, 10);
                                    ofInt.setDuration(300L);
                                    ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$internalExpandAnimation$1$1$1$1
                                        /* JADX WARN: Multi-variable type inference failed */
                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            if (((Integer) ofInt.getAnimatedValue()).intValue() < 3 || ((ViewGroup) ref$ObjectRef.element).getVisibility() == 0) {
                                                return;
                                            }
                                            ((ViewGroup) ref$ObjectRef.element).setVisibility(0);
                                        }
                                    });
                                    ofInt.start();
                                    CardStackView cardStackView3 = cardStackView2;
                                    View findViewById2 = scene.getSceneRoot().findViewById(R.id.stack_pip_layout);
                                    Intrinsics.checkNotNull(findViewById2);
                                    cardStackView3.getClass();
                                    CardStackView.changeLayoutSize(findViewById2, -1, -2);
                                    View findViewById3 = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                                    Intrinsics.checkNotNull(findViewById3);
                                    CardStackView cardStackView4 = cardStackView2;
                                    SpringAnimation springAnimation = new SpringAnimation(findViewById3, DynamicAnimation.SCALE_X, 2.0f);
                                    springAnimation.mSpring.setStiffness(200.0f);
                                    springAnimation.mSpring.setDampingRatio(0.8131728f);
                                    springAnimation.start();
                                    SpringAnimation springAnimation2 = new SpringAnimation(findViewById3, DynamicAnimation.SCALE_Y, 2.0f);
                                    springAnimation2.mSpring.setStiffness(200.0f);
                                    springAnimation2.mSpring.setDampingRatio(0.8131728f);
                                    springAnimation2.start();
                                    int dimensionPixelSize = findViewById3.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_progress_start_margin);
                                    cardStackView4.getClass();
                                    CardStackView.changeLayoutMargin(findViewById3, dimensionPixelSize);
                                    View findViewById4 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_item_top_layout);
                                    Intrinsics.checkNotNull(findViewById4);
                                    View findViewById5 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_item_app_icon);
                                    Intrinsics.checkNotNull(findViewById5);
                                    View findViewById6 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_item_noti_expanded_info);
                                    Intrinsics.checkNotNull(findViewById6);
                                    View findViewById7 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_remote_container);
                                    Intrinsics.checkNotNull(findViewById7);
                                    findViewById4.setVisibility(0);
                                    findViewById4.setAlpha(1.0f);
                                    ViewPropertyAnimator alpha2 = findViewById4.animate().alpha(0.0f);
                                    PathInterpolator pathInterpolator = CardStackView.alphaInterpolator;
                                    alpha2.setInterpolator(pathInterpolator).setDuration(300L).start();
                                    findViewById5.setAlpha(1.0f);
                                    findViewById5.animate().alpha(0.0f).setInterpolator(pathInterpolator).setDuration(300L).start();
                                    findViewById6.animate().alpha(0.0f).setInterpolator(pathInterpolator).setDuration(300L).start();
                                    findViewById7.animate().alpha(0.0f).setInterpolator(pathInterpolator).setDuration(300L).start();
                                }
                            });
                            TransitionManager.go(scene, changeBounds);
                            i10 = i11;
                        }
                    }
                });
            }
        };
    }
}
