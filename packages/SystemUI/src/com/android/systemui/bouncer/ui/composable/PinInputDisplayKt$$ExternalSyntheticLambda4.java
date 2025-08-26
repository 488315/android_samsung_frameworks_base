package com.android.systemui.bouncer.ui.composable;

import androidx.compose.runtime.snapshots.SnapshotStateList;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class PinInputDisplayKt$$ExternalSyntheticLambda4 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PinInputDisplayKt$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                ((PinBouncerViewModel) this.f$0).simBouncerInteractor.disableEsim();
                return Unit.INSTANCE;
            default:
                SnapshotStateList snapshotStateList = ((PinInputRow) this.f$0).entries;
                if (snapshotStateList == null || !snapshotStateList.isEmpty()) {
                    Iterator it = snapshotStateList.iterator();
                    while (it.hasNext()) {
                        if (((PinInputEntry) it.next()).isUnused()) {
                            z = true;
                        }
                    }
                    z = false;
                } else {
                    z = false;
                }
                return Boolean.valueOf(z);
        }
    }
}
