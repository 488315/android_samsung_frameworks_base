package android.media;

import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MediaDrm$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ MediaDrm f$0;

    public /* synthetic */ MediaDrm$$ExternalSyntheticLambda1(MediaDrm mediaDrm) {
        this.f$0 = mediaDrm;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Consumer createOnKeyStatusChangeListener;
        createOnKeyStatusChangeListener =
                this.f$0.createOnKeyStatusChangeListener((MediaDrm.OnKeyStatusChangeListener) obj);
        return createOnKeyStatusChangeListener;
    }
}
