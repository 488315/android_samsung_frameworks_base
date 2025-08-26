package com.android.systemui.shared.clocks;

import java.util.Calendar;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public class TimespecHandler {
    public final Calendar cal;

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

    public TimespecHandler(Calendar calendar) {
        this.cal = calendar;
    }

    public static /* synthetic */ void getFakeTimeMills$annotations() {
    }
}
