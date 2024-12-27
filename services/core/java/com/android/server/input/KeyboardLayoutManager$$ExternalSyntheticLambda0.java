package com.android.server.input;

import android.content.res.Resources;
import android.hardware.input.KeyboardLayout;
import com.android.server.input.KeyboardLayoutManager;
import java.util.ArrayList;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyboardLayoutManager$$ExternalSyntheticLambda0 implements KeyboardLayoutManager.KeyboardLayoutVisitor {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyboardLayoutManager$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
    public final void visitKeyboardLayout(Resources resources, int i, KeyboardLayout keyboardLayout) {
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                ((KeyboardLayout[]) obj)[0] = keyboardLayout;
                break;
            case 1:
                ((ArrayList) obj).add(keyboardLayout);
                break;
            default:
                ((HashSet) obj).add(keyboardLayout.getDescriptor());
                break;
        }
    }
}
