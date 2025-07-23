package android.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;

@Deprecated
/* loaded from: classes.dex */
public class ListFragment extends Fragment {
    ListAdapter mAdapter;
    CharSequence mEmptyText;
    View mEmptyView;
    ListView mList;
    View mListContainer;
    boolean mListShown;
    View mProgressContainer;
    TextView mStandardEmptyView;
    private final Handler mHandler = new Handler();
    private final Runnable mRequestFocus = new Runnable() { // from class: android.app.ListFragment.1
        @Override // java.lang.Runnable
        public void run() {
            ListFragment.this.mList.focusableViewAvailable(ListFragment.this.mList);
        }
    };
    private final AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() { // from class: android.app.ListFragment.2
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            ListFragment.this.onListItemClick((ListView) adapterView, view, i, j);
        }
    };

    public void onListItemClick(ListView listView, View view, int i, long j) {
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(17367060, viewGroup, false);
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ensureList();
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mList = null;
        this.mListShown = false;
        this.mListContainer = null;
        this.mProgressContainer = null;
        this.mEmptyView = null;
        this.mStandardEmptyView = null;
        super.onDestroyView();
    }

    public void setListAdapter(ListAdapter listAdapter) {
        boolean z = this.mAdapter != null;
        this.mAdapter = listAdapter;
        ListView listView = this.mList;
        if (listView != null) {
            listView.setAdapter(listAdapter);
            if (this.mListShown || z) {
                return;
            }
            setListShown(true, getView().getWindowToken() != null);
        }
    }

    public void setSelection(int i) {
        ensureList();
        this.mList.setSelection(i);
    }

    public int getSelectedItemPosition() {
        ensureList();
        return this.mList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        ensureList();
        return this.mList.getSelectedItemId();
    }

    public ListView getListView() {
        ensureList();
        return this.mList;
    }

    public void setEmptyText(CharSequence charSequence) {
        ensureList();
        TextView textView = this.mStandardEmptyView;
        if (textView == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        textView.lambda$setTextAsync$0(charSequence);
        if (this.mEmptyText == null) {
            this.mList.setEmptyView(this.mStandardEmptyView);
        }
        this.mEmptyText = charSequence;
    }

    public void setListShown(boolean z) {
        setListShown(z, true);
    }

    public void setListShownNoAnimation(boolean z) {
        setListShown(z, false);
    }

    private void setListShown(boolean z, boolean z2) {
        ensureList();
        View view = this.mProgressContainer;
        if (view == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        if (this.mListShown == z) {
            return;
        }
        this.mListShown = z;
        if (z) {
            if (z2) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
                this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
            } else {
                view.clearAnimation();
                this.mListContainer.clearAnimation();
            }
            this.mProgressContainer.setVisibility(8);
            this.mListContainer.setVisibility(0);
            return;
        }
        if (z2) {
            view.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
            this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
        } else {
            view.clearAnimation();
            this.mListContainer.clearAnimation();
        }
        this.mProgressContainer.setVisibility(0);
        this.mListContainer.setVisibility(8);
    }

    public ListAdapter getListAdapter() {
        return this.mAdapter;
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        View view = getView();
        if (view == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        if (view instanceof ListView) {
            this.mList = (ListView) view;
        } else {
            TextView textView = (TextView) view.findViewById(R.id.internalEmpty);
            this.mStandardEmptyView = textView;
            if (textView == null) {
                this.mEmptyView = view.findViewById(16908292);
            } else {
                textView.setVisibility(8);
            }
            this.mProgressContainer = view.findViewById(R.id.progressContainer);
            this.mListContainer = view.findViewById(R.id.listContainer);
            View findViewById = view.findViewById(16908298);
            if (!(findViewById instanceof ListView)) {
                throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
            }
            ListView listView = (ListView) findViewById;
            this.mList = listView;
            if (listView == null) {
                throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
            }
            View view2 = this.mEmptyView;
            if (view2 != null) {
                listView.setEmptyView(view2);
            } else {
                CharSequence charSequence = this.mEmptyText;
                if (charSequence != null) {
                    this.mStandardEmptyView.lambda$setTextAsync$0(charSequence);
                    this.mList.setEmptyView(this.mStandardEmptyView);
                }
            }
        }
        this.mListShown = true;
        this.mList.setOnItemClickListener(this.mOnClickListener);
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            this.mAdapter = null;
            setListAdapter(listAdapter);
        } else if (this.mProgressContainer != null) {
            setListShown(false, false);
        }
        this.mHandler.post(this.mRequestFocus);
    }
}
