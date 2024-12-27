package com.android.server.wm;

import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import java.util.function.Supplier;

public final /* synthetic */ class ActivityRecord$$ExternalSyntheticLambda23 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ActivityRecord$$ExternalSyntheticLambda23(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                activityRecord.getClass();
                Context context = null;
                try {
                    context =
                            activityRecord.mAtmService.mContext.createPackageContextAsUser(
                                    activityRecord.info.packageName,
                                    4,
                                    UserHandle.of(activityRecord.mUserId));
                    context.setTheme(activityRecord.theme);
                    return context;
                } catch (PackageManager.NameNotFoundException unused) {
                    return context;
                }
            default:
                return ((Context) obj).getDrawable(R.drawable.ic_doc_word);
        }
    }
}
