package com.android.systemui.statusbar.notification.collection.init;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManagerFactory;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifPipelineInitializer implements Dumpable, PipelineDumpable {
    public final DumpManager mDumpManager;
    public final GroupCoalescer mGroupCoalescer;
    public final ShadeListBuilder mListBuilder;
    public final NotifCollection mNotifCollection;
    public final NotifInflaterImpl mNotifInflater;
    public final NotifCoordinators mNotifPluggableCoordinators;
    public NotificationListener mNotificationService;
    public final NotifPipeline mPipelineWrapper;
    public final RenderStageManager mRenderStageManager;
    public ShadeViewManager mShadeViewManager;
    public final ShadeViewManagerFactory mShadeViewManagerFactory;

    public NotifPipelineInitializer(NotifPipeline notifPipeline, GroupCoalescer groupCoalescer, NotifCollection notifCollection, ShadeListBuilder shadeListBuilder, RenderStageManager renderStageManager, NotifCoordinators notifCoordinators, NotifInflaterImpl notifInflaterImpl, DumpManager dumpManager, ShadeViewManagerFactory shadeViewManagerFactory) {
        this.mPipelineWrapper = notifPipeline;
        this.mGroupCoalescer = groupCoalescer;
        this.mNotifCollection = notifCollection;
        this.mListBuilder = shadeListBuilder;
        this.mRenderStageManager = renderStageManager;
        this.mNotifPluggableCoordinators = notifCoordinators;
        this.mDumpManager = dumpManager;
        this.mNotifInflater = notifInflaterImpl;
        this.mShadeViewManagerFactory = shadeViewManagerFactory;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        dumpPipeline(new PipelineDumper(printWriter));
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.println("STAGE 0: SETUP");
        pipelineDumper.dump(this.mNotifPluggableCoordinators, "notifPluggableCoordinators");
        pipelineDumper.println("");
        pipelineDumper.println("STAGE 1: LISTEN");
        pipelineDumper.dump(this.mNotificationService, "notificationService");
        pipelineDumper.println("");
        pipelineDumper.println("STAGE 2: BATCH EVENTS");
        pipelineDumper.dump(this.mGroupCoalescer, "groupCoalescer");
        pipelineDumper.println("");
        pipelineDumper.println("STAGE 3: COLLECT");
        pipelineDumper.dump(this.mNotifCollection, "notifCollection");
        pipelineDumper.println("");
        pipelineDumper.println("STAGE 4: BUILD LIST");
        pipelineDumper.dump(this.mListBuilder, "listBuilder");
        pipelineDumper.println("");
        pipelineDumper.println("STAGE 5: DISPATCH RENDER");
        pipelineDumper.dump(this.mRenderStageManager, "renderStageManager");
        pipelineDumper.println("");
        pipelineDumper.println("STAGE 6: UPDATE SHADE");
        pipelineDumper.dump(this.mShadeViewManager, "shadeViewManager");
    }
}
