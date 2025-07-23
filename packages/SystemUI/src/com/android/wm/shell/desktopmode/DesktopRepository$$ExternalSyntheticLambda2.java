package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopRepository;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopRepository$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                int i = DesktopRepository.$r8$clinit;
                return Boolean.valueOf(((DesktopRepository.Desk) obj).deskLabel != 0);
            case 1:
                int i2 = DesktopRepository.$r8$clinit;
                return Integer.valueOf(((DesktopRepository.Desk) obj).deskId);
            case 2:
                int i3 = DesktopRepository.$r8$clinit;
                return ((DesktopRepository.Desk) obj).topTransparentFullscreenTaskId;
            case 3:
                int i4 = DesktopRepository.$r8$clinit;
                return ((DesktopRepository.Desk) obj).deepCopy();
            case 4:
                int i5 = DesktopRepository.$r8$clinit;
                return Boolean.valueOf(((DesktopRepository.Desk) obj).usedDesk != -1);
            case 5:
                int i6 = DesktopRepository.$r8$clinit;
                return Integer.valueOf(((DesktopRepository.Desk) obj).deskId);
            case 6:
                return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((DesktopRepository.DesktopDisplay) obj).orderedDesks);
            case 7:
                return Boolean.valueOf(((DesktopRepository.DesktopDisplay) obj).activeDeskId != null);
            case 8:
                DesktopRepository.DesktopDisplay desktopDisplay = (DesktopRepository.DesktopDisplay) obj;
                Object obj2 = null;
                for (Object obj3 : desktopDisplay.orderedDesks) {
                    int i7 = ((DesktopRepository.Desk) obj3).deskId;
                    Integer num = desktopDisplay.activeDeskId;
                    if (num != null && i7 == num.intValue()) {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        z = true;
                        obj2 = obj3;
                    }
                }
                if (z) {
                    return (DesktopRepository.Desk) obj2;
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            default:
                return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((DesktopRepository.DesktopDisplay) obj).orderedDesks);
        }
    }
}
