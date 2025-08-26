package android.app.ambientcontext;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;

@SystemApi
/* loaded from: classes.dex */
public final class AmbientContextEvent implements Parcelable {
    public static final Parcelable.Creator<AmbientContextEvent> CREATOR;
    public static final int EVENT_BACK_DOUBLE_TAP = 3;
    public static final int EVENT_COUGH = 1;
    public static final int EVENT_SNORE = 2;
    public static final int EVENT_UNKNOWN = 0;
    public static final int EVENT_VENDOR_WEARABLE_START = 100000;
    public static final String KEY_VENDOR_WEARABLE_EVENT_NAME = "wearable_event_name";
    public static final int LEVEL_HIGH = 5;
    public static final int LEVEL_LOW = 1;
    public static final int LEVEL_MEDIUM = 3;
    public static final int LEVEL_MEDIUM_HIGH = 4;
    public static final int LEVEL_MEDIUM_LOW = 2;
    public static final int LEVEL_UNKNOWN = 0;
    static Parcelling<Instant> sParcellingForEndTime;
    static Parcelling<Instant> sParcellingForStartTime;
    private final int mConfidenceLevel;
    private final int mDensityLevel;
    private final Instant mEndTime;
    private final int mEventType;
    private final Instant mStartTime;
    private final PersistableBundle mVendorData;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Level {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LevelValue {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultDensityLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultEventType() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Instant defaultStartTime() {
        return Instant.MIN;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Instant defaultEndTime() {
        return Instant.MAX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PersistableBundle defaultVendorData() {
        return new PersistableBundle();
    }

    public static String eventToString(int i) {
        if (i == 0) {
            return "EVENT_UNKNOWN";
        }
        if (i == 1) {
            return "EVENT_COUGH";
        }
        if (i == 2) {
            return "EVENT_SNORE";
        }
        if (i == 3) {
            return "EVENT_BACK_DOUBLE_TAP";
        }
        if (i == 100000) {
            return "EVENT_VENDOR_WEARABLE_START";
        }
        return Integer.toHexString(i);
    }

    public static String levelToString(int i) {
        if (i == 0) {
            return "LEVEL_UNKNOWN";
        }
        if (i == 1) {
            return "LEVEL_LOW";
        }
        if (i == 2) {
            return "LEVEL_MEDIUM_LOW";
        }
        if (i == 3) {
            return "LEVEL_MEDIUM";
        }
        if (i == 4) {
            return "LEVEL_MEDIUM_HIGH";
        }
        if (i == 5) {
            return "LEVEL_HIGH";
        }
        return Integer.toHexString(i);
    }

    AmbientContextEvent(int i, Instant instant, Instant instant2, int i2, int i3, PersistableBundle persistableBundle) {
        this.mEventType = i;
        AnnotationValidations.validate((Class<? extends Annotation>) EventCode.class, (Annotation) null, i);
        this.mStartTime = instant;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) instant);
        this.mEndTime = instant2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) instant2);
        this.mConfidenceLevel = i2;
        AnnotationValidations.validate((Class<? extends Annotation>) LevelValue.class, (Annotation) null, i2);
        this.mDensityLevel = i3;
        AnnotationValidations.validate((Class<? extends Annotation>) LevelValue.class, (Annotation) null, i3);
        this.mVendorData = persistableBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) persistableBundle);
    }

    public int getEventType() {
        return this.mEventType;
    }

    public Instant getStartTime() {
        return this.mStartTime;
    }

    public Instant getEndTime() {
        return this.mEndTime;
    }

    public int getConfidenceLevel() {
        return this.mConfidenceLevel;
    }

    public int getDensityLevel() {
        return this.mDensityLevel;
    }

    public PersistableBundle getVendorData() {
        return this.mVendorData;
    }

    public String toString() {
        return "AmbientContextEvent { eventType = " + this.mEventType + ", startTime = " + this.mStartTime + ", endTime = " + this.mEndTime + ", confidenceLevel = " + this.mConfidenceLevel + ", densityLevel = " + this.mDensityLevel + ", vendorData = " + this.mVendorData + " }";
    }

    static {
        Parcelling<Instant> parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForInstant.class);
        sParcellingForStartTime = parcelling;
        if (parcelling == null) {
            sParcellingForStartTime = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInstant());
        }
        Parcelling<Instant> parcelling2 = Parcelling.Cache.get(Parcelling.BuiltIn.ForInstant.class);
        sParcellingForEndTime = parcelling2;
        if (parcelling2 == null) {
            sParcellingForEndTime = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInstant());
        }
        CREATOR = new Parcelable.Creator<AmbientContextEvent>() { // from class: android.app.ambientcontext.AmbientContextEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AmbientContextEvent[] newArray(int i) {
                return new AmbientContextEvent[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AmbientContextEvent createFromParcel(Parcel parcel) {
                return new AmbientContextEvent(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mEventType);
        sParcellingForStartTime.parcel(this.mStartTime, parcel, i);
        sParcellingForEndTime.parcel(this.mEndTime, parcel, i);
        parcel.writeInt(this.mConfidenceLevel);
        parcel.writeInt(this.mDensityLevel);
        parcel.writeTypedObject(this.mVendorData, i);
    }

    AmbientContextEvent(Parcel parcel) {
        int i = parcel.readInt();
        Instant instantUnparcel = sParcellingForStartTime.unparcel(parcel);
        Instant instantUnparcel2 = sParcellingForEndTime.unparcel(parcel);
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
        this.mEventType = i;
        AnnotationValidations.validate((Class<? extends Annotation>) EventCode.class, (Annotation) null, i);
        this.mStartTime = instantUnparcel;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) instantUnparcel);
        this.mEndTime = instantUnparcel2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) instantUnparcel2);
        this.mConfidenceLevel = i2;
        AnnotationValidations.validate((Class<? extends Annotation>) LevelValue.class, (Annotation) null, i2);
        this.mDensityLevel = i3;
        AnnotationValidations.validate((Class<? extends Annotation>) LevelValue.class, (Annotation) null, i3);
        this.mVendorData = persistableBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) persistableBundle);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mConfidenceLevel;
        private int mDensityLevel;
        private Instant mEndTime;
        private int mEventType;
        private Instant mStartTime;
        private PersistableBundle mVendorData;

        public Builder setEventType(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mEventType = i;
            return this;
        }

        public Builder setStartTime(Instant instant) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mStartTime = instant;
            return this;
        }

        public Builder setEndTime(Instant instant) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mEndTime = instant;
            return this;
        }

        public Builder setConfidenceLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mConfidenceLevel = i;
            return this;
        }

        public Builder setDensityLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mDensityLevel = i;
            return this;
        }

        public Builder setVendorData(PersistableBundle persistableBundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mVendorData = persistableBundle;
            return this;
        }

        public AmbientContextEvent build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 64;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mEventType = AmbientContextEvent.defaultEventType();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mStartTime = AmbientContextEvent.defaultStartTime();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mEndTime = AmbientContextEvent.defaultEndTime();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mConfidenceLevel = AmbientContextEvent.defaultConfidenceLevel();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mDensityLevel = AmbientContextEvent.defaultDensityLevel();
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mVendorData = AmbientContextEvent.defaultVendorData();
            }
            return new AmbientContextEvent(this.mEventType, this.mStartTime, this.mEndTime, this.mConfidenceLevel, this.mDensityLevel, this.mVendorData);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 64) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
