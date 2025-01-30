package androidx.mediarouter.media;

import android.media.MediaRoute2Info;
import android.net.Uri;
import android.os.Bundle;
import androidx.mediarouter.media.MediaRouteDescriptor;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouter2Utils {
    private MediaRouter2Utils() {
    }

    public static List getRouteIds(List list) {
        if (list == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) it.next();
            if (mediaRoute2Info != null) {
                arrayList.add(mediaRoute2Info.getId());
            }
        }
        return arrayList;
    }

    public static MediaRouteDescriptor toMediaRouteDescriptor(MediaRoute2Info mediaRoute2Info) {
        if (mediaRoute2Info == null) {
            return null;
        }
        MediaRouteDescriptor.Builder builder = new MediaRouteDescriptor.Builder(mediaRoute2Info.getId(), mediaRoute2Info.getName().toString());
        int connectionState = mediaRoute2Info.getConnectionState();
        Bundle bundle = builder.mBundle;
        bundle.putInt(IMSParameter.GENERAL.CONNECTION_STATE, connectionState);
        bundle.putInt("volumeHandling", mediaRoute2Info.getVolumeHandling());
        bundle.putInt("volumeMax", mediaRoute2Info.getVolumeMax());
        bundle.putInt("volume", mediaRoute2Info.getVolume());
        Bundle extras = mediaRoute2Info.getExtras();
        if (extras == null) {
            bundle.putBundle("extras", null);
        } else {
            bundle.putBundle("extras", new Bundle(extras));
        }
        bundle.putBoolean("enabled", true);
        bundle.putBoolean("canDisconnect", false);
        CharSequence description = mediaRoute2Info.getDescription();
        if (description != null) {
            bundle.putString(IMSParameter.CALL.STATUS, description.toString());
        }
        Uri iconUri = mediaRoute2Info.getIconUri();
        if (iconUri != null) {
            bundle.putString("iconUri", iconUri.toString());
        }
        Bundle extras2 = mediaRoute2Info.getExtras();
        if (extras2 == null || !extras2.containsKey("androidx.mediarouter.media.KEY_EXTRAS") || !extras2.containsKey("androidx.mediarouter.media.KEY_DEVICE_TYPE") || !extras2.containsKey("androidx.mediarouter.media.KEY_CONTROL_FILTERS")) {
            return null;
        }
        Bundle bundle2 = extras2.getBundle("androidx.mediarouter.media.KEY_EXTRAS");
        if (bundle2 == null) {
            bundle.putBundle("extras", null);
        } else {
            bundle.putBundle("extras", new Bundle(bundle2));
        }
        bundle.putInt("deviceType", extras2.getInt("androidx.mediarouter.media.KEY_DEVICE_TYPE", 0));
        bundle.putInt("playbackType", extras2.getInt("androidx.mediarouter.media.KEY_PLAYBACK_TYPE", 1));
        ArrayList parcelableArrayList = extras2.getParcelableArrayList("androidx.mediarouter.media.KEY_CONTROL_FILTERS");
        if (parcelableArrayList != null) {
            builder.addControlFilters(parcelableArrayList);
        }
        return builder.build();
    }
}
