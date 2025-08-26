package com.android.wm.shell.desktopmode.education;

import android.content.Context;
import com.android.wm.shell.desktopmode.education.data.AppToWebEducationDatastoreRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class AppToWebEducationFilter {
    public final AppToWebEducationDatastoreRepository appToWebEducationDatastoreRepository;
    public final Context context;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AppToWebEducationFilter(Context context, AppToWebEducationDatastoreRepository appToWebEducationDatastoreRepository) {
        this.context = context;
        this.appToWebEducationDatastoreRepository = appToWebEducationDatastoreRepository;
    }
}
