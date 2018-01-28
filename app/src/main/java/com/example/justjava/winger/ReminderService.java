package com.example.justjava.winger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

public class ReminderService extends WakeReminderIntentService {

	public ReminderService() {
		super("ReminderService");
			}

	@Override
	void doReminderWork(Intent intent) {
		Log.d("ReminderService", "Doing work.");
		Long rowId = intent.getExtras().getLong(RemindersDbAdapter.KEY_ROWID);

		NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

		Intent notificationIntent = new Intent(this, ReminderEditActivity.class);
		notificationIntent.putExtra(RemindersDbAdapter.KEY_ROWID, rowId);

		PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

		Notification.Builder builder = new Notification.Builder(this)
				.setContentIntent(pi)
				.setContentTitle(getString(R.string.notify_new_task_title))
				.setContentText(getString(R.string.notify_new_task_message))
				.setDefaults(Notification.DEFAULT_SOUND);


		// An issue could occur if user ever enters over 2,147,483,647 tasks. (Max int value).
		// I highly doubt this will ever happen. But is good to note.
		int id = (int)((long)rowId);
		mgr.notify(id, builder.build());


	}
}
