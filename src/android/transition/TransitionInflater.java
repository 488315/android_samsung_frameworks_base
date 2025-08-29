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
import com.samsung.android.location.SemLocationManager;
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

    public TransitionManager inflateTransitionManager(int i, ViewGroup viewGroup) throws Resources.NotFoundException {
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

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0173, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Transition createTransitionFromXml(XmlPullParser xmlPullParser, AttributeSet attributeSet, Transition transition) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        TransitionSet transitionSet = transition instanceof TransitionSet ? (TransitionSet) transition : null;
        loop0: while (true) {
            Transition transitionSet2 = null;
            while (true) {
                int next = xmlPullParser.next();
                if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                    break loop0;
                }
                if (next == 2) {
                    String name = xmlPullParser.getName();
                    if ("fade".equals(name)) {
                        transitionSet2 = new Fade(this.mContext, attributeSet);
                    } else if ("changeBounds".equals(name)) {
                        transitionSet2 = new ChangeBounds(this.mContext, attributeSet);
                    } else if ("slide".equals(name)) {
                        transitionSet2 = new Slide(this.mContext, attributeSet);
                    } else if ("explode".equals(name)) {
                        transitionSet2 = new Explode(this.mContext, attributeSet);
                    } else if ("changeImageTransform".equals(name)) {
                        transitionSet2 = new ChangeImageTransform(this.mContext, attributeSet);
                    } else if ("changeTransform".equals(name)) {
                        transitionSet2 = new ChangeTransform(this.mContext, attributeSet);
                    } else if ("changeClipBounds".equals(name)) {
                        transitionSet2 = new ChangeClipBounds(this.mContext, attributeSet);
                    } else if ("autoTransition".equals(name)) {
                        transitionSet2 = new AutoTransition(this.mContext, attributeSet);
                    } else if ("recolor".equals(name)) {
                        transitionSet2 = new Recolor(this.mContext, attributeSet);
                    } else if ("changeScroll".equals(name)) {
                        transitionSet2 = new ChangeScroll(this.mContext, attributeSet);
                    } else if ("transitionSet".equals(name)) {
                        transitionSet2 = new TransitionSet(this.mContext, attributeSet);
                    } else if (SemLocationManager.GEOFENCE_TRANSITION.equals(name)) {
                        transitionSet2 = (Transition) createCustom(attributeSet, Transition.class, SemLocationManager.GEOFENCE_TRANSITION);
                    } else if ("targets".equals(name)) {
                        getTargetIds(xmlPullParser, attributeSet, transition);
                    } else if ("arcMotion".equals(name)) {
                        transition.setPathMotion(new ArcMotion(this.mContext, attributeSet));
                    } else if ("pathMotion".equals(name)) {
                        transition.setPathMotion((PathMotion) createCustom(attributeSet, PathMotion.class, "pathMotion"));
                    } else if ("patternPathMotion".equals(name)) {
                        transition.setPathMotion(new PatternPathMotion(this.mContext, attributeSet));
                    } else {
                        throw new RuntimeException("Unknown scene name: " + xmlPullParser.getName());
                    }
                    if (transitionSet2 == null) {
                        continue;
                    } else {
                        if (!xmlPullParser.isEmptyElementTag()) {
                            createTransitionFromXml(xmlPullParser, attributeSet, transitionSet2);
                        }
                        if (transitionSet != null) {
                            break;
                        }
                        if (transition != null) {
                            throw new InflateException("Could not add transition to another transition.");
                        }
                    }
                }
            }
            transitionSet.addTransition(transitionSet2);
        }
    }

    private Object createCustom(AttributeSet attributeSet, Class cls, String str) {
        Object objNewInstance;
        Class<? extends U> clsAsSubclass;
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        if (attributeValue == null) {
            throw new InflateException(str + " tag must have a 'class' attribute");
        }
        try {
            ArrayMap<String, Constructor> arrayMap = sConstructors;
            synchronized (arrayMap) {
                Constructor constructor = arrayMap.get(attributeValue);
                if (constructor == null && (clsAsSubclass = this.mContext.getClassLoader().loadClass(attributeValue).asSubclass(cls)) != 0) {
                    constructor = clsAsSubclass.getConstructor(sConstructorSignature);
                    constructor.setAccessible(true);
                    arrayMap.put(attributeValue, constructor);
                }
                objNewInstance = constructor.newInstance(this.mContext, attributeSet);
            }
            return objNewInstance;
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
                    TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.TransitionTarget);
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(1, 0);
                    if (resourceId != 0) {
                        transition.addTarget(resourceId);
                    } else {
                        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(2, 0);
                        if (resourceId2 != 0) {
                            transition.excludeTarget(resourceId2, true);
                        } else {
                            String string = typedArrayObtainStyledAttributes.getString(4);
                            if (string != null) {
                                transition.addTarget(string);
                            } else {
                                String string2 = typedArrayObtainStyledAttributes.getString(5);
                                if (string2 != null) {
                                    transition.excludeTarget(string2, true);
                                } else {
                                    String string3 = typedArrayObtainStyledAttributes.getString(3);
                                    if (string3 != null) {
                                        try {
                                            transition.excludeTarget((Class) Class.forName(string3), true);
                                        } catch (ClassNotFoundException e) {
                                            typedArrayObtainStyledAttributes.recycle();
                                            throw new RuntimeException("Could not create " + string3, e);
                                        }
                                    } else {
                                        String string4 = typedArrayObtainStyledAttributes.getString(0);
                                        if (string4 != null) {
                                            transition.addTarget(Class.forName(string4));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    typedArrayObtainStyledAttributes.recycle();
                } else {
                    throw new RuntimeException("Unknown scene name: " + xmlPullParser.getName());
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private TransitionManager createTransitionManagerFromXml(XmlPullParser xmlPullParser, AttributeSet attributeSet, ViewGroup viewGroup) throws XmlPullParserException, Resources.NotFoundException, IOException {
        int depth = xmlPullParser.getDepth();
        TransitionManager transitionManager = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                break;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("transitionManager")) {
                    transitionManager = new TransitionManager();
                } else {
                    if (!name.equals(SemLocationManager.GEOFENCE_TRANSITION) || transitionManager == null) {
                        break;
                    }
                    loadTransition(attributeSet, viewGroup, transitionManager);
                }
            }
        }
        throw new RuntimeException("Unknown scene name: " + xmlPullParser.getName());
    }

    private void loadTransition(AttributeSet attributeSet, ViewGroup viewGroup, TransitionManager transitionManager) throws Resources.NotFoundException {
        Transition transitionInflateTransition;
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.TransitionManager);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(2, -1);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(0, -1);
        Scene sceneForLayout = resourceId2 < 0 ? null : Scene.getSceneForLayout(viewGroup, resourceId2, this.mContext);
        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(1, -1);
        Scene sceneForLayout2 = resourceId3 >= 0 ? Scene.getSceneForLayout(viewGroup, resourceId3, this.mContext) : null;
        if (resourceId >= 0 && (transitionInflateTransition = inflateTransition(resourceId)) != null) {
            if (sceneForLayout2 == null) {
                throw new RuntimeException("No toScene for transition ID " + resourceId);
            }
            if (sceneForLayout == null) {
                transitionManager.setTransition(sceneForLayout2, transitionInflateTransition);
            } else {
                transitionManager.setTransition(sceneForLayout, sceneForLayout2, transitionInflateTransition);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
