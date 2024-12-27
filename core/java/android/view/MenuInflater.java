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

import com.android.internal.R;
import com.android.internal.view.menu.MenuItemImpl;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MenuInflater {
    private static final String LOG_TAG = "MenuInflater";
    private static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    private final Object[] mActionProviderConstructorArguments;
    private final Object[] mActionViewConstructorArguments;
    private Context mContext;
    private Object mRealOwner;
    private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE = {Context.class};
    private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE =
            ACTION_VIEW_CONSTRUCTOR_SIGNATURE;

    public MenuInflater(Context context) {
        this.mContext = context;
        this.mActionViewConstructorArguments = new Object[] {context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    public MenuInflater(Context context, Object realOwner) {
        this.mContext = context;
        this.mRealOwner = realOwner;
        this.mActionViewConstructorArguments = new Object[] {context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    public void inflate(int menuRes, Menu menu) {
        XmlResourceParser parser = null;
        try {
            try {
                parser = this.mContext.getResources().getLayout(menuRes);
                AttributeSet attrs = Xml.asAttributeSet(parser);
                parseMenu(parser, attrs, menu);
            } catch (IOException e) {
                throw new InflateException("Error inflating menu XML", e);
            } catch (XmlPullParserException e2) {
                throw new InflateException("Error inflating menu XML", e2);
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0047, code lost:

       switch(r1) {
           case 1: goto L58;
           case 2: goto L38;
           case 3: goto L17;
           default: goto L70;
       };
    */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x004c, code lost:

       r8 = r11.getName();
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0050, code lost:

       if (r2 == false) goto L22;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0056, code lost:

       if (r8.equals(r3) == false) goto L22;
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0058, code lost:

       r2 = false;
       r3 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00d2, code lost:

       r1 = r11.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0060, code lost:

       if (r8.equals(android.view.MenuInflater.XML_GROUP) == false) goto L25;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0062, code lost:

       r0.resetGroup();
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006b, code lost:

       if (r8.equals("item") == false) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0071, code lost:

       if (r0.hasAddedItem() != false) goto L61;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0077, code lost:

       if (r0.itemActionProvider == null) goto L34;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0081, code lost:

       if (r0.itemActionProvider.hasSubMenu() == false) goto L34;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0083, code lost:

       registerMenu(r0.addSubMenuItem(), r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008b, code lost:

       registerMenu(r0.addItem(), r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0097, code lost:

       if (r8.equals(android.view.MenuInflater.XML_MENU) == false) goto L64;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0099, code lost:

       r4 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x009b, code lost:

       if (r2 == false) goto L40;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x009e, code lost:

       r8 = r11.getName();
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a6, code lost:

       if (r8.equals(android.view.MenuInflater.XML_GROUP) == false) goto L43;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a8, code lost:

       r0.readGroup(r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b0, code lost:

       if (r8.equals("item") == false) goto L46;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00b2, code lost:

       r0.readItem(r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ba, code lost:

       if (r8.equals(android.view.MenuInflater.XML_MENU) == false) goto L49;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00bc, code lost:

       r6 = r0.addSubMenuItem();
       registerMenu(r6, r12);
       parseMenu(r11, r12, r6);
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00c7, code lost:

       r2 = true;
       r3 = r8;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00d1, code lost:

       throw new java.lang.RuntimeException("Unexpected end of document");
    */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00d8, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0040, code lost:

       r4 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0041, code lost:

       if (r4 != false) goto L57;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseMenu(
            org.xmlpull.v1.XmlPullParser r11, android.util.AttributeSet r12, android.view.Menu r13)
            throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r10 = this;
            android.view.MenuInflater$MenuState r0 = new android.view.MenuInflater$MenuState
            r0.<init>(r13)
            int r1 = r11.getEventType()
            r2 = 0
            r3 = 0
        Lb:
            r4 = 2
            java.lang.String r5 = "menu"
            if (r1 != r4) goto L39
            java.lang.String r4 = r11.getName()
            boolean r6 = r4.equals(r5)
            if (r6 == 0) goto L20
            int r1 = r11.next()
            goto L40
        L20:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Expecting menu, got "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r4)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L39:
            int r1 = r11.next()
            r4 = 1
            if (r1 != r4) goto Lb
        L40:
            r4 = 0
        L41:
            if (r4 != 0) goto Ld8
            java.lang.String r6 = "item"
            java.lang.String r7 = "group"
            switch(r1) {
                case 1: goto Lca;
                case 2: goto L9b;
                case 3: goto L4c;
                default: goto L4a;
            }
        L4a:
            goto Ld2
        L4c:
            java.lang.String r8 = r11.getName()
            if (r2 == 0) goto L5c
            boolean r9 = r8.equals(r3)
            if (r9 == 0) goto L5c
            r2 = 0
            r3 = 0
            goto Ld2
        L5c:
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L67
            r0.resetGroup()
            goto Ld2
        L67:
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L93
            boolean r6 = r0.hasAddedItem()
            if (r6 != 0) goto Ld2
            android.view.ActionProvider r6 = android.view.MenuInflater.MenuState.m5446$$Nest$fgetitemActionProvider(r0)
            if (r6 == 0) goto L8b
            android.view.ActionProvider r6 = android.view.MenuInflater.MenuState.m5446$$Nest$fgetitemActionProvider(r0)
            boolean r6 = r6.hasSubMenu()
            if (r6 == 0) goto L8b
            android.view.SubMenu r6 = r0.addSubMenuItem()
            r10.registerMenu(r6, r12)
            goto Ld2
        L8b:
            android.view.MenuItem r6 = r0.addItem()
            r10.registerMenu(r6, r12)
            goto Ld2
        L93:
            boolean r6 = r8.equals(r5)
            if (r6 == 0) goto Ld2
            r4 = 1
            goto Ld2
        L9b:
            if (r2 == 0) goto L9e
            goto Ld2
        L9e:
            java.lang.String r8 = r11.getName()
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto Lac
            r0.readGroup(r12)
            goto Ld2
        Lac:
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto Lb6
            r0.readItem(r12)
            goto Ld2
        Lb6:
            boolean r6 = r8.equals(r5)
            if (r6 == 0) goto Lc7
            android.view.SubMenu r6 = r0.addSubMenuItem()
            r10.registerMenu(r6, r12)
            r10.parseMenu(r11, r12, r6)
            goto Ld2
        Lc7:
            r2 = 1
            r3 = r8
            goto Ld2
        Lca:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.String r6 = "Unexpected end of document"
            r5.<init>(r6)
            throw r5
        Ld2:
            int r1 = r11.next()
            goto L41
        Ld8:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.MenuInflater.parseMenu(org.xmlpull.v1.XmlPullParser,"
                    + " android.util.AttributeSet, android.view.Menu):void");
    }

    private void registerMenu(MenuItem item, AttributeSet set) {}

    private void registerMenu(SubMenu subMenu, AttributeSet set) {}

    Context getContext() {
        return this.mContext;
    }

    private static class InflatedOnMenuItemClickListener
            implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = {MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object realOwner, String methodName) {
            this.mRealOwner = realOwner;
            Class<?> c = realOwner.getClass();
            try {
                this.mMethod = c.getMethod(methodName, PARAM_TYPES);
            } catch (Exception e) {
                InflateException ex =
                        new InflateException(
                                "Couldn't resolve menu item onClick handler "
                                        + methodName
                                        + " in class "
                                        + c.getName());
                ex.initCause(e);
                throw ex;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem item) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.mMethod.invoke(this.mRealOwner, item)).booleanValue();
                }
                this.mMethod.invoke(this.mRealOwner, item);
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

    private Object findRealOwner(Object owner) {
        if (owner instanceof Activity) {
            return owner;
        }
        if (owner instanceof ContextWrapper) {
            return findRealOwner(((ContextWrapper) owner).getBaseContext());
        }
        return owner;
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

        public void readGroup(AttributeSet attrs) {
            TypedArray a =
                    MenuInflater.this.mContext.obtainStyledAttributes(attrs, R.styleable.MenuGroup);
            this.groupId = a.getResourceId(1, 0);
            this.groupCategory = a.getInt(3, 0);
            this.groupOrder = a.getInt(4, 0);
            this.groupCheckable = a.getInt(5, 0);
            this.groupVisible = a.getBoolean(2, true);
            this.groupEnabled = a.getBoolean(0, true);
            a.recycle();
        }

        public void readItem(AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes =
                    MenuInflater.this.mContext.obtainStyledAttributes(
                            attributeSet, R.styleable.MenuItem);
            this.itemId = obtainStyledAttributes.getResourceId(2, 0);
            this.itemCategoryOrder =
                    ((-65536) & obtainStyledAttributes.getInt(5, this.groupCategory))
                            | (65535 & obtainStyledAttributes.getInt(6, this.groupOrder));
            this.itemTitle = obtainStyledAttributes.getText(7);
            this.itemTitleCondensed = obtainStyledAttributes.getText(8);
            this.itemIconResId = obtainStyledAttributes.getResourceId(0, 0);
            if (obtainStyledAttributes.hasValue(22)) {
                this.mItemIconBlendMode =
                        Drawable.parseBlendMode(
                                obtainStyledAttributes.getInt(22, -1), this.mItemIconBlendMode);
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
            this.itemActionProviderClassName = obtainStyledAttributes.getString(17);
            boolean z = this.itemActionProviderClassName != null;
            if (z && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider =
                        (ActionProvider)
                                newInstance(
                                        this.itemActionProviderClassName,
                                        MenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE,
                                        MenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if (z) {
                    Log.w(
                            MenuInflater.LOG_TAG,
                            "Ignoring attribute 'actionProviderClass'. Action view already"
                                + " specified.");
                }
                this.itemActionProvider = null;
            }
            this.itemContentDescription = obtainStyledAttributes.getText(13);
            this.itemTooltipText = obtainStyledAttributes.getText(18);
            obtainStyledAttributes.recycle();
            this.itemAdded = false;
        }

        private char getShortcut(String shortcutString) {
            if (shortcutString == null) {
                return (char) 0;
            }
            return shortcutString.charAt(0);
        }

        private void setItem(MenuItem item) {
            item.setChecked(this.itemChecked)
                    .setVisible(this.itemVisible)
                    .setEnabled(this.itemEnabled)
                    .setCheckable(this.itemCheckable >= 1)
                    .setTitleCondensed(this.itemTitleCondensed)
                    .setIcon(this.itemIconResId)
                    .setAlphabeticShortcut(
                            this.itemAlphabeticShortcut, this.itemAlphabeticModifiers)
                    .setNumericShortcut(this.itemNumericShortcut, this.itemNumericModifiers);
            if (this.itemShowAsAction >= 0) {
                item.setShowAsAction(this.itemShowAsAction);
            }
            if (this.mItemIconBlendMode != null) {
                item.setIconTintBlendMode(this.mItemIconBlendMode);
            }
            if (this.itemIconTintList != null) {
                item.setIconTintList(this.itemIconTintList);
            }
            if (this.itemListenerMethodName != null) {
                if (MenuInflater.this.mContext.isRestricted()) {
                    throw new IllegalStateException(
                            "The android:onClick attribute cannot be used within a restricted"
                                + " context");
                }
                item.setOnMenuItemClickListener(
                        new InflatedOnMenuItemClickListener(
                                MenuInflater.this.getRealOwner(), this.itemListenerMethodName));
            }
            if (item instanceof MenuItemImpl) {
                MenuItemImpl impl = (MenuItemImpl) item;
                if (this.itemCheckable >= 2) {
                    impl.setExclusiveCheckable(true);
                }
            }
            boolean actionViewSpecified = false;
            if (this.itemActionViewClassName != null) {
                View actionView =
                        (View)
                                newInstance(
                                        this.itemActionViewClassName,
                                        MenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE,
                                        MenuInflater.this.mActionViewConstructorArguments);
                item.setActionView(actionView);
                actionViewSpecified = true;
            }
            if (this.itemActionViewLayout > 0) {
                if (!actionViewSpecified) {
                    item.setActionView(this.itemActionViewLayout);
                } else {
                    Log.w(
                            MenuInflater.LOG_TAG,
                            "Ignoring attribute 'itemActionViewLayout'. Action view already"
                                + " specified.");
                }
            }
            if (this.itemActionProvider != null) {
                item.setActionProvider(this.itemActionProvider);
            }
            item.setContentDescription(this.itemContentDescription);
            item.setTooltipText(this.itemTooltipText);
        }

        public MenuItem addItem() {
            this.itemAdded = true;
            MenuItem item =
                    this.menu.add(
                            this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(item);
            return item;
        }

        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            SubMenu subMenu =
                    this.menu.addSubMenu(
                            this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(subMenu.getItem());
            return subMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        private <T> T newInstance(String str, Class<?>[] clsArr, Object[] objArr) {
            try {
                Constructor<?> constructor =
                        MenuInflater.this
                                .mContext
                                .getClassLoader()
                                .loadClass(str)
                                .getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (Exception e) {
                Log.w(MenuInflater.LOG_TAG, "Cannot instantiate class: " + str, e);
                return null;
            }
        }
    }
}
