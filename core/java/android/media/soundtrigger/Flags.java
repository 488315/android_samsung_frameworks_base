package android.media.soundtrigger;

/* loaded from: classes2.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_SOUND_TRIGGER_GENERIC_MODEL_API = "android.media.soundtrigger.sound_trigger_generic_model_api";

    public static boolean soundTriggerGenericModelApi() {
        return FEATURE_FLAGS.soundTriggerGenericModelApi();
    }
}
