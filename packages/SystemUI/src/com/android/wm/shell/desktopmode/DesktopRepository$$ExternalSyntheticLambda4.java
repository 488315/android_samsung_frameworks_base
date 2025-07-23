package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopRepository;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopRepository$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopRepository f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DesktopRepository$$ExternalSyntheticLambda4(int i, int i2, DesktopRepository desktopRepository) {
        this.$r8$classId = i2;
        this.f$0 = desktopRepository;
        this.f$1 = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        DesktopRepository desktopRepository = this.f$0;
        int i = this.f$1;
        DesktopRepository.Desk desk = (DesktopRepository.Desk) obj;
        switch (this.$r8$classId) {
            case 0:
                int i2 = DesktopRepository.$r8$clinit;
                desktopRepository.removeTaskFromDesk(desk.deskId, i);
                break;
            case 1:
                int i3 = DesktopRepository.$r8$clinit;
                desktopRepository.unminimizeTaskFromDesk(desk.deskId, i);
                break;
            default:
                int i4 = DesktopRepository.$r8$clinit;
                if (desk.closingTasks.remove(Integer.valueOf(i))) {
                    Object[] objArr = {Integer.valueOf(i), Integer.valueOf(desk.deskId)};
                    desktopRepository.getClass();
                    DesktopRepository.logD("Removed closing task=%d deskId=%d", objArr);
                }
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ DesktopRepository$$ExternalSyntheticLambda4(int i, DesktopRepository desktopRepository) {
        this.$r8$classId = 2;
        this.f$1 = i;
        this.f$0 = desktopRepository;
    }
}
