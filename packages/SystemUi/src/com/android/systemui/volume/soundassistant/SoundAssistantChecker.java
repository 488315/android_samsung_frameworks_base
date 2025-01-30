package com.android.systemui.volume.soundassistant;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundAssistantChecker {
    public final Context context;
    public boolean isNeedToChangeBuds3IconToBtIcon;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        boolean z2 = false;
        if (z) {
            try {
                int i = Result.$r8$clinit;
                PackageManager packageManager = this.context.getPackageManager();
                SoundAssistantConstants.INSTANCE.getClass();
                List split$default = StringsKt__StringsKt.split$default(packageManager.getPackageInfo(SoundAssistantConstants.SOUNDASSISTANT_PACKAGE_NAME, 0).versionName, new String[]{"."}, 0, 6);
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
                Iterator it = split$default.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(Integer.parseInt((String) it.next())));
                }
                Log.d("SecVolume.SoundAssistantChecker", "version=" + arrayList);
                failure = Integer.valueOf(((Number) arrayList.get(0)).intValue());
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            int i3 = Result.$r8$clinit;
            if (failure instanceof Result.Failure) {
                failure = 0;
            }
            int intValue = ((Number) failure).intValue();
            SoundAssistantConstants.INSTANCE.getClass();
            if (intValue <= SoundAssistantConstants.FIX_BUDS3_ICON_SAT_MAJOR_VERSION) {
                z2 = true;
            }
        }
        this.isNeedToChangeBuds3IconToBtIcon = z2;
        EmergencyButtonController$$ExternalSyntheticOutline0.m59m("isOn=", z, ", isNeedToChangeBuds3IconToBtIcon=", z2, "SecVolume.SoundAssistantChecker");
    }
}
