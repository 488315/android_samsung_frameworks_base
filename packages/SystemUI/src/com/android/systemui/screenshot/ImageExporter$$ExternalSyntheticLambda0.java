package com.android.systemui.screenshot;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.systemui.screenshot.ImageExporter;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final /* synthetic */ class ImageExporter$$ExternalSyntheticLambda0 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ Executor f$0;
    public final /* synthetic */ ImageExporter.Task f$1;

    public /* synthetic */ ImageExporter$$ExternalSyntheticLambda0(Executor executor, ImageExporter.Task task) {
        this.f$0 = executor;
        this.f$1 = task;
    }

    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
    public final Object attachCompleter(final CallbackToFutureAdapter.Completer completer) {
        Executor executor = this.f$0;
        String str = ImageExporter.mImageFileRelativePath;
        final ImageExporter.Task task = this.f$1;
        executor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CallbackToFutureAdapter.Completer completer2 = completer;
                ImageExporter.Task task2 = task;
                String str2 = ImageExporter.mImageFileRelativePath;
                Thread.currentThread().setPriority(10);
                try {
                    completer2.set(task2.execute());
                } catch (ImageExporter.ImageExportException | InterruptedException e) {
                    completer2.setException(e);
                }
            }
        });
        return task;
    }
}
