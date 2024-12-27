package com.samsung.android.server.packagefeature.core;

import com.samsung.android.server.corescpm.ScpmController;
import com.samsung.android.server.corescpm.ScpmControllerImpl;

import java.util.function.Function;

public final /* synthetic */ class PackageFeatureManagerService$$ExternalSyntheticLambda1
        implements Function {
    public final /* synthetic */ ScpmController f$0;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String str = (String) obj;
        ScpmControllerImpl scpmControllerImpl = (ScpmControllerImpl) this.f$0;
        synchronized (scpmControllerImpl.mLock) {
            try {
                if (!scpmControllerImpl.mStarted) {
                    return null;
                }
                try {
                    return scpmControllerImpl.getFileDescriptorInternal(str);
                } catch (Throwable th) {
                    scpmControllerImpl.mLogger.log(6, "Failed to getFileDescriptor from SCPM.", th);
                    return null;
                }
            } finally {
            }
        }
    }
}
