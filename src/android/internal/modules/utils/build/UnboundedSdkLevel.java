package android.internal.modules.utils.build;

import android.hardware.gnss.GnssSignalType;
import android.os.Build;
import android.util.ArraySet;
import android.util.SparseArray;
import java.util.Set;

/* loaded from: classes2.dex */
public final class UnboundedSdkLevel {
    private static final SparseArray<Set<String>> PREVIOUS_CODENAMES;
    private static final UnboundedSdkLevel sInstance;
    private final String mCodename;
    private final boolean mIsReleaseBuild;
    private final Set<String> mKnownCodenames;
    private final int mSdkInt;

    public static boolean isAtLeast(String str) {
        return sInstance.isAtLeastInternal(str);
    }

    public static boolean isAtMost(String str) {
        return sInstance.isAtMostInternal(str);
    }

    static {
        Set<String> set;
        SparseArray<Set<String>> sparseArray = new SparseArray<>(4);
        PREVIOUS_CODENAMES = sparseArray;
        sparseArray.put(29, setOf(GnssSignalType.CODE_TYPE_Q));
        sparseArray.put(30, setOf(GnssSignalType.CODE_TYPE_Q, "R"));
        sparseArray.put(31, setOf(GnssSignalType.CODE_TYPE_Q, "R", GnssSignalType.CODE_TYPE_S));
        sparseArray.put(32, setOf(GnssSignalType.CODE_TYPE_Q, "R", GnssSignalType.CODE_TYPE_S, "Sv2"));
        int i = Build.VERSION.SDK_INT;
        String str = Build.VERSION.CODENAME;
        if (SdkLevel.isAtLeastT()) {
            set = Build.VERSION.KNOWN_CODENAMES;
        } else {
            set = sparseArray.get(Build.VERSION.SDK_INT);
        }
        sInstance = new UnboundedSdkLevel(i, str, set);
    }

    private static Set<String> setOf(String... strArr) {
        if (SdkLevel.isAtLeastR()) {
            return Set.of((Object[]) strArr);
        }
        ArraySet arraySet = new ArraySet(strArr.length);
        for (String str : strArr) {
            arraySet.add(str);
        }
        return arraySet;
    }

    UnboundedSdkLevel(int i, String str, Set<String> set) {
        this.mSdkInt = i;
        this.mCodename = str;
        this.mIsReleaseBuild = "REL".equals(str);
        this.mKnownCodenames = set;
    }

    boolean isAtLeastInternal(String str) {
        String removeFingerprint = removeFingerprint(str);
        if (this.mIsReleaseBuild) {
            if (!isCodename(removeFingerprint)) {
                return this.mSdkInt >= Integer.parseInt(removeFingerprint);
            }
            if (!this.mKnownCodenames.contains(removeFingerprint)) {
                return false;
            }
            throw new IllegalArgumentException("Artifact with a known codename " + removeFingerprint + " must be recompiled with a finalized integer version.");
        }
        if (isCodename(removeFingerprint)) {
            return this.mKnownCodenames.contains(removeFingerprint);
        }
        return this.mSdkInt >= Integer.parseInt(removeFingerprint);
    }

    boolean isAtMostInternal(String str) {
        String removeFingerprint = removeFingerprint(str);
        if (!this.mIsReleaseBuild) {
            return isCodename(removeFingerprint) ? !this.mKnownCodenames.contains(removeFingerprint) || this.mCodename.equals(removeFingerprint) : this.mSdkInt < Integer.parseInt(removeFingerprint);
        }
        if (!isCodename(removeFingerprint)) {
            return this.mSdkInt <= Integer.parseInt(removeFingerprint);
        }
        if (!this.mKnownCodenames.contains(removeFingerprint)) {
            return true;
        }
        throw new IllegalArgumentException("Artifact with a known codename " + removeFingerprint + " must be recompiled with a finalized integer version.");
    }

    String removeFingerprint(String str) {
        int indexOf;
        return (!isCodename(str) || (indexOf = str.indexOf(46)) == -1) ? str : str.substring(0, indexOf);
    }

    private boolean isCodename(String str) {
        if (str.length() == 0) {
            throw new IllegalArgumentException();
        }
        return Character.isUpperCase(str.charAt(0));
    }
}
