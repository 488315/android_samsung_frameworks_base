package com.android.systemui.shared.clocks;

import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.plugins.ClockMetadata;
import java.util.Collections;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ClockRegistryKt {
    public static final Map KNOWN_PLUGINS = MapsKt__MapsKt.mapOf(new Pair("com.android.systemui.falcon.one", Collections.singletonList(new ClockMetadata("ANALOG_CLOCK_BIGNUM"))), new Pair("com.android.systemui.falcon.two", Collections.singletonList(new ClockMetadata("DIGITAL_CLOCK_CALLIGRAPHY"))), new Pair("com.android.systemui.falcon.three", Collections.singletonList(new ClockMetadata("DIGITAL_CLOCK_FLEX"))), new Pair("com.android.systemui.falcon.four", Collections.singletonList(new ClockMetadata("DIGITAL_CLOCK_GROWTH"))), new Pair("com.android.systemui.falcon.five", Collections.singletonList(new ClockMetadata("DIGITAL_CLOCK_HANDWRITTEN"))), new Pair("com.android.systemui.falcon.six", Collections.singletonList(new ClockMetadata("DIGITAL_CLOCK_INFLATE"))), new Pair("com.android.systemui.falcon.eight", Collections.singletonList(new ClockMetadata("DIGITAL_CLOCK_NUMBEROVERLAP"))), new Pair("com.android.systemui.falcon.nine", Collections.singletonList(new ClockMetadata("DIGITAL_CLOCK_WEATHER"))));
    public static final Lazy TMP_MESSAGE$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shared.clocks.ClockRegistryKt$TMP_MESSAGE$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            LogMessageImpl.Factory.getClass();
            return LogMessageImpl.Factory.create();
        }
    });

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LogLevel.values().length];
            try {
                iArr[LogLevel.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LogLevel.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[LogLevel.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[LogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[LogLevel.WTF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final LogMessage access$getTMP_MESSAGE() {
        return (LogMessage) TMP_MESSAGE$delegate.getValue();
    }
}
