package android.service.autofill;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.view.autofill.Helper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class CompositeUserData implements FieldClassificationUserData, Parcelable {
    public static final Parcelable.Creator<CompositeUserData> CREATOR = new Parcelable.Creator<CompositeUserData>() { // from class: android.service.autofill.CompositeUserData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompositeUserData createFromParcel(Parcel parcel) {
            return new CompositeUserData((UserData) parcel.readParcelable(null, UserData.class), (UserData) parcel.readParcelable(null, UserData.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompositeUserData[] newArray(int i) {
            return new CompositeUserData[i];
        }
    };
    private final String[] mCategories;
    private final UserData mGenericUserData;
    private final UserData mPackageUserData;
    private final String[] mValues;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CompositeUserData(UserData userData, UserData userData2) {
        this.mGenericUserData = userData;
        this.mPackageUserData = userData2;
        String[] categoryIds = userData2.getCategoryIds();
        String[] values = userData2.getValues();
        ArrayList arrayList = new ArrayList(categoryIds.length);
        ArrayList arrayList2 = new ArrayList(values.length);
        Collections.addAll(arrayList, categoryIds);
        Collections.addAll(arrayList2, values);
        if (userData != null) {
            String[] categoryIds2 = userData.getCategoryIds();
            String[] values2 = userData.getValues();
            int length = userData.getCategoryIds().length;
            for (int i = 0; i < length; i++) {
                if (!arrayList.contains(categoryIds2[i])) {
                    arrayList.add(categoryIds2[i]);
                    arrayList2.add(values2[i]);
                }
            }
        }
        String[] strArr = new String[arrayList.size()];
        this.mCategories = strArr;
        arrayList.toArray(strArr);
        String[] strArr2 = new String[arrayList2.size()];
        this.mValues = strArr2;
        arrayList2.toArray(strArr2);
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public String getFieldClassificationAlgorithm() {
        String fieldClassificationAlgorithm = this.mPackageUserData.getFieldClassificationAlgorithm();
        if (fieldClassificationAlgorithm != null) {
            return fieldClassificationAlgorithm;
        }
        UserData userData = this.mGenericUserData;
        if (userData == null) {
            return null;
        }
        return userData.getFieldClassificationAlgorithm();
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public Bundle getDefaultFieldClassificationArgs() {
        Bundle defaultFieldClassificationArgs = this.mPackageUserData.getDefaultFieldClassificationArgs();
        if (defaultFieldClassificationArgs != null) {
            return defaultFieldClassificationArgs;
        }
        UserData userData = this.mGenericUserData;
        if (userData == null) {
            return null;
        }
        return userData.getDefaultFieldClassificationArgs();
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public String getFieldClassificationAlgorithmForCategory(String str) {
        Objects.requireNonNull(str);
        ArrayMap<String, String> fieldClassificationAlgorithms = getFieldClassificationAlgorithms();
        if (fieldClassificationAlgorithms == null || !fieldClassificationAlgorithms.containsKey(str)) {
            return null;
        }
        return fieldClassificationAlgorithms.get(str);
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public ArrayMap<String, String> getFieldClassificationAlgorithms() {
        ArrayMap<String, String> fieldClassificationAlgorithms = this.mPackageUserData.getFieldClassificationAlgorithms();
        UserData userData = this.mGenericUserData;
        ArrayMap<String, String> fieldClassificationAlgorithms2 = userData == null ? null : userData.getFieldClassificationAlgorithms();
        if (fieldClassificationAlgorithms == null && fieldClassificationAlgorithms2 == null) {
            return null;
        }
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        if (fieldClassificationAlgorithms2 != null) {
            arrayMap.putAll((ArrayMap<? extends String, ? extends String>) fieldClassificationAlgorithms2);
        }
        if (fieldClassificationAlgorithms != null) {
            arrayMap.putAll((ArrayMap<? extends String, ? extends String>) fieldClassificationAlgorithms);
        }
        return arrayMap;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public ArrayMap<String, Bundle> getFieldClassificationArgs() {
        ArrayMap<String, Bundle> fieldClassificationArgs = this.mPackageUserData.getFieldClassificationArgs();
        UserData userData = this.mGenericUserData;
        ArrayMap<String, Bundle> fieldClassificationArgs2 = userData == null ? null : userData.getFieldClassificationArgs();
        if (fieldClassificationArgs == null && fieldClassificationArgs2 == null) {
            return null;
        }
        ArrayMap<String, Bundle> arrayMap = new ArrayMap<>();
        if (fieldClassificationArgs2 != null) {
            arrayMap.putAll((ArrayMap<? extends String, ? extends Bundle>) fieldClassificationArgs2);
        }
        if (fieldClassificationArgs != null) {
            arrayMap.putAll((ArrayMap<? extends String, ? extends Bundle>) fieldClassificationArgs);
        }
        return arrayMap;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public String[] getCategoryIds() {
        return this.mCategories;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public String[] getValues() {
        return this.mValues;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "genericUserData=" + this.mGenericUserData + ", packageUserData=" + this.mPackageUserData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mGenericUserData, 0);
        parcel.writeParcelable(this.mPackageUserData, 0);
    }
}
