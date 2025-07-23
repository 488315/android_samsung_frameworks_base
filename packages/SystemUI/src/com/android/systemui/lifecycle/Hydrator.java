package com.android.systemui.lifecycle;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.log.table.TableLogBuffer;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Hydrator extends ExclusiveActivatable {
    public final List children;
    public final TableLogBuffer tableLogBuffer;
    public final String traceName;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NamedActivatable {
        public final Activatable activatable;
        public final String traceName;

        public NamedActivatable(String str, Activatable activatable) {
            this.traceName = str;
            this.activatable = activatable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NamedActivatable)) {
                return false;
            }
            NamedActivatable namedActivatable = (NamedActivatable) obj;
            return Intrinsics.areEqual(this.traceName, namedActivatable.traceName) && Intrinsics.areEqual(this.activatable, namedActivatable.activatable);
        }

        public final int hashCode() {
            String str = this.traceName;
            return this.activatable.hashCode() + ((str == null ? 0 : str.hashCode()) * 31);
        }

        public final String toString() {
            return "NamedActivatable(traceName=" + this.traceName + ", activatable=" + this.activatable + ")";
        }
    }

    public /* synthetic */ Hydrator(String str, TableLogBuffer tableLogBuffer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : tableLogBuffer);
    }

    public final State hydratedStateOf(StateFlow stateFlow, String str) {
        return hydratedStateOf(str, stateFlow.getValue(), stateFlow);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.lifecycle.Hydrator$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.lifecycle.Hydrator$onActivated$1 r0 = (com.android.systemui.lifecycle.Hydrator$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.lifecycle.Hydrator$onActivated$1 r0 = new com.android.systemui.lifecycle.Hydrator$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.lifecycle.Hydrator$onActivated$2 r5 = new com.android.systemui.lifecycle.Hydrator$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.Hydrator.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public Hydrator(String str, TableLogBuffer tableLogBuffer) {
        this.traceName = str;
        this.tableLogBuffer = tableLogBuffer;
        this.children = new ArrayList();
    }

    public final State hydratedStateOf(String str, Object obj, Flow flow) {
        if (isActive()) {
            throw new IllegalStateException("Cannot call hydratedStateOf after Hydrator is already active.");
        }
        MutableState mutableStateOf$default = SnapshotStateKt.mutableStateOf$default(obj);
        TableLogBuffer tableLogBuffer = this.tableLogBuffer;
        if (tableLogBuffer != null) {
            tableLogBuffer.logChange(this.traceName, str, obj != null ? obj.toString() : null, true);
        }
        ((ArrayList) this.children).add(new NamedActivatable(str, new Hydrator$hydratedStateOf$3(flow, str, mutableStateOf$default, this)));
        return mutableStateOf$default;
    }
}
