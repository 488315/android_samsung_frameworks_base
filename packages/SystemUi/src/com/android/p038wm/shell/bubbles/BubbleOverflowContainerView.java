package com.android.p038wm.shell.bubbles;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.ContrastColorUtil;
import com.android.p038wm.shell.bubbles.BubbleData;
import com.android.systemui.R;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BubbleOverflowContainerView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BubbleOverflowAdapter mAdapter;
    public BubbleController mController;
    public final C38061 mDataListener;
    public LinearLayout mEmptyState;
    public ImageView mEmptyStateImage;
    public TextView mEmptyStateSubtitle;
    public TextView mEmptyStateTitle;
    public int mHorizontalMargin;
    public final BubbleOverflowContainerView$$ExternalSyntheticLambda0 mKeyListener;
    public final List mOverflowBubbles;
    public RecyclerView mRecyclerView;
    public int mVerticalMargin;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OverflowGridLayoutManager extends GridLayoutManager {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OverflowItemDecoration extends RecyclerView.ItemDecoration {
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
        BubbleController bubbleController = this.mController;
        if (bubbleController != null) {
            bubbleController.updateWindowFlagsForBackpress(true);
        }
        setOnKeyListener(this.mKeyListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BubbleController bubbleController = this.mController;
        if (bubbleController != null) {
            bubbleController.updateWindowFlagsForBackpress(false);
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
        this.mRecyclerView.setLayoutManager(new OverflowGridLayoutManager(this, getContext(), getResources().getInteger(R.integer.bubbles_overflow_columns)));
        int i = 0;
        if (this.mRecyclerView.mItemDecorations.size() == 0) {
            this.mRecyclerView.addItemDecoration(new OverflowItemDecoration(this, i));
        }
        Context context = getContext();
        List list = this.mOverflowBubbles;
        final BubbleController bubbleController = this.mController;
        Objects.requireNonNull(bubbleController);
        BubbleOverflowAdapter bubbleOverflowAdapter = new BubbleOverflowAdapter(context, list, new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleOverflowContainerView$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BubbleController.this.promoteBubbleFromOverflow((Bubble) obj);
            }
        }, this.mController.getPositioner());
        this.mAdapter = bubbleOverflowAdapter;
        this.mRecyclerView.setAdapter(bubbleOverflowAdapter);
        ((ArrayList) this.mOverflowBubbles).clear();
        ((ArrayList) this.mOverflowBubbles).addAll(this.mController.mBubbleData.getOverflowBubbles());
        this.mAdapter.notifyDataSetChanged();
        this.mController.mOverflowListener = this.mDataListener;
        updateEmptyStateVisibility();
        Resources resources = getResources();
        boolean z = (resources.getConfiguration().uiMode & 48) == 32;
        this.mHorizontalMargin = resources.getDimensionPixelSize(R.dimen.bubble_overflow_item_padding_horizontal);
        this.mVerticalMargin = resources.getDimensionPixelSize(R.dimen.bubble_overflow_item_padding_vertical);
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.invalidateItemDecorations();
        }
        this.mEmptyStateImage.setVisibility(8);
        this.mEmptyStateSubtitle.setVisibility(8);
        this.mEmptyStateTitle.setText(R.string.sec_bubble_overflow_empty_text);
        findViewById(R.id.bubble_overflow_container).setBackgroundColor(z ? resources.getColor(R.color.bubbles_dark) : resources.getColor(R.color.bubbles_light));
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{android.R.^attr-private.materialColorOnSecondaryFixed, android.R.^attr-private.magnifierHeight});
        int color = obtainStyledAttributes.getColor(0, z ? EmergencyPhoneWidget.BG_COLOR : -1);
        int ensureTextContrast = ContrastColorUtil.ensureTextContrast(((LinearLayout) this).mContext.getColor(R.color.no_more_bubble_text_color), color, z);
        obtainStyledAttributes.recycle();
        setBackgroundColor(color);
        this.mEmptyStateTitle.setTextColor(ensureTextContrast);
        this.mEmptyStateSubtitle.setTextColor(ensureTextContrast);
        this.mEmptyStateTitle.setTypeface(Typeface.create(Typeface.create("sec", 0), 400, false));
    }

    public final void updateEmptyStateVisibility() {
        this.mEmptyState.setVisibility(((ArrayList) this.mOverflowBubbles).isEmpty() ? 0 : 8);
        this.mRecyclerView.setVisibility(((ArrayList) this.mOverflowBubbles).isEmpty() ? 8 : 0);
    }

    public final void updateFontSize() {
        float dimensionPixelSize = ((LinearLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.no_more_bubble_text_size);
        this.mEmptyStateTitle.setTextSize(0, dimensionPixelSize);
        this.mEmptyStateSubtitle.setTextSize(0, dimensionPixelSize);
    }

    public BubbleOverflowContainerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleOverflowContainerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.wm.shell.bubbles.BubbleOverflowContainerView$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.wm.shell.bubbles.BubbleOverflowContainerView$1] */
    public BubbleOverflowContainerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOverflowBubbles = new ArrayList();
        this.mKeyListener = new View.OnKeyListener() { // from class: com.android.wm.shell.bubbles.BubbleOverflowContainerView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i3, KeyEvent keyEvent) {
                BubbleOverflowContainerView bubbleOverflowContainerView = BubbleOverflowContainerView.this;
                int i4 = BubbleOverflowContainerView.$r8$clinit;
                bubbleOverflowContainerView.getClass();
                if (keyEvent.getAction() != 1 || keyEvent.getKeyCode() != 4) {
                    return false;
                }
                bubbleOverflowContainerView.mController.collapseStack();
                return true;
            }
        };
        this.mDataListener = new BubbleData.Listener() { // from class: com.android.wm.shell.bubbles.BubbleOverflowContainerView.1
            @Override // com.android.wm.shell.bubbles.BubbleData.Listener
            public final void applyUpdate(BubbleData.Update update) {
                Bubble bubble = update.removedOverflowBubble;
                BubbleOverflowContainerView bubbleOverflowContainerView = BubbleOverflowContainerView.this;
                if (bubble != null) {
                    Log.d("Bubbles", "remove: " + bubble);
                    bubble.cleanupExpandedView();
                    bubble.mIconView = null;
                    int indexOf = ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).indexOf(bubble);
                    ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).remove(bubble);
                    bubbleOverflowContainerView.mAdapter.notifyItemRemoved(indexOf);
                }
                Bubble bubble2 = update.addedOverflowBubble;
                if (bubble2 != null) {
                    int indexOf2 = ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).indexOf(bubble2);
                    Log.d("Bubbles", "add: " + bubble2 + " prevIndex: " + indexOf2);
                    if (indexOf2 > 0) {
                        ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).remove(bubble2);
                        ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).add(0, bubble2);
                        bubbleOverflowContainerView.mAdapter.notifyItemMoved(indexOf2, 0);
                    } else {
                        ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).add(0, bubble2);
                        bubbleOverflowContainerView.mAdapter.notifyItemInserted(0);
                    }
                }
                bubbleOverflowContainerView.updateEmptyStateVisibility();
                Log.d("Bubbles", BubbleDebugConfig.formatBubblesString(bubbleOverflowContainerView.mController.mBubbleData.getOverflowBubbles(), null));
            }
        };
        setFocusableInTouchMode(true);
    }
}
