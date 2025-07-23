package com.samsung.android.wallpaper.live.sdk.provider.call;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SemSystemProperties;
import android.util.Log;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.wallpaper.live.sdk.provider.ProviderCallParams;
import com.samsung.android.wallpaper.live.sdk.provider.ProviderCallResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GetThumbnail {
    public static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Result extends ProviderCallResult {
        public final ParcelFileDescriptor mThumbnailFileDescriptor;

        public Result(ParcelFileDescriptor parcelFileDescriptor) {
            this.mThumbnailFileDescriptor = parcelFileDescriptor;
        }

        @Override // com.samsung.android.wallpaper.live.sdk.provider.ProviderCallResult
        public final Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putParcelable("thumbnail_file_descriptor", this.mThumbnailFileDescriptor);
            return bundle;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Params extends ProviderCallParams {
        public final int rotation;
        public final int sourceWhich;
        public final int userId;
        public final int wallpaperId;
        public final int which;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public enum ClockType {
            /* JADX INFO: Fake field, exist only in values array */
            DEFAULT,
            /* JADX INFO: Fake field, exist only in values array */
            CURRENT,
            /* JADX INFO: Fake field, exist only in values array */
            CUSTOM
        }

        public Params(int i, String str, Bundle bundle) {
            super(null);
            this.wallpaperId = -1;
            this.which = i;
            this.sourceWhich = i;
            this.userId = 0;
            this.rotation = 0;
            ClockType[] clockTypeArr = ClockType.$VALUES;
        }

        public Params(Bundle bundle) {
            super(bundle);
            this.wallpaperId = bundle.getInt("wallpaper_id", -1);
            int i = bundle.getInt("which", 5);
            this.which = i;
            this.sourceWhich = bundle.getInt("source_which", i);
            this.userId = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, 0);
            this.rotation = bundle.getInt("rotation", 0);
            bundle.getString("wallpaper_service_class_name");
            bundle.getBundle("service_settings");
            "current".equals(bundle.getString(SubRoom.EXTRA_VALUE_CLOCK));
            ClockType[] clockTypeArr = ClockType.$VALUES;
            if (GetThumbnail.DEBUG) {
                for (String str : bundle.keySet()) {
                    Log.d("GetThumbnail", "Params: Key = " + str + ", Value = " + bundle.get(str));
                }
            }
        }
    }
}
