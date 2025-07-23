package android.media.tv.interactive;

import android.annotation.NonNull;
import android.content.ComponentName;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes3.dex */
public final class AppLinkInfo implements Parcelable {
    public static final Parcelable.Creator<AppLinkInfo> CREATOR = new Parcelable.Creator<AppLinkInfo>() { // from class: android.media.tv.interactive.AppLinkInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppLinkInfo[] newArray(int i) {
            return new AppLinkInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppLinkInfo createFromParcel(Parcel parcel) {
            return new AppLinkInfo(parcel);
        }
    };
    private ComponentName mComponentName;
    private Uri mUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AppLinkInfo(String str, String str2, String str3) {
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str2);
        this.mComponentName = new ComponentName(str, str2);
        this.mUri = Uri.parse(str3);
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String toString() {
        return "AppLinkInfo { packageName = " + this.mComponentName.getPackageName() + ", className = " + this.mComponentName.getClassName() + ", uri = " + this.mUri.toString() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mComponentName.writeToParcel(parcel, i);
        Uri uri = this.mUri;
        parcel.writeString(uri == null ? null : uri.toString());
    }

    AppLinkInfo(Parcel parcel) {
        ComponentName readFromParcel = ComponentName.readFromParcel(parcel);
        this.mComponentName = readFromParcel;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readFromParcel.getPackageName());
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mComponentName.getClassName());
        String readString = parcel.readString();
        this.mUri = readString != null ? Uri.parse(readString) : null;
    }
}
