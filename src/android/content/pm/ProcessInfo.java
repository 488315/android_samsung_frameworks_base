package android.content.pm;

import android.annotation.NonNull;
import android.content.pm.ApplicationInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Annotation;

/* loaded from: classes.dex */
public class ProcessInfo implements Parcelable {
    public static final Parcelable.Creator<ProcessInfo> CREATOR;
    static Parcelling<ArraySet<String>> sParcellingForDeniedPermissions;
    public ArraySet<String> deniedPermissions;
    public int gwpAsanMode;
    public int memtagMode;
    public String name;
    public int nativeHeapZeroInitialized;
    public boolean useEmbeddedDex;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public ProcessInfo(ProcessInfo processInfo) {
        this.name = processInfo.name;
        this.deniedPermissions = processInfo.deniedPermissions;
        this.gwpAsanMode = processInfo.gwpAsanMode;
        this.memtagMode = processInfo.memtagMode;
        this.nativeHeapZeroInitialized = processInfo.nativeHeapZeroInitialized;
        this.useEmbeddedDex = processInfo.useEmbeddedDex;
    }

    public ProcessInfo(String str, ArraySet<String> arraySet, int i, int i2, int i3, boolean z) {
        this.name = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.deniedPermissions = arraySet;
        this.gwpAsanMode = i;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.GwpAsanMode.class, (Annotation) null, i);
        this.memtagMode = i2;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.MemtagMode.class, (Annotation) null, i2);
        this.nativeHeapZeroInitialized = i3;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.NativeHeapZeroInitialized.class, (Annotation) null, i3);
        this.useEmbeddedDex = z;
    }

    static {
        Parcelling<ArraySet<String>> parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedStringArraySet.class);
        sParcellingForDeniedPermissions = parcelling;
        if (parcelling == null) {
            sParcellingForDeniedPermissions = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedStringArraySet());
        }
        CREATOR = new Parcelable.Creator<ProcessInfo>() { // from class: android.content.pm.ProcessInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProcessInfo[] newArray(int i) {
                return new ProcessInfo[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProcessInfo createFromParcel(Parcel parcel) {
                return new ProcessInfo(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.useEmbeddedDex ? (byte) 32 : (byte) 0;
        if (this.deniedPermissions != null) {
            b = (byte) (b | 2);
        }
        parcel.writeByte(b);
        parcel.writeString(this.name);
        sParcellingForDeniedPermissions.parcel(this.deniedPermissions, parcel, i);
        parcel.writeInt(this.gwpAsanMode);
        parcel.writeInt(this.memtagMode);
        parcel.writeInt(this.nativeHeapZeroInitialized);
    }

    protected ProcessInfo(Parcel parcel) {
        boolean z = (parcel.readByte() & 32) != 0;
        String string = parcel.readString();
        ArraySet<String> arraySetUnparcel = sParcellingForDeniedPermissions.unparcel(parcel);
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        this.name = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.deniedPermissions = arraySetUnparcel;
        this.gwpAsanMode = i;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.GwpAsanMode.class, (Annotation) null, i);
        this.memtagMode = i2;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.MemtagMode.class, (Annotation) null, i2);
        this.nativeHeapZeroInitialized = i3;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.NativeHeapZeroInitialized.class, (Annotation) null, i3);
        this.useEmbeddedDex = z;
    }
}
