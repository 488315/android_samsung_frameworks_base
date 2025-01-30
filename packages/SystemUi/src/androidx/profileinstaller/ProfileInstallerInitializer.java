package androidx.profileinstaller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ProfileInstallerInitializer implements Initializer {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Result {
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.profileinstaller.ProfileInstallerInitializer$$ExternalSyntheticLambda0] */
    @Override // androidx.startup.Initializer
    public final Object create(Context context) {
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
    public final List dependencies() {
        return Collections.emptyList();
    }
}
