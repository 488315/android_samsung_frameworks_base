package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes5.dex */
public class ArrayAdapter<T> extends BaseAdapter implements Filterable, ThemedSpinnerAdapter {
    private final Context mContext;
    private LayoutInflater mDropDownInflater;
    private int mDropDownResource;
    private int mFieldId;
    private ArrayAdapter<T>.ArrayFilter mFilter;
    private final LayoutInflater mInflater;
    private final Object mLock;
    private boolean mNotifyOnChange;
    private List<T> mObjects;
    private boolean mObjectsFromResources;
    private ArrayList<T> mOriginalValues;
    private final int mResource;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public ArrayAdapter(Context context, int i) {
        this(context, i, 0, new ArrayList());
    }

    public ArrayAdapter(Context context, int i, int i2) {
        this(context, i, i2, new ArrayList());
    }

    public ArrayAdapter(Context context, int i, T[] tArr) {
        this(context, i, 0, Arrays.asList(tArr));
    }

    public ArrayAdapter(Context context, int i, int i2, T[] tArr) {
        this(context, i, i2, Arrays.asList(tArr));
    }

    public ArrayAdapter(Context context, int i, List<T> list) {
        this(context, i, 0, list);
    }

    public ArrayAdapter(Context context, int i, int i2, List<T> list) {
        this(context, i, i2, list, false);
    }

    private ArrayAdapter(Context context, int i, int i2, List<T> list, boolean z) {
        this.mLock = new Object();
        this.mFieldId = 0;
        this.mNotifyOnChange = true;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDropDownResource = i;
        this.mResource = i;
        this.mObjects = list;
        this.mObjectsFromResources = z;
        this.mFieldId = i2;
    }

