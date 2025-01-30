package com.android.internal.view.menu;

import android.app.AppGlobals;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextFlags;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.android.internal.C4337R;
import com.android.internal.view.menu.MenuView;
import java.text.NumberFormat;
import java.util.Locale;

/* loaded from: classes5.dex */
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
    private boolean mUseNewContextMenu;

    public ListMenuItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mIsDeviceDefaultLight = false;
        TypedArray a = context.obtainStyledAttributes(attrs, C4337R.styleable.MenuView, defStyleAttr, defStyleRes);
        this.mBackground = a.getDrawable(5);
        this.mTextAppearance = a.getResourceId(1, -1);
        this.mPreserveIconSpacing = a.getBoolean(8, false);
        this.mTextAppearanceContext = context;
        this.mSubMenuArrow = a.getDrawable(7);
        TypedArray b = context.getTheme().obtainStyledAttributes(null, new int[]{16843049}, 16842861, 0);
        this.mHasListDivider = b.hasValue(0);
        a.recycle();
        b.recycle();
        this.mUseNewContextMenu = AppGlobals.getIntCoreSetting(TextFlags.KEY_ENABLE_NEW_CONTEXT_MENU, 0) != 0;
        TypedValue themeValue = new TypedValue();
        context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, themeValue, false);
        if (themeValue.data != 0) {
            TypedValue colorValue = new TypedValue();
            context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefaultDark, colorValue, true);
            this.mIsDeviceDefaultLight = colorValue.data == 0;
        }
        this.mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
    }

    public ListMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ListMenuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 16844018);
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
        this.mShortcutView = (TextView) findViewById(C4337R.id.shortcut);
        ImageView imageView = (ImageView) findViewById(C4337R.id.submenuarrow);
        this.mSubMenuArrowView = imageView;
        if (imageView != null) {
            imageView.setImageDrawable(this.mSubMenuArrow);
        }
        this.mGroupDivider = (ImageView) findViewById(C4337R.id.group_divider);
        this.mContent = (LinearLayout) findViewById(16908290);
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl itemData, int menuType) {
        this.mItemData = itemData;
        this.mMenuType = menuType;
        setVisibility(itemData.isVisible() ? 0 : 8);
        setTitle(itemData.getTitleForItemView(this));
        setCheckable(itemData.isCheckable());
        setShortcut(itemData.shouldShowShortcut(), itemData.getShortcut());
        setIcon(itemData.getIcon());
        setEnabled(itemData.isEnabled());
        setSubMenuArrowVisible(itemData.hasSubMenu());
        setContentDescription(itemData.getContentDescription());
        setBadgeText(itemData.getBadgeText());
        int i = this.mTextAppearance;
        if (i != -1) {
            TypedArray a = this.mTextAppearanceContext.obtainStyledAttributes(i, C4337R.styleable.TextAppearance);
            int textSize = a.getDimensionPixelSize(0, 0);
            if (textSize != 0) {
                this.mTitleView.getPaint().setTextSize(textSize);
            }
            a.recycle();
        }
    }

    private void addContentView(View v) {
        addContentView(v, -1);
    }

    private void addContentView(View v, int index) {
        LinearLayout linearLayout = this.mContent;
        if (linearLayout != null) {
            linearLayout.addView(v, index);
        } else {
            addView(v, index);
        }
    }

    public void setForceShowIcon(boolean forceShow) {
        this.mForceShowIcon = forceShow;
        this.mPreserveIconSpacing = forceShow;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setTitle(CharSequence title) {
        if (title != null) {
            this.mTitleView.setText(title);
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
    public void setCheckable(boolean checkable) {
        CompoundButton compoundButton;
        CompoundButton otherCompoundButton;
        if (!checkable && this.mRadioButton == null && this.mCheckBox == null) {
            return;
        }
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            compoundButton = this.mRadioButton;
            otherCompoundButton = this.mCheckBox;
        } else {
            CompoundButton compoundButton2 = this.mCheckBox;
            if (compoundButton2 == null) {
                insertCheckBox();
            }
            compoundButton = this.mCheckBox;
            otherCompoundButton = this.mRadioButton;
        }
        if (checkable) {
            compoundButton.setChecked(this.mItemData.isChecked());
            int newVisibility = checkable ? 0 : 8;
            if (compoundButton.getVisibility() != newVisibility) {
                compoundButton.setVisibility(newVisibility);
            }
            if (otherCompoundButton != null && otherCompoundButton.getVisibility() != 8) {
                otherCompoundButton.setVisibility(8);
                return;
            }
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
    public void setChecked(boolean checked) {
        CompoundButton compoundButton;
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            compoundButton = this.mRadioButton;
        } else {
            CompoundButton compoundButton2 = this.mCheckBox;
            if (compoundButton2 == null) {
                insertCheckBox();
            }
            compoundButton = this.mCheckBox;
        }
        compoundButton.setChecked(checked);
    }

    private void setSubMenuArrowVisible(boolean hasSubmenu) {
        ImageView imageView = this.mSubMenuArrowView;
        if (imageView != null) {
            imageView.setVisibility(hasSubmenu ? 0 : 8);
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setShortcut(boolean showShortcut, char shortcutKey) {
        int newVisibility = (showShortcut && this.mItemData.shouldShowShortcut()) ? 0 : 8;
        if (newVisibility == 0) {
            this.mShortcutView.setText(this.mItemData.getShortcutLabel());
        }
        if (this.mShortcutView.getVisibility() != newVisibility) {
            this.mShortcutView.setVisibility(newVisibility);
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setIcon(Drawable icon) {
        boolean showIcon = this.mItemData.shouldShowIcon() || this.mForceShowIcon;
        if (!showIcon && !this.mPreserveIconSpacing) {
            return;
        }
        ImageView imageView = this.mIconView;
        if (imageView == null && icon == null && !this.mPreserveIconSpacing) {
            return;
        }
        if (imageView == null) {
            insertIconView();
        }
        if (icon != null || this.mPreserveIconSpacing) {
            this.mIconView.setImageDrawable(showIcon ? icon : null);
            if (this.mIconView.getVisibility() != 0) {
                this.mIconView.setVisibility(0);
                return;
            }
            return;
        }
        this.mIconView.setVisibility(8);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mIconView != null && this.mPreserveIconSpacing) {
            ViewGroup.LayoutParams lp = getLayoutParams();
            LinearLayout.LayoutParams iconLp = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
            if (lp.height > 0 && iconLp.width <= 0) {
                iconLp.width = lp.height;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void insertIconView() {
        LayoutInflater inflater = getInflater();
        ImageView imageView = (ImageView) inflater.inflate(this.mUseNewContextMenu ? C4337R.layout.list_menu_item_fixed_size_icon : C4337R.layout.list_menu_item_icon, (ViewGroup) this, false);
        this.mIconView = imageView;
        addContentView(imageView, 0);
    }

    private void insertRadioButton() {
        LayoutInflater inflater = getInflater();
        if (this.mIsDeviceDefaultLight) {
            RadioButton radioButton = (RadioButton) inflater.inflate(C4337R.layout.sem_list_menu_item_radio, (ViewGroup) this, false);
            this.mRadioButton = radioButton;
            radioButton.setBackground(null);
        } else {
            this.mRadioButton = (RadioButton) inflater.inflate(C4337R.layout.list_menu_item_radio, (ViewGroup) this, false);
        }
        addContentView(this.mRadioButton);
    }

    private void insertCheckBox() {
        LayoutInflater inflater = getInflater();
        if (this.mIsDeviceDefaultLight) {
            CheckBox checkBox = (CheckBox) inflater.inflate(C4337R.layout.sem_list_menu_item_checkbox, (ViewGroup) this, false);
            this.mCheckBox = checkBox;
            checkBox.setBackground(null);
        } else {
            this.mCheckBox = (CheckBox) inflater.inflate(C4337R.layout.list_menu_item_checkbox, (ViewGroup) this, false);
        }
        addContentView(this.mCheckBox);
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
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
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        MenuItemImpl menuItemImpl = this.mItemData;
        if (menuItemImpl != null && menuItemImpl.hasSubMenu()) {
            info.setCanOpenPopup(true);
        }
        TextView textView = this.mBadgeView;
        if (textView != null && textView.getVisibility() == 0 && this.mBadgeView.getWidth() > 0) {
            MenuItemImpl menuItemImpl2 = this.mItemData;
            String customContentDescription = menuItemImpl2 != null ? menuItemImpl2.getTitle() : "";
            if (!TextUtils.isEmpty(getContentDescription())) {
                CharSequence customContentDescription2 = getContentDescription();
                info.setContentDescription(customContentDescription2);
            } else {
                String badgeContentDescription = ((Object) customContentDescription) + " , " + getResources().getString(C4337R.string.sem_action_menu_overflow_badge_description);
                info.setContentDescription(badgeContentDescription);
            }
        }
    }

    public void setGroupDividerEnabled(boolean groupDividerEnabled) {
        ImageView imageView = this.mGroupDivider;
        if (imageView != null) {
            imageView.setVisibility((this.mHasListDivider || !groupDividerEnabled) ? 8 : 0);
        }
    }

    @Override // android.widget.AbsListView.SelectionBoundsAdjuster
    public void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.mGroupDivider;
        if (imageView != null && imageView.getVisibility() == 0) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mGroupDivider.getLayoutParams();
            rect.top += this.mGroupDivider.getHeight() + lp.topMargin + lp.bottomMargin;
        }
    }

    private void setBadgeText(String text) {
        if (this.mBadgeView == null) {
            insertBadge();
        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mBadgeView.getLayoutParams();
        if (isNumericValue(text)) {
            int badgeNumber = Integer.parseInt(text);
            String localeFormattedNumber = this.mNumberFormat.format(Math.min(badgeNumber, 99));
            this.mBadgeView.setText(localeFormattedNumber);
            int badgeWidth = (int) (getResources().getDimension(C4337R.dimen.sem_badge_default_width) + (localeFormattedNumber.length() * getResources().getDimension(C4337R.dimen.sem_badge_additional_width)));
            int badgeHeight = (int) (getResources().getDimension(C4337R.dimen.sem_badge_default_width) + getResources().getDimension(C4337R.dimen.sem_badge_additional_width));
            lp.width = badgeWidth;
            lp.height = badgeHeight;
            lp.gravity = 8388629;
            this.mBadgeView.setLayoutParams(lp);
        } else {
            lp.topMargin = (int) getResources().getDimension(C4337R.dimen.sem_list_menu_item_dot_badge_top_margin);
            this.mBadgeView.setLayoutParams(lp);
        }
        this.mBadgeView.setVisibility(text != null ? 0 : 8);
    }

    private boolean isNumericValue(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void insertBadge() {
        LayoutInflater inflater = getInflater();
        TextView textView = (TextView) inflater.inflate(C4337R.layout.sem_list_menu_item_badge, (ViewGroup) this, false);
        this.mBadgeView = textView;
        addContentView(textView);
    }
}
