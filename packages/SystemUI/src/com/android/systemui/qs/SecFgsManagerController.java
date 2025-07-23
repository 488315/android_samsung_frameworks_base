package com.android.systemui.qs;

import android.util.Log;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecFgsManagerController {
    public final Function0 dialog;
    public TextView noItemTextView;
    public RecyclerView recyclerView;
    public final Function1 updateDialog;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public SecFgsManagerController(Function1 function1, Function0 function0, Function0 function02) {
        this.updateDialog = function1;
        this.dialog = function0;
        function02.invoke();
    }

    public static void log(String str) {
        Log.d("SecFgsManagerController", str);
    }
}
