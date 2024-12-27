package android.hardware.camera2;

import java.util.function.Predicate;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isPublicId;
        isPublicId = CameraManager.isPublicId((String) obj);
        return isPublicId;
    }
}
