package com.android.wm.shell.startingsurface;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.function.IntPredicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester$AlphaFilterQuantizer$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C4125xf5e80cfe implements IntPredicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.IntPredicate
    public final boolean test(int i) {
        switch (this.$r8$classId) {
            case 0:
                if ((i & EmergencyPhoneWidget.BG_COLOR) == 0) {
                    break;
                }
                break;
            default:
                if ((i & EmergencyPhoneWidget.BG_COLOR) != -16777216) {
                    break;
                }
                break;
        }
        return false;
    }
}
