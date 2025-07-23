package android.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorFilter;

/* loaded from: classes5.dex */
public abstract class CursorTreeAdapter extends BaseExpandableListAdapter implements Filterable, CursorFilter.CursorFilterClient {
    private boolean mAutoRequery;
    SparseArray<MyCursorHelper> mChildrenCursorHelpers;
    private Context mContext;
    CursorFilter mCursorFilter;
    FilterQueryProvider mFilterQueryProvider;
    MyCursorHelper mGroupCursorHelper;
    private Handler mHandler;

    protected abstract void bindChildView(View view, Context context, Cursor cursor, boolean z);

    protected abstract void bindGroupView(View view, Context context, Cursor cursor, boolean z);

    protected abstract Cursor getChildrenCursor(Cursor cursor);

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    protected abstract View newChildView(Context context, Cursor cursor, boolean z, ViewGroup viewGroup);

    protected abstract View newGroupView(Context context, Cursor cursor, boolean z, ViewGroup viewGroup);

    public CursorTreeAdapter(Cursor cursor, Context context) {
        init(cursor, context, true);
    }

    public CursorTreeAdapter(Cursor cursor, Context context, boolean z) {
        init(cursor, context, z);
    }

    private void init(Cursor cursor, Context context, boolean z) {
        this.mContext = context;
        this.mHandler = new Handler();
        this.mAutoRequery = z;
        this.mGroupCursorHelper = new MyCursorHelper(cursor);
        this.mChildrenCursorHelpers = new SparseArray<>();
    }

    synchronized MyCursorHelper getChildrenCursorHelper(int i, boolean z) {
        MyCursorHelper myCursorHelper = this.mChildrenCursorHelpers.get(i);
        if (myCursorHelper == null) {
            if (this.mGroupCursorHelper.moveTo(i) == null) {
                return null;
            }
            MyCursorHelper myCursorHelper2 = new MyCursorHelper(getChildrenCursor(this.mGroupCursorHelper.getCursor()));
            this.mChildrenCursorHelpers.put(i, myCursorHelper2);
            myCursorHelper = myCursorHelper2;
        }
        return myCursorHelper;
    }

    public void setGroupCursor(Cursor cursor) {
        this.mGroupCursorHelper.changeCursor(cursor, false);
    }

    public void setChildrenCursor(int i, Cursor cursor) {
        getChildrenCursorHelper(i, false).changeCursor(cursor, false);
    }

