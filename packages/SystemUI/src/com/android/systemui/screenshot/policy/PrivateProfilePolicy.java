package com.android.systemui.screenshot.policy;

import com.android.systemui.screenshot.data.repository.ProfileTypeRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PrivateProfilePolicy implements CapturePolicy {
    public final ProfileTypeRepository profileTypes;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public PrivateProfilePolicy(ProfileTypeRepository profileTypeRepository) {
        this.profileTypes = profileTypeRepository;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x00d9, code lost:
    
        if (r12 != com.android.systemui.screenshot.data.model.ProfileType.PRIVATE) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x00d4 -> B:10:0x00d7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x0082 -> B:12:0x00b1). Please report as a decompilation issue!!! */
    @Override // com.android.systemui.screenshot.policy.CapturePolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object check(com.android.systemui.screenshot.data.model.DisplayContentModel r11, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.policy.PrivateProfilePolicy.check(com.android.systemui.screenshot.data.model.DisplayContentModel, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
