package com.android.systemui.qs.customize;

import android.util.Log;
import android.view.View;
import java.util.HashMap;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class CustomActionManager {
    public final HashMap customActions = new HashMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public final void performAction(View view, CustomActionId customActionId) {
        Consumer consumer = (Consumer) this.customActions.get(customActionId);
        Log.d("CustomActionManager", "performAction actionId:" + customActionId + ", view=" + view + ", actions=" + this.customActions);
        if (consumer != null) {
            consumer.accept(view);
        }
    }

    public final void setCustomAction(CustomActionId customActionId, Consumer consumer) {
        if (this.customActions.containsKey(customActionId)) {
            return;
        }
        this.customActions.put(customActionId, consumer);
    }
}
