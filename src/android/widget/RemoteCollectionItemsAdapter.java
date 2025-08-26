package android.widget;

import android.appwidget.AppWidgetHostView;
import android.content.res.Resources;
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

    RemoteCollectionItemsAdapter(RemoteViews.RemoteCollectionItems remoteCollectionItems, RemoteViews.InteractionHandler interactionHandler, RemoteViews.ColorResources colorResources, boolean z) throws Throwable {
        this.mViewTypeCount = remoteCollectionItems.getViewTypeCount();
        this.mItems = remoteCollectionItems;
        this.mInteractionHandler = interactionHandler;
        this.mColorResources = colorResources;
        this.mOnLightBackground = z;
        initLayoutIdToViewType();
    }

    void setData(RemoteViews.RemoteCollectionItems remoteCollectionItems, RemoteViews.InteractionHandler interactionHandler, RemoteViews.ColorResources colorResources, boolean z) throws Throwable {
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

    private void initLayoutIdToViewType() throws Throwable {
        SparseIntArray sparseIntArray = this.mLayoutIdToViewType;
        this.mLayoutIdToViewType = new SparseIntArray(this.mViewTypeCount);
        int[] array = IntStream.range(0, this.mItems.getItemCount()).map(new IntUnaryOperator() { // from class: android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                return this.f$0.lambda$initLayoutIdToViewType$0(i);
            }
        }).distinct().toArray();
        int length = array.length;
        int i = this.mViewTypeCount;
        if (length > i) {
            throw new IllegalArgumentException("Collection items uses " + array.length + " distinct layouts, which is more than view type count of " + this.mViewTypeCount);
        }
        boolean[] zArr = new boolean[array.length];
        final boolean[] zArr2 = new boolean[i];
        int iOrElseThrow = -1;
        if (sparseIntArray != null) {
            for (int i2 = 0; i2 < array.length; i2++) {
                int i3 = array[i2];
                int i4 = sparseIntArray.get(i3, -1);
                if (i4 >= 0) {
                    this.mLayoutIdToViewType.put(i3, i4);
                    zArr[i2] = true;
                    zArr2[i4] = true;
                }
            }
        }
        for (int i5 = 0; i5 < array.length; i5++) {
            if (!zArr[i5]) {
                int i6 = array[i5];
                iOrElseThrow = IntStream.range(iOrElseThrow + 1, array.length).filter(new IntPredicate() { // from class: android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i7) {
                        return RemoteCollectionItemsAdapter.lambda$initLayoutIdToViewType$1(zArr2, i7);
                    }
                }).findFirst().orElseThrow(new Supplier() { // from class: android.widget.RemoteCollectionItemsAdapter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return RemoteCollectionItemsAdapter.lambda$initLayoutIdToViewType$2();
                    }
                });
                this.mLayoutIdToViewType.put(i6, iOrElseThrow);
                zArr[i5] = true;
                zArr2[iOrElseThrow] = true;
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
    public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
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
