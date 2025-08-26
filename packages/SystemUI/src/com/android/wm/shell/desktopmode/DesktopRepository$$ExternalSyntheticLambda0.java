package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopRepository;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopRepository$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = DesktopRepository.$r8$clinit;
                return Boolean.valueOf(((DesktopRepository.Desk) obj).deskLabel != 0);
            case 1:
                int i2 = DesktopRepository.$r8$clinit;
                return Integer.valueOf(((DesktopRepository.Desk) obj).deskId);
            case 2:
                int i3 = DesktopRepository.$r8$clinit;
                return Integer.valueOf(((DesktopRepository.Desk) obj).deskId);
            case 3:
                int i4 = DesktopRepository.$r8$clinit;
                return ((DesktopRepository.Desk) obj).deepCopy();
            case 4:
                int i5 = DesktopRepository.$r8$clinit;
                return ((DesktopRepository.Desk) obj).topTransparentFullscreenTaskId;
            case 5:
                int i6 = DesktopRepository.$r8$clinit;
                return Boolean.valueOf(((DesktopRepository.Desk) obj).deskLabel != 0);
            case 6:
                int i7 = DesktopRepository.$r8$clinit;
                return Boolean.valueOf(((DesktopRepository.Desk) obj).usedDesk != -1);
            case 7:
                return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((DesktopRepository.DesktopDisplay) obj).orderedDesks);
            case 8:
                return Boolean.valueOf(((DesktopRepository.DesktopDisplay) obj).activeDeskId != null);
            case 9:
                DesktopRepository.DesktopDisplay desktopDisplay = (DesktopRepository.DesktopDisplay) obj;
                Object obj2 = null;
                for (Object obj3 : desktopDisplay.orderedDesks) {
                    int i8 = ((DesktopRepository.Desk) obj3).deskId;
                    Integer num = desktopDisplay.activeDeskId;
                    if (num != null && i8 == num.intValue()) {
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
            case 10:
                return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((DesktopRepository.DesktopDisplay) obj).orderedDesks);
            default:
                return Boolean.valueOf(((DesktopRepository.Desk) obj).usedDesk == 0);
        }
    }
}
