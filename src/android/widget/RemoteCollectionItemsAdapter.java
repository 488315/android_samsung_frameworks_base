package android.widget;

import android.appwidget.AppWidgetHostView;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/* loaded from: classes5.dex */
class RemoteCollectionItemsAdapter extends BaseAdapter {
    private RemoteViews.ColorResources mColorResources;
    private RemoteViews.InteractionHandler mInteractionHandler;
    private RemoteViews.RemoteCollectionItems mItems;
    private SparseIntArray mLayoutIdToViewType;
    private boolean mOnLightBackground;
    private final int mViewTypeCount;

    RemoteCollectionItemsAdapter(RemoteViews.RemoteCollectionItems remoteCollectionItems, RemoteViews.InteractionHandler interactionHandler, RemoteViews.ColorResources colorResources, boolean z) {
        this.mViewTypeCount = remoteCollectionItems.getViewTypeCount();
        this.mItems = remoteCollectionItems;
        this.mInteractionHandler = interactionHandler;
        this.mColorResources = colorResources;
        this.mOnLightBackground = z;
        initLayoutIdToViewType();
    }

    void setData(RemoteViews.RemoteCollectionItems remoteCollectionItems, RemoteViews.InteractionHandler interactionHandler, RemoteViews.ColorResources colorResources, boolean z) {
        if (this.mViewTypeCount < remoteCollectionItems.getViewTypeCount()) {
            throw new IllegalArgumentException("RemoteCollectionItemsAdapter cannot increase view type count after creation");
        }
        this.mItems = remoteCollectionItems;
        this.mInteractionHandler = interactionHandler;
        this.mColorResources = colorResources;
        this.mOnLightBackground = z;
        initLayoutIdToViewType();
        notifyDataSetChanged();
    }

    private void initLayoutIdToViewType() {
        SparseIntArray sparseIntArray = this.mLayoutIdToViewType;
        this.mLayoutIdToViewType = new SparseIntArray(this.mViewTypeCount);
        int[] array = IntStream.range(0, this.mItems.getItemCount()).map(new IntUnaryOperator() { // from class: android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                int lambda$initLayoutIdToViewType$0;
                lambda$initLayoutIdToViewType$0 = RemoteCollectionItemsAdapter.this.lambda$initLayoutIdToViewType$0(i);
                return lambda$initLayoutIdToViewType$0;
            }
        }).distinct().toArray();
        int length = array.length;
        int i = this.mViewTypeCount;
        if (length > i) {
            throw new IllegalArgumentException("Collection items uses " + array.length + " distinct layouts, which is more than view type count of " + this.mViewTypeCount);
        }
        boolean[] zArr = new boolean[array.length];
        final boolean[] zArr2 = new boolean[i];
        int i2 = -1;
        if (sparseIntArray != null) {
            for (int i3 = 0; i3 < array.length; i3++) {
                int i4 = array[i3];
                int i5 = sparseIntArray.get(i4, -1);
                if (i5 >= 0) {
                    this.mLayoutIdToViewType.put(i4, i5);
                    zArr[i3] = true;
                    zArr2[i5] = true;
                }
            }
        }
        for (int i6 = 0; i6 < array.length; i6++) {
            if (!zArr[i6]) {
                int i7 = array[i6];
                i2 = IntStream.range(i2 + 1, array.length).filter(new IntPredicate() { // from class: android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i8) {
                        return RemoteCollectionItemsAdapter.lambda$initLayoutIdToViewType$1(zArr2, i8);
                    }
                }).findFirst().orElseThrow(new Supplier() { // from class: android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return RemoteCollectionItemsAdapter.lambda$initLayoutIdToViewType$2();
                    }
                });
                this.mLayoutIdToViewType.put(i7, i2);
                zArr[i6] = true;
                zArr2[i2] = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$initLayoutIdToViewType$0(int i) {
        return this.mItems.getItemView(i).getLayoutId();
    }

    static /* synthetic */ boolean lambda$initLayoutIdToViewType$1(boolean[] zArr, int i) {
        return !zArr[i];
    }

    static /* synthetic */ IllegalStateException lambda$initLayoutIdToViewType$2() {
        return new IllegalStateException("RemoteCollectionItems has more distinct layout ids than its view type count");
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mItems.getItemCount();
    }

    @Override // android.widget.Adapter
    public RemoteViews getItem(int i) {
        return this.mItems.getItemView(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return this.mItems.getItemId(i);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        return this.mLayoutIdToViewType.get(this.mItems.getItemView(i).getLayoutId());
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (i >= getCount()) {
            return null;
        }
        RemoteViews itemView = this.mItems.getItemView(i);
        itemView.addFlags(2);
        AppWidgetHostView.AdapterChildHostView adapterChildHostView = view instanceof AppWidgetHostView.AdapterChildHostView ? (AppWidgetHostView.AdapterChildHostView) view : new AppWidgetHostView.AdapterChildHostView(viewGroup.getContext());
        adapterChildHostView.setInteractionHandler(this.mInteractionHandler);
        adapterChildHostView.setColorResourcesNoReapply(this.mColorResources);
        adapterChildHostView.setOnLightBackground(this.mOnLightBackground);
        adapterChildHostView.updateAppWidget(itemView);
        return adapterChildHostView;
    }
}
