package androidx.sqlite.db.framework;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.CancellationSignal;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.sqlite.db.framework.FrameworkSQLiteDatabase;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FrameworkSQLiteDatabase implements SupportSQLiteDatabase {
    public static final Companion Companion = new Companion(null);
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Lazy beginTransactionMethod$delegate;
    public static final Lazy getThreadSessionMethod$delegate;
    public final SQLiteDatabase delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final int i = 0;
        getThreadSessionMethod$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.sqlite.db.framework.FrameworkSQLiteDatabase$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class<?> returnType;
                switch (i) {
                    case 0:
                        FrameworkSQLiteDatabase.Companion companion = FrameworkSQLiteDatabase.Companion;
                        try {
                            Class[] clsArr = new Class[0];
                            Method declaredMethod = SQLiteDatabase.class.getDeclaredMethod("getThreadSession", null);
                            declaredMethod.setAccessible(true);
                            return declaredMethod;
                        } catch (Throwable unused) {
                            return null;
                        }
                    default:
                        try {
                            FrameworkSQLiteDatabase.Companion.getClass();
                            Method method = (Method) FrameworkSQLiteDatabase.getThreadSessionMethod$delegate.getValue();
                            if (method == null || (returnType = method.getReturnType()) == null) {
                                return null;
                            }
                            Class<?> cls = Integer.TYPE;
                            return returnType.getDeclaredMethod("beginTransaction", cls, SQLiteTransactionListener.class, cls, CancellationSignal.class);
                        } catch (Throwable unused2) {
                            return null;
                        }
                }
            }
        });
        final int i2 = 1;
        beginTransactionMethod$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.sqlite.db.framework.FrameworkSQLiteDatabase$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class<?> returnType;
                switch (i2) {
                    case 0:
                        FrameworkSQLiteDatabase.Companion companion = FrameworkSQLiteDatabase.Companion;
                        try {
                            Class[] clsArr = new Class[0];
                            Method declaredMethod = SQLiteDatabase.class.getDeclaredMethod("getThreadSession", null);
                            declaredMethod.setAccessible(true);
                            return declaredMethod;
                        } catch (Throwable unused) {
                            return null;
                        }
                    default:
                        try {
                            FrameworkSQLiteDatabase.Companion.getClass();
                            Method method = (Method) FrameworkSQLiteDatabase.getThreadSessionMethod$delegate.getValue();
                            if (method == null || (returnType = method.getReturnType()) == null) {
                                return null;
                            }
                            Class<?> cls = Integer.TYPE;
                            return returnType.getDeclaredMethod("beginTransaction", cls, SQLiteTransactionListener.class, cls, CancellationSignal.class);
                        } catch (Throwable unused2) {
                            return null;
                        }
                }
            }
        });
    }

    public FrameworkSQLiteDatabase(SQLiteDatabase sQLiteDatabase) {
        this.delegate = sQLiteDatabase;
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final void beginTransaction() {
        this.delegate.beginTransaction();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final void beginTransactionNonExclusive() {
        this.delegate.beginTransactionNonExclusive();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final void beginTransactionReadOnly() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Companion.getClass();
        Lazy lazy = beginTransactionMethod$delegate;
        if (((Method) lazy.getValue()) != null) {
            Lazy lazy2 = getThreadSessionMethod$delegate;
            if (((Method) lazy2.getValue()) != null) {
                Method method = (Method) lazy.getValue();
                method.getClass();
                Method method2 = (Method) lazy2.getValue();
                method2.getClass();
                Object objInvoke = method2.invoke(this.delegate, null);
                if (objInvoke == null) {
                    throw new IllegalStateException("Required value was null.");
                }
                method.invoke(objInvoke, 0, null, 0, null);
                return;
            }
        }
        beginTransaction();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.delegate.close();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final SupportSQLiteStatement compileStatement(String str) {
        return new FrameworkSQLiteStatement(this.delegate.compileStatement(str));
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final void endTransaction() {
        this.delegate.endTransaction();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final void execSQL(String str) throws SQLException {
        this.delegate.execSQL(str);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final boolean inTransaction() {
        return this.delegate.inTransaction();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final boolean isOpen() {
        return this.delegate.isOpen();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final Cursor query() {
        return query(new SimpleSQLiteQuery("SELECT item_id, span_y FROM communal_widget_table"));
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final void setTransactionSuccessful() {
        this.delegate.setTransactionSuccessful();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public final Cursor query(SupportSQLiteQuery supportSQLiteQuery) {
        final FrameworkSQLiteDatabase$$ExternalSyntheticLambda0 frameworkSQLiteDatabase$$ExternalSyntheticLambda0 = new FrameworkSQLiteDatabase$$ExternalSyntheticLambda0(supportSQLiteQuery);
        return this.delegate.rawQueryWithFactory(new SQLiteDatabase.CursorFactory() { // from class: androidx.sqlite.db.framework.FrameworkSQLiteDatabase$$ExternalSyntheticLambda1
            @Override // android.database.sqlite.SQLiteDatabase.CursorFactory
            public final Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery) {
                FrameworkSQLiteDatabase$$ExternalSyntheticLambda0 frameworkSQLiteDatabase$$ExternalSyntheticLambda02 = frameworkSQLiteDatabase$$ExternalSyntheticLambda0;
                FrameworkSQLiteDatabase.Companion companion = FrameworkSQLiteDatabase.Companion;
                return (Cursor) frameworkSQLiteDatabase$$ExternalSyntheticLambda02.invoke(sQLiteDatabase, sQLiteCursorDriver, str, sQLiteQuery);
            }
        }, supportSQLiteQuery.getSql(), EMPTY_STRING_ARRAY, null);
    }
}
