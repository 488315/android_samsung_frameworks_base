package com.android.systemui.assist;

import com.android.internal.logging.UiEventLogger;
import com.samsung.android.knox.custom.CustomDeviceManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum AssistantInvocationEvent implements UiEventLogger.UiEventEnum {
    ASSISTANT_INVOCATION_UNKNOWN(CustomDeviceManager.MULTI_WINDOW_PERCENTAGE),
    ASSISTANT_INVOCATION_TOUCH_GESTURE(443),
    /* JADX INFO: Fake field, exist only in values array */
    ASSISTANT_INVOCATION_TOUCH_GESTURE_ALT(444),
    ASSISTANT_INVOCATION_HOTWORD(445),
    ASSISTANT_INVOCATION_QUICK_SEARCH_BAR(446),
    ASSISTANT_INVOCATION_HOME_LONG_PRESS(447),
    ASSISTANT_INVOCATION_PHYSICAL_GESTURE(448),
    ASSISTANT_INVOCATION_START_UNKNOWN(530),
    ASSISTANT_INVOCATION_START_TOUCH_GESTURE(531),
    ASSISTANT_INVOCATION_START_PHYSICAL_GESTURE(532),
    ASSISTANT_INVOCATION_POWER_LONG_PRESS(758);

    public static final Companion Companion = new Companion(null);

    /* renamed from: id */
    private final int f229id;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static int deviceStateFromLegacyDeviceState(int i) {
            switch (i) {
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
                case 6:
                    return 6;
                case 7:
                    return 7;
                case 8:
                    return 8;
                case 9:
                    return 9;
                case 10:
                    return 10;
                default:
                    return 0;
            }
        }
    }

    AssistantInvocationEvent(int i) {
        this.f229id = i;
    }

    public final int getId() {
        return this.f229id;
    }
}
