package com.android.systemui.volume.soundassistant;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes3.dex */
public final class SoundAssistantChecker {
    public final Context context;
    public boolean isNeedToChangeBuds3IconToBtIcon;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SoundAssistantChecker(Context context) {
        this.context = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v9, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v10, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.lang.Object, java.util.List] */
    public final void updateState(boolean z) {
        Object failure;
        ?? SingletonList;
        String str;
        List listSplit$default;
        boolean z2 = false;
        if (z) {
            try {
                int i = Result.$r8$clinit;
                PackageManager packageManager = this.context.getPackageManager();
                SoundAssistantConstants.INSTANCE.getClass();
                PackageInfo packageInfo = packageManager.getPackageInfo(SoundAssistantConstants.SOUNDASSISTANT_PACKAGE_NAME, 0);
                if (packageInfo == null || (str = packageInfo.versionName) == null || (listSplit$default = StringsKt__StringsKt.split$default(str, new String[]{"."}, 0, 6)) == null) {
                    SingletonList = Collections.singletonList(obj);
                } else {
                    List list = listSplit$default;
                    SingletonList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        SingletonList.add(Integer.valueOf(Integer.parseInt((String) it.next())));
                    }
                }
                Log.d("SecVolume.SoundAssistantChecker", "version=" + SingletonList);
                failure = Integer.valueOf(((Number) SingletonList.get(0)).intValue());
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            int i3 = Result.$r8$clinit;
            int iIntValue = ((Number) (failure instanceof Result.Failure ? 0 : failure)).intValue();
            SoundAssistantConstants.INSTANCE.getClass();
            if (iIntValue <= SoundAssistantConstants.FIX_BUDS3_ICON_SAT_MAJOR_VERSION) {
                z2 = true;
            }
        }
        this.isNeedToChangeBuds3IconToBtIcon = z2;
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isOn=", ", isNeedToChangeBuds3IconToBtIcon=", "SecVolume.SoundAssistantChecker", z, z2);
    }
}
