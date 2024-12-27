package android.hardware.camera2;

import java.util.function.Predicate;

public final /* synthetic */ class CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isPublicId;
        isPublicId = CameraManager.isPublicId((String) obj);
        return isPublicId;
    }
}
