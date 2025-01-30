package androidx.picker.features.scs;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppDataListSCSFactory extends AppDataListBixbyFactory {
    public AppDataListSCSFactory(Context context) {
        super(context);
    }

    @Override // androidx.picker.features.scs.AppDataListBixbyFactory
    public final String getAuthority() {
        return "com.samsung.android.scs.ai.search/v1";
    }

    @Override // androidx.picker.features.scs.AppDataListBixbyFactory, androidx.picker.features.scs.AbstractAppDataListFactory, androidx.picker.common.log.LogTag
    public final String getLogTag() {
        return "AppDataListSCSFactory";
    }
}
