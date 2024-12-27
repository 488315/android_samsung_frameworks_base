package com.android.server.broadcastradio.hal2;

import android.hardware.broadcastradio.V2_0.ProgramFilter;
import android.hardware.broadcastradio.V2_0.ProgramIdentifier;
import android.hardware.radio.ProgramSelector;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class Convert$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Convert$$ExternalSyntheticLambda5(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((ArrayList) obj2).add((ProgramIdentifier) obj);
                break;
            case 1:
                ((ArrayList) obj2).add((Integer) obj);
                break;
            default:
                ProgramSelector.Identifier identifier = (ProgramSelector.Identifier) obj;
                ArrayList arrayList = ((ProgramFilter) obj2).identifiers;
                ProgramIdentifier programIdentifier = new ProgramIdentifier();
                programIdentifier.type = identifier.getType();
                programIdentifier.value = identifier.getValue();
                arrayList.add(programIdentifier);
                break;
        }
    }
}
