package com.android.app.tracing.coroutines;

import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CoroutineTraceName implements CoroutineContext.Element {
    public static final Key Key = new Key(null);
    public final String name;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Key implements CoroutineContext.Key {
        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Key() {
        }
    }

    public CoroutineTraceName(String str) {
        this.name = str;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final Object fold(Object obj, Function2 function2) {
        return function2.invoke(obj, this);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext.Element get(CoroutineContext.Key key) {
        boolean z;
        CoroutineContext.Element element;
        boolean z2 = key instanceof AbstractCoroutineContextKey;
        Key key2 = Key;
        if (!z2) {
            if (key2 == key) {
                return this;
            }
            return null;
        }
        AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey) key;
        if (key2 == abstractCoroutineContextKey) {
            abstractCoroutineContextKey.getClass();
        } else if (abstractCoroutineContextKey.topmostKey != key2) {
            z = false;
            if (!z && (element = (CoroutineContext.Element) abstractCoroutineContextKey.safeCast.mo779invoke(this)) != null) {
                return element;
            }
        }
        z = true;
        return !z ? null : null;
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    public final CoroutineContext.Key getKey() {
        return Key;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext minusKey(CoroutineContext.Key key) {
        boolean z;
        boolean z2 = key instanceof AbstractCoroutineContextKey;
        Key key2 = Key;
        if (!z2) {
            return key2 == key ? EmptyCoroutineContext.INSTANCE : this;
        }
        AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey) key;
        if (key2 == abstractCoroutineContextKey) {
            abstractCoroutineContextKey.getClass();
        } else if (abstractCoroutineContextKey.topmostKey != key2) {
            z = false;
            return (!z || ((CoroutineContext.Element) abstractCoroutineContextKey.safeCast.mo779invoke(this)) == null) ? this : EmptyCoroutineContext.INSTANCE;
        }
        z = true;
        if (z) {
            return this;
        }
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }
}
