package com.android.systemui.qs.customize;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.customize.TileQueryHelper;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TileAdapter extends RecyclerView.Adapter {
    public final TileAdapterDelegate mAccessibilityDelegate;
    public int mAccessibilityFromIndex;
    public final AnonymousClass5 mCallbacks;
    public final Context mContext;
    public Holder mCurrentDrag;
    public List mCurrentSpecs;
    public final TileItemDecoration mDecoration;
    public int mEditIndex;
    public int mFocusIndex;
    public final QSHost mHost;
    public final ItemTouchHelper mItemTouchHelper;
    public final MarginTileDecoration mMarginDecoration;
    public final int mMinNumTiles;
    public final int mMinTileViewHeight;
    public boolean mNeedsFocus;
    public int mNumColumns;
    public RecyclerView mRecyclerView;
    public final AnonymousClass4 mSizeLookup;
    public int mTileDividerIndex;
    public final UiEventLogger mUiEventLogger;
    public final Handler mHandler = new Handler();
    public final List mTiles = new ArrayList();
    public int mAccessibilityAction = 0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Holder extends RecyclerView.ViewHolder {
        public final QSTileViewImpl mTileView;

        public Holder(View view) {
            super(view);
            if (view instanceof FrameLayout) {
                QSTileViewImpl qSTileViewImpl = (QSTileViewImpl) ((FrameLayout) view).getChildAt(0);
                this.mTileView = qSTileViewImpl;
                qSTileViewImpl.icon.mAnimationEnabled = false;
                qSTileViewImpl.setTag(this);
                ViewCompat.setAccessibilityDelegate(qSTileViewImpl, TileAdapter.this.mAccessibilityDelegate);
            }
        }

        public final void stopDrag() {
            this.itemView.animate().setDuration(100L).scaleX(1.0f).scaleY(1.0f);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MarginTileDecoration extends RecyclerView.ItemDecoration {
        public int mHalfMargin;

        public /* synthetic */ MarginTileDecoration(int i) {
            this();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (recyclerView.getLayoutManager() == null) {
                return;
            }
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            int i = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).mSpanIndex;
            if (view instanceof TextView) {
                super.getItemOffsets(rect, view, recyclerView, state);
                return;
            }
            if (i != 0 && i != gridLayoutManager.mSpanCount - 1) {
                int i2 = this.mHalfMargin;
                rect.left = i2;
                rect.right = i2;
            } else {
                if (recyclerView.isLayoutRtl()) {
                    if (i == 0) {
                        rect.left = this.mHalfMargin;
                        rect.right = 0;
                        return;
                    } else {
                        rect.left = 0;
                        rect.right = this.mHalfMargin;
                        return;
                    }
                }
                if (i == 0) {
                    rect.left = 0;
                    rect.right = this.mHalfMargin;
                } else {
                    rect.left = this.mHalfMargin;
                    rect.right = 0;
                }
            }
        }

        private MarginTileDecoration() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TileItemDecoration extends RecyclerView.ItemDecoration {
        public final Drawable mDrawable;

        public /* synthetic */ TileItemDecoration(TileAdapter tileAdapter, Context context, int i) {
            this(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            super.onDraw(canvas, recyclerView, state);
            int childCount = recyclerView.getChildCount();
            int width = recyclerView.getWidth();
            int bottom = recyclerView.getBottom();
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childAt);
                TileAdapter tileAdapter = TileAdapter.this;
                if (childViewHolder != tileAdapter.mCurrentDrag && childViewHolder.getBindingAdapterPosition() != 0 && (childViewHolder.getBindingAdapterPosition() >= tileAdapter.mEditIndex || (childAt instanceof TextView))) {
                    int top = childAt.getTop();
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    this.mDrawable.setBounds(0, Math.round(childAt.getTranslationY()) + top, width, bottom);
                    this.mDrawable.draw(canvas);
                    return;
                }
            }
        }

        private TileItemDecoration(Context context) {
            this.mDrawable = context.getDrawable(R.drawable.qs_customize_tile_decoration);
        }
    }

    /* renamed from: -$$Nest$mselectPosition, reason: not valid java name */
    public static void m2066$$Nest$mselectPosition(TileAdapter tileAdapter, int i) {
        if (tileAdapter.mAccessibilityAction == 1) {
            List list = tileAdapter.mTiles;
            int i2 = tileAdapter.mEditIndex;
            tileAdapter.mEditIndex = i2 - 1;
            ((ArrayList) list).remove(i2);
        }
        tileAdapter.mAccessibilityAction = 0;
        tileAdapter.move(tileAdapter.mAccessibilityFromIndex, i, false);
        tileAdapter.mFocusIndex = i;
        tileAdapter.mNeedsFocus = true;
        tileAdapter.notifyDataSetChanged();
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [androidx.recyclerview.widget.GridLayoutManager$SpanSizeLookup, com.android.systemui.qs.customize.TileAdapter$4] */
    public TileAdapter(Context context, QSHost qSHost, UiEventLogger uiEventLogger, FeatureFlags featureFlags) {
        int i = 0;
        ?? r1 = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.qs.customize.TileAdapter.4
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public final int getSpanSize(int i2) {
                TileAdapter tileAdapter = TileAdapter.this;
                int itemViewType = tileAdapter.getItemViewType(i2);
                if (itemViewType == 1 || itemViewType == 4 || itemViewType == 3) {
                    return tileAdapter.mNumColumns;
                }
                return 1;
            }
        };
        this.mSizeLookup = r1;
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() { // from class: com.android.systemui.qs.customize.TileAdapter.5
            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean canDropOver(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int bindingAdapterPosition = viewHolder2.getBindingAdapterPosition();
                if (bindingAdapterPosition == 0 || bindingAdapterPosition == -1) {
                    return false;
                }
                TileAdapter tileAdapter = TileAdapter.this;
                if (!(tileAdapter.mCurrentSpecs.size() > tileAdapter.mMinNumTiles)) {
                    int bindingAdapterPosition2 = viewHolder.getBindingAdapterPosition();
                    int i2 = tileAdapter.mEditIndex;
                    if (bindingAdapterPosition2 < i2) {
                        return bindingAdapterPosition < i2;
                    }
                }
                return bindingAdapterPosition <= tileAdapter.mEditIndex + 1;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                ((Holder) viewHolder).stopDrag();
                super.clearView(recyclerView, viewHolder);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
                int i2 = viewHolder.mItemViewType;
                return (i2 == 1 || i2 == 3 || i2 == 4) ? ItemTouchHelper.Callback.makeMovementFlags(0, 0) : ItemTouchHelper.Callback.makeMovementFlags(15, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean isLongPressDragEnabled() {
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                if (bindingAdapterPosition == 0 || bindingAdapterPosition == -1 || bindingAdapterPosition2 == 0 || bindingAdapterPosition2 == -1) {
                    return false;
                }
                TileAdapter.this.move(bindingAdapterPosition, bindingAdapterPosition2, true);
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i2) {
                super.onSelectedChanged(viewHolder, i2);
                if (i2 != 2) {
                    viewHolder = null;
                }
                TileAdapter tileAdapter = TileAdapter.this;
                Holder holder = tileAdapter.mCurrentDrag;
                if (viewHolder == holder) {
                    return;
                }
                if (holder != null) {
                    int bindingAdapterPosition = holder.getBindingAdapterPosition();
                    if (bindingAdapterPosition == -1) {
                        return;
                    }
                    TileQueryHelper.TileInfo tileInfo = (TileQueryHelper.TileInfo) ((ArrayList) tileAdapter.mTiles).get(bindingAdapterPosition);
                    CustomizeTileView customizeTileView = (CustomizeTileView) tileAdapter.mCurrentDrag.mTileView;
                    customizeTileView.showAppLabel = bindingAdapterPosition > tileAdapter.mEditIndex && !tileInfo.isSystem;
                    TextView textView = customizeTileView.secondaryLabel;
                    TextView textView2 = textView != null ? textView : null;
                    if (textView == null) {
                        textView = null;
                    }
                    textView2.setVisibility((!customizeTileView.showAppLabel || TextUtils.isEmpty(textView.getText())) ? 8 : 0);
                    tileAdapter.mCurrentDrag.stopDrag();
                    tileAdapter.mCurrentDrag = null;
                }
                if (viewHolder != null) {
                    Holder holder2 = (Holder) viewHolder;
                    tileAdapter.mCurrentDrag = holder2;
                    holder2.itemView.animate().setDuration(100L).scaleX(1.2f).scaleY(1.2f);
                }
                tileAdapter.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.customize.TileAdapter.5.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TileAdapter tileAdapter2 = TileAdapter.this;
                        tileAdapter2.notifyItemChanged(tileAdapter2.mEditIndex);
                    }
                });
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSwiped(RecyclerView.ViewHolder viewHolder) {
            }
        };
        this.mContext = context;
        this.mHost = qSHost;
        this.mUiEventLogger = uiEventLogger;
        this.mItemTouchHelper = new ItemTouchHelper(callback);
        this.mDecoration = new TileItemDecoration(this, context, i);
        this.mMarginDecoration = new MarginTileDecoration(i);
        this.mMinNumTiles = context.getResources().getInteger(R.integer.quick_settings_min_num_tiles);
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        this.mNumColumns = context.getResources().getInteger(R.integer.quick_settings_num_columns);
        this.mAccessibilityDelegate = new TileAdapterDelegate();
        r1.mCacheSpanIndices = true;
        new TextView(context);
        this.mMinTileViewHeight = context.getResources().getDimensionPixelSize(R.dimen.qs_tile_height);
    }

    public static String strip(TileQueryHelper.TileInfo tileInfo) {
        String str = tileInfo.spec;
        return str.startsWith("custom(") ? CustomTile.getComponentFromSpec(str).getPackageName() : str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return ((ArrayList) this.mTiles).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        if (i == 0) {
            return 3;
        }
        if (this.mAccessibilityAction == 1 && i == this.mEditIndex - 1) {
            return 2;
        }
        if (i == this.mTileDividerIndex) {
            return 4;
        }
        return ((ArrayList) this.mTiles).get(i) == null ? 1 : 0;
    }

    public final void move(int i, int i2, boolean z) {
        if (i2 == i) {
            return;
        }
        ArrayList arrayList = (ArrayList) this.mTiles;
        arrayList.add(i2, arrayList.remove(i));
        if (z) {
            notifyItemMoved(i, i2);
        }
        this.mEditIndex = -1;
        this.mTileDividerIndex = ((ArrayList) this.mTiles).size();
        for (int i3 = 1; i3 < ((ArrayList) this.mTiles).size(); i3++) {
            if (((ArrayList) this.mTiles).get(i3) == null) {
                if (this.mEditIndex == -1) {
                    this.mEditIndex = i3;
                } else {
                    this.mTileDividerIndex = i3;
                }
            }
        }
        int size = ((ArrayList) this.mTiles).size() - 1;
        int i4 = this.mTileDividerIndex;
        if (size == i4) {
            notifyItemChanged(i4);
        }
        int i5 = this.mEditIndex;
        if (i2 >= i5) {
            this.mUiEventLogger.log(QSEditEvent.QS_EDIT_REMOVE, 0, strip((TileQueryHelper.TileInfo) ((ArrayList) this.mTiles).get(i2)));
        } else if (i >= i5) {
            this.mUiEventLogger.log(QSEditEvent.QS_EDIT_ADD, 0, strip((TileQueryHelper.TileInfo) ((ArrayList) this.mTiles).get(i2)));
        } else {
            this.mUiEventLogger.log(QSEditEvent.QS_EDIT_MOVE, 0, strip((TileQueryHelper.TileInfo) ((ArrayList) this.mTiles).get(i2)));
        }
        ArrayList arrayList2 = new ArrayList();
        this.mNeedsFocus = false;
        if (this.mAccessibilityAction == 1) {
            List list = this.mTiles;
            int i6 = this.mEditIndex - 1;
            this.mEditIndex = i6;
            ((ArrayList) list).remove(i6);
            notifyDataSetChanged();
        }
        this.mAccessibilityAction = 0;
        for (int i7 = 1; i7 < ((ArrayList) this.mTiles).size() && ((ArrayList) this.mTiles).get(i7) != null; i7++) {
            arrayList2.add(((TileQueryHelper.TileInfo) ((ArrayList) this.mTiles).get(i7)).spec);
        }
        this.mHost.changeTilesByUser(this.mCurrentSpecs, arrayList2);
        this.mCurrentSpecs = arrayList2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int i2;
        String string;
        boolean z;
        final Holder holder = (Holder) viewHolder;
        QSTileViewImpl qSTileViewImpl = holder.mTileView;
        if (qSTileViewImpl != null) {
            qSTileViewImpl.setMinimumHeight(this.mMinTileViewHeight);
        }
        int i3 = holder.mItemViewType;
        if (i3 == 3) {
            View view = holder.itemView;
            z = this.mAccessibilityAction == 0;
            view.setFocusable(z);
            view.setImportantForAccessibility(z ? 1 : 4);
            view.setFocusableInTouchMode(z);
            return;
        }
        if (i3 == 4) {
            holder.itemView.setVisibility(this.mTileDividerIndex < ((ArrayList) this.mTiles).size() - 1 ? 0 : 4);
            return;
        }
        if (i3 == 1) {
            Resources resources = this.mContext.getResources();
            if (this.mCurrentDrag == null) {
                string = resources.getString(R.string.drag_to_add_tiles);
            } else {
                int size = this.mCurrentSpecs.size();
                int i4 = this.mMinNumTiles;
                string = (size <= i4 && this.mCurrentDrag.getBindingAdapterPosition() < this.mEditIndex) ? resources.getString(R.string.drag_to_remove_disabled, Integer.valueOf(i4)) : resources.getString(R.string.drag_to_remove_tiles);
            }
            ((TextView) holder.itemView.findViewById(android.R.id.title)).setText(string);
            View view2 = holder.itemView;
            z = this.mAccessibilityAction == 0;
            view2.setFocusable(z);
            view2.setImportantForAccessibility(z ? 1 : 4);
            view2.setFocusableInTouchMode(z);
            return;
        }
        QSTileViewImpl qSTileViewImpl2 = holder.mTileView;
        if (i3 == 2) {
            qSTileViewImpl2.setClickable(true);
            qSTileViewImpl2.setFocusable(true);
            qSTileViewImpl2.setFocusableInTouchMode(true);
            qSTileViewImpl2.setVisibility(0);
            qSTileViewImpl2.setImportantForAccessibility(1);
            qSTileViewImpl2.setContentDescription(this.mContext.getString(R.string.accessibility_qs_edit_tile_add_to_position, Integer.valueOf(i)));
            qSTileViewImpl2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.TileAdapter.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    TileAdapter.m2066$$Nest$mselectPosition(TileAdapter.this, holder.getLayoutPosition());
                }
            });
            if (this.mNeedsFocus) {
                holder.mTileView.requestLayout();
                holder.mTileView.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.qs.customize.TileAdapter.3
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view3, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
                        holder.mTileView.removeOnLayoutChangeListener(this);
                        holder.mTileView.requestAccessibilityFocus();
                    }
                });
                this.mNeedsFocus = false;
                this.mFocusIndex = -1;
                return;
            }
            return;
        }
        TileQueryHelper.TileInfo tileInfo = (TileQueryHelper.TileInfo) ((ArrayList) this.mTiles).get(i);
        boolean z2 = i > 0 && i < this.mEditIndex;
        if (z2 && this.mAccessibilityAction == 1) {
            tileInfo.state.contentDescription = this.mContext.getString(R.string.accessibility_qs_edit_tile_add_to_position, Integer.valueOf(i));
        } else if (z2 && this.mAccessibilityAction == 2) {
            tileInfo.state.contentDescription = this.mContext.getString(R.string.accessibility_qs_edit_tile_move_to_position, Integer.valueOf(i));
        } else if (z2 || !((i2 = this.mAccessibilityAction) == 2 || i2 == 1)) {
            QSTile.State state = tileInfo.state;
            state.contentDescription = state.label;
        } else {
            tileInfo.state.contentDescription = this.mContext.getString(R.string.accessibilit_qs_edit_tile_add_move_invalid_position);
        }
        tileInfo.state.expandedAccessibilityClassName = "";
        CustomizeTileView customizeTileView = (CustomizeTileView) qSTileViewImpl2;
        Objects.requireNonNull(customizeTileView, "The holder must have a tileView");
        customizeTileView.handleStateChanged(tileInfo.state);
        int i5 = this.mEditIndex;
        boolean z3 = tileInfo.isSystem;
        customizeTileView.showAppLabel = i > i5 && !z3;
        TextView textView = customizeTileView.secondaryLabel;
        TextView textView2 = textView != null ? textView : null;
        if (textView == null) {
            textView = null;
        }
        textView2.setVisibility((!customizeTileView.showAppLabel || TextUtils.isEmpty(textView.getText())) ? 8 : 0);
        boolean z4 = i < this.mEditIndex || z3;
        customizeTileView.showSideView = z4;
        if (!z4) {
            ViewGroup viewGroup = customizeTileView.sideView;
            if (viewGroup == null) {
                viewGroup = null;
            }
            viewGroup.setVisibility(8);
        }
        qSTileViewImpl2.setSelected(true);
        qSTileViewImpl2.setImportantForAccessibility(1);
        qSTileViewImpl2.setClickable(true);
        qSTileViewImpl2.setOnClickListener(null);
        qSTileViewImpl2.setFocusable(true);
        qSTileViewImpl2.setFocusableInTouchMode(true);
        qSTileViewImpl2.setAccessibilityTraversalBefore(-1);
        if (this.mAccessibilityAction != 0) {
            qSTileViewImpl2.setClickable(z2);
            qSTileViewImpl2.setFocusable(z2);
            qSTileViewImpl2.setFocusableInTouchMode(z2);
            if (z2) {
                qSTileViewImpl2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.TileAdapter.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        int layoutPosition = holder.getLayoutPosition();
                        if (layoutPosition == -1) {
                            return;
                        }
                        TileAdapter tileAdapter = TileAdapter.this;
                        if (tileAdapter.mAccessibilityAction != 0) {
                            TileAdapter.m2066$$Nest$mselectPosition(tileAdapter, layoutPosition);
                        }
                    }
                });
            }
        }
        if (i == this.mFocusIndex && this.mNeedsFocus) {
            holder.mTileView.requestLayout();
            holder.mTileView.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.qs.customize.TileAdapter.3
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view3, int i52, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
                    holder.mTileView.removeOnLayoutChangeListener(this);
                    holder.mTileView.requestAccessibilityFocus();
                }
            });
            this.mNeedsFocus = false;
            this.mFocusIndex = -1;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        Holder holder;
        Context context = viewGroup.getContext();
        LayoutInflater from = LayoutInflater.from(context);
        int i2 = 0;
        if (i != 3) {
            if (i == 4) {
                holder = new Holder(from.inflate(R.layout.qs_customize_tile_divider, viewGroup, false));
            } else {
                if (i != 1) {
                    FrameLayout frameLayout = (FrameLayout) from.inflate(R.layout.qs_customize_tile_frame, viewGroup, false);
                    com.android.systemui.Flags.FEATURE_FLAGS.getClass();
                    frameLayout.addView(new CustomizeTileView(context));
                    return new Holder(frameLayout);
                }
                holder = new Holder(from.inflate(R.layout.qs_customize_divider, viewGroup, false));
            }
            return holder;
        }
        View inflate = from.inflate(R.layout.qs_customize_header, viewGroup, false);
        Resources resources = context.getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.QSCustomizeToolbar, com.android.internal.R.styleable.Toolbar);
        int resourceId = obtainStyledAttributes.getResourceId(27, 0);
        obtainStyledAttributes.recycle();
        if (resourceId != 0) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, android.R.styleable.View);
            i2 = obtainStyledAttributes2.getDimensionPixelSize(36, 0);
            obtainStyledAttributes2.recycle();
        }
        inflate.setMinimumHeight(((resources.getDimensionPixelSize(R.dimen.qs_brightness_margin_bottom) + (resources.getDimensionPixelSize(R.dimen.qs_brightness_margin_top) + (resources.getDimensionPixelSize(R.dimen.brightness_mirror_height) + resources.getDimensionPixelSize(R.dimen.qs_panel_padding_top)))) - i2) - resources.getDimensionPixelSize(R.dimen.qs_tile_margin_top_bottom));
        return new Holder(inflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onDetachedFromRecyclerView() {
        this.mRecyclerView = null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final boolean onFailedToRecycleView(RecyclerView.ViewHolder viewHolder) {
        Holder holder = (Holder) viewHolder;
        holder.stopDrag();
        holder.itemView.clearAnimation();
        holder.itemView.setScaleX(1.0f);
        holder.itemView.setScaleY(1.0f);
        return true;
    }
}
