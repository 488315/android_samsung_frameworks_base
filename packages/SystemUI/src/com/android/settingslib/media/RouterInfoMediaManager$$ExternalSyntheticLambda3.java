package com.android.settingslib.media;

import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import com.android.settingslib.media.InfoMediaManager;
import java.util.HashMap;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class RouterInfoMediaManager$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RouterInfoMediaManager$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj;
                ((HashMap) obj2).put(mediaRoute2Info.getId(), mediaRoute2Info);
                break;
            case 1:
                MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) obj;
                ((HashMap) obj2).put(mediaRoute2Info2.getId(), mediaRoute2Info2);
                break;
            case 2:
                MediaRoute2Info mediaRoute2Info3 = (MediaRoute2Info) obj;
                ((HashMap) obj2).put(mediaRoute2Info3.getId(), mediaRoute2Info3);
                break;
            default:
                RouterInfoMediaManager routerInfoMediaManager = (RouterInfoMediaManager) obj2;
                InfoMediaManager.Api34Impl.onRouteListingPreferenceUpdated((RouteListingPreference) obj, routerInfoMediaManager.mPreferenceItemMap);
                routerInfoMediaManager.refreshDevices();
                break;
        }
    }
}
