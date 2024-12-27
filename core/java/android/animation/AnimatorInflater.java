package android.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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

    public static Animator loadAnimator(Context context, int id)
            throws Resources.NotFoundException {
        return loadAnimator(context.getResources(), context.getTheme(), id);
    }

    public static Animator loadAnimator(Resources resources, Resources.Theme theme, int id)
            throws Resources.NotFoundException {
        return loadAnimator(resources, theme, id, 1.0f);
    }

    /*  JADX ERROR: Types fix failed
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
    */
    /* JADX WARN: Failed to apply debug info
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnoreUnknown(TypeUpdate.java:74)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:137)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:133)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.searchAndApplyVarDebugInfo(DebugInfoApplyVisitor.java:75)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.lambda$applyDebugInfo$0(DebugInfoApplyVisitor.java:68)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
     */
    /* JADX WARN: Not initialized variable reg: 10, insn: 0x00a7: MOVE (r1 I:??[OBJECT, ARRAY]) = (r10 I:??[OBJECT, ARRAY] A[D('animator' android.animation.Animator)]), block:B:44:0x00a7 */
    public static android.animation.Animator loadAnimator(
            android.content.res.Resources r12,
            android.content.res.Resources.Theme r13,
            int r14,
            float r15)
            throws android.content.res.Resources.NotFoundException {
        /*
            java.lang.String r0 = "Can't load animation resource ID #0x"
            android.content.res.ConfigurationBoundResourceCache r7 = r12.getAnimatorCache()
            long r1 = (long) r14
            java.lang.Object r1 = r7.getInstance(r1, r12, r13)
            android.animation.Animator r1 = (android.animation.Animator) r1
            if (r1 == 0) goto L10
            return r1
        L10:
            int r8 = r7.getGeneration()
            r2 = 0
            android.content.res.XmlResourceParser r3 = r12.getAnimation(r14)     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L83
            r9 = r3
            android.animation.Animator r2 = createAnimatorFromXml(r12, r13, r9, r15)     // Catch: java.lang.Throwable -> L51 java.io.IOException -> L54 org.xmlpull.v1.XmlPullParserException -> L59
            r10 = r2
            if (r10 == 0) goto L4a
            int r1 = getChangingConfigs(r12, r14)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L44 org.xmlpull.v1.XmlPullParserException -> L47
            r10.appendChangingConfigurations(r1)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L44 org.xmlpull.v1.XmlPullParserException -> L47
            android.content.res.ConstantState r1 = r10.createConstantState()     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L44 org.xmlpull.v1.XmlPullParserException -> L47
            r11 = r1
            if (r11 == 0) goto L4a
            long r2 = (long) r14     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L44 org.xmlpull.v1.XmlPullParserException -> L47
            r1 = r7
            r4 = r13
            r5 = r11
            r6 = r8
            r1.put(r2, r4, r5, r6)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L44 org.xmlpull.v1.XmlPullParserException -> L47
            java.lang.Object r1 = r11.newInstance2(r12, r13)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L44 org.xmlpull.v1.XmlPullParserException -> L47
            android.animation.Animator r1 = (android.animation.Animator) r1     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L44 org.xmlpull.v1.XmlPullParserException -> L47
            r10 = r1
            goto L4a
        L3f:
            r0 = move-exception
            r2 = r9
            r1 = r10
            goto La8
        L44:
            r1 = move-exception
            r2 = r9
            goto L63
        L47:
            r1 = move-exception
            r2 = r9
            goto L86
        L4a:
            if (r9 == 0) goto L50
            r9.close()
        L50:
            return r10
        L51:
            r0 = move-exception
            r2 = r9
            goto La8
        L54:
            r2 = move-exception
            r10 = r1
            r1 = r2
            r2 = r9
            goto L63
        L59:
            r2 = move-exception
            r10 = r1
            r1 = r2
            r2 = r9
            goto L86
        L5e:
            r0 = move-exception
            goto La8
        L60:
            r3 = move-exception
            r10 = r1
            r1 = r3
        L63:
            android.content.res.Resources$NotFoundException r3 = new android.content.res.Resources$NotFoundException     // Catch: java.lang.Throwable -> La6
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La6
            r4.<init>()     // Catch: java.lang.Throwable -> La6
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch: java.lang.Throwable -> La6
            java.lang.String r4 = java.lang.Integer.toHexString(r14)     // Catch: java.lang.Throwable -> La6
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch: java.lang.Throwable -> La6
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> La6
            r3.<init>(r0)     // Catch: java.lang.Throwable -> La6
            r0 = r3
            r0.initCause(r1)     // Catch: java.lang.Throwable -> La6
            throw r0     // Catch: java.lang.Throwable -> La6
        L83:
            r3 = move-exception
            r10 = r1
            r1 = r3
        L86:
            android.content.res.Resources$NotFoundException r3 = new android.content.res.Resources$NotFoundException     // Catch: java.lang.Throwable -> La6
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La6
            r4.<init>()     // Catch: java.lang.Throwable -> La6
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch: java.lang.Throwable -> La6
            java.lang.String r4 = java.lang.Integer.toHexString(r14)     // Catch: java.lang.Throwable -> La6
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch: java.lang.Throwable -> La6
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> La6
            r3.<init>(r0)     // Catch: java.lang.Throwable -> La6
            r0 = r3
            r0.initCause(r1)     // Catch: java.lang.Throwable -> La6
            throw r0     // Catch: java.lang.Throwable -> La6
        La6:
            r0 = move-exception
            r1 = r10
        La8:
            if (r2 == 0) goto Lad
            r2.close()
        Lad:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.animation.AnimatorInflater.loadAnimator(android.content.res.Resources,"
                    + " android.content.res.Resources$Theme, int,"
                    + " float):android.animation.Animator");
    }

    /*  JADX ERROR: Types fix failed
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
    */
    /* JADX WARN: Failed to apply debug info
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnoreUnknown(TypeUpdate.java:74)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:137)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:133)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.searchAndApplyVarDebugInfo(DebugInfoApplyVisitor.java:75)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.lambda$applyDebugInfo$0(DebugInfoApplyVisitor.java:68)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
     */
    /* JADX WARN: Not initialized variable reg: 12, insn: 0x00b6: MOVE (r2 I:??[OBJECT, ARRAY]) = (r12 I:??[OBJECT, ARRAY] A[D('animator' android.animation.StateListAnimator)]), block:B:44:0x00b6 */
    public static android.animation.StateListAnimator loadStateListAnimator(
            android.content.Context r14, int r15)
            throws android.content.res.Resources.NotFoundException {
        /*
            java.lang.String r0 = "Can't load state list animator resource ID #0x"
            android.content.res.Resources r1 = r14.getResources()
            android.content.res.ConfigurationBoundResourceCache r8 = r1.getStateListAnimatorCache()
            android.content.res.Resources$Theme r9 = r14.getTheme()
            long r2 = (long) r15
            java.lang.Object r2 = r8.getInstance(r2, r1, r9)
            android.animation.StateListAnimator r2 = (android.animation.StateListAnimator) r2
            if (r2 == 0) goto L19
            return r2
        L19:
            int r10 = r8.getGeneration()
            r3 = 0
            android.content.res.XmlResourceParser r4 = r1.getAnimation(r15)     // Catch: java.lang.Throwable -> L6d java.io.IOException -> L6f org.xmlpull.v1.XmlPullParserException -> L92
            r11 = r4
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r11)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L63 org.xmlpull.v1.XmlPullParserException -> L68
            android.animation.StateListAnimator r3 = createStateListAnimatorFromXml(r14, r11, r3)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L63 org.xmlpull.v1.XmlPullParserException -> L68
            r12 = r3
            if (r12 == 0) goto L59
            int r2 = getChangingConfigs(r1, r15)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L53 org.xmlpull.v1.XmlPullParserException -> L56
            r12.appendChangingConfigurations(r2)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L53 org.xmlpull.v1.XmlPullParserException -> L56
            android.content.res.ConstantState r2 = r12.createConstantState()     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L53 org.xmlpull.v1.XmlPullParserException -> L56
            r13 = r2
            if (r13 == 0) goto L59
            long r3 = (long) r15     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L53 org.xmlpull.v1.XmlPullParserException -> L56
            r2 = r8
            r5 = r9
            r6 = r13
            r7 = r10
            r2.put(r3, r5, r6, r7)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L53 org.xmlpull.v1.XmlPullParserException -> L56
            java.lang.Object r2 = r13.newInstance2(r1, r9)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L53 org.xmlpull.v1.XmlPullParserException -> L56
            android.animation.StateListAnimator r2 = (android.animation.StateListAnimator) r2     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L53 org.xmlpull.v1.XmlPullParserException -> L56
            r12 = r2
            goto L59
        L4e:
            r0 = move-exception
            r3 = r11
            r2 = r12
            goto Lb7
        L53:
            r2 = move-exception
            r3 = r11
            goto L72
        L56:
            r2 = move-exception
            r3 = r11
            goto L95
        L59:
            if (r11 == 0) goto L5f
            r11.close()
        L5f:
            return r12
        L60:
            r0 = move-exception
            r3 = r11
            goto Lb7
        L63:
            r3 = move-exception
            r12 = r2
            r2 = r3
            r3 = r11
            goto L72
        L68:
            r3 = move-exception
            r12 = r2
            r2 = r3
            r3 = r11
            goto L95
        L6d:
            r0 = move-exception
            goto Lb7
        L6f:
            r4 = move-exception
            r12 = r2
            r2 = r4
        L72:
            android.content.res.Resources$NotFoundException r4 = new android.content.res.Resources$NotFoundException     // Catch: java.lang.Throwable -> Lb5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb5
            r5.<init>()     // Catch: java.lang.Throwable -> Lb5
            java.lang.StringBuilder r0 = r5.append(r0)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r5 = java.lang.Integer.toHexString(r15)     // Catch: java.lang.Throwable -> Lb5
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Lb5
            r4.<init>(r0)     // Catch: java.lang.Throwable -> Lb5
            r0 = r4
            r0.initCause(r2)     // Catch: java.lang.Throwable -> Lb5
            throw r0     // Catch: java.lang.Throwable -> Lb5
        L92:
            r4 = move-exception
            r12 = r2
            r2 = r4
        L95:
            android.content.res.Resources$NotFoundException r4 = new android.content.res.Resources$NotFoundException     // Catch: java.lang.Throwable -> Lb5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb5
            r5.<init>()     // Catch: java.lang.Throwable -> Lb5
            java.lang.StringBuilder r0 = r5.append(r0)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r5 = java.lang.Integer.toHexString(r15)     // Catch: java.lang.Throwable -> Lb5
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Lb5
            r4.<init>(r0)     // Catch: java.lang.Throwable -> Lb5
            r0 = r4
            r0.initCause(r2)     // Catch: java.lang.Throwable -> Lb5
            throw r0     // Catch: java.lang.Throwable -> Lb5
        Lb5:
            r0 = move-exception
            r2 = r12
        Lb7:
            if (r3 == 0) goto Lbc
            r3.close()
        Lbc:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.animation.AnimatorInflater.loadStateListAnimator(android.content.Context,"
                    + " int):android.animation.StateListAnimator");
    }

    private static StateListAnimator createStateListAnimatorFromXml(
            Context context, XmlPullParser parser, AttributeSet attributeSet)
            throws IOException, XmlPullParserException {
        StateListAnimator stateListAnimator = new StateListAnimator();
        while (true) {
            int type = parser.next();
            switch (type) {
                case 1:
                case 3:
                    return stateListAnimator;
                case 2:
                    Animator animator = null;
                    if (ImsConfig.EXTRA_CHANGED_ITEM.equals(parser.getName())) {
                        int attributeCount = parser.getAttributeCount();
                        int[] states = new int[attributeCount];
                        int stateIndex = 0;
                        for (int i = 0; i < attributeCount; i++) {
                            int attrName = attributeSet.getAttributeNameResource(i);
                            if (attrName == 16843213) {
                                int animId = attributeSet.getAttributeResourceValue(i, 0);
                                animator = loadAnimator(context, animId);
                            } else {
                                int stateIndex2 = stateIndex + 1;
                                states[stateIndex] =
                                        attributeSet.getAttributeBooleanValue(i, false)
                                                ? attrName
                                                : -attrName;
                                stateIndex = stateIndex2;
                            }
                        }
                        if (animator == null) {
                            animator =
                                    createAnimatorFromXml(
                                            context.getResources(),
                                            context.getTheme(),
                                            parser,
                                            1.0f);
                        }
                        if (animator == null) {
                            throw new Resources.NotFoundException(
                                    "animation state item must have a valid animation");
                        }
                        stateListAnimator.addState(
                                StateSet.trimStateSet(states, stateIndex), animator);
                        break;
                    } else {
                        continue;
                    }
            }
        }
    }

    private static class PathDataEvaluator implements TypeEvaluator<PathParser.PathData> {
        private final PathParser.PathData mPathData;

        private PathDataEvaluator() {
            this.mPathData = new PathParser.PathData();
        }

        @Override // android.animation.TypeEvaluator
        public PathParser.PathData evaluate(
                float fraction,
                PathParser.PathData startPathData,
                PathParser.PathData endPathData) {
            if (!PathParser.interpolatePathData(
                    this.mPathData, startPathData, endPathData, fraction)) {
                throw new IllegalArgumentException(
                        "Can't interpolate between two incompatible pathData");
            }
            return this.mPathData;
        }
    }

    private static PropertyValuesHolder getPVH(
            TypedArray styledAttributes,
            int valueType,
            int valueFromId,
            int valueToId,
            String propertyName) {
        int valueType2;
        PropertyValuesHolder returnValue;
        int valueTo;
        int valueFrom;
        int valueTo2;
        float valueTo3;
        float valueFrom2;
        int i;
        float valueTo4;
        int toType;
        PropertyValuesHolder propertyValuesHolder;
        PropertyValuesHolder returnValue2;
        TypedValue tvFrom = styledAttributes.peekValue(valueFromId);
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : 0;
        TypedValue tvTo = styledAttributes.peekValue(valueToId);
        boolean hasTo = tvTo != null;
        int toType2 = hasTo ? tvTo.type : 0;
        if (valueType != 4) {
            valueType2 = valueType;
        } else if ((hasFrom && isColorType(fromType)) || (hasTo && isColorType(toType2))) {
            valueType2 = 3;
        } else {
            valueType2 = 0;
        }
        boolean getFloats = valueType2 == 0;
        if (valueType2 == 2) {
            String fromString = styledAttributes.getString(valueFromId);
            String toString = styledAttributes.getString(valueToId);
            PathParser.PathData nodesFrom =
                    fromString == null ? null : new PathParser.PathData(fromString);
            PathParser.PathData nodesTo =
                    toString == null ? null : new PathParser.PathData(toString);
            if (nodesFrom == null && nodesTo == null) {
                toType = toType2;
                propertyValuesHolder = null;
            } else {
                if (nodesFrom == null) {
                    toType = toType2;
                    propertyValuesHolder = null;
                    if (nodesTo != null) {
                        returnValue2 =
                                PropertyValuesHolder.ofObject(
                                        propertyName, new PathDataEvaluator(), nodesTo);
                    }
                } else {
                    TypeEvaluator evaluator = new PathDataEvaluator();
                    if (nodesTo != null) {
                        if (!PathParser.canMorph(nodesFrom, nodesTo)) {
                            throw new InflateException(
                                    " Can't morph from " + fromString + " to " + toString);
                        }
                        returnValue2 =
                                PropertyValuesHolder.ofObject(
                                        propertyName, evaluator, nodesFrom, nodesTo);
                        toType = toType2;
                    } else {
                        toType = toType2;
                        returnValue2 =
                                PropertyValuesHolder.ofObject(propertyName, evaluator, nodesFrom);
                    }
                }
                return returnValue2;
            }
            returnValue2 = propertyValuesHolder;
            return returnValue2;
        }
        int toType3 = toType2;
        TypeEvaluator evaluator2 = valueType2 == 3 ? ArgbEvaluator.getInstance() : null;
        if (getFloats) {
            if (!hasFrom) {
                if (toType3 == 5) {
                    valueTo3 = styledAttributes.getDimension(valueToId, 0.0f);
                } else {
                    valueTo3 = styledAttributes.getFloat(valueToId, 0.0f);
                }
                returnValue = PropertyValuesHolder.ofFloat(propertyName, valueTo3);
            } else {
                if (fromType == 5) {
                    valueFrom2 = styledAttributes.getDimension(valueFromId, 0.0f);
                } else {
                    valueFrom2 = styledAttributes.getFloat(valueFromId, 0.0f);
                }
                if (!hasTo) {
                    returnValue = PropertyValuesHolder.ofFloat(propertyName, valueFrom2);
                } else {
                    if (toType3 == 5) {
                        i = valueToId;
                        valueTo4 = styledAttributes.getDimension(i, 0.0f);
                    } else {
                        i = valueToId;
                        valueTo4 = styledAttributes.getFloat(i, 0.0f);
                    }
                    returnValue = PropertyValuesHolder.ofFloat(propertyName, valueFrom2, valueTo4);
                }
            }
        } else if (hasFrom) {
            if (fromType == 5) {
                valueFrom = (int) styledAttributes.getDimension(valueFromId, 0.0f);
            } else if (isColorType(fromType)) {
                valueFrom = styledAttributes.getColor(valueFromId, 0);
            } else {
                valueFrom = styledAttributes.getInt(valueFromId, 0);
            }
            if (hasTo) {
                if (toType3 == 5) {
                    valueTo2 = (int) styledAttributes.getDimension(valueToId, 0.0f);
                } else if (isColorType(toType3)) {
                    valueTo2 = styledAttributes.getColor(valueToId, 0);
                } else {
                    valueTo2 = styledAttributes.getInt(valueToId, 0);
                }
                returnValue = PropertyValuesHolder.ofInt(propertyName, valueFrom, valueTo2);
            } else {
                returnValue = PropertyValuesHolder.ofInt(propertyName, valueFrom);
            }
        } else if (!hasTo) {
            returnValue = null;
        } else {
            if (toType3 == 5) {
                valueTo = (int) styledAttributes.getDimension(valueToId, 0.0f);
            } else if (isColorType(toType3)) {
                valueTo = styledAttributes.getColor(valueToId, 0);
            } else {
                valueTo = styledAttributes.getInt(valueToId, 0);
            }
            returnValue = PropertyValuesHolder.ofInt(propertyName, valueTo);
        }
        if (returnValue != null && evaluator2 != null) {
            returnValue.setEvaluator(evaluator2);
            return returnValue;
        }
        return returnValue;
    }

    private static void parseAnimatorFromTypeArray(
            ValueAnimator anim,
            TypedArray arrayAnimator,
            TypedArray arrayObjectAnimator,
            float pixelSize) {
        long duration = arrayAnimator.getInt(1, 300);
        long startDelay = arrayAnimator.getInt(2, 0);
        int valueType = arrayAnimator.getInt(7, 4);
        if (valueType == 4) {
            valueType = inferValueTypeFromValues(arrayAnimator, 5, 6);
        }
        PropertyValuesHolder pvh = getPVH(arrayAnimator, valueType, 5, 6, "");
        if (pvh != null) {
            anim.setValues(pvh);
        }
        anim.setDuration(duration);
        anim.setStartDelay(startDelay);
        if (arrayAnimator.hasValue(3)) {
            anim.setRepeatCount(arrayAnimator.getInt(3, 0));
        }
        if (arrayAnimator.hasValue(4)) {
            anim.setRepeatMode(arrayAnimator.getInt(4, 1));
        }
        if (arrayObjectAnimator != null) {
            setupObjectAnimator(anim, arrayObjectAnimator, valueType, pixelSize);
        }
    }

    private static TypeEvaluator setupAnimatorForPath(
            ValueAnimator anim, TypedArray arrayAnimator) {
        String fromString = arrayAnimator.getString(5);
        String toString = arrayAnimator.getString(6);
        PathParser.PathData pathDataFrom =
                fromString == null ? null : new PathParser.PathData(fromString);
        PathParser.PathData pathDataTo =
                toString == null ? null : new PathParser.PathData(toString);
        if (pathDataFrom != null) {
            if (pathDataTo != null) {
                anim.setObjectValues(pathDataFrom, pathDataTo);
                if (!PathParser.canMorph(pathDataFrom, pathDataTo)) {
                    throw new InflateException(
                            arrayAnimator.getPositionDescription()
                                    + " Can't morph from "
                                    + fromString
                                    + " to "
                                    + toString);
                }
            } else {
                anim.setObjectValues(pathDataFrom);
            }
            TypeEvaluator evaluator = new PathDataEvaluator();
            return evaluator;
        }
        if (pathDataTo == null) {
            return null;
        }
        anim.setObjectValues(pathDataTo);
        TypeEvaluator evaluator2 = new PathDataEvaluator();
        return evaluator2;
    }

    private static void setupObjectAnimator(
            ValueAnimator anim, TypedArray arrayObjectAnimator, int valueType, float pixelSize) {
        Keyframes xKeyframes;
        Keyframes yKeyframes;
        int valueType2 = valueType;
        ObjectAnimator oa = (ObjectAnimator) anim;
        String pathData = arrayObjectAnimator.getString(1);
        if (pathData != null) {
            String propertyXName = arrayObjectAnimator.getString(2);
            String propertyYName = arrayObjectAnimator.getString(3);
            if (valueType2 == 2 || valueType2 == 4) {
                valueType2 = 0;
            }
            if (propertyXName == null && propertyYName == null) {
                throw new InflateException(
                        arrayObjectAnimator.getPositionDescription()
                                + " propertyXName or propertyYName is needed for PathData");
            }
            Path path = PathParser.createPathFromPathData(pathData);
            float error = 0.5f * pixelSize;
            PathKeyframes keyframeSet = KeyframeSet.ofPath(path, error);
            if (valueType2 == 0) {
                xKeyframes = keyframeSet.createXFloatKeyframes();
                yKeyframes = keyframeSet.createYFloatKeyframes();
            } else {
                xKeyframes = keyframeSet.createXIntKeyframes();
                yKeyframes = keyframeSet.createYIntKeyframes();
            }
            PropertyValuesHolder x = null;
            PropertyValuesHolder y = null;
            if (propertyXName != null) {
                x = PropertyValuesHolder.ofKeyframes(propertyXName, xKeyframes);
            }
            if (propertyYName != null) {
                y = PropertyValuesHolder.ofKeyframes(propertyYName, yKeyframes);
            }
            if (x == null) {
                oa.setValues(y);
                return;
            } else if (y == null) {
                oa.setValues(x);
                return;
            } else {
                oa.setValues(x, y);
                return;
            }
        }
        String propertyName = arrayObjectAnimator.getString(0);
        oa.setPropertyName(propertyName);
    }

    private static void setupValues(
            ValueAnimator anim,
            TypedArray arrayAnimator,
            boolean getFloats,
            boolean hasFrom,
            int fromType,
            boolean hasTo,
            int toType) {
        int valueTo;
        int valueFrom;
        int valueTo2;
        float valueTo3;
        float valueFrom2;
        float valueTo4;
        if (getFloats) {
            if (hasFrom) {
                if (fromType == 5) {
                    valueFrom2 = arrayAnimator.getDimension(5, 0.0f);
                } else {
                    valueFrom2 = arrayAnimator.getFloat(5, 0.0f);
                }
                if (hasTo) {
                    if (toType == 5) {
                        valueTo4 = arrayAnimator.getDimension(6, 0.0f);
                    } else {
                        valueTo4 = arrayAnimator.getFloat(6, 0.0f);
                    }
                    anim.setFloatValues(valueFrom2, valueTo4);
                    return;
                }
                anim.setFloatValues(valueFrom2);
                return;
            }
            if (toType == 5) {
                valueTo3 = arrayAnimator.getDimension(6, 0.0f);
            } else {
                valueTo3 = arrayAnimator.getFloat(6, 0.0f);
            }
            anim.setFloatValues(valueTo3);
            return;
        }
        if (hasFrom) {
            if (fromType == 5) {
                valueFrom = (int) arrayAnimator.getDimension(5, 0.0f);
            } else if (isColorType(fromType)) {
                valueFrom = arrayAnimator.getColor(5, 0);
            } else {
                valueFrom = arrayAnimator.getInt(5, 0);
            }
            if (hasTo) {
                if (toType == 5) {
                    valueTo2 = (int) arrayAnimator.getDimension(6, 0.0f);
                } else if (isColorType(toType)) {
                    valueTo2 = arrayAnimator.getColor(6, 0);
                } else {
                    valueTo2 = arrayAnimator.getInt(6, 0);
                }
                anim.setIntValues(valueFrom, valueTo2);
                return;
            }
            anim.setIntValues(valueFrom);
            return;
        }
        if (hasTo) {
            if (toType == 5) {
                valueTo = (int) arrayAnimator.getDimension(6, 0.0f);
            } else if (isColorType(toType)) {
                valueTo = arrayAnimator.getColor(6, 0);
            } else {
                valueTo = arrayAnimator.getInt(6, 0);
            }
            anim.setIntValues(valueTo);
        }
    }

    private static Animator createAnimatorFromXml(
            Resources res, Resources.Theme theme, XmlPullParser parser, float pixelSize)
            throws XmlPullParserException, IOException {
        return createAnimatorFromXml(
                res, theme, parser, Xml.asAttributeSet(parser), null, 0, pixelSize);
    }

    private static Animator createAnimatorFromXml(
            Resources res,
            Resources.Theme theme,
            XmlPullParser parser,
            AttributeSet attrs,
            AnimatorSet parent,
            int sequenceOrdering,
            float pixelSize)
            throws XmlPullParserException, IOException {
        TypedArray a;
        Animator anim = null;
        int depth = parser.getDepth();
        ArrayList<Animator> childAnims = null;
        while (true) {
            int type = parser.next();
            if ((type != 3 || parser.getDepth() > depth) && type != 1) {
                if (type == 2) {
                    String name = parser.getName();
                    boolean gotValues = false;
                    if (name.equals("objectAnimator")) {
                        anim = loadObjectAnimator(res, theme, attrs, pixelSize);
                    } else if (name.equals("animator")) {
                        anim = loadAnimator(res, theme, attrs, null, pixelSize);
                    } else if (name.equals("set")) {
                        Animator anim2 = new AnimatorSet();
                        if (theme == null) {
                            a = res.obtainAttributes(attrs, R.styleable.AnimatorSet);
                        } else {
                            a = theme.obtainStyledAttributes(attrs, R.styleable.AnimatorSet, 0, 0);
                        }
                        anim2.appendChangingConfigurations(a.getChangingConfigurations());
                        int ordering = a.getInt(0, 0);
                        createAnimatorFromXml(
                                res,
                                theme,
                                parser,
                                attrs,
                                (AnimatorSet) anim2,
                                ordering,
                                pixelSize);
                        a.recycle();
                        anim = anim2;
                    } else {
                        if (!name.equals("propertyValuesHolder")) {
                            throw new RuntimeException(
                                    "Unknown animator name: " + parser.getName());
                        }
                        PropertyValuesHolder[] values =
                                loadValues(res, theme, parser, Xml.asAttributeSet(parser));
                        if (values != null && anim != null && (anim instanceof ValueAnimator)) {
                            ((ValueAnimator) anim).setValues(values);
                        }
                        gotValues = true;
                    }
                    if (parent != null && !gotValues) {
                        if (childAnims == null) {
                            childAnims = new ArrayList<>();
                        }
                        childAnims.add(anim);
                    }
                }
            }
        }
        if (parent != null && childAnims != null) {
            Animator[] animsArray = new Animator[childAnims.size()];
            int index = 0;
            Iterator<Animator> it = childAnims.iterator();
            while (it.hasNext()) {
                Animator a2 = it.next();
                animsArray[index] = a2;
                index++;
            }
            if (sequenceOrdering == 0) {
                parent.playTogether(animsArray);
            } else {
                parent.playSequentially(animsArray);
            }
        }
        return anim;
    }

    private static PropertyValuesHolder[] loadValues(
            Resources res, Resources.Theme theme, XmlPullParser parser, AttributeSet attrs)
            throws XmlPullParserException, IOException {
        TypedArray a;
        ArrayList<PropertyValuesHolder> values = null;
        while (true) {
            int type = parser.getEventType();
            if (type == 3 || type == 1) {
                break;
            }
            if (type != 2) {
                parser.next();
            } else {
                String name = parser.getName();
                if (name.equals("propertyValuesHolder")) {
                    if (theme != null) {
                        a =
                                theme.obtainStyledAttributes(
                                        attrs, R.styleable.PropertyValuesHolder, 0, 0);
                    } else {
                        a = res.obtainAttributes(attrs, R.styleable.PropertyValuesHolder);
                    }
                    String propertyName = a.getString(3);
                    int valueType = a.getInt(2, 4);
                    PropertyValuesHolder pvh = loadPvh(res, theme, parser, propertyName, valueType);
                    if (pvh == null) {
                        pvh = getPVH(a, valueType, 0, 1, propertyName);
                    }
                    if (pvh != null) {
                        if (values == null) {
                            values = new ArrayList<>();
                        }
                        values.add(pvh);
                    }
                    a.recycle();
                }
                parser.next();
            }
        }
        PropertyValuesHolder[] valuesArray = null;
        if (values != null) {
            int count = values.size();
            valuesArray = new PropertyValuesHolder[count];
            for (int i = 0; i < count; i++) {
                valuesArray[i] = values.get(i);
            }
        }
        return valuesArray;
    }

    private static int inferValueTypeOfKeyframe(
            Resources res, Resources.Theme theme, AttributeSet attrs) {
        TypedArray a;
        int valueType;
        if (theme != null) {
            a = theme.obtainStyledAttributes(attrs, R.styleable.Keyframe, 0, 0);
        } else {
            a = res.obtainAttributes(attrs, R.styleable.Keyframe);
        }
        TypedValue keyframeValue = a.peekValue(0);
        boolean hasValue = keyframeValue != null;
        if (hasValue && isColorType(keyframeValue.type)) {
            valueType = 3;
        } else {
            valueType = 0;
        }
        a.recycle();
        return valueType;
    }

    private static int inferValueTypeFromValues(
            TypedArray styledAttributes, int valueFromId, int valueToId) {
        TypedValue tvFrom = styledAttributes.peekValue(valueFromId);
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : 0;
        TypedValue tvTo = styledAttributes.peekValue(valueToId);
        boolean hasTo = tvTo != null;
        int toType = hasTo ? tvTo.type : 0;
        if ((hasFrom && isColorType(fromType)) || (hasTo && isColorType(toType))) {
            return 3;
        }
        return 0;
    }

    private static void dumpKeyframes(Object[] keyframes, String header) {
        if (keyframes == null || keyframes.length == 0) {
            return;
        }
        Log.d(TAG, header);
        int count = keyframes.length;
        for (int i = 0; i < count; i++) {
            Keyframe keyframe = (Keyframe) keyframes[i];
            Object obj = "null";
            StringBuilder append =
                    new StringBuilder()
                            .append("Keyframe ")
                            .append(i)
                            .append(": fraction ")
                            .append(
                                    keyframe.getFraction() < 0.0f
                                            ? "null"
                                            : Float.valueOf(keyframe.getFraction()))
                            .append(", , value : ");
            if (keyframe.hasValue()) {
                obj = keyframe.getValue();
            }
            Log.d(TAG, append.append(obj).toString());
        }
    }

    private static PropertyValuesHolder loadPvh(
            Resources res,
            Resources.Theme theme,
            XmlPullParser parser,
            String propertyName,
            int valueType)
            throws XmlPullParserException, IOException {
        int i;
        PropertyValuesHolder value = null;
        ArrayList<Keyframe> keyframes = null;
        int valueType2 = valueType;
        while (true) {
            int type = parser.next();
            if (type == 3 || type == 1) {
                break;
            }
            String name = parser.getName();
            if (name.equals("keyframe")) {
                if (valueType2 == 4) {
                    valueType2 = inferValueTypeOfKeyframe(res, theme, Xml.asAttributeSet(parser));
                }
                Keyframe keyframe =
                        loadKeyframe(res, theme, Xml.asAttributeSet(parser), valueType2);
                if (keyframe != null) {
                    if (keyframes == null) {
                        keyframes = new ArrayList<>();
                    }
                    keyframes.add(keyframe);
                }
                parser.next();
            }
        }
        if (keyframes != null) {
            int size = keyframes.size();
            int count = size;
            if (size > 0) {
                Keyframe firstKeyframe = keyframes.get(0);
                Keyframe lastKeyframe = keyframes.get(count - 1);
                float endFraction = lastKeyframe.getFraction();
                float f = 1.0f;
                int startIndex = 0;
                if (endFraction < 1.0f) {
                    if (endFraction >= 0.0f) {
                        keyframes.add(keyframes.size(), createNewKeyframe(lastKeyframe, 1.0f));
                        count++;
                    } else {
                        lastKeyframe.setFraction(1.0f);
                    }
                }
                float startFraction = firstKeyframe.getFraction();
                if (startFraction != 0.0f) {
                    if (startFraction >= 0.0f) {
                        keyframes.add(0, createNewKeyframe(firstKeyframe, 0.0f));
                        count++;
                    } else {
                        firstKeyframe.setFraction(0.0f);
                    }
                }
                Keyframe[] keyframeArray = new Keyframe[count];
                keyframes.toArray(keyframeArray);
                int i2 = 0;
                while (i2 < count) {
                    Keyframe keyframe2 = keyframeArray[i2];
                    if (keyframe2.getFraction() >= startIndex) {
                        i = startIndex;
                    } else if (i2 == 0) {
                        keyframe2.setFraction(startIndex);
                        i = startIndex;
                    } else if (i2 == count - 1) {
                        keyframe2.setFraction(f);
                        i = 0;
                    } else {
                        int startIndex2 = i2;
                        int endIndex = i2;
                        int endIndex2 = endIndex;
                        int j = startIndex2 + 1;
                        while (true) {
                            if (j >= count - 1) {
                                i = 0;
                                break;
                            }
                            i = 0;
                            if (keyframeArray[j].getFraction() >= 0.0f) {
                                break;
                            }
                            endIndex2 = j;
                            j++;
                        }
                        int j2 = endIndex2 + 1;
                        float gap =
                                keyframeArray[j2].getFraction()
                                        - keyframeArray[startIndex2 - 1].getFraction();
                        distributeKeyframes(keyframeArray, gap, startIndex2, endIndex2);
                    }
                    i2++;
                    f = 1.0f;
                    startIndex = i;
                }
                value = PropertyValuesHolder.ofKeyframe(propertyName, keyframeArray);
                if (valueType2 == 3) {
                    value.setEvaluator(ArgbEvaluator.getInstance());
                }
            }
        }
        return value;
    }

    private static Keyframe createNewKeyframe(Keyframe sampleKeyframe, float fraction) {
        if (sampleKeyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat(fraction);
        }
        if (sampleKeyframe.getType() == Integer.TYPE) {
            return Keyframe.ofInt(fraction);
        }
        return Keyframe.ofObject(fraction);
    }

    private static void distributeKeyframes(
            Keyframe[] keyframes, float gap, int startIndex, int endIndex) {
        int count = (endIndex - startIndex) + 2;
        float increment = gap / count;
        for (int i = startIndex; i <= endIndex; i++) {
            keyframes[i].setFraction(keyframes[i - 1].getFraction() + increment);
        }
    }

    private static Keyframe loadKeyframe(
            Resources res, Resources.Theme theme, AttributeSet attrs, int valueType)
            throws XmlPullParserException, IOException {
        TypedArray a;
        boolean hasValue;
        if (theme != null) {
            a = theme.obtainStyledAttributes(attrs, R.styleable.Keyframe, 0, 0);
        } else {
            a = res.obtainAttributes(attrs, R.styleable.Keyframe);
        }
        Keyframe keyframe = null;
        float fraction = a.getFloat(3, -1.0f);
        TypedValue keyframeValue = a.peekValue(0);
        if (keyframeValue == null) {
            hasValue = false;
        } else {
            hasValue = true;
        }
        if (valueType == 4) {
            if (hasValue && isColorType(keyframeValue.type)) {
                valueType = 3;
            } else {
                valueType = 0;
            }
        }
        if (hasValue) {
            switch (valueType) {
                case 0:
                    float value = a.getFloat(0, 0.0f);
                    keyframe = Keyframe.ofFloat(fraction, value);
                    break;
                case 1:
                case 3:
                    int intValue = a.getInt(0, 0);
                    keyframe = Keyframe.ofInt(fraction, intValue);
                    break;
            }
        } else {
            keyframe = valueType == 0 ? Keyframe.ofFloat(fraction) : Keyframe.ofInt(fraction);
        }
        int resID = a.getResourceId(1, 0);
        if (resID > 0) {
            Interpolator interpolator = AnimationUtils.loadInterpolator(res, theme, resID);
            keyframe.setInterpolator(interpolator);
        }
        a.recycle();
        return keyframe;
    }

    private static ObjectAnimator loadObjectAnimator(
            Resources res, Resources.Theme theme, AttributeSet attrs, float pathErrorScale)
            throws Resources.NotFoundException {
        ObjectAnimator anim = new ObjectAnimator();
        loadAnimator(res, theme, attrs, anim, pathErrorScale);
        return anim;
    }

    private static ValueAnimator loadAnimator(
            Resources res,
            Resources.Theme theme,
            AttributeSet attrs,
            ValueAnimator anim,
            float pathErrorScale)
            throws Resources.NotFoundException {
        TypedArray arrayAnimator;
        TypedArray arrayObjectAnimator = null;
        if (theme != null) {
            arrayAnimator = theme.obtainStyledAttributes(attrs, R.styleable.Animator, 0, 0);
        } else {
            arrayAnimator = res.obtainAttributes(attrs, R.styleable.Animator);
        }
        if (anim != null) {
            if (theme != null) {
                arrayObjectAnimator =
                        theme.obtainStyledAttributes(attrs, R.styleable.PropertyAnimator, 0, 0);
            } else {
                arrayObjectAnimator = res.obtainAttributes(attrs, R.styleable.PropertyAnimator);
            }
            anim.appendChangingConfigurations(arrayObjectAnimator.getChangingConfigurations());
        }
        if (anim == null) {
            anim = new ValueAnimator();
        }
        anim.appendChangingConfigurations(arrayAnimator.getChangingConfigurations());
        parseAnimatorFromTypeArray(anim, arrayAnimator, arrayObjectAnimator, pathErrorScale);
        int resID = arrayAnimator.getResourceId(0, 0);
        if (resID > 0) {
            Interpolator interpolator = AnimationUtils.loadInterpolator(res, theme, resID);
            if (interpolator instanceof BaseInterpolator) {
                anim.appendChangingConfigurations(
                        ((BaseInterpolator) interpolator).getChangingConfiguration());
            }
            anim.setInterpolator(interpolator);
        }
        arrayAnimator.recycle();
        if (arrayObjectAnimator != null) {
            arrayObjectAnimator.recycle();
        }
        return anim;
    }

    private static int getChangingConfigs(Resources resources, int id) {
        int i;
        synchronized (sTmpTypedValue) {
            resources.getValue(id, sTmpTypedValue, true);
            i = sTmpTypedValue.changingConfigurations;
        }
        return i;
    }

    private static boolean isColorType(int type) {
        return type >= 28 && type <= 31;
    }
}
