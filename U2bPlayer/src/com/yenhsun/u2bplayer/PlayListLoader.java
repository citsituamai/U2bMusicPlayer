
package com.yenhsun.u2bplayer;

import java.util.ArrayList;

import com.yenhsun.u2bplayer.utilities.PlayListInfo;
import com.yenhsun.u2bplayer.utilities.YoutubeDataParser;

import android.content.Context;

public class PlayListLoader {

    public interface PlayListLoaderCallback {
        public void loadDone();
    }

    private Context mContext;
    private final ArrayList<PlayListInfo> mPlayList = new ArrayList<PlayListInfo>();
    private final ArrayList<PlayListLoaderCallback> mCallbacks = new ArrayList<PlayListLoaderCallback>();
    private static PlayListLoader mSingleton;

    private PlayListLoader(Context context) {
        mContext = context;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlayListInfo> getPlayList() {
        return (ArrayList<PlayListInfo>) mPlayList.clone();
    }

    public static synchronized PlayListLoader getInstance(Context context) {
        if (mSingleton == null) {
            mSingleton = new PlayListLoader(context);
        }
        return mSingleton;
    }

    public void addCallback(PlayListLoaderCallback c) {
        mCallbacks.add(c);
    }

    public void removeCallback(PlayListLoaderCallback c) {
        mCallbacks.remove(c);
    }

    public void initPlayListContent() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // get list and put into mPlayList
                String[] cdList = new String[] {
                        "�ƨg�@��", "�֩�", "�z�S", "�ͬ�", "�R�����Ҽ�", "�K�ڭn���F", "�", "�ө��P�K�b", "Hosee", "�¥���",
                        "I Love You �L��", "���Y�j"
                };
                for (String music : cdList) {
                    YoutubeDataParser.showYoutubeResult(
                            "�����", "", music
                            , new YoutubeDataParser.YoutubeIdParserResultCallback() {

                                @Override
                                public void setResult(ArrayList<PlayListInfo> infoList) {
                                    mPlayList.addAll(infoList);
                                    if (mCallbacks.isEmpty() == false) {
                                        for (PlayListLoaderCallback c : mCallbacks) {
                                            c.loadDone();
                                        }
                                    }
                                }
                            });
                }
            }
        }).start();
    }
}
