package android.internal.modules.utils.build;

import android.hardware.gnss.GnssSignalType;
import android.os.Build;
import android.util.ArraySet;
import android.util.SparseArray;
import java.util.Set;

/* loaded from: classes2.dex */
public final class UnboundedSdkLevel {
    private static final SparseArray<Set<String>> PREVIOUS_CODENAMES = new SparseArray<>(4);
    private static final UnboundedSdkLevel sInstance;
    private final String mCodename;
    private final boolean mIsReleaseBuild;
    private final Set<String> mKnownCodenames;
    private final int mSdkInt;

    public static boolean isAtLeast(String version) {
        return sInstance.isAtLeastInternal(version);
    }

    public static boolean isAtMost(String version) {
        return sInstance.isAtMostInternal(version);
    }

    static {
        Set<String> set;
        PREVIOUS_CODENAMES.put(29, setOf(GnssSignalType.CODE_TYPE_Q));
        PREVIOUS_CODENAMES.put(30, setOf(GnssSignalType.CODE_TYPE_Q, "R"));
        PREVIOUS_CODENAMES.put(31, setOf(GnssSignalType.CODE_TYPE_Q, "R", GnssSignalType.CODE_TYPE_S));
        PREVIOUS_CODENAMES.put(32, setOf(GnssSignalType.CODE_TYPE_Q, "R", GnssSignalType.CODE_TYPE_S, "Sv2"));
        int i = Build.VERSION.SDK_INT;
        String str = Build.VERSION.CODENAME;
        if (SdkLevel.isAtLeastT()) {
            set = Build.VERSION.KNOWN_CODENAMES;
        } else {
            set = PREVIOUS_CODENAMES.get(Build.VERSION.SDK_INT);
        }
        sInstance = new UnboundedSdkLevel(i, str, set);
    }

    private static Set<String> setOf(String... contents) {
        if (SdkLevel.isAtLeastR()) {
            return Set.of((Object[]) contents);
        }
        Set<String> set = new ArraySet<>(contents.length);
        for (String codename : contents) {
            set.add(codename);
        }
        return set;
    }

    UnboundedSdkLevel(int sdkInt, String codename, Set<String> knownCodenames) {
        this.mSdkInt = sdkInt;
        this.mCodename = codename;
        this.mIsReleaseBuild = "REL".equals(codename);
        this.mKnownCodenames = knownCodenames;
    }

    boolean isAtLeastInternal(String version) {
        String version2 = removeFingerprint(version);
        if (this.mIsReleaseBuild) {
            if (!isCodename(version2)) {
                return this.mSdkInt >= Integer.parseInt(version2);
            }
            if (this.mKnownCodenames.contains(version2)) {
                throw new IllegalArgumentException("Artifact with a known codename " + version2 + " must be recompiled with a finalized integer version.");
            }
            return false;
        }
        if (isCodename(version2)) {
            return this.mKnownCodenames.contains(version2);
        }
        return this.mSdkInt >= Integer.parseInt(version2);
    }

    boolean isAtMostInternal(String version) {
        String version2 = removeFingerprint(version);
        if (!this.mIsReleaseBuild) {
            return isCodename(version2) ? !this.mKnownCodenames.contains(version2) || this.mCodename.equals(version2) : this.mSdkInt < Integer.parseInt(version2);
        }
        if (!isCodename(version2)) {
            return this.mSdkInt <= Integer.parseInt(version2);
        }
        if (this.mKnownCodenames.contains(version2)) {
            throw new IllegalArgumentException("Artifact with a known codename " + version2 + " must be recompiled with a finalized integer version.");
        }
        return true;
    }

    String removeFingerprint(String version) {
        int index;
        if (isCodename(version) && (index = version.indexOf(46)) != -1) {
            return version.substring(0, index);
        }
        return version;
    }

    private boolean isCodename(String version) {
        if (version.length() == 0) {
            throw new IllegalArgumentException();
        }
        return Character.isUpperCase(version.charAt(0));
    }
}
