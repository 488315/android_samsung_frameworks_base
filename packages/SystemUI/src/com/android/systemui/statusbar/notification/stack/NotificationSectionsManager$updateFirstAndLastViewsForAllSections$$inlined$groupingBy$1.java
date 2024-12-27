package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.Iterator;
import kotlin.collections.Grouping;
import kotlin.sequences.Sequence;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
