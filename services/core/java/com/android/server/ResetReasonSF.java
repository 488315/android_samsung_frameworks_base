package com.android.server;

import java.util.regex.Pattern;

/* compiled from: ResetReasonCode.java */
/* loaded from: classes.dex */
class ResetReasonSF extends CommonNativeResetReasonCode {
  @Override // com.android.server.ResetReasonCode
  public String addCauseContents() {
    return "graphics";
  }

  @Override // com.android.server.ResetReasonCode
  public Pattern getPatternByReason() {
    Pattern compile =
        Pattern.compile(
            "pid: (\\d+).*surfaceflinger.*|pid: (\\d+).*android\\.hardware\\.graphics.*|pid:"
                + " (\\d+).*vendor\\.qti\\.hardware\\.display.*");
    this.pattern = compile;
    return compile;
  }
}
