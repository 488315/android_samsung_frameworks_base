package com.android.systemui.dump;

import com.android.systemui.ProtoDumpable;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.DumpsysEntry;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class DumpHandler$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DumpHandler$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = (String) obj;
                DumpHandler.Companion companion = DumpHandler.Companion;
                if (ArraysKt___ArraysKt.contains(DumpHandlerKt.PRIORITY_OPTIONS, str)) {
                    return str;
                }
                throw new IllegalArgumentException();
            case 1:
                DumpHandler.Companion companion2 = DumpHandler.Companion;
                return Integer.valueOf(Integer.parseInt((String) obj));
            default:
                return Boolean.valueOf(((DumpsysEntry.DumpableEntry) obj).dumpable instanceof ProtoDumpable);
        }
    }
}
