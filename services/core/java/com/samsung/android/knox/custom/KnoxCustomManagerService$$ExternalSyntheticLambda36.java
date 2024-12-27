package com.samsung.android.knox.custom;

import android.content.ComponentName;
import com.android.internal.util.FunctionalUtils;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda36 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda36(KnoxCustomManagerService knoxCustomManagerService, StatusbarIconItem statusbarIconItem) {
        this.$r8$classId = 0;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = statusbarIconItem;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda36(KnoxCustomManagerService knoxCustomManagerService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = obj;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                StatusbarIconItem statusbarIconItem = (StatusbarIconItem) this.f$1;
                String str = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setBatteryLevelColourItem$59(statusbarIconItem);
            case 1:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$removeDexShortcut$4(componentName);
            default:
                KnoxCustomManagerService knoxCustomManagerService3 = this.f$0;
                List list = (List) this.f$1;
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService3.lambda$setPowerDialogCustomItemsLocal$146(list);
        }
    }
}
