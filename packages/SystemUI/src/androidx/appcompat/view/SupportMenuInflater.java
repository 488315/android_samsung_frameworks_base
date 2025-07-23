package androidx.appcompat.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.internal.view.SupportMenu;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.view.ActionProvider;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SupportMenuInflater extends MenuInflater {
    public static final Class[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
    public static final Class[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    public final Object[] mActionProviderConstructorArguments;
    public final Object[] mActionViewConstructorArguments;
    public final Context mContext;
    public Object mRealOwner;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class InflatedOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        public static final Class[] PARAM_TYPES = {MenuItem.class};
        public final Method mMethod;
        public final Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object obj, String str) {
            this.mRealOwner = obj;
            Class<?> cls = obj.getClass();
            try {
                this.mMethod = cls.getMethod(str, PARAM_TYPES);
            } catch (Exception e) {
                StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Couldn't resolve menu item onClick handler ", str, " in class ");
                m.append(cls.getName());
                InflateException inflateException = new InflateException(m.toString());
                inflateException.initCause(e);
                throw inflateException;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public final boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.mMethod.invoke(this.mRealOwner, menuItem)).booleanValue();
                }
                this.mMethod.invoke(this.mRealOwner, menuItem);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MenuState {
        public ActionProvider itemActionProvider;
        public String itemActionViewClassName;
        public int itemActionViewLayout;
        public boolean itemAdded;
        public int itemAlphabeticModifiers;
        public char itemAlphabeticShortcut;
        public int itemCategoryOrder;
        public int itemCheckable;
        public boolean itemChecked;
        public CharSequence itemContentDescription;
        public boolean itemEnabled;
        public int itemIconResId;
        public int itemId;
        public String itemListenerMethodName;
        public int itemNumericModifiers;
        public char itemNumericShortcut;
        public int itemShowAsAction;
        public CharSequence itemTitle;
        public CharSequence itemTitleCondensed;
        public CharSequence itemTooltipText;
        public boolean itemVisible;
        public final Menu menu;
        public ColorStateList itemIconTintList = null;
        public PorterDuff.Mode itemIconTintMode = null;
        public int groupId = 0;
        public int groupCategory = 0;
        public int groupOrder = 0;
        public int groupCheckable = 0;
        public boolean groupVisible = true;
        public boolean groupEnabled = true;

        public MenuState(Menu menu) {
            this.menu = menu;
        }

        public final Object newInstance(String str, Class[] clsArr, Object[] objArr) {
            try {
                Constructor<?> constructor = Class.forName(str, false, SupportMenuInflater.this.mContext.getClassLoader()).getConstructor(clsArr);
                constructor.setAccessible(true);
                return constructor.newInstance(objArr);
            } catch (Exception e) {
                Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e);
                return null;
            }
        }

        public final void setItem(MenuItem menuItem) {
            boolean z = false;
            menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId);
            int i = this.itemShowAsAction;
            if (i >= 0) {
                menuItem.setShowAsAction(i);
            }
            String str = this.itemListenerMethodName;
            SupportMenuInflater supportMenuInflater = SupportMenuInflater.this;
            if (str != null) {
                if (supportMenuInflater.mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                if (supportMenuInflater.mRealOwner == null) {
                    supportMenuInflater.mRealOwner = SupportMenuInflater.findRealOwner(supportMenuInflater.mContext);
                }
                menuItem.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(supportMenuInflater.mRealOwner, this.itemListenerMethodName));
            }
            if (this.itemCheckable >= 2) {
                if (menuItem instanceof MenuItemImpl) {
                    MenuItemImpl menuItemImpl = (MenuItemImpl) menuItem;
                    menuItemImpl.mFlags = (menuItemImpl.mFlags & (-5)) | 4;
                } else if (menuItem instanceof MenuItemWrapperICS) {
                    MenuItemWrapperICS menuItemWrapperICS = (MenuItemWrapperICS) menuItem;
                    try {
                        if (menuItemWrapperICS.mSetExclusiveCheckableMethod == null) {
                            menuItemWrapperICS.mSetExclusiveCheckableMethod = menuItemWrapperICS.mWrappedObject.getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
                        }
                        menuItemWrapperICS.mSetExclusiveCheckableMethod.invoke(menuItemWrapperICS.mWrappedObject, Boolean.TRUE);
                    } catch (Exception e) {
                        Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", e);
                    }
                }
            }
            String str2 = this.itemActionViewClassName;
            if (str2 != null) {
                menuItem.setActionView((View) newInstance(str2, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, supportMenuInflater.mActionViewConstructorArguments));
                z = true;
            }
            int i2 = this.itemActionViewLayout;
            if (i2 > 0) {
                if (z) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                } else {
                    menuItem.setActionView(i2);
                }
            }
            ActionProvider actionProvider = this.itemActionProvider;
            if (actionProvider != null) {
                if (menuItem instanceof SupportMenuItem) {
                    ((SupportMenuItem) menuItem).setSupportActionProvider(actionProvider);
                } else {
                    Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
                }
            }
            CharSequence charSequence = this.itemContentDescription;
            boolean z2 = menuItem instanceof SupportMenuItem;
            if (z2) {
                ((SupportMenuItem) menuItem).setContentDescription(charSequence);
            } else {
                menuItem.setContentDescription(charSequence);
            }
            CharSequence charSequence2 = this.itemTooltipText;
            if (z2) {
                ((SupportMenuItem) menuItem).setTooltipText(charSequence2);
            } else {
                menuItem.setTooltipText(charSequence2);
            }
            char c = this.itemAlphabeticShortcut;
            int i3 = this.itemAlphabeticModifiers;
            if (z2) {
                ((SupportMenuItem) menuItem).setAlphabeticShortcut(c, i3);
            } else {
                menuItem.setAlphabeticShortcut(c, i3);
            }
            char c2 = this.itemNumericShortcut;
            int i4 = this.itemNumericModifiers;
            if (z2) {
                ((SupportMenuItem) menuItem).setNumericShortcut(c2, i4);
            } else {
                menuItem.setNumericShortcut(c2, i4);
            }
            PorterDuff.Mode mode = this.itemIconTintMode;
            if (mode != null) {
                if (z2) {
                    ((SupportMenuItem) menuItem).setIconTintMode(mode);
                } else {
                    menuItem.setIconTintMode(mode);
                }
            }
            ColorStateList colorStateList = this.itemIconTintList;
            if (colorStateList != null) {
                if (z2) {
                    ((SupportMenuItem) menuItem).setIconTintList(colorStateList);
                } else {
                    menuItem.setIconTintList(colorStateList);
                }
            }
            if (z2) {
                ((SupportMenuItem) menuItem).setSeslNaviMenuItemType();
            }
        }
    }

    static {
        Class[] clsArr = {Context.class};
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = clsArr;
        ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = clsArr;
    }

    public SupportMenuInflater(Context context) {
        super(context);
        this.mContext = context;
        Object[] objArr = {context};
        this.mActionViewConstructorArguments = objArr;
        this.mActionProviderConstructorArguments = objArr;
    }

    public static Object findRealOwner(Object obj) {
        return obj instanceof Activity ? obj : obj instanceof ContextWrapper ? findRealOwner(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    @Override // android.view.MenuInflater
    public final void inflate(int i, Menu menu) {
        if (!(menu instanceof SupportMenu)) {
            super.inflate(i, menu);
            return;
        }
        XmlResourceParser xmlResourceParser = null;
        boolean z = false;
        try {
            try {
                xmlResourceParser = this.mContext.getResources().getLayout(i);
                AttributeSet asAttributeSet = Xml.asAttributeSet(xmlResourceParser);
                if (menu instanceof MenuBuilder) {
                    MenuBuilder menuBuilder = (MenuBuilder) menu;
                    if (!menuBuilder.mPreventDispatchingItemsChanged) {
                        menuBuilder.stopDispatchingItemsChanged();
                        z = true;
                    }
                }
                parseMenu(xmlResourceParser, asAttributeSet, menu);
                if (z) {
                    ((MenuBuilder) menu).startDispatchingItemsChanged();
                }
                xmlResourceParser.close();
            } catch (IOException e) {
                throw new InflateException("Error inflating menu XML", e);
            } catch (XmlPullParserException e2) {
                throw new InflateException("Error inflating menu XML", e2);
            }
        } catch (Throwable th) {
            if (z) {
                ((MenuBuilder) menu).startDispatchingItemsChanged();
            }
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v64 */
    public final void parseMenu(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) {
        ?? r4;
        int i;
        XmlPullParser xmlPullParser2;
        boolean z;
        ColorStateList colorStateList;
        MenuState menuState = new MenuState(menu);
        int eventType = xmlPullParser.getEventType();
        while (true) {
            r4 = 1;
            i = 2;
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                if (!name.equals("menu")) {
                    throw new RuntimeException("Expecting menu, got ".concat(name));
                }
                eventType = xmlPullParser.next();
            } else {
                eventType = xmlPullParser.next();
                if (eventType == 1) {
                    break;
                }
            }
        }
        boolean z2 = false;
        boolean z3 = false;
        String str = null;
        while (!z2) {
            if (eventType == r4) {
                throw new RuntimeException("Unexpected end of document");
            }
            if (eventType != i) {
                if (eventType == 3) {
                    String name2 = xmlPullParser.getName();
                    if (z3 && name2.equals(str)) {
                        xmlPullParser2 = xmlPullParser;
                        z = r4;
                        z3 = false;
                        str = null;
                        eventType = xmlPullParser2.next();
                        r4 = z;
                        i = 2;
                        z3 = z3;
                    } else if (name2.equals("group")) {
                        menuState.groupId = 0;
                        menuState.groupCategory = 0;
                        menuState.groupOrder = 0;
                        menuState.groupCheckable = 0;
                        menuState.groupVisible = r4;
                        menuState.groupEnabled = r4;
                    } else if (name2.equals("item")) {
                        if (!menuState.itemAdded) {
                            ActionProvider actionProvider = menuState.itemActionProvider;
                            if (actionProvider == null || !actionProvider.hasSubMenu()) {
                                menuState.itemAdded = r4;
                                menuState.setItem(menuState.menu.add(menuState.groupId, menuState.itemId, menuState.itemCategoryOrder, menuState.itemTitle));
                            } else {
                                menuState.itemAdded = r4;
                                menuState.setItem(menuState.menu.addSubMenu(menuState.groupId, menuState.itemId, menuState.itemCategoryOrder, menuState.itemTitle).getItem());
                            }
                        }
                    } else if (name2.equals("menu")) {
                        xmlPullParser2 = xmlPullParser;
                        z2 = r4;
                        z = z2;
                    }
                }
                xmlPullParser2 = xmlPullParser;
                z = r4;
            } else {
                if (!z3) {
                    String name3 = xmlPullParser.getName();
                    boolean equals = name3.equals("group");
                    SupportMenuInflater supportMenuInflater = SupportMenuInflater.this;
                    if (equals) {
                        TypedArray obtainStyledAttributes = supportMenuInflater.mContext.obtainStyledAttributes(attributeSet, R$styleable.MenuGroup);
                        menuState.groupId = obtainStyledAttributes.getResourceId(r4, 0);
                        menuState.groupCategory = obtainStyledAttributes.getInt(3, 0);
                        menuState.groupOrder = obtainStyledAttributes.getInt(4, 0);
                        menuState.groupCheckable = obtainStyledAttributes.getInt(5, 0);
                        menuState.groupVisible = obtainStyledAttributes.getBoolean(2, r4);
                        menuState.groupEnabled = obtainStyledAttributes.getBoolean(0, r4);
                        obtainStyledAttributes.recycle();
                    } else {
                        if (name3.equals("item")) {
                            TintTypedArray obtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(supportMenuInflater.mContext, attributeSet, R$styleable.MenuItem);
                            menuState.itemId = obtainStyledAttributes2.mWrapped.getResourceId(2, 0);
                            menuState.itemCategoryOrder = (obtainStyledAttributes2.mWrapped.getInt(6, menuState.groupOrder) & CustomDeviceManager.QUICK_PANEL_ALL) | (obtainStyledAttributes2.mWrapped.getInt(5, menuState.groupCategory) & (-65536));
                            menuState.itemTitle = obtainStyledAttributes2.mWrapped.getText(7);
                            menuState.itemTitleCondensed = obtainStyledAttributes2.mWrapped.getText(8);
                            menuState.itemIconResId = obtainStyledAttributes2.mWrapped.getResourceId(0, 0);
                            String string = obtainStyledAttributes2.mWrapped.getString(9);
                            menuState.itemAlphabeticShortcut = string == null ? (char) 0 : string.charAt(0);
                            menuState.itemAlphabeticModifiers = obtainStyledAttributes2.mWrapped.getInt(16, 4096);
                            String string2 = obtainStyledAttributes2.mWrapped.getString(10);
                            menuState.itemNumericShortcut = string2 == null ? (char) 0 : string2.charAt(0);
                            menuState.itemNumericModifiers = obtainStyledAttributes2.mWrapped.getInt(20, 4096);
                            if (obtainStyledAttributes2.mWrapped.hasValue(11)) {
                                menuState.itemCheckable = obtainStyledAttributes2.mWrapped.getBoolean(11, false) ? 1 : 0;
                            } else {
                                menuState.itemCheckable = menuState.groupCheckable;
                            }
                            menuState.itemChecked = obtainStyledAttributes2.mWrapped.getBoolean(3, false);
                            menuState.itemVisible = obtainStyledAttributes2.mWrapped.getBoolean(4, menuState.groupVisible);
                            menuState.itemEnabled = obtainStyledAttributes2.mWrapped.getBoolean(1, menuState.groupEnabled);
                            menuState.itemShowAsAction = obtainStyledAttributes2.mWrapped.getInt(22, -1);
                            menuState.itemListenerMethodName = obtainStyledAttributes2.mWrapped.getString(12);
                            menuState.itemActionViewLayout = obtainStyledAttributes2.mWrapped.getResourceId(13, 0);
                            menuState.itemActionViewClassName = obtainStyledAttributes2.mWrapped.getString(15);
                            String string3 = obtainStyledAttributes2.mWrapped.getString(14);
                            boolean z4 = string3 != null;
                            if (z4 && menuState.itemActionViewLayout == 0 && menuState.itemActionViewClassName == null) {
                                menuState.itemActionProvider = (ActionProvider) menuState.newInstance(string3, ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, supportMenuInflater.mActionProviderConstructorArguments);
                            } else {
                                if (z4) {
                                    Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                                }
                                menuState.itemActionProvider = null;
                            }
                            menuState.itemContentDescription = obtainStyledAttributes2.mWrapped.getText(17);
                            menuState.itemTooltipText = obtainStyledAttributes2.mWrapped.getText(23);
                            if (obtainStyledAttributes2.mWrapped.hasValue(19)) {
                                menuState.itemIconTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes2.mWrapped.getInt(19, -1), menuState.itemIconTintMode);
                                colorStateList = null;
                            } else {
                                colorStateList = null;
                                menuState.itemIconTintMode = null;
                            }
                            if (obtainStyledAttributes2.mWrapped.hasValue(18)) {
                                menuState.itemIconTintList = obtainStyledAttributes2.getColorStateList(18);
                            } else {
                                menuState.itemIconTintList = colorStateList;
                            }
                            obtainStyledAttributes2.mWrapped.getInt(21, 0);
                            obtainStyledAttributes2.recycle();
                            menuState.itemAdded = false;
                            xmlPullParser2 = xmlPullParser;
                            z = true;
                        } else if (name3.equals("menu")) {
                            z = true;
                            menuState.itemAdded = true;
                            SubMenu addSubMenu = menuState.menu.addSubMenu(menuState.groupId, menuState.itemId, menuState.itemCategoryOrder, menuState.itemTitle);
                            menuState.setItem(addSubMenu.getItem());
                            xmlPullParser2 = xmlPullParser;
                            parseMenu(xmlPullParser2, attributeSet, addSubMenu);
                        } else {
                            xmlPullParser2 = xmlPullParser;
                            z = true;
                            str = name3;
                            z3 = true;
                        }
                        eventType = xmlPullParser2.next();
                        r4 = z;
                        i = 2;
                        z3 = z3;
                    }
                }
                xmlPullParser2 = xmlPullParser;
                z = r4;
            }
            eventType = xmlPullParser2.next();
            r4 = z;
            i = 2;
            z3 = z3;
        }
    }
}
