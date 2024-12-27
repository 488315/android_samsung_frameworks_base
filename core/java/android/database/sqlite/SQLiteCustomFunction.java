package android.database.sqlite;

public final class SQLiteCustomFunction {
    public final SQLiteDatabase.CustomFunction callback;
    public final String name;
    public final int numArgs;

    public SQLiteCustomFunction(String name, int numArgs, SQLiteDatabase.CustomFunction callback) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null.");
        }
        this.name = name;
        this.numArgs = numArgs;
        this.callback = callback;
    }

    private void dispatchCallback(String[] args) {
        this.callback.callback(args);
    }
}
