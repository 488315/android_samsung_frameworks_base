package android.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;

/* loaded from: classes5.dex */
public abstract class SimpleCursorTreeAdapter extends ResourceCursorTreeAdapter {
    private int[] mChildFrom;
    private String[] mChildFromNames;
    private int[] mChildTo;
    private int[] mGroupFrom;
    private String[] mGroupFromNames;
    private int[] mGroupTo;
    private ViewBinder mViewBinder;

    public interface ViewBinder {
        boolean setViewValue(View view, Cursor cursor, int i);
    }

    public SimpleCursorTreeAdapter(Context context, Cursor cursor, int i, int i2, String[] strArr, int[] iArr, int i3, int i4, String[] strArr2, int[] iArr2) {
        super(context, cursor, i, i2, i3, i4);
        init(strArr, iArr, strArr2, iArr2);
    }

    public SimpleCursorTreeAdapter(Context context, Cursor cursor, int i, int i2, String[] strArr, int[] iArr, int i3, String[] strArr2, int[] iArr2) {
        super(context, cursor, i, i2, i3);
        init(strArr, iArr, strArr2, iArr2);
    }

    public SimpleCursorTreeAdapter(Context context, Cursor cursor, int i, String[] strArr, int[] iArr, int i2, String[] strArr2, int[] iArr2) {
        super(context, cursor, i, i2);
        init(strArr, iArr, strArr2, iArr2);
    }

    private void init(String[] strArr, int[] iArr, String[] strArr2, int[] iArr2) {
        this.mGroupFromNames = strArr;
        this.mGroupTo = iArr;
        this.mChildFromNames = strArr2;
        this.mChildTo = iArr2;
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    private void bindView(View view, Context context, Cursor cursor, int[] iArr, int[] iArr2) {
        ViewBinder viewBinder = this.mViewBinder;
        for (int i = 0; i < iArr2.length; i++) {
            View viewFindViewById = view.findViewById(iArr2[i]);
            if (viewFindViewById != null) {
                if (viewBinder != null ? viewBinder.setViewValue(viewFindViewById, cursor, iArr[i]) : false) {
                    continue;
                } else {
                    String string = cursor.getString(iArr[i]);
                    if (string == null) {
                        string = "";
                    }
                    if (viewFindViewById instanceof TextView) {
                        setViewText((TextView) viewFindViewById, string);
                    } else if (viewFindViewById instanceof ImageView) {
                        setViewImage((ImageView) viewFindViewById, string);
                    } else {
                        throw new IllegalStateException("SimpleCursorTreeAdapter can bind values only to TextView and ImageView!");
                    }
                }
            }
        }
    }

    private void initFromColumns(Cursor cursor, String[] strArr, int[] iArr) {
        for (int length = strArr.length - 1; length >= 0; length--) {
            iArr[length] = cursor.getColumnIndexOrThrow(strArr[length]);
        }
    }

    @Override // android.widget.CursorTreeAdapter
    protected void bindChildView(View view, Context context, Cursor cursor, boolean z) {
        if (this.mChildFrom == null) {
            String[] strArr = this.mChildFromNames;
            int[] iArr = new int[strArr.length];
            this.mChildFrom = iArr;
            initFromColumns(cursor, strArr, iArr);
        }
        bindView(view, context, cursor, this.mChildFrom, this.mChildTo);
    }

    @Override // android.widget.CursorTreeAdapter
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean z) {
        if (this.mGroupFrom == null) {
            String[] strArr = this.mGroupFromNames;
            int[] iArr = new int[strArr.length];
            this.mGroupFrom = iArr;
            initFromColumns(cursor, strArr, iArr);
        }
        bindView(view, context, cursor, this.mGroupFrom, this.mGroupTo);
    }

    protected void setViewImage(ImageView imageView, String str) {
        try {
            imageView.setImageResource(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            imageView.setImageURI(Uri.parse(str));
        }
    }

    public void setViewText(TextView textView, String str) {
        textView.lambda$setTextAsync$0(str);
    }
}
