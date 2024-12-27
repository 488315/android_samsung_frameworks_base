package com.android.server.am.mars.database;

import android.util.ArrayMap;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Base64;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MARsVersionManager {
    public ArrayList mExcludeTargetList;
    public ArrayMap mIsCurrentImportantMap;
    public ArrayList mRestrictionList;
    public static final String[][] mMARsSettingsInfoDefault = {
        new String[] {"marsversion", "1020230413"},
        new String[] {"skipratio", "90"},
        new String[] {"killthreshold", "100"},
        new String[] {"restrictionthreshold", "100"},
        new String[] {"unused_app_period_days", "32"},
        new String[] {"restriction_flag", "255"}
    };
    public static final String[][] mPolicyInfoDefault = {
        new String[] {"1", "1", "13", "354255120", "12", "10", "10", "337379600"},
        new String[] {"2", "0", "21", "270272648", "10", "10", "5", "270272640"},
        new String[] {"4", "0", "25", "270272648", "26", "5", "1", "270272640"},
        new String[] {"8", "1", "26", "0", "2", "5", "0", "0"}
    };
    public static final String[][] mCurrentImportantDefault = {
        new String[] {"0", "8000"},
        new String[] {"1", "18284"},
        new String[] {"2", "65529"},
        new String[] {"4", "24032"},
        new String[] {"5", "18284"},
        new String[] {"6", "18284"},
        new String[] {"7", "20251"},
        new String[] {"8", "20264"},
        new String[] {"9", "24446"}
    };
    public static ArrayList mPolicyInfoList = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdjustRestriction {
        public final String action;
        public final String actionMatchType;
        public final String callee;
        public final String caller;
        public final String isAllowed;
        public final int restrictionType;

        public AdjustRestriction(
                String str, String str2, String str3, String str4, String str5, int i) {
            this.restrictionType = i;
            this.isAllowed = str;
            this.callee = str2;
            this.caller = str3;
            this.actionMatchType = str4;
            this.action = str5;
        }

        public final String toString() {
            return "AdjustRestriction: restrictionType="
                    + this.restrictionType
                    + ", isAllowed="
                    + this.isAllowed
                    + ", callee="
                    + this.callee
                    + ", caller="
                    + this.caller
                    + ", actionMatchType="
                    + this.actionMatchType
                    + ", action="
                    + this.action;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdjustTargetCurrentImportant {
        public final int importantValue;
        public final int policyNum;

        public AdjustTargetCurrentImportant(int i, int i2) {
            this.policyNum = i;
            this.importantValue = i2;
        }

        public final String toString() {
            return "AdjustTargetCurrentImportant: policyNum="
                    + this.policyNum
                    + ", importantValue="
                    + this.importantValue;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdjustTargetExcludePackage {
        public final int condition;
        public final String packageName;
        public final String pkgNameMatchType;
        public final int policyNum;

        public AdjustTargetExcludePackage(int i, int i2, String str, String str2) {
            this.policyNum = i;
            this.condition = i2;
            this.pkgNameMatchType = str;
            this.packageName = str2;
        }

        public final String toString() {
            return "AdjustTargetExcludePackage: policyNum="
                    + this.policyNum
                    + ", condition="
                    + this.condition
                    + ", pkgNameMatchType="
                    + this.pkgNameMatchType
                    + ", packageName="
                    + this.packageName;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MARsSettingsInfo {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class MARsVersionManagerHolder {
        public static final MARsVersionManager INSTANCE;

        static {
            MARsVersionManager mARsVersionManager = new MARsVersionManager();
            new ArrayList();
            mARsVersionManager.mExcludeTargetList = new ArrayList();
            new ArrayList();
            mARsVersionManager.mIsCurrentImportantMap = new ArrayMap();
            mARsVersionManager.mRestrictionList = new ArrayList();
            INSTANCE = mARsVersionManager;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PolicyInfo {
        public final int action;
        public final int enabled;
        public final long firstTriggerTime;
        public final String name;
        public final int num;
        public final long repeatTriggerTime;
        public final int restriction;
        public final int targetCategory;

        public PolicyInfo(String str, int i, int i2, int i3, int i4, int i5, long j, long j2) {
            this.name = str;
            this.num = i;
            this.enabled = i2;
            this.targetCategory = i3;
            this.restriction = i4;
            this.action = i5;
            this.firstTriggerTime = j;
            this.repeatTriggerTime = j2;
        }

        public final String toString() {
            return this.name
                    + ", num="
                    + this.num
                    + ", enabled="
                    + this.enabled
                    + ", targetCategory="
                    + this.targetCategory
                    + ", restriction="
                    + this.restriction
                    + ", action="
                    + this.action
                    + ", firstTriggerTime="
                    + this.firstTriggerTime
                    + ", repeatTriggerTime = "
                    + this.repeatTriggerTime;
        }
    }

    public static String convertPolicyNumToName(int i) {
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
        if (6 == i) {
            return "sbikepolicy";
        }
        if (8 == i) {
            return "disablepolicy";
        }
        return null;
    }

    public static void getMARsSettingsInfoFromDefaultValue() {
        ArrayList arrayList = new ArrayList();
        String[][] strArr = mMARsSettingsInfoDefault;
        for (int i = 0; i < 6; i++) {
            String[] strArr2 = strArr[i];
            String str = strArr2[0];
            String str2 = strArr2[1];
            arrayList.add(new MARsSettingsInfo());
        }
    }

    public static void getPolicyFromDefaultValue() {
        ArrayList arrayList = new ArrayList();
        String[][] strArr = mPolicyInfoDefault;
        for (int i = 0; i < 4; i++) {
            String[] strArr2 = strArr[i];
            int parseInt = Integer.parseInt(strArr2[0]);
            int parseInt2 = Integer.parseInt(strArr2[1]);
            int parseInt3 = Integer.parseInt(strArr2[2]);
            int parseInt4 = Integer.parseInt(strArr2[3]);
            int parseInt5 = Integer.parseInt(strArr2[4]);
            long parseLong = Long.parseLong(strArr2[5]);
            long parseLong2 = Long.parseLong(strArr2[6]);
            Integer.parseInt(strArr2[7]);
            arrayList.add(
                    new PolicyInfo(
                            convertPolicyNumToName(parseInt),
                            parseInt,
                            parseInt2,
                            parseInt3,
                            parseInt4,
                            parseInt5,
                            parseLong,
                            parseLong2));
        }
        mPolicyInfoList = arrayList;
    }

    public static int getRestrictionFlag() {
        String[][] strArr = mMARsSettingsInfoDefault;
        int i = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            String[] strArr2 = strArr[i2];
            String str = strArr2[0];
            String str2 = strArr2[1];
            try {
                if ("restriction_flag".equals(str)) {
                    i = Integer.parseInt(str2);
                }
            } catch (Exception e) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        e, "Exception getRestrictionFlag!", "MARsVersionManager");
            }
        }
        return i;
    }

    public static String toNormalText(String str) {
        return (str == null || !str.startsWith("##"))
                ? str
                : new String(Base64.getDecoder().decode(str.substring(2)));
    }

    public final void getIsCurrentImportantFromDefaultValue() {
        ArrayList arrayList = new ArrayList();
        String[][] strArr = mCurrentImportantDefault;
        for (int i = 0; i < 9; i++) {
            String[] strArr2 = strArr[i];
            arrayList.add(
                    new AdjustTargetCurrentImportant(
                            Integer.parseInt(strArr2[0]), Integer.parseInt(strArr2[1])));
        }
        setAdjustTargetCurrentImportant(arrayList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0083, code lost:

       if ((r0.equals("equals") ? r9.equals(r2) : r0.equals("equalsIgnoreCase") ? r9.equalsIgnoreCase(r2) : r0.equals("contains") ? r9.contains(r2) : r0.equals("startsWith") ? r9.startsWith(r2) : r0.equals("endsWith") ? r9.endsWith(r2) : false) == false) goto L39;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0087, code lost:

       r0 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0085, code lost:

       if (r2 == null) goto L40;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAdjustRestrictionMatch(
            int r6, java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.mars.database.MARsVersionManager.isAdjustRestrictionMatch(int,"
                    + " java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public final void setAdjustTargetCurrentImportant(ArrayList arrayList) {
        ArrayMap arrayMap = this.mIsCurrentImportantMap;
        if (arrayMap == null) {
            Slog.e("MARsVersionManager", "mIsCurrentImportantMap is null!");
            return;
        }
        arrayMap.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            this.mIsCurrentImportantMap.put(
                    Integer.valueOf(((AdjustTargetCurrentImportant) arrayList.get(i)).policyNum),
                    Integer.valueOf(
                            ((AdjustTargetCurrentImportant) arrayList.get(i)).importantValue));
        }
    }
}
