package androidx.preference;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import androidx.core.graphics.Insets;
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
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class PreferenceFragmentCompat extends Fragment implements PreferenceManager.OnPreferenceTreeClickListener, PreferenceManager.OnDisplayPreferenceDialogListener, PreferenceManager.OnNavigateToScreenListener, DialogPreference.TargetFragment {
    public boolean mHavePrefs;
    public boolean mInitDone;
    public int mIsLargeLayout;
    public boolean mIsReducedMargin;
    public RecyclerView mList;
    public SeslRoundedCorner mListRoundedCorner;
    public AnonymousClass4 mOnPreDrawListener;
    public PreferenceManager mPreferenceManager;
    public SeslRoundedCorner mRoundedCorner;
    public int mScreenWidthDp;
    public int mSubheaderColor;
    public SeslSubheaderRoundedCorner mSubheaderRoundedCorner;
    public final DividerDecoration mDividerDecoration = new DividerDecoration();
    public int mLayoutResId = R.layout.preference_list_fragment;
    public final boolean mIsRoundedCorner = true;
    public int mLeft = -1;
    public int mTop = -1;
    public int mRight = -1;
    public int mBottom = -1;
    public final AnonymousClass1 mHandler = new Handler(Looper.getMainLooper()) { // from class: androidx.preference.PreferenceFragmentCompat.1
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
    public final AnonymousClass2 mRequestFocus = new Runnable() { // from class: androidx.preference.PreferenceFragmentCompat.2
        @Override // java.lang.Runnable
        public final void run() {
            RecyclerView recyclerView = PreferenceFragmentCompat.this.mList;
            recyclerView.focusableViewAvailable(recyclerView);
        }
    };

    /* renamed from: androidx.preference.PreferenceFragmentCompat$4, reason: invalid class name */
    public class AnonymousClass4 implements ViewTreeObserver.OnPreDrawListener {
        public AnonymousClass4() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            PreferenceFragmentCompat preferenceFragmentCompat = PreferenceFragmentCompat.this;
            RecyclerView recyclerView = preferenceFragmentCompat.mList;
            if (recyclerView != null) {
                RecyclerView.Adapter adapter = recyclerView.mAdapter;
                Configuration configuration = preferenceFragmentCompat.getResources().getConfiguration();
                int i = configuration.screenWidthDp;
                int i2 = ((i > 320 || configuration.fontScale < 1.1f) && (i >= 411 || configuration.fontScale < 1.3f)) ? 2 : 1;
                if (adapter instanceof PreferenceGroupAdapter) {
                    PreferenceFragmentCompat preferenceFragmentCompat2 = PreferenceFragmentCompat.this;
                    PreferenceGroupAdapter preferenceGroupAdapter = (PreferenceGroupAdapter) adapter;
                    if (i2 != preferenceFragmentCompat2.mIsLargeLayout || (i2 == 1 && (preferenceFragmentCompat2.mScreenWidthDp != i || preferenceGroupAdapter.mParentWidth == 0))) {
                        preferenceFragmentCompat2.mIsLargeLayout = i2;
                        for (int i3 = 0; i3 < ((ArrayList) preferenceGroupAdapter.mVisiblePreferences).size(); i3++) {
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

    public class DividerDecoration extends RecyclerView.ItemDecoration {
        public boolean mAllowDividerAfterLastItem = true;
        public Drawable mDivider;
        public int mDividerHeight;

        public DividerDecoration() {
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x007d  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0080  */
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void seslOnDispatchDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            PreferenceFragmentCompat preferenceFragmentCompat;
            PreferenceViewHolder preferenceViewHolder;
            boolean z;
            super.seslOnDispatchDraw(canvas, recyclerView, state);
            int childCount = recyclerView.getChildCount();
            int paddingLeft = recyclerView.getPaddingLeft() + recyclerView.getLeft();
            int right = recyclerView.getRight() - recyclerView.getPaddingRight();
            int i = 0;
            while (true) {
                preferenceFragmentCompat = PreferenceFragmentCompat.this;
                if (i >= childCount) {
                    break;
                }
                View childAt = recyclerView.getChildAt(i);
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childAt);
                if (childViewHolder instanceof PreferenceViewHolder) {
                    preferenceViewHolder = (PreferenceViewHolder) childViewHolder;
                    preferenceViewHolder.getClass();
                } else {
                    preferenceViewHolder = null;
                }
                preferenceFragmentCompat.getResources().getConfiguration().getLayoutDirection();
                int height = childAt.getHeight() + ((int) childAt.getY());
                if (this.mDivider != null) {
                    RecyclerView.ViewHolder childViewHolder2 = recyclerView.getChildViewHolder(childAt);
                    if ((childViewHolder2 instanceof PreferenceViewHolder) && ((PreferenceViewHolder) childViewHolder2).mDividerAllowedBelow) {
                        z = this.mAllowDividerAfterLastItem;
                        int iIndexOfChild = recyclerView.indexOfChild(childAt);
                        if (iIndexOfChild < recyclerView.getChildCount() - 1) {
                            RecyclerView.ViewHolder childViewHolder3 = recyclerView.getChildViewHolder(recyclerView.getChildAt(iIndexOfChild + 1));
                            if ((childViewHolder3 instanceof PreferenceViewHolder) && ((PreferenceViewHolder) childViewHolder3).mDividerAllowedAbove) {
                                z = true;
                            }
                        }
                        if (z) {
                        }
                    } else {
                        z = false;
                        if (z) {
                            this.mDivider.setBounds(paddingLeft, height, right, this.mDividerHeight + height);
                            this.mDivider.draw(canvas);
                        }
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
                i++;
            }
            if (preferenceFragmentCompat.mIsRoundedCorner) {
                SeslRoundedCorner seslRoundedCorner = preferenceFragmentCompat.mListRoundedCorner;
                seslRoundedCorner.mInsets = Insets.of(preferenceFragmentCompat.mLeft, preferenceFragmentCompat.mTop, preferenceFragmentCompat.mRight, preferenceFragmentCompat.mBottom);
                canvas.getClipBounds(seslRoundedCorner.mRoundedCornerBounds);
                seslRoundedCorner.drawRoundedCornerInternal$1(canvas);
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
                    this.mOnPreDrawListener = new AnonymousClass4();
                }
                viewTreeObserver.addOnPreDrawListener(this.mOnPreDrawListener);
            }
            RecyclerView recyclerView2 = this.mList;
            RecyclerView.Adapter adapter = recyclerView2.mAdapter;
            RecyclerView.LayoutManager layoutManager = recyclerView2.getLayoutManager();
            boolean z = configuration.screenWidthDp <= 250;
            if (z != this.mIsReducedMargin && (adapter instanceof PreferenceGroupAdapter) && layoutManager != null) {
                this.mIsReducedMargin = z;
                if (getContext() != null) {
                    TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R$styleable.PreferenceFragmentCompat, R.attr.preferenceFragmentCompatStyle, 0);
                    try {
                        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
                        DividerDecoration dividerDecoration = this.mDividerDecoration;
                        if (drawable != null) {
                            dividerDecoration.getClass();
                            dividerDecoration.mDividerHeight = drawable.getIntrinsicHeight();
                        } else {
                            dividerDecoration.mDividerHeight = 0;
                        }
                        dividerDecoration.mDivider = drawable;
                        PreferenceFragmentCompat.this.mList.invalidateItemDecorations();
                        Parcelable parcelableOnSaveInstanceState = layoutManager.onSaveInstanceState();
                        RecyclerView recyclerView3 = this.mList;
                        recyclerView3.setAdapter(recyclerView3.mAdapter);
                        layoutManager.onRestoreInstanceState(parcelableOnSaveInstanceState);
                    } finally {
                        typedArrayObtainStyledAttributes.recycle();
                    }
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
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws Resources.NotFoundException {
        RecyclerView recyclerView;
        TypedArray typedArrayObtainStyledAttributes = requireContext().obtainStyledAttributes(null, R$styleable.PreferenceFragmentCompat, R.attr.preferenceFragmentCompatStyle, 0);
        this.mLayoutResId = typedArrayObtainStyledAttributes.getResourceId(0, this.mLayoutResId);
        boolean z = true;
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, -1);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(3, true);
        typedArrayObtainStyledAttributes.recycle();
        Context context = getContext();
        if (context != null) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(null, androidx.appcompat.R$styleable.View, android.R.attr.listSeparatorTextViewStyle, 0);
            Drawable drawable2 = typedArrayObtainStyledAttributes2.getDrawable(1);
            if (drawable2 instanceof ColorDrawable) {
                this.mSubheaderColor = ((ColorDrawable) drawable2).getColor();
            }
            typedArrayObtainStyledAttributes2.recycle();
        }
        LayoutInflater layoutInflaterCloneInContext = layoutInflater.cloneInContext(context);
        View viewInflate = layoutInflaterCloneInContext.inflate(this.mLayoutResId, viewGroup, false);
        View viewFindViewById = viewInflate.findViewById(android.R.id.list_container);
        if (!(viewFindViewById instanceof ViewGroup)) {
            throw new IllegalStateException("Content has view with id attribute 'android.R.id.list_container' that is not a ViewGroup class");
        }
        ViewGroup viewGroup2 = (ViewGroup) viewFindViewById;
        if (!requireContext().getPackageManager().hasSystemFeature("android.hardware.type.automotive") || (recyclerView = (RecyclerView) viewGroup2.findViewById(R.id.recycler_view)) == null) {
            recyclerView = (RecyclerView) layoutInflaterCloneInContext.inflate(R.layout.sesl_preference_recyclerview, viewGroup2, false);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            PreferenceRecyclerViewAccessibilityDelegate preferenceRecyclerViewAccessibilityDelegate = new PreferenceRecyclerViewAccessibilityDelegate(recyclerView);
            recyclerView.mAccessibilityDelegate = preferenceRecyclerViewAccessibilityDelegate;
            ViewCompat.setAccessibilityDelegate(recyclerView, preferenceRecyclerViewAccessibilityDelegate);
        }
        this.mList = recyclerView;
        if (this.mOnPreDrawListener == null) {
            ViewTreeObserver viewTreeObserver = recyclerView.getViewTreeObserver();
            if (this.mList != null) {
                this.mOnPreDrawListener = new AnonymousClass4();
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
        this.mDividerDecoration.mAllowDividerAfterLastItem = z2;
        this.mList.setItemAnimator(null);
        this.mRoundedCorner = new SeslRoundedCorner(context);
        this.mSubheaderRoundedCorner = new SeslSubheaderRoundedCorner(context);
        if (this.mIsRoundedCorner) {
            recyclerView.seslSetFillBottomEnabled();
            recyclerView.seslSetFillBottomColor(this.mSubheaderColor);
            SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(context, true);
            this.mListRoundedCorner = seslRoundedCorner;
            seslRoundedCorner.setRoundedCorners(3);
        }
        if (this.mList.getParent() == null) {
            viewGroup2.addView(this.mList);
        }
        post(this.mRequestFocus);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.sesl_preference_padding_horizontal);
        int i = this.mLeft;
        if (i < 0) {
            i = dimensionPixelSize2;
        }
        int i2 = this.mTop;
        if (i2 < 0) {
            i2 = 0;
        }
        int i3 = this.mRight;
        if (i3 >= 0) {
            dimensionPixelSize2 = i3;
        }
        int i4 = this.mBottom;
        if (i4 < 0) {
            i4 = 0;
        }
        this.mLeft = i;
        this.mTop = i2;
        this.mRight = dimensionPixelSize2;
        this.mBottom = i4;
        RecyclerView recyclerView2 = this.mList;
        if (recyclerView2 != null) {
            recyclerView2.setPadding(i, i2, dimensionPixelSize2, i4);
            RecyclerView recyclerView3 = this.mList;
            if (this.mLeft == 0 && this.mRight == 0 && this.mTop == 0 && this.mBottom == 0) {
                z = false;
            }
            recyclerView3.seslSetFillHorizontalPaddingEnabled(z);
            this.mList.setScrollBarStyle((this.mLeft > 0 || this.mRight > 0) ? 33554432 : 0);
        }
        return viewInflate;
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
    public final void onDisplayPreferenceDialog(DialogPreference dialogPreference) {
        DialogFragment multiSelectListPreferenceDialogFragmentCompat;
        for (Fragment fragment = this; fragment != null; fragment = fragment.mParentFragment) {
        }
        getContext();
        getActivity();
        if (getParentFragmentManager().findFragmentByTag("androidx.preference.PreferenceFragment.DIALOG") != null) {
            return;
        }
        if (dialogPreference instanceof EditTextPreference) {
            String str = dialogPreference.mKey;
            multiSelectListPreferenceDialogFragmentCompat = new EditTextPreferenceDialogFragmentCompat();
            Bundle bundle = new Bundle(1);
            bundle.putString("key", str);
            multiSelectListPreferenceDialogFragmentCompat.setArguments(bundle);
        } else if (dialogPreference instanceof ListPreference) {
            String str2 = dialogPreference.mKey;
            multiSelectListPreferenceDialogFragmentCompat = new ListPreferenceDialogFragmentCompat();
            Bundle bundle2 = new Bundle(1);
            bundle2.putString("key", str2);
            multiSelectListPreferenceDialogFragmentCompat.setArguments(bundle2);
        } else {
            if (!(dialogPreference instanceof MultiSelectListPreference)) {
                throw new IllegalArgumentException("Cannot display dialog for an unknown Preference type: " + dialogPreference.getClass().getSimpleName() + ". Make sure to implement onPreferenceDisplayDialog() to handle displaying a custom dialog for this Preference.");
            }
            String str3 = dialogPreference.mKey;
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
        backStackRecord.commitInternal(false, true);
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
        Fragment fragmentInstantiate = parentFragmentManager.getFragmentFactory().instantiate(requireActivity().getClassLoader(), preference.mFragment);
        fragmentInstantiate.setArguments(bundle);
        fragmentInstantiate.setTargetFragment(this);
        BackStackRecord backStackRecord = new BackStackRecord(parentFragmentManager);
        backStackRecord.replace(((View) requireView().getParent()).getId(), fragmentInstantiate, null);
        if (!backStackRecord.mAllowAddToBackStack) {
            throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
        }
        backStackRecord.mAddToBackStack = true;
        backStackRecord.mName = null;
        backStackRecord.commitInternal(false, true);
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
