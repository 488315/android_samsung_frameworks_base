package com.android.internal.inputmethod;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.security.InvalidParameterException;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class InputMethodSubtypeHandle implements Parcelable {
    public static final Parcelable.Creator<InputMethodSubtypeHandle> CREATOR = new Parcelable.Creator<InputMethodSubtypeHandle>() { // from class: com.android.internal.inputmethod.InputMethodSubtypeHandle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMethodSubtypeHandle createFromParcel(Parcel parcel) {
            return InputMethodSubtypeHandle.of(parcel.readString8());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMethodSubtypeHandle[] newArray(int i) {
            return new InputMethodSubtypeHandle[i];
        }
    };
    private static final char DATA_SEPARATOR = ':';
    private static final String SUBTYPE_TAG = "subtype";
    private final String mHandle;

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RawHandle {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static String encodeHandle(String str, int i) {
        return str + ":subtype:" + i;
    }

    private InputMethodSubtypeHandle(String str) {
        this.mHandle = str;
    }

    public static InputMethodSubtypeHandle of(InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
        return new InputMethodSubtypeHandle(encodeHandle(inputMethodInfo.getId(), inputMethodSubtype != null ? inputMethodSubtype.hashCode() : 0));
    }

    public static InputMethodSubtypeHandle of(String str) {
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        simpleStringSplitter.setString((String) Objects.requireNonNull(str));
        if (!simpleStringSplitter.hasNext()) {
            throw new InvalidParameterException("Invalid handle=" + str);
        }
        String next = simpleStringSplitter.next();
        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(next);
        if (componentNameUnflattenFromString == null) {
            throw new InvalidParameterException("Invalid handle=" + str);
        }
        if (!Objects.equals(componentNameUnflattenFromString.flattenToShortString(), next)) {
            throw new InvalidParameterException("Invalid handle=" + str);
        }
        if (!simpleStringSplitter.hasNext()) {
            throw new InvalidParameterException("Invalid handle=" + str);
        }
        if (!Objects.equals(simpleStringSplitter.next(), SUBTYPE_TAG)) {
            throw new InvalidParameterException("Invalid handle=" + str);
        }
        if (!simpleStringSplitter.hasNext()) {
            throw new InvalidParameterException("Invalid handle=" + str);
        }
        String next2 = simpleStringSplitter.next();
        if (simpleStringSplitter.hasNext()) {
            throw new InvalidParameterException("Invalid handle=" + str);
        }
        try {
            if (!Objects.equals(encodeHandle(next, Integer.parseInt(next2)), str)) {
                throw new InvalidParameterException("Invalid handle=" + str);
            }
            return new InputMethodSubtypeHandle(str);
        } catch (NumberFormatException unused) {
            throw new InvalidParameterException("Invalid handle=" + str);
        }
    }

    public ComponentName getComponentName() {
        return ComponentName.unflattenFromString(getImeId());
    }

    public String getImeId() {
        String str = this.mHandle;
        return str.substring(0, str.indexOf(58));
    }

    public String toStringHandle() {
        return this.mHandle;
    }

    public boolean equals(Object obj) {
        if (obj instanceof InputMethodSubtypeHandle) {
            return Objects.equals(this.mHandle, ((InputMethodSubtypeHandle) obj).mHandle);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.mHandle);
    }

    public String toString() {
        return "InputMethodSubtypeHandle{mHandle=" + this.mHandle + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(toStringHandle());
    }
}
