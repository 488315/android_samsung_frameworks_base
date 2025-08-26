package android.database.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.CancellationSignal;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.util.EmptyArray;

/* loaded from: classes.dex */
public class SQLiteQueryBuilder {
    private static final int STRICT_COLUMNS = 2;
    private static final int STRICT_GRAMMAR = 4;
    private static final int STRICT_PARENTHESES = 1;
    private static final String TAG = "SQLiteQueryBuilder";
    private static final Pattern sAggregationPattern = Pattern.compile("(?i)(AVG|COUNT|MAX|MIN|SUM|TOTAL|GROUP_CONCAT)\\((.+)\\)");
    private int mStrictFlags;
    private Map<String, String> mProjectionMap = null;
    private Collection<Pattern> mProjectionGreylist = null;
    private String mTables = "";
    private StringBuilder mWhereClause = null;
    private boolean mDistinct = false;
    private SQLiteDatabase.CursorFactory mFactory = null;

    @Deprecated
    public boolean isProjectionAggregationAllowed() {
        return true;
    }

    @Deprecated
    public void setProjectionAggregationAllowed(boolean z) {
    }

    public void setDistinct(boolean z) {
        this.mDistinct = z;
    }

    public boolean isDistinct() {
        return this.mDistinct;
    }

    public String getTables() {
        return this.mTables;
    }

    public void setTables(String str) {
        this.mTables = str;
    }

