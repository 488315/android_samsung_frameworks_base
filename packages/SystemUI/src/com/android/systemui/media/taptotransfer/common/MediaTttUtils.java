package com.android.systemui.media.taptotransfer.common;

import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.media.taptotransfer.common.MediaTttIcon;
import com.android.systemui.temporarydisplay.chipbar.ChipbarInfo;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaTttUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static IconInfo getIconInfoFromPackageName(Context context, String str, boolean z, Function0 function0) {
            if (str != null) {
                PackageManager packageManager = context.getPackageManager();
                try {
                    String obj = packageManager.getApplicationInfo(str, PackageManager.ApplicationInfoFlags.of(0L)).loadLabel(packageManager).toString();
                    return new IconInfo(z ? new ContentDescription.Loaded(context.getString(R.string.media_transfer_receiver_content_description_with_app_name, obj)) : new ContentDescription.Loaded(obj), new MediaTttIcon.Loaded(packageManager.getApplicationIcon(str)), null, true);
                } catch (PackageManager.NameNotFoundException unused) {
                    function0.invoke();
                }
            }
            ContentDescription.Resource resource = z ? new ContentDescription.Resource(R.string.media_transfer_receiver_content_description_unknown_app) : new ContentDescription.Resource(R.string.media_output_dialog_unknown_launch_app_name);
            MediaTttIcon.Resource resource2 = new MediaTttIcon.Resource(R.drawable.ic_cast);
            ChipbarInfo.Companion.getClass();
            return new IconInfo(resource, resource2, Integer.valueOf(ChipbarInfo.DEFAULT_ICON_TINT), false);
        }

        private Companion() {
        }
    }
}
