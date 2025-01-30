package androidx.core.app;

import android.app.Notification;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat$BubbleMetadata;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationCompatBuilder {
    public final Notification.Builder mBuilder;
    public final NotificationCompat$Builder mBuilderCompat;
    public final Context mContext;
    public final Bundle mExtras;

    public NotificationCompatBuilder(NotificationCompat$Builder notificationCompat$Builder) {
        String str;
        String str2;
        Notification.BubbleMetadata bubbleMetadata;
        Bundle[] bundleArr;
        ArrayList arrayList;
        String str3;
        String str4;
        Iterator it;
        NotificationCompatBuilder notificationCompatBuilder = this;
        new ArrayList();
        notificationCompatBuilder.mExtras = new Bundle();
        notificationCompatBuilder.mBuilderCompat = notificationCompat$Builder;
        notificationCompatBuilder.mContext = notificationCompat$Builder.mContext;
        Context context = notificationCompat$Builder.mContext;
        String str5 = notificationCompat$Builder.mChannelId;
        Notification.Builder builder = new Notification.Builder(context, str5);
        notificationCompatBuilder.mBuilder = builder;
        Notification notification2 = notificationCompat$Builder.mNotification;
        Icon icon = null;
        builder.setWhen(notification2.when).setSmallIcon(notification2.icon, notification2.iconLevel).setContent(notification2.contentView).setTicker(notification2.tickerText, null).setVibrate(notification2.vibrate).setLights(notification2.ledARGB, notification2.ledOnMS, notification2.ledOffMS).setOngoing((notification2.flags & 2) != 0).setOnlyAlertOnce((notification2.flags & 8) != 0).setAutoCancel((notification2.flags & 16) != 0).setDefaults(notification2.defaults).setContentTitle(notificationCompat$Builder.mContentTitle).setContentText(notificationCompat$Builder.mContentText).setContentInfo(notificationCompat$Builder.mContentInfo).setContentIntent(notificationCompat$Builder.mContentIntent).setDeleteIntent(notification2.deleteIntent).setFullScreenIntent(notificationCompat$Builder.mFullScreenIntent, (notification2.flags & 128) != 0).setLargeIcon(notificationCompat$Builder.mLargeIcon).setNumber(notificationCompat$Builder.mNumber).setProgress(notificationCompat$Builder.mProgressMax, notificationCompat$Builder.mProgress, notificationCompat$Builder.mProgressIndeterminate);
        builder.setSubText(notificationCompat$Builder.mSubText).setUsesChronometer(notificationCompat$Builder.mUseChronometer).setPriority(notificationCompat$Builder.mPriority);
        Iterator it2 = notificationCompat$Builder.mActions.iterator();
        while (true) {
            str = "android.support.allowGeneratedReplies";
            if (!it2.hasNext()) {
                break;
            }
            NotificationCompat$Action notificationCompat$Action = (NotificationCompat$Action) it2.next();
            IconCompat iconCompat = notificationCompat$Action.getIconCompat();
            Notification.Action.Builder builder2 = new Notification.Action.Builder(iconCompat != null ? iconCompat.toIcon$1() : icon, notificationCompat$Action.title, notificationCompat$Action.actionIntent);
            RemoteInput[] remoteInputArr = notificationCompat$Action.mRemoteInputs;
            if (remoteInputArr != null) {
                int length = remoteInputArr.length;
                android.app.RemoteInput[] remoteInputArr2 = new android.app.RemoteInput[length];
                int i = 0;
                while (i < remoteInputArr.length) {
                    RemoteInput remoteInput = remoteInputArr[i];
                    RemoteInput.Builder addExtras = new RemoteInput.Builder(remoteInput.mResultKey).setLabel(remoteInput.mLabel).setChoices(remoteInput.mChoices).setAllowFreeFormInput(remoteInput.mAllowFreeFormTextInput).addExtras(remoteInput.mExtras);
                    Set set = remoteInput.mAllowedDataTypes;
                    if (set != null) {
                        Iterator it3 = set.iterator();
                        while (it3.hasNext()) {
                            addExtras.setAllowDataType((String) it3.next(), true);
                            it2 = it2;
                        }
                    }
                    addExtras.setEditChoicesBeforeSending(remoteInput.mEditChoicesBeforeSending);
                    remoteInputArr2[i] = addExtras.build();
                    i++;
                    it2 = it2;
                }
                it = it2;
                for (int i2 = 0; i2 < length; i2++) {
                    builder2.addRemoteInput(remoteInputArr2[i2]);
                }
            } else {
                it = it2;
            }
            Bundle bundle = notificationCompat$Action.mExtras;
            Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
            boolean z = notificationCompat$Action.mAllowGeneratedReplies;
            bundle2.putBoolean("android.support.allowGeneratedReplies", z);
            builder2.setAllowGeneratedReplies(z);
            int i3 = notificationCompat$Action.mSemanticAction;
            bundle2.putInt("android.support.action.semanticAction", i3);
            builder2.setSemanticAction(i3);
            builder2.setContextual(notificationCompat$Action.mIsContextual);
            builder2.setAuthenticationRequired(notificationCompat$Action.mAuthenticationRequired);
            bundle2.putBoolean("android.support.action.showsUserInterface", notificationCompat$Action.mShowsUserInterface);
            builder2.addExtras(bundle2);
            notificationCompatBuilder.mBuilder.addAction(builder2.build());
            it2 = it;
            icon = null;
        }
        Bundle bundle3 = notificationCompat$Builder.mExtras;
        if (bundle3 != null) {
            notificationCompatBuilder.mExtras.putAll(bundle3);
        }
        notificationCompatBuilder.mBuilder.setShowWhen(notificationCompat$Builder.mShowWhen);
        notificationCompatBuilder.mBuilder.setLocalOnly(notificationCompat$Builder.mLocalOnly).setGroup(notificationCompat$Builder.mGroupKey).setGroupSummary(notificationCompat$Builder.mGroupSummary).setSortKey(notificationCompat$Builder.mSortKey);
        notificationCompatBuilder.mBuilder.setCategory(notificationCompat$Builder.mCategory).setColor(notificationCompat$Builder.mColor).setVisibility(notificationCompat$Builder.mVisibility).setPublicVersion(notificationCompat$Builder.mPublicVersion).setSound(notification2.sound, notification2.audioAttributes);
        ArrayList arrayList2 = notificationCompat$Builder.mPeople;
        if (arrayList2 != null && !arrayList2.isEmpty()) {
            Iterator it4 = arrayList2.iterator();
            while (it4.hasNext()) {
                notificationCompatBuilder.mBuilder.addPerson((String) it4.next());
            }
        }
        ArrayList arrayList3 = notificationCompat$Builder.mInvisibleActions;
        if (arrayList3.size() > 0) {
            if (notificationCompat$Builder.mExtras == null) {
                notificationCompat$Builder.mExtras = new Bundle();
            }
            Bundle bundle4 = notificationCompat$Builder.mExtras.getBundle("android.car.EXTENSIONS");
            bundle4 = bundle4 == null ? new Bundle() : bundle4;
            Bundle bundle5 = new Bundle(bundle4);
            Bundle bundle6 = new Bundle();
            int i4 = 0;
            while (i4 < arrayList3.size()) {
                String num = Integer.toString(i4);
                NotificationCompat$Action notificationCompat$Action2 = (NotificationCompat$Action) arrayList3.get(i4);
                Object obj = NotificationCompatJellybean.sExtrasLock;
                Bundle bundle7 = new Bundle();
                IconCompat iconCompat2 = notificationCompat$Action2.getIconCompat();
                bundle7.putInt("icon", iconCompat2 != null ? iconCompat2.getResId() : 0);
                bundle7.putCharSequence(UniversalCredentialUtil.AGENT_TITLE, notificationCompat$Action2.title);
                bundle7.putParcelable("actionIntent", notificationCompat$Action2.actionIntent);
                Bundle bundle8 = notificationCompat$Action2.mExtras;
                Bundle bundle9 = bundle8 != null ? new Bundle(bundle8) : new Bundle();
                bundle9.putBoolean(str, notificationCompat$Action2.mAllowGeneratedReplies);
                bundle7.putBundle("extras", bundle9);
                RemoteInput[] remoteInputArr3 = notificationCompat$Action2.mRemoteInputs;
                if (remoteInputArr3 == null) {
                    arrayList = arrayList3;
                    str4 = str5;
                    str3 = str;
                    bundleArr = null;
                } else {
                    bundleArr = new Bundle[remoteInputArr3.length];
                    arrayList = arrayList3;
                    str3 = str;
                    int i5 = 0;
                    while (i5 < remoteInputArr3.length) {
                        RemoteInput remoteInput2 = remoteInputArr3[i5];
                        RemoteInput[] remoteInputArr4 = remoteInputArr3;
                        Bundle bundle10 = new Bundle();
                        String str6 = str5;
                        bundle10.putString("resultKey", remoteInput2.mResultKey);
                        bundle10.putCharSequence("label", remoteInput2.mLabel);
                        bundle10.putCharSequenceArray("choices", remoteInput2.mChoices);
                        bundle10.putBoolean("allowFreeFormInput", remoteInput2.mAllowFreeFormTextInput);
                        bundle10.putBundle("extras", remoteInput2.mExtras);
                        Set set2 = remoteInput2.mAllowedDataTypes;
                        if (set2 != null && !set2.isEmpty()) {
                            ArrayList<String> arrayList4 = new ArrayList<>(set2.size());
                            Iterator it5 = set2.iterator();
                            while (it5.hasNext()) {
                                arrayList4.add((String) it5.next());
                            }
                            bundle10.putStringArrayList("allowedDataTypes", arrayList4);
                        }
                        bundleArr[i5] = bundle10;
                        i5++;
                        remoteInputArr3 = remoteInputArr4;
                        str5 = str6;
                    }
                    str4 = str5;
                }
                bundle7.putParcelableArray("remoteInputs", bundleArr);
                bundle7.putBoolean("showsUserInterface", notificationCompat$Action2.mShowsUserInterface);
                bundle7.putInt("semanticAction", notificationCompat$Action2.mSemanticAction);
                bundle6.putBundle(num, bundle7);
                i4++;
                arrayList3 = arrayList;
                str = str3;
                str5 = str4;
            }
            str2 = str5;
            bundle4.putBundle("invisible_actions", bundle6);
            bundle5.putBundle("invisible_actions", bundle6);
            if (notificationCompat$Builder.mExtras == null) {
                notificationCompat$Builder.mExtras = new Bundle();
            }
            notificationCompat$Builder.mExtras.putBundle("android.car.EXTENSIONS", bundle4);
            notificationCompatBuilder = this;
            notificationCompatBuilder.mExtras.putBundle("android.car.EXTENSIONS", bundle5);
        } else {
            str2 = str5;
        }
        Icon icon2 = notificationCompat$Builder.mSmallIcon;
        if (icon2 != null) {
            notificationCompatBuilder.mBuilder.setSmallIcon(icon2);
        }
        notificationCompatBuilder.mBuilder.setExtras(notificationCompat$Builder.mExtras).setRemoteInputHistory(null);
        notificationCompatBuilder.mBuilder.setBadgeIconType(notificationCompat$Builder.mBadgeIcon).setSettingsText(notificationCompat$Builder.mSettingsText).setShortcutId(notificationCompat$Builder.mShortcutId).setTimeoutAfter(notificationCompat$Builder.mTimeout).setGroupAlertBehavior(0);
        if (notificationCompat$Builder.mColorizedSet) {
            notificationCompatBuilder.mBuilder.setColorized(notificationCompat$Builder.mColorized);
        }
        if (TextUtils.isEmpty(str2)) {
            bubbleMetadata = null;
        } else {
            bubbleMetadata = null;
            notificationCompatBuilder.mBuilder.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
        }
        Iterator it6 = notificationCompat$Builder.mPersonList.iterator();
        while (it6.hasNext()) {
            notificationCompatBuilder.mBuilder.addPerson(((Person) it6.next()).toAndroidPerson());
        }
        notificationCompatBuilder.mBuilder.setAllowSystemGeneratedContextualActions(notificationCompat$Builder.mAllowSystemGeneratedContextualActions);
        Notification.Builder builder3 = notificationCompatBuilder.mBuilder;
        NotificationCompat$BubbleMetadata notificationCompat$BubbleMetadata = notificationCompat$Builder.mBubbleMetadata;
        builder3.setBubbleMetadata(notificationCompat$BubbleMetadata == null ? bubbleMetadata : NotificationCompat$BubbleMetadata.Api30Impl.toPlatform(notificationCompat$BubbleMetadata));
        LocusIdCompat locusIdCompat = notificationCompat$Builder.mLocusId;
        if (locusIdCompat != null) {
            notificationCompatBuilder.mBuilder.setLocusId(locusIdCompat.mWrapped);
        }
    }
}
