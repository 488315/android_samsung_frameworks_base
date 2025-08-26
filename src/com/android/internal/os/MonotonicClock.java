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

    public MonotonicClock(long j, Clock clock) {
        this(null, j, clock);
    }

    public MonotonicClock(File file, long j, Clock clock) {
        this.mClock = clock;
        if (file != null) {
            this.mFile = new AtomicFile(file);
            this.mTimeshift = read(j - clock.elapsedRealtime());
        } else {
            this.mFile = null;
            this.mTimeshift = j - clock.elapsedRealtime();
        }
    }

    public long monotonicTime() {
        return monotonicTime(this.mClock.elapsedRealtime());
    }

    public long monotonicTime(long j) {
        return this.mTimeshift + j;
    }

    private long read(long j) {
        if (!this.mFile.exists()) {
            return j;
        }
        try {
            return readXml(new ByteArrayInputStream(this.mFile.readFully()), Xml.newBinaryPullParser());
        } catch (IOException e) {
            Log.e(TAG, "Cannot load monotonic clock from " + this.mFile.getBaseFile(), e);
            return j;
        }
    }

    public void write() throws IOException {
        FileOutputStream fileOutputStreamStartWrite;
        AtomicFile atomicFile = this.mFile;
        if (atomicFile == null) {
            return;
        }
        try {
            fileOutputStreamStartWrite = atomicFile.startWrite();
        } catch (IOException e) {
            e = e;
            fileOutputStreamStartWrite = null;
        }
        try {
            writeXml(fileOutputStreamStartWrite, Xml.newBinarySerializer());
            this.mFile.finishWrite(fileOutputStreamStartWrite);
        } catch (IOException e2) {
            e = e2;
            Log.e(TAG, "Cannot write monotonic clock to " + this.mFile.getBaseFile(), e);
            this.mFile.failWrite(fileOutputStreamStartWrite);
        }
    }

    private long readXml(InputStream inputStream, TypedXmlPullParser typedXmlPullParser) throws IOException {
        try {
            typedXmlPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
            int eventType = typedXmlPullParser.getEventType();
            long attributeLong = 0;
            while (eventType != 1) {
                if (eventType == 2) {
                    if (typedXmlPullParser.getName().equals(XML_TAG_MONOTONIC_TIME)) {
                        attributeLong = typedXmlPullParser.getAttributeLong(null, XML_ATTR_TIMESHIFT);
                    }
                }
                eventType = typedXmlPullParser.next();
            }
            return attributeLong - this.mClock.elapsedRealtime();
        } catch (XmlPullParserException e) {
            throw new IOException(e);
        }
    }

    private void writeXml(OutputStream outputStream, TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        typedXmlSerializer.startDocument(null, true);
        typedXmlSerializer.startTag(null, XML_TAG_MONOTONIC_TIME);
        typedXmlSerializer.attributeLong(null, XML_ATTR_TIMESHIFT, monotonicTime());
        typedXmlSerializer.endTag(null, XML_TAG_MONOTONIC_TIME);
        typedXmlSerializer.endDocument();
    }
}
