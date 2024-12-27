package com.android.systemui.statusbar;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.statusbar.model.KshData;
import java.util.List;

public final class KshView {
    public final AccessibilityManager mA11yManager;
    public Context mContext;
    public boolean mHardKeyScrolled;
    public final LayoutInflater mInflater;
    public Dialog mKeyboardShortcutsDialog;
    public RecyclerView mKshGroupRecyclerView;
    public KshViewAdapter mKshViewAdapter;
    public int mLastPosition;
    public LinearLayoutManager mLayoutManager;
    public int mMaxColumn;
    public float mMoveSelectorX;
    public boolean mNeedForceScroll;
    public int mPosition;
    public final KshPresenter mPresenter;
    public final Resources mResources;
    public boolean mRightScrolled;
    public final float mSelectorMoveRange;
    public View mSelectorView;
    public boolean mTabKeyIn;
    public int mViewHeight;
    public int mViewWidth;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final KshView$$ExternalSyntheticLambda0 mForceScroll = new KshView$$ExternalSyntheticLambda0(this, 0);
    public final AnonymousClass1 mHorizontalScrollListener = new AnonymousClass1();

    /* renamed from: com.android.systemui.statusbar.KshView$1, reason: invalid class name */
    public final class AnonymousClass1 extends RecyclerView.OnScrollListener {
        public AnonymousClass1() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
            KshView kshView = KshView.this;
            if (i != 0 && !kshView.mHardKeyScrolled) {
                kshView.mNeedForceScroll = true;
            }
            if (i != 1 && kshView.mNeedForceScroll) {
                kshView.mHandler.post(kshView.mForceScroll);
                kshView.mNeedForceScroll = false;
            }
            if (i == 1) {
                kshView.mHardKeyScrolled = false;
            }
            if (i == 2 && kshView.mTabKeyIn) {
                kshView.mHardKeyScrolled = true;
            }
            if (i == 0 && kshView.mHardKeyScrolled) {
                Handler handler = kshView.mHandler;
                handler.removeCallbacks(kshView.mForceScroll);
                if (kshView.isRTL()) {
                    kshView.mRightScrolled = !kshView.mRightScrolled;
                }
                LinearLayoutManager linearLayoutManager = kshView.mLayoutManager;
                View findOneVisibleChild = linearLayoutManager.findOneVisibleChild(0, linearLayoutManager.getChildCount(), true, false);
                int position = findOneVisibleChild == null ? -1 : RecyclerView.LayoutManager.getPosition(findOneVisibleChild);
                LinearLayoutManager linearLayoutManager2 = kshView.mLayoutManager;
                View findOneVisibleChild2 = linearLayoutManager2.findOneVisibleChild(linearLayoutManager2.getChildCount() - 1, -1, true, false);
                int position2 = findOneVisibleChild2 != null ? RecyclerView.LayoutManager.getPosition(findOneVisibleChild2) : -1;
                if (kshView.mRightScrolled) {
                    position = position2;
                }
                kshView.mPosition = position;
                if (position != kshView.mLastPosition) {
                    handler.post(new KshView$$ExternalSyntheticLambda0(this, 1));
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
            boolean z = i > 0;
            KshView kshView = KshView.this;
            kshView.mRightScrolled = z;
            if (i == 0 && kshView.isRTL()) {
                kshView.mRightScrolled = true;
            }
        }
    }

    public KshView(Context context, KshPresenter kshPresenter) {
        this.mContext = context;
        this.mPresenter = kshPresenter;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        Resources resources = this.mContext.getResources();
        this.mResources = resources;
        this.mSelectorMoveRange = resources.getDimension(R.dimen.ksh_selector_move_range);
        this.mA11yManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
    }

    public KshViewAdapter getKshViewAdapter() {
        return this.mKshViewAdapter;
    }

