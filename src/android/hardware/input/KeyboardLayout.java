package android.hardware.input;

import android.app.blob.XmlTags;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class KeyboardLayout implements Parcelable, Comparable<KeyboardLayout> {
    public static final Parcelable.Creator<KeyboardLayout> CREATOR = new Parcelable.Creator<KeyboardLayout>() { // from class: android.hardware.input.KeyboardLayout.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardLayout createFromParcel(Parcel parcel) {
            return new KeyboardLayout(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardLayout[] newArray(int i) {
            return new KeyboardLayout[i];
        }
    };
    public static final String LAYOUT_TYPE_AZERTY = "azerty";
    public static final String LAYOUT_TYPE_COLEMAK = "colemak";
    public static final String LAYOUT_TYPE_DVORAK = "dvorak";
    public static final String LAYOUT_TYPE_EXTENDED = "extended";
    public static final String LAYOUT_TYPE_QWERTY = "qwerty";
    public static final String LAYOUT_TYPE_QWERTZ = "qwertz";
    public static final String LAYOUT_TYPE_TURKISH_F = "turkish_f";
    public static final String LAYOUT_TYPE_TURKISH_Q = "turkish_q";
    public static final String LAYOUT_TYPE_UNDEFINED = "undefined";
    public static final String LAYOUT_TYPE_WORKMAN = "workman";
    private final String mCollection;
    private final String mDescriptor;
    private final String mLabel;
    private final LayoutType mLayoutType;
    private final LocaleList mLocales;
    private final int mPriority;
    private final int mProductId;
    private final int mVendorId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public enum LayoutType {
        UNDEFINED(0, KeyboardLayout.LAYOUT_TYPE_UNDEFINED),
        QWERTY(1, "qwerty"),
        QWERTZ(2, KeyboardLayout.LAYOUT_TYPE_QWERTZ),
        AZERTY(3, KeyboardLayout.LAYOUT_TYPE_AZERTY),
        DVORAK(4, KeyboardLayout.LAYOUT_TYPE_DVORAK),
        COLEMAK(5, KeyboardLayout.LAYOUT_TYPE_COLEMAK),
        WORKMAN(6, KeyboardLayout.LAYOUT_TYPE_WORKMAN),
        TURKISH_Q(7, KeyboardLayout.LAYOUT_TYPE_TURKISH_Q),
        TURKISH_F(8, KeyboardLayout.LAYOUT_TYPE_TURKISH_F),
        EXTENDED(9, KeyboardLayout.LAYOUT_TYPE_EXTENDED);

        private final String mName;
        private final int mValue;
        private static final Map<Integer, LayoutType> VALUE_TO_ENUM_MAP = new HashMap();
        private static final Map<String, LayoutType> NAME_TO_ENUM_MAP = new HashMap();

        static {
            for (LayoutType layoutType : values()) {
                VALUE_TO_ENUM_MAP.put(Integer.valueOf(layoutType.mValue), layoutType);
                NAME_TO_ENUM_MAP.put(layoutType.mName, layoutType);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static LayoutType of(int i) {
            return VALUE_TO_ENUM_MAP.getOrDefault(Integer.valueOf(i), UNDEFINED);
        }

        LayoutType(int i, String str) {
            this.mValue = i;
            this.mName = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getValue() {
            return this.mValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getName() {
            return this.mName;
        }

        public static int getLayoutTypeEnumValue(String str) {
            return NAME_TO_ENUM_MAP.getOrDefault(str, UNDEFINED).getValue();
        }

        public static String getLayoutNameFromValue(int i) {
            return VALUE_TO_ENUM_MAP.getOrDefault(Integer.valueOf(i), UNDEFINED).getName();
        }
    }

    public KeyboardLayout(String str, String str2, String str3, int i, LocaleList localeList, int i2, int i3, int i4) {
        this.mDescriptor = str;
        this.mLabel = str2;
        this.mCollection = str3;
        this.mPriority = i;
        this.mLocales = localeList;
        this.mLayoutType = LayoutType.of(i2);
        this.mVendorId = i3;
        this.mProductId = i4;
    }

    private KeyboardLayout(Parcel parcel) {
        this.mDescriptor = parcel.readString();
        this.mLabel = parcel.readString();
        this.mCollection = parcel.readString();
        this.mPriority = parcel.readInt();
        this.mLocales = LocaleList.CREATOR.createFromParcel(parcel);
        this.mLayoutType = LayoutType.of(parcel.readInt());
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
    }

    public String getDescriptor() {
        return this.mDescriptor;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public String getCollection() {
        return this.mCollection;
    }

    public LocaleList getLocales() {
        return this.mLocales;
    }

    public String getLayoutType() {
        return this.mLayoutType.getName();
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public boolean isAnsiLayout() {
        for (int i = 0; i < this.mLocales.size(); i++) {
            Locale locale = this.mLocales.get(i);
            if (locale != null && locale.getCountry().equalsIgnoreCase(XmlTags.ATTR_USER_ID) && this.mLayoutType != LayoutType.EXTENDED) {
                return true;
            }
        }
        return false;
    }

    public boolean isJisLayout() {
        for (int i = 0; i < this.mLocales.size(); i++) {
            Locale locale = this.mLocales.get(i);
            if (locale != null && locale.getCountry().equalsIgnoreCase("jp")) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mDescriptor);
        parcel.writeString(this.mLabel);
        parcel.writeString(this.mCollection);
        parcel.writeInt(this.mPriority);
        this.mLocales.writeToParcel(parcel, 0);
        parcel.writeInt(this.mLayoutType.getValue());
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
    }

    @Override // java.lang.Comparable
    public int compareTo(KeyboardLayout keyboardLayout) {
        int compare = Integer.compare(keyboardLayout.mPriority, this.mPriority);
        if (compare == 0) {
            compare = Integer.compare(this.mLayoutType.mValue, keyboardLayout.mLayoutType.mValue);
        }
        if (compare == 0) {
            compare = this.mLabel.compareToIgnoreCase(keyboardLayout.mLabel);
        }
        return compare == 0 ? this.mCollection.compareToIgnoreCase(keyboardLayout.mCollection) : compare;
    }

    public String toString() {
        String str;
        if (this.mCollection.isEmpty()) {
            str = "";
        } else {
            str = " - " + this.mCollection;
        }
        return "KeyboardLayout " + this.mLabel + str + ", descriptor: " + this.mDescriptor + ", priority: " + this.mPriority + ", locales: " + this.mLocales.toString() + ", layout type: " + this.mLayoutType.getName() + ", vendorId: " + this.mVendorId + ", productId: " + this.mProductId;
    }

    public static boolean isLayoutTypeValid(String str) {
        Objects.requireNonNull(str, "Provided layout name should not be null");
        for (LayoutType layoutType : LayoutType.values()) {
            if (str.equals(layoutType.getName())) {
                return true;
            }
        }
        return false;
    }
}
