package com.android.systemui.subscreen;

import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SubScreenManager$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SubScreenManager$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                SubScreenManager subScreenManager = (SubScreenManager) obj2;
                Long l = (Long) obj;
                if (subScreenManager.mSubScreenPlugin != null) {
                    Log.d("SubScreenManager", "dozeTimeTick() time=" + l);
                    subScreenManager.mSubScreenPlugin.dozeTimeTick();
                    break;
                } else {
                    Log.w("SubScreenManager", "dozeTimeTick() no plugin");
                    break;
                }
            default:
                ((List) obj2).add(((NotificationEntry) obj).mSbn);
                break;
        }
    }
}
