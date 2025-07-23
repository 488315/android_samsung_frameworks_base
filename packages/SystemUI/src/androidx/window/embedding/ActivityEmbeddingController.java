package androidx.window.embedding;

import android.content.Context;
import androidx.window.embedding.EmbeddingBackend;
import androidx.window.embedding.ExtensionEmbeddingBackend;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ActivityEmbeddingController {
    public static final Companion Companion = new Companion(null);
    public final EmbeddingBackend backend;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ActivityEmbeddingController(EmbeddingBackend embeddingBackend) {
        this.backend = embeddingBackend;
    }

    public static final ActivityEmbeddingController getInstance(Context context) {
        Companion.getClass();
        EmbeddingBackend.Companion.getClass();
        Function1 function1 = EmbeddingBackend.Companion.decorator;
        ExtensionEmbeddingBackend.Companion.getClass();
        if (ExtensionEmbeddingBackend.globalInstance == null) {
            ReentrantLock reentrantLock = ExtensionEmbeddingBackend.globalLock;
            reentrantLock.lock();
            try {
                if (ExtensionEmbeddingBackend.globalInstance == null) {
                    Context applicationContext = context.getApplicationContext();
                    ExtensionEmbeddingBackend.globalInstance = new ExtensionEmbeddingBackend(applicationContext, ExtensionEmbeddingBackend.Companion.initAndVerifyEmbeddingExtension(applicationContext));
                }
                Unit unit = Unit.INSTANCE;
                reentrantLock.unlock();
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }
        ExtensionEmbeddingBackend extensionEmbeddingBackend = ExtensionEmbeddingBackend.globalInstance;
        extensionEmbeddingBackend.getClass();
        ((EmbeddingBackend$Companion$decorator$1) function1).getClass();
        return new ActivityEmbeddingController(extensionEmbeddingBackend);
    }
}
