package android.widget;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telecom.PhoneAccount;
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
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.Theme);
        this.mOverlay = typedArrayObtainStyledAttributes.getDrawable(345);
        typedArrayObtainStyledAttributes.recycle();
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

        /* JADX WARN: Removed duplicated region for block: B:33:0x006e  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x007d  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
        @Override // android.content.AsyncQueryHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        protected void onQueryComplete(int i, Object obj, Cursor cursor) {
            Uri uriFromParts;
            boolean z;
            Bundle bundle = obj != null ? (Bundle) obj : new Bundle();
            Uri lookupUri = null;
            boolean z2 = false;
            try {
                if (i != 0) {
                    if (i == 1) {
                        uriFromParts = null;
                        z = false;
                    } else if (i == 2) {
                        uriFromParts = Uri.fromParts("mailto", bundle.getString(QuickContactBadge.EXTRA_URI_CONTENT), null);
                        z = true;
                    } else if (i == 3) {
                        uriFromParts = Uri.fromParts(PhoneAccount.SCHEME_TEL, bundle.getString(QuickContactBadge.EXTRA_URI_CONTENT), null);
                        z = true;
                    } else {
                        uriFromParts = null;
                        if (cursor != null) {
                            cursor.close();
                        }
                        QuickContactBadge.this.mContactUri = lookupUri;
                        QuickContactBadge.this.onContactUriChanged();
                        if (!z2 && QuickContactBadge.this.mContactUri != null) {
                            Context context = QuickContactBadge.this.getContext();
                            QuickContactBadge quickContactBadge = QuickContactBadge.this;
                            ContactsContract.QuickContact.showQuickContact(context, quickContactBadge, quickContactBadge.mContactUri, QuickContactBadge.this.mExcludeMimes, QuickContactBadge.this.mPrioritizedMimeType);
                            return;
                        } else {
                            if (uriFromParts != null) {
                                Intent intent = new Intent("com.android.contacts.action.SHOW_OR_CREATE_CONTACT", uriFromParts);
                                if (bundle != null) {
                                    Bundle bundle2 = new Bundle(bundle);
                                    bundle2.remove(QuickContactBadge.EXTRA_URI_CONTENT);
                                    intent.putExtras(bundle2);
                                }
                                QuickContactBadge.this.getContext().startActivity(intent);
                                return;
                            }
                            return;
                        }
                    }
                    if (cursor != null && cursor.moveToFirst()) {
                        lookupUri = ContactsContract.Contacts.getLookupUri(cursor.getLong(0), cursor.getString(1));
                    }
                    z2 = z;
                    if (cursor != null) {
                    }
                    QuickContactBadge.this.mContactUri = lookupUri;
                    QuickContactBadge.this.onContactUriChanged();
                    if (!z2) {
                    }
                    if (uriFromParts != null) {
                    }
                } else {
                    uriFromParts = null;
                    z = false;
                }
                if (cursor != null && cursor.moveToFirst()) {
                    lookupUri = ContactsContract.Contacts.getLookupUri(cursor.getLong(0), cursor.getString(1));
                }
                z2 = z;
                if (cursor != null) {
                }
                QuickContactBadge.this.mContactUri = lookupUri;
                QuickContactBadge.this.onContactUriChanged();
                if (!z2) {
                }
                if (uriFromParts != null) {
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
    }
}
