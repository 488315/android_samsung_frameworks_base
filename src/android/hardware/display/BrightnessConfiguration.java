package android.hardware.display;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes2.dex */
public final class BrightnessConfiguration implements Parcelable {
    private static final String ATTR_CATEGORY = "category";
    private static final String ATTR_COLLECT_COLOR = "collect-color";
    private static final String ATTR_DESCRIPTION = "description";
    private static final String ATTR_LUX = "lux";
    private static final String ATTR_MODEL_LOWER_BOUND = "model-lower-bound";
    private static final String ATTR_MODEL_TIMEOUT = "model-timeout";
    private static final String ATTR_MODEL_UPPER_BOUND = "model-upper-bound";
    private static final String ATTR_NITS = "nits";
    private static final String ATTR_PACKAGE_NAME = "package-name";
    public static final Parcelable.Creator<BrightnessConfiguration> CREATOR = new Parcelable.Creator<BrightnessConfiguration>() { // from class: android.hardware.display.BrightnessConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BrightnessConfiguration createFromParcel(Parcel parcel) {
            Builder builder = new Builder(parcel.createFloatArray(), parcel.createFloatArray());
            int i = parcel.readInt();
            for (int i2 = 0; i2 < i; i2++) {
                builder.addCorrectionByPackageName(parcel.readString(), BrightnessCorrection.CREATOR.createFromParcel(parcel));
            }
            int i3 = parcel.readInt();
            for (int i4 = 0; i4 < i3; i4++) {
                builder.addCorrectionByCategory(parcel.readInt(), BrightnessCorrection.CREATOR.createFromParcel(parcel));
            }
            builder.setDescription(parcel.readString());
            builder.setShouldCollectColorSamples(parcel.readBoolean());
            builder.setShortTermModelTimeoutMillis(parcel.readLong());
            builder.setShortTermModelLowerLuxMultiplier(parcel.readFloat());
            builder.setShortTermModelUpperLuxMultiplier(parcel.readFloat());
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BrightnessConfiguration[] newArray(int i) {
            return new BrightnessConfiguration[i];
        }
    };
    public static final long SHORT_TERM_TIMEOUT_UNSET = -1;
    private static final String TAG_BRIGHTNESS_CORRECTION = "brightness-correction";
    private static final String TAG_BRIGHTNESS_CORRECTIONS = "brightness-corrections";
    private static final String TAG_BRIGHTNESS_CURVE = "brightness-curve";
    private static final String TAG_BRIGHTNESS_PARAMS = "brightness-params";
    private static final String TAG_BRIGHTNESS_POINT = "brightness-point";
    private final Map<Integer, BrightnessCorrection> mCorrectionsByCategory;
    private final Map<String, BrightnessCorrection> mCorrectionsByPackageName;
    private final String mDescription;
    private final float[] mLux;
    private final float[] mNits;
    private final float mShortTermModelLowerLuxMultiplier;
    private final long mShortTermModelTimeout;
    private final float mShortTermModelUpperLuxMultiplier;
    private final boolean mShouldCollectColorSamples;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BrightnessConfiguration(float[] fArr, float[] fArr2, Map<String, BrightnessCorrection> map, Map<Integer, BrightnessCorrection> map2, String str, boolean z, long j, float f, float f2) {
        this.mLux = fArr;
        this.mNits = fArr2;
        this.mCorrectionsByPackageName = map;
        this.mCorrectionsByCategory = map2;
        this.mDescription = str;
        this.mShouldCollectColorSamples = z;
        this.mShortTermModelTimeout = j;
        this.mShortTermModelLowerLuxMultiplier = f;
        this.mShortTermModelUpperLuxMultiplier = f2;
    }

    public Pair<float[], float[]> getCurve() {
        float[] fArr = this.mLux;
        float[] fArrCopyOf = Arrays.copyOf(fArr, fArr.length);
        float[] fArr2 = this.mNits;
        return Pair.create(fArrCopyOf, Arrays.copyOf(fArr2, fArr2.length));
    }

    public BrightnessCorrection getCorrectionByPackageName(String str) {
        return this.mCorrectionsByPackageName.get(str);
    }

