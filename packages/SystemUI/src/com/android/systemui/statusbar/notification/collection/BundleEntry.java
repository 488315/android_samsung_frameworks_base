package com.android.systemui.statusbar.notification.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BundleEntry extends PipelineEntry {
    public static final Companion Companion = null;
    public static final List ROOT_BUNDLES = null;
    public final List _children;
    public final List children;
    public final StateFlowImpl isSensitive;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Arrays.asList(new BundleEntry("android.app.promotions"), new BundleEntry("android.app.social"), new BundleEntry("android.app.news"), new BundleEntry("android.app.recs"));
    }

    public BundleEntry(String str) {
        super(str);
        this.isSensitive = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        ArrayList arrayList = new ArrayList();
        this._children = arrayList;
        this.children = Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineEntry
    public final PipelineEntry getParent() {
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineEntry
    public final NotificationEntry getRepresentativeEntry() {
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineEntry
    public final boolean wasAttachedInPreviousPass() {
        return false;
    }
}
