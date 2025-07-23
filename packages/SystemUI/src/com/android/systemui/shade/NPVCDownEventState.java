package com.android.systemui.shade;

import android.icu.text.SimpleDateFormat;
import com.android.systemui.common.buffer.RingBuffer;
import java.util.Arrays;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Buffer {
        public final RingBuffer buffer;

        public Buffer(int i) {
            this.buffer = new RingBuffer(i, new NPVCDownEventState$Buffer$$ExternalSyntheticLambda0());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TABLE_HEADERS = Arrays.asList("Timestamp", "X", "Y", "QSTouchAboveFalsingThreshold", "Dozing", "Collapsed", "CanCollapseOnQQS", "ListenForHeadsUp", "AllowExpandForSmallExpansion", "TouchSlopExceededBeforeDown", "LastEventSynthesized");
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
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.NPVCDownEventState$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List list = NPVCDownEventState.TABLE_HEADERS;
                SimpleDateFormat simpleDateFormat = NPVCDownEventStateKt.DATE_FORMAT;
                NPVCDownEventState nPVCDownEventState = NPVCDownEventState.this;
                return Arrays.asList(simpleDateFormat.format(Long.valueOf(nPVCDownEventState.timeStamp)), String.valueOf(nPVCDownEventState.x), String.valueOf(nPVCDownEventState.y), String.valueOf(nPVCDownEventState.qsTouchAboveFalsingThreshold), String.valueOf(nPVCDownEventState.dozing), String.valueOf(nPVCDownEventState.collapsed), String.valueOf(nPVCDownEventState.canCollapseOnQQS), String.valueOf(nPVCDownEventState.listenForHeadsUp), String.valueOf(nPVCDownEventState.allowExpandForSmallExpansion), String.valueOf(nPVCDownEventState.touchSlopExceededBeforeDown), String.valueOf(nPVCDownEventState.lastEventSynthesized));
            }
        });
    }

    public /* synthetic */ NPVCDownEventState(long j, float f, float f2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j, (i & 2) != 0 ? 0.0f : f, (i & 4) != 0 ? 0.0f : f2, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? false : z3, (i & 64) != 0 ? false : z4, (i & 128) != 0 ? false : z5, (i & 256) != 0 ? false : z6, (i & 512) != 0 ? false : z7, (i & 1024) != 0 ? false : z8);
    }
}
