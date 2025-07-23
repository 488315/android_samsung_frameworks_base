package android.content.pm;

import android.content.ComponentName;
import android.content.LocusId;
import android.content.pm.LauncherApps;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ShortcutQueryWrapper extends LauncherApps.ShortcutQuery implements Parcelable {
    public static final Parcelable.Creator<ShortcutQueryWrapper> CREATOR = new Parcelable.Creator<ShortcutQueryWrapper>() { // from class: android.content.pm.ShortcutQueryWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShortcutQueryWrapper[] newArray(int i) {
            return new ShortcutQueryWrapper[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShortcutQueryWrapper createFromParcel(Parcel parcel) {
            return new ShortcutQueryWrapper(parcel);
        }
    };

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ShortcutQueryWrapper(LauncherApps.ShortcutQuery shortcutQuery) {
        this();
        this.mChangedSince = shortcutQuery.mChangedSince;
        this.mPackage = shortcutQuery.mPackage;
        this.mLocusIds = shortcutQuery.mLocusIds;
        this.mShortcutIds = shortcutQuery.mShortcutIds;
        this.mActivity = shortcutQuery.mActivity;
        this.mQueryFlags = shortcutQuery.mQueryFlags;
    }

    public long getChangedSince() {
        return this.mChangedSince;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public List<LocusId> getLocusIds() {
        return this.mLocusIds;
    }

    public List<String> getShortcutIds() {
        return this.mShortcutIds;
    }

    public ComponentName getActivity() {
        return this.mActivity;
    }

    public int getQueryFlags() {
        return this.mQueryFlags;
    }

    public ShortcutQueryWrapper() {
    }

    public String toString() {
        return "ShortcutQueryWrapper {  }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mPackage != null ? (byte) 2 : (byte) 0;
        if (this.mShortcutIds != null) {
            b = (byte) (b | 4);
        }
        if (this.mLocusIds != null) {
            b = (byte) (b | 8);
        }
        if (this.mActivity != null) {
            b = (byte) (b | 16);
        }
        parcel.writeByte(b);
        parcel.writeLong(this.mChangedSince);
        if (this.mPackage != null) {
            parcel.writeString(this.mPackage);
        }
        if (this.mShortcutIds != null) {
            parcel.writeStringList(this.mShortcutIds);
        }
        if (this.mLocusIds != null) {
            parcel.writeParcelableList(this.mLocusIds, i);
        }
        if (this.mActivity != null) {
            parcel.writeTypedObject(this.mActivity, i);
        }
        parcel.writeInt(this.mQueryFlags);
    }

    ShortcutQueryWrapper(Parcel parcel) {
        ArrayList arrayList;
        ArrayList arrayList2;
        byte readByte = parcel.readByte();
        long readLong = parcel.readLong();
        String readString = (readByte & 2) == 0 ? null : parcel.readString();
        if ((readByte & 4) != 0) {
            arrayList = new ArrayList();
            parcel.readStringList(arrayList);
        } else {
            arrayList = null;
        }
        if ((readByte & 8) != 0) {
            arrayList2 = new ArrayList();
            parcel.readParcelableList(arrayList2, LocusId.class.getClassLoader(), LocusId.class);
        } else {
            arrayList2 = null;
        }
        ComponentName componentName = (readByte & 16) == 0 ? null : (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        int readInt = parcel.readInt();
        this.mChangedSince = readLong;
        this.mPackage = readString;
        this.mShortcutIds = arrayList;
        this.mLocusIds = arrayList2;
        this.mActivity = componentName;
        this.mQueryFlags = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) LauncherApps.ShortcutQuery.QueryFlags.class, (Annotation) null, this.mQueryFlags);
    }
}
