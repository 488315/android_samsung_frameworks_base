package androidx.mediarouter.media;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouteDescriptor {
    public final Bundle mBundle;
    public List mControlFilters;
    public List mGroupMemberIds;

    public MediaRouteDescriptor(Bundle bundle) {
        this.mBundle = bundle;
    }

    public final void ensureControlFilters() {
        if (this.mControlFilters == null) {
            ArrayList parcelableArrayList = this.mBundle.getParcelableArrayList("controlFilters");
            this.mControlFilters = parcelableArrayList;
            if (parcelableArrayList == null) {
                this.mControlFilters = Collections.emptyList();
            }
        }
    }

    public final List getGroupMemberIds() {
        if (this.mGroupMemberIds == null) {
            ArrayList<String> stringArrayList = this.mBundle.getStringArrayList("groupMemberIds");
            this.mGroupMemberIds = stringArrayList;
            if (stringArrayList == null) {
                this.mGroupMemberIds = Collections.emptyList();
            }
        }
        return this.mGroupMemberIds;
    }

    public final Uri getIconUri() {
        String string = this.mBundle.getString("iconUri");
        if (string == null) {
            return null;
        }
        return Uri.parse(string);
    }

    public final String getId() {
        return this.mBundle.getString("id");
    }

    public final boolean isValid() {
        ensureControlFilters();
        return (TextUtils.isEmpty(getId()) || TextUtils.isEmpty(this.mBundle.getString("name")) || this.mControlFilters.contains(null)) ? false : true;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MediaRouteDescriptor{ id=");
        sb.append(getId());
        sb.append(", groupMemberIds=");
        sb.append(getGroupMemberIds());
        sb.append(", name=");
        Bundle bundle = this.mBundle;
        sb.append(bundle.getString("name"));
        sb.append(", description=");
        sb.append(bundle.getString(IMSParameter.CALL.STATUS));
        sb.append(", iconUri=");
        sb.append(getIconUri());
        sb.append(", isEnabled=");
        sb.append(bundle.getBoolean("enabled", true));
        sb.append(", connectionState=");
        sb.append(bundle.getInt(IMSParameter.GENERAL.CONNECTION_STATE, 0));
        sb.append(", controlFilters=");
        ensureControlFilters();
        sb.append(Arrays.toString(this.mControlFilters.toArray()));
        sb.append(", playbackType=");
        sb.append(bundle.getInt("playbackType", 1));
        sb.append(", playbackStream=");
        sb.append(bundle.getInt("playbackStream", -1));
        sb.append(", deviceType=");
        sb.append(bundle.getInt("deviceType"));
        sb.append(", volume=");
        sb.append(bundle.getInt("volume"));
        sb.append(", volumeMax=");
        sb.append(bundle.getInt("volumeMax"));
        sb.append(", volumeHandling=");
        sb.append(bundle.getInt("volumeHandling", 0));
        sb.append(", presentationDisplayId=");
        sb.append(bundle.getInt("presentationDisplayId", -1));
        sb.append(", extras=");
        sb.append(bundle.getBundle("extras"));
        sb.append(", isValid=");
        sb.append(isValid());
        sb.append(", minClientVersion=");
        sb.append(bundle.getInt("minClientVersion", 1));
        sb.append(", maxClientVersion=");
        sb.append(bundle.getInt("maxClientVersion", Integer.MAX_VALUE));
        sb.append(" }");
        return sb.toString();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final Bundle mBundle;
        public ArrayList mControlFilters;
        public ArrayList mGroupMemberIds;

        public Builder(String str, String str2) {
            Bundle bundle = new Bundle();
            this.mBundle = bundle;
            if (str == null) {
                throw new NullPointerException("id must not be null");
            }
            bundle.putString("id", str);
            if (str2 == null) {
                throw new NullPointerException("name must not be null");
            }
            bundle.putString("name", str2);
        }

        public final void addControlFilters(Collection collection) {
            if (collection == null) {
                throw new IllegalArgumentException("filters must not be null");
            }
            if (collection.isEmpty()) {
                return;
            }
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                IntentFilter intentFilter = (IntentFilter) it.next();
                if (intentFilter != null) {
                    if (this.mControlFilters == null) {
                        this.mControlFilters = new ArrayList();
                    }
                    if (!this.mControlFilters.contains(intentFilter)) {
                        this.mControlFilters.add(intentFilter);
                    }
                }
            }
        }

        public final MediaRouteDescriptor build() {
            ArrayList<? extends Parcelable> arrayList = this.mControlFilters;
            Bundle bundle = this.mBundle;
            if (arrayList != null) {
                bundle.putParcelableArrayList("controlFilters", arrayList);
            }
            ArrayList<String> arrayList2 = this.mGroupMemberIds;
            if (arrayList2 != null) {
                bundle.putStringArrayList("groupMemberIds", arrayList2);
            }
            return new MediaRouteDescriptor(bundle);
        }

        public Builder(MediaRouteDescriptor mediaRouteDescriptor) {
            if (mediaRouteDescriptor != null) {
                this.mBundle = new Bundle(mediaRouteDescriptor.mBundle);
                if (!mediaRouteDescriptor.getGroupMemberIds().isEmpty()) {
                    this.mGroupMemberIds = new ArrayList(mediaRouteDescriptor.getGroupMemberIds());
                }
                mediaRouteDescriptor.ensureControlFilters();
                if (mediaRouteDescriptor.mControlFilters.isEmpty()) {
                    return;
                }
                this.mControlFilters = new ArrayList(mediaRouteDescriptor.mControlFilters);
                return;
            }
            throw new IllegalArgumentException("descriptor must not be null");
        }
    }
}
