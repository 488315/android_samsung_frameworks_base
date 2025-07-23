package android.view.accessibility;

import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.WindowManager;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class AccessibilityWindowAttributes implements Parcelable {
    public static final Parcelable.Creator<AccessibilityWindowAttributes> CREATOR = new Parcelable.Creator<AccessibilityWindowAttributes>() { // from class: android.view.accessibility.AccessibilityWindowAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityWindowAttributes createFromParcel(Parcel parcel) {
            return new AccessibilityWindowAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityWindowAttributes[] newArray(int i) {
            return new AccessibilityWindowAttributes[i];
        }
    };
    private final LocaleList mLocales;
    private final CharSequence mWindowTitle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AccessibilityWindowAttributes(WindowManager.LayoutParams layoutParams, LocaleList localeList) {
        this.mWindowTitle = populateWindowTitle(layoutParams);
        this.mLocales = localeList;
    }

    private AccessibilityWindowAttributes(Parcel parcel) {
        this.mWindowTitle = parcel.readCharSequence();
        LocaleList localeList = (LocaleList) parcel.readParcelable(null, LocaleList.class);
        if (localeList != null) {
            this.mLocales = localeList;
        } else {
            this.mLocales = LocaleList.getEmptyLocaleList();
        }
    }

    public CharSequence getWindowTitle() {
        return this.mWindowTitle;
    }

    private CharSequence populateWindowTitle(WindowManager.LayoutParams layoutParams) {
        CharSequence charSequence = layoutParams.accessibilityTitle;
        boolean z = layoutParams.type >= 1000 && layoutParams.type <= 1999;
        boolean z2 = layoutParams.type == 2032;
        if (!TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        if (!z && !z2) {
            return charSequence;
        }
        if (TextUtils.isEmpty(layoutParams.getTitle())) {
            return null;
        }
        return layoutParams.getTitle();
    }

    public LocaleList getLocales() {
        return this.mLocales;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AccessibilityWindowAttributes)) {
            return false;
        }
        AccessibilityWindowAttributes accessibilityWindowAttributes = (AccessibilityWindowAttributes) obj;
        return TextUtils.equals(this.mWindowTitle, accessibilityWindowAttributes.mWindowTitle) && Objects.equals(this.mLocales, accessibilityWindowAttributes.mLocales);
    }

    public int hashCode() {
        return Objects.hash(this.mWindowTitle, this.mLocales);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mWindowTitle);
        parcel.writeParcelable(this.mLocales, i);
    }

    public String toString() {
        return "AccessibilityWindowAttributes{mAccessibilityWindowTitle=" + ((Object) this.mWindowTitle) + "mLocales=" + this.mLocales + '}';
    }
}
