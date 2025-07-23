package android.transition;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;
import com.android.internal.R;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class TransitionInflater {
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final ArrayMap<String, Constructor> sConstructors = new ArrayMap<>();
    private Context mContext;

    private TransitionInflater(Context context) {
        this.mContext = context;
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    public Transition inflateTransition(int i) {
        XmlResourceParser xml = this.mContext.getResources().getXml(i);
        try {
            try {
                return createTransitionFromXml(xml, Xml.asAttributeSet(xml), null);
            } catch (IOException e) {
                InflateException inflateException = new InflateException(xml.getPositionDescription() + ": " + e.getMessage());
                inflateException.initCause(e);
                throw inflateException;
            } catch (XmlPullParserException e2) {
                InflateException inflateException2 = new InflateException(e2.getMessage());
                inflateException2.initCause(e2);
                throw inflateException2;
            }
        } finally {
            xml.close();
        }
    }

    public TransitionManager inflateTransitionManager(int i, ViewGroup viewGroup) {
        XmlResourceParser xml = this.mContext.getResources().getXml(i);
        try {
            try {
                return createTransitionManagerFromXml(xml, Xml.asAttributeSet(xml), viewGroup);
            } catch (IOException e) {
                InflateException inflateException = new InflateException(xml.getPositionDescription() + ": " + e.getMessage());
                inflateException.initCause(e);
                throw inflateException;
            } catch (XmlPullParserException e2) {
                InflateException inflateException2 = new InflateException(e2.getMessage());
                inflateException2.initCause(e2);
                throw inflateException2;
            }
        } finally {
            xml.close();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0173, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.transition.Transition createTransitionFromXml(org.xmlpull.v1.XmlPullParser r8, android.util.AttributeSet r9, android.transition.Transition r10) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.TransitionInflater.createTransitionFromXml(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.transition.Transition):android.transition.Transition");
    }

    private Object createCustom(AttributeSet attributeSet, Class cls, String str) {
        Object newInstance;
        Class<? extends U> asSubclass;
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        if (attributeValue == null) {
            throw new InflateException(str + " tag must have a 'class' attribute");
        }
        try {
            ArrayMap<String, Constructor> arrayMap = sConstructors;
            synchronized (arrayMap) {
                Constructor constructor = arrayMap.get(attributeValue);
                if (constructor == null && (asSubclass = this.mContext.getClassLoader().loadClass(attributeValue).asSubclass(cls)) != 0) {
                    constructor = asSubclass.getConstructor(sConstructorSignature);
                    constructor.setAccessible(true);
                    arrayMap.put(attributeValue, constructor);
                }
                newInstance = constructor.newInstance(this.mContext, attributeSet);
            }
            return newInstance;
        } catch (ClassNotFoundException e) {
            throw new InflateException("Could not instantiate " + cls + " class " + attributeValue, e);
        } catch (IllegalAccessException e2) {
            throw new InflateException("Could not instantiate " + cls + " class " + attributeValue, e2);
        } catch (InstantiationException e3) {
            throw new InflateException("Could not instantiate " + cls + " class " + attributeValue, e3);
        } catch (NoSuchMethodException e4) {
            throw new InflateException("Could not instantiate " + cls + " class " + attributeValue, e4);
        } catch (InvocationTargetException e5) {
            throw new InflateException("Could not instantiate " + cls + " class " + attributeValue, e5);
        }
    }

    private void getTargetIds(XmlPullParser xmlPullParser, AttributeSet attributeSet, Transition transition) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                return;
            }
            if (next == 2) {
                if (xmlPullParser.getName().equals("target")) {
                    TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.TransitionTarget);
                    int resourceId = obtainStyledAttributes.getResourceId(1, 0);
                    if (resourceId != 0) {
                        transition.addTarget(resourceId);
                    } else {
                        int resourceId2 = obtainStyledAttributes.getResourceId(2, 0);
                        if (resourceId2 != 0) {
                            transition.excludeTarget(resourceId2, true);
                        } else {
                            String string = obtainStyledAttributes.getString(4);
                            if (string != null) {
                                transition.addTarget(string);
                            } else {
                                String string2 = obtainStyledAttributes.getString(5);
                                if (string2 != null) {
                                    transition.excludeTarget(string2, true);
                                } else {
                                    String string3 = obtainStyledAttributes.getString(3);
                                    if (string3 != null) {
                                        try {
                                            transition.excludeTarget((Class) Class.forName(string3), true);
                                        } catch (ClassNotFoundException e) {
                                            obtainStyledAttributes.recycle();
                                            throw new RuntimeException("Could not create " + string3, e);
                                        }
                                    } else {
                                        String string4 = obtainStyledAttributes.getString(0);
                                        if (string4 != null) {
                                            transition.addTarget(Class.forName(string4));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    obtainStyledAttributes.recycle();
                } else {
                    throw new RuntimeException("Unknown scene name: " + xmlPullParser.getName());
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0053, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.transition.TransitionManager createTransitionManagerFromXml(org.xmlpull.v1.XmlPullParser r5, android.util.AttributeSet r6, android.view.ViewGroup r7) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r4 = this;
            int r0 = r5.getDepth()
            r1 = 0
        L5:
            int r2 = r5.next()
            r3 = 3
            if (r2 != r3) goto L12
            int r3 = r5.getDepth()
            if (r3 <= r0) goto L53
        L12:
            r3 = 1
            if (r2 == r3) goto L53
            r3 = 2
            if (r2 == r3) goto L19
            goto L5
        L19:
            java.lang.String r2 = r5.getName()
            java.lang.String r3 = "transitionManager"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L2c
            android.transition.TransitionManager r1 = new android.transition.TransitionManager
            r1.<init>()
            goto L5
        L2c:
            java.lang.String r3 = "transition"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L3b
            if (r1 == 0) goto L3b
            r4.loadTransition(r6, r7, r1)
            goto L5
        L3b:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Unknown scene name: "
            r6.<init>(r7)
            java.lang.String r5 = r5.getName()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            r4.<init>(r5)
            throw r4
        L53:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.TransitionInflater.createTransitionManagerFromXml(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.ViewGroup):android.transition.TransitionManager");
    }

    private void loadTransition(AttributeSet attributeSet, ViewGroup viewGroup, TransitionManager transitionManager) throws Resources.NotFoundException {
        Transition inflateTransition;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.TransitionManager);
        int resourceId = obtainStyledAttributes.getResourceId(2, -1);
        int resourceId2 = obtainStyledAttributes.getResourceId(0, -1);
        Scene sceneForLayout = resourceId2 < 0 ? null : Scene.getSceneForLayout(viewGroup, resourceId2, this.mContext);
        int resourceId3 = obtainStyledAttributes.getResourceId(1, -1);
        Scene sceneForLayout2 = resourceId3 >= 0 ? Scene.getSceneForLayout(viewGroup, resourceId3, this.mContext) : null;
        if (resourceId >= 0 && (inflateTransition = inflateTransition(resourceId)) != null) {
            if (sceneForLayout2 == null) {
                throw new RuntimeException("No toScene for transition ID " + resourceId);
            }
            if (sceneForLayout == null) {
                transitionManager.setTransition(sceneForLayout2, inflateTransition);
            } else {
                transitionManager.setTransition(sceneForLayout, sceneForLayout2, inflateTransition);
            }
        }
        obtainStyledAttributes.recycle();
    }
}