    public final CharSequence getLabel(int i, List list) {
        int size = list.size();
        return i == size + (-2) ? this.mContext.getString(R.string.ksh_indicator_label_system) : i == size + (-1) ? this.mContext.getString(R.string.ksh_indicator_label_app) : ((KeyboardShortcutGroup) list.get(i)).getLabel();
    }

    public final boolean isRTL() {
        return this.mResources.getConfiguration().getLayoutDirection() == 1;
    }

    public final void moveSelector(int i) {
        int abs;
        int i2 = this.mLastPosition;
        if (i != i2 && (abs = Math.abs(i2 - i)) >= this.mMaxColumn) {
            int size = this.mKshViewAdapter.mData.size();
            int i3 = this.mMaxColumn;
            int i4 = size - i3;
            if (i <= i4) {
                i4 = i;
            }
            int i5 = this.mLastPosition;
            if (i4 == i5) {
                return;
            }
            int i6 = (abs - i3) + 1;
            this.mRightScrolled = i4 > i5;
            if (isRTL()) {
                this.mRightScrolled = !this.mRightScrolled;
            }
            boolean z = this.mRightScrolled;
            float f = this.mSelectorMoveRange;
            if (z) {
                this.mMoveSelectorX = (f * i6) + this.mMoveSelectorX;
            } else {
                this.mMoveSelectorX -= f * i6;
            }
            this.mLastPosition = i;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mSelectorView, "translationX", this.mMoveSelectorX);
            ofFloat.setDuration(250L);
            ofFloat.start();
        }
    }

