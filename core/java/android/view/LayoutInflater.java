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
    private static final String TAG = LayoutInflater.class.getSimpleName();
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

        FactoryMerger(Factory f1, Factory2 f12, Factory f2, Factory2 f22) {
            this.mF1 = f1;
            this.mF2 = f2;
            this.mF12 = f12;
            this.mF22 = f22;
        }

        @Override // android.view.LayoutInflater.Factory
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = this.mF1.onCreateView(name, context, attrs);
            return v != null ? v : this.mF2.onCreateView(name, context, attrs);
        }

        @Override // android.view.LayoutInflater.Factory2
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View v = this.mF12 != null ? this.mF12.onCreateView(parent, name, context, attrs) : this.mF1.onCreateView(name, context, attrs);
            return v != null ? v : this.mF22 != null ? this.mF22.onCreateView(parent, name, context, attrs) : this.mF2.onCreateView(name, context, attrs);
        }
    }

    protected LayoutInflater(Context context) {
        this.mConstructorArgs = new Object[2];
        StrictMode.assertConfigurationContext(context, "LayoutInflater");
        this.mContext = context;
    }

    protected LayoutInflater(LayoutInflater original, Context newContext) {
        this.mConstructorArgs = new Object[2];
        StrictMode.assertConfigurationContext(newContext, "LayoutInflater");
        this.mContext = newContext;
        this.mFactory = original.mFactory;
        this.mFactory2 = original.mFactory2;
        this.mPrivateFactory = original.mPrivateFactory;
        setFilter(original.mFilter);
    }

    public static LayoutInflater from(Context context) {
        LayoutInflater LayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (LayoutInflater == null) {
            throw new AssertionError("LayoutInflater not found.");
        }
        return LayoutInflater;
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

    public void setFactory2(Factory2 factory) {
        if (this.mFactorySet) {
            throw new IllegalStateException("A factory has already been set on this LayoutInflater");
        }
        if (factory == null) {
            throw new NullPointerException("Given factory can not be null");
        }
        this.mFactorySet = true;
        if (this.mFactory == null) {
            this.mFactory2 = factory;
            this.mFactory = factory;
        } else {
            FactoryMerger factoryMerger = new FactoryMerger(factory, factory, this.mFactory, this.mFactory2);
            this.mFactory2 = factoryMerger;
            this.mFactory = factoryMerger;
        }
    }

    public void setPrivateFactory(Factory2 factory) {
        if (this.mPrivateFactory == null) {
            this.mPrivateFactory = factory;
        } else {
            this.mPrivateFactory = new FactoryMerger(factory, factory, this.mPrivateFactory, this.mPrivateFactory);
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

    public View inflate(int resource, ViewGroup root) {
        return inflate(resource, root, root != null);
    }

    public View inflate(XmlPullParser parser, ViewGroup root) {
        return inflate(parser, root, root != null);
    }

    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        Resources res = getContext().getResources();
        XmlResourceParser parser = res.getLayout(resource);
        try {
            return inflate(parser, root, attachToRoot);
        } finally {
            parser.close();
        }
    }

    private void advanceToRootNode(XmlPullParser parser) throws InflateException, IOException, XmlPullParserException {
        int type;
        do {
            type = parser.next();
            if (type == 2) {
                break;
            }
        } while (type != 1);
        if (type != 2) {
            throw new InflateException(parser.getPositionDescription() + ": No start tag found!");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v5 */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    public View inflate(XmlPullParser xmlPullParser, ViewGroup viewGroup, boolean z) {
        String name;
        ?? r10;
        synchronized (this.mConstructorArgs) {
            try {
            } catch (Throwable th) {
                th = th;
            }
            try {
                ?? r102 = 8;
                r102 = 8;
                Trace.traceBegin(8L, "inflate");
                Context context = this.mContext;
                AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
                Context context2 = (Context) this.mConstructorArgs[0];
                this.mConstructorArgs[0] = context;
                View view = viewGroup;
                if (viewGroup != null && viewGroup.getViewRootImpl() != null) {
                    viewGroup.getViewRootImpl().notifyRendererOfExpensiveFrame();
                }
                try {
                    try {
                        advanceToRootNode(xmlPullParser);
                        name = xmlPullParser.getName();
                    } catch (XmlPullParserException e) {
                        e = e;
                    } catch (Exception e2) {
                        e = e2;
                    } catch (Throwable th2) {
                        th = th2;
                        r102 = 1;
                    }
                    try {
                        if (!TAG_MERGE.equals(name)) {
                            r10 = 1;
                            View createViewFromTag = createViewFromTag(viewGroup, name, context, asAttributeSet);
                            if (viewGroup == null && createViewFromTag != null && createViewFromTag.getViewRootImpl() != null) {
                                createViewFromTag.getViewRootImpl().notifyRendererOfExpensiveFrame();
                            }
                            ViewGroup.LayoutParams layoutParams = null;
                            if (viewGroup != null) {
                                layoutParams = viewGroup.generateLayoutParams(asAttributeSet);
                                if (!z) {
                                    createViewFromTag.setLayoutParams(layoutParams);
                                }
                            }
                            try {
                                rInflateChildren(xmlPullParser, createViewFromTag, asAttributeSet, true);
                                if (viewGroup != null && z) {
                                    viewGroup.addView(createViewFromTag, layoutParams);
                                }
                                if (viewGroup == null || !z) {
                                    view = createViewFromTag;
                                }
                            } catch (XmlPullParserException e3) {
                                e = e3;
                                InflateException inflateException = new InflateException(e.getMessage(), e);
                                inflateException.setStackTrace(EMPTY_STACK_TRACE);
                                throw inflateException;
                            } catch (Exception e4) {
                                e = e4;
                                InflateException inflateException2 = new InflateException(getParserStateDescription(context, asAttributeSet) + ": " + e.getMessage(), e);
                                inflateException2.setStackTrace(EMPTY_STACK_TRACE);
                                throw inflateException2;
                            }
                        } else {
                            if (viewGroup == null || !z) {
                                throw new InflateException("<merge /> can be used only with a valid ViewGroup root and attachToRoot=true");
                            }
                            r10 = 1;
                            rInflate(xmlPullParser, viewGroup, context, asAttributeSet, false);
                        }
                        this.mConstructorArgs[0] = context2;
                        this.mConstructorArgs[r10] = null;
                        Trace.traceEnd(8L);
                        idsUiUpdated();
                        return view;
                    } catch (XmlPullParserException e5) {
                        e = e5;
                    } catch (Exception e6) {
                        e = e6;
                    } catch (Throwable th3) {
                        th = th3;
                        this.mConstructorArgs[0] = context2;
                        this.mConstructorArgs[r102] = null;
                        Trace.traceEnd(8L);
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Throwable th5) {
                th = th5;
                throw th;
            }
        }
    }

    private void idsUiUpdated() {
        ActivityThread.currentActivityThread().getIdsController().uiUpdated(4);
    }

    private static String getParserStateDescription(Context context, AttributeSet attrs) {
        int sourceResId = Resources.getAttributeSetSourceResId(attrs);
        if (sourceResId == 0) {
            return attrs.getPositionDescription();
        }
        return attrs.getPositionDescription() + " in " + context.getResources().getResourceName(sourceResId);
    }

    private final boolean verifyClassLoader(Constructor<? extends View> constructor) {
        ClassLoader constructorLoader = constructor.getDeclaringClass().getClassLoader();
        if (constructorLoader == BOOT_CLASS_LOADER) {
            return true;
        }
        ClassLoader cl = this.mContext.getClassLoader();
        while (constructorLoader != cl) {
            cl = cl.getParent();
            if (cl == null) {
                return false;
            }
        }
        return true;
    }

    public final View createView(String name, String prefix, AttributeSet attrs) throws ClassNotFoundException, InflateException {
        Context context = (Context) this.mConstructorArgs[0];
        if (context == null) {
            context = this.mContext;
        }
        return createView(context, name, prefix, attrs);
    }

    public final View createView(Context viewContext, String name, String prefix, AttributeSet attrs) throws ClassNotFoundException, InflateException {
        Objects.requireNonNull(viewContext);
        Objects.requireNonNull(name);
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        if (constructor != null && !verifyClassLoader(constructor)) {
            constructor = null;
            sConstructorMap.remove(name);
        }
        Class<? extends View> clazz = null;
        try {
            try {
                try {
                    Trace.traceBegin(8L, name);
                    if (constructor == null) {
                        Class asSubclass = Class.forName(prefix != null ? prefix + name : name, false, this.mContext.getClassLoader()).asSubclass(View.class);
                        if (this.mFilter != null && asSubclass != null) {
                            if (!this.mFilter.onLoadClass(asSubclass)) {
                                failNotAllowed(name, prefix, viewContext, attrs);
                            }
                        }
                        constructor = asSubclass.getConstructor(mConstructorSignature);
                        constructor.setAccessible(true);
                        sConstructorMap.put(name, constructor);
                    } else if (this.mFilter != null) {
                        Boolean allowedState = this.mFilterMap.get(name);
                        if (allowedState == null) {
                            Class asSubclass2 = Class.forName(prefix != null ? prefix + name : name, false, this.mContext.getClassLoader()).asSubclass(View.class);
                            boolean allowed = asSubclass2 != null && this.mFilter.onLoadClass(asSubclass2);
                            this.mFilterMap.put(name, Boolean.valueOf(allowed));
                            if (!allowed) {
                                failNotAllowed(name, prefix, viewContext, attrs);
                            }
                        } else if (allowedState.equals(Boolean.FALSE)) {
                            failNotAllowed(name, prefix, viewContext, attrs);
                        }
                    }
                    Object lastContext = this.mConstructorArgs[0];
                    this.mConstructorArgs[0] = viewContext;
                    Object[] args = this.mConstructorArgs;
                    args[1] = attrs;
                    try {
                        View view = constructor.newInstance(args);
                        if (view instanceof ViewStub) {
                            ViewStub viewStub = (ViewStub) view;
                            viewStub.setLayoutInflater(cloneInContext((Context) args[0]));
                        }
                        return view;
                    } finally {
                        this.mConstructorArgs[0] = lastContext;
                    }
                } catch (ClassNotFoundException e) {
                    throw e;
                } catch (Exception e2) {
                    InflateException ie = new InflateException(getParserStateDescription(viewContext, attrs) + ": Error inflating class " + (0 == 0 ? "<unknown>" : clazz.getName()), e2);
                    ie.setStackTrace(EMPTY_STACK_TRACE);
                    throw ie;
                }
            } catch (ClassCastException e3) {
                InflateException ie2 = new InflateException(getParserStateDescription(viewContext, attrs) + ": Class is not a View " + (prefix != null ? prefix + name : name), e3);
                ie2.setStackTrace(EMPTY_STACK_TRACE);
                throw ie2;
            } catch (NoSuchMethodException e4) {
                InflateException ie3 = new InflateException(getParserStateDescription(viewContext, attrs) + ": Error inflating class " + (prefix != null ? prefix + name : name), e4);
                ie3.setStackTrace(EMPTY_STACK_TRACE);
                throw ie3;
            }
        } finally {
            Trace.traceEnd(8L);
        }
    }

    private void failNotAllowed(String name, String prefix, Context context, AttributeSet attrs) {
        throw new InflateException(getParserStateDescription(context, attrs) + ": Class not allowed to be inflated " + (prefix != null ? prefix + name : name));
    }

    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        return createView(name, "android.view.", attrs);
    }

    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return onCreateView(name, attrs);
    }

    public View onCreateView(Context viewContext, View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return onCreateView(parent, name, attrs);
    }

    private View createViewFromTag(View parent, String name, Context context, AttributeSet attrs) {
        return createViewFromTag(parent, name, context, attrs, false);
    }

    View createViewFromTag(View parent, String name, Context context, AttributeSet attrs, boolean ignoreThemeAttr) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        if (!ignoreThemeAttr) {
            TypedArray ta = context.obtainStyledAttributes(attrs, ATTRS_THEME);
            int themeResId = ta.getResourceId(0, 0);
            if (themeResId != 0) {
                context = new ContextThemeWrapper(context, themeResId);
            }
            ta.recycle();
        }
        try {
            View view = tryCreateView(parent, name, context, attrs);
            if (view == null) {
                Object lastContext = this.mConstructorArgs[0];
                this.mConstructorArgs[0] = context;
                try {
                    if (-1 == name.indexOf(46)) {
                        view = onCreateView(context, parent, name, attrs);
                    } else {
                        view = createView(context, name, null, attrs);
                    }
                    this.mConstructorArgs[0] = lastContext;
                } catch (Throwable th) {
                    this.mConstructorArgs[0] = lastContext;
                    throw th;
                }
            }
            return view;
        } catch (InflateException e) {
            throw e;
        } catch (ClassNotFoundException e2) {
            InflateException ie = new InflateException(getParserStateDescription(context, attrs) + ": Error inflating class " + name, e2);
            ie.setStackTrace(EMPTY_STACK_TRACE);
            throw ie;
        } catch (Exception e3) {
            InflateException ie2 = new InflateException(getParserStateDescription(context, attrs) + ": Error inflating class " + name, e3);
            ie2.setStackTrace(EMPTY_STACK_TRACE);
            throw ie2;
        }
    }

    public final View tryCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view;
        if (name.equals(TAG_1995)) {
            return new BlinkLayout(context, attrs);
        }
        if (this.mFactory2 != null) {
            view = this.mFactory2.onCreateView(parent, name, context, attrs);
        } else if (this.mFactory != null) {
            view = this.mFactory.onCreateView(name, context, attrs);
        } else {
            view = null;
        }
        if (view == null && this.mPrivateFactory != null) {
            View view2 = this.mPrivateFactory.onCreateView(parent, name, context, attrs);
            return view2;
        }
        return view;
    }

    final void rInflateChildren(XmlPullParser parser, View parent, AttributeSet attrs, boolean finishInflate) throws XmlPullParserException, IOException {
        rInflate(parser, parent, parent.getContext(), attrs, finishInflate);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x007d, code lost:
    
        r10.onFinishInflate();
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0080, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0076, code lost:
    
        if (r1 == false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0078, code lost:
    
        r10.restoreDefaultFocus();
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x007b, code lost:
    
        if (r13 == false) goto L53;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void rInflate(org.xmlpull.v1.XmlPullParser r9, android.view.View r10, android.content.Context r11, android.util.AttributeSet r12, boolean r13) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r8 = this;
            int r0 = r9.getDepth()
            r1 = 0
        L5:
            int r2 = r9.next()
            r3 = r2
            r4 = 3
            if (r2 != r4) goto L13
            int r2 = r9.getDepth()
            if (r2 <= r0) goto L76
        L13:
            r2 = 1
            if (r3 == r2) goto L76
            r4 = 2
            if (r3 == r4) goto L1a
            goto L5
        L1a:
            java.lang.String r4 = r9.getName()
            java.lang.String r5 = "requestFocus"
            boolean r5 = r5.equals(r4)
            if (r5 == 0) goto L2c
            r1 = 1
            consumeChildElements(r9)
            goto L6d
        L2c:
            java.lang.String r5 = "tag"
            boolean r5 = r5.equals(r4)
            if (r5 == 0) goto L39
            r8.parseViewTag(r9, r10, r12)
            goto L6d
        L39:
            java.lang.String r5 = "include"
            boolean r5 = r5.equals(r4)
            if (r5 == 0) goto L53
            int r2 = r9.getDepth()
            if (r2 == 0) goto L4b
            r8.parseInclude(r9, r11, r10, r12)
            goto L6d
        L4b:
            android.view.InflateException r2 = new android.view.InflateException
            java.lang.String r5 = "<include /> cannot be the root element"
            r2.<init>(r5)
            throw r2
        L53:
            java.lang.String r5 = "merge"
            boolean r5 = r5.equals(r4)
            if (r5 != 0) goto L6e
            android.view.View r5 = r8.createViewFromTag(r10, r4, r11, r12)
            r6 = r10
            android.view.ViewGroup r6 = (android.view.ViewGroup) r6
            android.view.ViewGroup$LayoutParams r7 = r6.generateLayoutParams(r12)
            r8.rInflateChildren(r9, r5, r12, r2)
            r6.addView(r5, r7)
        L6d:
            goto L5
        L6e:
            android.view.InflateException r2 = new android.view.InflateException
            java.lang.String r5 = "<merge /> must be the root element"
            r2.<init>(r5)
            throw r2
        L76:
            if (r1 == 0) goto L7b
            r10.restoreDefaultFocus()
        L7b:
            if (r13 == 0) goto L80
            r10.onFinishInflate()
        L80:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.LayoutInflater.rInflate(org.xmlpull.v1.XmlPullParser, android.view.View, android.content.Context, android.util.AttributeSet, boolean):void");
    }

    private void parseViewTag(XmlPullParser parser, View view, AttributeSet attrs) throws XmlPullParserException, IOException {
        Context context = view.getContext();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewTag);
        int key = ta.getResourceId(1, 0);
        CharSequence value = ta.getText(0);
        view.setTag(key, value);
        ta.recycle();
        consumeChildElements(parser);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [android.util.AttributeSet] */
    private void parseInclude(XmlPullParser parser, Context context, View parent, AttributeSet attrs) throws XmlPullParserException, IOException {
        XmlResourceParser childParser;
        int type;
        AttributeSet childAttrs;
        ViewGroup.LayoutParams params;
        XmlResourceParser childParser2;
        if (!(parent instanceof ViewGroup)) {
            throw new InflateException("<include /> can only be used inside of a ViewGroup");
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, ATTRS_THEME);
        int themeResId = ta.getResourceId(0, 0);
        boolean hasThemeOverride = themeResId != 0;
        Context context2 = hasThemeOverride ? new ContextThemeWrapper(context, themeResId) : context;
        ta.recycle();
        int layout = attrs.getAttributeResourceValue(null, "layout", 0);
        if (layout == 0) {
            String value = attrs.getAttributeValue(null, "layout");
            if (value == null || value.length() <= 0) {
                throw new InflateException("You must specify a layout in the include tag: <include layout=\"@layout/layoutID\" />");
            }
            layout = context2.getResources().getIdentifier(value.substring(1), "attr", context2.getPackageName());
        }
        if (this.mTempValue == null) {
            this.mTempValue = new TypedValue();
        }
        int layout2 = (layout == 0 || !context2.getTheme().resolveAttribute(layout, this.mTempValue, true)) ? layout : this.mTempValue.resourceId;
        if (layout2 == 0) {
            throw new InflateException("You must specify a valid layout reference. The layout ID " + attrs.getAttributeValue(null, "layout") + " is not valid.");
        }
        XmlResourceParser childParser3 = context2.getResources().getLayout(layout2);
        try {
            childParser = Xml.asAttributeSet(childParser3);
            try {
                do {
                    type = childParser3.next();
                    if (type != 2) {
                    }
                    break;
                } while (type != 1);
                break;
                if (type != 2) {
                    throw new InflateException(getParserStateDescription(context2, childParser) + ": No start tag found!");
                }
                String childName = childParser3.getName();
                if (TAG_MERGE.equals(childName)) {
                    try {
                        rInflate(childParser3, parent, context2, childParser, false);
                        childParser2 = childParser3;
                    } catch (Throwable th) {
                        th = th;
                        childParser = childParser3;
                        childParser.close();
                        throw th;
                    }
                } else {
                    try {
                        View view = createViewFromTag(parent, childName, context2, childParser, hasThemeOverride);
                        ViewGroup group = (ViewGroup) parent;
                        TypedArray a = context2.obtainStyledAttributes(attrs, R.styleable.Include);
                        int id = a.getResourceId(0, -1);
                        int visibility = a.getInt(1, -1);
                        a.recycle();
                        ViewGroup.LayoutParams params2 = null;
                        try {
                            try {
                                params2 = group.generateLayoutParams(attrs);
                            } catch (Throwable th2) {
                                th = th2;
                                childParser = childParser3;
                                childParser.close();
                                throw th;
                            }
                        } catch (RuntimeException e) {
                        }
                        if (params2 == null) {
                            childAttrs = childParser;
                            params = group.generateLayoutParams(childAttrs);
                        } else {
                            childAttrs = childParser;
                            params = params2;
                        }
                        view.setLayoutParams(params);
                        childParser2 = childParser3;
                        rInflateChildren(childParser2, view, childAttrs, true);
                        if (id != -1) {
                            view.setId(id);
                        }
                        switch (visibility) {
                            case 0:
                                view.setVisibility(0);
                                break;
                            case 1:
                                view.setVisibility(4);
                                break;
                            case 2:
                                view.setVisibility(8);
                                break;
                        }
                        group.addView(view);
                    } catch (Throwable th3) {
                        th = th3;
                        childParser = childParser3;
                    }
                }
                childParser2.close();
                consumeChildElements(parser);
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Throwable th5) {
            th = th5;
            childParser = childParser3;
        }
    }

    static final void consumeChildElements(XmlPullParser parser) throws XmlPullParserException, IOException {
        int type;
        int currentDepth = parser.getDepth();
        do {
            type = parser.next();
            if (type == 3 && parser.getDepth() <= currentDepth) {
                return;
            }
        } while (type != 1);
    }

    private static class BlinkLayout extends FrameLayout {
        private static final int BLINK_DELAY = 500;
        private static final int MESSAGE_BLINK = 66;
        private boolean mBlink;
        private boolean mBlinkState;
        private final Handler mHandler;

        public BlinkLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mHandler = new Handler(new Handler.Callback() { // from class: android.view.LayoutInflater.BlinkLayout.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message msg) {
                    if (msg.what == 66) {
                        if (BlinkLayout.this.mBlink) {
                            BlinkLayout.this.mBlinkState = !BlinkLayout.this.mBlinkState;
                            BlinkLayout.this.makeBlink();
                        }
                        BlinkLayout.this.invalidate();
                        return true;
                    }
                    return false;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void makeBlink() {
            Message message = this.mHandler.obtainMessage(66);
            this.mHandler.sendMessageDelayed(message, 500L);
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
