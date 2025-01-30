package androidx.preference;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.appcompat.util.SeslSubheaderRoundedCorner;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class PreferenceFragmentCompat extends Fragment implements PreferenceManager.OnPreferenceTreeClickListener, PreferenceManager.OnDisplayPreferenceDialogListener, PreferenceManager.OnNavigateToScreenListener, DialogPreference.TargetFragment {
    public boolean mHavePrefs;
    public boolean mInitDone;
    public int mIsLargeLayout;
    public boolean mIsReducedMargin;
    public RecyclerView mList;
    public SeslRoundedCorner mListRoundedCorner;
    public ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;
    public PreferenceManager mPreferenceManager;
    public SeslRoundedCorner mRoundedCorner;
    public int mScreenWidthDp;
    public int mSubheaderColor;
    public SeslSubheaderRoundedCorner mSubheaderRoundedCorner;
    public final DividerDecoration mDividerDecoration = new DividerDecoration();
    public int mLayoutResId = R.layout.preference_list_fragment;
    public final boolean mIsRoundedCorner = true;
    public final HandlerC04111 mHandler = new Handler() { // from class: androidx.preference.PreferenceFragmentCompat.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            PreferenceFragmentCompat preferenceFragmentCompat = PreferenceFragmentCompat.this;
            PreferenceScreen preferenceScreen = preferenceFragmentCompat.mPreferenceManager.mPreferenceScreen;
            if (preferenceScreen != null) {
                preferenceFragmentCompat.mList.setAdapter(new PreferenceGroupAdapter(preferenceScreen));
                preferenceScreen.onAttached();
            }
        }
    };
    public final RunnableC04122 mRequestFocus = new Runnable() { // from class: androidx.preference.PreferenceFragmentCompat.2
        @Override // java.lang.Runnable
        public final void run() {
            RecyclerView recyclerView = PreferenceFragmentCompat.this.mList;
            recyclerView.focusableViewAvailable(recyclerView);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.preference.PreferenceFragmentCompat$4 */
    public final class ViewTreeObserverOnPreDrawListenerC04144 implements ViewTreeObserver.OnPreDrawListener {
        public ViewTreeObserverOnPreDrawListenerC04144() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            PreferenceFragmentCompat preferenceFragmentCompat = PreferenceFragmentCompat.this;
            RecyclerView recyclerView = preferenceFragmentCompat.mList;
            if (recyclerView != null) {
                RecyclerView.Adapter adapter = recyclerView.mAdapter;
                Configuration configuration = preferenceFragmentCompat.getResources().getConfiguration();
                int i = configuration.screenWidthDp;
                boolean z = true;
                int i2 = ((i > 320 || configuration.fontScale < 1.1f) && (i >= 411 || configuration.fontScale < 1.3f)) ? 2 : 1;
                if (adapter instanceof PreferenceGroupAdapter) {
                    PreferenceFragmentCompat preferenceFragmentCompat2 = PreferenceFragmentCompat.this;
                    PreferenceGroupAdapter preferenceGroupAdapter = (PreferenceGroupAdapter) adapter;
                    if (i2 == preferenceFragmentCompat2.mIsLargeLayout && (i2 != 1 || (preferenceFragmentCompat2.mScreenWidthDp == i && preferenceGroupAdapter.mParentWidth != 0))) {
                        z = false;
                    }
                    if (z) {
                        preferenceFragmentCompat2.mIsLargeLayout = i2;
                        for (int i3 = 0; i3 < preferenceGroupAdapter.getItemCount(); i3++) {
                            Preference item = preferenceGroupAdapter.getItem(i3);
                            if (item != null && PreferenceGroupAdapter.isSwitchLayout(item) && (item instanceof SwitchPreferenceCompat)) {
                                adapter.notifyItemChanged(i3);
                            }
                        }
                    }
                }
                PreferenceFragmentCompat preferenceFragmentCompat3 = PreferenceFragmentCompat.this;
                preferenceFragmentCompat3.mScreenWidthDp = configuration.screenWidthDp;
                preferenceFragmentCompat3.mList.getViewTreeObserver().removeOnPreDrawListener(this);
                PreferenceFragmentCompat.this.mOnPreDrawListener = null;
            }
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DividerDecoration extends RecyclerView.ItemDecoration {
        public boolean mAllowDividerAfterLastItem = true;
        public Drawable mDivider;
        public int mDividerHeight;

        public DividerDecoration() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void seslOnDispatchDraw(Canvas canvas, RecyclerView recyclerView) {
            PreferenceFragmentCompat preferenceFragmentCompat;
            PreferenceViewHolder preferenceViewHolder;
            int i;
            boolean z;
            int childCount = recyclerView.getChildCount();
            int width = recyclerView.getWidth();
            int i2 = 0;
            while (true) {
                preferenceFragmentCompat = PreferenceFragmentCompat.this;
                if (i2 >= childCount) {
                    break;
                }
                View childAt = recyclerView.getChildAt(i2);
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childAt);
                if (childViewHolder instanceof PreferenceViewHolder) {
                    preferenceViewHolder = (PreferenceViewHolder) childViewHolder;
                    i = preferenceViewHolder.mDividerStartOffset;
                } else {
                    preferenceViewHolder = null;
                    i = 0;
                }
                boolean z2 = preferenceFragmentCompat.getResources().getConfiguration().getLayoutDirection() == 1;
                int height = childAt.getHeight() + ((int) childAt.getY());
                if (this.mDivider != null) {
                    RecyclerView.ViewHolder childViewHolder2 = recyclerView.getChildViewHolder(childAt);
                    if ((childViewHolder2 instanceof PreferenceViewHolder) && ((PreferenceViewHolder) childViewHolder2).mDividerAllowedBelow) {
                        z = this.mAllowDividerAfterLastItem;
                        int indexOfChild = recyclerView.indexOfChild(childAt);
                        if (indexOfChild < recyclerView.getChildCount() - 1) {
                            RecyclerView.ViewHolder childViewHolder3 = recyclerView.getChildViewHolder(recyclerView.getChildAt(indexOfChild + 1));
                            z = (childViewHolder3 instanceof PreferenceViewHolder) && ((PreferenceViewHolder) childViewHolder3).mDividerAllowedAbove;
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        if (z2) {
                            this.mDivider.setBounds(0, height, width - i, this.mDividerHeight + height);
                        } else {
                            this.mDivider.setBounds(i, height, width, this.mDividerHeight + height);
                        }
                        this.mDivider.draw(canvas);
                    }
                }
                if (preferenceFragmentCompat.mIsRoundedCorner && preferenceViewHolder != null && preferenceViewHolder.mDrawBackground) {
                    if (preferenceViewHolder.mSubheaderRound) {
                        preferenceFragmentCompat.mSubheaderRoundedCorner.setRoundedCorners(preferenceViewHolder.mDrawCorners);
                        preferenceFragmentCompat.mSubheaderRoundedCorner.drawRoundedCorner(childAt, canvas);
                    } else {
                        preferenceFragmentCompat.mRoundedCorner.setRoundedCorners(preferenceViewHolder.mDrawCorners);
                        preferenceFragmentCompat.mRoundedCorner.drawRoundedCorner(childAt, canvas);
                    }
                }
                i2++;
            }
            if (preferenceFragmentCompat.mIsRoundedCorner) {
                SeslRoundedCorner seslRoundedCorner = preferenceFragmentCompat.mListRoundedCorner;
                canvas.getClipBounds(seslRoundedCorner.mRoundedCornerBounds);
                seslRoundedCorner.drawRoundedCornerInternal(canvas);
            }
        }
    }

    @Override // androidx.preference.DialogPreference.TargetFragment
    public final Preference findPreference(CharSequence charSequence) {
        PreferenceScreen preferenceScreen;
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null || (preferenceScreen = preferenceManager.mPreferenceScreen) == null) {
            return null;
        }
        return preferenceScreen.findPreference(charSequence);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        RecyclerView recyclerView = this.mList;
        if (recyclerView != null) {
            if (this.mOnPreDrawListener == null) {
                ViewTreeObserver viewTreeObserver = recyclerView.getViewTreeObserver();
                if (this.mList != null) {
                    this.mOnPreDrawListener = new ViewTreeObserverOnPreDrawListenerC04144();
                }
                viewTreeObserver.addOnPreDrawListener(this.mOnPreDrawListener);
            }
            RecyclerView recyclerView2 = this.mList;
            RecyclerView.Adapter adapter = recyclerView2.mAdapter;
            RecyclerView.LayoutManager layoutManager$1 = recyclerView2.getLayoutManager$1();
            boolean z = configuration.screenWidthDp <= 250;
            if (z != this.mIsReducedMargin && (adapter instanceof PreferenceGroupAdapter) && layoutManager$1 != null) {
                this.mIsReducedMargin = z;
                TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, R$styleable.PreferenceFragmentCompat, R.attr.preferenceFragmentCompatStyle, 0);
                try {
                    Drawable drawable = obtainStyledAttributes.getDrawable(1);
                    DividerDecoration dividerDecoration = this.mDividerDecoration;
                    if (drawable != null) {
                        dividerDecoration.getClass();
                        dividerDecoration.mDividerHeight = drawable.getIntrinsicHeight();
                    } else {
                        dividerDecoration.mDividerHeight = 0;
                    }
                    dividerDecoration.mDivider = drawable;
                    PreferenceFragmentCompat.this.mList.invalidateItemDecorations();
                    Parcelable onSaveInstanceState = layoutManager$1.onSaveInstanceState();
                    RecyclerView recyclerView3 = this.mList;
                    recyclerView3.setAdapter(recyclerView3.mAdapter);
                    layoutManager$1.onRestoreInstanceState(onSaveInstanceState);
                } finally {
                    obtainStyledAttributes.recycle();
                }
            }
        }
        this.mCalled = true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(R.attr.preferenceTheme, typedValue, true);
        Configuration configuration = getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        this.mIsLargeLayout = ((i > 320 || configuration.fontScale < 1.1f) && (i >= 411 || configuration.fontScale < 1.3f)) ? 2 : 1;
        this.mScreenWidthDp = i;
        this.mIsReducedMargin = i <= 250;
        int i2 = typedValue.resourceId;
        if (i2 == 0) {
            i2 = R.style.PreferenceThemeOverlay;
        }
        requireContext().getTheme().applyStyle(i2, false);
        PreferenceManager preferenceManager = new PreferenceManager(requireContext());
        this.mPreferenceManager = preferenceManager;
        preferenceManager.mOnNavigateToScreenListener = this;
        Bundle bundle2 = this.mArguments;
        onCreatePreferences(bundle2 != null ? bundle2.getString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT") : null);
    }

    public abstract void onCreatePreferences(String str);

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        RecyclerView recyclerView;
        TypedArray obtainStyledAttributes = requireContext().obtainStyledAttributes(null, R$styleable.PreferenceFragmentCompat, R.attr.preferenceFragmentCompatStyle, 0);
        this.mLayoutResId = obtainStyledAttributes.getResourceId(0, this.mLayoutResId);
        Drawable drawable = obtainStyledAttributes.getDrawable(1);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, -1);
        boolean z = obtainStyledAttributes.getBoolean(3, true);
        obtainStyledAttributes.recycle();
        Context context = getContext();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(null, androidx.appcompat.R$styleable.View, android.R.attr.listSeparatorTextViewStyle, 0);
        Drawable drawable2 = obtainStyledAttributes2.getDrawable(1);
        if (drawable2 instanceof ColorDrawable) {
            this.mSubheaderColor = ((ColorDrawable) drawable2).getColor();
        }
        obtainStyledAttributes2.recycle();
        LayoutInflater cloneInContext = layoutInflater.cloneInContext(context);
        View inflate = cloneInContext.inflate(this.mLayoutResId, viewGroup, false);
        View findViewById = inflate.findViewById(android.R.id.list_container);
        if (!(findViewById instanceof ViewGroup)) {
            throw new IllegalStateException("Content has view with id attribute 'android.R.id.list_container' that is not a ViewGroup class");
        }
        ViewGroup viewGroup2 = (ViewGroup) findViewById;
        if (!requireContext().getPackageManager().hasSystemFeature("android.hardware.type.automotive") || (recyclerView = (RecyclerView) viewGroup2.findViewById(R.id.recycler_view)) == null) {
            recyclerView = (RecyclerView) cloneInContext.inflate(R.layout.sesl_preference_recyclerview, viewGroup2, false);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            PreferenceRecyclerViewAccessibilityDelegate preferenceRecyclerViewAccessibilityDelegate = new PreferenceRecyclerViewAccessibilityDelegate(recyclerView);
            recyclerView.mAccessibilityDelegate = preferenceRecyclerViewAccessibilityDelegate;
            ViewCompat.setAccessibilityDelegate(recyclerView, preferenceRecyclerViewAccessibilityDelegate);
        }
        this.mList = recyclerView;
        if (this.mOnPreDrawListener == null) {
            ViewTreeObserver viewTreeObserver = recyclerView.getViewTreeObserver();
            if (this.mList != null) {
                this.mOnPreDrawListener = new ViewTreeObserverOnPreDrawListenerC04144();
            }
            viewTreeObserver.addOnPreDrawListener(this.mOnPreDrawListener);
        }
        this.mList.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.preference.PreferenceFragmentCompat.3
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                view.getViewTreeObserver().removeOnPreDrawListener(PreferenceFragmentCompat.this.mOnPreDrawListener);
                view.removeOnAttachStateChangeListener(this);
                PreferenceFragmentCompat.this.mOnPreDrawListener = null;
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
            }
        });
        recyclerView.addItemDecoration(this.mDividerDecoration);
        DividerDecoration dividerDecoration = this.mDividerDecoration;
        if (drawable != null) {
            dividerDecoration.getClass();
            dividerDecoration.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            dividerDecoration.mDividerHeight = 0;
        }
        dividerDecoration.mDivider = drawable;
        PreferenceFragmentCompat.this.mList.invalidateItemDecorations();
        if (dimensionPixelSize != -1) {
            DividerDecoration dividerDecoration2 = this.mDividerDecoration;
            dividerDecoration2.mDividerHeight = dimensionPixelSize;
            PreferenceFragmentCompat.this.mList.invalidateItemDecorations();
        }
        this.mDividerDecoration.mAllowDividerAfterLastItem = z;
        this.mList.setItemAnimator(null);
        this.mRoundedCorner = new SeslRoundedCorner(context);
        this.mSubheaderRoundedCorner = new SeslSubheaderRoundedCorner(context);
        if (this.mIsRoundedCorner) {
            recyclerView.seslSetFillBottomEnabled(true);
            int i = this.mSubheaderColor;
            recyclerView.mRectPaint.setColor(i);
            recyclerView.mRoundedCorner.setRoundedCornerColor(12, i);
            SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(context, true);
            this.mListRoundedCorner = seslRoundedCorner;
            seslRoundedCorner.setRoundedCorners(3);
        }
        if (this.mList.getParent() == null) {
            viewGroup2.addView(this.mList);
        }
        post(this.mRequestFocus);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyView() {
        RecyclerView recyclerView;
        removeCallbacks(this.mRequestFocus);
        removeMessages(1);
        if (this.mHavePrefs) {
            this.mList.setAdapter(null);
            PreferenceScreen preferenceScreen = this.mPreferenceManager.mPreferenceScreen;
            if (preferenceScreen != null) {
                preferenceScreen.onDetached();
            }
        }
        if (this.mOnPreDrawListener != null && (recyclerView = this.mList) != null) {
            recyclerView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        }
        this.mList = null;
        this.mCalled = true;
    }

    @Override // androidx.preference.PreferenceManager.OnDisplayPreferenceDialogListener
    public final void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment multiSelectListPreferenceDialogFragmentCompat;
        for (Fragment fragment = this; fragment != null; fragment = fragment.mParentFragment) {
        }
        getContext();
        getActivity();
        if (getParentFragmentManager().findFragmentByTag("androidx.preference.PreferenceFragment.DIALOG") != null) {
            return;
        }
        if (preference instanceof EditTextPreference) {
            String str = preference.mKey;
            multiSelectListPreferenceDialogFragmentCompat = new EditTextPreferenceDialogFragmentCompat();
            Bundle bundle = new Bundle(1);
            bundle.putString("key", str);
            multiSelectListPreferenceDialogFragmentCompat.setArguments(bundle);
        } else if (preference instanceof ListPreference) {
            String str2 = preference.mKey;
            multiSelectListPreferenceDialogFragmentCompat = new ListPreferenceDialogFragmentCompat();
            Bundle bundle2 = new Bundle(1);
            bundle2.putString("key", str2);
            multiSelectListPreferenceDialogFragmentCompat.setArguments(bundle2);
        } else {
            if (!(preference instanceof MultiSelectListPreference)) {
                throw new IllegalArgumentException("Cannot display dialog for an unknown Preference type: " + preference.getClass().getSimpleName() + ". Make sure to implement onPreferenceDisplayDialog() to handle displaying a custom dialog for this Preference.");
            }
            String str3 = preference.mKey;
            multiSelectListPreferenceDialogFragmentCompat = new MultiSelectListPreferenceDialogFragmentCompat();
            Bundle bundle3 = new Bundle(1);
            bundle3.putString("key", str3);
            multiSelectListPreferenceDialogFragmentCompat.setArguments(bundle3);
        }
        multiSelectListPreferenceDialogFragmentCompat.setTargetFragment(this);
        FragmentManager parentFragmentManager = getParentFragmentManager();
        multiSelectListPreferenceDialogFragmentCompat.mDismissed = false;
        multiSelectListPreferenceDialogFragmentCompat.mShownByMe = true;
        BackStackRecord backStackRecord = new BackStackRecord(parentFragmentManager);
        backStackRecord.mReorderingAllowed = true;
        backStackRecord.doAddOp(0, multiSelectListPreferenceDialogFragmentCompat, "androidx.preference.PreferenceFragment.DIALOG", 1);
        backStackRecord.commit();
    }

    @Override // androidx.preference.PreferenceManager.OnNavigateToScreenListener
    public final void onNavigateToScreen(PreferenceScreen preferenceScreen) {
        for (Fragment fragment = this; fragment != null; fragment = fragment.mParentFragment) {
        }
        getContext();
        getActivity();
    }

    @Override // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference.mFragment == null) {
            return false;
        }
        for (Fragment fragment = this; fragment != null; fragment = fragment.mParentFragment) {
        }
        getContext();
        getActivity();
        Log.w("SeslPreferenceFragmentC", "onPreferenceStartFragment is not implemented in the parent activity - attempting to use a fallback implementation. You should implement this method so that you can configure the new fragment that will be displayed, and set a transition between the fragments.");
        FragmentManager parentFragmentManager = getParentFragmentManager();
        if (preference.mExtras == null) {
            preference.mExtras = new Bundle();
        }
        Bundle bundle = preference.mExtras;
        Fragment instantiate = parentFragmentManager.getFragmentFactory().instantiate(requireActivity().getClassLoader(), preference.mFragment);
        instantiate.setArguments(bundle);
        instantiate.setTargetFragment(this);
        BackStackRecord backStackRecord = new BackStackRecord(parentFragmentManager);
        backStackRecord.replace(((View) requireView().getParent()).getId(), instantiate, null);
        if (!backStackRecord.mAllowAddToBackStack) {
            throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
        }
        backStackRecord.mAddToBackStack = true;
        backStackRecord.mName = null;
        backStackRecord.commit();
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        PreferenceScreen preferenceScreen = this.mPreferenceManager.mPreferenceScreen;
        if (preferenceScreen != null) {
            Bundle bundle2 = new Bundle();
            preferenceScreen.dispatchSaveInstanceState(bundle2);
            bundle.putBundle("android:preferences", bundle2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStart() {
        this.mCalled = true;
        PreferenceManager preferenceManager = this.mPreferenceManager;
        preferenceManager.mOnPreferenceTreeClickListener = this;
        preferenceManager.mOnDisplayPreferenceDialogListener = this;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStop() {
        this.mCalled = true;
        PreferenceManager preferenceManager = this.mPreferenceManager;
        preferenceManager.mOnPreferenceTreeClickListener = null;
        preferenceManager.mOnDisplayPreferenceDialogListener = null;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        PreferenceScreen preferenceScreen;
        Bundle bundle2;
        PreferenceScreen preferenceScreen2;
        if (bundle != null && (bundle2 = bundle.getBundle("android:preferences")) != null && (preferenceScreen2 = this.mPreferenceManager.mPreferenceScreen) != null) {
            preferenceScreen2.dispatchRestoreInstanceState(bundle2);
        }
        if (this.mHavePrefs && (preferenceScreen = this.mPreferenceManager.mPreferenceScreen) != null) {
            this.mList.setAdapter(new PreferenceGroupAdapter(preferenceScreen));
            preferenceScreen.onAttached();
        }
        this.mInitDone = true;
    }
}
