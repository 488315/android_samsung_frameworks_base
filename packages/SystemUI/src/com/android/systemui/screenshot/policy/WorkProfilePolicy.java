package com.android.systemui.screenshot.policy;

import android.content.Context;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WorkProfilePolicy implements CapturePolicy {
    public final Context context;
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

    public WorkProfilePolicy(ProfileTypeRepository profileTypeRepository, Context context) {
        this.profileTypes = profileTypeRepository;
        this.context = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0156 -> B:10:0x015a). Please report as a decompilation issue!!! */
    @Override // com.android.systemui.screenshot.policy.CapturePolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object check(com.android.systemui.screenshot.data.model.DisplayContentModel r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.policy.WorkProfilePolicy.check(com.android.systemui.screenshot.data.model.DisplayContentModel, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
