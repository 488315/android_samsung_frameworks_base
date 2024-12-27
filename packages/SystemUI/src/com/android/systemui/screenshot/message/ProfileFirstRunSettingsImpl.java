package com.android.systemui.screenshot.message;

import android.content.Context;
import com.android.systemui.screenshot.message.ProfileMessageController;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ProfileFirstRunSettingsImpl implements ProfileFirstRunSettings {
    public final Context context;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProfileMessageController.FirstRunProfile.values().length];
            try {
                iArr[ProfileMessageController.FirstRunProfile.WORK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProfileMessageController.FirstRunProfile.PRIVATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public ProfileFirstRunSettingsImpl(Context context) {
        this.context = context;
    }
}
