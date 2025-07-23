package android.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.BlendMode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import com.android.internal.R;
import com.android.internal.view.menu.MenuItemImpl;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class MenuInflater {
    private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
    private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    private static final String LOG_TAG = "MenuInflater";
    private static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    private final Object[] mActionProviderConstructorArguments;
    private final Object[] mActionViewConstructorArguments;
    private Context mContext;
    private Object mRealOwner;

    private void registerMenu(MenuItem menuItem, AttributeSet attributeSet) {
    }

    private void registerMenu(SubMenu subMenu, AttributeSet attributeSet) {
    }

    static {
        Class<?>[] clsArr = {Context.class};
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = clsArr;
        ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = clsArr;
    }

    public MenuInflater(Context context) {
        this.mContext = context;
        Object[] objArr = {context};
        this.mActionViewConstructorArguments = objArr;
        this.mActionProviderConstructorArguments = objArr;
    }

    public MenuInflater(Context context, Object obj) {
        this.mContext = context;
        this.mRealOwner = obj;
        Object[] objArr = {context};
        this.mActionViewConstructorArguments = objArr;
        this.mActionProviderConstructorArguments = objArr;
    }

    public void inflate(int i, Menu menu) {
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                try {
                    xmlResourceParser = this.mContext.getResources().getLayout(i);
                    parseMenu(xmlResourceParser, Xml.asAttributeSet(xmlResourceParser), menu);
                } catch (IOException e) {
                    throw new InflateException("Error inflating menu XML", e);
                }
            } catch (XmlPullParserException e2) {
                throw new InflateException("Error inflating menu XML", e2);
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0046, code lost:
    
        if (r15 == 2) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0049, code lost:
    
        if (r15 == 3) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x004d, code lost:
    
        r15 = r13.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0051, code lost:
    
        if (r7 == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0057, code lost:
    
        if (r15.equals(r8) == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0059, code lost:
    
        r7 = false;
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00ca, code lost:
    
        r15 = r13.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0061, code lost:
    
        if (r15.equals(android.view.MenuInflater.XML_GROUP) == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0063, code lost:
    
        r0.resetGroup();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006c, code lost:
    
        if (r15.equals("item") == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0072, code lost:
    
        if (r0.hasAddedItem() != false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0078, code lost:
    
        if (r0.itemActionProvider == null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0082, code lost:
    
        if (r0.itemActionProvider.hasSubMenu() == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0084, code lost:
    
        registerMenu(r0.addSubMenuItem(), r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x008c, code lost:
    
        registerMenu(r0.addItem(), r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0098, code lost:
    
        if (r15.equals(android.view.MenuInflater.XML_MENU) == false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x009a, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x009c, code lost:
    
        if (r7 == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009f, code lost:
    
        r15 = r13.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a7, code lost:
    
        if (r15.equals(android.view.MenuInflater.XML_GROUP) == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a9, code lost:
    
        r0.readGroup(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b1, code lost:
    
        if (r15.equals("item") == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b3, code lost:
    
        r0.readItem(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00bb, code lost:
    
        if (r15.equals(android.view.MenuInflater.XML_MENU) == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00bd, code lost:
    
        r15 = r0.addSubMenuItem();
        registerMenu(r15, r14);
        parseMenu(r13, r14, r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c8, code lost:
    
        r8 = r15;
        r7 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00d7, code lost:
    
        throw new java.lang.RuntimeException("Unexpected end of document");
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d8, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0039, code lost:
    
        r6 = false;
        r7 = false;
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x003e, code lost:
    
        if (r6 != false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0040, code lost:
    
        if (r15 == 1) goto L60;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseMenu(org.xmlpull.v1.XmlPullParser r13, android.util.AttributeSet r14, android.view.Menu r15) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r12 = this;
            android.view.MenuInflater$MenuState r0 = new android.view.MenuInflater$MenuState
            r0.<init>(r15)
            int r15 = r13.getEventType()
        L9:
            r1 = 2
            java.lang.String r2 = "menu"
            r3 = 1
            if (r15 != r1) goto L33
            java.lang.String r15 = r13.getName()
            boolean r4 = r15.equals(r2)
            if (r4 == 0) goto L1f
            int r15 = r13.next()
            goto L39
        L1f:
            java.lang.RuntimeException r12 = new java.lang.RuntimeException
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r14 = "Expecting menu, got "
            r13.<init>(r14)
            r13.append(r15)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L33:
            int r15 = r13.next()
            if (r15 != r3) goto L9
        L39:
            r4 = 0
            r5 = 0
            r6 = r4
            r7 = r6
            r8 = r5
        L3e:
            if (r6 != 0) goto Ld8
            if (r15 == r3) goto Ld0
            java.lang.String r9 = "item"
            java.lang.String r10 = "group"
            if (r15 == r1) goto L9c
            r11 = 3
            if (r15 == r11) goto L4d
            goto Lca
        L4d:
            java.lang.String r15 = r13.getName()
            if (r7 == 0) goto L5d
            boolean r11 = r15.equals(r8)
            if (r11 == 0) goto L5d
            r7 = r4
            r8 = r5
            goto Lca
        L5d:
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L68
            r0.resetGroup()
            goto Lca
        L68:
            boolean r9 = r15.equals(r9)
            if (r9 == 0) goto L94
            boolean r15 = r0.hasAddedItem()
            if (r15 != 0) goto Lca
            android.view.ActionProvider r15 = android.view.MenuInflater.MenuState.m5740$$Nest$fgetitemActionProvider(r0)
            if (r15 == 0) goto L8c
            android.view.ActionProvider r15 = android.view.MenuInflater.MenuState.m5740$$Nest$fgetitemActionProvider(r0)
            boolean r15 = r15.hasSubMenu()
            if (r15 == 0) goto L8c
            android.view.SubMenu r15 = r0.addSubMenuItem()
            r12.registerMenu(r15, r14)
            goto Lca
        L8c:
            android.view.MenuItem r15 = r0.addItem()
            r12.registerMenu(r15, r14)
            goto Lca
        L94:
            boolean r15 = r15.equals(r2)
            if (r15 == 0) goto Lca
            r6 = r3
            goto Lca
        L9c:
            if (r7 == 0) goto L9f
            goto Lca
        L9f:
            java.lang.String r15 = r13.getName()
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto Lad
            r0.readGroup(r14)
            goto Lca
        Lad:
            boolean r9 = r15.equals(r9)
            if (r9 == 0) goto Lb7
            r0.readItem(r14)
            goto Lca
        Lb7:
            boolean r9 = r15.equals(r2)
            if (r9 == 0) goto Lc8
            android.view.SubMenu r15 = r0.addSubMenuItem()
            r12.registerMenu(r15, r14)
            r12.parseMenu(r13, r14, r15)
            goto Lca
        Lc8:
            r8 = r15
            r7 = r3
        Lca:
            int r15 = r13.next()
            goto L3e
        Ld0:
            java.lang.RuntimeException r12 = new java.lang.RuntimeException
            java.lang.String r13 = "Unexpected end of document"
            r12.<init>(r13)
            throw r12
        Ld8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.MenuInflater.parseMenu(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.Menu):void");
    }

    Context getContext() {
        return this.mContext;
    }

    private static class InflatedOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = {MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object obj, String str) {
            this.mRealOwner = obj;
            Class<?> cls = obj.getClass();
            try {
                this.mMethod = cls.getMethod(str, PARAM_TYPES);
            } catch (Exception e) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e);
                throw inflateException;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = findRealOwner(this.mContext);
        }
        return this.mRealOwner;
    }

    private Object findRealOwner(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? findRealOwner(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        private ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private int itemAlphabeticModifiers;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private CharSequence itemContentDescription;
        private boolean itemEnabled;
        private int itemIconResId;
        private int itemId;
        private String itemListenerMethodName;
        private int itemNumericModifiers;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private CharSequence itemTooltipText;
        private boolean itemVisible;
        private Menu menu;
        private ColorStateList itemIconTintList = null;
        private BlendMode mItemIconBlendMode = null;

        public MenuState(Menu menu) {
            this.menu = menu;
            resetGroup();
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }

        public void readGroup(AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = MenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuGroup);
            this.groupId = obtainStyledAttributes.getResourceId(1, 0);
            this.groupCategory = obtainStyledAttributes.getInt(3, 0);
            this.groupOrder = obtainStyledAttributes.getInt(4, 0);
            this.groupCheckable = obtainStyledAttributes.getInt(5, 0);
            this.groupVisible = obtainStyledAttributes.getBoolean(2, true);
            this.groupEnabled = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
        }

        public void readItem(AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = MenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuItem);
            this.itemId = obtainStyledAttributes.getResourceId(2, 0);
            this.itemCategoryOrder = (obtainStyledAttributes.getInt(5, this.groupCategory) & (-65536)) | (obtainStyledAttributes.getInt(6, this.groupOrder) & 65535);
            this.itemTitle = obtainStyledAttributes.getText(7);
            this.itemTitleCondensed = obtainStyledAttributes.getText(8);
            this.itemIconResId = obtainStyledAttributes.getResourceId(0, 0);
            if (obtainStyledAttributes.hasValue(22)) {
                this.mItemIconBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(22, -1), this.mItemIconBlendMode);
            } else {
                this.mItemIconBlendMode = null;
            }
            if (obtainStyledAttributes.hasValue(21)) {
                this.itemIconTintList = obtainStyledAttributes.getColorStateList(21);
            } else {
                this.itemIconTintList = null;
            }
            this.itemAlphabeticShortcut = getShortcut(obtainStyledAttributes.getString(9));
            this.itemAlphabeticModifiers = obtainStyledAttributes.getInt(19, 4096);
            this.itemNumericShortcut = getShortcut(obtainStyledAttributes.getString(10));
            this.itemNumericModifiers = obtainStyledAttributes.getInt(20, 4096);
            if (obtainStyledAttributes.hasValue(11)) {
                this.itemCheckable = obtainStyledAttributes.getBoolean(11, false) ? 1 : 0;
            } else {
                this.itemCheckable = this.groupCheckable;
            }
            this.itemChecked = obtainStyledAttributes.getBoolean(3, false);
            this.itemVisible = obtainStyledAttributes.getBoolean(4, this.groupVisible);
            this.itemEnabled = obtainStyledAttributes.getBoolean(1, this.groupEnabled);
            this.itemShowAsAction = obtainStyledAttributes.getInt(14, -1);
            this.itemListenerMethodName = obtainStyledAttributes.getString(12);
            this.itemActionViewLayout = obtainStyledAttributes.getResourceId(15, 0);
            this.itemActionViewClassName = obtainStyledAttributes.getString(16);
            String string = obtainStyledAttributes.getString(17);
            this.itemActionProviderClassName = string;
            boolean z = string != null;
            if (z && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider) newInstance(string, MenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, MenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if (z) {
                    Log.w(MenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            this.itemContentDescription = obtainStyledAttributes.getText(13);
            this.itemTooltipText = obtainStyledAttributes.getText(18);
            obtainStyledAttributes.recycle();
            this.itemAdded = false;
        }

        private char getShortcut(String str) {
            if (str == null) {
                return (char) 0;
            }
            return str.charAt(0);
        }

        private void setItem(MenuItem menuItem) {
            boolean z = false;
            menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut, this.itemAlphabeticModifiers).setNumericShortcut(this.itemNumericShortcut, this.itemNumericModifiers);
            int i = this.itemShowAsAction;
            if (i >= 0) {
                menuItem.setShowAsAction(i);
            }
            BlendMode blendMode = this.mItemIconBlendMode;
            if (blendMode != null) {
                menuItem.setIconTintBlendMode(blendMode);
            }
            ColorStateList colorStateList = this.itemIconTintList;
            if (colorStateList != null) {
                menuItem.setIconTintList(colorStateList);
            }
            if (this.itemListenerMethodName != null) {
                if (MenuInflater.this.mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                menuItem.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(MenuInflater.this.getRealOwner(), this.itemListenerMethodName));
            }
            if (menuItem instanceof MenuItemImpl) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) menuItem;
                if (this.itemCheckable >= 2) {
                    menuItemImpl.setExclusiveCheckable(true);
                }
            }
            String str = this.itemActionViewClassName;
            if (str != null) {
                menuItem.setActionView((View) newInstance(str, MenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, MenuInflater.this.mActionViewConstructorArguments));
                z = true;
            }
            int i2 = this.itemActionViewLayout;
            if (i2 > 0) {
                if (!z) {
                    menuItem.setActionView(i2);
                } else {
                    Log.w(MenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            ActionProvider actionProvider = this.itemActionProvider;
            if (actionProvider != null) {
                menuItem.setActionProvider(actionProvider);
            }
            menuItem.setContentDescription(this.itemContentDescription);
            menuItem.setTooltipText(this.itemTooltipText);
        }

        public MenuItem addItem() {
            this.itemAdded = true;
            MenuItem add = this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(add);
            return add;
        }

        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            SubMenu addSubMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(addSubMenu.getItem());
            return addSubMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        private <T> T newInstance(String str, Class<?>[] clsArr, Object[] objArr) {
            try {
                Constructor<?> constructor = MenuInflater.this.mContext.getClassLoader().loadClass(str).getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (Exception e) {
                Log.w(MenuInflater.LOG_TAG, "Cannot instantiate class: " + str, e);
                return null;
            }
        }
    }
}
