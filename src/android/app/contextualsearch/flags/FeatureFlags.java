package android.app.contextualsearch.flags;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean contextualSearchPreventSelfCapture();

    boolean enableService();

    boolean enableTokenRefresh();

    boolean includeAudioPlayingStatus();

    boolean multiWindowScreenContext();

    boolean reportSecureSurfacesInAssistStructure();

    boolean selfInvocation();
}
