package com.android.server.biometrics;

import com.android.internal.util.jobs.XmlUtils;

/* loaded from: classes.dex */
public class SensorConfig {

  /* renamed from: id */
  public final int f1652id;
  public final int modality;
  public final int strength;

  public SensorConfig(String str) {
    String[] split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
    this.f1652id = Integer.parseInt(split[0]);
    this.modality = Integer.parseInt(split[1]);
    this.strength = Integer.parseInt(split[2]);
  }
}
