package com.android.systemui.media.mediaoutput.common;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceUtils {
    public static final DeviceUtils INSTANCE = new DeviceUtils();
    public static final Lazy isChinaSalesCode$delegate;
    public static final Lazy isDebug$delegate;
    public static final Lazy supportMusicShare$delegate;

    static {
        final int i = 0;
        isDebug$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.common.DeviceUtils$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean z;
                switch (i) {
                    case 0:
                        DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
                        return Boolean.valueOf(Arrays.asList("eng", "userdebug").contains(Build.TYPE));
                    case 1:
                        DeviceUtils deviceUtils2 = DeviceUtils.INSTANCE;
                        String salesCode = SemSystemProperties.getSalesCode();
                        List asList = Arrays.asList("CBK", "CHM", "CTC", "CHN", "CHC", "CHU");
                        if (!(asList instanceof Collection) || !asList.isEmpty()) {
                            Iterator it = asList.iterator();
                            while (it.hasNext()) {
                                z = true;
                                if (StringsKt__StringsJVMKt.equals(salesCode, (String) it.next(), true)) {
                                    return Boolean.valueOf(z);
                                }
                            }
                        }
                        z = false;
                        return Boolean.valueOf(z);
                    default:
                        DeviceUtils deviceUtils3 = DeviceUtils.INSTANCE;
                        return Boolean.valueOf(SemBluetoothCastAdapter.isBluetoothCastSupported());
                }
            }
        });
        final int i2 = 1;
        isChinaSalesCode$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.common.DeviceUtils$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean z;
                switch (i2) {
                    case 0:
                        DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
                        return Boolean.valueOf(Arrays.asList("eng", "userdebug").contains(Build.TYPE));
                    case 1:
                        DeviceUtils deviceUtils2 = DeviceUtils.INSTANCE;
                        String salesCode = SemSystemProperties.getSalesCode();
                        List asList = Arrays.asList("CBK", "CHM", "CTC", "CHN", "CHC", "CHU");
                        if (!(asList instanceof Collection) || !asList.isEmpty()) {
                            Iterator it = asList.iterator();
                            while (it.hasNext()) {
                                z = true;
                                if (StringsKt__StringsJVMKt.equals(salesCode, (String) it.next(), true)) {
                                    return Boolean.valueOf(z);
                                }
                            }
                        }
                        z = false;
                        return Boolean.valueOf(z);
                    default:
                        DeviceUtils deviceUtils3 = DeviceUtils.INSTANCE;
                        return Boolean.valueOf(SemBluetoothCastAdapter.isBluetoothCastSupported());
                }
            }
        });
        final int i3 = 2;
        supportMusicShare$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.common.DeviceUtils$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean z;
                switch (i3) {
                    case 0:
                        DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
                        return Boolean.valueOf(Arrays.asList("eng", "userdebug").contains(Build.TYPE));
                    case 1:
                        DeviceUtils deviceUtils2 = DeviceUtils.INSTANCE;
                        String salesCode = SemSystemProperties.getSalesCode();
                        List asList = Arrays.asList("CBK", "CHM", "CTC", "CHN", "CHC", "CHU");
                        if (!(asList instanceof Collection) || !asList.isEmpty()) {
                            Iterator it = asList.iterator();
                            while (it.hasNext()) {
                                z = true;
                                if (StringsKt__StringsJVMKt.equals(salesCode, (String) it.next(), true)) {
                                    return Boolean.valueOf(z);
                                }
                            }
                        }
                        z = false;
                        return Boolean.valueOf(z);
                    default:
                        DeviceUtils deviceUtils3 = DeviceUtils.INSTANCE;
                        return Boolean.valueOf(SemBluetoothCastAdapter.isBluetoothCastSupported());
                }
            }
        });
    }

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

    public static boolean getSupportCastSetting(Context context) {
        return !((Boolean) isChinaSalesCode$delegate.getValue()).booleanValue() && PackageManagerExtKt.isPackageInstalled(context.getPackageManager(), "com.samsung.android.audiomirroring");
    }
}
