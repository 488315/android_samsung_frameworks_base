package com.android.internal.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActionMenu implements Menu {
    private Context mContext;
    private boolean mIsQwerty;
    private ArrayList<ActionMenuItem> mItems = new ArrayList<>();

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i) {
        return null;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return null;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        return null;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return null;
    }

    @Override // android.view.Menu
    public void close() {
    }

    public ActionMenu(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override // android.view.Menu
    public MenuItem add(CharSequence charSequence) {
        return add(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public MenuItem add(int i) {
        return add(0, 0, 0, i);
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, int i4) {
        return add(i, i2, i3, this.mContext.getResources().getString(i4));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        ActionMenuItem actionMenuItem = new ActionMenuItem(getContext(), i, i2, 0, i3, charSequence);
        this.mItems.add(i3, actionMenuItem);
        return actionMenuItem;
    }

    @Override // android.view.Menu
    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        PackageManager packageManager = this.mContext.getPackageManager();
        List<ResolveInfo> queryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = queryIntentActivityOptions != null ? queryIntentActivityOptions.size() : 0;
        if ((i4 & 1) == 0) {
            removeGroup(i);
        }
        for (int i5 = 0; i5 < size; i5++) {
            ResolveInfo resolveInfo = queryIntentActivityOptions.get(i5);
            Intent intent2 = new Intent(resolveInfo.specificIndex < 0 ? intent : intentArr[resolveInfo.specificIndex]);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            MenuItem intent3 = add(i, i2, i3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArr != null && resolveInfo.specificIndex >= 0) {
                menuItemArr[resolveInfo.specificIndex] = intent3;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public void clear() {
        this.mItems.clear();
    }

    private int findItemIndex(int i) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (arrayList.get(i2).getItemId() == i) {
                return i2;
            }
        }
        return -1;
    }

    @Override // android.view.Menu
    public MenuItem findItem(int i) {
        return this.mItems.get(findItemIndex(i));
    }

    @Override // android.view.Menu
    public MenuItem getItem(int i) {
        return this.mItems.get(i);
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (arrayList.get(i).isVisible()) {
                return true;
            }
        }
        return false;
    }

    private ActionMenuItem findItemWithShortcut(int i, KeyEvent keyEvent) {
        char numericShortcut;
        boolean z = this.mIsQwerty;
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        int modifiers = keyEvent.getModifiers();
        for (int i2 = 0; i2 < size; i2++) {
            ActionMenuItem actionMenuItem = arrayList.get(i2);
            if (z) {
                numericShortcut = actionMenuItem.getAlphabeticShortcut();
            } else {
                numericShortcut = actionMenuItem.getNumericShortcut();
            }
            boolean z2 = (modifiers & Menu.SUPPORTED_MODIFIERS_MASK) == ((z ? actionMenuItem.getAlphabeticModifiers() : actionMenuItem.getNumericModifiers()) & Menu.SUPPORTED_MODIFIERS_MASK);
            if (i == numericShortcut && z2) {
                return actionMenuItem;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return findItemWithShortcut(i, keyEvent) != null;
    }

    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        int findItemIndex = findItemIndex(i);
        if (findItemIndex < 0) {
            return false;
        }
        return this.mItems.get(findItemIndex).invoke();
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        ActionMenuItem findItemWithShortcut = findItemWithShortcut(i, keyEvent);
        if (findItemWithShortcut == null) {
            return false;
        }
        return findItemWithShortcut.invoke();
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            if (arrayList.get(i2).getGroupId() == i) {
                arrayList.remove(i2);
                size--;
            } else {
                i2++;
            }
        }
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        this.mItems.remove(findItemIndex(i));
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ActionMenuItem actionMenuItem = arrayList.get(i2);
            if (actionMenuItem.getGroupId() == i) {
                actionMenuItem.setCheckable(z);
                actionMenuItem.setExclusiveCheckable(z2);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ActionMenuItem actionMenuItem = arrayList.get(i2);
            if (actionMenuItem.getGroupId() == i) {
                actionMenuItem.setEnabled(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ActionMenuItem actionMenuItem = arrayList.get(i2);
            if (actionMenuItem.getGroupId() == i) {
                actionMenuItem.setVisible(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        this.mIsQwerty = z;
    }

    @Override // android.view.Menu
    public int size() {
        return this.mItems.size();
    }
}
