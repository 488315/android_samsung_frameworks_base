package com.android.internal.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.MenuView;
import java.text.NumberFormat;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ListMenuItemView extends LinearLayout implements MenuView.ItemView, AbsListView.SelectionBoundsAdjuster {
    private static final int BADGE_LIMIT_NUMBER = 99;
    private static final String TAG = "ListMenuItemView";
    private Drawable mBackground;
    private TextView mBadgeView;
    private CheckBox mCheckBox;
    private LinearLayout mContent;
    private boolean mForceShowIcon;
    private ImageView mGroupDivider;
    private boolean mHasListDivider;
    private ImageView mIconView;
    private LayoutInflater mInflater;
    private boolean mIsDeviceDefaultLight;
    private MenuItemImpl mItemData;
    private int mMenuType;
    private NumberFormat mNumberFormat;
    private boolean mPreserveIconSpacing;
    private RadioButton mRadioButton;
    private TextView mShortcutView;
    private Drawable mSubMenuArrow;
    private ImageView mSubMenuArrowView;
    private int mTextAppearance;
    private Context mTextAppearanceContext;
    private TextView mTitleView;

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsDeviceDefaultLight = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MenuView, i, i2);
        this.mBackground = obtainStyledAttributes.getDrawable(5);
        this.mTextAppearance = obtainStyledAttributes.getResourceId(1, -1);
        this.mPreserveIconSpacing = obtainStyledAttributes.getBoolean(8, false);
        this.mTextAppearanceContext = context;
        this.mSubMenuArrow = obtainStyledAttributes.getDrawable(7);
        TypedArray obtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(null, new int[]{16843049}, 16842861, 0);
        this.mHasListDivider = obtainStyledAttributes2.hasValue(0);
        obtainStyledAttributes.recycle();
        obtainStyledAttributes2.recycle();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, false);
        if (typedValue.data != 0) {
            TypedValue typedValue2 = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue2, true);
            this.mIsDeviceDefaultLight = typedValue2.data == 0;
        }
        this.mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16844018);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundDrawable(this.mBackground);
        TextView textView = (TextView) findViewById(16908310);
        this.mTitleView = textView;
        int i = this.mTextAppearance;
        if (i != -1) {
            textView.setTextAppearance(this.mTextAppearanceContext, i);
        }
        this.mShortcutView = (TextView) findViewById(R.id.shortcut);
        ImageView imageView = (ImageView) findViewById(R.id.submenuarrow);
        this.mSubMenuArrowView = imageView;
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$2(this.mSubMenuArrow);
        }
        this.mGroupDivider = (ImageView) findViewById(R.id.group_divider);
        this.mContent = (LinearLayout) findViewById(16908290);
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl menuItemImpl, int i) {
        this.mItemData = menuItemImpl;
        this.mMenuType = i;
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        setTitle(menuItemImpl.getTitleForItemView(this));
        setCheckable(menuItemImpl.isCheckable());
        setShortcut(menuItemImpl.shouldShowShortcut(), menuItemImpl.getShortcut());
        setIcon(menuItemImpl.getIcon());
        setEnabled(menuItemImpl.isEnabled());
        setSubMenuArrowVisible(menuItemImpl.hasSubMenu());
        setContentDescription(menuItemImpl.getContentDescription());
        setBadgeText(menuItemImpl.getBadgeText());
        int i2 = this.mTextAppearance;
        if (i2 != -1) {
            TypedArray obtainStyledAttributes = this.mTextAppearanceContext.obtainStyledAttributes(i2, R.styleable.TextAppearance);
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            if (dimensionPixelSize != 0) {
                this.mTitleView.getPaint().setTextSize(dimensionPixelSize);
            }
            obtainStyledAttributes.recycle();
        }
    }

    private void addContentView(View view) {
        addContentView(view, -1);
    }

    private void addContentView(View view, int i) {
        LinearLayout linearLayout = this.mContent;
        if (linearLayout != null) {
            linearLayout.addView(view, i);
        } else {
            addView(view, i);
        }
    }

    public void setForceShowIcon(boolean z) {
        this.mForceShowIcon = z;
        this.mPreserveIconSpacing = z;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setTitle(CharSequence charSequence) {
        if (charSequence != null) {
            this.mTitleView.lambda$setTextAsync$0(charSequence);
            if (this.mTitleView.getVisibility() != 0) {
                this.mTitleView.setVisibility(0);
                return;
            }
            return;
        }
        if (this.mTitleView.getVisibility() != 8) {
            this.mTitleView.setVisibility(8);
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setCheckable(boolean z) {
        CompoundButton compoundButton;
        View view;
        if (!z && this.mRadioButton == null && this.mCheckBox == null) {
            return;
        }
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            compoundButton = this.mRadioButton;
            view = this.mCheckBox;
        } else {
            if (this.mCheckBox == null) {
                insertCheckBox();
            }
            compoundButton = this.mCheckBox;
            view = this.mRadioButton;
        }
        if (z) {
            compoundButton.setChecked(this.mItemData.isChecked());
            int i = z ? 0 : 8;
            if (compoundButton.getVisibility() != i) {
                compoundButton.setVisibility(i);
            }
            if (view == null || view.getVisibility() == 8) {
                return;
            }
            view.setVisibility(8);
            return;
        }
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null) {
            checkBox.setVisibility(8);
        }
        RadioButton radioButton = this.mRadioButton;
        if (radioButton != null) {
            radioButton.setVisibility(8);
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setChecked(boolean z) {
        Checkable checkable;
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            checkable = this.mRadioButton;
        } else {
            if (this.mCheckBox == null) {
                insertCheckBox();
            }
            checkable = this.mCheckBox;
        }
        checkable.setChecked(z);
    }

    private void setSubMenuArrowVisible(boolean z) {
        ImageView imageView = this.mSubMenuArrowView;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 8);
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setShortcut(boolean z, char c) {
        int i = (z && this.mItemData.shouldShowShortcut()) ? 0 : 8;
        if (i == 0) {
            this.mShortcutView.lambda$setTextAsync$0(this.mItemData.getShortcutLabel());
        }
        if (this.mShortcutView.getVisibility() != i) {
            this.mShortcutView.setVisibility(i);
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setIcon(Drawable drawable) {
        boolean z = this.mItemData.shouldShowIcon() || this.mForceShowIcon;
        if (z || this.mPreserveIconSpacing) {
            ImageView imageView = this.mIconView;
            if (imageView == null && drawable == null && !this.mPreserveIconSpacing) {
                return;
            }
            if (imageView == null) {
                insertIconView();
            }
            if (drawable != null || this.mPreserveIconSpacing) {
                ImageView imageView2 = this.mIconView;
                if (!z) {
                    drawable = null;
                }
                imageView2.lambda$setImageURIAsync$2(drawable);
                if (this.mIconView.getVisibility() != 0) {
                    this.mIconView.setVisibility(0);
                    return;
                }
                return;
            }
            this.mIconView.setVisibility(8);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mIconView != null && this.mPreserveIconSpacing) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
            if (layoutParams.height > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = layoutParams.height;
            }
        }
        super.onMeasure(i, i2);
    }

    private void insertIconView() {
        ImageView imageView = (ImageView) getInflater().inflate(R.layout.list_menu_item_icon, (ViewGroup) this, false);
        this.mIconView = imageView;
        addContentView(imageView, 0);
    }

    private void insertRadioButton() {
        LayoutInflater inflater = getInflater();
        if (this.mIsDeviceDefaultLight) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.sem_list_menu_item_radio, (ViewGroup) this, false);
            this.mRadioButton = radioButton;
            radioButton.setBackground(null);
        } else {
            this.mRadioButton = (RadioButton) inflater.inflate(R.layout.list_menu_item_radio, (ViewGroup) this, false);
        }
        addContentView(this.mRadioButton);
    }

    private void insertCheckBox() {
        LayoutInflater inflater = getInflater();
        if (this.mIsDeviceDefaultLight) {
            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.sem_list_menu_item_checkbox, (ViewGroup) this, false);
            this.mCheckBox = checkBox;
            checkBox.setBackground(null);
        } else {
            this.mCheckBox = (CheckBox) inflater.inflate(R.layout.list_menu_item_checkbox, (ViewGroup) this, false);
        }
        addContentView(this.mCheckBox);
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return this.mForceShowIcon;
    }

    private LayoutInflater getInflater() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(this.mContext);
        }
        return this.mInflater;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        MenuItemImpl menuItemImpl = this.mItemData;
        if (menuItemImpl != null && menuItemImpl.hasSubMenu()) {
            accessibilityNodeInfo.setCanOpenPopup(true);
        }
        TextView textView = this.mBadgeView;
        if (textView == null || textView.getVisibility() != 0 || this.mBadgeView.getWidth() <= 0) {
            return;
        }
        MenuItemImpl menuItemImpl2 = this.mItemData;
        String title = menuItemImpl2 != null ? menuItemImpl2.getTitle() : "";
        if (!TextUtils.isEmpty(getContentDescription())) {
            accessibilityNodeInfo.setContentDescription(getContentDescription());
            return;
        }
        accessibilityNodeInfo.setContentDescription(((Object) title) + " , " + getResources().getString(R.string.sem_action_menu_overflow_badge_description));
    }

    public void setGroupDividerEnabled(boolean z) {
        ImageView imageView = this.mGroupDivider;
        if (imageView != null) {
            imageView.setVisibility((this.mHasListDivider || !z) ? 8 : 0);
        }
    }

    @Override // android.widget.AbsListView.SelectionBoundsAdjuster
    public void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.mGroupDivider;
        if (imageView == null || imageView.getVisibility() != 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mGroupDivider.getLayoutParams();
        rect.top += this.mGroupDivider.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    private void setBadgeText(String str) {
        if (this.mBadgeView == null) {
            insertBadge();
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBadgeView.getLayoutParams();
        if (isNumericValue(str)) {
            this.mBadgeView.lambda$setTextAsync$0(this.mNumberFormat.format(Math.min(Integer.parseInt(str), 99)));
            int dimension = (int) (getResources().getDimension(R.dimen.sem_badge_default_width) + (r1.length() * getResources().getDimension(R.dimen.sem_badge_additional_width)));
            int dimension2 = (int) (getResources().getDimension(R.dimen.sem_badge_default_width) + getResources().getDimension(R.dimen.sem_badge_additional_width));
            layoutParams.width = dimension;
            layoutParams.height = dimension2;
            layoutParams.gravity = 8388629;
            this.mBadgeView.setLayoutParams(layoutParams);
        } else {
            layoutParams.topMargin = (int) getResources().getDimension(R.dimen.sem_list_menu_item_dot_badge_top_margin);
            this.mBadgeView.setLayoutParams(layoutParams);
        }
        this.mBadgeView.setVisibility(str != null ? 0 : 8);
    }

    private boolean isNumericValue(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private void insertBadge() {
        TextView textView = (TextView) getInflater().inflate(R.layout.sem_list_menu_item_badge, (ViewGroup) this, false);
        this.mBadgeView = textView;
        addContentView(textView);
    }
}
