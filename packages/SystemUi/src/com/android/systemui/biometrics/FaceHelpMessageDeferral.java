package com.android.systemui.biometrics;

import android.content.res.Resources;
import com.android.keyguard.logging.FaceMessageDeferralLogger;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import java.util.HashSet;
import kotlin.collections.MapsKt__MapsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceHelpMessageDeferral extends BiometricMessageDeferral {
    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FaceHelpMessageDeferral(Resources resources, FaceMessageDeferralLogger faceMessageDeferralLogger, DumpManager dumpManager) {
        super(r1, resources.getFloat(R.dimen.config_face_help_msgs_defer_until_timeout_threshold), faceMessageDeferralLogger, dumpManager);
        int[] intArray = resources.getIntArray(R.array.config_face_help_msgs_defer_until_timeout);
        HashSet hashSet = new HashSet(MapsKt__MapsJVMKt.mapCapacity(intArray.length));
        for (int i : intArray) {
            hashSet.add(Integer.valueOf(i));
        }
    }
}
