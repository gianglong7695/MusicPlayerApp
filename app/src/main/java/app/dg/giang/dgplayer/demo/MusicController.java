package app.dg.giang.dgplayer.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

import app.dg.giang.dgplayer.models.Song;

/**
 * Created by TAKASHI20 on 8/22/2015.
 */
public class MusicController {

    /**
     * set shuffle mode for mainplayer, update also the UI
     *
     * @param s         Shuffle
     * @param needtoast if true, toast will be shown
     */
    public static void setShuffle(Context context/*, PlaylistManager mPlaylistMgr*/, boolean s, boolean needtoast, ImageView imageShuffle) {

//        if (mPlaylistMgr == null) ;
//        mPlaylistMgr.setShuffle(s);
//        if (s) {
//            if (needtoast)
//                Toast.makeText(context, R.string.info_shuffle_on, Toast.LENGTH_SHORT).show();
//            imageShuffle.setImageResource(R.drawable.ic_shuffleon);
//        } else {
//            if (needtoast)
//                Toast.makeText(context, R.string.info_shuffle_off, Toast.LENGTH_SHORT).show();
//            imageShuffle.setImageResource(R.drawable.ic_shuffleoff);
//        }
    }

//    /**
//     * Set repeat mode for mainplayer, update UI also
//     *
//     * @param rp
//     * @param needtoast if true, toast will be shown
//     */
//    public static void setRepeatMode(Context context, PlaylistManager mPlaylistMgr, PlaylistManager.RepeatMode rp, boolean needtoast, ImageView imageRepeat) {
//        if (mPlaylistMgr == null)
//            return;
////        mPlaylistMgr.setRepeatMode(rp);
////
////        if (rp == PlaylistManager.RepeatMode.RepeatAll) {
////            if (needtoast)
////                Toast.makeText(context, R.string.info_repeat_all, Toast.LENGTH_SHORT).show();
////            imageRepeat.setImageResource(R.drawable.ic_repeat_all);
////        } else if (rp == PlaylistManager.RepeatMode.RepeatOne) {
////            if (needtoast)
////                Toast.makeText(context, R.string.info_repeat_one, Toast.LENGTH_SHORT).show();
////            imageRepeat.setImageResource(R.drawable.ic_repeat_one);
////
////        } else {
////            if (needtoast)
////                Toast.makeText(context, R.string.info_repeat_none, Toast.LENGTH_SHORT).show();
////            imageRepeat.setImageResource(R.drawable.ic_repeat_non);
////        }
//    }

    public static void nextSong(Context context) {
        Intent i = new Intent();
        i.setAction(CommonValues.ACTION_NEXT);
        context.startService(i);
    }

    public static void playSong(Context context) {

        Intent i = new Intent();
        i.setAction(CommonValues.ACTION_TOGGLE_PLAYBACK);
        context.startService(i);
    }

    public static void previousSong(Context context) {
        Intent i = new Intent();
        i.setAction(CommonValues.ACTION_PREVIOUS);
        context.startService(i);
    }

    public static void playAllSong(Context context) {
//        if (MusicService.getPlaylistManager() != null) {
//            SongItem si = MusicService.getPlaylistManager().getArrayList().get(0);
//
//            Singleton singleton = Singleton.getInstance();
//            singleton.setCurrentSongItem(si);
//
//            Intent i = new Intent();
//            i.setAction(CommonValues.ACTION_PLAY_SPECIFIC_SONG);
//            context.startService(i);
//        } else {
//            Toast.makeText(context, "Can't play all song", Toast.LENGTH_SHORT).show();
//        }
    }

    public static void playSongItemListView(Context context, ListView parent, int pos) {
//        SongItem si = (SongItem) parent.getItemAtPosition(pos);
//
//        Singleton singleton = Singleton.getInstance();
//        singleton.setCurrentSongItem(si);
//
//        Intent i = new Intent();
//        i.setAction(CommonValues.ACTION_PLAY_SPECIFIC_SONG);
//        context.startService(i);
    }

    public static void playSongItemAnyway(Context context, ArrayList<Song> myListSongItem, int pos) {

//        SongItem si = myListSongItem.get(pos);
//        Singleton singleton = Singleton.getInstance();
//        singleton.setCurrentSongItem(si);

        Intent i = new Intent();
        i.setAction(CommonValues.ACTION_PLAY_SPECIFIC_SONG);
        context.startService(i);
    }


    /**
     * action for button Shuffle song
     *
     * @param context
     * @param mPlaylistManager
     * @param isToast
     * @param imageShuffle
     */
//    public static void actionShuffle(Context context, PlaylistManager mPlaylistManager, boolean isToast, ImageView imageShuffle) {
//        boolean s = mPlaylistManager.isShuffle();
//        setShuffle(context, mPlaylistManager, !s, true, imageShuffle);
//    }

