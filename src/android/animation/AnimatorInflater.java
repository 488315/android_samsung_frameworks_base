package android.animation;

import android.content.Context;
import android.content.res.ConfigurationBoundResourceCache;
import android.content.res.ConstantState;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.PathParser;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import android.view.animation.AnimationUtils;
import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;
import com.android.internal.R;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AnimatorInflater {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int SEQUENTIALLY = 1;
    private static final String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;
    private static final TypedValue sTmpTypedValue = new TypedValue();

    private static boolean isColorType(int i) {
        return i >= 28 && i <= 31;
    }

    public static Animator loadAnimator(Context context, int i) throws Resources.NotFoundException {
        return loadAnimator(context.getResources(), context.getTheme(), i);
    }

    public static Animator loadAnimator(Resources resources, Resources.Theme theme, int i) throws Resources.NotFoundException {
        return loadAnimator(resources, theme, i, 1.0f);
    }

    public static Animator loadAnimator(Resources resources, Resources.Theme theme, int i, float f) throws Resources.NotFoundException {
        XmlPullParserException xmlPullParserException;
        IOException iOException;
        Throwable th;
        XmlResourceParser animation;
        ConfigurationBoundResourceCache<Animator> animatorCache = resources.getAnimatorCache();
        long j = i;
        Animator configurationBoundResourceCache = animatorCache.getInstance(j, resources, theme);
        if (configurationBoundResourceCache != null) {
            return configurationBoundResourceCache;
        }
        int generation = animatorCache.getGeneration();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                animation = resources.getAnimation(i);
            } catch (IOException e) {
                iOException = e;
            } catch (XmlPullParserException e2) {
                xmlPullParserException = e2;
            }
            try {
                Animator createAnimatorFromXml = createAnimatorFromXml(resources, theme, animation, f);
                if (createAnimatorFromXml != null) {
                    createAnimatorFromXml.appendChangingConfigurations(getChangingConfigs(resources, i));
                    ConstantState<Animator> createConstantState = createAnimatorFromXml.createConstantState();
                    if (createConstantState != null) {
                        animatorCache.put(j, theme, createConstantState, generation);
                        createAnimatorFromXml = createConstantState.newInstance2(resources, theme);
                    }
                }
                if (animation != null) {
                    animation.close();
                }
                return createAnimatorFromXml;
            } catch (IOException e3) {
                iOException = e3;
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i));
                notFoundException.initCause(iOException);
                throw notFoundException;
            } catch (XmlPullParserException e4) {
                xmlPullParserException = e4;
                Resources.NotFoundException notFoundException2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i));
                notFoundException2.initCause(xmlPullParserException);
                throw notFoundException2;
            } catch (Throwable th2) {
                th = th2;
                xmlResourceParser = animation;
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                    throw th;
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static StateListAnimator loadStateListAnimator(Context context, int i) throws Resources.NotFoundException {
        XmlPullParserException xmlPullParserException;
        IOException iOException;
        Throwable th;
        Resources resources = context.getResources();
        ConfigurationBoundResourceCache<StateListAnimator> stateListAnimatorCache = resources.getStateListAnimatorCache();
        Resources.Theme theme = context.getTheme();
        long j = i;
        StateListAnimator configurationBoundResourceCache = stateListAnimatorCache.getInstance(j, resources, theme);
        if (configurationBoundResourceCache != null) {
            return configurationBoundResourceCache;
        }
        int generation = stateListAnimatorCache.getGeneration();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                XmlResourceParser animation = resources.getAnimation(i);
                try {
                    StateListAnimator createStateListAnimatorFromXml = createStateListAnimatorFromXml(context, animation, Xml.asAttributeSet(animation));
                    if (createStateListAnimatorFromXml != null) {
                        createStateListAnimatorFromXml.appendChangingConfigurations(getChangingConfigs(resources, i));
                        ConstantState<StateListAnimator> createConstantState = createStateListAnimatorFromXml.createConstantState();
                        if (createConstantState != null) {
                            stateListAnimatorCache.put(j, theme, createConstantState, generation);
                            createStateListAnimatorFromXml = createConstantState.newInstance2(resources, theme);
                        }
                    }
                    if (animation != null) {
                        animation.close();
                    }
                    return createStateListAnimatorFromXml;
                } catch (IOException e) {
                    iOException = e;
                    Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load state list animator resource ID #0x" + Integer.toHexString(i));
                    notFoundException.initCause(iOException);
                    throw notFoundException;
                } catch (XmlPullParserException e2) {
                    xmlPullParserException = e2;
                    Resources.NotFoundException notFoundException2 = new Resources.NotFoundException("Can't load state list animator resource ID #0x" + Integer.toHexString(i));
                    notFoundException2.initCause(xmlPullParserException);
                    throw notFoundException2;
                } catch (Throwable th2) {
                    th = th2;
                    xmlResourceParser = animation;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                        throw th;
                    }
                    throw th;
                }
            } catch (IOException e3) {
                iOException = e3;
            } catch (XmlPullParserException e4) {
                xmlPullParserException = e4;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x006f, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.animation.StateListAnimator createStateListAnimatorFromXml(android.content.Context r10, org.xmlpull.v1.XmlPullParser r11, android.util.AttributeSet r12) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            android.animation.StateListAnimator r0 = new android.animation.StateListAnimator
            r0.<init>()
        L5:
            int r1 = r11.next()
            r2 = 1
            if (r1 == r2) goto L6f
            r2 = 2
            if (r1 == r2) goto L13
            r2 = 3
            if (r1 == r2) goto L6f
            goto L5
        L13:
            java.lang.String r1 = "item"
            java.lang.String r2 = r11.getName()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L5
            int r1 = r11.getAttributeCount()
            int[] r2 = new int[r1]
            r3 = 0
            r4 = 0
            r5 = r3
            r6 = r5
        L29:
            if (r5 >= r1) goto L4d
            int r7 = r12.getAttributeNameResource(r5)
            r8 = 16843213(0x10101cd, float:2.369485E-38)
            if (r7 != r8) goto L3d
            int r4 = r12.getAttributeResourceValue(r5, r3)
            android.animation.Animator r4 = loadAnimator(r10, r4)
            goto L4a
        L3d:
            int r8 = r6 + 1
            boolean r9 = r12.getAttributeBooleanValue(r5, r3)
            if (r9 == 0) goto L46
            goto L47
        L46:
            int r7 = -r7
        L47:
            r2[r6] = r7
            r6 = r8
        L4a:
            int r5 = r5 + 1
            goto L29
        L4d:
            if (r4 != 0) goto L5d
            android.content.res.Resources r1 = r10.getResources()
            android.content.res.Resources$Theme r3 = r10.getTheme()
            r4 = 1065353216(0x3f800000, float:1.0)
            android.animation.Animator r4 = createAnimatorFromXml(r1, r3, r11, r4)
        L5d:
            if (r4 == 0) goto L67
            int[] r1 = android.util.StateSet.trimStateSet(r2, r6)
            r0.addState(r1, r4)
            goto L5
        L67:
            android.content.res.Resources$NotFoundException r10 = new android.content.res.Resources$NotFoundException
            java.lang.String r11 = "animation state item must have a valid animation"
            r10.<init>(r11)
            throw r10
        L6f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.animation.AnimatorInflater.createStateListAnimatorFromXml(android.content.Context, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet):android.animation.StateListAnimator");
    }

    private static class PathDataEvaluator implements TypeEvaluator<PathParser.PathData> {
        private final PathParser.PathData mPathData;

        private PathDataEvaluator() {
            this.mPathData = new PathParser.PathData();
        }

        @Override // android.animation.TypeEvaluator
        public PathParser.PathData evaluate(float f, PathParser.PathData pathData, PathParser.PathData pathData2) {
            if (!PathParser.interpolatePathData(this.mPathData, pathData, pathData2, f)) {
                throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
            }
            return this.mPathData;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static PropertyValuesHolder getPVH(TypedArray typedArray, int i, int i2, int i3, String str) {
        int i4;
        int i5;
        int i6;
        float f;
        PropertyValuesHolder ofFloat;
        float f2;
        float f3;
        TypedValue peekValue = typedArray.peekValue(i2);
        byte b = peekValue != null;
        int i7 = b != false ? peekValue.type : 0;
        TypedValue peekValue2 = typedArray.peekValue(i3);
        byte b2 = peekValue2 != null;
        int i8 = b2 != false ? peekValue2.type : 0;
        if (i == 4) {
            i = ((b == true && isColorType(i7)) || (b2 == true && isColorType(i8))) ? 3 : 0;
        }
        byte b3 = i == 0;
        PropertyValuesHolder propertyValuesHolder = null;
        byte b4 = 0;
        byte b5 = 0;
        if (i == 2) {
            String string = typedArray.getString(i2);
            String string2 = typedArray.getString(i3);
            PathParser.PathData pathData = string == null ? null : new PathParser.PathData(string);
            PathParser.PathData pathData2 = string2 == null ? null : new PathParser.PathData(string2);
            if (pathData != null || pathData2 != null) {
                if (pathData != null) {
                    PathDataEvaluator pathDataEvaluator = new PathDataEvaluator();
                    if (pathData2 != null) {
                        if (!PathParser.canMorph(pathData, pathData2)) {
                            throw new InflateException(" Can't morph from " + string + " to " + string2);
                        }
                        return PropertyValuesHolder.ofObject(str, pathDataEvaluator, pathData, pathData2);
                    }
                    return PropertyValuesHolder.ofObject(str, pathDataEvaluator, pathData);
                }
                if (pathData2 != null) {
                    return PropertyValuesHolder.ofObject(str, new PathDataEvaluator(), pathData2);
                }
            }
            return null;
        }
        ArgbEvaluator argbEvaluator = i == 3 ? ArgbEvaluator.getInstance() : null;
        if (b3 == true) {
            if (b == true) {
                if (i7 == 5) {
                    f2 = typedArray.getDimension(i2, 0.0f);
                } else {
                    f2 = typedArray.getFloat(i2, 0.0f);
                }
                if (b2 != false) {
                    if (i8 == 5) {
                        f3 = typedArray.getDimension(i3, 0.0f);
                    } else {
                        f3 = typedArray.getFloat(i3, 0.0f);
                    }
                    ofFloat = PropertyValuesHolder.ofFloat(str, f2, f3);
                } else {
                    ofFloat = PropertyValuesHolder.ofFloat(str, f2);
                }
            } else {
                if (i8 == 5) {
                    f = typedArray.getDimension(i3, 0.0f);
                } else {
                    f = typedArray.getFloat(i3, 0.0f);
                }
                ofFloat = PropertyValuesHolder.ofFloat(str, f);
            }
            propertyValuesHolder = ofFloat;
        } else if (b == true) {
            if (i7 == 5) {
                i5 = (int) typedArray.getDimension(i2, 0.0f);
            } else if (isColorType(i7)) {
                i5 = typedArray.getColor(i2, 0);
            } else {
                i5 = typedArray.getInt(i2, 0);
            }
            if (b2 != false) {
                if (i8 == 5) {
                    i6 = (int) typedArray.getDimension(i3, 0.0f);
                } else if (isColorType(i8)) {
                    i6 = typedArray.getColor(i3, 0);
                } else {
                    i6 = typedArray.getInt(i3, 0);
                }
                propertyValuesHolder = PropertyValuesHolder.ofInt(str, i5, i6);
            } else {
                propertyValuesHolder = PropertyValuesHolder.ofInt(str, i5);
            }
        } else if (b2 != false) {
            if (i8 == 5) {
                i4 = (int) typedArray.getDimension(i3, 0.0f);
            } else if (isColorType(i8)) {
                i4 = typedArray.getColor(i3, 0);
            } else {
                i4 = typedArray.getInt(i3, 0);
            }
            propertyValuesHolder = PropertyValuesHolder.ofInt(str, i4);
        }
        if (propertyValuesHolder != null && argbEvaluator != null) {
            propertyValuesHolder.setEvaluator(argbEvaluator);
        }
        return propertyValuesHolder;
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator valueAnimator, TypedArray typedArray, TypedArray typedArray2, float f) {
        long j = typedArray.getInt(1, 300);
        long j2 = typedArray.getInt(2, 0);
        int i = typedArray.getInt(7, 4);
        if (i == 4) {
            i = inferValueTypeFromValues(typedArray, 5, 6);
        }
        PropertyValuesHolder pvh = getPVH(typedArray, i, 5, 6, "");
        if (pvh != null) {
            valueAnimator.setValues(pvh);
        }
        valueAnimator.setDuration(j);
        valueAnimator.setStartDelay(j2);
        if (typedArray.hasValue(3)) {
            valueAnimator.setRepeatCount(typedArray.getInt(3, 0));
        }
        if (typedArray.hasValue(4)) {
            valueAnimator.setRepeatMode(typedArray.getInt(4, 1));
        }
        if (typedArray2 != null) {
            setupObjectAnimator(valueAnimator, typedArray2, i, f);
        }
    }

    private static TypeEvaluator setupAnimatorForPath(ValueAnimator valueAnimator, TypedArray typedArray) {
        String string = typedArray.getString(5);
        String string2 = typedArray.getString(6);
        PathParser.PathData pathData = string == null ? null : new PathParser.PathData(string);
        PathParser.PathData pathData2 = string2 == null ? null : new PathParser.PathData(string2);
        if (pathData == null) {
            if (pathData2 == null) {
                return null;
            }
            valueAnimator.setObjectValues(pathData2);
            return new PathDataEvaluator();
        }
        if (pathData2 != null) {
            valueAnimator.setObjectValues(pathData, pathData2);
            if (!PathParser.canMorph(pathData, pathData2)) {
                throw new InflateException(typedArray.getPositionDescription() + " Can't morph from " + string + " to " + string2);
            }
        } else {
            valueAnimator.setObjectValues(pathData);
        }
        return new PathDataEvaluator();
    }

    private static void setupObjectAnimator(ValueAnimator valueAnimator, TypedArray typedArray, int i, float f) {
        Keyframes createXIntKeyframes;
        Keyframes createYIntKeyframes;
        ObjectAnimator objectAnimator = (ObjectAnimator) valueAnimator;
        String string = typedArray.getString(1);
        if (string != null) {
            String string2 = typedArray.getString(2);
            String string3 = typedArray.getString(3);
            if (i == 2 || i == 4) {
                i = 0;
            }
            if (string2 == null && string3 == null) {
                throw new InflateException(typedArray.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
            }
            PathKeyframes ofPath = KeyframeSet.ofPath(PathParser.createPathFromPathData(string), f * 0.5f);
            if (i == 0) {
                createXIntKeyframes = ofPath.createXFloatKeyframes();
                createYIntKeyframes = ofPath.createYFloatKeyframes();
            } else {
                createXIntKeyframes = ofPath.createXIntKeyframes();
                createYIntKeyframes = ofPath.createYIntKeyframes();
            }
            PropertyValuesHolder ofKeyframes = string2 != null ? PropertyValuesHolder.ofKeyframes(string2, createXIntKeyframes) : null;
            PropertyValuesHolder ofKeyframes2 = string3 != null ? PropertyValuesHolder.ofKeyframes(string3, createYIntKeyframes) : null;
            if (ofKeyframes == null) {
                objectAnimator.setValues(ofKeyframes2);
                return;
            } else if (ofKeyframes2 == null) {
                objectAnimator.setValues(ofKeyframes);
                return;
            } else {
                objectAnimator.setValues(ofKeyframes, ofKeyframes2);
                return;
            }
        }
        objectAnimator.setPropertyName(typedArray.getString(0));
    }

    private static void setupValues(ValueAnimator valueAnimator, TypedArray typedArray, boolean z, boolean z2, int i, boolean z3, int i2) {
        int i3;
        int i4;
        int i5;
        float f;
        float f2;
        float f3;
        if (z) {
            if (!z2) {
                if (i2 == 5) {
                    f = typedArray.getDimension(6, 0.0f);
                } else {
                    f = typedArray.getFloat(6, 0.0f);
                }
                valueAnimator.setFloatValues(f);
                return;
            }
            if (i == 5) {
                f2 = typedArray.getDimension(5, 0.0f);
            } else {
                f2 = typedArray.getFloat(5, 0.0f);
            }
            if (z3) {
                if (i2 == 5) {
                    f3 = typedArray.getDimension(6, 0.0f);
                } else {
                    f3 = typedArray.getFloat(6, 0.0f);
                }
                valueAnimator.setFloatValues(f2, f3);
                return;
            }
            valueAnimator.setFloatValues(f2);
            return;
        }
        if (!z2) {
            if (z3) {
                if (i2 == 5) {
                    i3 = (int) typedArray.getDimension(6, 0.0f);
                } else if (isColorType(i2)) {
                    i3 = typedArray.getColor(6, 0);
                } else {
                    i3 = typedArray.getInt(6, 0);
                }
                valueAnimator.setIntValues(i3);
                return;
            }
            return;
        }
        if (i == 5) {
            i4 = (int) typedArray.getDimension(5, 0.0f);
        } else if (isColorType(i)) {
            i4 = typedArray.getColor(5, 0);
        } else {
            i4 = typedArray.getInt(5, 0);
        }
        if (z3) {
            if (i2 == 5) {
                i5 = (int) typedArray.getDimension(6, 0.0f);
            } else if (isColorType(i2)) {
                i5 = typedArray.getColor(6, 0);
            } else {
                i5 = typedArray.getInt(6, 0);
            }
            valueAnimator.setIntValues(i4, i5);
            return;
        }
        valueAnimator.setIntValues(i4);
    }

    private static Animator createAnimatorFromXml(Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, float f) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(resources, theme, xmlPullParser, Xml.asAttributeSet(xmlPullParser), null, 0, f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x00d6, code lost:
    
        if (r14.hasNext() == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x00d8, code lost:
    
        r13[r11] = (android.animation.Animator) r14.next();
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x00e4, code lost:
    
        if (r18 != 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x00e6, code lost:
    
        r17.playTogether(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00e9, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00ea, code lost:
    
        r17.playSequentially(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00ed, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x00c4, code lost:
    
        if (r17 == null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x00c6, code lost:
    
        if (r10 == null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x00c8, code lost:
    
        r13 = new android.animation.Animator[r10.size()];
        r14 = r10.iterator();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.animation.Animator createAnimatorFromXml(android.content.res.Resources r13, android.content.res.Resources.Theme r14, org.xmlpull.v1.XmlPullParser r15, android.util.AttributeSet r16, android.animation.AnimatorSet r17, int r18, float r19) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.animation.AnimatorInflater.createAnimatorFromXml(android.content.res.Resources, android.content.res.Resources$Theme, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.animation.AnimatorSet, int, float):android.animation.Animator");
    }

    private static PropertyValuesHolder[] loadValues(Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int i;
        TypedArray obtainAttributes;
        ArrayList arrayList = null;
        while (true) {
            int eventType = xmlPullParser.getEventType();
            if (eventType == 3 || eventType == 1) {
                break;
            }
            if (eventType != 2) {
                xmlPullParser.next();
            } else {
                if (xmlPullParser.getName().equals("propertyValuesHolder")) {
                    if (theme != null) {
                        obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.PropertyValuesHolder, 0, 0);
                    } else {
                        obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.PropertyValuesHolder);
                    }
                    String string = obtainAttributes.getString(3);
                    int i2 = obtainAttributes.getInt(2, 4);
                    PropertyValuesHolder loadPvh = loadPvh(resources, theme, xmlPullParser, string, i2);
                    if (loadPvh == null) {
                        loadPvh = getPVH(obtainAttributes, i2, 0, 1, string);
                    }
                    if (loadPvh != null) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(loadPvh);
                    }
                    obtainAttributes.recycle();
                }
                xmlPullParser.next();
            }
        }
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[size];
        for (i = 0; i < size; i++) {
            propertyValuesHolderArr[i] = (PropertyValuesHolder) arrayList.get(i);
        }
        return propertyValuesHolderArr;
    }

    private static int inferValueTypeOfKeyframe(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainAttributes;
        int i = 0;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.Keyframe, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.Keyframe);
        }
        TypedValue peekValue = obtainAttributes.peekValue(0);
        if (peekValue != null && isColorType(peekValue.type)) {
            i = 3;
        }
        obtainAttributes.recycle();
        return i;
    }

    private static int inferValueTypeFromValues(TypedArray typedArray, int i, int i2) {
        TypedValue peekValue = typedArray.peekValue(i);
        boolean z = peekValue != null;
        int i3 = z ? peekValue.type : 0;
        TypedValue peekValue2 = typedArray.peekValue(i2);
        boolean z2 = peekValue2 != null;
        int i4 = z2 ? peekValue2.type : 0;
        if (z && isColorType(i3)) {
            return 3;
        }
        return (z2 && isColorType(i4)) ? 3 : 0;
    }

    private static void dumpKeyframes(Object[] objArr, String str) {
        if (objArr == null || objArr.length == 0) {
            return;
        }
        Log.d(TAG, str);
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            Keyframe keyframe = (Keyframe) objArr[i];
            StringBuilder sb = new StringBuilder("Keyframe ");
            sb.append(i);
            sb.append(": fraction ");
            float fraction = keyframe.getFraction();
            Object obj = PerfettoProtoLogImpl.NULL_STRING;
            sb.append(fraction < 0.0f ? PerfettoProtoLogImpl.NULL_STRING : Float.valueOf(keyframe.getFraction()));
            sb.append(", , value : ");
            if (keyframe.hasValue()) {
                obj = keyframe.getValue();
            }
            sb.append(obj);
            Log.d(TAG, sb.toString());
        }
    }

    private static PropertyValuesHolder loadPvh(Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, String str, int i) throws XmlPullParserException, IOException {
        int size;
        ArrayList arrayList = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 3 || next == 1) {
                break;
            }
            if (xmlPullParser.getName().equals("keyframe")) {
                if (i == 4) {
                    i = inferValueTypeOfKeyframe(resources, theme, Xml.asAttributeSet(xmlPullParser));
                }
                Keyframe loadKeyframe = loadKeyframe(resources, theme, Xml.asAttributeSet(xmlPullParser), i);
                if (loadKeyframe != null) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(loadKeyframe);
                }
                xmlPullParser.next();
            }
        }
        if (arrayList == null || (size = arrayList.size()) <= 0) {
            return null;
        }
        Keyframe keyframe = (Keyframe) arrayList.get(0);
        Keyframe keyframe2 = (Keyframe) arrayList.get(size - 1);
        float fraction = keyframe2.getFraction();
        if (fraction < 1.0f) {
            if (fraction < 0.0f) {
                keyframe2.setFraction(1.0f);
            } else {
                arrayList.add(arrayList.size(), createNewKeyframe(keyframe2, 1.0f));
                size++;
            }
        }
        float fraction2 = keyframe.getFraction();
        if (fraction2 != 0.0f) {
            if (fraction2 < 0.0f) {
                keyframe.setFraction(0.0f);
            } else {
                arrayList.add(0, createNewKeyframe(keyframe, 0.0f));
                size++;
            }
        }
        Keyframe[] keyframeArr = new Keyframe[size];
        arrayList.toArray(keyframeArr);
        for (int i2 = 0; i2 < size; i2++) {
            Keyframe keyframe3 = keyframeArr[i2];
            if (keyframe3.getFraction() < 0.0f) {
                if (i2 == 0) {
                    keyframe3.setFraction(0.0f);
                } else {
                    int i3 = size - 1;
                    if (i2 == i3) {
                        keyframe3.setFraction(1.0f);
                    } else {
                        int i4 = i2;
                        for (int i5 = i2 + 1; i5 < i3 && keyframeArr[i5].getFraction() < 0.0f; i5++) {
                            i4 = i5;
                        }
                        distributeKeyframes(keyframeArr, keyframeArr[i4 + 1].getFraction() - keyframeArr[i2 - 1].getFraction(), i2, i4);
                    }
                }
            }
        }
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe(str, keyframeArr);
        if (i == 3) {
            ofKeyframe.setEvaluator(ArgbEvaluator.getInstance());
        }
        return ofKeyframe;
    }

    private static Keyframe createNewKeyframe(Keyframe keyframe, float f) {
        if (keyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat(f);
        }
        if (keyframe.getType() == Integer.TYPE) {
            return Keyframe.ofInt(f);
        }
        return Keyframe.ofObject(f);
    }

    private static void distributeKeyframes(Keyframe[] keyframeArr, float f, int i, int i2) {
        float f2 = f / ((i2 - i) + 2);
        while (i <= i2) {
            keyframeArr[i].setFraction(keyframeArr[i - 1].getFraction() + f2);
            i++;
        }
    }

    private static Keyframe loadKeyframe(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int i) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes;
        Keyframe ofInt;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.Keyframe, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.Keyframe);
        }
        float f = obtainAttributes.getFloat(3, -1.0f);
        TypedValue peekValue = obtainAttributes.peekValue(0);
        boolean z = peekValue != null;
        if (i == 4) {
            i = (z && isColorType(peekValue.type)) ? 3 : 0;
        }
        if (z) {
            if (i == 0) {
                ofInt = Keyframe.ofFloat(f, obtainAttributes.getFloat(0, 0.0f));
            } else {
                ofInt = (i == 1 || i == 3) ? Keyframe.ofInt(f, obtainAttributes.getInt(0, 0)) : null;
            }
        } else if (i == 0) {
            ofInt = Keyframe.ofFloat(f);
        } else {
            ofInt = Keyframe.ofInt(f);
        }
        int resourceId = obtainAttributes.getResourceId(1, 0);
        if (resourceId > 0) {
            ofInt.setInterpolator(AnimationUtils.loadInterpolator(resources, theme, resourceId));
        }
        obtainAttributes.recycle();
        return ofInt;
    }

    private static ObjectAnimator loadObjectAnimator(Resources resources, Resources.Theme theme, AttributeSet attributeSet, float f) throws Resources.NotFoundException {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        loadAnimator(resources, theme, attributeSet, objectAnimator, f);
        return objectAnimator;
    }

    private static ValueAnimator loadAnimator(Resources resources, Resources.Theme theme, AttributeSet attributeSet, ValueAnimator valueAnimator, float f) throws Resources.NotFoundException {
        TypedArray obtainAttributes;
        TypedArray typedArray;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.Animator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.Animator);
        }
        if (valueAnimator != null) {
            if (theme != null) {
                typedArray = theme.obtainStyledAttributes(attributeSet, R.styleable.PropertyAnimator, 0, 0);
            } else {
                typedArray = resources.obtainAttributes(attributeSet, R.styleable.PropertyAnimator);
            }
            valueAnimator.appendChangingConfigurations(typedArray.getChangingConfigurations());
        } else {
            typedArray = null;
        }
        if (valueAnimator == null) {
            valueAnimator = new ValueAnimator();
        }
        valueAnimator.appendChangingConfigurations(obtainAttributes.getChangingConfigurations());
        parseAnimatorFromTypeArray(valueAnimator, obtainAttributes, typedArray, f);
        int resourceId = obtainAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            Interpolator loadInterpolator = AnimationUtils.loadInterpolator(resources, theme, resourceId);
            if (loadInterpolator instanceof BaseInterpolator) {
                valueAnimator.appendChangingConfigurations(((BaseInterpolator) loadInterpolator).getChangingConfiguration());
            }
            valueAnimator.setInterpolator(loadInterpolator);
        }
        obtainAttributes.recycle();
        if (typedArray != null) {
            typedArray.recycle();
        }
        return valueAnimator;
    }

    private static int getChangingConfigs(Resources resources, int i) {
        int i2;
        TypedValue typedValue = sTmpTypedValue;
        synchronized (typedValue) {
            resources.getValue(i, typedValue, true);
            i2 = typedValue.changingConfigurations;
        }
        return i2;
    }
}
