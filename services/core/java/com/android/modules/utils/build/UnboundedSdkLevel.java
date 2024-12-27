package com.android.modules.utils.build;

import android.os.Build;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UnboundedSdkLevel {
    private static final SparseArray PREVIOUS_CODENAMES;
    private static final UnboundedSdkLevel sInstance;
    private final String mCodename;
    private final boolean mIsReleaseBuild;
    private final Set mKnownCodenames;
    private final int mSdkInt;

    static {
        SparseArray sparseArray = new SparseArray(4);
        PREVIOUS_CODENAMES = sparseArray;
        sparseArray.put(29, setOf("Q"));
        sparseArray.put(30, setOf("Q", "R"));
        sparseArray.put(31, setOf("Q", "R", "S"));
        sparseArray.put(32, setOf("Q", "R", "S", "Sv2"));
        int i = Build.VERSION.SDK_INT;
        sInstance = new UnboundedSdkLevel(i, Build.VERSION.CODENAME, SdkLevel.isAtLeastT() ? Build.VERSION.KNOWN_CODENAMES : (Set) sparseArray.get(i));
    }

    public UnboundedSdkLevel(int i, String str, Set set) {
        this.mSdkInt = i;
        this.mCodename = str;
        this.mIsReleaseBuild = "REL".equals(str);
        this.mKnownCodenames = set;
    }

    public static boolean isAtLeast(String str) {
        return sInstance.isAtLeastInternal(str);
    }

    public static boolean isAtMost(String str) {
        return sInstance.isAtMostInternal(str);
    }

    private boolean isCodename(String str) {
        if (str.length() != 0) {
            return Character.isUpperCase(str.charAt(0));
        }
        throw new IllegalArgumentException();
    }

    private static Set setOf(String... strArr) {
        if (SdkLevel.isAtLeastR()) {
            return Set.of((Object[]) strArr);
        }
        ArraySet arraySet = new ArraySet(strArr.length);
        for (String str : strArr) {
            arraySet.add(str);
        }
        return arraySet;
    }

    public boolean isAtLeastInternal(String str) {
        String removeFingerprint = removeFingerprint(str);
        if (!this.mIsReleaseBuild) {
            return isCodename(removeFingerprint) ? this.mKnownCodenames.contains(removeFingerprint) : this.mSdkInt >= Integer.parseInt(removeFingerprint);
        }
        if (!isCodename(removeFingerprint)) {
            return this.mSdkInt >= Integer.parseInt(removeFingerprint);
        }
        if (this.mKnownCodenames.contains(removeFingerprint)) {
            throw new IllegalArgumentException(XmlUtils$$ExternalSyntheticOutline0.m("Artifact with a known codename ", removeFingerprint, " must be recompiled with a finalized integer version."));
        }
        return false;
    }

    public boolean isAtMostInternal(String str) {
        String removeFingerprint = removeFingerprint(str);
        if (!this.mIsReleaseBuild) {
            return isCodename(removeFingerprint) ? !this.mKnownCodenames.contains(removeFingerprint) || this.mCodename.equals(removeFingerprint) : this.mSdkInt < Integer.parseInt(removeFingerprint);
        }
        if (!isCodename(removeFingerprint)) {
            return this.mSdkInt <= Integer.parseInt(removeFingerprint);
        }
        if (this.mKnownCodenames.contains(removeFingerprint)) {
            throw new IllegalArgumentException(XmlUtils$$ExternalSyntheticOutline0.m("Artifact with a known codename ", removeFingerprint, " must be recompiled with a finalized integer version."));
        }
        return true;
    }

    public String removeFingerprint(String str) {
        int indexOf;
        return (!isCodename(str) || (indexOf = str.indexOf(46)) == -1) ? str : str.substring(0, indexOf);
    }
}
