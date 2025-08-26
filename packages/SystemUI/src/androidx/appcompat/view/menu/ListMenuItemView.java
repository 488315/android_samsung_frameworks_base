package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.SeslDropDownItemTextView;
import androidx.appcompat.widget.TintTypedArray;
import com.android.systemui.R;
import java.text.NumberFormat;
import java.util.Locale;

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

    /* JADX WARN: Removed duplicated region for block: B:122:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x015e  */
    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initialize(MenuItemImpl menuItemImpl) throws Resources.NotFoundException, NumberFormatException {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        boolean z;
        ImageView imageView;
        int i;
        String string;
        boolean z2;
        this.mItemData = menuItemImpl;
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        CharSequence charSequence = menuItemImpl.mTitle;
        if (this.mIsSubMenu) {
            if (charSequence != null) {
                this.mDropDownItemTextView.setText(charSequence);
                if (this.mDropDownItemTextView.getVisibility() != 0) {
                    this.mDropDownItemTextView.setVisibility(0);
                }
            } else if (this.mDropDownItemTextView.getVisibility() != 8) {
                this.mDropDownItemTextView.setVisibility(8);
            }
        } else if (charSequence != null) {
            this.mTitleView.setText(charSequence);
            if (this.mTitleView.getVisibility() != 0) {
                this.mTitleView.setVisibility(0);
            }
        } else if (this.mTitleView.getVisibility() != 8) {
            this.mTitleView.setVisibility(8);
        }
        boolean zIsCheckable = menuItemImpl.isCheckable();
        if (zIsCheckable || this.mRadioButton != null || this.mCheckBox != null) {
            if (!this.mIsSubMenu) {
                if ((this.mItemData.mFlags & 4) != 0) {
                    if (this.mRadioButton == null) {
                        if (this.mInflater == null) {
                            this.mInflater = LayoutInflater.from(getContext());
                        }
                        RadioButton radioButton = (RadioButton) this.mInflater.inflate(R.layout.sesl_list_menu_item_radio, (ViewGroup) this, false);
                        this.mRadioButton = radioButton;
                        LinearLayout linearLayout = this.mContent;
                        if (linearLayout != null) {
                            linearLayout.addView(radioButton, -1);
                        } else {
                            addView(radioButton, -1);
                        }
                    }
                    compoundButton = this.mRadioButton;
                    compoundButton2 = this.mCheckBox;
                } else {
                    if (this.mCheckBox == null) {
                        if (this.mInflater == null) {
                            this.mInflater = LayoutInflater.from(getContext());
                        }
                        CheckBox checkBox = (CheckBox) this.mInflater.inflate(R.layout.sesl_list_menu_item_checkbox, (ViewGroup) this, false);
                        this.mCheckBox = checkBox;
                        LinearLayout linearLayout2 = this.mContent;
                        if (linearLayout2 != null) {
                            linearLayout2.addView(checkBox, -1);
                        } else {
                            addView(checkBox, -1);
                        }
                    }
                    compoundButton = this.mCheckBox;
                    compoundButton2 = this.mRadioButton;
                }
                if (zIsCheckable) {
                    compoundButton.setChecked(this.mItemData.isChecked());
                    if (compoundButton.getVisibility() != 0) {
                        compoundButton.setVisibility(0);
                    }
                    if (compoundButton2 != null && compoundButton2.getVisibility() != 8) {
                        compoundButton2.setVisibility(8);
                    }
                } else {
                    CheckBox checkBox2 = this.mCheckBox;
                    if (checkBox2 != null) {
                        checkBox2.setVisibility(8);
                    }
                    RadioButton radioButton2 = this.mRadioButton;
                    if (radioButton2 != null) {
                        radioButton2.setVisibility(8);
                    }
                }
            } else if (zIsCheckable) {
                this.mDropDownItemTextView.setChecked(this.mItemData.isChecked());
            }
        }
        boolean z3 = true;
        if (menuItemImpl.mMenu.isShortcutsVisible()) {
            if ((menuItemImpl.mMenu.isQwertyMode() ? menuItemImpl.mShortcutAlphabeticChar : menuItemImpl.mShortcutNumericChar) != 0) {
                z = true;
            }
        } else {
            z = false;
        }
        menuItemImpl.mMenu.isQwertyMode();
        if (!this.mIsSubMenu) {
            if (z) {
                MenuItemImpl menuItemImpl2 = this.mItemData;
                if (menuItemImpl2.mMenu.isShortcutsVisible()) {
                    if ((menuItemImpl2.mMenu.isQwertyMode() ? menuItemImpl2.mShortcutAlphabeticChar : menuItemImpl2.mShortcutNumericChar) != 0) {
                        z2 = true;
                    }
                    if (!z2) {
                    }
                    if (i == 0) {
                    }
                    if (this.mShortcutView.getVisibility() != i) {
                    }
                } else {
                    z2 = false;
                    i = !z2 ? 0 : 8;
                    if (i == 0) {
                        TextView textView = this.mShortcutView;
                        MenuItemImpl menuItemImpl3 = this.mItemData;
                        char c = menuItemImpl3.mMenu.isQwertyMode() ? menuItemImpl3.mShortcutAlphabeticChar : menuItemImpl3.mShortcutNumericChar;
                        if (c == 0) {
                            string = "";
                        } else {
                            Resources resources = menuItemImpl3.mMenu.mContext.getResources();
                            StringBuilder sb = new StringBuilder();
                            if (ViewConfiguration.get(menuItemImpl3.mMenu.mContext).hasPermanentMenuKey()) {
                                sb.append(resources.getString(R.string.abc_prepend_shortcut_label));
                            }
                            int i2 = menuItemImpl3.mMenu.isQwertyMode() ? menuItemImpl3.mShortcutAlphabeticModifiers : menuItemImpl3.mShortcutNumericModifiers;
                            MenuItemImpl.appendModifier(i2, 65536, resources.getString(R.string.abc_menu_meta_shortcut_label), sb);
                            MenuItemImpl.appendModifier(i2, 4096, resources.getString(R.string.abc_menu_ctrl_shortcut_label), sb);
                            MenuItemImpl.appendModifier(i2, 2, resources.getString(R.string.abc_menu_alt_shortcut_label), sb);
                            MenuItemImpl.appendModifier(i2, 1, resources.getString(R.string.abc_menu_shift_shortcut_label), sb);
                            MenuItemImpl.appendModifier(i2, 4, resources.getString(R.string.abc_menu_sym_shortcut_label), sb);
                            MenuItemImpl.appendModifier(i2, 8, resources.getString(R.string.abc_menu_function_shortcut_label), sb);
                            if (c == '\b') {
                                sb.append(resources.getString(R.string.abc_menu_delete_shortcut_label));
                            } else if (c == '\n') {
                                sb.append(resources.getString(R.string.abc_menu_enter_shortcut_label));
                            } else if (c != ' ') {
                                sb.append(c);
                            } else {
                                sb.append(resources.getString(R.string.abc_menu_space_shortcut_label));
                            }
                            string = sb.toString();
                        }
                        textView.setText(string);
                    }
                    if (this.mShortcutView.getVisibility() != i) {
                        this.mShortcutView.setVisibility(i);
                    }
                }
            }
        }
        Drawable icon = menuItemImpl.getIcon();
        boolean z4 = this.mIsSubMenu;
        if (!z4) {
            if (!this.mItemData.mMenu.mOptionalIconsVisible && !this.mForceShowIcon) {
                z3 = false;
            }
            if ((z3 || this.mPreserveIconSpacing) && ((imageView = this.mIconView) != null || icon != null || this.mPreserveIconSpacing)) {
                if (imageView == null && !z4) {
                    if (this.mInflater == null) {
                        this.mInflater = LayoutInflater.from(getContext());
                    }
                    ImageView imageView2 = (ImageView) this.mInflater.inflate(R.layout.abc_list_menu_item_icon, (ViewGroup) this, false);
                    this.mIconView = imageView2;
                    LinearLayout linearLayout3 = this.mContent;
                    if (linearLayout3 != null) {
                        linearLayout3.addView(imageView2, 0);
                    } else {
                        addView(imageView2, 0);
                    }
                }
                if (icon != null || this.mPreserveIconSpacing) {
                    ImageView imageView3 = this.mIconView;
                    if (!z3) {
                        icon = null;
                    }
                    imageView3.setImageDrawable(icon);
                    if (this.mIconView.getVisibility() != 0) {
                        this.mIconView.setVisibility(0);
                    }
                } else {
                    this.mIconView.setVisibility(8);
                }
            }
        }
        setEnabled(menuItemImpl.isEnabled());
        boolean zHasSubMenu = menuItemImpl.hasSubMenu();
        ImageView imageView4 = this.mSubMenuArrowView;
        if (imageView4 != null && !this.mIsSubMenu) {
            imageView4.setVisibility(zHasSubMenu ? 0 : 8);
        }
        setContentDescription(menuItemImpl.mContentDescription);
        String str = menuItemImpl.mBadgeText;
        if (this.mBadgeView == null) {
            this.mBadgeView = (TextView) findViewById(R.id.menu_badge);
        }
        if (this.mBadgeView == null) {
            Log.i("ListMenuItemView", "SUB_MENU_ITEM_LAYOUT case, mBadgeView is null");
            return;
        }
        if (this.mTitleParent == null) {
            Log.i("ListMenuItemView", "mTitleParent is null");
            return;
        }
        Resources resources2 = getResources();
        float dimension = resources2.getDimension(R.dimen.sesl_badge_additional_width);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mBadgeView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mTitleParent.getLayoutParams();
        if (str == null) {
            layoutParams.topMargin = (int) resources2.getDimension(R.dimen.sesl_list_menu_item_dot_badge_top_margin);
            layoutParams2.width = -2;
            this.mTitleParent.setLayoutParams(layoutParams2);
            this.mBadgeView.setLayoutParams(layoutParams);
        } else {
            try {
                Integer.parseInt(str);
                String str2 = this.mNumberFormat.format(Math.min(Integer.parseInt(str), 99));
                int dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.sesl_menu_item_badge_text_size);
                TextView textView2 = this.mBadgeView;
                float f = getResources().getConfiguration().fontScale;
                if (f > 1.2f) {
                    textView2.setTextSize(0, (dimensionPixelSize / f) * 1.2f);
                }
                this.mBadgeView.setText(str2);
                int length = (int) ((str2.length() * dimension) + resources2.getDimension(R.dimen.sesl_badge_default_width));
                int dimension2 = (int) (resources2.getDimension(R.dimen.sesl_badge_default_width) + dimension);
                layoutParams.width = length;
                layoutParams.height = dimension2;
                layoutParams.addRule(15, -1);
                this.mBadgeView.setLayoutParams(layoutParams);
            } catch (NumberFormatException unused) {
            }
        }
        int i3 = layoutParams.width;
        if (str != null) {
            this.mTitleParent.setPaddingRelative(0, 0, getResources().getDimensionPixelSize(R.dimen.sesl_list_menu_item_dot_badge_end_margin) + i3, 0);
        }
        this.mBadgeView.setVisibility(str != null ? 0 : 8);
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
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R$styleable.MenuView, i, 0);
        this.mBackground = tintTypedArrayObtainStyledAttributes.getDrawable(5);
        this.mTextAppearance = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(1, -1);
        this.mPreserveIconSpacing = tintTypedArrayObtainStyledAttributes.mWrapped.getBoolean(7, false);
        this.mTextAppearanceContext = context;
        this.mSubMenuArrow = tintTypedArrayObtainStyledAttributes.getDrawable(8);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, new int[]{android.R.attr.divider}, R.attr.dropDownListViewStyle, 0);
        this.mHasListDivider = typedArrayObtainStyledAttributes.hasValue(0);
        tintTypedArrayObtainStyledAttributes.recycle();
        typedArrayObtainStyledAttributes.recycle();
        this.mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
    }
}
