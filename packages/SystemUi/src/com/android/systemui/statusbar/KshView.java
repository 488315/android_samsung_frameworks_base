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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final C25611 mHorizontalScrollListener = new C25611();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.KshView$1 */
    public final class C25611 extends RecyclerView.OnScrollListener {
        public C25611() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
            int i2 = 1;
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
                    handler.post(new KshView$$ExternalSyntheticLambda0(this, i2));
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
            int itemCount = this.mKshViewAdapter.getItemCount();
            int i3 = this.mMaxColumn;
            int i4 = itemCount - i3;
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
        LayoutInflater layoutInflater = this.mInflater;
        ViewGroup viewGroup = null;
        View inflate = layoutInflater.inflate(R.layout.samsung_keyboard_shortcuts_view, (ViewGroup) null);
        TypedValue typedValue = new TypedValue();
        Resources resources = this.mResources;
        int i = resources.getDisplayMetrics().widthPixels;
        int i2 = resources.getDisplayMetrics().heightPixels;
        int i3 = (int) (resources.getDisplayMetrics().density * 600.0f);
        resources.getValue(R.dimen.ksh_dialog_width_ratio, typedValue, true);
        float f = i;
        this.mViewWidth = (int) typedValue.getFraction(f, f);
        resources.getValue(R.dimen.ksh_dialog_height_ratio, typedValue, true);
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
        this.mMaxColumn = resources.getInteger(R.integer.ksh_max_column);
        int size = list.size();
        if (size < this.mMaxColumn) {
            this.mMaxColumn = size;
        }
        this.mKshGroupRecyclerView = (RecyclerView) inflate.findViewById(R.id.ksh_group_recycler_view);
        int i4 = 0;
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
        int i5 = this.mMaxColumn;
        C25611 c25611 = this.mHorizontalScrollListener;
        if (size > i5) {
            int size2 = list.size();
            LinearLayout linearLayout2 = (LinearLayout) frameLayout.findViewById(R.id.label_container);
            int i6 = 0;
            while (i6 < size2) {
                TextView textView = (TextView) layoutInflater.inflate(R.layout.samsung_ksh_indicator_label_view, viewGroup);
                CharSequence label = getLabel(i6, list);
                textView.setText(label);
                textView.setTag(Integer.valueOf(i6));
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
                                    int i7 = intValue;
                                    List list3 = list2;
                                    kshView2.mPosition = i7;
                                    kshView2.mKshGroupRecyclerView.scrollToPosition(i7);
                                    kshView2.moveSelector(i7);
                                    AccessibilityManager accessibilityManager = kshView2.mA11yManager;
                                    if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
                                        return;
                                    }
                                    AccessibilityEvent obtain = AccessibilityEvent.obtain();
                                    obtain.setEventType(16384);
                                    obtain.getText().add(((Object) kshView2.getLabel(i7, list3)) + ", " + kshView2.mContext.getResources().getString(R.string.keyboard_shortcut_indicator_selector_tab_description));
                                    accessibilityManager.sendAccessibilityEvent(obtain);
                                }
                            });
                        }
                    }
                });
                linearLayout2.addView(textView, i6);
                i6++;
                viewGroup = null;
                i4 = 0;
            }
            linearLayout2.setVisibility(i4);
            View findViewById = frameLayout.findViewById(R.id.label_selector);
            this.mSelectorView = findViewById;
            findViewById.setLayoutParams(new LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.ksh_selector_width) * this.mMaxColumn, -1));
            this.mSelectorView.setVisibility(0);
            linearLayout.setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.ksh_padding_bottom_with_indicator));
            this.mKshGroupRecyclerView.addOnScrollListener(c25611);
        } else {
            frameLayout.setVisibility(8);
            linearLayout.setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.ksh_padding_bottom));
            this.mKshGroupRecyclerView.removeOnScrollListener(c25611);
        }
        builder.setView(inflate);
        AlertDialog create = builder.create();
        this.mKeyboardShortcutsDialog = create;
        Window window = create.getWindow();
        window.setType(2008);
        this.mKeyboardShortcutsDialog.setCanceledOnTouchOutside(true);
        this.mKeyboardShortcutsDialog.show();
        window.getDecorView().setBackground(resources.getDrawable(R.drawable.ksh_dialog_background_material, this.mContext.getTheme()));
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.setTitle("KeyboardShortcutsDialog");
        attributes.width = this.mViewWidth;
        attributes.height = this.mViewHeight;
        window.setAttributes(attributes);
        this.mKeyboardShortcutsDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.android.systemui.statusbar.KshView$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i7, KeyEvent keyEvent) {
                KshView kshView = KshView.this;
                kshView.getClass();
                if (i7 == 21 || i7 == 22) {
                    kshView.mHardKeyScrolled = true;
                }
                if (i7 != 61) {
                    return false;
                }
                kshView.mTabKeyIn = true;
                return false;
            }
        });
    }
}
