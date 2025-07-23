package android.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes5.dex */
public abstract class BaseAdapter implements ListAdapter, SpinnerAdapter {
    private CharSequence[] mAutofillOptions;
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    @Override // android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override // android.widget.Adapter
    public int getItemViewType(int i) {
        return 0;
    }

    @Override // android.widget.Adapter
    public int getViewTypeCount() {
        return 1;
    }

    @Override // android.widget.Adapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return true;
    }

    @Override // android.widget.Adapter
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.registerObserver(dataSetObserver);
    }

    @Override // android.widget.Adapter
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.unregisterObserver(dataSetObserver);
    }

    public void notifyDataSetChanged() {
        this.mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        this.mDataSetObservable.notifyInvalidated();
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup);
    }

    @Override // android.widget.Adapter
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override // android.widget.Adapter
    public CharSequence[] getAutofillOptions() {
        return this.mAutofillOptions;
    }

    public void setAutofillOptions(CharSequence... charSequenceArr) {
        this.mAutofillOptions = charSequenceArr;
    }
}
