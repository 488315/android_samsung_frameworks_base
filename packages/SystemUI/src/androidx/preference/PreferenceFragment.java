package androidx.preference;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.appcompat.util.SeslSubheaderRoundedCorner;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.tuner.TunerActivity;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class PreferenceFragment extends Fragment implements PreferenceManager.OnPreferenceTreeClickListener, PreferenceManager.OnDisplayPreferenceDialogListener, PreferenceManager.OnNavigateToScreenListener, DialogPreference.TargetFragment {
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
    public Context mStyledContext;
    public int mSubheaderColor;
    public SeslSubheaderRoundedCorner mSubheaderRoundedCorner;
    public final DividerDecoration mDividerDecoration = new DividerDecoration();
    public int mLayoutResId = R.layout.preference_list_fragment;
    public final boolean mIsRoundedCorner = true;
    public int mLeft = -1;
    public int mTop = -1;
    public int mRight = -1;
    public int mBottom = -1;
    public final AnonymousClass1 mHandler = new Handler() { // from class: androidx.preference.PreferenceFragment.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            PreferenceFragment preferenceFragment = PreferenceFragment.this;
            PreferenceScreen preferenceScreen = preferenceFragment.mPreferenceManager.mPreferenceScreen;
            if (preferenceScreen != null) {
                preferenceFragment.mList.setAdapter(new PreferenceGroupAdapter(preferenceScreen));
                preferenceScreen.onAttached();
            }
        }
    };
    public final AnonymousClass2 mRequestFocus = new Runnable() { // from class: androidx.preference.PreferenceFragment.2
        @Override // java.lang.Runnable
        public final void run() {
            RecyclerView recyclerView = PreferenceFragment.this.mList;
            recyclerView.focusableViewAvailable(recyclerView);
        }
    };

    /* renamed from: androidx.preference.PreferenceFragment$4, reason: invalid class name */
    public class AnonymousClass4 implements ViewTreeObserver.OnPreDrawListener {
        public AnonymousClass4() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            PreferenceFragment preferenceFragment = PreferenceFragment.this;
            RecyclerView recyclerView = preferenceFragment.mList;
            if (recyclerView != null) {
                RecyclerView.Adapter adapter = recyclerView.mAdapter;
                Configuration configuration = preferenceFragment.getResources().getConfiguration();
                int i = configuration.screenWidthDp;
                int i2 = ((i > 320 || configuration.fontScale < 1.1f) && (i >= 411 || configuration.fontScale < 1.3f)) ? 2 : 1;
                if (adapter instanceof PreferenceGroupAdapter) {
                    PreferenceFragment preferenceFragment2 = PreferenceFragment.this;
                    PreferenceGroupAdapter preferenceGroupAdapter = (PreferenceGroupAdapter) adapter;
                    if (i2 != preferenceFragment2.mIsLargeLayout || (i2 == 1 && (preferenceFragment2.mScreenWidthDp != i || preferenceGroupAdapter.mParentWidth == 0))) {
                        preferenceFragment2.mIsLargeLayout = i2;
                        for (int i3 = 0; i3 < ((ArrayList) preferenceGroupAdapter.mVisiblePreferences).size(); i3++) {
                            Preference item = preferenceGroupAdapter.getItem(i3);
                            if (item != null && PreferenceGroupAdapter.isSwitchLayout(item) && ((item instanceof SwitchPreferenceCompat) || (item instanceof SwitchPreference))) {
                                adapter.notifyItemChanged(i3);
                            }
                        }
                    }
                }
                PreferenceFragment preferenceFragment3 = PreferenceFragment.this;
                preferenceFragment3.mScreenWidthDp = configuration.screenWidthDp;
                preferenceFragment3.mList.getViewTreeObserver().removeOnPreDrawListener(this);
                PreferenceFragment.this.mOnPreDrawListener = null;
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

        /* JADX WARN: Removed duplicated region for block: B:22:0x0064  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0067  */
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void seslOnDispatchDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            PreferenceFragment preferenceFragment;
            PreferenceViewHolder preferenceViewHolder;
            boolean z;
            super.seslOnDispatchDraw(canvas, recyclerView, state);
            int childCount = recyclerView.getChildCount();
            int width = recyclerView.getWidth();
            int i = 0;
            while (true) {
                preferenceFragment = PreferenceFragment.this;
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
                            this.mDivider.setBounds(0, height, width, this.mDividerHeight + height);
                            this.mDivider.draw(canvas);
                        }
                    }
                }
                if (preferenceFragment.mIsRoundedCorner && preferenceViewHolder != null && preferenceViewHolder.mDrawBackground) {
                    if (preferenceViewHolder.mSubheaderRound) {
                        preferenceFragment.mSubheaderRoundedCorner.setRoundedCorners(preferenceViewHolder.mDrawCorners);
                        preferenceFragment.mSubheaderRoundedCorner.drawRoundedCorner(childAt, canvas);
                    } else {
                        preferenceFragment.mRoundedCorner.setRoundedCorners(preferenceViewHolder.mDrawCorners);
                        preferenceFragment.mRoundedCorner.drawRoundedCorner(childAt, canvas);
                    }
                }
                i++;
            }
            if (preferenceFragment.mIsRoundedCorner) {
                SeslRoundedCorner seslRoundedCorner = preferenceFragment.mListRoundedCorner;
                seslRoundedCorner.mInsets = Insets.of(preferenceFragment.mLeft, preferenceFragment.mTop, preferenceFragment.mRight, preferenceFragment.mBottom);
                canvas.getClipBounds(seslRoundedCorner.mRoundedCornerBounds);
                seslRoundedCorner.drawRoundedCornerInternal$1(canvas);
            }
        }
    }

    public interface OnPreferenceStartFragmentCallback {
    }

    public interface OnPreferenceStartScreenCallback {
    }

    public final void addPreferencesFromResource(int i) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
        setPreferenceScreen(preferenceManager.inflateFromResource(this.mStyledContext, i, preferenceManager.mPreferenceScreen));
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

    @Override // android.app.Fragment, android.content.ComponentCallbacks
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
            RecyclerView.Adapter adapter = this.mList.mAdapter;
            boolean z = configuration.screenWidthDp <= 250;
            if (z != this.mIsReducedMargin && (adapter instanceof PreferenceGroupAdapter)) {
                this.mIsReducedMargin = z;
                TypedArray typedArrayObtainStyledAttributes = null;
                try {
                    Context context = this.mStyledContext;
                    typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R$styleable.PreferenceFragment, TypedArrayUtils.getAttr(context, R.attr.preferenceFragmentStyle, android.R.attr.preferenceFragmentStyle), 0);
                    Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
                    DividerDecoration dividerDecoration = this.mDividerDecoration;
                    if (drawable != null) {
                        dividerDecoration.getClass();
                        dividerDecoration.mDividerHeight = drawable.getIntrinsicHeight();
                    } else {
                        dividerDecoration.mDividerHeight = 0;
                    }
                    dividerDecoration.mDivider = drawable;
                    PreferenceFragment.this.mList.invalidateItemDecorations();
                    RecyclerView recyclerView2 = this.mList;
                    recyclerView2.setAdapter(recyclerView2.mAdapter);
                    RecyclerView.LayoutManager layoutManager = this.mList.getLayoutManager();
                    if (layoutManager != null) {
                        layoutManager.onRestoreInstanceState(layoutManager.onSaveInstanceState());
                    }
                    typedArrayObtainStyledAttributes.recycle();
                } catch (Throwable th) {
                    if (typedArrayObtainStyledAttributes != null) {
                        typedArrayObtainStyledAttributes.recycle();
                    }
                    throw th;
                }
            }
        }
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.preferenceTheme, typedValue, true);
        Configuration configuration = getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        this.mIsLargeLayout = ((i > 320 || configuration.fontScale < 1.1f) && (i >= 411 || configuration.fontScale < 1.3f)) ? 2 : 1;
        this.mScreenWidthDp = i;
        this.mIsReducedMargin = i <= 250;
        int i2 = typedValue.resourceId;
        if (i2 == 0) {
            i2 = R.style.PreferenceThemeOverlay;
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), i2);
        this.mStyledContext = contextThemeWrapper;
        PreferenceManager preferenceManager = new PreferenceManager(contextThemeWrapper);
        this.mPreferenceManager = preferenceManager;
        preferenceManager.mOnNavigateToScreenListener = this;
        onCreatePreferences(getArguments() != null ? getArguments().getString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT") : null);
    }

    public abstract void onCreatePreferences(String str);

    @Override // android.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws Resources.NotFoundException {
        RecyclerView recyclerView;
        Context context = this.mStyledContext;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R$styleable.PreferenceFragment, TypedArrayUtils.getAttr(context, R.attr.preferenceFragmentStyle, android.R.attr.preferenceFragmentStyle), 0);
        this.mLayoutResId = typedArrayObtainStyledAttributes.getResourceId(0, this.mLayoutResId);
        boolean z = true;
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, -1);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(3, true);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = this.mStyledContext.obtainStyledAttributes(null, androidx.appcompat.R$styleable.View, android.R.attr.listSeparatorTextViewStyle, 0);
        Drawable drawable2 = typedArrayObtainStyledAttributes2.getDrawable(1);
        if (drawable2 instanceof ColorDrawable) {
            this.mSubheaderColor = ((ColorDrawable) drawable2).getColor();
        }
        Log.d("SeslPreferenceFragment", " sub header color = " + this.mSubheaderColor);
        typedArrayObtainStyledAttributes2.recycle();
        LayoutInflater layoutInflaterCloneInContext = layoutInflater.cloneInContext(this.mStyledContext);
        View viewInflate = layoutInflaterCloneInContext.inflate(this.mLayoutResId, viewGroup, false);
        View viewFindViewById = viewInflate.findViewById(android.R.id.list_container);
        if (!(viewFindViewById instanceof ViewGroup)) {
            throw new RuntimeException("Content has view with id attribute 'android.R.id.list_container' that is not a ViewGroup class");
        }
        ViewGroup viewGroup2 = (ViewGroup) viewFindViewById;
        if (!this.mStyledContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive") || (recyclerView = (RecyclerView) viewGroup2.findViewById(R.id.recycler_view)) == null) {
            recyclerView = (RecyclerView) layoutInflaterCloneInContext.inflate(R.layout.sesl_preference_recyclerview, viewGroup2, false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        this.mList.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.preference.PreferenceFragment.3
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                view.getViewTreeObserver().removeOnPreDrawListener(PreferenceFragment.this.mOnPreDrawListener);
                view.removeOnAttachStateChangeListener(this);
                PreferenceFragment.this.mOnPreDrawListener = null;
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
        PreferenceFragment.this.mList.invalidateItemDecorations();
        if (dimensionPixelSize != -1) {
            DividerDecoration dividerDecoration2 = this.mDividerDecoration;
            dividerDecoration2.mDividerHeight = dimensionPixelSize;
            PreferenceFragment.this.mList.invalidateItemDecorations();
        }
        this.mDividerDecoration.mAllowDividerAfterLastItem = z2;
        this.mList.setItemAnimator(null);
        this.mRoundedCorner = new SeslRoundedCorner(this.mStyledContext);
        this.mSubheaderRoundedCorner = new SeslSubheaderRoundedCorner(this.mStyledContext);
        if (this.mIsRoundedCorner) {
            recyclerView.seslSetFillBottomEnabled();
            recyclerView.seslSetFillBottomColor(this.mSubheaderColor);
            SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(this.mStyledContext, true);
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

    @Override // android.app.Fragment
    public final void onDestroyView() {
        RecyclerView recyclerView;
        PreferenceScreen preferenceScreen;
        removeCallbacks(this.mRequestFocus);
        removeMessages(1);
        if (this.mHavePrefs && (preferenceScreen = this.mPreferenceManager.mPreferenceScreen) != null) {
            preferenceScreen.onDetached();
        }
        if (this.mOnPreDrawListener != null && (recyclerView = this.mList) != null) {
            recyclerView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        }
        this.mList = null;
        super.onDestroyView();
    }

    @Override // androidx.preference.PreferenceManager.OnDisplayPreferenceDialogListener
    public void onDisplayPreferenceDialog(DialogPreference dialogPreference) {
        DialogFragment multiSelectListPreferenceDialogFragment;
        getActivity();
        if (getFragmentManager().findFragmentByTag("androidx.preference.PreferenceFragment.DIALOG") != null) {
            return;
        }
        if (dialogPreference instanceof EditTextPreference) {
            String str = dialogPreference.mKey;
            multiSelectListPreferenceDialogFragment = new EditTextPreferenceDialogFragment();
            Bundle bundle = new Bundle(1);
            bundle.putString("key", str);
            multiSelectListPreferenceDialogFragment.setArguments(bundle);
        } else if (dialogPreference instanceof ListPreference) {
            String str2 = dialogPreference.mKey;
            multiSelectListPreferenceDialogFragment = new ListPreferenceDialogFragment();
            Bundle bundle2 = new Bundle(1);
            bundle2.putString("key", str2);
            multiSelectListPreferenceDialogFragment.setArguments(bundle2);
        } else {
            if (!(dialogPreference instanceof MultiSelectListPreference)) {
                throw new IllegalArgumentException("Tried to display dialog for unknown preference type. Did you forget to override onDisplayPreferenceDialog()?");
            }
            String str3 = dialogPreference.mKey;
            multiSelectListPreferenceDialogFragment = new MultiSelectListPreferenceDialogFragment();
            Bundle bundle3 = new Bundle(1);
            bundle3.putString("key", str3);
            multiSelectListPreferenceDialogFragment.setArguments(bundle3);
        }
        multiSelectListPreferenceDialogFragment.setTargetFragment(this, 0);
        multiSelectListPreferenceDialogFragment.show(getFragmentManager(), "androidx.preference.PreferenceFragment.DIALOG");
    }

    @Override // androidx.preference.PreferenceManager.OnNavigateToScreenListener
    public final void onNavigateToScreen(PreferenceScreen preferenceScreen) {
        if (getActivity() instanceof OnPreferenceStartScreenCallback) {
            FragmentTransaction fragmentTransactionBeginTransaction = ((TunerActivity) ((OnPreferenceStartScreenCallback) getActivity())).getFragmentManager().beginTransaction();
            TunerActivity.SubSettingsFragment subSettingsFragment = new TunerActivity.SubSettingsFragment();
            Bundle bundle = new Bundle(1);
            bundle.putString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT", preferenceScreen.mKey);
            subSettingsFragment.setArguments(bundle);
            subSettingsFragment.setTargetFragment(this, 0);
            fragmentTransactionBeginTransaction.replace(R.id.content_frame, subSettingsFragment);
            fragmentTransactionBeginTransaction.addToBackStack("PreferenceFragment");
            fragmentTransactionBeginTransaction.commit();
        }
    }

    @Override // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.mFragment != null && (getActivity() instanceof OnPreferenceStartFragmentCallback)) {
            TunerActivity tunerActivity = (TunerActivity) ((OnPreferenceStartFragmentCallback) getActivity());
            tunerActivity.getClass();
            try {
                Fragment fragment = (Fragment) Class.forName(preference.mFragment).newInstance();
                Bundle bundle = new Bundle(1);
                bundle.putString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT", preference.mKey);
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransactionBeginTransaction = tunerActivity.getFragmentManager().beginTransaction();
                tunerActivity.setTitle(preference.getTitle());
                fragmentTransactionBeginTransaction.replace(R.id.content_frame, fragment);
                fragmentTransactionBeginTransaction.addToBackStack("PreferenceFragment");
                fragmentTransactionBeginTransaction.commit();
                return true;
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                Log.d("TunerActivity", "Problem launching fragment", e);
            }
        }
        return false;
    }

    @Override // android.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PreferenceScreen preferenceScreen = this.mPreferenceManager.mPreferenceScreen;
        if (preferenceScreen != null) {
            Bundle bundle2 = new Bundle();
            preferenceScreen.dispatchSaveInstanceState(bundle2);
            bundle.putBundle("android:preferences", bundle2);
        }
    }

    @Override // android.app.Fragment
    public final void onStart() {
        super.onStart();
        PreferenceManager preferenceManager = this.mPreferenceManager;
        preferenceManager.mOnPreferenceTreeClickListener = this;
        preferenceManager.mOnDisplayPreferenceDialogListener = this;
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        PreferenceManager preferenceManager = this.mPreferenceManager;
        preferenceManager.mOnPreferenceTreeClickListener = null;
        preferenceManager.mOnDisplayPreferenceDialogListener = null;
    }

    @Override // android.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        PreferenceScreen preferenceScreen;
        Bundle bundle2;
        PreferenceScreen preferenceScreen2;
        super.onViewCreated(view, bundle);
        if (bundle != null && (bundle2 = bundle.getBundle("android:preferences")) != null && (preferenceScreen2 = this.mPreferenceManager.mPreferenceScreen) != null) {
            preferenceScreen2.dispatchRestoreInstanceState(bundle2);
        }
        if (this.mHavePrefs && (preferenceScreen = this.mPreferenceManager.mPreferenceScreen) != null) {
            this.mList.setAdapter(new PreferenceGroupAdapter(preferenceScreen));
            preferenceScreen.onAttached();
        }
        this.mInitDone = true;
    }

    public final void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        PreferenceScreen preferenceScreen2 = preferenceManager.mPreferenceScreen;
        if (preferenceScreen != preferenceScreen2) {
            if (preferenceScreen2 != null) {
                preferenceScreen2.onDetached();
            }
            preferenceManager.mPreferenceScreen = preferenceScreen;
            this.mHavePrefs = true;
            if (!this.mInitDone || hasMessages(1)) {
                return;
            }
            obtainMessage(1).sendToTarget();
        }
    }
}
