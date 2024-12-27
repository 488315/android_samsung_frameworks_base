package com.android.systemui.volume.soundassistant;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

public final class SoundAssistantChecker {
    public final Context context;
    public boolean isNeedToChangeBuds3IconToBtIcon;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SoundAssistantChecker(Context context) {
        this.context = context;
    }

    public final void updateState(boolean z) {
        Object failure;
        ?? singletonList;
        String str;
        boolean z2 = false;
        if (z) {
            try {
                int i = Result.$r8$clinit;
                PackageManager packageManager = this.context.getPackageManager();
                SoundAssistantConstants.INSTANCE.getClass();
                PackageInfo packageInfo = packageManager.getPackageInfo(SoundAssistantConstants.SOUNDASSISTANT_PACKAGE_NAME, 0);
                if (packageInfo == null || (str = packageInfo.versionName) == null) {
                    singletonList = Collections.singletonList(r1);
                } else {
                    List split$default = StringsKt__StringsKt.split$default(str, new String[]{"."}, 0, 6);
                    singletonList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
                    Iterator it = split$default.iterator();
                    while (it.hasNext()) {
                        singletonList.add(Integer.valueOf(Integer.parseInt((String) it.next())));
                    }
                }
                Log.d("SecVolume.SoundAssistantChecker", "version=" + singletonList);
                failure = Integer.valueOf(((Number) singletonList.get(0)).intValue());
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            int i3 = Result.$r8$clinit;
            int intValue = ((Number) (failure instanceof Result.Failure ? 0 : failure)).intValue();
            SoundAssistantConstants.INSTANCE.getClass();
            if (intValue <= SoundAssistantConstants.FIX_BUDS3_ICON_SAT_MAJOR_VERSION) {
                z2 = true;
            }
        }
        this.isNeedToChangeBuds3IconToBtIcon = z2;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("isOn=", ", isNeedToChangeBuds3IconToBtIcon=", "SecVolume.SoundAssistantChecker", z, z2);
    }
}
