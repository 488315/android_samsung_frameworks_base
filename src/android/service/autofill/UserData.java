package android.service.autofill;

import android.app.ActivityThread;
import android.content.ContentResolver;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import com.samsung.android.wifi.SemWifiManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class UserData implements FieldClassificationUserData, Parcelable {
    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() { // from class: android.service.autofill.UserData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserData createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            String[] readStringArray = parcel.readStringArray();
            String[] readStringArray2 = parcel.readStringArray();
            String readString2 = parcel.readString();
            Bundle readBundle = parcel.readBundle();
            ArrayMap arrayMap = new ArrayMap();
            parcel.readMap(arrayMap, String.class.getClassLoader());
            Map arrayMap2 = new ArrayMap();
            parcel.readMap(arrayMap2, Bundle.class.getClassLoader());
            Builder fieldClassificationAlgorithm = new Builder(readString, readStringArray2[0], readStringArray[0]).setFieldClassificationAlgorithm(readString2, readBundle);
            for (int i = 1; i < readStringArray.length; i++) {
                fieldClassificationAlgorithm.add(readStringArray2[i], readStringArray[i]);
            }
            int size = arrayMap.size();
            if (size > 0) {
                for (int i2 = 0; i2 < size; i2++) {
                    String str = (String) arrayMap.keyAt(i2);
                    fieldClassificationAlgorithm.setFieldClassificationAlgorithmForCategory(str, (String) arrayMap.valueAt(i2), (Bundle) arrayMap2.get(str));
                }
            }
            return fieldClassificationAlgorithm.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserData[] newArray(int i) {
            return new UserData[i];
        }
    };
    private static final int DEFAULT_MAX_CATEGORY_COUNT = 10;
    private static final int DEFAULT_MAX_FIELD_CLASSIFICATION_IDS_SIZE = 10;
    private static final int DEFAULT_MAX_USER_DATA_SIZE = 50;
    private static final int DEFAULT_MAX_VALUE_LENGTH = 100;
    private static final int DEFAULT_MIN_VALUE_LENGTH = 3;
    private static final String TAG = "UserData";
    private final ArrayMap<String, String> mCategoryAlgorithms;
    private final ArrayMap<String, Bundle> mCategoryArgs;
    private final String[] mCategoryIds;
    private final String mDefaultAlgorithm;
    private final Bundle mDefaultArgs;
    private final String mId;
    private final String[] mValues;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UserData(Builder builder) {
        this.mId = builder.mId;
        String[] strArr = new String[builder.mCategoryIds.size()];
        this.mCategoryIds = strArr;
        builder.mCategoryIds.toArray(strArr);
        String[] strArr2 = new String[builder.mValues.size()];
        this.mValues = strArr2;
        builder.mValues.toArray(strArr2);
        builder.mValues.toArray(strArr2);
        this.mDefaultAlgorithm = builder.mDefaultAlgorithm;
        this.mDefaultArgs = builder.mDefaultArgs;
        this.mCategoryAlgorithms = builder.mCategoryAlgorithms;
        this.mCategoryArgs = builder.mCategoryArgs;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public String getFieldClassificationAlgorithm() {
        return this.mDefaultAlgorithm;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public Bundle getDefaultFieldClassificationArgs() {
        return this.mDefaultArgs;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public String getFieldClassificationAlgorithmForCategory(String str) {
        Objects.requireNonNull(str);
        ArrayMap<String, String> arrayMap = this.mCategoryAlgorithms;
        if (arrayMap == null || !arrayMap.containsKey(str)) {
            return null;
        }
        return this.mCategoryAlgorithms.get(str);
    }

    public String getId() {
        return this.mId;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public String[] getCategoryIds() {
        return this.mCategoryIds;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public String[] getValues() {
        return this.mValues;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public ArrayMap<String, String> getFieldClassificationAlgorithms() {
        return this.mCategoryAlgorithms;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public ArrayMap<String, Bundle> getFieldClassificationArgs() {
        return this.mCategoryArgs;
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("id: ");
        printWriter.print(this.mId);
        printWriter.print(str);
        printWriter.print("Default Algorithm: ");
        printWriter.print(this.mDefaultAlgorithm);
        printWriter.print(str);
        printWriter.print("Default Args");
        printWriter.print(this.mDefaultArgs);
        ArrayMap<String, String> arrayMap = this.mCategoryAlgorithms;
        if (arrayMap != null && arrayMap.size() > 0) {
            printWriter.print(str);
            printWriter.print("Algorithms per category: ");
            for (int i = 0; i < this.mCategoryAlgorithms.size(); i++) {
                printWriter.print(str);
                printWriter.print(str);
                printWriter.print(this.mCategoryAlgorithms.keyAt(i));
                printWriter.print(": ");
                printWriter.println(Helper.getRedacted(this.mCategoryAlgorithms.valueAt(i)));
                printWriter.print("args=");
                printWriter.print(this.mCategoryArgs.get(this.mCategoryAlgorithms.keyAt(i)));
            }
        }
        printWriter.print(str);
        printWriter.print("Field ids size: ");
        printWriter.println(this.mCategoryIds.length);
        for (int i2 = 0; i2 < this.mCategoryIds.length; i2++) {
            printWriter.print(str);
            printWriter.print(str);
            printWriter.print(i2);
            printWriter.print(": ");
            printWriter.println(Helper.getRedacted(this.mCategoryIds[i2]));
        }
        printWriter.print(str);
        printWriter.print("Values size: ");
        printWriter.println(this.mValues.length);
        for (int i3 = 0; i3 < this.mValues.length; i3++) {
            printWriter.print(str);
            printWriter.print(str);
            printWriter.print(i3);
            printWriter.print(": ");
            printWriter.println(Helper.getRedacted(this.mValues[i3]));
        }
    }

    public static void dumpConstraints(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("maxUserDataSize: ");
        printWriter.println(getMaxUserDataSize());
        printWriter.print(str);
        printWriter.print("maxFieldClassificationIdsSize: ");
        printWriter.println(getMaxFieldClassificationIdsSize());
        printWriter.print(str);
        printWriter.print("maxCategoryCount: ");
        printWriter.println(getMaxCategoryCount());
        printWriter.print(str);
        printWriter.print("minValueLength: ");
        printWriter.println(getMinValueLength());
        printWriter.print(str);
        printWriter.print("maxValueLength: ");
        printWriter.println(getMaxValueLength());
    }

    public static final class Builder {
        private ArrayMap<String, String> mCategoryAlgorithms;
        private ArrayMap<String, Bundle> mCategoryArgs;
        private final ArrayList<String> mCategoryIds;
        private String mDefaultAlgorithm;
        private Bundle mDefaultArgs;
        private boolean mDestroyed;
        private final String mId;
        private final ArraySet<String> mUniqueCategoryIds;
        private final ArraySet<String> mUniqueValueCategoryPairs;
        private final ArrayList<String> mValues;

        public Builder(String str, String str2, String str3) {
            this.mId = checkNotEmpty("id", str);
            checkNotEmpty(SemWifiManager.EXTRA_CATEGORY_ID, str3);
            checkValidValue(str2);
            int maxUserDataSize = UserData.getMaxUserDataSize();
            this.mCategoryIds = new ArrayList<>(maxUserDataSize);
            this.mValues = new ArrayList<>(maxUserDataSize);
            this.mUniqueValueCategoryPairs = new ArraySet<>(maxUserDataSize);
            this.mUniqueCategoryIds = new ArraySet<>(UserData.getMaxCategoryCount());
            addMapping(str2, str3);
        }

        public Builder setFieldClassificationAlgorithm(String str, Bundle bundle) {
            throwIfDestroyed();
            this.mDefaultAlgorithm = str;
            this.mDefaultArgs = bundle;
            return this;
        }

        public Builder setFieldClassificationAlgorithmForCategory(String str, String str2, Bundle bundle) {
            throwIfDestroyed();
            Objects.requireNonNull(str);
            if (this.mCategoryAlgorithms == null) {
                this.mCategoryAlgorithms = new ArrayMap<>(UserData.getMaxCategoryCount());
            }
            if (this.mCategoryArgs == null) {
                this.mCategoryArgs = new ArrayMap<>(UserData.getMaxCategoryCount());
            }
            this.mCategoryAlgorithms.put(str, str2);
            this.mCategoryArgs.put(str, bundle);
            return this;
        }

        public Builder add(String str, String str2) {
            throwIfDestroyed();
            checkNotEmpty(SemWifiManager.EXTRA_CATEGORY_ID, str2);
            checkValidValue(str);
            if (!this.mUniqueCategoryIds.contains(str2)) {
                Preconditions.checkState(this.mUniqueCategoryIds.size() < UserData.getMaxCategoryCount(), "already added %d unique category ids", Integer.valueOf(this.mUniqueCategoryIds.size()));
            }
            Preconditions.checkState(this.mValues.size() < UserData.getMaxUserDataSize(), "already added %d elements", Integer.valueOf(this.mValues.size()));
            addMapping(str, str2);
            return this;
        }

        private void addMapping(String str, String str2) {
            String str3 = str + ":" + str2;
            if (this.mUniqueValueCategoryPairs.contains(str3)) {
                Log.w(UserData.TAG, "Ignoring entry with same value / category");
                return;
            }
            this.mCategoryIds.add(str2);
            this.mValues.add(str);
            this.mUniqueCategoryIds.add(str2);
            this.mUniqueValueCategoryPairs.add(str3);
        }

        private String checkNotEmpty(String str, String str2) {
            Objects.requireNonNull(str2);
            Preconditions.checkArgument(!TextUtils.isEmpty(str2), "%s cannot be empty", str);
            return str2;
        }

        private void checkValidValue(String str) {
            Objects.requireNonNull(str);
            int length = str.length();
            Preconditions.checkArgumentInRange(length, UserData.getMinValueLength(), UserData.getMaxValueLength(), "value length (" + length + NavigationBarInflaterView.KEY_CODE_END);
        }

        public UserData build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new UserData(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new IllegalStateException("Already called #build()");
            }
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder sb = new StringBuilder("UserData: [id=");
        sb.append(this.mId);
        sb.append(", categoryIds=");
        Helper.appendRedacted(sb, this.mCategoryIds);
        sb.append(", values=");
        Helper.appendRedacted(sb, this.mValues);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeStringArray(this.mCategoryIds);
        parcel.writeStringArray(this.mValues);
        parcel.writeString(this.mDefaultAlgorithm);
        parcel.writeBundle(this.mDefaultArgs);
        parcel.writeMap(this.mCategoryAlgorithms);
        parcel.writeMap(this.mCategoryArgs);
    }

    public static int getMaxUserDataSize() {
        return getInt(Settings.Secure.AUTOFILL_USER_DATA_MAX_USER_DATA_SIZE, 50);
    }

    public static int getMaxFieldClassificationIdsSize() {
        return getInt(Settings.Secure.AUTOFILL_USER_DATA_MAX_FIELD_CLASSIFICATION_IDS_SIZE, 10);
    }

    public static int getMaxCategoryCount() {
        return getInt(Settings.Secure.AUTOFILL_USER_DATA_MAX_CATEGORY_COUNT, 10);
    }

    public static int getMinValueLength() {
        return getInt(Settings.Secure.AUTOFILL_USER_DATA_MIN_VALUE_LENGTH, 3);
    }

    public static int getMaxValueLength() {
        return getInt(Settings.Secure.AUTOFILL_USER_DATA_MAX_VALUE_LENGTH, 100);
    }

    private static int getInt(String str, int i) {
        ActivityThread currentActivityThread = ActivityThread.currentActivityThread();
        ContentResolver contentResolver = currentActivityThread != null ? currentActivityThread.getApplication().getContentResolver() : null;
        if (contentResolver == null) {
            Log.w(TAG, "Could not read from " + str + "; hardcoding " + i);
            return i;
        }
        return Settings.Secure.getInt(contentResolver, str, i);
    }
}
