package com.android.systemui.qs.customize;

import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SecQSCustomizerAnimator {
    public static final Companion Companion = new Companion(null);
    public static View mainView;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static ListBuilder getActiveTileContents(View view) {
            ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
            listBuilderCreateListBuilder.add(view.findViewById(R.id.qs_customize_top_summary_buttons));
            listBuilderCreateListBuilder.add(view.findViewById(R.id.qs_active_page_parent));
            return listBuilderCreateListBuilder.build();
        }

        public static ListBuilder getAvailableTileContents(View view) {
            ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
            if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
                listBuilderCreateListBuilder.add(view.findViewById(R.id.qs_available_area));
            } else {
                listBuilderCreateListBuilder.add(view.findViewById(R.id.qs_edit_available_text));
                listBuilderCreateListBuilder.add(view.findViewById(R.id.qs_available_page_parent));
                listBuilderCreateListBuilder.add(view.findViewById(R.id.qs_available_paged_indicator_container));
            }
            return listBuilderCreateListBuilder.build();
        }

        private Companion() {
        }
    }
}
