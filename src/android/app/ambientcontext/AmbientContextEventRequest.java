package android.app.ambientcontext;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.ambientcontext.AmbientContextEvent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.ArraySet;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@SystemApi
/* loaded from: classes.dex */
public final class AmbientContextEventRequest implements Parcelable {
    public static final Parcelable.Creator<AmbientContextEventRequest> CREATOR = new Parcelable.Creator<AmbientContextEventRequest>() { // from class: android.app.ambientcontext.AmbientContextEventRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientContextEventRequest[] newArray(int i) {
            return new AmbientContextEventRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientContextEventRequest createFromParcel(Parcel parcel) {
            return new AmbientContextEventRequest(parcel);
        }
    };
    private final Set<Integer> mEventTypes;
    private final PersistableBundle mOptions;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AmbientContextEventRequest(Set<Integer> set, PersistableBundle persistableBundle) {
        this.mEventTypes = set;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) set);
        Preconditions.checkArgument(!set.isEmpty(), "eventTypes cannot be empty");
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            AnnotationValidations.validate((Class<? extends Annotation>) AmbientContextEvent.EventCode.class, (Annotation) null, it.next().intValue());
        }
        this.mOptions = persistableBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) persistableBundle);
    }

    public Set<Integer> getEventTypes() {
        return this.mEventTypes;
    }

    public PersistableBundle getOptions() {
        return this.mOptions;
    }

    public String toString() {
        return "AmbientContextEventRequest { eventTypes = " + this.mEventTypes + ", options = " + this.mOptions + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeArraySet(new ArraySet<>(this.mEventTypes));
        parcel.writeTypedObject(this.mOptions, i);
    }

    private AmbientContextEventRequest(Parcel parcel) {
        ArraySet<? extends Object> arraySet = parcel.readArraySet(Integer.class.getClassLoader());
        PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
        this.mEventTypes = arraySet;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arraySet);
        Preconditions.checkArgument(!arraySet.isEmpty(), "eventTypes cannot be empty");
        Iterator<? extends Object> it = arraySet.iterator();
        while (it.hasNext()) {
            AnnotationValidations.validate((Class<? extends Annotation>) AmbientContextEvent.EventCode.class, (Annotation) null, ((Integer) it.next()).intValue());
        }
        this.mOptions = persistableBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) persistableBundle);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private Set<Integer> mEventTypes;
        private PersistableBundle mOptions;

        public Builder addEventType(int i) {
            checkNotUsed();
            if (this.mEventTypes == null) {
                this.mBuilderFieldsSet |= 1;
                this.mEventTypes = new HashSet();
            }
            this.mEventTypes.add(Integer.valueOf(i));
            return this;
        }

        public Builder setOptions(PersistableBundle persistableBundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mOptions = persistableBundle;
            return this;
        }

        public AmbientContextEventRequest build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 4;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mEventTypes = new HashSet();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mOptions = new PersistableBundle();
            }
            return new AmbientContextEventRequest(this.mEventTypes, this.mOptions);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
