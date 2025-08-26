package android.content.res;

import android.content.res.ColorStateListProto;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import android.util.StateSet;
import android.util.Xml;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.ims.ImsConfig;
import com.android.internal.R;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.cam.Cam;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ColorStateList extends ComplexColor implements Parcelable {
    private static final int DEFAULT_COLOR = -65536;
    private static final String TAG = "ColorStateList";
    private int mChangingConfigurations;
    private int[] mColors;
    private int mDefaultColor;
    private ColorStateListFactory mFactory;
    private boolean mIsOpaque;
    private int[][] mStateSpecs;
    private int[][] mThemeAttrs;
    private static final int[][] EMPTY = {new int[0]};
    private static final SparseArray<WeakReference<ColorStateList>> sCache = new SparseArray<>();
    public static final Parcelable.Creator<ColorStateList> CREATOR = new Parcelable.Creator<ColorStateList>() { // from class: android.content.res.ColorStateList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ColorStateList[] newArray(int i) {
            return new ColorStateList[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ColorStateList createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            int[][] iArr = new int[i][];
            for (int i2 = 0; i2 < i; i2++) {
                iArr[i2] = parcel.createIntArray();
            }
            return new ColorStateList(iArr, parcel.createIntArray());
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ColorStateList() {
    }

    public ColorStateList(int[][] iArr, int[] iArr2) {
        this.mStateSpecs = iArr;
        this.mColors = iArr2;
        onColorsChanged();
    }

    public static ColorStateList valueOf(int i) {
        SparseArray<WeakReference<ColorStateList>> sparseArray = sCache;
        synchronized (sparseArray) {
            int iIndexOfKey = sparseArray.indexOfKey(i);
            if (iIndexOfKey >= 0) {
                ColorStateList colorStateList = sparseArray.valueAt(iIndexOfKey).get();
                if (colorStateList != null) {
                    return colorStateList;
                }
                sparseArray.removeAt(iIndexOfKey);
            }
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                SparseArray<WeakReference<ColorStateList>> sparseArray2 = sCache;
                if (sparseArray2.valueAt(size).refersTo(null)) {
                    sparseArray2.removeAt(size);
                }
            }
            ColorStateList colorStateList2 = new ColorStateList(EMPTY, new int[]{i});
            sCache.put(i, new WeakReference<>(colorStateList2));
            return colorStateList2;
        }
    }

    private ColorStateList(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.mChangingConfigurations = colorStateList.mChangingConfigurations;
            this.mStateSpecs = colorStateList.mStateSpecs;
            this.mDefaultColor = colorStateList.mDefaultColor;
            this.mIsOpaque = colorStateList.mIsOpaque;
            this.mThemeAttrs = (int[][]) colorStateList.mThemeAttrs.clone();
            this.mColors = (int[]) colorStateList.mColors.clone();
        }
    }

    @Deprecated
    public static ColorStateList createFromXml(Resources resources, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return createFromXml(resources, xmlPullParser, null);
    }

    public static ColorStateList createFromXml(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        return createFromXmlInner(resources, xmlPullParser, attributeSetAsAttributeSet, theme);
    }

    static ColorStateList createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = xmlPullParser.getName();
        if (!name.equals("selector")) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
        }
        ColorStateList colorStateList = new ColorStateList();
        colorStateList.inflate(resources, xmlPullParser, attributeSet, theme);
        return colorStateList;
    }

    public ColorStateList withAlpha(int i) {
        int length = this.mColors.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = (this.mColors[i2] & 16777215) | (i << 24);
        }
        return new ColorStateList(this.mStateSpecs, iArr);
    }

    public ColorStateList withLStar(float f) {
        int length = this.mColors.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = modulateColor(this.mColors[i], 1.0f, f);
        }
        return new ColorStateList(this.mStateSpecs, iArr);
    }

    private void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int i;
        int depth;
        int i2 = 1;
        int depth2 = xmlPullParser.getDepth() + 1;
        int[][] iArr = (int[][]) ArrayUtils.newUnpaddedArray(int[].class, 20);
        int[][] iArr2 = new int[iArr.length][];
        int[] iArrAppend = new int[iArr.length];
        int i3 = 0;
        int i4 = -65536;
        int i5 = 0;
        boolean z = false;
        int i6 = 0;
        while (true) {
            int next = xmlPullParser.next();
            if (next == i2 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals(ImsConfig.EXTRA_CHANGED_ITEM)) {
                TypedArray typedArrayObtainAttributes = Resources.obtainAttributes(resources, theme, attributeSet, R.styleable.ColorStateListItem);
                int[] iArrExtractThemeAttrs = typedArrayObtainAttributes.extractThemeAttrs();
                int color = typedArrayObtainAttributes.getColor(i3, Color.MAGENTA);
                int i7 = depth2;
                float f = typedArrayObtainAttributes.getFloat(1, 1.0f);
                boolean z2 = z;
                float f2 = typedArrayObtainAttributes.getFloat(2, -1.0f);
                int changingConfigurations = i5 | typedArrayObtainAttributes.getChangingConfigurations();
                typedArrayObtainAttributes.recycle();
                int attributeCount = attributeSet.getAttributeCount();
                int[] iArr3 = new int[attributeCount];
                int i8 = 0;
                int i9 = 0;
                while (i8 < attributeCount) {
                    int i10 = attributeCount;
                    int attributeNameResource = attributeSet.getAttributeNameResource(i8);
                    if (attributeNameResource != 16844359 && attributeNameResource != 16843173 && attributeNameResource != 16843551) {
                        int i11 = i9 + 1;
                        if (!attributeSet.getAttributeBooleanValue(i8, false)) {
                            attributeNameResource = -attributeNameResource;
                        }
                        iArr3[i9] = attributeNameResource;
                        i9 = i11;
                    }
                    i8++;
                    attributeCount = i10;
                }
                int[] iArrTrimStateSet = StateSet.trimStateSet(iArr3, i9);
                int iModulateColor = modulateColor(color, f, f2);
                if (i6 == 0 || iArrTrimStateSet.length == 0) {
                    i4 = iModulateColor;
                }
                z = iArrExtractThemeAttrs != null ? true : z2;
                iArrAppend = GrowingArrayUtils.append(iArrAppend, i6, iModulateColor);
                iArr2 = (int[][]) GrowingArrayUtils.append(iArr2, i6, iArrExtractThemeAttrs);
                iArr = (int[][]) GrowingArrayUtils.append(iArr, i6, iArrTrimStateSet);
                i6++;
                i5 = changingConfigurations;
                depth2 = i7;
            } else {
                depth2 = depth2;
                z = z;
            }
            i2 = 1;
            i3 = 0;
        }
        boolean z3 = z;
        this.mChangingConfigurations = i5;
        this.mDefaultColor = i4;
        if (z3) {
            int[][] iArr4 = new int[i6][];
            this.mThemeAttrs = iArr4;
            i = 0;
            System.arraycopy(iArr2, 0, iArr4, 0, i6);
        } else {
            i = 0;
            this.mThemeAttrs = null;
        }
        int[] iArr5 = new int[i6];
        this.mColors = iArr5;
        this.mStateSpecs = new int[i6][];
        System.arraycopy(iArrAppend, i, iArr5, i, i6);
        System.arraycopy(iArr, i, this.mStateSpecs, i, i6);
        onColorsChanged();
    }

    @Override // android.content.res.ComplexColor
    public boolean canApplyTheme() {
        return this.mThemeAttrs != null;
    }

    private void applyTheme(Resources.Theme theme) {
        int[][] iArr = this.mThemeAttrs;
        if (iArr == null) {
            return;
        }
        int length = iArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            int[] iArr2 = iArr[i];
            if (iArr2 != null) {
                TypedArray typedArrayResolveAttributes = theme.resolveAttributes(iArr2, R.styleable.ColorStateListItem);
                float fAlpha = iArr[i][0] != 0 ? Color.alpha(this.mColors[i]) / 255.0f : 1.0f;
                int[] iArrExtractThemeAttrs = typedArrayResolveAttributes.extractThemeAttrs(iArr[i]);
                iArr[i] = iArrExtractThemeAttrs;
                if (iArrExtractThemeAttrs != null) {
                    z = true;
                }
                this.mColors[i] = modulateColor(typedArrayResolveAttributes.getColor(0, this.mColors[i]), typedArrayResolveAttributes.getFloat(1, fAlpha), typedArrayResolveAttributes.getFloat(2, -1.0f));
                this.mChangingConfigurations |= typedArrayResolveAttributes.getChangingConfigurations();
                typedArrayResolveAttributes.recycle();
            }
        }
        if (!z) {
            this.mThemeAttrs = null;
        }
        onColorsChanged();
    }

    @Override // android.content.res.ComplexColor
    public ColorStateList obtainForTheme(Resources.Theme theme) {
        if (theme == null || !canApplyTheme()) {
            return this;
        }
        ColorStateList colorStateList = new ColorStateList(this);
        colorStateList.applyTheme(theme);
        return colorStateList;
    }

    @Override // android.content.res.ComplexColor
    public int getChangingConfigurations() {
        return this.mChangingConfigurations | super.getChangingConfigurations();
    }

    private int modulateColor(int i, float f, float f2) {
        boolean z = f2 >= 0.0f && f2 <= 100.0f;
        if (f == 1.0f && !z) {
            return i;
        }
        int iConstrain = MathUtils.constrain((int) ((Color.alpha(i) * f) + 0.5f), 0, 255);
        if (z) {
            Cam camColorToCAM = ColorUtils.colorToCAM(i);
            i = ColorUtils.CAMToColor(camColorToCAM.getHue(), camColorToCAM.getChroma(), f2);
        }
        return (16777215 & i) | (iConstrain << 24);
    }

    @Override // android.content.res.ComplexColor
    public boolean isStateful() {
        int[][] iArr = this.mStateSpecs;
        return iArr.length >= 1 && iArr[0].length > 0;
    }

    public boolean hasFocusStateSpecified() {
        return StateSet.containsAttribute(this.mStateSpecs, 16842908);
    }

    public boolean isOpaque() {
        return this.mIsOpaque;
    }

    public int getColorForState(int[] iArr, int i) {
        int length = this.mStateSpecs.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (StateSet.stateSetMatches(this.mStateSpecs[i2], iArr)) {
                return this.mColors[i2];
            }
        }
        return i;
    }

    @Override // android.content.res.ComplexColor
    public int getDefaultColor() {
        return this.mDefaultColor;
    }

    public int[][] getStates() {
        return this.mStateSpecs;
    }

    public int[] getColors() {
        return this.mColors;
    }

    public boolean hasState(int i) {
        for (int[] iArr : this.mStateSpecs) {
            for (int i2 : iArr) {
                if (i2 == i || i2 == (~i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String toString() {
        return "ColorStateList{mThemeAttrs=" + Arrays.deepToString(this.mThemeAttrs) + "mChangingConfigurations=" + this.mChangingConfigurations + "mStateSpecs=" + Arrays.deepToString(this.mStateSpecs) + "mColors=" + Arrays.toString(this.mColors) + "mDefaultColor=" + this.mDefaultColor + '}';
    }

    private void onColorsChanged() {
        int i;
        int[][] iArr = this.mStateSpecs;
        int[] iArr2 = this.mColors;
        int length = iArr.length;
        boolean z = true;
        if (length > 0) {
            i = iArr2[0];
            int i2 = length - 1;
            while (true) {
                if (i2 <= 0) {
                    break;
                }
                if (iArr[i2].length == 0) {
                    i = iArr2[i2];
                    break;
                }
                i2--;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                if (Color.alpha(iArr2[i3]) != 255) {
                    z = false;
                    break;
                }
                i3++;
            }
        } else {
            i = -65536;
        }
        this.mDefaultColor = i;
        this.mIsOpaque = z;
    }

    @Override // android.content.res.ComplexColor
    public ConstantState<ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new ColorStateListFactory(this);
        }
        return this.mFactory;
    }

    private static class ColorStateListFactory extends ConstantState<ComplexColor> {
        private final ColorStateList mSrc;

        public ColorStateListFactory(ColorStateList colorStateList) {
            this.mSrc = colorStateList;
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mSrc.mChangingConfigurations;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance, reason: merged with bridge method [inline-methods] */
        public ComplexColor newInstance2() {
            return this.mSrc;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance, reason: merged with bridge method [inline-methods] */
        public ComplexColor newInstance2(Resources resources, Resources.Theme theme) {
            return this.mSrc.obtainForTheme(theme);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (canApplyTheme()) {
            Log.w(TAG, "Wrote partially-resolved ColorStateList to parcel!");
        }
        int length = this.mStateSpecs.length;
        parcel.writeInt(length);
        for (int i2 = 0; i2 < length; i2++) {
            parcel.writeIntArray(this.mStateSpecs[i2]);
        }
        parcel.writeIntArray(this.mColors);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream) {
        for (int[] iArr : this.mStateSpecs) {
            long jStart = protoOutputStream.start(2246267895809L);
            for (int i : iArr) {
                protoOutputStream.write(ColorStateListProto.StateSpec.STATE, i);
            }
            protoOutputStream.end(jStart);
        }
        for (int i2 : this.mColors) {
            protoOutputStream.write(ColorStateListProto.COLORS, i2);
        }
    }

    public static ColorStateList createFromProto(ProtoInputStream protoInputStream) throws Exception {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                long jStart = protoInputStream.start(2246267895809L);
                final ArrayList arrayList3 = new ArrayList();
                while (protoInputStream.nextField() != -1) {
                    if (protoInputStream.getFieldNumber() == 1) {
                        arrayList3.add(Integer.valueOf(protoInputStream.readInt(ColorStateListProto.StateSpec.STATE)));
                    } else {
                        Log.w(TAG, "Unhandled field while reading Icon proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                    }
                }
                int[] iArr = new int[arrayList3.size()];
                Arrays.setAll(iArr, new IntUnaryOperator() { // from class: android.content.res.ColorStateList$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntUnaryOperator
                    public final int applyAsInt(int i) {
                        return ((Integer) arrayList3.get(i)).intValue();
                    }
                });
                arrayList.add(iArr);
                protoInputStream.end(jStart);
            } else if (fieldNumber == 2) {
                arrayList2.add(Integer.valueOf(protoInputStream.readInt(ColorStateListProto.COLORS)));
            } else {
                Log.w(TAG, "Unhandled field while reading Icon proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        int[][] iArr2 = new int[arrayList.size()][];
        Arrays.setAll(iArr2, new IntFunction() { // from class: android.content.res.ColorStateList$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return (int[]) arrayList.get(i);
            }
        });
        int[] iArr3 = new int[arrayList2.size()];
        Arrays.setAll(iArr3, new IntUnaryOperator() { // from class: android.content.res.ColorStateList$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                return ((Integer) arrayList2.get(i)).intValue();
            }
        });
        return new ColorStateList(iArr2, iArr3);
    }
}
