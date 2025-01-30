package com.android.server.am.mars.database;

import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.am.mars.MARsDebugConfig;
import java.com.android.server.p028am.mars.database.MARsListManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MARsVersionManager {
    public long mApplicationSizeIncreased;
    public ArrayList mExcludeTargetList;
    public ArrayList mIsCurrentImportantList;
    public ArrayMap mIsCurrentImportantMap;
    public ArrayList mMARsSettingsInfoList;
    public long mNotifiUpdatedCount;
    public ArrayList mRestrictionList;
    public long mTrafficAmount;
    public long mTrafficInterval;
    public long mUnusedTime;
    public static final String[][] mMARsSettingsInfoDefault = {new String[]{"marsversion", "1020230413"}, new String[]{"skipratio", "90"}, new String[]{"killthreshold", "100"}, new String[]{"restrictionthreshold", "100"}, new String[]{"unused_app_period_days", "32"}, new String[]{"restriction_flag", "255"}};
    public static final String[][] mPolicyInfoDefault = {new String[]{"1", "1", "13", "354255120", "12", "10", "10", "337379600"}, new String[]{"2", "0", "21", "270272648", "10", "10", "5", "270272640"}, new String[]{"4", "0", "25", "270272648", "26", "5", "1", "270272640"}, new String[]{"8", "1", "26", "0", "2", "5", "0", "0"}};
    public static final String[] mAutoRunParameterDefault = {"2", "15", "300", "1", "1"};
    public static final String[][] mCurrentImportantDefault = {new String[]{"0", "8000"}, new String[]{"1", "18284"}, new String[]{"2", "65529"}, new String[]{"4", "24032"}, new String[]{"5", "18284"}, new String[]{"6", "18284"}, new String[]{"7", "20251"}, new String[]{"8", "20264"}, new String[]{"9", "24446"}};
    public static ArrayList mPolicyInfoList = new ArrayList();

    public abstract class MARsVersionManagerHolder {
        public static final MARsVersionManager INSTANCE = new MARsVersionManager();
    }

    public String convertPolicyNumToName(int i) {
        if (i == 0) {
            return "force";
        }
        if (1 == i) {
            return "applocker";
        }
        if (2 == i) {
            return "autorun";
        }
        if (4 == i) {
            return "freecesspolicy";
        }
        if (5 == i) {
            return "udspolicy";
        }
        if (6 == i) {
            return "sbikepolicy";
        }
        if (8 == i) {
            return "disablepolicy";
        }
        return null;
    }

    public final String convertRestrictionTypeToName(int i) {
        if (i == 1 || i == 2 || i == 7) {
            return "CHN app";
        }
        if (i == 3 || i == 4) {
            return "Essential intent";
        }
        if (i == 14) {
            return "Rogue app";
        }
        if (i == 15) {
            return "Foreground Service";
        }
        return null;
    }

    public final boolean isRestrictionTypeAllowed(int i) {
        return i == 1 || i == 3 || i == 7;
    }

    public MARsVersionManager() {
        this.mMARsSettingsInfoList = new ArrayList();
        this.mExcludeTargetList = new ArrayList();
        this.mIsCurrentImportantList = new ArrayList();
        this.mIsCurrentImportantMap = new ArrayMap();
        this.mRestrictionList = new ArrayList();
        this.mTrafficInterval = 2000L;
        this.mUnusedTime = 900000L;
        this.mTrafficAmount = 300L;
        this.mNotifiUpdatedCount = 0L;
        this.mApplicationSizeIncreased = 0L;
    }

    public static MARsVersionManager getInstance() {
        return MARsVersionManagerHolder.INSTANCE;
    }

    public class MARsSettingsInfo {
        public String key;
        public String value;

        public MARsSettingsInfo(String str, String str2) {
            this.key = str;
            this.value = str2;
        }
    }

    public final class PolicyInfo {
        public final int action;
        public int bigdataRestriction;
        public int enabled;
        public long firstTriggerTime;
        public final String name;
        public final int num;
        public long repeatTriggerTime;
        public final int restriction;
        public final int targetCategory;

        public PolicyInfo(String str, int i, int i2, int i3, int i4, int i5, long j, long j2, int i6) {
            this.name = str;
            this.num = i;
            this.enabled = i2;
            this.targetCategory = i3;
            this.restriction = i4;
            this.action = i5;
            this.firstTriggerTime = j;
            this.repeatTriggerTime = j2;
            this.bigdataRestriction = i6;
        }

        public String toString() {
            return this.name + ", num=" + this.num + ", enabled=" + this.enabled + ", targetCategory=" + this.targetCategory + ", restriction=" + this.restriction + ", action=" + this.action + ", firstTriggerTime=" + this.firstTriggerTime + ", repeatTriggerTime = " + this.repeatTriggerTime;
        }

        public String getName() {
            return this.name;
        }

        public int getNum() {
            return this.num;
        }

        public int getEnabled() {
            return this.enabled;
        }

        public int getTargetCategory() {
            return this.targetCategory;
        }

        public int getRestriction() {
            return this.restriction;
        }

        public int getAction() {
            return this.action;
        }
    }

    public class AdjustTargetExcludePackage {
        public int condition;
        public String packageName;
        public String pkgNameMatchType;
        public int policyNum;

        public AdjustTargetExcludePackage(int i, int i2, String str, String str2) {
            this.policyNum = i;
            this.condition = i2;
            this.pkgNameMatchType = str;
            this.packageName = str2;
        }

        public String toString() {
            return "AdjustTargetExcludePackage: policyNum=" + this.policyNum + ", condition=" + this.condition + ", pkgNameMatchType=" + this.pkgNameMatchType + ", packageName=" + this.packageName;
        }
    }

    public class AdjustTargetCurrentImportant {
        public int importantValue;
        public int policyNum;

        public AdjustTargetCurrentImportant(int i, int i2) {
            this.policyNum = i;
            this.importantValue = i2;
        }

        public String toString() {
            return "AdjustTargetCurrentImportant: policyNum=" + this.policyNum + ", importantValue=" + this.importantValue;
        }
    }

    public class AdjustRestriction {
        public String action;
        public String actionMatchType;
        public String callee;
        public String caller;
        public String isAllowed;
        public int restrictionType;

        public AdjustRestriction(int i, String str, String str2, String str3, String str4, String str5) {
            this.restrictionType = i;
            this.isAllowed = str;
            this.callee = str2;
            this.caller = str3;
            this.actionMatchType = str4;
            this.action = str5;
        }

        public String toString() {
            return "AdjustRestriction: restrictionType=" + this.restrictionType + ", isAllowed=" + this.isAllowed + ", callee=" + this.callee + ", caller=" + this.caller + ", actionMatchType=" + this.actionMatchType + ", action=" + this.action;
        }
    }

    public void setMARsSettingsInfoList(ArrayList arrayList) {
        this.mMARsSettingsInfoList = arrayList;
    }

    public void setPolicy(ArrayList arrayList) {
        mPolicyInfoList = arrayList;
    }

    public void setAdjustTargetExcludePackage(ArrayList arrayList) {
        this.mExcludeTargetList = arrayList;
    }

    public void setAdjustTargetCurrentImportant(ArrayList arrayList) {
        this.mIsCurrentImportantList = arrayList;
        convertListToMap(arrayList);
    }

    public final void convertListToMap(ArrayList arrayList) {
        ArrayMap arrayMap = this.mIsCurrentImportantMap;
        if (arrayMap != null) {
            arrayMap.clear();
            for (int i = 0; i < arrayList.size(); i++) {
                this.mIsCurrentImportantMap.put(Integer.valueOf(((AdjustTargetCurrentImportant) arrayList.get(i)).policyNum), Integer.valueOf(((AdjustTargetCurrentImportant) arrayList.get(i)).importantValue));
            }
            return;
        }
        Slog.e("MARsVersionManager", "mIsCurrentImportantMap is null!");
    }

    public void setAdjustRestriction(ArrayList arrayList) {
        this.mRestrictionList = arrayList;
    }

    public void getMARsSettingsInfoFromDefaultValue() {
        ArrayList arrayList = new ArrayList();
        for (String[] strArr : mMARsSettingsInfoDefault) {
            arrayList.add(new MARsSettingsInfo(strArr[0], strArr[1]));
        }
        setMARsSettingsInfoList(arrayList);
    }

    public String getMARsLocalVersionFromDefaultValue() {
        String str = null;
        for (String[] strArr : mMARsSettingsInfoDefault) {
            if ("marsversion".equals(strArr[0])) {
                str = strArr[1];
            }
        }
        return str;
    }

    public int getRestrictionFlag() {
        int i = 0;
        for (String[] strArr : mMARsSettingsInfoDefault) {
            String str = strArr[0];
            String str2 = strArr[1];
            try {
                if ("restriction_flag".equals(str)) {
                    i = Integer.parseInt(str2);
                }
            } catch (Exception e) {
                Slog.e("MARsVersionManager", "Exception getRestrictionFlag!" + e);
            }
        }
        return i;
    }

    public void getPolicyFromDefaultValue() {
        ArrayList arrayList = new ArrayList();
        String[][] strArr = mPolicyInfoDefault;
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String[] strArr2 = strArr[i];
            int parseInt = Integer.parseInt(strArr2[0]);
            arrayList.add(new PolicyInfo(convertPolicyNumToName(parseInt), parseInt, Integer.parseInt(strArr2[1]), Integer.parseInt(strArr2[2]), Integer.parseInt(strArr2[3]), Integer.parseInt(strArr2[4]), Long.parseLong(strArr2[5]), Long.parseLong(strArr2[6]), Integer.parseInt(strArr2[7])));
            i++;
            length = length;
            strArr = strArr;
        }
        setPolicy(arrayList);
    }

    public void getExcludeTargetFromDefaultValue() {
        setAdjustTargetExcludePackage(MARsListManager.getInstance().getExcludePackageDefault());
    }

    public void getIsCurrentImportantFromDefaultValue() {
        ArrayList arrayList = new ArrayList();
        for (String[] strArr : mCurrentImportantDefault) {
            arrayList.add(new AdjustTargetCurrentImportant(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1])));
        }
        setAdjustTargetCurrentImportant(arrayList);
    }

    public void getAdjustRestrictionFromDefaultValue() {
        setAdjustRestriction(MARsListManager.getInstance().getAdjustRestrictionDefault());
    }

    public final boolean isActionMatch(AdjustRestriction adjustRestriction, String str) {
        if (adjustRestriction.actionMatchType.equals("equals")) {
            return str.equals(adjustRestriction.action);
        }
        if (adjustRestriction.actionMatchType.equals("equalsIgnoreCase")) {
            return str.equalsIgnoreCase(adjustRestriction.action);
        }
        if (adjustRestriction.actionMatchType.equals("contains")) {
            return str.contains(adjustRestriction.action);
        }
        if (adjustRestriction.actionMatchType.equals("startsWith")) {
            return str.startsWith(adjustRestriction.action);
        }
        if (adjustRestriction.actionMatchType.equals("endsWith")) {
            return str.endsWith(adjustRestriction.action);
        }
        return false;
    }

    public final boolean isAdjustRestrictionMatchInternal(AdjustRestriction adjustRestriction, String str, String str2, String str3) {
        String str4 = adjustRestriction.callee;
        if ((str4 == null || str == null || !str4.equals(str)) && adjustRestriction.callee != null) {
            return false;
        }
        String str5 = adjustRestriction.caller;
        if ((str5 == null || str2 == null || !str5.equals(str2)) && adjustRestriction.caller != null) {
            return false;
        }
        return !(adjustRestriction.action == null || str3 == null || !isActionMatch(adjustRestriction, str3)) || adjustRestriction.action == null;
    }

    public boolean isAdjustRestrictionMatch(int i, String str, String str2, String str3) {
        Iterator it = this.mRestrictionList.iterator();
        while (it.hasNext()) {
            AdjustRestriction adjustRestriction = (AdjustRestriction) it.next();
            if (i == adjustRestriction.restrictionType && isAdjustRestrictionMatchInternal(adjustRestriction, str, str2, str3)) {
                if (!MARsDebugConfig.DEBUG_DATABASE) {
                    return true;
                }
                Slog.d("MARsVersionManager", " restrictionType = " + convertRestrictionTypeToName(i) + " isAllowed = " + isRestrictionTypeAllowed(i) + " callee = " + str + " caller = " + str2 + " action = " + str3);
                return true;
            }
        }
        return false;
    }

    public static String toNormalText(String str) {
        return (str == null || !str.startsWith("##")) ? str : new String(Base64.getDecoder().decode(str.substring(2)));
    }
}
