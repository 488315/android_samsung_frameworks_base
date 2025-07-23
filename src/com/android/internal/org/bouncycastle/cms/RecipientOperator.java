package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.operator.InputDecryptor;
import com.android.internal.org.bouncycastle.operator.MacCalculator;
import com.android.internal.org.bouncycastle.util.io.TeeInputStream;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class RecipientOperator {
    private final Object operator;

    public RecipientOperator(InputDecryptor inputDecryptor) {
        this.operator = inputDecryptor;
    }

    public RecipientOperator(MacCalculator macCalculator) {
        this.operator = macCalculator;
    }

    public InputStream getInputStream(InputStream inputStream) {
        Object obj = this.operator;
        if (obj instanceof InputDecryptor) {
            return ((InputDecryptor) obj).getInputStream(inputStream);
        }
        return new TeeInputStream(inputStream, ((MacCalculator) this.operator).getOutputStream());
    }

    public boolean isMacBased() {
        return this.operator instanceof MacCalculator;
    }

    public byte[] getMac() {
        return ((MacCalculator) this.operator).getMac();
    }
}
