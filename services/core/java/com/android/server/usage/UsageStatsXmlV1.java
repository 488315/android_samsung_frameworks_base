package com.android.server.usage;

import android.app.usage.ConfigurationStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.content.res.Configuration;
import android.util.ArrayMap;
import android.util.Log;

import com.android.internal.util.XmlUtils;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.net.ProtocolException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class UsageStatsXmlV1 {
    public static void loadCountAndTime(
            XmlPullParser xmlPullParser, IntervalStats.EventTracker eventTracker) {
        eventTracker.count = XmlUtils.readIntAttribute(xmlPullParser, "count", 0);
        eventTracker.duration = XmlUtils.readLongAttribute(xmlPullParser, "time", 0L);
        XmlUtils.skipCurrentTag(xmlPullParser);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static void read(XmlPullParser xmlPullParser, IntervalStats intervalStats) {
        int i;
        String readStringAttribute;
        int i2 = 5;
        int i3 = 2;
        intervalStats.packageStats.clear();
        intervalStats.configurations.clear();
        String str = null;
        intervalStats.activeConfiguration = null;
        intervalStats.events.clear();
        intervalStats.endTime =
                XmlUtils.readLongAttribute(xmlPullParser, "endTime") + intervalStats.beginTime;
        try {
            intervalStats.majorVersion = XmlUtils.readIntAttribute(xmlPullParser, "majorVersion");
        } catch (IOException unused) {
            Log.i("UsageStatsXmlV1", "Failed to parse majorVersion");
        }
        try {
            intervalStats.minorVersion = XmlUtils.readIntAttribute(xmlPullParser, "minorVersion");
        } catch (IOException unused2) {
            Log.i("UsageStatsXmlV1", "Failed to parse minorVersion");
        }
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next == i3) {
                String name = xmlPullParser.getName();
                name.getClass();
                int i4 = -1;
                switch (name.hashCode()) {
                    case -1354792126:
                        if (name.equals("config")) {
                            i4 = 0;
                            break;
                        }
                        break;
                    case -1169351247:
                        if (name.equals("keyguard-hidden")) {
                            i4 = 1;
                            break;
                        }
                        break;
                    case -807157790:
                        if (name.equals("non-interactive")) {
                            i4 = 2;
                            break;
                        }
                        break;
                    case -807062458:
                        if (name.equals("package")) {
                            i4 = 3;
                            break;
                        }
                        break;
                    case 96891546:
                        if (name.equals("event")) {
                            i4 = 4;
                            break;
                        }
                        break;
                    case 526608426:
                        if (name.equals("keyguard-shown")) {
                            i4 = i2;
                            break;
                        }
                        break;
                    case 1844104930:
                        if (name.equals("interactive")) {
                            i4 = 6;
                            break;
                        }
                        break;
                }
                switch (i4) {
                    case 0:
                        i = depth;
                        Configuration configuration = new Configuration();
                        Configuration.readXmlAttrs(xmlPullParser, configuration);
                        ConfigurationStats orCreateConfigurationStats =
                                intervalStats.getOrCreateConfigurationStats(configuration);
                        orCreateConfigurationStats.mLastTimeActive =
                                XmlUtils.readLongAttribute(xmlPullParser, "lastTimeActive")
                                        + intervalStats.beginTime;
                        orCreateConfigurationStats.mTotalTimeActive =
                                XmlUtils.readLongAttribute(xmlPullParser, "timeActive");
                        orCreateConfigurationStats.mActivationCount =
                                XmlUtils.readIntAttribute(xmlPullParser, "count");
                        if (XmlUtils.readBooleanAttribute(xmlPullParser, "active")) {
                            intervalStats.activeConfiguration =
                                    orCreateConfigurationStats.mConfiguration;
                        }
                        depth = i;
                        i2 = 5;
                        i3 = 2;
                        str = null;
                        break;
                    case 1:
                        i = depth;
                        loadCountAndTime(xmlPullParser, intervalStats.keyguardHiddenTracker);
                        depth = i;
                        i2 = 5;
                        i3 = 2;
                        str = null;
                        break;
                    case 2:
                        i = depth;
                        loadCountAndTime(xmlPullParser, intervalStats.nonInteractiveTracker);
                        depth = i;
                        i2 = 5;
                        i3 = 2;
                        str = null;
                        break;
                    case 3:
                        String attributeValue = xmlPullParser.getAttributeValue(str, "package");
                        if (attributeValue == null) {
                            throw new ProtocolException("no package attribute present");
                        }
                        UsageStats orCreateUsageStats =
                                intervalStats.getOrCreateUsageStats(attributeValue);
                        i = depth;
                        orCreateUsageStats.mLastTimeUsed =
                                XmlUtils.readLongAttribute(xmlPullParser, "lastTimeActive")
                                        + intervalStats.beginTime;
                        try {
                            orCreateUsageStats.mLastTimeVisible =
                                    intervalStats.beginTime
                                            + XmlUtils.readLongAttribute(
                                                    xmlPullParser, "lastTimeVisible");
                        } catch (IOException unused3) {
                            Log.i("UsageStatsXmlV1", "Failed to parse mLastTimeVisible");
                        }
                        try {
                            orCreateUsageStats.mLastTimeForegroundServiceUsed =
                                    intervalStats.beginTime
                                            + XmlUtils.readLongAttribute(
                                                    xmlPullParser, "lastTimeServiceUsed");
                        } catch (IOException unused4) {
                            Log.i(
                                    "UsageStatsXmlV1",
                                    "Failed to parse mLastTimeForegroundServiceUsed");
                        }
                        orCreateUsageStats.mTotalTimeInForeground =
                                XmlUtils.readLongAttribute(xmlPullParser, "timeActive");
                        try {
                            orCreateUsageStats.mTotalTimeVisible =
                                    XmlUtils.readLongAttribute(xmlPullParser, "timeVisible");
                        } catch (IOException unused5) {
                            Log.i("UsageStatsXmlV1", "Failed to parse mTotalTimeVisible");
                        }
                        try {
                            orCreateUsageStats.mTotalTimeForegroundServiceUsed =
                                    XmlUtils.readLongAttribute(xmlPullParser, "timeServiceUsed");
                        } catch (IOException unused6) {
                            Log.i(
                                    "UsageStatsXmlV1",
                                    "Failed to parse mTotalTimeForegroundServiceUsed");
                        }
                        orCreateUsageStats.mLastEvent =
                                XmlUtils.readIntAttribute(xmlPullParser, "lastEvent");
                        orCreateUsageStats.mAppLaunchCount =
                                XmlUtils.readIntAttribute(xmlPullParser, "appLaunchCount", 0);
                        while (true) {
                            int next2 = xmlPullParser.next();
                            if (next2 != 1) {
                                String name2 = xmlPullParser.getName();
                                if (next2 != 3 || !name2.equals("package")) {
                                    if (next2 == 2) {
                                        if (name2.equals("chosen_action")
                                                && (readStringAttribute =
                                                                XmlUtils.readStringAttribute(
                                                                        xmlPullParser, "name"))
                                                        != null) {
                                            if (orCreateUsageStats.mChooserCounts == null) {
                                                orCreateUsageStats.mChooserCounts = new ArrayMap();
                                            }
                                            if (!orCreateUsageStats.mChooserCounts.containsKey(
                                                    readStringAttribute)) {
                                                orCreateUsageStats.mChooserCounts.put(
                                                        readStringAttribute, new ArrayMap());
                                            }
                                            while (true) {
                                                int next3 = xmlPullParser.next();
                                                if (next3 != 1) {
                                                    String name3 = xmlPullParser.getName();
                                                    if (next3 != 3
                                                            || !name3.equals("chosen_action")) {
                                                        if (next3 == 2
                                                                && name3.equals("category")) {
                                                            ((ArrayMap)
                                                                            orCreateUsageStats
                                                                                    .mChooserCounts
                                                                                    .get(
                                                                                            readStringAttribute))
                                                                    .put(
                                                                            XmlUtils
                                                                                    .readStringAttribute(
                                                                                            xmlPullParser,
                                                                                            "name"),
                                                                            Integer.valueOf(
                                                                                    XmlUtils
                                                                                            .readIntAttribute(
                                                                                                    xmlPullParser,
                                                                                                    "count")));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        depth = i;
                        i2 = 5;
                        i3 = 2;
                        str = null;
                        break;
                        break;
                    case 4:
                        String readStringAttribute2 =
                                XmlUtils.readStringAttribute(xmlPullParser, "package");
                        if (readStringAttribute2 == null) {
                            throw new ProtocolException("no package attribute present");
                        }
                        String readStringAttribute3 =
                                XmlUtils.readStringAttribute(xmlPullParser, "class");
                        UsageEvents.Event event = new UsageEvents.Event();
                        event.mPackage = intervalStats.getCachedStringRef(readStringAttribute2);
                        if (readStringAttribute3 != null) {
                            event.mClass = intervalStats.getCachedStringRef(readStringAttribute3);
                        }
                        event.mFlags = XmlUtils.readIntAttribute(xmlPullParser, "flags", 0);
                        event.mTimeStamp =
                                intervalStats.beginTime
                                        + XmlUtils.readLongAttribute(xmlPullParser, "time");
                        event.mEventType = XmlUtils.readIntAttribute(xmlPullParser, "type");
                        try {
                            event.mInstanceId =
                                    XmlUtils.readIntAttribute(xmlPullParser, "instanceId");
                        } catch (IOException unused7) {
                            Log.i("UsageStatsXmlV1", "Failed to parse mInstanceId");
                        }
                        int i5 = event.mEventType;
                        if (i5 == i2) {
                            Configuration configuration2 = new Configuration();
                            event.mConfiguration = configuration2;
                            Configuration.readXmlAttrs(xmlPullParser, configuration2);
                        } else if (i5 == 8) {
                            String readStringAttribute4 =
                                    XmlUtils.readStringAttribute(xmlPullParser, "shortcutId");
                            event.mShortcutId =
                                    readStringAttribute4 != null
                                            ? readStringAttribute4.intern()
                                            : str;
                        } else if (i5 == 11) {
                            event.mBucketAndReason =
                                    XmlUtils.readIntAttribute(xmlPullParser, "standbyBucket", 0);
                        } else if (i5 == 12) {
                            String readStringAttribute5 =
                                    XmlUtils.readStringAttribute(
                                            xmlPullParser, "notificationChannel");
                            event.mNotificationChannelId =
                                    readStringAttribute5 != null
                                            ? readStringAttribute5.intern()
                                            : str;
                        }
                        intervalStats.addEvent(event);
                        i = depth;
                        depth = i;
                        i2 = 5;
                        i3 = 2;
                        str = null;
                        break;
                    case 5:
                        loadCountAndTime(xmlPullParser, intervalStats.keyguardShownTracker);
                        i = depth;
                        depth = i;
                        i2 = 5;
                        i3 = 2;
                        str = null;
                        break;
                    case 6:
                        loadCountAndTime(xmlPullParser, intervalStats.interactiveTracker);
                        i = depth;
                        depth = i;
                        i2 = 5;
                        i3 = 2;
                        str = null;
                        break;
                    default:
                        i = depth;
                        depth = i;
                        i2 = 5;
                        i3 = 2;
                        str = null;
                        break;
                }
            }
        }
    }
}
