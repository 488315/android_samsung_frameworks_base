package android.widget;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class QuickContactBadge extends ImageView implements View.OnClickListener {
    static final int EMAIL_ID_COLUMN_INDEX = 0;
    static final int EMAIL_LOOKUP_STRING_COLUMN_INDEX = 1;
    private static final String EXTRA_URI_CONTENT = "uri_content";
    static final int PHONE_ID_COLUMN_INDEX = 0;
    static final int PHONE_LOOKUP_STRING_COLUMN_INDEX = 1;
    private static final int TOKEN_EMAIL_LOOKUP = 0;
    private static final int TOKEN_EMAIL_LOOKUP_AND_TRIGGER = 2;
    private static final int TOKEN_PHONE_LOOKUP = 1;
    private static final int TOKEN_PHONE_LOOKUP_AND_TRIGGER = 3;
    private String mContactEmail;
    private String mContactPhone;
    private Uri mContactUri;
    private Drawable mDefaultAvatar;
    protected String[] mExcludeMimes;
    private Bundle mExtras;
    private Drawable mOverlay;
    private String mPrioritizedMimeType;
    private QueryHandler mQueryHandler;
    static final String[] EMAIL_LOOKUP_PROJECTION = {"contact_id", "lookup"};
    static final String[] PHONE_LOOKUP_PROJECTION = {"_id", "lookup"};

    public void setMode(int i) {
    }

    public QuickContactBadge(Context context) {
        this(context, null);
    }

    public QuickContactBadge(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QuickContactBadge(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public QuickContactBadge(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mExtras = null;
        this.mExcludeMimes = null;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.Theme);
        this.mOverlay = obtainStyledAttributes.getDrawable(345);
        obtainStyledAttributes.recycle();
        setOnClickListener(this);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            return;
        }
        this.mQueryHandler = new QueryHandler(this.mContext.getContentResolver());
    }

    @Override // android.widget.ImageView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mOverlay;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mOverlay;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    public void setPrioritizedMimeType(String str) {
        this.mPrioritizedMimeType = str;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Drawable drawable;
        super.onDraw(canvas);
        if (!isEnabled() || (drawable = this.mOverlay) == null || drawable.getIntrinsicWidth() == 0 || this.mOverlay.getIntrinsicHeight() == 0) {
            return;
        }
        this.mOverlay.setBounds(0, 0, getWidth(), getHeight());
        if (this.mPaddingTop == 0 && this.mPaddingLeft == 0) {
            this.mOverlay.draw(canvas);
            return;
        }
        int saveCount = canvas.getSaveCount();
        canvas.save();
        canvas.translate(this.mPaddingLeft, this.mPaddingTop);
        this.mOverlay.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    private boolean isAssigned() {
        return (this.mContactUri == null && this.mContactEmail == null && this.mContactPhone == null) ? false : true;
    }

    public void setImageToDefault() {
        if (this.mDefaultAvatar == null) {
            this.mDefaultAvatar = this.mContext.getDrawable(R.drawable.ic_contact_picture);
        }
        lambda$setImageURIAsync$2(this.mDefaultAvatar);
    }

    public void assignContactUri(Uri uri) {
        this.mContactUri = uri;
        this.mContactEmail = null;
        this.mContactPhone = null;
        onContactUriChanged();
    }

    public void assignContactFromEmail(String str, boolean z) {
        assignContactFromEmail(str, z, null);
    }

    public void assignContactFromEmail(String str, boolean z, Bundle bundle) {
        QueryHandler queryHandler;
        this.mContactEmail = str;
        this.mExtras = bundle;
        if (!z && (queryHandler = this.mQueryHandler) != null) {
            queryHandler.startQuery(0, null, Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(this.mContactEmail)), EMAIL_LOOKUP_PROJECTION, null, null, null);
        } else {
            this.mContactUri = null;
            onContactUriChanged();
        }
    }

    public void assignContactFromPhone(String str, boolean z) {
        assignContactFromPhone(str, z, new Bundle());
    }

    public void assignContactFromPhone(String str, boolean z, Bundle bundle) {
        QueryHandler queryHandler;
        this.mContactPhone = str;
        this.mExtras = bundle;
        if (!z && (queryHandler = this.mQueryHandler) != null) {
            queryHandler.startQuery(1, null, Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, this.mContactPhone), PHONE_LOOKUP_PROJECTION, null, null, null);
        } else {
            this.mContactUri = null;
            onContactUriChanged();
        }
    }

    public void setOverlay(Drawable drawable) {
        this.mOverlay = drawable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onContactUriChanged() {
        setEnabled(isAssigned());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            bundle = new Bundle();
        }
        Bundle bundle2 = bundle;
        if (this.mContactUri != null) {
            ContactsContract.QuickContact.showQuickContact(getContext(), this, this.mContactUri, this.mExcludeMimes, this.mPrioritizedMimeType);
            return;
        }
        String str = this.mContactEmail;
        if (str != null && this.mQueryHandler != null) {
            bundle2.putString(EXTRA_URI_CONTENT, str);
            this.mQueryHandler.startQuery(2, bundle2, Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(this.mContactEmail)), EMAIL_LOOKUP_PROJECTION, null, null, null);
            return;
        }
        String str2 = this.mContactPhone;
        if (str2 == null || this.mQueryHandler == null) {
            return;
        }
        bundle2.putString(EXTRA_URI_CONTENT, str2);
        this.mQueryHandler.startQuery(3, bundle2, Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, this.mContactPhone), PHONE_LOOKUP_PROJECTION, null, null, null);
    }

    @Override // android.widget.ImageView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return QuickContactBadge.class.getName();
    }

    public void setExcludeMimes(String[] strArr) {
        this.mExcludeMimes = strArr;
    }

    private class QueryHandler extends AsyncQueryHandler {
        public QueryHandler(ContentResolver contentResolver) {
            super(contentResolver);
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x006e  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x007d  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
        @Override // android.content.AsyncQueryHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void onQueryComplete(int r7, java.lang.Object r8, android.database.Cursor r9) {
            /*
                r6 = this;
                if (r8 == 0) goto L5
                android.os.Bundle r8 = (android.os.Bundle) r8
                goto La
            L5:
                android.os.Bundle r8 = new android.os.Bundle
                r8.<init>()
            La:
                java.lang.String r0 = "uri_content"
                r1 = 1
                r2 = 0
                r3 = 0
                if (r7 == 0) goto L4e
                if (r7 == r1) goto L37
                r4 = 2
                if (r7 == r4) goto L29
                r4 = 3
                if (r7 == r4) goto L1c
                r7 = r2
                goto L6c
            L1c:
                java.lang.String r7 = "tel"
                java.lang.String r4 = r8.getString(r0)     // Catch: java.lang.Throwable -> L35
                android.net.Uri r7 = android.net.Uri.fromParts(r7, r4, r2)     // Catch: java.lang.Throwable -> L35
                r4 = r1
                goto L39
            L29:
                java.lang.String r7 = "mailto"
                java.lang.String r4 = r8.getString(r0)     // Catch: java.lang.Throwable -> L35
                android.net.Uri r7 = android.net.Uri.fromParts(r7, r4, r2)     // Catch: java.lang.Throwable -> L35
                r4 = r1
                goto L50
            L35:
                r6 = move-exception
                goto L65
            L37:
                r7 = r2
                r4 = r3
            L39:
                if (r9 == 0) goto L6b
                boolean r5 = r9.moveToFirst()     // Catch: java.lang.Throwable -> L35
                if (r5 == 0) goto L6b
                long r2 = r9.getLong(r3)     // Catch: java.lang.Throwable -> L35
                java.lang.String r1 = r9.getString(r1)     // Catch: java.lang.Throwable -> L35
                android.net.Uri r2 = android.provider.ContactsContract.Contacts.getLookupUri(r2, r1)     // Catch: java.lang.Throwable -> L35
                goto L6b
            L4e:
                r7 = r2
                r4 = r3
            L50:
                if (r9 == 0) goto L6b
                boolean r5 = r9.moveToFirst()     // Catch: java.lang.Throwable -> L35
                if (r5 == 0) goto L6b
                long r2 = r9.getLong(r3)     // Catch: java.lang.Throwable -> L35
                java.lang.String r1 = r9.getString(r1)     // Catch: java.lang.Throwable -> L35
                android.net.Uri r2 = android.provider.ContactsContract.Contacts.getLookupUri(r2, r1)     // Catch: java.lang.Throwable -> L35
                goto L6b
            L65:
                if (r9 == 0) goto L6a
                r9.close()
            L6a:
                throw r6
            L6b:
                r3 = r4
            L6c:
                if (r9 == 0) goto L71
                r9.close()
            L71:
                android.widget.QuickContactBadge r9 = android.widget.QuickContactBadge.this
                android.widget.QuickContactBadge.m7094$$Nest$fputmContactUri(r9, r2)
                android.widget.QuickContactBadge r9 = android.widget.QuickContactBadge.this
                android.widget.QuickContactBadge.m7095$$Nest$monContactUriChanged(r9)
                if (r3 == 0) goto L9f
                android.widget.QuickContactBadge r9 = android.widget.QuickContactBadge.this
                android.net.Uri r9 = android.widget.QuickContactBadge.m7092$$Nest$fgetmContactUri(r9)
                if (r9 == 0) goto L9f
                android.widget.QuickContactBadge r7 = android.widget.QuickContactBadge.this
                android.content.Context r7 = r7.getContext()
                android.widget.QuickContactBadge r8 = android.widget.QuickContactBadge.this
                android.net.Uri r9 = android.widget.QuickContactBadge.m7092$$Nest$fgetmContactUri(r8)
                android.widget.QuickContactBadge r0 = android.widget.QuickContactBadge.this
                java.lang.String[] r0 = r0.mExcludeMimes
                android.widget.QuickContactBadge r6 = android.widget.QuickContactBadge.this
                java.lang.String r6 = android.widget.QuickContactBadge.m7093$$Nest$fgetmPrioritizedMimeType(r6)
                android.provider.ContactsContract.QuickContact.showQuickContact(r7, r8, r9, r0, r6)
                return
            L9f:
                if (r7 == 0) goto Lbe
                android.content.Intent r9 = new android.content.Intent
                java.lang.String r1 = "com.android.contacts.action.SHOW_OR_CREATE_CONTACT"
                r9.<init>(r1, r7)
                if (r8 == 0) goto Lb5
                android.os.Bundle r7 = new android.os.Bundle
                r7.<init>(r8)
                r7.remove(r0)
                r9.putExtras(r7)
            Lb5:
                android.widget.QuickContactBadge r6 = android.widget.QuickContactBadge.this
                android.content.Context r6 = r6.getContext()
                r6.startActivity(r9)
            Lbe:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.QuickContactBadge.QueryHandler.onQueryComplete(int, java.lang.Object, android.database.Cursor):void");
        }
    }
}
