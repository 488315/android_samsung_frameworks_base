package com.android.systemui.screenshot.resources;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public class Messages {
    public final Context context;
    public final Lazy savingScreenshotAnnouncement$delegate;
    public final Lazy savingToPrivateProfileAnnouncement$delegate;
    public final Lazy savingToWorkProfileAnnouncement$delegate;

    public Messages(Context context) {
        this.context = context;
        final int i = 0;
        this.savingScreenshotAnnouncement$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.screenshot.resources.Messages$$ExternalSyntheticLambda0
            public final /* synthetic */ Messages f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws Resources.NotFoundException {
                switch (i) {
                    case 0:
                        String string = this.f$0.context.getResources().getString(R.string.screenshot_saving_title);
                        if (string != null) {
                            return string;
                        }
                        throw new IllegalArgumentException("Required value was null.");
                    case 1:
                        String string2 = this.f$0.context.getResources().getString(R.string.screenshot_saving_work_profile_title);
                        if (string2 != null) {
                            return string2;
                        }
                        throw new IllegalArgumentException("Required value was null.");
                    default:
                        String string3 = this.f$0.context.getResources().getString(R.string.screenshot_saving_private_profile);
                        if (string3 != null) {
                            return string3;
                        }
                        throw new IllegalArgumentException("Required value was null.");
                }
            }
        });
        final int i2 = 1;
        this.savingToWorkProfileAnnouncement$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.screenshot.resources.Messages$$ExternalSyntheticLambda0
            public final /* synthetic */ Messages f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws Resources.NotFoundException {
                switch (i2) {
                    case 0:
                        String string = this.f$0.context.getResources().getString(R.string.screenshot_saving_title);
                        if (string != null) {
                            return string;
                        }
                        throw new IllegalArgumentException("Required value was null.");
                    case 1:
                        String string2 = this.f$0.context.getResources().getString(R.string.screenshot_saving_work_profile_title);
                        if (string2 != null) {
                            return string2;
                        }
                        throw new IllegalArgumentException("Required value was null.");
                    default:
                        String string3 = this.f$0.context.getResources().getString(R.string.screenshot_saving_private_profile);
                        if (string3 != null) {
                            return string3;
                        }
                        throw new IllegalArgumentException("Required value was null.");
                }
            }
        });
        final int i3 = 2;
        this.savingToPrivateProfileAnnouncement$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.screenshot.resources.Messages$$ExternalSyntheticLambda0
            public final /* synthetic */ Messages f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws Resources.NotFoundException {
                switch (i3) {
                    case 0:
                        String string = this.f$0.context.getResources().getString(R.string.screenshot_saving_title);
                        if (string != null) {
                            return string;
                        }
                        throw new IllegalArgumentException("Required value was null.");
                    case 1:
                        String string2 = this.f$0.context.getResources().getString(R.string.screenshot_saving_work_profile_title);
                        if (string2 != null) {
                            return string2;
                        }
                        throw new IllegalArgumentException("Required value was null.");
                    default:
                        String string3 = this.f$0.context.getResources().getString(R.string.screenshot_saving_private_profile);
                        if (string3 != null) {
                            return string3;
                        }
                        throw new IllegalArgumentException("Required value was null.");
                }
            }
        });
    }
}
