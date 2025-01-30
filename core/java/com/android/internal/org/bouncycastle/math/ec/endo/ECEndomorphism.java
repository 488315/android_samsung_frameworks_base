package com.android.internal.org.bouncycastle.math.ec.endo;

import com.android.internal.org.bouncycastle.math.ec.ECPointMap;

/* loaded from: classes5.dex */
public interface ECEndomorphism {
  ECPointMap getPointMap();

  boolean hasEfficientPointMap();
}