    public BrightnessCorrection getCorrectionByCategory(int i) {
        return this.mCorrectionsByCategory.get(Integer.valueOf(i));
    }

    public String getDescription() {
        return this.mDescription;
    }

    public boolean shouldCollectColorSamples() {
        return this.mShouldCollectColorSamples;
    }

    public long getShortTermModelTimeoutMillis() {
        return this.mShortTermModelTimeout;
    }

    public float getShortTermModelUpperLuxMultiplier() {
        return this.mShortTermModelUpperLuxMultiplier;
    }

    public float getShortTermModelLowerLuxMultiplier() {
        return this.mShortTermModelLowerLuxMultiplier;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloatArray(this.mLux);
        parcel.writeFloatArray(this.mNits);
        parcel.writeInt(this.mCorrectionsByPackageName.size());
        for (Map.Entry<String, BrightnessCorrection> entry : this.mCorrectionsByPackageName.entrySet()) {
            String key = entry.getKey();
            BrightnessCorrection value = entry.getValue();
            parcel.writeString(key);
            value.writeToParcel(parcel, i);
        }
        parcel.writeInt(this.mCorrectionsByCategory.size());
        for (Map.Entry<Integer, BrightnessCorrection> entry2 : this.mCorrectionsByCategory.entrySet()) {
            int iIntValue = entry2.getKey().intValue();
            BrightnessCorrection value2 = entry2.getValue();
            parcel.writeInt(iIntValue);
            value2.writeToParcel(parcel, i);
        }
        parcel.writeString(this.mDescription);
        parcel.writeBoolean(this.mShouldCollectColorSamples);
        parcel.writeLong(this.mShortTermModelTimeout);
        parcel.writeFloat(this.mShortTermModelLowerLuxMultiplier);
        parcel.writeFloat(this.mShortTermModelUpperLuxMultiplier);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("BrightnessConfiguration{[");
        int length = this.mLux.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(this.mLux[i]);
            sb.append(", ");
            sb.append(this.mNits[i]);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        sb.append("], {");
        for (Map.Entry<String, BrightnessCorrection> entry : this.mCorrectionsByPackageName.entrySet()) {
            sb.append("'" + entry.getKey() + "': " + entry.getValue() + ", ");
        }
        for (Map.Entry<Integer, BrightnessCorrection> entry2 : this.mCorrectionsByCategory.entrySet()) {
            sb.append(entry2.getKey() + ": " + entry2.getValue() + ", ");
        }
        sb.append("}, '");
        String str = this.mDescription;
        if (str != null) {
            sb.append(str);
        }
        sb.append(", shouldCollectColorSamples = " + this.mShouldCollectColorSamples);
        if (this.mShortTermModelTimeout >= 0) {
            sb.append(", shortTermModelTimeout = " + this.mShortTermModelTimeout);
        }
        if (!Float.isNaN(this.mShortTermModelLowerLuxMultiplier)) {
            sb.append(", shortTermModelLowerLuxMultiplier = " + this.mShortTermModelLowerLuxMultiplier);
        }
        if (!Float.isNaN(this.mShortTermModelLowerLuxMultiplier)) {
            sb.append(", shortTermModelUpperLuxMultiplier = " + this.mShortTermModelUpperLuxMultiplier);
        }
        sb.append("'}");
        return sb.toString();
    }

