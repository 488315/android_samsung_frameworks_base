package com.android.settingslib.satellite;

import android.content.Context;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SatelliteDialogUtils {
    public static final SatelliteDialogUtils INSTANCE = new SatelliteDialogUtils();

    private SatelliteDialogUtils() {
    }

    public static final Object access$requestIsSessionStarted(SatelliteDialogUtils satelliteDialogUtils, Context context, Continuation continuation) {
        satelliteDialogUtils.getClass();
        return BuildersKt.withContext(Dispatchers.Default, new SatelliteDialogUtils$requestIsSessionStarted$2(context, null), continuation);
    }

    public static final StandaloneCoroutine mayStartSatelliteWarningDialog(Context context, CoroutineScope coroutineScope, Function1 function1) {
        return BuildersKt.launch$default(coroutineScope, null, null, new SatelliteDialogUtils$mayStartSatelliteWarningDialog$1(context, 0, function1, null), 3);
    }
}
