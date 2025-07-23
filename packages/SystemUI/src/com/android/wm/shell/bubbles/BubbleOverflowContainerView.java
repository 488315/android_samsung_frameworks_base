package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewRootImpl;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackAnimationCallback;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.wm.shell.shared.TypefaceUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleOverflowContainerView extends LinearLayout {
    public float fontSize;
    public BubbleOverflowAdapter mAdapter;
    public final AnonymousClass2 mDataListener;
    public LinearLayout mEmptyState;
    public ImageView mEmptyStateImage;
    public TextView mEmptyStateSubtitle;
    public TextView mEmptyStateTitle;
    public BubbleExpandedViewManager mExpandedViewManager;
    public int mHorizontalMargin;
    public boolean mIsBackCallbackRegistered;
    public final AnonymousClass1 mOnBackInvokedCallback;
    public final List mOverflowBubbles;
    public BubblePositioner mPositioner;
    public RecyclerView mRecyclerView;
    public int mVerticalMargin;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.BubbleOverflowContainerView$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class OverflowGridLayoutManager extends GridLayoutManager {
        public OverflowGridLayoutManager(BubbleOverflowContainerView bubbleOverflowContainerView, Context context, int i) {
            super(context, i);
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
            int itemCount = state.getItemCount();
            int columnCountForAccessibility = super.getColumnCountForAccessibility(recycler, state);
            return itemCount < columnCountForAccessibility ? itemCount : columnCountForAccessibility;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class OverflowItemDecoration extends RecyclerView.ItemDecoration {
        public /* synthetic */ OverflowItemDecoration(BubbleOverflowContainerView bubbleOverflowContainerView, int i) {
            this();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            BubbleOverflowContainerView bubbleOverflowContainerView = BubbleOverflowContainerView.this;
            int i = bubbleOverflowContainerView.mHorizontalMargin;
            rect.left = i;
            int i2 = bubbleOverflowContainerView.mVerticalMargin;
            rect.top = i2;
            rect.right = i;
            rect.bottom = i2;
        }

        private OverflowItemDecoration() {
        }
    }

    public BubbleOverflowContainerView(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        BubbleExpandedViewManager bubbleExpandedViewManager = this.mExpandedViewManager;
        if (bubbleExpandedViewManager != null) {
            ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleExpandedViewManager).$controller.updateWindowFlagsForBackpress(true);
        }
        if (this.mIsBackCallbackRegistered) {
            return;
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            Log.d("Bubbles", "view root was null, could not register back callback");
        } else {
            viewRootImpl.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
            this.mIsBackCallbackRegistered = true;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BubbleExpandedViewManager bubbleExpandedViewManager = this.mExpandedViewManager;
        if (bubbleExpandedViewManager != null) {
            ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleExpandedViewManager).$controller.updateWindowFlagsForBackpress(false);
        }
        setOnKeyListener(null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mRecyclerView = (RecyclerView) findViewById(R.id.bubble_overflow_recycler);
        this.mEmptyState = (LinearLayout) findViewById(R.id.bubble_overflow_empty_state);
        this.mEmptyStateTitle = (TextView) findViewById(R.id.bubble_overflow_empty_title);
        this.mEmptyStateSubtitle = (TextView) findViewById(R.id.bubble_overflow_empty_subtitle);
        this.mEmptyStateImage = (ImageView) findViewById(R.id.bubble_overflow_empty_state_image);
    }

    public final void show() {
        requestFocus();
        Resources resources = getResources();
        int round = Math.round(getWidth() / resources.getDimension(R.dimen.bubble_name_width));
        if (round <= 0) {
            round = resources.getInteger(R.integer.bubbles_overflow_columns);
        }
        this.mRecyclerView.setLayoutManager(new OverflowGridLayoutManager(this, getContext(), round));
        if (this.mRecyclerView.mItemDecorations.size() == 0) {
            this.mRecyclerView.addItemDecoration(new OverflowItemDecoration(this, r1));
        }
        Context context = getContext();
        List list = this.mOverflowBubbles;
        final BubbleExpandedViewManager bubbleExpandedViewManager = this.mExpandedViewManager;
        Objects.requireNonNull(bubbleExpandedViewManager);
        BubbleOverflowAdapter bubbleOverflowAdapter = new BubbleOverflowAdapter(context, list, new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleOverflowContainerView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((BubbleExpandedViewManager$Companion$fromBubbleController$1) BubbleExpandedViewManager.this).$controller.promoteBubbleFromOverflow((Bubble) obj);
            }
        }, this.mPositioner);
        this.mAdapter = bubbleOverflowAdapter;
        this.mRecyclerView.setAdapter(bubbleOverflowAdapter);
        ((ArrayList) this.mOverflowBubbles).clear();
        ((ArrayList) this.mOverflowBubbles).addAll(((BubbleExpandedViewManager$Companion$fromBubbleController$1) this.mExpandedViewManager).$controller.mBubbleData.getOverflowBubbles());
        this.mAdapter.notifyDataSetChanged();
        ((BubbleExpandedViewManager$Companion$fromBubbleController$1) this.mExpandedViewManager).$controller.mOverflowListener = this.mDataListener;
        updateEmptyStateVisibility();
        Resources resources2 = getResources();
        r1 = (resources2.getConfiguration().uiMode & 48) == 32 ? 1 : 0;
        this.mHorizontalMargin = resources2.getDimensionPixelSize(R.dimen.bubble_overflow_item_padding_horizontal);
        this.mVerticalMargin = resources2.getDimensionPixelSize(R.dimen.bubble_overflow_item_padding_vertical);
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.invalidateItemDecorations();
        }
        this.mEmptyStateImage.setVisibility(8);
        this.mEmptyStateSubtitle.setVisibility(8);
        this.mEmptyStateTitle.setText(R.string.sec_bubble_overflow_empty_text);
        findViewById(R.id.bubble_overflow_container).setBackgroundColor(r1 != 0 ? resources2.getColor(R.color.bubbles_dark) : resources2.getColor(R.color.bubbles_light));
        int color = getContext().getColor(android.R.color.side_fps_toast_background);
        int color2 = getContext().getColor(android.R.color.search_url_text_material_light);
        setBackgroundColor(color);
        this.mEmptyStateTitle.setTextColor(color2);
        this.mEmptyStateSubtitle.setTextColor(color2);
        TypefaceUtils.FontFamily fontFamily = TypefaceUtils.FontFamily.GSF_TITLE_MEDIUM;
        TypefaceUtils.setTypeface();
        TypefaceUtils.setTypeface();
    }

    public final void updateEmptyStateVisibility() {
        this.mEmptyState.setVisibility(((ArrayList) this.mOverflowBubbles).isEmpty() ? 0 : 8);
        this.mRecyclerView.setVisibility(((ArrayList) this.mOverflowBubbles).isEmpty() ? 8 : 0);
    }

    public final void updateFontSize() {
        float dimensionPixelSize = ((LinearLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.no_more_bubble_text_size);
        this.fontSize = dimensionPixelSize;
        this.mEmptyStateTitle.setTextSize(0, dimensionPixelSize);
        this.mEmptyStateSubtitle.setTextSize(0, this.fontSize);
    }

    public final void updateLocale() {
        this.mEmptyStateTitle.setText(((LinearLayout) this).mContext.getString(R.string.bubble_overflow_empty_title));
        this.mEmptyStateSubtitle.setText(((LinearLayout) this).mContext.getString(R.string.bubble_overflow_empty_subtitle));
    }

    public BubbleOverflowContainerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleOverflowContainerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.wm.shell.bubbles.BubbleOverflowContainerView$1] */
    public BubbleOverflowContainerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOverflowBubbles = new ArrayList();
        this.mIsBackCallbackRegistered = false;
        this.mOnBackInvokedCallback = new OnBackAnimationCallback() { // from class: com.android.wm.shell.bubbles.BubbleOverflowContainerView.1
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                ((BubbleExpandedViewManager$Companion$fromBubbleController$1) BubbleOverflowContainerView.this.mExpandedViewManager).$controller.collapseStack();
            }
        };
        this.mDataListener = new AnonymousClass2();
        setFocusableInTouchMode(true);
    }
}
