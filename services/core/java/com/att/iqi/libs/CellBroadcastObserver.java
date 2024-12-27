package com.att.iqi.libs;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.telephony.SmsCbMessage;
import android.text.TextUtils;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import com.att.iqi.lib.IQIManager;
import com.att.iqi.lib.metrics.ea.EA12;
import com.att.iqi.lib.metrics.ea.EA13;
import com.att.iqi.lib.metrics.ea.EA14;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CellBroadcastObserver extends ContentObserver {
    private static final String CELLBROADCASTS_APP_AUTHORITY = "cellbroadcasts-app";
    private static final String CELLBROADCASTS_AUTHORITY = "cellbroadcasts";
    private final Context mContext;
    private final IQIManager mIQIManager;
    private final Set pendingReadList;
    private static final Uri CELLBROADCASTS_CONTENT_URI = Uri.parse("content://cellbroadcasts/");
    private static final Uri CELLBROADCASTS_DISPLAYED_CONTENT_URI =
            Uri.parse("content://cellbroadcasts/displayed");
    private static final Uri CELLBROADCASTS_APP_CONTENT_URI =
            Uri.parse("content://cellbroadcasts-app/");

    public CellBroadcastObserver(Context context, Handler handler) {
        super(handler);
        this.pendingReadList = new HashSet();
        this.mContext = context;
        this.mIQIManager = IQIManager.getInstance();
    }

    private String buildQuerySelection(Set set) {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m("read=1 AND date IN (");
        Iterator it = set.iterator();
        while (it.hasNext()) {
            m.append((Long) it.next());
            m.append(",");
        }
        m.deleteCharAt(m.length() - 1);
        m.append(")");
        return m.toString();
    }

    private void checkAndMaybeReportReadMessages() {
        if (this.pendingReadList.size() >= 1) {
            try {
                Cursor query =
                        this.mContext
                                .getContentResolver()
                                .query(
                                        CELLBROADCASTS_APP_CONTENT_URI,
                                        null,
                                        buildQuerySelection(this.pendingReadList),
                                        null,
                                        null);
                while (query.moveToNext()) {
                    try {
                        long j = query.getLong(query.getColumnIndex("date"));
                        if (this.pendingReadList.contains(Long.valueOf(j))) {
                            int i = query.getInt(query.getColumnIndex("serial_number"));
                            this.mIQIManager.submitMetric(
                                    new EA14(
                                            query.getInt(query.getColumnIndex("service_category")),
                                            i));
                            this.pendingReadList.remove(Long.valueOf(j));
                            LogUtil.logd("CB with serial #: " + i + " read");
                        }
                    } finally {
                    }
                }
                query.close();
            } catch (Exception e) {
                LogUtil.logd("Error while querying " + CELLBROADCASTS_APP_CONTENT_URI, e);
            }
        }
    }

    private void reportNewMessageReceivedAndDisplayed(String str) {
        try {
            Cursor query =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    CELLBROADCASTS_CONTENT_URI,
                                    null,
                                    "_id=?",
                                    new String[] {str},
                                    null);
            while (query.moveToNext()) {
                try {
                    SmsCbMessage createFromCursor = SmsCbMessage.createFromCursor(query);
                    LogUtil.logd(
                            "New CB Message [ID: " + str + "] was displayed: " + createFromCursor);
                    EA12 ea12 = new EA12((Parcelable) createFromCursor);
                    EA13 ea13 =
                            new EA13(
                                    createFromCursor.getServiceCategory(),
                                    createFromCursor.getSerialNumber());
                    this.mIQIManager.submitMetric(ea12);
                    this.mIQIManager.submitMetric(ea13);
                    this.pendingReadList.add(Long.valueOf(createFromCursor.getReceivedTime()));
                } finally {
                }
            }
            query.close();
        } catch (Exception e) {
            LogUtil.loge("Error while querying " + CELLBROADCASTS_CONTENT_URI, e);
        }
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
        if (uri == null) {
            LogUtil.logd("Received null uri!");
            return;
        }
        LogUtil.logd("CellBroadcasterObserver received update: " + uri);
        String host = uri.getHost();
        if (!TextUtils.equals(host, CELLBROADCASTS_AUTHORITY)) {
            if (TextUtils.equals(host, CELLBROADCASTS_APP_AUTHORITY)) {
                checkAndMaybeReportReadMessages();
                return;
            }
            LogUtil.logd("Received callback on unknown URI: " + uri);
            return;
        }
        List<String> pathSegments = uri.getPathSegments();
        Iterator<String> it = pathSegments.iterator();
        while (it.hasNext()) {
            LogUtil.logd("URI segment: " + it.next());
        }
        if (pathSegments.size() >= 1) {
            String lastPathSegment = uri.getLastPathSegment();
            try {
                LogUtil.logd("Record ID " + Integer.parseInt(lastPathSegment));
                reportNewMessageReceivedAndDisplayed(lastPathSegment);
            } catch (Exception e) {
                LogUtil.loge("The last segment is not an integer!", e);
            }
        }
    }

    public final void register() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(CELLBROADCASTS_DISPLAYED_CONTENT_URI, true, this);
        this.mContext
                .getContentResolver()
                .registerContentObserver(CELLBROADCASTS_APP_CONTENT_URI, true, this);
        LogUtil.logd("CB observers registered");
    }

    public final void unregister() {
        this.mContext.getContentResolver().unregisterContentObserver(this);
        LogUtil.logd("CB observers unregistered");
    }
}