    public final void showKshDialog(final List list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        ViewGroup viewGroup = null;
        View inflate = this.mInflater.inflate(R.layout.samsung_keyboard_shortcuts_view, (ViewGroup) null);
        TypedValue typedValue = new TypedValue();
        int i = this.mResources.getDisplayMetrics().widthPixels;
        int i2 = this.mResources.getDisplayMetrics().heightPixels;
        int i3 = (int) (this.mResources.getDisplayMetrics().density * 600.0f);
        this.mResources.getValue(R.dimen.ksh_dialog_width_ratio, typedValue, true);
        float f = i;
        this.mViewWidth = (int) typedValue.getFraction(f, f);
        this.mResources.getValue(R.dimen.ksh_dialog_height_ratio, typedValue, true);
        float f2 = i2;
        int fraction = (int) typedValue.getFraction(f2, f2);
        this.mViewHeight = fraction;
        if (fraction >= i2) {
            this.mViewHeight = -1;
        }
        if (this.mViewHeight > i3) {
            this.mViewHeight = i3;
        }
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ksh_view);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(this.mViewWidth, this.mViewHeight));
        this.mMaxColumn = this.mResources.getInteger(R.integer.ksh_max_column);
        int size = list.size();
        if (size < this.mMaxColumn) {
            this.mMaxColumn = size;
        }
        this.mKshGroupRecyclerView = (RecyclerView) inflate.findViewById(R.id.ksh_group_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext, 0, false);
        this.mLayoutManager = linearLayoutManager;
        this.mKshGroupRecyclerView.setLayoutManager(linearLayoutManager);
        KshViewAdapter kshViewAdapter = new KshViewAdapter(this.mContext);
        this.mKshViewAdapter = kshViewAdapter;
        kshViewAdapter.mMaxColumn = this.mMaxColumn;
        KshData kshData = this.mPresenter.mKshData;
        kshViewAdapter.mData = list;
        kshViewAdapter.mKshData = kshData;
        this.mKshGroupRecyclerView.setAdapter(kshViewAdapter);
        this.mMoveSelectorX = 0.0f;
        this.mLastPosition = 0;
        this.mPosition = 0;
        FrameLayout frameLayout = (FrameLayout) inflate.findViewById(R.id.indicator_frame);
        int i4 = this.mMaxColumn;
        AnonymousClass1 anonymousClass1 = this.mHorizontalScrollListener;
        if (size > i4) {
            int size2 = list.size();
            LinearLayout linearLayout2 = (LinearLayout) frameLayout.findViewById(R.id.label_container);
            int i5 = 0;
            while (i5 < size2) {
                TextView textView = (TextView) this.mInflater.inflate(R.layout.samsung_ksh_indicator_label_view, viewGroup);
                CharSequence label = getLabel(i5, list);
                textView.setText(label);
                textView.setTag(Integer.valueOf(i5));
                textView.setContentDescription(((Object) label) + ", " + this.mContext.getResources().getString(R.string.keyboard_shortcut_indicator_selector_tab_description));
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.KshView$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        final KshView kshView = KshView.this;
                        final List list2 = list;
                        kshView.getClass();
                        final int intValue = ((Integer) view.getTag()).intValue();
                        if (intValue != kshView.mLastPosition) {
                            kshView.mKshGroupRecyclerView.post(new Runnable() { // from class: com.android.systemui.statusbar.KshView$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    KshView kshView2 = KshView.this;
                                    int i6 = intValue;
                                    List list3 = list2;
                                    kshView2.mPosition = i6;
                                    kshView2.mKshGroupRecyclerView.scrollToPosition(i6);
                                    kshView2.moveSelector(i6);
                                    AccessibilityManager accessibilityManager = kshView2.mA11yManager;
                                    if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
                                        return;
                                    }
                                    AccessibilityEvent obtain = AccessibilityEvent.obtain();
                                    obtain.setEventType(16384);
                                    obtain.getText().add(((Object) kshView2.getLabel(i6, list3)) + ", " + kshView2.mContext.getResources().getString(R.string.keyboard_shortcut_indicator_selector_tab_description));
                                    kshView2.mA11yManager.sendAccessibilityEvent(obtain);
                                }
                            });
                        }
                    }
                });
                linearLayout2.addView(textView, i5);
                i5++;
                viewGroup = null;
            }
            linearLayout2.setVisibility(0);
            View findViewById = frameLayout.findViewById(R.id.label_selector);
            this.mSelectorView = findViewById;
            findViewById.setLayoutParams(new LinearLayout.LayoutParams(this.mResources.getDimensionPixelSize(R.dimen.ksh_selector_width) * this.mMaxColumn, -1));
            this.mSelectorView.setVisibility(0);
            linearLayout.setPadding(0, 0, 0, this.mResources.getDimensionPixelSize(R.dimen.ksh_padding_bottom_with_indicator));
            this.mKshGroupRecyclerView.addOnScrollListener(anonymousClass1);
        } else {
            frameLayout.setVisibility(8);
            linearLayout.setPadding(0, 0, 0, this.mResources.getDimensionPixelSize(R.dimen.ksh_padding_bottom));
            this.mKshGroupRecyclerView.removeOnScrollListener(anonymousClass1);
        }
        builder.setView(inflate);
        AlertDialog create = builder.create();
        this.mKeyboardShortcutsDialog = create;
        Window window = create.getWindow();
        window.setType(2008);
        this.mKeyboardShortcutsDialog.setCanceledOnTouchOutside(true);
        this.mKeyboardShortcutsDialog.show();
        window.getDecorView().setBackground(this.mResources.getDrawable(R.drawable.ksh_dialog_background_material, this.mContext.getTheme()));
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.setTitle("KeyboardShortcutsDialog");
        attributes.width = this.mViewWidth;
        attributes.height = this.mViewHeight;
        window.setAttributes(attributes);
        this.mKeyboardShortcutsDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.android.systemui.statusbar.KshView$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i6, KeyEvent keyEvent) {
                KshView kshView = KshView.this;
                kshView.getClass();
                if (i6 == 21 || i6 == 22) {
                    kshView.mHardKeyScrolled = true;
                }
                if (i6 != 61) {
                    return false;
                }
                kshView.mTabKeyIn = true;
                return false;
            }
        });
    }
}
