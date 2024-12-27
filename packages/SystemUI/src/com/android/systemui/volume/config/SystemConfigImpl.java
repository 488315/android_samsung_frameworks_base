package com.android.systemui.volume.config;

import android.content.Context;
import com.android.systemui.util.DeviceType;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Result;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemConfigImpl {
    public final Context context;
    public final Lazy isTablet$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.config.SystemConfigImpl$isTablet$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(DeviceType.isTablet());
        }
    });
    public final Lazy hasCutout$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.config.SystemConfigImpl$hasCutout$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Object failure;
            SystemConfigImpl systemConfigImpl = SystemConfigImpl.this;
            try {
                int i = Result.$r8$clinit;
                int identifier = systemConfigImpl.context.getResources().getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
                String string = identifier > 0 ? systemConfigImpl.context.getString(identifier) : "";
                Intrinsics.checkNotNull(string);
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
