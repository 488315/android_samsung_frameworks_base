package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class CMSProcessableByteArray implements CMSTypedData, CMSReadable {
    private final byte[] bytes;
    private final ASN1ObjectIdentifier type;

    public CMSProcessableByteArray(byte[] bArr) {
        this(CMSObjectIdentifiers.data, bArr);
    }

    public CMSProcessableByteArray(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) {
        this.type = aSN1ObjectIdentifier;
        this.bytes = bArr;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSReadable
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public void write(OutputStream outputStream) throws IOException, CMSException {
        outputStream.write(this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public Object getContent() {
        return Arrays.clone(this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSTypedData
    public ASN1ObjectIdentifier getContentType() {
        return this.type;
    }
}
