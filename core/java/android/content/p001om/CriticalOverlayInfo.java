package android.content.p001om;

/* loaded from: classes.dex */
public interface CriticalOverlayInfo {
  OverlayIdentifier getOverlayIdentifier();

  String getOverlayName();

  String getPackageName();

  String getTargetOverlayableName();

  String getTargetPackageName();

  boolean isFabricated();
}
