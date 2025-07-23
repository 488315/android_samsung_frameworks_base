package com.android.systemui.qs.customize;

import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSCustomizerAnimator {
    public static final Companion Companion = new Companion(null);
    public static View mainView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static ListBuilder getActiveTileContents(View view) {
            ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
            createListBuilder.add(view.findViewById(R.id.qs_customize_top_summary_buttons));
            createListBuilder.add(view.findViewById(R.id.qs_active_page_parent));
            return createListBuilder.build();
        }

        public static ListBuilder getAvailableTileContents(View view) {
            ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
            if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
                createListBuilder.add(view.findViewById(R.id.qs_available_area));
            } else {
                createListBuilder.add(view.findViewById(R.id.qs_edit_available_text));
                createListBuilder.add(view.findViewById(R.id.qs_available_page_parent));
                createListBuilder.add(view.findViewById(R.id.qs_available_paged_indicator_container));
            }
            return createListBuilder.build();
        }

        private Companion() {
        }
    }
}
