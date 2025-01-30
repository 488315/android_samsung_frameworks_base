package com.android.systemui.statusbar.pipeline.wifi.ui.model;

import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface WifiIcon extends Diffable {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Hidden implements WifiIcon {
        public static final Hidden INSTANCE = new Hidden();

        private Hidden() {
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (Intrinsics.areEqual(((WifiIcon) diffable).toString(), "hidden")) {
                return;
            }
            tableRowLoggerImpl.logChange("icon", "hidden");
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("icon", "hidden");
        }

        public final String toString() {
            return "hidden";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Visible implements WifiIcon {
        public final ContentDescription.Loaded contentDescription;
        public final Icon.Resource icon;

        public Visible(int i, ContentDescription.Loaded loaded) {
            this.contentDescription = loaded;
            this.icon = new Icon.Resource(i, loaded);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (Intrinsics.areEqual(((WifiIcon) diffable).toString(), toString())) {
                return;
            }
            tableRowLoggerImpl.logChange("icon", toString());
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("icon", toString());
        }

        public final String toString() {
            return String.valueOf(this.contentDescription.description);
        }
    }
}
