package com.android.internal.org.bouncycastle.util;

import java.util.Collection;

/* loaded from: classes5.dex */
public interface Store<T> {
    Collection<T> getMatches(Selector<T> selector) throws StoreException;
}
