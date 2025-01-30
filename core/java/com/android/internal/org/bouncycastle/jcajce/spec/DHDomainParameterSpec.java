package com.android.internal.org.bouncycastle.jcajce.spec;

import com.android.internal.org.bouncycastle.crypto.params.DHParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters;
import java.math.BigInteger;
import javax.crypto.spec.DHParameterSpec;

/* loaded from: classes5.dex */
public class DHDomainParameterSpec extends DHParameterSpec {

  /* renamed from: j */
  private final BigInteger f909j;

  /* renamed from: m */
  private final int f910m;

  /* renamed from: q */
  private final BigInteger f911q;
  private DHValidationParameters validationParameters;

  public DHDomainParameterSpec(DHParameters domainParameters) {
    this(
        domainParameters.getP(),
        domainParameters.getQ(),
        domainParameters.getG(),
        domainParameters.getJ(),
        domainParameters.getM(),
        domainParameters.getL());
    this.validationParameters = domainParameters.getValidationParameters();
  }

  public DHDomainParameterSpec(BigInteger p, BigInteger q, BigInteger g) {
    this(p, q, g, null, 0);
  }

  public DHDomainParameterSpec(BigInteger p, BigInteger q, BigInteger g, int l) {
    this(p, q, g, null, l);
  }

  public DHDomainParameterSpec(BigInteger p, BigInteger q, BigInteger g, BigInteger j, int l) {
    this(p, q, g, j, 0, l);
  }

  public DHDomainParameterSpec(
      BigInteger p, BigInteger q, BigInteger g, BigInteger j, int m, int l) {
    super(p, g, l);
    this.f911q = q;
    this.f909j = j;
    this.f910m = m;
  }

  public BigInteger getQ() {
    return this.f911q;
  }

  public BigInteger getJ() {
    return this.f909j;
  }

  public int getM() {
    return this.f910m;
  }

  public DHParameters getDomainParameters() {
    return new DHParameters(
        getP(), getG(), this.f911q, this.f910m, getL(), this.f909j, this.validationParameters);
  }
}
