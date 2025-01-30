package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;

/* loaded from: classes5.dex */
public interface CMSTypedData extends CMSProcessable {
  ASN1ObjectIdentifier getContentType();
}
