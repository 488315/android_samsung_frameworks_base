package android.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;

/* loaded from: classes4.dex */
public interface MenuItem {
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;

    public interface OnActionExpandListener {
        boolean onMenuItemActionCollapse(MenuItem menuItem);

        boolean onMenuItemActionExpand(MenuItem menuItem);
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    boolean collapseActionView();

    boolean expandActionView();

    ActionProvider getActionProvider();

    View getActionView();

    default int getAlphabeticModifiers() {
        return 4096;
    }

    char getAlphabeticShortcut();

    default CharSequence getContentDescription() {
        return null;
    }

    int getGroupId();

    Drawable getIcon();

    default ColorStateList getIconTintList() {
        return null;
    }

    default PorterDuff.Mode getIconTintMode() {
        return null;
    }

    Intent getIntent();

    int getItemId();

    ContextMenu.ContextMenuInfo getMenuInfo();

    default int getNumericModifiers() {
        return 4096;
    }

    char getNumericShortcut();

    int getOrder();

    SubMenu getSubMenu();

    CharSequence getTitle();

    CharSequence getTitleCondensed();

    default CharSequence getTooltipText() {
        return null;
    }

    boolean hasSubMenu();

    boolean isActionViewExpanded();

    boolean isCheckable();

    boolean isChecked();

    boolean isEnabled();

    boolean isVisible();

    default boolean requiresActionButton() {
        return false;
    }

    default boolean requiresOverflow() {
        return true;
    }

    MenuItem setActionProvider(ActionProvider actionProvider);

    MenuItem setActionView(int i);

    MenuItem setActionView(View view);

    MenuItem setAlphabeticShortcut(char c);

    MenuItem setCheckable(boolean z);

    MenuItem setChecked(boolean z);

    default MenuItem setContentDescription(CharSequence charSequence) {
        return this;
    }

    MenuItem setEnabled(boolean z);

    MenuItem setIcon(int i);

    MenuItem setIcon(Drawable drawable);

    default MenuItem setIconTintList(ColorStateList colorStateList) {
        return this;
    }

    default MenuItem setIconTintMode(PorterDuff.Mode mode) {
        return this;
    }

    MenuItem setIntent(Intent intent);

    MenuItem setNumericShortcut(char c);

    MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener);

    MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener);

    MenuItem setShortcut(char c, char c2);

    void setShowAsAction(int i);

    MenuItem setShowAsActionFlags(int i);

    MenuItem setTitle(int i);

    MenuItem setTitle(CharSequence charSequence);

    MenuItem setTitleCondensed(CharSequence charSequence);

    default MenuItem setTooltipText(CharSequence charSequence) {
        return this;
    }

    MenuItem setVisible(boolean z);

    default MenuItem setIconTintBlendMode(BlendMode blendMode) {
        PorterDuff.Mode modeBlendModeToPorterDuffMode = BlendMode.blendModeToPorterDuffMode(blendMode);
        return modeBlendModeToPorterDuffMode != null ? setIconTintMode(modeBlendModeToPorterDuffMode) : this;
    }

    default BlendMode getIconTintBlendMode() {
        PorterDuff.Mode iconTintMode = getIconTintMode();
        if (iconTintMode != null) {
            return BlendMode.fromValue(iconTintMode.nativeInt);
        }
        return null;
    }

    default MenuItem setShortcut(char c, char c2, int i, int i2) {
        return ((i2 & Menu.SUPPORTED_MODIFIERS_MASK) == 4096 && (i & Menu.SUPPORTED_MODIFIERS_MASK) == 4096) ? setShortcut(c, c2) : this;
    }

    default MenuItem setNumericShortcut(char c, int i) {
        return (i & Menu.SUPPORTED_MODIFIERS_MASK) == 4096 ? setNumericShortcut(c) : this;
    }

    default MenuItem setAlphabeticShortcut(char c, int i) {
        return (i & Menu.SUPPORTED_MODIFIERS_MASK) == 4096 ? setAlphabeticShortcut(c) : this;
    }
}
