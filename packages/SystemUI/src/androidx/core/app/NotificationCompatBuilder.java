package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat$BubbleMetadata;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import com.android.systemui.R;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NotificationCompatBuilder {
    public final Notification.Builder mBuilder;
    public final NotificationCompat$Builder mBuilderCompat;
    public final Context mContext;
    public final Bundle mExtras;

    public NotificationCompatBuilder(NotificationCompat$Builder notificationCompat$Builder) {
        String str;
        Notification.BubbleMetadata bubbleMetadata;
        int i;
        Bundle[] bundleArr;
        NotificationCompat$Action makeAction;
        new ArrayList();
        this.mExtras = new Bundle();
        this.mBuilderCompat = notificationCompat$Builder;
        Context context = notificationCompat$Builder.mContext;
        this.mContext = context;
        String str2 = notificationCompat$Builder.mChannelId;
        Notification.Builder builder = new Notification.Builder(context, str2);
        this.mBuilder = builder;
        Notification notification2 = notificationCompat$Builder.mNotification;
        Bundle[] bundleArr2 = null;
        int i2 = 2;
        builder.setWhen(notification2.when).setSmallIcon(notification2.icon, notification2.iconLevel).setContent(notification2.contentView).setTicker(notification2.tickerText, null).setVibrate(notification2.vibrate).setLights(notification2.ledARGB, notification2.ledOnMS, notification2.ledOffMS).setOngoing((notification2.flags & 2) != 0).setOnlyAlertOnce((notification2.flags & 8) != 0).setAutoCancel((notification2.flags & 16) != 0).setDefaults(notification2.defaults).setContentTitle(notificationCompat$Builder.mContentTitle).setContentText(notificationCompat$Builder.mContentText).setContentInfo(notificationCompat$Builder.mContentInfo).setContentIntent(notificationCompat$Builder.mContentIntent).setDeleteIntent(notification2.deleteIntent).setFullScreenIntent(notificationCompat$Builder.mFullScreenIntent, (notification2.flags & 128) != 0).setNumber(notificationCompat$Builder.mNumber).setProgress(notificationCompat$Builder.mProgressMax, notificationCompat$Builder.mProgress, notificationCompat$Builder.mProgressIndeterminate);
        IconCompat iconCompat = notificationCompat$Builder.mLargeIcon;
        builder.setLargeIcon(iconCompat == null ? null : iconCompat.toIcon$1());
        builder.setSubText(notificationCompat$Builder.mSubText).setUsesChronometer(notificationCompat$Builder.mUseChronometer).setPriority(notificationCompat$Builder.mPriority);
        NotificationCompat$Style notificationCompat$Style = notificationCompat$Builder.mStyle;
        if (notificationCompat$Style instanceof NotificationCompat$CallStyle) {
            NotificationCompat$CallStyle notificationCompat$CallStyle = (NotificationCompat$CallStyle) notificationCompat$Style;
            PendingIntent pendingIntent = notificationCompat$CallStyle.mDeclineIntent;
            NotificationCompat$Action makeAction2 = pendingIntent == null ? notificationCompat$CallStyle.makeAction(R.drawable.ic_call_decline, R.string.call_notification_hang_up_action, notificationCompat$CallStyle.mDeclineButtonColor, R.color.call_notification_decline_color, notificationCompat$CallStyle.mHangUpIntent) : notificationCompat$CallStyle.makeAction(R.drawable.ic_call_decline, R.string.call_notification_decline_action, notificationCompat$CallStyle.mDeclineButtonColor, R.color.call_notification_decline_color, pendingIntent);
            PendingIntent pendingIntent2 = notificationCompat$CallStyle.mAnswerIntent;
            if (pendingIntent2 == null) {
                makeAction = null;
            } else {
                boolean z = notificationCompat$CallStyle.mIsVideo;
                makeAction = notificationCompat$CallStyle.makeAction(z ? R.drawable.ic_call_answer_video : R.drawable.ic_call_answer, z ? R.string.call_notification_answer_video_action : R.string.call_notification_answer_action, notificationCompat$CallStyle.mAnswerButtonColor, R.color.call_notification_answer_color, pendingIntent2);
            }
            ArrayList arrayList = new ArrayList(3);
            arrayList.add(makeAction2);
            ArrayList arrayList2 = notificationCompat$CallStyle.mBuilder.mActions;
            if (arrayList2 != null) {
                int size = arrayList2.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList2.get(i3);
                    i3++;
                    NotificationCompat$Action notificationCompat$Action = (NotificationCompat$Action) obj;
                    if (notificationCompat$Action.mIsContextual) {
                        arrayList.add(notificationCompat$Action);
                    } else if (!notificationCompat$Action.mExtras.getBoolean("key_action_priority") && i2 > 1) {
                        arrayList.add(notificationCompat$Action);
                        i2--;
                    }
                    if (makeAction != null && i2 == 1) {
                        arrayList.add(makeAction);
                        i2--;
                    }
                }
            }
            if (makeAction != null && i2 >= 1) {
                arrayList.add(makeAction);
            }
            int size2 = arrayList.size();
            int i4 = 0;
            while (i4 < size2) {
                Object obj2 = arrayList.get(i4);
                i4++;
                addAction((NotificationCompat$Action) obj2);
            }
        } else {
            ArrayList arrayList3 = notificationCompat$Builder.mActions;
            int size3 = arrayList3.size();
            int i5 = 0;
            while (i5 < size3) {
                Object obj3 = arrayList3.get(i5);
                i5++;
                addAction((NotificationCompat$Action) obj3);
            }
        }
        Bundle bundle = notificationCompat$Builder.mExtras;
        if (bundle != null) {
            this.mExtras.putAll(bundle);
        }
        this.mBuilder.setShowWhen(notificationCompat$Builder.mShowWhen);
        this.mBuilder.setLocalOnly(notificationCompat$Builder.mLocalOnly);
        this.mBuilder.setGroup(notificationCompat$Builder.mGroupKey);
        this.mBuilder.setSortKey(notificationCompat$Builder.mSortKey);
        this.mBuilder.setGroupSummary(notificationCompat$Builder.mGroupSummary);
        this.mBuilder.setCategory(notificationCompat$Builder.mCategory);
        this.mBuilder.setColor(notificationCompat$Builder.mColor);
        this.mBuilder.setVisibility(notificationCompat$Builder.mVisibility);
        this.mBuilder.setPublicVersion(notificationCompat$Builder.mPublicVersion);
        this.mBuilder.setSound(notification2.sound, notification2.audioAttributes);
        ArrayList arrayList4 = notificationCompat$Builder.mPeople;
        if (arrayList4 != null && !arrayList4.isEmpty()) {
            int size4 = arrayList4.size();
            int i6 = 0;
            while (i6 < size4) {
                Object obj4 = arrayList4.get(i6);
                i6++;
                this.mBuilder.addPerson((String) obj4);
            }
        }
        if (notificationCompat$Builder.mInvisibleActions.size() > 0) {
            if (notificationCompat$Builder.mExtras == null) {
                notificationCompat$Builder.mExtras = new Bundle();
            }
            Bundle bundle2 = notificationCompat$Builder.mExtras.getBundle("android.car.EXTENSIONS");
            bundle2 = bundle2 == null ? new Bundle() : bundle2;
            Bundle bundle3 = new Bundle(bundle2);
            Bundle bundle4 = new Bundle();
            int i7 = 0;
            while (i7 < notificationCompat$Builder.mInvisibleActions.size()) {
                String num = Integer.toString(i7);
                NotificationCompat$Action notificationCompat$Action2 = (NotificationCompat$Action) notificationCompat$Builder.mInvisibleActions.get(i7);
                Bundle bundle5 = new Bundle();
                IconCompat iconCompat2 = notificationCompat$Action2.getIconCompat();
                bundle5.putInt("icon", iconCompat2 != null ? iconCompat2.getResId() : 0);
                bundle5.putCharSequence(UniversalCredentialUtil.AGENT_TITLE, notificationCompat$Action2.title);
                bundle5.putParcelable("actionIntent", notificationCompat$Action2.actionIntent);
                Bundle bundle6 = notificationCompat$Action2.mExtras != null ? new Bundle(notificationCompat$Action2.mExtras) : new Bundle();
                bundle6.putBoolean("android.support.allowGeneratedReplies", notificationCompat$Action2.mAllowGeneratedReplies);
                bundle5.putBundle("extras", bundle6);
                RemoteInput[] remoteInputArr = notificationCompat$Action2.mRemoteInputs;
                if (remoteInputArr == null) {
                    bundleArr = bundleArr2;
                } else {
                    bundleArr = new Bundle[remoteInputArr.length];
                    int i8 = 0;
                    while (i8 < remoteInputArr.length) {
                        RemoteInput remoteInput = remoteInputArr[i8];
                        String str3 = str2;
                        Bundle bundle7 = new Bundle();
                        int i9 = i7;
                        int i10 = i8;
                        bundle7.putString("resultKey", remoteInput.mResultKey);
                        bundle7.putCharSequence("label", remoteInput.mLabel);
                        bundle7.putCharSequenceArray("choices", remoteInput.mChoices);
                        bundle7.putBoolean("allowFreeFormInput", remoteInput.mAllowFreeFormTextInput);
                        bundle7.putBundle("extras", remoteInput.mExtras);
                        Set set = remoteInput.mAllowedDataTypes;
                        if (set != null && !set.isEmpty()) {
                            ArrayList<String> arrayList5 = new ArrayList<>(set.size());
                            Iterator it = set.iterator();
                            while (it.hasNext()) {
                                arrayList5.add((String) it.next());
                            }
                            bundle7.putStringArrayList("allowedDataTypes", arrayList5);
                        }
                        bundleArr[i10] = bundle7;
                        i8 = i10 + 1;
                        str2 = str3;
                        i7 = i9;
                    }
                }
                String str4 = str2;
                int i11 = i7;
                bundle5.putParcelableArray("remoteInputs", bundleArr);
                bundle5.putBoolean("showsUserInterface", notificationCompat$Action2.mShowsUserInterface);
                bundle5.putInt("semanticAction", notificationCompat$Action2.mSemanticAction);
                bundle4.putBundle(num, bundle5);
                i7 = i11 + 1;
                str2 = str4;
                bundleArr2 = null;
            }
            str = str2;
            bundle2.putBundle("invisible_actions", bundle4);
            bundle3.putBundle("invisible_actions", bundle4);
            if (notificationCompat$Builder.mExtras == null) {
                notificationCompat$Builder.mExtras = new Bundle();
            }
            notificationCompat$Builder.mExtras.putBundle("android.car.EXTENSIONS", bundle2);
            this.mExtras.putBundle("android.car.EXTENSIONS", bundle3);
        } else {
            str = str2;
        }
        Object obj5 = notificationCompat$Builder.mSmallIcon;
        if (obj5 != null) {
            this.mBuilder.setSmallIcon((Icon) obj5);
        }
        this.mBuilder.setExtras(notificationCompat$Builder.mExtras);
        this.mBuilder.setRemoteInputHistory(null);
        this.mBuilder.setBadgeIconType(notificationCompat$Builder.mBadgeIcon);
        this.mBuilder.setSettingsText(notificationCompat$Builder.mSettingsText);
        this.mBuilder.setShortcutId(notificationCompat$Builder.mShortcutId);
        this.mBuilder.setTimeoutAfter(notificationCompat$Builder.mTimeout);
        this.mBuilder.setGroupAlertBehavior(0);
        if (notificationCompat$Builder.mColorizedSet) {
            this.mBuilder.setColorized(notificationCompat$Builder.mColorized);
        }
        if (TextUtils.isEmpty(str)) {
            bubbleMetadata = null;
            i = 0;
        } else {
            bubbleMetadata = null;
            i = 0;
            this.mBuilder.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
        }
        ArrayList arrayList6 = notificationCompat$Builder.mPersonList;
        int size5 = arrayList6.size();
        int i12 = i;
        while (i12 < size5) {
            Object obj6 = arrayList6.get(i12);
            i12++;
            this.mBuilder.addPerson(((Person) obj6).toAndroidPerson());
        }
        this.mBuilder.setAllowSystemGeneratedContextualActions(notificationCompat$Builder.mAllowSystemGeneratedContextualActions);
        Notification.Builder builder2 = this.mBuilder;
        NotificationCompat$BubbleMetadata notificationCompat$BubbleMetadata = notificationCompat$Builder.mBubbleMetadata;
        builder2.setBubbleMetadata(notificationCompat$BubbleMetadata == null ? bubbleMetadata : NotificationCompat$BubbleMetadata.Api30Impl.toPlatform(notificationCompat$BubbleMetadata));
        LocusIdCompat locusIdCompat = notificationCompat$Builder.mLocusId;
        if (locusIdCompat != null) {
            this.mBuilder.setLocusId(locusIdCompat.mWrapped);
        }
    }

    public final void addAction(NotificationCompat$Action notificationCompat$Action) {
        IconCompat iconCompat = notificationCompat$Action.getIconCompat();
        Notification.Action.Builder builder = new Notification.Action.Builder(iconCompat != null ? iconCompat.toIcon$1() : null, notificationCompat$Action.title, notificationCompat$Action.actionIntent);
        RemoteInput[] remoteInputArr = notificationCompat$Action.mRemoteInputs;
        if (remoteInputArr != null) {
            android.app.RemoteInput[] remoteInputArr2 = new android.app.RemoteInput[remoteInputArr.length];
            for (int i = 0; i < remoteInputArr.length; i++) {
                RemoteInput remoteInput = remoteInputArr[i];
                RemoteInput.Builder addExtras = new RemoteInput.Builder(remoteInput.mResultKey).setLabel(remoteInput.mLabel).setChoices(remoteInput.mChoices).setAllowFreeFormInput(remoteInput.mAllowFreeFormTextInput).addExtras(remoteInput.mExtras);
                Set set = remoteInput.mAllowedDataTypes;
                if (set != null) {
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        addExtras.setAllowDataType((String) it.next(), true);
                    }
                }
                addExtras.setEditChoicesBeforeSending(remoteInput.mEditChoicesBeforeSending);
                remoteInputArr2[i] = addExtras.build();
            }
            for (android.app.RemoteInput remoteInput2 : remoteInputArr2) {
                builder.addRemoteInput(remoteInput2);
            }
        }
        Bundle bundle = notificationCompat$Action.mExtras != null ? new Bundle(notificationCompat$Action.mExtras) : new Bundle();
        boolean z = notificationCompat$Action.mAllowGeneratedReplies;
        bundle.putBoolean("android.support.allowGeneratedReplies", z);
        builder.setAllowGeneratedReplies(z);
        int i2 = notificationCompat$Action.mSemanticAction;
        bundle.putInt("android.support.action.semanticAction", i2);
        builder.setSemanticAction(i2);
        builder.setContextual(notificationCompat$Action.mIsContextual);
        builder.setAuthenticationRequired(notificationCompat$Action.mAuthenticationRequired);
        bundle.putBoolean("android.support.action.showsUserInterface", notificationCompat$Action.mShowsUserInterface);
        builder.addExtras(bundle);
        this.mBuilder.addAction(builder.build());
    }
}
