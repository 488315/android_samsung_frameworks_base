package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/* loaded from: classes5.dex */
public abstract class BaseAlgorithmParameterGeneratorSpi extends AlgorithmParameterGeneratorSpi {
  private final JcaJceHelper helper = new BCJcaJceHelper();

  protected final AlgorithmParameters createParametersInstance(String algorithm)
      throws NoSuchAlgorithmException, NoSuchProviderException {
    return this.helper.createAlgorithmParameters(algorithm);
  }
}
