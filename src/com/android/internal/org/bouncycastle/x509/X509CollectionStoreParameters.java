package com.android.internal.org.bouncycastle.x509;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes5.dex */
public class X509CollectionStoreParameters implements X509StoreParameters {
    private Collection collection;

    public X509CollectionStoreParameters(Collection collection) {
        if (collection == null) {
            throw new NullPointerException("collection cannot be null");
        }
        this.collection = collection;
    }

    public Object clone() {
        return new X509CollectionStoreParameters(this.collection);
    }

    public Collection getCollection() {
        return new ArrayList(this.collection);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("X509CollectionStoreParameters: [\n");
        stringBuffer.append("  collection: " + this.collection + ShaderAssembler.NEWLINE);
        stringBuffer.append(NavigationBarInflaterView.SIZE_MOD_END);
        return stringBuffer.toString();
    }
}
