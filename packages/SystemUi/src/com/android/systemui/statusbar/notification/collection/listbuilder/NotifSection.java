package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifSection implements PipelineDumpable {
    public final int bucket;
    public final NotifComparator comparator;
    public final NodeController headerController;
    public final int index;
    public final String label;
    public final NotifSectioner sectioner;

    public NotifSection(NotifSectioner notifSectioner, int i) {
        this.sectioner = notifSectioner;
        this.index = i;
        int i2 = notifSectioner.mBucket;
        this.bucket = i2;
        this.label = i + ":" + i2 + ":" + notifSectioner.mName;
        this.headerController = notifSectioner.getHeaderNodeController();
        this.comparator = notifSectioner.getComparator();
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(Integer.valueOf(this.index), "index");
        pipelineDumper.dump(Integer.valueOf(this.bucket), "bucket");
        pipelineDumper.dump(this.sectioner, "sectioner");
        pipelineDumper.dump(this.headerController, "headerController");
        pipelineDumper.dump(this.comparator, "comparator");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotifSection)) {
            return false;
        }
        NotifSection notifSection = (NotifSection) obj;
        return Intrinsics.areEqual(this.sectioner, notifSection.sectioner) && this.index == notifSection.index;
    }

    public final int hashCode() {
        return Integer.hashCode(this.index) + (this.sectioner.hashCode() * 31);
    }

    public final String toString() {
        return "NotifSection(sectioner=" + this.sectioner + ", index=" + this.index + ")";
    }
}
