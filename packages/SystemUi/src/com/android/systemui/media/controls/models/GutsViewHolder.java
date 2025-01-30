package com.android.systemui.media.controls.models;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GutsViewHolder {
    public final View cancel;
    public final ViewGroup dismiss;

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
        SetsKt__SetsKt.setOf(Integer.valueOf(R.id.remove_text), Integer.valueOf(R.id.cancel), Integer.valueOf(R.id.dismiss), Integer.valueOf(R.id.settings));
    }

    public GutsViewHolder(View view) {
        this.cancel = view.requireViewById(R.id.cancel);
        this.dismiss = (ViewGroup) view.requireViewById(R.id.dismiss);
    }
}
