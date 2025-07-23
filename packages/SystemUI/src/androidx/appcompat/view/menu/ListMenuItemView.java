package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.SeslDropDownItemTextView;
import androidx.appcompat.widget.TintTypedArray;
import com.android.systemui.R;
import java.text.NumberFormat;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ListMenuItemView extends LinearLayout implements MenuView.ItemView, AbsListView.SelectionBoundsAdjuster {
    public final Drawable mBackground;
    public TextView mBadgeView;
    public CheckBox mCheckBox;
    public LinearLayout mContent;
    public SeslDropDownItemTextView mDropDownItemTextView;
    public boolean mForceShowIcon;
    public ImageView mGroupDivider;
    public final boolean mHasListDivider;
    public ImageView mIconView;
    public LayoutInflater mInflater;
    public boolean mIsSubMenu;
    public MenuItemImpl mItemData;
    public final NumberFormat mNumberFormat;
    public boolean mPreserveIconSpacing;
    public RadioButton mRadioButton;
    public TextView mShortcutView;
    public final Drawable mSubMenuArrow;
    public ImageView mSubMenuArrowView;
    public final int mTextAppearance;
    public final Context mTextAppearanceContext;
    public LinearLayout mTitleParent;
    public TextView mTitleView;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listMenuViewStyle);
    }

    @Override // android.widget.AbsListView.SelectionBoundsAdjuster
    public final void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.mGroupDivider;
        if (imageView == null || imageView.getVisibility() != 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mGroupDivider.getLayoutParams();
        rect.top = this.mGroupDivider.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin + rect.top;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public final MenuItemImpl getItemData() {
        return this.mItemData;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0234  */
    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initialize(androidx.appcompat.view.menu.MenuItemImpl r14) {
        /*
            Method dump skipped, instructions count: 921
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.ListMenuItemView.initialize(androidx.appcompat.view.menu.MenuItemImpl):void");
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setBackground(this.mBackground);
        SeslDropDownItemTextView seslDropDownItemTextView = (SeslDropDownItemTextView) findViewById(R.id.sub_menu_title);
        this.mDropDownItemTextView = seslDropDownItemTextView;
        boolean z = seslDropDownItemTextView != null;
        this.mIsSubMenu = z;
        if (z) {
            return;
        }
        TextView textView = (TextView) findViewById(R.id.title);
        this.mTitleView = textView;
        int i = this.mTextAppearance;
        if (i != -1) {
            textView.setTextAppearance(this.mTextAppearanceContext, i);
        }
        TextView textView2 = this.mTitleView;
        if (textView2 != null) {
            textView2.setSingleLine(false);
            this.mTitleView.setMaxLines(2);
        }
        this.mShortcutView = (TextView) findViewById(R.id.shortcut);
        ImageView imageView = (ImageView) findViewById(R.id.submenuarrow);
        this.mSubMenuArrowView = imageView;
        if (imageView != null) {
            imageView.setImageDrawable(this.mSubMenuArrow);
        }
        this.mGroupDivider = (ImageView) findViewById(R.id.group_divider);
        this.mContent = (LinearLayout) findViewById(R.id.content);
        this.mTitleParent = (LinearLayout) findViewById(R.id.title_parent);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        TextView textView = this.mBadgeView;
        if (textView == null || textView.getVisibility() != 0 || this.mBadgeView.getWidth() <= 0) {
            return;
        }
        CharSequence charSequence = this.mItemData.mTitle;
        if (!TextUtils.isEmpty(getContentDescription())) {
            accessibilityNodeInfo.setContentDescription(getContentDescription());
            return;
        }
        accessibilityNodeInfo.setContentDescription(((Object) charSequence) + " , " + getResources().getString(R.string.sesl_action_menu_overflow_badge_description));
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        if (this.mIconView != null && this.mPreserveIconSpacing && !this.mIsSubMenu) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
            int i3 = layoutParams.height;
            if (i3 > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = i3;
            }
        }
        super.onMeasure(i, i2);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.mIsSubMenu = false;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R$styleable.MenuView, i, 0);
        this.mBackground = obtainStyledAttributes.getDrawable(5);
        this.mTextAppearance = obtainStyledAttributes.mWrapped.getResourceId(1, -1);
        this.mPreserveIconSpacing = obtainStyledAttributes.mWrapped.getBoolean(7, false);
        this.mTextAppearanceContext = context;
        this.mSubMenuArrow = obtainStyledAttributes.getDrawable(8);
        TypedArray obtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(null, new int[]{android.R.attr.divider}, R.attr.dropDownListViewStyle, 0);
        this.mHasListDivider = obtainStyledAttributes2.hasValue(0);
        obtainStyledAttributes.recycle();
        obtainStyledAttributes2.recycle();
        this.mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
    }
}
