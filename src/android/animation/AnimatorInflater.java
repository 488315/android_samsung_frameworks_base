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
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import android.view.animation.AnimationUtils;
import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;
import com.android.ims.ImsConfig;
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
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                Animator animatorCreateAnimatorFromXml = createAnimatorFromXml(resources, theme, animation, f);
                if (animatorCreateAnimatorFromXml != null) {
                    animatorCreateAnimatorFromXml.appendChangingConfigurations(getChangingConfigs(resources, i));
                    ConstantState<Animator> constantStateCreateConstantState = animatorCreateAnimatorFromXml.createConstantState();
                    if (constantStateCreateConstantState != null) {
                        animatorCache.put(j, theme, constantStateCreateConstantState, generation);
                        animatorCreateAnimatorFromXml = constantStateCreateConstantState.newInstance2(resources, theme);
                    }
                }
                if (animation != null) {
                    animation.close();
                }
                return animatorCreateAnimatorFromXml;
            } catch (IOException e) {
                iOException = e;
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i));
                notFoundException.initCause(iOException);
                throw notFoundException;
            } catch (XmlPullParserException e2) {
                xmlPullParserException = e2;
                Resources.NotFoundException notFoundException2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i));
                notFoundException2.initCause(xmlPullParserException);
                throw notFoundException2;
            } catch (Throwable th3) {
                th = th3;
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
    }

    public static StateListAnimator loadStateListAnimator(Context context, int i) throws Throwable {
        XmlPullParserException xmlPullParserException;
        IOException iOException;
        Throwable th;
        XmlResourceParser animation;
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
                animation = resources.getAnimation(i);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e) {
            iOException = e;
        } catch (XmlPullParserException e2) {
            xmlPullParserException = e2;
        }
        try {
            StateListAnimator stateListAnimatorCreateStateListAnimatorFromXml = createStateListAnimatorFromXml(context, animation, Xml.asAttributeSet(animation));
            if (stateListAnimatorCreateStateListAnimatorFromXml != null) {
                stateListAnimatorCreateStateListAnimatorFromXml.appendChangingConfigurations(getChangingConfigs(resources, i));
                ConstantState<StateListAnimator> constantStateCreateConstantState = stateListAnimatorCreateStateListAnimatorFromXml.createConstantState();
                if (constantStateCreateConstantState != null) {
                    stateListAnimatorCache.put(j, theme, constantStateCreateConstantState, generation);
                    stateListAnimatorCreateStateListAnimatorFromXml = constantStateCreateConstantState.newInstance2(resources, theme);
                }
            }
            if (animation != null) {
                animation.close();
            }
            return stateListAnimatorCreateStateListAnimatorFromXml;
        } catch (IOException e3) {
            iOException = e3;
            Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load state list animator resource ID #0x" + Integer.toHexString(i));
            notFoundException.initCause(iOException);
            throw notFoundException;
        } catch (XmlPullParserException e4) {
            xmlPullParserException = e4;
            Resources.NotFoundException notFoundException2 = new Resources.NotFoundException("Can't load state list animator resource ID #0x" + Integer.toHexString(i));
            notFoundException2.initCause(xmlPullParserException);
            throw notFoundException2;
        } catch (Throwable th3) {
            th = th3;
            xmlResourceParser = animation;
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
                throw th;
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x006f, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static StateListAnimator createStateListAnimatorFromXml(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, Resources.NotFoundException, IOException {
        StateListAnimator stateListAnimator = new StateListAnimator();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                break;
            }
            if (next != 2) {
                if (next == 3) {
                    break;
                }
            } else if (ImsConfig.EXTRA_CHANGED_ITEM.equals(xmlPullParser.getName())) {
                int attributeCount = xmlPullParser.getAttributeCount();
                int[] iArr = new int[attributeCount];
                Animator animatorCreateAnimatorFromXml = null;
                int i = 0;
                for (int i2 = 0; i2 < attributeCount; i2++) {
                    int attributeNameResource = attributeSet.getAttributeNameResource(i2);
                    if (attributeNameResource == 16843213) {
                        animatorCreateAnimatorFromXml = loadAnimator(context, attributeSet.getAttributeResourceValue(i2, 0));
                    } else {
                        int i3 = i + 1;
                        if (!attributeSet.getAttributeBooleanValue(i2, false)) {
                            attributeNameResource = -attributeNameResource;
                        }
                        iArr[i] = attributeNameResource;
                        i = i3;
                    }
                }
                if (animatorCreateAnimatorFromXml == null) {
                    animatorCreateAnimatorFromXml = createAnimatorFromXml(context.getResources(), context.getTheme(), xmlPullParser, 1.0f);
                }
                if (animatorCreateAnimatorFromXml == null) {
                    throw new Resources.NotFoundException("animation state item must have a valid animation");
                }
                stateListAnimator.addState(StateSet.trimStateSet(iArr, i), animatorCreateAnimatorFromXml);
            } else {
                continue;
            }
        }
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
        int color;
        int color2;
        int color3;
        float dimension;
        PropertyValuesHolder propertyValuesHolderOfFloat;
        float dimension2;
        float dimension3;
        TypedValue typedValuePeekValue = typedArray.peekValue(i2);
        Object[] objArr = typedValuePeekValue != null;
        int i4 = objArr != false ? typedValuePeekValue.type : 0;
        TypedValue typedValuePeekValue2 = typedArray.peekValue(i3);
        Object[] objArr2 = typedValuePeekValue2 != null;
        int i5 = objArr2 != false ? typedValuePeekValue2.type : 0;
        if (i == 4) {
            i = ((objArr == true && isColorType(i4)) || (objArr2 == true && isColorType(i5))) ? 3 : 0;
        }
        Object[] objArr3 = i == 0;
        PropertyValuesHolder propertyValuesHolderOfInt = null;
        Object[] objArr4 = 0;
        Object[] objArr5 = 0;
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
        if (objArr3 == true) {
            if (objArr == true) {
                if (i4 == 5) {
                    dimension2 = typedArray.getDimension(i2, 0.0f);
                } else {
                    dimension2 = typedArray.getFloat(i2, 0.0f);
                }
                if (objArr2 != false) {
                    if (i5 == 5) {
                        dimension3 = typedArray.getDimension(i3, 0.0f);
                    } else {
                        dimension3 = typedArray.getFloat(i3, 0.0f);
                    }
                    propertyValuesHolderOfFloat = PropertyValuesHolder.ofFloat(str, dimension2, dimension3);
                } else {
                    propertyValuesHolderOfFloat = PropertyValuesHolder.ofFloat(str, dimension2);
                }
            } else {
                if (i5 == 5) {
                    dimension = typedArray.getDimension(i3, 0.0f);
                } else {
                    dimension = typedArray.getFloat(i3, 0.0f);
                }
                propertyValuesHolderOfFloat = PropertyValuesHolder.ofFloat(str, dimension);
            }
            propertyValuesHolderOfInt = propertyValuesHolderOfFloat;
        } else if (objArr == true) {
            if (i4 == 5) {
                color2 = (int) typedArray.getDimension(i2, 0.0f);
            } else if (isColorType(i4)) {
                color2 = typedArray.getColor(i2, 0);
            } else {
                color2 = typedArray.getInt(i2, 0);
            }
            if (objArr2 != false) {
                if (i5 == 5) {
                    color3 = (int) typedArray.getDimension(i3, 0.0f);
                } else if (isColorType(i5)) {
                    color3 = typedArray.getColor(i3, 0);
                } else {
                    color3 = typedArray.getInt(i3, 0);
                }
                propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, color2, color3);
            } else {
                propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, color2);
            }
        } else if (objArr2 != false) {
            if (i5 == 5) {
                color = (int) typedArray.getDimension(i3, 0.0f);
            } else if (isColorType(i5)) {
                color = typedArray.getColor(i3, 0);
            } else {
                color = typedArray.getInt(i3, 0);
            }
            propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, color);
        }
        if (propertyValuesHolderOfInt != null && argbEvaluator != null) {
            propertyValuesHolderOfInt.setEvaluator(argbEvaluator);
        }
        return propertyValuesHolderOfInt;
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator valueAnimator, TypedArray typedArray, TypedArray typedArray2, float f) {
        long j = typedArray.getInt(1, 300);
        long j2 = typedArray.getInt(2, 0);
        int iInferValueTypeFromValues = typedArray.getInt(7, 4);
        if (iInferValueTypeFromValues == 4) {
            iInferValueTypeFromValues = inferValueTypeFromValues(typedArray, 5, 6);
        }
        PropertyValuesHolder pvh = getPVH(typedArray, iInferValueTypeFromValues, 5, 6, "");
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
            setupObjectAnimator(valueAnimator, typedArray2, iInferValueTypeFromValues, f);
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
        Keyframes keyframesCreateXIntKeyframes;
        Keyframes keyframesCreateYIntKeyframes;
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
            PathKeyframes pathKeyframesOfPath = KeyframeSet.ofPath(PathParser.createPathFromPathData(string), f * 0.5f);
            if (i == 0) {
                keyframesCreateXIntKeyframes = pathKeyframesOfPath.createXFloatKeyframes();
                keyframesCreateYIntKeyframes = pathKeyframesOfPath.createYFloatKeyframes();
            } else {
                keyframesCreateXIntKeyframes = pathKeyframesOfPath.createXIntKeyframes();
                keyframesCreateYIntKeyframes = pathKeyframesOfPath.createYIntKeyframes();
            }
            PropertyValuesHolder propertyValuesHolderOfKeyframes = string2 != null ? PropertyValuesHolder.ofKeyframes(string2, keyframesCreateXIntKeyframes) : null;
            PropertyValuesHolder propertyValuesHolderOfKeyframes2 = string3 != null ? PropertyValuesHolder.ofKeyframes(string3, keyframesCreateYIntKeyframes) : null;
            if (propertyValuesHolderOfKeyframes == null) {
                objectAnimator.setValues(propertyValuesHolderOfKeyframes2);
                return;
            } else if (propertyValuesHolderOfKeyframes2 == null) {
                objectAnimator.setValues(propertyValuesHolderOfKeyframes);
                return;
            } else {
                objectAnimator.setValues(propertyValuesHolderOfKeyframes, propertyValuesHolderOfKeyframes2);
                return;
            }
        }
        objectAnimator.setPropertyName(typedArray.getString(0));
    }

    private static void setupValues(ValueAnimator valueAnimator, TypedArray typedArray, boolean z, boolean z2, int i, boolean z3, int i2) {
        int color;
        int color2;
        int color3;
        float dimension;
        float dimension2;
        float dimension3;
        if (z) {
            if (!z2) {
                if (i2 == 5) {
                    dimension = typedArray.getDimension(6, 0.0f);
                } else {
                    dimension = typedArray.getFloat(6, 0.0f);
                }
                valueAnimator.setFloatValues(dimension);
                return;
            }
            if (i == 5) {
                dimension2 = typedArray.getDimension(5, 0.0f);
            } else {
                dimension2 = typedArray.getFloat(5, 0.0f);
            }
            if (z3) {
                if (i2 == 5) {
                    dimension3 = typedArray.getDimension(6, 0.0f);
                } else {
                    dimension3 = typedArray.getFloat(6, 0.0f);
                }
                valueAnimator.setFloatValues(dimension2, dimension3);
                return;
            }
            valueAnimator.setFloatValues(dimension2);
            return;
        }
        if (!z2) {
            if (z3) {
                if (i2 == 5) {
                    color = (int) typedArray.getDimension(6, 0.0f);
                } else if (isColorType(i2)) {
                    color = typedArray.getColor(6, 0);
                } else {
                    color = typedArray.getInt(6, 0);
                }
                valueAnimator.setIntValues(color);
                return;
            }
            return;
        }
        if (i == 5) {
            color2 = (int) typedArray.getDimension(5, 0.0f);
        } else if (isColorType(i)) {
            color2 = typedArray.getColor(5, 0);
        } else {
            color2 = typedArray.getInt(5, 0);
        }
        if (z3) {
            if (i2 == 5) {
                color3 = (int) typedArray.getDimension(6, 0.0f);
            } else if (isColorType(i2)) {
                color3 = typedArray.getColor(6, 0);
            } else {
                color3 = typedArray.getInt(6, 0);
            }
            valueAnimator.setIntValues(color2, color3);
            return;
        }
        valueAnimator.setIntValues(color2);
    }

    private static Animator createAnimatorFromXml(Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, float f) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(resources, theme, xmlPullParser, Xml.asAttributeSet(xmlPullParser), null, 0, f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c4, code lost:
    
        if (r17 == null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00c6, code lost:
    
        if (r10 == null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00c8, code lost:
    
        r13 = new android.animation.Animator[r10.size()];
        r14 = r10.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d6, code lost:
    
        if (r14.hasNext() == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d8, code lost:
    
        r13[r11] = (android.animation.Animator) r14.next();
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e4, code lost:
    
        if (r18 != 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e6, code lost:
    
        r17.playTogether(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e9, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ea, code lost:
    
        r17.playSequentially(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ed, code lost:
    
        return r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Animator createAnimatorFromXml(Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet, AnimatorSet animatorSet, int i, float f) throws XmlPullParserException, Resources.NotFoundException, IOException {
        TypedArray typedArrayObtainAttributes;
        AttributeSet attributeSet2 = attributeSet;
        float f2 = f;
        int depth = xmlPullParser.getDepth();
        ValueAnimator valueAnimatorLoadAnimator = null;
        ArrayList arrayList = null;
        while (true) {
            int next = xmlPullParser.next();
            int i2 = 0;
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                break;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("objectAnimator")) {
                    valueAnimatorLoadAnimator = loadObjectAnimator(resources, theme, attributeSet2, f2);
                } else if (name.equals("animator")) {
                    valueAnimatorLoadAnimator = loadAnimator(resources, theme, attributeSet2, null, f2);
                } else if (name.equals("set")) {
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    if (theme != null) {
                        typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSet2, R.styleable.AnimatorSet, 0, 0);
                    } else {
                        typedArrayObtainAttributes = resources.obtainAttributes(attributeSet2, R.styleable.AnimatorSet);
                    }
                    TypedArray typedArray = typedArrayObtainAttributes;
                    animatorSet2.appendChangingConfigurations(typedArray.getChangingConfigurations());
                    createAnimatorFromXml(resources, theme, xmlPullParser, attributeSet2, animatorSet2, typedArray.getInt(0, 0), f2);
                    valueAnimatorLoadAnimator = animatorSet2;
                    typedArray.recycle();
                } else if (name.equals("propertyValuesHolder")) {
                    PropertyValuesHolder[] propertyValuesHolderArrLoadValues = loadValues(resources, theme, xmlPullParser, Xml.asAttributeSet(xmlPullParser));
                    if (propertyValuesHolderArrLoadValues != null && valueAnimatorLoadAnimator != null && (valueAnimatorLoadAnimator instanceof ValueAnimator)) {
                        valueAnimatorLoadAnimator.setValues(propertyValuesHolderArrLoadValues);
                    }
                    i2 = 1;
                } else {
                    throw new RuntimeException("Unknown animator name: " + xmlPullParser.getName());
                }
                if (animatorSet != null && i2 == 0) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(valueAnimatorLoadAnimator);
                }
                attributeSet2 = attributeSet;
                f2 = f;
            }
        }
    }

    private static PropertyValuesHolder[] loadValues(Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int i;
        TypedArray typedArrayObtainAttributes;
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
                        typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.PropertyValuesHolder, 0, 0);
                    } else {
                        typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.PropertyValuesHolder);
                    }
                    String string = typedArrayObtainAttributes.getString(3);
                    int i2 = typedArrayObtainAttributes.getInt(2, 4);
                    PropertyValuesHolder propertyValuesHolderLoadPvh = loadPvh(resources, theme, xmlPullParser, string, i2);
                    if (propertyValuesHolderLoadPvh == null) {
                        propertyValuesHolderLoadPvh = getPVH(typedArrayObtainAttributes, i2, 0, 1, string);
                    }
                    if (propertyValuesHolderLoadPvh != null) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(propertyValuesHolderLoadPvh);
                    }
                    typedArrayObtainAttributes.recycle();
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
        TypedArray typedArrayObtainAttributes;
        int i = 0;
        if (theme != null) {
            typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.Keyframe, 0, 0);
        } else {
            typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.Keyframe);
        }
        TypedValue typedValuePeekValue = typedArrayObtainAttributes.peekValue(0);
        if (typedValuePeekValue != null && isColorType(typedValuePeekValue.type)) {
            i = 3;
        }
        typedArrayObtainAttributes.recycle();
        return i;
    }

    private static int inferValueTypeFromValues(TypedArray typedArray, int i, int i2) {
        TypedValue typedValuePeekValue = typedArray.peekValue(i);
        boolean z = typedValuePeekValue != null;
        int i3 = z ? typedValuePeekValue.type : 0;
        TypedValue typedValuePeekValue2 = typedArray.peekValue(i2);
        boolean z2 = typedValuePeekValue2 != null;
        int i4 = z2 ? typedValuePeekValue2.type : 0;
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
            Object value = PerfettoProtoLogImpl.NULL_STRING;
            sb.append(fraction < 0.0f ? PerfettoProtoLogImpl.NULL_STRING : Float.valueOf(keyframe.getFraction()));
            sb.append(", , value : ");
            if (keyframe.hasValue()) {
                value = keyframe.getValue();
            }
            sb.append(value);
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
                Keyframe keyframeLoadKeyframe = loadKeyframe(resources, theme, Xml.asAttributeSet(xmlPullParser), i);
                if (keyframeLoadKeyframe != null) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(keyframeLoadKeyframe);
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
        PropertyValuesHolder propertyValuesHolderOfKeyframe = PropertyValuesHolder.ofKeyframe(str, keyframeArr);
        if (i == 3) {
            propertyValuesHolderOfKeyframe.setEvaluator(ArgbEvaluator.getInstance());
        }
        return propertyValuesHolderOfKeyframe;
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
        TypedArray typedArrayObtainAttributes;
        Keyframe keyframeOfInt;
        if (theme != null) {
            typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.Keyframe, 0, 0);
        } else {
            typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.Keyframe);
        }
        float f = typedArrayObtainAttributes.getFloat(3, -1.0f);
        TypedValue typedValuePeekValue = typedArrayObtainAttributes.peekValue(0);
        boolean z = typedValuePeekValue != null;
        if (i == 4) {
            i = (z && isColorType(typedValuePeekValue.type)) ? 3 : 0;
        }
        if (z) {
            if (i == 0) {
                keyframeOfInt = Keyframe.ofFloat(f, typedArrayObtainAttributes.getFloat(0, 0.0f));
            } else {
                keyframeOfInt = (i == 1 || i == 3) ? Keyframe.ofInt(f, typedArrayObtainAttributes.getInt(0, 0)) : null;
            }
        } else if (i == 0) {
            keyframeOfInt = Keyframe.ofFloat(f);
        } else {
            keyframeOfInt = Keyframe.ofInt(f);
        }
        int resourceId = typedArrayObtainAttributes.getResourceId(1, 0);
        if (resourceId > 0) {
            keyframeOfInt.setInterpolator(AnimationUtils.loadInterpolator(resources, theme, resourceId));
        }
        typedArrayObtainAttributes.recycle();
        return keyframeOfInt;
    }

    private static ObjectAnimator loadObjectAnimator(Resources resources, Resources.Theme theme, AttributeSet attributeSet, float f) throws Resources.NotFoundException {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        loadAnimator(resources, theme, attributeSet, objectAnimator, f);
        return objectAnimator;
    }

    private static ValueAnimator loadAnimator(Resources resources, Resources.Theme theme, AttributeSet attributeSet, ValueAnimator valueAnimator, float f) throws Resources.NotFoundException {
        TypedArray typedArrayObtainAttributes;
        TypedArray typedArrayObtainAttributes2;
        if (theme != null) {
            typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.Animator, 0, 0);
        } else {
            typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.Animator);
        }
        if (valueAnimator != null) {
            if (theme != null) {
                typedArrayObtainAttributes2 = theme.obtainStyledAttributes(attributeSet, R.styleable.PropertyAnimator, 0, 0);
            } else {
                typedArrayObtainAttributes2 = resources.obtainAttributes(attributeSet, R.styleable.PropertyAnimator);
            }
            valueAnimator.appendChangingConfigurations(typedArrayObtainAttributes2.getChangingConfigurations());
        } else {
            typedArrayObtainAttributes2 = null;
        }
        if (valueAnimator == null) {
            valueAnimator = new ValueAnimator();
        }
        valueAnimator.appendChangingConfigurations(typedArrayObtainAttributes.getChangingConfigurations());
        parseAnimatorFromTypeArray(valueAnimator, typedArrayObtainAttributes, typedArrayObtainAttributes2, f);
        int resourceId = typedArrayObtainAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            Interpolator interpolatorLoadInterpolator = AnimationUtils.loadInterpolator(resources, theme, resourceId);
            if (interpolatorLoadInterpolator instanceof BaseInterpolator) {
                valueAnimator.appendChangingConfigurations(((BaseInterpolator) interpolatorLoadInterpolator).getChangingConfiguration());
            }
            valueAnimator.setInterpolator(interpolatorLoadInterpolator);
        }
        typedArrayObtainAttributes.recycle();
        if (typedArrayObtainAttributes2 != null) {
            typedArrayObtainAttributes2.recycle();
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
