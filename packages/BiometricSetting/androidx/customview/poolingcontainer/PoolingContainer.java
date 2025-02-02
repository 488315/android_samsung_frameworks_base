package androidx.customview.poolingcontainer;

import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewGroupKt;
import androidx.core.view.ViewGroupKt$iterator$1;
import androidx.core.view.ViewKt;
import com.samsung.android.biometrics.app.setting.R;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PoolingContainer.kt */
/* loaded from: classes.dex */
public final class PoolingContainer {
    public static final void callPoolingContainerOnRelease() {
        Intrinsics.checkNotNullParameter(null, "<this>");
        ViewKt.getAllViews();
        throw null;
    }

    public static final void callPoolingContainerOnReleaseForChildren(ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        Iterator<View> it = ViewGroupKt.getChildren(viewGroup).iterator();
        while (true) {
            ViewGroupKt$iterator$1 viewGroupKt$iterator$1 = (ViewGroupKt$iterator$1) it;
            if (!viewGroupKt$iterator$1.hasNext()) {
                return;
            }
            View view = (View) viewGroupKt$iterator$1.next();
            PoolingContainerListenerHolder poolingContainerListenerHolder = (PoolingContainerListenerHolder) view.getTag(R.id.pooling_container_listener_holder_tag);
            if (poolingContainerListenerHolder == null) {
                poolingContainerListenerHolder = new PoolingContainerListenerHolder();
                view.setTag(R.id.pooling_container_listener_holder_tag, poolingContainerListenerHolder);
            }
            poolingContainerListenerHolder.onRelease();
        }
    }
}
