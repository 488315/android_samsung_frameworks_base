package com.android.internal.os;

import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class MonotonicClock {
    private static final String TAG = "MonotonicClock";
    public static final long UNDEFINED = -1;
    private static final String XML_ATTR_TIMESHIFT = "timeshift";
    private static final String XML_TAG_MONOTONIC_TIME = "monotonic_time";
    private final Clock mClock;
    private final AtomicFile mFile;
    private final long mTimeshift;

    public MonotonicClock(File file) {
        this(file, Clock.SYSTEM_CLOCK.elapsedRealtime(), Clock.SYSTEM_CLOCK);
    }

    public MonotonicClock(long monotonicTime, Clock clock) {
        this(null, monotonicTime, clock);
    }

    public MonotonicClock(File file, long monotonicTime, Clock clock) {
        this.mClock = clock;
        if (file != null) {
            this.mFile = new AtomicFile(file);
            this.mTimeshift = read(monotonicTime - this.mClock.elapsedRealtime());
        } else {
            this.mFile = null;
            this.mTimeshift = monotonicTime - this.mClock.elapsedRealtime();
        }
    }

    public long monotonicTime() {
        return monotonicTime(this.mClock.elapsedRealtime());
    }

    public long monotonicTime(long elapsedRealtimeMs) {
        return this.mTimeshift + elapsedRealtimeMs;
    }

    private long read(long defaultTimeshift) {
        if (!this.mFile.exists()) {
            return defaultTimeshift;
        }
        try {
            return readXml(new ByteArrayInputStream(this.mFile.readFully()), Xml.newBinaryPullParser());
        } catch (IOException e) {
            Log.e(TAG, "Cannot load monotonic clock from " + this.mFile.getBaseFile(), e);
            return defaultTimeshift;
        }
    }

    public void write() {
        if (this.mFile == null) {
            return;
        }
        FileOutputStream out = null;
        try {
            out = this.mFile.startWrite();
            writeXml(out, Xml.newBinarySerializer());
            this.mFile.finishWrite(out);
        } catch (IOException e) {
            Log.e(TAG, "Cannot write monotonic clock to " + this.mFile.getBaseFile(), e);
            this.mFile.failWrite(out);
        }
    }

    private long readXml(InputStream inputStream, TypedXmlPullParser parser) throws IOException {
        long savedTimeshift = 0;
        try {
            parser.setInput(inputStream, StandardCharsets.UTF_8.name());
            int eventType = parser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    if (parser.getName().equals(XML_TAG_MONOTONIC_TIME)) {
                        savedTimeshift = parser.getAttributeLong(null, XML_ATTR_TIMESHIFT);
                    }
                }
                eventType = parser.next();
            }
            return savedTimeshift - this.mClock.elapsedRealtime();
        } catch (XmlPullParserException e) {
            throw new IOException(e);
        }
    }

    private void writeXml(OutputStream out, TypedXmlSerializer serializer) throws IOException {
        serializer.setOutput(out, StandardCharsets.UTF_8.name());
        serializer.startDocument(null, true);
        serializer.startTag(null, XML_TAG_MONOTONIC_TIME);
        serializer.attributeLong(null, XML_ATTR_TIMESHIFT, monotonicTime());
        serializer.endTag(null, XML_TAG_MONOTONIC_TIME);
        serializer.endDocument();
    }
}
