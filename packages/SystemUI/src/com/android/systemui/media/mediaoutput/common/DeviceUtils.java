package com.android.systemui.media.mediaoutput.common;

import android.content.ContentResolver;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.util.Log;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;

public final class DeviceUtils {
    public static final DeviceUtils INSTANCE = new DeviceUtils();
    public static final Lazy isChinaSalesCode$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.common.DeviceUtils$isChinaSalesCode$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            String salesCode = SemSystemProperties.getSalesCode();
            List listOf = CollectionsKt__CollectionsKt.listOf("CBK", "CHM", "CTC", "CHN", "CHC", "CHU");
            boolean z = false;
            if (!(listOf instanceof Collection) || !listOf.isEmpty()) {
                Iterator it = listOf.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (salesCode.equalsIgnoreCase((String) it.next())) {
                        z = true;
                        break;
                    }
                }
            }
            return Boolean.valueOf(z);
        }
    });

    private DeviceUtils() {
    }

    public static void checkVolumeLimiter(ContentResolver contentResolver, boolean z, int i, Function0 function0) {
        if (!z || Settings.System.getInt(contentResolver, "volumelimit_on", 0) == 0) {
            return;
        }
        int i2 = Settings.System.getInt(contentResolver, "volume_limiter_value", 150);
        if (i2 * 10 < i) {
            Log.d("DeviceUtils", "volumeLimitValue = " + i2);
            function0.invoke();
        }
    }
}
