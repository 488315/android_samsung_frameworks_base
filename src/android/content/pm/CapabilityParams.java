package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class CapabilityParams implements Parcelable {
    public static final Parcelable.Creator<CapabilityParams> CREATOR = new Parcelable.Creator<CapabilityParams>() { // from class: android.content.pm.CapabilityParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CapabilityParams[] newArray(int i) {
            return new CapabilityParams[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CapabilityParams createFromParcel(Parcel parcel) {
            return new CapabilityParams(parcel);
        }
    };
    private final List<String> mAliases;
    private final String mName;
    private final String mPrimaryValue;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CapabilityParams(String str, String str2, Collection<String> collection) {
        List<String> listUnmodifiableList;
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        this.mName = str;
        this.mPrimaryValue = str2;
        if (collection == null) {
            listUnmodifiableList = Collections.EMPTY_LIST;
        } else {
            listUnmodifiableList = Collections.unmodifiableList(new ArrayList(collection));
        }
        this.mAliases = listUnmodifiableList;
    }

    CapabilityParams(CapabilityParams capabilityParams) {
        this(capabilityParams.mName, capabilityParams.mPrimaryValue, capabilityParams.mAliases);
    }

    private CapabilityParams(Builder builder) {
        this(builder.mKey, builder.mPrimaryValue, builder.mAliases);
    }

    private CapabilityParams(Parcel parcel) {
        this.mName = parcel.readString();
        this.mPrimaryValue = parcel.readString();
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        this.mAliases = Collections.unmodifiableList(arrayList);
    }

    public String getName() {
        return this.mName;
    }

    public String getValue() {
        return this.mPrimaryValue;
    }

    public List<String> getAliases() {
        return new ArrayList(this.mAliases);
    }

    List<String> getValues() {
        if (this.mAliases == null) {
            return new ArrayList(Collections.singletonList(this.mPrimaryValue));
        }
        ArrayList arrayList = new ArrayList(this.mAliases.size() + 1);
        arrayList.add(this.mPrimaryValue);
        arrayList.addAll(this.mAliases);
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CapabilityParams)) {
            return false;
        }
        CapabilityParams capabilityParams = (CapabilityParams) obj;
        return this.mName.equals(capabilityParams.mName) && this.mPrimaryValue.equals(capabilityParams.mPrimaryValue) && this.mAliases.equals(capabilityParams.mAliases);
    }

    public int hashCode() {
        return Objects.hash(this.mName, this.mPrimaryValue, this.mAliases);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mPrimaryValue);
        parcel.writeStringList(this.mAliases);
    }

    public static final class Builder {
        private Set<String> mAliases;
        private final String mKey;
        private final String mPrimaryValue;

        public Builder(String str, String str2) {
            Objects.requireNonNull(str);
            if (TextUtils.isEmpty(str2)) {
                throw new IllegalArgumentException("Primary value cannot be empty or null");
            }
            this.mPrimaryValue = str2;
            this.mKey = str;
        }

        public Builder addAlias(String str) {
            if (this.mAliases == null) {
                this.mAliases = new ArraySet(1);
            }
            this.mAliases.add(str);
            return this;
        }

        public CapabilityParams build() {
            return new CapabilityParams(this);
        }
    }
}
