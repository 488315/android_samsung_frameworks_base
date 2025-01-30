package android.database.sqlite.trace;

import android.database.sqlite.trace.SQLiteTrace;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public abstract class SQLiteTraceExporter implements AutoCloseable {
    private SQLiteTrace.TraceConfiguration mConfiguration;

    abstract void open(SQLiteTrace.TraceConfiguration traceConfiguration) throws IOException;

    abstract void writeOperation(SQLiteTrace.TraceOperation traceOperation) throws IOException;

    public SQLiteTraceExporter(SQLiteTrace.TraceConfiguration configuration) throws IOException {
        this.mConfiguration = configuration;
        open(configuration);
    }

    void writeOperations(List<SQLiteTrace.TraceOperation> operations) throws IOException {
        for (SQLiteTrace.TraceOperation operation : operations) {
            writeOperation(operation);
        }
    }

    public SQLiteTrace.TraceConfiguration getConfiguration() {
        return this.mConfiguration;
    }
}
