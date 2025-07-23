package com.android.systemui.kairos.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.Lazy;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class TransactionalImpl {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Const extends TransactionalImpl {
        public final Lazy value;

        public Const(Lazy lazy) {
            super(null);
            this.value = lazy;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Const) && Intrinsics.areEqual(this.value, ((Const) obj).value);
        }

        public final int hashCode() {
            return this.value.hashCode();
        }

        public final String toString() {
            return "Const(value=" + this.value + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Impl extends TransactionalImpl {
        public final Function1 block;
        public final TransactionCache cache;

        public Impl(Function1 function1) {
            super(null);
            this.block = function1;
            this.cache = new TransactionCache();
        }

        public final String toString() {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(Impl.class).getSimpleName(), "@", UtilKt.getHashString(this));
        }
    }

    public /* synthetic */ TransactionalImpl(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private TransactionalImpl() {
    }
}
