package android.service.ambientcontext;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.ambientcontext.AmbientContextEvent;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class AmbientContextDetectionResult implements Parcelable {
    public static final Parcelable.Creator<AmbientContextDetectionResult> CREATOR = new Parcelable.Creator<AmbientContextDetectionResult>() { // from class: android.service.ambientcontext.AmbientContextDetectionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientContextDetectionResult[] newArray(int i) {
            return new AmbientContextDetectionResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientContextDetectionResult createFromParcel(Parcel parcel) {
            return new AmbientContextDetectionResult(parcel);
        }
    };
    public static final String RESULT_RESPONSE_BUNDLE_KEY = "android.app.ambientcontext.AmbientContextDetectionResultBundleKey";
    private final List<AmbientContextEvent> mEvents;
    private final String mPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AmbientContextDetectionResult(List<AmbientContextEvent> list, String str) {
        this.mEvents = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
        this.mPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
    }

    public List<AmbientContextEvent> getEvents() {
        return this.mEvents;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String toString() {
        return "AmbientContextEventResponse { events = " + this.mEvents + ", packageName = " + this.mPackageName + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) 0);
        parcel.writeParcelableList(this.mEvents, i);
        parcel.writeString(this.mPackageName);
    }

    AmbientContextDetectionResult(Parcel parcel) {
        parcel.readByte();
        ArrayList arrayList = new ArrayList();
        parcel.readParcelableList(arrayList, AmbientContextEvent.class.getClassLoader(), AmbientContextEvent.class);
        String readString = parcel.readString();
        this.mEvents = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mPackageName = readString;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private ArrayList<AmbientContextEvent> mEvents;
        private String mPackageName;

        public Builder(String str) {
            Objects.requireNonNull(str);
            this.mPackageName = str;
        }

        public Builder addEvent(AmbientContextEvent ambientContextEvent) {
            checkNotUsed();
            if (this.mEvents == null) {
                this.mBuilderFieldsSet |= 1;
                this.mEvents = new ArrayList<>();
            }
            this.mEvents.add(ambientContextEvent);
            return this;
        }

        public Builder addEvents(List<AmbientContextEvent> list) {
            checkNotUsed();
            if (this.mEvents == null) {
                this.mBuilderFieldsSet |= 1;
                this.mEvents = new ArrayList<>();
            }
            this.mEvents.addAll(list);
            return this;
        }

        public Builder clearEvents() {
            checkNotUsed();
            ArrayList<AmbientContextEvent> arrayList = this.mEvents;
            if (arrayList != null) {
                arrayList.clear();
            }
            return this;
        }

        public AmbientContextDetectionResult build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 2;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mEvents = new ArrayList<>();
            }
            return new AmbientContextDetectionResult(this.mEvents, this.mPackageName);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
