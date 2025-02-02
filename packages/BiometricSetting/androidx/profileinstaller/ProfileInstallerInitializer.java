package androidx.profileinstaller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/* loaded from: classes.dex */
public class ProfileInstallerInitializer implements Initializer<Result> {

    public static class Result {
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.profileinstaller.ProfileInstallerInitializer$$ExternalSyntheticLambda0] */
    @Override // androidx.startup.Initializer
    public final Result create(Context context) {
        final Context applicationContext = context.getApplicationContext();
        final ?? r0 = new Runnable() { // from class: androidx.profileinstaller.ProfileInstallerInitializer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProfileInstallerInitializer profileInstallerInitializer = ProfileInstallerInitializer.this;
                Context context2 = applicationContext;
                profileInstallerInitializer.getClass();
                Handler.createAsync(Looper.getMainLooper()).postDelayed(new ProfileInstallerInitializer$$ExternalSyntheticLambda1(context2, 0), new Random().nextInt(Math.max(1000, 1)) + 5000);
            }
        };
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() { // from class: androidx.profileinstaller.ProfileInstallerInitializer$Choreographer16Impl$$ExternalSyntheticLambda0
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                r0.run();
            }
        });
        return new Result();
    }

    @Override // androidx.startup.Initializer
    public final List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
