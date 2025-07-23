package android.database.sqlite.trace;

import android.database.sqlite.trace.SQLiteTrace;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class SQLiteTraceExporter implements AutoCloseable {
    private SQLiteTrace.TraceConfiguration mConfiguration;

    abstract void open(SQLiteTrace.TraceConfiguration traceConfiguration) throws IOException;

    abstract void writeOperation(SQLiteTrace.TraceOperation traceOperation) throws IOException;

    public SQLiteTraceExporter(SQLiteTrace.TraceConfiguration traceConfiguration) throws IOException {
        this.mConfiguration = traceConfiguration;
        open(traceConfiguration);
    }

    void writeOperations(List<SQLiteTrace.TraceOperation> list) throws IOException {
        Iterator<SQLiteTrace.TraceOperation> it = list.iterator();
        while (it.hasNext()) {
            writeOperation(it.next());
        }
    }

    public SQLiteTrace.TraceConfiguration getConfiguration() {
        return this.mConfiguration;
    }
}
