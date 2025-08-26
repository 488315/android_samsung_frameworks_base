package com.samsung.android.sdk.routines.v3.internal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;

/* loaded from: classes4.dex */
public final class RoutineSdkContentProvider extends ContentProvider {

    /* renamed from: com.samsung.android.sdk.routines.v3.internal.RoutineSdkContentProvider$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ExtraValue.values().length];
            a = iArr;
            try {
                iArr[ExtraValue.CALL_TYPE_CONDITION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ExtraValue.CALL_TYPE_ACTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        ExtraValue extraValue;
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (getContext() == null) {
                Log.a("RoutineSdkContentProvider", "call - context is null");
                return null;
            }
            if (bundle == null) {
                Log.a("RoutineSdkContentProvider", "call - extras is null");
                return null;
            }
            String string = bundle.getString(ExtraKey.CALL_TYPE.a);
            if (string == null) {
                Log.a("RoutineSdkContentProvider", "call - callType is null");
                return null;
            }
            int[] iArr = AnonymousClass1.a;
            ExtraValue[] extraValueArrValues = ExtraValue.values();
            int length = extraValueArrValues.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    Log.a("ExtraValue", "ExtraValue - not supported value: " + string);
                    extraValue = ExtraValue.UNKNOWN;
                    break;
                }
                extraValue = extraValueArrValues[i];
                if (extraValue.a.equals(string)) {
                    break;
                }
                i++;
            }
            int i2 = iArr[extraValue.ordinal()];
            if (i2 == 1) {
                return new ConditionDispatcher().a(getContext(), str, bundle);
            }
            if (i2 == 2) {
                return new ActionDispatcher().a(getContext(), str, bundle);
            }
            Log.a("RoutineSdkContentProvider", "call - not supported callType: " + string);
            return null;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return -1;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return -1;
    }
}
