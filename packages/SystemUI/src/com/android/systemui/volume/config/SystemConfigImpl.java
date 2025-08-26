package com.android.systemui.volume.config;

import android.content.Context;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Result;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class SystemConfigImpl {
    public final Context context;
    public final Lazy isTablet$delegate = LazyKt__LazyJVMKt.lazy(new SystemConfigImpl$$ExternalSyntheticLambda0());
    public final Lazy hasCutout$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.config.SystemConfigImpl$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Object failure;
            SystemConfigImpl systemConfigImpl = this.f$0;
            try {
                int i = Result.$r8$clinit;
                int identifier = systemConfigImpl.context.getResources().getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
                String string = identifier > 0 ? systemConfigImpl.context.getString(identifier) : "";
                string.getClass();
                failure = Boolean.valueOf(string.length() > 0);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Object obj = Boolean.FALSE;
            if (failure instanceof Result.Failure) {
                failure = obj;
            }
            return (Boolean) failure;
        }
    });

    public SystemConfigImpl(Context context) {
        this.context = context;
    }

    public final boolean isTablet() {
        return ((Boolean) this.isTablet$delegate.getValue()).booleanValue();
    }
}
