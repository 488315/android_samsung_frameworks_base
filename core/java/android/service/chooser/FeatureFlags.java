package android.service.chooser;

public interface FeatureFlags {
    boolean chooserAlbumText();

    boolean chooserPayloadToggling();

    boolean enableChooserResult();

    boolean enableSharesheetMetadataExtra();

    boolean fixResolverMemoryLeak();
}
