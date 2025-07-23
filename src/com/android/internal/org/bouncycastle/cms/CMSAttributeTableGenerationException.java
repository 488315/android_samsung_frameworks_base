package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes5.dex */
public class CMSAttributeTableGenerationException extends CMSRuntimeException {
    Exception e;

    public CMSAttributeTableGenerationException(String str) {
        super(str);
    }

    public CMSAttributeTableGenerationException(String str, Exception exc) {
        super(str);
        this.e = exc;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSRuntimeException
    public Exception getUnderlyingException() {
        return this.e;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSRuntimeException, java.lang.Throwable
    public Throwable getCause() {
        return this.e;
    }
}
