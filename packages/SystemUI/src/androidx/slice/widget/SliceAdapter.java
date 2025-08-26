package androidx.slice.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.ArrayUtils;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceQuery;
import com.android.systemui.R;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda5;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class SliceAdapter extends RecyclerView.Adapter {
    public boolean mAllowTwoLines;
    public final Context mContext;
    public int mInsetBottom;
    public int mInsetEnd;
    public int mInsetStart;
    public int mInsetTop;
    public long mLastUpdated;
    public SliceView mParent;
    public SliceViewPolicy mPolicy;
    public boolean mShowLastUpdated;
    public List mSliceActions;
    public VolumePanelDialog$$ExternalSyntheticLambda5 mSliceObserver;
    public SliceStyle mSliceStyle;
    public TemplateView mTemplateView;
    public final IdGenerator mIdGen = new IdGenerator();
    public List mSlices = new ArrayList();
    public Set mLoadingActions = new HashSet();

    public class IdGenerator {
        public long mNextLong = 0;
        public final ArrayMap mCurrentIds = new ArrayMap();
        public final ArrayMap mUsedIds = new ArrayMap();
    }

    public class SliceViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, View.OnClickListener {
        public final SliceChildView mSliceChildView;

        public SliceViewHolder(View view) {
            super(view);
            this.mSliceChildView = view instanceof SliceChildView ? (SliceChildView) view : null;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            SliceView sliceView = SliceAdapter.this.mParent;
            if (sliceView != null) {
                sliceView.mClickInfo = (int[]) view.getTag();
                SliceAdapter.this.mParent.performClick();
            }
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            ListContent listContent;
            TemplateView templateView = SliceAdapter.this.mTemplateView;
            if (templateView != null) {
                SliceView sliceView = templateView.mParent;
                if (sliceView != null && sliceView.mOnClickListener == null && ((listContent = sliceView.mListContent) == null || listContent.getShortcut(sliceView.getContext()) == null)) {
                    templateView.mForeground.setPressed(false);
                } else {
                    templateView.mForeground.getLocationOnScreen(templateView.mLoc);
                    templateView.mForeground.getBackground().setHotspot((int) (motionEvent.getRawX() - templateView.mLoc[0]), (int) (motionEvent.getRawY() - templateView.mLoc[1]));
                    int actionMasked = motionEvent.getActionMasked();
                    if (actionMasked == 0) {
                        templateView.mForeground.setPressed(true);
                    } else if (actionMasked == 3 || actionMasked == 1 || actionMasked == 2) {
                        templateView.mForeground.setPressed(false);
                    }
                }
            }
            return false;
        }
    }

    public class SliceWrapper {
        public final long mId;
        public final SliceContent mItem;
        public final int mType;

        public SliceWrapper(SliceContent sliceContent, IdGenerator idGenerator, int i) {
            this.mItem = sliceContent;
            SliceItem sliceItem = sliceContent.mSliceItem;
            this.mType = "message".equals(sliceItem.mSubType) ? SliceQuery.findSubtype(sliceItem, (String) null, "source") != null ? 4 : 5 : ArrayUtils.contains(sliceItem.mHints, "horizontal") ? 3 : !ArrayUtils.contains(sliceItem.mHints, "list_item") ? 2 : 1;
            SliceItem sliceItem2 = sliceContent.mSliceItem;
            idGenerator.getClass();
            String strValueOf = ("slice".equals(sliceItem2.mFormat) || "action".equals(sliceItem2.mFormat)) ? String.valueOf(Arrays.asList(sliceItem2.getSlice().mItems).size()) : sliceItem2.toString("");
            ArrayMap arrayMap = idGenerator.mCurrentIds;
            if (!arrayMap.containsKey(strValueOf)) {
                long j = idGenerator.mNextLong;
                idGenerator.mNextLong = 1 + j;
                arrayMap.put(strValueOf, Long.valueOf(j));
            }
            long jLongValue = ((Long) arrayMap.get(strValueOf)).longValue();
            ArrayMap arrayMap2 = idGenerator.mUsedIds;
            Integer num = (Integer) arrayMap2.get(strValueOf);
            arrayMap2.put(strValueOf, Integer.valueOf((num != null ? num.intValue() : 0) + 1));
            this.mId = jLongValue + (r7 * 10000);
        }
    }

    public SliceAdapter(Context context) {
        this.mContext = context;
        setHasStableIds(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mSlices.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return ((SliceWrapper) this.mSlices.get(i)).mId;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return ((SliceWrapper) this.mSlices.get(i)).mType;
    }

    public final void notifyHeaderChanged() {
        if (this.mSlices.size() > 0) {
            notifyItemChanged(0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        SliceViewHolder sliceViewHolder = (SliceViewHolder) viewHolder;
        SliceContent sliceContent = ((SliceWrapper) ((ArrayList) this.mSlices).get(i)).mItem;
        if (sliceViewHolder.mSliceChildView == null || sliceContent == null) {
            return;
        }
        RowStyle rowStyle = SliceAdapter.this.mSliceStyle.getRowStyle();
        sliceViewHolder.mSliceChildView.setOnClickListener(sliceViewHolder);
        sliceViewHolder.mSliceChildView.setOnTouchListener(sliceViewHolder);
        SliceChildView sliceChildView = sliceViewHolder.mSliceChildView;
        SliceAdapter sliceAdapter = SliceAdapter.this;
        sliceChildView.mLoadingListener = sliceAdapter;
        boolean z = sliceContent instanceof RowContent ? ((RowContent) sliceContent).mIsHeader : i == 0;
        sliceChildView.setLoadingActions(sliceAdapter.mLoadingActions);
        sliceViewHolder.mSliceChildView.setPolicy(SliceAdapter.this.mPolicy);
        SliceChildView sliceChildView2 = sliceViewHolder.mSliceChildView;
        Integer num = rowStyle.mTintColor;
        sliceChildView2.setTint(num != null ? num.intValue() : rowStyle.mSliceStyle.mTintColor);
        sliceViewHolder.mSliceChildView.setStyle(SliceAdapter.this.mSliceStyle, rowStyle);
        sliceViewHolder.mSliceChildView.setShowLastUpdated(z && SliceAdapter.this.mShowLastUpdated);
        sliceViewHolder.mSliceChildView.setLastUpdated(z ? SliceAdapter.this.mLastUpdated : -1L);
        int i2 = i == 0 ? SliceAdapter.this.mInsetTop : 0;
        int i3 = i == ((ArrayList) SliceAdapter.this.mSlices).size() - 1 ? SliceAdapter.this.mInsetBottom : 0;
        SliceChildView sliceChildView3 = sliceViewHolder.mSliceChildView;
        SliceAdapter sliceAdapter2 = SliceAdapter.this;
        sliceChildView3.setInsets(sliceAdapter2.mInsetStart, i2, sliceAdapter2.mInsetEnd, i3);
        sliceViewHolder.mSliceChildView.setAllowTwoLines(SliceAdapter.this.mAllowTwoLines);
        sliceViewHolder.mSliceChildView.setSliceActions(z ? SliceAdapter.this.mSliceActions : null);
        boolean z2 = z;
        sliceViewHolder.mSliceChildView.setSliceItem(sliceContent, z2, i, ((ArrayList) SliceAdapter.this.mSlices).size(), SliceAdapter.this.mSliceObserver);
        sliceViewHolder.mSliceChildView.setTag(new int[]{ListContent.getRowType(sliceContent, z2, SliceAdapter.this.mSliceActions), i});
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View gridRowView;
        if (i != 3) {
            gridRowView = i != 4 ? i != 5 ? new RowView(this.mContext) : LayoutInflater.from(this.mContext).inflate(R.layout.abc_slice_message_local, (ViewGroup) null) : LayoutInflater.from(this.mContext).inflate(R.layout.abc_slice_message, (ViewGroup) null);
        } else {
            View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.abc_slice_grid, (ViewGroup) null);
            gridRowView = viewInflate instanceof GridRowView ? (GridRowView) viewInflate : new GridRowView(this.mContext, null);
        }
        gridRowView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        return new SliceViewHolder(gridRowView);
    }

    public final void onSliceActionLoading(SliceItem sliceItem, int i) {
        this.mLoadingActions.add(sliceItem);
        if (((ArrayList) this.mSlices).size() > i) {
            notifyItemChanged(i);
        } else {
            notifyDataSetChanged();
        }
    }

    public final void setSliceItems(int i, List list) {
        if (list == null) {
            this.mLoadingActions.clear();
            ((ArrayList) this.mSlices).clear();
        } else {
            IdGenerator idGenerator = this.mIdGen;
            idGenerator.mUsedIds.clear();
            this.mSlices = new ArrayList(list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                SliceContent sliceContent = (SliceContent) it.next();
                ((ArrayList) this.mSlices).add(new SliceWrapper(sliceContent, idGenerator, i));
            }
        }
        notifyDataSetChanged();
    }
}
