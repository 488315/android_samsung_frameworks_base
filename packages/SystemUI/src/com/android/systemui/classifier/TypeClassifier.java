package com.android.systemui.classifier;

import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.classifier.FalsingClassifier;

/* loaded from: classes.dex */
public class TypeClassifier extends FalsingClassifier {
    public TypeClassifier(FalsingDataProvider falsingDataProvider) {
        super(falsingDataProvider);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.classifier.FalsingClassifier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FalsingClassifier.Result calculateFalsingResult(int i) {
        double d = 0.0d;
        if (i == 13) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        boolean zIsHorizontal = falsingDataProvider.isHorizontal();
        boolean z = !zIsHorizontal;
        boolean zIsUp = isUp();
        boolean zIsRight = isRight();
        switch (i) {
            case 0:
            case 2:
            case 9:
                zIsHorizontal = zIsHorizontal || zIsUp;
                d = 1.0d;
                break;
            case 1:
            case 15:
                zIsHorizontal = z;
                d = 1.0d;
                break;
            case 4:
            case 8:
            case 19:
                if (zIsHorizontal || !zIsUp) {
                }
                d = 1.0d;
                break;
            case 5:
                if (!zIsRight || !zIsUp) {
                }
                d = 1.0d;
                break;
            case 6:
                if (zIsRight || !zIsUp) {
                }
                d = 1.0d;
                break;
            case 10:
            case 18:
                zIsHorizontal = z;
                break;
            case 11:
            case 17:
                d = 1.0d;
                break;
            case 12:
                if (zIsHorizontal || !zIsUp) {
                }
                d = 1.0d;
                break;
        }
        if (!zIsHorizontal) {
            return FalsingClassifier.Result.passed(0.5d);
        }
        boolean z2 = !falsingDataProvider.isHorizontal();
        boolean zIsUp2 = isUp();
        boolean zIsRight2 = isRight();
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("{interaction=", i, ", vertical=", z2, ", up=");
        sbM.append(zIsUp2);
        sbM.append(", right=");
        sbM.append(zIsRight2);
        sbM.append("}");
        return falsed(d, sbM.toString());
    }
}
