package android.view;

import android.app.ActivityThread;
import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.p009os.Handler;
import android.p009os.Message;
import android.p009os.StrictMode;
import android.p009os.Trace;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.C4337R;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public abstract class LayoutInflater {
    private static final String ATTR_LAYOUT = "layout";
    private static final String COMPILED_VIEW_DEX_FILE_NAME = "/compiled_view.dex";
    private static final boolean DEBUG = false;
    private static final String TAG_1995 = "blink";
    private static final String TAG_INCLUDE = "include";
    private static final String TAG_MERGE = "merge";
    private static final String TAG_REQUEST_FOCUS = "requestFocus";
    private static final String TAG_TAG = "tag";
    private static final String USE_PRECOMPILED_LAYOUT = "view.precompiled_layout_enabled";
    final Object[] mConstructorArgs;
    protected final Context mContext;
    private Factory mFactory;
    private Factory2 mFactory2;
    private boolean mFactorySet;
    private Filter mFilter;
    private HashMap<String, Boolean> mFilterMap;
    private ClassLoader mPrecompiledClassLoader;
    private Factory2 mPrivateFactory;
    private TypedValue mTempValue;
    private boolean mUseCompiledView;
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
            Factory2 factory2 = this.mF12;
            View v = factory2 != null ? factory2.onCreateView(parent, name, context, attrs) : this.mF1.onCreateView(name, context, attrs);
            if (v != null) {
                return v;
            }
            Factory2 factory22 = this.mF22;
            return factory22 != null ? factory22.onCreateView(parent, name, context, attrs) : this.mF2.onCreateView(name, context, attrs);
        }
    }

    protected LayoutInflater(Context context) {
        this.mConstructorArgs = new Object[2];
        StrictMode.assertConfigurationContext(context, "LayoutInflater");
        this.mContext = context;
        initPrecompiledViews();
    }

    protected LayoutInflater(LayoutInflater original, Context newContext) {
        this.mConstructorArgs = new Object[2];
        StrictMode.assertConfigurationContext(newContext, "LayoutInflater");
        this.mContext = newContext;
        this.mFactory = original.mFactory;
        this.mFactory2 = original.mFactory2;
        this.mPrivateFactory = original.mPrivateFactory;
        setFilter(original.mFilter);
        initPrecompiledViews();
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
        Factory factory2 = this.mFactory;
        if (factory2 == null) {
            this.mFactory = factory;
        } else {
            this.mFactory = new FactoryMerger(factory, null, factory2, this.mFactory2);
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
        Factory factory2 = this.mFactory;
        if (factory2 == null) {
            this.mFactory2 = factory;
            this.mFactory = factory;
        } else {
            FactoryMerger factoryMerger = new FactoryMerger(factory, factory, factory2, this.mFactory2);
            this.mFactory2 = factoryMerger;
            this.mFactory = factoryMerger;
        }
    }

    public void setPrivateFactory(Factory2 factory) {
        Factory2 factory2 = this.mPrivateFactory;
        if (factory2 == null) {
            this.mPrivateFactory = factory;
        } else {
            this.mPrivateFactory = new FactoryMerger(factory, factory, factory2, factory2);
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

    private void initPrecompiledViews() {
        initPrecompiledViews(false);
    }

    private void initPrecompiledViews(boolean enablePrecompiledViews) {
        this.mUseCompiledView = enablePrecompiledViews;
        if (!enablePrecompiledViews) {
            this.mPrecompiledClassLoader = null;
            return;
        }
        ApplicationInfo appInfo = this.mContext.getApplicationInfo();
        if (appInfo.isEmbeddedDexUsed() || appInfo.isPrivilegedApp()) {
            this.mUseCompiledView = false;
            return;
        }
        try {
            this.mPrecompiledClassLoader = this.mContext.getClassLoader();
            String dexFile = this.mContext.getCodeCacheDir() + COMPILED_VIEW_DEX_FILE_NAME;
            if (new File(dexFile).exists()) {
                this.mPrecompiledClassLoader = new PathClassLoader(dexFile, this.mPrecompiledClassLoader);
            } else {
                this.mUseCompiledView = false;
            }
        } catch (Throwable th) {
            this.mUseCompiledView = false;
        }
        if (!this.mUseCompiledView) {
            this.mPrecompiledClassLoader = null;
        }
    }

    public void setPrecompiledLayoutsEnabledForTesting(boolean enablePrecompiledLayouts) {
        initPrecompiledViews(enablePrecompiledLayouts);
    }

    public View inflate(int resource, ViewGroup root) {
        return inflate(resource, root, root != null);
    }

    public View inflate(XmlPullParser parser, ViewGroup root) {
        return inflate(parser, root, root != null);
    }

    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        Resources res = getContext().getResources();
        View view = tryInflatePrecompiled(resource, res, root, attachToRoot);
        if (view != null) {
            return view;
        }
        XmlResourceParser parser = res.getLayout(resource);
        try {
            return inflate(parser, root, attachToRoot);
        } finally {
            parser.close();
        }
    }

    private View tryInflatePrecompiled(int resource, Resources res, ViewGroup root, boolean attachToRoot) {
        if (!this.mUseCompiledView) {
            return null;
        }
        Trace.traceBegin(8L, "inflate (precompiled)");
        String pkg = res.getResourcePackageName(resource);
        String layout = res.getResourceEntryName(resource);
        try {
            Class clazz = Class.forName("" + pkg + ".CompiledView", false, this.mPrecompiledClassLoader);
            Method inflater = clazz.getMethod(layout, Context.class, Integer.TYPE);
            View view = (View) inflater.invoke(null, this.mContext, Integer.valueOf(resource));
            if (view != null && root != null) {
                XmlResourceParser parser = res.getLayout(resource);
                try {
                    AttributeSet attrs = Xml.asAttributeSet(parser);
                    advanceToRootNode(parser);
                    ViewGroup.LayoutParams params = root.generateLayoutParams(attrs);
                    if (attachToRoot) {
                        root.addView(view, params);
                    } else {
                        view.setLayoutParams(params);
                    }
                    parser.close();
                } catch (Throwable th) {
                    parser.close();
                    throw th;
                }
            }
            Trace.traceEnd(8L);
            return view;
        } catch (Throwable th2) {
            Trace.traceEnd(8L);
            return null;
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
                try {
                    ?? r102 = 8;
                    r102 = 8;
                    Trace.traceBegin(8L, "inflate");
                    Context context = this.mContext;
                    AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
                    Object[] objArr = this.mConstructorArgs;
                    Context context2 = (Context) objArr[0];
                    objArr[0] = context;
                    View view = viewGroup;
                    if (viewGroup != null && viewGroup.getViewRootImpl() != null) {
                        viewGroup.getViewRootImpl().notifyRendererOfExpensiveFrame();
                    }
                    try {
                        try {
                            advanceToRootNode(xmlPullParser);
                            name = xmlPullParser.getName();
                        } catch (Throwable th) {
                            th = th;
                        }
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
                        Object[] objArr2 = this.mConstructorArgs;
                        objArr2[0] = context2;
                        objArr2[r10] = null;
                        Trace.traceEnd(8L);
                        idsUiUpdated();
                        return view;
                    } catch (XmlPullParserException e5) {
                        e = e5;
                    } catch (Exception e6) {
                        e = e6;
                    } catch (Throwable th3) {
                        th = th3;
                        Object[] objArr3 = this.mConstructorArgs;
                        objArr3[0] = context2;
                        objArr3[r102] = null;
                        Trace.traceEnd(8L);
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
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
        HashMap<String, Constructor<? extends View>> hashMap = sConstructorMap;
        Constructor<? extends View> constructor = hashMap.get(name);
        if (constructor != null && !verifyClassLoader(constructor)) {
            constructor = null;
            hashMap.remove(name);
        }
        Class<? extends View> clazz = null;
        try {
            try {
                try {
                    try {
                        Trace.traceBegin(8L, name);
                        if (constructor == null) {
                            Class asSubclass = Class.forName(prefix != null ? prefix + name : name, false, this.mContext.getClassLoader()).asSubclass(View.class);
                            Filter filter = this.mFilter;
                            if (filter != null && asSubclass != null) {
                                if (!filter.onLoadClass(asSubclass)) {
                                    failNotAllowed(name, prefix, viewContext, attrs);
                                }
                            }
                            constructor = asSubclass.getConstructor(mConstructorSignature);
                            constructor.setAccessible(true);
                            hashMap.put(name, constructor);
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
                        Object[] args = this.mConstructorArgs;
                        Object lastContext = args[0];
                        args[0] = viewContext;
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
                    } catch (Exception e) {
                        InflateException ie = new InflateException(getParserStateDescription(viewContext, attrs) + ": Error inflating class " + (0 == 0 ? "<unknown>" : clazz.getName()), e);
                        ie.setStackTrace(EMPTY_STACK_TRACE);
                        throw ie;
                    }
                } catch (ClassCastException e2) {
                    InflateException ie2 = new InflateException(getParserStateDescription(viewContext, attrs) + ": Class is not a View " + (prefix != null ? prefix + name : name), e2);
                    ie2.setStackTrace(EMPTY_STACK_TRACE);
                    throw ie2;
                }
            } catch (ClassNotFoundException e3) {
                throw e3;
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
                Object[] objArr = this.mConstructorArgs;
                Object lastContext = objArr[0];
                objArr[0] = context;
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
        Factory2 factory2;
        if (name.equals(TAG_1995)) {
            return new BlinkLayout(context, attrs);
        }
        Factory2 factory22 = this.mFactory2;
        if (factory22 != null) {
            view = factory22.onCreateView(parent, name, context, attrs);
        } else {
            Factory factory = this.mFactory;
            if (factory != null) {
                view = factory.onCreateView(name, context, attrs);
            } else {
                view = null;
            }
        }
        if (view == null && (factory2 = this.mPrivateFactory) != null) {
            View view2 = factory2.onCreateView(parent, name, context, attrs);
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
    */
    void rInflate(XmlPullParser parser, View parent, Context context, AttributeSet attrs, boolean finishInflate) throws XmlPullParserException, IOException {
        int depth = parser.getDepth();
        boolean pendingRequestFocus = false;
        while (true) {
            int type = parser.next();
            if ((type != 3 || parser.getDepth() > depth) && type != 1) {
                if (type == 2) {
                    String name = parser.getName();
                    if (TAG_REQUEST_FOCUS.equals(name)) {
                        pendingRequestFocus = true;
                        consumeChildElements(parser);
                    } else if ("tag".equals(name)) {
                        parseViewTag(parser, parent, attrs);
                    } else if (TAG_INCLUDE.equals(name)) {
                        if (parser.getDepth() == 0) {
                            throw new InflateException("<include /> cannot be the root element");
                        }
                        parseInclude(parser, context, parent, attrs);
                    } else {
                        if (TAG_MERGE.equals(name)) {
                            throw new InflateException("<merge /> must be the root element");
                        }
                        View view = createViewFromTag(parent, name, context, attrs);
                        ViewGroup viewGroup = (ViewGroup) parent;
                        ViewGroup.LayoutParams params = viewGroup.generateLayoutParams(attrs);
                        rInflateChildren(parser, view, attrs, true);
                        viewGroup.addView(view, params);
                    }
                }
            }
        }
    }

    private void parseViewTag(XmlPullParser parser, View view, AttributeSet attrs) throws XmlPullParserException, IOException {
        Context context = view.getContext();
        TypedArray ta = context.obtainStyledAttributes(attrs, C4337R.styleable.ViewTag);
        int key = ta.getResourceId(1, 0);
        CharSequence value = ta.getText(0);
        view.setTag(key, value);
        ta.recycle();
        consumeChildElements(parser);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.util.AttributeSet] */
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
        View precompiled = tryInflatePrecompiled(layout2, context2.getResources(), (ViewGroup) parent, true);
        if (precompiled == null) {
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
                            TypedArray a = context2.obtainStyledAttributes(attrs, C4337R.styleable.Include);
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
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Throwable th5) {
                th = th5;
                childParser = childParser3;
            }
        }
        consumeChildElements(parser);
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
                            BlinkLayout.this.mBlinkState = !r0.mBlinkState;
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
