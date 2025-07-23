package android.hardware.display;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MathUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes2.dex */
public final class BrightnessCorrection implements Parcelable {
    public static final Parcelable.Creator<BrightnessCorrection> CREATOR = new Parcelable.Creator<BrightnessCorrection>() { // from class: android.hardware.display.BrightnessCorrection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BrightnessCorrection createFromParcel(Parcel parcel) {
            if (parcel.readInt() != 1) {
                return null;
            }
            return ScaleAndTranslateLog.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BrightnessCorrection[] newArray(int i) {
            return new BrightnessCorrection[i];
        }
    };
    private static final int SCALE_AND_TRANSLATE_LOG = 1;
    private static final String TAG_SCALE_AND_TRANSLATE_LOG = "scale-and-translate-log";
    private BrightnessCorrectionImplementation mImplementation;

    private interface BrightnessCorrectionImplementation {
        float apply(float f);

        void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException;

        String toString();

        void writeToParcel(Parcel parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BrightnessCorrection(BrightnessCorrectionImplementation brightnessCorrectionImplementation) {
        this.mImplementation = brightnessCorrectionImplementation;
    }

    public static BrightnessCorrection createScaleAndTranslateLog(float f, float f2) {
        return new BrightnessCorrection(new ScaleAndTranslateLog(f, f2));
    }

    public float apply(float f) {
        return this.mImplementation.apply(f);
    }

    public String toString() {
        return this.mImplementation.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BrightnessCorrection) {
            return ((BrightnessCorrection) obj).mImplementation.equals(this.mImplementation);
        }
        return false;
    }

    public int hashCode() {
        return this.mImplementation.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mImplementation.writeToParcel(parcel);
    }

    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        this.mImplementation.saveToXml(typedXmlSerializer);
    }

    public static BrightnessCorrection loadFromXml(TypedXmlPullParser typedXmlPullParser) throws IOException, XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (TAG_SCALE_AND_TRANSLATE_LOG.equals(typedXmlPullParser.getName())) {
                return ScaleAndTranslateLog.loadFromXml(typedXmlPullParser);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float loadFloatFromXml(TypedXmlPullParser typedXmlPullParser, String str) {
        return typedXmlPullParser.getAttributeFloat(null, str, Float.NaN);
    }

    private static class ScaleAndTranslateLog implements BrightnessCorrectionImplementation {
        private static final String ATTR_SCALE = "scale";
        private static final String ATTR_TRANSLATE = "translate";
        private static final float MAX_SCALE = 2.0f;
        private static final float MAX_TRANSLATE = 0.7f;
        private static final float MIN_SCALE = 0.5f;
        private static final float MIN_TRANSLATE = -0.6f;
        private final float mScale;
        private final float mTranslate;

        ScaleAndTranslateLog(float f, float f2) {
            if (Float.isNaN(f) || Float.isNaN(f2)) {
                throw new IllegalArgumentException("scale and translate must be numbers");
            }
            this.mScale = MathUtils.constrain(f, 0.5f, 2.0f);
            this.mTranslate = MathUtils.constrain(f2, MIN_TRANSLATE, MAX_TRANSLATE);
        }

        @Override // android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation
        public float apply(float f) {
            return MathUtils.exp((this.mScale * MathUtils.log(f)) + this.mTranslate);
        }

        @Override // android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation
        public String toString() {
            return "ScaleAndTranslateLog(" + this.mScale + ", " + this.mTranslate + NavigationBarInflaterView.KEY_CODE_END;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ScaleAndTranslateLog)) {
                return false;
            }
            ScaleAndTranslateLog scaleAndTranslateLog = (ScaleAndTranslateLog) obj;
            return scaleAndTranslateLog.mScale == this.mScale && scaleAndTranslateLog.mTranslate == this.mTranslate;
        }

        public int hashCode() {
            return ((Float.hashCode(this.mScale) + 31) * 31) + Float.hashCode(this.mTranslate);
        }

        @Override // android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation
        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(1);
            parcel.writeFloat(this.mScale);
            parcel.writeFloat(this.mTranslate);
        }

        @Override // android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation
        public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
            typedXmlSerializer.startTag(null, BrightnessCorrection.TAG_SCALE_AND_TRANSLATE_LOG);
            typedXmlSerializer.attributeFloat(null, "scale", this.mScale);
            typedXmlSerializer.attributeFloat(null, ATTR_TRANSLATE, this.mTranslate);
            typedXmlSerializer.endTag(null, BrightnessCorrection.TAG_SCALE_AND_TRANSLATE_LOG);
        }

        static BrightnessCorrection readFromParcel(Parcel parcel) {
            return BrightnessCorrection.createScaleAndTranslateLog(parcel.readFloat(), parcel.readFloat());
        }

        static BrightnessCorrection loadFromXml(TypedXmlPullParser typedXmlPullParser) throws IOException, XmlPullParserException {
            return BrightnessCorrection.createScaleAndTranslateLog(BrightnessCorrection.loadFloatFromXml(typedXmlPullParser, "scale"), BrightnessCorrection.loadFloatFromXml(typedXmlPullParser, ATTR_TRANSLATE));
        }
    }
}
