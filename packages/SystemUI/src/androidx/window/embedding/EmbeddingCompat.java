package androidx.window.embedding;

import android.content.Context;
import android.util.Log;
import androidx.window.WindowSdkExtensions;
import androidx.window.WindowSdkExtensions$Companion$getInstance$1;
import androidx.window.core.ConsumerAdapter;
import androidx.window.extensions.WindowExtensionsProvider;
import androidx.window.extensions.embedding.ActivityEmbeddingComponent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class EmbeddingCompat implements EmbeddingInterfaceCompat {
    public static final Companion Companion = new Companion(null);
    public final EmbeddingAdapter adapter;
    public final ConsumerAdapter consumerAdapter;
    public final ActivityEmbeddingComponent embeddingExtension;
    public final WindowSdkExtensions$Companion$getInstance$1 windowSdkExtensions;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean isEmbeddingAvailable() {
            try {
                ClassLoader classLoader = EmbeddingCompat.class.getClassLoader();
                if (classLoader != null) {
                    return new SafeActivityEmbeddingComponentProvider(classLoader, new ConsumerAdapter(classLoader), WindowExtensionsProvider.getWindowExtensions()).getActivityEmbeddingComponent() != null;
                }
                return false;
            } catch (NoClassDefFoundError unused) {
                Log.d("EmbeddingCompat", "Embedding extension version not found");
                return false;
            } catch (UnsupportedOperationException unused2) {
                Log.d("EmbeddingCompat", "Stub Extension");
                return false;
            }
        }

        private Companion() {
        }
    }

    public EmbeddingCompat(ActivityEmbeddingComponent activityEmbeddingComponent, EmbeddingAdapter embeddingAdapter, ConsumerAdapter consumerAdapter, Context context, OverlayControllerImpl overlayControllerImpl, ActivityWindowInfoCallbackController activityWindowInfoCallbackController) {
        this.embeddingExtension = activityEmbeddingComponent;
        this.adapter = embeddingAdapter;
        this.consumerAdapter = consumerAdapter;
        WindowSdkExtensions.Companion.getClass();
        this.windowSdkExtensions = WindowSdkExtensions.Companion.getInstance();
    }
}