    /**
     * Action for button repeat song
     *
     * @param context
     * @param mPlaylistMgr
     * @param needtoast
     * @param imageRepeat
     */
//    public static void actionRepeat(Context context, PlaylistManager mPlaylistMgr, boolean needtoast, ImageView imageRepeat) {
//        PlaylistManager.RepeatMode rp = mPlaylistMgr.getRepeatMode();
//
//        if (rp == RepeatMode.NoRepeat)
//            setRepeatMode(context, mPlaylistMgr, RepeatMode.RepeatAll, needtoast, imageRepeat);
//        else if (rp == RepeatMode.RepeatAll)
//            setRepeatMode(context, mPlaylistMgr, RepeatMode.RepeatOne, needtoast, imageRepeat);
//        else
//            setRepeatMode(context, mPlaylistMgr, RepeatMode.NoRepeat, needtoast, imageRepeat);
//    }

    /**
     * Save the preferences
     */
//    public static void savePreferences(Context context, PlaylistManager mPlaylistManager) {
//        // Save preference
//        if (mPlaylistManager != null) {
//            SharedPreferences sp = context.getSharedPreferences(CommonValues.MY_PREF, 0);
//            SharedPreferences.Editor ed = sp.edit();
//
//            ed.putBoolean(CommonValues.SETTING_SHUFFLE, mPlaylistManager.isShuffle());
//            ed.putString(CommonValues.SETTING_REPEAT, mPlaylistManager.getRepeatMode().toString());
//            ed.commit();
//        }
//    }
    public static void actionSeekBarControl(final Context context, SeekBar mSeekBar) {

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {


                Intent i = new Intent();
                i.setAction(CommonValues.ACTION_SEEK);

                Bundle b = new Bundle();
                b.putInt(CommonValues.BKEY_PERCENTAGE, seekBar.getProgress());

                i.putExtras(b);
                context.startService(i);
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
        });
    }

    /**
     * Restore preferences
     */
//    public static void restorePreferences(Context context, ImageView imageShuffle, ImageView imageRepeat) {
//        SharedPreferences sp = context.getSharedPreferences(CommonValues.MY_PREF, 0);
//
//        // shuffle mode
//        setShuffle(context, MusicService.getPlaylistManager(), sp.getBoolean(CommonValues.SETTING_SHUFFLE, false), false, imageShuffle);
//
//        // repeat mode
//        String rp = sp.getString(CommonValues.SETTING_REPEAT, PlaylistManager.RepeatMode.NoRepeat.toString());
//        setRepeatMode(context, MusicService.getPlaylistManager(), PlaylistManager.RepeatMode.valueOf(rp), false, imageRepeat);
//    }

//    public static void dialogChangeVolume(Context mContext) {
//        final ImageView imageVolume;
//        SeekBar seekBarVolume;
//        Dialog myDialog;
//        myDialog = CustomDialog.showCustomDialog(mContext, R.layout.seekbar_dialog);
//        imageVolume = (ImageView) myDialog.findViewById(R.id.image_volume_dialog);
//        seekBarVolume = (SeekBar) myDialog.findViewById(R.id.seekbar_volume);
//        int maxVolume = 0;
//        int currentProgress = 0;
//        try {
//            final AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
//
//            maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//            currentProgress = audioManager
//                    .getStreamVolume(AudioManager.STREAM_MUSIC);
//
//            final int finalMaxVolume = maxVolume;
//
//            int percennt = (int) ((currentProgress / (float) finalMaxVolume) * 100.0f);
//            setImageViewVolume(imageVolume, percennt);
//            seekBarVolume.setMax(maxVolume);
//            seekBarVolume.setProgress(currentProgress);
//            imageVolume.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (CommonValues.isMute) {
//                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
//                                CommonValues.currentVolume, 0);
//                        int percennt = (int) ((CommonValues.currentVolume / (float) finalMaxVolume) * 100.0f);
//                        setImageViewVolume(imageVolume, percennt);
//                        Log.e("TAKASHI", "Media Volume value = " + percennt);
//                        CommonValues.isMute = false;
//                        setImageViewVolume(imageVolume, CommonValues.currentVolume);
//                    } else {
//                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
//                                0, 0);
//                        int percennt = (int) (((float) CommonValues.currentVolume / (float) finalMaxVolume) * 100.0f);
//                        setImageViewVolume(imageVolume, percennt);
//                        Log.e("TAKASHI", "Media Volume value = " + percennt);
//                        imageVolume.setImageResource(R.drawable.ic_volume_mute);
//                        CommonValues.isMute = true;
//                    }
//                }
//            });
//            seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
//                            progress, 0);
//                    CommonValues.currentVolume = progress;
//                    int percennt = (int) (((float) progress / (float) finalMaxVolume) * 100.0f);
//                    setImageViewVolume(imageVolume, percennt);
//                    Log.e("TAKASHI", "Media Volume value = " + percennt);
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        myDialog.show();
//    }

//    private static void setImageViewVolume(ImageView myImageViewVolume, int percentProgress) {
//        if (percentProgress <= 0) {
//            myImageViewVolume.setImageResource(R.drawable.ic_volume_mute);
//        } else if (percentProgress > 0 && percentProgress < 40) {
//            myImageViewVolume.setImageResource(R.drawable.ic_volume_low);
//        } else if (percentProgress >= 40 && percentProgress < 75) {
//            myImageViewVolume.setImageResource(R.drawable.ic_volume_medium);
//        } else if (percentProgress > 75) {
//            myImageViewVolume.setImageResource(R.drawable.ic_volume_max);
//        }
//    }


}


