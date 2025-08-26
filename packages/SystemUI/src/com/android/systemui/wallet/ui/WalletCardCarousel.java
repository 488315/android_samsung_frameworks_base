package com.android.systemui.wallet.ui;

import android.content.Context;
import android.graphics.Rect;
import android.service.quickaccesswallet.WalletCard;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.wallet.ui.WalletCardCarousel;
import com.android.systemui.wallet.ui.WalletScreenController;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class WalletCardCarousel extends RecyclerView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mCardCenterToScreenCenterDistancePx;
    public float mCardEdgeToCenterDistance;
    public int mCardHeightPx;
    public int mCardMarginPx;
    public WalletView mCardScrollListener;
    public int mCardWidthPx;
    public int mCenteredAdapterPosition;
    public float mCornerRadiusPx;
    public float mEdgeToCenterDistance;
    public int mExpectedViewWidth;
    public WalletScreenController mSelectionListener;
    public final Rect mSystemGestureExclusionZone;
    public int mTotalCardWidth;
    public final WalletCardCarouselAdapter mWalletCardCarouselAdapter;

    public class CardCarouselAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
        public /* synthetic */ CardCarouselAccessibilityDelegate(WalletCardCarousel walletCardCarousel, WalletCardCarousel walletCardCarousel2) {
            this((RecyclerView) walletCardCarousel2);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() == 32768) {
                WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
                walletCardCarousel.getClass();
                walletCardCarousel.scrollToPosition(RecyclerView.getChildAdapterPosition(view));
            }
            return this.mOriginalDelegate.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }

        private CardCarouselAccessibilityDelegate(RecyclerView recyclerView) {
            super(recyclerView);
        }
    }

    public class CardCarouselScrollListener extends RecyclerView.OnScrollListener {
        public int mOldState;

        public /* synthetic */ CardCarouselScrollListener(WalletCardCarousel walletCardCarousel, int i) {
            this();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 0 && i != this.mOldState) {
                WalletCardCarousel.this.performHapticFeedback(1);
            }
            this.mOldState = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
            WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
            walletCardCarousel.mCenteredAdapterPosition = -1;
            walletCardCarousel.mEdgeToCenterDistance = Float.MAX_VALUE;
            walletCardCarousel.mCardCenterToScreenCenterDistancePx = Float.MAX_VALUE;
            for (int i3 = 0; i3 < walletCardCarousel.getChildCount(); i3++) {
                walletCardCarousel.updateCardView(walletCardCarousel.getChildAt(i3));
            }
            int i4 = walletCardCarousel.mCenteredAdapterPosition;
            if (i4 == -1 || i == 0) {
                return;
            }
            int i5 = i4 + (walletCardCarousel.mEdgeToCenterDistance > 0.0f ? 1 : -1);
            if (i5 < 0 || i5 >= walletCardCarousel.mWalletCardCarouselAdapter.mData.size()) {
                return;
            }
            walletCardCarousel.mCardScrollListener.onCardScroll((WalletCardViewInfo) walletCardCarousel.mWalletCardCarouselAdapter.mData.get(walletCardCarousel.mCenteredAdapterPosition), (WalletCardViewInfo) walletCardCarousel.mWalletCardCarouselAdapter.mData.get(i5), Math.abs(walletCardCarousel.mEdgeToCenterDistance) / walletCardCarousel.mCardEdgeToCenterDistance);
        }

        private CardCarouselScrollListener() {
            this.mOldState = -1;
        }
    }

    public class CarouselSnapHelper extends PagerSnapHelper {
        public /* synthetic */ CarouselSnapHelper(WalletCardCarousel walletCardCarousel, int i) {
            this();
        }

        @Override // androidx.recyclerview.widget.PagerSnapHelper, androidx.recyclerview.widget.SnapHelper
        public final RecyclerView.SmoothScroller createScroller(final RecyclerView.LayoutManager layoutManager) {
            return new LinearSmoothScroller(WalletCardCarousel.this.getContext()) { // from class: com.android.systemui.wallet.ui.WalletCardCarousel.CarouselSnapHelper.1
                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 200.0f / displayMetrics.densityDpi;
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public final int calculateTimeForScrolling(int i) {
                    return Math.min(80, super.calculateTimeForScrolling(i));
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
                public final void onTargetFound(View view, RecyclerView.SmoothScroller.Action action) {
                    int[] iArrCalculateDistanceToFinalSnap = CarouselSnapHelper.this.calculateDistanceToFinalSnap(layoutManager, view);
                    int i = iArrCalculateDistanceToFinalSnap[0];
                    int i2 = iArrCalculateDistanceToFinalSnap[1];
                    int iCalculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(i), Math.abs(i2)));
                    if (iCalculateTimeForDeceleration > 0) {
                        action.update(i, i2, iCalculateTimeForDeceleration, this.mDecelerateInterpolator);
                    }
                }
            };
        }

        @Override // androidx.recyclerview.widget.PagerSnapHelper, androidx.recyclerview.widget.SnapHelper
        public final View findSnapView(RecyclerView.LayoutManager layoutManager) {
            View viewFindSnapView = super.findSnapView(layoutManager);
            if (viewFindSnapView == null) {
                return null;
            }
            WalletCardViewInfo walletCardViewInfo = ((WalletCardViewHolder) viewFindSnapView.getTag()).mCardViewInfo;
            WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
            walletCardCarousel.mSelectionListener.onCardSelected(walletCardViewInfo);
            walletCardCarousel.mCardScrollListener.onCardScroll(walletCardViewInfo, walletCardViewInfo, 0.0f);
            return viewFindSnapView;
        }

        private CarouselSnapHelper() {
        }
    }

    public class WalletCardCarouselAdapter extends RecyclerView.Adapter {
        public List mData;

        public /* synthetic */ WalletCardCarouselAdapter(WalletCardCarousel walletCardCarousel, int i) {
            this();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.mData.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final long getItemId(int i) {
            return ((WalletScreenController.QAWalletCardViewInfo) ((WalletCardViewInfo) this.mData.get(i))).mWalletCard.getCardId().hashCode();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
            WalletCardViewHolder walletCardViewHolder = (WalletCardViewHolder) viewHolder;
            final WalletCardViewInfo walletCardViewInfo = (WalletCardViewInfo) this.mData.get(i);
            walletCardViewHolder.mCardViewInfo = walletCardViewInfo;
            WalletScreenController.QAWalletCardViewInfo qAWalletCardViewInfo = (WalletScreenController.QAWalletCardViewInfo) walletCardViewInfo;
            if (qAWalletCardViewInfo.mWalletCard.getCardId().isEmpty()) {
                walletCardViewHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER);
            }
            walletCardViewHolder.mImageView.setImageDrawable(qAWalletCardViewInfo.mCardDrawable);
            CharSequence contentDescription = qAWalletCardViewInfo.mWalletCard.getContentDescription();
            CardView cardView = walletCardViewHolder.mCardView;
            cardView.setContentDescription(contentDescription);
            cardView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.wallet.ui.WalletCardCarousel$WalletCardCarouselAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WalletCard walletCard;
                    WalletCardCarousel.WalletCardCarouselAdapter walletCardCarouselAdapter = this.f$0;
                    int i2 = i;
                    WalletCardViewInfo walletCardViewInfo2 = walletCardViewInfo;
                    WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
                    if (i2 != walletCardCarousel.mCenteredAdapterPosition) {
                        WalletScreenController walletScreenController = walletCardCarousel.mSelectionListener;
                        if (walletScreenController.mFalsingManager.isFalseTap(1)) {
                            return;
                        }
                        walletScreenController.mCardCarousel.smoothScrollToPosition(i2);
                        return;
                    }
                    WalletScreenController walletScreenController2 = walletCardCarousel.mSelectionListener;
                    if (walletScreenController2.mFalsingManager.isFalseTap(1) || !(walletCardViewInfo2 instanceof WalletScreenController.QAWalletCardViewInfo) || (walletCard = ((WalletScreenController.QAWalletCardViewInfo) walletCardViewInfo2).mWalletCard) == null || walletCard.getPendingIntent() == null) {
                        return;
                    }
                    if (!walletScreenController2.mKeyguardStateController.isUnlocked()) {
                        walletScreenController2.mUiEventLogger.log(WalletUiEvent.QAW_UNLOCK_FROM_CARD_CLICK);
                    }
                    walletScreenController2.mUiEventLogger.log(WalletUiEvent.QAW_CLICK_CARD);
                    walletScreenController2.mActivityStarter.startPendingIntentDismissingKeyguard(((WalletScreenController.QAWalletCardViewInfo) walletCardViewInfo2).mWalletCard.getPendingIntent());
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View viewM = KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.wallet_card_view, viewGroup, false);
            WalletCardViewHolder walletCardViewHolder = new WalletCardViewHolder(viewM);
            WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
            float f = walletCardCarousel.mCornerRadiusPx;
            CardView cardView = walletCardViewHolder.mCardView;
            cardView.setRadius(f);
            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            layoutParams.width = walletCardCarousel.mCardWidthPx;
            layoutParams.height = walletCardCarousel.mCardHeightPx;
            viewM.setTag(walletCardViewHolder);
            return walletCardViewHolder;
        }

        private WalletCardCarouselAdapter() {
            this.mData = Collections.EMPTY_LIST;
        }
    }

    public WalletCardCarousel(Context context) {
        this(context, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int width = getWidth();
        if (this.mWalletCardCarouselAdapter.mData.size() > 1 && width < this.mTotalCardWidth * 1.5d) {
            this.mSystemGestureExclusionZone.set(0, 0, width, getHeight());
            setSystemGestureExclusionRects(Collections.singletonList(this.mSystemGestureExclusionZone));
        }
        if (width != this.mExpectedViewWidth) {
            updatePadding(width);
        }
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(final View view) {
        super.onViewAdded(view);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int i = this.mCardMarginPx;
        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = i;
        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = i;
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.wallet.ui.WalletCardCarousel$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                WalletCardCarousel walletCardCarousel = this.f$0;
                View view3 = view;
                int i10 = WalletCardCarousel.$r8$clinit;
                walletCardCarousel.updateCardView(view3);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void scrollToPosition(int i) {
        super.scrollToPosition(i);
        this.mSelectionListener.onCardSelected((WalletCardViewInfo) this.mWalletCardCarouselAdapter.mData.get(i));
    }

    public final void updateCardView(View view) {
        CardView cardView = ((WalletCardViewHolder) view.getTag()).mCardView;
        float width = getWidth() / 2.0f;
        float left = (view.getLeft() + view.getRight()) / 2.0f;
        float f = left - width;
        float fMax = Math.max(0.83f, 1.0f - Math.abs(f / view.getWidth()));
        cardView.setScaleX(fMax);
        cardView.setScaleY(fMax);
        int right = left < width ? view.getRight() + this.mCardMarginPx : view.getLeft() - this.mCardMarginPx;
        if (Math.abs(f) >= this.mCardCenterToScreenCenterDistancePx || RecyclerView.getChildAdapterPosition(view) == -1) {
            return;
        }
        this.mCenteredAdapterPosition = RecyclerView.getChildAdapterPosition(view);
        this.mEdgeToCenterDistance = right - width;
        this.mCardCenterToScreenCenterDistancePx = Math.abs(f);
    }

    public final void updatePadding(int i) {
        int i2;
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition;
        int iMax = Math.max(0, ((i - this.mTotalCardWidth) / 2) - this.mCardMarginPx);
        setPadding(iMax, getPaddingTop(), iMax, getPaddingBottom());
        WalletCardCarouselAdapter walletCardCarouselAdapter = this.mWalletCardCarouselAdapter;
        if (walletCardCarouselAdapter == null || walletCardCarouselAdapter.mData.size() <= 0 || (i2 = this.mCenteredAdapterPosition) == -1 || (viewHolderFindViewHolderForAdapterPosition = findViewHolderForAdapterPosition(i2)) == null) {
            return;
        }
        View view = viewHolderFindViewHolderForAdapterPosition.itemView;
        scrollBy(((view.getRight() + view.getLeft()) / 2) - ((getRight() + getLeft()) / 2), 0);
    }

    public WalletCardCarousel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSystemGestureExclusionZone = new Rect();
        this.mCenteredAdapterPosition = -1;
        this.mEdgeToCenterDistance = Float.MAX_VALUE;
        this.mCardCenterToScreenCenterDistancePx = Float.MAX_VALUE;
        int i = 0;
        setLayoutManager(new LinearLayoutManager(context, 0, false));
        addOnScrollListener(new CardCarouselScrollListener(this, i));
        new CarouselSnapHelper(this, i).attachToRecyclerView(this);
        WalletCardCarouselAdapter walletCardCarouselAdapter = new WalletCardCarouselAdapter(this, i);
        this.mWalletCardCarouselAdapter = walletCardCarouselAdapter;
        walletCardCarouselAdapter.setHasStableIds(true);
        setAdapter(walletCardCarouselAdapter);
        ViewCompat.setAccessibilityDelegate(this, new CardCarouselAccessibilityDelegate(this, this));
        addItemDecoration(new DotIndicatorDecoration(getContext()));
    }
}
