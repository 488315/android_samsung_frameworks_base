package android.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public final class ComponentName implements Parcelable, Cloneable, Comparable<ComponentName> {
    public static final Parcelable.Creator<ComponentName> CREATOR = new Parcelable.Creator<ComponentName>() { // from class: android.content.ComponentName.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ComponentName createFromParcel(Parcel parcel) {
            return new ComponentName(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ComponentName[] newArray(int i) {
            return new ComponentName[i];
        }
    };
    private final String mClass;
    private final String mPackage;

    @FunctionalInterface
    public interface WithComponentName {
        ComponentName getComponentName();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static ComponentName createRelative(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("class name cannot be empty");
        }
        if (str2.charAt(0) == '.') {
            str2 = str + str2;
        }
        return new ComponentName(str, str2);
    }

    public static ComponentName createRelative(Context context, String str) {
        return createRelative(context.getPackageName(), str);
    }

    public ComponentName(String str, String str2) {
        if (str == null) {
            throw new NullPointerException("package name is null");
        }
        if (str2 == null) {
            throw new NullPointerException("class name is null");
        }
        this.mPackage = str;
        this.mClass = str2;
    }

    public ComponentName(Context context, String str) {
        if (str == null) {
            throw new NullPointerException("class name is null");
        }
        this.mPackage = context.getPackageName();
        this.mClass = str;
    }

    public ComponentName(Context context, Class<?> cls) {
        this.mPackage = context.getPackageName();
        this.mClass = cls.getName();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ComponentName m926clone() {
        return new ComponentName(this.mPackage, this.mClass);
    }

    public String getPackageName() {
        return this.mPackage;
    }

    public String getClassName() {
        return this.mClass;
    }

    public String getShortClassName() {
        int length;
        int length2;
        if (this.mClass.startsWith(this.mPackage) && (length2 = this.mClass.length()) > (length = this.mPackage.length()) && this.mClass.charAt(length) == '.') {
            return this.mClass.substring(length, length2);
        }
        return this.mClass;
    }

    private static void appendShortClassName(StringBuilder sb, String str, String str2) {
        int length;
        int length2;
        if (str2.startsWith(str) && (length2 = str2.length()) > (length = str.length()) && str2.charAt(length) == '.') {
            sb.append((CharSequence) str2, length, length2);
        } else {
            sb.append(str2);
        }
    }

    private static void printShortClassName(PrintWriter printWriter, String str, String str2) {
        int length;
        int length2;
        if (str2.startsWith(str) && (length2 = str2.length()) > (length = str.length()) && str2.charAt(length) == '.') {
            printWriter.write(str2, length, length2 - length);
        } else {
            printWriter.print(str2);
        }
    }

    public static String flattenToShortString(ComponentName componentName) {
        if (componentName == null) {
            return null;
        }
        return componentName.flattenToShortString();
    }

    public String flattenToString() {
        return this.mPackage + "/" + this.mClass;
    }

    public String flattenToShortString() {
        StringBuilder sb = new StringBuilder(this.mPackage.length() + this.mClass.length());
        appendShortString(sb, this.mPackage, this.mClass);
        return sb.toString();
    }

    public void appendShortString(StringBuilder sb) {
        appendShortString(sb, this.mPackage, this.mClass);
    }

    public static void appendShortString(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append('/');
        appendShortClassName(sb, str, str2);
    }

    public static void printShortString(PrintWriter printWriter, String str, String str2) {
        printWriter.print(str);
        printWriter.print('/');
        printShortClassName(printWriter, str, str2);
    }

    public static ComponentName unflattenFromString(String str) {
        int i;
        int indexOf = str.indexOf(47);
        if (indexOf < 0 || (i = indexOf + 1) >= str.length()) {
            return null;
        }
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(i);
        if (substring2.length() > 0 && substring2.charAt(0) == '.') {
            substring2 = substring + substring2;
        }
        return new ComponentName(substring, substring2);
    }

    public String toShortString() {
        return "{" + this.mPackage + "/" + this.mClass + "}";
    }

    public String toString() {
        return "ComponentInfo{" + this.mPackage + "/" + this.mClass + "}";
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mPackage);
        protoOutputStream.write(1138166333442L, this.mClass);
        protoOutputStream.end(start);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ComponentName) {
            ComponentName componentName = (ComponentName) obj;
            if (this.mPackage.equals(componentName.mPackage) && this.mClass.equals(componentName.mClass)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.mPackage.hashCode() + this.mClass.hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(ComponentName componentName) {
        int compareTo = this.mPackage.compareTo(componentName.mPackage);
        return compareTo != 0 ? compareTo : this.mClass.compareTo(componentName.mClass);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackage);
        parcel.writeString(this.mClass);
    }

    public static void writeToParcel(ComponentName componentName, Parcel parcel) {
        if (componentName != null) {
            componentName.writeToParcel(parcel, 0);
        } else {
            parcel.writeString(null);
        }
    }

    public static ComponentName readFromParcel(Parcel parcel) {
        String readString = parcel.readString();
        if (readString != null) {
            return new ComponentName(readString, parcel);
        }
        return null;
    }

    public ComponentName(Parcel parcel) {
        String readString = parcel.readString();
        this.mPackage = readString;
        if (readString == null) {
            throw new NullPointerException("package name is null");
        }
        String readString2 = parcel.readString();
        this.mClass = readString2;
        if (readString2 == null) {
            throw new NullPointerException("class name is null");
        }
    }

    private ComponentName(String str, Parcel parcel) {
        this.mPackage = str;
        this.mClass = parcel.readString();
    }
}
