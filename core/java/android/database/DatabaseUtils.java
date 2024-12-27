package android.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.content.OperationApplicationException;
import android.database.sqlite.Flags;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteProgram;
import android.database.sqlite.SQLiteStatement;
import android.hardware.Sensor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.OperationCanceledException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.Collator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class DatabaseUtils {
    private static final boolean DEBUG = false;
    private static final int PREFIX_GROUP_NUM = 2;
    public static final int STATEMENT_ABORT = 6;
    public static final int STATEMENT_ATTACH = 3;
    public static final int STATEMENT_BEGIN = 4;
    public static final int STATEMENT_COMMENT = 102;
    public static final int STATEMENT_COMMIT = 5;
    public static final int STATEMENT_CREATE = 101;
    public static final int STATEMENT_DDL = 8;
    public static final int STATEMENT_OTHER = 99;
    public static final int STATEMENT_PRAGMA = 7;
    public static final int STATEMENT_SELECT = 1;
    public static final int STATEMENT_UNPREPARED = 9;
    public static final int STATEMENT_UPDATE = 2;
    public static final int STATEMENT_WITH = 100;
    private static final String TAG = "DatabaseUtils";
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.AM_PM, 'b', 'c', DateFormat.DATE, 'e', 'f'};
    private static Collator mColl = null;
    private static final String PREFIX_REGEX = "(\\s+|--.*?\n|/\\*[\\w\\W]*?\\*/)*(\\w\\w\\w)";
    private static final Pattern sPrefixPattern = Pattern.compile(PREFIX_REGEX);

    public static final void writeExceptionToParcel(Parcel reply, Exception e) {
        int code;
        boolean logException = true;
        if (e instanceof FileNotFoundException) {
            code = 1;
            logException = false;
        } else if (e instanceof IllegalArgumentException) {
            code = 2;
        } else if (e instanceof UnsupportedOperationException) {
            code = 3;
        } else if (e instanceof SQLiteAbortException) {
            code = 4;
        } else if (e instanceof SQLiteConstraintException) {
            code = 5;
        } else if (e instanceof SQLiteDatabaseCorruptException) {
            code = 6;
        } else if (e instanceof SQLiteFullException) {
            code = 7;
        } else if (e instanceof SQLiteDiskIOException) {
            code = 8;
        } else if (e instanceof SQLiteException) {
            code = 9;
        } else if (e instanceof OperationApplicationException) {
            code = 10;
        } else if (e instanceof OperationCanceledException) {
            code = 11;
            logException = false;
        } else {
            reply.writeException(e);
            Log.e(TAG, "Writing exception to parcel", e);
            return;
        }
        reply.writeInt(code);
        reply.writeString(e.getMessage());
        if (logException) {
            Log.e(TAG, "Writing exception to parcel", e);
        }
    }

    public static final void readExceptionFromParcel(Parcel reply) {
        int code = reply.readExceptionCode();
        if (code == 0) {
            return;
        }
        String msg = reply.readString();
        readExceptionFromParcel(reply, msg, code);
    }

    public static void readExceptionWithFileNotFoundExceptionFromParcel(Parcel reply) throws FileNotFoundException {
        int code = reply.readExceptionCode();
        if (code == 0) {
            return;
        }
        String msg = reply.readString();
        if (code == 1) {
            throw new FileNotFoundException(msg);
        }
        readExceptionFromParcel(reply, msg, code);
    }

    public static void readExceptionWithOperationApplicationExceptionFromParcel(Parcel reply) throws OperationApplicationException {
        int code = reply.readExceptionCode();
        if (code == 0) {
            return;
        }
        String msg = reply.readString();
        if (code == 10) {
            throw new OperationApplicationException(msg);
        }
        readExceptionFromParcel(reply, msg, code);
    }

    private static final void readExceptionFromParcel(Parcel reply, String msg, int code) {
        switch (code) {
            case 2:
                throw new IllegalArgumentException(msg);
            case 3:
                throw new UnsupportedOperationException(msg);
            case 4:
                throw new SQLiteAbortException(msg);
            case 5:
                throw new SQLiteConstraintException(msg);
            case 6:
                throw new SQLiteDatabaseCorruptException(msg);
            case 7:
                throw new SQLiteFullException(msg);
            case 8:
                throw new SQLiteDiskIOException(msg);
            case 9:
                throw new SQLiteException(msg);
            case 10:
            default:
                reply.readException(code, msg);
                return;
            case 11:
                throw new OperationCanceledException(msg);
        }
    }

    public static long executeInsert(SQLiteDatabase db, String sql, Object[] bindArgs) throws SQLException {
        SQLiteStatement st = db.compileStatement(sql);
        try {
            bindArgs(st, bindArgs);
            long executeInsert = st.executeInsert();
            if (st != null) {
                st.close();
            }
            return executeInsert;
        } catch (Throwable th) {
            if (st != null) {
                try {
                    st.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static int executeUpdateDelete(SQLiteDatabase db, String sql, Object[] bindArgs) throws SQLException {
        SQLiteStatement st = db.compileStatement(sql);
        try {
            bindArgs(st, bindArgs);
            int executeUpdateDelete = st.executeUpdateDelete();
            if (st != null) {
                st.close();
            }
            return executeUpdateDelete;
        } catch (Throwable th) {
            if (st != null) {
                try {
                    st.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static void bindArgs(SQLiteStatement st, Object[] bindArgs) {
        if (bindArgs == null) {
            return;
        }
        for (int i = 0; i < bindArgs.length; i++) {
            Object bindArg = bindArgs[i];
            switch (getTypeOfObject(bindArg)) {
                case 0:
                    st.bindNull(i + 1);
                    break;
                case 1:
                    st.bindLong(i + 1, ((Number) bindArg).longValue());
                    break;
                case 2:
                    st.bindDouble(i + 1, ((Number) bindArg).doubleValue());
                    break;
                case 3:
                default:
                    if (bindArg instanceof Boolean) {
                        st.bindLong(i + 1, ((Boolean) bindArg).booleanValue() ? 1L : 0L);
                        break;
                    } else {
                        st.bindString(i + 1, bindArg.toString());
                        break;
                    }
                case 4:
                    st.bindBlob(i + 1, (byte[]) bindArg);
                    break;
            }
        }
    }

    public static void bindObjectToProgram(SQLiteProgram prog, int index, Object value) {
        if (value == null) {
            prog.bindNull(index);
            return;
        }
        if ((value instanceof Double) || (value instanceof Float)) {
            prog.bindDouble(index, ((Number) value).doubleValue());
            return;
        }
        if (value instanceof Number) {
            prog.bindLong(index, ((Number) value).longValue());
            return;
        }
        if (!(value instanceof Boolean)) {
            if (value instanceof byte[]) {
                prog.bindBlob(index, (byte[]) value);
                return;
            } else {
                prog.bindString(index, value.toString());
                return;
            }
        }
        Boolean bool = (Boolean) value;
        if (bool.booleanValue()) {
            prog.bindLong(index, 1L);
        } else {
            prog.bindLong(index, 0L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0073 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String bindSelection(java.lang.String r13, java.lang.Object... r14) {
        /*
            if (r13 != 0) goto L4
            r0 = 0
            return r0
        L4:
            boolean r0 = com.android.internal.util.ArrayUtils.isEmpty(r14)
            if (r0 == 0) goto Lb
            return r13
        Lb:
            r0 = 63
            int r1 = r13.indexOf(r0)
            r2 = -1
            if (r1 != r2) goto L15
            return r13
        L15:
            r1 = 32
            r2 = 32
            r3 = 0
            int r4 = r13.length()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            r6 = 0
        L24:
            if (r6 >= r4) goto Lb9
            int r7 = r6 + 1
            char r6 = r13.charAt(r6)
            if (r6 != r0) goto Lb2
            r2 = 32
            r8 = r7
        L31:
            if (r7 >= r4) goto L45
            char r6 = r13.charAt(r7)
            r9 = 48
            if (r6 < r9) goto L43
            r9 = 57
            if (r6 <= r9) goto L40
            goto L43
        L40:
            int r7 = r7 + 1
            goto L31
        L43:
            r2 = r6
        L45:
            if (r8 == r7) goto L51
            java.lang.String r9 = r13.substring(r8, r7)
            int r9 = java.lang.Integer.parseInt(r9)
            int r3 = r9 + (-1)
        L51:
            int r9 = r3 + 1
            r3 = r14[r3]
            r10 = 32
            if (r1 == r10) goto L60
            r11 = 61
            if (r1 == r11) goto L60
            r5.append(r10)
        L60:
            int r11 = getTypeOfObject(r3)
            switch(r11) {
                case 0: goto L91;
                case 1: goto L86;
                case 2: goto L7b;
                case 3: goto L67;
                case 4: goto L73;
                default: goto L67;
            }
        L67:
            boolean r11 = r3 instanceof java.lang.Boolean
            if (r11 == 0) goto L9b
            r11 = r3
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            goto L97
        L73:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "Blobs not supported"
            r0.<init>(r10)
            throw r0
        L7b:
            r11 = r3
            java.lang.Number r11 = (java.lang.Number) r11
            double r11 = r11.doubleValue()
            r5.append(r11)
            goto Laa
        L86:
            r11 = r3
            java.lang.Number r11 = (java.lang.Number) r11
            long r11 = r11.longValue()
            r5.append(r11)
            goto Laa
        L91:
            java.lang.String r11 = "NULL"
            r5.append(r11)
            goto Laa
        L97:
            r5.append(r11)
            goto Laa
        L9b:
            r11 = 39
            r5.append(r11)
            java.lang.String r12 = r3.toString()
            r5.append(r12)
            r5.append(r11)
        Laa:
            if (r2 == r10) goto Laf
            r5.append(r10)
        Laf:
            r6 = r7
            r3 = r9
            goto Lb7
        Lb2:
            r5.append(r6)
            r1 = r6
            r6 = r7
        Lb7:
            goto L24
        Lb9:
            java.lang.String r0 = r5.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.DatabaseUtils.bindSelection(java.lang.String, java.lang.Object[]):java.lang.String");
    }

    public static Object[] deepCopyOf(Object[] args) {
        if (args == null) {
            return null;
        }
        Object[] res = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null || (arg instanceof Number) || (arg instanceof String)) {
                res[i] = arg;
            } else if (arg instanceof byte[]) {
                byte[] castArg = (byte[]) arg;
                res[i] = Arrays.copyOf(castArg, castArg.length);
            } else {
                res[i] = String.valueOf(arg);
            }
        }
        return res;
    }

    public static int getTypeOfObject(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof byte[]) {
            return 4;
        }
        if ((obj instanceof Float) || (obj instanceof Double)) {
            return 2;
        }
        if ((obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte)) {
            return 1;
        }
        return 3;
    }

    public static void cursorFillWindow(Cursor cursor, int position, CursorWindow window) {
        boolean success;
        if (position < 0 || position >= cursor.getCount()) {
            return;
        }
        int oldPos = cursor.getPosition();
        int numColumns = cursor.getColumnCount();
        window.clear();
        window.setStartPosition(position);
        window.setNumColumns(numColumns);
        if (cursor.moveToPosition(position)) {
            while (true) {
                if (window.allocRow()) {
                    int i = 0;
                    while (true) {
                        if (i < numColumns) {
                            int type = cursor.getType(i);
                            switch (type) {
                                case 0:
                                    success = window.putNull(position, i);
                                    break;
                                case 1:
                                    success = window.putLong(cursor.getLong(i), position, i);
                                    break;
                                case 2:
                                    success = window.putDouble(cursor.getDouble(i), position, i);
                                    break;
                                case 3:
                                default:
                                    String value = cursor.getString(i);
                                    if (value == null) {
                                        success = window.putNull(position, i);
                                        break;
                                    } else {
                                        success = window.putString(value, position, i);
                                        break;
                                    }
                                case 4:
                                    byte[] value2 = cursor.getBlob(i);
                                    if (value2 == null) {
                                        success = window.putNull(position, i);
                                        break;
                                    } else {
                                        success = window.putBlob(value2, position, i);
                                        break;
                                    }
                            }
                            if (success) {
                                i++;
                            } else {
                                window.freeLastRow();
                            }
                        } else {
                            position++;
                            if (!cursor.moveToNext()) {
                            }
                        }
                    }
                }
            }
        }
        cursor.moveToPosition(oldPos);
    }

    public static void appendEscapedSQLString(StringBuilder sb, String sqlString) {
        sb.append(DateFormat.QUOTE);
        int length = sqlString.length();
        for (int i = 0; i < length; i++) {
            char c = sqlString.charAt(i);
            if (Character.isHighSurrogate(c)) {
                if (i != length - 1 && Character.isLowSurrogate(sqlString.charAt(i + 1))) {
                    sb.append(c);
                    sb.append(sqlString.charAt(i + 1));
                }
            } else if (!Character.isLowSurrogate(c)) {
                if (c == '\'') {
                    sb.append(DateFormat.QUOTE);
                }
                sb.append(c);
            }
        }
        sb.append(DateFormat.QUOTE);
    }

    public static String sqlEscapeString(String value) {
        StringBuilder escaper = new StringBuilder();
        appendEscapedSQLString(escaper, value);
        return escaper.toString();
    }

    public static final void appendValueToSql(StringBuilder sql, Object value) {
        if (value == null) {
            sql.append("NULL");
            return;
        }
        if (value instanceof Boolean) {
            Boolean bool = (Boolean) value;
            if (bool.booleanValue()) {
                sql.append('1');
                return;
            } else {
                sql.append('0');
                return;
            }
        }
        appendEscapedSQLString(sql, value.toString());
    }

    public static String concatenateWhere(String a, String b) {
        if (TextUtils.isEmpty(a)) {
            return b;
        }
        if (TextUtils.isEmpty(b)) {
            return a;
        }
        return NavigationBarInflaterView.KEY_CODE_START + a + ") AND (" + b + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String getCollationKey(String name) {
        byte[] arr = getCollationKeyInBytes(name);
        try {
            return new String(arr, 0, getKeyLen(arr), "ISO8859_1");
        } catch (Exception e) {
            return "";
        }
    }

    public static String getHexCollationKey(String name) {
        byte[] arr = getCollationKeyInBytes(name);
        char[] keys = encodeHex(arr);
        return new String(keys, 0, getKeyLen(arr) * 2);
    }

    private static char[] encodeHex(byte[] input) {
        int l = input.length;
        char[] out = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; i++) {
            int j2 = j + 1;
            out[j] = DIGITS[(input[i] & 240) >>> 4];
            j = j2 + 1;
            out[j2] = DIGITS[input[i] & 15];
        }
        return out;
    }

    private static int getKeyLen(byte[] arr) {
        if (arr[arr.length - 1] != 0) {
            return arr.length;
        }
        return arr.length - 1;
    }

    private static byte[] getCollationKeyInBytes(String name) {
        if (mColl == null) {
            mColl = Collator.getInstance();
            mColl.setStrength(0);
        }
        return mColl.getCollationKey(name).toByteArray();
    }

    public static void dumpCursor(Cursor cursor) {
        dumpCursor(cursor, System.out);
    }

    public static void dumpCursor(Cursor cursor, PrintStream stream) {
        stream.println(">>>>> Dumping cursor " + cursor);
        if (cursor != null) {
            int startPos = cursor.getPosition();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                dumpCurrentRow(cursor, stream);
            }
            cursor.moveToPosition(startPos);
        }
        stream.println("<<<<<");
    }

    public static void dumpCursor(Cursor cursor, StringBuilder sb) {
        sb.append(">>>>> Dumping cursor ").append(cursor).append('\n');
        if (cursor != null) {
            int startPos = cursor.getPosition();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                dumpCurrentRow(cursor, sb);
            }
            cursor.moveToPosition(startPos);
        }
        sb.append("<<<<<\n");
    }

    public static String dumpCursorToString(Cursor cursor) {
        StringBuilder sb = new StringBuilder();
        dumpCursor(cursor, sb);
        return sb.toString();
    }

    public static void dumpCurrentRow(Cursor cursor) {
        dumpCurrentRow(cursor, System.out);
    }

    public static void dumpCurrentRow(Cursor cursor, PrintStream stream) {
        String value;
        String[] cols = cursor.getColumnNames();
        stream.println("" + cursor.getPosition() + " {");
        int length = cols.length;
        for (int i = 0; i < length; i++) {
            try {
                value = cursor.getString(i);
            } catch (SQLiteException e) {
                value = "<unprintable>";
            }
            stream.println("   " + cols[i] + '=' + value);
        }
        stream.println("}");
    }

    public static void dumpCurrentRow(Cursor cursor, StringBuilder sb) {
        String value;
        String[] cols = cursor.getColumnNames();
        sb.append(cursor.getPosition()).append(" {\n");
        int length = cols.length;
        for (int i = 0; i < length; i++) {
            try {
                value = cursor.getString(i);
            } catch (SQLiteException e) {
                value = "<unprintable>";
            }
            sb.append("   ").append(cols[i]).append('=').append(value).append('\n');
        }
        sb.append("}\n");
    }

    public static String dumpCurrentRowToString(Cursor cursor) {
        StringBuilder sb = new StringBuilder();
        dumpCurrentRow(cursor, sb);
        return sb.toString();
    }

    public static void cursorStringToContentValues(Cursor cursor, String field, ContentValues values) {
        cursorStringToContentValues(cursor, field, values, field);
    }

    public static void cursorStringToInsertHelper(Cursor cursor, String field, InsertHelper inserter, int index) {
        inserter.bind(index, cursor.getString(cursor.getColumnIndexOrThrow(field)));
    }

    public static void cursorStringToContentValues(Cursor cursor, String field, ContentValues values, String key) {
        values.put(key, cursor.getString(cursor.getColumnIndexOrThrow(field)));
    }

    public static void cursorIntToContentValues(Cursor cursor, String field, ContentValues values) {
        cursorIntToContentValues(cursor, field, values, field);
    }

    public static void cursorIntToContentValues(Cursor cursor, String field, ContentValues values, String key) {
        int colIndex = cursor.getColumnIndex(field);
        if (!cursor.isNull(colIndex)) {
            values.put(key, Integer.valueOf(cursor.getInt(colIndex)));
        } else {
            values.put(key, (Integer) null);
        }
    }

    public static void cursorLongToContentValues(Cursor cursor, String field, ContentValues values) {
        cursorLongToContentValues(cursor, field, values, field);
    }

    public static void cursorLongToContentValues(Cursor cursor, String field, ContentValues values, String key) {
        int colIndex = cursor.getColumnIndex(field);
        if (!cursor.isNull(colIndex)) {
            Long value = Long.valueOf(cursor.getLong(colIndex));
            values.put(key, value);
        } else {
            values.put(key, (Long) null);
        }
    }

    public static void cursorDoubleToCursorValues(Cursor cursor, String field, ContentValues values) {
        cursorDoubleToContentValues(cursor, field, values, field);
    }

    public static void cursorDoubleToContentValues(Cursor cursor, String field, ContentValues values, String key) {
        int colIndex = cursor.getColumnIndex(field);
        if (!cursor.isNull(colIndex)) {
            values.put(key, Double.valueOf(cursor.getDouble(colIndex)));
        } else {
            values.put(key, (Double) null);
        }
    }

    public static void cursorRowToContentValues(Cursor cursor, ContentValues values) {
        String[] columns = cursor.getColumnNames();
        int length = columns.length;
        for (int i = 0; i < length; i++) {
            if (cursor.getType(i) == 4) {
                values.put(columns[i], cursor.getBlob(i));
            } else {
                values.put(columns[i], cursor.getString(i));
            }
        }
    }

    public static int cursorPickFillWindowStartPosition(int cursorPosition, int cursorWindowCapacity) {
        return Math.max(cursorPosition - (cursorWindowCapacity / 3), 0);
    }

    public static long queryNumEntries(SQLiteDatabase db, String table) {
        return queryNumEntries(db, table, null, null);
    }

    public static long queryNumEntries(SQLiteDatabase db, String table, String selection) {
        return queryNumEntries(db, table, selection, null);
    }

    public static long queryNumEntries(SQLiteDatabase db, String table, String selection, String[] selectionArgs) {
        String s = !TextUtils.isEmpty(selection) ? " where " + selection : "";
        return longForQuery(db, "select count(*) from " + table + s, selectionArgs);
    }

    public static boolean queryIsEmpty(SQLiteDatabase db, String table) {
        long isEmpty = longForQuery(db, "select exists(select 1 from " + table + NavigationBarInflaterView.KEY_CODE_END, null);
        return isEmpty == 0;
    }

    public static long longForQuery(SQLiteDatabase db, String query, String[] selectionArgs) {
        SQLiteStatement prog = db.compileStatement(query);
        try {
            return longForQuery(prog, selectionArgs);
        } finally {
            prog.close();
        }
    }

    public static long longForQuery(SQLiteStatement prog, String[] selectionArgs) {
        prog.bindAllArgsAsStrings(selectionArgs);
        return prog.simpleQueryForLong();
    }

    public static String stringForQuery(SQLiteDatabase db, String query, String[] selectionArgs) {
        SQLiteStatement prog = db.compileStatement(query);
        try {
            return stringForQuery(prog, selectionArgs);
        } finally {
            prog.close();
        }
    }

    public static String stringForQuery(SQLiteStatement prog, String[] selectionArgs) {
        prog.bindAllArgsAsStrings(selectionArgs);
        return prog.simpleQueryForString();
    }

    public static ParcelFileDescriptor blobFileDescriptorForQuery(SQLiteDatabase db, String query, String[] selectionArgs) {
        SQLiteStatement prog = db.compileStatement(query);
        try {
            return blobFileDescriptorForQuery(prog, selectionArgs);
        } finally {
            prog.close();
        }
    }

    public static ParcelFileDescriptor blobFileDescriptorForQuery(SQLiteStatement prog, String[] selectionArgs) {
        prog.bindAllArgsAsStrings(selectionArgs);
        return prog.simpleQueryForBlobFileDescriptor();
    }

    public static void cursorStringToContentValuesIfPresent(Cursor cursor, ContentValues values, String column) {
        int index = cursor.getColumnIndex(column);
        if (index != -1 && !cursor.isNull(index)) {
            values.put(column, cursor.getString(index));
        }
    }

    public static void cursorLongToContentValuesIfPresent(Cursor cursor, ContentValues values, String column) {
        int index = cursor.getColumnIndex(column);
        if (index != -1 && !cursor.isNull(index)) {
            values.put(column, Long.valueOf(cursor.getLong(index)));
        }
    }

    public static void cursorShortToContentValuesIfPresent(Cursor cursor, ContentValues values, String column) {
        int index = cursor.getColumnIndex(column);
        if (index != -1 && !cursor.isNull(index)) {
            values.put(column, Short.valueOf(cursor.getShort(index)));
        }
    }

    public static void cursorIntToContentValuesIfPresent(Cursor cursor, ContentValues values, String column) {
        int index = cursor.getColumnIndex(column);
        if (index != -1 && !cursor.isNull(index)) {
            values.put(column, Integer.valueOf(cursor.getInt(index)));
        }
    }

    public static void cursorFloatToContentValuesIfPresent(Cursor cursor, ContentValues values, String column) {
        int index = cursor.getColumnIndex(column);
        if (index != -1 && !cursor.isNull(index)) {
            values.put(column, Float.valueOf(cursor.getFloat(index)));
        }
    }

    public static void cursorDoubleToContentValuesIfPresent(Cursor cursor, ContentValues values, String column) {
        int index = cursor.getColumnIndex(column);
        if (index != -1 && !cursor.isNull(index)) {
            values.put(column, Double.valueOf(cursor.getDouble(index)));
        }
    }

    @Deprecated
    public static class InsertHelper {
        public static final int TABLE_INFO_PRAGMA_COLUMNNAME_INDEX = 1;
        public static final int TABLE_INFO_PRAGMA_DEFAULT_INDEX = 4;
        private HashMap<String, Integer> mColumns;
        private final SQLiteDatabase mDb;
        private final String mTableName;
        private String mInsertSQL = null;
        private SQLiteStatement mInsertStatement = null;
        private SQLiteStatement mReplaceStatement = null;
        private SQLiteStatement mPreparedStatement = null;

        public InsertHelper(SQLiteDatabase db, String tableName) {
            this.mDb = db;
            this.mTableName = tableName;
        }

        private void buildSQL() throws SQLException {
            StringBuilder sb = new StringBuilder(128);
            sb.append("INSERT INTO ");
            sb.append(this.mTableName);
            sb.append(" (");
            StringBuilder sbv = new StringBuilder(128);
            sbv.append("VALUES (");
            int i = 1;
            Cursor cur = null;
            try {
                cur = this.mDb.rawQuery("PRAGMA table_info(" + this.mTableName + NavigationBarInflaterView.KEY_CODE_END, null);
                this.mColumns = new HashMap<>(cur.getCount());
                while (cur.moveToNext()) {
                    String columnName = cur.getString(1);
                    String defaultValue = cur.getString(4);
                    this.mColumns.put(columnName, Integer.valueOf(i));
                    sb.append("'");
                    sb.append(columnName);
                    sb.append("'");
                    if (defaultValue == null) {
                        sbv.append("?");
                    } else {
                        sbv.append("COALESCE(?, ");
                        sbv.append(defaultValue);
                        sbv.append(NavigationBarInflaterView.KEY_CODE_END);
                    }
                    sb.append(i == cur.getCount() ? ") " : ", ");
                    sbv.append(i == cur.getCount() ? ");" : ", ");
                    i++;
                }
                sb.append((CharSequence) sbv);
                this.mInsertSQL = sb.toString();
            } finally {
                if (cur != null) {
                    cur.close();
                }
            }
        }

        private SQLiteStatement getStatement(boolean allowReplace) throws SQLException {
            if (allowReplace) {
                if (this.mReplaceStatement == null) {
                    if (this.mInsertSQL == null) {
                        buildSQL();
                    }
                    String replaceSQL = "INSERT OR REPLACE" + this.mInsertSQL.substring(6);
                    this.mReplaceStatement = this.mDb.compileStatement(replaceSQL);
                }
                return this.mReplaceStatement;
            }
            if (this.mInsertStatement == null) {
                if (this.mInsertSQL == null) {
                    buildSQL();
                }
                this.mInsertStatement = this.mDb.compileStatement(this.mInsertSQL);
            }
            return this.mInsertStatement;
        }

        private long insertInternal(ContentValues values, boolean allowReplace) {
            this.mDb.beginTransactionNonExclusive();
            try {
                try {
                    SQLiteStatement stmt = getStatement(allowReplace);
                    stmt.clearBindings();
                    for (Map.Entry<String, Object> e : values.valueSet()) {
                        String key = e.getKey();
                        int i = getColumnIndex(key);
                        DatabaseUtils.bindObjectToProgram(stmt, i, e.getValue());
                    }
                    long result = stmt.executeInsert();
                    this.mDb.setTransactionSuccessful();
                    return result;
                } catch (SQLException e2) {
                    Log.e(DatabaseUtils.TAG, "Error inserting " + values + " into table  " + this.mTableName, e2);
                    this.mDb.endTransaction();
                    return -1L;
                }
            } finally {
                this.mDb.endTransaction();
            }
        }

        public int getColumnIndex(String key) {
            getStatement(false);
            Integer index = this.mColumns.get(key);
            if (index == null) {
                throw new IllegalArgumentException("column '" + key + "' is invalid");
            }
            return index.intValue();
        }

        public void bind(int index, double value) {
            this.mPreparedStatement.bindDouble(index, value);
        }

        public void bind(int index, float value) {
            this.mPreparedStatement.bindDouble(index, value);
        }

        public void bind(int index, long value) {
            this.mPreparedStatement.bindLong(index, value);
        }

        public void bind(int index, int value) {
            this.mPreparedStatement.bindLong(index, value);
        }

        public void bind(int index, boolean value) {
            this.mPreparedStatement.bindLong(index, value ? 1L : 0L);
        }

        public void bindNull(int index) {
            this.mPreparedStatement.bindNull(index);
        }

        public void bind(int index, byte[] value) {
            if (value == null) {
                this.mPreparedStatement.bindNull(index);
            } else {
                this.mPreparedStatement.bindBlob(index, value);
            }
        }

        public void bind(int index, String value) {
            if (value == null) {
                this.mPreparedStatement.bindNull(index);
            } else {
                this.mPreparedStatement.bindString(index, value);
            }
        }

        public long insert(ContentValues values) {
            return insertInternal(values, false);
        }

        public long execute() {
            if (this.mPreparedStatement == null) {
                throw new IllegalStateException("you must prepare this inserter before calling execute");
            }
            try {
                try {
                    return this.mPreparedStatement.executeInsert();
                } catch (SQLException e) {
                    Log.e(DatabaseUtils.TAG, "Error executing InsertHelper with table " + this.mTableName, e);
                    this.mPreparedStatement = null;
                    return -1L;
                }
            } finally {
                this.mPreparedStatement = null;
            }
        }

        public void prepareForInsert() {
            this.mPreparedStatement = getStatement(false);
            this.mPreparedStatement.clearBindings();
        }

        public void prepareForReplace() {
            this.mPreparedStatement = getStatement(true);
            this.mPreparedStatement.clearBindings();
        }

        public long replace(ContentValues values) {
            return insertInternal(values, true);
        }

        public void close() {
            if (this.mInsertStatement != null) {
                this.mInsertStatement.close();
                this.mInsertStatement = null;
            }
            if (this.mReplaceStatement != null) {
                this.mReplaceStatement.close();
                this.mReplaceStatement = null;
            }
            this.mInsertSQL = null;
            this.mColumns = null;
        }
    }

    public static void createDbFromSqlStatements(Context context, String dbName, int dbVersion, String sqlStatements) {
        SQLiteDatabase db = context.openOrCreateDatabase(dbName, 0, null);
        String[] statements = TextUtils.split(sqlStatements, ";\n");
        for (String statement : statements) {
            if (!TextUtils.isEmpty(statement)) {
                db.execSQL(statement);
            }
        }
        db.setVersion(dbVersion);
        db.close();
    }

    private static String getSqlStatementPrefixSimple(String sql) {
        String sql2 = sql.trim();
        if (sql2.length() < 3) {
            return null;
        }
        return sql2.substring(0, 3).toUpperCase(Locale.ROOT);
    }

    private static String getSqlStatementPrefixExtendedRegex(String sql) {
        Matcher m = sPrefixPattern.matcher(sql);
        if (m.lookingAt()) {
            return m.group(2).toUpperCase(Locale.ROOT);
        }
        return null;
    }

    private static int getSqlStatementPrefixOffset(String s) {
        int limit = s.length() - 2;
        if (limit < 0) {
            return -1;
        }
        int i = 0;
        while (i < limit) {
            char c = s.charAt(i);
            if (c <= ' ') {
                i++;
            } else if (c == '-') {
                if (s.charAt(i + 1) != '-') {
                    return i;
                }
                int i2 = s.indexOf(10, i + 2);
                if (i2 < 0) {
                    return -1;
                }
                i = i2 + 1;
            } else if (c == '/') {
                if (s.charAt(i + 1) != '*') {
                    return i;
                }
                int i3 = i + 1;
                do {
                    int i4 = s.indexOf(42, i3 + 1);
                    if (i4 < 0) {
                        return -1;
                    }
                    i3 = i4 + 1;
                } while (s.charAt(i3) != '/');
                i = i3 + 1;
            } else {
                return i;
            }
        }
        return -1;
    }

    private static String getSqlStatementPrefixExtendedNoRegex(String sql) {
        int end;
        int n = getSqlStatementPrefixOffset(sql);
        if (n < 0 || n > (end = sql.length())) {
            return null;
        }
        int eos = Math.min(n + 3, end);
        return sql.substring(n, eos).toUpperCase(Locale.ROOT);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int categorizeStatement(String prefix, String sql) {
        char c;
        if (prefix == null) {
            return 99;
        }
        switch (prefix.hashCode()) {
            case 64905:
                if (prefix.equals("ALT")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 64948:
                if (prefix.equals("ANA")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 65153:
                if (prefix.equals("ATT")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case Sensor.SEM_TYPE_GRIP_SUB /* 65636 */:
                if (prefix.equals("BEG")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 66913:
                if (prefix.equals("COM")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 66998:
                if (prefix.equals("CRE")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 67563:
                if (prefix.equals("DEL")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 67571:
                if (prefix.equals("DET")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 67969:
                if (prefix.equals("DRO")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 68795:
                if (prefix.equals("END")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 72654:
                if (prefix.equals("INS")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 79487:
                if (prefix.equals("PRA")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 81021:
                if (prefix.equals("REP")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 81327:
                if (prefix.equals("ROL")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 81978:
                if (prefix.equals("SEL")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 84233:
                if (prefix.equals("UPD")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 85954:
                if (prefix.equals("WIT")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                break;
            case 5:
                break;
            case 6:
            case 7:
                break;
            case '\b':
                if (sql.toUpperCase(Locale.ROOT).contains(" TO ")) {
                }
                break;
            case '\t':
                break;
            case '\n':
                break;
            case 11:
                break;
            case '\f':
            case '\r':
                break;
            case 14:
            case 15:
                break;
            case 16:
                break;
            default:
                if (prefix.startsWith("--") || prefix.startsWith(IntentFilter.WILDCARD_PATH)) {
                }
                break;
        }
        return 99;
    }

    public static int getSqlStatementTypeExtended(String sql) {
        if (Flags.simpleSqlCommentScanner()) {
            return categorizeStatement(getSqlStatementPrefixExtendedNoRegex(sql), sql);
        }
        int type = categorizeStatement(getSqlStatementPrefixSimple(sql), sql);
        if (type == 102) {
            return categorizeStatement(getSqlStatementPrefixExtendedRegex(sql), sql);
        }
        return type;
    }

    public static int getSqlStatementType(int extended) {
        switch (extended) {
            case 100:
                return 99;
            case 101:
                return 8;
            case 102:
                return 99;
            default:
                return extended;
        }
    }

    public static int getSqlStatementType(String sql) {
        return getSqlStatementType(getSqlStatementTypeExtended(sql));
    }

    public static String[] appendSelectionArgs(String[] originalValues, String[] newValues) {
        if (originalValues == null || originalValues.length == 0) {
            return newValues;
        }
        String[] result = new String[originalValues.length + newValues.length];
        System.arraycopy(originalValues, 0, result, 0, originalValues.length);
        System.arraycopy(newValues, 0, result, originalValues.length, newValues.length);
        return result;
    }

    public static int findRowIdColumnIndex(String[] columnNames) {
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            if (columnNames[i].equals("_id")) {
                return i;
            }
        }
        return -1;
    }

    public static String escapeForLike(String arg) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arg.length(); i++) {
            char c = arg.charAt(i);
            switch (c) {
                case '%':
                    sb.append('\\');
                    break;
                case '_':
                    sb.append('\\');
                    break;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
