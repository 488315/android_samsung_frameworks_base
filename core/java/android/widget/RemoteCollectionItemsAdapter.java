package android.widget;

import android.appwidget.AppWidgetHostView;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntStream;

class RemoteCollectionItemsAdapter extends BaseAdapter {
    private RemoteViews.ColorResources mColorResources;
    private RemoteViews.InteractionHandler mInteractionHandler;
    private RemoteViews.RemoteCollectionItems mItems;
    private SparseIntArray mLayoutIdToViewType;
    private final int mViewTypeCount;

    RemoteCollectionItemsAdapter(
            RemoteViews.RemoteCollectionItems items,
            RemoteViews.InteractionHandler interactionHandler,
            RemoteViews.ColorResources colorResources) {
        this.mViewTypeCount = items.getViewTypeCount();
        this.mItems = items;
        this.mInteractionHandler = interactionHandler;
        this.mColorResources = colorResources;
        initLayoutIdToViewType();
    }

    void setData(
            RemoteViews.RemoteCollectionItems items,
            RemoteViews.InteractionHandler interactionHandler,
            RemoteViews.ColorResources colorResources) {
        if (this.mViewTypeCount < items.getViewTypeCount()) {
            throw new IllegalArgumentException(
                    "RemoteCollectionItemsAdapter cannot increase view type count after creation");
        }
        this.mItems = items;
        this.mInteractionHandler = interactionHandler;
        this.mColorResources = colorResources;
        initLayoutIdToViewType();
        notifyDataSetChanged();
    }

    private void initLayoutIdToViewType() {
        SparseIntArray previousLayoutIdToViewType = this.mLayoutIdToViewType;
        this.mLayoutIdToViewType = new SparseIntArray(this.mViewTypeCount);
        int[] layoutIds =
                IntStream.range(0, this.mItems.getItemCount())
                        .map(
                                new IntUnaryOperator() { // from class:
                                                         // android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda0
                                    @Override // java.util.function.IntUnaryOperator
                                    public final int applyAsInt(int i) {
                                        int lambda$initLayoutIdToViewType$0;
                                        lambda$initLayoutIdToViewType$0 =
                                                RemoteCollectionItemsAdapter.this
                                                        .lambda$initLayoutIdToViewType$0(i);
                                        return lambda$initLayoutIdToViewType$0;
                                    }
                                })
                        .distinct()
                        .toArray();
        if (layoutIds.length > this.mViewTypeCount) {
            throw new IllegalArgumentException(
                    "Collection items uses "
                            + layoutIds.length
                            + " distinct layouts, which is more than view type count of "
                            + this.mViewTypeCount);
        }
        boolean[] processedLayoutIdIndices = new boolean[layoutIds.length];
        final boolean[] assignedViewTypes = new boolean[this.mViewTypeCount];
        if (previousLayoutIdToViewType != null) {
            for (int i = 0; i < layoutIds.length; i++) {
                int layoutId = layoutIds[i];
                int previousViewType = previousLayoutIdToViewType.get(layoutId, -1);
                if (previousViewType >= 0) {
                    this.mLayoutIdToViewType.put(layoutId, previousViewType);
                    processedLayoutIdIndices[i] = true;
                    assignedViewTypes[previousViewType] = true;
                }
            }
        }
        int lastViewType = -1;
        for (int i2 = 0; i2 < layoutIds.length; i2++) {
            if (!processedLayoutIdIndices[i2]) {
                int layoutId2 = layoutIds[i2];
                int viewType =
                        IntStream.range(lastViewType + 1, layoutIds.length)
                                .filter(
                                        new IntPredicate() { // from class:
                                                             // android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda1
                                            @Override // java.util.function.IntPredicate
                                            public final boolean test(int i3) {
                                                return RemoteCollectionItemsAdapter
                                                        .lambda$initLayoutIdToViewType$1(
                                                                assignedViewTypes, i3);
                                            }
                                        })
                                .findFirst()
                                .orElseThrow(
                                        new Supplier() { // from class:
                                                         // android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                return RemoteCollectionItemsAdapter
                                                        .lambda$initLayoutIdToViewType$2();
                                            }
                                        });
                this.mLayoutIdToViewType.put(layoutId2, viewType);
                processedLayoutIdIndices[i2] = true;
                assignedViewTypes[viewType] = true;
                lastViewType = viewType;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$initLayoutIdToViewType$0(int position) {
        return this.mItems.getItemView(position).getLayoutId();
    }

    static /* synthetic */ boolean lambda$initLayoutIdToViewType$1(
            boolean[] assignedViewTypes, int type) {
        return !assignedViewTypes[type];
    }

    static /* synthetic */ IllegalStateException lambda$initLayoutIdToViewType$2() {
        return new IllegalStateException(
                "RemoteCollectionItems has more distinct layout ids than its view type count");
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mItems.getItemCount();
    }

    @Override // android.widget.Adapter
    public RemoteViews getItem(int position) {
        return this.mItems.getItemView(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return this.mItems.getItemId(position);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int position) {
        return this.mLayoutIdToViewType.get(this.mItems.getItemView(position).getLayoutId());
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return this.mViewTypeCount;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return this.mItems.hasStableIds();
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position >= getCount()) {
            return null;
        }
        RemoteViews item = this.mItems.getItemView(position);
        item.addFlags(2);
        AppWidgetHostView newView =
                convertView instanceof AppWidgetHostView.AdapterChildHostView
                        ? (AppWidgetHostView.AdapterChildHostView) convertView
                        : new AppWidgetHostView.AdapterChildHostView(parent.getContext());
        newView.setInteractionHandler(this.mInteractionHandler);
        newView.setColorResourcesNoReapply(this.mColorResources);
        newView.updateAppWidget(item);
        return newView;
    }
}
