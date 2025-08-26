package com.android.settingslib.notification.modes;

import android.graphics.drawable.Drawable;
import android.util.Log;
import com.android.settingslib.notification.modes.ZenIcon;
import com.google.common.base.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class ZenIconLoader$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ZenIcon.Key f$1;

    @Override // com.google.common.base.Function
    public final Object apply(Object obj) {
        ZenIcon.Key key = this.f$1;
        switch (this.$r8$classId) {
            case 0:
                Drawable drawable = (Drawable) obj;
                Drawable drawable2 = ZenIconLoader.MISSING;
                drawable.getClass();
                return new ZenIcon(key, drawable);
            default:
                Drawable drawable3 = ZenIconLoader.MISSING;
                Log.e("ZenIconLoader", "Error while loading mode icon " + key, (Exception) obj);
                return null;
        }
    }
}
