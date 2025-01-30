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
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public final LayoutInflater getInflater() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getContext());
        }
        return this.mInflater;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public final MenuItemImpl getItemData() {
        return this.mItemData;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0369  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0221  */
    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initialize(MenuItemImpl menuItemImpl) {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        boolean z;
        boolean z2;
        boolean hasSubMenu;
        ImageView imageView;
        ImageView imageView2;
        int i;
        String sb;
        boolean z3;
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
        boolean isCheckable = menuItemImpl.isCheckable();
        boolean z4 = true;
        if (isCheckable || this.mRadioButton != null || this.mCheckBox != null) {
            if (!this.mIsSubMenu) {
                if ((this.mItemData.mFlags & 4) != 0) {
                    if (this.mRadioButton == null) {
                        RadioButton radioButton = (RadioButton) getInflater().inflate(R.layout.sesl_list_menu_item_radio, (ViewGroup) this, false);
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
                        CheckBox checkBox = (CheckBox) getInflater().inflate(R.layout.sesl_list_menu_item_checkbox, (ViewGroup) this, false);
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
                if (isCheckable) {
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
            } else if (isCheckable) {
                this.mDropDownItemTextView.setChecked(this.mItemData.isChecked());
            }
        }
        if (menuItemImpl.mMenu.isShortcutsVisible()) {
            if ((menuItemImpl.mMenu.isQwertyMode() ? menuItemImpl.mShortcutAlphabeticChar : menuItemImpl.mShortcutNumericChar) != 0) {
                z = true;
                menuItemImpl.mMenu.isQwertyMode();
                if (!this.mIsSubMenu) {
                    if (z) {
                        MenuItemImpl menuItemImpl2 = this.mItemData;
                        if (menuItemImpl2.mMenu.isShortcutsVisible()) {
                            if ((menuItemImpl2.mMenu.isQwertyMode() ? menuItemImpl2.mShortcutAlphabeticChar : menuItemImpl2.mShortcutNumericChar) != 0) {
                                z3 = true;
                                if (z3) {
                                    i = 0;
                                    if (i == 0) {
                                        TextView textView = this.mShortcutView;
                                        MenuItemImpl menuItemImpl3 = this.mItemData;
                                        char c = menuItemImpl3.mMenu.isQwertyMode() ? menuItemImpl3.mShortcutAlphabeticChar : menuItemImpl3.mShortcutNumericChar;
                                        if (c == 0) {
                                            sb = "";
                                        } else {
                                            Resources resources = menuItemImpl3.mMenu.mContext.getResources();
                                            StringBuilder sb2 = new StringBuilder();
                                            if (ViewConfiguration.get(menuItemImpl3.mMenu.mContext).hasPermanentMenuKey()) {
                                                sb2.append(resources.getString(R.string.abc_prepend_shortcut_label));
                                            }
                                            int i2 = menuItemImpl3.mMenu.isQwertyMode() ? menuItemImpl3.mShortcutAlphabeticModifiers : menuItemImpl3.mShortcutNumericModifiers;
                                            MenuItemImpl.appendModifier(i2, 65536, resources.getString(R.string.abc_menu_meta_shortcut_label), sb2);
                                            MenuItemImpl.appendModifier(i2, 4096, resources.getString(R.string.abc_menu_ctrl_shortcut_label), sb2);
                                            MenuItemImpl.appendModifier(i2, 2, resources.getString(R.string.abc_menu_alt_shortcut_label), sb2);
                                            MenuItemImpl.appendModifier(i2, 1, resources.getString(R.string.abc_menu_shift_shortcut_label), sb2);
                                            MenuItemImpl.appendModifier(i2, 4, resources.getString(R.string.abc_menu_sym_shortcut_label), sb2);
                                            MenuItemImpl.appendModifier(i2, 8, resources.getString(R.string.abc_menu_function_shortcut_label), sb2);
                                            if (c == '\b') {
                                                sb2.append(resources.getString(R.string.abc_menu_delete_shortcut_label));
                                            } else if (c == '\n') {
                                                sb2.append(resources.getString(R.string.abc_menu_enter_shortcut_label));
                                            } else if (c != ' ') {
                                                sb2.append(c);
                                            } else {
                                                sb2.append(resources.getString(R.string.abc_menu_space_shortcut_label));
                                            }
                                            sb = sb2.toString();
                                        }
                                        textView.setText(sb);
                                    }
                                    if (this.mShortcutView.getVisibility() != i) {
                                        this.mShortcutView.setVisibility(i);
                                    }
                                }
                            }
                        }
                        z3 = false;
                        if (z3) {
                        }
                    }
                    i = 8;
                    if (i == 0) {
                    }
                    if (this.mShortcutView.getVisibility() != i) {
                    }
                }
                Drawable icon = menuItemImpl.getIcon();
                z2 = this.mIsSubMenu;
                if (!z2) {
                    boolean z5 = this.mItemData.mMenu.mOptionalIconsVisible || this.mForceShowIcon;
                    if ((z5 || this.mPreserveIconSpacing) && ((imageView2 = this.mIconView) != null || icon != null || this.mPreserveIconSpacing)) {
                        if (imageView2 == null && !z2) {
                            ImageView imageView3 = (ImageView) getInflater().inflate(R.layout.abc_list_menu_item_icon, (ViewGroup) this, false);
                            this.mIconView = imageView3;
                            LinearLayout linearLayout3 = this.mContent;
                            if (linearLayout3 != null) {
                                linearLayout3.addView(imageView3, 0);
                            } else {
                                addView(imageView3, 0);
                            }
                        }
                        if (icon != null || this.mPreserveIconSpacing) {
                            ImageView imageView4 = this.mIconView;
                            if (!z5) {
                                icon = null;
                            }
                            imageView4.setImageDrawable(icon);
                            if (this.mIconView.getVisibility() != 0) {
                                this.mIconView.setVisibility(0);
                            }
                        } else {
                            this.mIconView.setVisibility(8);
                        }
                    }
                }
                setEnabled(menuItemImpl.isEnabled());
                hasSubMenu = menuItemImpl.hasSubMenu();
                imageView = this.mSubMenuArrowView;
                if (imageView != null && !this.mIsSubMenu) {
                    imageView.setVisibility(!hasSubMenu ? 0 : 8);
                }
                setContentDescription(menuItemImpl.mContentDescription);
                String str = menuItemImpl.mBadgeText;
                if (this.mBadgeView == null) {
                    this.mBadgeView = (TextView) findViewById(R.id.menu_badge);
                }
                if (this.mBadgeView != null) {
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
                if (str != null) {
                    try {
                        Integer.parseInt(str);
                    } catch (NumberFormatException unused) {
                    }
                    if (z4) {
                        layoutParams.topMargin = (int) resources2.getDimension(R.dimen.sesl_list_menu_item_dot_badge_top_margin);
                        layoutParams2.width = -2;
                        this.mTitleParent.setLayoutParams(layoutParams2);
                        this.mBadgeView.setLayoutParams(layoutParams);
                    } else {
                        String format = this.mNumberFormat.format(Math.min(Integer.parseInt(str), 99));
                        int dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.sesl_menu_item_badge_text_size);
                        TextView textView2 = this.mBadgeView;
                        float f = getResources().getConfiguration().fontScale;
                        if (f > 1.2f) {
                            textView2.setTextSize(0, (dimensionPixelSize / f) * 1.2f);
                        }
                        this.mBadgeView.setText(format);
                        int length = (int) ((format.length() * dimension) + resources2.getDimension(R.dimen.sesl_badge_default_width));
                        int dimension2 = (int) (resources2.getDimension(R.dimen.sesl_badge_default_width) + dimension);
                        layoutParams.width = length;
                        layoutParams.height = dimension2;
                        layoutParams.addRule(15, -1);
                        this.mBadgeView.setLayoutParams(layoutParams);
                    }
                    int i3 = layoutParams.width;
                    if (str != null) {
                        this.mTitleParent.setPaddingRelative(0, 0, getResources().getDimensionPixelSize(R.dimen.sesl_list_menu_item_dot_badge_end_margin) + i3, 0);
                    }
                    this.mBadgeView.setVisibility(str == null ? 8 : 0);
                    return;
                }
                z4 = false;
                if (z4) {
                }
                int i32 = layoutParams.width;
                if (str != null) {
                }
                this.mBadgeView.setVisibility(str == null ? 8 : 0);
                return;
            }
        }
        z = false;
        menuItemImpl.mMenu.isQwertyMode();
        if (!this.mIsSubMenu) {
        }
        Drawable icon2 = menuItemImpl.getIcon();
        z2 = this.mIsSubMenu;
        if (!z2) {
        }
        setEnabled(menuItemImpl.isEnabled());
        hasSubMenu = menuItemImpl.hasSubMenu();
        imageView = this.mSubMenuArrowView;
        if (imageView != null) {
            imageView.setVisibility(!hasSubMenu ? 0 : 8);
        }
        setContentDescription(menuItemImpl.mContentDescription);
        String str2 = menuItemImpl.mBadgeText;
        if (this.mBadgeView == null) {
        }
        if (this.mBadgeView != null) {
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Drawable drawable = this.mBackground;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(this, drawable);
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
        this.mTextAppearance = obtainStyledAttributes.getResourceId(1, -1);
        this.mPreserveIconSpacing = obtainStyledAttributes.getBoolean(7, false);
        this.mTextAppearanceContext = context;
        this.mSubMenuArrow = obtainStyledAttributes.getDrawable(8);
        TypedArray obtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(null, new int[]{android.R.attr.divider}, R.attr.dropDownListViewStyle, 0);
        this.mHasListDivider = obtainStyledAttributes2.hasValue(0);
        obtainStyledAttributes.recycle();
        obtainStyledAttributes2.recycle();
        this.mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
    }
}
