package androidx.picker.features.scs;

import androidx.picker.common.log.LogTag;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbstractAppDataListFactory implements LogTag {
    public static final C03561 EMPTY_FACTORY = new AbstractAppDataListFactory() { // from class: androidx.picker.features.scs.AbstractAppDataListFactory.1
        @Override // androidx.picker.features.scs.AbstractAppDataListFactory
        public final List getDataList() {
            return Collections.emptyList();
        }
    };

    public abstract List getDataList();

    @Override // androidx.picker.common.log.LogTag
    public String getLogTag() {
        return "AbstractAppDataListFactory";
    }
}
