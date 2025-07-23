package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SubscreenNotificationInfo implements Parcelable {
    public SubscreenNotificationController controller;
    public Drawable mAppIcon;
    public String mAppName;
    public int mAppPrimaryDefaultColor;
    public String mBigText;
    public String mBigTitle;
    public Bitmap mBitmap;
    public int mChildCount;
    public String mContent;
    public PendingIntent mContentIntent;
    public RemoteViews mContentView;
    public final Context mContext;
    public Icon mConversationIcon;
    public boolean mGroupSummary;
    public boolean mHasSemanticCall;
    public Drawable mIcon;
    public boolean mIsCall;
    public boolean mIsGroupConversation;
    public boolean mIsMessagingStyle;
    public boolean mIsMissedCall;
    public String mKey;
    public Drawable mKnoxBadgeDrawable;
    public Icon mLargeIcon;
    public boolean mNeedsOnePhoneIcon;
    public boolean mNeedsTwoPhoneIcon;
    public View mOngoingView;
    public String mPkg;
    public PendingIntent mRemoteInputActionIntent;
    public boolean mRemoteInputIsSms;
    public int mRemoteInputMaxLength;
    public String mRemoteInputSignature;
    public boolean mRemoteinput;
    public ExpandableNotificationRow mRow;
    public StatusBarNotification mSbn;
    public PendingIntent mSemanticCallPendingIntent;
    public boolean mShowSmallIcon;
    public boolean mShowWhen;
    public String mTitle;
    public int mUnreadMessageCnt;
    public boolean mUseSmallIcon;
    public long mWhen;
    public static final Parcelable.Creator<SubscreenNotificationInfo> CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationInfo.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new SubscreenNotificationInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SubscreenNotificationInfo[i];
        }
    };
    public static final Uri CONTENT_URI = Uri.parse("content://com.android.server.notification.provider");
    public final String[] mInBox = {null, null, null, null, null, null, null};
    public final ArrayList mMessageingStyleInfoArray = new ArrayList(25);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MessagingStyleInfo {
        public String mContentText;
        public boolean mIsChecked;
        public boolean mIsReply;
        public long mPostedTime;
        public String mSender;
        public long mTimeStamp;
        public Drawable mUriImage;
    }

    public SubscreenNotificationInfo(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0043  */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r9v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.Drawable queryContentUriInternal(android.content.Context r9, java.lang.String r10) {
        /*
            java.lang.String r0 = "image"
            java.lang.String r1 = "SQLiteException occurs in deleteContentUri because  "
            r2 = 0
            android.content.ContentResolver r3 = r9.getContentResolver()     // Catch: java.lang.Throwable -> L47 android.database.sqlite.SQLiteException -> L4b
            android.net.Uri r4 = com.android.systemui.statusbar.notification.SubscreenNotificationInfo.CONTENT_URI     // Catch: java.lang.Throwable -> L47 android.database.sqlite.SQLiteException -> L4b
            java.lang.String[] r5 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L47 android.database.sqlite.SQLiteException -> L4b
            java.lang.String r6 = "uri_id=?"
            java.lang.String[] r7 = new java.lang.String[]{r10}     // Catch: java.lang.Throwable -> L47 android.database.sqlite.SQLiteException -> L4b
            r8 = 0
            android.database.Cursor r9 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L47 android.database.sqlite.SQLiteException -> L4b
            if (r9 == 0) goto L40
            boolean r10 = r9.moveToFirst()     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3d
            if (r10 == 0) goto L40
            int r10 = r9.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3d
            byte[] r10 = r9.getBlob(r10)     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3d
            int r0 = r10.length     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3d
            if (r0 == 0) goto L40
            int r0 = r10.length     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3d
            r3 = 0
            android.graphics.Bitmap r10 = android.graphics.BitmapFactory.decodeByteArray(r10, r3, r0)     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3d
            android.graphics.drawable.BitmapDrawable r0 = new android.graphics.drawable.BitmapDrawable     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3d
            r0.<init>(r10)     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3d
            goto L41
        L3a:
            r0 = move-exception
            r10 = r0
            goto L69
        L3d:
            r0 = move-exception
            r10 = r0
            goto L4e
        L40:
            r0 = r2
        L41:
            if (r9 == 0) goto L68
            r9.close()
            return r0
        L47:
            r0 = move-exception
            r10 = r0
            r9 = r2
            goto L69
        L4b:
            r0 = move-exception
            r10 = r0
            r9 = r2
        L4e:
            java.lang.String r0 = "SubscreenNotificationInfo"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3a
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L3a
            java.lang.String r10 = r10.getMessage()     // Catch: java.lang.Throwable -> L3a
            r3.append(r10)     // Catch: java.lang.Throwable -> L3a
            java.lang.String r10 = r3.toString()     // Catch: java.lang.Throwable -> L3a
            android.util.Log.e(r0, r10)     // Catch: java.lang.Throwable -> L3a
            if (r9 == 0) goto L68
            r9.close()
        L68:
            return r2
        L69:
            if (r9 == 0) goto L6f
            r9.close()
            return r2
        L6f:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenNotificationInfo.queryContentUriInternal(android.content.Context, java.lang.String):android.graphics.drawable.Drawable");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final CharSequence findConversationTitle(StatusBarNotification statusBarNotification) {
        CharSequence charSequence = statusBarNotification.getNotification().extras.getCharSequence("android.conversationTitle");
        if (charSequence != null && !TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        Person person = (Person) statusBarNotification.getNotification().extras.getParcelable("android.messagingUser", Person.class);
        CharSequence name = person != null ? person.getName() : null;
        ArrayList arrayList = (ArrayList) getHistories(200);
        if (arrayList.size() > 0) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Bundle bundle = (Bundle) obj;
                int i2 = bundle.getInt("type", 0);
                String string = bundle.getString(UniversalCredentialUtil.AGENT_TITLE, "");
                if (i2 != 1 && name != null && !string.equals(name)) {
                    return string;
                }
            }
        }
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(statusBarNotification.getPackageContext(this.mContext), statusBarNotification.getNotification());
        if (recoverBuilder.getStyle() instanceof Notification.MessagingStyle) {
            for (Notification.MessagingStyle.Message message : ((Notification.MessagingStyle) recoverBuilder.getStyle()).getMessages()) {
                if (name != null && !TextUtils.isEmpty(name) && message.getSender() != null && !name.equals(message.getSender())) {
                    return message.getSender();
                }
            }
        }
        Log.d("SubscreenNotificationInfo", "coverscreen can't find conversation title properly so ,, return empty");
        return "";
    }

    public final String getContentHiddenText() {
        int i = (this.mGroupSummary && this.mRow.mIsSummaryWithChildren) ? this.mChildCount : 1;
        return this.mContext.getResources().getQuantityString(R.plurals.plural_notification_count, i, Integer.valueOf(i)).toString();
    }

    public final List getHistories(int i) {
        List semGetNotificationHistoryForPackage = ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).semGetNotificationHistoryForPackage(this.mContext.getPackageName(), this.mContext.getAttributionTag(), this.mSbn.getUserId(), this.mSbn.getPackageName(), this.mSbn.getKey(), i);
        ArrayList arrayList = new ArrayList();
        if (semGetNotificationHistoryForPackage != null) {
            Iterator it = semGetNotificationHistoryForPackage.iterator();
            while (it.hasNext()) {
                arrayList.add((Bundle) it.next());
            }
        }
        return arrayList;
    }

    public final String getTitle() {
        String str = this.mBigTitle;
        return str != null ? str : this.mTitle;
    }

    public final boolean isSportsOngoing() {
        return this.mRow != null && "com.samsung.android.app.aodservice".equals(this.mPkg) && this.mRow.mEntry.isOngoingActivity();
    }

    public final void makeConversation(ExpandableNotificationRow expandableNotificationRow) {
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        if (notificationEntry != null) {
            Notification notification2 = notificationEntry.mSbn.getNotification();
            Parcelable[] parcelableArray = notification2.extras.getParcelableArray("android.messages");
            Person person = (Person) notification2.extras.getParcelable("android.messagingUser", Person.class);
            for (Notification.MessagingStyle.Message message : Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray)) {
                MessagingStyleInfo messagingStyleInfo = new MessagingStyleInfo();
                String charSequence = message.getText() != null ? message.getText().toString() : "";
                messagingStyleInfo.mContentText = charSequence;
                Uri dataUri = message.getDataUri();
                if (dataUri != null) {
                    Drawable loadImage = expandableNotificationRow.mImageResolver.loadImage(dataUri);
                    if (loadImage == null) {
                        Log.d("SubscreenNotificationInfo", notificationEntry.mKey + " : no drawable for " + dataUri);
                    } else {
                        messagingStyleInfo.mUriImage = loadImage;
                    }
                }
                Person senderPerson = message.getSenderPerson();
                String charSequence2 = (senderPerson == null || senderPerson.getName() == null) ? (person == null || person.getName() == null) ? "" : person.getName().toString() : senderPerson.getName().toString();
                boolean z = senderPerson == null || !(person == null || person.getName() == null || !charSequence2.equals(person.getName().toString()));
                if ("com.viber.voip".equals(notificationEntry.mSbn.getPackageName())) {
                    z = "Me".equals(charSequence2);
                }
                messagingStyleInfo.mIsReply = z;
                messagingStyleInfo.mSender = charSequence2;
                messagingStyleInfo.mTimeStamp = message.getTimestamp();
                this.mMessageingStyleInfoArray.add(messagingStyleInfo);
                this.mContent = charSequence;
                if (!"".equals(findConversationTitle(notificationEntry.mSbn).toString())) {
                    this.mTitle = findConversationTitle(notificationEntry.mSbn).toString();
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:110:0x04ad  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x04f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setItemsData(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r23) {
        /*
            Method dump skipped, instructions count: 1325
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenNotificationInfo.setItemsData(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow):void");
    }

    public final boolean useSmallIcon() {
        boolean equals = "android".equals(this.mPkg);
        boolean equals2 = "com.android.systemui".equals(this.mPkg);
        boolean z = equals || equals2 || this.mUseSmallIcon || this.mShowSmallIcon;
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mKey);
            sb.append(": use small icon. androidPkg = ");
            sb.append(equals);
            sb.append(", systemuiPkg = ");
            sb.append(equals2);
            sb.append(", mUseSmallIcon = ");
            sb.append(this.mUseSmallIcon);
            sb.append(", mShowSmallIcon = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mShowSmallIcon, "SubscreenNotificationInfo");
        }
        return z;
    }

    public SubscreenNotificationInfo(Parcel parcel) {
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
    }
}
