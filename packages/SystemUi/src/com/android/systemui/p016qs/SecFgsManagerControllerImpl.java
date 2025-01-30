package com.android.systemui.p016qs;

import android.util.Log;
import android.widget.TextView;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecFgsManagerControllerImpl {
    public final Consumer dialogConsumer;
    public final Supplier dialogSupplier;
    public TextView noItemTextView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SecFgsManagerControllerImpl(Consumer<SystemUIDialog> consumer, Supplier<SystemUIDialog> supplier, Runnable runnable) {
        this.dialogConsumer = consumer;
        this.dialogSupplier = supplier;
        runnable.run();
    }

    public final void log(String str) {
        Log.d("SecFgsManagerController", str);
    }
}
