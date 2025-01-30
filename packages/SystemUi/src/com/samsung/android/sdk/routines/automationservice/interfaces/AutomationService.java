package com.samsung.android.sdk.routines.automationservice.interfaces;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface AutomationService {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum SystemRoutineType {
        SOUND_CRAFT("system_sound_craft");

        public static final Companion Companion = new Companion(null);
        private final String value;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        SystemRoutineType(String str) {
            this.value = str;
        }

        public final String getValue() {
            return this.value;
        }
    }
}
