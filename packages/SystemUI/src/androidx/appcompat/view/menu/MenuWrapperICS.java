package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenu;
import androidx.core.internal.view.SupportMenuItem;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MenuWrapperICS extends BaseMenuWrapper implements Menu {
    public final SupportMenu mWrappedObject;

    public MenuWrapperICS(Context context, SupportMenu supportMenu) {
        super(context);
        if (supportMenu == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.mWrappedObject = supportMenu;
    }

    @Override // android.view.Menu
    public final MenuItem add(CharSequence charSequence) {
        return getMenuItemWrapper(this.mWrappedObject.add(charSequence));
    }

    @Override // android.view.Menu
    public final int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2 = menuItemArr != null ? new MenuItem[menuItemArr.length] : null;
        int addIntentOptions = this.mWrappedObject.addIntentOptions(i, i2, i3, componentName, intentArr, intent, i4, menuItemArr2);
        if (menuItemArr2 != null) {
            int length = menuItemArr2.length;
            for (int i5 = 0; i5 < length; i5++) {
                menuItemArr[i5] = getMenuItemWrapper(menuItemArr2[i5]);
            }
        }
        return addIntentOptions;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(CharSequence charSequence) {
        return this.mWrappedObject.addSubMenu(charSequence);
    }

    @Override // android.view.Menu
    public final void clear() {
        SimpleArrayMap simpleArrayMap = this.mMenuItems;
        if (simpleArrayMap != null) {
            simpleArrayMap.clear();
        }
        this.mWrappedObject.clear();
    }

    @Override // android.view.Menu
    public final void close() {
        this.mWrappedObject.close();
    }

    @Override // android.view.Menu
    public final MenuItem findItem(int i) {
        return getMenuItemWrapper(this.mWrappedObject.findItem(i));
    }

    @Override // android.view.Menu
    public final MenuItem getItem(int i) {
        return getMenuItemWrapper(this.mWrappedObject.getItem(i));
    }

    @Override // android.view.Menu
    public final boolean hasVisibleItems() {
        return this.mWrappedObject.hasVisibleItems();
    }

    @Override // android.view.Menu
    public final boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return this.mWrappedObject.isShortcutKey(i, keyEvent);
    }

    @Override // android.view.Menu
    public final boolean performIdentifierAction(int i, int i2) {
        return this.mWrappedObject.performIdentifierAction(i, i2);
    }

    @Override // android.view.Menu
    public final boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        return this.mWrappedObject.performShortcut(i, keyEvent, i2);
    }

    @Override // android.view.Menu
    public final void removeGroup(int i) {
        if (this.mMenuItems != null) {
            int i2 = 0;
            while (true) {
                SimpleArrayMap simpleArrayMap = this.mMenuItems;
                if (i2 >= simpleArrayMap.size) {
                    break;
                }
                if (((SupportMenuItem) simpleArrayMap.keyAt(i2)).getGroupId() == i) {
                    this.mMenuItems.removeAt(i2);
                    i2--;
                }
                i2++;
            }
        }
        this.mWrappedObject.removeGroup(i);
    }

    @Override // android.view.Menu
    public final void removeItem(int i) {
        if (this.mMenuItems != null) {
            int i2 = 0;
            while (true) {
                SimpleArrayMap simpleArrayMap = this.mMenuItems;
                if (i2 >= simpleArrayMap.size) {
                    break;
                }
                if (((SupportMenuItem) simpleArrayMap.keyAt(i2)).getItemId() == i) {
                    this.mMenuItems.removeAt(i2);
                    break;
                }
                i2++;
            }
        }
        this.mWrappedObject.removeItem(i);
    }

    @Override // android.view.Menu
    public final void setGroupCheckable(int i, boolean z, boolean z2) {
        this.mWrappedObject.setGroupCheckable(i, z, z2);
    }

    @Override // android.view.Menu
    public final void setGroupEnabled(int i, boolean z) {
        this.mWrappedObject.setGroupEnabled(i, z);
    }

    @Override // android.view.Menu
    public final void setGroupVisible(int i, boolean z) {
        this.mWrappedObject.setGroupVisible(i, z);
    }

    @Override // android.view.Menu
    public final void setQwertyMode(boolean z) {
        this.mWrappedObject.setQwertyMode(z);
    }

    @Override // android.view.Menu
    public final int size() {
        return this.mWrappedObject.size();
    }

    @Override // android.view.Menu
    public final MenuItem add(int i) {
        return getMenuItemWrapper(this.mWrappedObject.add(i));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i) {
        return this.mWrappedObject.addSubMenu(i);
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return getMenuItemWrapper(this.mWrappedObject.add(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        return this.mWrappedObject.addSubMenu(i, i2, i3, charSequence);
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, int i4) {
        return getMenuItemWrapper(this.mWrappedObject.add(i, i2, i3, i4));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return this.mWrappedObject.addSubMenu(i, i2, i3, i4);
    }
}
