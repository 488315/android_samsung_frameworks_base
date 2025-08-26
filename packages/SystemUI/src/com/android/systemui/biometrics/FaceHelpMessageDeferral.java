package com.android.systemui.biometrics;

import android.content.res.Resources;
import com.android.keyguard.logging.BiometricMessageDeferralLogger;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import dagger.Lazy;
import java.util.HashSet;
import kotlin.collections.MapsKt__MapsJVMKt;

/* loaded from: classes.dex */
public final class FaceHelpMessageDeferral extends BiometricMessageDeferral {
    public FaceHelpMessageDeferral(Resources resources, BiometricMessageDeferralLogger biometricMessageDeferralLogger, DumpManager dumpManager, String str, Lazy lazy) throws Resources.NotFoundException {
        int[] intArray = resources.getIntArray(R.array.config_face_help_msgs_defer_until_timeout);
        HashSet hashSet = new HashSet(MapsKt__MapsJVMKt.mapCapacity(intArray.length));
        for (int i : intArray) {
            hashSet.add(Integer.valueOf(i));
        }
        int[] intArray2 = resources.getIntArray(R.array.config_face_help_msgs_ignore);
        HashSet hashSet2 = new HashSet(MapsKt__MapsJVMKt.mapCapacity(intArray2.length));
        for (int i2 : intArray2) {
            hashSet2.add(Integer.valueOf(i2));
        }
        super(hashSet, hashSet2, resources.getFloat(R.dimen.config_face_help_msgs_defer_until_timeout_threshold), resources.getInteger(R.integer.config_face_help_msgs_defer_analyze_timeframe), biometricMessageDeferralLogger, dumpManager, str, lazy);
    }
}
