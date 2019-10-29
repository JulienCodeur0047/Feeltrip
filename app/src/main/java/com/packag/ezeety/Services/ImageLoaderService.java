package com.packag.ezeety.Services;

import android.app.Activity;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.packag.ezeety.model.PhotoItem;
import com.packag.ezeety.model.TaskCompletion;

import java.util.ArrayList;

public class ImageLoaderService extends AsyncTask<Void,Void, ArrayList<PhotoItem>> {
    private Cursor cursor, cursorInterne,cursorExterne;
    private Activity activity;
    private TaskCompletion taskCompletion;

    public ImageLoaderService(Activity activity, TaskCompletion taskCompletion) {
        this.activity = activity;
        this.taskCompletion = taskCompletion;
    }

    @Override
    protected ArrayList<PhotoItem> doInBackground(Void... voids) {
        cursor = getCursor();
        ArrayList<PhotoItem> quotes = new   ArrayList<>();
        if(cursor!=null && cursor.moveToFirst()) {
            do {
                PhotoItem item = new PhotoItem();
                item.setId(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)));
                item.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)));
                item.setLabel(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
                quotes.add(item);
            }while (cursor.moveToNext());
        }
        if(cursor!=null) cursor.close();
        if(cursorInterne!=null) cursorInterne.close();
        if(cursorInterne!=null) cursorInterne.close();
        return quotes;
    }
    private Cursor getCursor(){
        String[] projection = {
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        cursorInterne = activity.getContentResolver().query(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                projection, null, null, MediaStore.MediaColumns.DATE_ADDED+ " DESC");
        cursorInterne = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null,  MediaStore.MediaColumns.DATE_ADDED+ " DESC");
        Cursor[] cursorArray = { cursorInterne, cursorExterne };
        return new MergeCursor(cursorArray);
    }
    @Override
    protected void onPostExecute(ArrayList<PhotoItem> photoItems) {
        super.onPostExecute(photoItems);
        taskCompletion.onSuccess(photoItems);
    }

}
