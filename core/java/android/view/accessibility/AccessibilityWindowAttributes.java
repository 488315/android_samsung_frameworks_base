package android.view.accessibility;

import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.WindowManager;

import java.util.Objects;

public final class AccessibilityWindowAttributes implements Parcelable {
    public static final Parcelable.Creator<AccessibilityWindowAttributes> CREATOR =
            new Parcelable.Creator<
                    AccessibilityWindowAttributes>() { // from class:
                                                       // android.view.accessibility.AccessibilityWindowAttributes.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AccessibilityWindowAttributes createFromParcel(Parcel in) {
                    return new AccessibilityWindowAttributes(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AccessibilityWindowAttributes[] newArray(int size) {
                    return new AccessibilityWindowAttributes[size];
                }
            };
    private final LocaleList mLocales;
    private final CharSequence mWindowTitle;

    public AccessibilityWindowAttributes(
            WindowManager.LayoutParams layoutParams, LocaleList locales) {
        this.mWindowTitle = populateWindowTitle(layoutParams);
        this.mLocales = locales;
    }

    private AccessibilityWindowAttributes(Parcel in) {
        this.mWindowTitle = in.readCharSequence();
        LocaleList inLocales = (LocaleList) in.readParcelable(null, LocaleList.class);
        if (inLocales != null) {
            this.mLocales = inLocales;
        } else {
            this.mLocales = LocaleList.getEmptyLocaleList();
        }
    }

    public CharSequence getWindowTitle() {
        return this.mWindowTitle;
    }

    private CharSequence populateWindowTitle(WindowManager.LayoutParams layoutParams) {
        CharSequence windowTitle = layoutParams.accessibilityTitle;
        boolean isPanelWindow = layoutParams.type >= 1000 && layoutParams.type <= 1999;
        boolean isAccessibilityOverlay = layoutParams.type == 2032;
        if (!TextUtils.isEmpty(windowTitle)) {
            return windowTitle;
        }
        if (isPanelWindow || isAccessibilityOverlay) {
            return TextUtils.isEmpty(layoutParams.getTitle()) ? null : layoutParams.getTitle();
        }
        return windowTitle;
    }

    public LocaleList getLocales() {
        return this.mLocales;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccessibilityWindowAttributes)) {
            return false;
        }
        AccessibilityWindowAttributes that = (AccessibilityWindowAttributes) o;
        return TextUtils.equals(this.mWindowTitle, that.mWindowTitle)
                && Objects.equals(this.mLocales, that.mLocales);
    }

    public int hashCode() {
        return Objects.hash(this.mWindowTitle, this.mLocales);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeCharSequence(this.mWindowTitle);
        parcel.writeParcelable(this.mLocales, flags);
    }

    public String toString() {
        return "AccessibilityWindowAttributes{mAccessibilityWindowTitle="
                + ((Object) this.mWindowTitle)
                + "mLocales="
                + this.mLocales
                + '}';
    }
}
