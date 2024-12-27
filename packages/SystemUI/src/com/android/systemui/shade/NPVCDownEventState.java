package com.android.systemui.shade;

import com.android.systemui.common.buffer.RingBuffer;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NPVCDownEventState {
    public static final List TABLE_HEADERS;
    public boolean allowExpandForSmallExpansion;
    public final Lazy asStringList$delegate;
    public boolean canCollapseOnQQS;
    public boolean collapsed;
    public boolean dozing;
    public boolean lastEventSynthesized;
    public boolean listenForHeadsUp;
    public boolean qsTouchAboveFalsingThreshold;
    public long timeStamp;
    public boolean touchSlopExceededBeforeDown;
    public float x;
    public float y;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Buffer {
        public final RingBuffer buffer;

        public Buffer(int i) {
            this.buffer = new RingBuffer(i, new Function0() { // from class: com.android.systemui.shade.NPVCDownEventState$Buffer$buffer$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new NPVCDownEventState(0L, 0.0f, 0.0f, false, false, false, false, false, false, false, false, CustomDeviceManager.SETTINGS_ALL_PREVIOUS, null);
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TABLE_HEADERS = CollectionsKt__CollectionsKt.listOf("Timestamp", "X", "Y", "QSTouchAboveFalsingThreshold", "Dozing", "Collapsed", "CanCollapseOnQQS", "ListenForHeadsUp", "AllowExpandForSmallExpansion", "TouchSlopExceededBeforeDown", "LastEventSynthesized");
    }

    private NPVCDownEventState(long j, float f, float f2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
        this.timeStamp = j;
        this.x = f;
        this.y = f2;
        this.qsTouchAboveFalsingThreshold = z;
        this.dozing = z2;
        this.collapsed = z3;
        this.canCollapseOnQQS = z4;
        this.listenForHeadsUp = z5;
        this.allowExpandForSmallExpansion = z6;
        this.touchSlopExceededBeforeDown = z7;
        this.lastEventSynthesized = z8;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.NPVCDownEventState$asStringList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CollectionsKt__CollectionsKt.listOf(NPVCDownEventStateKt.DATE_FORMAT.format(Long.valueOf(NPVCDownEventState.this.timeStamp)), String.valueOf(NPVCDownEventState.this.x), String.valueOf(NPVCDownEventState.this.y), String.valueOf(NPVCDownEventState.this.qsTouchAboveFalsingThreshold), String.valueOf(NPVCDownEventState.this.dozing), String.valueOf(NPVCDownEventState.this.collapsed), String.valueOf(NPVCDownEventState.this.canCollapseOnQQS), String.valueOf(NPVCDownEventState.this.listenForHeadsUp), String.valueOf(NPVCDownEventState.this.allowExpandForSmallExpansion), String.valueOf(NPVCDownEventState.this.touchSlopExceededBeforeDown), String.valueOf(NPVCDownEventState.this.lastEventSynthesized));
            }
        });
    }

    public /* synthetic */ NPVCDownEventState(long j, float f, float f2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j, (i & 2) != 0 ? 0.0f : f, (i & 4) == 0 ? f2 : 0.0f, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? false : z3, (i & 64) != 0 ? false : z4, (i & 128) != 0 ? false : z5, (i & 256) != 0 ? false : z6, (i & 512) != 0 ? false : z7, (i & 1024) == 0 ? z8 : false);
    }
}
