package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopRepository;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopRepository$MultiDesktopData$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ DesktopRepository$MultiDesktopData$$ExternalSyntheticLambda1(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(((DesktopRepository.Desk) obj).deskId == this.f$0);
            default:
                return Boolean.valueOf(((DesktopRepository.DesktopDisplay) obj).displayId == this.f$0);
        }
    }
}
