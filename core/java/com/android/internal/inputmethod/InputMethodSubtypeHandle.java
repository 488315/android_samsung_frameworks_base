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

public final class InputMethodSubtypeHandle implements Parcelable {
    public static final Parcelable.Creator<InputMethodSubtypeHandle> CREATOR =
            new Parcelable.Creator<
                    InputMethodSubtypeHandle>() { // from class:
                                                  // com.android.internal.inputmethod.InputMethodSubtypeHandle.1
                @Override // android.os.Parcelable.Creator
                public InputMethodSubtypeHandle createFromParcel(Parcel in) {
                    return InputMethodSubtypeHandle.of(in.readString8());
                }

                @Override // android.os.Parcelable.Creator
                public InputMethodSubtypeHandle[] newArray(int size) {
                    return new InputMethodSubtypeHandle[size];
                }
            };
    private static final char DATA_SEPARATOR = ':';
    private static final String SUBTYPE_TAG = "subtype";
    private final String mHandle;

    @Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.LOCAL_VARIABLE,
        ElementType.PARAMETER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RawHandle {}

    private static String encodeHandle(String imeId, int subtypeHashCode) {
        return imeId + ':' + SUBTYPE_TAG + ':' + subtypeHashCode;
    }

    private InputMethodSubtypeHandle(String handle) {
        this.mHandle = handle;
    }

    public static InputMethodSubtypeHandle of(InputMethodInfo imi, InputMethodSubtype subtype) {
        int subtypeHashCode = subtype != null ? subtype.hashCode() : 0;
        return new InputMethodSubtypeHandle(encodeHandle(imi.getId(), subtypeHashCode));
    }

    public static InputMethodSubtypeHandle of(String stringHandle) {
        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');
        splitter.setString((String) Objects.requireNonNull(stringHandle));
        if (!splitter.hasNext()) {
            throw new InvalidParameterException("Invalid handle=" + stringHandle);
        }
        String imeId = splitter.next();
        ComponentName componentName = ComponentName.unflattenFromString(imeId);
        if (componentName == null) {
            throw new InvalidParameterException("Invalid handle=" + stringHandle);
        }
        if (!Objects.equals(componentName.flattenToShortString(), imeId)) {
            throw new InvalidParameterException("Invalid handle=" + stringHandle);
        }
        if (!splitter.hasNext()) {
            throw new InvalidParameterException("Invalid handle=" + stringHandle);
        }
        String source = splitter.next();
        if (!Objects.equals(source, SUBTYPE_TAG)) {
            throw new InvalidParameterException("Invalid handle=" + stringHandle);
        }
        if (!splitter.hasNext()) {
            throw new InvalidParameterException("Invalid handle=" + stringHandle);
        }
        String hashCodeStr = splitter.next();
        if (splitter.hasNext()) {
            throw new InvalidParameterException("Invalid handle=" + stringHandle);
        }
        try {
            int subtypeHashCode = Integer.parseInt(hashCodeStr);
            if (!Objects.equals(encodeHandle(imeId, subtypeHashCode), stringHandle)) {
                throw new InvalidParameterException("Invalid handle=" + stringHandle);
            }
            return new InputMethodSubtypeHandle(stringHandle);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Invalid handle=" + stringHandle);
        }
    }

    public ComponentName getComponentName() {
        return ComponentName.unflattenFromString(getImeId());
    }

    public String getImeId() {
        return this.mHandle.substring(0, this.mHandle.indexOf(58));
    }

    public String toStringHandle() {
        return this.mHandle;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof InputMethodSubtypeHandle)) {
            return false;
        }
        InputMethodSubtypeHandle that = (InputMethodSubtypeHandle) obj;
        return Objects.equals(this.mHandle, that.mHandle);
    }

    public int hashCode() {
        return Objects.hashCode(this.mHandle);
    }

    public String toString() {
        return "InputMethodSubtypeHandle{mHandle=" + this.mHandle + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(toStringHandle());
    }
}
