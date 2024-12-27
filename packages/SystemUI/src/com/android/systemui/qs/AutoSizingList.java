package com.android.systemui.qs;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.android.systemui.res.R$styleable;

public class AutoSizingList extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ListAdapter mAdapter;
    public final AutoSizingList$$ExternalSyntheticLambda0 mBindChildren;
    public int mCount;
    public final AnonymousClass1 mDataObserver;
    public final boolean mEnableAutoSizing;
    public final Handler mHandler;
    public final int mItemSize;

    public AutoSizingList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBindChildren = new Runnable() { // from class: com.android.systemui.qs.AutoSizingList$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AutoSizingList autoSizingList = AutoSizingList.this;
                if (autoSizingList.mAdapter == null) {
                    return;
                }
                int i = 0;
                while (i < autoSizingList.mCount) {
                    View childAt = i < autoSizingList.getChildCount() ? autoSizingList.getChildAt(i) : null;
                    View view = autoSizingList.mAdapter.getView(i, childAt, autoSizingList);
                    if (view != childAt) {
                        if (childAt != null) {
                            autoSizingList.removeView(childAt);
                        }
                        autoSizingList.addView(view, i);
                    }
                    i++;
                }
                while (autoSizingList.getChildCount() > autoSizingList.mCount) {
                    autoSizingList.removeViewAt(autoSizingList.getChildCount() - 1);
                }
            }
        };
        this.mDataObserver = new DataSetObserver() { // from class: com.android.systemui.qs.AutoSizingList.1
            @Override // android.database.DataSetObserver
            public final void onChanged() {
                AutoSizingList autoSizingList = AutoSizingList.this;
                int i = AutoSizingList.$r8$clinit;
                ListAdapter listAdapter = autoSizingList.mAdapter;
                autoSizingList.mCount = listAdapter != null ? listAdapter.getCount() : 0;
                AutoSizingList autoSizingList2 = AutoSizingList.this;
                autoSizingList2.mHandler.post(autoSizingList2.mBindChildren);
            }

            @Override // android.database.DataSetObserver
            public final void onInvalidated() {
                AutoSizingList autoSizingList = AutoSizingList.this;
                int i = AutoSizingList.$r8$clinit;
                autoSizingList.mHandler.post(autoSizingList.mBindChildren);
            }
        };
        this.mHandler = new Handler();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AutoSizingList);
        this.mItemSize = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        this.mEnableAutoSizing = obtainStyledAttributes.getBoolean(0, true);
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i2);
        if (size != 0) {
            ListAdapter listAdapter = this.mAdapter;
            int count = listAdapter != null ? listAdapter.getCount() : 0;
            if (this.mEnableAutoSizing) {
                count = Math.min(size / this.mItemSize, count);
            }
            if (this.mCount != count) {
                this.mHandler.post(this.mBindChildren);
                this.mCount = count;
            }
        }
        super.onMeasure(i, i2);
    }

    public final void setAdapter(ListAdapter listAdapter) {
        ListAdapter listAdapter2 = this.mAdapter;
        if (listAdapter2 != null) {
            listAdapter2.unregisterDataSetObserver(this.mDataObserver);
        }
        this.mAdapter = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.mDataObserver);
        }
    }
}