    @Override // android.widget.ExpandableListAdapter
    public Cursor getChild(int i, int i2) {
        return getChildrenCursorHelper(i, true).moveTo(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int i, int i2) {
        return getChildrenCursorHelper(i, true).getId(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int i) {
        MyCursorHelper childrenCursorHelper = getChildrenCursorHelper(i, true);
        if (!this.mGroupCursorHelper.isValid() || childrenCursorHelper == null) {
            return 0;
        }
        return childrenCursorHelper.getCount();
    }

    @Override // android.widget.ExpandableListAdapter
    public Cursor getGroup(int i) {
        return this.mGroupCursorHelper.moveTo(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.mGroupCursorHelper.getCount();
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int i) {
        return this.mGroupCursorHelper.getId(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        Cursor moveTo = this.mGroupCursorHelper.moveTo(i);
        if (moveTo == null) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (view == null) {
            view = newGroupView(this.mContext, moveTo, z, viewGroup);
        }
        bindGroupView(view, this.mContext, moveTo, z);
        return view;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        Cursor moveTo = getChildrenCursorHelper(i, true).moveTo(i2);
        if (moveTo == null) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (view == null) {
            view = newChildView(this.mContext, moveTo, z, viewGroup);
        }
        bindChildView(view, this.mContext, moveTo, z);
        return view;
    }

    private synchronized void releaseCursorHelpers() {
        for (int size = this.mChildrenCursorHelpers.size() - 1; size >= 0; size--) {
            this.mChildrenCursorHelpers.valueAt(size).deactivate();
        }
        this.mChildrenCursorHelpers.clear();
    }

    @Override // android.widget.BaseExpandableListAdapter
    public void notifyDataSetChanged() {
        notifyDataSetChanged(true);
    }

    public void notifyDataSetChanged(boolean z) {
        if (z) {
            releaseCursorHelpers();
        }
        super.notifyDataSetChanged();
    }

    @Override // android.widget.BaseExpandableListAdapter
    public void notifyDataSetInvalidated() {
        releaseCursorHelpers();
        super.notifyDataSetInvalidated();
    }

    @Override // android.widget.BaseExpandableListAdapter, android.widget.ExpandableListAdapter
    public void onGroupCollapsed(int i) {
        deactivateChildrenCursorHelper(i);
    }

    synchronized void deactivateChildrenCursorHelper(int i) {
        MyCursorHelper childrenCursorHelper = getChildrenCursorHelper(i, true);
        this.mChildrenCursorHelpers.remove(i);
        childrenCursorHelper.deactivate();
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public String convertToString(Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        FilterQueryProvider filterQueryProvider = this.mFilterQueryProvider;
        if (filterQueryProvider != null) {
            return filterQueryProvider.runQuery(charSequence);
        }
        return this.mGroupCursorHelper.getCursor();
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        if (this.mCursorFilter == null) {
            this.mCursorFilter = new CursorFilter(this);
        }
        return this.mCursorFilter;
    }

    public FilterQueryProvider getFilterQueryProvider() {
        return this.mFilterQueryProvider;
    }

    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        this.mFilterQueryProvider = filterQueryProvider;
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public void changeCursor(Cursor cursor) {
        this.mGroupCursorHelper.changeCursor(cursor, true);
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public Cursor getCursor() {
        return this.mGroupCursorHelper.getCursor();
    }

    class MyCursorHelper {
        private MyContentObserver mContentObserver;
        private Cursor mCursor;
        private MyDataSetObserver mDataSetObserver;
        private boolean mDataValid;
        private int mRowIDColumn;

        MyCursorHelper(Cursor cursor) {
            boolean z = cursor != null;
            this.mCursor = cursor;
            this.mDataValid = z;
            this.mRowIDColumn = z ? cursor.getColumnIndex("_id") : -1;
            this.mContentObserver = new MyContentObserver();
            this.mDataSetObserver = new MyDataSetObserver();
            if (z) {
                cursor.registerContentObserver(this.mContentObserver);
                cursor.registerDataSetObserver(this.mDataSetObserver);
            }
        }

        Cursor getCursor() {
            return this.mCursor;
        }

        int getCount() {
            Cursor cursor;
            if (!this.mDataValid || (cursor = this.mCursor) == null) {
                return 0;
            }
            return cursor.getCount();
        }

        long getId(int i) {
            Cursor cursor;
            if (this.mDataValid && (cursor = this.mCursor) != null && cursor.moveToPosition(i)) {
                return this.mCursor.getLong(this.mRowIDColumn);
            }
            return 0L;
        }

        Cursor moveTo(int i) {
            Cursor cursor;
            if (this.mDataValid && (cursor = this.mCursor) != null && cursor.moveToPosition(i)) {
                return this.mCursor;
            }
            return null;
        }

        void changeCursor(Cursor cursor, boolean z) {
            if (cursor == this.mCursor) {
                return;
            }
            deactivate();
            this.mCursor = cursor;
            if (cursor != null) {
                cursor.registerContentObserver(this.mContentObserver);
                cursor.registerDataSetObserver(this.mDataSetObserver);
                this.mRowIDColumn = cursor.getColumnIndex("_id");
                this.mDataValid = true;
                CursorTreeAdapter.this.notifyDataSetChanged(z);
                return;
            }
            this.mRowIDColumn = -1;
            this.mDataValid = false;
            CursorTreeAdapter.this.notifyDataSetInvalidated();
        }

        void deactivate() {
            Cursor cursor = this.mCursor;
            if (cursor == null) {
                return;
            }
            cursor.unregisterContentObserver(this.mContentObserver);
            this.mCursor.unregisterDataSetObserver(this.mDataSetObserver);
            this.mCursor.close();
            this.mCursor = null;
        }

        boolean isValid() {
            return this.mDataValid && this.mCursor != null;
        }

        private class MyContentObserver extends ContentObserver {
            @Override // android.database.ContentObserver
            public boolean deliverSelfNotifications() {
                return true;
            }

            public MyContentObserver() {
                super(CursorTreeAdapter.this.mHandler);
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (!CursorTreeAdapter.this.mAutoRequery || MyCursorHelper.this.mCursor == null || MyCursorHelper.this.mCursor.isClosed()) {
                    return;
                }
                MyCursorHelper myCursorHelper = MyCursorHelper.this;
                myCursorHelper.mDataValid = myCursorHelper.mCursor.requery();
            }
        }

        private class MyDataSetObserver extends DataSetObserver {
            private MyDataSetObserver() {
            }

            @Override // android.database.DataSetObserver
            public void onChanged() {
                MyCursorHelper.this.mDataValid = true;
                CursorTreeAdapter.this.notifyDataSetChanged();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                MyCursorHelper.this.mDataValid = false;
                CursorTreeAdapter.this.notifyDataSetInvalidated();
            }
        }
    }
}
