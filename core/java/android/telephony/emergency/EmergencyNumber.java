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
    private static final Set<Integer> EMERGENCY_SERVICE_CATEGORY_SET = new HashSet();
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
    public @interface EmergencyCallRouting {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyNumberSources {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyServiceCategories {}

    static {
        EMERGENCY_SERVICE_CATEGORY_SET.add(1);
        EMERGENCY_SERVICE_CATEGORY_SET.add(2);
        EMERGENCY_SERVICE_CATEGORY_SET.add(4);
        EMERGENCY_SERVICE_CATEGORY_SET.add(8);
        EMERGENCY_SERVICE_CATEGORY_SET.add(16);
        EMERGENCY_SERVICE_CATEGORY_SET.add(32);
        EMERGENCY_SERVICE_CATEGORY_SET.add(64);
        EMERGENCY_NUMBER_SOURCE_SET = new HashSet();
        EMERGENCY_NUMBER_SOURCE_SET.add(1);
        EMERGENCY_NUMBER_SOURCE_SET.add(2);
        EMERGENCY_NUMBER_SOURCE_SET.add(16);
        EMERGENCY_NUMBER_SOURCE_SET.add(4);
        EMERGENCY_NUMBER_SOURCE_SET.add(8);
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE = new int[6];
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[0] = 256;
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[1] = 1;
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[2] = 2;
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[3] = 128;
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[4] = 16;
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[5] = 4;
        CREATOR =
                new Parcelable.Creator<
                        EmergencyNumber>() { // from class:
                                             // android.telephony.emergency.EmergencyNumber.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public EmergencyNumber createFromParcel(Parcel in) {
                        return new EmergencyNumber(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public EmergencyNumber[] newArray(int size) {
                        return new EmergencyNumber[size];
                    }
                };
    }

    public EmergencyNumber(
            String number,
            String countryIso,
            String mnc,
            int emergencyServiceCategories,
            List<String> emergencyUrns,
            int emergencyNumberSources,
            int emergencyCallRouting) {
        this.mNumber = number;
        this.mCountryIso = countryIso;
        this.mMnc = mnc;
        this.mEmergencyServiceCategoryBitmask = emergencyServiceCategories;
        this.mEmergencyUrns = emergencyUrns;
        this.mEmergencyNumberSourceBitmask = emergencyNumberSources;
        this.mEmergencyCallRouting = emergencyCallRouting;
    }

    public EmergencyNumber(Parcel source) {
        this.mNumber = source.readString();
        this.mCountryIso = source.readString();
        this.mMnc = source.readString();
        this.mEmergencyServiceCategoryBitmask = source.readInt();
        this.mEmergencyUrns = source.createStringArrayList();
        this.mEmergencyNumberSourceBitmask = source.readInt();
        this.mEmergencyCallRouting = source.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mNumber);
        dest.writeString(this.mCountryIso);
        dest.writeString(this.mMnc);
        dest.writeInt(this.mEmergencyServiceCategoryBitmask);
        dest.writeStringList(this.mEmergencyUrns);
        dest.writeInt(this.mEmergencyNumberSourceBitmask);
        dest.writeInt(this.mEmergencyCallRouting);
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
        List<Integer> categories = new ArrayList<>();
        if (serviceUnspecified()) {
            categories.add(0);
            return categories;
        }
        for (Integer category : EMERGENCY_SERVICE_CATEGORY_SET) {
            if (isInEmergencyServiceCategories(category.intValue())) {
                categories.add(category);
            }
        }
        return categories;
    }

    public List<String> getEmergencyUrns() {
        return Collections.unmodifiableList(this.mEmergencyUrns);
    }

    private boolean serviceUnspecified() {
        return this.mEmergencyServiceCategoryBitmask == 0;
    }

    public boolean isInEmergencyServiceCategories(int categories) {
        if (categories == 0) {
            return serviceUnspecified();
        }
        return serviceUnspecified()
                || (this.mEmergencyServiceCategoryBitmask & categories) == categories;
    }

    public int getEmergencyNumberSourceBitmask() {
        return this.mEmergencyNumberSourceBitmask;
    }

    public List<Integer> getEmergencyNumberSources() {
        List<Integer> sources = new ArrayList<>();
        for (Integer source : EMERGENCY_NUMBER_SOURCE_SET) {
            if ((this.mEmergencyNumberSourceBitmask & source.intValue()) == source.intValue()) {
                sources.add(source);
            }
        }
        return sources;
    }

    public boolean isFromSources(int sources) {
        return (this.mEmergencyNumberSourceBitmask & sources) == sources;
    }

    public int getEmergencyCallRouting() {
        return this.mEmergencyCallRouting;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(NavigationBarInflaterView.SIZE_MOD_START).append(this.mNumber);
        if (!TextUtils.isEmpty(this.mCountryIso)) {
            sb.append(", countryIso=").append(this.mCountryIso);
        }
        if (!TextUtils.isEmpty(this.mMnc)) {
            sb.append(", mnc=").append(this.mMnc);
        }
        sb.append(", src=").append(sourceBitmaskToString(this.mEmergencyNumberSourceBitmask));
        if (this.mEmergencyCallRouting != 0) {
            sb.append(", routing=").append(routingToString(this.mEmergencyCallRouting));
        }
        sb.append(", categories=")
                .append(categoriesToString(this.mEmergencyServiceCategoryBitmask));
        if (this.mEmergencyUrns != null && !this.mEmergencyUrns.isEmpty()) {
            sb.append(", urns=")
                    .append((String) this.mEmergencyUrns.stream().collect(Collectors.joining(",")));
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    private String categoriesToString(int categories) {
        StringBuilder sb = new StringBuilder();
        if ((categories & 64) == 64) {
            sb.append("auto ");
        }
        if ((categories & 2) == 2) {
            sb.append("ambulance ");
        }
        if ((categories & 4) == 4) {
            sb.append("fire ");
        }
        if ((categories & 8) == 8) {
            sb.append("marine ");
        }
        if ((categories & 16) == 16) {
            sb.append("mountain ");
        }
        if ((categories & 1) == 1) {
            sb.append("police ");
        }
        if ((categories & 32) == 32) {
            sb.append("manual ");
        }
        return sb.toString();
    }

    private String routingToString(int routing) {
        switch (routing) {
            case 0:
                return "unknown";
            case 1:
                return "emergency";
            case 2:
                return "normal";
            default:
                return " ";
        }
    }

    private String sourceBitmaskToString(int sourceBitmask) {
        StringBuilder sb = new StringBuilder();
        if ((sourceBitmask & 1) == 1) {
            sb.append("net ");
        }
        if ((sourceBitmask & 2) == 2) {
            sb.append("sim ");
        }
        if ((sourceBitmask & 16) == 16) {
            sb.append("db ");
        }
        if ((sourceBitmask & 4) == 4) {
            sb.append("mdm ");
        }
        if ((sourceBitmask & 8) == 8) {
            sb.append("def ");
        }
        if ((sourceBitmask & 32) == 32) {
            sb.append("tst ");
        }
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (!EmergencyNumber.class.isInstance(o)) {
            return false;
        }
        EmergencyNumber other = (EmergencyNumber) o;
        return this.mNumber.equals(other.mNumber)
                && this.mCountryIso.equals(other.mCountryIso)
                && this.mMnc.equals(other.mMnc)
                && this.mEmergencyServiceCategoryBitmask == other.mEmergencyServiceCategoryBitmask
                && this.mEmergencyUrns.equals(other.mEmergencyUrns)
                && this.mEmergencyNumberSourceBitmask == other.mEmergencyNumberSourceBitmask
                && this.mEmergencyCallRouting == other.mEmergencyCallRouting;
    }

    public int hashCode() {
        return Objects.hash(
                this.mNumber,
                this.mCountryIso,
                this.mMnc,
                Integer.valueOf(this.mEmergencyServiceCategoryBitmask),
                this.mEmergencyUrns,
                Integer.valueOf(this.mEmergencyNumberSourceBitmask),
                Integer.valueOf(this.mEmergencyCallRouting));
    }

    private int getDisplayPriorityScore() {
        int score = 0;
        if (isFromSources(256)) {
            score = 0 + 64;
        }
        if (isFromSources(1)) {
            score += 32;
        }
        if (isFromSources(2)) {
            score += 16;
        }
        if (isFromSources(128)) {
            score += 8;
        }
        if (isFromSources(16)) {
            score += 4;
        }
        if (isFromSources(8)) {
            score += 2;
        }
        if (isFromSources(4)) {
            return score + 1;
        }
        return score;
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
        if (getEmergencyServiceCategoryBitmask()
                != emergencyNumber.getEmergencyServiceCategoryBitmask()) {
            return getEmergencyServiceCategoryBitmask()
                            > emergencyNumber.getEmergencyServiceCategoryBitmask()
                    ? -1
                    : 1;
        }
        if (getEmergencyUrns().toString().compareTo(emergencyNumber.getEmergencyUrns().toString())
                != 0) {
            return getEmergencyUrns()
                    .toString()
                    .compareTo(emergencyNumber.getEmergencyUrns().toString());
        }
        if (getEmergencyCallRouting() != emergencyNumber.getEmergencyCallRouting()) {
            return getEmergencyCallRouting() > emergencyNumber.getEmergencyCallRouting() ? -1 : 1;
        }
        return 0;
    }

    public static void mergeSameNumbersInEmergencyNumberList(
            List<EmergencyNumber> emergencyNumberList) {
        mergeSameNumbersInEmergencyNumberList(emergencyNumberList, false);
    }

    public static void mergeSameNumbersInEmergencyNumberList(
            List<EmergencyNumber> emergencyNumberList, boolean mergeServiceCategoriesAndUrns) {
        if (emergencyNumberList == null) {
            return;
        }
        Set<Integer> duplicatedEmergencyNumberPosition = new HashSet<>();
        for (int i = 0; i < emergencyNumberList.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (areSameEmergencyNumbers(
                        emergencyNumberList.get(i),
                        emergencyNumberList.get(j),
                        mergeServiceCategoriesAndUrns)) {
                    emergencyNumberList.set(
                            i,
                            mergeSameEmergencyNumbers(
                                    emergencyNumberList.get(i),
                                    emergencyNumberList.get(j),
                                    mergeServiceCategoriesAndUrns));
                    duplicatedEmergencyNumberPosition.add(Integer.valueOf(j));
                }
            }
        }
        int i2 = emergencyNumberList.size();
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            if (duplicatedEmergencyNumberPosition.contains(Integer.valueOf(i3))) {
                emergencyNumberList.remove(i3);
            }
        }
        Collections.sort(emergencyNumberList);
    }

    public static boolean areSameEmergencyNumbers(
            EmergencyNumber first, EmergencyNumber second, boolean ignoreServiceCategoryAndUrns) {
        if (!first.getNumber().equals(second.getNumber())
                || !first.getCountryIso().equals(second.getCountryIso())
                || !first.getMnc().equals(second.getMnc())) {
            return false;
        }
        if (ignoreServiceCategoryAndUrns
                || (first.getEmergencyServiceCategoryBitmask()
                                == second.getEmergencyServiceCategoryBitmask()
                        && first.getEmergencyUrns().equals(second.getEmergencyUrns()))) {
            return !(second.isFromSources(32) ^ first.isFromSources(32));
        }
        return false;
    }

    public static EmergencyNumber mergeSameEmergencyNumbers(
            EmergencyNumber first, EmergencyNumber second) {
        if (areSameEmergencyNumbers(first, second, false)) {
            int routing = first.getEmergencyCallRouting();
            if (second.isFromSources(16)) {
                routing = second.getEmergencyCallRouting();
            }
            return new EmergencyNumber(
                    first.getNumber(),
                    first.getCountryIso(),
                    first.getMnc(),
                    first.getEmergencyServiceCategoryBitmask(),
                    first.getEmergencyUrns(),
                    second.getEmergencyNumberSourceBitmask()
                            | first.getEmergencyNumberSourceBitmask(),
                    routing);
        }
        return null;
    }

    private static List<String> mergeEmergencyUrns(
            List<String> firstEmergencyUrns, List<String> secondEmergencyUrns) {
        List<String> mergedUrns = new ArrayList<>();
        mergedUrns.addAll(firstEmergencyUrns);
        for (String urn : secondEmergencyUrns) {
            if (!firstEmergencyUrns.contains(urn)) {
                mergedUrns.add(urn);
            }
        }
        return mergedUrns;
    }

    private static void fillServiceCategoryAndUrns(
            EmergencyNumber num,
            SparseIntArray serviceCategoryArray,
            SparseArray<List<String>> urnsArray) {
        int numberSrc = num.getEmergencyNumberSourceBitmask();
        for (int i : EMERGENCY_NUMBER_SOURCE_PRECEDENCE) {
            Integer source = Integer.valueOf(i);
            if ((source.intValue() & numberSrc) == source.intValue()) {
                if (!num.isInEmergencyServiceCategories(0)) {
                    serviceCategoryArray.put(
                            source.intValue(), num.getEmergencyServiceCategoryBitmask());
                }
                urnsArray.put(source.intValue(), num.getEmergencyUrns());
                return;
            }
        }
    }

    public static EmergencyNumber mergeSameEmergencyNumbers(
            EmergencyNumber first, EmergencyNumber second, boolean mergeServiceCategoriesAndUrns) {
        if (!mergeServiceCategoriesAndUrns) {
            return mergeSameEmergencyNumbers(first, second);
        }
        int routing = first.getEmergencyCallRouting();
        int serviceCategory = first.getEmergencyServiceCategoryBitmask();
        List<String> mergedEmergencyUrns = new ArrayList<>();
        SparseIntArray serviceCategoryArray = new SparseIntArray(2);
        SparseArray<List<String>> urnsArray = new SparseArray<>(2);
        fillServiceCategoryAndUrns(first, serviceCategoryArray, urnsArray);
        fillServiceCategoryAndUrns(second, serviceCategoryArray, urnsArray);
        if (second.isFromSources(16)) {
            routing = second.getEmergencyCallRouting();
        }
        int[] iArr = EMERGENCY_NUMBER_SOURCE_PRECEDENCE;
        int length = iArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int sourceOfCategory = iArr[i];
            if (serviceCategoryArray.indexOfKey(sourceOfCategory) < 0) {
                i++;
            } else {
                serviceCategory = serviceCategoryArray.get(sourceOfCategory);
                break;
            }
        }
        for (int sourceOfUrn : EMERGENCY_NUMBER_SOURCE_PRECEDENCE) {
            if (urnsArray.contains(sourceOfUrn)) {
                mergedEmergencyUrns =
                        mergeEmergencyUrns(mergedEmergencyUrns, urnsArray.get(sourceOfUrn));
            }
        }
        return new EmergencyNumber(
                first.getNumber(),
                first.getCountryIso(),
                first.getMnc(),
                serviceCategory,
                mergedEmergencyUrns,
                first.getEmergencyNumberSourceBitmask() | second.getEmergencyNumberSourceBitmask(),
                routing);
    }

    public static boolean validateEmergencyNumberAddress(String address) {
        if (address == null) {
            return false;
        }
        for (char c : address.toCharArray()) {
            if (!PhoneNumberUtils.isDialable(c)) {
                return false;
            }
        }
        return true;
    }
}
