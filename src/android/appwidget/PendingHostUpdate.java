package android.appwidget;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;

/* loaded from: classes.dex */
public class PendingHostUpdate implements Parcelable {
    public static final Parcelable.Creator<PendingHostUpdate> CREATOR = new Parcelable.Creator<PendingHostUpdate>() { // from class: android.appwidget.PendingHostUpdate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PendingHostUpdate createFromParcel(Parcel parcel) {
            return new PendingHostUpdate(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PendingHostUpdate[] newArray(int i) {
            return new PendingHostUpdate[i];
        }
    };
    static final int TYPE_APP_WIDGET_REMOVED = 3;
    static final int TYPE_PROVIDER_CHANGED = 1;
    static final int TYPE_VIEWS_UPDATE = 0;
    static final int TYPE_VIEW_DATA_CHANGED = 2;
    final int appWidgetId;
    final int type;
    int viewId;
    RemoteViews views;
    AppWidgetProviderInfo widgetInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static PendingHostUpdate updateAppWidget(int i, RemoteViews remoteViews) {
        PendingHostUpdate pendingHostUpdate = new PendingHostUpdate(i, 0);
        pendingHostUpdate.views = remoteViews;
        return pendingHostUpdate;
    }

    public static PendingHostUpdate providerChanged(int i, AppWidgetProviderInfo appWidgetProviderInfo) {
        PendingHostUpdate pendingHostUpdate = new PendingHostUpdate(i, 1);
        pendingHostUpdate.widgetInfo = appWidgetProviderInfo;
        return pendingHostUpdate;
    }

    public static PendingHostUpdate viewDataChanged(int i, int i2) {
        PendingHostUpdate pendingHostUpdate = new PendingHostUpdate(i, 2);
        pendingHostUpdate.viewId = i2;
        return pendingHostUpdate;
    }

    public static PendingHostUpdate appWidgetRemoved(int i) {
        return new PendingHostUpdate(i, 3);
    }

    private PendingHostUpdate(int i, int i2) {
        this.appWidgetId = i;
        this.type = i2;
    }

    private PendingHostUpdate(Parcel parcel) {
        this.appWidgetId = parcel.readInt();
        int i = parcel.readInt();
        this.type = i;
        if (i == 0) {
            if (parcel.readInt() != 0) {
                this.views = new RemoteViews(parcel);
            }
        } else if (i != 1) {
            if (i != 2) {
                return;
            }
            this.viewId = parcel.readInt();
        } else if (parcel.readInt() != 0) {
            this.widgetInfo = new AppWidgetProviderInfo(parcel);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.appWidgetId);
        parcel.writeInt(this.type);
        int i2 = this.type;
        if (i2 == 0) {
            writeNullParcelable(this.views, parcel, i);
        } else if (i2 == 1) {
            writeNullParcelable(this.widgetInfo, parcel, i);
        } else {
            if (i2 != 2) {
                return;
            }
            parcel.writeInt(this.viewId);
        }
    }

    private void writeNullParcelable(Parcelable parcelable, Parcel parcel, int i) {
        if (parcelable != null) {
            parcel.writeInt(1);
            parcelable.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
    }
}
