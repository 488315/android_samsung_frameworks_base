package com.android.systemui.qs.customize;

import android.view.View;
import com.android.systemui.R;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SecQSCustomizerAnimator {
    public static final Companion Companion = new Companion(null);
    public static View mainView;

    public final class Companion {
        private Companion() {
        }

        public static ListBuilder getActiveTileContents(View view) {
            ListBuilder listBuilder = new ListBuilder();
            listBuilder.add(view.findViewById(R.id.qs_customize_top_summary_buttons));
            listBuilder.add(view.findViewById(R.id.qs_active_page_parent));
            return listBuilder.build();
        }

        public static ListBuilder getAvailableTileContents(View view) {
            ListBuilder listBuilder = new ListBuilder();
            listBuilder.add(view.findViewById(R.id.qs_edit_available_text));
            listBuilder.add(view.findViewById(R.id.qs_available_page_parent));
            listBuilder.add(view.findViewById(R.id.qs_available_paged_indicator_container));
            return listBuilder.build();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
