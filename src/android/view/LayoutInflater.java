package android.view;

import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.R;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public abstract class LayoutInflater {
    private static final String ATTR_LAYOUT = "layout";
    private static final boolean DEBUG = false;
    private static final String TAG = "LayoutInflater";
    private static final String TAG_1995 = "blink";
    private static final String TAG_INCLUDE = "include";
    private static final String TAG_MERGE = "merge";
    private static final String TAG_REQUEST_FOCUS = "requestFocus";
    private static final String TAG_TAG = "tag";
    final Object[] mConstructorArgs;
    protected final Context mContext;
    private Factory mFactory;
    private Factory2 mFactory2;
    private boolean mFactorySet;
    private Filter mFilter;
    private HashMap<String, Boolean> mFilterMap;
    private Factory2 mPrivateFactory;
    private TypedValue mTempValue;
    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
    static final Class<?>[] mConstructorSignature = {Context.class, AttributeSet.class};
    private static final HashMap<String, Constructor<? extends View>> sConstructorMap = new HashMap<>();
    private static final int[] ATTRS_THEME = {16842752};
    private static final ClassLoader BOOT_CLASS_LOADER = LayoutInflater.class.getClassLoader();

    public interface Factory {
        View onCreateView(String str, Context context, AttributeSet attributeSet);
    }

    public interface Factory2 extends Factory {
        View onCreateView(View view, String str, Context context, AttributeSet attributeSet);
    }

    public interface Filter {
        boolean onLoadClass(Class cls);
    }

    public abstract LayoutInflater cloneInContext(Context context);

    private static class FactoryMerger implements Factory2 {
        private final Factory mF1;
        private final Factory2 mF12;
        private final Factory mF2;
        private final Factory2 mF22;

        FactoryMerger(Factory factory, Factory2 factory2, Factory factory3, Factory2 factory22) {
            this.mF1 = factory;
            this.mF2 = factory3;
            this.mF12 = factory2;
            this.mF22 = factory22;
        }

        @Override // android.view.LayoutInflater.Factory
        public View onCreateView(String str, Context context, AttributeSet attributeSet) {
            View onCreateView = this.mF1.onCreateView(str, context, attributeSet);
            return onCreateView != null ? onCreateView : this.mF2.onCreateView(str, context, attributeSet);
        }

        @Override // android.view.LayoutInflater.Factory2
        public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
            Factory2 factory2 = this.mF12;
            View onCreateView = factory2 != null ? factory2.onCreateView(view, str, context, attributeSet) : this.mF1.onCreateView(str, context, attributeSet);
            if (onCreateView != null) {
                return onCreateView;
            }
            Factory2 factory22 = this.mF22;
            return factory22 != null ? factory22.onCreateView(view, str, context, attributeSet) : this.mF2.onCreateView(str, context, attributeSet);
        }
    }

    protected LayoutInflater(Context context) {
        this.mConstructorArgs = new Object[2];
        StrictMode.assertConfigurationContext(context, TAG);
        this.mContext = context;
    }

    protected LayoutInflater(LayoutInflater layoutInflater, Context context) {
        this.mConstructorArgs = new Object[2];
        StrictMode.assertConfigurationContext(context, TAG);
        this.mContext = context;
        this.mFactory = layoutInflater.mFactory;
        this.mFactory2 = layoutInflater.mFactory2;
        this.mPrivateFactory = layoutInflater.mPrivateFactory;
        setFilter(layoutInflater.mFilter);
    }

    public static LayoutInflater from(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            return layoutInflater;
        }
        throw new AssertionError("LayoutInflater not found.");
    }

    public Context getContext() {
        return this.mContext;
    }

    public final Factory getFactory() {
        return this.mFactory;
    }

    public final Factory2 getFactory2() {
        return this.mFactory2;
    }

    public void setFactory(Factory factory) {
        if (this.mFactorySet) {
            throw new IllegalStateException("A factory has already been set on this LayoutInflater");
        }
        if (factory == null) {
            throw new NullPointerException("Given factory can not be null");
        }
        this.mFactorySet = true;
        if (this.mFactory == null) {
            this.mFactory = factory;
        } else {
            this.mFactory = new FactoryMerger(factory, null, this.mFactory, this.mFactory2);
        }
    }

    public void setFactory2(Factory2 factory2) {
        if (this.mFactorySet) {
            throw new IllegalStateException("A factory has already been set on this LayoutInflater");
        }
        if (factory2 == null) {
            throw new NullPointerException("Given factory can not be null");
        }
        this.mFactorySet = true;
        if (this.mFactory == null) {
            this.mFactory2 = factory2;
            this.mFactory = factory2;
        } else {
            FactoryMerger factoryMerger = new FactoryMerger(factory2, factory2, this.mFactory, this.mFactory2);
            this.mFactory2 = factoryMerger;
            this.mFactory = factoryMerger;
        }
    }

    public void setPrivateFactory(Factory2 factory2) {
        if (this.mPrivateFactory == null) {
            this.mPrivateFactory = factory2;
        } else {
            Factory2 factory22 = this.mPrivateFactory;
            this.mPrivateFactory = new FactoryMerger(factory2, factory2, factory22, factory22);
        }
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    public void setFilter(Filter filter) {
        this.mFilter = filter;
        if (filter != null) {
            this.mFilterMap = new HashMap<>();
        }
    }

    public View inflate(int i, ViewGroup viewGroup) {
        return inflate(i, viewGroup, viewGroup != null);
    }

    public View inflate(XmlPullParser xmlPullParser, ViewGroup viewGroup) {
        return inflate(xmlPullParser, viewGroup, viewGroup != null);
    }

    public View inflate(int i, ViewGroup viewGroup, boolean z) {
        XmlResourceParser layout = getContext().getResources().getLayout(i);
        try {
            return inflate(layout, viewGroup, z);
        } finally {
            layout.close();
        }
    }

    private void advanceToRootNode(XmlPullParser xmlPullParser) throws InflateException, IOException, XmlPullParserException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return;
        }
        throw new InflateException(xmlPullParser.getPositionDescription() + ": No start tag found!");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x007e, code lost:
    
        if (r17 != false) goto L36;
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View inflate(org.xmlpull.v1.XmlPullParser r15, android.view.ViewGroup r16, boolean r17) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View");
    }

    private void idsUiUpdated() {
        ActivityThread.currentActivityThread().getIdsController().uiUpdated(4);
    }

    private static String getParserStateDescription(Context context, AttributeSet attributeSet) {
        int attributeSetSourceResId = Resources.getAttributeSetSourceResId(attributeSet);
        if (attributeSetSourceResId == 0) {
            return attributeSet.getPositionDescription();
        }
        return attributeSet.getPositionDescription() + " in " + context.getResources().getResourceName(attributeSetSourceResId);
    }

    private final boolean verifyClassLoader(Constructor<? extends View> constructor) {
        ClassLoader classLoader = constructor.getDeclaringClass().getClassLoader();
        if (classLoader == BOOT_CLASS_LOADER) {
            return true;
        }
        ClassLoader classLoader2 = this.mContext.getClassLoader();
        while (classLoader != classLoader2) {
            classLoader2 = classLoader2.getParent();
            if (classLoader2 == null) {
                return false;
            }
        }
        return true;
    }

    public final View createView(String str, String str2, AttributeSet attributeSet) throws ClassNotFoundException, InflateException {
        Context context = (Context) this.mConstructorArgs[0];
        if (context == null) {
            context = this.mContext;
        }
        return createView(context, str, str2, attributeSet);
    }

    public final View createView(Context context, String str, String str2, AttributeSet attributeSet) throws ClassNotFoundException, InflateException {
        String str3;
        String str4;
        Objects.requireNonNull(context);
        Objects.requireNonNull(str);
        HashMap<String, Constructor<? extends View>> hashMap = sConstructorMap;
        Constructor<? extends View> constructor = hashMap.get(str);
        Class cls = null;
        if (constructor != null && !verifyClassLoader(constructor)) {
            hashMap.remove(str);
            constructor = null;
        }
        try {
            try {
                try {
                    Trace.traceBegin(8L, str);
                    if (constructor == null) {
                        if (str2 != null) {
                            str4 = str2 + str;
                        } else {
                            str4 = str;
                        }
                        Class asSubclass = Class.forName(str4, false, this.mContext.getClassLoader()).asSubclass(View.class);
                        Filter filter = this.mFilter;
                        if (filter != null && asSubclass != null && !filter.onLoadClass(asSubclass)) {
                            failNotAllowed(str, str2, context, attributeSet);
                        }
                        constructor = asSubclass.getConstructor(mConstructorSignature);
                        constructor.setAccessible(true);
                        hashMap.put(str, constructor);
                    } else if (this.mFilter != null) {
                        Boolean bool = this.mFilterMap.get(str);
                        if (bool == null) {
                            if (str2 != null) {
                                str3 = str2 + str;
                            } else {
                                str3 = str;
                            }
                            Class asSubclass2 = Class.forName(str3, false, this.mContext.getClassLoader()).asSubclass(View.class);
                            boolean z = asSubclass2 != null && this.mFilter.onLoadClass(asSubclass2);
                            this.mFilterMap.put(str, Boolean.valueOf(z));
                            if (!z) {
                                failNotAllowed(str, str2, context, attributeSet);
                            }
                        } else if (bool.equals(Boolean.FALSE)) {
                            failNotAllowed(str, str2, context, attributeSet);
                        }
                    }
                    Object[] objArr = this.mConstructorArgs;
                    Object obj = objArr[0];
                    objArr[0] = context;
                    objArr[1] = attributeSet;
                    try {
                        View newInstance = constructor.newInstance(objArr);
                        if (newInstance instanceof ViewStub) {
                            ((ViewStub) newInstance).setLayoutInflater(cloneInContext((Context) objArr[0]));
                        }
                        return newInstance;
                    } finally {
                        this.mConstructorArgs[0] = obj;
                    }
                } catch (ClassCastException e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(getParserStateDescription(context, attributeSet));
                    sb.append(": Class is not a View ");
                    if (str2 != null) {
                        str = str2 + str;
                    }
                    sb.append(str);
                    InflateException inflateException = new InflateException(sb.toString(), e);
                    inflateException.setStackTrace(EMPTY_STACK_TRACE);
                    throw inflateException;
                } catch (NoSuchMethodException e2) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(getParserStateDescription(context, attributeSet));
                    sb2.append(": Error inflating class ");
                    if (str2 != null) {
                        str = str2 + str;
                    }
                    sb2.append(str);
                    InflateException inflateException2 = new InflateException(sb2.toString(), e2);
                    inflateException2.setStackTrace(EMPTY_STACK_TRACE);
                    throw inflateException2;
                }
            } catch (ClassNotFoundException e3) {
                throw e3;
            } catch (Exception e4) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getParserStateDescription(context, attributeSet));
                sb3.append(": Error inflating class ");
                sb3.append(0 == 0 ? "<unknown>" : cls.getName());
                InflateException inflateException3 = new InflateException(sb3.toString(), e4);
                inflateException3.setStackTrace(EMPTY_STACK_TRACE);
                throw inflateException3;
            }
        } finally {
            Trace.traceEnd(8L);
        }
    }

    private void failNotAllowed(String str, String str2, Context context, AttributeSet attributeSet) {
        StringBuilder sb = new StringBuilder();
        sb.append(getParserStateDescription(context, attributeSet));
        sb.append(": Class not allowed to be inflated ");
        if (str2 != null) {
            str = str2 + str;
        }
        sb.append(str);
        throw new InflateException(sb.toString());
    }

    protected View onCreateView(String str, AttributeSet attributeSet) throws ClassNotFoundException {
        return createView(str, "android.view.", attributeSet);
    }

    protected View onCreateView(View view, String str, AttributeSet attributeSet) throws ClassNotFoundException {
        return onCreateView(str, attributeSet);
    }

    public View onCreateView(Context context, View view, String str, AttributeSet attributeSet) throws ClassNotFoundException {
        return onCreateView(view, str, attributeSet);
    }

    private View createViewFromTag(View view, String str, Context context, AttributeSet attributeSet) {
        return createViewFromTag(view, str, context, attributeSet, false);
    }

    View createViewFromTag(View view, String str, Context context, AttributeSet attributeSet, boolean z) {
        View createView;
        if (str.equals("view")) {
            str = attributeSet.getAttributeValue(null, "class");
        }
        if (!z) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS_THEME);
            int resourceId = obtainStyledAttributes.getResourceId(0, 0);
            if (resourceId != 0) {
                context = new ContextThemeWrapper(context, resourceId);
            }
            obtainStyledAttributes.recycle();
        }
        try {
            View tryCreateView = tryCreateView(view, str, context, attributeSet);
            if (tryCreateView != null) {
                return tryCreateView;
            }
            Object[] objArr = this.mConstructorArgs;
            Object obj = objArr[0];
            objArr[0] = context;
            try {
                if (-1 == str.indexOf(46)) {
                    createView = onCreateView(context, view, str, attributeSet);
                } else {
                    createView = createView(context, str, null, attributeSet);
                }
                return createView;
            } finally {
                this.mConstructorArgs[0] = obj;
            }
        } catch (InflateException e) {
            throw e;
        } catch (ClassNotFoundException e2) {
            InflateException inflateException = new InflateException(getParserStateDescription(context, attributeSet) + ": Error inflating class " + str, e2);
            inflateException.setStackTrace(EMPTY_STACK_TRACE);
            throw inflateException;
        } catch (Exception e3) {
            InflateException inflateException2 = new InflateException(getParserStateDescription(context, attributeSet) + ": Error inflating class " + str, e3);
            inflateException2.setStackTrace(EMPTY_STACK_TRACE);
            throw inflateException2;
        }
    }

    public final View tryCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View onCreateView;
        Factory2 factory2;
        if (str.equals(TAG_1995)) {
            return new BlinkLayout(context, attributeSet);
        }
        Factory2 factory22 = this.mFactory2;
        if (factory22 != null) {
            onCreateView = factory22.onCreateView(view, str, context, attributeSet);
        } else {
            Factory factory = this.mFactory;
            onCreateView = factory != null ? factory.onCreateView(str, context, attributeSet) : null;
        }
        return (onCreateView != null || (factory2 = this.mPrivateFactory) == null) ? onCreateView : factory2.onCreateView(view, str, context, attributeSet);
    }

    final void rInflateChildren(XmlPullParser xmlPullParser, View view, AttributeSet attributeSet, boolean z) throws XmlPullParserException, IOException {
        rInflate(xmlPullParser, view, view.getContext(), attributeSet, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x007c, code lost:
    
        r8.onFinishInflate();
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x007f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0075, code lost:
    
        if (r1 == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0077, code lost:
    
        r8.restoreDefaultFocus();
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x007a, code lost:
    
        if (r11 == false) goto L52;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void rInflate(org.xmlpull.v1.XmlPullParser r7, android.view.View r8, android.content.Context r9, android.util.AttributeSet r10, boolean r11) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r6 = this;
            int r0 = r7.getDepth()
            r1 = 0
        L5:
            int r2 = r7.next()
            r3 = 3
            if (r2 != r3) goto L12
            int r3 = r7.getDepth()
            if (r3 <= r0) goto L75
        L12:
            r3 = 1
            if (r2 == r3) goto L75
            r4 = 2
            if (r2 == r4) goto L19
            goto L5
        L19:
            java.lang.String r2 = r7.getName()
            java.lang.String r4 = "requestFocus"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L2b
            consumeChildElements(r7)
            r1 = r3
            goto L5
        L2b:
            java.lang.String r4 = "tag"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L38
            r6.parseViewTag(r7, r8, r10)
            goto L5
        L38:
            java.lang.String r4 = "include"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L52
            int r2 = r7.getDepth()
            if (r2 == 0) goto L4a
            r6.parseInclude(r7, r9, r8, r10)
            goto L5
        L4a:
            android.view.InflateException r6 = new android.view.InflateException
            java.lang.String r7 = "<include /> cannot be the root element"
            r6.<init>(r7)
            throw r6
        L52:
            java.lang.String r4 = "merge"
            boolean r4 = r4.equals(r2)
            if (r4 != 0) goto L6d
            android.view.View r2 = r6.createViewFromTag(r8, r2, r9, r10)
            r4 = r8
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            android.view.ViewGroup$LayoutParams r5 = r4.generateLayoutParams(r10)
            r6.rInflateChildren(r7, r2, r10, r3)
            r4.addView(r2, r5)
            goto L5
        L6d:
            android.view.InflateException r6 = new android.view.InflateException
            java.lang.String r7 = "<merge /> must be the root element"
            r6.<init>(r7)
            throw r6
        L75:
            if (r1 == 0) goto L7a
            r8.restoreDefaultFocus()
        L7a:
            if (r11 == 0) goto L7f
            r8.onFinishInflate()
        L7f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.LayoutInflater.rInflate(org.xmlpull.v1.XmlPullParser, android.view.View, android.content.Context, android.util.AttributeSet, boolean):void");
    }

    private void parseViewTag(XmlPullParser xmlPullParser, View view, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.ViewTag);
        view.setTag(obtainStyledAttributes.getResourceId(1, 0), obtainStyledAttributes.getText(0));
        obtainStyledAttributes.recycle();
        consumeChildElements(xmlPullParser);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2, types: [android.content.res.XmlResourceParser] */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v6, types: [android.content.res.Resources] */
    private void parseInclude(XmlPullParser xmlPullParser, Context context, View view, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        ?? r11;
        int next;
        XmlResourceParser xmlResourceParser;
        if (!(view instanceof ViewGroup)) {
            throw new InflateException("<include /> can only be used inside of a ViewGroup");
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS_THEME);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        boolean z = resourceId != 0;
        Context contextThemeWrapper = z ? new ContextThemeWrapper(context, resourceId) : context;
        obtainStyledAttributes.recycle();
        ViewGroup.LayoutParams layoutParams = null;
        int attributeResourceValue = attributeSet.getAttributeResourceValue(null, "layout", 0);
        if (attributeResourceValue == 0) {
            String attributeValue = attributeSet.getAttributeValue(null, "layout");
            if (attributeValue == null || attributeValue.length() <= 0) {
                throw new InflateException("You must specify a layout in the include tag: <include layout=\"@layout/layoutID\" />");
            }
            ?? resources = contextThemeWrapper.getResources();
            String substring = attributeValue.substring(1);
            r11 = contextThemeWrapper.getPackageName();
            attributeResourceValue = resources.getIdentifier(substring, "attr", r11);
        }
        if (this.mTempValue == null) {
            this.mTempValue = new TypedValue();
        }
        if (attributeResourceValue != 0 && contextThemeWrapper.getTheme().resolveAttribute(attributeResourceValue, this.mTempValue, true)) {
            attributeResourceValue = this.mTempValue.resourceId;
        }
        if (attributeResourceValue == 0) {
            throw new InflateException("You must specify a valid layout reference. The layout ID " + attributeSet.getAttributeValue(null, "layout") + " is not valid.");
        }
        XmlResourceParser layout = contextThemeWrapper.getResources().getLayout(attributeResourceValue);
        try {
            AttributeSet asAttributeSet = Xml.asAttributeSet(layout);
            try {
                do {
                    next = layout.next();
                    if (next != 2) {
                    }
                    break;
                } while (next != 1);
                break;
                if (next != 2) {
                    throw new InflateException(getParserStateDescription(contextThemeWrapper, asAttributeSet) + ": No start tag found!");
                }
                try {
                    String name = layout.getName();
                    if (TAG_MERGE.equals(name)) {
                        rInflate(layout, view, contextThemeWrapper, asAttributeSet, false);
                        xmlResourceParser = layout;
                    } else {
                        xmlResourceParser = layout;
                        View createViewFromTag = createViewFromTag(view, name, contextThemeWrapper, asAttributeSet, z);
                        ViewGroup viewGroup = (ViewGroup) view;
                        TypedArray obtainStyledAttributes2 = contextThemeWrapper.obtainStyledAttributes(attributeSet, R.styleable.Include);
                        int resourceId2 = obtainStyledAttributes2.getResourceId(0, -1);
                        int i = obtainStyledAttributes2.getInt(1, -1);
                        obtainStyledAttributes2.recycle();
                        try {
                            layoutParams = viewGroup.generateLayoutParams(attributeSet);
                        } catch (RuntimeException unused) {
                        }
                        if (layoutParams == null) {
                            layoutParams = viewGroup.generateLayoutParams(asAttributeSet);
                        }
                        createViewFromTag.setLayoutParams(layoutParams);
                        rInflateChildren(xmlResourceParser, createViewFromTag, asAttributeSet, true);
                        if (resourceId2 != -1) {
                            createViewFromTag.setId(resourceId2);
                        }
                        if (i == 0) {
                            createViewFromTag.setVisibility(0);
                        } else if (i == 1) {
                            createViewFromTag.setVisibility(4);
                        } else if (i == 2) {
                            createViewFromTag.setVisibility(8);
                        }
                        viewGroup.addView(createViewFromTag);
                    }
                    xmlResourceParser.close();
                    consumeChildElements(xmlPullParser);
                } catch (Throwable th) {
                    th = th;
                    r11 = layout;
                    Throwable th2 = th;
                    r11.close();
                    throw th2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            r11 = layout;
        }
    }

    static final void consumeChildElements(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next;
        int depth = xmlPullParser.getDepth();
        do {
            next = xmlPullParser.next();
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
        } while (next != 1);
    }

    private static class BlinkLayout extends FrameLayout {
        private static final int BLINK_DELAY = 500;
        private static final int MESSAGE_BLINK = 66;
        private boolean mBlink;
        private boolean mBlinkState;
        private final Handler mHandler;

        public BlinkLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mHandler = new Handler(new Handler.Callback() { // from class: android.view.LayoutInflater.BlinkLayout.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    if (message.what != 66) {
                        return false;
                    }
                    if (BlinkLayout.this.mBlink) {
                        BlinkLayout.this.mBlinkState = !r3.mBlinkState;
                        BlinkLayout.this.makeBlink();
                    }
                    BlinkLayout.this.invalidate();
                    return true;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void makeBlink() {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(66), 500L);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.mBlink = true;
            this.mBlinkState = true;
            makeBlink();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.mBlink = false;
            this.mBlinkState = true;
            this.mHandler.removeMessages(66);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (this.mBlinkState) {
                super.dispatchDraw(canvas);
            }
        }
    }
}
