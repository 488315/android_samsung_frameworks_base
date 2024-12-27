package com.android.server.pm;

import java.util.function.Function;

public final /* synthetic */ class ComputerEngine$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ComputerEngine$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ComputerEngine computerEngine = (ComputerEngine) obj2;
                ComputerEngine$$ExternalSyntheticLambda1 computerEngine$$ExternalSyntheticLambda1 =
                        ComputerEngine.sProviderInitOrderSorter;
                computerEngine.getClass();
                return computerEngine.getPackagesForUid(((Integer) obj).intValue());
            case 1:
                ComputerEngine computerEngine2 = (ComputerEngine) obj2;
                ComputerEngine$$ExternalSyntheticLambda1 computerEngine$$ExternalSyntheticLambda12 =
                        ComputerEngine.sProviderInitOrderSorter;
                computerEngine2.getClass();
                return computerEngine2.getPackagesForUid(((Integer) obj).intValue());
            default:
                return ((ComputerEngine.Settings) obj2).mSettings.getPackageLPr((String) obj);
        }
    }
}