    public int hashCode() {
        int iHashCode = ((((((Arrays.hashCode(this.mLux) + 31) * 31) + Arrays.hashCode(this.mNits)) * 31) + this.mCorrectionsByPackageName.hashCode()) * 31) + this.mCorrectionsByCategory.hashCode();
        String str = this.mDescription;
        if (str != null) {
            iHashCode = (iHashCode * 31) + str.hashCode();
        }
        return (((((((iHashCode * 31) + Boolean.hashCode(this.mShouldCollectColorSamples)) * 31) + Long.hashCode(this.mShortTermModelTimeout)) * 31) + Float.hashCode(this.mShortTermModelLowerLuxMultiplier)) * 31) + Float.hashCode(this.mShortTermModelUpperLuxMultiplier);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BrightnessConfiguration)) {
            return false;
        }
        BrightnessConfiguration brightnessConfiguration = (BrightnessConfiguration) obj;
        return Arrays.equals(this.mLux, brightnessConfiguration.mLux) && Arrays.equals(this.mNits, brightnessConfiguration.mNits) && this.mCorrectionsByPackageName.equals(brightnessConfiguration.mCorrectionsByPackageName) && this.mCorrectionsByCategory.equals(brightnessConfiguration.mCorrectionsByCategory) && Objects.equals(this.mDescription, brightnessConfiguration.mDescription) && this.mShouldCollectColorSamples == brightnessConfiguration.mShouldCollectColorSamples && this.mShortTermModelTimeout == brightnessConfiguration.mShortTermModelTimeout && checkFloatEquals(this.mShortTermModelLowerLuxMultiplier, brightnessConfiguration.mShortTermModelLowerLuxMultiplier) && checkFloatEquals(this.mShortTermModelUpperLuxMultiplier, brightnessConfiguration.mShortTermModelUpperLuxMultiplier);
    }

    private boolean checkFloatEquals(float f, float f2) {
        return (Float.isNaN(f) && Float.isNaN(f2)) || f == f2;
    }

    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_CURVE);
        String str = this.mDescription;
        if (str != null) {
            typedXmlSerializer.attribute(null, "description", str);
        }
        for (int i = 0; i < this.mLux.length; i++) {
            typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_POINT);
            typedXmlSerializer.attributeFloat(null, ATTR_LUX, this.mLux[i]);
            typedXmlSerializer.attributeFloat(null, "nits", this.mNits[i]);
            typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_POINT);
        }
        typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_CURVE);
        typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_CORRECTIONS);
        for (Map.Entry<String, BrightnessCorrection> entry : this.mCorrectionsByPackageName.entrySet()) {
            String key = entry.getKey();
            BrightnessCorrection value = entry.getValue();
            typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_CORRECTION);
            typedXmlSerializer.attribute(null, ATTR_PACKAGE_NAME, key);
            value.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_CORRECTION);
        }
        for (Map.Entry<Integer, BrightnessCorrection> entry2 : this.mCorrectionsByCategory.entrySet()) {
            int iIntValue = entry2.getKey().intValue();
            BrightnessCorrection value2 = entry2.getValue();
            typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_CORRECTION);
            typedXmlSerializer.attributeInt(null, ATTR_CATEGORY, iIntValue);
            value2.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_CORRECTION);
        }
        typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_CORRECTIONS);
        typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_PARAMS);
        if (this.mShouldCollectColorSamples) {
            typedXmlSerializer.attributeBoolean(null, ATTR_COLLECT_COLOR, true);
        }
        long j = this.mShortTermModelTimeout;
        if (j >= 0) {
            typedXmlSerializer.attributeLong(null, ATTR_MODEL_TIMEOUT, j);
        }
        if (!Float.isNaN(this.mShortTermModelLowerLuxMultiplier)) {
            typedXmlSerializer.attributeFloat(null, ATTR_MODEL_LOWER_BOUND, this.mShortTermModelLowerLuxMultiplier);
        }
        if (!Float.isNaN(this.mShortTermModelUpperLuxMultiplier)) {
            typedXmlSerializer.attributeFloat(null, ATTR_MODEL_UPPER_BOUND, this.mShortTermModelUpperLuxMultiplier);
        }
        typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_PARAMS);
    }

    public static BrightnessConfiguration loadFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        int i;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        int depth = typedXmlPullParser.getDepth();
        String str = null;
        long jLongValue = -1;
        float fLoadFloatFromXml = Float.NaN;
        String attributeValue = null;
        float fLoadFloatFromXml2 = Float.NaN;
        boolean attributeBoolean = false;
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (TAG_BRIGHTNESS_CURVE.equals(typedXmlPullParser.getName())) {
                attributeValue = typedXmlPullParser.getAttributeValue(str, "description");
                int depth2 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                    if (TAG_BRIGHTNESS_POINT.equals(typedXmlPullParser.getName())) {
                        float fLoadFloatFromXml3 = loadFloatFromXml(typedXmlPullParser, ATTR_LUX);
                        float fLoadFloatFromXml4 = loadFloatFromXml(typedXmlPullParser, "nits");
                        arrayList.add(Float.valueOf(fLoadFloatFromXml3));
                        arrayList2.add(Float.valueOf(fLoadFloatFromXml4));
                    }
                }
            } else {
                if (TAG_BRIGHTNESS_CORRECTIONS.equals(typedXmlPullParser.getName())) {
                    int depth3 = typedXmlPullParser.getDepth();
                    while (XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                        if (TAG_BRIGHTNESS_CORRECTION.equals(typedXmlPullParser.getName())) {
                            String attributeValue2 = typedXmlPullParser.getAttributeValue(str, ATTR_PACKAGE_NAME);
                            int i2 = depth;
                            int attributeInt = typedXmlPullParser.getAttributeInt(str, ATTR_CATEGORY, -1);
                            BrightnessCorrection brightnessCorrectionLoadFromXml = BrightnessCorrection.loadFromXml(typedXmlPullParser);
                            if (attributeValue2 != null) {
                                map.put(attributeValue2, brightnessCorrectionLoadFromXml);
                            } else if (attributeInt != -1) {
                                map2.put(Integer.valueOf(attributeInt), brightnessCorrectionLoadFromXml);
                            }
                            depth = i2;
                            str = null;
                        }
                    }
                    i = depth;
                } else {
                    i = depth;
                    if (TAG_BRIGHTNESS_PARAMS.equals(typedXmlPullParser.getName())) {
                        str = null;
                        attributeBoolean = typedXmlPullParser.getAttributeBoolean(null, ATTR_COLLECT_COLOR, false);
                        Long lLoadLongFromXml = loadLongFromXml(typedXmlPullParser, ATTR_MODEL_TIMEOUT);
                        if (lLoadLongFromXml != null) {
                            jLongValue = lLoadLongFromXml.longValue();
                        }
                        fLoadFloatFromXml = loadFloatFromXml(typedXmlPullParser, ATTR_MODEL_LOWER_BOUND);
                        fLoadFloatFromXml2 = loadFloatFromXml(typedXmlPullParser, ATTR_MODEL_UPPER_BOUND);
                        depth = i;
                    } else {
                        str = null;
                    }
                }
                depth = i;
            }
        }
        int size = arrayList.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        for (int i3 = 0; i3 < size; i3++) {
            fArr[i3] = ((Float) arrayList.get(i3)).floatValue();
            fArr2[i3] = ((Float) arrayList2.get(i3)).floatValue();
        }
        Builder builder = new Builder(fArr, fArr2);
        builder.setDescription(attributeValue);
        for (Map.Entry entry : map.entrySet()) {
            builder.addCorrectionByPackageName((String) entry.getKey(), (BrightnessCorrection) entry.getValue());
        }
        for (Map.Entry entry2 : map2.entrySet()) {
            builder.addCorrectionByCategory(((Integer) entry2.getKey()).intValue(), (BrightnessCorrection) entry2.getValue());
        }
        builder.setShouldCollectColorSamples(attributeBoolean);
        builder.setShortTermModelTimeoutMillis(jLongValue);
        builder.setShortTermModelLowerLuxMultiplier(fLoadFloatFromXml);
        builder.setShortTermModelUpperLuxMultiplier(fLoadFloatFromXml2);
        return builder.build();
    }

    private static float loadFloatFromXml(TypedXmlPullParser typedXmlPullParser, String str) {
        return typedXmlPullParser.getAttributeFloat(null, str, Float.NaN);
    }

    private static Long loadLongFromXml(TypedXmlPullParser typedXmlPullParser, String str) {
        try {
            return Long.valueOf(typedXmlPullParser.getAttributeLong(null, str));
        } catch (Exception unused) {
            return null;
        }
    }

    public static class Builder {
        private static final int MAX_CORRECTIONS_BY_CATEGORY = 20;
        private static final int MAX_CORRECTIONS_BY_PACKAGE_NAME = 20;
        private Map<Integer, BrightnessCorrection> mCorrectionsByCategory;
        private Map<String, BrightnessCorrection> mCorrectionsByPackageName;
        private float[] mCurveLux;
        private float[] mCurveNits;
        private String mDescription;
        private boolean mShouldCollectColorSamples;
        private long mShortTermModelTimeout = -1;
        private float mShortTermModelLowerLuxMultiplier = Float.NaN;
        private float mShortTermModelUpperLuxMultiplier = Float.NaN;

        public int getMaxCorrectionsByCategory() {
            return 20;
        }

        public int getMaxCorrectionsByPackageName() {
            return 20;
        }

        public Builder(float[] fArr, float[] fArr2) {
            Objects.requireNonNull(fArr);
            Objects.requireNonNull(fArr2);
            if (fArr.length == 0 || fArr2.length == 0) {
                throw new IllegalArgumentException("Lux and nits arrays must not be empty");
            }
            if (fArr.length != fArr2.length) {
                throw new IllegalArgumentException("Lux and nits arrays must be the same length");
            }
            if (fArr[0] != 0.0f) {
                throw new IllegalArgumentException("Initial control point must be for 0 lux");
            }
            Preconditions.checkArrayElementsInRange(fArr, 0.0f, Float.MAX_VALUE, BrightnessConfiguration.ATTR_LUX);
            Preconditions.checkArrayElementsInRange(fArr2, 0.0f, Float.MAX_VALUE, "nits");
            checkMonotonic(fArr, true, BrightnessConfiguration.ATTR_LUX);
            checkMonotonic(fArr2, false, "nits");
            this.mCurveLux = fArr;
            this.mCurveNits = fArr2;
            this.mCorrectionsByPackageName = new HashMap();
            this.mCorrectionsByCategory = new HashMap();
        }

        public Builder addCorrectionByPackageName(String str, BrightnessCorrection brightnessCorrection) {
            Objects.requireNonNull(str, "packageName must not be null");
            Objects.requireNonNull(brightnessCorrection, "correction must not be null");
            if (this.mCorrectionsByPackageName.size() >= getMaxCorrectionsByPackageName()) {
                throw new IllegalArgumentException("Too many corrections by package name");
            }
            this.mCorrectionsByPackageName.put(str, brightnessCorrection);
            return this;
        }

        public Builder addCorrectionByCategory(int i, BrightnessCorrection brightnessCorrection) {
            Objects.requireNonNull(brightnessCorrection, "correction must not be null");
            if (this.mCorrectionsByCategory.size() >= getMaxCorrectionsByCategory()) {
                throw new IllegalArgumentException("Too many corrections by category");
            }
            this.mCorrectionsByCategory.put(Integer.valueOf(i), brightnessCorrection);
            return this;
        }

        public Builder setDescription(String str) {
            this.mDescription = str;
            return this;
        }

        public Builder setShouldCollectColorSamples(boolean z) {
            this.mShouldCollectColorSamples = z;
            return this;
        }

        public Builder setShortTermModelTimeoutMillis(long j) {
            this.mShortTermModelTimeout = j;
            return this;
        }

        public Builder setShortTermModelUpperLuxMultiplier(float f) {
            if (f < 0.0f) {
                throw new IllegalArgumentException("Negative lux multiplier");
            }
            this.mShortTermModelUpperLuxMultiplier = f;
            return this;
        }

        public Builder setShortTermModelLowerLuxMultiplier(float f) {
            if (f < 0.0f) {
                throw new IllegalArgumentException("Negative lux multiplier");
            }
            this.mShortTermModelLowerLuxMultiplier = f;
            return this;
        }

        public BrightnessConfiguration build() {
            if (this.mCurveLux == null || this.mCurveNits == null) {
                throw new IllegalStateException("A curve must be set!");
            }
            return new BrightnessConfiguration(this.mCurveLux, this.mCurveNits, this.mCorrectionsByPackageName, this.mCorrectionsByCategory, this.mDescription, this.mShouldCollectColorSamples, this.mShortTermModelTimeout, this.mShortTermModelLowerLuxMultiplier, this.mShortTermModelUpperLuxMultiplier);
        }

        private static void checkMonotonic(float[] fArr, boolean z, String str) {
            int i = 1;
            if (fArr.length <= 1) {
                return;
            }
            float f = fArr[0];
            while (i < fArr.length) {
                float f2 = fArr[i];
                if (f > f2 || (f == f2 && z)) {
                    throw new IllegalArgumentException(str + " values must be " + (z ? "strictly increasing" : "monotonic"));
                }
                i++;
                f = f2;
            }
        }
    }
}
