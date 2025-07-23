package android.database.sqlite;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean concurrentOpenHelper();

    boolean noCheckpointOnFinalize();

    boolean onewayFinalizerCloseFixed();

    boolean sqliteApis35();
}
