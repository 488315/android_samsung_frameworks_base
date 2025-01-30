package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenNotificationInfo implements Parcelable {
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
    public String mPkg;
    public PendingIntent mRemoteInputActionIntent;
    public boolean mRemoteInputIsSms;
    public int mRemoteInputMaxLength;
    public String mRemoteInputSignature;
    public boolean mRemoteinput;
    public ExpandableNotificationRow mRow;
    public StatusBarNotification mSbn;
    public PendingIntent mSemanticCallPendingIntent;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MessagingStyleInfo {
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Drawable queryContentUriInternal(Context context, String str) {
        Throwable th;
        Cursor cursor;
        SQLiteException e;
        BitmapDrawable bitmapDrawable;
        try {
            cursor = context.getContentResolver().query(CONTENT_URI, new String[]{"image"}, "uri_id=?", new String[]{str}, null);
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
            }
        }
        if (cursor != null) {
            try {
                try {
                } catch (SQLiteException e3) {
                    e = e3;
                    Log.e("SubscreenNotificationInfo", "SQLiteException occurs in deleteContentUri because  " + e.getMessage());
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                if (cursor.moveToFirst()) {
                    byte[] blob = cursor.getBlob(cursor.getColumnIndex("image"));
                    if (blob.length != 0) {
                        bitmapDrawable = new BitmapDrawable(BitmapFactory.decodeByteArray(blob, 0, blob.length));
                        if (cursor != null) {
                            cursor.close();
                            return bitmapDrawable;
                        }
                        return null;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                if (cursor != null) {
                    throw th;
                }
                cursor.close();
                return null;
            }
        }
        bitmapDrawable = null;
        if (cursor != null) {
        }
        return null;
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
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Bundle bundle = (Bundle) it.next();
                int i = bundle.getInt("type", 0);
                String string = bundle.getString(UniversalCredentialUtil.AGENT_TITLE, "");
                if (i != 1 && name != null && !string.equals(name)) {
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

    public final String getContentHiddenText(SubscreenNotificationInfo subscreenNotificationInfo) {
        int i = (subscreenNotificationInfo.mGroupSummary && subscreenNotificationInfo.mRow.mIsSummaryWithChildren) ? this.mChildCount : 1;
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

    public final boolean useSmallIcon() {
        boolean equals = "android".equals(this.mPkg);
        boolean equals2 = "com.android.systemui".equals(this.mPkg);
        boolean z = equals || equals2 || this.mUseSmallIcon;
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mKey);
            sb.append(": use small icon. androidPkg = ");
            sb.append(equals);
            sb.append(", systemuiPkg = ");
            sb.append(equals2);
            sb.append(", mUseSmallIcon = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mUseSmallIcon, "SubscreenNotificationInfo");
        }
        return z;
    }

    public SubscreenNotificationInfo(Parcel parcel) {
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
    }
}
