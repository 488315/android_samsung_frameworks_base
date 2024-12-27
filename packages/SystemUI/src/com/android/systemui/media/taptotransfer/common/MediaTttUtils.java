package com.android.systemui.media.taptotransfer.common;

import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.media.taptotransfer.common.MediaTttIcon;
import com.android.systemui.temporarydisplay.chipbar.ChipbarInfo;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaTttUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
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

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
