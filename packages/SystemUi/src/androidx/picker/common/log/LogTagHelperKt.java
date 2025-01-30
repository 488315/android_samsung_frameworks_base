package androidx.picker.common.log;

import android.os.Build;
import android.util.Log;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class LogTagHelperKt {
    public static final boolean IS_DEBUG_DEVICE;

    static {
        String str = Build.TYPE;
        Locale locale = Locale.ROOT;
        IS_DEBUG_DEVICE = StringsKt__StringsKt.contains(str.toLowerCase(locale), "debug", false) || Intrinsics.areEqual(str.toLowerCase(locale), "eng");
    }

    public static final void debug(LogTag logTag, String str) {
        if (IS_DEBUG_DEVICE) {
            Log.d("SeslAppPicker[1.0.44-sesl6]." + logTag.getLogTag(), str);
        }
    }

    public static final void info(LogTag logTag, String str) {
        Log.i("SeslAppPicker[1.0.44-sesl6]." + logTag.getLogTag(), str);
    }

    public static final void warn(LogTag logTag, String str) {
        Log.w("SeslAppPicker[1.0.44-sesl6]." + logTag.getLogTag(), str);
    }
}
