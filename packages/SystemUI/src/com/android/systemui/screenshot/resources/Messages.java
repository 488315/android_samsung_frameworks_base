package com.android.systemui.screenshot.resources;

import android.content.Context;
import com.android.systemui.R;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Messages {
    public final Context context;
    public final Lazy savingScreenshotAnnouncement$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.screenshot.resources.Messages$savingScreenshotAnnouncement$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            String string = Messages.this.context.getResources().getString(R.string.screenshot_saving_title);
            if (string != null) {
                return string;
            }
            throw new IllegalArgumentException("Required value was null.".toString());
        }
    });
    public final Lazy savingToWorkProfileAnnouncement$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.screenshot.resources.Messages$savingToWorkProfileAnnouncement$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            String string = Messages.this.context.getResources().getString(R.string.screenshot_saving_work_profile_title);
            if (string != null) {
                return string;
            }
            throw new IllegalArgumentException("Required value was null.".toString());
        }
    });
    public final Lazy savingToPrivateProfileAnnouncement$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.screenshot.resources.Messages$savingToPrivateProfileAnnouncement$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            String string = Messages.this.context.getResources().getString(R.string.screenshot_saving_private_profile);
            if (string != null) {
                return string;
            }
            throw new IllegalArgumentException("Required value was null.".toString());
        }
    });

    public Messages(Context context) {
        this.context = context;
    }
}
