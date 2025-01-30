package com.android.systemui.classifier;

import com.android.systemui.classifier.FalsingClassifier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TypeClassifier extends FalsingClassifier {
    public TypeClassifier(FalsingDataProvider falsingDataProvider) {
        super(falsingDataProvider);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x003c, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002a, code lost:
    
        if (r5 != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
    
        if (r5 != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0034, code lost:
    
        if (r5 != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0039, code lost:
    
        if (r5 == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0021, code lost:
    
        if (r5 != false) goto L26;
     */
    @Override // com.android.systemui.classifier.FalsingClassifier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FalsingClassifier.Result calculateFalsingResult(int i) {
        double d = 0.0d;
        if (i == 13) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        boolean z = !this.mDataProvider.isHorizontal();
        boolean isUp = isUp();
        boolean isRight = isRight();
        switch (i) {
            case 0:
            case 2:
            case 9:
                if (z) {
                }
                z = true;
                d = 1.0d;
                break;
            case 1:
            case 15:
                d = 1.0d;
                break;
            case 3:
            case 7:
            case 13:
            case 14:
            case 16:
            default:
                z = true;
                d = 1.0d;
                break;
            case 4:
            case 8:
                if (z) {
                }
                z = true;
                d = 1.0d;
                break;
            case 5:
                if (isRight) {
                }
                z = true;
                d = 1.0d;
                break;
            case 6:
                if (!isRight) {
                }
                z = true;
                d = 1.0d;
                break;
            case 10:
            case 18:
                break;
            case 11:
            case 17:
                boolean z2 = !z;
                z = z2;
                d = 1.0d;
                break;
            case 12:
                if (z) {
                }
                z = true;
                d = 1.0d;
                break;
        }
        return z ? falsed(d, String.format("{interaction=%s, vertical=%s, up=%s, right=%s}", Integer.valueOf(i), Boolean.valueOf(!r0.isHorizontal()), Boolean.valueOf(isUp()), Boolean.valueOf(isRight()))) : FalsingClassifier.Result.passed(0.5d);
    }
}
