package com.android.systemui.keyguard;

import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda0 */
/* loaded from: classes.dex */
public final /* synthetic */ class C1457x419da85a implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ C1457x419da85a(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((Integer) obj).intValue() != this.f$0) {
                    break;
                }
                break;
            case 1:
                if (((Integer) obj).intValue() != this.f$0) {
                    break;
                }
                break;
            default:
                if (((Integer) obj).intValue() != this.f$0) {
                    break;
                }
                break;
        }
        return false;
    }
}
