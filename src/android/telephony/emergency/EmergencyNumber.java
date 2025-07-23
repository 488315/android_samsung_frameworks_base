package android.telephony.emergency;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public final class EmergencyNumber implements Parcelable, Comparable<EmergencyNumber> {
    public static final Parcelable.Creator<EmergencyNumber> CREATOR;
    public static final int EMERGENCY_CALL_ROUTING_EMERGENCY = 1;
    public static final int EMERGENCY_CALL_ROUTING_NORMAL = 2;
    public static final int EMERGENCY_CALL_ROUTING_UNKNOWN = 0;
    public static final int EMERGENCY_NUMBER_SOURCE_DATABASE = 16;
    public static final int EMERGENCY_NUMBER_SOURCE_DEFAULT = 8;
    public static final int EMERGENCY_NUMBER_SOURCE_HIGH_PRIORITY = 256;
    public static final int EMERGENCY_NUMBER_SOURCE_MODEM_CONFIG = 4;
    public static final int EMERGENCY_NUMBER_SOURCE_NETWORK_SIGNALING = 1;
    public static final int EMERGENCY_NUMBER_SOURCE_OVER_DATABASE = 128;
    private static final int[] EMERGENCY_NUMBER_SOURCE_PRECEDENCE;
    private static final Set<Integer> EMERGENCY_NUMBER_SOURCE_SET;
    public static final int EMERGENCY_NUMBER_SOURCE_SIM = 2;
    public static final int EMERGENCY_NUMBER_SOURCE_TEST = 32;
    public static final int EMERGENCY_SERVICE_CATEGORY_AIEC = 64;
    public static final int EMERGENCY_SERVICE_CATEGORY_AMBULANCE = 2;
    public static final int EMERGENCY_SERVICE_CATEGORY_FIRE_BRIGADE = 4;
    public static final int EMERGENCY_SERVICE_CATEGORY_MARINE_GUARD = 8;
    public static final int EMERGENCY_SERVICE_CATEGORY_MIEC = 32;
    public static final int EMERGENCY_SERVICE_CATEGORY_MOUNTAIN_RESCUE = 16;
    public static final int EMERGENCY_SERVICE_CATEGORY_POLICE = 1;
    private static final Set<Integer> EMERGENCY_SERVICE_CATEGORY_SET;
    public static final int EMERGENCY_SERVICE_CATEGORY_UNSPECIFIED = 0;
    private static final String LOG_TAG = "EmergencyNumber";
    private final String mCountryIso;
    private final int mEmergencyCallRouting;
    private final int mEmergencyNumberSourceBitmask;
    private final int mEmergencyServiceCategoryBitmask;
    private final List<String> mEmergencyUrns;
    private final String mMnc;
    private final String mNumber;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyCallRouting {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyNumberSources {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyServiceCategories {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        HashSet hashSet = new HashSet();
        EMERGENCY_SERVICE_CATEGORY_SET = hashSet;
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(4);
        hashSet.add(8);
        hashSet.add(16);
        hashSet.add(32);
        hashSet.add(64);
        HashSet hashSet2 = new HashSet();
        EMERGENCY_NUMBER_SOURCE_SET = hashSet2;
        hashSet2.add(1);
        hashSet2.add(2);
        hashSet2.add(16);
        hashSet2.add(4);
        hashSet2.add(8);
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE = new int[]{256, 1, 2, 128, 16, 4};
        CREATOR = new Parcelable.Creator<EmergencyNumber>() { // from class: android.telephony.emergency.EmergencyNumber.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EmergencyNumber createFromParcel(Parcel parcel) {
                return new EmergencyNumber(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EmergencyNumber[] newArray(int i) {
                return new EmergencyNumber[i];
            }
        };
    }

    public EmergencyNumber(String str, String str2, String str3, int i, List<String> list, int i2, int i3) {
        this.mNumber = str;
        this.mCountryIso = str2;
        this.mMnc = str3;
        this.mEmergencyServiceCategoryBitmask = i;
        this.mEmergencyUrns = list;
        this.mEmergencyNumberSourceBitmask = i2;
        this.mEmergencyCallRouting = i3;
    }

    public EmergencyNumber(Parcel parcel) {
        this.mNumber = parcel.readString();
        this.mCountryIso = parcel.readString();
        this.mMnc = parcel.readString();
        this.mEmergencyServiceCategoryBitmask = parcel.readInt();
        this.mEmergencyUrns = parcel.createStringArrayList();
        this.mEmergencyNumberSourceBitmask = parcel.readInt();
        this.mEmergencyCallRouting = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mNumber);
        parcel.writeString(this.mCountryIso);
        parcel.writeString(this.mMnc);
        parcel.writeInt(this.mEmergencyServiceCategoryBitmask);
        parcel.writeStringList(this.mEmergencyUrns);
        parcel.writeInt(this.mEmergencyNumberSourceBitmask);
        parcel.writeInt(this.mEmergencyCallRouting);
    }

    public String getNumber() {
        return this.mNumber;
    }

    public String getCountryIso() {
        return this.mCountryIso;
    }

    public String getMnc() {
        return this.mMnc;
    }

    public int getEmergencyServiceCategoryBitmask() {
        return this.mEmergencyServiceCategoryBitmask;
    }

    public int getEmergencyServiceCategoryBitmaskInternalDial() {
        if (this.mEmergencyNumberSourceBitmask == 16) {
            return 0;
        }
        return this.mEmergencyServiceCategoryBitmask;
    }

    public List<Integer> getEmergencyServiceCategories() {
        ArrayList arrayList = new ArrayList();
        if (serviceUnspecified()) {
            arrayList.add(0);
            return arrayList;
        }
        for (Integer num : EMERGENCY_SERVICE_CATEGORY_SET) {
            if (isInEmergencyServiceCategories(num.intValue())) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }

    public List<String> getEmergencyUrns() {
        return Collections.unmodifiableList(this.mEmergencyUrns);
    }

    private boolean serviceUnspecified() {
        return this.mEmergencyServiceCategoryBitmask == 0;
    }

    public boolean isInEmergencyServiceCategories(int i) {
        if (i == 0) {
            return serviceUnspecified();
        }
        return serviceUnspecified() || (this.mEmergencyServiceCategoryBitmask & i) == i;
    }

    public int getEmergencyNumberSourceBitmask() {
        return this.mEmergencyNumberSourceBitmask;
    }

    public List<Integer> getEmergencyNumberSources() {
        ArrayList arrayList = new ArrayList();
        for (Integer num : EMERGENCY_NUMBER_SOURCE_SET) {
            if ((this.mEmergencyNumberSourceBitmask & num.intValue()) == num.intValue()) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }

    public boolean isFromSources(int i) {
        return (this.mEmergencyNumberSourceBitmask & i) == i;
    }

    public int getEmergencyCallRouting() {
        return this.mEmergencyCallRouting;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(this.mNumber);
        if (!TextUtils.isEmpty(this.mCountryIso)) {
            sb.append(", countryIso=");
            sb.append(this.mCountryIso);
        }
        if (!TextUtils.isEmpty(this.mMnc)) {
            sb.append(", mnc=");
            sb.append(this.mMnc);
        }
        sb.append(", src=");
        sb.append(sourceBitmaskToString(this.mEmergencyNumberSourceBitmask));
        if (this.mEmergencyCallRouting != 0) {
            sb.append(", routing=");
            sb.append(routingToString(this.mEmergencyCallRouting));
        }
        sb.append(", categories=");
        sb.append(categoriesToString(this.mEmergencyServiceCategoryBitmask));
        List<String> list = this.mEmergencyUrns;
        if (list != null && !list.isEmpty()) {
            sb.append(", urns=");
            sb.append((String) this.mEmergencyUrns.stream().collect(Collectors.joining(",")));
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    private String categoriesToString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 64) == 64) {
            sb.append("auto ");
        }
        if ((i & 2) == 2) {
            sb.append("ambulance ");
        }
        if ((i & 4) == 4) {
            sb.append("fire ");
        }
        if ((i & 8) == 8) {
            sb.append("marine ");
        }
        if ((i & 16) == 16) {
            sb.append("mountain ");
        }
        if ((i & 1) == 1) {
            sb.append("police ");
        }
        if ((i & 32) == 32) {
            sb.append("manual ");
        }
        return sb.toString();
    }

    private String routingToString(int i) {
        if (i == 0) {
            return "unknown";
        }
        if (i == 1) {
            return "emergency";
        }
        if (i == 2) {
            return "normal";
        }
        return " ";
    }

    private String sourceBitmaskToString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) == 1) {
            sb.append("net ");
        }
        if ((i & 2) == 2) {
            sb.append("sim ");
        }
        if ((i & 16) == 16) {
            sb.append("db ");
        }
        if ((i & 4) == 4) {
            sb.append("mdm ");
        }
        if ((i & 8) == 8) {
            sb.append("def ");
        }
        if ((i & 32) == 32) {
            sb.append("tst ");
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!EmergencyNumber.class.isInstance(obj)) {
            return false;
        }
        EmergencyNumber emergencyNumber = (EmergencyNumber) obj;
        return this.mNumber.equals(emergencyNumber.mNumber) && this.mCountryIso.equals(emergencyNumber.mCountryIso) && this.mMnc.equals(emergencyNumber.mMnc) && this.mEmergencyServiceCategoryBitmask == emergencyNumber.mEmergencyServiceCategoryBitmask && this.mEmergencyUrns.equals(emergencyNumber.mEmergencyUrns) && this.mEmergencyNumberSourceBitmask == emergencyNumber.mEmergencyNumberSourceBitmask && this.mEmergencyCallRouting == emergencyNumber.mEmergencyCallRouting;
    }

    public int hashCode() {
        return Objects.hash(this.mNumber, this.mCountryIso, this.mMnc, Integer.valueOf(this.mEmergencyServiceCategoryBitmask), this.mEmergencyUrns, Integer.valueOf(this.mEmergencyNumberSourceBitmask), Integer.valueOf(this.mEmergencyCallRouting));
    }

    private int getDisplayPriorityScore() {
        int i = isFromSources(256) ? 64 : 0;
        if (isFromSources(1)) {
            i += 32;
        }
        if (isFromSources(2)) {
            i += 16;
        }
        if (isFromSources(128)) {
            i += 8;
        }
        if (isFromSources(16)) {
            i += 4;
        }
        if (isFromSources(8)) {
            i += 2;
        }
        return isFromSources(4) ? i + 1 : i;
    }

    @Override // java.lang.Comparable
    public int compareTo(EmergencyNumber emergencyNumber) {
        if (getDisplayPriorityScore() > emergencyNumber.getDisplayPriorityScore()) {
            return -1;
        }
        if (getDisplayPriorityScore() < emergencyNumber.getDisplayPriorityScore()) {
            return 1;
        }
        if (getNumber().compareTo(emergencyNumber.getNumber()) != 0) {
            return getNumber().compareTo(emergencyNumber.getNumber());
        }
        if (getCountryIso().compareTo(emergencyNumber.getCountryIso()) != 0) {
            return getCountryIso().compareTo(emergencyNumber.getCountryIso());
        }
        if (getMnc().compareTo(emergencyNumber.getMnc()) != 0) {
            return getMnc().compareTo(emergencyNumber.getMnc());
        }
        if (getEmergencyServiceCategoryBitmask() != emergencyNumber.getEmergencyServiceCategoryBitmask()) {
            return getEmergencyServiceCategoryBitmask() > emergencyNumber.getEmergencyServiceCategoryBitmask() ? -1 : 1;
        }
        if (getEmergencyUrns().toString().compareTo(emergencyNumber.getEmergencyUrns().toString()) != 0) {
            return getEmergencyUrns().toString().compareTo(emergencyNumber.getEmergencyUrns().toString());
        }
        if (getEmergencyCallRouting() != emergencyNumber.getEmergencyCallRouting()) {
            return getEmergencyCallRouting() > emergencyNumber.getEmergencyCallRouting() ? -1 : 1;
        }
        return 0;
    }

    public static void mergeSameNumbersInEmergencyNumberList(List<EmergencyNumber> list) {
        mergeSameNumbersInEmergencyNumberList(list, false);
    }

    public static void mergeSameNumbersInEmergencyNumberList(List<EmergencyNumber> list, boolean z) {
        if (list == null) {
            return;
        }
        HashSet hashSet = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            for (int i2 = 0; i2 < i; i2++) {
                if (areSameEmergencyNumbers(list.get(i), list.get(i2), z)) {
                    list.set(i, mergeSameEmergencyNumbers(list.get(i), list.get(i2), z));
                    hashSet.add(Integer.valueOf(i2));
                }
            }
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            if (hashSet.contains(Integer.valueOf(size))) {
                list.remove(size);
            }
        }
        Collections.sort(list);
    }

    public static boolean areSameEmergencyNumbers(EmergencyNumber emergencyNumber, EmergencyNumber emergencyNumber2, boolean z) {
        if (emergencyNumber.getNumber().equals(emergencyNumber2.getNumber()) && emergencyNumber.getCountryIso().equals(emergencyNumber2.getCountryIso()) && emergencyNumber.getMnc().equals(emergencyNumber2.getMnc())) {
            return (z || (emergencyNumber.getEmergencyServiceCategoryBitmask() == emergencyNumber2.getEmergencyServiceCategoryBitmask() && emergencyNumber.getEmergencyUrns().equals(emergencyNumber2.getEmergencyUrns()))) && !(emergencyNumber.isFromSources(32) ^ emergencyNumber2.isFromSources(32));
        }
        return false;
    }

    public static EmergencyNumber mergeSameEmergencyNumbers(EmergencyNumber emergencyNumber, EmergencyNumber emergencyNumber2) {
        if (!areSameEmergencyNumbers(emergencyNumber, emergencyNumber2, false)) {
            return null;
        }
        int emergencyCallRouting = emergencyNumber.getEmergencyCallRouting();
        if (emergencyNumber2.isFromSources(16)) {
            emergencyCallRouting = emergencyNumber2.getEmergencyCallRouting();
        }
        return new EmergencyNumber(emergencyNumber.getNumber(), emergencyNumber.getCountryIso(), emergencyNumber.getMnc(), emergencyNumber.getEmergencyServiceCategoryBitmask(), emergencyNumber.getEmergencyUrns(), emergencyNumber.getEmergencyNumberSourceBitmask() | emergencyNumber2.getEmergencyNumberSourceBitmask(), emergencyCallRouting);
    }

    private static List<String> mergeEmergencyUrns(List<String> list, List<String> list2) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list);
        for (String str : list2) {
            if (!list.contains(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private static void fillServiceCategoryAndUrns(EmergencyNumber emergencyNumber, SparseIntArray sparseIntArray, SparseArray<List<String>> sparseArray) {
        int emergencyNumberSourceBitmask = emergencyNumber.getEmergencyNumberSourceBitmask();
        for (int i : EMERGENCY_NUMBER_SOURCE_PRECEDENCE) {
            Integer valueOf = Integer.valueOf(i);
            valueOf.getClass();
            int i2 = emergencyNumberSourceBitmask & i;
            valueOf.getClass();
            if (i2 == i) {
                if (!emergencyNumber.isInEmergencyServiceCategories(0)) {
                    valueOf.getClass();
                    sparseIntArray.put(i, emergencyNumber.getEmergencyServiceCategoryBitmask());
                }
                valueOf.getClass();
                sparseArray.put(i, emergencyNumber.getEmergencyUrns());
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static EmergencyNumber mergeSameEmergencyNumbers(EmergencyNumber emergencyNumber, EmergencyNumber emergencyNumber2, boolean z) {
        if (!z) {
            return mergeSameEmergencyNumbers(emergencyNumber, emergencyNumber2);
        }
        int emergencyCallRouting = emergencyNumber.getEmergencyCallRouting();
        int emergencyServiceCategoryBitmask = emergencyNumber.getEmergencyServiceCategoryBitmask();
        List arrayList = new ArrayList();
        SparseIntArray sparseIntArray = new SparseIntArray(2);
        SparseArray sparseArray = new SparseArray(2);
        fillServiceCategoryAndUrns(emergencyNumber, sparseIntArray, sparseArray);
        fillServiceCategoryAndUrns(emergencyNumber2, sparseIntArray, sparseArray);
        if (emergencyNumber2.isFromSources(16)) {
            emergencyCallRouting = emergencyNumber2.getEmergencyCallRouting();
        }
        int i = emergencyCallRouting;
        int[] iArr = EMERGENCY_NUMBER_SOURCE_PRECEDENCE;
        int length = iArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            int i3 = iArr[i2];
            if (sparseIntArray.indexOfKey(i3) >= 0) {
                emergencyServiceCategoryBitmask = sparseIntArray.get(i3);
                break;
            }
            i2++;
        }
        int i4 = emergencyServiceCategoryBitmask;
        List list = arrayList;
        for (int i5 : EMERGENCY_NUMBER_SOURCE_PRECEDENCE) {
            if (sparseArray.contains(i5)) {
                list = mergeEmergencyUrns(list, (List) sparseArray.get(i5));
            }
        }
        return new EmergencyNumber(emergencyNumber.getNumber(), emergencyNumber.getCountryIso(), emergencyNumber.getMnc(), i4, list, emergencyNumber.getEmergencyNumberSourceBitmask() | emergencyNumber2.getEmergencyNumberSourceBitmask(), i);
    }

    public static boolean validateEmergencyNumberAddress(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!PhoneNumberUtils.isDialable(c)) {
                return false;
            }
        }
        return true;
    }
}