    public void appendWhere(CharSequence charSequence) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(charSequence.length() + 16);
        }
        this.mWhereClause.append(charSequence);
    }

    public void appendWhereEscapeString(String str) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(str.length() + 16);
        }
        DatabaseUtils.appendEscapedSQLString(this.mWhereClause, str);
    }

    public void appendWhereStandalone(CharSequence charSequence) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(charSequence.length() + 16);
        }
        if (this.mWhereClause.length() > 0) {
            this.mWhereClause.append(" AND ");
        }
        StringBuilder sb = this.mWhereClause;
        sb.append('(');
        sb.append(charSequence);
        sb.append(')');
    }

    public void setProjectionMap(Map<String, String> map) {
        this.mProjectionMap = map;
    }

    public Map<String, String> getProjectionMap() {
        return this.mProjectionMap;
    }

    public void setProjectionGreylist(Collection<Pattern> collection) {
        this.mProjectionGreylist = collection;
    }

    public Collection<Pattern> getProjectionGreylist() {
        return this.mProjectionGreylist;
    }

    public void setCursorFactory(SQLiteDatabase.CursorFactory cursorFactory) {
        this.mFactory = cursorFactory;
    }

    public SQLiteDatabase.CursorFactory getCursorFactory() {
        return this.mFactory;
    }

    public void setStrict(boolean z) {
        if (z) {
            this.mStrictFlags |= 1;
        } else {
            this.mStrictFlags &= -2;
        }
    }

    public boolean isStrict() {
        return (this.mStrictFlags & 1) != 0;
    }

    public void setStrictColumns(boolean z) {
        if (z) {
            this.mStrictFlags |= 2;
        } else {
            this.mStrictFlags &= -3;
        }
    }

    public boolean isStrictColumns() {
        return (this.mStrictFlags & 2) != 0;
    }

    public void setStrictGrammar(boolean z) {
        if (z) {
            this.mStrictFlags |= 4;
        } else {
            this.mStrictFlags &= -5;
        }
    }

    public boolean isStrictGrammar() {
        return (this.mStrictFlags & 4) != 0;
    }

    public static String buildQueryString(boolean z, String str, String[] strArr, String str2, String str3, String str4, String str5, String str6) {
        if (TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4)) {
            throw new IllegalArgumentException("HAVING clauses are only permitted when using a groupBy clause");
        }
        StringBuilder sb = new StringBuilder(120);
        sb.append("SELECT ");
        if (z) {
            sb.append("DISTINCT ");
        }
        if (strArr != null && strArr.length != 0) {
            appendColumns(sb, strArr);
        } else {
            sb.append("* ");
        }
        sb.append("FROM ");
        sb.append(str);
        appendClause(sb, " WHERE ", str2);
        appendClause(sb, " GROUP BY ", str3);
        appendClause(sb, " HAVING ", str4);
        appendClause(sb, " ORDER BY ", str5);
        appendClause(sb, " LIMIT ", str6);
        return sb.toString();
    }

    private static void appendClause(StringBuilder sb, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        sb.append(str);
        sb.append(str2);
    }

    public static void appendColumns(StringBuilder sb, String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            if (str != null) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(str);
            }
        }
        sb.append(' ');
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String[] strArr, String str, String[] strArr2, String str2, String str3, String str4) {
        return query(sQLiteDatabase, strArr, str, strArr2, str2, str3, str4, null, null);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5) {
        return query(sQLiteDatabase, strArr, str, strArr2, str2, str3, str4, str5, null);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5, CancellationSignal cancellationSignal) {
        CancellationSignal cancellationSignal2;
        if (this.mTables == null) {
            return null;
        }
        String strBuildQuery = buildQuery(strArr, str, str2, str3, str4, str5);
        if (isStrictColumns()) {
            enforceStrictColumns(strArr);
        }
        if (isStrictGrammar()) {
            enforceStrictGrammar(str, str2, str3, str4, str5);
        }
        if (isStrict()) {
            cancellationSignal2 = cancellationSignal;
            sQLiteDatabase.validateSql(strBuildQuery, cancellationSignal2);
            strBuildQuery = buildQuery(strArr, wrap(str), str2, wrap(str3), str4, str5);
        } else {
            cancellationSignal2 = cancellationSignal;
        }
        String str6 = strBuildQuery;
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.d(TAG, str6 + " with args " + Arrays.toString(strArr2));
            } else {
                Log.d(TAG, str6);
            }
        }
        return sQLiteDatabase.rawQueryWithFactory(this.mFactory, str6, strArr2, SQLiteDatabase.findEditTable(this.mTables), cancellationSignal2);
    }

    public long insert(SQLiteDatabase sQLiteDatabase, ContentValues contentValues) {
        Objects.requireNonNull(this.mTables, "No tables defined");
        Objects.requireNonNull(sQLiteDatabase, "No database defined");
        Objects.requireNonNull(contentValues, "No values defined");
        if (isStrictColumns()) {
            enforceStrictColumns(contentValues);
        }
        String strBuildInsert = buildInsert(contentValues);
        ArrayMap<String, Object> values = contentValues.getValues();
        int size = values.size();
        Object[] objArr = new Object[size];
        for (int i = 0; i < size; i++) {
            objArr[i] = values.valueAt(i);
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.d(TAG, strBuildInsert + " with args " + Arrays.toString(objArr));
            } else {
                Log.d(TAG, strBuildInsert);
            }
        }
        return DatabaseUtils.executeInsert(sQLiteDatabase, strBuildInsert, objArr);
    }

    public int update(SQLiteDatabase sQLiteDatabase, ContentValues contentValues, String str, String[] strArr) {
        SQLiteQueryBuilder sQLiteQueryBuilder;
        String str2;
        Objects.requireNonNull(this.mTables, "No tables defined");
        Objects.requireNonNull(sQLiteDatabase, "No database defined");
        Objects.requireNonNull(contentValues, "No values defined");
        String strBuildUpdate = buildUpdate(contentValues, str);
        if (isStrictColumns()) {
            enforceStrictColumns(contentValues);
        }
        if (isStrictGrammar()) {
            sQLiteQueryBuilder = this;
            str2 = str;
            sQLiteQueryBuilder.enforceStrictGrammar(str2, null, null, null, null);
        } else {
            sQLiteQueryBuilder = this;
            str2 = str;
        }
        if (sQLiteQueryBuilder.isStrict()) {
            sQLiteDatabase.validateSql(strBuildUpdate, null);
            strBuildUpdate = sQLiteQueryBuilder.buildUpdate(contentValues, sQLiteQueryBuilder.wrap(str2));
        }
        if (strArr == null) {
            strArr = EmptyArray.STRING;
        }
        ArrayMap<String, Object> values = contentValues.getValues();
        int size = values.size();
        int length = strArr.length + size;
        Object[] objArr = new Object[length];
        for (int i = 0; i < length; i++) {
            if (i < size) {
                objArr[i] = values.valueAt(i);
            } else {
                objArr[i] = strArr[i - size];
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.d(TAG, strBuildUpdate + " with args " + Arrays.toString(objArr));
            } else {
                Log.d(TAG, strBuildUpdate);
            }
        }
        return DatabaseUtils.executeUpdateDelete(sQLiteDatabase, strBuildUpdate, objArr);
    }

    public int delete(SQLiteDatabase sQLiteDatabase, String str, String[] strArr) {
        SQLiteQueryBuilder sQLiteQueryBuilder;
        String str2;
        Objects.requireNonNull(this.mTables, "No tables defined");
        Objects.requireNonNull(sQLiteDatabase, "No database defined");
        String strBuildDelete = buildDelete(str);
        if (isStrictGrammar()) {
            sQLiteQueryBuilder = this;
            str2 = str;
            sQLiteQueryBuilder.enforceStrictGrammar(str2, null, null, null, null);
        } else {
            sQLiteQueryBuilder = this;
            str2 = str;
        }
        if (sQLiteQueryBuilder.isStrict()) {
            sQLiteDatabase.validateSql(strBuildDelete, null);
            strBuildDelete = sQLiteQueryBuilder.buildDelete(sQLiteQueryBuilder.wrap(str2));
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.d(TAG, strBuildDelete + " with args " + Arrays.toString(strArr));
            } else {
                Log.d(TAG, strBuildDelete);
            }
        }
        return DatabaseUtils.executeUpdateDelete(sQLiteDatabase, strBuildDelete, strArr);
    }

    private void enforceStrictColumns(String[] strArr) {
        Objects.requireNonNull(this.mProjectionMap, "No projection map defined");
        computeProjection(strArr);
    }

    private void enforceStrictColumns(ContentValues contentValues) {
        Objects.requireNonNull(this.mProjectionMap, "No projection map defined");
        ArrayMap<String, Object> values = contentValues.getValues();
        for (int i = 0; i < values.size(); i++) {
            String strKeyAt = values.keyAt(i);
            if (!this.mProjectionMap.containsKey(strKeyAt)) {
                throw new IllegalArgumentException("Invalid column " + strKeyAt);
            }
        }
    }

    private void enforceStrictGrammar(String str, String str2, String str3, String str4, String str5) {
        SQLiteTokenizer.tokenize(str, 0, new Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.enforceStrictToken((String) obj);
            }
        });
        SQLiteTokenizer.tokenize(str2, 0, new Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.enforceStrictToken((String) obj);
            }
        });
        SQLiteTokenizer.tokenize(str3, 0, new Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.enforceStrictToken((String) obj);
            }
        });
        SQLiteTokenizer.tokenize(str4, 0, new Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.enforceStrictToken((String) obj);
            }
        });
        SQLiteTokenizer.tokenize(str5, 0, new Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.enforceStrictToken((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceStrictToken(String str) {
        boolean zIsKeyword;
        if (TextUtils.isEmpty(str) || isTableOrColumn(str) || SQLiteTokenizer.isFunction(str) || SQLiteTokenizer.isType(str)) {
            return;
        }
        zIsKeyword = SQLiteTokenizer.isKeyword(str);
        String upperCase = str.toUpperCase(Locale.US);
        upperCase.hashCode();
        switch (upperCase) {
            case "SELECT":
            case "VALUES":
            case "WINDOW":
            case "FROM":
            case "GROUP":
            case "LIMIT":
            case "ORDER":
            case "WHERE":
            case "HAVING":
                zIsKeyword = false;
                break;
        }
        if (zIsKeyword) {
            return;
        }
        throw new IllegalArgumentException("Invalid token " + str);
    }

    public String buildQuery(String[] strArr, String str, String str2, String str3, String str4, String str5) {
        return buildQueryString(this.mDistinct, this.mTables, computeProjection(strArr), computeWhere(str), str2, str3, str4, str5);
    }

    @Deprecated
    public String buildQuery(String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5) {
        return buildQuery(strArr, str, str2, str3, str4, str5);
    }

    public String buildInsert(ContentValues contentValues) {
        if (contentValues == null || contentValues.isEmpty()) {
            throw new IllegalArgumentException("Empty values");
        }
        StringBuilder sb = new StringBuilder(120);
        sb.append("INSERT INTO ");
        sb.append(SQLiteDatabase.findEditTable(this.mTables));
        sb.append(" (");
        ArrayMap<String, Object> values = contentValues.getValues();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(values.keyAt(i));
        }
        sb.append(") VALUES (");
        for (int i2 = 0; i2 < values.size(); i2++) {
            if (i2 > 0) {
                sb.append(',');
            }
            sb.append('?');
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public String buildUpdate(ContentValues contentValues, String str) {
        if (contentValues == null || contentValues.isEmpty()) {
            throw new IllegalArgumentException("Empty values");
        }
        StringBuilder sb = new StringBuilder(120);
        sb.append("UPDATE ");
        sb.append(SQLiteDatabase.findEditTable(this.mTables));
        sb.append(" SET ");
        ArrayMap<String, Object> values = contentValues.getValues();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(values.keyAt(i));
            sb.append("=?");
        }
        appendClause(sb, " WHERE ", computeWhere(str));
        return sb.toString();
    }

    public String buildDelete(String str) {
        StringBuilder sb = new StringBuilder(120);
        sb.append("DELETE FROM ");
        sb.append(SQLiteDatabase.findEditTable(this.mTables));
        appendClause(sb, " WHERE ", computeWhere(str));
        return sb.toString();
    }

    public String buildUnionSubQuery(String str, String[] strArr, Set<String> set, int i, String str2, String str3, String str4, String str5) {
        int length = strArr.length;
        String[] strArr2 = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            String str6 = strArr[i2];
            if (str6.equals(str)) {
                strArr2[i2] = "'" + str2 + "' AS " + str;
            } else if (i2 <= i || set.contains(str6)) {
                strArr2[i2] = str6;
            } else {
                strArr2[i2] = "NULL AS " + str6;
            }
        }
        return buildQuery(strArr2, str3, str4, str5, null, null);
    }

    @Deprecated
    public String buildUnionSubQuery(String str, String[] strArr, Set<String> set, int i, String str2, String str3, String[] strArr2, String str4, String str5) {
        return buildUnionSubQuery(str, strArr, set, i, str2, str3, str4, str5);
    }

    public String buildUnionQuery(String[] strArr, String str, String str2) {
        StringBuilder sb = new StringBuilder(128);
        int length = strArr.length;
        String str3 = this.mDistinct ? " UNION " : " UNION ALL ";
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(str3);
            }
            sb.append(strArr[i]);
        }
        appendClause(sb, " ORDER BY ", str);
        appendClause(sb, " LIMIT ", str2);
        return sb.toString();
    }

    private static String maybeWithOperator(String str, String str2) {
        if (str == null) {
            return str2;
        }
        return str + NavigationBarInflaterView.KEY_CODE_START + str2 + NavigationBarInflaterView.KEY_CODE_END;
    }

    public String[] computeProjection(String[] strArr) {
        int i = 0;
        if (!ArrayUtils.isEmpty(strArr)) {
            String[] strArr2 = new String[strArr.length];
            while (i < strArr.length) {
                strArr2[i] = computeSingleProjectionOrThrow(strArr[i]);
                i++;
            }
            return strArr2;
        }
        Map<String, String> map = this.mProjectionMap;
        if (map == null) {
            return null;
        }
        Set<Map.Entry<String, String>> setEntrySet = map.entrySet();
        String[] strArr3 = new String[setEntrySet.size()];
        for (Map.Entry<String, String> entry : setEntrySet) {
            if (!entry.getKey().equals(BaseColumns._COUNT)) {
                strArr3[i] = entry.getValue();
                i++;
            }
        }
        return strArr3;
    }

    private String computeSingleProjectionOrThrow(String str) {
        String strComputeSingleProjection = computeSingleProjection(str);
        if (strComputeSingleProjection != null) {
            return strComputeSingleProjection;
        }
        throw new IllegalArgumentException("Invalid column " + str);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String computeSingleProjection(String str) {
        String str2;
        String strGroup;
        String strGroup2;
        Map<String, String> map = this.mProjectionMap;
        if (map == null) {
            return str;
        }
        String str3 = map.get(str);
        if (str3 == null) {
            Matcher matcher = sAggregationPattern.matcher(str);
            if (matcher.matches()) {
                strGroup2 = matcher.group(1);
                strGroup = matcher.group(2);
                str2 = this.mProjectionMap.get(strGroup);
            } else {
                str2 = str3;
                strGroup = str;
                strGroup2 = null;
            }
        }
        if (str2 != null) {
            return maybeWithOperator(strGroup2, str2);
        }
        if (this.mStrictFlags == 0 && (strGroup.contains(" AS ") || strGroup.contains(" as "))) {
            return maybeWithOperator(strGroup2, strGroup);
        }
        Collection<Pattern> collection = this.mProjectionGreylist;
        if (collection != null) {
            Iterator<Pattern> it = collection.iterator();
            while (it.hasNext()) {
                if (it.next().matcher(strGroup).matches()) {
                    Log.w(TAG, "Allowing abusive custom column: " + strGroup);
                    return maybeWithOperator(strGroup2, strGroup);
                }
            }
        }
        return null;
    }

    private boolean isTableOrColumn(String str) {
        return this.mTables.equals(str) || computeSingleProjection(str) != null;
    }

    public String computeWhere(String str) {
        boolean zIsEmpty = TextUtils.isEmpty(this.mWhereClause);
        boolean zIsEmpty2 = TextUtils.isEmpty(str);
        if (zIsEmpty && zIsEmpty2) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (!zIsEmpty) {
            sb.append('(');
            sb.append((CharSequence) this.mWhereClause);
            sb.append(')');
        }
        if (!zIsEmpty && !zIsEmpty2) {
            sb.append(" AND ");
        }
        if (!zIsEmpty2) {
            sb.append('(');
            sb.append(str);
            sb.append(')');
        }
        return sb.toString();
    }

    private String wrap(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        return NavigationBarInflaterView.KEY_CODE_START + str + NavigationBarInflaterView.KEY_CODE_END;
    }
}
