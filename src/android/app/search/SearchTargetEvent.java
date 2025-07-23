package android.app.search;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SearchTargetEvent implements Parcelable {
    public static final int ACTION_DELETE = 9;
    public static final int ACTION_DISMISS = 10;
    public static final int ACTION_DRAGNDROP = 7;
    public static final int ACTION_LAUNCH_KEYBOARD_FOCUS = 6;
    public static final int ACTION_LAUNCH_TOUCH = 5;
    public static final int ACTION_LONGPRESS = 4;
    public static final int ACTION_SURFACE_INVISIBLE = 8;
    public static final int ACTION_SURFACE_VISIBLE = 1;
    public static final int ACTION_TAP = 3;
    public static final Parcelable.Creator<SearchTargetEvent> CREATOR = new Parcelable.Creator<SearchTargetEvent>() { // from class: android.app.search.SearchTargetEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchTargetEvent createFromParcel(Parcel parcel) {
            return new SearchTargetEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchTargetEvent[] newArray(int i) {
            return new SearchTargetEvent[i];
        }
    };
    public static final int FLAG_IME_SHOWN = 1;
    private final int mAction;
    private int mFlags;
    private final String mLocation;
    private final List<String> mTargetIds;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FlagType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SearchTargetEvent(List<String> list, String str, int i, int i2) {
        this.mTargetIds = (List) Objects.requireNonNull(list);
        this.mLocation = str;
        this.mAction = i;
        this.mFlags = i2;
    }

    private SearchTargetEvent(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mTargetIds = arrayList;
        parcel.readStringList(arrayList);
        this.mLocation = parcel.readString();
        this.mAction = parcel.readInt();
        this.mFlags = parcel.readInt();
    }

    public String getTargetId() {
        return this.mTargetIds.get(0);
    }

    public List<String> getTargetIds() {
        return this.mTargetIds;
    }

    public String getLaunchLocation() {
        return this.mLocation;
    }

    public int getAction() {
        return this.mAction;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int hashCode() {
        return this.mTargetIds.get(0).hashCode() + this.mAction;
    }

    public boolean equals(Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        SearchTargetEvent searchTargetEvent = (SearchTargetEvent) obj;
        return (this.mTargetIds.equals(searchTargetEvent.mTargetIds) && this.mAction == searchTargetEvent.mAction && this.mFlags == searchTargetEvent.mFlags && this.mLocation == null) ? searchTargetEvent.mLocation == null : this.mLocation.equals(searchTargetEvent.mLocation);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.mTargetIds);
        parcel.writeString(this.mLocation);
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mFlags);
    }

    @SystemApi
    public static final class Builder {
        private int mAction;
        private int mFlags;
        private String mLocation;
        private List<String> mTargetIds;

        public Builder(String str, int i) {
            ArrayList arrayList = new ArrayList();
            this.mTargetIds = arrayList;
            arrayList.add(str);
            this.mAction = i;
        }

        public Builder(List<String> list, int i) {
            this.mTargetIds = list;
            this.mAction = i;
        }

        public Builder setLaunchLocation(String str) {
            this.mLocation = str;
            return this;
        }

        public Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public SearchTargetEvent build() {
            return new SearchTargetEvent(this.mTargetIds, this.mLocation, this.mAction, this.mFlags);
        }
    }
}
