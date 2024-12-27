package com.android.server.wm;

import android.view.SurfaceControl;
import android.window.DisplayAreaAppearedInfo;
import android.window.IDisplayAreaOrganizer;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class DisplayAreaOrganizerController$$ExternalSyntheticLambda5
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayAreaOrganizerController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ List f$2;
    public final /* synthetic */ IDisplayAreaOrganizer f$3;

    public /* synthetic */ DisplayAreaOrganizerController$$ExternalSyntheticLambda5(
            DisplayAreaOrganizerController displayAreaOrganizerController,
            int i,
            List list,
            IDisplayAreaOrganizer iDisplayAreaOrganizer,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = displayAreaOrganizerController;
        this.f$1 = i;
        this.f$2 = list;
        this.f$3 = iDisplayAreaOrganizer;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DisplayAreaOrganizerController displayAreaOrganizerController = this.f$0;
                int i = this.f$1;
                List list = this.f$2;
                IDisplayAreaOrganizer iDisplayAreaOrganizer = this.f$3;
                DisplayArea displayArea = (DisplayArea) obj;
                displayAreaOrganizerController.getClass();
                if (displayArea.mFeatureId == i) {
                    displayArea.setOrganizer(iDisplayAreaOrganizer, true);
                    list.add(
                            new DisplayAreaAppearedInfo(
                                    displayArea.getDisplayAreaInfo(),
                                    new SurfaceControl(
                                            displayArea.getSurfaceControl(),
                                            "DisplayAreaOrganizerController.registerOrganizer")));
                    break;
                }
                break;
            default:
                DisplayAreaOrganizerController displayAreaOrganizerController2 = this.f$0;
                int i2 = this.f$1;
                List list2 = this.f$2;
                IDisplayAreaOrganizer iDisplayAreaOrganizer2 = this.f$3;
                DisplayContent displayContent = (DisplayContent) obj;
                displayAreaOrganizerController2.getClass();
                if (!displayContent.mDisplay.isTrusted()) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[3]) {
                        ProtoLogImpl_54989576.w(
                                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                -3066370283926570943L,
                                1,
                                null,
                                Long.valueOf(displayContent.mDisplayId));
                        break;
                    }
                } else {
                    displayContent.forAllDisplayAreas(
                            new DisplayAreaOrganizerController$$ExternalSyntheticLambda5(
                                    displayAreaOrganizerController2,
                                    i2,
                                    list2,
                                    iDisplayAreaOrganizer2,
                                    0));
                    break;
                }
                break;
        }
    }
}
