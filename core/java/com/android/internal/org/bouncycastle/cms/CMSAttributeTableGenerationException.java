package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes5.dex */
public class CMSAttributeTableGenerationException extends CMSRuntimeException {

    /* renamed from: e */
    Exception f756e;

    public CMSAttributeTableGenerationException(String name) {
        super(name);
    }

    public CMSAttributeTableGenerationException(String name, Exception e) {
        super(name);
        this.f756e = e;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSRuntimeException
    public Exception getUnderlyingException() {
        return this.f756e;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSRuntimeException, java.lang.Throwable
    public Throwable getCause() {
        return this.f756e;
    }
}
