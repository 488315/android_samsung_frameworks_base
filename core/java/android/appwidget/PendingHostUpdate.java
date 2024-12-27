package android.appwidget;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;

/* loaded from: classes.dex */
public class PendingHostUpdate implements Parcelable {
    public static final Parcelable.Creator<PendingHostUpdate> CREATOR =
            new Parcelable.Creator<
                    PendingHostUpdate>() { // from class: android.appwidget.PendingHostUpdate.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PendingHostUpdate createFromParcel(Parcel parcel) {
                    return new PendingHostUpdate(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PendingHostUpdate[] newArray(int size) {
                    return new PendingHostUpdate[size];
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

    public static PendingHostUpdate updateAppWidget(int appWidgetId, RemoteViews views) {
        PendingHostUpdate update = new PendingHostUpdate(appWidgetId, 0);
        update.views = views;
        return update;
    }

    public static PendingHostUpdate providerChanged(int appWidgetId, AppWidgetProviderInfo info) {
        PendingHostUpdate update = new PendingHostUpdate(appWidgetId, 1);
        update.widgetInfo = info;
        return update;
    }

    public static PendingHostUpdate viewDataChanged(int appWidgetId, int viewId) {
        PendingHostUpdate update = new PendingHostUpdate(appWidgetId, 2);
        update.viewId = viewId;
        return update;
    }

    public static PendingHostUpdate appWidgetRemoved(int appWidgetId) {
        return new PendingHostUpdate(appWidgetId, 3);
    }

    private PendingHostUpdate(int appWidgetId, int type) {
        this.appWidgetId = appWidgetId;
        this.type = type;
    }

    private PendingHostUpdate(Parcel in) {
        this.appWidgetId = in.readInt();
        this.type = in.readInt();
        switch (this.type) {
            case 0:
                if (in.readInt() != 0) {
                    this.views = new RemoteViews(in);
                    break;
                }
                break;
            case 1:
                if (in.readInt() != 0) {
                    this.widgetInfo = new AppWidgetProviderInfo(in);
                    break;
                }
                break;
            case 2:
                this.viewId = in.readInt();
                break;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.appWidgetId);
        dest.writeInt(this.type);
        switch (this.type) {
            case 0:
                writeNullParcelable(this.views, dest, flags);
                break;
            case 1:
                writeNullParcelable(this.widgetInfo, dest, flags);
                break;
            case 2:
                dest.writeInt(this.viewId);
                break;
        }
    }

    private void writeNullParcelable(Parcelable p, Parcel dest, int flags) {
        if (p != null) {
            dest.writeInt(1);
            p.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
    }
}
