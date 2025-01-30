package com.android.internal.org.bouncycastle.operator;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import java.io.InputStream;

/* loaded from: classes5.dex */
public interface InputDecryptor {
  AlgorithmIdentifier getAlgorithmIdentifier();

  InputStream getInputStream(InputStream inputStream);
}
