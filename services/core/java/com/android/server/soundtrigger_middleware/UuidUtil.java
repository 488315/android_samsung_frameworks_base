package com.android.server.soundtrigger_middleware;

import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public abstract class UuidUtil {
  public static final Pattern PATTERN =
      Pattern.compile(
          "^([a-fA-F0-9]{8})-([a-fA-F0-9]{4})-([a-fA-F0-9]{4})-([a-fA-F0-9]{4})-([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})$");
}
