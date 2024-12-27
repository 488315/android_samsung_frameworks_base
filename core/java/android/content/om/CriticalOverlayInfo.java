package android.content.om;

public interface CriticalOverlayInfo {
    OverlayIdentifier getOverlayIdentifier();

    String getOverlayName();

    String getPackageName();

    String getTargetOverlayableName();

    String getTargetPackageName();

    boolean isFabricated();
}
