package android.database.sqlite.trace;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteUtils;
import android.util.JsonWriter;
import android.widget.SemRemoteViewsValueAnimation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

public class SQLiteTraceJsonExporter extends SQLiteTraceExporter {
    private FileOutputStream mFileOutputStream;
    private JsonWriter mWriter;

    public SQLiteTraceJsonExporter(SQLiteTrace.TraceConfiguration configuration)
            throws IOException {
        super(configuration);
    }

    @Override // android.database.sqlite.trace.SQLiteTraceExporter
    void open(SQLiteTrace.TraceConfiguration configuration) throws IOException {
        this.mFileOutputStream = new FileOutputStream(configuration.traceFilePath);
        this.mWriter = new JsonWriter(new OutputStreamWriter(this.mFileOutputStream));
        this.mWriter.beginObject();
        this.mWriter.name("dbname").value(configuration.databaseName);
        this.mWriter.name("dbpath").value(configuration.databaseFilePath);
        this.mWriter.name("operations");
        this.mWriter.beginArray();
    }

    @Override // android.database.sqlite.trace.SQLiteTraceExporter
    void writeOperations(List<SQLiteTrace.TraceOperation> operations) throws IOException {
        for (SQLiteTrace.TraceOperation operation : operations) {
            writeOperation(operation);
        }
        this.mWriter.flush();
    }

    @Override // android.database.sqlite.trace.SQLiteTraceExporter
    void writeOperation(SQLiteTrace.TraceOperation operation) throws IOException {
        this.mWriter.beginObject();
        this.mWriter.name("calling-pid").value(operation.callingPid);
        this.mWriter.name("tid").value(operation.tid);
        this.mWriter.name("connection-id").value(operation.connectionId);
        this.mWriter.name("sql").value(operation.sql);
        this.mWriter.name("start").value(operation.startTime);
        this.mWriter.name("end").value(operation.endTime);
        this.mWriter.name("took").value(operation.executionTime);
        if (operation.countedRows >= 0) {
            this.mWriter.name("counted-rows").value(operation.countedRows);
        }
        if (operation.totalRows >= 0) {
            this.mWriter.name("total-rows").value(operation.totalRows);
        }
        if (operation.exception != null) {
            this.mWriter.name("error").value(operation.exception.toString());
        } else {
            this.mWriter.name("error").nullValue();
        }
        if (operation.bindArgs != null) {
            this.mWriter.name("bindargs");
            this.mWriter.beginArray();
            Iterator<Object> it = operation.bindArgs.iterator();
            while (it.hasNext()) {
                Object arg = it.next();
                this.mWriter.beginObject();
                switch (DatabaseUtils.getTypeOfObject(arg)) {
                    case 0:
                        this.mWriter.name("type").value("null");
                        break;
                    case 1:
                        this.mWriter.name("type").value("int");
                        this.mWriter.name("value").value(((Number) arg).longValue());
                        break;
                    case 2:
                        this.mWriter
                                .name("type")
                                .value(SemRemoteViewsValueAnimation.VALUE_TYPE_FLOAT);
                        this.mWriter.name("value").value(((Number) arg).doubleValue());
                        break;
                    case 3:
                        this.mWriter.name("type").value("string");
                        this.mWriter.name("value").value(arg.toString());
                        break;
                    default:
                        this.mWriter.name("type").value("blob");
                        this.mWriter.name("value").value(SQLiteUtils.getHexString((byte[]) arg));
                        break;
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
