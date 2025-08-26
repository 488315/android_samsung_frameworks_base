package com.android.systemui.log.table;

import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class DiffableKt$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ TableLogBuffer f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DiffableKt$$ExternalSyntheticLambda2(TableLogBuffer tableLogBuffer, String str, String str2, String str3) {
        this.f$0 = tableLogBuffer;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = str3;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                EmptyList emptyList = (EmptyList) this.f$3;
                emptyList.getClass();
                this.f$0.logChange(this.f$1, this.f$2, "[]", true);
                return emptyList;
            default:
                TableLogBuffer tableLogBuffer = this.f$0;
                String str = this.f$1;
                String str2 = this.f$2;
                String str3 = (String) this.f$3;
                tableLogBuffer.logChange(str, str2, str3, true);
                return str3;
        }
    }

    public /* synthetic */ DiffableKt$$ExternalSyntheticLambda2(TableLogBuffer tableLogBuffer, String str, String str2, EmptyList emptyList) {
        this.f$0 = tableLogBuffer;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = emptyList;
    }
}