    public void add(T t) {
        synchronized (this.mLock) {
            ArrayList<T> arrayList = this.mOriginalValues;
            if (arrayList != null) {
                arrayList.add(t);
            } else {
                this.mObjects.add(t);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void addAll(Collection<? extends T> collection) {
        synchronized (this.mLock) {
            ArrayList<T> arrayList = this.mOriginalValues;
            if (arrayList != null) {
                arrayList.addAll(collection);
            } else {
                this.mObjects.addAll(collection);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void addAll(T... tArr) {
        synchronized (this.mLock) {
            ArrayList<T> arrayList = this.mOriginalValues;
            if (arrayList != null) {
                Collections.addAll(arrayList, tArr);
            } else {
                Collections.addAll(this.mObjects, tArr);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void insert(T t, int i) {
        synchronized (this.mLock) {
            ArrayList<T> arrayList = this.mOriginalValues;
            if (arrayList != null) {
                arrayList.add(i, t);
            } else {
                this.mObjects.add(i, t);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void remove(T t) {
        synchronized (this.mLock) {
            ArrayList<T> arrayList = this.mOriginalValues;
            if (arrayList != null) {
                arrayList.remove(t);
            } else {
                this.mObjects.remove(t);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        synchronized (this.mLock) {
            ArrayList<T> arrayList = this.mOriginalValues;
            if (arrayList != null) {
                arrayList.clear();
            } else {
                this.mObjects.clear();
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void sort(Comparator<? super T> comparator) {
        synchronized (this.mLock) {
            ArrayList<T> arrayList = this.mOriginalValues;
            if (arrayList != null) {
                Collections.sort(arrayList, comparator);
            } else {
                Collections.sort(this.mObjects, comparator);
            }
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.mNotifyOnChange = true;
    }

    public void setNotifyOnChange(boolean z) {
        this.mNotifyOnChange = z;
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mObjects.size();
    }

    @Override // android.widget.Adapter
    public T getItem(int i) {
        return this.mObjects.get(i);
    }

    public int getPosition(T t) {
        return this.mObjects.indexOf(t);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return createViewFromResource(this.mInflater, i, view, viewGroup, this.mResource);
    }

    private View createViewFromResource(LayoutInflater layoutInflater, int i, View view, ViewGroup viewGroup, int i2) {
        TextView textView;
        if (view == null) {
            view = layoutInflater.inflate(i2, viewGroup, false);
        }
        try {
            int i3 = this.mFieldId;
            if (i3 == 0) {
                textView = (TextView) view;
            } else {
                textView = (TextView) view.findViewById(i3);
                if (textView == null) {
                    throw new RuntimeException("Failed to find view with ID " + this.mContext.getResources().getResourceName(this.mFieldId) + " in item layout");
                }
            }
            T item = getItem(i);
            if (item instanceof CharSequence) {
                textView.lambda$setTextAsync$0((CharSequence) item);
                return view;
            }
            if (item != null) {
                textView.lambda$setTextAsync$0(item.toString());
            }
            return view;
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException("ArrayAdapter requires the resource ID to be a TextView", e);
        }
    }

    public void setDropDownViewResource(int i) {
        this.mDropDownResource = i;
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public void setDropDownViewTheme(Resources.Theme theme) {
        if (theme == null) {
            this.mDropDownInflater = null;
        } else if (theme == this.mInflater.getContext().getTheme()) {
            this.mDropDownInflater = this.mInflater;
        } else {
            this.mDropDownInflater = LayoutInflater.from(new ContextThemeWrapper(this.mContext, theme));
        }
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public Resources.Theme getDropDownViewTheme() {
        LayoutInflater layoutInflater = this.mDropDownInflater;
        if (layoutInflater == null) {
            return null;
        }
        return layoutInflater.getContext().getTheme();
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = this.mDropDownInflater;
        if (layoutInflater == null) {
            layoutInflater = this.mInflater;
        }
        return createViewFromResource(layoutInflater, i, view, viewGroup, this.mDropDownResource);
    }

    public static ArrayAdapter<CharSequence> createFromResource(Context context, int i, int i2) {
        return new ArrayAdapter<>(context, i2, 0, Arrays.asList(context.getResources().getTextArray(i)), true);
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        if (this.mFilter == null) {
            this.mFilter = new ArrayFilter();
        }
        return this.mFilter;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public CharSequence[] getAutofillOptions() {
        List<T> list;
        CharSequence[] autofillOptions = super.getAutofillOptions();
        if (autofillOptions != null) {
            return autofillOptions;
        }
        if (!this.mObjectsFromResources || (list = this.mObjects) == null || list.isEmpty()) {
            return null;
        }
        CharSequence[] charSequenceArr = new CharSequence[this.mObjects.size()];
        this.mObjects.toArray(charSequenceArr);
        return charSequenceArr;
    }

    private class ArrayFilter extends Filter {
        private ArrayFilter() {
        }

        @Override // android.widget.Filter
        protected Filter.FilterResults performFiltering(CharSequence charSequence) {
            ArrayList arrayList;
            ArrayList arrayList2;
            Filter.FilterResults filterResults = new Filter.FilterResults();
            if (ArrayAdapter.this.mOriginalValues == null) {
                synchronized (ArrayAdapter.this.mLock) {
                    ArrayAdapter.this.mOriginalValues = new ArrayList(ArrayAdapter.this.mObjects);
                }
            }
            if (charSequence == null || charSequence.length() == 0) {
                synchronized (ArrayAdapter.this.mLock) {
                    arrayList = new ArrayList(ArrayAdapter.this.mOriginalValues);
                }
                filterResults.values = arrayList;
                filterResults.count = arrayList.size();
                return filterResults;
            }
            String lowerCase = charSequence.toString().toLowerCase();
            synchronized (ArrayAdapter.this.mLock) {
                arrayList2 = new ArrayList(ArrayAdapter.this.mOriginalValues);
            }
            int size = arrayList2.size();
            ArrayList arrayList3 = new ArrayList();
            for (int i = 0; i < size; i++) {
                Object obj = arrayList2.get(i);
                String lowerCase2 = obj.toString().toLowerCase();
                if (lowerCase2.startsWith(lowerCase)) {
                    arrayList3.add(obj);
                } else {
                    String[] split = lowerCase2.split(" ");
                    int length = split.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        if (split[i2].startsWith(lowerCase)) {
                            arrayList3.add(obj);
                            break;
                        }
                        i2++;
                    }
                }
            }
            filterResults.values = arrayList3;
            filterResults.count = arrayList3.size();
            return filterResults;
        }

        @Override // android.widget.Filter
        protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            ArrayAdapter.this.mObjects = (List) filterResults.values;
            if (filterResults.count > 0) {
                ArrayAdapter.this.notifyDataSetChanged();
            } else {
                ArrayAdapter.this.notifyDataSetInvalidated();
            }
        }
    }
}
