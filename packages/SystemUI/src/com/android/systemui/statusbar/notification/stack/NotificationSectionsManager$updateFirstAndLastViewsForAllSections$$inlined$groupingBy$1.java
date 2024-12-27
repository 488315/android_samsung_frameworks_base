package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.Iterator;
import kotlin.collections.Grouping;
import kotlin.sequences.Sequence;

public final class NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1 implements Grouping {
    public final /* synthetic */ Sequence $this_groupingBy;
    public final /* synthetic */ NotificationSectionsManager this$0;

    public NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1(Sequence sequence, NotificationSectionsManager notificationSectionsManager) {
        this.$this_groupingBy = sequence;
        this.this$0 = notificationSectionsManager;
    }

    @Override // kotlin.collections.Grouping
    public final Object keyOf(Object obj) {
        SourceType$Companion$from$1 sourceType$Companion$from$1 = NotificationSectionsManager.SECTION;
        Integer bucket = this.this$0.getBucket((ExpandableView) obj);
        if (bucket != null) {
            return bucket;
        }
        throw new IllegalArgumentException("Cannot find section bucket for view");
    }

    @Override // kotlin.collections.Grouping
    public final Iterator sourceIterator() {
        return this.$this_groupingBy.iterator();
    }
}
