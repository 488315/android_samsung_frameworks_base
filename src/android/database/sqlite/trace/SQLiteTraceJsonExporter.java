package android.database.sqlite.trace;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteUtils;
import android.database.sqlite.trace.SQLiteTrace;
import android.util.JsonWriter;
import android.widget.SemRemoteViewsValueAnimation;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SQLiteTraceJsonExporter extends SQLiteTraceExporter {
    private FileOutputStream mFileOutputStream;
    private JsonWriter mWriter;

    public SQLiteTraceJsonExporter(SQLiteTrace.TraceConfiguration traceConfiguration) throws IOException {
        super(traceConfiguration);
    }

    @Override // android.database.sqlite.trace.SQLiteTraceExporter
    void open(SQLiteTrace.TraceConfiguration traceConfiguration) throws IOException {
        this.mFileOutputStream = new FileOutputStream(traceConfiguration.traceFilePath);
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(this.mFileOutputStream));
        this.mWriter = jsonWriter;
        jsonWriter.beginObject();
        this.mWriter.name("dbname").value(traceConfiguration.databaseName);
        this.mWriter.name("dbpath").value(traceConfiguration.databaseFilePath);
        this.mWriter.name("operations");
        this.mWriter.beginArray();
    }

    @Override // android.database.sqlite.trace.SQLiteTraceExporter
    void writeOperations(List<SQLiteTrace.TraceOperation> list) throws IOException {
        Iterator<SQLiteTrace.TraceOperation> it = list.iterator();
        while (it.hasNext()) {
            writeOperation(it.next());
        }
        this.mWriter.flush();
    }

    @Override // android.database.sqlite.trace.SQLiteTraceExporter
    void writeOperation(SQLiteTrace.TraceOperation traceOperation) throws IOException {
        this.mWriter.beginObject();
        this.mWriter.name("calling-pid").value(traceOperation.callingPid);
        this.mWriter.name("tid").value(traceOperation.tid);
        this.mWriter.name("connection-id").value(traceOperation.connectionId);
        this.mWriter.name("sql").value(traceOperation.sql);
        this.mWriter.name("start").value(traceOperation.startTime);
        this.mWriter.name("end").value(traceOperation.endTime);
        this.mWriter.name("took").value(traceOperation.executionTime);
        if (traceOperation.countedRows >= 0) {
            this.mWriter.name("counted-rows").value(traceOperation.countedRows);
        }
        if (traceOperation.totalRows >= 0) {
            this.mWriter.name("total-rows").value(traceOperation.totalRows);
        }
        if (traceOperation.exception != null) {
            this.mWriter.name("error").value(traceOperation.exception.toString());
        } else {
            this.mWriter.name("error").nullValue();
        }
        if (traceOperation.bindArgs != null) {
            this.mWriter.name("bindargs");
            this.mWriter.beginArray();
            Iterator<Object> it = traceOperation.bindArgs.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                this.mWriter.beginObject();
                int typeOfObject = DatabaseUtils.getTypeOfObject(next);
                if (typeOfObject == 0) {
                    this.mWriter.name("type").value(PerfettoProtoLogImpl.NULL_STRING);
                } else if (typeOfObject == 1) {
                    this.mWriter.name("type").value("int");
                    this.mWriter.name("value").value(((Number) next).longValue());
                } else if (typeOfObject == 2) {
                    this.mWriter.name("type").value(SemRemoteViewsValueAnimation.VALUE_TYPE_FLOAT);
                    this.mWriter.name("value").value(((Number) next).doubleValue());
                } else if (typeOfObject == 3) {
                    this.mWriter.name("type").value("string");
                    this.mWriter.name("value").value(next.toString());
                } else {
                    this.mWriter.name("type").value("blob");
                    this.mWriter.name("value").value(SQLiteUtils.getHexString((byte[]) next));
                }
                this.mWriter.endObject();
            }
            this.mWriter.endArray();
        } else {
            this.mWriter.name("bindargs").nullValue();
        }
        this.mWriter.endObject();
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        this.mWriter.endArray();
        this.mWriter.endObject();
        this.mWriter.close();
        this.mFileOutputStream.close();
    }
}
