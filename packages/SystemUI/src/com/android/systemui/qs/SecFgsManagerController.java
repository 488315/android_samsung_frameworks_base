package com.android.systemui.qs;

import android.util.Log;
import android.widget.TextView;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SecFgsManagerController {
    public final Consumer dialogConsumer;
    public final Supplier dialogSupplier;
    public TextView noItemTextView;

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

    public SecFgsManagerController(Consumer<SystemUIDialog> consumer, Supplier<SystemUIDialog> supplier, Runnable runnable) {
        this.dialogConsumer = consumer;
        this.dialogSupplier = supplier;
        runnable.run();
    }

    public static void log(String str) {
        Log.d("SecFgsManagerController", str);
    }
}
