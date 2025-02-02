package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R$styleable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class LogJson extends ConstraintHelper {
    private int mDelay;
    private boolean mLogConsole;
    private String mLogToFile;
    private int mMode;
    private boolean mPeriodic;

    public static void $r8$lambda$TrDMQ9reyW_61Fo0RCam71GwuYw(LogJson logJson) {
        if (logJson.mPeriodic) {
            logJson.writeLog();
            logJson.postDelayed(new LogJson$$ExternalSyntheticLambda0(logJson, 2), logJson.mDelay);
        }
    }

    public static void $r8$lambda$zdm4ZGyCN7ReSTKwTzMWVPBjVr8(LogJson logJson) {
        if (logJson.mMode == 3) {
            logJson.writeLog();
        }
    }

    public LogJson(Context context) {
        super(context);
        this.mDelay = 1000;
        this.mMode = 2;
        this.mLogToFile = null;
        this.mLogConsole = true;
        this.mPeriodic = false;
    }

    private void initLogJson(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.LogJson);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 0) {
                    this.mDelay = obtainStyledAttributes.getInt(index, this.mDelay);
                } else if (index == 1) {
                    this.mMode = obtainStyledAttributes.getInt(index, this.mMode);
                } else if (index == 2) {
                    if (obtainStyledAttributes.peekValue(index).type == 3) {
                        this.mLogToFile = obtainStyledAttributes.getString(index);
                    } else {
                        this.mLogConsole = obtainStyledAttributes.getInt(index, 0) == 2;
                    }
                }
            }
            obtainStyledAttributes.recycle();
        }
        setVisibility(8);
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        int i = this.mMode;
        int i2 = 1;
        if (i == 1) {
            this.mPeriodic = true;
            postDelayed(new LogJson$$ExternalSyntheticLambda0(this, 0), this.mDelay);
        } else if (i == 2) {
            postDelayed(new LogJson$$ExternalSyntheticLambda0(this, i2), this.mDelay);
        } else {
            if (i != 3) {
                return;
            }
            ((ConstraintLayout) getParent()).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.constraintlayout.helper.widget.LogJson$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                    LogJson.$r8$lambda$zdm4ZGyCN7ReSTKwTzMWVPBjVr8(LogJson.this);
                }
            });
        }
    }

    public void setDelay(int i) {
        this.mDelay = i;
    }

    public final void writeLog() {
        String iOException;
        String constraintLayoutToJson = new JsonWriter().constraintLayoutToJson((ConstraintLayout) getParent());
        String str = this.mLogToFile;
        if (str == null) {
            if (this.mLogConsole) {
                System.out.println(constraintLayoutToJson);
                return;
            }
            int length = constraintLayoutToJson.length();
            int i = 0;
            while (i < length) {
                int indexOf = constraintLayoutToJson.indexOf("\n", i);
                if (indexOf == -1) {
                    Log.v("JSON5", constraintLayoutToJson.substring(i));
                    return;
                } else {
                    Log.v("JSON5", constraintLayoutToJson.substring(i, indexOf));
                    i = indexOf + 1;
                }
            }
            return;
        }
        if (!str.endsWith(".json5")) {
            str = str.concat(".json5");
        }
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), str);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(constraintLayoutToJson.getBytes());
            fileOutputStream.close();
            iOException = file.getCanonicalPath();
        } catch (IOException e) {
            iOException = e.toString();
        }
        Log.v("JSON", "\"" + iOException + "\" written!");
    }

    private static class JsonWriter {
        Context mContext;
        ConstraintSet mSet;
        Writer mWriter;
        int mUnknownCount = 0;
        HashMap<Integer, String> mIdMap = new HashMap<>();
        HashMap<Integer, String> mNames = new HashMap<>();

        static {
            new AtomicInteger(1);
        }

        JsonWriter() {
        }

        private String getName(int i) {
            return "'" + getSimpleName(i) + "'";
        }

        private String getSimpleName(int i) {
            String sb;
            if (this.mIdMap.containsKey(Integer.valueOf(i))) {
                return "" + this.mIdMap.get(Integer.valueOf(i));
            }
            if (i == 0) {
                return "parent";
            }
            try {
                if (this.mNames.containsKey(Integer.valueOf(i))) {
                    sb = this.mNames.get(Integer.valueOf(i));
                } else if (i != -1) {
                    sb = this.mContext.getResources().getResourceEntryName(i);
                } else {
                    StringBuilder sb2 = new StringBuilder("unknown");
                    int i2 = this.mUnknownCount + 1;
                    this.mUnknownCount = i2;
                    sb2.append(i2);
                    sb = sb2.toString();
                }
            } catch (Exception unused) {
                StringBuilder sb3 = new StringBuilder("unknown");
                int i3 = this.mUnknownCount + 1;
                this.mUnknownCount = i3;
                sb3.append(i3);
                sb = sb3.toString();
            }
            this.mIdMap.put(Integer.valueOf(i), sb);
            return "" + sb + "";
        }

        private void writeConstraint(String str, int i, String str2, int i2, int i3) throws IOException {
            if (i == -1) {
                return;
            }
            this.mWriter.write("    ".concat(str));
            this.mWriter.write(":[");
            this.mWriter.write(getName(i));
            this.mWriter.write(", ");
            this.mWriter.write("'" + str2 + "'");
            if (i2 != 0 || i3 != Integer.MIN_VALUE) {
                this.mWriter.write(", " + i2);
                if (i3 != Integer.MIN_VALUE) {
                    this.mWriter.write(", " + i3);
                }
            }
            this.mWriter.write("],\n");
        }

        private void writeDimension(String str, int i, int i2, float f, int i3, int i4) throws IOException {
            String m4m;
            if (i != 0) {
                if (i == -2) {
                    this.mWriter.write("    " + str + ": 'wrap',\n");
                    return;
                }
                if (i == -1) {
                    this.mWriter.write("    " + str + ": 'parent',\n");
                    return;
                }
                this.mWriter.write("    " + str + ": " + i + ",\n");
                return;
            }
            if (i4 == -1 && i3 == -1) {
                if (i2 == 1) {
                    this.mWriter.write("    " + str + ": '???????????',\n");
                    return;
                }
                if (i2 != 2) {
                    return;
                }
                this.mWriter.write("    " + str + ": '" + f + "%',\n");
                return;
            }
            if (i2 == 0) {
                m4m = LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("    ", str, ": {value:'spread'");
            } else if (i2 == 1) {
                m4m = LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("    ", str, ": {value:'wrap'");
            } else if (i2 != 2) {
                m4m = "-----";
            } else {
                m4m = "    " + str + ": {value: '" + f + "%'";
            }
            if (i4 != -1) {
                m4m = m4m + ", max: " + i4;
            }
            if (i4 != -1) {
                m4m = m4m + ", min: " + i3;
            }
            this.mWriter.write(AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(m4m, "},\n"));
        }

        private void writeLayout() throws IOException {
            this.mWriter.write("{\n");
            for (int i : this.mSet.getKnownIds()) {
                Integer valueOf = Integer.valueOf(i);
                ConstraintSet.Constraint constraint = this.mSet.getConstraint(valueOf.intValue());
                String simpleName = getSimpleName(valueOf.intValue());
                if (!"LogJson".equals(simpleName)) {
                    this.mWriter.write("  " + simpleName + ":{\n");
                    ConstraintSet.Layout layout = constraint.layout;
                    if (layout.mReferenceIds != null) {
                        StringBuilder sb = new StringBuilder(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("type: '_", simpleName, "_' , contains: ["));
                        int i2 = 0;
                        while (true) {
                            int[] iArr = layout.mReferenceIds;
                            if (i2 < iArr.length) {
                                int i3 = iArr[i2];
                                sb.append(i2 == 0 ? "" : ", ");
                                sb.append(getName(i3));
                                i2++;
                            } else {
                                this.mWriter.write(((Object) sb) + "]\n");
                            }
                        }
                    }
                    if (layout.mReferenceIdString != null) {
                        StringBuilder sb2 = new StringBuilder("  type: '???' , contains: [");
                        String[] split = layout.mReferenceIdString.split(",");
                        int i4 = 0;
                        while (i4 < split.length) {
                            String str = split[i4];
                            sb2.append(i4 == 0 ? "" : ", ");
                            sb2.append("`");
                            sb2.append(str);
                            sb2.append("`");
                            i4++;
                        }
                        this.mWriter.write(((Object) sb2) + "]\n");
                    }
                    writeDimension("height", layout.mHeight, layout.heightDefault, layout.heightPercent, layout.heightMin, layout.heightMax);
                    writeDimension("width", layout.mWidth, layout.widthDefault, layout.widthPercent, layout.widthMin, layout.widthMax);
                    writeConstraint("left", layout.leftToLeft, "left", layout.leftMargin, layout.goneLeftMargin);
                    writeConstraint("left", layout.leftToRight, "right", layout.leftMargin, layout.goneLeftMargin);
                    writeConstraint("right", layout.rightToLeft, "left", layout.rightMargin, layout.goneRightMargin);
                    writeConstraint("right", layout.rightToRight, "right", layout.rightMargin, layout.goneRightMargin);
                    writeConstraint("baseline", layout.baselineToBaseline, "baseline", -1, layout.goneBaselineMargin);
                    writeConstraint("baseline", layout.baselineToTop, "top", -1, layout.goneBaselineMargin);
                    writeConstraint("baseline", layout.baselineToBottom, "bottom", -1, layout.goneBaselineMargin);
                    writeConstraint("top", layout.topToBottom, "bottom", layout.topMargin, layout.goneTopMargin);
                    writeConstraint("top", layout.topToTop, "top", layout.topMargin, layout.goneTopMargin);
                    writeConstraint("bottom", layout.bottomToBottom, "bottom", layout.bottomMargin, layout.goneBottomMargin);
                    writeConstraint("bottom", layout.bottomToTop, "top", layout.bottomMargin, layout.goneBottomMargin);
                    writeConstraint("start", layout.startToStart, "start", layout.startMargin, layout.goneStartMargin);
                    writeConstraint("start", layout.startToEnd, "end", layout.startMargin, layout.goneStartMargin);
                    writeConstraint("end", layout.endToStart, "start", layout.endMargin, layout.goneEndMargin);
                    writeConstraint("end", layout.endToEnd, "end", layout.endMargin, layout.goneEndMargin);
                    writeVariable("horizontalBias", layout.horizontalBias, 0.5f);
                    writeVariable("verticalBias", layout.verticalBias, 0.5f);
                    int i5 = layout.circleConstraint;
                    float f = layout.circleAngle;
                    int i6 = layout.circleRadius;
                    if (i5 != -1) {
                        this.mWriter.write("    circle");
                        this.mWriter.write(":[");
                        this.mWriter.write(getName(i5));
                        this.mWriter.write(", " + f);
                        this.mWriter.write(i6 + "],\n");
                    }
                    int i7 = layout.orientation;
                    int i8 = layout.guideBegin;
                    int i9 = layout.guideEnd;
                    float f2 = layout.guidePercent;
                    writeVariable(i7, "orientation");
                    writeVariable(i8, "guideBegin");
                    writeVariable(i9, "guideEnd");
                    writeVariable("guidePercent", f2);
                    writeVariable("dimensionRatio", layout.dimensionRatio);
                    writeVariable(layout.mBarrierMargin, "barrierMargin");
                    writeVariable(layout.mHelperType, "type");
                    writeVariable("ReferenceId", layout.mReferenceIdString);
                    boolean z = layout.mBarrierAllowsGoneWidgets;
                    if (!z) {
                        this.mWriter.write("    mBarrierAllowsGoneWidgets");
                        this.mWriter.write(": " + z);
                        this.mWriter.write(",\n");
                    }
                    writeVariable(layout.mWrapBehavior, "WrapBehavior");
                    writeVariable("verticalWeight", layout.verticalWeight);
                    writeVariable("horizontalWeight", layout.horizontalWeight);
                    writeVariable(layout.horizontalChainStyle, "horizontalChainStyle");
                    writeVariable(layout.verticalChainStyle, "verticalChainStyle");
                    writeVariable(layout.mBarrierDirection, "barrierDirection");
                    int[] iArr2 = layout.mReferenceIds;
                    if (iArr2 != null) {
                        this.mWriter.write("    ReferenceIds");
                        this.mWriter.write(": ");
                        int i10 = 0;
                        while (i10 < iArr2.length) {
                            Writer writer = this.mWriter;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(i10 == 0 ? "[" : ", ");
                            sb3.append(getName(iArr2[i10]));
                            writer.write(sb3.toString());
                            i10++;
                        }
                        this.mWriter.write("],\n");
                    }
                    ConstraintSet.Transform transform = constraint.transform;
                    if (transform.applyElevation) {
                        writeVariable("elevation", transform.elevation);
                    }
                    writeVariable("rotationX", transform.rotationX, 0.0f);
                    writeVariable("rotationY", transform.rotationY, 0.0f);
                    writeVariable("rotationZ", transform.rotation, 0.0f);
                    writeVariable("scaleX", transform.scaleX, 1.0f);
                    writeVariable("scaleY", transform.scaleY, 1.0f);
                    writeVariable("translationX", transform.translationX, 0.0f);
                    writeVariable("translationY", transform.translationY, 0.0f);
                    writeVariable("translationZ", transform.translationZ, 0.0f);
                    HashMap<String, ConstraintAttribute> hashMap = constraint.mCustomConstraints;
                    if (hashMap != null && hashMap.size() > 0) {
                        this.mWriter.write("    custom: {\n");
                        Iterator<String> it = hashMap.keySet().iterator();
                        while (it.hasNext()) {
                            ConstraintAttribute constraintAttribute = hashMap.get(it.next());
                            if (constraintAttribute != null) {
                                String str2 = "      " + constraintAttribute.getName() + ": ";
                                switch (constraintAttribute.getType().ordinal()) {
                                    case 0:
                                        StringBuilder m2m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str2);
                                        m2m.append(constraintAttribute.getIntegerValue());
                                        str2 = m2m.toString();
                                        break;
                                    case 1:
                                        StringBuilder m2m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str2);
                                        m2m2.append(constraintAttribute.getFloatValue());
                                        str2 = m2m2.toString();
                                        break;
                                    case 2:
                                        StringBuilder m2m3 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str2);
                                        m2m3.append("#" + ("00000000" + Integer.toHexString(constraintAttribute.getColorValue())).substring(r4.length() - 8));
                                        str2 = m2m3.toString();
                                        break;
                                    case 3:
                                    case 5:
                                    case 7:
                                        str2 = null;
                                        break;
                                    case 4:
                                        str2 = str2 + "'" + constraintAttribute.getStringValue() + "'";
                                        break;
                                    case 6:
                                        StringBuilder m2m4 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str2);
                                        m2m4.append(constraintAttribute.getFloatValue());
                                        str2 = m2m4.toString();
                                        break;
                                }
                                if (str2 != null) {
                                    this.mWriter.write(str2.concat(",\n"));
                                }
                            }
                        }
                        this.mWriter.write("     } \n");
                    }
                    this.mWriter.write("  },\n");
                }
            }
            this.mWriter.write("},\n");
        }

        private void writeVariable(int i, String str) throws IOException {
            if (i == 0 || i == -1) {
                return;
            }
            this.mWriter.write("    ".concat(str));
            this.mWriter.write(": " + i);
            this.mWriter.write(",\n");
        }

        final String constraintLayoutToJson(ConstraintLayout constraintLayout) {
            String str;
            StringWriter stringWriter = new StringWriter();
            int childCount = constraintLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = constraintLayout.getChildAt(i);
                String simpleName = childAt.getClass().getSimpleName();
                int id = childAt.getId();
                if (id == -1) {
                    int generateViewId = View.generateViewId();
                    childAt.setId(generateViewId);
                    if (!"LogJson".equals(simpleName)) {
                        simpleName = "noid_".concat(simpleName);
                    }
                    this.mNames.put(Integer.valueOf(generateViewId), simpleName);
                } else if ("LogJson".equals(simpleName)) {
                    this.mNames.put(Integer.valueOf(id), simpleName);
                }
            }
            stringWriter.append((CharSequence) "{\n");
            stringWriter.append((CharSequence) "Widgets:{\n");
            int childCount2 = constraintLayout.getChildCount();
            int i2 = -1;
            while (i2 < childCount2) {
                View childAt2 = i2 == -1 ? constraintLayout : constraintLayout.getChildAt(i2);
                int id2 = childAt2.getId();
                if (!"LogJson".equals(childAt2.getClass().getSimpleName())) {
                    String name = this.mNames.containsKey(Integer.valueOf(id2)) ? this.mNames.get(Integer.valueOf(id2)) : i2 == -1 ? "parent" : Debug.getName(childAt2);
                    String simpleName2 = childAt2.getClass().getSimpleName();
                    String str2 = ", bounds: [" + childAt2.getLeft() + ", " + childAt2.getTop() + ", " + childAt2.getRight() + ", " + childAt2.getBottom() + "]},\n";
                    stringWriter.append((CharSequence) ("  " + name + ": { "));
                    if (i2 == -1) {
                        stringWriter.append((CharSequence) ("type: '" + childAt2.getClass().getSimpleName() + "' , "));
                        try {
                            ViewGroup.LayoutParams layoutParams = childAt2.getLayoutParams();
                            String str3 = "'WRAP_CONTENT'";
                            int i3 = layoutParams.width;
                            if (i3 == -1) {
                                str = "'MATCH_PARENT'";
                            } else if (i3 == -2) {
                                str = "'WRAP_CONTENT'";
                            } else {
                                str = layoutParams.width + "";
                            }
                            stringWriter.append((CharSequence) ("width: " + str + ", "));
                            int i4 = layoutParams.height;
                            if (i4 == -1) {
                                str3 = "'MATCH_PARENT'";
                            } else if (i4 != -2) {
                                str3 = layoutParams.height + "";
                            }
                            stringWriter.append((CharSequence) "height: ").append((CharSequence) str3);
                        } catch (Exception unused) {
                        }
                    } else if (simpleName2.contains("Text")) {
                        if (childAt2 instanceof TextView) {
                            stringWriter.append((CharSequence) ("type: 'Text', label: '" + ((TextView) childAt2).getText().toString().replaceAll("'", "\\'") + "'"));
                        } else {
                            stringWriter.append((CharSequence) "type: 'Text' },\n");
                        }
                    } else if (simpleName2.contains("Button")) {
                        if (childAt2 instanceof Button) {
                            stringWriter.append((CharSequence) ("type: 'Button', label: '" + ((Object) ((Button) childAt2).getText()) + "'"));
                        } else {
                            stringWriter.append((CharSequence) "type: 'Button'");
                        }
                    } else if (simpleName2.contains("Image")) {
                        stringWriter.append((CharSequence) "type: 'Image'");
                    } else if (simpleName2.contains("View")) {
                        stringWriter.append((CharSequence) "type: 'Box'");
                    } else {
                        stringWriter.append((CharSequence) ("type: '" + childAt2.getClass().getSimpleName() + "'"));
                    }
                    stringWriter.append((CharSequence) str2);
                }
                i2++;
            }
            stringWriter.append((CharSequence) "},\n");
            stringWriter.append((CharSequence) "  ConstraintSet:{\n");
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            try {
                stringWriter.append((CharSequence) ((constraintLayout.getId() == -1 ? "cset" : Debug.getName(constraintLayout)) + ":"));
                this.mWriter = stringWriter;
                this.mContext = constraintLayout.getContext();
                this.mSet = constraintSet;
                constraintSet.getConstraint(2);
                writeLayout();
                stringWriter.append((CharSequence) "\n");
                stringWriter.append((CharSequence) "  }\n");
                stringWriter.append((CharSequence) "}\n");
                return stringWriter.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeVariable(String str, float f) throws IOException {
            if (f == -1.0f) {
                return;
            }
            this.mWriter.write("    ".concat(str));
            this.mWriter.write(": " + f);
            this.mWriter.write(",\n");
        }

        private void writeVariable(String str, float f, float f2) throws IOException {
            if (f == f2) {
                return;
            }
            this.mWriter.write("    ".concat(str));
            this.mWriter.write(": " + f);
            this.mWriter.write(",\n");
        }

        private void writeVariable(String str, String str2) throws IOException {
            if (str2 == null) {
                return;
            }
            this.mWriter.write("    ".concat(str));
            this.mWriter.write(": '".concat(str2));
            this.mWriter.write("',\n");
        }
    }

    public LogJson(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDelay = 1000;
        this.mMode = 2;
        this.mLogToFile = null;
        this.mLogConsole = true;
        this.mPeriodic = false;
        initLogJson(attributeSet);
    }

    public LogJson(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDelay = 1000;
        this.mMode = 2;
        this.mLogToFile = null;
        this.mLogConsole = true;
        this.mPeriodic = false;
        initLogJson(attributeSet);
    }
}
